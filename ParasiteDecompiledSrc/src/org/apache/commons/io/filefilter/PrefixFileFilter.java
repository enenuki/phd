/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.commons.io.IOCase;
/*   7:    */ 
/*   8:    */ public class PrefixFileFilter
/*   9:    */   extends AbstractFileFilter
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private final String[] prefixes;
/*  13:    */   private final IOCase caseSensitivity;
/*  14:    */   
/*  15:    */   public PrefixFileFilter(String prefix)
/*  16:    */   {
/*  17: 64 */     this(prefix, IOCase.SENSITIVE);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public PrefixFileFilter(String prefix, IOCase caseSensitivity)
/*  21:    */   {
/*  22: 77 */     if (prefix == null) {
/*  23: 78 */       throw new IllegalArgumentException("The prefix must not be null");
/*  24:    */     }
/*  25: 80 */     this.prefixes = new String[] { prefix };
/*  26: 81 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public PrefixFileFilter(String[] prefixes)
/*  30:    */   {
/*  31: 94 */     this(prefixes, IOCase.SENSITIVE);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public PrefixFileFilter(String[] prefixes, IOCase caseSensitivity)
/*  35:    */   {
/*  36:110 */     if (prefixes == null) {
/*  37:111 */       throw new IllegalArgumentException("The array of prefixes must not be null");
/*  38:    */     }
/*  39:113 */     this.prefixes = new String[prefixes.length];
/*  40:114 */     System.arraycopy(prefixes, 0, this.prefixes, 0, prefixes.length);
/*  41:115 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public PrefixFileFilter(List<String> prefixes)
/*  45:    */   {
/*  46:126 */     this(prefixes, IOCase.SENSITIVE);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public PrefixFileFilter(List<String> prefixes, IOCase caseSensitivity)
/*  50:    */   {
/*  51:140 */     if (prefixes == null) {
/*  52:141 */       throw new IllegalArgumentException("The list of prefixes must not be null");
/*  53:    */     }
/*  54:143 */     this.prefixes = ((String[])prefixes.toArray(new String[prefixes.size()]));
/*  55:144 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean accept(File file)
/*  59:    */   {
/*  60:155 */     String name = file.getName();
/*  61:156 */     for (String prefix : this.prefixes) {
/*  62:157 */       if (this.caseSensitivity.checkStartsWith(name, prefix)) {
/*  63:158 */         return true;
/*  64:    */       }
/*  65:    */     }
/*  66:161 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean accept(File file, String name)
/*  70:    */   {
/*  71:173 */     for (String prefix : this.prefixes) {
/*  72:174 */       if (this.caseSensitivity.checkStartsWith(name, prefix)) {
/*  73:175 */         return true;
/*  74:    */       }
/*  75:    */     }
/*  76:178 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String toString()
/*  80:    */   {
/*  81:188 */     StringBuilder buffer = new StringBuilder();
/*  82:189 */     buffer.append(super.toString());
/*  83:190 */     buffer.append("(");
/*  84:191 */     if (this.prefixes != null) {
/*  85:192 */       for (int i = 0; i < this.prefixes.length; i++)
/*  86:    */       {
/*  87:193 */         if (i > 0) {
/*  88:194 */           buffer.append(",");
/*  89:    */         }
/*  90:196 */         buffer.append(this.prefixes[i]);
/*  91:    */       }
/*  92:    */     }
/*  93:199 */     buffer.append(")");
/*  94:200 */     return buffer.toString();
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.PrefixFileFilter
 * JD-Core Version:    0.7.0.1
 */