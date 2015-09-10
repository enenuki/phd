/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  4:   */ import org.jboss.jandex.DotName;
/*  5:   */ 
/*  6:   */ public enum AttributeNature
/*  7:   */ {
/*  8:36 */   BASIC(JPADotNames.BASIC),  ONE_TO_ONE(JPADotNames.ONE_TO_ONE),  ONE_TO_MANY(JPADotNames.ONE_TO_MANY),  MANY_TO_ONE(JPADotNames.MANY_TO_ONE),  MANY_TO_MANY(JPADotNames.MANY_TO_MANY),  ELEMENT_COLLECTION(JPADotNames.ELEMENT_COLLECTION),  EMBEDDED_ID(JPADotNames.EMBEDDED_ID),  EMBEDDED(JPADotNames.EMBEDDED);
/*  9:   */   
/* 10:   */   private final DotName annotationDotName;
/* 11:   */   
/* 12:   */   private AttributeNature(DotName annotationDotName)
/* 13:   */   {
/* 14:48 */     this.annotationDotName = annotationDotName;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public DotName getAnnotationDotName()
/* 18:   */   {
/* 19:52 */     return this.annotationDotName;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.AttributeNature
 * JD-Core Version:    0.7.0.1
 */