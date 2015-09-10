/*  1:   */ package org.hibernate.dialect.resolver;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ import org.hibernate.JDBCException;
/*  5:   */ import org.hibernate.exception.internal.SQLStateConverter;
/*  6:   */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  7:   */ import org.hibernate.internal.CoreMessageLogger;
/*  8:   */ import org.jboss.logging.Logger;
/*  9:   */ 
/* 10:   */ public class BasicSQLExceptionConverter
/* 11:   */ {
/* 12:41 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BasicSQLExceptionConverter.class.getName());
/* 13:42 */   public static final BasicSQLExceptionConverter INSTANCE = new BasicSQLExceptionConverter();
/* 14:43 */   public static final String MSG = LOG.unableToQueryDatabaseMetadata();
/* 15:45 */   private static final SQLStateConverter CONVERTER = new SQLStateConverter(new ConstraintNameExtracter(null));
/* 16:   */   
/* 17:   */   public JDBCException convert(SQLException sqlException)
/* 18:   */   {
/* 19:54 */     return CONVERTER.convert(sqlException, MSG, null);
/* 20:   */   }
/* 21:   */   
/* 22:   */   private static class ConstraintNameExtracter
/* 23:   */     implements ViolatedConstraintNameExtracter
/* 24:   */   {
/* 25:   */     public String extractConstraintName(SQLException sqle)
/* 26:   */     {
/* 27:62 */       return "???";
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.resolver.BasicSQLExceptionConverter
 * JD-Core Version:    0.7.0.1
 */