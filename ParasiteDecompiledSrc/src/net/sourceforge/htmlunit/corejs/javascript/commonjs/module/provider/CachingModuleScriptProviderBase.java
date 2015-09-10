/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScript;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScriptProvider;
/*   6:    */ 
/*   7:    */ public abstract class CachingModuleScriptProviderBase
/*   8:    */   implements ModuleScriptProvider, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 1L;
/*  11: 27 */   private static final int loadConcurrencyLevel = Runtime.getRuntime().availableProcessors() * 8;
/*  12:    */   private static final int loadLockShift;
/*  13:    */   private static final int loadLockMask;
/*  14:    */   private static final int loadLockCount;
/*  15:    */   
/*  16:    */   static
/*  17:    */   {
/*  18: 33 */     int sshift = 0;
/*  19: 34 */     int ssize = 1;
/*  20: 35 */     while (ssize < loadConcurrencyLevel)
/*  21:    */     {
/*  22: 36 */       sshift++;
/*  23: 37 */       ssize <<= 1;
/*  24:    */     }
/*  25: 39 */     loadLockShift = 32 - sshift;
/*  26: 40 */     loadLockMask = ssize - 1;
/*  27: 41 */     loadLockCount = ssize;
/*  28:    */   }
/*  29:    */   
/*  30: 43 */   private final Object[] loadLocks = new Object[loadLockCount];
/*  31:    */   private final ModuleSourceProvider moduleSourceProvider;
/*  32:    */   
/*  33:    */   protected CachingModuleScriptProviderBase(ModuleSourceProvider moduleSourceProvider)
/*  34:    */   {
/*  35: 44 */     for (int i = 0; i < this.loadLocks.length; i++) {
/*  36: 45 */       this.loadLocks[i] = new Object();
/*  37:    */     }
/*  38: 57 */     this.moduleSourceProvider = moduleSourceProvider;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static class CachedModuleScript
/*  42:    */   {
/*  43:    */     private final ModuleScript moduleScript;
/*  44:    */     private final Object validator;
/*  45:    */     
/*  46:    */     public CachedModuleScript(ModuleScript moduleScript, Object validator)
/*  47:    */     {
/*  48:133 */       this.moduleScript = moduleScript;
/*  49:134 */       this.validator = validator;
/*  50:    */     }
/*  51:    */     
/*  52:    */     ModuleScript getModule()
/*  53:    */     {
/*  54:142 */       return this.moduleScript;
/*  55:    */     }
/*  56:    */     
/*  57:    */     Object getValidator()
/*  58:    */     {
/*  59:150 */       return this.validator;
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   private static Object getValidator(CachedModuleScript cachedModule)
/*  64:    */   {
/*  65:155 */     return cachedModule == null ? null : cachedModule.getValidator();
/*  66:    */   }
/*  67:    */   
/*  68:    */   private static boolean equal(Object o1, Object o2)
/*  69:    */   {
/*  70:159 */     return o1 == null ? false : o2 == null ? true : o1.equals(o2);
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected static int getConcurrencyLevel()
/*  74:    */   {
/*  75:167 */     return loadLockCount;
/*  76:    */   }
/*  77:    */   
/*  78:    */   /* Error */
/*  79:    */   public ModuleScript getModuleScript(net.sourceforge.htmlunit.corejs.javascript.Context cx, String moduleId, java.net.URI moduleUri, net.sourceforge.htmlunit.corejs.javascript.Scriptable paths)
/*  80:    */     throws java.lang.Exception
/*  81:    */   {
/*  82:    */     // Byte code:
/*  83:    */     //   0: aload_0
/*  84:    */     //   1: aload_2
/*  85:    */     //   2: invokevirtual 6	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:getLoadedModule	(Ljava/lang/String;)Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase$CachedModuleScript;
/*  86:    */     //   5: astore 5
/*  87:    */     //   7: aload 5
/*  88:    */     //   9: invokestatic 7	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:getValidator	(Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase$CachedModuleScript;)Ljava/lang/Object;
/*  89:    */     //   12: astore 6
/*  90:    */     //   14: aload_3
/*  91:    */     //   15: ifnonnull +20 -> 35
/*  92:    */     //   18: aload_0
/*  93:    */     //   19: getfield 5	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:moduleSourceProvider	Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSourceProvider;
/*  94:    */     //   22: aload_2
/*  95:    */     //   23: aload 4
/*  96:    */     //   25: aload 6
/*  97:    */     //   27: invokeinterface 8 4 0
/*  98:    */     //   32: goto +15 -> 47
/*  99:    */     //   35: aload_0
/* 100:    */     //   36: getfield 5	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:moduleSourceProvider	Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSourceProvider;
/* 101:    */     //   39: aload_3
/* 102:    */     //   40: aload 6
/* 103:    */     //   42: invokeinterface 9 3 0
/* 104:    */     //   47: astore 7
/* 105:    */     //   49: aload 7
/* 106:    */     //   51: getstatic 10	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSourceProvider:NOT_MODIFIED	Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSource;
/* 107:    */     //   54: if_acmpne +9 -> 63
/* 108:    */     //   57: aload 5
/* 109:    */     //   59: invokevirtual 11	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase$CachedModuleScript:getModule	()Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/ModuleScript;
/* 110:    */     //   62: areturn
/* 111:    */     //   63: aload 7
/* 112:    */     //   65: ifnonnull +5 -> 70
/* 113:    */     //   68: aconst_null
/* 114:    */     //   69: areturn
/* 115:    */     //   70: aload 7
/* 116:    */     //   72: invokevirtual 12	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSource:getReader	()Ljava/io/Reader;
/* 117:    */     //   75: astore 8
/* 118:    */     //   77: aload_2
/* 119:    */     //   78: invokevirtual 13	java/lang/String:hashCode	()I
/* 120:    */     //   81: istore 9
/* 121:    */     //   83: aload_0
/* 122:    */     //   84: getfield 4	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:loadLocks	[Ljava/lang/Object;
/* 123:    */     //   87: iload 9
/* 124:    */     //   89: getstatic 14	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:loadLockShift	I
/* 125:    */     //   92: iushr
/* 126:    */     //   93: getstatic 15	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:loadLockMask	I
/* 127:    */     //   96: iand
/* 128:    */     //   97: aaload
/* 129:    */     //   98: dup
/* 130:    */     //   99: astore 10
/* 131:    */     //   101: monitorenter
/* 132:    */     //   102: aload_0
/* 133:    */     //   103: aload_2
/* 134:    */     //   104: invokevirtual 6	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:getLoadedModule	(Ljava/lang/String;)Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase$CachedModuleScript;
/* 135:    */     //   107: astore 11
/* 136:    */     //   109: aload 11
/* 137:    */     //   111: ifnull +34 -> 145
/* 138:    */     //   114: aload 6
/* 139:    */     //   116: aload 11
/* 140:    */     //   118: invokestatic 7	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:getValidator	(Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase$CachedModuleScript;)Ljava/lang/Object;
/* 141:    */     //   121: invokestatic 16	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:equal	(Ljava/lang/Object;Ljava/lang/Object;)Z
/* 142:    */     //   124: ifne +21 -> 145
/* 143:    */     //   127: aload 11
/* 144:    */     //   129: invokevirtual 11	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase$CachedModuleScript:getModule	()Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/ModuleScript;
/* 145:    */     //   132: astore 12
/* 146:    */     //   134: aload 10
/* 147:    */     //   136: monitorexit
/* 148:    */     //   137: aload 8
/* 149:    */     //   139: invokevirtual 17	java/io/Reader:close	()V
/* 150:    */     //   142: aload 12
/* 151:    */     //   144: areturn
/* 152:    */     //   145: aload 7
/* 153:    */     //   147: invokevirtual 18	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSource:getUri	()Ljava/net/URI;
/* 154:    */     //   150: astore 12
/* 155:    */     //   152: new 19	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/ModuleScript
/* 156:    */     //   155: dup
/* 157:    */     //   156: aload_1
/* 158:    */     //   157: aload 8
/* 159:    */     //   159: aload 12
/* 160:    */     //   161: invokevirtual 20	java/net/URI:toString	()Ljava/lang/String;
/* 161:    */     //   164: iconst_1
/* 162:    */     //   165: aload 7
/* 163:    */     //   167: invokevirtual 21	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSource:getSecurityDomain	()Ljava/lang/Object;
/* 164:    */     //   170: invokevirtual 22	net/sourceforge/htmlunit/corejs/javascript/Context:compileReader	(Ljava/io/Reader;Ljava/lang/String;ILjava/lang/Object;)Lnet/sourceforge/htmlunit/corejs/javascript/Script;
/* 165:    */     //   173: aload 12
/* 166:    */     //   175: aload 7
/* 167:    */     //   177: invokevirtual 23	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSource:getBase	()Ljava/net/URI;
/* 168:    */     //   180: invokespecial 24	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/ModuleScript:<init>	(Lnet/sourceforge/htmlunit/corejs/javascript/Script;Ljava/net/URI;Ljava/net/URI;)V
/* 169:    */     //   183: astore 13
/* 170:    */     //   185: aload_0
/* 171:    */     //   186: aload_2
/* 172:    */     //   187: aload 13
/* 173:    */     //   189: aload 7
/* 174:    */     //   191: invokevirtual 25	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/ModuleSource:getValidator	()Ljava/lang/Object;
/* 175:    */     //   194: invokevirtual 26	net/sourceforge/htmlunit/corejs/javascript/commonjs/module/provider/CachingModuleScriptProviderBase:putLoadedModule	(Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/commonjs/module/ModuleScript;Ljava/lang/Object;)V
/* 176:    */     //   197: aload 13
/* 177:    */     //   199: astore 14
/* 178:    */     //   201: aload 10
/* 179:    */     //   203: monitorexit
/* 180:    */     //   204: aload 8
/* 181:    */     //   206: invokevirtual 17	java/io/Reader:close	()V
/* 182:    */     //   209: aload 14
/* 183:    */     //   211: areturn
/* 184:    */     //   212: astore 15
/* 185:    */     //   214: aload 10
/* 186:    */     //   216: monitorexit
/* 187:    */     //   217: aload 15
/* 188:    */     //   219: athrow
/* 189:    */     //   220: astore 16
/* 190:    */     //   222: aload 8
/* 191:    */     //   224: invokevirtual 17	java/io/Reader:close	()V
/* 192:    */     //   227: aload 16
/* 193:    */     //   229: athrow
/* 194:    */     // Line number table:
/* 195:    */     //   Java source line #63	-> byte code offset #0
/* 196:    */     //   Java source line #64	-> byte code offset #7
/* 197:    */     //   Java source line #65	-> byte code offset #14
/* 198:    */     //   Java source line #68	-> byte code offset #49
/* 199:    */     //   Java source line #69	-> byte code offset #57
/* 200:    */     //   Java source line #71	-> byte code offset #63
/* 201:    */     //   Java source line #72	-> byte code offset #68
/* 202:    */     //   Java source line #74	-> byte code offset #70
/* 203:    */     //   Java source line #76	-> byte code offset #77
/* 204:    */     //   Java source line #77	-> byte code offset #83
/* 205:    */     //   Java source line #78	-> byte code offset #102
/* 206:    */     //   Java source line #79	-> byte code offset #109
/* 207:    */     //   Java source line #80	-> byte code offset #114
/* 208:    */     //   Java source line #81	-> byte code offset #127
/* 209:    */     //   Java source line #95	-> byte code offset #137
/* 210:    */     //   Java source line #84	-> byte code offset #145
/* 211:    */     //   Java source line #85	-> byte code offset #152
/* 212:    */     //   Java source line #89	-> byte code offset #185
/* 213:    */     //   Java source line #91	-> byte code offset #197
/* 214:    */     //   Java source line #95	-> byte code offset #204
/* 215:    */     //   Java source line #92	-> byte code offset #212
/* 216:    */     //   Java source line #95	-> byte code offset #220
/* 217:    */     // Local variable table:
/* 218:    */     //   start	length	slot	name	signature
/* 219:    */     //   0	230	0	this	CachingModuleScriptProviderBase
/* 220:    */     //   0	230	1	cx	net.sourceforge.htmlunit.corejs.javascript.Context
/* 221:    */     //   0	230	2	moduleId	String
/* 222:    */     //   0	230	3	moduleUri	java.net.URI
/* 223:    */     //   0	230	4	paths	net.sourceforge.htmlunit.corejs.javascript.Scriptable
/* 224:    */     //   5	53	5	cachedModule1	CachedModuleScript
/* 225:    */     //   12	103	6	validator1	Object
/* 226:    */     //   47	143	7	moduleSource	ModuleSource
/* 227:    */     //   75	148	8	reader	java.io.Reader
/* 228:    */     //   81	7	9	idHash	int
/* 229:    */     //   107	21	11	cachedModule2	CachedModuleScript
/* 230:    */     //   132	11	12	localModuleScript1	ModuleScript
/* 231:    */     //   150	24	12	sourceUri	java.net.URI
/* 232:    */     //   183	15	13	moduleScript	ModuleScript
/* 233:    */     //   199	11	14	localModuleScript2	ModuleScript
/* 234:    */     //   212	6	15	localObject1	Object
/* 235:    */     //   220	8	16	localObject2	Object
/* 236:    */     // Exception table:
/* 237:    */     //   from	to	target	type
/* 238:    */     //   102	137	212	finally
/* 239:    */     //   145	204	212	finally
/* 240:    */     //   212	217	212	finally
/* 241:    */     //   77	137	220	finally
/* 242:    */     //   145	204	220	finally
/* 243:    */     //   212	222	220	finally
/* 244:    */   }
/* 245:    */   
/* 246:    */   protected abstract void putLoadedModule(String paramString, ModuleScript paramModuleScript, Object paramObject);
/* 247:    */   
/* 248:    */   protected abstract CachedModuleScript getLoadedModule(String paramString);
/* 249:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.CachingModuleScriptProviderBase
 * JD-Core Version:    0.7.0.1
 */