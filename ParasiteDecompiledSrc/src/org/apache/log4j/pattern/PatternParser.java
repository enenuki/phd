/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.apache.log4j.helpers.Loader;
/*  11:    */ import org.apache.log4j.helpers.LogLog;
/*  12:    */ 
/*  13:    */ public final class PatternParser
/*  14:    */ {
/*  15:    */   private static final char ESCAPE_CHAR = '%';
/*  16:    */   private static final int LITERAL_STATE = 0;
/*  17:    */   private static final int CONVERTER_STATE = 1;
/*  18:    */   private static final int DOT_STATE = 3;
/*  19:    */   private static final int MIN_STATE = 4;
/*  20:    */   private static final int MAX_STATE = 5;
/*  21:    */   private static final Map PATTERN_LAYOUT_RULES;
/*  22:    */   private static final Map FILENAME_PATTERN_RULES;
/*  23:    */   
/*  24:    */   static
/*  25:    */   {
/*  26: 91 */     Map rules = new HashMap(17);
/*  27: 92 */     rules.put("c", LoggerPatternConverter.class);
/*  28: 93 */     rules.put("logger", LoggerPatternConverter.class);
/*  29:    */     
/*  30: 95 */     rules.put("C", ClassNamePatternConverter.class);
/*  31: 96 */     rules.put("class", ClassNamePatternConverter.class);
/*  32:    */     
/*  33: 98 */     rules.put("d", DatePatternConverter.class);
/*  34: 99 */     rules.put("date", DatePatternConverter.class);
/*  35:    */     
/*  36:101 */     rules.put("F", FileLocationPatternConverter.class);
/*  37:102 */     rules.put("file", FileLocationPatternConverter.class);
/*  38:    */     
/*  39:104 */     rules.put("l", FullLocationPatternConverter.class);
/*  40:    */     
/*  41:106 */     rules.put("L", LineLocationPatternConverter.class);
/*  42:107 */     rules.put("line", LineLocationPatternConverter.class);
/*  43:    */     
/*  44:109 */     rules.put("m", MessagePatternConverter.class);
/*  45:110 */     rules.put("message", MessagePatternConverter.class);
/*  46:    */     
/*  47:112 */     rules.put("n", LineSeparatorPatternConverter.class);
/*  48:    */     
/*  49:114 */     rules.put("M", MethodLocationPatternConverter.class);
/*  50:115 */     rules.put("method", MethodLocationPatternConverter.class);
/*  51:    */     
/*  52:117 */     rules.put("p", LevelPatternConverter.class);
/*  53:118 */     rules.put("level", LevelPatternConverter.class);
/*  54:    */     
/*  55:120 */     rules.put("r", RelativeTimePatternConverter.class);
/*  56:121 */     rules.put("relative", RelativeTimePatternConverter.class);
/*  57:    */     
/*  58:123 */     rules.put("t", ThreadPatternConverter.class);
/*  59:124 */     rules.put("thread", ThreadPatternConverter.class);
/*  60:    */     
/*  61:126 */     rules.put("x", NDCPatternConverter.class);
/*  62:127 */     rules.put("ndc", NDCPatternConverter.class);
/*  63:    */     
/*  64:129 */     rules.put("X", PropertiesPatternConverter.class);
/*  65:130 */     rules.put("properties", PropertiesPatternConverter.class);
/*  66:    */     
/*  67:132 */     rules.put("sn", SequenceNumberPatternConverter.class);
/*  68:133 */     rules.put("sequenceNumber", SequenceNumberPatternConverter.class);
/*  69:    */     
/*  70:135 */     rules.put("throwable", ThrowableInformationPatternConverter.class);
/*  71:136 */     PATTERN_LAYOUT_RULES = new ReadOnlyMap(rules);
/*  72:    */     
/*  73:138 */     Map fnameRules = new HashMap(4);
/*  74:139 */     fnameRules.put("d", FileDatePatternConverter.class);
/*  75:140 */     fnameRules.put("date", FileDatePatternConverter.class);
/*  76:141 */     fnameRules.put("i", IntegerPatternConverter.class);
/*  77:142 */     fnameRules.put("index", IntegerPatternConverter.class);
/*  78:    */     
/*  79:144 */     FILENAME_PATTERN_RULES = new ReadOnlyMap(fnameRules);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static Map getPatternLayoutRules()
/*  83:    */   {
/*  84:158 */     return PATTERN_LAYOUT_RULES;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static Map getFileNamePatternRules()
/*  88:    */   {
/*  89:166 */     return FILENAME_PATTERN_RULES;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private static int extractConverter(char lastChar, String pattern, int i, StringBuffer convBuf, StringBuffer currentLiteral)
/*  93:    */   {
/*  94:187 */     convBuf.setLength(0);
/*  95:194 */     if (!Character.isUnicodeIdentifierStart(lastChar)) {
/*  96:195 */       return i;
/*  97:    */     }
/*  98:198 */     convBuf.append(lastChar);
/*  99:202 */     while ((i < pattern.length()) && (Character.isUnicodeIdentifierPart(pattern.charAt(i))))
/* 100:    */     {
/* 101:203 */       convBuf.append(pattern.charAt(i));
/* 102:204 */       currentLiteral.append(pattern.charAt(i));
/* 103:    */       
/* 104:    */ 
/* 105:207 */       i++;
/* 106:    */     }
/* 107:210 */     return i;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private static int extractOptions(String pattern, int i, List options)
/* 111:    */   {
/* 112:221 */     while ((i < pattern.length()) && (pattern.charAt(i) == '{'))
/* 113:    */     {
/* 114:222 */       int end = pattern.indexOf('}', i);
/* 115:224 */       if (end == -1) {
/* 116:    */         break;
/* 117:    */       }
/* 118:228 */       String r = pattern.substring(i + 1, end);
/* 119:229 */       options.add(r);
/* 120:230 */       i = end + 1;
/* 121:    */     }
/* 122:233 */     return i;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static void parse(String pattern, List patternConverters, List formattingInfos, Map converterRegistry, Map rules)
/* 126:    */   {
/* 127:247 */     if (pattern == null) {
/* 128:248 */       throw new NullPointerException("pattern");
/* 129:    */     }
/* 130:251 */     StringBuffer currentLiteral = new StringBuffer(32);
/* 131:    */     
/* 132:253 */     int patternLength = pattern.length();
/* 133:254 */     int state = 0;
/* 134:    */     
/* 135:256 */     int i = 0;
/* 136:257 */     FormattingInfo formattingInfo = FormattingInfo.getDefault();
/* 137:259 */     while (i < patternLength)
/* 138:    */     {
/* 139:260 */       char c = pattern.charAt(i++);
/* 140:262 */       switch (state)
/* 141:    */       {
/* 142:    */       case 0: 
/* 143:266 */         if (i == patternLength) {
/* 144:267 */           currentLiteral.append(c);
/* 145:272 */         } else if (c == '%') {
/* 146:274 */           switch (pattern.charAt(i))
/* 147:    */           {
/* 148:    */           case '%': 
/* 149:276 */             currentLiteral.append(c);
/* 150:277 */             i++;
/* 151:    */             
/* 152:279 */             break;
/* 153:    */           default: 
/* 154:283 */             if (currentLiteral.length() != 0)
/* 155:    */             {
/* 156:284 */               patternConverters.add(new LiteralPatternConverter(currentLiteral.toString()));
/* 157:    */               
/* 158:286 */               formattingInfos.add(FormattingInfo.getDefault());
/* 159:    */             }
/* 160:289 */             currentLiteral.setLength(0);
/* 161:290 */             currentLiteral.append(c);
/* 162:291 */             state = 1;
/* 163:292 */             formattingInfo = FormattingInfo.getDefault(); break;
/* 164:    */           }
/* 165:    */         } else {
/* 166:295 */           currentLiteral.append(c);
/* 167:    */         }
/* 168:298 */         break;
/* 169:    */       case 1: 
/* 170:301 */         currentLiteral.append(c);
/* 171:303 */         switch (c)
/* 172:    */         {
/* 173:    */         case '-': 
/* 174:305 */           formattingInfo = new FormattingInfo(true, formattingInfo.getMinLength(), formattingInfo.getMaxLength());
/* 175:    */           
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:310 */           break;
/* 180:    */         case '.': 
/* 181:313 */           state = 3;
/* 182:    */           
/* 183:315 */           break;
/* 184:    */         default: 
/* 185:319 */           if ((c >= '0') && (c <= '9'))
/* 186:    */           {
/* 187:320 */             formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), c - '0', formattingInfo.getMaxLength());
/* 188:    */             
/* 189:    */ 
/* 190:    */ 
/* 191:324 */             state = 4;
/* 192:    */           }
/* 193:    */           else
/* 194:    */           {
/* 195:326 */             i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, converterRegistry, rules, patternConverters, formattingInfos);
/* 196:    */             
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:331 */             state = 0;
/* 201:332 */             formattingInfo = FormattingInfo.getDefault();
/* 202:333 */             currentLiteral.setLength(0);
/* 203:    */           }
/* 204:    */           break;
/* 205:    */         }
/* 206:337 */         break;
/* 207:    */       case 4: 
/* 208:340 */         currentLiteral.append(c);
/* 209:342 */         if ((c >= '0') && (c <= '9'))
/* 210:    */         {
/* 211:343 */           formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength() * 10 + (c - '0'), formattingInfo.getMaxLength());
/* 212:    */         }
/* 213:348 */         else if (c == '.')
/* 214:    */         {
/* 215:349 */           state = 3;
/* 216:    */         }
/* 217:    */         else
/* 218:    */         {
/* 219:351 */           i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, converterRegistry, rules, patternConverters, formattingInfos);
/* 220:    */           
/* 221:    */ 
/* 222:354 */           state = 0;
/* 223:355 */           formattingInfo = FormattingInfo.getDefault();
/* 224:356 */           currentLiteral.setLength(0);
/* 225:    */         }
/* 226:359 */         break;
/* 227:    */       case 3: 
/* 228:362 */         currentLiteral.append(c);
/* 229:364 */         if ((c >= '0') && (c <= '9'))
/* 230:    */         {
/* 231:365 */           formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), c - '0');
/* 232:    */           
/* 233:    */ 
/* 234:    */ 
/* 235:369 */           state = 5;
/* 236:    */         }
/* 237:    */         else
/* 238:    */         {
/* 239:371 */           LogLog.error("Error occured in position " + i + ".\n Was expecting digit, instead got char \"" + c + "\".");
/* 240:    */           
/* 241:    */ 
/* 242:    */ 
/* 243:375 */           state = 0;
/* 244:    */         }
/* 245:378 */         break;
/* 246:    */       case 5: 
/* 247:381 */         currentLiteral.append(c);
/* 248:383 */         if ((c >= '0') && (c <= '9'))
/* 249:    */         {
/* 250:384 */           formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), formattingInfo.getMaxLength() * 10 + (c - '0'));
/* 251:    */         }
/* 252:    */         else
/* 253:    */         {
/* 254:389 */           i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, converterRegistry, rules, patternConverters, formattingInfos);
/* 255:    */           
/* 256:    */ 
/* 257:392 */           state = 0;
/* 258:393 */           formattingInfo = FormattingInfo.getDefault();
/* 259:394 */           currentLiteral.setLength(0);
/* 260:    */         }
/* 261:    */         break;
/* 262:    */       }
/* 263:    */     }
/* 264:402 */     if (currentLiteral.length() != 0)
/* 265:    */     {
/* 266:403 */       patternConverters.add(new LiteralPatternConverter(currentLiteral.toString()));
/* 267:    */       
/* 268:405 */       formattingInfos.add(FormattingInfo.getDefault());
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   private static PatternConverter createConverter(String converterId, StringBuffer currentLiteral, Map converterRegistry, Map rules, List options)
/* 273:    */   {
/* 274:424 */     String converterName = converterId;
/* 275:425 */     Object converterObj = null;
/* 276:427 */     for (int i = converterId.length(); (i > 0) && (converterObj == null); i--)
/* 277:    */     {
/* 278:429 */       converterName = converterName.substring(0, i);
/* 279:431 */       if (converterRegistry != null) {
/* 280:432 */         converterObj = converterRegistry.get(converterName);
/* 281:    */       }
/* 282:435 */       if ((converterObj == null) && (rules != null)) {
/* 283:436 */         converterObj = rules.get(converterName);
/* 284:    */       }
/* 285:    */     }
/* 286:440 */     if (converterObj == null)
/* 287:    */     {
/* 288:441 */       LogLog.error("Unrecognized format specifier [" + converterId + "]");
/* 289:    */       
/* 290:443 */       return null;
/* 291:    */     }
/* 292:446 */     Class converterClass = null;
/* 293:448 */     if ((converterObj instanceof Class))
/* 294:    */     {
/* 295:449 */       converterClass = (Class)converterObj;
/* 296:    */     }
/* 297:451 */     else if ((converterObj instanceof String))
/* 298:    */     {
/* 299:    */       try
/* 300:    */       {
/* 301:453 */         converterClass = Loader.loadClass((String)converterObj);
/* 302:    */       }
/* 303:    */       catch (ClassNotFoundException ex)
/* 304:    */       {
/* 305:455 */         LogLog.warn("Class for conversion pattern %" + converterName + " not found", ex);
/* 306:    */         
/* 307:    */ 
/* 308:    */ 
/* 309:459 */         return null;
/* 310:    */       }
/* 311:    */     }
/* 312:    */     else
/* 313:    */     {
/* 314:462 */       LogLog.warn("Bad map entry for conversion pattern %" + converterName + ".");
/* 315:    */       
/* 316:    */ 
/* 317:465 */       return null;
/* 318:    */     }
/* 319:    */     try
/* 320:    */     {
/* 321:470 */       Method factory = converterClass.getMethod("newInstance", new Class[] { Class.forName("[Ljava.lang.String;") });
/* 322:    */       
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:476 */       String[] optionsArray = new String[options.size()];
/* 328:477 */       optionsArray = (String[])options.toArray(optionsArray);
/* 329:    */       
/* 330:479 */       Object newObj = factory.invoke(null, new Object[] { optionsArray });
/* 331:482 */       if ((newObj instanceof PatternConverter))
/* 332:    */       {
/* 333:483 */         currentLiteral.delete(0, currentLiteral.length() - (converterId.length() - converterName.length()));
/* 334:    */         
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:488 */         return (PatternConverter)newObj;
/* 339:    */       }
/* 340:490 */       LogLog.warn("Class " + converterClass.getName() + " does not extend PatternConverter.");
/* 341:    */     }
/* 342:    */     catch (Exception ex)
/* 343:    */     {
/* 344:495 */       LogLog.error("Error creating converter for " + converterId, ex);
/* 345:    */       try
/* 346:    */       {
/* 347:500 */         PatternConverter pc = (PatternConverter)converterClass.newInstance();
/* 348:501 */         currentLiteral.delete(0, currentLiteral.length() - (converterId.length() - converterName.length()));
/* 349:    */         
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:506 */         return pc;
/* 354:    */       }
/* 355:    */       catch (Exception ex2)
/* 356:    */       {
/* 357:508 */         LogLog.error("Error creating converter for " + converterId, ex2);
/* 358:    */       }
/* 359:    */     }
/* 360:512 */     return null;
/* 361:    */   }
/* 362:    */   
/* 363:    */   private static int finalizeConverter(char c, String pattern, int i, StringBuffer currentLiteral, FormattingInfo formattingInfo, Map converterRegistry, Map rules, List patternConverters, List formattingInfos)
/* 364:    */   {
/* 365:534 */     StringBuffer convBuf = new StringBuffer();
/* 366:535 */     i = extractConverter(c, pattern, i, convBuf, currentLiteral);
/* 367:    */     
/* 368:537 */     String converterId = convBuf.toString();
/* 369:    */     
/* 370:539 */     List options = new ArrayList();
/* 371:540 */     i = extractOptions(pattern, i, options);
/* 372:    */     
/* 373:542 */     PatternConverter pc = createConverter(converterId, currentLiteral, converterRegistry, rules, options);
/* 374:546 */     if (pc == null)
/* 375:    */     {
/* 376:    */       StringBuffer msg;
/* 377:    */       StringBuffer msg;
/* 378:549 */       if ((converterId == null) || (converterId.length() == 0))
/* 379:    */       {
/* 380:550 */         msg = new StringBuffer("Empty conversion specifier starting at position ");
/* 381:    */       }
/* 382:    */       else
/* 383:    */       {
/* 384:553 */         msg = new StringBuffer("Unrecognized conversion specifier [");
/* 385:554 */         msg.append(converterId);
/* 386:555 */         msg.append("] starting at position ");
/* 387:    */       }
/* 388:558 */       msg.append(Integer.toString(i));
/* 389:559 */       msg.append(" in conversion pattern.");
/* 390:    */       
/* 391:561 */       LogLog.error(msg.toString());
/* 392:    */       
/* 393:563 */       patternConverters.add(new LiteralPatternConverter(currentLiteral.toString()));
/* 394:    */       
/* 395:565 */       formattingInfos.add(FormattingInfo.getDefault());
/* 396:    */     }
/* 397:    */     else
/* 398:    */     {
/* 399:567 */       patternConverters.add(pc);
/* 400:568 */       formattingInfos.add(formattingInfo);
/* 401:570 */       if (currentLiteral.length() > 0)
/* 402:    */       {
/* 403:571 */         patternConverters.add(new LiteralPatternConverter(currentLiteral.toString()));
/* 404:    */         
/* 405:573 */         formattingInfos.add(FormattingInfo.getDefault());
/* 406:    */       }
/* 407:    */     }
/* 408:577 */     currentLiteral.setLength(0);
/* 409:    */     
/* 410:579 */     return i;
/* 411:    */   }
/* 412:    */   
/* 413:    */   private static class ReadOnlyMap
/* 414:    */     implements Map
/* 415:    */   {
/* 416:    */     private final Map map;
/* 417:    */     
/* 418:    */     public ReadOnlyMap(Map src)
/* 419:    */     {
/* 420:596 */       this.map = src;
/* 421:    */     }
/* 422:    */     
/* 423:    */     public void clear()
/* 424:    */     {
/* 425:603 */       throw new UnsupportedOperationException();
/* 426:    */     }
/* 427:    */     
/* 428:    */     public boolean containsKey(Object key)
/* 429:    */     {
/* 430:610 */       return this.map.containsKey(key);
/* 431:    */     }
/* 432:    */     
/* 433:    */     public boolean containsValue(Object value)
/* 434:    */     {
/* 435:617 */       return this.map.containsValue(value);
/* 436:    */     }
/* 437:    */     
/* 438:    */     public Set entrySet()
/* 439:    */     {
/* 440:624 */       return this.map.entrySet();
/* 441:    */     }
/* 442:    */     
/* 443:    */     public Object get(Object key)
/* 444:    */     {
/* 445:631 */       return this.map.get(key);
/* 446:    */     }
/* 447:    */     
/* 448:    */     public boolean isEmpty()
/* 449:    */     {
/* 450:638 */       return this.map.isEmpty();
/* 451:    */     }
/* 452:    */     
/* 453:    */     public Set keySet()
/* 454:    */     {
/* 455:645 */       return this.map.keySet();
/* 456:    */     }
/* 457:    */     
/* 458:    */     public Object put(Object key, Object value)
/* 459:    */     {
/* 460:652 */       throw new UnsupportedOperationException();
/* 461:    */     }
/* 462:    */     
/* 463:    */     public void putAll(Map t)
/* 464:    */     {
/* 465:659 */       throw new UnsupportedOperationException();
/* 466:    */     }
/* 467:    */     
/* 468:    */     public Object remove(Object key)
/* 469:    */     {
/* 470:666 */       throw new UnsupportedOperationException();
/* 471:    */     }
/* 472:    */     
/* 473:    */     public int size()
/* 474:    */     {
/* 475:673 */       return this.map.size();
/* 476:    */     }
/* 477:    */     
/* 478:    */     public Collection values()
/* 479:    */     {
/* 480:680 */       return this.map.values();
/* 481:    */     }
/* 482:    */   }
/* 483:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.PatternParser
 * JD-Core Version:    0.7.0.1
 */