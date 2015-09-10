/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.security.GeneralSecurityException;
/*   6:    */ import java.security.MessageDigest;
/*   7:    */ import java.security.Principal;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Random;
/*  10:    */ import jcifs.Config;
/*  11:    */ import jcifs.util.DES;
/*  12:    */ import jcifs.util.Encdec;
/*  13:    */ import jcifs.util.HMACT64;
/*  14:    */ import jcifs.util.LogStream;
/*  15:    */ import jcifs.util.MD4;
/*  16:    */ 
/*  17:    */ public final class NtlmPasswordAuthentication
/*  18:    */   implements Principal, Serializable
/*  19:    */ {
/*  20: 44 */   private static final int LM_COMPATIBILITY = Config.getInt("jcifs.smb.lmCompatibility", 3);
/*  21: 47 */   private static final Random RANDOM = new Random();
/*  22: 49 */   private static LogStream log = LogStream.getInstance();
/*  23: 52 */   private static final byte[] S8 = { 75, 71, 83, 33, 64, 35, 36, 37 };
/*  24:    */   static String DEFAULT_DOMAIN;
/*  25:    */   static String DEFAULT_USERNAME;
/*  26:    */   static String DEFAULT_PASSWORD;
/*  27:    */   static final String BLANK = "";
/*  28:    */   
/*  29:    */   private static void E(byte[] key, byte[] data, byte[] e)
/*  30:    */   {
/*  31: 61 */     byte[] key7 = new byte[7];
/*  32: 62 */     byte[] e8 = new byte[8];
/*  33: 64 */     for (int i = 0; i < key.length / 7; i++)
/*  34:    */     {
/*  35: 65 */       System.arraycopy(key, i * 7, key7, 0, 7);
/*  36: 66 */       DES des = new DES(key7);
/*  37: 67 */       des.encrypt(data, e8);
/*  38: 68 */       System.arraycopy(e8, 0, e, i * 8, 8);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42: 77 */   public static final NtlmPasswordAuthentication ANONYMOUS = new NtlmPasswordAuthentication("", "", "");
/*  43:    */   
/*  44:    */   static void initDefaults()
/*  45:    */   {
/*  46: 80 */     if (DEFAULT_DOMAIN != null) {
/*  47: 80 */       return;
/*  48:    */     }
/*  49: 81 */     DEFAULT_DOMAIN = Config.getProperty("jcifs.smb.client.domain", "?");
/*  50: 82 */     DEFAULT_USERNAME = Config.getProperty("jcifs.smb.client.username", "GUEST");
/*  51: 83 */     DEFAULT_PASSWORD = Config.getProperty("jcifs.smb.client.password", "");
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static byte[] getPreNTLMResponse(String password, byte[] challenge)
/*  55:    */   {
/*  56: 90 */     byte[] p14 = new byte[14];
/*  57: 91 */     byte[] p21 = new byte[21];
/*  58: 92 */     byte[] p24 = new byte[24];
/*  59:    */     try
/*  60:    */     {
/*  61: 95 */       passwordBytes = password.toUpperCase().getBytes(SmbConstants.OEM_ENCODING);
/*  62:    */     }
/*  63:    */     catch (UnsupportedEncodingException uee)
/*  64:    */     {
/*  65:    */       byte[] passwordBytes;
/*  66: 97 */       throw new RuntimeException("Try setting jcifs.encoding=US-ASCII", uee);
/*  67:    */     }
/*  68:    */     byte[] passwordBytes;
/*  69: 99 */     int passwordLength = passwordBytes.length;
/*  70:102 */     if (passwordLength > 14) {
/*  71:103 */       passwordLength = 14;
/*  72:    */     }
/*  73:105 */     System.arraycopy(passwordBytes, 0, p14, 0, passwordLength);
/*  74:106 */     E(p14, S8, p21);
/*  75:107 */     E(p21, challenge, p24);
/*  76:108 */     return p24;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static byte[] getNTLMResponse(String password, byte[] challenge)
/*  80:    */   {
/*  81:114 */     byte[] uni = null;
/*  82:115 */     byte[] p21 = new byte[21];
/*  83:116 */     byte[] p24 = new byte[24];
/*  84:    */     try
/*  85:    */     {
/*  86:119 */       uni = password.getBytes("UTF-16LE");
/*  87:    */     }
/*  88:    */     catch (UnsupportedEncodingException uee)
/*  89:    */     {
/*  90:121 */       if (LogStream.level > 0) {
/*  91:122 */         uee.printStackTrace(log);
/*  92:    */       }
/*  93:    */     }
/*  94:124 */     MD4 md4 = new MD4();
/*  95:125 */     md4.update(uni);
/*  96:    */     try
/*  97:    */     {
/*  98:127 */       md4.digest(p21, 0, 16);
/*  99:    */     }
/* 100:    */     catch (Exception ex)
/* 101:    */     {
/* 102:129 */       if (LogStream.level > 0) {
/* 103:130 */         ex.printStackTrace(log);
/* 104:    */       }
/* 105:    */     }
/* 106:132 */     E(p21, challenge, p24);
/* 107:133 */     return p24;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static byte[] getLMv2Response(String domain, String user, String password, byte[] challenge, byte[] clientChallenge)
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:148 */       byte[] hash = new byte[16];
/* 115:149 */       byte[] response = new byte[24];
/* 116:    */       
/* 117:151 */       MD4 md4 = new MD4();
/* 118:152 */       md4.update(password.getBytes("UTF-16LE"));
/* 119:153 */       HMACT64 hmac = new HMACT64(md4.digest());
/* 120:154 */       hmac.update(user.toUpperCase().getBytes("UTF-16LE"));
/* 121:155 */       hmac.update(domain.toUpperCase().getBytes("UTF-16LE"));
/* 122:156 */       hmac = new HMACT64(hmac.digest());
/* 123:157 */       hmac.update(challenge);
/* 124:158 */       hmac.update(clientChallenge);
/* 125:159 */       hmac.digest(response, 0, 16);
/* 126:160 */       System.arraycopy(clientChallenge, 0, response, 16, 8);
/* 127:161 */       return response;
/* 128:    */     }
/* 129:    */     catch (Exception ex)
/* 130:    */     {
/* 131:163 */       if (LogStream.level > 0) {
/* 132:164 */         ex.printStackTrace(log);
/* 133:    */       }
/* 134:    */     }
/* 135:165 */     return null;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static byte[] getNTLM2Response(byte[] nTOWFv1, byte[] serverChallenge, byte[] clientChallenge)
/* 139:    */   {
/* 140:172 */     byte[] sessionHash = new byte[8];
/* 141:    */     try
/* 142:    */     {
/* 143:176 */       MessageDigest md5 = MessageDigest.getInstance("MD5");
/* 144:177 */       md5.update(serverChallenge);
/* 145:178 */       md5.update(clientChallenge, 0, 8);
/* 146:179 */       System.arraycopy(md5.digest(), 0, sessionHash, 0, 8);
/* 147:    */     }
/* 148:    */     catch (GeneralSecurityException gse)
/* 149:    */     {
/* 150:181 */       if (LogStream.level > 0) {
/* 151:182 */         gse.printStackTrace(log);
/* 152:    */       }
/* 153:183 */       throw new RuntimeException("MD5", gse);
/* 154:    */     }
/* 155:186 */     byte[] key = new byte[21];
/* 156:187 */     System.arraycopy(nTOWFv1, 0, key, 0, 16);
/* 157:188 */     byte[] ntResponse = new byte[24];
/* 158:189 */     E(key, sessionHash, ntResponse);
/* 159:    */     
/* 160:191 */     return ntResponse;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static byte[] nTOWFv1(String password)
/* 164:    */   {
/* 165:    */     try
/* 166:    */     {
/* 167:196 */       MD4 md4 = new MD4();
/* 168:197 */       md4.update(password.getBytes("UTF-16LE"));
/* 169:198 */       return md4.digest();
/* 170:    */     }
/* 171:    */     catch (UnsupportedEncodingException uee)
/* 172:    */     {
/* 173:200 */       throw new RuntimeException(uee.getMessage());
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static byte[] nTOWFv2(String domain, String username, String password)
/* 178:    */   {
/* 179:    */     try
/* 180:    */     {
/* 181:206 */       MD4 md4 = new MD4();
/* 182:207 */       md4.update(password.getBytes("UTF-16LE"));
/* 183:208 */       HMACT64 hmac = new HMACT64(md4.digest());
/* 184:209 */       hmac.update(username.toUpperCase().getBytes("UTF-16LE"));
/* 185:210 */       hmac.update(domain.getBytes("UTF-16LE"));
/* 186:211 */       return hmac.digest();
/* 187:    */     }
/* 188:    */     catch (UnsupportedEncodingException uee)
/* 189:    */     {
/* 190:213 */       throw new RuntimeException(uee.getMessage());
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   static byte[] computeResponse(byte[] responseKey, byte[] serverChallenge, byte[] clientData, int offset, int length)
/* 195:    */   {
/* 196:222 */     HMACT64 hmac = new HMACT64(responseKey);
/* 197:223 */     hmac.update(serverChallenge);
/* 198:224 */     hmac.update(clientData, offset, length);
/* 199:225 */     byte[] mac = hmac.digest();
/* 200:226 */     byte[] ret = new byte[mac.length + clientData.length];
/* 201:227 */     System.arraycopy(mac, 0, ret, 0, mac.length);
/* 202:228 */     System.arraycopy(clientData, 0, ret, mac.length, clientData.length);
/* 203:229 */     return ret;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static byte[] getLMv2Response(byte[] responseKeyLM, byte[] serverChallenge, byte[] clientChallenge)
/* 207:    */   {
/* 208:236 */     return computeResponse(responseKeyLM, serverChallenge, clientChallenge, 0, clientChallenge.length);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public static byte[] getNTLMv2Response(byte[] responseKeyNT, byte[] serverChallenge, byte[] clientChallenge, long nanos1601, byte[] targetInfo)
/* 212:    */   {
/* 213:249 */     int targetInfoLength = targetInfo != null ? targetInfo.length : 0;
/* 214:250 */     byte[] temp = new byte[28 + targetInfoLength + 4];
/* 215:    */     
/* 216:252 */     Encdec.enc_uint32le(257, temp, 0);
/* 217:253 */     Encdec.enc_uint32le(0, temp, 4);
/* 218:254 */     Encdec.enc_uint64le(nanos1601, temp, 8);
/* 219:255 */     System.arraycopy(clientChallenge, 0, temp, 16, 8);
/* 220:256 */     Encdec.enc_uint32le(0, temp, 24);
/* 221:257 */     if (targetInfo != null) {
/* 222:258 */       System.arraycopy(targetInfo, 0, temp, 28, targetInfoLength);
/* 223:    */     }
/* 224:259 */     Encdec.enc_uint32le(0, temp, 28 + targetInfoLength);
/* 225:    */     
/* 226:261 */     return computeResponse(responseKeyNT, serverChallenge, temp, 0, temp.length);
/* 227:    */   }
/* 228:    */   
/* 229:268 */   static final NtlmPasswordAuthentication NULL = new NtlmPasswordAuthentication("", "", "");
/* 230:270 */   static final NtlmPasswordAuthentication GUEST = new NtlmPasswordAuthentication("?", "GUEST", "");
/* 231:272 */   static final NtlmPasswordAuthentication DEFAULT = new NtlmPasswordAuthentication(null);
/* 232:    */   String domain;
/* 233:    */   String username;
/* 234:    */   String password;
/* 235:    */   byte[] ansiHash;
/* 236:    */   byte[] unicodeHash;
/* 237:280 */   boolean hashesExternal = false;
/* 238:281 */   byte[] clientChallenge = null;
/* 239:282 */   byte[] challenge = null;
/* 240:    */   
/* 241:    */   public NtlmPasswordAuthentication(String userInfo)
/* 242:    */   {
/* 243:291 */     this.domain = (this.username = this.password = null);
/* 244:293 */     if (userInfo != null)
/* 245:    */     {
/* 246:    */       try
/* 247:    */       {
/* 248:295 */         userInfo = unescape(userInfo);
/* 249:    */       }
/* 250:    */       catch (UnsupportedEncodingException uee) {}
/* 251:301 */       int end = userInfo.length();
/* 252:302 */       int i = 0;
/* 253:302 */       for (int u = 0; i < end; i++)
/* 254:    */       {
/* 255:303 */         char c = userInfo.charAt(i);
/* 256:304 */         if (c == ';')
/* 257:    */         {
/* 258:305 */           this.domain = userInfo.substring(0, i);
/* 259:306 */           u = i + 1;
/* 260:    */         }
/* 261:307 */         else if (c == ':')
/* 262:    */         {
/* 263:308 */           this.password = userInfo.substring(i + 1);
/* 264:309 */           break;
/* 265:    */         }
/* 266:    */       }
/* 267:312 */       this.username = userInfo.substring(u, i);
/* 268:    */     }
/* 269:315 */     initDefaults();
/* 270:317 */     if (this.domain == null) {
/* 271:317 */       this.domain = DEFAULT_DOMAIN;
/* 272:    */     }
/* 273:318 */     if (this.username == null) {
/* 274:318 */       this.username = DEFAULT_USERNAME;
/* 275:    */     }
/* 276:319 */     if (this.password == null) {
/* 277:319 */       this.password = DEFAULT_PASSWORD;
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   public NtlmPasswordAuthentication(String domain, String username, String password)
/* 282:    */   {
/* 283:331 */     int ci = username.indexOf('@');
/* 284:332 */     if (ci > 0)
/* 285:    */     {
/* 286:333 */       domain = username.substring(ci + 1);
/* 287:334 */       username = username.substring(0, ci);
/* 288:    */     }
/* 289:    */     else
/* 290:    */     {
/* 291:336 */       ci = username.indexOf('\\');
/* 292:337 */       if (ci > 0)
/* 293:    */       {
/* 294:338 */         domain = username.substring(0, ci);
/* 295:339 */         username = username.substring(ci + 1);
/* 296:    */       }
/* 297:    */     }
/* 298:343 */     this.domain = domain;
/* 299:344 */     this.username = username;
/* 300:345 */     this.password = password;
/* 301:    */     
/* 302:347 */     initDefaults();
/* 303:349 */     if (domain == null) {
/* 304:349 */       this.domain = DEFAULT_DOMAIN;
/* 305:    */     }
/* 306:350 */     if (username == null) {
/* 307:350 */       this.username = DEFAULT_USERNAME;
/* 308:    */     }
/* 309:351 */     if (password == null) {
/* 310:351 */       this.password = DEFAULT_PASSWORD;
/* 311:    */     }
/* 312:    */   }
/* 313:    */   
/* 314:    */   public NtlmPasswordAuthentication(String domain, String username, byte[] challenge, byte[] ansiHash, byte[] unicodeHash)
/* 315:    */   {
/* 316:360 */     if ((domain == null) || (username == null) || (ansiHash == null) || (unicodeHash == null)) {
/* 317:362 */       throw new IllegalArgumentException("External credentials cannot be null");
/* 318:    */     }
/* 319:364 */     this.domain = domain;
/* 320:365 */     this.username = username;
/* 321:366 */     this.password = null;
/* 322:367 */     this.challenge = challenge;
/* 323:368 */     this.ansiHash = ansiHash;
/* 324:369 */     this.unicodeHash = unicodeHash;
/* 325:370 */     this.hashesExternal = true;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String getDomain()
/* 329:    */   {
/* 330:377 */     return this.domain;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public String getUsername()
/* 334:    */   {
/* 335:384 */     return this.username;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String getPassword()
/* 339:    */   {
/* 340:394 */     return this.password;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public String getName()
/* 344:    */   {
/* 345:401 */     boolean d = (this.domain.length() > 0) && (!this.domain.equals("?"));
/* 346:402 */     return d ? this.domain + "\\" + this.username : this.username;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public byte[] getAnsiHash(byte[] challenge)
/* 350:    */   {
/* 351:409 */     if (this.hashesExternal) {
/* 352:410 */       return this.ansiHash;
/* 353:    */     }
/* 354:412 */     switch (LM_COMPATIBILITY)
/* 355:    */     {
/* 356:    */     case 0: 
/* 357:    */     case 1: 
/* 358:415 */       return getPreNTLMResponse(this.password, challenge);
/* 359:    */     case 2: 
/* 360:417 */       return getNTLMResponse(this.password, challenge);
/* 361:    */     case 3: 
/* 362:    */     case 4: 
/* 363:    */     case 5: 
/* 364:421 */       if (this.clientChallenge == null)
/* 365:    */       {
/* 366:422 */         this.clientChallenge = new byte[8];
/* 367:423 */         RANDOM.nextBytes(this.clientChallenge);
/* 368:    */       }
/* 369:425 */       return getLMv2Response(this.domain, this.username, this.password, challenge, this.clientChallenge);
/* 370:    */     }
/* 371:428 */     return getPreNTLMResponse(this.password, challenge);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public byte[] getUnicodeHash(byte[] challenge)
/* 375:    */   {
/* 376:435 */     if (this.hashesExternal) {
/* 377:436 */       return this.unicodeHash;
/* 378:    */     }
/* 379:438 */     switch (LM_COMPATIBILITY)
/* 380:    */     {
/* 381:    */     case 0: 
/* 382:    */     case 1: 
/* 383:    */     case 2: 
/* 384:442 */       return getNTLMResponse(this.password, challenge);
/* 385:    */     case 3: 
/* 386:    */     case 4: 
/* 387:    */     case 5: 
/* 388:454 */       return new byte[0];
/* 389:    */     }
/* 390:456 */     return getNTLMResponse(this.password, challenge);
/* 391:    */   }
/* 392:    */   
/* 393:    */   public byte[] getSigningKey(byte[] challenge)
/* 394:    */     throws SmbException
/* 395:    */   {
/* 396:462 */     switch (LM_COMPATIBILITY)
/* 397:    */     {
/* 398:    */     case 0: 
/* 399:    */     case 1: 
/* 400:    */     case 2: 
/* 401:466 */       byte[] signingKey = new byte[40];
/* 402:467 */       getUserSessionKey(challenge, signingKey, 0);
/* 403:468 */       System.arraycopy(getUnicodeHash(challenge), 0, signingKey, 16, 24);
/* 404:469 */       return signingKey;
/* 405:    */     case 3: 
/* 406:    */     case 4: 
/* 407:    */     case 5: 
/* 408:476 */       throw new SmbException("NTLMv2 requires extended security (jcifs.smb.client.useExtendedSecurity must be true if jcifs.smb.lmCompatibility >= 3)");
/* 409:    */     }
/* 410:478 */     return null;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public byte[] getUserSessionKey(byte[] challenge)
/* 414:    */   {
/* 415:489 */     if (this.hashesExternal) {
/* 416:489 */       return null;
/* 417:    */     }
/* 418:490 */     byte[] key = new byte[16];
/* 419:    */     try
/* 420:    */     {
/* 421:492 */       getUserSessionKey(challenge, key, 0);
/* 422:    */     }
/* 423:    */     catch (Exception ex)
/* 424:    */     {
/* 425:494 */       if (LogStream.level > 0) {
/* 426:495 */         ex.printStackTrace(log);
/* 427:    */       }
/* 428:    */     }
/* 429:497 */     return key;
/* 430:    */   }
/* 431:    */   
/* 432:    */   void getUserSessionKey(byte[] challenge, byte[] dest, int offset)
/* 433:    */     throws SmbException
/* 434:    */   {
/* 435:510 */     if (this.hashesExternal) {
/* 436:510 */       return;
/* 437:    */     }
/* 438:    */     try
/* 439:    */     {
/* 440:512 */       MD4 md4 = new MD4();
/* 441:513 */       md4.update(this.password.getBytes("UTF-16LE"));
/* 442:514 */       switch (LM_COMPATIBILITY)
/* 443:    */       {
/* 444:    */       case 0: 
/* 445:    */       case 1: 
/* 446:    */       case 2: 
/* 447:518 */         md4.update(md4.digest());
/* 448:519 */         md4.digest(dest, offset, 16);
/* 449:520 */         break;
/* 450:    */       case 3: 
/* 451:    */       case 4: 
/* 452:    */       case 5: 
/* 453:524 */         if (this.clientChallenge == null)
/* 454:    */         {
/* 455:525 */           this.clientChallenge = new byte[8];
/* 456:526 */           RANDOM.nextBytes(this.clientChallenge);
/* 457:    */         }
/* 458:529 */         HMACT64 hmac = new HMACT64(md4.digest());
/* 459:530 */         hmac.update(this.username.toUpperCase().getBytes("UTF-16LE"));
/* 460:    */         
/* 461:532 */         hmac.update(this.domain.toUpperCase().getBytes("UTF-16LE"));
/* 462:    */         
/* 463:534 */         byte[] ntlmv2Hash = hmac.digest();
/* 464:535 */         hmac = new HMACT64(ntlmv2Hash);
/* 465:536 */         hmac.update(challenge);
/* 466:537 */         hmac.update(this.clientChallenge);
/* 467:538 */         HMACT64 userKey = new HMACT64(ntlmv2Hash);
/* 468:539 */         userKey.update(hmac.digest());
/* 469:540 */         userKey.digest(dest, offset, 16);
/* 470:541 */         break;
/* 471:    */       default: 
/* 472:543 */         md4.update(md4.digest());
/* 473:544 */         md4.digest(dest, offset, 16);
/* 474:    */       }
/* 475:    */     }
/* 476:    */     catch (Exception e)
/* 477:    */     {
/* 478:548 */       throw new SmbException("", e);
/* 479:    */     }
/* 480:    */   }
/* 481:    */   
/* 482:    */   public boolean equals(Object obj)
/* 483:    */   {
/* 484:558 */     if ((obj instanceof NtlmPasswordAuthentication))
/* 485:    */     {
/* 486:559 */       NtlmPasswordAuthentication ntlm = (NtlmPasswordAuthentication)obj;
/* 487:560 */       if ((ntlm.domain.toUpperCase().equals(this.domain.toUpperCase())) && (ntlm.username.toUpperCase().equals(this.username.toUpperCase())))
/* 488:    */       {
/* 489:562 */         if ((this.hashesExternal) && (ntlm.hashesExternal)) {
/* 490:563 */           return (Arrays.equals(this.ansiHash, ntlm.ansiHash)) && (Arrays.equals(this.unicodeHash, ntlm.unicodeHash));
/* 491:    */         }
/* 492:569 */         if ((!this.hashesExternal) && (this.password.equals(ntlm.password))) {
/* 493:570 */           return true;
/* 494:    */         }
/* 495:    */       }
/* 496:    */     }
/* 497:574 */     return false;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public int hashCode()
/* 501:    */   {
/* 502:582 */     return getName().toUpperCase().hashCode();
/* 503:    */   }
/* 504:    */   
/* 505:    */   public String toString()
/* 506:    */   {
/* 507:589 */     return getName();
/* 508:    */   }
/* 509:    */   
/* 510:    */   static String unescape(String str)
/* 511:    */     throws NumberFormatException, UnsupportedEncodingException
/* 512:    */   {
/* 513:596 */     byte[] b = new byte[1];
/* 514:598 */     if (str == null) {
/* 515:599 */       return null;
/* 516:    */     }
/* 517:602 */     int len = str.length();
/* 518:603 */     char[] out = new char[len];
/* 519:604 */     int state = 0;
/* 520:    */     int j;
/* 521:605 */     for (int i = j = 0; i < len; i++) {
/* 522:606 */       switch (state)
/* 523:    */       {
/* 524:    */       case 0: 
/* 525:608 */         char ch = str.charAt(i);
/* 526:609 */         if (ch == '%') {
/* 527:610 */           state = 1;
/* 528:    */         } else {
/* 529:612 */           out[(j++)] = ch;
/* 530:    */         }
/* 531:614 */         break;
/* 532:    */       case 1: 
/* 533:619 */         b[0] = ((byte)(Integer.parseInt(str.substring(i, i + 2), 16) & 0xFF));
/* 534:620 */         out[(j++)] = new String(b, 0, 1, "ASCII").charAt(0);
/* 535:621 */         i++;
/* 536:622 */         state = 0;
/* 537:    */       }
/* 538:    */     }
/* 539:626 */     return new String(out, 0, j);
/* 540:    */   }
/* 541:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NtlmPasswordAuthentication
 * JD-Core Version:    0.7.0.1
 */