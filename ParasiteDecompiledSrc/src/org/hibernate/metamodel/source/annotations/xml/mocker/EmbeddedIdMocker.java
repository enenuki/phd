/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  4:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddedId;
/*  5:   */ import org.jboss.jandex.ClassInfo;
/*  6:   */ 
/*  7:   */ class EmbeddedIdMocker
/*  8:   */   extends PropertyMocker
/*  9:   */ {
/* 10:   */   private JaxbEmbeddedId embeddedId;
/* 11:   */   
/* 12:   */   EmbeddedIdMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbEmbeddedId embeddedId)
/* 13:   */   {
/* 14:38 */     super(indexBuilder, classInfo, defaults);
/* 15:39 */     this.embeddedId = embeddedId;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected String getFieldName()
/* 19:   */   {
/* 20:44 */     return this.embeddedId.getName();
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected void processExtra()
/* 24:   */   {
/* 25:49 */     create(EMBEDDED_ID);
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected JaxbAccessType getAccessType()
/* 29:   */   {
/* 30:54 */     return this.embeddedId.getAccess();
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void setAccessType(JaxbAccessType accessType)
/* 34:   */   {
/* 35:59 */     this.embeddedId.setAccess(accessType);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.EmbeddedIdMocker
 * JD-Core Version:    0.7.0.1
 */