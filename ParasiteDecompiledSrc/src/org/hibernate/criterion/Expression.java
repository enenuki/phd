/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ @Deprecated
/*  7:   */ public final class Expression
/*  8:   */   extends Restrictions
/*  9:   */ {
/* 10:   */   @Deprecated
/* 11:   */   public static Criterion sql(String sql, Object[] values, Type[] types)
/* 12:   */   {
/* 13:55 */     return new SQLCriterion(sql, values, types);
/* 14:   */   }
/* 15:   */   
/* 16:   */   @Deprecated
/* 17:   */   public static Criterion sql(String sql, Object value, Type type)
/* 18:   */   {
/* 19:70 */     return new SQLCriterion(sql, new Object[] { value }, new Type[] { type });
/* 20:   */   }
/* 21:   */   
/* 22:   */   @Deprecated
/* 23:   */   public static Criterion sql(String sql)
/* 24:   */   {
/* 25:82 */     return new SQLCriterion(sql, ArrayHelper.EMPTY_OBJECT_ARRAY, ArrayHelper.EMPTY_TYPE_ARRAY);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Expression
 * JD-Core Version:    0.7.0.1
 */