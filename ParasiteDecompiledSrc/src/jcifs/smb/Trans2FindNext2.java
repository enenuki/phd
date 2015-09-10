/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.Hexdump;
/*  4:   */ 
/*  5:   */ class Trans2FindNext2
/*  6:   */   extends SmbComTransaction
/*  7:   */ {
/*  8:   */   private int sid;
/*  9:   */   private int informationLevel;
/* 10:   */   private int resumeKey;
/* 11:   */   private int flags;
/* 12:   */   private String filename;
/* 13:   */   
/* 14:   */   Trans2FindNext2(int sid, int resumeKey, String filename)
/* 15:   */   {
/* 16:30 */     this.sid = sid;
/* 17:31 */     this.resumeKey = resumeKey;
/* 18:32 */     this.filename = filename;
/* 19:33 */     this.command = 50;
/* 20:34 */     this.subCommand = 2;
/* 21:35 */     this.informationLevel = 260;
/* 22:36 */     this.flags = 0;
/* 23:37 */     this.maxParameterCount = 8;
/* 24:38 */     this.maxDataCount = Trans2FindFirst2.LIST_SIZE;
/* 25:39 */     this.maxSetupCount = 0;
/* 26:   */   }
/* 27:   */   
/* 28:   */   void reset(int resumeKey, String lastName)
/* 29:   */   {
/* 30:43 */     super.reset();
/* 31:44 */     this.resumeKey = resumeKey;
/* 32:45 */     this.filename = lastName;
/* 33:46 */     this.flags2 = 0;
/* 34:   */   }
/* 35:   */   
/* 36:   */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 37:   */   {
/* 38:50 */     dst[(dstIndex++)] = this.subCommand;
/* 39:51 */     dst[(dstIndex++)] = 0;
/* 40:52 */     return 2;
/* 41:   */   }
/* 42:   */   
/* 43:   */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 44:   */   {
/* 45:55 */     int start = dstIndex;
/* 46:   */     
/* 47:57 */     writeInt2(this.sid, dst, dstIndex);
/* 48:58 */     dstIndex += 2;
/* 49:59 */     writeInt2(Trans2FindFirst2.LIST_COUNT, dst, dstIndex);
/* 50:60 */     dstIndex += 2;
/* 51:61 */     writeInt2(this.informationLevel, dst, dstIndex);
/* 52:62 */     dstIndex += 2;
/* 53:63 */     writeInt4(this.resumeKey, dst, dstIndex);
/* 54:64 */     dstIndex += 4;
/* 55:65 */     writeInt2(this.flags, dst, dstIndex);
/* 56:66 */     dstIndex += 2;
/* 57:67 */     dstIndex += writeString(this.filename, dst, dstIndex);
/* 58:   */     
/* 59:69 */     return dstIndex - start;
/* 60:   */   }
/* 61:   */   
/* 62:   */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 63:   */   {
/* 64:72 */     return 0;
/* 65:   */   }
/* 66:   */   
/* 67:   */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 68:   */   {
/* 69:75 */     return 0;
/* 70:   */   }
/* 71:   */   
/* 72:   */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 73:   */   {
/* 74:78 */     return 0;
/* 75:   */   }
/* 76:   */   
/* 77:   */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 78:   */   {
/* 79:81 */     return 0;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public String toString()
/* 83:   */   {
/* 84:84 */     return new String("Trans2FindNext2[" + super.toString() + ",sid=" + this.sid + ",searchCount=" + Trans2FindFirst2.LIST_SIZE + ",informationLevel=0x" + Hexdump.toHexString(this.informationLevel, 3) + ",resumeKey=0x" + Hexdump.toHexString(this.resumeKey, 4) + ",flags=0x" + Hexdump.toHexString(this.flags, 2) + ",filename=" + this.filename + "]");
/* 85:   */   }
/* 86:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2FindNext2
 * JD-Core Version:    0.7.0.1
 */