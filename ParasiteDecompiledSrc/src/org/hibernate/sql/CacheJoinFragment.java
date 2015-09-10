/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import org.hibernate.AssertionFailure;
/*  4:   */ 
/*  5:   */ public class CacheJoinFragment
/*  6:   */   extends ANSIJoinFragment
/*  7:   */ {
/*  8:   */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType, String on)
/*  9:   */   {
/* 10:38 */     if (joinType == JoinType.FULL_JOIN) {
/* 11:39 */       throw new AssertionFailure("Cache does not support full outer joins");
/* 12:   */     }
/* 13:41 */     super.addJoin(tableName, alias, fkColumns, pkColumns, joinType, on);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.CacheJoinFragment
 * JD-Core Version:    0.7.0.1
 */