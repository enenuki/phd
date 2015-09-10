/*   1:    */ package antlr.debug;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import java.util.Vector;
/*   7:    */ 
/*   8:    */ public class ParserEventSupport
/*   9:    */ {
/*  10:    */   private Object source;
/*  11:    */   private Hashtable doneListeners;
/*  12:    */   private Vector matchListeners;
/*  13:    */   private Vector messageListeners;
/*  14:    */   private Vector tokenListeners;
/*  15:    */   private Vector traceListeners;
/*  16:    */   private Vector semPredListeners;
/*  17:    */   private Vector synPredListeners;
/*  18:    */   private Vector newLineListeners;
/*  19:    */   private ParserMatchEvent matchEvent;
/*  20:    */   private MessageEvent messageEvent;
/*  21:    */   private ParserTokenEvent tokenEvent;
/*  22:    */   private SemanticPredicateEvent semPredEvent;
/*  23:    */   private SyntacticPredicateEvent synPredEvent;
/*  24:    */   private TraceEvent traceEvent;
/*  25:    */   private NewLineEvent newLineEvent;
/*  26:    */   private ParserController controller;
/*  27:    */   protected static final int CONSUME = 0;
/*  28:    */   protected static final int ENTER_RULE = 1;
/*  29:    */   protected static final int EXIT_RULE = 2;
/*  30:    */   protected static final int LA = 3;
/*  31:    */   protected static final int MATCH = 4;
/*  32:    */   protected static final int MATCH_NOT = 5;
/*  33:    */   protected static final int MISMATCH = 6;
/*  34:    */   protected static final int MISMATCH_NOT = 7;
/*  35:    */   protected static final int REPORT_ERROR = 8;
/*  36:    */   protected static final int REPORT_WARNING = 9;
/*  37:    */   protected static final int SEMPRED = 10;
/*  38:    */   protected static final int SYNPRED_FAILED = 11;
/*  39:    */   protected static final int SYNPRED_STARTED = 12;
/*  40:    */   protected static final int SYNPRED_SUCCEEDED = 13;
/*  41:    */   protected static final int NEW_LINE = 14;
/*  42:    */   protected static final int DONE_PARSING = 15;
/*  43: 50 */   private int ruleDepth = 0;
/*  44:    */   
/*  45:    */   public ParserEventSupport(Object paramObject)
/*  46:    */   {
/*  47: 54 */     this.matchEvent = new ParserMatchEvent(paramObject);
/*  48: 55 */     this.messageEvent = new MessageEvent(paramObject);
/*  49: 56 */     this.tokenEvent = new ParserTokenEvent(paramObject);
/*  50: 57 */     this.traceEvent = new TraceEvent(paramObject);
/*  51: 58 */     this.semPredEvent = new SemanticPredicateEvent(paramObject);
/*  52: 59 */     this.synPredEvent = new SyntacticPredicateEvent(paramObject);
/*  53: 60 */     this.newLineEvent = new NewLineEvent(paramObject);
/*  54: 61 */     this.source = paramObject;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void addDoneListener(ListenerBase paramListenerBase)
/*  58:    */   {
/*  59: 64 */     if (this.doneListeners == null) {
/*  60: 64 */       this.doneListeners = new Hashtable();
/*  61:    */     }
/*  62: 65 */     Integer localInteger = (Integer)this.doneListeners.get(paramListenerBase);
/*  63:    */     int i;
/*  64: 67 */     if (localInteger != null) {
/*  65: 68 */       i = localInteger.intValue() + 1;
/*  66:    */     } else {
/*  67: 70 */       i = 1;
/*  68:    */     }
/*  69: 71 */     this.doneListeners.put(paramListenerBase, new Integer(i));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void addMessageListener(MessageListener paramMessageListener)
/*  73:    */   {
/*  74: 74 */     if (this.messageListeners == null) {
/*  75: 74 */       this.messageListeners = new Vector();
/*  76:    */     }
/*  77: 75 */     this.messageListeners.addElement(paramMessageListener);
/*  78: 76 */     addDoneListener(paramMessageListener);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void addNewLineListener(NewLineListener paramNewLineListener)
/*  82:    */   {
/*  83: 79 */     if (this.newLineListeners == null) {
/*  84: 79 */       this.newLineListeners = new Vector();
/*  85:    */     }
/*  86: 80 */     this.newLineListeners.addElement(paramNewLineListener);
/*  87: 81 */     addDoneListener(paramNewLineListener);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void addParserListener(ParserListener paramParserListener)
/*  91:    */   {
/*  92: 84 */     if ((paramParserListener instanceof ParserController))
/*  93:    */     {
/*  94: 85 */       ((ParserController)paramParserListener).setParserEventSupport(this);
/*  95: 86 */       this.controller = ((ParserController)paramParserListener);
/*  96:    */     }
/*  97: 88 */     addParserMatchListener(paramParserListener);
/*  98: 89 */     addParserTokenListener(paramParserListener);
/*  99:    */     
/* 100: 91 */     addMessageListener(paramParserListener);
/* 101: 92 */     addTraceListener(paramParserListener);
/* 102: 93 */     addSemanticPredicateListener(paramParserListener);
/* 103: 94 */     addSyntacticPredicateListener(paramParserListener);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void addParserMatchListener(ParserMatchListener paramParserMatchListener)
/* 107:    */   {
/* 108: 97 */     if (this.matchListeners == null) {
/* 109: 97 */       this.matchListeners = new Vector();
/* 110:    */     }
/* 111: 98 */     this.matchListeners.addElement(paramParserMatchListener);
/* 112: 99 */     addDoneListener(paramParserMatchListener);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void addParserTokenListener(ParserTokenListener paramParserTokenListener)
/* 116:    */   {
/* 117:102 */     if (this.tokenListeners == null) {
/* 118:102 */       this.tokenListeners = new Vector();
/* 119:    */     }
/* 120:103 */     this.tokenListeners.addElement(paramParserTokenListener);
/* 121:104 */     addDoneListener(paramParserTokenListener);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void addSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener)
/* 125:    */   {
/* 126:107 */     if (this.semPredListeners == null) {
/* 127:107 */       this.semPredListeners = new Vector();
/* 128:    */     }
/* 129:108 */     this.semPredListeners.addElement(paramSemanticPredicateListener);
/* 130:109 */     addDoneListener(paramSemanticPredicateListener);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void addSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener)
/* 134:    */   {
/* 135:112 */     if (this.synPredListeners == null) {
/* 136:112 */       this.synPredListeners = new Vector();
/* 137:    */     }
/* 138:113 */     this.synPredListeners.addElement(paramSyntacticPredicateListener);
/* 139:114 */     addDoneListener(paramSyntacticPredicateListener);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void addTraceListener(TraceListener paramTraceListener)
/* 143:    */   {
/* 144:117 */     if (this.traceListeners == null) {
/* 145:117 */       this.traceListeners = new Vector();
/* 146:    */     }
/* 147:118 */     this.traceListeners.addElement(paramTraceListener);
/* 148:119 */     addDoneListener(paramTraceListener);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void fireConsume(int paramInt)
/* 152:    */   {
/* 153:122 */     this.tokenEvent.setValues(ParserTokenEvent.CONSUME, 1, paramInt);
/* 154:123 */     fireEvents(0, this.tokenListeners);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void fireDoneParsing()
/* 158:    */   {
/* 159:126 */     this.traceEvent.setValues(TraceEvent.DONE_PARSING, 0, 0, 0);
/* 160:    */     
/* 161:128 */     Hashtable localHashtable = null;
/* 162:    */     
/* 163:130 */     ListenerBase localListenerBase = null;
/* 164:132 */     synchronized (this)
/* 165:    */     {
/* 166:133 */       if (this.doneListeners == null) {
/* 167:133 */         return;
/* 168:    */       }
/* 169:134 */       localHashtable = (Hashtable)this.doneListeners.clone();
/* 170:    */     }
/* 171:137 */     if (localHashtable != null)
/* 172:    */     {
/* 173:138 */       ??? = localHashtable.keys();
/* 174:139 */       while (((Enumeration)???).hasMoreElements())
/* 175:    */       {
/* 176:140 */         localListenerBase = (ListenerBase)((Enumeration)???).nextElement();
/* 177:141 */         fireEvent(15, localListenerBase);
/* 178:    */       }
/* 179:    */     }
/* 180:144 */     if (this.controller != null) {
/* 181:145 */       this.controller.checkBreak();
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void fireEnterRule(int paramInt1, int paramInt2, int paramInt3)
/* 186:    */   {
/* 187:148 */     this.ruleDepth += 1;
/* 188:149 */     this.traceEvent.setValues(TraceEvent.ENTER, paramInt1, paramInt2, paramInt3);
/* 189:150 */     fireEvents(1, this.traceListeners);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void fireEvent(int paramInt, ListenerBase paramListenerBase)
/* 193:    */   {
/* 194:153 */     switch (paramInt)
/* 195:    */     {
/* 196:    */     case 0: 
/* 197:154 */       ((ParserTokenListener)paramListenerBase).parserConsume(this.tokenEvent); break;
/* 198:    */     case 3: 
/* 199:155 */       ((ParserTokenListener)paramListenerBase).parserLA(this.tokenEvent); break;
/* 200:    */     case 1: 
/* 201:157 */       ((TraceListener)paramListenerBase).enterRule(this.traceEvent); break;
/* 202:    */     case 2: 
/* 203:158 */       ((TraceListener)paramListenerBase).exitRule(this.traceEvent); break;
/* 204:    */     case 4: 
/* 205:160 */       ((ParserMatchListener)paramListenerBase).parserMatch(this.matchEvent); break;
/* 206:    */     case 5: 
/* 207:161 */       ((ParserMatchListener)paramListenerBase).parserMatchNot(this.matchEvent); break;
/* 208:    */     case 6: 
/* 209:162 */       ((ParserMatchListener)paramListenerBase).parserMismatch(this.matchEvent); break;
/* 210:    */     case 7: 
/* 211:163 */       ((ParserMatchListener)paramListenerBase).parserMismatchNot(this.matchEvent); break;
/* 212:    */     case 10: 
/* 213:165 */       ((SemanticPredicateListener)paramListenerBase).semanticPredicateEvaluated(this.semPredEvent); break;
/* 214:    */     case 12: 
/* 215:167 */       ((SyntacticPredicateListener)paramListenerBase).syntacticPredicateStarted(this.synPredEvent); break;
/* 216:    */     case 11: 
/* 217:168 */       ((SyntacticPredicateListener)paramListenerBase).syntacticPredicateFailed(this.synPredEvent); break;
/* 218:    */     case 13: 
/* 219:169 */       ((SyntacticPredicateListener)paramListenerBase).syntacticPredicateSucceeded(this.synPredEvent); break;
/* 220:    */     case 8: 
/* 221:171 */       ((MessageListener)paramListenerBase).reportError(this.messageEvent); break;
/* 222:    */     case 9: 
/* 223:172 */       ((MessageListener)paramListenerBase).reportWarning(this.messageEvent); break;
/* 224:    */     case 15: 
/* 225:174 */       paramListenerBase.doneParsing(this.traceEvent); break;
/* 226:    */     case 14: 
/* 227:175 */       ((NewLineListener)paramListenerBase).hitNewLine(this.newLineEvent); break;
/* 228:    */     default: 
/* 229:178 */       throw new IllegalArgumentException("bad type " + paramInt + " for fireEvent()");
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void fireEvents(int paramInt, Vector paramVector)
/* 234:    */   {
/* 235:182 */     ListenerBase localListenerBase = null;
/* 236:184 */     if (paramVector != null) {
/* 237:185 */       for (int i = 0; i < paramVector.size(); i++)
/* 238:    */       {
/* 239:186 */         localListenerBase = (ListenerBase)paramVector.elementAt(i);
/* 240:187 */         fireEvent(paramInt, localListenerBase);
/* 241:    */       }
/* 242:    */     }
/* 243:189 */     if (this.controller != null) {
/* 244:190 */       this.controller.checkBreak();
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void fireExitRule(int paramInt1, int paramInt2, int paramInt3)
/* 249:    */   {
/* 250:193 */     this.traceEvent.setValues(TraceEvent.EXIT, paramInt1, paramInt2, paramInt3);
/* 251:194 */     fireEvents(2, this.traceListeners);
/* 252:195 */     this.ruleDepth -= 1;
/* 253:196 */     if (this.ruleDepth == 0) {
/* 254:197 */       fireDoneParsing();
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void fireLA(int paramInt1, int paramInt2)
/* 259:    */   {
/* 260:200 */     this.tokenEvent.setValues(ParserTokenEvent.LA, paramInt1, paramInt2);
/* 261:201 */     fireEvents(3, this.tokenListeners);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void fireMatch(char paramChar, int paramInt)
/* 265:    */   {
/* 266:204 */     this.matchEvent.setValues(ParserMatchEvent.CHAR, paramChar, new Character(paramChar), null, paramInt, false, true);
/* 267:205 */     fireEvents(4, this.matchListeners);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void fireMatch(char paramChar, BitSet paramBitSet, int paramInt)
/* 271:    */   {
/* 272:208 */     this.matchEvent.setValues(ParserMatchEvent.CHAR_BITSET, paramChar, paramBitSet, null, paramInt, false, true);
/* 273:209 */     fireEvents(4, this.matchListeners);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void fireMatch(char paramChar, String paramString, int paramInt)
/* 277:    */   {
/* 278:212 */     this.matchEvent.setValues(ParserMatchEvent.CHAR_RANGE, paramChar, paramString, null, paramInt, false, true);
/* 279:213 */     fireEvents(4, this.matchListeners);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void fireMatch(int paramInt1, BitSet paramBitSet, String paramString, int paramInt2)
/* 283:    */   {
/* 284:216 */     this.matchEvent.setValues(ParserMatchEvent.BITSET, paramInt1, paramBitSet, paramString, paramInt2, false, true);
/* 285:217 */     fireEvents(4, this.matchListeners);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void fireMatch(int paramInt1, String paramString, int paramInt2)
/* 289:    */   {
/* 290:220 */     this.matchEvent.setValues(ParserMatchEvent.TOKEN, paramInt1, new Integer(paramInt1), paramString, paramInt2, false, true);
/* 291:221 */     fireEvents(4, this.matchListeners);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void fireMatch(String paramString, int paramInt)
/* 295:    */   {
/* 296:224 */     this.matchEvent.setValues(ParserMatchEvent.STRING, 0, paramString, null, paramInt, false, true);
/* 297:225 */     fireEvents(4, this.matchListeners);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void fireMatchNot(char paramChar1, char paramChar2, int paramInt)
/* 301:    */   {
/* 302:228 */     this.matchEvent.setValues(ParserMatchEvent.CHAR, paramChar1, new Character(paramChar2), null, paramInt, true, true);
/* 303:229 */     fireEvents(5, this.matchListeners);
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void fireMatchNot(int paramInt1, int paramInt2, String paramString, int paramInt3)
/* 307:    */   {
/* 308:232 */     this.matchEvent.setValues(ParserMatchEvent.TOKEN, paramInt1, new Integer(paramInt2), paramString, paramInt3, true, true);
/* 309:233 */     fireEvents(5, this.matchListeners);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void fireMismatch(char paramChar1, char paramChar2, int paramInt)
/* 313:    */   {
/* 314:236 */     this.matchEvent.setValues(ParserMatchEvent.CHAR, paramChar1, new Character(paramChar2), null, paramInt, false, false);
/* 315:237 */     fireEvents(6, this.matchListeners);
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void fireMismatch(char paramChar, BitSet paramBitSet, int paramInt)
/* 319:    */   {
/* 320:240 */     this.matchEvent.setValues(ParserMatchEvent.CHAR_BITSET, paramChar, paramBitSet, null, paramInt, false, true);
/* 321:241 */     fireEvents(6, this.matchListeners);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void fireMismatch(char paramChar, String paramString, int paramInt)
/* 325:    */   {
/* 326:244 */     this.matchEvent.setValues(ParserMatchEvent.CHAR_RANGE, paramChar, paramString, null, paramInt, false, true);
/* 327:245 */     fireEvents(6, this.matchListeners);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void fireMismatch(int paramInt1, int paramInt2, String paramString, int paramInt3)
/* 331:    */   {
/* 332:248 */     this.matchEvent.setValues(ParserMatchEvent.TOKEN, paramInt1, new Integer(paramInt2), paramString, paramInt3, false, false);
/* 333:249 */     fireEvents(6, this.matchListeners);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void fireMismatch(int paramInt1, BitSet paramBitSet, String paramString, int paramInt2)
/* 337:    */   {
/* 338:252 */     this.matchEvent.setValues(ParserMatchEvent.BITSET, paramInt1, paramBitSet, paramString, paramInt2, false, true);
/* 339:253 */     fireEvents(6, this.matchListeners);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void fireMismatch(String paramString1, String paramString2, int paramInt)
/* 343:    */   {
/* 344:256 */     this.matchEvent.setValues(ParserMatchEvent.STRING, 0, paramString2, paramString1, paramInt, false, true);
/* 345:257 */     fireEvents(6, this.matchListeners);
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void fireMismatchNot(char paramChar1, char paramChar2, int paramInt)
/* 349:    */   {
/* 350:260 */     this.matchEvent.setValues(ParserMatchEvent.CHAR, paramChar1, new Character(paramChar2), null, paramInt, true, true);
/* 351:261 */     fireEvents(7, this.matchListeners);
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void fireMismatchNot(int paramInt1, int paramInt2, String paramString, int paramInt3)
/* 355:    */   {
/* 356:264 */     this.matchEvent.setValues(ParserMatchEvent.TOKEN, paramInt1, new Integer(paramInt2), paramString, paramInt3, true, true);
/* 357:265 */     fireEvents(7, this.matchListeners);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void fireNewLine(int paramInt)
/* 361:    */   {
/* 362:268 */     this.newLineEvent.setValues(paramInt);
/* 363:269 */     fireEvents(14, this.newLineListeners);
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void fireReportError(Exception paramException)
/* 367:    */   {
/* 368:272 */     this.messageEvent.setValues(MessageEvent.ERROR, paramException.toString());
/* 369:273 */     fireEvents(8, this.messageListeners);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void fireReportError(String paramString)
/* 373:    */   {
/* 374:276 */     this.messageEvent.setValues(MessageEvent.ERROR, paramString);
/* 375:277 */     fireEvents(8, this.messageListeners);
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void fireReportWarning(String paramString)
/* 379:    */   {
/* 380:280 */     this.messageEvent.setValues(MessageEvent.WARNING, paramString);
/* 381:281 */     fireEvents(9, this.messageListeners);
/* 382:    */   }
/* 383:    */   
/* 384:    */   public boolean fireSemanticPredicateEvaluated(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
/* 385:    */   {
/* 386:284 */     this.semPredEvent.setValues(paramInt1, paramInt2, paramBoolean, paramInt3);
/* 387:285 */     fireEvents(10, this.semPredListeners);
/* 388:286 */     return paramBoolean;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void fireSyntacticPredicateFailed(int paramInt)
/* 392:    */   {
/* 393:289 */     this.synPredEvent.setValues(0, paramInt);
/* 394:290 */     fireEvents(11, this.synPredListeners);
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void fireSyntacticPredicateStarted(int paramInt)
/* 398:    */   {
/* 399:293 */     this.synPredEvent.setValues(0, paramInt);
/* 400:294 */     fireEvents(12, this.synPredListeners);
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void fireSyntacticPredicateSucceeded(int paramInt)
/* 404:    */   {
/* 405:297 */     this.synPredEvent.setValues(0, paramInt);
/* 406:298 */     fireEvents(13, this.synPredListeners);
/* 407:    */   }
/* 408:    */   
/* 409:    */   protected void refresh(Vector paramVector)
/* 410:    */   {
/* 411:    */     Vector localVector;
/* 412:302 */     synchronized (paramVector)
/* 413:    */     {
/* 414:303 */       localVector = (Vector)paramVector.clone();
/* 415:    */     }
/* 416:305 */     if (localVector != null) {
/* 417:306 */       for (int i = 0; i < localVector.size(); i++) {
/* 418:307 */         ((ListenerBase)localVector.elementAt(i)).refresh();
/* 419:    */       }
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void refreshListeners()
/* 424:    */   {
/* 425:310 */     refresh(this.matchListeners);
/* 426:311 */     refresh(this.messageListeners);
/* 427:312 */     refresh(this.tokenListeners);
/* 428:313 */     refresh(this.traceListeners);
/* 429:314 */     refresh(this.semPredListeners);
/* 430:315 */     refresh(this.synPredListeners);
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void removeDoneListener(ListenerBase paramListenerBase)
/* 434:    */   {
/* 435:318 */     if (this.doneListeners == null) {
/* 436:318 */       return;
/* 437:    */     }
/* 438:319 */     Integer localInteger = (Integer)this.doneListeners.get(paramListenerBase);
/* 439:320 */     int i = 0;
/* 440:321 */     if (localInteger != null) {
/* 441:322 */       i = localInteger.intValue() - 1;
/* 442:    */     }
/* 443:324 */     if (i == 0) {
/* 444:325 */       this.doneListeners.remove(paramListenerBase);
/* 445:    */     } else {
/* 446:327 */       this.doneListeners.put(paramListenerBase, new Integer(i));
/* 447:    */     }
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void removeMessageListener(MessageListener paramMessageListener)
/* 451:    */   {
/* 452:330 */     if (this.messageListeners != null) {
/* 453:331 */       this.messageListeners.removeElement(paramMessageListener);
/* 454:    */     }
/* 455:332 */     removeDoneListener(paramMessageListener);
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void removeNewLineListener(NewLineListener paramNewLineListener)
/* 459:    */   {
/* 460:335 */     if (this.newLineListeners != null) {
/* 461:336 */       this.newLineListeners.removeElement(paramNewLineListener);
/* 462:    */     }
/* 463:337 */     removeDoneListener(paramNewLineListener);
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void removeParserListener(ParserListener paramParserListener)
/* 467:    */   {
/* 468:340 */     removeParserMatchListener(paramParserListener);
/* 469:341 */     removeMessageListener(paramParserListener);
/* 470:342 */     removeParserTokenListener(paramParserListener);
/* 471:343 */     removeTraceListener(paramParserListener);
/* 472:344 */     removeSemanticPredicateListener(paramParserListener);
/* 473:345 */     removeSyntacticPredicateListener(paramParserListener);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void removeParserMatchListener(ParserMatchListener paramParserMatchListener)
/* 477:    */   {
/* 478:348 */     if (this.matchListeners != null) {
/* 479:349 */       this.matchListeners.removeElement(paramParserMatchListener);
/* 480:    */     }
/* 481:350 */     removeDoneListener(paramParserMatchListener);
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void removeParserTokenListener(ParserTokenListener paramParserTokenListener)
/* 485:    */   {
/* 486:353 */     if (this.tokenListeners != null) {
/* 487:354 */       this.tokenListeners.removeElement(paramParserTokenListener);
/* 488:    */     }
/* 489:355 */     removeDoneListener(paramParserTokenListener);
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void removeSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener)
/* 493:    */   {
/* 494:358 */     if (this.semPredListeners != null) {
/* 495:359 */       this.semPredListeners.removeElement(paramSemanticPredicateListener);
/* 496:    */     }
/* 497:360 */     removeDoneListener(paramSemanticPredicateListener);
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void removeSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener)
/* 501:    */   {
/* 502:363 */     if (this.synPredListeners != null) {
/* 503:364 */       this.synPredListeners.removeElement(paramSyntacticPredicateListener);
/* 504:    */     }
/* 505:365 */     removeDoneListener(paramSyntacticPredicateListener);
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void removeTraceListener(TraceListener paramTraceListener)
/* 509:    */   {
/* 510:368 */     if (this.traceListeners != null) {
/* 511:369 */       this.traceListeners.removeElement(paramTraceListener);
/* 512:    */     }
/* 513:370 */     removeDoneListener(paramTraceListener);
/* 514:    */   }
/* 515:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParserEventSupport
 * JD-Core Version:    0.7.0.1
 */