/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.relational.Value;
/*  4:   */ 
/*  5:   */ public abstract class AbstractCollectionElement
/*  6:   */ {
/*  7:   */   private final AbstractPluralAttributeBinding collectionBinding;
/*  8:   */   private Value elementValue;
/*  9:   */   
/* 10:   */   AbstractCollectionElement(AbstractPluralAttributeBinding collectionBinding)
/* 11:   */   {
/* 12:39 */     this.collectionBinding = collectionBinding;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public abstract CollectionElementNature getCollectionElementNature();
/* 16:   */   
/* 17:   */   public AbstractPluralAttributeBinding getCollectionBinding()
/* 18:   */   {
/* 19:45 */     return this.collectionBinding;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Value getElementValue()
/* 23:   */   {
/* 24:49 */     return this.elementValue;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.AbstractCollectionElement
 * JD-Core Version:    0.7.0.1
 */