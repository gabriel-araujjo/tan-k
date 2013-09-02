/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.controller;

import tan.k.model.Tank;
import tan.k.view.ChangeParameterEvent;

/**
 *
 * @author sergio
 */
public class TanKController implements Runnable {
  
  /*
   * Time when the class was loaded
   */
  public static long ABSOLUTE_TIME = System.currentTimeMillis();
  
  
  /*
   * tank model 
   */
  private Tank tank;
  
  /*
   * Auxiliar times
   */
  private long initTime;
  private long lastTime;
  private double curTime;
  private double samplePeriod;
  
  /*
   * Open Loop parameters
   */
  private OpenLoopSettings openLoopSettings;
  
  /*
   * Closed Loop parameters
   */
  private CloseLoopSettings closeLoopSettings;
  private double lastI;
  private double lastError;
  
  /*
   * Choosen loop
   */
  private Loop loop;
  
  
  /*
   * Events
   */
  private ChangeParameterEvent writeVoltage;
  private ChangeParameterEvent onReadLevel1;
  private ChangeParameterEvent onReadLevel2;
  
  /*
   * measured levels and voltage of the Tank 
   */
  private double calculatedVoltage;
  private double currentVoltage;
  private double currentLevel1;
  private double lastLevel1;
  private double currentLevel2;
  private double lastLevel2;
  
  /*
   * Aux ramdomTime and RandomAmplitude for OpenLoop Random wave
   */
  private Long randomTime;
  private double randomAmplitude;
  
  /*
   * Flag to start and run the controller
   */
  private boolean startRun;
  
  /**
   * 
   * @param tank
   * @param closeLoopSettings
   */
  public TanKController(Tank tank, CloseLoopSettings closeLoopSettings) {
    this(tank, Loop.CLOSED, closeLoopSettings, new OpenLoopSettings(Wave.STEP, 1));
  }
  
  /**
   * 
   * @param tank
   * @param openLoopSettings 
   */
  public TanKController(Tank tank, OpenLoopSettings openLoopSettings){
    this(tank, Loop.OPENED, new CloseLoopSettings(0, TankNumber.TANK_1, null, 1), openLoopSettings);
  }
  
  /**
   * 
   * @param tank
   * @param loop
   * @param closeLoopSettings
   * @param openLoopSettings 
   */
  public TanKController(Tank tank, Loop loop, CloseLoopSettings closeLoopSettings, OpenLoopSettings openLoopSettings){
    this.tank = tank;
    this.loop = loop;
    this.closeLoopSettings = closeLoopSettings;
    
    this.openLoopSettings = openLoopSettings;
    
    this.calculatedVoltage = 0;
    this.lastError = this.lastI = 0;
    
    this.startRun = false;
    
    this.initTime = System.currentTimeMillis();
    this.randomAmplitude = 0;
    this.randomTime = 0L;  
  }
  
  /**
   * @deprecated 
   * 
   * @param tank
   * @param loop
   * @param closeLoopSettings
   * @param wave
   * @param period
   * @param amplitude
   * @param setPoint
   * @param pv 
   */
  @Deprecated
  public TanKController(Tank tank, Loop loop, Wave wave, double period, double amplitude, double setPoint, TankNumber pv){
      this(tank, loop, new CloseLoopSettings(setPoint, pv, ControllerType.P, 1), new OpenLoopSettings(Wave.STEP, 1));
  }
  
  @Override
  public void run() {
    lastTime = 0;
    double[] toSignal = new double[2];
    while (true) {
      if (lastTime + 100 <= System.currentTimeMillis() && _getStartRun()) {
        lastTime = System.currentTimeMillis();
        samplePeriod = Math.floor(((double) System.currentTimeMillis() - lastTime ) / 100.0) /10.0;
        curTime += samplePeriod; 
        
        //System.out.println("loop_"+loop.name()+" wave_"+getWaveType());
        
        double e = calcError(), i = calcI(e), p = calcP(e), d = calcD(e), _d = calc_D();
        setLastError(e);
        setLastI(i);
        
        switch (loop) {
          case CLOSED:
            switch(getControllerType()){
                case P:
                    calculatedVoltage = p;
                    break;
                case PI:
                    calculatedVoltage = p + i;
                    break;
                case PID:
                    calculatedVoltage = p + i + d;
                    break;
                case PI_D:
                    calculatedVoltage = p + i + _d;
                    break;
                case PD:
                    calculatedVoltage = p + d;
                    break;
                default:
                    calculatedVoltage = e;
                    break;
            }
            break;
          case OPENED:
            switch (getWaveType()) {
              case RANDOM:
                calculatedVoltage = calcRand(curTime, getPeriod(), getAmplitude());
                break;
              case SAWTOOTH:
                calculatedVoltage = calcSaw(curTime, getPeriod(), getAmplitude());
                break;
              case SQUARE:
                calculatedVoltage = calcSqu(curTime, getPeriod(), getAmplitude());
                break;
              case STEP:
                calculatedVoltage = calcStep(curTime, getPeriod(), getAmplitude());
                break;
              case SINUSOID:
                calculatedVoltage = calcSin(curTime, getPeriod(), getAmplitude());
                break;
              default:
                calculatedVoltage = 0;
                break;
            }
            break;
          default:
            calculatedVoltage = 0;
            break;
        }
        
        lastLevel1 = currentLevel1;
        lastLevel2 = currentLevel2;
        
        currentLevel1 = tank.getLevel1();
        currentLevel2 = tank.getLevel2();
        
        if(lastTime>=initTime){
            tank.setVoltage(calculatedVoltage);

            currentVoltage = tank.getVoltage();
            toSignal[0] = Math.floor(((double) lastTime - initTime ) / 100.0) /10.0;

            toSignal[1] = currentVoltage;
            writeVoltage.onChangeParameter(toSignal);

            toSignal[1] = currentLevel1;
            onReadLevel1.onChangeParameter(toSignal);

            toSignal[1] = currentLevel2;
            onReadLevel2.onChangeParameter(toSignal);
        }
      }
    }
  }

  private double calcSin(double t, double p, double a) {
    return a/2 *Math.sin(t / p * 2 * Math.PI) + a/2;
  }

  private double calcSaw(double t, double p, double a) {
    double r = t % p;
    return (a / p) * r;
  }

  private double calcSqu(double t, double p, double a) {
    double r = t % p;
    if (r >= p/2) {
      return 0;
    }
    return a;
  }

  private double calcStep(double t, double p, double a) {
    return a;
  }

  private double calcRand(double t, double p, double a) {
    if(System.currentTimeMillis() > randomTime){
      randomTime = System.currentTimeMillis() + (long) ((Math.random()* 0.8 + 0.2)*p*1000);
      return randomAmplitude = (Math.random()*.8 +.2)*a;
    }else{
      return randomAmplitude;
    }
  }

  private double calcError() {
    return getSetPoint() - (getPv() == TankNumber.TANK_1 ? currentLevel1 : getPv() == TankNumber.TANK_2 ? currentLevel2 : 0);
  }

  public void onWriteVoltage(ChangeParameterEvent changeParameterEvent) {
    writeVoltage = changeParameterEvent;
  }

  public void onReadLevelTank1(ChangeParameterEvent changeParameterEvent) {
    onReadLevel1 = changeParameterEvent;
  }

  public void onReadLevelTank2(ChangeParameterEvent changeParameterEvent) {
    onReadLevel2 = changeParameterEvent;
  }
  
  /**
   * Reset initialization time
   */
  public synchronized void resetTime() {
    this.initTime = System.currentTimeMillis();
  }
  
  /**
   * @return the initTime
   */
  public synchronized long getInitTime() {
    return initTime;
  }
  
  /**
   * @return the tank
   */
  public Tank getTank() {
    return tank;
  }

  /**
   * @return the waveType
   */
  public Wave getWaveType() {
    return openLoopSettings.wave;
  }

  /**
   * @param waveType the waveType to set
   */
  public void setWaveType(Wave waveType) {
    this.openLoopSettings.wave = waveType;
  }

  /**
   * @return the period
   */
  public double getPeriod() {
    return openLoopSettings.period;
  }

  /**
   * @param period the period to set
   */
  public void setPeriod(double period) {
    this.openLoopSettings.period = period;
  }

  /**
   * @return the amplitude
   */
  public double getAmplitude() {
    return openLoopSettings.amplitude;
  }

  /**
   * @param amplitude the amplitude to set
   */
  public void setAmplitude(double amplitude) {
    this.openLoopSettings.amplitude = amplitude;
  }

  /**
   * @return the loop
   */
  public Loop getLoop() {
    return loop;
  }

  /**
   * @param loop the loop to set
   */
  public void setLoop(Loop loop) {
    this.loop = loop;
  }

  /**
   * @return the setPoint
   */
  public double getSetPoint() {
    return closeLoopSettings.setPoint;
  }

  /**
   * @param setPoint the setPoint to set
   */
  public void setSetPoint(double setPoint) {
    this.closeLoopSettings.setPoint = setPoint;
  }

  /**
   * @return the pv
   */
  public TankNumber getPv() {
    return closeLoopSettings.pv;
  }

  /**
   * @param pv the pv to set
   */
  public void setPv(TankNumber pv) {
    this.closeLoopSettings.pv = pv;
  }
  
  /**
   * 
   * @return P, PI, PD, PID or PI_D ControllerType 
   */
  public ControllerType getControllerType(){
      return closeLoopSettings.type;
  }
  
  /**
   * 
   * @param controllerType 
   */
  public void setControllerType(ControllerType controllerType){
      this.closeLoopSettings.type = controllerType;
  }
  
  /**
   * 
   * @param controllerType 
   */
  public void setControllerType(String controllerType){
      this.closeLoopSettings.type = ControllerType.valueOf(controllerType);
  }
  
  /**
   * 
   * @return p
   */
  public double getKp(){
      return closeLoopSettings.P;
  }
  
  /**
   * 
   * @param p 
   */
  public void setKp(double p){
      this.closeLoopSettings.P = p;
  }
  
  /**
   * 
   * @return i 
   */
  public double getKi(){
      return closeLoopSettings.I;
  }
  
  /**
   * 
   * @param i 
   */
  public void setKi(double i){
      this.closeLoopSettings.I = i;
  }
  /**
   * 
   * @return d
   */
  public double getKd(){
      return closeLoopSettings.D;
  }
  
  /**
   * 
   * @param d 
   */
  public void setKd(double d){
      this.closeLoopSettings.D = d;
  }

    /**
     * @return the lastIError
     */
    public double getLastI() {
        return lastI;
    }

    /**
     * @param lastIError the lastIError to set
     */
    public void setLastI(double lastIError) {
        this.lastI = lastIError;
    }

    /**
     * @return the lastDError
     */
    public double getLastError() {
        return lastError;
    }

    /**
     * @param lastError the lastError to set
     */
    public void setLastError(double lastDError) {
        this.lastError = lastDError;
    }
  
  public synchronized void startController(){
    this.initTime = System.currentTimeMillis();
    this.startRun = true;
  }
  
  public synchronized void pauseController(){
    this.tank.setVoltage(0);
    this.startRun = false;
  }
  
  private synchronized boolean _getStartRun(){
    return this.startRun;
  }

    private double calcP(double e) {
        return getKp()*e;
    }

    private double calcI(double e) {
        return getLastI() + getKi()*samplePeriod*e;
    }

    private double calcD(double e) {
        return getKd()*(e - getLastError())/samplePeriod;
    }

    private double calc_D() {
        return getKd()*(getPv()==TankNumber.TANK_1?(currentLevel1-lastLevel1):(currentLevel2-lastLevel2))/samplePeriod;
    }
  
  public enum Wave {

    SINUSOID, SAWTOOTH, SQUARE, STEP, RANDOM
  }

  public enum Loop {

    OPENED, CLOSED
  }
  
  public enum ControllerType {
      P, PI, PD, PID, PI_D
  }
  
  public enum TankNumber {

    TANK_1, TANK_2
  }
  
  public static class OpenLoopSettings{
      public double period;
      public double amplitude;
      public Wave wave;
      public OpenLoopSettings(String wave, double amplitude){
          this(Wave.valueOf(wave), amplitude);
      }
      public OpenLoopSettings(String wave, double amplitude, double period){
          this(Wave.valueOf(wave), amplitude, period);
      }
      public OpenLoopSettings(Wave wave, double amplitude){
          if(Wave.STEP.equals(wave)){
              this.wave = wave;
              this.amplitude = amplitude;
              period = 0;
          }
      }
      public OpenLoopSettings(Wave wave, double amplitude, double period){
          this.wave = wave;
          this.amplitude = amplitude;
          this.period = period;
      }
  }
  
  public static class CloseLoopSettings{
    public ControllerType type;
    public double P;
    public double I;
    public double D;
    public TankNumber pv;
    public double setPoint;
    public CloseLoopSettings(double setPoint, String pv, String controllerType, double... weights){
        this(setPoint, TankNumber.valueOf(pv), ControllerType.valueOf(controllerType), weights);
    }
    public CloseLoopSettings(double setPoint, TankNumber pv, ControllerType controllerType, double... weights) {
        this.pv = pv;
        this.setPoint = setPoint;
        type = controllerType;
        P = weights[0];
        switch(type){
            case PI:
                I = weights[1];
                D = 0;
                break;
            case PID:
            case PI_D:
                I = weights[1];
                D = weights[2];
                break;
            case PD:
                D = weights[1];
                I = 0;
                break;
            default:
                break;
        }
    }
  }
}
