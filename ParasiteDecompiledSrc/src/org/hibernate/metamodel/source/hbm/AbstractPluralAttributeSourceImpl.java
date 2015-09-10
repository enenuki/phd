/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.FetchMode;
/*   6:    */ import org.hibernate.cache.spi.access.AccessType;
/*   7:    */ import org.hibernate.cfg.NotYetImplementedException;
/*   8:    */ import org.hibernate.engine.FetchStyle;
/*   9:    */ import org.hibernate.engine.FetchTiming;
/*  10:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbCacheElement;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchAttributeWithSubselect;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbLazyAttributeWithExtra;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbLoaderElement;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbOuterJoinAttribute;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.hbm.PluralAttributeElement;
/*  17:    */ import org.hibernate.internal.util.StringHelper;
/*  18:    */ import org.hibernate.metamodel.binding.Caching;
/*  19:    */ import org.hibernate.metamodel.binding.CustomSQL;
/*  20:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  21:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  22:    */ import org.hibernate.metamodel.source.MappingException;
/*  23:    */ import org.hibernate.metamodel.source.binder.AttributeSourceContainer;
/*  24:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  25:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  26:    */ import org.hibernate.metamodel.source.binder.PluralAttributeElementSource;
/*  27:    */ import org.hibernate.metamodel.source.binder.PluralAttributeKeySource;
/*  28:    */ import org.hibernate.metamodel.source.binder.PluralAttributeSource;
/*  29:    */ 
/*  30:    */ public abstract class AbstractPluralAttributeSourceImpl
/*  31:    */   implements PluralAttributeSource
/*  32:    */ {
/*  33:    */   private final PluralAttributeElement pluralAttributeElement;
/*  34:    */   private final AttributeSourceContainer container;
/*  35:    */   private final ExplicitHibernateTypeSource typeInformation;
/*  36:    */   private final PluralAttributeKeySource keySource;
/*  37:    */   private final PluralAttributeElementSource elementSource;
/*  38:    */   
/*  39:    */   protected AbstractPluralAttributeSourceImpl(final PluralAttributeElement pluralAttributeElement, AttributeSourceContainer container)
/*  40:    */   {
/*  41: 64 */     this.pluralAttributeElement = pluralAttributeElement;
/*  42: 65 */     this.container = container;
/*  43:    */     
/*  44: 67 */     this.keySource = new PluralAttributeKeySourceImpl(pluralAttributeElement.getKey(), container);
/*  45: 68 */     this.elementSource = interpretElementType();
/*  46:    */     
/*  47: 70 */     this.typeInformation = new ExplicitHibernateTypeSource()
/*  48:    */     {
/*  49:    */       public String getName()
/*  50:    */       {
/*  51: 73 */         return pluralAttributeElement.getCollectionType();
/*  52:    */       }
/*  53:    */       
/*  54:    */       public Map<String, String> getParameters()
/*  55:    */       {
/*  56: 78 */         return Collections.emptyMap();
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   private PluralAttributeElementSource interpretElementType()
/*  62:    */   {
/*  63: 84 */     if (this.pluralAttributeElement.getElement() != null) {
/*  64: 85 */       return new BasicPluralAttributeElementSourceImpl(this.pluralAttributeElement.getElement(), this.container.getLocalBindingContext());
/*  65:    */     }
/*  66: 89 */     if (this.pluralAttributeElement.getCompositeElement() != null) {
/*  67: 90 */       return new CompositePluralAttributeElementSourceImpl(this.pluralAttributeElement.getCompositeElement(), this.container.getLocalBindingContext());
/*  68:    */     }
/*  69: 94 */     if (this.pluralAttributeElement.getOneToMany() != null) {
/*  70: 95 */       return new OneToManyPluralAttributeElementSourceImpl(this.pluralAttributeElement.getOneToMany(), this.container.getLocalBindingContext());
/*  71:    */     }
/*  72: 99 */     if (this.pluralAttributeElement.getManyToMany() != null) {
/*  73:100 */       return new ManyToManyPluralAttributeElementSourceImpl(this.pluralAttributeElement.getManyToMany(), this.container.getLocalBindingContext());
/*  74:    */     }
/*  75:104 */     if (this.pluralAttributeElement.getManyToAny() != null) {
/*  76:105 */       throw new NotYetImplementedException("Support for many-to-any not yet implemented");
/*  77:    */     }
/*  78:109 */     throw new MappingException("Unexpected collection element type : " + this.pluralAttributeElement.getName(), bindingContext().getOrigin());
/*  79:    */   }
/*  80:    */   
/*  81:    */   public PluralAttributeElement getPluralAttributeElement()
/*  82:    */   {
/*  83:117 */     return this.pluralAttributeElement;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected AttributeSourceContainer container()
/*  87:    */   {
/*  88:121 */     return this.container;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected LocalBindingContext bindingContext()
/*  92:    */   {
/*  93:125 */     return container().getLocalBindingContext();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public PluralAttributeKeySource getKeySource()
/*  97:    */   {
/*  98:130 */     return this.keySource;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public PluralAttributeElementSource getElementSource()
/* 102:    */   {
/* 103:135 */     return this.elementSource;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getExplicitSchemaName()
/* 107:    */   {
/* 108:140 */     return this.pluralAttributeElement.getSchema();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getExplicitCatalogName()
/* 112:    */   {
/* 113:145 */     return this.pluralAttributeElement.getCatalog();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getExplicitCollectionTableName()
/* 117:    */   {
/* 118:150 */     return this.pluralAttributeElement.getTable();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getCollectionTableComment()
/* 122:    */   {
/* 123:155 */     return this.pluralAttributeElement.getComment();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getCollectionTableCheck()
/* 127:    */   {
/* 128:160 */     return this.pluralAttributeElement.getCheck();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Caching getCaching()
/* 132:    */   {
/* 133:165 */     JaxbCacheElement cache = this.pluralAttributeElement.getCache();
/* 134:166 */     if (cache == null) {
/* 135:167 */       return null;
/* 136:    */     }
/* 137:169 */     String region = cache.getRegion() != null ? cache.getRegion() : StringHelper.qualify(container().getPath(), getName());
/* 138:    */     
/* 139:    */ 
/* 140:172 */     AccessType accessType = (AccessType)Enum.valueOf(AccessType.class, cache.getUsage());
/* 141:173 */     boolean cacheLazyProps = !"non-lazy".equals(cache.getInclude());
/* 142:174 */     return new Caching(region, accessType, cacheLazyProps);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getWhere()
/* 146:    */   {
/* 147:179 */     return this.pluralAttributeElement.getWhere();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getName()
/* 151:    */   {
/* 152:184 */     return this.pluralAttributeElement.getName();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean isSingular()
/* 156:    */   {
/* 157:189 */     return false;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public ExplicitHibernateTypeSource getTypeInformation()
/* 161:    */   {
/* 162:194 */     return this.typeInformation;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getPropertyAccessorName()
/* 166:    */   {
/* 167:199 */     return this.pluralAttributeElement.getAccess();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean isIncludedInOptimisticLocking()
/* 171:    */   {
/* 172:204 */     return this.pluralAttributeElement.isOptimisticLock();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean isInverse()
/* 176:    */   {
/* 177:209 */     return this.pluralAttributeElement.isInverse();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getCustomPersisterClassName()
/* 181:    */   {
/* 182:214 */     return this.pluralAttributeElement.getPersister();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getCustomLoaderName()
/* 186:    */   {
/* 187:219 */     return this.pluralAttributeElement.getLoader() == null ? null : this.pluralAttributeElement.getLoader().getQueryRef();
/* 188:    */   }
/* 189:    */   
/* 190:    */   public CustomSQL getCustomSqlInsert()
/* 191:    */   {
/* 192:226 */     return Helper.buildCustomSql(this.pluralAttributeElement.getSqlInsert());
/* 193:    */   }
/* 194:    */   
/* 195:    */   public CustomSQL getCustomSqlUpdate()
/* 196:    */   {
/* 197:231 */     return Helper.buildCustomSql(this.pluralAttributeElement.getSqlUpdate());
/* 198:    */   }
/* 199:    */   
/* 200:    */   public CustomSQL getCustomSqlDelete()
/* 201:    */   {
/* 202:236 */     return Helper.buildCustomSql(this.pluralAttributeElement.getSqlDelete());
/* 203:    */   }
/* 204:    */   
/* 205:    */   public CustomSQL getCustomSqlDeleteAll()
/* 206:    */   {
/* 207:241 */     return Helper.buildCustomSql(this.pluralAttributeElement.getSqlDeleteAll());
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 211:    */   {
/* 212:246 */     return Helper.buildMetaAttributeSources(this.pluralAttributeElement.getMeta());
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Iterable<CascadeStyle> getCascadeStyles()
/* 216:    */   {
/* 217:251 */     return Helper.interpretCascadeStyles(this.pluralAttributeElement.getCascade(), bindingContext());
/* 218:    */   }
/* 219:    */   
/* 220:    */   public FetchTiming getFetchTiming()
/* 221:    */   {
/* 222:256 */     String fetchSelection = this.pluralAttributeElement.getFetch() != null ? this.pluralAttributeElement.getFetch().value() : null;
/* 223:    */     
/* 224:    */ 
/* 225:259 */     String lazySelection = this.pluralAttributeElement.getLazy() != null ? this.pluralAttributeElement.getLazy().value() : null;
/* 226:    */     
/* 227:    */ 
/* 228:262 */     String outerJoinSelection = this.pluralAttributeElement.getOuterJoin() != null ? this.pluralAttributeElement.getOuterJoin().value() : null;
/* 229:266 */     if (lazySelection == null)
/* 230:    */     {
/* 231:267 */       if (("join".equals(fetchSelection)) || ("true".equals(outerJoinSelection))) {
/* 232:268 */         return FetchTiming.IMMEDIATE;
/* 233:    */       }
/* 234:270 */       if ("false".equals(outerJoinSelection)) {
/* 235:271 */         return FetchTiming.DELAYED;
/* 236:    */       }
/* 237:274 */       return bindingContext().getMappingDefaults().areAssociationsLazy() ? FetchTiming.DELAYED : FetchTiming.IMMEDIATE;
/* 238:    */     }
/* 239:279 */     if ("extra".equals(lazySelection)) {
/* 240:280 */       return FetchTiming.EXTRA_LAZY;
/* 241:    */     }
/* 242:282 */     if ("true".equals(lazySelection)) {
/* 243:283 */       return FetchTiming.DELAYED;
/* 244:    */     }
/* 245:285 */     if ("false".equals(lazySelection)) {
/* 246:286 */       return FetchTiming.IMMEDIATE;
/* 247:    */     }
/* 248:289 */     throw new MappingException(String.format("Unexpected lazy selection [%s] on '%s'", new Object[] { lazySelection, this.pluralAttributeElement.getName() }), bindingContext().getOrigin());
/* 249:    */   }
/* 250:    */   
/* 251:    */   public FetchStyle getFetchStyle()
/* 252:    */   {
/* 253:301 */     String fetchSelection = this.pluralAttributeElement.getFetch() != null ? this.pluralAttributeElement.getFetch().value() : null;
/* 254:    */     
/* 255:    */ 
/* 256:304 */     String outerJoinSelection = this.pluralAttributeElement.getOuterJoin() != null ? this.pluralAttributeElement.getOuterJoin().value() : null;
/* 257:    */     
/* 258:    */ 
/* 259:307 */     int batchSize = Helper.getIntValue(this.pluralAttributeElement.getBatchSize(), -1);
/* 260:309 */     if (fetchSelection == null)
/* 261:    */     {
/* 262:310 */       if (outerJoinSelection == null) {
/* 263:311 */         return batchSize > 1 ? FetchStyle.BATCH : FetchStyle.SELECT;
/* 264:    */       }
/* 265:314 */       if ("auto".equals(outerJoinSelection)) {
/* 266:315 */         return bindingContext().getMappingDefaults().areAssociationsLazy() ? FetchStyle.SELECT : FetchStyle.JOIN;
/* 267:    */       }
/* 268:320 */       return "true".equals(outerJoinSelection) ? FetchStyle.JOIN : FetchStyle.SELECT;
/* 269:    */     }
/* 270:325 */     if ("subselect".equals(fetchSelection)) {
/* 271:326 */       return FetchStyle.SUBSELECT;
/* 272:    */     }
/* 273:329 */     return "join".equals(fetchSelection) ? FetchStyle.JOIN : FetchStyle.SELECT;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public FetchMode getFetchMode()
/* 277:    */   {
/* 278:336 */     return this.pluralAttributeElement.getFetch() == null ? FetchMode.DEFAULT : FetchMode.valueOf(this.pluralAttributeElement.getFetch().value());
/* 279:    */   }
/* 280:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.AbstractPluralAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */