/*   1:    */ package org.hibernate.bytecode.instrumentation.internal;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Set;
/*   5:    */ import org.hibernate.bytecode.instrumentation.internal.javassist.JavassistHelper;
/*   6:    */ import org.hibernate.bytecode.instrumentation.spi.FieldInterceptor;
/*   7:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   8:    */ 
/*   9:    */ public class FieldInterceptionHelper
/*  10:    */ {
/*  11: 42 */   private static final Set<Delegate> INSTRUMENTATION_DELEGATES = ;
/*  12:    */   
/*  13:    */   private static Set<Delegate> buildInstrumentationDelegates()
/*  14:    */   {
/*  15: 45 */     HashSet<Delegate> delegates = new HashSet();
/*  16: 46 */     delegates.add(JavassistDelegate.INSTANCE);
/*  17: 47 */     return delegates;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static boolean isInstrumented(Class entityClass)
/*  21:    */   {
/*  22: 54 */     for (Delegate delegate : INSTRUMENTATION_DELEGATES) {
/*  23: 55 */       if (delegate.isInstrumented(entityClass)) {
/*  24: 56 */         return true;
/*  25:    */       }
/*  26:    */     }
/*  27: 59 */     return false;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static boolean isInstrumented(Object entity)
/*  31:    */   {
/*  32: 63 */     return (entity != null) && (isInstrumented(entity.getClass()));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static FieldInterceptor extractFieldInterceptor(Object entity)
/*  36:    */   {
/*  37: 67 */     if (entity == null) {
/*  38: 68 */       return null;
/*  39:    */     }
/*  40: 70 */     FieldInterceptor interceptor = null;
/*  41: 71 */     for (Delegate delegate : INSTRUMENTATION_DELEGATES)
/*  42:    */     {
/*  43: 72 */       interceptor = delegate.extractInterceptor(entity);
/*  44: 73 */       if (interceptor != null) {
/*  45:    */         break;
/*  46:    */       }
/*  47:    */     }
/*  48: 77 */     return interceptor;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static FieldInterceptor injectFieldInterceptor(Object entity, String entityName, Set uninitializedFieldNames, SessionImplementor session)
/*  52:    */   {
/*  53: 86 */     if (entity == null) {
/*  54: 87 */       return null;
/*  55:    */     }
/*  56: 89 */     FieldInterceptor interceptor = null;
/*  57: 90 */     for (Delegate delegate : INSTRUMENTATION_DELEGATES)
/*  58:    */     {
/*  59: 91 */       interceptor = delegate.injectInterceptor(entity, entityName, uninitializedFieldNames, session);
/*  60: 92 */       if (interceptor != null) {
/*  61:    */         break;
/*  62:    */       }
/*  63:    */     }
/*  64: 96 */     return interceptor;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void clearDirty(Object entity)
/*  68:    */   {
/*  69:100 */     FieldInterceptor interceptor = extractFieldInterceptor(entity);
/*  70:101 */     if (interceptor != null) {
/*  71:102 */       interceptor.clearDirty();
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void markDirty(Object entity)
/*  76:    */   {
/*  77:107 */     FieldInterceptor interceptor = extractFieldInterceptor(entity);
/*  78:108 */     if (interceptor != null) {
/*  79:109 */       interceptor.dirty();
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private static class JavassistDelegate
/*  84:    */     implements FieldInterceptionHelper.Delegate
/*  85:    */   {
/*  86:120 */     public static final JavassistDelegate INSTANCE = new JavassistDelegate();
/*  87:    */     public static final String MARKER = "org.hibernate.bytecode.internal.javassist.FieldHandled";
/*  88:    */     
/*  89:    */     public boolean isInstrumented(Class classToCheck)
/*  90:    */     {
/*  91:125 */       for (Class definedInterface : classToCheck.getInterfaces()) {
/*  92:126 */         if ("org.hibernate.bytecode.internal.javassist.FieldHandled".equals(definedInterface.getName())) {
/*  93:127 */           return true;
/*  94:    */         }
/*  95:    */       }
/*  96:130 */       return false;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public FieldInterceptor extractInterceptor(Object entity)
/* 100:    */     {
/* 101:135 */       for (Class definedInterface : entity.getClass().getInterfaces()) {
/* 102:136 */         if ("org.hibernate.bytecode.internal.javassist.FieldHandled".equals(definedInterface.getName())) {
/* 103:137 */           return JavassistHelper.extractFieldInterceptor(entity);
/* 104:    */         }
/* 105:    */       }
/* 106:140 */       return null;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public FieldInterceptor injectInterceptor(Object entity, String entityName, Set uninitializedFieldNames, SessionImplementor session)
/* 110:    */     {
/* 111:149 */       for (Class definedInterface : entity.getClass().getInterfaces()) {
/* 112:150 */         if ("org.hibernate.bytecode.internal.javassist.FieldHandled".equals(definedInterface.getName())) {
/* 113:151 */           return JavassistHelper.injectFieldInterceptor(entity, entityName, uninitializedFieldNames, session);
/* 114:    */         }
/* 115:    */       }
/* 116:154 */       return null;
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   private static abstract interface Delegate
/* 121:    */   {
/* 122:    */     public abstract boolean isInstrumented(Class paramClass);
/* 123:    */     
/* 124:    */     public abstract FieldInterceptor extractInterceptor(Object paramObject);
/* 125:    */     
/* 126:    */     public abstract FieldInterceptor injectInterceptor(Object paramObject, String paramString, Set paramSet, SessionImplementor paramSessionImplementor);
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper
 * JD-Core Version:    0.7.0.1
 */