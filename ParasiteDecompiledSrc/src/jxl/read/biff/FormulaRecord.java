/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import jxl.CellType;
/*   4:    */ import jxl.WorkbookSettings;
/*   5:    */ import jxl.biff.DoubleHelper;
/*   6:    */ import jxl.biff.FormattingRecords;
/*   7:    */ import jxl.biff.IntegerHelper;
/*   8:    */ import jxl.biff.WorkbookMethods;
/*   9:    */ import jxl.biff.formula.ExternalSheet;
/*  10:    */ import jxl.common.Assert;
/*  11:    */ import jxl.common.Logger;
/*  12:    */ 
/*  13:    */ class FormulaRecord
/*  14:    */   extends CellValue
/*  15:    */ {
/*  16: 41 */   private static Logger logger = Logger.getLogger(FormulaRecord.class);
/*  17:    */   private CellValue formula;
/*  18:    */   private boolean shared;
/*  19: 58 */   public static final IgnoreSharedFormula ignoreSharedFormula = new IgnoreSharedFormula(null);
/*  20:    */   
/*  21:    */   public FormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si, WorkbookSettings ws)
/*  22:    */   {
/*  23: 82 */     super(t, fr, si);
/*  24:    */     
/*  25: 84 */     byte[] data = getRecord().getData();
/*  26:    */     
/*  27: 86 */     this.shared = false;
/*  28:    */     
/*  29:    */ 
/*  30: 89 */     int grbit = IntegerHelper.getInt(data[14], data[15]);
/*  31: 90 */     if ((grbit & 0x8) != 0)
/*  32:    */     {
/*  33: 92 */       this.shared = true;
/*  34: 94 */       if ((data[6] == 0) && (data[12] == -1) && (data[13] == -1))
/*  35:    */       {
/*  36: 97 */         this.formula = new SharedStringFormulaRecord(t, excelFile, fr, es, nt, si, ws);
/*  37:    */       }
/*  38:100 */       else if ((data[6] == 3) && (data[12] == -1) && (data[13] == -1))
/*  39:    */       {
/*  40:103 */         this.formula = new SharedStringFormulaRecord(t, excelFile, fr, es, nt, si, SharedStringFormulaRecord.EMPTY_STRING);
/*  41:    */       }
/*  42:107 */       else if ((data[6] == 2) && (data[12] == -1) && (data[13] == -1))
/*  43:    */       {
/*  44:112 */         int errorCode = data[8];
/*  45:113 */         this.formula = new SharedErrorFormulaRecord(t, excelFile, errorCode, fr, es, nt, si);
/*  46:    */       }
/*  47:116 */       else if ((data[6] == 1) && (data[12] == -1) && (data[13] == -1))
/*  48:    */       {
/*  49:120 */         boolean value = data[8] == 1;
/*  50:121 */         this.formula = new SharedBooleanFormulaRecord(t, excelFile, value, fr, es, nt, si);
/*  51:    */       }
/*  52:    */       else
/*  53:    */       {
/*  54:127 */         double value = DoubleHelper.getIEEEDouble(data, 6);
/*  55:128 */         SharedNumberFormulaRecord snfr = new SharedNumberFormulaRecord(t, excelFile, value, fr, es, nt, si);
/*  56:    */         
/*  57:130 */         snfr.setNumberFormat(fr.getNumberFormat(getXFIndex()));
/*  58:131 */         this.formula = snfr;
/*  59:    */       }
/*  60:134 */       return;
/*  61:    */     }
/*  62:139 */     if ((data[6] == 0) && (data[12] == -1) && (data[13] == -1)) {
/*  63:142 */       this.formula = new StringFormulaRecord(t, excelFile, fr, es, nt, si, ws);
/*  64:144 */     } else if ((data[6] == 1) && (data[12] == -1) && (data[13] == -1)) {
/*  65:150 */       this.formula = new BooleanFormulaRecord(t, fr, es, nt, si);
/*  66:152 */     } else if ((data[6] == 2) && (data[12] == -1) && (data[13] == -1)) {
/*  67:157 */       this.formula = new ErrorFormulaRecord(t, fr, es, nt, si);
/*  68:159 */     } else if ((data[6] == 3) && (data[12] == -1) && (data[13] == -1)) {
/*  69:162 */       this.formula = new StringFormulaRecord(t, fr, es, nt, si);
/*  70:    */     } else {
/*  71:167 */       this.formula = new NumberFormulaRecord(t, fr, es, nt, si);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public FormulaRecord(Record t, File excelFile, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, IgnoreSharedFormula i, SheetImpl si, WorkbookSettings ws)
/*  76:    */   {
/*  77:195 */     super(t, fr, si);
/*  78:196 */     byte[] data = getRecord().getData();
/*  79:    */     
/*  80:198 */     this.shared = false;
/*  81:202 */     if ((data[6] == 0) && (data[12] == -1) && (data[13] == -1)) {
/*  82:205 */       this.formula = new StringFormulaRecord(t, excelFile, fr, es, nt, si, ws);
/*  83:207 */     } else if ((data[6] == 1) && (data[12] == -1) && (data[13] == -1)) {
/*  84:213 */       this.formula = new BooleanFormulaRecord(t, fr, es, nt, si);
/*  85:215 */     } else if ((data[6] == 2) && (data[12] == -1) && (data[13] == -1)) {
/*  86:220 */       this.formula = new ErrorFormulaRecord(t, fr, es, nt, si);
/*  87:    */     } else {
/*  88:225 */       this.formula = new NumberFormulaRecord(t, fr, es, nt, si);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getContents()
/*  93:    */   {
/*  94:236 */     Assert.verify(false);
/*  95:237 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public CellType getType()
/*  99:    */   {
/* 100:247 */     Assert.verify(false);
/* 101:248 */     return CellType.EMPTY;
/* 102:    */   }
/* 103:    */   
/* 104:    */   final CellValue getFormula()
/* 105:    */   {
/* 106:258 */     return this.formula;
/* 107:    */   }
/* 108:    */   
/* 109:    */   final boolean isShared()
/* 110:    */   {
/* 111:269 */     return this.shared;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static class IgnoreSharedFormula {}
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.FormulaRecord
 * JD-Core Version:    0.7.0.1
 */