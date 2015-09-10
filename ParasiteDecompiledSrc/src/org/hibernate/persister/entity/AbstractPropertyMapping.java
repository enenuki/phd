/*   1:    */ package org.hibernate.persister.entity;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.QueryException;
/*   7:    */ import org.hibernate.engine.spi.Mapping;
/*   8:    */ import org.hibernate.internal.CoreMessageLogger;
/*   9:    */ import org.hibernate.internal.util.StringHelper;
/*  10:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  11:    */ import org.hibernate.type.AssociationType;
/*  12:    */ import org.hibernate.type.CompositeType;
/*  13:    */ import org.hibernate.type.EntityType;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public abstract class AbstractPropertyMapping
/*  18:    */   implements PropertyMapping
/*  19:    */ {
/*  20: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractPropertyMapping.class.getName());
/*  21: 54 */   private final Map typesByPropertyPath = new HashMap();
/*  22: 55 */   private final Map columnsByPropertyPath = new HashMap();
/*  23: 56 */   private final Map columnReadersByPropertyPath = new HashMap();
/*  24: 57 */   private final Map columnReaderTemplatesByPropertyPath = new HashMap();
/*  25: 58 */   private final Map formulaTemplatesByPropertyPath = new HashMap();
/*  26:    */   
/*  27:    */   public String[] getIdentifierColumnNames()
/*  28:    */   {
/*  29: 61 */     throw new UnsupportedOperationException("one-to-one is not supported here");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String[] getIdentifierColumnReaderTemplates()
/*  33:    */   {
/*  34: 65 */     throw new UnsupportedOperationException("one-to-one is not supported here");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String[] getIdentifierColumnReaders()
/*  38:    */   {
/*  39: 69 */     throw new UnsupportedOperationException("one-to-one is not supported here");
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected abstract String getEntityName();
/*  43:    */   
/*  44:    */   public Type toType(String propertyName)
/*  45:    */     throws QueryException
/*  46:    */   {
/*  47: 75 */     Type type = (Type)this.typesByPropertyPath.get(propertyName);
/*  48: 76 */     if (type == null) {
/*  49: 77 */       throw propertyException(propertyName);
/*  50:    */     }
/*  51: 79 */     return type;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected final QueryException propertyException(String propertyName)
/*  55:    */     throws QueryException
/*  56:    */   {
/*  57: 83 */     return new QueryException("could not resolve property: " + propertyName + " of: " + getEntityName());
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String[] getColumnNames(String propertyName)
/*  61:    */   {
/*  62: 87 */     String[] cols = (String[])this.columnsByPropertyPath.get(propertyName);
/*  63: 88 */     if (cols == null) {
/*  64: 89 */       throw new MappingException("unknown property: " + propertyName);
/*  65:    */     }
/*  66: 91 */     return cols;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String[] toColumns(String alias, String propertyName)
/*  70:    */     throws QueryException
/*  71:    */   {
/*  72: 96 */     String[] columns = (String[])this.columnsByPropertyPath.get(propertyName);
/*  73: 97 */     if (columns == null) {
/*  74: 98 */       throw propertyException(propertyName);
/*  75:    */     }
/*  76:100 */     String[] formulaTemplates = (String[])this.formulaTemplatesByPropertyPath.get(propertyName);
/*  77:101 */     String[] columnReaderTemplates = (String[])this.columnReaderTemplatesByPropertyPath.get(propertyName);
/*  78:102 */     String[] result = new String[columns.length];
/*  79:103 */     for (int i = 0; i < columns.length; i++) {
/*  80:104 */       if (columnReaderTemplates[i] == null) {
/*  81:105 */         result[i] = StringHelper.replace(formulaTemplates[i], "$PlaceHolder$", alias);
/*  82:    */       } else {
/*  83:108 */         result[i] = StringHelper.replace(columnReaderTemplates[i], "$PlaceHolder$", alias);
/*  84:    */       }
/*  85:    */     }
/*  86:111 */     return result;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String[] toColumns(String propertyName)
/*  90:    */     throws QueryException
/*  91:    */   {
/*  92:115 */     String[] columns = (String[])this.columnsByPropertyPath.get(propertyName);
/*  93:116 */     if (columns == null) {
/*  94:117 */       throw propertyException(propertyName);
/*  95:    */     }
/*  96:119 */     String[] formulaTemplates = (String[])this.formulaTemplatesByPropertyPath.get(propertyName);
/*  97:120 */     String[] columnReaders = (String[])this.columnReadersByPropertyPath.get(propertyName);
/*  98:121 */     String[] result = new String[columns.length];
/*  99:122 */     for (int i = 0; i < columns.length; i++) {
/* 100:123 */       if (columnReaders[i] == null) {
/* 101:124 */         result[i] = StringHelper.replace(formulaTemplates[i], "$PlaceHolder$", "");
/* 102:    */       } else {
/* 103:127 */         result[i] = columnReaders[i];
/* 104:    */       }
/* 105:    */     }
/* 106:130 */     return result;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected void addPropertyPath(String path, Type type, String[] columns, String[] columnReaders, String[] columnReaderTemplates, String[] formulaTemplates)
/* 110:    */   {
/* 111:141 */     if (this.typesByPropertyPath.containsKey(path))
/* 112:    */     {
/* 113:142 */       if (LOG.isTraceEnabled()) {
/* 114:143 */         LOG.tracev("Skipping duplicate registration of path [{0}], existing type = [{1}], incoming type = [{2}]", path, this.typesByPropertyPath.get(path), type);
/* 115:    */       }
/* 116:145 */       return;
/* 117:    */     }
/* 118:147 */     this.typesByPropertyPath.put(path, type);
/* 119:148 */     this.columnsByPropertyPath.put(path, columns);
/* 120:149 */     this.columnReadersByPropertyPath.put(path, columnReaders);
/* 121:150 */     this.columnReaderTemplatesByPropertyPath.put(path, columnReaderTemplates);
/* 122:151 */     if (formulaTemplates != null) {
/* 123:152 */       this.formulaTemplatesByPropertyPath.put(path, formulaTemplates);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void initPropertyPaths(String path, Type type, String[] columns, String[] columnReaders, String[] columnReaderTemplates, String[] formulaTemplates, Mapping factory)
/* 128:    */     throws MappingException
/* 129:    */   {
/* 130:177 */     if (columns.length != type.getColumnSpan(factory)) {
/* 131:178 */       throw new MappingException("broken column mapping for: " + path + " of: " + getEntityName());
/* 132:    */     }
/* 133:184 */     if (type.isAssociationType())
/* 134:    */     {
/* 135:185 */       AssociationType actype = (AssociationType)type;
/* 136:186 */       if (actype.useLHSPrimaryKey())
/* 137:    */       {
/* 138:187 */         columns = getIdentifierColumnNames();
/* 139:188 */         columnReaders = getIdentifierColumnReaders();
/* 140:189 */         columnReaderTemplates = getIdentifierColumnReaderTemplates();
/* 141:    */       }
/* 142:    */       else
/* 143:    */       {
/* 144:192 */         String foreignKeyProperty = actype.getLHSPropertyName();
/* 145:193 */         if ((foreignKeyProperty != null) && (!path.equals(foreignKeyProperty)))
/* 146:    */         {
/* 147:196 */           columns = (String[])this.columnsByPropertyPath.get(foreignKeyProperty);
/* 148:197 */           if (columns == null) {
/* 149:197 */             return;
/* 150:    */           }
/* 151:198 */           columnReaders = (String[])this.columnReadersByPropertyPath.get(foreignKeyProperty);
/* 152:199 */           columnReaderTemplates = (String[])this.columnReaderTemplatesByPropertyPath.get(foreignKeyProperty);
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:204 */     if (path != null) {
/* 157:204 */       addPropertyPath(path, type, columns, columnReaders, columnReaderTemplates, formulaTemplates);
/* 158:    */     }
/* 159:206 */     if (type.isComponentType())
/* 160:    */     {
/* 161:207 */       CompositeType actype = (CompositeType)type;
/* 162:208 */       initComponentPropertyPaths(path, actype, columns, columnReaders, columnReaderTemplates, formulaTemplates, factory);
/* 163:209 */       if (actype.isEmbedded()) {
/* 164:210 */         initComponentPropertyPaths(path == null ? null : StringHelper.qualifier(path), actype, columns, columnReaders, columnReaderTemplates, formulaTemplates, factory);
/* 165:    */       }
/* 166:    */     }
/* 167:221 */     else if (type.isEntityType())
/* 168:    */     {
/* 169:222 */       initIdentifierPropertyPaths(path, (EntityType)type, columns, columnReaders, columnReaderTemplates, factory);
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   protected void initIdentifierPropertyPaths(String path, EntityType etype, String[] columns, String[] columnReaders, String[] columnReaderTemplates, Mapping factory)
/* 174:    */     throws MappingException
/* 175:    */   {
/* 176:234 */     Type idtype = etype.getIdentifierOrUniqueKeyType(factory);
/* 177:235 */     String idPropName = etype.getIdentifierOrUniqueKeyPropertyName(factory);
/* 178:236 */     boolean hasNonIdentifierPropertyNamedId = hasNonIdentifierPropertyNamedId(etype, factory);
/* 179:238 */     if ((etype.isReferenceToPrimaryKey()) && 
/* 180:239 */       (!hasNonIdentifierPropertyNamedId))
/* 181:    */     {
/* 182:240 */       String idpath1 = extendPath(path, "id");
/* 183:241 */       addPropertyPath(idpath1, idtype, columns, columnReaders, columnReaderTemplates, null);
/* 184:242 */       initPropertyPaths(idpath1, idtype, columns, columnReaders, columnReaderTemplates, null, factory);
/* 185:    */     }
/* 186:246 */     if (idPropName != null)
/* 187:    */     {
/* 188:247 */       String idpath2 = extendPath(path, idPropName);
/* 189:248 */       addPropertyPath(idpath2, idtype, columns, columnReaders, columnReaderTemplates, null);
/* 190:249 */       initPropertyPaths(idpath2, idtype, columns, columnReaders, columnReaderTemplates, null, factory);
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   private boolean hasNonIdentifierPropertyNamedId(EntityType entityType, Mapping factory)
/* 195:    */   {
/* 196:    */     try
/* 197:    */     {
/* 198:258 */       return factory.getReferencedPropertyType(entityType.getAssociatedEntityName(), "id") != null;
/* 199:    */     }
/* 200:    */     catch (MappingException e) {}
/* 201:261 */     return false;
/* 202:    */   }
/* 203:    */   
/* 204:    */   protected void initComponentPropertyPaths(String path, CompositeType type, String[] columns, String[] columnReaders, String[] columnReaderTemplates, String[] formulaTemplates, Mapping factory)
/* 205:    */     throws MappingException
/* 206:    */   {
/* 207:273 */     Type[] types = type.getSubtypes();
/* 208:274 */     String[] properties = type.getPropertyNames();
/* 209:275 */     int begin = 0;
/* 210:276 */     for (int i = 0; i < properties.length; i++)
/* 211:    */     {
/* 212:277 */       String subpath = extendPath(path, properties[i]);
/* 213:    */       try
/* 214:    */       {
/* 215:279 */         int length = types[i].getColumnSpan(factory);
/* 216:280 */         String[] columnSlice = ArrayHelper.slice(columns, begin, length);
/* 217:281 */         String[] columnReaderSlice = ArrayHelper.slice(columnReaders, begin, length);
/* 218:282 */         String[] columnReaderTemplateSlice = ArrayHelper.slice(columnReaderTemplates, begin, length);
/* 219:283 */         String[] formulaSlice = formulaTemplates == null ? null : ArrayHelper.slice(formulaTemplates, begin, length);
/* 220:    */         
/* 221:285 */         initPropertyPaths(subpath, types[i], columnSlice, columnReaderSlice, columnReaderTemplateSlice, formulaSlice, factory);
/* 222:286 */         begin += length;
/* 223:    */       }
/* 224:    */       catch (Exception e)
/* 225:    */       {
/* 226:289 */         throw new MappingException("bug in initComponentPropertyPaths", e);
/* 227:    */       }
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   private static String extendPath(String path, String property)
/* 232:    */   {
/* 233:295 */     if ((path == null) || ("".equals(path))) {
/* 234:296 */       return property;
/* 235:    */     }
/* 236:299 */     return StringHelper.qualify(path, property);
/* 237:    */   }
/* 238:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.AbstractPropertyMapping
 * JD-Core Version:    0.7.0.1
 */