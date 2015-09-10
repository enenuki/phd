/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*   2:    */ 
/*   3:    */ import java.io.FileNotFoundException;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.io.Reader;
/*   8:    */ import java.io.Serializable;
/*   9:    */ import java.net.HttpURLConnection;
/*  10:    */ import java.net.URI;
/*  11:    */ import java.net.URISyntaxException;
/*  12:    */ import java.net.URL;
/*  13:    */ import java.net.URLConnection;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ 
/*  18:    */ public class UrlModuleSourceProvider
/*  19:    */   extends ModuleSourceProviderBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   private final Iterable<URI> privilegedUris;
/*  23:    */   private final Iterable<URI> fallbackUris;
/*  24:    */   private final UrlConnectionSecurityDomainProvider urlConnectionSecurityDomainProvider;
/*  25:    */   private final UrlConnectionExpiryCalculator urlConnectionExpiryCalculator;
/*  26:    */   
/*  27:    */   public UrlModuleSourceProvider(Iterable<URI> privilegedUris, Iterable<URI> fallbackUris)
/*  28:    */   {
/*  29: 52 */     this(privilegedUris, fallbackUris, new DefaultUrlConnectionExpiryCalculator(), null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public UrlModuleSourceProvider(Iterable<URI> privilegedUris, Iterable<URI> fallbackUris, UrlConnectionExpiryCalculator urlConnectionExpiryCalculator, UrlConnectionSecurityDomainProvider urlConnectionSecurityDomainProvider)
/*  33:    */   {
/*  34: 77 */     this.privilegedUris = privilegedUris;
/*  35: 78 */     this.fallbackUris = fallbackUris;
/*  36: 79 */     this.urlConnectionExpiryCalculator = urlConnectionExpiryCalculator;
/*  37: 80 */     this.urlConnectionSecurityDomainProvider = urlConnectionSecurityDomainProvider;
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected ModuleSource loadFromPrivilegedLocations(String moduleId, Object validator)
/*  41:    */     throws IOException, URISyntaxException
/*  42:    */   {
/*  43: 89 */     return loadFromPathList(moduleId, validator, this.privilegedUris);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected ModuleSource loadFromFallbackLocations(String moduleId, Object validator)
/*  47:    */     throws IOException, URISyntaxException
/*  48:    */   {
/*  49: 97 */     return loadFromPathList(moduleId, validator, this.fallbackUris);
/*  50:    */   }
/*  51:    */   
/*  52:    */   private ModuleSource loadFromPathList(String moduleId, Object validator, Iterable<URI> paths)
/*  53:    */     throws IOException, URISyntaxException
/*  54:    */   {
/*  55:104 */     if (paths == null) {
/*  56:105 */       return null;
/*  57:    */     }
/*  58:107 */     for (URI path : paths)
/*  59:    */     {
/*  60:108 */       ModuleSource moduleSource = loadFromUri(path.resolve(moduleId), path, validator);
/*  61:110 */       if (moduleSource != null) {
/*  62:111 */         return moduleSource;
/*  63:    */       }
/*  64:    */     }
/*  65:114 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected ModuleSource loadFromUri(URI uri, URI base, Object validator)
/*  69:    */     throws IOException, URISyntaxException
/*  70:    */   {
/*  71:122 */     URI fullUri = new URI(uri + ".js");
/*  72:123 */     ModuleSource source = loadFromActualUri(fullUri, base, validator);
/*  73:    */     
/*  74:    */ 
/*  75:126 */     return source != null ? source : loadFromActualUri(uri, base, validator);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected ModuleSource loadFromActualUri(URI uri, URI base, Object validator)
/*  79:    */     throws IOException
/*  80:    */   {
/*  81:133 */     URL url = uri.toURL();
/*  82:134 */     long request_time = System.currentTimeMillis();
/*  83:135 */     URLConnection urlConnection = openUrlConnection(url);
/*  84:    */     URLValidator applicableValidator;
/*  85:    */     URLValidator applicableValidator;
/*  86:137 */     if ((validator instanceof URLValidator))
/*  87:    */     {
/*  88:138 */       URLValidator uriValidator = (URLValidator)validator;
/*  89:139 */       applicableValidator = uriValidator.appliesTo(uri) ? uriValidator : null;
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:143 */       applicableValidator = null;
/*  94:    */     }
/*  95:145 */     if (applicableValidator != null) {
/*  96:146 */       applicableValidator.applyConditionals(urlConnection);
/*  97:    */     }
/*  98:    */     try
/*  99:    */     {
/* 100:149 */       urlConnection.connect();
/* 101:150 */       if ((applicableValidator != null) && (applicableValidator.updateValidator(urlConnection, request_time, this.urlConnectionExpiryCalculator)))
/* 102:    */       {
/* 103:154 */         close(urlConnection);
/* 104:155 */         return NOT_MODIFIED;
/* 105:    */       }
/* 106:158 */       return new ModuleSource(getReader(urlConnection), getSecurityDomain(urlConnection), uri, base, new URLValidator(uri, urlConnection, request_time, this.urlConnectionExpiryCalculator));
/* 107:    */     }
/* 108:    */     catch (FileNotFoundException e)
/* 109:    */     {
/* 110:164 */       return null;
/* 111:    */     }
/* 112:    */     catch (RuntimeException e)
/* 113:    */     {
/* 114:167 */       close(urlConnection);
/* 115:168 */       throw e;
/* 116:    */     }
/* 117:    */     catch (IOException e)
/* 118:    */     {
/* 119:171 */       close(urlConnection);
/* 120:172 */       throw e;
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private static Reader getReader(URLConnection urlConnection)
/* 125:    */     throws IOException
/* 126:    */   {
/* 127:179 */     return new InputStreamReader(urlConnection.getInputStream(), getCharacterEncoding(urlConnection));
/* 128:    */   }
/* 129:    */   
/* 130:    */   private static String getCharacterEncoding(URLConnection urlConnection)
/* 131:    */   {
/* 132:184 */     ParsedContentType pct = new ParsedContentType(urlConnection.getContentType());
/* 133:    */     
/* 134:186 */     String encoding = pct.getEncoding();
/* 135:187 */     if (encoding != null) {
/* 136:188 */       return encoding;
/* 137:    */     }
/* 138:190 */     String contentType = pct.getContentType();
/* 139:191 */     if ((contentType != null) && (contentType.startsWith("text/"))) {
/* 140:192 */       return "8859_1";
/* 141:    */     }
/* 142:195 */     return "utf-8";
/* 143:    */   }
/* 144:    */   
/* 145:    */   private Object getSecurityDomain(URLConnection urlConnection)
/* 146:    */   {
/* 147:200 */     return this.urlConnectionSecurityDomainProvider == null ? null : this.urlConnectionSecurityDomainProvider.getSecurityDomain(urlConnection);
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void close(URLConnection urlConnection)
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:207 */       urlConnection.getInputStream().close();
/* 155:    */     }
/* 156:    */     catch (IOException e)
/* 157:    */     {
/* 158:210 */       onFailedClosingUrlConnection(urlConnection, e);
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected void onFailedClosingUrlConnection(URLConnection urlConnection, IOException cause) {}
/* 163:    */   
/* 164:    */   protected URLConnection openUrlConnection(URL url)
/* 165:    */     throws IOException
/* 166:    */   {
/* 167:232 */     return url.openConnection();
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected boolean entityNeedsRevalidation(Object validator)
/* 171:    */   {
/* 172:237 */     return (!(validator instanceof URLValidator)) || (((URLValidator)validator).entityNeedsRevalidation());
/* 173:    */   }
/* 174:    */   
/* 175:    */   private static class URLValidator
/* 176:    */     implements Serializable
/* 177:    */   {
/* 178:    */     private static final long serialVersionUID = 1L;
/* 179:    */     private final URI uri;
/* 180:    */     private final long lastModified;
/* 181:    */     private final String entityTags;
/* 182:    */     private long expiry;
/* 183:    */     
/* 184:    */     public URLValidator(URI uri, URLConnection urlConnection, long request_time, UrlConnectionExpiryCalculator urlConnectionExpiryCalculator)
/* 185:    */     {
/* 186:252 */       this.uri = uri;
/* 187:253 */       this.lastModified = urlConnection.getLastModified();
/* 188:254 */       this.entityTags = getEntityTags(urlConnection);
/* 189:255 */       this.expiry = calculateExpiry(urlConnection, request_time, urlConnectionExpiryCalculator);
/* 190:    */     }
/* 191:    */     
/* 192:    */     boolean updateValidator(URLConnection urlConnection, long request_time, UrlConnectionExpiryCalculator urlConnectionExpiryCalculator)
/* 193:    */       throws IOException
/* 194:    */     {
/* 195:263 */       boolean isResourceChanged = isResourceChanged(urlConnection);
/* 196:264 */       if (!isResourceChanged) {
/* 197:265 */         this.expiry = calculateExpiry(urlConnection, request_time, urlConnectionExpiryCalculator);
/* 198:    */       }
/* 199:268 */       return isResourceChanged;
/* 200:    */     }
/* 201:    */     
/* 202:    */     private boolean isResourceChanged(URLConnection urlConnection)
/* 203:    */       throws IOException
/* 204:    */     {
/* 205:273 */       if ((urlConnection instanceof HttpURLConnection)) {
/* 206:274 */         return ((HttpURLConnection)urlConnection).getResponseCode() == 304;
/* 207:    */       }
/* 208:277 */       return this.lastModified == urlConnection.getLastModified();
/* 209:    */     }
/* 210:    */     
/* 211:    */     private long calculateExpiry(URLConnection urlConnection, long request_time, UrlConnectionExpiryCalculator urlConnectionExpiryCalculator)
/* 212:    */     {
/* 213:284 */       if ("no-cache".equals(urlConnection.getHeaderField("Pragma"))) {
/* 214:285 */         return 0L;
/* 215:    */       }
/* 216:287 */       String cacheControl = urlConnection.getHeaderField("Cache-Control");
/* 217:289 */       if (cacheControl != null)
/* 218:    */       {
/* 219:290 */         if (cacheControl.indexOf("no-cache") != -1) {
/* 220:291 */           return 0L;
/* 221:    */         }
/* 222:293 */         int max_age = getMaxAge(cacheControl);
/* 223:294 */         if (-1 != max_age)
/* 224:    */         {
/* 225:295 */           long response_time = System.currentTimeMillis();
/* 226:296 */           long apparent_age = Math.max(0L, response_time - urlConnection.getDate());
/* 227:    */           
/* 228:298 */           long corrected_received_age = Math.max(apparent_age, urlConnection.getHeaderFieldInt("Age", 0) * 1000L);
/* 229:    */           
/* 230:300 */           long response_delay = response_time - request_time;
/* 231:301 */           long corrected_initial_age = corrected_received_age + response_delay;
/* 232:    */           
/* 233:303 */           long creation_time = response_time - corrected_initial_age;
/* 234:    */           
/* 235:305 */           return max_age * 1000L + creation_time;
/* 236:    */         }
/* 237:    */       }
/* 238:308 */       long explicitExpiry = urlConnection.getHeaderFieldDate("Expires", -1L);
/* 239:310 */       if (explicitExpiry != -1L) {
/* 240:311 */         return explicitExpiry;
/* 241:    */       }
/* 242:313 */       return urlConnectionExpiryCalculator == null ? 0L : urlConnectionExpiryCalculator.calculateExpiry(urlConnection);
/* 243:    */     }
/* 244:    */     
/* 245:    */     private int getMaxAge(String cacheControl)
/* 246:    */     {
/* 247:318 */       int maxAgeIndex = cacheControl.indexOf("max-age");
/* 248:319 */       if (maxAgeIndex == -1) {
/* 249:320 */         return -1;
/* 250:    */       }
/* 251:322 */       int eq = cacheControl.indexOf('=', maxAgeIndex + 7);
/* 252:323 */       if (eq == -1) {
/* 253:324 */         return -1;
/* 254:    */       }
/* 255:326 */       int comma = cacheControl.indexOf(',', eq + 1);
/* 256:    */       String strAge;
/* 257:    */       String strAge;
/* 258:328 */       if (comma == -1) {
/* 259:329 */         strAge = cacheControl.substring(eq + 1);
/* 260:    */       } else {
/* 261:332 */         strAge = cacheControl.substring(eq + 1, comma);
/* 262:    */       }
/* 263:    */       try
/* 264:    */       {
/* 265:335 */         return Integer.parseInt(strAge);
/* 266:    */       }
/* 267:    */       catch (NumberFormatException e) {}
/* 268:338 */       return -1;
/* 269:    */     }
/* 270:    */     
/* 271:    */     private String getEntityTags(URLConnection urlConnection)
/* 272:    */     {
/* 273:343 */       List<String> etags = (List)urlConnection.getHeaderFields().get("ETag");
/* 274:344 */       if ((etags == null) || (etags.isEmpty())) {
/* 275:345 */         return null;
/* 276:    */       }
/* 277:347 */       StringBuilder b = new StringBuilder();
/* 278:348 */       Iterator<String> it = etags.iterator();
/* 279:349 */       b.append((String)it.next());
/* 280:350 */       while (it.hasNext()) {
/* 281:351 */         b.append(", ").append((String)it.next());
/* 282:    */       }
/* 283:353 */       return b.toString();
/* 284:    */     }
/* 285:    */     
/* 286:    */     boolean appliesTo(URI uri)
/* 287:    */     {
/* 288:357 */       return this.uri.equals(uri);
/* 289:    */     }
/* 290:    */     
/* 291:    */     void applyConditionals(URLConnection urlConnection)
/* 292:    */     {
/* 293:361 */       if (this.lastModified != 0L) {
/* 294:362 */         urlConnection.setIfModifiedSince(this.lastModified);
/* 295:    */       }
/* 296:364 */       if ((this.entityTags != null) && (this.entityTags.length() > 0)) {
/* 297:365 */         urlConnection.addRequestProperty("If-None-Match", this.entityTags);
/* 298:    */       }
/* 299:    */     }
/* 300:    */     
/* 301:    */     boolean entityNeedsRevalidation()
/* 302:    */     {
/* 303:370 */       return System.currentTimeMillis() > this.expiry;
/* 304:    */     }
/* 305:    */   }
/* 306:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.UrlModuleSourceProvider
 * JD-Core Version:    0.7.0.1
 */