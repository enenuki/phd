/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElement;
/*   9:    */ import javax.xml.bind.annotation.XmlRootElement;
/*  10:    */ import javax.xml.bind.annotation.XmlType;
/*  11:    */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*  12:    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*  13:    */ 
/*  14:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  15:    */ @XmlType(name="", propOrder={"description", "persistenceUnitMetadata", "_package", "schema", "catalog", "access", "sequenceGenerator", "tableGenerator", "namedQuery", "namedNativeQuery", "sqlResultSetMapping", "mappedSuperclass", "entity", "embeddable"})
/*  16:    */ @XmlRootElement(name="entity-mappings")
/*  17:    */ public class JaxbEntityMappings
/*  18:    */ {
/*  19:    */   protected String description;
/*  20:    */   @XmlElement(name="persistence-unit-metadata")
/*  21:    */   protected JaxbPersistenceUnitMetadata persistenceUnitMetadata;
/*  22:    */   @XmlElement(name="package")
/*  23:    */   protected String _package;
/*  24:    */   protected String schema;
/*  25:    */   protected String catalog;
/*  26:    */   protected JaxbAccessType access;
/*  27:    */   @XmlElement(name="sequence-generator")
/*  28:    */   protected List<JaxbSequenceGenerator> sequenceGenerator;
/*  29:    */   @XmlElement(name="table-generator")
/*  30:    */   protected List<JaxbTableGenerator> tableGenerator;
/*  31:    */   @XmlElement(name="named-query")
/*  32:    */   protected List<JaxbNamedQuery> namedQuery;
/*  33:    */   @XmlElement(name="named-native-query")
/*  34:    */   protected List<JaxbNamedNativeQuery> namedNativeQuery;
/*  35:    */   @XmlElement(name="sql-result-set-mapping")
/*  36:    */   protected List<JaxbSqlResultSetMapping> sqlResultSetMapping;
/*  37:    */   @XmlElement(name="mapped-superclass")
/*  38:    */   protected List<JaxbMappedSuperclass> mappedSuperclass;
/*  39:    */   protected List<JaxbEntity> entity;
/*  40:    */   protected List<JaxbEmbeddable> embeddable;
/*  41:    */   @XmlAttribute(required=true)
/*  42:    */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  43:    */   protected String version;
/*  44:    */   
/*  45:    */   public String getDescription()
/*  46:    */   {
/*  47:129 */     return this.description;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setDescription(String value)
/*  51:    */   {
/*  52:141 */     this.description = value;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public JaxbPersistenceUnitMetadata getPersistenceUnitMetadata()
/*  56:    */   {
/*  57:153 */     return this.persistenceUnitMetadata;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setPersistenceUnitMetadata(JaxbPersistenceUnitMetadata value)
/*  61:    */   {
/*  62:165 */     this.persistenceUnitMetadata = value;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getPackage()
/*  66:    */   {
/*  67:177 */     return this._package;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setPackage(String value)
/*  71:    */   {
/*  72:189 */     this._package = value;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getSchema()
/*  76:    */   {
/*  77:201 */     return this.schema;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setSchema(String value)
/*  81:    */   {
/*  82:213 */     this.schema = value;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getCatalog()
/*  86:    */   {
/*  87:225 */     return this.catalog;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setCatalog(String value)
/*  91:    */   {
/*  92:237 */     this.catalog = value;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public JaxbAccessType getAccess()
/*  96:    */   {
/*  97:249 */     return this.access;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setAccess(JaxbAccessType value)
/* 101:    */   {
/* 102:261 */     this.access = value;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<JaxbSequenceGenerator> getSequenceGenerator()
/* 106:    */   {
/* 107:287 */     if (this.sequenceGenerator == null) {
/* 108:288 */       this.sequenceGenerator = new ArrayList();
/* 109:    */     }
/* 110:290 */     return this.sequenceGenerator;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<JaxbTableGenerator> getTableGenerator()
/* 114:    */   {
/* 115:316 */     if (this.tableGenerator == null) {
/* 116:317 */       this.tableGenerator = new ArrayList();
/* 117:    */     }
/* 118:319 */     return this.tableGenerator;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<JaxbNamedQuery> getNamedQuery()
/* 122:    */   {
/* 123:345 */     if (this.namedQuery == null) {
/* 124:346 */       this.namedQuery = new ArrayList();
/* 125:    */     }
/* 126:348 */     return this.namedQuery;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public List<JaxbNamedNativeQuery> getNamedNativeQuery()
/* 130:    */   {
/* 131:374 */     if (this.namedNativeQuery == null) {
/* 132:375 */       this.namedNativeQuery = new ArrayList();
/* 133:    */     }
/* 134:377 */     return this.namedNativeQuery;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<JaxbSqlResultSetMapping> getSqlResultSetMapping()
/* 138:    */   {
/* 139:403 */     if (this.sqlResultSetMapping == null) {
/* 140:404 */       this.sqlResultSetMapping = new ArrayList();
/* 141:    */     }
/* 142:406 */     return this.sqlResultSetMapping;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<JaxbMappedSuperclass> getMappedSuperclass()
/* 146:    */   {
/* 147:432 */     if (this.mappedSuperclass == null) {
/* 148:433 */       this.mappedSuperclass = new ArrayList();
/* 149:    */     }
/* 150:435 */     return this.mappedSuperclass;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<JaxbEntity> getEntity()
/* 154:    */   {
/* 155:461 */     if (this.entity == null) {
/* 156:462 */       this.entity = new ArrayList();
/* 157:    */     }
/* 158:464 */     return this.entity;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<JaxbEmbeddable> getEmbeddable()
/* 162:    */   {
/* 163:490 */     if (this.embeddable == null) {
/* 164:491 */       this.embeddable = new ArrayList();
/* 165:    */     }
/* 166:493 */     return this.embeddable;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getVersion()
/* 170:    */   {
/* 171:505 */     if (this.version == null) {
/* 172:506 */       return "2.0";
/* 173:    */     }
/* 174:508 */     return this.version;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setVersion(String value)
/* 178:    */   {
/* 179:521 */     this.version = value;
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEntityMappings
 * JD-Core Version:    0.7.0.1
 */