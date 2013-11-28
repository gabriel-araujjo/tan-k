/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.main;

import Jama.Matrix;
import br.ufrn.dca.controle.QuanserClientException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import org.ejml.data.Complex64F;
import org.java.ayatana.ApplicationMenu;
import org.java.ayatana.AyatanaDesktop;
import org.jfree.data.time.Millisecond;
import tan.k.controller.Observer;
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
  private static Observer observer;

  public static void main(String[] args) {
    List<Complex64F> poles = new ArrayList<>();
    poles.add(new Complex64F(-0.1, 0));
    poles.add(new Complex64F(0.3, 0));
    
    double[][] a = {{-0.0065,0},{0.0065,-0.0065}};
    double[][] c = {{0,1}};
    
    double[][] h = {{0.0296}, {0}};
    double[][] g = {{0.9994, 0}, {0.0006, 0.9994}};
    
    
    //Observer observer = new Observer(new Matrix(g), new Matrix(c), poles);
    //Observer observer = new Observer(new Matrix(g), new Matrix(c), new Matrix(new double[][] {{1281.5339333333334 /*2.7834230769230777*/}, {1.7988 /*0.28700000000000003*/}}));
    observer = new Observer(new Matrix(g), new Matrix(c));
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    view = new MainFrame();
    view.setObserver(observer);
    tank = new Tank(view.getCurrentIp(), view.getCurrentPort(), 0, 0, 1);
    controller = new TanKController(
            tank,
            view.getCurrentLoop(),
            view.getCloseLoopSettings(),
            view.getOpenLoopSettings());
    controller.setObserver(observer);
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
    
    view.onCascadeChange(new ChangeParameterEvent() {

      @Override
      public void onChangeParameter(Object value) {
        System.out.println("setting cascade;");
        System.out.println("value = "+value.toString());
        controller.setCascade((boolean) value);
      }
    });
    
    view.onKp1Change(new ChangeParameterEvent() {

      @Override
      public void onChangeParameter(Object value) {
        controller.setKp1((double) value);
      }
    });
    
    view.onKi1Change(new ChangeParameterEvent() {

      @Override
      public void onChangeParameter(Object value) {
        controller.setKi1((double) value);
      }
    });
    
    view.onKd1Change(new ChangeParameterEvent() {

      @Override
      public void onChangeParameter(Object value) {
        controller.setKd1((double) value);
      }
    });
    
    view.onControllerTypeChange1(new ChangeParameterEvent() {
      @Override
      public void onChangeParameter(Object value) {
        System.out.println("controllerType1 =" + ((ControllerType) value).name());
        controller.setControllerType1((ControllerType) value);
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
            view.addPointToGraph(MainFrame.OBSERVER_CURVE_1, m, observer.get$L0());
            view.addPointToGraph(MainFrame.OBSERVER_CURVE_2, m, observer.get$L1());
            
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
