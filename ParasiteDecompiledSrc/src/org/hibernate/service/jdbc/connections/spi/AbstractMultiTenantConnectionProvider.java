/*  1:   */ package org.hibernate.service.jdbc.connections.spi;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.service.UnknownUnwrapTypeException;
/*  6:   */ 
/*  7:   */ public abstract class AbstractMultiTenantConnectionProvider
/*  8:   */   implements MultiTenantConnectionProvider
/*  9:   */ {
/* 10:   */   protected abstract ConnectionProvider getAnyConnectionProvider();
/* 11:   */   
/* 12:   */   protected abstract ConnectionProvider selectConnectionProvider(String paramString);
/* 13:   */   
/* 14:   */   public Connection getAnyConnection()
/* 15:   */     throws SQLException
/* 16:   */   {
/* 17:40 */     return getAnyConnectionProvider().getConnection();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void releaseAnyConnection(Connection connection)
/* 21:   */     throws SQLException
/* 22:   */   {
/* 23:45 */     getAnyConnectionProvider().closeConnection(connection);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Connection getConnection(String tenantIdentifier)
/* 27:   */     throws SQLException
/* 28:   */   {
/* 29:50 */     return selectConnectionProvider(tenantIdentifier).getConnection();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void releaseConnection(String tenantIdentifier, Connection connection)
/* 33:   */     throws SQLException
/* 34:   */   {
/* 35:55 */     selectConnectionProvider(tenantIdentifier).getConnection();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean supportsAggressiveRelease()
/* 39:   */   {
/* 40:60 */     return getAnyConnectionProvider().supportsAggressiveRelease();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean isUnwrappableAs(Class unwrapType)
/* 44:   */   {
/* 45:65 */     return (ConnectionProvider.class.equals(unwrapType)) || (MultiTenantConnectionProvider.class.equals(unwrapType)) || (AbstractMultiTenantConnectionProvider.class.isAssignableFrom(unwrapType));
/* 46:   */   }
/* 47:   */   
/* 48:   */   public <T> T unwrap(Class<T> unwrapType)
/* 49:   */   {
/* 50:73 */     if (isUnwrappableAs(unwrapType)) {
/* 51:74 */       return this;
/* 52:   */     }
/* 53:77 */     throw new UnknownUnwrapTypeException(unwrapType);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.connections.spi.AbstractMultiTenantConnectionProvider
 * JD-Core Version:    0.7.0.1
 */