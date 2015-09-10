/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.util.Date;
/*   4:    */ import jcifs.util.Hexdump;
/*   5:    */ 
/*   6:    */ class Trans2QueryPathInformationResponse
/*   7:    */   extends SmbComTransactionResponse
/*   8:    */ {
/*   9:    */   static final int SMB_QUERY_FILE_BASIC_INFO = 257;
/*  10:    */   static final int SMB_QUERY_FILE_STANDARD_INFO = 258;
/*  11:    */   private int informationLevel;
/*  12:    */   Info info;
/*  13:    */   
/*  14:    */   class SmbQueryFileBasicInfo
/*  15:    */     implements Info
/*  16:    */   {
/*  17:    */     long createTime;
/*  18:    */     long lastAccessTime;
/*  19:    */     long lastWriteTime;
/*  20:    */     long changeTime;
/*  21:    */     int attributes;
/*  22:    */     
/*  23:    */     SmbQueryFileBasicInfo() {}
/*  24:    */     
/*  25:    */     public int getAttributes()
/*  26:    */     {
/*  27: 38 */       return this.attributes;
/*  28:    */     }
/*  29:    */     
/*  30:    */     public long getCreateTime()
/*  31:    */     {
/*  32: 41 */       return this.createTime;
/*  33:    */     }
/*  34:    */     
/*  35:    */     public long getLastWriteTime()
/*  36:    */     {
/*  37: 44 */       return this.lastWriteTime;
/*  38:    */     }
/*  39:    */     
/*  40:    */     public long getSize()
/*  41:    */     {
/*  42: 47 */       return 0L;
/*  43:    */     }
/*  44:    */     
/*  45:    */     public String toString()
/*  46:    */     {
/*  47: 50 */       return new String("SmbQueryFileBasicInfo[createTime=" + new Date(this.createTime) + ",lastAccessTime=" + new Date(this.lastAccessTime) + ",lastWriteTime=" + new Date(this.lastWriteTime) + ",changeTime=" + new Date(this.changeTime) + ",attributes=0x" + Hexdump.toHexString(this.attributes, 4) + "]");
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   class SmbQueryFileStandardInfo
/*  52:    */     implements Info
/*  53:    */   {
/*  54:    */     long allocationSize;
/*  55:    */     long endOfFile;
/*  56:    */     int numberOfLinks;
/*  57:    */     boolean deletePending;
/*  58:    */     boolean directory;
/*  59:    */     
/*  60:    */     SmbQueryFileStandardInfo() {}
/*  61:    */     
/*  62:    */     public int getAttributes()
/*  63:    */     {
/*  64: 66 */       return 0;
/*  65:    */     }
/*  66:    */     
/*  67:    */     public long getCreateTime()
/*  68:    */     {
/*  69: 69 */       return 0L;
/*  70:    */     }
/*  71:    */     
/*  72:    */     public long getLastWriteTime()
/*  73:    */     {
/*  74: 72 */       return 0L;
/*  75:    */     }
/*  76:    */     
/*  77:    */     public long getSize()
/*  78:    */     {
/*  79: 75 */       return this.endOfFile;
/*  80:    */     }
/*  81:    */     
/*  82:    */     public String toString()
/*  83:    */     {
/*  84: 78 */       return new String("SmbQueryInfoStandard[allocationSize=" + this.allocationSize + ",endOfFile=" + this.endOfFile + ",numberOfLinks=" + this.numberOfLinks + ",deletePending=" + this.deletePending + ",directory=" + this.directory + "]");
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   Trans2QueryPathInformationResponse(int informationLevel)
/*  89:    */   {
/*  90: 92 */     this.informationLevel = informationLevel;
/*  91: 93 */     this.subCommand = 5;
/*  92:    */   }
/*  93:    */   
/*  94:    */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/*  95:    */   {
/*  96: 97 */     return 0;
/*  97:    */   }
/*  98:    */   
/*  99:    */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 100:    */   {
/* 101:100 */     return 0;
/* 102:    */   }
/* 103:    */   
/* 104:    */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 105:    */   {
/* 106:103 */     return 0;
/* 107:    */   }
/* 108:    */   
/* 109:    */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 110:    */   {
/* 111:106 */     return 0;
/* 112:    */   }
/* 113:    */   
/* 114:    */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 115:    */   {
/* 116:110 */     return 2;
/* 117:    */   }
/* 118:    */   
/* 119:    */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 120:    */   {
/* 121:113 */     switch (this.informationLevel)
/* 122:    */     {
/* 123:    */     case 257: 
/* 124:115 */       return readSmbQueryFileBasicInfoWireFormat(buffer, bufferIndex);
/* 125:    */     case 258: 
/* 126:117 */       return readSmbQueryFileStandardInfoWireFormat(buffer, bufferIndex);
/* 127:    */     }
/* 128:119 */     return 0;
/* 129:    */   }
/* 130:    */   
/* 131:    */   int readSmbQueryFileStandardInfoWireFormat(byte[] buffer, int bufferIndex)
/* 132:    */   {
/* 133:123 */     int start = bufferIndex;
/* 134:    */     
/* 135:125 */     SmbQueryFileStandardInfo info = new SmbQueryFileStandardInfo();
/* 136:126 */     info.allocationSize = readInt8(buffer, bufferIndex);
/* 137:127 */     bufferIndex += 8;
/* 138:128 */     info.endOfFile = readInt8(buffer, bufferIndex);
/* 139:129 */     bufferIndex += 8;
/* 140:130 */     info.numberOfLinks = readInt4(buffer, bufferIndex);
/* 141:131 */     bufferIndex += 4;
/* 142:132 */     info.deletePending = ((buffer[(bufferIndex++)] & 0xFF) > 0);
/* 143:133 */     info.directory = ((buffer[(bufferIndex++)] & 0xFF) > 0);
/* 144:134 */     this.info = info;
/* 145:    */     
/* 146:136 */     return bufferIndex - start;
/* 147:    */   }
/* 148:    */   
/* 149:    */   int readSmbQueryFileBasicInfoWireFormat(byte[] buffer, int bufferIndex)
/* 150:    */   {
/* 151:139 */     int start = bufferIndex;
/* 152:    */     
/* 153:141 */     SmbQueryFileBasicInfo info = new SmbQueryFileBasicInfo();
/* 154:142 */     info.createTime = readTime(buffer, bufferIndex);
/* 155:143 */     bufferIndex += 8;
/* 156:144 */     info.lastAccessTime = readTime(buffer, bufferIndex);
/* 157:145 */     bufferIndex += 8;
/* 158:146 */     info.lastWriteTime = readTime(buffer, bufferIndex);
/* 159:147 */     bufferIndex += 8;
/* 160:148 */     info.changeTime = readTime(buffer, bufferIndex);
/* 161:149 */     bufferIndex += 8;
/* 162:150 */     info.attributes = readInt2(buffer, bufferIndex);
/* 163:151 */     bufferIndex += 2;
/* 164:152 */     this.info = info;
/* 165:    */     
/* 166:154 */     return bufferIndex - start;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String toString()
/* 170:    */   {
/* 171:157 */     return new String("Trans2QueryPathInformationResponse[" + super.toString() + "]");
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2QueryPathInformationResponse
 * JD-Core Version:    0.7.0.1
 */