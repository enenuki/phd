/*  1:   */ package org.hibernate.metamodel.source.hbm;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.binding.InheritanceType;
/*  4:   */ import org.hibernate.metamodel.source.MappingException;
/*  5:   */ import org.hibernate.metamodel.source.binder.EntityHierarchy;
/*  6:   */ import org.hibernate.metamodel.source.binder.RootEntitySource;
/*  7:   */ 
/*  8:   */ public class EntityHierarchyImpl
/*  9:   */   implements EntityHierarchy
/* 10:   */ {
/* 11:   */   private final RootEntitySourceImpl rootEntitySource;
/* 12:35 */   private InheritanceType hierarchyInheritanceType = InheritanceType.NO_INHERITANCE;
/* 13:   */   
/* 14:   */   public EntityHierarchyImpl(RootEntitySourceImpl rootEntitySource)
/* 15:   */   {
/* 16:38 */     this.rootEntitySource = rootEntitySource;
/* 17:39 */     this.rootEntitySource.injectHierarchy(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public InheritanceType getHierarchyInheritanceType()
/* 21:   */   {
/* 22:44 */     return this.hierarchyInheritanceType;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public RootEntitySource getRootEntitySource()
/* 26:   */   {
/* 27:49 */     return this.rootEntitySource;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void processSubclass(SubclassEntitySourceImpl subclassEntitySource)
/* 31:   */   {
/* 32:53 */     InheritanceType inheritanceType = Helper.interpretInheritanceType(subclassEntitySource.entityElement());
/* 33:54 */     if (this.hierarchyInheritanceType == InheritanceType.NO_INHERITANCE) {
/* 34:55 */       this.hierarchyInheritanceType = inheritanceType;
/* 35:57 */     } else if (this.hierarchyInheritanceType != inheritanceType) {
/* 36:58 */       throw new MappingException("Mixed inheritance strategies not supported", subclassEntitySource.getOrigin());
/* 37:   */     }
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.EntityHierarchyImpl
 * JD-Core Version:    0.7.0.1
 */