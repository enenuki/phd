/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import jcifs.util.LogStream;
/*   5:    */ 
/*   6:    */ abstract class SmbComTransactionResponse
/*   7:    */   extends ServerMessageBlock
/*   8:    */   implements Enumeration
/*   9:    */ {
/*  10:    */   private static final int SETUP_OFFSET = 61;
/*  11:    */   private static final int DISCONNECT_TID = 1;
/*  12:    */   private static final int ONE_WAY_TRANSACTION = 2;
/*  13:    */   private int pad;
/*  14:    */   private int pad1;
/*  15:    */   private boolean parametersDone;
/*  16:    */   private boolean dataDone;
/*  17:    */   protected int totalParameterCount;
/*  18:    */   protected int totalDataCount;
/*  19:    */   protected int parameterCount;
/*  20:    */   protected int parameterOffset;
/*  21:    */   protected int parameterDisplacement;
/*  22:    */   protected int dataOffset;
/*  23:    */   protected int dataDisplacement;
/*  24:    */   protected int setupCount;
/*  25:    */   protected int bufParameterStart;
/*  26:    */   protected int bufDataStart;
/*  27:    */   int dataCount;
/*  28:    */   byte subCommand;
/*  29: 48 */   boolean hasMore = true;
/*  30: 49 */   boolean isPrimary = true;
/*  31:    */   byte[] txn_buf;
/*  32:    */   int status;
/*  33:    */   int numEntries;
/*  34:    */   FileEntry[] results;
/*  35:    */   
/*  36:    */   SmbComTransactionResponse()
/*  37:    */   {
/*  38: 58 */     this.txn_buf = null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   void reset()
/*  42:    */   {
/*  43: 62 */     super.reset();
/*  44: 63 */     this.bufDataStart = 0;
/*  45: 64 */     this.isPrimary = (this.hasMore = 1);
/*  46: 65 */     this.parametersDone = (this.dataDone = 0);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasMoreElements()
/*  50:    */   {
/*  51: 68 */     return (this.errorCode == 0) && (this.hasMore);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object nextElement()
/*  55:    */   {
/*  56: 71 */     if (this.isPrimary) {
/*  57: 72 */       this.isPrimary = false;
/*  58:    */     }
/*  59: 74 */     return this;
/*  60:    */   }
/*  61:    */   
/*  62:    */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/*  63:    */   {
/*  64: 77 */     return 0;
/*  65:    */   }
/*  66:    */   
/*  67:    */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/*  68:    */   {
/*  69: 80 */     return 0;
/*  70:    */   }
/*  71:    */   
/*  72:    */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/*  73:    */   {
/*  74: 83 */     int start = bufferIndex;
/*  75:    */     
/*  76: 85 */     this.totalParameterCount = readInt2(buffer, bufferIndex);
/*  77: 86 */     if (this.bufDataStart == 0) {
/*  78: 87 */       this.bufDataStart = this.totalParameterCount;
/*  79:    */     }
/*  80: 89 */     bufferIndex += 2;
/*  81: 90 */     this.totalDataCount = readInt2(buffer, bufferIndex);
/*  82: 91 */     bufferIndex += 4;
/*  83: 92 */     this.parameterCount = readInt2(buffer, bufferIndex);
/*  84: 93 */     bufferIndex += 2;
/*  85: 94 */     this.parameterOffset = readInt2(buffer, bufferIndex);
/*  86: 95 */     bufferIndex += 2;
/*  87: 96 */     this.parameterDisplacement = readInt2(buffer, bufferIndex);
/*  88: 97 */     bufferIndex += 2;
/*  89: 98 */     this.dataCount = readInt2(buffer, bufferIndex);
/*  90: 99 */     bufferIndex += 2;
/*  91:100 */     this.dataOffset = readInt2(buffer, bufferIndex);
/*  92:101 */     bufferIndex += 2;
/*  93:102 */     this.dataDisplacement = readInt2(buffer, bufferIndex);
/*  94:103 */     bufferIndex += 2;
/*  95:104 */     this.setupCount = (buffer[bufferIndex] & 0xFF);
/*  96:105 */     bufferIndex += 2;
/*  97:106 */     if ((this.setupCount != 0) && 
/*  98:107 */       (LogStream.level > 2)) {
/*  99:108 */       log.println("setupCount is not zero: " + this.setupCount);
/* 100:    */     }
/* 101:111 */     return bufferIndex - start;
/* 102:    */   }
/* 103:    */   
/* 104:    */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 105:    */   {
/* 106:114 */     this.pad = (this.pad1 = 0);
/* 107:117 */     if (this.parameterCount > 0)
/* 108:    */     {
/* 109:118 */       bufferIndex += (this.pad = this.parameterOffset - (bufferIndex - this.headerStart));
/* 110:119 */       System.arraycopy(buffer, bufferIndex, this.txn_buf, this.bufParameterStart + this.parameterDisplacement, this.parameterCount);
/* 111:    */       
/* 112:121 */       bufferIndex += this.parameterCount;
/* 113:    */     }
/* 114:123 */     if (this.dataCount > 0)
/* 115:    */     {
/* 116:124 */       bufferIndex += (this.pad1 = this.dataOffset - (bufferIndex - this.headerStart));
/* 117:125 */       System.arraycopy(buffer, bufferIndex, this.txn_buf, this.bufDataStart + this.dataDisplacement, this.dataCount);
/* 118:    */       
/* 119:127 */       bufferIndex += this.dataCount;
/* 120:    */     }
/* 121:134 */     if ((!this.parametersDone) && (this.parameterDisplacement + this.parameterCount == this.totalParameterCount)) {
/* 122:136 */       this.parametersDone = true;
/* 123:    */     }
/* 124:139 */     if ((!this.dataDone) && (this.dataDisplacement + this.dataCount == this.totalDataCount)) {
/* 125:140 */       this.dataDone = true;
/* 126:    */     }
/* 127:143 */     if ((this.parametersDone) && (this.dataDone))
/* 128:    */     {
/* 129:144 */       this.hasMore = false;
/* 130:145 */       readParametersWireFormat(this.txn_buf, this.bufParameterStart, this.totalParameterCount);
/* 131:146 */       readDataWireFormat(this.txn_buf, this.bufDataStart, this.totalDataCount);
/* 132:    */     }
/* 133:149 */     return this.pad + this.parameterCount + this.pad1 + this.dataCount;
/* 134:    */   }
/* 135:    */   
/* 136:    */   abstract int writeSetupWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 137:    */   
/* 138:    */   abstract int writeParametersWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 139:    */   
/* 140:    */   abstract int writeDataWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 141:    */   
/* 142:    */   abstract int readSetupWireFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/* 143:    */   
/* 144:    */   abstract int readParametersWireFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/* 145:    */   
/* 146:    */   abstract int readDataWireFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/* 147:    */   
/* 148:    */   public String toString()
/* 149:    */   {
/* 150:160 */     return new String(super.toString() + ",totalParameterCount=" + this.totalParameterCount + ",totalDataCount=" + this.totalDataCount + ",parameterCount=" + this.parameterCount + ",parameterOffset=" + this.parameterOffset + ",parameterDisplacement=" + this.parameterDisplacement + ",dataCount=" + this.dataCount + ",dataOffset=" + this.dataOffset + ",dataDisplacement=" + this.dataDisplacement + ",setupCount=" + this.setupCount + ",pad=" + this.pad + ",pad1=" + this.pad1);
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComTransactionResponse
 * JD-Core Version:    0.7.0.1
 */