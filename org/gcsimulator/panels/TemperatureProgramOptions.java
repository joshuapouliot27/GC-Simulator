/*     */ package org.gcsimulator.panels;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import org.gcsimulator.TemperatureProgramListener;
/*     */ import org.jdesktop.swingx.JXPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TemperatureProgramOptions
/*     */   extends JXPanel
/*     */   implements ActionListener, ListSelectionListener, TableModelListener
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public SpecialTableModel2 tmTemperatureProgram;
/*  35 */   public JButton jbtnInsertRow = null;
/*  36 */   public JButton jbtnAddRow = null;
/*  37 */   public JButton jbtnRemoveRow = null;
/*  38 */   private JLabel jlblInitialTemperature = null;
/*  39 */   public JTextField jtxtInitialTemperature = null;
/*  40 */   private JLabel jlblInitialTemperatureUnit = null;
/*  41 */   private JLabel jlblInitialTime = null;
/*  42 */   public JTextField jtxtInitialTime = null;
/*  43 */   private JLabel jlblInitialTimeUnit = null;
/*  44 */   private JScrollPane jScrollPane = null;
/*  45 */   public JTable jtblTemperatureProgram = null;
/*  46 */   public boolean m_bDoNotChangeTable = false;
/*     */   
/*  48 */   public List<TemperatureProgramListener> listeners = new ArrayList();
/*     */   
/*     */   public void addListener(TemperatureProgramListener toAdd)
/*     */   {
/*  52 */     this.listeners.add(toAdd);
/*     */   }
/*     */   
/*     */   public void sayTemperatureProgramChanged()
/*     */   {
/*  57 */     for (TemperatureProgramListener tpl : this.listeners)
/*     */     {
/*  59 */       tpl.temperatureProgramChanged();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TemperatureProgramOptions()
/*     */   {
/*  70 */     initialize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initialize()
/*     */   {
/*  79 */     this.jlblInitialTimeUnit = new JLabel();
/*  80 */     this.jlblInitialTimeUnit.setBounds(new Rectangle(256, 36, 57, 16));
/*  81 */     this.jlblInitialTimeUnit.setText("min");
/*  82 */     this.jlblInitialTime = new JLabel();
/*  83 */     this.jlblInitialTime.setBounds(new Rectangle(8, 36, 181, 16));
/*  84 */     this.jlblInitialTime.setText("Initial time:");
/*  85 */     this.jlblInitialTime.setDisplayedMnemonic(0);
/*  86 */     this.jlblInitialTemperatureUnit = new JLabel();
/*  87 */     this.jlblInitialTemperatureUnit.setBounds(new Rectangle(256, 12, 57, 16));
/*  88 */     this.jlblInitialTemperatureUnit.setText("°C");
/*  89 */     this.jlblInitialTemperature = new JLabel();
/*  90 */     this.jlblInitialTemperature.setBounds(new Rectangle(8, 12, 181, 16));
/*  91 */     this.jlblInitialTemperature.setText("Initial oven temperature:");
/*     */     
/*  93 */     setLayout(null);
/*  94 */     setBounds(new Rectangle(0, 0, 314, 220));
/*  95 */     setPreferredSize(getSize());
/*  96 */     add(getJbtnInsertRow(), null);
/*  97 */     add(getJbtnRemoveRow(), null);
/*  98 */     add(this.jlblInitialTemperature, null);
/*  99 */     add(getJtxtInitialTemperature(), null);
/* 100 */     add(this.jlblInitialTemperatureUnit, null);
/* 101 */     add(this.jlblInitialTime, null);
/* 102 */     add(getJtxtInitialTime(), null);
/* 103 */     add(this.jlblInitialTimeUnit, null);
/* 104 */     add(getJScrollPane(), null);
/* 105 */     add(getJbtnAddRow(), null);
/*     */     
/* 107 */     this.jbtnInsertRow.addActionListener(this);
/* 108 */     this.jbtnRemoveRow.addActionListener(this);
/* 109 */     this.jbtnAddRow.addActionListener(this);
/* 110 */     this.jtblTemperatureProgram.getSelectionModel().addListSelectionListener(this);
/* 111 */     this.tmTemperatureProgram.addTableModelListener(this);
/*     */   }
/*     */   
/*     */ 
/*     */   public class SpecialTableModel
/*     */     extends DefaultTableModel
/*     */   {
/*     */     private static final long serialVersionUID = 9144486981092084762L;
/*     */     
/*     */ 
/*     */     public SpecialTableModel(Object[] columnNames, int rowCount)
/*     */     {
/* 123 */       super(rowCount);
/*     */     }
/*     */     
/*     */     public SpecialTableModel(Object[][] data, Object[] columnNames)
/*     */     {
/* 128 */       setDataVector(data, columnNames);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isCellEditable(int row, int column)
/*     */     {
/* 140 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 151 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */   }
/*     */   
/*     */   public class SpecialTableModel2
/*     */     extends DefaultTableModel
/*     */   {
/*     */     public SpecialTableModel2(Object[] columnNames, int rowCount)
/*     */     {
/* 160 */       super(rowCount);
/*     */     }
/*     */     
/*     */     public SpecialTableModel2(Object[][] data, Object[] columnNames)
/*     */     {
/* 165 */       setDataVector(data, columnNames);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isCellEditable(int row, int column)
/*     */     {
/* 177 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 187 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTable getJtblTemperatureProgram()
/*     */   {
/* 198 */     if (this.jtblTemperatureProgram == null)
/*     */     {
/* 200 */       Object[] columnNames = { "Ramp (°C/min)", "Final temp (°C)", "Hold time (min)" };
/* 201 */       Double[][] data = { { Double.valueOf(20.0D), Double.valueOf(260.0D), Double.valueOf(5.0D) } };
/*     */       
/* 203 */       this.tmTemperatureProgram = new SpecialTableModel2(data, columnNames);
/*     */       
/* 205 */       this.jtblTemperatureProgram = new JTable(this.tmTemperatureProgram);
/*     */       
/* 207 */       this.jtblTemperatureProgram.setSelectionMode(0);
/*     */       
/* 209 */       this.jtblTemperatureProgram.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
/*     */       
/* 211 */       this.jtblTemperatureProgram.getTableHeader().setPreferredSize(new Dimension(22, 22));
/* 212 */       this.jtblTemperatureProgram.getColumnModel().getColumn(0).setPreferredWidth(85);
/*     */     }
/*     */     
/* 215 */     return this.jtblTemperatureProgram;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnInsertRow()
/*     */   {
/* 224 */     if (this.jbtnInsertRow == null) {
/* 225 */       this.jbtnInsertRow = new JButton();
/* 226 */       this.jbtnInsertRow.setActionCommand("Insert Row");
/* 227 */       this.jbtnInsertRow.setLocation(new Point(8, 184));
/* 228 */       this.jbtnInsertRow.setSize(new Dimension(94, 29));
/* 229 */       this.jbtnInsertRow.setEnabled(false);
/* 230 */       this.jbtnInsertRow.setText("Insert");
/*     */     }
/* 232 */     return this.jbtnInsertRow;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnRemoveRow()
/*     */   {
/* 241 */     if (this.jbtnRemoveRow == null) {
/* 242 */       this.jbtnRemoveRow = new JButton();
/* 243 */       this.jbtnRemoveRow.setActionCommand("Remove Row");
/* 244 */       this.jbtnRemoveRow.setLocation(new Point(208, 184));
/* 245 */       this.jbtnRemoveRow.setSize(new Dimension(94, 29));
/* 246 */       this.jbtnRemoveRow.setEnabled(false);
/* 247 */       this.jbtnRemoveRow.setText("Remove");
/*     */     }
/* 249 */     return this.jbtnRemoveRow;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtInitialTemperature()
/*     */   {
/* 258 */     if (this.jtxtInitialTemperature == null) {
/* 259 */       this.jtxtInitialTemperature = new JTextField();
/* 260 */       this.jtxtInitialTemperature.setBounds(new Rectangle(192, 8, 61, 26));
/* 261 */       this.jtxtInitialTemperature.setText("60.0");
/*     */     }
/* 263 */     return this.jtxtInitialTemperature;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTextField getJtxtInitialTime()
/*     */   {
/* 272 */     if (this.jtxtInitialTime == null) {
/* 273 */       this.jtxtInitialTime = new JTextField();
/* 274 */       this.jtxtInitialTime.setBounds(new Rectangle(192, 32, 61, 26));
/* 275 */       this.jtxtInitialTime.setText("5");
/*     */     }
/* 277 */     return this.jtxtInitialTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JScrollPane getJScrollPane()
/*     */   {
/* 286 */     if (this.jScrollPane == null) {
/* 287 */       this.jScrollPane = new JScrollPane();
/* 288 */       this.jScrollPane.setBounds(new Rectangle(8, 64, 297, 117));
/* 289 */       this.jScrollPane.setViewportView(getJtblTemperatureProgram());
/*     */     }
/* 291 */     return this.jScrollPane;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JButton getJbtnAddRow()
/*     */   {
/* 300 */     if (this.jbtnAddRow == null) {
/* 301 */       this.jbtnAddRow = new JButton();
/* 302 */       this.jbtnAddRow.setText("Add");
/* 303 */       this.jbtnAddRow.setLocation(new Point(108, 184));
/* 304 */       this.jbtnAddRow.setSize(new Dimension(94, 28));
/* 305 */       this.jbtnAddRow.setActionCommand("Add Row");
/*     */     }
/* 307 */     return this.jbtnAddRow;
/*     */   }
/*     */   
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 313 */     if (e.getActionCommand() == "Add Row")
/*     */     {
/* 315 */       int lastRowIndex = this.jtblTemperatureProgram.getRowCount() - 1;
/*     */       Double dRowValue3;
/*     */       Double dRowValue1;
/*     */       Double dRowValue2;
/* 319 */       Double dRowValue3; if (lastRowIndex == -1)
/*     */       {
/* 321 */         Double dRowValue1 = Double.valueOf(10.0D);
/* 322 */         Double dRowValue2 = Double.valueOf(Float.valueOf(this.jtxtInitialTemperature.getText()).doubleValue());
/* 323 */         dRowValue3 = Double.valueOf(5.0D);
/*     */       }
/*     */       else
/*     */       {
/* 327 */         dRowValue1 = Double.valueOf(10.0D);
/* 328 */         dRowValue2 = (Double)this.tmTemperatureProgram.getValueAt(lastRowIndex, 1);
/* 329 */         dRowValue3 = Double.valueOf(5.0D);
/*     */       }
/*     */       
/* 332 */       Double[] dRowData = { dRowValue1, dRowValue2, dRowValue3 };
/* 333 */       this.tmTemperatureProgram.addRow(dRowData);
/*     */     }
/* 335 */     else if (e.getActionCommand() == "Insert Row")
/*     */     {
/* 337 */       int activatedRowIndex = this.jtblTemperatureProgram.getSelectedRow();
/* 338 */       if (activatedRowIndex < 0) {
/* 339 */         return;
/*     */       }
/*     */       
/*     */ 
/* 343 */       Double dRowValue1 = (Double)this.tmTemperatureProgram.getValueAt(activatedRowIndex, 0);
/* 344 */       Double dRowValue2 = (Double)this.tmTemperatureProgram.getValueAt(activatedRowIndex, 1);
/* 345 */       Double dRowValue3 = (Double)this.tmTemperatureProgram.getValueAt(activatedRowIndex, 2);
/*     */       
/* 347 */       Double[] dRowData = { dRowValue1, dRowValue2, dRowValue3 };
/* 348 */       this.tmTemperatureProgram.insertRow(activatedRowIndex, dRowData);
/*     */     }
/* 350 */     else if (e.getActionCommand() == "Remove Row")
/*     */     {
/* 352 */       int activatedRowIndex = this.jtblTemperatureProgram.getSelectedRow();
/* 353 */       if (activatedRowIndex < 0) {
/* 354 */         return;
/*     */       }
/* 356 */       this.tmTemperatureProgram.removeRow(activatedRowIndex);
/*     */       
/* 358 */       this.jbtnInsertRow.setEnabled(false);
/* 359 */       this.jbtnRemoveRow.setEnabled(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void valueChanged(ListSelectionEvent e)
/*     */   {
/* 366 */     if (e.getSource() == this.jtblTemperatureProgram.getSelectionModel())
/*     */     {
/* 368 */       int iSelectedRow = this.jtblTemperatureProgram.getSelectedRow();
/* 369 */       if (iSelectedRow >= 0)
/*     */       {
/* 371 */         this.jbtnRemoveRow.setEnabled(true);
/* 372 */         this.jbtnInsertRow.setEnabled(true);
/*     */       }
/*     */       else
/*     */       {
/* 376 */         this.jbtnRemoveRow.setEnabled(false);
/* 377 */         this.jbtnInsertRow.setEnabled(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void tableChanged(TableModelEvent e)
/*     */   {
/* 385 */     if (this.m_bDoNotChangeTable)
/*     */     {
/* 387 */       this.m_bDoNotChangeTable = false;
/* 388 */       return;
/*     */     }
/*     */     
/* 391 */     if (e.getSource() == this.tmTemperatureProgram)
/*     */     {
/* 393 */       int row = e.getFirstRow();
/* 394 */       int column = e.getColumn();
/*     */       
/* 396 */       if (column == 0)
/*     */       {
/* 398 */         Double dNewValue = (Double)this.tmTemperatureProgram.getValueAt(row, 0);
/*     */         
/* 400 */         double dTemp = dNewValue.doubleValue();
/* 401 */         if (dTemp < 0.0D)
/* 402 */           dTemp = 0.0D;
/* 403 */         if (dTemp > 1000.0D) {
/* 404 */           dTemp = 1000.0D;
/*     */         }
/* 406 */         this.m_bDoNotChangeTable = true;
/* 407 */         this.tmTemperatureProgram.setValueAt(Double.valueOf(dTemp), row, column);
/*     */       }
/* 409 */       else if (column == 1)
/*     */       {
/* 411 */         Double dNewValue = (Double)this.tmTemperatureProgram.getValueAt(row, 1);
/*     */         
/* 413 */         double dTemp = dNewValue.doubleValue();
/* 414 */         if (dTemp < 60.0D)
/* 415 */           dTemp = 60.0D;
/* 416 */         if (dTemp > 320.0D)
/* 417 */           dTemp = 320.0D;
/* 418 */         if (row == 0)
/*     */         {
/* 420 */           if (dTemp < Double.valueOf(Float.valueOf(this.jtxtInitialTemperature.getText()).doubleValue()).doubleValue())
/*     */           {
/* 422 */             dTemp = Float.valueOf(this.jtxtInitialTemperature.getText()).doubleValue();
/*     */           }
/*     */         }
/* 425 */         if (row < this.tmTemperatureProgram.getRowCount() - 1)
/*     */         {
/* 427 */           if (dTemp > ((Double)this.tmTemperatureProgram.getValueAt(row + 1, column)).doubleValue())
/*     */           {
/* 429 */             dTemp = ((Double)this.tmTemperatureProgram.getValueAt(row + 1, column)).doubleValue();
/*     */           }
/*     */         }
/* 432 */         if (row > 0)
/*     */         {
/* 434 */           if (dTemp < ((Double)this.tmTemperatureProgram.getValueAt(row - 1, column)).doubleValue())
/*     */           {
/* 436 */             dTemp = ((Double)this.tmTemperatureProgram.getValueAt(row - 1, column)).doubleValue();
/*     */           }
/*     */         }
/* 439 */         this.m_bDoNotChangeTable = true;
/* 440 */         this.tmTemperatureProgram.setValueAt(Double.valueOf(dTemp), row, column);
/*     */       }
/* 442 */       else if (column == 2)
/*     */       {
/* 444 */         Double dNewValue = (Double)this.tmTemperatureProgram.getValueAt(row, 2);
/*     */         
/* 446 */         double dTemp = dNewValue.doubleValue();
/* 447 */         if (dTemp < 0.0D)
/* 448 */           dTemp = 0.0D;
/* 449 */         if (dTemp > 1000.0D) {
/* 450 */           dTemp = 1000.0D;
/*     */         }
/* 452 */         this.m_bDoNotChangeTable = true;
/* 453 */         this.tmTemperatureProgram.setValueAt(Double.valueOf(dTemp), row, column);
/*     */       }
/*     */       
/* 456 */       sayTemperatureProgramChanged();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/joshua/Downloads/GCSimulator.jar!/org/gcsimulator/panels/TemperatureProgramOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */