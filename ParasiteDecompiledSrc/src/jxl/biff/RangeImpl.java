/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.Cell;
/*   4:    */ import jxl.Range;
/*   5:    */ import jxl.Sheet;
/*   6:    */ import jxl.common.Logger;
/*   7:    */ 
/*   8:    */ public class RangeImpl
/*   9:    */   implements Range
/*  10:    */ {
/*  11: 40 */   private static Logger logger = Logger.getLogger(RangeImpl.class);
/*  12:    */   private WorkbookMethods workbook;
/*  13:    */   private int sheet1;
/*  14:    */   private int column1;
/*  15:    */   private int row1;
/*  16:    */   private int sheet2;
/*  17:    */   private int column2;
/*  18:    */   private int row2;
/*  19:    */   
/*  20:    */   public RangeImpl(WorkbookMethods w, int s1, int c1, int r1, int s2, int c2, int r2)
/*  21:    */   {
/*  22: 92 */     this.workbook = w;
/*  23: 93 */     this.sheet1 = s1;
/*  24: 94 */     this.sheet2 = s2;
/*  25: 95 */     this.row1 = r1;
/*  26: 96 */     this.row2 = r2;
/*  27: 97 */     this.column1 = c1;
/*  28: 98 */     this.column2 = c2;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Cell getTopLeft()
/*  32:    */   {
/*  33:108 */     Sheet s = this.workbook.getReadSheet(this.sheet1);
/*  34:110 */     if ((this.column1 < s.getColumns()) && (this.row1 < s.getRows())) {
/*  35:113 */       return s.getCell(this.column1, this.row1);
/*  36:    */     }
/*  37:117 */     return new EmptyCell(this.column1, this.row1);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Cell getBottomRight()
/*  41:    */   {
/*  42:128 */     Sheet s = this.workbook.getReadSheet(this.sheet2);
/*  43:130 */     if ((this.column2 < s.getColumns()) && (this.row2 < s.getRows())) {
/*  44:133 */       return s.getCell(this.column2, this.row2);
/*  45:    */     }
/*  46:137 */     return new EmptyCell(this.column2, this.row2);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getFirstSheetIndex()
/*  50:    */   {
/*  51:148 */     return this.sheet1;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getLastSheetIndex()
/*  55:    */   {
/*  56:158 */     return this.sheet2;
/*  57:    */   }
/*  58:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.RangeImpl
 * JD-Core Version:    0.7.0.1
 */