/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.main;

import br.ufrn.dca.controle.QuanserClientException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import org.java.ayatana.ApplicationMenu;
import org.java.ayatana.AyatanaDesktop;
import tan.k.controller.TanKController;
import tan.k.controller.TanKController.Loop;
import tan.k.controller.TanKController.TankNumber;
import tan.k.controller.TanKController.Wave;
import tan.k.model.Tank;
import tan.k.view.ChangeParameterEvent;
import tan.k.view.MainFrame;

/**
 *
 * @author gabriel
 */
public class Main {
    
    private static Tank tank;
    private static MainFrame view;
    private static TanKController controller;
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        view = new MainFrame();
        view.getGraphPanel3().getLegend().setVisible(false);
        view.getGraphPanel3().addSerie("Sinal para configuracao");
        // view.getGraphPanel1().addSerie("Tanque 1");
        //view.getGraphPanel2().addSerie("Sinal");
        //tank = new Tank(view.getCurrentIp(), view.getCurrentPort());
        //controller = new TanKController(tank, view.getCurrentLoop(), view.getCurrentWave(), view.getCurrentPeriod(), view.getCurrentAmplitide(), view.getCurrentSetPoint(), view.getCurrentPV());

//    view.getGraphPanel1().startTime();
//    view.getGraphPanel2().startTime();

        view.onSetPointChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("setPoint : " + Double.toString((double) value));
                //controller.setSetPoint((double) value);
            }
        });
        
        view.onLoopChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("loop = "+((Loop) value).name());
            }
        });
        
        view.onWaveChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("wave = "+((Wave) value).name());
                //controller.setWaveType((Wave) value);
            }
        });
        
        view.onPeriodChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("period = "+((double) value));
                //controller.setPeriod((double) value);
            }
        });
        
        view.onAmplitudeChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("amplitude = "+((double) value));
                //controller.setAmplitude((double) value);
            }
        });
        
        view.onProcessVariableChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("PV = "+((TankNumber) value).name());
                //controller.setPv((TankNumber) value);
            }
        });
        
        view.onIpChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("ip = "+((String) value));
//                try {
//                    tank.setIp((String) value);
//                } catch (QuanserClientException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        });
        
        view.onPortChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("port = "+((int) value));
//                try {
//                    tank.setPort((int) value);
//                } catch (QuanserClientException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        });
        
        view.onWriteChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("writeChannel = "+((int) value));
//                tank.setVoltageChannel((int) value);
            }
        });

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*
                 * Set Look And Feel
                 */
                
                if (AyatanaDesktop.isSupported()) {
                    ApplicationMenu.tryInstall(view);
                }
                
                view.setVisible(true);
            }
        });
        
        (new Thread(controller)).start();
    }
}
