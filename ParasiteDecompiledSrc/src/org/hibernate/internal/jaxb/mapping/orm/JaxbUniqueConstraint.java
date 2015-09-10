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
/*  12:    */ @XmlType(name="unique-constraint", propOrder={"columnName"})
/*  13:    */ public class JaxbUniqueConstraint
/*  14:    */ {
/*  15:    */   @XmlElement(name="column-name", required=true)
/*  16:    */   protected List<String> columnName;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String name;
/*  19:    */   
/*  20:    */   public List<String> getColumnName()
/*  21:    */   {
/*  22: 80 */     if (this.columnName == null) {
/*  23: 81 */       this.columnName = new ArrayList();
/*  24:    */     }
/*  25: 83 */     return this.columnName;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getName()
/*  29:    */   {
/*  30: 95 */     return this.name;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setName(String value)
/*  34:    */   {
/*  35:107 */     this.name = value;
/*  36:    */   }
/*  37:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbUniqueConstraint
 * JD-Core Version:    0.7.0.1
 */