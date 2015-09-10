/*  1:   */ package org.hibernate.service.jdbc.dialect.internal;
/*  2:   */ 
/*  3:   */ import java.sql.DatabaseMetaData;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Arrays;
/*  6:   */ import java.util.List;
/*  7:   */ import org.hibernate.dialect.Dialect;
/*  8:   */ import org.hibernate.exception.JDBCConnectionException;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.hibernate.service.jdbc.dialect.spi.DialectResolver;
/* 11:   */ import org.jboss.logging.Logger;
/* 12:   */ 
/* 13:   */ public class DialectResolverSet
/* 14:   */   implements DialectResolver
/* 15:   */ {
/* 16:46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DialectResolverSet.class.getName());
/* 17:   */   private List<DialectResolver> resolvers;
/* 18:   */   
/* 19:   */   public DialectResolverSet()
/* 20:   */   {
/* 21:51 */     this(new ArrayList());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public DialectResolverSet(List<DialectResolver> resolvers)
/* 25:   */   {
/* 26:55 */     this.resolvers = resolvers;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public DialectResolverSet(DialectResolver... resolvers)
/* 30:   */   {
/* 31:59 */     this(Arrays.asList(resolvers));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Dialect resolveDialect(DatabaseMetaData metaData)
/* 35:   */     throws JDBCConnectionException
/* 36:   */   {
/* 37:63 */     for (DialectResolver resolver : this.resolvers) {
/* 38:   */       try
/* 39:   */       {
/* 40:65 */         Dialect dialect = resolver.resolveDialect(metaData);
/* 41:66 */         if (dialect != null) {
/* 42:67 */           return dialect;
/* 43:   */         }
/* 44:   */       }
/* 45:   */       catch (JDBCConnectionException e)
/* 46:   */       {
/* 47:71 */         throw e;
/* 48:   */       }
/* 49:   */       catch (Exception e)
/* 50:   */       {
/* 51:74 */         LOG.exceptionInSubResolver(e.getMessage());
/* 52:   */       }
/* 53:   */     }
/* 54:77 */     return null;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void addResolver(DialectResolver resolver)
/* 58:   */   {
/* 59:87 */     this.resolvers.add(resolver);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void addResolverAtFirst(DialectResolver resolver)
/* 63:   */   {
/* 64:97 */     this.resolvers.add(0, resolver);
/* 65:   */   }
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.internal.DialectResolverSet
 * JD-Core Version:    0.7.0.1
 */