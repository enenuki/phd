/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import org.apache.xml.utils.FastStringBuffer;
/*   4:    */ import org.apache.xml.utils.XMLCharacterRecognizer;
/*   5:    */ import org.apache.xml.utils.XMLString;
/*   6:    */ import org.apache.xml.utils.XMLStringFactory;
/*   7:    */ import org.apache.xpath.res.XPATHMessages;
/*   8:    */ import org.xml.sax.ContentHandler;
/*   9:    */ import org.xml.sax.SAXException;
/*  10:    */ import org.xml.sax.ext.LexicalHandler;
/*  11:    */ 
/*  12:    */ public class XStringForFSB
/*  13:    */   extends XString
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = -1533039186550674548L;
/*  16:    */   int m_start;
/*  17:    */   int m_length;
/*  18: 44 */   protected String m_strCache = null;
/*  19: 47 */   protected int m_hash = 0;
/*  20:    */   
/*  21:    */   public XStringForFSB(FastStringBuffer val, int start, int length)
/*  22:    */   {
/*  23: 59 */     super(val);
/*  24:    */     
/*  25: 61 */     this.m_start = start;
/*  26: 62 */     this.m_length = length;
/*  27: 64 */     if (null == val) {
/*  28: 65 */       throw new IllegalArgumentException(XPATHMessages.createXPATHMessage("ER_FASTSTRINGBUFFER_CANNOT_BE_NULL", null));
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   private XStringForFSB(String val)
/*  33:    */   {
/*  34: 77 */     super(val);
/*  35:    */     
/*  36: 79 */     throw new IllegalArgumentException(XPATHMessages.createXPATHMessage("ER_FSB_CANNOT_TAKE_STRING", null));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public FastStringBuffer fsb()
/*  40:    */   {
/*  41: 90 */     return (FastStringBuffer)this.m_obj;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void appendToFsb(FastStringBuffer fsb)
/*  45:    */   {
/*  46:101 */     fsb.append(str());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasString()
/*  50:    */   {
/*  51:111 */     return null != this.m_strCache;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object object()
/*  55:    */   {
/*  56:128 */     return str();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String str()
/*  60:    */   {
/*  61:139 */     if (null == this.m_strCache) {
/*  62:141 */       this.m_strCache = fsb().getString(this.m_start, this.m_length);
/*  63:    */     }
/*  64:167 */     return this.m_strCache;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void dispatchCharactersEvents(ContentHandler ch)
/*  68:    */     throws SAXException
/*  69:    */   {
/*  70:184 */     fsb().sendSAXcharacters(ch, this.m_start, this.m_length);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void dispatchAsComment(LexicalHandler lh)
/*  74:    */     throws SAXException
/*  75:    */   {
/*  76:199 */     fsb().sendSAXComment(lh, this.m_start, this.m_length);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int length()
/*  80:    */   {
/*  81:210 */     return this.m_length;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public char charAt(int index)
/*  85:    */   {
/*  86:228 */     return fsb().charAt(this.m_start + index);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
/*  90:    */   {
/*  91:257 */     int n = srcEnd - srcBegin;
/*  92:259 */     if (n > this.m_length) {
/*  93:260 */       n = this.m_length;
/*  94:    */     }
/*  95:262 */     if (n > dst.length - dstBegin) {
/*  96:263 */       n = dst.length - dstBegin;
/*  97:    */     }
/*  98:265 */     int end = srcBegin + this.m_start + n;
/*  99:266 */     int d = dstBegin;
/* 100:267 */     FastStringBuffer fsb = fsb();
/* 101:269 */     for (int i = srcBegin + this.m_start; i < end; i++) {
/* 102:271 */       dst[(d++)] = fsb.charAt(i);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean equals(XMLString obj2)
/* 107:    */   {
/* 108:292 */     if (this == obj2) {
/* 109:294 */       return true;
/* 110:    */     }
/* 111:297 */     int n = this.m_length;
/* 112:299 */     if (n == obj2.length())
/* 113:    */     {
/* 114:301 */       FastStringBuffer fsb = fsb();
/* 115:302 */       int i = this.m_start;
/* 116:303 */       int j = 0;
/* 117:305 */       while (n-- != 0)
/* 118:    */       {
/* 119:307 */         if (fsb.charAt(i) != obj2.charAt(j)) {
/* 120:309 */           return false;
/* 121:    */         }
/* 122:312 */         i++;
/* 123:313 */         j++;
/* 124:    */       }
/* 125:316 */       return true;
/* 126:    */     }
/* 127:319 */     return false;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean equals(XObject obj2)
/* 131:    */   {
/* 132:334 */     if (this == obj2) {
/* 133:336 */       return true;
/* 134:    */     }
/* 135:338 */     if (obj2.getType() == 2) {
/* 136:339 */       return obj2.equals(this);
/* 137:    */     }
/* 138:341 */     String str = obj2.str();
/* 139:342 */     int n = this.m_length;
/* 140:344 */     if (n == str.length())
/* 141:    */     {
/* 142:346 */       FastStringBuffer fsb = fsb();
/* 143:347 */       int i = this.m_start;
/* 144:348 */       int j = 0;
/* 145:350 */       while (n-- != 0)
/* 146:    */       {
/* 147:352 */         if (fsb.charAt(i) != str.charAt(j)) {
/* 148:354 */           return false;
/* 149:    */         }
/* 150:357 */         i++;
/* 151:358 */         j++;
/* 152:    */       }
/* 153:361 */       return true;
/* 154:    */     }
/* 155:364 */     return false;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean equals(String anotherString)
/* 159:    */   {
/* 160:379 */     int n = this.m_length;
/* 161:381 */     if (n == anotherString.length())
/* 162:    */     {
/* 163:383 */       FastStringBuffer fsb = fsb();
/* 164:384 */       int i = this.m_start;
/* 165:385 */       int j = 0;
/* 166:387 */       while (n-- != 0)
/* 167:    */       {
/* 168:389 */         if (fsb.charAt(i) != anotherString.charAt(j)) {
/* 169:391 */           return false;
/* 170:    */         }
/* 171:394 */         i++;
/* 172:395 */         j++;
/* 173:    */       }
/* 174:398 */       return true;
/* 175:    */     }
/* 176:401 */     return false;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean equals(Object obj2)
/* 180:    */   {
/* 181:421 */     if (null == obj2) {
/* 182:422 */       return false;
/* 183:    */     }
/* 184:424 */     if ((obj2 instanceof XNumber)) {
/* 185:425 */       return obj2.equals(this);
/* 186:    */     }
/* 187:430 */     if ((obj2 instanceof XNodeSet)) {
/* 188:431 */       return obj2.equals(this);
/* 189:    */     }
/* 190:432 */     if ((obj2 instanceof XStringForFSB)) {
/* 191:433 */       return equals((XMLString)obj2);
/* 192:    */     }
/* 193:435 */     return equals(obj2.toString());
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean equalsIgnoreCase(String anotherString)
/* 197:    */   {
/* 198:455 */     return this.m_length == anotherString.length() ? str().equalsIgnoreCase(anotherString) : false;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int compareTo(XMLString xstr)
/* 202:    */   {
/* 203:475 */     int len1 = this.m_length;
/* 204:476 */     int len2 = xstr.length();
/* 205:477 */     int n = Math.min(len1, len2);
/* 206:478 */     FastStringBuffer fsb = fsb();
/* 207:479 */     int i = this.m_start;
/* 208:480 */     int j = 0;
/* 209:482 */     while (n-- != 0)
/* 210:    */     {
/* 211:484 */       char c1 = fsb.charAt(i);
/* 212:485 */       char c2 = xstr.charAt(j);
/* 213:487 */       if (c1 != c2) {
/* 214:489 */         return c1 - c2;
/* 215:    */       }
/* 216:492 */       i++;
/* 217:493 */       j++;
/* 218:    */     }
/* 219:496 */     return len1 - len2;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int compareToIgnoreCase(XMLString xstr)
/* 223:    */   {
/* 224:521 */     int len1 = this.m_length;
/* 225:522 */     int len2 = xstr.length();
/* 226:523 */     int n = Math.min(len1, len2);
/* 227:524 */     FastStringBuffer fsb = fsb();
/* 228:525 */     int i = this.m_start;
/* 229:526 */     int j = 0;
/* 230:528 */     while (n-- != 0)
/* 231:    */     {
/* 232:530 */       char c1 = Character.toLowerCase(fsb.charAt(i));
/* 233:531 */       char c2 = Character.toLowerCase(xstr.charAt(j));
/* 234:533 */       if (c1 != c2) {
/* 235:535 */         return c1 - c2;
/* 236:    */       }
/* 237:538 */       i++;
/* 238:539 */       j++;
/* 239:    */     }
/* 240:542 */     return len1 - len2;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public int hashCode()
/* 244:    */   {
/* 245:586 */     return super.hashCode();
/* 246:    */   }
/* 247:    */   
/* 248:    */   public boolean startsWith(XMLString prefix, int toffset)
/* 249:    */   {
/* 250:611 */     FastStringBuffer fsb = fsb();
/* 251:612 */     int to = this.m_start + toffset;
/* 252:613 */     int tlim = this.m_start + this.m_length;
/* 253:614 */     int po = 0;
/* 254:615 */     int pc = prefix.length();
/* 255:618 */     if ((toffset < 0) || (toffset > this.m_length - pc)) {
/* 256:620 */       return false;
/* 257:    */     }
/* 258:    */     do
/* 259:    */     {
/* 260:625 */       if (fsb.charAt(to) != prefix.charAt(po)) {
/* 261:627 */         return false;
/* 262:    */       }
/* 263:630 */       to++;
/* 264:631 */       po++;pc--;
/* 265:623 */     } while (pc >= 0);
/* 266:634 */     return true;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public boolean startsWith(XMLString prefix)
/* 270:    */   {
/* 271:654 */     return startsWith(prefix, 0);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public int indexOf(int ch)
/* 275:    */   {
/* 276:676 */     return indexOf(ch, 0);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public int indexOf(int ch, int fromIndex)
/* 280:    */   {
/* 281:710 */     int max = this.m_start + this.m_length;
/* 282:711 */     FastStringBuffer fsb = fsb();
/* 283:713 */     if (fromIndex < 0) {
/* 284:715 */       fromIndex = 0;
/* 285:717 */     } else if (fromIndex >= this.m_length) {
/* 286:721 */       return -1;
/* 287:    */     }
/* 288:724 */     for (int i = this.m_start + fromIndex; i < max; i++) {
/* 289:726 */       if (fsb.charAt(i) == ch) {
/* 290:728 */         return i - this.m_start;
/* 291:    */       }
/* 292:    */     }
/* 293:732 */     return -1;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public XMLString substring(int beginIndex)
/* 297:    */   {
/* 298:755 */     int len = this.m_length - beginIndex;
/* 299:757 */     if (len <= 0) {
/* 300:758 */       return XString.EMPTYSTRING;
/* 301:    */     }
/* 302:761 */     int start = this.m_start + beginIndex;
/* 303:    */     
/* 304:763 */     return new XStringForFSB(fsb(), start, len);
/* 305:    */   }
/* 306:    */   
/* 307:    */   public XMLString substring(int beginIndex, int endIndex)
/* 308:    */   {
/* 309:786 */     int len = endIndex - beginIndex;
/* 310:788 */     if (len > this.m_length) {
/* 311:789 */       len = this.m_length;
/* 312:    */     }
/* 313:791 */     if (len <= 0) {
/* 314:792 */       return XString.EMPTYSTRING;
/* 315:    */     }
/* 316:795 */     int start = this.m_start + beginIndex;
/* 317:    */     
/* 318:797 */     return new XStringForFSB(fsb(), start, len);
/* 319:    */   }
/* 320:    */   
/* 321:    */   public XMLString concat(String str)
/* 322:    */   {
/* 323:815 */     return new XString(str().concat(str));
/* 324:    */   }
/* 325:    */   
/* 326:    */   public XMLString trim()
/* 327:    */   {
/* 328:825 */     return fixWhiteSpace(true, true, false);
/* 329:    */   }
/* 330:    */   
/* 331:    */   private static boolean isSpace(char ch)
/* 332:    */   {
/* 333:837 */     return XMLCharacterRecognizer.isWhiteSpace(ch);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public XMLString fixWhiteSpace(boolean trimHead, boolean trimTail, boolean doublePunctuationSpaces)
/* 337:    */   {
/* 338:858 */     int end = this.m_length + this.m_start;
/* 339:859 */     char[] buf = new char[this.m_length];
/* 340:860 */     FastStringBuffer fsb = fsb();
/* 341:861 */     boolean edit = false;
/* 342:    */     
/* 343:    */ 
/* 344:864 */     int d = 0;
/* 345:865 */     boolean pres = false;
/* 346:867 */     for (int s = this.m_start; s < end; s++)
/* 347:    */     {
/* 348:869 */       char c = fsb.charAt(s);
/* 349:871 */       if (isSpace(c))
/* 350:    */       {
/* 351:873 */         if (!pres)
/* 352:    */         {
/* 353:875 */           if (' ' != c) {
/* 354:877 */             edit = true;
/* 355:    */           }
/* 356:880 */           buf[(d++)] = ' ';
/* 357:882 */           if ((doublePunctuationSpaces) && (d != 0))
/* 358:    */           {
/* 359:884 */             char prevChar = buf[(d - 1)];
/* 360:886 */             if ((prevChar != '.') && (prevChar != '!') && (prevChar != '?')) {
/* 361:889 */               pres = true;
/* 362:    */             }
/* 363:    */           }
/* 364:    */           else
/* 365:    */           {
/* 366:894 */             pres = true;
/* 367:    */           }
/* 368:    */         }
/* 369:    */         else
/* 370:    */         {
/* 371:899 */           edit = true;
/* 372:900 */           pres = true;
/* 373:    */         }
/* 374:    */       }
/* 375:    */       else
/* 376:    */       {
/* 377:905 */         buf[(d++)] = c;
/* 378:906 */         pres = false;
/* 379:    */       }
/* 380:    */     }
/* 381:910 */     if ((trimTail) && (1 <= d) && (' ' == buf[(d - 1)]))
/* 382:    */     {
/* 383:912 */       edit = true;
/* 384:    */       
/* 385:914 */       d--;
/* 386:    */     }
/* 387:917 */     int start = 0;
/* 388:919 */     if ((trimHead) && (0 < d) && (' ' == buf[0]))
/* 389:    */     {
/* 390:921 */       edit = true;
/* 391:    */       
/* 392:923 */       start++;
/* 393:    */     }
/* 394:926 */     XMLStringFactory xsf = XMLStringFactoryImpl.getFactory();
/* 395:    */     
/* 396:928 */     return edit ? xsf.newstr(buf, start, d - start) : this;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public double toDouble()
/* 400:    */   {
/* 401:949 */     if (this.m_length == 0) {
/* 402:950 */       return (0.0D / 0.0D);
/* 403:    */     }
/* 404:953 */     String valueString = fsb().getString(this.m_start, this.m_length);
/* 405:962 */     for (int i = 0; i < this.m_length; i++) {
/* 406:963 */       if (!XMLCharacterRecognizer.isWhiteSpace(valueString.charAt(i))) {
/* 407:    */         break;
/* 408:    */       }
/* 409:    */     }
/* 410:965 */     if (i == this.m_length) {
/* 411:965 */       return (0.0D / 0.0D);
/* 412:    */     }
/* 413:966 */     if (valueString.charAt(i) == '-') {
/* 414:967 */       i++;
/* 415:    */     }
/* 416:968 */     for (; i < this.m_length; i++)
/* 417:    */     {
/* 418:969 */       char c = valueString.charAt(i);
/* 419:970 */       if ((c != '.') && ((c < '0') || (c > '9'))) {
/* 420:    */         break;
/* 421:    */       }
/* 422:    */     }
/* 423:973 */     for (; i < this.m_length; i++) {
/* 424:974 */       if (!XMLCharacterRecognizer.isWhiteSpace(valueString.charAt(i))) {
/* 425:    */         break;
/* 426:    */       }
/* 427:    */     }
/* 428:976 */     if (i != this.m_length) {
/* 429:977 */       return (0.0D / 0.0D);
/* 430:    */     }
/* 431:    */     try
/* 432:    */     {
/* 433:980 */       return new Double(valueString).doubleValue();
/* 434:    */     }
/* 435:    */     catch (NumberFormatException nfe) {}
/* 436:983 */     return (0.0D / 0.0D);
/* 437:    */   }
/* 438:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XStringForFSB
 * JD-Core Version:    0.7.0.1
 */