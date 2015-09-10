/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Random;
/*   6:    */ import java.util.Set;
/*   7:    */ import javax.persistence.AttributeOverride;
/*   8:    */ import javax.persistence.AttributeOverrides;
/*   9:    */ import javax.persistence.MapKeyClass;
/*  10:    */ import org.hibernate.AnnotationException;
/*  11:    */ import org.hibernate.AssertionFailure;
/*  12:    */ import org.hibernate.FetchMode;
/*  13:    */ import org.hibernate.MappingException;
/*  14:    */ import org.hibernate.annotations.MapKeyType;
/*  15:    */ import org.hibernate.annotations.Type;
/*  16:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  17:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  18:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  19:    */ import org.hibernate.cfg.AccessType;
/*  20:    */ import org.hibernate.cfg.AnnotatedClassType;
/*  21:    */ import org.hibernate.cfg.AnnotationBinder;
/*  22:    */ import org.hibernate.cfg.BinderHelper;
/*  23:    */ import org.hibernate.cfg.CollectionSecondPass;
/*  24:    */ import org.hibernate.cfg.Ejb3Column;
/*  25:    */ import org.hibernate.cfg.Ejb3JoinColumn;
/*  26:    */ import org.hibernate.cfg.Mappings;
/*  27:    */ import org.hibernate.cfg.PropertyData;
/*  28:    */ import org.hibernate.cfg.PropertyHolder;
/*  29:    */ import org.hibernate.cfg.PropertyHolderBuilder;
/*  30:    */ import org.hibernate.cfg.PropertyPreloadedData;
/*  31:    */ import org.hibernate.cfg.SecondPass;
/*  32:    */ import org.hibernate.dialect.HSQLDialect;
/*  33:    */ import org.hibernate.internal.util.StringHelper;
/*  34:    */ import org.hibernate.mapping.Collection;
/*  35:    */ import org.hibernate.mapping.Column;
/*  36:    */ import org.hibernate.mapping.Component;
/*  37:    */ import org.hibernate.mapping.DependantValue;
/*  38:    */ import org.hibernate.mapping.Formula;
/*  39:    */ import org.hibernate.mapping.KeyValue;
/*  40:    */ import org.hibernate.mapping.ManyToOne;
/*  41:    */ import org.hibernate.mapping.OneToMany;
/*  42:    */ import org.hibernate.mapping.PersistentClass;
/*  43:    */ import org.hibernate.mapping.Property;
/*  44:    */ import org.hibernate.mapping.SimpleValue;
/*  45:    */ import org.hibernate.mapping.Table;
/*  46:    */ import org.hibernate.mapping.ToOne;
/*  47:    */ import org.hibernate.mapping.Value;
/*  48:    */ import org.hibernate.sql.Template;
/*  49:    */ 
/*  50:    */ public class MapBinder
/*  51:    */   extends CollectionBinder
/*  52:    */ {
/*  53:    */   public MapBinder(boolean sorted)
/*  54:    */   {
/*  55: 77 */     super(sorted);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public MapBinder() {}
/*  59:    */   
/*  60:    */   public boolean isMap()
/*  61:    */   {
/*  62: 85 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected Collection createCollection(PersistentClass persistentClass)
/*  66:    */   {
/*  67: 89 */     return new org.hibernate.mapping.Map(getMappings(), persistentClass);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public SecondPass getSecondPass(final Ejb3JoinColumn[] fkJoinColumns, final Ejb3JoinColumn[] keyColumns, final Ejb3JoinColumn[] inverseColumns, final Ejb3Column[] elementColumns, final Ejb3Column[] mapKeyColumns, final Ejb3JoinColumn[] mapKeyManyToManyColumns, final boolean isEmbedded, final XProperty property, final XClass collType, final boolean ignoreNotFound, final boolean unique, final TableBinder assocTableBinder, final Mappings mappings)
/*  71:    */   {
/*  72:107 */     new CollectionSecondPass(mappings, this.collection)
/*  73:    */     {
/*  74:    */       public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas)
/*  75:    */         throws MappingException
/*  76:    */       {
/*  77:110 */         MapBinder.this.bindStarToManySecondPass(persistentClasses, collType, fkJoinColumns, keyColumns, inverseColumns, elementColumns, isEmbedded, property, unique, assocTableBinder, ignoreNotFound, mappings);
/*  78:    */         
/*  79:    */ 
/*  80:    */ 
/*  81:114 */         MapBinder.this.bindKeyFromAssociationTable(collType, persistentClasses, MapBinder.this.mapKeyPropertyName, property, isEmbedded, mappings, mapKeyColumns, mapKeyManyToManyColumns, inverseColumns != null ? inverseColumns[0].getPropertyName() : null);
/*  82:    */       }
/*  83:    */     };
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void bindKeyFromAssociationTable(XClass collType, java.util.Map persistentClasses, String mapKeyPropertyName, XProperty property, boolean isEmbedded, Mappings mappings, Ejb3Column[] mapKeyColumns, Ejb3JoinColumn[] mapKeyManyToManyColumns, String targetPropertyName)
/*  87:    */   {
/*  88:133 */     if (mapKeyPropertyName != null)
/*  89:    */     {
/*  90:135 */       PersistentClass associatedClass = (PersistentClass)persistentClasses.get(collType.getName());
/*  91:136 */       if (associatedClass == null) {
/*  92:136 */         throw new AnnotationException("Associated class not found: " + collType);
/*  93:    */       }
/*  94:137 */       Property mapProperty = BinderHelper.findPropertyByName(associatedClass, mapKeyPropertyName);
/*  95:138 */       if (mapProperty == null) {
/*  96:139 */         throw new AnnotationException("Map key property not found: " + collType + "." + mapKeyPropertyName);
/*  97:    */       }
/*  98:143 */       org.hibernate.mapping.Map map = (org.hibernate.mapping.Map)this.collection;
/*  99:144 */       Value indexValue = createFormulatedValue(mapProperty.getValue(), map, targetPropertyName, associatedClass, mappings);
/* 100:    */       
/* 101:    */ 
/* 102:147 */       map.setIndex(indexValue);
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:153 */       Class target = Void.TYPE;
/* 107:158 */       if (property.isAnnotationPresent(MapKeyClass.class)) {
/* 108:159 */         target = ((MapKeyClass)property.getAnnotation(MapKeyClass.class)).value();
/* 109:    */       }
/* 110:    */       String mapKeyType;
/* 111:    */       String mapKeyType;
/* 112:161 */       if (!Void.TYPE.equals(target)) {
/* 113:162 */         mapKeyType = target.getName();
/* 114:    */       } else {
/* 115:165 */         mapKeyType = property.getMapKey().getName();
/* 116:    */       }
/* 117:167 */       PersistentClass collectionEntity = (PersistentClass)persistentClasses.get(mapKeyType);
/* 118:168 */       boolean isIndexOfEntities = collectionEntity != null;
/* 119:169 */       ManyToOne element = null;
/* 120:170 */       org.hibernate.mapping.Map mapValue = (org.hibernate.mapping.Map)this.collection;
/* 121:171 */       if (isIndexOfEntities)
/* 122:    */       {
/* 123:172 */         element = new ManyToOne(mappings, mapValue.getCollectionTable());
/* 124:173 */         mapValue.setIndex(element);
/* 125:174 */         element.setReferencedEntityName(mapKeyType);
/* 126:    */         
/* 127:    */ 
/* 128:    */ 
/* 129:178 */         element.setFetchMode(FetchMode.JOIN);
/* 130:179 */         element.setLazy(false);
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:185 */         PropertyHolder holder = null;
/* 135:    */         XClass elementClass;
/* 136:    */         XClass elementClass;
/* 137:    */         AnnotatedClassType classType;
/* 138:186 */         if (BinderHelper.PRIMITIVE_NAMES.contains(mapKeyType))
/* 139:    */         {
/* 140:187 */           AnnotatedClassType classType = AnnotatedClassType.NONE;
/* 141:188 */           elementClass = null;
/* 142:    */         }
/* 143:    */         else
/* 144:    */         {
/* 145:    */           try
/* 146:    */           {
/* 147:192 */             elementClass = mappings.getReflectionManager().classForName(mapKeyType, MapBinder.class);
/* 148:    */           }
/* 149:    */           catch (ClassNotFoundException e)
/* 150:    */           {
/* 151:195 */             throw new AnnotationException("Unable to find class: " + mapKeyType, e);
/* 152:    */           }
/* 153:197 */           classType = mappings.getClassType(elementClass);
/* 154:    */           
/* 155:199 */           holder = PropertyHolderBuilder.buildPropertyHolder(mapValue, StringHelper.qualify(mapValue.getRole(), "mapkey"), elementClass, property, this.propertyHolder, mappings);
/* 156:    */           
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:206 */           boolean attributeOverride = (property.isAnnotationPresent(AttributeOverride.class)) || (property.isAnnotationPresent(AttributeOverrides.class));
/* 163:208 */           if ((isEmbedded) || (attributeOverride)) {
/* 164:209 */             classType = AnnotatedClassType.EMBEDDABLE;
/* 165:    */           }
/* 166:    */         }
/* 167:213 */         if (AnnotatedClassType.EMBEDDABLE.equals(classType))
/* 168:    */         {
/* 169:214 */           EntityBinder entityBinder = new EntityBinder();
/* 170:215 */           PersistentClass owner = mapValue.getOwner();
/* 171:    */           boolean isPropertyAnnotated;
/* 172:219 */           if (owner.getIdentifierProperty() != null)
/* 173:    */           {
/* 174:220 */             isPropertyAnnotated = owner.getIdentifierProperty().getPropertyAccessorName().equals("property");
/* 175:    */           }
/* 176:    */           else
/* 177:    */           {
/* 178:    */             boolean isPropertyAnnotated;
/* 179:225 */             if ((owner.getIdentifierMapper() != null) && (owner.getIdentifierMapper().getPropertySpan() > 0))
/* 180:    */             {
/* 181:226 */               Property prop = (Property)owner.getIdentifierMapper().getPropertyIterator().next();
/* 182:227 */               isPropertyAnnotated = prop.getPropertyAccessorName().equals("property");
/* 183:    */             }
/* 184:    */             else
/* 185:    */             {
/* 186:230 */               throw new AssertionFailure("Unable to guess collection property accessor name");
/* 187:    */             }
/* 188:    */           }
/* 189:    */           boolean isPropertyAnnotated;
/* 190:    */           PropertyData inferredData;
/* 191:    */           PropertyData inferredData;
/* 192:235 */           if (isHibernateExtensionMapping()) {
/* 193:236 */             inferredData = new PropertyPreloadedData(AccessType.PROPERTY, "index", elementClass);
/* 194:    */           } else {
/* 195:240 */             inferredData = new PropertyPreloadedData(AccessType.PROPERTY, "key", elementClass);
/* 196:    */           }
/* 197:244 */           Component component = AnnotationBinder.fillComponent(holder, inferredData, isPropertyAnnotated ? AccessType.PROPERTY : AccessType.FIELD, true, entityBinder, false, false, true, mappings, this.inheritanceStatePerClass);
/* 198:    */           
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:249 */           mapValue.setIndex(component);
/* 203:    */         }
/* 204:    */         else
/* 205:    */         {
/* 206:252 */           SimpleValueBinder elementBinder = new SimpleValueBinder();
/* 207:253 */           elementBinder.setMappings(mappings);
/* 208:254 */           elementBinder.setReturnedClassName(mapKeyType);
/* 209:    */           
/* 210:256 */           Ejb3Column[] elementColumns = mapKeyColumns;
/* 211:257 */           if ((elementColumns == null) || (elementColumns.length == 0))
/* 212:    */           {
/* 213:258 */             elementColumns = new Ejb3Column[1];
/* 214:259 */             Ejb3Column column = new Ejb3Column();
/* 215:260 */             column.setImplicit(false);
/* 216:261 */             column.setNullable(true);
/* 217:262 */             column.setLength(255);
/* 218:263 */             column.setLogicalColumnName("id");
/* 219:    */             
/* 220:265 */             column.setJoins(new HashMap());
/* 221:266 */             column.setMappings(mappings);
/* 222:267 */             column.bind();
/* 223:268 */             elementColumns[0] = column;
/* 224:    */           }
/* 225:271 */           for (Ejb3Column column : elementColumns) {
/* 226:272 */             column.setTable(mapValue.getCollectionTable());
/* 227:    */           }
/* 228:274 */           elementBinder.setColumns(elementColumns);
/* 229:    */           
/* 230:    */ 
/* 231:277 */           elementBinder.setKey(true);
/* 232:278 */           MapKeyType mapKeyTypeAnnotation = (MapKeyType)property.getAnnotation(MapKeyType.class);
/* 233:279 */           if ((mapKeyTypeAnnotation != null) && (!BinderHelper.isEmptyAnnotationValue(mapKeyTypeAnnotation.value().type()))) {
/* 234:283 */             elementBinder.setExplicitType(mapKeyTypeAnnotation.value());
/* 235:    */           } else {
/* 236:286 */             elementBinder.setType(property, elementClass);
/* 237:    */           }
/* 238:288 */           mapValue.setIndex(elementBinder.make());
/* 239:    */         }
/* 240:    */       }
/* 241:292 */       if (!this.collection.isOneToMany()) {
/* 242:294 */         for (Ejb3JoinColumn col : mapKeyManyToManyColumns) {
/* 243:295 */           col.forceNotNull();
/* 244:    */         }
/* 245:    */       }
/* 246:298 */       if (isIndexOfEntities) {
/* 247:299 */         bindManytoManyInverseFk(collectionEntity, mapKeyManyToManyColumns, element, false, mappings);
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   protected Value createFormulatedValue(Value value, Collection collection, String targetPropertyName, PersistentClass associatedClass, Mappings mappings)
/* 253:    */   {
/* 254:316 */     Value element = collection.getElement();
/* 255:317 */     String fromAndWhere = null;
/* 256:318 */     if (!(element instanceof OneToMany))
/* 257:    */     {
/* 258:319 */       String referencedPropertyName = null;
/* 259:320 */       if ((element instanceof ToOne)) {
/* 260:321 */         referencedPropertyName = ((ToOne)element).getReferencedPropertyName();
/* 261:323 */       } else if ((element instanceof DependantValue)) {
/* 262:325 */         if (this.propertyName != null) {
/* 263:326 */           referencedPropertyName = collection.getReferencedPropertyName();
/* 264:    */         } else {
/* 265:329 */           throw new AnnotationException("SecondaryTable JoinColumn cannot reference a non primary key");
/* 266:    */         }
/* 267:    */       }
/* 268:    */       Iterator referencedEntityColumns;
/* 269:    */       Iterator referencedEntityColumns;
/* 270:333 */       if (referencedPropertyName == null)
/* 271:    */       {
/* 272:334 */         referencedEntityColumns = associatedClass.getIdentifier().getColumnIterator();
/* 273:    */       }
/* 274:    */       else
/* 275:    */       {
/* 276:337 */         Property referencedProperty = associatedClass.getRecursiveProperty(referencedPropertyName);
/* 277:338 */         referencedEntityColumns = referencedProperty.getColumnIterator();
/* 278:    */       }
/* 279:340 */       String alias = "$alias$";
/* 280:341 */       StringBuilder fromAndWhereSb = new StringBuilder(" from ").append(associatedClass.getTable().getName()).append(" ").append(alias).append(" where ");
/* 281:    */       
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:346 */       Iterator collectionTableColumns = element.getColumnIterator();
/* 286:347 */       while (collectionTableColumns.hasNext())
/* 287:    */       {
/* 288:348 */         Column colColumn = (Column)collectionTableColumns.next();
/* 289:349 */         Column refColumn = (Column)referencedEntityColumns.next();
/* 290:350 */         fromAndWhereSb.append(alias).append('.').append(refColumn.getQuotedName()).append('=').append(colColumn.getQuotedName()).append(" and ");
/* 291:    */       }
/* 292:353 */       fromAndWhere = fromAndWhereSb.substring(0, fromAndWhereSb.length() - 5);
/* 293:    */     }
/* 294:356 */     if ((value instanceof Component))
/* 295:    */     {
/* 296:357 */       Component component = (Component)value;
/* 297:358 */       Iterator properties = component.getPropertyIterator();
/* 298:359 */       Component indexComponent = new Component(mappings, collection);
/* 299:360 */       indexComponent.setComponentClassName(component.getComponentClassName());
/* 300:    */       
/* 301:362 */       indexComponent.setNodeName("index");
/* 302:363 */       while (properties.hasNext())
/* 303:    */       {
/* 304:364 */         Property current = (Property)properties.next();
/* 305:365 */         Property newProperty = new Property();
/* 306:366 */         newProperty.setCascade(current.getCascade());
/* 307:367 */         newProperty.setGeneration(current.getGeneration());
/* 308:368 */         newProperty.setInsertable(false);
/* 309:369 */         newProperty.setUpdateable(false);
/* 310:370 */         newProperty.setMetaAttributes(current.getMetaAttributes());
/* 311:371 */         newProperty.setName(current.getName());
/* 312:372 */         newProperty.setNodeName(current.getNodeName());
/* 313:373 */         newProperty.setNaturalIdentifier(false);
/* 314:    */         
/* 315:375 */         newProperty.setOptional(false);
/* 316:376 */         newProperty.setPersistentClass(current.getPersistentClass());
/* 317:377 */         newProperty.setPropertyAccessorName(current.getPropertyAccessorName());
/* 318:378 */         newProperty.setSelectable(current.isSelectable());
/* 319:379 */         newProperty.setValue(createFormulatedValue(current.getValue(), collection, targetPropertyName, associatedClass, mappings));
/* 320:    */         
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:384 */         indexComponent.addProperty(newProperty);
/* 325:    */       }
/* 326:386 */       return indexComponent;
/* 327:    */     }
/* 328:388 */     if ((value instanceof SimpleValue))
/* 329:    */     {
/* 330:389 */       SimpleValue sourceValue = (SimpleValue)value;
/* 331:    */       SimpleValue targetValue;
/* 332:    */       SimpleValue targetValue;
/* 333:391 */       if ((value instanceof ManyToOne))
/* 334:    */       {
/* 335:392 */         ManyToOne sourceManyToOne = (ManyToOne)sourceValue;
/* 336:393 */         ManyToOne targetManyToOne = new ManyToOne(mappings, collection.getCollectionTable());
/* 337:394 */         targetManyToOne.setFetchMode(FetchMode.DEFAULT);
/* 338:395 */         targetManyToOne.setLazy(true);
/* 339:    */         
/* 340:397 */         targetManyToOne.setReferencedEntityName(sourceManyToOne.getReferencedEntityName());
/* 341:398 */         targetValue = targetManyToOne;
/* 342:    */       }
/* 343:    */       else
/* 344:    */       {
/* 345:401 */         targetValue = new SimpleValue(mappings, collection.getCollectionTable());
/* 346:402 */         targetValue.setTypeName(sourceValue.getTypeName());
/* 347:403 */         targetValue.setTypeParameters(sourceValue.getTypeParameters());
/* 348:    */       }
/* 349:405 */       Iterator columns = sourceValue.getColumnIterator();
/* 350:406 */       Random random = new Random();
/* 351:407 */       while (columns.hasNext())
/* 352:    */       {
/* 353:408 */         Object current = columns.next();
/* 354:409 */         Formula formula = new Formula();
/* 355:    */         String formulaString;
/* 356:411 */         if ((current instanceof Column))
/* 357:    */         {
/* 358:412 */           formulaString = ((Column)current).getQuotedName();
/* 359:    */         }
/* 360:    */         else
/* 361:    */         {
/* 362:    */           String formulaString;
/* 363:414 */           if ((current instanceof Formula)) {
/* 364:415 */             formulaString = ((Formula)current).getFormula();
/* 365:    */           } else {
/* 366:418 */             throw new AssertionFailure("Unknown element in column iterator: " + current.getClass());
/* 367:    */           }
/* 368:    */         }
/* 369:    */         String formulaString;
/* 370:420 */         if (fromAndWhere != null)
/* 371:    */         {
/* 372:421 */           formulaString = Template.renderWhereStringTemplate(formulaString, "$alias$", new HSQLDialect());
/* 373:422 */           formulaString = "(select " + formulaString + fromAndWhere + ")";
/* 374:423 */           formulaString = StringHelper.replace(formulaString, "$alias$", "a" + random.nextInt(16));
/* 375:    */         }
/* 376:429 */         formula.setFormula(formulaString);
/* 377:430 */         targetValue.addFormula(formula);
/* 378:    */       }
/* 379:433 */       return targetValue;
/* 380:    */     }
/* 381:436 */     throw new AssertionFailure("Unknown type encounters for map key: " + value.getClass());
/* 382:    */   }
/* 383:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.MapBinder
 * JD-Core Version:    0.7.0.1
 */