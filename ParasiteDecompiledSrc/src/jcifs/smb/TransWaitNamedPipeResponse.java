/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class TransWaitNamedPipeResponse
/*  4:   */   extends SmbComTransactionResponse
/*  5:   */ {
/*  6:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/*  7:   */   {
/*  8:29 */     return 0;
/*  9:   */   }
/* 10:   */   
/* 11:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 12:   */   {
/* 13:32 */     return 0;
/* 14:   */   }
/* 15:   */   
/* 16:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 17:   */   {
/* 18:35 */     return 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 22:   */   {
/* 23:38 */     return 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 27:   */   {
/* 28:41 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 32:   */   {
/* 33:44 */     return 0;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String toString()
/* 37:   */   {
/* 38:47 */     return new String("TransWaitNamedPipeResponse[" + super.toString() + "]");
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransWaitNamedPipeResponse
 * JD-Core Version:    0.7.0.1
 */