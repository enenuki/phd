/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ public class OneToManyCollectionElement
/*  4:   */   extends AbstractCollectionElement
/*  5:   */ {
/*  6:   */   OneToManyCollectionElement(AbstractPluralAttributeBinding binding)
/*  7:   */   {
/*  8:33 */     super(binding);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public CollectionElementNature getCollectionElementNature()
/* 12:   */   {
/* 13:38 */     return CollectionElementNature.ONE_TO_MANY;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.OneToManyCollectionElement
 * JD-Core Version:    0.7.0.1
 */