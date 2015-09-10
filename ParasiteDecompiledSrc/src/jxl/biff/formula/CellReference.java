/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import jxl.Cell;
/*   4:    */ import jxl.biff.CellReferenceHelper;
/*   5:    */ import jxl.biff.IntegerHelper;
/*   6:    */ import jxl.common.Logger;
/*   7:    */ 
/*   8:    */ class CellReference
/*   9:    */   extends Operand
/*  10:    */   implements ParsedThing
/*  11:    */ {
/*  12: 36 */   private static Logger logger = Logger.getLogger(CellReference.class);
/*  13:    */   private boolean columnRelative;
/*  14:    */   private boolean rowRelative;
/*  15:    */   private int column;
/*  16:    */   private int row;
/*  17:    */   private Cell relativeTo;
/*  18:    */   
/*  19:    */   public CellReference(Cell rt)
/*  20:    */   {
/*  21: 71 */     this.relativeTo = rt;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public CellReference() {}
/*  25:    */   
/*  26:    */   public CellReference(String s)
/*  27:    */   {
/*  28: 88 */     this.column = CellReferenceHelper.getColumn(s);
/*  29: 89 */     this.row = CellReferenceHelper.getRow(s);
/*  30: 90 */     this.columnRelative = CellReferenceHelper.isColumnRelative(s);
/*  31: 91 */     this.rowRelative = CellReferenceHelper.isRowRelative(s);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int read(byte[] data, int pos)
/*  35:    */   {
/*  36:103 */     this.row = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  37:104 */     int columnMask = IntegerHelper.getInt(data[(pos + 2)], data[(pos + 3)]);
/*  38:105 */     this.column = (columnMask & 0xFF);
/*  39:106 */     this.columnRelative = ((columnMask & 0x4000) != 0);
/*  40:107 */     this.rowRelative = ((columnMask & 0x8000) != 0);
/*  41:    */     
/*  42:109 */     return 4;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getColumn()
/*  46:    */   {
/*  47:119 */     return this.column;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getRow()
/*  51:    */   {
/*  52:129 */     return this.row;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void getString(StringBuffer buf)
/*  56:    */   {
/*  57:139 */     CellReferenceHelper.getCellReference(this.column, !this.columnRelative, this.row, !this.rowRelative, buf);
/*  58:    */   }
/*  59:    */   
/*  60:    */   byte[] getBytes()
/*  61:    */   {
/*  62:151 */     byte[] data = new byte[5];
/*  63:152 */     data[0] = (!useAlternateCode() ? Token.REF.getCode() : Token.REF.getCode2());
/*  64:    */     
/*  65:    */ 
/*  66:155 */     IntegerHelper.getTwoBytes(this.row, data, 1);
/*  67:    */     
/*  68:157 */     int grcol = this.column;
/*  69:160 */     if (this.rowRelative) {
/*  70:162 */       grcol |= 0x8000;
/*  71:    */     }
/*  72:165 */     if (this.columnRelative) {
/*  73:167 */       grcol |= 0x4000;
/*  74:    */     }
/*  75:170 */     IntegerHelper.getTwoBytes(grcol, data, 3);
/*  76:    */     
/*  77:172 */     return data;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void adjustRelativeCellReferences(int colAdjust, int rowAdjust)
/*  81:    */   {
/*  82:184 */     if (this.columnRelative) {
/*  83:186 */       this.column += colAdjust;
/*  84:    */     }
/*  85:189 */     if (this.rowRelative) {
/*  86:191 */       this.row += rowAdjust;
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void columnInserted(int sheetIndex, int col, boolean currentSheet)
/*  91:    */   {
/*  92:207 */     if (!currentSheet) {
/*  93:209 */       return;
/*  94:    */     }
/*  95:212 */     if (this.column >= col) {
/*  96:214 */       this.column += 1;
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   void columnRemoved(int sheetIndex, int col, boolean currentSheet)
/* 101:    */   {
/* 102:230 */     if (!currentSheet) {
/* 103:232 */       return;
/* 104:    */     }
/* 105:235 */     if (this.column >= col) {
/* 106:237 */       this.column -= 1;
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   void rowInserted(int sheetIndex, int r, boolean currentSheet)
/* 111:    */   {
/* 112:253 */     if (!currentSheet) {
/* 113:255 */       return;
/* 114:    */     }
/* 115:258 */     if (this.row >= r) {
/* 116:260 */       this.row += 1;
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   void rowRemoved(int sheetIndex, int r, boolean currentSheet)
/* 121:    */   {
/* 122:276 */     if (!currentSheet) {
/* 123:278 */       return;
/* 124:    */     }
/* 125:281 */     if (this.row >= r) {
/* 126:283 */       this.row -= 1;
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   void handleImportedCellReferences() {}
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.CellReference
 * JD-Core Version:    0.7.0.1
 */