/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.internal.util.StringHelper;
/*   6:    */ 
/*   7:    */ public class EJB3NamingStrategy
/*   8:    */   implements NamingStrategy, Serializable
/*   9:    */ {
/*  10: 36 */   public static final NamingStrategy INSTANCE = new EJB3NamingStrategy();
/*  11:    */   
/*  12:    */   public String classToTableName(String className)
/*  13:    */   {
/*  14: 39 */     return StringHelper.unqualify(className);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String propertyToColumnName(String propertyName)
/*  18:    */   {
/*  19: 43 */     return StringHelper.unqualify(propertyName);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String tableName(String tableName)
/*  23:    */   {
/*  24: 47 */     return tableName;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String columnName(String columnName)
/*  28:    */   {
/*  29: 51 */     return columnName;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName)
/*  33:    */   {
/*  34: 58 */     return tableName(ownerEntityTable + "_" + (associatedEntityTable != null ? associatedEntityTable : StringHelper.unqualify(propertyName)));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String joinKeyColumnName(String joinedColumn, String joinedTable)
/*  38:    */   {
/*  39: 69 */     return columnName(joinedColumn);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName)
/*  43:    */   {
/*  44: 75 */     String header = propertyName != null ? StringHelper.unqualify(propertyName) : propertyTableName;
/*  45: 76 */     if (header == null) {
/*  46: 76 */       throw new AssertionFailure("NamingStrategy not properly filled");
/*  47:    */     }
/*  48: 77 */     return columnName(header + "_" + referencedColumnName);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String logicalColumnName(String columnName, String propertyName)
/*  52:    */   {
/*  53: 81 */     return StringHelper.isNotEmpty(columnName) ? columnName : StringHelper.unqualify(propertyName);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName)
/*  57:    */   {
/*  58: 88 */     if (tableName != null) {
/*  59: 89 */       return tableName;
/*  60:    */     }
/*  61: 93 */     return ownerEntityTable + "_" + (associatedEntityTable != null ? associatedEntityTable : StringHelper.unqualify(propertyName));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn)
/*  65:    */   {
/*  66:103 */     return StringHelper.unqualify(propertyName) + "_" + referencedColumn;
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.EJB3NamingStrategy
 * JD-Core Version:    0.7.0.1
 */