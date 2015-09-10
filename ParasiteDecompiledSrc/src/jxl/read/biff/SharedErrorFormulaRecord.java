/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import jxl.CellType;
/*   4:    */ import jxl.ErrorCell;
/*   5:    */ import jxl.ErrorFormulaCell;
/*   6:    */ import jxl.biff.FormattingRecords;
/*   7:    */ import jxl.biff.FormulaData;
/*   8:    */ import jxl.biff.IntegerHelper;
/*   9:    */ import jxl.biff.WorkbookMethods;
/*  10:    */ import jxl.biff.formula.ExternalSheet;
/*  11:    */ import jxl.biff.formula.FormulaErrorCode;
/*  12:    */ import jxl.biff.formula.FormulaException;
/*  13:    */ import jxl.biff.formula.FormulaParser;
/*  14:    */ import jxl.common.Logger;
/*  15:    */ 
/*  16:    */ public class SharedErrorFormulaRecord
/*  17:    */   extends BaseSharedFormulaRecord
/*  18:    */   implements ErrorCell, FormulaData, ErrorFormulaCell
/*  19:    */ {
/*  20: 46 */   private static Logger logger = Logger.getLogger(SharedErrorFormulaRecord.class);
/*  21:    */   private int errorCode;
/*  22:    */   private byte[] data;
/*  23:    */   private FormulaErrorCode error;
/*  24:    */   
/*  25:    */   public SharedErrorFormulaRecord(Record t, File excelFile, int ec, FormattingRecords fr, ExternalSheet es, WorkbookMethods nt, SheetImpl si)
/*  26:    */   {
/*  27: 83 */     super(t, fr, es, nt, si, excelFile.getPos());
/*  28: 84 */     this.errorCode = ec;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getErrorCode()
/*  32:    */   {
/*  33: 96 */     return this.errorCode;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getContents()
/*  37:    */   {
/*  38:106 */     if (this.error == null) {
/*  39:108 */       this.error = FormulaErrorCode.getErrorCode(this.errorCode);
/*  40:    */     }
/*  41:111 */     return "ERROR " + this.errorCode;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CellType getType()
/*  45:    */   {
/*  46:122 */     return CellType.FORMULA_ERROR;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public byte[] getFormulaData()
/*  50:    */     throws FormulaException
/*  51:    */   {
/*  52:134 */     if (!getSheet().getWorkbookBof().isBiff8()) {
/*  53:136 */       throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
/*  54:    */     }
/*  55:141 */     FormulaParser fp = new FormulaParser(getTokens(), this, getExternalSheet(), getNameTable(), getSheet().getWorkbook().getSettings());
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59:145 */     fp.parse();
/*  60:146 */     byte[] rpnTokens = fp.getBytes();
/*  61:    */     
/*  62:148 */     byte[] data = new byte[rpnTokens.length + 22];
/*  63:    */     
/*  64:    */ 
/*  65:151 */     IntegerHelper.getTwoBytes(getRow(), data, 0);
/*  66:152 */     IntegerHelper.getTwoBytes(getColumn(), data, 2);
/*  67:153 */     IntegerHelper.getTwoBytes(getXFIndex(), data, 4);
/*  68:    */     
/*  69:155 */     data[6] = 2;
/*  70:156 */     data[8] = ((byte)this.errorCode);
/*  71:157 */     data[12] = -1;
/*  72:158 */     data[13] = -1;
/*  73:    */     
/*  74:    */ 
/*  75:161 */     System.arraycopy(rpnTokens, 0, data, 22, rpnTokens.length);
/*  76:162 */     IntegerHelper.getTwoBytes(rpnTokens.length, data, 20);
/*  77:    */     
/*  78:    */ 
/*  79:165 */     byte[] d = new byte[data.length - 6];
/*  80:166 */     System.arraycopy(data, 6, d, 0, data.length - 6);
/*  81:    */     
/*  82:168 */     return d;
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.SharedErrorFormulaRecord
 * JD-Core Version:    0.7.0.1
 */