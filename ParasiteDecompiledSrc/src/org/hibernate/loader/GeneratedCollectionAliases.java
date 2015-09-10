/*   1:    */ package org.hibernate.loader;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.internal.util.StringHelper;
/*   5:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*   6:    */ import org.hibernate.persister.collection.CollectionPersister;
/*   7:    */ 
/*   8:    */ public class GeneratedCollectionAliases
/*   9:    */   implements CollectionAliases
/*  10:    */ {
/*  11:    */   private final String suffix;
/*  12:    */   private final String[] keyAliases;
/*  13:    */   private final String[] indexAliases;
/*  14:    */   private final String[] elementAliases;
/*  15:    */   private final String identifierAlias;
/*  16:    */   private Map userProvidedAliases;
/*  17:    */   
/*  18:    */   public GeneratedCollectionAliases(Map userProvidedAliases, CollectionPersister persister, String suffix)
/*  19:    */   {
/*  20: 48 */     this.suffix = suffix;
/*  21: 49 */     this.userProvidedAliases = userProvidedAliases;
/*  22:    */     
/*  23: 51 */     this.keyAliases = getUserProvidedAliases("key", persister.getKeyColumnAliases(suffix));
/*  24:    */     
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28: 56 */     this.indexAliases = getUserProvidedAliases("index", persister.getIndexColumnAliases(suffix));
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33: 61 */     this.elementAliases = getUserProvidedAliases("element", persister.getElementColumnAliases(suffix));
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37: 65 */     this.identifierAlias = getUserProvidedAlias("id", persister.getIdentifierColumnAlias(suffix));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public GeneratedCollectionAliases(CollectionPersister persister, String string)
/*  41:    */   {
/*  42: 71 */     this(CollectionHelper.EMPTY_MAP, persister, string);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String[] getSuffixedKeyAliases()
/*  46:    */   {
/*  47: 81 */     return this.keyAliases;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String[] getSuffixedIndexAliases()
/*  51:    */   {
/*  52: 90 */     return this.indexAliases;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String[] getSuffixedElementAliases()
/*  56:    */   {
/*  57: 99 */     return this.elementAliases;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getSuffixedIdentifierAlias()
/*  61:    */   {
/*  62:108 */     return this.identifierAlias;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getSuffix()
/*  66:    */   {
/*  67:117 */     return this.suffix;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String toString()
/*  71:    */   {
/*  72:122 */     return super.toString() + " [suffix=" + this.suffix + ", suffixedKeyAliases=[" + join(this.keyAliases) + "], suffixedIndexAliases=[" + join(this.indexAliases) + "], suffixedElementAliases=[" + join(this.elementAliases) + "], suffixedIdentifierAlias=[" + this.identifierAlias + "]]";
/*  73:    */   }
/*  74:    */   
/*  75:    */   private String join(String[] aliases)
/*  76:    */   {
/*  77:130 */     if (aliases == null) {
/*  78:130 */       return null;
/*  79:    */     }
/*  80:132 */     return StringHelper.join(", ", aliases);
/*  81:    */   }
/*  82:    */   
/*  83:    */   private String[] getUserProvidedAliases(String propertyPath, String[] defaultAliases)
/*  84:    */   {
/*  85:136 */     String[] result = (String[])this.userProvidedAliases.get(propertyPath);
/*  86:137 */     if (result == null) {
/*  87:138 */       return defaultAliases;
/*  88:    */     }
/*  89:141 */     return result;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private String getUserProvidedAlias(String propertyPath, String defaultAlias)
/*  93:    */   {
/*  94:146 */     String[] columns = (String[])this.userProvidedAliases.get(propertyPath);
/*  95:147 */     if (columns == null) {
/*  96:148 */       return defaultAlias;
/*  97:    */     }
/*  98:151 */     return columns[0];
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.GeneratedCollectionAliases
 * JD-Core Version:    0.7.0.1
 */