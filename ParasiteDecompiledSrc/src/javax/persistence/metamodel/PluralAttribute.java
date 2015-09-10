/*  1:   */ package javax.persistence.metamodel;
/*  2:   */ 
/*  3:   */ public abstract interface PluralAttribute<X, C, E>
/*  4:   */   extends Attribute<X, C>, Bindable<E>
/*  5:   */ {
/*  6:   */   public abstract CollectionType getCollectionType();
/*  7:   */   
/*  8:   */   public abstract Type<E> getElementType();
/*  9:   */   
/* 10:   */   public static enum CollectionType
/* 11:   */   {
/* 12:34 */     COLLECTION,  SET,  LIST,  MAP;
/* 13:   */     
/* 14:   */     private CollectionType() {}
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.PluralAttribute
 * JD-Core Version:    0.7.0.1
 */