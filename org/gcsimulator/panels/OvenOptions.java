/*    */ package org.gcsimulator.panels;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JRadioButton;
/*    */ import org.jdesktop.swingx.JXPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OvenOptions
/*    */   extends JXPanel
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 15 */   public JRadioButton jrdoIsothermalElution = null;
/* 16 */   public JRadioButton jrdoProgrammedTemperatureElution = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public OvenOptions()
/*    */   {
/* 24 */     initialize();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private void initialize()
/*    */   {
/* 32 */     setLayout(null);
/* 33 */     setBounds(new Rectangle(0, 0, 314, 57));
/* 34 */     setPreferredSize(getSize());
/* 35 */     add(getJrdoIsothermalElution(), null);
/* 36 */     add(getJrdoProgrammedTemperatureElution(), null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private JRadioButton getJrdoIsothermalElution()
/*    */   {
/* 46 */     if (this.jrdoIsothermalElution == null) {
/* 47 */       this.jrdoIsothermalElution = new JRadioButton();
/* 48 */       this.jrdoIsothermalElution.setBounds(new Rectangle(8, 8, 233, 17));
/* 49 */       this.jrdoIsothermalElution.setText("Isothermal elution mode");
/* 50 */       this.jrdoIsothermalElution.setSelected(true);
/*    */     }
/* 52 */     return this.jrdoIsothermalElution;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private JRadioButton getJrdoProgrammedTemperatureElution()
/*    */   {
/* 61 */     if (this.jrdoProgrammedTemperatureElution == null) {
/* 62 */       this.jrdoProgrammedTemperatureElution = new JRadioButton();
/* 63 */       this.jrdoProgrammedTemperatureElution.setBounds(new Rectangle(8, 32, 297, 17));
/* 64 */       this.jrdoProgrammedTemperatureElution.setSelected(false);
/* 65 */       this.jrdoProgrammedTemperatureElution.setText("Programmed-temperature elution mode");
/* 66 */       this.jrdoProgrammedTemperatureElution.setActionCommand("Programmed temperature elution mode");
/* 67 */       this.jrdoProgrammedTemperatureElution.setRolloverEnabled(true);
/*    */     }
/* 69 */     return this.jrdoProgrammedTemperatureElution;
/*    */   }
/*    */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/panels/OvenOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */