/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.internal.util.StringHelper;
/*   6:    */ 
/*   7:    */ public class DefaultNamingStrategy
/*   8:    */   implements NamingStrategy, Serializable
/*   9:    */ {
/*  10: 41 */   public static final NamingStrategy INSTANCE = new DefaultNamingStrategy();
/*  11:    */   
/*  12:    */   public String classToTableName(String className)
/*  13:    */   {
/*  14: 47 */     return StringHelper.unqualify(className);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String propertyToColumnName(String propertyName)
/*  18:    */   {
/*  19: 53 */     return StringHelper.unqualify(propertyName);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String tableName(String tableName)
/*  23:    */   {
/*  24: 59 */     return tableName;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String columnName(String columnName)
/*  28:    */   {
/*  29: 65 */     return columnName;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName)
/*  33:    */   {
/*  34: 76 */     return StringHelper.unqualify(propertyName);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String joinKeyColumnName(String joinedColumn, String joinedTable)
/*  38:    */   {
/*  39: 83 */     return columnName(joinedColumn);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName)
/*  43:    */   {
/*  44: 92 */     String header = propertyName != null ? StringHelper.unqualify(propertyName) : propertyTableName;
/*  45: 93 */     if (header == null) {
/*  46: 93 */       throw new AssertionFailure("NammingStrategy not properly filled");
/*  47:    */     }
/*  48: 94 */     return columnName(header);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String logicalColumnName(String columnName, String propertyName)
/*  52:    */   {
/*  53:101 */     return StringHelper.isNotEmpty(columnName) ? columnName : StringHelper.unqualify(propertyName);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName)
/*  57:    */   {
/*  58:112 */     if (tableName != null) {
/*  59:113 */       return tableName;
/*  60:    */     }
/*  61:117 */     return ownerEntityTable + "_" + (associatedEntityTable != null ? associatedEntityTable : StringHelper.unqualify(propertyName));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn)
/*  65:    */   {
/*  66:130 */     return propertyName + "_" + referencedColumn;
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.DefaultNamingStrategy
 * JD-Core Version:    0.7.0.1
 */