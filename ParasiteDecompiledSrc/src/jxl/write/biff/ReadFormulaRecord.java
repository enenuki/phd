/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.CellReferenceHelper;
/*   4:    */ import jxl.CellType;
/*   5:    */ import jxl.FormulaCell;
/*   6:    */ import jxl.Sheet;
/*   7:    */ import jxl.WorkbookSettings;
/*   8:    */ import jxl.biff.FormattingRecords;
/*   9:    */ import jxl.biff.FormulaData;
/*  10:    */ import jxl.biff.IntegerHelper;
/*  11:    */ import jxl.biff.Type;
/*  12:    */ import jxl.biff.WorkbookMethods;
/*  13:    */ import jxl.biff.formula.ExternalSheet;
/*  14:    */ import jxl.biff.formula.FormulaException;
/*  15:    */ import jxl.biff.formula.FormulaParser;
/*  16:    */ import jxl.common.Assert;
/*  17:    */ import jxl.common.Logger;
/*  18:    */ import jxl.write.WritableCell;
/*  19:    */ 
/*  20:    */ class ReadFormulaRecord
/*  21:    */   extends CellValue
/*  22:    */   implements FormulaData
/*  23:    */ {
/*  24: 51 */   private static Logger logger = Logger.getLogger(ReadFormulaRecord.class);
/*  25:    */   private FormulaData formula;
/*  26:    */   private FormulaParser parser;
/*  27:    */   
/*  28:    */   protected ReadFormulaRecord(FormulaData f)
/*  29:    */   {
/*  30: 70 */     super(Type.FORMULA, f);
/*  31: 71 */     this.formula = f;
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected final byte[] getCellData()
/*  35:    */   {
/*  36: 76 */     return super.getData();
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected byte[] handleFormulaException()
/*  40:    */   {
/*  41: 88 */     byte[] expressiondata = null;
/*  42: 89 */     byte[] celldata = super.getData();
/*  43:    */     
/*  44:    */ 
/*  45: 92 */     WritableWorkbookImpl w = getSheet().getWorkbook();
/*  46: 93 */     this.parser = new FormulaParser(getContents(), w, w, w.getSettings());
/*  47:    */     try
/*  48:    */     {
/*  49: 99 */       this.parser.parse();
/*  50:    */     }
/*  51:    */     catch (FormulaException e2)
/*  52:    */     {
/*  53:103 */       logger.warn(e2.getMessage());
/*  54:104 */       this.parser = new FormulaParser("\"ERROR\"", w, w, w.getSettings());
/*  55:    */       try
/*  56:    */       {
/*  57:105 */         this.parser.parse();
/*  58:    */       }
/*  59:    */       catch (FormulaException e3)
/*  60:    */       {
/*  61:106 */         Assert.verify(false);
/*  62:    */       }
/*  63:    */     }
/*  64:108 */     byte[] formulaBytes = this.parser.getBytes();
/*  65:109 */     expressiondata = new byte[formulaBytes.length + 16];
/*  66:110 */     IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
/*  67:111 */     System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length); byte[] 
/*  68:    */     
/*  69:    */ 
/*  70:    */ 
/*  71:115 */       tmp136_133 = expressiondata;tmp136_133[8] = ((byte)(tmp136_133[8] | 0x2));
/*  72:    */     
/*  73:117 */     byte[] data = new byte[celldata.length + expressiondata.length];
/*  74:    */     
/*  75:119 */     System.arraycopy(celldata, 0, data, 0, celldata.length);
/*  76:120 */     System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
/*  77:    */     
/*  78:122 */     return data;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public byte[] getData()
/*  82:    */   {
/*  83:134 */     byte[] celldata = super.getData();
/*  84:135 */     byte[] expressiondata = null;
/*  85:    */     try
/*  86:    */     {
/*  87:139 */       if (this.parser == null)
/*  88:    */       {
/*  89:141 */         expressiondata = this.formula.getFormulaData();
/*  90:    */       }
/*  91:    */       else
/*  92:    */       {
/*  93:145 */         byte[] formulaBytes = this.parser.getBytes();
/*  94:146 */         expressiondata = new byte[formulaBytes.length + 16];
/*  95:147 */         IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
/*  96:148 */         System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length);
/*  97:    */       }
/*  98:153 */       byte[] tmp64_61 = expressiondata;tmp64_61[8] = ((byte)(tmp64_61[8] | 0x2));
/*  99:    */       
/* 100:155 */       byte[] data = new byte[celldata.length + expressiondata.length];
/* 101:    */       
/* 102:157 */       System.arraycopy(celldata, 0, data, 0, celldata.length);
/* 103:158 */       System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
/* 104:    */       
/* 105:160 */       return data;
/* 106:    */     }
/* 107:    */     catch (FormulaException e)
/* 108:    */     {
/* 109:166 */       logger.warn(CellReferenceHelper.getCellReference(getColumn(), getRow()) + " " + e.getMessage());
/* 110:    */     }
/* 111:169 */     return handleFormulaException();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public CellType getType()
/* 115:    */   {
/* 116:180 */     return this.formula.getType();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getContents()
/* 120:    */   {
/* 121:190 */     return this.formula.getContents();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public byte[] getFormulaData()
/* 125:    */     throws FormulaException
/* 126:    */   {
/* 127:201 */     byte[] d = this.formula.getFormulaData();
/* 128:202 */     byte[] data = new byte[d.length];
/* 129:    */     
/* 130:204 */     System.arraycopy(d, 0, data, 0, d.length); byte[] 
/* 131:    */     
/* 132:    */ 
/* 133:207 */       tmp27_24 = data;tmp27_24[8] = ((byte)(tmp27_24[8] | 0x2));
/* 134:    */     
/* 135:209 */     return data;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public byte[] getFormulaBytes()
/* 139:    */     throws FormulaException
/* 140:    */   {
/* 141:220 */     if (this.parser != null) {
/* 142:222 */       return this.parser.getBytes();
/* 143:    */     }
/* 144:226 */     byte[] readFormulaData = getFormulaData();
/* 145:227 */     byte[] formulaBytes = new byte[readFormulaData.length - 16];
/* 146:228 */     System.arraycopy(readFormulaData, 16, formulaBytes, 0, formulaBytes.length);
/* 147:    */     
/* 148:230 */     return formulaBytes;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public WritableCell copyTo(int col, int row)
/* 152:    */   {
/* 153:242 */     return new FormulaRecord(col, row, this);
/* 154:    */   }
/* 155:    */   
/* 156:    */   void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s)
/* 157:    */   {
/* 158:256 */     super.setCellDetails(fr, ss, s);
/* 159:257 */     s.getWorkbook().addRCIRCell(this);
/* 160:    */   }
/* 161:    */   
/* 162:    */   void columnInserted(Sheet s, int sheetIndex, int col)
/* 163:    */   {
/* 164:    */     try
/* 165:    */     {
/* 166:272 */       if (this.parser == null)
/* 167:    */       {
/* 168:274 */         byte[] formulaData = this.formula.getFormulaData();
/* 169:275 */         byte[] formulaBytes = new byte[formulaData.length - 16];
/* 170:276 */         System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
/* 171:    */         
/* 172:278 */         this.parser = new FormulaParser(formulaBytes, this, getSheet().getWorkbook(), getSheet().getWorkbook(), getSheet().getWorkbookSettings());
/* 173:    */         
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:283 */         this.parser.parse();
/* 178:    */       }
/* 179:286 */       this.parser.columnInserted(sheetIndex, col, s == getSheet());
/* 180:    */     }
/* 181:    */     catch (FormulaException e)
/* 182:    */     {
/* 183:290 */       logger.warn("cannot insert column within formula:  " + e.getMessage());
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   void columnRemoved(Sheet s, int sheetIndex, int col)
/* 188:    */   {
/* 189:    */     try
/* 190:    */     {
/* 191:306 */       if (this.parser == null)
/* 192:    */       {
/* 193:308 */         byte[] formulaData = this.formula.getFormulaData();
/* 194:309 */         byte[] formulaBytes = new byte[formulaData.length - 16];
/* 195:310 */         System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
/* 196:    */         
/* 197:312 */         this.parser = new FormulaParser(formulaBytes, this, getSheet().getWorkbook(), getSheet().getWorkbook(), getSheet().getWorkbookSettings());
/* 198:    */         
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:317 */         this.parser.parse();
/* 203:    */       }
/* 204:320 */       this.parser.columnRemoved(sheetIndex, col, s == getSheet());
/* 205:    */     }
/* 206:    */     catch (FormulaException e)
/* 207:    */     {
/* 208:324 */       logger.warn("cannot remove column within formula:  " + e.getMessage());
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   void rowInserted(Sheet s, int sheetIndex, int row)
/* 213:    */   {
/* 214:    */     try
/* 215:    */     {
/* 216:340 */       if (this.parser == null)
/* 217:    */       {
/* 218:342 */         byte[] formulaData = this.formula.getFormulaData();
/* 219:343 */         byte[] formulaBytes = new byte[formulaData.length - 16];
/* 220:344 */         System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
/* 221:    */         
/* 222:346 */         this.parser = new FormulaParser(formulaBytes, this, getSheet().getWorkbook(), getSheet().getWorkbook(), getSheet().getWorkbookSettings());
/* 223:    */         
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:351 */         this.parser.parse();
/* 228:    */       }
/* 229:354 */       this.parser.rowInserted(sheetIndex, row, s == getSheet());
/* 230:    */     }
/* 231:    */     catch (FormulaException e)
/* 232:    */     {
/* 233:358 */       logger.warn("cannot insert row within formula:  " + e.getMessage());
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   void rowRemoved(Sheet s, int sheetIndex, int row)
/* 238:    */   {
/* 239:    */     try
/* 240:    */     {
/* 241:374 */       if (this.parser == null)
/* 242:    */       {
/* 243:376 */         byte[] formulaData = this.formula.getFormulaData();
/* 244:377 */         byte[] formulaBytes = new byte[formulaData.length - 16];
/* 245:378 */         System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
/* 246:    */         
/* 247:380 */         this.parser = new FormulaParser(formulaBytes, this, getSheet().getWorkbook(), getSheet().getWorkbook(), getSheet().getWorkbookSettings());
/* 248:    */         
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:385 */         this.parser.parse();
/* 253:    */       }
/* 254:388 */       this.parser.rowRemoved(sheetIndex, row, s == getSheet());
/* 255:    */     }
/* 256:    */     catch (FormulaException e)
/* 257:    */     {
/* 258:392 */       logger.warn("cannot remove row within formula:  " + e.getMessage());
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   protected FormulaData getReadFormula()
/* 263:    */   {
/* 264:403 */     return this.formula;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getFormula()
/* 268:    */     throws FormulaException
/* 269:    */   {
/* 270:413 */     return ((FormulaCell)this.formula).getFormula();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public boolean handleImportedCellReferences(ExternalSheet es, WorkbookMethods mt, WorkbookSettings ws)
/* 274:    */   {
/* 275:    */     try
/* 276:    */     {
/* 277:428 */       if (this.parser == null)
/* 278:    */       {
/* 279:430 */         byte[] formulaData = this.formula.getFormulaData();
/* 280:431 */         byte[] formulaBytes = new byte[formulaData.length - 16];
/* 281:432 */         System.arraycopy(formulaData, 16, formulaBytes, 0, formulaBytes.length);
/* 282:    */         
/* 283:434 */         this.parser = new FormulaParser(formulaBytes, this, es, mt, ws);
/* 284:    */         
/* 285:    */ 
/* 286:437 */         this.parser.parse();
/* 287:    */       }
/* 288:440 */       return this.parser.handleImportedCellReferences();
/* 289:    */     }
/* 290:    */     catch (FormulaException e)
/* 291:    */     {
/* 292:444 */       logger.warn("cannot import formula:  " + e.getMessage());
/* 293:    */     }
/* 294:445 */     return false;
/* 295:    */   }
/* 296:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.ReadFormulaRecord
 * JD-Core Version:    0.7.0.1
 */