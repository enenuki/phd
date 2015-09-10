/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import javax.persistence.ManyToOne;
/*  4:   */ import javax.persistence.OneToOne;
/*  5:   */ import org.hibernate.AssertionFailure;
/*  6:   */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  7:   */ import org.hibernate.annotations.common.reflection.XClass;
/*  8:   */ import org.hibernate.annotations.common.reflection.XProperty;
/*  9:   */ 
/* 10:   */ public class ToOneBinder
/* 11:   */ {
/* 12:   */   public static String getReferenceEntityName(PropertyData propertyData, XClass targetEntity, Mappings mappings)
/* 13:   */   {
/* 14:41 */     if (AnnotationBinder.isDefault(targetEntity, mappings)) {
/* 15:42 */       return propertyData.getClassOrElementName();
/* 16:   */     }
/* 17:45 */     return targetEntity.getName();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static String getReferenceEntityName(PropertyData propertyData, Mappings mappings)
/* 21:   */   {
/* 22:50 */     XClass targetEntity = getTargetEntity(propertyData, mappings);
/* 23:51 */     if (AnnotationBinder.isDefault(targetEntity, mappings)) {
/* 24:52 */       return propertyData.getClassOrElementName();
/* 25:   */     }
/* 26:55 */     return targetEntity.getName();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static XClass getTargetEntity(PropertyData propertyData, Mappings mappings)
/* 30:   */   {
/* 31:60 */     XProperty property = propertyData.getProperty();
/* 32:61 */     return mappings.getReflectionManager().toXClass(getTargetEntityClass(property));
/* 33:   */   }
/* 34:   */   
/* 35:   */   private static Class<?> getTargetEntityClass(XProperty property)
/* 36:   */   {
/* 37:65 */     ManyToOne mTo = (ManyToOne)property.getAnnotation(ManyToOne.class);
/* 38:66 */     if (mTo != null) {
/* 39:67 */       return mTo.targetEntity();
/* 40:   */     }
/* 41:69 */     OneToOne oTo = (OneToOne)property.getAnnotation(OneToOne.class);
/* 42:70 */     if (oTo != null) {
/* 43:71 */       return oTo.targetEntity();
/* 44:   */     }
/* 45:73 */     throw new AssertionFailure("Unexpected discovery of a targetEntity: " + property.getName());
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ToOneBinder
 * JD-Core Version:    0.7.0.1
 */