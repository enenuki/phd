/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.io.UnsupportedEncodingException;
/*  4:   */ 
/*  5:   */ class NetShareEnum
/*  6:   */   extends SmbComTransaction
/*  7:   */ {
/*  8:   */   private static final String DESCR = "";
/*  9:   */   
/* 10:   */   NetShareEnum()
/* 11:   */   {
/* 12:28 */     this.command = 37;
/* 13:29 */     this.subCommand = 0;
/* 14:30 */     this.name = new String("\\PIPE\\LANMAN");
/* 15:31 */     this.maxParameterCount = 8;
/* 16:   */     
/* 17:   */ 
/* 18:34 */     this.maxSetupCount = 0;
/* 19:35 */     this.setupCount = 0;
/* 20:36 */     this.timeout = 5000;
/* 21:   */   }
/* 22:   */   
/* 23:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 24:   */   {
/* 25:40 */     return 0;
/* 26:   */   }
/* 27:   */   
/* 28:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 29:   */   {
/* 30:43 */     int start = dstIndex;
/* 31:   */     try
/* 32:   */     {
/* 33:47 */       descr = "".getBytes("ASCII");
/* 34:   */     }
/* 35:   */     catch (UnsupportedEncodingException uee)
/* 36:   */     {
/* 37:   */       byte[] descr;
/* 38:49 */       return 0;
/* 39:   */     }
/* 40:   */     byte[] descr;
/* 41:52 */     writeInt2(0L, dst, dstIndex);
/* 42:53 */     dstIndex += 2;
/* 43:54 */     System.arraycopy(descr, 0, dst, dstIndex, descr.length);
/* 44:55 */     dstIndex += descr.length;
/* 45:56 */     writeInt2(1L, dst, dstIndex);
/* 46:57 */     dstIndex += 2;
/* 47:58 */     writeInt2(this.maxDataCount, dst, dstIndex);
/* 48:59 */     dstIndex += 2;
/* 49:   */     
/* 50:61 */     return dstIndex - start;
/* 51:   */   }
/* 52:   */   
/* 53:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 54:   */   {
/* 55:64 */     return 0;
/* 56:   */   }
/* 57:   */   
/* 58:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 59:   */   {
/* 60:67 */     return 0;
/* 61:   */   }
/* 62:   */   
/* 63:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 64:   */   {
/* 65:70 */     return 0;
/* 66:   */   }
/* 67:   */   
/* 68:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 69:   */   {
/* 70:73 */     return 0;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public String toString()
/* 74:   */   {
/* 75:76 */     return new String("NetShareEnum[" + super.toString() + "]");
/* 76:   */   }
/* 77:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NetShareEnum
 * JD-Core Version:    0.7.0.1
 */