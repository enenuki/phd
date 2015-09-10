/*  1:   */ package org.hibernate.hql.internal.classic;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public class FromPathExpressionParser
/*  7:   */   extends PathExpressionParser
/*  8:   */ {
/*  9:   */   public void end(QueryTranslatorImpl q)
/* 10:   */     throws QueryException
/* 11:   */   {
/* 12:33 */     if (!isCollectionValued())
/* 13:   */     {
/* 14:34 */       Type type = getPropertyType();
/* 15:35 */       if (type.isEntityType())
/* 16:   */       {
/* 17:37 */         token(".", q);
/* 18:38 */         token(null, q);
/* 19:   */       }
/* 20:40 */       else if (type.isCollectionType())
/* 21:   */       {
/* 22:42 */         token(".", q);
/* 23:43 */         token("elements", q);
/* 24:   */       }
/* 25:   */     }
/* 26:46 */     super.end(q);
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected void setExpectingCollectionIndex()
/* 30:   */     throws QueryException
/* 31:   */   {
/* 32:50 */     throw new QueryException("illegal syntax near collection-valued path expression in from: " + getCollectionName());
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.FromPathExpressionParser
 * JD-Core Version:    0.7.0.1
 */