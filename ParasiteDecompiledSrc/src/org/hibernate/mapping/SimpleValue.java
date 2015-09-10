/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Properties;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.FetchMode;
/*   9:    */ import org.hibernate.MappingException;
/*  10:    */ import org.hibernate.cfg.Mappings;
/*  11:    */ import org.hibernate.dialect.Dialect;
/*  12:    */ import org.hibernate.engine.spi.Mapping;
/*  13:    */ import org.hibernate.id.IdentifierGenerator;
/*  14:    */ import org.hibernate.id.IdentityGenerator;
/*  15:    */ import org.hibernate.id.factory.IdentifierGeneratorFactory;
/*  16:    */ import org.hibernate.internal.util.ReflectHelper;
/*  17:    */ import org.hibernate.type.Type;
/*  18:    */ import org.hibernate.type.TypeResolver;
/*  19:    */ 
/*  20:    */ public class SimpleValue
/*  21:    */   implements KeyValue
/*  22:    */ {
/*  23:    */   public static final String DEFAULT_ID_GEN_STRATEGY = "assigned";
/*  24:    */   private final Mappings mappings;
/*  25: 52 */   private final List columns = new ArrayList();
/*  26:    */   private String typeName;
/*  27:    */   private Properties identifierGeneratorProperties;
/*  28: 55 */   private String identifierGeneratorStrategy = "assigned";
/*  29:    */   private String nullValue;
/*  30:    */   private Table table;
/*  31:    */   private String foreignKeyName;
/*  32:    */   private boolean alternateUniqueKey;
/*  33:    */   private Properties typeParameters;
/*  34:    */   private boolean cascadeDeleteEnabled;
/*  35:    */   
/*  36:    */   public SimpleValue(Mappings mappings)
/*  37:    */   {
/*  38: 64 */     this.mappings = mappings;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public SimpleValue(Mappings mappings, Table table)
/*  42:    */   {
/*  43: 68 */     this(mappings);
/*  44: 69 */     this.table = table;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Mappings getMappings()
/*  48:    */   {
/*  49: 73 */     return this.mappings;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isCascadeDeleteEnabled()
/*  53:    */   {
/*  54: 77 */     return this.cascadeDeleteEnabled;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setCascadeDeleteEnabled(boolean cascadeDeleteEnabled)
/*  58:    */   {
/*  59: 81 */     this.cascadeDeleteEnabled = cascadeDeleteEnabled;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void addColumn(Column column)
/*  63:    */   {
/*  64: 85 */     if (!this.columns.contains(column)) {
/*  65: 85 */       this.columns.add(column);
/*  66:    */     }
/*  67: 86 */     column.setValue(this);
/*  68: 87 */     column.setTypeIndex(this.columns.size() - 1);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void addFormula(Formula formula)
/*  72:    */   {
/*  73: 91 */     this.columns.add(formula);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean hasFormula()
/*  77:    */   {
/*  78: 95 */     Iterator iter = getColumnIterator();
/*  79: 96 */     while (iter.hasNext())
/*  80:    */     {
/*  81: 97 */       Object o = iter.next();
/*  82: 98 */       if ((o instanceof Formula)) {
/*  83: 98 */         return true;
/*  84:    */       }
/*  85:    */     }
/*  86:100 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getColumnSpan()
/*  90:    */   {
/*  91:104 */     return this.columns.size();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Iterator getColumnIterator()
/*  95:    */   {
/*  96:107 */     return this.columns.iterator();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List getConstraintColumns()
/* 100:    */   {
/* 101:110 */     return this.columns;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getTypeName()
/* 105:    */   {
/* 106:113 */     return this.typeName;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setTypeName(String type)
/* 110:    */   {
/* 111:116 */     this.typeName = type;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setTable(Table table)
/* 115:    */   {
/* 116:119 */     this.table = table;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void createForeignKey()
/* 120:    */     throws MappingException
/* 121:    */   {}
/* 122:    */   
/* 123:    */   public void createForeignKeyOfEntity(String entityName)
/* 124:    */   {
/* 125:125 */     if ((!hasFormula()) && (!"none".equals(getForeignKeyName())))
/* 126:    */     {
/* 127:126 */       ForeignKey fk = this.table.createForeignKey(getForeignKeyName(), getConstraintColumns(), entityName);
/* 128:127 */       fk.setCascadeDeleteEnabled(this.cascadeDeleteEnabled);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public IdentifierGenerator createIdentifierGenerator(IdentifierGeneratorFactory identifierGeneratorFactory, Dialect dialect, String defaultCatalog, String defaultSchema, RootClass rootClass)
/* 133:    */     throws MappingException
/* 134:    */   {
/* 135:138 */     Properties params = new Properties();
/* 136:144 */     if (defaultSchema != null) {
/* 137:145 */       params.setProperty("schema", defaultSchema);
/* 138:    */     }
/* 139:147 */     if (defaultCatalog != null) {
/* 140:148 */       params.setProperty("catalog", defaultCatalog);
/* 141:    */     }
/* 142:152 */     if (rootClass != null) {
/* 143:153 */       params.setProperty("entity_name", rootClass.getEntityName());
/* 144:    */     }
/* 145:159 */     String tableName = getTable().getQuotedName(dialect);
/* 146:160 */     params.setProperty("target_table", tableName);
/* 147:    */     
/* 148:    */ 
/* 149:163 */     String columnName = ((Column)getColumnIterator().next()).getQuotedName(dialect);
/* 150:164 */     params.setProperty("target_column", columnName);
/* 151:166 */     if (rootClass != null)
/* 152:    */     {
/* 153:167 */       StringBuffer tables = new StringBuffer();
/* 154:168 */       Iterator iter = rootClass.getIdentityTables().iterator();
/* 155:169 */       while (iter.hasNext())
/* 156:    */       {
/* 157:170 */         Table table = (Table)iter.next();
/* 158:171 */         tables.append(table.getQuotedName(dialect));
/* 159:172 */         if (iter.hasNext()) {
/* 160:172 */           tables.append(", ");
/* 161:    */         }
/* 162:    */       }
/* 163:174 */       params.setProperty("identity_tables", tables.toString());
/* 164:    */     }
/* 165:    */     else
/* 166:    */     {
/* 167:177 */       params.setProperty("identity_tables", tableName);
/* 168:    */     }
/* 169:180 */     if (this.identifierGeneratorProperties != null) {
/* 170:181 */       params.putAll(this.identifierGeneratorProperties);
/* 171:    */     }
/* 172:185 */     params.put("hibernate.id.optimizer.pooled.prefer_lo", this.mappings.getConfigurationProperties().getProperty("hibernate.id.optimizer.pooled.prefer_lo", "false"));
/* 173:    */     
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:190 */     identifierGeneratorFactory.setDialect(dialect);
/* 178:191 */     return identifierGeneratorFactory.createIdentifierGenerator(this.identifierGeneratorStrategy, getType(), params);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isUpdateable()
/* 182:    */   {
/* 183:197 */     return true;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public FetchMode getFetchMode()
/* 187:    */   {
/* 188:201 */     return FetchMode.SELECT;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Properties getIdentifierGeneratorProperties()
/* 192:    */   {
/* 193:205 */     return this.identifierGeneratorProperties;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String getNullValue()
/* 197:    */   {
/* 198:209 */     return this.nullValue;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Table getTable()
/* 202:    */   {
/* 203:213 */     return this.table;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getIdentifierGeneratorStrategy()
/* 207:    */   {
/* 208:221 */     return this.identifierGeneratorStrategy;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean isIdentityColumn(IdentifierGeneratorFactory identifierGeneratorFactory, Dialect dialect)
/* 212:    */   {
/* 213:225 */     identifierGeneratorFactory.setDialect(dialect);
/* 214:226 */     return identifierGeneratorFactory.getIdentifierGeneratorClass(this.identifierGeneratorStrategy).equals(IdentityGenerator.class);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setIdentifierGeneratorProperties(Properties identifierGeneratorProperties)
/* 218:    */   {
/* 219:235 */     this.identifierGeneratorProperties = identifierGeneratorProperties;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setIdentifierGeneratorStrategy(String identifierGeneratorStrategy)
/* 223:    */   {
/* 224:243 */     this.identifierGeneratorStrategy = identifierGeneratorStrategy;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setNullValue(String nullValue)
/* 228:    */   {
/* 229:251 */     this.nullValue = nullValue;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String getForeignKeyName()
/* 233:    */   {
/* 234:255 */     return this.foreignKeyName;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setForeignKeyName(String foreignKeyName)
/* 238:    */   {
/* 239:259 */     this.foreignKeyName = foreignKeyName;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean isAlternateUniqueKey()
/* 243:    */   {
/* 244:263 */     return this.alternateUniqueKey;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setAlternateUniqueKey(boolean unique)
/* 248:    */   {
/* 249:267 */     this.alternateUniqueKey = unique;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean isNullable()
/* 253:    */   {
/* 254:271 */     if (hasFormula()) {
/* 255:271 */       return true;
/* 256:    */     }
/* 257:272 */     boolean nullable = true;
/* 258:273 */     Iterator iter = getColumnIterator();
/* 259:274 */     while (iter.hasNext()) {
/* 260:275 */       if (!((Column)iter.next()).isNullable())
/* 261:    */       {
/* 262:276 */         nullable = false;
/* 263:277 */         return nullable;
/* 264:    */       }
/* 265:    */     }
/* 266:280 */     return nullable;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public boolean isSimpleValue()
/* 270:    */   {
/* 271:284 */     return true;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean isValid(Mapping mapping)
/* 275:    */     throws MappingException
/* 276:    */   {
/* 277:288 */     return getColumnSpan() == getType().getColumnSpan(mapping);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Type getType()
/* 281:    */     throws MappingException
/* 282:    */   {
/* 283:292 */     if (this.typeName == null) {
/* 284:293 */       throw new MappingException("No type name");
/* 285:    */     }
/* 286:295 */     Type result = this.mappings.getTypeResolver().heuristicType(this.typeName, this.typeParameters);
/* 287:296 */     if (result == null)
/* 288:    */     {
/* 289:297 */       String msg = "Could not determine type for: " + this.typeName;
/* 290:298 */       if (this.table != null) {
/* 291:299 */         msg = msg + ", at table: " + this.table.getName();
/* 292:    */       }
/* 293:301 */       if ((this.columns != null) && (this.columns.size() > 0)) {
/* 294:302 */         msg = msg + ", for columns: " + this.columns;
/* 295:    */       }
/* 296:304 */       throw new MappingException(msg);
/* 297:    */     }
/* 298:306 */     return result;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setTypeUsingReflection(String className, String propertyName)
/* 302:    */     throws MappingException
/* 303:    */   {
/* 304:310 */     if (this.typeName == null)
/* 305:    */     {
/* 306:311 */       if (className == null) {
/* 307:312 */         throw new MappingException("you must specify types for a dynamic entity: " + propertyName);
/* 308:    */       }
/* 309:314 */       this.typeName = ReflectHelper.reflectedPropertyClass(className, propertyName).getName();
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   public boolean isTypeSpecified()
/* 314:    */   {
/* 315:319 */     return this.typeName != null;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setTypeParameters(Properties parameterMap)
/* 319:    */   {
/* 320:323 */     this.typeParameters = parameterMap;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public Properties getTypeParameters()
/* 324:    */   {
/* 325:327 */     return this.typeParameters;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String toString()
/* 329:    */   {
/* 330:332 */     return getClass().getName() + '(' + this.columns.toString() + ')';
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Object accept(ValueVisitor visitor)
/* 334:    */   {
/* 335:336 */     return visitor.accept(this);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public boolean[] getColumnInsertability()
/* 339:    */   {
/* 340:340 */     boolean[] result = new boolean[getColumnSpan()];
/* 341:341 */     int i = 0;
/* 342:342 */     Iterator iter = getColumnIterator();
/* 343:343 */     while (iter.hasNext())
/* 344:    */     {
/* 345:344 */       Selectable s = (Selectable)iter.next();
/* 346:345 */       result[(i++)] = (!s.isFormula() ? 1 : false);
/* 347:    */     }
/* 348:347 */     return result;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public boolean[] getColumnUpdateability()
/* 352:    */   {
/* 353:351 */     return getColumnInsertability();
/* 354:    */   }
/* 355:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.SimpleValue
 * JD-Core Version:    0.7.0.1
 */