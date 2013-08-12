/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.main;

import javax.naming.ldap.Control;
import javax.swing.UIManager;
import org.java.ayatana.ApplicationMenu;
import org.java.ayatana.AyatanaDesktop;
import org.jfree.ui.about.Contributor;
import tan.k.controller.TanKController;
import tan.k.view.ChangeParameterEvent;
import tan.k.view.MainFrame;

/**
 *
 * @author gabriel
 */
public class Main {
  
  public static void main(String[] args) {
    /*
     * Set Look And Feel
     */
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
    }
    /* Create and display the form */

    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        MainFrame mainFrame = new MainFrame();
        //TanKController controller = new TanKController(null, TanKController.Loop.OPENED, TanKController.Wave.STEP, period, amplitude, setPoint, TanKController.TankNumber.TANK_1);
        
        
        
        if (AyatanaDesktop.isSupported()) {
          ApplicationMenu.tryInstall(mainFrame);
        }

        mainFrame.setVisible(true);
      }
    });
  }
  
}
