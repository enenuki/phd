/*  1:   */ package jxl.write.biff;
/*  2:   */ 
/*  3:   */ import jxl.biff.Type;
/*  4:   */ import jxl.biff.WritableRecordData;
/*  5:   */ 
/*  6:   */ class InterfaceEndRecord
/*  7:   */   extends WritableRecordData
/*  8:   */ {
/*  9:   */   public InterfaceEndRecord()
/* 10:   */   {
/* 11:36 */     super(Type.INTERFACEEND);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public byte[] getData()
/* 15:   */   {
/* 16:46 */     return new byte[0];
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.InterfaceEndRecord
 * JD-Core Version:    0.7.0.1
 */