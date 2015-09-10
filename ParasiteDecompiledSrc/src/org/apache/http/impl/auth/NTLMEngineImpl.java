/*    1:     */ package org.apache.http.impl.auth;
/*    2:     */ 
/*    3:     */ import java.io.UnsupportedEncodingException;
/*    4:     */ import java.security.Key;
/*    5:     */ import java.security.MessageDigest;
/*    6:     */ import java.security.SecureRandom;
/*    7:     */ import java.util.Arrays;
/*    8:     */ import javax.crypto.Cipher;
/*    9:     */ import javax.crypto.spec.SecretKeySpec;
/*   10:     */ import org.apache.commons.codec.binary.Base64;
/*   11:     */ import org.apache.http.util.EncodingUtils;
/*   12:     */ 
/*   13:     */ final class NTLMEngineImpl
/*   14:     */   implements NTLMEngine
/*   15:     */ {
/*   16:     */   protected static final int FLAG_UNICODE_ENCODING = 1;
/*   17:     */   protected static final int FLAG_TARGET_DESIRED = 4;
/*   18:     */   protected static final int FLAG_NEGOTIATE_SIGN = 16;
/*   19:     */   protected static final int FLAG_NEGOTIATE_SEAL = 32;
/*   20:     */   protected static final int FLAG_NEGOTIATE_NTLM = 512;
/*   21:     */   protected static final int FLAG_NEGOTIATE_ALWAYS_SIGN = 32768;
/*   22:     */   protected static final int FLAG_NEGOTIATE_NTLM2 = 524288;
/*   23:     */   protected static final int FLAG_NEGOTIATE_128 = 536870912;
/*   24:     */   protected static final int FLAG_NEGOTIATE_KEY_EXCH = 1073741824;
/*   25:     */   private static final SecureRandom RND_GEN;
/*   26:     */   static final String DEFAULT_CHARSET = "ASCII";
/*   27:     */   private String credentialCharset;
/*   28:     */   private static byte[] SIGNATURE;
/*   29:     */   
/*   30:     */   NTLMEngineImpl()
/*   31:     */   {
/*   32:  73 */     this.credentialCharset = "ASCII";
/*   33:     */   }
/*   34:     */   
/*   35:     */   static
/*   36:     */   {
/*   37:  61 */     SecureRandom rnd = null;
/*   38:     */     try
/*   39:     */     {
/*   40:  63 */       rnd = SecureRandom.getInstance("SHA1PRNG");
/*   41:     */     }
/*   42:     */     catch (Exception e) {}
/*   43:  66 */     RND_GEN = rnd;
/*   44:     */     
/*   45:     */ 
/*   46:     */ 
/*   47:     */ 
/*   48:     */ 
/*   49:     */ 
/*   50:     */ 
/*   51:     */ 
/*   52:     */ 
/*   53:     */ 
/*   54:     */ 
/*   55:     */ 
/*   56:  79 */     byte[] bytesWithoutNull = EncodingUtils.getBytes("NTLMSSP", "ASCII");
/*   57:  80 */     SIGNATURE = new byte[bytesWithoutNull.length + 1];
/*   58:  81 */     System.arraycopy(bytesWithoutNull, 0, SIGNATURE, 0, bytesWithoutNull.length);
/*   59:  82 */     SIGNATURE[bytesWithoutNull.length] = 0;
/*   60:     */   }
/*   61:     */   
/*   62:     */   final String getResponseFor(String message, String username, String password, String host, String domain)
/*   63:     */     throws NTLMEngineException
/*   64:     */   {
/*   65:     */     String response;
/*   66:     */     String response;
/*   67: 106 */     if ((message == null) || (message.trim().equals("")))
/*   68:     */     {
/*   69: 107 */       response = getType1Message(host, domain);
/*   70:     */     }
/*   71:     */     else
/*   72:     */     {
/*   73: 109 */       Type2Message t2m = new Type2Message(message);
/*   74: 110 */       response = getType3Message(username, password, host, domain, t2m.getChallenge(), t2m.getFlags(), t2m.getTarget(), t2m.getTargetInfo());
/*   75:     */     }
/*   76: 113 */     return response;
/*   77:     */   }
/*   78:     */   
/*   79:     */   String getType1Message(String host, String domain)
/*   80:     */     throws NTLMEngineException
/*   81:     */   {
/*   82: 128 */     return new Type1Message(domain, host).getResponse();
/*   83:     */   }
/*   84:     */   
/*   85:     */   String getType3Message(String user, String password, String host, String domain, byte[] nonce, int type2Flags, String target, byte[] targetInformation)
/*   86:     */     throws NTLMEngineException
/*   87:     */   {
/*   88: 154 */     return new Type3Message(domain, host, user, password, nonce, type2Flags, target, targetInformation).getResponse();
/*   89:     */   }
/*   90:     */   
/*   91:     */   String getCredentialCharset()
/*   92:     */   {
/*   93: 162 */     return this.credentialCharset;
/*   94:     */   }
/*   95:     */   
/*   96:     */   void setCredentialCharset(String credentialCharset)
/*   97:     */   {
/*   98: 170 */     this.credentialCharset = credentialCharset;
/*   99:     */   }
/*  100:     */   
/*  101:     */   private static String stripDotSuffix(String value)
/*  102:     */   {
/*  103: 175 */     int index = value.indexOf(".");
/*  104: 176 */     if (index != -1) {
/*  105: 177 */       return value.substring(0, index);
/*  106:     */     }
/*  107: 178 */     return value;
/*  108:     */   }
/*  109:     */   
/*  110:     */   private static String convertHost(String host)
/*  111:     */   {
/*  112: 183 */     return stripDotSuffix(host);
/*  113:     */   }
/*  114:     */   
/*  115:     */   private static String convertDomain(String domain)
/*  116:     */   {
/*  117: 188 */     return stripDotSuffix(domain);
/*  118:     */   }
/*  119:     */   
/*  120:     */   private static int readULong(byte[] src, int index)
/*  121:     */     throws NTLMEngineException
/*  122:     */   {
/*  123: 192 */     if (src.length < index + 4) {
/*  124: 193 */       throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
/*  125:     */     }
/*  126: 194 */     return src[index] & 0xFF | (src[(index + 1)] & 0xFF) << 8 | (src[(index + 2)] & 0xFF) << 16 | (src[(index + 3)] & 0xFF) << 24;
/*  127:     */   }
/*  128:     */   
/*  129:     */   private static int readUShort(byte[] src, int index)
/*  130:     */     throws NTLMEngineException
/*  131:     */   {
/*  132: 199 */     if (src.length < index + 2) {
/*  133: 200 */       throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
/*  134:     */     }
/*  135: 201 */     return src[index] & 0xFF | (src[(index + 1)] & 0xFF) << 8;
/*  136:     */   }
/*  137:     */   
/*  138:     */   private static byte[] readSecurityBuffer(byte[] src, int index)
/*  139:     */     throws NTLMEngineException
/*  140:     */   {
/*  141: 205 */     int length = readUShort(src, index);
/*  142: 206 */     int offset = readULong(src, index + 4);
/*  143: 207 */     if (src.length < offset + length) {
/*  144: 208 */       throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
/*  145:     */     }
/*  146: 210 */     byte[] buffer = new byte[length];
/*  147: 211 */     System.arraycopy(src, offset, buffer, 0, length);
/*  148: 212 */     return buffer;
/*  149:     */   }
/*  150:     */   
/*  151:     */   private static byte[] makeRandomChallenge()
/*  152:     */     throws NTLMEngineException
/*  153:     */   {
/*  154: 217 */     if (RND_GEN == null) {
/*  155: 218 */       throw new NTLMEngineException("Random generator not available");
/*  156:     */     }
/*  157: 220 */     byte[] rval = new byte[8];
/*  158: 221 */     synchronized (RND_GEN)
/*  159:     */     {
/*  160: 222 */       RND_GEN.nextBytes(rval);
/*  161:     */     }
/*  162: 224 */     return rval;
/*  163:     */   }
/*  164:     */   
/*  165:     */   private static byte[] makeNTLM2RandomChallenge()
/*  166:     */     throws NTLMEngineException
/*  167:     */   {
/*  168: 229 */     if (RND_GEN == null) {
/*  169: 230 */       throw new NTLMEngineException("Random generator not available");
/*  170:     */     }
/*  171: 232 */     byte[] rval = new byte[24];
/*  172: 233 */     synchronized (RND_GEN)
/*  173:     */     {
/*  174: 234 */       RND_GEN.nextBytes(rval);
/*  175:     */     }
/*  176: 237 */     Arrays.fill(rval, 8, 24, (byte)0);
/*  177: 238 */     return rval;
/*  178:     */   }
/*  179:     */   
/*  180:     */   static byte[] getLMResponse(String password, byte[] challenge)
/*  181:     */     throws NTLMEngineException
/*  182:     */   {
/*  183: 254 */     byte[] lmHash = lmHash(password);
/*  184: 255 */     return lmResponse(lmHash, challenge);
/*  185:     */   }
/*  186:     */   
/*  187:     */   static byte[] getNTLMResponse(String password, byte[] challenge)
/*  188:     */     throws NTLMEngineException
/*  189:     */   {
/*  190: 271 */     byte[] ntlmHash = ntlmHash(password);
/*  191: 272 */     return lmResponse(ntlmHash, challenge);
/*  192:     */   }
/*  193:     */   
/*  194:     */   static byte[] getNTLMv2Response(String target, String user, String password, byte[] challenge, byte[] clientChallenge, byte[] targetInformation)
/*  195:     */     throws NTLMEngineException
/*  196:     */   {
/*  197: 298 */     byte[] ntlmv2Hash = ntlmv2Hash(target, user, password);
/*  198: 299 */     byte[] blob = createBlob(clientChallenge, targetInformation);
/*  199: 300 */     return lmv2Response(ntlmv2Hash, challenge, blob);
/*  200:     */   }
/*  201:     */   
/*  202:     */   static byte[] getLMv2Response(String target, String user, String password, byte[] challenge, byte[] clientChallenge)
/*  203:     */     throws NTLMEngineException
/*  204:     */   {
/*  205: 322 */     byte[] ntlmv2Hash = ntlmv2Hash(target, user, password);
/*  206: 323 */     return lmv2Response(ntlmv2Hash, challenge, clientChallenge);
/*  207:     */   }
/*  208:     */   
/*  209:     */   static byte[] getNTLM2SessionResponse(String password, byte[] challenge, byte[] clientChallenge)
/*  210:     */     throws NTLMEngineException
/*  211:     */   {
/*  212:     */     try
/*  213:     */     {
/*  214: 344 */       byte[] ntlmHash = ntlmHash(password);
/*  215:     */       
/*  216:     */ 
/*  217:     */ 
/*  218:     */ 
/*  219:     */ 
/*  220:     */ 
/*  221:     */ 
/*  222:     */ 
/*  223:     */ 
/*  224:     */ 
/*  225:     */ 
/*  226:     */ 
/*  227:     */ 
/*  228:     */ 
/*  229: 359 */       MessageDigest md5 = MessageDigest.getInstance("MD5");
/*  230: 360 */       md5.update(challenge);
/*  231: 361 */       md5.update(clientChallenge);
/*  232: 362 */       byte[] digest = md5.digest();
/*  233:     */       
/*  234: 364 */       byte[] sessionHash = new byte[8];
/*  235: 365 */       System.arraycopy(digest, 0, sessionHash, 0, 8);
/*  236: 366 */       return lmResponse(ntlmHash, sessionHash);
/*  237:     */     }
/*  238:     */     catch (Exception e)
/*  239:     */     {
/*  240: 368 */       if ((e instanceof NTLMEngineException)) {
/*  241: 369 */         throw ((NTLMEngineException)e);
/*  242:     */       }
/*  243: 370 */       throw new NTLMEngineException(e.getMessage(), e);
/*  244:     */     }
/*  245:     */   }
/*  246:     */   
/*  247:     */   private static byte[] lmHash(String password)
/*  248:     */     throws NTLMEngineException
/*  249:     */   {
/*  250:     */     try
/*  251:     */     {
/*  252: 385 */       byte[] oemPassword = password.toUpperCase().getBytes("US-ASCII");
/*  253: 386 */       int length = Math.min(oemPassword.length, 14);
/*  254: 387 */       byte[] keyBytes = new byte[14];
/*  255: 388 */       System.arraycopy(oemPassword, 0, keyBytes, 0, length);
/*  256: 389 */       Key lowKey = createDESKey(keyBytes, 0);
/*  257: 390 */       Key highKey = createDESKey(keyBytes, 7);
/*  258: 391 */       byte[] magicConstant = "KGS!@#$%".getBytes("US-ASCII");
/*  259: 392 */       Cipher des = Cipher.getInstance("DES/ECB/NoPadding");
/*  260: 393 */       des.init(1, lowKey);
/*  261: 394 */       byte[] lowHash = des.doFinal(magicConstant);
/*  262: 395 */       des.init(1, highKey);
/*  263: 396 */       byte[] highHash = des.doFinal(magicConstant);
/*  264: 397 */       byte[] lmHash = new byte[16];
/*  265: 398 */       System.arraycopy(lowHash, 0, lmHash, 0, 8);
/*  266: 399 */       System.arraycopy(highHash, 0, lmHash, 8, 8);
/*  267: 400 */       return lmHash;
/*  268:     */     }
/*  269:     */     catch (Exception e)
/*  270:     */     {
/*  271: 402 */       throw new NTLMEngineException(e.getMessage(), e);
/*  272:     */     }
/*  273:     */   }
/*  274:     */   
/*  275:     */   private static byte[] ntlmHash(String password)
/*  276:     */     throws NTLMEngineException
/*  277:     */   {
/*  278:     */     try
/*  279:     */     {
/*  280: 417 */       byte[] unicodePassword = password.getBytes("UnicodeLittleUnmarked");
/*  281: 418 */       MD4 md4 = new MD4();
/*  282: 419 */       md4.update(unicodePassword);
/*  283: 420 */       return md4.getOutput();
/*  284:     */     }
/*  285:     */     catch (UnsupportedEncodingException e)
/*  286:     */     {
/*  287: 422 */       throw new NTLMEngineException("Unicode not supported: " + e.getMessage(), e);
/*  288:     */     }
/*  289:     */   }
/*  290:     */   
/*  291:     */   private static byte[] ntlmv2Hash(String target, String user, String password)
/*  292:     */     throws NTLMEngineException
/*  293:     */   {
/*  294:     */     try
/*  295:     */     {
/*  296: 442 */       byte[] ntlmHash = ntlmHash(password);
/*  297: 443 */       HMACMD5 hmacMD5 = new HMACMD5(ntlmHash);
/*  298:     */       
/*  299: 445 */       hmacMD5.update(user.toUpperCase().getBytes("UnicodeLittleUnmarked"));
/*  300: 446 */       hmacMD5.update(target.getBytes("UnicodeLittleUnmarked"));
/*  301: 447 */       return hmacMD5.getOutput();
/*  302:     */     }
/*  303:     */     catch (UnsupportedEncodingException e)
/*  304:     */     {
/*  305: 449 */       throw new NTLMEngineException("Unicode not supported! " + e.getMessage(), e);
/*  306:     */     }
/*  307:     */   }
/*  308:     */   
/*  309:     */   private static byte[] lmResponse(byte[] hash, byte[] challenge)
/*  310:     */     throws NTLMEngineException
/*  311:     */   {
/*  312:     */     try
/*  313:     */     {
/*  314: 465 */       byte[] keyBytes = new byte[21];
/*  315: 466 */       System.arraycopy(hash, 0, keyBytes, 0, 16);
/*  316: 467 */       Key lowKey = createDESKey(keyBytes, 0);
/*  317: 468 */       Key middleKey = createDESKey(keyBytes, 7);
/*  318: 469 */       Key highKey = createDESKey(keyBytes, 14);
/*  319: 470 */       Cipher des = Cipher.getInstance("DES/ECB/NoPadding");
/*  320: 471 */       des.init(1, lowKey);
/*  321: 472 */       byte[] lowResponse = des.doFinal(challenge);
/*  322: 473 */       des.init(1, middleKey);
/*  323: 474 */       byte[] middleResponse = des.doFinal(challenge);
/*  324: 475 */       des.init(1, highKey);
/*  325: 476 */       byte[] highResponse = des.doFinal(challenge);
/*  326: 477 */       byte[] lmResponse = new byte[24];
/*  327: 478 */       System.arraycopy(lowResponse, 0, lmResponse, 0, 8);
/*  328: 479 */       System.arraycopy(middleResponse, 0, lmResponse, 8, 8);
/*  329: 480 */       System.arraycopy(highResponse, 0, lmResponse, 16, 8);
/*  330: 481 */       return lmResponse;
/*  331:     */     }
/*  332:     */     catch (Exception e)
/*  333:     */     {
/*  334: 483 */       throw new NTLMEngineException(e.getMessage(), e);
/*  335:     */     }
/*  336:     */   }
/*  337:     */   
/*  338:     */   private static byte[] lmv2Response(byte[] hash, byte[] challenge, byte[] clientData)
/*  339:     */     throws NTLMEngineException
/*  340:     */   {
/*  341: 503 */     HMACMD5 hmacMD5 = new HMACMD5(hash);
/*  342: 504 */     hmacMD5.update(challenge);
/*  343: 505 */     hmacMD5.update(clientData);
/*  344: 506 */     byte[] mac = hmacMD5.getOutput();
/*  345: 507 */     byte[] lmv2Response = new byte[mac.length + clientData.length];
/*  346: 508 */     System.arraycopy(mac, 0, lmv2Response, 0, mac.length);
/*  347: 509 */     System.arraycopy(clientData, 0, lmv2Response, mac.length, clientData.length);
/*  348: 510 */     return lmv2Response;
/*  349:     */   }
/*  350:     */   
/*  351:     */   private static byte[] createBlob(byte[] clientChallenge, byte[] targetInformation)
/*  352:     */   {
/*  353: 525 */     byte[] blobSignature = { 1, 1, 0, 0 };
/*  354: 526 */     byte[] reserved = { 0, 0, 0, 0 };
/*  355: 527 */     byte[] unknown1 = { 0, 0, 0, 0 };
/*  356: 528 */     long time = System.currentTimeMillis();
/*  357: 529 */     time += 11644473600000L;
/*  358: 530 */     time *= 10000L;
/*  359:     */     
/*  360: 532 */     byte[] timestamp = new byte[8];
/*  361: 533 */     for (int i = 0; i < 8; i++)
/*  362:     */     {
/*  363: 534 */       timestamp[i] = ((byte)(int)time);
/*  364: 535 */       time >>>= 8;
/*  365:     */     }
/*  366: 537 */     byte[] blob = new byte[blobSignature.length + reserved.length + timestamp.length + 8 + unknown1.length + targetInformation.length];
/*  367:     */     
/*  368: 539 */     int offset = 0;
/*  369: 540 */     System.arraycopy(blobSignature, 0, blob, offset, blobSignature.length);
/*  370: 541 */     offset += blobSignature.length;
/*  371: 542 */     System.arraycopy(reserved, 0, blob, offset, reserved.length);
/*  372: 543 */     offset += reserved.length;
/*  373: 544 */     System.arraycopy(timestamp, 0, blob, offset, timestamp.length);
/*  374: 545 */     offset += timestamp.length;
/*  375: 546 */     System.arraycopy(clientChallenge, 0, blob, offset, 8);
/*  376: 547 */     offset += 8;
/*  377: 548 */     System.arraycopy(unknown1, 0, blob, offset, unknown1.length);
/*  378: 549 */     offset += unknown1.length;
/*  379: 550 */     System.arraycopy(targetInformation, 0, blob, offset, targetInformation.length);
/*  380: 551 */     return blob;
/*  381:     */   }
/*  382:     */   
/*  383:     */   private static Key createDESKey(byte[] bytes, int offset)
/*  384:     */   {
/*  385: 567 */     byte[] keyBytes = new byte[7];
/*  386: 568 */     System.arraycopy(bytes, offset, keyBytes, 0, 7);
/*  387: 569 */     byte[] material = new byte[8];
/*  388: 570 */     material[0] = keyBytes[0];
/*  389: 571 */     material[1] = ((byte)(keyBytes[0] << 7 | (keyBytes[1] & 0xFF) >>> 1));
/*  390: 572 */     material[2] = ((byte)(keyBytes[1] << 6 | (keyBytes[2] & 0xFF) >>> 2));
/*  391: 573 */     material[3] = ((byte)(keyBytes[2] << 5 | (keyBytes[3] & 0xFF) >>> 3));
/*  392: 574 */     material[4] = ((byte)(keyBytes[3] << 4 | (keyBytes[4] & 0xFF) >>> 4));
/*  393: 575 */     material[5] = ((byte)(keyBytes[4] << 3 | (keyBytes[5] & 0xFF) >>> 5));
/*  394: 576 */     material[6] = ((byte)(keyBytes[5] << 2 | (keyBytes[6] & 0xFF) >>> 6));
/*  395: 577 */     material[7] = ((byte)(keyBytes[6] << 1));
/*  396: 578 */     oddParity(material);
/*  397: 579 */     return new SecretKeySpec(material, "DES");
/*  398:     */   }
/*  399:     */   
/*  400:     */   private static void oddParity(byte[] bytes)
/*  401:     */   {
/*  402: 589 */     for (int i = 0; i < bytes.length; i++)
/*  403:     */     {
/*  404: 590 */       byte b = bytes[i];
/*  405: 591 */       boolean needsParity = ((b >>> 7 ^ b >>> 6 ^ b >>> 5 ^ b >>> 4 ^ b >>> 3 ^ b >>> 2 ^ b >>> 1) & 0x1) == 0;
/*  406: 593 */       if (needsParity)
/*  407:     */       {
/*  408: 594 */         int tmp58_57 = i;bytes[tmp58_57] = ((byte)(bytes[tmp58_57] | 0x1));
/*  409:     */       }
/*  410:     */       else
/*  411:     */       {
/*  412: 596 */         int tmp69_68 = i;bytes[tmp69_68] = ((byte)(bytes[tmp69_68] & 0xFFFFFFFE));
/*  413:     */       }
/*  414:     */     }
/*  415:     */   }
/*  416:     */   
/*  417:     */   static class NTLMMessage
/*  418:     */   {
/*  419: 604 */     private byte[] messageContents = null;
/*  420: 607 */     private int currentOutputPosition = 0;
/*  421:     */     
/*  422:     */     NTLMMessage() {}
/*  423:     */     
/*  424:     */     NTLMMessage(String messageBody, int expectedType)
/*  425:     */       throws NTLMEngineException
/*  426:     */     {
/*  427: 615 */       this.messageContents = Base64.decodeBase64(EncodingUtils.getBytes(messageBody, "ASCII"));
/*  428: 618 */       if (this.messageContents.length < NTLMEngineImpl.SIGNATURE.length) {
/*  429: 619 */         throw new NTLMEngineException("NTLM message decoding error - packet too short");
/*  430:     */       }
/*  431: 620 */       int i = 0;
/*  432: 621 */       while (i < NTLMEngineImpl.SIGNATURE.length)
/*  433:     */       {
/*  434: 622 */         if (this.messageContents[i] != NTLMEngineImpl.SIGNATURE[i]) {
/*  435: 623 */           throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
/*  436:     */         }
/*  437: 625 */         i++;
/*  438:     */       }
/*  439: 629 */       int type = readULong(NTLMEngineImpl.SIGNATURE.length);
/*  440: 630 */       if (type != expectedType) {
/*  441: 631 */         throw new NTLMEngineException("NTLM type " + Integer.toString(expectedType) + " message expected - instead got type " + Integer.toString(type));
/*  442:     */       }
/*  443: 634 */       this.currentOutputPosition = this.messageContents.length;
/*  444:     */     }
/*  445:     */     
/*  446:     */     protected int getPreambleLength()
/*  447:     */     {
/*  448: 642 */       return NTLMEngineImpl.SIGNATURE.length + 4;
/*  449:     */     }
/*  450:     */     
/*  451:     */     protected int getMessageLength()
/*  452:     */     {
/*  453: 647 */       return this.currentOutputPosition;
/*  454:     */     }
/*  455:     */     
/*  456:     */     protected byte readByte(int position)
/*  457:     */       throws NTLMEngineException
/*  458:     */     {
/*  459: 652 */       if (this.messageContents.length < position + 1) {
/*  460: 653 */         throw new NTLMEngineException("NTLM: Message too short");
/*  461:     */       }
/*  462: 654 */       return this.messageContents[position];
/*  463:     */     }
/*  464:     */     
/*  465:     */     protected void readBytes(byte[] buffer, int position)
/*  466:     */       throws NTLMEngineException
/*  467:     */     {
/*  468: 659 */       if (this.messageContents.length < position + buffer.length) {
/*  469: 660 */         throw new NTLMEngineException("NTLM: Message too short");
/*  470:     */       }
/*  471: 661 */       System.arraycopy(this.messageContents, position, buffer, 0, buffer.length);
/*  472:     */     }
/*  473:     */     
/*  474:     */     protected int readUShort(int position)
/*  475:     */       throws NTLMEngineException
/*  476:     */     {
/*  477: 666 */       return NTLMEngineImpl.readUShort(this.messageContents, position);
/*  478:     */     }
/*  479:     */     
/*  480:     */     protected int readULong(int position)
/*  481:     */       throws NTLMEngineException
/*  482:     */     {
/*  483: 671 */       return NTLMEngineImpl.readULong(this.messageContents, position);
/*  484:     */     }
/*  485:     */     
/*  486:     */     protected byte[] readSecurityBuffer(int position)
/*  487:     */       throws NTLMEngineException
/*  488:     */     {
/*  489: 676 */       return NTLMEngineImpl.readSecurityBuffer(this.messageContents, position);
/*  490:     */     }
/*  491:     */     
/*  492:     */     protected void prepareResponse(int maxlength, int messageType)
/*  493:     */     {
/*  494: 688 */       this.messageContents = new byte[maxlength];
/*  495: 689 */       this.currentOutputPosition = 0;
/*  496: 690 */       addBytes(NTLMEngineImpl.SIGNATURE);
/*  497: 691 */       addULong(messageType);
/*  498:     */     }
/*  499:     */     
/*  500:     */     protected void addByte(byte b)
/*  501:     */     {
/*  502: 701 */       this.messageContents[this.currentOutputPosition] = b;
/*  503: 702 */       this.currentOutputPosition += 1;
/*  504:     */     }
/*  505:     */     
/*  506:     */     protected void addBytes(byte[] bytes)
/*  507:     */     {
/*  508: 712 */       for (int i = 0; i < bytes.length; i++)
/*  509:     */       {
/*  510: 713 */         this.messageContents[this.currentOutputPosition] = bytes[i];
/*  511: 714 */         this.currentOutputPosition += 1;
/*  512:     */       }
/*  513:     */     }
/*  514:     */     
/*  515:     */     protected void addUShort(int value)
/*  516:     */     {
/*  517: 720 */       addByte((byte)(value & 0xFF));
/*  518: 721 */       addByte((byte)(value >> 8 & 0xFF));
/*  519:     */     }
/*  520:     */     
/*  521:     */     protected void addULong(int value)
/*  522:     */     {
/*  523: 726 */       addByte((byte)(value & 0xFF));
/*  524: 727 */       addByte((byte)(value >> 8 & 0xFF));
/*  525: 728 */       addByte((byte)(value >> 16 & 0xFF));
/*  526: 729 */       addByte((byte)(value >> 24 & 0xFF));
/*  527:     */     }
/*  528:     */     
/*  529:     */     String getResponse()
/*  530:     */     {
/*  531:     */       byte[] resp;
/*  532:     */       byte[] resp;
/*  533: 740 */       if (this.messageContents.length > this.currentOutputPosition)
/*  534:     */       {
/*  535: 741 */         byte[] tmp = new byte[this.currentOutputPosition];
/*  536: 742 */         for (int i = 0; i < this.currentOutputPosition; i++) {
/*  537: 743 */           tmp[i] = this.messageContents[i];
/*  538:     */         }
/*  539: 745 */         resp = tmp;
/*  540:     */       }
/*  541:     */       else
/*  542:     */       {
/*  543: 747 */         resp = this.messageContents;
/*  544:     */       }
/*  545: 749 */       return EncodingUtils.getAsciiString(Base64.encodeBase64(resp));
/*  546:     */     }
/*  547:     */   }
/*  548:     */   
/*  549:     */   static class Type1Message
/*  550:     */     extends NTLMEngineImpl.NTLMMessage
/*  551:     */   {
/*  552:     */     protected byte[] hostBytes;
/*  553:     */     protected byte[] domainBytes;
/*  554:     */     
/*  555:     */     Type1Message(String domain, String host)
/*  556:     */       throws NTLMEngineException
/*  557:     */     {
/*  558:     */       try
/*  559:     */       {
/*  560: 764 */         host = NTLMEngineImpl.convertHost(host);
/*  561:     */         
/*  562: 766 */         domain = NTLMEngineImpl.convertDomain(domain);
/*  563:     */         
/*  564: 768 */         this.hostBytes = host.getBytes("UnicodeLittleUnmarked");
/*  565: 769 */         this.domainBytes = domain.toUpperCase().getBytes("UnicodeLittleUnmarked");
/*  566:     */       }
/*  567:     */       catch (UnsupportedEncodingException e)
/*  568:     */       {
/*  569: 771 */         throw new NTLMEngineException("Unicode unsupported: " + e.getMessage(), e);
/*  570:     */       }
/*  571:     */     }
/*  572:     */     
/*  573:     */     String getResponse()
/*  574:     */     {
/*  575: 783 */       int finalLength = 32 + this.hostBytes.length + this.domainBytes.length;
/*  576:     */       
/*  577:     */ 
/*  578:     */ 
/*  579: 787 */       prepareResponse(finalLength, 1);
/*  580:     */       
/*  581:     */ 
/*  582: 790 */       addULong(537395765);
/*  583:     */       
/*  584:     */ 
/*  585:     */ 
/*  586:     */ 
/*  587:     */ 
/*  588:     */ 
/*  589:     */ 
/*  590: 798 */       addUShort(this.domainBytes.length);
/*  591: 799 */       addUShort(this.domainBytes.length);
/*  592:     */       
/*  593:     */ 
/*  594: 802 */       addULong(this.hostBytes.length + 32);
/*  595:     */       
/*  596:     */ 
/*  597: 805 */       addUShort(this.hostBytes.length);
/*  598: 806 */       addUShort(this.hostBytes.length);
/*  599:     */       
/*  600:     */ 
/*  601: 809 */       addULong(32);
/*  602:     */       
/*  603:     */ 
/*  604: 812 */       addBytes(this.hostBytes);
/*  605:     */       
/*  606:     */ 
/*  607: 815 */       addBytes(this.domainBytes);
/*  608:     */       
/*  609: 817 */       return super.getResponse();
/*  610:     */     }
/*  611:     */   }
/*  612:     */   
/*  613:     */   static class Type2Message
/*  614:     */     extends NTLMEngineImpl.NTLMMessage
/*  615:     */   {
/*  616:     */     protected byte[] challenge;
/*  617:     */     protected String target;
/*  618:     */     protected byte[] targetInfo;
/*  619:     */     protected int flags;
/*  620:     */     
/*  621:     */     Type2Message(String message)
/*  622:     */       throws NTLMEngineException
/*  623:     */     {
/*  624: 830 */       super(2);
/*  625:     */       
/*  626:     */ 
/*  627:     */ 
/*  628: 834 */       this.challenge = new byte[8];
/*  629: 835 */       readBytes(this.challenge, 24);
/*  630:     */       
/*  631: 837 */       this.flags = readULong(20);
/*  632: 838 */       if ((this.flags & 0x1) == 0) {
/*  633: 839 */         throw new NTLMEngineException("NTLM type 2 message has flags that make no sense: " + Integer.toString(this.flags));
/*  634:     */       }
/*  635: 843 */       this.target = null;
/*  636: 847 */       if (getMessageLength() >= 20)
/*  637:     */       {
/*  638: 848 */         byte[] bytes = readSecurityBuffer(12);
/*  639: 849 */         if (bytes.length != 0) {
/*  640:     */           try
/*  641:     */           {
/*  642: 851 */             this.target = new String(bytes, "UnicodeLittleUnmarked");
/*  643:     */           }
/*  644:     */           catch (UnsupportedEncodingException e)
/*  645:     */           {
/*  646: 853 */             throw new NTLMEngineException(e.getMessage(), e);
/*  647:     */           }
/*  648:     */         }
/*  649:     */       }
/*  650: 859 */       this.targetInfo = null;
/*  651: 861 */       if (getMessageLength() >= 48)
/*  652:     */       {
/*  653: 862 */         byte[] bytes = readSecurityBuffer(40);
/*  654: 863 */         if (bytes.length != 0) {
/*  655: 864 */           this.targetInfo = bytes;
/*  656:     */         }
/*  657:     */       }
/*  658:     */     }
/*  659:     */     
/*  660:     */     byte[] getChallenge()
/*  661:     */     {
/*  662: 871 */       return this.challenge;
/*  663:     */     }
/*  664:     */     
/*  665:     */     String getTarget()
/*  666:     */     {
/*  667: 876 */       return this.target;
/*  668:     */     }
/*  669:     */     
/*  670:     */     byte[] getTargetInfo()
/*  671:     */     {
/*  672: 881 */       return this.targetInfo;
/*  673:     */     }
/*  674:     */     
/*  675:     */     int getFlags()
/*  676:     */     {
/*  677: 886 */       return this.flags;
/*  678:     */     }
/*  679:     */   }
/*  680:     */   
/*  681:     */   static class Type3Message
/*  682:     */     extends NTLMEngineImpl.NTLMMessage
/*  683:     */   {
/*  684:     */     protected int type2Flags;
/*  685:     */     protected byte[] domainBytes;
/*  686:     */     protected byte[] hostBytes;
/*  687:     */     protected byte[] userBytes;
/*  688:     */     protected byte[] lmResp;
/*  689:     */     protected byte[] ntResp;
/*  690:     */     
/*  691:     */     Type3Message(String domain, String host, String user, String password, byte[] nonce, int type2Flags, String target, byte[] targetInformation)
/*  692:     */       throws NTLMEngineException
/*  693:     */     {
/*  694: 908 */       this.type2Flags = type2Flags;
/*  695:     */       
/*  696:     */ 
/*  697: 911 */       host = NTLMEngineImpl.convertHost(host);
/*  698:     */       
/*  699: 913 */       domain = NTLMEngineImpl.convertDomain(domain);
/*  700:     */       try
/*  701:     */       {
/*  702: 918 */         if ((targetInformation != null) && (target != null))
/*  703:     */         {
/*  704: 919 */           byte[] clientChallenge = NTLMEngineImpl.access$600();
/*  705: 920 */           this.ntResp = NTLMEngineImpl.getNTLMv2Response(target, user, password, nonce, clientChallenge, targetInformation);
/*  706:     */           
/*  707: 922 */           this.lmResp = NTLMEngineImpl.getLMv2Response(target, user, password, nonce, clientChallenge);
/*  708:     */         }
/*  709: 924 */         else if ((type2Flags & 0x80000) != 0)
/*  710:     */         {
/*  711: 926 */           byte[] clientChallenge = NTLMEngineImpl.access$700();
/*  712:     */           
/*  713: 928 */           this.ntResp = NTLMEngineImpl.getNTLM2SessionResponse(password, nonce, clientChallenge);
/*  714: 929 */           this.lmResp = clientChallenge;
/*  715:     */         }
/*  716:     */         else
/*  717:     */         {
/*  718: 936 */           this.ntResp = NTLMEngineImpl.getNTLMResponse(password, nonce);
/*  719: 937 */           this.lmResp = NTLMEngineImpl.getLMResponse(password, nonce);
/*  720:     */         }
/*  721:     */       }
/*  722:     */       catch (NTLMEngineException e)
/*  723:     */       {
/*  724: 943 */         this.ntResp = new byte[0];
/*  725: 944 */         this.lmResp = NTLMEngineImpl.getLMResponse(password, nonce);
/*  726:     */       }
/*  727:     */       try
/*  728:     */       {
/*  729: 948 */         this.domainBytes = domain.toUpperCase().getBytes("UnicodeLittleUnmarked");
/*  730: 949 */         this.hostBytes = host.getBytes("UnicodeLittleUnmarked");
/*  731: 950 */         this.userBytes = user.getBytes("UnicodeLittleUnmarked");
/*  732:     */       }
/*  733:     */       catch (UnsupportedEncodingException e)
/*  734:     */       {
/*  735: 952 */         throw new NTLMEngineException("Unicode not supported: " + e.getMessage(), e);
/*  736:     */       }
/*  737:     */     }
/*  738:     */     
/*  739:     */     String getResponse()
/*  740:     */     {
/*  741: 959 */       int ntRespLen = this.ntResp.length;
/*  742: 960 */       int lmRespLen = this.lmResp.length;
/*  743:     */       
/*  744: 962 */       int domainLen = this.domainBytes.length;
/*  745: 963 */       int hostLen = this.hostBytes.length;
/*  746: 964 */       int userLen = this.userBytes.length;
/*  747:     */       
/*  748:     */ 
/*  749: 967 */       int lmRespOffset = 64;
/*  750: 968 */       int ntRespOffset = lmRespOffset + lmRespLen;
/*  751: 969 */       int domainOffset = ntRespOffset + ntRespLen;
/*  752: 970 */       int userOffset = domainOffset + domainLen;
/*  753: 971 */       int hostOffset = userOffset + userLen;
/*  754: 972 */       int sessionKeyOffset = hostOffset + hostLen;
/*  755: 973 */       int finalLength = sessionKeyOffset + 0;
/*  756:     */       
/*  757:     */ 
/*  758: 976 */       prepareResponse(finalLength, 3);
/*  759:     */       
/*  760:     */ 
/*  761: 979 */       addUShort(lmRespLen);
/*  762: 980 */       addUShort(lmRespLen);
/*  763:     */       
/*  764:     */ 
/*  765: 983 */       addULong(lmRespOffset);
/*  766:     */       
/*  767:     */ 
/*  768: 986 */       addUShort(ntRespLen);
/*  769: 987 */       addUShort(ntRespLen);
/*  770:     */       
/*  771:     */ 
/*  772: 990 */       addULong(ntRespOffset);
/*  773:     */       
/*  774:     */ 
/*  775: 993 */       addUShort(domainLen);
/*  776: 994 */       addUShort(domainLen);
/*  777:     */       
/*  778:     */ 
/*  779: 997 */       addULong(domainOffset);
/*  780:     */       
/*  781:     */ 
/*  782:1000 */       addUShort(userLen);
/*  783:1001 */       addUShort(userLen);
/*  784:     */       
/*  785:     */ 
/*  786:1004 */       addULong(userOffset);
/*  787:     */       
/*  788:     */ 
/*  789:1007 */       addUShort(hostLen);
/*  790:1008 */       addUShort(hostLen);
/*  791:     */       
/*  792:     */ 
/*  793:1011 */       addULong(hostOffset);
/*  794:     */       
/*  795:     */ 
/*  796:1014 */       addULong(0);
/*  797:     */       
/*  798:     */ 
/*  799:1017 */       addULong(finalLength);
/*  800:     */       
/*  801:     */ 
/*  802:     */ 
/*  803:1021 */       addULong(0x20000205 | this.type2Flags & 0x80000 | this.type2Flags & 0x10 | this.type2Flags & 0x20 | this.type2Flags & 0x40000000 | this.type2Flags & 0x8000);
/*  804:     */       
/*  805:     */ 
/*  806:     */ 
/*  807:     */ 
/*  808:     */ 
/*  809:     */ 
/*  810:1028 */       addBytes(this.lmResp);
/*  811:1029 */       addBytes(this.ntResp);
/*  812:1030 */       addBytes(this.domainBytes);
/*  813:1031 */       addBytes(this.userBytes);
/*  814:1032 */       addBytes(this.hostBytes);
/*  815:     */       
/*  816:1034 */       return super.getResponse();
/*  817:     */     }
/*  818:     */   }
/*  819:     */   
/*  820:     */   static void writeULong(byte[] buffer, int value, int offset)
/*  821:     */   {
/*  822:1039 */     buffer[offset] = ((byte)(value & 0xFF));
/*  823:1040 */     buffer[(offset + 1)] = ((byte)(value >> 8 & 0xFF));
/*  824:1041 */     buffer[(offset + 2)] = ((byte)(value >> 16 & 0xFF));
/*  825:1042 */     buffer[(offset + 3)] = ((byte)(value >> 24 & 0xFF));
/*  826:     */   }
/*  827:     */   
/*  828:     */   static int F(int x, int y, int z)
/*  829:     */   {
/*  830:1046 */     return x & y | (x ^ 0xFFFFFFFF) & z;
/*  831:     */   }
/*  832:     */   
/*  833:     */   static int G(int x, int y, int z)
/*  834:     */   {
/*  835:1050 */     return x & y | x & z | y & z;
/*  836:     */   }
/*  837:     */   
/*  838:     */   static int H(int x, int y, int z)
/*  839:     */   {
/*  840:1054 */     return x ^ y ^ z;
/*  841:     */   }
/*  842:     */   
/*  843:     */   static int rotintlft(int val, int numbits)
/*  844:     */   {
/*  845:1058 */     return val << numbits | val >>> 32 - numbits;
/*  846:     */   }
/*  847:     */   
/*  848:     */   static class MD4
/*  849:     */   {
/*  850:1069 */     protected int A = 1732584193;
/*  851:1070 */     protected int B = -271733879;
/*  852:1071 */     protected int C = -1732584194;
/*  853:1072 */     protected int D = 271733878;
/*  854:1073 */     protected long count = 0L;
/*  855:1074 */     protected byte[] dataBuffer = new byte[64];
/*  856:     */     
/*  857:     */     void update(byte[] input)
/*  858:     */     {
/*  859:1083 */       int curBufferPos = (int)(this.count & 0x3F);
/*  860:1084 */       int inputIndex = 0;
/*  861:1085 */       while (input.length - inputIndex + curBufferPos >= this.dataBuffer.length)
/*  862:     */       {
/*  863:1089 */         int transferAmt = this.dataBuffer.length - curBufferPos;
/*  864:1090 */         System.arraycopy(input, inputIndex, this.dataBuffer, curBufferPos, transferAmt);
/*  865:1091 */         this.count += transferAmt;
/*  866:1092 */         curBufferPos = 0;
/*  867:1093 */         inputIndex += transferAmt;
/*  868:1094 */         processBuffer();
/*  869:     */       }
/*  870:1099 */       if (inputIndex < input.length)
/*  871:     */       {
/*  872:1100 */         int transferAmt = input.length - inputIndex;
/*  873:1101 */         System.arraycopy(input, inputIndex, this.dataBuffer, curBufferPos, transferAmt);
/*  874:1102 */         this.count += transferAmt;
/*  875:1103 */         curBufferPos += transferAmt;
/*  876:     */       }
/*  877:     */     }
/*  878:     */     
/*  879:     */     byte[] getOutput()
/*  880:     */     {
/*  881:1110 */       int bufferIndex = (int)(this.count & 0x3F);
/*  882:1111 */       int padLen = bufferIndex < 56 ? 56 - bufferIndex : 120 - bufferIndex;
/*  883:1112 */       byte[] postBytes = new byte[padLen + 8];
/*  884:     */       
/*  885:     */ 
/*  886:1115 */       postBytes[0] = -128;
/*  887:1117 */       for (int i = 0; i < 8; i++) {
/*  888:1118 */         postBytes[(padLen + i)] = ((byte)(int)(this.count * 8L >>> 8 * i));
/*  889:     */       }
/*  890:1122 */       update(postBytes);
/*  891:     */       
/*  892:     */ 
/*  893:1125 */       byte[] result = new byte[16];
/*  894:1126 */       NTLMEngineImpl.writeULong(result, this.A, 0);
/*  895:1127 */       NTLMEngineImpl.writeULong(result, this.B, 4);
/*  896:1128 */       NTLMEngineImpl.writeULong(result, this.C, 8);
/*  897:1129 */       NTLMEngineImpl.writeULong(result, this.D, 12);
/*  898:1130 */       return result;
/*  899:     */     }
/*  900:     */     
/*  901:     */     protected void processBuffer()
/*  902:     */     {
/*  903:1135 */       int[] d = new int[16];
/*  904:1137 */       for (int i = 0; i < 16; i++) {
/*  905:1138 */         d[i] = ((this.dataBuffer[(i * 4)] & 0xFF) + ((this.dataBuffer[(i * 4 + 1)] & 0xFF) << 8) + ((this.dataBuffer[(i * 4 + 2)] & 0xFF) << 16) + ((this.dataBuffer[(i * 4 + 3)] & 0xFF) << 24));
/*  906:     */       }
/*  907:1144 */       int AA = this.A;
/*  908:1145 */       int BB = this.B;
/*  909:1146 */       int CC = this.C;
/*  910:1147 */       int DD = this.D;
/*  911:1148 */       round1(d);
/*  912:1149 */       round2(d);
/*  913:1150 */       round3(d);
/*  914:1151 */       this.A += AA;
/*  915:1152 */       this.B += BB;
/*  916:1153 */       this.C += CC;
/*  917:1154 */       this.D += DD;
/*  918:     */     }
/*  919:     */     
/*  920:     */     protected void round1(int[] d)
/*  921:     */     {
/*  922:1159 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + d[0], 3);
/*  923:1160 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + d[1], 7);
/*  924:1161 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + d[2], 11);
/*  925:1162 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + d[3], 19);
/*  926:     */       
/*  927:1164 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + d[4], 3);
/*  928:1165 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + d[5], 7);
/*  929:1166 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + d[6], 11);
/*  930:1167 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + d[7], 19);
/*  931:     */       
/*  932:1169 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + d[8], 3);
/*  933:1170 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + d[9], 7);
/*  934:1171 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + d[10], 11);
/*  935:1172 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + d[11], 19);
/*  936:     */       
/*  937:1174 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + d[12], 3);
/*  938:1175 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + d[13], 7);
/*  939:1176 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + d[14], 11);
/*  940:1177 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + d[15], 19);
/*  941:     */     }
/*  942:     */     
/*  943:     */     protected void round2(int[] d)
/*  944:     */     {
/*  945:1181 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + d[0] + 1518500249, 3);
/*  946:1182 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + d[4] + 1518500249, 5);
/*  947:1183 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + d[8] + 1518500249, 9);
/*  948:1184 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + d[12] + 1518500249, 13);
/*  949:     */       
/*  950:1186 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + d[1] + 1518500249, 3);
/*  951:1187 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + d[5] + 1518500249, 5);
/*  952:1188 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + d[9] + 1518500249, 9);
/*  953:1189 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + d[13] + 1518500249, 13);
/*  954:     */       
/*  955:1191 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + d[2] + 1518500249, 3);
/*  956:1192 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + d[6] + 1518500249, 5);
/*  957:1193 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + d[10] + 1518500249, 9);
/*  958:1194 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + d[14] + 1518500249, 13);
/*  959:     */       
/*  960:1196 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + d[3] + 1518500249, 3);
/*  961:1197 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + d[7] + 1518500249, 5);
/*  962:1198 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + d[11] + 1518500249, 9);
/*  963:1199 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + d[15] + 1518500249, 13);
/*  964:     */     }
/*  965:     */     
/*  966:     */     protected void round3(int[] d)
/*  967:     */     {
/*  968:1204 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + d[0] + 1859775393, 3);
/*  969:1205 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + d[8] + 1859775393, 9);
/*  970:1206 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + d[4] + 1859775393, 11);
/*  971:1207 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + d[12] + 1859775393, 15);
/*  972:     */       
/*  973:1209 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + d[2] + 1859775393, 3);
/*  974:1210 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + d[10] + 1859775393, 9);
/*  975:1211 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + d[6] + 1859775393, 11);
/*  976:1212 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + d[14] + 1859775393, 15);
/*  977:     */       
/*  978:1214 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + d[1] + 1859775393, 3);
/*  979:1215 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + d[9] + 1859775393, 9);
/*  980:1216 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + d[5] + 1859775393, 11);
/*  981:1217 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + d[13] + 1859775393, 15);
/*  982:     */       
/*  983:1219 */       this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + d[3] + 1859775393, 3);
/*  984:1220 */       this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + d[11] + 1859775393, 9);
/*  985:1221 */       this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + d[7] + 1859775393, 11);
/*  986:1222 */       this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + d[15] + 1859775393, 15);
/*  987:     */     }
/*  988:     */   }
/*  989:     */   
/*  990:     */   static class HMACMD5
/*  991:     */   {
/*  992:     */     protected byte[] ipad;
/*  993:     */     protected byte[] opad;
/*  994:     */     protected MessageDigest md5;
/*  995:     */     
/*  996:     */     HMACMD5(byte[] key)
/*  997:     */       throws NTLMEngineException
/*  998:     */     {
/*  999:     */       try
/* 1000:     */       {
/* 1001:1239 */         this.md5 = MessageDigest.getInstance("MD5");
/* 1002:     */       }
/* 1003:     */       catch (Exception ex)
/* 1004:     */       {
/* 1005:1243 */         throw new NTLMEngineException("Error getting md5 message digest implementation: " + ex.getMessage(), ex);
/* 1006:     */       }
/* 1007:1248 */       this.ipad = new byte[64];
/* 1008:1249 */       this.opad = new byte[64];
/* 1009:     */       
/* 1010:1251 */       int keyLength = key.length;
/* 1011:1252 */       if (keyLength > 64)
/* 1012:     */       {
/* 1013:1254 */         this.md5.update(key);
/* 1014:1255 */         key = this.md5.digest();
/* 1015:1256 */         keyLength = key.length;
/* 1016:     */       }
/* 1017:1258 */       int i = 0;
/* 1018:1259 */       while (i < keyLength)
/* 1019:     */       {
/* 1020:1260 */         this.ipad[i] = ((byte)(key[i] ^ 0x36));
/* 1021:1261 */         this.opad[i] = ((byte)(key[i] ^ 0x5C));
/* 1022:1262 */         i++;
/* 1023:     */       }
/* 1024:1264 */       while (i < 64)
/* 1025:     */       {
/* 1026:1265 */         this.ipad[i] = 54;
/* 1027:1266 */         this.opad[i] = 92;
/* 1028:1267 */         i++;
/* 1029:     */       }
/* 1030:1271 */       this.md5.reset();
/* 1031:1272 */       this.md5.update(this.ipad);
/* 1032:     */     }
/* 1033:     */     
/* 1034:     */     byte[] getOutput()
/* 1035:     */     {
/* 1036:1278 */       byte[] digest = this.md5.digest();
/* 1037:1279 */       this.md5.update(this.opad);
/* 1038:1280 */       return this.md5.digest(digest);
/* 1039:     */     }
/* 1040:     */     
/* 1041:     */     void update(byte[] input)
/* 1042:     */     {
/* 1043:1285 */       this.md5.update(input);
/* 1044:     */     }
/* 1045:     */     
/* 1046:     */     void update(byte[] input, int offset, int length)
/* 1047:     */     {
/* 1048:1290 */       this.md5.update(input, offset, length);
/* 1049:     */     }
/* 1050:     */   }
/* 1051:     */   
/* 1052:     */   public String generateType1Msg(String domain, String workstation)
/* 1053:     */     throws NTLMEngineException
/* 1054:     */   {
/* 1055:1298 */     return getType1Message(workstation, domain);
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   public String generateType3Msg(String username, String password, String domain, String workstation, String challenge)
/* 1059:     */     throws NTLMEngineException
/* 1060:     */   {
/* 1061:1307 */     Type2Message t2m = new Type2Message(challenge);
/* 1062:1308 */     return getType3Message(username, password, workstation, domain, t2m.getChallenge(), t2m.getFlags(), t2m.getTarget(), t2m.getTargetInfo());
/* 1063:     */   }
/* 1064:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.NTLMEngineImpl
 * JD-Core Version:    0.7.0.1
 */