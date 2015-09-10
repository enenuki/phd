/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOneToMany;
/*  7:   */ import org.jboss.jandex.AnnotationValue;
/*  8:   */ import org.jboss.jandex.ClassInfo;
/*  9:   */ 
/* 10:   */ class OneToManyMocker
/* 11:   */   extends PropertyMocker
/* 12:   */ {
/* 13:   */   private JaxbOneToMany oneToMany;
/* 14:   */   
/* 15:   */   OneToManyMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbOneToMany oneToMany)
/* 16:   */   {
/* 17:42 */     super(indexBuilder, classInfo, defaults);
/* 18:43 */     this.oneToMany = oneToMany;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected String getFieldName()
/* 22:   */   {
/* 23:48 */     return this.oneToMany.getName();
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void processExtra()
/* 27:   */   {
/* 28:53 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 29:54 */     MockHelper.classValue("targetEntity", this.oneToMany.getTargetEntity(), annotationValueList, this.indexBuilder.getServiceRegistry());
/* 30:   */     
/* 31:   */ 
/* 32:57 */     MockHelper.enumValue("fetch", FETCH_TYPE, this.oneToMany.getFetch(), annotationValueList);
/* 33:58 */     MockHelper.stringValue("mappedBy", this.oneToMany.getMappedBy(), annotationValueList);
/* 34:59 */     MockHelper.booleanValue("orphanRemoval", this.oneToMany.isOrphanRemoval(), annotationValueList);
/* 35:60 */     MockHelper.cascadeValue("cascade", this.oneToMany.getCascade(), isDefaultCascadePersist(), annotationValueList);
/* 36:61 */     create(ONE_TO_MANY, getTarget(), annotationValueList);
/* 37:62 */     parserAttributeOverrides(this.oneToMany.getMapKeyAttributeOverride(), getTarget());
/* 38:63 */     parserMapKeyJoinColumnList(this.oneToMany.getMapKeyJoinColumn(), getTarget());
/* 39:64 */     parserMapKey(this.oneToMany.getMapKey(), getTarget());
/* 40:65 */     parserMapKeyColumn(this.oneToMany.getMapKeyColumn(), getTarget());
/* 41:66 */     parserMapKeyClass(this.oneToMany.getMapKeyClass(), getTarget());
/* 42:67 */     parserMapKeyTemporal(this.oneToMany.getMapKeyTemporal(), getTarget());
/* 43:68 */     parserMapKeyEnumerated(this.oneToMany.getMapKeyEnumerated(), getTarget());
/* 44:69 */     parserJoinColumnList(this.oneToMany.getJoinColumn(), getTarget());
/* 45:70 */     parserOrderColumn(this.oneToMany.getOrderColumn(), getTarget());
/* 46:71 */     parserJoinTable(this.oneToMany.getJoinTable(), getTarget());
/* 47:72 */     if (this.oneToMany.getOrderBy() != null) {
/* 48:73 */       create(ORDER_BY, getTarget(), MockHelper.stringValueArray("value", this.oneToMany.getOrderBy()));
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   protected JaxbAccessType getAccessType()
/* 53:   */   {
/* 54:79 */     return this.oneToMany.getAccess();
/* 55:   */   }
/* 56:   */   
/* 57:   */   protected void setAccessType(JaxbAccessType accessType)
/* 58:   */   {
/* 59:84 */     this.oneToMany.setAccess(accessType);
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.OneToManyMocker
 * JD-Core Version:    0.7.0.1
 */