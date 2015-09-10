/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ public final class Transformers
/*  4:   */ {
/*  5:35 */   public static final AliasToEntityMapResultTransformer ALIAS_TO_ENTITY_MAP = AliasToEntityMapResultTransformer.INSTANCE;
/*  6:41 */   public static final ToListResultTransformer TO_LIST = ToListResultTransformer.INSTANCE;
/*  7:   */   
/*  8:   */   public static ResultTransformer aliasToBean(Class target)
/*  9:   */   {
/* 10:48 */     return new AliasToBeanResultTransformer(target);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.Transformers
 * JD-Core Version:    0.7.0.1
 */