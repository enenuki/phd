/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.LogStream;
/*  4:   */ 
/*  5:   */ abstract class SmbComNtTransactionResponse
/*  6:   */   extends SmbComTransactionResponse
/*  7:   */ {
/*  8:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/*  9:   */   {
/* 10:28 */     int start = bufferIndex;
/* 11:   */     
/* 12:30 */     buffer[(bufferIndex++)] = 0;
/* 13:31 */     buffer[(bufferIndex++)] = 0;
/* 14:32 */     buffer[(bufferIndex++)] = 0;
/* 15:   */     
/* 16:34 */     this.totalParameterCount = readInt4(buffer, bufferIndex);
/* 17:35 */     if (this.bufDataStart == 0) {
/* 18:36 */       this.bufDataStart = this.totalParameterCount;
/* 19:   */     }
/* 20:38 */     bufferIndex += 4;
/* 21:39 */     this.totalDataCount = readInt4(buffer, bufferIndex);
/* 22:40 */     bufferIndex += 4;
/* 23:41 */     this.parameterCount = readInt4(buffer, bufferIndex);
/* 24:42 */     bufferIndex += 4;
/* 25:43 */     this.parameterOffset = readInt4(buffer, bufferIndex);
/* 26:44 */     bufferIndex += 4;
/* 27:45 */     this.parameterDisplacement = readInt4(buffer, bufferIndex);
/* 28:46 */     bufferIndex += 4;
/* 29:47 */     this.dataCount = readInt4(buffer, bufferIndex);
/* 30:48 */     bufferIndex += 4;
/* 31:49 */     this.dataOffset = readInt4(buffer, bufferIndex);
/* 32:50 */     bufferIndex += 4;
/* 33:51 */     this.dataDisplacement = readInt4(buffer, bufferIndex);
/* 34:52 */     bufferIndex += 4;
/* 35:53 */     this.setupCount = (buffer[bufferIndex] & 0xFF);
/* 36:54 */     bufferIndex += 2;
/* 37:55 */     if ((this.setupCount != 0) && 
/* 38:56 */       (LogStream.level >= 3)) {
/* 39:57 */       log.println("setupCount is not zero: " + this.setupCount);
/* 40:   */     }
/* 41:60 */     return bufferIndex - start;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComNtTransactionResponse
 * JD-Core Version:    0.7.0.1
 */