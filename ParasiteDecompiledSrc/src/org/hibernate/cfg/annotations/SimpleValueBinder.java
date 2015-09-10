/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.Blob;
/*   5:    */ import java.sql.Clob;
/*   6:    */ import java.util.Calendar;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.Properties;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.Lob;
/*  11:    */ import javax.persistence.MapKeyEnumerated;
/*  12:    */ import javax.persistence.MapKeyTemporal;
/*  13:    */ import javax.persistence.Temporal;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ import org.hibernate.AnnotationException;
/*  16:    */ import org.hibernate.AssertionFailure;
/*  17:    */ import org.hibernate.annotations.Parameter;
/*  18:    */ import org.hibernate.annotations.Type;
/*  19:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  20:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  21:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  22:    */ import org.hibernate.cfg.BinderHelper;
/*  23:    */ import org.hibernate.cfg.Ejb3Column;
/*  24:    */ import org.hibernate.cfg.Ejb3JoinColumn;
/*  25:    */ import org.hibernate.cfg.Mappings;
/*  26:    */ import org.hibernate.cfg.NotYetImplementedException;
/*  27:    */ import org.hibernate.cfg.PkDrivenByDefaultMapsIdSecondPass;
/*  28:    */ import org.hibernate.cfg.SetSimpleValueTypeSecondPass;
/*  29:    */ import org.hibernate.internal.CoreMessageLogger;
/*  30:    */ import org.hibernate.internal.util.StringHelper;
/*  31:    */ import org.hibernate.mapping.SimpleValue;
/*  32:    */ import org.hibernate.mapping.Table;
/*  33:    */ import org.hibernate.mapping.TypeDef;
/*  34:    */ import org.hibernate.type.CharacterArrayClobType;
/*  35:    */ import org.hibernate.type.MaterializedBlobType;
/*  36:    */ import org.hibernate.type.MaterializedClobType;
/*  37:    */ import org.hibernate.type.PrimitiveCharacterArrayClobType;
/*  38:    */ import org.hibernate.type.SerializableToBlobType;
/*  39:    */ import org.hibernate.type.StandardBasicTypes;
/*  40:    */ import org.hibernate.type.WrappedMaterializedBlobType;
/*  41:    */ import org.jboss.logging.Logger;
/*  42:    */ 
/*  43:    */ public class SimpleValueBinder
/*  44:    */ {
/*  45: 68 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SimpleValueBinder.class.getName());
/*  46:    */   private String propertyName;
/*  47:    */   private String returnedClassName;
/*  48:    */   private Ejb3Column[] columns;
/*  49:    */   private String persistentClassName;
/*  50: 74 */   private String explicitType = "";
/*  51: 75 */   private Properties typeParameters = new Properties();
/*  52:    */   private Mappings mappings;
/*  53:    */   private Table table;
/*  54:    */   private SimpleValue simpleValue;
/*  55:    */   private boolean isVersion;
/*  56:    */   private String timeStampVersionType;
/*  57:    */   private boolean key;
/*  58:    */   private String referencedEntityName;
/*  59:    */   
/*  60:    */   public void setReferencedEntityName(String referencedEntityName)
/*  61:    */   {
/*  62: 86 */     this.referencedEntityName = referencedEntityName;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isVersion()
/*  66:    */   {
/*  67: 90 */     return this.isVersion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setVersion(boolean isVersion)
/*  71:    */   {
/*  72: 94 */     this.isVersion = isVersion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setTimestampVersionType(String versionType)
/*  76:    */   {
/*  77: 98 */     this.timeStampVersionType = versionType;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setPropertyName(String propertyName)
/*  81:    */   {
/*  82:102 */     this.propertyName = propertyName;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setReturnedClassName(String returnedClassName)
/*  86:    */   {
/*  87:106 */     this.returnedClassName = returnedClassName;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setTable(Table table)
/*  91:    */   {
/*  92:110 */     this.table = table;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setColumns(Ejb3Column[] columns)
/*  96:    */   {
/*  97:114 */     this.columns = columns;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPersistentClassName(String persistentClassName)
/* 101:    */   {
/* 102:119 */     this.persistentClassName = persistentClassName;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setType(XProperty property, XClass returnedClass)
/* 106:    */   {
/* 107:125 */     if (returnedClass == null) {
/* 108:126 */       return;
/* 109:    */     }
/* 110:128 */     XClass returnedClassOrElement = returnedClass;
/* 111:129 */     boolean isArray = false;
/* 112:130 */     if (property.isArray())
/* 113:    */     {
/* 114:131 */       returnedClassOrElement = property.getElementClass();
/* 115:132 */       isArray = true;
/* 116:    */     }
/* 117:134 */     Properties typeParameters = this.typeParameters;
/* 118:135 */     typeParameters.clear();
/* 119:136 */     String type = "";
/* 120:137 */     if (((!this.key) && (property.isAnnotationPresent(Temporal.class))) || ((this.key) && (property.isAnnotationPresent(MapKeyTemporal.class))))
/* 121:    */     {
/* 122:    */       boolean isDate;
/* 123:141 */       if (this.mappings.getReflectionManager().equals(returnedClassOrElement, Date.class))
/* 124:    */       {
/* 125:142 */         isDate = true;
/* 126:    */       }
/* 127:    */       else
/* 128:    */       {
/* 129:    */         boolean isDate;
/* 130:144 */         if (this.mappings.getReflectionManager().equals(returnedClassOrElement, Calendar.class)) {
/* 131:145 */           isDate = false;
/* 132:    */         } else {
/* 133:148 */           throw new AnnotationException("@Temporal should only be set on a java.util.Date or java.util.Calendar property: " + StringHelper.qualify(this.persistentClassName, this.propertyName));
/* 134:    */         }
/* 135:    */       }
/* 136:    */       boolean isDate;
/* 137:153 */       TemporalType temporalType = getTemporalType(property);
/* 138:154 */       switch (1.$SwitchMap$javax$persistence$TemporalType[temporalType.ordinal()])
/* 139:    */       {
/* 140:    */       case 1: 
/* 141:156 */         type = isDate ? "date" : "calendar_date";
/* 142:157 */         break;
/* 143:    */       case 2: 
/* 144:159 */         type = "time";
/* 145:160 */         if (!isDate) {
/* 146:161 */           throw new NotYetImplementedException("Calendar cannot persist TIME only" + StringHelper.qualify(this.persistentClassName, this.propertyName));
/* 147:    */         }
/* 148:    */         break;
/* 149:    */       case 3: 
/* 150:168 */         type = isDate ? "timestamp" : "calendar";
/* 151:169 */         break;
/* 152:    */       default: 
/* 153:171 */         throw new AssertionFailure("Unknown temporal type: " + temporalType);
/* 154:    */       }
/* 155:    */     }
/* 156:174 */     else if (property.isAnnotationPresent(Lob.class))
/* 157:    */     {
/* 158:176 */       if (this.mappings.getReflectionManager().equals(returnedClassOrElement, Clob.class))
/* 159:    */       {
/* 160:177 */         type = "clob";
/* 161:    */       }
/* 162:179 */       else if (this.mappings.getReflectionManager().equals(returnedClassOrElement, Blob.class))
/* 163:    */       {
/* 164:180 */         type = "blob";
/* 165:    */       }
/* 166:182 */       else if (this.mappings.getReflectionManager().equals(returnedClassOrElement, String.class))
/* 167:    */       {
/* 168:183 */         type = StandardBasicTypes.MATERIALIZED_CLOB.getName();
/* 169:    */       }
/* 170:185 */       else if ((this.mappings.getReflectionManager().equals(returnedClassOrElement, Character.class)) && (isArray))
/* 171:    */       {
/* 172:186 */         type = CharacterArrayClobType.class.getName();
/* 173:    */       }
/* 174:188 */       else if ((this.mappings.getReflectionManager().equals(returnedClassOrElement, Character.TYPE)) && (isArray))
/* 175:    */       {
/* 176:189 */         type = PrimitiveCharacterArrayClobType.class.getName();
/* 177:    */       }
/* 178:191 */       else if ((this.mappings.getReflectionManager().equals(returnedClassOrElement, Byte.class)) && (isArray))
/* 179:    */       {
/* 180:192 */         type = WrappedMaterializedBlobType.class.getName();
/* 181:    */       }
/* 182:194 */       else if ((this.mappings.getReflectionManager().equals(returnedClassOrElement, Byte.TYPE)) && (isArray))
/* 183:    */       {
/* 184:195 */         type = StandardBasicTypes.MATERIALIZED_BLOB.getName();
/* 185:    */       }
/* 186:197 */       else if (this.mappings.getReflectionManager().toXClass(Serializable.class).isAssignableFrom(returnedClassOrElement))
/* 187:    */       {
/* 188:200 */         type = SerializableToBlobType.class.getName();
/* 189:    */         
/* 190:202 */         typeParameters.setProperty("classname", returnedClassOrElement.getName());
/* 191:    */       }
/* 192:    */       else
/* 193:    */       {
/* 194:208 */         type = "blob";
/* 195:    */       }
/* 196:    */     }
/* 197:212 */     if (this.columns == null) {
/* 198:213 */       throw new AssertionFailure("SimpleValueBinder.setColumns should be set before SimpleValueBinder.setType");
/* 199:    */     }
/* 200:215 */     if (("".equals(type)) && 
/* 201:216 */       (returnedClassOrElement.isEnum()))
/* 202:    */     {
/* 203:217 */       type = org.hibernate.type.EnumType.class.getName();
/* 204:218 */       typeParameters = new Properties();
/* 205:219 */       typeParameters.setProperty("enumClass", returnedClassOrElement.getName());
/* 206:220 */       String schema = this.columns[0].getTable().getSchema();
/* 207:221 */       schema = schema == null ? "" : schema;
/* 208:222 */       String catalog = this.columns[0].getTable().getCatalog();
/* 209:223 */       catalog = catalog == null ? "" : catalog;
/* 210:224 */       typeParameters.setProperty("schema", schema);
/* 211:225 */       typeParameters.setProperty("catalog", catalog);
/* 212:226 */       typeParameters.setProperty("table", this.columns[0].getTable().getName());
/* 213:227 */       typeParameters.setProperty("column", this.columns[0].getName());
/* 214:228 */       javax.persistence.EnumType enumType = getEnumType(property);
/* 215:229 */       if (enumType != null) {
/* 216:230 */         if (javax.persistence.EnumType.ORDINAL.equals(enumType)) {
/* 217:231 */           typeParameters.setProperty("type", String.valueOf(4));
/* 218:233 */         } else if (javax.persistence.EnumType.STRING.equals(enumType)) {
/* 219:234 */           typeParameters.setProperty("type", String.valueOf(12));
/* 220:    */         } else {
/* 221:237 */           throw new AssertionFailure("Unknown EnumType: " + enumType);
/* 222:    */         }
/* 223:    */       }
/* 224:    */     }
/* 225:242 */     this.explicitType = type;
/* 226:243 */     this.typeParameters = typeParameters;
/* 227:244 */     Type annType = (Type)property.getAnnotation(Type.class);
/* 228:245 */     setExplicitType(annType);
/* 229:    */   }
/* 230:    */   
/* 231:    */   private javax.persistence.EnumType getEnumType(XProperty property)
/* 232:    */   {
/* 233:249 */     javax.persistence.EnumType enumType = null;
/* 234:250 */     if (this.key)
/* 235:    */     {
/* 236:251 */       MapKeyEnumerated enumAnn = (MapKeyEnumerated)property.getAnnotation(MapKeyEnumerated.class);
/* 237:252 */       if (enumAnn != null) {
/* 238:253 */         enumType = enumAnn.value();
/* 239:    */       }
/* 240:    */     }
/* 241:    */     else
/* 242:    */     {
/* 243:257 */       Enumerated enumAnn = (Enumerated)property.getAnnotation(Enumerated.class);
/* 244:258 */       if (enumAnn != null) {
/* 245:259 */         enumType = enumAnn.value();
/* 246:    */       }
/* 247:    */     }
/* 248:262 */     return enumType;
/* 249:    */   }
/* 250:    */   
/* 251:    */   private TemporalType getTemporalType(XProperty property)
/* 252:    */   {
/* 253:266 */     if (this.key)
/* 254:    */     {
/* 255:267 */       MapKeyTemporal ann = (MapKeyTemporal)property.getAnnotation(MapKeyTemporal.class);
/* 256:268 */       return ann.value();
/* 257:    */     }
/* 258:271 */     Temporal ann = (Temporal)property.getAnnotation(Temporal.class);
/* 259:272 */     return ann.value();
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setExplicitType(String explicitType)
/* 263:    */   {
/* 264:277 */     this.explicitType = explicitType;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setExplicitType(Type typeAnn)
/* 268:    */   {
/* 269:283 */     if (typeAnn != null)
/* 270:    */     {
/* 271:284 */       this.explicitType = typeAnn.type();
/* 272:285 */       this.typeParameters.clear();
/* 273:286 */       for (Parameter param : typeAnn.parameters()) {
/* 274:287 */         this.typeParameters.setProperty(param.name(), param.value());
/* 275:    */       }
/* 276:    */     }
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setMappings(Mappings mappings)
/* 280:    */   {
/* 281:293 */     this.mappings = mappings;
/* 282:    */   }
/* 283:    */   
/* 284:    */   private void validate()
/* 285:    */   {
/* 286:298 */     Ejb3Column.checkPropertyConsistency(this.columns, this.propertyName);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public SimpleValue make()
/* 290:    */   {
/* 291:303 */     validate();
/* 292:304 */     LOG.debugf("building SimpleValue for %s", this.propertyName);
/* 293:305 */     if (this.table == null) {
/* 294:306 */       this.table = this.columns[0].getTable();
/* 295:    */     }
/* 296:308 */     this.simpleValue = new SimpleValue(this.mappings, this.table);
/* 297:    */     
/* 298:310 */     linkWithValue();
/* 299:    */     
/* 300:312 */     boolean isInSecondPass = this.mappings.isInSecondPass();
/* 301:313 */     SetSimpleValueTypeSecondPass secondPass = new SetSimpleValueTypeSecondPass(this);
/* 302:314 */     if (!isInSecondPass) {
/* 303:316 */       this.mappings.addSecondPass(secondPass);
/* 304:    */     } else {
/* 305:320 */       fillSimpleValue();
/* 306:    */     }
/* 307:322 */     return this.simpleValue;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void linkWithValue()
/* 311:    */   {
/* 312:326 */     if ((this.columns[0].isNameDeferred()) && (!this.mappings.isInSecondPass()) && (this.referencedEntityName != null)) {
/* 313:327 */       this.mappings.addSecondPass(new PkDrivenByDefaultMapsIdSecondPass(this.referencedEntityName, (Ejb3JoinColumn[])this.columns, this.simpleValue));
/* 314:    */     } else {
/* 315:334 */       for (Ejb3Column column : this.columns) {
/* 316:335 */         column.linkWithValue(this.simpleValue);
/* 317:    */       }
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void fillSimpleValue()
/* 322:    */   {
/* 323:342 */     LOG.debugf("Setting SimpleValue typeName for %s", this.propertyName);
/* 324:    */     
/* 325:344 */     String type = BinderHelper.isEmptyAnnotationValue(this.explicitType) ? this.returnedClassName : this.explicitType;
/* 326:345 */     TypeDef typeDef = this.mappings.getTypeDef(type);
/* 327:346 */     if (typeDef != null)
/* 328:    */     {
/* 329:347 */       type = typeDef.getTypeClass();
/* 330:348 */       this.simpleValue.setTypeParameters(typeDef.getParameters());
/* 331:    */     }
/* 332:350 */     if ((this.typeParameters != null) && (this.typeParameters.size() != 0)) {
/* 333:352 */       this.simpleValue.setTypeParameters(this.typeParameters);
/* 334:    */     }
/* 335:354 */     this.simpleValue.setTypeName(type);
/* 336:355 */     if (this.persistentClassName != null) {
/* 337:356 */       this.simpleValue.setTypeUsingReflection(this.persistentClassName, this.propertyName);
/* 338:    */     }
/* 339:359 */     if ((!this.simpleValue.isTypeSpecified()) && (isVersion())) {
/* 340:360 */       this.simpleValue.setTypeName("integer");
/* 341:    */     }
/* 342:364 */     if (this.timeStampVersionType != null) {
/* 343:365 */       this.simpleValue.setTypeName(this.timeStampVersionType);
/* 344:    */     }
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setKey(boolean key)
/* 348:    */   {
/* 349:370 */     this.key = key;
/* 350:    */   }
/* 351:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.SimpleValueBinder
 * JD-Core Version:    0.7.0.1
 */