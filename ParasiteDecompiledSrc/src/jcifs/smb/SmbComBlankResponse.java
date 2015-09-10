/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComBlankResponse
/*  4:   */   extends ServerMessageBlock
/*  5:   */ {
/*  6:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/*  7:   */   {
/*  8:27 */     return 0;
/*  9:   */   }
/* 10:   */   
/* 11:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 12:   */   {
/* 13:30 */     return 0;
/* 14:   */   }
/* 15:   */   
/* 16:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 17:   */   {
/* 18:33 */     return 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 22:   */   {
/* 23:36 */     return 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String toString()
/* 27:   */   {
/* 28:39 */     return new String("SmbComBlankResponse[" + super.toString() + "]");
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComBlankResponse
 * JD-Core Version:    0.7.0.1
 */