/*   1:    */ package org.hibernate.annotations.common.annotationfactory;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.lang.reflect.InvocationHandler;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.SortedSet;
/*  10:    */ import java.util.TreeSet;
/*  11:    */ 
/*  12:    */ public class AnnotationProxy
/*  13:    */   implements Annotation, InvocationHandler
/*  14:    */ {
/*  15:    */   private final Class<? extends Annotation> annotationType;
/*  16:    */   private final Map<Method, Object> values;
/*  17:    */   
/*  18:    */   public AnnotationProxy(AnnotationDescriptor descriptor)
/*  19:    */   {
/*  20: 68 */     this.annotationType = descriptor.type();
/*  21: 69 */     this.values = getAnnotationValues(descriptor);
/*  22:    */   }
/*  23:    */   
/*  24:    */   private Map<Method, Object> getAnnotationValues(AnnotationDescriptor descriptor)
/*  25:    */   {
/*  26: 73 */     Map<Method, Object> result = new HashMap();
/*  27: 74 */     int processedValuesFromDescriptor = 0;
/*  28: 75 */     for (Method m : this.annotationType.getDeclaredMethods()) {
/*  29: 76 */       if (descriptor.containsElement(m.getName()))
/*  30:    */       {
/*  31: 77 */         result.put(m, descriptor.valueOf(m.getName()));
/*  32: 78 */         processedValuesFromDescriptor++;
/*  33:    */       }
/*  34: 80 */       else if (m.getDefaultValue() != null)
/*  35:    */       {
/*  36: 81 */         result.put(m, m.getDefaultValue());
/*  37:    */       }
/*  38:    */       else
/*  39:    */       {
/*  40: 84 */         throw new IllegalArgumentException("No value provided for " + m.getName());
/*  41:    */       }
/*  42:    */     }
/*  43: 87 */     if (processedValuesFromDescriptor != descriptor.numberOfElements()) {
/*  44: 88 */       throw new RuntimeException("Trying to instanciate " + this.annotationType + " with unknown elements");
/*  45:    */     }
/*  46: 90 */     return result;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object invoke(Object proxy, Method method, Object[] args)
/*  50:    */     throws Throwable
/*  51:    */   {
/*  52: 94 */     if (this.values.containsKey(method)) {
/*  53: 95 */       return this.values.get(method);
/*  54:    */     }
/*  55: 97 */     return method.invoke(this, args);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Class<? extends Annotation> annotationType()
/*  59:    */   {
/*  60:101 */     return this.annotationType;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String toString()
/*  64:    */   {
/*  65:105 */     StringBuilder result = new StringBuilder();
/*  66:106 */     result.append('@').append(annotationType().getName()).append('(');
/*  67:107 */     for (Method m : getRegisteredMethodsInAlphabeticalOrder()) {
/*  68:108 */       result.append(m.getName()).append('=').append(this.values.get(m)).append(", ");
/*  69:    */     }
/*  70:111 */     if (this.values.size() > 0)
/*  71:    */     {
/*  72:112 */       result.delete(result.length() - 2, result.length());
/*  73:113 */       result.append(")");
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:116 */       result.delete(result.length() - 1, result.length());
/*  78:    */     }
/*  79:119 */     return result.toString();
/*  80:    */   }
/*  81:    */   
/*  82:    */   private SortedSet<Method> getRegisteredMethodsInAlphabeticalOrder()
/*  83:    */   {
/*  84:123 */     SortedSet<Method> result = new TreeSet(new Comparator()
/*  85:    */     {
/*  86:    */       public int compare(Method o1, Method o2)
/*  87:    */       {
/*  88:126 */         return o1.getName().compareTo(o2.getName());
/*  89:    */       }
/*  90:130 */     });
/*  91:131 */     result.addAll(this.values.keySet());
/*  92:132 */     return result;
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.annotationfactory.AnnotationProxy
 * JD-Core Version:    0.7.0.1
 */