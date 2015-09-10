/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.Hexdump;
/*  4:   */ 
/*  5:   */ class SmbComRename
/*  6:   */   extends ServerMessageBlock
/*  7:   */ {
/*  8:   */   private int searchAttributes;
/*  9:   */   private String oldFileName;
/* 10:   */   private String newFileName;
/* 11:   */   
/* 12:   */   SmbComRename(String oldFileName, String newFileName)
/* 13:   */   {
/* 14:30 */     this.command = 7;
/* 15:31 */     this.oldFileName = oldFileName;
/* 16:32 */     this.newFileName = newFileName;
/* 17:33 */     this.searchAttributes = 22;
/* 18:   */   }
/* 19:   */   
/* 20:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 21:   */   {
/* 22:37 */     writeInt2(this.searchAttributes, dst, dstIndex);
/* 23:38 */     return 2;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 27:   */   {
/* 28:41 */     int start = dstIndex;
/* 29:   */     
/* 30:43 */     dst[(dstIndex++)] = 4;
/* 31:44 */     dstIndex += writeString(this.oldFileName, dst, dstIndex);
/* 32:45 */     dst[(dstIndex++)] = 4;
/* 33:46 */     if (this.useUnicode) {
/* 34:47 */       dst[(dstIndex++)] = 0;
/* 35:   */     }
/* 36:49 */     dstIndex += writeString(this.newFileName, dst, dstIndex);
/* 37:   */     
/* 38:51 */     return dstIndex - start;
/* 39:   */   }
/* 40:   */   
/* 41:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 42:   */   {
/* 43:54 */     return 0;
/* 44:   */   }
/* 45:   */   
/* 46:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 47:   */   {
/* 48:57 */     return 0;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String toString()
/* 52:   */   {
/* 53:60 */     return new String("SmbComRename[" + super.toString() + ",searchAttributes=0x" + Hexdump.toHexString(this.searchAttributes, 4) + ",oldFileName=" + this.oldFileName + ",newFileName=" + this.newFileName + "]");
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComRename
 * JD-Core Version:    0.7.0.1
 */