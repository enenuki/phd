/*   1:    */ package org.hibernate.jmx;
/*   2:    */ 
/*   3:    */ import javax.naming.InitialContext;
/*   4:    */ import javax.naming.NameNotFoundException;
/*   5:    */ import javax.naming.NamingException;
/*   6:    */ import javax.naming.RefAddr;
/*   7:    */ import javax.naming.Reference;
/*   8:    */ import org.hibernate.SessionFactory;
/*   9:    */ import org.hibernate.internal.CoreMessageLogger;
/*  10:    */ import org.hibernate.internal.SessionFactoryRegistry;
/*  11:    */ import org.hibernate.stat.CollectionStatistics;
/*  12:    */ import org.hibernate.stat.EntityStatistics;
/*  13:    */ import org.hibernate.stat.QueryStatistics;
/*  14:    */ import org.hibernate.stat.SecondLevelCacheStatistics;
/*  15:    */ import org.hibernate.stat.Statistics;
/*  16:    */ import org.hibernate.stat.internal.ConcurrentStatisticsImpl;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ @Deprecated
/*  20:    */ public class StatisticsService
/*  21:    */   implements StatisticsServiceMBean
/*  22:    */ {
/*  23: 56 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, StatisticsService.class.getName());
/*  24:    */   SessionFactory sf;
/*  25:    */   String sfJNDIName;
/*  26: 61 */   Statistics stats = new ConcurrentStatisticsImpl();
/*  27:    */   
/*  28:    */   public void setSessionFactoryJNDIName(String sfJNDIName)
/*  29:    */   {
/*  30: 67 */     this.sfJNDIName = sfJNDIName;
/*  31:    */     try
/*  32:    */     {
/*  33: 70 */       Object jndiValue = new InitialContext().lookup(sfJNDIName);
/*  34:    */       SessionFactory sessionFactory;
/*  35:    */       SessionFactory sessionFactory;
/*  36: 71 */       if ((jndiValue instanceof Reference))
/*  37:    */       {
/*  38: 72 */         String uuid = (String)((Reference)jndiValue).get(0).getContent();
/*  39: 73 */         sessionFactory = SessionFactoryRegistry.INSTANCE.getSessionFactory(uuid);
/*  40:    */       }
/*  41:    */       else
/*  42:    */       {
/*  43: 76 */         sessionFactory = (SessionFactory)jndiValue;
/*  44:    */       }
/*  45: 78 */       setSessionFactory(sessionFactory);
/*  46:    */     }
/*  47:    */     catch (NameNotFoundException e)
/*  48:    */     {
/*  49: 81 */       LOG.noSessionFactoryWithJndiName(sfJNDIName, e);
/*  50: 82 */       setSessionFactory(null);
/*  51:    */     }
/*  52:    */     catch (NamingException e)
/*  53:    */     {
/*  54: 85 */       LOG.unableToAccessSessionFactory(sfJNDIName, e);
/*  55: 86 */       setSessionFactory(null);
/*  56:    */     }
/*  57:    */     catch (ClassCastException e)
/*  58:    */     {
/*  59: 89 */       LOG.jndiNameDoesNotHandleSessionFactoryReference(sfJNDIName, e);
/*  60: 90 */       setSessionFactory(null);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setSessionFactory(SessionFactory sf)
/*  65:    */   {
/*  66:100 */     this.sf = sf;
/*  67:101 */     if (sf == null) {
/*  68:102 */       this.stats = new ConcurrentStatisticsImpl();
/*  69:    */     } else {
/*  70:105 */       this.stats = sf.getStatistics();
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void clear()
/*  75:    */   {
/*  76:113 */     this.stats.clear();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public EntityStatistics getEntityStatistics(String entityName)
/*  80:    */   {
/*  81:119 */     return this.stats.getEntityStatistics(entityName);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public CollectionStatistics getCollectionStatistics(String role)
/*  85:    */   {
/*  86:125 */     return this.stats.getCollectionStatistics(role);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public SecondLevelCacheStatistics getSecondLevelCacheStatistics(String regionName)
/*  90:    */   {
/*  91:131 */     return this.stats.getSecondLevelCacheStatistics(regionName);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public QueryStatistics getQueryStatistics(String hql)
/*  95:    */   {
/*  96:137 */     return this.stats.getQueryStatistics(hql);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public long getEntityDeleteCount()
/* 100:    */   {
/* 101:143 */     return this.stats.getEntityDeleteCount();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public long getEntityInsertCount()
/* 105:    */   {
/* 106:149 */     return this.stats.getEntityInsertCount();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public long getEntityLoadCount()
/* 110:    */   {
/* 111:155 */     return this.stats.getEntityLoadCount();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public long getEntityFetchCount()
/* 115:    */   {
/* 116:161 */     return this.stats.getEntityFetchCount();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public long getEntityUpdateCount()
/* 120:    */   {
/* 121:167 */     return this.stats.getEntityUpdateCount();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public long getQueryExecutionCount()
/* 125:    */   {
/* 126:173 */     return this.stats.getQueryExecutionCount();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public long getQueryCacheHitCount()
/* 130:    */   {
/* 131:176 */     return this.stats.getQueryCacheHitCount();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public long getQueryExecutionMaxTime()
/* 135:    */   {
/* 136:179 */     return this.stats.getQueryExecutionMaxTime();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public long getQueryCacheMissCount()
/* 140:    */   {
/* 141:182 */     return this.stats.getQueryCacheMissCount();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public long getQueryCachePutCount()
/* 145:    */   {
/* 146:185 */     return this.stats.getQueryCachePutCount();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public long getUpdateTimestampsCacheHitCount()
/* 150:    */   {
/* 151:189 */     return this.stats.getUpdateTimestampsCacheHitCount();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public long getUpdateTimestampsCacheMissCount()
/* 155:    */   {
/* 156:193 */     return this.stats.getUpdateTimestampsCacheMissCount();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public long getUpdateTimestampsCachePutCount()
/* 160:    */   {
/* 161:197 */     return this.stats.getUpdateTimestampsCachePutCount();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public long getFlushCount()
/* 165:    */   {
/* 166:204 */     return this.stats.getFlushCount();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public long getConnectCount()
/* 170:    */   {
/* 171:210 */     return this.stats.getConnectCount();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public long getSecondLevelCacheHitCount()
/* 175:    */   {
/* 176:216 */     return this.stats.getSecondLevelCacheHitCount();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public long getSecondLevelCacheMissCount()
/* 180:    */   {
/* 181:222 */     return this.stats.getSecondLevelCacheMissCount();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public long getSecondLevelCachePutCount()
/* 185:    */   {
/* 186:228 */     return this.stats.getSecondLevelCachePutCount();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public long getSessionCloseCount()
/* 190:    */   {
/* 191:234 */     return this.stats.getSessionCloseCount();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public long getSessionOpenCount()
/* 195:    */   {
/* 196:240 */     return this.stats.getSessionOpenCount();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public long getCollectionLoadCount()
/* 200:    */   {
/* 201:246 */     return this.stats.getCollectionLoadCount();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public long getCollectionFetchCount()
/* 205:    */   {
/* 206:252 */     return this.stats.getCollectionFetchCount();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public long getCollectionUpdateCount()
/* 210:    */   {
/* 211:258 */     return this.stats.getCollectionUpdateCount();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public long getCollectionRemoveCount()
/* 215:    */   {
/* 216:264 */     return this.stats.getCollectionRemoveCount();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public long getCollectionRecreateCount()
/* 220:    */   {
/* 221:270 */     return this.stats.getCollectionRecreateCount();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public long getStartTime()
/* 225:    */   {
/* 226:276 */     return this.stats.getStartTime();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public boolean isStatisticsEnabled()
/* 230:    */   {
/* 231:283 */     return this.stats.isStatisticsEnabled();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setStatisticsEnabled(boolean enable)
/* 235:    */   {
/* 236:290 */     this.stats.setStatisticsEnabled(enable);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void logSummary()
/* 240:    */   {
/* 241:294 */     this.stats.logSummary();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String[] getCollectionRoleNames()
/* 245:    */   {
/* 246:298 */     return this.stats.getCollectionRoleNames();
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String[] getEntityNames()
/* 250:    */   {
/* 251:302 */     return this.stats.getEntityNames();
/* 252:    */   }
/* 253:    */   
/* 254:    */   public String[] getQueries()
/* 255:    */   {
/* 256:306 */     return this.stats.getQueries();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String[] getSecondLevelCacheRegionNames()
/* 260:    */   {
/* 261:310 */     return this.stats.getSecondLevelCacheRegionNames();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public long getSuccessfulTransactionCount()
/* 265:    */   {
/* 266:314 */     return this.stats.getSuccessfulTransactionCount();
/* 267:    */   }
/* 268:    */   
/* 269:    */   public long getTransactionCount()
/* 270:    */   {
/* 271:317 */     return this.stats.getTransactionCount();
/* 272:    */   }
/* 273:    */   
/* 274:    */   public long getCloseStatementCount()
/* 275:    */   {
/* 276:321 */     return this.stats.getCloseStatementCount();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public long getPrepareStatementCount()
/* 280:    */   {
/* 281:324 */     return this.stats.getPrepareStatementCount();
/* 282:    */   }
/* 283:    */   
/* 284:    */   public long getOptimisticFailureCount()
/* 285:    */   {
/* 286:328 */     return this.stats.getOptimisticFailureCount();
/* 287:    */   }
/* 288:    */   
/* 289:    */   public String getQueryExecutionMaxTimeQueryString()
/* 290:    */   {
/* 291:332 */     return this.stats.getQueryExecutionMaxTimeQueryString();
/* 292:    */   }
/* 293:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jmx.StatisticsService
 * JD-Core Version:    0.7.0.1
 */