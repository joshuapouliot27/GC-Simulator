/*     */ package org.gcsimulator.panels;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JTextField;
/*     */ import org.gcsimulator.Globals;
/*     */ import org.jdesktop.swingx.JXPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InletOutlet
/*     */   extends JXPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  22 */   private JLabel jlblCarrierGas = null;
/*  23 */   public JComboBox jcboCarrierGas = null;
/*  24 */   private JLabel jlblGasViscosity = null;
/*  25 */   public JLabel jlblGasViscosityIndicator = null;
/*  26 */   private JLabel jlblGasViscosityUnit = null;
/*  27 */   public JRadioButton jrdoConstantFlowRate = null;
/*  28 */   public JRadioButton jrdoConstantPressure = null;
/*  29 */   public JLabel jlblFlowRate = null;
/*  30 */   public JTextField jtxtFlowRate = null;
/*  31 */   public JLabel jlblFlowRateUnit = null;
/*  32 */   public JLabel jlblPressure = null;
/*  33 */   public JTextField jtxtInletPressure = null;
/*  34 */   private JLabel jlblOutletPressure = null;
/*  35 */   public JRadioButton jrdoVacuum = null;
/*  36 */   public JRadioButton jrdoOtherPressure = null;
/*  37 */   public JTextField jtxtOtherPressure = null;
/*  38 */   public JLabel jlblOtherPressureUnit = null;
/*  39 */   private JLabel jlblSplitSplitless = null;
/*  40 */   public JRadioButton jrdoSplitless = null;
/*  41 */   public JRadioButton jrdoSplit = null;
/*  42 */   public JLabel jlblSplitRatio = null;
/*  43 */   public JTextField jtxtSplitRatio = null;
/*  44 */   public JLabel jlblSplitRatioUnits = null;
/*  45 */   private JLabel jlblLinerLength = null;
/*  46 */   public JTextField jtxtLinerLength = null;
/*  47 */   private JLabel jlblLinerLengthUnits = null;
/*  48 */   private JLabel jlblLinerInnerDiameter = null;
/*  49 */   public JTextField jtxtLinerInnerDiameter = null;
/*  50 */   private JLabel jlblLinerInnerDiameterUnits = null;
/*  51 */   private JLabel jlblLinerVolume = null;
/*  52 */   public JLabel jlblLinerVolumeIndicator = null;
/*  53 */   private JLabel jlblLinerVolumeUnits = null;
/*  54 */   private JLabel jlblInjectionVolume = null;
/*  55 */   public JTextField jtxtInjectionVolume = null;
/*  56 */   private JLabel jlblInjectionVolumeUnits = null;
/*     */   
/*  58 */   public JComboBox jcboInletPressureUnits = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InletOutlet()
/*     */   {
/*  66 */     initialize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initialize()
/*     */   {
/*  74 */     this.jlblLinerVolumeIndicator = new JLabel();
/*  75 */     this.jlblLinerVolumeIndicator.setBounds(new Rectangle(196, 344, 57, 16));
/*  76 */     this.jlblLinerVolumeIndicator.setText("400");
/*  77 */     this.jlblLinerVolumeIndicator.setFont(new Font("Dialog", 0, 12));
/*  78 */     this.jlblInjectionVolumeUnits = new JLabel();
/*  79 */     this.jlblInjectionVolumeUnits.setBounds(new Rectangle(256, 368, 53, 16));
/*  80 */     this.jlblInjectionVolumeUnits.setText("<html>&micro;L</html>");
/*  81 */     this.jlblInjectionVolume = new JLabel();
/*  82 */     this.jlblInjectionVolume.setBounds(new Rectangle(8, 368, 181, 16));
/*  83 */     this.jlblInjectionVolume.setDisplayedMnemonic(0);
/*  84 */     this.jlblInjectionVolume.setText("Injection volume:");
/*  85 */     this.jlblInjectionVolume.setVisible(true);
/*  86 */     this.jlblLinerVolumeUnits = new JLabel();
/*  87 */     this.jlblLinerVolumeUnits.setBounds(new Rectangle(256, 344, 53, 16));
/*  88 */     this.jlblLinerVolumeUnits.setText("mL");
/*  89 */     this.jlblLinerVolume = new JLabel();
/*  90 */     this.jlblLinerVolume.setBounds(new Rectangle(8, 344, 181, 16));
/*  91 */     this.jlblLinerVolume.setDisplayedMnemonic(0);
/*  92 */     this.jlblLinerVolume.setText("Liner volume:");
/*  93 */     this.jlblLinerVolume.setVisible(true);
/*  94 */     this.jlblLinerInnerDiameterUnits = new JLabel();
/*  95 */     this.jlblLinerInnerDiameterUnits.setBounds(new Rectangle(256, 320, 53, 16));
/*  96 */     this.jlblLinerInnerDiameterUnits.setText("mm");
/*  97 */     this.jlblLinerInnerDiameter = new JLabel();
/*  98 */     this.jlblLinerInnerDiameter.setBounds(new Rectangle(8, 320, 181, 16));
/*  99 */     this.jlblLinerInnerDiameter.setDisplayedMnemonic(0);
/* 100 */     this.jlblLinerInnerDiameter.setText("Liner inner diameter:");
/* 101 */     this.jlblLinerInnerDiameter.setVisible(true);
/* 102 */     this.jlblLinerLengthUnits = new JLabel();
/* 103 */     this.jlblLinerLengthUnits.setBounds(new Rectangle(256, 296, 53, 16));
/* 104 */     this.jlblLinerLengthUnits.setText("mm");
/* 105 */     this.jlblLinerLength = new JLabel();
/* 106 */     this.jlblLinerLength.setBounds(new Rectangle(8, 296, 181, 16));
/* 107 */     this.jlblLinerLength.setDisplayedMnemonic(0);
/* 108 */     this.jlblLinerLength.setText("Liner length:");
/* 109 */     this.jlblLinerLength.setVisible(true);
/* 110 */     this.jlblSplitRatioUnits = new JLabel();
/* 111 */     this.jlblSplitRatioUnits.setBounds(new Rectangle(256, 272, 53, 16));
/* 112 */     this.jlblSplitRatioUnits.setText(": 1");
/* 113 */     this.jlblSplitRatio = new JLabel();
/* 114 */     this.jlblSplitRatio.setBounds(new Rectangle(24, 272, 165, 16));
/* 115 */     this.jlblSplitRatio.setText("Split ratio:");
/* 116 */     this.jlblSplitRatio.setDisplayedMnemonic(0);
/* 117 */     this.jlblSplitSplitless = new JLabel();
/* 118 */     this.jlblSplitSplitless.setBounds(new Rectangle(8, 204, 237, 16));
/* 119 */     this.jlblSplitSplitless.setDisplayedMnemonic(0);
/* 120 */     this.jlblSplitSplitless.setText("Injection mode:");
/* 121 */     this.jlblSplitSplitless.setVisible(true);
/* 122 */     this.jlblOtherPressureUnit = new JLabel();
/* 123 */     this.jlblOtherPressureUnit.setBounds(new Rectangle(252, 176, 57, 16));
/* 124 */     this.jlblOtherPressureUnit.setEnabled(false);
/* 125 */     this.jlblOtherPressureUnit.setText("kPa");
/* 126 */     this.jlblOtherPressureUnit.setVisible(true);
/* 127 */     this.jlblOutletPressure = new JLabel();
/* 128 */     this.jlblOutletPressure.setBounds(new Rectangle(8, 156, 221, 16));
/* 129 */     this.jlblOutletPressure.setDisplayedMnemonic(0);
/* 130 */     this.jlblOutletPressure.setText("Column outlet pressure:");
/* 131 */     this.jlblOutletPressure.setVisible(true);
/* 132 */     this.jlblPressure = new JLabel();
/* 133 */     this.jlblPressure.setBounds(new Rectangle(24, 132, 161, 16));
/* 134 */     this.jlblPressure.setDisplayedMnemonic(0);
/* 135 */     this.jlblPressure.setText("Column inlet pressure:");
/* 136 */     this.jlblPressure.setEnabled(false);
/* 137 */     this.jlblFlowRateUnit = new JLabel();
/* 138 */     this.jlblFlowRateUnit.setBounds(new Rectangle(252, 88, 57, 16));
/* 139 */     this.jlblFlowRateUnit.setText("mL/min");
/* 140 */     this.jlblFlowRate = new JLabel();
/* 141 */     this.jlblFlowRate.setBounds(new Rectangle(24, 88, 161, 16));
/* 142 */     this.jlblFlowRate.setText("Flow rate:");
/* 143 */     this.jlblFlowRate.setDisplayedMnemonic(0);
/* 144 */     this.jlblGasViscosityUnit = new JLabel();
/* 145 */     this.jlblGasViscosityUnit.setBounds(new Rectangle(252, 40, 57, 16));
/* 146 */     this.jlblGasViscosityUnit.setPreferredSize(new Dimension(50, 16));
/* 147 */     this.jlblGasViscosityUnit.setText("<html>&micro;Pa s</html>");
/* 148 */     this.jlblGasViscosityUnit.setFont(new Font("Dialog", 0, 12));
/* 149 */     this.jlblGasViscosityIndicator = new JLabel();
/* 150 */     this.jlblGasViscosityIndicator.setBounds(new Rectangle(188, 40, 61, 16));
/* 151 */     this.jlblGasViscosityIndicator.setText("400");
/* 152 */     this.jlblGasViscosityIndicator.setFont(new Font("Dialog", 0, 12));
/* 153 */     this.jlblGasViscosity = new JLabel();
/* 154 */     this.jlblGasViscosity.setBounds(new Rectangle(8, 40, 173, 16));
/* 155 */     this.jlblGasViscosity.setText("Gas viscosity:");
/* 156 */     this.jlblCarrierGas = new JLabel();
/* 157 */     this.jlblCarrierGas.setBounds(new Rectangle(8, 12, 117, 16));
/* 158 */     this.jlblCarrierGas.setText("Carrier gas:");
/* 159 */     setLayout(null);
/* 160 */     setSize(new Dimension(314, 397));
/* 161 */     setPreferredSize(getSize());
/* 162 */     add(this.jlblCarrierGas, null);
/* 163 */     add(getJcboCarrierGas(), null);
/* 164 */     add(this.jlblGasViscosity, null);
/* 165 */     add(this.jlblGasViscosityIndicator, null);
/* 166 */     add(this.jlblGasViscosityUnit, null);
/* 167 */     add(getJrdoConstantFlowRate(), null);
/* 168 */     add(getJrdoConstantPressure(), null);
/* 169 */     add(this.jlblFlowRate, null);
/* 170 */     add(getJtxtFlowRate(), null);
/* 171 */     add(this.jlblFlowRateUnit, null);
/* 172 */     add(this.jlblPressure, null);
/* 173 */     add(getJtxtInletPressure(), null);
/* 174 */     add(this.jlblOutletPressure, null);
/* 175 */     add(getJrdoVacuum(), null);
/* 176 */     add(getJrdoOtherPressure(), null);
/* 177 */     add(getJtxtOtherPressure(), null);
/* 178 */     add(this.jlblOtherPressureUnit, null);
/* 179 */     add(this.jlblSplitSplitless, null);
/* 180 */     add(getJrdoSplitless(), null);
/* 181 */     add(getJrdoSplit(), null);
/* 182 */     add(this.jlblSplitRatio, null);
/* 183 */     add(getJtxtSplitRatio(), null);
/* 184 */     add(this.jlblSplitRatioUnits, null);
/* 185 */     add(this.jlblLinerLength, null);
/* 186 */     add(getJtxtLinerLength(), null);
/* 187 */     add(this.jlblLinerLengthUnits, null);
/* 188 */     add(this.jlblLinerInnerDiameter, null);
/* 189 */     add(getJtxtLinerInnerDiameter(), null);
/* 190 */     add(this.jlblLinerInnerDiameterUnits, null);
/* 191 */     add(this.jlblLinerVolume, null);
/* 192 */     add(this.jlblLinerVolumeUnits, null);
/* 193 */     add(this.jlblInjectionVolume, null);
/* 194 */     add(getJtxtInjectionVolume(), null);
/* 195 */     add(this.jlblInjectionVolumeUnits, null);
/* 196 */     add(this.jlblLinerVolumeIndicator, null);
/* 197 */     add(getJcboInletPressureUnits(), null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JComboBox getJcboCarrierGas()
/*     */   {
/* 207 */     if (this.jcboCarrierGas == null) {
/* 208 */       this.jcboCarrierGas = new JComboBox(Globals.CarrierGases);
/* 209 */       this.jcboCarrierGas.setBounds(new Rectangle(128, 8, 177, 25));
/* 210 */       this.jcboCarrierGas.setEnabled(true);
/* 211 */       this.jcboCarrierGas.setSelectedIndex(1);
/* 212 */       this.jcboCarrierGas.setActionCommand("CarrierGasChanged");
/*     */     }
/* 214 */     return this.jcboCarrierGas;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoConstantFlowRate()
/*     */   {
/* 223 */     if (this.jrdoConstantFlowRate == null) {
/* 224 */       this.jrdoConstantFlowRate = new JRadioButton();
/* 225 */       this.jrdoConstantFlowRate.setBounds(new Rectangle(8, 64, 293, 20));
/* 226 */       this.jrdoConstantFlowRate.setText("Constant flow rate mode");
/* 227 */       this.jrdoConstantFlowRate.setSelected(true);
/*     */     }
/* 229 */     return this.jrdoConstantFlowRate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoConstantPressure()
/*     */   {
/* 238 */     if (this.jrdoConstantPressure == null) {
/* 239 */       this.jrdoConstantPressure = new JRadioButton();
/* 240 */       this.jrdoConstantPressure.setBounds(new Rectangle(8, 108, 237, 20));
/* 241 */       this.jrdoConstantPressure.setText("Constant pressure mode");
/*     */     }
/* 243 */     return this.jrdoConstantPressure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtFlowRate()
/*     */   {
/* 252 */     if (this.jtxtFlowRate == null) {
/* 253 */       this.jtxtFlowRate = new JTextField();
/* 254 */       this.jtxtFlowRate.setBounds(new Rectangle(188, 84, 61, 26));
/* 255 */       this.jtxtFlowRate.setText("1.0");
/*     */     }
/* 257 */     return this.jtxtFlowRate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtInletPressure()
/*     */   {
/* 266 */     if (this.jtxtInletPressure == null) {
/* 267 */       this.jtxtInletPressure = new JTextField();
/* 268 */       this.jtxtInletPressure.setBounds(new Rectangle(188, 128, 61, 26));
/* 269 */       this.jtxtInletPressure.setText("100");
/* 270 */       this.jtxtInletPressure.setEditable(true);
/* 271 */       this.jtxtInletPressure.setActionCommand("Inlet Pressure");
/* 272 */       this.jtxtInletPressure.setEnabled(false);
/*     */     }
/* 274 */     return this.jtxtInletPressure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoVacuum()
/*     */   {
/* 283 */     if (this.jrdoVacuum == null) {
/* 284 */       this.jrdoVacuum = new JRadioButton();
/* 285 */       this.jrdoVacuum.setBounds(new Rectangle(8, 176, 89, 20));
/* 286 */       this.jrdoVacuum.setText("Vacuum");
/* 287 */       this.jrdoVacuum.setSelected(true);
/*     */     }
/* 289 */     return this.jrdoVacuum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoOtherPressure()
/*     */   {
/* 298 */     if (this.jrdoOtherPressure == null) {
/* 299 */       this.jrdoOtherPressure = new JRadioButton();
/* 300 */       this.jrdoOtherPressure.setBounds(new Rectangle(104, 176, 81, 20));
/* 301 */       this.jrdoOtherPressure.setText("Other:");
/* 302 */       this.jrdoOtherPressure.setActionCommand("OtherPressure");
/*     */     }
/* 304 */     return this.jrdoOtherPressure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtOtherPressure()
/*     */   {
/* 313 */     if (this.jtxtOtherPressure == null) {
/* 314 */       this.jtxtOtherPressure = new JTextField();
/* 315 */       this.jtxtOtherPressure.setBounds(new Rectangle(188, 172, 61, 26));
/* 316 */       this.jtxtOtherPressure.setText("101.325");
/* 317 */       this.jtxtOtherPressure.setEnabled(false);
/*     */     }
/* 319 */     return this.jtxtOtherPressure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoSplitless()
/*     */   {
/* 328 */     if (this.jrdoSplitless == null) {
/* 329 */       this.jrdoSplitless = new JRadioButton();
/* 330 */       this.jrdoSplitless.setBounds(new Rectangle(8, 224, 160, 18));
/* 331 */       this.jrdoSplitless.setSelected(false);
/* 332 */       this.jrdoSplitless.setText("Splitless injection");
/*     */     }
/* 334 */     return this.jrdoSplitless;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JRadioButton getJrdoSplit()
/*     */   {
/* 343 */     if (this.jrdoSplit == null) {
/* 344 */       this.jrdoSplit = new JRadioButton();
/* 345 */       this.jrdoSplit.setBounds(new Rectangle(8, 248, 119, 18));
/* 346 */       this.jrdoSplit.setSelected(true);
/* 347 */       this.jrdoSplit.setText("Split injection");
/*     */     }
/* 349 */     return this.jrdoSplit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtSplitRatio()
/*     */   {
/* 358 */     if (this.jtxtSplitRatio == null) {
/* 359 */       this.jtxtSplitRatio = new JTextField();
/* 360 */       this.jtxtSplitRatio.setBounds(new Rectangle(192, 268, 61, 26));
/* 361 */       this.jtxtSplitRatio.setText("10");
/*     */     }
/* 363 */     return this.jtxtSplitRatio;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtLinerLength()
/*     */   {
/* 372 */     if (this.jtxtLinerLength == null) {
/* 373 */       this.jtxtLinerLength = new JTextField();
/* 374 */       this.jtxtLinerLength.setBounds(new Rectangle(192, 292, 61, 26));
/* 375 */       this.jtxtLinerLength.setText("4");
/*     */     }
/* 377 */     return this.jtxtLinerLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtLinerInnerDiameter()
/*     */   {
/* 386 */     if (this.jtxtLinerInnerDiameter == null) {
/* 387 */       this.jtxtLinerInnerDiameter = new JTextField();
/* 388 */       this.jtxtLinerInnerDiameter.setBounds(new Rectangle(192, 316, 61, 26));
/* 389 */       this.jtxtLinerInnerDiameter.setText(".2");
/*     */     }
/* 391 */     return this.jtxtLinerInnerDiameter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtInjectionVolume()
/*     */   {
/* 400 */     if (this.jtxtInjectionVolume == null) {
/* 401 */       this.jtxtInjectionVolume = new JTextField();
/* 402 */       this.jtxtInjectionVolume.setBounds(new Rectangle(192, 364, 61, 26));
/* 403 */       this.jtxtInjectionVolume.setText("1");
/*     */     }
/* 405 */     return this.jtxtInjectionVolume;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JComboBox getJcboInletPressureUnits()
/*     */   {
/* 414 */     if (this.jcboInletPressureUnits == null) {
/* 415 */       this.jcboInletPressureUnits = new JComboBox(new String[] { "kPag", "kPa" });
/* 416 */       this.jcboInletPressureUnits.setBounds(new Rectangle(248, 128, 61, 25));
/* 417 */       this.jcboInletPressureUnits.setActionCommand("Inlet pressure units changed");
/*     */     }
/* 419 */     return this.jcboInletPressureUnits;
/*     */   }
/*     */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/panels/InletOutlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */