/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomChangeEvent;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomChangeListener;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeEvent;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeListener;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  13:    */ import com.gargoylesoftware.htmlunit.javascript.configuration.ClassConfiguration;
/*  14:    */ import com.gargoylesoftware.htmlunit.javascript.configuration.JavaScriptConfiguration;
/*  15:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Collections;
/*  18:    */ import java.util.List;
/*  19:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  20:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  21:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  22:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  23:    */ import org.w3c.dom.Node;
/*  24:    */ import org.w3c.dom.NodeList;
/*  25:    */ 
/*  26:    */ public class HTMLCollection
/*  27:    */   extends SimpleScriptable
/*  28:    */   implements Function, NodeList
/*  29:    */ {
/*  30:    */   @Deprecated
/*  31:    */   public HTMLCollection() {}
/*  32:    */   
/*  33:    */   protected static enum EffectOnCache
/*  34:    */   {
/*  35: 63 */     NONE,  RESET;
/*  36:    */     
/*  37:    */     private EffectOnCache() {}
/*  38:    */   }
/*  39:    */   
/*  40: 68 */   private boolean avoidObjectDetection_ = false;
/*  41:    */   private String description_;
/*  42: 70 */   private boolean attributeChangeSensitive_ = true;
/*  43:    */   private List<Object> cachedElements_;
/*  44: 80 */   private int currentIndex_ = 0;
/*  45:    */   private boolean listenerRegistered_;
/*  46:    */   
/*  47:    */   private HTMLCollection(ScriptableObject parentScope)
/*  48:    */   {
/*  49: 98 */     setParentScope(parentScope);
/*  50: 99 */     setPrototype(getPrototype(getClass()));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public HTMLCollection(DomNode parentScope, boolean attributeChangeSensitive, String description)
/*  54:    */   {
/*  55:110 */     this(parentScope.getScriptObject());
/*  56:111 */     setDomNode(parentScope, false);
/*  57:112 */     this.description_ = description;
/*  58:113 */     this.attributeChangeSensitive_ = attributeChangeSensitive;
/*  59:    */   }
/*  60:    */   
/*  61:    */   HTMLCollection(DomNode parentScope, List<?> initialElements)
/*  62:    */   {
/*  63:122 */     this(parentScope.getScriptObject());
/*  64:123 */     this.cachedElements_ = new ArrayList(initialElements);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static HTMLCollection emptyCollection(Window window)
/*  68:    */   {
/*  69:132 */     final List<Object> list = Collections.emptyList();
/*  70:133 */     new HTMLCollection(window, list)
/*  71:    */     {
/*  72:    */       protected List<Object> getElements()
/*  73:    */       {
/*  74:136 */         return list;
/*  75:    */       }
/*  76:    */     };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean avoidObjectDetection()
/*  80:    */   {
/*  81:147 */     return this.avoidObjectDetection_;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setAvoidObjectDetection(boolean newValue)
/*  85:    */   {
/*  86:154 */     this.avoidObjectDetection_ = newValue;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  90:    */   {
/*  91:161 */     if (args.length == 0) {
/*  92:162 */       throw Context.reportRuntimeError("Zero arguments; need an index or a key.");
/*  93:    */     }
/*  94:164 */     return nullIfNotFound(getIt(args[0]));
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  98:    */   {
/*  99:171 */     return null;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private Object getIt(Object o)
/* 103:    */   {
/* 104:181 */     if ((o instanceof Number))
/* 105:    */     {
/* 106:182 */       Number n = (Number)o;
/* 107:183 */       int i = n.intValue();
/* 108:184 */       return get(i, this);
/* 109:    */     }
/* 110:186 */     String key = String.valueOf(o);
/* 111:187 */     return get(key, this);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public final Object get(int index, Scriptable start)
/* 115:    */   {
/* 116:196 */     HTMLCollection array = (HTMLCollection)start;
/* 117:197 */     List<Object> elements = array.getElements();
/* 118:198 */     if ((index >= 0) && (index < elements.size())) {
/* 119:199 */       return getScriptableForElement(elements.get(index));
/* 120:    */     }
/* 121:201 */     return NOT_FOUND;
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected List<Object> getElements()
/* 125:    */   {
/* 126:210 */     List<Object> cachedElements = this.cachedElements_;
/* 127:212 */     if (cachedElements == null)
/* 128:    */     {
/* 129:213 */       cachedElements = computeElements();
/* 130:214 */       this.cachedElements_ = cachedElements;
/* 131:215 */       if (!this.listenerRegistered_)
/* 132:    */       {
/* 133:216 */         DomHtmlAttributeChangeListenerImpl listener = new DomHtmlAttributeChangeListenerImpl(null);
/* 134:217 */         DomNode domNode = getDomNodeOrNull();
/* 135:218 */         domNode.addDomChangeListener(listener);
/* 136:219 */         if ((this.attributeChangeSensitive_) && ((domNode instanceof HtmlElement))) {
/* 137:220 */           ((HtmlElement)domNode).addHtmlAttributeChangeListener(listener);
/* 138:    */         }
/* 139:222 */         this.listenerRegistered_ = true;
/* 140:    */       }
/* 141:    */     }
/* 142:228 */     return cachedElements;
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected List<Object> computeElements()
/* 146:    */   {
/* 147:236 */     List<Object> response = new ArrayList();
/* 148:237 */     DomNode domNode = getDomNodeOrNull();
/* 149:238 */     if (domNode == null) {
/* 150:239 */       return response;
/* 151:    */     }
/* 152:241 */     for (DomNode node : getCandidates()) {
/* 153:242 */       if (((node instanceof DomElement)) && (isMatching(node))) {
/* 154:243 */         response.add(node);
/* 155:    */       }
/* 156:    */     }
/* 157:246 */     return response;
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected Iterable<DomNode> getCandidates()
/* 161:    */   {
/* 162:255 */     DomNode domNode = getDomNodeOrNull();
/* 163:256 */     return domNode.getDescendants();
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected boolean isMatching(DomNode node)
/* 167:    */   {
/* 168:266 */     return false;
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected Object getWithPreemption(String name)
/* 172:    */   {
/* 173:281 */     if ("length".equals(name)) {
/* 174:282 */       return NOT_FOUND;
/* 175:    */     }
/* 176:285 */     List<Object> elements = getElements();
/* 177:    */     
/* 178:    */ 
/* 179:288 */     List<Object> matchingElements = new ArrayList();
/* 180:290 */     for (Object next : elements) {
/* 181:291 */       if ((next instanceof DomElement))
/* 182:    */       {
/* 183:292 */         String id = ((DomElement)next).getAttribute("id");
/* 184:293 */         if (name.equals(id))
/* 185:    */         {
/* 186:294 */           if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLCOLLECTION_IDENTICAL_IDS)) {
/* 187:295 */             return getScriptableForElement(next);
/* 188:    */           }
/* 189:297 */           matchingElements.add(next);
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:302 */     if (matchingElements.size() == 1) {
/* 194:303 */       return getScriptableForElement(matchingElements.get(0));
/* 195:    */     }
/* 196:305 */     if (!matchingElements.isEmpty())
/* 197:    */     {
/* 198:306 */       HTMLCollection collection = new HTMLCollection(getDomNodeOrDie(), matchingElements);
/* 199:307 */       collection.setAvoidObjectDetection(!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_46));
/* 200:308 */       return collection;
/* 201:    */     }
/* 202:312 */     for (Object next : elements) {
/* 203:313 */       if ((next instanceof DomElement))
/* 204:    */       {
/* 205:314 */         String nodeName = ((DomElement)next).getAttribute("name");
/* 206:315 */         if (name.equals(nodeName))
/* 207:    */         {
/* 208:316 */           if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLCOLLECTION_IDENTICAL_IDS)) {
/* 209:317 */             return getScriptableForElement(next);
/* 210:    */           }
/* 211:319 */           matchingElements.add(next);
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:324 */     if (matchingElements.isEmpty()) {
/* 216:325 */       return NOT_FOUND;
/* 217:    */     }
/* 218:327 */     if (matchingElements.size() == 1) {
/* 219:328 */       return getScriptableForElement(matchingElements.get(0));
/* 220:    */     }
/* 221:332 */     DomNode domNode = getDomNodeOrNull();
/* 222:333 */     HTMLCollection collection = new HTMLCollection(domNode, matchingElements);
/* 223:334 */     collection.setAvoidObjectDetection(!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_46));
/* 224:335 */     return collection;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public final int jsxGet_length()
/* 228:    */   {
/* 229:344 */     return getElements().size();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public final Object jsxFunction_item(Object index)
/* 233:    */   {
/* 234:354 */     return nullIfNotFound(getIt(index));
/* 235:    */   }
/* 236:    */   
/* 237:    */   private Object nullIfNotFound(Object object)
/* 238:    */   {
/* 239:365 */     if (object == NOT_FOUND)
/* 240:    */     {
/* 241:366 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_48)) {
/* 242:367 */         return null;
/* 243:    */       }
/* 244:369 */       return Context.getUndefinedValue();
/* 245:    */     }
/* 246:371 */     return object;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public final Object jsxFunction_namedItem(String name)
/* 250:    */   {
/* 251:382 */     return nullIfNotFound(getIt(name));
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Object jsxFunction_nextNode()
/* 255:    */   {
/* 256:391 */     List<Object> elements = getElements();
/* 257:    */     Object nextNode;
/* 258:    */     Object nextNode;
/* 259:392 */     if ((this.currentIndex_ >= 0) && (this.currentIndex_ < elements.size())) {
/* 260:393 */       nextNode = elements.get(this.currentIndex_);
/* 261:    */     } else {
/* 262:396 */       nextNode = null;
/* 263:    */     }
/* 264:398 */     this.currentIndex_ += 1;
/* 265:399 */     return nextNode;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void jsxFunction_reset()
/* 269:    */   {
/* 270:406 */     this.currentIndex_ = 0;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public Object jsxFunction_tags(String tagName)
/* 274:    */   {
/* 275:418 */     final String tagNameLC = tagName.toLowerCase();
/* 276:419 */     HTMLCollection collection = new HTMLSubCollection(this, ".tags('" + tagName + "')")
/* 277:    */     {
/* 278:    */       protected boolean isMatching(DomNode node)
/* 279:    */       {
/* 280:422 */         return tagNameLC.equalsIgnoreCase(node.getLocalName());
/* 281:    */       }
/* 282:424 */     };
/* 283:425 */     return collection;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public String toString()
/* 287:    */   {
/* 288:433 */     return this.description_;
/* 289:    */   }
/* 290:    */   
/* 291:    */   protected Object equivalentValues(Object other)
/* 292:    */   {
/* 293:442 */     if (other == this) {
/* 294:443 */       return Boolean.TRUE;
/* 295:    */     }
/* 296:445 */     if ((other instanceof HTMLCollection))
/* 297:    */     {
/* 298:446 */       HTMLCollection otherArray = (HTMLCollection)other;
/* 299:447 */       DomNode domNode = getDomNodeOrNull();
/* 300:448 */       DomNode domNodeOther = otherArray.getDomNodeOrNull();
/* 301:449 */       if ((getClass() == other.getClass()) && (domNode == domNodeOther) && (getElements().equals(otherArray.getElements()))) {
/* 302:452 */         return Boolean.TRUE;
/* 303:    */       }
/* 304:454 */       return NOT_FOUND;
/* 305:    */     }
/* 306:457 */     return super.equivalentValues(other);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public boolean has(String name, Scriptable start)
/* 310:    */   {
/* 311:466 */     if (isPrototype()) {
/* 312:467 */       return super.has(name, start);
/* 313:    */     }
/* 314:    */     try
/* 315:    */     {
/* 316:471 */       int index = Integer.parseInt(name);
/* 317:472 */       List<Object> elements = getElements();
/* 318:473 */       if ((index >= 0) && (index < elements.size())) {
/* 319:474 */         return true;
/* 320:    */       }
/* 321:    */     }
/* 322:    */     catch (NumberFormatException e) {}
/* 323:481 */     if ("length".equals(name)) {
/* 324:482 */       return true;
/* 325:    */     }
/* 326:484 */     if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_49))
/* 327:    */     {
/* 328:485 */       JavaScriptConfiguration jsConfig = JavaScriptConfiguration.getInstance(BrowserVersion.FIREFOX_3_6);
/* 329:486 */       for (String functionName : jsConfig.getClassConfiguration(getClassName()).functionKeys()) {
/* 330:487 */         if (name.equals(functionName)) {
/* 331:488 */           return true;
/* 332:    */         }
/* 333:    */       }
/* 334:491 */       return false;
/* 335:    */     }
/* 336:493 */     return getWithPreemption(name) != NOT_FOUND;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public Object[] getIds()
/* 340:    */   {
/* 341:502 */     if (isPrototype()) {
/* 342:503 */       return super.getIds();
/* 343:    */     }
/* 344:506 */     List<String> idList = new ArrayList();
/* 345:    */     
/* 346:508 */     List<Object> elements = getElements();
/* 347:510 */     if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_50))
/* 348:    */     {
/* 349:511 */       int length = elements.size();
/* 350:512 */       for (int i = 0; i < length; i++) {
/* 351:513 */         idList.add(Integer.toString(i));
/* 352:    */       }
/* 353:516 */       idList.add("length");
/* 354:517 */       JavaScriptConfiguration jsConfig = JavaScriptConfiguration.getInstance(BrowserVersion.FIREFOX_3_6);
/* 355:518 */       for (String name : jsConfig.getClassConfiguration(getClassName()).functionKeys()) {
/* 356:519 */         idList.add(name);
/* 357:    */       }
/* 358:    */     }
/* 359:    */     else
/* 360:    */     {
/* 361:523 */       idList.add("length");
/* 362:    */       
/* 363:525 */       addElementIds(idList, elements);
/* 364:    */     }
/* 365:527 */     return idList.toArray();
/* 366:    */   }
/* 367:    */   
/* 368:    */   private boolean isPrototype()
/* 369:    */   {
/* 370:531 */     return !(getPrototype() instanceof HTMLCollection);
/* 371:    */   }
/* 372:    */   
/* 373:    */   protected void addElementIds(List<String> idList, List<Object> elements)
/* 374:    */   {
/* 375:540 */     int index = 0;
/* 376:541 */     for (Object next : elements)
/* 377:    */     {
/* 378:542 */       HtmlElement element = (HtmlElement)next;
/* 379:543 */       String name = element.getAttribute("name");
/* 380:544 */       if (name != DomElement.ATTRIBUTE_NOT_DEFINED)
/* 381:    */       {
/* 382:545 */         idList.add(name);
/* 383:    */       }
/* 384:    */       else
/* 385:    */       {
/* 386:548 */         String id = element.getId();
/* 387:549 */         if (id != DomElement.ATTRIBUTE_NOT_DEFINED) {
/* 388:550 */           idList.add(id);
/* 389:    */         } else {
/* 390:553 */           idList.add(Integer.toString(index));
/* 391:    */         }
/* 392:    */       }
/* 393:556 */       index++;
/* 394:    */     }
/* 395:    */   }
/* 396:    */   
/* 397:    */   private class DomHtmlAttributeChangeListenerImpl
/* 398:    */     implements DomChangeListener, HtmlAttributeChangeListener
/* 399:    */   {
/* 400:    */     private DomHtmlAttributeChangeListenerImpl() {}
/* 401:    */     
/* 402:    */     public void nodeAdded(DomChangeEvent event)
/* 403:    */     {
/* 404:566 */       HTMLCollection.this.cachedElements_ = null;
/* 405:    */     }
/* 406:    */     
/* 407:    */     public void nodeDeleted(DomChangeEvent event)
/* 408:    */     {
/* 409:573 */       HTMLCollection.this.cachedElements_ = null;
/* 410:    */     }
/* 411:    */     
/* 412:    */     public void attributeAdded(HtmlAttributeChangeEvent event)
/* 413:    */     {
/* 414:580 */       handleChangeOnCache(HTMLCollection.this.getEffectOnCache(event));
/* 415:    */     }
/* 416:    */     
/* 417:    */     public void attributeRemoved(HtmlAttributeChangeEvent event)
/* 418:    */     {
/* 419:587 */       handleChangeOnCache(HTMLCollection.this.getEffectOnCache(event));
/* 420:    */     }
/* 421:    */     
/* 422:    */     public void attributeReplaced(HtmlAttributeChangeEvent event)
/* 423:    */     {
/* 424:594 */       if (HTMLCollection.this.attributeChangeSensitive_) {
/* 425:595 */         handleChangeOnCache(HTMLCollection.this.getEffectOnCache(event));
/* 426:    */       }
/* 427:    */     }
/* 428:    */     
/* 429:    */     private void handleChangeOnCache(HTMLCollection.EffectOnCache effectOnCache)
/* 430:    */     {
/* 431:600 */       if (HTMLCollection.EffectOnCache.NONE == effectOnCache) {
/* 432:601 */         return;
/* 433:    */       }
/* 434:603 */       if (HTMLCollection.EffectOnCache.RESET == effectOnCache) {
/* 435:604 */         HTMLCollection.this.cachedElements_ = null;
/* 436:    */       }
/* 437:    */     }
/* 438:    */   }
/* 439:    */   
/* 440:    */   public int getLength()
/* 441:    */   {
/* 442:613 */     return jsxGet_length();
/* 443:    */   }
/* 444:    */   
/* 445:    */   protected EffectOnCache getEffectOnCache(HtmlAttributeChangeEvent event)
/* 446:    */   {
/* 447:623 */     return EffectOnCache.RESET;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public Node item(int index)
/* 451:    */   {
/* 452:630 */     return (DomNode)getElements().get(index);
/* 453:    */   }
/* 454:    */   
/* 455:    */   protected Scriptable getScriptableForElement(Object object)
/* 456:    */   {
/* 457:639 */     if ((object instanceof Scriptable)) {
/* 458:640 */       return (Scriptable)object;
/* 459:    */     }
/* 460:642 */     return getScriptableFor(object);
/* 461:    */   }
/* 462:    */   
/* 463:    */   public String getClassName()
/* 464:    */   {
/* 465:650 */     return "HTMLCollection";
/* 466:    */   }
/* 467:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection
 * JD-Core Version:    0.7.0.1
 */