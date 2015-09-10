/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.source.annotations.entity.EntityClass;
/*  4:   */ import org.hibernate.metamodel.source.binder.DiscriminatorSource;
/*  5:   */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  6:   */ 
/*  7:   */ public class DiscriminatorSourceImpl
/*  8:   */   implements DiscriminatorSource
/*  9:   */ {
/* 10:   */   private final EntityClass entityClass;
/* 11:   */   
/* 12:   */   public DiscriminatorSourceImpl(EntityClass entityClass)
/* 13:   */   {
/* 14:37 */     this.entityClass = entityClass;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isForced()
/* 18:   */   {
/* 19:42 */     return this.entityClass.isDiscriminatorForced();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean isInserted()
/* 23:   */   {
/* 24:47 */     return this.entityClass.isDiscriminatorIncludedInSql();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public RelationalValueSource getDiscriminatorRelationalValueSource()
/* 28:   */   {
/* 29:52 */     return this.entityClass.getDiscriminatorFormula() != null ? new DerivedValueSourceImpl(this.entityClass.getDiscriminatorFormula()) : new ColumnValuesSourceImpl(this.entityClass.getDiscriminatorColumnValues());
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getExplicitHibernateTypeName()
/* 33:   */   {
/* 34:59 */     return this.entityClass.getDiscriminatorType().getName();
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.DiscriminatorSourceImpl
 * JD-Core Version:    0.7.0.1
 */