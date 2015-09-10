/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class Trans2GetDfsReferral
/*  4:   */   extends SmbComTransaction
/*  5:   */ {
/*  6:23 */   private int maxReferralLevel = 3;
/*  7:   */   
/*  8:   */   Trans2GetDfsReferral(String filename)
/*  9:   */   {
/* 10:26 */     this.path = filename;
/* 11:27 */     this.command = 50;
/* 12:28 */     this.subCommand = 16;
/* 13:29 */     this.totalDataCount = 0;
/* 14:30 */     this.maxParameterCount = 0;
/* 15:31 */     this.maxDataCount = 4096;
/* 16:32 */     this.maxSetupCount = 0;
/* 17:   */   }
/* 18:   */   
/* 19:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 20:   */   {
/* 21:36 */     dst[(dstIndex++)] = this.subCommand;
/* 22:37 */     dst[(dstIndex++)] = 0;
/* 23:38 */     return 2;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 27:   */   {
/* 28:41 */     int start = dstIndex;
/* 29:   */     
/* 30:43 */     writeInt2(this.maxReferralLevel, dst, dstIndex);
/* 31:44 */     dstIndex += 2;
/* 32:45 */     dstIndex += writeString(this.path, dst, dstIndex);
/* 33:   */     
/* 34:47 */     return dstIndex - start;
/* 35:   */   }
/* 36:   */   
/* 37:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 38:   */   {
/* 39:50 */     return 0;
/* 40:   */   }
/* 41:   */   
/* 42:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 43:   */   {
/* 44:53 */     return 0;
/* 45:   */   }
/* 46:   */   
/* 47:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 48:   */   {
/* 49:56 */     return 0;
/* 50:   */   }
/* 51:   */   
/* 52:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 53:   */   {
/* 54:59 */     return 0;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String toString()
/* 58:   */   {
/* 59:62 */     return new String("Trans2GetDfsReferral[" + super.toString() + ",maxReferralLevel=0x" + this.maxReferralLevel + ",filename=" + this.path + "]");
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2GetDfsReferral
 * JD-Core Version:    0.7.0.1
 */