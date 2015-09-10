/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbBasic;
/*  7:   */ import org.jboss.jandex.AnnotationValue;
/*  8:   */ import org.jboss.jandex.ClassInfo;
/*  9:   */ 
/* 10:   */ class BasicMocker
/* 11:   */   extends PropertyMocker
/* 12:   */ {
/* 13:   */   private JaxbBasic basic;
/* 14:   */   
/* 15:   */   BasicMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbBasic basic)
/* 16:   */   {
/* 17:42 */     super(indexBuilder, classInfo, defaults);
/* 18:43 */     this.basic = basic;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected String getFieldName()
/* 22:   */   {
/* 23:48 */     return this.basic.getName();
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void processExtra()
/* 27:   */   {
/* 28:53 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 29:54 */     MockHelper.booleanValue("optional", this.basic.isOptional(), annotationValueList);
/* 30:55 */     MockHelper.enumValue("fetch", FETCH_TYPE, this.basic.getFetch(), annotationValueList);
/* 31:56 */     create(BASIC, annotationValueList);
/* 32:57 */     parserColumn(this.basic.getColumn(), getTarget());
/* 33:58 */     parserEnumType(this.basic.getEnumerated(), getTarget());
/* 34:59 */     parserLob(this.basic.getLob(), getTarget());
/* 35:60 */     parserTemporalType(this.basic.getTemporal(), getTarget());
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected JaxbAccessType getAccessType()
/* 39:   */   {
/* 40:67 */     return this.basic.getAccess();
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected void setAccessType(JaxbAccessType accessType)
/* 44:   */   {
/* 45:72 */     this.basic.setAccess(accessType);
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.BasicMocker
 * JD-Core Version:    0.7.0.1
 */