/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute.type;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.AnnotationException;
/*   6:    */ import org.hibernate.AssertionFailure;
/*   7:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*   8:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*   9:    */ import org.hibernate.metamodel.source.annotations.attribute.MappedAttribute;
/*  10:    */ import org.jboss.jandex.AnnotationInstance;
/*  11:    */ 
/*  12:    */ public class EnumeratedTypeResolver
/*  13:    */   extends AbstractAttributeTypeResolver
/*  14:    */ {
/*  15:    */   private final MappedAttribute mappedAttribute;
/*  16:    */   private final boolean isMapKey;
/*  17:    */   
/*  18:    */   public EnumeratedTypeResolver(MappedAttribute mappedAttribute)
/*  19:    */   {
/*  20: 48 */     if (mappedAttribute == null) {
/*  21: 49 */       throw new AssertionFailure("MappedAttribute is null");
/*  22:    */     }
/*  23: 51 */     this.mappedAttribute = mappedAttribute;
/*  24: 52 */     this.isMapKey = false;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected AnnotationInstance getTypeDeterminingAnnotationInstance()
/*  28:    */   {
/*  29: 57 */     return JandexHelper.getSingleAnnotation(this.mappedAttribute.annotations(), JPADotNames.ENUMERATED);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String resolveHibernateTypeName(AnnotationInstance enumeratedAnnotation)
/*  33:    */   {
/*  34: 65 */     boolean isEnum = this.mappedAttribute.getAttributeType().isEnum();
/*  35: 66 */     if (!isEnum)
/*  36:    */     {
/*  37: 67 */       if (enumeratedAnnotation != null) {
/*  38: 68 */         throw new AnnotationException("Attribute " + this.mappedAttribute.getName() + " is not a Enumerated type, but has a @Enumerated annotation.");
/*  39:    */       }
/*  40: 71 */       return null;
/*  41:    */     }
/*  42: 74 */     return org.hibernate.type.EnumType.class.getName();
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected Map<String, String> resolveHibernateTypeParameters(AnnotationInstance annotationInstance)
/*  46:    */   {
/*  47: 79 */     HashMap<String, String> typeParameters = new HashMap();
/*  48: 80 */     typeParameters.put("enumClass", this.mappedAttribute.getAttributeType().getName());
/*  49: 81 */     if (annotationInstance != null)
/*  50:    */     {
/*  51: 82 */       javax.persistence.EnumType enumType = (javax.persistence.EnumType)JandexHelper.getEnumValue(annotationInstance, "value", javax.persistence.EnumType.class);
/*  52: 87 */       if (javax.persistence.EnumType.ORDINAL.equals(enumType)) {
/*  53: 88 */         typeParameters.put("type", String.valueOf(4));
/*  54: 90 */       } else if (javax.persistence.EnumType.STRING.equals(enumType)) {
/*  55: 91 */         typeParameters.put("type", String.valueOf(12));
/*  56:    */       } else {
/*  57: 94 */         throw new AssertionFailure("Unknown EnumType: " + enumType);
/*  58:    */       }
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 98 */       typeParameters.put("type", String.valueOf(4));
/*  63:    */     }
/*  64:100 */     return typeParameters;
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.type.EnumeratedTypeResolver
 * JD-Core Version:    0.7.0.1
 */