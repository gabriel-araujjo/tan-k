/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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
  private PrintWriter out;
  private BufferedReader in;
  private long nextLevel1Time;
  private long nextLevel2Time;
  private String readLine;
  private boolean connected;
  private String ip;
  private Integer port;

  public Tank() {
    /* Initialize tank with default ip and port */
    //this("127.0.0.1", 20081);
    this("10.13.99.69",20081);
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
    
    this.nextLevel1Time = this.nextLevel2Time = 0;
    
    this.voltage = this.level1 = this.level2 = 0;
  }

  public synchronized void connect() {
    try {
      Socket socket = new Socket(ip, port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
      connected = true;
    } catch (Exception ex) {
      
    }
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
  public synchronized void setVoltage(double v) {
    double lastV = this.voltage;
    this.voltage = v > 1.4 && getLevel1() > 27 ? 1.4 : v < 0 && getLevel1() < 3 ? 0 : v < -3 ? -3 : v > 3 ? 3 : v;
    if (isConnected()) {
      try {
        out.println("WRITE "+this.voltageChannel+" "+this.voltage);
        readLine = in.readLine();
        this.voltage = readLine.matches("ACK")?this.voltage:lastV;
      } catch (IOException ex) {
        //Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * @return the level1
   */
  public synchronized double getLevel1() {
    if(System.currentTimeMillis() > nextLevel1Time){
      try {
        out.println("READ "+this.level1Channel);
        readLine = in.readLine();
        level1 = Double.parseDouble(readLine) * 6.25;
        nextLevel1Time = System.currentTimeMillis() + 80;
      } catch (IOException | NumberFormatException ex) {
        Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return level1;
  }

  /**
   * @return the level2
   */
  public synchronized double getLevel2() {
    if(System.currentTimeMillis() > nextLevel2Time){
      try {
        out.println("READ " + this.level2Channel);
        readLine = in.readLine();
        level2 = Double.parseDouble(readLine) * 6.25;
        nextLevel2Time = System.currentTimeMillis() + 80;
      } catch (IOException | NumberFormatException ex) {
        Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return level2;
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

  /**
   * @return the port
   */
  public synchronized Integer getPort() {
    return port;
  }

  /**
   * @param port the port to set
   */
  public synchronized void setPort(Integer port) {
    this.port = port;
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
  
  /** 
   * @return true if connection was successful
   */
  public boolean isConnected() {
    return connected;
  }
}
