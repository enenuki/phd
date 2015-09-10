/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import jxl.Cell;
/*   4:    */ import jxl.biff.CellReferenceHelper;
/*   5:    */ import jxl.biff.IntegerHelper;
/*   6:    */ import jxl.common.Logger;
/*   7:    */ 
/*   8:    */ class CellReference3d
/*   9:    */   extends Operand
/*  10:    */   implements ParsedThing
/*  11:    */ {
/*  12: 37 */   private static Logger logger = Logger.getLogger(CellReference3d.class);
/*  13:    */   private boolean columnRelative;
/*  14:    */   private boolean rowRelative;
/*  15:    */   private int column;
/*  16:    */   private int row;
/*  17:    */   private Cell relativeTo;
/*  18:    */   private int sheet;
/*  19:    */   private ExternalSheet workbook;
/*  20:    */   
/*  21:    */   public CellReference3d(Cell rt, ExternalSheet w)
/*  22:    */   {
/*  23: 83 */     this.relativeTo = rt;
/*  24: 84 */     this.workbook = w;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public CellReference3d(String s, ExternalSheet w)
/*  28:    */     throws FormulaException
/*  29:    */   {
/*  30: 96 */     this.workbook = w;
/*  31: 97 */     this.columnRelative = true;
/*  32: 98 */     this.rowRelative = true;
/*  33:    */     
/*  34:    */ 
/*  35:101 */     int sep = s.indexOf('!');
/*  36:102 */     String cellString = s.substring(sep + 1);
/*  37:103 */     this.column = CellReferenceHelper.getColumn(cellString);
/*  38:104 */     this.row = CellReferenceHelper.getRow(cellString);
/*  39:    */     
/*  40:    */ 
/*  41:107 */     String sheetName = s.substring(0, sep);
/*  42:110 */     if ((sheetName.charAt(0) == '\'') && (sheetName.charAt(sheetName.length() - 1) == '\'')) {
/*  43:113 */       sheetName = sheetName.substring(1, sheetName.length() - 1);
/*  44:    */     }
/*  45:115 */     this.sheet = w.getExternalSheetIndex(sheetName);
/*  46:117 */     if (this.sheet < 0) {
/*  47:119 */       throw new FormulaException(FormulaException.SHEET_REF_NOT_FOUND, sheetName);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int read(byte[] data, int pos)
/*  52:    */   {
/*  53:133 */     this.sheet = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  54:134 */     this.row = IntegerHelper.getInt(data[(pos + 2)], data[(pos + 3)]);
/*  55:135 */     int columnMask = IntegerHelper.getInt(data[(pos + 4)], data[(pos + 5)]);
/*  56:136 */     this.column = (columnMask & 0xFF);
/*  57:137 */     this.columnRelative = ((columnMask & 0x4000) != 0);
/*  58:138 */     this.rowRelative = ((columnMask & 0x8000) != 0);
/*  59:    */     
/*  60:140 */     return 6;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getColumn()
/*  64:    */   {
/*  65:150 */     return this.column;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getRow()
/*  69:    */   {
/*  70:160 */     return this.row;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void getString(StringBuffer buf)
/*  74:    */   {
/*  75:170 */     CellReferenceHelper.getCellReference(this.sheet, this.column, !this.columnRelative, this.row, !this.rowRelative, this.workbook, buf);
/*  76:    */   }
/*  77:    */   
/*  78:    */   byte[] getBytes()
/*  79:    */   {
/*  80:182 */     byte[] data = new byte[7];
/*  81:183 */     data[0] = Token.REF3D.getCode();
/*  82:    */     
/*  83:185 */     IntegerHelper.getTwoBytes(this.sheet, data, 1);
/*  84:186 */     IntegerHelper.getTwoBytes(this.row, data, 3);
/*  85:    */     
/*  86:188 */     int grcol = this.column;
/*  87:191 */     if (this.rowRelative) {
/*  88:193 */       grcol |= 0x8000;
/*  89:    */     }
/*  90:196 */     if (this.columnRelative) {
/*  91:198 */       grcol |= 0x4000;
/*  92:    */     }
/*  93:201 */     IntegerHelper.getTwoBytes(grcol, data, 5);
/*  94:    */     
/*  95:203 */     return data;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void adjustRelativeCellReferences(int colAdjust, int rowAdjust)
/*  99:    */   {
/* 100:215 */     if (this.columnRelative) {
/* 101:217 */       this.column += colAdjust;
/* 102:    */     }
/* 103:220 */     if (this.rowRelative) {
/* 104:222 */       this.row += rowAdjust;
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void columnInserted(int sheetIndex, int col, boolean currentSheet)
/* 109:    */   {
/* 110:238 */     if (sheetIndex != this.sheet) {
/* 111:240 */       return;
/* 112:    */     }
/* 113:243 */     if (this.column >= col) {
/* 114:245 */       this.column += 1;
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   void columnRemoved(int sheetIndex, int col, boolean currentSheet)
/* 119:    */   {
/* 120:262 */     if (sheetIndex != this.sheet) {
/* 121:264 */       return;
/* 122:    */     }
/* 123:267 */     if (this.column >= col) {
/* 124:269 */       this.column -= 1;
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   void rowInserted(int sheetIndex, int r, boolean currentSheet)
/* 129:    */   {
/* 130:285 */     if (sheetIndex != this.sheet) {
/* 131:287 */       return;
/* 132:    */     }
/* 133:290 */     if (this.row >= r) {
/* 134:292 */       this.row += 1;
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   void rowRemoved(int sheetIndex, int r, boolean currentSheet)
/* 139:    */   {
/* 140:308 */     if (sheetIndex != this.sheet) {
/* 141:310 */       return;
/* 142:    */     }
/* 143:313 */     if (this.row >= r) {
/* 144:315 */       this.row -= 1;
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   void handleImportedCellReferences()
/* 149:    */   {
/* 150:326 */     setInvalid();
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.CellReference3d
 * JD-Core Version:    0.7.0.1
 */