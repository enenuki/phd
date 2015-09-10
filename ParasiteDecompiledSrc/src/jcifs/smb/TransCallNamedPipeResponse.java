/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class TransCallNamedPipeResponse
/*  4:   */   extends SmbComTransactionResponse
/*  5:   */ {
/*  6:   */   private SmbNamedPipe pipe;
/*  7:   */   
/*  8:   */   TransCallNamedPipeResponse(SmbNamedPipe pipe)
/*  9:   */   {
/* 10:26 */     this.pipe = pipe;
/* 11:   */   }
/* 12:   */   
/* 13:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 14:   */   {
/* 15:30 */     return 0;
/* 16:   */   }
/* 17:   */   
/* 18:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 19:   */   {
/* 20:33 */     return 0;
/* 21:   */   }
/* 22:   */   
/* 23:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 24:   */   {
/* 25:36 */     return 0;
/* 26:   */   }
/* 27:   */   
/* 28:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 29:   */   {
/* 30:39 */     return 0;
/* 31:   */   }
/* 32:   */   
/* 33:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 34:   */   {
/* 35:42 */     return 0;
/* 36:   */   }
/* 37:   */   
/* 38:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 39:   */   {
/* 40:45 */     if (this.pipe.pipeIn != null)
/* 41:   */     {
/* 42:46 */       TransactNamedPipeInputStream in = (TransactNamedPipeInputStream)this.pipe.pipeIn;
/* 43:47 */       synchronized (in.lock)
/* 44:   */       {
/* 45:48 */         in.receive(buffer, bufferIndex, len);
/* 46:49 */         in.lock.notify();
/* 47:   */       }
/* 48:   */     }
/* 49:52 */     return len;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:55 */     return new String("TransCallNamedPipeResponse[" + super.toString() + "]");
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransCallNamedPipeResponse
 * JD-Core Version:    0.7.0.1
 */