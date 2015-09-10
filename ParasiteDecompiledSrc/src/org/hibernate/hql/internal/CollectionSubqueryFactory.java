/*  1:   */ package org.hibernate.hql.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.QueryException;
/*  6:   */ import org.hibernate.engine.internal.JoinSequence;
/*  7:   */ import org.hibernate.internal.util.StringHelper;
/*  8:   */ import org.hibernate.sql.JoinFragment;
/*  9:   */ 
/* 10:   */ public final class CollectionSubqueryFactory
/* 11:   */ {
/* 12:   */   public static String createCollectionSubquery(JoinSequence joinSequence, Map enabledFilters, String[] columns)
/* 13:   */   {
/* 14:   */     try
/* 15:   */     {
/* 16:54 */       JoinFragment join = joinSequence.toJoinFragment(enabledFilters, true);
/* 17:55 */       return "select " + StringHelper.join(", ", columns) + " from " + join.toFromFragmentString().substring(2) + " where " + join.toWhereFragmentString().substring(5);
/* 18:   */     }
/* 19:   */     catch (MappingException me)
/* 20:   */     {
/* 21:64 */       throw new QueryException(me);
/* 22:   */     }
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.CollectionSubqueryFactory
 * JD-Core Version:    0.7.0.1
 */