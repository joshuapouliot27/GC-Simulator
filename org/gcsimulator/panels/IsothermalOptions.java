/*    */ package org.gcsimulator.panels;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JSlider;
/*    */ import javax.swing.JTextField;
/*    */ import org.jdesktop.swingx.JXPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IsothermalOptions
/*    */   extends JXPanel
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   private JLabel jlblTemperature = null;
/* 18 */   public JSlider jsliderTemperature = null;
/* 19 */   public JTextField jtxtTemperature = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IsothermalOptions()
/*    */   {
/* 26 */     initialize();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private void initialize()
/*    */   {
/* 34 */     this.jlblTemperature = new JLabel();
/* 35 */     this.jlblTemperature.setBounds(new Rectangle(8, 0, 189, 16));
/* 36 */     this.jlblTemperature.setText("<html>Temperature (&deg;C):</html>");
/* 37 */     setLayout(null);
/* 38 */     setBounds(new Rectangle(0, 0, 314, 63));
/* 39 */     setPreferredSize(getSize());
/* 40 */     add(this.jlblTemperature, null);
/* 41 */     add(getJsliderTemperature(), null);
/* 42 */     add(getJtxtTemperature(), null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private JSlider getJsliderTemperature()
/*    */   {
/* 52 */     if (this.jsliderTemperature == null) {
/* 53 */       this.jsliderTemperature = new JSlider();
/* 54 */       this.jsliderTemperature.setBounds(new Rectangle(8, 16, 237, 43));
/* 55 */       this.jsliderTemperature.setFont(new Font("Dialog", 0, 12));
/* 56 */       this.jsliderTemperature.setName("Temperature Slider");
/* 57 */       this.jsliderTemperature.setMajorTickSpacing(40);
/* 58 */       this.jsliderTemperature.setMaximum(320);
/* 59 */       this.jsliderTemperature.setMinorTickSpacing(10);
/* 60 */       this.jsliderTemperature.setPaintLabels(true);
/* 61 */       this.jsliderTemperature.setPaintTicks(true);
/* 62 */       this.jsliderTemperature.setPaintTrack(true);
/* 63 */       this.jsliderTemperature.setMinimum(60);
/* 64 */       this.jsliderTemperature.setValue(40);
/*    */     }
/* 66 */     return this.jsliderTemperature;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private JTextField getJtxtTemperature()
/*    */   {
/* 75 */     if (this.jtxtTemperature == null) {
/* 76 */       this.jtxtTemperature = new JTextField();
/* 77 */       this.jtxtTemperature.setText("60");
/* 78 */       this.jtxtTemperature.setBounds(new Rectangle(252, 16, 57, 26));
/*    */     }
/* 80 */     return this.jtxtTemperature;
/*    */   }
/*    */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/panels/IsothermalOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */