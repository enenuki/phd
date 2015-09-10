/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.Array;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.Arrays;
/*    6:     */ import java.util.Collection;
/*    7:     */ import java.util.Comparator;
/*    8:     */ import java.util.Iterator;
/*    9:     */ import java.util.LinkedHashSet;
/*   10:     */ import java.util.List;
/*   11:     */ import java.util.ListIterator;
/*   12:     */ import java.util.NoSuchElementException;
/*   13:     */ import java.util.Set;
/*   14:     */ 
/*   15:     */ public class NativeArray
/*   16:     */   extends IdScriptableObject
/*   17:     */   implements List
/*   18:     */ {
/*   19:     */   static final long serialVersionUID = 7331366857676127338L;
/*   20:  74 */   private static final Object ARRAY_TAG = "Array";
/*   21:  75 */   private static final Integer NEGATIVE_ONE = Integer.valueOf(-1);
/*   22:     */   private static final int Id_length = 1;
/*   23:     */   private static final int MAX_INSTANCE_ID = 1;
/*   24:     */   private static final int Id_constructor = 1;
/*   25:     */   private static final int Id_toString = 2;
/*   26:     */   private static final int Id_toLocaleString = 3;
/*   27:     */   private static final int Id_toSource = 4;
/*   28:     */   private static final int Id_join = 5;
/*   29:     */   private static final int Id_reverse = 6;
/*   30:     */   private static final int Id_sort = 7;
/*   31:     */   private static final int Id_push = 8;
/*   32:     */   private static final int Id_pop = 9;
/*   33:     */   private static final int Id_shift = 10;
/*   34:     */   private static final int Id_unshift = 11;
/*   35:     */   private static final int Id_splice = 12;
/*   36:     */   private static final int Id_concat = 13;
/*   37:     */   private static final int Id_slice = 14;
/*   38:     */   private static final int Id_indexOf = 15;
/*   39:     */   private static final int Id_lastIndexOf = 16;
/*   40:     */   private static final int Id_every = 17;
/*   41:     */   private static final int Id_filter = 18;
/*   42:     */   private static final int Id_forEach = 19;
/*   43:     */   private static final int Id_map = 20;
/*   44:     */   private static final int Id_some = 21;
/*   45:     */   private static final int Id_reduce = 22;
/*   46:     */   private static final int Id_reduceRight = 23;
/*   47:     */   private static final int MAX_PROTOTYPE_ID = 23;
/*   48:     */   private static final int ConstructorId_join = -5;
/*   49:     */   private static final int ConstructorId_reverse = -6;
/*   50:     */   private static final int ConstructorId_sort = -7;
/*   51:     */   private static final int ConstructorId_push = -8;
/*   52:     */   private static final int ConstructorId_pop = -9;
/*   53:     */   private static final int ConstructorId_shift = -10;
/*   54:     */   private static final int ConstructorId_unshift = -11;
/*   55:     */   private static final int ConstructorId_splice = -12;
/*   56:     */   private static final int ConstructorId_concat = -13;
/*   57:     */   private static final int ConstructorId_slice = -14;
/*   58:     */   private static final int ConstructorId_indexOf = -15;
/*   59:     */   private static final int ConstructorId_lastIndexOf = -16;
/*   60:     */   private static final int ConstructorId_every = -17;
/*   61:     */   private static final int ConstructorId_filter = -18;
/*   62:     */   private static final int ConstructorId_forEach = -19;
/*   63:     */   private static final int ConstructorId_map = -20;
/*   64:     */   private static final int ConstructorId_some = -21;
/*   65:     */   private static final int ConstructorId_reduce = -22;
/*   66:     */   private static final int ConstructorId_reduceRight = -23;
/*   67:     */   private static final int ConstructorId_isArray = -24;
/*   68:     */   private long length;
/*   69:     */   private Object[] dense;
/*   70:     */   private boolean denseOnly;
/*   71:     */   
/*   72:     */   static void init(Scriptable scope, boolean sealed)
/*   73:     */   {
/*   74:  79 */     NativeArray obj = new NativeArray(0L);
/*   75:  80 */     obj.exportAsJSClass(23, scope, sealed);
/*   76:     */   }
/*   77:     */   
/*   78:     */   static int getMaximumInitialCapacity()
/*   79:     */   {
/*   80:  84 */     return maximumInitialCapacity;
/*   81:     */   }
/*   82:     */   
/*   83:     */   static void setMaximumInitialCapacity(int maximumInitialCapacity)
/*   84:     */   {
/*   85:  88 */     maximumInitialCapacity = maximumInitialCapacity;
/*   86:     */   }
/*   87:     */   
/*   88:     */   public NativeArray(long lengthArg)
/*   89:     */   {
/*   90:  93 */     this.denseOnly = (lengthArg <= maximumInitialCapacity);
/*   91:  94 */     if (this.denseOnly)
/*   92:     */     {
/*   93:  95 */       int intLength = (int)lengthArg;
/*   94:  96 */       if (intLength < 10) {
/*   95:  97 */         intLength = 10;
/*   96:     */       }
/*   97:  98 */       this.dense = new Object[intLength];
/*   98:  99 */       Arrays.fill(this.dense, Scriptable.NOT_FOUND);
/*   99:     */     }
/*  100: 101 */     this.length = lengthArg;
/*  101:     */   }
/*  102:     */   
/*  103:     */   public NativeArray(Object[] array)
/*  104:     */   {
/*  105: 106 */     this.denseOnly = true;
/*  106: 107 */     this.dense = array;
/*  107: 108 */     this.length = array.length;
/*  108:     */   }
/*  109:     */   
/*  110:     */   public String getClassName()
/*  111:     */   {
/*  112: 114 */     return "Array";
/*  113:     */   }
/*  114:     */   
/*  115:     */   protected int getMaxInstanceId()
/*  116:     */   {
/*  117: 124 */     return 1;
/*  118:     */   }
/*  119:     */   
/*  120:     */   protected int findInstanceIdInfo(String s)
/*  121:     */   {
/*  122: 130 */     if (s.equals("length")) {
/*  123: 131 */       return instanceIdInfo(6, 1);
/*  124:     */     }
/*  125: 133 */     return super.findInstanceIdInfo(s);
/*  126:     */   }
/*  127:     */   
/*  128:     */   protected String getInstanceIdName(int id)
/*  129:     */   {
/*  130: 139 */     if (id == 1) {
/*  131: 139 */       return "length";
/*  132:     */     }
/*  133: 140 */     return super.getInstanceIdName(id);
/*  134:     */   }
/*  135:     */   
/*  136:     */   protected Object getInstanceIdValue(int id)
/*  137:     */   {
/*  138: 146 */     if (id == 1) {
/*  139: 147 */       return ScriptRuntime.wrapNumber(this.length);
/*  140:     */     }
/*  141: 149 */     return super.getInstanceIdValue(id);
/*  142:     */   }
/*  143:     */   
/*  144:     */   protected void setInstanceIdValue(int id, Object value)
/*  145:     */   {
/*  146: 155 */     if (id == 1)
/*  147:     */     {
/*  148: 156 */       setLength(value);return;
/*  149:     */     }
/*  150: 158 */     super.setInstanceIdValue(id, value);
/*  151:     */   }
/*  152:     */   
/*  153:     */   protected void fillConstructorProperties(IdFunctionObject ctor)
/*  154:     */   {
/*  155: 164 */     addIdFunctionProperty(ctor, ARRAY_TAG, -5, "join", 1);
/*  156:     */     
/*  157: 166 */     addIdFunctionProperty(ctor, ARRAY_TAG, -6, "reverse", 0);
/*  158:     */     
/*  159: 168 */     addIdFunctionProperty(ctor, ARRAY_TAG, -7, "sort", 1);
/*  160:     */     
/*  161: 170 */     addIdFunctionProperty(ctor, ARRAY_TAG, -8, "push", 1);
/*  162:     */     
/*  163: 172 */     addIdFunctionProperty(ctor, ARRAY_TAG, -9, "pop", 0);
/*  164:     */     
/*  165: 174 */     addIdFunctionProperty(ctor, ARRAY_TAG, -10, "shift", 0);
/*  166:     */     
/*  167: 176 */     addIdFunctionProperty(ctor, ARRAY_TAG, -11, "unshift", 1);
/*  168:     */     
/*  169: 178 */     addIdFunctionProperty(ctor, ARRAY_TAG, -12, "splice", 2);
/*  170:     */     
/*  171: 180 */     addIdFunctionProperty(ctor, ARRAY_TAG, -13, "concat", 1);
/*  172:     */     
/*  173: 182 */     addIdFunctionProperty(ctor, ARRAY_TAG, -14, "slice", 2);
/*  174:     */     
/*  175: 184 */     addIdFunctionProperty(ctor, ARRAY_TAG, -15, "indexOf", 1);
/*  176:     */     
/*  177: 186 */     addIdFunctionProperty(ctor, ARRAY_TAG, -16, "lastIndexOf", 1);
/*  178:     */     
/*  179: 188 */     addIdFunctionProperty(ctor, ARRAY_TAG, -17, "every", 1);
/*  180:     */     
/*  181: 190 */     addIdFunctionProperty(ctor, ARRAY_TAG, -18, "filter", 1);
/*  182:     */     
/*  183: 192 */     addIdFunctionProperty(ctor, ARRAY_TAG, -19, "forEach", 1);
/*  184:     */     
/*  185: 194 */     addIdFunctionProperty(ctor, ARRAY_TAG, -20, "map", 1);
/*  186:     */     
/*  187: 196 */     addIdFunctionProperty(ctor, ARRAY_TAG, -21, "some", 1);
/*  188:     */     
/*  189: 198 */     addIdFunctionProperty(ctor, ARRAY_TAG, -22, "reduce", 1);
/*  190:     */     
/*  191: 200 */     addIdFunctionProperty(ctor, ARRAY_TAG, -23, "reduceRight", 1);
/*  192:     */     
/*  193: 202 */     addIdFunctionProperty(ctor, ARRAY_TAG, -24, "isArray", 1);
/*  194:     */     
/*  195: 204 */     super.fillConstructorProperties(ctor);
/*  196:     */   }
/*  197:     */   
/*  198:     */   protected void initPrototypeId(int id)
/*  199:     */   {
/*  200:     */     int arity;
/*  201:     */     String s;
/*  202: 212 */     switch (id)
/*  203:     */     {
/*  204:     */     case 1: 
/*  205: 213 */       arity = 1;s = "constructor"; break;
/*  206:     */     case 2: 
/*  207: 214 */       arity = 0;s = "toString"; break;
/*  208:     */     case 3: 
/*  209: 215 */       arity = 0;s = "toLocaleString"; break;
/*  210:     */     case 4: 
/*  211: 216 */       arity = 0;s = "toSource"; break;
/*  212:     */     case 5: 
/*  213: 217 */       arity = 1;s = "join"; break;
/*  214:     */     case 6: 
/*  215: 218 */       arity = 0;s = "reverse"; break;
/*  216:     */     case 7: 
/*  217: 219 */       arity = 1;s = "sort"; break;
/*  218:     */     case 8: 
/*  219: 220 */       arity = 1;s = "push"; break;
/*  220:     */     case 9: 
/*  221: 221 */       arity = 0;s = "pop"; break;
/*  222:     */     case 10: 
/*  223: 222 */       arity = 0;s = "shift"; break;
/*  224:     */     case 11: 
/*  225: 223 */       arity = 1;s = "unshift"; break;
/*  226:     */     case 12: 
/*  227: 224 */       arity = 2;s = "splice"; break;
/*  228:     */     case 13: 
/*  229: 225 */       arity = 1;s = "concat"; break;
/*  230:     */     case 14: 
/*  231: 226 */       arity = 2;s = "slice"; break;
/*  232:     */     case 15: 
/*  233: 227 */       arity = 1;s = "indexOf"; break;
/*  234:     */     case 16: 
/*  235: 228 */       arity = 1;s = "lastIndexOf"; break;
/*  236:     */     case 17: 
/*  237: 229 */       arity = 1;s = "every"; break;
/*  238:     */     case 18: 
/*  239: 230 */       arity = 1;s = "filter"; break;
/*  240:     */     case 19: 
/*  241: 231 */       arity = 1;s = "forEach"; break;
/*  242:     */     case 20: 
/*  243: 232 */       arity = 1;s = "map"; break;
/*  244:     */     case 21: 
/*  245: 233 */       arity = 1;s = "some"; break;
/*  246:     */     case 22: 
/*  247: 234 */       arity = 1;s = "reduce"; break;
/*  248:     */     case 23: 
/*  249: 235 */       arity = 1;s = "reduceRight"; break;
/*  250:     */     default: 
/*  251: 236 */       throw new IllegalArgumentException(String.valueOf(id));
/*  252:     */     }
/*  253: 238 */     initPrototypeMethod(ARRAY_TAG, id, s, arity);
/*  254:     */   }
/*  255:     */   
/*  256:     */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  257:     */   {
/*  258: 245 */     if (!f.hasTag(ARRAY_TAG)) {
/*  259: 246 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  260:     */     }
/*  261: 248 */     int id = f.methodId();
/*  262:     */     for (;;)
/*  263:     */     {
/*  264: 251 */       switch (id)
/*  265:     */       {
/*  266:     */       case -23: 
/*  267:     */       case -22: 
/*  268:     */       case -21: 
/*  269:     */       case -20: 
/*  270:     */       case -19: 
/*  271:     */       case -18: 
/*  272:     */       case -17: 
/*  273:     */       case -16: 
/*  274:     */       case -15: 
/*  275:     */       case -14: 
/*  276:     */       case -13: 
/*  277:     */       case -12: 
/*  278:     */       case -11: 
/*  279:     */       case -10: 
/*  280:     */       case -9: 
/*  281:     */       case -8: 
/*  282:     */       case -7: 
/*  283:     */       case -6: 
/*  284:     */       case -5: 
/*  285: 271 */         if (args.length > 0)
/*  286:     */         {
/*  287: 272 */           thisObj = ScriptRuntime.toObject(scope, args[0]);
/*  288: 273 */           Object[] newArgs = new Object[args.length - 1];
/*  289: 274 */           for (int i = 0; i < newArgs.length; i++) {
/*  290: 275 */             newArgs[i] = args[(i + 1)];
/*  291:     */           }
/*  292: 276 */           args = newArgs;
/*  293:     */         }
/*  294: 278 */         id = -id;
/*  295:     */       }
/*  296:     */     }
/*  297: 283 */     return Boolean.valueOf((args.length > 0) && ((args[0] instanceof NativeArray)));
/*  298:     */     
/*  299:     */ 
/*  300: 286 */     boolean inNewExpr = thisObj == null;
/*  301: 287 */     if (!inNewExpr) {
/*  302: 289 */       return f.construct(cx, scope, args);
/*  303:     */     }
/*  304: 291 */     return jsConstructor(cx, scope, args);
/*  305:     */     
/*  306:     */ 
/*  307:     */ 
/*  308: 295 */     return toStringHelper(cx, scope, thisObj, cx.hasFeature(4), false);
/*  309:     */     
/*  310:     */ 
/*  311:     */ 
/*  312: 299 */     return toStringHelper(cx, scope, thisObj, false, true);
/*  313:     */     
/*  314:     */ 
/*  315: 302 */     return toStringHelper(cx, scope, thisObj, true, false);
/*  316:     */     
/*  317:     */ 
/*  318: 305 */     return js_join(cx, thisObj, args);
/*  319:     */     
/*  320:     */ 
/*  321: 308 */     return js_reverse(cx, thisObj, args);
/*  322:     */     
/*  323:     */ 
/*  324: 311 */     return js_sort(cx, scope, thisObj, args);
/*  325:     */     
/*  326:     */ 
/*  327: 314 */     return js_push(cx, thisObj, args);
/*  328:     */     
/*  329:     */ 
/*  330: 317 */     return js_pop(cx, thisObj, args);
/*  331:     */     
/*  332:     */ 
/*  333: 320 */     return js_shift(cx, thisObj, args);
/*  334:     */     
/*  335:     */ 
/*  336: 323 */     return js_unshift(cx, thisObj, args);
/*  337:     */     
/*  338:     */ 
/*  339: 326 */     return js_splice(cx, scope, thisObj, args);
/*  340:     */     
/*  341:     */ 
/*  342: 329 */     return js_concat(cx, scope, thisObj, args);
/*  343:     */     
/*  344:     */ 
/*  345: 332 */     return js_slice(cx, thisObj, args);
/*  346:     */     
/*  347:     */ 
/*  348: 335 */     return indexOfHelper(cx, thisObj, args, false);
/*  349:     */     
/*  350:     */ 
/*  351: 338 */     return indexOfHelper(cx, thisObj, args, true);
/*  352:     */     
/*  353:     */ 
/*  354:     */ 
/*  355:     */ 
/*  356:     */ 
/*  357:     */ 
/*  358: 345 */     return iterativeMethod(cx, id, scope, thisObj, args);
/*  359:     */     
/*  360:     */ 
/*  361: 348 */     return reduceMethod(cx, id, scope, thisObj, args);
/*  362:     */     
/*  363: 350 */     throw new IllegalArgumentException(String.valueOf(id));
/*  364:     */   }
/*  365:     */   
/*  366:     */   public Object get(int index, Scriptable start)
/*  367:     */   {
/*  368: 357 */     if ((!this.denseOnly) && (isGetterOrSetter(null, index, false))) {
/*  369: 358 */       return super.get(index, start);
/*  370:     */     }
/*  371: 359 */     if ((this.dense != null) && (0 <= index) && (index < this.dense.length)) {
/*  372: 360 */       return this.dense[index];
/*  373:     */     }
/*  374: 361 */     return super.get(index, start);
/*  375:     */   }
/*  376:     */   
/*  377:     */   public boolean has(int index, Scriptable start)
/*  378:     */   {
/*  379: 367 */     if ((!this.denseOnly) && (isGetterOrSetter(null, index, false))) {
/*  380: 368 */       return super.has(index, start);
/*  381:     */     }
/*  382: 369 */     if ((this.dense != null) && (0 <= index) && (index < this.dense.length)) {
/*  383: 370 */       return this.dense[index] != NOT_FOUND;
/*  384:     */     }
/*  385: 371 */     return super.has(index, start);
/*  386:     */   }
/*  387:     */   
/*  388:     */   private static long toArrayIndex(String id)
/*  389:     */   {
/*  390: 378 */     double d = ScriptRuntime.toNumber(id);
/*  391: 379 */     if (d == d)
/*  392:     */     {
/*  393: 380 */       long index = ScriptRuntime.toUint32(d);
/*  394: 381 */       if ((index == d) && (index != 4294967295L)) {
/*  395: 384 */         if (Long.toString(index).equals(id)) {
/*  396: 385 */           return index;
/*  397:     */         }
/*  398:     */       }
/*  399:     */     }
/*  400: 389 */     return -1L;
/*  401:     */   }
/*  402:     */   
/*  403:     */   public void put(String id, Scriptable start, Object value)
/*  404:     */   {
/*  405: 395 */     super.put(id, start, value);
/*  406: 396 */     if (start == this)
/*  407:     */     {
/*  408: 398 */       long index = toArrayIndex(id);
/*  409: 399 */       if (index >= this.length)
/*  410:     */       {
/*  411: 400 */         this.length = (index + 1L);
/*  412: 401 */         this.denseOnly = false;
/*  413:     */       }
/*  414:     */     }
/*  415:     */   }
/*  416:     */   
/*  417:     */   private boolean ensureCapacity(int capacity)
/*  418:     */   {
/*  419: 408 */     if (capacity > this.dense.length)
/*  420:     */     {
/*  421: 409 */       if (capacity > 1431655764)
/*  422:     */       {
/*  423: 410 */         this.denseOnly = false;
/*  424: 411 */         return false;
/*  425:     */       }
/*  426: 413 */       capacity = Math.max(capacity, (int)(this.dense.length * 1.5D));
/*  427: 414 */       Object[] newDense = new Object[capacity];
/*  428: 415 */       System.arraycopy(this.dense, 0, newDense, 0, this.dense.length);
/*  429: 416 */       Arrays.fill(newDense, this.dense.length, newDense.length, Scriptable.NOT_FOUND);
/*  430:     */       
/*  431: 418 */       this.dense = newDense;
/*  432:     */     }
/*  433: 420 */     return true;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void put(int index, Scriptable start, Object value)
/*  437:     */   {
/*  438: 426 */     if ((start == this) && (!isSealed()) && (this.dense != null) && (0 <= index) && ((this.denseOnly) || (!isGetterOrSetter(null, index, true))))
/*  439:     */     {
/*  440: 429 */       if (index < this.dense.length)
/*  441:     */       {
/*  442: 430 */         this.dense[index] = value;
/*  443: 431 */         if (this.length <= index) {
/*  444: 432 */           this.length = (index + 1L);
/*  445:     */         }
/*  446: 433 */         return;
/*  447:     */       }
/*  448: 434 */       if ((this.denseOnly) && (index < this.dense.length * 1.5D) && (ensureCapacity(index + 1)))
/*  449:     */       {
/*  450: 437 */         this.dense[index] = value;
/*  451: 438 */         this.length = (index + 1L);
/*  452: 439 */         return;
/*  453:     */       }
/*  454: 441 */       this.denseOnly = false;
/*  455:     */     }
/*  456: 444 */     super.put(index, start, value);
/*  457: 445 */     if (start == this) {
/*  458: 447 */       if (this.length <= index) {
/*  459: 449 */         this.length = (index + 1L);
/*  460:     */       }
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   public void delete(int index)
/*  465:     */   {
/*  466: 457 */     if ((this.dense != null) && (0 <= index) && (index < this.dense.length) && (!isSealed()) && ((this.denseOnly) || (!isGetterOrSetter(null, index, true)))) {
/*  467: 460 */       this.dense[index] = NOT_FOUND;
/*  468:     */     } else {
/*  469: 462 */       super.delete(index);
/*  470:     */     }
/*  471:     */   }
/*  472:     */   
/*  473:     */   public Object[] getIds()
/*  474:     */   {
/*  475: 469 */     Object[] superIds = super.getIds();
/*  476: 470 */     if (this.dense == null) {
/*  477: 470 */       return superIds;
/*  478:     */     }
/*  479: 471 */     int N = this.dense.length;
/*  480: 472 */     long currentLength = this.length;
/*  481: 473 */     if (N > currentLength) {
/*  482: 474 */       N = (int)currentLength;
/*  483:     */     }
/*  484: 476 */     if (N == 0) {
/*  485: 476 */       return superIds;
/*  486:     */     }
/*  487: 477 */     int superLength = superIds.length;
/*  488: 478 */     Object[] ids = new Object[N + superLength];
/*  489:     */     
/*  490: 480 */     int presentCount = 0;
/*  491: 481 */     for (int i = 0; i != N; i++) {
/*  492: 483 */       if (this.dense[i] != NOT_FOUND)
/*  493:     */       {
/*  494: 484 */         ids[presentCount] = Integer.valueOf(i);
/*  495: 485 */         presentCount++;
/*  496:     */       }
/*  497:     */     }
/*  498: 488 */     if (presentCount != N)
/*  499:     */     {
/*  500: 490 */       Object[] tmp = new Object[presentCount + superLength];
/*  501: 491 */       System.arraycopy(ids, 0, tmp, 0, presentCount);
/*  502: 492 */       ids = tmp;
/*  503:     */     }
/*  504: 494 */     System.arraycopy(superIds, 0, ids, presentCount, superLength);
/*  505: 495 */     return ids;
/*  506:     */   }
/*  507:     */   
/*  508:     */   public Object[] getAllIds()
/*  509:     */   {
/*  510: 501 */     Set<Object> allIds = new LinkedHashSet(Arrays.asList(getIds()));
/*  511:     */     
/*  512: 503 */     allIds.addAll(Arrays.asList(super.getAllIds()));
/*  513: 504 */     return allIds.toArray();
/*  514:     */   }
/*  515:     */   
/*  516:     */   public Integer[] getIndexIds()
/*  517:     */   {
/*  518: 508 */     Object[] ids = getIds();
/*  519: 509 */     List<Integer> indices = new ArrayList(ids.length);
/*  520: 510 */     for (Object id : ids)
/*  521:     */     {
/*  522: 511 */       int int32Id = ScriptRuntime.toInt32(id);
/*  523: 512 */       if ((int32Id >= 0) && (ScriptRuntime.toString(int32Id).equals(ScriptRuntime.toString(id)))) {
/*  524: 513 */         indices.add(Integer.valueOf(int32Id));
/*  525:     */       }
/*  526:     */     }
/*  527: 516 */     return (Integer[])indices.toArray(new Integer[indices.size()]);
/*  528:     */   }
/*  529:     */   
/*  530:     */   public Object getDefaultValue(Class<?> hint)
/*  531:     */   {
/*  532: 522 */     if (hint == ScriptRuntime.NumberClass)
/*  533:     */     {
/*  534: 523 */       Context cx = Context.getContext();
/*  535: 524 */       if (cx.getLanguageVersion() == 120) {
/*  536: 525 */         return Long.valueOf(this.length);
/*  537:     */       }
/*  538:     */     }
/*  539: 527 */     return super.getDefaultValue(hint);
/*  540:     */   }
/*  541:     */   
/*  542:     */   private ScriptableObject defaultIndexPropertyDescriptor(Object value)
/*  543:     */   {
/*  544: 531 */     Scriptable scope = getParentScope();
/*  545: 532 */     if (scope == null) {
/*  546: 532 */       scope = this;
/*  547:     */     }
/*  548: 533 */     ScriptableObject desc = new NativeObject();
/*  549: 534 */     ScriptRuntime.setBuiltinProtoAndParent(desc, scope, TopLevel.Builtins.Object);
/*  550: 535 */     desc.defineProperty("value", value, 0);
/*  551: 536 */     desc.defineProperty("writable", Boolean.valueOf(true), 0);
/*  552: 537 */     desc.defineProperty("enumerable", Boolean.valueOf(true), 0);
/*  553: 538 */     desc.defineProperty("configurable", Boolean.valueOf(true), 0);
/*  554: 539 */     return desc;
/*  555:     */   }
/*  556:     */   
/*  557:     */   protected ScriptableObject getOwnPropertyDescriptor(Context cx, Object id)
/*  558:     */   {
/*  559: 544 */     if (this.dense != null)
/*  560:     */     {
/*  561: 545 */       int index = toIndex(id);
/*  562: 546 */       if ((0 <= index) && (index < this.length))
/*  563:     */       {
/*  564: 547 */         Object value = this.dense[index];
/*  565: 548 */         return defaultIndexPropertyDescriptor(value);
/*  566:     */       }
/*  567:     */     }
/*  568: 551 */     return super.getOwnPropertyDescriptor(cx, id);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public void defineOwnProperty(Context cx, Object id, ScriptableObject desc)
/*  572:     */   {
/*  573: 556 */     if (this.dense != null)
/*  574:     */     {
/*  575: 557 */       Object[] values = this.dense;
/*  576: 558 */       this.dense = null;
/*  577: 559 */       this.denseOnly = false;
/*  578: 560 */       for (int i = 0; i < values.length; i++) {
/*  579: 561 */         if (values[i] != NOT_FOUND) {
/*  580: 562 */           put(i, this, values[i]);
/*  581:     */         }
/*  582:     */       }
/*  583:     */     }
/*  584: 566 */     int index = toIndex(id);
/*  585: 567 */     if (index >= this.length) {
/*  586: 568 */       this.length = (index + 1);
/*  587:     */     }
/*  588: 570 */     super.defineOwnProperty(cx, id, desc);
/*  589:     */   }
/*  590:     */   
/*  591:     */   private int toIndex(Object id)
/*  592:     */   {
/*  593: 574 */     if ((id instanceof String)) {
/*  594: 575 */       return (int)toArrayIndex((String)id);
/*  595:     */     }
/*  596: 576 */     if ((id instanceof Number)) {
/*  597: 577 */       return ((Number)id).intValue();
/*  598:     */     }
/*  599: 579 */     return -1;
/*  600:     */   }
/*  601:     */   
/*  602:     */   private static Object jsConstructor(Context cx, Scriptable scope, Object[] args)
/*  603:     */   {
/*  604: 589 */     if (args.length == 0) {
/*  605: 590 */       return new NativeArray(0L);
/*  606:     */     }
/*  607: 595 */     if (cx.getLanguageVersion() == 120) {
/*  608: 596 */       return new NativeArray(args);
/*  609:     */     }
/*  610: 598 */     Object arg0 = args[0];
/*  611: 599 */     if ((args.length > 1) || (!(arg0 instanceof Number))) {
/*  612: 600 */       return new NativeArray(args);
/*  613:     */     }
/*  614: 602 */     long len = ScriptRuntime.toUint32(arg0);
/*  615: 603 */     if (len != ((Number)arg0).doubleValue())
/*  616:     */     {
/*  617: 604 */       String msg = ScriptRuntime.getMessage0("msg.arraylength.bad");
/*  618: 605 */       throw ScriptRuntime.constructError("RangeError", msg);
/*  619:     */     }
/*  620: 607 */     return new NativeArray(len);
/*  621:     */   }
/*  622:     */   
/*  623:     */   public long getLength()
/*  624:     */   {
/*  625: 613 */     return this.length;
/*  626:     */   }
/*  627:     */   
/*  628:     */   /**
/*  629:     */    * @deprecated
/*  630:     */    */
/*  631:     */   public long jsGet_length()
/*  632:     */   {
/*  633: 618 */     return getLength();
/*  634:     */   }
/*  635:     */   
/*  636:     */   void setDenseOnly(boolean denseOnly)
/*  637:     */   {
/*  638: 631 */     if ((denseOnly) && (!this.denseOnly)) {
/*  639: 632 */       throw new IllegalArgumentException();
/*  640:     */     }
/*  641: 633 */     this.denseOnly = denseOnly;
/*  642:     */   }
/*  643:     */   
/*  644:     */   private void setLength(Object val)
/*  645:     */   {
/*  646: 644 */     double d = ScriptRuntime.toNumber(val);
/*  647: 645 */     long longVal = ScriptRuntime.toUint32(d);
/*  648: 646 */     if (longVal != d)
/*  649:     */     {
/*  650: 647 */       String msg = ScriptRuntime.getMessage0("msg.arraylength.bad");
/*  651: 648 */       throw ScriptRuntime.constructError("RangeError", msg);
/*  652:     */     }
/*  653: 651 */     if (this.denseOnly)
/*  654:     */     {
/*  655: 652 */       if (longVal < this.length)
/*  656:     */       {
/*  657: 654 */         Arrays.fill(this.dense, (int)longVal, this.dense.length, NOT_FOUND);
/*  658: 655 */         this.length = longVal;
/*  659: 656 */         return;
/*  660:     */       }
/*  661: 657 */       if ((longVal < 1431655764L) && (longVal < this.length * 1.5D) && (ensureCapacity((int)longVal)))
/*  662:     */       {
/*  663: 661 */         this.length = longVal;
/*  664: 662 */         return;
/*  665:     */       }
/*  666: 664 */       this.denseOnly = false;
/*  667:     */     }
/*  668: 667 */     if (longVal < this.length) {
/*  669: 669 */       if (this.length - longVal > 4096L)
/*  670:     */       {
/*  671: 671 */         Object[] e = getIds();
/*  672: 672 */         for (int i = 0; i < e.length; i++)
/*  673:     */         {
/*  674: 673 */           Object id = e[i];
/*  675: 674 */           if ((id instanceof String))
/*  676:     */           {
/*  677: 676 */             String strId = (String)id;
/*  678: 677 */             long index = toArrayIndex(strId);
/*  679: 678 */             if (index >= longVal) {
/*  680: 679 */               delete(strId);
/*  681:     */             }
/*  682:     */           }
/*  683:     */           else
/*  684:     */           {
/*  685: 681 */             int index = ((Integer)id).intValue();
/*  686: 682 */             if (index >= longVal) {
/*  687: 683 */               delete(index);
/*  688:     */             }
/*  689:     */           }
/*  690:     */         }
/*  691:     */       }
/*  692:     */       else
/*  693:     */       {
/*  694: 688 */         for (long i = longVal; i < this.length; i += 1L) {
/*  695: 689 */           deleteElem(this, i);
/*  696:     */         }
/*  697:     */       }
/*  698:     */     }
/*  699: 693 */     this.length = longVal;
/*  700:     */   }
/*  701:     */   
/*  702:     */   static long getLengthProperty(Context cx, Scriptable obj)
/*  703:     */   {
/*  704: 704 */     if ((obj instanceof NativeString)) {
/*  705: 705 */       return ((NativeString)obj).getLength();
/*  706:     */     }
/*  707: 706 */     if ((obj instanceof NativeArray)) {
/*  708: 707 */       return ((NativeArray)obj).getLength();
/*  709:     */     }
/*  710: 709 */     return ScriptRuntime.toUint32(ScriptRuntime.getObjectProp(obj, "length", cx));
/*  711:     */   }
/*  712:     */   
/*  713:     */   private static Object setLengthProperty(Context cx, Scriptable target, long length)
/*  714:     */   {
/*  715: 716 */     return ScriptRuntime.setObjectProp(target, "length", ScriptRuntime.wrapNumber(length), cx);
/*  716:     */   }
/*  717:     */   
/*  718:     */   private static void deleteElem(Scriptable target, long index)
/*  719:     */   {
/*  720: 726 */     int i = (int)index;
/*  721: 727 */     if (i == index) {
/*  722: 727 */       target.delete(i);
/*  723:     */     } else {
/*  724: 728 */       target.delete(Long.toString(index));
/*  725:     */     }
/*  726:     */   }
/*  727:     */   
/*  728:     */   private static Object getElem(Context cx, Scriptable target, long index)
/*  729:     */   {
/*  730: 733 */     if (index > 2147483647L)
/*  731:     */     {
/*  732: 734 */       String id = Long.toString(index);
/*  733: 735 */       return ScriptRuntime.getObjectProp(target, id, cx);
/*  734:     */     }
/*  735: 737 */     return ScriptRuntime.getObjectIndex(target, (int)index, cx);
/*  736:     */   }
/*  737:     */   
/*  738:     */   private static Object getRawElem(Scriptable target, long index)
/*  739:     */   {
/*  740: 743 */     if (index > 2147483647L) {
/*  741: 744 */       return ScriptableObject.getProperty(target, Long.toString(index));
/*  742:     */     }
/*  743: 746 */     return ScriptableObject.getProperty(target, (int)index);
/*  744:     */   }
/*  745:     */   
/*  746:     */   private static void setElem(Context cx, Scriptable target, long index, Object value)
/*  747:     */   {
/*  748: 753 */     if (index > 2147483647L)
/*  749:     */     {
/*  750: 754 */       String id = Long.toString(index);
/*  751: 755 */       ScriptRuntime.setObjectProp(target, id, value, cx);
/*  752:     */     }
/*  753:     */     else
/*  754:     */     {
/*  755: 757 */       ScriptRuntime.setObjectIndex(target, (int)index, value, cx);
/*  756:     */     }
/*  757:     */   }
/*  758:     */   
/*  759:     */   private static String toStringHelper(Context cx, Scriptable scope, Scriptable thisObj, boolean toSource, boolean toLocale)
/*  760:     */   {
/*  761: 769 */     long length = getLengthProperty(cx, thisObj);
/*  762:     */     
/*  763: 771 */     StringBuilder result = new StringBuilder(256);
/*  764:     */     String separator;
/*  765:     */     String separator;
/*  766: 776 */     if (toSource)
/*  767:     */     {
/*  768: 777 */       result.append('[');
/*  769: 778 */       separator = ", ";
/*  770:     */     }
/*  771:     */     else
/*  772:     */     {
/*  773: 780 */       separator = ",";
/*  774:     */     }
/*  775: 783 */     boolean haslast = false;
/*  776: 784 */     long i = 0L;
/*  777:     */     boolean toplevel;
/*  778:     */     boolean iterating;
/*  779: 787 */     if (cx.iterating == null)
/*  780:     */     {
/*  781: 788 */       boolean toplevel = true;
/*  782: 789 */       boolean iterating = false;
/*  783: 790 */       cx.iterating = new ObjToIntMap(31);
/*  784:     */     }
/*  785:     */     else
/*  786:     */     {
/*  787: 792 */       toplevel = false;
/*  788: 793 */       iterating = cx.iterating.has(thisObj);
/*  789:     */     }
/*  790:     */     try
/*  791:     */     {
/*  792: 799 */       if (!iterating)
/*  793:     */       {
/*  794: 800 */         cx.iterating.put(thisObj, 0);
/*  795: 801 */         for (i = 0L; i < length; i += 1L)
/*  796:     */         {
/*  797: 802 */           if (i > 0L) {
/*  798: 802 */             result.append(separator);
/*  799:     */           }
/*  800: 803 */           Object elem = getElem(cx, thisObj, i);
/*  801: 804 */           if ((elem == null) || (elem == Undefined.instance))
/*  802:     */           {
/*  803: 805 */             haslast = false;
/*  804:     */           }
/*  805:     */           else
/*  806:     */           {
/*  807: 808 */             haslast = true;
/*  808: 810 */             if (toSource)
/*  809:     */             {
/*  810: 811 */               result.append(ScriptRuntime.uneval(cx, scope, elem));
/*  811:     */             }
/*  812: 813 */             else if ((elem instanceof String))
/*  813:     */             {
/*  814: 814 */               String s = (String)elem;
/*  815: 815 */               if (toSource)
/*  816:     */               {
/*  817: 816 */                 result.append('"');
/*  818: 817 */                 result.append(ScriptRuntime.escapeString(s));
/*  819: 818 */                 result.append('"');
/*  820:     */               }
/*  821:     */               else
/*  822:     */               {
/*  823: 820 */                 result.append(s);
/*  824:     */               }
/*  825:     */             }
/*  826:     */             else
/*  827:     */             {
/*  828: 824 */               if (toLocale)
/*  829:     */               {
/*  830: 828 */                 Callable fun = ScriptRuntime.getPropFunctionAndThis(elem, "toLocaleString", cx);
/*  831:     */                 
/*  832: 830 */                 Scriptable funThis = ScriptRuntime.lastStoredScriptable(cx);
/*  833: 831 */                 elem = fun.call(cx, scope, funThis, ScriptRuntime.emptyArgs);
/*  834:     */               }
/*  835: 834 */               result.append(ScriptRuntime.toString(elem));
/*  836:     */             }
/*  837:     */           }
/*  838:     */         }
/*  839:     */       }
/*  840:     */     }
/*  841:     */     finally
/*  842:     */     {
/*  843: 839 */       if (toplevel) {
/*  844: 840 */         cx.iterating = null;
/*  845:     */       }
/*  846:     */     }
/*  847: 844 */     if (toSource) {
/*  848: 846 */       if ((!haslast) && (i > 0L)) {
/*  849: 847 */         result.append(", ]");
/*  850:     */       } else {
/*  851: 849 */         result.append(']');
/*  852:     */       }
/*  853:     */     }
/*  854: 851 */     return result.toString();
/*  855:     */   }
/*  856:     */   
/*  857:     */   private static String js_join(Context cx, Scriptable thisObj, Object[] args)
/*  858:     */   {
/*  859: 860 */     long llength = getLengthProperty(cx, thisObj);
/*  860: 861 */     int length = (int)llength;
/*  861: 862 */     if (llength != length) {
/*  862: 863 */       throw Context.reportRuntimeError1("msg.arraylength.too.big", String.valueOf(llength));
/*  863:     */     }
/*  864: 867 */     String separator = (args.length < 1) || (args[0] == Undefined.instance) ? "," : ScriptRuntime.toString(args[0]);
/*  865: 870 */     if ((thisObj instanceof NativeArray))
/*  866:     */     {
/*  867: 871 */       NativeArray na = (NativeArray)thisObj;
/*  868: 872 */       if (na.denseOnly)
/*  869:     */       {
/*  870: 873 */         StringBuilder sb = new StringBuilder();
/*  871: 874 */         for (int i = 0; i < length; i++)
/*  872:     */         {
/*  873: 875 */           if (i != 0) {
/*  874: 876 */             sb.append(separator);
/*  875:     */           }
/*  876: 878 */           if (i < na.dense.length)
/*  877:     */           {
/*  878: 879 */             Object temp = na.dense[i];
/*  879: 880 */             if ((temp != null) && (temp != Undefined.instance) && (temp != Scriptable.NOT_FOUND)) {
/*  880: 883 */               sb.append(ScriptRuntime.toString(temp));
/*  881:     */             }
/*  882:     */           }
/*  883:     */         }
/*  884: 887 */         return sb.toString();
/*  885:     */       }
/*  886:     */     }
/*  887: 890 */     if (length == 0) {
/*  888: 891 */       return "";
/*  889:     */     }
/*  890: 893 */     String[] buf = new String[length];
/*  891: 894 */     int total_size = 0;
/*  892: 895 */     for (int i = 0; i != length; i++)
/*  893:     */     {
/*  894: 896 */       Object temp = getElem(cx, thisObj, i);
/*  895: 897 */       if ((temp != null) && (temp != Undefined.instance))
/*  896:     */       {
/*  897: 898 */         String str = ScriptRuntime.toString(temp);
/*  898: 899 */         total_size += str.length();
/*  899: 900 */         buf[i] = str;
/*  900:     */       }
/*  901:     */     }
/*  902: 903 */     total_size += (length - 1) * separator.length();
/*  903: 904 */     StringBuilder sb = new StringBuilder(total_size);
/*  904: 905 */     for (int i = 0; i != length; i++)
/*  905:     */     {
/*  906: 906 */       if (i != 0) {
/*  907: 907 */         sb.append(separator);
/*  908:     */       }
/*  909: 909 */       String str = buf[i];
/*  910: 910 */       if (str != null) {
/*  911: 912 */         sb.append(str);
/*  912:     */       }
/*  913:     */     }
/*  914: 915 */     return sb.toString();
/*  915:     */   }
/*  916:     */   
/*  917:     */   private static Scriptable js_reverse(Context cx, Scriptable thisObj, Object[] args)
/*  918:     */   {
/*  919: 924 */     if ((thisObj instanceof NativeArray))
/*  920:     */     {
/*  921: 925 */       NativeArray na = (NativeArray)thisObj;
/*  922: 926 */       if (na.denseOnly)
/*  923:     */       {
/*  924: 927 */         int i = 0;
/*  925: 927 */         for (int j = (int)na.length - 1; i < j; j--)
/*  926:     */         {
/*  927: 928 */           Object temp = na.dense[i];
/*  928: 929 */           na.dense[i] = na.dense[j];
/*  929: 930 */           na.dense[j] = temp;i++;
/*  930:     */         }
/*  931: 932 */         return thisObj;
/*  932:     */       }
/*  933:     */     }
/*  934: 935 */     long len = getLengthProperty(cx, thisObj);
/*  935:     */     
/*  936: 937 */     long half = len / 2L;
/*  937: 938 */     for (long i = 0L; i < half; i += 1L)
/*  938:     */     {
/*  939: 939 */       long j = len - i - 1L;
/*  940: 940 */       Object temp1 = getElem(cx, thisObj, i);
/*  941: 941 */       Object temp2 = getElem(cx, thisObj, j);
/*  942: 942 */       setElem(cx, thisObj, i, temp2);
/*  943: 943 */       setElem(cx, thisObj, j, temp1);
/*  944:     */     }
/*  945: 945 */     return thisObj;
/*  946:     */   }
/*  947:     */   
/*  948:     */   private static Scriptable js_sort(final Context cx, final Scriptable scope, Scriptable thisObj, Object[] args)
/*  949:     */   {
/*  950:     */     Comparator<Object> comparator;
/*  951:     */     Comparator<Object> comparator;
/*  952: 955 */     if ((args.length > 0) && (Undefined.instance != args[0]))
/*  953:     */     {
/*  954: 956 */       final Callable jsCompareFunction = ScriptRuntime.getValueFunctionAndThis(args[0], cx);
/*  955:     */       
/*  956: 958 */       final Scriptable funThis = ScriptRuntime.lastStoredScriptable(cx);
/*  957: 959 */       Object[] cmpBuf = new Object[2];
/*  958: 960 */       comparator = new Comparator()
/*  959:     */       {
/*  960:     */         public int compare(Object x, Object y)
/*  961:     */         {
/*  962: 963 */           if (x == y) {
/*  963: 964 */             return 0;
/*  964:     */           }
/*  965: 965 */           if ((y == Undefined.instance) || (y == Scriptable.NOT_FOUND)) {
/*  966: 967 */             return -1;
/*  967:     */           }
/*  968: 968 */           if ((x == Undefined.instance) || (x == Scriptable.NOT_FOUND)) {
/*  969: 970 */             return 1;
/*  970:     */           }
/*  971: 973 */           this.val$cmpBuf[0] = x;
/*  972: 974 */           this.val$cmpBuf[1] = y;
/*  973: 975 */           Object ret = jsCompareFunction.call(cx, scope, funThis, this.val$cmpBuf);
/*  974:     */           
/*  975: 977 */           double d = ScriptRuntime.toNumber(ret);
/*  976: 978 */           if (d < 0.0D) {
/*  977: 979 */             return -1;
/*  978:     */           }
/*  979: 980 */           if (d > 0.0D) {
/*  980: 981 */             return 1;
/*  981:     */           }
/*  982: 983 */           return 0;
/*  983:     */         }
/*  984:     */       };
/*  985:     */     }
/*  986:     */     else
/*  987:     */     {
/*  988: 987 */       comparator = new Comparator()
/*  989:     */       {
/*  990:     */         public int compare(Object x, Object y)
/*  991:     */         {
/*  992: 990 */           if (x == y) {
/*  993: 991 */             return 0;
/*  994:     */           }
/*  995: 992 */           if ((y == Undefined.instance) || (y == Scriptable.NOT_FOUND)) {
/*  996: 994 */             return -1;
/*  997:     */           }
/*  998: 995 */           if ((x == Undefined.instance) || (x == Scriptable.NOT_FOUND)) {
/*  999: 997 */             return 1;
/* 1000:     */           }
/* 1001:1000 */           String a = ScriptRuntime.toString(x);
/* 1002:1001 */           String b = ScriptRuntime.toString(y);
/* 1003:1002 */           return a.compareTo(b);
/* 1004:     */         }
/* 1005:     */       };
/* 1006:     */     }
/* 1007:1007 */     int length = (int)getLengthProperty(cx, thisObj);
/* 1008:     */     
/* 1009:     */ 
/* 1010:1010 */     Object[] working = new Object[length];
/* 1011:1011 */     for (int i = 0; i != length; i++) {
/* 1012:1012 */       working[i] = getElem(cx, thisObj, i);
/* 1013:     */     }
/* 1014:1015 */     Arrays.sort(working, comparator);
/* 1015:1018 */     for (int i = 0; i < length; i++) {
/* 1016:1019 */       setElem(cx, thisObj, i, working[i]);
/* 1017:     */     }
/* 1018:1022 */     return thisObj;
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   private static Object js_push(Context cx, Scriptable thisObj, Object[] args)
/* 1022:     */   {
/* 1023:1032 */     if ((thisObj instanceof NativeArray))
/* 1024:     */     {
/* 1025:1033 */       NativeArray na = (NativeArray)thisObj;
/* 1026:1034 */       if ((na.denseOnly) && (na.ensureCapacity((int)na.length + args.length)))
/* 1027:     */       {
/* 1028:1037 */         for (int i = 0; i < args.length; i++) {
/* 1029:1038 */           na.dense[((int)na.length++)] = args[i];
/* 1030:     */         }
/* 1031:1040 */         return ScriptRuntime.wrapNumber(na.length);
/* 1032:     */       }
/* 1033:     */     }
/* 1034:1043 */     long length = getLengthProperty(cx, thisObj);
/* 1035:1044 */     for (int i = 0; i < args.length; i++) {
/* 1036:1045 */       setElem(cx, thisObj, length + i, args[i]);
/* 1037:     */     }
/* 1038:1048 */     length += args.length;
/* 1039:1049 */     Object lengthObj = setLengthProperty(cx, thisObj, length);
/* 1040:1055 */     if (cx.getLanguageVersion() == 120) {
/* 1041:1057 */       return args.length == 0 ? Undefined.instance : args[(args.length - 1)];
/* 1042:     */     }
/* 1043:1062 */     return lengthObj;
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   private static Object js_pop(Context cx, Scriptable thisObj, Object[] args)
/* 1047:     */   {
/* 1048:1069 */     if ((thisObj instanceof NativeArray))
/* 1049:     */     {
/* 1050:1070 */       NativeArray na = (NativeArray)thisObj;
/* 1051:1071 */       if ((na.denseOnly) && (na.length > 0L))
/* 1052:     */       {
/* 1053:1072 */         na.length -= 1L;
/* 1054:1073 */         Object result = na.dense[((int)na.length)];
/* 1055:1074 */         na.dense[((int)na.length)] = NOT_FOUND;
/* 1056:1075 */         return result;
/* 1057:     */       }
/* 1058:     */     }
/* 1059:1078 */     long length = getLengthProperty(cx, thisObj);
/* 1060:     */     Object result;
/* 1061:     */     Object result;
/* 1062:1079 */     if (length > 0L)
/* 1063:     */     {
/* 1064:1080 */       length -= 1L;
/* 1065:     */       
/* 1066:     */ 
/* 1067:1083 */       result = getElem(cx, thisObj, length);
/* 1068:     */     }
/* 1069:     */     else
/* 1070:     */     {
/* 1071:1088 */       result = Undefined.instance;
/* 1072:     */     }
/* 1073:1092 */     setLengthProperty(cx, thisObj, length);
/* 1074:     */     
/* 1075:1094 */     return result;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   private static Object js_shift(Context cx, Scriptable thisObj, Object[] args)
/* 1079:     */   {
/* 1080:1100 */     if ((thisObj instanceof NativeArray))
/* 1081:     */     {
/* 1082:1101 */       NativeArray na = (NativeArray)thisObj;
/* 1083:1102 */       if ((na.denseOnly) && (na.length > 0L))
/* 1084:     */       {
/* 1085:1103 */         na.length -= 1L;
/* 1086:1104 */         Object result = na.dense[0];
/* 1087:1105 */         System.arraycopy(na.dense, 1, na.dense, 0, (int)na.length);
/* 1088:1106 */         na.dense[((int)na.length)] = NOT_FOUND;
/* 1089:1107 */         return result;
/* 1090:     */       }
/* 1091:     */     }
/* 1092:1111 */     long length = getLengthProperty(cx, thisObj);
/* 1093:     */     Object result;
/* 1094:1112 */     if (length > 0L)
/* 1095:     */     {
/* 1096:1113 */       long i = 0L;
/* 1097:1114 */       length -= 1L;
/* 1098:     */       
/* 1099:     */ 
/* 1100:1117 */       Object result = getElem(cx, thisObj, i);
/* 1101:1123 */       if (length > 0L) {
/* 1102:1124 */         for (i = 1L; i <= length; i += 1L)
/* 1103:     */         {
/* 1104:1125 */           Object temp = getElem(cx, thisObj, i);
/* 1105:1126 */           setElem(cx, thisObj, i - 1L, temp);
/* 1106:     */         }
/* 1107:     */       }
/* 1108:     */     }
/* 1109:     */     else
/* 1110:     */     {
/* 1111:1132 */       result = Undefined.instance;
/* 1112:     */     }
/* 1113:1134 */     setLengthProperty(cx, thisObj, length);
/* 1114:1135 */     return result;
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   private static Object js_unshift(Context cx, Scriptable thisObj, Object[] args)
/* 1118:     */   {
/* 1119:1141 */     if ((thisObj instanceof NativeArray))
/* 1120:     */     {
/* 1121:1142 */       NativeArray na = (NativeArray)thisObj;
/* 1122:1143 */       if ((na.denseOnly) && (na.ensureCapacity((int)na.length + args.length)))
/* 1123:     */       {
/* 1124:1146 */         System.arraycopy(na.dense, 0, na.dense, args.length, (int)na.length);
/* 1125:1148 */         for (int i = 0; i < args.length; i++) {
/* 1126:1149 */           na.dense[i] = args[i];
/* 1127:     */         }
/* 1128:1151 */         na.length += args.length;
/* 1129:1152 */         return ScriptRuntime.wrapNumber(na.length);
/* 1130:     */       }
/* 1131:     */     }
/* 1132:1155 */     long length = getLengthProperty(cx, thisObj);
/* 1133:1156 */     int argc = args.length;
/* 1134:1158 */     if (args.length > 0)
/* 1135:     */     {
/* 1136:1160 */       if (length > 0L) {
/* 1137:1161 */         for (long last = length - 1L; last >= 0L; last -= 1L)
/* 1138:     */         {
/* 1139:1162 */           Object temp = getElem(cx, thisObj, last);
/* 1140:1163 */           setElem(cx, thisObj, last + argc, temp);
/* 1141:     */         }
/* 1142:     */       }
/* 1143:1168 */       for (int i = 0; i < args.length; i++) {
/* 1144:1169 */         setElem(cx, thisObj, i, args[i]);
/* 1145:     */       }
/* 1146:1173 */       length += args.length;
/* 1147:1174 */       return setLengthProperty(cx, thisObj, length);
/* 1148:     */     }
/* 1149:1176 */     return ScriptRuntime.wrapNumber(length);
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   private static Object js_splice(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 1153:     */   {
/* 1154:1182 */     NativeArray na = null;
/* 1155:1183 */     boolean denseMode = false;
/* 1156:1184 */     if ((thisObj instanceof NativeArray))
/* 1157:     */     {
/* 1158:1185 */       na = (NativeArray)thisObj;
/* 1159:1186 */       denseMode = na.denseOnly;
/* 1160:     */     }
/* 1161:1190 */     scope = getTopLevelScope(scope);
/* 1162:1191 */     int argc = args.length;
/* 1163:1192 */     if (argc == 0) {
/* 1164:1193 */       return cx.newArray(scope, 0);
/* 1165:     */     }
/* 1166:1194 */     long length = getLengthProperty(cx, thisObj);
/* 1167:     */     
/* 1168:     */ 
/* 1169:1197 */     long begin = toSliceIndex(ScriptRuntime.toInteger(args[0]), length);
/* 1170:1198 */     argc--;
/* 1171:     */     long count;
/* 1172:     */     long count;
/* 1173:1202 */     if (args.length == 1)
/* 1174:     */     {
/* 1175:1203 */       count = length - begin;
/* 1176:     */     }
/* 1177:     */     else
/* 1178:     */     {
/* 1179:1205 */       double dcount = ScriptRuntime.toInteger(args[1]);
/* 1180:     */       long count;
/* 1181:1206 */       if (dcount < 0.0D)
/* 1182:     */       {
/* 1183:1207 */         count = 0L;
/* 1184:     */       }
/* 1185:     */       else
/* 1186:     */       {
/* 1187:     */         long count;
/* 1188:1208 */         if (dcount > length - begin) {
/* 1189:1209 */           count = length - begin;
/* 1190:     */         } else {
/* 1191:1211 */           count = dcount;
/* 1192:     */         }
/* 1193:     */       }
/* 1194:1213 */       argc--;
/* 1195:     */     }
/* 1196:1216 */     long end = begin + count;
/* 1197:     */     Object result;
/* 1198:     */     Object result;
/* 1199:1220 */     if (count != 0L)
/* 1200:     */     {
/* 1201:     */       Object result;
/* 1202:1221 */       if ((count == 1L) && (cx.getLanguageVersion() == 120))
/* 1203:     */       {
/* 1204:1235 */         result = getElem(cx, thisObj, begin);
/* 1205:     */       }
/* 1206:     */       else
/* 1207:     */       {
/* 1208:     */         Object result;
/* 1209:1237 */         if (denseMode)
/* 1210:     */         {
/* 1211:1238 */           int intLen = (int)(end - begin);
/* 1212:1239 */           Object[] copy = new Object[intLen];
/* 1213:1240 */           System.arraycopy(na.dense, (int)begin, copy, 0, intLen);
/* 1214:1241 */           result = cx.newArray(scope, copy);
/* 1215:     */         }
/* 1216:     */         else
/* 1217:     */         {
/* 1218:1243 */           Scriptable resultArray = cx.newArray(scope, 0);
/* 1219:1244 */           for (long last = begin; last != end; last += 1L)
/* 1220:     */           {
/* 1221:1245 */             Object temp = getElem(cx, thisObj, last);
/* 1222:1246 */             setElem(cx, resultArray, last - begin, temp);
/* 1223:     */           }
/* 1224:1248 */           result = resultArray;
/* 1225:     */         }
/* 1226:     */       }
/* 1227:     */     }
/* 1228:     */     else
/* 1229:     */     {
/* 1230:     */       Object result;
/* 1231:1252 */       if (cx.getLanguageVersion() == 120) {
/* 1232:1254 */         result = Undefined.instance;
/* 1233:     */       } else {
/* 1234:1256 */         result = cx.newArray(scope, 0);
/* 1235:     */       }
/* 1236:     */     }
/* 1237:1261 */     long delta = argc - count;
/* 1238:1262 */     if ((denseMode) && (length + delta < 2147483647L) && (na.ensureCapacity((int)(length + delta))))
/* 1239:     */     {
/* 1240:1265 */       System.arraycopy(na.dense, (int)end, na.dense, (int)(begin + argc), (int)(length - end));
/* 1241:1267 */       if (argc > 0) {
/* 1242:1268 */         System.arraycopy(args, 2, na.dense, (int)begin, argc);
/* 1243:     */       }
/* 1244:1270 */       if (delta < 0L) {
/* 1245:1271 */         Arrays.fill(na.dense, (int)(length + delta), (int)length, NOT_FOUND);
/* 1246:     */       }
/* 1247:1274 */       na.length = (length + delta);
/* 1248:1275 */       return result;
/* 1249:     */     }
/* 1250:1278 */     if (delta > 0L) {
/* 1251:1279 */       for (long last = length - 1L; last >= end; last -= 1L)
/* 1252:     */       {
/* 1253:1280 */         Object temp = getElem(cx, thisObj, last);
/* 1254:1281 */         setElem(cx, thisObj, last + delta, temp);
/* 1255:     */       }
/* 1256:1283 */     } else if (delta < 0L) {
/* 1257:1284 */       for (long last = end; last < length; last += 1L)
/* 1258:     */       {
/* 1259:1285 */         Object temp = getElem(cx, thisObj, last);
/* 1260:1286 */         setElem(cx, thisObj, last + delta, temp);
/* 1261:     */       }
/* 1262:     */     }
/* 1263:1291 */     int argoffset = args.length - argc;
/* 1264:1292 */     for (int i = 0; i < argc; i++) {
/* 1265:1293 */       setElem(cx, thisObj, begin + i, args[(i + argoffset)]);
/* 1266:     */     }
/* 1267:1297 */     setLengthProperty(cx, thisObj, length + delta);
/* 1268:1298 */     return result;
/* 1269:     */   }
/* 1270:     */   
/* 1271:     */   private static Scriptable js_concat(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 1272:     */   {
/* 1273:1308 */     scope = getTopLevelScope(scope);
/* 1274:1309 */     Function ctor = ScriptRuntime.getExistingCtor(cx, scope, "Array");
/* 1275:1310 */     Scriptable result = ctor.construct(cx, scope, ScriptRuntime.emptyArgs);
/* 1276:1311 */     if (((thisObj instanceof NativeArray)) && ((result instanceof NativeArray)))
/* 1277:     */     {
/* 1278:1312 */       NativeArray denseThis = (NativeArray)thisObj;
/* 1279:1313 */       NativeArray denseResult = (NativeArray)result;
/* 1280:1314 */       if ((denseThis.denseOnly) && (denseResult.denseOnly))
/* 1281:     */       {
/* 1282:1316 */         boolean canUseDense = true;
/* 1283:1317 */         int length = (int)denseThis.length;
/* 1284:1318 */         for (int i = 0; (i < args.length) && (canUseDense); i++) {
/* 1285:1319 */           if ((args[i] instanceof NativeArray))
/* 1286:     */           {
/* 1287:1322 */             NativeArray arg = (NativeArray)args[i];
/* 1288:1323 */             canUseDense = arg.denseOnly;
/* 1289:1324 */             length = (int)(length + arg.length);
/* 1290:     */           }
/* 1291:     */           else
/* 1292:     */           {
/* 1293:1326 */             length++;
/* 1294:     */           }
/* 1295:     */         }
/* 1296:1329 */         if ((canUseDense) && (denseResult.ensureCapacity(length)))
/* 1297:     */         {
/* 1298:1330 */           System.arraycopy(denseThis.dense, 0, denseResult.dense, 0, (int)denseThis.length);
/* 1299:     */           
/* 1300:1332 */           int cursor = (int)denseThis.length;
/* 1301:1333 */           for (int i = 0; (i < args.length) && (canUseDense); i++) {
/* 1302:1334 */             if ((args[i] instanceof NativeArray))
/* 1303:     */             {
/* 1304:1335 */               NativeArray arg = (NativeArray)args[i];
/* 1305:1336 */               System.arraycopy(arg.dense, 0, denseResult.dense, cursor, (int)arg.length);
/* 1306:     */               
/* 1307:     */ 
/* 1308:1339 */               cursor += (int)arg.length;
/* 1309:     */             }
/* 1310:     */             else
/* 1311:     */             {
/* 1312:1341 */               denseResult.dense[(cursor++)] = args[i];
/* 1313:     */             }
/* 1314:     */           }
/* 1315:1344 */           denseResult.length = length;
/* 1316:1345 */           return result;
/* 1317:     */         }
/* 1318:     */       }
/* 1319:     */     }
/* 1320:1351 */     long slot = 0L;
/* 1321:1356 */     if (ScriptRuntime.instanceOf(thisObj, ctor, cx))
/* 1322:     */     {
/* 1323:1357 */       long length = getLengthProperty(cx, thisObj);
/* 1324:1360 */       for (slot = 0L; slot < length; slot += 1L)
/* 1325:     */       {
/* 1326:1361 */         Object temp = getElem(cx, thisObj, slot);
/* 1327:1362 */         setElem(cx, result, slot, temp);
/* 1328:     */       }
/* 1329:     */     }
/* 1330:1365 */     setElem(cx, result, slot++, thisObj);
/* 1331:1372 */     for (int i = 0; i < args.length; i++) {
/* 1332:1373 */       if (ScriptRuntime.instanceOf(args[i], ctor, cx))
/* 1333:     */       {
/* 1334:1375 */         Scriptable arg = (Scriptable)args[i];
/* 1335:1376 */         long length = getLengthProperty(cx, arg);
/* 1336:1377 */         for (long j = 0L; j < length; slot += 1L)
/* 1337:     */         {
/* 1338:1378 */           Object temp = getElem(cx, arg, j);
/* 1339:1379 */           setElem(cx, result, slot, temp);j += 1L;
/* 1340:     */         }
/* 1341:     */       }
/* 1342:     */       else
/* 1343:     */       {
/* 1344:1382 */         setElem(cx, result, slot++, args[i]);
/* 1345:     */       }
/* 1346:     */     }
/* 1347:1385 */     return result;
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   private Scriptable js_slice(Context cx, Scriptable thisObj, Object[] args)
/* 1351:     */   {
/* 1352:1391 */     Scriptable scope = getTopLevelScope(this);
/* 1353:1392 */     Scriptable result = cx.newArray(scope, 0);
/* 1354:1393 */     long length = getLengthProperty(cx, thisObj);
/* 1355:     */     long end;
/* 1356:     */     long begin;
/* 1357:     */     long end;
/* 1358:1396 */     if (args.length == 0)
/* 1359:     */     {
/* 1360:1397 */       long begin = 0L;
/* 1361:1398 */       end = length;
/* 1362:     */     }
/* 1363:     */     else
/* 1364:     */     {
/* 1365:1400 */       begin = toSliceIndex(ScriptRuntime.toInteger(args[0]), length);
/* 1366:     */       long end;
/* 1367:1401 */       if (args.length == 1) {
/* 1368:1402 */         end = length;
/* 1369:     */       } else {
/* 1370:1404 */         end = toSliceIndex(ScriptRuntime.toInteger(args[1]), length);
/* 1371:     */       }
/* 1372:     */     }
/* 1373:1408 */     for (long slot = begin; slot < end; slot += 1L)
/* 1374:     */     {
/* 1375:1409 */       Object temp = getElem(cx, thisObj, slot);
/* 1376:1410 */       setElem(cx, result, slot - begin, temp);
/* 1377:     */     }
/* 1378:1413 */     return result;
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   private static long toSliceIndex(double value, long length)
/* 1382:     */   {
/* 1383:     */     long result;
/* 1384:     */     long result;
/* 1385:1418 */     if (value < 0.0D)
/* 1386:     */     {
/* 1387:     */       long result;
/* 1388:1419 */       if (value + length < 0.0D) {
/* 1389:1420 */         result = 0L;
/* 1390:     */       } else {
/* 1391:1422 */         result = (value + length);
/* 1392:     */       }
/* 1393:     */     }
/* 1394:     */     else
/* 1395:     */     {
/* 1396:     */       long result;
/* 1397:1424 */       if (value > length) {
/* 1398:1425 */         result = length;
/* 1399:     */       } else {
/* 1400:1427 */         result = value;
/* 1401:     */       }
/* 1402:     */     }
/* 1403:1429 */     return result;
/* 1404:     */   }
/* 1405:     */   
/* 1406:     */   private Object indexOfHelper(Context cx, Scriptable thisObj, Object[] args, boolean isLast)
/* 1407:     */   {
/* 1408:1438 */     Object compareTo = args.length > 0 ? args[0] : Undefined.instance;
/* 1409:1439 */     long length = getLengthProperty(cx, thisObj);
/* 1410:     */     long start;
/* 1411:1441 */     if (isLast)
/* 1412:     */     {
/* 1413:     */       long start;
/* 1414:1454 */       if (args.length < 2)
/* 1415:     */       {
/* 1416:1456 */         start = length - 1L;
/* 1417:     */       }
/* 1418:     */       else
/* 1419:     */       {
/* 1420:1458 */         long start = ScriptRuntime.toInt32(ScriptRuntime.toNumber(args[1]));
/* 1421:1459 */         if (start >= length) {
/* 1422:1460 */           start = length - 1L;
/* 1423:1461 */         } else if (start < 0L) {
/* 1424:1462 */           start += length;
/* 1425:     */         }
/* 1426:     */       }
/* 1427:     */     }
/* 1428:     */     else
/* 1429:     */     {
/* 1430:     */       long start;
/* 1431:1478 */       if (args.length < 2)
/* 1432:     */       {
/* 1433:1480 */         start = 0L;
/* 1434:     */       }
/* 1435:     */       else
/* 1436:     */       {
/* 1437:1482 */         start = ScriptRuntime.toInt32(ScriptRuntime.toNumber(args[1]));
/* 1438:1483 */         if (start < 0L)
/* 1439:     */         {
/* 1440:1484 */           start += length;
/* 1441:1485 */           if (start < 0L) {
/* 1442:1486 */             start = 0L;
/* 1443:     */           }
/* 1444:     */         }
/* 1445:     */       }
/* 1446:     */     }
/* 1447:1492 */     if ((thisObj instanceof NativeArray))
/* 1448:     */     {
/* 1449:1493 */       NativeArray na = (NativeArray)thisObj;
/* 1450:1494 */       if (na.denseOnly)
/* 1451:     */       {
/* 1452:1495 */         if (isLast) {
/* 1453:1496 */           for (int i = (int)start; i >= 0; i--) {
/* 1454:1497 */             if ((na.dense[i] != Scriptable.NOT_FOUND) && (ScriptRuntime.shallowEq(na.dense[i], compareTo))) {
/* 1455:1500 */               return Long.valueOf(i);
/* 1456:     */             }
/* 1457:     */           }
/* 1458:     */         } else {
/* 1459:1504 */           for (int i = (int)start; i < length; i++) {
/* 1460:1505 */             if ((na.dense[i] != Scriptable.NOT_FOUND) && (ScriptRuntime.shallowEq(na.dense[i], compareTo))) {
/* 1461:1508 */               return Long.valueOf(i);
/* 1462:     */             }
/* 1463:     */           }
/* 1464:     */         }
/* 1465:1512 */         return NEGATIVE_ONE;
/* 1466:     */       }
/* 1467:     */     }
/* 1468:1515 */     if (isLast) {
/* 1469:1516 */       for (long i = start; i >= 0L; i -= 1L) {
/* 1470:1517 */         if (ScriptRuntime.shallowEq(getElem(cx, thisObj, i), compareTo)) {
/* 1471:1518 */           return Long.valueOf(i);
/* 1472:     */         }
/* 1473:     */       }
/* 1474:     */     } else {
/* 1475:1522 */       for (long i = start; i < length; i += 1L) {
/* 1476:1523 */         if (ScriptRuntime.shallowEq(getElem(cx, thisObj, i), compareTo)) {
/* 1477:1524 */           return Long.valueOf(i);
/* 1478:     */         }
/* 1479:     */       }
/* 1480:     */     }
/* 1481:1528 */     return NEGATIVE_ONE;
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   private Object iterativeMethod(Context cx, int id, Scriptable scope, Scriptable thisObj, Object[] args)
/* 1485:     */   {
/* 1486:1537 */     Object callbackArg = args.length > 0 ? args[0] : Undefined.instance;
/* 1487:1538 */     if ((callbackArg == null) || (!(callbackArg instanceof Function))) {
/* 1488:1539 */       throw ScriptRuntime.notFunctionError(callbackArg);
/* 1489:     */     }
/* 1490:1541 */     Function f = (Function)callbackArg;
/* 1491:1542 */     Scriptable parent = ScriptableObject.getTopLevelScope(f);
/* 1492:     */     Scriptable thisArg;
/* 1493:     */     Scriptable thisArg;
/* 1494:1544 */     if ((args.length < 2) || (args[1] == null) || (args[1] == Undefined.instance)) {
/* 1495:1546 */       thisArg = parent;
/* 1496:     */     } else {
/* 1497:1548 */       thisArg = ScriptRuntime.toObject(cx, scope, args[1]);
/* 1498:     */     }
/* 1499:1550 */     long length = getLengthProperty(cx, thisObj);
/* 1500:1551 */     int resultLength = id == 20 ? (int)length : 0;
/* 1501:1552 */     Scriptable array = cx.newArray(scope, resultLength);
/* 1502:1553 */     long j = 0L;
/* 1503:1554 */     for (long i = 0L; i < length; i += 1L)
/* 1504:     */     {
/* 1505:1555 */       Object[] innerArgs = new Object[3];
/* 1506:1556 */       Object elem = getRawElem(thisObj, i);
/* 1507:1557 */       if (elem != Scriptable.NOT_FOUND)
/* 1508:     */       {
/* 1509:1560 */         innerArgs[0] = elem;
/* 1510:1561 */         innerArgs[1] = Long.valueOf(i);
/* 1511:1562 */         innerArgs[2] = thisObj;
/* 1512:1563 */         Object result = f.call(cx, parent, thisArg, innerArgs);
/* 1513:1564 */         switch (id)
/* 1514:     */         {
/* 1515:     */         case 17: 
/* 1516:1566 */           if (!ScriptRuntime.toBoolean(result)) {
/* 1517:1567 */             return Boolean.FALSE;
/* 1518:     */           }
/* 1519:     */           break;
/* 1520:     */         case 18: 
/* 1521:1570 */           if (ScriptRuntime.toBoolean(result)) {
/* 1522:1571 */             setElem(cx, array, j++, innerArgs[0]);
/* 1523:     */           }
/* 1524:     */           break;
/* 1525:     */         case 19: 
/* 1526:     */           break;
/* 1527:     */         case 20: 
/* 1528:1576 */           setElem(cx, array, i, result);
/* 1529:1577 */           break;
/* 1530:     */         case 21: 
/* 1531:1579 */           if (ScriptRuntime.toBoolean(result)) {
/* 1532:1580 */             return Boolean.TRUE;
/* 1533:     */           }
/* 1534:     */           break;
/* 1535:     */         }
/* 1536:     */       }
/* 1537:     */     }
/* 1538:1584 */     switch (id)
/* 1539:     */     {
/* 1540:     */     case 17: 
/* 1541:1586 */       return Boolean.TRUE;
/* 1542:     */     case 18: 
/* 1543:     */     case 20: 
/* 1544:1589 */       return array;
/* 1545:     */     case 21: 
/* 1546:1591 */       return Boolean.FALSE;
/* 1547:     */     }
/* 1548:1594 */     return Undefined.instance;
/* 1549:     */   }
/* 1550:     */   
/* 1551:     */   private Object reduceMethod(Context cx, int id, Scriptable scope, Scriptable thisObj, Object[] args)
/* 1552:     */   {
/* 1553:1604 */     Object callbackArg = args.length > 0 ? args[0] : Undefined.instance;
/* 1554:1605 */     if ((callbackArg == null) || (!(callbackArg instanceof Function))) {
/* 1555:1606 */       throw ScriptRuntime.notFunctionError(callbackArg);
/* 1556:     */     }
/* 1557:1608 */     Function f = (Function)callbackArg;
/* 1558:1609 */     Scriptable parent = ScriptableObject.getTopLevelScope(f);
/* 1559:1610 */     long length = getLengthProperty(cx, thisObj);
/* 1560:     */     
/* 1561:1612 */     long offset = id == 23 ? length - 1L : 0L;
/* 1562:1613 */     Object value = args.length > 1 ? args[1] : Scriptable.NOT_FOUND;
/* 1563:1614 */     for (long i = 0L; i < length; i += 1L)
/* 1564:     */     {
/* 1565:1615 */       Object elem = getRawElem(thisObj, Math.abs(i - offset));
/* 1566:1616 */       if (elem != Scriptable.NOT_FOUND) {
/* 1567:1619 */         if (value == Scriptable.NOT_FOUND)
/* 1568:     */         {
/* 1569:1621 */           value = elem;
/* 1570:     */         }
/* 1571:     */         else
/* 1572:     */         {
/* 1573:1623 */           Object[] innerArgs = new Object[4];
/* 1574:1624 */           innerArgs[0] = value;
/* 1575:1625 */           innerArgs[1] = elem;
/* 1576:1626 */           innerArgs[2] = Long.valueOf(i);
/* 1577:1627 */           innerArgs[3] = thisObj;
/* 1578:1628 */           value = f.call(cx, parent, parent, innerArgs);
/* 1579:     */         }
/* 1580:     */       }
/* 1581:     */     }
/* 1582:1631 */     if (value == Scriptable.NOT_FOUND) {
/* 1583:1633 */       throw ScriptRuntime.typeError0("msg.empty.array.reduce");
/* 1584:     */     }
/* 1585:1635 */     return value;
/* 1586:     */   }
/* 1587:     */   
/* 1588:     */   public boolean contains(Object o)
/* 1589:     */   {
/* 1590:1641 */     return indexOf(o) > -1;
/* 1591:     */   }
/* 1592:     */   
/* 1593:     */   public Object[] toArray()
/* 1594:     */   {
/* 1595:1645 */     return toArray(ScriptRuntime.emptyArgs);
/* 1596:     */   }
/* 1597:     */   
/* 1598:     */   public Object[] toArray(Object[] a)
/* 1599:     */   {
/* 1600:1649 */     long longLen = this.length;
/* 1601:1650 */     if (longLen > 2147483647L) {
/* 1602:1651 */       throw new IllegalStateException();
/* 1603:     */     }
/* 1604:1653 */     int len = (int)longLen;
/* 1605:1654 */     Object[] array = a.length >= len ? a : (Object[])Array.newInstance(a.getClass().getComponentType(), len);
/* 1606:1657 */     for (int i = 0; i < len; i++) {
/* 1607:1658 */       array[i] = get(i);
/* 1608:     */     }
/* 1609:1660 */     return array;
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   public boolean containsAll(Collection c)
/* 1613:     */   {
/* 1614:1664 */     for (Object aC : c) {
/* 1615:1665 */       if (!contains(aC)) {
/* 1616:1666 */         return false;
/* 1617:     */       }
/* 1618:     */     }
/* 1619:1667 */     return true;
/* 1620:     */   }
/* 1621:     */   
/* 1622:     */   public int size()
/* 1623:     */   {
/* 1624:1671 */     long longLen = this.length;
/* 1625:1672 */     if (longLen > 2147483647L) {
/* 1626:1673 */       throw new IllegalStateException();
/* 1627:     */     }
/* 1628:1675 */     return (int)longLen;
/* 1629:     */   }
/* 1630:     */   
/* 1631:     */   public Object get(long index)
/* 1632:     */   {
/* 1633:1679 */     if ((index < 0L) || (index >= this.length)) {
/* 1634:1680 */       throw new IndexOutOfBoundsException();
/* 1635:     */     }
/* 1636:1682 */     Object value = getRawElem(this, index);
/* 1637:1683 */     if ((value == Scriptable.NOT_FOUND) || (value == Undefined.instance)) {
/* 1638:1684 */       return null;
/* 1639:     */     }
/* 1640:1685 */     if ((value instanceof Wrapper)) {
/* 1641:1686 */       return ((Wrapper)value).unwrap();
/* 1642:     */     }
/* 1643:1688 */     return value;
/* 1644:     */   }
/* 1645:     */   
/* 1646:     */   public Object get(int index)
/* 1647:     */   {
/* 1648:1693 */     return get(index);
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   public int indexOf(Object o)
/* 1652:     */   {
/* 1653:1697 */     long longLen = this.length;
/* 1654:1698 */     if (longLen > 2147483647L) {
/* 1655:1699 */       throw new IllegalStateException();
/* 1656:     */     }
/* 1657:1701 */     int len = (int)longLen;
/* 1658:1702 */     if (o == null) {
/* 1659:1703 */       for (int i = 0; i < len; i++) {
/* 1660:1704 */         if (get(i) == null) {
/* 1661:1705 */           return i;
/* 1662:     */         }
/* 1663:     */       }
/* 1664:     */     } else {
/* 1665:1709 */       for (int i = 0; i < len; i++) {
/* 1666:1710 */         if (o.equals(get(i))) {
/* 1667:1711 */           return i;
/* 1668:     */         }
/* 1669:     */       }
/* 1670:     */     }
/* 1671:1715 */     return -1;
/* 1672:     */   }
/* 1673:     */   
/* 1674:     */   public int lastIndexOf(Object o)
/* 1675:     */   {
/* 1676:1719 */     long longLen = this.length;
/* 1677:1720 */     if (longLen > 2147483647L) {
/* 1678:1721 */       throw new IllegalStateException();
/* 1679:     */     }
/* 1680:1723 */     int len = (int)longLen;
/* 1681:1724 */     if (o == null) {
/* 1682:1725 */       for (int i = len - 1; i >= 0; i--) {
/* 1683:1726 */         if (get(i) == null) {
/* 1684:1727 */           return i;
/* 1685:     */         }
/* 1686:     */       }
/* 1687:     */     } else {
/* 1688:1731 */       for (int i = len - 1; i >= 0; i--) {
/* 1689:1732 */         if (o.equals(get(i))) {
/* 1690:1733 */           return i;
/* 1691:     */         }
/* 1692:     */       }
/* 1693:     */     }
/* 1694:1737 */     return -1;
/* 1695:     */   }
/* 1696:     */   
/* 1697:     */   public Iterator iterator()
/* 1698:     */   {
/* 1699:1741 */     return listIterator(0);
/* 1700:     */   }
/* 1701:     */   
/* 1702:     */   public ListIterator listIterator()
/* 1703:     */   {
/* 1704:1745 */     return listIterator(0);
/* 1705:     */   }
/* 1706:     */   
/* 1707:     */   public ListIterator listIterator(final int start)
/* 1708:     */   {
/* 1709:1749 */     long longLen = this.length;
/* 1710:1750 */     if (longLen > 2147483647L) {
/* 1711:1751 */       throw new IllegalStateException();
/* 1712:     */     }
/* 1713:1753 */     final int len = (int)longLen;
/* 1714:1755 */     if ((start < 0) || (start > len)) {
/* 1715:1756 */       throw new IndexOutOfBoundsException("Index: " + start);
/* 1716:     */     }
/* 1717:1759 */     new ListIterator()
/* 1718:     */     {
/* 1719:1761 */       int cursor = start;
/* 1720:     */       
/* 1721:     */       public boolean hasNext()
/* 1722:     */       {
/* 1723:1764 */         return this.cursor < len;
/* 1724:     */       }
/* 1725:     */       
/* 1726:     */       public Object next()
/* 1727:     */       {
/* 1728:1768 */         if (this.cursor == len) {
/* 1729:1769 */           throw new NoSuchElementException();
/* 1730:     */         }
/* 1731:1771 */         return NativeArray.this.get(this.cursor++);
/* 1732:     */       }
/* 1733:     */       
/* 1734:     */       public boolean hasPrevious()
/* 1735:     */       {
/* 1736:1775 */         return this.cursor > 0;
/* 1737:     */       }
/* 1738:     */       
/* 1739:     */       public Object previous()
/* 1740:     */       {
/* 1741:1779 */         if (this.cursor == 0) {
/* 1742:1780 */           throw new NoSuchElementException();
/* 1743:     */         }
/* 1744:1782 */         return NativeArray.this.get(--this.cursor);
/* 1745:     */       }
/* 1746:     */       
/* 1747:     */       public int nextIndex()
/* 1748:     */       {
/* 1749:1786 */         return this.cursor;
/* 1750:     */       }
/* 1751:     */       
/* 1752:     */       public int previousIndex()
/* 1753:     */       {
/* 1754:1790 */         return this.cursor - 1;
/* 1755:     */       }
/* 1756:     */       
/* 1757:     */       public void remove()
/* 1758:     */       {
/* 1759:1794 */         throw new UnsupportedOperationException();
/* 1760:     */       }
/* 1761:     */       
/* 1762:     */       public void add(Object o)
/* 1763:     */       {
/* 1764:1798 */         throw new UnsupportedOperationException();
/* 1765:     */       }
/* 1766:     */       
/* 1767:     */       public void set(Object o)
/* 1768:     */       {
/* 1769:1802 */         throw new UnsupportedOperationException();
/* 1770:     */       }
/* 1771:     */     };
/* 1772:     */   }
/* 1773:     */   
/* 1774:     */   public boolean add(Object o)
/* 1775:     */   {
/* 1776:1808 */     throw new UnsupportedOperationException();
/* 1777:     */   }
/* 1778:     */   
/* 1779:     */   public boolean remove(Object o)
/* 1780:     */   {
/* 1781:1812 */     throw new UnsupportedOperationException();
/* 1782:     */   }
/* 1783:     */   
/* 1784:     */   public boolean addAll(Collection c)
/* 1785:     */   {
/* 1786:1816 */     throw new UnsupportedOperationException();
/* 1787:     */   }
/* 1788:     */   
/* 1789:     */   public boolean removeAll(Collection c)
/* 1790:     */   {
/* 1791:1820 */     throw new UnsupportedOperationException();
/* 1792:     */   }
/* 1793:     */   
/* 1794:     */   public boolean retainAll(Collection c)
/* 1795:     */   {
/* 1796:1824 */     throw new UnsupportedOperationException();
/* 1797:     */   }
/* 1798:     */   
/* 1799:     */   public void clear()
/* 1800:     */   {
/* 1801:1828 */     throw new UnsupportedOperationException();
/* 1802:     */   }
/* 1803:     */   
/* 1804:     */   public void add(int index, Object element)
/* 1805:     */   {
/* 1806:1832 */     throw new UnsupportedOperationException();
/* 1807:     */   }
/* 1808:     */   
/* 1809:     */   public boolean addAll(int index, Collection c)
/* 1810:     */   {
/* 1811:1836 */     throw new UnsupportedOperationException();
/* 1812:     */   }
/* 1813:     */   
/* 1814:     */   public Object set(int index, Object element)
/* 1815:     */   {
/* 1816:1840 */     throw new UnsupportedOperationException();
/* 1817:     */   }
/* 1818:     */   
/* 1819:     */   public Object remove(int index)
/* 1820:     */   {
/* 1821:1844 */     throw new UnsupportedOperationException();
/* 1822:     */   }
/* 1823:     */   
/* 1824:     */   public List subList(int fromIndex, int toIndex)
/* 1825:     */   {
/* 1826:1848 */     throw new UnsupportedOperationException();
/* 1827:     */   }
/* 1828:     */   
/* 1829:     */   protected int findPrototypeId(String s)
/* 1830:     */   {
/* 1831:1858 */     int id = 0;String X = null;
/* 1832:     */     int c;
/* 1833:1859 */     switch (s.length())
/* 1834:     */     {
/* 1835:     */     case 3: 
/* 1836:1860 */       c = s.charAt(0);
/* 1837:1861 */       if (c == 109)
/* 1838:     */       {
/* 1839:1861 */         if ((s.charAt(2) == 'p') && (s.charAt(1) == 'a'))
/* 1840:     */         {
/* 1841:1861 */           id = 20; return id;
/* 1842:     */         }
/* 1843:     */       }
/* 1844:1862 */       else if ((c == 112) && (s.charAt(2) == 'p') && (s.charAt(1) == 'o')) {
/* 1845:1862 */         id = 9;
/* 1846:     */       }
/* 1847:     */       break;
/* 1848:     */     case 4: 
/* 1849:1864 */       switch (s.charAt(2))
/* 1850:     */       {
/* 1851:     */       case 'i': 
/* 1852:1865 */         X = "join";id = 5; break;
/* 1853:     */       case 'm': 
/* 1854:1866 */         X = "some";id = 21; break;
/* 1855:     */       case 'r': 
/* 1856:1867 */         X = "sort";id = 7; break;
/* 1857:     */       case 's': 
/* 1858:1868 */         X = "push";id = 8;
/* 1859:     */       }
/* 1860:1869 */       break;
/* 1861:     */     case 5: 
/* 1862:1870 */       c = s.charAt(1);
/* 1863:1871 */       if (c == 104)
/* 1864:     */       {
/* 1865:1871 */         X = "shift";id = 10;
/* 1866:     */       }
/* 1867:1872 */       else if (c == 108)
/* 1868:     */       {
/* 1869:1872 */         X = "slice";id = 14;
/* 1870:     */       }
/* 1871:1873 */       else if (c == 118)
/* 1872:     */       {
/* 1873:1873 */         X = "every";id = 17;
/* 1874:     */       }
/* 1875:     */       break;
/* 1876:     */     case 6: 
/* 1877:1875 */       c = s.charAt(0);
/* 1878:1876 */       if (c == 99)
/* 1879:     */       {
/* 1880:1876 */         X = "concat";id = 13;
/* 1881:     */       }
/* 1882:1877 */       else if (c == 102)
/* 1883:     */       {
/* 1884:1877 */         X = "filter";id = 18;
/* 1885:     */       }
/* 1886:1878 */       else if (c == 115)
/* 1887:     */       {
/* 1888:1878 */         X = "splice";id = 12;
/* 1889:     */       }
/* 1890:1879 */       else if (c == 114)
/* 1891:     */       {
/* 1892:1879 */         X = "reduce";id = 22;
/* 1893:     */       }
/* 1894:     */       break;
/* 1895:     */     case 7: 
/* 1896:1881 */       switch (s.charAt(0))
/* 1897:     */       {
/* 1898:     */       case 'f': 
/* 1899:1882 */         X = "forEach";id = 19; break;
/* 1900:     */       case 'i': 
/* 1901:1883 */         X = "indexOf";id = 15; break;
/* 1902:     */       case 'r': 
/* 1903:1884 */         X = "reverse";id = 6; break;
/* 1904:     */       case 'u': 
/* 1905:1885 */         X = "unshift";id = 11;
/* 1906:     */       }
/* 1907:1886 */       break;
/* 1908:     */     case 8: 
/* 1909:1887 */       c = s.charAt(3);
/* 1910:1888 */       if (c == 111)
/* 1911:     */       {
/* 1912:1888 */         X = "toSource";id = 4;
/* 1913:     */       }
/* 1914:1889 */       else if (c == 116)
/* 1915:     */       {
/* 1916:1889 */         X = "toString";id = 2;
/* 1917:     */       }
/* 1918:     */       break;
/* 1919:     */     case 11: 
/* 1920:1891 */       c = s.charAt(0);
/* 1921:1892 */       if (c == 99)
/* 1922:     */       {
/* 1923:1892 */         X = "constructor";id = 1;
/* 1924:     */       }
/* 1925:1893 */       else if (c == 108)
/* 1926:     */       {
/* 1927:1893 */         X = "lastIndexOf";id = 16;
/* 1928:     */       }
/* 1929:1894 */       else if (c == 114)
/* 1930:     */       {
/* 1931:1894 */         X = "reduceRight";id = 23;
/* 1932:     */       }
/* 1933:     */       break;
/* 1934:     */     case 14: 
/* 1935:1896 */       X = "toLocaleString";id = 3; break;
/* 1936:     */     }
/* 1937:1898 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 1938:1898 */       id = 0;
/* 1939:     */     }
/* 1940:1901 */     return id;
/* 1941:     */   }
/* 1942:     */   
/* 1943:1974 */   private static int maximumInitialCapacity = 10000;
/* 1944:     */   private static final int DEFAULT_INITIAL_CAPACITY = 10;
/* 1945:     */   private static final double GROW_FACTOR = 1.5D;
/* 1946:     */   private static final int MAX_PRE_GROW_SIZE = 1431655764;
/* 1947:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeArray
 * JD-Core Version:    0.7.0.1
 */