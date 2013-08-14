/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import tan.k.model.Tank;
import tan.k.view.ChangeParameterEvent;

/**
 *
 * @author sergio
 */
public class TanKController implements Runnable {

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
    private double time;
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

    public TanKController(Tank tank, Loop loop, Wave wave, double period, double amplitude, double setPoint, TankNumber pv) {
        this.tank = tank;
        this.loop = loop;

        this.time = time;

        this.wave = wave;
        this.period = period;
        this.amplitude = amplitude;
        this.setPoint = setPoint;
        this.pv = pv;

        this.calculatedVoltage = 0;
    }

    @Override
    public void run() {
        long lastTime = 0;
        long initTime = System.currentTimeMillis();
        double relativeTime;
        double[] toSignal = new double[2];
        while (true) {
            if (lastTime + 100 <= System.currentTimeMillis() ) {
                lastTime = System.currentTimeMillis();
                relativeTime = (lastTime-initTime)/1000;
                toSignal[0]= relativeTime;
                
                toSignal[1] = tank.getLevel1();
                onReadLevel1.onChangeParameter(toSignal);
                
                toSignal[1] = tank.getLevel2();
                onReadLevel2.onChangeParameter(toSignal);
                switch (loop) {
                    case OPENED:
                        switch (wave) {
                            case SINUSOID:
                                calculatedVoltage = calcSin(relativeTime, period, amplitude);
                                break;
                            case SAWTOOTH:
                                calculatedVoltage = calcSaw(relativeTime, period, amplitude);
                                break;
                            case SQUARE:
                                calculatedVoltage = calcSqu(relativeTime, period, amplitude);
                                break;
                            case STEP:
                                calculatedVoltage = calcStep(relativeTime, period, amplitude);
                                break;
                            default:
                                calculatedVoltage = calcRand(relativeTime, period, amplitude);
                                break;
                        }
                        break;
                    default:
                        calculatedVoltage = calcClosedLoop();
                        break;
                }
                tank.setVoltage(calculatedVoltage);
                
                toSignal[1] = calculatedVoltage;
                writeVoltage.onChangeParameter(toSignal);
            }
        }
    }

    private double calcSin(double t, double p, double a) {
        return a * Math.sin((t / p*2*Math.PI));
    }

    private double calcSaw(double t, double p, double a) {
        double r = t % p;
        return (a / p) * r;
    }

    private double calcSqu(double t, double p, double a) {
        double r = t % p;
        if (r >= p) {
            return 0;
        } 
        return a;
    }

    private double calcStep(double t, double p, double a) {
        return a;
    }

    private double calcRand(double t, double p, double a) {
        double r = t % (Math.random() * p);
        if (r >= p) {
            return Math.random() * a;
        } 
        return a;
    }

    private double calcClosedLoop() {
        return (pv == TankNumber.TANK_1 ? tank.getLevel1() : tank.getLevel2()) - setPoint;
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
}