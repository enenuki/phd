/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComLogoffAndX
/*  4:   */   extends AndXServerMessageBlock
/*  5:   */ {
/*  6:   */   SmbComLogoffAndX(ServerMessageBlock andx)
/*  7:   */   {
/*  8:24 */     super(andx);
/*  9:25 */     this.command = 116;
/* 10:   */   }
/* 11:   */   
/* 12:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 13:   */   {
/* 14:29 */     return 0;
/* 15:   */   }
/* 16:   */   
/* 17:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 18:   */   {
/* 19:32 */     return 0;
/* 20:   */   }
/* 21:   */   
/* 22:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 23:   */   {
/* 24:35 */     return 0;
/* 25:   */   }
/* 26:   */   
/* 27:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 28:   */   {
/* 29:38 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String toString()
/* 33:   */   {
/* 34:41 */     return new String("SmbComLogoffAndX[" + super.toString() + "]");
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComLogoffAndX
 * JD-Core Version:    0.7.0.1
 */