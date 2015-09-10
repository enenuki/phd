/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import java.util.Vector;
/*   8:    */ import javax.xml.transform.TransformerException;
/*   9:    */ import org.apache.xml.dtm.DTM;
/*  10:    */ import org.apache.xml.utils.QName;
/*  11:    */ import org.apache.xpath.Expression;
/*  12:    */ import org.apache.xpath.XPath;
/*  13:    */ import org.apache.xpath.XPathContext;
/*  14:    */ import org.apache.xpath.patterns.NodeTest;
/*  15:    */ import org.apache.xpath.patterns.StepPattern;
/*  16:    */ import org.apache.xpath.patterns.UnionPattern;
/*  17:    */ 
/*  18:    */ public class TemplateList
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   static final long serialVersionUID = 5803675288911728791L;
/*  22:    */   static final boolean DEBUG = false;
/*  23:    */   
/*  24:    */   public void setTemplate(ElemTemplate template)
/*  25:    */   {
/*  26: 66 */     XPath matchXPath = template.getMatch();
/*  27: 68 */     if ((null == template.getName()) && (null == matchXPath)) {
/*  28: 70 */       template.error("ER_NEED_NAME_OR_MATCH_ATTRIB", new Object[] { "xsl:template" });
/*  29:    */     }
/*  30: 74 */     if (null != template.getName())
/*  31:    */     {
/*  32: 76 */       ElemTemplate existingTemplate = (ElemTemplate)this.m_namedTemplates.get(template.getName());
/*  33: 77 */       if (null == existingTemplate)
/*  34:    */       {
/*  35: 79 */         this.m_namedTemplates.put(template.getName(), template);
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39: 83 */         int existingPrecedence = existingTemplate.getStylesheetComposed().getImportCountComposed();
/*  40:    */         
/*  41: 85 */         int newPrecedence = template.getStylesheetComposed().getImportCountComposed();
/*  42: 86 */         if (newPrecedence > existingPrecedence) {
/*  43: 89 */           this.m_namedTemplates.put(template.getName(), template);
/*  44: 91 */         } else if (newPrecedence == existingPrecedence) {
/*  45: 92 */           template.error("ER_DUPLICATE_NAMED_TEMPLATE", new Object[] { template.getName() });
/*  46:    */         }
/*  47:    */       }
/*  48:    */     }
/*  49: 99 */     if (null != matchXPath)
/*  50:    */     {
/*  51:101 */       Expression matchExpr = matchXPath.getExpression();
/*  52:103 */       if ((matchExpr instanceof StepPattern))
/*  53:    */       {
/*  54:105 */         insertPatternInTable((StepPattern)matchExpr, template);
/*  55:    */       }
/*  56:107 */       else if ((matchExpr instanceof UnionPattern))
/*  57:    */       {
/*  58:109 */         UnionPattern upat = (UnionPattern)matchExpr;
/*  59:110 */         StepPattern[] pats = upat.getPatterns();
/*  60:111 */         int n = pats.length;
/*  61:113 */         for (int i = 0; i < n; i++) {
/*  62:115 */           insertPatternInTable(pats[i], template);
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   void dumpAssociationTables()
/*  69:    */   {
/*  70:136 */     Enumeration associations = this.m_patternTable.elements();
/*  71:138 */     while (associations.hasMoreElements())
/*  72:    */     {
/*  73:140 */       TemplateSubPatternAssociation head = (TemplateSubPatternAssociation)associations.nextElement();
/*  74:143 */       while (null != head)
/*  75:    */       {
/*  76:145 */         System.out.print("(" + head.getTargetString() + ", " + head.getPattern() + ")");
/*  77:    */         
/*  78:    */ 
/*  79:148 */         head = head.getNext();
/*  80:    */       }
/*  81:151 */       System.out.println("\n.....");
/*  82:    */     }
/*  83:154 */     TemplateSubPatternAssociation head = this.m_wildCardPatterns;
/*  84:    */     
/*  85:156 */     System.out.print("wild card list: ");
/*  86:158 */     while (null != head)
/*  87:    */     {
/*  88:160 */       System.out.print("(" + head.getTargetString() + ", " + head.getPattern() + ")");
/*  89:    */       
/*  90:    */ 
/*  91:163 */       head = head.getNext();
/*  92:    */     }
/*  93:166 */     System.out.println("\n.....");
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void compose(StylesheetRoot sroot)
/*  97:    */   {
/*  98:182 */     if (null != this.m_wildCardPatterns)
/*  99:    */     {
/* 100:184 */       Enumeration associations = this.m_patternTable.elements();
/* 101:    */       TemplateSubPatternAssociation wild;
/* 102:186 */       for (; associations.hasMoreElements(); null != wild)
/* 103:    */       {
/* 104:188 */         TemplateSubPatternAssociation head = (TemplateSubPatternAssociation)associations.nextElement();
/* 105:    */         
/* 106:190 */         wild = this.m_wildCardPatterns;
/* 107:    */         
/* 108:192 */         continue;
/* 109:    */         try
/* 110:    */         {
/* 111:196 */           head = insertAssociationIntoList(head, (TemplateSubPatternAssociation)wild.clone(), true);
/* 112:    */         }
/* 113:    */         catch (CloneNotSupportedException cnse) {}
/* 114:201 */         wild = wild.getNext();
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private TemplateSubPatternAssociation insertAssociationIntoList(TemplateSubPatternAssociation head, TemplateSubPatternAssociation item, boolean isWildCardInsert)
/* 120:    */   {
/* 121:233 */     double priority = getPriorityOrScore(item);
/* 122:    */     
/* 123:235 */     int importLevel = item.getImportLevel();
/* 124:236 */     int docOrder = item.getDocOrderPos();
/* 125:237 */     TemplateSubPatternAssociation insertPoint = head;
/* 126:    */     TemplateSubPatternAssociation next;
/* 127:    */     double workPriority;
/* 128:    */     for (;;)
/* 129:    */     {
/* 130:255 */       next = insertPoint.getNext();
/* 131:256 */       if (null == next) {
/* 132:    */         break;
/* 133:    */       }
/* 134:260 */       workPriority = getPriorityOrScore(next);
/* 135:261 */       if (importLevel > next.getImportLevel()) {
/* 136:    */         break;
/* 137:    */       }
/* 138:263 */       if (importLevel < next.getImportLevel())
/* 139:    */       {
/* 140:264 */         insertPoint = next;
/* 141:    */       }
/* 142:    */       else
/* 143:    */       {
/* 144:265 */         if (priority > workPriority) {
/* 145:    */           break;
/* 146:    */         }
/* 147:267 */         if (priority < workPriority)
/* 148:    */         {
/* 149:268 */           insertPoint = next;
/* 150:    */         }
/* 151:    */         else
/* 152:    */         {
/* 153:269 */           if (docOrder >= next.getDocOrderPos()) {
/* 154:    */             break;
/* 155:    */           }
/* 156:272 */           insertPoint = next;
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:    */     boolean insertBefore;
/* 161:276 */     if ((null == next) || (insertPoint == head))
/* 162:    */     {
/* 163:278 */       workPriority = getPriorityOrScore(insertPoint);
/* 164:279 */       if (importLevel > insertPoint.getImportLevel()) {
/* 165:280 */         insertBefore = true;
/* 166:281 */       } else if (importLevel < insertPoint.getImportLevel()) {
/* 167:282 */         insertBefore = false;
/* 168:283 */       } else if (priority > workPriority) {
/* 169:284 */         insertBefore = true;
/* 170:285 */       } else if (priority < workPriority) {
/* 171:286 */         insertBefore = false;
/* 172:287 */       } else if (docOrder >= insertPoint.getDocOrderPos()) {
/* 173:288 */         insertBefore = true;
/* 174:    */       } else {
/* 175:290 */         insertBefore = false;
/* 176:    */       }
/* 177:    */     }
/* 178:    */     else
/* 179:    */     {
/* 180:293 */       insertBefore = false;
/* 181:    */     }
/* 182:297 */     if (isWildCardInsert)
/* 183:    */     {
/* 184:299 */       if (insertBefore)
/* 185:    */       {
/* 186:301 */         item.setNext(insertPoint);
/* 187:    */         
/* 188:303 */         String key = insertPoint.getTargetString();
/* 189:    */         
/* 190:305 */         item.setTargetString(key);
/* 191:306 */         putHead(key, item);
/* 192:307 */         return item;
/* 193:    */       }
/* 194:311 */       item.setNext(next);
/* 195:312 */       insertPoint.setNext(item);
/* 196:313 */       return head;
/* 197:    */     }
/* 198:318 */     if (insertBefore)
/* 199:    */     {
/* 200:320 */       item.setNext(insertPoint);
/* 201:322 */       if ((insertPoint.isWild()) || (item.isWild())) {
/* 202:323 */         this.m_wildCardPatterns = item;
/* 203:    */       } else {
/* 204:325 */         putHead(item.getTargetString(), item);
/* 205:    */       }
/* 206:326 */       return item;
/* 207:    */     }
/* 208:330 */     item.setNext(next);
/* 209:331 */     insertPoint.setNext(item);
/* 210:332 */     return head;
/* 211:    */   }
/* 212:    */   
/* 213:    */   private void insertPatternInTable(StepPattern pattern, ElemTemplate template)
/* 214:    */   {
/* 215:346 */     String target = pattern.getTargetString();
/* 216:348 */     if (null != target)
/* 217:    */     {
/* 218:350 */       String pstring = template.getMatch().getPatternString();
/* 219:351 */       TemplateSubPatternAssociation association = new TemplateSubPatternAssociation(template, pattern, pstring);
/* 220:    */       
/* 221:    */ 
/* 222:    */ 
/* 223:355 */       boolean isWildCard = association.isWild();
/* 224:356 */       TemplateSubPatternAssociation head = isWildCard ? this.m_wildCardPatterns : getHead(target);
/* 225:360 */       if (null == head)
/* 226:    */       {
/* 227:362 */         if (isWildCard) {
/* 228:363 */           this.m_wildCardPatterns = association;
/* 229:    */         } else {
/* 230:365 */           putHead(target, association);
/* 231:    */         }
/* 232:    */       }
/* 233:    */       else {
/* 234:369 */         insertAssociationIntoList(head, association, false);
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   private double getPriorityOrScore(TemplateSubPatternAssociation matchPat)
/* 240:    */   {
/* 241:392 */     double priority = matchPat.getTemplate().getPriority();
/* 242:394 */     if (priority == (-1.0D / 0.0D))
/* 243:    */     {
/* 244:396 */       Expression ex = matchPat.getStepPattern();
/* 245:398 */       if ((ex instanceof NodeTest)) {
/* 246:400 */         return ((NodeTest)ex).getDefaultScore();
/* 247:    */       }
/* 248:    */     }
/* 249:404 */     return priority;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public ElemTemplate getTemplate(QName qname)
/* 253:    */   {
/* 254:416 */     return (ElemTemplate)this.m_namedTemplates.get(qname);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public TemplateSubPatternAssociation getHead(XPathContext xctxt, int targetNode, DTM dtm)
/* 258:    */   {
/* 259:433 */     short targetNodeType = dtm.getNodeType(targetNode);
/* 260:    */     TemplateSubPatternAssociation head;
/* 261:436 */     switch (targetNodeType)
/* 262:    */     {
/* 263:    */     case 1: 
/* 264:    */     case 2: 
/* 265:440 */       head = (TemplateSubPatternAssociation)this.m_patternTable.get(dtm.getLocalName(targetNode));
/* 266:    */       
/* 267:442 */       break;
/* 268:    */     case 3: 
/* 269:    */     case 4: 
/* 270:445 */       head = this.m_textPatterns;
/* 271:446 */       break;
/* 272:    */     case 5: 
/* 273:    */     case 6: 
/* 274:449 */       head = (TemplateSubPatternAssociation)this.m_patternTable.get(dtm.getNodeName(targetNode));
/* 275:    */       
/* 276:451 */       break;
/* 277:    */     case 7: 
/* 278:453 */       head = (TemplateSubPatternAssociation)this.m_patternTable.get(dtm.getLocalName(targetNode));
/* 279:    */       
/* 280:455 */       break;
/* 281:    */     case 8: 
/* 282:457 */       head = this.m_commentPatterns;
/* 283:458 */       break;
/* 284:    */     case 9: 
/* 285:    */     case 11: 
/* 286:461 */       head = this.m_docPatterns;
/* 287:462 */       break;
/* 288:    */     case 10: 
/* 289:    */     case 12: 
/* 290:    */     default: 
/* 291:465 */       head = (TemplateSubPatternAssociation)this.m_patternTable.get(dtm.getNodeName(targetNode));
/* 292:    */     }
/* 293:469 */     return null == head ? this.m_wildCardPatterns : head;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public ElemTemplate getTemplateFast(XPathContext xctxt, int targetNode, int expTypeID, QName mode, int maxImportLevel, boolean quietConflictWarnings, DTM dtm)
/* 297:    */     throws TransformerException
/* 298:    */   {
/* 299:    */     TemplateSubPatternAssociation head;
/* 300:504 */     switch (dtm.getNodeType(targetNode))
/* 301:    */     {
/* 302:    */     case 1: 
/* 303:    */     case 2: 
/* 304:508 */       head = (TemplateSubPatternAssociation)this.m_patternTable.get(dtm.getLocalNameFromExpandedNameID(expTypeID));
/* 305:    */       
/* 306:510 */       break;
/* 307:    */     case 3: 
/* 308:    */     case 4: 
/* 309:513 */       head = this.m_textPatterns;
/* 310:514 */       break;
/* 311:    */     case 5: 
/* 312:    */     case 6: 
/* 313:517 */       head = (TemplateSubPatternAssociation)this.m_patternTable.get(dtm.getNodeName(targetNode));
/* 314:    */       
/* 315:519 */       break;
/* 316:    */     case 7: 
/* 317:521 */       head = (TemplateSubPatternAssociation)this.m_patternTable.get(dtm.getLocalName(targetNode));
/* 318:    */       
/* 319:523 */       break;
/* 320:    */     case 8: 
/* 321:525 */       head = this.m_commentPatterns;
/* 322:526 */       break;
/* 323:    */     case 9: 
/* 324:    */     case 11: 
/* 325:529 */       head = this.m_docPatterns;
/* 326:530 */       break;
/* 327:    */     case 10: 
/* 328:    */     case 12: 
/* 329:    */     default: 
/* 330:533 */       head = (TemplateSubPatternAssociation)this.m_patternTable.get(dtm.getNodeName(targetNode));
/* 331:    */     }
/* 332:537 */     if (null == head)
/* 333:    */     {
/* 334:539 */       head = this.m_wildCardPatterns;
/* 335:540 */       if (null == head) {
/* 336:541 */         return null;
/* 337:    */       }
/* 338:    */     }
/* 339:547 */     xctxt.pushNamespaceContextNull();
/* 340:    */     try
/* 341:    */     {
/* 342:    */       do
/* 343:    */       {
/* 344:552 */         if ((maxImportLevel <= -1) || (head.getImportLevel() <= maxImportLevel))
/* 345:    */         {
/* 346:556 */           ElemTemplate template = head.getTemplate();
/* 347:557 */           xctxt.setNamespaceContext(template);
/* 348:559 */           if ((head.m_stepPattern.execute(xctxt, targetNode, dtm, expTypeID) != NodeTest.SCORE_NONE) && (head.matchMode(mode)))
/* 349:    */           {
/* 350:562 */             if (quietConflictWarnings) {
/* 351:563 */               checkConflicts(head, xctxt, targetNode, mode);
/* 352:    */             }
/* 353:565 */             return template;
/* 354:    */           }
/* 355:    */         }
/* 356:568 */       } while (null != (head = head.getNext()));
/* 357:    */     }
/* 358:    */     finally
/* 359:    */     {
/* 360:572 */       xctxt.popNamespaceContext();
/* 361:    */     }
/* 362:575 */     return null;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public ElemTemplate getTemplate(XPathContext xctxt, int targetNode, QName mode, boolean quietConflictWarnings, DTM dtm)
/* 366:    */     throws TransformerException
/* 367:    */   {
/* 368:601 */     TemplateSubPatternAssociation head = getHead(xctxt, targetNode, dtm);
/* 369:603 */     if (null != head)
/* 370:    */     {
/* 371:608 */       xctxt.pushNamespaceContextNull();
/* 372:609 */       xctxt.pushCurrentNodeAndExpression(targetNode, targetNode);
/* 373:    */       try
/* 374:    */       {
/* 375:    */         do
/* 376:    */         {
/* 377:614 */           ElemTemplate template = head.getTemplate();
/* 378:615 */           xctxt.setNamespaceContext(template);
/* 379:617 */           if ((head.m_stepPattern.execute(xctxt, targetNode) != NodeTest.SCORE_NONE) && (head.matchMode(mode)))
/* 380:    */           {
/* 381:620 */             if (quietConflictWarnings) {
/* 382:621 */               checkConflicts(head, xctxt, targetNode, mode);
/* 383:    */             }
/* 384:623 */             return template;
/* 385:    */           }
/* 386:626 */         } while (null != (head = head.getNext()));
/* 387:    */       }
/* 388:    */       finally
/* 389:    */       {
/* 390:630 */         xctxt.popCurrentNodeAndExpression();
/* 391:631 */         xctxt.popNamespaceContext();
/* 392:    */       }
/* 393:    */     }
/* 394:635 */     return null;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public ElemTemplate getTemplate(XPathContext xctxt, int targetNode, QName mode, int maxImportLevel, int endImportLevel, boolean quietConflictWarnings, DTM dtm)
/* 398:    */     throws TransformerException
/* 399:    */   {
/* 400:666 */     TemplateSubPatternAssociation head = getHead(xctxt, targetNode, dtm);
/* 401:668 */     if (null != head)
/* 402:    */     {
/* 403:673 */       xctxt.pushNamespaceContextNull();
/* 404:674 */       xctxt.pushCurrentNodeAndExpression(targetNode, targetNode);
/* 405:    */       try
/* 406:    */       {
/* 407:    */         do
/* 408:    */         {
/* 409:679 */           if ((maxImportLevel <= -1) || (head.getImportLevel() <= maxImportLevel))
/* 410:    */           {
/* 411:683 */             if (head.getImportLevel() <= maxImportLevel - endImportLevel) {
/* 412:684 */               return null;
/* 413:    */             }
/* 414:685 */             ElemTemplate template = head.getTemplate();
/* 415:686 */             xctxt.setNamespaceContext(template);
/* 416:688 */             if ((head.m_stepPattern.execute(xctxt, targetNode) != NodeTest.SCORE_NONE) && (head.matchMode(mode)))
/* 417:    */             {
/* 418:691 */               if (quietConflictWarnings) {
/* 419:692 */                 checkConflicts(head, xctxt, targetNode, mode);
/* 420:    */               }
/* 421:694 */               return template;
/* 422:    */             }
/* 423:    */           }
/* 424:697 */         } while (null != (head = head.getNext()));
/* 425:    */       }
/* 426:    */       finally
/* 427:    */       {
/* 428:701 */         xctxt.popCurrentNodeAndExpression();
/* 429:702 */         xctxt.popNamespaceContext();
/* 430:    */       }
/* 431:    */     }
/* 432:706 */     return null;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public TemplateWalker getWalker()
/* 436:    */   {
/* 437:715 */     return new TemplateWalker(null);
/* 438:    */   }
/* 439:    */   
/* 440:    */   private void checkConflicts(TemplateSubPatternAssociation head, XPathContext xctxt, int targetNode, QName mode) {}
/* 441:    */   
/* 442:    */   private void addObjectIfNotFound(Object obj, Vector v)
/* 443:    */   {
/* 444:742 */     int n = v.size();
/* 445:743 */     boolean addIt = true;
/* 446:745 */     for (int i = 0; i < n; i++) {
/* 447:747 */       if (v.elementAt(i) == obj)
/* 448:    */       {
/* 449:749 */         addIt = false;
/* 450:    */         
/* 451:751 */         break;
/* 452:    */       }
/* 453:    */     }
/* 454:755 */     if (addIt) {
/* 455:757 */       v.addElement(obj);
/* 456:    */     }
/* 457:    */   }
/* 458:    */   
/* 459:768 */   private Hashtable m_namedTemplates = new Hashtable(89);
/* 460:777 */   private Hashtable m_patternTable = new Hashtable(89);
/* 461:781 */   private TemplateSubPatternAssociation m_wildCardPatterns = null;
/* 462:785 */   private TemplateSubPatternAssociation m_textPatterns = null;
/* 463:789 */   private TemplateSubPatternAssociation m_docPatterns = null;
/* 464:793 */   private TemplateSubPatternAssociation m_commentPatterns = null;
/* 465:    */   
/* 466:    */   private Hashtable getNamedTemplates()
/* 467:    */   {
/* 468:806 */     return this.m_namedTemplates;
/* 469:    */   }
/* 470:    */   
/* 471:    */   private void setNamedTemplates(Hashtable v)
/* 472:    */   {
/* 473:820 */     this.m_namedTemplates = v;
/* 474:    */   }
/* 475:    */   
/* 476:    */   private TemplateSubPatternAssociation getHead(String key)
/* 477:    */   {
/* 478:833 */     return (TemplateSubPatternAssociation)this.m_patternTable.get(key);
/* 479:    */   }
/* 480:    */   
/* 481:    */   private void putHead(String key, TemplateSubPatternAssociation assoc)
/* 482:    */   {
/* 483:845 */     if (key.equals("#text")) {
/* 484:846 */       this.m_textPatterns = assoc;
/* 485:847 */     } else if (key.equals("/")) {
/* 486:848 */       this.m_docPatterns = assoc;
/* 487:849 */     } else if (key.equals("#comment")) {
/* 488:850 */       this.m_commentPatterns = assoc;
/* 489:    */     }
/* 490:852 */     this.m_patternTable.put(key, assoc);
/* 491:    */   }
/* 492:    */   
/* 493:    */   public class TemplateWalker
/* 494:    */   {
/* 495:    */     private Enumeration hashIterator;
/* 496:    */     private boolean inPatterns;
/* 497:    */     private TemplateSubPatternAssociation curPattern;
/* 498:    */     
/* 499:    */     TemplateWalker(TemplateList.1 x1)
/* 500:    */     {
/* 501:860 */       this();
/* 502:    */     }
/* 503:    */     
/* 504:866 */     private Hashtable m_compilerCache = new Hashtable();
/* 505:    */     
/* 506:    */     private TemplateWalker()
/* 507:    */     {
/* 508:870 */       this.hashIterator = TemplateList.this.m_patternTable.elements();
/* 509:871 */       this.inPatterns = true;
/* 510:872 */       this.curPattern = null;
/* 511:    */     }
/* 512:    */     
/* 513:    */     public ElemTemplate next()
/* 514:    */     {
/* 515:878 */       ElemTemplate retValue = null;
/* 516:    */       ElemTemplate ct;
/* 517:    */       do
/* 518:    */       {
/* 519:883 */         if (this.inPatterns)
/* 520:    */         {
/* 521:885 */           if (null != this.curPattern) {
/* 522:886 */             this.curPattern = this.curPattern.getNext();
/* 523:    */           }
/* 524:888 */           if (null != this.curPattern)
/* 525:    */           {
/* 526:889 */             retValue = this.curPattern.getTemplate();
/* 527:    */           }
/* 528:892 */           else if (this.hashIterator.hasMoreElements())
/* 529:    */           {
/* 530:894 */             this.curPattern = ((TemplateSubPatternAssociation)this.hashIterator.nextElement());
/* 531:895 */             retValue = this.curPattern.getTemplate();
/* 532:    */           }
/* 533:    */           else
/* 534:    */           {
/* 535:899 */             this.inPatterns = false;
/* 536:900 */             this.hashIterator = TemplateList.this.m_namedTemplates.elements();
/* 537:    */           }
/* 538:    */         }
/* 539:905 */         if (!this.inPatterns) {
/* 540:907 */           if (this.hashIterator.hasMoreElements()) {
/* 541:908 */             retValue = (ElemTemplate)this.hashIterator.nextElement();
/* 542:    */           } else {
/* 543:910 */             return null;
/* 544:    */           }
/* 545:    */         }
/* 546:913 */         ct = (ElemTemplate)this.m_compilerCache.get(new Integer(retValue.getUid()));
/* 547:914 */       } while (null != ct);
/* 548:916 */       this.m_compilerCache.put(new Integer(retValue.getUid()), retValue);
/* 549:917 */       return retValue;
/* 550:    */     }
/* 551:    */   }
/* 552:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.TemplateList
 * JD-Core Version:    0.7.0.1
 */