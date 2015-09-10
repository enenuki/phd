/*   1:    */ package org.hibernate.metamodel.source.annotations.global;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.AnnotationException;
/*   6:    */ import org.hibernate.CacheMode;
/*   7:    */ import org.hibernate.FlushMode;
/*   8:    */ import org.hibernate.LockMode;
/*   9:    */ import org.hibernate.cfg.NotYetImplementedException;
/*  10:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryRootReturn;
/*  11:    */ import org.hibernate.engine.spi.NamedQueryDefinition;
/*  12:    */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.internal.util.StringHelper;
/*  15:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  16:    */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*  17:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  18:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  19:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  20:    */ import org.jboss.jandex.AnnotationInstance;
/*  21:    */ import org.jboss.jandex.AnnotationValue;
/*  22:    */ import org.jboss.jandex.Index;
/*  23:    */ import org.jboss.logging.Logger;
/*  24:    */ 
/*  25:    */ public class QueryBinder
/*  26:    */ {
/*  27: 63 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, QueryBinder.class.getName());
/*  28:    */   
/*  29:    */   public static void bind(AnnotationBindingContext bindingContext)
/*  30:    */   {
/*  31: 80 */     List<AnnotationInstance> annotations = bindingContext.getIndex().getAnnotations(JPADotNames.NAMED_QUERY);
/*  32: 81 */     for (AnnotationInstance query : annotations) {
/*  33: 82 */       bindNamedQuery(bindingContext.getMetadataImplementor(), query);
/*  34:    */     }
/*  35: 85 */     annotations = bindingContext.getIndex().getAnnotations(JPADotNames.NAMED_QUERIES);
/*  36: 86 */     for (AnnotationInstance queries : annotations) {
/*  37: 87 */       for (AnnotationInstance query : (AnnotationInstance[])JandexHelper.getValue(queries, "value", [Lorg.jboss.jandex.AnnotationInstance.class)) {
/*  38: 88 */         bindNamedQuery(bindingContext.getMetadataImplementor(), query);
/*  39:    */       }
/*  40:    */     }
/*  41: 92 */     annotations = bindingContext.getIndex().getAnnotations(JPADotNames.NAMED_NATIVE_QUERY);
/*  42: 93 */     for (AnnotationInstance query : annotations) {
/*  43: 94 */       bindNamedNativeQuery(bindingContext.getMetadataImplementor(), query);
/*  44:    */     }
/*  45: 97 */     annotations = bindingContext.getIndex().getAnnotations(JPADotNames.NAMED_NATIVE_QUERIES);
/*  46: 98 */     for (AnnotationInstance queries : annotations) {
/*  47: 99 */       for (AnnotationInstance query : (AnnotationInstance[])JandexHelper.getValue(queries, "value", [Lorg.jboss.jandex.AnnotationInstance.class)) {
/*  48:100 */         bindNamedNativeQuery(bindingContext.getMetadataImplementor(), query);
/*  49:    */       }
/*  50:    */     }
/*  51:104 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.NAMED_QUERY);
/*  52:105 */     for (AnnotationInstance query : annotations) {
/*  53:106 */       bindNamedQuery(bindingContext.getMetadataImplementor(), query);
/*  54:    */     }
/*  55:109 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.NAMED_QUERIES);
/*  56:110 */     for (AnnotationInstance queries : annotations) {
/*  57:111 */       for (AnnotationInstance query : (AnnotationInstance[])JandexHelper.getValue(queries, "value", [Lorg.jboss.jandex.AnnotationInstance.class)) {
/*  58:112 */         bindNamedQuery(bindingContext.getMetadataImplementor(), query);
/*  59:    */       }
/*  60:    */     }
/*  61:116 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.NAMED_NATIVE_QUERY);
/*  62:117 */     for (AnnotationInstance query : annotations) {
/*  63:118 */       bindNamedNativeQuery(bindingContext.getMetadataImplementor(), query);
/*  64:    */     }
/*  65:121 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.NAMED_NATIVE_QUERIES);
/*  66:122 */     for (AnnotationInstance queries : annotations) {
/*  67:123 */       for (AnnotationInstance query : (AnnotationInstance[])JandexHelper.getValue(queries, "value", [Lorg.jboss.jandex.AnnotationInstance.class)) {
/*  68:124 */         bindNamedNativeQuery(bindingContext.getMetadataImplementor(), query);
/*  69:    */       }
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static void bindNamedQuery(MetadataImplementor metadata, AnnotationInstance annotation)
/*  74:    */   {
/*  75:136 */     String name = (String)JandexHelper.getValue(annotation, "name", String.class);
/*  76:137 */     if (StringHelper.isEmpty(name)) {
/*  77:138 */       throw new AnnotationException("A named query must have a name when used in class or package level");
/*  78:    */     }
/*  79:141 */     String query = (String)JandexHelper.getValue(annotation, "query", String.class);
/*  80:    */     
/*  81:143 */     AnnotationInstance[] hints = (AnnotationInstance[])JandexHelper.getValue(annotation, "hints", [Lorg.jboss.jandex.AnnotationInstance.class);
/*  82:    */     
/*  83:145 */     String cacheRegion = getString(hints, "org.hibernate.cacheRegion");
/*  84:146 */     if (StringHelper.isEmpty(cacheRegion)) {
/*  85:147 */       cacheRegion = null;
/*  86:    */     }
/*  87:150 */     Integer timeout = getTimeout(hints, query);
/*  88:151 */     if ((timeout != null) && (timeout.intValue() < 0)) {
/*  89:152 */       timeout = null;
/*  90:    */     }
/*  91:155 */     Integer fetchSize = getInteger(hints, "org.hibernate.fetchSize", name);
/*  92:156 */     if ((fetchSize != null) && (fetchSize.intValue() < 0)) {
/*  93:157 */       fetchSize = null;
/*  94:    */     }
/*  95:160 */     String comment = getString(hints, "org.hibernate.comment");
/*  96:161 */     if (StringHelper.isEmpty(comment)) {
/*  97:162 */       comment = null;
/*  98:    */     }
/*  99:165 */     metadata.addNamedQuery(new NamedQueryDefinition(name, query, getBoolean(hints, "org.hibernate.cacheable", name), cacheRegion, timeout, fetchSize, getFlushMode(hints, "org.hibernate.flushMode", name), getCacheMode(hints, "org.hibernate.cacheMode", name), getBoolean(hints, "org.hibernate.readOnly", name), comment, null));
/* 100:    */     
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:174 */     LOG.debugf("Binding named query: %s => %s", name, query);
/* 109:    */   }
/* 110:    */   
/* 111:    */   private static void bindNamedNativeQuery(MetadataImplementor metadata, AnnotationInstance annotation)
/* 112:    */   {
/* 113:178 */     String name = (String)JandexHelper.getValue(annotation, "name", String.class);
/* 114:179 */     if (StringHelper.isEmpty(name)) {
/* 115:180 */       throw new AnnotationException("A named native query must have a name when used in class or package level");
/* 116:    */     }
/* 117:183 */     String query = (String)JandexHelper.getValue(annotation, "query", String.class);
/* 118:    */     
/* 119:185 */     String resultSetMapping = (String)JandexHelper.getValue(annotation, "resultSetMapping", String.class);
/* 120:    */     
/* 121:187 */     AnnotationInstance[] hints = (AnnotationInstance[])JandexHelper.getValue(annotation, "hints", [Lorg.jboss.jandex.AnnotationInstance.class);
/* 122:    */     
/* 123:189 */     boolean cacheable = getBoolean(hints, "org.hibernate.cacheable", name);
/* 124:190 */     String cacheRegion = getString(hints, "org.hibernate.cacheRegion");
/* 125:191 */     if (StringHelper.isEmpty(cacheRegion)) {
/* 126:192 */       cacheRegion = null;
/* 127:    */     }
/* 128:195 */     Integer timeout = getTimeout(hints, query);
/* 129:196 */     if ((timeout != null) && (timeout.intValue() < 0)) {
/* 130:197 */       timeout = null;
/* 131:    */     }
/* 132:200 */     Integer fetchSize = getInteger(hints, "org.hibernate.fetchSize", name);
/* 133:201 */     if ((fetchSize != null) && (fetchSize.intValue() < 0)) {
/* 134:202 */       fetchSize = null;
/* 135:    */     }
/* 136:205 */     FlushMode flushMode = getFlushMode(hints, "org.hibernate.flushMode", name);
/* 137:206 */     CacheMode cacheMode = getCacheMode(hints, "org.hibernate.cacheMode", name);
/* 138:    */     
/* 139:208 */     boolean readOnly = getBoolean(hints, "org.hibernate.readOnly", name);
/* 140:    */     
/* 141:210 */     String comment = getString(hints, "org.hibernate.comment");
/* 142:211 */     if (StringHelper.isEmpty(comment)) {
/* 143:212 */       comment = null;
/* 144:    */     }
/* 145:215 */     boolean callable = getBoolean(hints, "org.hibernate.callable", name);
/* 146:    */     NamedSQLQueryDefinition def;
/* 147:    */     NamedSQLQueryDefinition def;
/* 148:217 */     if (StringHelper.isNotEmpty(resultSetMapping))
/* 149:    */     {
/* 150:218 */       def = new NamedSQLQueryDefinition(name, query, resultSetMapping, null, cacheable, cacheRegion, timeout, fetchSize, flushMode, cacheMode, readOnly, comment, null, callable);
/* 151:    */     }
/* 152:    */     else
/* 153:    */     {
/* 154:227 */       AnnotationValue annotationValue = annotation.value("resultClass");
/* 155:228 */       if (annotationValue == null) {
/* 156:229 */         throw new NotYetImplementedException("Pure native scalar queries are not yet supported");
/* 157:    */       }
/* 158:231 */       NativeSQLQueryRootReturn[] queryRoots = { new NativeSQLQueryRootReturn("alias1", annotationValue.asString(), new HashMap(), LockMode.READ) };
/* 159:    */       
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:239 */       def = new NamedSQLQueryDefinition(name, query, queryRoots, null, cacheable, cacheRegion, timeout, fetchSize, flushMode, cacheMode, readOnly, comment, null, callable);
/* 167:    */     }
/* 168:256 */     metadata.addNamedNativeQuery(def);
/* 169:257 */     LOG.debugf("Binding named native query: %s => %s", name, query);
/* 170:    */   }
/* 171:    */   
/* 172:    */   private static boolean getBoolean(AnnotationInstance[] hints, String element, String query)
/* 173:    */   {
/* 174:261 */     String val = getString(hints, element);
/* 175:262 */     if ((val == null) || (val.equalsIgnoreCase("false"))) {
/* 176:263 */       return false;
/* 177:    */     }
/* 178:265 */     if (val.equalsIgnoreCase("true")) {
/* 179:266 */       return true;
/* 180:    */     }
/* 181:268 */     throw new AnnotationException("Not a boolean in hint: " + query + ":" + element);
/* 182:    */   }
/* 183:    */   
/* 184:    */   private static CacheMode getCacheMode(AnnotationInstance[] hints, String element, String query)
/* 185:    */   {
/* 186:272 */     String val = getString(hints, element);
/* 187:273 */     if (val == null) {
/* 188:274 */       return null;
/* 189:    */     }
/* 190:276 */     if (val.equalsIgnoreCase(CacheMode.GET.toString())) {
/* 191:277 */       return CacheMode.GET;
/* 192:    */     }
/* 193:279 */     if (val.equalsIgnoreCase(CacheMode.IGNORE.toString())) {
/* 194:280 */       return CacheMode.IGNORE;
/* 195:    */     }
/* 196:282 */     if (val.equalsIgnoreCase(CacheMode.NORMAL.toString())) {
/* 197:283 */       return CacheMode.NORMAL;
/* 198:    */     }
/* 199:285 */     if (val.equalsIgnoreCase(CacheMode.PUT.toString())) {
/* 200:286 */       return CacheMode.PUT;
/* 201:    */     }
/* 202:288 */     if (val.equalsIgnoreCase(CacheMode.REFRESH.toString())) {
/* 203:289 */       return CacheMode.REFRESH;
/* 204:    */     }
/* 205:291 */     throw new AnnotationException("Unknown CacheMode in hint: " + query + ":" + element);
/* 206:    */   }
/* 207:    */   
/* 208:    */   private static FlushMode getFlushMode(AnnotationInstance[] hints, String element, String query)
/* 209:    */   {
/* 210:295 */     String val = getString(hints, element);
/* 211:296 */     if (val == null) {
/* 212:297 */       return null;
/* 213:    */     }
/* 214:299 */     if (val.equalsIgnoreCase(FlushMode.ALWAYS.toString())) {
/* 215:300 */       return FlushMode.ALWAYS;
/* 216:    */     }
/* 217:302 */     if (val.equalsIgnoreCase(FlushMode.AUTO.toString())) {
/* 218:303 */       return FlushMode.AUTO;
/* 219:    */     }
/* 220:305 */     if (val.equalsIgnoreCase(FlushMode.COMMIT.toString())) {
/* 221:306 */       return FlushMode.COMMIT;
/* 222:    */     }
/* 223:308 */     if (val.equalsIgnoreCase(FlushMode.NEVER.toString())) {
/* 224:309 */       return FlushMode.MANUAL;
/* 225:    */     }
/* 226:311 */     if (val.equalsIgnoreCase(FlushMode.MANUAL.toString())) {
/* 227:312 */       return FlushMode.MANUAL;
/* 228:    */     }
/* 229:315 */     throw new AnnotationException("Unknown FlushMode in hint: " + query + ":" + element);
/* 230:    */   }
/* 231:    */   
/* 232:    */   private static Integer getInteger(AnnotationInstance[] hints, String element, String query)
/* 233:    */   {
/* 234:320 */     String val = getString(hints, element);
/* 235:321 */     if (val == null) {
/* 236:322 */       return null;
/* 237:    */     }
/* 238:    */     try
/* 239:    */     {
/* 240:325 */       return Integer.decode(val);
/* 241:    */     }
/* 242:    */     catch (NumberFormatException nfe)
/* 243:    */     {
/* 244:328 */       throw new AnnotationException("Not an integer in hint: " + query + ":" + element, nfe);
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   private static String getString(AnnotationInstance[] hints, String element)
/* 249:    */   {
/* 250:333 */     for (AnnotationInstance hint : hints) {
/* 251:334 */       if (element.equals(JandexHelper.getValue(hint, "name", String.class))) {
/* 252:335 */         return (String)JandexHelper.getValue(hint, "value", String.class);
/* 253:    */       }
/* 254:    */     }
/* 255:338 */     return null;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private static Integer getTimeout(AnnotationInstance[] hints, String query)
/* 259:    */   {
/* 260:342 */     Integer timeout = getInteger(hints, "javax.persistence.query.timeout", query);
/* 261:343 */     if (timeout == null) {
/* 262:344 */       return getInteger(hints, "org.hibernate.timeout", query);
/* 263:    */     }
/* 264:346 */     return Integer.valueOf((timeout.intValue() + 500) / 1000);
/* 265:    */   }
/* 266:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.global.QueryBinder
 * JD-Core Version:    0.7.0.1
 */