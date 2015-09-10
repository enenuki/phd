/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import org.hibernate.internal.util.StringHelper;
/*   4:    */ 
/*   5:    */ public abstract class ObjectNameNormalizer
/*   6:    */ {
/*   7:    */   public String normalizeDatabaseIdentifier(String explicitName, NamingStrategyHelper helper)
/*   8:    */   {
/*   9: 68 */     String objectName = null;
/*  10: 70 */     if (StringHelper.isEmpty(explicitName))
/*  11:    */     {
/*  12: 73 */       objectName = helper.determineImplicitName(getNamingStrategy());
/*  13:    */     }
/*  14:    */     else
/*  15:    */     {
/*  16: 79 */       objectName = normalizeIdentifierQuoting(explicitName);
/*  17: 80 */       objectName = helper.handleExplicitName(getNamingStrategy(), objectName);
/*  18: 81 */       return normalizeIdentifierQuoting(objectName);
/*  19:    */     }
/*  20: 85 */     return normalizeIdentifierQuoting(objectName);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String normalizeIdentifierQuoting(String identifier)
/*  24:    */   {
/*  25:102 */     if (StringHelper.isEmpty(identifier)) {
/*  26:103 */       return null;
/*  27:    */     }
/*  28:107 */     if ((identifier.startsWith("\"")) && (identifier.endsWith("\""))) {
/*  29:108 */       return '`' + identifier.substring(1, identifier.length() - 1) + '`';
/*  30:    */     }
/*  31:113 */     if ((isUseQuotedIdentifiersGlobally()) && ((!identifier.startsWith("`")) || (!identifier.endsWith("`")))) {
/*  32:114 */       return '`' + identifier + '`';
/*  33:    */     }
/*  34:117 */     return identifier;
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected abstract boolean isUseQuotedIdentifiersGlobally();
/*  38:    */   
/*  39:    */   protected abstract NamingStrategy getNamingStrategy();
/*  40:    */   
/*  41:    */   public static abstract interface NamingStrategyHelper
/*  42:    */   {
/*  43:    */     public abstract String determineImplicitName(NamingStrategy paramNamingStrategy);
/*  44:    */     
/*  45:    */     public abstract String handleExplicitName(NamingStrategy paramNamingStrategy, String paramString);
/*  46:    */   }
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ObjectNameNormalizer
 * JD-Core Version:    0.7.0.1
 */