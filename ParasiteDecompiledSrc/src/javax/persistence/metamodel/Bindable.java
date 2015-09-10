/*  1:   */ package javax.persistence.metamodel;
/*  2:   */ 
/*  3:   */ public abstract interface Bindable<T>
/*  4:   */ {
/*  5:   */   public abstract BindableType getBindableType();
/*  6:   */   
/*  7:   */   public abstract Class<T> getBindableJavaType();
/*  8:   */   
/*  9:   */   public static enum BindableType
/* 10:   */   {
/* 11:32 */     SINGULAR_ATTRIBUTE,  PLURAL_ATTRIBUTE,  ENTITY_TYPE;
/* 12:   */     
/* 13:   */     private BindableType() {}
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.Bindable
 * JD-Core Version:    0.7.0.1
 */