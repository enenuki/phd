/*   1:    */ package org.apache.xpath.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.utils.ObjectVector;
/*   6:    */ import org.apache.xml.utils.PrefixResolver;
/*   7:    */ 
/*   8:    */ class Lexer
/*   9:    */ {
/*  10:    */   private Compiler m_compiler;
/*  11:    */   PrefixResolver m_namespaceContext;
/*  12:    */   XPathParser m_processor;
/*  13:    */   static final int TARGETEXTRA = 10000;
/*  14: 64 */   private int[] m_patternMap = new int[100];
/*  15:    */   private int m_patternMapSize;
/*  16:    */   
/*  17:    */   Lexer(Compiler compiler, PrefixResolver resolver, XPathParser xpathProcessor)
/*  18:    */   {
/*  19: 84 */     this.m_compiler = compiler;
/*  20: 85 */     this.m_namespaceContext = resolver;
/*  21: 86 */     this.m_processor = xpathProcessor;
/*  22:    */   }
/*  23:    */   
/*  24:    */   void tokenize(String pat)
/*  25:    */     throws TransformerException
/*  26:    */   {
/*  27: 98 */     tokenize(pat, null);
/*  28:    */   }
/*  29:    */   
/*  30:    */   void tokenize(String pat, Vector targetStrings)
/*  31:    */     throws TransformerException
/*  32:    */   {
/*  33:113 */     this.m_compiler.m_currentPattern = pat;
/*  34:114 */     this.m_patternMapSize = 0;
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:120 */     int initTokQueueSize = (pat.length() < 500 ? pat.length() : 500) * 5;
/*  41:    */     
/*  42:122 */     this.m_compiler.m_opMap = new OpMapVector(initTokQueueSize, 2500, 1);
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46:126 */     int nChars = pat.length();
/*  47:127 */     int startSubstring = -1;
/*  48:128 */     int posOfNSSep = -1;
/*  49:129 */     boolean isStartOfPat = true;
/*  50:130 */     boolean isAttrName = false;
/*  51:131 */     boolean isNum = false;
/*  52:    */     
/*  53:    */ 
/*  54:    */ 
/*  55:135 */     int nesting = 0;
/*  56:138 */     for (int i = 0; i < nChars; i++)
/*  57:    */     {
/*  58:140 */       char c = pat.charAt(i);
/*  59:142 */       switch (c)
/*  60:    */       {
/*  61:    */       case '"': 
/*  62:146 */         if (startSubstring != -1)
/*  63:    */         {
/*  64:148 */           isNum = false;
/*  65:149 */           isStartOfPat = mapPatternElemPos(nesting, isStartOfPat, isAttrName);
/*  66:150 */           isAttrName = false;
/*  67:152 */           if (-1 != posOfNSSep) {
/*  68:154 */             posOfNSSep = mapNSTokens(pat, startSubstring, posOfNSSep, i);
/*  69:    */           } else {
/*  70:158 */             addToTokenQueue(pat.substring(startSubstring, i));
/*  71:    */           }
/*  72:    */         }
/*  73:162 */         startSubstring = i;
/*  74:164 */         for (i++; (i < nChars) && ((c = pat.charAt(i)) != '"'); i++) {}
/*  75:166 */         if ((c == '"') && (i < nChars))
/*  76:    */         {
/*  77:168 */           addToTokenQueue(pat.substring(startSubstring, i + 1));
/*  78:    */           
/*  79:170 */           startSubstring = -1;
/*  80:    */         }
/*  81:    */         else
/*  82:    */         {
/*  83:174 */           this.m_processor.error("ER_EXPECTED_DOUBLE_QUOTE", null);
/*  84:    */         }
/*  85:178 */         break;
/*  86:    */       case '\'': 
/*  87:180 */         if (startSubstring != -1)
/*  88:    */         {
/*  89:182 */           isNum = false;
/*  90:183 */           isStartOfPat = mapPatternElemPos(nesting, isStartOfPat, isAttrName);
/*  91:184 */           isAttrName = false;
/*  92:186 */           if (-1 != posOfNSSep) {
/*  93:188 */             posOfNSSep = mapNSTokens(pat, startSubstring, posOfNSSep, i);
/*  94:    */           } else {
/*  95:192 */             addToTokenQueue(pat.substring(startSubstring, i));
/*  96:    */           }
/*  97:    */         }
/*  98:196 */         startSubstring = i;
/*  99:198 */         for (i++; (i < nChars) && ((c = pat.charAt(i)) != '\''); i++) {}
/* 100:200 */         if ((c == '\'') && (i < nChars))
/* 101:    */         {
/* 102:202 */           addToTokenQueue(pat.substring(startSubstring, i + 1));
/* 103:    */           
/* 104:204 */           startSubstring = -1;
/* 105:    */         }
/* 106:    */         else
/* 107:    */         {
/* 108:208 */           this.m_processor.error("ER_EXPECTED_SINGLE_QUOTE", null);
/* 109:    */         }
/* 110:211 */         break;
/* 111:    */       case '\t': 
/* 112:    */       case '\n': 
/* 113:    */       case '\r': 
/* 114:    */       case ' ': 
/* 115:216 */         if (startSubstring == -1) {
/* 116:    */           continue;
/* 117:    */         }
/* 118:218 */         isNum = false;
/* 119:219 */         isStartOfPat = mapPatternElemPos(nesting, isStartOfPat, isAttrName);
/* 120:220 */         isAttrName = false;
/* 121:222 */         if (-1 != posOfNSSep) {
/* 122:224 */           posOfNSSep = mapNSTokens(pat, startSubstring, posOfNSSep, i);
/* 123:    */         } else {
/* 124:228 */           addToTokenQueue(pat.substring(startSubstring, i));
/* 125:    */         }
/* 126:231 */         startSubstring = -1; break;
/* 127:    */       case '@': 
/* 128:235 */         isAttrName = true;
/* 129:    */       case '-': 
/* 130:239 */         if ('-' == c)
/* 131:    */         {
/* 132:241 */           if ((!isNum) && (startSubstring != -1)) {
/* 133:    */             continue;
/* 134:    */           }
/* 135:246 */           isNum = false;
/* 136:    */         }
/* 137:    */       case '!': 
/* 138:    */       case '$': 
/* 139:    */       case '(': 
/* 140:    */       case ')': 
/* 141:    */       case '*': 
/* 142:    */       case '+': 
/* 143:    */       case ',': 
/* 144:    */       case '/': 
/* 145:    */       case '<': 
/* 146:    */       case '=': 
/* 147:    */       case '>': 
/* 148:    */       case '[': 
/* 149:    */       case '\\': 
/* 150:    */       case ']': 
/* 151:    */       case '^': 
/* 152:    */       case '|': 
/* 153:266 */         if (startSubstring != -1)
/* 154:    */         {
/* 155:268 */           isNum = false;
/* 156:269 */           isStartOfPat = mapPatternElemPos(nesting, isStartOfPat, isAttrName);
/* 157:270 */           isAttrName = false;
/* 158:272 */           if (-1 != posOfNSSep) {
/* 159:274 */             posOfNSSep = mapNSTokens(pat, startSubstring, posOfNSSep, i);
/* 160:    */           } else {
/* 161:278 */             addToTokenQueue(pat.substring(startSubstring, i));
/* 162:    */           }
/* 163:281 */           startSubstring = -1;
/* 164:    */         }
/* 165:283 */         else if (('/' == c) && (isStartOfPat))
/* 166:    */         {
/* 167:285 */           isStartOfPat = mapPatternElemPos(nesting, isStartOfPat, isAttrName);
/* 168:    */         }
/* 169:287 */         else if ('*' == c)
/* 170:    */         {
/* 171:289 */           isStartOfPat = mapPatternElemPos(nesting, isStartOfPat, isAttrName);
/* 172:290 */           isAttrName = false;
/* 173:    */         }
/* 174:293 */         if (0 == nesting) {
/* 175:295 */           if ('|' == c)
/* 176:    */           {
/* 177:297 */             if (null != targetStrings) {
/* 178:299 */               recordTokenString(targetStrings);
/* 179:    */             }
/* 180:302 */             isStartOfPat = true;
/* 181:    */           }
/* 182:    */         }
/* 183:306 */         if ((')' == c) || (']' == c)) {
/* 184:308 */           nesting--;
/* 185:310 */         } else if (('(' == c) || ('[' == c)) {
/* 186:312 */           nesting++;
/* 187:    */         }
/* 188:315 */         addToTokenQueue(pat.substring(i, i + 1));
/* 189:316 */         break;
/* 190:    */       case ':': 
/* 191:318 */         if (i > 0)
/* 192:    */         {
/* 193:320 */           if (posOfNSSep == i - 1)
/* 194:    */           {
/* 195:322 */             if (startSubstring != -1) {
/* 196:324 */               if (startSubstring < i - 1) {
/* 197:325 */                 addToTokenQueue(pat.substring(startSubstring, i - 1));
/* 198:    */               }
/* 199:    */             }
/* 200:328 */             isNum = false;
/* 201:329 */             isAttrName = false;
/* 202:330 */             startSubstring = -1;
/* 203:331 */             posOfNSSep = -1;
/* 204:    */             
/* 205:333 */             addToTokenQueue(pat.substring(i - 1, i + 1));
/* 206:    */             
/* 207:335 */             continue;
/* 208:    */           }
/* 209:339 */           posOfNSSep = i;
/* 210:    */         }
/* 211:    */         break;
/* 212:    */       }
/* 213:345 */       if (-1 == startSubstring)
/* 214:    */       {
/* 215:347 */         startSubstring = i;
/* 216:348 */         isNum = Character.isDigit(c);
/* 217:    */       }
/* 218:350 */       else if (isNum)
/* 219:    */       {
/* 220:352 */         isNum = Character.isDigit(c);
/* 221:    */       }
/* 222:    */     }
/* 223:357 */     if (startSubstring != -1)
/* 224:    */     {
/* 225:359 */       isNum = false;
/* 226:360 */       isStartOfPat = mapPatternElemPos(nesting, isStartOfPat, isAttrName);
/* 227:362 */       if ((-1 != posOfNSSep) || ((this.m_namespaceContext != null) && (this.m_namespaceContext.handlesNullPrefixes()))) {
/* 228:365 */         posOfNSSep = mapNSTokens(pat, startSubstring, posOfNSSep, nChars);
/* 229:    */       } else {
/* 230:369 */         addToTokenQueue(pat.substring(startSubstring, nChars));
/* 231:    */       }
/* 232:    */     }
/* 233:373 */     if (0 == this.m_compiler.getTokenQueueSize()) {
/* 234:375 */       this.m_processor.error("ER_EMPTY_EXPRESSION", null);
/* 235:377 */     } else if (null != targetStrings) {
/* 236:379 */       recordTokenString(targetStrings);
/* 237:    */     }
/* 238:382 */     this.m_processor.m_queueMark = 0;
/* 239:    */   }
/* 240:    */   
/* 241:    */   private boolean mapPatternElemPos(int nesting, boolean isStart, boolean isAttrName)
/* 242:    */   {
/* 243:400 */     if (0 == nesting)
/* 244:    */     {
/* 245:402 */       if (this.m_patternMapSize >= this.m_patternMap.length)
/* 246:    */       {
/* 247:404 */         int[] patternMap = this.m_patternMap;
/* 248:405 */         int len = this.m_patternMap.length;
/* 249:406 */         this.m_patternMap = new int[this.m_patternMapSize + 100];
/* 250:407 */         System.arraycopy(patternMap, 0, this.m_patternMap, 0, len);
/* 251:    */       }
/* 252:409 */       if (!isStart) {
/* 253:411 */         this.m_patternMap[(this.m_patternMapSize - 1)] -= 10000;
/* 254:    */       }
/* 255:413 */       this.m_patternMap[this.m_patternMapSize] = (this.m_compiler.getTokenQueueSize() - (isAttrName ? 1 : 0) + 10000);
/* 256:    */       
/* 257:    */ 
/* 258:416 */       this.m_patternMapSize += 1;
/* 259:    */       
/* 260:418 */       isStart = false;
/* 261:    */     }
/* 262:421 */     return isStart;
/* 263:    */   }
/* 264:    */   
/* 265:    */   private int getTokenQueuePosFromMap(int i)
/* 266:    */   {
/* 267:434 */     int pos = this.m_patternMap[i];
/* 268:    */     
/* 269:436 */     return pos >= 10000 ? pos - 10000 : pos;
/* 270:    */   }
/* 271:    */   
/* 272:    */   private final void resetTokenMark(int mark)
/* 273:    */   {
/* 274:447 */     int qsz = this.m_compiler.getTokenQueueSize();
/* 275:    */     
/* 276:449 */     this.m_processor.m_queueMark = (mark > 0 ? mark : mark <= qsz ? mark - 1 : 0);
/* 277:452 */     if (this.m_processor.m_queueMark < qsz)
/* 278:    */     {
/* 279:454 */       this.m_processor.m_token = ((String)this.m_compiler.getTokenQueue().elementAt(this.m_processor.m_queueMark++));
/* 280:    */       
/* 281:456 */       this.m_processor.m_tokenChar = this.m_processor.m_token.charAt(0);
/* 282:    */     }
/* 283:    */     else
/* 284:    */     {
/* 285:460 */       this.m_processor.m_token = null;
/* 286:461 */       this.m_processor.m_tokenChar = '\000';
/* 287:    */     }
/* 288:    */   }
/* 289:    */   
/* 290:    */   final int getKeywordToken(String key)
/* 291:    */   {
/* 292:    */     int tok;
/* 293:    */     try
/* 294:    */     {
/* 295:479 */       Integer itok = (Integer)Keywords.getKeyWord(key);
/* 296:    */       
/* 297:481 */       tok = null != itok ? itok.intValue() : 0;
/* 298:    */     }
/* 299:    */     catch (NullPointerException npe)
/* 300:    */     {
/* 301:485 */       tok = 0;
/* 302:    */     }
/* 303:    */     catch (ClassCastException cce)
/* 304:    */     {
/* 305:489 */       tok = 0;
/* 306:    */     }
/* 307:492 */     return tok;
/* 308:    */   }
/* 309:    */   
/* 310:    */   private void recordTokenString(Vector targetStrings)
/* 311:    */   {
/* 312:503 */     int tokPos = getTokenQueuePosFromMap(this.m_patternMapSize - 1);
/* 313:    */     
/* 314:505 */     resetTokenMark(tokPos + 1);
/* 315:507 */     if (this.m_processor.lookahead('(', 1))
/* 316:    */     {
/* 317:509 */       int tok = getKeywordToken(this.m_processor.m_token);
/* 318:511 */       switch (tok)
/* 319:    */       {
/* 320:    */       case 1030: 
/* 321:514 */         targetStrings.addElement("#comment");
/* 322:515 */         break;
/* 323:    */       case 1031: 
/* 324:517 */         targetStrings.addElement("#text");
/* 325:518 */         break;
/* 326:    */       case 1033: 
/* 327:520 */         targetStrings.addElement("*");
/* 328:521 */         break;
/* 329:    */       case 35: 
/* 330:523 */         targetStrings.addElement("/");
/* 331:524 */         break;
/* 332:    */       case 36: 
/* 333:526 */         targetStrings.addElement("*");
/* 334:527 */         break;
/* 335:    */       case 1032: 
/* 336:529 */         targetStrings.addElement("*");
/* 337:530 */         break;
/* 338:    */       default: 
/* 339:532 */         targetStrings.addElement("*");
/* 340:    */       }
/* 341:    */     }
/* 342:    */     else
/* 343:    */     {
/* 344:537 */       if (this.m_processor.tokenIs('@'))
/* 345:    */       {
/* 346:539 */         tokPos++;
/* 347:    */         
/* 348:541 */         resetTokenMark(tokPos + 1);
/* 349:    */       }
/* 350:544 */       if (this.m_processor.lookahead(':', 1)) {
/* 351:546 */         tokPos += 2;
/* 352:    */       }
/* 353:549 */       targetStrings.addElement(this.m_compiler.getTokenQueue().elementAt(tokPos));
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   private final void addToTokenQueue(String s)
/* 358:    */   {
/* 359:561 */     this.m_compiler.getTokenQueue().addElement(s);
/* 360:    */   }
/* 361:    */   
/* 362:    */   private int mapNSTokens(String pat, int startSubstring, int posOfNSSep, int posOfScan)
/* 363:    */     throws TransformerException
/* 364:    */   {
/* 365:582 */     String prefix = "";
/* 366:584 */     if ((startSubstring >= 0) && (posOfNSSep >= 0)) {
/* 367:586 */       prefix = pat.substring(startSubstring, posOfNSSep);
/* 368:    */     }
/* 369:    */     String uName;
/* 370:590 */     if ((null != this.m_namespaceContext) && (!prefix.equals("*")) && (!prefix.equals("xmlns"))) {
/* 371:    */       try
/* 372:    */       {
/* 373:595 */         if (prefix.length() > 0) {
/* 374:596 */           uName = this.m_namespaceContext.getNamespaceForPrefix(prefix);
/* 375:    */         } else {
/* 376:617 */           uName = this.m_namespaceContext.getNamespaceForPrefix(prefix);
/* 377:    */         }
/* 378:    */       }
/* 379:    */       catch (ClassCastException cce)
/* 380:    */       {
/* 381:625 */         uName = this.m_namespaceContext.getNamespaceForPrefix(prefix);
/* 382:    */       }
/* 383:    */     } else {
/* 384:630 */       uName = prefix;
/* 385:    */     }
/* 386:633 */     if ((null != uName) && (uName.length() > 0))
/* 387:    */     {
/* 388:635 */       addToTokenQueue(uName);
/* 389:636 */       addToTokenQueue(":");
/* 390:    */       
/* 391:638 */       String s = pat.substring(posOfNSSep + 1, posOfScan);
/* 392:640 */       if (s.length() > 0) {
/* 393:641 */         addToTokenQueue(s);
/* 394:    */       }
/* 395:    */     }
/* 396:    */     else
/* 397:    */     {
/* 398:647 */       this.m_processor.errorForDOM3("ER_PREFIX_MUST_RESOLVE", new String[] { prefix });
/* 399:    */     }
/* 400:667 */     return -1;
/* 401:    */   }
/* 402:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.compiler.Lexer
 * JD-Core Version:    0.7.0.1
 */