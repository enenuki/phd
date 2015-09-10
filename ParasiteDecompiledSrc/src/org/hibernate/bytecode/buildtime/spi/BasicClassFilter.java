/*  1:   */ package org.hibernate.bytecode.buildtime.spi;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.HashSet;
/*  5:   */ import java.util.Set;
/*  6:   */ 
/*  7:   */ public class BasicClassFilter
/*  8:   */   implements ClassFilter
/*  9:   */ {
/* 10:   */   private final String[] includedPackages;
/* 11:39 */   private final Set<String> includedClassNames = new HashSet();
/* 12:   */   private final boolean isAllEmpty;
/* 13:   */   
/* 14:   */   public BasicClassFilter()
/* 15:   */   {
/* 16:43 */     this(null, null);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public BasicClassFilter(String[] includedPackages, String[] includedClassNames)
/* 20:   */   {
/* 21:47 */     this.includedPackages = includedPackages;
/* 22:48 */     if (includedClassNames != null) {
/* 23:49 */       this.includedClassNames.addAll(Arrays.asList(includedClassNames));
/* 24:   */     }
/* 25:52 */     this.isAllEmpty = (((this.includedPackages == null) || (this.includedPackages.length == 0)) && (this.includedClassNames.isEmpty()));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean shouldInstrumentClass(String className)
/* 29:   */   {
/* 30:57 */     return (this.isAllEmpty) || (this.includedClassNames.contains(className)) || (isInIncludedPackage(className));
/* 31:   */   }
/* 32:   */   
/* 33:   */   private boolean isInIncludedPackage(String className)
/* 34:   */   {
/* 35:63 */     if (this.includedPackages != null) {
/* 36:64 */       for (String includedPackage : this.includedPackages) {
/* 37:65 */         if (className.startsWith(includedPackage)) {
/* 38:66 */           return true;
/* 39:   */         }
/* 40:   */       }
/* 41:   */     }
/* 42:70 */     return false;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.buildtime.spi.BasicClassFilter
 * JD-Core Version:    0.7.0.1
 */