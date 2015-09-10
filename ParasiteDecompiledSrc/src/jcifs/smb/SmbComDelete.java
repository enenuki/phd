/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.Hexdump;
/*  4:   */ 
/*  5:   */ class SmbComDelete
/*  6:   */   extends ServerMessageBlock
/*  7:   */ {
/*  8:   */   private int searchAttributes;
/*  9:   */   
/* 10:   */   SmbComDelete(String fileName)
/* 11:   */   {
/* 12:28 */     this.path = fileName;
/* 13:29 */     this.command = 6;
/* 14:30 */     this.searchAttributes = 6;
/* 15:   */   }
/* 16:   */   
/* 17:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 18:   */   {
/* 19:34 */     writeInt2(this.searchAttributes, dst, dstIndex);
/* 20:35 */     return 2;
/* 21:   */   }
/* 22:   */   
/* 23:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 24:   */   {
/* 25:38 */     int start = dstIndex;
/* 26:   */     
/* 27:40 */     dst[(dstIndex++)] = 4;
/* 28:41 */     dstIndex += writeString(this.path, dst, dstIndex);
/* 29:   */     
/* 30:43 */     return dstIndex - start;
/* 31:   */   }
/* 32:   */   
/* 33:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 34:   */   {
/* 35:46 */     return 0;
/* 36:   */   }
/* 37:   */   
/* 38:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 39:   */   {
/* 40:49 */     return 0;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String toString()
/* 44:   */   {
/* 45:52 */     return new String("SmbComDelete[" + super.toString() + ",searchAttributes=0x" + Hexdump.toHexString(this.searchAttributes, 4) + ",fileName=" + this.path + "]");
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComDelete
 * JD-Core Version:    0.7.0.1
 */