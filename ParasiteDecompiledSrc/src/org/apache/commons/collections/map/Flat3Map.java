/*    1:     */ package org.apache.commons.collections.map;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.ObjectOutputStream;
/*    6:     */ import java.io.Serializable;
/*    7:     */ import java.util.AbstractCollection;
/*    8:     */ import java.util.AbstractSet;
/*    9:     */ import java.util.Collection;
/*   10:     */ import java.util.Iterator;
/*   11:     */ import java.util.Map;
/*   12:     */ import java.util.Map.Entry;
/*   13:     */ import java.util.NoSuchElementException;
/*   14:     */ import java.util.Set;
/*   15:     */ import org.apache.commons.collections.IterableMap;
/*   16:     */ import org.apache.commons.collections.MapIterator;
/*   17:     */ import org.apache.commons.collections.ResettableIterator;
/*   18:     */ import org.apache.commons.collections.iterators.EmptyIterator;
/*   19:     */ import org.apache.commons.collections.iterators.EmptyMapIterator;
/*   20:     */ 
/*   21:     */ public class Flat3Map
/*   22:     */   implements IterableMap, Serializable, Cloneable
/*   23:     */ {
/*   24:     */   private static final long serialVersionUID = -6701087419741928296L;
/*   25:     */   private transient int size;
/*   26:     */   private transient int hash1;
/*   27:     */   private transient int hash2;
/*   28:     */   private transient int hash3;
/*   29:     */   private transient Object key1;
/*   30:     */   private transient Object key2;
/*   31:     */   private transient Object key3;
/*   32:     */   private transient Object value1;
/*   33:     */   private transient Object value2;
/*   34:     */   private transient Object value3;
/*   35:     */   private transient AbstractHashedMap delegateMap;
/*   36:     */   
/*   37:     */   public Flat3Map() {}
/*   38:     */   
/*   39:     */   public Flat3Map(Map map)
/*   40:     */   {
/*   41: 118 */     putAll(map);
/*   42:     */   }
/*   43:     */   
/*   44:     */   public Object get(Object key)
/*   45:     */   {
/*   46: 129 */     if (this.delegateMap != null) {
/*   47: 130 */       return this.delegateMap.get(key);
/*   48:     */     }
/*   49: 132 */     if (key == null)
/*   50:     */     {
/*   51: 133 */       switch (this.size)
/*   52:     */       {
/*   53:     */       case 3: 
/*   54: 136 */         if (this.key3 == null) {
/*   55: 136 */           return this.value3;
/*   56:     */         }
/*   57:     */       case 2: 
/*   58: 138 */         if (this.key2 == null) {
/*   59: 138 */           return this.value2;
/*   60:     */         }
/*   61:     */       case 1: 
/*   62: 140 */         if (this.key1 == null) {
/*   63: 140 */           return this.value1;
/*   64:     */         }
/*   65:     */         break;
/*   66:     */       }
/*   67:     */     }
/*   68: 143 */     else if (this.size > 0)
/*   69:     */     {
/*   70: 144 */       int hashCode = key.hashCode();
/*   71: 145 */       switch (this.size)
/*   72:     */       {
/*   73:     */       case 3: 
/*   74: 148 */         if ((this.hash3 == hashCode) && (key.equals(this.key3))) {
/*   75: 148 */           return this.value3;
/*   76:     */         }
/*   77:     */       case 2: 
/*   78: 150 */         if ((this.hash2 == hashCode) && (key.equals(this.key2))) {
/*   79: 150 */           return this.value2;
/*   80:     */         }
/*   81:     */       case 1: 
/*   82: 152 */         if ((this.hash1 == hashCode) && (key.equals(this.key1))) {
/*   83: 152 */           return this.value1;
/*   84:     */         }
/*   85:     */         break;
/*   86:     */       }
/*   87:     */     }
/*   88: 156 */     return null;
/*   89:     */   }
/*   90:     */   
/*   91:     */   public int size()
/*   92:     */   {
/*   93: 165 */     if (this.delegateMap != null) {
/*   94: 166 */       return this.delegateMap.size();
/*   95:     */     }
/*   96: 168 */     return this.size;
/*   97:     */   }
/*   98:     */   
/*   99:     */   public boolean isEmpty()
/*  100:     */   {
/*  101: 177 */     return size() == 0;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public boolean containsKey(Object key)
/*  105:     */   {
/*  106: 188 */     if (this.delegateMap != null) {
/*  107: 189 */       return this.delegateMap.containsKey(key);
/*  108:     */     }
/*  109: 191 */     if (key == null)
/*  110:     */     {
/*  111: 192 */       switch (this.size)
/*  112:     */       {
/*  113:     */       case 3: 
/*  114: 194 */         if (this.key3 == null) {
/*  115: 194 */           return true;
/*  116:     */         }
/*  117:     */       case 2: 
/*  118: 196 */         if (this.key2 == null) {
/*  119: 196 */           return true;
/*  120:     */         }
/*  121:     */       case 1: 
/*  122: 198 */         if (this.key1 == null) {
/*  123: 198 */           return true;
/*  124:     */         }
/*  125:     */         break;
/*  126:     */       }
/*  127:     */     }
/*  128: 201 */     else if (this.size > 0)
/*  129:     */     {
/*  130: 202 */       int hashCode = key.hashCode();
/*  131: 203 */       switch (this.size)
/*  132:     */       {
/*  133:     */       case 3: 
/*  134: 205 */         if ((this.hash3 == hashCode) && (key.equals(this.key3))) {
/*  135: 205 */           return true;
/*  136:     */         }
/*  137:     */       case 2: 
/*  138: 207 */         if ((this.hash2 == hashCode) && (key.equals(this.key2))) {
/*  139: 207 */           return true;
/*  140:     */         }
/*  141:     */       case 1: 
/*  142: 209 */         if ((this.hash1 == hashCode) && (key.equals(this.key1))) {
/*  143: 209 */           return true;
/*  144:     */         }
/*  145:     */         break;
/*  146:     */       }
/*  147:     */     }
/*  148: 213 */     return false;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public boolean containsValue(Object value)
/*  152:     */   {
/*  153: 223 */     if (this.delegateMap != null) {
/*  154: 224 */       return this.delegateMap.containsValue(value);
/*  155:     */     }
/*  156: 226 */     if (value == null) {
/*  157: 227 */       switch (this.size)
/*  158:     */       {
/*  159:     */       case 3: 
/*  160: 229 */         if (this.value3 == null) {
/*  161: 229 */           return true;
/*  162:     */         }
/*  163:     */       case 2: 
/*  164: 231 */         if (this.value2 == null) {
/*  165: 231 */           return true;
/*  166:     */         }
/*  167:     */       case 1: 
/*  168: 233 */         if (this.value1 == null) {
/*  169: 233 */           return true;
/*  170:     */         }
/*  171:     */         break;
/*  172:     */       }
/*  173:     */     } else {
/*  174: 236 */       switch (this.size)
/*  175:     */       {
/*  176:     */       case 3: 
/*  177: 238 */         if (value.equals(this.value3)) {
/*  178: 238 */           return true;
/*  179:     */         }
/*  180:     */       case 2: 
/*  181: 240 */         if (value.equals(this.value2)) {
/*  182: 240 */           return true;
/*  183:     */         }
/*  184:     */       case 1: 
/*  185: 242 */         if (value.equals(this.value1)) {
/*  186: 242 */           return true;
/*  187:     */         }
/*  188:     */         break;
/*  189:     */       }
/*  190:     */     }
/*  191: 245 */     return false;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Object put(Object key, Object value)
/*  195:     */   {
/*  196: 257 */     if (this.delegateMap != null) {
/*  197: 258 */       return this.delegateMap.put(key, value);
/*  198:     */     }
/*  199: 261 */     if (key == null)
/*  200:     */     {
/*  201: 262 */       switch (this.size)
/*  202:     */       {
/*  203:     */       case 3: 
/*  204: 264 */         if (this.key3 == null)
/*  205:     */         {
/*  206: 265 */           Object old = this.value3;
/*  207: 266 */           this.value3 = value;
/*  208: 267 */           return old;
/*  209:     */         }
/*  210:     */       case 2: 
/*  211: 270 */         if (this.key2 == null)
/*  212:     */         {
/*  213: 271 */           Object old = this.value2;
/*  214: 272 */           this.value2 = value;
/*  215: 273 */           return old;
/*  216:     */         }
/*  217:     */       case 1: 
/*  218: 276 */         if (this.key1 == null)
/*  219:     */         {
/*  220: 277 */           Object old = this.value1;
/*  221: 278 */           this.value1 = value;
/*  222: 279 */           return old;
/*  223:     */         }
/*  224:     */         break;
/*  225:     */       }
/*  226:     */     }
/*  227: 283 */     else if (this.size > 0)
/*  228:     */     {
/*  229: 284 */       int hashCode = key.hashCode();
/*  230: 285 */       switch (this.size)
/*  231:     */       {
/*  232:     */       case 3: 
/*  233: 287 */         if ((this.hash3 == hashCode) && (key.equals(this.key3)))
/*  234:     */         {
/*  235: 288 */           Object old = this.value3;
/*  236: 289 */           this.value3 = value;
/*  237: 290 */           return old;
/*  238:     */         }
/*  239:     */       case 2: 
/*  240: 293 */         if ((this.hash2 == hashCode) && (key.equals(this.key2)))
/*  241:     */         {
/*  242: 294 */           Object old = this.value2;
/*  243: 295 */           this.value2 = value;
/*  244: 296 */           return old;
/*  245:     */         }
/*  246:     */       case 1: 
/*  247: 299 */         if ((this.hash1 == hashCode) && (key.equals(this.key1)))
/*  248:     */         {
/*  249: 300 */           Object old = this.value1;
/*  250: 301 */           this.value1 = value;
/*  251: 302 */           return old;
/*  252:     */         }
/*  253:     */         break;
/*  254:     */       }
/*  255:     */     }
/*  256: 309 */     switch (this.size)
/*  257:     */     {
/*  258:     */     default: 
/*  259: 311 */       convertToMap();
/*  260: 312 */       this.delegateMap.put(key, value);
/*  261: 313 */       return null;
/*  262:     */     case 2: 
/*  263: 315 */       this.hash3 = (key == null ? 0 : key.hashCode());
/*  264: 316 */       this.key3 = key;
/*  265: 317 */       this.value3 = value;
/*  266: 318 */       break;
/*  267:     */     case 1: 
/*  268: 320 */       this.hash2 = (key == null ? 0 : key.hashCode());
/*  269: 321 */       this.key2 = key;
/*  270: 322 */       this.value2 = value;
/*  271: 323 */       break;
/*  272:     */     case 0: 
/*  273: 325 */       this.hash1 = (key == null ? 0 : key.hashCode());
/*  274: 326 */       this.key1 = key;
/*  275: 327 */       this.value1 = value;
/*  276:     */     }
/*  277: 330 */     this.size += 1;
/*  278: 331 */     return null;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void putAll(Map map)
/*  282:     */   {
/*  283: 341 */     int size = map.size();
/*  284: 342 */     if (size == 0) {
/*  285: 343 */       return;
/*  286:     */     }
/*  287: 345 */     if (this.delegateMap != null)
/*  288:     */     {
/*  289: 346 */       this.delegateMap.putAll(map); return;
/*  290:     */     }
/*  291:     */     Iterator it;
/*  292: 349 */     if (size < 4)
/*  293:     */     {
/*  294: 350 */       for (it = map.entrySet().iterator(); it.hasNext();)
/*  295:     */       {
/*  296: 351 */         Map.Entry entry = (Map.Entry)it.next();
/*  297: 352 */         put(entry.getKey(), entry.getValue());
/*  298:     */       }
/*  299:     */     }
/*  300:     */     else
/*  301:     */     {
/*  302: 355 */       convertToMap();
/*  303: 356 */       this.delegateMap.putAll(map);
/*  304:     */     }
/*  305:     */   }
/*  306:     */   
/*  307:     */   private void convertToMap()
/*  308:     */   {
/*  309: 364 */     this.delegateMap = createDelegateMap();
/*  310: 365 */     switch (this.size)
/*  311:     */     {
/*  312:     */     case 3: 
/*  313: 367 */       this.delegateMap.put(this.key3, this.value3);
/*  314:     */     case 2: 
/*  315: 369 */       this.delegateMap.put(this.key2, this.value2);
/*  316:     */     case 1: 
/*  317: 371 */       this.delegateMap.put(this.key1, this.value1);
/*  318:     */     }
/*  319: 374 */     this.size = 0;
/*  320: 375 */     this.hash1 = (this.hash2 = this.hash3 = 0);
/*  321: 376 */     this.key1 = (this.key2 = this.key3 = null);
/*  322: 377 */     this.value1 = (this.value2 = this.value3 = null);
/*  323:     */   }
/*  324:     */   
/*  325:     */   protected AbstractHashedMap createDelegateMap()
/*  326:     */   {
/*  327: 391 */     return new HashedMap();
/*  328:     */   }
/*  329:     */   
/*  330:     */   public Object remove(Object key)
/*  331:     */   {
/*  332: 401 */     if (this.delegateMap != null) {
/*  333: 402 */       return this.delegateMap.remove(key);
/*  334:     */     }
/*  335: 404 */     if (this.size == 0) {
/*  336: 405 */       return null;
/*  337:     */     }
/*  338: 407 */     if (key == null)
/*  339:     */     {
/*  340: 408 */       switch (this.size)
/*  341:     */       {
/*  342:     */       case 3: 
/*  343: 410 */         if (this.key3 == null)
/*  344:     */         {
/*  345: 411 */           Object old = this.value3;
/*  346: 412 */           this.hash3 = 0;
/*  347: 413 */           this.key3 = null;
/*  348: 414 */           this.value3 = null;
/*  349: 415 */           this.size = 2;
/*  350: 416 */           return old;
/*  351:     */         }
/*  352: 418 */         if (this.key2 == null)
/*  353:     */         {
/*  354: 419 */           Object old = this.value3;
/*  355: 420 */           this.hash2 = this.hash3;
/*  356: 421 */           this.key2 = this.key3;
/*  357: 422 */           this.value2 = this.value3;
/*  358: 423 */           this.hash3 = 0;
/*  359: 424 */           this.key3 = null;
/*  360: 425 */           this.value3 = null;
/*  361: 426 */           this.size = 2;
/*  362: 427 */           return old;
/*  363:     */         }
/*  364: 429 */         if (this.key1 == null)
/*  365:     */         {
/*  366: 430 */           Object old = this.value3;
/*  367: 431 */           this.hash1 = this.hash3;
/*  368: 432 */           this.key1 = this.key3;
/*  369: 433 */           this.value1 = this.value3;
/*  370: 434 */           this.hash3 = 0;
/*  371: 435 */           this.key3 = null;
/*  372: 436 */           this.value3 = null;
/*  373: 437 */           this.size = 2;
/*  374: 438 */           return old;
/*  375:     */         }
/*  376: 440 */         return null;
/*  377:     */       case 2: 
/*  378: 442 */         if (this.key2 == null)
/*  379:     */         {
/*  380: 443 */           Object old = this.value2;
/*  381: 444 */           this.hash2 = 0;
/*  382: 445 */           this.key2 = null;
/*  383: 446 */           this.value2 = null;
/*  384: 447 */           this.size = 1;
/*  385: 448 */           return old;
/*  386:     */         }
/*  387: 450 */         if (this.key1 == null)
/*  388:     */         {
/*  389: 451 */           Object old = this.value2;
/*  390: 452 */           this.hash1 = this.hash2;
/*  391: 453 */           this.key1 = this.key2;
/*  392: 454 */           this.value1 = this.value2;
/*  393: 455 */           this.hash2 = 0;
/*  394: 456 */           this.key2 = null;
/*  395: 457 */           this.value2 = null;
/*  396: 458 */           this.size = 1;
/*  397: 459 */           return old;
/*  398:     */         }
/*  399: 461 */         return null;
/*  400:     */       case 1: 
/*  401: 463 */         if (this.key1 == null)
/*  402:     */         {
/*  403: 464 */           Object old = this.value1;
/*  404: 465 */           this.hash1 = 0;
/*  405: 466 */           this.key1 = null;
/*  406: 467 */           this.value1 = null;
/*  407: 468 */           this.size = 0;
/*  408: 469 */           return old;
/*  409:     */         }
/*  410:     */         break;
/*  411:     */       }
/*  412:     */     }
/*  413: 473 */     else if (this.size > 0)
/*  414:     */     {
/*  415: 474 */       int hashCode = key.hashCode();
/*  416: 475 */       switch (this.size)
/*  417:     */       {
/*  418:     */       case 3: 
/*  419: 477 */         if ((this.hash3 == hashCode) && (key.equals(this.key3)))
/*  420:     */         {
/*  421: 478 */           Object old = this.value3;
/*  422: 479 */           this.hash3 = 0;
/*  423: 480 */           this.key3 = null;
/*  424: 481 */           this.value3 = null;
/*  425: 482 */           this.size = 2;
/*  426: 483 */           return old;
/*  427:     */         }
/*  428: 485 */         if ((this.hash2 == hashCode) && (key.equals(this.key2)))
/*  429:     */         {
/*  430: 486 */           Object old = this.value3;
/*  431: 487 */           this.hash2 = this.hash3;
/*  432: 488 */           this.key2 = this.key3;
/*  433: 489 */           this.value2 = this.value3;
/*  434: 490 */           this.hash3 = 0;
/*  435: 491 */           this.key3 = null;
/*  436: 492 */           this.value3 = null;
/*  437: 493 */           this.size = 2;
/*  438: 494 */           return old;
/*  439:     */         }
/*  440: 496 */         if ((this.hash1 == hashCode) && (key.equals(this.key1)))
/*  441:     */         {
/*  442: 497 */           Object old = this.value3;
/*  443: 498 */           this.hash1 = this.hash3;
/*  444: 499 */           this.key1 = this.key3;
/*  445: 500 */           this.value1 = this.value3;
/*  446: 501 */           this.hash3 = 0;
/*  447: 502 */           this.key3 = null;
/*  448: 503 */           this.value3 = null;
/*  449: 504 */           this.size = 2;
/*  450: 505 */           return old;
/*  451:     */         }
/*  452: 507 */         return null;
/*  453:     */       case 2: 
/*  454: 509 */         if ((this.hash2 == hashCode) && (key.equals(this.key2)))
/*  455:     */         {
/*  456: 510 */           Object old = this.value2;
/*  457: 511 */           this.hash2 = 0;
/*  458: 512 */           this.key2 = null;
/*  459: 513 */           this.value2 = null;
/*  460: 514 */           this.size = 1;
/*  461: 515 */           return old;
/*  462:     */         }
/*  463: 517 */         if ((this.hash1 == hashCode) && (key.equals(this.key1)))
/*  464:     */         {
/*  465: 518 */           Object old = this.value2;
/*  466: 519 */           this.hash1 = this.hash2;
/*  467: 520 */           this.key1 = this.key2;
/*  468: 521 */           this.value1 = this.value2;
/*  469: 522 */           this.hash2 = 0;
/*  470: 523 */           this.key2 = null;
/*  471: 524 */           this.value2 = null;
/*  472: 525 */           this.size = 1;
/*  473: 526 */           return old;
/*  474:     */         }
/*  475: 528 */         return null;
/*  476:     */       case 1: 
/*  477: 530 */         if ((this.hash1 == hashCode) && (key.equals(this.key1)))
/*  478:     */         {
/*  479: 531 */           Object old = this.value1;
/*  480: 532 */           this.hash1 = 0;
/*  481: 533 */           this.key1 = null;
/*  482: 534 */           this.value1 = null;
/*  483: 535 */           this.size = 0;
/*  484: 536 */           return old;
/*  485:     */         }
/*  486:     */         break;
/*  487:     */       }
/*  488:     */     }
/*  489: 541 */     return null;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void clear()
/*  493:     */   {
/*  494: 549 */     if (this.delegateMap != null)
/*  495:     */     {
/*  496: 550 */       this.delegateMap.clear();
/*  497: 551 */       this.delegateMap = null;
/*  498:     */     }
/*  499:     */     else
/*  500:     */     {
/*  501: 553 */       this.size = 0;
/*  502: 554 */       this.hash1 = (this.hash2 = this.hash3 = 0);
/*  503: 555 */       this.key1 = (this.key2 = this.key3 = null);
/*  504: 556 */       this.value1 = (this.value2 = this.value3 = null);
/*  505:     */     }
/*  506:     */   }
/*  507:     */   
/*  508:     */   public MapIterator mapIterator()
/*  509:     */   {
/*  510: 573 */     if (this.delegateMap != null) {
/*  511: 574 */       return this.delegateMap.mapIterator();
/*  512:     */     }
/*  513: 576 */     if (this.size == 0) {
/*  514: 577 */       return EmptyMapIterator.INSTANCE;
/*  515:     */     }
/*  516: 579 */     return new FlatMapIterator(this);
/*  517:     */   }
/*  518:     */   
/*  519:     */   static class FlatMapIterator
/*  520:     */     implements MapIterator, ResettableIterator
/*  521:     */   {
/*  522:     */     private final Flat3Map parent;
/*  523: 587 */     private int nextIndex = 0;
/*  524: 588 */     private boolean canRemove = false;
/*  525:     */     
/*  526:     */     FlatMapIterator(Flat3Map parent)
/*  527:     */     {
/*  528: 592 */       this.parent = parent;
/*  529:     */     }
/*  530:     */     
/*  531:     */     public boolean hasNext()
/*  532:     */     {
/*  533: 596 */       return this.nextIndex < this.parent.size;
/*  534:     */     }
/*  535:     */     
/*  536:     */     public Object next()
/*  537:     */     {
/*  538: 600 */       if (!hasNext()) {
/*  539: 601 */         throw new NoSuchElementException("No next() entry in the iteration");
/*  540:     */       }
/*  541: 603 */       this.canRemove = true;
/*  542: 604 */       this.nextIndex += 1;
/*  543: 605 */       return getKey();
/*  544:     */     }
/*  545:     */     
/*  546:     */     public void remove()
/*  547:     */     {
/*  548: 609 */       if (!this.canRemove) {
/*  549: 610 */         throw new IllegalStateException("remove() can only be called once after next()");
/*  550:     */       }
/*  551: 612 */       this.parent.remove(getKey());
/*  552: 613 */       this.nextIndex -= 1;
/*  553: 614 */       this.canRemove = false;
/*  554:     */     }
/*  555:     */     
/*  556:     */     public Object getKey()
/*  557:     */     {
/*  558: 618 */       if (!this.canRemove) {
/*  559: 619 */         throw new IllegalStateException("getKey() can only be called after next() and before remove()");
/*  560:     */       }
/*  561: 621 */       switch (this.nextIndex)
/*  562:     */       {
/*  563:     */       case 3: 
/*  564: 623 */         return this.parent.key3;
/*  565:     */       case 2: 
/*  566: 625 */         return this.parent.key2;
/*  567:     */       case 1: 
/*  568: 627 */         return this.parent.key1;
/*  569:     */       }
/*  570: 629 */       throw new IllegalStateException("Invalid map index");
/*  571:     */     }
/*  572:     */     
/*  573:     */     public Object getValue()
/*  574:     */     {
/*  575: 633 */       if (!this.canRemove) {
/*  576: 634 */         throw new IllegalStateException("getValue() can only be called after next() and before remove()");
/*  577:     */       }
/*  578: 636 */       switch (this.nextIndex)
/*  579:     */       {
/*  580:     */       case 3: 
/*  581: 638 */         return this.parent.value3;
/*  582:     */       case 2: 
/*  583: 640 */         return this.parent.value2;
/*  584:     */       case 1: 
/*  585: 642 */         return this.parent.value1;
/*  586:     */       }
/*  587: 644 */       throw new IllegalStateException("Invalid map index");
/*  588:     */     }
/*  589:     */     
/*  590:     */     public Object setValue(Object value)
/*  591:     */     {
/*  592: 648 */       if (!this.canRemove) {
/*  593: 649 */         throw new IllegalStateException("setValue() can only be called after next() and before remove()");
/*  594:     */       }
/*  595: 651 */       Object old = getValue();
/*  596: 652 */       switch (this.nextIndex)
/*  597:     */       {
/*  598:     */       case 3: 
/*  599: 654 */         this.parent.value3 = value;
/*  600:     */       case 2: 
/*  601: 656 */         this.parent.value2 = value;
/*  602:     */       case 1: 
/*  603: 658 */         this.parent.value1 = value;
/*  604:     */       }
/*  605: 660 */       return old;
/*  606:     */     }
/*  607:     */     
/*  608:     */     public void reset()
/*  609:     */     {
/*  610: 664 */       this.nextIndex = 0;
/*  611: 665 */       this.canRemove = false;
/*  612:     */     }
/*  613:     */     
/*  614:     */     public String toString()
/*  615:     */     {
/*  616: 669 */       if (this.canRemove) {
/*  617: 670 */         return "Iterator[" + getKey() + "=" + getValue() + "]";
/*  618:     */       }
/*  619: 672 */       return "Iterator[]";
/*  620:     */     }
/*  621:     */   }
/*  622:     */   
/*  623:     */   public Set entrySet()
/*  624:     */   {
/*  625: 687 */     if (this.delegateMap != null) {
/*  626: 688 */       return this.delegateMap.entrySet();
/*  627:     */     }
/*  628: 690 */     return new EntrySet(this);
/*  629:     */   }
/*  630:     */   
/*  631:     */   static class EntrySet
/*  632:     */     extends AbstractSet
/*  633:     */   {
/*  634:     */     private final Flat3Map parent;
/*  635:     */     
/*  636:     */     EntrySet(Flat3Map parent)
/*  637:     */     {
/*  638: 701 */       this.parent = parent;
/*  639:     */     }
/*  640:     */     
/*  641:     */     public int size()
/*  642:     */     {
/*  643: 705 */       return this.parent.size();
/*  644:     */     }
/*  645:     */     
/*  646:     */     public void clear()
/*  647:     */     {
/*  648: 709 */       this.parent.clear();
/*  649:     */     }
/*  650:     */     
/*  651:     */     public boolean remove(Object obj)
/*  652:     */     {
/*  653: 713 */       if (!(obj instanceof Map.Entry)) {
/*  654: 714 */         return false;
/*  655:     */       }
/*  656: 716 */       Map.Entry entry = (Map.Entry)obj;
/*  657: 717 */       Object key = entry.getKey();
/*  658: 718 */       boolean result = this.parent.containsKey(key);
/*  659: 719 */       this.parent.remove(key);
/*  660: 720 */       return result;
/*  661:     */     }
/*  662:     */     
/*  663:     */     public Iterator iterator()
/*  664:     */     {
/*  665: 724 */       if (this.parent.delegateMap != null) {
/*  666: 725 */         return this.parent.delegateMap.entrySet().iterator();
/*  667:     */       }
/*  668: 727 */       if (this.parent.size() == 0) {
/*  669: 728 */         return EmptyIterator.INSTANCE;
/*  670:     */       }
/*  671: 730 */       return new Flat3Map.EntrySetIterator(this.parent);
/*  672:     */     }
/*  673:     */   }
/*  674:     */   
/*  675:     */   static class EntrySetIterator
/*  676:     */     implements Iterator, Map.Entry
/*  677:     */   {
/*  678:     */     private final Flat3Map parent;
/*  679: 739 */     private int nextIndex = 0;
/*  680: 740 */     private boolean canRemove = false;
/*  681:     */     
/*  682:     */     EntrySetIterator(Flat3Map parent)
/*  683:     */     {
/*  684: 744 */       this.parent = parent;
/*  685:     */     }
/*  686:     */     
/*  687:     */     public boolean hasNext()
/*  688:     */     {
/*  689: 748 */       return this.nextIndex < this.parent.size;
/*  690:     */     }
/*  691:     */     
/*  692:     */     public Object next()
/*  693:     */     {
/*  694: 752 */       if (!hasNext()) {
/*  695: 753 */         throw new NoSuchElementException("No next() entry in the iteration");
/*  696:     */       }
/*  697: 755 */       this.canRemove = true;
/*  698: 756 */       this.nextIndex += 1;
/*  699: 757 */       return this;
/*  700:     */     }
/*  701:     */     
/*  702:     */     public void remove()
/*  703:     */     {
/*  704: 761 */       if (!this.canRemove) {
/*  705: 762 */         throw new IllegalStateException("remove() can only be called once after next()");
/*  706:     */       }
/*  707: 764 */       this.parent.remove(getKey());
/*  708: 765 */       this.nextIndex -= 1;
/*  709: 766 */       this.canRemove = false;
/*  710:     */     }
/*  711:     */     
/*  712:     */     public Object getKey()
/*  713:     */     {
/*  714: 770 */       if (!this.canRemove) {
/*  715: 771 */         throw new IllegalStateException("getKey() can only be called after next() and before remove()");
/*  716:     */       }
/*  717: 773 */       switch (this.nextIndex)
/*  718:     */       {
/*  719:     */       case 3: 
/*  720: 775 */         return this.parent.key3;
/*  721:     */       case 2: 
/*  722: 777 */         return this.parent.key2;
/*  723:     */       case 1: 
/*  724: 779 */         return this.parent.key1;
/*  725:     */       }
/*  726: 781 */       throw new IllegalStateException("Invalid map index");
/*  727:     */     }
/*  728:     */     
/*  729:     */     public Object getValue()
/*  730:     */     {
/*  731: 785 */       if (!this.canRemove) {
/*  732: 786 */         throw new IllegalStateException("getValue() can only be called after next() and before remove()");
/*  733:     */       }
/*  734: 788 */       switch (this.nextIndex)
/*  735:     */       {
/*  736:     */       case 3: 
/*  737: 790 */         return this.parent.value3;
/*  738:     */       case 2: 
/*  739: 792 */         return this.parent.value2;
/*  740:     */       case 1: 
/*  741: 794 */         return this.parent.value1;
/*  742:     */       }
/*  743: 796 */       throw new IllegalStateException("Invalid map index");
/*  744:     */     }
/*  745:     */     
/*  746:     */     public Object setValue(Object value)
/*  747:     */     {
/*  748: 800 */       if (!this.canRemove) {
/*  749: 801 */         throw new IllegalStateException("setValue() can only be called after next() and before remove()");
/*  750:     */       }
/*  751: 803 */       Object old = getValue();
/*  752: 804 */       switch (this.nextIndex)
/*  753:     */       {
/*  754:     */       case 3: 
/*  755: 806 */         this.parent.value3 = value;
/*  756:     */       case 2: 
/*  757: 808 */         this.parent.value2 = value;
/*  758:     */       case 1: 
/*  759: 810 */         this.parent.value1 = value;
/*  760:     */       }
/*  761: 812 */       return old;
/*  762:     */     }
/*  763:     */     
/*  764:     */     public boolean equals(Object obj)
/*  765:     */     {
/*  766: 816 */       if (!this.canRemove) {
/*  767: 817 */         return false;
/*  768:     */       }
/*  769: 819 */       if (!(obj instanceof Map.Entry)) {
/*  770: 820 */         return false;
/*  771:     */       }
/*  772: 822 */       Map.Entry other = (Map.Entry)obj;
/*  773: 823 */       Object key = getKey();
/*  774: 824 */       Object value = getValue();
/*  775: 825 */       return (key == null ? other.getKey() == null : key.equals(other.getKey())) && (value == null ? other.getValue() == null : value.equals(other.getValue()));
/*  776:     */     }
/*  777:     */     
/*  778:     */     public int hashCode()
/*  779:     */     {
/*  780: 830 */       if (!this.canRemove) {
/*  781: 831 */         return 0;
/*  782:     */       }
/*  783: 833 */       Object key = getKey();
/*  784: 834 */       Object value = getValue();
/*  785: 835 */       return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
/*  786:     */     }
/*  787:     */     
/*  788:     */     public String toString()
/*  789:     */     {
/*  790: 840 */       if (this.canRemove) {
/*  791: 841 */         return getKey() + "=" + getValue();
/*  792:     */       }
/*  793: 843 */       return "";
/*  794:     */     }
/*  795:     */   }
/*  796:     */   
/*  797:     */   public Set keySet()
/*  798:     */   {
/*  799: 856 */     if (this.delegateMap != null) {
/*  800: 857 */       return this.delegateMap.keySet();
/*  801:     */     }
/*  802: 859 */     return new KeySet(this);
/*  803:     */   }
/*  804:     */   
/*  805:     */   static class KeySet
/*  806:     */     extends AbstractSet
/*  807:     */   {
/*  808:     */     private final Flat3Map parent;
/*  809:     */     
/*  810:     */     KeySet(Flat3Map parent)
/*  811:     */     {
/*  812: 870 */       this.parent = parent;
/*  813:     */     }
/*  814:     */     
/*  815:     */     public int size()
/*  816:     */     {
/*  817: 874 */       return this.parent.size();
/*  818:     */     }
/*  819:     */     
/*  820:     */     public void clear()
/*  821:     */     {
/*  822: 878 */       this.parent.clear();
/*  823:     */     }
/*  824:     */     
/*  825:     */     public boolean contains(Object key)
/*  826:     */     {
/*  827: 882 */       return this.parent.containsKey(key);
/*  828:     */     }
/*  829:     */     
/*  830:     */     public boolean remove(Object key)
/*  831:     */     {
/*  832: 886 */       boolean result = this.parent.containsKey(key);
/*  833: 887 */       this.parent.remove(key);
/*  834: 888 */       return result;
/*  835:     */     }
/*  836:     */     
/*  837:     */     public Iterator iterator()
/*  838:     */     {
/*  839: 892 */       if (this.parent.delegateMap != null) {
/*  840: 893 */         return this.parent.delegateMap.keySet().iterator();
/*  841:     */       }
/*  842: 895 */       if (this.parent.size() == 0) {
/*  843: 896 */         return EmptyIterator.INSTANCE;
/*  844:     */       }
/*  845: 898 */       return new Flat3Map.KeySetIterator(this.parent);
/*  846:     */     }
/*  847:     */   }
/*  848:     */   
/*  849:     */   static class KeySetIterator
/*  850:     */     extends Flat3Map.EntrySetIterator
/*  851:     */   {
/*  852:     */     KeySetIterator(Flat3Map parent)
/*  853:     */     {
/*  854: 908 */       super();
/*  855:     */     }
/*  856:     */     
/*  857:     */     public Object next()
/*  858:     */     {
/*  859: 912 */       super.next();
/*  860: 913 */       return getKey();
/*  861:     */     }
/*  862:     */   }
/*  863:     */   
/*  864:     */   public Collection values()
/*  865:     */   {
/*  866: 925 */     if (this.delegateMap != null) {
/*  867: 926 */       return this.delegateMap.values();
/*  868:     */     }
/*  869: 928 */     return new Values(this);
/*  870:     */   }
/*  871:     */   
/*  872:     */   static class Values
/*  873:     */     extends AbstractCollection
/*  874:     */   {
/*  875:     */     private final Flat3Map parent;
/*  876:     */     
/*  877:     */     Values(Flat3Map parent)
/*  878:     */     {
/*  879: 939 */       this.parent = parent;
/*  880:     */     }
/*  881:     */     
/*  882:     */     public int size()
/*  883:     */     {
/*  884: 943 */       return this.parent.size();
/*  885:     */     }
/*  886:     */     
/*  887:     */     public void clear()
/*  888:     */     {
/*  889: 947 */       this.parent.clear();
/*  890:     */     }
/*  891:     */     
/*  892:     */     public boolean contains(Object value)
/*  893:     */     {
/*  894: 951 */       return this.parent.containsValue(value);
/*  895:     */     }
/*  896:     */     
/*  897:     */     public Iterator iterator()
/*  898:     */     {
/*  899: 955 */       if (this.parent.delegateMap != null) {
/*  900: 956 */         return this.parent.delegateMap.values().iterator();
/*  901:     */       }
/*  902: 958 */       if (this.parent.size() == 0) {
/*  903: 959 */         return EmptyIterator.INSTANCE;
/*  904:     */       }
/*  905: 961 */       return new Flat3Map.ValuesIterator(this.parent);
/*  906:     */     }
/*  907:     */   }
/*  908:     */   
/*  909:     */   static class ValuesIterator
/*  910:     */     extends Flat3Map.EntrySetIterator
/*  911:     */   {
/*  912:     */     ValuesIterator(Flat3Map parent)
/*  913:     */     {
/*  914: 971 */       super();
/*  915:     */     }
/*  916:     */     
/*  917:     */     public Object next()
/*  918:     */     {
/*  919: 975 */       super.next();
/*  920: 976 */       return getValue();
/*  921:     */     }
/*  922:     */   }
/*  923:     */   
/*  924:     */   private void writeObject(ObjectOutputStream out)
/*  925:     */     throws IOException
/*  926:     */   {
/*  927: 985 */     out.defaultWriteObject();
/*  928: 986 */     out.writeInt(size());
/*  929: 987 */     for (MapIterator it = mapIterator(); it.hasNext();)
/*  930:     */     {
/*  931: 988 */       out.writeObject(it.next());
/*  932: 989 */       out.writeObject(it.getValue());
/*  933:     */     }
/*  934:     */   }
/*  935:     */   
/*  936:     */   private void readObject(ObjectInputStream in)
/*  937:     */     throws IOException, ClassNotFoundException
/*  938:     */   {
/*  939: 997 */     in.defaultReadObject();
/*  940: 998 */     int count = in.readInt();
/*  941: 999 */     if (count > 3) {
/*  942:1000 */       this.delegateMap = createDelegateMap();
/*  943:     */     }
/*  944:1002 */     for (int i = count; i > 0; i--) {
/*  945:1003 */       put(in.readObject(), in.readObject());
/*  946:     */     }
/*  947:     */   }
/*  948:     */   
/*  949:     */   public Object clone()
/*  950:     */   {
/*  951:     */     try
/*  952:     */     {
/*  953:1016 */       Flat3Map cloned = (Flat3Map)super.clone();
/*  954:1017 */       if (cloned.delegateMap != null) {
/*  955:1018 */         cloned.delegateMap = ((HashedMap)cloned.delegateMap.clone());
/*  956:     */       }
/*  957:1020 */       return cloned;
/*  958:     */     }
/*  959:     */     catch (CloneNotSupportedException ex)
/*  960:     */     {
/*  961:1022 */       throw new InternalError();
/*  962:     */     }
/*  963:     */   }
/*  964:     */   
/*  965:     */   public boolean equals(Object obj)
/*  966:     */   {
/*  967:1033 */     if (obj == this) {
/*  968:1034 */       return true;
/*  969:     */     }
/*  970:1036 */     if (this.delegateMap != null) {
/*  971:1037 */       return this.delegateMap.equals(obj);
/*  972:     */     }
/*  973:1039 */     if (!(obj instanceof Map)) {
/*  974:1040 */       return false;
/*  975:     */     }
/*  976:1042 */     Map other = (Map)obj;
/*  977:1043 */     if (this.size != other.size()) {
/*  978:1044 */       return false;
/*  979:     */     }
/*  980:1046 */     if (this.size > 0)
/*  981:     */     {
/*  982:1047 */       Object otherValue = null;
/*  983:1048 */       switch (this.size)
/*  984:     */       {
/*  985:     */       case 3: 
/*  986:1050 */         if (!other.containsKey(this.key3)) {
/*  987:1051 */           return false;
/*  988:     */         }
/*  989:1053 */         otherValue = other.get(this.key3);
/*  990:1054 */         if (this.value3 == null ? otherValue != null : !this.value3.equals(otherValue)) {
/*  991:1055 */           return false;
/*  992:     */         }
/*  993:     */       case 2: 
/*  994:1058 */         if (!other.containsKey(this.key2)) {
/*  995:1059 */           return false;
/*  996:     */         }
/*  997:1061 */         otherValue = other.get(this.key2);
/*  998:1062 */         if (this.value2 == null ? otherValue != null : !this.value2.equals(otherValue)) {
/*  999:1063 */           return false;
/* 1000:     */         }
/* 1001:     */       case 1: 
/* 1002:1066 */         if (!other.containsKey(this.key1)) {
/* 1003:1067 */           return false;
/* 1004:     */         }
/* 1005:1069 */         otherValue = other.get(this.key1);
/* 1006:1070 */         if (this.value1 == null ? otherValue != null : !this.value1.equals(otherValue)) {
/* 1007:1071 */           return false;
/* 1008:     */         }
/* 1009:     */         break;
/* 1010:     */       }
/* 1011:     */     }
/* 1012:1075 */     return true;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public int hashCode()
/* 1016:     */   {
/* 1017:1084 */     if (this.delegateMap != null) {
/* 1018:1085 */       return this.delegateMap.hashCode();
/* 1019:     */     }
/* 1020:1087 */     int total = 0;
/* 1021:1088 */     switch (this.size)
/* 1022:     */     {
/* 1023:     */     case 3: 
/* 1024:1090 */       total += (this.hash3 ^ (this.value3 == null ? 0 : this.value3.hashCode()));
/* 1025:     */     case 2: 
/* 1026:1092 */       total += (this.hash2 ^ (this.value2 == null ? 0 : this.value2.hashCode()));
/* 1027:     */     case 1: 
/* 1028:1094 */       total += (this.hash1 ^ (this.value1 == null ? 0 : this.value1.hashCode()));
/* 1029:     */     }
/* 1030:1096 */     return total;
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public String toString()
/* 1034:     */   {
/* 1035:1105 */     if (this.delegateMap != null) {
/* 1036:1106 */       return this.delegateMap.toString();
/* 1037:     */     }
/* 1038:1108 */     if (this.size == 0) {
/* 1039:1109 */       return "{}";
/* 1040:     */     }
/* 1041:1111 */     StringBuffer buf = new StringBuffer(128);
/* 1042:1112 */     buf.append('{');
/* 1043:1113 */     switch (this.size)
/* 1044:     */     {
/* 1045:     */     case 3: 
/* 1046:1115 */       buf.append(this.key3 == this ? "(this Map)" : this.key3);
/* 1047:1116 */       buf.append('=');
/* 1048:1117 */       buf.append(this.value3 == this ? "(this Map)" : this.value3);
/* 1049:1118 */       buf.append(',');
/* 1050:     */     case 2: 
/* 1051:1120 */       buf.append(this.key2 == this ? "(this Map)" : this.key2);
/* 1052:1121 */       buf.append('=');
/* 1053:1122 */       buf.append(this.value2 == this ? "(this Map)" : this.value2);
/* 1054:1123 */       buf.append(',');
/* 1055:     */     case 1: 
/* 1056:1125 */       buf.append(this.key1 == this ? "(this Map)" : this.key1);
/* 1057:1126 */       buf.append('=');
/* 1058:1127 */       buf.append(this.value1 == this ? "(this Map)" : this.value1);
/* 1059:     */     }
/* 1060:1129 */     buf.append('}');
/* 1061:1130 */     return buf.toString();
/* 1062:     */   }
/* 1063:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.Flat3Map
 * JD-Core Version:    0.7.0.1
 */