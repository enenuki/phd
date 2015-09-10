/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbColumnResult;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityResult;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbFieldResult;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbNamedNativeQuery;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbNamedQuery;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbQueryHint;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbSequenceGenerator;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbSqlResultSetMapping;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTableGenerator;
/*  17:    */ import org.jboss.jandex.AnnotationInstance;
/*  18:    */ import org.jboss.jandex.AnnotationValue;
/*  19:    */ 
/*  20:    */ class GlobalAnnotationMocker
/*  21:    */   extends AbstractMocker
/*  22:    */ {
/*  23:    */   private GlobalAnnotations globalAnnotations;
/*  24:    */   
/*  25:    */   GlobalAnnotationMocker(IndexBuilder indexBuilder, GlobalAnnotations globalAnnotations)
/*  26:    */   {
/*  27: 51 */     super(indexBuilder);
/*  28: 52 */     this.globalAnnotations = globalAnnotations;
/*  29:    */   }
/*  30:    */   
/*  31:    */   void process()
/*  32:    */   {
/*  33: 57 */     if (!this.globalAnnotations.getTableGeneratorMap().isEmpty()) {
/*  34: 58 */       for (JaxbTableGenerator generator : this.globalAnnotations.getTableGeneratorMap().values()) {
/*  35: 59 */         parserTableGenerator(generator);
/*  36:    */       }
/*  37:    */     }
/*  38: 62 */     if (!this.globalAnnotations.getSequenceGeneratorMap().isEmpty()) {
/*  39: 63 */       for (JaxbSequenceGenerator generator : this.globalAnnotations.getSequenceGeneratorMap().values()) {
/*  40: 64 */         parserSequenceGenerator(generator);
/*  41:    */       }
/*  42:    */     }
/*  43: 67 */     if (!this.globalAnnotations.getNamedQueryMap().isEmpty())
/*  44:    */     {
/*  45: 68 */       Collection<JaxbNamedQuery> namedQueries = this.globalAnnotations.getNamedQueryMap().values();
/*  46: 69 */       if (namedQueries.size() > 1) {
/*  47: 70 */         parserNamedQueries(namedQueries);
/*  48:    */       } else {
/*  49: 73 */         parserNamedQuery((JaxbNamedQuery)namedQueries.iterator().next());
/*  50:    */       }
/*  51:    */     }
/*  52: 76 */     if (!this.globalAnnotations.getNamedNativeQueryMap().isEmpty())
/*  53:    */     {
/*  54: 77 */       Collection<JaxbNamedNativeQuery> namedQueries = this.globalAnnotations.getNamedNativeQueryMap().values();
/*  55: 78 */       if (namedQueries.size() > 1) {
/*  56: 79 */         parserNamedNativeQueries(namedQueries);
/*  57:    */       } else {
/*  58: 82 */         parserNamedNativeQuery((JaxbNamedNativeQuery)namedQueries.iterator().next());
/*  59:    */       }
/*  60:    */     }
/*  61: 85 */     if (!this.globalAnnotations.getSqlResultSetMappingMap().isEmpty()) {
/*  62: 86 */       parserSqlResultSetMappings(this.globalAnnotations.getSqlResultSetMappingMap().values());
/*  63:    */     }
/*  64: 88 */     this.indexBuilder.finishGlobalConfigurationMocking(this.globalAnnotations);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private AnnotationInstance parserSqlResultSetMappings(Collection<JaxbSqlResultSetMapping> namedQueries)
/*  68:    */   {
/*  69: 92 */     AnnotationValue[] values = new AnnotationValue[namedQueries.size()];
/*  70: 93 */     int i = 0;
/*  71: 94 */     for (Iterator<JaxbSqlResultSetMapping> iterator = namedQueries.iterator(); iterator.hasNext();)
/*  72:    */     {
/*  73: 95 */       AnnotationInstance annotationInstance = parserSqlResultSetMapping((JaxbSqlResultSetMapping)iterator.next());
/*  74: 96 */       values[(i++)] = MockHelper.nestedAnnotationValue("", annotationInstance);
/*  75:    */     }
/*  76:100 */     return create(SQL_RESULT_SET_MAPPINGS, null, new AnnotationValue[] { AnnotationValue.createArrayValue("values", values) });
/*  77:    */   }
/*  78:    */   
/*  79:    */   private AnnotationInstance parserSqlResultSetMapping(JaxbSqlResultSetMapping mapping)
/*  80:    */   {
/*  81:111 */     List<AnnotationValue> annotationValueList = new ArrayList();
/*  82:112 */     MockHelper.stringValue("name", mapping.getName(), annotationValueList);
/*  83:113 */     nestedEntityResultList("entities", mapping.getEntityResult(), annotationValueList);
/*  84:114 */     nestedColumnResultList("columns", mapping.getColumnResult(), annotationValueList);
/*  85:115 */     return create(SQL_RESULT_SET_MAPPING, null, annotationValueList);
/*  86:    */   }
/*  87:    */   
/*  88:    */   private AnnotationInstance parserEntityResult(JaxbEntityResult result)
/*  89:    */   {
/*  90:126 */     List<AnnotationValue> annotationValueList = new ArrayList();
/*  91:127 */     MockHelper.stringValue("discriminatorColumn", result.getDiscriminatorColumn(), annotationValueList);
/*  92:    */     
/*  93:    */ 
/*  94:130 */     nestedFieldResultList("fields", result.getFieldResult(), annotationValueList);
/*  95:131 */     MockHelper.classValue("entityClass", result.getEntityClass(), annotationValueList, this.indexBuilder.getServiceRegistry());
/*  96:    */     
/*  97:    */ 
/*  98:134 */     return create(ENTITY_RESULT, null, annotationValueList);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private void nestedEntityResultList(String name, List<JaxbEntityResult> entityResults, List<AnnotationValue> annotationValueList)
/* 102:    */   {
/* 103:142 */     if (MockHelper.isNotEmpty(entityResults))
/* 104:    */     {
/* 105:143 */       AnnotationValue[] values = new AnnotationValue[entityResults.size()];
/* 106:144 */       for (int i = 0; i < entityResults.size(); i++)
/* 107:    */       {
/* 108:145 */         AnnotationInstance annotationInstance = parserEntityResult((JaxbEntityResult)entityResults.get(i));
/* 109:146 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 110:    */       }
/* 111:150 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   private AnnotationInstance parserColumnResult(JaxbColumnResult result)
/* 116:    */   {
/* 117:158 */     return create(COLUMN_RESULT, null, MockHelper.stringValueArray("name", result.getName()));
/* 118:    */   }
/* 119:    */   
/* 120:    */   private void nestedColumnResultList(String name, List<JaxbColumnResult> columnResults, List<AnnotationValue> annotationValueList)
/* 121:    */   {
/* 122:162 */     if (MockHelper.isNotEmpty(columnResults))
/* 123:    */     {
/* 124:163 */       AnnotationValue[] values = new AnnotationValue[columnResults.size()];
/* 125:164 */       for (int i = 0; i < columnResults.size(); i++)
/* 126:    */       {
/* 127:165 */         AnnotationInstance annotationInstance = parserColumnResult((JaxbColumnResult)columnResults.get(i));
/* 128:166 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 129:    */       }
/* 130:170 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   private AnnotationInstance parserFieldResult(JaxbFieldResult result)
/* 135:    */   {
/* 136:178 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 137:179 */     MockHelper.stringValue("name", result.getName(), annotationValueList);
/* 138:180 */     MockHelper.stringValue("column", result.getColumn(), annotationValueList);
/* 139:181 */     return create(FIELD_RESULT, null, annotationValueList);
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void nestedFieldResultList(String name, List<JaxbFieldResult> fieldResultList, List<AnnotationValue> annotationValueList)
/* 143:    */   {
/* 144:186 */     if (MockHelper.isNotEmpty(fieldResultList))
/* 145:    */     {
/* 146:187 */       AnnotationValue[] values = new AnnotationValue[fieldResultList.size()];
/* 147:188 */       for (int i = 0; i < fieldResultList.size(); i++)
/* 148:    */       {
/* 149:189 */         AnnotationInstance annotationInstance = parserFieldResult((JaxbFieldResult)fieldResultList.get(i));
/* 150:190 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 151:    */       }
/* 152:194 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private AnnotationInstance parserNamedNativeQueries(Collection<JaxbNamedNativeQuery> namedQueries)
/* 157:    */   {
/* 158:201 */     AnnotationValue[] values = new AnnotationValue[namedQueries.size()];
/* 159:202 */     int i = 0;
/* 160:203 */     for (Iterator<JaxbNamedNativeQuery> iterator = namedQueries.iterator(); iterator.hasNext();)
/* 161:    */     {
/* 162:204 */       AnnotationInstance annotationInstance = parserNamedNativeQuery((JaxbNamedNativeQuery)iterator.next());
/* 163:205 */       values[(i++)] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 164:    */     }
/* 165:209 */     return create(NAMED_NATIVE_QUERIES, null, new AnnotationValue[] { AnnotationValue.createArrayValue("values", values) });
/* 166:    */   }
/* 167:    */   
/* 168:    */   private AnnotationInstance parserNamedNativeQuery(JaxbNamedNativeQuery namedNativeQuery)
/* 169:    */   {
/* 170:218 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 171:219 */     MockHelper.stringValue("name", namedNativeQuery.getName(), annotationValueList);
/* 172:220 */     MockHelper.stringValue("query", namedNativeQuery.getQuery(), annotationValueList);
/* 173:221 */     MockHelper.stringValue("resultSetMapping", namedNativeQuery.getResultSetMapping(), annotationValueList);
/* 174:    */     
/* 175:    */ 
/* 176:224 */     MockHelper.classValue("resultClass", namedNativeQuery.getResultClass(), annotationValueList, this.indexBuilder.getServiceRegistry());
/* 177:    */     
/* 178:    */ 
/* 179:227 */     nestedQueryHintList("hints", namedNativeQuery.getHint(), annotationValueList);
/* 180:228 */     return create(NAMED_NATIVE_QUERY, null, annotationValueList);
/* 181:    */   }
/* 182:    */   
/* 183:    */   private AnnotationInstance parserNamedQueries(Collection<JaxbNamedQuery> namedQueries)
/* 184:    */   {
/* 185:237 */     AnnotationValue[] values = new AnnotationValue[namedQueries.size()];
/* 186:238 */     int i = 0;
/* 187:239 */     for (Iterator<JaxbNamedQuery> iterator = namedQueries.iterator(); iterator.hasNext();)
/* 188:    */     {
/* 189:240 */       AnnotationInstance annotationInstance = parserNamedQuery((JaxbNamedQuery)iterator.next());
/* 190:241 */       values[(i++)] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 191:    */     }
/* 192:245 */     return create(NAMED_QUERIES, null, new AnnotationValue[] { AnnotationValue.createArrayValue("values", values) });
/* 193:    */   }
/* 194:    */   
/* 195:    */   private AnnotationInstance parserNamedQuery(JaxbNamedQuery namedQuery)
/* 196:    */   {
/* 197:255 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 198:256 */     MockHelper.stringValue("name", namedQuery.getName(), annotationValueList);
/* 199:257 */     MockHelper.stringValue("query", namedQuery.getQuery(), annotationValueList);
/* 200:258 */     MockHelper.enumValue("lockMode", LOCK_MODE_TYPE, namedQuery.getLockMode(), annotationValueList);
/* 201:259 */     nestedQueryHintList("hints", namedQuery.getHint(), annotationValueList);
/* 202:260 */     return create(NAMED_QUERY, null, annotationValueList);
/* 203:    */   }
/* 204:    */   
/* 205:    */   private AnnotationInstance parserQueryHint(JaxbQueryHint queryHint)
/* 206:    */   {
/* 207:265 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 208:266 */     MockHelper.stringValue("name", queryHint.getName(), annotationValueList);
/* 209:267 */     MockHelper.stringValue("value", queryHint.getValue(), annotationValueList);
/* 210:268 */     return create(QUERY_HINT, null, annotationValueList);
/* 211:    */   }
/* 212:    */   
/* 213:    */   private void nestedQueryHintList(String name, List<JaxbQueryHint> constraints, List<AnnotationValue> annotationValueList)
/* 214:    */   {
/* 215:273 */     if (MockHelper.isNotEmpty(constraints))
/* 216:    */     {
/* 217:274 */       AnnotationValue[] values = new AnnotationValue[constraints.size()];
/* 218:275 */       for (int i = 0; i < constraints.size(); i++)
/* 219:    */       {
/* 220:276 */         AnnotationInstance annotationInstance = parserQueryHint((JaxbQueryHint)constraints.get(i));
/* 221:277 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 222:    */       }
/* 223:281 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   private AnnotationInstance parserSequenceGenerator(JaxbSequenceGenerator generator)
/* 228:    */   {
/* 229:290 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 230:291 */     MockHelper.stringValue("name", generator.getName(), annotationValueList);
/* 231:292 */     MockHelper.stringValue("catalog", generator.getCatalog(), annotationValueList);
/* 232:293 */     MockHelper.stringValue("schema", generator.getSchema(), annotationValueList);
/* 233:294 */     MockHelper.stringValue("sequenceName", generator.getSequenceName(), annotationValueList);
/* 234:295 */     MockHelper.integerValue("initialValue", generator.getInitialValue(), annotationValueList);
/* 235:296 */     MockHelper.integerValue("allocationSize", generator.getAllocationSize(), annotationValueList);
/* 236:297 */     return create(SEQUENCE_GENERATOR, null, annotationValueList);
/* 237:    */   }
/* 238:    */   
/* 239:    */   private AnnotationInstance parserTableGenerator(JaxbTableGenerator generator)
/* 240:    */   {
/* 241:306 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 242:307 */     MockHelper.stringValue("name", generator.getName(), annotationValueList);
/* 243:308 */     MockHelper.stringValue("catalog", generator.getCatalog(), annotationValueList);
/* 244:309 */     MockHelper.stringValue("schema", generator.getSchema(), annotationValueList);
/* 245:310 */     MockHelper.stringValue("table", generator.getTable(), annotationValueList);
/* 246:311 */     MockHelper.stringValue("pkColumnName", generator.getPkColumnName(), annotationValueList);
/* 247:312 */     MockHelper.stringValue("valueColumnName", generator.getValueColumnName(), annotationValueList);
/* 248:313 */     MockHelper.stringValue("pkColumnValue", generator.getPkColumnValue(), annotationValueList);
/* 249:314 */     MockHelper.integerValue("initialValue", generator.getInitialValue(), annotationValueList);
/* 250:315 */     MockHelper.integerValue("allocationSize", generator.getAllocationSize(), annotationValueList);
/* 251:316 */     nestedUniqueConstraintList("uniqueConstraints", generator.getUniqueConstraint(), annotationValueList);
/* 252:317 */     return create(TABLE_GENERATOR, null, annotationValueList);
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected AnnotationInstance push(AnnotationInstance annotationInstance)
/* 256:    */   {
/* 257:326 */     if (annotationInstance != null) {
/* 258:327 */       return this.globalAnnotations.push(annotationInstance.name(), annotationInstance);
/* 259:    */     }
/* 260:329 */     return null;
/* 261:    */   }
/* 262:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.GlobalAnnotationMocker
 * JD-Core Version:    0.7.0.1
 */