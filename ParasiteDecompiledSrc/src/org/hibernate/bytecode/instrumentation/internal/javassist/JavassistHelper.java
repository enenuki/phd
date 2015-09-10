/*  1:   */ package org.hibernate.bytecode.instrumentation.internal.javassist;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.hibernate.bytecode.instrumentation.spi.FieldInterceptor;
/*  5:   */ import org.hibernate.bytecode.internal.javassist.FieldHandled;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ 
/*  8:   */ public class JavassistHelper
/*  9:   */ {
/* 10:   */   public static FieldInterceptor extractFieldInterceptor(Object entity)
/* 11:   */   {
/* 12:40 */     return (FieldInterceptor)((FieldHandled)entity).getFieldHandler();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static FieldInterceptor injectFieldInterceptor(Object entity, String entityName, Set uninitializedFieldNames, SessionImplementor session)
/* 16:   */   {
/* 17:48 */     FieldInterceptorImpl fieldInterceptor = new FieldInterceptorImpl(session, uninitializedFieldNames, entityName);
/* 18:49 */     ((FieldHandled)entity).setFieldHandler(fieldInterceptor);
/* 19:50 */     return fieldInterceptor;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.instrumentation.internal.javassist.JavassistHelper
 * JD-Core Version:    0.7.0.1
 */