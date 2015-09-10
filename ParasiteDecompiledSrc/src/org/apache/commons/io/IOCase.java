/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public final class IOCase
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8: 43 */   public static final IOCase SENSITIVE = new IOCase("Sensitive", true);
/*   9: 48 */   public static final IOCase INSENSITIVE = new IOCase("Insensitive", false);
/*  10: 62 */   public static final IOCase SYSTEM = new IOCase("System", !FilenameUtils.isSystemWindows());
/*  11:    */   private static final long serialVersionUID = -6343169151696340687L;
/*  12:    */   private final String name;
/*  13:    */   private final transient boolean sensitive;
/*  14:    */   
/*  15:    */   public static IOCase forName(String name)
/*  16:    */   {
/*  17: 82 */     if (SENSITIVE.name.equals(name)) {
/*  18: 83 */       return SENSITIVE;
/*  19:    */     }
/*  20: 85 */     if (INSENSITIVE.name.equals(name)) {
/*  21: 86 */       return INSENSITIVE;
/*  22:    */     }
/*  23: 88 */     if (SYSTEM.name.equals(name)) {
/*  24: 89 */       return SYSTEM;
/*  25:    */     }
/*  26: 91 */     throw new IllegalArgumentException("Invalid IOCase name: " + name);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private IOCase(String name, boolean sensitive)
/*  30:    */   {
/*  31:102 */     this.name = name;
/*  32:103 */     this.sensitive = sensitive;
/*  33:    */   }
/*  34:    */   
/*  35:    */   private Object readResolve()
/*  36:    */   {
/*  37:113 */     return forName(this.name);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getName()
/*  41:    */   {
/*  42:123 */     return this.name;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isCaseSensitive()
/*  46:    */   {
/*  47:132 */     return this.sensitive;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int checkCompareTo(String str1, String str2)
/*  51:    */   {
/*  52:148 */     if ((str1 == null) || (str2 == null)) {
/*  53:149 */       throw new NullPointerException("The strings must not be null");
/*  54:    */     }
/*  55:151 */     return this.sensitive ? str1.compareTo(str2) : str1.compareToIgnoreCase(str2);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean checkEquals(String str1, String str2)
/*  59:    */   {
/*  60:166 */     if ((str1 == null) || (str2 == null)) {
/*  61:167 */       throw new NullPointerException("The strings must not be null");
/*  62:    */     }
/*  63:169 */     return this.sensitive ? str1.equals(str2) : str1.equalsIgnoreCase(str2);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean checkStartsWith(String str, String start)
/*  67:    */   {
/*  68:184 */     return str.regionMatches(!this.sensitive, 0, start, 0, start.length());
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean checkEndsWith(String str, String end)
/*  72:    */   {
/*  73:199 */     int endLen = end.length();
/*  74:200 */     return str.regionMatches(!this.sensitive, str.length() - endLen, end, 0, endLen);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int checkIndexOf(String str, int strStartIndex, String search)
/*  78:    */   {
/*  79:219 */     int endIndex = str.length() - search.length();
/*  80:220 */     if (endIndex >= strStartIndex) {
/*  81:221 */       for (int i = strStartIndex; i <= endIndex; i++) {
/*  82:222 */         if (checkRegionMatches(str, i, search)) {
/*  83:223 */           return i;
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:227 */     return -1;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean checkRegionMatches(String str, int strStartIndex, String search)
/*  91:    */   {
/*  92:243 */     return str.regionMatches(!this.sensitive, strStartIndex, search, 0, search.length());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String toString()
/*  96:    */   {
/*  97:254 */     return this.name;
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.IOCase
 * JD-Core Version:    0.7.0.1
 */