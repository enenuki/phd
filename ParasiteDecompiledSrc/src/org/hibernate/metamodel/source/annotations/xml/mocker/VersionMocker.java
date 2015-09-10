/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*  4:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbVersion;
/*  5:   */ import org.jboss.jandex.ClassInfo;
/*  6:   */ 
/*  7:   */ class VersionMocker
/*  8:   */   extends PropertyMocker
/*  9:   */ {
/* 10:   */   private JaxbVersion version;
/* 11:   */   
/* 12:   */   VersionMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults, JaxbVersion version)
/* 13:   */   {
/* 14:38 */     super(indexBuilder, classInfo, defaults);
/* 15:39 */     this.version = version;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected String getFieldName()
/* 19:   */   {
/* 20:44 */     return this.version.getName();
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected void processExtra()
/* 24:   */   {
/* 25:49 */     create(VERSION);
/* 26:50 */     parserColumn(this.version.getColumn(), getTarget());
/* 27:51 */     parserTemporalType(this.version.getTemporal(), getTarget());
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected JaxbAccessType getAccessType()
/* 31:   */   {
/* 32:56 */     return this.version.getAccess();
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected void setAccessType(JaxbAccessType accessType)
/* 36:   */   {
/* 37:61 */     this.version.setAccess(accessType);
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.VersionMocker
 * JD-Core Version:    0.7.0.1
 */