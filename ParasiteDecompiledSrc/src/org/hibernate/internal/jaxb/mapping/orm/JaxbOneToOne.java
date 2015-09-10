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
/*  12:    */ @XmlType(name="one-to-one", propOrder={"primaryKeyJoinColumn", "joinColumn", "joinTable", "cascade"})
/*  13:    */ public class JaxbOneToOne
/*  14:    */ {
/*  15:    */   @XmlElement(name="primary-key-join-column")
/*  16:    */   protected List<JaxbPrimaryKeyJoinColumn> primaryKeyJoinColumn;
/*  17:    */   @XmlElement(name="join-column")
/*  18:    */   protected List<JaxbJoinColumn> joinColumn;
/*  19:    */   @XmlElement(name="join-table")
/*  20:    */   protected JaxbJoinTable joinTable;
/*  21:    */   protected JaxbCascadeType cascade;
/*  22:    */   @XmlAttribute(required=true)
/*  23:    */   protected String name;
/*  24:    */   @XmlAttribute(name="target-entity")
/*  25:    */   protected String targetEntity;
/*  26:    */   @XmlAttribute
/*  27:    */   protected JaxbFetchType fetch;
/*  28:    */   @XmlAttribute
/*  29:    */   protected Boolean optional;
/*  30:    */   @XmlAttribute
/*  31:    */   protected JaxbAccessType access;
/*  32:    */   @XmlAttribute(name="mapped-by")
/*  33:    */   protected String mappedBy;
/*  34:    */   @XmlAttribute(name="orphan-removal")
/*  35:    */   protected Boolean orphanRemoval;
/*  36:    */   @XmlAttribute(name="maps-id")
/*  37:    */   protected String mapsId;
/*  38:    */   @XmlAttribute
/*  39:    */   protected Boolean id;
/*  40:    */   
/*  41:    */   public List<JaxbPrimaryKeyJoinColumn> getPrimaryKeyJoinColumn()
/*  42:    */   {
/*  43:120 */     if (this.primaryKeyJoinColumn == null) {
/*  44:121 */       this.primaryKeyJoinColumn = new ArrayList();
/*  45:    */     }
/*  46:123 */     return this.primaryKeyJoinColumn;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<JaxbJoinColumn> getJoinColumn()
/*  50:    */   {
/*  51:149 */     if (this.joinColumn == null) {
/*  52:150 */       this.joinColumn = new ArrayList();
/*  53:    */     }
/*  54:152 */     return this.joinColumn;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public JaxbJoinTable getJoinTable()
/*  58:    */   {
/*  59:164 */     return this.joinTable;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setJoinTable(JaxbJoinTable value)
/*  63:    */   {
/*  64:176 */     this.joinTable = value;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public JaxbCascadeType getCascade()
/*  68:    */   {
/*  69:188 */     return this.cascade;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setCascade(JaxbCascadeType value)
/*  73:    */   {
/*  74:200 */     this.cascade = value;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getName()
/*  78:    */   {
/*  79:212 */     return this.name;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setName(String value)
/*  83:    */   {
/*  84:224 */     this.name = value;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getTargetEntity()
/*  88:    */   {
/*  89:236 */     return this.targetEntity;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setTargetEntity(String value)
/*  93:    */   {
/*  94:248 */     this.targetEntity = value;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public JaxbFetchType getFetch()
/*  98:    */   {
/*  99:260 */     return this.fetch;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setFetch(JaxbFetchType value)
/* 103:    */   {
/* 104:272 */     this.fetch = value;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Boolean isOptional()
/* 108:    */   {
/* 109:284 */     return this.optional;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setOptional(Boolean value)
/* 113:    */   {
/* 114:296 */     this.optional = value;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public JaxbAccessType getAccess()
/* 118:    */   {
/* 119:308 */     return this.access;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setAccess(JaxbAccessType value)
/* 123:    */   {
/* 124:320 */     this.access = value;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getMappedBy()
/* 128:    */   {
/* 129:332 */     return this.mappedBy;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setMappedBy(String value)
/* 133:    */   {
/* 134:344 */     this.mappedBy = value;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Boolean isOrphanRemoval()
/* 138:    */   {
/* 139:356 */     return this.orphanRemoval;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setOrphanRemoval(Boolean value)
/* 143:    */   {
/* 144:368 */     this.orphanRemoval = value;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getMapsId()
/* 148:    */   {
/* 149:380 */     return this.mapsId;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setMapsId(String value)
/* 153:    */   {
/* 154:392 */     this.mapsId = value;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Boolean isId()
/* 158:    */   {
/* 159:404 */     return this.id;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setId(Boolean value)
/* 163:    */   {
/* 164:416 */     this.id = value;
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbOneToOne
 * JD-Core Version:    0.7.0.1
 */