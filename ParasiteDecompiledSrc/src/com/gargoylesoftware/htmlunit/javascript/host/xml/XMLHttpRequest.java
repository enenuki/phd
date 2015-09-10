/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.xml;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.AjaxController;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   5:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   6:    */ import com.gargoylesoftware.htmlunit.HttpMethod;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*  13:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  14:    */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJob;
/*  15:    */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
/*  16:    */ import com.gargoylesoftware.htmlunit.javascript.host.ActiveXObject;
/*  17:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  18:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*  19:    */ import com.gargoylesoftware.htmlunit.util.WebResponseWrapper;
/*  20:    */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*  21:    */ import java.io.IOException;
/*  22:    */ import java.io.InputStream;
/*  23:    */ import java.net.MalformedURLException;
/*  24:    */ import java.net.URL;
/*  25:    */ import java.util.Arrays;
/*  26:    */ import java.util.Collection;
/*  27:    */ import java.util.Collections;
/*  28:    */ import java.util.List;
/*  29:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  30:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*  31:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*  32:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  33:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  34:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  35:    */ import org.apache.commons.lang.ArrayUtils;
/*  36:    */ import org.apache.commons.logging.Log;
/*  37:    */ import org.apache.commons.logging.LogFactory;
/*  38:    */ import org.apache.http.auth.UsernamePasswordCredentials;
/*  39:    */ 
/*  40:    */ public class XMLHttpRequest
/*  41:    */   extends SimpleScriptable
/*  42:    */ {
/*  43: 67 */   private static final Log LOG = LogFactory.getLog(XMLHttpRequest.class);
/*  44:    */   public static final int STATE_UNINITIALIZED = 0;
/*  45:    */   public static final int STATE_LOADING = 1;
/*  46:    */   public static final int STATE_LOADED = 2;
/*  47:    */   public static final int STATE_INTERACTIVE = 3;
/*  48:    */   public static final int STATE_COMPLETED = 4;
/*  49: 80 */   private static final String[] ALL_PROPERTIES_ = { "onreadystatechange", "readyState", "responseText", "responseXML", "status", "statusText", "abort", "getAllResponseHeaders", "getResponseHeader", "open", "send", "setRequestHeader" };
/*  50: 84 */   private static Collection<String> PROHIBITED_HEADERS_ = Arrays.asList(new String[] { "accept-charset", "accept-encoding", "connection", "content-length", "cookie", "cookie2", "content-transfer-encoding", "date", "expect", "host", "keep-alive", "referer", "te", "trailer", "transfer-encoding", "upgrade", "user-agent", "via" });
/*  51:    */   private int state_;
/*  52:    */   private Function stateChangeHandler_;
/*  53:    */   private Function loadHandler_;
/*  54:    */   private Function errorHandler_;
/*  55:    */   private WebRequest webRequest_;
/*  56:    */   private boolean async_;
/*  57:    */   private int threadID_;
/*  58:    */   private WebResponse webResponse_;
/*  59:    */   private String overriddenMimeType_;
/*  60:    */   private HtmlPage containingPage_;
/*  61:    */   private boolean caseSensitiveProperties_;
/*  62:    */   
/*  63:    */   public XMLHttpRequest()
/*  64:    */   {
/*  65:104 */     this(true);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public XMLHttpRequest(boolean caseSensitiveProperties)
/*  69:    */   {
/*  70:112 */     this.caseSensitiveProperties_ = caseSensitiveProperties;
/*  71:113 */     this.state_ = 0;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void jsConstructor() {}
/*  75:    */   
/*  76:    */   public Function jsxGet_onreadystatechange()
/*  77:    */   {
/*  78:128 */     return this.stateChangeHandler_;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void jsxSet_onreadystatechange(Function stateChangeHandler)
/*  82:    */   {
/*  83:136 */     this.stateChangeHandler_ = stateChangeHandler;
/*  84:137 */     if (this.state_ == 1) {
/*  85:138 */       setState(this.state_, null);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void setState(int state, Context context)
/*  90:    */   {
/*  91:149 */     this.state_ = state;
/*  92:    */     
/*  93:    */ 
/*  94:152 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_135);
/*  95:153 */     if ((this.stateChangeHandler_ != null) && ((ie) || (this.async_)))
/*  96:    */     {
/*  97:154 */       if (context == null) {
/*  98:155 */         context = Context.getCurrentContext();
/*  99:    */       }
/* 100:157 */       Scriptable scope = this.stateChangeHandler_.getParentScope();
/* 101:158 */       JavaScriptEngine jsEngine = this.containingPage_.getWebClient().getJavaScriptEngine();
/* 102:    */       int nbExecutions;
/* 103:    */       int nbExecutions;
/* 104:161 */       if ((this.async_) && (1 == state)) {
/* 105:164 */         nbExecutions = 2;
/* 106:    */       } else {
/* 107:167 */         nbExecutions = 1;
/* 108:    */       }
/* 109:    */       Scriptable thisValue;
/* 110:    */       Scriptable thisValue;
/* 111:171 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.XMLHTTPREQUEST_HANDLER_THIS_IS_FUNCTION)) {
/* 112:172 */         thisValue = this.stateChangeHandler_;
/* 113:    */       } else {
/* 114:175 */         thisValue = this;
/* 115:    */       }
/* 116:177 */       for (int i = 0; i < nbExecutions; i++)
/* 117:    */       {
/* 118:178 */         if (LOG.isDebugEnabled()) {
/* 119:179 */           LOG.debug("Calling onreadystatechange handler for state " + state);
/* 120:    */         }
/* 121:181 */         jsEngine.callFunction(this.containingPage_, this.stateChangeHandler_, scope, thisValue, ArrayUtils.EMPTY_OBJECT_ARRAY);
/* 122:183 */         if (LOG.isDebugEnabled())
/* 123:    */         {
/* 124:184 */           LOG.debug("onreadystatechange handler: " + context.decompileFunction(this.stateChangeHandler_, 4));
/* 125:185 */           LOG.debug("Calling onreadystatechange handler for state " + state + ". Done.");
/* 126:    */         }
/* 127:    */       }
/* 128:    */     }
/* 129:191 */     if ((!ie) && (this.loadHandler_ != null) && (state == 4))
/* 130:    */     {
/* 131:192 */       if (context == null) {
/* 132:193 */         context = Context.getCurrentContext();
/* 133:    */       }
/* 134:195 */       Scriptable scope = this.loadHandler_.getParentScope();
/* 135:196 */       JavaScriptEngine jsEngine = this.containingPage_.getWebClient().getJavaScriptEngine();
/* 136:197 */       jsEngine.callFunction(this.containingPage_, this.loadHandler_, scope, this, ArrayUtils.EMPTY_OBJECT_ARRAY);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Function jsxGet_onload()
/* 141:    */   {
/* 142:206 */     return this.loadHandler_;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void jsxSet_onload(Function loadHandler)
/* 146:    */   {
/* 147:214 */     this.loadHandler_ = loadHandler;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Function jsxGet_onerror()
/* 151:    */   {
/* 152:222 */     return this.errorHandler_;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void jsxSet_onerror(Function errorHandler)
/* 156:    */   {
/* 157:230 */     this.errorHandler_ = errorHandler;
/* 158:    */   }
/* 159:    */   
/* 160:    */   private void processError(Context context)
/* 161:    */   {
/* 162:239 */     if ((this.errorHandler_ != null) && (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_136)))
/* 163:    */     {
/* 164:240 */       if (context == null) {
/* 165:241 */         context = Context.getCurrentContext();
/* 166:    */       }
/* 167:243 */       Scriptable scope = this.errorHandler_.getParentScope();
/* 168:244 */       JavaScriptEngine jsEngine = this.containingPage_.getWebClient().getJavaScriptEngine();
/* 169:246 */       if (LOG.isDebugEnabled()) {
/* 170:247 */         LOG.debug("Calling onerror handler");
/* 171:    */       }
/* 172:249 */       jsEngine.callFunction(this.containingPage_, this.errorHandler_, this, scope, ArrayUtils.EMPTY_OBJECT_ARRAY);
/* 173:250 */       if (LOG.isDebugEnabled())
/* 174:    */       {
/* 175:251 */         LOG.debug("onerror handler: " + context.decompileFunction(this.errorHandler_, 4));
/* 176:252 */         LOG.debug("Calling onerror handler done.");
/* 177:    */       }
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int jsxGet_readyState()
/* 182:    */   {
/* 183:269 */     return this.state_;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String jsxGet_responseText()
/* 187:    */   {
/* 188:277 */     if (this.webResponse_ != null) {
/* 189:278 */       return this.webResponse_.getContentAsString();
/* 190:    */     }
/* 191:280 */     if (LOG.isDebugEnabled()) {
/* 192:281 */       LOG.debug("XMLHttpRequest.responseText was retrieved before the response was available.");
/* 193:    */     }
/* 194:283 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Object jsxGet_responseXML()
/* 198:    */   {
/* 199:291 */     if (this.webResponse_ == null) {
/* 200:292 */       return null;
/* 201:    */     }
/* 202:294 */     String contentType = this.webResponse_.getContentType();
/* 203:295 */     if ((contentType.length() == 0) || (contentType.contains("xml"))) {
/* 204:    */       try
/* 205:    */       {
/* 206:297 */         XmlPage page = new XmlPage(this.webResponse_, getWindow().getWebWindow());
/* 207:    */         XMLDocument doc;
/* 208:    */         XMLDocument doc;
/* 209:299 */         if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_137))
/* 210:    */         {
/* 211:300 */           doc = ActiveXObject.buildXMLDocument(null);
/* 212:    */         }
/* 213:    */         else
/* 214:    */         {
/* 215:303 */           doc = new XMLDocument();
/* 216:304 */           doc.setPrototype(getPrototype(doc.getClass()));
/* 217:    */         }
/* 218:306 */         doc.setParentScope(getWindow());
/* 219:307 */         doc.setDomNode(page);
/* 220:308 */         return doc;
/* 221:    */       }
/* 222:    */       catch (IOException e)
/* 223:    */       {
/* 224:311 */         LOG.warn("Failed parsing XML document " + this.webResponse_.getWebRequest().getUrl() + ": " + e.getMessage());
/* 225:    */         
/* 226:313 */         return null;
/* 227:    */       }
/* 228:    */     }
/* 229:316 */     if (LOG.isDebugEnabled()) {
/* 230:317 */       LOG.debug("XMLHttpRequest.responseXML was called but the response is " + this.webResponse_.getContentType());
/* 231:    */     }
/* 232:320 */     return null;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public int jsxGet_status()
/* 236:    */   {
/* 237:329 */     if (this.webResponse_ != null) {
/* 238:330 */       return this.webResponse_.getStatusCode();
/* 239:    */     }
/* 240:332 */     LOG.error("XMLHttpRequest.status was retrieved before the response was available.");
/* 241:333 */     return 0;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String jsxGet_statusText()
/* 245:    */   {
/* 246:341 */     if (this.webResponse_ != null) {
/* 247:342 */       return this.webResponse_.getStatusMessage();
/* 248:    */     }
/* 249:344 */     LOG.error("XMLHttpRequest.statusText was retrieved before the response was available.");
/* 250:345 */     return null;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void jsxFunction_abort()
/* 254:    */   {
/* 255:352 */     getWindow().getWebWindow().getJobManager().stopJob(this.threadID_);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String jsxFunction_getAllResponseHeaders()
/* 259:    */   {
/* 260:360 */     if (this.webResponse_ != null)
/* 261:    */     {
/* 262:361 */       StringBuilder buffer = new StringBuilder();
/* 263:362 */       for (NameValuePair header : this.webResponse_.getResponseHeaders()) {
/* 264:363 */         buffer.append(header.getName()).append(": ").append(header.getValue()).append("\n");
/* 265:    */       }
/* 266:365 */       return buffer.toString();
/* 267:    */     }
/* 268:367 */     LOG.error("XMLHttpRequest.getAllResponseHeaders() was called before the response was available.");
/* 269:368 */     return null;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String jsxFunction_getResponseHeader(String headerName)
/* 273:    */   {
/* 274:377 */     if (this.webResponse_ != null) {
/* 275:378 */       return this.webResponse_.getResponseHeaderValue(headerName);
/* 276:    */     }
/* 277:381 */     return null;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void jsxFunction_open(String method, Object urlParam, boolean async, Object user, Object password)
/* 281:    */   {
/* 282:394 */     if ((urlParam == null) || ("".equals(urlParam))) {
/* 283:395 */       throw Context.reportRuntimeError("URL for XHR.open can't be empty!");
/* 284:    */     }
/* 285:398 */     String url = Context.toString(urlParam);
/* 286:    */     
/* 287:    */ 
/* 288:401 */     this.containingPage_ = ((HtmlPage)getWindow().getWebWindow().getEnclosedPage());
/* 289:    */     try
/* 290:    */     {
/* 291:404 */       URL fullUrl = this.containingPage_.getFullyQualifiedUrl(url);
/* 292:405 */       URL originUrl = this.containingPage_.getWebResponse().getWebRequest().getUrl();
/* 293:406 */       if (!isSameOrigin(originUrl, fullUrl)) {
/* 294:407 */         throw Context.reportRuntimeError("Access to restricted URI denied");
/* 295:    */       }
/* 296:410 */       WebRequest request = new WebRequest(fullUrl);
/* 297:411 */       request.setCharset("UTF-8");
/* 298:412 */       request.setAdditionalHeader("Referer", this.containingPage_.getWebResponse().getWebRequest().getUrl().toExternalForm());
/* 299:    */       
/* 300:414 */       HttpMethod submitMethod = HttpMethod.valueOf(method.toUpperCase());
/* 301:415 */       request.setHttpMethod(submitMethod);
/* 302:416 */       if ((Undefined.instance != user) || (Undefined.instance != password))
/* 303:    */       {
/* 304:417 */         String userCred = null;
/* 305:418 */         String passwordCred = "";
/* 306:419 */         if (Undefined.instance != user) {
/* 307:420 */           userCred = user.toString();
/* 308:    */         }
/* 309:422 */         if (Undefined.instance != password) {
/* 310:423 */           passwordCred = user.toString();
/* 311:    */         }
/* 312:427 */         if (null != userCred) {
/* 313:428 */           request.setCredentials(new UsernamePasswordCredentials(userCred, passwordCred));
/* 314:    */         }
/* 315:    */       }
/* 316:431 */       this.webRequest_ = request;
/* 317:    */     }
/* 318:    */     catch (MalformedURLException e)
/* 319:    */     {
/* 320:434 */       LOG.error("Unable to initialize XMLHttpRequest using malformed URL '" + url + "'.");
/* 321:435 */       return;
/* 322:    */     }
/* 323:438 */     this.async_ = async;
/* 324:    */     
/* 325:440 */     setState(1, null);
/* 326:    */   }
/* 327:    */   
/* 328:    */   private boolean isSameOrigin(URL originUrl, URL newUrl)
/* 329:    */   {
/* 330:444 */     if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_138)) && ("about".equals(newUrl.getProtocol()))) {
/* 331:446 */       return true;
/* 332:    */     }
/* 333:448 */     return originUrl.getHost().equals(newUrl.getHost());
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void jsxFunction_send(Object content)
/* 337:    */   {
/* 338:456 */     prepareRequest(content);
/* 339:    */     
/* 340:458 */     WebClient client = getWindow().getWebWindow().getWebClient();
/* 341:459 */     AjaxController ajaxController = client.getAjaxController();
/* 342:460 */     HtmlPage page = (HtmlPage)getWindow().getWebWindow().getEnclosedPage();
/* 343:461 */     boolean synchron = ajaxController.processSynchron(page, this.webRequest_, this.async_);
/* 344:462 */     if (synchron)
/* 345:    */     {
/* 346:463 */       doSend(Context.getCurrentContext());
/* 347:    */     }
/* 348:    */     else
/* 349:    */     {
/* 350:467 */       final Object startingScope = getWindow();
/* 351:468 */       final ContextFactory cf = client.getJavaScriptEngine().getContextFactory();
/* 352:469 */       final ContextAction action = new ContextAction()
/* 353:    */       {
/* 354:    */         public Object run(Context cx)
/* 355:    */         {
/* 356:471 */           cx.putThreadLocal("startingScope", startingScope);
/* 357:472 */           XMLHttpRequest.this.doSend(cx);
/* 358:473 */           return null;
/* 359:    */         }
/* 360:475 */       };
/* 361:476 */       JavaScriptJob job = new JavaScriptJob()
/* 362:    */       {
/* 363:    */         public void run()
/* 364:    */         {
/* 365:478 */           cf.call(action);
/* 366:    */         }
/* 367:    */         
/* 368:    */         public String toString()
/* 369:    */         {
/* 370:482 */           return "XMLHttpRequest Job " + getId();
/* 371:    */         }
/* 372:    */       };
/* 373:485 */       if (LOG.isDebugEnabled()) {
/* 374:486 */         LOG.debug("Starting XMLHttpRequest thread for asynchronous request");
/* 375:    */       }
/* 376:488 */       this.threadID_ = getWindow().getWebWindow().getJobManager().addJob(job, page);
/* 377:    */     }
/* 378:    */   }
/* 379:    */   
/* 380:    */   private void prepareRequest(Object content)
/* 381:    */   {
/* 382:497 */     if ((content != null) && ((HttpMethod.POST == this.webRequest_.getHttpMethod()) || (HttpMethod.PUT == this.webRequest_.getHttpMethod())) && (!Context.getUndefinedValue().equals(content)))
/* 383:    */     {
/* 384:501 */       String body = Context.toString(content);
/* 385:502 */       if (body.length() > 0)
/* 386:    */       {
/* 387:503 */         if (LOG.isDebugEnabled()) {
/* 388:504 */           LOG.debug("Setting request body to: " + body);
/* 389:    */         }
/* 390:506 */         this.webRequest_.setRequestBody(body);
/* 391:    */       }
/* 392:    */     }
/* 393:    */   }
/* 394:    */   
/* 395:    */   private void doSend(Context context)
/* 396:    */   {
/* 397:516 */     WebClient wc = getWindow().getWebWindow().getWebClient();
/* 398:    */     try
/* 399:    */     {
/* 400:518 */       setState(2, context);
/* 401:519 */       WebResponse webResponse = wc.loadWebResponse(this.webRequest_);
/* 402:520 */       if (LOG.isDebugEnabled()) {
/* 403:521 */         LOG.debug("Web response loaded successfully.");
/* 404:    */       }
/* 405:523 */       if (this.overriddenMimeType_ == null) {
/* 406:524 */         this.webResponse_ = webResponse;
/* 407:    */       } else {
/* 408:527 */         this.webResponse_ = new WebResponseWrapper(webResponse)
/* 409:    */         {
/* 410:    */           public String getContentType()
/* 411:    */           {
/* 412:530 */             return XMLHttpRequest.this.overriddenMimeType_;
/* 413:    */           }
/* 414:    */         };
/* 415:    */       }
/* 416:534 */       setState(3, context);
/* 417:535 */       setState(4, context);
/* 418:    */     }
/* 419:    */     catch (IOException e)
/* 420:    */     {
/* 421:538 */       if (LOG.isDebugEnabled()) {
/* 422:539 */         LOG.debug("IOException: returning a network error response.", e);
/* 423:    */       }
/* 424:541 */       this.webResponse_ = new NetworkErrorWebResponse(this.webRequest_, null);
/* 425:542 */       setState(4, context);
/* 426:543 */       processError(context);
/* 427:    */     }
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void jsxFunction_setRequestHeader(String name, String value)
/* 431:    */   {
/* 432:554 */     if (!isAuthorizedHeader(name))
/* 433:    */     {
/* 434:555 */       LOG.warn("Ignoring XMLHttpRequest.setRequestHeader for " + name + ": it is a restricted header");
/* 435:    */       
/* 436:557 */       return;
/* 437:    */     }
/* 438:560 */     if (this.webRequest_ != null) {
/* 439:561 */       this.webRequest_.setAdditionalHeader(name, value);
/* 440:    */     } else {
/* 441:564 */       throw Context.reportRuntimeError("The open() method must be called before setRequestHeader().");
/* 442:    */     }
/* 443:    */   }
/* 444:    */   
/* 445:    */   static boolean isAuthorizedHeader(String name)
/* 446:    */   {
/* 447:575 */     String nameLowerCase = name.toLowerCase();
/* 448:576 */     if (PROHIBITED_HEADERS_.contains(nameLowerCase)) {
/* 449:577 */       return false;
/* 450:    */     }
/* 451:579 */     if ((nameLowerCase.startsWith("proxy-")) || (nameLowerCase.startsWith("sec-"))) {
/* 452:580 */       return false;
/* 453:    */     }
/* 454:582 */     return true;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void jsxFunction_overrideMimeType(String mimeType)
/* 458:    */   {
/* 459:593 */     this.overriddenMimeType_ = mimeType;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public Object get(String name, Scriptable start)
/* 463:    */   {
/* 464:601 */     if (!this.caseSensitiveProperties_) {
/* 465:602 */       for (String property : ALL_PROPERTIES_) {
/* 466:603 */         if (property.equalsIgnoreCase(name))
/* 467:    */         {
/* 468:604 */           name = property;
/* 469:605 */           break;
/* 470:    */         }
/* 471:    */       }
/* 472:    */     }
/* 473:609 */     return super.get(name, start);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void put(String name, Scriptable start, Object value)
/* 477:    */   {
/* 478:617 */     if (!this.caseSensitiveProperties_) {
/* 479:618 */       for (String property : ALL_PROPERTIES_) {
/* 480:619 */         if (property.equalsIgnoreCase(name))
/* 481:    */         {
/* 482:620 */           name = property;
/* 483:621 */           break;
/* 484:    */         }
/* 485:    */       }
/* 486:    */     }
/* 487:625 */     super.put(name, start, value);
/* 488:    */   }
/* 489:    */   
/* 490:    */   private static final class NetworkErrorWebResponse
/* 491:    */     extends WebResponse
/* 492:    */   {
/* 493:    */     private final WebRequest request_;
/* 494:    */     
/* 495:    */     private NetworkErrorWebResponse(WebRequest webRequest)
/* 496:    */     {
/* 497:632 */       super(null, 0L);
/* 498:633 */       this.request_ = webRequest;
/* 499:    */     }
/* 500:    */     
/* 501:    */     public int getStatusCode()
/* 502:    */     {
/* 503:638 */       return 0;
/* 504:    */     }
/* 505:    */     
/* 506:    */     public String getStatusMessage()
/* 507:    */     {
/* 508:643 */       return "";
/* 509:    */     }
/* 510:    */     
/* 511:    */     public String getContentType()
/* 512:    */     {
/* 513:648 */       return "";
/* 514:    */     }
/* 515:    */     
/* 516:    */     public String getContentAsString()
/* 517:    */     {
/* 518:653 */       return "";
/* 519:    */     }
/* 520:    */     
/* 521:    */     public String getContentAsString(String encoding)
/* 522:    */     {
/* 523:658 */       return "";
/* 524:    */     }
/* 525:    */     
/* 526:    */     public InputStream getContentAsStream()
/* 527:    */     {
/* 528:663 */       return null;
/* 529:    */     }
/* 530:    */     
/* 531:    */     public byte[] getContentAsBytes()
/* 532:    */     {
/* 533:668 */       return new byte[0];
/* 534:    */     }
/* 535:    */     
/* 536:    */     public List<NameValuePair> getResponseHeaders()
/* 537:    */     {
/* 538:673 */       return Collections.emptyList();
/* 539:    */     }
/* 540:    */     
/* 541:    */     public String getResponseHeaderValue(String headerName)
/* 542:    */     {
/* 543:678 */       return "";
/* 544:    */     }
/* 545:    */     
/* 546:    */     public long getLoadTime()
/* 547:    */     {
/* 548:683 */       return 0L;
/* 549:    */     }
/* 550:    */     
/* 551:    */     public String getContentCharset()
/* 552:    */     {
/* 553:688 */       return "";
/* 554:    */     }
/* 555:    */     
/* 556:    */     public String getContentCharsetOrNull()
/* 557:    */     {
/* 558:693 */       return "";
/* 559:    */     }
/* 560:    */     
/* 561:    */     public WebRequest getRequestSettings()
/* 562:    */     {
/* 563:698 */       return this.request_;
/* 564:    */     }
/* 565:    */     
/* 566:    */     public WebRequest getWebRequest()
/* 567:    */     {
/* 568:703 */       return this.request_;
/* 569:    */     }
/* 570:    */   }
/* 571:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.xml.XMLHttpRequest
 * JD-Core Version:    0.7.0.1
 */