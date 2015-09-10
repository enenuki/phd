/*  1:   */ package org.hibernate.hql.internal.classic;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ 
/*  5:   */ public class SelectPathExpressionParser
/*  6:   */   extends PathExpressionParser
/*  7:   */ {
/*  8:   */   public void end(QueryTranslatorImpl q)
/*  9:   */     throws QueryException
/* 10:   */   {
/* 11:31 */     if ((getCurrentProperty() != null) && (!q.isShallowQuery()))
/* 12:   */     {
/* 13:33 */       token(".", q);
/* 14:34 */       token(null, q);
/* 15:   */     }
/* 16:36 */     super.end(q);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected void setExpectingCollectionIndex()
/* 20:   */     throws QueryException
/* 21:   */   {
/* 22:40 */     throw new QueryException("illegal syntax near collection-valued path expression in select: " + getCollectionName());
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getSelectName()
/* 26:   */   {
/* 27:44 */     return getCurrentName();
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.SelectPathExpressionParser
 * JD-Core Version:    0.7.0.1
 */