/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.commons.io.FilenameUtils;
/*   7:    */ import org.apache.commons.io.IOCase;
/*   8:    */ 
/*   9:    */ public class WildcardFileFilter
/*  10:    */   extends AbstractFileFilter
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private final String[] wildcards;
/*  14:    */   private final IOCase caseSensitivity;
/*  15:    */   
/*  16:    */   public WildcardFileFilter(String wildcard)
/*  17:    */   {
/*  18: 66 */     this(wildcard, null);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public WildcardFileFilter(String wildcard, IOCase caseSensitivity)
/*  22:    */   {
/*  23: 77 */     if (wildcard == null) {
/*  24: 78 */       throw new IllegalArgumentException("The wildcard must not be null");
/*  25:    */     }
/*  26: 80 */     this.wildcards = new String[] { wildcard };
/*  27: 81 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public WildcardFileFilter(String[] wildcards)
/*  31:    */   {
/*  32: 94 */     this(wildcards, null);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public WildcardFileFilter(String[] wildcards, IOCase caseSensitivity)
/*  36:    */   {
/*  37:108 */     if (wildcards == null) {
/*  38:109 */       throw new IllegalArgumentException("The wildcard array must not be null");
/*  39:    */     }
/*  40:111 */     this.wildcards = new String[wildcards.length];
/*  41:112 */     System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
/*  42:113 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public WildcardFileFilter(List<String> wildcards)
/*  46:    */   {
/*  47:124 */     this(wildcards, null);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public WildcardFileFilter(List<String> wildcards, IOCase caseSensitivity)
/*  51:    */   {
/*  52:136 */     if (wildcards == null) {
/*  53:137 */       throw new IllegalArgumentException("The wildcard list must not be null");
/*  54:    */     }
/*  55:139 */     this.wildcards = ((String[])wildcards.toArray(new String[wildcards.size()]));
/*  56:140 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean accept(File dir, String name)
/*  60:    */   {
/*  61:153 */     for (String wildcard : this.wildcards) {
/*  62:154 */       if (FilenameUtils.wildcardMatch(name, wildcard, this.caseSensitivity)) {
/*  63:155 */         return true;
/*  64:    */       }
/*  65:    */     }
/*  66:158 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean accept(File file)
/*  70:    */   {
/*  71:169 */     String name = file.getName();
/*  72:170 */     for (String wildcard : this.wildcards) {
/*  73:171 */       if (FilenameUtils.wildcardMatch(name, wildcard, this.caseSensitivity)) {
/*  74:172 */         return true;
/*  75:    */       }
/*  76:    */     }
/*  77:175 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toString()
/*  81:    */   {
/*  82:185 */     StringBuilder buffer = new StringBuilder();
/*  83:186 */     buffer.append(super.toString());
/*  84:187 */     buffer.append("(");
/*  85:188 */     if (this.wildcards != null) {
/*  86:189 */       for (int i = 0; i < this.wildcards.length; i++)
/*  87:    */       {
/*  88:190 */         if (i > 0) {
/*  89:191 */           buffer.append(",");
/*  90:    */         }
/*  91:193 */         buffer.append(this.wildcards[i]);
/*  92:    */       }
/*  93:    */     }
/*  94:196 */     buffer.append(")");
/*  95:197 */     return buffer.toString();
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.WildcardFileFilter
 * JD-Core Version:    0.7.0.1
 */