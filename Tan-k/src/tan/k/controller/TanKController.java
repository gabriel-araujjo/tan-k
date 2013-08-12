/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import tan.k.model.Tank;

/**
 *
 * @author sergio
 */
public class TanKController implements Runnable{

  public  enum Wave {
    SINUSOID, SAWTOOTH, SQUARE, STEP, RANDOM 
  }
  
  public enum Loop {
    OPENED, CLOSED
  }
  
  public enum TankNumber {
    TANK_1, TANK_2
  }
  
  private Tank tank;
  
  private Wave wave;
  private double period;
  private double amplitude;
  
  private double setPoint;
  private TankNumber pv;
  
  private Loop loop;
  
  private double calculatedVoltage;
  
  public TanKController(Tank tank, Loop loop, Wave wave, double period, double amplitude, double setPoint, TankNumber pv) {
    this.tank = tank;
    this.loop = loop;
    
    this.wave = wave;
    this.period = period;
    this.amplitude = amplitude;
    this.setPoint = setPoint;
    this.pv = pv;
    
    this.calculatedVoltage = 0;
  }
  
  @Override
  public void run(){
    calculatedVoltage = 2;
    while(true){
      try {
        switch(loop){
          case OPENED:
            switch(wave){
              case SINUSOID:
                calculatedVoltage = calcSin();
                break;
              case SAWTOOTH:
                calculatedVoltage = calcSaw();
                break;
              case SQUARE:
                calculatedVoltage = calcSqu();
                break;
              case STEP:
                calculatedVoltage = calcStep();
                break;
              default:
                calculatedVoltage = calcRand();
                break;
            }
            break;
          default:
            calculatedVoltage = calcClosedLoop();
            break;
        }
        tank.setVoltage(calculatedVoltage);
        Thread.sleep(100);
      } catch (InterruptedException ex) {
        Logger.getLogger(TanKController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
  private double calcSin(){
    //implement this
    return this.amplitude;
  }
  
  private double calcSaw(){
    //implement this
    return this.amplitude;
  }
  
  private double calcSqu(){
    //implement this
    return this.amplitude;
  }
  
  private double calcStep(){
    //implement this
    return this.amplitude;
  }
  
  private double calcRand(){
    //implement this
    return this.amplitude;
  }
  
  private double calcClosedLoop(){
    return (pv == TankNumber.TANK_1 ? tank.getLevel1():tank.getLevel2()) - setPoint;
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
  
}