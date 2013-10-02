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
import org.jfree.data.time.Millisecond;
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
  private static Thread plotThread;

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
        tank.setIp((String) value);
      }
    });

    view.onPortChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("port = " + ((int) value));
        tank.setPort((int) value);
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
        view.setConnectionStatus(ConnectionStatus.CONNECTING);
        tank.connect();
        if(tank.isConnected() && !plotThread.isAlive()) plotThread.start();
        view.setConnectionStatus(tank.isConnected()?ConnectionStatus.CONNECTED: ConnectionStatus.DISCONNECTED);
      }
    });


    view.onWriteChange(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("writeChannel = " + ((int) value));
        tank.setVoltageChannel((int) value);
      }
    });

//    controller.onWriteVoltage(new ChangeParameterEvent() {
//      @Override
//      public void onChangeParameter(Object value) {
//        view.addPointToGraph(MainFrame.SENDED_SIGNAL_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
//        view.addPointToGraph(MainFrame.CALCULATED_SIGNAL_CURVE, ((double []) value)[0], controller.getCalculatedVoltage());
//        view.addPointToGraph(MainFrame.SETPOINT_CURVE, ((double []) value)[0], controller.getSetPoint());
//      }
//    });

//    controller.onReadLevelTank1(new ChangeParameterEvent() {
//      @Override
//      public void onChangeParameter(Object value) {
//        view.addPointToGraph(MainFrame.TANK1_LEVEL_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
//      }
//    });

//    controller.onReadLevelTank2(new ChangeParameterEvent() {
//      @Override
//      public void onChangeParameter(Object value) {
//        view.addPointToGraph(MainFrame.TANK2_LEVEL_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
//      }
//    });
    
//    controller.onCalcP(new ChangeParameterEvent() {
//
//      @Override
//      public void onChangeParameter(Object value) {
//        view.addPointToGraph(MainFrame.PROPORCIONAL_PART_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
//      }
//    });
    
//    controller.onCalcI(new ChangeParameterEvent() {
//
//      @Override
//      public void onChangeParameter(Object value) {
//        view.addPointToGraph(MainFrame.INTEGRAL_PART_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
//      }
//    });
    
//    controller.onCalcD(new ChangeParameterEvent() {
//
//      @Override
//      public void onChangeParameter(Object value) {
//        view.addPointToGraph(MainFrame.DERIVATIVE_PART_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
//      }
//    });
    
//    controller.onCalcError(new ChangeParameterEvent() {
//
//      @Override
//      public void onChangeParameter(Object value) {
//        view.addPointToGraph(MainFrame.ERROR_CURVE, ((double[]) value)[0], ((double[]) value)[1]);
//      }
//    });
    
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
    plotThread = new Thread(new Runnable() {
      long nextTime;
      @Override
      public void run() {
        nextTime = 0L;
        Millisecond m;
        while(true){
          if(System.currentTimeMillis() > nextTime){
            nextTime = System.currentTimeMillis() + 100;
            m = new Millisecond();
            if(view.isNivel1()) view.addPointToGraph(MainFrame.TANK1_LEVEL_CURVE, m, tank.getLevel1());
            if(view.isNivel2()) view.addPointToGraph(MainFrame.TANK2_LEVEL_CURVE, m, tank.getLevel2());
            if(view.isErro()) view.addPointToGraph(MainFrame.ERROR_CURVE, m, controller.getE());
            if(view.isSetpoint()) view.addPointToGraph(MainFrame.SETPOINT_CURVE, m, controller.getSetPoint());
            
            if(view.isU_bar()) view.addPointToGraph(MainFrame.SENDED_SIGNAL_CURVE, m, controller.getCurrentVoltage());
            if(view.isU()) view.addPointToGraph(MainFrame.CALCULATED_SIGNAL_CURVE, m, controller.getCalculatedVoltage());
            if(view.isP()) view.addPointToGraph(MainFrame.PROPORCIONAL_PART_CURVE, m, controller.getP());
            if(view.isI()) view.addPointToGraph(MainFrame.INTEGRAL_PART_CURVE, m, controller.getI());
            if(view.isD()) view.addPointToGraph(MainFrame.DERIVATIVE_PART_CURVE, m, controller.getD());
            
            if(controller.isMpCalculated()){
              view.setMP(controller.getMp());
              view.setPeakTime(controller.getPeakTime());
            } 
            if(controller.isSteady5percentCalculated()){
              view.setSettlingTime(controller.getSettlingTime());
            }
          }
        }
      }
    });
  }
}
