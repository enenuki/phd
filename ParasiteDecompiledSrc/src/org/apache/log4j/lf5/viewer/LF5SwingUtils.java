/*   1:    */ package org.apache.log4j.lf5.viewer;
/*   2:    */ 
/*   3:    */ import java.awt.Adjustable;
/*   4:    */ import java.awt.Component;
/*   5:    */ import javax.swing.JComponent;
/*   6:    */ import javax.swing.JScrollPane;
/*   7:    */ import javax.swing.JTable;
/*   8:    */ import javax.swing.ListSelectionModel;
/*   9:    */ import javax.swing.SwingUtilities;
/*  10:    */ import javax.swing.table.TableModel;
/*  11:    */ 
/*  12:    */ public class LF5SwingUtils
/*  13:    */ {
/*  14:    */   public static void selectRow(int row, JTable table, JScrollPane pane)
/*  15:    */   {
/*  16: 67 */     if ((table == null) || (pane == null)) {
/*  17: 68 */       return;
/*  18:    */     }
/*  19: 70 */     if (!contains(row, table.getModel())) {
/*  20: 71 */       return;
/*  21:    */     }
/*  22: 73 */     moveAdjustable(row * table.getRowHeight(), pane.getVerticalScrollBar());
/*  23: 74 */     selectRow(row, table.getSelectionModel());
/*  24:    */     
/*  25:    */ 
/*  26:    */ 
/*  27: 78 */     repaintLater(table);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void makeScrollBarTrack(Adjustable scrollBar)
/*  31:    */   {
/*  32: 86 */     if (scrollBar == null) {
/*  33: 87 */       return;
/*  34:    */     }
/*  35: 89 */     scrollBar.addAdjustmentListener(new TrackingAdjustmentListener());
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static void makeVerticalScrollBarTrack(JScrollPane pane)
/*  39:    */   {
/*  40: 98 */     if (pane == null) {
/*  41: 99 */       return;
/*  42:    */     }
/*  43:101 */     makeScrollBarTrack(pane.getVerticalScrollBar());
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected static boolean contains(int row, TableModel model)
/*  47:    */   {
/*  48:108 */     if (model == null) {
/*  49:109 */       return false;
/*  50:    */     }
/*  51:111 */     if (row < 0) {
/*  52:112 */       return false;
/*  53:    */     }
/*  54:114 */     if (row >= model.getRowCount()) {
/*  55:115 */       return false;
/*  56:    */     }
/*  57:117 */     return true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected static void selectRow(int row, ListSelectionModel model)
/*  61:    */   {
/*  62:121 */     if (model == null) {
/*  63:122 */       return;
/*  64:    */     }
/*  65:124 */     model.setSelectionInterval(row, row);
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected static void moveAdjustable(int location, Adjustable scrollBar)
/*  69:    */   {
/*  70:128 */     if (scrollBar == null) {
/*  71:129 */       return;
/*  72:    */     }
/*  73:131 */     scrollBar.setValue(location);
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected static void repaintLater(JComponent component)
/*  77:    */   {
/*  78:139 */     SwingUtilities.invokeLater(new Runnable()
/*  79:    */     {
/*  80:    */       private final JComponent val$component;
/*  81:    */       
/*  82:    */       public void run()
/*  83:    */       {
/*  84:141 */         this.val$component.repaint();
/*  85:    */       }
/*  86:    */     });
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LF5SwingUtils
 * JD-Core Version:    0.7.0.1
 */