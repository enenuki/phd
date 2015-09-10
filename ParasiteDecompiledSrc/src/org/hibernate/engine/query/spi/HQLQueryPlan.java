/*   1:    */ package org.hibernate.engine.query.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.QueryException;
/*  15:    */ import org.hibernate.ScrollableResults;
/*  16:    */ import org.hibernate.cfg.Settings;
/*  17:    */ import org.hibernate.engine.spi.QueryParameters;
/*  18:    */ import org.hibernate.engine.spi.RowSelection;
/*  19:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  20:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  21:    */ import org.hibernate.event.spi.EventSource;
/*  22:    */ import org.hibernate.hql.internal.QuerySplitter;
/*  23:    */ import org.hibernate.hql.spi.FilterTranslator;
/*  24:    */ import org.hibernate.hql.spi.ParameterTranslations;
/*  25:    */ import org.hibernate.hql.spi.QueryTranslator;
/*  26:    */ import org.hibernate.hql.spi.QueryTranslatorFactory;
/*  27:    */ import org.hibernate.internal.CoreMessageLogger;
/*  28:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  29:    */ import org.hibernate.internal.util.collections.EmptyIterator;
/*  30:    */ import org.hibernate.internal.util.collections.IdentitySet;
/*  31:    */ import org.hibernate.internal.util.collections.JoinedIterator;
/*  32:    */ import org.hibernate.type.Type;
/*  33:    */ import org.jboss.logging.Logger;
/*  34:    */ 
/*  35:    */ public class HQLQueryPlan
/*  36:    */   implements Serializable
/*  37:    */ {
/*  38: 65 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, HQLQueryPlan.class.getName());
/*  39:    */   private final String sourceQuery;
/*  40:    */   private final QueryTranslator[] translators;
/*  41:    */   private final String[] sqlStrings;
/*  42:    */   private final ParameterMetadata parameterMetadata;
/*  43:    */   private final ReturnMetadata returnMetadata;
/*  44:    */   private final Set querySpaces;
/*  45:    */   private final Set enabledFilterNames;
/*  46:    */   private final boolean shallow;
/*  47:    */   
/*  48:    */   public HQLQueryPlan(String hql, boolean shallow, Map enabledFilters, SessionFactoryImplementor factory)
/*  49:    */   {
/*  50: 80 */     this(hql, null, shallow, enabledFilters, factory);
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected HQLQueryPlan(String hql, String collectionRole, boolean shallow, Map enabledFilters, SessionFactoryImplementor factory)
/*  54:    */   {
/*  55: 84 */     this.sourceQuery = hql;
/*  56: 85 */     this.shallow = shallow;
/*  57:    */     
/*  58: 87 */     Set copy = new HashSet();
/*  59: 88 */     copy.addAll(enabledFilters.keySet());
/*  60: 89 */     this.enabledFilterNames = Collections.unmodifiableSet(copy);
/*  61:    */     
/*  62: 91 */     Set combinedQuerySpaces = new HashSet();
/*  63: 92 */     String[] concreteQueryStrings = QuerySplitter.concreteQueries(hql, factory);
/*  64: 93 */     int length = concreteQueryStrings.length;
/*  65: 94 */     this.translators = new QueryTranslator[length];
/*  66: 95 */     List sqlStringList = new ArrayList();
/*  67: 96 */     for (int i = 0; i < length; i++)
/*  68:    */     {
/*  69: 97 */       if (collectionRole == null)
/*  70:    */       {
/*  71: 98 */         this.translators[i] = factory.getSettings().getQueryTranslatorFactory().createQueryTranslator(hql, concreteQueryStrings[i], enabledFilters, factory);
/*  72:    */         
/*  73:    */ 
/*  74:101 */         this.translators[i].compile(factory.getSettings().getQuerySubstitutions(), shallow);
/*  75:    */       }
/*  76:    */       else
/*  77:    */       {
/*  78:104 */         this.translators[i] = factory.getSettings().getQueryTranslatorFactory().createFilterTranslator(hql, concreteQueryStrings[i], enabledFilters, factory);
/*  79:    */         
/*  80:    */ 
/*  81:107 */         ((FilterTranslator)this.translators[i]).compile(collectionRole, factory.getSettings().getQuerySubstitutions(), shallow);
/*  82:    */       }
/*  83:109 */       combinedQuerySpaces.addAll(this.translators[i].getQuerySpaces());
/*  84:110 */       sqlStringList.addAll(this.translators[i].collectSqlStrings());
/*  85:    */     }
/*  86:113 */     this.sqlStrings = ArrayHelper.toStringArray(sqlStringList);
/*  87:114 */     this.querySpaces = combinedQuerySpaces;
/*  88:116 */     if (length == 0)
/*  89:    */     {
/*  90:117 */       this.parameterMetadata = new ParameterMetadata(null, null);
/*  91:118 */       this.returnMetadata = null;
/*  92:    */     }
/*  93:    */     else
/*  94:    */     {
/*  95:121 */       this.parameterMetadata = buildParameterMetadata(this.translators[0].getParameterTranslations(), hql);
/*  96:122 */       if (this.translators[0].isManipulationStatement())
/*  97:    */       {
/*  98:123 */         this.returnMetadata = null;
/*  99:    */       }
/* 100:126 */       else if (length > 1)
/* 101:    */       {
/* 102:127 */         int returns = this.translators[0].getReturnTypes().length;
/* 103:128 */         this.returnMetadata = new ReturnMetadata(this.translators[0].getReturnAliases(), new Type[returns]);
/* 104:    */       }
/* 105:    */       else
/* 106:    */       {
/* 107:131 */         this.returnMetadata = new ReturnMetadata(this.translators[0].getReturnAliases(), this.translators[0].getReturnTypes());
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getSourceQuery()
/* 113:    */   {
/* 114:138 */     return this.sourceQuery;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Set getQuerySpaces()
/* 118:    */   {
/* 119:142 */     return this.querySpaces;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public ParameterMetadata getParameterMetadata()
/* 123:    */   {
/* 124:146 */     return this.parameterMetadata;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public ReturnMetadata getReturnMetadata()
/* 128:    */   {
/* 129:150 */     return this.returnMetadata;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Set getEnabledFilterNames()
/* 133:    */   {
/* 134:154 */     return this.enabledFilterNames;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String[] getSqlStrings()
/* 138:    */   {
/* 139:158 */     return this.sqlStrings;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Set getUtilizedFilterNames()
/* 143:    */   {
/* 144:163 */     return null;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isShallow()
/* 148:    */   {
/* 149:167 */     return this.shallow;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List performList(QueryParameters queryParameters, SessionImplementor session)
/* 153:    */     throws HibernateException
/* 154:    */   {
/* 155:173 */     if (LOG.isTraceEnabled())
/* 156:    */     {
/* 157:174 */       LOG.tracev("Find: {0}", getSourceQuery());
/* 158:175 */       queryParameters.traceParameters(session.getFactory());
/* 159:    */     }
/* 160:177 */     boolean hasLimit = (queryParameters.getRowSelection() != null) && (queryParameters.getRowSelection().definesLimits());
/* 161:    */     
/* 162:179 */     boolean needsLimit = (hasLimit) && (this.translators.length > 1);
/* 163:    */     QueryParameters queryParametersToUse;
/* 164:    */     QueryParameters queryParametersToUse;
/* 165:181 */     if (needsLimit)
/* 166:    */     {
/* 167:182 */       LOG.needsLimit();
/* 168:183 */       RowSelection selection = new RowSelection();
/* 169:184 */       selection.setFetchSize(queryParameters.getRowSelection().getFetchSize());
/* 170:185 */       selection.setTimeout(queryParameters.getRowSelection().getTimeout());
/* 171:186 */       queryParametersToUse = queryParameters.createCopyUsing(selection);
/* 172:    */     }
/* 173:    */     else
/* 174:    */     {
/* 175:189 */       queryParametersToUse = queryParameters;
/* 176:    */     }
/* 177:192 */     List combinedResults = new ArrayList();
/* 178:193 */     IdentitySet distinction = new IdentitySet();
/* 179:194 */     int includedCount = -1;
/* 180:195 */     for (int i = 0; i < this.translators.length; i++)
/* 181:    */     {
/* 182:196 */       List tmp = this.translators[i].list(session, queryParametersToUse);
/* 183:197 */       if (needsLimit)
/* 184:    */       {
/* 185:199 */         int first = queryParameters.getRowSelection().getFirstRow() == null ? 0 : queryParameters.getRowSelection().getFirstRow().intValue();
/* 186:    */         
/* 187:    */ 
/* 188:202 */         int max = queryParameters.getRowSelection().getMaxRows() == null ? -1 : queryParameters.getRowSelection().getMaxRows().intValue();
/* 189:    */         
/* 190:    */ 
/* 191:205 */         int size = tmp.size();
/* 192:206 */         for (int x = 0; x < size; x++)
/* 193:    */         {
/* 194:207 */           Object result = tmp.get(x);
/* 195:208 */           if (distinction.add(result))
/* 196:    */           {
/* 197:211 */             includedCount++;
/* 198:212 */             if (includedCount >= first)
/* 199:    */             {
/* 200:215 */               combinedResults.add(result);
/* 201:216 */               if ((max >= 0) && (includedCount > max)) {
/* 202:    */                 break label352;
/* 203:    */               }
/* 204:    */             }
/* 205:    */           }
/* 206:    */         }
/* 207:    */       }
/* 208:    */       else
/* 209:    */       {
/* 210:223 */         combinedResults.addAll(tmp);
/* 211:    */       }
/* 212:    */     }
/* 213:    */     label352:
/* 214:226 */     return combinedResults;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Iterator performIterate(QueryParameters queryParameters, EventSource session)
/* 218:    */     throws HibernateException
/* 219:    */   {
/* 220:232 */     if (LOG.isTraceEnabled())
/* 221:    */     {
/* 222:233 */       LOG.tracev("Iterate: {0}", getSourceQuery());
/* 223:234 */       queryParameters.traceParameters(session.getFactory());
/* 224:    */     }
/* 225:236 */     if (this.translators.length == 0) {
/* 226:237 */       return EmptyIterator.INSTANCE;
/* 227:    */     }
/* 228:240 */     Iterator[] results = null;
/* 229:241 */     boolean many = this.translators.length > 1;
/* 230:242 */     if (many) {
/* 231:243 */       results = new Iterator[this.translators.length];
/* 232:    */     }
/* 233:246 */     Iterator result = null;
/* 234:247 */     for (int i = 0; i < this.translators.length; i++)
/* 235:    */     {
/* 236:248 */       result = this.translators[i].iterate(queryParameters, session);
/* 237:249 */       if (many) {
/* 238:249 */         results[i] = result;
/* 239:    */       }
/* 240:    */     }
/* 241:252 */     return many ? new JoinedIterator(results) : result;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public ScrollableResults performScroll(QueryParameters queryParameters, SessionImplementor session)
/* 245:    */     throws HibernateException
/* 246:    */   {
/* 247:258 */     if (LOG.isTraceEnabled())
/* 248:    */     {
/* 249:259 */       LOG.tracev("Iterate: {0}", getSourceQuery());
/* 250:260 */       queryParameters.traceParameters(session.getFactory());
/* 251:    */     }
/* 252:262 */     if (this.translators.length != 1) {
/* 253:263 */       throw new QueryException("implicit polymorphism not supported for scroll() queries");
/* 254:    */     }
/* 255:265 */     if ((queryParameters.getRowSelection().definesLimits()) && (this.translators[0].containsCollectionFetches())) {
/* 256:266 */       throw new QueryException("firstResult/maxResults not supported in conjunction with scroll() of a query containing collection fetches");
/* 257:    */     }
/* 258:269 */     return this.translators[0].scroll(queryParameters, session);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public int performExecuteUpdate(QueryParameters queryParameters, SessionImplementor session)
/* 262:    */     throws HibernateException
/* 263:    */   {
/* 264:274 */     if (LOG.isTraceEnabled())
/* 265:    */     {
/* 266:275 */       LOG.tracev("Execute update: {0}", getSourceQuery());
/* 267:276 */       queryParameters.traceParameters(session.getFactory());
/* 268:    */     }
/* 269:278 */     if (this.translators.length != 1) {
/* 270:279 */       LOG.splitQueries(getSourceQuery(), this.translators.length);
/* 271:    */     }
/* 272:281 */     int result = 0;
/* 273:282 */     for (int i = 0; i < this.translators.length; i++) {
/* 274:283 */       result += this.translators[i].executeUpdate(queryParameters, session);
/* 275:    */     }
/* 276:285 */     return result;
/* 277:    */   }
/* 278:    */   
/* 279:    */   private ParameterMetadata buildParameterMetadata(ParameterTranslations parameterTranslations, String hql)
/* 280:    */   {
/* 281:289 */     long start = System.currentTimeMillis();
/* 282:290 */     ParamLocationRecognizer recognizer = ParamLocationRecognizer.parseLocations(hql);
/* 283:291 */     long end = System.currentTimeMillis();
/* 284:292 */     LOG.tracev("HQL param location recognition took {0} mills ({1})", Long.valueOf(end - start), hql);
/* 285:    */     
/* 286:294 */     int ordinalParamCount = parameterTranslations.getOrdinalParameterCount();
/* 287:295 */     int[] locations = ArrayHelper.toIntArray(recognizer.getOrdinalParameterLocationList());
/* 288:296 */     if ((parameterTranslations.supportsOrdinalParameterMetadata()) && (locations.length != ordinalParamCount)) {
/* 289:297 */       throw new HibernateException("ordinal parameter mismatch");
/* 290:    */     }
/* 291:299 */     ordinalParamCount = locations.length;
/* 292:300 */     OrdinalParameterDescriptor[] ordinalParamDescriptors = new OrdinalParameterDescriptor[ordinalParamCount];
/* 293:301 */     for (int i = 1; i <= ordinalParamCount; i++) {
/* 294:302 */       ordinalParamDescriptors[(i - 1)] = new OrdinalParameterDescriptor(i, parameterTranslations.supportsOrdinalParameterMetadata() ? parameterTranslations.getOrdinalParameterExpectedType(i) : null, locations[(i - 1)]);
/* 295:    */     }
/* 296:311 */     Iterator itr = recognizer.getNamedParameterDescriptionMap().entrySet().iterator();
/* 297:312 */     Map namedParamDescriptorMap = new HashMap();
/* 298:313 */     while (itr.hasNext())
/* 299:    */     {
/* 300:314 */       Map.Entry entry = (Map.Entry)itr.next();
/* 301:315 */       String name = (String)entry.getKey();
/* 302:316 */       ParamLocationRecognizer.NamedParameterDescription description = (ParamLocationRecognizer.NamedParameterDescription)entry.getValue();
/* 303:    */       
/* 304:318 */       namedParamDescriptorMap.put(name, new NamedParameterDescriptor(name, parameterTranslations.getNamedParameterExpectedType(name), description.buildPositionsArray(), description.isJpaStyle()));
/* 305:    */     }
/* 306:329 */     return new ParameterMetadata(ordinalParamDescriptors, namedParamDescriptorMap);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public QueryTranslator[] getTranslators()
/* 310:    */   {
/* 311:333 */     QueryTranslator[] copy = new QueryTranslator[this.translators.length];
/* 312:334 */     System.arraycopy(this.translators, 0, copy, 0, copy.length);
/* 313:335 */     return copy;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Class getDynamicInstantiationResultType()
/* 317:    */   {
/* 318:339 */     return this.translators[0].getDynamicInstantiationResultType();
/* 319:    */   }
/* 320:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.HQLQueryPlan
 * JD-Core Version:    0.7.0.1
 */