/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import org.hibernate.AssertionFailure;
/*  4:   */ import org.hibernate.internal.util.StringHelper;
/*  5:   */ 
/*  6:   */ public class DefaultComponentSafeNamingStrategy
/*  7:   */   extends EJB3NamingStrategy
/*  8:   */ {
/*  9:32 */   public static final NamingStrategy INSTANCE = new DefaultComponentSafeNamingStrategy();
/* 10:   */   
/* 11:   */   protected static String addUnderscores(String name)
/* 12:   */   {
/* 13:35 */     return name.replace('.', '_').toLowerCase();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String propertyToColumnName(String propertyName)
/* 17:   */   {
/* 18:40 */     return addUnderscores(propertyName);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName)
/* 22:   */   {
/* 23:48 */     return tableName(ownerEntityTable + "_" + (associatedEntityTable != null ? associatedEntityTable : addUnderscores(propertyName)));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName)
/* 27:   */   {
/* 28:62 */     String header = propertyName != null ? addUnderscores(propertyName) : propertyTableName;
/* 29:63 */     if (header == null) {
/* 30:63 */       throw new AssertionFailure("NamingStrategy not properly filled");
/* 31:   */     }
/* 32:64 */     return columnName(header + "_" + referencedColumnName);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String logicalColumnName(String columnName, String propertyName)
/* 36:   */   {
/* 37:69 */     return StringHelper.isNotEmpty(columnName) ? columnName : propertyName;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName)
/* 41:   */   {
/* 42:76 */     if (tableName != null) {
/* 43:77 */       return tableName;
/* 44:   */     }
/* 45:81 */     return ownerEntityTable + "_" + (associatedEntityTable != null ? associatedEntityTable : propertyName);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn)
/* 49:   */   {
/* 50:93 */     return propertyName + "_" + referencedColumn;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.DefaultComponentSafeNamingStrategy
 * JD-Core Version:    0.7.0.1
 */