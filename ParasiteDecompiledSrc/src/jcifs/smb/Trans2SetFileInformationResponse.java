/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class Trans2SetFileInformationResponse
/*  4:   */   extends SmbComTransactionResponse
/*  5:   */ {
/*  6:   */   Trans2SetFileInformationResponse()
/*  7:   */   {
/*  8:24 */     this.subCommand = 8;
/*  9:   */   }
/* 10:   */   
/* 11:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 12:   */   {
/* 13:28 */     return 0;
/* 14:   */   }
/* 15:   */   
/* 16:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 17:   */   {
/* 18:31 */     return 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 22:   */   {
/* 23:34 */     return 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 27:   */   {
/* 28:37 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 32:   */   {
/* 33:40 */     return 0;
/* 34:   */   }
/* 35:   */   
/* 36:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 37:   */   {
/* 38:43 */     return 0;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String toString()
/* 42:   */   {
/* 43:46 */     return new String("Trans2SetFileInformationResponse[" + super.toString() + "]");
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2SetFileInformationResponse
 * JD-Core Version:    0.7.0.1
 */