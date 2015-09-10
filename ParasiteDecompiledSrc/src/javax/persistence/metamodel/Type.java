/*  1:   */ package javax.persistence.metamodel;
/*  2:   */ 
/*  3:   */ public abstract interface Type<X>
/*  4:   */ {
/*  5:   */   public abstract PersistenceType getPersistenceType();
/*  6:   */   
/*  7:   */   public abstract Class<X> getJavaType();
/*  8:   */   
/*  9:   */   public static enum PersistenceType
/* 10:   */   {
/* 11:32 */     ENTITY,  EMBEDDABLE,  MAPPED_SUPERCLASS,  BASIC;
/* 12:   */     
/* 13:   */     private PersistenceType() {}
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.Type
 * JD-Core Version:    0.7.0.1
 */