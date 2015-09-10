/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComQueryInformation
/*  4:   */   extends ServerMessageBlock
/*  5:   */ {
/*  6:   */   SmbComQueryInformation(String filename)
/*  7:   */   {
/*  8:24 */     this.path = filename;
/*  9:25 */     this.command = 8;
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
/* 20:33 */     dst[(dstIndex++)] = 4;
/* 21:34 */     dstIndex += writeString(this.path, dst, dstIndex);
/* 22:35 */     return dstIndex - start;
/* 23:   */   }
/* 24:   */   
/* 25:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 26:   */   {
/* 27:38 */     return 0;
/* 28:   */   }
/* 29:   */   
/* 30:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 31:   */   {
/* 32:41 */     return 0;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String toString()
/* 36:   */   {
/* 37:44 */     return new String("SmbComQueryInformation[" + super.toString() + ",filename=" + this.path + "]");
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComQueryInformation
 * JD-Core Version:    0.7.0.1
 */