/*  1:   */ package jxl.write.biff;
/*  2:   */ 
/*  3:   */ import jxl.biff.Type;
/*  4:   */ import jxl.biff.WritableRecordData;
/*  5:   */ 
/*  6:   */ class HorizontalCentreRecord
/*  7:   */   extends WritableRecordData
/*  8:   */ {
/*  9:   */   private byte[] data;
/* 10:   */   private boolean centre;
/* 11:   */   
/* 12:   */   public HorizontalCentreRecord(boolean ce)
/* 13:   */   {
/* 14:47 */     super(Type.HCENTER);
/* 15:   */     
/* 16:49 */     this.centre = ce;
/* 17:   */     
/* 18:51 */     this.data = new byte[2];
/* 19:53 */     if (this.centre) {
/* 20:55 */       this.data[0] = 1;
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public byte[] getData()
/* 25:   */   {
/* 26:66 */     return this.data;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.HorizontalCentreRecord
 * JD-Core Version:    0.7.0.1
 */