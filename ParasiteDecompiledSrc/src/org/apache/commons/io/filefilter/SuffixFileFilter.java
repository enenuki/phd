/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.commons.io.IOCase;
/*   7:    */ 
/*   8:    */ public class SuffixFileFilter
/*   9:    */   extends AbstractFileFilter
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private final String[] suffixes;
/*  13:    */   private final IOCase caseSensitivity;
/*  14:    */   
/*  15:    */   public SuffixFileFilter(String suffix)
/*  16:    */   {
/*  17: 65 */     this(suffix, IOCase.SENSITIVE);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public SuffixFileFilter(String suffix, IOCase caseSensitivity)
/*  21:    */   {
/*  22: 78 */     if (suffix == null) {
/*  23: 79 */       throw new IllegalArgumentException("The suffix must not be null");
/*  24:    */     }
/*  25: 81 */     this.suffixes = new String[] { suffix };
/*  26: 82 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public SuffixFileFilter(String[] suffixes)
/*  30:    */   {
/*  31: 95 */     this(suffixes, IOCase.SENSITIVE);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public SuffixFileFilter(String[] suffixes, IOCase caseSensitivity)
/*  35:    */   {
/*  36:111 */     if (suffixes == null) {
/*  37:112 */       throw new IllegalArgumentException("The array of suffixes must not be null");
/*  38:    */     }
/*  39:114 */     this.suffixes = new String[suffixes.length];
/*  40:115 */     System.arraycopy(suffixes, 0, this.suffixes, 0, suffixes.length);
/*  41:116 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public SuffixFileFilter(List<String> suffixes)
/*  45:    */   {
/*  46:127 */     this(suffixes, IOCase.SENSITIVE);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public SuffixFileFilter(List<String> suffixes, IOCase caseSensitivity)
/*  50:    */   {
/*  51:141 */     if (suffixes == null) {
/*  52:142 */       throw new IllegalArgumentException("The list of suffixes must not be null");
/*  53:    */     }
/*  54:144 */     this.suffixes = ((String[])suffixes.toArray(new String[suffixes.size()]));
/*  55:145 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean accept(File file)
/*  59:    */   {
/*  60:156 */     String name = file.getName();
/*  61:157 */     for (String suffix : this.suffixes) {
/*  62:158 */       if (this.caseSensitivity.checkEndsWith(name, suffix)) {
/*  63:159 */         return true;
/*  64:    */       }
/*  65:    */     }
/*  66:162 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean accept(File file, String name)
/*  70:    */   {
/*  71:174 */     for (String suffix : this.suffixes) {
/*  72:175 */       if (this.caseSensitivity.checkEndsWith(name, suffix)) {
/*  73:176 */         return true;
/*  74:    */       }
/*  75:    */     }
/*  76:179 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String toString()
/*  80:    */   {
/*  81:189 */     StringBuilder buffer = new StringBuilder();
/*  82:190 */     buffer.append(super.toString());
/*  83:191 */     buffer.append("(");
/*  84:192 */     if (this.suffixes != null) {
/*  85:193 */       for (int i = 0; i < this.suffixes.length; i++)
/*  86:    */       {
/*  87:194 */         if (i > 0) {
/*  88:195 */           buffer.append(",");
/*  89:    */         }
/*  90:197 */         buffer.append(this.suffixes[i]);
/*  91:    */       }
/*  92:    */     }
/*  93:200 */     buffer.append(")");
/*  94:201 */     return buffer.toString();
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.SuffixFileFilter
 * JD-Core Version:    0.7.0.1
 */