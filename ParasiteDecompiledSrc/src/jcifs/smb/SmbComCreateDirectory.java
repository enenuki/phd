/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComCreateDirectory
/*  4:   */   extends ServerMessageBlock
/*  5:   */ {
/*  6:   */   SmbComCreateDirectory(String directoryName)
/*  7:   */   {
/*  8:24 */     this.path = directoryName;
/*  9:25 */     this.command = 0;
/* 10:   */   }
/* 11:   */   
/* 12:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 13:   */   {
/* 14:29 */     return 0;
/* 15:   */   }
/* 16:   */   
/* 17:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 18:   */   {
/* 19:32 */     int start = dstIndex;
/* 20:   */     
/* 21:34 */     dst[(dstIndex++)] = 4;
/* 22:35 */     dstIndex += writeString(this.path, dst, dstIndex);
/* 23:   */     
/* 24:37 */     return dstIndex - start;
/* 25:   */   }
/* 26:   */   
/* 27:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 28:   */   {
/* 29:40 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 33:   */   {
/* 34:43 */     return 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String toString()
/* 38:   */   {
/* 39:46 */     return new String("SmbComCreateDirectory[" + super.toString() + ",directoryName=" + this.path + "]");
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComCreateDirectory
 * JD-Core Version:    0.7.0.1
 */