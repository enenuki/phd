/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ public class BasicCollectionElement
/*  4:   */   extends AbstractCollectionElement
/*  5:   */ {
/*  6:31 */   private final HibernateTypeDescriptor hibernateTypeDescriptor = new HibernateTypeDescriptor();
/*  7:   */   
/*  8:   */   public BasicCollectionElement(AbstractPluralAttributeBinding binding)
/*  9:   */   {
/* 10:34 */     super(binding);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public CollectionElementNature getCollectionElementNature()
/* 14:   */   {
/* 15:38 */     return CollectionElementNature.BASIC;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public HibernateTypeDescriptor getHibernateTypeDescriptor()
/* 19:   */   {
/* 20:42 */     return this.hibernateTypeDescriptor;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.BasicCollectionElement
 * JD-Core Version:    0.7.0.1
 */