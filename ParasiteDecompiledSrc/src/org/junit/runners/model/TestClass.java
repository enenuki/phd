/*   1:    */ package org.junit.runners.model;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.Field;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.junit.Assert;
/*  12:    */ import org.junit.Before;
/*  13:    */ import org.junit.BeforeClass;
/*  14:    */ 
/*  15:    */ public class TestClass
/*  16:    */ {
/*  17:    */   private final Class<?> fClass;
/*  18: 22 */   private Map<Class<?>, List<FrameworkMethod>> fMethodsForAnnotations = new HashMap();
/*  19: 24 */   private Map<Class<?>, List<FrameworkField>> fFieldsForAnnotations = new HashMap();
/*  20:    */   
/*  21:    */   public TestClass(Class<?> klass)
/*  22:    */   {
/*  23: 33 */     this.fClass = klass;
/*  24: 34 */     if ((klass != null) && (klass.getConstructors().length > 1)) {
/*  25: 35 */       throw new IllegalArgumentException("Test class can only have one constructor");
/*  26:    */     }
/*  27: 38 */     for (Class<?> eachClass : getSuperClasses(this.fClass))
/*  28:    */     {
/*  29: 39 */       for (Method eachMethod : eachClass.getDeclaredMethods()) {
/*  30: 40 */         addToAnnotationLists(new FrameworkMethod(eachMethod), this.fMethodsForAnnotations);
/*  31:    */       }
/*  32: 42 */       for (Field eachField : eachClass.getDeclaredFields()) {
/*  33: 43 */         addToAnnotationLists(new FrameworkField(eachField), this.fFieldsForAnnotations);
/*  34:    */       }
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   private <T extends FrameworkMember<T>> void addToAnnotationLists(T member, Map<Class<?>, List<T>> map)
/*  39:    */   {
/*  40: 50 */     for (Annotation each : member.getAnnotations())
/*  41:    */     {
/*  42: 51 */       Class<? extends Annotation> type = each.annotationType();
/*  43: 52 */       List<T> members = getAnnotatedMembers(map, type);
/*  44: 53 */       if (member.isShadowedBy(members)) {
/*  45: 54 */         return;
/*  46:    */       }
/*  47: 55 */       if (runsTopToBottom(type)) {
/*  48: 56 */         members.add(0, member);
/*  49:    */       } else {
/*  50: 58 */         members.add(member);
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<FrameworkMethod> getAnnotatedMethods(Class<? extends Annotation> annotationClass)
/*  56:    */   {
/*  57: 68 */     return getAnnotatedMembers(this.fMethodsForAnnotations, annotationClass);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<FrameworkField> getAnnotatedFields(Class<? extends Annotation> annotationClass)
/*  61:    */   {
/*  62: 77 */     return getAnnotatedMembers(this.fFieldsForAnnotations, annotationClass);
/*  63:    */   }
/*  64:    */   
/*  65:    */   private <T> List<T> getAnnotatedMembers(Map<Class<?>, List<T>> map, Class<? extends Annotation> type)
/*  66:    */   {
/*  67: 82 */     if (!map.containsKey(type)) {
/*  68: 83 */       map.put(type, new ArrayList());
/*  69:    */     }
/*  70: 84 */     return (List)map.get(type);
/*  71:    */   }
/*  72:    */   
/*  73:    */   private boolean runsTopToBottom(Class<? extends Annotation> annotation)
/*  74:    */   {
/*  75: 88 */     return (annotation.equals(Before.class)) || (annotation.equals(BeforeClass.class));
/*  76:    */   }
/*  77:    */   
/*  78:    */   private List<Class<?>> getSuperClasses(Class<?> testClass)
/*  79:    */   {
/*  80: 93 */     ArrayList<Class<?>> results = new ArrayList();
/*  81: 94 */     Class<?> current = testClass;
/*  82: 95 */     while (current != null)
/*  83:    */     {
/*  84: 96 */       results.add(current);
/*  85: 97 */       current = current.getSuperclass();
/*  86:    */     }
/*  87: 99 */     return results;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Class<?> getJavaClass()
/*  91:    */   {
/*  92:106 */     return this.fClass;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getName()
/*  96:    */   {
/*  97:113 */     if (this.fClass == null) {
/*  98:114 */       return "null";
/*  99:    */     }
/* 100:115 */     return this.fClass.getName();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Constructor<?> getOnlyConstructor()
/* 104:    */   {
/* 105:124 */     Constructor<?>[] constructors = this.fClass.getConstructors();
/* 106:125 */     Assert.assertEquals(1L, constructors.length);
/* 107:126 */     return constructors[0];
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Annotation[] getAnnotations()
/* 111:    */   {
/* 112:133 */     if (this.fClass == null) {
/* 113:134 */       return new Annotation[0];
/* 114:    */     }
/* 115:135 */     return this.fClass.getAnnotations();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public <T> List<T> getAnnotatedFieldValues(Object test, Class<? extends Annotation> annotationClass, Class<T> valueClass)
/* 119:    */   {
/* 120:140 */     List<T> results = new ArrayList();
/* 121:141 */     for (FrameworkField each : getAnnotatedFields(annotationClass)) {
/* 122:    */       try
/* 123:    */       {
/* 124:143 */         Object fieldValue = each.get(test);
/* 125:144 */         if (valueClass.isInstance(fieldValue)) {
/* 126:145 */           results.add(valueClass.cast(fieldValue));
/* 127:    */         }
/* 128:    */       }
/* 129:    */       catch (IllegalAccessException e)
/* 130:    */       {
/* 131:147 */         throw new RuntimeException("How did getFields return a field we couldn't access?");
/* 132:    */       }
/* 133:    */     }
/* 134:151 */     return results;
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.model.TestClass
 * JD-Core Version:    0.7.0.1
 */