/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import javax.persistence.QueryHint;
/*   5:    */ import javax.persistence.SqlResultSetMapping;
/*   6:    */ import javax.persistence.SqlResultSetMappings;
/*   7:    */ import org.hibernate.AnnotationException;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.CacheMode;
/*  10:    */ import org.hibernate.FlushMode;
/*  11:    */ import org.hibernate.LockMode;
/*  12:    */ import org.hibernate.annotations.CacheModeType;
/*  13:    */ import org.hibernate.annotations.FlushModeType;
/*  14:    */ import org.hibernate.cfg.BinderHelper;
/*  15:    */ import org.hibernate.cfg.Mappings;
/*  16:    */ import org.hibernate.cfg.NotYetImplementedException;
/*  17:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
/*  18:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryRootReturn;
/*  19:    */ import org.hibernate.engine.spi.NamedQueryDefinition;
/*  20:    */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*  21:    */ import org.hibernate.internal.CoreMessageLogger;
/*  22:    */ import org.jboss.logging.Logger;
/*  23:    */ 
/*  24:    */ public abstract class QueryBinder
/*  25:    */ {
/*  26: 58 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, QueryBinder.class.getName());
/*  27:    */   
/*  28:    */   public static void bindQuery(javax.persistence.NamedQuery queryAnn, Mappings mappings, boolean isDefault)
/*  29:    */   {
/*  30: 61 */     if (queryAnn == null) {
/*  31: 61 */       return;
/*  32:    */     }
/*  33: 62 */     if (BinderHelper.isEmptyAnnotationValue(queryAnn.name())) {
/*  34: 63 */       throw new AnnotationException("A named query must have a name when used in class or package level");
/*  35:    */     }
/*  36: 66 */     QueryHint[] hints = queryAnn.hints();
/*  37: 67 */     String queryName = queryAnn.query();
/*  38: 68 */     NamedQueryDefinition query = new NamedQueryDefinition(queryAnn.name(), queryName, getBoolean(queryName, "org.hibernate.cacheable", hints), getString(queryName, "org.hibernate.cacheRegion", hints), getTimeout(queryName, hints), getInteger(queryName, "org.hibernate.fetchSize", hints), getFlushMode(queryName, hints), getCacheMode(queryName, hints), getBoolean(queryName, "org.hibernate.readOnly", hints), getString(queryName, "org.hibernate.comment", hints), null);
/*  39: 81 */     if (isDefault) {
/*  40: 82 */       mappings.addDefaultQuery(query.getName(), query);
/*  41:    */     } else {
/*  42: 85 */       mappings.addQuery(query.getName(), query);
/*  43:    */     }
/*  44: 87 */     if (LOG.isDebugEnabled()) {
/*  45: 88 */       LOG.debugf("Binding named query: %s => %s", query.getName(), query.getQueryString());
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static void bindNativeQuery(javax.persistence.NamedNativeQuery queryAnn, Mappings mappings, boolean isDefault)
/*  50:    */   {
/*  51: 94 */     if (queryAnn == null) {
/*  52: 94 */       return;
/*  53:    */     }
/*  54: 96 */     if (BinderHelper.isEmptyAnnotationValue(queryAnn.name())) {
/*  55: 97 */       throw new AnnotationException("A named query must have a name when used in class or package level");
/*  56:    */     }
/*  57:100 */     String resultSetMapping = queryAnn.resultSetMapping();
/*  58:101 */     QueryHint[] hints = queryAnn.hints();
/*  59:102 */     String queryName = queryAnn.query();
/*  60:    */     NamedSQLQueryDefinition query;
/*  61:103 */     if (!BinderHelper.isEmptyAnnotationValue(resultSetMapping))
/*  62:    */     {
/*  63:105 */       query = new NamedSQLQueryDefinition(queryAnn.name(), queryName, resultSetMapping, null, getBoolean(queryName, "org.hibernate.cacheable", hints), getString(queryName, "org.hibernate.cacheRegion", hints), getTimeout(queryName, hints), getInteger(queryName, "org.hibernate.fetchSize", hints), getFlushMode(queryName, hints), getCacheMode(queryName, hints), getBoolean(queryName, "org.hibernate.readOnly", hints), getString(queryName, "org.hibernate.comment", hints), null, getBoolean(queryName, "org.hibernate.callable", hints));
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67:    */       NamedSQLQueryDefinition query;
/*  68:122 */       if (!Void.TYPE.equals(queryAnn.resultClass()))
/*  69:    */       {
/*  70:125 */         NativeSQLQueryRootReturn entityQueryReturn = new NativeSQLQueryRootReturn("alias1", queryAnn.resultClass().getName(), new HashMap(), LockMode.READ);
/*  71:    */         
/*  72:127 */         query = new NamedSQLQueryDefinition(queryAnn.name(), queryName, new NativeSQLQueryReturn[] { entityQueryReturn }, null, getBoolean(queryName, "org.hibernate.cacheable", hints), getString(queryName, "org.hibernate.cacheRegion", hints), getTimeout(queryName, hints), getInteger(queryName, "org.hibernate.fetchSize", hints), getFlushMode(queryName, hints), getCacheMode(queryName, hints), getBoolean(queryName, "org.hibernate.readOnly", hints), getString(queryName, "org.hibernate.comment", hints), null, getBoolean(queryName, "org.hibernate.callable", hints));
/*  73:    */       }
/*  74:    */       else
/*  75:    */       {
/*  76:145 */         throw new NotYetImplementedException("Pure native scalar queries are not yet supported");
/*  77:    */       }
/*  78:    */     }
/*  79:    */     NamedSQLQueryDefinition query;
/*  80:147 */     if (isDefault) {
/*  81:148 */       mappings.addDefaultSQLQuery(query.getName(), query);
/*  82:    */     } else {
/*  83:151 */       mappings.addSQLQuery(query.getName(), query);
/*  84:    */     }
/*  85:153 */     if (LOG.isDebugEnabled()) {
/*  86:154 */       LOG.debugf("Binding named native query: %s => %s", queryAnn.name(), queryAnn.query());
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void bindNativeQuery(org.hibernate.annotations.NamedNativeQuery queryAnn, Mappings mappings)
/*  91:    */   {
/*  92:159 */     if (queryAnn == null) {
/*  93:159 */       return;
/*  94:    */     }
/*  95:161 */     if (BinderHelper.isEmptyAnnotationValue(queryAnn.name())) {
/*  96:162 */       throw new AnnotationException("A named query must have a name when used in class or package level");
/*  97:    */     }
/*  98:165 */     String resultSetMapping = queryAnn.resultSetMapping();
/*  99:    */     NamedSQLQueryDefinition query;
/* 100:166 */     if (!BinderHelper.isEmptyAnnotationValue(resultSetMapping))
/* 101:    */     {
/* 102:168 */       query = new NamedSQLQueryDefinition(queryAnn.name(), queryAnn.query(), resultSetMapping, null, queryAnn.cacheable(), BinderHelper.isEmptyAnnotationValue(queryAnn.cacheRegion()) ? null : queryAnn.cacheRegion(), queryAnn.timeout() < 0 ? null : Integer.valueOf(queryAnn.timeout()), queryAnn.fetchSize() < 0 ? null : Integer.valueOf(queryAnn.fetchSize()), getFlushMode(queryAnn.flushMode()), getCacheMode(queryAnn.cacheMode()), queryAnn.readOnly(), BinderHelper.isEmptyAnnotationValue(queryAnn.comment()) ? null : queryAnn.comment(), null, queryAnn.callable());
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:    */       NamedSQLQueryDefinition query;
/* 107:185 */       if (!Void.TYPE.equals(queryAnn.resultClass()))
/* 108:    */       {
/* 109:188 */         NativeSQLQueryRootReturn entityQueryReturn = new NativeSQLQueryRootReturn("alias1", queryAnn.resultClass().getName(), new HashMap(), LockMode.READ);
/* 110:    */         
/* 111:190 */         query = new NamedSQLQueryDefinition(queryAnn.name(), queryAnn.query(), new NativeSQLQueryReturn[] { entityQueryReturn }, null, queryAnn.cacheable(), BinderHelper.isEmptyAnnotationValue(queryAnn.cacheRegion()) ? null : queryAnn.cacheRegion(), queryAnn.timeout() < 0 ? null : Integer.valueOf(queryAnn.timeout()), queryAnn.fetchSize() < 0 ? null : Integer.valueOf(queryAnn.fetchSize()), getFlushMode(queryAnn.flushMode()), getCacheMode(queryAnn.cacheMode()), queryAnn.readOnly(), BinderHelper.isEmptyAnnotationValue(queryAnn.comment()) ? null : queryAnn.comment(), null, queryAnn.callable());
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:208 */         throw new NotYetImplementedException("Pure native scalar queries are not yet supported");
/* 116:    */       }
/* 117:    */     }
/* 118:    */     NamedSQLQueryDefinition query;
/* 119:210 */     mappings.addSQLQuery(query.getName(), query);
/* 120:211 */     if (LOG.isDebugEnabled()) {
/* 121:212 */       LOG.debugf("Binding named native query: %s => %s", query.getName(), queryAnn.query());
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static void bindQueries(javax.persistence.NamedQueries queriesAnn, Mappings mappings, boolean isDefault)
/* 126:    */   {
/* 127:217 */     if (queriesAnn == null) {
/* 128:217 */       return;
/* 129:    */     }
/* 130:218 */     for (javax.persistence.NamedQuery q : queriesAnn.value()) {
/* 131:219 */       bindQuery(q, mappings, isDefault);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static void bindNativeQueries(javax.persistence.NamedNativeQueries queriesAnn, Mappings mappings, boolean isDefault)
/* 136:    */   {
/* 137:224 */     if (queriesAnn == null) {
/* 138:224 */       return;
/* 139:    */     }
/* 140:225 */     for (javax.persistence.NamedNativeQuery q : queriesAnn.value()) {
/* 141:226 */       bindNativeQuery(q, mappings, isDefault);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static void bindNativeQueries(org.hibernate.annotations.NamedNativeQueries queriesAnn, Mappings mappings)
/* 146:    */   {
/* 147:233 */     if (queriesAnn == null) {
/* 148:233 */       return;
/* 149:    */     }
/* 150:234 */     for (org.hibernate.annotations.NamedNativeQuery q : queriesAnn.value()) {
/* 151:235 */       bindNativeQuery(q, mappings);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static void bindQuery(org.hibernate.annotations.NamedQuery queryAnn, Mappings mappings)
/* 156:    */   {
/* 157:240 */     if (queryAnn == null) {
/* 158:240 */       return;
/* 159:    */     }
/* 160:241 */     if (BinderHelper.isEmptyAnnotationValue(queryAnn.name())) {
/* 161:242 */       throw new AnnotationException("A named query must have a name when used in class or package level");
/* 162:    */     }
/* 163:245 */     FlushMode flushMode = getFlushMode(queryAnn.flushMode());
/* 164:    */     
/* 165:247 */     NamedQueryDefinition query = new NamedQueryDefinition(queryAnn.name(), queryAnn.query(), queryAnn.cacheable(), BinderHelper.isEmptyAnnotationValue(queryAnn.cacheRegion()) ? null : queryAnn.cacheRegion(), queryAnn.timeout() < 0 ? null : Integer.valueOf(queryAnn.timeout()), queryAnn.fetchSize() < 0 ? null : Integer.valueOf(queryAnn.fetchSize()), flushMode, getCacheMode(queryAnn.cacheMode()), queryAnn.readOnly(), BinderHelper.isEmptyAnnotationValue(queryAnn.comment()) ? null : queryAnn.comment(), null);
/* 166:    */     
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:261 */     mappings.addQuery(query.getName(), query);
/* 180:262 */     if (LOG.isDebugEnabled()) {
/* 181:263 */       LOG.debugf("Binding named query: %s => %s", query.getName(), query.getQueryString());
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   private static FlushMode getFlushMode(FlushModeType flushModeType)
/* 186:    */   {
/* 187:    */     FlushMode flushMode;
/* 188:269 */     switch (1.$SwitchMap$org$hibernate$annotations$FlushModeType[flushModeType.ordinal()])
/* 189:    */     {
/* 190:    */     case 1: 
/* 191:271 */       flushMode = FlushMode.ALWAYS;
/* 192:272 */       break;
/* 193:    */     case 2: 
/* 194:274 */       flushMode = FlushMode.AUTO;
/* 195:275 */       break;
/* 196:    */     case 3: 
/* 197:277 */       flushMode = FlushMode.COMMIT;
/* 198:278 */       break;
/* 199:    */     case 4: 
/* 200:280 */       flushMode = FlushMode.MANUAL;
/* 201:281 */       break;
/* 202:    */     case 5: 
/* 203:283 */       flushMode = FlushMode.MANUAL;
/* 204:284 */       break;
/* 205:    */     case 6: 
/* 206:286 */       flushMode = null;
/* 207:287 */       break;
/* 208:    */     default: 
/* 209:289 */       throw new AssertionFailure("Unknown flushModeType: " + flushModeType);
/* 210:    */     }
/* 211:291 */     return flushMode;
/* 212:    */   }
/* 213:    */   
/* 214:    */   private static CacheMode getCacheMode(CacheModeType cacheModeType)
/* 215:    */   {
/* 216:295 */     switch (1.$SwitchMap$org$hibernate$annotations$CacheModeType[cacheModeType.ordinal()])
/* 217:    */     {
/* 218:    */     case 1: 
/* 219:297 */       return CacheMode.GET;
/* 220:    */     case 2: 
/* 221:299 */       return CacheMode.IGNORE;
/* 222:    */     case 3: 
/* 223:301 */       return CacheMode.NORMAL;
/* 224:    */     case 4: 
/* 225:303 */       return CacheMode.PUT;
/* 226:    */     case 5: 
/* 227:305 */       return CacheMode.REFRESH;
/* 228:    */     }
/* 229:307 */     throw new AssertionFailure("Unknown cacheModeType: " + cacheModeType);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static void bindQueries(org.hibernate.annotations.NamedQueries queriesAnn, Mappings mappings)
/* 233:    */   {
/* 234:313 */     if (queriesAnn == null) {
/* 235:313 */       return;
/* 236:    */     }
/* 237:314 */     for (org.hibernate.annotations.NamedQuery q : queriesAnn.value()) {
/* 238:315 */       bindQuery(q, mappings);
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static void bindSqlResultsetMappings(SqlResultSetMappings ann, Mappings mappings, boolean isDefault)
/* 243:    */   {
/* 244:320 */     if (ann == null) {
/* 245:320 */       return;
/* 246:    */     }
/* 247:321 */     for (SqlResultSetMapping rs : ann.value()) {
/* 248:323 */       mappings.addSecondPass(new ResultsetMappingSecondPass(rs, mappings, true));
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public static void bindSqlResultsetMapping(SqlResultSetMapping ann, Mappings mappings, boolean isDefault)
/* 253:    */   {
/* 254:329 */     mappings.addSecondPass(new ResultsetMappingSecondPass(ann, mappings, isDefault));
/* 255:    */   }
/* 256:    */   
/* 257:    */   private static CacheMode getCacheMode(String query, QueryHint[] hints)
/* 258:    */   {
/* 259:333 */     for (QueryHint hint : hints) {
/* 260:334 */       if ("org.hibernate.cacheMode".equals(hint.name()))
/* 261:    */       {
/* 262:335 */         if (hint.value().equalsIgnoreCase(CacheMode.GET.toString())) {
/* 263:336 */           return CacheMode.GET;
/* 264:    */         }
/* 265:338 */         if (hint.value().equalsIgnoreCase(CacheMode.IGNORE.toString())) {
/* 266:339 */           return CacheMode.IGNORE;
/* 267:    */         }
/* 268:341 */         if (hint.value().equalsIgnoreCase(CacheMode.NORMAL.toString())) {
/* 269:342 */           return CacheMode.NORMAL;
/* 270:    */         }
/* 271:344 */         if (hint.value().equalsIgnoreCase(CacheMode.PUT.toString())) {
/* 272:345 */           return CacheMode.PUT;
/* 273:    */         }
/* 274:347 */         if (hint.value().equalsIgnoreCase(CacheMode.REFRESH.toString())) {
/* 275:348 */           return CacheMode.REFRESH;
/* 276:    */         }
/* 277:351 */         throw new AnnotationException("Unknown CacheMode in hint: " + query + ":" + hint.name());
/* 278:    */       }
/* 279:    */     }
/* 280:355 */     return null;
/* 281:    */   }
/* 282:    */   
/* 283:    */   private static FlushMode getFlushMode(String query, QueryHint[] hints)
/* 284:    */   {
/* 285:359 */     for (QueryHint hint : hints) {
/* 286:360 */       if ("org.hibernate.flushMode".equals(hint.name()))
/* 287:    */       {
/* 288:361 */         if (hint.value().equalsIgnoreCase(FlushMode.ALWAYS.toString())) {
/* 289:362 */           return FlushMode.ALWAYS;
/* 290:    */         }
/* 291:364 */         if (hint.value().equalsIgnoreCase(FlushMode.AUTO.toString())) {
/* 292:365 */           return FlushMode.AUTO;
/* 293:    */         }
/* 294:367 */         if (hint.value().equalsIgnoreCase(FlushMode.COMMIT.toString())) {
/* 295:368 */           return FlushMode.COMMIT;
/* 296:    */         }
/* 297:370 */         if (hint.value().equalsIgnoreCase(FlushMode.NEVER.toString())) {
/* 298:371 */           return FlushMode.MANUAL;
/* 299:    */         }
/* 300:373 */         if (hint.value().equalsIgnoreCase(FlushMode.MANUAL.toString())) {
/* 301:374 */           return FlushMode.MANUAL;
/* 302:    */         }
/* 303:377 */         throw new AnnotationException("Unknown FlushMode in hint: " + query + ":" + hint.name());
/* 304:    */       }
/* 305:    */     }
/* 306:381 */     return null;
/* 307:    */   }
/* 308:    */   
/* 309:    */   private static boolean getBoolean(String query, String hintName, QueryHint[] hints)
/* 310:    */   {
/* 311:385 */     for (QueryHint hint : hints) {
/* 312:386 */       if (hintName.equals(hint.name()))
/* 313:    */       {
/* 314:387 */         if (hint.value().equalsIgnoreCase("true")) {
/* 315:388 */           return true;
/* 316:    */         }
/* 317:390 */         if (hint.value().equalsIgnoreCase("false")) {
/* 318:391 */           return false;
/* 319:    */         }
/* 320:394 */         throw new AnnotationException("Not a boolean in hint: " + query + ":" + hint.name());
/* 321:    */       }
/* 322:    */     }
/* 323:398 */     return false;
/* 324:    */   }
/* 325:    */   
/* 326:    */   private static String getString(String query, String hintName, QueryHint[] hints)
/* 327:    */   {
/* 328:402 */     for (QueryHint hint : hints) {
/* 329:403 */       if (hintName.equals(hint.name())) {
/* 330:404 */         return hint.value();
/* 331:    */       }
/* 332:    */     }
/* 333:407 */     return null;
/* 334:    */   }
/* 335:    */   
/* 336:    */   private static Integer getInteger(String query, String hintName, QueryHint[] hints)
/* 337:    */   {
/* 338:411 */     for (QueryHint hint : hints) {
/* 339:412 */       if (hintName.equals(hint.name())) {
/* 340:    */         try
/* 341:    */         {
/* 342:414 */           return Integer.decode(hint.value());
/* 343:    */         }
/* 344:    */         catch (NumberFormatException nfe)
/* 345:    */         {
/* 346:417 */           throw new AnnotationException("Not an integer in hint: " + query + ":" + hint.name(), nfe);
/* 347:    */         }
/* 348:    */       }
/* 349:    */     }
/* 350:421 */     return null;
/* 351:    */   }
/* 352:    */   
/* 353:    */   private static Integer getTimeout(String queryName, QueryHint[] hints)
/* 354:    */   {
/* 355:425 */     Integer timeout = getInteger(queryName, "javax.persistence.query.timeout", hints);
/* 356:427 */     if (timeout != null) {
/* 357:429 */       timeout = new Integer((int)Math.round(timeout.doubleValue() / 1000.0D));
/* 358:    */     } else {
/* 359:433 */       timeout = getInteger(queryName, "org.hibernate.timeout", hints);
/* 360:    */     }
/* 361:435 */     return timeout;
/* 362:    */   }
/* 363:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.QueryBinder
 * JD-Core Version:    0.7.0.1
 */