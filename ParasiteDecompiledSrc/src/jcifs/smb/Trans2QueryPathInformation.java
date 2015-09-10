/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.Hexdump;
/*  4:   */ 
/*  5:   */ class Trans2QueryPathInformation
/*  6:   */   extends SmbComTransaction
/*  7:   */ {
/*  8:   */   private int informationLevel;
/*  9:   */   
/* 10:   */   Trans2QueryPathInformation(String filename, int informationLevel)
/* 11:   */   {
/* 12:28 */     this.path = filename;
/* 13:29 */     this.informationLevel = informationLevel;
/* 14:30 */     this.command = 50;
/* 15:31 */     this.subCommand = 5;
/* 16:32 */     this.totalDataCount = 0;
/* 17:33 */     this.maxParameterCount = 2;
/* 18:34 */     this.maxDataCount = 40;
/* 19:35 */     this.maxSetupCount = 0;
/* 20:   */   }
/* 21:   */   
/* 22:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 23:   */   {
/* 24:39 */     dst[(dstIndex++)] = this.subCommand;
/* 25:40 */     dst[(dstIndex++)] = 0;
/* 26:41 */     return 2;
/* 27:   */   }
/* 28:   */   
/* 29:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 30:   */   {
/* 31:44 */     int start = dstIndex;
/* 32:   */     
/* 33:46 */     writeInt2(this.informationLevel, dst, dstIndex);
/* 34:47 */     dstIndex += 2;
/* 35:48 */     dst[(dstIndex++)] = 0;
/* 36:49 */     dst[(dstIndex++)] = 0;
/* 37:50 */     dst[(dstIndex++)] = 0;
/* 38:51 */     dst[(dstIndex++)] = 0;
/* 39:52 */     dstIndex += writeString(this.path, dst, dstIndex);
/* 40:   */     
/* 41:54 */     return dstIndex - start;
/* 42:   */   }
/* 43:   */   
/* 44:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 45:   */   {
/* 46:57 */     return 0;
/* 47:   */   }
/* 48:   */   
/* 49:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 50:   */   {
/* 51:60 */     return 0;
/* 52:   */   }
/* 53:   */   
/* 54:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 55:   */   {
/* 56:63 */     return 0;
/* 57:   */   }
/* 58:   */   
/* 59:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 60:   */   {
/* 61:66 */     return 0;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String toString()
/* 65:   */   {
/* 66:69 */     return new String("Trans2QueryPathInformation[" + super.toString() + ",informationLevel=0x" + Hexdump.toHexString(this.informationLevel, 3) + ",filename=" + this.path + "]");
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2QueryPathInformation
 * JD-Core Version:    0.7.0.1
 */