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
/*  12:    */ @XmlType(name="entity", propOrder={"description", "table", "secondaryTable", "primaryKeyJoinColumn", "idClass", "inheritance", "discriminatorValue", "discriminatorColumn", "sequenceGenerator", "tableGenerator", "namedQuery", "namedNativeQuery", "sqlResultSetMapping", "excludeDefaultListeners", "excludeSuperclassListeners", "entityListeners", "prePersist", "postPersist", "preRemove", "postRemove", "preUpdate", "postUpdate", "postLoad", "attributeOverride", "associationOverride", "attributes"})
/*  13:    */ public class JaxbEntity
/*  14:    */ {
/*  15:    */   protected String description;
/*  16:    */   protected JaxbTable table;
/*  17:    */   @XmlElement(name="secondary-table")
/*  18:    */   protected List<JaxbSecondaryTable> secondaryTable;
/*  19:    */   @XmlElement(name="primary-key-join-column")
/*  20:    */   protected List<JaxbPrimaryKeyJoinColumn> primaryKeyJoinColumn;
/*  21:    */   @XmlElement(name="id-class")
/*  22:    */   protected JaxbIdClass idClass;
/*  23:    */   protected JaxbInheritance inheritance;
/*  24:    */   @XmlElement(name="discriminator-value")
/*  25:    */   protected String discriminatorValue;
/*  26:    */   @XmlElement(name="discriminator-column")
/*  27:    */   protected JaxbDiscriminatorColumn discriminatorColumn;
/*  28:    */   @XmlElement(name="sequence-generator")
/*  29:    */   protected JaxbSequenceGenerator sequenceGenerator;
/*  30:    */   @XmlElement(name="table-generator")
/*  31:    */   protected JaxbTableGenerator tableGenerator;
/*  32:    */   @XmlElement(name="named-query")
/*  33:    */   protected List<JaxbNamedQuery> namedQuery;
/*  34:    */   @XmlElement(name="named-native-query")
/*  35:    */   protected List<JaxbNamedNativeQuery> namedNativeQuery;
/*  36:    */   @XmlElement(name="sql-result-set-mapping")
/*  37:    */   protected List<JaxbSqlResultSetMapping> sqlResultSetMapping;
/*  38:    */   @XmlElement(name="exclude-default-listeners")
/*  39:    */   protected JaxbEmptyType excludeDefaultListeners;
/*  40:    */   @XmlElement(name="exclude-superclass-listeners")
/*  41:    */   protected JaxbEmptyType excludeSuperclassListeners;
/*  42:    */   @XmlElement(name="entity-listeners")
/*  43:    */   protected JaxbEntityListeners entityListeners;
/*  44:    */   @XmlElement(name="pre-persist")
/*  45:    */   protected JaxbPrePersist prePersist;
/*  46:    */   @XmlElement(name="post-persist")
/*  47:    */   protected JaxbPostPersist postPersist;
/*  48:    */   @XmlElement(name="pre-remove")
/*  49:    */   protected JaxbPreRemove preRemove;
/*  50:    */   @XmlElement(name="post-remove")
/*  51:    */   protected JaxbPostRemove postRemove;
/*  52:    */   @XmlElement(name="pre-update")
/*  53:    */   protected JaxbPreUpdate preUpdate;
/*  54:    */   @XmlElement(name="post-update")
/*  55:    */   protected JaxbPostUpdate postUpdate;
/*  56:    */   @XmlElement(name="post-load")
/*  57:    */   protected JaxbPostLoad postLoad;
/*  58:    */   @XmlElement(name="attribute-override")
/*  59:    */   protected List<JaxbAttributeOverride> attributeOverride;
/*  60:    */   @XmlElement(name="association-override")
/*  61:    */   protected List<JaxbAssociationOverride> associationOverride;
/*  62:    */   protected JaxbAttributes attributes;
/*  63:    */   @XmlAttribute
/*  64:    */   protected String name;
/*  65:    */   @XmlAttribute(name="class", required=true)
/*  66:    */   protected String clazz;
/*  67:    */   @XmlAttribute
/*  68:    */   protected JaxbAccessType access;
/*  69:    */   @XmlAttribute
/*  70:    */   protected Boolean cacheable;
/*  71:    */   @XmlAttribute(name="metadata-complete")
/*  72:    */   protected Boolean metadataComplete;
/*  73:    */   
/*  74:    */   public String getDescription()
/*  75:    */   {
/*  76:179 */     return this.description;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setDescription(String value)
/*  80:    */   {
/*  81:191 */     this.description = value;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public JaxbTable getTable()
/*  85:    */   {
/*  86:203 */     return this.table;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setTable(JaxbTable value)
/*  90:    */   {
/*  91:215 */     this.table = value;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<JaxbSecondaryTable> getSecondaryTable()
/*  95:    */   {
/*  96:241 */     if (this.secondaryTable == null) {
/*  97:242 */       this.secondaryTable = new ArrayList();
/*  98:    */     }
/*  99:244 */     return this.secondaryTable;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<JaxbPrimaryKeyJoinColumn> getPrimaryKeyJoinColumn()
/* 103:    */   {
/* 104:270 */     if (this.primaryKeyJoinColumn == null) {
/* 105:271 */       this.primaryKeyJoinColumn = new ArrayList();
/* 106:    */     }
/* 107:273 */     return this.primaryKeyJoinColumn;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public JaxbIdClass getIdClass()
/* 111:    */   {
/* 112:285 */     return this.idClass;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdClass(JaxbIdClass value)
/* 116:    */   {
/* 117:297 */     this.idClass = value;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public JaxbInheritance getInheritance()
/* 121:    */   {
/* 122:309 */     return this.inheritance;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setInheritance(JaxbInheritance value)
/* 126:    */   {
/* 127:321 */     this.inheritance = value;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getDiscriminatorValue()
/* 131:    */   {
/* 132:333 */     return this.discriminatorValue;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setDiscriminatorValue(String value)
/* 136:    */   {
/* 137:345 */     this.discriminatorValue = value;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public JaxbDiscriminatorColumn getDiscriminatorColumn()
/* 141:    */   {
/* 142:357 */     return this.discriminatorColumn;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDiscriminatorColumn(JaxbDiscriminatorColumn value)
/* 146:    */   {
/* 147:369 */     this.discriminatorColumn = value;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public JaxbSequenceGenerator getSequenceGenerator()
/* 151:    */   {
/* 152:381 */     return this.sequenceGenerator;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setSequenceGenerator(JaxbSequenceGenerator value)
/* 156:    */   {
/* 157:393 */     this.sequenceGenerator = value;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public JaxbTableGenerator getTableGenerator()
/* 161:    */   {
/* 162:405 */     return this.tableGenerator;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTableGenerator(JaxbTableGenerator value)
/* 166:    */   {
/* 167:417 */     this.tableGenerator = value;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<JaxbNamedQuery> getNamedQuery()
/* 171:    */   {
/* 172:443 */     if (this.namedQuery == null) {
/* 173:444 */       this.namedQuery = new ArrayList();
/* 174:    */     }
/* 175:446 */     return this.namedQuery;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<JaxbNamedNativeQuery> getNamedNativeQuery()
/* 179:    */   {
/* 180:472 */     if (this.namedNativeQuery == null) {
/* 181:473 */       this.namedNativeQuery = new ArrayList();
/* 182:    */     }
/* 183:475 */     return this.namedNativeQuery;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<JaxbSqlResultSetMapping> getSqlResultSetMapping()
/* 187:    */   {
/* 188:501 */     if (this.sqlResultSetMapping == null) {
/* 189:502 */       this.sqlResultSetMapping = new ArrayList();
/* 190:    */     }
/* 191:504 */     return this.sqlResultSetMapping;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public JaxbEmptyType getExcludeDefaultListeners()
/* 195:    */   {
/* 196:516 */     return this.excludeDefaultListeners;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setExcludeDefaultListeners(JaxbEmptyType value)
/* 200:    */   {
/* 201:528 */     this.excludeDefaultListeners = value;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public JaxbEmptyType getExcludeSuperclassListeners()
/* 205:    */   {
/* 206:540 */     return this.excludeSuperclassListeners;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setExcludeSuperclassListeners(JaxbEmptyType value)
/* 210:    */   {
/* 211:552 */     this.excludeSuperclassListeners = value;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public JaxbEntityListeners getEntityListeners()
/* 215:    */   {
/* 216:564 */     return this.entityListeners;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setEntityListeners(JaxbEntityListeners value)
/* 220:    */   {
/* 221:576 */     this.entityListeners = value;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public JaxbPrePersist getPrePersist()
/* 225:    */   {
/* 226:588 */     return this.prePersist;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setPrePersist(JaxbPrePersist value)
/* 230:    */   {
/* 231:600 */     this.prePersist = value;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public JaxbPostPersist getPostPersist()
/* 235:    */   {
/* 236:612 */     return this.postPersist;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setPostPersist(JaxbPostPersist value)
/* 240:    */   {
/* 241:624 */     this.postPersist = value;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public JaxbPreRemove getPreRemove()
/* 245:    */   {
/* 246:636 */     return this.preRemove;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setPreRemove(JaxbPreRemove value)
/* 250:    */   {
/* 251:648 */     this.preRemove = value;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public JaxbPostRemove getPostRemove()
/* 255:    */   {
/* 256:660 */     return this.postRemove;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setPostRemove(JaxbPostRemove value)
/* 260:    */   {
/* 261:672 */     this.postRemove = value;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public JaxbPreUpdate getPreUpdate()
/* 265:    */   {
/* 266:684 */     return this.preUpdate;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setPreUpdate(JaxbPreUpdate value)
/* 270:    */   {
/* 271:696 */     this.preUpdate = value;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public JaxbPostUpdate getPostUpdate()
/* 275:    */   {
/* 276:708 */     return this.postUpdate;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setPostUpdate(JaxbPostUpdate value)
/* 280:    */   {
/* 281:720 */     this.postUpdate = value;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public JaxbPostLoad getPostLoad()
/* 285:    */   {
/* 286:732 */     return this.postLoad;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setPostLoad(JaxbPostLoad value)
/* 290:    */   {
/* 291:744 */     this.postLoad = value;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public List<JaxbAttributeOverride> getAttributeOverride()
/* 295:    */   {
/* 296:770 */     if (this.attributeOverride == null) {
/* 297:771 */       this.attributeOverride = new ArrayList();
/* 298:    */     }
/* 299:773 */     return this.attributeOverride;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public List<JaxbAssociationOverride> getAssociationOverride()
/* 303:    */   {
/* 304:799 */     if (this.associationOverride == null) {
/* 305:800 */       this.associationOverride = new ArrayList();
/* 306:    */     }
/* 307:802 */     return this.associationOverride;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public JaxbAttributes getAttributes()
/* 311:    */   {
/* 312:814 */     return this.attributes;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setAttributes(JaxbAttributes value)
/* 316:    */   {
/* 317:826 */     this.attributes = value;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String getName()
/* 321:    */   {
/* 322:838 */     return this.name;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setName(String value)
/* 326:    */   {
/* 327:850 */     this.name = value;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public String getClazz()
/* 331:    */   {
/* 332:862 */     return this.clazz;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setClazz(String value)
/* 336:    */   {
/* 337:874 */     this.clazz = value;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public JaxbAccessType getAccess()
/* 341:    */   {
/* 342:886 */     return this.access;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setAccess(JaxbAccessType value)
/* 346:    */   {
/* 347:898 */     this.access = value;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public Boolean isCacheable()
/* 351:    */   {
/* 352:910 */     return this.cacheable;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setCacheable(Boolean value)
/* 356:    */   {
/* 357:922 */     this.cacheable = value;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public Boolean isMetadataComplete()
/* 361:    */   {
/* 362:934 */     return this.metadataComplete;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setMetadataComplete(Boolean value)
/* 366:    */   {
/* 367:946 */     this.metadataComplete = value;
/* 368:    */   }
/* 369:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEntity
 * JD-Core Version:    0.7.0.1
 */