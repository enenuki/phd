/*  1:   */ package org.junit.runners.model;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.util.List;
/*  5:   */ 
/*  6:   */ abstract class FrameworkMember<T extends FrameworkMember<T>>
/*  7:   */ {
/*  8:   */   abstract Annotation[] getAnnotations();
/*  9:   */   
/* 10:   */   abstract boolean isShadowedBy(T paramT);
/* 11:   */   
/* 12:   */   boolean isShadowedBy(List<T> members)
/* 13:   */   {
/* 14:15 */     for (T each : members) {
/* 15:16 */       if (isShadowedBy(each)) {
/* 16:17 */         return true;
/* 17:   */       }
/* 18:   */     }
/* 19:18 */     return false;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.model.FrameworkMember
 * JD-Core Version:    0.7.0.1
 */