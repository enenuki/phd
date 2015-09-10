/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.commons.io.IOCase;
/*   7:    */ 
/*   8:    */ public class NameFileFilter
/*   9:    */   extends AbstractFileFilter
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private final String[] names;
/*  13:    */   private final IOCase caseSensitivity;
/*  14:    */   
/*  15:    */   public NameFileFilter(String name)
/*  16:    */   {
/*  17: 63 */     this(name, null);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public NameFileFilter(String name, IOCase caseSensitivity)
/*  21:    */   {
/*  22: 74 */     if (name == null) {
/*  23: 75 */       throw new IllegalArgumentException("The wildcard must not be null");
/*  24:    */     }
/*  25: 77 */     this.names = new String[] { name };
/*  26: 78 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public NameFileFilter(String[] names)
/*  30:    */   {
/*  31: 91 */     this(names, null);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public NameFileFilter(String[] names, IOCase caseSensitivity)
/*  35:    */   {
/*  36:105 */     if (names == null) {
/*  37:106 */       throw new IllegalArgumentException("The array of names must not be null");
/*  38:    */     }
/*  39:108 */     this.names = new String[names.length];
/*  40:109 */     System.arraycopy(names, 0, this.names, 0, names.length);
/*  41:110 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public NameFileFilter(List<String> names)
/*  45:    */   {
/*  46:121 */     this(names, null);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public NameFileFilter(List<String> names, IOCase caseSensitivity)
/*  50:    */   {
/*  51:133 */     if (names == null) {
/*  52:134 */       throw new IllegalArgumentException("The list of names must not be null");
/*  53:    */     }
/*  54:136 */     this.names = ((String[])names.toArray(new String[names.size()]));
/*  55:137 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean accept(File file)
/*  59:    */   {
/*  60:149 */     String name = file.getName();
/*  61:150 */     for (String name2 : this.names) {
/*  62:151 */       if (this.caseSensitivity.checkEquals(name, name2)) {
/*  63:152 */         return true;
/*  64:    */       }
/*  65:    */     }
/*  66:155 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean accept(File file, String name)
/*  70:    */   {
/*  71:167 */     for (String name2 : this.names) {
/*  72:168 */       if (this.caseSensitivity.checkEquals(name, name2)) {
/*  73:169 */         return true;
/*  74:    */       }
/*  75:    */     }
/*  76:172 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String toString()
/*  80:    */   {
/*  81:182 */     StringBuilder buffer = new StringBuilder();
/*  82:183 */     buffer.append(super.toString());
/*  83:184 */     buffer.append("(");
/*  84:185 */     if (this.names != null) {
/*  85:186 */       for (int i = 0; i < this.names.length; i++)
/*  86:    */       {
/*  87:187 */         if (i > 0) {
/*  88:188 */           buffer.append(",");
/*  89:    */         }
/*  90:190 */         buffer.append(this.names[i]);
/*  91:    */       }
/*  92:    */     }
/*  93:193 */     buffer.append(")");
/*  94:194 */     return buffer.toString();
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.NameFileFilter
 * JD-Core Version:    0.7.0.1
 */