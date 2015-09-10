/*   1:    */ package org.hibernate.service.jdbc.dialect.internal;
/*   2:    */ 
/*   3:    */ import java.sql.DatabaseMetaData;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import org.hibernate.dialect.CUBRIDDialect;
/*   6:    */ import org.hibernate.dialect.DB2Dialect;
/*   7:    */ import org.hibernate.dialect.DerbyDialect;
/*   8:    */ import org.hibernate.dialect.DerbyTenFiveDialect;
/*   9:    */ import org.hibernate.dialect.DerbyTenSevenDialect;
/*  10:    */ import org.hibernate.dialect.DerbyTenSixDialect;
/*  11:    */ import org.hibernate.dialect.Dialect;
/*  12:    */ import org.hibernate.dialect.H2Dialect;
/*  13:    */ import org.hibernate.dialect.HSQLDialect;
/*  14:    */ import org.hibernate.dialect.InformixDialect;
/*  15:    */ import org.hibernate.dialect.Ingres10Dialect;
/*  16:    */ import org.hibernate.dialect.Ingres9Dialect;
/*  17:    */ import org.hibernate.dialect.IngresDialect;
/*  18:    */ import org.hibernate.dialect.MySQLDialect;
/*  19:    */ import org.hibernate.dialect.Oracle10gDialect;
/*  20:    */ import org.hibernate.dialect.Oracle8iDialect;
/*  21:    */ import org.hibernate.dialect.Oracle9iDialect;
/*  22:    */ import org.hibernate.dialect.PostgreSQLDialect;
/*  23:    */ import org.hibernate.dialect.SQLServer2005Dialect;
/*  24:    */ import org.hibernate.dialect.SQLServer2008Dialect;
/*  25:    */ import org.hibernate.dialect.SQLServerDialect;
/*  26:    */ import org.hibernate.dialect.SybaseASE15Dialect;
/*  27:    */ import org.hibernate.dialect.SybaseAnywhereDialect;
/*  28:    */ import org.hibernate.internal.CoreMessageLogger;
/*  29:    */ import org.jboss.logging.Logger;
/*  30:    */ 
/*  31:    */ public class StandardDialectResolver
/*  32:    */   extends AbstractDialectResolver
/*  33:    */ {
/*  34: 63 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, StandardDialectResolver.class.getName());
/*  35:    */   
/*  36:    */   protected Dialect resolveDialectInternal(DatabaseMetaData metaData)
/*  37:    */     throws SQLException
/*  38:    */   {
/*  39: 68 */     String databaseName = metaData.getDatabaseProductName();
/*  40: 69 */     int databaseMajorVersion = metaData.getDatabaseMajorVersion();
/*  41: 71 */     if ("CUBRID".equalsIgnoreCase(databaseName)) {
/*  42: 72 */       return new CUBRIDDialect();
/*  43:    */     }
/*  44: 75 */     if ("HSQL Database Engine".equals(databaseName)) {
/*  45: 76 */       return new HSQLDialect();
/*  46:    */     }
/*  47: 79 */     if ("H2".equals(databaseName)) {
/*  48: 80 */       return new H2Dialect();
/*  49:    */     }
/*  50: 83 */     if ("MySQL".equals(databaseName)) {
/*  51: 84 */       return new MySQLDialect();
/*  52:    */     }
/*  53: 87 */     if ("PostgreSQL".equals(databaseName)) {
/*  54: 88 */       return new PostgreSQLDialect();
/*  55:    */     }
/*  56: 91 */     if ("Apache Derby".equals(databaseName))
/*  57:    */     {
/*  58: 92 */       int databaseMinorVersion = metaData.getDatabaseMinorVersion();
/*  59: 93 */       if ((databaseMajorVersion > 10) || ((databaseMajorVersion == 10) && (databaseMinorVersion >= 7))) {
/*  60: 94 */         return new DerbyTenSevenDialect();
/*  61:    */       }
/*  62: 96 */       if ((databaseMajorVersion == 10) && (databaseMinorVersion == 6)) {
/*  63: 97 */         return new DerbyTenSixDialect();
/*  64:    */       }
/*  65: 99 */       if ((databaseMajorVersion == 10) && (databaseMinorVersion == 5)) {
/*  66:100 */         return new DerbyTenFiveDialect();
/*  67:    */       }
/*  68:103 */       return new DerbyDialect();
/*  69:    */     }
/*  70:107 */     if ("ingres".equalsIgnoreCase(databaseName))
/*  71:    */     {
/*  72:108 */       switch (databaseMajorVersion)
/*  73:    */       {
/*  74:    */       case 9: 
/*  75:110 */         int databaseMinorVersion = metaData.getDatabaseMinorVersion();
/*  76:111 */         if (databaseMinorVersion > 2) {
/*  77:112 */           return new Ingres9Dialect();
/*  78:    */         }
/*  79:114 */         return new IngresDialect();
/*  80:    */       case 10: 
/*  81:116 */         return new Ingres10Dialect();
/*  82:    */       }
/*  83:118 */       LOG.unknownIngresVersion(databaseMajorVersion);
/*  84:    */       
/*  85:120 */       return new IngresDialect();
/*  86:    */     }
/*  87:123 */     if (databaseName.startsWith("Microsoft SQL Server"))
/*  88:    */     {
/*  89:124 */       switch (databaseMajorVersion)
/*  90:    */       {
/*  91:    */       case 8: 
/*  92:126 */         return new SQLServerDialect();
/*  93:    */       case 9: 
/*  94:128 */         return new SQLServer2005Dialect();
/*  95:    */       case 10: 
/*  96:130 */         return new SQLServer2008Dialect();
/*  97:    */       }
/*  98:132 */       LOG.unknownSqlServerVersion(databaseMajorVersion);
/*  99:    */       
/* 100:134 */       return new SQLServerDialect();
/* 101:    */     }
/* 102:137 */     if (("Sybase SQL Server".equals(databaseName)) || ("Adaptive Server Enterprise".equals(databaseName))) {
/* 103:138 */       return new SybaseASE15Dialect();
/* 104:    */     }
/* 105:141 */     if (databaseName.startsWith("Adaptive Server Anywhere")) {
/* 106:142 */       return new SybaseAnywhereDialect();
/* 107:    */     }
/* 108:145 */     if ("Informix Dynamic Server".equals(databaseName)) {
/* 109:146 */       return new InformixDialect();
/* 110:    */     }
/* 111:149 */     if (databaseName.startsWith("DB2/")) {
/* 112:150 */       return new DB2Dialect();
/* 113:    */     }
/* 114:153 */     if ("Oracle".equals(databaseName))
/* 115:    */     {
/* 116:154 */       switch (databaseMajorVersion)
/* 117:    */       {
/* 118:    */       case 11: 
/* 119:156 */         return new Oracle10gDialect();
/* 120:    */       case 10: 
/* 121:158 */         return new Oracle10gDialect();
/* 122:    */       case 9: 
/* 123:160 */         return new Oracle9iDialect();
/* 124:    */       case 8: 
/* 125:162 */         return new Oracle8iDialect();
/* 126:    */       }
/* 127:164 */       LOG.unknownOracleVersion(databaseMajorVersion);
/* 128:    */     }
/* 129:168 */     return null;
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.internal.StandardDialectResolver
 * JD-Core Version:    0.7.0.1
 */