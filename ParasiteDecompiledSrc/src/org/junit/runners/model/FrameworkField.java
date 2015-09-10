/*  1:   */ package org.junit.runners.model;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.lang.reflect.Field;
/*  5:   */ 
/*  6:   */ public class FrameworkField
/*  7:   */   extends FrameworkMember<FrameworkField>
/*  8:   */ {
/*  9:   */   private final Field fField;
/* 10:   */   
/* 11:   */   FrameworkField(Field field)
/* 12:   */   {
/* 13:16 */     this.fField = field;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Annotation[] getAnnotations()
/* 17:   */   {
/* 18:21 */     return this.fField.getAnnotations();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isShadowedBy(FrameworkField otherMember)
/* 22:   */   {
/* 23:26 */     return otherMember.getField().getName().equals(getField().getName());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Field getField()
/* 27:   */   {
/* 28:33 */     return this.fField;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Class<?> getType()
/* 32:   */   {
/* 33:41 */     return this.fField.getType();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Object get(Object target)
/* 37:   */     throws IllegalArgumentException, IllegalAccessException
/* 38:   */   {
/* 39:48 */     return this.fField.get(target);
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.model.FrameworkField
 * JD-Core Version:    0.7.0.1
 */