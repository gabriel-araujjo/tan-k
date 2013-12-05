/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k.view;

import Jama.Matrix;
import java.awt.Dimension;
import java.io.Console;
import java.text.DecimalFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.ejml.data.Complex64F;
import org.jfree.data.time.Millisecond;
import tan.k.controller.Observer;
import tan.k.controller.TanKController.CloseLoopSettings;
import tan.k.controller.TanKController.ControllerType;
import tan.k.controller.TanKController.Loop;
import tan.k.controller.TanKController.OpenLoopSettings;
import tan.k.controller.TanKController.TankTag;
import tan.k.controller.TanKController.Wave;

/**
 *
 * @author gabriel,ricardo
 */
public class MainFrame extends javax.swing.JFrame {

  //Current values
  //   openLoop
  private double currentAmplitide;
  private double currentPeriod;
  private Wave currentWave;
  private JToggleButton currentWaveButton; /*Aux*/

  //   closeLoop
  private double currentSetPoint;
  private TankTag currentPV;
  private ControllerType currentControllerType;
  private ControllerType currentControllerType1;
  private double currentKp, currentKi, currentKd;
  private Loop currentLoop;
  private boolean cascade;
  //   tank settings
  private String currentIp;
  private int currentPort;
  private int currentWriteChannel;
  //Events
  private ChangeParameterEvent setPointChange;
  private ChangeParameterEvent processVariableChange;
  private ChangeParameterEvent amplitudeChange;
  private ChangeParameterEvent periodChange;
  private ChangeParameterEvent waveChange;
  private ChangeParameterEvent loopChange;
  private ChangeParameterEvent ipChange;
  private ChangeParameterEvent portChange;
  private ChangeParameterEvent writeChange;
  private ChangeParameterEvent applyClicked;
  private ChangeParameterEvent connectClicked;
  private DefaultComboBoxModel<ComboItem> pvItems;
  private List<String> pvItemsString;
  private List<String> enabledCurves;
  private ChangeParameterEvent stopClicked;
  private ChangeParameterEvent controllerTypeChange;
  private ChangeParameterEvent kpChange;
  private ChangeParameterEvent kiChange;
  private ChangeParameterEvent kdChange;
  private double currentKp1;
  private ChangeParameterEvent kp1Change;
  private double currentKi1;
  private ChangeParameterEvent ki1Change;
  private double currentKd1;
  private ChangeParameterEvent kd1Change;
  private ChangeParameterEvent cascadeChange;
  private ChangeParameterEvent controllerTypeChange1;
  private Matrix A;
  private Matrix C;
  private Observer observer;
  private double currentTak;
  private ChangeParameterEvent TakChange;

  /**
   * Creates new form MainFrame
   */
  public MainFrame() {

    this.currentAmplitide = 0;
    this.currentPeriod = 1;
    this.currentWave = Wave.STEP;
    this.currentSetPoint = 0;
    this.currentPV = TankTag.TANK_1;
    this.currentLoop = Loop.OPENED;
    //this.currentIp = "127.0.0.1";
    this.currentIp = "10.13.99.69";
    
    double[][] a = {{-0.0065,0},{0.0065,-0.0065}};
    double[][] c = {{0,1}};
    
    this.A = new Matrix(a);
    this.C = new Matrix(c);
    
    this.currentPort = 20081;
    this.currentWriteChannel = 0;
    this.enabledCurves = new ArrayList<>();
    enabledCurves.addAll(Arrays.asList(new String[]{
      TANK1_LEVEL_CURVE, 
      TANK2_LEVEL_CURVE, 
      ERROR_CURVE,
      SETPOINT_CURVE,
      SENDED_SIGNAL_CURVE,
      CALCULATED_SIGNAL_CURVE,
      OBSERVER_CURVE_2,
      OBSERVER_CURVE_1,
      ERROR_OBSERVER_CURVE_1,
      ERROR_OBSERVER_CURVE_2
    }));
//    
//  public static String TANK1_LEVEL_CURVE = "Nível do tanque 1";
//  public static String TANK2_LEVEL_CURVE = "Nível do tanque 2";
//  public static String ERROR_CURVE = "Erro";
//  public static String SETPOINT_CURVE = "Set Point";
//  
//  public static String SENDED_SIGNAL_CURVE = "Sinal Enviado";
//  public static String CALCULATED_SIGNAL_CURVE = "Sinal calculado";
//  
//  public static String PROPORCIONAL_PART_CURVE = "Componente proporcional";
//  public static String INTEGRAL_PART_CURVE = "Componente integral";
//  public static String DERIVATIVE_PART_CURVE = "Componente derivativa";
    initComponents();

    graphPanel3.disableLegend();
    graphPanel3.addSerie("Sinal para configuracao");
    
    this.graphTitle.setVisible(true);
    //this.graphToggler.setVisible(true);
    this.graphBox.setVisible(true);
    
    cascade = false;
    parameters2loop.setVisible(false);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    writeChannelChooser = new javax.swing.ButtonGroup();
    loopTypeChooser = new javax.swing.ButtonGroup();
    waveTypeChooser = new javax.swing.ButtonGroup();
    buttonGroup1 = new javax.swing.ButtonGroup();
    graphPanel1 = new tan.k.view.GraphPanel();
    graphPanel2 = new tan.k.view.GraphPanel();
    statusConnectedLabel = new javax.swing.JLabel();
    statusLockLabel = new javax.swing.JLabel();
    scrollPane1 = new java.awt.ScrollPane();
    sidebar = new javax.swing.JPanel();
    readTitle = new javax.swing.JPanel();
    readToggler = new javax.swing.JLabel();
    readBox = new javax.swing.JPanel();
    inputTable = new javax.swing.JPanel();
    inputColumn0 = new javax.swing.JPanel();
    input0 = new javax.swing.JCheckBox();
    input1 = new javax.swing.JCheckBox();
    inputColumn1 = new javax.swing.JPanel();
    input2 = new javax.swing.JCheckBox();
    input3 = new javax.swing.JCheckBox();
    inputColumn2 = new javax.swing.JPanel();
    input4 = new javax.swing.JCheckBox();
    input5 = new javax.swing.JCheckBox();
    inputColumn3 = new javax.swing.JPanel();
    input6 = new javax.swing.JCheckBox();
    input7 = new javax.swing.JCheckBox();
    writeTitle = new javax.swing.JPanel();
    writeToggler = new javax.swing.JLabel();
    graphTitle = new javax.swing.JPanel();
    graphToggler = new javax.swing.JLabel();
    graphBox = new javax.swing.JPanel();
    inputTable1 = new javax.swing.JPanel();
    inputColumn4 = new javax.swing.JPanel();
    uCheckbox = new javax.swing.JCheckBox();
    u_barCheckbox = new javax.swing.JCheckBox();
    inputColumn5 = new javax.swing.JPanel();
    errorCheckbox = new javax.swing.JCheckBox();
    SPCheckbox = new javax.swing.JCheckBox();
    inputColumn6 = new javax.swing.JPanel();
    level1Checkbox = new javax.swing.JCheckBox();
    level2Checkbox = new javax.swing.JCheckBox();
    inputColumn7 = new javax.swing.JPanel();
    PCheckbox = new javax.swing.JCheckBox();
    ICheckbox = new javax.swing.JCheckBox();
    inputColumn11 = new javax.swing.JPanel();
    DCheckbox = new javax.swing.JCheckBox();
    OCheckbox = new javax.swing.JCheckBox();
    writeBox = new javax.swing.JPanel();
    outputTable = new javax.swing.JPanel();
    outputColumn0 = new javax.swing.JPanel();
    output0 = new javax.swing.JRadioButton();
    output1 = new javax.swing.JRadioButton();
    outputColumn1 = new javax.swing.JPanel();
    output2 = new javax.swing.JRadioButton();
    output3 = new javax.swing.JRadioButton();
    outputColumn2 = new javax.swing.JPanel();
    output4 = new javax.swing.JRadioButton();
    output5 = new javax.swing.JRadioButton();
    outputColumn3 = new javax.swing.JPanel();
    output6 = new javax.swing.JRadioButton();
    output7 = new javax.swing.JRadioButton();
    jSeparator1 = new javax.swing.JSeparator();
    jSeparator2 = new javax.swing.JSeparator();
    openedLoopSettings = new javax.swing.JPanel();
    waveType = new javax.swing.JPanel();
    sinus = new javax.swing.JToggleButton();
    sawtooth = new javax.swing.JToggleButton();
    square = new javax.swing.JToggleButton();
    step = new javax.swing.JToggleButton();
    random = new javax.swing.JToggleButton();
    outputSettings = new javax.swing.JPanel();
    waveParams = new javax.swing.JPanel();
    amplitude = new javax.swing.JPanel();
    amplitudeLabel = new javax.swing.JLabel();
    amplitudeField = new javax.swing.JFormattedTextField();
    jLabelVoltage = new javax.swing.JLabel();
    period = new javax.swing.JPanel();
    periodLabel = new javax.swing.JLabel();
    periodField = new javax.swing.JFormattedTextField();
    jLabelSeconds = new javax.swing.JLabel();
    frequency = new javax.swing.JPanel();
    frequencyLabel = new javax.swing.JLabel();
    frequencyField = new javax.swing.JFormattedTextField();
    jLabelHertz = new javax.swing.JLabel();
    graphPanel3 = new tan.k.view.GraphPanel();
    outputPreview = new java.awt.Canvas();
    closedLoopSettings = new javax.swing.JPanel();
    controlSettings = new javax.swing.JPanel();
    waveParams1 = new javax.swing.JPanel();
    setPoint = new javax.swing.JPanel();
    setPointLabel = new javax.swing.JLabel();
    setPointField = new javax.swing.JFormattedTextField();
    jLabelVoltage1 = new javax.swing.JLabel();
    processVariableLabel = new javax.swing.JLabel();
    processVariableField = new javax.swing.JComboBox();
    jSeparator3 = new javax.swing.JSeparator();
    controllerTypeLabel = new javax.swing.JLabel();
    controllerTypeCombo = new javax.swing.JComboBox();
    kpLabel = new javax.swing.JLabel();
    kpField = new javax.swing.JTextField();
    tauiLabel = new javax.swing.JLabel();
    tauiField = new javax.swing.JTextField();
    kiLabel = new javax.swing.JLabel();
    kiField = new javax.swing.JTextField();
    taudLabel = new javax.swing.JLabel();
    taudField = new javax.swing.JTextField();
    kdLabel = new javax.swing.JLabel();
    kdField = new javax.swing.JTextField();
    parameters2loop = new javax.swing.JPanel();
    taudField1 = new javax.swing.JTextField();
    controllerTypeCombo1 = new javax.swing.JComboBox();
    kpField1 = new javax.swing.JTextField();
    taudLabel1 = new javax.swing.JLabel();
    tauiField1 = new javax.swing.JTextField();
    kdField1 = new javax.swing.JTextField();
    kiLabel1 = new javax.swing.JLabel();
    kiField1 = new javax.swing.JTextField();
    tauiLabel1 = new javax.swing.JLabel();
    controllerTypeLabel1 = new javax.swing.JLabel();
    kpLabel1 = new javax.swing.JLabel();
    kdLabel1 = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    simpleButton = new javax.swing.JRadioButton();
    cascadeButton = new javax.swing.JRadioButton();
    takCheck = new javax.swing.JCheckBox();
    kdLabel2 = new javax.swing.JLabel();
    takField = new javax.swing.JTextField();
    apply = new javax.swing.JButton();
    jButtonPreview = new javax.swing.JButton();
    loopChooser = new javax.swing.JPanel();
    openedLoop = new javax.swing.JRadioButton();
    closedLoop = new javax.swing.JRadioButton();
    jButtonStop = new javax.swing.JButton();
    settingTimeLabel = new javax.swing.JLabel();
    settlingTimeField = new javax.swing.JLabel();
    PeakTimeLavel = new javax.swing.JLabel();
    PeakTimeField = new javax.swing.JLabel();
    mpLabel = new javax.swing.JLabel();
    mpField = new javax.swing.JLabel();
    riseTimeLabel = new javax.swing.JLabel();
    riseTimeField = new javax.swing.JLabel();
    ipPortTitle = new javax.swing.JPanel();
    ipPortToggler = new javax.swing.JLabel();
    ipPortBox = new javax.swing.JPanel();
    ipPortWrapper = new javax.swing.JPanel();
    ipPortLabel = new javax.swing.JLabel();
    ipPortField = new javax.swing.JFormattedTextField();
    connect = new javax.swing.JButton();
    observerTitle = new javax.swing.JPanel();
    observerToggler = new javax.swing.JLabel();
    observerBox = new javax.swing.JPanel();
    polo2Label = new javax.swing.JLabel();
    polo2Field = new javax.swing.JTextField();
    polo1Field = new javax.swing.JTextField();
    polo1Label = new javax.swing.JLabel();
    jTableMatrizGanho = new javax.swing.JTable();
    jLabelCochete1 = new javax.swing.JLabel();
    jLabelCochete2 = new javax.swing.JLabel();
    applyObserver = new javax.swing.JButton();
    display1Title = new javax.swing.JLabel();
    display1Level = new javax.swing.JLabel();
    display2Level = new javax.swing.JLabel();
    display2Title = new javax.swing.JLabel();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenu2 = new javax.swing.JMenu();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Tank");
    setMinimumSize(new java.awt.Dimension(1300, 720));

    graphPanel1.setPanelBoundaries(-35, 35);
    graphPanel1.setyAxisLabel("Altura(cm)");
    graphPanel1.setxAxisLabel("Tempo(s)");

    graphPanel2.setPanelBoundaries(-4, 4);
    graphPanel2.setyAxisLabel("Volts(V)");
    graphPanel2.setxAxisLabel("Tempo(s)");

    statusConnectedLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/ball_red.png"))); // NOI18N
    statusConnectedLabel.setText("Não Conectado");

    statusLockLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/ball_stroke.png"))); // NOI18N
    statusLockLabel.setText("Trava");

    sidebar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    sidebar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    sidebar.setMinimumSize(new java.awt.Dimension(260, 100));
    sidebar.setBackground(java.awt.SystemColor.control);

    readTitle.setBackground(new java.awt.Color(158, 158, 158));

    readToggler.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
    readToggler.setText("Leitura");
    readToggler.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        readTogglerMouseClicked(evt);
      }
    });

    javax.swing.GroupLayout readTitleLayout = new javax.swing.GroupLayout(readTitle);
    readTitle.setLayout(readTitleLayout);
    readTitleLayout.setHorizontalGroup(
      readTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(readTitleLayout.createSequentialGroup()
        .addComponent(readToggler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    readTitleLayout.setVerticalGroup(
      readTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(readToggler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    inputColumn0.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn0.setPreferredSize(new java.awt.Dimension(65, 50));

    input0.setText("A0");
    input0.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        updatePVSelector(evt);
      }
    });

    input1.setText("A1");
    input1.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        updatePVSelector(evt);
      }
    });

    javax.swing.GroupLayout inputColumn0Layout = new javax.swing.GroupLayout(inputColumn0);
    inputColumn0.setLayout(inputColumn0Layout);
    inputColumn0Layout.setHorizontalGroup(
      inputColumn0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn0Layout.createSequentialGroup()
        .addGroup(inputColumn0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(input0)
          .addComponent(input1))
        .addGap(0, 23, Short.MAX_VALUE))
    );
    inputColumn0Layout.setVerticalGroup(
      inputColumn0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn0Layout.createSequentialGroup()
        .addComponent(input0)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(input1)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    inputColumn1.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn1.setPreferredSize(new java.awt.Dimension(65, 50));

    input2.setText("A2");
    input2.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        updatePVSelector(evt);
      }
    });

    input3.setText("A3");
    input3.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        updatePVSelector(evt);
      }
    });

    javax.swing.GroupLayout inputColumn1Layout = new javax.swing.GroupLayout(inputColumn1);
    inputColumn1.setLayout(inputColumn1Layout);
    inputColumn1Layout.setHorizontalGroup(
      inputColumn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn1Layout.createSequentialGroup()
        .addGroup(inputColumn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(input2)
          .addComponent(input3))
        .addGap(0, 23, Short.MAX_VALUE))
    );
    inputColumn1Layout.setVerticalGroup(
      inputColumn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn1Layout.createSequentialGroup()
        .addComponent(input2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(input3)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    inputColumn2.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn2.setPreferredSize(new java.awt.Dimension(65, 50));

    input4.setText("A4");
    input4.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        updatePVSelector(evt);
      }
    });

    input5.setText("A5");
    input5.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        updatePVSelector(evt);
      }
    });

    javax.swing.GroupLayout inputColumn2Layout = new javax.swing.GroupLayout(inputColumn2);
    inputColumn2.setLayout(inputColumn2Layout);
    inputColumn2Layout.setHorizontalGroup(
      inputColumn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn2Layout.createSequentialGroup()
        .addGroup(inputColumn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(input4)
          .addComponent(input5))
        .addGap(0, 23, Short.MAX_VALUE))
    );
    inputColumn2Layout.setVerticalGroup(
      inputColumn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn2Layout.createSequentialGroup()
        .addComponent(input4)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(input5)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    inputColumn3.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn3.setPreferredSize(new java.awt.Dimension(65, 50));

    input6.setText("A6");
    input6.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        updatePVSelector(evt);
      }
    });

    input7.setText("A7");
    input7.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        updatePVSelector(evt);
      }
    });

    javax.swing.GroupLayout inputColumn3Layout = new javax.swing.GroupLayout(inputColumn3);
    inputColumn3.setLayout(inputColumn3Layout);
    inputColumn3Layout.setHorizontalGroup(
      inputColumn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn3Layout.createSequentialGroup()
        .addGroup(inputColumn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(input6)
          .addComponent(input7))
        .addGap(0, 23, Short.MAX_VALUE))
    );
    inputColumn3Layout.setVerticalGroup(
      inputColumn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn3Layout.createSequentialGroup()
        .addComponent(input6)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(input7)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout inputTableLayout = new javax.swing.GroupLayout(inputTable);
    inputTable.setLayout(inputTableLayout);
    inputTableLayout.setHorizontalGroup(
      inputTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputTableLayout.createSequentialGroup()
        .addComponent(inputColumn0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(inputColumn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(inputColumn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(inputColumn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    inputTableLayout.setVerticalGroup(
      inputTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(inputColumn0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addComponent(inputColumn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addComponent(inputColumn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addComponent(inputColumn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    javax.swing.GroupLayout readBoxLayout = new javax.swing.GroupLayout(readBox);
    readBox.setLayout(readBoxLayout);
    readBoxLayout.setHorizontalGroup(
      readBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(readBoxLayout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(inputTable, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    readBoxLayout.setVerticalGroup(
      readBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, readBoxLayout.createSequentialGroup()
        .addComponent(inputTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(6, 6, 6))
    );

    writeTitle.setBackground(new java.awt.Color(158, 158, 158));

    writeToggler.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
    writeToggler.setText("Escrita");
    writeToggler.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        writeTogglerMouseClicked(evt);
      }
    });

    javax.swing.GroupLayout writeTitleLayout = new javax.swing.GroupLayout(writeTitle);
    writeTitle.setLayout(writeTitleLayout);
    writeTitleLayout.setHorizontalGroup(
      writeTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(writeToggler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    writeTitleLayout.setVerticalGroup(
      writeTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(writeToggler)
    );

    graphTitle.setBackground(new java.awt.Color(158, 158, 158));

    graphToggler.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
    graphToggler.setText("Gráficos");
    graphToggler.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        graphTogglerMouseClicked(evt);
      }
    });

    javax.swing.GroupLayout graphTitleLayout = new javax.swing.GroupLayout(graphTitle);
    graphTitle.setLayout(graphTitleLayout);
    graphTitleLayout.setHorizontalGroup(
      graphTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(graphToggler, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
    );
    graphTitleLayout.setVerticalGroup(
      graphTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(graphToggler, javax.swing.GroupLayout.Alignment.TRAILING)
    );

    inputColumn4.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn4.setPreferredSize(new java.awt.Dimension(65, 50));

    uCheckbox.setText("U");
    uCheckbox.setSelected(true);
    uCheckbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        uCheckboxItemStateChanged(evt);
      }
    });

    u_barCheckbox.setText("Ū");
    u_barCheckbox.setSelected(true);
    u_barCheckbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        u_barCheckboxItemStateChanged(evt);
      }
    });

    javax.swing.GroupLayout inputColumn4Layout = new javax.swing.GroupLayout(inputColumn4);
    inputColumn4.setLayout(inputColumn4Layout);
    inputColumn4Layout.setHorizontalGroup(
      inputColumn4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn4Layout.createSequentialGroup()
        .addGroup(inputColumn4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(uCheckbox)
          .addComponent(u_barCheckbox))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    inputColumn4Layout.setVerticalGroup(
      inputColumn4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn4Layout.createSequentialGroup()
        .addComponent(uCheckbox)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(u_barCheckbox)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    inputColumn5.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn5.setPreferredSize(new java.awt.Dimension(65, 50));

    errorCheckbox.setText("Erro");
    errorCheckbox.setEnabled(false);
    errorCheckbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        errorCheckboxItemStateChanged(evt);
      }
    });

    SPCheckbox.setText("SP");
    SPCheckbox.setEnabled(false);
    SPCheckbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        SPCheckboxItemStateChanged(evt);
      }
    });

    javax.swing.GroupLayout inputColumn5Layout = new javax.swing.GroupLayout(inputColumn5);
    inputColumn5.setLayout(inputColumn5Layout);
    inputColumn5Layout.setHorizontalGroup(
      inputColumn5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn5Layout.createSequentialGroup()
        .addGroup(inputColumn5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(errorCheckbox)
          .addComponent(SPCheckbox))
        .addGap(0, 0, 0))
    );
    inputColumn5Layout.setVerticalGroup(
      inputColumn5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn5Layout.createSequentialGroup()
        .addComponent(errorCheckbox)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(SPCheckbox)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    inputColumn6.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn6.setPreferredSize(new java.awt.Dimension(65, 50));

    level1Checkbox.setText("Nível 1");
    level1Checkbox.setSelected(true);
    level1Checkbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        level1CheckboxItemStateChanged(evt);
      }
    });

    level2Checkbox.setText("Nível 2");
    level2Checkbox.setSelected(true);
    level2Checkbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        level2CheckboxItemStateChanged(evt);
      }
    });

    javax.swing.GroupLayout inputColumn6Layout = new javax.swing.GroupLayout(inputColumn6);
    inputColumn6.setLayout(inputColumn6Layout);
    inputColumn6Layout.setHorizontalGroup(
      inputColumn6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn6Layout.createSequentialGroup()
        .addGroup(inputColumn6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(level1Checkbox)
          .addComponent(level2Checkbox))
        .addGap(0, 24, Short.MAX_VALUE))
    );
    inputColumn6Layout.setVerticalGroup(
      inputColumn6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn6Layout.createSequentialGroup()
        .addComponent(level1Checkbox)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(level2Checkbox)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    inputColumn7.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn7.setPreferredSize(new java.awt.Dimension(65, 50));

    PCheckbox.setText("P");
    PCheckbox.setEnabled(false);
    PCheckbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        PCheckboxItemStateChanged(evt);
      }
    });

    ICheckbox.setText("I");
    ICheckbox.setEnabled(false);
    ICheckbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        ICheckboxItemStateChanged(evt);
      }
    });

    javax.swing.GroupLayout inputColumn7Layout = new javax.swing.GroupLayout(inputColumn7);
    inputColumn7.setLayout(inputColumn7Layout);
    inputColumn7Layout.setHorizontalGroup(
      inputColumn7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn7Layout.createSequentialGroup()
        .addGroup(inputColumn7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(PCheckbox)
          .addComponent(ICheckbox))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    inputColumn7Layout.setVerticalGroup(
      inputColumn7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn7Layout.createSequentialGroup()
        .addComponent(PCheckbox)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(ICheckbox)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    inputColumn11.setMinimumSize(new java.awt.Dimension(65, 100));
    inputColumn11.setPreferredSize(new java.awt.Dimension(65, 50));

    DCheckbox.setText(" D");
    DCheckbox.setEnabled(false);
    DCheckbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        DCheckboxItemStateChanged(evt);
      }
    });

    OCheckbox.setText("O");
    DCheckbox.setEnabled(false);
    OCheckbox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        OCheckboxItemStateChanged(evt);
      }
    });
    OCheckbox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        OCheckboxActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout inputColumn11Layout = new javax.swing.GroupLayout(inputColumn11);
    inputColumn11.setLayout(inputColumn11Layout);
    inputColumn11Layout.setHorizontalGroup(
      inputColumn11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn11Layout.createSequentialGroup()
        .addGroup(inputColumn11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(DCheckbox)
          .addComponent(OCheckbox))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    inputColumn11Layout.setVerticalGroup(
      inputColumn11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputColumn11Layout.createSequentialGroup()
        .addComponent(DCheckbox)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(OCheckbox)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout inputTable1Layout = new javax.swing.GroupLayout(inputTable1);
    inputTable1.setLayout(inputTable1Layout);
    inputTable1Layout.setHorizontalGroup(
      inputTable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputTable1Layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(inputColumn4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(inputColumn5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(inputColumn6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(6, 6, 6)
        .addComponent(inputColumn7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(inputColumn11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(46, 46, 46))
    );
    inputTable1Layout.setVerticalGroup(
      inputTable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputTable1Layout.createSequentialGroup()
        .addGroup(inputTable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(inputColumn4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(inputColumn5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(inputColumn6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(inputColumn7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(inputColumn11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );

    javax.swing.GroupLayout graphBoxLayout = new javax.swing.GroupLayout(graphBox);
    graphBox.setLayout(graphBoxLayout);
    graphBoxLayout.setHorizontalGroup(
      graphBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(graphBoxLayout.createSequentialGroup()
        .addComponent(inputTable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );
    graphBoxLayout.setVerticalGroup(
      graphBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(inputTable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    writeBox.setPreferredSize(new Dimension(100,100));
    writeBox.setPreferredSize(new java.awt.Dimension(357, 600));

    outputColumn0.setMinimumSize(new java.awt.Dimension(65, 100));
    outputColumn0.setPreferredSize(new java.awt.Dimension(65, 50));

    writeChannelChooser.add(output0);
    output0.setText("A0");

    writeChannelChooser.add(output1);
    output1.setText("A1");

    javax.swing.GroupLayout outputColumn0Layout = new javax.swing.GroupLayout(outputColumn0);
    outputColumn0.setLayout(outputColumn0Layout);
    outputColumn0Layout.setHorizontalGroup(
      outputColumn0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputColumn0Layout.createSequentialGroup()
        .addGroup(outputColumn0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(output0)
          .addComponent(output1))
        .addGap(0, 23, Short.MAX_VALUE))
    );
    outputColumn0Layout.setVerticalGroup(
      outputColumn0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputColumn0Layout.createSequentialGroup()
        .addComponent(output0)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(output1)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    outputColumn1.setMinimumSize(new java.awt.Dimension(65, 100));
    outputColumn1.setPreferredSize(new java.awt.Dimension(65, 50));

    writeChannelChooser.add(output2);
    output2.setText("A2");

    writeChannelChooser.add(output3);
    output3.setText("A3");

    javax.swing.GroupLayout outputColumn1Layout = new javax.swing.GroupLayout(outputColumn1);
    outputColumn1.setLayout(outputColumn1Layout);
    outputColumn1Layout.setHorizontalGroup(
      outputColumn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputColumn1Layout.createSequentialGroup()
        .addGroup(outputColumn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(output2)
          .addComponent(output3))
        .addGap(0, 23, Short.MAX_VALUE))
    );
    outputColumn1Layout.setVerticalGroup(
      outputColumn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputColumn1Layout.createSequentialGroup()
        .addComponent(output2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(output3)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    outputColumn2.setMinimumSize(new java.awt.Dimension(65, 100));
    outputColumn2.setPreferredSize(new java.awt.Dimension(65, 50));

    writeChannelChooser.add(output4);
    output4.setText("A4");

    writeChannelChooser.add(output5);
    output5.setText("A5");

    javax.swing.GroupLayout outputColumn2Layout = new javax.swing.GroupLayout(outputColumn2);
    outputColumn2.setLayout(outputColumn2Layout);
    outputColumn2Layout.setHorizontalGroup(
      outputColumn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputColumn2Layout.createSequentialGroup()
        .addGroup(outputColumn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(output4)
          .addComponent(output5))
        .addGap(0, 23, Short.MAX_VALUE))
    );
    outputColumn2Layout.setVerticalGroup(
      outputColumn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputColumn2Layout.createSequentialGroup()
        .addComponent(output4)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(output5)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    outputColumn3.setMinimumSize(new java.awt.Dimension(65, 100));
    outputColumn3.setPreferredSize(new java.awt.Dimension(65, 50));

    writeChannelChooser.add(output6);
    output6.setText("A6");

    writeChannelChooser.add(output7);
    output7.setText("A7");

    javax.swing.GroupLayout outputColumn3Layout = new javax.swing.GroupLayout(outputColumn3);
    outputColumn3.setLayout(outputColumn3Layout);
    outputColumn3Layout.setHorizontalGroup(
      outputColumn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputColumn3Layout.createSequentialGroup()
        .addGroup(outputColumn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(output6)
          .addComponent(output7))
        .addGap(0, 23, Short.MAX_VALUE))
    );
    outputColumn3Layout.setVerticalGroup(
      outputColumn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputColumn3Layout.createSequentialGroup()
        .addComponent(output6)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(output7)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout outputTableLayout = new javax.swing.GroupLayout(outputTable);
    outputTable.setLayout(outputTableLayout);
    outputTableLayout.setHorizontalGroup(
      outputTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputTableLayout.createSequentialGroup()
        .addComponent(outputColumn0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(outputColumn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(outputColumn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(outputColumn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(43, Short.MAX_VALUE))
    );
    outputTableLayout.setVerticalGroup(
      outputTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(outputColumn0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addComponent(outputColumn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addComponent(outputColumn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addComponent(outputColumn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    //waveType.setAlignmentX(Component.CENTER_ALIGNMENT);

    sinus.setToolTipText("Senoidal");
    sinus.setSelected(true);
    currentWave = Wave.SINUSOID;
    waveTypeChooser.add(sinus);
    sinus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/sinus.png"))); // NOI18N
    sinus.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        sinusItemStateChanged(evt);
      }
    });

    sawtooth.setToolTipText("Dente de serra");
    waveTypeChooser.add(sawtooth);
    sawtooth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/sawtooth.png"))); // NOI18N
    sawtooth.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        sawtoothItemStateChanged(evt);
      }
    });

    square.setToolTipText("Quadrada");
    waveTypeChooser.add(square);
    square.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/square.png"))); // NOI18N
    square.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        squareItemStateChanged(evt);
      }
    });

    step.setToolTipText("Degrau");
    waveTypeChooser.add(step);
    step.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/step.png"))); // NOI18N
    step.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        stepItemStateChanged(evt);
      }
    });

    random.setToolTipText("Aleatória");
    waveTypeChooser.add(random);
    random.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/random.png"))); // NOI18N
    random.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        randomItemStateChanged(evt);
      }
    });

    javax.swing.GroupLayout waveTypeLayout = new javax.swing.GroupLayout(waveType);
    waveType.setLayout(waveTypeLayout);
    waveTypeLayout.setHorizontalGroup(
      waveTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(waveTypeLayout.createSequentialGroup()
        .addGap(0, 67, Short.MAX_VALUE)
        .addComponent(sinus)
        .addGap(0, 0, 0)
        .addComponent(sawtooth)
        .addGap(0, 0, 0)
        .addComponent(square)
        .addGap(0, 0, 0)
        .addComponent(step)
        .addGap(0, 0, 0)
        .addComponent(random)
        .addGap(0, 37, Short.MAX_VALUE))
    );
    waveTypeLayout.setVerticalGroup(
      waveTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(waveTypeLayout.createSequentialGroup()
        .addGroup(waveTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(random)
          .addComponent(step)
          .addComponent(square)
          .addGroup(waveTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sinus)
            .addComponent(sawtooth)))
        .addGap(0, 0, 0))
    );

    waveParams.setMinimumSize(new java.awt.Dimension(169, 96));

    amplitude.setPreferredSize(new java.awt.Dimension(199, 28));

    amplitudeLabel.setText("Amplitude");

    ((AbstractDocument)amplitudeField.getDocument()).setDocumentFilter(new DoubleFilter());

    jLabelVoltage.setText("V");

    javax.swing.GroupLayout amplitudeLayout = new javax.swing.GroupLayout(amplitude);
    amplitude.setLayout(amplitudeLayout);
    amplitudeLayout.setHorizontalGroup(
      amplitudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(amplitudeLayout.createSequentialGroup()
        .addGap(0, 0, 0)
        .addComponent(amplitudeLabel)
        .addGap(25, 25, 25)
        .addComponent(amplitudeField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(6, 6, 6)
        .addComponent(jLabelVoltage)
        .addGap(0, 0, 0))
    );
    amplitudeLayout.setVerticalGroup(
      amplitudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(amplitudeLayout.createSequentialGroup()
        .addGroup(amplitudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(amplitudeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(amplitudeLabel)
          .addComponent(jLabelVoltage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(0, 0, Short.MAX_VALUE))
    );

    period.setPreferredSize(new java.awt.Dimension(199, 28));

    periodLabel.setText("Período");

    ((AbstractDocument)periodField.getDocument()).setDocumentFilter(new DoubleFilter());
    periodField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        periodFieldKeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        periodFieldKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        periodFieldKeyPressed(evt);
      }
    });

    jLabelSeconds.setText("s");

    javax.swing.GroupLayout periodLayout = new javax.swing.GroupLayout(period);
    period.setLayout(periodLayout);
    periodLayout.setHorizontalGroup(
      periodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(periodLayout.createSequentialGroup()
        .addGap(0, 0, 0)
        .addComponent(periodLabel)
        .addGap(42, 42, 42)
        .addComponent(periodField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(6, 6, 6)
        .addComponent(jLabelSeconds, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0))
    );
    periodLayout.setVerticalGroup(
      periodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(periodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(periodLabel)
        .addComponent(periodField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(jLabelSeconds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    frequencyLabel.setText("Frequência");

    ((AbstractDocument)frequencyField.getDocument()).setDocumentFilter(new DoubleFilter());
    frequencyField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        frequencyFieldKeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        frequencyFieldKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        frequencyFieldKeyPressed(evt);
      }
    });

    jLabelHertz.setText("Hz");

    javax.swing.GroupLayout frequencyLayout = new javax.swing.GroupLayout(frequency);
    frequency.setLayout(frequencyLayout);
    frequencyLayout.setHorizontalGroup(
      frequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(frequencyLayout.createSequentialGroup()
        .addComponent(frequencyLabel)
        .addGap(18, 18, 18)
        .addComponent(frequencyField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(6, 6, 6)
        .addComponent(jLabelHertz)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    frequencyLayout.setVerticalGroup(
      frequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(frequencyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(frequencyLabel)
        .addComponent(jLabelHertz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    graphPanel3.setPanelBoundaries(4, 4);

    javax.swing.GroupLayout waveParamsLayout = new javax.swing.GroupLayout(waveParams);
    waveParams.setLayout(waveParamsLayout);
    waveParamsLayout.setHorizontalGroup(
      waveParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(waveParamsLayout.createSequentialGroup()
        .addGroup(waveParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(waveParamsLayout.createSequentialGroup()
            .addGroup(waveParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(period, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(amplitude, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(frequency, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 93, Short.MAX_VALUE))
          .addComponent(graphPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        .addContainerGap())
    );
    waveParamsLayout.setVerticalGroup(
      waveParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(waveParamsLayout.createSequentialGroup()
        .addComponent(amplitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(period, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(frequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(graphPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0))
    );

    javax.swing.GroupLayout outputSettingsLayout = new javax.swing.GroupLayout(outputSettings);
    outputSettings.setLayout(outputSettingsLayout);
    outputSettingsLayout.setHorizontalGroup(
      outputSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputSettingsLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(waveParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(outputPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    outputSettingsLayout.setVerticalGroup(
      outputSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(outputSettingsLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addGroup(outputSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(waveParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(outputPreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(0, 0, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout openedLoopSettingsLayout = new javax.swing.GroupLayout(openedLoopSettings);
    openedLoopSettings.setLayout(openedLoopSettingsLayout);
    openedLoopSettingsLayout.setHorizontalGroup(
      openedLoopSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(waveType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addGroup(openedLoopSettingsLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(outputSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        .addContainerGap())
    );
    openedLoopSettingsLayout.setVerticalGroup(
      openedLoopSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(openedLoopSettingsLayout.createSequentialGroup()
        .addComponent(waveType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(outputSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    closedLoopSettings.setVisible(false);

    waveParams1.setMinimumSize(new java.awt.Dimension(169, 96));

    setPoint.setPreferredSize(new java.awt.Dimension(199, 33));

    setPointLabel.setText("SP");

    ((AbstractDocument)setPointField.getDocument()).setDocumentFilter(new DoubleFilter());

    jLabelVoltage1.setText("cm");

    processVariableLabel.setText("PV");

    pvItems =  new javax.swing.DefaultComboBoxModel<>();
    processVariableField.setModel(pvItems);
    pvItemsString = new ArrayList<>();
    processVariableField.setEnabled(false);
    processVariableField.setPreferredSize(new java.awt.Dimension(75, 28));

    javax.swing.GroupLayout setPointLayout = new javax.swing.GroupLayout(setPoint);
    setPoint.setLayout(setPointLayout);
    setPointLayout.setHorizontalGroup(
      setPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(setPointLayout.createSequentialGroup()
        .addGap(0, 0, 0)
        .addComponent(setPointLabel)
        .addGap(25, 25, 25)
        .addComponent(setPointField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(4, 4, 4)
        .addComponent(jLabelVoltage1)
        .addGap(25, 25, 25)
        .addComponent(processVariableLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(processVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    setPointLayout.setVerticalGroup(
      setPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(setPointLayout.createSequentialGroup()
        .addGap(5, 5, 5)
        .addGroup(setPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(setPointLabel)
          .addComponent(setPointField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabelVoltage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(processVariableLabel)
          .addComponent(processVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
    );

    controllerTypeLabel.setText("Tipo");

    DefaultComboBoxModel<ComboItem> model = new DefaultComboBoxModel<>();
    model.addElement(new ComboItem(ControllerType.P, "P"));
    model.addElement(new ComboItem(ControllerType.PI, "PI"));
    model.addElement(new ComboItem(ControllerType.PD, "PD"));
    model.addElement(new ComboItem(ControllerType.PID, "PID"));
    model.addElement(new ComboItem(ControllerType.PI_D, "PI-D"));

    controllerTypeCombo.setModel(model);

    controllerTypeCombo.setSelectedIndex(0);
    controllerTypeCombo.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        controllerTypeComboItemStateChanged(evt);
      }
    });

    kpLabel.setText("Kₚ");

    ((AbstractDocument)kpField.getDocument()).setDocumentFilter(new DoubleFilter());

    tauiLabel.setText("τᵢ");
    tauiLabel.setVisible(false);

    ((AbstractDocument)tauiField.getDocument()).setDocumentFilter(new DoubleFilter());
    tauiField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        tauiFieldKeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        tauiFieldKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        tauiFieldKeyPressed(evt);
      }
    });
    tauiField.setVisible(false);

    kiLabel.setText("Kᵢ");
    kiLabel.setVisible(false);

    ((AbstractDocument)kiField.getDocument()).setDocumentFilter(new DoubleFilter());
    kiField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        kiFieldKeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        kiFieldKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        kiFieldKeyPressed(evt);
      }
    });
    kiField.setVisible(false);

    taudLabel.setText("τd");
    taudLabel.setVisible(false);

    ((AbstractDocument)taudField.getDocument()).setDocumentFilter(new DoubleFilter());
    taudField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        taudFieldKeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        taudFieldKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        taudFieldKeyPressed(evt);
      }
    });
    taudField.setVisible(false);

    kdLabel.setText("Kd ");
    kdLabel.setVisible(false);

    ((AbstractDocument)kdField.getDocument()).setDocumentFilter(new DoubleFilter());
    kdField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        kdFieldKeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        kdFieldKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        kdFieldKeyPressed(evt);
      }
    });
    kdField.setVisible(false);

    parameters2loop.setVisible(false);

    ((AbstractDocument)taudField1.getDocument()).setDocumentFilter(new DoubleFilter());
    taudField1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        taudField1KeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        taudField1KeyPressed1(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        taudField1KeyPressed2(evt);
      }
    });
    taudField1.setVisible(false);

    DefaultComboBoxModel<ComboItem> model1 = new DefaultComboBoxModel<>();
    model1.addElement(new ComboItem(ControllerType.P, "P"));
    model1.addElement(new ComboItem(ControllerType.PI, "PI"));
    model1.addElement(new ComboItem(ControllerType.PD, "PD"));
    model1.addElement(new ComboItem(ControllerType.PID, "PID"));
    model1.addElement(new ComboItem(ControllerType.PI_D, "PI-D"));

    controllerTypeCombo1.setModel(model1);

    controllerTypeCombo1.setSelectedIndex(0);
    controllerTypeCombo1.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        controllerTypeCombo1ItemStateChanged(evt);
      }
    });

    ((AbstractDocument)kpField1.getDocument()).setDocumentFilter(new DoubleFilter());

    taudLabel1.setText("τd2");
    taudLabel1.setVisible(false);

    ((AbstractDocument)tauiField1.getDocument()).setDocumentFilter(new DoubleFilter());
    tauiField1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        tauiField1KeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        tauiField1KeyPressed1(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        tauiField1KeyPressed2(evt);
      }
    });
    tauiField1.setVisible(false);

    ((AbstractDocument)kdField1.getDocument()).setDocumentFilter(new DoubleFilter());
    kdField1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        kdField1KeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        kdField1KeyPressed1(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        kdField1KeyPressed2(evt);
      }
    });
    kdField1.setVisible(false);

    kiLabel1.setText("Kᵢ2");
    kiLabel1.setVisible(false);

    ((AbstractDocument)kiField1.getDocument()).setDocumentFilter(new DoubleFilter());
    kiField1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        kiField1KeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        kiField1KeyPressed1(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        kiField1KeyPressed2(evt);
      }
    });
    kiField1.setVisible(false);

    tauiLabel1.setText("τᵢ2");
    tauiLabel1.setVisible(false);

    controllerTypeLabel1.setText("Tipo");

    kpLabel1.setText("Kₚ2");

    kdLabel1.setText("Kd2 ");
    kdLabel1.setVisible(false);

    jLabel1.setText("Controlador Escravo");

    javax.swing.GroupLayout parameters2loopLayout = new javax.swing.GroupLayout(parameters2loop);
    parameters2loop.setLayout(parameters2loopLayout);
    parameters2loopLayout.setHorizontalGroup(
      parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parameters2loopLayout.createSequentialGroup()
        .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(parameters2loopLayout.createSequentialGroup()
            .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(tauiLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(taudLabel1, javax.swing.GroupLayout.Alignment.LEADING))
            .addGap(6, 6, 6))
          .addComponent(controllerTypeLabel1))
        .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(parameters2loopLayout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(taudField1)
              .addComponent(tauiField1)
              .addComponent(controllerTypeCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(40, 40, 40)
            .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(kiLabel1)
              .addComponent(kpLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(kdLabel1))
            .addGap(0, 0, 0)
            .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(kpField1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(kiField1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(kdField1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
          .addGroup(parameters2loopLayout.createSequentialGroup()
            .addGap(47, 47, 47)
            .addComponent(jLabel1)))
        .addGap(3, 3, 3))
    );
    parameters2loopLayout.setVerticalGroup(
      parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(parameters2loopLayout.createSequentialGroup()
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(controllerTypeCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(kpLabel1)
          .addComponent(kpField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(controllerTypeLabel1))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(tauiLabel1)
          .addComponent(tauiField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(kiLabel1)
          .addComponent(kiField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(parameters2loopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(taudLabel1)
          .addComponent(taudField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(kdLabel1)
          .addComponent(kdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(0, 0, 0))
    );

    jLabel2.setText("Controlador Mestre");

    javax.swing.GroupLayout waveParams1Layout = new javax.swing.GroupLayout(waveParams1);
    waveParams1.setLayout(waveParams1Layout);
    waveParams1Layout.setHorizontalGroup(
      waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jSeparator3)
      .addComponent(setPoint, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
      .addGroup(waveParams1Layout.createSequentialGroup()
        .addGap(1, 1, 1)
        .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(waveParams1Layout.createSequentialGroup()
            .addComponent(parameters2loop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
          .addGroup(waveParams1Layout.createSequentialGroup()
            .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(controllerTypeLabel)
              .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(tauiLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(taudLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(taudField)
              .addComponent(tauiField)
              .addComponent(controllerTypeCombo, 0, 72, Short.MAX_VALUE))
            .addGap(43, 43, 43)
            .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(kdLabel, javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(kiLabel)
              .addComponent(kpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(kpField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(kiField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(kdField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(16, 16, 16))))
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, waveParams1Layout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jLabel2)
        .addGap(69, 69, 69))
    );
    waveParams1Layout.setVerticalGroup(
      waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(waveParams1Layout.createSequentialGroup()
        .addGap(0, 0, 0)
        .addComponent(setPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(7, 7, 7)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(controllerTypeLabel)
          .addComponent(controllerTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(kpLabel)
          .addComponent(kpField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(tauiLabel)
          .addComponent(tauiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(kiLabel)
          .addComponent(kiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(waveParams1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(taudLabel)
          .addComponent(taudField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(kdLabel)
          .addComponent(kdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(parameters2loop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGap(42, 42, 42))
    );

    buttonGroup1.add(simpleButton);
    simpleButton.setText("Controle simples");
    simpleButton.setSelected(true);

    buttonGroup1.add(cascadeButton);
    cascadeButton.setText("Controle cascata");
    cascadeButton.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        cascadeButtonItemStateChanged(evt);
      }
    });

    takCheck.setText("Anti Wind-up");
    takCheck.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        takCheckActionPerformed(evt);
      }
    });

    kdLabel2.setText("Tₐₖ");
    kdLabel1.setVisible(false);

    ((AbstractDocument)kdField1.getDocument()).setDocumentFilter(new DoubleFilter());

    javax.swing.GroupLayout controlSettingsLayout = new javax.swing.GroupLayout(controlSettings);
    controlSettings.setLayout(controlSettingsLayout);
    controlSettingsLayout.setHorizontalGroup(
      controlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(controlSettingsLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(controlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(controlSettingsLayout.createSequentialGroup()
            .addGroup(controlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(controlSettingsLayout.createSequentialGroup()
                .addComponent(simpleButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cascadeButton))
              .addComponent(waveParams1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(controlSettingsLayout.createSequentialGroup()
            .addComponent(takCheck)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(kdLabel2)
            .addGap(18, 18, 18)
            .addComponent(takField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(34, 34, 34))))
    );
    controlSettingsLayout.setVerticalGroup(
      controlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlSettingsLayout.createSequentialGroup()
        .addGap(0, 0, 0)
        .addGroup(controlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(simpleButton)
          .addComponent(cascadeButton))
        .addGap(0, 0, 0)
        .addComponent(waveParams1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(controlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(controlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(kdLabel2)
            .addComponent(takField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(takCheck))
        .addGap(0, 0, 0))
    );

    javax.swing.GroupLayout closedLoopSettingsLayout = new javax.swing.GroupLayout(closedLoopSettings);
    closedLoopSettings.setLayout(closedLoopSettingsLayout);
    closedLoopSettingsLayout.setHorizontalGroup(
      closedLoopSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(closedLoopSettingsLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(controlSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    closedLoopSettingsLayout.setVerticalGroup(
      closedLoopSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(closedLoopSettingsLayout.createSequentialGroup()
        .addComponent(controlSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0))
    );

    apply.setText("Aplicar");
    apply.setEnabled(false);
    apply.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        applyMouseClicked(evt);
      }
    });
    apply.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        applyActionPerformed(evt);
      }
    });

    jButtonPreview.setText("Pré-Visualizar");
    jButtonPreview.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonPreviewActionPerformed(evt);
      }
    });

    loopChooser.setMinimumSize(new java.awt.Dimension(100, 50));
    loopChooser.setPreferredSize(new java.awt.Dimension(100, 50));

    openedLoop.setSelected(true);
    loopTypeChooser.add(openedLoop);
    openedLoop.setText("Malha aberta");
    openedLoop.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        openedLoopItemStateChanged(evt);
      }
    });

    loopTypeChooser.add(closedLoop);
    closedLoop.setText("Malha Fechada");
    closedLoop.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        closedLoopItemStateChanged(evt);
      }
    });
    closedLoop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        closedLoopActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout loopChooserLayout = new javax.swing.GroupLayout(loopChooser);
    loopChooser.setLayout(loopChooserLayout);
    loopChooserLayout.setHorizontalGroup(
      loopChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loopChooserLayout.createSequentialGroup()
        .addContainerGap(32, Short.MAX_VALUE)
        .addComponent(openedLoop)
        .addGap(65, 65, 65)
        .addComponent(closedLoop))
    );
    loopChooserLayout.setVerticalGroup(
      loopChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(loopChooserLayout.createSequentialGroup()
        .addGap(17, 17, 17)
        .addGroup(loopChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(openedLoop)
          .addComponent(closedLoop))
        .addGap(9, 9, 9))
    );

    jButtonStop.setText("Interromper");
    jButtonStop.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jButtonStopMouseClicked(evt);
      }
    });

    settingTimeLabel.setText("Tempo de acomodação(5%)");
    settingTimeLabel.setVisible(false);

    settlingTimeField.setText("...");
    settlingTimeField.setVisible(false);

    PeakTimeLavel.setText("Tempo de pico");
    PeakTimeLavel.setVisible(false);

    PeakTimeField.setText("...");
    PeakTimeField.setVisible(false);

    mpLabel.setText("Sobressinal máximo");
    mpLabel.setVisible(false);

    mpField.setText("...");
    mpField.setVisible(false);

    riseTimeLabel.setText("Tempo de subida (95%)");
    mpLabel.setVisible(false);

    riseTimeField.setText("...");
    mpField.setVisible(false);

    javax.swing.GroupLayout writeBoxLayout = new javax.swing.GroupLayout(writeBox);
    writeBox.setLayout(writeBoxLayout);
    writeBoxLayout.setHorizontalGroup(
      writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jSeparator1)
      .addComponent(jSeparator2)
      .addGroup(writeBoxLayout.createSequentialGroup()
        .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(writeBoxLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(closedLoopSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addGroup(javax.swing.GroupLayout.Alignment.LEADING, writeBoxLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(writeBoxLayout.createSequentialGroup()
                      .addComponent(jButtonPreview)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(jButtonStop)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(apply))
                    .addGroup(writeBoxLayout.createSequentialGroup()
                      .addComponent(mpLabel)
                      .addGap(58, 58, 58)
                      .addComponent(mpField, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                    .addGroup(writeBoxLayout.createSequentialGroup()
                      .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(PeakTimeLavel)
                        .addComponent(settingTimeLabel))
                      .addGap(32, 32, 32)
                      .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(settlingTimeField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PeakTimeField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                  .addGroup(writeBoxLayout.createSequentialGroup()
                    .addComponent(riseTimeLabel)
                    .addGap(73, 73, 73)
                    .addComponent(riseTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))))
          .addComponent(openedLoopSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(loopChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(0, 0, Short.MAX_VALUE))
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, writeBoxLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(outputTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    writeBoxLayout.setVerticalGroup(
      writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(writeBoxLayout.createSequentialGroup()
        .addComponent(loopChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(outputTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(5, 5, 5)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(1, 1, 1)
        .addComponent(openedLoopSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(closedLoopSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(apply)
          .addComponent(jButtonPreview)
          .addComponent(jButtonStop))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(settingTimeLabel)
          .addComponent(settlingTimeField))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(PeakTimeLavel)
          .addComponent(PeakTimeField))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(mpLabel)
          .addComponent(mpField))
        .addGap(8, 8, 8)
        .addGroup(writeBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(riseTimeLabel)
          .addComponent(riseTimeField))
        .addContainerGap())
    );

    ipPortTitle.setBackground(new java.awt.Color(158, 158, 158));
    ipPortTitle.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        ipPortTitleMouseClicked(evt);
      }
    });

    ipPortToggler.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
    ipPortToggler.setText("IP / Porta");

    javax.swing.GroupLayout ipPortTitleLayout = new javax.swing.GroupLayout(ipPortTitle);
    ipPortTitle.setLayout(ipPortTitleLayout);
    ipPortTitleLayout.setHorizontalGroup(
      ipPortTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ipPortTitleLayout.createSequentialGroup()
        .addComponent(ipPortToggler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    ipPortTitleLayout.setVerticalGroup(
      ipPortTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(ipPortToggler)
    );

    ipPortLabel.setText("IP:Porta");

    ipPortField.setText(this.currentIp+":"+this.currentPort);
    ipPortField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ipPortFieldActionPerformed(evt);
      }
    });

    connect.setText("Conectar");
    connect.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        connectMouseClicked(evt);
      }
    });

    javax.swing.GroupLayout ipPortWrapperLayout = new javax.swing.GroupLayout(ipPortWrapper);
    ipPortWrapper.setLayout(ipPortWrapperLayout);
    ipPortWrapperLayout.setHorizontalGroup(
      ipPortWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ipPortWrapperLayout.createSequentialGroup()
        .addGap(0, 0, 0)
        .addComponent(ipPortLabel)
        .addGap(18, 18, 18)
        .addComponent(ipPortField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(connect)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    ipPortWrapperLayout.setVerticalGroup(
      ipPortWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ipPortWrapperLayout.createSequentialGroup()
        .addGap(5, 5, 5)
        .addGroup(ipPortWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ipPortLabel)
          .addComponent(ipPortField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(connect)))
    );

    javax.swing.GroupLayout ipPortBoxLayout = new javax.swing.GroupLayout(ipPortBox);
    ipPortBox.setLayout(ipPortBoxLayout);
    ipPortBoxLayout.setHorizontalGroup(
      ipPortBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ipPortBoxLayout.createSequentialGroup()
        .addGap(23, 23, 23)
        .addComponent(ipPortWrapper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    ipPortBoxLayout.setVerticalGroup(
      ipPortBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ipPortBoxLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(ipPortWrapper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    observerTitle.setBackground(new java.awt.Color(158, 158, 158));

    observerToggler.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
    observerToggler.setText("Observador");
    observerToggler.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        observerTogglerMouseClicked(evt);
      }
    });

    javax.swing.GroupLayout observerTitleLayout = new javax.swing.GroupLayout(observerTitle);
    observerTitle.setLayout(observerTitleLayout);
    observerTitleLayout.setHorizontalGroup(
      observerTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(observerTitleLayout.createSequentialGroup()
        .addComponent(observerToggler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    observerTitleLayout.setVerticalGroup(
      observerTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, observerTitleLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(observerToggler))
    );

    polo2Label.setText("Polo 2:");
    taudLabel1.setVisible(false);

    ((AbstractDocument)taudField1.getDocument()).setDocumentFilter(new DoubleFilter());
    polo2Field.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        polo2FieldKeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        polo2FieldKeyPressed1(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        polo2FieldKeyPressed2(evt);
      }
    });
    taudField1.setVisible(false);

    ((AbstractDocument)tauiField1.getDocument()).setDocumentFilter(new DoubleFilter());
    polo1Field.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(java.awt.event.KeyEvent evt) {
        polo1FieldKeyPressed(evt);
      }
      public void keyPressed(java.awt.event.KeyEvent evt) {
        polo1FieldKeyPressed1(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        polo1FieldKeyPressed2(evt);
      }
    });
    tauiField1.setVisible(false);

    polo1Label.setText("Polo 1:");
    tauiLabel1.setVisible(false);

    jTableMatrizGanho.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null},
        {null}
      },
      new String [] {
        "Matriz de Ganho"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.Double.class
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }
    });
    jTableMatrizGanho.setAutoscrolls(false);
    jTableMatrizGanho.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jTableMatrizGanho.setFocusable(false);
    jTableMatrizGanho.setGridColor(new java.awt.Color(255, 255, 255));
    jTableMatrizGanho.setRowMargin(2);
    jTableMatrizGanho.setSelectionBackground(new java.awt.Color(255, 255, 255));
    jTableMatrizGanho.setSelectionForeground(new java.awt.Color(0, 0, 0));
    jTableMatrizGanho.setShowVerticalLines(false);

    jLabelCochete1.setFont(new java.awt.Font("DejaVu Sans Light", 0, 48)); // NOI18N
    jLabelCochete1.setText("[");

    jLabelCochete2.setFont(new java.awt.Font("DejaVu Sans Light", 0, 48)); // NOI18N
    jLabelCochete2.setText("]");

    applyObserver.setText("Aplicar");
    applyObserver.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        applyObserverClicked(evt);
      }
    });

    javax.swing.GroupLayout observerBoxLayout = new javax.swing.GroupLayout(observerBox);
    observerBox.setLayout(observerBoxLayout);
    observerBoxLayout.setHorizontalGroup(
      observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, observerBoxLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(observerBoxLayout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(applyObserver))
          .addGroup(observerBoxLayout.createSequentialGroup()
            .addGroup(observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(polo1Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(polo2Label, javax.swing.GroupLayout.Alignment.LEADING))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(polo1Field, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
              .addComponent(polo2Field))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelCochete1)
            .addGap(3, 3, 3)
            .addComponent(jTableMatrizGanho, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(3, 3, 3)
            .addComponent(jLabelCochete2)))
        .addGap(24, 24, 24))
    );
    observerBoxLayout.setVerticalGroup(
      observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(observerBoxLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(observerBoxLayout.createSequentialGroup()
            .addGroup(observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(polo1Label)
              .addComponent(polo1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(polo2Label)
              .addComponent(polo2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(observerBoxLayout.createSequentialGroup()
            .addGroup(observerBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(observerBoxLayout.createSequentialGroup()
                .addComponent(jLabelCochete2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
              .addGroup(observerBoxLayout.createSequentialGroup()
                .addComponent(jLabelCochete1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
              .addGroup(observerBoxLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jTableMatrizGanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(applyObserver))))
    );

    javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
    sidebar.setLayout(sidebarLayout);
    sidebarLayout.setHorizontalGroup(
      sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(graphBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(observerTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(graphTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(readTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(observerBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(ipPortBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(readBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(ipPortTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(writeTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(writeBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    sidebarLayout.setVerticalGroup(
      sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(sidebarLayout.createSequentialGroup()
        .addGap(0, 0, 0)
        .addComponent(ipPortTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(ipPortBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(readTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(readBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(writeTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(writeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 915, Short.MAX_VALUE)
        .addGap(0, 0, 0)
        .addComponent(observerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(observerBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(graphTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(graphBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0))
    );

    scrollPane1.add(sidebar);

    display1Title.setText("Level 1:");

    display1Level.setText("...");

    display2Level.setText("...");

    display2Title.setText("Level 2:");

    jMenu1.setText("File");
    jMenuBar1.add(jMenu1);

    jMenu2.setText("Edit");
    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(statusConnectedLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(display2Title)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(display2Level)
            .addGap(69, 69, 69)
            .addComponent(display1Title)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(display1Level)
            .addGap(72, 72, 72)
            .addComponent(statusLockLabel))
          .addGroup(layout.createSequentialGroup()
            .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(graphPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(graphPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE))))
        .addGap(12, 12, 12))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(graphPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(graphPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
          .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(statusConnectedLabel)
          .addComponent(statusLockLabel)
          .addComponent(display1Title)
          .addComponent(display1Level)
          .addComponent(display2Level)
          .addComponent(display2Title))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void ipPortFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipPortFieldActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_ipPortFieldActionPerformed

    private void ipPortTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ipPortTitleMouseClicked
      ipPortBox.setVisible(!ipPortBox.isVisible());
    }//GEN-LAST:event_ipPortTitleMouseClicked

    private void writeTogglerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_writeTogglerMouseClicked
      writeBox.setVisible(!writeBox.isVisible());
    }//GEN-LAST:event_writeTogglerMouseClicked

    private void updatePVSelector(java.awt.event.ItemEvent evt){//GEN-FIRST:event_updatePVSelector
      JCheckBox clickedCBox = (JCheckBox) evt.getSource();
      String text = clickedCBox.getText();
      TankTag t = text.equals("A0") ? TankTag.TANK_1 : text.equals("A1") ? TankTag.TANK_2 : null;
      if (clickedCBox.isSelected()) {
        pvItems.addElement(new ComboItem(t, text));
        pvItemsString.add(text);
      } else {
        pvItems.removeElementAt(pvItemsString.indexOf(text));
        pvItemsString.remove(text);
      }
      processVariableField.setEnabled(pvItems.getSize() > 0);
    }//GEN-LAST:event_updatePVSelector

    private void readTogglerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_readTogglerMouseClicked
      readBox.setVisible(!readBox.isVisible());
    }//GEN-LAST:event_readTogglerMouseClicked

    private void closedLoopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_closedLoopItemStateChanged
      JRadioButton self = (JRadioButton) evt.getSource();
      if (self.isSelected()) {
        openedLoopSettings.setVisible(false);
        closedLoopSettings.setVisible(true);
        writeBox.setPreferredSize(new Dimension(357, 140));
        jButtonPreview.setVisible(false);
        
        
        kiField.setVisible(false);
        kiLabel.setVisible(false);

        tauiField.setVisible(false);
        tauiLabel.setVisible(false);

        kdField.setVisible(false);
        kdLabel.setVisible(false);

        taudField.setVisible(false);
        taudLabel.setVisible(false);
        
        PCheckbox.setEnabled(true);
        ICheckbox.setEnabled(true);
        DCheckbox.setEnabled(true);
        
        OCheckbox.setEnabled(true);
        
        errorCheckbox.setSelected(true);
        SPCheckbox.setSelected(true);
        errorCheckbox.setEnabled(true);
        SPCheckbox.setEnabled(true);
                
        settingTimeLabel.setVisible(true);
        settlingTimeField.setVisible(true);
        
        PeakTimeField.setVisible(true);
        PeakTimeLavel.setVisible(true);
        
        mpField.setVisible(true);
        mpLabel.setVisible(true);
        pack();
      }
    }//GEN-LAST:event_closedLoopItemStateChanged

    private void openedLoopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_openedLoopItemStateChanged
      JRadioButton self = (JRadioButton) evt.getSource();
      if (self.isSelected()) {
        openedLoopSettings.setVisible(true);
        closedLoopSettings.setVisible(false);
        writeBox.setPreferredSize(new Dimension(357, 215));
        jButtonPreview.setVisible(true);
        
        PCheckbox.setSelected(false);
        ICheckbox.setSelected(false);
        DCheckbox.setSelected(false);
        errorCheckbox.setSelected(false);
        SPCheckbox.setSelected(false);
        
        
        
        PCheckbox.setEnabled(false);
        ICheckbox.setEnabled(false);
        DCheckbox.setEnabled(false);
        errorCheckbox.setEnabled(false);
        SPCheckbox.setEnabled(false);
        
        settingTimeLabel.setVisible(false);
        settlingTimeField.setVisible(false);
        
        PeakTimeField.setVisible(false);
        PeakTimeLavel.setVisible(false);
        
        mpField.setVisible(false);
        mpLabel.setVisible(false);
        
        pack();
      }
    }//GEN-LAST:event_openedLoopItemStateChanged

  private void applyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_applyMouseClicked
    
    mpField.setText("...");
    PeakTimeField.setText("...");
    settlingTimeField.setText("...");
    riseTimeField.setText("...");
   
    graphPanel1.clearAll();
    graphPanel2.clearAll();

    if (!"".equals(setPointField.getText())) {
      double selectedSetPoint = Double.parseDouble(setPointField.getText());
      if (selectedSetPoint != currentSetPoint) {
        call(setPointChange, currentSetPoint = selectedSetPoint);
      }
    }

    if (!"".equals(kpField.getText())) {
      double selectedKp = Double.parseDouble(kpField.getText());
      if (selectedKp != currentKp) {
        call(kpChange, currentKp = selectedKp);
      }
    }

    if (!"".equals(kiField.getText())) {
      double selectedKi = Double.parseDouble(kiField.getText());
      if (selectedKi != currentKi) {
        call(kiChange, currentKi = selectedKi);
      }
    }

    if (!"".equals(kdField.getText())) {
      double selectedKd = Double.parseDouble(kdField.getText());
      if (selectedKd != currentKd) {
        call(kdChange, currentKd = selectedKd);
      }
    }
    
    if (!"".equals(kpField1.getText())) {
      double selectedKp1 = Double.parseDouble(kpField1.getText());
      if (selectedKp1 != currentKp1) {
        call(kp1Change, currentKp1 = selectedKp1);
      }
    }

    if (!"".equals(kiField1.getText())) {
      double selectedKi1 = Double.parseDouble(kiField1.getText());
      if (selectedKi1 != currentKi1) {
        call(ki1Change, currentKi1 = selectedKi1);
      }
    }

    if (!"".equals(kdField1.getText())) {
      double selectedKd1 = Double.parseDouble(kdField1.getText());
      if (selectedKd1 != currentKd1) {
        call(kd1Change, currentKd1 = selectedKd1);
      }
    }
    
    if(cascade != cascadeButton.isSelected()){
      call(cascadeChange, cascade = cascadeButton.isSelected());
    }
    
    if(!"".equals(takField.getText())){
      double selectedTak = Double.parseDouble(takField.getText());
      if(selectedTak != currentTak){
        call(TakChange, currentTak = selectedTak);
      }
    }
    
    call(TakChange, currentTak = 0.0);
    
    if ((ComboItem) controllerTypeCombo.getSelectedItem() != null) {
      ControllerType selectedControllerType = (ControllerType) ((ComboItem) controllerTypeCombo.getSelectedItem()).getValue();
      if (!selectedControllerType.equals(currentControllerType)) {
        switch(selectedControllerType){
          case P:
            PCheckbox.setSelected(true);
            ICheckbox.setSelected(false);
            DCheckbox.setSelected(false);
            break;
          case PI:
            PCheckbox.setSelected(true);
            ICheckbox.setSelected(true);
            DCheckbox.setSelected(false);
            break;
          case PD:
            PCheckbox.setSelected(true);
            ICheckbox.setSelected(false);
            DCheckbox.setSelected(true);
            break;
          case PID:
            PCheckbox.setSelected(true);
            ICheckbox.setSelected(true);
            DCheckbox.setSelected(true);
            break;
          case PI_D:
            PCheckbox.setSelected(true);
            ICheckbox.setSelected(true);
            DCheckbox.setSelected(true);
        }
        
        call(controllerTypeChange, currentControllerType = selectedControllerType);
      }
    }
    
    if ((ComboItem) controllerTypeCombo1.getSelectedItem() != null) {
      ControllerType selectedControllerType = (ControllerType) ((ComboItem) controllerTypeCombo1.getSelectedItem()).getValue();
      if (!selectedControllerType.equals(currentControllerType1)) {
        call(controllerTypeChange1, currentControllerType1 = selectedControllerType);
      }
    }
    
    if ((ComboItem) processVariableField.getSelectedItem() != null) {
      TankTag selectedPV = (TankTag) ((ComboItem) processVariableField.getSelectedItem()).getValue();
      if (selectedPV != currentPV) {
        call(processVariableChange, currentPV = selectedPV);
      }
    }

    if (!"".equals(amplitudeField.getText())) {
      double selectedAmplitude = Double.parseDouble(amplitudeField.getText());
      if (selectedAmplitude != currentAmplitide) {
        call(amplitudeChange, currentAmplitide = selectedAmplitude);
      }
    }
    if (!"".equals(periodField.getText())) {
      double selectedPeriod = Double.parseDouble(periodField.getText());
      if (selectedPeriod != currentPeriod) {
        call(periodChange, currentPeriod = selectedPeriod);
      }
    }

    JToggleButton[] waves = {sinus, sawtooth, square, step, random};
    int i = 0;
    for (JToggleButton wave : waves) {
      if (wave.isSelected() && !wave.equals(currentWaveButton)) {
        call(waveChange, currentWave);
        currentWaveButton = wave;
      }
    }

    if (closedLoop.isSelected() ^ currentLoop.equals(Loop.CLOSED)) {
      call(loopChange, currentLoop = closedLoop.isSelected() ? Loop.CLOSED : Loop.OPENED);
    }

    call(applyClicked);

  }//GEN-LAST:event_applyMouseClicked

    private void randomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_randomItemStateChanged
      if (((JToggleButton) evt.getSource()).isSelected()) {
        currentWave = Wave.RANDOM;
        frequency.setVisible(true);
        period.setVisible(true);
      }
    }//GEN-LAST:event_randomItemStateChanged

    private void connectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectMouseClicked
      String[] ipPortArray = ipPortField.getText().split(":");
      if (ipPortArray.length > 1) {
        String ip = ipPortArray[0];
        int port = Integer.parseInt(ipPortArray[1]);

        if (!currentIp.equals(ip)) {
          call(ipChange, currentIp = ip);
        }
        if (port != currentPort) {
          call(portChange, currentPort = port);
        }
      }
      apply.setEnabled(false);
      statusConnectedLabel.setText("Não Conectado");
      statusConnectedLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/ball_red.png")));
      call(connectClicked);
    }//GEN-LAST:event_connectMouseClicked

    private void stepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_stepItemStateChanged
      if (((JToggleButton) evt.getSource()).isSelected()) {
        currentWave = Wave.STEP;
        frequency.setVisible(false);
        period.setVisible(false);
      }
    }//GEN-LAST:event_stepItemStateChanged

    private void squareItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_squareItemStateChanged
      if (((JToggleButton) evt.getSource()).isSelected()) {
        currentWave = Wave.SQUARE;
        frequency.setVisible(true);
        period.setVisible(true);
      }
    }//GEN-LAST:event_squareItemStateChanged

    private void sawtoothItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sawtoothItemStateChanged
      if (((JToggleButton) evt.getSource()).isSelected()) {
        currentWave = Wave.SAWTOOTH;
        frequency.setVisible(true);
        period.setVisible(true);
      }
    }//GEN-LAST:event_sawtoothItemStateChanged

    private void sinusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sinusItemStateChanged
      if (((JToggleButton) evt.getSource()).isSelected()) {
        currentWave = Wave.SINUSOID;
        frequency.setVisible(true);
        period.setVisible(true);
      }
    }//GEN-LAST:event_sinusItemStateChanged

    private void jButtonPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviewActionPerformed
      double x = 0;
      double p;
      double a = Double.parseDouble(amplitudeField.getText());
      double y;
      graphPanel3.clean("Sinal para configuracao");
      switch (currentWave) {
        case SINUSOID:
          p = Double.parseDouble(periodField.getText());
          while (x <= 1.05 * p) {
            y = a / 2 * Math.sin((x / p * 2 * Math.PI)) + a / 2;
            graphPanel3.addValue("Sinal para configuracao", new Millisecond((int) Math.round((x-Math.floor(x))*1000), (int) Math.floor(x), 0, 0, 1, 1, 1900), y);
            x = x + p / 40;
          }
          break;
        case SAWTOOTH:
          p = Double.parseDouble(periodField.getText());
          while (x <= 2 * p) {
            double r = x % p;
            y = (a / p) * r;
            graphPanel3.addValue("Sinal para configuracao", new Millisecond((int) Math.round((x-Math.floor(x))*1000), (int) Math.floor(x), 0, 0, 1, 1, 1900), y);
            x = x + p / 1000;
          }
          break;
        case SQUARE:
          p = Double.parseDouble(periodField.getText());
          while (x <= 2 * p) {
            double r = x % p;
            y = r >= p / 2 ? 0 : a;
            graphPanel3.addValue("Sinal para configuracao", new Millisecond((int) Math.round((x-Math.floor(x))*1000), (int) Math.floor(x), 0, 0, 1, 1, 1900), y);
            x = x + p / 1000;
          }
          break;
        case STEP:
          p = 5;
          while (x <= 2 * p) {
            y = a;
            graphPanel3.addValue("Sinal para configuracao", new Millisecond((int) Math.round((x-Math.floor(x))*1000), (int) Math.floor(x), 0, 0, 1, 1, 1900), y);
            x = x + p / 1000;
          }
          break;
        case RANDOM:
          break;
      }
    }//GEN-LAST:event_jButtonPreviewActionPerformed

    private void closedLoopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closedLoopActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_closedLoopActionPerformed

    private void jButtonStopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonStopMouseClicked
      call(stopClicked);
      // TODO add your handling code here:
    }//GEN-LAST:event_jButtonStopMouseClicked

    private void controllerTypeComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_controllerTypeComboItemStateChanged
      switch((ControllerType)((ComboItem)controllerTypeCombo.getSelectedItem()).getValue()){
        case PI:
          kiField.setVisible(true);
          kiLabel.setVisible(true);
          
          tauiField.setVisible(true);
          tauiLabel.setVisible(true);
          
          kdField.setVisible(false);
          kdLabel.setVisible(false);
          
          taudField.setVisible(false);
          taudLabel.setVisible(false);
          
          if(enabledCurves.contains(DERIVATIVE_PART_CURVE))
            enabledCurves.remove(DERIVATIVE_PART_CURVE);
          if(!enabledCurves.contains(PROPORCIONAL_PART_CURVE))
            enabledCurves.add(PROPORCIONAL_PART_CURVE);
          if(!enabledCurves.contains(INTEGRAL_PART_CURVE))
            enabledCurves.add(INTEGRAL_PART_CURVE);
          break;
        case PID:
        case PI_D:
          kiField.setVisible(true);
          kiLabel.setVisible(true);
          
          tauiField.setVisible(true);
          tauiLabel.setVisible(true);
          
          kdField.setVisible(true);
          kdLabel.setVisible(true);
          
          taudField.setVisible(true);
          taudLabel.setVisible(true);
          
          if(!enabledCurves.contains(DERIVATIVE_PART_CURVE))
            enabledCurves.add(DERIVATIVE_PART_CURVE);
          if(!enabledCurves.contains(PROPORCIONAL_PART_CURVE))
            enabledCurves.add(PROPORCIONAL_PART_CURVE);
          if(!enabledCurves.contains(INTEGRAL_PART_CURVE))
            enabledCurves.add(INTEGRAL_PART_CURVE);
          
          break;
        case PD:
          kiField.setVisible(false);
          kiLabel.setVisible(false);
          
          tauiField.setVisible(false);
          tauiLabel.setVisible(false);
          
          kdField.setVisible(true);
          kdLabel.setVisible(true);
          
          taudField.setVisible(true);
          taudLabel.setVisible(true);
          
          if(!enabledCurves.contains(DERIVATIVE_PART_CURVE))
            enabledCurves.add(DERIVATIVE_PART_CURVE);
          if(!enabledCurves.contains(PROPORCIONAL_PART_CURVE))
            enabledCurves.add(PROPORCIONAL_PART_CURVE);
          if(enabledCurves.contains(INTEGRAL_PART_CURVE))
            enabledCurves.remove(INTEGRAL_PART_CURVE);
          
          break;
        default:
          kiField.setVisible(false);
          kiLabel.setVisible(false);
          
          tauiField.setVisible(false);
          tauiLabel.setVisible(false);
          
          kdField.setVisible(false);
          kdLabel.setVisible(false);
          
          taudField.setVisible(false);
          taudLabel.setVisible(false);
          break;
      }
    }//GEN-LAST:event_controllerTypeComboItemStateChanged

  private void periodFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_periodFieldKeyPressed
    try{
    if(!"".equals(periodField.getText()) && periodField.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double f = 1/Double.parseDouble(periodField.getText());
      frequencyField.setText(String.format("%6.1e", f));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_periodFieldKeyPressed

  private void frequencyFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_frequencyFieldKeyPressed
    try{
    if(!"".equals(frequencyField.getText()) && frequencyField.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double f = 1/Double.parseDouble(frequencyField.getText());
      periodField.setText(String.format("%6.1e", f));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_frequencyFieldKeyPressed

  private void kiFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kiFieldKeyPressed
    try{
    if(!"".equals(kiField.getText()) && kiField.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double taui = 0;
      if(!kpField.getText().equals("")){
        taui = Double.parseDouble(kpField.getText())/Double.parseDouble(kiField.getText());
      }
      tauiField.setText(String.format("%6.1e", taui));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_kiFieldKeyPressed

  private void tauiFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tauiFieldKeyPressed
    try{
    if(!"".equals(tauiField.getText()) && tauiField.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double ki = 0;
      if(!kpField.getText().equals("")){
        ki = Double.parseDouble(kpField.getText())/Double.parseDouble(tauiField.getText());
      }
      kiField.setText(String.format("%6.1e", ki));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_tauiFieldKeyPressed

  private void kdFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdFieldKeyPressed
    try{
      if(!"".equals(kdField.getText()) && kdField.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
        double taud = Double.MAX_VALUE;
        if(!kpField.getText().equals("") && Double.parseDouble(kpField.getText()) != 0){
          taud = Double.parseDouble(kdField.getText())/Double.parseDouble(kpField.getText());
        }else if(Double.parseDouble(kdField.getText())<0){
          taud = Double.MIN_VALUE;
        }
        taudField.setText(String.format("%6.1e", taud));
      }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_kdFieldKeyPressed

  private void taudFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taudFieldKeyPressed
    try{
    if(!"".equals(taudField.getText()) && taudField.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double kd = 0;
      if(!kpField.getText().equals("")){
        kd = Double.parseDouble(kpField.getText())*Double.parseDouble(taudField.getText());
      }
      kdField.setText(String.format("%6.1e", kd));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_taudFieldKeyPressed

    boolean buttonGraph = false;
    private void graphTogglerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_graphTogglerMouseClicked
        graphBox.setVisible(!graphBox.isVisible());
    }//GEN-LAST:event_graphTogglerMouseClicked

  private void uCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_uCheckboxItemStateChanged
    if(!uCheckbox.isSelected())
      graphPanel2.removeSerie(CALCULATED_SIGNAL_CURVE);
  }//GEN-LAST:event_uCheckboxItemStateChanged

  private void u_barCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_u_barCheckboxItemStateChanged
    if(!u_barCheckbox.isSelected())
      graphPanel2.removeSerie(SENDED_SIGNAL_CURVE);
  }//GEN-LAST:event_u_barCheckboxItemStateChanged

  private void errorCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_errorCheckboxItemStateChanged
    if(!errorCheckbox.isSelected())
      graphPanel1.removeSerie(ERROR_CURVE);
  }//GEN-LAST:event_errorCheckboxItemStateChanged

  private void SPCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SPCheckboxItemStateChanged
    if(!SPCheckbox.isSelected())
      graphPanel1.removeSerie(SETPOINT_CURVE);
  }//GEN-LAST:event_SPCheckboxItemStateChanged

  private void level1CheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_level1CheckboxItemStateChanged
    if(!level1Checkbox.isSelected())
        graphPanel1.removeSerie(TANK1_LEVEL_CURVE);
  }//GEN-LAST:event_level1CheckboxItemStateChanged

  private void level2CheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_level2CheckboxItemStateChanged
    if(!level2Checkbox.isSelected())
      graphPanel1.removeSerie(TANK2_LEVEL_CURVE);
  }//GEN-LAST:event_level2CheckboxItemStateChanged

  private void PCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PCheckboxItemStateChanged
    if(!PCheckbox.isSelected())
      graphPanel2.removeSerie(PROPORCIONAL_PART_CURVE);
  }//GEN-LAST:event_PCheckboxItemStateChanged

  private void ICheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ICheckboxItemStateChanged
    if(!ICheckbox.isSelected())
      graphPanel2.removeSerie(INTEGRAL_PART_CURVE);
  }//GEN-LAST:event_ICheckboxItemStateChanged

  private void DCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DCheckboxItemStateChanged
    if(!DCheckbox.isSelected())
      graphPanel2.removeSerie(DERIVATIVE_PART_CURVE);
  }//GEN-LAST:event_DCheckboxItemStateChanged

  private void cascadeButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cascadeButtonItemStateChanged
    if(cascadeButton.isSelected()){
      processVariableLabel.setVisible(false);
      processVariableField.setVisible(false);
      
      parameters2loop.setVisible(true);
      try{
      processVariableField.setSelectedIndex(1);
      }catch(IllegalArgumentException e){
        System.out.println("Nenhuma entrada selecionada.");
      }
    }else{
      processVariableLabel.setVisible(true);
      processVariableField.setVisible(true);
      
      parameters2loop.setVisible(false);
      
    }
  }//GEN-LAST:event_cascadeButtonItemStateChanged

  private void controllerTypeCombo1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_controllerTypeCombo1ItemStateChanged
    switch((ControllerType)((ComboItem)controllerTypeCombo1.getSelectedItem()).getValue()){
        case PI:
          kiField1.setVisible(true);
          kiLabel1.setVisible(true);
          
          tauiField1.setVisible(true);
          tauiLabel1.setVisible(true);
          
          kdField1.setVisible(false);
          kdLabel1.setVisible(false);
          
          taudField1.setVisible(false);
          taudLabel1.setVisible(false);
          
          if(enabledCurves.contains(DERIVATIVE_PART_CURVE_1))
            enabledCurves.remove(DERIVATIVE_PART_CURVE_1);
          if(!enabledCurves.contains(PROPORCIONAL_PART_CURVE_1))
            enabledCurves.add(PROPORCIONAL_PART_CURVE_1);
          if(!enabledCurves.contains(INTEGRAL_PART_CURVE_1))
            enabledCurves.add(INTEGRAL_PART_CURVE_1);
          break;
        case PID:
        case PI_D:
          kiField1.setVisible(true);
          kiLabel1.setVisible(true);
          
          tauiField1.setVisible(true);
          tauiLabel1.setVisible(true);
          
          kdField1.setVisible(true);
          kdLabel1.setVisible(true);
          
          taudField1.setVisible(true);
          taudLabel1.setVisible(true);
          
          if(!enabledCurves.contains(DERIVATIVE_PART_CURVE_1))
            enabledCurves.add(DERIVATIVE_PART_CURVE_1);
          if(!enabledCurves.contains(PROPORCIONAL_PART_CURVE_1))
            enabledCurves.add(PROPORCIONAL_PART_CURVE_1);
          if(!enabledCurves.contains(INTEGRAL_PART_CURVE_1))
            enabledCurves.add(INTEGRAL_PART_CURVE_1);
          
          break;
        case PD:
          kiField1.setVisible(false);
          kiLabel1.setVisible(false);
          
          tauiField1.setVisible(false);
          tauiLabel1.setVisible(false);
          
          kdField1.setVisible(true);
          kdLabel1.setVisible(true);
          
          taudField1.setVisible(true);
          taudLabel1.setVisible(true);
          
          if(!enabledCurves.contains(DERIVATIVE_PART_CURVE_1))
            enabledCurves.add(DERIVATIVE_PART_CURVE_1);
          if(!enabledCurves.contains(PROPORCIONAL_PART_CURVE_1))
            enabledCurves.add(PROPORCIONAL_PART_CURVE_1);
          if(enabledCurves.contains(INTEGRAL_PART_CURVE_1))
            enabledCurves.remove(INTEGRAL_PART_CURVE_1);
          
          break;
        default:
          kiField1.setVisible(false);
          kiLabel1.setVisible(false);
          
          tauiField1.setVisible(false);
          tauiLabel1.setVisible(false);
          
          kdField1.setVisible(false);
          kdLabel1.setVisible(false);
          
          taudField1.setVisible(false);
          taudLabel1.setVisible(false);
          break;
      }
  }//GEN-LAST:event_controllerTypeCombo1ItemStateChanged

  private void tauiField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tauiField1KeyPressed
    try{
    if(!"".equals(tauiField1.getText()) && tauiField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double ki = 0;
      if(!kpField1.getText().equals("")){
        ki = Double.parseDouble(kpField1.getText())/Double.parseDouble(tauiField1.getText());
      }
      kiField1.setText(String.format("%6.1e", ki));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_tauiField1KeyPressed

  private void tauiField1KeyPressed1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tauiField1KeyPressed1
    try{
    if(!"".equals(tauiField1.getText()) && tauiField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double ki = 0;
      if(!kpField1.getText().equals("")){
        ki = Double.parseDouble(kpField1.getText())/Double.parseDouble(tauiField1.getText());
      }
      kiField1.setText(String.format("%6.1e", ki));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_tauiField1KeyPressed1

  private void tauiField1KeyPressed2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tauiField1KeyPressed2
    try{
    if(!"".equals(tauiField1.getText()) && tauiField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double ki = 0;
      if(!kpField1.getText().equals("")){
        ki = Double.parseDouble(kpField1.getText())/Double.parseDouble(tauiField1.getText());
      }
      kiField1.setText(String.format("%6.1e", ki));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_tauiField1KeyPressed2

  private void kiField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kiField1KeyPressed
    try{
    if(!"".equals(kiField1.getText()) && kiField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double taui = 0;
      if(!kpField1.getText().equals("")){
        taui = Double.parseDouble(kpField1.getText())/Double.parseDouble(kiField1.getText());
      }
      tauiField1.setText(String.format("%6.1e", taui));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_kiField1KeyPressed

  private void kiField1KeyPressed1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kiField1KeyPressed1
    try{
    if(!"".equals(kiField1.getText()) && kiField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double taui = 0;
      if(!kpField1.getText().equals("")){
        taui = Double.parseDouble(kpField1.getText())/Double.parseDouble(kiField1.getText());
      }
      tauiField1.setText(String.format("%6.1e", taui));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_kiField1KeyPressed1

  private void kiField1KeyPressed2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kiField1KeyPressed2
    try{
    if(!"".equals(kiField1.getText()) && kiField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double taui = 0;
      if(!kpField1.getText().equals("")){
        taui = Double.parseDouble(kpField1.getText())/Double.parseDouble(kiField1.getText());
      }
      tauiField1.setText(String.format("%6.1e", taui));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_kiField1KeyPressed2

  private void taudField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taudField1KeyPressed
    try{
    if(!"".equals(taudField1.getText()) && taudField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double kd = 0;
      if(!kpField1.getText().equals("")){
        kd = Double.parseDouble(kpField1.getText())*Double.parseDouble(taudField1.getText());
      }
      kdField1.setText(String.format("%6.1e", kd));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_taudField1KeyPressed

  private void taudField1KeyPressed1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taudField1KeyPressed1
    try{
    if(!"".equals(taudField1.getText()) && taudField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double kd = 0;
      if(!kpField1.getText().equals("")){
        kd = Double.parseDouble(kpField1.getText())*Double.parseDouble(taudField1.getText());
      }
      kdField1.setText(String.format("%6.1e", kd));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_taudField1KeyPressed1

  private void taudField1KeyPressed2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taudField1KeyPressed2
    try{
    if(!"".equals(taudField1.getText()) && taudField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
      double kd = 0;
      if(!kpField1.getText().equals("")){
        kd = Double.parseDouble(kpField1.getText())*Double.parseDouble(taudField1.getText());
      }
      kdField1.setText(String.format("%6.1e", kd));
    }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_taudField1KeyPressed2

  private void kdField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdField1KeyPressed
    try{
      if(!"".equals(kdField1.getText()) && kdField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
        double taud = Double.MAX_VALUE;
        if(!kpField1.getText().equals("") && Double.parseDouble(kpField1.getText()) != 0){
          taud = Double.parseDouble(kdField1.getText())/Double.parseDouble(kpField1.getText());
        }else if(Double.parseDouble(kdField1.getText())<0){
          taud = Double.MIN_VALUE;
        }
        taudField1.setText(String.format("%6.1e", taud));
      }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_kdField1KeyPressed

  private void kdField1KeyPressed1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdField1KeyPressed1
    try{
      if(!"".equals(kdField1.getText()) && kdField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
        double taud = Double.MAX_VALUE;
        if(!kpField1.getText().equals("") && Double.parseDouble(kpField1.getText()) != 0){
          taud = Double.parseDouble(kdField1.getText())/Double.parseDouble(kpField1.getText());
        }else if(Double.parseDouble(kdField1.getText())<0){
          taud = Double.MIN_VALUE;
        }
        taudField1.setText(String.format("%6.1e", taud));
      }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_kdField1KeyPressed1

  private void kdField1KeyPressed2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdField1KeyPressed2
    try{
      if(!"".equals(kdField1.getText()) && kdField1.getText().matches("^([\\+\\-]?([0-9]*(\\.[0-9])?)+([eE][\\+\\-]?[\\d]+)?)+$")){
        double taud = Double.MAX_VALUE;
        if(!kpField1.getText().equals("") && Double.parseDouble(kpField1.getText()) != 0){
          taud = Double.parseDouble(kdField1.getText())/Double.parseDouble(kpField1.getText());
        }else if(Double.parseDouble(kdField1.getText())<0){
          taud = Double.MIN_VALUE;
        }
        taudField1.setText(String.format("%6.1e", taud));
      }
    }catch(Exception e){
      
    }
  }//GEN-LAST:event_kdField1KeyPressed2

    private void observerTogglerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_observerTogglerMouseClicked
        observerBox.setVisible(!observerBox.isVisible());
    }//GEN-LAST:event_observerTogglerMouseClicked

    private void polo2FieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_polo2FieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_polo2FieldKeyPressed

    private void polo2FieldKeyPressed1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_polo2FieldKeyPressed1
        // TODO add your handling code here:
    }//GEN-LAST:event_polo2FieldKeyPressed1

    private void polo2FieldKeyPressed2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_polo2FieldKeyPressed2
        // TODO add your handling code here:
    }//GEN-LAST:event_polo2FieldKeyPressed2

    private void polo1FieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_polo1FieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_polo1FieldKeyPressed

    private void polo1FieldKeyPressed1(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_polo1FieldKeyPressed1
        // TODO add your handling code here:
    }//GEN-LAST:event_polo1FieldKeyPressed1

    private void polo1FieldKeyPressed2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_polo1FieldKeyPressed2
        // TODO add your handling code here:
    }//GEN-LAST:event_polo1FieldKeyPressed2

  private void applyObserverClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_applyObserverClicked
    System.out.println("Apertei na merdda do botao aplicar dos polos");
    System.out.println("polo1 cheio =" + !polo1Field.getText().equals(""));
    System.out.println("polo2 cheio = "+ !polo2Field.getText().equals(""));
    if(!polo1Field.getText().equals("")&&!polo2Field.getText().equals("")){
      List<Complex64F> poles = observer.getPoles();
      
      Complex64F selectedPole1 = null, selectedPole2 = null, aux;
      boolean refresh = false;
      
      Pattern complexPattern = Pattern.compile("^\\s*([-+]?(?:\\d+|\\d*\\.\\d+))?(?:\\s*([-+](?:\\d+|\\d*\\.\\d+))[ij])?\\s*$");
      Matcher matcher;
      
      matcher = complexPattern.matcher(polo1Field.getText());
      while (matcher.find()) {
        System.out.println("real encontrado = "+matcher.group(1));
        System.out.println("imaginario encontrado ="+matcher.group(2));
        double real = matcher.group(1)!=null?Double.parseDouble(matcher.group(1)):0.0;
        double imag = matcher.group(2)!=null?Double.parseDouble(matcher.group(2)):0.0;
        System.out.println("real 1 = "+real);
        System.out.println("imag 1 = "+imag);
        selectedPole1 = new Complex64F(real, imag);
        if(selectedPole1.getReal()!=(aux = poles.get(0)).getReal() 
              || selectedPole1.getImaginary()!=aux.getImaginary()){
          refresh = true;
        }
      }
      
      matcher = complexPattern.matcher(polo2Field.getText());
      while (matcher.find()) {
        System.out.println("real encontrado 2= "+matcher.group(1));
        System.out.println("imaginario encontrado 2="+matcher.group(2));
        double real = matcher.group(1)!=null?Double.parseDouble(matcher.group(1)):0.0;
        double imag = matcher.group(2)!=null?Double.parseDouble(matcher.group(2)):0.0;
                
        System.out.println("real 2 = "+real);
        System.out.println("imag 2 = "+imag);
        selectedPole2 = new Complex64F(real, imag);
        if(selectedPole2.getReal()!=(aux = poles.get(1)).getReal() 
              || selectedPole2.getImaginary()!=aux.getImaginary()){
          refresh = true;
        }
      }
      
      System.out.println("Polo1 = "+selectedPole1);
      System.out.println("Polo2 = "+selectedPole2);
      
      if(refresh){
        List<Complex64F> refreshedPoles = new ArrayList<>();
        refreshedPoles.add(selectedPole1);
        refreshedPoles.add(selectedPole2);
        observer.setPoles(refreshedPoles);
        
        Matrix L = observer.getL();
        
        System.out.println("Ganho 0 =" + Double.toString(L.get(0, 0)));
        System.out.println("Ganho 1 =" + Double.toString(L.get(1, 0)));
        
        jTableMatrizGanho.getModel().setValueAt(L.get(0, 0), 0, 0);
        jTableMatrizGanho.getModel().setValueAt(L.get(1, 0), 1, 0);
        
      }
      
      
      
    }
    
    if(jTableMatrizGanho.getModel().getValueAt(0, 0)!=null
            && !"".equals(jTableMatrizGanho.getModel().getValueAt(0, 0).toString()) 
            && jTableMatrizGanho.getModel().getValueAt(1, 0)!=null
            && !"".equals(jTableMatrizGanho.getModel().getValueAt(1, 0).toString())){
      double selectedGanho1 = 0, selectedGanho2 = 0;
      boolean refresh = false;
      
      try{
        selectedGanho1 = Double.parseDouble(jTableMatrizGanho.getModel().getValueAt(0, 0).toString());
        selectedGanho2 = Double.parseDouble(jTableMatrizGanho.getModel().getValueAt(1, 0).toString());
        System.out.println("ganho 2 diferente?" + (selectedGanho2 != observer.getL().get(1, 0)));
        System.out.println("ganho 2 diferente?" + (observer.getL().get(1, 0)));
        System.out.println("ganho 2 diferente?" + (selectedGanho2));
        
        if(selectedGanho1 != observer.getL().get(0, 0)) refresh = true;
        selectedGanho2 = Double.parseDouble(jTableMatrizGanho.getModel().getValueAt(1, 0).toString());
        if(selectedGanho2 != observer.getL().get(1, 0)) refresh = true;
      } catch(Exception e){
        e.printStackTrace();
      }
      if(refresh){
        System.out.println("ganho 00000 = "+selectedGanho1);
        System.out.println("ganho 11111 = "+selectedGanho2);

        Matrix L = new Matrix(2, 1);

        L.set(0, 0, selectedGanho1);
        L.set(1, 0, selectedGanho2);

        observer.setL(L);

        List<Complex64F> poles = observer.getPoles();

        Complex64F aux = poles.get(0);
        polo1Field.setText(aux.getReal()+"+"+aux.getImaginary()+"i");

        aux = poles.get(1);
        polo2Field.setText(aux.getReal()+"+"+aux.getImaginary()+"i");
      }
    }
  }//GEN-LAST:event_applyObserverClicked

    private void OCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_OCheckboxItemStateChanged
            if(!OCheckbox.isSelected()){
              graphPanel1.removeSerie(OBSERVER_CURVE_2);
              graphPanel1.removeSerie(OBSERVER_CURVE_1);
              graphPanel1.removeSerie(ERROR_OBSERVER_CURVE_1);
              graphPanel1.removeSerie(ERROR_OBSERVER_CURVE_2);
            }
    }//GEN-LAST:event_OCheckboxItemStateChanged

    private void OCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OCheckboxActionPerformed

  private void takCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_takCheckActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_takCheckActionPerformed

  private void applyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_applyActionPerformed

  private void writeChannelChange(java.awt.event.ItemEvent evt) {
    JRadioButton selected = (JRadioButton) writeChannelChooser.getSelection();
    int selectedValue = Integer.parseInt(selected.getText().replace("A", ""));
    if (selectedValue != currentWriteChannel) {
      call(writeChange, currentWriteChannel = selectedValue);
    }
  }

  /**
   *
   * @return All reading channels selected by User
   */
  private List<JCheckBox> getSelectedInputs() {
    JCheckBox[] inputs = {input0, input1, input2, input3, input4, input5, input6, input7};
    List<JCheckBox> selectedInputs = new ArrayList<>();
    for (JCheckBox input : inputs) {
      if (input.isSelected()) {
        selectedInputs.add(input);
      }
    }
    return selectedInputs;
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox DCheckbox;
  private javax.swing.JCheckBox ICheckbox;
  private javax.swing.JCheckBox OCheckbox;
  private javax.swing.JCheckBox PCheckbox;
  private javax.swing.JLabel PeakTimeField;
  private javax.swing.JLabel PeakTimeLavel;
  private javax.swing.JCheckBox SPCheckbox;
  private javax.swing.JPanel amplitude;
  private javax.swing.JFormattedTextField amplitudeField;
  private javax.swing.JLabel amplitudeLabel;
  private javax.swing.JButton apply;
  private javax.swing.JButton applyObserver;
  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JRadioButton cascadeButton;
  private javax.swing.JRadioButton closedLoop;
  private javax.swing.JPanel closedLoopSettings;
  private javax.swing.JButton connect;
  private javax.swing.JPanel controlSettings;
  private javax.swing.JComboBox controllerTypeCombo;
  private javax.swing.JComboBox controllerTypeCombo1;
  private javax.swing.JLabel controllerTypeLabel;
  private javax.swing.JLabel controllerTypeLabel1;
  private javax.swing.JLabel display1Level;
  private javax.swing.JLabel display1Title;
  private javax.swing.JLabel display2Level;
  private javax.swing.JLabel display2Title;
  private javax.swing.JCheckBox errorCheckbox;
  private javax.swing.JPanel frequency;
  private javax.swing.JFormattedTextField frequencyField;
  private javax.swing.JLabel frequencyLabel;
  private javax.swing.JPanel graphBox;
  private tan.k.view.GraphPanel graphPanel1;
  private tan.k.view.GraphPanel graphPanel2;
  private tan.k.view.GraphPanel graphPanel3;
  private javax.swing.JPanel graphTitle;
  private javax.swing.JLabel graphToggler;
  private javax.swing.JCheckBox input0;
  private javax.swing.JCheckBox input1;
  private javax.swing.JCheckBox input2;
  private javax.swing.JCheckBox input3;
  private javax.swing.JCheckBox input4;
  private javax.swing.JCheckBox input5;
  private javax.swing.JCheckBox input6;
  private javax.swing.JCheckBox input7;
  private javax.swing.JPanel inputColumn0;
  private javax.swing.JPanel inputColumn1;
  private javax.swing.JPanel inputColumn11;
  private javax.swing.JPanel inputColumn2;
  private javax.swing.JPanel inputColumn3;
  private javax.swing.JPanel inputColumn4;
  private javax.swing.JPanel inputColumn5;
  private javax.swing.JPanel inputColumn6;
  private javax.swing.JPanel inputColumn7;
  private javax.swing.JPanel inputTable;
  private javax.swing.JPanel inputTable1;
  private javax.swing.JPanel ipPortBox;
  private javax.swing.JFormattedTextField ipPortField;
  private javax.swing.JLabel ipPortLabel;
  private javax.swing.JPanel ipPortTitle;
  private javax.swing.JLabel ipPortToggler;
  private javax.swing.JPanel ipPortWrapper;
  private javax.swing.JButton jButtonPreview;
  private javax.swing.JButton jButtonStop;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabelCochete1;
  private javax.swing.JLabel jLabelCochete2;
  private javax.swing.JLabel jLabelHertz;
  private javax.swing.JLabel jLabelSeconds;
  private javax.swing.JLabel jLabelVoltage;
  private javax.swing.JLabel jLabelVoltage1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSeparator jSeparator3;
  private javax.swing.JTable jTableMatrizGanho;
  private javax.swing.JTextField kdField;
  private javax.swing.JTextField kdField1;
  private javax.swing.JLabel kdLabel;
  private javax.swing.JLabel kdLabel1;
  private javax.swing.JLabel kdLabel2;
  private javax.swing.JTextField kiField;
  private javax.swing.JTextField kiField1;
  private javax.swing.JLabel kiLabel;
  private javax.swing.JLabel kiLabel1;
  private javax.swing.JTextField kpField;
  private javax.swing.JTextField kpField1;
  private javax.swing.JLabel kpLabel;
  private javax.swing.JLabel kpLabel1;
  private javax.swing.JCheckBox level1Checkbox;
  private javax.swing.JCheckBox level2Checkbox;
  private javax.swing.JPanel loopChooser;
  private javax.swing.ButtonGroup loopTypeChooser;
  private javax.swing.JLabel mpField;
  private javax.swing.JLabel mpLabel;
  private javax.swing.JPanel observerBox;
  private javax.swing.JPanel observerTitle;
  private javax.swing.JLabel observerToggler;
  private javax.swing.JRadioButton openedLoop;
  private javax.swing.JPanel openedLoopSettings;
  private javax.swing.JRadioButton output0;
  private javax.swing.JRadioButton output1;
  private javax.swing.JRadioButton output2;
  private javax.swing.JRadioButton output3;
  private javax.swing.JRadioButton output4;
  private javax.swing.JRadioButton output5;
  private javax.swing.JRadioButton output6;
  private javax.swing.JRadioButton output7;
  private javax.swing.JPanel outputColumn0;
  private javax.swing.JPanel outputColumn1;
  private javax.swing.JPanel outputColumn2;
  private javax.swing.JPanel outputColumn3;
  private java.awt.Canvas outputPreview;
  private javax.swing.JPanel outputSettings;
  private javax.swing.JPanel outputTable;
  private javax.swing.JPanel parameters2loop;
  private javax.swing.JPanel period;
  private javax.swing.JFormattedTextField periodField;
  private javax.swing.JLabel periodLabel;
  private javax.swing.JTextField polo1Field;
  private javax.swing.JLabel polo1Label;
  private javax.swing.JTextField polo2Field;
  private javax.swing.JLabel polo2Label;
  private javax.swing.JComboBox processVariableField;
  private javax.swing.JLabel processVariableLabel;
  private javax.swing.JToggleButton random;
  private javax.swing.JPanel readBox;
  private javax.swing.JPanel readTitle;
  private javax.swing.JLabel readToggler;
  private javax.swing.JLabel riseTimeField;
  private javax.swing.JLabel riseTimeLabel;
  private javax.swing.JToggleButton sawtooth;
  private java.awt.ScrollPane scrollPane1;
  private javax.swing.JPanel setPoint;
  private javax.swing.JFormattedTextField setPointField;
  private javax.swing.JLabel setPointLabel;
  private javax.swing.JLabel settingTimeLabel;
  private javax.swing.JLabel settlingTimeField;
  private javax.swing.JPanel sidebar;
  private javax.swing.JRadioButton simpleButton;
  private javax.swing.JToggleButton sinus;
  private javax.swing.JToggleButton square;
  private javax.swing.JLabel statusConnectedLabel;
  private javax.swing.JLabel statusLockLabel;
  private javax.swing.JToggleButton step;
  private javax.swing.JCheckBox takCheck;
  private javax.swing.JTextField takField;
  private javax.swing.JTextField taudField;
  private javax.swing.JTextField taudField1;
  private javax.swing.JLabel taudLabel;
  private javax.swing.JLabel taudLabel1;
  private javax.swing.JTextField tauiField;
  private javax.swing.JTextField tauiField1;
  private javax.swing.JLabel tauiLabel;
  private javax.swing.JLabel tauiLabel1;
  private javax.swing.JCheckBox uCheckbox;
  private javax.swing.JCheckBox u_barCheckbox;
  private javax.swing.JPanel waveParams;
  private javax.swing.JPanel waveParams1;
  private javax.swing.JPanel waveType;
  private javax.swing.ButtonGroup waveTypeChooser;
  private javax.swing.JPanel writeBox;
  private javax.swing.ButtonGroup writeChannelChooser;
  private javax.swing.JPanel writeTitle;
  private javax.swing.JLabel writeToggler;
  // End of variables declaration//GEN-END:variables

  /**
   * @param setPointChange the setPointChange to set
   */
  public void onSetPointChange(ChangeParameterEvent setPointChange) {
    this.setPointChange = setPointChange;
  }

  /**
   * @param processVariableChange the processVariableChange to set
   */
  public void onProcessVariableChange(ChangeParameterEvent processVariableChange) {
    this.processVariableChange = processVariableChange;
  }

  /**
   * @param amplitudeChange the amplitudeChange to set
   */
  public void onAmplitudeChange(ChangeParameterEvent amplitudeChange) {
    this.amplitudeChange = amplitudeChange;
  }

  /**
   * @param periodChange the periodChange to set
   */
  public void onPeriodChange(ChangeParameterEvent periodChange) {
    this.periodChange = periodChange;
  }

  /**
   * @param waveChange the waveChange to set
   */
  public void onWaveChange(ChangeParameterEvent waveChange) {
    this.waveChange = waveChange;
  }

  /**
   * @param loopChange the loopChange to set
   */
  public void onLoopChange(ChangeParameterEvent loopChange) {
    this.loopChange = loopChange;
  }

  /**
   * @param ipChange the ipChange to set
   */
  public void onIpChange(ChangeParameterEvent ipChange) {
    this.ipChange = ipChange;
  }

  /**
   * @param portChange the portChange to set
   */
  public void onPortChange(ChangeParameterEvent portChange) {
    this.portChange = portChange;
  }

  /**
   * @return the currentAmplitide
   */
  public double getCurrentAmplitide() {
    return currentAmplitide;
  }

  /**
   * @return the currentPeriod
   */
  public double getCurrentPeriod() {
    return currentPeriod;
  }

  /**
   * @return the currentWave
   */
  public Wave getCurrentWave() {
    return currentWave;
  }

  /**
   * @return the currentSetPoint
   */
  public double getCurrentSetPoint() {
    return currentSetPoint;
  }

  /**
   * @return the currentPV
   */
  public TankTag getCurrentPV() {
    return currentPV;
  }

  /**
   * @return the currentLoop
   */
  public Loop getCurrentLoop() {
    return currentLoop;
  }

  /**
   * @return the currentIp
   */
  public String getCurrentIp() {
    return currentIp;
  }

  /**
   * @return the currentPort
   */
  public int getCurrentPort() {
    return currentPort;
  }

  /**
   * @return the openLoopSettings configurated by user
   */
  public OpenLoopSettings getOpenLoopSettings() {
    return new OpenLoopSettings(getCurrentWave(), getCurrentAmplitide(), getCurrentPeriod());
  }

  public CloseLoopSettings getCloseLoopSettings() {
    return new CloseLoopSettings(getCurrentSetPoint(), getCurrentPV(), getCurrentControllerType(), getCurrentKp(), getCurrentKi(), getCurrentKd());
  }

  /**
   * @param writeChange the writeChange to set
   */
  public void onWriteChange(ChangeParameterEvent writeChange) {
    this.writeChange = writeChange;
  }

  public void onApplyClicked(ChangeParameterEvent applyClicked) {
    this.applyClicked = applyClicked;
  }

  public void addPointToGraph(String sinal, Millisecond t, double d) {
    //System.out.println(t + " " + d + ";");
    if(!enabledCurves.contains(sinal)) return;
    switch (sinal) {
      case TANK1_LEVEL_CURVE:
          display1Level.setText(Math.round(d) + "cm");
          graphPanel1.addValue(sinal, t, d);
          break;
      case TANK2_LEVEL_CURVE:
          display2Level.setText(Math.round(d) + "cm");
          graphPanel1.addValue(sinal, t, d);
          break;
      case OBSERVER_CURVE_1:
      case OBSERVER_CURVE_2:
      case ERROR_OBSERVER_CURVE_1:
      case ERROR_OBSERVER_CURVE_2:
        System.out.println("Add Value to "+sinal);
        graphPanel1.addValue(sinal, t, d);
        break;
      case SETPOINT_CURVE:
      case ERROR_CURVE:
        graphPanel1.addValue(sinal, t, d);
        break;
      default:
        graphPanel2.addValue(sinal, t, d);
        break;
    }
  }

  public void onConnectedClicked(ChangeParameterEvent changeParameterEvent) {
    this.connectClicked = changeParameterEvent;
  }

  public void setConnectionStatus(ConnectionStatus connectionStatus) {
    System.out.println(connectionStatus.name());
    if (ConnectionStatus.CONNECTED.equals(connectionStatus)) {
      apply.setEnabled(true);
      statusConnectedLabel.setText("Conectado");
      statusConnectedLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/ball_green.png")));
    } else {
      apply.setEnabled(false);
      statusConnectedLabel.setText("Não Conectado");
      statusConnectedLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/ball_red.png")));
    }
  }

  public void onStopClicked(ChangeParameterEvent changeParameterEvent) {
    this.stopClicked = changeParameterEvent;
  }

  /**
   * @return the currentKp
   */
  public double getCurrentKp() {
    return currentKp;
  }

  /**
   * @param currentKp the currentKp to set
   */
  public void setCurrentKp(double currentKp) {
    this.currentKp = currentKp;
  }

  /**
   * @return the currentKi
   */
  public double getCurrentKi() {
    return currentKi;
  }

  /**
   * @param currentKi the currentKi to set
   */
  public void setCurrentKi(double currentKi) {
    this.currentKi = currentKi;
  }

  /**
   * @return the currentKd
   */
  public double getCurrentKd() {
    return currentKd;
  }

  /**
   * @param currentKd the currentKd to set
   */
  public void setCurrentKd(double currentKd) {
    this.currentKd = currentKd;
  }

  /**
   * @return the currentControllerType
   */
  public ControllerType getCurrentControllerType() {
    return currentControllerType;
  }

  /**
   * @param currentControllerType the currentControllerType to set
   */
  public void setCurrentControllerType(ControllerType currentControllerType) {
    this.currentControllerType = currentControllerType;
  }

  public void onControllerTypeChange(ChangeParameterEvent changeParameterEvent) {
    this.controllerTypeChange = changeParameterEvent;
  }

  public void onKpChange(ChangeParameterEvent changeParameterEvent) {
    this.kpChange = changeParameterEvent;
  }

  public void onKiChange(ChangeParameterEvent changeParameterEvent) {
    this.kiChange = changeParameterEvent;
  }

  public void onKdChange(ChangeParameterEvent changeParameterEvent) {
    this.kdChange = changeParameterEvent;
  }

  private void call(ChangeParameterEvent callback, Object param) {
    if (null != callback) {
      callback.onChangeParameter(param);
    }
  }

  private void call(ChangeParameterEvent callback) {
    call(callback, null);
  }

  public void setLockStatus(boolean b) {
    statusLockLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tan/k/resource/ball_"+(b?"red":"stroke")+".png")));
  }

  /**
   * @return the u
   */
  public boolean isU() {
    return uCheckbox.isSelected();
  }

  /**
   * @return the u_bar
   */
  public boolean isU_bar() {
    return u_barCheckbox.isSelected();
  }

  /**
   * @return the erro
   */
  public boolean isErro() {
    return errorCheckbox.isSelected();
  }

  /**
   * @return the setpoint
   */
  public boolean isSetpoint() {
    return SPCheckbox.isSelected();
  }

  /**
   * @return the nivel1
   */
  public boolean isNivel1() {
    return level1Checkbox.isSelected();
  }

  /**
   * @return the nivel2
   */
  public boolean isNivel2() {
    return level2Checkbox.isSelected();
  }

  /**
   * @return the p
   */
  public boolean isP() {
    return PCheckbox.isSelected();
  }

  /**
   * @return the d
   */
  public boolean isD() {
    return DCheckbox.isSelected();
  }
  
    public boolean isO() {
    return OCheckbox.isSelected();
  }

  /**
   * @return the i
   */
  public boolean isI() {
    return ICheckbox.isSelected();
  }

  public void setPeakTime(double peakTime) {
    PeakTimeField.setText(Double.toString(peakTime));
  }

  public void setMP(double mp) {
    DecimalFormat f = new DecimalFormat("#.##%");
    mpField.setText(f.format(mp));
  }

  public void setSettlingTime(double settlingTime) {
    settlingTimeField.setText(Double.toString(settlingTime));
  }

  public void onCascadeChange(ChangeParameterEvent changeParameterEvent) {
    this.cascadeChange = changeParameterEvent;
  }

  public void onKp1Change(ChangeParameterEvent changeParameterEvent) {
    this.kp1Change = changeParameterEvent;
  }

  public void onKi1Change(ChangeParameterEvent changeParameterEvent) {
    this.ki1Change = changeParameterEvent;
  }

  public void onKd1Change(ChangeParameterEvent changeParameterEvent) {
    this.kd1Change = changeParameterEvent;
  }

  public void onControllerTypeChange1(ChangeParameterEvent changeParameterEvent) {
    this.controllerTypeChange1 = changeParameterEvent;
  }

  /**
   * @return the observer
   */
  public Observer getObserver() {
    return observer;
  }

  /**
   * @param observer the observer to set
   */
  public void setObserver(Observer observer) {
    this.observer = observer;
  }

  public void onTakChange(ChangeParameterEvent changeParameterEvent) {
    TakChange = changeParameterEvent;
  }

  public void setRisingTime(double risingTime) {
    this.riseTimeField.setText(Double.toString(risingTime));
  }

  //Classes and enums
  private class ComboItem {

    private Object value;
    private String label;

    public ComboItem(Object value, String label) {
      this.value = value;
      this.label = label;
    }

    public Object getValue() {
      return this.value;
    }

    public String getLabel() {
      return this.label;
    }

    @Override
    public String toString() {
      return label;
    }
  }

  public enum ConnectionStatus {

    CONNECTED, DISCONNECTED, CONNECTING
  }
  
  public static final String TANK1_LEVEL_CURVE = "Nível do tanque 1";
  public static final String TANK2_LEVEL_CURVE = "Nível do tanque 2";
  public static final String ERROR_CURVE = "Erro";
  public static final String SETPOINT_CURVE = "Set Point";
  
  public static final String SENDED_SIGNAL_CURVE = "Sinal Enviado";
  public static final String CALCULATED_SIGNAL_CURVE = "Sinal calculado";
  
  public static final String PROPORCIONAL_PART_CURVE = "Componente proporcional";
  public static final String INTEGRAL_PART_CURVE = "Componente integral";
  public static final String DERIVATIVE_PART_CURVE = "Componente derivativa";

  public static final String PROPORCIONAL_PART_CURVE_1 = "Componente proporcional 2";
  public static final String INTEGRAL_PART_CURVE_1 = "Componente integral 2";
  public static final String DERIVATIVE_PART_CURVE_1 = "Componente derivativa 2";
  
  public static final String OBSERVER_CURVE_1 = "Saida observada 1";
  public static final String OBSERVER_CURVE_2 = "Saida observada 2";
  
  public static final String ERROR_OBSERVER_CURVE_1 = "Erro observador 1";
  public static final String ERROR_OBSERVER_CURVE_2 = "Erro observador 2";
  
  public static class DoubleFilter extends DocumentFilter {
    
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
      StringBuilder sb = new StringBuilder();
      sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
      sb.insert(offset, text);
      if (!containsOnlyNumbers(sb.toString())) {
        return;
      }
      fb.insertString(offset, text, attr);
    }
    
    
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
      StringBuilder sb = new StringBuilder();
      sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
      sb.replace(offset, offset + length, text);
      if (!containsOnlyNumbers(sb.toString())) {
        return;
      }
      fb.replace(offset, length, text, attr);
    }

    /**
     * This method checks if a String contains only numbers
     */
    public boolean containsOnlyNumbers(String text) {
      return text.matches("^[\\+\\-]?[0-9]*(\\.[0-9]*)?([eE][\\+\\-]?[\\d]*)?$");
    }
  }
}
