/*  1:   */ package org.dom4j.util;
/*  2:   */ 
/*  3:   */ public class SimpleSingleton
/*  4:   */   implements SingletonStrategy
/*  5:   */ {
/*  6:23 */   private String singletonClassName = null;
/*  7:25 */   private Object singletonInstance = null;
/*  8:   */   
/*  9:   */   public Object instance()
/* 10:   */   {
/* 11:31 */     return this.singletonInstance;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void reset()
/* 15:   */   {
/* 16:35 */     if (this.singletonClassName != null)
/* 17:   */     {
/* 18:36 */       Class clazz = null;
/* 19:   */       try
/* 20:   */       {
/* 21:38 */         clazz = Thread.currentThread().getContextClassLoader().loadClass(this.singletonClassName);
/* 22:   */         
/* 23:40 */         this.singletonInstance = clazz.newInstance();
/* 24:   */       }
/* 25:   */       catch (Exception ignore)
/* 26:   */       {
/* 27:   */         try
/* 28:   */         {
/* 29:43 */           clazz = Class.forName(this.singletonClassName);
/* 30:44 */           this.singletonInstance = clazz.newInstance();
/* 31:   */         }
/* 32:   */         catch (Exception ignore2) {}
/* 33:   */       }
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setSingletonClassName(String singletonClassName)
/* 38:   */   {
/* 39:53 */     this.singletonClassName = singletonClassName;
/* 40:54 */     reset();
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.SimpleSingleton
 * JD-Core Version:    0.7.0.1
 */