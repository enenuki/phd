/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.Date;
/*   5:    */ import jcifs.util.LogStream;
/*   6:    */ 
/*   7:    */ class Trans2FindFirst2Response
/*   8:    */   extends SmbComTransactionResponse
/*   9:    */ {
/*  10:    */   static final int SMB_INFO_STANDARD = 1;
/*  11:    */   static final int SMB_INFO_QUERY_EA_SIZE = 2;
/*  12:    */   static final int SMB_INFO_QUERY_EAS_FROM_LIST = 3;
/*  13:    */   static final int SMB_FIND_FILE_DIRECTORY_INFO = 257;
/*  14:    */   static final int SMB_FIND_FILE_FULL_DIRECTORY_INFO = 258;
/*  15:    */   static final int SMB_FILE_NAMES_INFO = 259;
/*  16:    */   static final int SMB_FILE_BOTH_DIRECTORY_INFO = 260;
/*  17:    */   int sid;
/*  18:    */   boolean isEndOfSearch;
/*  19:    */   int eaErrorOffset;
/*  20:    */   int lastNameOffset;
/*  21:    */   int lastNameBufferIndex;
/*  22:    */   String lastName;
/*  23:    */   int resumeKey;
/*  24:    */   
/*  25:    */   class SmbFindFileBothDirectoryInfo
/*  26:    */     implements FileEntry
/*  27:    */   {
/*  28:    */     int nextEntryOffset;
/*  29:    */     int fileIndex;
/*  30:    */     long creationTime;
/*  31:    */     long lastAccessTime;
/*  32:    */     long lastWriteTime;
/*  33:    */     long changeTime;
/*  34:    */     long endOfFile;
/*  35:    */     long allocationSize;
/*  36:    */     int extFileAttributes;
/*  37:    */     int fileNameLength;
/*  38:    */     int eaSize;
/*  39:    */     int shortNameLength;
/*  40:    */     String shortName;
/*  41:    */     String filename;
/*  42:    */     
/*  43:    */     SmbFindFileBothDirectoryInfo() {}
/*  44:    */     
/*  45:    */     public String getName()
/*  46:    */     {
/*  47: 53 */       return this.filename;
/*  48:    */     }
/*  49:    */     
/*  50:    */     public int getType()
/*  51:    */     {
/*  52: 56 */       return 1;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public int getAttributes()
/*  56:    */     {
/*  57: 59 */       return this.extFileAttributes;
/*  58:    */     }
/*  59:    */     
/*  60:    */     public long createTime()
/*  61:    */     {
/*  62: 62 */       return this.creationTime;
/*  63:    */     }
/*  64:    */     
/*  65:    */     public long lastModified()
/*  66:    */     {
/*  67: 65 */       return this.lastWriteTime;
/*  68:    */     }
/*  69:    */     
/*  70:    */     public long length()
/*  71:    */     {
/*  72: 68 */       return this.endOfFile;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public String toString()
/*  76:    */     {
/*  77: 72 */       return new String("SmbFindFileBothDirectoryInfo[nextEntryOffset=" + this.nextEntryOffset + ",fileIndex=" + this.fileIndex + ",creationTime=" + new Date(this.creationTime) + ",lastAccessTime=" + new Date(this.lastAccessTime) + ",lastWriteTime=" + new Date(this.lastWriteTime) + ",changeTime=" + new Date(this.changeTime) + ",endOfFile=" + this.endOfFile + ",allocationSize=" + this.allocationSize + ",extFileAttributes=" + this.extFileAttributes + ",fileNameLength=" + this.fileNameLength + ",eaSize=" + this.eaSize + ",shortNameLength=" + this.shortNameLength + ",shortName=" + this.shortName + ",filename=" + this.filename + "]");
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   Trans2FindFirst2Response()
/*  82:    */   {
/*  83: 99 */     this.command = 50;
/*  84:100 */     this.subCommand = 1;
/*  85:    */   }
/*  86:    */   
/*  87:    */   String readString(byte[] src, int srcIndex, int len)
/*  88:    */   {
/*  89:104 */     String str = null;
/*  90:    */     try
/*  91:    */     {
/*  92:106 */       if (this.useUnicode)
/*  93:    */       {
/*  94:108 */         str = new String(src, srcIndex, len, "UTF-16LE");
/*  95:    */       }
/*  96:    */       else
/*  97:    */       {
/*  98:123 */         if ((len > 0) && (src[(srcIndex + len - 1)] == 0)) {
/*  99:124 */           len--;
/* 100:    */         }
/* 101:126 */         str = new String(src, srcIndex, len, SmbConstants.OEM_ENCODING);
/* 102:    */       }
/* 103:    */     }
/* 104:    */     catch (UnsupportedEncodingException uee)
/* 105:    */     {
/* 106:129 */       if (LogStream.level > 1) {
/* 107:130 */         uee.printStackTrace(log);
/* 108:    */       }
/* 109:    */     }
/* 110:132 */     return str;
/* 111:    */   }
/* 112:    */   
/* 113:    */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/* 114:    */   {
/* 115:135 */     return 0;
/* 116:    */   }
/* 117:    */   
/* 118:    */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/* 119:    */   {
/* 120:138 */     return 0;
/* 121:    */   }
/* 122:    */   
/* 123:    */   int writeDataWireFormat(byte[] dst, int dstIndex)
/* 124:    */   {
/* 125:141 */     return 0;
/* 126:    */   }
/* 127:    */   
/* 128:    */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/* 129:    */   {
/* 130:144 */     return 0;
/* 131:    */   }
/* 132:    */   
/* 133:    */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 134:    */   {
/* 135:147 */     int start = bufferIndex;
/* 136:149 */     if (this.subCommand == 1)
/* 137:    */     {
/* 138:150 */       this.sid = readInt2(buffer, bufferIndex);
/* 139:151 */       bufferIndex += 2;
/* 140:    */     }
/* 141:153 */     this.numEntries = readInt2(buffer, bufferIndex);
/* 142:154 */     bufferIndex += 2;
/* 143:155 */     this.isEndOfSearch = ((buffer[bufferIndex] & 0x1) == 1);
/* 144:156 */     bufferIndex += 2;
/* 145:157 */     this.eaErrorOffset = readInt2(buffer, bufferIndex);
/* 146:158 */     bufferIndex += 2;
/* 147:159 */     this.lastNameOffset = readInt2(buffer, bufferIndex);
/* 148:160 */     bufferIndex += 2;
/* 149:    */     
/* 150:162 */     return bufferIndex - start;
/* 151:    */   }
/* 152:    */   
/* 153:    */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 154:    */   {
/* 155:165 */     int start = bufferIndex;
/* 156:    */     
/* 157:    */ 
/* 158:168 */     this.lastNameBufferIndex = (bufferIndex + this.lastNameOffset);
/* 159:    */     
/* 160:170 */     this.results = new SmbFindFileBothDirectoryInfo[this.numEntries];
/* 161:171 */     for (int i = 0; i < this.numEntries; i++)
/* 162:    */     {
/* 163:172 */       void tmp50_47 = new SmbFindFileBothDirectoryInfo();SmbFindFileBothDirectoryInfo e = tmp50_47;this.results[i] = tmp50_47;
/* 164:    */       
/* 165:174 */       e.nextEntryOffset = readInt4(buffer, bufferIndex);
/* 166:175 */       e.fileIndex = readInt4(buffer, bufferIndex + 4);
/* 167:176 */       e.creationTime = readTime(buffer, bufferIndex + 8);
/* 168:    */       
/* 169:178 */       e.lastWriteTime = readTime(buffer, bufferIndex + 24);
/* 170:    */       
/* 171:180 */       e.endOfFile = readInt8(buffer, bufferIndex + 40);
/* 172:    */       
/* 173:182 */       e.extFileAttributes = readInt4(buffer, bufferIndex + 56);
/* 174:183 */       e.fileNameLength = readInt4(buffer, bufferIndex + 60);
/* 175:    */       
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:191 */       e.filename = readString(buffer, bufferIndex + 94, e.fileNameLength);
/* 183:201 */       if ((this.lastNameBufferIndex >= bufferIndex) && ((e.nextEntryOffset == 0) || (this.lastNameBufferIndex < bufferIndex + e.nextEntryOffset)))
/* 184:    */       {
/* 185:203 */         this.lastName = e.filename;
/* 186:204 */         this.resumeKey = e.fileIndex;
/* 187:    */       }
/* 188:207 */       bufferIndex += e.nextEntryOffset;
/* 189:    */     }
/* 190:216 */     return this.dataCount;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String toString()
/* 194:    */   {
/* 195:    */     String c;
/* 196:    */     String c;
/* 197:220 */     if (this.subCommand == 1) {
/* 198:221 */       c = "Trans2FindFirst2Response[";
/* 199:    */     } else {
/* 200:223 */       c = "Trans2FindNext2Response[";
/* 201:    */     }
/* 202:225 */     return new String(c + super.toString() + ",sid=" + this.sid + ",searchCount=" + this.numEntries + ",isEndOfSearch=" + this.isEndOfSearch + ",eaErrorOffset=" + this.eaErrorOffset + ",lastNameOffset=" + this.lastNameOffset + ",lastName=" + this.lastName + "]");
/* 203:    */   }
/* 204:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2FindFirst2Response
 * JD-Core Version:    0.7.0.1
 */