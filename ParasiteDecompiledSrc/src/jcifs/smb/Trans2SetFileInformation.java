/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class Trans2SetFileInformation
/*  4:   */   extends SmbComTransaction
/*  5:   */ {
/*  6:   */   static final int SMB_FILE_BASIC_INFO = 257;
/*  7:   */   private int fid;
/*  8:   */   private int attributes;
/*  9:   */   private long createTime;
/* 10:   */   private long lastWriteTime;
/* 11:   */   
/* 12:   */   Trans2SetFileInformation(int fid, int attributes, long createTime, long lastWriteTime)
/* 13:   */   {
/* 14:30 */     this.fid = fid;
/* 15:31 */     this.attributes = attributes;
/* 16:32 */     this.createTime = createTime;
/* 17:33 */     this.lastWriteTime = lastWriteTime;
/* 18:34 */     this.command = 50;
/* 19:35 */     this.subCommand = 8;
/* 20:36 */     this.maxParameterCount = 6;
/* 21:37 */     this.maxDataCount = 0;
/* 22:38 */     this.maxSetupCount = 0;
/* 23:   */   }
/* 24:   */   
/* 25:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 26:   */   {
/* 27:42 */     dst[(dstIndex++)] = this.subCommand;
/* 28:43 */     dst[(dstIndex++)] = 0;
/* 29:44 */     return 2;
/* 30:   */   }
/* 31:   */   
/* 32:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 33:   */   {
/* 34:47 */     int start = dstIndex;
/* 35:   */     
/* 36:49 */     writeInt2(this.fid, dst, dstIndex);
/* 37:50 */     dstIndex += 2;
/* 38:51 */     writeInt2(257L, dst, dstIndex);
/* 39:52 */     dstIndex += 2;
/* 40:53 */     writeInt2(0L, dst, dstIndex);
/* 41:54 */     dstIndex += 2;
/* 42:   */     
/* 43:56 */     return dstIndex - start;
/* 44:   */   }
/* 45:   */   
/* 46:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 47:   */   {
/* 48:59 */     int start = dstIndex;
/* 49:   */     
/* 50:61 */     writeTime(this.createTime, dst, dstIndex);dstIndex += 8;
/* 51:62 */     writeInt8(0L, dst, dstIndex);dstIndex += 8;
/* 52:63 */     writeTime(this.lastWriteTime, dst, dstIndex);dstIndex += 8;
/* 53:64 */     writeInt8(0L, dst, dstIndex);dstIndex += 8;
/* 54:   */     
/* 55:   */ 
/* 56:67 */     writeInt2(0x80 | this.attributes, dst, dstIndex);dstIndex += 2;
/* 57:   */     
/* 58:69 */     writeInt8(0L, dst, dstIndex);dstIndex += 6;
/* 59:   */     
/* 60:   */ 
/* 61:   */ 
/* 62:   */ 
/* 63:74 */     return dstIndex - start;
/* 64:   */   }
/* 65:   */   
/* 66:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 67:   */   {
/* 68:77 */     return 0;
/* 69:   */   }
/* 70:   */   
/* 71:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 72:   */   {
/* 73:80 */     return 0;
/* 74:   */   }
/* 75:   */   
/* 76:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 77:   */   {
/* 78:83 */     return 0;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public String toString()
/* 82:   */   {
/* 83:86 */     return new String("Trans2SetFileInformation[" + super.toString() + ",fid=" + this.fid + "]");
/* 84:   */   }
/* 85:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2SetFileInformation
 * JD-Core Version:    0.7.0.1
 */