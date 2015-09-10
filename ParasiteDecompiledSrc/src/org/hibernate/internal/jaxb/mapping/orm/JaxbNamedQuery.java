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
/*  12:    */ @XmlType(name="named-query", propOrder={"description", "query", "lockMode", "hint"})
/*  13:    */ public class JaxbNamedQuery
/*  14:    */ {
/*  15:    */   protected String description;
/*  16:    */   @XmlElement(required=true)
/*  17:    */   protected String query;
/*  18:    */   @XmlElement(name="lock-mode")
/*  19:    */   protected JaxbLockModeType lockMode;
/*  20:    */   protected List<JaxbQueryHint> hint;
/*  21:    */   @XmlAttribute(required=true)
/*  22:    */   protected String name;
/*  23:    */   
/*  24:    */   public String getDescription()
/*  25:    */   {
/*  26: 76 */     return this.description;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setDescription(String value)
/*  30:    */   {
/*  31: 88 */     this.description = value;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getQuery()
/*  35:    */   {
/*  36:100 */     return this.query;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setQuery(String value)
/*  40:    */   {
/*  41:112 */     this.query = value;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public JaxbLockModeType getLockMode()
/*  45:    */   {
/*  46:124 */     return this.lockMode;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setLockMode(JaxbLockModeType value)
/*  50:    */   {
/*  51:136 */     this.lockMode = value;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<JaxbQueryHint> getHint()
/*  55:    */   {
/*  56:162 */     if (this.hint == null) {
/*  57:163 */       this.hint = new ArrayList();
/*  58:    */     }
/*  59:165 */     return this.hint;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getName()
/*  63:    */   {
/*  64:177 */     return this.name;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setName(String value)
/*  68:    */   {
/*  69:189 */     this.name = value;
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbNamedQuery
 * JD-Core Version:    0.7.0.1
 */