/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbGeneratedValue;
/*  7:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbId;
/*  8:   */ import org.jboss.jandex.AnnotationInstance;
/*  9:   */ import org.jboss.jandex.AnnotationTarget;
/* 10:   */ import org.jboss.jandex.AnnotationValue;
/* 11:   */ import org.jboss.jandex.ClassInfo;
/* 12:   */ 
/* 13:   */ class IdMocker
/* 14:   */   extends PropertyMocker
/* 15:   */ {
/* 16:   */   private JaxbId id;
/* 17:   */   
/* 18:   */   IdMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbId id)
/* 19:   */   {
/* 20:45 */     super(indexBuilder, classInfo, defaults);
/* 21:46 */     this.id = id;
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected void processExtra()
/* 25:   */   {
/* 26:51 */     create(ID);
/* 27:52 */     parserColumn(this.id.getColumn(), getTarget());
/* 28:53 */     parserGeneratedValue(this.id.getGeneratedValue(), getTarget());
/* 29:54 */     parserTemporalType(this.id.getTemporal(), getTarget());
/* 30:   */   }
/* 31:   */   
/* 32:   */   private AnnotationInstance parserGeneratedValue(JaxbGeneratedValue generatedValue, AnnotationTarget target)
/* 33:   */   {
/* 34:58 */     if (generatedValue == null) {
/* 35:59 */       return null;
/* 36:   */     }
/* 37:61 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 38:62 */     MockHelper.stringValue("generator", generatedValue.getGenerator(), annotationValueList);
/* 39:63 */     MockHelper.enumValue("strategy", GENERATION_TYPE, generatedValue.getStrategy(), annotationValueList);
/* 40:   */     
/* 41:   */ 
/* 42:   */ 
/* 43:67 */     return create(GENERATED_VALUE, target, annotationValueList);
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected String getFieldName()
/* 47:   */   {
/* 48:72 */     return this.id.getName();
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected JaxbAccessType getAccessType()
/* 52:   */   {
/* 53:77 */     return this.id.getAccess();
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected void setAccessType(JaxbAccessType accessType)
/* 57:   */   {
/* 58:82 */     this.id.setAccess(accessType);
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.IdMocker
 * JD-Core Version:    0.7.0.1
 */