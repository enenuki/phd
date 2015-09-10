/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.Hexdump;
/*  4:   */ 
/*  5:   */ class Trans2QueryFSInformation
/*  6:   */   extends SmbComTransaction
/*  7:   */ {
/*  8:   */   private int informationLevel;
/*  9:   */   
/* 10:   */   Trans2QueryFSInformation(int informationLevel)
/* 11:   */   {
/* 12:28 */     this.command = 50;
/* 13:29 */     this.subCommand = 3;
/* 14:30 */     this.informationLevel = informationLevel;
/* 15:31 */     this.totalParameterCount = 2;
/* 16:32 */     this.totalDataCount = 0;
/* 17:33 */     this.maxParameterCount = 0;
/* 18:34 */     this.maxDataCount = 800;
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
/* 35:   */     
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:56 */     return dstIndex - start;
/* 44:   */   }
/* 45:   */   
/* 46:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 47:   */   {
/* 48:59 */     return 0;
/* 49:   */   }
/* 50:   */   
/* 51:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 52:   */   {
/* 53:62 */     return 0;
/* 54:   */   }
/* 55:   */   
/* 56:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 57:   */   {
/* 58:65 */     return 0;
/* 59:   */   }
/* 60:   */   
/* 61:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 62:   */   {
/* 63:68 */     return 0;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public String toString()
/* 67:   */   {
/* 68:71 */     return new String("Trans2QueryFSInformation[" + super.toString() + ",informationLevel=0x" + Hexdump.toHexString(this.informationLevel, 3) + "]");
/* 69:   */   }
/* 70:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2QueryFSInformation
 * JD-Core Version:    0.7.0.1
 */