/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElements;
/*   9:    */ import javax.xml.bind.annotation.XmlType;
/*  10:    */ 
/*  11:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  12:    */ @XmlType(name="resultset-element", propOrder={"returnScalarOrReturnOrReturnJoin"})
/*  13:    */ public class JaxbResultsetElement
/*  14:    */ {
/*  15:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="load-collection", type=JaxbLoadCollectionElement.class), @javax.xml.bind.annotation.XmlElement(name="return", type=JaxbReturnElement.class), @javax.xml.bind.annotation.XmlElement(name="return-join", type=JaxbReturnJoinElement.class), @javax.xml.bind.annotation.XmlElement(name="return-scalar", type=JaxbReturnScalarElement.class)})
/*  16:    */   protected List<Object> returnScalarOrReturnOrReturnJoin;
/*  17:    */   @XmlAttribute(required=true)
/*  18:    */   protected String name;
/*  19:    */   
/*  20:    */   public List<Object> getReturnScalarOrReturnOrReturnJoin()
/*  21:    */   {
/*  22: 86 */     if (this.returnScalarOrReturnOrReturnJoin == null) {
/*  23: 87 */       this.returnScalarOrReturnOrReturnJoin = new ArrayList();
/*  24:    */     }
/*  25: 89 */     return this.returnScalarOrReturnOrReturnJoin;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getName()
/*  29:    */   {
/*  30:101 */     return this.name;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setName(String value)
/*  34:    */   {
/*  35:113 */     this.name = value;
/*  36:    */   }
/*  37:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbResultsetElement
 * JD-Core Version:    0.7.0.1
 */