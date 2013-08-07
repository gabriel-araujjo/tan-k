/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrn.dca.controle.QuanserClient;
import br.ufrn.dca.controle.QuanserClientException;

/**
 *
 * @author sergio
 */
public class TanKController implements Runnable {
  
  public TanKController(){
    
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    (new Thread(new TanKController())).start();
  }

  @Override
  public void run() {
    QuanserClient quanserClient;
    while (true) {
      try {
        quanserClient = new QuanserClient("127.0.0.1", 20081);
        double _read = quanserClient.read(0);
        System.out.println("Leitura Canal 0: " + _read * 6.25);
        System.out.println("Gravando 0.8 volts no canal 0...");
        quanserClient.write(0, 0.8);
        Thread.sleep(1000);
      } catch (QuanserClientException | InterruptedException ex) {
        Logger.getLogger(TanKController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
