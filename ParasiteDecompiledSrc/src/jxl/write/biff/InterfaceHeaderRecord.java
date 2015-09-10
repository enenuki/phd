/*  1:   */ package jxl.write.biff;
/*  2:   */ 
/*  3:   */ import jxl.biff.Type;
/*  4:   */ import jxl.biff.WritableRecordData;
/*  5:   */ 
/*  6:   */ class InterfaceHeaderRecord
/*  7:   */   extends WritableRecordData
/*  8:   */ {
/*  9:   */   public InterfaceHeaderRecord()
/* 10:   */   {
/* 11:35 */     super(Type.INTERFACEHDR);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public byte[] getData()
/* 15:   */   {
/* 16:46 */     byte[] data = { -80, 4 };
/* 17:   */     
/* 18:48 */     return data;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.InterfaceHeaderRecord
 * JD-Core Version:    0.7.0.1
 */