/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlRegistry;
/*   4:    */ 
/*   5:    */ @XmlRegistry
/*   6:    */ public class ObjectFactory
/*   7:    */ {
/*   8:    */   public JaxbPersistenceUnitMetadata createJaxbPersistenceUnitMetadata()
/*   9:    */   {
/*  10: 44 */     return new JaxbPersistenceUnitMetadata();
/*  11:    */   }
/*  12:    */   
/*  13:    */   public JaxbMapKeyJoinColumn createJaxbMapKeyJoinColumn()
/*  14:    */   {
/*  15: 52 */     return new JaxbMapKeyJoinColumn();
/*  16:    */   }
/*  17:    */   
/*  18:    */   public JaxbPostRemove createJaxbPostRemove()
/*  19:    */   {
/*  20: 60 */     return new JaxbPostRemove();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public JaxbNamedNativeQuery createJaxbNamedNativeQuery()
/*  24:    */   {
/*  25: 68 */     return new JaxbNamedNativeQuery();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public JaxbQueryHint createJaxbQueryHint()
/*  29:    */   {
/*  30: 76 */     return new JaxbQueryHint();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public JaxbEmbeddableAttributes createJaxbEmbeddableAttributes()
/*  34:    */   {
/*  35: 84 */     return new JaxbEmbeddableAttributes();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public JaxbMappedSuperclass createJaxbMappedSuperclass()
/*  39:    */   {
/*  40: 92 */     return new JaxbMappedSuperclass();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public JaxbManyToMany createJaxbManyToMany()
/*  44:    */   {
/*  45:100 */     return new JaxbManyToMany();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public JaxbTableGenerator createJaxbTableGenerator()
/*  49:    */   {
/*  50:108 */     return new JaxbTableGenerator();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public JaxbOneToOne createJaxbOneToOne()
/*  54:    */   {
/*  55:116 */     return new JaxbOneToOne();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public JaxbSequenceGenerator createJaxbSequenceGenerator()
/*  59:    */   {
/*  60:124 */     return new JaxbSequenceGenerator();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public JaxbTransient createJaxbTransient()
/*  64:    */   {
/*  65:132 */     return new JaxbTransient();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public JaxbDiscriminatorColumn createJaxbDiscriminatorColumn()
/*  69:    */   {
/*  70:140 */     return new JaxbDiscriminatorColumn();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public JaxbLob createJaxbLob()
/*  74:    */   {
/*  75:148 */     return new JaxbLob();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public JaxbGeneratedValue createJaxbGeneratedValue()
/*  79:    */   {
/*  80:156 */     return new JaxbGeneratedValue();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public JaxbMapKey createJaxbMapKey()
/*  84:    */   {
/*  85:164 */     return new JaxbMapKey();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public JaxbEntityResult createJaxbEntityResult()
/*  89:    */   {
/*  90:172 */     return new JaxbEntityResult();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public JaxbSecondaryTable createJaxbSecondaryTable()
/*  94:    */   {
/*  95:180 */     return new JaxbSecondaryTable();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public JaxbEntityMappings createJaxbEntityMappings()
/*  99:    */   {
/* 100:188 */     return new JaxbEntityMappings();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public JaxbOneToMany createJaxbOneToMany()
/* 104:    */   {
/* 105:196 */     return new JaxbOneToMany();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public JaxbNamedQuery createJaxbNamedQuery()
/* 109:    */   {
/* 110:204 */     return new JaxbNamedQuery();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public JaxbUniqueConstraint createJaxbUniqueConstraint()
/* 114:    */   {
/* 115:212 */     return new JaxbUniqueConstraint();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public JaxbFieldResult createJaxbFieldResult()
/* 119:    */   {
/* 120:220 */     return new JaxbFieldResult();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public JaxbPrimaryKeyJoinColumn createJaxbPrimaryKeyJoinColumn()
/* 124:    */   {
/* 125:228 */     return new JaxbPrimaryKeyJoinColumn();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public JaxbInheritance createJaxbInheritance()
/* 129:    */   {
/* 130:236 */     return new JaxbInheritance();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public JaxbVersion createJaxbVersion()
/* 134:    */   {
/* 135:244 */     return new JaxbVersion();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public JaxbId createJaxbId()
/* 139:    */   {
/* 140:252 */     return new JaxbId();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public JaxbEmptyType createJaxbEmptyType()
/* 144:    */   {
/* 145:260 */     return new JaxbEmptyType();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public JaxbEmbeddedId createJaxbEmbeddedId()
/* 149:    */   {
/* 150:268 */     return new JaxbEmbeddedId();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public JaxbCascadeType createJaxbCascadeType()
/* 154:    */   {
/* 155:276 */     return new JaxbCascadeType();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public JaxbMapKeyColumn createJaxbMapKeyColumn()
/* 159:    */   {
/* 160:284 */     return new JaxbMapKeyColumn();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public JaxbEntityListener createJaxbEntityListener()
/* 164:    */   {
/* 165:292 */     return new JaxbEntityListener();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public JaxbPrePersist createJaxbPrePersist()
/* 169:    */   {
/* 170:300 */     return new JaxbPrePersist();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public JaxbColumn createJaxbColumn()
/* 174:    */   {
/* 175:308 */     return new JaxbColumn();
/* 176:    */   }
/* 177:    */   
/* 178:    */   public JaxbIdClass createJaxbIdClass()
/* 179:    */   {
/* 180:316 */     return new JaxbIdClass();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public JaxbAttributeOverride createJaxbAttributeOverride()
/* 184:    */   {
/* 185:324 */     return new JaxbAttributeOverride();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public JaxbAssociationOverride createJaxbAssociationOverride()
/* 189:    */   {
/* 190:332 */     return new JaxbAssociationOverride();
/* 191:    */   }
/* 192:    */   
/* 193:    */   public JaxbEmbedded createJaxbEmbedded()
/* 194:    */   {
/* 195:340 */     return new JaxbEmbedded();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public JaxbColumnResult createJaxbColumnResult()
/* 199:    */   {
/* 200:348 */     return new JaxbColumnResult();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public JaxbJoinColumn createJaxbJoinColumn()
/* 204:    */   {
/* 205:356 */     return new JaxbJoinColumn();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public JaxbEntity createJaxbEntity()
/* 209:    */   {
/* 210:364 */     return new JaxbEntity();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public JaxbPreRemove createJaxbPreRemove()
/* 214:    */   {
/* 215:372 */     return new JaxbPreRemove();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public JaxbEntityListeners createJaxbEntityListeners()
/* 219:    */   {
/* 220:380 */     return new JaxbEntityListeners();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public JaxbMapKeyClass createJaxbMapKeyClass()
/* 224:    */   {
/* 225:388 */     return new JaxbMapKeyClass();
/* 226:    */   }
/* 227:    */   
/* 228:    */   public JaxbTable createJaxbTable()
/* 229:    */   {
/* 230:396 */     return new JaxbTable();
/* 231:    */   }
/* 232:    */   
/* 233:    */   public JaxbPreUpdate createJaxbPreUpdate()
/* 234:    */   {
/* 235:404 */     return new JaxbPreUpdate();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public JaxbPersistenceUnitDefaults createJaxbPersistenceUnitDefaults()
/* 239:    */   {
/* 240:412 */     return new JaxbPersistenceUnitDefaults();
/* 241:    */   }
/* 242:    */   
/* 243:    */   public JaxbOrderColumn createJaxbOrderColumn()
/* 244:    */   {
/* 245:420 */     return new JaxbOrderColumn();
/* 246:    */   }
/* 247:    */   
/* 248:    */   public JaxbPostUpdate createJaxbPostUpdate()
/* 249:    */   {
/* 250:428 */     return new JaxbPostUpdate();
/* 251:    */   }
/* 252:    */   
/* 253:    */   public JaxbPostPersist createJaxbPostPersist()
/* 254:    */   {
/* 255:436 */     return new JaxbPostPersist();
/* 256:    */   }
/* 257:    */   
/* 258:    */   public JaxbBasic createJaxbBasic()
/* 259:    */   {
/* 260:444 */     return new JaxbBasic();
/* 261:    */   }
/* 262:    */   
/* 263:    */   public JaxbSqlResultSetMapping createJaxbSqlResultSetMapping()
/* 264:    */   {
/* 265:452 */     return new JaxbSqlResultSetMapping();
/* 266:    */   }
/* 267:    */   
/* 268:    */   public JaxbPostLoad createJaxbPostLoad()
/* 269:    */   {
/* 270:460 */     return new JaxbPostLoad();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public JaxbAttributes createJaxbAttributes()
/* 274:    */   {
/* 275:468 */     return new JaxbAttributes();
/* 276:    */   }
/* 277:    */   
/* 278:    */   public JaxbJoinTable createJaxbJoinTable()
/* 279:    */   {
/* 280:476 */     return new JaxbJoinTable();
/* 281:    */   }
/* 282:    */   
/* 283:    */   public JaxbCollectionTable createJaxbCollectionTable()
/* 284:    */   {
/* 285:484 */     return new JaxbCollectionTable();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public JaxbElementCollection createJaxbElementCollection()
/* 289:    */   {
/* 290:492 */     return new JaxbElementCollection();
/* 291:    */   }
/* 292:    */   
/* 293:    */   public JaxbManyToOne createJaxbManyToOne()
/* 294:    */   {
/* 295:500 */     return new JaxbManyToOne();
/* 296:    */   }
/* 297:    */   
/* 298:    */   public JaxbEmbeddable createJaxbEmbeddable()
/* 299:    */   {
/* 300:508 */     return new JaxbEmbeddable();
/* 301:    */   }
/* 302:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.ObjectFactory
 * JD-Core Version:    0.7.0.1
 */