/*  1:   */ package org.hibernate.metamodel.domain;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.Value;
/*  4:   */ 
/*  5:   */ public class Component
/*  6:   */   extends AbstractAttributeContainer
/*  7:   */ {
/*  8:   */   public Component(String name, String className, Value<Class<?>> classReference, Hierarchical superType)
/*  9:   */   {
/* 10:37 */     super(name, className, classReference, superType);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean isAssociation()
/* 14:   */   {
/* 15:42 */     return false;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean isComponent()
/* 19:   */   {
/* 20:47 */     return true;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getRoleBaseName()
/* 24:   */   {
/* 25:54 */     return getClassName();
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.Component
 * JD-Core Version:    0.7.0.1
 */