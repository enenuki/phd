/*  1:   */ package org.hibernate.service.jdbc.dialect.internal;
/*  2:   */ 
/*  3:   */ import java.sql.DatabaseMetaData;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.dialect.Dialect;
/*  7:   */ 
/*  8:   */ public class BasicDialectResolver
/*  9:   */   extends AbstractDialectResolver
/* 10:   */ {
/* 11:   */   public static final int VERSION_INSENSITIVE_VERSION = -9999;
/* 12:   */   private final String matchingName;
/* 13:   */   private final int matchingVersion;
/* 14:   */   private final Class dialectClass;
/* 15:   */   
/* 16:   */   public BasicDialectResolver(String matchingName, Class dialectClass)
/* 17:   */   {
/* 18:47 */     this(matchingName, -9999, dialectClass);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public BasicDialectResolver(String matchingName, int matchingVersion, Class dialectClass)
/* 22:   */   {
/* 23:51 */     this.matchingName = matchingName;
/* 24:52 */     this.matchingVersion = matchingVersion;
/* 25:53 */     this.dialectClass = dialectClass;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected final Dialect resolveDialectInternal(DatabaseMetaData metaData)
/* 29:   */     throws SQLException
/* 30:   */   {
/* 31:57 */     String databaseName = metaData.getDatabaseProductName();
/* 32:58 */     int databaseMajorVersion = metaData.getDatabaseMajorVersion();
/* 33:60 */     if ((this.matchingName.equalsIgnoreCase(databaseName)) && ((this.matchingVersion == -9999) || (this.matchingVersion == databaseMajorVersion))) {
/* 34:   */       try
/* 35:   */       {
/* 36:63 */         return (Dialect)this.dialectClass.newInstance();
/* 37:   */       }
/* 38:   */       catch (HibernateException e)
/* 39:   */       {
/* 40:67 */         throw e;
/* 41:   */       }
/* 42:   */       catch (Throwable t)
/* 43:   */       {
/* 44:70 */         throw new HibernateException("Could not instantiate specified Dialect class [" + this.dialectClass.getName() + "]", t);
/* 45:   */       }
/* 46:   */     }
/* 47:77 */     return null;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.internal.BasicDialectResolver
 * JD-Core Version:    0.7.0.1
 */