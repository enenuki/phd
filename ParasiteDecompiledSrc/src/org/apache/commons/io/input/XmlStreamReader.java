/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.BufferedReader;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.InputStreamReader;
/*  10:    */ import java.io.Reader;
/*  11:    */ import java.io.StringReader;
/*  12:    */ import java.net.HttpURLConnection;
/*  13:    */ import java.net.URL;
/*  14:    */ import java.net.URLConnection;
/*  15:    */ import java.text.MessageFormat;
/*  16:    */ import java.util.regex.Matcher;
/*  17:    */ import java.util.regex.Pattern;
/*  18:    */ import org.apache.commons.io.ByteOrderMark;
/*  19:    */ 
/*  20:    */ public class XmlStreamReader
/*  21:    */   extends Reader
/*  22:    */ {
/*  23:    */   private static final int BUFFER_SIZE = 4096;
/*  24:    */   private static final String UTF_8 = "UTF-8";
/*  25:    */   private static final String US_ASCII = "US-ASCII";
/*  26:    */   private static final String UTF_16BE = "UTF-16BE";
/*  27:    */   private static final String UTF_16LE = "UTF-16LE";
/*  28:    */   private static final String UTF_16 = "UTF-16";
/*  29:    */   private static final String EBCDIC = "CP1047";
/*  30: 81 */   private static final ByteOrderMark[] BOMS = { ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE };
/*  31: 86 */   private static final ByteOrderMark[] XML_GUESS_BYTES = { new ByteOrderMark("UTF-8", new int[] { 60, 63, 120, 109 }), new ByteOrderMark("UTF-16BE", new int[] { 0, 60, 0, 63 }), new ByteOrderMark("UTF-16LE", new int[] { 60, 0, 63, 0 }), new ByteOrderMark("CP1047", new int[] { 76, 111, 167, 148 }) };
/*  32:    */   private final Reader reader;
/*  33:    */   private final String encoding;
/*  34:    */   private final String defaultEncoding;
/*  35:    */   
/*  36:    */   public String getDefaultEncoding()
/*  37:    */   {
/*  38:109 */     return this.defaultEncoding;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public XmlStreamReader(File file)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:125 */     this(new FileInputStream(file));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public XmlStreamReader(InputStream is)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:140 */     this(is, true);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public XmlStreamReader(InputStream is, boolean lenient)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56:171 */     this(is, lenient, null);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public XmlStreamReader(InputStream is, boolean lenient, String defaultEncoding)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62:203 */     this.defaultEncoding = defaultEncoding;
/*  63:204 */     BOMInputStream bom = new BOMInputStream(new BufferedInputStream(is, 4096), false, BOMS);
/*  64:205 */     BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
/*  65:206 */     this.encoding = doRawStream(bom, pis, lenient);
/*  66:207 */     this.reader = new InputStreamReader(pis, this.encoding);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public XmlStreamReader(URL url)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:228 */     this(url.openConnection(), null);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public XmlStreamReader(URLConnection conn, String defaultEncoding)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:251 */     this.defaultEncoding = defaultEncoding;
/*  79:252 */     boolean lenient = true;
/*  80:253 */     String contentType = conn.getContentType();
/*  81:254 */     InputStream is = conn.getInputStream();
/*  82:255 */     BOMInputStream bom = new BOMInputStream(new BufferedInputStream(is, 4096), false, BOMS);
/*  83:256 */     BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
/*  84:257 */     if (((conn instanceof HttpURLConnection)) || (contentType != null)) {
/*  85:258 */       this.encoding = doHttpStream(bom, pis, contentType, lenient);
/*  86:    */     } else {
/*  87:260 */       this.encoding = doRawStream(bom, pis, lenient);
/*  88:    */     }
/*  89:262 */     this.reader = new InputStreamReader(pis, this.encoding);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public XmlStreamReader(InputStream is, String httpContentType)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:284 */     this(is, httpContentType, true);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public XmlStreamReader(InputStream is, String httpContentType, boolean lenient, String defaultEncoding)
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:323 */     this.defaultEncoding = defaultEncoding;
/* 102:324 */     BOMInputStream bom = new BOMInputStream(new BufferedInputStream(is, 4096), false, BOMS);
/* 103:325 */     BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
/* 104:326 */     this.encoding = doHttpStream(bom, pis, httpContentType, lenient);
/* 105:327 */     this.reader = new InputStreamReader(pis, this.encoding);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public XmlStreamReader(InputStream is, String httpContentType, boolean lenient)
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:365 */     this(is, httpContentType, lenient, null);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getEncoding()
/* 115:    */   {
/* 116:374 */     return this.encoding;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int read(char[] buf, int offset, int len)
/* 120:    */     throws IOException
/* 121:    */   {
/* 122:387 */     return this.reader.read(buf, offset, len);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void close()
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:397 */     this.reader.close();
/* 129:    */   }
/* 130:    */   
/* 131:    */   private String doRawStream(BOMInputStream bom, BOMInputStream pis, boolean lenient)
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:412 */     String bomEnc = bom.getBOMCharsetName();
/* 135:413 */     String xmlGuessEnc = pis.getBOMCharsetName();
/* 136:414 */     String xmlEnc = getXmlProlog(pis, xmlGuessEnc);
/* 137:    */     try
/* 138:    */     {
/* 139:416 */       return calculateRawEncoding(bomEnc, xmlGuessEnc, xmlEnc);
/* 140:    */     }
/* 141:    */     catch (XmlStreamReaderException ex)
/* 142:    */     {
/* 143:418 */       if (lenient) {
/* 144:419 */         return doLenientDetection(null, ex);
/* 145:    */       }
/* 146:421 */       throw ex;
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   private String doHttpStream(BOMInputStream bom, BOMInputStream pis, String httpContentType, boolean lenient)
/* 151:    */     throws IOException
/* 152:    */   {
/* 153:439 */     String bomEnc = bom.getBOMCharsetName();
/* 154:440 */     String xmlGuessEnc = pis.getBOMCharsetName();
/* 155:441 */     String xmlEnc = getXmlProlog(pis, xmlGuessEnc);
/* 156:    */     try
/* 157:    */     {
/* 158:443 */       return calculateHttpEncoding(httpContentType, bomEnc, xmlGuessEnc, xmlEnc, lenient);
/* 159:    */     }
/* 160:    */     catch (XmlStreamReaderException ex)
/* 161:    */     {
/* 162:446 */       if (lenient) {
/* 163:447 */         return doLenientDetection(httpContentType, ex);
/* 164:    */       }
/* 165:449 */       throw ex;
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   private String doLenientDetection(String httpContentType, XmlStreamReaderException ex)
/* 170:    */     throws IOException
/* 171:    */   {
/* 172:465 */     if ((httpContentType != null) && (httpContentType.startsWith("text/html")))
/* 173:    */     {
/* 174:466 */       httpContentType = httpContentType.substring("text/html".length());
/* 175:467 */       httpContentType = "text/xml" + httpContentType;
/* 176:    */       try
/* 177:    */       {
/* 178:469 */         return calculateHttpEncoding(httpContentType, ex.getBomEncoding(), ex.getXmlGuessEncoding(), ex.getXmlEncoding(), true);
/* 179:    */       }
/* 180:    */       catch (XmlStreamReaderException ex2)
/* 181:    */       {
/* 182:472 */         ex = ex2;
/* 183:    */       }
/* 184:    */     }
/* 185:475 */     String encoding = ex.getXmlEncoding();
/* 186:476 */     if (encoding == null) {
/* 187:477 */       encoding = ex.getContentTypeEncoding();
/* 188:    */     }
/* 189:479 */     if (encoding == null) {
/* 190:480 */       encoding = this.defaultEncoding == null ? "UTF-8" : this.defaultEncoding;
/* 191:    */     }
/* 192:482 */     return encoding;
/* 193:    */   }
/* 194:    */   
/* 195:    */   String calculateRawEncoding(String bomEnc, String xmlGuessEnc, String xmlEnc)
/* 196:    */     throws IOException
/* 197:    */   {
/* 198:498 */     if (bomEnc == null)
/* 199:    */     {
/* 200:499 */       if ((xmlGuessEnc == null) || (xmlEnc == null)) {
/* 201:500 */         return this.defaultEncoding == null ? "UTF-8" : this.defaultEncoding;
/* 202:    */       }
/* 203:502 */       if ((xmlEnc.equals("UTF-16")) && ((xmlGuessEnc.equals("UTF-16BE")) || (xmlGuessEnc.equals("UTF-16LE")))) {
/* 204:504 */         return xmlGuessEnc;
/* 205:    */       }
/* 206:506 */       return xmlEnc;
/* 207:    */     }
/* 208:510 */     if (bomEnc.equals("UTF-8"))
/* 209:    */     {
/* 210:511 */       if ((xmlGuessEnc != null) && (!xmlGuessEnc.equals("UTF-8")))
/* 211:    */       {
/* 212:512 */         String msg = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { bomEnc, xmlGuessEnc, xmlEnc });
/* 213:513 */         throw new XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
/* 214:    */       }
/* 215:515 */       if ((xmlEnc != null) && (!xmlEnc.equals("UTF-8")))
/* 216:    */       {
/* 217:516 */         String msg = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { bomEnc, xmlGuessEnc, xmlEnc });
/* 218:517 */         throw new XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
/* 219:    */       }
/* 220:519 */       return bomEnc;
/* 221:    */     }
/* 222:523 */     if ((bomEnc.equals("UTF-16BE")) || (bomEnc.equals("UTF-16LE")))
/* 223:    */     {
/* 224:524 */       if ((xmlGuessEnc != null) && (!xmlGuessEnc.equals(bomEnc)))
/* 225:    */       {
/* 226:525 */         String msg = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { bomEnc, xmlGuessEnc, xmlEnc });
/* 227:526 */         throw new XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
/* 228:    */       }
/* 229:528 */       if ((xmlEnc != null) && (!xmlEnc.equals("UTF-16")) && (!xmlEnc.equals(bomEnc)))
/* 230:    */       {
/* 231:529 */         String msg = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { bomEnc, xmlGuessEnc, xmlEnc });
/* 232:530 */         throw new XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
/* 233:    */       }
/* 234:532 */       return bomEnc;
/* 235:    */     }
/* 236:536 */     String msg = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM", new Object[] { bomEnc, xmlGuessEnc, xmlEnc });
/* 237:537 */     throw new XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
/* 238:    */   }
/* 239:    */   
/* 240:    */   String calculateHttpEncoding(String httpContentType, String bomEnc, String xmlGuessEnc, String xmlEnc, boolean lenient)
/* 241:    */     throws IOException
/* 242:    */   {
/* 243:558 */     if ((lenient) && (xmlEnc != null)) {
/* 244:559 */       return xmlEnc;
/* 245:    */     }
/* 246:563 */     String cTMime = getContentTypeMime(httpContentType);
/* 247:564 */     String cTEnc = getContentTypeEncoding(httpContentType);
/* 248:565 */     boolean appXml = isAppXml(cTMime);
/* 249:566 */     boolean textXml = isTextXml(cTMime);
/* 250:569 */     if ((!appXml) && (!textXml))
/* 251:    */     {
/* 252:570 */       String msg = MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME", new Object[] { cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc });
/* 253:571 */       throw new XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
/* 254:    */     }
/* 255:575 */     if (cTEnc == null)
/* 256:    */     {
/* 257:576 */       if (appXml) {
/* 258:577 */         return calculateRawEncoding(bomEnc, xmlGuessEnc, xmlEnc);
/* 259:    */       }
/* 260:579 */       return this.defaultEncoding == null ? "US-ASCII" : this.defaultEncoding;
/* 261:    */     }
/* 262:584 */     if ((cTEnc.equals("UTF-16BE")) || (cTEnc.equals("UTF-16LE")))
/* 263:    */     {
/* 264:585 */       if (bomEnc != null)
/* 265:    */       {
/* 266:586 */         String msg = MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL", new Object[] { cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc });
/* 267:587 */         throw new XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
/* 268:    */       }
/* 269:589 */       return cTEnc;
/* 270:    */     }
/* 271:593 */     if (cTEnc.equals("UTF-16"))
/* 272:    */     {
/* 273:594 */       if ((bomEnc != null) && (bomEnc.startsWith("UTF-16"))) {
/* 274:595 */         return bomEnc;
/* 275:    */       }
/* 276:597 */       String msg = MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch", new Object[] { cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc });
/* 277:598 */       throw new XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
/* 278:    */     }
/* 279:601 */     return cTEnc;
/* 280:    */   }
/* 281:    */   
/* 282:    */   static String getContentTypeMime(String httpContentType)
/* 283:    */   {
/* 284:611 */     String mime = null;
/* 285:612 */     if (httpContentType != null)
/* 286:    */     {
/* 287:613 */       int i = httpContentType.indexOf(";");
/* 288:614 */       if (i >= 0) {
/* 289:615 */         mime = httpContentType.substring(0, i);
/* 290:    */       } else {
/* 291:617 */         mime = httpContentType;
/* 292:    */       }
/* 293:619 */       mime = mime.trim();
/* 294:    */     }
/* 295:621 */     return mime;
/* 296:    */   }
/* 297:    */   
/* 298:624 */   private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=[\"']?([.[^; \"']]*)[\"']?");
/* 299:    */   
/* 300:    */   static String getContentTypeEncoding(String httpContentType)
/* 301:    */   {
/* 302:635 */     String encoding = null;
/* 303:636 */     if (httpContentType != null)
/* 304:    */     {
/* 305:637 */       int i = httpContentType.indexOf(";");
/* 306:638 */       if (i > -1)
/* 307:    */       {
/* 308:639 */         String postMime = httpContentType.substring(i + 1);
/* 309:640 */         Matcher m = CHARSET_PATTERN.matcher(postMime);
/* 310:641 */         encoding = m.find() ? m.group(1) : null;
/* 311:642 */         encoding = encoding != null ? encoding.toUpperCase() : null;
/* 312:    */       }
/* 313:    */     }
/* 314:645 */     return encoding;
/* 315:    */   }
/* 316:    */   
/* 317:648 */   public static final Pattern ENCODING_PATTERN = Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:'.[^']*'))", 8);
/* 318:    */   private static final String RAW_EX_1 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch";
/* 319:    */   private static final String RAW_EX_2 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM";
/* 320:    */   private static final String HTTP_EX_1 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL";
/* 321:    */   private static final String HTTP_EX_2 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch";
/* 322:    */   private static final String HTTP_EX_3 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME";
/* 323:    */   
/* 324:    */   private static String getXmlProlog(InputStream is, String guessedEnc)
/* 325:    */     throws IOException
/* 326:    */   {
/* 327:662 */     String encoding = null;
/* 328:663 */     if (guessedEnc != null)
/* 329:    */     {
/* 330:664 */       byte[] bytes = new byte[4096];
/* 331:665 */       is.mark(4096);
/* 332:666 */       int offset = 0;
/* 333:667 */       int max = 4096;
/* 334:668 */       int c = is.read(bytes, offset, max);
/* 335:669 */       int firstGT = -1;
/* 336:670 */       String xmlProlog = null;
/* 337:671 */       while ((c != -1) && (firstGT == -1) && (offset < 4096))
/* 338:    */       {
/* 339:672 */         offset += c;
/* 340:673 */         max -= c;
/* 341:674 */         c = is.read(bytes, offset, max);
/* 342:675 */         xmlProlog = new String(bytes, 0, offset, guessedEnc);
/* 343:676 */         firstGT = xmlProlog.indexOf('>');
/* 344:    */       }
/* 345:678 */       if (firstGT == -1)
/* 346:    */       {
/* 347:679 */         if (c == -1) {
/* 348:680 */           throw new IOException("Unexpected end of XML stream");
/* 349:    */         }
/* 350:682 */         throw new IOException("XML prolog or ROOT element not found on first " + offset + " bytes");
/* 351:    */       }
/* 352:687 */       int bytesRead = offset;
/* 353:688 */       if (bytesRead > 0)
/* 354:    */       {
/* 355:689 */         is.reset();
/* 356:690 */         BufferedReader bReader = new BufferedReader(new StringReader(xmlProlog.substring(0, firstGT + 1)));
/* 357:    */         
/* 358:692 */         StringBuffer prolog = new StringBuffer();
/* 359:693 */         String line = bReader.readLine();
/* 360:694 */         while (line != null)
/* 361:    */         {
/* 362:695 */           prolog.append(line);
/* 363:696 */           line = bReader.readLine();
/* 364:    */         }
/* 365:698 */         Matcher m = ENCODING_PATTERN.matcher(prolog);
/* 366:699 */         if (m.find())
/* 367:    */         {
/* 368:700 */           encoding = m.group(1).toUpperCase();
/* 369:701 */           encoding = encoding.substring(1, encoding.length() - 1);
/* 370:    */         }
/* 371:    */       }
/* 372:    */     }
/* 373:705 */     return encoding;
/* 374:    */   }
/* 375:    */   
/* 376:    */   static boolean isAppXml(String mime)
/* 377:    */   {
/* 378:716 */     return (mime != null) && ((mime.equals("application/xml")) || (mime.equals("application/xml-dtd")) || (mime.equals("application/xml-external-parsed-entity")) || ((mime.startsWith("application/")) && (mime.endsWith("+xml"))));
/* 379:    */   }
/* 380:    */   
/* 381:    */   static boolean isTextXml(String mime)
/* 382:    */   {
/* 383:731 */     return (mime != null) && ((mime.equals("text/xml")) || (mime.equals("text/xml-external-parsed-entity")) || ((mime.startsWith("text/")) && (mime.endsWith("+xml"))));
/* 384:    */   }
/* 385:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.XmlStreamReader
 * JD-Core Version:    0.7.0.1
 */