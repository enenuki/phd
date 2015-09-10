/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComWriteAndXResponse
/*  4:   */   extends AndXServerMessageBlock
/*  5:   */ {
/*  6:   */   long count;
/*  7:   */   
/*  8:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/*  9:   */   {
/* 10:29 */     return 0;
/* 11:   */   }
/* 12:   */   
/* 13:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 14:   */   {
/* 15:32 */     return 0;
/* 16:   */   }
/* 17:   */   
/* 18:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 19:   */   {
/* 20:35 */     this.count = (readInt2(buffer, bufferIndex) & 0xFFFF);
/* 21:36 */     return 8;
/* 22:   */   }
/* 23:   */   
/* 24:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 25:   */   {
/* 26:39 */     return 0;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String toString()
/* 30:   */   {
/* 31:42 */     return new String("SmbComWriteAndXResponse[" + super.toString() + ",count=" + this.count + "]");
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComWriteAndXResponse
 * JD-Core Version:    0.7.0.1
 */