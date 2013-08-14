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
public class Tank {

  private double voltage;
  private double level1;
  private double level2;
  private Integer voltageChannel;
  private Integer level1Channel;
  private Integer level2Channel;
  private QuanserClient quanserClient;
  private String ip;
  private Integer port;

  public Tank() {
    /* Initialize tank with default ip and port */
    this("127.0.0.1", 20081);
  }

  public Tank(String ip, Integer port) {
    /* Initialize tank with default channels */
    this(ip, port, 0, 0, 1);
  }

  public Tank(String ip, Integer port, Integer voltageChannel, Integer level1Channel, Integer level2Channel) {
    /* Set reading/writing channels */
    this.voltageChannel = voltageChannel;
    this.level1Channel = level1Channel;
    this.level2Channel = level2Channel;

    /* Set communication parameters */
    this.ip = ip;
    this.port = port;
    
    this.voltage = this.level1 = this.level2 = 0;

    /* Establish communication with the plant */
    try {
      this.quanserClient = new QuanserClient(ip, port);
    } catch (QuanserClientException ex) {
      Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * @recursive Check levels of tank 1 and 2 If it is on simulator, delay 100
   * milliseconds between checks.
   */
  /*private void checkLevels() {
    try {
      setLevel1(quanserClient.read(level1Channel));
      setLevel2(quanserClient.read(level2Channel));
      quanserClient.write(voltageChannel, getVoltage());
      Thread.sleep(100);
      checkLevels();
    } catch (QuanserClientException | InterruptedException ex) {
      Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void run() {
    checkLevels();
  }*/

  /**
   * @return the voltage
   */
  public double getVoltage() {
    return voltage;
  }

  /**
   * @param voltage the voltage to set
   */
  public synchronized void setVoltage(double v) {
    double lastV = this.voltage;
    this.voltage = v > 3 ? 3 : v > 1.7 && getLevel1() > 27 ? 1.7 : v < 0 && getLevel1() < 3 ? 0 : v < -3 ? -3 : v;
    try{
      quanserClient.write(getVoltageChannel(), getVoltage());
    }catch(QuanserClientException e){
      this.voltage = lastV;
    }
  }

  /**
   * @return the level1
   */
  public synchronized double getLevel1() {
    try{
      return level1 = quanserClient.read(getLevel1Channel());
    }catch(QuanserClientException e){
      return level1;
    }
  }

  /**
   * @return the level2
   */
  public synchronized double getLevel2() {
    try{
      return level2 = quanserClient.read(getLevel2Channel());
    }catch(QuanserClientException e){
      return level2;
    }
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
  public synchronized void setIp(String ip) throws QuanserClientException {
    this.ip = ip;
    this.quanserClient = new QuanserClient(this.getIp(), getPort());
  }

  /**
   * @return the port
   */
  public synchronized Integer getPort() {
    return port;
  }

  /**
   * @param port the port to set
   */
  public synchronized void setPort(Integer port) throws QuanserClientException {
    this.port = port;
    this.quanserClient = new QuanserClient(this.getIp(), getPort());
  }

    /**
     * @return the voltageChannel
     */
    public Integer getVoltageChannel() {
        return voltageChannel;
    }

    /**
     * @param voltageChannel the voltageChannel to set
     */
    public void setVoltageChannel(Integer voltageChannel) {
        this.voltageChannel = voltageChannel;
    }

    /**
     * @return the level1Channel
     */
    public Integer getLevel1Channel() {
        return level1Channel;
    }

    /**
     * @param level1Channel the level1Channel to set
     */
    public void setLevel1Channel(Integer level1Channel) {
        this.level1Channel = level1Channel;
    }

    /**
     * @return the level2Channel
     */
    public Integer getLevel2Channel() {
        return level2Channel;
    }

    /**
     * @param level2Channel the level2Channel to set
     */
    public void setLevel2Channel(Integer level2Channel) {
        this.level2Channel = level2Channel;
    }
}
