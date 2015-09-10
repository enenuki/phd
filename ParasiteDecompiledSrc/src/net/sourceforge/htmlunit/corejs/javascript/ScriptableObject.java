/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.ObjectOutputStream;
/*    6:     */ import java.io.Serializable;
/*    7:     */ import java.lang.annotation.Annotation;
/*    8:     */ import java.lang.reflect.AccessibleObject;
/*    9:     */ import java.lang.reflect.Constructor;
/*   10:     */ import java.lang.reflect.InvocationTargetException;
/*   11:     */ import java.lang.reflect.Member;
/*   12:     */ import java.lang.reflect.Method;
/*   13:     */ import java.lang.reflect.Modifier;
/*   14:     */ import java.util.HashMap;
/*   15:     */ import java.util.HashSet;
/*   16:     */ import java.util.Map;
/*   17:     */ import net.sourceforge.htmlunit.corejs.javascript.annotations.JSConstructor;
/*   18:     */ import net.sourceforge.htmlunit.corejs.javascript.annotations.JSFunction;
/*   19:     */ import net.sourceforge.htmlunit.corejs.javascript.annotations.JSGetter;
/*   20:     */ import net.sourceforge.htmlunit.corejs.javascript.annotations.JSSetter;
/*   21:     */ import net.sourceforge.htmlunit.corejs.javascript.annotations.JSStaticFunction;
/*   22:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableObject;
/*   23:     */ 
/*   24:     */ public abstract class ScriptableObject
/*   25:     */   implements Scriptable, Serializable, DebuggableObject, ConstProperties
/*   26:     */ {
/*   27:     */   public static final int EMPTY = 0;
/*   28:     */   public static final int READONLY = 1;
/*   29:     */   public static final int DONTENUM = 2;
/*   30:     */   public static final int PERMANENT = 4;
/*   31:     */   public static final int UNINITIALIZED_CONST = 8;
/*   32:     */   public static final int CONST = 13;
/*   33:     */   private Scriptable prototypeObject;
/*   34:     */   private Scriptable parentScopeObject;
/*   35:     */   private transient Slot[] slots;
/*   36:     */   private int count;
/*   37:     */   private transient Slot firstAdded;
/*   38:     */   private transient Slot lastAdded;
/*   39:     */   private volatile Map<Object, Object> associatedValues;
/*   40:     */   private static final int SLOT_QUERY = 1;
/*   41:     */   private static final int SLOT_MODIFY = 2;
/*   42:     */   private static final int SLOT_MODIFY_CONST = 3;
/*   43:     */   private static final int SLOT_MODIFY_GETTER_SETTER = 4;
/*   44:     */   private static final int SLOT_CONVERT_ACCESSOR_TO_DATA = 5;
/*   45:     */   private static final int INITIAL_SLOT_SIZE = 4;
/*   46: 170 */   private boolean isExtensible = true;
/*   47:     */   
/*   48:     */   private static class Slot
/*   49:     */     implements Serializable
/*   50:     */   {
/*   51:     */     private static final long serialVersionUID = -6090581677123995491L;
/*   52:     */     String name;
/*   53:     */     int indexOrHash;
/*   54:     */     private volatile short attributes;
/*   55:     */     volatile transient boolean wasDeleted;
/*   56:     */     volatile Object value;
/*   57:     */     volatile transient Slot next;
/*   58:     */     volatile transient Slot orderedNext;
/*   59:     */     
/*   60:     */     Slot(String name, int indexOrHash, int attributes)
/*   61:     */     {
/*   62: 185 */       this.name = name;
/*   63: 186 */       this.indexOrHash = indexOrHash;
/*   64: 187 */       this.attributes = ((short)attributes);
/*   65:     */     }
/*   66:     */     
/*   67:     */     private void readObject(ObjectInputStream in)
/*   68:     */       throws IOException, ClassNotFoundException
/*   69:     */     {
/*   70: 193 */       in.defaultReadObject();
/*   71: 194 */       if (this.name != null) {
/*   72: 195 */         this.indexOrHash = this.name.hashCode();
/*   73:     */       }
/*   74:     */     }
/*   75:     */     
/*   76:     */     boolean setValue(Object value, Scriptable owner, Scriptable start)
/*   77:     */     {
/*   78: 200 */       if ((this.attributes & 0x1) != 0) {
/*   79: 201 */         return true;
/*   80:     */       }
/*   81: 203 */       if (owner == start)
/*   82:     */       {
/*   83: 204 */         this.value = value;
/*   84: 205 */         return true;
/*   85:     */       }
/*   86: 207 */       return false;
/*   87:     */     }
/*   88:     */     
/*   89:     */     Object getValue(Scriptable start)
/*   90:     */     {
/*   91: 212 */       return this.value;
/*   92:     */     }
/*   93:     */     
/*   94:     */     final int getAttributes()
/*   95:     */     {
/*   96: 217 */       return this.attributes;
/*   97:     */     }
/*   98:     */     
/*   99:     */     final synchronized void setAttributes(int value)
/*  100:     */     {
/*  101: 222 */       ScriptableObject.checkValidAttributes(value);
/*  102: 223 */       this.attributes = ((short)value);
/*  103:     */     }
/*  104:     */     
/*  105:     */     final void checkNotReadonly()
/*  106:     */     {
/*  107: 228 */       if ((this.attributes & 0x1) != 0)
/*  108:     */       {
/*  109: 229 */         String str = this.name != null ? this.name : Integer.toString(this.indexOrHash);
/*  110:     */         
/*  111: 231 */         throw Context.reportRuntimeError1("msg.modify.readonly", str);
/*  112:     */       }
/*  113:     */     }
/*  114:     */     
/*  115:     */     ScriptableObject getPropertyDescriptor(Context cx, Scriptable scope)
/*  116:     */     {
/*  117: 236 */       return ScriptableObject.buildDataDescriptor(scope, this.value == null ? Undefined.instance : this.value, this.attributes);
/*  118:     */     }
/*  119:     */   }
/*  120:     */   
/*  121:     */   protected static ScriptableObject buildDataDescriptor(Scriptable scope, Object value, int attributes)
/*  122:     */   {
/*  123: 245 */     ScriptableObject desc = new NativeObject();
/*  124: 246 */     ScriptRuntime.setBuiltinProtoAndParent(desc, scope, TopLevel.Builtins.Object);
/*  125:     */     
/*  126: 248 */     desc.defineProperty("value", value, 0);
/*  127: 249 */     desc.defineProperty("writable", Boolean.valueOf((attributes & 0x1) == 0), 0);
/*  128: 250 */     desc.defineProperty("enumerable", Boolean.valueOf((attributes & 0x2) == 0), 0);
/*  129: 251 */     desc.defineProperty("configurable", Boolean.valueOf((attributes & 0x4) == 0), 0);
/*  130: 252 */     return desc;
/*  131:     */   }
/*  132:     */   
/*  133:     */   private static final class GetterSlot
/*  134:     */     extends ScriptableObject.Slot
/*  135:     */   {
/*  136:     */     static final long serialVersionUID = -4900574849788797588L;
/*  137:     */     Object getter;
/*  138:     */     Object setter;
/*  139:     */     
/*  140:     */     GetterSlot(String name, int indexOrHash, int attributes)
/*  141:     */     {
/*  142: 264 */       super(indexOrHash, attributes);
/*  143:     */     }
/*  144:     */     
/*  145:     */     ScriptableObject getPropertyDescriptor(Context cx, Scriptable parent)
/*  146:     */     {
/*  147: 269 */       ScriptableObject desc = super.getPropertyDescriptor(cx, parent);
/*  148: 270 */       desc.delete("value");
/*  149: 271 */       desc.delete("writable");
/*  150: 272 */       if (this.getter != null) {
/*  151: 272 */         desc.defineProperty("get", this.getter, 0);
/*  152:     */       }
/*  153: 273 */       if (this.setter != null) {
/*  154: 273 */         desc.defineProperty("set", this.setter, 0);
/*  155:     */       }
/*  156: 274 */       return desc;
/*  157:     */     }
/*  158:     */     
/*  159:     */     boolean setValue(Object value, Scriptable owner, Scriptable start)
/*  160:     */     {
/*  161: 279 */       if (this.setter == null)
/*  162:     */       {
/*  163: 280 */         if (this.getter != null)
/*  164:     */         {
/*  165: 281 */           if (Context.getContext().hasFeature(11)) {
/*  166: 284 */             throw ScriptRuntime.typeError3("msg.set.prop.no.setter", this.name, start.getClassName(), Context.toString(value));
/*  167:     */           }
/*  168: 286 */           if (Context.getContext().hasFeature(14)) {
/*  169: 289 */             this.getter = null;
/*  170:     */           } else {
/*  171: 294 */             throw ScriptRuntime.typeError3("msg.set.prop.no.setter", this.name, start.getClassName(), Context.toString(value));
/*  172:     */           }
/*  173:     */         }
/*  174:     */       }
/*  175:     */       else
/*  176:     */       {
/*  177: 298 */         Context cx = Context.getContext();
/*  178: 299 */         if ((this.setter instanceof MemberBox))
/*  179:     */         {
/*  180: 300 */           MemberBox nativeSetter = (MemberBox)this.setter;
/*  181: 301 */           Class<?>[] pTypes = nativeSetter.argTypes;
/*  182:     */           
/*  183:     */ 
/*  184: 304 */           Class<?> valueType = pTypes[(pTypes.length - 1)];
/*  185: 305 */           int tag = FunctionObject.getTypeTag(valueType);
/*  186: 306 */           Object actualArg = FunctionObject.convertArg(cx, start, value, tag);
/*  187:     */           Object[] args;
/*  188:     */           Object setterThis;
/*  189:     */           Object[] args;
/*  190: 310 */           if (nativeSetter.delegateTo == null)
/*  191:     */           {
/*  192: 311 */             Object setterThis = start;
/*  193: 312 */             args = new Object[] { actualArg };
/*  194:     */           }
/*  195:     */           else
/*  196:     */           {
/*  197: 314 */             setterThis = nativeSetter.delegateTo;
/*  198: 315 */             args = new Object[] { start, actualArg };
/*  199:     */           }
/*  200: 317 */           nativeSetter.invoke(setterThis, args);
/*  201:     */         }
/*  202:     */         else
/*  203:     */         {
/*  204: 319 */           Function f = (Function)this.setter;
/*  205: 320 */           f.call(cx, f.getParentScope(), start, new Object[] { value });
/*  206:     */         }
/*  207: 323 */         return true;
/*  208:     */       }
/*  209: 325 */       return super.setValue(value, owner, start);
/*  210:     */     }
/*  211:     */     
/*  212:     */     Object getValue(Scriptable start)
/*  213:     */     {
/*  214: 330 */       if (this.getter != null)
/*  215:     */       {
/*  216: 331 */         if ((this.getter instanceof MemberBox))
/*  217:     */         {
/*  218: 332 */           MemberBox nativeGetter = (MemberBox)this.getter;
/*  219:     */           Object[] args;
/*  220:     */           Object getterThis;
/*  221:     */           Object[] args;
/*  222: 335 */           if (nativeGetter.delegateTo == null)
/*  223:     */           {
/*  224: 336 */             Object getterThis = start;
/*  225: 337 */             args = ScriptRuntime.emptyArgs;
/*  226:     */           }
/*  227:     */           else
/*  228:     */           {
/*  229: 339 */             getterThis = nativeGetter.delegateTo;
/*  230: 340 */             args = new Object[] { start };
/*  231:     */           }
/*  232: 342 */           return nativeGetter.invoke(getterThis, args);
/*  233:     */         }
/*  234: 344 */         Function f = (Function)this.getter;
/*  235: 345 */         Context cx = Context.getContext();
/*  236: 346 */         return f.call(cx, f.getParentScope(), start, ScriptRuntime.emptyArgs);
/*  237:     */       }
/*  238: 350 */       if ((this.value instanceof LazilyLoadedCtor))
/*  239:     */       {
/*  240: 351 */         LazilyLoadedCtor initializer = (LazilyLoadedCtor)this.value;
/*  241:     */         try
/*  242:     */         {
/*  243: 353 */           initializer.init();
/*  244:     */         }
/*  245:     */         finally
/*  246:     */         {
/*  247: 355 */           this.value = initializer.getValue();
/*  248:     */         }
/*  249:     */       }
/*  250: 358 */       return this.value;
/*  251:     */     }
/*  252:     */   }
/*  253:     */   
/*  254:     */   static void checkValidAttributes(int attributes)
/*  255:     */   {
/*  256: 365 */     int mask = 15;
/*  257: 366 */     if ((attributes & 0xFFFFFFF0) != 0) {
/*  258: 367 */       throw new IllegalArgumentException(String.valueOf(attributes));
/*  259:     */     }
/*  260:     */   }
/*  261:     */   
/*  262:     */   public ScriptableObject() {}
/*  263:     */   
/*  264:     */   public ScriptableObject(Scriptable scope, Scriptable prototype)
/*  265:     */   {
/*  266: 377 */     if (scope == null) {
/*  267: 378 */       throw new IllegalArgumentException();
/*  268:     */     }
/*  269: 380 */     this.parentScopeObject = scope;
/*  270: 381 */     this.prototypeObject = prototype;
/*  271:     */   }
/*  272:     */   
/*  273:     */   public String getTypeOf()
/*  274:     */   {
/*  275: 390 */     return avoidObjectDetection() ? "undefined" : "object";
/*  276:     */   }
/*  277:     */   
/*  278:     */   public abstract String getClassName();
/*  279:     */   
/*  280:     */   public boolean has(String name, Scriptable start)
/*  281:     */   {
/*  282: 411 */     return null != getSlot(name, 0, 1);
/*  283:     */   }
/*  284:     */   
/*  285:     */   public boolean has(int index, Scriptable start)
/*  286:     */   {
/*  287: 423 */     return null != getSlot(null, index, 1);
/*  288:     */   }
/*  289:     */   
/*  290:     */   public Object get(String name, Scriptable start)
/*  291:     */   {
/*  292: 438 */     return getImpl(name, 0, start);
/*  293:     */   }
/*  294:     */   
/*  295:     */   public Object get(int index, Scriptable start)
/*  296:     */   {
/*  297: 450 */     return getImpl(null, index, start);
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void put(String name, Scriptable start, Object value)
/*  301:     */   {
/*  302: 470 */     if (putImpl(name, 0, start, value, 0)) {
/*  303: 471 */       return;
/*  304:     */     }
/*  305: 473 */     if (start == this) {
/*  306: 473 */       throw Kit.codeBug();
/*  307:     */     }
/*  308: 474 */     start.put(name, start, value);
/*  309:     */   }
/*  310:     */   
/*  311:     */   public void put(int index, Scriptable start, Object value)
/*  312:     */   {
/*  313: 486 */     if (putImpl(null, index, start, value, 0)) {
/*  314: 487 */       return;
/*  315:     */     }
/*  316: 489 */     if (start == this) {
/*  317: 489 */       throw Kit.codeBug();
/*  318:     */     }
/*  319: 490 */     start.put(index, start, value);
/*  320:     */   }
/*  321:     */   
/*  322:     */   public void delete(String name)
/*  323:     */   {
/*  324: 503 */     checkNotSealed(name, 0);
/*  325: 504 */     removeSlot(name, 0);
/*  326:     */   }
/*  327:     */   
/*  328:     */   public void delete(int index)
/*  329:     */   {
/*  330: 517 */     checkNotSealed(null, index);
/*  331: 518 */     removeSlot(null, index);
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void putConst(String name, Scriptable start, Object value)
/*  335:     */   {
/*  336: 538 */     if (putImpl(name, 0, start, value, 1)) {
/*  337: 539 */       return;
/*  338:     */     }
/*  339: 541 */     if (start == this) {
/*  340: 541 */       throw Kit.codeBug();
/*  341:     */     }
/*  342: 542 */     if ((start instanceof ConstProperties)) {
/*  343: 543 */       ((ConstProperties)start).putConst(name, start, value);
/*  344:     */     } else {
/*  345: 545 */       start.put(name, start, value);
/*  346:     */     }
/*  347:     */   }
/*  348:     */   
/*  349:     */   public void defineConst(String name, Scriptable start)
/*  350:     */   {
/*  351: 550 */     if (putImpl(name, 0, start, Undefined.instance, 8)) {
/*  352: 551 */       return;
/*  353:     */     }
/*  354: 553 */     if (start == this) {
/*  355: 553 */       throw Kit.codeBug();
/*  356:     */     }
/*  357: 554 */     if ((start instanceof ConstProperties)) {
/*  358: 555 */       ((ConstProperties)start).defineConst(name, start);
/*  359:     */     }
/*  360:     */   }
/*  361:     */   
/*  362:     */   public boolean isConst(String name)
/*  363:     */   {
/*  364: 565 */     Slot slot = getSlot(name, 0, 1);
/*  365: 566 */     if (slot == null) {
/*  366: 567 */       return false;
/*  367:     */     }
/*  368: 569 */     return (slot.getAttributes() & 0x5) == 5;
/*  369:     */   }
/*  370:     */   
/*  371:     */   /**
/*  372:     */    * @deprecated
/*  373:     */    */
/*  374:     */   public final int getAttributes(String name, Scriptable start)
/*  375:     */   {
/*  376: 579 */     return getAttributes(name);
/*  377:     */   }
/*  378:     */   
/*  379:     */   /**
/*  380:     */    * @deprecated
/*  381:     */    */
/*  382:     */   public final int getAttributes(int index, Scriptable start)
/*  383:     */   {
/*  384: 588 */     return getAttributes(index);
/*  385:     */   }
/*  386:     */   
/*  387:     */   /**
/*  388:     */    * @deprecated
/*  389:     */    */
/*  390:     */   public final void setAttributes(String name, Scriptable start, int attributes)
/*  391:     */   {
/*  392: 598 */     setAttributes(name, attributes);
/*  393:     */   }
/*  394:     */   
/*  395:     */   /**
/*  396:     */    * @deprecated
/*  397:     */    */
/*  398:     */   public void setAttributes(int index, Scriptable start, int attributes)
/*  399:     */   {
/*  400: 608 */     setAttributes(index, attributes);
/*  401:     */   }
/*  402:     */   
/*  403:     */   public int getAttributes(String name)
/*  404:     */   {
/*  405: 628 */     return findAttributeSlot(name, 0, 1).getAttributes();
/*  406:     */   }
/*  407:     */   
/*  408:     */   public int getAttributes(int index)
/*  409:     */   {
/*  410: 646 */     return findAttributeSlot(null, index, 1).getAttributes();
/*  411:     */   }
/*  412:     */   
/*  413:     */   public void setAttributes(String name, int attributes)
/*  414:     */   {
/*  415: 672 */     checkNotSealed(name, 0);
/*  416: 673 */     findAttributeSlot(name, 0, 2).setAttributes(attributes);
/*  417:     */   }
/*  418:     */   
/*  419:     */   public void setAttributes(int index, int attributes)
/*  420:     */   {
/*  421: 690 */     checkNotSealed(null, index);
/*  422: 691 */     findAttributeSlot(null, index, 2).setAttributes(attributes);
/*  423:     */   }
/*  424:     */   
/*  425:     */   public void setGetterOrSetter(String name, int index, Callable getterOrSetter, boolean isSetter)
/*  426:     */   {
/*  427: 700 */     setGetterOrSetter(name, index, getterOrSetter, isSetter, false);
/*  428:     */   }
/*  429:     */   
/*  430:     */   private void setGetterOrSetter(String name, int index, Callable getterOrSetter, boolean isSetter, boolean force)
/*  431:     */   {
/*  432: 705 */     if ((name != null) && (index != 0)) {
/*  433: 706 */       throw new IllegalArgumentException(name);
/*  434:     */     }
/*  435: 708 */     if (!force) {
/*  436: 709 */       checkNotSealed(name, index);
/*  437:     */     }
/*  438:     */     GetterSlot gslot;
/*  439:     */     GetterSlot gslot;
/*  440: 713 */     if (isExtensible())
/*  441:     */     {
/*  442: 714 */       gslot = (GetterSlot)getSlot(name, index, 4);
/*  443:     */     }
/*  444:     */     else
/*  445:     */     {
/*  446: 716 */       gslot = (GetterSlot)getSlot(name, index, 1);
/*  447: 717 */       if (gslot == null) {
/*  448: 718 */         return;
/*  449:     */       }
/*  450:     */     }
/*  451: 721 */     if (!force) {
/*  452: 722 */       gslot.checkNotReadonly();
/*  453:     */     }
/*  454: 724 */     if (isSetter) {
/*  455: 725 */       gslot.setter = getterOrSetter;
/*  456:     */     } else {
/*  457: 727 */       gslot.getter = getterOrSetter;
/*  458:     */     }
/*  459: 729 */     gslot.value = Undefined.instance;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public Object getGetterOrSetter(String name, int index, boolean isSetter)
/*  463:     */   {
/*  464: 747 */     if ((name != null) && (index != 0)) {
/*  465: 748 */       throw new IllegalArgumentException(name);
/*  466:     */     }
/*  467: 749 */     Slot slot = getSlot(name, index, 1);
/*  468: 750 */     if (slot == null) {
/*  469: 751 */       return null;
/*  470:     */     }
/*  471: 752 */     if ((slot instanceof GetterSlot))
/*  472:     */     {
/*  473: 753 */       GetterSlot gslot = (GetterSlot)slot;
/*  474: 754 */       Object result = isSetter ? gslot.setter : gslot.getter;
/*  475: 755 */       return result != null ? result : Undefined.instance;
/*  476:     */     }
/*  477: 757 */     return Undefined.instance;
/*  478:     */   }
/*  479:     */   
/*  480:     */   protected boolean isGetterOrSetter(String name, int index, boolean setter)
/*  481:     */   {
/*  482: 768 */     Slot slot = getSlot(name, index, 1);
/*  483: 769 */     if ((slot instanceof GetterSlot))
/*  484:     */     {
/*  485: 770 */       if ((setter) && (((GetterSlot)slot).setter != null)) {
/*  486: 770 */         return true;
/*  487:     */       }
/*  488: 771 */       if ((!setter) && (((GetterSlot)slot).getter != null)) {
/*  489: 771 */         return true;
/*  490:     */       }
/*  491:     */     }
/*  492: 773 */     return false;
/*  493:     */   }
/*  494:     */   
/*  495:     */   void addLazilyInitializedValue(String name, int index, LazilyLoadedCtor init, int attributes)
/*  496:     */   {
/*  497: 779 */     if ((name != null) && (index != 0)) {
/*  498: 780 */       throw new IllegalArgumentException(name);
/*  499:     */     }
/*  500: 781 */     checkNotSealed(name, index);
/*  501: 782 */     GetterSlot gslot = (GetterSlot)getSlot(name, index, 4);
/*  502:     */     
/*  503: 784 */     gslot.setAttributes(attributes);
/*  504: 785 */     gslot.getter = null;
/*  505: 786 */     gslot.setter = null;
/*  506: 787 */     gslot.value = init;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public Scriptable getPrototype()
/*  510:     */   {
/*  511: 795 */     return this.prototypeObject;
/*  512:     */   }
/*  513:     */   
/*  514:     */   public void setPrototype(Scriptable m)
/*  515:     */   {
/*  516: 803 */     this.prototypeObject = m;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public Scriptable getParentScope()
/*  520:     */   {
/*  521: 811 */     return this.parentScopeObject;
/*  522:     */   }
/*  523:     */   
/*  524:     */   public void setParentScope(Scriptable m)
/*  525:     */   {
/*  526: 819 */     this.parentScopeObject = m;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public Object[] getIds()
/*  530:     */   {
/*  531: 834 */     return getIds(false);
/*  532:     */   }
/*  533:     */   
/*  534:     */   public Object[] getAllIds()
/*  535:     */   {
/*  536: 849 */     return getIds(true);
/*  537:     */   }
/*  538:     */   
/*  539:     */   public Object getDefaultValue(Class<?> typeHint)
/*  540:     */   {
/*  541: 868 */     return getDefaultValue(this, typeHint);
/*  542:     */   }
/*  543:     */   
/*  544:     */   public static Object getDefaultValue(Scriptable object, Class<?> typeHint)
/*  545:     */   {
/*  546: 873 */     Context cx = null;
/*  547: 874 */     for (int i = 0; i < 2; i++)
/*  548:     */     {
/*  549:     */       boolean tryToString;
/*  550:     */       boolean tryToString;
/*  551: 876 */       if (typeHint == ScriptRuntime.StringClass) {
/*  552: 877 */         tryToString = i == 0;
/*  553:     */       } else {
/*  554: 879 */         tryToString = i == 1;
/*  555:     */       }
/*  556:     */       Object[] args;
/*  557:     */       String methodName;
/*  558:     */       Object[] args;
/*  559: 884 */       if (tryToString)
/*  560:     */       {
/*  561: 885 */         String methodName = "toString";
/*  562: 886 */         args = ScriptRuntime.emptyArgs;
/*  563:     */       }
/*  564:     */       else
/*  565:     */       {
/*  566: 888 */         methodName = "valueOf";
/*  567: 889 */         args = new Object[1];
/*  568:     */         String hint;
/*  569: 891 */         if (typeHint == null)
/*  570:     */         {
/*  571: 892 */           hint = "undefined";
/*  572:     */         }
/*  573:     */         else
/*  574:     */         {
/*  575:     */           String hint;
/*  576: 893 */           if (typeHint == ScriptRuntime.StringClass)
/*  577:     */           {
/*  578: 894 */             hint = "string";
/*  579:     */           }
/*  580:     */           else
/*  581:     */           {
/*  582:     */             String hint;
/*  583: 895 */             if (typeHint == ScriptRuntime.ScriptableClass)
/*  584:     */             {
/*  585: 896 */               hint = "object";
/*  586:     */             }
/*  587:     */             else
/*  588:     */             {
/*  589:     */               String hint;
/*  590: 897 */               if (typeHint == ScriptRuntime.FunctionClass)
/*  591:     */               {
/*  592: 898 */                 hint = "function";
/*  593:     */               }
/*  594:     */               else
/*  595:     */               {
/*  596:     */                 String hint;
/*  597: 899 */                 if ((typeHint == ScriptRuntime.BooleanClass) || (typeHint == Boolean.TYPE))
/*  598:     */                 {
/*  599: 902 */                   hint = "boolean";
/*  600:     */                 }
/*  601:     */                 else
/*  602:     */                 {
/*  603:     */                   String hint;
/*  604: 903 */                   if ((typeHint == ScriptRuntime.NumberClass) || (typeHint == ScriptRuntime.ByteClass) || (typeHint == Byte.TYPE) || (typeHint == ScriptRuntime.ShortClass) || (typeHint == Short.TYPE) || (typeHint == ScriptRuntime.IntegerClass) || (typeHint == Integer.TYPE) || (typeHint == ScriptRuntime.FloatClass) || (typeHint == Float.TYPE) || (typeHint == ScriptRuntime.DoubleClass) || (typeHint == Double.TYPE)) {
/*  605: 915 */                     hint = "number";
/*  606:     */                   } else {
/*  607: 917 */                     throw Context.reportRuntimeError1("msg.invalid.type", typeHint.toString());
/*  608:     */                   }
/*  609:     */                 }
/*  610:     */               }
/*  611:     */             }
/*  612:     */           }
/*  613:     */         }
/*  614:     */         String hint;
/*  615: 920 */         args[0] = hint;
/*  616:     */       }
/*  617: 922 */       Object v = getProperty(object, methodName);
/*  618: 923 */       if ((v instanceof Function))
/*  619:     */       {
/*  620: 925 */         Function fun = (Function)v;
/*  621: 926 */         if (cx == null) {
/*  622: 927 */           cx = Context.getContext();
/*  623:     */         }
/*  624: 928 */         v = fun.call(cx, fun.getParentScope(), object, args);
/*  625: 929 */         if (v != null)
/*  626:     */         {
/*  627: 930 */           if (!(v instanceof Scriptable)) {
/*  628: 931 */             return v;
/*  629:     */           }
/*  630: 933 */           if ((typeHint == ScriptRuntime.ScriptableClass) || (typeHint == ScriptRuntime.FunctionClass)) {
/*  631: 936 */             return v;
/*  632:     */           }
/*  633: 938 */           if ((tryToString) && ((v instanceof Wrapper)))
/*  634:     */           {
/*  635: 941 */             Object u = ((Wrapper)v).unwrap();
/*  636: 942 */             if ((u instanceof String)) {
/*  637: 943 */               return u;
/*  638:     */             }
/*  639:     */           }
/*  640:     */         }
/*  641:     */       }
/*  642:     */     }
/*  643: 948 */     String arg = typeHint == null ? "undefined" : typeHint.getName();
/*  644: 949 */     throw ScriptRuntime.typeError1("msg.default.value", arg);
/*  645:     */   }
/*  646:     */   
/*  647:     */   public boolean hasInstance(Scriptable instance)
/*  648:     */   {
/*  649: 967 */     return ScriptRuntime.jsDelegatesTo(instance, this);
/*  650:     */   }
/*  651:     */   
/*  652:     */   public boolean avoidObjectDetection()
/*  653:     */   {
/*  654: 982 */     return false;
/*  655:     */   }
/*  656:     */   
/*  657:     */   protected Object equivalentValues(Object value)
/*  658:     */   {
/*  659:1000 */     return this == value ? Boolean.TRUE : Scriptable.NOT_FOUND;
/*  660:     */   }
/*  661:     */   
/*  662:     */   public static <T extends Scriptable> void defineClass(Scriptable scope, Class<T> clazz)
/*  663:     */     throws IllegalAccessException, InstantiationException, InvocationTargetException
/*  664:     */   {
/*  665:1101 */     defineClass(scope, clazz, false, false);
/*  666:     */   }
/*  667:     */   
/*  668:     */   public static <T extends Scriptable> void defineClass(Scriptable scope, Class<T> clazz, boolean sealed)
/*  669:     */     throws IllegalAccessException, InstantiationException, InvocationTargetException
/*  670:     */   {
/*  671:1132 */     defineClass(scope, clazz, sealed, false);
/*  672:     */   }
/*  673:     */   
/*  674:     */   public static <T extends Scriptable> String defineClass(Scriptable scope, Class<T> clazz, boolean sealed, boolean mapInheritance)
/*  675:     */     throws IllegalAccessException, InstantiationException, InvocationTargetException
/*  676:     */   {
/*  677:1168 */     BaseFunction ctor = buildClassCtor(scope, clazz, sealed, mapInheritance);
/*  678:1170 */     if (ctor == null) {
/*  679:1171 */       return null;
/*  680:     */     }
/*  681:1172 */     String name = ctor.getClassPrototype().getClassName();
/*  682:1173 */     defineProperty(scope, name, ctor, 2);
/*  683:1174 */     return name;
/*  684:     */   }
/*  685:     */   
/*  686:     */   static <T extends Scriptable> BaseFunction buildClassCtor(Scriptable scope, Class<T> clazz, boolean sealed, boolean mapInheritance)
/*  687:     */     throws IllegalAccessException, InstantiationException, InvocationTargetException
/*  688:     */   {
/*  689:1184 */     Method[] methods = FunctionObject.getMethodList(clazz);
/*  690:1185 */     for (int i = 0; i < methods.length; i++)
/*  691:     */     {
/*  692:1186 */       Method method = methods[i];
/*  693:1187 */       if (method.getName().equals("init"))
/*  694:     */       {
/*  695:1189 */         Class<?>[] parmTypes = method.getParameterTypes();
/*  696:1190 */         if ((parmTypes.length == 3) && (parmTypes[0] == ScriptRuntime.ContextClass) && (parmTypes[1] == ScriptRuntime.ScriptableClass) && (parmTypes[2] == Boolean.TYPE) && (Modifier.isStatic(method.getModifiers())))
/*  697:     */         {
/*  698:1196 */           Object[] args = { Context.getContext(), scope, sealed ? Boolean.TRUE : Boolean.FALSE };
/*  699:     */           
/*  700:1198 */           method.invoke(null, args);
/*  701:1199 */           return null;
/*  702:     */         }
/*  703:1201 */         if ((parmTypes.length == 1) && (parmTypes[0] == ScriptRuntime.ScriptableClass) && (Modifier.isStatic(method.getModifiers())))
/*  704:     */         {
/*  705:1205 */           Object[] args = { scope };
/*  706:1206 */           method.invoke(null, args);
/*  707:1207 */           return null;
/*  708:     */         }
/*  709:     */       }
/*  710:     */     }
/*  711:1215 */     Constructor<?>[] ctors = clazz.getConstructors();
/*  712:1216 */     Constructor<?> protoCtor = null;
/*  713:1217 */     for (int i = 0; i < ctors.length; i++) {
/*  714:1218 */       if (ctors[i].getParameterTypes().length == 0)
/*  715:     */       {
/*  716:1219 */         protoCtor = ctors[i];
/*  717:1220 */         break;
/*  718:     */       }
/*  719:     */     }
/*  720:1223 */     if (protoCtor == null) {
/*  721:1224 */       throw Context.reportRuntimeError1("msg.zero.arg.ctor", clazz.getName());
/*  722:     */     }
/*  723:1228 */     Scriptable proto = (Scriptable)protoCtor.newInstance(ScriptRuntime.emptyArgs);
/*  724:1229 */     String className = proto.getClassName();
/*  725:     */     
/*  726:     */ 
/*  727:     */ 
/*  728:1233 */     Scriptable superProto = null;
/*  729:1234 */     if (mapInheritance)
/*  730:     */     {
/*  731:1235 */       Class<? super T> superClass = clazz.getSuperclass();
/*  732:1236 */       if ((ScriptRuntime.ScriptableClass.isAssignableFrom(superClass)) && (!Modifier.isAbstract(superClass.getModifiers())))
/*  733:     */       {
/*  734:1239 */         Class<? extends Scriptable> superScriptable = extendsScriptable(superClass);
/*  735:     */         
/*  736:1241 */         String name = defineClass(scope, superScriptable, sealed, mapInheritance);
/*  737:1243 */         if (name != null) {
/*  738:1244 */           superProto = getClassPrototype(scope, name);
/*  739:     */         }
/*  740:     */       }
/*  741:     */     }
/*  742:1248 */     if (superProto == null) {
/*  743:1249 */       superProto = getObjectPrototype(scope);
/*  744:     */     }
/*  745:1251 */     proto.setPrototype(superProto);
/*  746:     */     
/*  747:     */ 
/*  748:     */ 
/*  749:     */ 
/*  750:1256 */     String functionPrefix = "jsFunction_";
/*  751:1257 */     String staticFunctionPrefix = "jsStaticFunction_";
/*  752:1258 */     String getterPrefix = "jsGet_";
/*  753:1259 */     String setterPrefix = "jsSet_";
/*  754:1260 */     String ctorName = "jsConstructor";
/*  755:     */     
/*  756:1262 */     Member ctorMember = findAnnotatedMember(methods, JSConstructor.class);
/*  757:1263 */     if (ctorMember == null) {
/*  758:1264 */       ctorMember = findAnnotatedMember(ctors, JSConstructor.class);
/*  759:     */     }
/*  760:1266 */     if (ctorMember == null) {
/*  761:1267 */       ctorMember = FunctionObject.findSingleMethod(methods, "jsConstructor");
/*  762:     */     }
/*  763:1269 */     if (ctorMember == null)
/*  764:     */     {
/*  765:1270 */       if (ctors.length == 1) {
/*  766:1271 */         ctorMember = ctors[0];
/*  767:1272 */       } else if (ctors.length == 2) {
/*  768:1273 */         if (ctors[0].getParameterTypes().length == 0) {
/*  769:1274 */           ctorMember = ctors[1];
/*  770:1275 */         } else if (ctors[1].getParameterTypes().length == 0) {
/*  771:1276 */           ctorMember = ctors[0];
/*  772:     */         }
/*  773:     */       }
/*  774:1278 */       if (ctorMember == null) {
/*  775:1279 */         throw Context.reportRuntimeError1("msg.ctor.multiple.parms", clazz.getName());
/*  776:     */       }
/*  777:     */     }
/*  778:1284 */     FunctionObject ctor = new FunctionObject(className, ctorMember, scope);
/*  779:1285 */     if (ctor.isVarArgsMethod()) {
/*  780:1286 */       throw Context.reportRuntimeError1("msg.varargs.ctor", ctorMember.getName());
/*  781:     */     }
/*  782:1289 */     ctor.initAsConstructor(scope, proto);
/*  783:     */     
/*  784:1291 */     Method finishInit = null;
/*  785:1292 */     HashSet<String> staticNames = new HashSet();
/*  786:1293 */     HashSet<String> instanceNames = new HashSet();
/*  787:1294 */     for (Method method : methods) {
/*  788:1295 */       if (method != ctorMember)
/*  789:     */       {
/*  790:1298 */         String name = method.getName();
/*  791:1299 */         if (name.equals("finishInit"))
/*  792:     */         {
/*  793:1300 */           Class<?>[] parmTypes = method.getParameterTypes();
/*  794:1301 */           if ((parmTypes.length == 3) && (parmTypes[0] == ScriptRuntime.ScriptableClass) && (parmTypes[1] == FunctionObject.class) && (parmTypes[2] == ScriptRuntime.ScriptableClass) && (Modifier.isStatic(method.getModifiers())))
/*  795:     */           {
/*  796:1307 */             finishInit = method;
/*  797:1308 */             continue;
/*  798:     */           }
/*  799:     */         }
/*  800:1312 */         if (name.indexOf('$') == -1) {
/*  801:1314 */           if (!name.equals("jsConstructor"))
/*  802:     */           {
/*  803:1317 */             Annotation annotation = null;
/*  804:1318 */             String prefix = null;
/*  805:1319 */             if (method.isAnnotationPresent(JSFunction.class)) {
/*  806:1320 */               annotation = method.getAnnotation(JSFunction.class);
/*  807:1321 */             } else if (method.isAnnotationPresent(JSStaticFunction.class)) {
/*  808:1322 */               annotation = method.getAnnotation(JSStaticFunction.class);
/*  809:1323 */             } else if (method.isAnnotationPresent(JSGetter.class)) {
/*  810:1324 */               annotation = method.getAnnotation(JSGetter.class);
/*  811:     */             } else {
/*  812:1325 */               if (method.isAnnotationPresent(JSSetter.class)) {
/*  813:     */                 continue;
/*  814:     */               }
/*  815:     */             }
/*  816:1329 */             if (annotation == null) {
/*  817:1330 */               if (name.startsWith("jsFunction_")) {
/*  818:1331 */                 prefix = "jsFunction_";
/*  819:1332 */               } else if (name.startsWith("jsStaticFunction_")) {
/*  820:1333 */                 prefix = "jsStaticFunction_";
/*  821:1334 */               } else if (name.startsWith("jsGet_")) {
/*  822:1335 */                 prefix = "jsGet_";
/*  823:     */               } else {
/*  824:1336 */                 if (annotation == null) {
/*  825:     */                   continue;
/*  826:     */                 }
/*  827:     */               }
/*  828:     */             }
/*  829:1343 */             boolean isStatic = ((annotation instanceof JSStaticFunction)) || (prefix == "jsStaticFunction_");
/*  830:     */             
/*  831:1345 */             HashSet<String> names = isStatic ? staticNames : instanceNames;
/*  832:1346 */             String propName = getPropertyName(name, prefix, annotation);
/*  833:1347 */             if (names.contains(propName)) {
/*  834:1348 */               throw Context.reportRuntimeError2("duplicate.defineClass.name", name, propName);
/*  835:     */             }
/*  836:1351 */             names.add(propName);
/*  837:1352 */             name = propName;
/*  838:1354 */             if (((annotation instanceof JSGetter)) || (prefix == "jsGet_"))
/*  839:     */             {
/*  840:1355 */               if (!(proto instanceof ScriptableObject)) {
/*  841:1356 */                 throw Context.reportRuntimeError2("msg.extend.scriptable", proto.getClass().toString(), name);
/*  842:     */               }
/*  843:1360 */               Method setter = findSetterMethod(methods, name, "jsSet_");
/*  844:1361 */               int attr = 0x6 | (setter != null ? 0 : 1);
/*  845:     */               
/*  846:     */ 
/*  847:     */ 
/*  848:1365 */               ((ScriptableObject)proto).defineProperty(name, null, method, setter, attr);
/*  849:     */             }
/*  850:     */             else
/*  851:     */             {
/*  852:1371 */               if ((isStatic) && (!Modifier.isStatic(method.getModifiers()))) {
/*  853:1372 */                 throw Context.reportRuntimeError("jsStaticFunction must be used with static method.");
/*  854:     */               }
/*  855:1376 */               FunctionObject f = new FunctionObject(name, method, proto);
/*  856:1377 */               if (f.isVarArgsConstructor()) {
/*  857:1378 */                 throw Context.reportRuntimeError1("msg.varargs.fun", ctorMember.getName());
/*  858:     */               }
/*  859:1381 */               defineProperty(isStatic ? ctor : proto, name, f, 2);
/*  860:1382 */               if (sealed) {
/*  861:1383 */                 f.sealObject();
/*  862:     */               }
/*  863:     */             }
/*  864:     */           }
/*  865:     */         }
/*  866:     */       }
/*  867:     */     }
/*  868:1388 */     if (finishInit != null)
/*  869:     */     {
/*  870:1389 */       Object[] finishArgs = { scope, ctor, proto };
/*  871:1390 */       finishInit.invoke(null, finishArgs);
/*  872:     */     }
/*  873:1394 */     if (sealed)
/*  874:     */     {
/*  875:1395 */       ctor.sealObject();
/*  876:1396 */       if ((proto instanceof ScriptableObject)) {
/*  877:1397 */         ((ScriptableObject)proto).sealObject();
/*  878:     */       }
/*  879:     */     }
/*  880:1401 */     return ctor;
/*  881:     */   }
/*  882:     */   
/*  883:     */   private static Member findAnnotatedMember(AccessibleObject[] members, Class<? extends Annotation> annotation)
/*  884:     */   {
/*  885:1406 */     for (AccessibleObject member : members) {
/*  886:1407 */       if (member.isAnnotationPresent(annotation)) {
/*  887:1408 */         return (Member)member;
/*  888:     */       }
/*  889:     */     }
/*  890:1411 */     return null;
/*  891:     */   }
/*  892:     */   
/*  893:     */   private static Method findSetterMethod(Method[] methods, String name, String prefix)
/*  894:     */   {
/*  895:1417 */     String newStyleName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
/*  896:1420 */     for (Method method : methods)
/*  897:     */     {
/*  898:1421 */       JSSetter annotation = (JSSetter)method.getAnnotation(JSSetter.class);
/*  899:1422 */       if ((annotation != null) && (
/*  900:1423 */         (name.equals(annotation.value())) || (("".equals(annotation.value())) && (newStyleName.equals(method.getName()))))) {
/*  901:1425 */         return method;
/*  902:     */       }
/*  903:     */     }
/*  904:1429 */     String oldStyleName = prefix + name;
/*  905:1430 */     for (Method method : methods) {
/*  906:1431 */       if (oldStyleName.equals(method.getName())) {
/*  907:1432 */         return method;
/*  908:     */       }
/*  909:     */     }
/*  910:1435 */     return null;
/*  911:     */   }
/*  912:     */   
/*  913:     */   private static String getPropertyName(String methodName, String prefix, Annotation annotation)
/*  914:     */   {
/*  915:1441 */     if (prefix != null) {
/*  916:1442 */       return methodName.substring(prefix.length());
/*  917:     */     }
/*  918:1444 */     String propName = null;
/*  919:1445 */     if ((annotation instanceof JSGetter))
/*  920:     */     {
/*  921:1446 */       propName = ((JSGetter)annotation).value();
/*  922:1447 */       if (((propName == null) || (propName.length() == 0)) && 
/*  923:1448 */         (methodName.length() > 3) && (methodName.startsWith("get")))
/*  924:     */       {
/*  925:1449 */         propName = methodName.substring(3);
/*  926:1450 */         if (Character.isUpperCase(propName.charAt(0))) {
/*  927:1451 */           if (propName.length() == 1) {
/*  928:1452 */             propName = propName.toLowerCase();
/*  929:1453 */           } else if (!Character.isUpperCase(propName.charAt(1))) {
/*  930:1454 */             propName = Character.toLowerCase(propName.charAt(0)) + propName.substring(1);
/*  931:     */           }
/*  932:     */         }
/*  933:     */       }
/*  934:     */     }
/*  935:1460 */     else if ((annotation instanceof JSFunction))
/*  936:     */     {
/*  937:1461 */       propName = ((JSFunction)annotation).value();
/*  938:     */     }
/*  939:1462 */     else if ((annotation instanceof JSStaticFunction))
/*  940:     */     {
/*  941:1463 */       propName = ((JSStaticFunction)annotation).value();
/*  942:     */     }
/*  943:1465 */     if ((propName == null) || (propName.length() == 0)) {
/*  944:1466 */       propName = methodName;
/*  945:     */     }
/*  946:1468 */     return propName;
/*  947:     */   }
/*  948:     */   
/*  949:     */   private static <T extends Scriptable> Class<T> extendsScriptable(Class<?> c)
/*  950:     */   {
/*  951:1474 */     if (ScriptRuntime.ScriptableClass.isAssignableFrom(c)) {
/*  952:1475 */       return c;
/*  953:     */     }
/*  954:1476 */     return null;
/*  955:     */   }
/*  956:     */   
/*  957:     */   public void defineProperty(String propertyName, Object value, int attributes)
/*  958:     */   {
/*  959:1492 */     checkNotSealed(propertyName, 0);
/*  960:1493 */     put(propertyName, this, value);
/*  961:1494 */     setAttributes(propertyName, attributes);
/*  962:     */   }
/*  963:     */   
/*  964:     */   public static void defineProperty(Scriptable destination, String propertyName, Object value, int attributes)
/*  965:     */   {
/*  966:1507 */     if (!(destination instanceof ScriptableObject))
/*  967:     */     {
/*  968:1508 */       destination.put(propertyName, destination, value);
/*  969:1509 */       return;
/*  970:     */     }
/*  971:1511 */     ScriptableObject so = (ScriptableObject)destination;
/*  972:1512 */     so.defineProperty(propertyName, value, attributes);
/*  973:     */   }
/*  974:     */   
/*  975:     */   public static void defineConstProperty(Scriptable destination, String propertyName)
/*  976:     */   {
/*  977:1524 */     if ((destination instanceof ConstProperties))
/*  978:     */     {
/*  979:1525 */       ConstProperties cp = (ConstProperties)destination;
/*  980:1526 */       cp.defineConst(propertyName, destination);
/*  981:     */     }
/*  982:     */     else
/*  983:     */     {
/*  984:1528 */       defineProperty(destination, propertyName, Undefined.instance, 13);
/*  985:     */     }
/*  986:     */   }
/*  987:     */   
/*  988:     */   public void defineProperty(String propertyName, Class<?> clazz, int attributes)
/*  989:     */   {
/*  990:1552 */     int length = propertyName.length();
/*  991:1553 */     if (length == 0) {
/*  992:1553 */       throw new IllegalArgumentException();
/*  993:     */     }
/*  994:1554 */     char[] buf = new char[3 + length];
/*  995:1555 */     propertyName.getChars(0, length, buf, 3);
/*  996:1556 */     buf[3] = Character.toUpperCase(buf[3]);
/*  997:1557 */     buf[0] = 'g';
/*  998:1558 */     buf[1] = 'e';
/*  999:1559 */     buf[2] = 't';
/* 1000:1560 */     String getterName = new String(buf);
/* 1001:1561 */     buf[0] = 's';
/* 1002:1562 */     String setterName = new String(buf);
/* 1003:     */     
/* 1004:1564 */     Method[] methods = FunctionObject.getMethodList(clazz);
/* 1005:1565 */     Method getter = FunctionObject.findSingleMethod(methods, getterName);
/* 1006:1566 */     Method setter = FunctionObject.findSingleMethod(methods, setterName);
/* 1007:1567 */     if (setter == null) {
/* 1008:1568 */       attributes |= 0x1;
/* 1009:     */     }
/* 1010:1569 */     defineProperty(propertyName, null, getter, setter == null ? null : setter, attributes);
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public void defineProperty(String propertyName, Object delegateTo, Method getter, Method setter, int attributes)
/* 1014:     */   {
/* 1015:1617 */     MemberBox getterBox = null;
/* 1016:1618 */     if (getter != null)
/* 1017:     */     {
/* 1018:1619 */       getterBox = new MemberBox(getter);
/* 1019:     */       boolean delegatedForm;
/* 1020:1622 */       if (!Modifier.isStatic(getter.getModifiers()))
/* 1021:     */       {
/* 1022:1623 */         boolean delegatedForm = delegateTo != null;
/* 1023:1624 */         getterBox.delegateTo = delegateTo;
/* 1024:     */       }
/* 1025:     */       else
/* 1026:     */       {
/* 1027:1626 */         delegatedForm = true;
/* 1028:     */         
/* 1029:     */ 
/* 1030:1629 */         getterBox.delegateTo = Void.TYPE;
/* 1031:     */       }
/* 1032:1632 */       String errorId = null;
/* 1033:1633 */       Class<?>[] parmTypes = getter.getParameterTypes();
/* 1034:1634 */       if (parmTypes.length == 0)
/* 1035:     */       {
/* 1036:1635 */         if (delegatedForm) {
/* 1037:1636 */           errorId = "msg.obj.getter.parms";
/* 1038:     */         }
/* 1039:     */       }
/* 1040:1638 */       else if (parmTypes.length == 1)
/* 1041:     */       {
/* 1042:1639 */         Object argType = parmTypes[0];
/* 1043:1641 */         if ((argType != ScriptRuntime.ScriptableClass) && (argType != ScriptRuntime.ScriptableObjectClass)) {
/* 1044:1644 */           errorId = "msg.bad.getter.parms";
/* 1045:1645 */         } else if (!delegatedForm) {
/* 1046:1646 */           errorId = "msg.bad.getter.parms";
/* 1047:     */         }
/* 1048:     */       }
/* 1049:     */       else
/* 1050:     */       {
/* 1051:1649 */         errorId = "msg.bad.getter.parms";
/* 1052:     */       }
/* 1053:1651 */       if (errorId != null) {
/* 1054:1652 */         throw Context.reportRuntimeError1(errorId, getter.toString());
/* 1055:     */       }
/* 1056:     */     }
/* 1057:1656 */     MemberBox setterBox = null;
/* 1058:1657 */     if (setter != null)
/* 1059:     */     {
/* 1060:1658 */       if (setter.getReturnType() != Void.TYPE) {
/* 1061:1659 */         throw Context.reportRuntimeError1("msg.setter.return", setter.toString());
/* 1062:     */       }
/* 1063:1662 */       setterBox = new MemberBox(setter);
/* 1064:     */       boolean delegatedForm;
/* 1065:1665 */       if (!Modifier.isStatic(setter.getModifiers()))
/* 1066:     */       {
/* 1067:1666 */         boolean delegatedForm = delegateTo != null;
/* 1068:1667 */         setterBox.delegateTo = delegateTo;
/* 1069:     */       }
/* 1070:     */       else
/* 1071:     */       {
/* 1072:1669 */         delegatedForm = true;
/* 1073:     */         
/* 1074:     */ 
/* 1075:1672 */         setterBox.delegateTo = Void.TYPE;
/* 1076:     */       }
/* 1077:1675 */       String errorId = null;
/* 1078:1676 */       Class<?>[] parmTypes = setter.getParameterTypes();
/* 1079:1677 */       if (parmTypes.length == 1)
/* 1080:     */       {
/* 1081:1678 */         if (delegatedForm) {
/* 1082:1679 */           errorId = "msg.setter2.expected";
/* 1083:     */         }
/* 1084:     */       }
/* 1085:1681 */       else if (parmTypes.length == 2)
/* 1086:     */       {
/* 1087:1682 */         Object argType = parmTypes[0];
/* 1088:1684 */         if ((argType != ScriptRuntime.ScriptableClass) && (argType != ScriptRuntime.ScriptableObjectClass)) {
/* 1089:1687 */           errorId = "msg.setter2.parms";
/* 1090:1688 */         } else if (!delegatedForm) {
/* 1091:1689 */           errorId = "msg.setter1.parms";
/* 1092:     */         }
/* 1093:     */       }
/* 1094:     */       else
/* 1095:     */       {
/* 1096:1692 */         errorId = "msg.setter.parms";
/* 1097:     */       }
/* 1098:1694 */       if (errorId != null) {
/* 1099:1695 */         throw Context.reportRuntimeError1(errorId, setter.toString());
/* 1100:     */       }
/* 1101:     */     }
/* 1102:1699 */     GetterSlot gslot = (GetterSlot)getSlot(propertyName, 0, 4);
/* 1103:     */     
/* 1104:1701 */     gslot.setAttributes(attributes);
/* 1105:1702 */     gslot.getter = getterBox;
/* 1106:1703 */     gslot.setter = setterBox;
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public void defineOwnProperties(Context cx, ScriptableObject props)
/* 1110:     */   {
/* 1111:1707 */     Object[] ids = props.getIds();
/* 1112:1708 */     for (Object id : ids)
/* 1113:     */     {
/* 1114:1709 */       String name = ScriptRuntime.toString(id);
/* 1115:1710 */       Object descObj = props.get(id);
/* 1116:1711 */       ScriptableObject desc = ensureScriptableObject(descObj);
/* 1117:1712 */       checkValidPropertyDefinition(getSlot(name, 0, 1), desc);
/* 1118:     */     }
/* 1119:1714 */     for (Object id : ids)
/* 1120:     */     {
/* 1121:1715 */       String name = ScriptRuntime.toString(id);
/* 1122:1716 */       ScriptableObject desc = (ScriptableObject)props.get(id);
/* 1123:1717 */       defineOwnProperty(cx, name, desc, false);
/* 1124:     */     }
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public void defineOwnProperty(Context cx, Object id, ScriptableObject desc)
/* 1128:     */   {
/* 1129:1731 */     defineOwnProperty(cx, id, desc, true);
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   private void defineOwnProperty(Context cx, Object id, ScriptableObject desc, boolean checkValid)
/* 1133:     */   {
/* 1134:1735 */     Slot slot = getSlot(cx, id, 1);
/* 1135:1737 */     if (checkValid) {
/* 1136:1738 */       checkValidPropertyDefinition(slot, desc);
/* 1137:     */     }
/* 1138:     */     int attributes;
/* 1139:     */     int attributes;
/* 1140:1741 */     if (slot == null)
/* 1141:     */     {
/* 1142:1742 */       slot = getSlot(cx, id, 2);
/* 1143:1743 */       attributes = applyDescriptorToAttributeBitset(7, desc);
/* 1144:     */     }
/* 1145:     */     else
/* 1146:     */     {
/* 1147:1745 */       attributes = applyDescriptorToAttributeBitset(slot.getAttributes(), desc);
/* 1148:     */     }
/* 1149:1748 */     defineOwnProperty(cx, slot, desc, attributes);
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   private void defineOwnProperty(Context cx, Slot slot, ScriptableObject desc, int attributes)
/* 1153:     */   {
/* 1154:1752 */     String name = slot.name;
/* 1155:1753 */     int index = slot.indexOrHash;
/* 1156:1755 */     if (isAccessorDescriptor(desc))
/* 1157:     */     {
/* 1158:1756 */       if (!(slot instanceof GetterSlot)) {
/* 1159:1757 */         slot = getSlot(cx, name != null ? name : Integer.valueOf(index), 4);
/* 1160:     */       }
/* 1161:1760 */       GetterSlot gslot = (GetterSlot)slot;
/* 1162:     */       
/* 1163:1762 */       Object getter = getProperty(desc, "get");
/* 1164:1763 */       if (getter != NOT_FOUND) {
/* 1165:1764 */         gslot.getter = getter;
/* 1166:     */       }
/* 1167:1766 */       Object setter = getProperty(desc, "set");
/* 1168:1767 */       if (setter != NOT_FOUND) {
/* 1169:1768 */         gslot.setter = setter;
/* 1170:     */       }
/* 1171:1771 */       gslot.value = Undefined.instance;
/* 1172:1772 */       gslot.setAttributes(attributes);
/* 1173:     */     }
/* 1174:     */     else
/* 1175:     */     {
/* 1176:1774 */       if (((slot instanceof GetterSlot)) && (isDataDescriptor(desc))) {
/* 1177:1775 */         slot = getSlot(cx, name != null ? name : Integer.valueOf(index), 5);
/* 1178:     */       }
/* 1179:1778 */       Object value = getProperty(desc, "value");
/* 1180:1779 */       if (value != NOT_FOUND) {
/* 1181:1780 */         slot.value = value;
/* 1182:     */       }
/* 1183:1782 */       slot.setAttributes(attributes);
/* 1184:     */     }
/* 1185:     */   }
/* 1186:     */   
/* 1187:     */   private void checkValidPropertyDefinition(Slot slot, ScriptableObject desc)
/* 1188:     */   {
/* 1189:1787 */     Object getter = getProperty(desc, "get");
/* 1190:1788 */     if ((getter != NOT_FOUND) && (getter != Undefined.instance) && (!(getter instanceof Callable))) {
/* 1191:1789 */       throw ScriptRuntime.notFunctionError(getter);
/* 1192:     */     }
/* 1193:1791 */     Object setter = getProperty(desc, "set");
/* 1194:1792 */     if ((setter != NOT_FOUND) && (setter != Undefined.instance) && (!(setter instanceof Callable))) {
/* 1195:1793 */       throw ScriptRuntime.notFunctionError(setter);
/* 1196:     */     }
/* 1197:1795 */     if ((isDataDescriptor(desc)) && (isAccessorDescriptor(desc))) {
/* 1198:1796 */       throw ScriptRuntime.typeError0("msg.both.data.and.accessor.desc");
/* 1199:     */     }
/* 1200:1799 */     if (slot == null)
/* 1201:     */     {
/* 1202:1800 */       if (!isExtensible()) {
/* 1203:1800 */         throw ScriptRuntime.typeError0("msg.not.extensible");
/* 1204:     */       }
/* 1205:     */     }
/* 1206:     */     else
/* 1207:     */     {
/* 1208:1802 */       ScriptableObject current = slot.getPropertyDescriptor(Context.getContext(), this);
/* 1209:1803 */       if (isFalse(current.get("configurable", current)))
/* 1210:     */       {
/* 1211:1804 */         String id = slot.name != null ? slot.name : Integer.toString(slot.indexOrHash);
/* 1212:1806 */         if (isTrue(getProperty(desc, "configurable"))) {
/* 1213:1807 */           throw ScriptRuntime.typeError1("msg.change.configurable.false.to.true", id);
/* 1214:     */         }
/* 1215:1809 */         if (isTrue(current.get("enumerable", current)) != isTrue(getProperty(desc, "enumerable"))) {
/* 1216:1810 */           throw ScriptRuntime.typeError1("msg.change.enumerable.with.configurable.false", id);
/* 1217:     */         }
/* 1218:1812 */         if (!isGenericDescriptor(desc)) {
/* 1219:1814 */           if ((isDataDescriptor(desc)) && (isDataDescriptor(current)))
/* 1220:     */           {
/* 1221:1815 */             if (isFalse(current.get("writable", current)))
/* 1222:     */             {
/* 1223:1816 */               if (isTrue(getProperty(desc, "writable"))) {
/* 1224:1817 */                 throw ScriptRuntime.typeError1("msg.change.writable.false.to.true.with.configurable.false", id);
/* 1225:     */               }
/* 1226:1820 */               if (changes(current.get("value", current), getProperty(desc, "value"))) {
/* 1227:1821 */                 throw ScriptRuntime.typeError1("msg.change.value.with.writable.false", id);
/* 1228:     */               }
/* 1229:     */             }
/* 1230:     */           }
/* 1231:1824 */           else if ((isAccessorDescriptor(desc)) && (isAccessorDescriptor(current)))
/* 1232:     */           {
/* 1233:1825 */             if (changes(current.get("set", current), setter)) {
/* 1234:1826 */               throw ScriptRuntime.typeError1("msg.change.setter.with.configurable.false", id);
/* 1235:     */             }
/* 1236:1829 */             if (changes(current.get("get", current), getter)) {
/* 1237:1830 */               throw ScriptRuntime.typeError1("msg.change.getter.with.configurable.false", id);
/* 1238:     */             }
/* 1239:     */           }
/* 1240:     */           else
/* 1241:     */           {
/* 1242:1833 */             if (isDataDescriptor(current)) {
/* 1243:1834 */               throw ScriptRuntime.typeError1("msg.change.property.data.to.accessor.with.configurable.false", id);
/* 1244:     */             }
/* 1245:1837 */             throw ScriptRuntime.typeError1("msg.change.property.accessor.to.data.with.configurable.false", id);
/* 1246:     */           }
/* 1247:     */         }
/* 1248:     */       }
/* 1249:     */     }
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   protected static boolean isTrue(Object value)
/* 1253:     */   {
/* 1254:1845 */     return value == NOT_FOUND ? false : ScriptRuntime.toBoolean(value);
/* 1255:     */   }
/* 1256:     */   
/* 1257:     */   protected static boolean isFalse(Object value)
/* 1258:     */   {
/* 1259:1849 */     return !isTrue(value);
/* 1260:     */   }
/* 1261:     */   
/* 1262:     */   private boolean changes(Object currentValue, Object newValue)
/* 1263:     */   {
/* 1264:1853 */     if (newValue == NOT_FOUND) {
/* 1265:1853 */       return false;
/* 1266:     */     }
/* 1267:1854 */     if (currentValue == NOT_FOUND) {
/* 1268:1855 */       currentValue = Undefined.instance;
/* 1269:     */     }
/* 1270:1857 */     return !ScriptRuntime.shallowEq(currentValue, newValue);
/* 1271:     */   }
/* 1272:     */   
/* 1273:     */   protected int applyDescriptorToAttributeBitset(int attributes, ScriptableObject desc)
/* 1274:     */   {
/* 1275:1863 */     Object enumerable = getProperty(desc, "enumerable");
/* 1276:1864 */     if (enumerable != NOT_FOUND) {
/* 1277:1865 */       attributes = ScriptRuntime.toBoolean(enumerable) ? attributes & 0xFFFFFFFD : attributes | 0x2;
/* 1278:     */     }
/* 1279:1869 */     Object writable = getProperty(desc, "writable");
/* 1280:1870 */     if (writable != NOT_FOUND) {
/* 1281:1871 */       attributes = ScriptRuntime.toBoolean(writable) ? attributes & 0xFFFFFFFE : attributes | 0x1;
/* 1282:     */     }
/* 1283:1875 */     Object configurable = getProperty(desc, "configurable");
/* 1284:1876 */     if (configurable != NOT_FOUND) {
/* 1285:1877 */       attributes = ScriptRuntime.toBoolean(configurable) ? attributes & 0xFFFFFFFB : attributes | 0x4;
/* 1286:     */     }
/* 1287:1881 */     return attributes;
/* 1288:     */   }
/* 1289:     */   
/* 1290:     */   protected boolean isDataDescriptor(ScriptableObject desc)
/* 1291:     */   {
/* 1292:1885 */     return (hasProperty(desc, "value")) || (hasProperty(desc, "writable"));
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   protected boolean isAccessorDescriptor(ScriptableObject desc)
/* 1296:     */   {
/* 1297:1889 */     return (hasProperty(desc, "get")) || (hasProperty(desc, "set"));
/* 1298:     */   }
/* 1299:     */   
/* 1300:     */   protected boolean isGenericDescriptor(ScriptableObject desc)
/* 1301:     */   {
/* 1302:1893 */     return (!isDataDescriptor(desc)) && (!isAccessorDescriptor(desc));
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   protected Scriptable ensureScriptable(Object arg)
/* 1306:     */   {
/* 1307:1897 */     if (!(arg instanceof Scriptable)) {
/* 1308:1898 */       throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(arg));
/* 1309:     */     }
/* 1310:1899 */     return (Scriptable)arg;
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   protected ScriptableObject ensureScriptableObject(Object arg)
/* 1314:     */   {
/* 1315:1903 */     if (!(arg instanceof ScriptableObject)) {
/* 1316:1904 */       throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(arg));
/* 1317:     */     }
/* 1318:1905 */     return (ScriptableObject)arg;
/* 1319:     */   }
/* 1320:     */   
/* 1321:     */   public void defineFunctionProperties(String[] names, Class<?> clazz, int attributes)
/* 1322:     */   {
/* 1323:1924 */     Method[] methods = FunctionObject.getMethodList(clazz);
/* 1324:1925 */     for (int i = 0; i < names.length; i++)
/* 1325:     */     {
/* 1326:1926 */       String name = names[i];
/* 1327:1927 */       Method m = FunctionObject.findSingleMethod(methods, name);
/* 1328:1928 */       if (m == null) {
/* 1329:1929 */         throw Context.reportRuntimeError2("msg.method.not.found", name, clazz.getName());
/* 1330:     */       }
/* 1331:1932 */       FunctionObject f = new FunctionObject(name, m, this);
/* 1332:1933 */       defineProperty(name, f, attributes);
/* 1333:     */     }
/* 1334:     */   }
/* 1335:     */   
/* 1336:     */   public static Scriptable getObjectPrototype(Scriptable scope)
/* 1337:     */   {
/* 1338:1942 */     return TopLevel.getBuiltinPrototype(getTopLevelScope(scope), TopLevel.Builtins.Object);
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   public static Scriptable getFunctionPrototype(Scriptable scope)
/* 1342:     */   {
/* 1343:1951 */     return TopLevel.getBuiltinPrototype(getTopLevelScope(scope), TopLevel.Builtins.Function);
/* 1344:     */   }
/* 1345:     */   
/* 1346:     */   public static Scriptable getArrayPrototype(Scriptable scope)
/* 1347:     */   {
/* 1348:1956 */     return TopLevel.getBuiltinPrototype(getTopLevelScope(scope), TopLevel.Builtins.Array);
/* 1349:     */   }
/* 1350:     */   
/* 1351:     */   public static Scriptable getClassPrototype(Scriptable scope, String className)
/* 1352:     */   {
/* 1353:1978 */     scope = getTopLevelScope(scope);
/* 1354:1979 */     Object ctor = getProperty(scope, className);
/* 1355:     */     Object proto;
/* 1356:1981 */     if ((ctor instanceof BaseFunction))
/* 1357:     */     {
/* 1358:1982 */       proto = ((BaseFunction)ctor).getPrototypeProperty();
/* 1359:     */     }
/* 1360:     */     else
/* 1361:     */     {
/* 1362:     */       Object proto;
/* 1363:1983 */       if ((ctor instanceof Scriptable))
/* 1364:     */       {
/* 1365:1984 */         Scriptable ctorObj = (Scriptable)ctor;
/* 1366:1985 */         proto = ctorObj.get("prototype", ctorObj);
/* 1367:     */       }
/* 1368:     */       else
/* 1369:     */       {
/* 1370:1987 */         return null;
/* 1371:     */       }
/* 1372:     */     }
/* 1373:     */     Object proto;
/* 1374:1989 */     if ((proto instanceof Scriptable)) {
/* 1375:1990 */       return (Scriptable)proto;
/* 1376:     */     }
/* 1377:1992 */     return null;
/* 1378:     */   }
/* 1379:     */   
/* 1380:     */   public static Scriptable getTopLevelScope(Scriptable obj)
/* 1381:     */   {
/* 1382:     */     for (;;)
/* 1383:     */     {
/* 1384:2007 */       Scriptable parent = obj.getParentScope();
/* 1385:2008 */       if (parent == null) {
/* 1386:2009 */         return obj;
/* 1387:     */       }
/* 1388:2011 */       obj = parent;
/* 1389:     */     }
/* 1390:     */   }
/* 1391:     */   
/* 1392:     */   public boolean isExtensible()
/* 1393:     */   {
/* 1394:2016 */     return this.isExtensible;
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   public void preventExtensions()
/* 1398:     */   {
/* 1399:2020 */     this.isExtensible = false;
/* 1400:     */   }
/* 1401:     */   
/* 1402:     */   public synchronized void sealObject()
/* 1403:     */   {
/* 1404:2033 */     if (this.count >= 0)
/* 1405:     */     {
/* 1406:2035 */       Slot slot = this.firstAdded;
/* 1407:2036 */       while (slot != null)
/* 1408:     */       {
/* 1409:2037 */         if ((slot.value instanceof LazilyLoadedCtor))
/* 1410:     */         {
/* 1411:2038 */           LazilyLoadedCtor initializer = (LazilyLoadedCtor)slot.value;
/* 1412:     */           try
/* 1413:     */           {
/* 1414:2040 */             initializer.init();
/* 1415:     */           }
/* 1416:     */           finally
/* 1417:     */           {
/* 1418:2042 */             slot.value = initializer.getValue();
/* 1419:     */           }
/* 1420:     */         }
/* 1421:2045 */         slot = slot.orderedNext;
/* 1422:     */       }
/* 1423:2047 */       this.count ^= 0xFFFFFFFF;
/* 1424:     */     }
/* 1425:     */   }
/* 1426:     */   
/* 1427:     */   public final boolean isSealed()
/* 1428:     */   {
/* 1429:2059 */     return this.count < 0;
/* 1430:     */   }
/* 1431:     */   
/* 1432:     */   private void checkNotSealed(String name, int index)
/* 1433:     */   {
/* 1434:2064 */     if (!isSealed()) {
/* 1435:2065 */       return;
/* 1436:     */     }
/* 1437:2067 */     String str = name != null ? name : Integer.toString(index);
/* 1438:2068 */     throw Context.reportRuntimeError1("msg.modify.sealed", str);
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public static Object getProperty(Scriptable obj, String name)
/* 1442:     */   {
/* 1443:2085 */     Scriptable start = obj;
/* 1444:     */     Object result;
/* 1445:     */     do
/* 1446:     */     {
/* 1447:2088 */       result = obj.get(name, start);
/* 1448:2089 */       if (result != Scriptable.NOT_FOUND) {
/* 1449:     */         break;
/* 1450:     */       }
/* 1451:2091 */       obj = obj.getPrototype();
/* 1452:2092 */     } while (obj != null);
/* 1453:2093 */     return result;
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public static <T> T getTypedProperty(Scriptable s, int index, Class<T> type)
/* 1457:     */   {
/* 1458:2116 */     Object val = getProperty(s, index);
/* 1459:2117 */     if (val == Scriptable.NOT_FOUND) {
/* 1460:2118 */       val = null;
/* 1461:     */     }
/* 1462:2120 */     return type.cast(Context.jsToJava(val, type));
/* 1463:     */   }
/* 1464:     */   
/* 1465:     */   public static Object getProperty(Scriptable obj, int index)
/* 1466:     */   {
/* 1467:2140 */     Scriptable start = obj;
/* 1468:     */     Object result;
/* 1469:     */     do
/* 1470:     */     {
/* 1471:2143 */       result = obj.get(index, start);
/* 1472:2144 */       if (result != Scriptable.NOT_FOUND) {
/* 1473:     */         break;
/* 1474:     */       }
/* 1475:2146 */       obj = obj.getPrototype();
/* 1476:2147 */     } while (obj != null);
/* 1477:2148 */     return result;
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public static <T> T getTypedProperty(Scriptable s, String name, Class<T> type)
/* 1481:     */   {
/* 1482:2168 */     Object val = getProperty(s, name);
/* 1483:2169 */     if (val == Scriptable.NOT_FOUND) {
/* 1484:2170 */       val = null;
/* 1485:     */     }
/* 1486:2172 */     return type.cast(Context.jsToJava(val, type));
/* 1487:     */   }
/* 1488:     */   
/* 1489:     */   public static boolean hasProperty(Scriptable obj, String name)
/* 1490:     */   {
/* 1491:2188 */     return null != getBase(obj, name);
/* 1492:     */   }
/* 1493:     */   
/* 1494:     */   public static void redefineProperty(Scriptable obj, String name, boolean isConst)
/* 1495:     */   {
/* 1496:2203 */     Scriptable base = getBase(obj, name);
/* 1497:2204 */     if (base == null) {
/* 1498:2205 */       return;
/* 1499:     */     }
/* 1500:2206 */     if ((base instanceof ConstProperties))
/* 1501:     */     {
/* 1502:2207 */       ConstProperties cp = (ConstProperties)base;
/* 1503:2209 */       if (cp.isConst(name)) {
/* 1504:2210 */         throw Context.reportRuntimeError1("msg.const.redecl", name);
/* 1505:     */       }
/* 1506:     */     }
/* 1507:2212 */     if (isConst) {
/* 1508:2213 */       throw Context.reportRuntimeError1("msg.var.redecl", name);
/* 1509:     */     }
/* 1510:     */   }
/* 1511:     */   
/* 1512:     */   public static boolean hasProperty(Scriptable obj, int index)
/* 1513:     */   {
/* 1514:2228 */     return null != getBase(obj, index);
/* 1515:     */   }
/* 1516:     */   
/* 1517:     */   public static void putProperty(Scriptable obj, String name, Object value)
/* 1518:     */   {
/* 1519:2248 */     Scriptable base = getBase(obj, name);
/* 1520:2249 */     if (base == null) {
/* 1521:2250 */       base = obj;
/* 1522:     */     }
/* 1523:2251 */     base.put(name, obj, value);
/* 1524:     */   }
/* 1525:     */   
/* 1526:     */   public static void putConstProperty(Scriptable obj, String name, Object value)
/* 1527:     */   {
/* 1528:2271 */     Scriptable base = getBase(obj, name);
/* 1529:2272 */     if (base == null) {
/* 1530:2273 */       base = obj;
/* 1531:     */     }
/* 1532:2274 */     if ((base instanceof ConstProperties)) {
/* 1533:2275 */       ((ConstProperties)base).putConst(name, obj, value);
/* 1534:     */     }
/* 1535:     */   }
/* 1536:     */   
/* 1537:     */   public static void putProperty(Scriptable obj, int index, Object value)
/* 1538:     */   {
/* 1539:2295 */     Scriptable base = getBase(obj, index);
/* 1540:2296 */     if (base == null) {
/* 1541:2297 */       base = obj;
/* 1542:     */     }
/* 1543:2298 */     base.put(index, obj, value);
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   public static boolean deleteProperty(Scriptable obj, String name)
/* 1547:     */   {
/* 1548:2314 */     Scriptable base = getBase(obj, name);
/* 1549:2315 */     if (base == null) {
/* 1550:2316 */       return true;
/* 1551:     */     }
/* 1552:2317 */     base.delete(name);
/* 1553:2318 */     return !base.has(name, obj);
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   public static boolean deleteProperty(Scriptable obj, int index)
/* 1557:     */   {
/* 1558:2334 */     Scriptable base = getBase(obj, index);
/* 1559:2335 */     if (base == null) {
/* 1560:2336 */       return true;
/* 1561:     */     }
/* 1562:2337 */     base.delete(index);
/* 1563:2338 */     return !base.has(index, obj);
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public static Object[] getPropertyIds(Scriptable obj)
/* 1567:     */   {
/* 1568:2352 */     if (obj == null) {
/* 1569:2353 */       return ScriptRuntime.emptyArgs;
/* 1570:     */     }
/* 1571:2355 */     Object[] result = obj.getIds();
/* 1572:2356 */     ObjToIntMap map = null;
/* 1573:     */     for (;;)
/* 1574:     */     {
/* 1575:2358 */       obj = obj.getPrototype();
/* 1576:2359 */       if (obj == null) {
/* 1577:     */         break;
/* 1578:     */       }
/* 1579:2362 */       Object[] ids = obj.getIds();
/* 1580:2363 */       if (ids.length != 0) {
/* 1581:2366 */         if (map == null)
/* 1582:     */         {
/* 1583:2367 */           if (result.length == 0)
/* 1584:     */           {
/* 1585:2368 */             result = ids;
/* 1586:     */           }
/* 1587:     */           else
/* 1588:     */           {
/* 1589:2371 */             map = new ObjToIntMap(result.length + ids.length);
/* 1590:2372 */             for (int i = 0; i != result.length; i++) {
/* 1591:2373 */               map.intern(result[i]);
/* 1592:     */             }
/* 1593:2375 */             result = null;
/* 1594:     */           }
/* 1595:     */         }
/* 1596:     */         else {
/* 1597:2377 */           for (int i = 0; i != ids.length; i++) {
/* 1598:2378 */             map.intern(ids[i]);
/* 1599:     */           }
/* 1600:     */         }
/* 1601:     */       }
/* 1602:     */     }
/* 1603:2381 */     if (map != null) {
/* 1604:2382 */       result = map.getKeys();
/* 1605:     */     }
/* 1606:2384 */     return result;
/* 1607:     */   }
/* 1608:     */   
/* 1609:     */   public static Object callMethod(Scriptable obj, String methodName, Object[] args)
/* 1610:     */   {
/* 1611:2398 */     return callMethod(null, obj, methodName, args);
/* 1612:     */   }
/* 1613:     */   
/* 1614:     */   public static Object callMethod(Context cx, Scriptable obj, String methodName, Object[] args)
/* 1615:     */   {
/* 1616:2412 */     Object funObj = getProperty(obj, methodName);
/* 1617:2413 */     if (!(funObj instanceof Function)) {
/* 1618:2414 */       throw ScriptRuntime.notFunctionError(obj, methodName);
/* 1619:     */     }
/* 1620:2416 */     Function fun = (Function)funObj;
/* 1621:     */     
/* 1622:     */ 
/* 1623:     */ 
/* 1624:     */ 
/* 1625:     */ 
/* 1626:     */ 
/* 1627:     */ 
/* 1628:2424 */     Scriptable scope = getTopLevelScope(obj);
/* 1629:2425 */     if (cx != null) {
/* 1630:2426 */       return fun.call(cx, scope, obj, args);
/* 1631:     */     }
/* 1632:2428 */     return Context.call(null, fun, scope, obj, args);
/* 1633:     */   }
/* 1634:     */   
/* 1635:     */   private static Scriptable getBase(Scriptable obj, String name)
/* 1636:     */   {
/* 1637:     */     do
/* 1638:     */     {
/* 1639:2435 */       if (obj.has(name, obj)) {
/* 1640:     */         break;
/* 1641:     */       }
/* 1642:2437 */       obj = obj.getPrototype();
/* 1643:2438 */     } while (obj != null);
/* 1644:2439 */     return obj;
/* 1645:     */   }
/* 1646:     */   
/* 1647:     */   private static Scriptable getBase(Scriptable obj, int index)
/* 1648:     */   {
/* 1649:     */     do
/* 1650:     */     {
/* 1651:2445 */       if (obj.has(index, obj)) {
/* 1652:     */         break;
/* 1653:     */       }
/* 1654:2447 */       obj = obj.getPrototype();
/* 1655:2448 */     } while (obj != null);
/* 1656:2449 */     return obj;
/* 1657:     */   }
/* 1658:     */   
/* 1659:     */   public final Object getAssociatedValue(Object key)
/* 1660:     */   {
/* 1661:2459 */     Map<Object, Object> h = this.associatedValues;
/* 1662:2460 */     if (h == null) {
/* 1663:2461 */       return null;
/* 1664:     */     }
/* 1665:2462 */     return h.get(key);
/* 1666:     */   }
/* 1667:     */   
/* 1668:     */   public static Object getTopScopeValue(Scriptable scope, Object key)
/* 1669:     */   {
/* 1670:2478 */     scope = getTopLevelScope(scope);
/* 1671:     */     do
/* 1672:     */     {
/* 1673:2480 */       if ((scope instanceof ScriptableObject))
/* 1674:     */       {
/* 1675:2481 */         ScriptableObject so = (ScriptableObject)scope;
/* 1676:2482 */         Object value = so.getAssociatedValue(key);
/* 1677:2483 */         if (value != null) {
/* 1678:2484 */           return value;
/* 1679:     */         }
/* 1680:     */       }
/* 1681:2487 */       scope = scope.getPrototype();
/* 1682:2488 */     } while (scope != null);
/* 1683:2489 */     return null;
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   public final synchronized Object associateValue(Object key, Object value)
/* 1687:     */   {
/* 1688:2508 */     if (value == null) {
/* 1689:2508 */       throw new IllegalArgumentException();
/* 1690:     */     }
/* 1691:2509 */     Map<Object, Object> h = this.associatedValues;
/* 1692:2510 */     if (h == null)
/* 1693:     */     {
/* 1694:2511 */       h = new HashMap();
/* 1695:2512 */       this.associatedValues = h;
/* 1696:     */     }
/* 1697:2514 */     return Kit.initHash(h, key, value);
/* 1698:     */   }
/* 1699:     */   
/* 1700:     */   private Object getImpl(String name, int index, Scriptable start)
/* 1701:     */   {
/* 1702:2519 */     Slot slot = getSlot(name, index, 1);
/* 1703:2520 */     if (slot == null) {
/* 1704:2521 */       return Scriptable.NOT_FOUND;
/* 1705:     */     }
/* 1706:2523 */     return slot.getValue(start);
/* 1707:     */   }
/* 1708:     */   
/* 1709:     */   private boolean putImpl(String name, int index, Scriptable start, Object value, int constFlag)
/* 1710:     */   {
/* 1711:     */     Slot slot;
/* 1712:2541 */     if (this != start)
/* 1713:     */     {
/* 1714:2542 */       Slot slot = getSlot(name, index, 1);
/* 1715:2543 */       if (slot == null) {
/* 1716:2544 */         return false;
/* 1717:     */       }
/* 1718:     */     }
/* 1719:2546 */     else if (!isExtensible())
/* 1720:     */     {
/* 1721:2547 */       Slot slot = getSlot(name, index, 1);
/* 1722:2548 */       if (slot == null) {
/* 1723:2549 */         return true;
/* 1724:     */       }
/* 1725:     */     }
/* 1726:     */     else
/* 1727:     */     {
/* 1728:2552 */       checkNotSealed(name, index);
/* 1729:2554 */       if (constFlag != 0)
/* 1730:     */       {
/* 1731:2555 */         Slot slot = getSlot(name, index, 3);
/* 1732:2556 */         int attr = slot.getAttributes();
/* 1733:2557 */         if ((attr & 0x1) == 0) {
/* 1734:2558 */           throw Context.reportRuntimeError1("msg.var.redecl", name);
/* 1735:     */         }
/* 1736:2559 */         if ((attr & 0x8) != 0)
/* 1737:     */         {
/* 1738:2560 */           slot.value = value;
/* 1739:2562 */           if (constFlag != 8) {
/* 1740:2563 */             slot.setAttributes(attr & 0xFFFFFFF7);
/* 1741:     */           }
/* 1742:     */         }
/* 1743:2565 */         return true;
/* 1744:     */       }
/* 1745:2567 */       slot = getSlot(name, index, 2);
/* 1746:     */     }
/* 1747:2569 */     return slot.setValue(value, this, start);
/* 1748:     */   }
/* 1749:     */   
/* 1750:     */   private Slot findAttributeSlot(String name, int index, int accessType)
/* 1751:     */   {
/* 1752:2574 */     Slot slot = getSlot(name, index, accessType);
/* 1753:2575 */     if (slot == null)
/* 1754:     */     {
/* 1755:2576 */       String str = name != null ? name : Integer.toString(index);
/* 1756:2577 */       throw Context.reportRuntimeError1("msg.prop.not.found", str);
/* 1757:     */     }
/* 1758:2579 */     return slot;
/* 1759:     */   }
/* 1760:     */   
/* 1761:     */   private Slot getSlot(String name, int index, int accessType)
/* 1762:     */   {
/* 1763:2592 */     Slot[] slotsLocalRef = this.slots;
/* 1764:2593 */     if ((slotsLocalRef == null) && (accessType == 1)) {
/* 1765:2594 */       return null;
/* 1766:     */     }
/* 1767:2597 */     int indexOrHash = name != null ? name.hashCode() : index;
/* 1768:2598 */     if (slotsLocalRef != null)
/* 1769:     */     {
/* 1770:2600 */       int slotIndex = getSlotIndex(slotsLocalRef.length, indexOrHash);
/* 1771:2601 */       for (Slot slot = slotsLocalRef[slotIndex]; slot != null; slot = slot.next)
/* 1772:     */       {
/* 1773:2604 */         Object sname = slot.name;
/* 1774:2605 */         if ((indexOrHash == slot.indexOrHash) && ((sname == name) || ((name != null) && (name.equals(sname))))) {
/* 1775:     */           break;
/* 1776:     */         }
/* 1777:     */       }
/* 1778:2611 */       switch (accessType)
/* 1779:     */       {
/* 1780:     */       case 1: 
/* 1781:2613 */         return slot;
/* 1782:     */       case 2: 
/* 1783:     */       case 3: 
/* 1784:2616 */         if (slot != null) {
/* 1785:2617 */           return slot;
/* 1786:     */         }
/* 1787:     */         break;
/* 1788:     */       case 4: 
/* 1789:2620 */         if ((slot instanceof GetterSlot)) {
/* 1790:2621 */           return slot;
/* 1791:     */         }
/* 1792:     */         break;
/* 1793:     */       case 5: 
/* 1794:2624 */         if (!(slot instanceof GetterSlot)) {
/* 1795:2625 */           return slot;
/* 1796:     */         }
/* 1797:     */         break;
/* 1798:     */       }
/* 1799:     */     }
/* 1800:2632 */     return createSlot(name, indexOrHash, accessType);
/* 1801:     */   }
/* 1802:     */   
/* 1803:     */   private synchronized Slot createSlot(String name, int indexOrHash, int accessType)
/* 1804:     */   {
/* 1805:2636 */     Slot[] slotsLocalRef = this.slots;
/* 1806:     */     int insertPos;
/* 1807:     */     int insertPos;
/* 1808:2638 */     if (this.count == 0)
/* 1809:     */     {
/* 1810:2640 */       slotsLocalRef = new Slot[4];
/* 1811:2641 */       this.slots = slotsLocalRef;
/* 1812:2642 */       insertPos = getSlotIndex(slotsLocalRef.length, indexOrHash);
/* 1813:     */     }
/* 1814:     */     else
/* 1815:     */     {
/* 1816:2644 */       int tableSize = slotsLocalRef.length;
/* 1817:2645 */       insertPos = getSlotIndex(tableSize, indexOrHash);
/* 1818:2646 */       Slot prev = slotsLocalRef[insertPos];
/* 1819:2647 */       Slot slot = prev;
/* 1820:2648 */       while ((slot != null) && (
/* 1821:2649 */         (slot.indexOrHash != indexOrHash) || ((slot.name != name) && ((name == null) || (!name.equals(slot.name))))))
/* 1822:     */       {
/* 1823:2655 */         prev = slot;
/* 1824:2656 */         slot = slot.next;
/* 1825:     */       }
/* 1826:2659 */       if (slot != null)
/* 1827:     */       {
/* 1828:     */         Slot newSlot;
/* 1829:2670 */         if ((accessType == 4) && (!(slot instanceof GetterSlot)))
/* 1830:     */         {
/* 1831:2671 */           newSlot = new GetterSlot(name, indexOrHash, slot.getAttributes());
/* 1832:     */         }
/* 1833:     */         else
/* 1834:     */         {
/* 1835:     */           Slot newSlot;
/* 1836:2672 */           if ((accessType == 5) && ((slot instanceof GetterSlot)))
/* 1837:     */           {
/* 1838:2673 */             newSlot = new Slot(name, indexOrHash, slot.getAttributes());
/* 1839:     */           }
/* 1840:     */           else
/* 1841:     */           {
/* 1842:2674 */             if (accessType == 3) {
/* 1843:2675 */               return null;
/* 1844:     */             }
/* 1845:2677 */             return slot;
/* 1846:     */           }
/* 1847:     */         }
/* 1848:     */         Slot newSlot;
/* 1849:2680 */         newSlot.value = slot.value;
/* 1850:2681 */         newSlot.next = slot.next;
/* 1851:2683 */         if (this.lastAdded != null) {
/* 1852:2684 */           this.lastAdded.orderedNext = newSlot;
/* 1853:     */         }
/* 1854:2685 */         if (this.firstAdded == null) {
/* 1855:2686 */           this.firstAdded = newSlot;
/* 1856:     */         }
/* 1857:2687 */         this.lastAdded = newSlot;
/* 1858:2689 */         if (prev == slot) {
/* 1859:2690 */           slotsLocalRef[insertPos] = newSlot;
/* 1860:     */         } else {
/* 1861:2692 */           prev.next = newSlot;
/* 1862:     */         }
/* 1863:2695 */         slot.wasDeleted = true;
/* 1864:2696 */         slot.value = null;
/* 1865:2697 */         slot.name = null;
/* 1866:2698 */         return newSlot;
/* 1867:     */       }
/* 1868:2701 */       if (4 * (this.count + 1) > 3 * slotsLocalRef.length)
/* 1869:     */       {
/* 1870:2703 */         slotsLocalRef = new Slot[slotsLocalRef.length * 2];
/* 1871:2704 */         copyTable(this.slots, slotsLocalRef, this.count);
/* 1872:2705 */         this.slots = slotsLocalRef;
/* 1873:2706 */         insertPos = getSlotIndex(slotsLocalRef.length, indexOrHash);
/* 1874:     */       }
/* 1875:     */     }
/* 1876:2711 */     Slot newSlot = accessType == 4 ? new GetterSlot(name, indexOrHash, 0) : new Slot(name, indexOrHash, 0);
/* 1877:2714 */     if (accessType == 3) {
/* 1878:2715 */       newSlot.setAttributes(13);
/* 1879:     */     }
/* 1880:2716 */     this.count += 1;
/* 1881:2718 */     if (this.lastAdded != null) {
/* 1882:2719 */       this.lastAdded.orderedNext = newSlot;
/* 1883:     */     }
/* 1884:2720 */     if (this.firstAdded == null) {
/* 1885:2721 */       this.firstAdded = newSlot;
/* 1886:     */     }
/* 1887:2722 */     this.lastAdded = newSlot;
/* 1888:     */     
/* 1889:2724 */     addKnownAbsentSlot(slotsLocalRef, newSlot, insertPos);
/* 1890:2725 */     return newSlot;
/* 1891:     */   }
/* 1892:     */   
/* 1893:     */   private synchronized void removeSlot(String name, int index)
/* 1894:     */   {
/* 1895:2729 */     int indexOrHash = name != null ? name.hashCode() : index;
/* 1896:     */     
/* 1897:2731 */     Slot[] slotsLocalRef = this.slots;
/* 1898:2732 */     if (this.count != 0)
/* 1899:     */     {
/* 1900:2733 */       int tableSize = this.slots.length;
/* 1901:2734 */       int slotIndex = getSlotIndex(tableSize, indexOrHash);
/* 1902:2735 */       Slot prev = slotsLocalRef[slotIndex];
/* 1903:2736 */       Slot slot = prev;
/* 1904:2737 */       while ((slot != null) && (
/* 1905:2738 */         (slot.indexOrHash != indexOrHash) || ((slot.name != name) && ((name == null) || (!name.equals(slot.name))))))
/* 1906:     */       {
/* 1907:2744 */         prev = slot;
/* 1908:2745 */         slot = slot.next;
/* 1909:     */       }
/* 1910:2747 */       if ((slot != null) && ((slot.getAttributes() & 0x4) == 0))
/* 1911:     */       {
/* 1912:2748 */         this.count -= 1;
/* 1913:2750 */         if (prev == slot) {
/* 1914:2751 */           slotsLocalRef[slotIndex] = slot.next;
/* 1915:     */         } else {
/* 1916:2753 */           prev.next = slot.next;
/* 1917:     */         }
/* 1918:2758 */         slot.wasDeleted = true;
/* 1919:2759 */         slot.value = null;
/* 1920:2760 */         slot.name = null;
/* 1921:     */       }
/* 1922:     */     }
/* 1923:     */   }
/* 1924:     */   
/* 1925:     */   private static int getSlotIndex(int tableSize, int indexOrHash)
/* 1926:     */   {
/* 1927:2768 */     return indexOrHash & tableSize - 1;
/* 1928:     */   }
/* 1929:     */   
/* 1930:     */   private static void copyTable(Slot[] slots, Slot[] newSlots, int count)
/* 1931:     */   {
/* 1932:2774 */     if (count == 0) {
/* 1933:2774 */       throw Kit.codeBug();
/* 1934:     */     }
/* 1935:2776 */     int tableSize = newSlots.length;
/* 1936:2777 */     int i = slots.length;
/* 1937:     */     for (;;)
/* 1938:     */     {
/* 1939:2779 */       i--;
/* 1940:2780 */       Slot slot = slots[i];
/* 1941:2781 */       while (slot != null)
/* 1942:     */       {
/* 1943:2782 */         int insertPos = getSlotIndex(tableSize, slot.indexOrHash);
/* 1944:2783 */         Slot next = slot.next;
/* 1945:2784 */         addKnownAbsentSlot(newSlots, slot, insertPos);
/* 1946:2785 */         slot.next = null;
/* 1947:2786 */         slot = next;
/* 1948:2787 */         count--;
/* 1949:2787 */         if (count == 0) {
/* 1950:2788 */           return;
/* 1951:     */         }
/* 1952:     */       }
/* 1953:     */     }
/* 1954:     */   }
/* 1955:     */   
/* 1956:     */   private static void addKnownAbsentSlot(Slot[] slots, Slot slot, int insertPos)
/* 1957:     */   {
/* 1958:2801 */     if (slots[insertPos] == null)
/* 1959:     */     {
/* 1960:2802 */       slots[insertPos] = slot;
/* 1961:     */     }
/* 1962:     */     else
/* 1963:     */     {
/* 1964:2804 */       Slot prev = slots[insertPos];
/* 1965:2805 */       while (prev.next != null) {
/* 1966:2806 */         prev = prev.next;
/* 1967:     */       }
/* 1968:2808 */       prev.next = slot;
/* 1969:     */     }
/* 1970:     */   }
/* 1971:     */   
/* 1972:     */   Object[] getIds(boolean getAll)
/* 1973:     */   {
/* 1974:2813 */     Slot[] s = this.slots;
/* 1975:2814 */     Object[] a = ScriptRuntime.emptyArgs;
/* 1976:2815 */     if (s == null) {
/* 1977:2816 */       return a;
/* 1978:     */     }
/* 1979:2817 */     int c = 0;
/* 1980:2818 */     Slot slot = this.firstAdded;
/* 1981:2819 */     while ((slot != null) && (slot.wasDeleted)) {
/* 1982:2822 */       slot = slot.orderedNext;
/* 1983:     */     }
/* 1984:2824 */     this.firstAdded = slot;
/* 1985:2825 */     if (slot != null) {
/* 1986:     */       for (;;)
/* 1987:     */       {
/* 1988:2827 */         if ((getAll) || ((slot.getAttributes() & 0x2) == 0))
/* 1989:     */         {
/* 1990:2828 */           if (c == 0) {
/* 1991:2829 */             a = new Object[s.length];
/* 1992:     */           }
/* 1993:2830 */           a[(c++)] = (slot.name != null ? slot.name : Integer.valueOf(slot.indexOrHash));
/* 1994:     */         }
/* 1995:2834 */         Slot next = slot.orderedNext;
/* 1996:2835 */         while ((next != null) && (next.wasDeleted)) {
/* 1997:2837 */           next = next.orderedNext;
/* 1998:     */         }
/* 1999:2839 */         slot.orderedNext = next;
/* 2000:2840 */         if (next == null) {
/* 2001:     */           break;
/* 2002:     */         }
/* 2003:2843 */         slot = next;
/* 2004:     */       }
/* 2005:     */     }
/* 2006:2846 */     this.lastAdded = slot;
/* 2007:2847 */     if (c == a.length) {
/* 2008:2848 */       return a;
/* 2009:     */     }
/* 2010:2849 */     Object[] result = new Object[c];
/* 2011:2850 */     System.arraycopy(a, 0, result, 0, c);
/* 2012:2851 */     return result;
/* 2013:     */   }
/* 2014:     */   
/* 2015:     */   private synchronized void writeObject(ObjectOutputStream out)
/* 2016:     */     throws IOException
/* 2017:     */   {
/* 2018:2857 */     out.defaultWriteObject();
/* 2019:2858 */     int objectsCount = this.count;
/* 2020:2859 */     if (objectsCount < 0) {
/* 2021:2861 */       objectsCount ^= 0xFFFFFFFF;
/* 2022:     */     }
/* 2023:2863 */     if (objectsCount == 0)
/* 2024:     */     {
/* 2025:2864 */       out.writeInt(0);
/* 2026:     */     }
/* 2027:     */     else
/* 2028:     */     {
/* 2029:2866 */       out.writeInt(this.slots.length);
/* 2030:2867 */       Slot slot = this.firstAdded;
/* 2031:2868 */       while ((slot != null) && (slot.wasDeleted)) {
/* 2032:2871 */         slot = slot.orderedNext;
/* 2033:     */       }
/* 2034:2873 */       this.firstAdded = slot;
/* 2035:2874 */       while (slot != null)
/* 2036:     */       {
/* 2037:2875 */         out.writeObject(slot);
/* 2038:2876 */         Slot next = slot.orderedNext;
/* 2039:2877 */         while ((next != null) && (next.wasDeleted)) {
/* 2040:2879 */           next = next.orderedNext;
/* 2041:     */         }
/* 2042:2881 */         slot.orderedNext = next;
/* 2043:2882 */         slot = next;
/* 2044:     */       }
/* 2045:     */     }
/* 2046:     */   }
/* 2047:     */   
/* 2048:     */   private void readObject(ObjectInputStream in)
/* 2049:     */     throws IOException, ClassNotFoundException
/* 2050:     */   {
/* 2051:2890 */     in.defaultReadObject();
/* 2052:     */     
/* 2053:2892 */     int tableSize = in.readInt();
/* 2054:2893 */     if (tableSize != 0)
/* 2055:     */     {
/* 2056:2896 */       if ((tableSize & tableSize - 1) != 0)
/* 2057:     */       {
/* 2058:2897 */         if (tableSize > 1073741824) {
/* 2059:2898 */           throw new RuntimeException("Property table overflow");
/* 2060:     */         }
/* 2061:2899 */         int newSize = 4;
/* 2062:2900 */         while (newSize < tableSize) {
/* 2063:2901 */           newSize <<= 1;
/* 2064:     */         }
/* 2065:2902 */         tableSize = newSize;
/* 2066:     */       }
/* 2067:2904 */       this.slots = new Slot[tableSize];
/* 2068:2905 */       int objectsCount = this.count;
/* 2069:2906 */       if (objectsCount < 0) {
/* 2070:2908 */         objectsCount ^= 0xFFFFFFFF;
/* 2071:     */       }
/* 2072:2910 */       Slot prev = null;
/* 2073:2911 */       for (int i = 0; i != objectsCount; i++)
/* 2074:     */       {
/* 2075:2912 */         this.lastAdded = ((Slot)in.readObject());
/* 2076:2913 */         if (i == 0) {
/* 2077:2914 */           this.firstAdded = this.lastAdded;
/* 2078:     */         } else {
/* 2079:2916 */           prev.orderedNext = this.lastAdded;
/* 2080:     */         }
/* 2081:2918 */         int slotIndex = getSlotIndex(tableSize, this.lastAdded.indexOrHash);
/* 2082:2919 */         addKnownAbsentSlot(this.slots, this.lastAdded, slotIndex);
/* 2083:2920 */         prev = this.lastAdded;
/* 2084:     */       }
/* 2085:     */     }
/* 2086:     */   }
/* 2087:     */   
/* 2088:     */   protected ScriptableObject getOwnPropertyDescriptor(Context cx, Object id)
/* 2089:     */   {
/* 2090:2926 */     Slot slot = getSlot(cx, id, 1);
/* 2091:2927 */     if (slot == null) {
/* 2092:2927 */       return null;
/* 2093:     */     }
/* 2094:2928 */     Scriptable scope = getParentScope();
/* 2095:2929 */     return slot.getPropertyDescriptor(cx, scope == null ? this : scope);
/* 2096:     */   }
/* 2097:     */   
/* 2098:     */   protected Slot getSlot(Context cx, Object id, int accessType)
/* 2099:     */   {
/* 2100:2934 */     String name = ScriptRuntime.toStringIdOrIndex(cx, id);
/* 2101:     */     Slot slot;
/* 2102:     */     Slot slot;
/* 2103:2935 */     if (name == null)
/* 2104:     */     {
/* 2105:2936 */       int index = ScriptRuntime.lastIndexResult(cx);
/* 2106:2937 */       slot = getSlot(null, index, accessType);
/* 2107:     */     }
/* 2108:     */     else
/* 2109:     */     {
/* 2110:2939 */       slot = getSlot(name, 0, accessType);
/* 2111:     */     }
/* 2112:2941 */     return slot;
/* 2113:     */   }
/* 2114:     */   
/* 2115:     */   public int size()
/* 2116:     */   {
/* 2117:2948 */     return this.count;
/* 2118:     */   }
/* 2119:     */   
/* 2120:     */   public boolean isEmpty()
/* 2121:     */   {
/* 2122:2952 */     return this.count == 0;
/* 2123:     */   }
/* 2124:     */   
/* 2125:     */   public Object get(Object key)
/* 2126:     */   {
/* 2127:2957 */     Object value = null;
/* 2128:2958 */     if ((key instanceof String)) {
/* 2129:2959 */       value = get((String)key, this);
/* 2130:2960 */     } else if ((key instanceof Number)) {
/* 2131:2961 */       value = get(((Number)key).intValue(), this);
/* 2132:     */     }
/* 2133:2963 */     if ((value == Scriptable.NOT_FOUND) || (value == Undefined.instance)) {
/* 2134:2964 */       return null;
/* 2135:     */     }
/* 2136:2965 */     if ((value instanceof Wrapper)) {
/* 2137:2966 */       return ((Wrapper)value).unwrap();
/* 2138:     */     }
/* 2139:2968 */     return value;
/* 2140:     */   }
/* 2141:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ScriptableObject
 * JD-Core Version:    0.7.0.1
 */