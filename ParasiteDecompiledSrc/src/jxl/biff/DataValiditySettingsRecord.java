/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.WorkbookSettings;
/*   4:    */ import jxl.biff.formula.ExternalSheet;
/*   5:    */ import jxl.biff.formula.FormulaException;
/*   6:    */ import jxl.common.Assert;
/*   7:    */ import jxl.common.Logger;
/*   8:    */ import jxl.read.biff.Record;
/*   9:    */ 
/*  10:    */ public class DataValiditySettingsRecord
/*  11:    */   extends WritableRecordData
/*  12:    */ {
/*  13: 40 */   private static Logger logger = Logger.getLogger(DataValiditySettingsRecord.class);
/*  14:    */   private byte[] data;
/*  15:    */   private DVParser dvParser;
/*  16:    */   private WorkbookMethods workbook;
/*  17:    */   private ExternalSheet externalSheet;
/*  18:    */   private WorkbookSettings workbookSettings;
/*  19:    */   private DataValidation dataValidation;
/*  20:    */   
/*  21:    */   public DataValiditySettingsRecord(Record t, ExternalSheet es, WorkbookMethods wm, WorkbookSettings ws)
/*  22:    */   {
/*  23: 81 */     super(t);
/*  24:    */     
/*  25: 83 */     this.data = t.getData();
/*  26: 84 */     this.externalSheet = es;
/*  27: 85 */     this.workbook = wm;
/*  28: 86 */     this.workbookSettings = ws;
/*  29:    */   }
/*  30:    */   
/*  31:    */   DataValiditySettingsRecord(DataValiditySettingsRecord dvsr)
/*  32:    */   {
/*  33: 94 */     super(Type.DV);
/*  34:    */     
/*  35: 96 */     this.data = dvsr.getData();
/*  36:    */   }
/*  37:    */   
/*  38:    */   DataValiditySettingsRecord(DataValiditySettingsRecord dvsr, ExternalSheet es, WorkbookMethods w, WorkbookSettings ws)
/*  39:    */   {
/*  40:109 */     super(Type.DV);
/*  41:    */     
/*  42:111 */     this.workbook = w;
/*  43:112 */     this.externalSheet = es;
/*  44:113 */     this.workbookSettings = ws;
/*  45:    */     
/*  46:115 */     Assert.verify(w != null);
/*  47:116 */     Assert.verify(es != null);
/*  48:    */     
/*  49:118 */     this.data = new byte[dvsr.data.length];
/*  50:119 */     System.arraycopy(dvsr.data, 0, this.data, 0, this.data.length);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public DataValiditySettingsRecord(DVParser dvp)
/*  54:    */   {
/*  55:129 */     super(Type.DV);
/*  56:130 */     this.dvParser = dvp;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void initialize()
/*  60:    */   {
/*  61:138 */     if (this.dvParser == null) {
/*  62:140 */       this.dvParser = new DVParser(this.data, this.externalSheet, this.workbook, this.workbookSettings);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public byte[] getData()
/*  67:    */   {
/*  68:152 */     if (this.dvParser == null) {
/*  69:154 */       return this.data;
/*  70:    */     }
/*  71:157 */     return this.dvParser.getData();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void insertRow(int row)
/*  75:    */   {
/*  76:167 */     if (this.dvParser == null) {
/*  77:169 */       initialize();
/*  78:    */     }
/*  79:172 */     this.dvParser.insertRow(row);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void removeRow(int row)
/*  83:    */   {
/*  84:182 */     if (this.dvParser == null) {
/*  85:184 */       initialize();
/*  86:    */     }
/*  87:187 */     this.dvParser.removeRow(row);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void insertColumn(int col)
/*  91:    */   {
/*  92:197 */     if (this.dvParser == null) {
/*  93:199 */       initialize();
/*  94:    */     }
/*  95:202 */     this.dvParser.insertColumn(col);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void removeColumn(int col)
/*  99:    */   {
/* 100:212 */     if (this.dvParser == null) {
/* 101:214 */       initialize();
/* 102:    */     }
/* 103:217 */     this.dvParser.removeColumn(col);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getFirstColumn()
/* 107:    */   {
/* 108:227 */     if (this.dvParser == null) {
/* 109:229 */       initialize();
/* 110:    */     }
/* 111:232 */     return this.dvParser.getFirstColumn();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getLastColumn()
/* 115:    */   {
/* 116:242 */     if (this.dvParser == null) {
/* 117:244 */       initialize();
/* 118:    */     }
/* 119:247 */     return this.dvParser.getLastColumn();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getFirstRow()
/* 123:    */   {
/* 124:257 */     if (this.dvParser == null) {
/* 125:259 */       initialize();
/* 126:    */     }
/* 127:262 */     return this.dvParser.getFirstRow();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getLastRow()
/* 131:    */   {
/* 132:272 */     if (this.dvParser == null) {
/* 133:274 */       initialize();
/* 134:    */     }
/* 135:277 */     return this.dvParser.getLastRow();
/* 136:    */   }
/* 137:    */   
/* 138:    */   void setDataValidation(DataValidation dv)
/* 139:    */   {
/* 140:287 */     this.dataValidation = dv;
/* 141:    */   }
/* 142:    */   
/* 143:    */   DVParser getDVParser()
/* 144:    */   {
/* 145:296 */     return this.dvParser;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getValidationFormula()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:303 */       if (this.dvParser == null) {
/* 153:305 */         initialize();
/* 154:    */       }
/* 155:308 */       return this.dvParser.getValidationFormula();
/* 156:    */     }
/* 157:    */     catch (FormulaException e)
/* 158:    */     {
/* 159:312 */       logger.warn("Cannot read drop down range " + e.getMessage());
/* 160:    */     }
/* 161:313 */     return "";
/* 162:    */   }
/* 163:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.DataValiditySettingsRecord
 * JD-Core Version:    0.7.0.1
 */