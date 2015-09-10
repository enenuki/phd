/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.transform.AliasToEntityMapResultTransformer;
/*  4:   */ import org.hibernate.transform.DistinctRootEntityResultTransformer;
/*  5:   */ import org.hibernate.transform.PassThroughResultTransformer;
/*  6:   */ import org.hibernate.transform.ResultTransformer;
/*  7:   */ import org.hibernate.transform.RootEntityResultTransformer;
/*  8:   */ 
/*  9:   */ public abstract interface CriteriaSpecification
/* 10:   */ {
/* 11:   */   public static final String ROOT_ALIAS = "this";
/* 12:46 */   public static final ResultTransformer ALIAS_TO_ENTITY_MAP = AliasToEntityMapResultTransformer.INSTANCE;
/* 13:51 */   public static final ResultTransformer ROOT_ENTITY = RootEntityResultTransformer.INSTANCE;
/* 14:56 */   public static final ResultTransformer DISTINCT_ROOT_ENTITY = DistinctRootEntityResultTransformer.INSTANCE;
/* 15:61 */   public static final ResultTransformer PROJECTION = PassThroughResultTransformer.INSTANCE;
/* 16:   */   @Deprecated
/* 17:   */   public static final int INNER_JOIN = 0;
/* 18:   */   @Deprecated
/* 19:   */   public static final int FULL_JOIN = 4;
/* 20:   */   @Deprecated
/* 21:   */   public static final int LEFT_JOIN = 1;
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.CriteriaSpecification
 * JD-Core Version:    0.7.0.1
 */