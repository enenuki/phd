/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.commons.io.FilenameUtils;
/*   7:    */ 
/*   8:    */ @Deprecated
/*   9:    */ public class WildcardFilter
/*  10:    */   extends AbstractFileFilter
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private final String[] wildcards;
/*  14:    */   
/*  15:    */   public WildcardFilter(String wildcard)
/*  16:    */   {
/*  17: 66 */     if (wildcard == null) {
/*  18: 67 */       throw new IllegalArgumentException("The wildcard must not be null");
/*  19:    */     }
/*  20: 69 */     this.wildcards = new String[] { wildcard };
/*  21:    */   }
/*  22:    */   
/*  23:    */   public WildcardFilter(String[] wildcards)
/*  24:    */   {
/*  25: 79 */     if (wildcards == null) {
/*  26: 80 */       throw new IllegalArgumentException("The wildcard array must not be null");
/*  27:    */     }
/*  28: 82 */     this.wildcards = new String[wildcards.length];
/*  29: 83 */     System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public WildcardFilter(List<String> wildcards)
/*  33:    */   {
/*  34: 94 */     if (wildcards == null) {
/*  35: 95 */       throw new IllegalArgumentException("The wildcard list must not be null");
/*  36:    */     }
/*  37: 97 */     this.wildcards = ((String[])wildcards.toArray(new String[wildcards.size()]));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean accept(File dir, String name)
/*  41:    */   {
/*  42:110 */     if ((dir != null) && (new File(dir, name).isDirectory())) {
/*  43:111 */       return false;
/*  44:    */     }
/*  45:114 */     for (String wildcard : this.wildcards) {
/*  46:115 */       if (FilenameUtils.wildcardMatch(name, wildcard)) {
/*  47:116 */         return true;
/*  48:    */       }
/*  49:    */     }
/*  50:120 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean accept(File file)
/*  54:    */   {
/*  55:131 */     if (file.isDirectory()) {
/*  56:132 */       return false;
/*  57:    */     }
/*  58:135 */     for (String wildcard : this.wildcards) {
/*  59:136 */       if (FilenameUtils.wildcardMatch(file.getName(), wildcard)) {
/*  60:137 */         return true;
/*  61:    */       }
/*  62:    */     }
/*  63:141 */     return false;
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.WildcardFilter
 * JD-Core Version:    0.7.0.1
 */