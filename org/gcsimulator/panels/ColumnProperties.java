/*     */ package org.gcsimulator.panels;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
/*     */ import org.gcsimulator.Globals;
/*     */ import org.jdesktop.swingx.JXPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColumnProperties
/*     */   extends JXPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  20 */   private JLabel jlblColumnLength = null;
/*  21 */   private JLabel jlblColumnDiameter = null;
/*  22 */   private JLabel jlblParticleSize = null;
/*  23 */   private JLabel jlblHoldUpTime = null;
/*  24 */   public JTextField jtxtColumnLength = null;
/*  25 */   public JTextField jtxtColumnDiameter = null;
/*  26 */   public JTextField jtxtFilmThickness = null;
/*  27 */   public JLabel jlblHoldUpTimeIndicator = null;
/*  28 */   private JLabel jlblColumnLengthUnits = null;
/*  29 */   private JLabel jlblColumnDiameterUnits = null;
/*  30 */   private JLabel jlblParticleSizeUnits = null;
/*  31 */   private JLabel jlblHoldUpTimeUnits = null;
/*  32 */   private JLabel jlblStationaryPhase = null;
/*  33 */   public JComboBox jcboStationaryPhase = null;
/*  34 */   private JLabel jlblTotalPorosity = null;
/*  35 */   public JLabel jlblPhaseRatioIndicator = null;
/*  36 */   private JLabel jlblHETP = null;
/*  37 */   public JLabel jlblHETPIndicator = null;
/*  38 */   private JLabel jlblHETPUnits = null;
/*  39 */   private JLabel jlblTheoreticalPlates = null;
/*  40 */   public JLabel jlblTheoreticalPlatesIndicator = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ColumnProperties()
/*     */   {
/*  48 */     initialize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initialize()
/*     */   {
/*  56 */     this.jlblTheoreticalPlatesIndicator = new JLabel();
/*  57 */     this.jlblTheoreticalPlatesIndicator.setBounds(new Rectangle(192, 204, 117, 16));
/*  58 */     this.jlblTheoreticalPlatesIndicator.setText("0.9987");
/*  59 */     this.jlblTheoreticalPlatesIndicator.setFont(new Font("Dialog", 0, 12));
/*  60 */     this.jlblTheoreticalPlates = new JLabel();
/*  61 */     this.jlblTheoreticalPlates.setBounds(new Rectangle(8, 204, 177, 16));
/*  62 */     this.jlblTheoreticalPlates.setText("Theoretical plates:");
/*  63 */     this.jlblHETPUnits = new JLabel();
/*  64 */     this.jlblHETPUnits.setBounds(new Rectangle(252, 180, 57, 16));
/*  65 */     this.jlblHETPUnits.setText("mm");
/*  66 */     this.jlblHETPUnits.setFont(new Font("Dialog", 0, 12));
/*  67 */     this.jlblHETPIndicator = new JLabel();
/*  68 */     this.jlblHETPIndicator.setBounds(new Rectangle(192, 180, 57, 16));
/*  69 */     this.jlblHETPIndicator.setText("0.9987");
/*  70 */     this.jlblHETPIndicator.setFont(new Font("Dialog", 0, 12));
/*  71 */     this.jlblHETP = new JLabel();
/*  72 */     this.jlblHETP.setBounds(new Rectangle(8, 180, 177, 16));
/*  73 */     this.jlblHETP.setText("HETP:");
/*  74 */     this.jlblPhaseRatioIndicator = new JLabel();
/*  75 */     this.jlblPhaseRatioIndicator.setBounds(new Rectangle(192, 132, 57, 17));
/*  76 */     this.jlblPhaseRatioIndicator.setText("0.64");
/*  77 */     this.jlblPhaseRatioIndicator.setFont(new Font("Dialog", 0, 12));
/*  78 */     this.jlblTotalPorosity = new JLabel();
/*  79 */     this.jlblTotalPorosity.setBounds(new Rectangle(8, 132, 177, 16));
/*  80 */     this.jlblTotalPorosity.setText("Phase ratio:");
/*  81 */     this.jlblStationaryPhase = new JLabel();
/*  82 */     this.jlblStationaryPhase.setText("Stationary phase:");
/*  83 */     this.jlblStationaryPhase.setSize(new Dimension(125, 16));
/*  84 */     this.jlblStationaryPhase.setLocation(new Point(8, 8));
/*  85 */     this.jlblHoldUpTimeUnits = new JLabel();
/*  86 */     this.jlblHoldUpTimeUnits.setText("min");
/*  87 */     this.jlblHoldUpTimeUnits.setLocation(new Point(252, 156));
/*  88 */     this.jlblHoldUpTimeUnits.setSize(new Dimension(57, 16));
/*  89 */     this.jlblHoldUpTimeUnits.setFont(new Font("Dialog", 0, 12));
/*  90 */     this.jlblParticleSizeUnits = new JLabel();
/*  91 */     this.jlblParticleSizeUnits.setText("µm");
/*  92 */     this.jlblParticleSizeUnits.setLocation(new Point(252, 108));
/*  93 */     this.jlblParticleSizeUnits.setSize(new Dimension(57, 16));
/*  94 */     this.jlblParticleSizeUnits.setFont(new Font("Dialog", 0, 12));
/*  95 */     this.jlblColumnDiameterUnits = new JLabel();
/*  96 */     this.jlblColumnDiameterUnits.setText("mm");
/*  97 */     this.jlblColumnDiameterUnits.setLocation(new Point(252, 84));
/*  98 */     this.jlblColumnDiameterUnits.setSize(new Dimension(57, 16));
/*  99 */     this.jlblColumnDiameterUnits.setFont(new Font("Dialog", 0, 12));
/* 100 */     this.jlblColumnLengthUnits = new JLabel();
/* 101 */     this.jlblColumnLengthUnits.setText("m");
/* 102 */     this.jlblColumnLengthUnits.setBounds(new Rectangle(252, 60, 57, 16));
/* 103 */     this.jlblColumnLengthUnits.setFont(new Font("Dialog", 0, 12));
/* 104 */     this.jlblHoldUpTimeIndicator = new JLabel();
/* 105 */     this.jlblHoldUpTimeIndicator.setText("0.9987");
/* 106 */     this.jlblHoldUpTimeIndicator.setLocation(new Point(192, 156));
/* 107 */     this.jlblHoldUpTimeIndicator.setSize(new Dimension(57, 16));
/* 108 */     this.jlblHoldUpTimeIndicator.setFont(new Font("Dialog", 0, 12));
/* 109 */     this.jlblHoldUpTime = new JLabel();
/* 110 */     this.jlblHoldUpTime.setText("Hold-up time:");
/* 111 */     this.jlblHoldUpTime.setSize(new Dimension(177, 16));
/* 112 */     this.jlblHoldUpTime.setLocation(new Point(8, 156));
/* 113 */     this.jlblParticleSize = new JLabel();
/* 114 */     this.jlblParticleSize.setText("Film thickness:");
/* 115 */     this.jlblParticleSize.setSize(new Dimension(177, 16));
/* 116 */     this.jlblParticleSize.setLocation(new Point(8, 108));
/* 117 */     this.jlblColumnDiameter = new JLabel();
/* 118 */     this.jlblColumnDiameter.setText("Inner diameter:");
/* 119 */     this.jlblColumnDiameter.setSize(new Dimension(177, 16));
/* 120 */     this.jlblColumnDiameter.setLocation(new Point(8, 84));
/* 121 */     this.jlblColumnLength = new JLabel();
/* 122 */     this.jlblColumnLength.setText("Length:");
/* 123 */     this.jlblColumnLength.setSize(new Dimension(177, 16));
/* 124 */     this.jlblColumnLength.setLocation(new Point(8, 60));
/* 125 */     setLayout(null);
/* 126 */     setBounds(new Rectangle(0, 0, 314, 227));
/* 127 */     setPreferredSize(getSize());
/* 128 */     add(this.jlblColumnLength, null);
/* 129 */     add(this.jlblColumnDiameter, null);
/* 130 */     add(this.jlblParticleSize, null);
/* 131 */     add(this.jlblHoldUpTime, null);
/* 132 */     add(getJtxtColumnLength(), null);
/* 133 */     add(getJtxtColumnDiameter(), null);
/* 134 */     add(getJtxtFilmThickness(), null);
/* 135 */     add(this.jlblHoldUpTimeIndicator, null);
/* 136 */     add(this.jlblColumnLengthUnits, null);
/* 137 */     add(this.jlblColumnDiameterUnits, null);
/* 138 */     add(this.jlblParticleSizeUnits, null);
/* 139 */     add(this.jlblHoldUpTimeUnits, null);
/* 140 */     add(this.jlblStationaryPhase, null);
/* 141 */     add(getJcboStationaryPhase(), null);
/* 142 */     add(this.jlblTotalPorosity, null);
/* 143 */     add(this.jlblPhaseRatioIndicator, null);
/* 144 */     add(this.jlblHETP, null);
/* 145 */     add(this.jlblHETPIndicator, null);
/* 146 */     add(this.jlblHETPUnits, null);
/* 147 */     add(this.jlblTheoreticalPlates, null);
/* 148 */     add(this.jlblTheoreticalPlatesIndicator, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtColumnLength()
/*     */   {
/* 158 */     if (this.jtxtColumnLength == null) {
/* 159 */       this.jtxtColumnLength = new JTextField();
/* 160 */       this.jtxtColumnLength.setText("30");
/* 161 */       this.jtxtColumnLength.setBounds(new Rectangle(188, 56, 61, 26));
/*     */     }
/* 163 */     return this.jtxtColumnLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtColumnDiameter()
/*     */   {
/* 172 */     if (this.jtxtColumnDiameter == null) {
/* 173 */       this.jtxtColumnDiameter = new JTextField();
/* 174 */       this.jtxtColumnDiameter.setText("0.25");
/* 175 */       this.jtxtColumnDiameter.setBounds(new Rectangle(188, 80, 61, 26));
/*     */     }
/* 177 */     return this.jtxtColumnDiameter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtFilmThickness()
/*     */   {
/* 186 */     if (this.jtxtFilmThickness == null) {
/* 187 */       this.jtxtFilmThickness = new JTextField();
/* 188 */       this.jtxtFilmThickness.setText("0.25");
/* 189 */       this.jtxtFilmThickness.setBounds(new Rectangle(188, 104, 61, 26));
/*     */     }
/* 191 */     return this.jtxtFilmThickness;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JComboBox getJcboStationaryPhase()
/*     */   {
/* 200 */     if (this.jcboStationaryPhase == null) {
/* 201 */       this.jcboStationaryPhase = new JComboBox(Globals.StationaryPhaseArray);
/* 202 */       this.jcboStationaryPhase.setSelectedIndex(0);
/* 203 */       this.jcboStationaryPhase.setLocation(new Point(8, 26));
/* 204 */       this.jcboStationaryPhase.setActionCommand("StationaryPhaseComboBoxChanged");
/* 205 */       this.jcboStationaryPhase.setSize(new Dimension(297, 27));
/*     */     }
/* 207 */     return this.jcboStationaryPhase;
/*     */   }
/*     */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/panels/ColumnProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */