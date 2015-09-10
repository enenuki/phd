/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
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
/*  12:    */ @XmlType(name="many-to-any-element", propOrder={"metaValue", "column"})
/*  13:    */ public class JaxbManyToAnyElement
/*  14:    */ {
/*  15:    */   @XmlElement(name="meta-value")
/*  16:    */   protected List<JaxbMetaValueElement> metaValue;
/*  17:    */   @XmlElement(required=true)
/*  18:    */   protected JaxbColumnElement column;
/*  19:    */   @XmlAttribute(name="id-type", required=true)
/*  20:    */   protected String idType;
/*  21:    */   @XmlAttribute(name="meta-type")
/*  22:    */   protected String metaType;
/*  23:    */   
/*  24:    */   public List<JaxbMetaValueElement> getMetaValue()
/*  25:    */   {
/*  26: 81 */     if (this.metaValue == null) {
/*  27: 82 */       this.metaValue = new ArrayList();
/*  28:    */     }
/*  29: 84 */     return this.metaValue;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public JaxbColumnElement getColumn()
/*  33:    */   {
/*  34: 96 */     return this.column;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setColumn(JaxbColumnElement value)
/*  38:    */   {
/*  39:108 */     this.column = value;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getIdType()
/*  43:    */   {
/*  44:120 */     return this.idType;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setIdType(String value)
/*  48:    */   {
/*  49:132 */     this.idType = value;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getMetaType()
/*  53:    */   {
/*  54:144 */     return this.metaType;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setMetaType(String value)
/*  58:    */   {
/*  59:156 */     this.metaType = value;
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbManyToAnyElement
 * JD-Core Version:    0.7.0.1
 */