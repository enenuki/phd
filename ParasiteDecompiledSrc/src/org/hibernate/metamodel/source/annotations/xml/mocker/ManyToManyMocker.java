/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbManyToMany;
/*  7:   */ import org.jboss.jandex.AnnotationValue;
/*  8:   */ import org.jboss.jandex.ClassInfo;
/*  9:   */ 
/* 10:   */ class ManyToManyMocker
/* 11:   */   extends PropertyMocker
/* 12:   */ {
/* 13:   */   private JaxbManyToMany manyToMany;
/* 14:   */   
/* 15:   */   ManyToManyMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbManyToMany manyToMany)
/* 16:   */   {
/* 17:42 */     super(indexBuilder, classInfo, defaults);
/* 18:43 */     this.manyToMany = manyToMany;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected String getFieldName()
/* 22:   */   {
/* 23:48 */     return this.manyToMany.getName();
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void processExtra()
/* 27:   */   {
/* 28:54 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 29:55 */     MockHelper.classValue("targetEntity", this.manyToMany.getTargetEntity(), annotationValueList, this.indexBuilder.getServiceRegistry());
/* 30:   */     
/* 31:   */ 
/* 32:58 */     MockHelper.enumValue("fetch", FETCH_TYPE, this.manyToMany.getFetch(), annotationValueList);
/* 33:59 */     MockHelper.stringValue("mappedBy", this.manyToMany.getMappedBy(), annotationValueList);
/* 34:60 */     MockHelper.cascadeValue("cascade", this.manyToMany.getCascade(), isDefaultCascadePersist(), annotationValueList);
/* 35:61 */     create(MANY_TO_MANY, annotationValueList);
/* 36:62 */     parserMapKeyClass(this.manyToMany.getMapKeyClass(), getTarget());
/* 37:63 */     parserMapKeyTemporal(this.manyToMany.getMapKeyTemporal(), getTarget());
/* 38:64 */     parserMapKeyEnumerated(this.manyToMany.getMapKeyEnumerated(), getTarget());
/* 39:65 */     parserMapKey(this.manyToMany.getMapKey(), getTarget());
/* 40:66 */     parserAttributeOverrides(this.manyToMany.getMapKeyAttributeOverride(), getTarget());
/* 41:67 */     parserMapKeyJoinColumnList(this.manyToMany.getMapKeyJoinColumn(), getTarget());
/* 42:68 */     parserOrderColumn(this.manyToMany.getOrderColumn(), getTarget());
/* 43:69 */     parserJoinTable(this.manyToMany.getJoinTable(), getTarget());
/* 44:70 */     if (this.manyToMany.getOrderBy() != null) {
/* 45:71 */       create(ORDER_BY, MockHelper.stringValueArray("value", this.manyToMany.getOrderBy()));
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected JaxbAccessType getAccessType()
/* 50:   */   {
/* 51:77 */     return this.manyToMany.getAccess();
/* 52:   */   }
/* 53:   */   
/* 54:   */   protected void setAccessType(JaxbAccessType accessType)
/* 55:   */   {
/* 56:82 */     this.manyToMany.setAccess(accessType);
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.ManyToManyMocker
 * JD-Core Version:    0.7.0.1
 */