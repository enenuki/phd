/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.internal.util.StringHelper;
/*   6:    */ 
/*   7:    */ public class ImprovedNamingStrategy
/*   8:    */   implements NamingStrategy, Serializable
/*   9:    */ {
/*  10: 42 */   public static final NamingStrategy INSTANCE = new ImprovedNamingStrategy();
/*  11:    */   
/*  12:    */   public String classToTableName(String className)
/*  13:    */   {
/*  14: 49 */     return addUnderscores(StringHelper.unqualify(className));
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String propertyToColumnName(String propertyName)
/*  18:    */   {
/*  19: 56 */     return addUnderscores(StringHelper.unqualify(propertyName));
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String tableName(String tableName)
/*  23:    */   {
/*  24: 62 */     return addUnderscores(tableName);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String columnName(String columnName)
/*  28:    */   {
/*  29: 68 */     return addUnderscores(columnName);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected static String addUnderscores(String name)
/*  33:    */   {
/*  34: 72 */     StringBuffer buf = new StringBuffer(name.replace('.', '_'));
/*  35: 73 */     for (int i = 1; i < buf.length() - 1; i++) {
/*  36: 74 */       if ((Character.isLowerCase(buf.charAt(i - 1))) && (Character.isUpperCase(buf.charAt(i))) && (Character.isLowerCase(buf.charAt(i + 1)))) {
/*  37: 79 */         buf.insert(i++, '_');
/*  38:    */       }
/*  39:    */     }
/*  40: 82 */     return buf.toString().toLowerCase();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName)
/*  44:    */   {
/*  45: 89 */     return tableName(ownerEntityTable + '_' + propertyToColumnName(propertyName));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String joinKeyColumnName(String joinedColumn, String joinedTable)
/*  49:    */   {
/*  50: 96 */     return columnName(joinedColumn);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName)
/*  54:    */   {
/*  55:105 */     String header = propertyName != null ? StringHelper.unqualify(propertyName) : propertyTableName;
/*  56:106 */     if (header == null) {
/*  57:106 */       throw new AssertionFailure("NamingStrategy not properly filled");
/*  58:    */     }
/*  59:107 */     return columnName(header);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String logicalColumnName(String columnName, String propertyName)
/*  63:    */   {
/*  64:114 */     return StringHelper.isNotEmpty(columnName) ? columnName : StringHelper.unqualify(propertyName);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName)
/*  68:    */   {
/*  69:125 */     if (tableName != null) {
/*  70:126 */       return tableName;
/*  71:    */     }
/*  72:130 */     return ownerEntityTable + "_" + (associatedEntityTable != null ? associatedEntityTable : StringHelper.unqualify(propertyName));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn)
/*  76:    */   {
/*  77:142 */     return StringHelper.unqualify(propertyName) + "_" + referencedColumn;
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ImprovedNamingStrategy
 * JD-Core Version:    0.7.0.1
 */