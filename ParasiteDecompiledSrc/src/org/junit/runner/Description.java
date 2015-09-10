/*   1:    */ package org.junit.runner;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.regex.Matcher;
/*   8:    */ import java.util.regex.Pattern;
/*   9:    */ 
/*  10:    */ public class Description
/*  11:    */ {
/*  12:    */   public static Description createSuiteDescription(String name, Annotation... annotations)
/*  13:    */   {
/*  14: 37 */     if (name.length() == 0) {
/*  15: 38 */       throw new IllegalArgumentException("name must have non-zero length");
/*  16:    */     }
/*  17: 39 */     return new Description(name, annotations);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Description createTestDescription(Class<?> clazz, String name, Annotation... annotations)
/*  21:    */   {
/*  22: 51 */     return new Description(String.format("%s(%s)", new Object[] { name, clazz.getName() }), annotations);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static Description createTestDescription(Class<?> clazz, String name)
/*  26:    */   {
/*  27: 63 */     return createTestDescription(clazz, name, new Annotation[0]);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Description createSuiteDescription(Class<?> testClass)
/*  31:    */   {
/*  32: 72 */     return new Description(testClass.getName(), testClass.getAnnotations());
/*  33:    */   }
/*  34:    */   
/*  35: 78 */   public static final Description EMPTY = new Description("No Tests", new Annotation[0]);
/*  36: 85 */   public static final Description TEST_MECHANISM = new Description("Test mechanism", new Annotation[0]);
/*  37: 87 */   private final ArrayList<Description> fChildren = new ArrayList();
/*  38:    */   private final String fDisplayName;
/*  39:    */   private final Annotation[] fAnnotations;
/*  40:    */   
/*  41:    */   private Description(String displayName, Annotation... annotations)
/*  42:    */   {
/*  43: 93 */     this.fDisplayName = displayName;
/*  44: 94 */     this.fAnnotations = annotations;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getDisplayName()
/*  48:    */   {
/*  49:101 */     return this.fDisplayName;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void addChild(Description description)
/*  53:    */   {
/*  54:109 */     getChildren().add(description);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public ArrayList<Description> getChildren()
/*  58:    */   {
/*  59:116 */     return this.fChildren;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isSuite()
/*  63:    */   {
/*  64:123 */     return !isTest();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isTest()
/*  68:    */   {
/*  69:130 */     return getChildren().isEmpty();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int testCount()
/*  73:    */   {
/*  74:137 */     if (isTest()) {
/*  75:138 */       return 1;
/*  76:    */     }
/*  77:139 */     int result = 0;
/*  78:140 */     for (Description child : getChildren()) {
/*  79:141 */       result += child.testCount();
/*  80:    */     }
/*  81:142 */     return result;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int hashCode()
/*  85:    */   {
/*  86:147 */     return getDisplayName().hashCode();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean equals(Object obj)
/*  90:    */   {
/*  91:152 */     if (!(obj instanceof Description)) {
/*  92:153 */       return false;
/*  93:    */     }
/*  94:154 */     Description d = (Description)obj;
/*  95:155 */     return (getDisplayName().equals(d.getDisplayName())) && (getChildren().equals(d.getChildren()));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String toString()
/*  99:    */   {
/* 100:161 */     return getDisplayName();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isEmpty()
/* 104:    */   {
/* 105:168 */     return equals(EMPTY);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Description childlessCopy()
/* 109:    */   {
/* 110:176 */     return new Description(this.fDisplayName, this.fAnnotations);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public <T extends Annotation> T getAnnotation(Class<T> annotationType)
/* 114:    */   {
/* 115:184 */     for (Annotation each : this.fAnnotations) {
/* 116:185 */       if (each.annotationType().equals(annotationType)) {
/* 117:186 */         return (Annotation)annotationType.cast(each);
/* 118:    */       }
/* 119:    */     }
/* 120:187 */     return null;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Collection<Annotation> getAnnotations()
/* 124:    */   {
/* 125:194 */     return Arrays.asList(this.fAnnotations);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Class<?> getTestClass()
/* 129:    */   {
/* 130:202 */     String name = getClassName();
/* 131:203 */     if (name == null) {
/* 132:204 */       return null;
/* 133:    */     }
/* 134:    */     try
/* 135:    */     {
/* 136:206 */       return Class.forName(name);
/* 137:    */     }
/* 138:    */     catch (ClassNotFoundException e) {}
/* 139:208 */     return null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getClassName()
/* 143:    */   {
/* 144:217 */     Matcher matcher = methodStringMatcher();
/* 145:218 */     return matcher.matches() ? matcher.group(2) : toString();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getMethodName()
/* 149:    */   {
/* 150:228 */     return parseMethod();
/* 151:    */   }
/* 152:    */   
/* 153:    */   private String parseMethod()
/* 154:    */   {
/* 155:232 */     Matcher matcher = methodStringMatcher();
/* 156:233 */     if (matcher.matches()) {
/* 157:234 */       return matcher.group(1);
/* 158:    */     }
/* 159:235 */     return null;
/* 160:    */   }
/* 161:    */   
/* 162:    */   private Matcher methodStringMatcher()
/* 163:    */   {
/* 164:239 */     return Pattern.compile("(.*)\\((.*)\\)").matcher(toString());
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.Description
 * JD-Core Version:    0.7.0.1
 */