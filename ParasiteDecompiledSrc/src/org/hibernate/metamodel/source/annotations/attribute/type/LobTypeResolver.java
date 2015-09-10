/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.Blob;
/*   5:    */ import java.sql.Clob;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  10:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  11:    */ import org.hibernate.metamodel.source.annotations.attribute.MappedAttribute;
/*  12:    */ import org.hibernate.type.BlobType;
/*  13:    */ import org.hibernate.type.CharacterArrayClobType;
/*  14:    */ import org.hibernate.type.ClobType;
/*  15:    */ import org.hibernate.type.MaterializedBlobType;
/*  16:    */ import org.hibernate.type.MaterializedClobType;
/*  17:    */ import org.hibernate.type.PrimitiveCharacterArrayClobType;
/*  18:    */ import org.hibernate.type.SerializableToBlobType;
/*  19:    */ import org.hibernate.type.StandardBasicTypes;
/*  20:    */ import org.hibernate.type.WrappedMaterializedBlobType;
/*  21:    */ import org.jboss.jandex.AnnotationInstance;
/*  22:    */ 
/*  23:    */ public class LobTypeResolver
/*  24:    */   extends AbstractAttributeTypeResolver
/*  25:    */ {
/*  26:    */   private final MappedAttribute mappedAttribute;
/*  27:    */   
/*  28:    */   public LobTypeResolver(MappedAttribute mappedAttribute)
/*  29:    */   {
/*  30: 52 */     if (mappedAttribute == null) {
/*  31: 53 */       throw new AssertionFailure("MappedAttribute is null");
/*  32:    */     }
/*  33: 55 */     this.mappedAttribute = mappedAttribute;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected AnnotationInstance getTypeDeterminingAnnotationInstance()
/*  37:    */   {
/*  38: 60 */     return JandexHelper.getSingleAnnotation(this.mappedAttribute.annotations(), JPADotNames.LOB);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String resolveHibernateTypeName(AnnotationInstance annotationInstance)
/*  42:    */   {
/*  43: 65 */     if (annotationInstance == null) {
/*  44: 66 */       return null;
/*  45:    */     }
/*  46: 68 */     String type = null;
/*  47: 69 */     if (Clob.class.isAssignableFrom(this.mappedAttribute.getAttributeType())) {
/*  48: 70 */       type = StandardBasicTypes.CLOB.getName();
/*  49: 72 */     } else if (Blob.class.isAssignableFrom(this.mappedAttribute.getAttributeType())) {
/*  50: 73 */       type = StandardBasicTypes.BLOB.getName();
/*  51: 75 */     } else if (String.class.isAssignableFrom(this.mappedAttribute.getAttributeType())) {
/*  52: 76 */       type = StandardBasicTypes.MATERIALIZED_CLOB.getName();
/*  53: 78 */     } else if ([Ljava.lang.Character.class.isAssignableFrom(this.mappedAttribute.getAttributeType())) {
/*  54: 79 */       type = CharacterArrayClobType.class.getName();
/*  55: 81 */     } else if ([C.class.isAssignableFrom(this.mappedAttribute.getAttributeType())) {
/*  56: 82 */       type = PrimitiveCharacterArrayClobType.class.getName();
/*  57: 84 */     } else if ([Ljava.lang.Byte.class.isAssignableFrom(this.mappedAttribute.getAttributeType())) {
/*  58: 85 */       type = WrappedMaterializedBlobType.class.getName();
/*  59: 87 */     } else if ([B.class.isAssignableFrom(this.mappedAttribute.getAttributeType())) {
/*  60: 88 */       type = StandardBasicTypes.MATERIALIZED_BLOB.getName();
/*  61: 90 */     } else if (Serializable.class.isAssignableFrom(this.mappedAttribute.getAttributeType())) {
/*  62: 91 */       type = SerializableToBlobType.class.getName();
/*  63:    */     } else {
/*  64: 94 */       type = "blob";
/*  65:    */     }
/*  66: 96 */     return type;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected Map<String, String> resolveHibernateTypeParameters(AnnotationInstance annotationInstance)
/*  70:    */   {
/*  71:101 */     if (getExplicitHibernateTypeName().equals(SerializableToBlobType.class.getName()))
/*  72:    */     {
/*  73:102 */       HashMap<String, String> typeParameters = new HashMap();
/*  74:103 */       typeParameters.put("classname", this.mappedAttribute.getAttributeType().getName());
/*  75:    */       
/*  76:    */ 
/*  77:    */ 
/*  78:107 */       return typeParameters;
/*  79:    */     }
/*  80:109 */     return null;
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.type.LobTypeResolver
 * JD-Core Version:    0.7.0.1
 */