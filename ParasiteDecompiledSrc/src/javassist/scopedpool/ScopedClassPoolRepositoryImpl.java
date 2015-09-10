/*   1:    */ package javassist.scopedpool;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.WeakHashMap;
/*   9:    */ import javassist.ClassPool;
/*  10:    */ import javassist.LoaderClassPath;
/*  11:    */ 
/*  12:    */ public class ScopedClassPoolRepositoryImpl
/*  13:    */   implements ScopedClassPoolRepository
/*  14:    */ {
/*  15: 36 */   private static final ScopedClassPoolRepositoryImpl instance = new ScopedClassPoolRepositoryImpl();
/*  16: 39 */   private boolean prune = true;
/*  17:    */   boolean pruneWhenCached;
/*  18: 45 */   protected Map registeredCLs = Collections.synchronizedMap(new WeakHashMap());
/*  19:    */   protected ClassPool classpool;
/*  20: 52 */   protected ScopedClassPoolFactory factory = new ScopedClassPoolFactoryImpl();
/*  21:    */   
/*  22:    */   public static ScopedClassPoolRepository getInstance()
/*  23:    */   {
/*  24: 60 */     return instance;
/*  25:    */   }
/*  26:    */   
/*  27:    */   private ScopedClassPoolRepositoryImpl()
/*  28:    */   {
/*  29: 67 */     this.classpool = ClassPool.getDefault();
/*  30:    */     
/*  31: 69 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  32: 70 */     this.classpool.insertClassPath(new LoaderClassPath(cl));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isPrune()
/*  36:    */   {
/*  37: 79 */     return this.prune;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setPrune(boolean prune)
/*  41:    */   {
/*  42: 88 */     this.prune = prune;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ScopedClassPool createScopedClassPool(ClassLoader cl, ClassPool src)
/*  46:    */   {
/*  47: 99 */     return this.factory.create(cl, src, this);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ClassPool findClassPool(ClassLoader cl)
/*  51:    */   {
/*  52:103 */     if (cl == null) {
/*  53:104 */       return registerClassLoader(ClassLoader.getSystemClassLoader());
/*  54:    */     }
/*  55:106 */     return registerClassLoader(cl);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ClassPool registerClassLoader(ClassLoader ucl)
/*  59:    */   {
/*  60:116 */     synchronized (this.registeredCLs)
/*  61:    */     {
/*  62:122 */       if (this.registeredCLs.containsKey(ucl)) {
/*  63:123 */         return (ClassPool)this.registeredCLs.get(ucl);
/*  64:    */       }
/*  65:125 */       ScopedClassPool pool = createScopedClassPool(ucl, this.classpool);
/*  66:126 */       this.registeredCLs.put(ucl, pool);
/*  67:127 */       return pool;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Map getRegisteredCLs()
/*  72:    */   {
/*  73:135 */     clearUnregisteredClassLoaders();
/*  74:136 */     return this.registeredCLs;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void clearUnregisteredClassLoaders()
/*  78:    */   {
/*  79:144 */     ArrayList toUnregister = null;
/*  80:145 */     synchronized (this.registeredCLs)
/*  81:    */     {
/*  82:146 */       Iterator it = this.registeredCLs.values().iterator();
/*  83:147 */       while (it.hasNext())
/*  84:    */       {
/*  85:148 */         ScopedClassPool pool = (ScopedClassPool)it.next();
/*  86:149 */         if (pool.isUnloadedClassLoader())
/*  87:    */         {
/*  88:150 */           it.remove();
/*  89:151 */           ClassLoader cl = pool.getClassLoader();
/*  90:152 */           if (cl != null)
/*  91:    */           {
/*  92:153 */             if (toUnregister == null) {
/*  93:154 */               toUnregister = new ArrayList();
/*  94:    */             }
/*  95:156 */             toUnregister.add(cl);
/*  96:    */           }
/*  97:    */         }
/*  98:    */       }
/*  99:160 */       if (toUnregister != null) {
/* 100:161 */         for (int i = 0; i < toUnregister.size(); i++) {
/* 101:162 */           unregisterClassLoader((ClassLoader)toUnregister.get(i));
/* 102:    */         }
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void unregisterClassLoader(ClassLoader cl)
/* 108:    */   {
/* 109:169 */     synchronized (this.registeredCLs)
/* 110:    */     {
/* 111:170 */       ScopedClassPool pool = (ScopedClassPool)this.registeredCLs.remove(cl);
/* 112:171 */       if (pool != null) {
/* 113:172 */         pool.close();
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void insertDelegate(ScopedClassPoolRepository delegate) {}
/* 119:    */   
/* 120:    */   public void setClassPoolFactory(ScopedClassPoolFactory factory)
/* 121:    */   {
/* 122:181 */     this.factory = factory;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public ScopedClassPoolFactory getClassPoolFactory()
/* 126:    */   {
/* 127:185 */     return this.factory;
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.scopedpool.ScopedClassPoolRepositoryImpl
 * JD-Core Version:    0.7.0.1
 */