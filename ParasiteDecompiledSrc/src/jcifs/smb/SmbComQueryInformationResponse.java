/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.util.Date;
/*  4:   */ import jcifs.util.Hexdump;
/*  5:   */ 
/*  6:   */ class SmbComQueryInformationResponse
/*  7:   */   extends ServerMessageBlock
/*  8:   */   implements Info
/*  9:   */ {
/* 10:26 */   private int fileAttributes = 0;
/* 11:27 */   private long lastWriteTime = 0L;
/* 12:   */   private long serverTimeZoneOffset;
/* 13:29 */   private int fileSize = 0;
/* 14:   */   
/* 15:   */   SmbComQueryInformationResponse(long serverTimeZoneOffset)
/* 16:   */   {
/* 17:32 */     this.serverTimeZoneOffset = serverTimeZoneOffset;
/* 18:33 */     this.command = 8;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getAttributes()
/* 22:   */   {
/* 23:37 */     return this.fileAttributes;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public long getCreateTime()
/* 27:   */   {
/* 28:40 */     return this.lastWriteTime + this.serverTimeZoneOffset;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public long getLastWriteTime()
/* 32:   */   {
/* 33:43 */     return this.lastWriteTime + this.serverTimeZoneOffset;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public long getSize()
/* 37:   */   {
/* 38:46 */     return this.fileSize;
/* 39:   */   }
/* 40:   */   
/* 41:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 42:   */   {
/* 43:49 */     return 0;
/* 44:   */   }
/* 45:   */   
/* 46:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 47:   */   {
/* 48:52 */     return 0;
/* 49:   */   }
/* 50:   */   
/* 51:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 52:   */   {
/* 53:55 */     if (this.wordCount == 0) {
/* 54:56 */       return 0;
/* 55:   */     }
/* 56:58 */     this.fileAttributes = readInt2(buffer, bufferIndex);
/* 57:59 */     bufferIndex += 2;
/* 58:60 */     this.lastWriteTime = readUTime(buffer, bufferIndex);
/* 59:61 */     bufferIndex += 4;
/* 60:62 */     this.fileSize = readInt4(buffer, bufferIndex);
/* 61:63 */     return 20;
/* 62:   */   }
/* 63:   */   
/* 64:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 65:   */   {
/* 66:66 */     return 0;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public String toString()
/* 70:   */   {
/* 71:69 */     return new String("SmbComQueryInformationResponse[" + super.toString() + ",fileAttributes=0x" + Hexdump.toHexString(this.fileAttributes, 4) + ",lastWriteTime=" + new Date(this.lastWriteTime) + ",fileSize=" + this.fileSize + "]");
/* 72:   */   }
/* 73:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComQueryInformationResponse
 * JD-Core Version:    0.7.0.1
 */