/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*   8:    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*   9:    */ 
/*  10:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  11:    */ @XmlType(name="tuplizer-element")
/*  12:    */ public class JaxbTuplizerElement
/*  13:    */ {
/*  14:    */   @XmlAttribute(name="class", required=true)
/*  15:    */   protected String clazz;
/*  16:    */   @XmlAttribute(name="entity-mode")
/*  17:    */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  18:    */   protected String entityMode;
/*  19:    */   
/*  20:    */   public String getClazz()
/*  21:    */   {
/*  22: 64 */     return this.clazz;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setClazz(String value)
/*  26:    */   {
/*  27: 76 */     this.clazz = value;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getEntityMode()
/*  31:    */   {
/*  32: 88 */     return this.entityMode;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setEntityMode(String value)
/*  36:    */   {
/*  37:100 */     this.entityMode = value;
/*  38:    */   }
/*  39:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbTuplizerElement
 * JD-Core Version:    0.7.0.1
 */