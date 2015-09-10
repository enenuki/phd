/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.LogStream;
/*  4:   */ 
/*  5:   */ class TransTransactNamedPipe
/*  6:   */   extends SmbComTransaction
/*  7:   */ {
/*  8:   */   private byte[] pipeData;
/*  9:   */   private int pipeFid;
/* 10:   */   private int pipeDataOff;
/* 11:   */   private int pipeDataLen;
/* 12:   */   
/* 13:   */   TransTransactNamedPipe(int fid, byte[] data, int off, int len)
/* 14:   */   {
/* 15:27 */     this.pipeFid = fid;
/* 16:28 */     this.pipeData = data;
/* 17:29 */     this.pipeDataOff = off;
/* 18:30 */     this.pipeDataLen = len;
/* 19:31 */     this.command = 37;
/* 20:32 */     this.subCommand = 38;
/* 21:33 */     this.maxParameterCount = 0;
/* 22:34 */     this.maxDataCount = 65535;
/* 23:35 */     this.maxSetupCount = 0;
/* 24:36 */     this.setupCount = 2;
/* 25:37 */     this.name = "\\PIPE\\";
/* 26:   */   }
/* 27:   */   
/* 28:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 29:   */   {
/* 30:41 */     dst[(dstIndex++)] = this.subCommand;
/* 31:42 */     dst[(dstIndex++)] = 0;
/* 32:43 */     writeInt2(this.pipeFid, dst, dstIndex);
/* 33:44 */     dstIndex += 2;
/* 34:45 */     return 4;
/* 35:   */   }
/* 36:   */   
/* 37:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 38:   */   {
/* 39:48 */     return 0;
/* 40:   */   }
/* 41:   */   
/* 42:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 43:   */   {
/* 44:51 */     return 0;
/* 45:   */   }
/* 46:   */   
/* 47:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 48:   */   {
/* 49:54 */     if (dst.length - dstIndex < this.pipeDataLen)
/* 50:   */     {
/* 51:55 */       if (LogStream.level >= 3) {
/* 52:56 */         log.println("TransTransactNamedPipe data too long for buffer");
/* 53:   */       }
/* 54:57 */       return 0;
/* 55:   */     }
/* 56:59 */     System.arraycopy(this.pipeData, this.pipeDataOff, dst, dstIndex, this.pipeDataLen);
/* 57:60 */     return this.pipeDataLen;
/* 58:   */   }
/* 59:   */   
/* 60:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 61:   */   {
/* 62:63 */     return 0;
/* 63:   */   }
/* 64:   */   
/* 65:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 66:   */   {
/* 67:66 */     return 0;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public String toString()
/* 71:   */   {
/* 72:69 */     return new String("TransTransactNamedPipe[" + super.toString() + ",pipeFid=" + this.pipeFid + "]");
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransTransactNamedPipe
 * JD-Core Version:    0.7.0.1
 */