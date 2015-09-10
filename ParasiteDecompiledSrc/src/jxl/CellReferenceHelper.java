/*   1:    */ package jxl;
/*   2:    */ 
/*   3:    */ import jxl.biff.formula.ExternalSheet;
/*   4:    */ import jxl.write.WritableWorkbook;
/*   5:    */ 
/*   6:    */ public final class CellReferenceHelper
/*   7:    */ {
/*   8:    */   public static void getCellReference(int column, int row, StringBuffer buf)
/*   9:    */   {
/*  10: 46 */     jxl.biff.CellReferenceHelper.getCellReference(column, row, buf);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public static void getCellReference(int column, boolean colabs, int row, boolean rowabs, StringBuffer buf)
/*  14:    */   {
/*  15: 64 */     jxl.biff.CellReferenceHelper.getCellReference(column, colabs, row, rowabs, buf);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static String getCellReference(int column, int row)
/*  19:    */   {
/*  20: 79 */     return jxl.biff.CellReferenceHelper.getCellReference(column, row);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static int getColumn(String s)
/*  24:    */   {
/*  25: 90 */     return jxl.biff.CellReferenceHelper.getColumn(s);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static String getColumnReference(int c)
/*  29:    */   {
/*  30:101 */     return jxl.biff.CellReferenceHelper.getColumnReference(c);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static int getRow(String s)
/*  34:    */   {
/*  35:111 */     return jxl.biff.CellReferenceHelper.getRow(s);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static boolean isColumnRelative(String s)
/*  39:    */   {
/*  40:122 */     return jxl.biff.CellReferenceHelper.isColumnRelative(s);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static boolean isRowRelative(String s)
/*  44:    */   {
/*  45:133 */     return jxl.biff.CellReferenceHelper.isRowRelative(s);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void getCellReference(int sheet, int column, int row, Workbook workbook, StringBuffer buf)
/*  49:    */   {
/*  50:150 */     jxl.biff.CellReferenceHelper.getCellReference(sheet, column, row, (ExternalSheet)workbook, buf);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static void getCellReference(int sheet, int column, int row, WritableWorkbook workbook, StringBuffer buf)
/*  54:    */   {
/*  55:170 */     jxl.biff.CellReferenceHelper.getCellReference(sheet, column, row, (ExternalSheet)workbook, buf);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static void getCellReference(int sheet, int column, boolean colabs, int row, boolean rowabs, Workbook workbook, StringBuffer buf)
/*  59:    */   {
/*  60:194 */     jxl.biff.CellReferenceHelper.getCellReference(sheet, column, colabs, row, rowabs, (ExternalSheet)workbook, buf);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String getCellReference(int sheet, int column, int row, Workbook workbook)
/*  64:    */   {
/*  65:214 */     return jxl.biff.CellReferenceHelper.getCellReference(sheet, column, row, (ExternalSheet)workbook);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static String getCellReference(int sheet, int column, int row, WritableWorkbook workbook)
/*  69:    */   {
/*  70:233 */     return jxl.biff.CellReferenceHelper.getCellReference(sheet, column, row, (ExternalSheet)workbook);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static String getSheet(String ref)
/*  74:    */   {
/*  75:246 */     return jxl.biff.CellReferenceHelper.getSheet(ref);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String getCellReference(Cell c)
/*  79:    */   {
/*  80:256 */     return getCellReference(c.getColumn(), c.getRow());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void getCellReference(Cell c, StringBuffer sb)
/*  84:    */   {
/*  85:267 */     getCellReference(c.getColumn(), c.getRow(), sb);
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.CellReferenceHelper
 * JD-Core Version:    0.7.0.1
 */