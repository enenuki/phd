/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ public class ManyToAnyCollectionElement
/*  4:   */   extends AbstractCollectionElement
/*  5:   */ {
/*  6:   */   ManyToAnyCollectionElement(AbstractPluralAttributeBinding binding)
/*  7:   */   {
/*  8:32 */     super(binding);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public CollectionElementNature getCollectionElementNature()
/* 12:   */   {
/* 13:37 */     return CollectionElementNature.MANY_TO_ANY;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.ManyToAnyCollectionElement
 * JD-Core Version:    0.7.0.1
 */