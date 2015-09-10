/*  1:   */ package org.hibernate.hql.internal.classic;
/*  2:   */ 
/*  3:   */ public class HavingParser
/*  4:   */   extends WhereParser
/*  5:   */ {
/*  6:   */   void appendToken(QueryTranslatorImpl q, String token)
/*  7:   */   {
/*  8:35 */     q.appendHavingToken(token);
/*  9:   */   }
/* 10:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.HavingParser
 * JD-Core Version:    0.7.0.1
 */