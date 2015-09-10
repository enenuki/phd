/*  1:   */ package org.hibernate.hql.internal.ast;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.hql.spi.FilterTranslator;
/*  6:   */ import org.hibernate.hql.spi.QueryTranslator;
/*  7:   */ import org.hibernate.hql.spi.QueryTranslatorFactory;
/*  8:   */ import org.hibernate.internal.CoreMessageLogger;
/*  9:   */ import org.jboss.logging.Logger;
/* 10:   */ 
/* 11:   */ public class ASTQueryTranslatorFactory
/* 12:   */   implements QueryTranslatorFactory
/* 13:   */ {
/* 14:44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ASTQueryTranslatorFactory.class.getName());
/* 15:   */   
/* 16:   */   public ASTQueryTranslatorFactory()
/* 17:   */   {
/* 18:48 */     LOG.usingAstQueryTranslatorFactory();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public QueryTranslator createQueryTranslator(String queryIdentifier, String queryString, Map filters, SessionFactoryImplementor factory)
/* 22:   */   {
/* 23:59 */     return new QueryTranslatorImpl(queryIdentifier, queryString, filters, factory);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public FilterTranslator createFilterTranslator(String queryIdentifier, String queryString, Map filters, SessionFactoryImplementor factory)
/* 27:   */   {
/* 28:70 */     return new QueryTranslatorImpl(queryIdentifier, queryString, filters, factory);
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory
 * JD-Core Version:    0.7.0.1
 */