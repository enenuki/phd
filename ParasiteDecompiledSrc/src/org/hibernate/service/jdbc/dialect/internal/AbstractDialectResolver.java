/*  1:   */ package org.hibernate.service.jdbc.dialect.internal;
/*  2:   */ 
/*  3:   */ import java.sql.DatabaseMetaData;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.JDBCException;
/*  6:   */ import org.hibernate.dialect.Dialect;
/*  7:   */ import org.hibernate.dialect.resolver.BasicSQLExceptionConverter;
/*  8:   */ import org.hibernate.exception.JDBCConnectionException;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.hibernate.service.jdbc.dialect.spi.DialectResolver;
/* 11:   */ import org.jboss.logging.Logger;
/* 12:   */ 
/* 13:   */ public abstract class AbstractDialectResolver
/* 14:   */   implements DialectResolver
/* 15:   */ {
/* 16:46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractDialectResolver.class.getName());
/* 17:   */   
/* 18:   */   public final Dialect resolveDialect(DatabaseMetaData metaData)
/* 19:   */   {
/* 20:   */     try
/* 21:   */     {
/* 22:57 */       return resolveDialectInternal(metaData);
/* 23:   */     }
/* 24:   */     catch (SQLException sqlException)
/* 25:   */     {
/* 26:60 */       JDBCException jdbcException = BasicSQLExceptionConverter.INSTANCE.convert(sqlException);
/* 27:61 */       if ((jdbcException instanceof JDBCConnectionException)) {
/* 28:61 */         throw jdbcException;
/* 29:   */       }
/* 30:62 */       LOG.warnf("%s : %s", BasicSQLExceptionConverter.MSG, sqlException.getMessage());
/* 31:63 */       return null;
/* 32:   */     }
/* 33:   */     catch (Throwable t)
/* 34:   */     {
/* 35:66 */       LOG.unableToExecuteResolver(this, t.getMessage());
/* 36:   */     }
/* 37:67 */     return null;
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected abstract Dialect resolveDialectInternal(DatabaseMetaData paramDatabaseMetaData)
/* 41:   */     throws SQLException;
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.internal.AbstractDialectResolver
 * JD-Core Version:    0.7.0.1
 */