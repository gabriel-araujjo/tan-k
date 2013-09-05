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
import tan.k.controller.TanKController.ControllerType;
import tan.k.controller.TanKController.Loop;
import tan.k.controller.TanKController.TankTag;
import tan.k.controller.TanKController.Wave;
import tan.k.model.Tank;
import tan.k.view.ChangeParameterEvent;
import tan.k.view.MainFrame;
import tan.k.view.MainFrame.ConnectionStatus;

/**
 *
 * @author gabriel
 */
public class Main {

  private static Tank tank;
  private static MainFrame view;
  private static TanKController controller;
  private static Thread controllerThread;

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    view = new MainFrame();
    tank = new Tank(view.getCurrentIp(), view.getCurrentPort());
    controller = new TanKController(
            tank,
            view.getCurrentLoop(),
            view.getCloseLoopSettings(),
            view.getOpenLoopSettings());
    controllerThread = new Thread(controller);

    view.onSetPointChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("setPoint : " + Double.toString((double) value));
        controller.setSetPoint((double) value);
      }
    });

    view.onControllerTypeChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("controllerType =" + ((ControllerType) value).name());
        controller.setControllerType((ControllerType) value);
      }
    });

    view.onKpChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("Kp = " + ((double) value));
        controller.setKp((double) value);
      }
    });

    view.onKiChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("Ki = " + ((double) value));
        controller.setKi((double) value);
      }
    });

    view.onKdChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("Kd = " + ((double) value));
        controller.setKd((double) value);
      }
    });

    view.onLoopChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("loop = " + ((Loop) value).name());
        controller.setLoop((Loop) value);
      }
    });

    view.onWaveChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("wave = " + ((Wave) value).name());
        controller.setWaveType((Wave) value);
      }
    });

    view.onPeriodChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("period = " + ((double) value));
        controller.setPeriod((double) value);
      }
    });

    view.onAmplitudeChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("amplitude = " + ((double) value));
        controller.setAmplitude((double) value);
      }
    });

    view.onProcessVariableChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        if (null != value) {
          System.out.println("PV = " + ((TankTag) value).name());
          controller.setPv((TankTag) value);
        }
      }
    });

    view.onIpChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("ip = " + ((String) value));
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
        System.out.println("port = " + ((int) value));
        try {
          tank.setPort((int) value);
        } catch (QuanserClientException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });

    view.onApplyClicked(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        controller.startController();
      }
    });

    view.onStopClicked(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        controller.pauseController();
      }
    });

    view.onConnectedClicked(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        try {
          tank.connect();
          view.setConnectionStatus(ConnectionStatus.CONNECTED);
        } catch (QuanserClientException ex) {
          view.setConnectionStatus(ConnectionStatus.DISCONNECTED);
        }
      }
    });


    view.onWriteChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("writeChannel = " + ((int) value));
        tank.setVoltageChannel((int) value);
      }
    });

    controller.onWriteVoltage(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        view.addPointToGraph(MainFrame.SENDED_SIGNAL_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
        view.addPointToGraph(MainFrame.CALCULATED_SIGNAL_CURVE, ((double []) value)[0], controller.getCalculatedVoltage());
        view.addPointToGraph(MainFrame.SETPOINT_CURVE, ((double []) value)[0], controller.getSetPoint());
      }
    });

    controller.onReadLevelTank1(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        view.addPointToGraph(MainFrame.TANK1_LEVEL_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
      }
    });

    controller.onReadLevelTank2(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        view.addPointToGraph(MainFrame.TANK2_LEVEL_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
      }
    });
    
    controller.onCalcP(new ChangeParameterEvent() {

      @Override
      public void onChangeParameter(Object value) {
        view.addPointToGraph(MainFrame.PROPORCIONAL_PART_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
      }
    });
    
    controller.onCalcI(new ChangeParameterEvent() {

      @Override
      public void onChangeParameter(Object value) {
        view.addPointToGraph(MainFrame.INTEGRAL_PART_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
      }
    });
    
    controller.onCalcD(new ChangeParameterEvent() {

      @Override
      public void onChangeParameter(Object value) {
        view.addPointToGraph(MainFrame.DERIVATIVE_PART_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
      }
    });
    
    controller.onCalcError(new ChangeParameterEvent() {

      @Override
      public void onChangeParameter(Object value) {
        view.addPointToGraph(MainFrame.ERROR_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
      }
    });
    
    controller.onLockStatusChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        view.setLockStatus((boolean) value);
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
