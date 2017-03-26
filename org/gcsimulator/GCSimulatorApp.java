package org.gcsimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Vector;
import javax.help.CSH.DisplayHelpAfterTracking;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.gcsimulator.panels.ColumnProperties;
import org.gcsimulator.panels.InletOutlet;
import org.gcsimulator.panels.IsothermalOptions;
import org.gcsimulator.panels.OvenOptions;
import org.gcsimulator.panels.PlotOptions;
import org.gcsimulator.panels.TemperatureProgramOptions;
import org.gcsimulator.panels.TemperatureProgramOptions.SpecialTableModel2;
import org.jdesktop.swingx.JXTaskPane;

public class GCSimulatorApp
  extends JFrame
  implements ActionListener, ChangeListener, KeyListener, FocusListener, ListSelectionListener, AutoScaleListener, TableModelListener, TemperatureProgramListener
{
  private static final long serialVersionUID = 1L;
/*  206 */   private boolean m_bSliderUpdate = true;
  
/*  208 */   TopPanel contentPane = null;
/*  209 */   public JScrollPane jMainScrollPane = null;
/*  210 */   public int m_iSecondPlotType = 0;
/*  211 */   public int m_iCarrierGas = 1;
/*  212 */   public double m_dTimeConstant = 0.5D;
/*  213 */   public double m_dTemperature = 200.0D;
/*  214 */   public double m_dSignalOffset = 30.0D;
/*  215 */   public double m_dNoise = 3.0D;
/*  216 */   public double m_dStartTime = 0.0D;
/*  217 */   public double m_dEndTime = 0.0D;
/*  218 */   public int m_iNumPoints = 3000;
/*  219 */   public double m_dFlowRate = 1.0D;
/*  220 */   public double m_dInletPressure = 100.0D;
/*  221 */   public double m_dOutletPressure = 1.0E-5D;
/*  222 */   public double m_dSplitRatio = 10.0D;
/*  223 */   public double m_dLinerLength = 0.04D;
/*  224 */   public double m_dLinerInnerDiameter = 0.002D;
/*  225 */   public double m_dLinerVolume = 0.0D;
/*  226 */   public double m_dInjectionVolume = 1.0D;
/*  227 */   public double m_dColumnLength = 30.0D;
/*  228 */   public double m_dColumnDiameter = 0.25D;
/*  229 */   public double m_dFilmThickness = 0.25D;
/*  230 */   public double m_dInitialTemperature = 60.0D;
/*  231 */   public double m_dInitialTime = 1.0D;
/*  232 */   public boolean m_bTemperatureProgramMode = false;
/*  233 */   public boolean m_bConstantFlowRateMode = true;
/*  234 */   public boolean m_bColumnOutletAtVacuum = true;
/*  235 */   public boolean m_bSplitInjectionMode = true;
  public double m_dHoldUpVolume;
  public double m_dHoldUpTime;
/*  238 */   public double m_dDiffusionCoefficient = 1.0E-5D;
  public double m_dReducedFlowVelocity;
  public double m_dReducedPlateHeight;
  public double m_dTheoreticalPlates;
  public double m_dHETP;
/*  243 */   public Vector<Compound> m_vectCompound = new Vector();
  public double[][] m_dTemperatureProgramArray;
/*  245 */   public LinearInterpolationFunction m_lifTemperatureProgram = null;
/*  246 */   public int m_iChromatogramPlotIndex = -1;
/*  247 */   public int m_iSinglePlotIndex = -1;
/*  248 */   public int m_iSecondPlotIndex = -1;
  public Vector<double[]> m_vectRetentionFactorArray;
  public Vector<double[]> m_vectPositionArray;
/*  251 */   public double m_dSelectedIsocraticRetentionFactor = 0.0D;
/*  252 */   public int m_iStationaryPhase = 0;
/*  253 */   public boolean m_bAutomaticTimeRange = true;
  public InterpolationFunction[] InterpolatedIsothermalData;
/*  255 */   public double m_dPlotFlowVelocityPosition = 0.0D;
/*  256 */   public double m_dPlotGasDensityPosition = 0.0D;
/*  257 */   public double m_dPlotPressurePosition = 0.0D;
/*  258 */   private boolean m_bDoNotMessage = false;
  

/*  261 */   JMenuItem menuLoadSettingsAction = new JMenuItem("Load Settings");
/*  262 */   JMenuItem menuSaveSettingsAction = new JMenuItem("Save Settings");
/*  263 */   JMenuItem menuSaveSettingsAsAction = new JMenuItem("Save Settings As...");
/*  264 */   JMenuItem menuResetToDefaultValuesAction = new JMenuItem("Reset To Default Settings");
/*  265 */   JMenuItem menuExitAction = new JMenuItem("Exit");
  
/*  267 */   JMenuItem menuHelpTopicsAction = new JMenuItem("Help Topics");
/*  268 */   JMenuItem menuAboutAction = new JMenuItem("About GC Simulator");
  
/*  270 */   File m_currentFile = null;
/*  271 */   boolean m_bDocumentChangedFlag = false;
  
  public class JFileChooser2 extends JFileChooser
  {
    public JFileChooser2() {}
    
    public void approveSelection() {
/*  278 */       File f = getSelectedFile();
/*  279 */       if ((f.exists()) && (getDialogType() == 1))
      {
/*  281 */         int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", 1);
/*  282 */         switch (result) {
        case 0: 
/*  284 */           super.approveSelection();
/*  285 */           return;
        case 1: 
/*  287 */           return;
        case 2: 
/*  289 */           cancelSelection();
/*  290 */           return;
        }
      }
/*  293 */       super.approveSelection();
    }
  }
  




  public static void main(String[] args)
  {
/*  303 */     SwingUtilities.invokeLater(new Runnable()
    {
      public void run() {}
    });
  }
  



  private static void createAndShowGUI()
  {
    try
    {
      UIManager.LookAndFeelInfo[] arrayOfLookAndFeelInfo;
      

/*  319 */       int j = (arrayOfLookAndFeelInfo = UIManager.getInstalledLookAndFeels()).length; for (int i = 0; i < j; i++) { UIManager.LookAndFeelInfo info = arrayOfLookAndFeelInfo[i];
/*  320 */         if ("Nimbus".equals(info.getName())) {
/*  321 */           UIManager.setLookAndFeel(info.getClassName());
/*  322 */           break;
        }
      }
    }
    catch (Exception localException) {}
    






/*  334 */     JFrame.setDefaultLookAndFeelDecorated(true);
/*  335 */     JDialog.setDefaultLookAndFeelDecorated(true);
    

/*  338 */     GCSimulatorApp frame = new GCSimulatorApp("GC Simulator");
    

/*  341 */     Toolkit kit = Toolkit.getDefaultToolkit();
/*  342 */     Image img = kit.createImage(frame.getClass().getResource("/org/gcsimulator/images/icon.png"));
/*  343 */     frame.setIconImage(img);
    
/*  345 */     frame.setDefaultCloseOperation(3);
    

/*  348 */     frame.init();
    

/*  351 */     frame.pack();
/*  352 */     frame.setLocationRelativeTo(null);
/*  353 */     frame.setVisible(true);
  }
  



  public GCSimulatorApp(String str)
  {
/*  361 */     super(str);
    
/*  363 */     setPreferredSize(new Dimension(1000, 700));
  }
  






  public void init()
  {
/*  374 */     String helpHS = "org/gcsimulator/help/HPLCSimHelp.hs";
/*  375 */     ClassLoader cl = TopPanel.class.getClassLoader();
    try {
/*  377 */       URL hsURL = HelpSet.findHelpSet(cl, helpHS);
/*  378 */       Globals.hsMainHelpSet = new HelpSet(null, hsURL);
    } catch (Exception ee) {
/*  380 */       System.out.println("HelpSet " + ee.getMessage());
/*  381 */       System.out.println("HelpSet " + helpHS + " not found");
/*  382 */       return;
    }
/*  384 */     Globals.hbMainHelpBroker = Globals.hsMainHelpSet.createHelpBroker();
    



    try
    {
/*  391 */       createGUI();
    }
    catch (Exception e)
    {
/*  395 */       System.err.println("createGUI didn't complete successfully");
/*  396 */       System.err.println(e.getMessage());
/*  397 */       System.err.println(e.getLocalizedMessage());
/*  398 */       System.err.println(e.toString());
/*  399 */       System.err.println(e.getStackTrace());
/*  400 */       System.err.println(e.getCause());
    }
    
/*  403 */     loadDefaultsForStationaryPhase(0);
/*  404 */     loadControlsWithStoredValues();
/*  405 */     calculateTemperatureProgram();
/*  406 */     performCalculations();
  }
  

  private void createGUI()
  {
/*  412 */     JMenuBar menuBar = new JMenuBar();
    

/*  415 */     setJMenuBar(menuBar);
    

/*  418 */     JMenu fileMenu = new JMenu("File");
/*  419 */     JMenu helpMenu = new JMenu("Help");
    
/*  421 */     menuBar.add(fileMenu);
/*  422 */     menuBar.add(helpMenu);
    

/*  425 */     this.menuLoadSettingsAction.addActionListener(this);
/*  426 */     this.menuSaveSettingsAction.addActionListener(this);
/*  427 */     this.menuSaveSettingsAsAction.addActionListener(this);
/*  428 */     this.menuResetToDefaultValuesAction.addActionListener(this);
/*  429 */     this.menuExitAction.addActionListener(this);
    
/*  431 */     this.menuHelpTopicsAction.addActionListener(this);
/*  432 */     this.menuAboutAction.addActionListener(this);
    
/*  434 */     fileMenu.add(this.menuLoadSettingsAction);
/*  435 */     fileMenu.add(this.menuSaveSettingsAction);
/*  436 */     fileMenu.add(this.menuSaveSettingsAsAction);
/*  437 */     fileMenu.addSeparator();
/*  438 */     fileMenu.add(this.menuResetToDefaultValuesAction);
/*  439 */     fileMenu.addSeparator();
/*  440 */     fileMenu.add(this.menuExitAction);
    
/*  442 */     this.menuHelpTopicsAction.setEnabled(false);
/*  443 */     helpMenu.add(this.menuHelpTopicsAction);
/*  444 */     helpMenu.addSeparator();
/*  445 */     helpMenu.add(this.menuAboutAction);
    

/*  448 */     this.jMainScrollPane = new JScrollPane();
/*  449 */     this.jMainScrollPane.setHorizontalScrollBarPolicy(30);
/*  450 */     this.jMainScrollPane.setVerticalScrollBarPolicy(20);
    
/*  452 */     this.contentPane = new TopPanel();
/*  453 */     this.contentPane.setOpaque(true);
/*  454 */     this.jMainScrollPane.setViewportView(this.contentPane);
/*  455 */     setContentPane(this.jMainScrollPane);
/*  456 */     this.jMainScrollPane.revalidate();
    
/*  458 */     this.contentPane.jbtnAddChemical.addActionListener(this);
/*  459 */     this.contentPane.jbtnEditChemical.addActionListener(this);
/*  460 */     this.contentPane.jbtnRemoveChemical.addActionListener(this);
    
/*  462 */     this.contentPane.jxpanelPlotOptions.jrdoNoPlot.addActionListener(this);
/*  463 */     this.contentPane.jxpanelPlotOptions.jrdoTemperature.addActionListener(this);
/*  464 */     this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.addActionListener(this);
/*  465 */     this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.addActionListener(this);
/*  466 */     this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.addActionListener(this);
/*  467 */     this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.addChangeListener(this);
/*  468 */     this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.addFocusListener(this);
/*  469 */     this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.addKeyListener(this);
/*  470 */     this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.addActionListener(this);
/*  471 */     this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.addChangeListener(this);
/*  472 */     this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.addFocusListener(this);
/*  473 */     this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.addKeyListener(this);
/*  474 */     this.contentPane.jxpanelPlotOptions.jrdoPressure.addActionListener(this);
/*  475 */     this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.addChangeListener(this);
/*  476 */     this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.addFocusListener(this);
/*  477 */     this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.addKeyListener(this);
/*  478 */     this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.addActionListener(this);
/*  479 */     this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.addActionListener(this);
/*  480 */     this.contentPane.jxpanelPlotOptions.jrdoPosition.addActionListener(this);
/*  481 */     this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.addActionListener(this);
/*  482 */     this.contentPane.jxpanelPlotOptions.jtxtTimeConstant.addKeyListener(this);
/*  483 */     this.contentPane.jxpanelPlotOptions.jtxtTimeConstant.addFocusListener(this);
/*  484 */     this.contentPane.jxpanelPlotOptions.jtxtSignalOffset.addKeyListener(this);
/*  485 */     this.contentPane.jxpanelPlotOptions.jtxtSignalOffset.addFocusListener(this);
/*  486 */     this.contentPane.jxpanelPlotOptions.jtxtNoise.addKeyListener(this);
/*  487 */     this.contentPane.jxpanelPlotOptions.jtxtNoise.addFocusListener(this);
/*  488 */     this.contentPane.jxpanelPlotOptions.jtxtInitialTime.addKeyListener(this);
/*  489 */     this.contentPane.jxpanelPlotOptions.jtxtInitialTime.addFocusListener(this);
/*  490 */     this.contentPane.jxpanelPlotOptions.jtxtFinalTime.addKeyListener(this);
/*  491 */     this.contentPane.jxpanelPlotOptions.jtxtFinalTime.addFocusListener(this);
/*  492 */     this.contentPane.jxpanelPlotOptions.jtxtNumPoints.addKeyListener(this);
/*  493 */     this.contentPane.jxpanelPlotOptions.jtxtNumPoints.addFocusListener(this);
/*  494 */     this.contentPane.jxpanelPlotOptions.jchkAutoTimeRange.addActionListener(this);
    
/*  496 */     this.contentPane.jxpanelInletOutlet.jcboCarrierGas.addActionListener(this);
/*  497 */     this.contentPane.jxpanelInletOutlet.jrdoConstantFlowRate.addActionListener(this);
/*  498 */     this.contentPane.jxpanelInletOutlet.jrdoConstantPressure.addActionListener(this);
/*  499 */     this.contentPane.jxpanelInletOutlet.jtxtFlowRate.addKeyListener(this);
/*  500 */     this.contentPane.jxpanelInletOutlet.jtxtFlowRate.addFocusListener(this);
/*  501 */     this.contentPane.jxpanelInletOutlet.jtxtInletPressure.addKeyListener(this);
/*  502 */     this.contentPane.jxpanelInletOutlet.jtxtInletPressure.addFocusListener(this);
/*  503 */     this.contentPane.jxpanelInletOutlet.jrdoVacuum.addActionListener(this);
/*  504 */     this.contentPane.jxpanelInletOutlet.jrdoOtherPressure.addActionListener(this);
/*  505 */     this.contentPane.jxpanelInletOutlet.jtxtOtherPressure.addKeyListener(this);
/*  506 */     this.contentPane.jxpanelInletOutlet.jtxtOtherPressure.addFocusListener(this);
/*  507 */     this.contentPane.jxpanelInletOutlet.jrdoSplitless.addActionListener(this);
/*  508 */     this.contentPane.jxpanelInletOutlet.jrdoSplit.addActionListener(this);
/*  509 */     this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.addKeyListener(this);
/*  510 */     this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.addFocusListener(this);
/*  511 */     this.contentPane.jxpanelInletOutlet.jtxtLinerLength.addKeyListener(this);
/*  512 */     this.contentPane.jxpanelInletOutlet.jtxtLinerLength.addFocusListener(this);
/*  513 */     this.contentPane.jxpanelInletOutlet.jtxtLinerInnerDiameter.addKeyListener(this);
/*  514 */     this.contentPane.jxpanelInletOutlet.jtxtLinerInnerDiameter.addFocusListener(this);
/*  515 */     this.contentPane.jxpanelInletOutlet.jtxtInjectionVolume.addKeyListener(this);
/*  516 */     this.contentPane.jxpanelInletOutlet.jtxtInjectionVolume.addFocusListener(this);
/*  517 */     this.contentPane.jxpanelInletOutlet.jcboInletPressureUnits.addActionListener(this);
    
/*  519 */     this.contentPane.jxpanelColumnProperties.jtxtColumnLength.addKeyListener(this);
/*  520 */     this.contentPane.jxpanelColumnProperties.jtxtColumnLength.addFocusListener(this);
/*  521 */     this.contentPane.jxpanelColumnProperties.jtxtColumnDiameter.addKeyListener(this);
/*  522 */     this.contentPane.jxpanelColumnProperties.jtxtColumnDiameter.addFocusListener(this);
/*  523 */     this.contentPane.jxpanelColumnProperties.jtxtFilmThickness.addKeyListener(this);
/*  524 */     this.contentPane.jxpanelColumnProperties.jtxtFilmThickness.addFocusListener(this);
/*  525 */     this.contentPane.jxpanelColumnProperties.jcboStationaryPhase.addActionListener(this);
    
/*  527 */     this.contentPane.jxpanelIsothermalOptions.jsliderTemperature.addChangeListener(this);
/*  528 */     this.contentPane.jxpanelIsothermalOptions.jtxtTemperature.addKeyListener(this);
/*  529 */     this.contentPane.jxpanelIsothermalOptions.jtxtTemperature.addFocusListener(this);
    
/*  531 */     this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTemperature.addKeyListener(this);
/*  532 */     this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTemperature.addFocusListener(this);
/*  533 */     this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTime.addKeyListener(this);
/*  534 */     this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTime.addFocusListener(this);
    
/*  536 */     this.contentPane.jxpanelTemperatureProgramOptions.addListener(this);
    
/*  538 */     this.contentPane.jxpanelOvenOptions.jrdoIsothermalElution.addActionListener(this);
/*  539 */     this.contentPane.jxpanelOvenOptions.jrdoProgrammedTemperatureElution.addActionListener(this);
    
/*  541 */     this.contentPane.jtableChemicals.getSelectionModel().addListSelectionListener(this);
/*  542 */     this.contentPane.jbtnPan.addActionListener(this);
/*  543 */     this.contentPane.jbtnZoomIn.addActionListener(this);
/*  544 */     this.contentPane.jbtnZoomOut.addActionListener(this);
/*  545 */     this.contentPane.jbtnAutoscale.addActionListener(this);
/*  546 */     this.contentPane.jbtnAutoscaleX.addActionListener(this);
/*  547 */     this.contentPane.jbtnAutoscaleY.addActionListener(this);
/*  548 */     this.contentPane.jbtnHelp.addActionListener(this);
/*  549 */     this.contentPane.jbtnTutorials.addActionListener(this);
/*  550 */     this.contentPane.jbtnCopyImage.addActionListener(this);
/*  551 */     this.contentPane.m_GraphControl.addAutoScaleListener(this);
/*  552 */     this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(false));
/*  553 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(0.0D, 100.0D);
/*  554 */     this.contentPane.jbtnContextHelp.addActionListener(new CSH.DisplayHelpAfterTracking(Globals.hbMainHelpBroker));
  }
  
  private void validateTimeConstant()
  {
/*  559 */     if (this.contentPane.jxpanelPlotOptions.jtxtTimeConstant.getText().length() == 0) {
/*  560 */       this.contentPane.jxpanelPlotOptions.jtxtTimeConstant.setText("0");
    }
/*  562 */     double dTemp = Float.valueOf(this.contentPane.jxpanelPlotOptions.jtxtTimeConstant.getText()).floatValue();
    
/*  564 */     if (dTemp < 1.0E-6D)
/*  565 */       dTemp = 1.0E-6D;
/*  566 */     if (dTemp > 999999.0D) {
/*  567 */       dTemp = 999999.0D;
    }
/*  569 */     this.m_dTimeConstant = dTemp;
/*  570 */     this.contentPane.jxpanelPlotOptions.jtxtTimeConstant.setText(Float.toString((float)this.m_dTimeConstant));
  }
  
  private void validateSignalOffset()
  {
/*  575 */     if (this.contentPane.jxpanelPlotOptions.jtxtSignalOffset.getText().length() == 0) {
/*  576 */       this.contentPane.jxpanelPlotOptions.jtxtSignalOffset.setText("0");
    }
/*  578 */     double dTemp = Float.valueOf(this.contentPane.jxpanelPlotOptions.jtxtSignalOffset.getText()).floatValue();
    
/*  580 */     if (dTemp < 0.0D)
/*  581 */       dTemp = 0.0D;
/*  582 */     if (dTemp > 999999.0D) {
/*  583 */       dTemp = 999999.0D;
    }
/*  585 */     this.m_dSignalOffset = dTemp;
/*  586 */     this.contentPane.jxpanelPlotOptions.jtxtSignalOffset.setText(Float.toString((float)this.m_dSignalOffset));
  }
  
  private void validateNoise()
  {
/*  591 */     if (this.contentPane.jxpanelPlotOptions.jtxtNoise.getText().length() == 0) {
/*  592 */       this.contentPane.jxpanelPlotOptions.jtxtNoise.setText("0");
    }
/*  594 */     double dTemp = Float.valueOf(this.contentPane.jxpanelPlotOptions.jtxtNoise.getText()).floatValue();
    
/*  596 */     if (dTemp < 1.0E-6D)
/*  597 */       dTemp = 1.0E-6D;
/*  598 */     if (dTemp > 999999.0D) {
/*  599 */       dTemp = 999999.0D;
    }
/*  601 */     this.m_dNoise = dTemp;
/*  602 */     this.contentPane.jxpanelPlotOptions.jtxtNoise.setText(Float.toString((float)this.m_dNoise));
  }
  
  private void validateStartTime()
  {
/*  607 */     if (this.contentPane.jxpanelPlotOptions.jtxtInitialTime.getText().length() == 0) {
/*  608 */       this.contentPane.jxpanelPlotOptions.jtxtInitialTime.setText("0");
    }
/*  610 */     double dTemp = Float.valueOf(this.contentPane.jxpanelPlotOptions.jtxtInitialTime.getText()).floatValue();
    
/*  612 */     if (dTemp < 0.0D)
/*  613 */       dTemp = 0.0D;
/*  614 */     if (dTemp > this.m_dEndTime) {
/*  615 */       dTemp = this.m_dEndTime - 1.0E-6D;
    }
/*  617 */     this.m_dStartTime = dTemp;
/*  618 */     this.contentPane.jxpanelPlotOptions.jtxtInitialTime.setText(Float.toString((float)this.m_dStartTime));
  }
  
  private void validateEndTime()
  {
/*  623 */     if (this.contentPane.jxpanelPlotOptions.jtxtFinalTime.getText().length() == 0) {
/*  624 */       this.contentPane.jxpanelPlotOptions.jtxtFinalTime.setText("0");
    }
/*  626 */     double dTemp = Float.valueOf(this.contentPane.jxpanelPlotOptions.jtxtFinalTime.getText()).floatValue();
    
/*  628 */     if (dTemp < this.m_dStartTime)
/*  629 */       dTemp = this.m_dStartTime + 1.0E-6D;
/*  630 */     if (dTemp > 9.9999999E7D) {
/*  631 */       dTemp = 9.9999999E7D;
    }
/*  633 */     this.m_dEndTime = dTemp;
/*  634 */     this.contentPane.jxpanelPlotOptions.jtxtFinalTime.setText(Float.toString((float)this.m_dEndTime));
  }
  
  private void validateNumPoints()
  {
/*  639 */     if (this.contentPane.jxpanelPlotOptions.jtxtNumPoints.getText().length() == 0) {
/*  640 */       this.contentPane.jxpanelPlotOptions.jtxtNumPoints.setText("0");
    }
/*  642 */     int iTemp = Integer.valueOf(this.contentPane.jxpanelPlotOptions.jtxtNumPoints.getText()).intValue();
    
/*  644 */     if (iTemp < 2)
/*  645 */       iTemp = 2;
/*  646 */     if (iTemp > 100000) {
/*  647 */       iTemp = 100000;
    }
/*  649 */     this.m_iNumPoints = iTemp;
/*  650 */     this.contentPane.jxpanelPlotOptions.jtxtNumPoints.setText(Integer.toString(this.m_iNumPoints));
  }
  
  private void validateFlowRate()
  {
/*  655 */     if (this.contentPane.jxpanelInletOutlet.jtxtFlowRate.getText().length() == 0) {
/*  656 */       this.contentPane.jxpanelInletOutlet.jtxtFlowRate.setText("0");
    }
/*  658 */     double dTemp = Float.valueOf(this.contentPane.jxpanelInletOutlet.jtxtFlowRate.getText()).floatValue();
    
/*  660 */     if (dTemp < 1.0E-9D)
/*  661 */       dTemp = 1.0E-9D;
/*  662 */     if (dTemp > 10000.0D) {
/*  663 */       dTemp = 10000.0D;
    }
/*  665 */     this.m_dFlowRate = dTemp;
/*  666 */     this.contentPane.jxpanelInletOutlet.jtxtFlowRate.setText(Float.toString((float)dTemp));
  }
  
  private void validateInletPressure()
  {
/*  671 */     if (this.contentPane.jxpanelInletOutlet.jtxtInletPressure.getText().length() == 0) {
/*  672 */       this.contentPane.jxpanelInletOutlet.jtxtInletPressure.setText("0");
    }
/*  674 */     double dTemp = Float.valueOf(this.contentPane.jxpanelInletOutlet.jtxtInletPressure.getText()).floatValue();
    
/*  676 */     if (dTemp < 1.0E-9D)
/*  677 */       dTemp = 1.0E-9D;
/*  678 */     if (dTemp > 100000.0D) {
/*  679 */       dTemp = 100000.0D;
    }
/*  681 */     if (this.contentPane.jxpanelInletOutlet.jcboInletPressureUnits.getSelectedIndex() == 0) {
/*  682 */       this.m_dInletPressure = (dTemp * 1000.0D + 101325.0D);
    } else {
/*  684 */       this.m_dInletPressure = (dTemp * 1000.0D);
    }
/*  686 */     this.contentPane.jxpanelInletOutlet.jtxtInletPressure.setText(Float.toString((float)Globals.roundToSignificantFigures(dTemp, 5)));
  }
  
  private void validateOtherOutletPressure()
  {
/*  691 */     if (this.contentPane.jxpanelInletOutlet.jtxtOtherPressure.getText().length() == 0) {
/*  692 */       this.contentPane.jxpanelInletOutlet.jtxtOtherPressure.setText("0");
    }
/*  694 */     if (!this.contentPane.jxpanelInletOutlet.jrdoOtherPressure.isSelected()) {
/*  695 */       return;
    }
/*  697 */     double dTemp = Float.valueOf(this.contentPane.jxpanelInletOutlet.jtxtOtherPressure.getText()).floatValue();
    
/*  699 */     if (dTemp < 1.0E-6D)
/*  700 */       dTemp = 1.0E-6D;
/*  701 */     if (dTemp > 101.325D) {
/*  702 */       dTemp = 101.325D;
    }
/*  704 */     this.m_dOutletPressure = (dTemp * 1000.0D);
/*  705 */     this.contentPane.jxpanelInletOutlet.jtxtOtherPressure.setText(Float.toString((float)dTemp));
  }
  
  private void validateSplitRatio()
  {
/*  710 */     if (this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.getText().length() == 0) {
/*  711 */       this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.setText("0");
    }
/*  713 */     double dTemp = Float.valueOf(this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.getText()).floatValue();
    
/*  715 */     if (dTemp < 1.0E-9D)
/*  716 */       dTemp = 1.0E-9D;
/*  717 */     if (dTemp > 1000000.0D) {
/*  718 */       dTemp = 1000000.0D;
    }
/*  720 */     this.m_dSplitRatio = dTemp;
/*  721 */     this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.setText(Float.toString((float)dTemp));
  }
  
  private void validateLinerLength()
  {
/*  726 */     if (this.contentPane.jxpanelInletOutlet.jtxtLinerLength.getText().length() == 0) {
/*  727 */       this.contentPane.jxpanelInletOutlet.jtxtLinerLength.setText("0");
    }
/*  729 */     double dTemp = Float.valueOf(this.contentPane.jxpanelInletOutlet.jtxtLinerLength.getText()).floatValue();
    
/*  731 */     if (dTemp < 1.0E-9D)
/*  732 */       dTemp = 1.0E-9D;
/*  733 */     if (dTemp > 1000000.0D) {
/*  734 */       dTemp = 1000000.0D;
    }
/*  736 */     this.m_dLinerLength = (dTemp / 1000.0D);
/*  737 */     this.contentPane.jxpanelInletOutlet.jtxtLinerLength.setText(Float.toString((float)dTemp));
  }
  
  private void validateLinerInnerDiameter()
  {
/*  742 */     if (this.contentPane.jxpanelInletOutlet.jtxtLinerInnerDiameter.getText().length() == 0) {
/*  743 */       this.contentPane.jxpanelInletOutlet.jtxtLinerInnerDiameter.setText("0");
    }
/*  745 */     double dTemp = Float.valueOf(this.contentPane.jxpanelInletOutlet.jtxtLinerInnerDiameter.getText()).floatValue();
    
/*  747 */     if (dTemp < 1.0E-9D)
/*  748 */       dTemp = 1.0E-9D;
/*  749 */     if (dTemp > 1000000.0D) {
/*  750 */       dTemp = 1000000.0D;
    }
/*  752 */     this.m_dLinerInnerDiameter = (dTemp / 1000.0D);
/*  753 */     this.contentPane.jxpanelInletOutlet.jtxtLinerInnerDiameter.setText(Float.toString((float)dTemp));
  }
  
  private void validateInjectionVolume()
  {
/*  758 */     if (this.contentPane.jxpanelInletOutlet.jtxtInjectionVolume.getText().length() == 0) {
/*  759 */       this.contentPane.jxpanelInletOutlet.jtxtInjectionVolume.setText("0");
    }
/*  761 */     double dTemp = Float.valueOf(this.contentPane.jxpanelInletOutlet.jtxtInjectionVolume.getText()).floatValue();
    
/*  763 */     if (dTemp < 1.0E-6D)
/*  764 */       dTemp = 1.0E-6D;
/*  765 */     if (dTemp > 999999.0D) {
/*  766 */       dTemp = 999999.0D;
    }
/*  768 */     this.m_dInjectionVolume = dTemp;
/*  769 */     this.contentPane.jxpanelInletOutlet.jtxtInjectionVolume.setText(Float.toString((float)this.m_dInjectionVolume));
  }
  
  private void validateColumnLength()
  {
/*  774 */     if (this.contentPane.jxpanelColumnProperties.jtxtColumnLength.getText().length() == 0) {
/*  775 */       this.contentPane.jxpanelColumnProperties.jtxtColumnLength.setText("0");
    }
/*  777 */     double dTemp = Float.valueOf(this.contentPane.jxpanelColumnProperties.jtxtColumnLength.getText()).floatValue();
    
/*  779 */     if (dTemp < 0.01D)
/*  780 */       dTemp = 0.01D;
/*  781 */     if (dTemp > 999999.0D) {
/*  782 */       dTemp = 999999.0D;
    }
/*  784 */     this.m_dColumnLength = dTemp;
/*  785 */     this.contentPane.jxpanelColumnProperties.jtxtColumnLength.setText(Float.toString((float)this.m_dColumnLength));
  }
  
  private void validateColumnDiameter()
  {
/*  790 */     if (this.contentPane.jxpanelColumnProperties.jtxtColumnDiameter.getText().length() == 0) {
/*  791 */       this.contentPane.jxpanelColumnProperties.jtxtColumnDiameter.setText("0");
    }
/*  793 */     double dTemp = Float.valueOf(this.contentPane.jxpanelColumnProperties.jtxtColumnDiameter.getText()).floatValue();
    
/*  795 */     if (dTemp < 1.0E-4D)
/*  796 */       dTemp = 1.0E-4D;
/*  797 */     if (dTemp > 999999.0D) {
/*  798 */       dTemp = 999999.0D;
    }
/*  800 */     this.m_dColumnDiameter = (dTemp / 1000.0D);
/*  801 */     this.contentPane.jxpanelColumnProperties.jtxtColumnDiameter.setText(Float.toString((float)dTemp));
  }
  
  private void validateFilmThickness()
  {
/*  806 */     if (this.contentPane.jxpanelColumnProperties.jtxtFilmThickness.getText().length() == 0) {
/*  807 */       this.contentPane.jxpanelColumnProperties.jtxtFilmThickness.setText("0");
    }
/*  809 */     double dTemp = Float.valueOf(this.contentPane.jxpanelColumnProperties.jtxtFilmThickness.getText()).floatValue();
    
/*  811 */     if (dTemp < 0.001D)
/*  812 */       dTemp = 0.001D;
/*  813 */     if (dTemp > 999999.0D) {
/*  814 */       dTemp = 999999.0D;
    }
/*  816 */     this.m_dFilmThickness = (dTemp / 1000000.0D);
/*  817 */     this.contentPane.jxpanelColumnProperties.jtxtFilmThickness.setText(Float.toString((float)dTemp));
  }
  
  private void validateTemperature()
  {
/*  822 */     if (this.contentPane.jxpanelIsothermalOptions.jtxtTemperature.getText().length() == 0) {
/*  823 */       this.contentPane.jxpanelIsothermalOptions.jtxtTemperature.setText("0");
    }
/*  825 */     double dTemp = Float.valueOf(this.contentPane.jxpanelIsothermalOptions.jtxtTemperature.getText()).floatValue();
/*  826 */     dTemp = Math.floor(dTemp);
    
/*  828 */     if (dTemp < 60.0D)
/*  829 */       dTemp = 60.0D;
/*  830 */     if (dTemp > 320.0D) {
/*  831 */       dTemp = 320.0D;
    }
/*  833 */     this.m_dTemperature = dTemp;
/*  834 */     this.m_bSliderUpdate = false;
/*  835 */     this.contentPane.jxpanelIsothermalOptions.jsliderTemperature.setValue((int)this.m_dTemperature);
/*  836 */     this.m_bSliderUpdate = true;
/*  837 */     this.contentPane.jxpanelIsothermalOptions.jtxtTemperature.setText(Integer.toString((int)this.m_dTemperature));
  }
  
  private void validateFlowVelocityAtColumnPosition()
  {
/*  842 */     if (this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.getText().length() == 0) {
/*  843 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setText("0");
    }
/*  845 */     double dTemp = Float.valueOf(this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.getText()).floatValue();
    
/*  847 */     if (dTemp < 0.0D)
/*  848 */       dTemp = 0.0D;
/*  849 */     if (dTemp > 1.0D) {
/*  850 */       dTemp = 1.0D;
    }
/*  852 */     this.m_dPlotFlowVelocityPosition = dTemp;
/*  853 */     this.m_bSliderUpdate = false;
/*  854 */     this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setValue((int)(dTemp * 1000.0D));
/*  855 */     this.m_bSliderUpdate = true;
/*  856 */     this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setText(Float.toString((float)dTemp));
  }
  
  private void validateGasDensityAtColumnPosition()
  {
/*  861 */     if (this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.getText().length() == 0) {
/*  862 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setText("0");
    }
/*  864 */     double dTemp = Float.valueOf(this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.getText()).floatValue();
    
/*  866 */     if (dTemp < 0.0D)
/*  867 */       dTemp = 0.0D;
/*  868 */     if (dTemp > 1.0D) {
/*  869 */       dTemp = 1.0D;
    }
/*  871 */     this.m_dPlotGasDensityPosition = dTemp;
/*  872 */     this.m_bSliderUpdate = false;
/*  873 */     this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setValue((int)(dTemp * 1000.0D));
/*  874 */     this.m_bSliderUpdate = true;
/*  875 */     this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setText(Float.toString((float)dTemp));
  }
  
  private void validatePressureAtColumnPosition()
  {
/*  880 */     if (this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.getText().length() == 0) {
/*  881 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setText("0");
    }
/*  883 */     double dTemp = Float.valueOf(this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.getText()).floatValue();
    
/*  885 */     if (dTemp < 0.0D)
/*  886 */       dTemp = 0.0D;
/*  887 */     if (dTemp > 1.0D) {
/*  888 */       dTemp = 1.0D;
    }
/*  890 */     this.m_dPlotPressurePosition = dTemp;
/*  891 */     this.m_bSliderUpdate = false;
/*  892 */     this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setValue((int)(dTemp * 1000.0D));
/*  893 */     this.m_bSliderUpdate = true;
/*  894 */     this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setText(Float.toString((float)dTemp));
  }
  
  private void validateInitialTemperature()
  {
/*  899 */     if (this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTemperature.getText().length() == 0) {
/*  900 */       this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTemperature.setText("0");
    }
/*  902 */     double dTemp = Float.valueOf(this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTemperature.getText()).floatValue();
    
/*  904 */     if (dTemp < 60.0D)
/*  905 */       dTemp = 60.0D;
/*  906 */     if (dTemp > 320.0D) {
/*  907 */       dTemp = 320.0D;
    }
/*  909 */     if (this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getRowCount() > 0)
    {
/*  911 */       double dFirstTemp = ((Double)this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getValueAt(0, 1)).doubleValue();
/*  912 */       if (dTemp > dFirstTemp) {
/*  913 */         dTemp = dFirstTemp;
      }
    }
/*  916 */     this.m_dInitialTemperature = dTemp;
/*  917 */     this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTemperature.setText(Float.toString((float)dTemp));
  }
  
  private void validateInitialTime()
  {
/*  922 */     if (this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTime.getText().length() == 0) {
/*  923 */       this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTime.setText("0");
    }
/*  925 */     double dTemp = Float.valueOf(this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTime.getText()).floatValue();
    
/*  927 */     if (dTemp < 0.0D)
/*  928 */       dTemp = 0.0D;
/*  929 */     if (dTemp > 1000.0D) {
/*  930 */       dTemp = 1000.0D;
    }
/*  932 */     this.m_dInitialTime = dTemp;
/*  933 */     this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTime.setText(Float.toString((float)dTemp));
  }
  
  public boolean writeToOutputStream()
  {
    try
    {
/*  940 */       FileOutputStream fos = new FileOutputStream(this.m_currentFile);
/*  941 */       ObjectOutputStream oos = new ObjectOutputStream(fos);
      
/*  943 */       oos.writeLong(1L);
/*  944 */       oos.writeObject(this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getDataVector());
/*  945 */       oos.writeInt(this.m_iCarrierGas);
/*  946 */       oos.writeDouble(this.m_dFlowRate);
/*  947 */       oos.writeDouble(this.m_dInletPressure);
/*  948 */       oos.writeDouble(this.m_dOutletPressure);
/*  949 */       oos.writeDouble(this.m_dSplitRatio);
/*  950 */       oos.writeDouble(this.m_dLinerLength);
/*  951 */       oos.writeDouble(this.m_dLinerInnerDiameter);
/*  952 */       oos.writeDouble(this.m_dInjectionVolume);
/*  953 */       oos.writeBoolean(this.m_bTemperatureProgramMode);
/*  954 */       oos.writeBoolean(this.m_bAutomaticTimeRange);
/*  955 */       oos.writeDouble(this.m_dTemperature);
/*  956 */       oos.writeDouble(this.m_dColumnLength);
/*  957 */       oos.writeDouble(this.m_dColumnDiameter);
/*  958 */       oos.writeDouble(this.m_dFilmThickness);
/*  959 */       oos.writeDouble(this.m_dInitialTemperature);
/*  960 */       oos.writeDouble(this.m_dInitialTime);
/*  961 */       oos.writeBoolean(this.m_bConstantFlowRateMode);
/*  962 */       oos.writeBoolean(this.m_bColumnOutletAtVacuum);
/*  963 */       oos.writeBoolean(this.m_bSplitInjectionMode);
/*  964 */       oos.writeDouble(this.m_dTimeConstant);
/*  965 */       oos.writeDouble(this.m_dStartTime);
/*  966 */       oos.writeDouble(this.m_dEndTime);
/*  967 */       oos.writeDouble(this.m_dNoise);
/*  968 */       oos.writeDouble(this.m_dSignalOffset);
/*  969 */       oos.writeInt(this.m_iNumPoints);
/*  970 */       oos.writeObject(this.m_vectCompound);
/*  971 */       oos.writeInt(this.m_iStationaryPhase);
      
/*  973 */       oos.flush();
/*  974 */       oos.close();
/*  975 */       this.m_bDocumentChangedFlag = false;
    }
    catch (IOException e)
    {
/*  979 */       e.printStackTrace();
/*  980 */       JOptionPane.showMessageDialog(this, "The file could not be saved.", "Error saving file", 0);
/*  981 */       return false;
    }
    
/*  984 */     return true;
  }
  
  public boolean saveFile(boolean bSaveAs)
  {
/*  989 */     if ((!bSaveAs) && (this.m_currentFile != null))
    {
/*  991 */       if (writeToOutputStream()) {
/*  992 */         return true;
      }
    }
    else {
/*  996 */       JFileChooser2 fc = new JFileChooser2();
      
/*  998 */       FileNameExtensionFilter filter = new FileNameExtensionFilter("GC Simulator Files (*.gcsim)", new String[] { "gcsim" });
/*  999 */       fc.setFileFilter(filter);
/* 1000 */       fc.setDialogTitle("Save As...");
/* 1001 */       int returnVal = fc.showSaveDialog(this);
/* 1002 */       if (returnVal == 0)
      {
/* 1004 */         this.m_currentFile = fc.getSelectedFile();
/* 1005 */         String path = this.m_currentFile.getAbsolutePath();
/* 1006 */         if (path.lastIndexOf(".") >= 0) {
/* 1007 */           path = path.substring(0, path.lastIndexOf("."));
        }
/* 1009 */         this.m_currentFile = new File(path + ".gcsim");
        
/* 1011 */         if (writeToOutputStream()) {
/* 1012 */           return true;
        }
      }
    }
/* 1016 */     return false;
  }
  
  public void loadDefaultsForStationaryPhase(int iStationaryPhase)
  {
/* 1021 */     if (iStationaryPhase == 0)
    {
/* 1023 */       int iNumRows = this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getRowCount();
/* 1024 */       for (int i = 0; i < iNumRows; i++)
      {
/* 1026 */         this.contentPane.jxpanelTemperatureProgramOptions.m_bDoNotChangeTable = true;
/* 1027 */         this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.removeRow(0);
      }
      
/* 1030 */       this.contentPane.jxpanelTemperatureProgramOptions.m_bDoNotChangeTable = true;
/* 1031 */       this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.addRow(new Double[] { Double.valueOf(20.0D), Double.valueOf(260.0D), Double.valueOf(5.0D) });
      
/* 1033 */       this.m_bTemperatureProgramMode = false;
/* 1034 */       this.m_bAutomaticTimeRange = true;
/* 1035 */       this.m_bSplitInjectionMode = true;
/* 1036 */       this.m_bConstantFlowRateMode = true;
/* 1037 */       this.m_bColumnOutletAtVacuum = true;
/* 1038 */       this.m_iCarrierGas = 1;
/* 1039 */       this.m_dInletPressure = 151325.0D;
/* 1040 */       this.m_dOutletPressure = 1.0E-5D;
/* 1041 */       this.m_dSplitRatio = 100.0D;
/* 1042 */       this.m_dLinerLength = 0.078D;
/* 1043 */       this.m_dLinerInnerDiameter = 0.002D;
/* 1044 */       this.m_dFilmThickness = 2.5E-7D;
/* 1045 */       this.m_dInitialTemperature = 60.0D;
/* 1046 */       this.m_dInitialTime = 1.0D;
/* 1047 */       this.m_dTemperature = 200.0D;
/* 1048 */       this.m_dColumnLength = 30.0D;
/* 1049 */       this.m_dColumnDiameter = 2.5E-4D;
/* 1050 */       this.m_dFlowRate = 1.0D;
/* 1051 */       this.m_dInjectionVolume = 1.0D;
/* 1052 */       this.m_dTimeConstant = 0.1D;
/* 1053 */       this.m_dStartTime = 0.0D;
/* 1054 */       this.m_dEndTime = 277.0D;
/* 1055 */       this.m_dNoise = 0.5D;
/* 1056 */       this.m_dSignalOffset = 0.0D;
/* 1057 */       this.m_iNumPoints = 3000;
/* 1058 */       this.m_iStationaryPhase = 0;
/* 1059 */       this.m_vectCompound.clear();
      
/* 1061 */       double[][] compoundsToAdd = {
/* 1062 */         { 2.0D, 30.0D }, 
/* 1063 */         { 5.0D, 60.0D }, 
/* 1064 */         { 12.0D, 30.0D }, 
/* 1065 */         { 14.0D, 50.0D }, 
/* 1066 */         { 17.0D, 100.0D }, 
/* 1067 */         { 18.0D, 20.0D }, 
/* 1068 */         { 19.0D, 50.0D }, 
/* 1069 */         { 24.0D, 40.0D }, 
/* 1070 */         { 30.0D, 30.0D }, 
/* 1071 */         { 35.0D, 10.0D }, 
/* 1072 */         { 40.0D, 100.0D }, 
/* 1073 */         { 45.0D, 30.0D }, 
/* 1074 */         { 50.0D, 60.0D }, 
/* 1075 */         { 55.0D, 40.0D }, 
/* 1076 */         { 60.0D, 20.0D } };
      

/* 1079 */       for (int i = 0; i < compoundsToAdd.length; i++)
      {
/* 1081 */         Compound compound = new Compound();
/* 1082 */         compound.loadCompoundInfo(this.m_iStationaryPhase, (int)compoundsToAdd[i][0]);
/* 1083 */         compound.dConcentration = compoundsToAdd[i][1];
/* 1084 */         this.m_vectCompound.add(compound);
      }
    }
    

/* 1089 */     loadControlsWithStoredValues();
/* 1090 */     updateCompoundComboBoxes();
  }
  

  public boolean loadFile(File fileToLoad)
  {
/* 1096 */     loadDefaultsForStationaryPhase(0);
    
/* 1098 */     if (fileToLoad != null)
    {
      try
      {
/* 1102 */         FileInputStream fis = new FileInputStream(this.m_currentFile);
/* 1103 */         ObjectInputStream ois = new ObjectInputStream(fis);
        
/* 1105 */         long lFileVersion = ois.readLong();
        
/* 1107 */         if (lFileVersion >= 1L)
        {
/* 1109 */           int iNumRows = this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getRowCount();
/* 1110 */           for (int i = 0; i < iNumRows; i++)
          {
/* 1112 */             this.contentPane.jxpanelTemperatureProgramOptions.m_bDoNotChangeTable = true;
/* 1113 */             this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.removeRow(0);
          }
          
/* 1116 */           Vector<Vector<Double>> rowVector = (Vector)ois.readObject();
          
/* 1118 */           for (int i = 0; i < rowVector.size(); i++)
          {
/* 1120 */             if (rowVector.elementAt(i) != null)
            {
/* 1122 */               this.contentPane.jxpanelTemperatureProgramOptions.m_bDoNotChangeTable = true;
/* 1123 */               this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.addRow((Vector)rowVector.elementAt(i));
            }
          }
/* 1126 */           this.m_iCarrierGas = ois.readInt();
/* 1127 */           this.m_dFlowRate = ois.readDouble();
/* 1128 */           this.m_dInletPressure = ois.readDouble();
/* 1129 */           this.m_dOutletPressure = ois.readDouble();
/* 1130 */           this.m_dSplitRatio = ois.readDouble();
/* 1131 */           this.m_dLinerLength = ois.readDouble();
/* 1132 */           this.m_dLinerInnerDiameter = ois.readDouble();
/* 1133 */           this.m_dInjectionVolume = ois.readDouble();
/* 1134 */           this.m_bTemperatureProgramMode = ois.readBoolean();
/* 1135 */           this.m_bAutomaticTimeRange = ois.readBoolean();
/* 1136 */           this.m_dTemperature = ois.readDouble();
/* 1137 */           this.m_dColumnLength = ois.readDouble();
/* 1138 */           this.m_dColumnDiameter = ois.readDouble();
/* 1139 */           this.m_dFilmThickness = ois.readDouble();
/* 1140 */           this.m_dInitialTemperature = ois.readDouble();
/* 1141 */           this.m_dInitialTime = ois.readDouble();
/* 1142 */           this.m_bConstantFlowRateMode = ois.readBoolean();
/* 1143 */           this.m_bColumnOutletAtVacuum = ois.readBoolean();
/* 1144 */           this.m_bSplitInjectionMode = ois.readBoolean();
/* 1145 */           this.m_dTimeConstant = ois.readDouble();
/* 1146 */           this.m_dStartTime = ois.readDouble();
/* 1147 */           this.m_dEndTime = ois.readDouble();
/* 1148 */           this.m_dNoise = ois.readDouble();
/* 1149 */           this.m_dSignalOffset = ois.readDouble();
/* 1150 */           this.m_iNumPoints = ois.readInt();
/* 1151 */           this.m_vectCompound = ((Vector)ois.readObject());
/* 1152 */           this.m_iStationaryPhase = ois.readInt();
        }
        else
        {
/* 1156 */           JOptionPane.showMessageDialog(this, "Sorry! This file is no longer compatible with GC Simulator after the latest update. However, new files you save in this version WILL be compatible with future versions.", "Error opening file", 0);
/* 1157 */           ois.close();
/* 1158 */           this.m_currentFile = null;
/* 1159 */           return false;
        }
        
/* 1162 */         ois.close();
      }
      catch (IOException e)
      {
/* 1166 */         e.printStackTrace();
/* 1167 */         JOptionPane.showMessageDialog(this, "The file is not a valid GC Simulator file.", "Error opening file", 0);
/* 1168 */         this.m_currentFile = null;
/* 1169 */         return false;
      }
      catch (ClassNotFoundException e)
      {
/* 1173 */         e.printStackTrace();
/* 1174 */         JOptionPane.showMessageDialog(this, "The file is not a valid GC Simulator file.", "Error opening file", 0);
/* 1175 */         this.m_currentFile = null;
/* 1176 */         return false;
      }
    }
    
/* 1180 */     loadControlsWithStoredValues();
    
/* 1182 */     return true;
  }
  

  public void loadControlsWithStoredValues()
  {
/* 1188 */     if (this.m_bTemperatureProgramMode)
    {
/* 1190 */       this.contentPane.jxpanelOvenOptions.jrdoIsothermalElution.setSelected(false);
/* 1191 */       this.contentPane.jxpanelOvenOptions.jrdoProgrammedTemperatureElution.setSelected(true);
      
/* 1193 */       this.contentPane.jxtaskOvenOptions.remove(this.contentPane.jxpanelIsothermalOptions);
/* 1194 */       this.contentPane.jxtaskOvenOptions.add(this.contentPane.jxpanelTemperatureProgramOptions);
      
/* 1196 */       this.contentPane.jxtaskOvenOptions.validate();
/* 1197 */       this.contentPane.jsclControlPanel.validate();
    }
    else
    {
/* 1201 */       this.contentPane.jxpanelOvenOptions.jrdoIsothermalElution.setSelected(true);
/* 1202 */       this.contentPane.jxpanelOvenOptions.jrdoProgrammedTemperatureElution.setSelected(false);
      
/* 1204 */       this.contentPane.jxtaskOvenOptions.remove(this.contentPane.jxpanelTemperatureProgramOptions);
/* 1205 */       this.contentPane.jxtaskOvenOptions.add(this.contentPane.jxpanelIsothermalOptions);
      
/* 1207 */       this.contentPane.jxtaskOvenOptions.validate();
/* 1208 */       this.contentPane.jsclControlPanel.validate();
    }
    
/* 1211 */     this.m_bDoNotMessage = true;
/* 1212 */     this.contentPane.jxpanelInletOutlet.jcboCarrierGas.setSelectedIndex(this.m_iCarrierGas);
/* 1213 */     this.m_bDoNotMessage = false;
    
/* 1215 */     this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTemperature.setText(Float.toString((float)this.m_dInitialTemperature));
/* 1216 */     this.contentPane.jxpanelTemperatureProgramOptions.jtxtInitialTime.setText(Float.toString((float)this.m_dInitialTime));
    
/* 1218 */     this.contentPane.jxpanelInletOutlet.jtxtFlowRate.setText(Float.toString((float)this.m_dFlowRate));
/* 1219 */     this.contentPane.jxpanelInletOutlet.jtxtInletPressure.setText(Float.toString((float)(this.m_dInletPressure - 101325.0D) / 1000.0F));
/* 1220 */     this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.setText(Float.toString((float)this.m_dSplitRatio));
/* 1221 */     this.contentPane.jxpanelInletOutlet.jtxtLinerLength.setText(Float.toString((float)this.m_dLinerLength * 1000.0F));
/* 1222 */     this.contentPane.jxpanelInletOutlet.jtxtLinerInnerDiameter.setText(Float.toString((float)this.m_dLinerInnerDiameter * 1000.0F));
/* 1223 */     this.contentPane.jxpanelInletOutlet.jtxtInjectionVolume.setText(Float.toString((float)this.m_dInjectionVolume));
/* 1224 */     if (this.m_bConstantFlowRateMode) {
/* 1225 */       switchToConstantFlowRateMode();
    } else {
/* 1227 */       switchToConstantPressureMode();
    }
/* 1229 */     if (this.m_bColumnOutletAtVacuum) {
/* 1230 */       vacuumOutletPressure();
    } else {
/* 1232 */       otherOutletPressure();
    }
/* 1234 */     if (this.m_bSplitInjectionMode) {
/* 1235 */       switchToSplitMode();
    } else {
/* 1237 */       switchToSplitlessMode();
    }
/* 1239 */     this.m_bDoNotMessage = true;
/* 1240 */     this.contentPane.jxpanelColumnProperties.jcboStationaryPhase.setSelectedIndex(this.m_iStationaryPhase);
/* 1241 */     this.m_bDoNotMessage = false;
/* 1242 */     this.contentPane.jxpanelColumnProperties.jtxtColumnLength.setText(Float.toString((float)this.m_dColumnLength));
/* 1243 */     this.contentPane.jxpanelColumnProperties.jtxtColumnDiameter.setText(Float.toString((float)this.m_dColumnDiameter * 1000.0F));
/* 1244 */     this.contentPane.jxpanelColumnProperties.jtxtFilmThickness.setText(Float.toString((float)this.m_dFilmThickness * 1000000.0F));
    
/* 1246 */     this.m_bSliderUpdate = false;
/* 1247 */     this.contentPane.jxpanelIsothermalOptions.jtxtTemperature.setText(Integer.toString((int)this.m_dTemperature));
/* 1248 */     this.contentPane.jxpanelIsothermalOptions.jsliderTemperature.setValue((int)this.m_dTemperature);
/* 1249 */     this.m_bSliderUpdate = true;
    
/* 1251 */     this.contentPane.jxpanelPlotOptions.jtxtTimeConstant.setText(Float.toString((float)this.m_dTimeConstant));
/* 1252 */     this.contentPane.jxpanelPlotOptions.jtxtSignalOffset.setText(Float.toString((float)this.m_dSignalOffset));
/* 1253 */     this.contentPane.jxpanelPlotOptions.jtxtNoise.setText(Float.toString((float)this.m_dNoise));
/* 1254 */     this.contentPane.jxpanelPlotOptions.jtxtInitialTime.setText(Float.toString((float)this.m_dStartTime));
/* 1255 */     this.contentPane.jxpanelPlotOptions.jtxtFinalTime.setText(Float.toString((float)this.m_dEndTime));
/* 1256 */     this.contentPane.jxpanelPlotOptions.jtxtNumPoints.setText(Integer.toString(this.m_iNumPoints));
    
/* 1258 */     this.contentPane.jxpanelPlotOptions.jchkAutoTimeRange.setSelected(this.m_bAutomaticTimeRange);
/* 1259 */     if (this.m_bAutomaticTimeRange)
    {
/* 1261 */       this.contentPane.jxpanelPlotOptions.jtxtInitialTime.setEnabled(false);
/* 1262 */       this.contentPane.jxpanelPlotOptions.jtxtFinalTime.setEnabled(false);
    }
    else
    {
/* 1266 */       this.contentPane.jxpanelPlotOptions.jtxtInitialTime.setEnabled(true);
/* 1267 */       this.contentPane.jxpanelPlotOptions.jtxtFinalTime.setEnabled(true);
    }
    

/* 1271 */     this.contentPane.vectChemicalRows.clear();
/* 1272 */     for (int i = 0; i < this.m_vectCompound.size(); i++)
    {
/* 1274 */       Vector<String> vectNewRow = new Vector();
/* 1275 */       vectNewRow.add(((Compound)this.m_vectCompound.get(i)).strCompoundName);
/* 1276 */       vectNewRow.add(Float.toString((float)((Compound)this.m_vectCompound.get(i)).dConcentration));
/* 1277 */       vectNewRow.add("");
/* 1278 */       vectNewRow.add("");
/* 1279 */       vectNewRow.add("");
/* 1280 */       vectNewRow.add("");
/* 1281 */       vectNewRow.add("");
/* 1282 */       this.contentPane.vectChemicalRows.add(vectNewRow);
    }
    
/* 1285 */     updateCompoundComboBoxes();
    
/* 1287 */     performCalculations();
  }
  
  public void actionPerformed(ActionEvent evt)
  {
/* 1292 */     String strActionCommand = evt.getActionCommand();
    
/* 1294 */     if (strActionCommand == "Add Chemical")
    {

/* 1297 */       ChemicalDialog dlgChemical = new ChemicalDialog(this, false);
      
/* 1299 */       dlgChemical.setSelectedStationaryPhase(this.m_iStationaryPhase);
      

/* 1302 */       for (int i = 0; i < this.m_vectCompound.size(); i++)
      {
/* 1304 */         Integer k = new Integer(((Compound)this.m_vectCompound.get(i)).iCompoundIndex);
/* 1305 */         dlgChemical.m_vectCompoundsUsed.add(k);
      }
      

/* 1309 */       dlgChemical.setVisible(true);
      
/* 1311 */       if (!dlgChemical.m_bOk) {
/* 1312 */         return;
      }
      
/* 1315 */       Compound newCompound = new Compound();
      
/* 1317 */       newCompound.loadCompoundInfo(this.m_iStationaryPhase, dlgChemical.m_iCompound);
/* 1318 */       newCompound.dConcentration = dlgChemical.m_dConcentration1;
      
/* 1320 */       this.m_vectCompound.add(newCompound);
      

/* 1323 */       Vector<String> vectNewRow = new Vector();
/* 1324 */       vectNewRow.add(newCompound.strCompoundName);
/* 1325 */       vectNewRow.add(Float.toString((float)newCompound.dConcentration));
/* 1326 */       vectNewRow.add("");
/* 1327 */       vectNewRow.add("");
/* 1328 */       vectNewRow.add("");
/* 1329 */       vectNewRow.add("");
/* 1330 */       vectNewRow.add("");
      
/* 1332 */       this.contentPane.vectChemicalRows.add(vectNewRow);
      
/* 1334 */       updateCompoundComboBoxes();
      
/* 1336 */       performCalculations();
    }
/* 1338 */     else if (strActionCommand == "Edit Chemical")
    {
/* 1340 */       int iRowSel = this.contentPane.jtableChemicals.getSelectedRow();
/* 1341 */       if ((iRowSel < 0) || (iRowSel >= this.contentPane.vectChemicalRows.size())) {
/* 1342 */         return;
      }
      
/* 1345 */       ChemicalDialog dlgChemical = new ChemicalDialog(this, true);
      
/* 1347 */       dlgChemical.setSelectedStationaryPhase(this.m_iStationaryPhase);
      
/* 1349 */       dlgChemical.setSelectedCompound(((Compound)this.m_vectCompound.get(iRowSel)).iCompoundIndex);
/* 1350 */       dlgChemical.setCompoundConcentration(((Compound)this.m_vectCompound.get(iRowSel)).dConcentration);
      

/* 1353 */       for (int i = 0; i < this.m_vectCompound.size(); i++)
      {

/* 1356 */         if (i != iRowSel)
        {

/* 1359 */           Integer k = new Integer(((Compound)this.m_vectCompound.get(i)).iCompoundIndex);
/* 1360 */           dlgChemical.m_vectCompoundsUsed.add(k);
        }
      }
      
/* 1364 */       dlgChemical.setVisible(true);
      
/* 1366 */       if (!dlgChemical.m_bOk) {
/* 1367 */         return;
      }
      
/* 1370 */       Compound newCompound = new Compound();
      
/* 1372 */       newCompound.loadCompoundInfo(this.m_iStationaryPhase, dlgChemical.m_iCompound);
/* 1373 */       newCompound.dConcentration = dlgChemical.m_dConcentration1;
      
/* 1375 */       this.m_vectCompound.set(iRowSel, newCompound);
      

/* 1378 */       Vector<String> vectNewRow = new Vector();
/* 1379 */       vectNewRow.add(newCompound.strCompoundName);
/* 1380 */       vectNewRow.add(Float.toString((float)newCompound.dConcentration));
/* 1381 */       vectNewRow.add("");
/* 1382 */       vectNewRow.add("");
/* 1383 */       vectNewRow.add("");
/* 1384 */       vectNewRow.add("");
/* 1385 */       vectNewRow.add("");
      
/* 1387 */       this.contentPane.vectChemicalRows.set(iRowSel, vectNewRow);
      
/* 1389 */       updateCompoundComboBoxes();
      
/* 1391 */       performCalculations();
    }
/* 1393 */     else if (strActionCommand == "Remove Chemical")
    {
/* 1395 */       int iRowSel = this.contentPane.jtableChemicals.getSelectedRow();
/* 1396 */       if ((iRowSel < 0) || (iRowSel >= this.contentPane.vectChemicalRows.size())) {
/* 1397 */         return;
      }
/* 1399 */       this.contentPane.vectChemicalRows.remove(iRowSel);
/* 1400 */       this.contentPane.jtableChemicals.addNotify();
      
/* 1402 */       if (iRowSel >= this.m_vectCompound.size()) {
/* 1403 */         return;
      }
/* 1405 */       this.m_vectCompound.remove(iRowSel);
/* 1406 */       updateCompoundComboBoxes();
      
/* 1408 */       performCalculations();
    }
/* 1410 */     else if (strActionCommand == "Automatically determine time span")
    {
/* 1412 */       if (this.contentPane.jxpanelPlotOptions.jchkAutoTimeRange.isSelected())
      {
/* 1414 */         this.m_bAutomaticTimeRange = true;
/* 1415 */         this.contentPane.jxpanelPlotOptions.jtxtInitialTime.setEnabled(false);
/* 1416 */         this.contentPane.jxpanelPlotOptions.jtxtFinalTime.setEnabled(false);
      }
      else
      {
/* 1420 */         this.m_bAutomaticTimeRange = false;
/* 1421 */         this.contentPane.jxpanelPlotOptions.jtxtInitialTime.setEnabled(true);
/* 1422 */         this.contentPane.jxpanelPlotOptions.jtxtFinalTime.setEnabled(true);
      }
      
/* 1425 */       performCalculations();
    }
/* 1427 */     else if (strActionCommand == "Autoscale")
    {
/* 1429 */       if (this.contentPane.jbtnAutoscale.isSelected())
      {
/* 1431 */         this.contentPane.m_GraphControl.setAutoScaleX(true);
/* 1432 */         this.contentPane.m_GraphControl.setAutoScaleY(true);
/* 1433 */         this.contentPane.m_GraphControl.repaint();
      }
      else
      {
/* 1437 */         this.contentPane.m_GraphControl.setAutoScaleX(false);
/* 1438 */         this.contentPane.m_GraphControl.setAutoScaleY(false);
      }
    }
/* 1441 */     else if (strActionCommand == "Autoscale X")
    {
/* 1443 */       if (this.contentPane.jbtnAutoscaleX.isSelected())
      {
/* 1445 */         this.contentPane.m_GraphControl.setAutoScaleX(true);
/* 1446 */         this.contentPane.m_GraphControl.repaint();
      }
      else
      {
/* 1450 */         this.contentPane.m_GraphControl.setAutoScaleX(false);
      }
    }
/* 1453 */     else if (strActionCommand == "Autoscale Y")
    {
/* 1455 */       if (this.contentPane.jbtnAutoscaleY.isSelected())
      {
/* 1457 */         this.contentPane.m_GraphControl.setAutoScaleY(true);
/* 1458 */         this.contentPane.m_GraphControl.repaint();
      }
      else
      {
/* 1462 */         this.contentPane.m_GraphControl.setAutoScaleY(false);
      }
    }
/* 1465 */     else if (strActionCommand == "Pan")
    {
/* 1467 */       this.contentPane.jbtnPan.setSelected(true);
/* 1468 */       this.contentPane.jbtnZoomIn.setSelected(false);
/* 1469 */       this.contentPane.jbtnZoomOut.setSelected(false);
/* 1470 */       this.contentPane.m_GraphControl.selectPanMode();
    }
/* 1472 */     else if (strActionCommand == "Zoom in")
    {
/* 1474 */       this.contentPane.jbtnPan.setSelected(false);
/* 1475 */       this.contentPane.jbtnZoomIn.setSelected(true);
/* 1476 */       this.contentPane.jbtnZoomOut.setSelected(false);
/* 1477 */       this.contentPane.m_GraphControl.selectZoomInMode();
    }
/* 1479 */     else if (strActionCommand == "Zoom out")
    {
/* 1481 */       this.contentPane.jbtnPan.setSelected(false);
/* 1482 */       this.contentPane.jbtnZoomIn.setSelected(false);
/* 1483 */       this.contentPane.jbtnZoomOut.setSelected(true);
/* 1484 */       this.contentPane.m_GraphControl.selectZoomOutMode();
    }
/* 1486 */     else if (strActionCommand == "Help")
    {
/* 1488 */       Globals.hbMainHelpBroker.setCurrentID("getting_started");
/* 1489 */       Globals.hbMainHelpBroker.setDisplayed(true);
    }
/* 1491 */     else if (strActionCommand == "Tutorials")
    {
/* 1493 */       Globals.hbMainHelpBroker.setCurrentID("tutorials");
/* 1494 */       Globals.hbMainHelpBroker.setDisplayed(true);
    }
/* 1496 */     else if (strActionCommand == "CarrierGasChanged")
    {
/* 1498 */       if (this.m_iCarrierGas == this.contentPane.jxpanelInletOutlet.jcboCarrierGas.getSelectedIndex()) {
/* 1499 */         return;
      }
/* 1501 */       this.m_iCarrierGas = this.contentPane.jxpanelInletOutlet.jcboCarrierGas.getSelectedIndex();
      
/* 1503 */       performCalculations();
    }
/* 1505 */     else if (strActionCommand == "StationaryPhaseComboBoxChanged")
    {
/* 1507 */       if (!this.m_bDoNotMessage)
      {
/* 1509 */         if (this.m_iStationaryPhase == this.contentPane.jxpanelColumnProperties.jcboStationaryPhase.getSelectedIndex()) {
/* 1510 */           return;
        }
/* 1512 */         int retval = JOptionPane.showConfirmDialog(null, "If you select a new stationary phase, the selected compounds will reset to the default ones.\nAre you sure you want to choose a new stationary phase?", "GC Simulator", 0);
/* 1513 */         if (retval == 0)
        {
/* 1515 */           this.m_iStationaryPhase = this.contentPane.jxpanelColumnProperties.jcboStationaryPhase.getSelectedIndex();
/* 1516 */           loadDefaultsForStationaryPhase(this.m_iStationaryPhase);
        }
        else
        {
/* 1520 */           this.contentPane.jxpanelColumnProperties.jcboStationaryPhase.setSelectedIndex(this.m_iStationaryPhase);
        }
      }
    }
/* 1524 */     else if (strActionCommand == "Isothermal elution mode")
    {
/* 1526 */       this.contentPane.jxpanelOvenOptions.jrdoProgrammedTemperatureElution.setSelected(false);
/* 1527 */       this.contentPane.jxpanelOvenOptions.jrdoIsothermalElution.setSelected(true);
      
/* 1529 */       this.contentPane.jxtaskOvenOptions.remove(this.contentPane.jxpanelTemperatureProgramOptions);
/* 1530 */       this.contentPane.jxtaskOvenOptions.add(this.contentPane.jxpanelIsothermalOptions);
      
/* 1532 */       this.contentPane.jxpanelInletOutlet.validate();
/* 1533 */       this.contentPane.jsclControlPanel.validate();
      
/* 1535 */       this.m_bTemperatureProgramMode = false;
/* 1536 */       performCalculations();
    }
/* 1538 */     else if (strActionCommand == "Programmed temperature elution mode")
    {
/* 1540 */       this.contentPane.jxpanelOvenOptions.jrdoIsothermalElution.setSelected(false);
/* 1541 */       this.contentPane.jxpanelOvenOptions.jrdoProgrammedTemperatureElution.setSelected(true);
      
/* 1543 */       this.contentPane.jxtaskOvenOptions.remove(this.contentPane.jxpanelIsothermalOptions);
/* 1544 */       this.contentPane.jxtaskOvenOptions.add(this.contentPane.jxpanelTemperatureProgramOptions);
      
/* 1546 */       this.contentPane.jxpanelInletOutlet.validate();
/* 1547 */       this.contentPane.jsclControlPanel.validate();
      
/* 1549 */       this.m_bTemperatureProgramMode = true;
/* 1550 */       performCalculations();
    }
/* 1552 */     else if (strActionCommand == "Constant flow rate mode")
    {
/* 1554 */       switchToConstantFlowRateMode();
/* 1555 */       performCalculations();
    }
/* 1557 */     else if (strActionCommand == "Constant pressure mode")
    {
/* 1559 */       switchToConstantPressureMode();
/* 1560 */       performCalculations();
    }
/* 1562 */     else if (strActionCommand == "Vacuum")
    {
/* 1564 */       vacuumOutletPressure();
/* 1565 */       performCalculations();
    }
/* 1567 */     else if (strActionCommand == "OtherPressure")
    {
/* 1569 */       otherOutletPressure();
/* 1570 */       performCalculations();
    }
/* 1572 */     else if (strActionCommand == "Split injection")
    {
/* 1574 */       switchToSplitMode();
/* 1575 */       performCalculations();
    }
/* 1577 */     else if (strActionCommand == "Splitless injection")
    {
/* 1579 */       switchToSplitlessMode();
/* 1580 */       performCalculations();
    }
/* 1582 */     else if (strActionCommand == "No plot")
    {
/* 1584 */       this.m_iSecondPlotType = 0;
/* 1585 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(true);
/* 1586 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(false);
/* 1587 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(false);
/* 1588 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(false);
/* 1589 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(false);
/* 1590 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(false);
/* 1591 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(false);
/* 1592 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(false);
/* 1593 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(false);
/* 1594 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(false);
/* 1595 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 1596 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(false);
/* 1597 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(false);
/* 1598 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(false);
/* 1599 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(false);
/* 1600 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(false);
/* 1601 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(false);
      
/* 1603 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(false));
/* 1604 */       this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 1605 */       this.m_iSecondPlotIndex = -1;
      
/* 1607 */       performCalculations();
    }
/* 1609 */     else if (strActionCommand == "Plot temperature")
    {
/* 1611 */       this.m_iSecondPlotType = 1;
/* 1612 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(false);
/* 1613 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(true);
/* 1614 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(false);
/* 1615 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(false);
/* 1616 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(false);
/* 1617 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(false);
/* 1618 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(false);
/* 1619 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(false);
/* 1620 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(false);
/* 1621 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(false);
/* 1622 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 1623 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(false);
/* 1624 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(false);
/* 1625 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(false);
/* 1626 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(false);
/* 1627 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(false);
/* 1628 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(false);
      
/* 1630 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(true));
/* 1631 */       this.contentPane.m_GraphControl.setSecondYAxisTitle("Oven temperature");
/* 1632 */       this.contentPane.m_GraphControl.setSecondYAxisBaseUnit("°C", "°C");
      
/* 1634 */       performCalculations();
    }
/* 1636 */     else if (strActionCommand == "Plot hold-up time")
    {
/* 1638 */       this.m_iSecondPlotType = 2;
/* 1639 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(false);
/* 1640 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(false);
/* 1641 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(true);
/* 1642 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(false);
/* 1643 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(false);
/* 1644 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(false);
/* 1645 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(false);
/* 1646 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(false);
/* 1647 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(false);
/* 1648 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(false);
/* 1649 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 1650 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(false);
/* 1651 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(false);
/* 1652 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(false);
/* 1653 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(false);
/* 1654 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(false);
/* 1655 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(false);
      
/* 1657 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(true));
/* 1658 */       this.contentPane.m_GraphControl.setSecondYAxisTitle("Hold-up time");
/* 1659 */       this.contentPane.m_GraphControl.setSecondYAxisBaseUnit("seconds", "s");
      
/* 1661 */       performCalculations();
    }
/* 1663 */     else if (strActionCommand == "Plot mobile phase viscosity")
    {
/* 1665 */       this.m_iSecondPlotType = 3;
/* 1666 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(false);
/* 1667 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(false);
/* 1668 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(false);
/* 1669 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(true);
/* 1670 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(false);
/* 1671 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(false);
/* 1672 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(false);
/* 1673 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(false);
/* 1674 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(false);
/* 1675 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(false);
/* 1676 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 1677 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(false);
/* 1678 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(false);
/* 1679 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(false);
/* 1680 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(false);
/* 1681 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(false);
/* 1682 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(false);
      
/* 1684 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(true));
/* 1685 */       this.contentPane.m_GraphControl.setSecondYAxisTitle("Mobile phase viscosity");
/* 1686 */       this.contentPane.m_GraphControl.setSecondYAxisBaseUnit("pascal seconds", "Pa s");
      
/* 1688 */       performCalculations();
    }
/* 1690 */     else if (strActionCommand == "Plot flow velocity")
    {
/* 1692 */       this.m_iSecondPlotType = 4;
/* 1693 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(false);
/* 1694 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(false);
/* 1695 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(false);
/* 1696 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(false);
/* 1697 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(true);
/* 1698 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(false);
/* 1699 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(false);
/* 1700 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(false);
/* 1701 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(false);
/* 1702 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(true);
/* 1703 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(true);
/* 1704 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(false);
/* 1705 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(false);
/* 1706 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(false);
/* 1707 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(false);
/* 1708 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(false);
/* 1709 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(false);
      
/* 1711 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(true));
/* 1712 */       this.contentPane.m_GraphControl.setSecondYAxisTitle("Flow velocity");
/* 1713 */       this.contentPane.m_GraphControl.setSecondYAxisBaseUnit("meters/second", "m/s");
      
/* 1715 */       performCalculations();
    }
/* 1717 */     else if (strActionCommand == "Plot mobile phase density")
    {
/* 1719 */       this.m_iSecondPlotType = 5;
/* 1720 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(false);
/* 1721 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(false);
/* 1722 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(false);
/* 1723 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(false);
/* 1724 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(false);
/* 1725 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(true);
/* 1726 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(false);
/* 1727 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(false);
/* 1728 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(false);
/* 1729 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(false);
/* 1730 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 1731 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(true);
/* 1732 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(true);
/* 1733 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(false);
/* 1734 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(false);
/* 1735 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(false);
/* 1736 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(false);
      
/* 1738 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(true));
/* 1739 */       this.contentPane.m_GraphControl.setSecondYAxisTitle("Mobile phase density");
/* 1740 */       this.contentPane.m_GraphControl.setSecondYAxisBaseUnit("grams/liter", "g/L");
      
/* 1742 */       performCalculations();
    }
/* 1744 */     else if (strActionCommand == "Plot pressure")
    {
/* 1746 */       this.m_iSecondPlotType = 6;
/* 1747 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(false);
/* 1748 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(false);
/* 1749 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(false);
/* 1750 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(false);
/* 1751 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(false);
/* 1752 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(false);
/* 1753 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(true);
/* 1754 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(false);
/* 1755 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(false);
/* 1756 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(false);
/* 1757 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 1758 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(false);
/* 1759 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(false);
/* 1760 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(true);
/* 1761 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(true);
/* 1762 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(false);
/* 1763 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(false);
      
/* 1765 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(true));
/* 1766 */       this.contentPane.m_GraphControl.setSecondYAxisTitle("Pressure");
/* 1767 */       this.contentPane.m_GraphControl.setSecondYAxisBaseUnit("pascals", "Pa");
      
/* 1769 */       performCalculations();
    }
/* 1771 */     else if (strActionCommand == "Plot retention factor")
    {
/* 1773 */       this.m_iSecondPlotType = 7;
/* 1774 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(false);
/* 1775 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(false);
/* 1776 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(false);
/* 1777 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(false);
/* 1778 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(false);
/* 1779 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(false);
/* 1780 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(false);
/* 1781 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(true);
/* 1782 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(false);
/* 1783 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(false);
/* 1784 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 1785 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(false);
/* 1786 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(false);
/* 1787 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(false);
/* 1788 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(false);
/* 1789 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(true);
/* 1790 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(false);
      
/* 1792 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(true));
/* 1793 */       this.contentPane.m_GraphControl.setSecondYAxisBaseUnit("k", "");
/* 1794 */       int iSelectedCompound = this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.getSelectedIndex();
/* 1795 */       String strCompoundName = "";
      
/* 1797 */       if ((iSelectedCompound < this.m_vectCompound.size()) && (iSelectedCompound >= 0)) {
/* 1798 */         strCompoundName = ((Compound)this.m_vectCompound.get(iSelectedCompound)).strCompoundName;
      }
/* 1800 */       this.contentPane.m_GraphControl.setSecondYAxisTitle("Retention factor of " + strCompoundName);
      
/* 1802 */       performCalculations();
    }
/* 1804 */     else if (strActionCommand == "Plot position")
    {
/* 1806 */       this.m_iSecondPlotType = 8;
/* 1807 */       this.contentPane.jxpanelPlotOptions.jrdoNoPlot.setSelected(false);
/* 1808 */       this.contentPane.jxpanelPlotOptions.jrdoTemperature.setSelected(false);
/* 1809 */       this.contentPane.jxpanelPlotOptions.jrdoHoldUpTime.setSelected(false);
/* 1810 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseViscosity.setSelected(false);
/* 1811 */       this.contentPane.jxpanelPlotOptions.jrdoFlowVelocity.setSelected(false);
/* 1812 */       this.contentPane.jxpanelPlotOptions.jrdoMobilePhaseDensity.setSelected(false);
/* 1813 */       this.contentPane.jxpanelPlotOptions.jrdoPressure.setSelected(false);
/* 1814 */       this.contentPane.jxpanelPlotOptions.jrdoRetentionFactor.setSelected(false);
/* 1815 */       this.contentPane.jxpanelPlotOptions.jrdoPosition.setSelected(true);
/* 1816 */       this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.setEnabled(false);
/* 1817 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 1818 */       this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.setEnabled(false);
/* 1819 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setEnabled(false);
/* 1820 */       this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.setEnabled(false);
/* 1821 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setEnabled(false);
/* 1822 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.setEnabled(false);
/* 1823 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.setEnabled(true);
      
/* 1825 */       this.contentPane.m_GraphControl.setSecondYAxisVisible(Boolean.valueOf(true));
/* 1826 */       int iSelectedCompound = this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.getSelectedIndex();
/* 1827 */       String strCompoundName = "";
      
/* 1829 */       if ((iSelectedCompound < this.m_vectCompound.size()) && (iSelectedCompound >= 0)) {
/* 1830 */         strCompoundName = ((Compound)this.m_vectCompound.get(iSelectedCompound)).strCompoundName;
      }
/* 1832 */       this.contentPane.m_GraphControl.setSecondYAxisTitle("Position of " + strCompoundName + " along column");
/* 1833 */       this.contentPane.m_GraphControl.setSecondYAxisBaseUnit("meters", "m");
/* 1834 */       this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(0.0D, this.m_dColumnLength);
      
/* 1836 */       performCalculations();
    }
/* 1838 */     else if (strActionCommand == "RetentionFactorCompoundChanged")
    {
/* 1840 */       if (this.m_iSecondPlotType == 7)
      {
/* 1842 */         int iSelectedCompound = this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.getSelectedIndex();
/* 1843 */         String strCompoundName = "";
        
/* 1845 */         if ((iSelectedCompound < this.m_vectCompound.size()) && (iSelectedCompound >= 0)) {
/* 1846 */           strCompoundName = ((Compound)this.m_vectCompound.get(iSelectedCompound)).strCompoundName;
        }
/* 1848 */         this.contentPane.m_GraphControl.setSecondYAxisTitle("Retention factor of " + strCompoundName);
      }
      
/* 1851 */       performCalculations();
    }
/* 1853 */     else if (strActionCommand == "PositionCompoundChanged")
    {
/* 1855 */       if (this.m_iSecondPlotType == 8)
      {
/* 1857 */         int iSelectedCompound = this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.getSelectedIndex();
/* 1858 */         String strCompoundName = "";
        
/* 1860 */         if ((iSelectedCompound < this.m_vectCompound.size()) && (iSelectedCompound >= 0)) {
/* 1861 */           strCompoundName = ((Compound)this.m_vectCompound.get(iSelectedCompound)).strCompoundName;
        }
/* 1863 */         this.contentPane.m_GraphControl.setSecondYAxisTitle("Position of " + strCompoundName + " along column");
      }
      
/* 1866 */       performCalculations();
    }
/* 1868 */     else if (strActionCommand == "Inlet pressure units changed")
    {
/* 1870 */       if (this.contentPane.jxpanelInletOutlet.jcboInletPressureUnits.getSelectedIndex() == 0)
      {
/* 1872 */         this.contentPane.jxpanelInletOutlet.jtxtInletPressure.setText(Float.toString((float)(this.m_dInletPressure - 101325.0D) / 1000.0F));
      }
      else
      {
/* 1876 */         this.contentPane.jxpanelInletOutlet.jtxtInletPressure.setText(Float.toString((float)this.m_dInletPressure / 1000.0F));
      }
    }
/* 1879 */     else if (strActionCommand == "Copy Image")
    {
/* 1881 */       ByteBuffer bytePixels = this.contentPane.m_GraphControl.getPixels();
      
/* 1883 */       int h = this.contentPane.m_GraphControl.getHeight();
/* 1884 */       int w = this.contentPane.m_GraphControl.getWidth();
/* 1885 */       if (w % 4 > 0) {
/* 1886 */         w += 4 - w % 4;
      }
/* 1888 */       byte[] flippedPixels = new byte[bytePixels.array().length];
      
/* 1890 */       for (int y = 0; y < h; y++)
      {
/* 1892 */         for (int x = 0; x < w * 4; x++)
        {
/* 1894 */           flippedPixels[(y * w * 4 + x)] = bytePixels.array()[((h - y - 1) * w * 4 + x)];
        }
      }
      
/* 1898 */       DataBuffer dbuf = new DataBufferByte(flippedPixels, flippedPixels.length, 0);
      
/* 1900 */       int[] bandOffsets = { 0, 1, 2, 3 };
/* 1901 */       SampleModel sampleModel = new ComponentSampleModel(0, this.contentPane.m_GraphControl.getWidth(), this.contentPane.m_GraphControl.getHeight(), 4, w * 4, bandOffsets);
/* 1902 */       WritableRaster raster = Raster.createWritableRaster(sampleModel, dbuf, null);
/* 1903 */       ComponentColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(1000), 
/* 1904 */         new int[] { 8, 8, 8, 8 }, 
/* 1905 */         true, 
/* 1906 */         false, 
/* 1907 */         1, 
/* 1908 */         0);
/* 1909 */       Image image = new BufferedImage(colorModel, raster, false, null);
      
/* 1911 */       new ImageIcon(image);
/* 1912 */       BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
/* 1913 */       newImage.createGraphics().drawImage(image, 0, 0, null);
/* 1914 */       image = newImage;
      
/* 1916 */       ImageSelection imageSelection = new ImageSelection(image);
/* 1917 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 1918 */       toolkit.getSystemClipboard().setContents(imageSelection, null);
    }
/* 1920 */     else if (evt.getSource() == this.menuResetToDefaultValuesAction)
    {
/* 1922 */       if (this.m_bDocumentChangedFlag) {
        String fileName;
        String fileName;
/* 1925 */         if (this.m_currentFile == null) {
/* 1926 */           fileName = "Untitled";
        } else {
/* 1928 */           fileName = this.m_currentFile.getName();
        }
/* 1930 */         int result = JOptionPane.showConfirmDialog(this, "Do you want to save changes to " + fileName + "?", "GC Simulator", 1);
        
/* 1932 */         if (result == 0)
        {
/* 1934 */           if (saveFile(false)) {}

        }
/* 1937 */         else if (result == 2)
        {
/* 1939 */           return;
        }
      }
/* 1942 */       this.m_currentFile = null;
      
/* 1944 */       loadFile(null);
    }
/* 1946 */     else if (evt.getSource() == this.menuLoadSettingsAction)
    {
/* 1948 */       JFileChooser fc = new JFileChooser();
/* 1949 */       FileNameExtensionFilter filter = new FileNameExtensionFilter("GC Simulator Files (*.gcsim)", new String[] { "gcsim" });
/* 1950 */       fc.setFileFilter(filter);
/* 1951 */       fc.setDialogTitle("Open");
/* 1952 */       int returnVal = fc.showOpenDialog(this);
/* 1953 */       if (returnVal == 0)
      {
/* 1955 */         this.m_currentFile = fc.getSelectedFile();
        
/* 1957 */         loadFile(this.m_currentFile);
      }
    }
/* 1960 */     else if (evt.getSource() == this.menuSaveSettingsAction)
    {
/* 1962 */       saveFile(false);
    }
/* 1964 */     else if (evt.getSource() == this.menuSaveSettingsAsAction)
    {
/* 1966 */       saveFile(true);
    }
/* 1968 */     else if (evt.getSource() == this.menuExitAction)
    {
/* 1970 */       if (this.m_bDocumentChangedFlag) {
        String fileName;
        String fileName;
/* 1973 */         if (this.m_currentFile == null) {
/* 1974 */           fileName = "Untitled";
        } else {
/* 1976 */           fileName = this.m_currentFile.getName();
        }
/* 1978 */         int result = JOptionPane.showConfirmDialog(this, "Do you want to save changes to " + fileName + "?", "GC Simulator", 1);
        
/* 1980 */         if (result == 0)
        {
/* 1982 */           if (saveFile(false)) {}

        }
/* 1985 */         else if (result == 2)
        {
/* 1987 */           return;
        }
      }
      
/* 1991 */       setVisible(false);
/* 1992 */       System.exit(0);
    }
/* 1994 */     else if (evt.getSource() == this.menuAboutAction)
    {

/* 1997 */       AboutDialog aboutDialog = new AboutDialog(this);
/* 1998 */       Point dialogPosition = new Point(getSize().width / 2, getSize().height / 2);
/* 1999 */       dialogPosition.x -= aboutDialog.getWidth() / 2;
/* 2000 */       dialogPosition.y -= aboutDialog.getHeight() / 2;
/* 2001 */       aboutDialog.setLocation(dialogPosition);
      

/* 2004 */       aboutDialog.setVisible(true);

    }
/* 2007 */     else if (evt.getSource() == this.menuHelpTopicsAction)
    {
/* 2009 */       Globals.hbMainHelpBroker.setCurrentID("getting_started");
/* 2010 */       Globals.hbMainHelpBroker.setDisplayed(true);
    }
  }
  

  public void stateChanged(ChangeEvent e)
  {
/* 2017 */     JSlider source = (JSlider)e.getSource();
/* 2018 */     if (source.getName() == "Temperature Slider")
    {
/* 2020 */       if (!this.m_bSliderUpdate) {
/* 2021 */         return;
      }
/* 2023 */       this.m_dTemperature = this.contentPane.jxpanelIsothermalOptions.jsliderTemperature.getValue();
/* 2024 */       this.contentPane.jxpanelIsothermalOptions.jtxtTemperature.setText(Integer.toString((int)this.m_dTemperature));
/* 2025 */       performCalculations();
    }
/* 2027 */     else if (source.getName() == "Plot flow velocity position slider")
    {
/* 2029 */       if (!this.m_bSliderUpdate) {
/* 2030 */         return;
      }
/* 2032 */       this.m_dPlotFlowVelocityPosition = (this.contentPane.jxpanelPlotOptions.jsliderFlowVelocityPosition.getValue() / 1000.0D);
/* 2033 */       this.contentPane.jxpanelPlotOptions.jtxtFlowVelocityColumnPosition.setText(Float.toString((float)this.m_dPlotFlowVelocityPosition));
/* 2034 */       performCalculations();
    }
/* 2036 */     else if (source.getName() == "Plot gas density position slider")
    {
/* 2038 */       if (!this.m_bSliderUpdate) {
/* 2039 */         return;
      }
/* 2041 */       this.m_dPlotGasDensityPosition = (this.contentPane.jxpanelPlotOptions.jsliderDensityPosition.getValue() / 1000.0D);
/* 2042 */       this.contentPane.jxpanelPlotOptions.jtxtDensityColumnPosition.setText(Float.toString((float)this.m_dPlotGasDensityPosition));
/* 2043 */       performCalculations();
    }
/* 2045 */     else if (source.getName() == "Plot pressure position slider")
    {
/* 2047 */       if (!this.m_bSliderUpdate) {
/* 2048 */         return;
      }
/* 2050 */       this.m_dPlotPressurePosition = (this.contentPane.jxpanelPlotOptions.jsliderPressurePosition.getValue() / 1000.0D);
/* 2051 */       this.contentPane.jxpanelPlotOptions.jtxtPressurePosition.setText(Float.toString((float)this.m_dPlotPressurePosition));
/* 2052 */       performCalculations();
    }
  }
  




  public void keyPressed(KeyEvent arg0) {}
  




  public void keyReleased(KeyEvent e) {}
  




  public void keyTyped(KeyEvent e)
  {
/* 2074 */     if ((!Character.isDigit(e.getKeyChar())) && 
/* 2075 */       (e.getKeyChar() != '\b') && 
/* 2076 */       (e.getKeyChar() != '') && 
/* 2077 */       (e.getKeyChar() != '.'))
    {
/* 2079 */       e.consume();
    }
    
/* 2082 */     if (e.getKeyChar() == '\n')
    {
/* 2084 */       performCalculations();
    }
  }
  










  double calcGasViscosity(double dTempK, double dGasType)
  {
/* 2100 */     double dEtast = 18.63D;
/* 2101 */     double dE0 = 0.6958D;
/* 2102 */     double dE1 = -0.0071D;
/* 2103 */     double dRefTempK = 273.15D;
    
/* 2105 */     if (dGasType == 0.0D)
    {

/* 2108 */       dEtast = 8.382D;
/* 2109 */       dE0 = 0.6892D;
/* 2110 */       dE1 = 0.005D;
    }
/* 2112 */     else if (dGasType == 1.0D)
    {

/* 2115 */       dEtast = 18.63D;
/* 2116 */       dE0 = 0.6958D;
/* 2117 */       dE1 = -0.0071D;
    }
/* 2119 */     else if (dGasType == 2.0D)
    {

/* 2122 */       dEtast = 16.62D;
/* 2123 */       dE0 = 0.7665D;
/* 2124 */       dE1 = -0.0378D;
    }
/* 2126 */     else if (dGasType == 3.0D)
    {

/* 2129 */       dEtast = 21.04D;
/* 2130 */       dE0 = 0.8131D;
/* 2131 */       dE1 = -0.0426D;
    }
    
/* 2134 */     double dEta = dEtast * 1.0E-6D * Math.pow(dTempK / dRefTempK, dE0 + dE1 * ((dTempK - dRefTempK) / dRefTempK));
/* 2135 */     return dEta;
  }
  









  double calcGasSelfDiffusionCoefficient(double dTempK, double dPressure, double dGasType)
  {
/* 2149 */     double dR = 8.3144621D;
/* 2150 */     double dM = 4.003D;
/* 2151 */     double dE = 0.685D;
    
/* 2153 */     if (dGasType == 0.0D)
    {

/* 2156 */       dM = 2.016D;
/* 2157 */       dE = 0.698D;
    }
/* 2159 */     else if (dGasType == 1.0D)
    {

/* 2162 */       dM = 4.003D;
/* 2163 */       dE = 0.685D;
    }
/* 2165 */     else if (dGasType == 2.0D)
    {

/* 2168 */       dM = 28.01D;
/* 2169 */       dE = 0.71D;
    }
/* 2171 */     else if (dGasType == 3.0D)
    {

/* 2174 */       dM = 39.95D;
/* 2175 */       dE = 0.75D;
    }
    
/* 2178 */     double dDgst = 6.0D * dR * 273.15D * calcGasViscosity(273.15D, dGasType) / (5.0D * (dM / 1000.0D) * 101325.0D);
/* 2179 */     double dDg = dDgst * (101325.0D / dPressure) * Math.pow(dTempK / 273.15D, 1.0D + dE);
/* 2180 */     return dDg;
  }
  









  double calcSoluteDiffusivityInGas(double dTempK, double dPressure, double dGasType)
  {
/* 2194 */     double dR = 8.3144621D;
/* 2195 */     double dMg = 4.003D;
/* 2196 */     double dVg = 2.67D;
    
/* 2198 */     if (dGasType == 0.0D)
    {

/* 2201 */       dMg = 2.016D;
/* 2202 */       dVg = 6.12D;
    }
/* 2204 */     else if (dGasType == 1.0D)
    {

/* 2207 */       dMg = 4.003D;
/* 2208 */       dVg = 2.67D;
    }
/* 2210 */     else if (dGasType == 2.0D)
    {

/* 2213 */       dMg = 28.01D;
/* 2214 */       dVg = 18.5D;
    }
/* 2216 */     else if (dGasType == 3.0D)
    {

/* 2219 */       dMg = 39.95D;
/* 2220 */       dVg = 16.2D;
    }
    

/* 2224 */     double dMsol = 100.0D;
/* 2225 */     double dVsol = 200.0D;
    
/* 2227 */     double dDsol = 100.0D * Math.sqrt(1.0D / dMg + 1.0D / dMsol) / Math.pow(Math.pow(dVg, 0.3333333333333333D) + Math.pow(dVsol, 0.3333333333333333D), 2.0D) * (1.0D / dPressure) * Math.pow(dTempK, 1.75D);
    
/* 2229 */     return dDsol * 1.0E-4D;
  }
  









  double calcGasPressure(double dZPos, double dInletPressure, double dOutletPressure)
  {
/* 2243 */     double dLext = Math.pow(dInletPressure, 2.0D) / (Math.pow(dInletPressure, 2.0D) - Math.pow(dOutletPressure, 2.0D));
/* 2244 */     double dPressure = dInletPressure * Math.sqrt(1.0D - dZPos / dLext);
/* 2245 */     return dPressure;
  }
  












  double calcHoldUpTime(double dTempK, double dGasType, double dInletPressure, double dOutletPressure, double dColumnLength, double dInnerDiameter)
  {
/* 2262 */     double dOmega = dColumnLength * calcGasViscosity(dTempK, dGasType) * (32.0D / Math.pow(dInnerDiameter, 2.0D));
/* 2263 */     double dDeadTime = 4.0D * dOmega * this.m_dColumnLength * (Math.pow(dInletPressure, 3.0D) - Math.pow(dOutletPressure, 3.0D)) / (3.0D * Math.pow(Math.pow(dInletPressure, 2.0D) - Math.pow(dOutletPressure, 2.0D), 2.0D));
/* 2264 */     return dDeadTime;
  }
  















  double calcFlowVelocity(double dZPos, double dTempK, double dGasType, double dInletPressure, double dOutletPressure, double dInnerDiameter, double dColumnLength)
  {
/* 2284 */     double dOmega = dColumnLength * calcGasViscosity(dTempK, dGasType) * (32.0D / Math.pow(dInnerDiameter, 2.0D));
/* 2285 */     double dInletVelocity = (Math.pow(dInletPressure, 2.0D) - Math.pow(dOutletPressure, 2.0D)) / (2.0D * dOmega * dInletPressure);
    



/* 2290 */     double dLext = Math.pow(dInletPressure, 2.0D) / (Math.pow(dInletPressure, 2.0D) - Math.pow(dOutletPressure, 2.0D));
    

/* 2293 */     double dVelocityAtZ = dInletVelocity / Math.sqrt(1.0D - dZPos / dLext);
    
/* 2295 */     return dVelocityAtZ;
  }
  












  double calcInletPressureAtConstFlowRate(double dFlowRate, double dTempK, double dGasType, double dOutletPressure, double dInnerDiameter, double dColumnLength)
  {
/* 2312 */     double dRefTempK = 298.15D;
/* 2313 */     double dPst = 101325.0D;
/* 2314 */     double dOmega = dColumnLength * calcGasViscosity(dTempK, dGasType) * (32.0D / Math.pow(dInnerDiameter, 2.0D));
/* 2315 */     double dSpecificFlowRate = dTempK / dRefTempK * (dFlowRate / 6.0E7D / dInnerDiameter);
/* 2316 */     double dInletPressure = Math.sqrt(dSpecificFlowRate * 8.0D * dPst * dOmega / (3.141592653589793D * dInnerDiameter) + Math.pow(dOutletPressure, 2.0D));
/* 2317 */     return dInletPressure;
  }
  









  double calcSoluteDiffusivityInStationaryPhase(double dTempK)
  {
/* 2331 */     double dR = 8.3144621D;
    

/* 2334 */     double dK = -14.36381977D;
/* 2335 */     double dE = 22101.91968D;
/* 2336 */     double dDs = Math.exp(dK - dE / (dR * dTempK));
    
/* 2338 */     return dDs;
  }
  












  double calcPlateHeight(double dSoluteDiffusionCoefficientInGas, double dSoluteDiffusionCoefficientInStationaryPhase, double dFlowVelocity, double dInnerDiameter, double dRetentionFactor, double dFilmThickness)
  {
/* 2355 */     double dTerm1 = 2.0D * dSoluteDiffusionCoefficientInGas / dFlowVelocity;
/* 2356 */     double dTerm2 = (1.0D + 6.0D * dRetentionFactor + 11.0D * Math.pow(dRetentionFactor, 2.0D)) * Math.pow(dInnerDiameter, 2.0D) * dFlowVelocity / (96.0D * Math.pow(1.0D + dRetentionFactor, 2.0D) * dSoluteDiffusionCoefficientInGas);
/* 2357 */     double dTerm3 = 2.0D * Math.pow(dFilmThickness, 2.0D) * dRetentionFactor * dFlowVelocity / (3.0D * Math.pow(1.0D + dRetentionFactor, 2.0D) * dSoluteDiffusionCoefficientInStationaryPhase);
    
/* 2359 */     return dTerm1 + dTerm2 + dTerm3;
  }
  
  public void performCalculations()
  {
/* 2364 */     NumberFormat formatter = new DecimalFormat("#0.0000");
    
/* 2366 */     validateTemperature();
/* 2367 */     validateColumnDiameter();
/* 2368 */     validateColumnLength();
/* 2369 */     validateStartTime();
/* 2370 */     validateEndTime();
/* 2371 */     validateFilmThickness();
/* 2372 */     validateFlowRate();
/* 2373 */     validateInitialTemperature();
/* 2374 */     validateInitialTime();
/* 2375 */     validateInjectionVolume();
/* 2376 */     validateInletPressure();
/* 2377 */     validateLinerInnerDiameter();
/* 2378 */     validateLinerLength();
/* 2379 */     validateNoise();
/* 2380 */     validateNumPoints();
/* 2381 */     validateOtherOutletPressure();
/* 2382 */     validateSignalOffset();
/* 2383 */     validateSplitRatio();
/* 2384 */     validateTimeConstant();
/* 2385 */     validateFlowVelocityAtColumnPosition();
/* 2386 */     validateGasDensityAtColumnPosition();
/* 2387 */     validatePressureAtColumnPosition();
    
/* 2389 */     calculateTemperatureProgram();
    

/* 2392 */     double dInnerDiameter = this.m_dColumnDiameter - 2.0D * this.m_dFilmThickness;
    

/* 2395 */     double dV0 = 3.141592653589793D * Math.pow(dInnerDiameter / 2.0D, 2.0D) * this.m_dColumnLength;
    

/* 2398 */     double dBeta1 = Math.pow(0.12475D, 2.0D) / (Math.pow(0.125D, 2.0D) - Math.pow(0.12475D, 2.0D));
/* 2399 */     double dBeta2 = Math.pow(this.m_dColumnDiameter / 2.0D - this.m_dFilmThickness, 2.0D) / (Math.pow(this.m_dColumnDiameter / 2.0D, 2.0D) - Math.pow(this.m_dColumnDiameter / 2.0D - this.m_dFilmThickness, 2.0D));
/* 2400 */     double dBeta1Beta2 = dBeta1 / dBeta2;
/* 2401 */     this.contentPane.jxpanelColumnProperties.jlblPhaseRatioIndicator.setText(formatter.format(dBeta2));
    

/* 2404 */     if (this.m_bTemperatureProgramMode)
    {
/* 2406 */       this.contentPane.jxpanelInletOutlet.jlblGasViscosityIndicator.setText("--");
    }
    else
    {
/* 2410 */       double dViscosity = calcGasViscosity(this.m_dTemperature + 273.15D, this.m_iCarrierGas);
/* 2411 */       this.contentPane.jxpanelInletOutlet.jlblGasViscosityIndicator.setText(formatter.format(dViscosity * 1000000.0D));
    }
    

/* 2415 */     if (this.m_bTemperatureProgramMode)
    {
/* 2417 */       this.contentPane.jxpanelColumnProperties.jlblHoldUpTimeIndicator.setText("--");
    }
    else {
      double dInletPressure;
      double dInletPressure;
/* 2422 */       if (this.m_bConstantFlowRateMode) {
/* 2423 */         dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, this.m_dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
      } else {
/* 2425 */         dInletPressure = this.m_dInletPressure;
      }
/* 2427 */       double dHoldUpTime = calcHoldUpTime(this.m_dTemperature + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, this.m_dColumnLength, dInnerDiameter);
/* 2428 */       this.contentPane.jxpanelColumnProperties.jlblHoldUpTimeIndicator.setText(formatter.format(dHoldUpTime / 60.0D));
    }
    
/* 2431 */     this.m_dLinerVolume = (3.141592653589793D * Math.pow(this.m_dLinerInnerDiameter / 2.0D, 2.0D) * this.m_dLinerLength);
/* 2432 */     this.contentPane.jxpanelInletOutlet.jlblLinerVolumeIndicator.setText(formatter.format(this.m_dLinerVolume * 1000000.0D));
    














































































































/* 2544 */     int iNumCompounds = this.m_vectCompound.size();
    
/* 2546 */     this.m_vectRetentionFactorArray = new Vector();
/* 2547 */     this.m_vectPositionArray = new Vector();
    
/* 2549 */     double dSumHETP = 0.0D;
    
/* 2551 */     for (int iCompound = 0; iCompound < iNumCompounds; iCompound++)
    {

/* 2554 */       double dTcA = 0.0D;
/* 2555 */       double dTcB = 0.0D;
/* 2556 */       double dt = 60.0D;
/* 2557 */       double t = 0.0D;
/* 2558 */       double z = 0.0D;
/* 2559 */       double lastz = 0.0D;
/* 2560 */       boolean bIsEluted = false;
/* 2561 */       double tR = 0.0D;
      

/* 2564 */       if (this.m_bTemperatureProgramMode) {
/* 2565 */         dTcA = this.m_lifTemperatureProgram.getAt(0.0D);
      }
/* 2567 */       while (!bIsEluted)
      {
/* 2569 */         t += dt;
        double dTc;
        double dTc;
/* 2572 */         if (this.m_bTemperatureProgramMode)
        {

/* 2575 */           dTcB = this.m_lifTemperatureProgram.getAt(t / 60.0D);
          

/* 2578 */           dTc = (dTcA + dTcB) / 2.0D;
        }
        else {
/* 2581 */           dTc = this.m_dTemperature;
        }
        double dInletPressure;
        double dInletPressure;
/* 2585 */         if (this.m_bConstantFlowRateMode) {
/* 2586 */           dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, dTc + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        } else {
/* 2588 */           dInletPressure = this.m_dInletPressure;
        }
/* 2590 */         double dFlowVelocity = calcFlowVelocity(z, dTc + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        

/* 2593 */         double k = Math.pow(10.0D, ((Compound)this.m_vectCompound.get(iCompound)).InterpolatedLogkvsT.getAt(dTc)) * dBeta1Beta2;
        

/* 2596 */         double dz = dt / (1.0D + k) * dFlowVelocity;
/* 2597 */         dz /= this.m_dColumnLength;
        
/* 2599 */         lastz = z;
/* 2600 */         z += dz;
        
/* 2602 */         if (z >= 1.0D)
        {
/* 2604 */           tR = (1.0D - lastz) / (z - lastz) * dt + t;
/* 2605 */           bIsEluted = true;
/* 2606 */           break;
        }
        
/* 2609 */         dTcA = dTcB;
      }
      


/* 2614 */       int numSlices = 1000;
/* 2615 */       dt = tR / numSlices;
/* 2616 */       t = 0.0D;
/* 2617 */       z = 0.0D;
/* 2618 */       lastz = 0.0D;
/* 2619 */       bIsEluted = false;
      
/* 2621 */       boolean bRecordRetentionFactor = false;
/* 2622 */       boolean bRecordPosition = false;
      
/* 2624 */       if (this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.getSelectedIndex() == iCompound) {
/* 2625 */         bRecordRetentionFactor = true;
      }
/* 2627 */       if (this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.getSelectedIndex() == iCompound) {
/* 2628 */         bRecordPosition = true;
      }
      

/* 2632 */       double[][] kvsz = new double[numSlices * 2][2];
/* 2633 */       int kvszSize = 0;
      

/* 2636 */       if (this.m_bTemperatureProgramMode) {
/* 2637 */         dTcA = this.m_lifTemperatureProgram.getAt(0.0D);
      }
/* 2639 */       while (!bIsEluted)
      {
/* 2641 */         t += dt;
        double dTc;
        double dTc;
/* 2644 */         if (this.m_bTemperatureProgramMode)
        {

/* 2647 */           dTcB = this.m_lifTemperatureProgram.getAt(t / 60.0D);
          

/* 2650 */           dTc = (dTcA + dTcB) / 2.0D;
        }
        else {
/* 2653 */           dTc = this.m_dTemperature;
        }
        double dInletPressure;
        double dInletPressure;
/* 2657 */         if (this.m_bConstantFlowRateMode) {
/* 2658 */           dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, dTc + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        } else {
/* 2660 */           dInletPressure = this.m_dInletPressure;
        }
/* 2662 */         double dFlowVelocity = calcFlowVelocity(z, dTc + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        

/* 2665 */         double k = Math.pow(10.0D, ((Compound)this.m_vectCompound.get(iCompound)).InterpolatedLogkvsT.getAt(dTc)) * dBeta1Beta2;
        

/* 2668 */         kvsz[kvszSize][0] = z;
/* 2669 */         kvsz[kvszSize][1] = k;
/* 2670 */         kvszSize++;
        

/* 2673 */         double dz = dt / (1.0D + k) * dFlowVelocity;
/* 2674 */         dz /= this.m_dColumnLength;
        
/* 2676 */         lastz = z;
/* 2677 */         z += dz;
        
/* 2679 */         if (z >= 1.0D)
        {
/* 2681 */           tR = (1.0D - lastz) / (z - lastz) * dt + t;
/* 2682 */           bIsEluted = true;
/* 2683 */           break;
        }
        
/* 2686 */         if (bRecordRetentionFactor)
        {
/* 2688 */           double[] temp = { t, k };
/* 2689 */           this.m_vectRetentionFactorArray.add(temp);
        }
        
/* 2692 */         if (bRecordPosition)
        {
/* 2694 */           double[] temp = { t, z * this.m_dColumnLength };
/* 2695 */           this.m_vectPositionArray.add(temp);
        }
        
/* 2698 */         dTcA = dTcB;
      }
      

/* 2702 */       LinearInterpolationFunction lifKvsZ = new LinearInterpolationFunction(kvsz);
      

/* 2705 */       t = 0.0D;
/* 2706 */       double dz = 0.002D;
/* 2707 */       double k = 1.0D;
/* 2708 */       double dSigmaSquaredPlateHeight = 0.0D;
/* 2709 */       double dSigmaSquaredTotal = 0.0D;
/* 2710 */       double dFlowVelocity = 0.0D;
/* 2711 */       double dLastFlowVelocity = -1.0D;
      
      double dTemperature;
      
      double dTemperature;
      
/* 2717 */       if (this.m_bTemperatureProgramMode) {
/* 2718 */         dTemperature = this.m_lifTemperatureProgram.getAt(0.0D);
      } else
/* 2720 */         dTemperature = this.m_dTemperature;
      double dInletPressure;
      double dInletPressure;
/* 2723 */       if (this.m_bConstantFlowRateMode) {
/* 2724 */         dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
      } else {
/* 2726 */         dInletPressure = this.m_dInletPressure;
      }
/* 2728 */       dFlowVelocity = calcFlowVelocity(0.0D, dTemperature + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
      
/* 2730 */       k = lifKvsZ.getAt(0.0D);
      

/* 2733 */       double dFlowRate = 3.141592653589793D * Math.pow(dInnerDiameter / 2.0D, 2.0D);
/* 2734 */       double dLengthTakenByInletGas = this.m_dLinerVolume / dFlowRate;
      

/* 2737 */       if (this.m_bSplitInjectionMode) {
/* 2738 */         dLengthTakenByInletGas /= (this.m_dSplitRatio + 1.0D);
      }
/* 2740 */       double dLengthTakenBySample = dLengthTakenByInletGas / (1.0D + k);
/* 2741 */       double dInjectionVarianceZ = 0.08333333333333333D * Math.pow(dLengthTakenBySample, 2.0D);
/* 2742 */       dSigmaSquaredTotal = dInjectionVarianceZ;
      
/* 2744 */       for (z = 0.0D; z < 1.0D; z += dz)
      {
/* 2746 */         if (this.m_bTemperatureProgramMode) {
/* 2747 */           dTemperature = this.m_lifTemperatureProgram.getAt(t / 60.0D);
        } else {
/* 2749 */           dTemperature = this.m_dTemperature;
        }
/* 2751 */         if (this.m_bConstantFlowRateMode) {
/* 2752 */           dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        } else {
/* 2754 */           dInletPressure = this.m_dInletPressure;
        }
/* 2756 */         dFlowVelocity = calcFlowVelocity(z, dTemperature + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        













/* 2771 */         k = lifKvsZ.getAt(z);
        

/* 2774 */         double dPressure = calcGasPressure(z, dInletPressure, this.m_dOutletPressure);
/* 2775 */         double dSoluteDiffusivityInGas = calcSoluteDiffusivityInGas(dTemperature + 273.15D, dPressure, this.m_iCarrierGas);
/* 2776 */         double dSoluteDiffusivityInStationaryPhase = calcSoluteDiffusivityInStationaryPhase(dTemperature + 273.15D);
        
/* 2778 */         double dPlateHeight = calcPlateHeight(dSoluteDiffusivityInGas, dSoluteDiffusivityInStationaryPhase, dFlowVelocity, dInnerDiameter, k, this.m_dFilmThickness);
/* 2779 */         dSigmaSquaredPlateHeight += dPlateHeight * (dz * this.m_dColumnLength);
        

/* 2782 */         dSigmaSquaredTotal += dPlateHeight * (dz * this.m_dColumnLength);
        

/* 2785 */         double dSigmaSquaredFromDecompression = 0.0D;
/* 2786 */         if (dLastFlowVelocity > 0.0D)
/* 2787 */           dSigmaSquaredFromDecompression = 2.0D * dSigmaSquaredTotal / dLastFlowVelocity * (dFlowVelocity - dLastFlowVelocity);
/* 2788 */         dSigmaSquaredTotal += dSigmaSquaredFromDecompression;
        
/* 2790 */         dLastFlowVelocity = dFlowVelocity;
      }
      
/* 2793 */       if (this.m_bTemperatureProgramMode) {
/* 2794 */         ((Vector)this.contentPane.vectChemicalRows.get(iCompound)).set(2, "--");
      }
      else {
/* 2797 */         ((Vector)this.contentPane.vectChemicalRows.get(iCompound)).set(2, formatter.format(k));
/* 2798 */         if (this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.getSelectedIndex() == iCompound)
        {
/* 2800 */           this.m_dSelectedIsocraticRetentionFactor = k;
        }
      }
      
/* 2804 */       ((Compound)this.m_vectCompound.get(iCompound)).dRetentionTime = tR;
/* 2805 */       ((Vector)this.contentPane.vectChemicalRows.get(iCompound)).set(3, formatter.format(tR / 60.0D));
      

/* 2808 */       double dSigmaL = Math.sqrt(dSigmaSquaredTotal);
/* 2809 */       double dSigmaT = dSigmaL / (dFlowVelocity / (1.0D + k));
/* 2810 */       double dHETP = dSigmaSquaredPlateHeight / this.m_dColumnLength;
/* 2811 */       dSumHETP += dHETP;
      

/* 2814 */       dSigmaT = Math.sqrt(Math.pow(dSigmaT, 2.0D) + Math.pow(this.m_dTimeConstant, 2.0D));
/* 2815 */       ((Compound)this.m_vectCompound.get(iCompound)).dSigma = dSigmaT;
/* 2816 */       ((Vector)this.contentPane.vectChemicalRows.get(iCompound)).set(4, formatter.format(dSigmaT));
      
/* 2818 */       double dW = this.m_dInjectionVolume / 1000000.0D * ((Compound)this.m_vectCompound.get(iCompound)).dConcentration;
/* 2819 */       if (this.m_bSplitInjectionMode)
/* 2820 */         dW /= (this.m_dSplitRatio + 1.0D);
/* 2821 */       ((Compound)this.m_vectCompound.get(iCompound)).dW = dW;
/* 2822 */       ((Vector)this.contentPane.vectChemicalRows.get(iCompound)).set(5, formatter.format(dW * 1000000.0D));
    }
    
/* 2825 */     double dHETP = dSumHETP / iNumCompounds;
/* 2826 */     this.contentPane.jxpanelColumnProperties.jlblHETPIndicator.setText(formatter.format(dHETP * 1000.0D));
/* 2827 */     this.contentPane.jxpanelColumnProperties.jlblTheoreticalPlatesIndicator.setText(Integer.toString((int)(this.m_dColumnLength / dHETP)));
    

/* 2830 */     if (this.m_bAutomaticTimeRange)
    {

/* 2833 */       double dLongestRetentionTime = 0.0D;
      
/* 2835 */       for (int i = 0; i < this.m_vectCompound.size(); i++)
      {
/* 2837 */         if (((Compound)this.m_vectCompound.get(i)).dRetentionTime > dLongestRetentionTime)
        {
/* 2839 */           dLongestRetentionTime = ((Compound)this.m_vectCompound.get(i)).dRetentionTime;
        }
      }
      
/* 2843 */       this.m_dStartTime = 0.0D;
/* 2844 */       this.m_dEndTime = (dLongestRetentionTime * 1.1D);
      
/* 2846 */       this.contentPane.jxpanelPlotOptions.jtxtFinalTime.setText(Float.toString((float)this.m_dEndTime));
/* 2847 */       this.contentPane.jxpanelPlotOptions.jtxtInitialTime.setText("0");
    }
    

/* 2851 */     this.contentPane.m_GraphControl.RemoveAllSeries();
    

/* 2854 */     if (this.m_iSecondPlotType == 1) {
/* 2855 */       plotTemperatureProgram();
/* 2856 */     } else if (this.m_iSecondPlotType == 2) {
/* 2857 */       plotHoldUpTime();
/* 2858 */     } else if (this.m_iSecondPlotType == 3) {
/* 2859 */       plotViscosity();
/* 2860 */     } else if (this.m_iSecondPlotType == 4) {
/* 2861 */       plotFlowVelocity();
/* 2862 */     } else if (this.m_iSecondPlotType == 5) {
/* 2863 */       plotGasDensity();
/* 2864 */     } else if (this.m_iSecondPlotType == 6) {
/* 2865 */       plotGasPressure();
/* 2866 */     } else if (this.m_iSecondPlotType == 7) {
/* 2867 */       plotRetentionFactor();
/* 2868 */     } else if (this.m_iSecondPlotType == 8) {
/* 2869 */       plotPosition();
    }
    
/* 2872 */     Random random = new Random();
    

/* 2875 */     this.m_iChromatogramPlotIndex = -1;
    


/* 2879 */     this.m_iSinglePlotIndex = -1;
    
/* 2881 */     Color clrOrange = new Color(240, 90, 40);
/* 2882 */     Color clrOrange2 = new Color(132, 48, 2);
/* 2883 */     Color clrBlack = new Color(0, 0, 0);
/* 2884 */     Color clrBlue = new Color(98, 101, 214);
/* 2885 */     Color clrRed = new Color(206, 70, 70);
/* 2886 */     if (this.m_vectCompound.size() > 0)
    {
/* 2888 */       this.m_iChromatogramPlotIndex = this.contentPane.m_GraphControl.AddSeries("Chromatogram", clrBlack, 1, false, false);
      
/* 2890 */       int iRowSel = this.contentPane.jtableChemicals.getSelectedRow();
/* 2891 */       if ((iRowSel >= 0) && (iRowSel < this.contentPane.vectChemicalRows.size()))
      {
/* 2893 */         this.m_iSinglePlotIndex = this.contentPane.m_GraphControl.AddSeries("Single", clrOrange, 1, false, false);
      }
      
/* 2896 */       for (int i = 0; i < this.m_iNumPoints; i++)
      {
/* 2898 */         double dTime = this.m_dStartTime + i * ((this.m_dEndTime - this.m_dStartTime) / this.m_iNumPoints);
/* 2899 */         double dNoise = random.nextGaussian() * (this.m_dNoise / 1.0E9D);
/* 2900 */         double dCTotal = dNoise / Math.sqrt(this.m_dTimeConstant) + this.m_dSignalOffset;
        

/* 2903 */         for (int j = 0; j < this.m_vectCompound.size(); j++)
        {
/* 2905 */           Compound curCompound = (Compound)this.m_vectCompound.get(j);
          
/* 2907 */           double dCthis = curCompound.dW / 1000000.0D / (Math.sqrt(6.283185307179586D) * curCompound.dSigma * (this.m_dFlowRate / 60000.0D)) * Math.exp(-Math.pow(dTime - curCompound.dRetentionTime, 2.0D) / (2.0D * Math.pow(curCompound.dSigma, 2.0D)));
/* 2908 */           dCTotal += dCthis;
          

/* 2911 */           if ((this.m_iSinglePlotIndex >= 0) && (j == iRowSel)) {
/* 2912 */             this.contentPane.m_GraphControl.AddDataPoint(this.m_iSinglePlotIndex, dTime, dCthis + this.m_dSignalOffset);
          }
        }
/* 2915 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iChromatogramPlotIndex, dTime, dCTotal);
      }
      
/* 2918 */       if (this.contentPane.jbtnAutoscaleX.isSelected())
/* 2919 */         this.contentPane.m_GraphControl.AutoScaleX();
/* 2920 */       if (this.contentPane.jbtnAutoscaleY.isSelected()) {
/* 2921 */         this.contentPane.m_GraphControl.AutoScaleY();
      }
    }
/* 2924 */     this.contentPane.m_GraphControl.repaint();
/* 2925 */     this.contentPane.jtableChemicals.addNotify();
  }
  



  public void focusGained(FocusEvent e) {}
  


  public void focusLost(FocusEvent e)
  {
/* 2937 */     performCalculations();
  }
  

  public void valueChanged(ListSelectionEvent arg0)
  {
/* 2943 */     performCalculations();
  }
  

  public void autoScaleChanged(AutoScaleEvent event)
  {
/* 2949 */     if (event.getAutoScaleXState()) {
/* 2950 */       this.contentPane.jbtnAutoscaleX.setSelected(true);
    } else {
/* 2952 */       this.contentPane.jbtnAutoscaleX.setSelected(false);
    }
/* 2954 */     if (event.getAutoScaleYState()) {
/* 2955 */       this.contentPane.jbtnAutoscaleY.setSelected(true);
    } else {
/* 2957 */       this.contentPane.jbtnAutoscaleY.setSelected(false);
    }
/* 2959 */     if ((event.getAutoScaleXState()) && (event.getAutoScaleYState())) {
/* 2960 */       this.contentPane.jbtnAutoscale.setSelected(true);
    } else {
/* 2962 */       this.contentPane.jbtnAutoscale.setSelected(false);
    }
  }
  







  public void tableChanged(TableModelEvent e) {}
  






  public void calculateTemperatureProgram()
  {
/* 2983 */     this.m_dTemperatureProgramArray = new double[this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getRowCount() * 2 + 3][2];
/* 2984 */     int iPointCount = 0;
    
/* 2986 */     this.m_dTemperatureProgramArray[iPointCount][0] = 0.0D;
/* 2987 */     this.m_dTemperatureProgramArray[iPointCount][1] = this.m_dInitialTemperature;
/* 2988 */     iPointCount++;
    
/* 2990 */     if (this.m_dInitialTime > 0.0D)
    {
/* 2992 */       this.m_dTemperatureProgramArray[iPointCount][0] = this.m_dInitialTime;
/* 2993 */       this.m_dTemperatureProgramArray[iPointCount][1] = this.m_dInitialTemperature;
/* 2994 */       iPointCount++;
    }
    
/* 2997 */     double dTotalTime = this.m_dInitialTime;
/* 2998 */     double dLastTemp = this.m_dInitialTemperature;
/* 2999 */     double dFinalTemp = this.m_dInitialTemperature;
    

/* 3002 */     for (int i = 0; i < this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getRowCount(); i++)
    {
/* 3004 */       double dRamp = ((Double)this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getValueAt(i, 0)).doubleValue();
/* 3005 */       dFinalTemp = ((Double)this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getValueAt(i, 1)).doubleValue();
/* 3006 */       double dFinalTime = ((Double)this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getValueAt(i, 2)).doubleValue();
      
/* 3008 */       if (dRamp != 0.0D)
      {
/* 3010 */         dTotalTime += (dFinalTemp - dLastTemp) / dRamp;
/* 3011 */         this.m_dTemperatureProgramArray[iPointCount][0] = dTotalTime;
/* 3012 */         this.m_dTemperatureProgramArray[iPointCount][1] = dFinalTemp;
/* 3013 */         iPointCount++;
      }
      
/* 3016 */       if (dFinalTime != 0.0D)
      {
/* 3018 */         if (i < this.contentPane.jxpanelTemperatureProgramOptions.tmTemperatureProgram.getRowCount() - 1)
        {
/* 3020 */           dTotalTime += dFinalTime;
/* 3021 */           this.m_dTemperatureProgramArray[iPointCount][0] = dTotalTime;
/* 3022 */           this.m_dTemperatureProgramArray[iPointCount][1] = dFinalTemp;
/* 3023 */           iPointCount++;
        }
      }
      
/* 3027 */       dLastTemp = dFinalTemp;
    }
    

/* 3031 */     this.m_dTemperatureProgramArray[iPointCount][0] = (dTotalTime + 1.0D);
/* 3032 */     this.m_dTemperatureProgramArray[iPointCount][1] = this.m_dTemperatureProgramArray[(iPointCount - 1)][1];
/* 3033 */     iPointCount++;
    


/* 3037 */     double[][] tempArray = new double[iPointCount][2];
/* 3038 */     for (int i = 0; i < iPointCount; i++)
    {
/* 3040 */       tempArray[i][0] = this.m_dTemperatureProgramArray[i][0];
/* 3041 */       tempArray[i][1] = this.m_dTemperatureProgramArray[i][1];
    }
/* 3043 */     this.m_dTemperatureProgramArray = tempArray;
    

/* 3046 */     this.m_lifTemperatureProgram = new LinearInterpolationFunction(this.m_dTemperatureProgramArray);
  }
  
  public void plotTemperatureProgram()
  {
/* 3051 */     this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 3052 */     this.m_iSecondPlotIndex = -1;
    
/* 3054 */     this.m_iSecondPlotIndex = this.contentPane.m_GraphControl.AddSeries("SecondPlot", new Color(130, 130, 130), 1, false, true);
    
/* 3056 */     double dTemperatureMin = 9.99999999E8D;
/* 3057 */     double dTemperatureMax = 0.0D;
    
/* 3059 */     if (this.m_bTemperatureProgramMode)
    {
/* 3061 */       for (int i = 0; i < this.m_dTemperatureProgramArray.length; i++)
      {
/* 3063 */         double dTemperature = this.m_dTemperatureProgramArray[i][1];
        
/* 3065 */         if (dTemperature < dTemperatureMin)
/* 3066 */           dTemperatureMin = dTemperature;
/* 3067 */         if (dTemperature > dTemperatureMax) {
/* 3068 */           dTemperatureMax = dTemperature;
        }
/* 3070 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, this.m_dTemperatureProgramArray[i][0] * 60.0D, this.m_dTemperatureProgramArray[i][1]);
      }
      
/* 3073 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, this.m_dTemperatureProgramArray[(this.m_dTemperatureProgramArray.length - 1)][1]);
      
/* 3075 */       if (dTemperatureMin == dTemperatureMax)
      {
/* 3077 */         dTemperatureMin *= 0.8D;
/* 3078 */         dTemperatureMax *= 1.2D;
      }
      
    }
    else
    {
/* 3084 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 0.0D, this.m_dTemperature);
/* 3085 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, this.m_dTemperature);
/* 3086 */       dTemperatureMin = 60.0D;
/* 3087 */       dTemperatureMax = 320.0D;
    }
    
/* 3090 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(dTemperatureMin, dTemperatureMax);
  }
  
  public void plotHoldUpTime()
  {
/* 3095 */     this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 3096 */     this.m_iSecondPlotIndex = -1;
    
/* 3098 */     this.m_iSecondPlotIndex = this.contentPane.m_GraphControl.AddSeries("SecondPlot", new Color(130, 130, 130), 1, false, true);
    
/* 3100 */     double dHoldUpTimeMin = 9.99999999E8D;
/* 3101 */     double dHoldUpTimeMax = 0.0D;
    

/* 3104 */     double dInnerDiameter = this.m_dColumnDiameter - 2.0D * this.m_dFilmThickness;
    
/* 3106 */     if (this.m_bTemperatureProgramMode)
    {
/* 3108 */       double dFinalTime = this.m_dTemperatureProgramArray[(this.m_dTemperatureProgramArray.length - 1)][0] * 60.0D;
/* 3109 */       double dt = dFinalTime / 100.0D;
      
/* 3111 */       double dHoldUpTime = 0.0D;
      
/* 3113 */       for (double t = 0.0D; t <= dFinalTime; t += dt)
      {
/* 3115 */         double dTemperature = this.m_lifTemperatureProgram.getAt(t / 60.0D);
        double dInletPressure;
        double dInletPressure;
/* 3118 */         if (this.m_bConstantFlowRateMode) {
/* 3119 */           dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        } else {
/* 3121 */           dInletPressure = this.m_dInletPressure;
        }
/* 3123 */         dHoldUpTime = calcHoldUpTime(dTemperature + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, this.m_dColumnLength, dInnerDiameter);
        
/* 3125 */         if (dHoldUpTime < dHoldUpTimeMin)
/* 3126 */           dHoldUpTimeMin = dHoldUpTime;
/* 3127 */         if (dHoldUpTime > dHoldUpTimeMax) {
/* 3128 */           dHoldUpTimeMax = dHoldUpTime;
        }
/* 3130 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, t, dHoldUpTime);
      }
      
/* 3133 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dHoldUpTime);
      
/* 3135 */       if (dHoldUpTimeMin == dHoldUpTimeMax)
      {
/* 3137 */         dHoldUpTimeMin *= 0.8D;
/* 3138 */         dHoldUpTimeMax *= 1.2D;
      }
    }
    else
    {
      double dInletPressure;
      double dInletPressure;
/* 3145 */       if (this.m_bConstantFlowRateMode) {
/* 3146 */         dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, this.m_dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
      } else {
/* 3148 */         dInletPressure = this.m_dInletPressure;
      }
/* 3150 */       double dHoldUpTime = calcHoldUpTime(this.m_dTemperature + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, this.m_dColumnLength, dInnerDiameter);
      
/* 3152 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 0.0D, dHoldUpTime);
/* 3153 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dHoldUpTime);
/* 3154 */       dHoldUpTimeMin = dHoldUpTime * 0.8D;
/* 3155 */       dHoldUpTimeMax = dHoldUpTime * 1.2D;
    }
    
/* 3158 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(dHoldUpTimeMin, dHoldUpTimeMax);
  }
  
  public void plotViscosity()
  {
/* 3163 */     this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 3164 */     this.m_iSecondPlotIndex = -1;
    
/* 3166 */     this.m_iSecondPlotIndex = this.contentPane.m_GraphControl.AddSeries("SecondPlot", new Color(130, 130, 130), 1, false, true);
    
/* 3168 */     double dViscosityMin = 9.99999999E8D;
/* 3169 */     double dViscosityMax = 0.0D;
    
/* 3171 */     if (this.m_bTemperatureProgramMode)
    {
/* 3173 */       double dFinalTime = this.m_dTemperatureProgramArray[(this.m_dTemperatureProgramArray.length - 1)][0] * 60.0D;
/* 3174 */       double dt = dFinalTime / 100.0D;
      
/* 3176 */       double dViscosity = 0.0D;
      
/* 3178 */       for (double t = 0.0D; t <= dFinalTime; t += dt)
      {
/* 3180 */         double dTemperature = this.m_lifTemperatureProgram.getAt(t / 60.0D);
        
/* 3182 */         dViscosity = calcGasViscosity(dTemperature + 273.15D, this.m_iCarrierGas);
        
/* 3184 */         if (dViscosity < dViscosityMin)
/* 3185 */           dViscosityMin = dViscosity;
/* 3186 */         if (dViscosity > dViscosityMax) {
/* 3187 */           dViscosityMax = dViscosity;
        }
/* 3189 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, t, dViscosity);
      }
      
/* 3192 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dViscosity);
      
/* 3194 */       if (dViscosityMin == dViscosityMax)
      {
/* 3196 */         dViscosityMin *= 0.8D;
/* 3197 */         dViscosityMax *= 1.2D;
      }
      
    }
    else
    {
/* 3203 */       double dViscosity = calcGasViscosity(this.m_dTemperature + 273.15D, this.m_iCarrierGas);
      
/* 3205 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 0.0D, dViscosity);
/* 3206 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dViscosity);
/* 3207 */       dViscosityMin = dViscosity * 0.8D;
/* 3208 */       dViscosityMax = dViscosity * 1.2D;
    }
    
/* 3211 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(dViscosityMin, dViscosityMax);
  }
  
  public void plotFlowVelocity()
  {
/* 3216 */     this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 3217 */     this.m_iSecondPlotIndex = -1;
    
/* 3219 */     this.m_iSecondPlotIndex = this.contentPane.m_GraphControl.AddSeries("SecondPlot", new Color(130, 130, 130), 1, false, true);
    
/* 3221 */     double dFlowVelocityMin = 9.99999999E8D;
/* 3222 */     double dFlowVelocityMax = 0.0D;
    
/* 3224 */     double dPlotFlowVelocityPosition = this.m_dPlotFlowVelocityPosition;
/* 3225 */     if (this.m_dPlotFlowVelocityPosition == 1.0D) {
/* 3226 */       dPlotFlowVelocityPosition = 0.9999999D;
    }
    
/* 3229 */     double dInnerDiameter = this.m_dColumnDiameter - 2.0D * this.m_dFilmThickness;
    
/* 3231 */     if (this.m_bTemperatureProgramMode)
    {
/* 3233 */       double dFinalTime = this.m_dTemperatureProgramArray[(this.m_dTemperatureProgramArray.length - 1)][0] * 60.0D;
/* 3234 */       double dt = dFinalTime / 100.0D;
      
/* 3236 */       double dFlowVelocity = 0.0D;
      
/* 3238 */       for (double t = 0.0D; t <= dFinalTime; t += dt)
      {
/* 3240 */         double dTemperature = this.m_lifTemperatureProgram.getAt(t / 60.0D);
        double dInletPressure;
        double dInletPressure;
/* 3243 */         if (this.m_bConstantFlowRateMode) {
/* 3244 */           dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        } else {
/* 3246 */           dInletPressure = this.m_dInletPressure;
        }
/* 3248 */         dFlowVelocity = calcFlowVelocity(dPlotFlowVelocityPosition, dTemperature + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        
/* 3250 */         if (dFlowVelocity < dFlowVelocityMin)
/* 3251 */           dFlowVelocityMin = dFlowVelocity;
/* 3252 */         if (dFlowVelocity > dFlowVelocityMax) {
/* 3253 */           dFlowVelocityMax = dFlowVelocity;
        }
/* 3255 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, t, dFlowVelocity);
      }
      
/* 3258 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dFlowVelocity);
      
/* 3260 */       if (dFlowVelocityMin == dFlowVelocityMax)
      {
/* 3262 */         dFlowVelocityMin *= 0.8D;
/* 3263 */         dFlowVelocityMax *= 1.2D;
      }
    }
    else
    {
      double dInletPressure;
      double dInletPressure;
/* 3270 */       if (this.m_bConstantFlowRateMode) {
/* 3271 */         dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, this.m_dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
      } else {
/* 3273 */         dInletPressure = this.m_dInletPressure;
      }
/* 3275 */       double dFlowVelocity = calcFlowVelocity(dPlotFlowVelocityPosition, this.m_dTemperature + 273.15D, this.m_iCarrierGas, dInletPressure, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
      
/* 3277 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 0.0D, dFlowVelocity);
/* 3278 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dFlowVelocity);
/* 3279 */       dFlowVelocityMin = dFlowVelocity * 0.8D;
/* 3280 */       dFlowVelocityMax = dFlowVelocity * 1.2D;
    }
    
/* 3283 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(dFlowVelocityMin, dFlowVelocityMax);
  }
  
  public void plotGasDensity()
  {
/* 3288 */     this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 3289 */     this.m_iSecondPlotIndex = -1;
    
/* 3291 */     this.m_iSecondPlotIndex = this.contentPane.m_GraphControl.AddSeries("SecondPlot", new Color(130, 130, 130), 1, false, true);
    
/* 3293 */     double dDensityMin = 9.99999999E8D;
/* 3294 */     double dDensityMax = 0.0D;
    

/* 3297 */     double dInnerDiameter = this.m_dColumnDiameter - 2.0D * this.m_dFilmThickness;
    
/* 3299 */     if (this.m_bTemperatureProgramMode)
    {
/* 3301 */       double dFinalTime = this.m_dTemperatureProgramArray[(this.m_dTemperatureProgramArray.length - 1)][0] * 60.0D;
/* 3302 */       double dt = dFinalTime / 100.0D;
      
/* 3304 */       double dDensity = 0.0D;
      
/* 3306 */       for (double t = 0.0D; t <= dFinalTime; t += dt)
      {
/* 3308 */         double dTemperature = this.m_lifTemperatureProgram.getAt(t / 60.0D);
        double dInletPressure;
        double dInletPressure;
/* 3311 */         if (this.m_bConstantFlowRateMode) {
/* 3312 */           dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        } else
/* 3314 */           dInletPressure = this.m_dInletPressure;
        double dPressure;
        double dPressure;
/* 3317 */         if (this.m_dPlotGasDensityPosition == 1.0D) {
/* 3318 */           dPressure = this.m_dOutletPressure;
        } else {
/* 3320 */           dPressure = calcGasPressure(this.m_dPlotGasDensityPosition, dInletPressure, this.m_dOutletPressure);
        }
        
/* 3323 */         double dMolarity = dPressure / (8.3144621D * (dTemperature + 273.15D));
/* 3324 */         double dMg = 2.016D;
/* 3325 */         if (this.m_iCarrierGas == 0)
        {

/* 3328 */           dMg = 2.016D;
        }
/* 3330 */         else if (this.m_iCarrierGas == 1)
        {

/* 3333 */           dMg = 4.003D;
        }
/* 3335 */         else if (this.m_iCarrierGas == 2)
        {

/* 3338 */           dMg = 28.01D;
        }
/* 3340 */         else if (this.m_iCarrierGas == 3)
        {

/* 3343 */           dMg = 39.95D;
        }
        
/* 3346 */         dDensity = dMolarity * dMg;
        
/* 3348 */         if (dDensity < dDensityMin)
/* 3349 */           dDensityMin = dDensity;
/* 3350 */         if (dDensity > dDensityMax) {
/* 3351 */           dDensityMax = dDensity;
        }
/* 3353 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, t, dDensity);
      }
      
/* 3356 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dDensity);
      
/* 3358 */       if (dDensityMin == dDensityMax)
      {
/* 3360 */         dDensityMin *= 0.8D;
/* 3361 */         dDensityMax *= 1.2D;
      }
    }
    else
    {
      double dInletPressure;
      double dInletPressure;
/* 3368 */       if (this.m_bConstantFlowRateMode) {
/* 3369 */         dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, this.m_dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
      } else
/* 3371 */         dInletPressure = this.m_dInletPressure;
      double dPressure;
      double dPressure;
/* 3374 */       if (this.m_dPlotGasDensityPosition == 1.0D) {
/* 3375 */         dPressure = this.m_dOutletPressure;
      } else {
/* 3377 */         dPressure = calcGasPressure(this.m_dPlotGasDensityPosition, dInletPressure, this.m_dOutletPressure);
      }
      
/* 3380 */       double dMolarity = dPressure / (8.3144621D * (this.m_dTemperature + 273.15D));
/* 3381 */       double dMg = 2.016D;
/* 3382 */       if (this.m_iCarrierGas == 0)
      {

/* 3385 */         dMg = 2.016D;
      }
/* 3387 */       else if (this.m_iCarrierGas == 1)
      {

/* 3390 */         dMg = 4.003D;
      }
/* 3392 */       else if (this.m_iCarrierGas == 2)
      {

/* 3395 */         dMg = 28.01D;
      }
/* 3397 */       else if (this.m_iCarrierGas == 3)
      {

/* 3400 */         dMg = 39.95D;
      }
      
/* 3403 */       double dDensity = dMolarity * dMg;
/* 3404 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 0.0D, dDensity);
/* 3405 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dDensity);
/* 3406 */       dDensityMin = dDensity * 0.8D;
/* 3407 */       dDensityMax = dDensity * 1.2D;
    }
    
/* 3410 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(dDensityMin, dDensityMax);
  }
  
  public void plotGasPressure()
  {
/* 3415 */     this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 3416 */     this.m_iSecondPlotIndex = -1;
    
/* 3418 */     this.m_iSecondPlotIndex = this.contentPane.m_GraphControl.AddSeries("SecondPlot", new Color(130, 130, 130), 1, false, true);
    
/* 3420 */     double dPressureMin = 9.99999999E8D;
/* 3421 */     double dPressureMax = 0.0D;
    
/* 3423 */     if (this.m_dPlotPressurePosition == 1.0D)
    {
/* 3425 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 0.0D, this.m_dOutletPressure);
/* 3426 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, this.m_dOutletPressure);
/* 3427 */       dPressureMin = this.m_dOutletPressure * 0.8D;
/* 3428 */       dPressureMax = this.m_dOutletPressure * 1.2D;

    }
    else
    {
/* 3433 */       double dInnerDiameter = this.m_dColumnDiameter - 2.0D * this.m_dFilmThickness;
      
/* 3435 */       if (this.m_bTemperatureProgramMode)
      {
/* 3437 */         double dFinalTime = this.m_dTemperatureProgramArray[(this.m_dTemperatureProgramArray.length - 1)][0] * 60.0D;
/* 3438 */         double dt = dFinalTime / 100.0D;
        
/* 3440 */         double dPressure = 0.0D;
        
/* 3442 */         for (double t = 0.0D; t <= dFinalTime; t += dt)
        {
/* 3444 */           double dTemperature = this.m_lifTemperatureProgram.getAt(t / 60.0D);
          double dInletPressure;
          double dInletPressure;
/* 3447 */           if (this.m_bConstantFlowRateMode) {
/* 3448 */             dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
          } else {
/* 3450 */             dInletPressure = this.m_dInletPressure;
          }
/* 3452 */           dPressure = calcGasPressure(this.m_dPlotPressurePosition, dInletPressure, this.m_dOutletPressure);
          
/* 3454 */           if (dPressure < dPressureMin)
/* 3455 */             dPressureMin = dPressure;
/* 3456 */           if (dPressure > dPressureMax) {
/* 3457 */             dPressureMax = dPressure;
          }
/* 3459 */           this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, t, dPressure);
        }
        
/* 3462 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dPressure);
        
/* 3464 */         if (dPressureMin == dPressureMax)
        {
/* 3466 */           dPressureMin *= 0.8D;
/* 3467 */           dPressureMax *= 1.2D;
        }
      }
      else
      {
        double dInletPressure;
        double dInletPressure;
/* 3474 */         if (this.m_bConstantFlowRateMode) {
/* 3475 */           dInletPressure = calcInletPressureAtConstFlowRate(this.m_dFlowRate, this.m_dTemperature + 273.15D, this.m_iCarrierGas, this.m_dOutletPressure, dInnerDiameter, this.m_dColumnLength);
        } else {
/* 3477 */           dInletPressure = this.m_dInletPressure;
        }
/* 3479 */         double dPressure = calcGasPressure(this.m_dPlotPressurePosition, dInletPressure, this.m_dOutletPressure);
        
/* 3481 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 0.0D, dPressure);
/* 3482 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, 9.999999999E9D, dPressure);
/* 3483 */         dPressureMin = dPressure * 0.8D;
/* 3484 */         dPressureMax = dPressure * 1.2D;
      }
    }
    
/* 3488 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(dPressureMin, dPressureMax);
  }
  
  public void plotRetentionFactor()
  {
/* 3493 */     this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 3494 */     this.m_iSecondPlotIndex = -1;
    
/* 3496 */     this.m_iSecondPlotIndex = this.contentPane.m_GraphControl.AddSeries("SecondPlot", new Color(130, 130, 130), 1, false, true);
    
/* 3498 */     double dRetentionFactorMin = 9.99999999E8D;
/* 3499 */     double dRetentionFactorMax = 0.0D;
    
/* 3501 */     if (this.m_bTemperatureProgramMode)
    {
/* 3503 */       for (int i = 0; i < this.m_vectRetentionFactorArray.size(); i++)
      {
/* 3505 */         double dRetentionFactor = ((double[])this.m_vectRetentionFactorArray.get(i))[1];
        
/* 3507 */         if (dRetentionFactor < dRetentionFactorMin)
/* 3508 */           dRetentionFactorMin = dRetentionFactor;
/* 3509 */         if (dRetentionFactor > dRetentionFactorMax) {
/* 3510 */           dRetentionFactorMax = dRetentionFactor;
        }
/* 3512 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, ((double[])this.m_vectRetentionFactorArray.get(i))[0], dRetentionFactor);
      }
      
/* 3515 */       if (dRetentionFactorMin == dRetentionFactorMax)
      {
/* 3517 */         dRetentionFactorMin *= 0.8D;
/* 3518 */         dRetentionFactorMax *= 1.2D;
      }
      
    }
    else
    {
/* 3524 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, this.m_dStartTime, this.m_dSelectedIsocraticRetentionFactor);
/* 3525 */       this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, this.m_dEndTime, this.m_dSelectedIsocraticRetentionFactor);
/* 3526 */       dRetentionFactorMin = this.m_dSelectedIsocraticRetentionFactor * 0.8D;
/* 3527 */       dRetentionFactorMax = this.m_dSelectedIsocraticRetentionFactor * 1.2D;
    }
    
/* 3530 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(dRetentionFactorMin, dRetentionFactorMax);
  }
  
  public void plotPosition()
  {
/* 3535 */     this.contentPane.m_GraphControl.RemoveSeries(this.m_iSecondPlotIndex);
/* 3536 */     this.m_iSecondPlotIndex = -1;
    
/* 3538 */     this.m_iSecondPlotIndex = this.contentPane.m_GraphControl.AddSeries("SecondPlot", new Color(130, 130, 130), 1, false, true);
    
/* 3540 */     if (this.m_bTemperatureProgramMode)
    {
/* 3542 */       for (int i = 0; i < this.m_vectPositionArray.size(); i++)
      {
/* 3544 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, ((double[])this.m_vectPositionArray.get(i))[0], ((double[])this.m_vectPositionArray.get(i))[1]);
      }
      
    }
    else
    {
/* 3550 */       int iSelectedCompound = this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.getSelectedIndex();
/* 3551 */       if ((iSelectedCompound < this.m_vectCompound.size()) && (iSelectedCompound >= 0))
      {
/* 3553 */         double dRetentionTime = ((Compound)this.m_vectCompound.get(iSelectedCompound)).dRetentionTime;
        
/* 3555 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, this.m_dStartTime, this.m_dStartTime * (this.m_dColumnLength / dRetentionTime));
/* 3556 */         this.contentPane.m_GraphControl.AddDataPoint(this.m_iSecondPlotIndex, this.m_dEndTime, this.m_dEndTime * (this.m_dColumnLength / dRetentionTime));
      }
    }
    
/* 3560 */     this.contentPane.m_GraphControl.setSecondYAxisRangeLimits(0.0D, this.m_dColumnLength);
  }
  
  public void updateCompoundComboBoxes()
  {
/* 3565 */     int iNumCompounds = this.m_vectCompound.size();
    
/* 3567 */     this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.removeAllItems();
/* 3568 */     this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.removeAllItems();
    
/* 3570 */     for (int i = 0; i < iNumCompounds; i++)
    {
/* 3572 */       this.contentPane.jxpanelPlotOptions.jcboRetentionFactorCompounds.addItem(((Compound)this.m_vectCompound.get(i)).strCompoundName);
/* 3573 */       this.contentPane.jxpanelPlotOptions.jcboPositionCompounds.addItem(((Compound)this.m_vectCompound.get(i)).strCompoundName);
    }
  }
  
  public void vacuumOutletPressure()
  {
/* 3579 */     this.contentPane.jxpanelInletOutlet.jrdoVacuum.setSelected(true);
/* 3580 */     this.contentPane.jxpanelInletOutlet.jrdoOtherPressure.setSelected(false);
    
/* 3582 */     this.contentPane.jxpanelInletOutlet.jtxtOtherPressure.setEnabled(false);
/* 3583 */     this.contentPane.jxpanelInletOutlet.jlblOtherPressureUnit.setEnabled(false);
    
/* 3585 */     this.m_dOutletPressure = 1.0E-6D;
  }
  
  public void otherOutletPressure()
  {
/* 3590 */     this.contentPane.jxpanelInletOutlet.jrdoVacuum.setSelected(false);
/* 3591 */     this.contentPane.jxpanelInletOutlet.jrdoOtherPressure.setSelected(true);
    
/* 3593 */     this.contentPane.jxpanelInletOutlet.jtxtOtherPressure.setEnabled(true);
/* 3594 */     this.contentPane.jxpanelInletOutlet.jlblOtherPressureUnit.setEnabled(true);
    
/* 3596 */     this.m_dOutletPressure = 100.0D;
  }
  
  public void switchToConstantPressureMode()
  {
/* 3601 */     this.contentPane.jxpanelInletOutlet.jrdoConstantPressure.setSelected(true);
/* 3602 */     this.contentPane.jxpanelInletOutlet.jrdoConstantFlowRate.setSelected(false);
/* 3603 */     this.contentPane.jxpanelInletOutlet.jlblFlowRate.setEnabled(false);
/* 3604 */     this.contentPane.jxpanelInletOutlet.jlblFlowRateUnit.setEnabled(false);
/* 3605 */     this.contentPane.jxpanelInletOutlet.jtxtFlowRate.setEnabled(false);
/* 3606 */     this.contentPane.jxpanelInletOutlet.jlblPressure.setEnabled(true);
/* 3607 */     this.contentPane.jxpanelInletOutlet.jtxtInletPressure.setEnabled(true);
/* 3608 */     this.contentPane.jxpanelInletOutlet.jcboInletPressureUnits.setEnabled(true);
/* 3609 */     this.m_bConstantFlowRateMode = false;
  }
  
  public void switchToConstantFlowRateMode()
  {
/* 3614 */     this.contentPane.jxpanelInletOutlet.jrdoConstantFlowRate.setSelected(true);
/* 3615 */     this.contentPane.jxpanelInletOutlet.jrdoConstantPressure.setSelected(false);
/* 3616 */     this.contentPane.jxpanelInletOutlet.jlblFlowRate.setEnabled(true);
/* 3617 */     this.contentPane.jxpanelInletOutlet.jlblFlowRateUnit.setEnabled(true);
/* 3618 */     this.contentPane.jxpanelInletOutlet.jtxtFlowRate.setEnabled(true);
/* 3619 */     this.contentPane.jxpanelInletOutlet.jlblPressure.setEnabled(false);
/* 3620 */     this.contentPane.jxpanelInletOutlet.jtxtInletPressure.setEnabled(false);
/* 3621 */     this.contentPane.jxpanelInletOutlet.jcboInletPressureUnits.setEnabled(false);
/* 3622 */     this.m_bConstantFlowRateMode = true;
  }
  
  public void switchToSplitMode()
  {
/* 3627 */     this.contentPane.jxpanelInletOutlet.jrdoSplit.setSelected(true);
/* 3628 */     this.contentPane.jxpanelInletOutlet.jrdoSplitless.setSelected(false);
/* 3629 */     this.contentPane.jxpanelInletOutlet.jlblSplitRatio.setEnabled(true);
/* 3630 */     this.contentPane.jxpanelInletOutlet.jlblSplitRatioUnits.setEnabled(true);
/* 3631 */     this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.setEnabled(true);
/* 3632 */     this.m_bSplitInjectionMode = true;
  }
  
  public void switchToSplitlessMode()
  {
/* 3637 */     this.contentPane.jxpanelInletOutlet.jrdoSplit.setSelected(false);
/* 3638 */     this.contentPane.jxpanelInletOutlet.jrdoSplitless.setSelected(true);
/* 3639 */     this.contentPane.jxpanelInletOutlet.jlblSplitRatio.setEnabled(false);
/* 3640 */     this.contentPane.jxpanelInletOutlet.jlblSplitRatioUnits.setEnabled(false);
/* 3641 */     this.contentPane.jxpanelInletOutlet.jtxtSplitRatio.setEnabled(false);
/* 3642 */     this.m_bSplitInjectionMode = false;
  }
  

  public void temperatureProgramChanged()
  {
/* 3648 */     performCalculations();
  }
}


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/GCSimulatorApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */