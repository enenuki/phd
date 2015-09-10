/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ public class CompositeCollectionElement
/*  4:   */   extends AbstractCollectionElement
/*  5:   */ {
/*  6:   */   public CompositeCollectionElement(AbstractPluralAttributeBinding binding)
/*  7:   */   {
/*  8:32 */     super(binding);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public CollectionElementNature getCollectionElementNature()
/* 12:   */   {
/* 13:37 */     return CollectionElementNature.COMPOSITE;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.CompositeCollectionElement
 * JD-Core Version:    0.7.0.1
 */