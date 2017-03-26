/*     */ package org.gcsimulator;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.util.Vector;
/*     */ import javax.media.opengl.GLCapabilities;
/*     */ import javax.media.opengl.GLProfile;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.Scrollable;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import org.gcsimulator.panels.ColumnProperties;
/*     */ import org.gcsimulator.panels.InletOutlet;
/*     */ import org.gcsimulator.panels.IsothermalOptions;
/*     */ import org.gcsimulator.panels.OvenOptions;
/*     */ import org.gcsimulator.panels.PlotOptions;
/*     */ import org.gcsimulator.panels.TemperatureProgramOptions;
/*     */ import org.jdesktop.swingx.JXTaskPane;
/*     */ import org.jdesktop.swingx.JXTaskPaneContainer;
/*     */ import org.jdesktop.swingx.VerticalLayout;
/*     */ 
/*     */ public class TopPanel extends JPanel implements Scrollable, java.awt.event.ComponentListener
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  39 */   public GraphControl m_GraphControl = null;
/*  40 */   private JScrollPane jScrollPane = null;
/*  41 */   public JXTaskPaneContainer taskpanecontainer = null;
/*     */   
/*  43 */   public JScrollPane jsclControlPanel = null;
/*  44 */   public JTable jtableChemicals = null;
/*  45 */   public JButton jbtnAddChemical = null;
/*  46 */   public JButton jbtnEditChemical = null;
/*  47 */   public JButton jbtnRemoveChemical = null;
/*     */   
/*     */   public DefaultTableModel tabModel;
/*  50 */   public Vector<String> vectColumnNames = new Vector();
/*  51 */   public Vector<Vector<String>> vectChemicalRows = new Vector();
/*     */   
/*  53 */   private JPanel jpanelCompounds = null;
/*  54 */   public JToggleButton jbtnAutoscale = null;
/*  55 */   public JToggleButton jbtnZoomIn = null;
/*  56 */   public JToggleButton jbtnZoomOut = null;
/*  57 */   public JToggleButton jbtnPan = null;
/*  58 */   private JPanel jpanelSimulatedChromatogram = null;
/*  59 */   public JToggleButton jbtnAutoscaleX = null;
/*  60 */   public JToggleButton jbtnAutoscaleY = null;
/*  61 */   public JButton jbtnHelp = null;
/*  62 */   public JButton jbtnContextHelp = null;
/*  63 */   public JButton jbtnTutorials = null;
/*     */   
/*  65 */   public InletOutlet jxpanelInletOutlet = null;
/*  66 */   public JXTaskPane jxtaskInletOutlet = null;
/*     */   
/*  68 */   public ColumnProperties jxpanelColumnProperties = null;
/*  69 */   public JXTaskPane jxtaskColumnProperties = null;
/*     */   
/*  71 */   public IsothermalOptions jxpanelIsothermalOptions = null;
/*  72 */   public JXTaskPane jxtaskIsothermalOptions = null;
/*     */   
/*  74 */   public TemperatureProgramOptions jxpanelTemperatureProgramOptions = null;
/*  75 */   public JXTaskPane jxtaskTemperatureProgramOptions = null;
/*     */   
/*  77 */   public OvenOptions jxpanelOvenOptions = null;
/*  78 */   public JXTaskPane jxtaskOvenOptions = null;
/*     */   
/*  80 */   public PlotOptions jxpanelPlotOptions = null;
/*  81 */   public JXTaskPane jxtaskPlotOptions = null;
/*     */   
/*  83 */   public JButton jbtnCopyImage = null;
/*  84 */   private JPanel jPanelGraphControl = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public TopPanel()
/*     */   {
/*  90 */     initialize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initialize()
/*     */   {
/* 101 */     setLayout(null);
/*     */     
/* 103 */     setVisible(true);
/* 104 */     setPreferredSize(new Dimension(900, 500));
/* 105 */     setMinimumSize(new Dimension(900, 500));
/*     */     
/*     */ 
/* 108 */     GLCapabilities caps = new GLCapabilities(GLProfile.get("GL2"));
/* 109 */     caps.setDoubleBuffered(true);
/* 110 */     caps.setHardwareAccelerated(true);
/*     */     
/* 112 */     this.m_GraphControl = new GraphControl(caps);
/* 113 */     this.m_GraphControl.setYAxisTitle("Signal");
/* 114 */     this.m_GraphControl.setYAxisBaseUnit("moles/liter", "mol/L");
/*     */     
/* 116 */     add(getJpanelCompounds(), null);
/* 117 */     add(getJpanelSimulatedChromatogram(), null);
/* 118 */     add(getControlPanel(), null);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 232 */     addComponentListener(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JScrollPane getJScrollPane()
/*     */   {
/* 241 */     if (this.jScrollPane == null) {
/* 242 */       this.jScrollPane = new JScrollPane();
/* 243 */       this.jScrollPane.setVerticalScrollBarPolicy(22);
/* 244 */       this.jScrollPane.setSize(new Dimension(624, 117));
/* 245 */       this.jScrollPane.setLocation(new Point(1, 1));
/* 246 */       this.jScrollPane.setViewportView(getJtableChemicals());
/* 247 */       this.jScrollPane.setBorder(null);
/*     */     }
/* 249 */     return this.jScrollPane;
/*     */   }
/*     */   
/*     */   public class JChemicalTable extends JTable implements Scrollable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public JChemicalTable(DefaultTableModel tabModel) {
/* 257 */       super();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int column)
/*     */     {
/* 262 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean getScrollableTracksViewportWidth()
/*     */     {
/* 268 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTable getJtableChemicals()
/*     */   {
/* 279 */     if (this.jtableChemicals == null)
/*     */     {
/* 281 */       String[] columnNames = 
/* 282 */         {
/* 283 */         "Compound", 
/* 284 */         "Conc. (μM)", 
/* 285 */         "k'", 
/* 286 */         "tR (min)", 
/* 287 */         "<html>&#x03C3<sub>total</sub> (s)</html>", 
/* 288 */         "W (pmol)" };
/*     */       
/*     */ 
/* 291 */       for (int i = 0; i < columnNames.length; i++)
/*     */       {
/* 293 */         this.vectColumnNames.add(columnNames[i]);
/*     */       }
/*     */       
/* 296 */       this.tabModel = new DefaultTableModel();
/* 297 */       this.tabModel.setDataVector(this.vectChemicalRows, this.vectColumnNames);
/*     */       
/* 299 */       this.jtableChemicals = new JChemicalTable(this.tabModel);
/* 300 */       this.jtableChemicals.setSelectionMode(0);
/* 301 */       this.jtableChemicals.setAutoResizeMode(0);
/* 302 */       this.jtableChemicals.getTableHeader().setPreferredSize(new Dimension(this.jtableChemicals.getColumnModel().getTotalColumnWidth(), 22));
/* 303 */       this.jtableChemicals.getColumnModel().getColumn(0).setPreferredWidth(170);
/* 304 */       this.jtableChemicals.getColumnModel().getColumn(1).setPreferredWidth(85);
/* 305 */       this.jtableChemicals.getColumnModel().getColumn(2).setPreferredWidth(80);
/* 306 */       this.jtableChemicals.getColumnModel().getColumn(3).setPreferredWidth(80);
/* 307 */       this.jtableChemicals.getColumnModel().getColumn(4).setPreferredWidth(80);
/* 308 */       this.jtableChemicals.getColumnModel().getColumn(5).setPreferredWidth(80);
/*     */     }
/* 310 */     return this.jtableChemicals;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnAddChemical()
/*     */   {
/* 319 */     if (this.jbtnAddChemical == null) {
/* 320 */       this.jbtnAddChemical = new JButton();
/* 321 */       this.jbtnAddChemical.setActionCommand("Add Chemical");
/* 322 */       this.jbtnAddChemical.setBounds(new Rectangle(12, 132, 81, 30));
/* 323 */       this.jbtnAddChemical.setText("Add");
/*     */     }
/* 325 */     return this.jbtnAddChemical;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnEditChemical()
/*     */   {
/* 334 */     if (this.jbtnEditChemical == null) {
/* 335 */       this.jbtnEditChemical = new JButton();
/* 336 */       this.jbtnEditChemical.setActionCommand("Edit Chemical");
/* 337 */       this.jbtnEditChemical.setBounds(new Rectangle(100, 132, 81, 30));
/* 338 */       this.jbtnEditChemical.setText("Edit");
/*     */     }
/* 340 */     return this.jbtnEditChemical;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnRemoveChemical()
/*     */   {
/* 349 */     if (this.jbtnRemoveChemical == null) {
/* 350 */       this.jbtnRemoveChemical = new JButton();
/* 351 */       this.jbtnRemoveChemical.setActionCommand("Remove Chemical");
/* 352 */       this.jbtnRemoveChemical.setBounds(new Rectangle(188, 132, 81, 30));
/* 353 */       this.jbtnRemoveChemical.setText("Remove");
/*     */     }
/* 355 */     return this.jbtnRemoveChemical;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JToggleButton getJbtnAutoscale()
/*     */   {
/* 364 */     if (this.jbtnAutoscale == null) {
/* 365 */       this.jbtnAutoscale = new JToggleButton();
/* 366 */       this.jbtnAutoscale.setIcon(new ImageIcon(getClass().getResource("/org/gcsimulator/images/autoscale.png")));
/* 367 */       this.jbtnAutoscale.setRolloverEnabled(false);
/* 368 */       this.jbtnAutoscale.setSelected(true);
/* 369 */       this.jbtnAutoscale.setMargin(new Insets(0, 0, 0, 0));
/* 370 */       this.jbtnAutoscale.setText("Autoscale");
/* 371 */       this.jbtnAutoscale.setBounds(new Rectangle(8, 444, 105, 30));
/* 372 */       this.jbtnAutoscale.setToolTipText("");
/*     */     }
/* 374 */     return this.jbtnAutoscale;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JToggleButton getJbtnAutoscaleX()
/*     */   {
/* 384 */     if (this.jbtnAutoscaleX == null) {
/* 385 */       this.jbtnAutoscaleX = new JToggleButton();
/* 386 */       this.jbtnAutoscaleX.setIcon(new ImageIcon(getClass().getResource("/org/gcsimulator/images/autoscaleX.png")));
/* 387 */       this.jbtnAutoscaleX.setRolloverEnabled(false);
/* 388 */       this.jbtnAutoscaleX.setSelected(true);
/* 389 */       this.jbtnAutoscaleX.setText("");
/* 390 */       this.jbtnAutoscaleX.setActionCommand("Autoscale X");
/* 391 */       this.jbtnAutoscaleX.setBounds(new Rectangle(120, 444, 33, 30));
/* 392 */       this.jbtnAutoscaleX.setToolTipText("");
/*     */     }
/* 394 */     return this.jbtnAutoscaleX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JToggleButton getJbtnAutoscaleY()
/*     */   {
/* 403 */     if (this.jbtnAutoscaleY == null) {
/* 404 */       this.jbtnAutoscaleY = new JToggleButton();
/* 405 */       this.jbtnAutoscaleY.setActionCommand("Autoscale Y");
/* 406 */       this.jbtnAutoscaleY.setIcon(new ImageIcon(getClass().getResource("/org/gcsimulator/images/autoscaleY.png")));
/* 407 */       this.jbtnAutoscaleY.setRolloverEnabled(false);
/* 408 */       this.jbtnAutoscaleY.setSelected(true);
/* 409 */       this.jbtnAutoscaleY.setText("");
/* 410 */       this.jbtnAutoscaleY.setBounds(new Rectangle(160, 444, 33, 30));
/* 411 */       this.jbtnAutoscaleY.setToolTipText("");
/*     */     }
/* 413 */     return this.jbtnAutoscaleY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JToggleButton getJbtnZoomIn()
/*     */   {
/* 422 */     if (this.jbtnZoomIn == null) {
/* 423 */       this.jbtnZoomIn = new JToggleButton();
/* 424 */       this.jbtnZoomIn.setIcon(new ImageIcon(getClass().getResource("/org/gcsimulator/images/zoomin.png")));
/* 425 */       this.jbtnZoomIn.setRolloverEnabled(false);
/* 426 */       this.jbtnZoomIn.setSelected(false);
/* 427 */       this.jbtnZoomIn.setMargin(new Insets(0, 0, 0, 0));
/* 428 */       this.jbtnZoomIn.setText("Zoom in");
/* 429 */       this.jbtnZoomIn.setBounds(new Rectangle(312, 444, 105, 30));
/* 430 */       this.jbtnZoomIn.setToolTipText("");
/*     */     }
/* 432 */     return this.jbtnZoomIn;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JToggleButton getJbtnZoomOut()
/*     */   {
/* 441 */     if (this.jbtnZoomOut == null) {
/* 442 */       this.jbtnZoomOut = new JToggleButton();
/* 443 */       this.jbtnZoomOut.setIcon(new ImageIcon(getClass().getResource("/org/gcsimulator/images/zoomout.png")));
/* 444 */       this.jbtnZoomOut.setRolloverEnabled(false);
/* 445 */       this.jbtnZoomOut.setSelected(false);
/* 446 */       this.jbtnZoomOut.setText("Zoom out");
/* 447 */       this.jbtnZoomOut.setMargin(new Insets(0, 0, 0, 0));
/* 448 */       this.jbtnZoomOut.setBounds(new Rectangle(424, 444, 105, 30));
/* 449 */       this.jbtnZoomOut.setToolTipText("");
/*     */     }
/* 451 */     return this.jbtnZoomOut;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JToggleButton getJbtnPan()
/*     */   {
/* 461 */     if (this.jbtnPan == null)
/*     */     {
/* 463 */       this.jbtnPan = new JToggleButton();
/* 464 */       this.jbtnPan.setIcon(new ImageIcon(getClass().getResource("/org/gcsimulator/images/pan.png")));
/* 465 */       this.jbtnPan.setRolloverEnabled(false);
/* 466 */       this.jbtnPan.setSelected(true);
/* 467 */       this.jbtnPan.setText("Pan");
/* 468 */       this.jbtnPan.setMargin(new Insets(0, 0, 0, 0));
/* 469 */       this.jbtnPan.setBounds(new Rectangle(200, 444, 105, 30));
/* 470 */       this.jbtnPan.setToolTipText("");
/*     */     }
/* 472 */     return this.jbtnPan;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JPanel getJpanelCompounds()
/*     */   {
/* 482 */     if (this.jpanelCompounds == null) {
/* 483 */       this.jpanelCompounds = new JPanel();
/* 484 */       this.jpanelCompounds.setLayout(null);
/* 485 */       this.jpanelCompounds.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
/* 486 */       this.jpanelCompounds.setBounds(new Rectangle(284, 480, 625, 170));
/* 487 */       this.jpanelCompounds.add(getJScrollPane(), null);
/* 488 */       this.jpanelCompounds.add(getJbtnAddChemical(), null);
/* 489 */       this.jpanelCompounds.add(getJbtnEditChemical(), null);
/* 490 */       this.jpanelCompounds.add(getJbtnRemoveChemical(), null);
/* 491 */       this.jpanelCompounds.add(getJbtnTutorials(), null);
/* 492 */       this.jpanelCompounds.add(getJbtnContextHelp(), null);
/* 493 */       this.jpanelCompounds.add(getJbtnHelp(), null);
/*     */     }
/* 495 */     return this.jpanelCompounds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JPanel getJpanelSimulatedChromatogram()
/*     */   {
/* 505 */     if (this.jpanelSimulatedChromatogram == null)
/*     */     {
/* 507 */       this.jpanelSimulatedChromatogram = new JPanel();
/* 508 */       this.jpanelSimulatedChromatogram.setLayout(null);
/* 509 */       this.jpanelSimulatedChromatogram.setBounds(new Rectangle(284, 0, 625, 481));
/* 510 */       this.jpanelSimulatedChromatogram.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
/* 511 */       this.jpanelSimulatedChromatogram.setPreferredSize(new Dimension(0, 0));
/*     */       
/*     */ 
/* 514 */       this.jpanelSimulatedChromatogram.add(getJbtnAutoscale(), null);
/* 515 */       this.jpanelSimulatedChromatogram.add(getJbtnPan(), null);
/* 516 */       this.jpanelSimulatedChromatogram.add(getJbtnAutoscaleX(), null);
/* 517 */       this.jpanelSimulatedChromatogram.add(getJbtnAutoscaleY(), null);
/* 518 */       this.jpanelSimulatedChromatogram.add(getJbtnZoomOut(), null);
/* 519 */       this.jpanelSimulatedChromatogram.add(getJbtnZoomIn(), null);
/* 520 */       this.jpanelSimulatedChromatogram.add(getJbtnCopyImage(), null);
/* 521 */       this.jpanelSimulatedChromatogram.add(getJPanelGraphControl(), null);
/*     */     }
/* 523 */     return this.jpanelSimulatedChromatogram;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JScrollPane getControlPanel()
/*     */   {
/* 533 */     if (this.jsclControlPanel == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 538 */       VerticalLayout verticalLayout = new VerticalLayout();
/* 539 */       verticalLayout.setGap(0);
/*     */       
/*     */ 
/* 542 */       this.taskpanecontainer = new JXTaskPaneContainer();
/*     */       
/* 544 */       this.taskpanecontainer.setBorder(BorderFactory.createEmptyBorder());
/*     */       
/* 546 */       this.taskpanecontainer.setPaintBorderInsets(true);
/* 547 */       this.taskpanecontainer.setLayout(verticalLayout);
/*     */       
/*     */ 
/* 550 */       this.jxtaskInletOutlet = new JXTaskPane();
/* 551 */       this.jxtaskInletOutlet.setAnimated(false);
/* 552 */       this.jxtaskInletOutlet.setTitle("Inlet/Outlet");
/* 553 */       ((JComponent)this.jxtaskInletOutlet.getContentPane()).setBorder(BorderFactory.createEmptyBorder());
/*     */       
/* 555 */       this.jxpanelInletOutlet = new InletOutlet();
/* 556 */       this.jxtaskInletOutlet.add(this.jxpanelInletOutlet);
/*     */       
/*     */ 
/* 559 */       this.jxtaskOvenOptions = new JXTaskPane();
/* 560 */       this.jxtaskOvenOptions.setAnimated(false);
/* 561 */       this.jxtaskOvenOptions.setTitle("Oven Properties");
/* 562 */       ((JComponent)this.jxtaskOvenOptions.getContentPane()).setBorder(BorderFactory.createEmptyBorder());
/*     */       
/* 564 */       this.jxpanelOvenOptions = new OvenOptions();
/* 565 */       this.jxtaskOvenOptions.add(this.jxpanelOvenOptions);
/*     */       
/* 567 */       this.jxpanelIsothermalOptions = new IsothermalOptions();
/* 568 */       this.jxtaskOvenOptions.add(this.jxpanelIsothermalOptions);
/*     */       
/* 570 */       this.jxpanelTemperatureProgramOptions = new TemperatureProgramOptions();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 575 */       this.jxtaskPlotOptions = new JXTaskPane();
/* 576 */       this.jxtaskPlotOptions.setAnimated(false);
/* 577 */       this.jxtaskPlotOptions.setTitle("Plot Options");
/* 578 */       ((JComponent)this.jxtaskPlotOptions.getContentPane()).setBorder(null);
/*     */       
/* 580 */       this.jxpanelPlotOptions = new PlotOptions();
/* 581 */       this.jxtaskPlotOptions.add(this.jxpanelPlotOptions);
/*     */       
/*     */ 
/* 584 */       this.jxtaskColumnProperties = new JXTaskPane();
/* 585 */       this.jxtaskColumnProperties.setAnimated(false);
/* 586 */       this.jxtaskColumnProperties.setTitle("Column Properties");
/* 587 */       ((JComponent)this.jxtaskColumnProperties.getContentPane()).setBorder(null);
/*     */       
/* 589 */       this.jxpanelColumnProperties = new ColumnProperties();
/* 590 */       this.jxtaskColumnProperties.add(this.jxpanelColumnProperties);
/*     */       
/*     */ 
/* 593 */       this.taskpanecontainer.add(this.jxtaskOvenOptions, null);
/* 594 */       this.taskpanecontainer.add(this.jxtaskInletOutlet, null);
/* 595 */       this.taskpanecontainer.add(this.jxtaskColumnProperties, null);
/* 596 */       this.taskpanecontainer.add(this.jxtaskPlotOptions, null);
/*     */       
/* 598 */       this.jsclControlPanel = new JScrollPane(this.taskpanecontainer);
/*     */       
/*     */ 
/* 601 */       this.jsclControlPanel.setBorder(BorderFactory.createEmptyBorder());
/* 602 */       this.jsclControlPanel.setLocation(new Point(0, 0));
/*     */       
/* 604 */       this.jsclControlPanel.setSize(new Dimension(281, 665));
/*     */     }
/*     */     
/*     */ 
/* 608 */     return this.jsclControlPanel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnHelp()
/*     */   {
/* 617 */     if (this.jbtnHelp == null) {
/* 618 */       this.jbtnHelp = new JButton();
/* 619 */       this.jbtnHelp.setText("Help");
/* 620 */       this.jbtnHelp.setForeground(Color.blue);
/* 621 */       this.jbtnHelp.setBounds(new Rectangle(536, 132, 69, 30));
/*     */     }
/* 623 */     return this.jbtnHelp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnContextHelp()
/*     */   {
/* 632 */     if (this.jbtnContextHelp == null) {
/* 633 */       this.jbtnContextHelp = new JButton();
/* 634 */       this.jbtnContextHelp.setIcon(new ImageIcon(getClass().getResource("/org/gcsimulator/images/help.gif")));
/* 635 */       this.jbtnContextHelp.setBounds(new Rectangle(496, 132, 33, 30));
/*     */     }
/* 637 */     return this.jbtnContextHelp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnTutorials()
/*     */   {
/* 646 */     if (this.jbtnTutorials == null) {
/* 647 */       this.jbtnTutorials = new JButton();
/* 648 */       this.jbtnTutorials.setText("Tutorials");
/* 649 */       this.jbtnTutorials.setForeground(Color.blue);
/* 650 */       this.jbtnTutorials.setBounds(new Rectangle(396, 132, 93, 30));
/* 651 */       this.jbtnTutorials.setVisible(false);
/*     */     }
/* 653 */     return this.jbtnTutorials;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnCopyImage()
/*     */   {
/* 663 */     if (this.jbtnCopyImage == null) {
/* 664 */       this.jbtnCopyImage = new JButton();
/* 665 */       this.jbtnCopyImage.setBounds(new Rectangle(536, 444, 81, 30));
/* 666 */       this.jbtnCopyImage.setActionCommand("Copy Image");
/* 667 */       this.jbtnCopyImage.setIcon(new ImageIcon(getClass().getResource("/org/gcsimulator/images/clipboard.png")));
/* 668 */       this.jbtnCopyImage.setRolloverEnabled(false);
/* 669 */       this.jbtnCopyImage.setMargin(new Insets(0, 0, 0, 0));
/* 670 */       this.jbtnCopyImage.setSelected(false);
/* 671 */       this.jbtnCopyImage.setText("Copy");
/* 672 */       this.jbtnCopyImage.setToolTipText("");
/*     */     }
/* 674 */     return this.jbtnCopyImage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Dimension getPreferredScrollableViewportSize()
/*     */   {
/* 681 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2)
/*     */   {
/* 688 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getScrollableTracksViewportHeight()
/*     */   {
/* 694 */     Dimension minSize = getMinimumSize();
/* 695 */     Dimension portSize = null;
/* 696 */     if ((getParent() instanceof JViewport))
/*     */     {
/* 698 */       JViewport port = (JViewport)getParent();
/* 699 */       portSize = port.getSize();
/*     */     }
/*     */     else {
/* 702 */       return false;
/*     */     }
/* 704 */     if (portSize.height < minSize.height) {
/* 705 */       return false;
/*     */     }
/* 707 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getScrollableTracksViewportWidth()
/*     */   {
/* 713 */     Dimension minSize = getMinimumSize();
/* 714 */     Dimension portSize = null;
/* 715 */     if ((getParent() instanceof JViewport))
/*     */     {
/* 717 */       JViewport port = (JViewport)getParent();
/* 718 */       portSize = port.getSize();
/*     */     }
/*     */     else {
/* 721 */       return false;
/*     */     }
/* 723 */     if (portSize.width < minSize.width) {
/* 724 */       return false;
/*     */     }
/* 726 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2)
/*     */   {
/* 733 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void componentHidden(ComponentEvent arg0) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void componentMoved(ComponentEvent arg0)
/*     */   {
/* 746 */     revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void componentResized(ComponentEvent arg0)
/*     */   {
/* 753 */     if (arg0.getComponent() == this)
/*     */     {
/* 755 */       Dimension size = getSize();
/* 756 */       int iControlPanelWidth = 332;
/* 757 */       this.jsclControlPanel.setSize(iControlPanelWidth - 4, size.height);
/* 758 */       int divide = size.height * 3 / 4;
/* 759 */       this.jpanelSimulatedChromatogram.setLocation(iControlPanelWidth, 0);
/* 760 */       this.jpanelSimulatedChromatogram.setSize(size.width - iControlPanelWidth, divide + 1);
/* 761 */       this.jPanelGraphControl.setBounds(1, 1, this.jpanelSimulatedChromatogram.getWidth() - 2, this.jpanelSimulatedChromatogram.getHeight() - 44);
/* 762 */       this.jpanelCompounds.setLocation(iControlPanelWidth, divide);
/* 763 */       this.jpanelCompounds.setSize(size.width - iControlPanelWidth, size.height - divide);
/* 764 */       this.jbtnAutoscale.setLocation(this.jbtnAutoscale.getLocation().x, this.jpanelSimulatedChromatogram.getHeight() - 37);
/* 765 */       this.jbtnAutoscaleX.setLocation(this.jbtnAutoscaleX.getLocation().x, this.jpanelSimulatedChromatogram.getHeight() - 37);
/* 766 */       this.jbtnAutoscaleY.setLocation(this.jbtnAutoscaleY.getLocation().x, this.jpanelSimulatedChromatogram.getHeight() - 37);
/* 767 */       this.jbtnPan.setLocation(this.jbtnPan.getLocation().x, this.jpanelSimulatedChromatogram.getHeight() - 37);
/* 768 */       this.jbtnZoomIn.setLocation(this.jbtnZoomIn.getLocation().x, this.jpanelSimulatedChromatogram.getHeight() - 37);
/* 769 */       this.jbtnZoomOut.setLocation(this.jbtnZoomOut.getLocation().x, this.jpanelSimulatedChromatogram.getHeight() - 37);
/* 770 */       this.jbtnCopyImage.setLocation(this.jbtnCopyImage.getLocation().x, this.jpanelSimulatedChromatogram.getHeight() - 37);
/* 771 */       this.jScrollPane.setSize(this.jpanelCompounds.getWidth() - 2, this.jpanelCompounds.getHeight() - 44);
/* 772 */       this.jbtnAddChemical.setLocation(this.jbtnAddChemical.getLocation().x, this.jpanelCompounds.getHeight() - 37);
/* 773 */       this.jbtnEditChemical.setLocation(this.jbtnEditChemical.getLocation().x, this.jpanelCompounds.getHeight() - 37);
/* 774 */       this.jbtnRemoveChemical.setLocation(this.jbtnRemoveChemical.getLocation().x, this.jpanelCompounds.getHeight() - 37);
/* 775 */       this.jbtnTutorials.setLocation(this.jpanelCompounds.getWidth() - 220, this.jpanelCompounds.getHeight() - 37);
/* 776 */       this.jbtnContextHelp.setLocation(this.jpanelCompounds.getWidth() - 120, this.jpanelCompounds.getHeight() - 37);
/* 777 */       this.jbtnHelp.setLocation(this.jpanelCompounds.getWidth() - 80, this.jpanelCompounds.getHeight() - 37);
/*     */       
/* 779 */       int xpos = 0;
/*     */       
/* 781 */       int diff = this.jScrollPane.getViewport().getWidth() * 170 / 575;
/* 782 */       xpos += diff;
/* 783 */       this.jtableChemicals.getColumnModel().getColumn(0).setPreferredWidth(diff);
/* 784 */       diff = this.jScrollPane.getViewport().getWidth() * 85 / 575;
/* 785 */       xpos += diff;
/* 786 */       this.jtableChemicals.getColumnModel().getColumn(1).setPreferredWidth(diff);
/* 787 */       diff = this.jScrollPane.getViewport().getWidth() * 80 / 575;
/* 788 */       xpos += diff;
/* 789 */       this.jtableChemicals.getColumnModel().getColumn(2).setPreferredWidth(diff);
/* 790 */       diff = this.jScrollPane.getViewport().getWidth() * 80 / 575;
/* 791 */       xpos += diff;
/* 792 */       this.jtableChemicals.getColumnModel().getColumn(3).setPreferredWidth(diff);
/* 793 */       diff = this.jScrollPane.getViewport().getWidth() * 80 / 575;
/* 794 */       xpos += diff;
/* 795 */       this.jtableChemicals.getColumnModel().getColumn(4).setPreferredWidth(diff);
/* 796 */       this.jtableChemicals.getColumnModel().getColumn(5).setPreferredWidth(this.jScrollPane.getViewport().getWidth() - xpos);
/* 797 */       this.jtableChemicals.revalidate();
/* 798 */       this.m_GraphControl.repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void componentShown(ComponentEvent arg0) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JPanel getJPanelGraphControl()
/*     */   {
/* 816 */     if (this.jPanelGraphControl == null) {
/* 817 */       GridLayout gridLayout = new GridLayout();
/* 818 */       gridLayout.setRows(1);
/* 819 */       this.jPanelGraphControl = new JPanel();
/* 820 */       this.jPanelGraphControl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 821 */       this.jPanelGraphControl.setLayout(gridLayout);
/* 822 */       this.jPanelGraphControl.setBounds(new Rectangle(4, 2, 620, 432));
/* 823 */       this.jPanelGraphControl.add(this.m_GraphControl, null);
/*     */     }
/* 825 */     return this.jPanelGraphControl;
/*     */   }
/*     */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/TopPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */