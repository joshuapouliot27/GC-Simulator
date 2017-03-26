 package org.gcsimulator;
 
 import java.awt.Frame;
 import java.awt.Rectangle;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.FocusEvent;
 import java.awt.event.FocusListener;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class AboutDialog
   extends JDialog
   implements ActionListener, KeyListener, FocusListener
 {
   private static final long serialVersionUID = 1L;
/*  43 */   private JPanel jContentPane = null;
/*  44 */   private JButton jbtnOk = null;
   
/*  46 */   private JLabel jLabel = null;
   
 
 
 
   public AboutDialog(Frame owner)
   {
/*  53 */     super(owner);
/*  54 */     initialize();
     
/*  56 */     this.jbtnOk.addActionListener(this);
   }
   
 
 
 
 
 
   private void initialize()
   {
/*  66 */     setSize(284, 147);
/*  67 */     setContentPane(getJContentPane());
/*  68 */     setModal(true);
/*  69 */     setResizable(false);
/*  70 */     setTitle("About GC Simulator");
   }
   
 
 
 
 
   private JPanel getJContentPane()
   {
/*  79 */     if (this.jContentPane == null) {
/*  80 */       this.jLabel = new JLabel();
/*  81 */       this.jLabel.setBounds(new Rectangle(76, 32, 121, 16));
/*  82 */       this.jLabel.setHorizontalAlignment(0);
/*  83 */       this.jLabel.setText("Version 1.00");
/*  84 */       this.jContentPane = new JPanel();
/*  85 */       this.jContentPane.setLayout(null);
/*  86 */       this.jContentPane.add(getJbtnOk(), null);
/*  87 */       this.jContentPane.add(this.jLabel, null);
     }
/*  89 */     return this.jContentPane;
   }
   
 
 
 
 
   private JButton getJbtnOk()
   {
/*  98 */     if (this.jbtnOk == null) {
/*  99 */       this.jbtnOk = new JButton();
/* 100 */       this.jbtnOk.setBounds(new Rectangle(76, 80, 125, 31));
/* 101 */       this.jbtnOk.setActionCommand("OK");
      this.jbtnOk.setText("OK");
     }
     return this.jbtnOk;
   }
   
 
   public void actionPerformed(ActionEvent arg0)
   {
     if (arg0.getActionCommand() == "OK")
     {
       setVisible(false);
       dispose();
     }
   }
   
 
 
 
 
 
 
   public void keyPressed(KeyEvent arg0) {}
   
 
 
 
 
 
 
   public void keyReleased(KeyEvent arg0) {}
   
 
 
 
 
 
   public void keyTyped(KeyEvent e)
   {
/* 140 */     e.getKeyChar();
   }
   
   public void focusGained(FocusEvent arg0) {}
   
   public void focusLost(FocusEvent e) {}
 }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/AboutDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */