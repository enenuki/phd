/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import jxl.biff.CellReferenceHelper;
/*   4:    */ import jxl.common.Assert;
/*   5:    */ import jxl.common.Logger;
/*   6:    */ 
/*   7:    */ class ColumnRange3d
/*   8:    */   extends Area3d
/*   9:    */ {
/*  10: 35 */   private static Logger logger = Logger.getLogger(ColumnRange3d.class);
/*  11:    */   private ExternalSheet workbook;
/*  12:    */   private int sheet;
/*  13:    */   
/*  14:    */   ColumnRange3d(ExternalSheet es)
/*  15:    */   {
/*  16: 54 */     super(es);
/*  17: 55 */     this.workbook = es;
/*  18:    */   }
/*  19:    */   
/*  20:    */   ColumnRange3d(String s, ExternalSheet es)
/*  21:    */     throws FormulaException
/*  22:    */   {
/*  23: 67 */     super(es);
/*  24: 68 */     this.workbook = es;
/*  25: 69 */     int seppos = s.lastIndexOf(":");
/*  26: 70 */     Assert.verify(seppos != -1);
/*  27: 71 */     String startcell = s.substring(0, seppos);
/*  28: 72 */     String endcell = s.substring(seppos + 1);
/*  29:    */     
/*  30:    */ 
/*  31: 75 */     int sep = s.indexOf('!');
/*  32: 76 */     String cellString = s.substring(sep + 1, seppos);
/*  33: 77 */     int columnFirst = CellReferenceHelper.getColumn(cellString);
/*  34: 78 */     int rowFirst = 0;
/*  35:    */     
/*  36:    */ 
/*  37: 81 */     String sheetName = s.substring(0, sep);
/*  38: 82 */     int sheetNamePos = sheetName.lastIndexOf(']');
/*  39: 85 */     if ((sheetName.charAt(0) == '\'') && (sheetName.charAt(sheetName.length() - 1) == '\'')) {
/*  40: 88 */       sheetName = sheetName.substring(1, sheetName.length() - 1);
/*  41:    */     }
/*  42: 91 */     this.sheet = es.getExternalSheetIndex(sheetName);
/*  43: 93 */     if (this.sheet < 0) {
/*  44: 95 */       throw new FormulaException(FormulaException.SHEET_REF_NOT_FOUND, sheetName);
/*  45:    */     }
/*  46:100 */     int columnLast = CellReferenceHelper.getColumn(endcell);
/*  47:101 */     int rowLast = 65535;
/*  48:    */     
/*  49:103 */     boolean columnFirstRelative = true;
/*  50:104 */     boolean rowFirstRelative = true;
/*  51:105 */     boolean columnLastRelative = true;
/*  52:106 */     boolean rowLastRelative = true;
/*  53:    */     
/*  54:108 */     setRangeData(this.sheet, columnFirst, columnLast, rowFirst, rowLast, columnFirstRelative, rowFirstRelative, columnLastRelative, rowLastRelative);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void getString(StringBuffer buf)
/*  58:    */   {
/*  59:120 */     buf.append('\'');
/*  60:121 */     buf.append(this.workbook.getExternalSheetName(this.sheet));
/*  61:122 */     buf.append('\'');
/*  62:123 */     buf.append('!');
/*  63:    */     
/*  64:125 */     CellReferenceHelper.getColumnReference(getFirstColumn(), buf);
/*  65:126 */     buf.append(':');
/*  66:127 */     CellReferenceHelper.getColumnReference(getLastColumn(), buf);
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.ColumnRange3d
 * JD-Core Version:    0.7.0.1
 */