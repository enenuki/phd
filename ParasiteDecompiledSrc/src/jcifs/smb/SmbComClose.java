/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComClose
/*  4:   */   extends ServerMessageBlock
/*  5:   */ {
/*  6:   */   private int fid;
/*  7:   */   private long lastWriteTime;
/*  8:   */   
/*  9:   */   SmbComClose(int fid, long lastWriteTime)
/* 10:   */   {
/* 11:29 */     this.fid = fid;
/* 12:30 */     this.lastWriteTime = lastWriteTime;
/* 13:31 */     this.command = 4;
/* 14:   */   }
/* 15:   */   
/* 16:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 17:   */   {
/* 18:35 */     writeInt2(this.fid, dst, dstIndex);
/* 19:36 */     dstIndex += 2;
/* 20:37 */     writeUTime(this.lastWriteTime, dst, dstIndex);
/* 21:38 */     return 6;
/* 22:   */   }
/* 23:   */   
/* 24:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 25:   */   {
/* 26:41 */     return 0;
/* 27:   */   }
/* 28:   */   
/* 29:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 30:   */   {
/* 31:44 */     return 0;
/* 32:   */   }
/* 33:   */   
/* 34:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 35:   */   {
/* 36:47 */     return 0;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String toString()
/* 40:   */   {
/* 41:50 */     return new String("SmbComClose[" + super.toString() + ",fid=" + this.fid + ",lastWriteTime=" + this.lastWriteTime + "]");
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComClose
 * JD-Core Version:    0.7.0.1
 */