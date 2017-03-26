/*     */ package org.gcsimulator.panels;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Hashtable;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JTextField;
/*     */ import org.jdesktop.swingx.JXPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlotOptions
/*     */   extends JXPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   private JLabel jlblSecondPlot = null;
/*  24 */   public JRadioButton jrdoNoPlot = null;
/*  25 */   public JRadioButton jrdoFlowVelocity = null;
/*  26 */   public JRadioButton jrdoMobilePhaseViscosity = null;
/*  27 */   public JRadioButton jrdoMobilePhaseDensity = null;
/*  28 */   public JRadioButton jrdoRetentionFactor = null;
/*  29 */   public JRadioButton jrdoPosition = null;
/*  30 */   public JRadioButton jrdoPressure = null;
/*  31 */   public JComboBox jcboRetentionFactorCompounds = null;
/*  32 */   public JComboBox jcboPositionCompounds = null;
/*  33 */   private JLabel jlblTimeConstant = null;
/*  34 */   public JTextField jtxtTimeConstant = null;
/*  35 */   private JLabel jlblTimeConstantUnits = null;
/*  36 */   private JLabel jlblSignalOffset = null;
/*  37 */   public JTextField jtxtSignalOffset = null;
/*  38 */   private JLabel jlblSignalOffsetUnits = null;
/*  39 */   private JLabel jlblNoise = null;
/*  40 */   public JTextField jtxtNoise = null;
/*  41 */   public JCheckBox jchkAutoTimeRange = null;
/*  42 */   private JLabel jlblInitialTime = null;
/*  43 */   public JTextField jtxtInitialTime = null;
/*  44 */   private JLabel jlblInitialTimeUnits = null;
/*  45 */   private JLabel jlblFinalTime = null;
/*  46 */   public JTextField jtxtFinalTime = null;
/*  47 */   private JLabel jlblFinalTimeUnits = null;
/*  48 */   private JLabel jlblNumPoints = null;
/*  49 */   public JTextField jtxtNumPoints = null;
/*  50 */   public JRadioButton jrdoTemperature = null;
/*  51 */   public JRadioButton jrdoHoldUpTime = null;
/*  52 */   public JSlider jsliderFlowVelocityPosition = null;
/*  53 */   public JTextField jtxtFlowVelocityColumnPosition = null;
/*  54 */   public JSlider jsliderDensityPosition = null;
/*  55 */   public JTextField jtxtDensityColumnPosition = null;
/*  56 */   public JSlider jsliderPressurePosition = null;
/*  57 */   public JTextField jtxtPressurePosition = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PlotOptions()
/*     */   {
/*  64 */     initialize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initialize()
/*     */   {
/*  72 */     this.jlblNumPoints = new JLabel();
/*  73 */     this.jlblNumPoints.setBounds(new Rectangle(8, 600, 181, 16));
/*  74 */     this.jlblNumPoints.setText("Plot points:");
/*  75 */     this.jlblFinalTimeUnits = new JLabel();
/*  76 */     this.jlblFinalTimeUnits.setBounds(new Rectangle(256, 576, 49, 16));
/*  77 */     this.jlblFinalTimeUnits.setText("s");
/*  78 */     this.jlblFinalTimeUnits.setFont(new Font("Dialog", 0, 12));
/*  79 */     this.jlblFinalTime = new JLabel();
/*  80 */     this.jlblFinalTime.setBounds(new Rectangle(8, 576, 181, 16));
/*  81 */     this.jlblFinalTime.setText("Final time:");
/*  82 */     this.jlblInitialTimeUnits = new JLabel();
/*  83 */     this.jlblInitialTimeUnits.setBounds(new Rectangle(256, 552, 49, 16));
/*  84 */     this.jlblInitialTimeUnits.setText("s");
/*  85 */     this.jlblInitialTimeUnits.setFont(new Font("Dialog", 0, 12));
/*  86 */     this.jlblInitialTime = new JLabel();
/*  87 */     this.jlblInitialTime.setBounds(new Rectangle(8, 552, 181, 16));
/*  88 */     this.jlblInitialTime.setText("Initial time:");
/*  89 */     this.jlblNoise = new JLabel();
/*  90 */     this.jlblNoise.setBounds(new Rectangle(8, 500, 181, 16));
/*  91 */     this.jlblNoise.setText("Noise:");
/*  92 */     this.jlblSignalOffsetUnits = new JLabel();
/*  93 */     this.jlblSignalOffsetUnits.setBounds(new Rectangle(256, 476, 49, 16));
/*  94 */     this.jlblSignalOffsetUnits.setText("munits");
/*  95 */     this.jlblSignalOffsetUnits.setFont(new Font("Dialog", 0, 12));
/*  96 */     this.jlblSignalOffset = new JLabel();
/*  97 */     this.jlblSignalOffset.setBounds(new Rectangle(8, 476, 181, 16));
/*  98 */     this.jlblSignalOffset.setText("Signal offset:");
/*  99 */     this.jlblTimeConstantUnits = new JLabel();
/* 100 */     this.jlblTimeConstantUnits.setBounds(new Rectangle(256, 452, 50, 16));
/* 101 */     this.jlblTimeConstantUnits.setText("s");
/* 102 */     this.jlblTimeConstantUnits.setFont(new Font("Dialog", 0, 12));
/* 103 */     this.jlblTimeConstant = new JLabel();
/* 104 */     this.jlblTimeConstant.setBounds(new Rectangle(8, 452, 181, 16));
/* 105 */     this.jlblTimeConstant.setText("Time constant:");
/* 106 */     this.jlblSecondPlot = new JLabel();
/* 107 */     this.jlblSecondPlot.setBounds(new Rectangle(8, 8, 129, 16));
/* 108 */     this.jlblSecondPlot.setText("Second Plot:");
/* 109 */     setLayout(null);
/* 110 */     setBounds(new Rectangle(0, 0, 314, 630));
/* 111 */     setPreferredSize(getSize());
/* 112 */     setBackground(new Color(214, 217, 223));
/* 113 */     add(this.jlblSecondPlot, null);
/* 114 */     add(getJrdoNoPlot(), null);
/* 115 */     add(getJrdoFlowVelocity(), null);
/* 116 */     add(getJrdoMobilePhaseViscosity(), null);
/* 117 */     add(getJrdoMobilePhaseDensity(), null);
/* 118 */     add(getJrdoRetentionFactor(), null);
/* 119 */     add(getJrdoPosition(), null);
/* 120 */     add(getJcboRetentionFactorCompounds(), null);
/* 121 */     add(getJcboPositionCompounds(), null);
/* 122 */     add(getJrdoPressure(), null);
/* 123 */     add(this.jlblTimeConstant, null);
/* 124 */     add(getJtxtTimeConstant(), null);
/* 125 */     add(this.jlblTimeConstantUnits, null);
/* 126 */     add(this.jlblSignalOffset, null);
/* 127 */     add(getJtxtSignalOffset(), null);
/* 128 */     add(this.jlblSignalOffsetUnits, null);
/* 129 */     add(this.jlblNoise, null);
/* 130 */     add(getJtxtNoise(), null);
/* 131 */     add(getJchkAutoTimeRange(), null);
/* 132 */     add(this.jlblInitialTime, null);
/* 133 */     add(getJtxtInitialTime(), null);
/* 134 */     add(this.jlblInitialTimeUnits, null);
/* 135 */     add(this.jlblFinalTime, null);
/* 136 */     add(getJtxtFinalTime(), null);
/* 137 */     add(this.jlblFinalTimeUnits, null);
/* 138 */     add(this.jlblNumPoints, null);
/* 139 */     add(getJtxtNumPoints(), null);
/* 140 */     add(getJrdoTemperature(), null);
/* 141 */     add(getJrdoHoldUpTime(), null);
/* 142 */     add(getJsliderFlowVelocityPosition(), null);
/* 143 */     add(getJtxtFlowVelocityColumnPosition(), null);
/* 144 */     add(getJsliderDensityPosition(), null);
/* 145 */     add(getJtxtDensityColumnPosition(), null);
/* 146 */     add(getJsliderPressurePosition(), null);
/* 147 */     add(getJtxtPressurePosition(), null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoNoPlot()
/*     */   {
/* 157 */     if (this.jrdoNoPlot == null) {
/* 158 */       this.jrdoNoPlot = new JRadioButton();
/* 159 */       this.jrdoNoPlot.setBounds(new Rectangle(8, 28, 297, 17));
/* 160 */       this.jrdoNoPlot.setText("No plot");
/* 161 */       this.jrdoNoPlot.setSelected(true);
/*     */     }
/* 163 */     return this.jrdoNoPlot;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoFlowVelocity()
/*     */   {
/* 172 */     if (this.jrdoFlowVelocity == null) {
/* 173 */       this.jrdoFlowVelocity = new JRadioButton();
/* 174 */       this.jrdoFlowVelocity.setBounds(new Rectangle(8, 124, 297, 17));
/* 175 */       this.jrdoFlowVelocity.setText("Flow velocity at column position (z/L):");
/* 176 */       this.jrdoFlowVelocity.setActionCommand("Plot flow velocity");
/*     */     }
/* 178 */     return this.jrdoFlowVelocity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoMobilePhaseViscosity()
/*     */   {
/* 187 */     if (this.jrdoMobilePhaseViscosity == null) {
/* 188 */       this.jrdoMobilePhaseViscosity = new JRadioButton();
/* 189 */       this.jrdoMobilePhaseViscosity.setBounds(new Rectangle(8, 100, 297, 17));
/* 190 */       this.jrdoMobilePhaseViscosity.setText("Gas viscosity");
/* 191 */       this.jrdoMobilePhaseViscosity.setActionCommand("Plot mobile phase viscosity");
/*     */     }
/* 193 */     return this.jrdoMobilePhaseViscosity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoMobilePhaseDensity()
/*     */   {
/* 202 */     if (this.jrdoMobilePhaseDensity == null) {
/* 203 */       this.jrdoMobilePhaseDensity = new JRadioButton();
/* 204 */       this.jrdoMobilePhaseDensity.setBounds(new Rectangle(8, 200, 301, 17));
/* 205 */       this.jrdoMobilePhaseDensity.setText("Gas density at column position (z/L):");
/* 206 */       this.jrdoMobilePhaseDensity.setActionCommand("Plot mobile phase density");
/*     */     }
/* 208 */     return this.jrdoMobilePhaseDensity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoRetentionFactor()
/*     */   {
/* 217 */     if (this.jrdoRetentionFactor == null) {
/* 218 */       this.jrdoRetentionFactor = new JRadioButton();
/* 219 */       this.jrdoRetentionFactor.setBounds(new Rectangle(8, 348, 301, 17));
/* 220 */       this.jrdoRetentionFactor.setText("Retention factor of:");
/* 221 */       this.jrdoRetentionFactor.setActionCommand("Plot retention factor");
/*     */     }
/* 223 */     return this.jrdoRetentionFactor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoPosition()
/*     */   {
/* 232 */     if (this.jrdoPosition == null) {
/* 233 */       this.jrdoPosition = new JRadioButton();
/* 234 */       this.jrdoPosition.setBounds(new Rectangle(8, 396, 233, 17));
/* 235 */       this.jrdoPosition.setActionCommand("Plot position");
/* 236 */       this.jrdoPosition.setText("Position along column of:");
/*     */     }
/* 238 */     return this.jrdoPosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JComboBox getJcboRetentionFactorCompounds()
/*     */   {
/* 247 */     if (this.jcboRetentionFactorCompounds == null) {
/* 248 */       this.jcboRetentionFactorCompounds = new JComboBox();
/* 249 */       this.jcboRetentionFactorCompounds.setBounds(new Rectangle(32, 368, 273, 25));
/* 250 */       this.jcboRetentionFactorCompounds.setEnabled(false);
/* 251 */       this.jcboRetentionFactorCompounds.setActionCommand("RetentionFactorCompoundChanged");
/*     */     }
/* 253 */     return this.jcboRetentionFactorCompounds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JComboBox getJcboPositionCompounds()
/*     */   {
/* 262 */     if (this.jcboPositionCompounds == null) {
/* 263 */       this.jcboPositionCompounds = new JComboBox();
/* 264 */       this.jcboPositionCompounds.setBounds(new Rectangle(32, 416, 273, 25));
/* 265 */       this.jcboPositionCompounds.setEnabled(false);
/* 266 */       this.jcboPositionCompounds.setActionCommand("PositionCompoundChanged");
/*     */     }
/* 268 */     return this.jcboPositionCompounds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoPressure()
/*     */   {
/* 277 */     if (this.jrdoPressure == null) {
/* 278 */       this.jrdoPressure = new JRadioButton();
/* 279 */       this.jrdoPressure.setBounds(new Rectangle(8, 272, 301, 18));
/* 280 */       this.jrdoPressure.setText("Pressure at column position (z/L):");
/* 281 */       this.jrdoPressure.setActionCommand("Plot pressure");
/*     */     }
/* 283 */     return this.jrdoPressure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtTimeConstant()
/*     */   {
/* 292 */     if (this.jtxtTimeConstant == null) {
/* 293 */       this.jtxtTimeConstant = new JTextField();
/* 294 */       this.jtxtTimeConstant.setBounds(new Rectangle(192, 448, 61, 26));
/* 295 */       this.jtxtTimeConstant.setText("0.1");
/*     */     }
/* 297 */     return this.jtxtTimeConstant;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtSignalOffset()
/*     */   {
/* 306 */     if (this.jtxtSignalOffset == null) {
/* 307 */       this.jtxtSignalOffset = new JTextField();
/* 308 */       this.jtxtSignalOffset.setBounds(new Rectangle(192, 472, 61, 26));
/* 309 */       this.jtxtSignalOffset.setText("0");
/*     */     }
/* 311 */     return this.jtxtSignalOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtNoise()
/*     */   {
/* 320 */     if (this.jtxtNoise == null) {
/* 321 */       this.jtxtNoise = new JTextField();
/* 322 */       this.jtxtNoise.setBounds(new Rectangle(192, 496, 61, 26));
/* 323 */       this.jtxtNoise.setText("2");
/*     */     }
/* 325 */     return this.jtxtNoise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JCheckBox getJchkAutoTimeRange()
/*     */   {
/* 334 */     if (this.jchkAutoTimeRange == null) {
/* 335 */       this.jchkAutoTimeRange = new JCheckBox();
/* 336 */       this.jchkAutoTimeRange.setBounds(new Rectangle(8, 524, 301, 20));
/* 337 */       this.jchkAutoTimeRange.setSelected(true);
/* 338 */       this.jchkAutoTimeRange.setText("Automatically determine time span");
/* 339 */       this.jchkAutoTimeRange.setName("jchkAutoTimeRange");
/*     */     }
/* 341 */     return this.jchkAutoTimeRange;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtInitialTime()
/*     */   {
/* 350 */     if (this.jtxtInitialTime == null) {
/* 351 */       this.jtxtInitialTime = new JTextField();
/* 352 */       this.jtxtInitialTime.setBounds(new Rectangle(192, 548, 61, 26));
/* 353 */       this.jtxtInitialTime.setText("0");
/* 354 */       this.jtxtInitialTime.setEnabled(false);
/*     */     }
/* 356 */     return this.jtxtInitialTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtFinalTime()
/*     */   {
/* 365 */     if (this.jtxtFinalTime == null) {
/* 366 */       this.jtxtFinalTime = new JTextField();
/* 367 */       this.jtxtFinalTime.setBounds(new Rectangle(192, 572, 61, 26));
/* 368 */       this.jtxtFinalTime.setText("0");
/* 369 */       this.jtxtFinalTime.setEnabled(false);
/*     */     }
/* 371 */     return this.jtxtFinalTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtNumPoints()
/*     */   {
/* 380 */     if (this.jtxtNumPoints == null) {
/* 381 */       this.jtxtNumPoints = new JTextField();
/* 382 */       this.jtxtNumPoints.setBounds(new Rectangle(192, 596, 61, 26));
/* 383 */       this.jtxtNumPoints.setText("3000");
/*     */     }
/* 385 */     return this.jtxtNumPoints;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoTemperature()
/*     */   {
/* 394 */     if (this.jrdoTemperature == null) {
/* 395 */       this.jrdoTemperature = new JRadioButton();
/* 396 */       this.jrdoTemperature.setBounds(new Rectangle(8, 52, 297, 17));
/* 397 */       this.jrdoTemperature.setText("Temperature");
/* 398 */       this.jrdoTemperature.setActionCommand("Plot temperature");
/*     */     }
/* 400 */     return this.jrdoTemperature;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoHoldUpTime()
/*     */   {
/* 409 */     if (this.jrdoHoldUpTime == null) {
/* 410 */       this.jrdoHoldUpTime = new JRadioButton();
/* 411 */       this.jrdoHoldUpTime.setBounds(new Rectangle(8, 76, 297, 17));
/* 412 */       this.jrdoHoldUpTime.setText("Hold-up time");
/* 413 */       this.jrdoHoldUpTime.setActionCommand("Plot hold-up time");
/*     */     }
/* 415 */     return this.jrdoHoldUpTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JSlider getJsliderFlowVelocityPosition()
/*     */   {
/* 424 */     if (this.jsliderFlowVelocityPosition == null) {
/* 425 */       this.jsliderFlowVelocityPosition = new JSlider();
/* 426 */       this.jsliderFlowVelocityPosition.setBounds(new Rectangle(28, 148, 221, 45));
/* 427 */       this.jsliderFlowVelocityPosition.setName("Plot flow velocity position slider");
/* 428 */       this.jsliderFlowVelocityPosition.setMajorTickSpacing(500);
/* 429 */       this.jsliderFlowVelocityPosition.setMaximum(1000);
/* 430 */       this.jsliderFlowVelocityPosition.setMinimum(0);
/* 431 */       this.jsliderFlowVelocityPosition.setMinorTickSpacing(100);
/* 432 */       this.jsliderFlowVelocityPosition.setPaintLabels(true);
/* 433 */       this.jsliderFlowVelocityPosition.setPaintTicks(true);
/* 434 */       this.jsliderFlowVelocityPosition.setPaintTrack(true);
/* 435 */       this.jsliderFlowVelocityPosition.setEnabled(false);
/* 436 */       this.jsliderFlowVelocityPosition.setValue(0);
/* 437 */       Hashtable labelTable = new Hashtable();
/* 438 */       labelTable.put(new Integer(0), new JLabel("0.0"));
/* 439 */       labelTable.put(new Integer(500), new JLabel("0.5"));
/* 440 */       labelTable.put(new Integer(1000), new JLabel("1.0"));
/* 441 */       this.jsliderFlowVelocityPosition.setLabelTable(labelTable);
/* 442 */       this.jsliderFlowVelocityPosition.setFont(new Font("Dialog", 0, 12));
/*     */     }
/* 444 */     return this.jsliderFlowVelocityPosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtFlowVelocityColumnPosition()
/*     */   {
/* 453 */     if (this.jtxtFlowVelocityColumnPosition == null) {
/* 454 */       this.jtxtFlowVelocityColumnPosition = new JTextField();
/* 455 */       this.jtxtFlowVelocityColumnPosition.setBounds(new Rectangle(252, 152, 57, 26));
/* 456 */       this.jtxtFlowVelocityColumnPosition.setEnabled(false);
/* 457 */       this.jtxtFlowVelocityColumnPosition.setText("0");
/*     */     }
/* 459 */     return this.jtxtFlowVelocityColumnPosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JSlider getJsliderDensityPosition()
/*     */   {
/* 468 */     if (this.jsliderDensityPosition == null) {
/* 469 */       this.jsliderDensityPosition = new JSlider();
/* 470 */       this.jsliderDensityPosition.setBounds(new Rectangle(28, 220, 221, 45));
/* 471 */       this.jsliderDensityPosition.setName("Plot gas density position slider");
/* 472 */       this.jsliderDensityPosition.setMajorTickSpacing(500);
/* 473 */       this.jsliderDensityPosition.setMaximum(1000);
/* 474 */       this.jsliderDensityPosition.setMinimum(0);
/* 475 */       this.jsliderDensityPosition.setMinorTickSpacing(100);
/* 476 */       this.jsliderDensityPosition.setPaintLabels(true);
/* 477 */       this.jsliderDensityPosition.setPaintTicks(true);
/* 478 */       this.jsliderDensityPosition.setPaintTrack(true);
/* 479 */       this.jsliderDensityPosition.setEnabled(false);
/* 480 */       this.jsliderDensityPosition.setValue(0);
/* 481 */       Hashtable labelTable = new Hashtable();
/* 482 */       labelTable.put(new Integer(0), new JLabel("0.0"));
/* 483 */       labelTable.put(new Integer(500), new JLabel("0.5"));
/* 484 */       labelTable.put(new Integer(1000), new JLabel("1.0"));
/* 485 */       this.jsliderDensityPosition.setLabelTable(labelTable);
/* 486 */       this.jsliderDensityPosition.setFont(new Font("Dialog", 0, 12));
/*     */     }
/* 488 */     return this.jsliderDensityPosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtDensityColumnPosition()
/*     */   {
/* 497 */     if (this.jtxtDensityColumnPosition == null) {
/* 498 */       this.jtxtDensityColumnPosition = new JTextField();
/* 499 */       this.jtxtDensityColumnPosition.setBounds(new Rectangle(252, 224, 57, 26));
/* 500 */       this.jtxtDensityColumnPosition.setEnabled(false);
/* 501 */       this.jtxtDensityColumnPosition.setText("0");
/*     */     }
/* 503 */     return this.jtxtDensityColumnPosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JSlider getJsliderPressurePosition()
/*     */   {
/* 512 */     if (this.jsliderPressurePosition == null) {
/* 513 */       this.jsliderPressurePosition = new JSlider();
/* 514 */       this.jsliderPressurePosition.setBounds(new Rectangle(28, 296, 221, 45));
/* 515 */       this.jsliderPressurePosition.setName("Plot pressure position slider");
/* 516 */       this.jsliderPressurePosition.setMajorTickSpacing(500);
/* 517 */       this.jsliderPressurePosition.setMaximum(1000);
/* 518 */       this.jsliderPressurePosition.setMinimum(0);
/* 519 */       this.jsliderPressurePosition.setMinorTickSpacing(100);
/* 520 */       this.jsliderPressurePosition.setPaintLabels(true);
/* 521 */       this.jsliderPressurePosition.setPaintTicks(true);
/* 522 */       this.jsliderPressurePosition.setPaintTrack(true);
/* 523 */       this.jsliderPressurePosition.setEnabled(false);
/* 524 */       this.jsliderPressurePosition.setValue(0);
/* 525 */       Hashtable labelTable = new Hashtable();
/* 526 */       labelTable.put(new Integer(0), new JLabel("0.0"));
/* 527 */       labelTable.put(new Integer(500), new JLabel("0.5"));
/* 528 */       labelTable.put(new Integer(1000), new JLabel("1.0"));
/* 529 */       this.jsliderPressurePosition.setLabelTable(labelTable);
/* 530 */       this.jsliderPressurePosition.setFont(new Font("Dialog", 0, 12));
/*     */     }
/* 532 */     return this.jsliderPressurePosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtPressurePosition()
/*     */   {
/* 541 */     if (this.jtxtPressurePosition == null) {
/* 542 */       this.jtxtPressurePosition = new JTextField();
/* 543 */       this.jtxtPressurePosition.setBounds(new Rectangle(252, 300, 57, 28));
/* 544 */       this.jtxtPressurePosition.setEnabled(false);
/* 545 */       this.jtxtPressurePosition.setText("0");
/*     */     }
/* 547 */     return this.jtxtPressurePosition;
/*     */   }
/*     */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/panels/PlotOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */