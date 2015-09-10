/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.AnnotatedElement;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.annotations.common.reflection.AnnotationReader;
/*  7:   */ import org.hibernate.annotations.common.reflection.MetadataProvider;
/*  8:   */ 
/*  9:   */ public class JavaMetadataProvider
/* 10:   */   implements MetadataProvider
/* 11:   */ {
/* 12:   */   public Map<Object, Object> getDefaults()
/* 13:   */   {
/* 14:16 */     return Collections.emptyMap();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public AnnotationReader getAnnotationReader(AnnotatedElement annotatedElement)
/* 18:   */   {
/* 19:20 */     return new JavaAnnotationReader(annotatedElement);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaMetadataProvider
 * JD-Core Version:    0.7.0.1
 */