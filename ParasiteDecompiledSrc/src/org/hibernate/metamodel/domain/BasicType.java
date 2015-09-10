/*  1:   */ package org.hibernate.metamodel.domain;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.Value;
/*  4:   */ 
/*  5:   */ public class BasicType
/*  6:   */   implements Type
/*  7:   */ {
/*  8:   */   private final String name;
/*  9:   */   private final Value<Class<?>> classReference;
/* 10:   */   
/* 11:   */   public BasicType(String name, Value<Class<?>> classReference)
/* 12:   */   {
/* 13:38 */     this.name = name;
/* 14:39 */     this.classReference = classReference;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:44 */     return this.name;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getClassName()
/* 23:   */   {
/* 24:49 */     return this.name;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Class<?> getClassReference()
/* 28:   */   {
/* 29:54 */     return (Class)this.classReference.getValue();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Value<Class<?>> getClassReferenceUnresolved()
/* 33:   */   {
/* 34:59 */     return this.classReference;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean isAssociation()
/* 38:   */   {
/* 39:64 */     return false;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isComponent()
/* 43:   */   {
/* 44:69 */     return false;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.BasicType
 * JD-Core Version:    0.7.0.1
 */