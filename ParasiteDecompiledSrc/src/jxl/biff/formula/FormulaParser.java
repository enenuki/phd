/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import jxl.Cell;
/*   4:    */ import jxl.WorkbookSettings;
/*   5:    */ import jxl.biff.WorkbookMethods;
/*   6:    */ import jxl.common.Assert;
/*   7:    */ import jxl.common.Logger;
/*   8:    */ import jxl.read.biff.BOFRecord;
/*   9:    */ 
/*  10:    */ public class FormulaParser
/*  11:    */ {
/*  12: 38 */   private static final Logger logger = Logger.getLogger(FormulaParser.class);
/*  13:    */   private Parser parser;
/*  14:    */   
/*  15:    */   public FormulaParser(byte[] tokens, Cell rt, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws)
/*  16:    */     throws FormulaException
/*  17:    */   {
/*  18: 66 */     if ((es.getWorkbookBof() != null) && (!es.getWorkbookBof().isBiff8())) {
/*  19: 69 */       throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
/*  20:    */     }
/*  21: 71 */     Assert.verify(nt != null);
/*  22: 72 */     this.parser = new TokenFormulaParser(tokens, rt, es, nt, ws, ParseContext.DEFAULT);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public FormulaParser(byte[] tokens, Cell rt, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws, ParseContext pc)
/*  26:    */     throws FormulaException
/*  27:    */   {
/*  28: 97 */     if ((es.getWorkbookBof() != null) && (!es.getWorkbookBof().isBiff8())) {
/*  29:100 */       throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
/*  30:    */     }
/*  31:102 */     Assert.verify(nt != null);
/*  32:103 */     this.parser = new TokenFormulaParser(tokens, rt, es, nt, ws, pc);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public FormulaParser(String form, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws)
/*  36:    */   {
/*  37:119 */     this.parser = new StringFormulaParser(form, es, nt, ws, ParseContext.DEFAULT);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public FormulaParser(String form, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws, ParseContext pc)
/*  41:    */   {
/*  42:138 */     this.parser = new StringFormulaParser(form, es, nt, ws, pc);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void adjustRelativeCellReferences(int colAdjust, int rowAdjust)
/*  46:    */   {
/*  47:151 */     this.parser.adjustRelativeCellReferences(colAdjust, rowAdjust);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void parse()
/*  51:    */     throws FormulaException
/*  52:    */   {
/*  53:161 */     this.parser.parse();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getFormula()
/*  57:    */     throws FormulaException
/*  58:    */   {
/*  59:172 */     return this.parser.getFormula();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public byte[] getBytes()
/*  63:    */   {
/*  64:183 */     return this.parser.getBytes();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void columnInserted(int sheetIndex, int col, boolean currentSheet)
/*  68:    */   {
/*  69:198 */     this.parser.columnInserted(sheetIndex, col, currentSheet);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void columnRemoved(int sheetIndex, int col, boolean currentSheet)
/*  73:    */   {
/*  74:213 */     this.parser.columnRemoved(sheetIndex, col, currentSheet);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void rowInserted(int sheetIndex, int row, boolean currentSheet)
/*  78:    */   {
/*  79:228 */     this.parser.rowInserted(sheetIndex, row, currentSheet);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void rowRemoved(int sheetIndex, int row, boolean currentSheet)
/*  83:    */   {
/*  84:243 */     this.parser.rowRemoved(sheetIndex, row, currentSheet);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean handleImportedCellReferences()
/*  88:    */   {
/*  89:254 */     return this.parser.handleImportedCellReferences();
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.FormulaParser
 * JD-Core Version:    0.7.0.1
 */