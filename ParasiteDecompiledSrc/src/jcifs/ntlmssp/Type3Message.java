/*   1:    */ package jcifs.ntlmssp;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.UnknownHostException;
/*   5:    */ import java.security.SecureRandom;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import jcifs.Config;
/*   8:    */ import jcifs.netbios.NbtAddress;
/*   9:    */ import jcifs.smb.NtlmPasswordAuthentication;
/*  10:    */ import jcifs.util.HMACT64;
/*  11:    */ import jcifs.util.Hexdump;
/*  12:    */ import jcifs.util.MD4;
/*  13:    */ import jcifs.util.RC4;
/*  14:    */ 
/*  15:    */ public class Type3Message
/*  16:    */   extends NtlmMessage
/*  17:    */ {
/*  18: 59 */   private static final SecureRandom RANDOM = new SecureRandom();
/*  19: 71 */   private byte[] masterKey = null;
/*  20: 72 */   private byte[] sessionKey = null;
/*  21:    */   
/*  22:    */   static
/*  23:    */   {
/*  24: 75 */     DEFAULT_FLAGS = 0x200 | (Config.getBoolean("jcifs.smb.client.useUnicode", true) ? 1 : 2);
/*  25:    */     
/*  26:    */ 
/*  27: 78 */     DEFAULT_DOMAIN = Config.getProperty("jcifs.smb.client.domain", null);
/*  28: 79 */     DEFAULT_USER = Config.getProperty("jcifs.smb.client.username", null);
/*  29: 80 */     DEFAULT_PASSWORD = Config.getProperty("jcifs.smb.client.password", null);
/*  30:    */     
/*  31: 82 */     String defaultWorkstation = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 84 */       defaultWorkstation = NbtAddress.getLocalHost().getHostName();
/*  35:    */     }
/*  36:    */     catch (UnknownHostException ex) {}
/*  37: 86 */     DEFAULT_WORKSTATION = defaultWorkstation;
/*  38:    */   }
/*  39:    */   
/*  40: 87 */   private static final int LM_COMPATIBILITY = Config.getInt("jcifs.smb.lmCompatibility", 3);
/*  41:    */   static final long MILLISECONDS_BETWEEN_1970_AND_1601 = 11644473600000L;
/*  42:    */   private static final int DEFAULT_FLAGS;
/*  43:    */   private static final String DEFAULT_DOMAIN;
/*  44:    */   private static final String DEFAULT_USER;
/*  45:    */   private static final String DEFAULT_PASSWORD;
/*  46:    */   private static final String DEFAULT_WORKSTATION;
/*  47:    */   private byte[] lmResponse;
/*  48:    */   private byte[] ntResponse;
/*  49:    */   private String domain;
/*  50:    */   private String user;
/*  51:    */   private String workstation;
/*  52:    */   
/*  53:    */   public Type3Message()
/*  54:    */   {
/*  55: 95 */     setFlags(getDefaultFlags());
/*  56: 96 */     setDomain(getDefaultDomain());
/*  57: 97 */     setUser(getDefaultUser());
/*  58: 98 */     setWorkstation(getDefaultWorkstation());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Type3Message(Type2Message type2)
/*  62:    */   {
/*  63:108 */     setFlags(getDefaultFlags(type2));
/*  64:109 */     setWorkstation(getDefaultWorkstation());
/*  65:110 */     String domain = getDefaultDomain();
/*  66:111 */     setDomain(domain);
/*  67:112 */     String user = getDefaultUser();
/*  68:113 */     setUser(user);
/*  69:114 */     String password = getDefaultPassword();
/*  70:115 */     switch (LM_COMPATIBILITY)
/*  71:    */     {
/*  72:    */     case 0: 
/*  73:    */     case 1: 
/*  74:118 */       setLMResponse(getLMResponse(type2, password));
/*  75:119 */       setNTResponse(getNTResponse(type2, password));
/*  76:120 */       break;
/*  77:    */     case 2: 
/*  78:122 */       byte[] nt = getNTResponse(type2, password);
/*  79:123 */       setLMResponse(nt);
/*  80:124 */       setNTResponse(nt);
/*  81:125 */       break;
/*  82:    */     case 3: 
/*  83:    */     case 4: 
/*  84:    */     case 5: 
/*  85:129 */       byte[] clientChallenge = new byte[8];
/*  86:130 */       RANDOM.nextBytes(clientChallenge);
/*  87:131 */       setLMResponse(getLMv2Response(type2, domain, user, password, clientChallenge));
/*  88:    */       
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:137 */       break;
/*  94:    */     default: 
/*  95:139 */       setLMResponse(getLMResponse(type2, password));
/*  96:140 */       setNTResponse(getNTResponse(type2, password));
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Type3Message(Type2Message type2, String password, String domain, String user, String workstation, int flags)
/* 101:    */   {
/* 102:156 */     setFlags(flags | getDefaultFlags(type2));
/* 103:157 */     if (workstation == null) {
/* 104:158 */       workstation = getDefaultWorkstation();
/* 105:    */     }
/* 106:159 */     setWorkstation(workstation);
/* 107:160 */     setDomain(domain);
/* 108:161 */     setUser(user);
/* 109:163 */     switch (LM_COMPATIBILITY)
/* 110:    */     {
/* 111:    */     case 0: 
/* 112:    */     case 1: 
/* 113:166 */       if ((getFlags() & 0x80000) == 0)
/* 114:    */       {
/* 115:167 */         setLMResponse(getLMResponse(type2, password));
/* 116:168 */         setNTResponse(getNTResponse(type2, password));
/* 117:    */       }
/* 118:    */       else
/* 119:    */       {
/* 120:172 */         byte[] clientChallenge = new byte[24];
/* 121:173 */         RANDOM.nextBytes(clientChallenge);
/* 122:174 */         Arrays.fill(clientChallenge, 8, 24, (byte)0);
/* 123:    */         
/* 124:    */ 
/* 125:    */ 
/* 126:178 */         byte[] responseKeyNT = NtlmPasswordAuthentication.nTOWFv1(password);
/* 127:179 */         byte[] ntlm2Response = NtlmPasswordAuthentication.getNTLM2Response(responseKeyNT, type2.getChallenge(), clientChallenge);
/* 128:    */         
/* 129:    */ 
/* 130:    */ 
/* 131:183 */         setLMResponse(clientChallenge);
/* 132:184 */         setNTResponse(ntlm2Response);
/* 133:186 */         if ((getFlags() & 0x10) == 16)
/* 134:    */         {
/* 135:187 */           byte[] sessionNonce = new byte[16];
/* 136:188 */           System.arraycopy(type2.getChallenge(), 0, sessionNonce, 0, 8);
/* 137:189 */           System.arraycopy(clientChallenge, 0, sessionNonce, 8, 8);
/* 138:    */           
/* 139:191 */           MD4 md4 = new MD4();
/* 140:192 */           md4.update(responseKeyNT);
/* 141:193 */           byte[] userSessionKey = md4.digest();
/* 142:    */           
/* 143:195 */           HMACT64 hmac = new HMACT64(userSessionKey);
/* 144:196 */           hmac.update(sessionNonce);
/* 145:197 */           byte[] ntlm2SessionKey = hmac.digest();
/* 146:199 */           if ((getFlags() & 0x40000000) != 0)
/* 147:    */           {
/* 148:200 */             this.masterKey = new byte[16];
/* 149:201 */             RANDOM.nextBytes(this.masterKey);
/* 150:    */             
/* 151:203 */             byte[] exchangedKey = new byte[16];
/* 152:204 */             RC4 rc4 = new RC4(ntlm2SessionKey);
/* 153:205 */             rc4.update(this.masterKey, 0, 16, exchangedKey, 0);
/* 154:    */             
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:216 */             setSessionKey(exchangedKey);
/* 165:    */           }
/* 166:    */           else
/* 167:    */           {
/* 168:218 */             this.masterKey = ntlm2SessionKey;
/* 169:219 */             setSessionKey(this.masterKey);
/* 170:    */           }
/* 171:    */         }
/* 172:    */       }
/* 173:223 */       break;
/* 174:    */     case 2: 
/* 175:225 */       byte[] nt = getNTResponse(type2, password);
/* 176:226 */       setLMResponse(nt);
/* 177:227 */       setNTResponse(nt);
/* 178:228 */       break;
/* 179:    */     case 3: 
/* 180:    */     case 4: 
/* 181:    */     case 5: 
/* 182:232 */       byte[] responseKeyNT = NtlmPasswordAuthentication.nTOWFv2(domain, user, password);
/* 183:    */       
/* 184:234 */       byte[] clientChallenge = new byte[8];
/* 185:235 */       RANDOM.nextBytes(clientChallenge);
/* 186:236 */       setLMResponse(getLMv2Response(type2, domain, user, password, clientChallenge));
/* 187:    */       
/* 188:238 */       byte[] clientChallenge2 = new byte[8];
/* 189:239 */       RANDOM.nextBytes(clientChallenge2);
/* 190:240 */       setNTResponse(getNTLMv2Response(type2, responseKeyNT, clientChallenge2));
/* 191:242 */       if ((getFlags() & 0x10) == 16)
/* 192:    */       {
/* 193:243 */         HMACT64 hmac = new HMACT64(responseKeyNT);
/* 194:244 */         hmac.update(this.ntResponse, 0, 16);
/* 195:245 */         byte[] userSessionKey = hmac.digest();
/* 196:247 */         if ((getFlags() & 0x40000000) != 0)
/* 197:    */         {
/* 198:248 */           this.masterKey = new byte[16];
/* 199:249 */           RANDOM.nextBytes(this.masterKey);
/* 200:    */           
/* 201:251 */           byte[] exchangedKey = new byte[16];
/* 202:252 */           RC4 rc4 = new RC4(userSessionKey);
/* 203:253 */           rc4.update(this.masterKey, 0, 16, exchangedKey, 0);
/* 204:    */           
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:264 */           setSessionKey(exchangedKey);
/* 215:    */         }
/* 216:    */         else
/* 217:    */         {
/* 218:266 */           this.masterKey = userSessionKey;
/* 219:267 */           setSessionKey(this.masterKey);
/* 220:    */         }
/* 221:    */       }
/* 222:    */       break;
/* 223:    */     default: 
/* 224:273 */       setLMResponse(getLMResponse(type2, password));
/* 225:274 */       setNTResponse(getNTResponse(type2, password));
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Type3Message(int flags, byte[] lmResponse, byte[] ntResponse, String domain, String user, String workstation)
/* 230:    */   {
/* 231:291 */     setFlags(flags);
/* 232:292 */     setLMResponse(lmResponse);
/* 233:293 */     setNTResponse(ntResponse);
/* 234:294 */     setDomain(domain);
/* 235:295 */     setUser(user);
/* 236:296 */     setWorkstation(workstation);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public Type3Message(byte[] material)
/* 240:    */     throws IOException
/* 241:    */   {
/* 242:306 */     parse(material);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public byte[] getLMResponse()
/* 246:    */   {
/* 247:315 */     return this.lmResponse;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setLMResponse(byte[] lmResponse)
/* 251:    */   {
/* 252:324 */     this.lmResponse = lmResponse;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public byte[] getNTResponse()
/* 256:    */   {
/* 257:333 */     return this.ntResponse;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setNTResponse(byte[] ntResponse)
/* 261:    */   {
/* 262:342 */     this.ntResponse = ntResponse;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String getDomain()
/* 266:    */   {
/* 267:351 */     return this.domain;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setDomain(String domain)
/* 271:    */   {
/* 272:360 */     this.domain = domain;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String getUser()
/* 276:    */   {
/* 277:369 */     return this.user;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setUser(String user)
/* 281:    */   {
/* 282:378 */     this.user = user;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getWorkstation()
/* 286:    */   {
/* 287:387 */     return this.workstation;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setWorkstation(String workstation)
/* 291:    */   {
/* 292:396 */     this.workstation = workstation;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public byte[] getMasterKey()
/* 296:    */   {
/* 297:406 */     return this.masterKey;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public byte[] getSessionKey()
/* 301:    */   {
/* 302:415 */     return this.sessionKey;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setSessionKey(byte[] sessionKey)
/* 306:    */   {
/* 307:424 */     this.sessionKey = sessionKey;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public byte[] toByteArray()
/* 311:    */   {
/* 312:    */     try
/* 313:    */     {
/* 314:429 */       int flags = getFlags();
/* 315:430 */       boolean unicode = (flags & 0x1) != 0;
/* 316:431 */       String oem = unicode ? null : getOEMEncoding();
/* 317:432 */       String domainName = getDomain();
/* 318:433 */       byte[] domain = null;
/* 319:434 */       if ((domainName != null) && (domainName.length() != 0)) {
/* 320:435 */         domain = unicode ? domainName.getBytes("UTF-16LE") : domainName.getBytes(oem);
/* 321:    */       }
/* 322:439 */       int domainLength = domain != null ? domain.length : 0;
/* 323:440 */       String userName = getUser();
/* 324:441 */       byte[] user = null;
/* 325:442 */       if ((userName != null) && (userName.length() != 0)) {
/* 326:443 */         user = unicode ? userName.getBytes("UTF-16LE") : userName.toUpperCase().getBytes(oem);
/* 327:    */       }
/* 328:446 */       int userLength = user != null ? user.length : 0;
/* 329:447 */       String workstationName = getWorkstation();
/* 330:448 */       byte[] workstation = null;
/* 331:449 */       if ((workstationName != null) && (workstationName.length() != 0)) {
/* 332:450 */         workstation = unicode ? workstationName.getBytes("UTF-16LE") : workstationName.toUpperCase().getBytes(oem);
/* 333:    */       }
/* 334:454 */       int workstationLength = workstation != null ? workstation.length : 0;
/* 335:    */       
/* 336:456 */       byte[] lmResponse = getLMResponse();
/* 337:457 */       int lmLength = lmResponse != null ? lmResponse.length : 0;
/* 338:458 */       byte[] ntResponse = getNTResponse();
/* 339:459 */       int ntLength = ntResponse != null ? ntResponse.length : 0;
/* 340:460 */       byte[] sessionKey = getSessionKey();
/* 341:461 */       int keyLength = sessionKey != null ? sessionKey.length : 0;
/* 342:462 */       byte[] type3 = new byte[64 + domainLength + userLength + workstationLength + lmLength + ntLength + keyLength];
/* 343:    */       
/* 344:464 */       System.arraycopy(NTLMSSP_SIGNATURE, 0, type3, 0, 8);
/* 345:465 */       writeULong(type3, 8, 3);
/* 346:466 */       int offset = 64;
/* 347:467 */       writeSecurityBuffer(type3, 12, offset, lmResponse);
/* 348:468 */       offset += lmLength;
/* 349:469 */       writeSecurityBuffer(type3, 20, offset, ntResponse);
/* 350:470 */       offset += ntLength;
/* 351:471 */       writeSecurityBuffer(type3, 28, offset, domain);
/* 352:472 */       offset += domainLength;
/* 353:473 */       writeSecurityBuffer(type3, 36, offset, user);
/* 354:474 */       offset += userLength;
/* 355:475 */       writeSecurityBuffer(type3, 44, offset, workstation);
/* 356:476 */       offset += workstationLength;
/* 357:477 */       writeSecurityBuffer(type3, 52, offset, sessionKey);
/* 358:478 */       writeULong(type3, 60, flags);
/* 359:479 */       return type3;
/* 360:    */     }
/* 361:    */     catch (IOException ex)
/* 362:    */     {
/* 363:481 */       throw new IllegalStateException(ex.getMessage());
/* 364:    */     }
/* 365:    */   }
/* 366:    */   
/* 367:    */   public String toString()
/* 368:    */   {
/* 369:486 */     String user = getUser();
/* 370:487 */     String domain = getDomain();
/* 371:488 */     String workstation = getWorkstation();
/* 372:489 */     byte[] lmResponse = getLMResponse();
/* 373:490 */     byte[] ntResponse = getNTResponse();
/* 374:491 */     byte[] sessionKey = getSessionKey();
/* 375:    */     
/* 376:493 */     return "Type3Message[domain=" + domain + ",user=" + user + ",workstation=" + workstation + ",lmResponse=" + (lmResponse == null ? "null" : new StringBuffer().append("<").append(lmResponse.length).append(" bytes>").toString()) + ",ntResponse=" + (ntResponse == null ? "null" : new StringBuffer().append("<").append(ntResponse.length).append(" bytes>").toString()) + ",sessionKey=" + (sessionKey == null ? "null" : new StringBuffer().append("<").append(sessionKey.length).append(" bytes>").toString()) + ",flags=0x" + Hexdump.toHexString(getFlags(), 8) + "]";
/* 377:    */   }
/* 378:    */   
/* 379:    */   public static int getDefaultFlags()
/* 380:    */   {
/* 381:509 */     return DEFAULT_FLAGS;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public static int getDefaultFlags(Type2Message type2)
/* 385:    */   {
/* 386:519 */     if (type2 == null) {
/* 387:519 */       return DEFAULT_FLAGS;
/* 388:    */     }
/* 389:520 */     int flags = 512;
/* 390:521 */     flags |= ((type2.getFlags() & 0x1) != 0 ? 1 : 2);
/* 391:    */     
/* 392:523 */     return flags;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public static byte[] getLMResponse(Type2Message type2, String password)
/* 396:    */   {
/* 397:535 */     if ((type2 == null) || (password == null)) {
/* 398:535 */       return null;
/* 399:    */     }
/* 400:536 */     return NtlmPasswordAuthentication.getPreNTLMResponse(password, type2.getChallenge());
/* 401:    */   }
/* 402:    */   
/* 403:    */   public static byte[] getLMv2Response(Type2Message type2, String domain, String user, String password, byte[] clientChallenge)
/* 404:    */   {
/* 405:543 */     if ((type2 == null) || (domain == null) || (user == null) || (password == null) || (clientChallenge == null)) {
/* 406:545 */       return null;
/* 407:    */     }
/* 408:547 */     return NtlmPasswordAuthentication.getLMv2Response(domain, user, password, type2.getChallenge(), clientChallenge);
/* 409:    */   }
/* 410:    */   
/* 411:    */   public static byte[] getNTLMv2Response(Type2Message type2, byte[] responseKeyNT, byte[] clientChallenge)
/* 412:    */   {
/* 413:553 */     if ((type2 == null) || (responseKeyNT == null) || (clientChallenge == null)) {
/* 414:554 */       return null;
/* 415:    */     }
/* 416:556 */     long nanos1601 = (System.currentTimeMillis() + 11644473600000L) * 10000L;
/* 417:557 */     return NtlmPasswordAuthentication.getNTLMv2Response(responseKeyNT, type2.getChallenge(), clientChallenge, nanos1601, type2.getTargetInformation());
/* 418:    */   }
/* 419:    */   
/* 420:    */   public static byte[] getNTResponse(Type2Message type2, String password)
/* 421:    */   {
/* 422:573 */     if ((type2 == null) || (password == null)) {
/* 423:573 */       return null;
/* 424:    */     }
/* 425:574 */     return NtlmPasswordAuthentication.getNTLMResponse(password, type2.getChallenge());
/* 426:    */   }
/* 427:    */   
/* 428:    */   public static String getDefaultDomain()
/* 429:    */   {
/* 430:584 */     return DEFAULT_DOMAIN;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public static String getDefaultUser()
/* 434:    */   {
/* 435:593 */     return DEFAULT_USER;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public static String getDefaultPassword()
/* 439:    */   {
/* 440:602 */     return DEFAULT_PASSWORD;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public static String getDefaultWorkstation()
/* 444:    */   {
/* 445:611 */     return DEFAULT_WORKSTATION;
/* 446:    */   }
/* 447:    */   
/* 448:    */   private void parse(byte[] material)
/* 449:    */     throws IOException
/* 450:    */   {
/* 451:615 */     for (int i = 0; i < 8; i++) {
/* 452:616 */       if (material[i] != NTLMSSP_SIGNATURE[i]) {
/* 453:617 */         throw new IOException("Not an NTLMSSP message.");
/* 454:    */       }
/* 455:    */     }
/* 456:620 */     if (readULong(material, 8) != 3) {
/* 457:621 */       throw new IOException("Not a Type 3 message.");
/* 458:    */     }
/* 459:623 */     byte[] lmResponse = readSecurityBuffer(material, 12);
/* 460:624 */     int lmResponseOffset = readULong(material, 16);
/* 461:625 */     byte[] ntResponse = readSecurityBuffer(material, 20);
/* 462:626 */     int ntResponseOffset = readULong(material, 24);
/* 463:627 */     byte[] domain = readSecurityBuffer(material, 28);
/* 464:628 */     int domainOffset = readULong(material, 32);
/* 465:629 */     byte[] user = readSecurityBuffer(material, 36);
/* 466:630 */     int userOffset = readULong(material, 40);
/* 467:631 */     byte[] workstation = readSecurityBuffer(material, 44);
/* 468:632 */     int workstationOffset = readULong(material, 48);
/* 469:    */     
/* 470:    */ 
/* 471:635 */     byte[] _sessionKey = null;
/* 472:    */     String charset;
/* 473:    */     int flags;
/* 474:    */     String charset;
/* 475:636 */     if ((lmResponseOffset == 52) || (ntResponseOffset == 52) || (domainOffset == 52) || (userOffset == 52) || (workstationOffset == 52))
/* 476:    */     {
/* 477:639 */       int flags = 514;
/* 478:640 */       charset = getOEMEncoding();
/* 479:    */     }
/* 480:    */     else
/* 481:    */     {
/* 482:642 */       _sessionKey = readSecurityBuffer(material, 52);
/* 483:643 */       flags = readULong(material, 60);
/* 484:644 */       charset = (flags & 0x1) != 0 ? "UTF-16LE" : getOEMEncoding();
/* 485:    */     }
/* 486:647 */     setSessionKey(_sessionKey);
/* 487:648 */     setFlags(flags);
/* 488:649 */     setLMResponse(lmResponse);
/* 489:650 */     setNTResponse(ntResponse);
/* 490:651 */     setDomain(new String(domain, charset));
/* 491:652 */     setUser(new String(user, charset));
/* 492:653 */     setWorkstation(new String(workstation, charset));
/* 493:    */   }
/* 494:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.ntlmssp.Type3Message
 * JD-Core Version:    0.7.0.1
 */