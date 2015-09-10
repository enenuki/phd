/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.dom4j.Attribute;
/*  12:    */ import org.dom4j.Element;
/*  13:    */ import org.hibernate.LockMode;
/*  14:    */ import org.hibernate.MappingException;
/*  15:    */ import org.hibernate.engine.ResultSetMappingDefinition;
/*  16:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryCollectionReturn;
/*  17:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryJoinReturn;
/*  18:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryRootReturn;
/*  19:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryScalarReturn;
/*  20:    */ import org.hibernate.internal.util.StringHelper;
/*  21:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  22:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  23:    */ import org.hibernate.mapping.Component;
/*  24:    */ import org.hibernate.mapping.PersistentClass;
/*  25:    */ import org.hibernate.mapping.Property;
/*  26:    */ import org.hibernate.mapping.ToOne;
/*  27:    */ import org.hibernate.mapping.Value;
/*  28:    */ import org.hibernate.type.Type;
/*  29:    */ import org.hibernate.type.TypeResolver;
/*  30:    */ 
/*  31:    */ public abstract class ResultSetMappingBinder
/*  32:    */ {
/*  33:    */   protected static ResultSetMappingDefinition buildResultSetMappingDefinition(Element resultSetElem, String path, Mappings mappings)
/*  34:    */   {
/*  35: 66 */     String resultSetName = resultSetElem.attribute("name").getValue();
/*  36: 67 */     if (path != null) {
/*  37: 68 */       resultSetName = path + '.' + resultSetName;
/*  38:    */     }
/*  39: 70 */     ResultSetMappingDefinition definition = new ResultSetMappingDefinition(resultSetName);
/*  40:    */     
/*  41: 72 */     int cnt = 0;
/*  42: 73 */     Iterator returns = resultSetElem.elementIterator();
/*  43: 74 */     while (returns.hasNext())
/*  44:    */     {
/*  45: 75 */       cnt++;
/*  46: 76 */       Element returnElem = (Element)returns.next();
/*  47: 77 */       String name = returnElem.getName();
/*  48: 78 */       if ("return-scalar".equals(name))
/*  49:    */       {
/*  50: 79 */         String column = returnElem.attributeValue("column");
/*  51: 80 */         String typeFromXML = HbmBinder.getTypeFromXML(returnElem);
/*  52: 81 */         Type type = null;
/*  53: 82 */         if (typeFromXML != null)
/*  54:    */         {
/*  55: 83 */           type = mappings.getTypeResolver().heuristicType(typeFromXML);
/*  56: 84 */           if (type == null) {
/*  57: 85 */             throw new MappingException("could not determine type " + type);
/*  58:    */           }
/*  59:    */         }
/*  60: 88 */         definition.addQueryReturn(new NativeSQLQueryScalarReturn(column, type));
/*  61:    */       }
/*  62: 90 */       else if ("return".equals(name))
/*  63:    */       {
/*  64: 91 */         definition.addQueryReturn(bindReturn(returnElem, mappings, cnt));
/*  65:    */       }
/*  66: 93 */       else if ("return-join".equals(name))
/*  67:    */       {
/*  68: 94 */         definition.addQueryReturn(bindReturnJoin(returnElem, mappings));
/*  69:    */       }
/*  70: 96 */       else if ("load-collection".equals(name))
/*  71:    */       {
/*  72: 97 */         definition.addQueryReturn(bindLoadCollection(returnElem, mappings));
/*  73:    */       }
/*  74:    */     }
/*  75:100 */     return definition;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private static NativeSQLQueryRootReturn bindReturn(Element returnElem, Mappings mappings, int elementCount)
/*  79:    */   {
/*  80:104 */     String alias = returnElem.attributeValue("alias");
/*  81:105 */     if (StringHelper.isEmpty(alias)) {
/*  82:106 */       alias = "alias_" + elementCount;
/*  83:    */     }
/*  84:109 */     String entityName = HbmBinder.getEntityName(returnElem, mappings);
/*  85:110 */     if (entityName == null) {
/*  86:111 */       throw new MappingException("<return alias='" + alias + "'> must specify either a class or entity-name");
/*  87:    */     }
/*  88:113 */     LockMode lockMode = getLockMode(returnElem.attributeValue("lock-mode"));
/*  89:    */     
/*  90:115 */     PersistentClass pc = mappings.getClass(entityName);
/*  91:116 */     Map propertyResults = bindPropertyResults(alias, returnElem, pc, mappings);
/*  92:    */     
/*  93:118 */     return new NativeSQLQueryRootReturn(alias, entityName, propertyResults, lockMode);
/*  94:    */   }
/*  95:    */   
/*  96:    */   private static NativeSQLQueryJoinReturn bindReturnJoin(Element returnElem, Mappings mappings)
/*  97:    */   {
/*  98:127 */     String alias = returnElem.attributeValue("alias");
/*  99:128 */     String roleAttribute = returnElem.attributeValue("property");
/* 100:129 */     LockMode lockMode = getLockMode(returnElem.attributeValue("lock-mode"));
/* 101:130 */     int dot = roleAttribute.lastIndexOf('.');
/* 102:131 */     if (dot == -1) {
/* 103:132 */       throw new MappingException("Role attribute for sql query return [alias=" + alias + "] not formatted correctly {owningAlias.propertyName}");
/* 104:    */     }
/* 105:137 */     String roleOwnerAlias = roleAttribute.substring(0, dot);
/* 106:138 */     String roleProperty = roleAttribute.substring(dot + 1);
/* 107:    */     
/* 108:    */ 
/* 109:141 */     Map propertyResults = bindPropertyResults(alias, returnElem, null, mappings);
/* 110:    */     
/* 111:143 */     return new NativeSQLQueryJoinReturn(alias, roleOwnerAlias, roleProperty, propertyResults, lockMode);
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static NativeSQLQueryCollectionReturn bindLoadCollection(Element returnElem, Mappings mappings)
/* 115:    */   {
/* 116:153 */     String alias = returnElem.attributeValue("alias");
/* 117:154 */     String collectionAttribute = returnElem.attributeValue("role");
/* 118:155 */     LockMode lockMode = getLockMode(returnElem.attributeValue("lock-mode"));
/* 119:156 */     int dot = collectionAttribute.lastIndexOf('.');
/* 120:157 */     if (dot == -1) {
/* 121:158 */       throw new MappingException("Collection attribute for sql query return [alias=" + alias + "] not formatted correctly {OwnerClassName.propertyName}");
/* 122:    */     }
/* 123:163 */     String ownerClassName = HbmBinder.getClassName(collectionAttribute.substring(0, dot), mappings);
/* 124:164 */     String ownerPropertyName = collectionAttribute.substring(dot + 1);
/* 125:    */     
/* 126:    */ 
/* 127:167 */     Map propertyResults = bindPropertyResults(alias, returnElem, null, mappings);
/* 128:    */     
/* 129:169 */     return new NativeSQLQueryCollectionReturn(alias, ownerClassName, ownerPropertyName, propertyResults, lockMode);
/* 130:    */   }
/* 131:    */   
/* 132:    */   private static Map bindPropertyResults(String alias, Element returnElement, PersistentClass pc, Mappings mappings)
/* 133:    */   {
/* 134:182 */     HashMap propertyresults = new HashMap();
/* 135:    */     
/* 136:184 */     Element discriminatorResult = returnElement.element("return-discriminator");
/* 137:185 */     if (discriminatorResult != null)
/* 138:    */     {
/* 139:186 */       ArrayList resultColumns = getResultColumns(discriminatorResult);
/* 140:187 */       propertyresults.put("class", ArrayHelper.toStringArray(resultColumns));
/* 141:    */     }
/* 142:189 */     Iterator iterator = returnElement.elementIterator("return-property");
/* 143:190 */     List properties = new ArrayList();
/* 144:191 */     List propertyNames = new ArrayList();
/* 145:192 */     while (iterator.hasNext())
/* 146:    */     {
/* 147:193 */       Element propertyresult = (Element)iterator.next();
/* 148:194 */       String name = propertyresult.attributeValue("name");
/* 149:195 */       if ((pc == null) || (name.indexOf('.') == -1))
/* 150:    */       {
/* 151:197 */         properties.add(propertyresult);
/* 152:198 */         propertyNames.add(name);
/* 153:    */       }
/* 154:    */       else
/* 155:    */       {
/* 156:207 */         if (pc == null) {
/* 157:208 */           throw new MappingException("dotted notation in <return-join> or <load_collection> not yet supported");
/* 158:    */         }
/* 159:209 */         int dotIndex = name.lastIndexOf('.');
/* 160:210 */         String reducedName = name.substring(0, dotIndex);
/* 161:211 */         Value value = pc.getRecursiveProperty(reducedName).getValue();
/* 162:    */         Iterator parentPropIter;
/* 163:213 */         if ((value instanceof Component))
/* 164:    */         {
/* 165:214 */           Component comp = (Component)value;
/* 166:215 */           parentPropIter = comp.getPropertyIterator();
/* 167:    */         }
/* 168:217 */         else if ((value instanceof ToOne))
/* 169:    */         {
/* 170:218 */           ToOne toOne = (ToOne)value;
/* 171:219 */           PersistentClass referencedPc = mappings.getClass(toOne.getReferencedEntityName());
/* 172:    */           Iterator parentPropIter;
/* 173:220 */           if (toOne.getReferencedPropertyName() != null) {
/* 174:    */             try
/* 175:    */             {
/* 176:222 */               parentPropIter = ((Component)referencedPc.getRecursiveProperty(toOne.getReferencedPropertyName()).getValue()).getPropertyIterator();
/* 177:    */             }
/* 178:    */             catch (ClassCastException e)
/* 179:    */             {
/* 180:224 */               throw new MappingException("dotted notation reference neither a component nor a many/one to one", e);
/* 181:    */             }
/* 182:    */           } else {
/* 183:    */             try
/* 184:    */             {
/* 185:229 */               if (referencedPc.getIdentifierMapper() == null) {
/* 186:230 */                 parentPropIter = ((Component)referencedPc.getIdentifierProperty().getValue()).getPropertyIterator();
/* 187:    */               } else {
/* 188:233 */                 parentPropIter = referencedPc.getIdentifierMapper().getPropertyIterator();
/* 189:    */               }
/* 190:    */             }
/* 191:    */             catch (ClassCastException e)
/* 192:    */             {
/* 193:    */               Iterator parentPropIter;
/* 194:237 */               throw new MappingException("dotted notation reference neither a component nor a many/one to one", e);
/* 195:    */             }
/* 196:    */           }
/* 197:    */         }
/* 198:    */         else
/* 199:    */         {
/* 200:242 */           throw new MappingException("dotted notation reference neither a component nor a many/one to one");
/* 201:    */         }
/* 202:    */         Iterator parentPropIter;
/* 203:244 */         boolean hasFollowers = false;
/* 204:245 */         List followers = new ArrayList();
/* 205:246 */         while (parentPropIter.hasNext())
/* 206:    */         {
/* 207:247 */           String currentPropertyName = ((Property)parentPropIter.next()).getName();
/* 208:248 */           String currentName = reducedName + '.' + currentPropertyName;
/* 209:249 */           if (hasFollowers) {
/* 210:250 */             followers.add(currentName);
/* 211:    */           }
/* 212:252 */           if (name.equals(currentName)) {
/* 213:252 */             hasFollowers = true;
/* 214:    */           }
/* 215:    */         }
/* 216:255 */         int index = propertyNames.size();
/* 217:256 */         int followersSize = followers.size();
/* 218:257 */         for (int loop = 0; loop < followersSize; loop++)
/* 219:    */         {
/* 220:258 */           String follower = (String)followers.get(loop);
/* 221:259 */           int currentIndex = getIndexOfFirstMatchingProperty(propertyNames, follower);
/* 222:260 */           index = (currentIndex != -1) && (currentIndex < index) ? currentIndex : index;
/* 223:    */         }
/* 224:262 */         propertyNames.add(index, name);
/* 225:263 */         properties.add(index, propertyresult);
/* 226:    */       }
/* 227:    */     }
/* 228:267 */     Set uniqueReturnProperty = new HashSet();
/* 229:268 */     iterator = properties.iterator();
/* 230:269 */     while (iterator.hasNext())
/* 231:    */     {
/* 232:270 */       Element propertyresult = (Element)iterator.next();
/* 233:271 */       String name = propertyresult.attributeValue("name");
/* 234:272 */       if ("class".equals(name)) {
/* 235:273 */         throw new MappingException("class is not a valid property name to use in a <return-property>, use <return-discriminator> instead");
/* 236:    */       }
/* 237:278 */       ArrayList allResultColumns = getResultColumns(propertyresult);
/* 238:280 */       if (allResultColumns.isEmpty()) {
/* 239:281 */         throw new MappingException("return-property for alias " + alias + " must specify at least one column or return-column name");
/* 240:    */       }
/* 241:286 */       if (uniqueReturnProperty.contains(name)) {
/* 242:287 */         throw new MappingException("duplicate return-property for property " + name + " on alias " + alias);
/* 243:    */       }
/* 244:292 */       uniqueReturnProperty.add(name);
/* 245:    */       
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:315 */       String key = name;
/* 268:316 */       ArrayList intermediateResults = (ArrayList)propertyresults.get(key);
/* 269:317 */       if (intermediateResults == null) {
/* 270:318 */         propertyresults.put(key, allResultColumns);
/* 271:    */       } else {
/* 272:321 */         intermediateResults.addAll(allResultColumns);
/* 273:    */       }
/* 274:    */     }
/* 275:325 */     Iterator entries = propertyresults.entrySet().iterator();
/* 276:326 */     while (entries.hasNext())
/* 277:    */     {
/* 278:327 */       Map.Entry entry = (Map.Entry)entries.next();
/* 279:328 */       if ((entry.getValue() instanceof ArrayList))
/* 280:    */       {
/* 281:329 */         ArrayList list = (ArrayList)entry.getValue();
/* 282:330 */         entry.setValue(list.toArray(new String[list.size()]));
/* 283:    */       }
/* 284:    */     }
/* 285:333 */     return propertyresults.isEmpty() ? CollectionHelper.EMPTY_MAP : propertyresults;
/* 286:    */   }
/* 287:    */   
/* 288:    */   private static int getIndexOfFirstMatchingProperty(List propertyNames, String follower)
/* 289:    */   {
/* 290:337 */     int propertySize = propertyNames.size();
/* 291:338 */     for (int propIndex = 0; propIndex < propertySize; propIndex++) {
/* 292:339 */       if (((String)propertyNames.get(propIndex)).startsWith(follower)) {
/* 293:340 */         return propIndex;
/* 294:    */       }
/* 295:    */     }
/* 296:343 */     return -1;
/* 297:    */   }
/* 298:    */   
/* 299:    */   private static ArrayList getResultColumns(Element propertyresult)
/* 300:    */   {
/* 301:347 */     String column = unquote(propertyresult.attributeValue("column"));
/* 302:348 */     ArrayList allResultColumns = new ArrayList();
/* 303:349 */     if (column != null) {
/* 304:349 */       allResultColumns.add(column);
/* 305:    */     }
/* 306:350 */     Iterator resultColumns = propertyresult.elementIterator("return-column");
/* 307:351 */     while (resultColumns.hasNext())
/* 308:    */     {
/* 309:352 */       Element element = (Element)resultColumns.next();
/* 310:353 */       allResultColumns.add(unquote(element.attributeValue("name")));
/* 311:    */     }
/* 312:355 */     return allResultColumns;
/* 313:    */   }
/* 314:    */   
/* 315:    */   private static String unquote(String name)
/* 316:    */   {
/* 317:359 */     if ((name != null) && (name.charAt(0) == '`')) {
/* 318:360 */       name = name.substring(1, name.length() - 1);
/* 319:    */     }
/* 320:362 */     return name;
/* 321:    */   }
/* 322:    */   
/* 323:    */   private static LockMode getLockMode(String lockMode)
/* 324:    */   {
/* 325:366 */     if ((lockMode == null) || ("read".equals(lockMode))) {
/* 326:367 */       return LockMode.READ;
/* 327:    */     }
/* 328:369 */     if ("none".equals(lockMode)) {
/* 329:370 */       return LockMode.NONE;
/* 330:    */     }
/* 331:372 */     if ("upgrade".equals(lockMode)) {
/* 332:373 */       return LockMode.UPGRADE;
/* 333:    */     }
/* 334:375 */     if ("upgrade-nowait".equals(lockMode)) {
/* 335:376 */       return LockMode.UPGRADE_NOWAIT;
/* 336:    */     }
/* 337:378 */     if ("write".equals(lockMode)) {
/* 338:379 */       return LockMode.WRITE;
/* 339:    */     }
/* 340:381 */     if ("force".equals(lockMode)) {
/* 341:382 */       return LockMode.FORCE;
/* 342:    */     }
/* 343:384 */     if ("optimistic".equals(lockMode)) {
/* 344:385 */       return LockMode.OPTIMISTIC;
/* 345:    */     }
/* 346:387 */     if ("optimistic_force_increment".equals(lockMode)) {
/* 347:388 */       return LockMode.OPTIMISTIC_FORCE_INCREMENT;
/* 348:    */     }
/* 349:390 */     if ("pessimistic_read".equals(lockMode)) {
/* 350:391 */       return LockMode.PESSIMISTIC_READ;
/* 351:    */     }
/* 352:393 */     if ("pessimistic_write".equals(lockMode)) {
/* 353:394 */       return LockMode.PESSIMISTIC_WRITE;
/* 354:    */     }
/* 355:396 */     if ("pessimistic_force_increment".equals(lockMode)) {
/* 356:397 */       return LockMode.PESSIMISTIC_FORCE_INCREMENT;
/* 357:    */     }
/* 358:400 */     throw new MappingException("unknown lockmode");
/* 359:    */   }
/* 360:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ResultSetMappingBinder
 * JD-Core Version:    0.7.0.1
 */