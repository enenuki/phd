/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.security.AccessController;
/*   4:    */ import java.security.PrivilegedAction;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.xml.XMLLib.Factory;
/*   6:    */ 
/*   7:    */ public class ContextFactory
/*   8:    */ {
/*   9:    */   private static volatile boolean hasCustomGlobal;
/*  10:145 */   private static ContextFactory global = new ContextFactory();
/*  11:    */   private volatile boolean sealed;
/*  12:    */   private final Object listenersLock;
/*  13:    */   private volatile Object listeners;
/*  14:    */   private boolean disabledListening;
/*  15:    */   private ClassLoader applicationClassLoader;
/*  16:    */   
/*  17:    */   public ContextFactory()
/*  18:    */   {
/*  19:149 */     this.listenersLock = new Object();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static ContextFactory getGlobal()
/*  23:    */   {
/*  24:179 */     return global;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static boolean hasExplicitGlobal()
/*  28:    */   {
/*  29:193 */     return hasCustomGlobal;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static synchronized void initGlobal(ContextFactory factory)
/*  33:    */   {
/*  34:205 */     if (factory == null) {
/*  35:206 */       throw new IllegalArgumentException();
/*  36:    */     }
/*  37:208 */     if (hasCustomGlobal) {
/*  38:209 */       throw new IllegalStateException();
/*  39:    */     }
/*  40:211 */     hasCustomGlobal = true;
/*  41:212 */     global = factory;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static synchronized GlobalSetter getGlobalSetter()
/*  45:    */   {
/*  46:221 */     if (hasCustomGlobal) {
/*  47:222 */       throw new IllegalStateException();
/*  48:    */     }
/*  49:224 */     hasCustomGlobal = true;
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:233 */     new GlobalSetter()
/*  59:    */     {
/*  60:    */       public void setContextFactoryGlobal(ContextFactory factory)
/*  61:    */       {
/*  62:227 */         ContextFactory.access$002(factory == null ? new ContextFactory() : factory);
/*  63:    */       }
/*  64:    */       
/*  65:    */       public ContextFactory getContextFactoryGlobal()
/*  66:    */       {
/*  67:230 */         return ContextFactory.global;
/*  68:    */       }
/*  69:    */     };
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected Context makeContext()
/*  73:    */   {
/*  74:247 */     return new Context(this);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected boolean hasFeature(Context cx, int featureIndex)
/*  78:    */   {
/*  79:    */     int version;
/*  80:258 */     switch (featureIndex)
/*  81:    */     {
/*  82:    */     case 1: 
/*  83:271 */       version = cx.getLanguageVersion();
/*  84:272 */       return (version == 100) || (version == 110) || (version == 120);
/*  85:    */     case 2: 
/*  86:277 */       return false;
/*  87:    */     case 3: 
/*  88:280 */       return true;
/*  89:    */     case 4: 
/*  90:283 */       version = cx.getLanguageVersion();
/*  91:284 */       return version == 120;
/*  92:    */     case 5: 
/*  93:287 */       return true;
/*  94:    */     case 6: 
/*  95:290 */       version = cx.getLanguageVersion();
/*  96:291 */       return (version == 0) || (version >= 160);
/*  97:    */     case 7: 
/*  98:295 */       return false;
/*  99:    */     case 8: 
/* 100:298 */       return false;
/* 101:    */     case 9: 
/* 102:301 */       return false;
/* 103:    */     case 10: 
/* 104:304 */       return false;
/* 105:    */     case 11: 
/* 106:307 */       return false;
/* 107:    */     case 12: 
/* 108:310 */       return false;
/* 109:    */     case 13: 
/* 110:    */     case 14: 
/* 111:314 */       return false;
/* 112:    */     case 15: 
/* 113:316 */       return true;
/* 114:    */     }
/* 115:319 */     throw new IllegalArgumentException(String.valueOf(featureIndex));
/* 116:    */   }
/* 117:    */   
/* 118:    */   private boolean isDom3Present()
/* 119:    */   {
/* 120:323 */     Class<?> nodeClass = Kit.classOrNull("org.w3c.dom.Node");
/* 121:324 */     if (nodeClass == null) {
/* 122:324 */       return false;
/* 123:    */     }
/* 124:    */     try
/* 125:    */     {
/* 126:328 */       nodeClass.getMethod("getUserData", new Class[] { String.class });
/* 127:329 */       return true;
/* 128:    */     }
/* 129:    */     catch (NoSuchMethodException e) {}
/* 130:331 */     return false;
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected XMLLib.Factory getE4xImplementationFactory()
/* 134:    */   {
/* 135:355 */     if (isDom3Present()) {
/* 136:356 */       return XMLLib.Factory.create("net.sourceforge.htmlunit.corejs.javascript.xmlimpl.XMLLibImpl");
/* 137:    */     }
/* 138:359 */     if (Kit.classOrNull("org.apache.xmlbeans.XmlCursor") != null) {
/* 139:360 */       return XMLLib.Factory.create("net.sourceforge.htmlunit.corejs.javascript.xml.impl.xmlbeans.XMLLibImpl");
/* 140:    */     }
/* 141:364 */     return null;
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected GeneratedClassLoader createClassLoader(final ClassLoader parent)
/* 145:    */   {
/* 146:379 */     (GeneratedClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/* 147:    */     {
/* 148:    */       public DefiningClassLoader run()
/* 149:    */       {
/* 150:381 */         return new DefiningClassLoader(parent);
/* 151:    */       }
/* 152:    */     });
/* 153:    */   }
/* 154:    */   
/* 155:    */   public final ClassLoader getApplicationClassLoader()
/* 156:    */   {
/* 157:394 */     return this.applicationClassLoader;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public final void initApplicationClassLoader(ClassLoader loader)
/* 161:    */   {
/* 162:404 */     if (loader == null) {
/* 163:405 */       throw new IllegalArgumentException("loader is null");
/* 164:    */     }
/* 165:406 */     if (!Kit.testIfCanLoadRhinoClasses(loader)) {
/* 166:407 */       throw new IllegalArgumentException("Loader can not resolve Rhino classes");
/* 167:    */     }
/* 168:410 */     if (this.applicationClassLoader != null) {
/* 169:411 */       throw new IllegalStateException("applicationClassLoader can only be set once");
/* 170:    */     }
/* 171:413 */     checkNotSealed();
/* 172:    */     
/* 173:415 */     this.applicationClassLoader = loader;
/* 174:    */   }
/* 175:    */   
/* 176:    */   protected Object doTopCall(Callable callable, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 177:    */   {
/* 178:429 */     return callable.call(cx, scope, thisObj, args);
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected void observeInstructionCount(Context cx, int instructionCount) {}
/* 182:    */   
/* 183:    */   protected void onContextCreated(Context cx)
/* 184:    */   {
/* 185:443 */     Object listeners = this.listeners;
/* 186:444 */     for (int i = 0;; i++)
/* 187:    */     {
/* 188:445 */       Listener l = (Listener)Kit.getListener(listeners, i);
/* 189:446 */       if (l == null) {
/* 190:    */         break;
/* 191:    */       }
/* 192:448 */       l.contextCreated(cx);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected void onContextReleased(Context cx)
/* 197:    */   {
/* 198:454 */     Object listeners = this.listeners;
/* 199:455 */     for (int i = 0;; i++)
/* 200:    */     {
/* 201:456 */       Listener l = (Listener)Kit.getListener(listeners, i);
/* 202:457 */       if (l == null) {
/* 203:    */         break;
/* 204:    */       }
/* 205:459 */       l.contextReleased(cx);
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   public final void addListener(Listener listener)
/* 210:    */   {
/* 211:465 */     checkNotSealed();
/* 212:466 */     synchronized (this.listenersLock)
/* 213:    */     {
/* 214:467 */       if (this.disabledListening) {
/* 215:468 */         throw new IllegalStateException();
/* 216:    */       }
/* 217:470 */       this.listeners = Kit.addListener(this.listeners, listener);
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public final void removeListener(Listener listener)
/* 222:    */   {
/* 223:476 */     checkNotSealed();
/* 224:477 */     synchronized (this.listenersLock)
/* 225:    */     {
/* 226:478 */       if (this.disabledListening) {
/* 227:479 */         throw new IllegalStateException();
/* 228:    */       }
/* 229:481 */       this.listeners = Kit.removeListener(this.listeners, listener);
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   final void disableContextListening()
/* 234:    */   {
/* 235:491 */     checkNotSealed();
/* 236:492 */     synchronized (this.listenersLock)
/* 237:    */     {
/* 238:493 */       this.disabledListening = true;
/* 239:494 */       this.listeners = null;
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public final boolean isSealed()
/* 244:    */   {
/* 245:504 */     return this.sealed;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public final void seal()
/* 249:    */   {
/* 250:514 */     checkNotSealed();
/* 251:515 */     this.sealed = true;
/* 252:    */   }
/* 253:    */   
/* 254:    */   protected final void checkNotSealed()
/* 255:    */   {
/* 256:520 */     if (this.sealed) {
/* 257:520 */       throw new IllegalStateException();
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   public final Object call(ContextAction action)
/* 262:    */   {
/* 263:538 */     return Context.call(this, action);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Context enterContext()
/* 267:    */   {
/* 268:582 */     return enterContext(null);
/* 269:    */   }
/* 270:    */   
/* 271:    */   /**
/* 272:    */    * @deprecated
/* 273:    */    */
/* 274:    */   public final Context enter()
/* 275:    */   {
/* 276:591 */     return enterContext(null);
/* 277:    */   }
/* 278:    */   
/* 279:    */   /**
/* 280:    */    * @deprecated
/* 281:    */    */
/* 282:    */   public final void exit() {}
/* 283:    */   
/* 284:    */   public final Context enterContext(Context cx)
/* 285:    */   {
/* 286:619 */     return Context.enter(cx, this);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public static abstract interface GlobalSetter
/* 290:    */   {
/* 291:    */     public abstract void setContextFactoryGlobal(ContextFactory paramContextFactory);
/* 292:    */     
/* 293:    */     public abstract ContextFactory getContextFactoryGlobal();
/* 294:    */   }
/* 295:    */   
/* 296:    */   public static abstract interface Listener
/* 297:    */   {
/* 298:    */     public abstract void contextCreated(Context paramContext);
/* 299:    */     
/* 300:    */     public abstract void contextReleased(Context paramContext);
/* 301:    */   }
/* 302:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ContextFactory
 * JD-Core Version:    0.7.0.1
 */