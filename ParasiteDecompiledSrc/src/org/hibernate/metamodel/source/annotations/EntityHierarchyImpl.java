/*  1:   */ package org.hibernate.metamodel.source.annotations;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.binding.InheritanceType;
/*  4:   */ import org.hibernate.metamodel.source.binder.EntityHierarchy;
/*  5:   */ import org.hibernate.metamodel.source.binder.RootEntitySource;
/*  6:   */ 
/*  7:   */ public class EntityHierarchyImpl
/*  8:   */   implements EntityHierarchy
/*  9:   */ {
/* 10:   */   private final RootEntitySource rootEntitySource;
/* 11:   */   private final InheritanceType inheritanceType;
/* 12:   */   
/* 13:   */   public EntityHierarchyImpl(RootEntitySource source, InheritanceType inheritanceType)
/* 14:   */   {
/* 15:38 */     this.rootEntitySource = source;
/* 16:39 */     this.inheritanceType = inheritanceType;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public InheritanceType getHierarchyInheritanceType()
/* 20:   */   {
/* 21:44 */     return this.inheritanceType;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public RootEntitySource getRootEntitySource()
/* 25:   */   {
/* 26:49 */     return this.rootEntitySource;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.EntityHierarchyImpl
 * JD-Core Version:    0.7.0.1
 */