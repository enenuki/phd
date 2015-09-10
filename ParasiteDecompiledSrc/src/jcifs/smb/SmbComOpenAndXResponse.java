/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComOpenAndXResponse
/*  4:   */   extends AndXServerMessageBlock
/*  5:   */ {
/*  6:   */   int fid;
/*  7:   */   int fileAttributes;
/*  8:   */   int dataSize;
/*  9:   */   int grantedAccess;
/* 10:   */   int fileType;
/* 11:   */   int deviceState;
/* 12:   */   int action;
/* 13:   */   int serverFid;
/* 14:   */   long lastWriteTime;
/* 15:   */   
/* 16:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 17:   */   {
/* 18:40 */     return 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 22:   */   {
/* 23:43 */     return 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 27:   */   {
/* 28:46 */     int start = bufferIndex;
/* 29:   */     
/* 30:48 */     this.fid = readInt2(buffer, bufferIndex);
/* 31:49 */     bufferIndex += 2;
/* 32:50 */     this.fileAttributes = readInt2(buffer, bufferIndex);
/* 33:51 */     bufferIndex += 2;
/* 34:52 */     this.lastWriteTime = readUTime(buffer, bufferIndex);
/* 35:53 */     bufferIndex += 4;
/* 36:54 */     this.dataSize = readInt4(buffer, bufferIndex);
/* 37:55 */     bufferIndex += 4;
/* 38:56 */     this.grantedAccess = readInt2(buffer, bufferIndex);
/* 39:57 */     bufferIndex += 2;
/* 40:58 */     this.fileType = readInt2(buffer, bufferIndex);
/* 41:59 */     bufferIndex += 2;
/* 42:60 */     this.deviceState = readInt2(buffer, bufferIndex);
/* 43:61 */     bufferIndex += 2;
/* 44:62 */     this.action = readInt2(buffer, bufferIndex);
/* 45:63 */     bufferIndex += 2;
/* 46:64 */     this.serverFid = readInt4(buffer, bufferIndex);
/* 47:65 */     bufferIndex += 6;
/* 48:   */     
/* 49:67 */     return bufferIndex - start;
/* 50:   */   }
/* 51:   */   
/* 52:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 53:   */   {
/* 54:70 */     return 0;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String toString()
/* 58:   */   {
/* 59:73 */     return new String("SmbComOpenAndXResponse[" + super.toString() + ",fid=" + this.fid + ",fileAttributes=" + this.fileAttributes + ",lastWriteTime=" + this.lastWriteTime + ",dataSize=" + this.dataSize + ",grantedAccess=" + this.grantedAccess + ",fileType=" + this.fileType + ",deviceState=" + this.deviceState + ",action=" + this.action + ",serverFid=" + this.serverFid + "]");
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComOpenAndXResponse
 * JD-Core Version:    0.7.0.1
 */