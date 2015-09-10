/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComFindClose2
/*  4:   */   extends ServerMessageBlock
/*  5:   */ {
/*  6:   */   private int sid;
/*  7:   */   
/*  8:   */   SmbComFindClose2(int sid)
/*  9:   */   {
/* 10:26 */     this.sid = sid;
/* 11:27 */     this.command = 52;
/* 12:   */   }
/* 13:   */   
/* 14:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 15:   */   {
/* 16:31 */     writeInt2(this.sid, dst, dstIndex);
/* 17:32 */     return 2;
/* 18:   */   }
/* 19:   */   
/* 20:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 21:   */   {
/* 22:35 */     return 0;
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
/* 37:44 */     return new String("SmbComFindClose2[" + super.toString() + ",sid=" + this.sid + "]");
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComFindClose2
 * JD-Core Version:    0.7.0.1
 */