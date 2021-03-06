/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  6:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  7:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  8:   */ import javax.xml.bind.annotation.XmlType;
/*  9:   */ 
/* 10:   */ @XmlAccessorType(XmlAccessType.FIELD)
/* 11:   */ @XmlType(name="generator-element", propOrder={"param"})
/* 12:   */ public class JaxbGeneratorElement
/* 13:   */ {
/* 14:   */   protected List<JaxbParamElement> param;
/* 15:   */   @XmlAttribute(name="class", required=true)
/* 16:   */   protected String clazz;
/* 17:   */   
/* 18:   */   public List<JaxbParamElement> getParam()
/* 19:   */   {
/* 20:72 */     if (this.param == null) {
/* 21:73 */       this.param = new ArrayList();
/* 22:   */     }
/* 23:75 */     return this.param;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getClazz()
/* 27:   */   {
/* 28:87 */     return this.clazz;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setClazz(String value)
/* 32:   */   {
/* 33:99 */     this.clazz = value;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbGeneratorElement
 * JD-Core Version:    0.7.0.1
 */