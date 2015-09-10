/*    1:     */ package org.apache.http.impl.client;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.InterruptedIOException;
/*    5:     */ import java.net.URI;
/*    6:     */ import java.net.URISyntaxException;
/*    7:     */ import java.util.Locale;
/*    8:     */ import java.util.Map;
/*    9:     */ import java.util.concurrent.TimeUnit;
/*   10:     */ import org.apache.commons.logging.Log;
/*   11:     */ import org.apache.commons.logging.LogFactory;
/*   12:     */ import org.apache.http.ConnectionReuseStrategy;
/*   13:     */ import org.apache.http.Header;
/*   14:     */ import org.apache.http.HttpEntity;
/*   15:     */ import org.apache.http.HttpEntityEnclosingRequest;
/*   16:     */ import org.apache.http.HttpException;
/*   17:     */ import org.apache.http.HttpHost;
/*   18:     */ import org.apache.http.HttpRequest;
/*   19:     */ import org.apache.http.HttpResponse;
/*   20:     */ import org.apache.http.ProtocolException;
/*   21:     */ import org.apache.http.ProtocolVersion;
/*   22:     */ import org.apache.http.RequestLine;
/*   23:     */ import org.apache.http.StatusLine;
/*   24:     */ import org.apache.http.annotation.NotThreadSafe;
/*   25:     */ import org.apache.http.auth.AuthScheme;
/*   26:     */ import org.apache.http.auth.AuthScope;
/*   27:     */ import org.apache.http.auth.AuthState;
/*   28:     */ import org.apache.http.auth.AuthenticationException;
/*   29:     */ import org.apache.http.auth.Credentials;
/*   30:     */ import org.apache.http.auth.MalformedChallengeException;
/*   31:     */ import org.apache.http.client.AuthenticationHandler;
/*   32:     */ import org.apache.http.client.CredentialsProvider;
/*   33:     */ import org.apache.http.client.HttpRequestRetryHandler;
/*   34:     */ import org.apache.http.client.NonRepeatableRequestException;
/*   35:     */ import org.apache.http.client.RedirectException;
/*   36:     */ import org.apache.http.client.RedirectHandler;
/*   37:     */ import org.apache.http.client.RedirectStrategy;
/*   38:     */ import org.apache.http.client.RequestDirector;
/*   39:     */ import org.apache.http.client.UserTokenHandler;
/*   40:     */ import org.apache.http.client.methods.AbortableHttpRequest;
/*   41:     */ import org.apache.http.client.methods.HttpUriRequest;
/*   42:     */ import org.apache.http.client.params.HttpClientParams;
/*   43:     */ import org.apache.http.client.utils.URIUtils;
/*   44:     */ import org.apache.http.conn.BasicManagedEntity;
/*   45:     */ import org.apache.http.conn.ClientConnectionManager;
/*   46:     */ import org.apache.http.conn.ClientConnectionRequest;
/*   47:     */ import org.apache.http.conn.ConnectionKeepAliveStrategy;
/*   48:     */ import org.apache.http.conn.ManagedClientConnection;
/*   49:     */ import org.apache.http.conn.params.ConnManagerParams;
/*   50:     */ import org.apache.http.conn.routing.BasicRouteDirector;
/*   51:     */ import org.apache.http.conn.routing.HttpRoute;
/*   52:     */ import org.apache.http.conn.routing.HttpRouteDirector;
/*   53:     */ import org.apache.http.conn.routing.HttpRoutePlanner;
/*   54:     */ import org.apache.http.conn.scheme.Scheme;
/*   55:     */ import org.apache.http.conn.scheme.SchemeRegistry;
/*   56:     */ import org.apache.http.entity.BufferedHttpEntity;
/*   57:     */ import org.apache.http.impl.conn.ConnectionShutdownException;
/*   58:     */ import org.apache.http.message.BasicHttpRequest;
/*   59:     */ import org.apache.http.params.HttpConnectionParams;
/*   60:     */ import org.apache.http.params.HttpParams;
/*   61:     */ import org.apache.http.params.HttpProtocolParams;
/*   62:     */ import org.apache.http.protocol.HttpContext;
/*   63:     */ import org.apache.http.protocol.HttpProcessor;
/*   64:     */ import org.apache.http.protocol.HttpRequestExecutor;
/*   65:     */ import org.apache.http.util.EntityUtils;
/*   66:     */ 
/*   67:     */ @NotThreadSafe
/*   68:     */ public class DefaultRequestDirector
/*   69:     */   implements RequestDirector
/*   70:     */ {
/*   71:     */   private final Log log;
/*   72:     */   protected final ClientConnectionManager connManager;
/*   73:     */   protected final HttpRoutePlanner routePlanner;
/*   74:     */   protected final ConnectionReuseStrategy reuseStrategy;
/*   75:     */   protected final ConnectionKeepAliveStrategy keepAliveStrategy;
/*   76:     */   protected final HttpRequestExecutor requestExec;
/*   77:     */   protected final HttpProcessor httpProcessor;
/*   78:     */   protected final HttpRequestRetryHandler retryHandler;
/*   79:     */   @Deprecated
/*   80: 163 */   protected final RedirectHandler redirectHandler = null;
/*   81:     */   protected final RedirectStrategy redirectStrategy;
/*   82:     */   protected final AuthenticationHandler targetAuthHandler;
/*   83:     */   protected final AuthenticationHandler proxyAuthHandler;
/*   84:     */   protected final UserTokenHandler userTokenHandler;
/*   85:     */   protected final HttpParams params;
/*   86:     */   protected ManagedClientConnection managedConn;
/*   87:     */   protected final AuthState targetAuthState;
/*   88:     */   protected final AuthState proxyAuthState;
/*   89:     */   private int execCount;
/*   90:     */   private int redirectCount;
/*   91:     */   private int maxRedirects;
/*   92:     */   private HttpHost virtualHost;
/*   93:     */   
/*   94:     */   @Deprecated
/*   95:     */   public DefaultRequestDirector(HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectHandler redirectHandler, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler userTokenHandler, HttpParams params)
/*   96:     */   {
/*   97: 209 */     this(LogFactory.getLog(DefaultRequestDirector.class), requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, new DefaultRedirectStrategyAdaptor(redirectHandler), targetAuthHandler, proxyAuthHandler, userTokenHandler, params);
/*   98:     */   }
/*   99:     */   
/*  100:     */   public DefaultRequestDirector(Log log, HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectStrategy redirectStrategy, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler userTokenHandler, HttpParams params)
/*  101:     */   {
/*  102: 234 */     if (log == null) {
/*  103: 235 */       throw new IllegalArgumentException("Log may not be null.");
/*  104:     */     }
/*  105: 238 */     if (requestExec == null) {
/*  106: 239 */       throw new IllegalArgumentException("Request executor may not be null.");
/*  107:     */     }
/*  108: 242 */     if (conman == null) {
/*  109: 243 */       throw new IllegalArgumentException("Client connection manager may not be null.");
/*  110:     */     }
/*  111: 246 */     if (reustrat == null) {
/*  112: 247 */       throw new IllegalArgumentException("Connection reuse strategy may not be null.");
/*  113:     */     }
/*  114: 250 */     if (kastrat == null) {
/*  115: 251 */       throw new IllegalArgumentException("Connection keep alive strategy may not be null.");
/*  116:     */     }
/*  117: 254 */     if (rouplan == null) {
/*  118: 255 */       throw new IllegalArgumentException("Route planner may not be null.");
/*  119:     */     }
/*  120: 258 */     if (httpProcessor == null) {
/*  121: 259 */       throw new IllegalArgumentException("HTTP protocol processor may not be null.");
/*  122:     */     }
/*  123: 262 */     if (retryHandler == null) {
/*  124: 263 */       throw new IllegalArgumentException("HTTP request retry handler may not be null.");
/*  125:     */     }
/*  126: 266 */     if (redirectStrategy == null) {
/*  127: 267 */       throw new IllegalArgumentException("Redirect strategy may not be null.");
/*  128:     */     }
/*  129: 270 */     if (targetAuthHandler == null) {
/*  130: 271 */       throw new IllegalArgumentException("Target authentication handler may not be null.");
/*  131:     */     }
/*  132: 274 */     if (proxyAuthHandler == null) {
/*  133: 275 */       throw new IllegalArgumentException("Proxy authentication handler may not be null.");
/*  134:     */     }
/*  135: 278 */     if (userTokenHandler == null) {
/*  136: 279 */       throw new IllegalArgumentException("User token handler may not be null.");
/*  137:     */     }
/*  138: 282 */     if (params == null) {
/*  139: 283 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  140:     */     }
/*  141: 286 */     this.log = log;
/*  142: 287 */     this.requestExec = requestExec;
/*  143: 288 */     this.connManager = conman;
/*  144: 289 */     this.reuseStrategy = reustrat;
/*  145: 290 */     this.keepAliveStrategy = kastrat;
/*  146: 291 */     this.routePlanner = rouplan;
/*  147: 292 */     this.httpProcessor = httpProcessor;
/*  148: 293 */     this.retryHandler = retryHandler;
/*  149: 294 */     this.redirectStrategy = redirectStrategy;
/*  150: 295 */     this.targetAuthHandler = targetAuthHandler;
/*  151: 296 */     this.proxyAuthHandler = proxyAuthHandler;
/*  152: 297 */     this.userTokenHandler = userTokenHandler;
/*  153: 298 */     this.params = params;
/*  154:     */     
/*  155: 300 */     this.managedConn = null;
/*  156:     */     
/*  157: 302 */     this.execCount = 0;
/*  158: 303 */     this.redirectCount = 0;
/*  159: 304 */     this.maxRedirects = this.params.getIntParameter("http.protocol.max-redirects", 100);
/*  160: 305 */     this.targetAuthState = new AuthState();
/*  161: 306 */     this.proxyAuthState = new AuthState();
/*  162:     */   }
/*  163:     */   
/*  164:     */   private RequestWrapper wrapRequest(HttpRequest request)
/*  165:     */     throws ProtocolException
/*  166:     */   {
/*  167: 312 */     if ((request instanceof HttpEntityEnclosingRequest)) {
/*  168: 313 */       return new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)request);
/*  169:     */     }
/*  170: 316 */     return new RequestWrapper(request);
/*  171:     */   }
/*  172:     */   
/*  173:     */   protected void rewriteRequestURI(RequestWrapper request, HttpRoute route)
/*  174:     */     throws ProtocolException
/*  175:     */   {
/*  176:     */     try
/*  177:     */     {
/*  178: 327 */       URI uri = request.getURI();
/*  179: 328 */       if ((route.getProxyHost() != null) && (!route.isTunnelled()))
/*  180:     */       {
/*  181: 330 */         if (!uri.isAbsolute())
/*  182:     */         {
/*  183: 331 */           HttpHost target = route.getTargetHost();
/*  184: 332 */           uri = URIUtils.rewriteURI(uri, target);
/*  185: 333 */           request.setURI(uri);
/*  186:     */         }
/*  187:     */       }
/*  188: 337 */       else if (uri.isAbsolute())
/*  189:     */       {
/*  190: 338 */         uri = URIUtils.rewriteURI(uri, null);
/*  191: 339 */         request.setURI(uri);
/*  192:     */       }
/*  193:     */     }
/*  194:     */     catch (URISyntaxException ex)
/*  195:     */     {
/*  196: 344 */       throw new ProtocolException("Invalid URI: " + request.getRequestLine().getUri(), ex);
/*  197:     */     }
/*  198:     */   }
/*  199:     */   
/*  200:     */   public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
/*  201:     */     throws HttpException, IOException
/*  202:     */   {
/*  203: 355 */     HttpRequest orig = request;
/*  204: 356 */     RequestWrapper origWrapper = wrapRequest(orig);
/*  205: 357 */     origWrapper.setParams(this.params);
/*  206: 358 */     HttpRoute origRoute = determineRoute(target, origWrapper, context);
/*  207:     */     
/*  208: 360 */     this.virtualHost = ((HttpHost)orig.getParams().getParameter("http.virtual-host"));
/*  209: 364 */     if ((this.virtualHost != null) && (this.virtualHost.getPort() == -1))
/*  210:     */     {
/*  211: 366 */       int port = target.getPort();
/*  212: 367 */       if (port != -1) {
/*  213: 368 */         this.virtualHost = new HttpHost(this.virtualHost.getHostName(), port, this.virtualHost.getSchemeName());
/*  214:     */       }
/*  215:     */     }
/*  216: 372 */     RoutedRequest roureq = new RoutedRequest(origWrapper, origRoute);
/*  217:     */     
/*  218: 374 */     boolean reuse = false;
/*  219: 375 */     boolean done = false;
/*  220:     */     try
/*  221:     */     {
/*  222: 377 */       HttpResponse response = null;
/*  223: 378 */       while (!done)
/*  224:     */       {
/*  225: 384 */         RequestWrapper wrapper = roureq.getRequest();
/*  226: 385 */         HttpRoute route = roureq.getRoute();
/*  227: 386 */         response = null;
/*  228:     */         
/*  229:     */ 
/*  230: 389 */         Object userToken = context.getAttribute("http.user-token");
/*  231: 392 */         if (this.managedConn == null)
/*  232:     */         {
/*  233: 393 */           ClientConnectionRequest connRequest = this.connManager.requestConnection(route, userToken);
/*  234: 395 */           if ((orig instanceof AbortableHttpRequest)) {
/*  235: 396 */             ((AbortableHttpRequest)orig).setConnectionRequest(connRequest);
/*  236:     */           }
/*  237: 399 */           long timeout = ConnManagerParams.getTimeout(this.params);
/*  238:     */           try
/*  239:     */           {
/*  240: 401 */             this.managedConn = connRequest.getConnection(timeout, TimeUnit.MILLISECONDS);
/*  241:     */           }
/*  242:     */           catch (InterruptedException interrupted)
/*  243:     */           {
/*  244: 403 */             InterruptedIOException iox = new InterruptedIOException();
/*  245: 404 */             iox.initCause(interrupted);
/*  246: 405 */             throw iox;
/*  247:     */           }
/*  248: 408 */           if (HttpConnectionParams.isStaleCheckingEnabled(this.params)) {
/*  249: 410 */             if (this.managedConn.isOpen())
/*  250:     */             {
/*  251: 411 */               this.log.debug("Stale connection check");
/*  252: 412 */               if (this.managedConn.isStale())
/*  253:     */               {
/*  254: 413 */                 this.log.debug("Stale connection detected");
/*  255: 414 */                 this.managedConn.close();
/*  256:     */               }
/*  257:     */             }
/*  258:     */           }
/*  259:     */         }
/*  260: 420 */         if ((orig instanceof AbortableHttpRequest)) {
/*  261: 421 */           ((AbortableHttpRequest)orig).setReleaseTrigger(this.managedConn);
/*  262:     */         }
/*  263:     */         try
/*  264:     */         {
/*  265: 425 */           tryConnect(roureq, context);
/*  266:     */         }
/*  267:     */         catch (TunnelRefusedException ex)
/*  268:     */         {
/*  269: 427 */           if (this.log.isDebugEnabled()) {
/*  270: 428 */             this.log.debug(ex.getMessage());
/*  271:     */           }
/*  272: 430 */           response = ex.getResponse();
/*  273: 431 */           break;
/*  274:     */         }
/*  275: 435 */         wrapper.resetHeaders();
/*  276:     */         
/*  277:     */ 
/*  278: 438 */         rewriteRequestURI(wrapper, route);
/*  279:     */         
/*  280:     */ 
/*  281: 441 */         target = this.virtualHost;
/*  282: 443 */         if (target == null) {
/*  283: 444 */           target = route.getTargetHost();
/*  284:     */         }
/*  285: 447 */         HttpHost proxy = route.getProxyHost();
/*  286:     */         
/*  287:     */ 
/*  288: 450 */         context.setAttribute("http.target_host", target);
/*  289:     */         
/*  290: 452 */         context.setAttribute("http.proxy_host", proxy);
/*  291:     */         
/*  292: 454 */         context.setAttribute("http.connection", this.managedConn);
/*  293:     */         
/*  294: 456 */         context.setAttribute("http.auth.target-scope", this.targetAuthState);
/*  295:     */         
/*  296: 458 */         context.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
/*  297:     */         
/*  298:     */ 
/*  299:     */ 
/*  300: 462 */         this.requestExec.preProcess(wrapper, this.httpProcessor, context);
/*  301:     */         
/*  302: 464 */         response = tryExecute(roureq, context);
/*  303: 465 */         if (response != null)
/*  304:     */         {
/*  305: 471 */           response.setParams(this.params);
/*  306: 472 */           this.requestExec.postProcess(response, this.httpProcessor, context);
/*  307:     */           
/*  308:     */ 
/*  309:     */ 
/*  310: 476 */           reuse = this.reuseStrategy.keepAlive(response, context);
/*  311: 477 */           if (reuse)
/*  312:     */           {
/*  313: 479 */             long duration = this.keepAliveStrategy.getKeepAliveDuration(response, context);
/*  314: 480 */             if (this.log.isDebugEnabled())
/*  315:     */             {
/*  316:     */               String s;
/*  317:     */               String s;
/*  318: 482 */               if (duration > 0L) {
/*  319: 483 */                 s = "for " + duration + " " + TimeUnit.MILLISECONDS;
/*  320:     */               } else {
/*  321: 485 */                 s = "indefinitely";
/*  322:     */               }
/*  323: 487 */               this.log.debug("Connection can be kept alive " + s);
/*  324:     */             }
/*  325: 489 */             this.managedConn.setIdleDuration(duration, TimeUnit.MILLISECONDS);
/*  326:     */           }
/*  327: 492 */           RoutedRequest followup = handleResponse(roureq, response, context);
/*  328: 493 */           if (followup == null)
/*  329:     */           {
/*  330: 494 */             done = true;
/*  331:     */           }
/*  332:     */           else
/*  333:     */           {
/*  334: 496 */             if (reuse)
/*  335:     */             {
/*  336: 498 */               HttpEntity entity = response.getEntity();
/*  337: 499 */               EntityUtils.consume(entity);
/*  338:     */               
/*  339:     */ 
/*  340: 502 */               this.managedConn.markReusable();
/*  341:     */             }
/*  342:     */             else
/*  343:     */             {
/*  344: 504 */               this.managedConn.close();
/*  345: 505 */               invalidateAuthIfSuccessful(this.proxyAuthState);
/*  346: 506 */               invalidateAuthIfSuccessful(this.targetAuthState);
/*  347:     */             }
/*  348: 509 */             if (!followup.getRoute().equals(roureq.getRoute())) {
/*  349: 510 */               releaseConnection();
/*  350:     */             }
/*  351: 512 */             roureq = followup;
/*  352:     */           }
/*  353: 515 */           if ((this.managedConn != null) && (userToken == null))
/*  354:     */           {
/*  355: 516 */             userToken = this.userTokenHandler.getUserToken(context);
/*  356: 517 */             context.setAttribute("http.user-token", userToken);
/*  357: 518 */             if (userToken != null) {
/*  358: 519 */               this.managedConn.setState(userToken);
/*  359:     */             }
/*  360:     */           }
/*  361:     */         }
/*  362:     */       }
/*  363: 527 */       if ((response == null) || (response.getEntity() == null) || (!response.getEntity().isStreaming()))
/*  364:     */       {
/*  365: 530 */         if (reuse) {
/*  366: 531 */           this.managedConn.markReusable();
/*  367:     */         }
/*  368: 532 */         releaseConnection();
/*  369:     */       }
/*  370:     */       else
/*  371:     */       {
/*  372: 535 */         HttpEntity entity = response.getEntity();
/*  373: 536 */         entity = new BasicManagedEntity(entity, this.managedConn, reuse);
/*  374: 537 */         response.setEntity(entity);
/*  375:     */       }
/*  376: 540 */       return response;
/*  377:     */     }
/*  378:     */     catch (ConnectionShutdownException ex)
/*  379:     */     {
/*  380: 543 */       InterruptedIOException ioex = new InterruptedIOException("Connection has been shut down");
/*  381:     */       
/*  382: 545 */       ioex.initCause(ex);
/*  383: 546 */       throw ioex;
/*  384:     */     }
/*  385:     */     catch (HttpException ex)
/*  386:     */     {
/*  387: 548 */       abortConnection();
/*  388: 549 */       throw ex;
/*  389:     */     }
/*  390:     */     catch (IOException ex)
/*  391:     */     {
/*  392: 551 */       abortConnection();
/*  393: 552 */       throw ex;
/*  394:     */     }
/*  395:     */     catch (RuntimeException ex)
/*  396:     */     {
/*  397: 554 */       abortConnection();
/*  398: 555 */       throw ex;
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   private void tryConnect(RoutedRequest req, HttpContext context)
/*  403:     */     throws HttpException, IOException
/*  404:     */   {
/*  405: 565 */     HttpRoute route = req.getRoute();
/*  406:     */     
/*  407: 567 */     int connectCount = 0;
/*  408:     */     for (;;)
/*  409:     */     {
/*  410: 570 */       connectCount++;
/*  411:     */       try
/*  412:     */       {
/*  413: 572 */         if (!this.managedConn.isOpen()) {
/*  414: 573 */           this.managedConn.open(route, context, this.params);
/*  415:     */         } else {
/*  416: 575 */           this.managedConn.setSocketTimeout(HttpConnectionParams.getSoTimeout(this.params));
/*  417:     */         }
/*  418: 577 */         establishRoute(route, context);
/*  419:     */       }
/*  420:     */       catch (IOException ex)
/*  421:     */       {
/*  422:     */         try
/*  423:     */         {
/*  424: 581 */           this.managedConn.close();
/*  425:     */         }
/*  426:     */         catch (IOException ignore) {}
/*  427: 584 */         if (this.retryHandler.retryRequest(ex, connectCount, context))
/*  428:     */         {
/*  429: 585 */           if (this.log.isInfoEnabled()) {
/*  430: 586 */             this.log.info("I/O exception (" + ex.getClass().getName() + ") caught when connecting to the target host: " + ex.getMessage());
/*  431:     */           }
/*  432: 590 */           if (this.log.isDebugEnabled()) {
/*  433: 591 */             this.log.debug(ex.getMessage(), ex);
/*  434:     */           }
/*  435: 593 */           this.log.info("Retrying connect");
/*  436:     */         }
/*  437:     */         else
/*  438:     */         {
/*  439: 595 */           throw ex;
/*  440:     */         }
/*  441:     */       }
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   private HttpResponse tryExecute(RoutedRequest req, HttpContext context)
/*  446:     */     throws HttpException, IOException
/*  447:     */   {
/*  448: 606 */     RequestWrapper wrapper = req.getRequest();
/*  449: 607 */     HttpRoute route = req.getRoute();
/*  450: 608 */     HttpResponse response = null;
/*  451:     */     
/*  452: 610 */     Exception retryReason = null;
/*  453:     */     for (;;)
/*  454:     */     {
/*  455: 613 */       this.execCount += 1;
/*  456:     */       
/*  457: 615 */       wrapper.incrementExecCount();
/*  458: 616 */       if (!wrapper.isRepeatable())
/*  459:     */       {
/*  460: 617 */         this.log.debug("Cannot retry non-repeatable request");
/*  461: 618 */         if (retryReason != null) {
/*  462: 619 */           throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.", retryReason);
/*  463:     */         }
/*  464: 623 */         throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
/*  465:     */       }
/*  466:     */       try
/*  467:     */       {
/*  468: 629 */         if (!this.managedConn.isOpen()) {
/*  469: 632 */           if (!route.isTunnelled())
/*  470:     */           {
/*  471: 633 */             this.log.debug("Reopening the direct connection.");
/*  472: 634 */             this.managedConn.open(route, context, this.params);
/*  473:     */           }
/*  474:     */           else
/*  475:     */           {
/*  476: 637 */             this.log.debug("Proxied connection. Need to start over.");
/*  477: 638 */             break;
/*  478:     */           }
/*  479:     */         }
/*  480: 642 */         if (this.log.isDebugEnabled()) {
/*  481: 643 */           this.log.debug("Attempt " + this.execCount + " to execute request");
/*  482:     */         }
/*  483: 645 */         response = this.requestExec.execute(wrapper, this.managedConn, context);
/*  484:     */       }
/*  485:     */       catch (IOException ex)
/*  486:     */       {
/*  487: 649 */         this.log.debug("Closing the connection.");
/*  488:     */         try
/*  489:     */         {
/*  490: 651 */           this.managedConn.close();
/*  491:     */         }
/*  492:     */         catch (IOException ignore) {}
/*  493: 654 */         if (this.retryHandler.retryRequest(ex, wrapper.getExecCount(), context))
/*  494:     */         {
/*  495: 655 */           if (this.log.isInfoEnabled()) {
/*  496: 656 */             this.log.info("I/O exception (" + ex.getClass().getName() + ") caught when processing request: " + ex.getMessage());
/*  497:     */           }
/*  498: 660 */           if (this.log.isDebugEnabled()) {
/*  499: 661 */             this.log.debug(ex.getMessage(), ex);
/*  500:     */           }
/*  501: 663 */           this.log.info("Retrying request");
/*  502: 664 */           retryReason = ex;
/*  503:     */         }
/*  504:     */         else
/*  505:     */         {
/*  506: 666 */           throw ex;
/*  507:     */         }
/*  508:     */       }
/*  509:     */     }
/*  510: 670 */     return response;
/*  511:     */   }
/*  512:     */   
/*  513:     */   protected void releaseConnection()
/*  514:     */   {
/*  515:     */     try
/*  516:     */     {
/*  517: 683 */       this.managedConn.releaseConnection();
/*  518:     */     }
/*  519:     */     catch (IOException ignored)
/*  520:     */     {
/*  521: 685 */       this.log.debug("IOException releasing connection", ignored);
/*  522:     */     }
/*  523: 687 */     this.managedConn = null;
/*  524:     */   }
/*  525:     */   
/*  526:     */   protected HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
/*  527:     */     throws HttpException
/*  528:     */   {
/*  529: 712 */     if (target == null) {
/*  530: 713 */       target = (HttpHost)request.getParams().getParameter("http.default-host");
/*  531:     */     }
/*  532: 716 */     if (target == null) {
/*  533: 717 */       throw new IllegalStateException("Target host must not be null, or set in parameters.");
/*  534:     */     }
/*  535: 721 */     return this.routePlanner.determineRoute(target, request, context);
/*  536:     */   }
/*  537:     */   
/*  538:     */   protected void establishRoute(HttpRoute route, HttpContext context)
/*  539:     */     throws HttpException, IOException
/*  540:     */   {
/*  541: 737 */     HttpRouteDirector rowdy = new BasicRouteDirector();
/*  542:     */     int step;
/*  543:     */     do
/*  544:     */     {
/*  545: 740 */       HttpRoute fact = this.managedConn.getRoute();
/*  546: 741 */       step = rowdy.nextStep(route, fact);
/*  547: 743 */       switch (step)
/*  548:     */       {
/*  549:     */       case 1: 
/*  550:     */       case 2: 
/*  551: 747 */         this.managedConn.open(route, context, this.params);
/*  552: 748 */         break;
/*  553:     */       case 3: 
/*  554: 751 */         boolean secure = createTunnelToTarget(route, context);
/*  555: 752 */         this.log.debug("Tunnel to target created.");
/*  556: 753 */         this.managedConn.tunnelTarget(secure, this.params);
/*  557: 754 */         break;
/*  558:     */       case 4: 
/*  559: 761 */         int hop = fact.getHopCount() - 1;
/*  560: 762 */         boolean secure = createTunnelToProxy(route, hop, context);
/*  561: 763 */         this.log.debug("Tunnel to proxy created.");
/*  562: 764 */         this.managedConn.tunnelProxy(route.getHopTarget(hop), secure, this.params);
/*  563:     */         
/*  564: 766 */         break;
/*  565:     */       case 5: 
/*  566: 770 */         this.managedConn.layerProtocol(context, this.params);
/*  567: 771 */         break;
/*  568:     */       case -1: 
/*  569: 774 */         throw new HttpException("Unable to establish route: planned = " + route + "; current = " + fact);
/*  570:     */       case 0: 
/*  571:     */         break;
/*  572:     */       default: 
/*  573: 780 */         throw new IllegalStateException("Unknown step indicator " + step + " from RouteDirector.");
/*  574:     */       }
/*  575: 784 */     } while (step > 0);
/*  576:     */   }
/*  577:     */   
/*  578:     */   protected boolean createTunnelToTarget(HttpRoute route, HttpContext context)
/*  579:     */     throws HttpException, IOException
/*  580:     */   {
/*  581: 812 */     HttpHost proxy = route.getProxyHost();
/*  582: 813 */     HttpHost target = route.getTargetHost();
/*  583: 814 */     HttpResponse response = null;
/*  584:     */     
/*  585: 816 */     boolean done = false;
/*  586: 817 */     while (!done)
/*  587:     */     {
/*  588: 819 */       done = true;
/*  589: 821 */       if (!this.managedConn.isOpen()) {
/*  590: 822 */         this.managedConn.open(route, context, this.params);
/*  591:     */       }
/*  592: 825 */       HttpRequest connect = createConnectRequest(route, context);
/*  593: 826 */       connect.setParams(this.params);
/*  594:     */       
/*  595:     */ 
/*  596: 829 */       context.setAttribute("http.target_host", target);
/*  597:     */       
/*  598: 831 */       context.setAttribute("http.proxy_host", proxy);
/*  599:     */       
/*  600: 833 */       context.setAttribute("http.connection", this.managedConn);
/*  601:     */       
/*  602: 835 */       context.setAttribute("http.auth.target-scope", this.targetAuthState);
/*  603:     */       
/*  604: 837 */       context.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
/*  605:     */       
/*  606: 839 */       context.setAttribute("http.request", connect);
/*  607:     */       
/*  608:     */ 
/*  609: 842 */       this.requestExec.preProcess(connect, this.httpProcessor, context);
/*  610:     */       
/*  611: 844 */       response = this.requestExec.execute(connect, this.managedConn, context);
/*  612:     */       
/*  613: 846 */       response.setParams(this.params);
/*  614: 847 */       this.requestExec.postProcess(response, this.httpProcessor, context);
/*  615:     */       
/*  616: 849 */       int status = response.getStatusLine().getStatusCode();
/*  617: 850 */       if (status < 200) {
/*  618: 851 */         throw new HttpException("Unexpected response to CONNECT request: " + response.getStatusLine());
/*  619:     */       }
/*  620: 855 */       CredentialsProvider credsProvider = (CredentialsProvider)context.getAttribute("http.auth.credentials-provider");
/*  621: 858 */       if ((credsProvider != null) && (HttpClientParams.isAuthenticating(this.params))) {
/*  622: 859 */         if (this.proxyAuthHandler.isAuthenticationRequested(response, context))
/*  623:     */         {
/*  624: 861 */           this.log.debug("Proxy requested authentication");
/*  625: 862 */           Map<String, Header> challenges = this.proxyAuthHandler.getChallenges(response, context);
/*  626:     */           try
/*  627:     */           {
/*  628: 865 */             processChallenges(challenges, this.proxyAuthState, this.proxyAuthHandler, response, context);
/*  629:     */           }
/*  630:     */           catch (AuthenticationException ex)
/*  631:     */           {
/*  632: 869 */             if (this.log.isWarnEnabled())
/*  633:     */             {
/*  634: 870 */               this.log.warn("Authentication error: " + ex.getMessage());
/*  635: 871 */               break;
/*  636:     */             }
/*  637:     */           }
/*  638: 874 */           updateAuthState(this.proxyAuthState, proxy, credsProvider);
/*  639: 876 */           if (this.proxyAuthState.getCredentials() != null)
/*  640:     */           {
/*  641: 877 */             done = false;
/*  642: 880 */             if (this.reuseStrategy.keepAlive(response, context))
/*  643:     */             {
/*  644: 881 */               this.log.debug("Connection kept alive");
/*  645:     */               
/*  646: 883 */               HttpEntity entity = response.getEntity();
/*  647: 884 */               EntityUtils.consume(entity);
/*  648:     */             }
/*  649:     */             else
/*  650:     */             {
/*  651: 886 */               this.managedConn.close();
/*  652:     */             }
/*  653:     */           }
/*  654:     */         }
/*  655:     */         else
/*  656:     */         {
/*  657: 893 */           this.proxyAuthState.setAuthScope(null);
/*  658:     */         }
/*  659:     */       }
/*  660:     */     }
/*  661: 898 */     int status = response.getStatusLine().getStatusCode();
/*  662: 900 */     if (status > 299)
/*  663:     */     {
/*  664: 903 */       HttpEntity entity = response.getEntity();
/*  665: 904 */       if (entity != null) {
/*  666: 905 */         response.setEntity(new BufferedHttpEntity(entity));
/*  667:     */       }
/*  668: 908 */       this.managedConn.close();
/*  669: 909 */       throw new TunnelRefusedException("CONNECT refused by proxy: " + response.getStatusLine(), response);
/*  670:     */     }
/*  671: 913 */     this.managedConn.markReusable();
/*  672:     */     
/*  673:     */ 
/*  674:     */ 
/*  675:     */ 
/*  676:     */ 
/*  677: 919 */     return false;
/*  678:     */   }
/*  679:     */   
/*  680:     */   protected boolean createTunnelToProxy(HttpRoute route, int hop, HttpContext context)
/*  681:     */     throws HttpException, IOException
/*  682:     */   {
/*  683: 955 */     throw new HttpException("Proxy chains are not supported.");
/*  684:     */   }
/*  685:     */   
/*  686:     */   protected HttpRequest createConnectRequest(HttpRoute route, HttpContext context)
/*  687:     */   {
/*  688: 975 */     HttpHost target = route.getTargetHost();
/*  689:     */     
/*  690: 977 */     String host = target.getHostName();
/*  691: 978 */     int port = target.getPort();
/*  692: 979 */     if (port < 0)
/*  693:     */     {
/*  694: 980 */       Scheme scheme = this.connManager.getSchemeRegistry().getScheme(target.getSchemeName());
/*  695:     */       
/*  696: 982 */       port = scheme.getDefaultPort();
/*  697:     */     }
/*  698: 985 */     StringBuilder buffer = new StringBuilder(host.length() + 6);
/*  699: 986 */     buffer.append(host);
/*  700: 987 */     buffer.append(':');
/*  701: 988 */     buffer.append(Integer.toString(port));
/*  702:     */     
/*  703: 990 */     String authority = buffer.toString();
/*  704: 991 */     ProtocolVersion ver = HttpProtocolParams.getVersion(this.params);
/*  705: 992 */     HttpRequest req = new BasicHttpRequest("CONNECT", authority, ver);
/*  706:     */     
/*  707:     */ 
/*  708: 995 */     return req;
/*  709:     */   }
/*  710:     */   
/*  711:     */   protected RoutedRequest handleResponse(RoutedRequest roureq, HttpResponse response, HttpContext context)
/*  712:     */     throws HttpException, IOException
/*  713:     */   {
/*  714:1017 */     HttpRoute route = roureq.getRoute();
/*  715:1018 */     RequestWrapper request = roureq.getRequest();
/*  716:     */     
/*  717:1020 */     HttpParams params = request.getParams();
/*  718:1021 */     if ((HttpClientParams.isRedirecting(params)) && (this.redirectStrategy.isRedirected(request, response, context)))
/*  719:     */     {
/*  720:1024 */       if (this.redirectCount >= this.maxRedirects) {
/*  721:1025 */         throw new RedirectException("Maximum redirects (" + this.maxRedirects + ") exceeded");
/*  722:     */       }
/*  723:1028 */       this.redirectCount += 1;
/*  724:     */       
/*  725:     */ 
/*  726:1031 */       this.virtualHost = null;
/*  727:     */       
/*  728:1033 */       HttpUriRequest redirect = this.redirectStrategy.getRedirect(request, response, context);
/*  729:1034 */       HttpRequest orig = request.getOriginal();
/*  730:1035 */       redirect.setHeaders(orig.getAllHeaders());
/*  731:     */       
/*  732:1037 */       URI uri = redirect.getURI();
/*  733:1038 */       if (uri.getHost() == null) {
/*  734:1039 */         throw new ProtocolException("Redirect URI does not specify a valid host name: " + uri);
/*  735:     */       }
/*  736:1042 */       HttpHost newTarget = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
/*  737:     */       
/*  738:     */ 
/*  739:     */ 
/*  740:     */ 
/*  741:     */ 
/*  742:1048 */       this.targetAuthState.setAuthScope(null);
/*  743:1049 */       this.proxyAuthState.setAuthScope(null);
/*  744:1052 */       if (!route.getTargetHost().equals(newTarget))
/*  745:     */       {
/*  746:1053 */         this.targetAuthState.invalidate();
/*  747:1054 */         AuthScheme authScheme = this.proxyAuthState.getAuthScheme();
/*  748:1055 */         if ((authScheme != null) && (authScheme.isConnectionBased())) {
/*  749:1056 */           this.proxyAuthState.invalidate();
/*  750:     */         }
/*  751:     */       }
/*  752:1060 */       RequestWrapper wrapper = wrapRequest(redirect);
/*  753:1061 */       wrapper.setParams(params);
/*  754:     */       
/*  755:1063 */       HttpRoute newRoute = determineRoute(newTarget, wrapper, context);
/*  756:1064 */       RoutedRequest newRequest = new RoutedRequest(wrapper, newRoute);
/*  757:1066 */       if (this.log.isDebugEnabled()) {
/*  758:1067 */         this.log.debug("Redirecting to '" + uri + "' via " + newRoute);
/*  759:     */       }
/*  760:1070 */       return newRequest;
/*  761:     */     }
/*  762:1073 */     CredentialsProvider credsProvider = (CredentialsProvider)context.getAttribute("http.auth.credentials-provider");
/*  763:1076 */     if ((credsProvider != null) && (HttpClientParams.isAuthenticating(params)))
/*  764:     */     {
/*  765:1078 */       if (this.targetAuthHandler.isAuthenticationRequested(response, context))
/*  766:     */       {
/*  767:1080 */         HttpHost target = (HttpHost)context.getAttribute("http.target_host");
/*  768:1082 */         if (target == null) {
/*  769:1083 */           target = route.getTargetHost();
/*  770:     */         }
/*  771:1086 */         this.log.debug("Target requested authentication");
/*  772:1087 */         Map<String, Header> challenges = this.targetAuthHandler.getChallenges(response, context);
/*  773:     */         try
/*  774:     */         {
/*  775:1090 */           processChallenges(challenges, this.targetAuthState, this.targetAuthHandler, response, context);
/*  776:     */         }
/*  777:     */         catch (AuthenticationException ex)
/*  778:     */         {
/*  779:1094 */           if (this.log.isWarnEnabled())
/*  780:     */           {
/*  781:1095 */             this.log.warn("Authentication error: " + ex.getMessage());
/*  782:1096 */             return null;
/*  783:     */           }
/*  784:     */         }
/*  785:1099 */         updateAuthState(this.targetAuthState, target, credsProvider);
/*  786:1101 */         if (this.targetAuthState.getCredentials() != null) {
/*  787:1103 */           return roureq;
/*  788:     */         }
/*  789:1105 */         return null;
/*  790:     */       }
/*  791:1109 */       this.targetAuthState.setAuthScope(null);
/*  792:1112 */       if (this.proxyAuthHandler.isAuthenticationRequested(response, context))
/*  793:     */       {
/*  794:1114 */         HttpHost proxy = route.getProxyHost();
/*  795:     */         
/*  796:1116 */         this.log.debug("Proxy requested authentication");
/*  797:1117 */         Map<String, Header> challenges = this.proxyAuthHandler.getChallenges(response, context);
/*  798:     */         try
/*  799:     */         {
/*  800:1120 */           processChallenges(challenges, this.proxyAuthState, this.proxyAuthHandler, response, context);
/*  801:     */         }
/*  802:     */         catch (AuthenticationException ex)
/*  803:     */         {
/*  804:1124 */           if (this.log.isWarnEnabled())
/*  805:     */           {
/*  806:1125 */             this.log.warn("Authentication error: " + ex.getMessage());
/*  807:1126 */             return null;
/*  808:     */           }
/*  809:     */         }
/*  810:1129 */         updateAuthState(this.proxyAuthState, proxy, credsProvider);
/*  811:1131 */         if (this.proxyAuthState.getCredentials() != null) {
/*  812:1133 */           return roureq;
/*  813:     */         }
/*  814:1135 */         return null;
/*  815:     */       }
/*  816:1139 */       this.proxyAuthState.setAuthScope(null);
/*  817:     */     }
/*  818:1142 */     return null;
/*  819:     */   }
/*  820:     */   
/*  821:     */   private void abortConnection()
/*  822:     */   {
/*  823:1152 */     ManagedClientConnection mcc = this.managedConn;
/*  824:1153 */     if (mcc != null)
/*  825:     */     {
/*  826:1156 */       this.managedConn = null;
/*  827:     */       try
/*  828:     */       {
/*  829:1158 */         mcc.abortConnection();
/*  830:     */       }
/*  831:     */       catch (IOException ex)
/*  832:     */       {
/*  833:1160 */         if (this.log.isDebugEnabled()) {
/*  834:1161 */           this.log.debug(ex.getMessage(), ex);
/*  835:     */         }
/*  836:     */       }
/*  837:     */       try
/*  838:     */       {
/*  839:1166 */         mcc.releaseConnection();
/*  840:     */       }
/*  841:     */       catch (IOException ignored)
/*  842:     */       {
/*  843:1168 */         this.log.debug("Error releasing connection", ignored);
/*  844:     */       }
/*  845:     */     }
/*  846:     */   }
/*  847:     */   
/*  848:     */   private void processChallenges(Map<String, Header> challenges, AuthState authState, AuthenticationHandler authHandler, HttpResponse response, HttpContext context)
/*  849:     */     throws MalformedChallengeException, AuthenticationException
/*  850:     */   {
/*  851:1182 */     AuthScheme authScheme = authState.getAuthScheme();
/*  852:1183 */     if (authScheme == null)
/*  853:     */     {
/*  854:1185 */       authScheme = authHandler.selectScheme(challenges, response, context);
/*  855:1186 */       authState.setAuthScheme(authScheme);
/*  856:     */     }
/*  857:1188 */     String id = authScheme.getSchemeName();
/*  858:     */     
/*  859:1190 */     Header challenge = (Header)challenges.get(id.toLowerCase(Locale.ENGLISH));
/*  860:1191 */     if (challenge == null) {
/*  861:1192 */       throw new AuthenticationException(id + " authorization challenge expected, but not found");
/*  862:     */     }
/*  863:1195 */     authScheme.processChallenge(challenge);
/*  864:1196 */     this.log.debug("Authorization challenge processed");
/*  865:     */   }
/*  866:     */   
/*  867:     */   private void updateAuthState(AuthState authState, HttpHost host, CredentialsProvider credsProvider)
/*  868:     */   {
/*  869:1205 */     if (!authState.isValid()) {
/*  870:1206 */       return;
/*  871:     */     }
/*  872:1209 */     String hostname = host.getHostName();
/*  873:1210 */     int port = host.getPort();
/*  874:1211 */     if (port < 0)
/*  875:     */     {
/*  876:1212 */       Scheme scheme = this.connManager.getSchemeRegistry().getScheme(host);
/*  877:1213 */       port = scheme.getDefaultPort();
/*  878:     */     }
/*  879:1216 */     AuthScheme authScheme = authState.getAuthScheme();
/*  880:1217 */     AuthScope authScope = new AuthScope(hostname, port, authScheme.getRealm(), authScheme.getSchemeName());
/*  881:1223 */     if (this.log.isDebugEnabled()) {
/*  882:1224 */       this.log.debug("Authentication scope: " + authScope);
/*  883:     */     }
/*  884:1226 */     Credentials creds = authState.getCredentials();
/*  885:1227 */     if (creds == null)
/*  886:     */     {
/*  887:1228 */       creds = credsProvider.getCredentials(authScope);
/*  888:1229 */       if (this.log.isDebugEnabled()) {
/*  889:1230 */         if (creds != null) {
/*  890:1231 */           this.log.debug("Found credentials");
/*  891:     */         } else {
/*  892:1233 */           this.log.debug("Credentials not found");
/*  893:     */         }
/*  894:     */       }
/*  895:     */     }
/*  896:1237 */     else if (authScheme.isComplete())
/*  897:     */     {
/*  898:1238 */       this.log.debug("Authentication failed");
/*  899:1239 */       creds = null;
/*  900:     */     }
/*  901:1242 */     authState.setAuthScope(authScope);
/*  902:1243 */     authState.setCredentials(creds);
/*  903:     */   }
/*  904:     */   
/*  905:     */   private void invalidateAuthIfSuccessful(AuthState authState)
/*  906:     */   {
/*  907:1247 */     AuthScheme authscheme = authState.getAuthScheme();
/*  908:1248 */     if ((authscheme != null) && (authscheme.isConnectionBased()) && (authscheme.isComplete()) && (authState.getCredentials() != null)) {
/*  909:1252 */       authState.invalidate();
/*  910:     */     }
/*  911:     */   }
/*  912:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultRequestDirector
 * JD-Core Version:    0.7.0.1
 */