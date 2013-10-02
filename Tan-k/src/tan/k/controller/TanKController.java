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
   * measured levels and voltage of the TankTag 
   */
  private double calculatedVoltage;
  private double currentVoltage;
  private boolean activedLock;
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
  private ChangeParameterEvent lockChange;
  private ChangeParameterEvent pChange;
  private ChangeParameterEvent dChange;
  private ChangeParameterEvent iChange;
  private ChangeParameterEvent errorChange;
  private int putPointsFlag;
  private double p;
  private double i;
  private double d;
  private double _d;
  private double e;
  private double stepHeight;
  private double mp;
  private boolean mpCalculated;
  private double peakTime;
  private boolean steady5percentCalculated;
  private double settlingTime;
  private boolean rising;
  private double curOvershoot;

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
  public TanKController(Tank tank, OpenLoopSettings openLoopSettings) {
    this(tank, Loop.OPENED, new CloseLoopSettings(0, TankTag.TANK_1, null, 1), openLoopSettings);
  }

  /**
   *
   * @param tank
   * @param loop
   * @param closeLoopSettings
   * @param openLoopSettings
   */
  public TanKController(Tank tank, Loop loop, CloseLoopSettings closeLoopSettings, OpenLoopSettings openLoopSettings) {
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
    
    this.putPointsFlag = 0;
    this.mp = -1;
    this.curOvershoot = -1;
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
  public TanKController(Tank tank, Loop loop, Wave wave, double period, double amplitude, double setPoint, TankTag pv) {
    this(tank, loop, new CloseLoopSettings(setPoint, pv, ControllerType.P, 1), new OpenLoopSettings(Wave.STEP, 1));
  }

  @Override
  public void run() {
    setLastTime(0);
    double[] toSignal = new double[2];
    double lastOvershoot, relativeCurTime;
    while (true) {
      if (getLastTime() + 100 <= System.currentTimeMillis() && _getStartRun()) {
        samplePeriod = Math.floor(((double) System.currentTimeMillis() - getLastTime()) / 100.0) / 10.0;
        curTime += samplePeriod;
        setLastTime(System.currentTimeMillis());
        
        lastLevel1 = getCurrentLevel1();
        lastLevel2 = getCurrentLevel2();

        currentLevel1 = tank.getLevel1();
        currentLevel2 = tank.getLevel2();
        
        setE(calcError());
        i = calcI(getE());
        p = calcP(getE());
        d = calcD(getE());
        _d = calc_D();
        setLastError(getE());
        setLastI(i);
        switch (loop) {
          case CLOSED:
            if(!isMpCalculated() || !isSteady5percentCalculated()){
              relativeCurTime = Math.floor(((double) getLastTime() - initTime) / 100.0) / 10.0;
              lastOvershoot = curOvershoot;
              curOvershoot = calcOvershoot();
              System.out.println("curOvershoot="+ curOvershoot);
              System.out.println("lastOvershoot= "+lastOvershoot);
              System.out.println("mp="+mp);
              System.out.println("rising="+rising);
              if(!isMpCalculated()){
                if(mp > 1.1*curOvershoot && Math.abs(mp) > Math.abs(1.1*curOvershoot)){
                  mpCalculated = true;
                }else if(curOvershoot > mp){
                  mp = curOvershoot;
                  peakTime = relativeCurTime;
                }
              }
              if(!isSteady5percentCalculated()){
                if(Math.abs(curOvershoot) <= 0.05){
                  settlingTime = relativeCurTime;
                  if(rising ^ curOvershoot < lastOvershoot){
                      steady5percentCalculated = true;
                  }
                }
                rising = curOvershoot < lastOvershoot;
              }
            }
            switch (getControllerType()) {
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
                calculatedVoltage = getE();
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
                

        if (getLastTime() >= initTime) {
          tank.setVoltage(getCalculatedVoltage());
          currentVoltage = tank.getVoltage();
          if(putPointsFlag==0){
            putPointsFlag++;
            if((getCurrentVoltage()!=getCalculatedVoltage()) != activedLock ){
              call(lockChange, activedLock = (getCurrentVoltage()!=getCalculatedVoltage()));
            }
            /*
            toSignal[0] = Math.floor(((double) getLastTime() - initTime) / 100.0) / 10.0;

            toSignal[1] = currentLevel1;
            call(onReadLevel1, toSignal);

            toSignal[1] = currentLevel2;
            call(onReadLevel2, toSignal);

            toSignal[1] = e;
            call(errorChange, toSignal);

            toSignal[1] = p;
            call(pChange, toSignal);

            toSignal[1] = i;
            call(iChange, toSignal);

            toSignal[1] = d;
            call(dChange, toSignal);

            toSignal[1] = currentVoltage;
            call(writeVoltage, toSignal);
            */
          }else{
            if(putPointsFlag != 4) putPointsFlag++;
            else putPointsFlag = 0;
          }
        }
      }
    }
  }

  private double calcSin(double t, double p, double a) {
    return a / 2 * Math.sin(t / p * 2 * Math.PI) + a / 2;
  }

  private double calcSaw(double t, double p, double a) {
    double r = t % p;
    return (a / p) * r;
  }

  private double calcSqu(double t, double p, double a) {
    double r = t % p;
    if (r >= p / 2) {
      return 0;
    }
    return a;
  }

  private double calcStep(double t, double p, double a) {
    return a;
  }

  private double calcRand(double t, double p, double a) {
    if (System.currentTimeMillis() > randomTime) {
      randomTime = System.currentTimeMillis() + (long) ((Math.random() * 0.8 + 0.2) * p * 1000);
      return randomAmplitude = (Math.random() * .8 + .2) * a;
    } else {
      return randomAmplitude;
    }
  }

  private double calcError() {
    return closeLoopSettings.setPoint - (closeLoopSettings.pv == TankTag.TANK_2 ? getCurrentLevel2() :  getCurrentLevel1());
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
  public TankTag getPv() {
    return closeLoopSettings.pv;
  }

  /**
   * @param pv the pv to set
   */
  public void setPv(TankTag pv) {
    this.closeLoopSettings.pv = pv;
  }

  /**
   *
   * @return P, PI, PD, PID or PI_D ControllerType
   */
  public ControllerType getControllerType() {
    return closeLoopSettings.type;
  }

  /**
   *
   * @param controllerType
   */
  public void setControllerType(ControllerType controllerType) {
    this.closeLoopSettings.type = controllerType;
  }

  /**
   *
   * @param controllerType
   */
  public void setControllerType(String controllerType) {
    this.closeLoopSettings.type = ControllerType.valueOf(controllerType);
  }

  /**
   *
   * @return p
   */
  public double getKp() {
    return closeLoopSettings.P;
  }

  /**
   *
   * @param p
   */
  public void setKp(double p) {
    this.closeLoopSettings.P = p;
  }

  /**
   *
   * @return i
   */
  public double getKi() {
    return closeLoopSettings.I;
  }

  /**
   *
   * @param i
   */
  public void setKi(double i) {
    this.closeLoopSettings.I = i;
  }

  /**
   *
   * @return d
   */
  public double getKd() {
    return closeLoopSettings.D;
  }

  /**
   *
   * @param d
   */
  public void setKd(double d) {
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
  public void setLastError(double e) {
    this.lastError = e;
  }

  public synchronized void startController() {
    this.startRun = false;
    Long vaEm = System.currentTimeMillis() + 100L;
    while(System.currentTimeMillis() < vaEm);
    this.initTime = System.currentTimeMillis();
    this.stepHeight = getSetPoint() - (getPv().equals(TankTag.TANK_1)?tank.getLevel1():tank.getLevel2());
    this.steady5percentCalculated = false;
    this.mpCalculated = false;
    this.mp = -1;
    this.curOvershoot = -1;
    this.rising = true;
    
    currentLevel1 = tank.getLevel1();
    currentLevel2 = tank.getLevel2();
    setLastTime(System.currentTimeMillis());
    
    this.startRun = true;
  }

  public synchronized void pauseController() {
    this.startRun = false;
    this.calculatedVoltage = 0;
    this.currentVoltage = 0;
    this.tank.setVoltage(0);
  }

  private synchronized boolean _getStartRun() {
    return this.startRun;
  }

  private double calcP(double e) {
    return this.p = closeLoopSettings.P * e;
  }

  private double calcI(double e) {
    return this.i = lastI + closeLoopSettings.I * samplePeriod * e;
  }

  private double calcD(double e) {
    return this.d = closeLoopSettings.D * (e - lastError) / samplePeriod;
  }

  private double calc_D() {
    return this._d = getKd() * (getPv() == TankTag.TANK_1 ? (getCurrentLevel1() - lastLevel1) : (getCurrentLevel2() - lastLevel2)) / samplePeriod;
  }

  public void onLockStatusChange(ChangeParameterEvent changeParameterEvent) {
    lockChange = changeParameterEvent;
  }

  public void onCalcP(ChangeParameterEvent changeParameterEvent) {
    pChange = changeParameterEvent;
  }

  public void onCalcD(ChangeParameterEvent changeParameterEvent) {
    dChange = changeParameterEvent;
  }

  public void onCalcI(ChangeParameterEvent changeParameterEvent) {
    iChange = changeParameterEvent;
  }

  /**
   * @return the calculatedVoltage
   */
  public synchronized double getCalculatedVoltage() {
    return calculatedVoltage;
  }

  public void onCalcError(ChangeParameterEvent changeParameterEvent) {
    errorChange = changeParameterEvent;
  }

  /**
   * @return the lastTime
   */
  private synchronized long getLastTime() {
    return lastTime;
  }

  /**
   * @param lastTime the lastTime to set
   */
  private synchronized void setLastTime(long lastTime) {
    this.lastTime = lastTime;
  }

  /**
   * @return the currentVoltage
   */
  public synchronized double getCurrentVoltage() {
    return currentVoltage;
  }

  /**
   * @return the currentLevel1
   */
  public synchronized double getCurrentLevel1() {
    return currentLevel1;
  }

  /**
   * @return the currentLevel2
   */
  public synchronized double getCurrentLevel2() {
    return currentLevel2;
  }

  /**
   * @return the p
   */
  public double getP() {
    return p;
  }

  /**
   * @return the i
   */
  public double getI() {
    return i;
  }

  /**
   * @return the d
   */
  public double getD() {
    return d;
  }
  
  /**
   * @return the _d
   */
  public double get_D() {
    return _d;
  }

  /**
   * @return the e
   */
  public double getE() {
    return e;
  }

  /**
   * @param e the e to set
   */
  public void setE(double e) {
    this.e = e;
  }

  private double calcOvershoot() {
    if(stepHeight==0) return 0;
    return ((closeLoopSettings.pv == TankTag.TANK_1? tank.getLevel1():tank.getLevel2())-closeLoopSettings.setPoint)/stepHeight;
  }

  /**
   * @return the mpCalculated
   */
  public boolean isMpCalculated() {
    return mpCalculated;
  }

  /**
   * @return the peakTime
   */
  public double getPeakTime() {
    return peakTime;
  }

  /**
   * @return the steady5percentCalculated
   */
  public boolean isSteady5percentCalculated() {
    return steady5percentCalculated;
  }

  /**
   * @return the settlingTime
   */
  public double getSettlingTime() {
    return settlingTime;
  }

  /**
   * @return the mp
   */
  public double getMp() {
    return mp;
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

  public enum TankTag {

    TANK_1, TANK_2
  }
  
  private void call(final ChangeParameterEvent callback, final Object param){
    callback.onChangeParameter(param);
  }

  public static class OpenLoopSettings {

    public double period;
    public double amplitude;
    public Wave wave;

    public OpenLoopSettings(String wave, double amplitude) {
      this(Wave.valueOf(wave), amplitude);
    }

    public OpenLoopSettings(String wave, double amplitude, double period) {
      this(Wave.valueOf(wave), amplitude, period);
    }

    public OpenLoopSettings(Wave wave, double amplitude) {
      if (Wave.STEP.equals(wave)) {
        this.wave = wave;
        this.amplitude = amplitude;
        period = 0;
      }
    }

    public OpenLoopSettings(Wave wave, double amplitude, double period) {
      this.wave = wave;
      this.amplitude = amplitude;
      this.period = period;
    }
  }

  public static class CloseLoopSettings {

    public ControllerType type;
    public double P;
    public double I;
    public double D;
    public TankTag pv;
    public double setPoint;

    public CloseLoopSettings(double setPoint, String pv, String controllerType, double... weights) {
      this(setPoint, TankTag.valueOf(pv), ControllerType.valueOf(controllerType), weights);
    }

    public CloseLoopSettings(double setPoint, TankTag pv, ControllerType controllerType, double... weights) {
      this.pv = pv;
      this.setPoint = setPoint;
      type = controllerType;
      P = weights[0];
      if (weights.length < 3) {
        switch (type) {
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
      } else {
        I = weights[1];
        D = weights[2];
      }
    }
  }
}
