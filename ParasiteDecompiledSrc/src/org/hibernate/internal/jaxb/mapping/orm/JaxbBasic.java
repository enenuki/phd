/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="basic", propOrder={"column", "lob", "temporal", "enumerated"})
/*  10:    */ public class JaxbBasic
/*  11:    */ {
/*  12:    */   protected JaxbColumn column;
/*  13:    */   protected JaxbLob lob;
/*  14:    */   protected JaxbTemporalType temporal;
/*  15:    */   protected JaxbEnumType enumerated;
/*  16:    */   @XmlAttribute(required=true)
/*  17:    */   protected String name;
/*  18:    */   @XmlAttribute
/*  19:    */   protected JaxbFetchType fetch;
/*  20:    */   @XmlAttribute
/*  21:    */   protected Boolean optional;
/*  22:    */   @XmlAttribute
/*  23:    */   protected JaxbAccessType access;
/*  24:    */   
/*  25:    */   public JaxbColumn getColumn()
/*  26:    */   {
/*  27: 82 */     return this.column;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setColumn(JaxbColumn value)
/*  31:    */   {
/*  32: 94 */     this.column = value;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public JaxbLob getLob()
/*  36:    */   {
/*  37:106 */     return this.lob;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setLob(JaxbLob value)
/*  41:    */   {
/*  42:118 */     this.lob = value;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public JaxbTemporalType getTemporal()
/*  46:    */   {
/*  47:130 */     return this.temporal;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setTemporal(JaxbTemporalType value)
/*  51:    */   {
/*  52:142 */     this.temporal = value;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public JaxbEnumType getEnumerated()
/*  56:    */   {
/*  57:154 */     return this.enumerated;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setEnumerated(JaxbEnumType value)
/*  61:    */   {
/*  62:166 */     this.enumerated = value;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getName()
/*  66:    */   {
/*  67:178 */     return this.name;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setName(String value)
/*  71:    */   {
/*  72:190 */     this.name = value;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public JaxbFetchType getFetch()
/*  76:    */   {
/*  77:202 */     return this.fetch;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setFetch(JaxbFetchType value)
/*  81:    */   {
/*  82:214 */     this.fetch = value;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Boolean isOptional()
/*  86:    */   {
/*  87:226 */     return this.optional;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setOptional(Boolean value)
/*  91:    */   {
/*  92:238 */     this.optional = value;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public JaxbAccessType getAccess()
/*  96:    */   {
/*  97:250 */     return this.access;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setAccess(JaxbAccessType value)
/* 101:    */   {
/* 102:262 */     this.access = value;
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbBasic
 * JD-Core Version:    0.7.0.1
 */