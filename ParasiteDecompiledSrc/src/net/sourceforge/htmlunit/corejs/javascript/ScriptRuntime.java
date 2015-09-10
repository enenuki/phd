/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.io.Serializable;
/*    5:     */ import java.lang.reflect.Constructor;
/*    6:     */ import java.text.MessageFormat;
/*    7:     */ import java.util.Locale;
/*    8:     */ import java.util.MissingResourceException;
/*    9:     */ import java.util.ResourceBundle;
/*   10:     */ import net.sourceforge.htmlunit.corejs.javascript.xml.XMLLib;
/*   11:     */ import net.sourceforge.htmlunit.corejs.javascript.xml.XMLLib.Factory;
/*   12:     */ import net.sourceforge.htmlunit.corejs.javascript.xml.XMLObject;
/*   13:     */ 
/*   14:     */ public class ScriptRuntime
/*   15:     */ {
/*   16:     */   public static BaseFunction typeErrorThrower()
/*   17:     */   {
/*   18:  82 */     if (THROW_TYPE_ERROR == null)
/*   19:     */     {
/*   20:  83 */       BaseFunction thrower = new BaseFunction()
/*   21:     */       {
/*   22:     */         public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*   23:     */         {
/*   24:  86 */           throw ScriptRuntime.typeError0("msg.op.not.allowed");
/*   25:     */         }
/*   26:     */         
/*   27:     */         public int getLength()
/*   28:     */         {
/*   29:  90 */           return 0;
/*   30:     */         }
/*   31:  92 */       };
/*   32:  93 */       thrower.preventExtensions();
/*   33:  94 */       THROW_TYPE_ERROR = thrower;
/*   34:     */     }
/*   35:  96 */     return THROW_TYPE_ERROR;
/*   36:     */   }
/*   37:     */   
/*   38:  98 */   private static BaseFunction THROW_TYPE_ERROR = null;
/*   39:     */   
/*   40:     */   static class NoSuchMethodShim
/*   41:     */     implements Callable
/*   42:     */   {
/*   43:     */     String methodName;
/*   44:     */     Callable noSuchMethodMethod;
/*   45:     */     
/*   46:     */     NoSuchMethodShim(Callable noSuchMethodMethod, String methodName)
/*   47:     */     {
/*   48: 106 */       this.noSuchMethodMethod = noSuchMethodMethod;
/*   49: 107 */       this.methodName = methodName;
/*   50:     */     }
/*   51:     */     
/*   52:     */     public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*   53:     */     {
/*   54: 121 */       Object[] nestedArgs = new Object[2];
/*   55:     */       
/*   56: 123 */       nestedArgs[0] = this.methodName;
/*   57: 124 */       nestedArgs[1] = ScriptRuntime.newArrayLiteral(args, null, cx, scope);
/*   58: 125 */       return this.noSuchMethodMethod.call(cx, scope, thisObj, nestedArgs);
/*   59:     */     }
/*   60:     */   }
/*   61:     */   
/*   62: 140 */   public static final Class<?> BooleanClass = Kit.classOrNull("java.lang.Boolean");
/*   63: 141 */   public static final Class<?> ByteClass = Kit.classOrNull("java.lang.Byte");
/*   64: 142 */   public static final Class<?> CharacterClass = Kit.classOrNull("java.lang.Character");
/*   65: 143 */   public static final Class<?> ClassClass = Kit.classOrNull("java.lang.Class");
/*   66: 144 */   public static final Class<?> DoubleClass = Kit.classOrNull("java.lang.Double");
/*   67: 145 */   public static final Class<?> FloatClass = Kit.classOrNull("java.lang.Float");
/*   68: 146 */   public static final Class<?> IntegerClass = Kit.classOrNull("java.lang.Integer");
/*   69: 147 */   public static final Class<?> LongClass = Kit.classOrNull("java.lang.Long");
/*   70: 148 */   public static final Class<?> NumberClass = Kit.classOrNull("java.lang.Number");
/*   71: 149 */   public static final Class<?> ObjectClass = Kit.classOrNull("java.lang.Object");
/*   72: 150 */   public static final Class<?> ShortClass = Kit.classOrNull("java.lang.Short");
/*   73: 151 */   public static final Class<?> StringClass = Kit.classOrNull("java.lang.String");
/*   74: 152 */   public static final Class<?> DateClass = Kit.classOrNull("java.util.Date");
/*   75: 155 */   public static final Class<?> ContextClass = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.Context");
/*   76: 157 */   public static final Class<?> ContextFactoryClass = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.ContextFactory");
/*   77: 159 */   public static final Class<?> FunctionClass = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.Function");
/*   78: 161 */   public static final Class<?> ScriptableObjectClass = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.ScriptableObject");
/*   79: 163 */   public static final Class<Scriptable> ScriptableClass = Scriptable.class;
/*   80: 166 */   private static final String[] lazilyNames = { "RegExp", "net.sourceforge.htmlunit.corejs.javascript.regexp.NativeRegExp", "Packages", "net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage", "java", "net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage", "javax", "net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage", "org", "net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage", "com", "net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage", "edu", "net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage", "net", "net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage", "getClass", "net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage", "JavaAdapter", "net.sourceforge.htmlunit.corejs.javascript.JavaAdapter", "JavaImporter", "net.sourceforge.htmlunit.corejs.javascript.ImporterTopLevel", "Continuation", "net.sourceforge.htmlunit.corejs.javascript.NativeContinuation", "XML", "(xml)", "XMLList", "(xml)", "Namespace", "(xml)", "QName", "(xml)" };
/*   81: 188 */   public static Locale ROOT_LOCALE = new Locale("");
/*   82: 190 */   private static final Object LIBRARY_SCOPE_KEY = "LIBRARY_SCOPE";
/*   83:     */   
/*   84:     */   public static boolean isRhinoRuntimeType(Class<?> cl)
/*   85:     */   {
/*   86: 194 */     if (cl.isPrimitive()) {
/*   87: 195 */       return cl != Character.TYPE;
/*   88:     */     }
/*   89: 197 */     return (cl == StringClass) || (cl == BooleanClass) || (NumberClass.isAssignableFrom(cl)) || (ScriptableClass.isAssignableFrom(cl));
/*   90:     */   }
/*   91:     */   
/*   92:     */   public static ScriptableObject initStandardObjects(Context cx, ScriptableObject scope, boolean sealed)
/*   93:     */   {
/*   94: 207 */     if (scope == null) {
/*   95: 208 */       scope = new NativeObject();
/*   96:     */     }
/*   97: 210 */     scope.associateValue(LIBRARY_SCOPE_KEY, scope);
/*   98: 211 */     new ClassCache().associate(scope);
/*   99:     */     
/*  100: 213 */     BaseFunction.init(scope, sealed);
/*  101: 214 */     NativeObject.init(scope, sealed);
/*  102:     */     
/*  103: 216 */     Scriptable objectProto = ScriptableObject.getObjectPrototype(scope);
/*  104:     */     
/*  105:     */ 
/*  106: 219 */     Scriptable functionProto = ScriptableObject.getClassPrototype(scope, "Function");
/*  107: 220 */     functionProto.setPrototype(objectProto);
/*  108: 223 */     if (scope.getPrototype() == null) {
/*  109: 224 */       scope.setPrototype(objectProto);
/*  110:     */     }
/*  111: 227 */     NativeError.init(scope, sealed);
/*  112: 228 */     NativeGlobal.init(cx, scope, sealed);
/*  113:     */     
/*  114: 230 */     NativeArray.init(scope, sealed);
/*  115: 231 */     if (cx.getOptimizationLevel() > 0) {
/*  116: 235 */       NativeArray.setMaximumInitialCapacity(200000);
/*  117:     */     }
/*  118: 237 */     NativeString.init(scope, sealed);
/*  119: 238 */     NativeBoolean.init(scope, sealed);
/*  120: 239 */     NativeNumber.init(scope, sealed);
/*  121: 240 */     NativeDate.init(scope, sealed);
/*  122: 241 */     NativeMath.init(scope, sealed);
/*  123: 242 */     NativeJSON.init(scope, sealed);
/*  124:     */     
/*  125: 244 */     NativeWith.init(scope, sealed);
/*  126: 245 */     NativeCall.init(scope, sealed);
/*  127: 246 */     NativeScript.init(scope, sealed);
/*  128:     */     
/*  129: 248 */     NativeIterator.init(scope, sealed);
/*  130:     */     
/*  131: 250 */     boolean withXml = (cx.hasFeature(6)) && (cx.getE4xImplementationFactory() != null);
/*  132: 253 */     for (int i = 0; i != lazilyNames.length; i += 2)
/*  133:     */     {
/*  134: 254 */       String topProperty = lazilyNames[i];
/*  135: 255 */       String className = lazilyNames[(i + 1)];
/*  136: 256 */       if ((withXml) || (!className.equals("(xml)")))
/*  137:     */       {
/*  138: 258 */         if ((withXml) && (className.equals("(xml)"))) {
/*  139: 259 */           className = cx.getE4xImplementationFactory().getImplementationClassName();
/*  140:     */         }
/*  141: 262 */         new LazilyLoadedCtor(scope, topProperty, className, sealed, true);
/*  142:     */       }
/*  143:     */     }
/*  144: 265 */     if ((scope instanceof TopLevel)) {
/*  145: 266 */       ((TopLevel)scope).cacheBuiltins();
/*  146:     */     }
/*  147: 269 */     return scope;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public static ScriptableObject getLibraryScopeOrNull(Scriptable scope)
/*  151:     */   {
/*  152: 275 */     ScriptableObject libScope = (ScriptableObject)ScriptableObject.getTopScopeValue(scope, LIBRARY_SCOPE_KEY);
/*  153:     */     
/*  154: 277 */     return libScope;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public static boolean isJSLineTerminator(int c)
/*  158:     */   {
/*  159: 285 */     if ((c & 0xDFD0) != 0) {
/*  160: 286 */       return false;
/*  161:     */     }
/*  162: 288 */     return (c == 10) || (c == 13) || (c == 8232) || (c == 8233);
/*  163:     */   }
/*  164:     */   
/*  165:     */   public static boolean isJSWhitespaceOrLineTerminator(int c)
/*  166:     */   {
/*  167: 292 */     return (isStrWhiteSpaceChar(c)) || (isJSLineTerminator(c));
/*  168:     */   }
/*  169:     */   
/*  170:     */   static boolean isStrWhiteSpaceChar(int c)
/*  171:     */   {
/*  172: 312 */     switch (c)
/*  173:     */     {
/*  174:     */     case 9: 
/*  175:     */     case 10: 
/*  176:     */     case 11: 
/*  177:     */     case 12: 
/*  178:     */     case 13: 
/*  179:     */     case 32: 
/*  180:     */     case 160: 
/*  181:     */     case 8232: 
/*  182:     */     case 8233: 
/*  183:     */     case 65279: 
/*  184: 323 */       return true;
/*  185:     */     }
/*  186: 325 */     return Character.getType(c) == 12;
/*  187:     */   }
/*  188:     */   
/*  189:     */   public static Boolean wrapBoolean(boolean b)
/*  190:     */   {
/*  191: 331 */     return b ? Boolean.TRUE : Boolean.FALSE;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public static Integer wrapInt(int i)
/*  195:     */   {
/*  196: 336 */     return Integer.valueOf(i);
/*  197:     */   }
/*  198:     */   
/*  199:     */   public static Number wrapNumber(double x)
/*  200:     */   {
/*  201: 341 */     if (x != x) {
/*  202: 342 */       return NaNobj;
/*  203:     */     }
/*  204: 344 */     return new Double(x);
/*  205:     */   }
/*  206:     */   
/*  207:     */   public static boolean toBoolean(Object val)
/*  208:     */   {
/*  209:     */     do
/*  210:     */     {
/*  211: 355 */       if ((val instanceof Boolean)) {
/*  212: 356 */         return ((Boolean)val).booleanValue();
/*  213:     */       }
/*  214: 357 */       if ((val == null) || (val == Undefined.instance)) {
/*  215: 358 */         return false;
/*  216:     */       }
/*  217: 359 */       if ((val instanceof String)) {
/*  218: 360 */         return ((String)val).length() != 0;
/*  219:     */       }
/*  220: 361 */       if ((val instanceof Number))
/*  221:     */       {
/*  222: 362 */         double d = ((Number)val).doubleValue();
/*  223: 363 */         return (d == d) && (d != 0.0D);
/*  224:     */       }
/*  225: 365 */       if (!(val instanceof Scriptable)) {
/*  226:     */         break;
/*  227:     */       }
/*  228: 366 */       if (((val instanceof ScriptableObject)) && (((ScriptableObject)val).avoidObjectDetection())) {
/*  229: 369 */         return false;
/*  230:     */       }
/*  231: 371 */       if (Context.getContext().isVersionECMA1()) {
/*  232: 373 */         return true;
/*  233:     */       }
/*  234: 376 */       val = ((Scriptable)val).getDefaultValue(BooleanClass);
/*  235: 377 */     } while (!(val instanceof Scriptable));
/*  236: 378 */     throw errorWithClassName("msg.primitive.expected", val);
/*  237:     */     
/*  238:     */ 
/*  239: 381 */     warnAboutNonJSObject(val);
/*  240: 382 */     return true;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public static double toNumber(Object val)
/*  244:     */   {
/*  245:     */     do
/*  246:     */     {
/*  247: 394 */       if ((val instanceof Number)) {
/*  248: 395 */         return ((Number)val).doubleValue();
/*  249:     */       }
/*  250: 396 */       if (val == null) {
/*  251: 397 */         return 0.0D;
/*  252:     */       }
/*  253: 398 */       if (val == Undefined.instance) {
/*  254: 399 */         return NaN;
/*  255:     */       }
/*  256: 400 */       if ((val instanceof String)) {
/*  257: 401 */         return toNumber((String)val);
/*  258:     */       }
/*  259: 402 */       if ((val instanceof Boolean)) {
/*  260: 403 */         return ((Boolean)val).booleanValue() ? 1.0D : 0.0D;
/*  261:     */       }
/*  262: 404 */       if (!(val instanceof Scriptable)) {
/*  263:     */         break;
/*  264:     */       }
/*  265: 405 */       val = ((Scriptable)val).getDefaultValue(NumberClass);
/*  266: 406 */     } while (!(val instanceof Scriptable));
/*  267: 407 */     throw errorWithClassName("msg.primitive.expected", val);
/*  268:     */     
/*  269:     */ 
/*  270: 410 */     warnAboutNonJSObject(val);
/*  271: 411 */     return NaN;
/*  272:     */   }
/*  273:     */   
/*  274:     */   public static double toNumber(Object[] args, int index)
/*  275:     */   {
/*  276: 416 */     return index < args.length ? toNumber(args[index]) : NaN;
/*  277:     */   }
/*  278:     */   
/*  279: 424 */   public static final double NaN = Double.longBitsToDouble(9221120237041090560L);
/*  280: 428 */   public static final double negativeZero = Double.longBitsToDouble(-9223372036854775808L);
/*  281: 430 */   public static final Double NaNobj = new Double(NaN);
/*  282:     */   private static final boolean MSJVM_BUG_WORKAROUNDS = true;
/*  283:     */   private static final String DEFAULT_NS_TAG = "__default_namespace__";
/*  284:     */   public static final int ENUMERATE_KEYS = 0;
/*  285:     */   public static final int ENUMERATE_VALUES = 1;
/*  286:     */   public static final int ENUMERATE_ARRAY = 2;
/*  287:     */   public static final int ENUMERATE_KEYS_NO_ITERATOR = 3;
/*  288:     */   public static final int ENUMERATE_VALUES_NO_ITERATOR = 4;
/*  289:     */   public static final int ENUMERATE_ARRAY_NO_ITERATOR = 5;
/*  290:     */   
/*  291:     */   static double stringToNumber(String s, int start, int radix)
/*  292:     */   {
/*  293: 436 */     char digitMax = '9';
/*  294: 437 */     char lowerCaseBound = 'a';
/*  295: 438 */     char upperCaseBound = 'A';
/*  296: 439 */     int len = s.length();
/*  297: 440 */     if (radix < 10) {
/*  298: 441 */       digitMax = (char)(48 + radix - 1);
/*  299:     */     }
/*  300: 443 */     if (radix > 10)
/*  301:     */     {
/*  302: 444 */       lowerCaseBound = (char)(97 + radix - 10);
/*  303: 445 */       upperCaseBound = (char)(65 + radix - 10);
/*  304:     */     }
/*  305: 448 */     double sum = 0.0D;
/*  306: 449 */     for (int end = start; end < len; end++)
/*  307:     */     {
/*  308: 450 */       char c = s.charAt(end);
/*  309:     */       int newDigit;
/*  310:     */       int newDigit;
/*  311: 452 */       if (('0' <= c) && (c <= digitMax))
/*  312:     */       {
/*  313: 453 */         newDigit = c - '0';
/*  314:     */       }
/*  315:     */       else
/*  316:     */       {
/*  317:     */         int newDigit;
/*  318: 454 */         if (('a' <= c) && (c < lowerCaseBound))
/*  319:     */         {
/*  320: 455 */           newDigit = c - 'a' + 10;
/*  321:     */         }
/*  322:     */         else
/*  323:     */         {
/*  324: 456 */           if (('A' > c) || (c >= upperCaseBound)) {
/*  325:     */             break;
/*  326:     */           }
/*  327: 457 */           newDigit = c - 'A' + 10;
/*  328:     */         }
/*  329:     */       }
/*  330: 460 */       sum = sum * radix + newDigit;
/*  331:     */     }
/*  332: 462 */     if (start == end) {
/*  333: 463 */       return NaN;
/*  334:     */     }
/*  335: 465 */     if (sum >= 9007199254740992.0D)
/*  336:     */     {
/*  337: 466 */       if (radix == 10) {
/*  338:     */         try
/*  339:     */         {
/*  340: 473 */           return Double.valueOf(s.substring(start, end)).doubleValue();
/*  341:     */         }
/*  342:     */         catch (NumberFormatException nfe)
/*  343:     */         {
/*  344: 475 */           return NaN;
/*  345:     */         }
/*  346:     */       }
/*  347: 477 */       if ((radix == 2) || (radix == 4) || (radix == 8) || (radix == 16) || (radix == 32))
/*  348:     */       {
/*  349: 490 */         int bitShiftInChar = 1;
/*  350: 491 */         int digit = 0;
/*  351:     */         
/*  352: 493 */         int SKIP_LEADING_ZEROS = 0;
/*  353: 494 */         int FIRST_EXACT_53_BITS = 1;
/*  354: 495 */         int AFTER_BIT_53 = 2;
/*  355: 496 */         int ZEROS_AFTER_54 = 3;
/*  356: 497 */         int MIXED_AFTER_54 = 4;
/*  357:     */         
/*  358: 499 */         int state = 0;
/*  359: 500 */         int exactBitsLimit = 53;
/*  360: 501 */         double factor = 0.0D;
/*  361: 502 */         boolean bit53 = false;
/*  362:     */         
/*  363: 504 */         boolean bit54 = false;
/*  364:     */         for (;;)
/*  365:     */         {
/*  366: 507 */           if (bitShiftInChar == 1)
/*  367:     */           {
/*  368: 508 */             if (start == end) {
/*  369:     */               break;
/*  370:     */             }
/*  371: 510 */             digit = s.charAt(start++);
/*  372: 511 */             if ((48 <= digit) && (digit <= 57)) {
/*  373: 512 */               digit -= 48;
/*  374: 513 */             } else if ((97 <= digit) && (digit <= 122)) {
/*  375: 514 */               digit -= 87;
/*  376:     */             } else {
/*  377: 516 */               digit -= 55;
/*  378:     */             }
/*  379: 517 */             bitShiftInChar = radix;
/*  380:     */           }
/*  381: 519 */           bitShiftInChar >>= 1;
/*  382: 520 */           boolean bit = (digit & bitShiftInChar) != 0;
/*  383: 522 */           switch (state)
/*  384:     */           {
/*  385:     */           case 0: 
/*  386: 524 */             if (bit)
/*  387:     */             {
/*  388: 525 */               exactBitsLimit--;
/*  389: 526 */               sum = 1.0D;
/*  390: 527 */               state = 1;
/*  391:     */             }
/*  392:     */             break;
/*  393:     */           case 1: 
/*  394: 531 */             sum *= 2.0D;
/*  395: 532 */             if (bit) {
/*  396: 533 */               sum += 1.0D;
/*  397:     */             }
/*  398: 534 */             exactBitsLimit--;
/*  399: 535 */             if (exactBitsLimit == 0)
/*  400:     */             {
/*  401: 536 */               bit53 = bit;
/*  402: 537 */               state = 2;
/*  403:     */             }
/*  404:     */             break;
/*  405:     */           case 2: 
/*  406: 541 */             bit54 = bit;
/*  407: 542 */             factor = 2.0D;
/*  408: 543 */             state = 3;
/*  409: 544 */             break;
/*  410:     */           case 3: 
/*  411: 546 */             if (bit) {
/*  412: 547 */               state = 4;
/*  413:     */             }
/*  414:     */           case 4: 
/*  415: 551 */             factor *= 2.0D;
/*  416:     */           }
/*  417:     */         }
/*  418: 555 */         switch (state)
/*  419:     */         {
/*  420:     */         case 0: 
/*  421: 557 */           sum = 0.0D;
/*  422: 558 */           break;
/*  423:     */         case 1: 
/*  424:     */         case 2: 
/*  425:     */           break;
/*  426:     */         case 3: 
/*  427: 566 */           if ((bit54 & bit53)) {
/*  428: 567 */             sum += 1.0D;
/*  429:     */           }
/*  430: 568 */           sum *= factor;
/*  431: 569 */           break;
/*  432:     */         case 4: 
/*  433: 573 */           if (bit54) {
/*  434: 574 */             sum += 1.0D;
/*  435:     */           }
/*  436: 575 */           sum *= factor;
/*  437:     */         }
/*  438:     */       }
/*  439:     */     }
/*  440: 581 */     return sum;
/*  441:     */   }
/*  442:     */   
/*  443:     */   public static double toNumber(String s)
/*  444:     */   {
/*  445: 591 */     int len = s.length();
/*  446: 592 */     int start = 0;
/*  447:     */     char startChar;
/*  448:     */     for (;;)
/*  449:     */     {
/*  450: 595 */       if (start == len) {
/*  451: 597 */         return 0.0D;
/*  452:     */       }
/*  453: 599 */       startChar = s.charAt(start);
/*  454: 600 */       if (!isStrWhiteSpaceChar(startChar)) {
/*  455:     */         break;
/*  456:     */       }
/*  457: 602 */       start++;
/*  458:     */     }
/*  459: 605 */     if (startChar == '0')
/*  460:     */     {
/*  461: 606 */       if (start + 2 < len)
/*  462:     */       {
/*  463: 607 */         int c1 = s.charAt(start + 1);
/*  464: 608 */         if ((c1 == 120) || (c1 == 88)) {
/*  465: 610 */           return stringToNumber(s, start + 2, 16);
/*  466:     */         }
/*  467:     */       }
/*  468:     */     }
/*  469: 613 */     else if (((startChar == '+') || (startChar == '-')) && 
/*  470: 614 */       (start + 3 < len) && (s.charAt(start + 1) == '0'))
/*  471:     */     {
/*  472: 615 */       int c2 = s.charAt(start + 2);
/*  473: 616 */       if ((c2 == 120) || (c2 == 88))
/*  474:     */       {
/*  475: 618 */         double val = stringToNumber(s, start + 3, 16);
/*  476: 619 */         return startChar == '-' ? -val : val;
/*  477:     */       }
/*  478:     */     }
/*  479: 624 */     int end = len - 1;
/*  480:     */     char endChar;
/*  481: 626 */     while (isStrWhiteSpaceChar(endChar = s.charAt(end))) {
/*  482: 627 */       end--;
/*  483:     */     }
/*  484: 628 */     if (endChar == 'y')
/*  485:     */     {
/*  486: 630 */       if ((startChar == '+') || (startChar == '-')) {
/*  487: 631 */         start++;
/*  488:     */       }
/*  489: 632 */       if ((start + 7 == end) && (s.regionMatches(start, "Infinity", 0, 8))) {
/*  490: 633 */         return startChar == '-' ? (-1.0D / 0.0D) : (1.0D / 0.0D);
/*  491:     */       }
/*  492: 636 */       return NaN;
/*  493:     */     }
/*  494: 640 */     String sub = s.substring(start, end + 1);
/*  495: 645 */     for (int i = sub.length() - 1; i >= 0; i--)
/*  496:     */     {
/*  497: 646 */       char c = sub.charAt(i);
/*  498: 647 */       if ((('0' > c) || (c > '9')) && (c != '.') && (c != 'e') && (c != 'E') && (c != '+') && (c != '-')) {
/*  499: 651 */         return NaN;
/*  500:     */       }
/*  501:     */     }
/*  502:     */     try
/*  503:     */     {
/*  504: 655 */       return Double.valueOf(sub).doubleValue();
/*  505:     */     }
/*  506:     */     catch (NumberFormatException ex) {}
/*  507: 657 */     return NaN;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public static Object[] padArguments(Object[] args, int count)
/*  511:     */   {
/*  512: 668 */     if (count < args.length) {
/*  513: 669 */       return args;
/*  514:     */     }
/*  515: 672 */     Object[] result = new Object[count];
/*  516: 673 */     for (int i = 0; i < args.length; i++) {
/*  517: 674 */       result[i] = args[i];
/*  518:     */     }
/*  519: 677 */     for (; i < count; i++) {
/*  520: 678 */       result[i] = Undefined.instance;
/*  521:     */     }
/*  522: 681 */     return result;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public static String escapeString(String s)
/*  526:     */   {
/*  527: 689 */     return escapeString(s, '"');
/*  528:     */   }
/*  529:     */   
/*  530:     */   public static String escapeString(String s, char escapeQuote)
/*  531:     */   {
/*  532: 698 */     if ((escapeQuote != '"') && (escapeQuote != '\'')) {
/*  533: 698 */       Kit.codeBug();
/*  534:     */     }
/*  535: 699 */     StringBuffer sb = null;
/*  536:     */     
/*  537: 701 */     int i = 0;
/*  538: 701 */     for (int L = s.length(); i != L; i++)
/*  539:     */     {
/*  540: 702 */       int c = s.charAt(i);
/*  541: 704 */       if ((32 <= c) && (c <= 126) && (c != escapeQuote) && (c != 92))
/*  542:     */       {
/*  543: 707 */         if (sb != null) {
/*  544: 708 */           sb.append((char)c);
/*  545:     */         }
/*  546:     */       }
/*  547:     */       else
/*  548:     */       {
/*  549: 712 */         if (sb == null)
/*  550:     */         {
/*  551: 713 */           sb = new StringBuffer(L + 3);
/*  552: 714 */           sb.append(s);
/*  553: 715 */           sb.setLength(i);
/*  554:     */         }
/*  555: 718 */         int escape = -1;
/*  556: 719 */         switch (c)
/*  557:     */         {
/*  558:     */         case 8: 
/*  559: 720 */           escape = 98; break;
/*  560:     */         case 12: 
/*  561: 721 */           escape = 102; break;
/*  562:     */         case 10: 
/*  563: 722 */           escape = 110; break;
/*  564:     */         case 13: 
/*  565: 723 */           escape = 114; break;
/*  566:     */         case 9: 
/*  567: 724 */           escape = 116; break;
/*  568:     */         case 11: 
/*  569: 725 */           escape = 118; break;
/*  570:     */         case 32: 
/*  571: 726 */           escape = 32; break;
/*  572:     */         case 92: 
/*  573: 727 */           escape = 92;
/*  574:     */         }
/*  575: 729 */         if (escape >= 0)
/*  576:     */         {
/*  577: 731 */           sb.append('\\');
/*  578: 732 */           sb.append((char)escape);
/*  579:     */         }
/*  580: 733 */         else if (c == escapeQuote)
/*  581:     */         {
/*  582: 734 */           sb.append('\\');
/*  583: 735 */           sb.append(escapeQuote);
/*  584:     */         }
/*  585:     */         else
/*  586:     */         {
/*  587:     */           int hexSize;
/*  588:     */           int hexSize;
/*  589: 738 */           if (c < 256)
/*  590:     */           {
/*  591: 740 */             sb.append("\\x");
/*  592: 741 */             hexSize = 2;
/*  593:     */           }
/*  594:     */           else
/*  595:     */           {
/*  596: 744 */             sb.append("\\u");
/*  597: 745 */             hexSize = 4;
/*  598:     */           }
/*  599: 748 */           for (int shift = (hexSize - 1) * 4; shift >= 0; shift -= 4)
/*  600:     */           {
/*  601: 749 */             int digit = 0xF & c >> shift;
/*  602: 750 */             int hc = digit < 10 ? 48 + digit : 87 + digit;
/*  603: 751 */             sb.append((char)hc);
/*  604:     */           }
/*  605:     */         }
/*  606:     */       }
/*  607:     */     }
/*  608: 755 */     return sb == null ? s : sb.toString();
/*  609:     */   }
/*  610:     */   
/*  611:     */   static boolean isValidIdentifierName(String s)
/*  612:     */   {
/*  613: 760 */     int L = s.length();
/*  614: 761 */     if (L == 0) {
/*  615: 762 */       return false;
/*  616:     */     }
/*  617: 763 */     if (!Character.isJavaIdentifierStart(s.charAt(0))) {
/*  618: 764 */       return false;
/*  619:     */     }
/*  620: 765 */     for (int i = 1; i != L; i++) {
/*  621: 766 */       if (!Character.isJavaIdentifierPart(s.charAt(i))) {
/*  622: 767 */         return false;
/*  623:     */       }
/*  624:     */     }
/*  625: 769 */     return !TokenStream.isKeyword(s);
/*  626:     */   }
/*  627:     */   
/*  628:     */   public static String toString(Object val)
/*  629:     */   {
/*  630:     */     do
/*  631:     */     {
/*  632: 779 */       if (val == null) {
/*  633: 780 */         return "null";
/*  634:     */       }
/*  635: 782 */       if (val == Undefined.instance) {
/*  636: 783 */         return "undefined";
/*  637:     */       }
/*  638: 785 */       if ((val instanceof String)) {
/*  639: 786 */         return (String)val;
/*  640:     */       }
/*  641: 788 */       if ((val instanceof Number)) {
/*  642: 791 */         return numberToString(((Number)val).doubleValue(), 10);
/*  643:     */       }
/*  644: 793 */       if (!(val instanceof Scriptable)) {
/*  645:     */         break;
/*  646:     */       }
/*  647: 794 */       val = ((Scriptable)val).getDefaultValue(StringClass);
/*  648: 795 */     } while (!(val instanceof Scriptable));
/*  649: 796 */     throw errorWithClassName("msg.primitive.expected", val);
/*  650:     */     
/*  651:     */ 
/*  652:     */ 
/*  653: 800 */     return val.toString();
/*  654:     */   }
/*  655:     */   
/*  656:     */   static String defaultObjectToString(Scriptable obj)
/*  657:     */   {
/*  658: 806 */     return "[object " + obj.getClassName() + ']';
/*  659:     */   }
/*  660:     */   
/*  661:     */   public static String toString(Object[] args, int index)
/*  662:     */   {
/*  663: 811 */     return index < args.length ? toString(args[index]) : "undefined";
/*  664:     */   }
/*  665:     */   
/*  666:     */   public static String toString(double val)
/*  667:     */   {
/*  668: 818 */     return numberToString(val, 10);
/*  669:     */   }
/*  670:     */   
/*  671:     */   public static String numberToString(double d, int base)
/*  672:     */   {
/*  673: 822 */     if (d != d) {
/*  674: 823 */       return "NaN";
/*  675:     */     }
/*  676: 824 */     if (d == (1.0D / 0.0D)) {
/*  677: 825 */       return "Infinity";
/*  678:     */     }
/*  679: 826 */     if (d == (-1.0D / 0.0D)) {
/*  680: 827 */       return "-Infinity";
/*  681:     */     }
/*  682: 828 */     if (d == 0.0D) {
/*  683: 829 */       return "0";
/*  684:     */     }
/*  685: 831 */     if ((base < 2) || (base > 36)) {
/*  686: 832 */       throw Context.reportRuntimeError1("msg.bad.radix", Integer.toString(base));
/*  687:     */     }
/*  688: 836 */     if (base != 10) {
/*  689: 837 */       return DToA.JS_dtobasestr(base, d);
/*  690:     */     }
/*  691: 839 */     StringBuffer result = new StringBuffer();
/*  692: 840 */     DToA.JS_dtostr(result, 0, 0, d);
/*  693: 841 */     return result.toString();
/*  694:     */   }
/*  695:     */   
/*  696:     */   static String uneval(Context cx, Scriptable scope, Object value)
/*  697:     */   {
/*  698: 848 */     if (value == null) {
/*  699: 849 */       return "null";
/*  700:     */     }
/*  701: 851 */     if (value == Undefined.instance) {
/*  702: 852 */       return "undefined";
/*  703:     */     }
/*  704: 854 */     if ((value instanceof String))
/*  705:     */     {
/*  706: 855 */       String escaped = escapeString((String)value);
/*  707: 856 */       StringBuffer sb = new StringBuffer(escaped.length() + 2);
/*  708: 857 */       sb.append('"');
/*  709: 858 */       sb.append(escaped);
/*  710: 859 */       sb.append('"');
/*  711: 860 */       return sb.toString();
/*  712:     */     }
/*  713: 862 */     if ((value instanceof Number))
/*  714:     */     {
/*  715: 863 */       double d = ((Number)value).doubleValue();
/*  716: 864 */       if ((d == 0.0D) && (1.0D / d < 0.0D)) {
/*  717: 865 */         return "-0";
/*  718:     */       }
/*  719: 867 */       return toString(d);
/*  720:     */     }
/*  721: 869 */     if ((value instanceof Boolean)) {
/*  722: 870 */       return toString(value);
/*  723:     */     }
/*  724: 872 */     if ((value instanceof Scriptable))
/*  725:     */     {
/*  726: 873 */       Scriptable obj = (Scriptable)value;
/*  727: 876 */       if (ScriptableObject.hasProperty(obj, "toSource"))
/*  728:     */       {
/*  729: 877 */         Object v = ScriptableObject.getProperty(obj, "toSource");
/*  730: 878 */         if ((v instanceof Function))
/*  731:     */         {
/*  732: 879 */           Function f = (Function)v;
/*  733: 880 */           return toString(f.call(cx, scope, obj, emptyArgs));
/*  734:     */         }
/*  735:     */       }
/*  736: 883 */       return toString(value);
/*  737:     */     }
/*  738: 885 */     warnAboutNonJSObject(value);
/*  739: 886 */     return value.toString();
/*  740:     */   }
/*  741:     */   
/*  742:     */   static String defaultObjectToSource(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  743:     */   {
/*  744:     */     boolean toplevel;
/*  745:     */     boolean iterating;
/*  746: 893 */     if (cx.iterating == null)
/*  747:     */     {
/*  748: 894 */       boolean toplevel = true;
/*  749: 895 */       boolean iterating = false;
/*  750: 896 */       cx.iterating = new ObjToIntMap(31);
/*  751:     */     }
/*  752:     */     else
/*  753:     */     {
/*  754: 898 */       toplevel = false;
/*  755: 899 */       iterating = cx.iterating.has(thisObj);
/*  756:     */     }
/*  757: 902 */     StringBuffer result = new StringBuffer(128);
/*  758: 903 */     if (toplevel) {
/*  759: 904 */       result.append("(");
/*  760:     */     }
/*  761: 906 */     result.append('{');
/*  762:     */     try
/*  763:     */     {
/*  764: 911 */       if (!iterating)
/*  765:     */       {
/*  766: 912 */         cx.iterating.intern(thisObj);
/*  767: 913 */         Object[] ids = thisObj.getIds();
/*  768: 914 */         for (int i = 0; i < ids.length; i++)
/*  769:     */         {
/*  770: 915 */           Object id = ids[i];
/*  771:     */           Object value;
/*  772: 917 */           if ((id instanceof Integer))
/*  773:     */           {
/*  774: 918 */             int intId = ((Integer)id).intValue();
/*  775: 919 */             Object value = thisObj.get(intId, thisObj);
/*  776: 920 */             if (value == Scriptable.NOT_FOUND) {
/*  777:     */               continue;
/*  778:     */             }
/*  779: 922 */             if (i > 0) {
/*  780: 923 */               result.append(", ");
/*  781:     */             }
/*  782: 924 */             result.append(intId);
/*  783:     */           }
/*  784:     */           else
/*  785:     */           {
/*  786: 926 */             String strId = (String)id;
/*  787: 927 */             value = thisObj.get(strId, thisObj);
/*  788: 928 */             if (value == Scriptable.NOT_FOUND) {
/*  789:     */               continue;
/*  790:     */             }
/*  791: 930 */             if (i > 0) {
/*  792: 931 */               result.append(", ");
/*  793:     */             }
/*  794: 932 */             if (isValidIdentifierName(strId))
/*  795:     */             {
/*  796: 933 */               result.append(strId);
/*  797:     */             }
/*  798:     */             else
/*  799:     */             {
/*  800: 935 */               result.append('\'');
/*  801: 936 */               result.append(escapeString(strId, '\''));
/*  802:     */               
/*  803: 938 */               result.append('\'');
/*  804:     */             }
/*  805:     */           }
/*  806: 941 */           result.append(':');
/*  807: 942 */           result.append(uneval(cx, scope, value));
/*  808:     */         }
/*  809:     */       }
/*  810:     */     }
/*  811:     */     finally
/*  812:     */     {
/*  813: 946 */       if (toplevel) {
/*  814: 947 */         cx.iterating = null;
/*  815:     */       }
/*  816:     */     }
/*  817: 951 */     result.append('}');
/*  818: 952 */     if (toplevel) {
/*  819: 953 */       result.append(')');
/*  820:     */     }
/*  821: 955 */     return result.toString();
/*  822:     */   }
/*  823:     */   
/*  824:     */   public static Scriptable toObject(Scriptable scope, Object val)
/*  825:     */   {
/*  826: 960 */     if ((val instanceof Scriptable)) {
/*  827: 961 */       return (Scriptable)val;
/*  828:     */     }
/*  829: 963 */     return toObject(Context.getContext(), scope, val);
/*  830:     */   }
/*  831:     */   
/*  832:     */   public static Scriptable toObjectOrNull(Context cx, Object obj)
/*  833:     */   {
/*  834: 971 */     if ((obj instanceof Scriptable)) {
/*  835: 972 */       return (Scriptable)obj;
/*  836:     */     }
/*  837: 973 */     if ((obj != null) && (obj != Undefined.instance)) {
/*  838: 974 */       return toObject(cx, getTopCallScope(cx), obj);
/*  839:     */     }
/*  840: 976 */     return null;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public static Scriptable toObjectOrNull(Context cx, Object obj, Scriptable scope)
/*  844:     */   {
/*  845: 985 */     if ((obj instanceof Delegator)) {
/*  846: 986 */       return ((Delegator)obj).getDelegee();
/*  847:     */     }
/*  848: 988 */     if ((obj instanceof Scriptable)) {
/*  849: 989 */       return (Scriptable)obj;
/*  850:     */     }
/*  851: 990 */     if ((obj != null) && (obj != Undefined.instance)) {
/*  852: 991 */       return toObject(cx, scope, obj);
/*  853:     */     }
/*  854: 993 */     return null;
/*  855:     */   }
/*  856:     */   
/*  857:     */   /**
/*  858:     */    * @deprecated
/*  859:     */    */
/*  860:     */   public static Scriptable toObject(Scriptable scope, Object val, Class<?> staticClass)
/*  861:     */   {
/*  862:1002 */     if ((val instanceof Scriptable)) {
/*  863:1003 */       return (Scriptable)val;
/*  864:     */     }
/*  865:1005 */     return toObject(Context.getContext(), scope, val);
/*  866:     */   }
/*  867:     */   
/*  868:     */   public static Scriptable toObject(Context cx, Scriptable scope, Object val)
/*  869:     */   {
/*  870:1015 */     if ((val instanceof Scriptable)) {
/*  871:1016 */       return (Scriptable)val;
/*  872:     */     }
/*  873:1018 */     if ((val instanceof String))
/*  874:     */     {
/*  875:1019 */       NativeString result = new NativeString((String)val);
/*  876:1020 */       setBuiltinProtoAndParent(result, scope, TopLevel.Builtins.String);
/*  877:1021 */       return result;
/*  878:     */     }
/*  879:1023 */     if ((val instanceof Number))
/*  880:     */     {
/*  881:1024 */       NativeNumber result = new NativeNumber(((Number)val).doubleValue());
/*  882:1025 */       setBuiltinProtoAndParent(result, scope, TopLevel.Builtins.Number);
/*  883:1026 */       return result;
/*  884:     */     }
/*  885:1028 */     if ((val instanceof Boolean))
/*  886:     */     {
/*  887:1029 */       NativeBoolean result = new NativeBoolean(((Boolean)val).booleanValue());
/*  888:1030 */       setBuiltinProtoAndParent(result, scope, TopLevel.Builtins.Boolean);
/*  889:1031 */       return result;
/*  890:     */     }
/*  891:1033 */     if (val == null) {
/*  892:1034 */       throw typeError0("msg.null.to.object");
/*  893:     */     }
/*  894:1036 */     if (val == Undefined.instance) {
/*  895:1037 */       throw typeError0("msg.undef.to.object");
/*  896:     */     }
/*  897:1041 */     Object wrapped = cx.getWrapFactory().wrap(cx, scope, val, null);
/*  898:1042 */     if ((wrapped instanceof Scriptable)) {
/*  899:1043 */       return (Scriptable)wrapped;
/*  900:     */     }
/*  901:1044 */     throw errorWithClassName("msg.invalid.type", val);
/*  902:     */   }
/*  903:     */   
/*  904:     */   /**
/*  905:     */    * @deprecated
/*  906:     */    */
/*  907:     */   public static Scriptable toObject(Context cx, Scriptable scope, Object val, Class<?> staticClass)
/*  908:     */   {
/*  909:1053 */     return toObject(cx, scope, val);
/*  910:     */   }
/*  911:     */   
/*  912:     */   /**
/*  913:     */    * @deprecated
/*  914:     */    */
/*  915:     */   public static Object call(Context cx, Object fun, Object thisArg, Object[] args, Scriptable scope)
/*  916:     */   {
/*  917:1062 */     if (!(fun instanceof Function)) {
/*  918:1063 */       throw notFunctionError(toString(fun));
/*  919:     */     }
/*  920:1065 */     Function function = (Function)fun;
/*  921:1066 */     Scriptable thisObj = toObjectOrNull(cx, thisArg);
/*  922:1067 */     if (thisObj == null) {
/*  923:1068 */       throw undefCallError(thisObj, "function");
/*  924:     */     }
/*  925:1070 */     return function.call(cx, scope, thisObj, args);
/*  926:     */   }
/*  927:     */   
/*  928:     */   public static Scriptable newObject(Context cx, Scriptable scope, String constructorName, Object[] args)
/*  929:     */   {
/*  930:1076 */     scope = ScriptableObject.getTopLevelScope(scope);
/*  931:1077 */     Function ctor = getExistingCtor(cx, scope, constructorName);
/*  932:1078 */     if (args == null) {
/*  933:1078 */       args = emptyArgs;
/*  934:     */     }
/*  935:1079 */     return ctor.construct(cx, scope, args);
/*  936:     */   }
/*  937:     */   
/*  938:     */   public static Scriptable newBuiltinObject(Context cx, Scriptable scope, TopLevel.Builtins type, Object[] args)
/*  939:     */   {
/*  940:1086 */     scope = ScriptableObject.getTopLevelScope(scope);
/*  941:1087 */     Function ctor = TopLevel.getBuiltinCtor(cx, scope, type);
/*  942:1088 */     if (args == null) {
/*  943:1088 */       args = emptyArgs;
/*  944:     */     }
/*  945:1089 */     return ctor.construct(cx, scope, args);
/*  946:     */   }
/*  947:     */   
/*  948:     */   public static double toInteger(Object val)
/*  949:     */   {
/*  950:1097 */     return toInteger(toNumber(val));
/*  951:     */   }
/*  952:     */   
/*  953:     */   public static double toInteger(double d)
/*  954:     */   {
/*  955:1103 */     if (d != d) {
/*  956:1104 */       return 0.0D;
/*  957:     */     }
/*  958:1106 */     if ((d == 0.0D) || (d == (1.0D / 0.0D)) || (d == (-1.0D / 0.0D))) {
/*  959:1109 */       return d;
/*  960:     */     }
/*  961:1111 */     if (d > 0.0D) {
/*  962:1112 */       return Math.floor(d);
/*  963:     */     }
/*  964:1114 */     return Math.ceil(d);
/*  965:     */   }
/*  966:     */   
/*  967:     */   public static double toInteger(Object[] args, int index)
/*  968:     */   {
/*  969:1118 */     return index < args.length ? toInteger(args[index]) : 0.0D;
/*  970:     */   }
/*  971:     */   
/*  972:     */   public static int toInt32(Object val)
/*  973:     */   {
/*  974:1128 */     if ((val instanceof Integer)) {
/*  975:1129 */       return ((Integer)val).intValue();
/*  976:     */     }
/*  977:1131 */     return toInt32(toNumber(val));
/*  978:     */   }
/*  979:     */   
/*  980:     */   public static int toInt32(Object[] args, int index)
/*  981:     */   {
/*  982:1135 */     return index < args.length ? toInt32(args[index]) : 0;
/*  983:     */   }
/*  984:     */   
/*  985:     */   public static int toInt32(double d)
/*  986:     */   {
/*  987:1139 */     int id = (int)d;
/*  988:1140 */     if (id == d) {
/*  989:1142 */       return id;
/*  990:     */     }
/*  991:1145 */     if ((d != d) || (d == (1.0D / 0.0D)) || (d == (-1.0D / 0.0D))) {
/*  992:1149 */       return 0;
/*  993:     */     }
/*  994:1152 */     d = d >= 0.0D ? Math.floor(d) : Math.ceil(d);
/*  995:     */     
/*  996:1154 */     double two32 = 4294967296.0D;
/*  997:1155 */     d = Math.IEEEremainder(d, two32);
/*  998:     */     
/*  999:     */ 
/* 1000:1158 */     long l = d;
/* 1001:     */     
/* 1002:     */ 
/* 1003:1161 */     return (int)l;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public static long toUint32(double d)
/* 1007:     */   {
/* 1008:1169 */     long l = d;
/* 1009:1170 */     if (l == d) {
/* 1010:1172 */       return l & 0xFFFFFFFF;
/* 1011:     */     }
/* 1012:1175 */     if ((d != d) || (d == (1.0D / 0.0D)) || (d == (-1.0D / 0.0D))) {
/* 1013:1179 */       return 0L;
/* 1014:     */     }
/* 1015:1182 */     d = d >= 0.0D ? Math.floor(d) : Math.ceil(d);
/* 1016:     */     
/* 1017:     */ 
/* 1018:1185 */     double two32 = 4294967296.0D;
/* 1019:1186 */     l = Math.IEEEremainder(d, two32);
/* 1020:     */     
/* 1021:1188 */     return l & 0xFFFFFFFF;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public static long toUint32(Object val)
/* 1025:     */   {
/* 1026:1192 */     return toUint32(toNumber(val));
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public static char toUint16(Object val)
/* 1030:     */   {
/* 1031:1200 */     double d = toNumber(val);
/* 1032:     */     
/* 1033:1202 */     int i = (int)d;
/* 1034:1203 */     if (i == d) {
/* 1035:1204 */       return (char)i;
/* 1036:     */     }
/* 1037:1207 */     if ((d != d) || (d == (1.0D / 0.0D)) || (d == (-1.0D / 0.0D))) {
/* 1038:1211 */       return '\000';
/* 1039:     */     }
/* 1040:1214 */     d = d >= 0.0D ? Math.floor(d) : Math.ceil(d);
/* 1041:     */     
/* 1042:1216 */     int int16 = 65536;
/* 1043:1217 */     i = (int)Math.IEEEremainder(d, int16);
/* 1044:     */     
/* 1045:1219 */     return (char)i;
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public static Object setDefaultNamespace(Object namespace, Context cx)
/* 1049:     */   {
/* 1050:1228 */     Scriptable scope = cx.currentActivationCall;
/* 1051:1229 */     if (scope == null) {
/* 1052:1230 */       scope = getTopCallScope(cx);
/* 1053:     */     }
/* 1054:1233 */     XMLLib xmlLib = currentXMLLib(cx);
/* 1055:1234 */     Object ns = xmlLib.toDefaultXmlNamespace(cx, namespace);
/* 1056:1237 */     if (!scope.has("__default_namespace__", scope)) {
/* 1057:1239 */       ScriptableObject.defineProperty(scope, "__default_namespace__", ns, 6);
/* 1058:     */     } else {
/* 1059:1243 */       scope.put("__default_namespace__", scope, ns);
/* 1060:     */     }
/* 1061:1246 */     return Undefined.instance;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public static Object searchDefaultNamespace(Context cx)
/* 1065:     */   {
/* 1066:1251 */     Scriptable scope = cx.currentActivationCall;
/* 1067:1252 */     if (scope == null) {
/* 1068:1253 */       scope = getTopCallScope(cx);
/* 1069:     */     }
/* 1070:     */     Object nsObject;
/* 1071:     */     for (;;)
/* 1072:     */     {
/* 1073:1257 */       Scriptable parent = scope.getParentScope();
/* 1074:1258 */       if (parent == null)
/* 1075:     */       {
/* 1076:1259 */         Object nsObject = ScriptableObject.getProperty(scope, "__default_namespace__");
/* 1077:1260 */         if (nsObject != Scriptable.NOT_FOUND) {
/* 1078:     */           break;
/* 1079:     */         }
/* 1080:1261 */         return null;
/* 1081:     */       }
/* 1082:1265 */       nsObject = scope.get("__default_namespace__", scope);
/* 1083:1266 */       if (nsObject != Scriptable.NOT_FOUND) {
/* 1084:     */         break;
/* 1085:     */       }
/* 1086:1269 */       scope = parent;
/* 1087:     */     }
/* 1088:1271 */     return nsObject;
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public static Object getTopLevelProp(Scriptable scope, String id)
/* 1092:     */   {
/* 1093:1275 */     scope = ScriptableObject.getTopLevelScope(scope);
/* 1094:1276 */     return ScriptableObject.getProperty(scope, id);
/* 1095:     */   }
/* 1096:     */   
/* 1097:     */   static Function getExistingCtor(Context cx, Scriptable scope, String constructorName)
/* 1098:     */   {
/* 1099:1282 */     Object ctorVal = ScriptableObject.getProperty(scope, constructorName);
/* 1100:1283 */     if ((ctorVal instanceof Function)) {
/* 1101:1284 */       return (Function)ctorVal;
/* 1102:     */     }
/* 1103:1286 */     if (ctorVal == Scriptable.NOT_FOUND) {
/* 1104:1287 */       throw Context.reportRuntimeError1("msg.ctor.not.found", constructorName);
/* 1105:     */     }
/* 1106:1290 */     throw Context.reportRuntimeError1("msg.not.ctor", constructorName);
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public static long indexFromString(String str)
/* 1110:     */   {
/* 1111:1304 */     int MAX_VALUE_LENGTH = 10;
/* 1112:     */     
/* 1113:1306 */     int len = str.length();
/* 1114:1307 */     if (len > 0)
/* 1115:     */     {
/* 1116:1308 */       int i = 0;
/* 1117:1309 */       boolean negate = false;
/* 1118:1310 */       int c = str.charAt(0);
/* 1119:1311 */       if ((c == 45) && 
/* 1120:1312 */         (len > 1))
/* 1121:     */       {
/* 1122:1313 */         c = str.charAt(1);
/* 1123:1314 */         i = 1;
/* 1124:1315 */         negate = true;
/* 1125:     */       }
/* 1126:1318 */       c -= 48;
/* 1127:1319 */       if ((0 <= c) && (c <= 9)) {
/* 1128:1319 */         if (len <= (negate ? 11 : 10))
/* 1129:     */         {
/* 1130:1325 */           int index = -c;
/* 1131:1326 */           int oldIndex = 0;
/* 1132:1327 */           i++;
/* 1133:1328 */           if (index != 0) {
/* 1134:1330 */             while ((i != len) && (0 <= (c = str.charAt(i) - '0')) && (c <= 9))
/* 1135:     */             {
/* 1136:1332 */               oldIndex = index;
/* 1137:1333 */               index = 10 * index - c;
/* 1138:1334 */               i++;
/* 1139:     */             }
/* 1140:     */           }
/* 1141:1339 */           if (i == len) {
/* 1142:1339 */             if (oldIndex <= -214748364)
/* 1143:     */             {
/* 1144:1339 */               if (oldIndex == -214748364) {
/* 1145:1339 */                 if (c > (negate ? 8 : 7)) {}
/* 1146:     */               }
/* 1147:     */             }
/* 1148:     */             else {
/* 1149:1345 */               return 0xFFFFFFFF & (negate ? index : -index);
/* 1150:     */             }
/* 1151:     */           }
/* 1152:     */         }
/* 1153:     */       }
/* 1154:     */     }
/* 1155:1349 */     return -1L;
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public static long testUint32String(String str)
/* 1159:     */   {
/* 1160:1360 */     int MAX_VALUE_LENGTH = 10;
/* 1161:     */     
/* 1162:1362 */     int len = str.length();
/* 1163:1363 */     if ((1 <= len) && (len <= 10))
/* 1164:     */     {
/* 1165:1364 */       int c = str.charAt(0);
/* 1166:1365 */       c -= 48;
/* 1167:1366 */       if (c == 0) {
/* 1168:1368 */         return len == 1 ? 0L : -1L;
/* 1169:     */       }
/* 1170:1370 */       if ((1 <= c) && (c <= 9))
/* 1171:     */       {
/* 1172:1371 */         long v = c;
/* 1173:1372 */         for (int i = 1; i != len; i++)
/* 1174:     */         {
/* 1175:1373 */           c = str.charAt(i) - '0';
/* 1176:1374 */           if ((0 > c) || (c > 9)) {
/* 1177:1375 */             return -1L;
/* 1178:     */           }
/* 1179:1377 */           v = 10L * v + c;
/* 1180:     */         }
/* 1181:1380 */         if (v >>> 32 == 0L) {
/* 1182:1381 */           return v;
/* 1183:     */         }
/* 1184:     */       }
/* 1185:     */     }
/* 1186:1385 */     return -1L;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   static Object getIndexObject(String s)
/* 1190:     */   {
/* 1191:1394 */     long indexTest = indexFromString(s);
/* 1192:1395 */     if (indexTest >= 0L) {
/* 1193:1396 */       return Integer.valueOf((int)indexTest);
/* 1194:     */     }
/* 1195:1398 */     return s;
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   static Object getIndexObject(double d)
/* 1199:     */   {
/* 1200:1407 */     int i = (int)d;
/* 1201:1408 */     if (i == d) {
/* 1202:1409 */       return Integer.valueOf(i);
/* 1203:     */     }
/* 1204:1411 */     return toString(d);
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   static String toStringIdOrIndex(Context cx, Object id)
/* 1208:     */   {
/* 1209:1421 */     if ((id instanceof Number))
/* 1210:     */     {
/* 1211:1422 */       double d = ((Number)id).doubleValue();
/* 1212:1423 */       int index = (int)d;
/* 1213:1424 */       if (index == d)
/* 1214:     */       {
/* 1215:1425 */         storeIndexResult(cx, index);
/* 1216:1426 */         return null;
/* 1217:     */       }
/* 1218:1428 */       return toString(id);
/* 1219:     */     }
/* 1220:     */     String s;
/* 1221:     */     String s;
/* 1222:1431 */     if ((id instanceof String)) {
/* 1223:1432 */       s = (String)id;
/* 1224:     */     } else {
/* 1225:1434 */       s = toString(id);
/* 1226:     */     }
/* 1227:1436 */     long indexTest = indexFromString(s);
/* 1228:1437 */     if (indexTest >= 0L)
/* 1229:     */     {
/* 1230:1438 */       storeIndexResult(cx, (int)indexTest);
/* 1231:1439 */       return null;
/* 1232:     */     }
/* 1233:1441 */     return s;
/* 1234:     */   }
/* 1235:     */   
/* 1236:     */   public static Object getObjectElem(Object obj, Object elem, Context cx)
/* 1237:     */   {
/* 1238:1450 */     return getObjectElem(obj, elem, cx, getTopCallScope(cx));
/* 1239:     */   }
/* 1240:     */   
/* 1241:     */   public static Object getObjectElem(Object obj, Object elem, Context cx, Scriptable scope)
/* 1242:     */   {
/* 1243:1458 */     Scriptable sobj = toObjectOrNull(cx, obj, scope);
/* 1244:1459 */     if (sobj == null) {
/* 1245:1460 */       throw undefReadError(obj, elem);
/* 1246:     */     }
/* 1247:1462 */     return getObjectElem(sobj, elem, cx);
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public static Object getObjectElem(Scriptable obj, Object elem, Context cx)
/* 1251:     */   {
/* 1252:1468 */     if ((obj instanceof XMLObject))
/* 1253:     */     {
/* 1254:1469 */       XMLObject xmlObject = (XMLObject)obj;
/* 1255:1470 */       return xmlObject.ecmaGet(cx, elem);
/* 1256:     */     }
/* 1257:1475 */     String s = toStringIdOrIndex(cx, elem);
/* 1258:     */     Object result;
/* 1259:     */     Object result;
/* 1260:1476 */     if (s == null)
/* 1261:     */     {
/* 1262:1477 */       int index = lastIndexResult(cx);
/* 1263:1478 */       result = ScriptableObject.getProperty(obj, index);
/* 1264:     */     }
/* 1265:     */     else
/* 1266:     */     {
/* 1267:1480 */       result = ScriptableObject.getProperty(obj, s);
/* 1268:     */     }
/* 1269:1483 */     if (result == Scriptable.NOT_FOUND) {
/* 1270:1484 */       result = Undefined.instance;
/* 1271:     */     }
/* 1272:1487 */     return result;
/* 1273:     */   }
/* 1274:     */   
/* 1275:     */   public static Object getObjectProp(Object obj, String property, Context cx)
/* 1276:     */   {
/* 1277:1496 */     Scriptable sobj = toObjectOrNull(cx, obj);
/* 1278:1497 */     if (sobj == null) {
/* 1279:1498 */       throw undefReadError(obj, property);
/* 1280:     */     }
/* 1281:1500 */     return getObjectProp(sobj, property, cx);
/* 1282:     */   }
/* 1283:     */   
/* 1284:     */   public static Object getObjectProp(Object obj, String property, Context cx, Scriptable scope)
/* 1285:     */   {
/* 1286:1509 */     Scriptable sobj = toObjectOrNull(cx, obj, scope);
/* 1287:1510 */     if (sobj == null) {
/* 1288:1511 */       throw undefReadError(obj, property);
/* 1289:     */     }
/* 1290:1513 */     return getObjectProp(sobj, property, cx);
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public static Object getObjectProp(Scriptable obj, String property, Context cx)
/* 1294:     */   {
/* 1295:1519 */     if ((obj instanceof XMLObject))
/* 1296:     */     {
/* 1297:1523 */       XMLObject xmlObject = (XMLObject)obj;
/* 1298:1524 */       return xmlObject.ecmaGet(cx, property);
/* 1299:     */     }
/* 1300:1527 */     Object result = ScriptableObject.getProperty(obj, property);
/* 1301:1528 */     if (result == Scriptable.NOT_FOUND)
/* 1302:     */     {
/* 1303:1529 */       if (cx.hasFeature(11)) {
/* 1304:1530 */         Context.reportWarning(getMessage1("msg.ref.undefined.prop", property));
/* 1305:     */       }
/* 1306:1533 */       result = Undefined.instance;
/* 1307:     */     }
/* 1308:1536 */     return result;
/* 1309:     */   }
/* 1310:     */   
/* 1311:     */   public static Object getObjectPropNoWarn(Object obj, String property, Context cx)
/* 1312:     */   {
/* 1313:1542 */     Scriptable sobj = toObjectOrNull(cx, obj);
/* 1314:1543 */     if (sobj == null) {
/* 1315:1544 */       throw undefReadError(obj, property);
/* 1316:     */     }
/* 1317:1546 */     if ((obj instanceof XMLObject)) {
/* 1318:1548 */       getObjectProp(sobj, property, cx);
/* 1319:     */     }
/* 1320:1550 */     Object result = ScriptableObject.getProperty(sobj, property);
/* 1321:1551 */     if (result == Scriptable.NOT_FOUND) {
/* 1322:1552 */       return Undefined.instance;
/* 1323:     */     }
/* 1324:1554 */     return result;
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public static Object getObjectIndex(Object obj, double dblIndex, Context cx)
/* 1328:     */   {
/* 1329:1564 */     Scriptable sobj = toObjectOrNull(cx, obj);
/* 1330:1565 */     if (sobj == null) {
/* 1331:1566 */       throw undefReadError(obj, toString(dblIndex));
/* 1332:     */     }
/* 1333:1569 */     int index = (int)dblIndex;
/* 1334:1570 */     if (index == dblIndex) {
/* 1335:1571 */       return getObjectIndex(sobj, index, cx);
/* 1336:     */     }
/* 1337:1573 */     String s = toString(dblIndex);
/* 1338:1574 */     return getObjectProp(sobj, s, cx);
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   public static Object getObjectIndex(Scriptable obj, int index, Context cx)
/* 1342:     */   {
/* 1343:1581 */     if ((obj instanceof XMLObject))
/* 1344:     */     {
/* 1345:1582 */       XMLObject xmlObject = (XMLObject)obj;
/* 1346:1583 */       return xmlObject.ecmaGet(cx, Integer.valueOf(index));
/* 1347:     */     }
/* 1348:1586 */     Object result = ScriptableObject.getProperty(obj, index);
/* 1349:1587 */     if (result == Scriptable.NOT_FOUND) {
/* 1350:1588 */       result = Undefined.instance;
/* 1351:     */     }
/* 1352:1591 */     return result;
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public static Object setObjectElem(Object obj, Object elem, Object value, Context cx)
/* 1356:     */   {
/* 1357:1600 */     Scriptable sobj = toObjectOrNull(cx, obj);
/* 1358:1601 */     if (sobj == null) {
/* 1359:1602 */       throw undefWriteError(obj, elem, value);
/* 1360:     */     }
/* 1361:1604 */     return setObjectElem(sobj, elem, value, cx);
/* 1362:     */   }
/* 1363:     */   
/* 1364:     */   public static Object setObjectElem(Scriptable obj, Object elem, Object value, Context cx)
/* 1365:     */   {
/* 1366:1610 */     if ((obj instanceof XMLObject))
/* 1367:     */     {
/* 1368:1611 */       XMLObject xmlObject = (XMLObject)obj;
/* 1369:1612 */       xmlObject.ecmaPut(cx, elem, value);
/* 1370:1613 */       return value;
/* 1371:     */     }
/* 1372:1616 */     String s = toStringIdOrIndex(cx, elem);
/* 1373:1617 */     if (s == null)
/* 1374:     */     {
/* 1375:1618 */       int index = lastIndexResult(cx);
/* 1376:1619 */       ScriptableObject.putProperty(obj, index, value);
/* 1377:     */     }
/* 1378:     */     else
/* 1379:     */     {
/* 1380:1621 */       ScriptableObject.putProperty(obj, s, value);
/* 1381:     */     }
/* 1382:1624 */     return value;
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   public static Object setObjectProp(Object obj, String property, Object value, Context cx)
/* 1386:     */   {
/* 1387:1633 */     Scriptable sobj = toObjectOrNull(cx, obj);
/* 1388:1634 */     if (sobj == null) {
/* 1389:1635 */       throw undefWriteError(obj, property, value);
/* 1390:     */     }
/* 1391:1637 */     return setObjectProp(sobj, property, value, cx);
/* 1392:     */   }
/* 1393:     */   
/* 1394:     */   public static Object setObjectProp(Scriptable obj, String property, Object value, Context cx)
/* 1395:     */   {
/* 1396:1643 */     if ((obj instanceof XMLObject))
/* 1397:     */     {
/* 1398:1644 */       XMLObject xmlObject = (XMLObject)obj;
/* 1399:1645 */       xmlObject.ecmaPut(cx, property, value);
/* 1400:     */     }
/* 1401:     */     else
/* 1402:     */     {
/* 1403:1647 */       ScriptableObject.putProperty(obj, property, value);
/* 1404:     */     }
/* 1405:1649 */     return value;
/* 1406:     */   }
/* 1407:     */   
/* 1408:     */   public static Object setObjectIndex(Object obj, double dblIndex, Object value, Context cx)
/* 1409:     */   {
/* 1410:1659 */     Scriptable sobj = toObjectOrNull(cx, obj);
/* 1411:1660 */     if (sobj == null) {
/* 1412:1661 */       throw undefWriteError(obj, String.valueOf(dblIndex), value);
/* 1413:     */     }
/* 1414:1664 */     int index = (int)dblIndex;
/* 1415:1665 */     if (index == dblIndex) {
/* 1416:1666 */       return setObjectIndex(sobj, index, value, cx);
/* 1417:     */     }
/* 1418:1668 */     String s = toString(dblIndex);
/* 1419:1669 */     return setObjectProp(sobj, s, value, cx);
/* 1420:     */   }
/* 1421:     */   
/* 1422:     */   public static Object setObjectIndex(Scriptable obj, int index, Object value, Context cx)
/* 1423:     */   {
/* 1424:1676 */     if ((obj instanceof XMLObject))
/* 1425:     */     {
/* 1426:1677 */       XMLObject xmlObject = (XMLObject)obj;
/* 1427:1678 */       xmlObject.ecmaPut(cx, Integer.valueOf(index), value);
/* 1428:     */     }
/* 1429:     */     else
/* 1430:     */     {
/* 1431:1680 */       ScriptableObject.putProperty(obj, index, value);
/* 1432:     */     }
/* 1433:1682 */     return value;
/* 1434:     */   }
/* 1435:     */   
/* 1436:     */   public static boolean deleteObjectElem(Scriptable target, Object elem, Context cx)
/* 1437:     */   {
/* 1438:     */     boolean result;
/* 1439:1689 */     if ((target instanceof XMLObject))
/* 1440:     */     {
/* 1441:1690 */       XMLObject xmlObject = (XMLObject)target;
/* 1442:1691 */       result = xmlObject.ecmaDelete(cx, elem);
/* 1443:     */     }
/* 1444:     */     else
/* 1445:     */     {
/* 1446:1693 */       String s = toStringIdOrIndex(cx, elem);
/* 1447:1694 */       if (s == null)
/* 1448:     */       {
/* 1449:1695 */         int index = lastIndexResult(cx);
/* 1450:1696 */         target.delete(index);
/* 1451:1697 */         return !target.has(index, target);
/* 1452:     */       }
/* 1453:1699 */       target.delete(s);
/* 1454:1700 */       return !target.has(s, target);
/* 1455:     */     }
/* 1456:     */     boolean result;
/* 1457:1703 */     return result;
/* 1458:     */   }
/* 1459:     */   
/* 1460:     */   public static boolean hasObjectElem(Scriptable target, Object elem, Context cx)
/* 1461:     */   {
/* 1462:     */     boolean result;
/* 1463:     */     boolean result;
/* 1464:1711 */     if ((target instanceof XMLObject))
/* 1465:     */     {
/* 1466:1712 */       XMLObject xmlObject = (XMLObject)target;
/* 1467:1713 */       result = xmlObject.ecmaHas(cx, elem);
/* 1468:     */     }
/* 1469:     */     else
/* 1470:     */     {
/* 1471:1715 */       String s = toStringIdOrIndex(cx, elem);
/* 1472:     */       boolean result;
/* 1473:1716 */       if (s == null)
/* 1474:     */       {
/* 1475:1717 */         int index = lastIndexResult(cx);
/* 1476:1718 */         result = ScriptableObject.hasProperty(target, index);
/* 1477:     */       }
/* 1478:     */       else
/* 1479:     */       {
/* 1480:1720 */         result = ScriptableObject.hasProperty(target, s);
/* 1481:     */       }
/* 1482:     */     }
/* 1483:1724 */     return result;
/* 1484:     */   }
/* 1485:     */   
/* 1486:     */   public static Object refGet(Ref ref, Context cx)
/* 1487:     */   {
/* 1488:1729 */     return ref.get(cx);
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public static Object refSet(Ref ref, Object value, Context cx)
/* 1492:     */   {
/* 1493:1734 */     return ref.set(cx, value);
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public static Object refDel(Ref ref, Context cx)
/* 1497:     */   {
/* 1498:1739 */     return wrapBoolean(ref.delete(cx));
/* 1499:     */   }
/* 1500:     */   
/* 1501:     */   static boolean isSpecialProperty(String s)
/* 1502:     */   {
/* 1503:1744 */     return (s.equals("__proto__")) || (s.equals("__parent__"));
/* 1504:     */   }
/* 1505:     */   
/* 1506:     */   public static Ref specialRef(Object obj, String specialProperty, Context cx)
/* 1507:     */   {
/* 1508:1750 */     return SpecialRef.createSpecial(cx, obj, specialProperty);
/* 1509:     */   }
/* 1510:     */   
/* 1511:     */   public static Object delete(Object obj, Object id, Context cx)
/* 1512:     */   {
/* 1513:1766 */     Scriptable sobj = toObjectOrNull(cx, obj);
/* 1514:1767 */     if (sobj == null)
/* 1515:     */     {
/* 1516:1768 */       String idStr = id == null ? "null" : id.toString();
/* 1517:1769 */       throw typeError2("msg.undef.prop.delete", toString(obj), idStr);
/* 1518:     */     }
/* 1519:1771 */     boolean result = deleteObjectElem(sobj, id, cx);
/* 1520:1772 */     return wrapBoolean(result);
/* 1521:     */   }
/* 1522:     */   
/* 1523:     */   public static Object name(Context cx, Scriptable scope, String name)
/* 1524:     */   {
/* 1525:1780 */     Scriptable parent = scope.getParentScope();
/* 1526:1781 */     if (parent == null)
/* 1527:     */     {
/* 1528:1782 */       Object result = topScopeName(cx, scope, name);
/* 1529:1783 */       if (result == Scriptable.NOT_FOUND) {
/* 1530:1784 */         throw notFoundError(scope, name);
/* 1531:     */       }
/* 1532:1786 */       return result;
/* 1533:     */     }
/* 1534:1789 */     return nameOrFunction(cx, scope, parent, name, false);
/* 1535:     */   }
/* 1536:     */   
/* 1537:     */   private static Object nameOrFunction(Context cx, Scriptable scope, Scriptable parentScope, String name, boolean asFunctionCall)
/* 1538:     */   {
/* 1539:1797 */     Scriptable thisObj = scope;
/* 1540:     */     
/* 1541:1799 */     XMLObject firstXMLObject = null;
/* 1542:     */     do
/* 1543:     */     {
/* 1544:1801 */       if ((scope instanceof NativeWith))
/* 1545:     */       {
/* 1546:1802 */         Scriptable withObj = scope.getPrototype();
/* 1547:1803 */         if ((withObj instanceof XMLObject))
/* 1548:     */         {
/* 1549:1804 */           XMLObject xmlObj = (XMLObject)withObj;
/* 1550:1805 */           if (xmlObj.ecmaHas(cx, name))
/* 1551:     */           {
/* 1552:1807 */             thisObj = xmlObj;
/* 1553:1808 */             Object result = xmlObj.ecmaGet(cx, name);
/* 1554:1809 */             break;
/* 1555:     */           }
/* 1556:1811 */           if (firstXMLObject == null) {
/* 1557:1812 */             firstXMLObject = xmlObj;
/* 1558:     */           }
/* 1559:     */         }
/* 1560:     */         else
/* 1561:     */         {
/* 1562:1815 */           Object result = ScriptableObject.getProperty(withObj, name);
/* 1563:1816 */           if (result != Scriptable.NOT_FOUND)
/* 1564:     */           {
/* 1565:1818 */             thisObj = withObj;
/* 1566:1819 */             break;
/* 1567:     */           }
/* 1568:     */         }
/* 1569:     */       }
/* 1570:1822 */       else if ((scope instanceof NativeCall))
/* 1571:     */       {
/* 1572:1825 */         Object result = scope.get(name, scope);
/* 1573:1826 */         if (result != Scriptable.NOT_FOUND)
/* 1574:     */         {
/* 1575:1827 */           if (!asFunctionCall) {
/* 1576:     */             break;
/* 1577:     */           }
/* 1578:1830 */           thisObj = ScriptableObject.getTopLevelScope(parentScope); break;
/* 1579:     */         }
/* 1580:     */       }
/* 1581:     */       else
/* 1582:     */       {
/* 1583:1838 */         Object result = ScriptableObject.getProperty(scope, name);
/* 1584:1839 */         if (result != Scriptable.NOT_FOUND)
/* 1585:     */         {
/* 1586:1840 */           thisObj = scope;
/* 1587:1841 */           break;
/* 1588:     */         }
/* 1589:     */       }
/* 1590:1844 */       scope = parentScope;
/* 1591:1845 */       parentScope = parentScope.getParentScope();
/* 1592:1846 */     } while (parentScope != null);
/* 1593:1847 */     Object result = topScopeName(cx, scope, name);
/* 1594:1848 */     if (result == Scriptable.NOT_FOUND)
/* 1595:     */     {
/* 1596:1849 */       if ((firstXMLObject == null) || (asFunctionCall)) {
/* 1597:1850 */         throw notFoundError(scope, name);
/* 1598:     */       }
/* 1599:1856 */       result = firstXMLObject.ecmaGet(cx, name);
/* 1600:     */     }
/* 1601:1859 */     thisObj = scope;
/* 1602:1864 */     if (asFunctionCall)
/* 1603:     */     {
/* 1604:1865 */       if (!(result instanceof Callable)) {
/* 1605:1866 */         throw notFunctionError(result, name);
/* 1606:     */       }
/* 1607:1868 */       storeScriptable(cx, thisObj);
/* 1608:     */     }
/* 1609:1871 */     return result;
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   private static Object topScopeName(Context cx, Scriptable scope, String name)
/* 1613:     */   {
/* 1614:1877 */     if (cx.useDynamicScope) {
/* 1615:1878 */       scope = checkDynamicScope(cx.topCallScope, scope);
/* 1616:     */     }
/* 1617:1880 */     return ScriptableObject.getProperty(scope, name);
/* 1618:     */   }
/* 1619:     */   
/* 1620:     */   public static Scriptable bind(Context cx, Scriptable scope, String id)
/* 1621:     */   {
/* 1622:1899 */     Scriptable firstXMLObject = null;
/* 1623:1900 */     Scriptable parent = scope.getParentScope();
/* 1624:1901 */     if (parent != null)
/* 1625:     */     {
/* 1626:1903 */       while ((scope instanceof NativeWith))
/* 1627:     */       {
/* 1628:1904 */         Scriptable withObj = scope.getPrototype();
/* 1629:1905 */         if ((withObj instanceof XMLObject))
/* 1630:     */         {
/* 1631:1906 */           XMLObject xmlObject = (XMLObject)withObj;
/* 1632:1907 */           if (xmlObject.ecmaHas(cx, id)) {
/* 1633:1908 */             return xmlObject;
/* 1634:     */           }
/* 1635:1910 */           if (firstXMLObject == null) {
/* 1636:1911 */             firstXMLObject = xmlObject;
/* 1637:     */           }
/* 1638:     */         }
/* 1639:1914 */         else if (ScriptableObject.hasProperty(withObj, id))
/* 1640:     */         {
/* 1641:1915 */           return withObj;
/* 1642:     */         }
/* 1643:1918 */         scope = parent;
/* 1644:1919 */         parent = parent.getParentScope();
/* 1645:1920 */         if (parent == null) {
/* 1646:     */           break label133;
/* 1647:     */         }
/* 1648:     */       }
/* 1649:     */       for (;;)
/* 1650:     */       {
/* 1651:1925 */         if (ScriptableObject.hasProperty(scope, id)) {
/* 1652:1926 */           return scope;
/* 1653:     */         }
/* 1654:1928 */         scope = parent;
/* 1655:1929 */         parent = parent.getParentScope();
/* 1656:1930 */         if (parent == null) {
/* 1657:     */           break;
/* 1658:     */         }
/* 1659:     */       }
/* 1660:     */     }
/* 1661:     */     label133:
/* 1662:1936 */     if (cx.useDynamicScope) {
/* 1663:1937 */       scope = checkDynamicScope(cx.topCallScope, scope);
/* 1664:     */     }
/* 1665:1939 */     if (ScriptableObject.hasProperty(scope, id)) {
/* 1666:1940 */       return scope;
/* 1667:     */     }
/* 1668:1944 */     return firstXMLObject;
/* 1669:     */   }
/* 1670:     */   
/* 1671:     */   public static Object setName(Scriptable bound, Object value, Context cx, Scriptable scope, String id)
/* 1672:     */   {
/* 1673:1950 */     if (bound != null)
/* 1674:     */     {
/* 1675:1951 */       if ((bound instanceof XMLObject))
/* 1676:     */       {
/* 1677:1952 */         XMLObject xmlObject = (XMLObject)bound;
/* 1678:1953 */         xmlObject.ecmaPut(cx, id, value);
/* 1679:     */       }
/* 1680:     */       else
/* 1681:     */       {
/* 1682:1955 */         ScriptableObject.putProperty(bound, id, value);
/* 1683:     */       }
/* 1684:     */     }
/* 1685:     */     else
/* 1686:     */     {
/* 1687:1961 */       if ((cx.hasFeature(11)) || (cx.hasFeature(8))) {
/* 1688:1964 */         Context.reportWarning(getMessage1("msg.assn.create.strict", id));
/* 1689:     */       }
/* 1690:1968 */       bound = ScriptableObject.getTopLevelScope(scope);
/* 1691:1969 */       if (cx.useDynamicScope) {
/* 1692:1970 */         bound = checkDynamicScope(cx.topCallScope, bound);
/* 1693:     */       }
/* 1694:1972 */       bound.put(id, bound, value);
/* 1695:     */     }
/* 1696:1974 */     return value;
/* 1697:     */   }
/* 1698:     */   
/* 1699:     */   public static Object strictSetName(Scriptable bound, Object value, Context cx, Scriptable scope, String id)
/* 1700:     */   {
/* 1701:1979 */     if (bound != null)
/* 1702:     */     {
/* 1703:1986 */       if ((bound instanceof XMLObject))
/* 1704:     */       {
/* 1705:1987 */         XMLObject xmlObject = (XMLObject)bound;
/* 1706:1988 */         xmlObject.ecmaPut(cx, id, value);
/* 1707:     */       }
/* 1708:     */       else
/* 1709:     */       {
/* 1710:1990 */         ScriptableObject.putProperty(bound, id, value);
/* 1711:     */       }
/* 1712:1992 */       return value;
/* 1713:     */     }
/* 1714:1995 */     String msg = "Assignment to undefined \"" + id + "\" in strict mode";
/* 1715:1996 */     throw constructError("ReferenceError", msg);
/* 1716:     */   }
/* 1717:     */   
/* 1718:     */   public static Object setConst(Scriptable bound, Object value, Context cx, String id)
/* 1719:     */   {
/* 1720:2003 */     if ((bound instanceof XMLObject))
/* 1721:     */     {
/* 1722:2004 */       XMLObject xmlObject = (XMLObject)bound;
/* 1723:2005 */       xmlObject.ecmaPut(cx, id, value);
/* 1724:     */     }
/* 1725:     */     else
/* 1726:     */     {
/* 1727:2007 */       ScriptableObject.putConstProperty(bound, id, value);
/* 1728:     */     }
/* 1729:2009 */     return value;
/* 1730:     */   }
/* 1731:     */   
/* 1732:     */   public static Scriptable toIterator(Context cx, Scriptable scope, Scriptable obj, boolean keyOnly)
/* 1733:     */   {
/* 1734:2047 */     if (ScriptableObject.hasProperty(obj, "__iterator__"))
/* 1735:     */     {
/* 1736:2050 */       Object v = ScriptableObject.getProperty(obj, "__iterator__");
/* 1737:2052 */       if (!(v instanceof Callable)) {
/* 1738:2053 */         throw typeError0("msg.invalid.iterator");
/* 1739:     */       }
/* 1740:2055 */       Callable f = (Callable)v;
/* 1741:2056 */       Object[] args = { keyOnly ? Boolean.TRUE : Boolean.FALSE };
/* 1742:     */       
/* 1743:2058 */       v = f.call(cx, scope, obj, args);
/* 1744:2059 */       if (!(v instanceof Scriptable)) {
/* 1745:2060 */         throw typeError0("msg.iterator.primitive");
/* 1746:     */       }
/* 1747:2062 */       return (Scriptable)v;
/* 1748:     */     }
/* 1749:2064 */     return null;
/* 1750:     */   }
/* 1751:     */   
/* 1752:     */   public static Object enumInit(Object value, Context cx, boolean enumValues)
/* 1753:     */   {
/* 1754:2070 */     return enumInit(value, cx, enumValues ? 1 : 0);
/* 1755:     */   }
/* 1756:     */   
/* 1757:     */   public static Object enumInit(Object value, Context cx, int enumType)
/* 1758:     */   {
/* 1759:2083 */     IdEnumeration x = new IdEnumeration(null);
/* 1760:2084 */     x.obj = toObjectOrNull(cx, value);
/* 1761:2085 */     if (x.obj == null) {
/* 1762:2088 */       return x;
/* 1763:     */     }
/* 1764:2090 */     x.enumType = enumType;
/* 1765:2091 */     x.iterator = null;
/* 1766:2092 */     if ((enumType != 3) && (enumType != 4) && (enumType != 5)) {
/* 1767:2096 */       x.iterator = toIterator(cx, x.obj.getParentScope(), x.obj, enumType == 0);
/* 1768:     */     }
/* 1769:2099 */     if (x.iterator == null) {
/* 1770:2102 */       enumChangeObject(x);
/* 1771:     */     }
/* 1772:2105 */     return x;
/* 1773:     */   }
/* 1774:     */   
/* 1775:     */   public static void setEnumNumbers(Object enumObj, boolean enumNumbers)
/* 1776:     */   {
/* 1777:2109 */     ((IdEnumeration)enumObj).enumNumbers = enumNumbers;
/* 1778:     */   }
/* 1779:     */   
/* 1780:     */   public static Boolean enumNext(Object enumObj)
/* 1781:     */   {
/* 1782:2114 */     IdEnumeration x = (IdEnumeration)enumObj;
/* 1783:2115 */     if (x.iterator != null)
/* 1784:     */     {
/* 1785:2116 */       Object v = ScriptableObject.getProperty(x.iterator, "next");
/* 1786:2117 */       if (!(v instanceof Callable)) {
/* 1787:2118 */         return Boolean.FALSE;
/* 1788:     */       }
/* 1789:2119 */       Callable f = (Callable)v;
/* 1790:2120 */       Context cx = Context.getContext();
/* 1791:     */       try
/* 1792:     */       {
/* 1793:2122 */         x.currentId = f.call(cx, x.iterator.getParentScope(), x.iterator, emptyArgs);
/* 1794:     */         
/* 1795:2124 */         return Boolean.TRUE;
/* 1796:     */       }
/* 1797:     */       catch (JavaScriptException e)
/* 1798:     */       {
/* 1799:2126 */         if ((e.getValue() instanceof NativeIterator.StopIteration)) {
/* 1800:2127 */           return Boolean.FALSE;
/* 1801:     */         }
/* 1802:2129 */         throw e;
/* 1803:     */       }
/* 1804:     */     }
/* 1805:     */     int intId;
/* 1806:     */     do
/* 1807:     */     {
/* 1808:     */       Object id;
/* 1809:     */       String strId;
/* 1810:     */       do
/* 1811:     */       {
/* 1812:     */         do
/* 1813:     */         {
/* 1814:     */           for (;;)
/* 1815:     */           {
/* 1816:2133 */             if (x.obj == null) {
/* 1817:2134 */               return Boolean.FALSE;
/* 1818:     */             }
/* 1819:2136 */             if (x.index != x.ids.length) {
/* 1820:     */               break;
/* 1821:     */             }
/* 1822:2137 */             x.obj = x.obj.getPrototype();
/* 1823:2138 */             enumChangeObject(x);
/* 1824:     */           }
/* 1825:2141 */           id = x.ids[(x.index++)];
/* 1826:2142 */         } while ((x.used != null) && (x.used.has(id)));
/* 1827:2145 */         if (!(id instanceof String)) {
/* 1828:     */           break;
/* 1829:     */         }
/* 1830:2146 */         strId = (String)id;
/* 1831:2147 */       } while (!x.obj.has(strId, x.obj));
/* 1832:2149 */       x.currentId = strId;
/* 1833:2150 */       break;
/* 1834:2151 */       intId = ((Number)id).intValue();
/* 1835:2152 */     } while (!x.obj.has(intId, x.obj));
/* 1836:2154 */     x.currentId = (x.enumNumbers ? Integer.valueOf(intId) : String.valueOf(intId));
/* 1837:     */     
/* 1838:     */ 
/* 1839:2157 */     return Boolean.TRUE;
/* 1840:     */   }
/* 1841:     */   
/* 1842:     */   public static Object enumId(Object enumObj, Context cx)
/* 1843:     */   {
/* 1844:2163 */     IdEnumeration x = (IdEnumeration)enumObj;
/* 1845:2164 */     if (x.iterator != null) {
/* 1846:2165 */       return x.currentId;
/* 1847:     */     }
/* 1848:2167 */     switch (x.enumType)
/* 1849:     */     {
/* 1850:     */     case 0: 
/* 1851:     */     case 3: 
/* 1852:2170 */       return x.currentId;
/* 1853:     */     case 1: 
/* 1854:     */     case 4: 
/* 1855:2173 */       return enumValue(enumObj, cx);
/* 1856:     */     case 2: 
/* 1857:     */     case 5: 
/* 1858:2176 */       Object[] elements = { x.currentId, enumValue(enumObj, cx) };
/* 1859:2177 */       return cx.newArray(ScriptableObject.getTopLevelScope(x.obj), elements);
/* 1860:     */     }
/* 1861:2179 */     throw Kit.codeBug();
/* 1862:     */   }
/* 1863:     */   
/* 1864:     */   public static Object enumValue(Object enumObj, Context cx)
/* 1865:     */   {
/* 1866:2184 */     IdEnumeration x = (IdEnumeration)enumObj;
/* 1867:     */     
/* 1868:     */ 
/* 1869:     */ 
/* 1870:2188 */     String s = toStringIdOrIndex(cx, x.currentId);
/* 1871:     */     Object result;
/* 1872:     */     Object result;
/* 1873:2189 */     if (s == null)
/* 1874:     */     {
/* 1875:2190 */       int index = lastIndexResult(cx);
/* 1876:2191 */       result = x.obj.get(index, x.obj);
/* 1877:     */     }
/* 1878:     */     else
/* 1879:     */     {
/* 1880:2193 */       result = x.obj.get(s, x.obj);
/* 1881:     */     }
/* 1882:2196 */     return result;
/* 1883:     */   }
/* 1884:     */   
/* 1885:     */   private static void enumChangeObject(IdEnumeration x)
/* 1886:     */   {
/* 1887:2201 */     Object[] ids = null;
/* 1888:2202 */     while (x.obj != null)
/* 1889:     */     {
/* 1890:2203 */       ids = x.obj.getIds();
/* 1891:2204 */       if (ids.length != 0) {
/* 1892:     */         break;
/* 1893:     */       }
/* 1894:2207 */       x.obj = x.obj.getPrototype();
/* 1895:     */     }
/* 1896:2209 */     if ((x.obj != null) && (x.ids != null))
/* 1897:     */     {
/* 1898:2210 */       Object[] previous = x.ids;
/* 1899:2211 */       int L = previous.length;
/* 1900:2212 */       if (x.used == null) {
/* 1901:2213 */         x.used = new ObjToIntMap(L);
/* 1902:     */       }
/* 1903:2215 */       for (int i = 0; i != L; i++) {
/* 1904:2216 */         x.used.intern(previous[i]);
/* 1905:     */       }
/* 1906:     */     }
/* 1907:2219 */     x.ids = ids;
/* 1908:2220 */     x.index = 0;
/* 1909:     */   }
/* 1910:     */   
/* 1911:     */   public static Callable getNameFunctionAndThis(String name, Context cx, Scriptable scope)
/* 1912:     */   {
/* 1913:2234 */     Scriptable parent = scope.getParentScope();
/* 1914:2235 */     if (parent == null)
/* 1915:     */     {
/* 1916:2236 */       Object result = topScopeName(cx, scope, name);
/* 1917:2237 */       if (!(result instanceof Callable))
/* 1918:     */       {
/* 1919:2238 */         if (result == Scriptable.NOT_FOUND) {
/* 1920:2239 */           throw notFoundError(scope, name);
/* 1921:     */         }
/* 1922:2241 */         throw notFunctionError(result, name);
/* 1923:     */       }
/* 1924:2245 */       Scriptable thisObj = scope;
/* 1925:2246 */       storeScriptable(cx, thisObj);
/* 1926:2247 */       return (Callable)result;
/* 1927:     */     }
/* 1928:2251 */     return (Callable)nameOrFunction(cx, scope, parent, name, true);
/* 1929:     */   }
/* 1930:     */   
/* 1931:     */   public static Callable getElemFunctionAndThis(Object obj, Object elem, Context cx)
/* 1932:     */   {
/* 1933:2265 */     String s = toStringIdOrIndex(cx, elem);
/* 1934:2266 */     if (s != null) {
/* 1935:2267 */       return getPropFunctionAndThis(obj, s, cx);
/* 1936:     */     }
/* 1937:2269 */     int index = lastIndexResult(cx);
/* 1938:     */     
/* 1939:2271 */     Scriptable thisObj = toObjectOrNull(cx, obj);
/* 1940:2272 */     if (thisObj == null) {
/* 1941:2273 */       throw undefCallError(obj, String.valueOf(index));
/* 1942:     */     }
/* 1943:     */     Object value;
/* 1944:     */     for (;;)
/* 1945:     */     {
/* 1946:2279 */       value = ScriptableObject.getProperty(thisObj, index);
/* 1947:2280 */       if (value != Scriptable.NOT_FOUND) {
/* 1948:     */         break;
/* 1949:     */       }
/* 1950:2283 */       if (!(thisObj instanceof XMLObject)) {
/* 1951:     */         break;
/* 1952:     */       }
/* 1953:2286 */       XMLObject xmlObject = (XMLObject)thisObj;
/* 1954:2287 */       Scriptable extra = xmlObject.getExtraMethodSource(cx);
/* 1955:2288 */       if (extra == null) {
/* 1956:     */         break;
/* 1957:     */       }
/* 1958:2291 */       thisObj = extra;
/* 1959:     */     }
/* 1960:2293 */     if (!(value instanceof Callable)) {
/* 1961:2294 */       throw notFunctionError(value, elem);
/* 1962:     */     }
/* 1963:2297 */     storeScriptable(cx, thisObj);
/* 1964:2298 */     return (Callable)value;
/* 1965:     */   }
/* 1966:     */   
/* 1967:     */   public static Callable getPropFunctionAndThis(Object obj, String property, Context cx)
/* 1968:     */   {
/* 1969:2314 */     Scriptable thisObj = toObjectOrNull(cx, obj);
/* 1970:2315 */     return getPropFunctionAndThisHelper(obj, property, cx, thisObj);
/* 1971:     */   }
/* 1972:     */   
/* 1973:     */   public static Callable getPropFunctionAndThis(Object obj, String property, Context cx, Scriptable scope)
/* 1974:     */   {
/* 1975:2329 */     Scriptable thisObj = toObjectOrNull(cx, obj, scope);
/* 1976:2330 */     return getPropFunctionAndThisHelper(obj, property, cx, thisObj);
/* 1977:     */   }
/* 1978:     */   
/* 1979:     */   private static Callable getPropFunctionAndThisHelper(Object obj, String property, Context cx, Scriptable thisObj)
/* 1980:     */   {
/* 1981:2336 */     if (thisObj == null) {
/* 1982:2337 */       throw undefCallError(obj, property);
/* 1983:     */     }
/* 1984:     */     Object value;
/* 1985:     */     for (;;)
/* 1986:     */     {
/* 1987:2343 */       value = ScriptableObject.getProperty(thisObj, property);
/* 1988:2344 */       if (value != Scriptable.NOT_FOUND) {
/* 1989:     */         break;
/* 1990:     */       }
/* 1991:2347 */       if (!(thisObj instanceof XMLObject)) {
/* 1992:     */         break;
/* 1993:     */       }
/* 1994:2350 */       XMLObject xmlObject = (XMLObject)thisObj;
/* 1995:2351 */       Scriptable extra = xmlObject.getExtraMethodSource(cx);
/* 1996:2352 */       if (extra == null) {
/* 1997:     */         break;
/* 1998:     */       }
/* 1999:2355 */       thisObj = extra;
/* 2000:     */     }
/* 2001:2358 */     if (!(value instanceof Callable))
/* 2002:     */     {
/* 2003:2359 */       Object noSuchMethod = ScriptableObject.getProperty(thisObj, "__noSuchMethod__");
/* 2004:2360 */       if ((noSuchMethod instanceof Callable)) {
/* 2005:2361 */         value = new NoSuchMethodShim((Callable)noSuchMethod, property);
/* 2006:     */       } else {
/* 2007:2363 */         throw notFunctionError(thisObj, value, property);
/* 2008:     */       }
/* 2009:     */     }
/* 2010:2366 */     storeScriptable(cx, thisObj);
/* 2011:2367 */     return (Callable)value;
/* 2012:     */   }
/* 2013:     */   
/* 2014:     */   public static Callable getValueFunctionAndThis(Object value, Context cx)
/* 2015:     */   {
/* 2016:2379 */     if (!(value instanceof Callable)) {
/* 2017:2380 */       throw notFunctionError(value);
/* 2018:     */     }
/* 2019:2383 */     Callable f = (Callable)value;
/* 2020:2384 */     Scriptable thisObj = null;
/* 2021:2385 */     if ((f instanceof Scriptable)) {
/* 2022:2386 */       thisObj = ((Scriptable)f).getParentScope();
/* 2023:     */     }
/* 2024:2388 */     if (thisObj == null)
/* 2025:     */     {
/* 2026:2389 */       if (cx.topCallScope == null) {
/* 2027:2389 */         throw new IllegalStateException();
/* 2028:     */       }
/* 2029:2390 */       thisObj = cx.topCallScope;
/* 2030:     */     }
/* 2031:2392 */     if ((thisObj.getParentScope() != null) && 
/* 2032:2393 */       (!(thisObj instanceof NativeWith))) {
/* 2033:2396 */       if ((thisObj instanceof NativeCall)) {
/* 2034:2398 */         thisObj = ScriptableObject.getTopLevelScope(thisObj);
/* 2035:     */       }
/* 2036:     */     }
/* 2037:2401 */     storeScriptable(cx, thisObj);
/* 2038:2402 */     return f;
/* 2039:     */   }
/* 2040:     */   
/* 2041:     */   public static Ref callRef(Callable function, Scriptable thisObj, Object[] args, Context cx)
/* 2042:     */   {
/* 2043:2417 */     if ((function instanceof RefCallable))
/* 2044:     */     {
/* 2045:2418 */       RefCallable rfunction = (RefCallable)function;
/* 2046:2419 */       Ref ref = rfunction.refCall(cx, thisObj, args);
/* 2047:2420 */       if (ref == null) {
/* 2048:2421 */         throw new IllegalStateException(rfunction.getClass().getName() + ".refCall() returned null");
/* 2049:     */       }
/* 2050:2423 */       return ref;
/* 2051:     */     }
/* 2052:2426 */     String msg = getMessage1("msg.no.ref.from.function", toString(function));
/* 2053:     */     
/* 2054:2428 */     throw constructError("ReferenceError", msg);
/* 2055:     */   }
/* 2056:     */   
/* 2057:     */   public static Scriptable newObject(Object fun, Context cx, Scriptable scope, Object[] args)
/* 2058:     */   {
/* 2059:2439 */     if (!(fun instanceof Function)) {
/* 2060:2440 */       throw notFunctionError(fun);
/* 2061:     */     }
/* 2062:2442 */     Function function = (Function)fun;
/* 2063:2443 */     return function.construct(cx, scope, args);
/* 2064:     */   }
/* 2065:     */   
/* 2066:     */   public static Object callSpecial(Context cx, Callable fun, Scriptable thisObj, Object[] args, Scriptable scope, Scriptable callerThis, int callType, String filename, int lineNumber)
/* 2067:     */   {
/* 2068:2452 */     if (callType == 1)
/* 2069:     */     {
/* 2070:2453 */       if ((thisObj.getParentScope() == null) && (NativeGlobal.isEvalFunction(fun))) {
/* 2071:2454 */         return evalSpecial(cx, scope, callerThis, args, filename, lineNumber);
/* 2072:     */       }
/* 2073:     */     }
/* 2074:2457 */     else if (callType == 2)
/* 2075:     */     {
/* 2076:2458 */       if (NativeWith.isWithFunction(fun)) {
/* 2077:2459 */         throw Context.reportRuntimeError1("msg.only.from.new", "With");
/* 2078:     */       }
/* 2079:     */     }
/* 2080:     */     else {
/* 2081:2463 */       throw Kit.codeBug();
/* 2082:     */     }
/* 2083:2466 */     return fun.call(cx, scope, thisObj, args);
/* 2084:     */   }
/* 2085:     */   
/* 2086:     */   public static Object newSpecial(Context cx, Object fun, Object[] args, Scriptable scope, int callType)
/* 2087:     */   {
/* 2088:2473 */     if (callType == 1)
/* 2089:     */     {
/* 2090:2474 */       if (NativeGlobal.isEvalFunction(fun)) {
/* 2091:2475 */         throw typeError1("msg.not.ctor", "eval");
/* 2092:     */       }
/* 2093:     */     }
/* 2094:2477 */     else if (callType == 2)
/* 2095:     */     {
/* 2096:2478 */       if (NativeWith.isWithFunction(fun)) {
/* 2097:2479 */         return NativeWith.newWithSpecial(cx, scope, args);
/* 2098:     */       }
/* 2099:     */     }
/* 2100:     */     else {
/* 2101:2482 */       throw Kit.codeBug();
/* 2102:     */     }
/* 2103:2485 */     return newObject(fun, cx, scope, args);
/* 2104:     */   }
/* 2105:     */   
/* 2106:     */   public static Object applyOrCall(boolean isApply, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 2107:     */   {
/* 2108:2497 */     int L = args.length;
/* 2109:2498 */     Callable function = getCallable(thisObj);
/* 2110:     */     
/* 2111:2500 */     Scriptable callThis = null;
/* 2112:2501 */     if (L != 0) {
/* 2113:2502 */       callThis = toObjectOrNull(cx, args[0]);
/* 2114:     */     }
/* 2115:2504 */     if (callThis == null) {
/* 2116:2506 */       callThis = getTopCallScope(cx);
/* 2117:     */     }
/* 2118:     */     Object[] callArgs;
/* 2119:     */     Object[] callArgs;
/* 2120:2510 */     if (isApply)
/* 2121:     */     {
/* 2122:2512 */       callArgs = L <= 1 ? emptyArgs : getApplyArguments(cx, args[1]);
/* 2123:     */     }
/* 2124:     */     else
/* 2125:     */     {
/* 2126:     */       Object[] callArgs;
/* 2127:2516 */       if (L <= 1)
/* 2128:     */       {
/* 2129:2517 */         callArgs = emptyArgs;
/* 2130:     */       }
/* 2131:     */       else
/* 2132:     */       {
/* 2133:2519 */         callArgs = new Object[L - 1];
/* 2134:2520 */         System.arraycopy(args, 1, callArgs, 0, L - 1);
/* 2135:     */       }
/* 2136:     */     }
/* 2137:2524 */     return function.call(cx, scope, callThis, callArgs);
/* 2138:     */   }
/* 2139:     */   
/* 2140:     */   static Object[] getApplyArguments(Context cx, Object arg1)
/* 2141:     */   {
/* 2142:2529 */     if ((arg1 == null) || (arg1 == Undefined.instance)) {
/* 2143:2530 */       return emptyArgs;
/* 2144:     */     }
/* 2145:2531 */     if (((arg1 instanceof NativeArray)) || ((arg1 instanceof Arguments))) {
/* 2146:2532 */       return cx.getElements((Scriptable)arg1);
/* 2147:     */     }
/* 2148:2534 */     throw typeError0("msg.arg.isnt.array");
/* 2149:     */   }
/* 2150:     */   
/* 2151:     */   static Callable getCallable(Scriptable thisObj)
/* 2152:     */   {
/* 2153:     */     Callable function;
/* 2154:     */     Callable function;
/* 2155:2541 */     if ((thisObj instanceof Callable))
/* 2156:     */     {
/* 2157:2542 */       function = (Callable)thisObj;
/* 2158:     */     }
/* 2159:     */     else
/* 2160:     */     {
/* 2161:2544 */       Object value = thisObj.getDefaultValue(FunctionClass);
/* 2162:2545 */       if (!(value instanceof Callable)) {
/* 2163:2546 */         throw notFunctionError(value, thisObj);
/* 2164:     */       }
/* 2165:2548 */       function = (Callable)value;
/* 2166:     */     }
/* 2167:2550 */     return function;
/* 2168:     */   }
/* 2169:     */   
/* 2170:     */   public static Object evalSpecial(Context cx, Scriptable scope, Object thisArg, Object[] args, String filename, int lineNumber)
/* 2171:     */   {
/* 2172:2562 */     if (args.length < 1) {
/* 2173:2563 */       return Undefined.instance;
/* 2174:     */     }
/* 2175:2564 */     Object x = args[0];
/* 2176:2565 */     if (!(x instanceof String))
/* 2177:     */     {
/* 2178:2566 */       if ((cx.hasFeature(11)) || (cx.hasFeature(9))) {
/* 2179:2569 */         throw Context.reportRuntimeError0("msg.eval.nonstring.strict");
/* 2180:     */       }
/* 2181:2571 */       String message = getMessage0("msg.eval.nonstring");
/* 2182:2572 */       Context.reportWarning(message);
/* 2183:2573 */       return x;
/* 2184:     */     }
/* 2185:2575 */     if (filename == null)
/* 2186:     */     {
/* 2187:2576 */       int[] linep = new int[1];
/* 2188:2577 */       filename = Context.getSourcePositionFromStack(linep);
/* 2189:2578 */       if (filename != null) {
/* 2190:2579 */         lineNumber = linep[0];
/* 2191:     */       } else {
/* 2192:2581 */         filename = "";
/* 2193:     */       }
/* 2194:     */     }
/* 2195:2584 */     String sourceName = makeUrlForGeneratedScript(true, filename, lineNumber);
/* 2196:     */     
/* 2197:     */ 
/* 2198:     */ 
/* 2199:2588 */     ErrorReporter reporter = DefaultErrorReporter.forEval(cx.getErrorReporter());
/* 2200:     */     
/* 2201:2590 */     Evaluator evaluator = Context.createInterpreter();
/* 2202:2591 */     if (evaluator == null) {
/* 2203:2592 */       throw new JavaScriptException("Interpreter not present", filename, lineNumber);
/* 2204:     */     }
/* 2205:2598 */     Script script = cx.compileString((String)x, evaluator, reporter, sourceName, 1, null);
/* 2206:     */     
/* 2207:2600 */     evaluator.setEvalScriptFlag(script);
/* 2208:2601 */     Callable c = (Callable)script;
/* 2209:2602 */     return c.call(cx, scope, (Scriptable)thisArg, emptyArgs);
/* 2210:     */   }
/* 2211:     */   
/* 2212:     */   public static String typeof(Object value)
/* 2213:     */   {
/* 2214:2610 */     if (value == null) {
/* 2215:2611 */       return "object";
/* 2216:     */     }
/* 2217:2612 */     if (value == Undefined.instance) {
/* 2218:2613 */       return "undefined";
/* 2219:     */     }
/* 2220:2614 */     if ((value instanceof ScriptableObject)) {
/* 2221:2615 */       return ((ScriptableObject)value).getTypeOf();
/* 2222:     */     }
/* 2223:2616 */     if ((value instanceof Delegator)) {
/* 2224:2617 */       return typeof(((Delegator)value).getDelegee());
/* 2225:     */     }
/* 2226:2618 */     if ((value instanceof Scriptable)) {
/* 2227:2619 */       return (value instanceof Callable) ? "function" : "object";
/* 2228:     */     }
/* 2229:2620 */     if ((value instanceof String)) {
/* 2230:2621 */       return "string";
/* 2231:     */     }
/* 2232:2622 */     if ((value instanceof Number)) {
/* 2233:2623 */       return "number";
/* 2234:     */     }
/* 2235:2624 */     if ((value instanceof Boolean)) {
/* 2236:2625 */       return "boolean";
/* 2237:     */     }
/* 2238:2626 */     throw errorWithClassName("msg.invalid.type", value);
/* 2239:     */   }
/* 2240:     */   
/* 2241:     */   public static String typeofName(Scriptable scope, String id)
/* 2242:     */   {
/* 2243:2634 */     Context cx = Context.getContext();
/* 2244:2635 */     Scriptable val = bind(cx, scope, id);
/* 2245:2636 */     if (val == null) {
/* 2246:2637 */       return "undefined";
/* 2247:     */     }
/* 2248:2638 */     return typeof(getObjectProp(val, id, cx));
/* 2249:     */   }
/* 2250:     */   
/* 2251:     */   public static Object add(Object val1, Object val2, Context cx)
/* 2252:     */   {
/* 2253:2655 */     if (((val1 instanceof Number)) && ((val2 instanceof Number))) {
/* 2254:2656 */       return wrapNumber(((Number)val1).doubleValue() + ((Number)val2).doubleValue());
/* 2255:     */     }
/* 2256:2659 */     if ((val1 instanceof XMLObject))
/* 2257:     */     {
/* 2258:2660 */       Object test = ((XMLObject)val1).addValues(cx, true, val2);
/* 2259:2661 */       if (test != Scriptable.NOT_FOUND) {
/* 2260:2662 */         return test;
/* 2261:     */       }
/* 2262:     */     }
/* 2263:2665 */     if ((val2 instanceof XMLObject))
/* 2264:     */     {
/* 2265:2666 */       Object test = ((XMLObject)val2).addValues(cx, false, val1);
/* 2266:2667 */       if (test != Scriptable.NOT_FOUND) {
/* 2267:2668 */         return test;
/* 2268:     */       }
/* 2269:     */     }
/* 2270:2671 */     if ((val1 instanceof Scriptable)) {
/* 2271:2672 */       val1 = ((Scriptable)val1).getDefaultValue(null);
/* 2272:     */     }
/* 2273:2673 */     if ((val2 instanceof Scriptable)) {
/* 2274:2674 */       val2 = ((Scriptable)val2).getDefaultValue(null);
/* 2275:     */     }
/* 2276:2675 */     if ((!(val1 instanceof String)) && (!(val2 instanceof String)))
/* 2277:     */     {
/* 2278:2676 */       if (((val1 instanceof Number)) && ((val2 instanceof Number))) {
/* 2279:2677 */         return wrapNumber(((Number)val1).doubleValue() + ((Number)val2).doubleValue());
/* 2280:     */       }
/* 2281:2680 */       return wrapNumber(toNumber(val1) + toNumber(val2));
/* 2282:     */     }
/* 2283:2681 */     return toString(val1).concat(toString(val2));
/* 2284:     */   }
/* 2285:     */   
/* 2286:     */   public static String add(String val1, Object val2)
/* 2287:     */   {
/* 2288:2685 */     return val1.concat(toString(val2));
/* 2289:     */   }
/* 2290:     */   
/* 2291:     */   public static String add(Object val1, String val2)
/* 2292:     */   {
/* 2293:2689 */     return toString(val1).concat(val2);
/* 2294:     */   }
/* 2295:     */   
/* 2296:     */   /**
/* 2297:     */    * @deprecated
/* 2298:     */    */
/* 2299:     */   public static Object nameIncrDecr(Scriptable scopeChain, String id, int incrDecrMask)
/* 2300:     */   {
/* 2301:2698 */     return nameIncrDecr(scopeChain, id, Context.getContext(), incrDecrMask);
/* 2302:     */   }
/* 2303:     */   
/* 2304:     */   public static Object nameIncrDecr(Scriptable scopeChain, String id, Context cx, int incrDecrMask)
/* 2305:     */   {
/* 2306:     */     Scriptable target;
/* 2307:     */     Object value;
/* 2308:     */     do
/* 2309:     */     {
/* 2310:2708 */       if ((cx.useDynamicScope) && (scopeChain.getParentScope() == null)) {
/* 2311:2709 */         scopeChain = checkDynamicScope(cx.topCallScope, scopeChain);
/* 2312:     */       }
/* 2313:2711 */       target = scopeChain;
/* 2314:     */       do
/* 2315:     */       {
/* 2316:2713 */         value = target.get(id, scopeChain);
/* 2317:2714 */         if (value != Scriptable.NOT_FOUND) {
/* 2318:     */           break;
/* 2319:     */         }
/* 2320:2717 */         target = target.getPrototype();
/* 2321:2718 */       } while (target != null);
/* 2322:2719 */       scopeChain = scopeChain.getParentScope();
/* 2323:2720 */     } while (scopeChain != null);
/* 2324:2721 */     throw notFoundError(scopeChain, id);
/* 2325:     */     
/* 2326:2723 */     return doScriptableIncrDecr(target, id, scopeChain, value, incrDecrMask);
/* 2327:     */   }
/* 2328:     */   
/* 2329:     */   public static Object propIncrDecr(Object obj, String id, Context cx, int incrDecrMask)
/* 2330:     */   {
/* 2331:2730 */     Scriptable start = toObjectOrNull(cx, obj);
/* 2332:2731 */     if (start == null) {
/* 2333:2732 */       throw undefReadError(obj, id);
/* 2334:     */     }
/* 2335:2735 */     Scriptable target = start;
/* 2336:     */     Object value;
/* 2337:     */     do
/* 2338:     */     {
/* 2339:2739 */       value = target.get(id, start);
/* 2340:2740 */       if (value != Scriptable.NOT_FOUND) {
/* 2341:     */         break;
/* 2342:     */       }
/* 2343:2743 */       target = target.getPrototype();
/* 2344:2744 */     } while (target != null);
/* 2345:2745 */     start.put(id, start, NaNobj);
/* 2346:2746 */     return NaNobj;
/* 2347:     */     
/* 2348:2748 */     return doScriptableIncrDecr(target, id, start, value, incrDecrMask);
/* 2349:     */   }
/* 2350:     */   
/* 2351:     */   private static Object doScriptableIncrDecr(Scriptable target, String id, Scriptable protoChainStart, Object value, int incrDecrMask)
/* 2352:     */   {
/* 2353:2758 */     boolean post = (incrDecrMask & 0x2) != 0;
/* 2354:     */     double number;
/* 2355:     */     double number;
/* 2356:2760 */     if ((value instanceof Number))
/* 2357:     */     {
/* 2358:2761 */       number = ((Number)value).doubleValue();
/* 2359:     */     }
/* 2360:     */     else
/* 2361:     */     {
/* 2362:2763 */       number = toNumber(value);
/* 2363:2764 */       if (post) {
/* 2364:2766 */         value = wrapNumber(number);
/* 2365:     */       }
/* 2366:     */     }
/* 2367:2769 */     if ((incrDecrMask & 0x1) == 0) {
/* 2368:2770 */       number += 1.0D;
/* 2369:     */     } else {
/* 2370:2772 */       number -= 1.0D;
/* 2371:     */     }
/* 2372:2774 */     Number result = wrapNumber(number);
/* 2373:2775 */     target.put(id, protoChainStart, result);
/* 2374:2776 */     if (post) {
/* 2375:2777 */       return value;
/* 2376:     */     }
/* 2377:2779 */     return result;
/* 2378:     */   }
/* 2379:     */   
/* 2380:     */   public static Object elemIncrDecr(Object obj, Object index, Context cx, int incrDecrMask)
/* 2381:     */   {
/* 2382:2786 */     Object value = getObjectElem(obj, index, cx);
/* 2383:2787 */     boolean post = (incrDecrMask & 0x2) != 0;
/* 2384:     */     double number;
/* 2385:     */     double number;
/* 2386:2789 */     if ((value instanceof Number))
/* 2387:     */     {
/* 2388:2790 */       number = ((Number)value).doubleValue();
/* 2389:     */     }
/* 2390:     */     else
/* 2391:     */     {
/* 2392:2792 */       number = toNumber(value);
/* 2393:2793 */       if (post) {
/* 2394:2795 */         value = wrapNumber(number);
/* 2395:     */       }
/* 2396:     */     }
/* 2397:2798 */     if ((incrDecrMask & 0x1) == 0) {
/* 2398:2799 */       number += 1.0D;
/* 2399:     */     } else {
/* 2400:2801 */       number -= 1.0D;
/* 2401:     */     }
/* 2402:2803 */     Number result = wrapNumber(number);
/* 2403:2804 */     setObjectElem(obj, index, result, cx);
/* 2404:2805 */     if (post) {
/* 2405:2806 */       return value;
/* 2406:     */     }
/* 2407:2808 */     return result;
/* 2408:     */   }
/* 2409:     */   
/* 2410:     */   public static Object refIncrDecr(Ref ref, Context cx, int incrDecrMask)
/* 2411:     */   {
/* 2412:2814 */     Object value = ref.get(cx);
/* 2413:2815 */     boolean post = (incrDecrMask & 0x2) != 0;
/* 2414:     */     double number;
/* 2415:     */     double number;
/* 2416:2817 */     if ((value instanceof Number))
/* 2417:     */     {
/* 2418:2818 */       number = ((Number)value).doubleValue();
/* 2419:     */     }
/* 2420:     */     else
/* 2421:     */     {
/* 2422:2820 */       number = toNumber(value);
/* 2423:2821 */       if (post) {
/* 2424:2823 */         value = wrapNumber(number);
/* 2425:     */       }
/* 2426:     */     }
/* 2427:2826 */     if ((incrDecrMask & 0x1) == 0) {
/* 2428:2827 */       number += 1.0D;
/* 2429:     */     } else {
/* 2430:2829 */       number -= 1.0D;
/* 2431:     */     }
/* 2432:2831 */     Number result = wrapNumber(number);
/* 2433:2832 */     ref.set(cx, result);
/* 2434:2833 */     if (post) {
/* 2435:2834 */       return value;
/* 2436:     */     }
/* 2437:2836 */     return result;
/* 2438:     */   }
/* 2439:     */   
/* 2440:     */   public static Object toPrimitive(Object val)
/* 2441:     */   {
/* 2442:2841 */     return toPrimitive(val, null);
/* 2443:     */   }
/* 2444:     */   
/* 2445:     */   public static Object toPrimitive(Object val, Class<?> typeHint)
/* 2446:     */   {
/* 2447:2846 */     if (!(val instanceof Scriptable)) {
/* 2448:2847 */       return val;
/* 2449:     */     }
/* 2450:2849 */     Scriptable s = (Scriptable)val;
/* 2451:2850 */     Object result = s.getDefaultValue(typeHint);
/* 2452:2851 */     if ((result instanceof Scriptable)) {
/* 2453:2852 */       throw typeError0("msg.bad.default.value");
/* 2454:     */     }
/* 2455:2853 */     return result;
/* 2456:     */   }
/* 2457:     */   
/* 2458:     */   public static boolean eq(Object x, Object y)
/* 2459:     */   {
/* 2460:2863 */     if ((x == null) || (x == Undefined.instance))
/* 2461:     */     {
/* 2462:2864 */       if ((y == null) || (y == Undefined.instance)) {
/* 2463:2865 */         return true;
/* 2464:     */       }
/* 2465:2867 */       if ((y instanceof ScriptableObject))
/* 2466:     */       {
/* 2467:2868 */         Object test = ((ScriptableObject)y).equivalentValues(x);
/* 2468:2869 */         if (test != Scriptable.NOT_FOUND) {
/* 2469:2870 */           return ((Boolean)test).booleanValue();
/* 2470:     */         }
/* 2471:     */       }
/* 2472:2873 */       return false;
/* 2473:     */     }
/* 2474:2874 */     if ((x instanceof Number)) {
/* 2475:2875 */       return eqNumber(((Number)x).doubleValue(), y);
/* 2476:     */     }
/* 2477:2876 */     if ((x instanceof String)) {
/* 2478:2877 */       return eqString((String)x, y);
/* 2479:     */     }
/* 2480:2878 */     if ((x instanceof Boolean))
/* 2481:     */     {
/* 2482:2879 */       boolean b = ((Boolean)x).booleanValue();
/* 2483:2880 */       if ((y instanceof Boolean)) {
/* 2484:2881 */         return b == ((Boolean)y).booleanValue();
/* 2485:     */       }
/* 2486:2883 */       if ((y instanceof ScriptableObject))
/* 2487:     */       {
/* 2488:2884 */         Object test = ((ScriptableObject)y).equivalentValues(x);
/* 2489:2885 */         if (test != Scriptable.NOT_FOUND) {
/* 2490:2886 */           return ((Boolean)test).booleanValue();
/* 2491:     */         }
/* 2492:     */       }
/* 2493:2889 */       return eqNumber(b ? 1.0D : 0.0D, y);
/* 2494:     */     }
/* 2495:2890 */     if ((x instanceof Scriptable))
/* 2496:     */     {
/* 2497:2891 */       if ((y instanceof Scriptable))
/* 2498:     */       {
/* 2499:2892 */         if (x == y) {
/* 2500:2893 */           return true;
/* 2501:     */         }
/* 2502:2895 */         if ((x instanceof ScriptableObject))
/* 2503:     */         {
/* 2504:2896 */           Object test = ((ScriptableObject)x).equivalentValues(y);
/* 2505:2897 */           if (test != Scriptable.NOT_FOUND) {
/* 2506:2898 */             return ((Boolean)test).booleanValue();
/* 2507:     */           }
/* 2508:     */         }
/* 2509:2901 */         if ((y instanceof ScriptableObject))
/* 2510:     */         {
/* 2511:2902 */           Object test = ((ScriptableObject)y).equivalentValues(x);
/* 2512:2903 */           if (test != Scriptable.NOT_FOUND) {
/* 2513:2904 */             return ((Boolean)test).booleanValue();
/* 2514:     */           }
/* 2515:     */         }
/* 2516:2907 */         if (((x instanceof Wrapper)) && ((y instanceof Wrapper)))
/* 2517:     */         {
/* 2518:2910 */           Object unwrappedX = ((Wrapper)x).unwrap();
/* 2519:2911 */           Object unwrappedY = ((Wrapper)y).unwrap();
/* 2520:2912 */           return (unwrappedX == unwrappedY) || ((isPrimitive(unwrappedX)) && (isPrimitive(unwrappedY)) && (eq(unwrappedX, unwrappedY)));
/* 2521:     */         }
/* 2522:2917 */         return false;
/* 2523:     */       }
/* 2524:2918 */       if ((y instanceof Boolean))
/* 2525:     */       {
/* 2526:2919 */         if ((x instanceof ScriptableObject))
/* 2527:     */         {
/* 2528:2920 */           Object test = ((ScriptableObject)x).equivalentValues(y);
/* 2529:2921 */           if (test != Scriptable.NOT_FOUND) {
/* 2530:2922 */             return ((Boolean)test).booleanValue();
/* 2531:     */           }
/* 2532:     */         }
/* 2533:2925 */         double d = ((Boolean)y).booleanValue() ? 1.0D : 0.0D;
/* 2534:2926 */         return eqNumber(d, x);
/* 2535:     */       }
/* 2536:2927 */       if ((y instanceof Number)) {
/* 2537:2928 */         return eqNumber(((Number)y).doubleValue(), x);
/* 2538:     */       }
/* 2539:2929 */       if ((y instanceof String)) {
/* 2540:2930 */         return eqString((String)y, x);
/* 2541:     */       }
/* 2542:2933 */       return false;
/* 2543:     */     }
/* 2544:2935 */     warnAboutNonJSObject(x);
/* 2545:2936 */     return x == y;
/* 2546:     */   }
/* 2547:     */   
/* 2548:     */   public static boolean isPrimitive(Object obj)
/* 2549:     */   {
/* 2550:2941 */     return (obj == null) || (obj == Undefined.instance) || ((obj instanceof Number)) || ((obj instanceof String)) || ((obj instanceof Boolean));
/* 2551:     */   }
/* 2552:     */   
/* 2553:     */   static boolean eqNumber(double x, Object y)
/* 2554:     */   {
/* 2555:     */     for (;;)
/* 2556:     */     {
/* 2557:2949 */       if ((y == null) || (y == Undefined.instance)) {
/* 2558:2950 */         return false;
/* 2559:     */       }
/* 2560:2951 */       if ((y instanceof Number)) {
/* 2561:2952 */         return x == ((Number)y).doubleValue();
/* 2562:     */       }
/* 2563:2953 */       if ((y instanceof String)) {
/* 2564:2954 */         return x == toNumber(y);
/* 2565:     */       }
/* 2566:2955 */       if ((y instanceof Boolean)) {
/* 2567:2956 */         return x == (((Boolean)y).booleanValue() ? 1.0D : 0.0D);
/* 2568:     */       }
/* 2569:2957 */       if (!(y instanceof Scriptable)) {
/* 2570:     */         break;
/* 2571:     */       }
/* 2572:2958 */       if ((y instanceof ScriptableObject))
/* 2573:     */       {
/* 2574:2959 */         Object xval = wrapNumber(x);
/* 2575:2960 */         Object test = ((ScriptableObject)y).equivalentValues(xval);
/* 2576:2961 */         if (test != Scriptable.NOT_FOUND) {
/* 2577:2962 */           return ((Boolean)test).booleanValue();
/* 2578:     */         }
/* 2579:     */       }
/* 2580:2965 */       y = toPrimitive(y);
/* 2581:     */     }
/* 2582:2967 */     warnAboutNonJSObject(y);
/* 2583:2968 */     return false;
/* 2584:     */   }
/* 2585:     */   
/* 2586:     */   private static boolean eqString(String x, Object y)
/* 2587:     */   {
/* 2588:     */     for (;;)
/* 2589:     */     {
/* 2590:2976 */       if ((y == null) || (y == Undefined.instance)) {
/* 2591:2977 */         return false;
/* 2592:     */       }
/* 2593:2978 */       if ((y instanceof String)) {
/* 2594:2979 */         return x.equals(y);
/* 2595:     */       }
/* 2596:2980 */       if ((y instanceof Number)) {
/* 2597:2981 */         return toNumber(x) == ((Number)y).doubleValue();
/* 2598:     */       }
/* 2599:2982 */       if ((y instanceof Boolean)) {
/* 2600:2983 */         return toNumber(x) == (((Boolean)y).booleanValue() ? 1.0D : 0.0D);
/* 2601:     */       }
/* 2602:2984 */       if (!(y instanceof Scriptable)) {
/* 2603:     */         break;
/* 2604:     */       }
/* 2605:2985 */       if ((y instanceof ScriptableObject))
/* 2606:     */       {
/* 2607:2986 */         Object test = ((ScriptableObject)y).equivalentValues(x);
/* 2608:2987 */         if (test != Scriptable.NOT_FOUND) {
/* 2609:2988 */           return ((Boolean)test).booleanValue();
/* 2610:     */         }
/* 2611:     */       }
/* 2612:2991 */       y = toPrimitive(y);
/* 2613:     */     }
/* 2614:2994 */     warnAboutNonJSObject(y);
/* 2615:2995 */     return false;
/* 2616:     */   }
/* 2617:     */   
/* 2618:     */   public static boolean shallowEq(Object x, Object y)
/* 2619:     */   {
/* 2620:3001 */     if (x == y)
/* 2621:     */     {
/* 2622:3002 */       if (!(x instanceof Number)) {
/* 2623:3003 */         return true;
/* 2624:     */       }
/* 2625:3006 */       double d = ((Number)x).doubleValue();
/* 2626:3007 */       return d == d;
/* 2627:     */     }
/* 2628:3009 */     if ((x == null) || (x == Undefined.instance)) {
/* 2629:3010 */       return false;
/* 2630:     */     }
/* 2631:3011 */     if ((x instanceof Number))
/* 2632:     */     {
/* 2633:3012 */       if ((y instanceof Number)) {
/* 2634:3013 */         return ((Number)x).doubleValue() == ((Number)y).doubleValue();
/* 2635:     */       }
/* 2636:     */     }
/* 2637:3015 */     else if ((x instanceof String))
/* 2638:     */     {
/* 2639:3016 */       if ((y instanceof String)) {
/* 2640:3017 */         return x.equals(y);
/* 2641:     */       }
/* 2642:     */     }
/* 2643:3019 */     else if ((x instanceof Boolean))
/* 2644:     */     {
/* 2645:3020 */       if ((y instanceof Boolean)) {
/* 2646:3021 */         return x.equals(y);
/* 2647:     */       }
/* 2648:     */     }
/* 2649:3023 */     else if ((x instanceof Scriptable))
/* 2650:     */     {
/* 2651:3024 */       if (((x instanceof Wrapper)) && ((y instanceof Wrapper))) {
/* 2652:3025 */         return ((Wrapper)x).unwrap() == ((Wrapper)y).unwrap();
/* 2653:     */       }
/* 2654:     */     }
/* 2655:     */     else
/* 2656:     */     {
/* 2657:3028 */       warnAboutNonJSObject(x);
/* 2658:3029 */       return x == y;
/* 2659:     */     }
/* 2660:3031 */     return false;
/* 2661:     */   }
/* 2662:     */   
/* 2663:     */   public static boolean instanceOf(Object a, Object b, Context cx)
/* 2664:     */   {
/* 2665:3042 */     if (!(b instanceof Scriptable)) {
/* 2666:3043 */       throw typeError0("msg.instanceof.not.object");
/* 2667:     */     }
/* 2668:3047 */     if (!(a instanceof Scriptable)) {
/* 2669:3048 */       return false;
/* 2670:     */     }
/* 2671:3050 */     return ((Scriptable)b).hasInstance((Scriptable)a);
/* 2672:     */   }
/* 2673:     */   
/* 2674:     */   public static boolean jsDelegatesTo(Scriptable lhs, Scriptable rhs)
/* 2675:     */   {
/* 2676:3059 */     Scriptable proto = lhs.getPrototype();
/* 2677:3061 */     while (proto != null)
/* 2678:     */     {
/* 2679:3062 */       if (proto.equals(rhs)) {
/* 2680:3062 */         return true;
/* 2681:     */       }
/* 2682:3063 */       proto = proto.getPrototype();
/* 2683:     */     }
/* 2684:3066 */     return false;
/* 2685:     */   }
/* 2686:     */   
/* 2687:     */   public static boolean in(Object a, Object b, Context cx)
/* 2688:     */   {
/* 2689:3085 */     if (!(b instanceof Scriptable)) {
/* 2690:3086 */       throw typeError0("msg.instanceof.not.object");
/* 2691:     */     }
/* 2692:3089 */     return hasObjectElem((Scriptable)b, a, cx);
/* 2693:     */   }
/* 2694:     */   
/* 2695:     */   public static boolean cmp_LT(Object val1, Object val2)
/* 2696:     */   {
/* 2697:     */     double d2;
/* 2698:     */     double d1;
/* 2699:     */     double d2;
/* 2700:3095 */     if (((val1 instanceof Number)) && ((val2 instanceof Number)))
/* 2701:     */     {
/* 2702:3096 */       double d1 = ((Number)val1).doubleValue();
/* 2703:3097 */       d2 = ((Number)val2).doubleValue();
/* 2704:     */     }
/* 2705:     */     else
/* 2706:     */     {
/* 2707:3099 */       if ((val1 instanceof Scriptable)) {
/* 2708:3100 */         val1 = ((Scriptable)val1).getDefaultValue(NumberClass);
/* 2709:     */       }
/* 2710:3101 */       if ((val2 instanceof Scriptable)) {
/* 2711:3102 */         val2 = ((Scriptable)val2).getDefaultValue(NumberClass);
/* 2712:     */       }
/* 2713:3103 */       if (((val1 instanceof String)) && ((val2 instanceof String))) {
/* 2714:3104 */         return ((String)val1).compareTo((String)val2) < 0;
/* 2715:     */       }
/* 2716:3106 */       d1 = toNumber(val1);
/* 2717:3107 */       d2 = toNumber(val2);
/* 2718:     */     }
/* 2719:3109 */     return d1 < d2;
/* 2720:     */   }
/* 2721:     */   
/* 2722:     */   public static boolean cmp_LE(Object val1, Object val2)
/* 2723:     */   {
/* 2724:     */     double d2;
/* 2725:     */     double d1;
/* 2726:     */     double d2;
/* 2727:3115 */     if (((val1 instanceof Number)) && ((val2 instanceof Number)))
/* 2728:     */     {
/* 2729:3116 */       double d1 = ((Number)val1).doubleValue();
/* 2730:3117 */       d2 = ((Number)val2).doubleValue();
/* 2731:     */     }
/* 2732:     */     else
/* 2733:     */     {
/* 2734:3119 */       if ((val1 instanceof Scriptable)) {
/* 2735:3120 */         val1 = ((Scriptable)val1).getDefaultValue(NumberClass);
/* 2736:     */       }
/* 2737:3121 */       if ((val2 instanceof Scriptable)) {
/* 2738:3122 */         val2 = ((Scriptable)val2).getDefaultValue(NumberClass);
/* 2739:     */       }
/* 2740:3123 */       if (((val1 instanceof String)) && ((val2 instanceof String))) {
/* 2741:3124 */         return ((String)val1).compareTo((String)val2) <= 0;
/* 2742:     */       }
/* 2743:3126 */       d1 = toNumber(val1);
/* 2744:3127 */       d2 = toNumber(val2);
/* 2745:     */     }
/* 2746:3129 */     return d1 <= d2;
/* 2747:     */   }
/* 2748:     */   
/* 2749:     */   public static ScriptableObject getGlobal(Context cx)
/* 2750:     */   {
/* 2751:3137 */     String GLOBAL_CLASS = "net.sourceforge.htmlunit.corejs.javascript.tools.shell.Global";
/* 2752:3138 */     Class<?> globalClass = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.tools.shell.Global");
/* 2753:3139 */     if (globalClass != null) {
/* 2754:     */       try
/* 2755:     */       {
/* 2756:3141 */         Class<?>[] parm = { ContextClass };
/* 2757:3142 */         Constructor<?> globalClassCtor = globalClass.getConstructor(parm);
/* 2758:3143 */         Object[] arg = { cx };
/* 2759:3144 */         return (ScriptableObject)globalClassCtor.newInstance(arg);
/* 2760:     */       }
/* 2761:     */       catch (RuntimeException e)
/* 2762:     */       {
/* 2763:3147 */         throw e;
/* 2764:     */       }
/* 2765:     */       catch (Exception e) {}
/* 2766:     */     }
/* 2767:3153 */     return new ImporterTopLevel(cx);
/* 2768:     */   }
/* 2769:     */   
/* 2770:     */   public static boolean hasTopCall(Context cx)
/* 2771:     */   {
/* 2772:3158 */     return cx.topCallScope != null;
/* 2773:     */   }
/* 2774:     */   
/* 2775:     */   public static Scriptable getTopCallScope(Context cx)
/* 2776:     */   {
/* 2777:3163 */     Scriptable scope = cx.topCallScope;
/* 2778:3164 */     if (scope == null) {
/* 2779:3165 */       throw new IllegalStateException();
/* 2780:     */     }
/* 2781:3167 */     return scope;
/* 2782:     */   }
/* 2783:     */   
/* 2784:     */   public static Object doTopCall(Callable callable, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 2785:     */   {
/* 2786:3174 */     if (scope == null) {
/* 2787:3175 */       throw new IllegalArgumentException();
/* 2788:     */     }
/* 2789:3176 */     if (cx.topCallScope != null) {
/* 2790:3176 */       throw new IllegalStateException();
/* 2791:     */     }
/* 2792:3179 */     cx.topCallScope = ScriptableObject.getTopLevelScope(scope);
/* 2793:3180 */     cx.useDynamicScope = cx.hasFeature(7);
/* 2794:3181 */     ContextFactory f = cx.getFactory();
/* 2795:     */     Object result;
/* 2796:     */     try
/* 2797:     */     {
/* 2798:3183 */       result = f.doTopCall(callable, cx, scope, thisObj, args);
/* 2799:     */     }
/* 2800:     */     finally
/* 2801:     */     {
/* 2802:3185 */       cx.topCallScope = null;
/* 2803:     */       
/* 2804:3187 */       cx.cachedXMLLib = null;
/* 2805:3189 */       if (cx.currentActivationCall != null) {
/* 2806:3192 */         throw new IllegalStateException();
/* 2807:     */       }
/* 2808:     */     }
/* 2809:3195 */     return result;
/* 2810:     */   }
/* 2811:     */   
/* 2812:     */   static Scriptable checkDynamicScope(Scriptable possibleDynamicScope, Scriptable staticTopScope)
/* 2813:     */   {
/* 2814:3208 */     if (possibleDynamicScope == staticTopScope) {
/* 2815:3209 */       return possibleDynamicScope;
/* 2816:     */     }
/* 2817:3211 */     Scriptable proto = possibleDynamicScope;
/* 2818:     */     do
/* 2819:     */     {
/* 2820:3213 */       proto = proto.getPrototype();
/* 2821:3214 */       if (proto == staticTopScope) {
/* 2822:3215 */         return possibleDynamicScope;
/* 2823:     */       }
/* 2824:3217 */     } while (proto != null);
/* 2825:3218 */     return staticTopScope;
/* 2826:     */   }
/* 2827:     */   
/* 2828:     */   public static void addInstructionCount(Context cx, int instructionsToAdd)
/* 2829:     */   {
/* 2830:3225 */     cx.instructionCount += instructionsToAdd;
/* 2831:3226 */     if (cx.instructionCount > cx.instructionThreshold)
/* 2832:     */     {
/* 2833:3228 */       cx.observeInstructionCount(cx.instructionCount);
/* 2834:3229 */       cx.instructionCount = 0;
/* 2835:     */     }
/* 2836:     */   }
/* 2837:     */   
/* 2838:     */   public static void initScript(NativeFunction funObj, Scriptable thisObj, Context cx, Scriptable scope, boolean evalScript)
/* 2839:     */   {
/* 2840:3237 */     if (cx.topCallScope == null) {
/* 2841:3238 */       throw new IllegalStateException();
/* 2842:     */     }
/* 2843:3240 */     int varCount = funObj.getParamAndVarCount();
/* 2844:     */     Scriptable varScope;
/* 2845:     */     int i;
/* 2846:3241 */     if (varCount != 0)
/* 2847:     */     {
/* 2848:3243 */       varScope = scope;
/* 2849:3246 */       while ((varScope instanceof NativeWith)) {
/* 2850:3247 */         varScope = varScope.getParentScope();
/* 2851:     */       }
/* 2852:3250 */       for (i = varCount; i-- != 0;)
/* 2853:     */       {
/* 2854:3251 */         String name = funObj.getParamOrVarName(i);
/* 2855:3252 */         boolean isConst = funObj.getParamOrVarConst(i);
/* 2856:3255 */         if (!ScriptableObject.hasProperty(scope, name))
/* 2857:     */         {
/* 2858:3256 */           if (!evalScript)
/* 2859:     */           {
/* 2860:3258 */             if (isConst) {
/* 2861:3259 */               ScriptableObject.defineConstProperty(varScope, name);
/* 2862:     */             } else {
/* 2863:3261 */               ScriptableObject.defineProperty(varScope, name, Undefined.instance, 4);
/* 2864:     */             }
/* 2865:     */           }
/* 2866:     */           else {
/* 2867:3265 */             varScope.put(name, varScope, Undefined.instance);
/* 2868:     */           }
/* 2869:     */         }
/* 2870:     */         else {
/* 2871:3268 */           ScriptableObject.redefineProperty(scope, name, isConst);
/* 2872:     */         }
/* 2873:     */       }
/* 2874:     */     }
/* 2875:     */   }
/* 2876:     */   
/* 2877:     */   public static Scriptable createFunctionActivation(NativeFunction funObj, Scriptable scope, Object[] args)
/* 2878:     */   {
/* 2879:3278 */     return new NativeCall(funObj, scope, args);
/* 2880:     */   }
/* 2881:     */   
/* 2882:     */   public static void enterActivationFunction(Context cx, Scriptable scope)
/* 2883:     */   {
/* 2884:3285 */     if (cx.topCallScope == null) {
/* 2885:3286 */       throw new IllegalStateException();
/* 2886:     */     }
/* 2887:3287 */     NativeCall call = (NativeCall)scope;
/* 2888:3288 */     call.parentActivationCall = cx.currentActivationCall;
/* 2889:3289 */     cx.currentActivationCall = call;
/* 2890:     */   }
/* 2891:     */   
/* 2892:     */   public static void exitActivationFunction(Context cx)
/* 2893:     */   {
/* 2894:3294 */     NativeCall call = cx.currentActivationCall;
/* 2895:3295 */     cx.currentActivationCall = call.parentActivationCall;
/* 2896:3296 */     call.parentActivationCall = null;
/* 2897:     */   }
/* 2898:     */   
/* 2899:     */   static NativeCall findFunctionActivation(Context cx, Function f)
/* 2900:     */   {
/* 2901:3301 */     NativeCall call = cx.currentActivationCall;
/* 2902:3302 */     while (call != null)
/* 2903:     */     {
/* 2904:3303 */       if (call.function == f) {
/* 2905:3304 */         return call;
/* 2906:     */       }
/* 2907:3305 */       call = call.parentActivationCall;
/* 2908:     */     }
/* 2909:3307 */     return null;
/* 2910:     */   }
/* 2911:     */   
/* 2912:     */   public static Scriptable newCatchScope(Throwable t, Scriptable lastCatchScope, String exceptionName, Context cx, Scriptable scope)
/* 2913:     */   {
/* 2914:     */     Object obj;
/* 2915:     */     boolean cacheObj;
/* 2916:     */     Object obj;
/* 2917:3319 */     if ((t instanceof JavaScriptException))
/* 2918:     */     {
/* 2919:3320 */       boolean cacheObj = false;
/* 2920:3321 */       obj = ((JavaScriptException)t).getValue();
/* 2921:     */     }
/* 2922:     */     else
/* 2923:     */     {
/* 2924:3323 */       cacheObj = true;
/* 2925:3328 */       if (lastCatchScope != null)
/* 2926:     */       {
/* 2927:3329 */         NativeObject last = (NativeObject)lastCatchScope;
/* 2928:3330 */         Object obj = last.getAssociatedValue(t);
/* 2929:3331 */         if (obj == null) {
/* 2930:3331 */           Kit.codeBug();
/* 2931:     */         }
/* 2932:     */       }
/* 2933:     */       else
/* 2934:     */       {
/* 2935:3338 */         Throwable javaException = null;
/* 2936:     */         String errorMsg;
/* 2937:3340 */         if ((t instanceof EcmaError))
/* 2938:     */         {
/* 2939:3341 */           EcmaError ee = (EcmaError)t;
/* 2940:3342 */           RhinoException re = ee;
/* 2941:3343 */           String errorName = ee.getName();
/* 2942:3344 */           errorMsg = ee.getErrorMessage();
/* 2943:     */         }
/* 2944:     */         else
/* 2945:     */         {
/* 2946:     */           String errorMsg;
/* 2947:3345 */           if ((t instanceof WrappedException))
/* 2948:     */           {
/* 2949:3346 */             WrappedException we = (WrappedException)t;
/* 2950:3347 */             RhinoException re = we;
/* 2951:3348 */             javaException = we.getWrappedException();
/* 2952:3349 */             String errorName = "JavaException";
/* 2953:3350 */             errorMsg = javaException.getClass().getName() + ": " + javaException.getMessage();
/* 2954:     */           }
/* 2955:     */           else
/* 2956:     */           {
/* 2957:     */             String errorMsg;
/* 2958:3352 */             if ((t instanceof EvaluatorException))
/* 2959:     */             {
/* 2960:3354 */               EvaluatorException ee = (EvaluatorException)t;
/* 2961:3355 */               RhinoException re = ee;
/* 2962:3356 */               String errorName = "InternalError";
/* 2963:3357 */               errorMsg = ee.getMessage();
/* 2964:     */             }
/* 2965:     */             else
/* 2966:     */             {
/* 2967:     */               String errorMsg;
/* 2968:3358 */               if (cx.hasFeature(13))
/* 2969:     */               {
/* 2970:3361 */                 RhinoException re = new WrappedException(t);
/* 2971:3362 */                 String errorName = "JavaException";
/* 2972:3363 */                 errorMsg = t.toString();
/* 2973:     */               }
/* 2974:     */               else
/* 2975:     */               {
/* 2976:3367 */                 throw Kit.codeBug();
/* 2977:     */               }
/* 2978:     */             }
/* 2979:     */           }
/* 2980:     */         }
/* 2981:     */         String errorMsg;
/* 2982:     */         String errorName;
/* 2983:     */         RhinoException re;
/* 2984:3370 */         String sourceUri = re.sourceName();
/* 2985:3371 */         if (sourceUri == null) {
/* 2986:3372 */           sourceUri = "";
/* 2987:     */         }
/* 2988:3374 */         int line = re.lineNumber();
/* 2989:     */         Object[] args;
/* 2990:     */         Object[] args;
/* 2991:3376 */         if (line > 0) {
/* 2992:3377 */           args = new Object[] { errorMsg, sourceUri, Integer.valueOf(line) };
/* 2993:     */         } else {
/* 2994:3379 */           args = new Object[] { errorMsg, sourceUri };
/* 2995:     */         }
/* 2996:3382 */         Scriptable errorObject = cx.newObject(scope, errorName, args);
/* 2997:3383 */         ScriptableObject.putProperty(errorObject, "name", errorName);
/* 2998:3385 */         if ((errorObject instanceof NativeError)) {
/* 2999:3386 */           ((NativeError)errorObject).setStackProvider(re);
/* 3000:     */         }
/* 3001:3389 */         if ((javaException != null) && (isVisible(cx, javaException)))
/* 3002:     */         {
/* 3003:3390 */           Object wrap = cx.getWrapFactory().wrap(cx, scope, javaException, null);
/* 3004:     */           
/* 3005:3392 */           ScriptableObject.defineProperty(errorObject, "javaException", wrap, 5);
/* 3006:     */         }
/* 3007:3396 */         if (isVisible(cx, re))
/* 3008:     */         {
/* 3009:3397 */           Object wrap = cx.getWrapFactory().wrap(cx, scope, re, null);
/* 3010:3398 */           ScriptableObject.defineProperty(errorObject, "rhinoException", wrap, 5);
/* 3011:     */         }
/* 3012:3402 */         obj = errorObject;
/* 3013:     */       }
/* 3014:     */     }
/* 3015:3405 */     NativeObject catchScopeObject = new NativeObject();
/* 3016:     */     
/* 3017:3407 */     catchScopeObject.defineProperty(exceptionName, obj, 4);
/* 3018:3410 */     if (isVisible(cx, t)) {
/* 3019:3414 */       catchScopeObject.defineProperty("__exception__", Context.javaToJS(t, scope), 6);
/* 3020:     */     }
/* 3021:3419 */     if (cacheObj) {
/* 3022:3420 */       catchScopeObject.associateValue(t, obj);
/* 3023:     */     }
/* 3024:3422 */     return catchScopeObject;
/* 3025:     */   }
/* 3026:     */   
/* 3027:     */   private static boolean isVisible(Context cx, Object obj)
/* 3028:     */   {
/* 3029:3426 */     ClassShutter shutter = cx.getClassShutter();
/* 3030:3427 */     return (shutter == null) || (shutter.visibleToScripts(obj.getClass().getName()));
/* 3031:     */   }
/* 3032:     */   
/* 3033:     */   public static Scriptable enterWith(Object obj, Context cx, Scriptable scope)
/* 3034:     */   {
/* 3035:3434 */     Scriptable sobj = toObjectOrNull(cx, obj);
/* 3036:3435 */     if (sobj == null) {
/* 3037:3436 */       throw typeError1("msg.undef.with", toString(obj));
/* 3038:     */     }
/* 3039:3438 */     if ((sobj instanceof XMLObject))
/* 3040:     */     {
/* 3041:3439 */       XMLObject xmlObject = (XMLObject)sobj;
/* 3042:3440 */       return xmlObject.enterWith(scope);
/* 3043:     */     }
/* 3044:3442 */     return new NativeWith(scope, sobj);
/* 3045:     */   }
/* 3046:     */   
/* 3047:     */   public static Scriptable leaveWith(Scriptable scope)
/* 3048:     */   {
/* 3049:3447 */     NativeWith nw = (NativeWith)scope;
/* 3050:3448 */     return nw.getParentScope();
/* 3051:     */   }
/* 3052:     */   
/* 3053:     */   public static Scriptable enterDotQuery(Object value, Scriptable scope)
/* 3054:     */   {
/* 3055:3453 */     if (!(value instanceof XMLObject)) {
/* 3056:3454 */       throw notXmlError(value);
/* 3057:     */     }
/* 3058:3456 */     XMLObject object = (XMLObject)value;
/* 3059:3457 */     return object.enterDotQuery(scope);
/* 3060:     */   }
/* 3061:     */   
/* 3062:     */   public static Object updateDotQuery(boolean value, Scriptable scope)
/* 3063:     */   {
/* 3064:3463 */     NativeWith nw = (NativeWith)scope;
/* 3065:3464 */     return nw.updateDotQuery(value);
/* 3066:     */   }
/* 3067:     */   
/* 3068:     */   public static Scriptable leaveDotQuery(Scriptable scope)
/* 3069:     */   {
/* 3070:3469 */     NativeWith nw = (NativeWith)scope;
/* 3071:3470 */     return nw.getParentScope();
/* 3072:     */   }
/* 3073:     */   
/* 3074:     */   public static void setFunctionProtoAndParent(BaseFunction fn, Scriptable scope)
/* 3075:     */   {
/* 3076:3476 */     fn.setParentScope(scope);
/* 3077:3477 */     fn.setPrototype(ScriptableObject.getFunctionPrototype(scope));
/* 3078:     */   }
/* 3079:     */   
/* 3080:     */   public static void setObjectProtoAndParent(ScriptableObject object, Scriptable scope)
/* 3081:     */   {
/* 3082:3484 */     scope = ScriptableObject.getTopLevelScope(scope);
/* 3083:3485 */     object.setParentScope(scope);
/* 3084:3486 */     Scriptable proto = ScriptableObject.getClassPrototype(scope, object.getClassName());
/* 3085:     */     
/* 3086:3488 */     object.setPrototype(proto);
/* 3087:     */   }
/* 3088:     */   
/* 3089:     */   public static void setBuiltinProtoAndParent(ScriptableObject object, Scriptable scope, TopLevel.Builtins type)
/* 3090:     */   {
/* 3091:3495 */     scope = ScriptableObject.getTopLevelScope(scope);
/* 3092:3496 */     object.setParentScope(scope);
/* 3093:3497 */     object.setPrototype(TopLevel.getBuiltinPrototype(scope, type));
/* 3094:     */   }
/* 3095:     */   
/* 3096:     */   public static void initFunction(Context cx, Scriptable scope, NativeFunction function, int type, boolean fromEvalCode)
/* 3097:     */   {
/* 3098:3505 */     if (type == 1)
/* 3099:     */     {
/* 3100:3506 */       String name = function.getFunctionName();
/* 3101:3507 */       if ((name != null) && (name.length() != 0)) {
/* 3102:3508 */         if (!fromEvalCode) {
/* 3103:3511 */           ScriptableObject.defineProperty(scope, name, function, 4);
/* 3104:     */         } else {
/* 3105:3514 */           scope.put(name, scope, function);
/* 3106:     */         }
/* 3107:     */       }
/* 3108:     */     }
/* 3109:3517 */     else if (type == 3)
/* 3110:     */     {
/* 3111:3518 */       String name = function.getFunctionName();
/* 3112:3519 */       if ((name != null) && (name.length() != 0))
/* 3113:     */       {
/* 3114:3523 */         while ((scope instanceof NativeWith)) {
/* 3115:3524 */           scope = scope.getParentScope();
/* 3116:     */         }
/* 3117:3526 */         scope.put(name, scope, function);
/* 3118:     */       }
/* 3119:     */     }
/* 3120:     */     else
/* 3121:     */     {
/* 3122:3529 */       throw Kit.codeBug();
/* 3123:     */     }
/* 3124:     */   }
/* 3125:     */   
/* 3126:     */   public static Scriptable newArrayLiteral(Object[] objects, int[] skipIndices, Context cx, Scriptable scope)
/* 3127:     */   {
/* 3128:3537 */     int SKIP_DENSITY = 2;
/* 3129:3538 */     int count = objects.length;
/* 3130:3539 */     int skipCount = 0;
/* 3131:3540 */     if (skipIndices != null) {
/* 3132:3541 */       skipCount = skipIndices.length;
/* 3133:     */     }
/* 3134:3543 */     int length = count + skipCount;
/* 3135:3544 */     if ((length > 1) && (skipCount * 2 < length))
/* 3136:     */     {
/* 3137:     */       Object[] sparse;
/* 3138:     */       Object[] sparse;
/* 3139:3547 */       if (skipCount == 0)
/* 3140:     */       {
/* 3141:3548 */         sparse = objects;
/* 3142:     */       }
/* 3143:     */       else
/* 3144:     */       {
/* 3145:3550 */         sparse = new Object[length];
/* 3146:3551 */         int skip = 0;
/* 3147:3552 */         int i = 0;
/* 3148:3552 */         for (int j = 0; i != length; i++) {
/* 3149:3553 */           if ((skip != skipCount) && (skipIndices[skip] == i))
/* 3150:     */           {
/* 3151:3554 */             sparse[i] = Scriptable.NOT_FOUND;
/* 3152:3555 */             skip++;
/* 3153:     */           }
/* 3154:     */           else
/* 3155:     */           {
/* 3156:3558 */             sparse[i] = objects[j];
/* 3157:3559 */             j++;
/* 3158:     */           }
/* 3159:     */         }
/* 3160:     */       }
/* 3161:3562 */       return cx.newArray(scope, sparse);
/* 3162:     */     }
/* 3163:3565 */     Scriptable array = cx.newArray(scope, length);
/* 3164:     */     
/* 3165:3567 */     int skip = 0;
/* 3166:3568 */     int i = 0;
/* 3167:3568 */     for (int j = 0; i != length; i++) {
/* 3168:3569 */       if ((skip != skipCount) && (skipIndices[skip] == i))
/* 3169:     */       {
/* 3170:3570 */         skip++;
/* 3171:     */       }
/* 3172:     */       else
/* 3173:     */       {
/* 3174:3573 */         ScriptableObject.putProperty(array, i, objects[j]);
/* 3175:3574 */         j++;
/* 3176:     */       }
/* 3177:     */     }
/* 3178:3576 */     return array;
/* 3179:     */   }
/* 3180:     */   
/* 3181:     */   /**
/* 3182:     */    * @deprecated
/* 3183:     */    */
/* 3184:     */   public static Scriptable newObjectLiteral(Object[] propertyIds, Object[] propertyValues, Context cx, Scriptable scope)
/* 3185:     */   {
/* 3186:3591 */     int[] getterSetters = new int[propertyIds.length];
/* 3187:3592 */     return newObjectLiteral(propertyIds, propertyValues, getterSetters, cx, scope);
/* 3188:     */   }
/* 3189:     */   
/* 3190:     */   public static Scriptable newObjectLiteral(Object[] propertyIds, Object[] propertyValues, int[] getterSetters, Context cx, Scriptable scope)
/* 3191:     */   {
/* 3192:3601 */     Scriptable object = cx.newObject(scope);
/* 3193:3602 */     int i = 0;
/* 3194:3602 */     for (int end = propertyIds.length; i != end; i++)
/* 3195:     */     {
/* 3196:3603 */       Object id = propertyIds[i];
/* 3197:3604 */       int getterSetter = getterSetters[i];
/* 3198:3605 */       Object value = propertyValues[i];
/* 3199:3606 */       if ((id instanceof String))
/* 3200:     */       {
/* 3201:3607 */         if (getterSetter == 0)
/* 3202:     */         {
/* 3203:3608 */           if (isSpecialProperty((String)id)) {
/* 3204:3609 */             specialRef(object, (String)id, cx).set(cx, value);
/* 3205:     */           } else {
/* 3206:3611 */             ScriptableObject.putProperty(object, (String)id, value);
/* 3207:     */           }
/* 3208:     */         }
/* 3209:     */         else
/* 3210:     */         {
/* 3211:     */           String definer;
/* 3212:     */           String definer;
/* 3213:3616 */           if (getterSetter < 0) {
/* 3214:3617 */             definer = "__defineGetter__";
/* 3215:     */           } else {
/* 3216:3619 */             definer = "__defineSetter__";
/* 3217:     */           }
/* 3218:3620 */           Callable fun = getPropFunctionAndThis(object, definer, cx);
/* 3219:     */           
/* 3220:3622 */           lastStoredScriptable(cx);
/* 3221:3623 */           Object[] outArgs = new Object[2];
/* 3222:3624 */           outArgs[0] = id;
/* 3223:3625 */           outArgs[1] = value;
/* 3224:3626 */           fun.call(cx, scope, object, outArgs);
/* 3225:     */         }
/* 3226:     */       }
/* 3227:     */       else
/* 3228:     */       {
/* 3229:3629 */         int index = ((Integer)id).intValue();
/* 3230:3630 */         ScriptableObject.putProperty(object, index, value);
/* 3231:     */       }
/* 3232:     */     }
/* 3233:3633 */     return object;
/* 3234:     */   }
/* 3235:     */   
/* 3236:     */   public static boolean isArrayObject(Object obj)
/* 3237:     */   {
/* 3238:3638 */     return ((obj instanceof NativeArray)) || ((obj instanceof Arguments));
/* 3239:     */   }
/* 3240:     */   
/* 3241:     */   public static Object[] getArrayElements(Scriptable object)
/* 3242:     */   {
/* 3243:3643 */     Context cx = Context.getContext();
/* 3244:3644 */     long longLen = NativeArray.getLengthProperty(cx, object);
/* 3245:3645 */     if (longLen > 2147483647L) {
/* 3246:3647 */       throw new IllegalArgumentException();
/* 3247:     */     }
/* 3248:3649 */     int len = (int)longLen;
/* 3249:3650 */     if (len == 0) {
/* 3250:3651 */       return emptyArgs;
/* 3251:     */     }
/* 3252:3653 */     Object[] result = new Object[len];
/* 3253:3654 */     for (int i = 0; i < len; i++)
/* 3254:     */     {
/* 3255:3655 */       Object elem = ScriptableObject.getProperty(object, i);
/* 3256:3656 */       result[i] = (elem == Scriptable.NOT_FOUND ? Undefined.instance : elem);
/* 3257:     */     }
/* 3258:3659 */     return result;
/* 3259:     */   }
/* 3260:     */   
/* 3261:     */   static void checkDeprecated(Context cx, String name)
/* 3262:     */   {
/* 3263:3664 */     int version = cx.getLanguageVersion();
/* 3264:3665 */     if ((version >= 140) || (version == 0))
/* 3265:     */     {
/* 3266:3666 */       String msg = getMessage1("msg.deprec.ctor", name);
/* 3267:3667 */       if (version == 0) {
/* 3268:3668 */         Context.reportWarning(msg);
/* 3269:     */       } else {
/* 3270:3670 */         throw Context.reportRuntimeError(msg);
/* 3271:     */       }
/* 3272:     */     }
/* 3273:     */   }
/* 3274:     */   
/* 3275:     */   public static String getMessage0(String messageId)
/* 3276:     */   {
/* 3277:3676 */     return getMessage(messageId, null);
/* 3278:     */   }
/* 3279:     */   
/* 3280:     */   public static String getMessage1(String messageId, Object arg1)
/* 3281:     */   {
/* 3282:3681 */     Object[] arguments = { arg1 };
/* 3283:3682 */     return getMessage(messageId, arguments);
/* 3284:     */   }
/* 3285:     */   
/* 3286:     */   public static String getMessage2(String messageId, Object arg1, Object arg2)
/* 3287:     */   {
/* 3288:3688 */     Object[] arguments = { arg1, arg2 };
/* 3289:3689 */     return getMessage(messageId, arguments);
/* 3290:     */   }
/* 3291:     */   
/* 3292:     */   public static String getMessage3(String messageId, Object arg1, Object arg2, Object arg3)
/* 3293:     */   {
/* 3294:3695 */     Object[] arguments = { arg1, arg2, arg3 };
/* 3295:3696 */     return getMessage(messageId, arguments);
/* 3296:     */   }
/* 3297:     */   
/* 3298:     */   public static String getMessage4(String messageId, Object arg1, Object arg2, Object arg3, Object arg4)
/* 3299:     */   {
/* 3300:3702 */     Object[] arguments = { arg1, arg2, arg3, arg4 };
/* 3301:3703 */     return getMessage(messageId, arguments);
/* 3302:     */   }
/* 3303:     */   
/* 3304:3724 */   public static MessageProvider messageProvider = new DefaultMessageProvider(null);
/* 3305:     */   
/* 3306:     */   public static String getMessage(String messageId, Object[] arguments)
/* 3307:     */   {
/* 3308:3728 */     return messageProvider.getMessage(messageId, arguments);
/* 3309:     */   }
/* 3310:     */   
/* 3311:     */   private static class IdEnumeration
/* 3312:     */     implements Serializable
/* 3313:     */   {
/* 3314:     */     private static final long serialVersionUID = 1L;
/* 3315:     */     Scriptable obj;
/* 3316:     */     Object[] ids;
/* 3317:     */     int index;
/* 3318:     */     ObjToIntMap used;
/* 3319:     */     Object currentId;
/* 3320:     */     int enumType;
/* 3321:     */     boolean enumNumbers;
/* 3322:     */     Scriptable iterator;
/* 3323:     */   }
/* 3324:     */   
/* 3325:     */   public static abstract interface MessageProvider
/* 3326:     */   {
/* 3327:     */     public abstract String getMessage(String paramString, Object[] paramArrayOfObject);
/* 3328:     */   }
/* 3329:     */   
/* 3330:     */   private static class DefaultMessageProvider
/* 3331:     */     implements ScriptRuntime.MessageProvider
/* 3332:     */   {
/* 3333:     */     public String getMessage(String messageId, Object[] arguments)
/* 3334:     */     {
/* 3335:3737 */       String defaultResource = "net.sourceforge.htmlunit.corejs.javascript.resources.Messages";
/* 3336:     */       
/* 3337:     */ 
/* 3338:3740 */       Context cx = Context.getCurrentContext();
/* 3339:3741 */       Locale locale = cx != null ? cx.getLocale() : Locale.getDefault();
/* 3340:     */       
/* 3341:     */ 
/* 3342:3744 */       ResourceBundle rb = ResourceBundle.getBundle("net.sourceforge.htmlunit.corejs.javascript.resources.Messages", locale);
/* 3343:     */       String formatString;
/* 3344:     */       try
/* 3345:     */       {
/* 3346:3748 */         formatString = rb.getString(messageId);
/* 3347:     */       }
/* 3348:     */       catch (MissingResourceException mre)
/* 3349:     */       {
/* 3350:3750 */         throw new RuntimeException("no message resource found for message property " + messageId);
/* 3351:     */       }
/* 3352:3759 */       MessageFormat formatter = new MessageFormat(formatString);
/* 3353:3760 */       return formatter.format(arguments);
/* 3354:     */     }
/* 3355:     */   }
/* 3356:     */   
/* 3357:     */   public static EcmaError constructError(String error, String message)
/* 3358:     */   {
/* 3359:3766 */     int[] linep = new int[1];
/* 3360:3767 */     String filename = Context.getSourcePositionFromStack(linep);
/* 3361:3768 */     return constructError(error, message, filename, linep[0], null, 0);
/* 3362:     */   }
/* 3363:     */   
/* 3364:     */   public static EcmaError constructError(String error, String message, int lineNumberDelta)
/* 3365:     */   {
/* 3366:3775 */     int[] linep = new int[1];
/* 3367:3776 */     String filename = Context.getSourcePositionFromStack(linep);
/* 3368:3777 */     if (linep[0] != 0) {
/* 3369:3778 */       linep[0] += lineNumberDelta;
/* 3370:     */     }
/* 3371:3780 */     return constructError(error, message, filename, linep[0], null, 0);
/* 3372:     */   }
/* 3373:     */   
/* 3374:     */   public static EcmaError constructError(String error, String message, String sourceName, int lineNumber, String lineSource, int columnNumber)
/* 3375:     */   {
/* 3376:3790 */     return new EcmaError(error, message, sourceName, lineNumber, lineSource, columnNumber);
/* 3377:     */   }
/* 3378:     */   
/* 3379:     */   public static EcmaError typeError(String message)
/* 3380:     */   {
/* 3381:3796 */     return constructError("TypeError", message);
/* 3382:     */   }
/* 3383:     */   
/* 3384:     */   public static EcmaError typeError0(String messageId)
/* 3385:     */   {
/* 3386:3801 */     String msg = getMessage0(messageId);
/* 3387:3802 */     return typeError(msg);
/* 3388:     */   }
/* 3389:     */   
/* 3390:     */   public static EcmaError typeError1(String messageId, String arg1)
/* 3391:     */   {
/* 3392:3807 */     String msg = getMessage1(messageId, arg1);
/* 3393:3808 */     return typeError(msg);
/* 3394:     */   }
/* 3395:     */   
/* 3396:     */   public static EcmaError typeError2(String messageId, String arg1, String arg2)
/* 3397:     */   {
/* 3398:3814 */     String msg = getMessage2(messageId, arg1, arg2);
/* 3399:3815 */     return typeError(msg);
/* 3400:     */   }
/* 3401:     */   
/* 3402:     */   public static EcmaError typeError3(String messageId, String arg1, String arg2, String arg3)
/* 3403:     */   {
/* 3404:3821 */     String msg = getMessage3(messageId, arg1, arg2, arg3);
/* 3405:3822 */     return typeError(msg);
/* 3406:     */   }
/* 3407:     */   
/* 3408:     */   public static RuntimeException undefReadError(Object object, Object id)
/* 3409:     */   {
/* 3410:3827 */     String idStr = toString(id);
/* 3411:3828 */     return typeError2("msg.undef.prop.read", toString(object), idStr);
/* 3412:     */   }
/* 3413:     */   
/* 3414:     */   public static RuntimeException undefCallError(Object object, Object id)
/* 3415:     */   {
/* 3416:3833 */     String idStr = toString(id);
/* 3417:3834 */     return typeError2("msg.undef.method.call", toString(object), idStr);
/* 3418:     */   }
/* 3419:     */   
/* 3420:     */   public static RuntimeException undefWriteError(Object object, Object id, Object value)
/* 3421:     */   {
/* 3422:3841 */     String idStr = toString(id);
/* 3423:3842 */     String valueStr = toString(value);
/* 3424:3843 */     return typeError3("msg.undef.prop.write", toString(object), idStr, valueStr);
/* 3425:     */   }
/* 3426:     */   
/* 3427:     */   public static RuntimeException notFoundError(Scriptable object, String property)
/* 3428:     */   {
/* 3429:3851 */     String msg = getMessage1("msg.is.not.defined", property);
/* 3430:3852 */     throw constructError("ReferenceError", msg);
/* 3431:     */   }
/* 3432:     */   
/* 3433:     */   public static RuntimeException notFunctionError(Object value)
/* 3434:     */   {
/* 3435:3857 */     return notFunctionError(value, value);
/* 3436:     */   }
/* 3437:     */   
/* 3438:     */   public static RuntimeException notFunctionError(Object value, Object messageHelper)
/* 3439:     */   {
/* 3440:3864 */     String msg = messageHelper == null ? "null" : messageHelper.toString();
/* 3441:3866 */     if (value == Scriptable.NOT_FOUND) {
/* 3442:3867 */       return typeError1("msg.function.not.found", msg);
/* 3443:     */     }
/* 3444:3869 */     return typeError2("msg.isnt.function", msg, typeof(value));
/* 3445:     */   }
/* 3446:     */   
/* 3447:     */   public static RuntimeException notFunctionError(Object obj, Object value, String propertyName)
/* 3448:     */   {
/* 3449:3876 */     String objString = toString(obj);
/* 3450:3877 */     if ((obj instanceof NativeFunction))
/* 3451:     */     {
/* 3452:3879 */       int curly = objString.indexOf('{');
/* 3453:3880 */       if (curly > -1) {
/* 3454:3881 */         objString = objString.substring(0, curly + 1) + "...}";
/* 3455:     */       }
/* 3456:     */     }
/* 3457:3884 */     if (value == Scriptable.NOT_FOUND) {
/* 3458:3885 */       return typeError2("msg.function.not.found.in", propertyName, objString);
/* 3459:     */     }
/* 3460:3888 */     return typeError3("msg.isnt.function.in", propertyName, objString, typeof(value));
/* 3461:     */   }
/* 3462:     */   
/* 3463:     */   private static RuntimeException notXmlError(Object value)
/* 3464:     */   {
/* 3465:3894 */     throw typeError1("msg.isnt.xml.object", toString(value));
/* 3466:     */   }
/* 3467:     */   
/* 3468:     */   private static void warnAboutNonJSObject(Object nonJSObject)
/* 3469:     */   {
/* 3470:3899 */     String message = "RHINO USAGE WARNING: Missed Context.javaToJS() conversion:\nRhino runtime detected object " + nonJSObject + " of class " + nonJSObject.getClass().getName() + " where it expected String, Number, Boolean or Scriptable instance. Please check your code for missing Context.javaToJS() call.";
/* 3471:     */     
/* 3472:     */ 
/* 3473:3902 */     Context.reportWarning(message);
/* 3474:     */     
/* 3475:3904 */     System.err.println(message);
/* 3476:     */   }
/* 3477:     */   
/* 3478:     */   public static RegExpProxy getRegExpProxy(Context cx)
/* 3479:     */   {
/* 3480:3909 */     return cx.getRegExpProxy();
/* 3481:     */   }
/* 3482:     */   
/* 3483:     */   public static void setRegExpProxy(Context cx, RegExpProxy proxy)
/* 3484:     */   {
/* 3485:3914 */     if (proxy == null) {
/* 3486:3914 */       throw new IllegalArgumentException();
/* 3487:     */     }
/* 3488:3915 */     cx.regExpProxy = proxy;
/* 3489:     */   }
/* 3490:     */   
/* 3491:     */   public static RegExpProxy checkRegExpProxy(Context cx)
/* 3492:     */   {
/* 3493:3920 */     RegExpProxy result = getRegExpProxy(cx);
/* 3494:3921 */     if (result == null) {
/* 3495:3922 */       throw Context.reportRuntimeError0("msg.no.regexp");
/* 3496:     */     }
/* 3497:3924 */     return result;
/* 3498:     */   }
/* 3499:     */   
/* 3500:     */   private static XMLLib currentXMLLib(Context cx)
/* 3501:     */   {
/* 3502:3930 */     if (cx.topCallScope == null) {
/* 3503:3931 */       throw new IllegalStateException();
/* 3504:     */     }
/* 3505:3933 */     XMLLib xmlLib = cx.cachedXMLLib;
/* 3506:3934 */     if (xmlLib == null)
/* 3507:     */     {
/* 3508:3935 */       xmlLib = XMLLib.extractFromScope(cx.topCallScope);
/* 3509:3936 */       if (xmlLib == null) {
/* 3510:3937 */         throw new IllegalStateException();
/* 3511:     */       }
/* 3512:3938 */       cx.cachedXMLLib = xmlLib;
/* 3513:     */     }
/* 3514:3941 */     return xmlLib;
/* 3515:     */   }
/* 3516:     */   
/* 3517:     */   public static String escapeAttributeValue(Object value, Context cx)
/* 3518:     */   {
/* 3519:3952 */     XMLLib xmlLib = currentXMLLib(cx);
/* 3520:3953 */     return xmlLib.escapeAttributeValue(value);
/* 3521:     */   }
/* 3522:     */   
/* 3523:     */   public static String escapeTextValue(Object value, Context cx)
/* 3524:     */   {
/* 3525:3964 */     XMLLib xmlLib = currentXMLLib(cx);
/* 3526:3965 */     return xmlLib.escapeTextValue(value);
/* 3527:     */   }
/* 3528:     */   
/* 3529:     */   public static Ref memberRef(Object obj, Object elem, Context cx, int memberTypeFlags)
/* 3530:     */   {
/* 3531:3971 */     if (!(obj instanceof XMLObject)) {
/* 3532:3972 */       throw notXmlError(obj);
/* 3533:     */     }
/* 3534:3974 */     XMLObject xmlObject = (XMLObject)obj;
/* 3535:3975 */     return xmlObject.memberRef(cx, elem, memberTypeFlags);
/* 3536:     */   }
/* 3537:     */   
/* 3538:     */   public static Ref memberRef(Object obj, Object namespace, Object elem, Context cx, int memberTypeFlags)
/* 3539:     */   {
/* 3540:3981 */     if (!(obj instanceof XMLObject)) {
/* 3541:3982 */       throw notXmlError(obj);
/* 3542:     */     }
/* 3543:3984 */     XMLObject xmlObject = (XMLObject)obj;
/* 3544:3985 */     return xmlObject.memberRef(cx, namespace, elem, memberTypeFlags);
/* 3545:     */   }
/* 3546:     */   
/* 3547:     */   public static Ref nameRef(Object name, Context cx, Scriptable scope, int memberTypeFlags)
/* 3548:     */   {
/* 3549:3991 */     XMLLib xmlLib = currentXMLLib(cx);
/* 3550:3992 */     return xmlLib.nameRef(cx, name, scope, memberTypeFlags);
/* 3551:     */   }
/* 3552:     */   
/* 3553:     */   public static Ref nameRef(Object namespace, Object name, Context cx, Scriptable scope, int memberTypeFlags)
/* 3554:     */   {
/* 3555:3998 */     XMLLib xmlLib = currentXMLLib(cx);
/* 3556:3999 */     return xmlLib.nameRef(cx, namespace, name, scope, memberTypeFlags);
/* 3557:     */   }
/* 3558:     */   
/* 3559:     */   private static void storeIndexResult(Context cx, int index)
/* 3560:     */   {
/* 3561:4004 */     cx.scratchIndex = index;
/* 3562:     */   }
/* 3563:     */   
/* 3564:     */   static int lastIndexResult(Context cx)
/* 3565:     */   {
/* 3566:4009 */     return cx.scratchIndex;
/* 3567:     */   }
/* 3568:     */   
/* 3569:     */   public static void storeUint32Result(Context cx, long value)
/* 3570:     */   {
/* 3571:4014 */     if (value >>> 32 != 0L) {
/* 3572:4015 */       throw new IllegalArgumentException();
/* 3573:     */     }
/* 3574:4016 */     cx.scratchUint32 = value;
/* 3575:     */   }
/* 3576:     */   
/* 3577:     */   public static long lastUint32Result(Context cx)
/* 3578:     */   {
/* 3579:4021 */     long value = cx.scratchUint32;
/* 3580:4022 */     if (value >>> 32 != 0L) {
/* 3581:4023 */       throw new IllegalStateException();
/* 3582:     */     }
/* 3583:4024 */     return value;
/* 3584:     */   }
/* 3585:     */   
/* 3586:     */   private static void storeScriptable(Context cx, Scriptable value)
/* 3587:     */   {
/* 3588:4030 */     if (cx.scratchScriptable != null) {
/* 3589:4031 */       throw new IllegalStateException();
/* 3590:     */     }
/* 3591:4032 */     cx.scratchScriptable = value;
/* 3592:     */   }
/* 3593:     */   
/* 3594:     */   public static Scriptable lastStoredScriptable(Context cx)
/* 3595:     */   {
/* 3596:4037 */     Scriptable result = cx.scratchScriptable;
/* 3597:4038 */     cx.scratchScriptable = null;
/* 3598:4039 */     return result;
/* 3599:     */   }
/* 3600:     */   
/* 3601:     */   static String makeUrlForGeneratedScript(boolean isEval, String masterScriptUrl, int masterScriptLine)
/* 3602:     */   {
/* 3603:4045 */     if (isEval) {
/* 3604:4046 */       return masterScriptUrl + '#' + masterScriptLine + "(eval)";
/* 3605:     */     }
/* 3606:4048 */     return masterScriptUrl + '#' + masterScriptLine + "(Function)";
/* 3607:     */   }
/* 3608:     */   
/* 3609:     */   static boolean isGeneratedScript(String sourceUrl)
/* 3610:     */   {
/* 3611:4055 */     return (sourceUrl.indexOf("(eval)") >= 0) || (sourceUrl.indexOf("(Function)") >= 0);
/* 3612:     */   }
/* 3613:     */   
/* 3614:     */   private static RuntimeException errorWithClassName(String msg, Object val)
/* 3615:     */   {
/* 3616:4061 */     return Context.reportRuntimeError1(msg, val.getClass().getName());
/* 3617:     */   }
/* 3618:     */   
/* 3619:     */   public static JavaScriptException throwError(Context cx, Scriptable scope, String message)
/* 3620:     */   {
/* 3621:4073 */     int[] linep = { 0 };
/* 3622:4074 */     String filename = Context.getSourcePositionFromStack(linep);
/* 3623:4075 */     Scriptable error = newBuiltinObject(cx, scope, TopLevel.Builtins.Error, new Object[] { message, filename, Integer.valueOf(linep[0]) });
/* 3624:     */     
/* 3625:4077 */     return new JavaScriptException(error, filename, linep[0]);
/* 3626:     */   }
/* 3627:     */   
/* 3628:4080 */   public static final Object[] emptyArgs = new Object[0];
/* 3629:4081 */   public static final String[] emptyStrings = new String[0];
/* 3630:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime
 * JD-Core Version:    0.7.0.1
 */