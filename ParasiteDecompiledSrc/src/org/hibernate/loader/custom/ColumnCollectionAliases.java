/*   1:    */ package org.hibernate.loader.custom;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.internal.util.StringHelper;
/*   5:    */ import org.hibernate.loader.CollectionAliases;
/*   6:    */ import org.hibernate.persister.collection.SQLLoadableCollection;
/*   7:    */ 
/*   8:    */ public class ColumnCollectionAliases
/*   9:    */   implements CollectionAliases
/*  10:    */ {
/*  11:    */   private final String[] keyAliases;
/*  12:    */   private final String[] indexAliases;
/*  13:    */   private final String[] elementAliases;
/*  14:    */   private final String identifierAlias;
/*  15:    */   private Map userProvidedAliases;
/*  16:    */   
/*  17:    */   public ColumnCollectionAliases(Map userProvidedAliases, SQLLoadableCollection persister)
/*  18:    */   {
/*  19: 49 */     this.userProvidedAliases = userProvidedAliases;
/*  20:    */     
/*  21: 51 */     this.keyAliases = getUserProvidedAliases("key", persister.getKeyColumnNames());
/*  22:    */     
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26: 56 */     this.indexAliases = getUserProvidedAliases("index", persister.getIndexColumnNames());
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31: 61 */     this.elementAliases = getUserProvidedAliases("element", persister.getElementColumnNames());
/*  32:    */     
/*  33:    */ 
/*  34:    */ 
/*  35: 65 */     this.identifierAlias = getUserProvidedAlias("id", persister.getIdentifierColumnName());
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String[] getSuffixedKeyAliases()
/*  39:    */   {
/*  40: 79 */     return this.keyAliases;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String[] getSuffixedIndexAliases()
/*  44:    */   {
/*  45: 88 */     return this.indexAliases;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String[] getSuffixedElementAliases()
/*  49:    */   {
/*  50: 97 */     return this.elementAliases;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getSuffixedIdentifierAlias()
/*  54:    */   {
/*  55:106 */     return this.identifierAlias;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getSuffix()
/*  59:    */   {
/*  60:115 */     return "";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String toString()
/*  64:    */   {
/*  65:120 */     return super.toString() + " [ suffixedKeyAliases=[" + join(this.keyAliases) + "], suffixedIndexAliases=[" + join(this.indexAliases) + "], suffixedElementAliases=[" + join(this.elementAliases) + "], suffixedIdentifierAlias=[" + this.identifierAlias + "]]";
/*  66:    */   }
/*  67:    */   
/*  68:    */   private String join(String[] aliases)
/*  69:    */   {
/*  70:127 */     if (aliases == null) {
/*  71:127 */       return null;
/*  72:    */     }
/*  73:129 */     return StringHelper.join(", ", aliases);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private String[] getUserProvidedAliases(String propertyPath, String[] defaultAliases)
/*  77:    */   {
/*  78:133 */     String[] result = (String[])this.userProvidedAliases.get(propertyPath);
/*  79:134 */     if (result == null) {
/*  80:135 */       return defaultAliases;
/*  81:    */     }
/*  82:138 */     return result;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private String getUserProvidedAlias(String propertyPath, String defaultAlias)
/*  86:    */   {
/*  87:143 */     String[] columns = (String[])this.userProvidedAliases.get(propertyPath);
/*  88:144 */     if (columns == null) {
/*  89:145 */       return defaultAlias;
/*  90:    */     }
/*  91:148 */     return columns[0];
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.ColumnCollectionAliases
 * JD-Core Version:    0.7.0.1
 */