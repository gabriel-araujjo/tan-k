package tan.k;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrn.dca.controle.QuanserClient;
import br.ufrn.dca.controle.QuanserClientException;


/**
 *
 * @author sergio,ricardo,guilherme,gabriel
 */
public class TanK implements Runnable{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      (new Thread(new TanK())).start();

    }
    
  @Override
  public void run() {
    QuanserClient quanserClient;
   
    float volts = 0;
    GraphicPanel gp = new GraphicPanel();
    JFrameTeste janela = new JFrameTeste();  
    janela.setGraphicPanel1(gp);
    gp.setVisible(true);
    janela.setVisible(true);
    

      while(true){
        try {
          quanserClient = new QuanserClient("127.0.0.1", 20081);
          double _read = quanserClient.read(0); 
          gp.addValue((float) _read);    
          
          janela.revalidate();
          janela.repaint();
          gp.revalidate();
          gp.repaint();
          
          volts = (float) (volts + 0.1);
          if (volts >= 1.5) volts = 0;
          System.out.println("Leitura Canal 0: " + _read*6.25);
          System.out.println("Gravando "+volts+" volts no canal 0...");
          quanserClient.write(0, volts);
          Thread.sleep(1000);
          
        } catch (QuanserClientException | InterruptedException ex) {
          Logger.getLogger(TanK.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
  }
}
