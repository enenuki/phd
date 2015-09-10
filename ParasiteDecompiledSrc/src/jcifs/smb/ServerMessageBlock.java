/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.TimeZone;
/*   6:    */ import jcifs.util.Hexdump;
/*   7:    */ import jcifs.util.LogStream;
/*   8:    */ import jcifs.util.transport.Request;
/*   9:    */ import jcifs.util.transport.Response;
/*  10:    */ 
/*  11:    */ abstract class ServerMessageBlock
/*  12:    */   extends Response
/*  13:    */   implements Request, SmbConstants
/*  14:    */ {
/*  15: 34 */   static LogStream log = ;
/*  16: 36 */   static final byte[] header = { -1, 83, 77, 66, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*  17:    */   static final byte SMB_COM_CREATE_DIRECTORY = 0;
/*  18:    */   static final byte SMB_COM_DELETE_DIRECTORY = 1;
/*  19:    */   static final byte SMB_COM_CLOSE = 4;
/*  20:    */   static final byte SMB_COM_DELETE = 6;
/*  21:    */   static final byte SMB_COM_RENAME = 7;
/*  22:    */   static final byte SMB_COM_QUERY_INFORMATION = 8;
/*  23:    */   static final byte SMB_COM_WRITE = 11;
/*  24:    */   static final byte SMB_COM_CHECK_DIRECTORY = 16;
/*  25:    */   static final byte SMB_COM_TRANSACTION = 37;
/*  26:    */   static final byte SMB_COM_TRANSACTION_SECONDARY = 38;
/*  27:    */   static final byte SMB_COM_MOVE = 42;
/*  28:    */   static final byte SMB_COM_ECHO = 43;
/*  29:    */   static final byte SMB_COM_OPEN_ANDX = 45;
/*  30:    */   static final byte SMB_COM_READ_ANDX = 46;
/*  31:    */   static final byte SMB_COM_WRITE_ANDX = 47;
/*  32:    */   static final byte SMB_COM_TRANSACTION2 = 50;
/*  33:    */   static final byte SMB_COM_FIND_CLOSE2 = 52;
/*  34:    */   static final byte SMB_COM_TREE_DISCONNECT = 113;
/*  35:    */   static final byte SMB_COM_NEGOTIATE = 114;
/*  36:    */   static final byte SMB_COM_SESSION_SETUP_ANDX = 115;
/*  37:    */   static final byte SMB_COM_LOGOFF_ANDX = 116;
/*  38:    */   static final byte SMB_COM_TREE_CONNECT_ANDX = 117;
/*  39:    */   static final byte SMB_COM_NT_TRANSACT = -96;
/*  40:    */   static final byte SMB_COM_NT_TRANSACT_SECONDARY = -95;
/*  41:    */   static final byte SMB_COM_NT_CREATE_ANDX = -94;
/*  42:    */   byte command;
/*  43:    */   byte flags;
/*  44:    */   int headerStart;
/*  45:    */   int length;
/*  46:    */   int batchLevel;
/*  47:    */   int errorCode;
/*  48:    */   int flags2;
/*  49:    */   int tid;
/*  50:    */   int pid;
/*  51:    */   int uid;
/*  52:    */   int mid;
/*  53:    */   int wordCount;
/*  54:    */   int byteCount;
/*  55:    */   boolean useUnicode;
/*  56:    */   boolean received;
/*  57:    */   boolean extendedSecurity;
/*  58:    */   
/*  59:    */   static void writeInt2(long val, byte[] dst, int dstIndex)
/*  60:    */   {
/*  61: 46 */     dst[dstIndex] = ((byte)(int)val);
/*  62: 47 */     dst[(++dstIndex)] = ((byte)(int)(val >> 8));
/*  63:    */   }
/*  64:    */   
/*  65:    */   static void writeInt4(long val, byte[] dst, int dstIndex)
/*  66:    */   {
/*  67: 50 */     dst[dstIndex] = ((byte)(int)val); long 
/*  68: 51 */       tmp15_14 = (val >> 8);val = tmp15_14;dst[(++dstIndex)] = ((byte)(int)tmp15_14); long 
/*  69: 52 */       tmp29_28 = (val >> 8);val = tmp29_28;dst[(++tmp15_14)] = ((byte)(int)tmp29_28);
/*  70: 53 */     dst[(++tmp15_14)] = ((byte)(int)(val >> 8));
/*  71:    */   }
/*  72:    */   
/*  73:    */   static int readInt2(byte[] src, int srcIndex)
/*  74:    */   {
/*  75: 56 */     return (src[srcIndex] & 0xFF) + ((src[(srcIndex + 1)] & 0xFF) << 8);
/*  76:    */   }
/*  77:    */   
/*  78:    */   static int readInt4(byte[] src, int srcIndex)
/*  79:    */   {
/*  80: 60 */     return (src[srcIndex] & 0xFF) + ((src[(srcIndex + 1)] & 0xFF) << 8) + ((src[(srcIndex + 2)] & 0xFF) << 16) + ((src[(srcIndex + 3)] & 0xFF) << 24);
/*  81:    */   }
/*  82:    */   
/*  83:    */   static long readInt8(byte[] src, int srcIndex)
/*  84:    */   {
/*  85: 66 */     return (readInt4(src, srcIndex) & 0xFFFFFFFF) + (readInt4(src, srcIndex + 4) << 32);
/*  86:    */   }
/*  87:    */   
/*  88:    */   static void writeInt8(long val, byte[] dst, int dstIndex)
/*  89:    */   {
/*  90: 70 */     dst[dstIndex] = ((byte)(int)val); long 
/*  91: 71 */       tmp15_14 = (val >> 8);val = tmp15_14;dst[(++dstIndex)] = ((byte)(int)tmp15_14); long 
/*  92: 72 */       tmp29_28 = (val >> 8);val = tmp29_28;dst[(++tmp15_14)] = ((byte)(int)tmp29_28); long 
/*  93: 73 */       tmp43_42 = (val >> 8);val = tmp43_42;dst[(++tmp15_14)] = ((byte)(int)tmp43_42); long 
/*  94: 74 */       tmp57_56 = (val >> 8);val = tmp57_56;dst[(++tmp15_14)] = ((byte)(int)tmp57_56); long 
/*  95: 75 */       tmp71_70 = (val >> 8);val = tmp71_70;dst[(++tmp15_14)] = ((byte)(int)tmp71_70); long 
/*  96: 76 */       tmp85_84 = (val >> 8);val = tmp85_84;dst[(++tmp15_14)] = ((byte)(int)tmp85_84);
/*  97: 77 */     dst[(++tmp15_14)] = ((byte)(int)(val >> 8));
/*  98:    */   }
/*  99:    */   
/* 100:    */   static long readTime(byte[] src, int srcIndex)
/* 101:    */   {
/* 102: 80 */     int low = readInt4(src, srcIndex);
/* 103: 81 */     int hi = readInt4(src, srcIndex + 4);
/* 104: 82 */     long t = hi << 32 | low & 0xFFFFFFFF;
/* 105: 83 */     t = t / 10000L - 11644473600000L;
/* 106: 84 */     return t;
/* 107:    */   }
/* 108:    */   
/* 109:    */   static void writeTime(long t, byte[] dst, int dstIndex)
/* 110:    */   {
/* 111: 87 */     if (t != 0L) {
/* 112: 88 */       t = (t + 11644473600000L) * 10000L;
/* 113:    */     }
/* 114: 90 */     writeInt8(t, dst, dstIndex);
/* 115:    */   }
/* 116:    */   
/* 117:    */   static long readUTime(byte[] buffer, int bufferIndex)
/* 118:    */   {
/* 119: 93 */     return readInt4(buffer, bufferIndex) * 1000L;
/* 120:    */   }
/* 121:    */   
/* 122:    */   static void writeUTime(long t, byte[] dst, int dstIndex)
/* 123:    */   {
/* 124: 96 */     if ((t == 0L) || (t == -1L))
/* 125:    */     {
/* 126: 97 */       writeInt4(-1L, dst, dstIndex);
/* 127: 98 */       return;
/* 128:    */     }
/* 129:100 */     synchronized (SmbConstants.TZ)
/* 130:    */     {
/* 131:101 */       if (SmbConstants.TZ.inDaylightTime(new Date()))
/* 132:    */       {
/* 133:103 */         if (!SmbConstants.TZ.inDaylightTime(new Date(t))) {
/* 134:107 */           t -= 3600000L;
/* 135:    */         }
/* 136:    */       }
/* 137:111 */       else if (SmbConstants.TZ.inDaylightTime(new Date(t))) {
/* 138:113 */         t += 3600000L;
/* 139:    */       }
/* 140:    */     }
/* 141:119 */     writeInt4((int)(t / 1000L), dst, dstIndex);
/* 142:    */   }
/* 143:    */   
/* 144:180 */   long responseTimeout = 1L;
/* 145:    */   int signSeq;
/* 146:    */   boolean verifyFailed;
/* 147:183 */   NtlmPasswordAuthentication auth = null;
/* 148:    */   String path;
/* 149:185 */   SigningDigest digest = null;
/* 150:    */   ServerMessageBlock response;
/* 151:    */   
/* 152:    */   ServerMessageBlock()
/* 153:    */   {
/* 154:189 */     this.flags = 24;
/* 155:190 */     this.pid = SmbConstants.PID;
/* 156:191 */     this.batchLevel = 0;
/* 157:    */   }
/* 158:    */   
/* 159:    */   void reset()
/* 160:    */   {
/* 161:195 */     this.flags = 24;
/* 162:196 */     this.flags2 = 0;
/* 163:197 */     this.errorCode = 0;
/* 164:198 */     this.received = false;
/* 165:199 */     this.digest = null;
/* 166:    */   }
/* 167:    */   
/* 168:    */   int writeString(String str, byte[] dst, int dstIndex)
/* 169:    */   {
/* 170:202 */     return writeString(str, dst, dstIndex, this.useUnicode);
/* 171:    */   }
/* 172:    */   
/* 173:    */   int writeString(String str, byte[] dst, int dstIndex, boolean useUnicode)
/* 174:    */   {
/* 175:205 */     int start = dstIndex;
/* 176:    */     try
/* 177:    */     {
/* 178:208 */       if (useUnicode)
/* 179:    */       {
/* 180:210 */         if ((dstIndex - this.headerStart) % 2 != 0) {
/* 181:211 */           dst[(dstIndex++)] = 0;
/* 182:    */         }
/* 183:213 */         System.arraycopy(str.getBytes("UTF-16LE"), 0, dst, dstIndex, str.length() * 2);
/* 184:    */         
/* 185:215 */         dstIndex += str.length() * 2;
/* 186:216 */         dst[(dstIndex++)] = 0;
/* 187:217 */         dst[(dstIndex++)] = 0;
/* 188:    */       }
/* 189:    */       else
/* 190:    */       {
/* 191:219 */         byte[] b = str.getBytes(SmbConstants.OEM_ENCODING);
/* 192:220 */         System.arraycopy(b, 0, dst, dstIndex, b.length);
/* 193:221 */         dstIndex += b.length;
/* 194:222 */         dst[(dstIndex++)] = 0;
/* 195:    */       }
/* 196:    */     }
/* 197:    */     catch (UnsupportedEncodingException uee)
/* 198:    */     {
/* 199:225 */       if (LogStream.level > 1) {
/* 200:226 */         uee.printStackTrace(log);
/* 201:    */       }
/* 202:    */     }
/* 203:229 */     return dstIndex - start;
/* 204:    */   }
/* 205:    */   
/* 206:    */   String readString(byte[] src, int srcIndex)
/* 207:    */   {
/* 208:232 */     return readString(src, srcIndex, 256, this.useUnicode);
/* 209:    */   }
/* 210:    */   
/* 211:    */   String readString(byte[] src, int srcIndex, int maxLen, boolean useUnicode)
/* 212:    */   {
/* 213:235 */     int len = 0;
/* 214:236 */     String str = null;
/* 215:    */     try
/* 216:    */     {
/* 217:238 */       if (useUnicode)
/* 218:    */       {
/* 219:240 */         if ((srcIndex - this.headerStart) % 2 != 0) {
/* 220:241 */           srcIndex++;
/* 221:    */         }
/* 222:244 */         while ((src[(srcIndex + len)] != 0) || (src[(srcIndex + len + 1)] != 0))
/* 223:    */         {
/* 224:245 */           len += 2;
/* 225:246 */           if (len > maxLen)
/* 226:    */           {
/* 227:247 */             if (LogStream.level > 0) {
/* 228:248 */               Hexdump.hexdump(System.err, src, srcIndex, maxLen < 128 ? maxLen + 8 : 128);
/* 229:    */             }
/* 230:249 */             throw new RuntimeException("zero termination not found");
/* 231:    */           }
/* 232:    */         }
/* 233:252 */         str = new String(src, srcIndex, len, "UTF-16LE");
/* 234:    */       }
/* 235:    */       else
/* 236:    */       {
/* 237:254 */         while (src[(srcIndex + len)] != 0)
/* 238:    */         {
/* 239:255 */           len++;
/* 240:256 */           if (len > maxLen)
/* 241:    */           {
/* 242:257 */             if (LogStream.level > 0) {
/* 243:258 */               Hexdump.hexdump(System.err, src, srcIndex, maxLen < 128 ? maxLen + 8 : 128);
/* 244:    */             }
/* 245:259 */             throw new RuntimeException("zero termination not found");
/* 246:    */           }
/* 247:    */         }
/* 248:262 */         str = new String(src, srcIndex, len, SmbConstants.OEM_ENCODING);
/* 249:    */       }
/* 250:    */     }
/* 251:    */     catch (UnsupportedEncodingException uee)
/* 252:    */     {
/* 253:265 */       if (LogStream.level > 1) {
/* 254:266 */         uee.printStackTrace(log);
/* 255:    */       }
/* 256:    */     }
/* 257:268 */     return str;
/* 258:    */   }
/* 259:    */   
/* 260:    */   String readString(byte[] src, int srcIndex, int srcEnd, int maxLen, boolean useUnicode)
/* 261:    */   {
/* 262:271 */     int len = 0;
/* 263:272 */     String str = null;
/* 264:    */     try
/* 265:    */     {
/* 266:274 */       if (useUnicode)
/* 267:    */       {
/* 268:276 */         if ((srcIndex - this.headerStart) % 2 != 0) {
/* 269:277 */           srcIndex++;
/* 270:    */         }
/* 271:279 */         for (len = 0; srcIndex + len + 1 < srcEnd; len += 2)
/* 272:    */         {
/* 273:280 */           if ((src[(srcIndex + len)] == 0) && (src[(srcIndex + len + 1)] == 0)) {
/* 274:    */             break;
/* 275:    */           }
/* 276:283 */           if (len > maxLen)
/* 277:    */           {
/* 278:284 */             if (LogStream.level > 0) {
/* 279:285 */               Hexdump.hexdump(System.err, src, srcIndex, maxLen < 128 ? maxLen + 8 : 128);
/* 280:    */             }
/* 281:286 */             throw new RuntimeException("zero termination not found");
/* 282:    */           }
/* 283:    */         }
/* 284:289 */         str = new String(src, srcIndex, len, "UTF-16LE");
/* 285:    */       }
/* 286:    */       else
/* 287:    */       {
/* 288:291 */         for (len = 0; srcIndex < srcEnd; len++)
/* 289:    */         {
/* 290:292 */           if (src[(srcIndex + len)] == 0) {
/* 291:    */             break;
/* 292:    */           }
/* 293:295 */           if (len > maxLen)
/* 294:    */           {
/* 295:296 */             if (LogStream.level > 0) {
/* 296:297 */               Hexdump.hexdump(System.err, src, srcIndex, maxLen < 128 ? maxLen + 8 : 128);
/* 297:    */             }
/* 298:298 */             throw new RuntimeException("zero termination not found");
/* 299:    */           }
/* 300:    */         }
/* 301:301 */         str = new String(src, srcIndex, len, SmbConstants.OEM_ENCODING);
/* 302:    */       }
/* 303:    */     }
/* 304:    */     catch (UnsupportedEncodingException uee)
/* 305:    */     {
/* 306:304 */       if (LogStream.level > 1) {
/* 307:305 */         uee.printStackTrace(log);
/* 308:    */       }
/* 309:    */     }
/* 310:307 */     return str;
/* 311:    */   }
/* 312:    */   
/* 313:    */   int stringWireLength(String str, int offset)
/* 314:    */   {
/* 315:310 */     int len = str.length() + 1;
/* 316:311 */     if (this.useUnicode)
/* 317:    */     {
/* 318:312 */       len = str.length() * 2 + 2;
/* 319:313 */       len = offset % 2 != 0 ? len + 1 : len;
/* 320:    */     }
/* 321:315 */     return len;
/* 322:    */   }
/* 323:    */   
/* 324:    */   int readStringLength(byte[] src, int srcIndex, int max)
/* 325:    */   {
/* 326:318 */     int len = 0;
/* 327:319 */     while (src[(srcIndex + len)] != 0) {
/* 328:320 */       if (len++ > max) {
/* 329:321 */         throw new RuntimeException("zero termination not found: " + this);
/* 330:    */       }
/* 331:    */     }
/* 332:324 */     return len;
/* 333:    */   }
/* 334:    */   
/* 335:    */   int encode(byte[] dst, int dstIndex)
/* 336:    */   {
/* 337:327 */     int start = this.headerStart = dstIndex;
/* 338:    */     
/* 339:329 */     dstIndex += writeHeaderWireFormat(dst, dstIndex);
/* 340:330 */     this.wordCount = writeParameterWordsWireFormat(dst, dstIndex + 1);
/* 341:331 */     dst[(dstIndex++)] = ((byte)(this.wordCount / 2 & 0xFF));
/* 342:332 */     dstIndex += this.wordCount;
/* 343:333 */     this.wordCount /= 2;
/* 344:334 */     this.byteCount = writeBytesWireFormat(dst, dstIndex + 2);
/* 345:335 */     dst[(dstIndex++)] = ((byte)(this.byteCount & 0xFF));
/* 346:336 */     dst[(dstIndex++)] = ((byte)(this.byteCount >> 8 & 0xFF));
/* 347:337 */     dstIndex += this.byteCount;
/* 348:    */     
/* 349:339 */     this.length = (dstIndex - start);
/* 350:341 */     if (this.digest != null) {
/* 351:342 */       this.digest.sign(dst, this.headerStart, this.length, this, this.response);
/* 352:    */     }
/* 353:345 */     return this.length;
/* 354:    */   }
/* 355:    */   
/* 356:    */   int decode(byte[] buffer, int bufferIndex)
/* 357:    */   {
/* 358:348 */     int start = this.headerStart = bufferIndex;
/* 359:    */     
/* 360:350 */     bufferIndex += readHeaderWireFormat(buffer, bufferIndex);
/* 361:    */     
/* 362:352 */     this.wordCount = buffer[(bufferIndex++)];
/* 363:353 */     if (this.wordCount != 0)
/* 364:    */     {
/* 365:    */       int n;
/* 366:355 */       if (((n = readParameterWordsWireFormat(buffer, bufferIndex)) != this.wordCount * 2) && 
/* 367:356 */         (LogStream.level >= 5)) {
/* 368:357 */         log.println("wordCount * 2=" + this.wordCount * 2 + " but readParameterWordsWireFormat returned " + n);
/* 369:    */       }
/* 370:361 */       bufferIndex += this.wordCount * 2;
/* 371:    */     }
/* 372:364 */     this.byteCount = readInt2(buffer, bufferIndex);
/* 373:365 */     bufferIndex += 2;
/* 374:367 */     if (this.byteCount != 0)
/* 375:    */     {
/* 376:    */       int n;
/* 377:369 */       if (((n = readBytesWireFormat(buffer, bufferIndex)) != this.byteCount) && 
/* 378:370 */         (LogStream.level >= 5)) {
/* 379:371 */         log.println("byteCount=" + this.byteCount + " but readBytesWireFormat returned " + n);
/* 380:    */       }
/* 381:378 */       bufferIndex += this.byteCount;
/* 382:    */     }
/* 383:381 */     this.length = (bufferIndex - start);
/* 384:382 */     return this.length;
/* 385:    */   }
/* 386:    */   
/* 387:    */   int writeHeaderWireFormat(byte[] dst, int dstIndex)
/* 388:    */   {
/* 389:385 */     System.arraycopy(header, 0, dst, dstIndex, header.length);
/* 390:386 */     dst[(dstIndex + 4)] = this.command;
/* 391:387 */     dst[(dstIndex + 9)] = this.flags;
/* 392:388 */     writeInt2(this.flags2, dst, dstIndex + 9 + 1);
/* 393:389 */     dstIndex += 24;
/* 394:390 */     writeInt2(this.tid, dst, dstIndex);
/* 395:391 */     writeInt2(this.pid, dst, dstIndex + 2);
/* 396:392 */     writeInt2(this.uid, dst, dstIndex + 4);
/* 397:393 */     writeInt2(this.mid, dst, dstIndex + 6);
/* 398:394 */     return 32;
/* 399:    */   }
/* 400:    */   
/* 401:    */   int readHeaderWireFormat(byte[] buffer, int bufferIndex)
/* 402:    */   {
/* 403:397 */     this.command = buffer[(bufferIndex + 4)];
/* 404:398 */     this.errorCode = readInt4(buffer, bufferIndex + 5);
/* 405:399 */     this.flags = buffer[(bufferIndex + 9)];
/* 406:400 */     this.flags2 = readInt2(buffer, bufferIndex + 9 + 1);
/* 407:401 */     this.tid = readInt2(buffer, bufferIndex + 24);
/* 408:402 */     this.pid = readInt2(buffer, bufferIndex + 24 + 2);
/* 409:403 */     this.uid = readInt2(buffer, bufferIndex + 24 + 4);
/* 410:404 */     this.mid = readInt2(buffer, bufferIndex + 24 + 6);
/* 411:405 */     return 32;
/* 412:    */   }
/* 413:    */   
/* 414:    */   boolean isResponse()
/* 415:    */   {
/* 416:408 */     return (this.flags & 0x80) == 128;
/* 417:    */   }
/* 418:    */   
/* 419:    */   abstract int writeParameterWordsWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 420:    */   
/* 421:    */   abstract int writeBytesWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 422:    */   
/* 423:    */   abstract int readParameterWordsWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 424:    */   
/* 425:    */   abstract int readBytesWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 426:    */   
/* 427:    */   public int hashCode()
/* 428:    */   {
/* 429:438 */     return this.mid;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public boolean equals(Object obj)
/* 433:    */   {
/* 434:441 */     return ((obj instanceof ServerMessageBlock)) && (((ServerMessageBlock)obj).mid == this.mid);
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String toString()
/* 438:    */   {
/* 439:    */     String c;
/* 440:    */     String c;
/* 441:    */     String c;
/* 442:    */     String c;
/* 443:    */     String c;
/* 444:    */     String c;
/* 445:    */     String c;
/* 446:    */     String c;
/* 447:    */     String c;
/* 448:    */     String c;
/* 449:    */     String c;
/* 450:    */     String c;
/* 451:    */     String c;
/* 452:    */     String c;
/* 453:    */     String c;
/* 454:    */     String c;
/* 455:    */     String c;
/* 456:    */     String c;
/* 457:    */     String c;
/* 458:    */     String c;
/* 459:    */     String c;
/* 460:    */     String c;
/* 461:    */     String c;
/* 462:    */     String c;
/* 463:    */     String c;
/* 464:445 */     switch (this.command)
/* 465:    */     {
/* 466:    */     case 114: 
/* 467:447 */       c = "SMB_COM_NEGOTIATE";
/* 468:448 */       break;
/* 469:    */     case 115: 
/* 470:450 */       c = "SMB_COM_SESSION_SETUP_ANDX";
/* 471:451 */       break;
/* 472:    */     case 117: 
/* 473:453 */       c = "SMB_COM_TREE_CONNECT_ANDX";
/* 474:454 */       break;
/* 475:    */     case 8: 
/* 476:456 */       c = "SMB_COM_QUERY_INFORMATION";
/* 477:457 */       break;
/* 478:    */     case 16: 
/* 479:459 */       c = "SMB_COM_CHECK_DIRECTORY";
/* 480:460 */       break;
/* 481:    */     case 37: 
/* 482:462 */       c = "SMB_COM_TRANSACTION";
/* 483:463 */       break;
/* 484:    */     case 50: 
/* 485:465 */       c = "SMB_COM_TRANSACTION2";
/* 486:466 */       break;
/* 487:    */     case 38: 
/* 488:468 */       c = "SMB_COM_TRANSACTION_SECONDARY";
/* 489:469 */       break;
/* 490:    */     case 52: 
/* 491:471 */       c = "SMB_COM_FIND_CLOSE2";
/* 492:472 */       break;
/* 493:    */     case 113: 
/* 494:474 */       c = "SMB_COM_TREE_DISCONNECT";
/* 495:475 */       break;
/* 496:    */     case 116: 
/* 497:477 */       c = "SMB_COM_LOGOFF_ANDX";
/* 498:478 */       break;
/* 499:    */     case 43: 
/* 500:480 */       c = "SMB_COM_ECHO";
/* 501:481 */       break;
/* 502:    */     case 42: 
/* 503:483 */       c = "SMB_COM_MOVE";
/* 504:484 */       break;
/* 505:    */     case 7: 
/* 506:486 */       c = "SMB_COM_RENAME";
/* 507:487 */       break;
/* 508:    */     case 6: 
/* 509:489 */       c = "SMB_COM_DELETE";
/* 510:490 */       break;
/* 511:    */     case 1: 
/* 512:492 */       c = "SMB_COM_DELETE_DIRECTORY";
/* 513:493 */       break;
/* 514:    */     case -94: 
/* 515:495 */       c = "SMB_COM_NT_CREATE_ANDX";
/* 516:496 */       break;
/* 517:    */     case 45: 
/* 518:498 */       c = "SMB_COM_OPEN_ANDX";
/* 519:499 */       break;
/* 520:    */     case 46: 
/* 521:501 */       c = "SMB_COM_READ_ANDX";
/* 522:502 */       break;
/* 523:    */     case 4: 
/* 524:504 */       c = "SMB_COM_CLOSE";
/* 525:505 */       break;
/* 526:    */     case 47: 
/* 527:507 */       c = "SMB_COM_WRITE_ANDX";
/* 528:508 */       break;
/* 529:    */     case 0: 
/* 530:510 */       c = "SMB_COM_CREATE_DIRECTORY";
/* 531:511 */       break;
/* 532:    */     case -96: 
/* 533:513 */       c = "SMB_COM_NT_TRANSACT";
/* 534:514 */       break;
/* 535:    */     case -95: 
/* 536:516 */       c = "SMB_COM_NT_TRANSACT_SECONDARY";
/* 537:517 */       break;
/* 538:    */     default: 
/* 539:519 */       c = "UNKNOWN";
/* 540:    */     }
/* 541:521 */     String str = this.errorCode == 0 ? "0" : SmbException.getMessageByCode(this.errorCode);
/* 542:522 */     return new String("command=" + c + ",received=" + this.received + ",errorCode=" + str + ",flags=0x" + Hexdump.toHexString(this.flags & 0xFF, 4) + ",flags2=0x" + Hexdump.toHexString(this.flags2, 4) + ",signSeq=" + this.signSeq + ",tid=" + this.tid + ",pid=" + this.pid + ",uid=" + this.uid + ",mid=" + this.mid + ",wordCount=" + this.wordCount + ",byteCount=" + this.byteCount);
/* 543:    */   }
/* 544:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.ServerMessageBlock
 * JD-Core Version:    0.7.0.1
 */