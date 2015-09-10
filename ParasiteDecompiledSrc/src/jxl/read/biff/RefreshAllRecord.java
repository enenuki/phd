/*  1:   */ package jxl.read.biff;
/*  2:   */ 
/*  3:   */ import jxl.biff.IntegerHelper;
/*  4:   */ import jxl.biff.RecordData;
/*  5:   */ import jxl.common.Logger;
/*  6:   */ 
/*  7:   */ class RefreshAllRecord
/*  8:   */   extends RecordData
/*  9:   */ {
/* 10:34 */   private static Logger logger = Logger.getLogger(RefreshAllRecord.class);
/* 11:   */   private boolean refreshAll;
/* 12:   */   
/* 13:   */   public RefreshAllRecord(Record t)
/* 14:   */   {
/* 15:48 */     super(t);
/* 16:49 */     byte[] data = t.getData();
/* 17:50 */     int mode = IntegerHelper.getInt(data[0], data[1]);
/* 18:51 */     this.refreshAll = (mode == 1);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean getRefreshAll()
/* 22:   */   {
/* 23:61 */     return this.refreshAll;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.RefreshAllRecord
 * JD-Core Version:    0.7.0.1
 */