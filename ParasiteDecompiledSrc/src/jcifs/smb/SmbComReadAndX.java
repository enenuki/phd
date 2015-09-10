/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.Config;
/*  4:   */ 
/*  5:   */ class SmbComReadAndX
/*  6:   */   extends AndXServerMessageBlock
/*  7:   */ {
/*  8:25 */   private static final int BATCH_LIMIT = Config.getInt("jcifs.smb.client.ReadAndX.Close", 1);
/*  9:   */   private long offset;
/* 10:   */   private int fid;
/* 11:   */   private int openTimeout;
/* 12:   */   int maxCount;
/* 13:   */   int minCount;
/* 14:   */   int remaining;
/* 15:   */   
/* 16:   */   SmbComReadAndX()
/* 17:   */   {
/* 18:33 */     super(null);
/* 19:34 */     this.command = 46;
/* 20:35 */     this.openTimeout = -1;
/* 21:   */   }
/* 22:   */   
/* 23:   */   SmbComReadAndX(int fid, long offset, int maxCount, ServerMessageBlock andx)
/* 24:   */   {
/* 25:38 */     super(andx);
/* 26:39 */     this.fid = fid;
/* 27:40 */     this.offset = offset;
/* 28:41 */     this.maxCount = (this.minCount = maxCount);
/* 29:42 */     this.command = 46;
/* 30:43 */     this.openTimeout = -1;
/* 31:   */   }
/* 32:   */   
/* 33:   */   void setParam(int fid, long offset, int maxCount)
/* 34:   */   {
/* 35:47 */     this.fid = fid;
/* 36:48 */     this.offset = offset;
/* 37:49 */     this.maxCount = (this.minCount = maxCount);
/* 38:   */   }
/* 39:   */   
/* 40:   */   int getBatchLimit(byte command)
/* 41:   */   {
/* 42:52 */     return command == 4 ? BATCH_LIMIT : 0;
/* 43:   */   }
/* 44:   */   
/* 45:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 46:   */   {
/* 47:55 */     int start = dstIndex;
/* 48:   */     
/* 49:57 */     writeInt2(this.fid, dst, dstIndex);
/* 50:58 */     dstIndex += 2;
/* 51:59 */     writeInt4(this.offset, dst, dstIndex);
/* 52:60 */     dstIndex += 4;
/* 53:61 */     writeInt2(this.maxCount, dst, dstIndex);
/* 54:62 */     dstIndex += 2;
/* 55:63 */     writeInt2(this.minCount, dst, dstIndex);
/* 56:64 */     dstIndex += 2;
/* 57:65 */     writeInt4(this.openTimeout, dst, dstIndex);
/* 58:66 */     dstIndex += 4;
/* 59:67 */     writeInt2(this.remaining, dst, dstIndex);
/* 60:68 */     dstIndex += 2;
/* 61:69 */     writeInt4(this.offset >> 32, dst, dstIndex);
/* 62:70 */     dstIndex += 4;
/* 63:   */     
/* 64:72 */     return dstIndex - start;
/* 65:   */   }
/* 66:   */   
/* 67:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 68:   */   {
/* 69:75 */     return 0;
/* 70:   */   }
/* 71:   */   
/* 72:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 73:   */   {
/* 74:78 */     return 0;
/* 75:   */   }
/* 76:   */   
/* 77:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 78:   */   {
/* 79:81 */     return 0;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public String toString()
/* 83:   */   {
/* 84:84 */     return new String("SmbComReadAndX[" + super.toString() + ",fid=" + this.fid + ",offset=" + this.offset + ",maxCount=" + this.maxCount + ",minCount=" + this.minCount + ",openTimeout=" + this.openTimeout + ",remaining=" + this.remaining + ",offset=" + this.offset + "]");
/* 85:   */   }
/* 86:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComReadAndX
 * JD-Core Version:    0.7.0.1
 */