 package org.gcsimulator;
 
 import java.awt.Font;
 import java.awt.Frame;
 import java.awt.Rectangle;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.FocusEvent;
 import java.awt.event.FocusListener;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 import java.text.DecimalFormat;
 import java.text.NumberFormat;
 import java.util.Vector;
 import javax.swing.DefaultComboBoxModel;
 import javax.swing.JButton;
 import javax.swing.JComboBox;
 import javax.swing.JDialog;
 import javax.swing.JLabel;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JTextField;
 import javax.swing.event.ChangeEvent;
 import javax.swing.event.ChangeListener;
 
 
 
 
 
 
 
 
 
 
 
 public class ChemicalDialog
   extends JDialog
   implements ActionListener, KeyListener, FocusListener, ChangeListener
 {
   private static final long serialVersionUID = 1L;
/*  41 */   private JPanel jContentPane = null;
/*  42 */   private JComboBox jcboCompound = null;
/*  43 */   private JLabel jLabel1 = null;
/*  44 */   private JTextField jtxtConcentration1 = null;
/*  45 */   private JLabel jLabel2 = null;
/*  46 */   private JButton jbtnOk = null;
/*  47 */   private JButton jbtnCancel = null;
/*  48 */   private JPanel jPanel = null;
   
/*  50 */   public Vector<Integer> m_vectCompoundsUsed = new Vector();
/*  51 */   public boolean m_bOk = false;
/*  52 */   public int m_iCompound = 0;
/*  53 */   public String m_strCompoundName = "";
/*  54 */   public double m_dConcentration1 = 50.0D;
/*  55 */   public int m_iStationaryPhase = 0;
   public InterpolationFunction InterpolatedLogkvsT;
/*  57 */   private JLabel jLabel31 = null;
/*  58 */   private JLabel jlblCompound = null;
   
 
 
   public ChemicalDialog(Frame owner, boolean bEditDialog)
   {
/*  64 */     super(owner);
/*  65 */     initialize();
     
/*  67 */     if (bEditDialog)
     {
/*  69 */       setTitle("Edit Compound");
     }
     
/*  72 */     this.jcboCompound.addActionListener(this);
/*  73 */     this.jbtnOk.addActionListener(this);
/*  74 */     this.jbtnCancel.addActionListener(this);
/*  75 */     this.jtxtConcentration1.addKeyListener(this);
/*  76 */     this.jtxtConcentration1.addFocusListener(this);
   }
   
 
 
 
 
   private void initialize()
   {
/*  85 */     setSize(441, 172);
/*  86 */     setContentPane(getJContentPane());
/*  87 */     setModal(true);
/*  88 */     setResizable(false);
/*  89 */     setTitle("Add New Compound");
/*  90 */     setCompoundVariables();
/*  91 */     performValidations();
   }
   
 
 
 
 
   private JPanel getJContentPane()
   {
/* 100 */     if (this.jContentPane == null) {
/* 101 */       this.jlblCompound = new JLabel();
/* 102 */       this.jlblCompound.setBounds(new Rectangle(12, 8, 138, 16));
/* 103 */       this.jlblCompound.setText("Compound:");
/* 104 */       this.jLabel2 = new JLabel();
/* 105 */       this.jLabel2.setFont(new Font("Dialog", 0, 12));
/* 106 */       this.jLabel2.setBounds(new Rectangle(212, 64, 57, 17));
/* 107 */       this.jLabel2.setText("µM");
/* 108 */       this.jLabel1 = new JLabel();
/* 109 */       this.jLabel1.setText("Concentration:");
/* 110 */       this.jLabel1.setBounds(new Rectangle(12, 64, 105, 17));
/* 111 */       this.jContentPane = new JPanel();
/* 112 */       this.jContentPane.setLayout(null);
/* 113 */       this.jContentPane.add(getJbtnOk(), null);
/* 114 */       this.jContentPane.add(getJbtnCancel(), null);
/* 115 */       this.jContentPane.add(this.jLabel1, null);
/* 116 */       this.jContentPane.add(getJtxtConcentration1(), null);
/* 117 */       this.jContentPane.add(this.jLabel2, null);
       
/* 119 */       this.jContentPane.add(getJcboCompound(), null);
/* 120 */       this.jContentPane.add(this.jlblCompound, null);
     }
/* 122 */     return this.jContentPane;
   }
   
 
 
 
 
 
   private JComboBox getJcboCompound()
   {
/* 132 */     if (this.jcboCompound == null)
     {
/* 134 */       this.jcboCompound = new JComboBox(Globals.CompoundNameArray[this.m_iStationaryPhase]);
/* 135 */       this.jcboCompound.setFont(new Font("Dialog", 0, 12));
/* 136 */       this.jcboCompound.setBounds(new Rectangle(8, 28, 417, 26));
     }
     
/* 139 */     return this.jcboCompound;
   }
   
 
 
 
 
   private JTextField getJtxtConcentration1()
   {
/* 148 */     if (this.jtxtConcentration1 == null) {
/* 149 */       this.jtxtConcentration1 = new JTextField();
/* 150 */       this.jtxtConcentration1.setText("50");
/* 151 */       this.jtxtConcentration1.setBounds(new Rectangle(120, 60, 85, 26));
     }
/* 153 */     return this.jtxtConcentration1;
   }
   
 
 
 
 
   private JButton getJbtnOk()
   {
/* 162 */     if (this.jbtnOk == null) {
/* 163 */       this.jbtnOk = new JButton();
/* 164 */       this.jbtnOk.setBounds(new Rectangle(176, 104, 125, 31));
/* 165 */       this.jbtnOk.setActionCommand("OK");
/* 166 */       this.jbtnOk.setText("OK");
     }
/* 168 */     return this.jbtnOk;
   }
   
 
 
 
 
   private JButton getJbtnCancel()
   {
/* 177 */     if (this.jbtnCancel == null) {
/* 178 */       this.jbtnCancel = new JButton();
/* 179 */       this.jbtnCancel.setBounds(new Rectangle(308, 104, 124, 31));
/* 180 */       this.jbtnCancel.setText("Cancel");
     }
/* 182 */     return this.jbtnCancel;
   }
   
   public void setSelectedCompound(int iCompoundIndex)
   {
/* 187 */     this.jcboCompound.setSelectedIndex(iCompoundIndex);
   }
   
   public void setSelectedStationaryPhase(int iStationaryPhase)
   {
/* 192 */     this.m_iStationaryPhase = iStationaryPhase;
/* 193 */     DefaultComboBoxModel newModel = new DefaultComboBoxModel(Globals.CompoundNameArray[this.m_iStationaryPhase]);
/* 194 */     this.jcboCompound.setModel(newModel);
   }
   
 
   public void setCompoundConcentration(double dConcentration)
   {
/* 200 */     this.m_dConcentration1 = dConcentration;
/* 201 */     this.jtxtConcentration1.setText(Float.toString((float)dConcentration));
   }
   
   private void setCompoundVariables()
   {
/* 206 */     this.m_iCompound = this.jcboCompound.getSelectedIndex();
     
/* 208 */     Compound thisCompound = new Compound();
/* 209 */     thisCompound.loadCompoundInfo(this.m_iStationaryPhase, this.m_iCompound);
     
/* 211 */     this.m_strCompoundName = thisCompound.strCompoundName;
     
/* 213 */     this.InterpolatedLogkvsT = thisCompound.InterpolatedLogkvsT;
     
/* 215 */     NumberFormat formatter = new DecimalFormat("#0.0000");
     
/* 217 */     this.jtxtConcentration1.setText(formatter.format(this.m_dConcentration1));
   }
   
 
   public void actionPerformed(ActionEvent arg0)
   {
/* 223 */     if (arg0.getActionCommand() == "comboBoxChanged")
     {
/* 225 */       setCompoundVariables();
     }
/* 227 */     else if (arg0.getActionCommand() == "OK")
     {
/* 229 */       for (int i = 0; i < this.m_vectCompoundsUsed.size(); i++)
       {
/* 231 */         if (this.m_iCompound == ((Integer)this.m_vectCompoundsUsed.get(i)).intValue())
         {
 
/* 234 */           JOptionPane.showMessageDialog(this, 
/* 235 */             "That compound was already added. Please select a different compound.", 
/* 236 */             "Compound already added", 
/* 237 */             1, 
/* 238 */             null);
/* 239 */           return;
         }
       }
       
/* 243 */       this.m_bOk = true;
/* 244 */       setVisible(false);
/* 245 */       dispose();
     }
/* 247 */     else if (arg0.getActionCommand() == "Cancel")
     {
/* 249 */       setVisible(false);
/* 250 */       dispose();
     }
   }
   
 
 
 
   public void keyPressed(KeyEvent arg0) {}
   
 
 
 
   public void keyReleased(KeyEvent arg0) {}
   
 
 
   public void keyTyped(KeyEvent e)
   {
/* 268 */     if ((!Character.isDigit(e.getKeyChar())) && 
/* 269 */       (e.getKeyChar() != '\b') && 
/* 270 */       (e.getKeyChar() != '') && 
/* 271 */       (e.getKeyChar() != '.'))
     {
/* 273 */       e.consume();
     }
     
/* 276 */     if (e.getKeyChar() == '\n')
     {
/* 278 */       performValidations();
     }
   }
   
 
 
 
   public void focusGained(FocusEvent arg0) {}
   
 
 
 
   public void focusLost(FocusEvent arg0)
   {
/* 292 */     performValidations();
   }
   
   public void performValidations()
   {
/* 297 */     validateConcentration1();
   }
   
   public void validateConcentration1()
   {
/* 302 */     if (this.jtxtConcentration1.getText().length() == 0) {
/* 303 */       this.jtxtConcentration1.setText("0");
     }
/* 305 */     double dTemp = Float.valueOf(this.jtxtConcentration1.getText()).floatValue();
     
/* 307 */     if (dTemp < 1.0E-6D)
/* 308 */       dTemp = 1.0E-6D;
/* 309 */     if (dTemp > 100000.0D) {
/* 310 */       dTemp = 100000.0D;
     }
/* 312 */     this.m_dConcentration1 = dTemp;
/* 313 */     this.jtxtConcentration1.setText(Float.toString((float)this.m_dConcentration1));
   }
   
 
   public void stateChanged(ChangeEvent arg0)
   {
/* 319 */     performValidations();
   }
 }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/ChemicalDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */