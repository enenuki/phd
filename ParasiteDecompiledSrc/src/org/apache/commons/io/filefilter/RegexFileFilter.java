/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.regex.Matcher;
/*   6:    */ import java.util.regex.Pattern;
/*   7:    */ import org.apache.commons.io.IOCase;
/*   8:    */ 
/*   9:    */ public class RegexFileFilter
/*  10:    */   extends AbstractFileFilter
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private final Pattern pattern;
/*  14:    */   
/*  15:    */   public RegexFileFilter(String pattern)
/*  16:    */   {
/*  17: 58 */     if (pattern == null) {
/*  18: 59 */       throw new IllegalArgumentException("Pattern is missing");
/*  19:    */     }
/*  20: 62 */     this.pattern = Pattern.compile(pattern);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public RegexFileFilter(String pattern, IOCase caseSensitivity)
/*  24:    */   {
/*  25: 73 */     if (pattern == null) {
/*  26: 74 */       throw new IllegalArgumentException("Pattern is missing");
/*  27:    */     }
/*  28: 76 */     int flags = 0;
/*  29: 77 */     if ((caseSensitivity != null) && (!caseSensitivity.isCaseSensitive())) {
/*  30: 78 */       flags = 2;
/*  31:    */     }
/*  32: 80 */     this.pattern = Pattern.compile(pattern, flags);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public RegexFileFilter(String pattern, int flags)
/*  36:    */   {
/*  37: 91 */     if (pattern == null) {
/*  38: 92 */       throw new IllegalArgumentException("Pattern is missing");
/*  39:    */     }
/*  40: 94 */     this.pattern = Pattern.compile(pattern, flags);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public RegexFileFilter(Pattern pattern)
/*  44:    */   {
/*  45:104 */     if (pattern == null) {
/*  46:105 */       throw new IllegalArgumentException("Pattern is missing");
/*  47:    */     }
/*  48:108 */     this.pattern = pattern;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean accept(File dir, String name)
/*  52:    */   {
/*  53:120 */     return this.pattern.matcher(name).matches();
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.RegexFileFilter
 * JD-Core Version:    0.7.0.1
 */