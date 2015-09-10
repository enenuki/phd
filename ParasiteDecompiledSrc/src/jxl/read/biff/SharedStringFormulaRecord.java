/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import jxl.CellType;
/*   4:    */ import jxl.LabelCell;
/*   5:    */ import jxl.StringFormulaCell;
/*   6:    */ import jxl.WorkbookSettings;
/*   7:    */ import jxl.biff.FormattingRecords;
/*   8:    */ import jxl.biff.FormulaData;
/*   9:    */ import jxl.biff.IntegerHelper;
/*  10:    */ import jxl.biff.StringHelper;
/*  11:    */ import jxl.biff.Type;
/*  12:    */ import jxl.biff.WorkbookMethods;
/*  13:    */ import jxl.biff.formula.ExternalSheet;
/*  14:    */ import jxl.biff.formula.FormulaException;
/*  15:    */ import jxl.biff.formula.FormulaParser;
/*  16:    */ import jxl.common.Assert;
/*  17:    */ import jxl.common.Logger;
/*  18:    */ 
/*  19:    */ public class SharedStringFormulaRecord
/*  20:    */   extends BaseSharedFormulaRecord
/*  21:    */   implements LabelCell, FormulaData, StringFormulaCell
/*  22:    */ {
/*  23: 49 */   private static Logger logger = Logger.getLogger(SharedStringFormulaRecord.class);
/*  24:    */   private String value;
/*  25: 60 */   protected static final EmptyString EMPTY_STRING = new EmptyString(null);
/*  26:    */   
/*  27:    */   public SharedStringFormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, WorkbookSettings ws)
/*  28:    */   {
/*  29: 81 */     super(t, fr, es, nt, si, excelFile.getPos());
/*  30: 82 */     int pos = excelFile.getPos();
/*  31:    */     
/*  32:    */ 
/*  33: 85 */     int filepos = excelFile.getPos();
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37: 89 */     Record nextRecord = excelFile.next();
/*  38: 90 */     int count = 0;
/*  39: 91 */     while ((nextRecord.getType() != Type.STRING) && (count < 4))
/*  40:    */     {
/*  41: 93 */       nextRecord = excelFile.next();
/*  42: 94 */       count++;
/*  43:    */     }
/*  44: 96 */     Assert.verify(count < 4, " @ " + pos);
/*  45:    */     
/*  46: 98 */     byte[] stringData = nextRecord.getData();
/*  47:    */     
/*  48:    */ 
/*  49:101 */     nextRecord = excelFile.peek();
/*  50:102 */     while (nextRecord.getType() == Type.CONTINUE)
/*  51:    */     {
/*  52:104 */       nextRecord = excelFile.next();
/*  53:105 */       byte[] d = new byte[stringData.length + nextRecord.getLength() - 1];
/*  54:106 */       System.arraycopy(stringData, 0, d, 0, stringData.length);
/*  55:107 */       System.arraycopy(nextRecord.getData(), 1, d, stringData.length, nextRecord.getLength() - 1);
/*  56:    */       
/*  57:109 */       stringData = d;
/*  58:110 */       nextRecord = excelFile.peek();
/*  59:    */     }
/*  60:113 */     int chars = IntegerHelper.getInt(stringData[0], stringData[1]);
/*  61:    */     
/*  62:115 */     boolean unicode = false;
/*  63:116 */     int startpos = 3;
/*  64:117 */     if (stringData.length == chars + 2)
/*  65:    */     {
/*  66:121 */       startpos = 2;
/*  67:122 */       unicode = false;
/*  68:    */     }
/*  69:124 */     else if (stringData[2] == 1)
/*  70:    */     {
/*  71:127 */       startpos = 3;
/*  72:128 */       unicode = true;
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76:133 */       startpos = 3;
/*  77:134 */       unicode = false;
/*  78:    */     }
/*  79:137 */     if (!unicode) {
/*  80:139 */       this.value = StringHelper.getString(stringData, chars, startpos, ws);
/*  81:    */     } else {
/*  82:143 */       this.value = StringHelper.getUnicodeString(stringData, chars, startpos);
/*  83:    */     }
/*  84:148 */     excelFile.setPos(filepos);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public SharedStringFormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, EmptyString dummy)
/*  88:    */   {
/*  89:170 */     super(t, fr, es, nt, si, excelFile.getPos());
/*  90:    */     
/*  91:172 */     this.value = "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getString()
/*  95:    */   {
/*  96:182 */     return this.value;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getContents()
/* 100:    */   {
/* 101:192 */     return this.value;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public CellType getType()
/* 105:    */   {
/* 106:202 */     return CellType.STRING_FORMULA;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public byte[] getFormulaData()
/* 110:    */     throws FormulaException
/* 111:    */   {
/* 112:214 */     if (!getSheet().getWorkbookBof().isBiff8()) {
/* 113:216 */       throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
/* 114:    */     }
/* 115:221 */     FormulaParser fp = new FormulaParser(getTokens(), this, getExternalSheet(), getNameTable(), getSheet().getWorkbook().getSettings());
/* 116:    */     
/* 117:    */ 
/* 118:    */ 
/* 119:225 */     fp.parse();
/* 120:226 */     byte[] rpnTokens = fp.getBytes();
/* 121:    */     
/* 122:228 */     byte[] data = new byte[rpnTokens.length + 22];
/* 123:    */     
/* 124:    */ 
/* 125:231 */     IntegerHelper.getTwoBytes(getRow(), data, 0);
/* 126:232 */     IntegerHelper.getTwoBytes(getColumn(), data, 2);
/* 127:233 */     IntegerHelper.getTwoBytes(getXFIndex(), data, 4);
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:237 */     data[6] = 0;
/* 132:238 */     data[12] = -1;
/* 133:239 */     data[13] = -1;
/* 134:    */     
/* 135:    */ 
/* 136:242 */     System.arraycopy(rpnTokens, 0, data, 22, rpnTokens.length);
/* 137:243 */     IntegerHelper.getTwoBytes(rpnTokens.length, data, 20);
/* 138:    */     
/* 139:    */ 
/* 140:246 */     byte[] d = new byte[data.length - 6];
/* 141:247 */     System.arraycopy(data, 6, d, 0, data.length - 6);
/* 142:    */     
/* 143:249 */     return d;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private static final class EmptyString {}
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.SharedStringFormulaRecord
 * JD-Core Version:    0.7.0.1
 */