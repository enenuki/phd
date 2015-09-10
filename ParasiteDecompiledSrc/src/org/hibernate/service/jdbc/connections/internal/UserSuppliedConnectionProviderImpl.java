/*  1:   */ package org.hibernate.service.jdbc.connections.internal;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.service.UnknownUnwrapTypeException;
/*  6:   */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  7:   */ 
/*  8:   */ public class UserSuppliedConnectionProviderImpl
/*  9:   */   implements ConnectionProvider
/* 10:   */ {
/* 11:   */   public boolean isUnwrappableAs(Class unwrapType)
/* 12:   */   {
/* 13:42 */     return (ConnectionProvider.class.equals(unwrapType)) || (UserSuppliedConnectionProviderImpl.class.isAssignableFrom(unwrapType));
/* 14:   */   }
/* 15:   */   
/* 16:   */   public <T> T unwrap(Class<T> unwrapType)
/* 17:   */   {
/* 18:49 */     if ((ConnectionProvider.class.equals(unwrapType)) || (UserSuppliedConnectionProviderImpl.class.isAssignableFrom(unwrapType))) {
/* 19:51 */       return this;
/* 20:   */     }
/* 21:54 */     throw new UnknownUnwrapTypeException(unwrapType);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Connection getConnection()
/* 25:   */     throws SQLException
/* 26:   */   {
/* 27:62 */     throw new UnsupportedOperationException("The application must supply JDBC connections");
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void closeConnection(Connection conn)
/* 31:   */     throws SQLException
/* 32:   */   {
/* 33:69 */     throw new UnsupportedOperationException("The application must supply JDBC connections");
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean supportsAggressiveRelease()
/* 37:   */   {
/* 38:76 */     return false;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.connections.internal.UserSuppliedConnectionProviderImpl
 * JD-Core Version:    0.7.0.1
 */