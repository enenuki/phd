/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.util.KeyDataPair;
/*   4:    */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*   5:    */ import java.io.ByteArrayInputStream;
/*   6:    */ import java.io.ByteArrayOutputStream;
/*   7:    */ import java.io.File;
/*   8:    */ import java.io.FileOutputStream;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.net.URI;
/*  12:    */ import java.net.URISyntaxException;
/*  13:    */ import java.net.URL;
/*  14:    */ import java.nio.charset.Charset;
/*  15:    */ import java.security.GeneralSecurityException;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Arrays;
/*  18:    */ import java.util.Collections;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import java.util.Map.Entry;
/*  23:    */ import java.util.Random;
/*  24:    */ import org.apache.commons.io.IOUtils;
/*  25:    */ import org.apache.commons.lang.StringUtils;
/*  26:    */ import org.apache.http.Header;
/*  27:    */ import org.apache.http.HttpEntity;
/*  28:    */ import org.apache.http.HttpEntityEnclosingRequest;
/*  29:    */ import org.apache.http.HttpHost;
/*  30:    */ import org.apache.http.HttpRequest;
/*  31:    */ import org.apache.http.HttpResponse;
/*  32:    */ import org.apache.http.ProtocolException;
/*  33:    */ import org.apache.http.StatusLine;
/*  34:    */ import org.apache.http.auth.AuthScope;
/*  35:    */ import org.apache.http.auth.Credentials;
/*  36:    */ import org.apache.http.client.CookieStore;
/*  37:    */ import org.apache.http.client.CredentialsProvider;
/*  38:    */ import org.apache.http.client.HttpClient;
/*  39:    */ import org.apache.http.client.methods.HttpDelete;
/*  40:    */ import org.apache.http.client.methods.HttpGet;
/*  41:    */ import org.apache.http.client.methods.HttpHead;
/*  42:    */ import org.apache.http.client.methods.HttpOptions;
/*  43:    */ import org.apache.http.client.methods.HttpPost;
/*  44:    */ import org.apache.http.client.methods.HttpPut;
/*  45:    */ import org.apache.http.client.methods.HttpRequestBase;
/*  46:    */ import org.apache.http.client.methods.HttpTrace;
/*  47:    */ import org.apache.http.client.methods.HttpUriRequest;
/*  48:    */ import org.apache.http.client.params.HttpClientParams;
/*  49:    */ import org.apache.http.client.utils.URIUtils;
/*  50:    */ import org.apache.http.client.utils.URLEncodedUtils;
/*  51:    */ import org.apache.http.conn.ClientConnectionManager;
/*  52:    */ import org.apache.http.conn.scheme.PlainSocketFactory;
/*  53:    */ import org.apache.http.conn.scheme.Scheme;
/*  54:    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  55:    */ import org.apache.http.conn.ssl.SSLSocketFactory;
/*  56:    */ import org.apache.http.cookie.Cookie;
/*  57:    */ import org.apache.http.cookie.CookieSpec;
/*  58:    */ import org.apache.http.cookie.CookieSpecFactory;
/*  59:    */ import org.apache.http.cookie.CookieSpecRegistry;
/*  60:    */ import org.apache.http.entity.StringEntity;
/*  61:    */ import org.apache.http.entity.mime.HttpMultipartMode;
/*  62:    */ import org.apache.http.entity.mime.MultipartEntity;
/*  63:    */ import org.apache.http.entity.mime.content.ContentBody;
/*  64:    */ import org.apache.http.entity.mime.content.FileBody;
/*  65:    */ import org.apache.http.entity.mime.content.InputStreamBody;
/*  66:    */ import org.apache.http.entity.mime.content.StringBody;
/*  67:    */ import org.apache.http.impl.client.AbstractHttpClient;
/*  68:    */ import org.apache.http.impl.client.DefaultHttpClient;
/*  69:    */ import org.apache.http.impl.client.DefaultRedirectStrategy;
/*  70:    */ import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
/*  71:    */ import org.apache.http.message.BasicHeader;
/*  72:    */ import org.apache.http.params.BasicHttpParams;
/*  73:    */ import org.apache.http.params.HttpParams;
/*  74:    */ import org.apache.http.protocol.HttpContext;
/*  75:    */ 
/*  76:    */ public class HttpWebConnection
/*  77:    */   implements WebConnection
/*  78:    */ {
/*  79:    */   private static final String HACKED_COOKIE_POLICY = "mine";
/*  80:    */   private AbstractHttpClient httpClient_;
/*  81:    */   private final WebClient webClient_;
/*  82:    */   private String virtualHost_;
/*  83:124 */   private final CookieSpecFactory htmlUnitCookieSpecFactory_ = new CookieSpecFactory()
/*  84:    */   {
/*  85:    */     public CookieSpec newInstance(HttpParams params)
/*  86:    */     {
/*  87:126 */       return new HtmlUnitBrowserCompatCookieSpec();
/*  88:    */     }
/*  89:    */   };
/*  90:    */   private static final long MAX_IN_MEMORY = 512000L;
/*  91:    */   
/*  92:    */   public HttpWebConnection(WebClient webClient)
/*  93:    */   {
/*  94:135 */     this.webClient_ = webClient;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public WebResponse getResponse(WebRequest request)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:142 */     URL url = request.getUrl();
/* 101:143 */     AbstractHttpClient httpClient = getHttpClient();
/* 102:144 */     this.webClient_.getCookieManager().updateState(httpClient.getCookieStore());
/* 103:    */     
/* 104:146 */     HttpUriRequest httpMethod = null;
/* 105:    */     try
/* 106:    */     {
/* 107:148 */       httpMethod = makeHttpMethod(request);
/* 108:149 */       HttpHost hostConfiguration = getHostConfiguration(request);
/* 109:150 */       setProxy(httpClient, request);
/* 110:151 */       long startTime = System.currentTimeMillis();
/* 111:152 */       HttpResponse httpResponse = httpClient.execute(hostConfiguration, httpMethod);
/* 112:153 */       DownloadedContent downloadedBody = downloadResponseBody(httpResponse);
/* 113:154 */       long endTime = System.currentTimeMillis();
/* 114:155 */       this.webClient_.getCookieManager().updateFromState(httpClient.getCookieStore());
/* 115:156 */       return makeWebResponse(httpResponse, request, downloadedBody, endTime - startTime);
/* 116:    */     }
/* 117:    */     catch (URISyntaxException e)
/* 118:    */     {
/* 119:159 */       throw new IOException("Unable to create URI from URL: " + url.toExternalForm() + " (reason: " + e.getMessage() + ")");
/* 120:    */     }
/* 121:    */     finally
/* 122:    */     {
/* 123:163 */       if (httpMethod != null) {
/* 124:164 */         onResponseGenerated(httpMethod);
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected void onResponseGenerated(HttpUriRequest httpMethod) {}
/* 130:    */   
/* 131:    */   private static HttpHost getHostConfiguration(WebRequest webRequest)
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:185 */     URL url = webRequest.getUrl();
/* 135:186 */     HttpHost hostConfiguration = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
/* 136:    */     
/* 137:188 */     return hostConfiguration;
/* 138:    */   }
/* 139:    */   
/* 140:    */   private static void setProxy(HttpClient httpClient, WebRequest webRequest)
/* 141:    */   {
/* 142:192 */     if (webRequest.getProxyHost() != null)
/* 143:    */     {
/* 144:193 */       String proxyHost = webRequest.getProxyHost();
/* 145:194 */       int proxyPort = webRequest.getProxyPort();
/* 146:195 */       HttpHost proxy = new HttpHost(proxyHost, proxyPort);
/* 147:196 */       if (webRequest.isSocksProxy())
/* 148:    */       {
/* 149:197 */         SocksSocketFactory factory = (SocksSocketFactory)httpClient.getConnectionManager().getSchemeRegistry().getScheme("http").getSchemeSocketFactory();
/* 150:    */         
/* 151:199 */         factory.setSocksProxy(proxy);
/* 152:    */       }
/* 153:    */       else
/* 154:    */       {
/* 155:202 */         httpClient.getParams().setParameter("http.route.default-proxy", proxy);
/* 156:    */       }
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   private HttpUriRequest makeHttpMethod(WebRequest webRequest)
/* 161:    */     throws IOException, URISyntaxException
/* 162:    */   {
/* 163:220 */     URL url = UrlUtils.encodeUrl(webRequest.getUrl(), false);
/* 164:221 */     String charset = webRequest.getCharset();
/* 165:222 */     URI uri = URIUtils.createURI(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), null);
/* 166:    */     
/* 167:224 */     HttpRequestBase httpMethod = buildHttpMethod(webRequest.getHttpMethod(), uri);
/* 168:225 */     if (!(httpMethod instanceof HttpEntityEnclosingRequest))
/* 169:    */     {
/* 170:227 */       if (!webRequest.getRequestParameters().isEmpty())
/* 171:    */       {
/* 172:228 */         List<com.gargoylesoftware.htmlunit.util.NameValuePair> pairs = webRequest.getRequestParameters();
/* 173:229 */         org.apache.http.NameValuePair[] httpClientPairs = com.gargoylesoftware.htmlunit.util.NameValuePair.toHttpClient(pairs);
/* 174:230 */         String query = URLEncodedUtils.format(Arrays.asList(httpClientPairs), charset);
/* 175:231 */         uri = URIUtils.createURI(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), query, null);
/* 176:232 */         httpMethod.setURI(uri);
/* 177:    */       }
/* 178:    */     }
/* 179:    */     else
/* 180:    */     {
/* 181:236 */       HttpEntityEnclosingRequest method = (HttpEntityEnclosingRequest)httpMethod;
/* 182:238 */       if ((webRequest.getEncodingType() == FormEncodingType.URL_ENCODED) && ((method instanceof HttpPost)))
/* 183:    */       {
/* 184:239 */         HttpPost postMethod = (HttpPost)method;
/* 185:240 */         if (webRequest.getRequestBody() == null)
/* 186:    */         {
/* 187:241 */           List<com.gargoylesoftware.htmlunit.util.NameValuePair> pairs = webRequest.getRequestParameters();
/* 188:242 */           org.apache.http.NameValuePair[] httpClientPairs = com.gargoylesoftware.htmlunit.util.NameValuePair.toHttpClient(pairs);
/* 189:243 */           String query = URLEncodedUtils.format(Arrays.asList(httpClientPairs), charset);
/* 190:244 */           StringEntity urlEncodedEntity = new StringEntity(query, charset);
/* 191:245 */           urlEncodedEntity.setContentType("application/x-www-form-urlencoded");
/* 192:246 */           postMethod.setEntity(urlEncodedEntity);
/* 193:    */         }
/* 194:    */         else
/* 195:    */         {
/* 196:249 */           String body = StringUtils.defaultString(webRequest.getRequestBody());
/* 197:250 */           StringEntity urlEncodedEntity = new StringEntity(body, charset);
/* 198:251 */           urlEncodedEntity.setContentType("application/x-www-form-urlencoded");
/* 199:252 */           postMethod.setEntity(urlEncodedEntity);
/* 200:    */         }
/* 201:    */       }
/* 202:255 */       else if (FormEncodingType.MULTIPART == webRequest.getEncodingType())
/* 203:    */       {
/* 204:256 */         StringBuilder boundary = new StringBuilder();
/* 205:257 */         boundary.append("---------------------------");
/* 206:258 */         Random rand = new Random();
/* 207:259 */         char[] chars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
/* 208:260 */         for (int i = 0; i < 14; i++) {
/* 209:261 */           boundary.append(chars[rand.nextInt(chars.length)]);
/* 210:    */         }
/* 211:263 */         Charset c = getCharset(charset, webRequest.getRequestParameters());
/* 212:264 */         MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary.toString(), c);
/* 213:267 */         for (com.gargoylesoftware.htmlunit.util.NameValuePair pair : webRequest.getRequestParameters()) {
/* 214:268 */           if ((pair instanceof KeyDataPair))
/* 215:    */           {
/* 216:269 */             KeyDataPair pairWithFile = (KeyDataPair)pair;
/* 217:270 */             ContentBody contentBody = buildFilePart(pairWithFile);
/* 218:271 */             multipartEntity.addPart(pair.getName(), contentBody);
/* 219:    */           }
/* 220:    */           else
/* 221:    */           {
/* 222:274 */             StringBody stringBody = new StringBody(pair.getValue(), Charset.forName(webRequest.getCharset()));
/* 223:    */             
/* 224:276 */             multipartEntity.addPart(pair.getName(), stringBody);
/* 225:    */           }
/* 226:    */         }
/* 227:279 */         method.setEntity(multipartEntity);
/* 228:    */       }
/* 229:    */       else
/* 230:    */       {
/* 231:282 */         String body = webRequest.getRequestBody();
/* 232:283 */         if (body != null) {
/* 233:284 */           method.setEntity(new StringEntity(body, charset));
/* 234:    */         }
/* 235:    */       }
/* 236:    */     }
/* 237:289 */     if (this.webClient_.getBrowserVersion().hasFeature(BrowserVersionFeatures.HTTP_HEADER_HOST_FIRST))
/* 238:    */     {
/* 239:290 */       int port = webRequest.getUrl().getPort();
/* 240:291 */       StringBuilder host = new StringBuilder(webRequest.getUrl().getHost());
/* 241:292 */       if ((port != 80) && (port > 0))
/* 242:    */       {
/* 243:293 */         host.append(':');
/* 244:294 */         host.append(Integer.toString(port));
/* 245:    */       }
/* 246:296 */       httpMethod.setHeader(new BasicHeader("Host", host.toString()));
/* 247:    */     }
/* 248:298 */     httpMethod.setHeader(new BasicHeader("User-Agent", this.webClient_.getBrowserVersion().getUserAgent()));
/* 249:    */     
/* 250:300 */     writeRequestHeadersToHttpMethod(httpMethod, webRequest.getAdditionalHeaders());
/* 251:    */     
/* 252:    */ 
/* 253:303 */     AbstractHttpClient httpClient = getHttpClient();
/* 254:    */     
/* 255:    */ 
/* 256:    */ 
/* 257:307 */     CredentialsProvider credentialsProvider = this.webClient_.getCredentialsProvider();
/* 258:    */     
/* 259:    */ 
/* 260:310 */     Credentials requestUrlCredentials = webRequest.getUrlCredentials();
/* 261:311 */     if ((null != requestUrlCredentials) && (this.webClient_.getBrowserVersion().hasFeature(BrowserVersionFeatures.URL_AUTH_CREDENTIALS)))
/* 262:    */     {
/* 263:313 */       URL requestUrl = webRequest.getUrl();
/* 264:314 */       AuthScope authScope = new AuthScope(requestUrl.getHost(), requestUrl.getPort());
/* 265:    */       
/* 266:316 */       credentialsProvider.setCredentials(authScope, requestUrlCredentials);
/* 267:    */     }
/* 268:320 */     Credentials requestCredentials = webRequest.getCredentials();
/* 269:321 */     if (null != requestCredentials)
/* 270:    */     {
/* 271:322 */       URL requestUrl = webRequest.getUrl();
/* 272:323 */       AuthScope authScope = new AuthScope(requestUrl.getHost(), requestUrl.getPort());
/* 273:    */       
/* 274:325 */       credentialsProvider.setCredentials(authScope, requestCredentials);
/* 275:    */     }
/* 276:327 */     httpClient.setCredentialsProvider(credentialsProvider);
/* 277:329 */     if (this.webClient_.getCookieManager().isCookiesEnabled())
/* 278:    */     {
/* 279:332 */       httpClient.getParams().setParameter("http.protocol.single-cookie-header", Boolean.TRUE);
/* 280:333 */       httpClient.getParams().setParameter("http.protocol.cookie-policy", "mine");
/* 281:    */     }
/* 282:    */     else
/* 283:    */     {
/* 284:337 */       httpClient.setCookieStore(new CookieStore()
/* 285:    */       {
/* 286:    */         public void addCookie(Cookie cookie) {}
/* 287:    */         
/* 288:    */         public void clear() {}
/* 289:    */         
/* 290:    */         public boolean clearExpired(Date date)
/* 291:    */         {
/* 292:341 */           return false;
/* 293:    */         }
/* 294:    */         
/* 295:    */         public List<Cookie> getCookies()
/* 296:    */         {
/* 297:345 */           return Collections.EMPTY_LIST;
/* 298:    */         }
/* 299:    */       });
/* 300:    */     }
/* 301:349 */     return httpMethod;
/* 302:    */   }
/* 303:    */   
/* 304:    */   private Charset getCharset(String charset, List<com.gargoylesoftware.htmlunit.util.NameValuePair> pairs)
/* 305:    */   {
/* 306:353 */     for (com.gargoylesoftware.htmlunit.util.NameValuePair pair : pairs) {
/* 307:354 */       if ((pair instanceof KeyDataPair))
/* 308:    */       {
/* 309:355 */         KeyDataPair pairWithFile = (KeyDataPair)pair;
/* 310:356 */         if ((pairWithFile.getData() == null) && (pairWithFile.getFile() != null))
/* 311:    */         {
/* 312:357 */           String fileName = pairWithFile.getFile().getName();
/* 313:358 */           for (int i = 0; i < fileName.length(); i++) {
/* 314:359 */             if (fileName.codePointAt(i) > 127) {
/* 315:360 */               return Charset.forName(charset);
/* 316:    */             }
/* 317:    */           }
/* 318:    */         }
/* 319:    */       }
/* 320:    */     }
/* 321:366 */     return null;
/* 322:    */   }
/* 323:    */   
/* 324:    */   ContentBody buildFilePart(final KeyDataPair pairWithFile)
/* 325:    */   {
/* 326:370 */     String contentType = pairWithFile.getContentType();
/* 327:371 */     if (contentType == null) {
/* 328:372 */       contentType = "application/octet-stream";
/* 329:    */     }
/* 330:375 */     File file = pairWithFile.getFile();
/* 331:377 */     if (pairWithFile.getData() != null)
/* 332:    */     {
/* 333:378 */       if (file == null) {
/* 334:379 */         return new InputStreamBody(new ByteArrayInputStream(pairWithFile.getData()), contentType, pairWithFile.getValue());
/* 335:    */       }
/* 336:383 */       if (this.webClient_.getBrowserVersion().hasFeature(BrowserVersionFeatures.HEADER_CONTENT_DISPOSITION_ABSOLUTE_PATH)) {
/* 337:385 */         return new InputStreamBody(new ByteArrayInputStream(pairWithFile.getData()), contentType, file.getAbsolutePath());
/* 338:    */       }
/* 339:389 */       return new InputStreamBody(new ByteArrayInputStream(pairWithFile.getData()), contentType, file.getName());
/* 340:    */     }
/* 341:393 */     if (file == null) {
/* 342:394 */       new InputStreamBody(new ByteArrayInputStream(new byte[0]), contentType, pairWithFile.getValue())
/* 343:    */       {
/* 344:    */         public long getContentLength()
/* 345:    */         {
/* 346:398 */           return 0L;
/* 347:    */         }
/* 348:    */       };
/* 349:    */     }
/* 350:403 */     new FileBody(pairWithFile.getFile(), contentType)
/* 351:    */     {
/* 352:    */       public String getFilename()
/* 353:    */       {
/* 354:406 */         if (getFile() == null) {
/* 355:407 */           return pairWithFile.getValue();
/* 356:    */         }
/* 357:409 */         if (HttpWebConnection.this.webClient_.getBrowserVersion().hasFeature(BrowserVersionFeatures.HEADER_CONTENT_DISPOSITION_ABSOLUTE_PATH)) {
/* 358:411 */           return getFile().getAbsolutePath();
/* 359:    */         }
/* 360:414 */         return super.getFilename();
/* 361:    */       }
/* 362:    */     };
/* 363:    */   }
/* 364:    */   
/* 365:    */   private static HttpRequestBase buildHttpMethod(HttpMethod submitMethod, URI uri)
/* 366:    */   {
/* 367:    */     HttpRequestBase method;
/* 368:428 */     switch (6.$SwitchMap$com$gargoylesoftware$htmlunit$HttpMethod[submitMethod.ordinal()])
/* 369:    */     {
/* 370:    */     case 1: 
/* 371:430 */       method = new HttpGet(uri);
/* 372:431 */       break;
/* 373:    */     case 2: 
/* 374:434 */       method = new HttpPost(uri);
/* 375:435 */       break;
/* 376:    */     case 3: 
/* 377:438 */       method = new HttpPut(uri);
/* 378:439 */       break;
/* 379:    */     case 4: 
/* 380:442 */       method = new HttpDelete(uri);
/* 381:443 */       break;
/* 382:    */     case 5: 
/* 383:446 */       method = new HttpOptions(uri);
/* 384:447 */       break;
/* 385:    */     case 6: 
/* 386:450 */       method = new HttpHead(uri);
/* 387:451 */       break;
/* 388:    */     case 7: 
/* 389:454 */       method = new HttpTrace(uri);
/* 390:455 */       break;
/* 391:    */     default: 
/* 392:458 */       throw new IllegalStateException("Submit method not yet supported: " + submitMethod);
/* 393:    */     }
/* 394:460 */     return method;
/* 395:    */   }
/* 396:    */   
/* 397:    */   protected synchronized AbstractHttpClient getHttpClient()
/* 398:    */   {
/* 399:469 */     if (this.httpClient_ == null)
/* 400:    */     {
/* 401:470 */       this.httpClient_ = createHttpClient();
/* 402:    */       
/* 403:    */ 
/* 404:    */ 
/* 405:474 */       this.httpClient_.getCookieSpecs().register("mine", this.htmlUnitCookieSpecFactory_);
/* 406:    */     }
/* 407:477 */     return this.httpClient_;
/* 408:    */   }
/* 409:    */   
/* 410:    */   protected int getTimeout()
/* 411:    */   {
/* 412:487 */     return this.webClient_.getTimeout();
/* 413:    */   }
/* 414:    */   
/* 415:    */   protected AbstractHttpClient createHttpClient()
/* 416:    */   {
/* 417:499 */     HttpParams httpsParams = new BasicHttpParams();
/* 418:    */     
/* 419:501 */     HttpClientParams.setRedirecting(httpsParams, false);
/* 420:    */     
/* 421:503 */     SchemeRegistry schemeRegistry = new SchemeRegistry();
/* 422:504 */     schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
/* 423:505 */     schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
/* 424:506 */     ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(schemeRegistry);
/* 425:    */     
/* 426:    */ 
/* 427:509 */     DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager, httpsParams);
/* 428:510 */     httpClient.setCookieStore(new HtmlUnitCookieStore());
/* 429:    */     
/* 430:512 */     httpClient.setRedirectStrategy(new DefaultRedirectStrategy()
/* 431:    */     {
/* 432:    */       public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)
/* 433:    */         throws ProtocolException
/* 434:    */       {
/* 435:515 */         return (super.isRedirected(request, response, context)) && (response.getFirstHeader("location") != null);
/* 436:    */       }
/* 437:    */     });
/* 438:520 */     if (getVirtualHost() != null) {
/* 439:521 */       httpClient.getParams().setParameter("http.virtual-host", this.virtualHost_);
/* 440:    */     }
/* 441:524 */     Scheme httpScheme = new Scheme("http", 80, new SocksSocketFactory());
/* 442:525 */     httpClient.getConnectionManager().getSchemeRegistry().register(httpScheme);
/* 443:    */     
/* 444:    */ 
/* 445:528 */     httpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(this.webClient_.getTimeout()));
/* 446:529 */     httpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(this.webClient_.getTimeout()));
/* 447:    */     
/* 448:    */ 
/* 449:532 */     return httpClient;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void setVirtualHost(String virtualHost)
/* 453:    */   {
/* 454:540 */     this.virtualHost_ = virtualHost;
/* 455:541 */     if (this.virtualHost_ != null) {
/* 456:542 */       getHttpClient().getParams().setParameter("http.virtual-host", this.virtualHost_);
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   public String getVirtualHost()
/* 461:    */   {
/* 462:551 */     return this.virtualHost_;
/* 463:    */   }
/* 464:    */   
/* 465:    */   private WebResponse makeWebResponse(HttpResponse httpResponse, WebRequest request, DownloadedContent responseBody, long loadTime)
/* 466:    */     throws IOException
/* 467:    */   {
/* 468:560 */     String statusMessage = httpResponse.getStatusLine().getReasonPhrase();
/* 469:561 */     if (statusMessage == null) {
/* 470:562 */       statusMessage = "Unknown status message";
/* 471:    */     }
/* 472:564 */     int statusCode = httpResponse.getStatusLine().getStatusCode();
/* 473:565 */     List<com.gargoylesoftware.htmlunit.util.NameValuePair> headers = new ArrayList();
/* 474:566 */     for (Header header : httpResponse.getAllHeaders()) {
/* 475:567 */       headers.add(new com.gargoylesoftware.htmlunit.util.NameValuePair(header.getName(), header.getValue()));
/* 476:    */     }
/* 477:569 */     WebResponseData responseData = new WebResponseData(responseBody, statusCode, statusMessage, headers);
/* 478:570 */     return newWebResponseInstance(responseData, loadTime, request);
/* 479:    */   }
/* 480:    */   
/* 481:    */   protected DownloadedContent downloadResponseBody(HttpResponse httpResponse)
/* 482:    */     throws IOException
/* 483:    */   {
/* 484:582 */     HttpEntity httpEntity = httpResponse.getEntity();
/* 485:583 */     if (httpEntity == null) {
/* 486:584 */       return new DownloadedContent.InMemory(new byte[0]);
/* 487:    */     }
/* 488:587 */     return downloadContent(httpEntity.getContent());
/* 489:    */   }
/* 490:    */   
/* 491:    */   public static DownloadedContent downloadContent(InputStream is)
/* 492:    */     throws IOException
/* 493:    */   {
/* 494:597 */     if (is == null) {
/* 495:598 */       return new DownloadedContent.InMemory(new byte[0]);
/* 496:    */     }
/* 497:600 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 498:    */     
/* 499:602 */     byte[] buffer = new byte[1024];
/* 500:    */     try
/* 501:    */     {
/* 502:    */       int nbRead;
/* 503:605 */       while ((nbRead = is.read(buffer)) != -1)
/* 504:    */       {
/* 505:606 */         bos.write(buffer, 0, nbRead);
/* 506:607 */         if (bos.size() > 512000L)
/* 507:    */         {
/* 508:609 */           File file = File.createTempFile("htmlunit", ".tmp");
/* 509:610 */           file.deleteOnExit();
/* 510:611 */           FileOutputStream fos = new FileOutputStream(file);
/* 511:612 */           bos.writeTo(fos);
/* 512:613 */           IOUtils.copyLarge(is, fos);
/* 513:614 */           fos.close();
/* 514:615 */           return new DownloadedContent.OnFile(file);
/* 515:    */         }
/* 516:    */       }
/* 517:    */     }
/* 518:    */     finally
/* 519:    */     {
/* 520:620 */       IOUtils.closeQuietly(is);
/* 521:    */     }
/* 522:623 */     return new DownloadedContent.InMemory(bos.toByteArray());
/* 523:    */   }
/* 524:    */   
/* 525:    */   protected WebResponse newWebResponseInstance(WebResponseData responseData, long loadTime, WebRequest request)
/* 526:    */   {
/* 527:638 */     return new WebResponse(responseData, request, loadTime);
/* 528:    */   }
/* 529:    */   
/* 530:    */   private static void writeRequestHeadersToHttpMethod(HttpUriRequest httpMethod, Map<String, String> requestHeaders)
/* 531:    */   {
/* 532:643 */     synchronized (requestHeaders)
/* 533:    */     {
/* 534:644 */       for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
/* 535:645 */         httpMethod.setHeader((String)entry.getKey(), (String)entry.getValue());
/* 536:    */       }
/* 537:    */     }
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setUseInsecureSSL(boolean useInsecureSSL)
/* 541:    */     throws GeneralSecurityException
/* 542:    */   {
/* 543:659 */     HttpWebConnectionInsecureSSL.setUseInsecureSSL(getHttpClient(), useInsecureSSL);
/* 544:    */   }
/* 545:    */   
/* 546:    */   public synchronized void shutdown()
/* 547:    */   {
/* 548:666 */     if (this.httpClient_ != null)
/* 549:    */     {
/* 550:667 */       this.httpClient_.getConnectionManager().shutdown();
/* 551:668 */       this.httpClient_ = null;
/* 552:    */     }
/* 553:    */   }
/* 554:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.HttpWebConnection
 * JD-Core Version:    0.7.0.1
 */