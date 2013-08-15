/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.controller;

import java.util.Random;
import org.omg.CORBA.PRIVATE_MEMBER;
import tan.k.model.Tank;
import tan.k.view.ChangeParameterEvent;

/**
 *
 * @author sergio
 */
public class TanKController implements Runnable {
  
  public static long ABSOLUTE_TIME = System.currentTimeMillis();
  
  public synchronized void resetTime() {
    this.initTime = System.currentTimeMillis();
  }

  /**
   * @return the initTime
   */
  public synchronized long getInitTime() {
    return initTime;
  }

  public enum Wave {

    SINUSOID, SAWTOOTH, SQUARE, STEP, RANDOM
  }

  public enum Loop {

    OPENED, CLOSED
  }

  public enum TankNumber {

    TANK_1, TANK_2
  }
  private Tank tank;
  private long initTime;
  private long lastTime;
  private double curTime;
  private Wave wave;
  private double period;
  private double amplitude;
  private double setPoint;
  private TankNumber pv;
  private Loop loop;
  private double calculatedVoltage;
  private ChangeParameterEvent writeVoltage;
  private ChangeParameterEvent onReadLevel1;
  private ChangeParameterEvent onReadLevel2;
  
  private double currentVoltage;
  private double currentLevel1;
  private double currentLevel2;
  private boolean startRun;
  private Long randomTime;
  private double randomAmplitude;
  
  public TanKController(Tank tank, Loop loop, Wave wave, double period, double amplitude, double setPoint, TankNumber pv) {
    this.tank = tank;
    this.loop = loop;

    this.initTime = System.currentTimeMillis();

    this.wave = wave;
    this.period = period;
    this.amplitude = amplitude;
    this.setPoint = setPoint;
    this.pv = pv;

    this.calculatedVoltage = 0;
    this.startRun = false;
    
    this.randomAmplitude = 0;
    this.randomTime = 0L;
  }

  @Override
  public void run() {
    lastTime = 0;
    double[] toSignal = new double[2];
    while (true) {
      if (lastTime + 100 <= System.currentTimeMillis() && _getStartRun()) {
        lastTime = System.currentTimeMillis();
        curTime = Math.floor(((double) System.currentTimeMillis() - getInitTime() ) / 100.0) /10.0;
        
        System.out.println("loop_"+loop.name()+" wave_"+wave);
        
        switch (loop) {
          case CLOSED:
            calculatedVoltage = calcClosedLoop();
            break;
          case OPENED:
            switch (wave) {
              case RANDOM:
                calculatedVoltage = calcRand(curTime, period, amplitude);
                break;
              case SAWTOOTH:
                calculatedVoltage = calcSaw(curTime, period, amplitude);
                break;
              case SQUARE:
                calculatedVoltage = calcSqu(curTime, period, amplitude);
                break;
              case STEP:
                calculatedVoltage = calcStep(curTime, period, amplitude);
                break;
              case SINUSOID:
                calculatedVoltage = calcSin(curTime, period, amplitude);
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
    return a*Math.sin(t / p * 2 * Math.PI) +a;
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

  private double calcClosedLoop() {
    return setPoint - (pv == TankNumber.TANK_1 ? currentLevel1 : pv == TankNumber.TANK_2 ? currentLevel2 : 0) ;
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
   * @return the tank
   */
  public Tank getTank() {
    return tank;
  }

  /**
   * @return the waveType
   */
  public Wave getWaveType() {
    return wave;
  }

  /**
   * @param waveType the waveType to set
   */
  public void setWaveType(Wave waveType) {
    this.wave = waveType;
  }

  /**
   * @return the period
   */
  public double getPeriod() {
    return period;
  }

  /**
   * @param period the period to set
   */
  public void setPeriod(double period) {
    this.period = period;
  }

  /**
   * @return the amplitude
   */
  public double getAmplitude() {
    return amplitude;
  }

  /**
   * @param amplitude the amplitude to set
   */
  public void setAmplitude(double amplitude) {
    this.amplitude = amplitude;
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
    return setPoint;
  }

  /**
   * @param setPoint the setPoint to set
   */
  public void setSetPoint(double setPoint) {
    this.setPoint = setPoint;
  }

  /**
   * @return the pv
   */
  public TankNumber getPv() {
    return pv;
  }

  /**
   * @param pv the pv to set
   */
  public void setPv(TankNumber pv) {
    this.pv = pv;
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
  
}
