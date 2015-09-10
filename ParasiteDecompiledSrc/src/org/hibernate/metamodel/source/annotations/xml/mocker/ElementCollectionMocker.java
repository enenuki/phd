/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbElementCollection;
/*  7:   */ import org.jboss.jandex.AnnotationValue;
/*  8:   */ import org.jboss.jandex.ClassInfo;
/*  9:   */ 
/* 10:   */ class ElementCollectionMocker
/* 11:   */   extends PropertyMocker
/* 12:   */ {
/* 13:   */   private JaxbElementCollection elementCollection;
/* 14:   */   
/* 15:   */   ElementCollectionMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbElementCollection elementCollection)
/* 16:   */   {
/* 17:42 */     super(indexBuilder, classInfo, defaults);
/* 18:43 */     this.elementCollection = elementCollection;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void processExtra()
/* 22:   */   {
/* 23:48 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 24:49 */     MockHelper.classValue("targetClass", this.elementCollection.getTargetClass(), annotationValueList, this.indexBuilder.getServiceRegistry());
/* 25:   */     
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:55 */     MockHelper.enumValue("fetch", FETCH_TYPE, this.elementCollection.getFetch(), annotationValueList);
/* 31:56 */     create(ELEMENT_COLLECTION, annotationValueList);
/* 32:57 */     parserLob(this.elementCollection.getLob(), getTarget());
/* 33:58 */     parserEnumType(this.elementCollection.getEnumerated(), getTarget());
/* 34:59 */     parserColumn(this.elementCollection.getColumn(), getTarget());
/* 35:60 */     parserTemporalType(this.elementCollection.getTemporal(), getTarget());
/* 36:61 */     parserCollectionTable(this.elementCollection.getCollectionTable(), getTarget());
/* 37:62 */     parserAssociationOverrides(this.elementCollection.getAssociationOverride(), getTarget());
/* 38:63 */     parserAttributeOverrides(this.elementCollection.getAttributeOverride(), getTarget());
/* 39:64 */     if (this.elementCollection.getOrderBy() != null) {
/* 40:65 */       create(ORDER_BY, MockHelper.stringValueArray("value", this.elementCollection.getOrderBy()));
/* 41:   */     }
/* 42:67 */     parserAttributeOverrides(this.elementCollection.getMapKeyAttributeOverride(), getTarget());
/* 43:68 */     parserMapKeyJoinColumnList(this.elementCollection.getMapKeyJoinColumn(), getTarget());
/* 44:69 */     parserMapKey(this.elementCollection.getMapKey(), getTarget());
/* 45:70 */     parserMapKeyColumn(this.elementCollection.getMapKeyColumn(), getTarget());
/* 46:71 */     parserMapKeyClass(this.elementCollection.getMapKeyClass(), getTarget());
/* 47:72 */     parserMapKeyEnumerated(this.elementCollection.getMapKeyEnumerated(), getTarget());
/* 48:73 */     parserMapKeyTemporal(this.elementCollection.getMapKeyTemporal(), getTarget());
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected String getFieldName()
/* 52:   */   {
/* 53:78 */     return this.elementCollection.getName();
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected JaxbAccessType getAccessType()
/* 57:   */   {
/* 58:83 */     return this.elementCollection.getAccess();
/* 59:   */   }
/* 60:   */   
/* 61:   */   protected void setAccessType(JaxbAccessType accessType)
/* 62:   */   {
/* 63:88 */     this.elementCollection.setAccess(accessType);
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.ElementCollectionMocker
 * JD-Core Version:    0.7.0.1
 */