/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class TransPeekNamedPipeResponse
/*  4:   */   extends SmbComTransactionResponse
/*  5:   */ {
/*  6:   */   private SmbNamedPipe pipe;
/*  7:   */   private int head;
/*  8:   */   static final int STATUS_DISCONNECTED = 1;
/*  9:   */   static final int STATUS_LISTENING = 2;
/* 10:   */   static final int STATUS_CONNECTION_OK = 3;
/* 11:   */   static final int STATUS_SERVER_END_CLOSED = 4;
/* 12:   */   int status;
/* 13:   */   int available;
/* 14:   */   
/* 15:   */   TransPeekNamedPipeResponse(SmbNamedPipe pipe)
/* 16:   */   {
/* 17:34 */     this.pipe = pipe;
/* 18:   */   }
/* 19:   */   
/* 20:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 21:   */   {
/* 22:38 */     return 0;
/* 23:   */   }
/* 24:   */   
/* 25:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 26:   */   {
/* 27:41 */     return 0;
/* 28:   */   }
/* 29:   */   
/* 30:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 31:   */   {
/* 32:44 */     return 0;
/* 33:   */   }
/* 34:   */   
/* 35:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 36:   */   {
/* 37:47 */     return 0;
/* 38:   */   }
/* 39:   */   
/* 40:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 41:   */   {
/* 42:50 */     this.available = readInt2(buffer, bufferIndex);bufferIndex += 2;
/* 43:51 */     this.head = readInt2(buffer, bufferIndex);bufferIndex += 2;
/* 44:52 */     this.status = readInt2(buffer, bufferIndex);
/* 45:53 */     return 6;
/* 46:   */   }
/* 47:   */   
/* 48:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 49:   */   {
/* 50:56 */     return 0;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String toString()
/* 54:   */   {
/* 55:59 */     return new String("TransPeekNamedPipeResponse[" + super.toString() + "]");
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransPeekNamedPipeResponse
 * JD-Core Version:    0.7.0.1
 */