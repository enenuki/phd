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
/*  12:    */ @XmlType(name="association-override", propOrder={"description", "joinColumn", "joinTable"})
/*  13:    */ public class JaxbAssociationOverride
/*  14:    */ {
/*  15:    */   protected String description;
/*  16:    */   @XmlElement(name="join-column")
/*  17:    */   protected List<JaxbJoinColumn> joinColumn;
/*  18:    */   @XmlElement(name="join-table")
/*  19:    */   protected JaxbJoinTable joinTable;
/*  20:    */   @XmlAttribute(required=true)
/*  21:    */   protected String name;
/*  22:    */   
/*  23:    */   public String getDescription()
/*  24:    */   {
/*  25: 75 */     return this.description;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setDescription(String value)
/*  29:    */   {
/*  30: 87 */     this.description = value;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<JaxbJoinColumn> getJoinColumn()
/*  34:    */   {
/*  35:113 */     if (this.joinColumn == null) {
/*  36:114 */       this.joinColumn = new ArrayList();
/*  37:    */     }
/*  38:116 */     return this.joinColumn;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public JaxbJoinTable getJoinTable()
/*  42:    */   {
/*  43:128 */     return this.joinTable;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setJoinTable(JaxbJoinTable value)
/*  47:    */   {
/*  48:140 */     this.joinTable = value;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getName()
/*  52:    */   {
/*  53:152 */     return this.name;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setName(String value)
/*  57:    */   {
/*  58:164 */     this.name = value;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbAssociationOverride
 * JD-Core Version:    0.7.0.1
 */