/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.util.Date;
/*   5:    */ import jxl.CellType;
/*   6:    */ import jxl.DateCell;
/*   7:    */ import jxl.DateFormulaCell;
/*   8:    */ import jxl.biff.DoubleHelper;
/*   9:    */ import jxl.biff.FormattingRecords;
/*  10:    */ import jxl.biff.FormulaData;
/*  11:    */ import jxl.biff.IntegerHelper;
/*  12:    */ import jxl.biff.formula.FormulaException;
/*  13:    */ import jxl.biff.formula.FormulaParser;
/*  14:    */ 
/*  15:    */ public class SharedDateFormulaRecord
/*  16:    */   extends BaseSharedFormulaRecord
/*  17:    */   implements DateCell, FormulaData, DateFormulaCell
/*  18:    */ {
/*  19:    */   private DateRecord dateRecord;
/*  20:    */   private double value;
/*  21:    */   
/*  22:    */   public SharedDateFormulaRecord(SharedNumberFormulaRecord nfr, FormattingRecords fr, boolean nf, SheetImpl si, int pos)
/*  23:    */   {
/*  24: 69 */     super(nfr.getRecord(), fr, nfr.getExternalSheet(), nfr.getNameTable(), si, pos);
/*  25:    */     
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30: 75 */     this.dateRecord = new DateRecord(nfr, nfr.getXFIndex(), fr, nf, si);
/*  31: 76 */     this.value = nfr.getValue();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public double getValue()
/*  35:    */   {
/*  36: 86 */     return this.value;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getContents()
/*  40:    */   {
/*  41: 96 */     return this.dateRecord.getContents();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CellType getType()
/*  45:    */   {
/*  46:106 */     return CellType.DATE_FORMULA;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public byte[] getFormulaData()
/*  50:    */     throws FormulaException
/*  51:    */   {
/*  52:118 */     if (!getSheet().getWorkbookBof().isBiff8()) {
/*  53:120 */       throw new FormulaException(FormulaException.BIFF8_SUPPORTED);
/*  54:    */     }
/*  55:125 */     FormulaParser fp = new FormulaParser(getTokens(), this, getExternalSheet(), getNameTable(), getSheet().getWorkbook().getSettings());
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59:129 */     fp.parse();
/*  60:130 */     byte[] rpnTokens = fp.getBytes();
/*  61:    */     
/*  62:132 */     byte[] data = new byte[rpnTokens.length + 22];
/*  63:    */     
/*  64:    */ 
/*  65:135 */     IntegerHelper.getTwoBytes(getRow(), data, 0);
/*  66:136 */     IntegerHelper.getTwoBytes(getColumn(), data, 2);
/*  67:137 */     IntegerHelper.getTwoBytes(getXFIndex(), data, 4);
/*  68:138 */     DoubleHelper.getIEEEBytes(this.value, data, 6);
/*  69:    */     
/*  70:    */ 
/*  71:141 */     System.arraycopy(rpnTokens, 0, data, 22, rpnTokens.length);
/*  72:142 */     IntegerHelper.getTwoBytes(rpnTokens.length, data, 20);
/*  73:    */     
/*  74:    */ 
/*  75:145 */     byte[] d = new byte[data.length - 6];
/*  76:146 */     System.arraycopy(data, 6, d, 0, data.length - 6);
/*  77:    */     
/*  78:148 */     return d;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Date getDate()
/*  82:    */   {
/*  83:158 */     return this.dateRecord.getDate();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isTime()
/*  87:    */   {
/*  88:169 */     return this.dateRecord.isTime();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public DateFormat getDateFormat()
/*  92:    */   {
/*  93:182 */     return this.dateRecord.getDateFormat();
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.SharedDateFormulaRecord
 * JD-Core Version:    0.7.0.1
 */