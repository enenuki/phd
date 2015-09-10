/*   1:    */ package com.gargoylesoftware.htmlunit.html.impl;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentFragment;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomNodeList;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*   8:    */ import java.io.Serializable;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  11:    */ import org.apache.commons.lang.builder.EqualsBuilder;
/*  12:    */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*  13:    */ import org.w3c.dom.DOMException;
/*  14:    */ import org.w3c.dom.DocumentFragment;
/*  15:    */ import org.w3c.dom.Node;
/*  16:    */ import org.w3c.dom.NodeList;
/*  17:    */ import org.w3c.dom.ranges.Range;
/*  18:    */ import org.w3c.dom.ranges.RangeException;
/*  19:    */ 
/*  20:    */ public class SimpleRange
/*  21:    */   implements Range, Serializable
/*  22:    */ {
/*  23:    */   private Node startContainer_;
/*  24:    */   private Node endContainer_;
/*  25:    */   private int startOffset_;
/*  26:    */   private int endOffset_;
/*  27:    */   
/*  28:    */   public SimpleRange() {}
/*  29:    */   
/*  30:    */   public SimpleRange(Node node)
/*  31:    */   {
/*  32: 75 */     this.startContainer_ = node;
/*  33: 76 */     this.endContainer_ = node;
/*  34: 77 */     this.startOffset_ = 0;
/*  35: 78 */     this.endOffset_ = getMaxOffset(node);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public SimpleRange(Node node, int offset)
/*  39:    */   {
/*  40: 87 */     this.startContainer_ = node;
/*  41: 88 */     this.endContainer_ = node;
/*  42: 89 */     this.startOffset_ = offset;
/*  43: 90 */     this.endOffset_ = offset;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public SimpleRange(Node startNode, int startOffset, Node endNode, int endOffset)
/*  47:    */   {
/*  48:101 */     this.startContainer_ = startNode;
/*  49:102 */     this.endContainer_ = endNode;
/*  50:103 */     this.startOffset_ = startOffset;
/*  51:104 */     this.endOffset_ = endOffset;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public DocumentFragment cloneContents()
/*  55:    */     throws DOMException
/*  56:    */   {
/*  57:111 */     throw new RuntimeException("Not implemented!");
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Range cloneRange()
/*  61:    */     throws DOMException
/*  62:    */   {
/*  63:118 */     return new SimpleRange(this.startContainer_, this.startOffset_, this.endContainer_, this.endOffset_);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void collapse(boolean toStart)
/*  67:    */     throws DOMException
/*  68:    */   {
/*  69:125 */     if (toStart)
/*  70:    */     {
/*  71:126 */       this.endContainer_ = this.startContainer_;
/*  72:127 */       this.endOffset_ = this.startOffset_;
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76:130 */       this.startContainer_ = this.endContainer_;
/*  77:131 */       this.startOffset_ = this.endOffset_;
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public short compareBoundaryPoints(short how, Range sourceRange)
/*  82:    */     throws DOMException
/*  83:    */   {
/*  84:139 */     throw new RuntimeException("Not implemented!");
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void deleteContents()
/*  88:    */     throws DOMException
/*  89:    */   {
/*  90:146 */     throw new RuntimeException("Not implemented!");
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void detach()
/*  94:    */     throws DOMException
/*  95:    */   {
/*  96:153 */     throw new RuntimeException("Not implemented!");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public DomDocumentFragment extractContents()
/* 100:    */     throws DOMException
/* 101:    */   {
/* 102:161 */     DomNode ancestor = (DomNode)getCommonAncestorContainer();
/* 103:162 */     DomNode ancestorClone = ancestor.cloneNode(true);
/* 104:    */     
/* 105:    */ 
/* 106:165 */     DomNode startClone = null;
/* 107:166 */     DomNode endClone = null;
/* 108:167 */     DomNode start = (DomNode)this.startContainer_;
/* 109:168 */     DomNode end = (DomNode)this.endContainer_;
/* 110:169 */     if (start == ancestor) {
/* 111:170 */       startClone = ancestorClone;
/* 112:    */     }
/* 113:172 */     if (end == ancestor) {
/* 114:173 */       endClone = ancestorClone;
/* 115:    */     }
/* 116:175 */     Iterable<DomNode> descendants = ancestor.getDescendants();
/* 117:176 */     if ((startClone == null) || (endClone == null))
/* 118:    */     {
/* 119:177 */       Iterator<DomNode> i = descendants.iterator();
/* 120:178 */       Iterator<DomNode> ci = ancestorClone.getDescendants().iterator();
/* 121:179 */       while (i.hasNext())
/* 122:    */       {
/* 123:180 */         DomNode e = (DomNode)i.next();
/* 124:181 */         DomNode ce = (DomNode)ci.next();
/* 125:182 */         if (start == e)
/* 126:    */         {
/* 127:183 */           startClone = ce;
/* 128:    */         }
/* 129:185 */         else if (end == e)
/* 130:    */         {
/* 131:186 */           endClone = ce;
/* 132:187 */           break;
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:193 */     if (startClone == null) {
/* 137:194 */       throw Context.reportRuntimeError("Unable to find start node clone.");
/* 138:    */     }
/* 139:196 */     deleteBefore(startClone, this.startOffset_);
/* 140:197 */     for (DomNode n = startClone; n != null; n = n.getParentNode()) {
/* 141:198 */       for (DomNode prev = n.getPreviousSibling(); prev != null; prev = prev.getPreviousSibling()) {
/* 142:199 */         prev.remove();
/* 143:    */       }
/* 144:    */     }
/* 145:204 */     if (endClone == null) {
/* 146:205 */       throw Context.reportRuntimeError("Unable to find end node clone.");
/* 147:    */     }
/* 148:207 */     deleteAfter(endClone, this.endOffset_);
/* 149:208 */     for (DomNode n = endClone; n != null; n = n.getParentNode()) {
/* 150:209 */       for (DomNode next = n.getNextSibling(); next != null; next = next.getNextSibling()) {
/* 151:210 */         next.remove();
/* 152:    */       }
/* 153:    */     }
/* 154:215 */     boolean foundStartNode = ancestor == start;
/* 155:216 */     boolean started = false;
/* 156:217 */     boolean foundEndNode = false;
/* 157:218 */     Iterator<DomNode> i = ancestor.getDescendants().iterator();
/* 158:219 */     while (i.hasNext())
/* 159:    */     {
/* 160:220 */       DomNode n = (DomNode)i.next();
/* 161:221 */       if (!foundStartNode)
/* 162:    */       {
/* 163:222 */         foundStartNode = n == start;
/* 164:223 */         if ((foundStartNode) && (isOffsetChars(n)))
/* 165:    */         {
/* 166:224 */           started = true;
/* 167:225 */           String text = getText(n);
/* 168:226 */           text = text.substring(0, this.startOffset_);
/* 169:227 */           setText(n, text);
/* 170:    */         }
/* 171:    */       }
/* 172:230 */       else if (!started)
/* 173:    */       {
/* 174:231 */         boolean atStart = (n.getParentNode() == start) && (n.getIndex() == this.startOffset_);
/* 175:232 */         boolean beyondStart = !start.isAncestorOf(n);
/* 176:233 */         started = (atStart) || (beyondStart);
/* 177:    */       }
/* 178:235 */       if (started)
/* 179:    */       {
/* 180:236 */         if (!foundEndNode) {
/* 181:237 */           foundEndNode = n == end;
/* 182:    */         }
/* 183:239 */         if (!foundEndNode)
/* 184:    */         {
/* 185:241 */           if (!n.isAncestorOfAny(new DomNode[] { start, end })) {
/* 186:242 */             i.remove();
/* 187:    */           }
/* 188:    */         }
/* 189:    */         else
/* 190:    */         {
/* 191:247 */           if (isOffsetChars(n))
/* 192:    */           {
/* 193:248 */             String text = getText(n);
/* 194:249 */             text = text.substring(this.endOffset_);
/* 195:250 */             setText(n, text);
/* 196:251 */             break;
/* 197:    */           }
/* 198:253 */           DomNodeList<DomNode> children = n.getChildNodes();
/* 199:254 */           for (int j = this.endOffset_ - 1; j >= 0; j--) {
/* 200:255 */             ((DomNode)children.get(j)).remove();
/* 201:    */           }
/* 202:258 */           break;
/* 203:    */         }
/* 204:    */       }
/* 205:    */     }
/* 206:264 */     SgmlPage page = ancestor.getPage();
/* 207:265 */     DomDocumentFragment fragment = new DomDocumentFragment(page);
/* 208:266 */     for (DomNode n : ancestorClone.getChildNodes()) {
/* 209:267 */       fragment.appendChild(n);
/* 210:    */     }
/* 211:269 */     return fragment;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean getCollapsed()
/* 215:    */     throws DOMException
/* 216:    */   {
/* 217:276 */     return (this.startContainer_ == this.endContainer_) && (this.startOffset_ == this.endOffset_);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Node getCommonAncestorContainer()
/* 221:    */     throws DOMException
/* 222:    */   {
/* 223:283 */     if ((this.startContainer_ != null) && (this.endContainer_ != null)) {
/* 224:284 */       for (Node p1 = this.startContainer_; p1 != null; p1 = p1.getParentNode()) {
/* 225:285 */         for (Node p2 = this.endContainer_; p2 != null; p2 = p2.getParentNode()) {
/* 226:286 */           if (p1 == p2) {
/* 227:287 */             return p1;
/* 228:    */           }
/* 229:    */         }
/* 230:    */       }
/* 231:    */     }
/* 232:292 */     return null;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Node getEndContainer()
/* 236:    */     throws DOMException
/* 237:    */   {
/* 238:299 */     return this.endContainer_;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public int getEndOffset()
/* 242:    */     throws DOMException
/* 243:    */   {
/* 244:306 */     return this.endOffset_;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Node getStartContainer()
/* 248:    */     throws DOMException
/* 249:    */   {
/* 250:313 */     return this.startContainer_;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public int getStartOffset()
/* 254:    */     throws DOMException
/* 255:    */   {
/* 256:320 */     return this.startOffset_;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void insertNode(Node newNode)
/* 260:    */     throws DOMException, RangeException
/* 261:    */   {
/* 262:327 */     throw new RuntimeException("Not implemented!");
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void selectNode(Node node)
/* 266:    */     throws RangeException, DOMException
/* 267:    */   {
/* 268:334 */     this.startContainer_ = node;
/* 269:335 */     this.startOffset_ = 0;
/* 270:336 */     this.endContainer_ = node;
/* 271:337 */     this.endOffset_ = getMaxOffset(node);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void selectNodeContents(Node node)
/* 275:    */     throws RangeException, DOMException
/* 276:    */   {
/* 277:344 */     this.startContainer_ = node.getFirstChild();
/* 278:345 */     this.startOffset_ = 0;
/* 279:346 */     this.endContainer_ = node.getLastChild();
/* 280:347 */     this.endOffset_ = getMaxOffset(node.getLastChild());
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setEnd(Node refNode, int offset)
/* 284:    */     throws RangeException, DOMException
/* 285:    */   {
/* 286:354 */     this.endContainer_ = refNode;
/* 287:355 */     this.endOffset_ = offset;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setEndAfter(Node refNode)
/* 291:    */     throws RangeException, DOMException
/* 292:    */   {
/* 293:362 */     throw new RuntimeException("Not implemented!");
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setEndBefore(Node refNode)
/* 297:    */     throws RangeException, DOMException
/* 298:    */   {
/* 299:369 */     throw new RuntimeException("Not implemented!");
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setStart(Node refNode, int offset)
/* 303:    */     throws RangeException, DOMException
/* 304:    */   {
/* 305:376 */     this.startContainer_ = refNode;
/* 306:377 */     this.startOffset_ = offset;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setStartAfter(Node refNode)
/* 310:    */     throws RangeException, DOMException
/* 311:    */   {
/* 312:384 */     throw new RuntimeException("Not implemented!");
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setStartBefore(Node refNode)
/* 316:    */     throws RangeException, DOMException
/* 317:    */   {
/* 318:391 */     throw new RuntimeException("Not implemented!");
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void surroundContents(Node newParent)
/* 322:    */     throws DOMException, RangeException
/* 323:    */   {
/* 324:398 */     throw new RuntimeException("Not implemented!");
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean equals(Object obj)
/* 328:    */   {
/* 329:406 */     if (!(obj instanceof SimpleRange)) {
/* 330:407 */       return false;
/* 331:    */     }
/* 332:409 */     SimpleRange other = (SimpleRange)obj;
/* 333:410 */     return new EqualsBuilder().append(this.startContainer_, other.startContainer_).append(this.endContainer_, other.endContainer_).append(this.startOffset_, other.startOffset_).append(this.endOffset_, other.endOffset_).isEquals();
/* 334:    */   }
/* 335:    */   
/* 336:    */   public int hashCode()
/* 337:    */   {
/* 338:422 */     return new HashCodeBuilder().append(this.startContainer_).append(this.endContainer_).append(this.startOffset_).append(this.endOffset_).toHashCode();
/* 339:    */   }
/* 340:    */   
/* 341:    */   public String toString()
/* 342:    */   {
/* 343:434 */     StringBuilder sb = new StringBuilder();
/* 344:435 */     append(sb);
/* 345:436 */     return sb.toString();
/* 346:    */   }
/* 347:    */   
/* 348:    */   private void append(StringBuilder sb)
/* 349:    */   {
/* 350:440 */     if (this.startContainer_ == this.endContainer_)
/* 351:    */     {
/* 352:441 */       if (this.startOffset_ == this.endOffset_) {
/* 353:442 */         return;
/* 354:    */       }
/* 355:444 */       if (isOffsetChars(this.startContainer_))
/* 356:    */       {
/* 357:445 */         sb.append(getText(this.startContainer_).substring(this.startOffset_, this.endOffset_));
/* 358:446 */         return;
/* 359:    */       }
/* 360:    */     }
/* 361:450 */     DomNode ancestor = (DomNode)getCommonAncestorContainer();
/* 362:451 */     DomNode start = (DomNode)this.startContainer_;
/* 363:452 */     DomNode end = (DomNode)this.endContainer_;
/* 364:453 */     boolean foundStartNode = ancestor == start;
/* 365:454 */     boolean started = false;
/* 366:455 */     boolean foundEndNode = false;
/* 367:456 */     Iterator<DomNode> i = ancestor.getDescendants().iterator();
/* 368:457 */     while (i.hasNext())
/* 369:    */     {
/* 370:458 */       DomNode n = (DomNode)i.next();
/* 371:459 */       if (!foundStartNode)
/* 372:    */       {
/* 373:460 */         foundStartNode = n == start;
/* 374:461 */         if ((foundStartNode) && (isOffsetChars(n)))
/* 375:    */         {
/* 376:462 */           started = true;
/* 377:463 */           String text = getText(n);
/* 378:464 */           text = text.substring(this.startOffset_);
/* 379:465 */           sb.append(text);
/* 380:    */         }
/* 381:    */       }
/* 382:468 */       else if (!started)
/* 383:    */       {
/* 384:469 */         boolean atStart = (n.getParentNode() == start) && (n.getIndex() == this.startOffset_);
/* 385:470 */         boolean beyondStart = !start.isAncestorOf(n);
/* 386:471 */         started = (atStart) || (beyondStart);
/* 387:    */       }
/* 388:473 */       if (started)
/* 389:    */       {
/* 390:474 */         if (!foundEndNode) {
/* 391:475 */           foundEndNode = n == end;
/* 392:    */         }
/* 393:477 */         if (!foundEndNode)
/* 394:    */         {
/* 395:479 */           if ((!n.isAncestorOfAny(new DomNode[] { start, end })) && (isOffsetChars(n))) {
/* 396:480 */             sb.append(getText(n));
/* 397:    */           }
/* 398:    */         }
/* 399:    */         else
/* 400:    */         {
/* 401:485 */           if (isOffsetChars(n))
/* 402:    */           {
/* 403:486 */             String text = getText(n);
/* 404:487 */             text = text.substring(0, this.endOffset_);
/* 405:488 */             sb.append(text);
/* 406:489 */             break;
/* 407:    */           }
/* 408:491 */           DomNodeList<DomNode> children = n.getChildNodes();
/* 409:492 */           for (int j = 0; j < this.endOffset_; j++) {
/* 410:493 */             sb.append(getText((Node)children.get(j)));
/* 411:    */           }
/* 412:496 */           break;
/* 413:    */         }
/* 414:    */       }
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   private static boolean isOffsetChars(Node node)
/* 419:    */   {
/* 420:503 */     return ((node instanceof DomText)) || ((node instanceof SelectableTextInput));
/* 421:    */   }
/* 422:    */   
/* 423:    */   private static String getText(Node node)
/* 424:    */   {
/* 425:507 */     if ((node instanceof SelectableTextInput)) {
/* 426:508 */       return ((SelectableTextInput)node).getText();
/* 427:    */     }
/* 428:510 */     return node.getTextContent();
/* 429:    */   }
/* 430:    */   
/* 431:    */   private static void setText(Node node, String text)
/* 432:    */   {
/* 433:514 */     if ((node instanceof SelectableTextInput)) {
/* 434:515 */       ((SelectableTextInput)node).setText(text);
/* 435:    */     } else {
/* 436:518 */       node.setTextContent(text);
/* 437:    */     }
/* 438:    */   }
/* 439:    */   
/* 440:    */   private static void deleteBefore(DomNode node, int offset)
/* 441:    */   {
/* 442:523 */     if (isOffsetChars(node))
/* 443:    */     {
/* 444:524 */       String text = getText(node);
/* 445:525 */       text = text.substring(offset);
/* 446:526 */       setText(node, text);
/* 447:    */     }
/* 448:    */     else
/* 449:    */     {
/* 450:529 */       DomNodeList<DomNode> children = node.getChildNodes();
/* 451:530 */       for (int i = 0; (i < offset) && (i < children.getLength()); i++)
/* 452:    */       {
/* 453:531 */         DomNode child = (DomNode)children.get(i);
/* 454:532 */         child.remove();
/* 455:533 */         i--;
/* 456:534 */         offset--;
/* 457:    */       }
/* 458:    */     }
/* 459:    */   }
/* 460:    */   
/* 461:    */   private static void deleteAfter(DomNode node, int offset)
/* 462:    */   {
/* 463:540 */     if (isOffsetChars(node))
/* 464:    */     {
/* 465:541 */       String text = getText(node);
/* 466:542 */       text = text.substring(0, offset);
/* 467:543 */       setText(node, text);
/* 468:    */     }
/* 469:    */     else
/* 470:    */     {
/* 471:546 */       DomNodeList<DomNode> children = node.getChildNodes();
/* 472:547 */       for (int i = offset; i < children.getLength(); i++)
/* 473:    */       {
/* 474:548 */         DomNode child = (DomNode)children.get(i);
/* 475:549 */         child.remove();
/* 476:550 */         i--;
/* 477:    */       }
/* 478:    */     }
/* 479:    */   }
/* 480:    */   
/* 481:    */   private static int getMaxOffset(Node node)
/* 482:    */   {
/* 483:556 */     return isOffsetChars(node) ? getText(node).length() : node.getChildNodes().getLength();
/* 484:    */   }
/* 485:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.impl.SimpleRange
 * JD-Core Version:    0.7.0.1
 */