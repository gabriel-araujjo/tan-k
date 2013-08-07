/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.model;

import br.ufrn.dca.controle.QuanserClient;
import br.ufrn.dca.controle.QuanserClientException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class Tank implements Runnable {

  private double voltage;
  private double level1;
  private double level2;
  private Integer voltageChannel;
  private Integer level1Channel;
  private Integer level2Channel;
  private QuanserClient quanserClient;
  private String ip;
  private Integer port;
  private Thread checkThread;

  public Tank() throws QuanserClientException {
    /* Initialize tank with default ip and port */
    this("127.0.0.1", 20081);
  }

  public Tank(String ip, Integer port) throws QuanserClientException {
    /* Initialize tank with default channels */
    this(ip, port, 0, 0, 0);
  }

  public Tank(String ip, Integer port, Integer voltageChannel, Integer level1Channel, Integer level2Channel) throws QuanserClientException {
    /* Set reading/writing channels */
    this.voltageChannel = voltageChannel;
    this.level1Channel = level1Channel;
    this.level2Channel = level2Channel;

    /* Set communication parameters */
    this.ip = ip;
    this.port = port;

    /* Establish communication with the plant */
    this.quanserClient = new QuanserClient(ip, port);
    this.checkThread = new Thread(this);
  }

  /**
   * @recursive Check levels of tank 1 and 2 If it is on simulator, delay 100
   * milliseconds between checks.
   */
  private void checkLevels() {
    try {
      setLevel1(quanserClient.read(level1Channel));
      setLevel2(quanserClient.read(level2Channel));
      Thread.sleep(100);
      checkLevels();
    } catch (QuanserClientException | InterruptedException ex) {
      Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void run() {
    checkLevels();
  }

  /**
   * @return the voltage
   */
  public double getVoltage() {
    return voltage;
  }

  /**
   * @param voltage the voltage to set
   */
  public synchronized void setVoltage(double v) throws QuanserClientException {
    this.voltage = v > 3 ? 3 : v > 1.7 && getLevel1() > 27 ? 1.7 : v < 0 && getLevel1() < 3 ? 0 : v < -3 ? -3 : v;
    quanserClient.write(voltageChannel, this.voltage);
  }

  /**
   * @return the level1
   */
  public synchronized double getLevel1() {
    return level1;
  }

  /**
   * @param level1 the level1 to set
   */
  private synchronized void setLevel1(double level1) {
    this.level1 = level1;
  }

  /**
   * @return the level2
   */
  public synchronized double getLevel2() {
    return level2;
  }

  /**
   * @param level2 the level2 to set
   */
  private synchronized void setLevel2(double level2) {
    this.level2 = level2;
  }

  /**
   * @return the ip
   */
  public synchronized String getIp() {
    return ip;
  }

  /**
   * @param ip the ip to set
   */
  public synchronized void setIp(String ip) {
    this.ip = ip;
  }
}
