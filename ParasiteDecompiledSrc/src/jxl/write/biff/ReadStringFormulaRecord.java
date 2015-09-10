/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.StringFormulaCell;
/*   4:    */ import jxl.biff.FormulaData;
/*   5:    */ import jxl.biff.IntegerHelper;
/*   6:    */ import jxl.biff.formula.FormulaException;
/*   7:    */ import jxl.biff.formula.FormulaParser;
/*   8:    */ import jxl.common.Assert;
/*   9:    */ import jxl.common.Logger;
/*  10:    */ 
/*  11:    */ class ReadStringFormulaRecord
/*  12:    */   extends ReadFormulaRecord
/*  13:    */   implements StringFormulaCell
/*  14:    */ {
/*  15: 38 */   private static Logger logger = Logger.getLogger(ReadFormulaRecord.class);
/*  16:    */   
/*  17:    */   public ReadStringFormulaRecord(FormulaData f)
/*  18:    */   {
/*  19: 47 */     super(f);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getString()
/*  23:    */   {
/*  24: 57 */     return ((StringFormulaCell)getReadFormula()).getString();
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected byte[] handleFormulaException()
/*  28:    */   {
/*  29: 69 */     byte[] expressiondata = null;
/*  30: 70 */     byte[] celldata = super.getCellData();
/*  31:    */     
/*  32:    */ 
/*  33: 73 */     WritableWorkbookImpl w = getSheet().getWorkbook();
/*  34: 74 */     FormulaParser parser = new FormulaParser("\"" + getContents() + "\"", w, w, w.getSettings());
/*  35:    */     try
/*  36:    */     {
/*  37: 80 */       parser.parse();
/*  38:    */     }
/*  39:    */     catch (FormulaException e2)
/*  40:    */     {
/*  41: 84 */       logger.warn(e2.getMessage());
/*  42: 85 */       parser = new FormulaParser("\"ERROR\"", w, w, w.getSettings());
/*  43:    */       try
/*  44:    */       {
/*  45: 86 */         parser.parse();
/*  46:    */       }
/*  47:    */       catch (FormulaException e3)
/*  48:    */       {
/*  49: 87 */         Assert.verify(false);
/*  50:    */       }
/*  51:    */     }
/*  52: 89 */     byte[] formulaBytes = parser.getBytes();
/*  53: 90 */     expressiondata = new byte[formulaBytes.length + 16];
/*  54: 91 */     IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
/*  55: 92 */     System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length); byte[] 
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59: 96 */       tmp149_146 = expressiondata;tmp149_146[8] = ((byte)(tmp149_146[8] | 0x2));
/*  60:    */     
/*  61: 98 */     byte[] data = new byte[celldata.length + expressiondata.length];
/*  62:    */     
/*  63:100 */     System.arraycopy(celldata, 0, data, 0, celldata.length);
/*  64:101 */     System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
/*  65:    */     
/*  66:    */ 
/*  67:    */ 
/*  68:105 */     data[6] = 0;
/*  69:106 */     data[12] = -1;
/*  70:107 */     data[13] = -1;
/*  71:    */     
/*  72:109 */     return data;
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.ReadStringFormulaRecord
 * JD-Core Version:    0.7.0.1
 */