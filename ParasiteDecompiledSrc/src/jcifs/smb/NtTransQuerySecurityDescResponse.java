/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ class NtTransQuerySecurityDescResponse
/*  6:   */   extends SmbComNtTransactionResponse
/*  7:   */ {
/*  8:   */   SecurityDescriptor securityDescriptor;
/*  9:   */   
/* 10:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 11:   */   {
/* 12:32 */     return 0;
/* 13:   */   }
/* 14:   */   
/* 15:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 16:   */   {
/* 17:35 */     return 0;
/* 18:   */   }
/* 19:   */   
/* 20:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 21:   */   {
/* 22:38 */     return 0;
/* 23:   */   }
/* 24:   */   
/* 25:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 26:   */   {
/* 27:41 */     return 0;
/* 28:   */   }
/* 29:   */   
/* 30:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 31:   */   {
/* 32:44 */     this.length = readInt4(buffer, bufferIndex);
/* 33:45 */     return 4;
/* 34:   */   }
/* 35:   */   
/* 36:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 37:   */   {
/* 38:48 */     int start = bufferIndex;
/* 39:50 */     if (this.errorCode != 0) {
/* 40:51 */       return 4;
/* 41:   */     }
/* 42:   */     try
/* 43:   */     {
/* 44:54 */       this.securityDescriptor = new SecurityDescriptor();
/* 45:55 */       bufferIndex += this.securityDescriptor.decode(buffer, bufferIndex, len);
/* 46:   */     }
/* 47:   */     catch (IOException ioe)
/* 48:   */     {
/* 49:57 */       throw new RuntimeException(ioe.getMessage());
/* 50:   */     }
/* 51:60 */     return bufferIndex - start;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String toString()
/* 55:   */   {
/* 56:63 */     return new String("NtTransQuerySecurityResponse[" + super.toString() + "]");
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NtTransQuerySecurityDescResponse
 * JD-Core Version:    0.7.0.1
 */