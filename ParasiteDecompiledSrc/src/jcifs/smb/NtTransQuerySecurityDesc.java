/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.Hexdump;
/*  4:   */ 
/*  5:   */ class NtTransQuerySecurityDesc
/*  6:   */   extends SmbComNtTransaction
/*  7:   */ {
/*  8:   */   int fid;
/*  9:   */   int securityInformation;
/* 10:   */   
/* 11:   */   NtTransQuerySecurityDesc(int fid, int securityInformation)
/* 12:   */   {
/* 13:29 */     this.fid = fid;
/* 14:30 */     this.securityInformation = securityInformation;
/* 15:31 */     this.command = -96;
/* 16:32 */     this.function = 6;
/* 17:33 */     this.setupCount = 0;
/* 18:34 */     this.totalDataCount = 0;
/* 19:35 */     this.maxParameterCount = 4;
/* 20:36 */     this.maxDataCount = 32768;
/* 21:37 */     this.maxSetupCount = 0;
/* 22:   */   }
/* 23:   */   
/* 24:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 25:   */   {
/* 26:41 */     return 0;
/* 27:   */   }
/* 28:   */   
/* 29:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 30:   */   {
/* 31:44 */     int start = dstIndex;
/* 32:   */     
/* 33:46 */     writeInt2(this.fid, dst, dstIndex);
/* 34:47 */     dstIndex += 2;
/* 35:48 */     dst[(dstIndex++)] = 0;
/* 36:49 */     dst[(dstIndex++)] = 0;
/* 37:50 */     writeInt4(this.securityInformation, dst, dstIndex);
/* 38:51 */     dstIndex += 4;
/* 39:   */     
/* 40:53 */     return dstIndex - start;
/* 41:   */   }
/* 42:   */   
/* 43:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 44:   */   {
/* 45:56 */     return 0;
/* 46:   */   }
/* 47:   */   
/* 48:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 49:   */   {
/* 50:59 */     return 0;
/* 51:   */   }
/* 52:   */   
/* 53:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 54:   */   {
/* 55:62 */     return 0;
/* 56:   */   }
/* 57:   */   
/* 58:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 59:   */   {
/* 60:65 */     return 0;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String toString()
/* 64:   */   {
/* 65:68 */     return new String("NtTransQuerySecurityDesc[" + super.toString() + ",fid=0x" + Hexdump.toHexString(this.fid, 4) + ",securityInformation=0x" + Hexdump.toHexString(this.securityInformation, 8) + "]");
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NtTransQuerySecurityDesc
 * JD-Core Version:    0.7.0.1
 */