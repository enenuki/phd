/*   1:    */ package org.junit.experimental.categories;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.lang.annotation.Retention;
/*   5:    */ import java.lang.annotation.RetentionPolicy;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.List;
/*   9:    */ import org.junit.runner.Description;
/*  10:    */ import org.junit.runner.manipulation.Filter;
/*  11:    */ import org.junit.runner.manipulation.NoTestsRemainException;
/*  12:    */ import org.junit.runners.Suite;
/*  13:    */ import org.junit.runners.model.InitializationError;
/*  14:    */ import org.junit.runners.model.RunnerBuilder;
/*  15:    */ 
/*  16:    */ public class Categories
/*  17:    */   extends Suite
/*  18:    */ {
/*  19:    */   public static class CategoryFilter
/*  20:    */     extends Filter
/*  21:    */   {
/*  22:    */     private final Class<?> fIncluded;
/*  23:    */     private final Class<?> fExcluded;
/*  24:    */     
/*  25:    */     public static CategoryFilter include(Class<?> categoryType)
/*  26:    */     {
/*  27: 82 */       return new CategoryFilter(categoryType, null);
/*  28:    */     }
/*  29:    */     
/*  30:    */     public CategoryFilter(Class<?> includedCategory, Class<?> excludedCategory)
/*  31:    */     {
/*  32: 91 */       this.fIncluded = includedCategory;
/*  33: 92 */       this.fExcluded = excludedCategory;
/*  34:    */     }
/*  35:    */     
/*  36:    */     public String describe()
/*  37:    */     {
/*  38: 97 */       return "category " + this.fIncluded;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public boolean shouldRun(Description description)
/*  42:    */     {
/*  43:102 */       if (hasCorrectCategoryAnnotation(description)) {
/*  44:103 */         return true;
/*  45:    */       }
/*  46:104 */       for (Description each : description.getChildren()) {
/*  47:105 */         if (shouldRun(each)) {
/*  48:106 */           return true;
/*  49:    */         }
/*  50:    */       }
/*  51:107 */       return false;
/*  52:    */     }
/*  53:    */     
/*  54:    */     private boolean hasCorrectCategoryAnnotation(Description description)
/*  55:    */     {
/*  56:111 */       List<Class<?>> categories = categories(description);
/*  57:112 */       if (categories.isEmpty()) {
/*  58:113 */         return this.fIncluded == null;
/*  59:    */       }
/*  60:114 */       for (Class<?> each : categories) {
/*  61:115 */         if ((this.fExcluded != null) && (this.fExcluded.isAssignableFrom(each))) {
/*  62:116 */           return false;
/*  63:    */         }
/*  64:    */       }
/*  65:117 */       for (Class<?> each : categories) {
/*  66:118 */         if ((this.fIncluded == null) || (this.fIncluded.isAssignableFrom(each))) {
/*  67:119 */           return true;
/*  68:    */         }
/*  69:    */       }
/*  70:120 */       return false;
/*  71:    */     }
/*  72:    */     
/*  73:    */     private List<Class<?>> categories(Description description)
/*  74:    */     {
/*  75:124 */       ArrayList<Class<?>> categories = new ArrayList();
/*  76:125 */       categories.addAll(Arrays.asList(directCategories(description)));
/*  77:126 */       categories.addAll(Arrays.asList(directCategories(parentDescription(description))));
/*  78:127 */       return categories;
/*  79:    */     }
/*  80:    */     
/*  81:    */     private Description parentDescription(Description description)
/*  82:    */     {
/*  83:131 */       Class<?> testClass = description.getTestClass();
/*  84:132 */       if (testClass == null) {
/*  85:133 */         return null;
/*  86:    */       }
/*  87:134 */       return Description.createSuiteDescription(testClass);
/*  88:    */     }
/*  89:    */     
/*  90:    */     private Class<?>[] directCategories(Description description)
/*  91:    */     {
/*  92:138 */       if (description == null) {
/*  93:139 */         return new Class[0];
/*  94:    */       }
/*  95:140 */       Category annotation = (Category)description.getAnnotation(Category.class);
/*  96:141 */       if (annotation == null) {
/*  97:142 */         return new Class[0];
/*  98:    */       }
/*  99:143 */       return annotation.value();
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Categories(Class<?> klass, RunnerBuilder builder)
/* 104:    */     throws InitializationError
/* 105:    */   {
/* 106:149 */     super(klass, builder);
/* 107:    */     try
/* 108:    */     {
/* 109:151 */       filter(new CategoryFilter(getIncludedCategory(klass), getExcludedCategory(klass)));
/* 110:    */     }
/* 111:    */     catch (NoTestsRemainException e)
/* 112:    */     {
/* 113:154 */       throw new InitializationError(e);
/* 114:    */     }
/* 115:156 */     assertNoCategorizedDescendentsOfUncategorizeableParents(getDescription());
/* 116:    */   }
/* 117:    */   
/* 118:    */   private Class<?> getIncludedCategory(Class<?> klass)
/* 119:    */   {
/* 120:160 */     IncludeCategory annotation = (IncludeCategory)klass.getAnnotation(IncludeCategory.class);
/* 121:161 */     return annotation == null ? null : annotation.value();
/* 122:    */   }
/* 123:    */   
/* 124:    */   private Class<?> getExcludedCategory(Class<?> klass)
/* 125:    */   {
/* 126:165 */     ExcludeCategory annotation = (ExcludeCategory)klass.getAnnotation(ExcludeCategory.class);
/* 127:166 */     return annotation == null ? null : annotation.value();
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void assertNoCategorizedDescendentsOfUncategorizeableParents(Description description)
/* 131:    */     throws InitializationError
/* 132:    */   {
/* 133:170 */     if (!canHaveCategorizedChildren(description)) {
/* 134:171 */       assertNoDescendantsHaveCategoryAnnotations(description);
/* 135:    */     }
/* 136:172 */     for (Description each : description.getChildren()) {
/* 137:173 */       assertNoCategorizedDescendentsOfUncategorizeableParents(each);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void assertNoDescendantsHaveCategoryAnnotations(Description description)
/* 142:    */     throws InitializationError
/* 143:    */   {
/* 144:177 */     for (Description each : description.getChildren())
/* 145:    */     {
/* 146:178 */       if (each.getAnnotation(Category.class) != null) {
/* 147:179 */         throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
/* 148:    */       }
/* 149:180 */       assertNoDescendantsHaveCategoryAnnotations(each);
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   private static boolean canHaveCategorizedChildren(Description description)
/* 154:    */   {
/* 155:187 */     for (Description each : description.getChildren()) {
/* 156:188 */       if (each.getTestClass() == null) {
/* 157:189 */         return false;
/* 158:    */       }
/* 159:    */     }
/* 160:190 */     return true;
/* 161:    */   }
/* 162:    */   
/* 163:    */   @Retention(RetentionPolicy.RUNTIME)
/* 164:    */   public static @interface ExcludeCategory
/* 165:    */   {
/* 166:    */     Class<?> value();
/* 167:    */   }
/* 168:    */   
/* 169:    */   @Retention(RetentionPolicy.RUNTIME)
/* 170:    */   public static @interface IncludeCategory
/* 171:    */   {
/* 172:    */     Class<?> value();
/* 173:    */   }
/* 174:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.categories.Categories
 * JD-Core Version:    0.7.0.1
 */