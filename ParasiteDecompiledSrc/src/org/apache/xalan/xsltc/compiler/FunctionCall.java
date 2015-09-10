/*    1:     */ package org.apache.xalan.xsltc.compiler;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.lang.reflect.Constructor;
/*    5:     */ import java.lang.reflect.Method;
/*    6:     */ import java.lang.reflect.Modifier;
/*    7:     */ import java.util.Enumeration;
/*    8:     */ import java.util.Hashtable;
/*    9:     */ import java.util.Vector;
/*   10:     */ import org.apache.bcel.generic.ClassGen;
/*   11:     */ import org.apache.bcel.generic.ConstantPoolGen;
/*   12:     */ import org.apache.bcel.generic.IFEQ;
/*   13:     */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   14:     */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   15:     */ import org.apache.bcel.generic.INVOKESTATIC;
/*   16:     */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   17:     */ import org.apache.bcel.generic.InstructionConstants;
/*   18:     */ import org.apache.bcel.generic.InstructionList;
/*   19:     */ import org.apache.bcel.generic.LocalVariableGen;
/*   20:     */ import org.apache.bcel.generic.MethodGen;
/*   21:     */ import org.apache.bcel.generic.NEW;
/*   22:     */ import org.apache.bcel.generic.PUSH;
/*   23:     */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*   24:     */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   25:     */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   26:     */ import org.apache.xalan.xsltc.compiler.util.IntType;
/*   27:     */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*   28:     */ import org.apache.xalan.xsltc.compiler.util.MethodType;
/*   29:     */ import org.apache.xalan.xsltc.compiler.util.MultiHashtable;
/*   30:     */ import org.apache.xalan.xsltc.compiler.util.ObjectType;
/*   31:     */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*   32:     */ import org.apache.xalan.xsltc.compiler.util.Type;
/*   33:     */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*   34:     */ 
/*   35:     */ class FunctionCall
/*   36:     */   extends Expression
/*   37:     */ {
/*   38:     */   private QName _fname;
/*   39:     */   private final Vector _arguments;
/*   40:  69 */   private static final Vector EMPTY_ARG_LIST = new Vector(0);
/*   41:     */   protected static final String EXT_XSLTC = "http://xml.apache.org/xalan/xsltc";
/*   42:     */   protected static final String JAVA_EXT_XSLTC = "http://xml.apache.org/xalan/xsltc/java";
/*   43:     */   protected static final String EXT_XALAN = "http://xml.apache.org/xalan";
/*   44:     */   protected static final String JAVA_EXT_XALAN = "http://xml.apache.org/xalan/java";
/*   45:     */   protected static final String JAVA_EXT_XALAN_OLD = "http://xml.apache.org/xslt/java";
/*   46:     */   protected static final String EXSLT_COMMON = "http://exslt.org/common";
/*   47:     */   protected static final String EXSLT_MATH = "http://exslt.org/math";
/*   48:     */   protected static final String EXSLT_SETS = "http://exslt.org/sets";
/*   49:     */   protected static final String EXSLT_DATETIME = "http://exslt.org/dates-and-times";
/*   50:     */   protected static final String EXSLT_STRINGS = "http://exslt.org/strings";
/*   51:     */   protected static final int NAMESPACE_FORMAT_JAVA = 0;
/*   52:     */   protected static final int NAMESPACE_FORMAT_CLASS = 1;
/*   53:     */   protected static final int NAMESPACE_FORMAT_PACKAGE = 2;
/*   54:     */   protected static final int NAMESPACE_FORMAT_CLASS_OR_PACKAGE = 3;
/*   55: 109 */   private int _namespace_format = 0;
/*   56: 114 */   Expression _thisArgument = null;
/*   57:     */   private String _className;
/*   58:     */   private Class _clazz;
/*   59:     */   private Method _chosenMethod;
/*   60:     */   private Constructor _chosenConstructor;
/*   61:     */   private MethodType _chosenMethodType;
/*   62:     */   private boolean unresolvedExternal;
/*   63: 127 */   private boolean _isExtConstructor = false;
/*   64: 130 */   private boolean _isStatic = false;
/*   65: 133 */   private static final MultiHashtable _internal2Java = new MultiHashtable();
/*   66: 136 */   private static final Hashtable _java2Internal = new Hashtable();
/*   67: 139 */   private static final Hashtable _extensionNamespaceTable = new Hashtable();
/*   68: 142 */   private static final Hashtable _extensionFunctionTable = new Hashtable();
/*   69:     */   
/*   70:     */   static class JavaType
/*   71:     */   {
/*   72:     */     public Class type;
/*   73:     */     public int distance;
/*   74:     */     
/*   75:     */     public JavaType(Class type, int distance)
/*   76:     */     {
/*   77: 153 */       this.type = type;
/*   78: 154 */       this.distance = distance;
/*   79:     */     }
/*   80:     */     
/*   81:     */     public boolean equals(Object query)
/*   82:     */     {
/*   83: 157 */       return query.equals(this.type);
/*   84:     */     }
/*   85:     */   }
/*   86:     */   
/*   87:     */   static
/*   88:     */   {
/*   89:     */     try
/*   90:     */     {
/*   91: 169 */       Class nodeClass = Class.forName("org.w3c.dom.Node");
/*   92: 170 */       Class nodeListClass = Class.forName("org.w3c.dom.NodeList");
/*   93:     */       
/*   94:     */ 
/*   95:     */ 
/*   96:     */ 
/*   97: 175 */       _internal2Java.put(Type.Boolean, new JavaType(Boolean.TYPE, 0));
/*   98: 176 */       _internal2Java.put(Type.Boolean, new JavaType(Boolean.class, 1));
/*   99: 177 */       _internal2Java.put(Type.Boolean, new JavaType(Object.class, 2));
/*  100:     */       
/*  101:     */ 
/*  102:     */ 
/*  103: 181 */       _internal2Java.put(Type.Real, new JavaType(Double.TYPE, 0));
/*  104: 182 */       _internal2Java.put(Type.Real, new JavaType(Double.class, 1));
/*  105: 183 */       _internal2Java.put(Type.Real, new JavaType(Float.TYPE, 2));
/*  106: 184 */       _internal2Java.put(Type.Real, new JavaType(Long.TYPE, 3));
/*  107: 185 */       _internal2Java.put(Type.Real, new JavaType(Integer.TYPE, 4));
/*  108: 186 */       _internal2Java.put(Type.Real, new JavaType(Short.TYPE, 5));
/*  109: 187 */       _internal2Java.put(Type.Real, new JavaType(Byte.TYPE, 6));
/*  110: 188 */       _internal2Java.put(Type.Real, new JavaType(Character.TYPE, 7));
/*  111: 189 */       _internal2Java.put(Type.Real, new JavaType(Object.class, 8));
/*  112:     */       
/*  113:     */ 
/*  114: 192 */       _internal2Java.put(Type.Int, new JavaType(Double.TYPE, 0));
/*  115: 193 */       _internal2Java.put(Type.Int, new JavaType(Double.class, 1));
/*  116: 194 */       _internal2Java.put(Type.Int, new JavaType(Float.TYPE, 2));
/*  117: 195 */       _internal2Java.put(Type.Int, new JavaType(Long.TYPE, 3));
/*  118: 196 */       _internal2Java.put(Type.Int, new JavaType(Integer.TYPE, 4));
/*  119: 197 */       _internal2Java.put(Type.Int, new JavaType(Short.TYPE, 5));
/*  120: 198 */       _internal2Java.put(Type.Int, new JavaType(Byte.TYPE, 6));
/*  121: 199 */       _internal2Java.put(Type.Int, new JavaType(Character.TYPE, 7));
/*  122: 200 */       _internal2Java.put(Type.Int, new JavaType(Object.class, 8));
/*  123:     */       
/*  124:     */ 
/*  125: 203 */       _internal2Java.put(Type.String, new JavaType(String.class, 0));
/*  126: 204 */       _internal2Java.put(Type.String, new JavaType(Object.class, 1));
/*  127:     */       
/*  128:     */ 
/*  129: 207 */       _internal2Java.put(Type.NodeSet, new JavaType(nodeListClass, 0));
/*  130: 208 */       _internal2Java.put(Type.NodeSet, new JavaType(nodeClass, 1));
/*  131: 209 */       _internal2Java.put(Type.NodeSet, new JavaType(Object.class, 2));
/*  132: 210 */       _internal2Java.put(Type.NodeSet, new JavaType(String.class, 3));
/*  133:     */       
/*  134:     */ 
/*  135: 213 */       _internal2Java.put(Type.Node, new JavaType(nodeListClass, 0));
/*  136: 214 */       _internal2Java.put(Type.Node, new JavaType(nodeClass, 1));
/*  137: 215 */       _internal2Java.put(Type.Node, new JavaType(Object.class, 2));
/*  138: 216 */       _internal2Java.put(Type.Node, new JavaType(String.class, 3));
/*  139:     */       
/*  140:     */ 
/*  141: 219 */       _internal2Java.put(Type.ResultTree, new JavaType(nodeListClass, 0));
/*  142: 220 */       _internal2Java.put(Type.ResultTree, new JavaType(nodeClass, 1));
/*  143: 221 */       _internal2Java.put(Type.ResultTree, new JavaType(Object.class, 2));
/*  144: 222 */       _internal2Java.put(Type.ResultTree, new JavaType(String.class, 3));
/*  145:     */       
/*  146: 224 */       _internal2Java.put(Type.Reference, new JavaType(Object.class, 0));
/*  147:     */       
/*  148:     */ 
/*  149: 227 */       _java2Internal.put(Boolean.TYPE, Type.Boolean);
/*  150: 228 */       _java2Internal.put(Void.TYPE, Type.Void);
/*  151: 229 */       _java2Internal.put(Character.TYPE, Type.Real);
/*  152: 230 */       _java2Internal.put(Byte.TYPE, Type.Real);
/*  153: 231 */       _java2Internal.put(Short.TYPE, Type.Real);
/*  154: 232 */       _java2Internal.put(Integer.TYPE, Type.Real);
/*  155: 233 */       _java2Internal.put(Long.TYPE, Type.Real);
/*  156: 234 */       _java2Internal.put(Float.TYPE, Type.Real);
/*  157: 235 */       _java2Internal.put(Double.TYPE, Type.Real);
/*  158:     */       
/*  159: 237 */       _java2Internal.put(String.class, Type.String);
/*  160:     */       
/*  161: 239 */       _java2Internal.put(Object.class, Type.Reference);
/*  162:     */       
/*  163:     */ 
/*  164: 242 */       _java2Internal.put(nodeListClass, Type.NodeSet);
/*  165: 243 */       _java2Internal.put(nodeClass, Type.NodeSet);
/*  166:     */       
/*  167:     */ 
/*  168: 246 */       _extensionNamespaceTable.put("http://xml.apache.org/xalan", "org.apache.xalan.lib.Extensions");
/*  169: 247 */       _extensionNamespaceTable.put("http://exslt.org/common", "org.apache.xalan.lib.ExsltCommon");
/*  170: 248 */       _extensionNamespaceTable.put("http://exslt.org/math", "org.apache.xalan.lib.ExsltMath");
/*  171: 249 */       _extensionNamespaceTable.put("http://exslt.org/sets", "org.apache.xalan.lib.ExsltSets");
/*  172: 250 */       _extensionNamespaceTable.put("http://exslt.org/dates-and-times", "org.apache.xalan.lib.ExsltDatetime");
/*  173: 251 */       _extensionNamespaceTable.put("http://exslt.org/strings", "org.apache.xalan.lib.ExsltStrings");
/*  174:     */       
/*  175:     */ 
/*  176: 254 */       _extensionFunctionTable.put("http://exslt.org/common:nodeSet", "nodeset");
/*  177: 255 */       _extensionFunctionTable.put("http://exslt.org/common:objectType", "objectType");
/*  178: 256 */       _extensionFunctionTable.put("http://xml.apache.org/xalan:nodeset", "nodeset");
/*  179:     */     }
/*  180:     */     catch (ClassNotFoundException e)
/*  181:     */     {
/*  182: 259 */       System.err.println(e);
/*  183:     */     }
/*  184:     */   }
/*  185:     */   
/*  186:     */   public FunctionCall(QName fname, Vector arguments)
/*  187:     */   {
/*  188: 264 */     this._fname = fname;
/*  189: 265 */     this._arguments = arguments;
/*  190: 266 */     this._type = null;
/*  191:     */   }
/*  192:     */   
/*  193:     */   public FunctionCall(QName fname)
/*  194:     */   {
/*  195: 270 */     this(fname, EMPTY_ARG_LIST);
/*  196:     */   }
/*  197:     */   
/*  198:     */   public String getName()
/*  199:     */   {
/*  200: 274 */     return this._fname.toString();
/*  201:     */   }
/*  202:     */   
/*  203:     */   public void setParser(Parser parser)
/*  204:     */   {
/*  205: 278 */     super.setParser(parser);
/*  206: 279 */     if (this._arguments != null)
/*  207:     */     {
/*  208: 280 */       int n = this._arguments.size();
/*  209: 281 */       for (int i = 0; i < n; i++)
/*  210:     */       {
/*  211: 282 */         Expression exp = (Expression)this._arguments.elementAt(i);
/*  212: 283 */         exp.setParser(parser);
/*  213: 284 */         exp.setParent(this);
/*  214:     */       }
/*  215:     */     }
/*  216:     */   }
/*  217:     */   
/*  218:     */   public String getClassNameFromUri(String uri)
/*  219:     */   {
/*  220: 291 */     String className = (String)_extensionNamespaceTable.get(uri);
/*  221: 293 */     if (className != null) {
/*  222: 294 */       return className;
/*  223:     */     }
/*  224: 296 */     if (uri.startsWith("http://xml.apache.org/xalan/xsltc/java"))
/*  225:     */     {
/*  226: 297 */       int length = "http://xml.apache.org/xalan/xsltc/java".length() + 1;
/*  227: 298 */       return uri.length() > length ? uri.substring(length) : "";
/*  228:     */     }
/*  229: 300 */     if (uri.startsWith("http://xml.apache.org/xalan/java"))
/*  230:     */     {
/*  231: 301 */       int length = "http://xml.apache.org/xalan/java".length() + 1;
/*  232: 302 */       return uri.length() > length ? uri.substring(length) : "";
/*  233:     */     }
/*  234: 304 */     if (uri.startsWith("http://xml.apache.org/xslt/java"))
/*  235:     */     {
/*  236: 305 */       int length = "http://xml.apache.org/xslt/java".length() + 1;
/*  237: 306 */       return uri.length() > length ? uri.substring(length) : "";
/*  238:     */     }
/*  239: 309 */     int index = uri.lastIndexOf('/');
/*  240: 310 */     return index > 0 ? uri.substring(index + 1) : uri;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public Type typeCheck(SymbolTable stable)
/*  244:     */     throws TypeCheckError
/*  245:     */   {
/*  246: 322 */     if (this._type != null) {
/*  247: 322 */       return this._type;
/*  248:     */     }
/*  249: 324 */     String namespace = this._fname.getNamespace();
/*  250: 325 */     String local = this._fname.getLocalPart();
/*  251: 327 */     if (isExtension())
/*  252:     */     {
/*  253: 328 */       this._fname = new QName(null, null, local);
/*  254: 329 */       return typeCheckStandard(stable);
/*  255:     */     }
/*  256: 331 */     if (isStandard()) {
/*  257: 332 */       return typeCheckStandard(stable);
/*  258:     */     }
/*  259:     */     try
/*  260:     */     {
/*  261: 337 */       this._className = getClassNameFromUri(namespace);
/*  262:     */       
/*  263: 339 */       int pos = local.lastIndexOf('.');
/*  264: 340 */       if (pos > 0)
/*  265:     */       {
/*  266: 341 */         this._isStatic = true;
/*  267: 342 */         if ((this._className != null) && (this._className.length() > 0))
/*  268:     */         {
/*  269: 343 */           this._namespace_format = 2;
/*  270: 344 */           this._className = (this._className + "." + local.substring(0, pos));
/*  271:     */         }
/*  272:     */         else
/*  273:     */         {
/*  274: 347 */           this._namespace_format = 0;
/*  275: 348 */           this._className = local.substring(0, pos);
/*  276:     */         }
/*  277: 351 */         this._fname = new QName(namespace, null, local.substring(pos + 1));
/*  278:     */       }
/*  279:     */       else
/*  280:     */       {
/*  281: 354 */         if ((this._className != null) && (this._className.length() > 0)) {
/*  282:     */           try
/*  283:     */           {
/*  284: 356 */             this._clazz = ObjectFactory.findProviderClass(this._className, ObjectFactory.findClassLoader(), true);
/*  285:     */             
/*  286: 358 */             this._namespace_format = 1;
/*  287:     */           }
/*  288:     */           catch (ClassNotFoundException e)
/*  289:     */           {
/*  290: 361 */             this._namespace_format = 2;
/*  291:     */           }
/*  292:     */         } else {
/*  293: 365 */           this._namespace_format = 0;
/*  294:     */         }
/*  295: 367 */         if (local.indexOf('-') > 0) {
/*  296: 368 */           local = replaceDash(local);
/*  297:     */         }
/*  298: 371 */         String extFunction = (String)_extensionFunctionTable.get(namespace + ":" + local);
/*  299: 372 */         if (extFunction != null)
/*  300:     */         {
/*  301: 373 */           this._fname = new QName(null, null, extFunction);
/*  302: 374 */           return typeCheckStandard(stable);
/*  303:     */         }
/*  304: 377 */         this._fname = new QName(namespace, null, local);
/*  305:     */       }
/*  306: 380 */       return typeCheckExternal(stable);
/*  307:     */     }
/*  308:     */     catch (TypeCheckError e)
/*  309:     */     {
/*  310: 383 */       ErrorMsg errorMsg = e.getErrorMsg();
/*  311: 384 */       if (errorMsg == null)
/*  312:     */       {
/*  313: 385 */         String name = this._fname.getLocalPart();
/*  314: 386 */         errorMsg = new ErrorMsg("METHOD_NOT_FOUND_ERR", name);
/*  315:     */       }
/*  316: 388 */       getParser().reportError(3, errorMsg);
/*  317:     */     }
/*  318: 389 */     return this._type = Type.Void;
/*  319:     */   }
/*  320:     */   
/*  321:     */   public Type typeCheckStandard(SymbolTable stable)
/*  322:     */     throws TypeCheckError
/*  323:     */   {
/*  324: 400 */     this._fname.clearNamespace();
/*  325:     */     
/*  326: 402 */     int n = this._arguments.size();
/*  327: 403 */     Vector argsType = typeCheckArgs(stable);
/*  328: 404 */     MethodType args = new MethodType(Type.Void, argsType);
/*  329: 405 */     MethodType ptype = lookupPrimop(stable, this._fname.getLocalPart(), args);
/*  330: 408 */     if (ptype != null)
/*  331:     */     {
/*  332: 409 */       for (int i = 0; i < n; i++)
/*  333:     */       {
/*  334: 410 */         Type argType = (Type)ptype.argsType().elementAt(i);
/*  335: 411 */         Expression exp = (Expression)this._arguments.elementAt(i);
/*  336: 412 */         if (!argType.identicalTo(exp.getType())) {
/*  337:     */           try
/*  338:     */           {
/*  339: 414 */             this._arguments.setElementAt(new CastExpr(exp, argType), i);
/*  340:     */           }
/*  341:     */           catch (TypeCheckError e)
/*  342:     */           {
/*  343: 417 */             throw new TypeCheckError(this);
/*  344:     */           }
/*  345:     */         }
/*  346:     */       }
/*  347: 421 */       this._chosenMethodType = ptype;
/*  348: 422 */       return this._type = ptype.resultType();
/*  349:     */     }
/*  350: 424 */     throw new TypeCheckError(this);
/*  351:     */   }
/*  352:     */   
/*  353:     */   public Type typeCheckConstructor(SymbolTable stable)
/*  354:     */     throws TypeCheckError
/*  355:     */   {
/*  356: 430 */     Vector constructors = findConstructors();
/*  357: 431 */     if (constructors == null) {
/*  358: 433 */       throw new TypeCheckError("CONSTRUCTOR_NOT_FOUND", this._className);
/*  359:     */     }
/*  360: 438 */     int nConstructors = constructors.size();
/*  361: 439 */     int nArgs = this._arguments.size();
/*  362: 440 */     Vector argsType = typeCheckArgs(stable);
/*  363:     */     
/*  364:     */ 
/*  365: 443 */     int bestConstrDistance = 2147483647;
/*  366: 444 */     this._type = null;
/*  367: 445 */     for (int i = 0; i < nConstructors; i++)
/*  368:     */     {
/*  369: 447 */       Constructor constructor = (Constructor)constructors.elementAt(i);
/*  370:     */       
/*  371: 449 */       Class[] paramTypes = constructor.getParameterTypes();
/*  372:     */       
/*  373: 451 */       Class extType = null;
/*  374: 452 */       int currConstrDistance = 0;
/*  375: 453 */       for (int j = 0; j < nArgs; j++)
/*  376:     */       {
/*  377: 455 */         extType = paramTypes[j];
/*  378: 456 */         Type intType = (Type)argsType.elementAt(j);
/*  379: 457 */         Object match = _internal2Java.maps(intType, extType);
/*  380: 458 */         if (match != null)
/*  381:     */         {
/*  382: 459 */           currConstrDistance += ((JavaType)match).distance;
/*  383:     */         }
/*  384: 461 */         else if ((intType instanceof ObjectType))
/*  385:     */         {
/*  386: 462 */           ObjectType objectType = (ObjectType)intType;
/*  387: 463 */           if (objectType.getJavaClass() != extType) {
/*  388: 465 */             if (extType.isAssignableFrom(objectType.getJavaClass()))
/*  389:     */             {
/*  390: 466 */               currConstrDistance++;
/*  391:     */             }
/*  392:     */             else
/*  393:     */             {
/*  394: 468 */               currConstrDistance = 2147483647;
/*  395: 469 */               break;
/*  396:     */             }
/*  397:     */           }
/*  398:     */         }
/*  399:     */         else
/*  400:     */         {
/*  401: 474 */           currConstrDistance = 2147483647;
/*  402: 475 */           break;
/*  403:     */         }
/*  404:     */       }
/*  405: 479 */       if ((j == nArgs) && (currConstrDistance < bestConstrDistance))
/*  406:     */       {
/*  407: 480 */         this._chosenConstructor = constructor;
/*  408: 481 */         this._isExtConstructor = true;
/*  409: 482 */         bestConstrDistance = currConstrDistance;
/*  410:     */         
/*  411: 484 */         this._type = (this._clazz != null ? Type.newObjectType(this._clazz) : Type.newObjectType(this._className));
/*  412:     */       }
/*  413:     */     }
/*  414: 489 */     if (this._type != null) {
/*  415: 490 */       return this._type;
/*  416:     */     }
/*  417: 493 */     throw new TypeCheckError("ARGUMENT_CONVERSION_ERR", getMethodSignature(argsType));
/*  418:     */   }
/*  419:     */   
/*  420:     */   public Type typeCheckExternal(SymbolTable stable)
/*  421:     */     throws TypeCheckError
/*  422:     */   {
/*  423: 505 */     int nArgs = this._arguments.size();
/*  424: 506 */     String name = this._fname.getLocalPart();
/*  425: 509 */     if (this._fname.getLocalPart().equals("new")) {
/*  426: 510 */       return typeCheckConstructor(stable);
/*  427:     */     }
/*  428: 514 */     boolean hasThisArgument = false;
/*  429: 516 */     if (nArgs == 0) {
/*  430: 517 */       this._isStatic = true;
/*  431:     */     }
/*  432: 519 */     if (!this._isStatic)
/*  433:     */     {
/*  434: 520 */       if ((this._namespace_format == 0) || (this._namespace_format == 2)) {
/*  435: 522 */         hasThisArgument = true;
/*  436:     */       }
/*  437: 524 */       Expression firstArg = (Expression)this._arguments.elementAt(0);
/*  438: 525 */       Type firstArgType = firstArg.typeCheck(stable);
/*  439: 527 */       if ((this._namespace_format == 1) && ((firstArgType instanceof ObjectType)) && (this._clazz != null) && (this._clazz.isAssignableFrom(((ObjectType)firstArgType).getJavaClass()))) {
/*  440: 531 */         hasThisArgument = true;
/*  441:     */       }
/*  442: 533 */       if (hasThisArgument)
/*  443:     */       {
/*  444: 534 */         this._thisArgument = ((Expression)this._arguments.elementAt(0));
/*  445: 535 */         this._arguments.remove(0);nArgs--;
/*  446: 536 */         if ((firstArgType instanceof ObjectType)) {
/*  447: 537 */           this._className = ((ObjectType)firstArgType).getJavaClassName();
/*  448:     */         } else {
/*  449: 540 */           throw new TypeCheckError("NO_JAVA_FUNCT_THIS_REF", name);
/*  450:     */         }
/*  451:     */       }
/*  452:     */     }
/*  453: 543 */     else if (this._className.length() == 0)
/*  454:     */     {
/*  455: 550 */       Parser parser = getParser();
/*  456: 551 */       if (parser != null) {
/*  457: 552 */         reportWarning(this, parser, "FUNCTION_RESOLVE_ERR", this._fname.toString());
/*  458:     */       }
/*  459: 555 */       this.unresolvedExternal = true;
/*  460: 556 */       return this._type = Type.Int;
/*  461:     */     }
/*  462: 560 */     Vector methods = findMethods();
/*  463: 562 */     if (methods == null) {
/*  464: 564 */       throw new TypeCheckError("METHOD_NOT_FOUND_ERR", this._className + "." + name);
/*  465:     */     }
/*  466: 567 */     Class extType = null;
/*  467: 568 */     int nMethods = methods.size();
/*  468: 569 */     Vector argsType = typeCheckArgs(stable);
/*  469:     */     
/*  470:     */ 
/*  471: 572 */     int bestMethodDistance = 2147483647;
/*  472: 573 */     this._type = null;
/*  473: 574 */     for (int i = 0; i < nMethods; i++)
/*  474:     */     {
/*  475: 576 */       Method method = (Method)methods.elementAt(i);
/*  476: 577 */       Class[] paramTypes = method.getParameterTypes();
/*  477:     */       
/*  478: 579 */       int currMethodDistance = 0;
/*  479: 580 */       for (int j = 0; j < nArgs; j++)
/*  480:     */       {
/*  481: 582 */         extType = paramTypes[j];
/*  482: 583 */         Type intType = (Type)argsType.elementAt(j);
/*  483: 584 */         Object match = _internal2Java.maps(intType, extType);
/*  484: 585 */         if (match != null)
/*  485:     */         {
/*  486: 586 */           currMethodDistance += ((JavaType)match).distance;
/*  487:     */         }
/*  488: 593 */         else if ((intType instanceof ReferenceType))
/*  489:     */         {
/*  490: 594 */           currMethodDistance++;
/*  491:     */         }
/*  492: 596 */         else if ((intType instanceof ObjectType))
/*  493:     */         {
/*  494: 597 */           ObjectType object = (ObjectType)intType;
/*  495: 598 */           if (extType.getName().equals(object.getJavaClassName()))
/*  496:     */           {
/*  497: 599 */             currMethodDistance += 0;
/*  498:     */           }
/*  499: 600 */           else if (extType.isAssignableFrom(object.getJavaClass()))
/*  500:     */           {
/*  501: 601 */             currMethodDistance++;
/*  502:     */           }
/*  503:     */           else
/*  504:     */           {
/*  505: 603 */             currMethodDistance = 2147483647;
/*  506: 604 */             break;
/*  507:     */           }
/*  508:     */         }
/*  509:     */         else
/*  510:     */         {
/*  511: 608 */           currMethodDistance = 2147483647;
/*  512: 609 */           break;
/*  513:     */         }
/*  514:     */       }
/*  515: 614 */       if (j == nArgs)
/*  516:     */       {
/*  517: 616 */         extType = method.getReturnType();
/*  518:     */         
/*  519: 618 */         this._type = ((Type)_java2Internal.get(extType));
/*  520: 619 */         if (this._type == null) {
/*  521: 620 */           this._type = Type.newObjectType(extType);
/*  522:     */         }
/*  523: 624 */         if ((this._type != null) && (currMethodDistance < bestMethodDistance))
/*  524:     */         {
/*  525: 625 */           this._chosenMethod = method;
/*  526: 626 */           bestMethodDistance = currMethodDistance;
/*  527:     */         }
/*  528:     */       }
/*  529:     */     }
/*  530: 633 */     if ((this._chosenMethod != null) && (this._thisArgument == null) && (!Modifier.isStatic(this._chosenMethod.getModifiers()))) {
/*  531: 635 */       throw new TypeCheckError("NO_JAVA_FUNCT_THIS_REF", getMethodSignature(argsType));
/*  532:     */     }
/*  533: 638 */     if (this._type != null)
/*  534:     */     {
/*  535: 639 */       if (this._type == Type.NodeSet) {
/*  536: 640 */         getXSLTC().setMultiDocument(true);
/*  537:     */       }
/*  538: 642 */       return this._type;
/*  539:     */     }
/*  540: 645 */     throw new TypeCheckError("ARGUMENT_CONVERSION_ERR", getMethodSignature(argsType));
/*  541:     */   }
/*  542:     */   
/*  543:     */   public Vector typeCheckArgs(SymbolTable stable)
/*  544:     */     throws TypeCheckError
/*  545:     */   {
/*  546: 652 */     Vector result = new Vector();
/*  547: 653 */     Enumeration e = this._arguments.elements();
/*  548: 654 */     while (e.hasMoreElements())
/*  549:     */     {
/*  550: 655 */       Expression exp = (Expression)e.nextElement();
/*  551: 656 */       result.addElement(exp.typeCheck(stable));
/*  552:     */     }
/*  553: 658 */     return result;
/*  554:     */   }
/*  555:     */   
/*  556:     */   protected final Expression argument(int i)
/*  557:     */   {
/*  558: 662 */     return (Expression)this._arguments.elementAt(i);
/*  559:     */   }
/*  560:     */   
/*  561:     */   protected final Expression argument()
/*  562:     */   {
/*  563: 666 */     return argument(0);
/*  564:     */   }
/*  565:     */   
/*  566:     */   protected final int argumentCount()
/*  567:     */   {
/*  568: 670 */     return this._arguments.size();
/*  569:     */   }
/*  570:     */   
/*  571:     */   protected final void setArgument(int i, Expression exp)
/*  572:     */   {
/*  573: 674 */     this._arguments.setElementAt(exp, i);
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/*  577:     */   {
/*  578: 684 */     Type type = Type.Boolean;
/*  579: 685 */     if (this._chosenMethodType != null) {
/*  580: 686 */       type = this._chosenMethodType.resultType();
/*  581:     */     }
/*  582: 688 */     InstructionList il = methodGen.getInstructionList();
/*  583: 689 */     translate(classGen, methodGen);
/*  584: 691 */     if (((type instanceof BooleanType)) || ((type instanceof IntType))) {
/*  585: 692 */       this._falseList.add(il.append(new IFEQ(null)));
/*  586:     */     }
/*  587:     */   }
/*  588:     */   
/*  589:     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  590:     */   {
/*  591: 702 */     int n = argumentCount();
/*  592: 703 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  593: 704 */     InstructionList il = methodGen.getInstructionList();
/*  594: 705 */     boolean isSecureProcessing = classGen.getParser().getXSLTC().isSecureProcessing();
/*  595:     */     int index;
/*  596: 709 */     if ((isStandard()) || (isExtension()))
/*  597:     */     {
/*  598: 710 */       for (int i = 0; i < n; i++)
/*  599:     */       {
/*  600: 711 */         Expression exp = argument(i);
/*  601: 712 */         exp.translate(classGen, methodGen);
/*  602: 713 */         exp.startIterator(classGen, methodGen);
/*  603:     */       }
/*  604: 717 */       String name = this._fname.toString().replace('-', '_') + "F";
/*  605: 718 */       String args = "";
/*  606: 721 */       if (name.equals("sumF"))
/*  607:     */       {
/*  608: 722 */         args = "Lorg/apache/xalan/xsltc/DOM;";
/*  609: 723 */         il.append(methodGen.loadDOM());
/*  610:     */       }
/*  611: 725 */       else if ((name.equals("normalize_spaceF")) && 
/*  612: 726 */         (this._chosenMethodType.toSignature(args).equals("()Ljava/lang/String;")))
/*  613:     */       {
/*  614: 728 */         args = "ILorg/apache/xalan/xsltc/DOM;";
/*  615: 729 */         il.append(methodGen.loadContextNode());
/*  616: 730 */         il.append(methodGen.loadDOM());
/*  617:     */       }
/*  618: 735 */       index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", name, this._chosenMethodType.toSignature(args));
/*  619:     */       
/*  620: 737 */       il.append(new INVOKESTATIC(index));
/*  621:     */     }
/*  622: 741 */     else if (this.unresolvedExternal)
/*  623:     */     {
/*  624: 742 */       index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "unresolved_externalF", "(Ljava/lang/String;)V");
/*  625:     */       
/*  626:     */ 
/*  627: 745 */       il.append(new PUSH(cpg, this._fname.toString()));
/*  628: 746 */       il.append(new INVOKESTATIC(index));
/*  629:     */     }
/*  630: 748 */     else if (this._isExtConstructor)
/*  631:     */     {
/*  632: 749 */       if (isSecureProcessing) {
/*  633: 750 */         translateUnallowedExtension(cpg, il);
/*  634:     */       }
/*  635: 752 */       String clazz = this._chosenConstructor.getDeclaringClass().getName();
/*  636:     */       
/*  637: 754 */       Class[] paramTypes = this._chosenConstructor.getParameterTypes();
/*  638: 755 */       LocalVariableGen[] paramTemp = new LocalVariableGen[n];
/*  639: 766 */       for (int i = 0; i < n; i++)
/*  640:     */       {
/*  641: 767 */         Expression exp = argument(i);
/*  642: 768 */         Type expType = exp.getType();
/*  643: 769 */         exp.translate(classGen, methodGen);
/*  644:     */         
/*  645: 771 */         exp.startIterator(classGen, methodGen);
/*  646: 772 */         expType.translateTo(classGen, methodGen, paramTypes[i]);
/*  647: 773 */         paramTemp[i] = methodGen.addLocalVariable("function_call_tmp" + i, expType.toJCType(), null, null);
/*  648:     */         
/*  649:     */ 
/*  650:     */ 
/*  651: 777 */         paramTemp[i].setStart(il.append(expType.STORE(paramTemp[i].getIndex())));
/*  652:     */       }
/*  653: 781 */       il.append(new NEW(cpg.addClass(this._className)));
/*  654: 782 */       il.append(InstructionConstants.DUP);
/*  655: 784 */       for (int i = 0; i < n; i++)
/*  656:     */       {
/*  657: 785 */         Expression arg = argument(i);
/*  658: 786 */         paramTemp[i].setEnd(il.append(arg.getType().LOAD(paramTemp[i].getIndex())));
/*  659:     */       }
/*  660: 790 */       StringBuffer buffer = new StringBuffer();
/*  661: 791 */       buffer.append('(');
/*  662: 792 */       for (int i = 0; i < paramTypes.length; i++) {
/*  663: 793 */         buffer.append(getSignature(paramTypes[i]));
/*  664:     */       }
/*  665: 795 */       buffer.append(')');
/*  666: 796 */       buffer.append("V");
/*  667:     */       
/*  668: 798 */       index = cpg.addMethodref(clazz, "<init>", buffer.toString());
/*  669:     */       
/*  670:     */ 
/*  671: 801 */       il.append(new INVOKESPECIAL(index));
/*  672:     */       
/*  673:     */ 
/*  674: 804 */       Type.Object.translateFrom(classGen, methodGen, this._chosenConstructor.getDeclaringClass());
/*  675:     */     }
/*  676:     */     else
/*  677:     */     {
/*  678: 810 */       if (isSecureProcessing) {
/*  679: 811 */         translateUnallowedExtension(cpg, il);
/*  680:     */       }
/*  681: 813 */       String clazz = this._chosenMethod.getDeclaringClass().getName();
/*  682: 814 */       Class[] paramTypes = this._chosenMethod.getParameterTypes();
/*  683: 817 */       if (this._thisArgument != null) {
/*  684: 818 */         this._thisArgument.translate(classGen, methodGen);
/*  685:     */       }
/*  686: 821 */       for (int i = 0; i < n; i++)
/*  687:     */       {
/*  688: 822 */         Expression exp = argument(i);
/*  689: 823 */         exp.translate(classGen, methodGen);
/*  690:     */         
/*  691: 825 */         exp.startIterator(classGen, methodGen);
/*  692: 826 */         exp.getType().translateTo(classGen, methodGen, paramTypes[i]);
/*  693:     */       }
/*  694: 829 */       StringBuffer buffer = new StringBuffer();
/*  695: 830 */       buffer.append('(');
/*  696: 831 */       for (int i = 0; i < paramTypes.length; i++) {
/*  697: 832 */         buffer.append(getSignature(paramTypes[i]));
/*  698:     */       }
/*  699: 834 */       buffer.append(')');
/*  700: 835 */       buffer.append(getSignature(this._chosenMethod.getReturnType()));
/*  701: 837 */       if ((this._thisArgument != null) && (this._clazz.isInterface()))
/*  702:     */       {
/*  703: 838 */         index = cpg.addInterfaceMethodref(clazz, this._fname.getLocalPart(), buffer.toString());
/*  704:     */         
/*  705:     */ 
/*  706: 841 */         il.append(new INVOKEINTERFACE(index, n + 1));
/*  707:     */       }
/*  708:     */       else
/*  709:     */       {
/*  710: 844 */         index = cpg.addMethodref(clazz, this._fname.getLocalPart(), buffer.toString());
/*  711:     */         
/*  712:     */ 
/*  713: 847 */         il.append(this._thisArgument != null ? new INVOKEVIRTUAL(index) : new INVOKESTATIC(index));
/*  714:     */       }
/*  715: 852 */       this._type.translateFrom(classGen, methodGen, this._chosenMethod.getReturnType());
/*  716:     */     }
/*  717:     */   }
/*  718:     */   
/*  719:     */   public String toString()
/*  720:     */   {
/*  721: 858 */     return "funcall(" + this._fname + ", " + this._arguments + ')';
/*  722:     */   }
/*  723:     */   
/*  724:     */   public boolean isStandard()
/*  725:     */   {
/*  726: 862 */     String namespace = this._fname.getNamespace();
/*  727: 863 */     return (namespace == null) || (namespace.equals(""));
/*  728:     */   }
/*  729:     */   
/*  730:     */   public boolean isExtension()
/*  731:     */   {
/*  732: 867 */     String namespace = this._fname.getNamespace();
/*  733: 868 */     return (namespace != null) && (namespace.equals("http://xml.apache.org/xalan/xsltc"));
/*  734:     */   }
/*  735:     */   
/*  736:     */   private Vector findMethods()
/*  737:     */   {
/*  738: 878 */     Vector result = null;
/*  739: 879 */     String namespace = this._fname.getNamespace();
/*  740: 881 */     if ((this._className != null) && (this._className.length() > 0))
/*  741:     */     {
/*  742: 882 */       int nArgs = this._arguments.size();
/*  743:     */       try
/*  744:     */       {
/*  745: 884 */         if (this._clazz == null)
/*  746:     */         {
/*  747: 885 */           this._clazz = ObjectFactory.findProviderClass(this._className, ObjectFactory.findClassLoader(), true);
/*  748: 888 */           if (this._clazz == null)
/*  749:     */           {
/*  750: 889 */             ErrorMsg msg = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/*  751:     */             
/*  752: 891 */             getParser().reportError(3, msg);
/*  753:     */           }
/*  754:     */         }
/*  755: 895 */         String methodName = this._fname.getLocalPart();
/*  756: 896 */         Method[] methods = this._clazz.getMethods();
/*  757: 898 */         for (int i = 0; i < methods.length; i++)
/*  758:     */         {
/*  759: 899 */           int mods = methods[i].getModifiers();
/*  760: 901 */           if ((Modifier.isPublic(mods)) && (methods[i].getName().equals(methodName)) && (methods[i].getParameterTypes().length == nArgs))
/*  761:     */           {
/*  762: 905 */             if (result == null) {
/*  763: 906 */               result = new Vector();
/*  764:     */             }
/*  765: 908 */             result.addElement(methods[i]);
/*  766:     */           }
/*  767:     */         }
/*  768:     */       }
/*  769:     */       catch (ClassNotFoundException e)
/*  770:     */       {
/*  771: 913 */         ErrorMsg msg = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/*  772: 914 */         getParser().reportError(3, msg);
/*  773:     */       }
/*  774:     */     }
/*  775: 917 */     return result;
/*  776:     */   }
/*  777:     */   
/*  778:     */   private Vector findConstructors()
/*  779:     */   {
/*  780: 926 */     Vector result = null;
/*  781: 927 */     String namespace = this._fname.getNamespace();
/*  782:     */     
/*  783: 929 */     int nArgs = this._arguments.size();
/*  784:     */     try
/*  785:     */     {
/*  786: 931 */       if (this._clazz == null)
/*  787:     */       {
/*  788: 932 */         this._clazz = ObjectFactory.findProviderClass(this._className, ObjectFactory.findClassLoader(), true);
/*  789: 935 */         if (this._clazz == null)
/*  790:     */         {
/*  791: 936 */           ErrorMsg msg = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/*  792: 937 */           getParser().reportError(3, msg);
/*  793:     */         }
/*  794:     */       }
/*  795: 941 */       Constructor[] constructors = this._clazz.getConstructors();
/*  796: 943 */       for (int i = 0; i < constructors.length; i++)
/*  797:     */       {
/*  798: 944 */         int mods = constructors[i].getModifiers();
/*  799: 946 */         if ((Modifier.isPublic(mods)) && (constructors[i].getParameterTypes().length == nArgs))
/*  800:     */         {
/*  801: 949 */           if (result == null) {
/*  802: 950 */             result = new Vector();
/*  803:     */           }
/*  804: 952 */           result.addElement(constructors[i]);
/*  805:     */         }
/*  806:     */       }
/*  807:     */     }
/*  808:     */     catch (ClassNotFoundException e)
/*  809:     */     {
/*  810: 957 */       ErrorMsg msg = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/*  811: 958 */       getParser().reportError(3, msg);
/*  812:     */     }
/*  813: 961 */     return result;
/*  814:     */   }
/*  815:     */   
/*  816:     */   static final String getSignature(Class clazz)
/*  817:     */   {
/*  818: 969 */     if (clazz.isArray())
/*  819:     */     {
/*  820: 970 */       StringBuffer sb = new StringBuffer();
/*  821: 971 */       Class cl = clazz;
/*  822: 972 */       while (cl.isArray())
/*  823:     */       {
/*  824: 973 */         sb.append("[");
/*  825: 974 */         cl = cl.getComponentType();
/*  826:     */       }
/*  827: 976 */       sb.append(getSignature(cl));
/*  828: 977 */       return sb.toString();
/*  829:     */     }
/*  830: 979 */     if (clazz.isPrimitive())
/*  831:     */     {
/*  832: 980 */       if (clazz == Integer.TYPE) {
/*  833: 981 */         return "I";
/*  834:     */       }
/*  835: 983 */       if (clazz == Byte.TYPE) {
/*  836: 984 */         return "B";
/*  837:     */       }
/*  838: 986 */       if (clazz == Long.TYPE) {
/*  839: 987 */         return "J";
/*  840:     */       }
/*  841: 989 */       if (clazz == Float.TYPE) {
/*  842: 990 */         return "F";
/*  843:     */       }
/*  844: 992 */       if (clazz == Double.TYPE) {
/*  845: 993 */         return "D";
/*  846:     */       }
/*  847: 995 */       if (clazz == Short.TYPE) {
/*  848: 996 */         return "S";
/*  849:     */       }
/*  850: 998 */       if (clazz == Character.TYPE) {
/*  851: 999 */         return "C";
/*  852:     */       }
/*  853:1001 */       if (clazz == Boolean.TYPE) {
/*  854:1002 */         return "Z";
/*  855:     */       }
/*  856:1004 */       if (clazz == Void.TYPE) {
/*  857:1005 */         return "V";
/*  858:     */       }
/*  859:1008 */       String name = clazz.toString();
/*  860:1009 */       ErrorMsg err = new ErrorMsg("UNKNOWN_SIG_TYPE_ERR", name);
/*  861:1010 */       throw new Error(err.toString());
/*  862:     */     }
/*  863:1014 */     return "L" + clazz.getName().replace('.', '/') + ';';
/*  864:     */   }
/*  865:     */   
/*  866:     */   static final String getSignature(Method meth)
/*  867:     */   {
/*  868:1022 */     StringBuffer sb = new StringBuffer();
/*  869:1023 */     sb.append('(');
/*  870:1024 */     Class[] params = meth.getParameterTypes();
/*  871:1025 */     for (int j = 0; j < params.length; j++) {
/*  872:1026 */       sb.append(getSignature(params[j]));
/*  873:     */     }
/*  874:1028 */     return ')' + getSignature(meth.getReturnType());
/*  875:     */   }
/*  876:     */   
/*  877:     */   static final String getSignature(Constructor cons)
/*  878:     */   {
/*  879:1036 */     StringBuffer sb = new StringBuffer();
/*  880:1037 */     sb.append('(');
/*  881:1038 */     Class[] params = cons.getParameterTypes();
/*  882:1039 */     for (int j = 0; j < params.length; j++) {
/*  883:1040 */       sb.append(getSignature(params[j]));
/*  884:     */     }
/*  885:1042 */     return ")V";
/*  886:     */   }
/*  887:     */   
/*  888:     */   private String getMethodSignature(Vector argsType)
/*  889:     */   {
/*  890:1049 */     StringBuffer buf = new StringBuffer(this._className);
/*  891:1050 */     buf.append('.').append(this._fname.getLocalPart()).append('(');
/*  892:     */     
/*  893:1052 */     int nArgs = argsType.size();
/*  894:1053 */     for (int i = 0; i < nArgs; i++)
/*  895:     */     {
/*  896:1054 */       Type intType = (Type)argsType.elementAt(i);
/*  897:1055 */       buf.append(intType.toString());
/*  898:1056 */       if (i < nArgs - 1) {
/*  899:1056 */         buf.append(", ");
/*  900:     */       }
/*  901:     */     }
/*  902:1059 */     buf.append(')');
/*  903:1060 */     return buf.toString();
/*  904:     */   }
/*  905:     */   
/*  906:     */   protected static String replaceDash(String name)
/*  907:     */   {
/*  908:1070 */     char dash = '-';
/*  909:1071 */     StringBuffer buff = new StringBuffer("");
/*  910:1072 */     for (int i = 0; i < name.length(); i++) {
/*  911:1073 */       if ((i > 0) && (name.charAt(i - 1) == dash)) {
/*  912:1074 */         buff.append(Character.toUpperCase(name.charAt(i)));
/*  913:1075 */       } else if (name.charAt(i) != dash) {
/*  914:1076 */         buff.append(name.charAt(i));
/*  915:     */       }
/*  916:     */     }
/*  917:1078 */     return buff.toString();
/*  918:     */   }
/*  919:     */   
/*  920:     */   private void translateUnallowedExtension(ConstantPoolGen cpg, InstructionList il)
/*  921:     */   {
/*  922:1087 */     int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "unallowed_extension_functionF", "(Ljava/lang/String;)V");
/*  923:     */     
/*  924:     */ 
/*  925:1090 */     il.append(new PUSH(cpg, this._fname.toString()));
/*  926:1091 */     il.append(new INVOKESTATIC(index));
/*  927:     */   }
/*  928:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.FunctionCall
 * JD-Core Version:    0.7.0.1
 */