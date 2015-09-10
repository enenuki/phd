/*  1:   */ package jxl.biff.formula;
/*  2:   */ 
/*  3:   */ import jxl.biff.CellReferenceHelper;
/*  4:   */ import jxl.common.Assert;
/*  5:   */ import jxl.common.Logger;
/*  6:   */ 
/*  7:   */ class ColumnRange
/*  8:   */   extends Area
/*  9:   */ {
/* 10:35 */   private static Logger logger = Logger.getLogger(ColumnRange.class);
/* 11:   */   
/* 12:   */   ColumnRange() {}
/* 13:   */   
/* 14:   */   ColumnRange(String s)
/* 15:   */   {
/* 16:52 */     int seppos = s.indexOf(":");
/* 17:53 */     Assert.verify(seppos != -1);
/* 18:54 */     String startcell = s.substring(0, seppos);
/* 19:55 */     String endcell = s.substring(seppos + 1);
/* 20:   */     
/* 21:57 */     int columnFirst = CellReferenceHelper.getColumn(startcell);
/* 22:58 */     int rowFirst = 0;
/* 23:59 */     int columnLast = CellReferenceHelper.getColumn(endcell);
/* 24:60 */     int rowLast = 65535;
/* 25:   */     
/* 26:62 */     boolean columnFirstRelative = CellReferenceHelper.isColumnRelative(startcell);
/* 27:   */     
/* 28:64 */     boolean rowFirstRelative = false;
/* 29:65 */     boolean columnLastRelative = CellReferenceHelper.isColumnRelative(endcell);
/* 30:66 */     boolean rowLastRelative = false;
/* 31:   */     
/* 32:68 */     setRangeData(columnFirst, columnLast, rowFirst, rowLast, columnFirstRelative, columnLastRelative, rowFirstRelative, rowLastRelative);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void getString(StringBuffer buf)
/* 36:   */   {
/* 37:81 */     CellReferenceHelper.getColumnReference(getFirstColumn(), buf);
/* 38:82 */     buf.append(':');
/* 39:83 */     CellReferenceHelper.getColumnReference(getLastColumn(), buf);
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.ColumnRange
 * JD-Core Version:    0.7.0.1
 */