/*   1:    */ package org.hibernate.cache.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Properties;
/*   7:    */ import java.util.Set;
/*   8:    */ import javax.persistence.EntityNotFoundException;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.UnresolvableObjectException;
/*  11:    */ import org.hibernate.cache.CacheException;
/*  12:    */ import org.hibernate.cache.spi.QueryCache;
/*  13:    */ import org.hibernate.cache.spi.QueryKey;
/*  14:    */ import org.hibernate.cache.spi.QueryResultsRegion;
/*  15:    */ import org.hibernate.cache.spi.RegionFactory;
/*  16:    */ import org.hibernate.cache.spi.UpdateTimestampsCache;
/*  17:    */ import org.hibernate.cfg.Settings;
/*  18:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  19:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  20:    */ import org.hibernate.internal.CoreMessageLogger;
/*  21:    */ import org.hibernate.type.Type;
/*  22:    */ import org.hibernate.type.TypeHelper;
/*  23:    */ import org.jboss.logging.Logger;
/*  24:    */ 
/*  25:    */ public class StandardQueryCache
/*  26:    */   implements QueryCache
/*  27:    */ {
/*  28: 59 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, StandardQueryCache.class.getName());
/*  29:    */   private QueryResultsRegion cacheRegion;
/*  30:    */   private UpdateTimestampsCache updateTimestampsCache;
/*  31:    */   
/*  32:    */   public void clear()
/*  33:    */     throws CacheException
/*  34:    */   {
/*  35: 68 */     this.cacheRegion.evictAll();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public StandardQueryCache(Settings settings, Properties props, UpdateTimestampsCache updateTimestampsCache, String regionName)
/*  39:    */     throws HibernateException
/*  40:    */   {
/*  41: 76 */     if (regionName == null) {
/*  42: 77 */       regionName = StandardQueryCache.class.getName();
/*  43:    */     }
/*  44: 79 */     String prefix = settings.getCacheRegionPrefix();
/*  45: 80 */     if (prefix != null) {
/*  46: 81 */       regionName = prefix + '.' + regionName;
/*  47:    */     }
/*  48: 83 */     LOG.startingQueryCache(regionName);
/*  49:    */     
/*  50: 85 */     this.cacheRegion = settings.getRegionFactory().buildQueryResultsRegion(regionName, props);
/*  51: 86 */     this.updateTimestampsCache = updateTimestampsCache;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean put(QueryKey key, Type[] returnTypes, List result, boolean isNaturalKeyLookup, SessionImplementor session)
/*  55:    */     throws HibernateException
/*  56:    */   {
/*  57: 96 */     if ((isNaturalKeyLookup) && (result.size() == 0)) {
/*  58: 97 */       return false;
/*  59:    */     }
/*  60: 99 */     Long ts = Long.valueOf(session.getFactory().getSettings().getRegionFactory().nextTimestamp());
/*  61:    */     
/*  62:101 */     LOG.debugf("Caching query results in region: %s; timestamp=%s", this.cacheRegion.getName(), ts);
/*  63:    */     
/*  64:103 */     List cacheable = new ArrayList(result.size() + 1);
/*  65:104 */     logCachedResultDetails(key, null, returnTypes, cacheable);
/*  66:105 */     cacheable.add(ts);
/*  67:106 */     for (Object aResult : result)
/*  68:    */     {
/*  69:107 */       if (returnTypes.length == 1) {
/*  70:108 */         cacheable.add(returnTypes[0].disassemble(aResult, session, null));
/*  71:    */       } else {
/*  72:111 */         cacheable.add(TypeHelper.disassemble((Object[])aResult, returnTypes, null, session, null));
/*  73:    */       }
/*  74:113 */       logCachedResultRowDetails(returnTypes, aResult);
/*  75:    */     }
/*  76:116 */     this.cacheRegion.put(key, cacheable);
/*  77:117 */     return true;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List get(QueryKey key, Type[] returnTypes, boolean isNaturalKeyLookup, Set spaces, SessionImplementor session)
/*  81:    */     throws HibernateException
/*  82:    */   {
/*  83:127 */     LOG.debugf("Checking cached query results in region: %s", this.cacheRegion.getName());
/*  84:    */     
/*  85:129 */     List cacheable = (List)this.cacheRegion.get(key);
/*  86:130 */     logCachedResultDetails(key, spaces, returnTypes, cacheable);
/*  87:132 */     if (cacheable == null)
/*  88:    */     {
/*  89:133 */       LOG.debug("Query results were not found in cache");
/*  90:134 */       return null;
/*  91:    */     }
/*  92:137 */     Long timestamp = (Long)cacheable.get(0);
/*  93:138 */     if ((!isNaturalKeyLookup) && (!isUpToDate(spaces, timestamp)))
/*  94:    */     {
/*  95:139 */       LOG.debug("Cached query results were not up-to-date");
/*  96:140 */       return null;
/*  97:    */     }
/*  98:143 */     LOG.debug("Returning cached query results");
/*  99:144 */     for (int i = 1; i < cacheable.size(); i++) {
/* 100:145 */       if (returnTypes.length == 1) {
/* 101:146 */         returnTypes[0].beforeAssemble((Serializable)cacheable.get(i), session);
/* 102:    */       } else {
/* 103:149 */         TypeHelper.beforeAssemble((Serializable[])cacheable.get(i), returnTypes, session);
/* 104:    */       }
/* 105:    */     }
/* 106:152 */     List result = new ArrayList(cacheable.size() - 1);
/* 107:153 */     for (int i = 1; i < cacheable.size(); i++) {
/* 108:    */       try
/* 109:    */       {
/* 110:155 */         if (returnTypes.length == 1) {
/* 111:156 */           result.add(returnTypes[0].assemble((Serializable)cacheable.get(i), session, null));
/* 112:    */         } else {
/* 113:159 */           result.add(TypeHelper.assemble((Serializable[])cacheable.get(i), returnTypes, session, null));
/* 114:    */         }
/* 115:163 */         logCachedResultRowDetails(returnTypes, result.get(i - 1));
/* 116:    */       }
/* 117:    */       catch (RuntimeException ex)
/* 118:    */       {
/* 119:166 */         if ((isNaturalKeyLookup) && ((UnresolvableObjectException.class.isInstance(ex)) || (EntityNotFoundException.class.isInstance(ex))))
/* 120:    */         {
/* 121:173 */           LOG.debug("Unable to reassemble cached result set");
/* 122:174 */           this.cacheRegion.evict(key);
/* 123:175 */           return null;
/* 124:    */         }
/* 125:177 */         throw ex;
/* 126:    */       }
/* 127:    */     }
/* 128:180 */     return result;
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected boolean isUpToDate(Set spaces, Long timestamp)
/* 132:    */   {
/* 133:184 */     LOG.debugf("Checking query spaces are up-to-date: %s", spaces);
/* 134:185 */     return this.updateTimestampsCache.isUpToDate(spaces, timestamp);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void destroy()
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:190 */       this.cacheRegion.destroy();
/* 142:    */     }
/* 143:    */     catch (Exception e)
/* 144:    */     {
/* 145:193 */       LOG.unableToDestroyQueryCache(this.cacheRegion.getName(), e.getMessage());
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public QueryResultsRegion getRegion()
/* 150:    */   {
/* 151:198 */     return this.cacheRegion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String toString()
/* 155:    */   {
/* 156:203 */     return "StandardQueryCache(" + this.cacheRegion.getName() + ')';
/* 157:    */   }
/* 158:    */   
/* 159:    */   private static void logCachedResultDetails(QueryKey key, Set querySpaces, Type[] returnTypes, List result)
/* 160:    */   {
/* 161:207 */     if (!LOG.isTraceEnabled()) {
/* 162:208 */       return;
/* 163:    */     }
/* 164:210 */     LOG.trace("key.hashCode=" + key.hashCode());
/* 165:211 */     LOG.trace("querySpaces=" + querySpaces);
/* 166:212 */     if ((returnTypes == null) || (returnTypes.length == 0))
/* 167:    */     {
/* 168:213 */       LOG.trace("Unexpected returnTypes is " + (returnTypes == null ? "null" : "empty") + "! result" + (result == null ? " is null" : new StringBuilder().append(".size()=").append(result.size()).toString()));
/* 169:    */     }
/* 170:    */     else
/* 171:    */     {
/* 172:220 */       StringBuilder returnTypeInfo = new StringBuilder();
/* 173:221 */       for (int i = 0; i < returnTypes.length; i++) {
/* 174:222 */         returnTypeInfo.append("typename=").append(returnTypes[i].getName()).append(" class=").append(returnTypes[i].getReturnedClass().getName()).append(' ');
/* 175:    */       }
/* 176:227 */       LOG.trace("unexpected returnTypes is " + returnTypeInfo.toString() + "! result");
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   private static void logCachedResultRowDetails(Type[] returnTypes, Object result)
/* 181:    */   {
/* 182:232 */     if (!LOG.isTraceEnabled()) {
/* 183:233 */       return;
/* 184:    */     }
/* 185:235 */     logCachedResultRowDetails(returnTypes, new Object[] { (result instanceof Object[]) ? (Object[])result : result });
/* 186:    */   }
/* 187:    */   
/* 188:    */   private static void logCachedResultRowDetails(Type[] returnTypes, Object[] tuple)
/* 189:    */   {
/* 190:242 */     if (!LOG.isTraceEnabled()) {
/* 191:243 */       return;
/* 192:    */     }
/* 193:245 */     if (tuple == null)
/* 194:    */     {
/* 195:246 */       LOG.trace("Type[" + returnTypes.length + "]");
/* 196:247 */       if ((returnTypes != null) && (returnTypes.length > 1)) {
/* 197:248 */         LOG.trace("Unexpected result tuple! tuple is null; should be Object[" + returnTypes.length + "]!");
/* 198:    */       }
/* 199:    */     }
/* 200:    */     else
/* 201:    */     {
/* 202:255 */       if ((returnTypes == null) || (returnTypes.length == 0)) {
/* 203:256 */         LOG.trace("Unexpected result tuple! tuple is null; returnTypes is " + (returnTypes == null ? "null" : "empty"));
/* 204:    */       }
/* 205:261 */       LOG.trace(" tuple is Object[" + tuple.length + "]; returnTypes is Type[" + returnTypes.length + "]");
/* 206:262 */       if (tuple.length != returnTypes.length) {
/* 207:263 */         LOG.trace("Unexpected tuple length! transformer= expected=" + returnTypes.length + " got=" + tuple.length);
/* 208:    */       } else {
/* 209:269 */         for (int j = 0; j < tuple.length; j++) {
/* 210:270 */           if ((tuple[j] != null) && (!returnTypes[j].getReturnedClass().isInstance(tuple[j]))) {
/* 211:271 */             LOG.trace("Unexpected tuple value type! transformer= expected=" + returnTypes[j].getReturnedClass().getName() + " got=" + tuple[j].getClass().getName());
/* 212:    */           }
/* 213:    */         }
/* 214:    */       }
/* 215:    */     }
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.internal.StandardQueryCache
 * JD-Core Version:    0.7.0.1
 */