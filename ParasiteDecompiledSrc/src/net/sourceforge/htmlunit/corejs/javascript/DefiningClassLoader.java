/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ public class DefiningClassLoader
/*  4:   */   extends ClassLoader
/*  5:   */   implements GeneratedClassLoader
/*  6:   */ {
/*  7:   */   private final ClassLoader parentLoader;
/*  8:   */   
/*  9:   */   public DefiningClassLoader()
/* 10:   */   {
/* 11:51 */     this.parentLoader = getClass().getClassLoader();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public DefiningClassLoader(ClassLoader parentLoader)
/* 15:   */   {
/* 16:55 */     this.parentLoader = parentLoader;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Class<?> defineClass(String name, byte[] data)
/* 20:   */   {
/* 21:62 */     return super.defineClass(name, data, 0, data.length, SecurityUtilities.getProtectionDomain(getClass()));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void linkClass(Class<?> cl)
/* 25:   */   {
/* 26:67 */     resolveClass(cl);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Class<?> loadClass(String name, boolean resolve)
/* 30:   */     throws ClassNotFoundException
/* 31:   */   {
/* 32:74 */     Class<?> cl = findLoadedClass(name);
/* 33:75 */     if (cl == null) {
/* 34:76 */       if (this.parentLoader != null) {
/* 35:77 */         cl = this.parentLoader.loadClass(name);
/* 36:   */       } else {
/* 37:79 */         cl = findSystemClass(name);
/* 38:   */       }
/* 39:   */     }
/* 40:82 */     if (resolve) {
/* 41:83 */       resolveClass(cl);
/* 42:   */     }
/* 43:85 */     return cl;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.DefiningClassLoader
 * JD-Core Version:    0.7.0.1
 */