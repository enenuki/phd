/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class TransWaitNamedPipe
/*  4:   */   extends SmbComTransaction
/*  5:   */ {
/*  6:   */   TransWaitNamedPipe(String pipeName)
/*  7:   */   {
/*  8:24 */     this.name = pipeName;
/*  9:25 */     this.command = 37;
/* 10:26 */     this.subCommand = 83;
/* 11:27 */     this.timeout = -1;
/* 12:28 */     this.maxParameterCount = 0;
/* 13:29 */     this.maxDataCount = 0;
/* 14:30 */     this.maxSetupCount = 0;
/* 15:31 */     this.setupCount = 2;
/* 16:   */   }
/* 17:   */   
/* 18:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 19:   */   {
/* 20:35 */     dst[(dstIndex++)] = this.subCommand;
/* 21:36 */     dst[(dstIndex++)] = 0;
/* 22:37 */     dst[(dstIndex++)] = 0;
/* 23:38 */     dst[(dstIndex++)] = 0;
/* 24:39 */     return 4;
/* 25:   */   }
/* 26:   */   
/* 27:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 28:   */   {
/* 29:42 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 33:   */   {
/* 34:45 */     return 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 38:   */   {
/* 39:48 */     return 0;
/* 40:   */   }
/* 41:   */   
/* 42:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 43:   */   {
/* 44:51 */     return 0;
/* 45:   */   }
/* 46:   */   
/* 47:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 48:   */   {
/* 49:54 */     return 0;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:57 */     return new String("TransWaitNamedPipe[" + super.toString() + ",pipeName=" + this.name + "]");
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransWaitNamedPipe
 * JD-Core Version:    0.7.0.1
 */