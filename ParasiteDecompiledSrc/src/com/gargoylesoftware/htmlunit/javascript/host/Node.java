/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*   6:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentFragment;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  14:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  15:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*  16:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLHtmlElement;
/*  17:    */ import com.gargoylesoftware.htmlunit.javascript.host.xml.XMLSerializer;
/*  18:    */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Iterator;
/*  21:    */ import java.util.List;
/*  22:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  23:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  24:    */ import net.sourceforge.htmlunit.corejs.javascript.Interpreter;
/*  25:    */ import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
/*  26:    */ import net.sourceforge.htmlunit.corejs.javascript.RhinoException;
/*  27:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  28:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  29:    */ import org.apache.commons.lang.ArrayUtils;
/*  30:    */ import org.apache.commons.lang.StringUtils;
/*  31:    */ 
/*  32:    */ public class Node
/*  33:    */   extends SimpleScriptable
/*  34:    */ {
/*  35:    */   private HTMLCollection childNodes_;
/*  36:    */   private EventListenersContainer eventListenersContainer_;
/*  37:    */   public static final short ELEMENT_NODE = 1;
/*  38:    */   public static final short ATTRIBUTE_NODE = 2;
/*  39:    */   public static final short TEXT_NODE = 3;
/*  40:    */   public static final short CDATA_SECTION_NODE = 4;
/*  41:    */   public static final short ENTITY_REFERENCE_NODE = 5;
/*  42:    */   public static final short ENTITY_NODE = 6;
/*  43:    */   public static final short PROCESSING_INSTRUCTION_NODE = 7;
/*  44:    */   public static final short COMMENT_NODE = 8;
/*  45:    */   public static final short DOCUMENT_NODE = 9;
/*  46:    */   public static final short DOCUMENT_TYPE_NODE = 10;
/*  47:    */   public static final short DOCUMENT_FRAGMENT_NODE = 11;
/*  48:    */   public static final short NOTATION_NODE = 12;
/*  49:    */   public static final short DOCUMENT_POSITION_DISCONNECTED = 1;
/*  50:    */   public static final short DOCUMENT_POSITION_PRECEDING = 2;
/*  51:    */   public static final short DOCUMENT_POSITION_FOLLOWING = 4;
/*  52:    */   public static final short DOCUMENT_POSITION_CONTAINS = 8;
/*  53:    */   public static final short DOCUMENT_POSITION_CONTAINED_BY = 16;
/*  54:    */   public static final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;
/*  55:    */   
/*  56:    */   public short jsxGet_nodeType()
/*  57:    */   {
/*  58:133 */     return getDomNodeOrDie().getNodeType();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String jsxGet_nodeName()
/*  62:    */   {
/*  63:141 */     return getDomNodeOrDie().getNodeName();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String jsxGet_nodeValue()
/*  67:    */   {
/*  68:149 */     return getDomNodeOrDie().getNodeValue();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void jsxSet_nodeValue(String newValue)
/*  72:    */   {
/*  73:157 */     getDomNodeOrDie().setNodeValue(newValue);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object jsxFunction_appendChild(Object childObject)
/*  77:    */   {
/*  78:166 */     Object appendedChild = null;
/*  79:167 */     if ((childObject instanceof Node))
/*  80:    */     {
/*  81:168 */       Node childNode = (Node)childObject;
/*  82:171 */       if (!isNodeInsertable(childNode))
/*  83:    */       {
/*  84:172 */         if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_117)) {
/*  85:173 */           return childObject;
/*  86:    */         }
/*  87:176 */         throw asJavaScriptException(new DOMException("Node cannot be inserted at the specified point in the hierarchy", (short)3));
/*  88:    */       }
/*  89:182 */       DomNode childDomNode = childNode.getDomNodeOrDie();
/*  90:    */       
/*  91:    */ 
/*  92:185 */       DomNode parentNode = getDomNodeOrDie();
/*  93:    */       
/*  94:    */ 
/*  95:188 */       parentNode.appendChild(childDomNode);
/*  96:189 */       appendedChild = childObject;
/*  97:193 */       if ((!(parentNode instanceof SgmlPage)) && (!(this instanceof DocumentFragment)) && (parentNode.getParentNode() == null) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_118)))
/*  98:    */       {
/*  99:196 */         DomDocumentFragment fragment = parentNode.getPage().createDomDocumentFragment();
/* 100:197 */         fragment.appendChild(parentNode);
/* 101:    */       }
/* 102:    */     }
/* 103:200 */     return appendedChild;
/* 104:    */   }
/* 105:    */   
/* 106:    */   private RhinoException asJavaScriptException(DOMException exception)
/* 107:    */   {
/* 108:204 */     exception.setPrototype(getWindow().getPrototype(exception.getClass()));
/* 109:205 */     exception.setParentScope(getWindow());
/* 110:    */     int lineNumber;
/* 111:212 */     if (Context.getCurrentContext().getOptimizationLevel() == -1)
/* 112:    */     {
/* 113:213 */       int[] linep = new int[1];
/* 114:214 */       String sourceName = new Interpreter().getSourcePositionFromStack(Context.getCurrentContext(), linep);
/* 115:215 */       String fileName = sourceName.replaceFirst("script in (.*) from .*", "$1");
/* 116:216 */       lineNumber = linep[0];
/* 117:    */     }
/* 118:    */     else
/* 119:    */     {
/* 120:219 */       throw new Error("HtmlUnit not ready to run in compiled mode");
/* 121:    */     }
/* 122:    */     int lineNumber;
/* 123:    */     String fileName;
/* 124:222 */     exception.setLocation(fileName, lineNumber);
/* 125:    */     
/* 126:224 */     return new JavaScriptException(exception, fileName, lineNumber);
/* 127:    */   }
/* 128:    */   
/* 129:    */   private boolean isNodeInsertable(Node childObject)
/* 130:    */   {
/* 131:233 */     return !(childObject instanceof HTMLHtmlElement);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Object jsxFunction_cloneNode(boolean deep)
/* 135:    */   {
/* 136:242 */     DomNode domNode = getDomNodeOrDie();
/* 137:243 */     DomNode clonedNode = domNode.cloneNode(deep);
/* 138:    */     
/* 139:245 */     Node jsClonedNode = getJavaScriptNode(clonedNode);
/* 140:246 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_119)) {
/* 141:248 */       copyEventListenersWhenNeeded(domNode, clonedNode);
/* 142:    */     }
/* 143:250 */     return jsClonedNode;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void copyEventListenersWhenNeeded(DomNode domNode, DomNode clonedNode)
/* 147:    */   {
/* 148:254 */     Node jsNode = (Node)domNode.getScriptObject();
/* 149:255 */     if (jsNode != null)
/* 150:    */     {
/* 151:256 */       Node jsClonedNode = getJavaScriptNode(clonedNode);
/* 152:257 */       jsClonedNode.getEventListenersContainer().copyFrom(jsNode.getEventListenersContainer());
/* 153:    */     }
/* 154:261 */     DomNode child = domNode.getFirstChild();
/* 155:262 */     DomNode clonedChild = clonedNode.getFirstChild();
/* 156:263 */     while ((child != null) && (clonedChild != null))
/* 157:    */     {
/* 158:264 */       copyEventListenersWhenNeeded(child, clonedChild);
/* 159:265 */       child = child.getNextSibling();
/* 160:266 */       clonedChild = clonedChild.getNextSibling();
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static Object jsxFunction_insertBefore(Context context, Scriptable thisObj, Object[] args, Function function)
/* 165:    */   {
/* 166:281 */     return ((Node)thisObj).jsxFunction_insertBefore(args);
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected Object jsxFunction_insertBefore(Object[] args)
/* 170:    */   {
/* 171:291 */     Object newChildObject = args[0];
/* 172:    */     Object refChildObject;
/* 173:    */     Object refChildObject;
/* 174:293 */     if (args.length > 1) {
/* 175:294 */       refChildObject = args[1];
/* 176:    */     } else {
/* 177:297 */       refChildObject = Undefined.instance;
/* 178:    */     }
/* 179:299 */     Object appendedChild = null;
/* 180:301 */     if ((newChildObject instanceof Node))
/* 181:    */     {
/* 182:302 */       Node newChild = (Node)newChildObject;
/* 183:303 */       DomNode newChildNode = newChild.getDomNodeOrDie();
/* 184:306 */       if (!isNodeInsertable(newChild))
/* 185:    */       {
/* 186:307 */         if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_120)) {
/* 187:308 */           return newChildNode;
/* 188:    */         }
/* 189:310 */         throw Context.reportRuntimeError("Node cannot be inserted at the specified point in the hierarchy");
/* 190:    */       }
/* 191:313 */       if ((newChildNode instanceof DomDocumentFragment))
/* 192:    */       {
/* 193:314 */         DomDocumentFragment fragment = (DomDocumentFragment)newChildNode;
/* 194:315 */         for (DomNode child : fragment.getChildren()) {
/* 195:316 */           jsxFunction_insertBefore(new Object[] { child.getScriptObject(), refChildObject });
/* 196:    */         }
/* 197:318 */         return newChildObject;
/* 198:    */       }
/* 199:    */       DomNode refChildNode;
/* 200:322 */       if (refChildObject == Undefined.instance)
/* 201:    */       {
/* 202:    */         DomNode refChildNode;
/* 203:323 */         if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_121))
/* 204:    */         {
/* 205:324 */           if (args.length > 1) {
/* 206:325 */             throw Context.reportRuntimeError("Invalid argument.");
/* 207:    */           }
/* 208:327 */           refChildNode = null;
/* 209:    */         }
/* 210:    */         else
/* 211:    */         {
/* 212:    */           DomNode refChildNode;
/* 213:330 */           if (args.length == 2) {
/* 214:331 */             refChildNode = null;
/* 215:    */           } else {
/* 216:334 */             throw Context.reportRuntimeError("insertBefore: not enough arguments");
/* 217:    */           }
/* 218:    */         }
/* 219:    */       }
/* 220:    */       else
/* 221:    */       {
/* 222:    */         DomNode refChildNode;
/* 223:338 */         if (refChildObject != null) {
/* 224:339 */           refChildNode = ((Node)refChildObject).getDomNodeOrDie();
/* 225:    */         } else {
/* 226:342 */           refChildNode = null;
/* 227:    */         }
/* 228:    */       }
/* 229:345 */       DomNode domNode = getDomNodeOrDie();
/* 230:347 */       if (refChildNode != null)
/* 231:    */       {
/* 232:348 */         refChildNode.insertBefore(newChildNode);
/* 233:349 */         appendedChild = newChildObject;
/* 234:    */       }
/* 235:    */       else
/* 236:    */       {
/* 237:352 */         domNode.appendChild(newChildNode);
/* 238:353 */         appendedChild = newChildObject;
/* 239:    */       }
/* 240:357 */       if ((domNode.getParentNode() == null) && (getWindow().getWebWindow().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_122)))
/* 241:    */       {
/* 242:360 */         DomDocumentFragment fragment = domNode.getPage().createDomDocumentFragment();
/* 243:361 */         fragment.appendChild(domNode);
/* 244:    */       }
/* 245:    */     }
/* 246:364 */     return appendedChild;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public boolean jsxFunction_isSameNode(Object other)
/* 250:    */   {
/* 251:379 */     return other == this;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Object jsxFunction_removeChild(Object childObject)
/* 255:    */   {
/* 256:388 */     Object removedChild = null;
/* 257:390 */     if ((childObject instanceof Node))
/* 258:    */     {
/* 259:392 */       DomNode childNode = ((Node)childObject).getDomNodeOrDie();
/* 260:    */       
/* 261:    */ 
/* 262:395 */       childNode.remove();
/* 263:396 */       removedChild = childObject;
/* 264:    */     }
/* 265:398 */     return removedChild;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public boolean jsxFunction_hasChildNodes()
/* 269:    */   {
/* 270:406 */     return getDomNodeOrDie().getChildren().iterator().hasNext();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public HTMLCollection jsxGet_childNodes()
/* 274:    */   {
/* 275:414 */     if (this.childNodes_ == null)
/* 276:    */     {
/* 277:415 */       final DomNode node = getDomNodeOrDie();
/* 278:416 */       boolean isXmlPage = node.getOwnerDocument() instanceof XmlPage;
/* 279:417 */       boolean isIE = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_45);
/* 280:418 */       Boolean xmlSpaceDefault = isXMLSpaceDefault(node);
/* 281:419 */       final boolean skipEmptyTextNode = (isIE) && (isXmlPage) && (!Boolean.FALSE.equals(xmlSpaceDefault));
/* 282:    */       
/* 283:421 */       this.childNodes_ = new HTMLCollection(node, false, "Node.childNodes")
/* 284:    */       {
/* 285:    */         protected List<Object> computeElements()
/* 286:    */         {
/* 287:424 */           List<Object> response = new ArrayList();
/* 288:425 */           for (DomNode child : node.getChildren()) {
/* 289:427 */             if ((!skipEmptyTextNode) || (!(child instanceof DomText)) || (!StringUtils.isBlank(((DomText)child).getNodeValue()))) {
/* 290:431 */               response.add(child);
/* 291:    */             }
/* 292:    */           }
/* 293:434 */           return response;
/* 294:    */         }
/* 295:    */       };
/* 296:    */     }
/* 297:438 */     return this.childNodes_;
/* 298:    */   }
/* 299:    */   
/* 300:    */   private static Boolean isXMLSpaceDefault(DomNode node)
/* 301:    */   {
/* 302:448 */     for (; (node instanceof DomElement); node = node.getParentNode())
/* 303:    */     {
/* 304:449 */       String value = ((DomElement)node).getAttribute("xml:space");
/* 305:450 */       if (value.length() != 0)
/* 306:    */       {
/* 307:451 */         if ("default".equals(value)) {
/* 308:452 */           return Boolean.TRUE;
/* 309:    */         }
/* 310:454 */         return Boolean.FALSE;
/* 311:    */       }
/* 312:    */     }
/* 313:457 */     return null;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Object jsxFunction_replaceChild(Object newChildObject, Object oldChildObject)
/* 317:    */   {
/* 318:467 */     Object removedChild = null;
/* 319:469 */     if ((newChildObject instanceof DocumentFragment))
/* 320:    */     {
/* 321:470 */       DocumentFragment fragment = (DocumentFragment)newChildObject;
/* 322:471 */       Node firstNode = null;
/* 323:472 */       Node refChildObject = ((Node)oldChildObject).jsxGet_nextSibling();
/* 324:473 */       for (DomNode node : fragment.getDomNodeOrDie().getChildren()) {
/* 325:474 */         if (firstNode == null)
/* 326:    */         {
/* 327:475 */           jsxFunction_replaceChild(node.getScriptObject(), oldChildObject);
/* 328:476 */           firstNode = (Node)node.getScriptObject();
/* 329:    */         }
/* 330:    */         else
/* 331:    */         {
/* 332:479 */           jsxFunction_insertBefore(new Object[] { node.getScriptObject(), refChildObject });
/* 333:    */         }
/* 334:    */       }
/* 335:482 */       if (firstNode == null) {
/* 336:483 */         jsxFunction_removeChild(oldChildObject);
/* 337:    */       }
/* 338:485 */       removedChild = oldChildObject;
/* 339:    */     }
/* 340:487 */     else if (((newChildObject instanceof Node)) && ((oldChildObject instanceof Node)))
/* 341:    */     {
/* 342:488 */       Node newChild = (Node)newChildObject;
/* 343:491 */       if (!isNodeInsertable(newChild)) {
/* 344:492 */         throw Context.reportRuntimeError("Node cannot be inserted at the specified point in the hierarchy");
/* 345:    */       }
/* 346:496 */       DomNode newChildNode = newChild.getDomNodeOrDie();
/* 347:    */       
/* 348:    */ 
/* 349:499 */       DomNode oldChildNode = ((Node)oldChildObject).getDomNodeOrDie();
/* 350:500 */       oldChildNode.replace(newChildNode);
/* 351:501 */       removedChild = oldChildObject;
/* 352:    */     }
/* 353:504 */     return removedChild;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public Node getParent()
/* 357:    */   {
/* 358:512 */     return getJavaScriptNode(getDomNodeOrDie().getParentNode());
/* 359:    */   }
/* 360:    */   
/* 361:    */   public Object jsxGet_parentNode()
/* 362:    */   {
/* 363:521 */     return getJavaScriptNode(getDomNodeOrDie().getParentNode());
/* 364:    */   }
/* 365:    */   
/* 366:    */   public Node jsxGet_nextSibling()
/* 367:    */   {
/* 368:531 */     return getJavaScriptNode(getDomNodeOrDie().getNextSibling());
/* 369:    */   }
/* 370:    */   
/* 371:    */   public Node jsxGet_previousSibling()
/* 372:    */   {
/* 373:541 */     return getJavaScriptNode(getDomNodeOrDie().getPreviousSibling());
/* 374:    */   }
/* 375:    */   
/* 376:    */   public Node jsxGet_firstChild()
/* 377:    */   {
/* 378:551 */     return getJavaScriptNode(getDomNodeOrDie().getFirstChild());
/* 379:    */   }
/* 380:    */   
/* 381:    */   public Node jsxGet_lastChild()
/* 382:    */   {
/* 383:561 */     return getJavaScriptNode(getDomNodeOrDie().getLastChild());
/* 384:    */   }
/* 385:    */   
/* 386:    */   protected Node getJavaScriptNode(DomNode domNode)
/* 387:    */   {
/* 388:570 */     if (domNode == null) {
/* 389:571 */       return null;
/* 390:    */     }
/* 391:573 */     return (Node)getScriptableFor(domNode);
/* 392:    */   }
/* 393:    */   
/* 394:    */   public boolean jsxFunction_attachEvent(String type, Function listener)
/* 395:    */   {
/* 396:584 */     return getEventListenersContainer().addEventListener(StringUtils.substring(type, 2), listener, false);
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void jsxFunction_addEventListener(String type, Function listener, boolean useCapture)
/* 400:    */   {
/* 401:595 */     getEventListenersContainer().addEventListener(type, listener, useCapture);
/* 402:    */   }
/* 403:    */   
/* 404:    */   private EventListenersContainer getEventListenersContainer()
/* 405:    */   {
/* 406:603 */     if (this.eventListenersContainer_ == null) {
/* 407:604 */       this.eventListenersContainer_ = new EventListenersContainer(this);
/* 408:    */     }
/* 409:606 */     return this.eventListenersContainer_;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void jsxFunction_detachEvent(String type, Function listener)
/* 413:    */   {
/* 414:616 */     jsxFunction_removeEventListener(StringUtils.substring(type, 2), listener, false);
/* 415:    */   }
/* 416:    */   
/* 417:    */   public void jsxFunction_removeEventListener(String type, Function listener, boolean useCapture)
/* 418:    */   {
/* 419:627 */     getEventListenersContainer().removeEventListener(type, listener, useCapture);
/* 420:    */   }
/* 421:    */   
/* 422:    */   public ScriptResult executeEvent(Event event)
/* 423:    */   {
/* 424:636 */     if (this.eventListenersContainer_ != null)
/* 425:    */     {
/* 426:638 */       HtmlPage page = (HtmlPage)getDomNodeOrDie().getPage();
/* 427:639 */       boolean isIE = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_123);
/* 428:640 */       Window window = (Window)page.getEnclosingWindow().getScriptObject();
/* 429:641 */       Object[] args = { event };
/* 430:    */       Object[] propHandlerArgs;
/* 431:    */       Object[] propHandlerArgs;
/* 432:645 */       if (isIE) {
/* 433:646 */         propHandlerArgs = ArrayUtils.EMPTY_OBJECT_ARRAY;
/* 434:    */       } else {
/* 435:649 */         propHandlerArgs = args;
/* 436:    */       }
/* 437:652 */       window.setCurrentEvent(event);
/* 438:    */       try
/* 439:    */       {
/* 440:654 */         return this.eventListenersContainer_.executeListeners(event, args, propHandlerArgs);
/* 441:    */       }
/* 442:    */       finally
/* 443:    */       {
/* 444:657 */         window.setCurrentEvent(null);
/* 445:    */       }
/* 446:    */     }
/* 447:661 */     return null;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public ScriptResult fireEvent(Event event)
/* 451:    */   {
/* 452:670 */     HtmlPage page = (HtmlPage)getDomNodeOrDie().getPage();
/* 453:671 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_124);
/* 454:672 */     Window window = (Window)page.getEnclosingWindow().getScriptObject();
/* 455:673 */     Object[] args = { event };
/* 456:    */     
/* 457:675 */     event.startFire();
/* 458:676 */     ScriptResult result = null;
/* 459:677 */     Event previousEvent = window.getCurrentEvent();
/* 460:678 */     window.setCurrentEvent(event);
/* 461:    */     try
/* 462:    */     {
/* 463:682 */       EventListenersContainer windowsListeners = getWindow().getEventListenersContainer();
/* 464:    */       
/* 465:    */ 
/* 466:685 */       event.setEventPhase((short)1);
/* 467:686 */       result = windowsListeners.executeCapturingListeners(event, args);
/* 468:687 */       if (event.isPropagationStopped()) {
/* 469:688 */         return result;
/* 470:    */       }
/* 471:690 */       Object parents = new ArrayList();
/* 472:691 */       DomNode node = getDomNodeOrDie();
/* 473:692 */       while (node != null)
/* 474:    */       {
/* 475:693 */         ((List)parents).add(node);
/* 476:694 */         node = node.getParentNode();
/* 477:    */       }
/* 478:    */       ScriptResult r;
/* 479:696 */       for (int i = ((List)parents).size() - 1; i >= 0; i--)
/* 480:    */       {
/* 481:697 */         DomNode curNode = (DomNode)((List)parents).get(i);
/* 482:698 */         Node jsNode = (Node)curNode.getScriptObject();
/* 483:699 */         EventListenersContainer elc = jsNode.eventListenersContainer_;
/* 484:700 */         if (elc != null)
/* 485:    */         {
/* 486:701 */           r = elc.executeCapturingListeners(event, args);
/* 487:702 */           result = ScriptResult.combine(r, result, ie);
/* 488:703 */           if (event.isPropagationStopped()) {
/* 489:704 */             return result;
/* 490:    */           }
/* 491:    */         }
/* 492:    */       }
/* 493:    */       Object[] propHandlerArgs;
/* 494:    */       Object[] propHandlerArgs;
/* 495:711 */       if (ie) {
/* 496:712 */         propHandlerArgs = ArrayUtils.EMPTY_OBJECT_ARRAY;
/* 497:    */       } else {
/* 498:715 */         propHandlerArgs = args;
/* 499:    */       }
/* 500:719 */       event.setEventPhase((short)2);
/* 501:720 */       node = getDomNodeOrDie();
/* 502:721 */       while (node != null)
/* 503:    */       {
/* 504:722 */         Node jsNode = (Node)node.getScriptObject();
/* 505:723 */         EventListenersContainer elc = jsNode.eventListenersContainer_;
/* 506:724 */         if (elc != null)
/* 507:    */         {
/* 508:725 */           ScriptResult r = elc.executeBubblingListeners(event, args, propHandlerArgs);
/* 509:726 */           result = ScriptResult.combine(r, result, ie);
/* 510:727 */           if (event.isPropagationStopped()) {
/* 511:728 */             return result;
/* 512:    */           }
/* 513:    */         }
/* 514:731 */         node = node.getParentNode();
/* 515:732 */         event.setEventPhase((short)3);
/* 516:    */       }
/* 517:735 */       ScriptResult r = windowsListeners.executeBubblingListeners(event, args, propHandlerArgs);
/* 518:736 */       result = ScriptResult.combine(r, result, ie);
/* 519:    */     }
/* 520:    */     finally
/* 521:    */     {
/* 522:739 */       event.endFire();
/* 523:740 */       window.setCurrentEvent(previousEvent);
/* 524:    */     }
/* 525:743 */     return result;
/* 526:    */   }
/* 527:    */   
/* 528:    */   public Function getEventHandler(String eventName)
/* 529:    */   {
/* 530:752 */     if (this.eventListenersContainer_ == null) {
/* 531:753 */       return null;
/* 532:    */     }
/* 533:755 */     return this.eventListenersContainer_.getEventHandler(StringUtils.substring(eventName, 2));
/* 534:    */   }
/* 535:    */   
/* 536:    */   public boolean hasEventHandlers(String eventName)
/* 537:    */   {
/* 538:764 */     if (this.eventListenersContainer_ == null) {
/* 539:765 */       return false;
/* 540:    */     }
/* 541:767 */     return this.eventListenersContainer_.hasEventHandlers(StringUtils.substring(eventName, 2));
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setEventHandler(String eventName, Function eventHandler)
/* 545:    */   {
/* 546:776 */     setEventHandlerProp(eventName, eventHandler);
/* 547:    */   }
/* 548:    */   
/* 549:    */   protected void setEventHandlerProp(String eventName, Object value)
/* 550:    */   {
/* 551:785 */     getEventListenersContainer().setEventHandlerProp(StringUtils.substring(eventName.toLowerCase(), 2), value);
/* 552:    */   }
/* 553:    */   
/* 554:    */   protected Object getEventHandlerProp(String eventName)
/* 555:    */   {
/* 556:794 */     if (this.eventListenersContainer_ == null) {
/* 557:795 */       return null;
/* 558:    */     }
/* 559:797 */     return this.eventListenersContainer_.getEventHandlerProp(StringUtils.substring(eventName.toLowerCase(), 2));
/* 560:    */   }
/* 561:    */   
/* 562:    */   public Object jsxGet_ownerDocument()
/* 563:    */   {
/* 564:805 */     Object document = getDomNodeOrDie().getOwnerDocument();
/* 565:806 */     if (document == null) {
/* 566:807 */       return null;
/* 567:    */     }
/* 568:809 */     return ((SgmlPage)document).getScriptObject();
/* 569:    */   }
/* 570:    */   
/* 571:    */   public String jsxGet_prefix()
/* 572:    */   {
/* 573:817 */     DomNode domNode = getDomNodeOrDie();
/* 574:818 */     String prefix = domNode.getPrefix();
/* 575:819 */     if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_125)) && ((prefix == null) || ((domNode.getPage() instanceof HtmlPage)))) {
/* 576:821 */       return "";
/* 577:    */     }
/* 578:823 */     return prefix;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public String jsxGet_localName()
/* 582:    */   {
/* 583:831 */     return getDomNodeOrDie().getLocalName();
/* 584:    */   }
/* 585:    */   
/* 586:    */   public String jsxGet_namespaceURI()
/* 587:    */   {
/* 588:839 */     String namespaceURI = getDomNodeOrDie().getNamespaceURI();
/* 589:840 */     if ((namespaceURI == null) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_126))) {
/* 590:841 */       return "";
/* 591:    */     }
/* 592:843 */     return namespaceURI;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public void setDomNode(DomNode domNode)
/* 596:    */   {
/* 597:851 */     super.setDomNode(domNode);
/* 598:852 */     if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_127)) && (!(getDomNodeOrDie().getPage() instanceof HtmlPage)))
/* 599:    */     {
/* 600:854 */       ActiveXObject.addProperty(this, "namespaceURI", true, false);
/* 601:855 */       ActiveXObject.addProperty(this, "prefix", true, false);
/* 602:    */     }
/* 603:    */   }
/* 604:    */   
/* 605:    */   public short jsxFunction_compareDocumentPosition(Node node)
/* 606:    */   {
/* 607:867 */     return getDomNodeOrDie().compareDocumentPosition(node.getDomNodeOrDie());
/* 608:    */   }
/* 609:    */   
/* 610:    */   public void jsxFunction_normalize()
/* 611:    */   {
/* 612:874 */     getDomNodeOrDie().normalize();
/* 613:    */   }
/* 614:    */   
/* 615:    */   public Object jsxGet_xml()
/* 616:    */   {
/* 617:882 */     DomNode node = getDomNodeOrDie();
/* 618:883 */     if ((node.getPage() instanceof XmlPage))
/* 619:    */     {
/* 620:884 */       if ((this instanceof Element))
/* 621:    */       {
/* 622:885 */         XMLSerializer serializer = new XMLSerializer();
/* 623:886 */         serializer.setParentScope(getParentScope());
/* 624:887 */         String xml = serializer.jsxFunction_serializeToString(this);
/* 625:888 */         if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_XML_SERIALIZER_APPENDS_CRLF)) && (xml.endsWith("\r\n"))) {
/* 626:890 */           xml = xml.substring(0, xml.length() - 2);
/* 627:    */         }
/* 628:892 */         return xml;
/* 629:    */       }
/* 630:894 */       return node.asXml();
/* 631:    */     }
/* 632:896 */     return Undefined.instance;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public String jsxGet_textContent()
/* 636:    */   {
/* 637:904 */     return getDomNodeOrDie().getTextContent();
/* 638:    */   }
/* 639:    */   
/* 640:    */   public String jsxGet_innerText()
/* 641:    */   {
/* 642:912 */     return "";
/* 643:    */   }
/* 644:    */   
/* 645:    */   public void jsxSet_innerText(String value) {}
/* 646:    */   
/* 647:    */   public void jsxSet_textContent(Object value)
/* 648:    */   {
/* 649:928 */     getDomNodeOrDie().setTextContent(value == null ? null : Context.toString(value));
/* 650:    */   }
/* 651:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Node
 * JD-Core Version:    0.7.0.1
 */