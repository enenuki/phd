/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComReadAndXResponse
/*  4:   */   extends AndXServerMessageBlock
/*  5:   */ {
/*  6:   */   byte[] b;
/*  7:   */   int off;
/*  8:   */   int dataCompactionMode;
/*  9:   */   int dataLength;
/* 10:   */   int dataOffset;
/* 11:   */   
/* 12:   */   SmbComReadAndXResponse() {}
/* 13:   */   
/* 14:   */   SmbComReadAndXResponse(byte[] b, int off)
/* 15:   */   {
/* 16:29 */     this.b = b;
/* 17:30 */     this.off = off;
/* 18:   */   }
/* 19:   */   
/* 20:   */   void setParam(byte[] b, int off)
/* 21:   */   {
/* 22:34 */     this.b = b;
/* 23:35 */     this.off = off;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 27:   */   {
/* 28:38 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 32:   */   {
/* 33:41 */     return 0;
/* 34:   */   }
/* 35:   */   
/* 36:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 37:   */   {
/* 38:44 */     int start = bufferIndex;
/* 39:   */     
/* 40:46 */     bufferIndex += 2;
/* 41:47 */     this.dataCompactionMode = readInt2(buffer, bufferIndex);
/* 42:48 */     bufferIndex += 4;
/* 43:49 */     this.dataLength = readInt2(buffer, bufferIndex);
/* 44:50 */     bufferIndex += 2;
/* 45:51 */     this.dataOffset = readInt2(buffer, bufferIndex);
/* 46:52 */     bufferIndex += 12;
/* 47:   */     
/* 48:54 */     return bufferIndex - start;
/* 49:   */   }
/* 50:   */   
/* 51:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 52:   */   {
/* 53:58 */     return 0;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String toString()
/* 57:   */   {
/* 58:61 */     return new String("SmbComReadAndXResponse[" + super.toString() + ",dataCompactionMode=" + this.dataCompactionMode + ",dataLength=" + this.dataLength + ",dataOffset=" + this.dataOffset + "]");
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComReadAndXResponse
 * JD-Core Version:    0.7.0.1
 */