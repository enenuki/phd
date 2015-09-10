/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.util.Date;
/*  4:   */ import jcifs.util.Hexdump;
/*  5:   */ 
/*  6:   */ class SmbComNTCreateAndXResponse
/*  7:   */   extends AndXServerMessageBlock
/*  8:   */ {
/*  9:   */   static final int EXCLUSIVE_OPLOCK_GRANTED = 1;
/* 10:   */   static final int BATCH_OPLOCK_GRANTED = 2;
/* 11:   */   static final int LEVEL_II_OPLOCK_GRANTED = 3;
/* 12:   */   byte oplockLevel;
/* 13:   */   int fid;
/* 14:   */   int createAction;
/* 15:   */   int extFileAttributes;
/* 16:   */   int fileType;
/* 17:   */   int deviceState;
/* 18:   */   long creationTime;
/* 19:   */   long lastAccessTime;
/* 20:   */   long lastWriteTime;
/* 21:   */   long changeTime;
/* 22:   */   long allocationSize;
/* 23:   */   long endOfFile;
/* 24:   */   boolean directory;
/* 25:   */   boolean isExtended;
/* 26:   */   
/* 27:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 28:   */   {
/* 29:49 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 33:   */   {
/* 34:52 */     return 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 38:   */   {
/* 39:55 */     int start = bufferIndex;
/* 40:   */     
/* 41:57 */     this.oplockLevel = buffer[(bufferIndex++)];
/* 42:58 */     this.fid = readInt2(buffer, bufferIndex);
/* 43:59 */     bufferIndex += 2;
/* 44:60 */     this.createAction = readInt4(buffer, bufferIndex);
/* 45:61 */     bufferIndex += 4;
/* 46:62 */     this.creationTime = readTime(buffer, bufferIndex);
/* 47:63 */     bufferIndex += 8;
/* 48:64 */     this.lastAccessTime = readTime(buffer, bufferIndex);
/* 49:65 */     bufferIndex += 8;
/* 50:66 */     this.lastWriteTime = readTime(buffer, bufferIndex);
/* 51:67 */     bufferIndex += 8;
/* 52:68 */     this.changeTime = readTime(buffer, bufferIndex);
/* 53:69 */     bufferIndex += 8;
/* 54:70 */     this.extFileAttributes = readInt4(buffer, bufferIndex);
/* 55:71 */     bufferIndex += 4;
/* 56:72 */     this.allocationSize = readInt8(buffer, bufferIndex);
/* 57:73 */     bufferIndex += 8;
/* 58:74 */     this.endOfFile = readInt8(buffer, bufferIndex);
/* 59:75 */     bufferIndex += 8;
/* 60:76 */     this.fileType = readInt2(buffer, bufferIndex);
/* 61:77 */     bufferIndex += 2;
/* 62:78 */     this.deviceState = readInt2(buffer, bufferIndex);
/* 63:79 */     bufferIndex += 2;
/* 64:80 */     this.directory = ((buffer[(bufferIndex++)] & 0xFF) > 0);
/* 65:   */     
/* 66:82 */     return bufferIndex - start;
/* 67:   */   }
/* 68:   */   
/* 69:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 70:   */   {
/* 71:85 */     return 0;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public String toString()
/* 75:   */   {
/* 76:88 */     return new String("SmbComNTCreateAndXResponse[" + super.toString() + ",oplockLevel=" + this.oplockLevel + ",fid=" + this.fid + ",createAction=0x" + Hexdump.toHexString(this.createAction, 4) + ",creationTime=" + new Date(this.creationTime) + ",lastAccessTime=" + new Date(this.lastAccessTime) + ",lastWriteTime=" + new Date(this.lastWriteTime) + ",changeTime=" + new Date(this.changeTime) + ",extFileAttributes=0x" + Hexdump.toHexString(this.extFileAttributes, 4) + ",allocationSize=" + this.allocationSize + ",endOfFile=" + this.endOfFile + ",fileType=" + this.fileType + ",deviceState=" + this.deviceState + ",directory=" + this.directory + "]");
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComNTCreateAndXResponse
 * JD-Core Version:    0.7.0.1
 */