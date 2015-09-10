/*  1:   */ package org.hibernate.metamodel.domain;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.Value;
/*  4:   */ import org.hibernate.internal.util.Value.DeferredInitializer;
/*  5:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  6:   */ 
/*  7:   */ public class JavaType
/*  8:   */ {
/*  9:   */   private final String name;
/* 10:   */   private final Value<Class<?>> classReference;
/* 11:   */   
/* 12:   */   public JavaType(final String name, final ClassLoaderService classLoaderService)
/* 13:   */   {
/* 14:40 */     this.name = name;
/* 15:41 */     this.classReference = new Value(new Value.DeferredInitializer()
/* 16:   */     {
/* 17:   */       public Class<?> initialize()
/* 18:   */       {
/* 19:45 */         return classLoaderService.classForName(name);
/* 20:   */       }
/* 21:   */     });
/* 22:   */   }
/* 23:   */   
/* 24:   */   public JavaType(Class<?> theClass)
/* 25:   */   {
/* 26:52 */     this.name = theClass.getName();
/* 27:53 */     this.classReference = new Value(theClass);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getName()
/* 31:   */   {
/* 32:57 */     return this.name;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Class<?> getClassReference()
/* 36:   */   {
/* 37:61 */     return (Class)this.classReference.getValue();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String toString()
/* 41:   */   {
/* 42:66 */     return super.toString() + "[name=" + this.name + "]";
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.JavaType
 * JD-Core Version:    0.7.0.1
 */