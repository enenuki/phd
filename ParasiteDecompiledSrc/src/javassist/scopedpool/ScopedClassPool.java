/*   1:    */ package javassist.scopedpool;
/*   2:    */ 
/*   3:    */ import java.lang.ref.WeakReference;
/*   4:    */ import java.security.ProtectionDomain;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import javassist.CannotCompileException;
/*  10:    */ import javassist.ClassPool;
/*  11:    */ import javassist.CtClass;
/*  12:    */ import javassist.LoaderClassPath;
/*  13:    */ import javassist.NotFoundException;
/*  14:    */ 
/*  15:    */ public class ScopedClassPool
/*  16:    */   extends ClassPool
/*  17:    */ {
/*  18:    */   protected ScopedClassPoolRepository repository;
/*  19:    */   protected WeakReference classLoader;
/*  20:    */   protected LoaderClassPath classPath;
/*  21: 43 */   protected SoftValueHashMap softcache = new SoftValueHashMap();
/*  22: 45 */   boolean isBootstrapCl = true;
/*  23:    */   
/*  24:    */   static
/*  25:    */   {
/*  26: 48 */     ClassPool.doPruning = false;
/*  27: 49 */     ClassPool.releaseUnmodifiedClassFile = false;
/*  28:    */   }
/*  29:    */   
/*  30:    */   /**
/*  31:    */    * @deprecated
/*  32:    */    */
/*  33:    */   protected ScopedClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
/*  34:    */   {
/*  35: 65 */     this(cl, src, repository, false);
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected ScopedClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository, boolean isTemp)
/*  39:    */   {
/*  40: 82 */     super(src);
/*  41: 83 */     this.repository = repository;
/*  42: 84 */     this.classLoader = new WeakReference(cl);
/*  43: 85 */     if (cl != null)
/*  44:    */     {
/*  45: 86 */       this.classPath = new LoaderClassPath(cl);
/*  46: 87 */       insertClassPath(this.classPath);
/*  47:    */     }
/*  48: 89 */     this.childFirstLookup = true;
/*  49: 90 */     if ((!isTemp) && (cl == null)) {
/*  50: 92 */       this.isBootstrapCl = true;
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ClassLoader getClassLoader()
/*  55:    */   {
/*  56:102 */     ClassLoader cl = getClassLoader0();
/*  57:103 */     if ((cl == null) && (!this.isBootstrapCl)) {
/*  58:105 */       throw new IllegalStateException("ClassLoader has been garbage collected");
/*  59:    */     }
/*  60:108 */     return cl;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected ClassLoader getClassLoader0()
/*  64:    */   {
/*  65:112 */     return (ClassLoader)this.classLoader.get();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void close()
/*  69:    */   {
/*  70:119 */     removeClassPath(this.classPath);
/*  71:120 */     this.classPath.close();
/*  72:121 */     this.classes.clear();
/*  73:122 */     this.softcache.clear();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public synchronized void flushClass(String classname)
/*  77:    */   {
/*  78:132 */     this.classes.remove(classname);
/*  79:133 */     this.softcache.remove(classname);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public synchronized void soften(CtClass clazz)
/*  83:    */   {
/*  84:143 */     if (this.repository.isPrune()) {
/*  85:144 */       clazz.prune();
/*  86:    */     }
/*  87:145 */     this.classes.remove(clazz.getName());
/*  88:146 */     this.softcache.put(clazz.getName(), clazz);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isUnloadedClassLoader()
/*  92:    */   {
/*  93:155 */     return false;
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected CtClass getCached(String classname)
/*  97:    */   {
/*  98:166 */     CtClass clazz = getCachedLocally(classname);
/*  99:167 */     if (clazz == null)
/* 100:    */     {
/* 101:168 */       boolean isLocal = false;
/* 102:    */       
/* 103:170 */       ClassLoader dcl = getClassLoader0();
/* 104:171 */       if (dcl != null)
/* 105:    */       {
/* 106:172 */         int lastIndex = classname.lastIndexOf('$');
/* 107:173 */         String classResourceName = null;
/* 108:174 */         if (lastIndex < 0) {
/* 109:175 */           classResourceName = classname.replaceAll("[\\.]", "/") + ".class";
/* 110:    */         } else {
/* 111:179 */           classResourceName = classname.substring(0, lastIndex).replaceAll("[\\.]", "/") + classname.substring(lastIndex) + ".class";
/* 112:    */         }
/* 113:184 */         isLocal = dcl.getResource(classResourceName) != null;
/* 114:    */       }
/* 115:187 */       if (!isLocal)
/* 116:    */       {
/* 117:188 */         Map registeredCLs = this.repository.getRegisteredCLs();
/* 118:189 */         synchronized (registeredCLs)
/* 119:    */         {
/* 120:190 */           Iterator it = registeredCLs.values().iterator();
/* 121:191 */           while (it.hasNext())
/* 122:    */           {
/* 123:192 */             ScopedClassPool pool = (ScopedClassPool)it.next();
/* 124:193 */             if (pool.isUnloadedClassLoader())
/* 125:    */             {
/* 126:194 */               this.repository.unregisterClassLoader(pool.getClassLoader());
/* 127:    */             }
/* 128:    */             else
/* 129:    */             {
/* 130:199 */               clazz = pool.getCachedLocally(classname);
/* 131:200 */               if (clazz != null) {
/* 132:201 */                 return clazz;
/* 133:    */               }
/* 134:    */             }
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:208 */     return clazz;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void cacheCtClass(String classname, CtClass c, boolean dynamic)
/* 143:    */   {
/* 144:222 */     if (dynamic)
/* 145:    */     {
/* 146:223 */       super.cacheCtClass(classname, c, dynamic);
/* 147:    */     }
/* 148:    */     else
/* 149:    */     {
/* 150:226 */       if (this.repository.isPrune()) {
/* 151:227 */         c.prune();
/* 152:    */       }
/* 153:228 */       this.softcache.put(classname, c);
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void lockInCache(CtClass c)
/* 158:    */   {
/* 159:239 */     super.cacheCtClass(c.getName(), c, false);
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected CtClass getCachedLocally(String classname)
/* 163:    */   {
/* 164:250 */     CtClass cached = (CtClass)this.classes.get(classname);
/* 165:251 */     if (cached != null) {
/* 166:252 */       return cached;
/* 167:    */     }
/* 168:253 */     synchronized (this.softcache)
/* 169:    */     {
/* 170:254 */       return (CtClass)this.softcache.get(classname);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public synchronized CtClass getLocally(String classname)
/* 175:    */     throws NotFoundException
/* 176:    */   {
/* 177:269 */     this.softcache.remove(classname);
/* 178:270 */     CtClass clazz = (CtClass)this.classes.get(classname);
/* 179:271 */     if (clazz == null)
/* 180:    */     {
/* 181:272 */       clazz = createCtClass(classname, true);
/* 182:273 */       if (clazz == null) {
/* 183:274 */         throw new NotFoundException(classname);
/* 184:    */       }
/* 185:275 */       super.cacheCtClass(classname, clazz, false);
/* 186:    */     }
/* 187:278 */     return clazz;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Class toClass(CtClass ct, ClassLoader loader, ProtectionDomain domain)
/* 191:    */     throws CannotCompileException
/* 192:    */   {
/* 193:305 */     lockInCache(ct);
/* 194:306 */     return super.toClass(ct, getClassLoader0(), domain);
/* 195:    */   }
/* 196:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.scopedpool.ScopedClassPool
 * JD-Core Version:    0.7.0.1
 */