/*  1:   */ package org.hibernate.metamodel.domain;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.Value;
/*  4:   */ 
/*  5:   */ public class Entity
/*  6:   */   extends AbstractAttributeContainer
/*  7:   */ {
/*  8:   */   public Entity(String entityName, String className, Value<Class<?>> classReference, Hierarchical superType)
/*  9:   */   {
/* 10:44 */     super(entityName, className, classReference, superType);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean isAssociation()
/* 14:   */   {
/* 15:49 */     return true;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean isComponent()
/* 19:   */   {
/* 20:54 */     return false;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.Entity
 * JD-Core Version:    0.7.0.1
 */