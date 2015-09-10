/*  1:   */ package jxl.biff;
/*  2:   */ 
/*  3:   */ import jxl.common.Logger;
/*  4:   */ import jxl.read.biff.Record;
/*  5:   */ 
/*  6:   */ public class AutoFilterRecord
/*  7:   */   extends WritableRecordData
/*  8:   */ {
/*  9:32 */   private static Logger logger = Logger.getLogger(AutoFilterRecord.class);
/* 10:   */   private byte[] data;
/* 11:   */   
/* 12:   */   public AutoFilterRecord(Record t)
/* 13:   */   {
/* 14:45 */     super(t);
/* 15:   */     
/* 16:47 */     this.data = getRecord().getData();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public byte[] getData()
/* 20:   */   {
/* 21:57 */     return this.data;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.AutoFilterRecord
 * JD-Core Version:    0.7.0.1
 */