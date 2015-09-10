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
/*  12:    */ @XmlType(name="many-to-one", propOrder={"joinColumn", "joinTable", "cascade"})
/*  13:    */ public class JaxbManyToOne
/*  14:    */ {
/*  15:    */   @XmlElement(name="join-column")
/*  16:    */   protected List<JaxbJoinColumn> joinColumn;
/*  17:    */   @XmlElement(name="join-table")
/*  18:    */   protected JaxbJoinTable joinTable;
/*  19:    */   protected JaxbCascadeType cascade;
/*  20:    */   @XmlAttribute(required=true)
/*  21:    */   protected String name;
/*  22:    */   @XmlAttribute(name="target-entity")
/*  23:    */   protected String targetEntity;
/*  24:    */   @XmlAttribute
/*  25:    */   protected JaxbFetchType fetch;
/*  26:    */   @XmlAttribute
/*  27:    */   protected Boolean optional;
/*  28:    */   @XmlAttribute
/*  29:    */   protected JaxbAccessType access;
/*  30:    */   @XmlAttribute(name="maps-id")
/*  31:    */   protected String mapsId;
/*  32:    */   @XmlAttribute
/*  33:    */   protected Boolean id;
/*  34:    */   
/*  35:    */   public List<JaxbJoinColumn> getJoinColumn()
/*  36:    */   {
/*  37:111 */     if (this.joinColumn == null) {
/*  38:112 */       this.joinColumn = new ArrayList();
/*  39:    */     }
/*  40:114 */     return this.joinColumn;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public JaxbJoinTable getJoinTable()
/*  44:    */   {
/*  45:126 */     return this.joinTable;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setJoinTable(JaxbJoinTable value)
/*  49:    */   {
/*  50:138 */     this.joinTable = value;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public JaxbCascadeType getCascade()
/*  54:    */   {
/*  55:150 */     return this.cascade;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setCascade(JaxbCascadeType value)
/*  59:    */   {
/*  60:162 */     this.cascade = value;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getName()
/*  64:    */   {
/*  65:174 */     return this.name;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setName(String value)
/*  69:    */   {
/*  70:186 */     this.name = value;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getTargetEntity()
/*  74:    */   {
/*  75:198 */     return this.targetEntity;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setTargetEntity(String value)
/*  79:    */   {
/*  80:210 */     this.targetEntity = value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public JaxbFetchType getFetch()
/*  84:    */   {
/*  85:222 */     return this.fetch;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setFetch(JaxbFetchType value)
/*  89:    */   {
/*  90:234 */     this.fetch = value;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Boolean isOptional()
/*  94:    */   {
/*  95:246 */     return this.optional;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setOptional(Boolean value)
/*  99:    */   {
/* 100:258 */     this.optional = value;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public JaxbAccessType getAccess()
/* 104:    */   {
/* 105:270 */     return this.access;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setAccess(JaxbAccessType value)
/* 109:    */   {
/* 110:282 */     this.access = value;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getMapsId()
/* 114:    */   {
/* 115:294 */     return this.mapsId;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setMapsId(String value)
/* 119:    */   {
/* 120:306 */     this.mapsId = value;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Boolean isId()
/* 124:    */   {
/* 125:318 */     return this.id;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setId(Boolean value)
/* 129:    */   {
/* 130:330 */     this.id = value;
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbManyToOne
 * JD-Core Version:    0.7.0.1
 */