/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import jxl.biff.CellReferenceHelper;
/*   4:    */ import jxl.biff.IntegerHelper;
/*   5:    */ import jxl.common.Assert;
/*   6:    */ import jxl.common.Logger;
/*   7:    */ 
/*   8:    */ class Area3d
/*   9:    */   extends Operand
/*  10:    */   implements ParsedThing
/*  11:    */ {
/*  12: 36 */   private static Logger logger = Logger.getLogger(Area3d.class);
/*  13:    */   private int sheet;
/*  14:    */   private int columnFirst;
/*  15:    */   private int rowFirst;
/*  16:    */   private int columnLast;
/*  17:    */   private int rowLast;
/*  18:    */   private boolean columnFirstRelative;
/*  19:    */   private boolean rowFirstRelative;
/*  20:    */   private boolean columnLastRelative;
/*  21:    */   private boolean rowLastRelative;
/*  22:    */   private ExternalSheet workbook;
/*  23:    */   
/*  24:    */   Area3d(ExternalSheet es)
/*  25:    */   {
/*  26: 95 */     this.workbook = es;
/*  27:    */   }
/*  28:    */   
/*  29:    */   Area3d(String s, ExternalSheet es)
/*  30:    */     throws FormulaException
/*  31:    */   {
/*  32:107 */     this.workbook = es;
/*  33:108 */     int seppos = s.lastIndexOf(":");
/*  34:109 */     Assert.verify(seppos != -1);
/*  35:110 */     String endcell = s.substring(seppos + 1);
/*  36:    */     
/*  37:    */ 
/*  38:113 */     int sep = s.indexOf('!');
/*  39:114 */     String cellString = s.substring(sep + 1, seppos);
/*  40:115 */     this.columnFirst = CellReferenceHelper.getColumn(cellString);
/*  41:116 */     this.rowFirst = CellReferenceHelper.getRow(cellString);
/*  42:    */     
/*  43:    */ 
/*  44:119 */     String sheetName = s.substring(0, sep);
/*  45:122 */     if ((sheetName.charAt(0) == '\'') && (sheetName.charAt(sheetName.length() - 1) == '\'')) {
/*  46:125 */       sheetName = sheetName.substring(1, sheetName.length() - 1);
/*  47:    */     }
/*  48:128 */     this.sheet = es.getExternalSheetIndex(sheetName);
/*  49:130 */     if (this.sheet < 0) {
/*  50:132 */       throw new FormulaException(FormulaException.SHEET_REF_NOT_FOUND, sheetName);
/*  51:    */     }
/*  52:137 */     this.columnLast = CellReferenceHelper.getColumn(endcell);
/*  53:138 */     this.rowLast = CellReferenceHelper.getRow(endcell);
/*  54:    */     
/*  55:140 */     this.columnFirstRelative = true;
/*  56:141 */     this.rowFirstRelative = true;
/*  57:142 */     this.columnLastRelative = true;
/*  58:143 */     this.rowLastRelative = true;
/*  59:    */   }
/*  60:    */   
/*  61:    */   int getFirstColumn()
/*  62:    */   {
/*  63:153 */     return this.columnFirst;
/*  64:    */   }
/*  65:    */   
/*  66:    */   int getFirstRow()
/*  67:    */   {
/*  68:163 */     return this.rowFirst;
/*  69:    */   }
/*  70:    */   
/*  71:    */   int getLastColumn()
/*  72:    */   {
/*  73:173 */     return this.columnLast;
/*  74:    */   }
/*  75:    */   
/*  76:    */   int getLastRow()
/*  77:    */   {
/*  78:183 */     return this.rowLast;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int read(byte[] data, int pos)
/*  82:    */   {
/*  83:195 */     this.sheet = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  84:196 */     this.rowFirst = IntegerHelper.getInt(data[(pos + 2)], data[(pos + 3)]);
/*  85:197 */     this.rowLast = IntegerHelper.getInt(data[(pos + 4)], data[(pos + 5)]);
/*  86:198 */     int columnMask = IntegerHelper.getInt(data[(pos + 6)], data[(pos + 7)]);
/*  87:199 */     this.columnFirst = (columnMask & 0xFF);
/*  88:200 */     this.columnFirstRelative = ((columnMask & 0x4000) != 0);
/*  89:201 */     this.rowFirstRelative = ((columnMask & 0x8000) != 0);
/*  90:202 */     columnMask = IntegerHelper.getInt(data[(pos + 8)], data[(pos + 9)]);
/*  91:203 */     this.columnLast = (columnMask & 0xFF);
/*  92:204 */     this.columnLastRelative = ((columnMask & 0x4000) != 0);
/*  93:205 */     this.rowLastRelative = ((columnMask & 0x8000) != 0);
/*  94:    */     
/*  95:207 */     return 10;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void getString(StringBuffer buf)
/*  99:    */   {
/* 100:217 */     CellReferenceHelper.getCellReference(this.sheet, this.columnFirst, this.rowFirst, this.workbook, buf);
/* 101:    */     
/* 102:219 */     buf.append(':');
/* 103:220 */     CellReferenceHelper.getCellReference(this.columnLast, this.rowLast, buf);
/* 104:    */   }
/* 105:    */   
/* 106:    */   byte[] getBytes()
/* 107:    */   {
/* 108:230 */     byte[] data = new byte[11];
/* 109:231 */     data[0] = Token.AREA3D.getCode();
/* 110:    */     
/* 111:233 */     IntegerHelper.getTwoBytes(this.sheet, data, 1);
/* 112:    */     
/* 113:235 */     IntegerHelper.getTwoBytes(this.rowFirst, data, 3);
/* 114:236 */     IntegerHelper.getTwoBytes(this.rowLast, data, 5);
/* 115:    */     
/* 116:238 */     int grcol = this.columnFirst;
/* 117:241 */     if (this.rowFirstRelative) {
/* 118:243 */       grcol |= 0x8000;
/* 119:    */     }
/* 120:246 */     if (this.columnFirstRelative) {
/* 121:248 */       grcol |= 0x4000;
/* 122:    */     }
/* 123:251 */     IntegerHelper.getTwoBytes(grcol, data, 7);
/* 124:    */     
/* 125:253 */     grcol = this.columnLast;
/* 126:256 */     if (this.rowLastRelative) {
/* 127:258 */       grcol |= 0x8000;
/* 128:    */     }
/* 129:261 */     if (this.columnLastRelative) {
/* 130:263 */       grcol |= 0x4000;
/* 131:    */     }
/* 132:266 */     IntegerHelper.getTwoBytes(grcol, data, 9);
/* 133:    */     
/* 134:268 */     return data;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void adjustRelativeCellReferences(int colAdjust, int rowAdjust)
/* 138:    */   {
/* 139:280 */     if (this.columnFirstRelative) {
/* 140:282 */       this.columnFirst += colAdjust;
/* 141:    */     }
/* 142:285 */     if (this.columnLastRelative) {
/* 143:287 */       this.columnLast += colAdjust;
/* 144:    */     }
/* 145:290 */     if (this.rowFirstRelative) {
/* 146:292 */       this.rowFirst += rowAdjust;
/* 147:    */     }
/* 148:295 */     if (this.rowLastRelative) {
/* 149:297 */       this.rowLast += rowAdjust;
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void columnInserted(int sheetIndex, int col, boolean currentSheet)
/* 154:    */   {
/* 155:313 */     if (sheetIndex != this.sheet) {
/* 156:315 */       return;
/* 157:    */     }
/* 158:318 */     if (this.columnFirst >= col) {
/* 159:320 */       this.columnFirst += 1;
/* 160:    */     }
/* 161:323 */     if (this.columnLast >= col) {
/* 162:325 */       this.columnLast += 1;
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   void columnRemoved(int sheetIndex, int col, boolean currentSheet)
/* 167:    */   {
/* 168:341 */     if (sheetIndex != this.sheet) {
/* 169:343 */       return;
/* 170:    */     }
/* 171:346 */     if (col < this.columnFirst) {
/* 172:348 */       this.columnFirst -= 1;
/* 173:    */     }
/* 174:351 */     if (col <= this.columnLast) {
/* 175:353 */       this.columnLast -= 1;
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   void rowInserted(int sheetIndex, int row, boolean currentSheet)
/* 180:    */   {
/* 181:369 */     if (sheetIndex != this.sheet) {
/* 182:371 */       return;
/* 183:    */     }
/* 184:374 */     if (this.rowLast == 65535) {
/* 185:377 */       return;
/* 186:    */     }
/* 187:380 */     if (row <= this.rowFirst) {
/* 188:382 */       this.rowFirst += 1;
/* 189:    */     }
/* 190:385 */     if (row <= this.rowLast) {
/* 191:387 */       this.rowLast += 1;
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   void rowRemoved(int sheetIndex, int row, boolean currentSheet)
/* 196:    */   {
/* 197:403 */     if (sheetIndex != this.sheet) {
/* 198:405 */       return;
/* 199:    */     }
/* 200:408 */     if (this.rowLast == 65535) {
/* 201:411 */       return;
/* 202:    */     }
/* 203:414 */     if (row < this.rowFirst) {
/* 204:416 */       this.rowFirst -= 1;
/* 205:    */     }
/* 206:419 */     if (row <= this.rowLast) {
/* 207:421 */       this.rowLast -= 1;
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   protected void setRangeData(int sht, int colFirst, int colLast, int rwFirst, int rwLast, boolean colFirstRel, boolean colLastRel, boolean rowFirstRel, boolean rowLastRel)
/* 212:    */   {
/* 213:448 */     this.sheet = sht;
/* 214:449 */     this.columnFirst = colFirst;
/* 215:450 */     this.columnLast = colLast;
/* 216:451 */     this.rowFirst = rwFirst;
/* 217:452 */     this.rowLast = rwLast;
/* 218:453 */     this.columnFirstRelative = colFirstRel;
/* 219:454 */     this.columnLastRelative = colLastRel;
/* 220:455 */     this.rowFirstRelative = rowFirstRel;
/* 221:456 */     this.rowLastRelative = rowLastRel;
/* 222:    */   }
/* 223:    */   
/* 224:    */   void handleImportedCellReferences()
/* 225:    */   {
/* 226:465 */     setInvalid();
/* 227:    */   }
/* 228:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.Area3d
 * JD-Core Version:    0.7.0.1
 */