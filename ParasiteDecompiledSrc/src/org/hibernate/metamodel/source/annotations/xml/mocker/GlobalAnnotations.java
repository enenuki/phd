/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.internal.CoreMessageLogger;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAttributes;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntity;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityMappings;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbId;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbNamedNativeQuery;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbNamedQuery;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbSequenceGenerator;
/*  17:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbSqlResultSetMapping;
/*  18:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTableGenerator;
/*  19:    */ import org.hibernate.internal.util.StringHelper;
/*  20:    */ import org.hibernate.metamodel.source.MappingException;
/*  21:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  22:    */ import org.jboss.jandex.AnnotationInstance;
/*  23:    */ import org.jboss.jandex.AnnotationValue;
/*  24:    */ import org.jboss.jandex.DotName;
/*  25:    */ import org.jboss.logging.Logger;
/*  26:    */ 
/*  27:    */ class GlobalAnnotations
/*  28:    */   implements JPADotNames
/*  29:    */ {
/*  30: 56 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, GlobalAnnotations.class.getName());
/*  31: 60 */   private Map<String, JaxbSequenceGenerator> sequenceGeneratorMap = new HashMap();
/*  32: 61 */   private Map<String, JaxbTableGenerator> tableGeneratorMap = new HashMap();
/*  33: 62 */   private Map<String, JaxbNamedQuery> namedQueryMap = new HashMap();
/*  34: 63 */   private Map<String, JaxbNamedNativeQuery> namedNativeQueryMap = new HashMap();
/*  35: 64 */   private Map<String, JaxbSqlResultSetMapping> sqlResultSetMappingMap = new HashMap();
/*  36: 65 */   private Map<DotName, List<AnnotationInstance>> annotationInstanceMap = new HashMap();
/*  37: 66 */   private List<AnnotationInstance> indexedAnnotationInstanceList = new ArrayList();
/*  38: 68 */   private Set<String> defaultNamedNativeQueryNames = new HashSet();
/*  39: 69 */   private Set<String> defaultNamedQueryNames = new HashSet();
/*  40: 70 */   private Set<String> defaultNamedGenerators = new HashSet();
/*  41: 71 */   private Set<String> defaultSqlResultSetMappingNames = new HashSet();
/*  42:    */   
/*  43:    */   Map<DotName, List<AnnotationInstance>> getAnnotationInstanceMap()
/*  44:    */   {
/*  45: 74 */     return this.annotationInstanceMap;
/*  46:    */   }
/*  47:    */   
/*  48:    */   AnnotationInstance push(DotName name, AnnotationInstance annotationInstance)
/*  49:    */   {
/*  50: 78 */     if ((name == null) || (annotationInstance == null)) {
/*  51: 79 */       return null;
/*  52:    */     }
/*  53: 81 */     List<AnnotationInstance> list = (List)this.annotationInstanceMap.get(name);
/*  54: 82 */     if (list == null)
/*  55:    */     {
/*  56: 83 */       list = new ArrayList();
/*  57: 84 */       this.annotationInstanceMap.put(name, list);
/*  58:    */     }
/*  59: 86 */     list.add(annotationInstance);
/*  60: 87 */     return annotationInstance;
/*  61:    */   }
/*  62:    */   
/*  63:    */   void addIndexedAnnotationInstance(List<AnnotationInstance> annotationInstanceList)
/*  64:    */   {
/*  65: 92 */     if (MockHelper.isNotEmpty(annotationInstanceList)) {
/*  66: 93 */       this.indexedAnnotationInstanceList.addAll(annotationInstanceList);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   boolean hasGlobalConfiguration()
/*  71:    */   {
/*  72:101 */     return (!this.namedQueryMap.isEmpty()) || (!this.namedNativeQueryMap.isEmpty()) || (!this.sequenceGeneratorMap.isEmpty()) || (!this.tableGeneratorMap.isEmpty()) || (!this.sqlResultSetMappingMap.isEmpty());
/*  73:    */   }
/*  74:    */   
/*  75:    */   Map<String, JaxbNamedNativeQuery> getNamedNativeQueryMap()
/*  76:    */   {
/*  77:106 */     return this.namedNativeQueryMap;
/*  78:    */   }
/*  79:    */   
/*  80:    */   Map<String, JaxbNamedQuery> getNamedQueryMap()
/*  81:    */   {
/*  82:110 */     return this.namedQueryMap;
/*  83:    */   }
/*  84:    */   
/*  85:    */   Map<String, JaxbSequenceGenerator> getSequenceGeneratorMap()
/*  86:    */   {
/*  87:114 */     return this.sequenceGeneratorMap;
/*  88:    */   }
/*  89:    */   
/*  90:    */   Map<String, JaxbSqlResultSetMapping> getSqlResultSetMappingMap()
/*  91:    */   {
/*  92:118 */     return this.sqlResultSetMappingMap;
/*  93:    */   }
/*  94:    */   
/*  95:    */   Map<String, JaxbTableGenerator> getTableGeneratorMap()
/*  96:    */   {
/*  97:122 */     return this.tableGeneratorMap;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void filterIndexedAnnotations()
/* 101:    */   {
/* 102:127 */     for (AnnotationInstance annotationInstance : this.indexedAnnotationInstanceList) {
/* 103:128 */       pushIfNotExist(annotationInstance);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void pushIfNotExist(AnnotationInstance annotationInstance)
/* 108:    */   {
/* 109:133 */     DotName annName = annotationInstance.name();
/* 110:134 */     boolean isNotExist = false;
/* 111:135 */     if (annName.equals(SQL_RESULT_SET_MAPPINGS))
/* 112:    */     {
/* 113:136 */       AnnotationInstance[] annotationInstances = annotationInstance.value().asNestedArray();
/* 114:137 */       for (AnnotationInstance ai : annotationInstances) {
/* 115:138 */         pushIfNotExist(ai);
/* 116:    */       }
/* 117:    */     }
/* 118:    */     else
/* 119:    */     {
/* 120:142 */       AnnotationValue value = annotationInstance.value("name");
/* 121:143 */       String name = value.asString();
/* 122:144 */       isNotExist = ((annName.equals(TABLE_GENERATOR)) && (!this.tableGeneratorMap.containsKey(name))) || ((annName.equals(SEQUENCE_GENERATOR)) && (!this.sequenceGeneratorMap.containsKey(name))) || ((annName.equals(NAMED_QUERY)) && (!this.namedQueryMap.containsKey(name))) || ((annName.equals(NAMED_NATIVE_QUERY)) && (!this.namedNativeQueryMap.containsKey(name))) || ((annName.equals(SQL_RESULT_SET_MAPPING)) && (!this.sqlResultSetMappingMap.containsKey(name)));
/* 123:    */     }
/* 124:150 */     if (isNotExist) {
/* 125:151 */       push(annName, annotationInstance);
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   void collectGlobalMappings(JaxbEntityMappings entityMappings, EntityMappingsMocker.Default defaults)
/* 130:    */   {
/* 131:156 */     for (JaxbSequenceGenerator generator : entityMappings.getSequenceGenerator())
/* 132:    */     {
/* 133:157 */       put(generator, defaults);
/* 134:158 */       this.defaultNamedGenerators.add(generator.getName());
/* 135:    */     }
/* 136:160 */     for (JaxbTableGenerator generator : entityMappings.getTableGenerator())
/* 137:    */     {
/* 138:161 */       put(generator, defaults);
/* 139:162 */       this.defaultNamedGenerators.add(generator.getName());
/* 140:    */     }
/* 141:164 */     for (JaxbNamedQuery namedQuery : entityMappings.getNamedQuery())
/* 142:    */     {
/* 143:165 */       put(namedQuery);
/* 144:166 */       this.defaultNamedQueryNames.add(namedQuery.getName());
/* 145:    */     }
/* 146:168 */     for (JaxbNamedNativeQuery namedNativeQuery : entityMappings.getNamedNativeQuery())
/* 147:    */     {
/* 148:169 */       put(namedNativeQuery);
/* 149:170 */       this.defaultNamedNativeQueryNames.add(namedNativeQuery.getName());
/* 150:    */     }
/* 151:172 */     for (JaxbSqlResultSetMapping sqlResultSetMapping : entityMappings.getSqlResultSetMapping())
/* 152:    */     {
/* 153:173 */       put(sqlResultSetMapping);
/* 154:174 */       this.defaultSqlResultSetMappingNames.add(sqlResultSetMapping.getName());
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   void collectGlobalMappings(JaxbEntity entity, EntityMappingsMocker.Default defaults)
/* 159:    */   {
/* 160:179 */     for (JaxbNamedQuery namedQuery : entity.getNamedQuery()) {
/* 161:180 */       if (!this.defaultNamedQueryNames.contains(namedQuery.getName())) {
/* 162:181 */         put(namedQuery);
/* 163:    */       } else {
/* 164:184 */         LOG.warn("Named Query [" + namedQuery.getName() + "] duplicated.");
/* 165:    */       }
/* 166:    */     }
/* 167:187 */     for (JaxbNamedNativeQuery namedNativeQuery : entity.getNamedNativeQuery()) {
/* 168:188 */       if (!this.defaultNamedNativeQueryNames.contains(namedNativeQuery.getName())) {
/* 169:189 */         put(namedNativeQuery);
/* 170:    */       } else {
/* 171:192 */         LOG.warn("Named native Query [" + namedNativeQuery.getName() + "] duplicated.");
/* 172:    */       }
/* 173:    */     }
/* 174:195 */     for (JaxbSqlResultSetMapping sqlResultSetMapping : entity.getSqlResultSetMapping()) {
/* 175:196 */       if (!this.defaultSqlResultSetMappingNames.contains(sqlResultSetMapping.getName())) {
/* 176:197 */         put(sqlResultSetMapping);
/* 177:    */       }
/* 178:    */     }
/* 179:200 */     JaxbSequenceGenerator sequenceGenerator = entity.getSequenceGenerator();
/* 180:201 */     if ((sequenceGenerator != null) && 
/* 181:202 */       (!this.defaultNamedGenerators.contains(sequenceGenerator.getName()))) {
/* 182:203 */       put(sequenceGenerator, defaults);
/* 183:    */     }
/* 184:206 */     JaxbTableGenerator tableGenerator = entity.getTableGenerator();
/* 185:207 */     if ((tableGenerator != null) && 
/* 186:208 */       (!this.defaultNamedGenerators.contains(tableGenerator.getName()))) {
/* 187:209 */       put(tableGenerator, defaults);
/* 188:    */     }
/* 189:212 */     JaxbAttributes attributes = entity.getAttributes();
/* 190:213 */     if (attributes != null) {
/* 191:214 */       for (JaxbId id : attributes.getId())
/* 192:    */       {
/* 193:215 */         sequenceGenerator = id.getSequenceGenerator();
/* 194:216 */         if (sequenceGenerator != null) {
/* 195:217 */           put(sequenceGenerator, defaults);
/* 196:    */         }
/* 197:219 */         tableGenerator = id.getTableGenerator();
/* 198:220 */         if (tableGenerator != null) {
/* 199:221 */           put(tableGenerator, defaults);
/* 200:    */         }
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   private static JaxbSequenceGenerator overrideGenerator(JaxbSequenceGenerator generator, EntityMappingsMocker.Default defaults)
/* 206:    */   {
/* 207:231 */     if ((StringHelper.isEmpty(generator.getSchema())) && (defaults != null)) {
/* 208:232 */       generator.setSchema(defaults.getSchema());
/* 209:    */     }
/* 210:234 */     if ((StringHelper.isEmpty(generator.getCatalog())) && (defaults != null)) {
/* 211:235 */       generator.setCatalog(defaults.getCatalog());
/* 212:    */     }
/* 213:237 */     return generator;
/* 214:    */   }
/* 215:    */   
/* 216:    */   private static JaxbTableGenerator overrideGenerator(JaxbTableGenerator generator, EntityMappingsMocker.Default defaults)
/* 217:    */   {
/* 218:244 */     if ((StringHelper.isEmpty(generator.getSchema())) && (defaults != null)) {
/* 219:245 */       generator.setSchema(defaults.getSchema());
/* 220:    */     }
/* 221:247 */     if ((StringHelper.isEmpty(generator.getCatalog())) && (defaults != null)) {
/* 222:248 */       generator.setCatalog(defaults.getCatalog());
/* 223:    */     }
/* 224:250 */     return generator;
/* 225:    */   }
/* 226:    */   
/* 227:    */   private void put(JaxbNamedNativeQuery query)
/* 228:    */   {
/* 229:254 */     if (query != null)
/* 230:    */     {
/* 231:255 */       checkQueryName(query.getName());
/* 232:256 */       this.namedNativeQueryMap.put(query.getName(), query);
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   private void checkQueryName(String name)
/* 237:    */   {
/* 238:261 */     if ((this.namedQueryMap.containsKey(name)) || (this.namedNativeQueryMap.containsKey(name))) {
/* 239:262 */       throw new MappingException("Duplicated query mapping " + name, null);
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   private void put(JaxbNamedQuery query)
/* 244:    */   {
/* 245:267 */     if (query != null)
/* 246:    */     {
/* 247:268 */       checkQueryName(query.getName());
/* 248:269 */       this.namedQueryMap.put(query.getName(), query);
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   private void put(JaxbSequenceGenerator generator, EntityMappingsMocker.Default defaults)
/* 253:    */   {
/* 254:274 */     if (generator != null)
/* 255:    */     {
/* 256:275 */       Object old = this.sequenceGeneratorMap.put(generator.getName(), overrideGenerator(generator, defaults));
/* 257:276 */       if (old != null) {
/* 258:277 */         LOG.duplicateGeneratorName(generator.getName());
/* 259:    */       }
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   private void put(JaxbTableGenerator generator, EntityMappingsMocker.Default defaults)
/* 264:    */   {
/* 265:283 */     if (generator != null)
/* 266:    */     {
/* 267:284 */       Object old = this.tableGeneratorMap.put(generator.getName(), overrideGenerator(generator, defaults));
/* 268:285 */       if (old != null) {
/* 269:286 */         LOG.duplicateGeneratorName(generator.getName());
/* 270:    */       }
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   private void put(JaxbSqlResultSetMapping mapping)
/* 275:    */   {
/* 276:292 */     if (mapping != null)
/* 277:    */     {
/* 278:293 */       Object old = this.sqlResultSetMappingMap.put(mapping.getName(), mapping);
/* 279:294 */       if (old != null) {
/* 280:295 */         throw new MappingException("Duplicated SQL result set mapping " + mapping.getName(), null);
/* 281:    */       }
/* 282:    */     }
/* 283:    */   }
/* 284:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.GlobalAnnotations
 * JD-Core Version:    0.7.0.1
 */