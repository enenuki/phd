/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOneToOne;
/*  7:   */ import org.jboss.jandex.AnnotationValue;
/*  8:   */ import org.jboss.jandex.ClassInfo;
/*  9:   */ 
/* 10:   */ class OneToOneMocker
/* 11:   */   extends PropertyMocker
/* 12:   */ {
/* 13:   */   private JaxbOneToOne oneToOne;
/* 14:   */   
/* 15:   */   OneToOneMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbOneToOne oneToOne)
/* 16:   */   {
/* 17:42 */     super(indexBuilder, classInfo, defaults);
/* 18:43 */     this.oneToOne = oneToOne;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected String getFieldName()
/* 22:   */   {
/* 23:48 */     return this.oneToOne.getName();
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void processExtra()
/* 27:   */   {
/* 28:53 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 29:54 */     MockHelper.classValue("targetEntity", this.oneToOne.getTargetEntity(), annotationValueList, this.indexBuilder.getServiceRegistry());
/* 30:   */     
/* 31:   */ 
/* 32:57 */     MockHelper.enumValue("fetch", FETCH_TYPE, this.oneToOne.getFetch(), annotationValueList);
/* 33:58 */     MockHelper.booleanValue("optional", this.oneToOne.isOptional(), annotationValueList);
/* 34:59 */     MockHelper.booleanValue("orphanRemoval", this.oneToOne.isOrphanRemoval(), annotationValueList);
/* 35:60 */     MockHelper.stringValue("mappedBy", this.oneToOne.getMappedBy(), annotationValueList);
/* 36:61 */     MockHelper.cascadeValue("cascade", this.oneToOne.getCascade(), isDefaultCascadePersist(), annotationValueList);
/* 37:62 */     create(ONE_TO_ONE, annotationValueList);
/* 38:   */     
/* 39:64 */     parserPrimaryKeyJoinColumnList(this.oneToOne.getPrimaryKeyJoinColumn(), getTarget());
/* 40:65 */     parserJoinColumnList(this.oneToOne.getJoinColumn(), getTarget());
/* 41:66 */     parserJoinTable(this.oneToOne.getJoinTable(), getTarget());
/* 42:67 */     if (this.oneToOne.getMapsId() != null) {
/* 43:68 */       create(MAPS_ID, MockHelper.stringValueArray("value", this.oneToOne.getMapsId()));
/* 44:   */     }
/* 45:70 */     if ((this.oneToOne.isId() != null) && (this.oneToOne.isId().booleanValue())) {
/* 46:71 */       create(ID);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected JaxbAccessType getAccessType()
/* 51:   */   {
/* 52:77 */     return this.oneToOne.getAccess();
/* 53:   */   }
/* 54:   */   
/* 55:   */   protected void setAccessType(JaxbAccessType accessType)
/* 56:   */   {
/* 57:82 */     this.oneToOne.setAccess(accessType);
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.OneToOneMocker
 * JD-Core Version:    0.7.0.1
 */