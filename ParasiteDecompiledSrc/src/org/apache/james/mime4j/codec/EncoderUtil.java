/*   1:    */ package org.apache.james.mime4j.codec;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.charset.Charset;
/*   5:    */ import java.util.BitSet;
/*   6:    */ import java.util.Locale;
/*   7:    */ import org.apache.james.mime4j.util.CharsetUtil;
/*   8:    */ 
/*   9:    */ public class EncoderUtil
/*  10:    */ {
/*  11: 35 */   private static final byte[] BASE64_TABLE = Base64OutputStream.BASE64_TABLE;
/*  12:    */   private static final char BASE64_PAD = '=';
/*  13: 38 */   private static final BitSet Q_REGULAR_CHARS = initChars("=_?");
/*  14: 40 */   private static final BitSet Q_RESTRICTED_CHARS = initChars("=_?\"#$%&'(),.:;<>@[\\]^`{|}~");
/*  15:    */   private static final int MAX_USED_CHARACTERS = 50;
/*  16:    */   private static final String ENC_WORD_PREFIX = "=?";
/*  17:    */   private static final String ENC_WORD_SUFFIX = "?=";
/*  18:    */   private static final int ENCODED_WORD_MAX_LENGTH = 75;
/*  19: 49 */   private static final BitSet TOKEN_CHARS = initChars("()<>@,;:\\\"/[]?=");
/*  20: 51 */   private static final BitSet ATEXT_CHARS = initChars("()<>@.,;:\\\"[]");
/*  21:    */   
/*  22:    */   private static BitSet initChars(String specials)
/*  23:    */   {
/*  24: 54 */     BitSet bs = new BitSet(128);
/*  25: 55 */     for (char ch = '!'; ch < ''; ch = (char)(ch + '\001')) {
/*  26: 56 */       if (specials.indexOf(ch) == -1) {
/*  27: 57 */         bs.set(ch);
/*  28:    */       }
/*  29:    */     }
/*  30: 60 */     return bs;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static enum Encoding
/*  34:    */   {
/*  35: 68 */     B,  Q;
/*  36:    */     
/*  37:    */     private Encoding() {}
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static enum Usage
/*  41:    */   {
/*  42: 81 */     TEXT_TOKEN,  WORD_ENTITY;
/*  43:    */     
/*  44:    */     private Usage() {}
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static String encodeAddressDisplayName(String displayName)
/*  48:    */   {
/*  49:110 */     if (isAtomPhrase(displayName)) {
/*  50:111 */       return displayName;
/*  51:    */     }
/*  52:112 */     if (hasToBeEncoded(displayName, 0)) {
/*  53:113 */       return encodeEncodedWord(displayName, Usage.WORD_ENTITY);
/*  54:    */     }
/*  55:115 */     return quote(displayName);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static String encodeAddressLocalPart(String localPart)
/*  59:    */   {
/*  60:134 */     if (isDotAtomText(localPart)) {
/*  61:135 */       return localPart;
/*  62:    */     }
/*  63:137 */     return quote(localPart);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static String encodeHeaderParameter(String name, String value)
/*  67:    */   {
/*  68:153 */     name = name.toLowerCase(Locale.US);
/*  69:156 */     if (isToken(value)) {
/*  70:157 */       return name + "=" + value;
/*  71:    */     }
/*  72:159 */     return name + "=" + quote(value);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static String encodeIfNecessary(String text, Usage usage, int usedCharacters)
/*  76:    */   {
/*  77:179 */     if (hasToBeEncoded(text, usedCharacters)) {
/*  78:180 */       return encodeEncodedWord(text, usage, usedCharacters);
/*  79:    */     }
/*  80:182 */     return text;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static boolean hasToBeEncoded(String text, int usedCharacters)
/*  84:    */   {
/*  85:200 */     if (text == null) {
/*  86:201 */       throw new IllegalArgumentException();
/*  87:    */     }
/*  88:202 */     if ((usedCharacters < 0) || (usedCharacters > 50)) {
/*  89:203 */       throw new IllegalArgumentException();
/*  90:    */     }
/*  91:205 */     int nonWhiteSpaceCount = usedCharacters;
/*  92:207 */     for (int idx = 0; idx < text.length(); idx++)
/*  93:    */     {
/*  94:208 */       char ch = text.charAt(idx);
/*  95:209 */       if ((ch == '\t') || (ch == ' '))
/*  96:    */       {
/*  97:210 */         nonWhiteSpaceCount = 0;
/*  98:    */       }
/*  99:    */       else
/* 100:    */       {
/* 101:212 */         nonWhiteSpaceCount++;
/* 102:213 */         if (nonWhiteSpaceCount > 77) {
/* 103:218 */           return true;
/* 104:    */         }
/* 105:221 */         if ((ch < ' ') || (ch >= '')) {
/* 106:223 */           return true;
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110:228 */     return false;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static String encodeEncodedWord(String text, Usage usage)
/* 114:    */   {
/* 115:252 */     return encodeEncodedWord(text, usage, 0, null, null);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static String encodeEncodedWord(String text, Usage usage, int usedCharacters)
/* 119:    */   {
/* 120:276 */     return encodeEncodedWord(text, usage, usedCharacters, null, null);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static String encodeEncodedWord(String text, Usage usage, int usedCharacters, Charset charset, Encoding encoding)
/* 124:    */   {
/* 125:305 */     if (text == null) {
/* 126:306 */       throw new IllegalArgumentException();
/* 127:    */     }
/* 128:307 */     if ((usedCharacters < 0) || (usedCharacters > 50)) {
/* 129:308 */       throw new IllegalArgumentException();
/* 130:    */     }
/* 131:310 */     if (charset == null) {
/* 132:311 */       charset = determineCharset(text);
/* 133:    */     }
/* 134:313 */     String mimeCharset = CharsetUtil.toMimeCharset(charset.name());
/* 135:314 */     if (mimeCharset == null) {
/* 136:316 */       throw new IllegalArgumentException("Unsupported charset");
/* 137:    */     }
/* 138:319 */     byte[] bytes = encode(text, charset);
/* 139:321 */     if (encoding == null) {
/* 140:322 */       encoding = determineEncoding(bytes, usage);
/* 141:    */     }
/* 142:324 */     if (encoding == Encoding.B)
/* 143:    */     {
/* 144:325 */       String prefix = "=?" + mimeCharset + "?B?";
/* 145:326 */       return encodeB(prefix, text, usedCharacters, charset, bytes);
/* 146:    */     }
/* 147:328 */     String prefix = "=?" + mimeCharset + "?Q?";
/* 148:329 */     return encodeQ(prefix, text, usage, usedCharacters, charset, bytes);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static String encodeB(byte[] bytes)
/* 152:    */   {
/* 153:342 */     StringBuilder sb = new StringBuilder();
/* 154:    */     
/* 155:344 */     int idx = 0;
/* 156:345 */     int end = bytes.length;
/* 157:346 */     for (; idx < end - 2; idx += 3)
/* 158:    */     {
/* 159:347 */       int data = (bytes[idx] & 0xFF) << 16 | (bytes[(idx + 1)] & 0xFF) << 8 | bytes[(idx + 2)] & 0xFF;
/* 160:    */       
/* 161:349 */       sb.append((char)BASE64_TABLE[(data >> 18 & 0x3F)]);
/* 162:350 */       sb.append((char)BASE64_TABLE[(data >> 12 & 0x3F)]);
/* 163:351 */       sb.append((char)BASE64_TABLE[(data >> 6 & 0x3F)]);
/* 164:352 */       sb.append((char)BASE64_TABLE[(data & 0x3F)]);
/* 165:    */     }
/* 166:355 */     if (idx == end - 2)
/* 167:    */     {
/* 168:356 */       int data = (bytes[idx] & 0xFF) << 16 | (bytes[(idx + 1)] & 0xFF) << 8;
/* 169:357 */       sb.append((char)BASE64_TABLE[(data >> 18 & 0x3F)]);
/* 170:358 */       sb.append((char)BASE64_TABLE[(data >> 12 & 0x3F)]);
/* 171:359 */       sb.append((char)BASE64_TABLE[(data >> 6 & 0x3F)]);
/* 172:360 */       sb.append('=');
/* 173:    */     }
/* 174:362 */     else if (idx == end - 1)
/* 175:    */     {
/* 176:363 */       int data = (bytes[idx] & 0xFF) << 16;
/* 177:364 */       sb.append((char)BASE64_TABLE[(data >> 18 & 0x3F)]);
/* 178:365 */       sb.append((char)BASE64_TABLE[(data >> 12 & 0x3F)]);
/* 179:366 */       sb.append('=');
/* 180:367 */       sb.append('=');
/* 181:    */     }
/* 182:370 */     return sb.toString();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static String encodeQ(byte[] bytes, Usage usage)
/* 186:    */   {
/* 187:385 */     BitSet qChars = usage == Usage.TEXT_TOKEN ? Q_REGULAR_CHARS : Q_RESTRICTED_CHARS;
/* 188:    */     
/* 189:    */ 
/* 190:388 */     StringBuilder sb = new StringBuilder();
/* 191:    */     
/* 192:390 */     int end = bytes.length;
/* 193:391 */     for (int idx = 0; idx < end; idx++)
/* 194:    */     {
/* 195:392 */       int v = bytes[idx] & 0xFF;
/* 196:393 */       if (v == 32)
/* 197:    */       {
/* 198:394 */         sb.append('_');
/* 199:    */       }
/* 200:395 */       else if (!qChars.get(v))
/* 201:    */       {
/* 202:396 */         sb.append('=');
/* 203:397 */         sb.append(hexDigit(v >>> 4));
/* 204:398 */         sb.append(hexDigit(v & 0xF));
/* 205:    */       }
/* 206:    */       else
/* 207:    */       {
/* 208:400 */         sb.append((char)v);
/* 209:    */       }
/* 210:    */     }
/* 211:404 */     return sb.toString();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static boolean isToken(String str)
/* 215:    */   {
/* 216:422 */     int length = str.length();
/* 217:423 */     if (length == 0) {
/* 218:424 */       return false;
/* 219:    */     }
/* 220:426 */     for (int idx = 0; idx < length; idx++)
/* 221:    */     {
/* 222:427 */       char ch = str.charAt(idx);
/* 223:428 */       if (!TOKEN_CHARS.get(ch)) {
/* 224:429 */         return false;
/* 225:    */       }
/* 226:    */     }
/* 227:432 */     return true;
/* 228:    */   }
/* 229:    */   
/* 230:    */   private static boolean isAtomPhrase(String str)
/* 231:    */   {
/* 232:438 */     boolean containsAText = false;
/* 233:    */     
/* 234:440 */     int length = str.length();
/* 235:441 */     for (int idx = 0; idx < length; idx++)
/* 236:    */     {
/* 237:442 */       char ch = str.charAt(idx);
/* 238:443 */       if (ATEXT_CHARS.get(ch)) {
/* 239:444 */         containsAText = true;
/* 240:445 */       } else if (!CharsetUtil.isWhitespace(ch)) {
/* 241:446 */         return false;
/* 242:    */       }
/* 243:    */     }
/* 244:450 */     return containsAText;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private static boolean isDotAtomText(String str)
/* 248:    */   {
/* 249:459 */     char prev = '.';
/* 250:    */     
/* 251:461 */     int length = str.length();
/* 252:462 */     if (length == 0) {
/* 253:463 */       return false;
/* 254:    */     }
/* 255:465 */     for (int idx = 0; idx < length; idx++)
/* 256:    */     {
/* 257:466 */       char ch = str.charAt(idx);
/* 258:468 */       if (ch == '.')
/* 259:    */       {
/* 260:469 */         if ((prev == '.') || (idx == length - 1)) {
/* 261:470 */           return false;
/* 262:    */         }
/* 263:    */       }
/* 264:472 */       else if (!ATEXT_CHARS.get(ch)) {
/* 265:473 */         return false;
/* 266:    */       }
/* 267:476 */       prev = ch;
/* 268:    */     }
/* 269:479 */     return true;
/* 270:    */   }
/* 271:    */   
/* 272:    */   private static String quote(String str)
/* 273:    */   {
/* 274:491 */     String escaped = str.replaceAll("[\\\\\"]", "\\\\$0");
/* 275:492 */     return "\"" + escaped + "\"";
/* 276:    */   }
/* 277:    */   
/* 278:    */   private static String encodeB(String prefix, String text, int usedCharacters, Charset charset, byte[] bytes)
/* 279:    */   {
/* 280:497 */     int encodedLength = bEncodedLength(bytes);
/* 281:    */     
/* 282:499 */     int totalLength = prefix.length() + encodedLength + "?=".length();
/* 283:501 */     if (totalLength <= 75 - usedCharacters) {
/* 284:502 */       return prefix + encodeB(bytes) + "?=";
/* 285:    */     }
/* 286:504 */     String part1 = text.substring(0, text.length() / 2);
/* 287:505 */     byte[] bytes1 = encode(part1, charset);
/* 288:506 */     String word1 = encodeB(prefix, part1, usedCharacters, charset, bytes1);
/* 289:    */     
/* 290:    */ 
/* 291:509 */     String part2 = text.substring(text.length() / 2);
/* 292:510 */     byte[] bytes2 = encode(part2, charset);
/* 293:511 */     String word2 = encodeB(prefix, part2, 0, charset, bytes2);
/* 294:    */     
/* 295:513 */     return word1 + " " + word2;
/* 296:    */   }
/* 297:    */   
/* 298:    */   private static int bEncodedLength(byte[] bytes)
/* 299:    */   {
/* 300:518 */     return (bytes.length + 2) / 3 * 4;
/* 301:    */   }
/* 302:    */   
/* 303:    */   private static String encodeQ(String prefix, String text, Usage usage, int usedCharacters, Charset charset, byte[] bytes)
/* 304:    */   {
/* 305:523 */     int encodedLength = qEncodedLength(bytes, usage);
/* 306:    */     
/* 307:525 */     int totalLength = prefix.length() + encodedLength + "?=".length();
/* 308:527 */     if (totalLength <= 75 - usedCharacters) {
/* 309:528 */       return prefix + encodeQ(bytes, usage) + "?=";
/* 310:    */     }
/* 311:530 */     String part1 = text.substring(0, text.length() / 2);
/* 312:531 */     byte[] bytes1 = encode(part1, charset);
/* 313:532 */     String word1 = encodeQ(prefix, part1, usage, usedCharacters, charset, bytes1);
/* 314:    */     
/* 315:    */ 
/* 316:535 */     String part2 = text.substring(text.length() / 2);
/* 317:536 */     byte[] bytes2 = encode(part2, charset);
/* 318:537 */     String word2 = encodeQ(prefix, part2, usage, 0, charset, bytes2);
/* 319:    */     
/* 320:539 */     return word1 + " " + word2;
/* 321:    */   }
/* 322:    */   
/* 323:    */   private static int qEncodedLength(byte[] bytes, Usage usage)
/* 324:    */   {
/* 325:544 */     BitSet qChars = usage == Usage.TEXT_TOKEN ? Q_REGULAR_CHARS : Q_RESTRICTED_CHARS;
/* 326:    */     
/* 327:    */ 
/* 328:547 */     int count = 0;
/* 329:549 */     for (int idx = 0; idx < bytes.length; idx++)
/* 330:    */     {
/* 331:550 */       int v = bytes[idx] & 0xFF;
/* 332:551 */       if (v == 32) {
/* 333:552 */         count++;
/* 334:553 */       } else if (!qChars.get(v)) {
/* 335:554 */         count += 3;
/* 336:    */       } else {
/* 337:556 */         count++;
/* 338:    */       }
/* 339:    */     }
/* 340:560 */     return count;
/* 341:    */   }
/* 342:    */   
/* 343:    */   private static byte[] encode(String text, Charset charset)
/* 344:    */   {
/* 345:564 */     ByteBuffer buffer = charset.encode(text);
/* 346:565 */     byte[] bytes = new byte[buffer.limit()];
/* 347:566 */     buffer.get(bytes);
/* 348:567 */     return bytes;
/* 349:    */   }
/* 350:    */   
/* 351:    */   private static Charset determineCharset(String text)
/* 352:    */   {
/* 353:573 */     boolean ascii = true;
/* 354:574 */     int len = text.length();
/* 355:575 */     for (int index = 0; index < len; index++)
/* 356:    */     {
/* 357:576 */       char ch = text.charAt(index);
/* 358:577 */       if (ch > 'ÿ') {
/* 359:578 */         return CharsetUtil.UTF_8;
/* 360:    */       }
/* 361:580 */       if (ch > '') {
/* 362:581 */         ascii = false;
/* 363:    */       }
/* 364:    */     }
/* 365:584 */     return ascii ? CharsetUtil.US_ASCII : CharsetUtil.ISO_8859_1;
/* 366:    */   }
/* 367:    */   
/* 368:    */   private static Encoding determineEncoding(byte[] bytes, Usage usage)
/* 369:    */   {
/* 370:588 */     if (bytes.length == 0) {
/* 371:589 */       return Encoding.Q;
/* 372:    */     }
/* 373:591 */     BitSet qChars = usage == Usage.TEXT_TOKEN ? Q_REGULAR_CHARS : Q_RESTRICTED_CHARS;
/* 374:    */     
/* 375:    */ 
/* 376:594 */     int qEncoded = 0;
/* 377:595 */     for (int i = 0; i < bytes.length; i++)
/* 378:    */     {
/* 379:596 */       int v = bytes[i] & 0xFF;
/* 380:597 */       if ((v != 32) && (!qChars.get(v))) {
/* 381:598 */         qEncoded++;
/* 382:    */       }
/* 383:    */     }
/* 384:602 */     int percentage = qEncoded * 100 / bytes.length;
/* 385:603 */     return percentage > 30 ? Encoding.B : Encoding.Q;
/* 386:    */   }
/* 387:    */   
/* 388:    */   private static char hexDigit(int i)
/* 389:    */   {
/* 390:607 */     return i < 10 ? (char)(i + 48) : (char)(i - 10 + 65);
/* 391:    */   }
/* 392:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.EncoderUtil
 * JD-Core Version:    0.7.0.1
 */