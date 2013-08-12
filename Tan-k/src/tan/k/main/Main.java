/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.main;

import br.ufrn.dca.controle.QuanserClient;
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
        view.getGraphPanel1().addSerie("Tanque 1");
        view.getGraphPanel2().addSerie("Sinal");
        tank = new Tank(view.getCurrentIp(), view.getCurrentPort());
        controller = new TanKController(tank, view.getCurrentLoop(), view.getCurrentWave(), view.getCurrentPeriod(), view.getCurrentAmplitide(), view.getCurrentSetPoint(), view.getCurrentPV());

//    view.getGraphPanel1().startTime();
//    view.getGraphPanel2().startTime();

        view.onSetPointChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                System.out.println("setPoint : " + Double.toString((double) value));
                controller.setSetPoint((double) value);
            }
        });

        view.onLoopChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                controller.setLoop((Loop) value);
            }
        });

        view.onWaveChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                controller.setWaveType((Wave) value);
            }
        });

        view.onPeriodChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                controller.setPeriod((double) value);
            }
        });

        view.onAmplitudeChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                controller.setAmplitude((double) value);
            }
        });

        view.onProcessVariableChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                controller.setPv((TankNumber) value);
            }
        });

        view.onIpChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                try {
                    tank.setIp((String) value);
                } catch (QuanserClientException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        view.onPortChange(new ChangeParameterEvent() {
            @Override
            public void onChangeParameter(Object value) {
                try {
                    tank.setPort((int) value);
                } catch (QuanserClientException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    }
}
