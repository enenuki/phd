/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class TransPeekNamedPipe
/*  4:   */   extends SmbComTransaction
/*  5:   */ {
/*  6:   */   private int fid;
/*  7:   */   
/*  8:   */   TransPeekNamedPipe(String pipeName, int fid)
/*  9:   */   {
/* 10:26 */     this.name = pipeName;
/* 11:27 */     this.fid = fid;
/* 12:28 */     this.command = 37;
/* 13:29 */     this.subCommand = 35;
/* 14:30 */     this.timeout = -1;
/* 15:31 */     this.maxParameterCount = 6;
/* 16:32 */     this.maxDataCount = 1;
/* 17:33 */     this.maxSetupCount = 0;
/* 18:34 */     this.setupCount = 2;
/* 19:   */   }
/* 20:   */   
/* 21:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 22:   */   {
/* 23:38 */     dst[(dstIndex++)] = this.subCommand;
/* 24:39 */     dst[(dstIndex++)] = 0;
/* 25:   */     
/* 26:41 */     writeInt2(this.fid, dst, dstIndex);
/* 27:42 */     return 4;
/* 28:   */   }
/* 29:   */   
/* 30:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 31:   */   {
/* 32:45 */     return 0;
/* 33:   */   }
/* 34:   */   
/* 35:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 36:   */   {
/* 37:48 */     return 0;
/* 38:   */   }
/* 39:   */   
/* 40:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 41:   */   {
/* 42:51 */     return 0;
/* 43:   */   }
/* 44:   */   
/* 45:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 46:   */   {
/* 47:54 */     return 0;
/* 48:   */   }
/* 49:   */   
/* 50:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 51:   */   {
/* 52:57 */     return 0;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String toString()
/* 56:   */   {
/* 57:60 */     return new String("TransPeekNamedPipe[" + super.toString() + ",pipeName=" + this.name + "]");
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransPeekNamedPipe
 * JD-Core Version:    0.7.0.1
 */