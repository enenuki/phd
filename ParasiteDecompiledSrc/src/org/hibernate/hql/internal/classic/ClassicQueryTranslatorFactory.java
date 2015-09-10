/*  1:   */ package org.hibernate.hql.internal.classic;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.hql.spi.FilterTranslator;
/*  6:   */ import org.hibernate.hql.spi.QueryTranslator;
/*  7:   */ import org.hibernate.hql.spi.QueryTranslatorFactory;
/*  8:   */ 
/*  9:   */ public class ClassicQueryTranslatorFactory
/* 10:   */   implements QueryTranslatorFactory
/* 11:   */ {
/* 12:   */   public QueryTranslator createQueryTranslator(String queryIdentifier, String queryString, Map filters, SessionFactoryImplementor factory)
/* 13:   */   {
/* 14:49 */     return new QueryTranslatorImpl(queryIdentifier, queryString, filters, factory);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public FilterTranslator createFilterTranslator(String queryIdentifier, String queryString, Map filters, SessionFactoryImplementor factory)
/* 18:   */   {
/* 19:60 */     return new QueryTranslatorImpl(queryIdentifier, queryString, filters, factory);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.ClassicQueryTranslatorFactory
 * JD-Core Version:    0.7.0.1
 */