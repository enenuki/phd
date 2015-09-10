/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import jxl.CellType;
/*   4:    */ import jxl.ErrorCell;
/*   5:    */ import jxl.ErrorFormulaCell;
/*   6:    */ import jxl.biff.FormattingRecords;
/*   7:    */ import jxl.biff.FormulaData;
/*   8:    */ import jxl.biff.WorkbookMethods;
/*   9:    */ import jxl.biff.formula.ExternalSheet;
/*  10:    */ import jxl.biff.formula.FormulaErrorCode;
/*  11:    */ import jxl.biff.formula.FormulaException;
/*  12:    */ import jxl.biff.formula.FormulaParser;
/*  13:    */ import jxl.common.Assert;
/*  14:    */ 
/*  15:    */ class ErrorFormulaRecord
/*  16:    */   extends CellValue
/*  17:    */   implements ErrorCell, FormulaData, ErrorFormulaCell
/*  18:    */ {
/*  19:    */   private int errorCode;
/*  20:    */   private ExternalSheet externalSheet;
/*  21:    */   private WorkbookMethods nameTable;
/*  22:    */   private String formulaString;
/*  23:    */   private byte[] data;
/*  24:    */   private FormulaErrorCode error;
/*  25:    */   
/*  26:    */   public ErrorFormulaRecord(Record t, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si)
/*  27:    */   {
/*  28: 84 */     super(t, fr, si);
/*  29:    */     
/*  30: 86 */     this.externalSheet = es;
/*  31: 87 */     this.nameTable = nt;
/*  32: 88 */     this.data = getRecord().getData();
/*  33:    */     
/*  34: 90 */     Assert.verify(this.data[6] == 2);
/*  35:    */     
/*  36: 92 */     this.errorCode = this.data[8];
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getErrorCode()
/*  40:    */   {
/*  41:104 */     return this.errorCode;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getContents()
/*  45:    */   {
/*  46:114 */     if (this.error == null) {
/*  47:116 */       this.error = FormulaErrorCode.getErrorCode(this.errorCode);
/*  48:    */     }
/*  49:119 */     return "ERROR " + this.errorCode;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public CellType getType()
/*  53:    */   {
/*  54:130 */     return CellType.FORMULA_ERROR;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public byte[] getFormulaData()
/*  58:    */     throws FormulaException
/*  59:    */   {
/*  60:141 */     if (!getSheet().getWorkbookBof().isBiff8()) {
/*  61:143 */       throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
/*  62:    */     }
/*  63:147 */     byte[] d = new byte[this.data.length - 6];
/*  64:148 */     System.arraycopy(this.data, 6, d, 0, this.data.length - 6);
/*  65:    */     
/*  66:150 */     return d;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getFormula()
/*  70:    */     throws FormulaException
/*  71:    */   {
/*  72:161 */     if (this.formulaString == null)
/*  73:    */     {
/*  74:163 */       byte[] tokens = new byte[this.data.length - 22];
/*  75:164 */       System.arraycopy(this.data, 22, tokens, 0, tokens.length);
/*  76:165 */       FormulaParser fp = new FormulaParser(tokens, this, this.externalSheet, this.nameTable, getSheet().getWorkbook().getSettings());
/*  77:    */       
/*  78:    */ 
/*  79:168 */       fp.parse();
/*  80:169 */       this.formulaString = fp.getFormula();
/*  81:    */     }
/*  82:172 */     return this.formulaString;
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.ErrorFormulaRecord
 * JD-Core Version:    0.7.0.1
 */