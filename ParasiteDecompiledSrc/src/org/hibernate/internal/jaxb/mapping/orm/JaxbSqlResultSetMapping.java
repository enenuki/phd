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
/*  12:    */ @XmlType(name="sql-result-set-mapping", propOrder={"description", "entityResult", "columnResult"})
/*  13:    */ public class JaxbSqlResultSetMapping
/*  14:    */ {
/*  15:    */   protected String description;
/*  16:    */   @XmlElement(name="entity-result")
/*  17:    */   protected List<JaxbEntityResult> entityResult;
/*  18:    */   @XmlElement(name="column-result")
/*  19:    */   protected List<JaxbColumnResult> columnResult;
/*  20:    */   @XmlAttribute(required=true)
/*  21:    */   protected String name;
/*  22:    */   
/*  23:    */   public String getDescription()
/*  24:    */   {
/*  25: 73 */     return this.description;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setDescription(String value)
/*  29:    */   {
/*  30: 85 */     this.description = value;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<JaxbEntityResult> getEntityResult()
/*  34:    */   {
/*  35:111 */     if (this.entityResult == null) {
/*  36:112 */       this.entityResult = new ArrayList();
/*  37:    */     }
/*  38:114 */     return this.entityResult;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<JaxbColumnResult> getColumnResult()
/*  42:    */   {
/*  43:140 */     if (this.columnResult == null) {
/*  44:141 */       this.columnResult = new ArrayList();
/*  45:    */     }
/*  46:143 */     return this.columnResult;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getName()
/*  50:    */   {
/*  51:155 */     return this.name;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setName(String value)
/*  55:    */   {
/*  56:167 */     this.name = value;
/*  57:    */   }
/*  58:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbSqlResultSetMapping
 * JD-Core Version:    0.7.0.1
 */