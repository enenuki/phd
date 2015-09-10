/*   1:    */ package org.hibernate.engine.query.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.hibernate.MappingException;
/*  14:    */ import org.hibernate.QueryException;
/*  15:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
/*  16:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  17:    */ import org.hibernate.internal.CoreMessageLogger;
/*  18:    */ import org.hibernate.internal.FilterImpl;
/*  19:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  20:    */ import org.hibernate.internal.util.collections.SimpleMRUCache;
/*  21:    */ import org.hibernate.internal.util.collections.SoftLimitMRUCache;
/*  22:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  23:    */ import org.jboss.logging.Logger;
/*  24:    */ 
/*  25:    */ public class QueryPlanCache
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28: 59 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, QueryPlanCache.class.getName());
/*  29:    */   private final SimpleMRUCache sqlParamMetadataCache;
/*  30:    */   private final SoftLimitMRUCache planCache;
/*  31:    */   private SessionFactoryImplementor factory;
/*  32:    */   
/*  33:    */   public QueryPlanCache(SessionFactoryImplementor factory)
/*  34:    */   {
/*  35: 77 */     int maxStrongReferenceCount = ConfigurationHelper.getInt("hibernate.query.plan_cache_max_strong_references", factory.getProperties(), 128);
/*  36:    */     
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40: 82 */     int maxSoftReferenceCount = ConfigurationHelper.getInt("hibernate.query.plan_cache_max_soft_references", factory.getProperties(), 2048);
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46: 88 */     this.factory = factory;
/*  47: 89 */     this.sqlParamMetadataCache = new SimpleMRUCache(maxStrongReferenceCount);
/*  48: 90 */     this.planCache = new SoftLimitMRUCache(maxStrongReferenceCount, maxSoftReferenceCount);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ParameterMetadata getSQLParameterMetadata(String query)
/*  52:    */   {
/*  53:104 */     ParameterMetadata metadata = (ParameterMetadata)this.sqlParamMetadataCache.get(query);
/*  54:105 */     if (metadata == null)
/*  55:    */     {
/*  56:106 */       metadata = buildNativeSQLParameterMetadata(query);
/*  57:107 */       this.sqlParamMetadataCache.put(query, metadata);
/*  58:    */     }
/*  59:109 */     return metadata;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public HQLQueryPlan getHQLQueryPlan(String queryString, boolean shallow, Map enabledFilters)
/*  63:    */     throws QueryException, MappingException
/*  64:    */   {
/*  65:114 */     HQLQueryPlanKey key = new HQLQueryPlanKey(queryString, shallow, enabledFilters);
/*  66:115 */     HQLQueryPlan plan = (HQLQueryPlan)this.planCache.get(key);
/*  67:117 */     if (plan == null)
/*  68:    */     {
/*  69:118 */       LOG.tracev("Unable to locate HQL query plan in cache; generating ({0})", queryString);
/*  70:119 */       plan = new HQLQueryPlan(queryString, shallow, enabledFilters, this.factory);
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74:122 */       LOG.tracev("Located HQL query plan in cache ({0})", queryString);
/*  75:    */     }
/*  76:124 */     this.planCache.put(key, plan);
/*  77:    */     
/*  78:126 */     return plan;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public FilterQueryPlan getFilterQueryPlan(String filterString, String collectionRole, boolean shallow, Map enabledFilters)
/*  82:    */     throws QueryException, MappingException
/*  83:    */   {
/*  84:131 */     FilterQueryPlanKey key = new FilterQueryPlanKey(filterString, collectionRole, shallow, enabledFilters);
/*  85:132 */     FilterQueryPlan plan = (FilterQueryPlan)this.planCache.get(key);
/*  86:134 */     if (plan == null)
/*  87:    */     {
/*  88:135 */       LOG.tracev("Unable to locate collection-filter query plan in cache; generating ({0} : {1} )", collectionRole, filterString);
/*  89:    */       
/*  90:137 */       plan = new FilterQueryPlan(filterString, collectionRole, shallow, enabledFilters, this.factory);
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:140 */       LOG.tracev("Located collection-filter query plan in cache ({0} : {1})", collectionRole, filterString);
/*  95:    */     }
/*  96:143 */     this.planCache.put(key, plan);
/*  97:    */     
/*  98:145 */     return plan;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public NativeSQLQueryPlan getNativeSQLQueryPlan(NativeSQLQuerySpecification spec)
/* 102:    */   {
/* 103:149 */     NativeSQLQueryPlan plan = (NativeSQLQueryPlan)this.planCache.get(spec);
/* 104:151 */     if (plan == null)
/* 105:    */     {
/* 106:152 */       if (LOG.isTraceEnabled()) {
/* 107:153 */         LOG.tracev("Unable to locate native-sql query plan in cache; generating ({0})", spec.getQueryString());
/* 108:    */       }
/* 109:155 */       plan = new NativeSQLQueryPlan(spec, this.factory);
/* 110:    */     }
/* 111:158 */     else if (LOG.isTraceEnabled())
/* 112:    */     {
/* 113:159 */       LOG.tracev("Located native-sql query plan in cache ({0})", spec.getQueryString());
/* 114:    */     }
/* 115:163 */     this.planCache.put(spec, plan);
/* 116:164 */     return plan;
/* 117:    */   }
/* 118:    */   
/* 119:    */   private ParameterMetadata buildNativeSQLParameterMetadata(String sqlString)
/* 120:    */   {
/* 121:169 */     ParamLocationRecognizer recognizer = ParamLocationRecognizer.parseLocations(sqlString);
/* 122:    */     
/* 123:171 */     OrdinalParameterDescriptor[] ordinalDescriptors = new OrdinalParameterDescriptor[recognizer.getOrdinalParameterLocationList().size()];
/* 124:173 */     for (int i = 0; i < recognizer.getOrdinalParameterLocationList().size(); i++)
/* 125:    */     {
/* 126:174 */       Integer position = (Integer)recognizer.getOrdinalParameterLocationList().get(i);
/* 127:175 */       ordinalDescriptors[i] = new OrdinalParameterDescriptor(i, null, position.intValue());
/* 128:    */     }
/* 129:178 */     Iterator itr = recognizer.getNamedParameterDescriptionMap().entrySet().iterator();
/* 130:179 */     Map<String, NamedParameterDescriptor> namedParamDescriptorMap = new HashMap();
/* 131:180 */     while (itr.hasNext())
/* 132:    */     {
/* 133:181 */       Map.Entry entry = (Map.Entry)itr.next();
/* 134:182 */       String name = (String)entry.getKey();
/* 135:183 */       ParamLocationRecognizer.NamedParameterDescription description = (ParamLocationRecognizer.NamedParameterDescription)entry.getValue();
/* 136:    */       
/* 137:185 */       namedParamDescriptorMap.put(name, new NamedParameterDescriptor(name, null, description.buildPositionsArray(), description.isJpaStyle()));
/* 138:    */     }
/* 139:191 */     return new ParameterMetadata(ordinalDescriptors, namedParamDescriptorMap);
/* 140:    */   }
/* 141:    */   
/* 142:    */   private static class HQLQueryPlanKey
/* 143:    */     implements Serializable
/* 144:    */   {
/* 145:    */     private final String query;
/* 146:    */     private final boolean shallow;
/* 147:    */     private final Set<QueryPlanCache.DynamicFilterKey> filterKeys;
/* 148:    */     private final int hashCode;
/* 149:    */     
/* 150:    */     public HQLQueryPlanKey(String query, boolean shallow, Map enabledFilters)
/* 151:    */     {
/* 152:201 */       this.query = query;
/* 153:202 */       this.shallow = shallow;
/* 154:204 */       if ((enabledFilters == null) || (enabledFilters.isEmpty()))
/* 155:    */       {
/* 156:205 */         this.filterKeys = Collections.emptySet();
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:208 */         Set<QueryPlanCache.DynamicFilterKey> tmp = new HashSet(CollectionHelper.determineProperSizing(enabledFilters), 0.75F);
/* 161:212 */         for (Object o : enabledFilters.values()) {
/* 162:213 */           tmp.add(new QueryPlanCache.DynamicFilterKey((FilterImpl)o, null));
/* 163:    */         }
/* 164:215 */         this.filterKeys = Collections.unmodifiableSet(tmp);
/* 165:    */       }
/* 166:218 */       int hash = query.hashCode();
/* 167:219 */       hash = 29 * hash + (shallow ? 1 : 0);
/* 168:220 */       hash = 29 * hash + this.filterKeys.hashCode();
/* 169:221 */       this.hashCode = hash;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public boolean equals(Object o)
/* 173:    */     {
/* 174:226 */       if (this == o) {
/* 175:227 */         return true;
/* 176:    */       }
/* 177:229 */       if ((o == null) || (getClass() != o.getClass())) {
/* 178:230 */         return false;
/* 179:    */       }
/* 180:233 */       HQLQueryPlanKey that = (HQLQueryPlanKey)o;
/* 181:    */       
/* 182:235 */       return (this.shallow == that.shallow) && (this.filterKeys.equals(that.filterKeys)) && (this.query.equals(that.query));
/* 183:    */     }
/* 184:    */     
/* 185:    */     public int hashCode()
/* 186:    */     {
/* 187:243 */       return this.hashCode;
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   private static class DynamicFilterKey
/* 192:    */     implements Serializable
/* 193:    */   {
/* 194:    */     private final String filterName;
/* 195:    */     private final Map<String, Integer> parameterMetadata;
/* 196:    */     private final int hashCode;
/* 197:    */     
/* 198:    */     private DynamicFilterKey(FilterImpl filter)
/* 199:    */     {
/* 200:254 */       this.filterName = filter.getName();
/* 201:255 */       if (filter.getParameters().isEmpty())
/* 202:    */       {
/* 203:256 */         this.parameterMetadata = Collections.emptyMap();
/* 204:    */       }
/* 205:    */       else
/* 206:    */       {
/* 207:259 */         this.parameterMetadata = new HashMap(CollectionHelper.determineProperSizing(filter.getParameters()), 0.75F);
/* 208:263 */         for (Object o : filter.getParameters().entrySet())
/* 209:    */         {
/* 210:264 */           Map.Entry entry = (Map.Entry)o;
/* 211:265 */           String key = (String)entry.getKey();
/* 212:    */           Integer valueCount;
/* 213:    */           Integer valueCount;
/* 214:267 */           if (Collection.class.isInstance(entry.getValue())) {
/* 215:268 */             valueCount = Integer.valueOf(((Collection)entry.getValue()).size());
/* 216:    */           } else {
/* 217:271 */             valueCount = Integer.valueOf(1);
/* 218:    */           }
/* 219:273 */           this.parameterMetadata.put(key, valueCount);
/* 220:    */         }
/* 221:    */       }
/* 222:277 */       int hash = this.filterName.hashCode();
/* 223:278 */       hash = 31 * hash + this.parameterMetadata.hashCode();
/* 224:279 */       this.hashCode = hash;
/* 225:    */     }
/* 226:    */     
/* 227:    */     public boolean equals(Object o)
/* 228:    */     {
/* 229:284 */       if (this == o) {
/* 230:285 */         return true;
/* 231:    */       }
/* 232:287 */       if ((o == null) || (getClass() != o.getClass())) {
/* 233:288 */         return false;
/* 234:    */       }
/* 235:291 */       DynamicFilterKey that = (DynamicFilterKey)o;
/* 236:    */       
/* 237:293 */       return (this.filterName.equals(that.filterName)) && (this.parameterMetadata.equals(that.parameterMetadata));
/* 238:    */     }
/* 239:    */     
/* 240:    */     public int hashCode()
/* 241:    */     {
/* 242:300 */       return this.hashCode;
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   private static class FilterQueryPlanKey
/* 247:    */     implements Serializable
/* 248:    */   {
/* 249:    */     private final String query;
/* 250:    */     private final String collectionRole;
/* 251:    */     private final boolean shallow;
/* 252:    */     private final Set<String> filterNames;
/* 253:    */     private final int hashCode;
/* 254:    */     
/* 255:    */     public FilterQueryPlanKey(String query, String collectionRole, boolean shallow, Map enabledFilters)
/* 256:    */     {
/* 257:313 */       this.query = query;
/* 258:314 */       this.collectionRole = collectionRole;
/* 259:315 */       this.shallow = shallow;
/* 260:317 */       if ((enabledFilters == null) || (enabledFilters.isEmpty()))
/* 261:    */       {
/* 262:318 */         this.filterNames = Collections.emptySet();
/* 263:    */       }
/* 264:    */       else
/* 265:    */       {
/* 266:321 */         Set<String> tmp = new HashSet();
/* 267:322 */         tmp.addAll(enabledFilters.keySet());
/* 268:323 */         this.filterNames = Collections.unmodifiableSet(tmp);
/* 269:    */       }
/* 270:326 */       int hash = query.hashCode();
/* 271:327 */       hash = 29 * hash + collectionRole.hashCode();
/* 272:328 */       hash = 29 * hash + (shallow ? 1 : 0);
/* 273:329 */       hash = 29 * hash + this.filterNames.hashCode();
/* 274:330 */       this.hashCode = hash;
/* 275:    */     }
/* 276:    */     
/* 277:    */     public boolean equals(Object o)
/* 278:    */     {
/* 279:335 */       if (this == o) {
/* 280:336 */         return true;
/* 281:    */       }
/* 282:338 */       if ((o == null) || (getClass() != o.getClass())) {
/* 283:339 */         return false;
/* 284:    */       }
/* 285:342 */       FilterQueryPlanKey that = (FilterQueryPlanKey)o;
/* 286:    */       
/* 287:344 */       return (this.shallow == that.shallow) && (this.filterNames.equals(that.filterNames)) && (this.query.equals(that.query)) && (this.collectionRole.equals(that.collectionRole));
/* 288:    */     }
/* 289:    */     
/* 290:    */     public int hashCode()
/* 291:    */     {
/* 292:353 */       return this.hashCode;
/* 293:    */     }
/* 294:    */   }
/* 295:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.QueryPlanCache
 * JD-Core Version:    0.7.0.1
 */