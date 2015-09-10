/*  1:   */ package org.apache.xalan.trace;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Constructor;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import org.apache.xalan.transformer.TransformerImpl;
/*  6:   */ 
/*  7:   */ public class ExtensionEvent
/*  8:   */ {
/*  9:   */   public static final int DEFAULT_CONSTRUCTOR = 0;
/* 10:   */   public static final int METHOD = 1;
/* 11:   */   public static final int CONSTRUCTOR = 2;
/* 12:   */   public final int m_callType;
/* 13:   */   public final TransformerImpl m_transformer;
/* 14:   */   public final Object m_method;
/* 15:   */   public final Object m_instance;
/* 16:   */   public final Object[] m_arguments;
/* 17:   */   
/* 18:   */   public ExtensionEvent(TransformerImpl transformer, Method method, Object instance, Object[] arguments)
/* 19:   */   {
/* 20:46 */     this.m_transformer = transformer;
/* 21:47 */     this.m_method = method;
/* 22:48 */     this.m_instance = instance;
/* 23:49 */     this.m_arguments = arguments;
/* 24:50 */     this.m_callType = 1;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public ExtensionEvent(TransformerImpl transformer, Constructor constructor, Object[] arguments)
/* 28:   */   {
/* 29:54 */     this.m_transformer = transformer;
/* 30:55 */     this.m_instance = null;
/* 31:56 */     this.m_arguments = arguments;
/* 32:57 */     this.m_method = constructor;
/* 33:58 */     this.m_callType = 2;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public ExtensionEvent(TransformerImpl transformer, Class clazz)
/* 37:   */   {
/* 38:62 */     this.m_transformer = transformer;
/* 39:63 */     this.m_instance = null;
/* 40:64 */     this.m_arguments = null;
/* 41:65 */     this.m_method = clazz;
/* 42:66 */     this.m_callType = 0;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.ExtensionEvent
 * JD-Core Version:    0.7.0.1
 */