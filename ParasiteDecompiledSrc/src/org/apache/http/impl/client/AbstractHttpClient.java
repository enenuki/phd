/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.UndeclaredThrowableException;
/*   5:    */ import java.net.URI;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.apache.http.ConnectionReuseStrategy;
/*   9:    */ import org.apache.http.HttpEntity;
/*  10:    */ import org.apache.http.HttpException;
/*  11:    */ import org.apache.http.HttpHost;
/*  12:    */ import org.apache.http.HttpRequest;
/*  13:    */ import org.apache.http.HttpRequestInterceptor;
/*  14:    */ import org.apache.http.HttpResponse;
/*  15:    */ import org.apache.http.HttpResponseInterceptor;
/*  16:    */ import org.apache.http.annotation.GuardedBy;
/*  17:    */ import org.apache.http.annotation.ThreadSafe;
/*  18:    */ import org.apache.http.auth.AuthSchemeRegistry;
/*  19:    */ import org.apache.http.client.AuthenticationHandler;
/*  20:    */ import org.apache.http.client.ClientProtocolException;
/*  21:    */ import org.apache.http.client.CookieStore;
/*  22:    */ import org.apache.http.client.CredentialsProvider;
/*  23:    */ import org.apache.http.client.HttpClient;
/*  24:    */ import org.apache.http.client.HttpRequestRetryHandler;
/*  25:    */ import org.apache.http.client.RedirectHandler;
/*  26:    */ import org.apache.http.client.RedirectStrategy;
/*  27:    */ import org.apache.http.client.RequestDirector;
/*  28:    */ import org.apache.http.client.ResponseHandler;
/*  29:    */ import org.apache.http.client.UserTokenHandler;
/*  30:    */ import org.apache.http.client.methods.HttpUriRequest;
/*  31:    */ import org.apache.http.client.utils.URIUtils;
/*  32:    */ import org.apache.http.conn.ClientConnectionManager;
/*  33:    */ import org.apache.http.conn.ClientConnectionManagerFactory;
/*  34:    */ import org.apache.http.conn.ConnectionKeepAliveStrategy;
/*  35:    */ import org.apache.http.conn.routing.HttpRoutePlanner;
/*  36:    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  37:    */ import org.apache.http.cookie.CookieSpecRegistry;
/*  38:    */ import org.apache.http.impl.DefaultConnectionReuseStrategy;
/*  39:    */ import org.apache.http.impl.auth.BasicSchemeFactory;
/*  40:    */ import org.apache.http.impl.auth.DigestSchemeFactory;
/*  41:    */ import org.apache.http.impl.auth.NTLMSchemeFactory;
/*  42:    */ import org.apache.http.impl.auth.NegotiateSchemeFactory;
/*  43:    */ import org.apache.http.impl.conn.DefaultHttpRoutePlanner;
/*  44:    */ import org.apache.http.impl.conn.SchemeRegistryFactory;
/*  45:    */ import org.apache.http.impl.conn.SingleClientConnManager;
/*  46:    */ import org.apache.http.impl.cookie.BestMatchSpecFactory;
/*  47:    */ import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
/*  48:    */ import org.apache.http.impl.cookie.IgnoreSpecFactory;
/*  49:    */ import org.apache.http.impl.cookie.NetscapeDraftSpecFactory;
/*  50:    */ import org.apache.http.impl.cookie.RFC2109SpecFactory;
/*  51:    */ import org.apache.http.impl.cookie.RFC2965SpecFactory;
/*  52:    */ import org.apache.http.params.HttpParams;
/*  53:    */ import org.apache.http.protocol.BasicHttpContext;
/*  54:    */ import org.apache.http.protocol.BasicHttpProcessor;
/*  55:    */ import org.apache.http.protocol.DefaultedHttpContext;
/*  56:    */ import org.apache.http.protocol.HttpContext;
/*  57:    */ import org.apache.http.protocol.HttpProcessor;
/*  58:    */ import org.apache.http.protocol.HttpRequestExecutor;
/*  59:    */ import org.apache.http.protocol.ImmutableHttpProcessor;
/*  60:    */ import org.apache.http.util.EntityUtils;
/*  61:    */ 
/*  62:    */ @ThreadSafe
/*  63:    */ public abstract class AbstractHttpClient
/*  64:    */   implements HttpClient
/*  65:    */ {
/*  66:182 */   private final Log log = LogFactory.getLog(getClass());
/*  67:    */   @GuardedBy("this")
/*  68:    */   private HttpParams defaultParams;
/*  69:    */   @GuardedBy("this")
/*  70:    */   private HttpRequestExecutor requestExec;
/*  71:    */   @GuardedBy("this")
/*  72:    */   private ClientConnectionManager connManager;
/*  73:    */   @GuardedBy("this")
/*  74:    */   private ConnectionReuseStrategy reuseStrategy;
/*  75:    */   @GuardedBy("this")
/*  76:    */   private ConnectionKeepAliveStrategy keepAliveStrategy;
/*  77:    */   @GuardedBy("this")
/*  78:    */   private CookieSpecRegistry supportedCookieSpecs;
/*  79:    */   @GuardedBy("this")
/*  80:    */   private AuthSchemeRegistry supportedAuthSchemes;
/*  81:    */   @GuardedBy("this")
/*  82:    */   private BasicHttpProcessor mutableProcessor;
/*  83:    */   @GuardedBy("this")
/*  84:    */   private ImmutableHttpProcessor protocolProcessor;
/*  85:    */   @GuardedBy("this")
/*  86:    */   private HttpRequestRetryHandler retryHandler;
/*  87:    */   @GuardedBy("this")
/*  88:    */   private RedirectStrategy redirectStrategy;
/*  89:    */   @GuardedBy("this")
/*  90:    */   private AuthenticationHandler targetAuthHandler;
/*  91:    */   @GuardedBy("this")
/*  92:    */   private AuthenticationHandler proxyAuthHandler;
/*  93:    */   @GuardedBy("this")
/*  94:    */   private CookieStore cookieStore;
/*  95:    */   @GuardedBy("this")
/*  96:    */   private CredentialsProvider credsProvider;
/*  97:    */   @GuardedBy("this")
/*  98:    */   private HttpRoutePlanner routePlanner;
/*  99:    */   @GuardedBy("this")
/* 100:    */   private UserTokenHandler userTokenHandler;
/* 101:    */   
/* 102:    */   protected AbstractHttpClient(ClientConnectionManager conman, HttpParams params)
/* 103:    */   {
/* 104:261 */     this.defaultParams = params;
/* 105:262 */     this.connManager = conman;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected abstract HttpParams createHttpParams();
/* 109:    */   
/* 110:    */   protected abstract BasicHttpProcessor createHttpProcessor();
/* 111:    */   
/* 112:    */   protected HttpContext createHttpContext()
/* 113:    */   {
/* 114:273 */     HttpContext context = new BasicHttpContext();
/* 115:274 */     context.setAttribute("http.scheme-registry", getConnectionManager().getSchemeRegistry());
/* 116:    */     
/* 117:    */ 
/* 118:277 */     context.setAttribute("http.authscheme-registry", getAuthSchemes());
/* 119:    */     
/* 120:    */ 
/* 121:280 */     context.setAttribute("http.cookiespec-registry", getCookieSpecs());
/* 122:    */     
/* 123:    */ 
/* 124:283 */     context.setAttribute("http.cookie-store", getCookieStore());
/* 125:    */     
/* 126:    */ 
/* 127:286 */     context.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
/* 128:    */     
/* 129:    */ 
/* 130:289 */     return context;
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected ClientConnectionManager createClientConnectionManager()
/* 134:    */   {
/* 135:294 */     SchemeRegistry registry = SchemeRegistryFactory.createDefault();
/* 136:    */     
/* 137:296 */     ClientConnectionManager connManager = null;
/* 138:297 */     HttpParams params = getParams();
/* 139:    */     
/* 140:299 */     ClientConnectionManagerFactory factory = null;
/* 141:    */     
/* 142:301 */     String className = (String)params.getParameter("http.connection-manager.factory-class-name");
/* 143:303 */     if (className != null) {
/* 144:    */       try
/* 145:    */       {
/* 146:305 */         Class<?> clazz = Class.forName(className);
/* 147:306 */         factory = (ClientConnectionManagerFactory)clazz.newInstance();
/* 148:    */       }
/* 149:    */       catch (ClassNotFoundException ex)
/* 150:    */       {
/* 151:308 */         throw new IllegalStateException("Invalid class name: " + className);
/* 152:    */       }
/* 153:    */       catch (IllegalAccessException ex)
/* 154:    */       {
/* 155:310 */         throw new IllegalAccessError(ex.getMessage());
/* 156:    */       }
/* 157:    */       catch (InstantiationException ex)
/* 158:    */       {
/* 159:312 */         throw new InstantiationError(ex.getMessage());
/* 160:    */       }
/* 161:    */     }
/* 162:315 */     if (factory != null) {
/* 163:316 */       connManager = factory.newInstance(params, registry);
/* 164:    */     } else {
/* 165:318 */       connManager = new SingleClientConnManager(registry);
/* 166:    */     }
/* 167:321 */     return connManager;
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected AuthSchemeRegistry createAuthSchemeRegistry()
/* 171:    */   {
/* 172:326 */     AuthSchemeRegistry registry = new AuthSchemeRegistry();
/* 173:327 */     registry.register("Basic", new BasicSchemeFactory());
/* 174:    */     
/* 175:    */ 
/* 176:330 */     registry.register("Digest", new DigestSchemeFactory());
/* 177:    */     
/* 178:    */ 
/* 179:333 */     registry.register("NTLM", new NTLMSchemeFactory());
/* 180:    */     
/* 181:    */ 
/* 182:336 */     registry.register("negotiate", new NegotiateSchemeFactory());
/* 183:    */     
/* 184:    */ 
/* 185:339 */     return registry;
/* 186:    */   }
/* 187:    */   
/* 188:    */   protected CookieSpecRegistry createCookieSpecRegistry()
/* 189:    */   {
/* 190:344 */     CookieSpecRegistry registry = new CookieSpecRegistry();
/* 191:345 */     registry.register("best-match", new BestMatchSpecFactory());
/* 192:    */     
/* 193:    */ 
/* 194:348 */     registry.register("compatibility", new BrowserCompatSpecFactory());
/* 195:    */     
/* 196:    */ 
/* 197:351 */     registry.register("netscape", new NetscapeDraftSpecFactory());
/* 198:    */     
/* 199:    */ 
/* 200:354 */     registry.register("rfc2109", new RFC2109SpecFactory());
/* 201:    */     
/* 202:    */ 
/* 203:357 */     registry.register("rfc2965", new RFC2965SpecFactory());
/* 204:    */     
/* 205:    */ 
/* 206:360 */     registry.register("ignoreCookies", new IgnoreSpecFactory());
/* 207:    */     
/* 208:    */ 
/* 209:363 */     return registry;
/* 210:    */   }
/* 211:    */   
/* 212:    */   protected HttpRequestExecutor createRequestExecutor()
/* 213:    */   {
/* 214:368 */     return new HttpRequestExecutor();
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected ConnectionReuseStrategy createConnectionReuseStrategy()
/* 218:    */   {
/* 219:373 */     return new DefaultConnectionReuseStrategy();
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy()
/* 223:    */   {
/* 224:378 */     return new DefaultConnectionKeepAliveStrategy();
/* 225:    */   }
/* 226:    */   
/* 227:    */   protected HttpRequestRetryHandler createHttpRequestRetryHandler()
/* 228:    */   {
/* 229:383 */     return new DefaultHttpRequestRetryHandler();
/* 230:    */   }
/* 231:    */   
/* 232:    */   @Deprecated
/* 233:    */   protected RedirectHandler createRedirectHandler()
/* 234:    */   {
/* 235:389 */     return new DefaultRedirectHandler();
/* 236:    */   }
/* 237:    */   
/* 238:    */   protected AuthenticationHandler createTargetAuthenticationHandler()
/* 239:    */   {
/* 240:394 */     return new DefaultTargetAuthenticationHandler();
/* 241:    */   }
/* 242:    */   
/* 243:    */   protected AuthenticationHandler createProxyAuthenticationHandler()
/* 244:    */   {
/* 245:399 */     return new DefaultProxyAuthenticationHandler();
/* 246:    */   }
/* 247:    */   
/* 248:    */   protected CookieStore createCookieStore()
/* 249:    */   {
/* 250:404 */     return new BasicCookieStore();
/* 251:    */   }
/* 252:    */   
/* 253:    */   protected CredentialsProvider createCredentialsProvider()
/* 254:    */   {
/* 255:409 */     return new BasicCredentialsProvider();
/* 256:    */   }
/* 257:    */   
/* 258:    */   protected HttpRoutePlanner createHttpRoutePlanner()
/* 259:    */   {
/* 260:414 */     return new DefaultHttpRoutePlanner(getConnectionManager().getSchemeRegistry());
/* 261:    */   }
/* 262:    */   
/* 263:    */   protected UserTokenHandler createUserTokenHandler()
/* 264:    */   {
/* 265:419 */     return new DefaultUserTokenHandler();
/* 266:    */   }
/* 267:    */   
/* 268:    */   public final synchronized HttpParams getParams()
/* 269:    */   {
/* 270:425 */     if (this.defaultParams == null) {
/* 271:426 */       this.defaultParams = createHttpParams();
/* 272:    */     }
/* 273:428 */     return this.defaultParams;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public synchronized void setParams(HttpParams params)
/* 277:    */   {
/* 278:439 */     this.defaultParams = params;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public final synchronized ClientConnectionManager getConnectionManager()
/* 282:    */   {
/* 283:444 */     if (this.connManager == null) {
/* 284:445 */       this.connManager = createClientConnectionManager();
/* 285:    */     }
/* 286:447 */     return this.connManager;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public final synchronized HttpRequestExecutor getRequestExecutor()
/* 290:    */   {
/* 291:452 */     if (this.requestExec == null) {
/* 292:453 */       this.requestExec = createRequestExecutor();
/* 293:    */     }
/* 294:455 */     return this.requestExec;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public final synchronized AuthSchemeRegistry getAuthSchemes()
/* 298:    */   {
/* 299:460 */     if (this.supportedAuthSchemes == null) {
/* 300:461 */       this.supportedAuthSchemes = createAuthSchemeRegistry();
/* 301:    */     }
/* 302:463 */     return this.supportedAuthSchemes;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public synchronized void setAuthSchemes(AuthSchemeRegistry authSchemeRegistry)
/* 306:    */   {
/* 307:468 */     this.supportedAuthSchemes = authSchemeRegistry;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public final synchronized CookieSpecRegistry getCookieSpecs()
/* 311:    */   {
/* 312:473 */     if (this.supportedCookieSpecs == null) {
/* 313:474 */       this.supportedCookieSpecs = createCookieSpecRegistry();
/* 314:    */     }
/* 315:476 */     return this.supportedCookieSpecs;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public synchronized void setCookieSpecs(CookieSpecRegistry cookieSpecRegistry)
/* 319:    */   {
/* 320:481 */     this.supportedCookieSpecs = cookieSpecRegistry;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public final synchronized ConnectionReuseStrategy getConnectionReuseStrategy()
/* 324:    */   {
/* 325:486 */     if (this.reuseStrategy == null) {
/* 326:487 */       this.reuseStrategy = createConnectionReuseStrategy();
/* 327:    */     }
/* 328:489 */     return this.reuseStrategy;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public synchronized void setReuseStrategy(ConnectionReuseStrategy reuseStrategy)
/* 332:    */   {
/* 333:494 */     this.reuseStrategy = reuseStrategy;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public final synchronized ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy()
/* 337:    */   {
/* 338:499 */     if (this.keepAliveStrategy == null) {
/* 339:500 */       this.keepAliveStrategy = createConnectionKeepAliveStrategy();
/* 340:    */     }
/* 341:502 */     return this.keepAliveStrategy;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public synchronized void setKeepAliveStrategy(ConnectionKeepAliveStrategy keepAliveStrategy)
/* 345:    */   {
/* 346:507 */     this.keepAliveStrategy = keepAliveStrategy;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public final synchronized HttpRequestRetryHandler getHttpRequestRetryHandler()
/* 350:    */   {
/* 351:512 */     if (this.retryHandler == null) {
/* 352:513 */       this.retryHandler = createHttpRequestRetryHandler();
/* 353:    */     }
/* 354:515 */     return this.retryHandler;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public synchronized void setHttpRequestRetryHandler(HttpRequestRetryHandler retryHandler)
/* 358:    */   {
/* 359:520 */     this.retryHandler = retryHandler;
/* 360:    */   }
/* 361:    */   
/* 362:    */   @Deprecated
/* 363:    */   public final synchronized RedirectHandler getRedirectHandler()
/* 364:    */   {
/* 365:526 */     return createRedirectHandler();
/* 366:    */   }
/* 367:    */   
/* 368:    */   @Deprecated
/* 369:    */   public synchronized void setRedirectHandler(RedirectHandler redirectHandler)
/* 370:    */   {
/* 371:532 */     this.redirectStrategy = new DefaultRedirectStrategyAdaptor(redirectHandler);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public final synchronized RedirectStrategy getRedirectStrategy()
/* 375:    */   {
/* 376:539 */     if (this.redirectStrategy == null) {
/* 377:540 */       this.redirectStrategy = new DefaultRedirectStrategy();
/* 378:    */     }
/* 379:542 */     return this.redirectStrategy;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public synchronized void setRedirectStrategy(RedirectStrategy redirectStrategy)
/* 383:    */   {
/* 384:549 */     this.redirectStrategy = redirectStrategy;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public final synchronized AuthenticationHandler getTargetAuthenticationHandler()
/* 388:    */   {
/* 389:554 */     if (this.targetAuthHandler == null) {
/* 390:555 */       this.targetAuthHandler = createTargetAuthenticationHandler();
/* 391:    */     }
/* 392:557 */     return this.targetAuthHandler;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public synchronized void setTargetAuthenticationHandler(AuthenticationHandler targetAuthHandler)
/* 396:    */   {
/* 397:563 */     this.targetAuthHandler = targetAuthHandler;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public final synchronized AuthenticationHandler getProxyAuthenticationHandler()
/* 401:    */   {
/* 402:568 */     if (this.proxyAuthHandler == null) {
/* 403:569 */       this.proxyAuthHandler = createProxyAuthenticationHandler();
/* 404:    */     }
/* 405:571 */     return this.proxyAuthHandler;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public synchronized void setProxyAuthenticationHandler(AuthenticationHandler proxyAuthHandler)
/* 409:    */   {
/* 410:577 */     this.proxyAuthHandler = proxyAuthHandler;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public final synchronized CookieStore getCookieStore()
/* 414:    */   {
/* 415:582 */     if (this.cookieStore == null) {
/* 416:583 */       this.cookieStore = createCookieStore();
/* 417:    */     }
/* 418:585 */     return this.cookieStore;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public synchronized void setCookieStore(CookieStore cookieStore)
/* 422:    */   {
/* 423:590 */     this.cookieStore = cookieStore;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public final synchronized CredentialsProvider getCredentialsProvider()
/* 427:    */   {
/* 428:595 */     if (this.credsProvider == null) {
/* 429:596 */       this.credsProvider = createCredentialsProvider();
/* 430:    */     }
/* 431:598 */     return this.credsProvider;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public synchronized void setCredentialsProvider(CredentialsProvider credsProvider)
/* 435:    */   {
/* 436:603 */     this.credsProvider = credsProvider;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public final synchronized HttpRoutePlanner getRoutePlanner()
/* 440:    */   {
/* 441:608 */     if (this.routePlanner == null) {
/* 442:609 */       this.routePlanner = createHttpRoutePlanner();
/* 443:    */     }
/* 444:611 */     return this.routePlanner;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public synchronized void setRoutePlanner(HttpRoutePlanner routePlanner)
/* 448:    */   {
/* 449:616 */     this.routePlanner = routePlanner;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public final synchronized UserTokenHandler getUserTokenHandler()
/* 453:    */   {
/* 454:621 */     if (this.userTokenHandler == null) {
/* 455:622 */       this.userTokenHandler = createUserTokenHandler();
/* 456:    */     }
/* 457:624 */     return this.userTokenHandler;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public synchronized void setUserTokenHandler(UserTokenHandler userTokenHandler)
/* 461:    */   {
/* 462:629 */     this.userTokenHandler = userTokenHandler;
/* 463:    */   }
/* 464:    */   
/* 465:    */   protected final synchronized BasicHttpProcessor getHttpProcessor()
/* 466:    */   {
/* 467:634 */     if (this.mutableProcessor == null) {
/* 468:635 */       this.mutableProcessor = createHttpProcessor();
/* 469:    */     }
/* 470:637 */     return this.mutableProcessor;
/* 471:    */   }
/* 472:    */   
/* 473:    */   private final synchronized HttpProcessor getProtocolProcessor()
/* 474:    */   {
/* 475:642 */     if (this.protocolProcessor == null)
/* 476:    */     {
/* 477:644 */       BasicHttpProcessor proc = getHttpProcessor();
/* 478:    */       
/* 479:646 */       int reqc = proc.getRequestInterceptorCount();
/* 480:647 */       HttpRequestInterceptor[] reqinterceptors = new HttpRequestInterceptor[reqc];
/* 481:648 */       for (int i = 0; i < reqc; i++) {
/* 482:649 */         reqinterceptors[i] = proc.getRequestInterceptor(i);
/* 483:    */       }
/* 484:651 */       int resc = proc.getResponseInterceptorCount();
/* 485:652 */       HttpResponseInterceptor[] resinterceptors = new HttpResponseInterceptor[resc];
/* 486:653 */       for (int i = 0; i < resc; i++) {
/* 487:654 */         resinterceptors[i] = proc.getResponseInterceptor(i);
/* 488:    */       }
/* 489:656 */       this.protocolProcessor = new ImmutableHttpProcessor(reqinterceptors, resinterceptors);
/* 490:    */     }
/* 491:658 */     return this.protocolProcessor;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public synchronized int getResponseInterceptorCount()
/* 495:    */   {
/* 496:663 */     return getHttpProcessor().getResponseInterceptorCount();
/* 497:    */   }
/* 498:    */   
/* 499:    */   public synchronized HttpResponseInterceptor getResponseInterceptor(int index)
/* 500:    */   {
/* 501:668 */     return getHttpProcessor().getResponseInterceptor(index);
/* 502:    */   }
/* 503:    */   
/* 504:    */   public synchronized HttpRequestInterceptor getRequestInterceptor(int index)
/* 505:    */   {
/* 506:673 */     return getHttpProcessor().getRequestInterceptor(index);
/* 507:    */   }
/* 508:    */   
/* 509:    */   public synchronized int getRequestInterceptorCount()
/* 510:    */   {
/* 511:678 */     return getHttpProcessor().getRequestInterceptorCount();
/* 512:    */   }
/* 513:    */   
/* 514:    */   public synchronized void addResponseInterceptor(HttpResponseInterceptor itcp)
/* 515:    */   {
/* 516:683 */     getHttpProcessor().addInterceptor(itcp);
/* 517:684 */     this.protocolProcessor = null;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public synchronized void addResponseInterceptor(HttpResponseInterceptor itcp, int index)
/* 521:    */   {
/* 522:689 */     getHttpProcessor().addInterceptor(itcp, index);
/* 523:690 */     this.protocolProcessor = null;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public synchronized void clearResponseInterceptors()
/* 527:    */   {
/* 528:695 */     getHttpProcessor().clearResponseInterceptors();
/* 529:696 */     this.protocolProcessor = null;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public synchronized void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> clazz)
/* 533:    */   {
/* 534:701 */     getHttpProcessor().removeResponseInterceptorByClass(clazz);
/* 535:702 */     this.protocolProcessor = null;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public synchronized void addRequestInterceptor(HttpRequestInterceptor itcp)
/* 539:    */   {
/* 540:707 */     getHttpProcessor().addInterceptor(itcp);
/* 541:708 */     this.protocolProcessor = null;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public synchronized void addRequestInterceptor(HttpRequestInterceptor itcp, int index)
/* 545:    */   {
/* 546:713 */     getHttpProcessor().addInterceptor(itcp, index);
/* 547:714 */     this.protocolProcessor = null;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public synchronized void clearRequestInterceptors()
/* 551:    */   {
/* 552:719 */     getHttpProcessor().clearRequestInterceptors();
/* 553:720 */     this.protocolProcessor = null;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public synchronized void removeRequestInterceptorByClass(Class<? extends HttpRequestInterceptor> clazz)
/* 557:    */   {
/* 558:725 */     getHttpProcessor().removeRequestInterceptorByClass(clazz);
/* 559:726 */     this.protocolProcessor = null;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public final HttpResponse execute(HttpUriRequest request)
/* 563:    */     throws IOException, ClientProtocolException
/* 564:    */   {
/* 565:732 */     return execute(request, (HttpContext)null);
/* 566:    */   }
/* 567:    */   
/* 568:    */   public final HttpResponse execute(HttpUriRequest request, HttpContext context)
/* 569:    */     throws IOException, ClientProtocolException
/* 570:    */   {
/* 571:749 */     if (request == null) {
/* 572:750 */       throw new IllegalArgumentException("Request must not be null.");
/* 573:    */     }
/* 574:754 */     return execute(determineTarget(request), request, context);
/* 575:    */   }
/* 576:    */   
/* 577:    */   private static HttpHost determineTarget(HttpUriRequest request)
/* 578:    */     throws ClientProtocolException
/* 579:    */   {
/* 580:760 */     HttpHost target = null;
/* 581:    */     
/* 582:762 */     URI requestURI = request.getURI();
/* 583:763 */     if (requestURI.isAbsolute())
/* 584:    */     {
/* 585:764 */       target = URIUtils.extractHost(requestURI);
/* 586:765 */       if (target == null) {
/* 587:766 */         throw new ClientProtocolException("URI does not specify a valid host name: " + requestURI);
/* 588:    */       }
/* 589:    */     }
/* 590:770 */     return target;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public final HttpResponse execute(HttpHost target, HttpRequest request)
/* 594:    */     throws IOException, ClientProtocolException
/* 595:    */   {
/* 596:776 */     return execute(target, request, (HttpContext)null);
/* 597:    */   }
/* 598:    */   
/* 599:    */   public final HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
/* 600:    */     throws IOException, ClientProtocolException
/* 601:    */   {
/* 602:783 */     if (request == null) {
/* 603:784 */       throw new IllegalArgumentException("Request must not be null.");
/* 604:    */     }
/* 605:790 */     HttpContext execContext = null;
/* 606:791 */     RequestDirector director = null;
/* 607:795 */     synchronized (this)
/* 608:    */     {
/* 609:797 */       HttpContext defaultContext = createHttpContext();
/* 610:798 */       if (context == null) {
/* 611:799 */         execContext = defaultContext;
/* 612:    */       } else {
/* 613:801 */         execContext = new DefaultedHttpContext(context, defaultContext);
/* 614:    */       }
/* 615:804 */       director = createClientRequestDirector(getRequestExecutor(), getConnectionManager(), getConnectionReuseStrategy(), getConnectionKeepAliveStrategy(), getRoutePlanner(), getProtocolProcessor(), getHttpRequestRetryHandler(), getRedirectStrategy(), getTargetAuthenticationHandler(), getProxyAuthenticationHandler(), getUserTokenHandler(), determineParams(request));
/* 616:    */     }
/* 617:    */     try
/* 618:    */     {
/* 619:820 */       return director.execute(target, request, execContext);
/* 620:    */     }
/* 621:    */     catch (HttpException httpException)
/* 622:    */     {
/* 623:822 */       throw new ClientProtocolException(httpException);
/* 624:    */     }
/* 625:    */   }
/* 626:    */   
/* 627:    */   @Deprecated
/* 628:    */   protected RequestDirector createClientRequestDirector(HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectHandler redirectHandler, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler stateHandler, HttpParams params)
/* 629:    */   {
/* 630:840 */     return new DefaultRequestDirector(requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectHandler, targetAuthHandler, proxyAuthHandler, stateHandler, params);
/* 631:    */   }
/* 632:    */   
/* 633:    */   protected RequestDirector createClientRequestDirector(HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectStrategy redirectStrategy, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler stateHandler, HttpParams params)
/* 634:    */   {
/* 635:871 */     return new DefaultRequestDirector(this.log, requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectStrategy, targetAuthHandler, proxyAuthHandler, stateHandler, params);
/* 636:    */   }
/* 637:    */   
/* 638:    */   protected HttpParams determineParams(HttpRequest req)
/* 639:    */   {
/* 640:902 */     return new ClientParamsStack(null, getParams(), req.getParams(), null);
/* 641:    */   }
/* 642:    */   
/* 643:    */   public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
/* 644:    */     throws IOException, ClientProtocolException
/* 645:    */   {
/* 646:910 */     return execute(request, responseHandler, null);
/* 647:    */   }
/* 648:    */   
/* 649:    */   public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context)
/* 650:    */     throws IOException, ClientProtocolException
/* 651:    */   {
/* 652:918 */     HttpHost target = determineTarget(request);
/* 653:919 */     return execute(target, request, responseHandler, context);
/* 654:    */   }
/* 655:    */   
/* 656:    */   public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler)
/* 657:    */     throws IOException, ClientProtocolException
/* 658:    */   {
/* 659:927 */     return execute(target, request, responseHandler, null);
/* 660:    */   }
/* 661:    */   
/* 662:    */   public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context)
/* 663:    */     throws IOException, ClientProtocolException
/* 664:    */   {
/* 665:936 */     if (responseHandler == null) {
/* 666:937 */       throw new IllegalArgumentException("Response handler must not be null.");
/* 667:    */     }
/* 668:941 */     HttpResponse response = execute(target, request, context);
/* 669:    */     T result;
/* 670:    */     try
/* 671:    */     {
/* 672:945 */       result = responseHandler.handleResponse(response);
/* 673:    */     }
/* 674:    */     catch (Throwable t)
/* 675:    */     {
/* 676:947 */       HttpEntity entity = response.getEntity();
/* 677:    */       try
/* 678:    */       {
/* 679:949 */         EntityUtils.consume(entity);
/* 680:    */       }
/* 681:    */       catch (Exception t2)
/* 682:    */       {
/* 683:953 */         this.log.warn("Error consuming content after an exception.", t2);
/* 684:    */       }
/* 685:956 */       if ((t instanceof Error)) {
/* 686:957 */         throw ((Error)t);
/* 687:    */       }
/* 688:960 */       if ((t instanceof RuntimeException)) {
/* 689:961 */         throw ((RuntimeException)t);
/* 690:    */       }
/* 691:964 */       if ((t instanceof IOException)) {
/* 692:965 */         throw ((IOException)t);
/* 693:    */       }
/* 694:968 */       throw new UndeclaredThrowableException(t);
/* 695:    */     }
/* 696:973 */     HttpEntity entity = response.getEntity();
/* 697:974 */     EntityUtils.consume(entity);
/* 698:975 */     return result;
/* 699:    */   }
/* 700:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.AbstractHttpClient
 * JD-Core Version:    0.7.0.1
 */