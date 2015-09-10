/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.AssertionFailure;
/*  11:    */ import org.hibernate.FetchMode;
/*  12:    */ import org.hibernate.engine.FetchStyle;
/*  13:    */ import org.hibernate.engine.FetchTiming;
/*  14:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  15:    */ import org.hibernate.engine.spi.CascadeStyle.MultipleCascadeStyle;
/*  16:    */ import org.hibernate.metamodel.domain.PluralAttribute;
/*  17:    */ import org.hibernate.metamodel.relational.Table;
/*  18:    */ import org.hibernate.metamodel.relational.TableSpecification;
/*  19:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  20:    */ 
/*  21:    */ public abstract class AbstractPluralAttributeBinding
/*  22:    */   extends AbstractAttributeBinding
/*  23:    */   implements PluralAttributeBinding
/*  24:    */ {
/*  25:    */   private final CollectionKey collectionKey;
/*  26:    */   private final AbstractCollectionElement collectionElement;
/*  27:    */   private Table collectionTable;
/*  28:    */   private FetchTiming fetchTiming;
/*  29:    */   private FetchStyle fetchStyle;
/*  30: 55 */   private int batchSize = -1;
/*  31:    */   private CascadeStyle cascadeStyle;
/*  32:    */   private boolean orphanDelete;
/*  33:    */   private Caching caching;
/*  34:    */   private boolean inverse;
/*  35: 63 */   private boolean mutable = true;
/*  36:    */   private Class<? extends CollectionPersister> collectionPersisterClass;
/*  37:    */   private String where;
/*  38:    */   private String orderBy;
/*  39:    */   private boolean sorted;
/*  40:    */   private Comparator comparator;
/*  41:    */   private String comparatorClassName;
/*  42:    */   private String customLoaderName;
/*  43:    */   private CustomSQL customSqlInsert;
/*  44:    */   private CustomSQL customSqlUpdate;
/*  45:    */   private CustomSQL customSqlDelete;
/*  46:    */   private CustomSQL customSqlDeleteAll;
/*  47:    */   private String referencedPropertyName;
/*  48: 81 */   private final Map filters = new HashMap();
/*  49: 82 */   private final Set<String> synchronizedTables = new HashSet();
/*  50:    */   
/*  51:    */   protected AbstractPluralAttributeBinding(AttributeBindingContainer container, PluralAttribute attribute, CollectionElementNature collectionElementNature)
/*  52:    */   {
/*  53: 88 */     super(container, attribute);
/*  54: 89 */     this.collectionKey = new CollectionKey(this);
/*  55: 90 */     this.collectionElement = interpretNature(collectionElementNature);
/*  56:    */   }
/*  57:    */   
/*  58:    */   private AbstractCollectionElement interpretNature(CollectionElementNature collectionElementNature)
/*  59:    */   {
/*  60: 94 */     switch (1.$SwitchMap$org$hibernate$metamodel$binding$CollectionElementNature[collectionElementNature.ordinal()])
/*  61:    */     {
/*  62:    */     case 1: 
/*  63: 96 */       return new BasicCollectionElement(this);
/*  64:    */     case 2: 
/*  65: 99 */       return new CompositeCollectionElement(this);
/*  66:    */     case 3: 
/*  67:102 */       return new OneToManyCollectionElement(this);
/*  68:    */     case 4: 
/*  69:105 */       return new ManyToManyCollectionElement(this);
/*  70:    */     case 5: 
/*  71:108 */       return new ManyToAnyCollectionElement(this);
/*  72:    */     }
/*  73:111 */     throw new AssertionFailure("Unknown collection element nature : " + collectionElementNature);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public PluralAttribute getAttribute()
/*  77:    */   {
/*  78:152 */     return (PluralAttribute)super.getAttribute();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isAssociation()
/*  82:    */   {
/*  83:157 */     return (this.collectionElement.getCollectionElementNature() == CollectionElementNature.MANY_TO_ANY) || (this.collectionElement.getCollectionElementNature() == CollectionElementNature.MANY_TO_MANY) || (this.collectionElement.getCollectionElementNature() == CollectionElementNature.ONE_TO_MANY);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public TableSpecification getCollectionTable()
/*  87:    */   {
/*  88:164 */     return this.collectionTable;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCollectionTable(Table collectionTable)
/*  92:    */   {
/*  93:168 */     this.collectionTable = collectionTable;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CollectionKey getCollectionKey()
/*  97:    */   {
/*  98:173 */     return this.collectionKey;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public AbstractCollectionElement getCollectionElement()
/* 102:    */   {
/* 103:178 */     return this.collectionElement;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public CascadeStyle getCascadeStyle()
/* 107:    */   {
/* 108:183 */     return this.cascadeStyle;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setCascadeStyles(Iterable<CascadeStyle> cascadeStyles)
/* 112:    */   {
/* 113:188 */     List<CascadeStyle> cascadeStyleList = new ArrayList();
/* 114:189 */     for (CascadeStyle style : cascadeStyles)
/* 115:    */     {
/* 116:190 */       if (style != CascadeStyle.NONE) {
/* 117:191 */         cascadeStyleList.add(style);
/* 118:    */       }
/* 119:193 */       if ((style == CascadeStyle.DELETE_ORPHAN) || (style == CascadeStyle.ALL_DELETE_ORPHAN)) {
/* 120:195 */         this.orphanDelete = true;
/* 121:    */       }
/* 122:    */     }
/* 123:199 */     if (cascadeStyleList.isEmpty()) {
/* 124:200 */       this.cascadeStyle = CascadeStyle.NONE;
/* 125:202 */     } else if (cascadeStyleList.size() == 1) {
/* 126:203 */       this.cascadeStyle = ((CascadeStyle)cascadeStyleList.get(0));
/* 127:    */     } else {
/* 128:206 */       this.cascadeStyle = new CascadeStyle.MultipleCascadeStyle((CascadeStyle[])cascadeStyleList.toArray(new CascadeStyle[cascadeStyleList.size()]));
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isOrphanDelete()
/* 133:    */   {
/* 134:214 */     return this.orphanDelete;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public FetchMode getFetchMode()
/* 138:    */   {
/* 139:219 */     if (getFetchStyle() == FetchStyle.JOIN) {
/* 140:220 */       return FetchMode.JOIN;
/* 141:    */     }
/* 142:223 */     return FetchMode.SELECT;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public FetchTiming getFetchTiming()
/* 146:    */   {
/* 147:229 */     return this.fetchTiming;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setFetchTiming(FetchTiming fetchTiming)
/* 151:    */   {
/* 152:234 */     this.fetchTiming = fetchTiming;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public FetchStyle getFetchStyle()
/* 156:    */   {
/* 157:239 */     return this.fetchStyle;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setFetchStyle(FetchStyle fetchStyle)
/* 161:    */   {
/* 162:244 */     this.fetchStyle = fetchStyle;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getCustomLoaderName()
/* 166:    */   {
/* 167:249 */     return this.customLoaderName;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setCustomLoaderName(String customLoaderName)
/* 171:    */   {
/* 172:253 */     this.customLoaderName = customLoaderName;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public CustomSQL getCustomSqlInsert()
/* 176:    */   {
/* 177:258 */     return this.customSqlInsert;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setCustomSqlInsert(CustomSQL customSqlInsert)
/* 181:    */   {
/* 182:262 */     this.customSqlInsert = customSqlInsert;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public CustomSQL getCustomSqlUpdate()
/* 186:    */   {
/* 187:267 */     return this.customSqlUpdate;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setCustomSqlUpdate(CustomSQL customSqlUpdate)
/* 191:    */   {
/* 192:271 */     this.customSqlUpdate = customSqlUpdate;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public CustomSQL getCustomSqlDelete()
/* 196:    */   {
/* 197:276 */     return this.customSqlDelete;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setCustomSqlDelete(CustomSQL customSqlDelete)
/* 201:    */   {
/* 202:280 */     this.customSqlDelete = customSqlDelete;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public CustomSQL getCustomSqlDeleteAll()
/* 206:    */   {
/* 207:285 */     return this.customSqlDeleteAll;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setCustomSqlDeleteAll(CustomSQL customSqlDeleteAll)
/* 211:    */   {
/* 212:289 */     this.customSqlDeleteAll = customSqlDeleteAll;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Class<? extends CollectionPersister> getCollectionPersisterClass()
/* 216:    */   {
/* 217:293 */     return this.collectionPersisterClass;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setCollectionPersisterClass(Class<? extends CollectionPersister> collectionPersisterClass)
/* 221:    */   {
/* 222:297 */     this.collectionPersisterClass = collectionPersisterClass;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Caching getCaching()
/* 226:    */   {
/* 227:301 */     return this.caching;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setCaching(Caching caching)
/* 231:    */   {
/* 232:305 */     this.caching = caching;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String getOrderBy()
/* 236:    */   {
/* 237:310 */     return this.orderBy;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setOrderBy(String orderBy)
/* 241:    */   {
/* 242:314 */     this.orderBy = orderBy;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public String getWhere()
/* 246:    */   {
/* 247:319 */     return this.where;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setWhere(String where)
/* 251:    */   {
/* 252:323 */     this.where = where;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public boolean isInverse()
/* 256:    */   {
/* 257:328 */     return this.inverse;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setInverse(boolean inverse)
/* 261:    */   {
/* 262:332 */     this.inverse = inverse;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public boolean isMutable()
/* 266:    */   {
/* 267:337 */     return this.mutable;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setMutable(boolean mutable)
/* 271:    */   {
/* 272:341 */     this.mutable = mutable;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public int getBatchSize()
/* 276:    */   {
/* 277:346 */     return this.batchSize;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setBatchSize(int batchSize)
/* 281:    */   {
/* 282:350 */     this.batchSize = batchSize;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getReferencedPropertyName()
/* 286:    */   {
/* 287:364 */     return this.referencedPropertyName;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public boolean isSorted()
/* 291:    */   {
/* 292:369 */     return this.sorted;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Comparator getComparator()
/* 296:    */   {
/* 297:374 */     return this.comparator;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setComparator(Comparator comparator)
/* 301:    */   {
/* 302:378 */     this.comparator = comparator;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public String getComparatorClassName()
/* 306:    */   {
/* 307:382 */     return this.comparatorClassName;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void addFilter(String name, String condition)
/* 311:    */   {
/* 312:386 */     this.filters.put(name, condition);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public Map getFilterMap()
/* 316:    */   {
/* 317:391 */     return this.filters;
/* 318:    */   }
/* 319:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.AbstractPluralAttributeBinding
 * JD-Core Version:    0.7.0.1
 */