/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ObjectInstantiationException;
/*   6:    */ import com.gargoylesoftware.htmlunit.Page;
/*   7:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  11:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLBodyElement;
/*  13:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement.ProxyDomNode;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.io.InputStream;
/*  16:    */ import java.io.StringReader;
/*  17:    */ import java.lang.reflect.InvocationTargetException;
/*  18:    */ import java.net.URL;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import java.util.Stack;
/*  24:    */ import org.apache.commons.lang.StringUtils;
/*  25:    */ import org.apache.xerces.parsers.AbstractSAXParser;
/*  26:    */ import org.apache.xerces.xni.Augmentations;
/*  27:    */ import org.apache.xerces.xni.QName;
/*  28:    */ import org.apache.xerces.xni.XMLAttributes;
/*  29:    */ import org.apache.xerces.xni.XNIException;
/*  30:    */ import org.apache.xerces.xni.parser.XMLInputSource;
/*  31:    */ import org.apache.xerces.xni.parser.XMLParserConfiguration;
/*  32:    */ import org.cyberneko.html.HTMLConfiguration;
/*  33:    */ import org.cyberneko.html.HTMLEventInfo;
/*  34:    */ import org.cyberneko.html.HTMLScanner;
/*  35:    */ import org.cyberneko.html.HTMLTagBalancingListener;
/*  36:    */ import org.w3c.dom.Element;
/*  37:    */ import org.w3c.dom.NamedNodeMap;
/*  38:    */ import org.w3c.dom.Node;
/*  39:    */ import org.xml.sax.Attributes;
/*  40:    */ import org.xml.sax.ContentHandler;
/*  41:    */ import org.xml.sax.Locator;
/*  42:    */ import org.xml.sax.SAXException;
/*  43:    */ import org.xml.sax.ext.LexicalHandler;
/*  44:    */ 
/*  45:    */ public final class HTMLParser
/*  46:    */ {
/*  47:    */   public static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
/*  48: 81 */   private static final Map<String, IElementFactory> ELEMENT_FACTORIES = new HashMap();
/*  49:    */   
/*  50:    */   static
/*  51:    */   {
/*  52: 84 */     ELEMENT_FACTORIES.put("input", InputElementFactory.instance);
/*  53:    */     
/*  54: 86 */     DefaultElementFactory defaultElementFactory = new DefaultElementFactory();
/*  55: 87 */     for (String tagName : DefaultElementFactory.SUPPORTED_TAGS_) {
/*  56: 88 */       ELEMENT_FACTORIES.put(tagName, defaultElementFactory);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static IElementFactory getFactory(String tagName)
/*  61:    */   {
/*  62: 97 */     IElementFactory result = (IElementFactory)ELEMENT_FACTORIES.get(tagName);
/*  63: 99 */     if (result != null) {
/*  64:100 */       return result;
/*  65:    */     }
/*  66:102 */     return UnknownElementFactory.instance;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static void parseFragment(DomNode parent, String source)
/*  70:    */     throws SAXException, IOException
/*  71:    */   {
/*  72:121 */     parseFragment(parent, parent, source);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void parseFragment(DomNode parent, DomNode context, String source)
/*  76:    */     throws SAXException, IOException
/*  77:    */   {
/*  78:135 */     HtmlPage page = (HtmlPage)parent.getPage();
/*  79:136 */     URL url = page.getWebResponse().getWebRequest().getUrl();
/*  80:    */     
/*  81:138 */     HtmlUnitDOMBuilder domBuilder = new HtmlUnitDOMBuilder(parent, url, null);
/*  82:139 */     domBuilder.setFeature("http://cyberneko.org/html/features/balance-tags/document-fragment", true);
/*  83:    */     
/*  84:141 */     DomNode node = context;
/*  85:142 */     List<QName> ancestors = new ArrayList();
/*  86:143 */     while ((node != null) && (node.getNodeType() != 9))
/*  87:    */     {
/*  88:144 */       ancestors.add(0, new QName(null, node.getNodeName(), null, null));
/*  89:145 */       node = node.getParentNode();
/*  90:    */     }
/*  91:147 */     if ((ancestors.isEmpty()) || (!"html".equals(((QName)ancestors.get(0)).localpart))) {
/*  92:148 */       ancestors.add(0, new QName(null, "html", null, null));
/*  93:    */     }
/*  94:150 */     if ((ancestors.size() == 1) || (!"body".equals(((QName)ancestors.get(1)).localpart))) {
/*  95:151 */       ancestors.add(1, new QName(null, "body", null, null));
/*  96:    */     }
/*  97:154 */     domBuilder.setProperty("http://cyberneko.org/html/properties/balance-tags/fragment-context-stack", ancestors.toArray(new QName[0]));
/*  98:    */     
/*  99:156 */     XMLInputSource in = new XMLInputSource(null, url.toString(), null, new StringReader(source), null);
/* 100:    */     
/* 101:158 */     page.registerParsingStart();
/* 102:159 */     page.registerSnippetParsingStart();
/* 103:    */     try
/* 104:    */     {
/* 105:161 */       domBuilder.parse(in);
/* 106:    */     }
/* 107:    */     finally
/* 108:    */     {
/* 109:164 */       page.registerParsingEnd();
/* 110:165 */       page.registerSnippetParsingEnd();
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static HtmlPage parseHtml(WebResponse webResponse, WebWindow webWindow)
/* 115:    */     throws IOException
/* 116:    */   {
/* 117:178 */     HtmlPage page = new HtmlPage(webResponse.getWebRequest().getUrl(), webResponse, webWindow);
/* 118:179 */     parse(webResponse, webWindow, page);
/* 119:180 */     return page;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static XHtmlPage parseXHtml(WebResponse webResponse, WebWindow webWindow)
/* 123:    */     throws IOException
/* 124:    */   {
/* 125:192 */     XHtmlPage page = new XHtmlPage(webResponse.getWebRequest().getUrl(), webResponse, webWindow);
/* 126:193 */     parse(webResponse, webWindow, page);
/* 127:194 */     return page;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private static void parse(WebResponse webResponse, WebWindow webWindow, HtmlPage page)
/* 131:    */     throws IOException
/* 132:    */   {
/* 133:200 */     webWindow.setEnclosedPage(page);
/* 134:    */     
/* 135:202 */     URL url = webResponse.getWebRequest().getUrl();
/* 136:203 */     HtmlUnitDOMBuilder domBuilder = new HtmlUnitDOMBuilder(page, url, null);
/* 137:204 */     String charset = webResponse.getContentCharsetOrNull();
/* 138:205 */     if (charset != null)
/* 139:    */     {
/* 140:    */       try
/* 141:    */       {
/* 142:207 */         domBuilder.setFeature("http://cyberneko.org/html/features/scanner/ignore-specified-charset", true);
/* 143:    */       }
/* 144:    */       catch (Exception e)
/* 145:    */       {
/* 146:210 */         throw new ObjectInstantiationException("Error setting HTML parser feature", e);
/* 147:    */       }
/* 148:    */     }
/* 149:    */     else
/* 150:    */     {
/* 151:214 */       String specifiedCharset = webResponse.getWebRequest().getCharset();
/* 152:215 */       if (specifiedCharset != null) {
/* 153:216 */         charset = specifiedCharset;
/* 154:    */       }
/* 155:    */     }
/* 156:220 */     InputStream content = webResponse.getContentAsStream();
/* 157:221 */     XMLInputSource in = new XMLInputSource(null, url.toString(), null, content, charset);
/* 158:    */     
/* 159:223 */     page.registerParsingStart();
/* 160:    */     try
/* 161:    */     {
/* 162:225 */       domBuilder.parse(in);
/* 163:    */     }
/* 164:    */     catch (XNIException e)
/* 165:    */     {
/* 166:229 */       Throwable origin = extractNestedException(e);
/* 167:230 */       throw new RuntimeException("Failed parsing content from " + url, origin);
/* 168:    */     }
/* 169:    */     finally
/* 170:    */     {
/* 171:233 */       page.registerParsingEnd();
/* 172:    */     }
/* 173:236 */     addBodyToPageIfNecessary(page, true, domBuilder.body_ != null);
/* 174:    */   }
/* 175:    */   
/* 176:    */   private static void addBodyToPageIfNecessary(HtmlPage page, boolean originalCall, boolean checkInsideFrameOnly)
/* 177:    */   {
/* 178:252 */     boolean waitToLoad = page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.PAGE_WAIT_LOAD_BEFORE_BODY);
/* 179:254 */     if (((page.getEnclosingWindow() instanceof FrameWindow)) && (originalCall) && (waitToLoad)) {
/* 180:255 */       return;
/* 181:    */     }
/* 182:259 */     Element doc = page.getDocumentElement();
/* 183:260 */     boolean hasBody = false;
/* 184:261 */     for (Node child = doc.getFirstChild(); child != null; child = child.getNextSibling()) {
/* 185:262 */       if (((child instanceof HtmlBody)) || ((child instanceof HtmlFrameSet)))
/* 186:    */       {
/* 187:263 */         hasBody = true;
/* 188:264 */         break;
/* 189:    */       }
/* 190:    */     }
/* 191:269 */     if ((!hasBody) && (!checkInsideFrameOnly))
/* 192:    */     {
/* 193:270 */       HtmlBody body = new HtmlBody(null, "body", page, null, false);
/* 194:271 */       doc.appendChild(body);
/* 195:    */     }
/* 196:276 */     if (waitToLoad) {
/* 197:277 */       for (FrameWindow frame : page.getFrames())
/* 198:    */       {
/* 199:278 */         Page containedPage = frame.getEnclosedPage();
/* 200:279 */         if ((containedPage instanceof HtmlPage)) {
/* 201:280 */           addBodyToPageIfNecessary((HtmlPage)containedPage, false, false);
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   static Throwable extractNestedException(Throwable e)
/* 208:    */   {
/* 209:294 */     Throwable originalException = e;
/* 210:295 */     Throwable cause = ((XNIException)e).getException();
/* 211:296 */     while (cause != null)
/* 212:    */     {
/* 213:297 */       originalException = cause;
/* 214:298 */       if ((cause instanceof XNIException)) {
/* 215:299 */         cause = ((XNIException)cause).getException();
/* 216:301 */       } else if ((cause instanceof InvocationTargetException)) {
/* 217:302 */         cause = cause.getCause();
/* 218:    */       } else {
/* 219:305 */         cause = null;
/* 220:    */       }
/* 221:    */     }
/* 222:308 */     return originalException;
/* 223:    */   }
/* 224:    */   
/* 225:    */   static IElementFactory getElementFactory(String namespaceURI, String qualifiedName)
/* 226:    */   {
/* 227:318 */     if ((namespaceURI == null) || (namespaceURI.length() == 0) || (!qualifiedName.contains(":")) || (namespaceURI.equals("http://www.w3.org/1999/xhtml")))
/* 228:    */     {
/* 229:320 */       String tagName = qualifiedName;
/* 230:321 */       int index = tagName.indexOf(':');
/* 231:322 */       if (index != -1) {
/* 232:323 */         tagName = tagName.substring(index + 1);
/* 233:    */       } else {
/* 234:326 */         tagName = tagName.toLowerCase();
/* 235:    */       }
/* 236:328 */       IElementFactory factory = (IElementFactory)ELEMENT_FACTORIES.get(tagName);
/* 237:330 */       if (factory != null) {
/* 238:331 */         return factory;
/* 239:    */       }
/* 240:    */     }
/* 241:334 */     return UnknownElementFactory.instance;
/* 242:    */   }
/* 243:    */   
/* 244:    */   static final class HtmlUnitDOMBuilder
/* 245:    */     extends AbstractSAXParser
/* 246:    */     implements ContentHandler, LexicalHandler, HTMLTagBalancingListener
/* 247:    */   {
/* 248:    */     private final HtmlPage page_;
/* 249:    */     private Locator locator_;
/* 250:347 */     private final Stack<DomNode> stack_ = new Stack();
/* 251:    */     private DomNode currentNode_;
/* 252:    */     private StringBuilder characters_;
/* 253:351 */     private boolean headParsed_ = false;
/* 254:352 */     private boolean parsingInnerHead_ = false;
/* 255:    */     private HtmlElement head_;
/* 256:    */     private HtmlElement body_;
/* 257:    */     private Augmentations augmentations_;
/* 258:    */     private HtmlForm formWaitingForLostChildren_;
/* 259:    */     private static final String FEATURE_AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";
/* 260:    */     private static final String FEATURE_PARSE_NOSCRIPT = "http://cyberneko.org/html/features/parse-noscript-content";
/* 261:    */     
/* 262:    */     public void pushInputString(String html)
/* 263:    */     {
/* 264:366 */       this.page_.registerParsingStart();
/* 265:367 */       this.page_.registerInlineSnippetParsingStart();
/* 266:    */       try
/* 267:    */       {
/* 268:369 */         WebResponse webResponse = this.page_.getWebResponse();
/* 269:370 */         String charset = webResponse.getContentCharset();
/* 270:371 */         String url = webResponse.getWebRequest().getUrl().toString();
/* 271:372 */         XMLInputSource in = new XMLInputSource(null, url, null, new StringReader(html), charset);
/* 272:373 */         ((HTMLConfiguration)this.fConfiguration).evaluateInputSource(in);
/* 273:    */       }
/* 274:    */       finally
/* 275:    */       {
/* 276:376 */         this.page_.registerParsingEnd();
/* 277:377 */         this.page_.registerInlineSnippetParsingEnd();
/* 278:    */       }
/* 279:    */     }
/* 280:    */     
/* 281:    */     private HtmlUnitDOMBuilder(DomNode node, URL url)
/* 282:    */     {
/* 283:387 */       super();
/* 284:388 */       this.page_ = ((HtmlPage)node.getPage());
/* 285:    */       
/* 286:390 */       this.currentNode_ = node;
/* 287:391 */       for (Node ancestor : this.currentNode_.getAncestors(true)) {
/* 288:392 */         this.stack_.push((DomNode)ancestor);
/* 289:    */       }
/* 290:395 */       WebClient webClient = this.page_.getWebClient();
/* 291:396 */       HTMLParserListener listener = webClient.getHTMLParserListener();
/* 292:    */       boolean reportErrors;
/* 293:398 */       if (listener != null)
/* 294:    */       {
/* 295:399 */         boolean reportErrors = true;
/* 296:400 */         this.fConfiguration.setErrorHandler(new HTMLErrorHandler(listener, url));
/* 297:    */       }
/* 298:    */       else
/* 299:    */       {
/* 300:403 */         reportErrors = false;
/* 301:    */       }
/* 302:    */       try
/* 303:    */       {
/* 304:407 */         setFeature("http://cyberneko.org/html/features/augmentations", true);
/* 305:408 */         setProperty("http://cyberneko.org/html/properties/names/elems", "default");
/* 306:409 */         setFeature("http://cyberneko.org/html/features/report-errors", reportErrors);
/* 307:410 */         setFeature("http://cyberneko.org/html/features/parse-noscript-content", !webClient.isJavaScriptEnabled());
/* 308:411 */         setFeature("http://cyberneko.org/html/features/scanner/allow-selfclosing-iframe", !webClient.getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLIFRAME_IGNORE_SELFCLOSING));
/* 309:    */         
/* 310:    */ 
/* 311:414 */         setContentHandler(this);
/* 312:415 */         setLexicalHandler(this);
/* 313:    */       }
/* 314:    */       catch (SAXException e)
/* 315:    */       {
/* 316:418 */         throw new ObjectInstantiationException("unable to create HTML parser", e);
/* 317:    */       }
/* 318:    */     }
/* 319:    */     
/* 320:    */     private static XMLParserConfiguration createConfiguration(WebClient webClient)
/* 321:    */     {
/* 322:428 */       BrowserVersion browserVersion = webClient.getBrowserVersion();
/* 323:430 */       if (browserVersion.hasFeature(BrowserVersionFeatures.HTMLCONDITIONAL_COMMENTS)) {
/* 324:431 */         new HTMLConfiguration()
/* 325:    */         {
/* 326:    */           protected HTMLScanner createDocumentScanner()
/* 327:    */           {
/* 328:434 */             return new HTMLScannerForIE(this.val$browserVersion);
/* 329:    */           }
/* 330:    */         };
/* 331:    */       }
/* 332:438 */       return new HTMLConfiguration();
/* 333:    */     }
/* 334:    */     
/* 335:    */     public Locator getLocator()
/* 336:    */     {
/* 337:445 */       return this.locator_;
/* 338:    */     }
/* 339:    */     
/* 340:    */     public void setDocumentLocator(Locator locator)
/* 341:    */     {
/* 342:450 */       this.locator_ = locator;
/* 343:    */     }
/* 344:    */     
/* 345:    */     public void startDocument()
/* 346:    */       throws SAXException
/* 347:    */     {}
/* 348:    */     
/* 349:    */     public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
/* 350:    */       throws SAXException
/* 351:    */     {
/* 352:463 */       handleCharacters();
/* 353:465 */       if (namespaceURI != null) {
/* 354:466 */         namespaceURI = namespaceURI.trim();
/* 355:    */       }
/* 356:468 */       String tagLower = localName.toLowerCase();
/* 357:470 */       if ((this.page_.isParsingHtmlSnippet()) && (("html".equals(tagLower)) || ("body".equals(tagLower)))) {
/* 358:471 */         return;
/* 359:    */       }
/* 360:474 */       if ((this.parsingInnerHead_) && (this.page_.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.IGNORE_CONTENTS_OF_INNER_HEAD))) {
/* 361:476 */         return;
/* 362:    */       }
/* 363:479 */       if ("head".equals(tagLower))
/* 364:    */       {
/* 365:480 */         if ((this.headParsed_) || (this.page_.isParsingHtmlSnippet()))
/* 366:    */         {
/* 367:481 */           this.parsingInnerHead_ = true;
/* 368:482 */           return;
/* 369:    */         }
/* 370:484 */         this.headParsed_ = true;
/* 371:    */       }
/* 372:487 */       else if ((!this.headParsed_) && (("body".equals(tagLower)) || ("frameset".equals(tagLower))))
/* 373:    */       {
/* 374:488 */         IElementFactory factory = HTMLParser.getElementFactory(namespaceURI, "head");
/* 375:489 */         HtmlElement newElement = factory.createElement(this.page_, "head", null);
/* 376:490 */         this.currentNode_.appendChild(newElement);
/* 377:491 */         this.headParsed_ = true;
/* 378:    */       }
/* 379:496 */       HtmlBody oldBody = null;
/* 380:497 */       if (("body".equals(qName)) && ((this.page_.getBody() instanceof HtmlBody))) {
/* 381:498 */         oldBody = (HtmlBody)this.page_.getBody();
/* 382:    */       }
/* 383:502 */       if ((!(this.page_ instanceof XHtmlPage)) && ("http://www.w3.org/1999/xhtml".equals(namespaceURI))) {
/* 384:503 */         namespaceURI = null;
/* 385:    */       }
/* 386:505 */       IElementFactory factory = HTMLParser.getElementFactory(namespaceURI, qName);
/* 387:506 */       HtmlElement newElement = factory.createElementNS(this.page_, namespaceURI, qName, atts);
/* 388:507 */       newElement.setStartLocation(this.locator_.getLineNumber(), this.locator_.getColumnNumber());
/* 389:    */       
/* 390:    */ 
/* 391:510 */       addNodeToRightParent(this.currentNode_, newElement);
/* 392:514 */       if (oldBody != null) {
/* 393:515 */         oldBody.quietlyRemoveAndMoveChildrenTo(newElement);
/* 394:    */       }
/* 395:518 */       if ("body".equals(tagLower)) {
/* 396:519 */         this.body_ = newElement;
/* 397:521 */       } else if ("head".equals(tagLower)) {
/* 398:522 */         this.head_ = newElement;
/* 399:    */       }
/* 400:525 */       this.currentNode_ = newElement;
/* 401:526 */       this.stack_.push(this.currentNode_);
/* 402:    */     }
/* 403:    */     
/* 404:    */     private void addNodeToRightParent(DomNode currentNode, HtmlElement newElement)
/* 405:    */     {
/* 406:534 */       String currentNodeName = currentNode.getNodeName();
/* 407:535 */       String newNodeName = newElement.getNodeName();
/* 408:539 */       if (("table".equals(currentNodeName)) && ("div".equals(newNodeName))) {
/* 409:540 */         currentNode.insertBefore(newElement);
/* 410:542 */       } else if (("title".equals(newNodeName)) && (this.head_ != null)) {
/* 411:543 */         this.head_.appendChild(newElement);
/* 412:    */       } else {
/* 413:546 */         currentNode.appendChild(newElement);
/* 414:    */       }
/* 415:    */     }
/* 416:    */     
/* 417:    */     public void endElement(QName element, Augmentations augs)
/* 418:    */       throws XNIException
/* 419:    */     {
/* 420:555 */       this.augmentations_ = augs;
/* 421:556 */       super.endElement(element, augs);
/* 422:    */     }
/* 423:    */     
/* 424:    */     public void endElement(String namespaceURI, String localName, String qName)
/* 425:    */       throws SAXException
/* 426:    */     {
/* 427:563 */       handleCharacters();
/* 428:    */       
/* 429:565 */       String tagLower = localName.toLowerCase();
/* 430:567 */       if ((this.page_.isParsingHtmlSnippet()) && (("html".equals(tagLower)) || ("body".equals(tagLower)))) {
/* 431:568 */         return;
/* 432:    */       }
/* 433:571 */       if (this.parsingInnerHead_)
/* 434:    */       {
/* 435:572 */         if ("head".equals(tagLower)) {
/* 436:573 */           this.parsingInnerHead_ = false;
/* 437:    */         }
/* 438:575 */         if (("head".equals(tagLower)) || (this.page_.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.IGNORE_CONTENTS_OF_INNER_HEAD))) {
/* 439:577 */           return;
/* 440:    */         }
/* 441:    */       }
/* 442:581 */       DomNode previousNode = (DomNode)this.stack_.pop();
/* 443:582 */       previousNode.setEndLocation(this.locator_.getLineNumber(), this.locator_.getColumnNumber());
/* 444:585 */       if (((previousNode instanceof HtmlForm)) && (((HTMLEventInfo)this.augmentations_.getItem("http://cyberneko.org/html/features/augmentations")).isSynthesized())) {
/* 445:587 */         this.formWaitingForLostChildren_ = ((HtmlForm)previousNode);
/* 446:589 */       } else if ((this.formWaitingForLostChildren_ != null) && ((previousNode instanceof SubmittableElement))) {
/* 447:590 */         this.formWaitingForLostChildren_.addLostChild((HtmlElement)previousNode);
/* 448:    */       }
/* 449:593 */       if (!this.stack_.isEmpty()) {
/* 450:594 */         this.currentNode_ = ((DomNode)this.stack_.peek());
/* 451:    */       }
/* 452:597 */       boolean postponed = this.page_.isParsingInlineHtmlSnippet();
/* 453:598 */       previousNode.onAllChildrenAddedToPage(postponed);
/* 454:    */     }
/* 455:    */     
/* 456:    */     public void characters(char[] ch, int start, int length)
/* 457:    */       throws SAXException
/* 458:    */     {
/* 459:603 */       if (((this.characters_ == null) || (this.characters_.length() == 0)) && (this.page_.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_2)) && (StringUtils.isBlank(new String(ch, start, length))))
/* 460:    */       {
/* 461:607 */         DomNode node = this.currentNode_.getLastChild();
/* 462:608 */         if ((this.currentNode_ instanceof HTMLElement.ProxyDomNode))
/* 463:    */         {
/* 464:609 */           HTMLElement.ProxyDomNode proxyNode = (HTMLElement.ProxyDomNode)this.currentNode_;
/* 465:610 */           node = proxyNode.getDomNode();
/* 466:611 */           if (!proxyNode.isAppend())
/* 467:    */           {
/* 468:612 */             node = node.getPreviousSibling();
/* 469:613 */             if (node == null) {
/* 470:614 */               node = proxyNode.getDomNode().getParentNode();
/* 471:    */             }
/* 472:    */           }
/* 473:    */         }
/* 474:618 */         if (removeEmptyCharacters(node)) {
/* 475:619 */           return;
/* 476:    */         }
/* 477:    */       }
/* 478:622 */       if (this.characters_ == null) {
/* 479:623 */         this.characters_ = new StringBuilder();
/* 480:    */       }
/* 481:625 */       this.characters_.append(ch, start, length);
/* 482:    */     }
/* 483:    */     
/* 484:    */     private boolean removeEmptyCharacters(DomNode node)
/* 485:    */     {
/* 486:629 */       if (node != null)
/* 487:    */       {
/* 488:630 */         if ((node instanceof HtmlInput)) {
/* 489:631 */           return false;
/* 490:    */         }
/* 491:633 */         if (((node instanceof HtmlAnchor)) || ((node instanceof HtmlSpan)) || ((node instanceof HtmlFont)))
/* 492:    */         {
/* 493:634 */           DomNode anchorChild = node.getFirstChild();
/* 494:635 */           if (anchorChild != null) {
/* 495:636 */             return false;
/* 496:    */           }
/* 497:    */         }
/* 498:    */       }
/* 499:641 */       else if ((this.currentNode_ instanceof HtmlFont))
/* 500:    */       {
/* 501:642 */         return false;
/* 502:    */       }
/* 503:645 */       return true;
/* 504:    */     }
/* 505:    */     
/* 506:    */     public void ignorableWhitespace(char[] ch, int start, int length)
/* 507:    */       throws SAXException
/* 508:    */     {
/* 509:650 */       if (this.characters_ == null) {
/* 510:651 */         this.characters_ = new StringBuilder();
/* 511:    */       }
/* 512:653 */       this.characters_.append(ch, start, length);
/* 513:    */     }
/* 514:    */     
/* 515:    */     private void handleCharacters()
/* 516:    */     {
/* 517:660 */       if ((this.characters_ != null) && (this.characters_.length() > 0)) {
/* 518:661 */         if ((this.currentNode_ instanceof HtmlHtml))
/* 519:    */         {
/* 520:664 */           this.characters_.setLength(0);
/* 521:    */         }
/* 522:    */         else
/* 523:    */         {
/* 524:668 */           String textValue = this.characters_.toString();
/* 525:669 */           DomText text = new DomText(this.page_, textValue);
/* 526:670 */           this.characters_.setLength(0);
/* 527:673 */           if (((this.currentNode_ instanceof HtmlTableRow)) && (StringUtils.isNotBlank(textValue)))
/* 528:    */           {
/* 529:674 */             HtmlTableRow row = (HtmlTableRow)this.currentNode_;
/* 530:675 */             HtmlTable enclosingTable = row.getEnclosingTable();
/* 531:676 */             if (enclosingTable != null) {
/* 532:677 */               enclosingTable.insertBefore(text);
/* 533:    */             }
/* 534:    */           }
/* 535:    */           else
/* 536:    */           {
/* 537:681 */             this.currentNode_.appendChild(text);
/* 538:    */           }
/* 539:    */         }
/* 540:    */       }
/* 541:    */     }
/* 542:    */     
/* 543:    */     public void endDocument()
/* 544:    */       throws SAXException
/* 545:    */     {
/* 546:689 */       handleCharacters();
/* 547:690 */       DomNode currentPage = this.page_;
/* 548:691 */       currentPage.setEndLocation(this.locator_.getLineNumber(), this.locator_.getColumnNumber());
/* 549:    */     }
/* 550:    */     
/* 551:    */     public void startPrefixMapping(String prefix, String uri)
/* 552:    */       throws SAXException
/* 553:    */     {}
/* 554:    */     
/* 555:    */     public void endPrefixMapping(String prefix)
/* 556:    */       throws SAXException
/* 557:    */     {}
/* 558:    */     
/* 559:    */     public void processingInstruction(String target, String data)
/* 560:    */       throws SAXException
/* 561:    */     {}
/* 562:    */     
/* 563:    */     public void skippedEntity(String name)
/* 564:    */       throws SAXException
/* 565:    */     {}
/* 566:    */     
/* 567:    */     public void comment(char[] ch, int start, int length)
/* 568:    */     {
/* 569:714 */       handleCharacters();
/* 570:715 */       String data = new String(ch, start, length);
/* 571:716 */       if ((!data.startsWith("[CDATA")) || (!this.page_.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_3)))
/* 572:    */       {
/* 573:718 */         DomComment comment = new DomComment(this.page_, data);
/* 574:719 */         this.currentNode_.appendChild(comment);
/* 575:    */       }
/* 576:    */     }
/* 577:    */     
/* 578:    */     public void endCDATA() {}
/* 579:    */     
/* 580:    */     public void endDTD() {}
/* 581:    */     
/* 582:    */     public void endEntity(String name) {}
/* 583:    */     
/* 584:    */     public void startCDATA() {}
/* 585:    */     
/* 586:    */     public void startDTD(String name, String publicId, String systemId)
/* 587:    */     {
/* 588:741 */       DomDocumentType type = new DomDocumentType(this.page_, name, publicId, systemId);
/* 589:742 */       this.page_.setDocumentType(type);
/* 590:743 */       this.page_.appendChild(type);
/* 591:    */     }
/* 592:    */     
/* 593:    */     public void startEntity(String name) {}
/* 594:    */     
/* 595:    */     public void ignoredEndElement(QName element, Augmentations augs)
/* 596:    */     {
/* 597:755 */       if ((this.formWaitingForLostChildren_ != null) && ("form".equals(element.localpart))) {
/* 598:756 */         this.formWaitingForLostChildren_ = null;
/* 599:    */       }
/* 600:    */     }
/* 601:    */     
/* 602:    */     public void ignoredStartElement(QName elem, XMLAttributes attrs, Augmentations augs)
/* 603:    */     {
/* 604:766 */       if ((this.body_ != null) && ("body".equalsIgnoreCase(elem.localpart)) && (attrs != null))
/* 605:    */       {
/* 606:768 */         int length = attrs.getLength();
/* 607:769 */         for (int i = 0; i < length; i++)
/* 608:    */         {
/* 609:770 */           String attrName = attrs.getLocalName(i).toLowerCase();
/* 610:771 */           if (this.body_.getAttributes().getNamedItem(attrName) == null)
/* 611:    */           {
/* 612:772 */             this.body_.setAttribute(attrName, attrs.getValue(i));
/* 613:773 */             if ((attrName.startsWith("on")) && (this.body_.getScriptObject() != null))
/* 614:    */             {
/* 615:774 */               HTMLBodyElement jsBody = (HTMLBodyElement)this.body_.getScriptObject();
/* 616:775 */               jsBody.createEventHandlerFromAttribute(attrName, attrs.getValue(i));
/* 617:    */             }
/* 618:    */           }
/* 619:    */         }
/* 620:    */       }
/* 621:    */     }
/* 622:    */     
/* 623:    */     public void parse(XMLInputSource inputSource)
/* 624:    */       throws XNIException, IOException
/* 625:    */     {
/* 626:786 */       HtmlUnitDOMBuilder oldBuilder = this.page_.getBuilder();
/* 627:787 */       this.page_.setBuilder(this);
/* 628:    */       try
/* 629:    */       {
/* 630:789 */         super.parse(inputSource);
/* 631:    */       }
/* 632:    */       finally
/* 633:    */       {
/* 634:792 */         this.page_.setBuilder(oldBuilder);
/* 635:    */       }
/* 636:    */     }
/* 637:    */   }
/* 638:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HTMLParser
 * JD-Core Version:    0.7.0.1
 */