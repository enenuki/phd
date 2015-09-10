/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComWrite
/*  4:   */   extends ServerMessageBlock
/*  5:   */ {
/*  6:   */   private int fid;
/*  7:   */   private int count;
/*  8:   */   private int offset;
/*  9:   */   private int remaining;
/* 10:   */   private int off;
/* 11:   */   private byte[] b;
/* 12:   */   
/* 13:   */   SmbComWrite()
/* 14:   */   {
/* 15:34 */     this.command = 11;
/* 16:   */   }
/* 17:   */   
/* 18:   */   SmbComWrite(int fid, int offset, int remaining, byte[] b, int off, int len)
/* 19:   */   {
/* 20:37 */     this.fid = fid;
/* 21:38 */     this.count = len;
/* 22:39 */     this.offset = offset;
/* 23:40 */     this.remaining = remaining;
/* 24:41 */     this.b = b;
/* 25:42 */     this.off = off;
/* 26:43 */     this.command = 11;
/* 27:   */   }
/* 28:   */   
/* 29:   */   void setParam(int fid, long offset, int remaining, byte[] b, int off, int len)
/* 30:   */   {
/* 31:48 */     this.fid = fid;
/* 32:49 */     this.offset = ((int)(offset & 0xFFFFFFFF));
/* 33:50 */     this.remaining = remaining;
/* 34:51 */     this.b = b;
/* 35:52 */     this.off = off;
/* 36:53 */     this.count = len;
/* 37:54 */     this.digest = null;
/* 38:   */   }
/* 39:   */   
/* 40:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 41:   */   {
/* 42:59 */     int start = dstIndex;
/* 43:   */     
/* 44:61 */     writeInt2(this.fid, dst, dstIndex);
/* 45:62 */     dstIndex += 2;
/* 46:63 */     writeInt2(this.count, dst, dstIndex);
/* 47:64 */     dstIndex += 2;
/* 48:65 */     writeInt4(this.offset, dst, dstIndex);
/* 49:66 */     dstIndex += 4;
/* 50:67 */     writeInt2(this.remaining, dst, dstIndex);
/* 51:68 */     dstIndex += 2;
/* 52:   */     
/* 53:70 */     return dstIndex - start;
/* 54:   */   }
/* 55:   */   
/* 56:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 57:   */   {
/* 58:73 */     int start = dstIndex;
/* 59:   */     
/* 60:75 */     dst[(dstIndex++)] = 1;
/* 61:76 */     writeInt2(this.count, dst, dstIndex);
/* 62:77 */     dstIndex += 2;
/* 63:78 */     System.arraycopy(this.b, this.off, dst, dstIndex, this.count);
/* 64:79 */     dstIndex += this.count;
/* 65:   */     
/* 66:81 */     return dstIndex - start;
/* 67:   */   }
/* 68:   */   
/* 69:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 70:   */   {
/* 71:84 */     return 0;
/* 72:   */   }
/* 73:   */   
/* 74:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 75:   */   {
/* 76:87 */     return 0;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public String toString()
/* 80:   */   {
/* 81:90 */     return new String("SmbComWrite[" + super.toString() + ",fid=" + this.fid + ",count=" + this.count + ",offset=" + this.offset + ",remaining=" + this.remaining + "]");
/* 82:   */   }
/* 83:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComWrite
 * JD-Core Version:    0.7.0.1
 */