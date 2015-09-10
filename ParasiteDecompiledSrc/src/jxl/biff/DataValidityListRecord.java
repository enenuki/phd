/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.common.Logger;
/*   4:    */ import jxl.read.biff.Record;
/*   5:    */ 
/*   6:    */ public class DataValidityListRecord
/*   7:    */   extends WritableRecordData
/*   8:    */ {
/*   9: 30 */   private static Logger logger = Logger.getLogger(DataValidityListRecord.class);
/*  10:    */   private int numSettings;
/*  11:    */   private int objectId;
/*  12:    */   private DValParser dvalParser;
/*  13:    */   private byte[] data;
/*  14:    */   
/*  15:    */   public DataValidityListRecord(Record t)
/*  16:    */   {
/*  17: 58 */     super(t);
/*  18:    */     
/*  19: 60 */     this.data = getRecord().getData();
/*  20: 61 */     this.objectId = IntegerHelper.getInt(this.data[10], this.data[11], this.data[12], this.data[13]);
/*  21: 62 */     this.numSettings = IntegerHelper.getInt(this.data[14], this.data[15], this.data[16], this.data[17]);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public DataValidityListRecord(DValParser dval)
/*  25:    */   {
/*  26: 70 */     super(Type.DVAL);
/*  27:    */     
/*  28: 72 */     this.dvalParser = dval;
/*  29:    */   }
/*  30:    */   
/*  31:    */   DataValidityListRecord(DataValidityListRecord dvlr)
/*  32:    */   {
/*  33: 82 */     super(Type.DVAL);
/*  34:    */     
/*  35: 84 */     this.data = dvlr.getData();
/*  36:    */   }
/*  37:    */   
/*  38:    */   int getNumberOfSettings()
/*  39:    */   {
/*  40: 92 */     return this.numSettings;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public byte[] getData()
/*  44:    */   {
/*  45:102 */     if (this.dvalParser == null) {
/*  46:104 */       return this.data;
/*  47:    */     }
/*  48:107 */     return this.dvalParser.getData();
/*  49:    */   }
/*  50:    */   
/*  51:    */   void dvRemoved()
/*  52:    */   {
/*  53:116 */     if (this.dvalParser == null) {
/*  54:118 */       this.dvalParser = new DValParser(this.data);
/*  55:    */     }
/*  56:121 */     this.dvalParser.dvRemoved();
/*  57:    */   }
/*  58:    */   
/*  59:    */   void dvAdded()
/*  60:    */   {
/*  61:129 */     if (this.dvalParser == null) {
/*  62:131 */       this.dvalParser = new DValParser(this.data);
/*  63:    */     }
/*  64:134 */     this.dvalParser.dvAdded();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean hasDVRecords()
/*  68:    */   {
/*  69:144 */     if (this.dvalParser == null) {
/*  70:146 */       return true;
/*  71:    */     }
/*  72:149 */     return this.dvalParser.getNumberOfDVRecords() > 0;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getObjectId()
/*  76:    */   {
/*  77:159 */     if (this.dvalParser == null) {
/*  78:161 */       return this.objectId;
/*  79:    */     }
/*  80:164 */     return this.dvalParser.getObjectId();
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.DataValidityListRecord
 * JD-Core Version:    0.7.0.1
 */