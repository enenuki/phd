/*  1:   */ package org.dom4j.util;
/*  2:   */ 
/*  3:   */ import java.lang.ref.WeakReference;
/*  4:   */ 
/*  5:   */ public class PerThreadSingleton
/*  6:   */   implements SingletonStrategy
/*  7:   */ {
/*  8:25 */   private String singletonClassName = null;
/*  9:27 */   private ThreadLocal perThreadCache = new ThreadLocal();
/* 10:   */   
/* 11:   */   public void reset()
/* 12:   */   {
/* 13:33 */     this.perThreadCache = new ThreadLocal();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Object instance()
/* 17:   */   {
/* 18:37 */     Object singletonInstancePerThread = null;
/* 19:   */     
/* 20:39 */     WeakReference ref = (WeakReference)this.perThreadCache.get();
/* 21:42 */     if ((ref == null) || (ref.get() == null))
/* 22:   */     {
/* 23:43 */       Class clazz = null;
/* 24:   */       try
/* 25:   */       {
/* 26:45 */         clazz = Thread.currentThread().getContextClassLoader().loadClass(this.singletonClassName);
/* 27:   */         
/* 28:47 */         singletonInstancePerThread = clazz.newInstance();
/* 29:   */       }
/* 30:   */       catch (Exception ignore)
/* 31:   */       {
/* 32:   */         try
/* 33:   */         {
/* 34:50 */           clazz = Class.forName(this.singletonClassName);
/* 35:51 */           singletonInstancePerThread = clazz.newInstance();
/* 36:   */         }
/* 37:   */         catch (Exception ignore2) {}
/* 38:   */       }
/* 39:55 */       this.perThreadCache.set(new WeakReference(singletonInstancePerThread));
/* 40:   */     }
/* 41:   */     else
/* 42:   */     {
/* 43:57 */       singletonInstancePerThread = ref.get();
/* 44:   */     }
/* 45:59 */     return singletonInstancePerThread;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setSingletonClassName(String singletonClassName)
/* 49:   */   {
/* 50:63 */     this.singletonClassName = singletonClassName;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.PerThreadSingleton
 * JD-Core Version:    0.7.0.1
 */