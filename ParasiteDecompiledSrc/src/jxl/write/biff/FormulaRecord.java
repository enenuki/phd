/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.CellReferenceHelper;
/*   4:    */ import jxl.CellType;
/*   5:    */ import jxl.Sheet;
/*   6:    */ import jxl.WorkbookSettings;
/*   7:    */ import jxl.biff.FormattingRecords;
/*   8:    */ import jxl.biff.FormulaData;
/*   9:    */ import jxl.biff.IntegerHelper;
/*  10:    */ import jxl.biff.Type;
/*  11:    */ import jxl.biff.WorkbookMethods;
/*  12:    */ import jxl.biff.formula.ExternalSheet;
/*  13:    */ import jxl.biff.formula.FormulaException;
/*  14:    */ import jxl.biff.formula.FormulaParser;
/*  15:    */ import jxl.common.Assert;
/*  16:    */ import jxl.common.Logger;
/*  17:    */ import jxl.format.CellFormat;
/*  18:    */ import jxl.write.WritableCell;
/*  19:    */ 
/*  20:    */ public class FormulaRecord
/*  21:    */   extends CellValue
/*  22:    */   implements FormulaData
/*  23:    */ {
/*  24: 49 */   private static Logger logger = Logger.getLogger(FormulaRecord.class);
/*  25:    */   private String formulaToParse;
/*  26:    */   private FormulaParser parser;
/*  27:    */   private String formulaString;
/*  28:    */   private byte[] formulaBytes;
/*  29:    */   private CellValue copiedFrom;
/*  30:    */   
/*  31:    */   public FormulaRecord(int c, int r, String f)
/*  32:    */   {
/*  33: 84 */     super(Type.FORMULA2, c, r);
/*  34: 85 */     this.formulaToParse = f;
/*  35: 86 */     this.copiedFrom = null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public FormulaRecord(int c, int r, String f, CellFormat st)
/*  39:    */   {
/*  40: 96 */     super(Type.FORMULA, c, r, st);
/*  41: 97 */     this.formulaToParse = f;
/*  42: 98 */     this.copiedFrom = null;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected FormulaRecord(int c, int r, FormulaRecord fr)
/*  46:    */   {
/*  47:110 */     super(Type.FORMULA, c, r, fr);
/*  48:111 */     this.copiedFrom = fr;
/*  49:112 */     this.formulaBytes = new byte[fr.formulaBytes.length];
/*  50:113 */     System.arraycopy(fr.formulaBytes, 0, this.formulaBytes, 0, this.formulaBytes.length);
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected FormulaRecord(int c, int r, ReadFormulaRecord rfr)
/*  54:    */   {
/*  55:125 */     super(Type.FORMULA, c, r, rfr);
/*  56:    */     try
/*  57:    */     {
/*  58:128 */       this.copiedFrom = rfr;
/*  59:129 */       this.formulaBytes = rfr.getFormulaBytes();
/*  60:    */     }
/*  61:    */     catch (FormulaException e)
/*  62:    */     {
/*  63:134 */       logger.error("", e);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void initialize(WorkbookSettings ws, ExternalSheet es, WorkbookMethods nt)
/*  68:    */   {
/*  69:150 */     if (this.copiedFrom != null)
/*  70:    */     {
/*  71:152 */       initializeCopiedFormula(ws, es, nt);
/*  72:153 */       return;
/*  73:    */     }
/*  74:156 */     this.parser = new FormulaParser(this.formulaToParse, es, nt, ws);
/*  75:    */     try
/*  76:    */     {
/*  77:160 */       this.parser.parse();
/*  78:161 */       this.formulaString = this.parser.getFormula();
/*  79:162 */       this.formulaBytes = this.parser.getBytes();
/*  80:    */     }
/*  81:    */     catch (FormulaException e)
/*  82:    */     {
/*  83:166 */       logger.warn(e.getMessage() + " when parsing formula " + this.formulaToParse + " in cell " + getSheet().getName() + "!" + CellReferenceHelper.getCellReference(getColumn(), getRow()));
/*  84:    */       try
/*  85:    */       {
/*  86:175 */         this.formulaToParse = "ERROR(1)";
/*  87:176 */         this.parser = new FormulaParser(this.formulaToParse, es, nt, ws);
/*  88:177 */         this.parser.parse();
/*  89:178 */         this.formulaString = this.parser.getFormula();
/*  90:179 */         this.formulaBytes = this.parser.getBytes();
/*  91:    */       }
/*  92:    */       catch (FormulaException e2)
/*  93:    */       {
/*  94:184 */         logger.error("", e2);
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void initializeCopiedFormula(WorkbookSettings ws, ExternalSheet es, WorkbookMethods nt)
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:202 */       this.parser = new FormulaParser(this.formulaBytes, this, es, nt, ws);
/* 104:203 */       this.parser.parse();
/* 105:204 */       this.parser.adjustRelativeCellReferences(getColumn() - this.copiedFrom.getColumn(), getRow() - this.copiedFrom.getRow());
/* 106:    */       
/* 107:    */ 
/* 108:207 */       this.formulaString = this.parser.getFormula();
/* 109:208 */       this.formulaBytes = this.parser.getBytes();
/* 110:    */     }
/* 111:    */     catch (FormulaException e)
/* 112:    */     {
/* 113:    */       try
/* 114:    */       {
/* 115:215 */         this.formulaToParse = "ERROR(1)";
/* 116:216 */         this.parser = new FormulaParser(this.formulaToParse, es, nt, ws);
/* 117:217 */         this.parser.parse();
/* 118:218 */         this.formulaString = this.parser.getFormula();
/* 119:219 */         this.formulaBytes = this.parser.getBytes();
/* 120:    */       }
/* 121:    */       catch (FormulaException e2)
/* 122:    */       {
/* 123:225 */         logger.error("", e2);
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s)
/* 129:    */   {
/* 130:242 */     super.setCellDetails(fr, ss, s);
/* 131:243 */     initialize(s.getWorkbookSettings(), s.getWorkbook(), s.getWorkbook());
/* 132:244 */     s.getWorkbook().addRCIRCell(this);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public byte[] getData()
/* 136:    */   {
/* 137:254 */     byte[] celldata = super.getData();
/* 138:255 */     byte[] formulaData = getFormulaData();
/* 139:256 */     byte[] data = new byte[formulaData.length + celldata.length];
/* 140:257 */     System.arraycopy(celldata, 0, data, 0, celldata.length);
/* 141:258 */     System.arraycopy(formulaData, 0, data, celldata.length, formulaData.length);
/* 142:    */     
/* 143:260 */     return data;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public CellType getType()
/* 147:    */   {
/* 148:270 */     return CellType.ERROR;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getContents()
/* 152:    */   {
/* 153:282 */     return this.formulaString;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public byte[] getFormulaData()
/* 157:    */   {
/* 158:293 */     byte[] data = new byte[this.formulaBytes.length + 16];
/* 159:294 */     System.arraycopy(this.formulaBytes, 0, data, 16, this.formulaBytes.length);
/* 160:    */     
/* 161:296 */     data[6] = 16;
/* 162:297 */     data[7] = 64;
/* 163:298 */     data[12] = -32;
/* 164:299 */     data[13] = -4; byte[] 
/* 165:    */     
/* 166:301 */       tmp54_51 = data;tmp54_51[8] = ((byte)(tmp54_51[8] | 0x2));
/* 167:    */     
/* 168:    */ 
/* 169:304 */     IntegerHelper.getTwoBytes(this.formulaBytes.length, data, 14);
/* 170:    */     
/* 171:306 */     return data;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public WritableCell copyTo(int col, int row)
/* 175:    */   {
/* 176:319 */     Assert.verify(false);
/* 177:320 */     return null;
/* 178:    */   }
/* 179:    */   
/* 180:    */   void columnInserted(Sheet s, int sheetIndex, int col)
/* 181:    */   {
/* 182:333 */     this.parser.columnInserted(sheetIndex, col, s == getSheet());
/* 183:334 */     this.formulaBytes = this.parser.getBytes();
/* 184:    */   }
/* 185:    */   
/* 186:    */   void columnRemoved(Sheet s, int sheetIndex, int col)
/* 187:    */   {
/* 188:347 */     this.parser.columnRemoved(sheetIndex, col, s == getSheet());
/* 189:348 */     this.formulaBytes = this.parser.getBytes();
/* 190:    */   }
/* 191:    */   
/* 192:    */   void rowInserted(Sheet s, int sheetIndex, int row)
/* 193:    */   {
/* 194:361 */     this.parser.rowInserted(sheetIndex, row, s == getSheet());
/* 195:362 */     this.formulaBytes = this.parser.getBytes();
/* 196:    */   }
/* 197:    */   
/* 198:    */   void rowRemoved(Sheet s, int sheetIndex, int row)
/* 199:    */   {
/* 200:375 */     this.parser.rowRemoved(sheetIndex, row, s == getSheet());
/* 201:376 */     this.formulaBytes = this.parser.getBytes();
/* 202:    */   }
/* 203:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.FormulaRecord
 * JD-Core Version:    0.7.0.1
 */