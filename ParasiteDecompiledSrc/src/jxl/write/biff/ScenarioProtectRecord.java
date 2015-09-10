/*  1:   */ package jxl.write.biff;
/*  2:   */ 
/*  3:   */ import jxl.biff.IntegerHelper;
/*  4:   */ import jxl.biff.Type;
/*  5:   */ import jxl.biff.WritableRecordData;
/*  6:   */ 
/*  7:   */ class ScenarioProtectRecord
/*  8:   */   extends WritableRecordData
/*  9:   */ {
/* 10:   */   private boolean protection;
/* 11:   */   private byte[] data;
/* 12:   */   
/* 13:   */   public ScenarioProtectRecord(boolean prot)
/* 14:   */   {
/* 15:47 */     super(Type.SCENPROTECT);
/* 16:   */     
/* 17:49 */     this.protection = prot;
/* 18:   */     
/* 19:51 */     this.data = new byte[2];
/* 20:53 */     if (this.protection) {
/* 21:55 */       IntegerHelper.getTwoBytes(1, this.data, 0);
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public byte[] getData()
/* 26:   */   {
/* 27:66 */     return this.data;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.ScenarioProtectRecord
 * JD-Core Version:    0.7.0.1
 */