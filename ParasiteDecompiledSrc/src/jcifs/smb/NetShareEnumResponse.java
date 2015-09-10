/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.LogStream;
/*  4:   */ 
/*  5:   */ class NetShareEnumResponse
/*  6:   */   extends SmbComTransactionResponse
/*  7:   */ {
/*  8:   */   private int converter;
/*  9:   */   private int totalAvailableEntries;
/* 10:   */   
/* 11:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 12:   */   {
/* 13:33 */     return 0;
/* 14:   */   }
/* 15:   */   
/* 16:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 17:   */   {
/* 18:36 */     return 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 22:   */   {
/* 23:39 */     return 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 27:   */   {
/* 28:42 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 32:   */   {
/* 33:45 */     int start = bufferIndex;
/* 34:   */     
/* 35:47 */     this.status = readInt2(buffer, bufferIndex);
/* 36:48 */     bufferIndex += 2;
/* 37:49 */     this.converter = readInt2(buffer, bufferIndex);
/* 38:50 */     bufferIndex += 2;
/* 39:51 */     this.numEntries = readInt2(buffer, bufferIndex);
/* 40:52 */     bufferIndex += 2;
/* 41:53 */     this.totalAvailableEntries = readInt2(buffer, bufferIndex);
/* 42:54 */     bufferIndex += 2;
/* 43:   */     
/* 44:56 */     return bufferIndex - start;
/* 45:   */   }
/* 46:   */   
/* 47:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 48:   */   {
/* 49:59 */     int start = bufferIndex;
/* 50:   */     
/* 51:   */ 
/* 52:62 */     this.useUnicode = false;
/* 53:   */     
/* 54:64 */     this.results = new SmbShareInfo[this.numEntries];
/* 55:65 */     for (int i = 0; i < this.numEntries; i++)
/* 56:   */     {
/* 57:66 */       void tmp44_41 = new SmbShareInfo();SmbShareInfo e = tmp44_41;this.results[i] = tmp44_41;
/* 58:67 */       e.netName = readString(buffer, bufferIndex, 13, false);
/* 59:68 */       bufferIndex += 14;
/* 60:69 */       e.type = readInt2(buffer, bufferIndex);
/* 61:70 */       bufferIndex += 2;
/* 62:71 */       int off = readInt4(buffer, bufferIndex);
/* 63:72 */       bufferIndex += 4;
/* 64:73 */       off = (off & 0xFFFF) - this.converter;
/* 65:74 */       off = start + off;
/* 66:75 */       e.remark = readString(buffer, off, 128, false);
/* 67:77 */       if (LogStream.level >= 4) {
/* 68:78 */         log.println(e);
/* 69:   */       }
/* 70:   */     }
/* 71:81 */     return bufferIndex - start;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public String toString()
/* 75:   */   {
/* 76:84 */     return new String("NetShareEnumResponse[" + super.toString() + ",status=" + this.status + ",converter=" + this.converter + ",entriesReturned=" + this.numEntries + ",totalAvailableEntries=" + this.totalAvailableEntries + "]");
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NetShareEnumResponse
 * JD-Core Version:    0.7.0.1
 */