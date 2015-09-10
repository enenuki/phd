/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ class Trans2QueryFSInformationResponse
/*   4:    */   extends SmbComTransactionResponse
/*   5:    */ {
/*   6:    */   static final int SMB_INFO_ALLOCATION = 1;
/*   7:    */   static final int SMB_QUERY_FS_SIZE_INFO = 259;
/*   8:    */   static final int SMB_FS_FULL_SIZE_INFORMATION = 1007;
/*   9:    */   private int informationLevel;
/*  10:    */   AllocInfo info;
/*  11:    */   
/*  12:    */   class SmbInfoAllocation
/*  13:    */     implements AllocInfo
/*  14:    */   {
/*  15:    */     long alloc;
/*  16:    */     long free;
/*  17:    */     int sectPerAlloc;
/*  18:    */     int bytesPerSect;
/*  19:    */     
/*  20:    */     SmbInfoAllocation() {}
/*  21:    */     
/*  22:    */     public long getCapacity()
/*  23:    */     {
/*  24: 37 */       return this.alloc * this.sectPerAlloc * this.bytesPerSect;
/*  25:    */     }
/*  26:    */     
/*  27:    */     public long getFree()
/*  28:    */     {
/*  29: 40 */       return this.free * this.sectPerAlloc * this.bytesPerSect;
/*  30:    */     }
/*  31:    */     
/*  32:    */     public String toString()
/*  33:    */     {
/*  34: 43 */       return new String("SmbInfoAllocation[alloc=" + this.alloc + ",free=" + this.free + ",sectPerAlloc=" + this.sectPerAlloc + ",bytesPerSect=" + this.bytesPerSect + "]");
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   Trans2QueryFSInformationResponse(int informationLevel)
/*  39:    */   {
/*  40: 55 */     this.informationLevel = informationLevel;
/*  41: 56 */     this.command = 50;
/*  42: 57 */     this.subCommand = 3;
/*  43:    */   }
/*  44:    */   
/*  45:    */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/*  46:    */   {
/*  47: 61 */     return 0;
/*  48:    */   }
/*  49:    */   
/*  50:    */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/*  51:    */   {
/*  52: 64 */     return 0;
/*  53:    */   }
/*  54:    */   
/*  55:    */   int writeDataWireFormat(byte[] dst, int dstIndex)
/*  56:    */   {
/*  57: 67 */     return 0;
/*  58:    */   }
/*  59:    */   
/*  60:    */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/*  61:    */   {
/*  62: 70 */     return 0;
/*  63:    */   }
/*  64:    */   
/*  65:    */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/*  66:    */   {
/*  67: 73 */     return 0;
/*  68:    */   }
/*  69:    */   
/*  70:    */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/*  71:    */   {
/*  72: 76 */     switch (this.informationLevel)
/*  73:    */     {
/*  74:    */     case 1: 
/*  75: 78 */       return readSmbInfoAllocationWireFormat(buffer, bufferIndex);
/*  76:    */     case 259: 
/*  77: 80 */       return readSmbQueryFSSizeInfoWireFormat(buffer, bufferIndex);
/*  78:    */     case 1007: 
/*  79: 82 */       return readFsFullSizeInformationWireFormat(buffer, bufferIndex);
/*  80:    */     }
/*  81: 84 */     return 0;
/*  82:    */   }
/*  83:    */   
/*  84:    */   int readSmbInfoAllocationWireFormat(byte[] buffer, int bufferIndex)
/*  85:    */   {
/*  86: 89 */     int start = bufferIndex;
/*  87:    */     
/*  88: 91 */     SmbInfoAllocation info = new SmbInfoAllocation();
/*  89:    */     
/*  90: 93 */     bufferIndex += 4;
/*  91:    */     
/*  92: 95 */     info.sectPerAlloc = readInt4(buffer, bufferIndex);
/*  93: 96 */     bufferIndex += 4;
/*  94:    */     
/*  95: 98 */     info.alloc = readInt4(buffer, bufferIndex);
/*  96: 99 */     bufferIndex += 4;
/*  97:    */     
/*  98:101 */     info.free = readInt4(buffer, bufferIndex);
/*  99:102 */     bufferIndex += 4;
/* 100:    */     
/* 101:104 */     info.bytesPerSect = readInt2(buffer, bufferIndex);
/* 102:105 */     bufferIndex += 4;
/* 103:    */     
/* 104:107 */     this.info = info;
/* 105:    */     
/* 106:109 */     return bufferIndex - start;
/* 107:    */   }
/* 108:    */   
/* 109:    */   int readSmbQueryFSSizeInfoWireFormat(byte[] buffer, int bufferIndex)
/* 110:    */   {
/* 111:112 */     int start = bufferIndex;
/* 112:    */     
/* 113:114 */     SmbInfoAllocation info = new SmbInfoAllocation();
/* 114:    */     
/* 115:116 */     info.alloc = readInt8(buffer, bufferIndex);
/* 116:117 */     bufferIndex += 8;
/* 117:    */     
/* 118:119 */     info.free = readInt8(buffer, bufferIndex);
/* 119:120 */     bufferIndex += 8;
/* 120:    */     
/* 121:122 */     info.sectPerAlloc = readInt4(buffer, bufferIndex);
/* 122:123 */     bufferIndex += 4;
/* 123:    */     
/* 124:125 */     info.bytesPerSect = readInt4(buffer, bufferIndex);
/* 125:126 */     bufferIndex += 4;
/* 126:    */     
/* 127:128 */     this.info = info;
/* 128:    */     
/* 129:130 */     return bufferIndex - start;
/* 130:    */   }
/* 131:    */   
/* 132:    */   int readFsFullSizeInformationWireFormat(byte[] buffer, int bufferIndex)
/* 133:    */   {
/* 134:134 */     int start = bufferIndex;
/* 135:    */     
/* 136:136 */     SmbInfoAllocation info = new SmbInfoAllocation();
/* 137:    */     
/* 138:    */ 
/* 139:139 */     info.alloc = readInt8(buffer, bufferIndex);
/* 140:140 */     bufferIndex += 8;
/* 141:    */     
/* 142:    */ 
/* 143:143 */     info.free = readInt8(buffer, bufferIndex);
/* 144:144 */     bufferIndex += 8;
/* 145:    */     
/* 146:    */ 
/* 147:147 */     bufferIndex += 8;
/* 148:    */     
/* 149:149 */     info.sectPerAlloc = readInt4(buffer, bufferIndex);
/* 150:150 */     bufferIndex += 4;
/* 151:    */     
/* 152:152 */     info.bytesPerSect = readInt4(buffer, bufferIndex);
/* 153:153 */     bufferIndex += 4;
/* 154:    */     
/* 155:155 */     this.info = info;
/* 156:    */     
/* 157:157 */     return bufferIndex - start;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String toString()
/* 161:    */   {
/* 162:161 */     return new String("Trans2QueryFSInformationResponse[" + super.toString() + "]");
/* 163:    */   }
/* 164:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2QueryFSInformationResponse
 * JD-Core Version:    0.7.0.1
 */