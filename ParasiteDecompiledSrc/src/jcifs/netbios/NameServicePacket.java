/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import jcifs.util.Hexdump;
/*   5:    */ 
/*   6:    */ abstract class NameServicePacket
/*   7:    */ {
/*   8:    */   static final int QUERY = 0;
/*   9:    */   static final int WACK = 7;
/*  10:    */   static final int FMT_ERR = 1;
/*  11:    */   static final int SRV_ERR = 2;
/*  12:    */   static final int IMP_ERR = 4;
/*  13:    */   static final int RFS_ERR = 5;
/*  14:    */   static final int ACT_ERR = 6;
/*  15:    */   static final int CFT_ERR = 7;
/*  16:    */   static final int NB_IN = 2097153;
/*  17:    */   static final int NBSTAT_IN = 2162689;
/*  18:    */   static final int NB = 32;
/*  19:    */   static final int NBSTAT = 33;
/*  20:    */   static final int IN = 1;
/*  21:    */   static final int A = 1;
/*  22:    */   static final int NS = 2;
/*  23:    */   static final int NULL = 10;
/*  24:    */   static final int HEADER_LENGTH = 12;
/*  25:    */   static final int OPCODE_OFFSET = 2;
/*  26:    */   static final int QUESTION_OFFSET = 4;
/*  27:    */   static final int ANSWER_OFFSET = 6;
/*  28:    */   static final int AUTHORITY_OFFSET = 8;
/*  29:    */   static final int ADDITIONAL_OFFSET = 10;
/*  30:    */   int addrIndex;
/*  31:    */   NbtAddress[] addrEntry;
/*  32:    */   int nameTrnId;
/*  33:    */   int opCode;
/*  34:    */   int resultCode;
/*  35:    */   int questionCount;
/*  36:    */   int answerCount;
/*  37:    */   int authorityCount;
/*  38:    */   int additionalCount;
/*  39:    */   boolean received;
/*  40:    */   boolean isResponse;
/*  41:    */   boolean isAuthAnswer;
/*  42:    */   boolean isTruncated;
/*  43:    */   boolean isRecurDesired;
/*  44:    */   boolean isRecurAvailable;
/*  45:    */   boolean isBroadcast;
/*  46:    */   Name questionName;
/*  47:    */   Name recordName;
/*  48:    */   int questionType;
/*  49:    */   int questionClass;
/*  50:    */   int recordType;
/*  51:    */   int recordClass;
/*  52:    */   int ttl;
/*  53:    */   int rDataLength;
/*  54:    */   InetAddress addr;
/*  55:    */   
/*  56:    */   static void writeInt2(int val, byte[] dst, int dstIndex)
/*  57:    */   {
/*  58: 58 */     dst[(dstIndex++)] = ((byte)(val >> 8 & 0xFF));
/*  59: 59 */     dst[dstIndex] = ((byte)(val & 0xFF));
/*  60:    */   }
/*  61:    */   
/*  62:    */   static void writeInt4(int val, byte[] dst, int dstIndex)
/*  63:    */   {
/*  64: 62 */     dst[(dstIndex++)] = ((byte)(val >> 24 & 0xFF));
/*  65: 63 */     dst[(dstIndex++)] = ((byte)(val >> 16 & 0xFF));
/*  66: 64 */     dst[(dstIndex++)] = ((byte)(val >> 8 & 0xFF));
/*  67: 65 */     dst[dstIndex] = ((byte)(val & 0xFF));
/*  68:    */   }
/*  69:    */   
/*  70:    */   static int readInt2(byte[] src, int srcIndex)
/*  71:    */   {
/*  72: 68 */     return ((src[srcIndex] & 0xFF) << 8) + (src[(srcIndex + 1)] & 0xFF);
/*  73:    */   }
/*  74:    */   
/*  75:    */   static int readInt4(byte[] src, int srcIndex)
/*  76:    */   {
/*  77: 72 */     return ((src[srcIndex] & 0xFF) << 24) + ((src[(srcIndex + 1)] & 0xFF) << 16) + ((src[(srcIndex + 2)] & 0xFF) << 8) + (src[(srcIndex + 3)] & 0xFF);
/*  78:    */   }
/*  79:    */   
/*  80:    */   static int readNameTrnId(byte[] src, int srcIndex)
/*  81:    */   {
/*  82: 79 */     return readInt2(src, srcIndex);
/*  83:    */   }
/*  84:    */   
/*  85:    */   NameServicePacket()
/*  86:    */   {
/*  87:114 */     this.isRecurDesired = true;
/*  88:115 */     this.isBroadcast = true;
/*  89:116 */     this.questionCount = 1;
/*  90:117 */     this.questionClass = 1;
/*  91:    */   }
/*  92:    */   
/*  93:    */   int writeWireFormat(byte[] dst, int dstIndex)
/*  94:    */   {
/*  95:121 */     int start = dstIndex;
/*  96:122 */     dstIndex += writeHeaderWireFormat(dst, dstIndex);
/*  97:123 */     dstIndex += writeBodyWireFormat(dst, dstIndex);
/*  98:124 */     return dstIndex - start;
/*  99:    */   }
/* 100:    */   
/* 101:    */   int readWireFormat(byte[] src, int srcIndex)
/* 102:    */   {
/* 103:127 */     int start = srcIndex;
/* 104:128 */     srcIndex += readHeaderWireFormat(src, srcIndex);
/* 105:129 */     srcIndex += readBodyWireFormat(src, srcIndex);
/* 106:130 */     return srcIndex - start;
/* 107:    */   }
/* 108:    */   
/* 109:    */   int writeHeaderWireFormat(byte[] dst, int dstIndex)
/* 110:    */   {
/* 111:134 */     int start = dstIndex;
/* 112:135 */     writeInt2(this.nameTrnId, dst, dstIndex);
/* 113:136 */     dst[(dstIndex + 2)] = ((byte)((this.isResponse ? '' : 0) + (this.opCode << 3 & 0x78) + (this.isAuthAnswer ? 4 : 0) + (this.isTruncated ? 2 : 0) + (this.isRecurDesired ? 1 : 0)));
/* 114:    */     
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:141 */     dst[(dstIndex + 2 + 1)] = ((byte)((this.isRecurAvailable ? '' : 0) + (this.isBroadcast ? 16 : 0) + (this.resultCode & 0xF)));
/* 119:    */     
/* 120:    */ 
/* 121:144 */     writeInt2(this.questionCount, dst, start + 4);
/* 122:145 */     writeInt2(this.answerCount, dst, start + 6);
/* 123:146 */     writeInt2(this.authorityCount, dst, start + 8);
/* 124:147 */     writeInt2(this.additionalCount, dst, start + 10);
/* 125:148 */     return 12;
/* 126:    */   }
/* 127:    */   
/* 128:    */   int readHeaderWireFormat(byte[] src, int srcIndex)
/* 129:    */   {
/* 130:151 */     this.nameTrnId = readInt2(src, srcIndex);
/* 131:152 */     this.isResponse = ((src[(srcIndex + 2)] & 0x80) != 0);
/* 132:153 */     this.opCode = ((src[(srcIndex + 2)] & 0x78) >> 3);
/* 133:154 */     this.isAuthAnswer = ((src[(srcIndex + 2)] & 0x4) != 0);
/* 134:155 */     this.isTruncated = ((src[(srcIndex + 2)] & 0x2) != 0);
/* 135:156 */     this.isRecurDesired = ((src[(srcIndex + 2)] & 0x1) != 0);
/* 136:157 */     this.isRecurAvailable = ((src[(srcIndex + 2 + 1)] & 0x80) != 0);
/* 137:    */     
/* 138:159 */     this.isBroadcast = ((src[(srcIndex + 2 + 1)] & 0x10) != 0);
/* 139:160 */     this.resultCode = (src[(srcIndex + 2 + 1)] & 0xF);
/* 140:161 */     this.questionCount = readInt2(src, srcIndex + 4);
/* 141:162 */     this.answerCount = readInt2(src, srcIndex + 6);
/* 142:163 */     this.authorityCount = readInt2(src, srcIndex + 8);
/* 143:164 */     this.additionalCount = readInt2(src, srcIndex + 10);
/* 144:165 */     return 12;
/* 145:    */   }
/* 146:    */   
/* 147:    */   int writeQuestionSectionWireFormat(byte[] dst, int dstIndex)
/* 148:    */   {
/* 149:168 */     int start = dstIndex;
/* 150:169 */     dstIndex += this.questionName.writeWireFormat(dst, dstIndex);
/* 151:170 */     writeInt2(this.questionType, dst, dstIndex);
/* 152:171 */     dstIndex += 2;
/* 153:172 */     writeInt2(this.questionClass, dst, dstIndex);
/* 154:173 */     dstIndex += 2;
/* 155:174 */     return dstIndex - start;
/* 156:    */   }
/* 157:    */   
/* 158:    */   int readQuestionSectionWireFormat(byte[] src, int srcIndex)
/* 159:    */   {
/* 160:177 */     int start = srcIndex;
/* 161:178 */     srcIndex += this.questionName.readWireFormat(src, srcIndex);
/* 162:179 */     this.questionType = readInt2(src, srcIndex);
/* 163:180 */     srcIndex += 2;
/* 164:181 */     this.questionClass = readInt2(src, srcIndex);
/* 165:182 */     srcIndex += 2;
/* 166:183 */     return srcIndex - start;
/* 167:    */   }
/* 168:    */   
/* 169:    */   int writeResourceRecordWireFormat(byte[] dst, int dstIndex)
/* 170:    */   {
/* 171:186 */     int start = dstIndex;
/* 172:187 */     if (this.recordName == this.questionName)
/* 173:    */     {
/* 174:188 */       dst[(dstIndex++)] = -64;
/* 175:189 */       dst[(dstIndex++)] = 12;
/* 176:    */     }
/* 177:    */     else
/* 178:    */     {
/* 179:191 */       dstIndex += this.recordName.writeWireFormat(dst, dstIndex);
/* 180:    */     }
/* 181:193 */     writeInt2(this.recordType, dst, dstIndex);
/* 182:194 */     dstIndex += 2;
/* 183:195 */     writeInt2(this.recordClass, dst, dstIndex);
/* 184:196 */     dstIndex += 2;
/* 185:197 */     writeInt4(this.ttl, dst, dstIndex);
/* 186:198 */     dstIndex += 4;
/* 187:199 */     this.rDataLength = writeRDataWireFormat(dst, dstIndex + 2);
/* 188:200 */     writeInt2(this.rDataLength, dst, dstIndex);
/* 189:201 */     dstIndex += 2 + this.rDataLength;
/* 190:202 */     return dstIndex - start;
/* 191:    */   }
/* 192:    */   
/* 193:    */   int readResourceRecordWireFormat(byte[] src, int srcIndex)
/* 194:    */   {
/* 195:205 */     int start = srcIndex;
/* 196:208 */     if ((src[srcIndex] & 0xC0) == 192)
/* 197:    */     {
/* 198:209 */       this.recordName = this.questionName;
/* 199:210 */       srcIndex += 2;
/* 200:    */     }
/* 201:    */     else
/* 202:    */     {
/* 203:212 */       srcIndex += this.recordName.readWireFormat(src, srcIndex);
/* 204:    */     }
/* 205:214 */     this.recordType = readInt2(src, srcIndex);
/* 206:215 */     srcIndex += 2;
/* 207:216 */     this.recordClass = readInt2(src, srcIndex);
/* 208:217 */     srcIndex += 2;
/* 209:218 */     this.ttl = readInt4(src, srcIndex);
/* 210:219 */     srcIndex += 4;
/* 211:220 */     this.rDataLength = readInt2(src, srcIndex);
/* 212:221 */     srcIndex += 2;
/* 213:    */     
/* 214:223 */     this.addrEntry = new NbtAddress[this.rDataLength / 6];
/* 215:224 */     int end = srcIndex + this.rDataLength;
/* 216:225 */     for (this.addrIndex = 0; srcIndex < end; this.addrIndex += 1) {
/* 217:226 */       srcIndex += readRDataWireFormat(src, srcIndex);
/* 218:    */     }
/* 219:229 */     return srcIndex - start;
/* 220:    */   }
/* 221:    */   
/* 222:    */   abstract int writeBodyWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 223:    */   
/* 224:    */   abstract int readBodyWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 225:    */   
/* 226:    */   abstract int writeRDataWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 227:    */   
/* 228:    */   abstract int readRDataWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 229:    */   
/* 230:    */   public String toString()
/* 231:    */   {
/* 232:    */     String opCodeString;
/* 233:    */     String opCodeString;
/* 234:    */     String opCodeString;
/* 235:245 */     switch (this.opCode)
/* 236:    */     {
/* 237:    */     case 0: 
/* 238:247 */       opCodeString = "QUERY";
/* 239:248 */       break;
/* 240:    */     case 7: 
/* 241:250 */       opCodeString = "WACK";
/* 242:251 */       break;
/* 243:    */     default: 
/* 244:253 */       opCodeString = Integer.toString(this.opCode);
/* 245:    */     }
/* 246:    */     String resultCodeString;
/* 247:    */     String resultCodeString;
/* 248:    */     String resultCodeString;
/* 249:    */     String resultCodeString;
/* 250:    */     String resultCodeString;
/* 251:    */     String resultCodeString;
/* 252:    */     String resultCodeString;
/* 253:256 */     switch (this.resultCode)
/* 254:    */     {
/* 255:    */     case 1: 
/* 256:258 */       resultCodeString = "FMT_ERR";
/* 257:259 */       break;
/* 258:    */     case 2: 
/* 259:261 */       resultCodeString = "SRV_ERR";
/* 260:262 */       break;
/* 261:    */     case 4: 
/* 262:264 */       resultCodeString = "IMP_ERR";
/* 263:265 */       break;
/* 264:    */     case 5: 
/* 265:267 */       resultCodeString = "RFS_ERR";
/* 266:268 */       break;
/* 267:    */     case 6: 
/* 268:270 */       resultCodeString = "ACT_ERR";
/* 269:271 */       break;
/* 270:    */     case 7: 
/* 271:273 */       resultCodeString = "CFT_ERR";
/* 272:274 */       break;
/* 273:    */     case 3: 
/* 274:    */     default: 
/* 275:276 */       resultCodeString = "0x" + Hexdump.toHexString(this.resultCode, 1);
/* 276:    */     }
/* 277:    */     String questionTypeString;
/* 278:    */     String questionTypeString;
/* 279:    */     String questionTypeString;
/* 280:279 */     switch (this.questionType)
/* 281:    */     {
/* 282:    */     case 32: 
/* 283:281 */       questionTypeString = "NB";
/* 284:282 */       break;
/* 285:    */     case 33: 
/* 286:284 */       questionTypeString = "NBSTAT";
/* 287:285 */       break;
/* 288:    */     default: 
/* 289:287 */       questionTypeString = "0x" + Hexdump.toHexString(this.questionType, 4);
/* 290:    */     }
/* 291:    */     String recordTypeString;
/* 292:    */     String recordTypeString;
/* 293:    */     String recordTypeString;
/* 294:    */     String recordTypeString;
/* 295:    */     String recordTypeString;
/* 296:    */     String recordTypeString;
/* 297:290 */     switch (this.recordType)
/* 298:    */     {
/* 299:    */     case 1: 
/* 300:292 */       recordTypeString = "A";
/* 301:293 */       break;
/* 302:    */     case 2: 
/* 303:295 */       recordTypeString = "NS";
/* 304:296 */       break;
/* 305:    */     case 10: 
/* 306:298 */       recordTypeString = "NULL";
/* 307:299 */       break;
/* 308:    */     case 32: 
/* 309:301 */       recordTypeString = "NB";
/* 310:302 */       break;
/* 311:    */     case 33: 
/* 312:304 */       recordTypeString = "NBSTAT";
/* 313:305 */       break;
/* 314:    */     default: 
/* 315:307 */       recordTypeString = "0x" + Hexdump.toHexString(this.recordType, 4);
/* 316:    */     }
/* 317:311 */     return new String("nameTrnId=" + this.nameTrnId + ",isResponse=" + this.isResponse + ",opCode=" + opCodeString + ",isAuthAnswer=" + this.isAuthAnswer + ",isTruncated=" + this.isTruncated + ",isRecurAvailable=" + this.isRecurAvailable + ",isRecurDesired=" + this.isRecurDesired + ",isBroadcast=" + this.isBroadcast + ",resultCode=" + this.resultCode + ",questionCount=" + this.questionCount + ",answerCount=" + this.answerCount + ",authorityCount=" + this.authorityCount + ",additionalCount=" + this.additionalCount + ",questionName=" + this.questionName + ",questionType=" + questionTypeString + ",questionClass=" + (this.questionClass == 1 ? "IN" : new StringBuffer().append("0x").append(Hexdump.toHexString(this.questionClass, 4)).toString()) + ",recordName=" + this.recordName + ",recordType=" + recordTypeString + ",recordClass=" + (this.recordClass == 1 ? "IN" : new StringBuffer().append("0x").append(Hexdump.toHexString(this.recordClass, 4)).toString()) + ",ttl=" + this.ttl + ",rDataLength=" + this.rDataLength);
/* 318:    */   }
/* 319:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NameServicePacket
 * JD-Core Version:    0.7.0.1
 */