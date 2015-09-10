/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  4:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTransient;
/*  5:   */ import org.jboss.jandex.ClassInfo;
/*  6:   */ 
/*  7:   */ class TransientMocker
/*  8:   */   extends PropertyMocker
/*  9:   */ {
/* 10:   */   private JaxbTransient transientObj;
/* 11:   */   
/* 12:   */   TransientMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbTransient transientObj)
/* 13:   */   {
/* 14:38 */     super(indexBuilder, classInfo, defaults);
/* 15:39 */     this.transientObj = transientObj;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected void processExtra()
/* 19:   */   {
/* 20:44 */     create(TRANSIENT);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected String getFieldName()
/* 24:   */   {
/* 25:49 */     return this.transientObj.getName();
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected JaxbAccessType getAccessType()
/* 29:   */   {
/* 30:54 */     return JaxbAccessType.FIELD;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void setAccessType(JaxbAccessType accessType) {}
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.TransientMocker
 * JD-Core Version:    0.7.0.1
 */