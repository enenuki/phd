/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.ErrorFormulaCell;
/*   4:    */ import jxl.biff.FormulaData;
/*   5:    */ import jxl.biff.IntegerHelper;
/*   6:    */ import jxl.biff.formula.FormulaErrorCode;
/*   7:    */ import jxl.biff.formula.FormulaException;
/*   8:    */ import jxl.biff.formula.FormulaParser;
/*   9:    */ import jxl.common.Logger;
/*  10:    */ 
/*  11:    */ class ReadErrorFormulaRecord
/*  12:    */   extends ReadFormulaRecord
/*  13:    */   implements ErrorFormulaCell
/*  14:    */ {
/*  15: 39 */   private static Logger logger = Logger.getLogger(ReadErrorFormulaRecord.class);
/*  16:    */   
/*  17:    */   public ReadErrorFormulaRecord(FormulaData f)
/*  18:    */   {
/*  19: 48 */     super(f);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getErrorCode()
/*  23:    */   {
/*  24: 58 */     return ((ErrorFormulaCell)getReadFormula()).getErrorCode();
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected byte[] handleFormulaException()
/*  28:    */   {
/*  29: 70 */     byte[] expressiondata = null;
/*  30: 71 */     byte[] celldata = super.getCellData();
/*  31:    */     
/*  32: 73 */     int errorCode = getErrorCode();
/*  33: 74 */     String formulaString = null;
/*  34: 76 */     if (errorCode == FormulaErrorCode.DIV0.getCode()) {
/*  35: 78 */       formulaString = "1/0";
/*  36: 80 */     } else if (errorCode == FormulaErrorCode.VALUE.getCode()) {
/*  37: 82 */       formulaString = "\"\"/0";
/*  38: 84 */     } else if (errorCode == FormulaErrorCode.REF.getCode()) {
/*  39: 86 */       formulaString = "\"#REF!\"";
/*  40:    */     } else {
/*  41: 90 */       formulaString = "\"ERROR\"";
/*  42:    */     }
/*  43: 94 */     WritableWorkbookImpl w = getSheet().getWorkbook();
/*  44: 95 */     FormulaParser parser = new FormulaParser(formulaString, w, w, w.getSettings());
/*  45:    */     try
/*  46:    */     {
/*  47:101 */       parser.parse();
/*  48:    */     }
/*  49:    */     catch (FormulaException e2)
/*  50:    */     {
/*  51:105 */       logger.warn(e2.getMessage());
/*  52:    */     }
/*  53:107 */     byte[] formulaBytes = parser.getBytes();
/*  54:108 */     expressiondata = new byte[formulaBytes.length + 16];
/*  55:109 */     IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
/*  56:110 */     System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length); byte[] 
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:114 */       tmp160_157 = expressiondata;tmp160_157[8] = ((byte)(tmp160_157[8] | 0x2));
/*  61:    */     
/*  62:116 */     byte[] data = new byte[celldata.length + expressiondata.length];
/*  63:    */     
/*  64:118 */     System.arraycopy(celldata, 0, data, 0, celldata.length);
/*  65:119 */     System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
/*  66:    */     
/*  67:    */ 
/*  68:    */ 
/*  69:123 */     data[6] = 2;
/*  70:124 */     data[12] = -1;
/*  71:125 */     data[13] = -1;
/*  72:    */     
/*  73:    */ 
/*  74:128 */     data[8] = ((byte)errorCode);
/*  75:    */     
/*  76:130 */     return data;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.ReadErrorFormulaRecord
 * JD-Core Version:    0.7.0.1
 */