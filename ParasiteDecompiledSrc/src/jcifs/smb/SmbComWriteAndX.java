/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import jcifs.Config;
/*   4:    */ 
/*   5:    */ class SmbComWriteAndX
/*   6:    */   extends AndXServerMessageBlock
/*   7:    */ {
/*   8: 26 */   private static final int READ_ANDX_BATCH_LIMIT = Config.getInt("jcifs.smb.client.WriteAndX.ReadAndX", 1);
/*   9: 28 */   private static final int CLOSE_BATCH_LIMIT = Config.getInt("jcifs.smb.client.WriteAndX.Close", 1);
/*  10:    */   private int fid;
/*  11:    */   private int remaining;
/*  12:    */   private int dataLength;
/*  13:    */   private int dataOffset;
/*  14:    */   private int off;
/*  15:    */   private byte[] b;
/*  16:    */   private long offset;
/*  17:    */   private int pad;
/*  18:    */   int writeMode;
/*  19:    */   
/*  20:    */   SmbComWriteAndX()
/*  21:    */   {
/*  22: 44 */     super(null);
/*  23: 45 */     this.command = 47;
/*  24:    */   }
/*  25:    */   
/*  26:    */   SmbComWriteAndX(int fid, long offset, int remaining, byte[] b, int off, int len, ServerMessageBlock andx)
/*  27:    */   {
/*  28: 49 */     super(andx);
/*  29: 50 */     this.fid = fid;
/*  30: 51 */     this.offset = offset;
/*  31: 52 */     this.remaining = remaining;
/*  32: 53 */     this.b = b;
/*  33: 54 */     this.off = off;
/*  34: 55 */     this.dataLength = len;
/*  35: 56 */     this.command = 47;
/*  36:    */   }
/*  37:    */   
/*  38:    */   void setParam(int fid, long offset, int remaining, byte[] b, int off, int len)
/*  39:    */   {
/*  40: 61 */     this.fid = fid;
/*  41: 62 */     this.offset = offset;
/*  42: 63 */     this.remaining = remaining;
/*  43: 64 */     this.b = b;
/*  44: 65 */     this.off = off;
/*  45: 66 */     this.dataLength = len;
/*  46: 67 */     this.digest = null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   int getBatchLimit(byte command)
/*  50:    */   {
/*  51: 72 */     if (command == 46) {
/*  52: 73 */       return READ_ANDX_BATCH_LIMIT;
/*  53:    */     }
/*  54: 75 */     if (command == 4) {
/*  55: 76 */       return CLOSE_BATCH_LIMIT;
/*  56:    */     }
/*  57: 78 */     return 0;
/*  58:    */   }
/*  59:    */   
/*  60:    */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/*  61:    */   {
/*  62: 81 */     int start = dstIndex;
/*  63:    */     
/*  64: 83 */     this.dataOffset = (dstIndex - this.headerStart + 26);
/*  65:    */     
/*  66: 85 */     this.pad = ((this.dataOffset - this.headerStart) % 4);
/*  67: 86 */     this.pad = (this.pad == 0 ? 0 : 4 - this.pad);
/*  68: 87 */     this.dataOffset += this.pad;
/*  69:    */     
/*  70: 89 */     writeInt2(this.fid, dst, dstIndex);
/*  71: 90 */     dstIndex += 2;
/*  72: 91 */     writeInt4(this.offset, dst, dstIndex);
/*  73: 92 */     dstIndex += 4;
/*  74: 93 */     for (int i = 0; i < 4; i++) {
/*  75: 94 */       dst[(dstIndex++)] = -1;
/*  76:    */     }
/*  77: 96 */     writeInt2(this.writeMode, dst, dstIndex);
/*  78: 97 */     dstIndex += 2;
/*  79: 98 */     writeInt2(this.remaining, dst, dstIndex);
/*  80: 99 */     dstIndex += 2;
/*  81:100 */     dst[(dstIndex++)] = 0;
/*  82:101 */     dst[(dstIndex++)] = 0;
/*  83:102 */     writeInt2(this.dataLength, dst, dstIndex);
/*  84:103 */     dstIndex += 2;
/*  85:104 */     writeInt2(this.dataOffset, dst, dstIndex);
/*  86:105 */     dstIndex += 2;
/*  87:106 */     writeInt4(this.offset >> 32, dst, dstIndex);
/*  88:107 */     dstIndex += 4;
/*  89:    */     
/*  90:109 */     return dstIndex - start;
/*  91:    */   }
/*  92:    */   
/*  93:    */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/*  94:    */   {
/*  95:112 */     int start = dstIndex;
/*  96:114 */     while (this.pad-- > 0) {
/*  97:115 */       dst[(dstIndex++)] = -18;
/*  98:    */     }
/*  99:117 */     System.arraycopy(this.b, this.off, dst, dstIndex, this.dataLength);
/* 100:118 */     dstIndex += this.dataLength;
/* 101:    */     
/* 102:120 */     return dstIndex - start;
/* 103:    */   }
/* 104:    */   
/* 105:    */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 106:    */   {
/* 107:123 */     return 0;
/* 108:    */   }
/* 109:    */   
/* 110:    */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 111:    */   {
/* 112:126 */     return 0;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String toString()
/* 116:    */   {
/* 117:129 */     return new String("SmbComWriteAndX[" + super.toString() + ",fid=" + this.fid + ",offset=" + this.offset + ",writeMode=" + this.writeMode + ",remaining=" + this.remaining + ",dataLength=" + this.dataLength + ",dataOffset=" + this.dataOffset + "]");
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComWriteAndX
 * JD-Core Version:    0.7.0.1
 */