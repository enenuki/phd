/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElement;
/*   9:    */ import javax.xml.bind.annotation.XmlType;
/*  10:    */ 
/*  11:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  12:    */ @XmlType(name="entity-result", propOrder={"fieldResult"})
/*  13:    */ public class JaxbEntityResult
/*  14:    */ {
/*  15:    */   @XmlElement(name="field-result")
/*  16:    */   protected List<JaxbFieldResult> fieldResult;
/*  17:    */   @XmlAttribute(name="entity-class", required=true)
/*  18:    */   protected String entityClass;
/*  19:    */   @XmlAttribute(name="discriminator-column")
/*  20:    */   protected String discriminatorColumn;
/*  21:    */   
/*  22:    */   public List<JaxbFieldResult> getFieldResult()
/*  23:    */   {
/*  24: 83 */     if (this.fieldResult == null) {
/*  25: 84 */       this.fieldResult = new ArrayList();
/*  26:    */     }
/*  27: 86 */     return this.fieldResult;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getEntityClass()
/*  31:    */   {
/*  32: 98 */     return this.entityClass;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setEntityClass(String value)
/*  36:    */   {
/*  37:110 */     this.entityClass = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getDiscriminatorColumn()
/*  41:    */   {
/*  42:122 */     return this.discriminatorColumn;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setDiscriminatorColumn(String value)
/*  46:    */   {
/*  47:134 */     this.discriminatorColumn = value;
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEntityResult
 * JD-Core Version:    0.7.0.1
 */