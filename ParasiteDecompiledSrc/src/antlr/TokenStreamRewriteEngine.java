/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.ASdebug.ASDebugStream;
/*   4:    */ import antlr.ASdebug.IASDebugStream;
/*   5:    */ import antlr.ASdebug.TokenOffsetInfo;
/*   6:    */ import antlr.collections.impl.BitSet;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.Comparator;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ 
/*  14:    */ public class TokenStreamRewriteEngine
/*  15:    */   implements TokenStream, IASDebugStream
/*  16:    */ {
/*  17:    */   public static final int MIN_TOKEN_INDEX = 0;
/*  18:    */   public static final String DEFAULT_PROGRAM_NAME = "default";
/*  19:    */   public static final int PROGRAM_INIT_SIZE = 100;
/*  20:    */   protected List tokens;
/*  21:    */   
/*  22:    */   static class RewriteOperation
/*  23:    */   {
/*  24:    */     protected int index;
/*  25:    */     protected String text;
/*  26:    */     
/*  27:    */     protected RewriteOperation(int paramInt, String paramString)
/*  28:    */     {
/*  29: 70 */       this.index = paramInt;
/*  30: 71 */       this.text = paramString;
/*  31:    */     }
/*  32:    */     
/*  33:    */     public int execute(StringBuffer paramStringBuffer)
/*  34:    */     {
/*  35: 77 */       return this.index;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public String toString()
/*  39:    */     {
/*  40: 80 */       String str = getClass().getName();
/*  41: 81 */       int i = str.indexOf('$');
/*  42: 82 */       str = str.substring(i + 1, str.length());
/*  43: 83 */       return str + "@" + this.index + '"' + this.text + '"';
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   static class InsertBeforeOp
/*  48:    */     extends TokenStreamRewriteEngine.RewriteOperation
/*  49:    */   {
/*  50:    */     public InsertBeforeOp(int paramInt, String paramString)
/*  51:    */     {
/*  52: 89 */       super(paramString);
/*  53:    */     }
/*  54:    */     
/*  55:    */     public int execute(StringBuffer paramStringBuffer)
/*  56:    */     {
/*  57: 92 */       paramStringBuffer.append(this.text);
/*  58: 93 */       return this.index;
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   static class ReplaceOp
/*  63:    */     extends TokenStreamRewriteEngine.RewriteOperation
/*  64:    */   {
/*  65:    */     protected int lastIndex;
/*  66:    */     
/*  67:    */     public ReplaceOp(int paramInt1, int paramInt2, String paramString)
/*  68:    */     {
/*  69:103 */       super(paramString);
/*  70:104 */       this.lastIndex = paramInt2;
/*  71:    */     }
/*  72:    */     
/*  73:    */     public int execute(StringBuffer paramStringBuffer)
/*  74:    */     {
/*  75:107 */       if (this.text != null) {
/*  76:108 */         paramStringBuffer.append(this.text);
/*  77:    */       }
/*  78:110 */       return this.lastIndex + 1;
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   static class DeleteOp
/*  83:    */     extends TokenStreamRewriteEngine.ReplaceOp
/*  84:    */   {
/*  85:    */     public DeleteOp(int paramInt1, int paramInt2)
/*  86:    */     {
/*  87:116 */       super(paramInt2, null);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:130 */   protected Map programs = null;
/*  92:133 */   protected Map lastRewriteTokenIndexes = null;
/*  93:136 */   protected int index = 0;
/*  94:    */   protected TokenStream stream;
/*  95:142 */   protected BitSet discardMask = new BitSet();
/*  96:    */   
/*  97:    */   public TokenStreamRewriteEngine(TokenStream paramTokenStream)
/*  98:    */   {
/*  99:145 */     this(paramTokenStream, 1000);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public TokenStreamRewriteEngine(TokenStream paramTokenStream, int paramInt)
/* 103:    */   {
/* 104:149 */     this.stream = paramTokenStream;
/* 105:150 */     this.tokens = new ArrayList(paramInt);
/* 106:151 */     this.programs = new HashMap();
/* 107:152 */     this.programs.put("default", new ArrayList(100));
/* 108:    */     
/* 109:154 */     this.lastRewriteTokenIndexes = new HashMap();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Token nextToken()
/* 113:    */     throws TokenStreamException
/* 114:    */   {
/* 115:    */     TokenWithIndex localTokenWithIndex;
/* 116:    */     do
/* 117:    */     {
/* 118:161 */       localTokenWithIndex = (TokenWithIndex)this.stream.nextToken();
/* 119:162 */       if (localTokenWithIndex != null)
/* 120:    */       {
/* 121:163 */         localTokenWithIndex.setIndex(this.index);
/* 122:164 */         if (localTokenWithIndex.getType() != 1) {
/* 123:165 */           this.tokens.add(localTokenWithIndex);
/* 124:    */         }
/* 125:167 */         this.index += 1;
/* 126:    */       }
/* 127:169 */     } while ((localTokenWithIndex != null) && (this.discardMask.member(localTokenWithIndex.getType())));
/* 128:170 */     return localTokenWithIndex;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void rollback(int paramInt)
/* 132:    */   {
/* 133:174 */     rollback("default", paramInt);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void rollback(String paramString, int paramInt)
/* 137:    */   {
/* 138:182 */     List localList = (List)this.programs.get(paramString);
/* 139:183 */     if (localList != null) {
/* 140:184 */       this.programs.put(paramString, localList.subList(0, paramInt));
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void deleteProgram()
/* 145:    */   {
/* 146:189 */     deleteProgram("default");
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void deleteProgram(String paramString)
/* 150:    */   {
/* 151:194 */     rollback(paramString, 0);
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected void addToSortedRewriteList(RewriteOperation paramRewriteOperation)
/* 155:    */   {
/* 156:200 */     addToSortedRewriteList("default", paramRewriteOperation);
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected void addToSortedRewriteList(String paramString, RewriteOperation paramRewriteOperation)
/* 160:    */   {
/* 161:247 */     List localList = getProgram(paramString);
/* 162:    */     
/* 163:249 */     Comparator local1 = new Comparator()
/* 164:    */     {
/* 165:    */       public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
/* 166:    */       {
/* 167:251 */         TokenStreamRewriteEngine.RewriteOperation localRewriteOperation1 = (TokenStreamRewriteEngine.RewriteOperation)paramAnonymousObject1;
/* 168:252 */         TokenStreamRewriteEngine.RewriteOperation localRewriteOperation2 = (TokenStreamRewriteEngine.RewriteOperation)paramAnonymousObject2;
/* 169:253 */         if (localRewriteOperation1.index < localRewriteOperation2.index) {
/* 170:253 */           return -1;
/* 171:    */         }
/* 172:254 */         if (localRewriteOperation1.index > localRewriteOperation2.index) {
/* 173:254 */           return 1;
/* 174:    */         }
/* 175:255 */         return 0;
/* 176:    */       }
/* 177:257 */     };
/* 178:258 */     int i = Collections.binarySearch(localList, paramRewriteOperation, local1);
/* 179:261 */     if (i >= 0)
/* 180:    */     {
/* 181:264 */       for (; i >= 0; i--)
/* 182:    */       {
/* 183:265 */         RewriteOperation localRewriteOperation1 = (RewriteOperation)localList.get(i);
/* 184:266 */         if (localRewriteOperation1.index < paramRewriteOperation.index) {
/* 185:    */           break;
/* 186:    */         }
/* 187:    */       }
/* 188:270 */       i++;
/* 189:277 */       if ((paramRewriteOperation instanceof ReplaceOp))
/* 190:    */       {
/* 191:278 */         int j = 0;
/* 192:281 */         for (int k = i; k < localList.size(); k++)
/* 193:    */         {
/* 194:282 */           RewriteOperation localRewriteOperation2 = (RewriteOperation)localList.get(i);
/* 195:283 */           if (localRewriteOperation2.index != paramRewriteOperation.index) {
/* 196:    */             break;
/* 197:    */           }
/* 198:286 */           if ((localRewriteOperation2 instanceof ReplaceOp))
/* 199:    */           {
/* 200:287 */             localList.set(i, paramRewriteOperation);
/* 201:288 */             j = 1;
/* 202:289 */             break;
/* 203:    */           }
/* 204:    */         }
/* 205:293 */         if (j == 0) {
/* 206:295 */           localList.add(k, paramRewriteOperation);
/* 207:    */         }
/* 208:    */       }
/* 209:    */       else
/* 210:    */       {
/* 211:300 */         localList.add(i, paramRewriteOperation);
/* 212:    */       }
/* 213:    */     }
/* 214:    */     else
/* 215:    */     {
/* 216:305 */       localList.add(-i - 1, paramRewriteOperation);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void insertAfter(Token paramToken, String paramString)
/* 221:    */   {
/* 222:311 */     insertAfter("default", paramToken, paramString);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void insertAfter(int paramInt, String paramString)
/* 226:    */   {
/* 227:315 */     insertAfter("default", paramInt, paramString);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void insertAfter(String paramString1, Token paramToken, String paramString2)
/* 231:    */   {
/* 232:319 */     insertAfter(paramString1, ((TokenWithIndex)paramToken).getIndex(), paramString2);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void insertAfter(String paramString1, int paramInt, String paramString2)
/* 236:    */   {
/* 237:324 */     insertBefore(paramString1, paramInt + 1, paramString2);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void insertBefore(Token paramToken, String paramString)
/* 241:    */   {
/* 242:328 */     insertBefore("default", paramToken, paramString);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void insertBefore(int paramInt, String paramString)
/* 246:    */   {
/* 247:332 */     insertBefore("default", paramInt, paramString);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void insertBefore(String paramString1, Token paramToken, String paramString2)
/* 251:    */   {
/* 252:336 */     insertBefore(paramString1, ((TokenWithIndex)paramToken).getIndex(), paramString2);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void insertBefore(String paramString1, int paramInt, String paramString2)
/* 256:    */   {
/* 257:340 */     addToSortedRewriteList(paramString1, new InsertBeforeOp(paramInt, paramString2));
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void replace(int paramInt, String paramString)
/* 261:    */   {
/* 262:344 */     replace("default", paramInt, paramInt, paramString);
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void replace(int paramInt1, int paramInt2, String paramString)
/* 266:    */   {
/* 267:348 */     replace("default", paramInt1, paramInt2, paramString);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void replace(Token paramToken, String paramString)
/* 271:    */   {
/* 272:352 */     replace("default", paramToken, paramToken, paramString);
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void replace(Token paramToken1, Token paramToken2, String paramString)
/* 276:    */   {
/* 277:356 */     replace("default", paramToken1, paramToken2, paramString);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void replace(String paramString1, int paramInt1, int paramInt2, String paramString2)
/* 281:    */   {
/* 282:360 */     addToSortedRewriteList(new ReplaceOp(paramInt1, paramInt2, paramString2));
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void replace(String paramString1, Token paramToken1, Token paramToken2, String paramString2)
/* 286:    */   {
/* 287:364 */     replace(paramString1, ((TokenWithIndex)paramToken1).getIndex(), ((TokenWithIndex)paramToken2).getIndex(), paramString2);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void delete(int paramInt)
/* 291:    */   {
/* 292:371 */     delete("default", paramInt, paramInt);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void delete(int paramInt1, int paramInt2)
/* 296:    */   {
/* 297:375 */     delete("default", paramInt1, paramInt2);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void delete(Token paramToken)
/* 301:    */   {
/* 302:379 */     delete("default", paramToken, paramToken);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void delete(Token paramToken1, Token paramToken2)
/* 306:    */   {
/* 307:383 */     delete("default", paramToken1, paramToken2);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void delete(String paramString, int paramInt1, int paramInt2)
/* 311:    */   {
/* 312:387 */     replace(paramString, paramInt1, paramInt2, null);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void delete(String paramString, Token paramToken1, Token paramToken2)
/* 316:    */   {
/* 317:391 */     replace(paramString, paramToken1, paramToken2, null);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void discard(int paramInt)
/* 321:    */   {
/* 322:395 */     this.discardMask.add(paramInt);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public TokenWithIndex getToken(int paramInt)
/* 326:    */   {
/* 327:399 */     return (TokenWithIndex)this.tokens.get(paramInt);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public int getTokenStreamSize()
/* 331:    */   {
/* 332:403 */     return this.tokens.size();
/* 333:    */   }
/* 334:    */   
/* 335:    */   public String toOriginalString()
/* 336:    */   {
/* 337:407 */     return toOriginalString(0, getTokenStreamSize() - 1);
/* 338:    */   }
/* 339:    */   
/* 340:    */   public String toOriginalString(int paramInt1, int paramInt2)
/* 341:    */   {
/* 342:411 */     StringBuffer localStringBuffer = new StringBuffer();
/* 343:412 */     for (int i = paramInt1; (i >= 0) && (i <= paramInt2) && (i < this.tokens.size()); i++) {
/* 344:413 */       localStringBuffer.append(getToken(i).getText());
/* 345:    */     }
/* 346:415 */     return localStringBuffer.toString();
/* 347:    */   }
/* 348:    */   
/* 349:    */   public String toString()
/* 350:    */   {
/* 351:419 */     return toString(0, getTokenStreamSize() - 1);
/* 352:    */   }
/* 353:    */   
/* 354:    */   public String toString(String paramString)
/* 355:    */   {
/* 356:423 */     return toString(paramString, 0, getTokenStreamSize() - 1);
/* 357:    */   }
/* 358:    */   
/* 359:    */   public String toString(int paramInt1, int paramInt2)
/* 360:    */   {
/* 361:427 */     return toString("default", paramInt1, paramInt2);
/* 362:    */   }
/* 363:    */   
/* 364:    */   public String toString(String paramString, int paramInt1, int paramInt2)
/* 365:    */   {
/* 366:431 */     List localList = (List)this.programs.get(paramString);
/* 367:432 */     if ((localList == null) || (localList.size() == 0)) {
/* 368:433 */       return toOriginalString(paramInt1, paramInt2);
/* 369:    */     }
/* 370:435 */     StringBuffer localStringBuffer = new StringBuffer();
/* 371:    */     
/* 372:    */ 
/* 373:438 */     RewriteOperation localRewriteOperation1 = 0;
/* 374:    */     
/* 375:440 */     int i = paramInt1;
/* 376:443 */     while ((i >= 0) && (i <= paramInt2) && (i < this.tokens.size()))
/* 377:    */     {
/* 378:447 */       if (localRewriteOperation1 < localList.size())
/* 379:    */       {
/* 380:448 */         localRewriteOperation2 = (RewriteOperation)localList.get(localRewriteOperation1);
/* 381:452 */         while ((localRewriteOperation2.index < i) && (localRewriteOperation1 < localList.size()))
/* 382:    */         {
/* 383:453 */           localRewriteOperation1++;
/* 384:454 */           if (localRewriteOperation1 < localList.size()) {
/* 385:455 */             localRewriteOperation2 = (RewriteOperation)localList.get(localRewriteOperation1);
/* 386:    */           }
/* 387:    */         }
/* 388:460 */         while ((i == localRewriteOperation2.index) && (localRewriteOperation1 < localList.size()))
/* 389:    */         {
/* 390:462 */           i = localRewriteOperation2.execute(localStringBuffer);
/* 391:    */           
/* 392:464 */           localRewriteOperation1++;
/* 393:465 */           if (localRewriteOperation1 < localList.size()) {
/* 394:466 */             localRewriteOperation2 = (RewriteOperation)localList.get(localRewriteOperation1);
/* 395:    */           }
/* 396:    */         }
/* 397:    */       }
/* 398:471 */       if (i <= paramInt2)
/* 399:    */       {
/* 400:472 */         localStringBuffer.append(getToken(i).getText());
/* 401:473 */         i++;
/* 402:    */       }
/* 403:    */     }
/* 404:477 */     for (RewriteOperation localRewriteOperation2 = localRewriteOperation1; localRewriteOperation2 < localList.size(); localRewriteOperation2++)
/* 405:    */     {
/* 406:478 */       RewriteOperation localRewriteOperation3 = (RewriteOperation)localList.get(localRewriteOperation2);
/* 407:480 */       if (localRewriteOperation3.index >= size()) {
/* 408:481 */         localRewriteOperation3.execute(localStringBuffer);
/* 409:    */       }
/* 410:    */     }
/* 411:487 */     return localStringBuffer.toString();
/* 412:    */   }
/* 413:    */   
/* 414:    */   public String toDebugString()
/* 415:    */   {
/* 416:491 */     return toDebugString(0, getTokenStreamSize() - 1);
/* 417:    */   }
/* 418:    */   
/* 419:    */   public String toDebugString(int paramInt1, int paramInt2)
/* 420:    */   {
/* 421:495 */     StringBuffer localStringBuffer = new StringBuffer();
/* 422:496 */     for (int i = paramInt1; (i >= 0) && (i <= paramInt2) && (i < this.tokens.size()); i++) {
/* 423:497 */       localStringBuffer.append(getToken(i));
/* 424:    */     }
/* 425:499 */     return localStringBuffer.toString();
/* 426:    */   }
/* 427:    */   
/* 428:    */   public int getLastRewriteTokenIndex()
/* 429:    */   {
/* 430:503 */     return getLastRewriteTokenIndex("default");
/* 431:    */   }
/* 432:    */   
/* 433:    */   protected int getLastRewriteTokenIndex(String paramString)
/* 434:    */   {
/* 435:507 */     Integer localInteger = (Integer)this.lastRewriteTokenIndexes.get(paramString);
/* 436:508 */     if (localInteger == null) {
/* 437:509 */       return -1;
/* 438:    */     }
/* 439:511 */     return localInteger.intValue();
/* 440:    */   }
/* 441:    */   
/* 442:    */   protected void setLastRewriteTokenIndex(String paramString, int paramInt)
/* 443:    */   {
/* 444:515 */     this.lastRewriteTokenIndexes.put(paramString, new Integer(paramInt));
/* 445:    */   }
/* 446:    */   
/* 447:    */   protected List getProgram(String paramString)
/* 448:    */   {
/* 449:519 */     List localList = (List)this.programs.get(paramString);
/* 450:520 */     if (localList == null) {
/* 451:521 */       localList = initializeProgram(paramString);
/* 452:    */     }
/* 453:523 */     return localList;
/* 454:    */   }
/* 455:    */   
/* 456:    */   private List initializeProgram(String paramString)
/* 457:    */   {
/* 458:527 */     ArrayList localArrayList = new ArrayList(100);
/* 459:528 */     this.programs.put(paramString, localArrayList);
/* 460:529 */     return localArrayList;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public int size()
/* 464:    */   {
/* 465:533 */     return this.tokens.size();
/* 466:    */   }
/* 467:    */   
/* 468:    */   public int index()
/* 469:    */   {
/* 470:537 */     return this.index;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public String getEntireText()
/* 474:    */   {
/* 475:542 */     return ASDebugStream.getEntireText(this.stream);
/* 476:    */   }
/* 477:    */   
/* 478:    */   public TokenOffsetInfo getOffsetInfo(Token paramToken)
/* 479:    */   {
/* 480:547 */     return ASDebugStream.getOffsetInfo(this.stream, paramToken);
/* 481:    */   }
/* 482:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenStreamRewriteEngine
 * JD-Core Version:    0.7.0.1
 */