/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComTreeDisconnect
/*  4:   */   extends ServerMessageBlock
/*  5:   */ {
/*  6:   */   SmbComTreeDisconnect()
/*  7:   */   {
/*  8:24 */     this.command = 113;
/*  9:   */   }
/* 10:   */   
/* 11:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 12:   */   {
/* 13:28 */     return 0;
/* 14:   */   }
/* 15:   */   
/* 16:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 17:   */   {
/* 18:31 */     return 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 22:   */   {
/* 23:34 */     return 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 27:   */   {
/* 28:37 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toString()
/* 32:   */   {
/* 33:40 */     return new String("SmbComTreeDisconnect[" + super.toString() + "]");
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComTreeDisconnect
 * JD-Core Version:    0.7.0.1
 */