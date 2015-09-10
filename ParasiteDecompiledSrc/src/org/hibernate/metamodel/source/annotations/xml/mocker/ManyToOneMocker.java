/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbManyToOne;
/*  7:   */ import org.jboss.jandex.AnnotationValue;
/*  8:   */ import org.jboss.jandex.ClassInfo;
/*  9:   */ 
/* 10:   */ class ManyToOneMocker
/* 11:   */   extends PropertyMocker
/* 12:   */ {
/* 13:   */   private JaxbManyToOne manyToOne;
/* 14:   */   
/* 15:   */   ManyToOneMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbManyToOne manyToOne)
/* 16:   */   {
/* 17:42 */     super(indexBuilder, classInfo, defaults);
/* 18:43 */     this.manyToOne = manyToOne;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected String getFieldName()
/* 22:   */   {
/* 23:48 */     return this.manyToOne.getName();
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void processExtra()
/* 27:   */   {
/* 28:53 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 29:54 */     MockHelper.classValue("targetEntity", this.manyToOne.getTargetEntity(), annotationValueList, this.indexBuilder.getServiceRegistry());
/* 30:   */     
/* 31:   */ 
/* 32:57 */     MockHelper.enumValue("fetch", FETCH_TYPE, this.manyToOne.getFetch(), annotationValueList);
/* 33:58 */     MockHelper.booleanValue("optional", this.manyToOne.isOptional(), annotationValueList);
/* 34:59 */     MockHelper.cascadeValue("cascade", this.manyToOne.getCascade(), isDefaultCascadePersist(), annotationValueList);
/* 35:60 */     create(MANY_TO_ONE, annotationValueList);
/* 36:61 */     parserJoinColumnList(this.manyToOne.getJoinColumn(), getTarget());
/* 37:62 */     parserJoinTable(this.manyToOne.getJoinTable(), getTarget());
/* 38:63 */     if (this.manyToOne.getMapsId() != null) {
/* 39:64 */       create(MAPS_ID, MockHelper.stringValueArray("value", this.manyToOne.getMapsId()));
/* 40:   */     }
/* 41:66 */     if ((this.manyToOne.isId() != null) && (this.manyToOne.isId().booleanValue())) {
/* 42:67 */       create(ID);
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected JaxbAccessType getAccessType()
/* 47:   */   {
/* 48:73 */     return this.manyToOne.getAccess();
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected void setAccessType(JaxbAccessType accessType)
/* 52:   */   {
/* 53:78 */     this.manyToOne.setAccess(accessType);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.ManyToOneMocker
 * JD-Core Version:    0.7.0.1
 */