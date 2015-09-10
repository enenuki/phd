/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import jcifs.Config;
/*   5:    */ import jcifs.util.Hexdump;
/*   6:    */ 
/*   7:    */ abstract class SmbComTransaction
/*   8:    */   extends ServerMessageBlock
/*   9:    */   implements Enumeration
/*  10:    */ {
/*  11: 27 */   private static final int DEFAULT_MAX_DATA_COUNT = Config.getInt("jcifs.smb.client.transaction_buf_size", 65535) - 512;
/*  12:    */   private static final int PRIMARY_SETUP_OFFSET = 61;
/*  13:    */   private static final int SECONDARY_PARAMETER_OFFSET = 51;
/*  14:    */   private static final int DISCONNECT_TID = 1;
/*  15:    */   private static final int ONE_WAY_TRANSACTION = 2;
/*  16:    */   private static final int PADDING_SIZE = 2;
/*  17: 40 */   private int flags = 0;
/*  18:    */   private int fid;
/*  19: 42 */   private int pad = 0;
/*  20: 43 */   private int pad1 = 0;
/*  21: 44 */   private boolean hasMore = true;
/*  22: 45 */   private boolean isPrimary = true;
/*  23:    */   private int bufParameterOffset;
/*  24:    */   private int bufDataOffset;
/*  25:    */   static final int TRANSACTION_BUF_SIZE = 65535;
/*  26:    */   static final byte TRANS2_FIND_FIRST2 = 1;
/*  27:    */   static final byte TRANS2_FIND_NEXT2 = 2;
/*  28:    */   static final byte TRANS2_QUERY_FS_INFORMATION = 3;
/*  29:    */   static final byte TRANS2_QUERY_PATH_INFORMATION = 5;
/*  30:    */   static final byte TRANS2_GET_DFS_REFERRAL = 16;
/*  31:    */   static final byte TRANS2_SET_FILE_INFORMATION = 8;
/*  32:    */   static final int NET_SHARE_ENUM = 0;
/*  33:    */   static final int NET_SERVER_ENUM2 = 104;
/*  34:    */   static final int NET_SERVER_ENUM3 = 215;
/*  35:    */   static final byte TRANS_PEEK_NAMED_PIPE = 35;
/*  36:    */   static final byte TRANS_WAIT_NAMED_PIPE = 83;
/*  37:    */   static final byte TRANS_CALL_NAMED_PIPE = 84;
/*  38:    */   static final byte TRANS_TRANSACT_NAMED_PIPE = 38;
/*  39:    */   protected int primarySetupOffset;
/*  40:    */   protected int secondaryParameterOffset;
/*  41:    */   protected int parameterCount;
/*  42:    */   protected int parameterOffset;
/*  43:    */   protected int parameterDisplacement;
/*  44:    */   protected int dataCount;
/*  45:    */   protected int dataOffset;
/*  46:    */   protected int dataDisplacement;
/*  47:    */   int totalParameterCount;
/*  48:    */   int totalDataCount;
/*  49:    */   int maxParameterCount;
/*  50: 79 */   int maxDataCount = DEFAULT_MAX_DATA_COUNT;
/*  51:    */   byte maxSetupCount;
/*  52: 81 */   int timeout = 0;
/*  53: 82 */   int setupCount = 1;
/*  54:    */   byte subCommand;
/*  55: 84 */   String name = "";
/*  56:    */   int maxBufferSize;
/*  57:    */   byte[] txn_buf;
/*  58:    */   
/*  59:    */   SmbComTransaction()
/*  60:    */   {
/*  61: 90 */     this.maxParameterCount = 1024;
/*  62: 91 */     this.primarySetupOffset = 61;
/*  63: 92 */     this.secondaryParameterOffset = 51;
/*  64:    */   }
/*  65:    */   
/*  66:    */   void reset()
/*  67:    */   {
/*  68: 96 */     super.reset();
/*  69: 97 */     this.isPrimary = (this.hasMore = 1);
/*  70:    */   }
/*  71:    */   
/*  72:    */   void reset(int key, String lastName)
/*  73:    */   {
/*  74:100 */     reset();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean hasMoreElements()
/*  78:    */   {
/*  79:103 */     return this.hasMore;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object nextElement()
/*  83:    */   {
/*  84:106 */     if (this.isPrimary)
/*  85:    */     {
/*  86:107 */       this.isPrimary = false;
/*  87:    */       
/*  88:109 */       this.parameterOffset = (this.primarySetupOffset + this.setupCount * 2 + 2);
/*  89:110 */       if (this.command != -96)
/*  90:    */       {
/*  91:111 */         if ((this.command == 37) && (!isResponse())) {
/*  92:112 */           this.parameterOffset += stringWireLength(this.name, this.parameterOffset);
/*  93:    */         }
/*  94:    */       }
/*  95:114 */       else if (this.command == -96) {
/*  96:115 */         this.parameterOffset += 2;
/*  97:    */       }
/*  98:117 */       this.pad = (this.parameterOffset % 2);
/*  99:118 */       this.pad = (this.pad == 0 ? 0 : 2 - this.pad);
/* 100:119 */       this.parameterOffset += this.pad;
/* 101:    */       
/* 102:121 */       this.totalParameterCount = writeParametersWireFormat(this.txn_buf, this.bufParameterOffset);
/* 103:122 */       this.bufDataOffset = this.totalParameterCount;
/* 104:    */       
/* 105:124 */       int available = this.maxBufferSize - this.parameterOffset;
/* 106:125 */       this.parameterCount = Math.min(this.totalParameterCount, available);
/* 107:126 */       available -= this.parameterCount;
/* 108:    */       
/* 109:128 */       this.dataOffset = (this.parameterOffset + this.parameterCount);
/* 110:129 */       this.pad1 = (this.dataOffset % 2);
/* 111:130 */       this.pad1 = (this.pad1 == 0 ? 0 : 2 - this.pad1);
/* 112:131 */       this.dataOffset += this.pad1;
/* 113:    */       
/* 114:133 */       this.totalDataCount = writeDataWireFormat(this.txn_buf, this.bufDataOffset);
/* 115:    */       
/* 116:135 */       this.dataCount = Math.min(this.totalDataCount, available);
/* 117:    */     }
/* 118:    */     else
/* 119:    */     {
/* 120:137 */       if (this.command != -96) {
/* 121:138 */         this.command = 38;
/* 122:    */       } else {
/* 123:140 */         this.command = -95;
/* 124:    */       }
/* 125:144 */       this.parameterOffset = 51;
/* 126:145 */       if (this.totalParameterCount - this.parameterDisplacement > 0)
/* 127:    */       {
/* 128:146 */         this.pad = (this.parameterOffset % 2);
/* 129:147 */         this.pad = (this.pad == 0 ? 0 : 2 - this.pad);
/* 130:148 */         this.parameterOffset += this.pad;
/* 131:    */       }
/* 132:152 */       this.parameterDisplacement += this.parameterCount;
/* 133:    */       
/* 134:154 */       int available = this.maxBufferSize - this.parameterOffset - this.pad;
/* 135:155 */       this.parameterCount = Math.min(this.totalParameterCount - this.parameterDisplacement, available);
/* 136:156 */       available -= this.parameterCount;
/* 137:    */       
/* 138:158 */       this.dataOffset = (this.parameterOffset + this.parameterCount);
/* 139:159 */       this.pad1 = (this.dataOffset % 2);
/* 140:160 */       this.pad1 = (this.pad1 == 0 ? 0 : 2 - this.pad1);
/* 141:161 */       this.dataOffset += this.pad1;
/* 142:    */       
/* 143:163 */       this.dataDisplacement += this.dataCount;
/* 144:    */       
/* 145:165 */       available -= this.pad1;
/* 146:166 */       this.dataCount = Math.min(this.totalDataCount - this.dataDisplacement, available);
/* 147:    */     }
/* 148:168 */     if ((this.parameterDisplacement + this.parameterCount >= this.totalParameterCount) && (this.dataDisplacement + this.dataCount >= this.totalDataCount)) {
/* 149:170 */       this.hasMore = false;
/* 150:    */     }
/* 151:172 */     return this;
/* 152:    */   }
/* 153:    */   
/* 154:    */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 155:    */   {
/* 156:175 */     int start = dstIndex;
/* 157:    */     
/* 158:177 */     writeInt2(this.totalParameterCount, dst, dstIndex);
/* 159:178 */     dstIndex += 2;
/* 160:179 */     writeInt2(this.totalDataCount, dst, dstIndex);
/* 161:180 */     dstIndex += 2;
/* 162:181 */     if (this.command != 38)
/* 163:    */     {
/* 164:182 */       writeInt2(this.maxParameterCount, dst, dstIndex);
/* 165:183 */       dstIndex += 2;
/* 166:184 */       writeInt2(this.maxDataCount, dst, dstIndex);
/* 167:185 */       dstIndex += 2;
/* 168:186 */       dst[(dstIndex++)] = this.maxSetupCount;
/* 169:187 */       dst[(dstIndex++)] = 0;
/* 170:188 */       writeInt2(this.flags, dst, dstIndex);
/* 171:189 */       dstIndex += 2;
/* 172:190 */       writeInt4(this.timeout, dst, dstIndex);
/* 173:191 */       dstIndex += 4;
/* 174:192 */       dst[(dstIndex++)] = 0;
/* 175:193 */       dst[(dstIndex++)] = 0;
/* 176:    */     }
/* 177:195 */     writeInt2(this.parameterCount, dst, dstIndex);
/* 178:196 */     dstIndex += 2;
/* 179:    */     
/* 180:198 */     writeInt2(this.parameterOffset, dst, dstIndex);
/* 181:199 */     dstIndex += 2;
/* 182:200 */     if (this.command == 38)
/* 183:    */     {
/* 184:201 */       writeInt2(this.parameterDisplacement, dst, dstIndex);
/* 185:202 */       dstIndex += 2;
/* 186:    */     }
/* 187:204 */     writeInt2(this.dataCount, dst, dstIndex);
/* 188:205 */     dstIndex += 2;
/* 189:206 */     writeInt2(this.dataCount == 0 ? 0 : this.dataOffset, dst, dstIndex);
/* 190:207 */     dstIndex += 2;
/* 191:208 */     if (this.command == 38)
/* 192:    */     {
/* 193:209 */       writeInt2(this.dataDisplacement, dst, dstIndex);
/* 194:210 */       dstIndex += 2;
/* 195:    */     }
/* 196:    */     else
/* 197:    */     {
/* 198:212 */       dst[(dstIndex++)] = ((byte)this.setupCount);
/* 199:213 */       dst[(dstIndex++)] = 0;
/* 200:214 */       dstIndex += writeSetupWireFormat(dst, dstIndex);
/* 201:    */     }
/* 202:217 */     return dstIndex - start;
/* 203:    */   }
/* 204:    */   
/* 205:    */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 206:    */   {
/* 207:220 */     int start = dstIndex;
/* 208:221 */     int p = this.pad;
/* 209:223 */     if ((this.command == 37) && (!isResponse())) {
/* 210:224 */       dstIndex += writeString(this.name, dst, dstIndex);
/* 211:    */     }
/* 212:227 */     if (this.parameterCount > 0)
/* 213:    */     {
/* 214:228 */       while (p-- > 0) {
/* 215:229 */         dst[(dstIndex++)] = 0;
/* 216:    */       }
/* 217:232 */       System.arraycopy(this.txn_buf, this.bufParameterOffset, dst, dstIndex, this.parameterCount);
/* 218:233 */       dstIndex += this.parameterCount;
/* 219:    */     }
/* 220:236 */     if (this.dataCount > 0)
/* 221:    */     {
/* 222:237 */       p = this.pad1;
/* 223:238 */       while (p-- > 0) {
/* 224:239 */         dst[(dstIndex++)] = 0;
/* 225:    */       }
/* 226:241 */       System.arraycopy(this.txn_buf, this.bufDataOffset, dst, dstIndex, this.dataCount);
/* 227:242 */       this.bufDataOffset += this.dataCount;
/* 228:243 */       dstIndex += this.dataCount;
/* 229:    */     }
/* 230:246 */     return dstIndex - start;
/* 231:    */   }
/* 232:    */   
/* 233:    */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 234:    */   {
/* 235:249 */     return 0;
/* 236:    */   }
/* 237:    */   
/* 238:    */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 239:    */   {
/* 240:252 */     return 0;
/* 241:    */   }
/* 242:    */   
/* 243:    */   abstract int writeSetupWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 244:    */   
/* 245:    */   abstract int writeParametersWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 246:    */   
/* 247:    */   abstract int writeDataWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 248:    */   
/* 249:    */   abstract int readSetupWireFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/* 250:    */   
/* 251:    */   abstract int readParametersWireFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/* 252:    */   
/* 253:    */   abstract int readDataWireFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/* 254:    */   
/* 255:    */   public String toString()
/* 256:    */   {
/* 257:263 */     return new String(super.toString() + ",totalParameterCount=" + this.totalParameterCount + ",totalDataCount=" + this.totalDataCount + ",maxParameterCount=" + this.maxParameterCount + ",maxDataCount=" + this.maxDataCount + ",maxSetupCount=" + this.maxSetupCount + ",flags=0x" + Hexdump.toHexString(this.flags, 2) + ",timeout=" + this.timeout + ",parameterCount=" + this.parameterCount + ",parameterOffset=" + this.parameterOffset + ",parameterDisplacement=" + this.parameterDisplacement + ",dataCount=" + this.dataCount + ",dataOffset=" + this.dataOffset + ",dataDisplacement=" + this.dataDisplacement + ",setupCount=" + this.setupCount + ",pad=" + this.pad + ",pad1=" + this.pad1);
/* 258:    */   }
/* 259:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComTransaction
 * JD-Core Version:    0.7.0.1
 */