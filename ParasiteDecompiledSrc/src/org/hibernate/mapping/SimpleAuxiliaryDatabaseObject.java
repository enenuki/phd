/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.engine.spi.Mapping;
/*  7:   */ import org.hibernate.internal.util.StringHelper;
/*  8:   */ 
/*  9:   */ public class SimpleAuxiliaryDatabaseObject
/* 10:   */   extends AbstractAuxiliaryDatabaseObject
/* 11:   */ {
/* 12:   */   private final String sqlCreateString;
/* 13:   */   private final String sqlDropString;
/* 14:   */   
/* 15:   */   public SimpleAuxiliaryDatabaseObject(String sqlCreateString, String sqlDropString)
/* 16:   */   {
/* 17:48 */     this.sqlCreateString = sqlCreateString;
/* 18:49 */     this.sqlDropString = sqlDropString;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SimpleAuxiliaryDatabaseObject(String sqlCreateString, String sqlDropString, HashSet dialectScopes)
/* 22:   */   {
/* 23:53 */     super(dialectScopes);
/* 24:54 */     this.sqlCreateString = sqlCreateString;
/* 25:55 */     this.sqlDropString = sqlDropString;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String sqlCreateString(Dialect dialect, Mapping p, String defaultCatalog, String defaultSchema)
/* 29:   */     throws HibernateException
/* 30:   */   {
/* 31:63 */     return injectCatalogAndSchema(this.sqlCreateString, defaultCatalog, defaultSchema);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String sqlDropString(Dialect dialect, String defaultCatalog, String defaultSchema)
/* 35:   */   {
/* 36:67 */     return injectCatalogAndSchema(this.sqlDropString, defaultCatalog, defaultSchema);
/* 37:   */   }
/* 38:   */   
/* 39:   */   private String injectCatalogAndSchema(String ddlString, String defaultCatalog, String defaultSchema)
/* 40:   */   {
/* 41:71 */     String rtn = StringHelper.replace(ddlString, "${catalog}", defaultCatalog);
/* 42:72 */     rtn = StringHelper.replace(rtn, "${schema}", defaultSchema);
/* 43:73 */     return rtn;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.SimpleAuxiliaryDatabaseObject
 * JD-Core Version:    0.7.0.1
 */