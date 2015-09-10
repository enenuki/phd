/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  6:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  7:   */ import javax.xml.bind.annotation.XmlElement;
/*  8:   */ import javax.xml.bind.annotation.XmlType;
/*  9:   */ 
/* 10:   */ @XmlAccessorType(XmlAccessType.FIELD)
/* 11:   */ @XmlType(name="entity-listeners", propOrder={"entityListener"})
/* 12:   */ public class JaxbEntityListeners
/* 13:   */ {
/* 14:   */   @XmlElement(name="entity-listener")
/* 15:   */   protected List<JaxbEntityListener> entityListener;
/* 16:   */   
/* 17:   */   public List<JaxbEntityListener> getEntityListener()
/* 18:   */   {
/* 19:76 */     if (this.entityListener == null) {
/* 20:77 */       this.entityListener = new ArrayList();
/* 21:   */     }
/* 22:79 */     return this.entityListener;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEntityListeners
 * JD-Core Version:    0.7.0.1
 */