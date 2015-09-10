/*   1:    */ package org.hibernate.hql.internal.classic;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.LinkedList;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import java.util.StringTokenizer;
/*   9:    */ import org.hibernate.MappingException;
/*  10:    */ import org.hibernate.QueryException;
/*  11:    */ import org.hibernate.engine.internal.JoinSequence;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.internal.util.ReflectHelper;
/*  14:    */ import org.hibernate.internal.util.StringHelper;
/*  15:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  16:    */ import org.hibernate.persister.entity.Queryable;
/*  17:    */ import org.hibernate.sql.JoinFragment;
/*  18:    */ import org.hibernate.type.EntityType;
/*  19:    */ import org.hibernate.type.LiteralType;
/*  20:    */ import org.hibernate.type.Type;
/*  21:    */ import org.hibernate.type.TypeResolver;
/*  22:    */ 
/*  23:    */ public class WhereParser
/*  24:    */   implements Parser
/*  25:    */ {
/*  26:    */   private final PathExpressionParser pathExpressionParser;
/*  27: 71 */   private static final Set EXPRESSION_TERMINATORS = new HashSet();
/*  28: 72 */   private static final Set EXPRESSION_OPENERS = new HashSet();
/*  29: 73 */   private static final Set BOOLEAN_OPERATORS = new HashSet();
/*  30: 74 */   private static final Map NEGATIONS = new HashMap();
/*  31:    */   private boolean betweenSpecialCase;
/*  32:    */   private boolean negated;
/*  33:    */   private boolean inSubselect;
/*  34:    */   private int bracketsSinceSelect;
/*  35:    */   private StringBuffer subselect;
/*  36:    */   private boolean expectingPathContinuation;
/*  37:    */   private int expectingIndex;
/*  38:    */   private LinkedList<Boolean> nots;
/*  39:    */   private LinkedList joins;
/*  40:    */   private LinkedList<Boolean> booleanTests;
/*  41:    */   
/*  42:    */   static
/*  43:    */   {
/*  44: 77 */     EXPRESSION_TERMINATORS.add("and");
/*  45: 78 */     EXPRESSION_TERMINATORS.add("or");
/*  46: 79 */     EXPRESSION_TERMINATORS.add(")");
/*  47:    */     
/*  48:    */ 
/*  49: 82 */     EXPRESSION_OPENERS.add("and");
/*  50: 83 */     EXPRESSION_OPENERS.add("or");
/*  51: 84 */     EXPRESSION_OPENERS.add("(");
/*  52:    */     
/*  53:    */ 
/*  54: 87 */     BOOLEAN_OPERATORS.add("<");
/*  55: 88 */     BOOLEAN_OPERATORS.add("=");
/*  56: 89 */     BOOLEAN_OPERATORS.add(">");
/*  57: 90 */     BOOLEAN_OPERATORS.add("#");
/*  58: 91 */     BOOLEAN_OPERATORS.add("~");
/*  59: 92 */     BOOLEAN_OPERATORS.add("like");
/*  60: 93 */     BOOLEAN_OPERATORS.add("ilike");
/*  61: 94 */     BOOLEAN_OPERATORS.add("regexp");
/*  62: 95 */     BOOLEAN_OPERATORS.add("rlike");
/*  63: 96 */     BOOLEAN_OPERATORS.add("is");
/*  64: 97 */     BOOLEAN_OPERATORS.add("in");
/*  65: 98 */     BOOLEAN_OPERATORS.add("any");
/*  66: 99 */     BOOLEAN_OPERATORS.add("some");
/*  67:100 */     BOOLEAN_OPERATORS.add("all");
/*  68:101 */     BOOLEAN_OPERATORS.add("exists");
/*  69:102 */     BOOLEAN_OPERATORS.add("between");
/*  70:103 */     BOOLEAN_OPERATORS.add("<=");
/*  71:104 */     BOOLEAN_OPERATORS.add(">=");
/*  72:105 */     BOOLEAN_OPERATORS.add("=>");
/*  73:106 */     BOOLEAN_OPERATORS.add("=<");
/*  74:107 */     BOOLEAN_OPERATORS.add("!=");
/*  75:108 */     BOOLEAN_OPERATORS.add("<>");
/*  76:109 */     BOOLEAN_OPERATORS.add("!#");
/*  77:110 */     BOOLEAN_OPERATORS.add("!~");
/*  78:111 */     BOOLEAN_OPERATORS.add("!<");
/*  79:112 */     BOOLEAN_OPERATORS.add("!>");
/*  80:113 */     BOOLEAN_OPERATORS.add("is not");
/*  81:114 */     BOOLEAN_OPERATORS.add("not like");
/*  82:115 */     BOOLEAN_OPERATORS.add("not ilike");
/*  83:116 */     BOOLEAN_OPERATORS.add("not regexp");
/*  84:117 */     BOOLEAN_OPERATORS.add("not rlike");
/*  85:118 */     BOOLEAN_OPERATORS.add("not in");
/*  86:119 */     BOOLEAN_OPERATORS.add("not between");
/*  87:120 */     BOOLEAN_OPERATORS.add("not exists");
/*  88:    */     
/*  89:122 */     NEGATIONS.put("and", "or");
/*  90:123 */     NEGATIONS.put("or", "and");
/*  91:124 */     NEGATIONS.put("<", ">=");
/*  92:125 */     NEGATIONS.put("=", "<>");
/*  93:126 */     NEGATIONS.put(">", "<=");
/*  94:127 */     NEGATIONS.put("#", "!#");
/*  95:128 */     NEGATIONS.put("~", "!~");
/*  96:129 */     NEGATIONS.put("like", "not like");
/*  97:130 */     NEGATIONS.put("ilike", "not ilike");
/*  98:131 */     NEGATIONS.put("regexp", "not regexp");
/*  99:132 */     NEGATIONS.put("rlike", "not rlike");
/* 100:133 */     NEGATIONS.put("is", "is not");
/* 101:134 */     NEGATIONS.put("in", "not in");
/* 102:135 */     NEGATIONS.put("exists", "not exists");
/* 103:136 */     NEGATIONS.put("between", "not between");
/* 104:137 */     NEGATIONS.put("<=", ">");
/* 105:138 */     NEGATIONS.put(">=", "<");
/* 106:139 */     NEGATIONS.put("=>", "<");
/* 107:140 */     NEGATIONS.put("=<", ">");
/* 108:141 */     NEGATIONS.put("!=", "=");
/* 109:142 */     NEGATIONS.put("<>", "=");
/* 110:143 */     NEGATIONS.put("!#", "#");
/* 111:144 */     NEGATIONS.put("!~", "~");
/* 112:145 */     NEGATIONS.put("!<", "<");
/* 113:146 */     NEGATIONS.put("!>", ">");
/* 114:147 */     NEGATIONS.put("is not", "is");
/* 115:148 */     NEGATIONS.put("not like", "like");
/* 116:149 */     NEGATIONS.put("not ilike", "ilike");
/* 117:150 */     NEGATIONS.put("not regexp", "regexp");
/* 118:151 */     NEGATIONS.put("not rlike", "rlike");
/* 119:152 */     NEGATIONS.put("not in", "in");
/* 120:153 */     NEGATIONS.put("not between", "between");
/* 121:154 */     NEGATIONS.put("not exists", "exists");
/* 122:    */   }
/* 123:    */   
/* 124:    */   public WhereParser()
/* 125:    */   {
/* 126: 67 */     this.pathExpressionParser = new PathExpressionParser();
/* 127: 68 */     this.pathExpressionParser.setUseThetaStyleJoin(true);
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
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
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
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
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:176 */     this.betweenSpecialCase = false;
/* 236:177 */     this.negated = false;
/* 237:    */     
/* 238:179 */     this.inSubselect = false;
/* 239:180 */     this.bracketsSinceSelect = 0;
/* 240:    */     
/* 241:    */ 
/* 242:183 */     this.expectingPathContinuation = false;
/* 243:184 */     this.expectingIndex = 0;
/* 244:    */     
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:189 */     this.nots = new LinkedList();
/* 249:190 */     this.joins = new LinkedList();
/* 250:191 */     this.booleanTests = new LinkedList();
/* 251:    */   }
/* 252:    */   
/* 253:    */   private String getElementName(PathExpressionParser.CollectionElement element, QueryTranslatorImpl q)
/* 254:    */     throws QueryException
/* 255:    */   {
/* 256:    */     String name;
/* 257:195 */     if (element.isOneToMany)
/* 258:    */     {
/* 259:196 */       name = element.alias;
/* 260:    */     }
/* 261:    */     else
/* 262:    */     {
/* 263:199 */       Type type = element.elementType;
/* 264:    */       String name;
/* 265:200 */       if (type.isEntityType())
/* 266:    */       {
/* 267:201 */         String entityName = ((EntityType)type).getAssociatedEntityName();
/* 268:202 */         name = this.pathExpressionParser.continueFromManyToMany(entityName, element.elementColumns, q);
/* 269:    */       }
/* 270:    */       else
/* 271:    */       {
/* 272:205 */         throw new QueryException("illegally dereferenced collection element");
/* 273:    */       }
/* 274:    */     }
/* 275:    */     String name;
/* 276:208 */     return name;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void token(String token, QueryTranslatorImpl q)
/* 280:    */     throws QueryException
/* 281:    */   {
/* 282:213 */     String lcToken = token.toLowerCase();
/* 283:216 */     if ((token.equals("[")) && (!this.expectingPathContinuation))
/* 284:    */     {
/* 285:217 */       this.expectingPathContinuation = false;
/* 286:218 */       if (this.expectingIndex == 0) {
/* 287:218 */         throw new QueryException("unexpected [");
/* 288:    */       }
/* 289:219 */       return;
/* 290:    */     }
/* 291:221 */     if (token.equals("]"))
/* 292:    */     {
/* 293:222 */       this.expectingIndex -= 1;
/* 294:223 */       this.expectingPathContinuation = true;
/* 295:224 */       return;
/* 296:    */     }
/* 297:228 */     if (this.expectingPathContinuation)
/* 298:    */     {
/* 299:229 */       boolean pathExpressionContinuesFurther = continuePathExpression(token, q);
/* 300:230 */       if (pathExpressionContinuesFurther) {
/* 301:230 */         return;
/* 302:    */       }
/* 303:    */     }
/* 304:234 */     if ((!this.inSubselect) && ((lcToken.equals("select")) || (lcToken.equals("from"))))
/* 305:    */     {
/* 306:235 */       this.inSubselect = true;
/* 307:236 */       this.subselect = new StringBuffer(20);
/* 308:    */     }
/* 309:238 */     if ((this.inSubselect) && (token.equals(")")))
/* 310:    */     {
/* 311:239 */       this.bracketsSinceSelect -= 1;
/* 312:241 */       if (this.bracketsSinceSelect == -1)
/* 313:    */       {
/* 314:242 */         QueryTranslatorImpl subq = new QueryTranslatorImpl(this.subselect.toString(), q.getEnabledFilters(), q.getFactory());
/* 315:    */         try
/* 316:    */         {
/* 317:248 */           subq.compile(q);
/* 318:    */         }
/* 319:    */         catch (MappingException me)
/* 320:    */         {
/* 321:251 */           throw new QueryException("MappingException occurred compiling subquery", me);
/* 322:    */         }
/* 323:253 */         appendToken(q, subq.getSQLString());
/* 324:254 */         this.inSubselect = false;
/* 325:255 */         this.bracketsSinceSelect = 0;
/* 326:    */       }
/* 327:    */     }
/* 328:258 */     if (this.inSubselect)
/* 329:    */     {
/* 330:259 */       if (token.equals("(")) {
/* 331:259 */         this.bracketsSinceSelect += 1;
/* 332:    */       }
/* 333:260 */       this.subselect.append(token).append(' ');
/* 334:261 */       return;
/* 335:    */     }
/* 336:265 */     specialCasesBefore(lcToken);
/* 337:268 */     if ((!this.betweenSpecialCase) && (EXPRESSION_TERMINATORS.contains(lcToken))) {
/* 338:269 */       closeExpression(q, lcToken);
/* 339:    */     }
/* 340:273 */     if (BOOLEAN_OPERATORS.contains(lcToken))
/* 341:    */     {
/* 342:274 */       this.booleanTests.removeLast();
/* 343:275 */       this.booleanTests.addLast(Boolean.TRUE);
/* 344:    */     }
/* 345:278 */     if (lcToken.equals("not"))
/* 346:    */     {
/* 347:279 */       this.nots.addLast(Boolean.valueOf(!((Boolean)this.nots.removeLast()).booleanValue()));
/* 348:280 */       this.negated = (!this.negated);
/* 349:281 */       return;
/* 350:    */     }
/* 351:285 */     doToken(token, q);
/* 352:288 */     if ((!this.betweenSpecialCase) && (EXPRESSION_OPENERS.contains(lcToken))) {
/* 353:289 */       openExpression(q, lcToken);
/* 354:    */     }
/* 355:293 */     specialCasesAfter(lcToken);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void start(QueryTranslatorImpl q)
/* 359:    */     throws QueryException
/* 360:    */   {
/* 361:298 */     token("(", q);
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void end(QueryTranslatorImpl q)
/* 365:    */     throws QueryException
/* 366:    */   {
/* 367:302 */     if (this.expectingPathContinuation)
/* 368:    */     {
/* 369:303 */       this.expectingPathContinuation = false;
/* 370:304 */       PathExpressionParser.CollectionElement element = this.pathExpressionParser.lastCollectionElement();
/* 371:305 */       if (element.elementColumns.length != 1) {
/* 372:305 */         throw new QueryException("path expression ended in composite collection element");
/* 373:    */       }
/* 374:306 */       appendToken(q, element.elementColumns[0]);
/* 375:307 */       addToCurrentJoin(element);
/* 376:    */     }
/* 377:309 */     token(")", q);
/* 378:    */   }
/* 379:    */   
/* 380:    */   private void closeExpression(QueryTranslatorImpl q, String lcToken)
/* 381:    */   {
/* 382:313 */     if (((Boolean)this.booleanTests.removeLast()).booleanValue())
/* 383:    */     {
/* 384:315 */       if (this.booleanTests.size() > 0)
/* 385:    */       {
/* 386:317 */         this.booleanTests.removeLast();
/* 387:318 */         this.booleanTests.addLast(Boolean.TRUE);
/* 388:    */       }
/* 389:322 */       appendToken(q, this.joins.removeLast().toString());
/* 390:    */     }
/* 391:    */     else
/* 392:    */     {
/* 393:326 */       StringBuffer join = (StringBuffer)this.joins.removeLast();
/* 394:327 */       ((StringBuffer)this.joins.getLast()).append(join.toString());
/* 395:    */     }
/* 396:330 */     if (((Boolean)this.nots.removeLast()).booleanValue()) {
/* 397:330 */       this.negated = (!this.negated);
/* 398:    */     }
/* 399:332 */     if (!")".equals(lcToken)) {
/* 400:332 */       appendToken(q, ")");
/* 401:    */     }
/* 402:    */   }
/* 403:    */   
/* 404:    */   private void openExpression(QueryTranslatorImpl q, String lcToken)
/* 405:    */   {
/* 406:336 */     this.nots.addLast(Boolean.FALSE);
/* 407:337 */     this.booleanTests.addLast(Boolean.FALSE);
/* 408:338 */     this.joins.addLast(new StringBuffer());
/* 409:339 */     if (!"(".equals(lcToken)) {
/* 410:339 */       appendToken(q, "(");
/* 411:    */     }
/* 412:    */   }
/* 413:    */   
/* 414:    */   private void preprocess(String token, QueryTranslatorImpl q)
/* 415:    */     throws QueryException
/* 416:    */   {
/* 417:345 */     String[] tokens = StringHelper.split(".", token, true);
/* 418:346 */     if ((tokens.length > 5) && (("elements".equals(tokens[(tokens.length - 1)])) || ("indices".equals(tokens[(tokens.length - 1)]))))
/* 419:    */     {
/* 420:351 */       this.pathExpressionParser.start(q);
/* 421:352 */       for (int i = 0; i < tokens.length - 3; i++) {
/* 422:353 */         this.pathExpressionParser.token(tokens[i], q);
/* 423:    */       }
/* 424:355 */       this.pathExpressionParser.token(null, q);
/* 425:356 */       this.pathExpressionParser.end(q);
/* 426:357 */       addJoin(this.pathExpressionParser.getWhereJoin(), q);
/* 427:358 */       this.pathExpressionParser.ignoreInitialJoin();
/* 428:    */     }
/* 429:    */   }
/* 430:    */   
/* 431:    */   private void doPathExpression(String token, QueryTranslatorImpl q)
/* 432:    */     throws QueryException
/* 433:    */   {
/* 434:364 */     preprocess(token, q);
/* 435:    */     
/* 436:366 */     StringTokenizer tokens = new StringTokenizer(token, ".", true);
/* 437:367 */     this.pathExpressionParser.start(q);
/* 438:368 */     while (tokens.hasMoreTokens()) {
/* 439:369 */       this.pathExpressionParser.token(tokens.nextToken(), q);
/* 440:    */     }
/* 441:371 */     this.pathExpressionParser.end(q);
/* 442:372 */     if (this.pathExpressionParser.isCollectionValued())
/* 443:    */     {
/* 444:373 */       openExpression(q, "");
/* 445:374 */       appendToken(q, this.pathExpressionParser.getCollectionSubquery(q.getEnabledFilters()));
/* 446:375 */       closeExpression(q, "");
/* 447:    */       
/* 448:377 */       q.addQuerySpaces(q.getCollectionPersister(this.pathExpressionParser.getCollectionRole()).getCollectionSpaces());
/* 449:    */     }
/* 450:380 */     else if (this.pathExpressionParser.isExpectingCollectionIndex())
/* 451:    */     {
/* 452:381 */       this.expectingIndex += 1;
/* 453:    */     }
/* 454:    */     else
/* 455:    */     {
/* 456:384 */       addJoin(this.pathExpressionParser.getWhereJoin(), q);
/* 457:385 */       appendToken(q, this.pathExpressionParser.getWhereColumn());
/* 458:    */     }
/* 459:    */   }
/* 460:    */   
/* 461:    */   private void addJoin(JoinSequence joinSequence, QueryTranslatorImpl q)
/* 462:    */     throws QueryException
/* 463:    */   {
/* 464:393 */     q.addFromJoinOnly(this.pathExpressionParser.getName(), joinSequence);
/* 465:    */     try
/* 466:    */     {
/* 467:395 */       addToCurrentJoin(joinSequence.toJoinFragment(q.getEnabledFilters(), true).toWhereFragmentString());
/* 468:    */     }
/* 469:    */     catch (MappingException me)
/* 470:    */     {
/* 471:398 */       throw new QueryException(me);
/* 472:    */     }
/* 473:    */   }
/* 474:    */   
/* 475:    */   private void doToken(String token, QueryTranslatorImpl q)
/* 476:    */     throws QueryException
/* 477:    */   {
/* 478:403 */     if (q.isName(StringHelper.root(token)))
/* 479:    */     {
/* 480:404 */       doPathExpression(q.unalias(token), q);
/* 481:    */     }
/* 482:406 */     else if (token.startsWith(":"))
/* 483:    */     {
/* 484:407 */       q.addNamedParameter(token.substring(1));
/* 485:408 */       appendToken(q, "?");
/* 486:    */     }
/* 487:    */     else
/* 488:    */     {
/* 489:411 */       Queryable persister = q.getEntityPersisterUsingImports(token);
/* 490:412 */       if (persister != null)
/* 491:    */       {
/* 492:413 */         String discrim = persister.getDiscriminatorSQLValue();
/* 493:414 */         if (("null".equals(discrim)) || ("not null".equals(discrim))) {
/* 494:415 */           throw new QueryException("subclass test not allowed for null or not null discriminator");
/* 495:    */         }
/* 496:418 */         appendToken(q, discrim);
/* 497:    */       }
/* 498:    */       else
/* 499:    */       {
/* 500:    */         Object constant;
/* 501:423 */         if ((token.indexOf('.') > -1) && ((constant = ReflectHelper.getConstantValue(token)) != null))
/* 502:    */         {
/* 503:    */           Type type;
/* 504:    */           try
/* 505:    */           {
/* 506:429 */             type = q.getFactory().getTypeResolver().heuristicType(constant.getClass().getName());
/* 507:    */           }
/* 508:    */           catch (MappingException me)
/* 509:    */           {
/* 510:432 */             throw new QueryException(me);
/* 511:    */           }
/* 512:434 */           if (type == null) {
/* 513:434 */             throw new QueryException("Could not determine type of: " + token);
/* 514:    */           }
/* 515:    */           try
/* 516:    */           {
/* 517:436 */             appendToken(q, ((LiteralType)type).objectToSQLString(constant, q.getFactory().getDialect()));
/* 518:    */           }
/* 519:    */           catch (Exception e)
/* 520:    */           {
/* 521:439 */             throw new QueryException("Could not format constant value to SQL literal: " + token, e);
/* 522:    */           }
/* 523:    */         }
/* 524:    */         else
/* 525:    */         {
/* 526:444 */           String negatedToken = this.negated ? (String)NEGATIONS.get(token.toLowerCase()) : null;
/* 527:445 */           if ((negatedToken != null) && ((!this.betweenSpecialCase) || (!"or".equals(negatedToken)))) {
/* 528:446 */             appendToken(q, negatedToken);
/* 529:    */           } else {
/* 530:449 */             appendToken(q, token);
/* 531:    */           }
/* 532:    */         }
/* 533:    */       }
/* 534:    */     }
/* 535:    */   }
/* 536:    */   
/* 537:    */   private void addToCurrentJoin(String sql)
/* 538:    */   {
/* 539:457 */     ((StringBuffer)this.joins.getLast()).append(sql);
/* 540:    */   }
/* 541:    */   
/* 542:    */   private void addToCurrentJoin(PathExpressionParser.CollectionElement ce)
/* 543:    */     throws QueryException
/* 544:    */   {
/* 545:    */     try
/* 546:    */     {
/* 547:463 */       addToCurrentJoin(ce.joinSequence.toJoinFragment().toWhereFragmentString() + ce.indexValue.toString());
/* 548:    */     }
/* 549:    */     catch (MappingException me)
/* 550:    */     {
/* 551:466 */       throw new QueryException(me);
/* 552:    */     }
/* 553:    */   }
/* 554:    */   
/* 555:    */   private void specialCasesBefore(String lcToken)
/* 556:    */   {
/* 557:471 */     if ((lcToken.equals("between")) || (lcToken.equals("not between"))) {
/* 558:472 */       this.betweenSpecialCase = true;
/* 559:    */     }
/* 560:    */   }
/* 561:    */   
/* 562:    */   private void specialCasesAfter(String lcToken)
/* 563:    */   {
/* 564:477 */     if ((this.betweenSpecialCase) && (lcToken.equals("and"))) {
/* 565:478 */       this.betweenSpecialCase = false;
/* 566:    */     }
/* 567:    */   }
/* 568:    */   
/* 569:    */   void appendToken(QueryTranslatorImpl q, String token)
/* 570:    */   {
/* 571:483 */     if (this.expectingIndex > 0) {
/* 572:484 */       this.pathExpressionParser.setLastCollectionElementIndexValue(token);
/* 573:    */     } else {
/* 574:487 */       q.appendWhereToken(token);
/* 575:    */     }
/* 576:    */   }
/* 577:    */   
/* 578:    */   private boolean continuePathExpression(String token, QueryTranslatorImpl q)
/* 579:    */     throws QueryException
/* 580:    */   {
/* 581:493 */     this.expectingPathContinuation = false;
/* 582:    */     
/* 583:495 */     PathExpressionParser.CollectionElement element = this.pathExpressionParser.lastCollectionElement();
/* 584:497 */     if (token.startsWith("."))
/* 585:    */     {
/* 586:499 */       doPathExpression(getElementName(element, q) + token, q);
/* 587:    */       
/* 588:501 */       addToCurrentJoin(element);
/* 589:502 */       return true;
/* 590:    */     }
/* 591:507 */     if (element.elementColumns.length != 1) {
/* 592:508 */       throw new QueryException("path expression ended in composite collection element");
/* 593:    */     }
/* 594:510 */     appendToken(q, element.elementColumns[0]);
/* 595:511 */     addToCurrentJoin(element);
/* 596:512 */     return false;
/* 597:    */   }
/* 598:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.WhereParser
 * JD-Core Version:    0.7.0.1
 */