/*   1:    */ package org.apache.james.mime4j.field.address.parser;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import java.util.Vector;
/*   8:    */ 
/*   9:    */ public class AddressListParser
/*  10:    */   implements AddressListParserTreeConstants, AddressListParserConstants
/*  11:    */ {
/*  12: 23 */   protected JJTAddressListParserState jjtree = new JJTAddressListParserState();
/*  13:    */   public AddressListParserTokenManager token_source;
/*  14:    */   SimpleCharStream jj_input_stream;
/*  15:    */   public Token token;
/*  16:    */   public Token jj_nt;
/*  17:    */   private int jj_ntk;
/*  18:    */   private Token jj_scanpos;
/*  19:    */   private Token jj_lastpos;
/*  20:    */   private int jj_la;
/*  21:    */   
/*  22:    */   public static void main(String[] args)
/*  23:    */     throws ParseException
/*  24:    */   {
/*  25:    */     try
/*  26:    */     {
/*  27:    */       for (;;)
/*  28:    */       {
/*  29: 26 */         AddressListParser parser = new AddressListParser(System.in);
/*  30: 27 */         parser.parseLine();
/*  31: 28 */         ((SimpleNode)parser.jjtree.rootNode()).dump("> ");
/*  32:    */       }
/*  33: 31 */       return;
/*  34:    */     }
/*  35:    */     catch (Exception x)
/*  36:    */     {
/*  37: 30 */       x.printStackTrace();
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ASTaddress_list parseAddressList()
/*  42:    */     throws ParseException
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46: 38 */       parseAddressList0();
/*  47: 39 */       return (ASTaddress_list)this.jjtree.rootNode();
/*  48:    */     }
/*  49:    */     catch (TokenMgrError tme)
/*  50:    */     {
/*  51: 41 */       throw new ParseException(tme.getMessage());
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ASTaddress parseAddress()
/*  56:    */     throws ParseException
/*  57:    */   {
/*  58:    */     try
/*  59:    */     {
/*  60: 47 */       parseAddress0();
/*  61: 48 */       return (ASTaddress)this.jjtree.rootNode();
/*  62:    */     }
/*  63:    */     catch (TokenMgrError tme)
/*  64:    */     {
/*  65: 50 */       throw new ParseException(tme.getMessage());
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ASTmailbox parseMailbox()
/*  70:    */     throws ParseException
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74: 56 */       parseMailbox0();
/*  75: 57 */       return (ASTmailbox)this.jjtree.rootNode();
/*  76:    */     }
/*  77:    */     catch (TokenMgrError tme)
/*  78:    */     {
/*  79: 59 */       throw new ParseException(tme.getMessage());
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   void jjtreeOpenNodeScope(Node n)
/*  84:    */   {
/*  85: 64 */     ((SimpleNode)n).firstToken = getToken(1);
/*  86:    */   }
/*  87:    */   
/*  88:    */   void jjtreeCloseNodeScope(Node n)
/*  89:    */   {
/*  90: 68 */     ((SimpleNode)n).lastToken = getToken(0);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public final void parseLine()
/*  94:    */     throws ParseException
/*  95:    */   {
/*  96: 72 */     address_list();
/*  97: 73 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  98:    */     {
/*  99:    */     case 1: 
/* 100: 75 */       jj_consume_token(1);
/* 101: 76 */       break;
/* 102:    */     default: 
/* 103: 78 */       this.jj_la1[0] = this.jj_gen;
/* 104:    */     }
/* 105: 81 */     jj_consume_token(2);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public final void parseAddressList0()
/* 109:    */     throws ParseException
/* 110:    */   {
/* 111: 85 */     address_list();
/* 112: 86 */     jj_consume_token(0);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public final void parseAddress0()
/* 116:    */     throws ParseException
/* 117:    */   {
/* 118: 90 */     address();
/* 119: 91 */     jj_consume_token(0);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final void parseMailbox0()
/* 123:    */     throws ParseException
/* 124:    */   {
/* 125: 95 */     mailbox();
/* 126: 96 */     jj_consume_token(0);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public final void address_list()
/* 130:    */     throws ParseException
/* 131:    */   {
/* 132:101 */     ASTaddress_list jjtn000 = new ASTaddress_list(1);
/* 133:102 */     boolean jjtc000 = true;
/* 134:103 */     this.jjtree.openNodeScope(jjtn000);
/* 135:104 */     jjtreeOpenNodeScope(jjtn000);
/* 136:    */     try
/* 137:    */     {
/* 138:106 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 139:    */       {
/* 140:    */       case 6: 
/* 141:    */       case 14: 
/* 142:    */       case 31: 
/* 143:110 */         address();
/* 144:111 */         break;
/* 145:    */       default: 
/* 146:113 */         this.jj_la1[1] = this.jj_gen;
/* 147:    */       }
/* 148:    */       for (;;)
/* 149:    */       {
/* 150:118 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 151:    */         {
/* 152:    */         case 3: 
/* 153:    */           break;
/* 154:    */         default: 
/* 155:123 */           this.jj_la1[2] = this.jj_gen;
/* 156:124 */           break;
/* 157:    */         }
/* 158:126 */         jj_consume_token(3);
/* 159:127 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 160:    */         {
/* 161:    */         case 6: 
/* 162:    */         case 14: 
/* 163:    */         case 31: 
/* 164:131 */           address();
/* 165:132 */           break;
/* 166:    */         default: 
/* 167:134 */           this.jj_la1[3] = this.jj_gen;
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:    */     catch (Throwable jjte000)
/* 172:    */     {
/* 173:139 */       if (jjtc000)
/* 174:    */       {
/* 175:140 */         this.jjtree.clearNodeScope(jjtn000);
/* 176:141 */         jjtc000 = false;
/* 177:    */       }
/* 178:    */       else
/* 179:    */       {
/* 180:143 */         this.jjtree.popNode();
/* 181:    */       }
/* 182:145 */       if ((jjte000 instanceof RuntimeException)) {
/* 183:146 */         throw ((RuntimeException)jjte000);
/* 184:    */       }
/* 185:148 */       if ((jjte000 instanceof ParseException)) {
/* 186:149 */         throw ((ParseException)jjte000);
/* 187:    */       }
/* 188:151 */       throw ((Error)jjte000);
/* 189:    */     }
/* 190:    */     finally
/* 191:    */     {
/* 192:153 */       if (jjtc000)
/* 193:    */       {
/* 194:154 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 195:155 */         jjtreeCloseNodeScope(jjtn000);
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public final void address()
/* 201:    */     throws ParseException
/* 202:    */   {
/* 203:162 */     ASTaddress jjtn000 = new ASTaddress(2);
/* 204:163 */     boolean jjtc000 = true;
/* 205:164 */     this.jjtree.openNodeScope(jjtn000);
/* 206:165 */     jjtreeOpenNodeScope(jjtn000);
/* 207:    */     try
/* 208:    */     {
/* 209:167 */       if (jj_2_1(2147483647)) {
/* 210:168 */         addr_spec();
/* 211:    */       } else {
/* 212:170 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 213:    */         {
/* 214:    */         case 6: 
/* 215:172 */           angle_addr();
/* 216:173 */           break;
/* 217:    */         case 14: 
/* 218:    */         case 31: 
/* 219:176 */           phrase();
/* 220:177 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 221:    */           {
/* 222:    */           case 4: 
/* 223:179 */             group_body();
/* 224:180 */             break;
/* 225:    */           case 6: 
/* 226:182 */             angle_addr();
/* 227:183 */             break;
/* 228:    */           default: 
/* 229:185 */             this.jj_la1[4] = this.jj_gen;
/* 230:186 */             jj_consume_token(-1);
/* 231:187 */             throw new ParseException();
/* 232:    */           }
/* 233:    */           break;
/* 234:    */         default: 
/* 235:191 */           this.jj_la1[5] = this.jj_gen;
/* 236:192 */           jj_consume_token(-1);
/* 237:193 */           throw new ParseException();
/* 238:    */         }
/* 239:    */       }
/* 240:    */     }
/* 241:    */     catch (Throwable jjte000)
/* 242:    */     {
/* 243:197 */       if (jjtc000)
/* 244:    */       {
/* 245:198 */         this.jjtree.clearNodeScope(jjtn000);
/* 246:199 */         jjtc000 = false;
/* 247:    */       }
/* 248:    */       else
/* 249:    */       {
/* 250:201 */         this.jjtree.popNode();
/* 251:    */       }
/* 252:203 */       if ((jjte000 instanceof RuntimeException)) {
/* 253:204 */         throw ((RuntimeException)jjte000);
/* 254:    */       }
/* 255:206 */       if ((jjte000 instanceof ParseException)) {
/* 256:207 */         throw ((ParseException)jjte000);
/* 257:    */       }
/* 258:209 */       throw ((Error)jjte000);
/* 259:    */     }
/* 260:    */     finally
/* 261:    */     {
/* 262:211 */       if (jjtc000)
/* 263:    */       {
/* 264:212 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 265:213 */         jjtreeCloseNodeScope(jjtn000);
/* 266:    */       }
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public final void mailbox()
/* 271:    */     throws ParseException
/* 272:    */   {
/* 273:220 */     ASTmailbox jjtn000 = new ASTmailbox(3);
/* 274:221 */     boolean jjtc000 = true;
/* 275:222 */     this.jjtree.openNodeScope(jjtn000);
/* 276:223 */     jjtreeOpenNodeScope(jjtn000);
/* 277:    */     try
/* 278:    */     {
/* 279:225 */       if (jj_2_2(2147483647)) {
/* 280:226 */         addr_spec();
/* 281:    */       } else {
/* 282:228 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 283:    */         {
/* 284:    */         case 6: 
/* 285:230 */           angle_addr();
/* 286:231 */           break;
/* 287:    */         case 14: 
/* 288:    */         case 31: 
/* 289:234 */           name_addr();
/* 290:235 */           break;
/* 291:    */         default: 
/* 292:237 */           this.jj_la1[6] = this.jj_gen;
/* 293:238 */           jj_consume_token(-1);
/* 294:239 */           throw new ParseException();
/* 295:    */         }
/* 296:    */       }
/* 297:    */     }
/* 298:    */     catch (Throwable jjte000)
/* 299:    */     {
/* 300:243 */       if (jjtc000)
/* 301:    */       {
/* 302:244 */         this.jjtree.clearNodeScope(jjtn000);
/* 303:245 */         jjtc000 = false;
/* 304:    */       }
/* 305:    */       else
/* 306:    */       {
/* 307:247 */         this.jjtree.popNode();
/* 308:    */       }
/* 309:249 */       if ((jjte000 instanceof RuntimeException)) {
/* 310:250 */         throw ((RuntimeException)jjte000);
/* 311:    */       }
/* 312:252 */       if ((jjte000 instanceof ParseException)) {
/* 313:253 */         throw ((ParseException)jjte000);
/* 314:    */       }
/* 315:255 */       throw ((Error)jjte000);
/* 316:    */     }
/* 317:    */     finally
/* 318:    */     {
/* 319:257 */       if (jjtc000)
/* 320:    */       {
/* 321:258 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 322:259 */         jjtreeCloseNodeScope(jjtn000);
/* 323:    */       }
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   public final void name_addr()
/* 328:    */     throws ParseException
/* 329:    */   {
/* 330:266 */     ASTname_addr jjtn000 = new ASTname_addr(4);
/* 331:267 */     boolean jjtc000 = true;
/* 332:268 */     this.jjtree.openNodeScope(jjtn000);
/* 333:269 */     jjtreeOpenNodeScope(jjtn000);
/* 334:    */     try
/* 335:    */     {
/* 336:271 */       phrase();
/* 337:272 */       angle_addr();
/* 338:    */     }
/* 339:    */     catch (Throwable jjte000)
/* 340:    */     {
/* 341:274 */       if (jjtc000)
/* 342:    */       {
/* 343:275 */         this.jjtree.clearNodeScope(jjtn000);
/* 344:276 */         jjtc000 = false;
/* 345:    */       }
/* 346:    */       else
/* 347:    */       {
/* 348:278 */         this.jjtree.popNode();
/* 349:    */       }
/* 350:280 */       if ((jjte000 instanceof RuntimeException)) {
/* 351:281 */         throw ((RuntimeException)jjte000);
/* 352:    */       }
/* 353:283 */       if ((jjte000 instanceof ParseException)) {
/* 354:284 */         throw ((ParseException)jjte000);
/* 355:    */       }
/* 356:286 */       throw ((Error)jjte000);
/* 357:    */     }
/* 358:    */     finally
/* 359:    */     {
/* 360:288 */       if (jjtc000)
/* 361:    */       {
/* 362:289 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 363:290 */         jjtreeCloseNodeScope(jjtn000);
/* 364:    */       }
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   public final void group_body()
/* 369:    */     throws ParseException
/* 370:    */   {
/* 371:297 */     ASTgroup_body jjtn000 = new ASTgroup_body(5);
/* 372:298 */     boolean jjtc000 = true;
/* 373:299 */     this.jjtree.openNodeScope(jjtn000);
/* 374:300 */     jjtreeOpenNodeScope(jjtn000);
/* 375:    */     try
/* 376:    */     {
/* 377:302 */       jj_consume_token(4);
/* 378:303 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 379:    */       {
/* 380:    */       case 6: 
/* 381:    */       case 14: 
/* 382:    */       case 31: 
/* 383:307 */         mailbox();
/* 384:308 */         break;
/* 385:    */       default: 
/* 386:310 */         this.jj_la1[7] = this.jj_gen;
/* 387:    */       }
/* 388:    */       for (;;)
/* 389:    */       {
/* 390:315 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 391:    */         {
/* 392:    */         case 3: 
/* 393:    */           break;
/* 394:    */         default: 
/* 395:320 */           this.jj_la1[8] = this.jj_gen;
/* 396:321 */           break;
/* 397:    */         }
/* 398:323 */         jj_consume_token(3);
/* 399:324 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 400:    */         {
/* 401:    */         case 6: 
/* 402:    */         case 14: 
/* 403:    */         case 31: 
/* 404:328 */           mailbox();
/* 405:329 */           break;
/* 406:    */         default: 
/* 407:331 */           this.jj_la1[9] = this.jj_gen;
/* 408:    */         }
/* 409:    */       }
/* 410:335 */       jj_consume_token(5);
/* 411:    */     }
/* 412:    */     catch (Throwable jjte000)
/* 413:    */     {
/* 414:337 */       if (jjtc000)
/* 415:    */       {
/* 416:338 */         this.jjtree.clearNodeScope(jjtn000);
/* 417:339 */         jjtc000 = false;
/* 418:    */       }
/* 419:    */       else
/* 420:    */       {
/* 421:341 */         this.jjtree.popNode();
/* 422:    */       }
/* 423:343 */       if ((jjte000 instanceof RuntimeException)) {
/* 424:344 */         throw ((RuntimeException)jjte000);
/* 425:    */       }
/* 426:346 */       if ((jjte000 instanceof ParseException)) {
/* 427:347 */         throw ((ParseException)jjte000);
/* 428:    */       }
/* 429:349 */       throw ((Error)jjte000);
/* 430:    */     }
/* 431:    */     finally
/* 432:    */     {
/* 433:351 */       if (jjtc000)
/* 434:    */       {
/* 435:352 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 436:353 */         jjtreeCloseNodeScope(jjtn000);
/* 437:    */       }
/* 438:    */     }
/* 439:    */   }
/* 440:    */   
/* 441:    */   public final void angle_addr()
/* 442:    */     throws ParseException
/* 443:    */   {
/* 444:360 */     ASTangle_addr jjtn000 = new ASTangle_addr(6);
/* 445:361 */     boolean jjtc000 = true;
/* 446:362 */     this.jjtree.openNodeScope(jjtn000);
/* 447:363 */     jjtreeOpenNodeScope(jjtn000);
/* 448:    */     try
/* 449:    */     {
/* 450:365 */       jj_consume_token(6);
/* 451:366 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 452:    */       {
/* 453:    */       case 8: 
/* 454:368 */         route();
/* 455:369 */         break;
/* 456:    */       default: 
/* 457:371 */         this.jj_la1[10] = this.jj_gen;
/* 458:    */       }
/* 459:374 */       addr_spec();
/* 460:375 */       jj_consume_token(7);
/* 461:    */     }
/* 462:    */     catch (Throwable jjte000)
/* 463:    */     {
/* 464:377 */       if (jjtc000)
/* 465:    */       {
/* 466:378 */         this.jjtree.clearNodeScope(jjtn000);
/* 467:379 */         jjtc000 = false;
/* 468:    */       }
/* 469:    */       else
/* 470:    */       {
/* 471:381 */         this.jjtree.popNode();
/* 472:    */       }
/* 473:383 */       if ((jjte000 instanceof RuntimeException)) {
/* 474:384 */         throw ((RuntimeException)jjte000);
/* 475:    */       }
/* 476:386 */       if ((jjte000 instanceof ParseException)) {
/* 477:387 */         throw ((ParseException)jjte000);
/* 478:    */       }
/* 479:389 */       throw ((Error)jjte000);
/* 480:    */     }
/* 481:    */     finally
/* 482:    */     {
/* 483:391 */       if (jjtc000)
/* 484:    */       {
/* 485:392 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 486:393 */         jjtreeCloseNodeScope(jjtn000);
/* 487:    */       }
/* 488:    */     }
/* 489:    */   }
/* 490:    */   
/* 491:    */   public final void route()
/* 492:    */     throws ParseException
/* 493:    */   {
/* 494:400 */     ASTroute jjtn000 = new ASTroute(7);
/* 495:401 */     boolean jjtc000 = true;
/* 496:402 */     this.jjtree.openNodeScope(jjtn000);
/* 497:403 */     jjtreeOpenNodeScope(jjtn000);
/* 498:    */     try
/* 499:    */     {
/* 500:405 */       jj_consume_token(8);
/* 501:406 */       domain();
/* 502:    */       for (;;)
/* 503:    */       {
/* 504:409 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 505:    */         {
/* 506:    */         case 3: 
/* 507:    */         case 8: 
/* 508:    */           break;
/* 509:    */         default: 
/* 510:415 */           this.jj_la1[11] = this.jj_gen;
/* 511:416 */           break;
/* 512:    */         }
/* 513:    */         for (;;)
/* 514:    */         {
/* 515:420 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 516:    */           {
/* 517:    */           case 3: 
/* 518:    */             break;
/* 519:    */           default: 
/* 520:425 */             this.jj_la1[12] = this.jj_gen;
/* 521:426 */             break;
/* 522:    */           }
/* 523:428 */           jj_consume_token(3);
/* 524:    */         }
/* 525:430 */         jj_consume_token(8);
/* 526:431 */         domain();
/* 527:    */       }
/* 528:433 */       jj_consume_token(4);
/* 529:    */     }
/* 530:    */     catch (Throwable jjte000)
/* 531:    */     {
/* 532:435 */       if (jjtc000)
/* 533:    */       {
/* 534:436 */         this.jjtree.clearNodeScope(jjtn000);
/* 535:437 */         jjtc000 = false;
/* 536:    */       }
/* 537:    */       else
/* 538:    */       {
/* 539:439 */         this.jjtree.popNode();
/* 540:    */       }
/* 541:441 */       if ((jjte000 instanceof RuntimeException)) {
/* 542:442 */         throw ((RuntimeException)jjte000);
/* 543:    */       }
/* 544:444 */       if ((jjte000 instanceof ParseException)) {
/* 545:445 */         throw ((ParseException)jjte000);
/* 546:    */       }
/* 547:447 */       throw ((Error)jjte000);
/* 548:    */     }
/* 549:    */     finally
/* 550:    */     {
/* 551:449 */       if (jjtc000)
/* 552:    */       {
/* 553:450 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 554:451 */         jjtreeCloseNodeScope(jjtn000);
/* 555:    */       }
/* 556:    */     }
/* 557:    */   }
/* 558:    */   
/* 559:    */   public final void phrase()
/* 560:    */     throws ParseException
/* 561:    */   {
/* 562:458 */     ASTphrase jjtn000 = new ASTphrase(8);
/* 563:459 */     boolean jjtc000 = true;
/* 564:460 */     this.jjtree.openNodeScope(jjtn000);
/* 565:461 */     jjtreeOpenNodeScope(jjtn000);
/* 566:    */     try
/* 567:    */     {
/* 568:    */       for (;;)
/* 569:    */       {
/* 570:465 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 571:    */         {
/* 572:    */         case 14: 
/* 573:467 */           jj_consume_token(14);
/* 574:468 */           break;
/* 575:    */         case 31: 
/* 576:470 */           jj_consume_token(31);
/* 577:471 */           break;
/* 578:    */         default: 
/* 579:473 */           this.jj_la1[13] = this.jj_gen;
/* 580:474 */           jj_consume_token(-1);
/* 581:475 */           throw new ParseException();
/* 582:    */         }
/* 583:477 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 584:    */         {
/* 585:    */         }
/* 586:    */       }
/* 587:483 */       this.jj_la1[14] = this.jj_gen;
/* 588:    */     }
/* 589:    */     finally
/* 590:    */     {
/* 591:488 */       if (jjtc000)
/* 592:    */       {
/* 593:489 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 594:490 */         jjtreeCloseNodeScope(jjtn000);
/* 595:    */       }
/* 596:    */     }
/* 597:    */   }
/* 598:    */   
/* 599:    */   public final void addr_spec()
/* 600:    */     throws ParseException
/* 601:    */   {
/* 602:497 */     ASTaddr_spec jjtn000 = new ASTaddr_spec(9);
/* 603:498 */     boolean jjtc000 = true;
/* 604:499 */     this.jjtree.openNodeScope(jjtn000);
/* 605:500 */     jjtreeOpenNodeScope(jjtn000);
/* 606:    */     try
/* 607:    */     {
/* 608:502 */       local_part();
/* 609:503 */       jj_consume_token(8);
/* 610:504 */       domain();
/* 611:    */     }
/* 612:    */     catch (Throwable jjte000)
/* 613:    */     {
/* 614:506 */       if (jjtc000)
/* 615:    */       {
/* 616:507 */         this.jjtree.clearNodeScope(jjtn000);
/* 617:508 */         jjtc000 = false;
/* 618:    */       }
/* 619:    */       else
/* 620:    */       {
/* 621:510 */         this.jjtree.popNode();
/* 622:    */       }
/* 623:512 */       if ((jjte000 instanceof RuntimeException)) {
/* 624:513 */         throw ((RuntimeException)jjte000);
/* 625:    */       }
/* 626:515 */       if ((jjte000 instanceof ParseException)) {
/* 627:516 */         throw ((ParseException)jjte000);
/* 628:    */       }
/* 629:518 */       throw ((Error)jjte000);
/* 630:    */     }
/* 631:    */     finally
/* 632:    */     {
/* 633:520 */       if (jjtc000)
/* 634:    */       {
/* 635:521 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 636:522 */         jjtreeCloseNodeScope(jjtn000);
/* 637:    */       }
/* 638:    */     }
/* 639:    */   }
/* 640:    */   
/* 641:    */   public final void local_part()
/* 642:    */     throws ParseException
/* 643:    */   {
/* 644:529 */     ASTlocal_part jjtn000 = new ASTlocal_part(10);
/* 645:530 */     boolean jjtc000 = true;
/* 646:531 */     this.jjtree.openNodeScope(jjtn000);
/* 647:532 */     jjtreeOpenNodeScope(jjtn000);
/* 648:    */     try
/* 649:    */     {
/* 650:    */       Token t;
/* 651:534 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 652:    */       {
/* 653:    */       case 14: 
/* 654:536 */         t = jj_consume_token(14);
/* 655:537 */         break;
/* 656:    */       case 31: 
/* 657:539 */         t = jj_consume_token(31);
/* 658:540 */         break;
/* 659:    */       default: 
/* 660:542 */         this.jj_la1[15] = this.jj_gen;
/* 661:543 */         jj_consume_token(-1);
/* 662:544 */         throw new ParseException();
/* 663:    */       }
/* 664:    */       for (;;)
/* 665:    */       {
/* 666:548 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 667:    */         {
/* 668:    */         case 9: 
/* 669:    */         case 14: 
/* 670:    */         case 31: 
/* 671:    */           break;
/* 672:    */         default: 
/* 673:555 */           this.jj_la1[16] = this.jj_gen;
/* 674:556 */           break;
/* 675:    */         }
/* 676:558 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 677:    */         {
/* 678:    */         case 9: 
/* 679:560 */           t = jj_consume_token(9);
/* 680:561 */           break;
/* 681:    */         default: 
/* 682:563 */           this.jj_la1[17] = this.jj_gen;
/* 683:    */         }
/* 684:566 */         if ((t.kind == 31) || (t.image.charAt(t.image.length() - 1) != '.')) {
/* 685:567 */           throw new ParseException("Words in local part must be separated by '.'");
/* 686:    */         }
/* 687:568 */         switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 688:    */         {
/* 689:    */         case 14: 
/* 690:570 */           t = jj_consume_token(14);
/* 691:571 */           break;
/* 692:    */         case 31: 
/* 693:573 */           t = jj_consume_token(31);
/* 694:    */         }
/* 695:    */       }
/* 696:576 */       this.jj_la1[18] = this.jj_gen;
/* 697:577 */       jj_consume_token(-1);
/* 698:578 */       throw new ParseException();
/* 699:    */     }
/* 700:    */     finally
/* 701:    */     {
/* 702:582 */       if (jjtc000)
/* 703:    */       {
/* 704:583 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 705:584 */         jjtreeCloseNodeScope(jjtn000);
/* 706:    */       }
/* 707:    */     }
/* 708:    */   }
/* 709:    */   
/* 710:    */   public final void domain()
/* 711:    */     throws ParseException
/* 712:    */   {
/* 713:591 */     ASTdomain jjtn000 = new ASTdomain(11);
/* 714:592 */     boolean jjtc000 = true;
/* 715:593 */     this.jjtree.openNodeScope(jjtn000);
/* 716:594 */     jjtreeOpenNodeScope(jjtn000);
/* 717:    */     try
/* 718:    */     {
/* 719:596 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 720:    */       {
/* 721:    */       case 14: 
/* 722:598 */         Token t = jj_consume_token(14);
/* 723:    */         for (;;)
/* 724:    */         {
/* 725:601 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 726:    */           {
/* 727:    */           case 9: 
/* 728:    */           case 14: 
/* 729:    */             break;
/* 730:    */           default: 
/* 731:607 */             this.jj_la1[19] = this.jj_gen;
/* 732:608 */             break;
/* 733:    */           }
/* 734:610 */           switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 735:    */           {
/* 736:    */           case 9: 
/* 737:612 */             t = jj_consume_token(9);
/* 738:613 */             break;
/* 739:    */           default: 
/* 740:615 */             this.jj_la1[20] = this.jj_gen;
/* 741:    */           }
/* 742:618 */           if (t.image.charAt(t.image.length() - 1) != '.') {
/* 743:619 */             throw new ParseException("Atoms in domain names must be separated by '.'");
/* 744:    */           }
/* 745:620 */           t = jj_consume_token(14);
/* 746:    */         }
/* 747:    */       case 18: 
/* 748:624 */         jj_consume_token(18);
/* 749:625 */         break;
/* 750:    */       default: 
/* 751:627 */         this.jj_la1[21] = this.jj_gen;
/* 752:628 */         jj_consume_token(-1);
/* 753:629 */         throw new ParseException();
/* 754:    */       }
/* 755:    */     }
/* 756:    */     finally
/* 757:    */     {
/* 758:632 */       if (jjtc000)
/* 759:    */       {
/* 760:633 */         this.jjtree.closeNodeScope(jjtn000, true);
/* 761:634 */         jjtreeCloseNodeScope(jjtn000);
/* 762:    */       }
/* 763:    */     }
/* 764:    */   }
/* 765:    */   
/* 766:    */   private final boolean jj_2_1(int xla)
/* 767:    */   {
/* 768:640 */     this.jj_la = xla;this.jj_lastpos = (this.jj_scanpos = this.token);
/* 769:    */     try
/* 770:    */     {
/* 771:641 */       return !jj_3_1();
/* 772:    */     }
/* 773:    */     catch (LookaheadSuccess ls)
/* 774:    */     {
/* 775:642 */       return true;
/* 776:    */     }
/* 777:    */     finally
/* 778:    */     {
/* 779:643 */       jj_save(0, xla);
/* 780:    */     }
/* 781:    */   }
/* 782:    */   
/* 783:    */   private final boolean jj_2_2(int xla)
/* 784:    */   {
/* 785:647 */     this.jj_la = xla;this.jj_lastpos = (this.jj_scanpos = this.token);
/* 786:    */     try
/* 787:    */     {
/* 788:648 */       return !jj_3_2();
/* 789:    */     }
/* 790:    */     catch (LookaheadSuccess ls)
/* 791:    */     {
/* 792:649 */       return true;
/* 793:    */     }
/* 794:    */     finally
/* 795:    */     {
/* 796:650 */       jj_save(1, xla);
/* 797:    */     }
/* 798:    */   }
/* 799:    */   
/* 800:    */   private final boolean jj_3R_12()
/* 801:    */   {
/* 802:654 */     if (jj_scan_token(14)) {
/* 803:654 */       return true;
/* 804:    */     }
/* 805:    */     Token xsp;
/* 806:    */     do
/* 807:    */     {
/* 808:657 */       xsp = this.jj_scanpos;
/* 809:658 */     } while (!jj_3R_13());
/* 810:658 */     this.jj_scanpos = xsp;
/* 811:    */     
/* 812:660 */     return false;
/* 813:    */   }
/* 814:    */   
/* 815:    */   private final boolean jj_3R_10()
/* 816:    */   {
/* 817:665 */     Token xsp = this.jj_scanpos;
/* 818:666 */     if (jj_3R_12())
/* 819:    */     {
/* 820:667 */       this.jj_scanpos = xsp;
/* 821:668 */       if (jj_scan_token(18)) {
/* 822:668 */         return true;
/* 823:    */       }
/* 824:    */     }
/* 825:670 */     return false;
/* 826:    */   }
/* 827:    */   
/* 828:    */   private final boolean jj_3_2()
/* 829:    */   {
/* 830:674 */     if (jj_3R_8()) {
/* 831:674 */       return true;
/* 832:    */     }
/* 833:675 */     return false;
/* 834:    */   }
/* 835:    */   
/* 836:    */   private final boolean jj_3R_9()
/* 837:    */   {
/* 838:680 */     Token xsp = this.jj_scanpos;
/* 839:681 */     if (jj_scan_token(14))
/* 840:    */     {
/* 841:682 */       this.jj_scanpos = xsp;
/* 842:683 */       if (jj_scan_token(31)) {
/* 843:683 */         return true;
/* 844:    */       }
/* 845:    */     }
/* 846:    */     do
/* 847:    */     {
/* 848:686 */       xsp = this.jj_scanpos;
/* 849:687 */     } while (!jj_3R_11());
/* 850:687 */     this.jj_scanpos = xsp;
/* 851:    */     
/* 852:689 */     return false;
/* 853:    */   }
/* 854:    */   
/* 855:    */   private final boolean jj_3R_11()
/* 856:    */   {
/* 857:694 */     Token xsp = this.jj_scanpos;
/* 858:695 */     if (jj_scan_token(9)) {
/* 859:695 */       this.jj_scanpos = xsp;
/* 860:    */     }
/* 861:696 */     xsp = this.jj_scanpos;
/* 862:697 */     if (jj_scan_token(14))
/* 863:    */     {
/* 864:698 */       this.jj_scanpos = xsp;
/* 865:699 */       if (jj_scan_token(31)) {
/* 866:699 */         return true;
/* 867:    */       }
/* 868:    */     }
/* 869:701 */     return false;
/* 870:    */   }
/* 871:    */   
/* 872:    */   private final boolean jj_3R_13()
/* 873:    */   {
/* 874:706 */     Token xsp = this.jj_scanpos;
/* 875:707 */     if (jj_scan_token(9)) {
/* 876:707 */       this.jj_scanpos = xsp;
/* 877:    */     }
/* 878:708 */     if (jj_scan_token(14)) {
/* 879:708 */       return true;
/* 880:    */     }
/* 881:709 */     return false;
/* 882:    */   }
/* 883:    */   
/* 884:    */   private final boolean jj_3R_8()
/* 885:    */   {
/* 886:713 */     if (jj_3R_9()) {
/* 887:713 */       return true;
/* 888:    */     }
/* 889:714 */     if (jj_scan_token(8)) {
/* 890:714 */       return true;
/* 891:    */     }
/* 892:715 */     if (jj_3R_10()) {
/* 893:715 */       return true;
/* 894:    */     }
/* 895:716 */     return false;
/* 896:    */   }
/* 897:    */   
/* 898:    */   private final boolean jj_3_1()
/* 899:    */   {
/* 900:720 */     if (jj_3R_8()) {
/* 901:720 */       return true;
/* 902:    */     }
/* 903:721 */     return false;
/* 904:    */   }
/* 905:    */   
/* 906:730 */   public boolean lookingAhead = false;
/* 907:    */   private boolean jj_semLA;
/* 908:    */   private int jj_gen;
/* 909:733 */   private final int[] jj_la1 = new int[22];
/* 910:    */   private static int[] jj_la1_0;
/* 911:    */   private static int[] jj_la1_1;
/* 912:    */   
/* 913:    */   static
/* 914:    */   {
/* 915:737 */     jj_la1_0();
/* 916:738 */     jj_la1_1();
/* 917:    */   }
/* 918:    */   
/* 919:    */   private static void jj_la1_0()
/* 920:    */   {
/* 921:741 */     jj_la1_0 = new int[] { 2, -2147467200, 8, -2147467200, 80, -2147467200, -2147467200, -2147467200, 8, -2147467200, 256, 264, 8, -2147467264, -2147467264, -2147467264, -2147466752, 512, -2147467264, 16896, 512, 278528 };
/* 922:    */   }
/* 923:    */   
/* 924:    */   private static void jj_la1_1()
/* 925:    */   {
/* 926:744 */     jj_la1_1 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/* 927:    */   }
/* 928:    */   
/* 929:746 */   private final JJCalls[] jj_2_rtns = new JJCalls[2];
/* 930:747 */   private boolean jj_rescan = false;
/* 931:748 */   private int jj_gc = 0;
/* 932:    */   
/* 933:    */   public AddressListParser(InputStream stream)
/* 934:    */   {
/* 935:751 */     this(stream, null);
/* 936:    */   }
/* 937:    */   
/* 938:    */   public AddressListParser(InputStream stream, String encoding)
/* 939:    */   {
/* 940:    */     try
/* 941:    */     {
/* 942:754 */       this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
/* 943:    */     }
/* 944:    */     catch (UnsupportedEncodingException e)
/* 945:    */     {
/* 946:754 */       throw new RuntimeException(e);
/* 947:    */     }
/* 948:755 */     this.token_source = new AddressListParserTokenManager(this.jj_input_stream);
/* 949:756 */     this.token = new Token();
/* 950:757 */     this.jj_ntk = -1;
/* 951:758 */     this.jj_gen = 0;
/* 952:759 */     for (int i = 0; i < 22; i++) {
/* 953:759 */       this.jj_la1[i] = -1;
/* 954:    */     }
/* 955:760 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 956:760 */       this.jj_2_rtns[i] = new JJCalls();
/* 957:    */     }
/* 958:    */   }
/* 959:    */   
/* 960:    */   public void ReInit(InputStream stream)
/* 961:    */   {
/* 962:764 */     ReInit(stream, null);
/* 963:    */   }
/* 964:    */   
/* 965:    */   public void ReInit(InputStream stream, String encoding)
/* 966:    */   {
/* 967:    */     try
/* 968:    */     {
/* 969:767 */       this.jj_input_stream.ReInit(stream, encoding, 1, 1);
/* 970:    */     }
/* 971:    */     catch (UnsupportedEncodingException e)
/* 972:    */     {
/* 973:767 */       throw new RuntimeException(e);
/* 974:    */     }
/* 975:768 */     this.token_source.ReInit(this.jj_input_stream);
/* 976:769 */     this.token = new Token();
/* 977:770 */     this.jj_ntk = -1;
/* 978:771 */     this.jjtree.reset();
/* 979:772 */     this.jj_gen = 0;
/* 980:773 */     for (int i = 0; i < 22; i++) {
/* 981:773 */       this.jj_la1[i] = -1;
/* 982:    */     }
/* 983:774 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 984:774 */       this.jj_2_rtns[i] = new JJCalls();
/* 985:    */     }
/* 986:    */   }
/* 987:    */   
/* 988:    */   public AddressListParser(Reader stream)
/* 989:    */   {
/* 990:778 */     this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
/* 991:779 */     this.token_source = new AddressListParserTokenManager(this.jj_input_stream);
/* 992:780 */     this.token = new Token();
/* 993:781 */     this.jj_ntk = -1;
/* 994:782 */     this.jj_gen = 0;
/* 995:783 */     for (int i = 0; i < 22; i++) {
/* 996:783 */       this.jj_la1[i] = -1;
/* 997:    */     }
/* 998:784 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* 999:784 */       this.jj_2_rtns[i] = new JJCalls();
/* :00:    */     }
/* :01:    */   }
/* :02:    */   
/* :03:    */   public void ReInit(Reader stream)
/* :04:    */   {
/* :05:788 */     this.jj_input_stream.ReInit(stream, 1, 1);
/* :06:789 */     this.token_source.ReInit(this.jj_input_stream);
/* :07:790 */     this.token = new Token();
/* :08:791 */     this.jj_ntk = -1;
/* :09:792 */     this.jjtree.reset();
/* :10:793 */     this.jj_gen = 0;
/* :11:794 */     for (int i = 0; i < 22; i++) {
/* :12:794 */       this.jj_la1[i] = -1;
/* :13:    */     }
/* :14:795 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* :15:795 */       this.jj_2_rtns[i] = new JJCalls();
/* :16:    */     }
/* :17:    */   }
/* :18:    */   
/* :19:    */   public AddressListParser(AddressListParserTokenManager tm)
/* :20:    */   {
/* :21:799 */     this.token_source = tm;
/* :22:800 */     this.token = new Token();
/* :23:801 */     this.jj_ntk = -1;
/* :24:802 */     this.jj_gen = 0;
/* :25:803 */     for (int i = 0; i < 22; i++) {
/* :26:803 */       this.jj_la1[i] = -1;
/* :27:    */     }
/* :28:804 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* :29:804 */       this.jj_2_rtns[i] = new JJCalls();
/* :30:    */     }
/* :31:    */   }
/* :32:    */   
/* :33:    */   public void ReInit(AddressListParserTokenManager tm)
/* :34:    */   {
/* :35:808 */     this.token_source = tm;
/* :36:809 */     this.token = new Token();
/* :37:810 */     this.jj_ntk = -1;
/* :38:811 */     this.jjtree.reset();
/* :39:812 */     this.jj_gen = 0;
/* :40:813 */     for (int i = 0; i < 22; i++) {
/* :41:813 */       this.jj_la1[i] = -1;
/* :42:    */     }
/* :43:814 */     for (int i = 0; i < this.jj_2_rtns.length; i++) {
/* :44:814 */       this.jj_2_rtns[i] = new JJCalls();
/* :45:    */     }
/* :46:    */   }
/* :47:    */   
/* :48:    */   private final Token jj_consume_token(int kind)
/* :49:    */     throws ParseException
/* :50:    */   {
/* :51:    */     Token oldToken;
/* :52:819 */     if ((oldToken = this.token).next != null) {
/* :53:819 */       this.token = this.token.next;
/* :54:    */     } else {
/* :55:820 */       this.token = (this.token.next = this.token_source.getNextToken());
/* :56:    */     }
/* :57:821 */     this.jj_ntk = -1;
/* :58:822 */     if (this.token.kind == kind)
/* :59:    */     {
/* :60:823 */       this.jj_gen += 1;
/* :61:824 */       if (++this.jj_gc > 100)
/* :62:    */       {
/* :63:825 */         this.jj_gc = 0;
/* :64:826 */         for (int i = 0; i < this.jj_2_rtns.length; i++)
/* :65:    */         {
/* :66:827 */           JJCalls c = this.jj_2_rtns[i];
/* :67:828 */           while (c != null)
/* :68:    */           {
/* :69:829 */             if (c.gen < this.jj_gen) {
/* :70:829 */               c.first = null;
/* :71:    */             }
/* :72:830 */             c = c.next;
/* :73:    */           }
/* :74:    */         }
/* :75:    */       }
/* :76:834 */       return this.token;
/* :77:    */     }
/* :78:836 */     this.token = oldToken;
/* :79:837 */     this.jj_kind = kind;
/* :80:838 */     throw generateParseException();
/* :81:    */   }
/* :82:    */   
/* :83:842 */   private final LookaheadSuccess jj_ls = new LookaheadSuccess(null);
/* :84:    */   
/* :85:    */   private final boolean jj_scan_token(int kind)
/* :86:    */   {
/* :87:844 */     if (this.jj_scanpos == this.jj_lastpos)
/* :88:    */     {
/* :89:845 */       this.jj_la -= 1;
/* :90:846 */       if (this.jj_scanpos.next == null) {
/* :91:847 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken());
/* :92:    */       } else {
/* :93:849 */         this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next);
/* :94:    */       }
/* :95:    */     }
/* :96:    */     else
/* :97:    */     {
/* :98:852 */       this.jj_scanpos = this.jj_scanpos.next;
/* :99:    */     }
/* ;00:854 */     if (this.jj_rescan)
/* ;01:    */     {
/* ;02:855 */       int i = 0;
/* ;03:855 */       for (Token tok = this.token; (tok != null) && (tok != this.jj_scanpos); tok = tok.next) {
/* ;04:856 */         i++;
/* ;05:    */       }
/* ;06:857 */       if (tok != null) {
/* ;07:857 */         jj_add_error_token(kind, i);
/* ;08:    */       }
/* ;09:    */     }
/* ;10:859 */     if (this.jj_scanpos.kind != kind) {
/* ;11:859 */       return true;
/* ;12:    */     }
/* ;13:860 */     if ((this.jj_la == 0) && (this.jj_scanpos == this.jj_lastpos)) {
/* ;14:860 */       throw this.jj_ls;
/* ;15:    */     }
/* ;16:861 */     return false;
/* ;17:    */   }
/* ;18:    */   
/* ;19:    */   public final Token getNextToken()
/* ;20:    */   {
/* ;21:865 */     if (this.token.next != null) {
/* ;22:865 */       this.token = this.token.next;
/* ;23:    */     } else {
/* ;24:866 */       this.token = (this.token.next = this.token_source.getNextToken());
/* ;25:    */     }
/* ;26:867 */     this.jj_ntk = -1;
/* ;27:868 */     this.jj_gen += 1;
/* ;28:869 */     return this.token;
/* ;29:    */   }
/* ;30:    */   
/* ;31:    */   public final Token getToken(int index)
/* ;32:    */   {
/* ;33:873 */     Token t = this.lookingAhead ? this.jj_scanpos : this.token;
/* ;34:874 */     for (int i = 0; i < index; i++) {
/* ;35:875 */       if (t.next != null) {
/* ;36:875 */         t = t.next;
/* ;37:    */       } else {
/* ;38:876 */         t = t.next = this.token_source.getNextToken();
/* ;39:    */       }
/* ;40:    */     }
/* ;41:878 */     return t;
/* ;42:    */   }
/* ;43:    */   
/* ;44:    */   private final int jj_ntk()
/* ;45:    */   {
/* ;46:882 */     if ((this.jj_nt = this.token.next) == null) {
/* ;47:883 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* ;48:    */     }
/* ;49:885 */     return this.jj_ntk = this.jj_nt.kind;
/* ;50:    */   }
/* ;51:    */   
/* ;52:888 */   private Vector<int[]> jj_expentries = new Vector();
/* ;53:    */   private int[] jj_expentry;
/* ;54:890 */   private int jj_kind = -1;
/* ;55:891 */   private int[] jj_lasttokens = new int[100];
/* ;56:    */   private int jj_endpos;
/* ;57:    */   
/* ;58:    */   private void jj_add_error_token(int kind, int pos)
/* ;59:    */   {
/* ;60:895 */     if (pos >= 100) {
/* ;61:895 */       return;
/* ;62:    */     }
/* ;63:896 */     if (pos == this.jj_endpos + 1)
/* ;64:    */     {
/* ;65:897 */       this.jj_lasttokens[(this.jj_endpos++)] = kind;
/* ;66:    */     }
/* ;67:898 */     else if (this.jj_endpos != 0)
/* ;68:    */     {
/* ;69:899 */       this.jj_expentry = new int[this.jj_endpos];
/* ;70:900 */       for (int i = 0; i < this.jj_endpos; i++) {
/* ;71:901 */         this.jj_expentry[i] = this.jj_lasttokens[i];
/* ;72:    */       }
/* ;73:903 */       boolean exists = false;
/* ;74:904 */       for (Enumeration e = this.jj_expentries.elements(); e.hasMoreElements();)
/* ;75:    */       {
/* ;76:905 */         int[] oldentry = (int[])e.nextElement();
/* ;77:906 */         if (oldentry.length == this.jj_expentry.length)
/* ;78:    */         {
/* ;79:907 */           exists = true;
/* ;80:908 */           for (int i = 0; i < this.jj_expentry.length; i++) {
/* ;81:909 */             if (oldentry[i] != this.jj_expentry[i])
/* ;82:    */             {
/* ;83:910 */               exists = false;
/* ;84:911 */               break;
/* ;85:    */             }
/* ;86:    */           }
/* ;87:914 */           if (exists) {
/* ;88:    */             break;
/* ;89:    */           }
/* ;90:    */         }
/* ;91:    */       }
/* ;92:917 */       if (!exists) {
/* ;93:917 */         this.jj_expentries.addElement(this.jj_expentry);
/* ;94:    */       }
/* ;95:918 */       if (pos != 0)
/* ;96:    */       {
/* ;97:918 */         int tmp205_204 = pos;this.jj_endpos = tmp205_204;this.jj_lasttokens[(tmp205_204 - 1)] = kind;
/* ;98:    */       }
/* ;99:    */     }
/* <00:    */   }
/* <01:    */   
/* <02:    */   public ParseException generateParseException()
/* <03:    */   {
/* <04:923 */     this.jj_expentries.removeAllElements();
/* <05:924 */     boolean[] la1tokens = new boolean[34];
/* <06:925 */     for (int i = 0; i < 34; i++) {
/* <07:926 */       la1tokens[i] = false;
/* <08:    */     }
/* <09:928 */     if (this.jj_kind >= 0)
/* <10:    */     {
/* <11:929 */       la1tokens[this.jj_kind] = true;
/* <12:930 */       this.jj_kind = -1;
/* <13:    */     }
/* <14:932 */     for (int i = 0; i < 22; i++) {
/* <15:933 */       if (this.jj_la1[i] == this.jj_gen) {
/* <16:934 */         for (int j = 0; j < 32; j++)
/* <17:    */         {
/* <18:935 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* <19:936 */             la1tokens[j] = true;
/* <20:    */           }
/* <21:938 */           if ((jj_la1_1[i] & 1 << j) != 0) {
/* <22:939 */             la1tokens[(32 + j)] = true;
/* <23:    */           }
/* <24:    */         }
/* <25:    */       }
/* <26:    */     }
/* <27:944 */     for (int i = 0; i < 34; i++) {
/* <28:945 */       if (la1tokens[i] != 0)
/* <29:    */       {
/* <30:946 */         this.jj_expentry = new int[1];
/* <31:947 */         this.jj_expentry[0] = i;
/* <32:948 */         this.jj_expentries.addElement(this.jj_expentry);
/* <33:    */       }
/* <34:    */     }
/* <35:951 */     this.jj_endpos = 0;
/* <36:952 */     jj_rescan_token();
/* <37:953 */     jj_add_error_token(0, 0);
/* <38:954 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* <39:955 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* <40:956 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* <41:    */     }
/* <42:958 */     return new ParseException(this.token, exptokseq, tokenImage);
/* <43:    */   }
/* <44:    */   
/* <45:    */   private final void jj_rescan_token()
/* <46:    */   {
/* <47:968 */     this.jj_rescan = true;
/* <48:969 */     for (int i = 0; i < 2; i++) {
/* <49:    */       try
/* <50:    */       {
/* <51:971 */         JJCalls p = this.jj_2_rtns[i];
/* <52:    */         do
/* <53:    */         {
/* <54:973 */           if (p.gen > this.jj_gen)
/* <55:    */           {
/* <56:974 */             this.jj_la = p.arg;this.jj_lastpos = (this.jj_scanpos = p.first);
/* <57:975 */             switch (i)
/* <58:    */             {
/* <59:    */             case 0: 
/* <60:976 */               jj_3_1(); break;
/* <61:    */             case 1: 
/* <62:977 */               jj_3_2();
/* <63:    */             }
/* <64:    */           }
/* <65:980 */           p = p.next;
/* <66:981 */         } while (p != null);
/* <67:    */       }
/* <68:    */       catch (LookaheadSuccess ls) {}
/* <69:    */     }
/* <70:984 */     this.jj_rescan = false;
/* <71:    */   }
/* <72:    */   
/* <73:    */   private final void jj_save(int index, int xla)
/* <74:    */   {
/* <75:988 */     JJCalls p = this.jj_2_rtns[index];
/* <76:989 */     while (p.gen > this.jj_gen)
/* <77:    */     {
/* <78:990 */       if (p.next == null)
/* <79:    */       {
/* <80:990 */         p = p.next = new JJCalls(); break;
/* <81:    */       }
/* <82:991 */       p = p.next;
/* <83:    */     }
/* <84:993 */     p.gen = (this.jj_gen + xla - this.jj_la);p.first = this.token;p.arg = xla;
/* <85:    */   }
/* <86:    */   
/* <87:    */   public final void enable_tracing() {}
/* <88:    */   
/* <89:    */   public final void disable_tracing() {}
/* <90:    */   
/* <91:    */   static final class JJCalls
/* <92:    */   {
/* <93:    */     int gen;
/* <94:    */     Token first;
/* <95:    */     int arg;
/* <96:    */     JJCalls next;
/* <97:    */   }
/* <98:    */   
/* <99:    */   private static final class LookaheadSuccess
/* =00:    */     extends Error
/* =01:    */   {}
/* =02:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.parser.AddressListParser
 * JD-Core Version:    0.7.0.1
 */