/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.LogStream;
/*  4:   */ 
/*  5:   */ class TransCallNamedPipe
/*  6:   */   extends SmbComTransaction
/*  7:   */ {
/*  8:   */   private byte[] pipeData;
/*  9:   */   private int pipeDataOff;
/* 10:   */   private int pipeDataLen;
/* 11:   */   
/* 12:   */   TransCallNamedPipe(String pipeName, byte[] data, int off, int len)
/* 13:   */   {
/* 14:27 */     this.name = pipeName;
/* 15:28 */     this.pipeData = data;
/* 16:29 */     this.pipeDataOff = off;
/* 17:30 */     this.pipeDataLen = len;
/* 18:31 */     this.command = 37;
/* 19:32 */     this.subCommand = 84;
/* 20:33 */     this.timeout = -1;
/* 21:34 */     this.maxParameterCount = 0;
/* 22:35 */     this.maxDataCount = 65535;
/* 23:36 */     this.maxSetupCount = 0;
/* 24:37 */     this.setupCount = 2;
/* 25:   */   }
/* 26:   */   
/* 27:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 28:   */   {
/* 29:41 */     dst[(dstIndex++)] = this.subCommand;
/* 30:42 */     dst[(dstIndex++)] = 0;
/* 31:   */     
/* 32:44 */     dst[(dstIndex++)] = 0;
/* 33:45 */     dst[(dstIndex++)] = 0;
/* 34:46 */     return 4;
/* 35:   */   }
/* 36:   */   
/* 37:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 38:   */   {
/* 39:49 */     return 0;
/* 40:   */   }
/* 41:   */   
/* 42:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 43:   */   {
/* 44:52 */     return 0;
/* 45:   */   }
/* 46:   */   
/* 47:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 48:   */   {
/* 49:55 */     if (dst.length - dstIndex < this.pipeDataLen)
/* 50:   */     {
/* 51:56 */       if (LogStream.level >= 3) {
/* 52:57 */         log.println("TransCallNamedPipe data too long for buffer");
/* 53:   */       }
/* 54:58 */       return 0;
/* 55:   */     }
/* 56:60 */     System.arraycopy(this.pipeData, this.pipeDataOff, dst, dstIndex, this.pipeDataLen);
/* 57:61 */     return this.pipeDataLen;
/* 58:   */   }
/* 59:   */   
/* 60:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 61:   */   {
/* 62:64 */     return 0;
/* 63:   */   }
/* 64:   */   
/* 65:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 66:   */   {
/* 67:67 */     return 0;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public String toString()
/* 71:   */   {
/* 72:70 */     return new String("TransCallNamedPipe[" + super.toString() + ",pipeName=" + this.name + "]");
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransCallNamedPipe
 * JD-Core Version:    0.7.0.1
 */