/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrn.dca.controle.QuanserClient;
import br.ufrn.dca.controle.QuanserClientException;

/**
 *
 * @author sergio
 */
public class TanK implements Runnable{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        QuanserClient quanserClient;
      while(true){
        try {
          quanserClient = new QuanserClient("127.0.0.1", 20081);
          double _read = quanserClient.read(0);
          System.out.println("Leitura Canal 0: " + _read*6.25);
          System.out.println("Gravando 0.8 volts no canal 4...");
          quanserClient.write(0, 0.8);
          Thread.sleep(1000);
        } catch (QuanserClientException | InterruptedException ex) {
          Logger.getLogger(TanK.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }

  @Override
  public void run() {
    QuanserClient quanserClient = null;
      try {
        quanserClient = new QuanserClient("127.0.0.1", 20081);
      } catch (QuanserClientException ex) {
        Logger.getLogger(TanK.class.getName()).log(Level.SEVERE, null, ex);
      }
    while(true){
      try {
        double _read = 0.0;
        if(null!=quanserClient) quanserClient.read(0);
        System.out.println("Leitura Canal 0: " + _read);
        Thread.sleep(300);
        System.out.println("Gravando 1.2 volts no canal 4...");
        if(null!=quanserClient) quanserClient.write(4, 1.2);
        Thread.sleep(300);
      } catch (QuanserClientException | InterruptedException ex) {
        Logger.getLogger(TanK.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
