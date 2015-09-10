/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import java.text.NumberFormat;
/*   4:    */ import jxl.NumberFormulaCell;
/*   5:    */ import jxl.biff.DoubleHelper;
/*   6:    */ import jxl.biff.FormulaData;
/*   7:    */ import jxl.biff.IntegerHelper;
/*   8:    */ import jxl.biff.formula.FormulaException;
/*   9:    */ import jxl.biff.formula.FormulaParser;
/*  10:    */ import jxl.common.Logger;
/*  11:    */ 
/*  12:    */ class ReadNumberFormulaRecord
/*  13:    */   extends ReadFormulaRecord
/*  14:    */   implements NumberFormulaCell
/*  15:    */ {
/*  16: 40 */   private static Logger logger = Logger.getLogger(ReadNumberFormulaRecord.class);
/*  17:    */   
/*  18:    */   public ReadNumberFormulaRecord(FormulaData f)
/*  19:    */   {
/*  20: 49 */     super(f);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public double getValue()
/*  24:    */   {
/*  25: 59 */     return ((NumberFormulaCell)getReadFormula()).getValue();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public NumberFormat getNumberFormat()
/*  29:    */   {
/*  30: 70 */     return ((NumberFormulaCell)getReadFormula()).getNumberFormat();
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected byte[] handleFormulaException()
/*  34:    */   {
/*  35: 82 */     byte[] expressiondata = null;
/*  36: 83 */     byte[] celldata = super.getCellData();
/*  37:    */     
/*  38:    */ 
/*  39: 86 */     WritableWorkbookImpl w = getSheet().getWorkbook();
/*  40: 87 */     FormulaParser parser = new FormulaParser(Double.toString(getValue()), w, w, w.getSettings());
/*  41:    */     try
/*  42:    */     {
/*  43: 93 */       parser.parse();
/*  44:    */     }
/*  45:    */     catch (FormulaException e2)
/*  46:    */     {
/*  47: 97 */       logger.warn(e2.getMessage());
/*  48:    */     }
/*  49: 99 */     byte[] formulaBytes = parser.getBytes();
/*  50:100 */     expressiondata = new byte[formulaBytes.length + 16];
/*  51:101 */     IntegerHelper.getTwoBytes(formulaBytes.length, expressiondata, 14);
/*  52:102 */     System.arraycopy(formulaBytes, 0, expressiondata, 16, formulaBytes.length); byte[] 
/*  53:    */     
/*  54:    */ 
/*  55:    */ 
/*  56:106 */       tmp98_95 = expressiondata;tmp98_95[8] = ((byte)(tmp98_95[8] | 0x2));
/*  57:    */     
/*  58:108 */     byte[] data = new byte[celldata.length + expressiondata.length];
/*  59:    */     
/*  60:110 */     System.arraycopy(celldata, 0, data, 0, celldata.length);
/*  61:111 */     System.arraycopy(expressiondata, 0, data, celldata.length, expressiondata.length);
/*  62:    */     
/*  63:    */ 
/*  64:    */ 
/*  65:115 */     DoubleHelper.getIEEEBytes(getValue(), data, 6);
/*  66:    */     
/*  67:117 */     return data;
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.ReadNumberFormulaRecord
 * JD-Core Version:    0.7.0.1
 */