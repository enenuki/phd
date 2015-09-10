/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import org.hibernate.dialect.Dialect;
/*   4:    */ import org.hibernate.internal.util.StringHelper;
/*   5:    */ 
/*   6:    */ public class Identifier
/*   7:    */ {
/*   8:    */   private final String name;
/*   9:    */   private final boolean isQuoted;
/*  10:    */   
/*  11:    */   public static Identifier toIdentifier(String name)
/*  12:    */   {
/*  13: 47 */     if (StringHelper.isEmpty(name)) {
/*  14: 48 */       return null;
/*  15:    */     }
/*  16: 50 */     String trimmedName = name.trim();
/*  17: 51 */     if (isQuoted(trimmedName))
/*  18:    */     {
/*  19: 52 */       String bareName = trimmedName.substring(1, trimmedName.length() - 1);
/*  20: 53 */       return new Identifier(bareName, true);
/*  21:    */     }
/*  22: 56 */     return new Identifier(trimmedName, false);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static boolean isQuoted(String name)
/*  26:    */   {
/*  27: 61 */     return (name.startsWith("`")) && (name.endsWith("`"));
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Identifier(String name, boolean quoted)
/*  31:    */   {
/*  32: 71 */     if (StringHelper.isEmpty(name)) {
/*  33: 72 */       throw new IllegalIdentifierException("Identifier text cannot be null");
/*  34:    */     }
/*  35: 74 */     if (isQuoted(name)) {
/*  36: 75 */       throw new IllegalIdentifierException("Identifier text should not contain quote markers (`)");
/*  37:    */     }
/*  38: 77 */     this.name = name;
/*  39: 78 */     this.isQuoted = quoted;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getName()
/*  43:    */   {
/*  44: 87 */     return this.name;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean isQuoted()
/*  48:    */   {
/*  49: 96 */     return this.isQuoted;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String encloseInQuotesIfQuoted(Dialect dialect)
/*  53:    */   {
/*  54:109 */     return this.isQuoted ? this.name.length() + 2 + dialect.openQuote() + this.name + dialect.closeQuote() : this.name;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String toString()
/*  58:    */   {
/*  59:120 */     return this.isQuoted ? '`' + getName() + '`' : getName();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean equals(Object o)
/*  63:    */   {
/*  64:127 */     if (this == o) {
/*  65:128 */       return true;
/*  66:    */     }
/*  67:130 */     if ((o == null) || (getClass() != o.getClass())) {
/*  68:131 */       return false;
/*  69:    */     }
/*  70:134 */     Identifier that = (Identifier)o;
/*  71:    */     
/*  72:136 */     return (this.isQuoted == that.isQuoted) && (this.name.equals(that.name));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int hashCode()
/*  76:    */   {
/*  77:142 */     return this.name.hashCode();
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Identifier
 * JD-Core Version:    0.7.0.1
 */