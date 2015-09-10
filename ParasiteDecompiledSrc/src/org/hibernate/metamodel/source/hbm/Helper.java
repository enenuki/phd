/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  10:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.hbm.CustomSqlElement;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.hbm.EntityElement;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbCheckAttribute;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbColumnElement;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbJoinedSubclassElement;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbMetaElement;
/*  17:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbParamElement;
/*  18:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbSubclassElement;
/*  19:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbUnionSubclassElement;
/*  20:    */ import org.hibernate.internal.util.StringHelper;
/*  21:    */ import org.hibernate.metamodel.binding.CustomSQL;
/*  22:    */ import org.hibernate.metamodel.binding.InheritanceType;
/*  23:    */ import org.hibernate.metamodel.binding.MetaAttribute;
/*  24:    */ import org.hibernate.metamodel.relational.Identifier;
/*  25:    */ import org.hibernate.metamodel.relational.Schema.Name;
/*  26:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  27:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  28:    */ import org.hibernate.metamodel.source.MetaAttributeContext;
/*  29:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  30:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  31:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  32:    */ import org.hibernate.service.ServiceRegistry;
/*  33:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  34:    */ import org.hibernate.service.classloading.spi.ClassLoadingException;
/*  35:    */ 
/*  36:    */ public class Helper
/*  37:    */ {
/*  38: 64 */   public static final ExplicitHibernateTypeSource TO_ONE_ATTRIBUTE_TYPE_SOURCE = new ExplicitHibernateTypeSource()
/*  39:    */   {
/*  40:    */     public String getName()
/*  41:    */     {
/*  42: 67 */       return null;
/*  43:    */     }
/*  44:    */     
/*  45:    */     public Map<String, String> getParameters()
/*  46:    */     {
/*  47: 72 */       return null;
/*  48:    */     }
/*  49:    */   };
/*  50:    */   
/*  51:    */   public static InheritanceType interpretInheritanceType(EntityElement entityElement)
/*  52:    */   {
/*  53: 77 */     if (JaxbSubclassElement.class.isInstance(entityElement)) {
/*  54: 78 */       return InheritanceType.SINGLE_TABLE;
/*  55:    */     }
/*  56: 80 */     if (JaxbJoinedSubclassElement.class.isInstance(entityElement)) {
/*  57: 81 */       return InheritanceType.JOINED;
/*  58:    */     }
/*  59: 83 */     if (JaxbUnionSubclassElement.class.isInstance(entityElement)) {
/*  60: 84 */       return InheritanceType.TABLE_PER_CLASS;
/*  61:    */     }
/*  62: 87 */     return InheritanceType.NO_INHERITANCE;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static CustomSQL buildCustomSql(CustomSqlElement customSqlElement)
/*  66:    */   {
/*  67: 99 */     if (customSqlElement == null) {
/*  68:100 */       return null;
/*  69:    */     }
/*  70:102 */     ExecuteUpdateResultCheckStyle checkStyle = customSqlElement.getCheck() == null ? ExecuteUpdateResultCheckStyle.COUNT : customSqlElement.isCallable() ? ExecuteUpdateResultCheckStyle.NONE : ExecuteUpdateResultCheckStyle.fromExternalName(customSqlElement.getCheck().value());
/*  71:    */     
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:107 */     return new CustomSQL(customSqlElement.getValue(), customSqlElement.isCallable(), checkStyle);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String determineEntityName(EntityElement entityElement, String unqualifiedClassPackage)
/*  79:    */   {
/*  80:119 */     return entityElement.getEntityName() != null ? entityElement.getEntityName() : qualifyIfNeeded(entityElement.getName(), unqualifiedClassPackage);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static String qualifyIfNeeded(String name, String unqualifiedClassPackage)
/*  84:    */   {
/*  85:133 */     if (name == null) {
/*  86:134 */       return null;
/*  87:    */     }
/*  88:136 */     if ((name.indexOf('.') < 0) && (unqualifiedClassPackage != null)) {
/*  89:137 */       return unqualifiedClassPackage + '.' + name;
/*  90:    */     }
/*  91:139 */     return name;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static String getPropertyAccessorName(String access, boolean isEmbedded, String defaultAccess)
/*  95:    */   {
/*  96:143 */     return getStringValue(access, isEmbedded ? "embedded" : defaultAccess);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static MetaAttributeContext extractMetaAttributeContext(List<JaxbMetaElement> metaElementList, boolean onlyInheritable, MetaAttributeContext parentContext)
/* 100:    */   {
/* 101:150 */     MetaAttributeContext subContext = new MetaAttributeContext(parentContext);
/* 102:152 */     for (JaxbMetaElement metaElement : metaElementList) {
/* 103:153 */       if (!(onlyInheritable & !metaElement.isInherit()))
/* 104:    */       {
/* 105:157 */         String name = metaElement.getAttribute();
/* 106:158 */         MetaAttribute inheritedMetaAttribute = parentContext.getMetaAttribute(name);
/* 107:159 */         MetaAttribute metaAttribute = subContext.getLocalMetaAttribute(name);
/* 108:160 */         if ((metaAttribute == null) || (metaAttribute == inheritedMetaAttribute))
/* 109:    */         {
/* 110:161 */           metaAttribute = new MetaAttribute(name);
/* 111:162 */           subContext.add(metaAttribute);
/* 112:    */         }
/* 113:164 */         metaAttribute.addValue(metaElement.getValue());
/* 114:    */       }
/* 115:    */     }
/* 116:167 */     return subContext;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static String getStringValue(String value, String defaultValue)
/* 120:    */   {
/* 121:171 */     return value == null ? defaultValue : value;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static int getIntValue(String value, int defaultValue)
/* 125:    */   {
/* 126:175 */     return value == null ? defaultValue : Integer.parseInt(value);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static long getLongValue(String value, long defaultValue)
/* 130:    */   {
/* 131:179 */     return value == null ? defaultValue : Long.parseLong(value);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static boolean getBooleanValue(Boolean value, boolean defaultValue)
/* 135:    */   {
/* 136:183 */     return value == null ? defaultValue : value.booleanValue();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static Iterable<CascadeStyle> interpretCascadeStyles(String cascades, LocalBindingContext bindingContext)
/* 140:    */   {
/* 141:187 */     Set<CascadeStyle> cascadeStyles = new HashSet();
/* 142:188 */     if (StringHelper.isEmpty(cascades)) {
/* 143:189 */       cascades = bindingContext.getMappingDefaults().getCascadeStyle();
/* 144:    */     }
/* 145:191 */     for (String cascade : StringHelper.split(",", cascades)) {
/* 146:192 */       cascadeStyles.add(CascadeStyle.getCascadeStyle(cascade));
/* 147:    */     }
/* 148:194 */     return cascadeStyles;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static Map<String, String> extractParameters(List<JaxbParamElement> xmlParamElements)
/* 152:    */   {
/* 153:198 */     if ((xmlParamElements == null) || (xmlParamElements.isEmpty())) {
/* 154:199 */       return null;
/* 155:    */     }
/* 156:201 */     HashMap<String, String> params = new HashMap();
/* 157:202 */     for (JaxbParamElement paramElement : xmlParamElements) {
/* 158:203 */       params.put(paramElement.getName(), paramElement.getValue());
/* 159:    */     }
/* 160:205 */     return params;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static Iterable<MetaAttributeSource> buildMetaAttributeSources(List<JaxbMetaElement> metaElements)
/* 164:    */   {
/* 165:209 */     ArrayList<MetaAttributeSource> result = new ArrayList();
/* 166:210 */     if ((metaElements != null) && (!metaElements.isEmpty())) {
/* 167:214 */       for (JaxbMetaElement metaElement : metaElements) {
/* 168:215 */         result.add(new MetaAttributeSource()
/* 169:    */         {
/* 170:    */           public String getName()
/* 171:    */           {
/* 172:219 */             return this.val$metaElement.getAttribute();
/* 173:    */           }
/* 174:    */           
/* 175:    */           public String getValue()
/* 176:    */           {
/* 177:224 */             return this.val$metaElement.getValue();
/* 178:    */           }
/* 179:    */           
/* 180:    */           public boolean isInheritable()
/* 181:    */           {
/* 182:229 */             return this.val$metaElement.isInherit();
/* 183:    */           }
/* 184:    */         });
/* 185:    */       }
/* 186:    */     }
/* 187:235 */     return result;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static Schema.Name determineDatabaseSchemaName(String explicitSchemaName, String explicitCatalogName, LocalBindingContext bindingContext)
/* 191:    */   {
/* 192:242 */     return new Schema.Name(resolveIdentifier(explicitSchemaName, bindingContext.getMappingDefaults().getSchemaName(), bindingContext.isGloballyQuotedIdentifiers()), resolveIdentifier(explicitCatalogName, bindingContext.getMappingDefaults().getCatalogName(), bindingContext.isGloballyQuotedIdentifiers()));
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static Identifier resolveIdentifier(String explicitName, String defaultName, boolean globalQuoting)
/* 196:    */   {
/* 197:257 */     String name = StringHelper.isNotEmpty(explicitName) ? explicitName : defaultName;
/* 198:258 */     if (globalQuoting) {
/* 199:259 */       name = StringHelper.quote(name);
/* 200:    */     }
/* 201:261 */     return Identifier.toIdentifier(name);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static class ValueSourcesAdapter
/* 205:    */   {
/* 206:    */     public String getContainingTableName()
/* 207:    */     {
/* 208:266 */       return null;
/* 209:    */     }
/* 210:    */     
/* 211:    */     public boolean isIncludedInInsertByDefault()
/* 212:    */     {
/* 213:270 */       return false;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public boolean isIncludedInUpdateByDefault()
/* 217:    */     {
/* 218:274 */       return false;
/* 219:    */     }
/* 220:    */     
/* 221:    */     public String getColumnAttribute()
/* 222:    */     {
/* 223:278 */       return null;
/* 224:    */     }
/* 225:    */     
/* 226:    */     public String getFormulaAttribute()
/* 227:    */     {
/* 228:282 */       return null;
/* 229:    */     }
/* 230:    */     
/* 231:    */     public List getColumnOrFormulaElements()
/* 232:    */     {
/* 233:286 */       return null;
/* 234:    */     }
/* 235:    */     
/* 236:    */     public boolean isForceNotNull()
/* 237:    */     {
/* 238:290 */       return false;
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static List<RelationalValueSource> buildValueSources(ValueSourcesAdapter valueSourcesAdapter, LocalBindingContext bindingContext)
/* 243:    */   {
/* 244:297 */     List<RelationalValueSource> result = new ArrayList();
/* 245:299 */     if (StringHelper.isNotEmpty(valueSourcesAdapter.getColumnAttribute()))
/* 246:    */     {
/* 247:300 */       if ((valueSourcesAdapter.getColumnOrFormulaElements() != null) && (!valueSourcesAdapter.getColumnOrFormulaElements().isEmpty())) {
/* 248:302 */         throw new org.hibernate.metamodel.source.MappingException("column/formula attribute may not be used together with <column>/<formula> subelement", bindingContext.getOrigin());
/* 249:    */       }
/* 250:307 */       if (StringHelper.isNotEmpty(valueSourcesAdapter.getFormulaAttribute())) {
/* 251:308 */         throw new org.hibernate.metamodel.source.MappingException("column and formula attributes may not be used together", bindingContext.getOrigin());
/* 252:    */       }
/* 253:313 */       result.add(new ColumnAttributeSourceImpl(valueSourcesAdapter.getContainingTableName(), valueSourcesAdapter.getColumnAttribute(), valueSourcesAdapter.isIncludedInInsertByDefault(), valueSourcesAdapter.isIncludedInUpdateByDefault(), valueSourcesAdapter.isForceNotNull()));
/* 254:    */     }
/* 255:323 */     else if (StringHelper.isNotEmpty(valueSourcesAdapter.getFormulaAttribute()))
/* 256:    */     {
/* 257:324 */       if ((valueSourcesAdapter.getColumnOrFormulaElements() != null) && (!valueSourcesAdapter.getColumnOrFormulaElements().isEmpty())) {
/* 258:326 */         throw new org.hibernate.metamodel.source.MappingException("column/formula attribute may not be used together with <column>/<formula> subelement", bindingContext.getOrigin());
/* 259:    */       }
/* 260:332 */       result.add(new FormulaImpl(valueSourcesAdapter.getContainingTableName(), valueSourcesAdapter.getFormulaAttribute()));
/* 261:    */     }
/* 262:339 */     else if ((valueSourcesAdapter.getColumnOrFormulaElements() != null) && (!valueSourcesAdapter.getColumnOrFormulaElements().isEmpty()))
/* 263:    */     {
/* 264:341 */       for (Object columnOrFormulaElement : valueSourcesAdapter.getColumnOrFormulaElements()) {
/* 265:342 */         if (JaxbColumnElement.class.isInstance(columnOrFormulaElement)) {
/* 266:343 */           result.add(new ColumnSourceImpl(valueSourcesAdapter.getContainingTableName(), (JaxbColumnElement)columnOrFormulaElement, valueSourcesAdapter.isIncludedInInsertByDefault(), valueSourcesAdapter.isIncludedInUpdateByDefault(), valueSourcesAdapter.isForceNotNull()));
/* 267:    */         } else {
/* 268:354 */           result.add(new FormulaImpl(valueSourcesAdapter.getContainingTableName(), (String)columnOrFormulaElement));
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:363 */     return result;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public static Class classForName(String className, ServiceRegistry serviceRegistry)
/* 276:    */   {
/* 277:369 */     ClassLoaderService classLoaderService = (ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class);
/* 278:    */     try
/* 279:    */     {
/* 280:371 */       return classLoaderService.classForName(className);
/* 281:    */     }
/* 282:    */     catch (ClassLoadingException e)
/* 283:    */     {
/* 284:374 */       throw new org.hibernate.MappingException("Could not find class: " + className);
/* 285:    */     }
/* 286:    */   }
/* 287:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.Helper
 * JD-Core Version:    0.7.0.1
 */