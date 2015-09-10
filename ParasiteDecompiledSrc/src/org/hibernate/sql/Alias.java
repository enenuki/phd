/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ public final class Alias
/*   4:    */ {
/*   5:    */   private final int length;
/*   6:    */   private final String suffix;
/*   7:    */   
/*   8:    */   public Alias(int length, String suffix)
/*   9:    */   {
/*  10: 42 */     this.length = (suffix == null ? length : length - suffix.length());
/*  11: 43 */     this.suffix = suffix;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Alias(String suffix)
/*  15:    */   {
/*  16: 51 */     this.length = 2147483647;
/*  17: 52 */     this.suffix = suffix;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String toAliasString(String sqlIdentifier)
/*  21:    */   {
/*  22: 56 */     char begin = sqlIdentifier.charAt(0);
/*  23: 57 */     int quoteType = "`\"[".indexOf(begin);
/*  24: 58 */     String unquoted = getUnquotedAliasString(sqlIdentifier, quoteType);
/*  25: 59 */     if (quoteType >= 0)
/*  26:    */     {
/*  27: 60 */       char endQuote = "`\"]".charAt(quoteType);
/*  28: 61 */       return begin + unquoted + endQuote;
/*  29:    */     }
/*  30: 64 */     return unquoted;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String toUnquotedAliasString(String sqlIdentifier)
/*  34:    */   {
/*  35: 69 */     return getUnquotedAliasString(sqlIdentifier);
/*  36:    */   }
/*  37:    */   
/*  38:    */   private String getUnquotedAliasString(String sqlIdentifier)
/*  39:    */   {
/*  40: 73 */     char begin = sqlIdentifier.charAt(0);
/*  41: 74 */     int quoteType = "`\"[".indexOf(begin);
/*  42: 75 */     return getUnquotedAliasString(sqlIdentifier, quoteType);
/*  43:    */   }
/*  44:    */   
/*  45:    */   private String getUnquotedAliasString(String sqlIdentifier, int quoteType)
/*  46:    */   {
/*  47: 79 */     String unquoted = sqlIdentifier;
/*  48: 80 */     if (quoteType >= 0) {
/*  49: 82 */       unquoted = unquoted.substring(1, unquoted.length() - 1);
/*  50:    */     }
/*  51: 84 */     if (unquoted.length() > this.length) {
/*  52: 86 */       unquoted = unquoted.substring(0, this.length);
/*  53:    */     }
/*  54: 88 */     if (this.suffix == null) {
/*  55: 89 */       return unquoted;
/*  56:    */     }
/*  57: 92 */     return unquoted + this.suffix;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String[] toUnquotedAliasStrings(String[] sqlIdentifiers)
/*  61:    */   {
/*  62: 97 */     String[] aliases = new String[sqlIdentifiers.length];
/*  63: 98 */     for (int i = 0; i < sqlIdentifiers.length; i++) {
/*  64: 99 */       aliases[i] = toUnquotedAliasString(sqlIdentifiers[i]);
/*  65:    */     }
/*  66:101 */     return aliases;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String[] toAliasStrings(String[] sqlIdentifiers)
/*  70:    */   {
/*  71:105 */     String[] aliases = new String[sqlIdentifiers.length];
/*  72:106 */     for (int i = 0; i < sqlIdentifiers.length; i++) {
/*  73:107 */       aliases[i] = toAliasString(sqlIdentifiers[i]);
/*  74:    */     }
/*  75:109 */     return aliases;
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.Alias
 * JD-Core Version:    0.7.0.1
 */