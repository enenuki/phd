/*  1:   */ package org.hibernate.annotations.common.annotationfactory;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class AnnotationDescriptor
/*  8:   */ {
/*  9:   */   private final Class<? extends Annotation> type;
/* 10:45 */   private final Map<String, Object> elements = new HashMap();
/* 11:   */   
/* 12:   */   public AnnotationDescriptor(Class<? extends Annotation> annotationType)
/* 13:   */   {
/* 14:48 */     this.type = annotationType;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setValue(String elementName, Object value)
/* 18:   */   {
/* 19:52 */     this.elements.put(elementName, value);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object valueOf(String elementName)
/* 23:   */   {
/* 24:56 */     return this.elements.get(elementName);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean containsElement(String elementName)
/* 28:   */   {
/* 29:60 */     return this.elements.containsKey(elementName);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int numberOfElements()
/* 33:   */   {
/* 34:64 */     return this.elements.size();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Class<? extends Annotation> type()
/* 38:   */   {
/* 39:68 */     return this.type;
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.annotationfactory.AnnotationDescriptor
 * JD-Core Version:    0.7.0.1
 */