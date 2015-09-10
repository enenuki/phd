/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.JAXBElement;
/*   4:    */ import javax.xml.bind.annotation.XmlElementDecl;
/*   5:    */ import javax.xml.bind.annotation.XmlRegistry;
/*   6:    */ import javax.xml.namespace.QName;
/*   7:    */ 
/*   8:    */ @XmlRegistry
/*   9:    */ public class ObjectFactory
/*  10:    */ {
/*  11: 34 */   private static final QName _JaxbQueryElementQueryParam_QNAME = new QName("http://www.hibernate.org/xsd/hibernate-mapping", "query-param");
/*  12: 35 */   private static final QName _JaxbHibernateMappingJaxbFilterDefFilterParam_QNAME = new QName("http://www.hibernate.org/xsd/hibernate-mapping", "filter-param");
/*  13: 36 */   private static final QName _JaxbSqlQueryElementReturn_QNAME = new QName("http://www.hibernate.org/xsd/hibernate-mapping", "return");
/*  14: 37 */   private static final QName _JaxbSqlQueryElementSynchronize_QNAME = new QName("http://www.hibernate.org/xsd/hibernate-mapping", "synchronize");
/*  15: 38 */   private static final QName _JaxbSqlQueryElementReturnJoin_QNAME = new QName("http://www.hibernate.org/xsd/hibernate-mapping", "return-join");
/*  16: 39 */   private static final QName _JaxbSqlQueryElementReturnScalar_QNAME = new QName("http://www.hibernate.org/xsd/hibernate-mapping", "return-scalar");
/*  17: 40 */   private static final QName _JaxbSqlQueryElementLoadCollection_QNAME = new QName("http://www.hibernate.org/xsd/hibernate-mapping", "load-collection");
/*  18:    */   
/*  19:    */   public JaxbMapElement.JaxbMapKeyManyToMany createJaxbMapElementJaxbMapKeyManyToMany()
/*  20:    */   {
/*  21: 54 */     return new JaxbMapElement.JaxbMapKeyManyToMany();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public JaxbJoinedSubclassElement createJaxbJoinedSubclassElement()
/*  25:    */   {
/*  26: 62 */     return new JaxbJoinedSubclassElement();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JaxbSetElement createJaxbSetElement()
/*  30:    */   {
/*  31: 70 */     return new JaxbSetElement();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public JaxbColumnElement createJaxbColumnElement()
/*  35:    */   {
/*  36: 78 */     return new JaxbColumnElement();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public JaxbManyToManyElement createJaxbManyToManyElement()
/*  40:    */   {
/*  41: 86 */     return new JaxbManyToManyElement();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public JaxbCompositeElementElement createJaxbCompositeElementElement()
/*  45:    */   {
/*  46: 94 */     return new JaxbCompositeElementElement();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public JaxbArrayElement createJaxbArrayElement()
/*  50:    */   {
/*  51:102 */     return new JaxbArrayElement();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public JaxbLoadCollectionElement createJaxbLoadCollectionElement()
/*  55:    */   {
/*  56:110 */     return new JaxbLoadCollectionElement();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public JaxbPropertyElement createJaxbPropertyElement()
/*  60:    */   {
/*  61:118 */     return new JaxbPropertyElement();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public JaxbSqlDeleteAllElement createJaxbSqlDeleteAllElement()
/*  65:    */   {
/*  66:126 */     return new JaxbSqlDeleteAllElement();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public JaxbHibernateMapping.JaxbClass.JaxbNaturalId createJaxbHibernateMappingJaxbClassJaxbNaturalId()
/*  70:    */   {
/*  71:134 */     return new JaxbHibernateMapping.JaxbClass.JaxbNaturalId();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public JaxbSqlQueryElement createJaxbSqlQueryElement()
/*  75:    */   {
/*  76:142 */     return new JaxbSqlQueryElement();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public JaxbMapElement.JaxbCompositeIndex createJaxbMapElementJaxbCompositeIndex()
/*  80:    */   {
/*  81:150 */     return new JaxbMapElement.JaxbCompositeIndex();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public JaxbSynchronizeElement createJaxbSynchronizeElement()
/*  85:    */   {
/*  86:158 */     return new JaxbSynchronizeElement();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public JaxbHibernateMapping.JaxbClass.JaxbCompositeId createJaxbHibernateMappingJaxbClassJaxbCompositeId()
/*  90:    */   {
/*  91:166 */     return new JaxbHibernateMapping.JaxbClass.JaxbCompositeId();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public JaxbResultsetElement createJaxbResultsetElement()
/*  95:    */   {
/*  96:174 */     return new JaxbResultsetElement();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public JaxbHibernateMapping.JaxbDatabaseObject createJaxbHibernateMappingJaxbDatabaseObject()
/* 100:    */   {
/* 101:182 */     return new JaxbHibernateMapping.JaxbDatabaseObject();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public JaxbQueryElement createJaxbQueryElement()
/* 105:    */   {
/* 106:190 */     return new JaxbQueryElement();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public JaxbHibernateMapping.JaxbIdentifierGenerator createJaxbHibernateMappingJaxbIdentifierGenerator()
/* 110:    */   {
/* 111:198 */     return new JaxbHibernateMapping.JaxbIdentifierGenerator();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public JaxbHibernateMapping createJaxbHibernateMapping()
/* 115:    */   {
/* 116:206 */     return new JaxbHibernateMapping();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public JaxbMapElement.JaxbMapKey createJaxbMapElementJaxbMapKey()
/* 120:    */   {
/* 121:214 */     return new JaxbMapElement.JaxbMapKey();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public JaxbGeneratorElement createJaxbGeneratorElement()
/* 125:    */   {
/* 126:222 */     return new JaxbGeneratorElement();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public JaxbSubclassElement createJaxbSubclassElement()
/* 130:    */   {
/* 131:230 */     return new JaxbSubclassElement();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public JaxbHibernateMapping.JaxbFilterDef createJaxbHibernateMappingJaxbFilterDef()
/* 135:    */   {
/* 136:238 */     return new JaxbHibernateMapping.JaxbFilterDef();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public JaxbReturnScalarElement createJaxbReturnScalarElement()
/* 140:    */   {
/* 141:246 */     return new JaxbReturnScalarElement();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public JaxbMapElement.JaxbCompositeMapKey createJaxbMapElementJaxbCompositeMapKey()
/* 145:    */   {
/* 146:254 */     return new JaxbMapElement.JaxbCompositeMapKey();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public JaxbOneToOneElement createJaxbOneToOneElement()
/* 150:    */   {
/* 151:262 */     return new JaxbOneToOneElement();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public JaxbHibernateMapping.JaxbClass createJaxbHibernateMappingJaxbClass()
/* 155:    */   {
/* 156:270 */     return new JaxbHibernateMapping.JaxbClass();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public JaxbParentElement createJaxbParentElement()
/* 160:    */   {
/* 161:278 */     return new JaxbParentElement();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public JaxbKeyPropertyElement createJaxbKeyPropertyElement()
/* 165:    */   {
/* 166:286 */     return new JaxbKeyPropertyElement();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public JaxbKeyManyToOneElement createJaxbKeyManyToOneElement()
/* 170:    */   {
/* 171:294 */     return new JaxbKeyManyToOneElement();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public JaxbSqlUpdateElement createJaxbSqlUpdateElement()
/* 175:    */   {
/* 176:302 */     return new JaxbSqlUpdateElement();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public JaxbHibernateMapping.JaxbImport createJaxbHibernateMappingJaxbImport()
/* 180:    */   {
/* 181:310 */     return new JaxbHibernateMapping.JaxbImport();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public JaxbIdbagElement.JaxbCollectionId createJaxbIdbagElementJaxbCollectionId()
/* 185:    */   {
/* 186:318 */     return new JaxbIdbagElement.JaxbCollectionId();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public JaxbHibernateMapping.JaxbClass.JaxbDiscriminator createJaxbHibernateMappingJaxbClassJaxbDiscriminator()
/* 190:    */   {
/* 191:326 */     return new JaxbHibernateMapping.JaxbClass.JaxbDiscriminator();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public JaxbFetchProfileElement.JaxbFetch createJaxbFetchProfileElementJaxbFetch()
/* 195:    */   {
/* 196:334 */     return new JaxbFetchProfileElement.JaxbFetch();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public JaxbComponentElement createJaxbComponentElement()
/* 200:    */   {
/* 201:342 */     return new JaxbComponentElement();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public JaxbHibernateMapping.JaxbClass.JaxbTimestamp createJaxbHibernateMappingJaxbClassJaxbTimestamp()
/* 205:    */   {
/* 206:350 */     return new JaxbHibernateMapping.JaxbClass.JaxbTimestamp();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public JaxbMetaElement createJaxbMetaElement()
/* 210:    */   {
/* 211:358 */     return new JaxbMetaElement();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public JaxbPropertiesElement createJaxbPropertiesElement()
/* 215:    */   {
/* 216:366 */     return new JaxbPropertiesElement();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public JaxbHibernateMapping.JaxbClass.JaxbVersion createJaxbHibernateMappingJaxbClassJaxbVersion()
/* 220:    */   {
/* 221:374 */     return new JaxbHibernateMapping.JaxbClass.JaxbVersion();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public JaxbTypeElement createJaxbTypeElement()
/* 225:    */   {
/* 226:382 */     return new JaxbTypeElement();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public JaxbPrimitiveArrayElement createJaxbPrimitiveArrayElement()
/* 230:    */   {
/* 231:390 */     return new JaxbPrimitiveArrayElement();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public JaxbIdbagElement createJaxbIdbagElement()
/* 235:    */   {
/* 236:398 */     return new JaxbIdbagElement();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public JaxbHibernateMapping.JaxbClass.JaxbId createJaxbHibernateMappingJaxbClassJaxbId()
/* 240:    */   {
/* 241:406 */     return new JaxbHibernateMapping.JaxbClass.JaxbId();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public JaxbDynamicComponentElement createJaxbDynamicComponentElement()
/* 245:    */   {
/* 246:414 */     return new JaxbDynamicComponentElement();
/* 247:    */   }
/* 248:    */   
/* 249:    */   public JaxbOneToManyElement createJaxbOneToManyElement()
/* 250:    */   {
/* 251:422 */     return new JaxbOneToManyElement();
/* 252:    */   }
/* 253:    */   
/* 254:    */   public JaxbListElement createJaxbListElement()
/* 255:    */   {
/* 256:430 */     return new JaxbListElement();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public JaxbQueryParamElement createJaxbQueryParamElement()
/* 260:    */   {
/* 261:438 */     return new JaxbQueryParamElement();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public JaxbReturnElement createJaxbReturnElement()
/* 265:    */   {
/* 266:446 */     return new JaxbReturnElement();
/* 267:    */   }
/* 268:    */   
/* 269:    */   public JaxbHibernateMapping.JaxbDatabaseObject.JaxbDefinition createJaxbHibernateMappingJaxbDatabaseObjectJaxbDefinition()
/* 270:    */   {
/* 271:454 */     return new JaxbHibernateMapping.JaxbDatabaseObject.JaxbDefinition();
/* 272:    */   }
/* 273:    */   
/* 274:    */   public JaxbJoinElement createJaxbJoinElement()
/* 275:    */   {
/* 276:462 */     return new JaxbJoinElement();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public JaxbReturnElement.JaxbReturnDiscriminator createJaxbReturnElementJaxbReturnDiscriminator()
/* 280:    */   {
/* 281:470 */     return new JaxbReturnElement.JaxbReturnDiscriminator();
/* 282:    */   }
/* 283:    */   
/* 284:    */   public JaxbReturnPropertyElement createJaxbReturnPropertyElement()
/* 285:    */   {
/* 286:478 */     return new JaxbReturnPropertyElement();
/* 287:    */   }
/* 288:    */   
/* 289:    */   public JaxbManyToAnyElement createJaxbManyToAnyElement()
/* 290:    */   {
/* 291:486 */     return new JaxbManyToAnyElement();
/* 292:    */   }
/* 293:    */   
/* 294:    */   public JaxbAnyElement createJaxbAnyElement()
/* 295:    */   {
/* 296:494 */     return new JaxbAnyElement();
/* 297:    */   }
/* 298:    */   
/* 299:    */   public JaxbBagElement createJaxbBagElement()
/* 300:    */   {
/* 301:502 */     return new JaxbBagElement();
/* 302:    */   }
/* 303:    */   
/* 304:    */   public JaxbIndexElement createJaxbIndexElement()
/* 305:    */   {
/* 306:510 */     return new JaxbIndexElement();
/* 307:    */   }
/* 308:    */   
/* 309:    */   public JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam createJaxbHibernateMappingJaxbFilterDefJaxbFilterParam()
/* 310:    */   {
/* 311:518 */     return new JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam();
/* 312:    */   }
/* 313:    */   
/* 314:    */   public JaxbSqlDeleteElement createJaxbSqlDeleteElement()
/* 315:    */   {
/* 316:526 */     return new JaxbSqlDeleteElement();
/* 317:    */   }
/* 318:    */   
/* 319:    */   public JaxbReturnJoinElement createJaxbReturnJoinElement()
/* 320:    */   {
/* 321:534 */     return new JaxbReturnJoinElement();
/* 322:    */   }
/* 323:    */   
/* 324:    */   public JaxbNestedCompositeElementElement createJaxbNestedCompositeElementElement()
/* 325:    */   {
/* 326:542 */     return new JaxbNestedCompositeElementElement();
/* 327:    */   }
/* 328:    */   
/* 329:    */   public JaxbParamElement createJaxbParamElement()
/* 330:    */   {
/* 331:550 */     return new JaxbParamElement();
/* 332:    */   }
/* 333:    */   
/* 334:    */   public JaxbMapElement.JaxbIndexManyToMany createJaxbMapElementJaxbIndexManyToMany()
/* 335:    */   {
/* 336:558 */     return new JaxbMapElement.JaxbIndexManyToMany();
/* 337:    */   }
/* 338:    */   
/* 339:    */   public JaxbMetaValueElement createJaxbMetaValueElement()
/* 340:    */   {
/* 341:566 */     return new JaxbMetaValueElement();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public JaxbHibernateMapping.JaxbDatabaseObject.JaxbDialectScope createJaxbHibernateMappingJaxbDatabaseObjectJaxbDialectScope()
/* 345:    */   {
/* 346:574 */     return new JaxbHibernateMapping.JaxbDatabaseObject.JaxbDialectScope();
/* 347:    */   }
/* 348:    */   
/* 349:    */   public JaxbCacheElement createJaxbCacheElement()
/* 350:    */   {
/* 351:582 */     return new JaxbCacheElement();
/* 352:    */   }
/* 353:    */   
/* 354:    */   public JaxbElementElement createJaxbElementElement()
/* 355:    */   {
/* 356:590 */     return new JaxbElementElement();
/* 357:    */   }
/* 358:    */   
/* 359:    */   public JaxbSqlInsertElement createJaxbSqlInsertElement()
/* 360:    */   {
/* 361:598 */     return new JaxbSqlInsertElement();
/* 362:    */   }
/* 363:    */   
/* 364:    */   public JaxbReturnPropertyElement.JaxbReturnColumn createJaxbReturnPropertyElementJaxbReturnColumn()
/* 365:    */   {
/* 366:606 */     return new JaxbReturnPropertyElement.JaxbReturnColumn();
/* 367:    */   }
/* 368:    */   
/* 369:    */   public JaxbMapElement.JaxbIndexManyToAny createJaxbMapElementJaxbIndexManyToAny()
/* 370:    */   {
/* 371:614 */     return new JaxbMapElement.JaxbIndexManyToAny();
/* 372:    */   }
/* 373:    */   
/* 374:    */   public JaxbUnionSubclassElement createJaxbUnionSubclassElement()
/* 375:    */   {
/* 376:622 */     return new JaxbUnionSubclassElement();
/* 377:    */   }
/* 378:    */   
/* 379:    */   public JaxbMapElement createJaxbMapElement()
/* 380:    */   {
/* 381:630 */     return new JaxbMapElement();
/* 382:    */   }
/* 383:    */   
/* 384:    */   public JaxbTuplizerElement createJaxbTuplizerElement()
/* 385:    */   {
/* 386:638 */     return new JaxbTuplizerElement();
/* 387:    */   }
/* 388:    */   
/* 389:    */   public JaxbLoaderElement createJaxbLoaderElement()
/* 390:    */   {
/* 391:646 */     return new JaxbLoaderElement();
/* 392:    */   }
/* 393:    */   
/* 394:    */   public JaxbFetchProfileElement createJaxbFetchProfileElement()
/* 395:    */   {
/* 396:654 */     return new JaxbFetchProfileElement();
/* 397:    */   }
/* 398:    */   
/* 399:    */   public JaxbKeyElement createJaxbKeyElement()
/* 400:    */   {
/* 401:662 */     return new JaxbKeyElement();
/* 402:    */   }
/* 403:    */   
/* 404:    */   public JaxbListIndexElement createJaxbListIndexElement()
/* 405:    */   {
/* 406:670 */     return new JaxbListIndexElement();
/* 407:    */   }
/* 408:    */   
/* 409:    */   public JaxbManyToOneElement createJaxbManyToOneElement()
/* 410:    */   {
/* 411:678 */     return new JaxbManyToOneElement();
/* 412:    */   }
/* 413:    */   
/* 414:    */   public JaxbHibernateMapping.JaxbTypedef createJaxbHibernateMappingJaxbTypedef()
/* 415:    */   {
/* 416:686 */     return new JaxbHibernateMapping.JaxbTypedef();
/* 417:    */   }
/* 418:    */   
/* 419:    */   public JaxbFilterElement createJaxbFilterElement()
/* 420:    */   {
/* 421:694 */     return new JaxbFilterElement();
/* 422:    */   }
/* 423:    */   
/* 424:    */   @XmlElementDecl(namespace="http://www.hibernate.org/xsd/hibernate-mapping", name="query-param", scope=JaxbQueryElement.class)
/* 425:    */   public JAXBElement<JaxbQueryParamElement> createJaxbQueryElementQueryParam(JaxbQueryParamElement value)
/* 426:    */   {
/* 427:703 */     return new JAXBElement(_JaxbQueryElementQueryParam_QNAME, JaxbQueryParamElement.class, JaxbQueryElement.class, value);
/* 428:    */   }
/* 429:    */   
/* 430:    */   @XmlElementDecl(namespace="http://www.hibernate.org/xsd/hibernate-mapping", name="filter-param", scope=JaxbHibernateMapping.JaxbFilterDef.class)
/* 431:    */   public JAXBElement<JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam> createJaxbHibernateMappingJaxbFilterDefFilterParam(JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam value)
/* 432:    */   {
/* 433:712 */     return new JAXBElement(_JaxbHibernateMappingJaxbFilterDefFilterParam_QNAME, JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam.class, JaxbHibernateMapping.JaxbFilterDef.class, value);
/* 434:    */   }
/* 435:    */   
/* 436:    */   @XmlElementDecl(namespace="http://www.hibernate.org/xsd/hibernate-mapping", name="return", scope=JaxbSqlQueryElement.class)
/* 437:    */   public JAXBElement<JaxbReturnElement> createJaxbSqlQueryElementReturn(JaxbReturnElement value)
/* 438:    */   {
/* 439:721 */     return new JAXBElement(_JaxbSqlQueryElementReturn_QNAME, JaxbReturnElement.class, JaxbSqlQueryElement.class, value);
/* 440:    */   }
/* 441:    */   
/* 442:    */   @XmlElementDecl(namespace="http://www.hibernate.org/xsd/hibernate-mapping", name="synchronize", scope=JaxbSqlQueryElement.class)
/* 443:    */   public JAXBElement<JaxbSynchronizeElement> createJaxbSqlQueryElementSynchronize(JaxbSynchronizeElement value)
/* 444:    */   {
/* 445:730 */     return new JAXBElement(_JaxbSqlQueryElementSynchronize_QNAME, JaxbSynchronizeElement.class, JaxbSqlQueryElement.class, value);
/* 446:    */   }
/* 447:    */   
/* 448:    */   @XmlElementDecl(namespace="http://www.hibernate.org/xsd/hibernate-mapping", name="return-join", scope=JaxbSqlQueryElement.class)
/* 449:    */   public JAXBElement<JaxbReturnJoinElement> createJaxbSqlQueryElementReturnJoin(JaxbReturnJoinElement value)
/* 450:    */   {
/* 451:739 */     return new JAXBElement(_JaxbSqlQueryElementReturnJoin_QNAME, JaxbReturnJoinElement.class, JaxbSqlQueryElement.class, value);
/* 452:    */   }
/* 453:    */   
/* 454:    */   @XmlElementDecl(namespace="http://www.hibernate.org/xsd/hibernate-mapping", name="return-scalar", scope=JaxbSqlQueryElement.class)
/* 455:    */   public JAXBElement<JaxbReturnScalarElement> createJaxbSqlQueryElementReturnScalar(JaxbReturnScalarElement value)
/* 456:    */   {
/* 457:748 */     return new JAXBElement(_JaxbSqlQueryElementReturnScalar_QNAME, JaxbReturnScalarElement.class, JaxbSqlQueryElement.class, value);
/* 458:    */   }
/* 459:    */   
/* 460:    */   @XmlElementDecl(namespace="http://www.hibernate.org/xsd/hibernate-mapping", name="query-param", scope=JaxbSqlQueryElement.class)
/* 461:    */   public JAXBElement<JaxbQueryParamElement> createJaxbSqlQueryElementQueryParam(JaxbQueryParamElement value)
/* 462:    */   {
/* 463:757 */     return new JAXBElement(_JaxbQueryElementQueryParam_QNAME, JaxbQueryParamElement.class, JaxbSqlQueryElement.class, value);
/* 464:    */   }
/* 465:    */   
/* 466:    */   @XmlElementDecl(namespace="http://www.hibernate.org/xsd/hibernate-mapping", name="load-collection", scope=JaxbSqlQueryElement.class)
/* 467:    */   public JAXBElement<JaxbLoadCollectionElement> createJaxbSqlQueryElementLoadCollection(JaxbLoadCollectionElement value)
/* 468:    */   {
/* 469:766 */     return new JAXBElement(_JaxbSqlQueryElementLoadCollection_QNAME, JaxbLoadCollectionElement.class, JaxbSqlQueryElement.class, value);
/* 470:    */   }
/* 471:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.ObjectFactory
 * JD-Core Version:    0.7.0.1
 */