/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class NativeJavaTopPackage
/*   4:    */   extends NativeJavaPackage
/*   5:    */   implements Function, IdFunctionCall
/*   6:    */ {
/*   7:    */   static final long serialVersionUID = -1455787259477709999L;
/*   8: 63 */   private static final String[][] commonPackages = { { "java", "lang", "reflect" }, { "java", "io" }, { "java", "math" }, { "java", "net" }, { "java", "util", "zip" }, { "java", "text", "resources" }, { "java", "applet" }, { "javax", "swing" } };
/*   9:    */   
/*  10:    */   NativeJavaTopPackage(ClassLoader loader)
/*  11:    */   {
/*  12: 76 */     super(true, "", loader);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  16:    */   {
/*  17: 82 */     return construct(cx, scope, args);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  21:    */   {
/*  22: 87 */     ClassLoader loader = null;
/*  23: 88 */     if (args.length != 0)
/*  24:    */     {
/*  25: 89 */       Object arg = args[0];
/*  26: 90 */       if ((arg instanceof Wrapper)) {
/*  27: 91 */         arg = ((Wrapper)arg).unwrap();
/*  28:    */       }
/*  29: 93 */       if ((arg instanceof ClassLoader)) {
/*  30: 94 */         loader = (ClassLoader)arg;
/*  31:    */       }
/*  32:    */     }
/*  33: 97 */     if (loader == null)
/*  34:    */     {
/*  35: 98 */       Context.reportRuntimeError0("msg.not.classloader");
/*  36: 99 */       return null;
/*  37:    */     }
/*  38:101 */     NativeJavaPackage pkg = new NativeJavaPackage(true, "", loader);
/*  39:102 */     ScriptRuntime.setObjectProtoAndParent(pkg, scope);
/*  40:103 */     return pkg;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static void init(Context cx, Scriptable scope, boolean sealed)
/*  44:    */   {
/*  45:108 */     ClassLoader loader = cx.getApplicationClassLoader();
/*  46:109 */     NativeJavaTopPackage top = new NativeJavaTopPackage(loader);
/*  47:110 */     top.setPrototype(getObjectPrototype(scope));
/*  48:111 */     top.setParentScope(scope);
/*  49:113 */     for (int i = 0; i != commonPackages.length; i++)
/*  50:    */     {
/*  51:114 */       NativeJavaPackage parent = top;
/*  52:115 */       for (int j = 0; j != commonPackages[i].length; j++) {
/*  53:116 */         parent = parent.forcePackage(commonPackages[i][j], scope);
/*  54:    */       }
/*  55:    */     }
/*  56:121 */     IdFunctionObject getClass = new IdFunctionObject(top, FTAG, 1, "getClass", 1, scope);
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:127 */     String[] topNames = { "java", "javax", "org", "com", "edu", "net" };
/*  63:128 */     NativeJavaPackage[] topPackages = new NativeJavaPackage[topNames.length];
/*  64:129 */     for (int i = 0; i < topNames.length; i++) {
/*  65:130 */       topPackages[i] = ((NativeJavaPackage)top.get(topNames[i], top));
/*  66:    */     }
/*  67:135 */     ScriptableObject global = (ScriptableObject)scope;
/*  68:137 */     if (sealed) {
/*  69:138 */       getClass.sealObject();
/*  70:    */     }
/*  71:140 */     getClass.exportAsScopeProperty();
/*  72:141 */     global.defineProperty("Packages", top, 2);
/*  73:142 */     for (int i = 0; i < topNames.length; i++) {
/*  74:143 */       global.defineProperty(topNames[i], topPackages[i], 2);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  79:    */   {
/*  80:151 */     if ((f.hasTag(FTAG)) && 
/*  81:152 */       (f.methodId() == 1)) {
/*  82:153 */       return js_getClass(cx, scope, args);
/*  83:    */     }
/*  84:156 */     throw f.unknown();
/*  85:    */   }
/*  86:    */   
/*  87:    */   private Scriptable js_getClass(Context cx, Scriptable scope, Object[] args)
/*  88:    */   {
/*  89:161 */     if ((args.length > 0) && ((args[0] instanceof Wrapper)))
/*  90:    */     {
/*  91:162 */       Scriptable result = this;
/*  92:163 */       Class<?> cl = ((Wrapper)args[0]).unwrap().getClass();
/*  93:    */       
/*  94:    */ 
/*  95:166 */       String name = cl.getName();
/*  96:167 */       int offset = 0;
/*  97:    */       for (;;)
/*  98:    */       {
/*  99:169 */         int index = name.indexOf('.', offset);
/* 100:170 */         String propName = index == -1 ? name.substring(offset) : name.substring(offset, index);
/* 101:    */         
/* 102:    */ 
/* 103:173 */         Object prop = result.get(propName, result);
/* 104:174 */         if (!(prop instanceof Scriptable)) {
/* 105:    */           break;
/* 106:    */         }
/* 107:176 */         result = (Scriptable)prop;
/* 108:177 */         if (index == -1) {
/* 109:178 */           return result;
/* 110:    */         }
/* 111:179 */         offset = index + 1;
/* 112:    */       }
/* 113:    */     }
/* 114:182 */     throw Context.reportRuntimeError0("msg.not.java.obj");
/* 115:    */   }
/* 116:    */   
/* 117:185 */   private static final Object FTAG = "JavaTopPackage";
/* 118:    */   private static final int Id_getClass = 1;
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeJavaTopPackage
 * JD-Core Version:    0.7.0.1
 */