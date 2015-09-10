/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  4:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbedded;
/*  5:   */ import org.jboss.jandex.ClassInfo;
/*  6:   */ 
/*  7:   */ class EmbeddedMocker
/*  8:   */   extends PropertyMocker
/*  9:   */ {
/* 10:   */   private JaxbEmbedded embedded;
/* 11:   */   
/* 12:   */   EmbeddedMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbEmbedded embedded)
/* 13:   */   {
/* 14:38 */     super(indexBuilder, classInfo, defaults);
/* 15:39 */     this.embedded = embedded;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected void processExtra()
/* 19:   */   {
/* 20:44 */     create(EMBEDDED);
/* 21:45 */     parserAttributeOverrides(this.embedded.getAttributeOverride(), getTarget());
/* 22:46 */     parserAssociationOverrides(this.embedded.getAssociationOverride(), getTarget());
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected String getFieldName()
/* 26:   */   {
/* 27:52 */     return this.embedded.getName();
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected JaxbAccessType getAccessType()
/* 31:   */   {
/* 32:57 */     return this.embedded.getAccess();
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected void setAccessType(JaxbAccessType accessType)
/* 36:   */   {
/* 37:62 */     this.embedded.setAccess(accessType);
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.EmbeddedMocker
 * JD-Core Version:    0.7.0.1
 */