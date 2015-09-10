/*  1:   */ package org.apache.log4j.lf5.viewer;
/*  2:   */ 
/*  3:   */ import javax.swing.table.DefaultTableModel;
/*  4:   */ 
/*  5:   */ public class LogTableModel
/*  6:   */   extends DefaultTableModel
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 3593300685868700894L;
/*  9:   */   
/* 10:   */   public LogTableModel(Object[] colNames, int numRows)
/* 11:   */   {
/* 12:49 */     super(colNames, numRows);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isCellEditable(int row, int column)
/* 16:   */   {
/* 17:57 */     return false;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogTableModel
 * JD-Core Version:    0.7.0.1
 */