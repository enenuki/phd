/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.Cache;
/*   6:    */ import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
/*   7:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  11:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.DomNodeList;
/*  14:    */ import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
/*  15:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  16:    */ import com.gargoylesoftware.htmlunit.html.HtmlInput;
/*  17:    */ import com.gargoylesoftware.htmlunit.html.HtmlLink;
/*  18:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  19:    */ import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
/*  20:    */ import com.gargoylesoftware.htmlunit.html.HtmlSelect;
/*  21:    */ import com.gargoylesoftware.htmlunit.html.HtmlStyle;
/*  22:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  23:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  24:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*  25:    */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*  26:    */ import com.steadystate.css.dom.CSSImportRuleImpl;
/*  27:    */ import com.steadystate.css.dom.CSSMediaRuleImpl;
/*  28:    */ import com.steadystate.css.dom.CSSStyleRuleImpl;
/*  29:    */ import com.steadystate.css.dom.CSSStyleSheetImpl;
/*  30:    */ import com.steadystate.css.parser.CSSOMParser;
/*  31:    */ import com.steadystate.css.parser.SACParserCSS21;
/*  32:    */ import com.steadystate.css.parser.SelectorListImpl;
/*  33:    */ import java.io.ByteArrayInputStream;
/*  34:    */ import java.io.IOException;
/*  35:    */ import java.io.InputStream;
/*  36:    */ import java.io.Reader;
/*  37:    */ import java.io.StringReader;
/*  38:    */ import java.net.MalformedURLException;
/*  39:    */ import java.net.URL;
/*  40:    */ import java.util.HashMap;
/*  41:    */ import java.util.HashSet;
/*  42:    */ import java.util.Map;
/*  43:    */ import java.util.Set;
/*  44:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  45:    */ import org.apache.commons.io.IOUtils;
/*  46:    */ import org.apache.commons.lang.StringUtils;
/*  47:    */ import org.apache.commons.logging.Log;
/*  48:    */ import org.apache.commons.logging.LogFactory;
/*  49:    */ import org.w3c.css.sac.AttributeCondition;
/*  50:    */ import org.w3c.css.sac.CombinatorCondition;
/*  51:    */ import org.w3c.css.sac.Condition;
/*  52:    */ import org.w3c.css.sac.ConditionalSelector;
/*  53:    */ import org.w3c.css.sac.ContentCondition;
/*  54:    */ import org.w3c.css.sac.DescendantSelector;
/*  55:    */ import org.w3c.css.sac.ElementSelector;
/*  56:    */ import org.w3c.css.sac.ErrorHandler;
/*  57:    */ import org.w3c.css.sac.InputSource;
/*  58:    */ import org.w3c.css.sac.LangCondition;
/*  59:    */ import org.w3c.css.sac.NegativeCondition;
/*  60:    */ import org.w3c.css.sac.NegativeSelector;
/*  61:    */ import org.w3c.css.sac.Selector;
/*  62:    */ import org.w3c.css.sac.SelectorList;
/*  63:    */ import org.w3c.css.sac.SiblingSelector;
/*  64:    */ import org.w3c.dom.css.CSSImportRule;
/*  65:    */ import org.w3c.dom.css.CSSRule;
/*  66:    */ import org.w3c.dom.css.CSSStyleDeclaration;
/*  67:    */ import org.w3c.dom.stylesheets.MediaList;
/*  68:    */ 
/*  69:    */ public class CSSStyleSheet
/*  70:    */   extends SimpleScriptable
/*  71:    */ {
/*  72: 94 */   private static final Log LOG = LogFactory.getLog(CSSStyleSheet.class);
/*  73:    */   private final org.w3c.dom.css.CSSStyleSheet wrapped_;
/*  74:    */   private final HTMLElement ownerNode_;
/*  75:    */   private CSSRuleList cssRules_;
/*  76:106 */   private Map<CSSImportRule, CSSStyleSheet> imports_ = new HashMap();
/*  77:    */   private String uri_;
/*  78:    */   
/*  79:    */   public CSSStyleSheet()
/*  80:    */   {
/*  81:115 */     this.wrapped_ = new CSSStyleSheetImpl();
/*  82:116 */     this.ownerNode_ = null;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public CSSStyleSheet(HTMLElement element, InputSource source, String uri)
/*  86:    */   {
/*  87:126 */     setParentScope(element.getWindow());
/*  88:127 */     setPrototype(getPrototype(CSSStyleSheet.class));
/*  89:128 */     this.wrapped_ = parseCSS(source);
/*  90:129 */     this.uri_ = uri;
/*  91:130 */     this.ownerNode_ = element;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public CSSStyleSheet(HTMLElement element, org.w3c.dom.css.CSSStyleSheet wrapped, String uri)
/*  95:    */   {
/*  96:140 */     setParentScope(element.getWindow());
/*  97:141 */     setPrototype(getPrototype(CSSStyleSheet.class));
/*  98:142 */     this.wrapped_ = wrapped;
/*  99:143 */     this.uri_ = uri;
/* 100:144 */     this.ownerNode_ = element;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public org.w3c.dom.css.CSSStyleSheet getWrappedSheet()
/* 104:    */   {
/* 105:152 */     return this.wrapped_;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void modifyIfNecessary(ComputedCSSStyleDeclaration style, HTMLElement element)
/* 109:    */   {
/* 110:164 */     org.w3c.dom.css.CSSRuleList rules = getWrappedSheet().getCssRules();
/* 111:165 */     modifyIfNecessary(style, element, rules, new HashSet());
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void modifyIfNecessary(ComputedCSSStyleDeclaration style, HTMLElement element, org.w3c.dom.css.CSSRuleList rules, Set<String> alreadyProcessing)
/* 115:    */   {
/* 116:170 */     if (rules == null) {
/* 117:171 */       return;
/* 118:    */     }
/* 119:173 */     HtmlElement e = element.getDomNodeOrDie();
/* 120:174 */     for (int i = 0; i < rules.getLength(); i++)
/* 121:    */     {
/* 122:175 */       CSSRule rule = rules.item(i);
/* 123:176 */       if (rule.getType() == 1)
/* 124:    */       {
/* 125:177 */         CSSStyleRuleImpl styleRule = (CSSStyleRuleImpl)rule;
/* 126:178 */         SelectorList selectors = styleRule.getSelectors();
/* 127:179 */         for (int j = 0; j < selectors.getLength(); j++)
/* 128:    */         {
/* 129:180 */           Selector selector = selectors.item(j);
/* 130:181 */           boolean selected = selects(selector, e);
/* 131:182 */           if (selected)
/* 132:    */           {
/* 133:183 */             CSSStyleDeclaration dec = styleRule.getStyle();
/* 134:184 */             style.applyStyleFromSelector(dec, selector);
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:188 */       else if (rule.getType() == 3)
/* 139:    */       {
/* 140:189 */         CSSImportRuleImpl importRule = (CSSImportRuleImpl)rule;
/* 141:190 */         CSSStyleSheet sheet = (CSSStyleSheet)this.imports_.get(importRule);
/* 142:191 */         if (sheet == null)
/* 143:    */         {
/* 144:193 */           String uri = this.uri_ != null ? this.uri_ : e.getPage().getWebResponse().getWebRequest().getUrl().toExternalForm();
/* 145:    */           
/* 146:195 */           String href = importRule.getHref();
/* 147:196 */           String url = UrlUtils.resolveUrl(uri, href);
/* 148:197 */           sheet = loadStylesheet(getWindow(), this.ownerNode_, null, url);
/* 149:198 */           this.imports_.put(importRule, sheet);
/* 150:    */         }
/* 151:201 */         if (!alreadyProcessing.contains(sheet.getUri()))
/* 152:    */         {
/* 153:202 */           org.w3c.dom.css.CSSRuleList sheetRules = sheet.getWrappedSheet().getCssRules();
/* 154:203 */           alreadyProcessing.add(getUri());
/* 155:204 */           sheet.modifyIfNecessary(style, element, sheetRules, alreadyProcessing);
/* 156:    */         }
/* 157:    */       }
/* 158:207 */       else if (rule.getType() == 4)
/* 159:    */       {
/* 160:208 */         CSSMediaRuleImpl mediaRule = (CSSMediaRuleImpl)rule;
/* 161:209 */         String media = mediaRule.getMedia().getMediaText();
/* 162:210 */         if (isActive(media))
/* 163:    */         {
/* 164:211 */           org.w3c.dom.css.CSSRuleList internalRules = mediaRule.getCssRules();
/* 165:212 */           modifyIfNecessary(style, element, internalRules, alreadyProcessing);
/* 166:    */         }
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static CSSStyleSheet loadStylesheet(Window window, HTMLElement element, HtmlLink link, String url)
/* 172:    */   {
/* 173:229 */     HtmlPage page = (HtmlPage)element.getDomNodeOrDie().getPage();
/* 174:230 */     String uri = page.getWebResponse().getWebRequest().getUrl().toExternalForm();
/* 175:    */     CSSStyleSheet sheet;
/* 176:    */     try
/* 177:    */     {
/* 178:234 */       WebClient client = page.getWebClient();
/* 179:    */       WebRequest request;
/* 180:    */       WebRequest request;
/* 181:235 */       if (link != null)
/* 182:    */       {
/* 183:237 */         request = link.getWebRequest();
/* 184:    */       }
/* 185:    */       else
/* 186:    */       {
/* 187:241 */         request = new WebRequest(new URL(url));
/* 188:242 */         String referer = page.getWebResponse().getWebRequest().getUrl().toExternalForm();
/* 189:243 */         request.setAdditionalHeader("Referer", referer);
/* 190:    */       }
/* 191:246 */       uri = request.getUrl().toExternalForm();
/* 192:247 */       Cache cache = client.getCache();
/* 193:248 */       Object fromCache = cache.getCachedObject(request);
/* 194:    */       CSSStyleSheet sheet;
/* 195:249 */       if ((fromCache != null) && ((fromCache instanceof org.w3c.dom.css.CSSStyleSheet)))
/* 196:    */       {
/* 197:250 */         sheet = new CSSStyleSheet(element, (org.w3c.dom.css.CSSStyleSheet)fromCache, uri);
/* 198:    */       }
/* 199:    */       else
/* 200:    */       {
/* 201:253 */         WebResponse response = client.loadWebResponse(request);
/* 202:254 */         uri = response.getWebRequest().getUrl().toExternalForm();
/* 203:255 */         client.printContentIfNecessary(response);
/* 204:256 */         client.throwFailingHttpStatusCodeExceptionIfNecessary(response);
/* 205:    */         
/* 206:258 */         InputSource source = new InputSource();
/* 207:259 */         source.setByteStream(response.getContentAsStream());
/* 208:260 */         source.setEncoding(response.getContentCharset());
/* 209:261 */         sheet = new CSSStyleSheet(element, source, uri);
/* 210:262 */         cache.cacheIfPossible(request, response, sheet.getWrappedSheet());
/* 211:    */       }
/* 212:    */     }
/* 213:    */     catch (FailingHttpStatusCodeException e)
/* 214:    */     {
/* 215:267 */       LOG.error(e.getMessage());
/* 216:268 */       InputSource source = new InputSource(new StringReader(""));
/* 217:269 */       sheet = new CSSStyleSheet(element, source, uri);
/* 218:    */     }
/* 219:    */     catch (IOException e)
/* 220:    */     {
/* 221:273 */       LOG.error(e.getMessage());
/* 222:274 */       InputSource source = new InputSource(new StringReader(""));
/* 223:275 */       sheet = new CSSStyleSheet(element, source, uri);
/* 224:    */     }
/* 225:    */     catch (Exception e)
/* 226:    */     {
/* 227:279 */       throw Context.reportRuntimeError("Exception: " + e);
/* 228:    */     }
/* 229:281 */     return sheet;
/* 230:    */   }
/* 231:    */   
/* 232:    */   boolean selects(Selector selector, HtmlElement element)
/* 233:    */   {
/* 234:292 */     return selects(getBrowserVersion(), selector, element);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static boolean selects(BrowserVersion browserVersion, Selector selector, HtmlElement element)
/* 238:    */   {
/* 239:305 */     switch (selector.getSelectorType())
/* 240:    */     {
/* 241:    */     case 1: 
/* 242:307 */       return true;
/* 243:    */     case 11: 
/* 244:309 */       if (element.getParentNode() == element.getPage()) {
/* 245:310 */         return false;
/* 246:    */       }
/* 247:312 */       DescendantSelector cs = (DescendantSelector)selector;
/* 248:313 */       if (!(element.getParentNode() instanceof HtmlElement)) {
/* 249:314 */         return false;
/* 250:    */       }
/* 251:316 */       HtmlElement parent = (HtmlElement)element.getParentNode();
/* 252:317 */       return (selects(browserVersion, cs.getSimpleSelector(), element)) && (parent != null) && (selects(browserVersion, cs.getAncestorSelector(), parent));
/* 253:    */     case 10: 
/* 254:320 */       DescendantSelector ds = (DescendantSelector)selector;
/* 255:321 */       if (selects(browserVersion, ds.getSimpleSelector(), element))
/* 256:    */       {
/* 257:322 */         DomNode ancestor = element.getParentNode();
/* 258:323 */         while ((ancestor instanceof HtmlElement))
/* 259:    */         {
/* 260:324 */           if (selects(browserVersion, ds.getAncestorSelector(), (HtmlElement)ancestor)) {
/* 261:325 */             return true;
/* 262:    */           }
/* 263:327 */           ancestor = ancestor.getParentNode();
/* 264:    */         }
/* 265:    */       }
/* 266:330 */       return false;
/* 267:    */     case 0: 
/* 268:332 */       ConditionalSelector conditional = (ConditionalSelector)selector;
/* 269:333 */       Condition condition = conditional.getCondition();
/* 270:334 */       return (selects(browserVersion, conditional.getSimpleSelector(), element)) && (selects(browserVersion, condition, element));
/* 271:    */     case 4: 
/* 272:337 */       ElementSelector es = (ElementSelector)selector;
/* 273:338 */       String name = es.getLocalName();
/* 274:339 */       return (name == null) || (name.equalsIgnoreCase(element.getTagName()));
/* 275:    */     case 2: 
/* 276:341 */       return "html".equalsIgnoreCase(element.getTagName());
/* 277:    */     case 12: 
/* 278:343 */       SiblingSelector ss = (SiblingSelector)selector;
/* 279:344 */       DomNode prev = element.getPreviousSibling();
/* 280:345 */       return ((prev instanceof HtmlElement)) && (selects(browserVersion, ss.getSelector(), (HtmlElement)prev)) && (selects(browserVersion, ss.getSiblingSelector(), element));
/* 281:    */     case 3: 
/* 282:349 */       NegativeSelector ns = (NegativeSelector)selector;
/* 283:350 */       return !selects(browserVersion, ns.getSimpleSelector(), element);
/* 284:    */     case 5: 
/* 285:    */     case 6: 
/* 286:    */     case 7: 
/* 287:    */     case 8: 
/* 288:    */     case 9: 
/* 289:356 */       return false;
/* 290:    */     }
/* 291:358 */     LOG.error("Unknown CSS selector type '" + selector.getSelectorType() + "'.");
/* 292:359 */     return false;
/* 293:    */   }
/* 294:    */   
/* 295:    */   static boolean selects(BrowserVersion browserVersion, Condition condition, HtmlElement element)
/* 296:    */   {
/* 297:372 */     switch (condition.getConditionType())
/* 298:    */     {
/* 299:    */     case 5: 
/* 300:374 */       AttributeCondition ac4 = (AttributeCondition)condition;
/* 301:375 */       return ac4.getValue().equals(element.getId());
/* 302:    */     case 9: 
/* 303:377 */       AttributeCondition ac3 = (AttributeCondition)condition;
/* 304:378 */       String v3 = ac3.getValue();
/* 305:379 */       String a3 = element.getAttribute("class");
/* 306:380 */       return (a3.equals(v3)) || (a3.startsWith(v3 + " ")) || (a3.endsWith(" " + v3)) || (a3.contains(" " + v3 + " "));
/* 307:    */     case 0: 
/* 308:382 */       CombinatorCondition cc1 = (CombinatorCondition)condition;
/* 309:383 */       return (selects(browserVersion, cc1.getFirstCondition(), element)) && (selects(browserVersion, cc1.getSecondCondition(), element));
/* 310:    */     case 4: 
/* 311:386 */       AttributeCondition ac1 = (AttributeCondition)condition;
/* 312:387 */       if (ac1.getSpecified()) {
/* 313:388 */         return element.getAttribute(ac1.getLocalName()).equals(ac1.getValue());
/* 314:    */       }
/* 315:390 */       return element.hasAttribute(ac1.getLocalName());
/* 316:    */     case 8: 
/* 317:392 */       AttributeCondition ac2 = (AttributeCondition)condition;
/* 318:393 */       String v = ac2.getValue();
/* 319:394 */       String a = element.getAttribute(ac2.getLocalName());
/* 320:395 */       return (a.equals(v)) || (a.startsWith(v + "-")) || (a.endsWith("-" + v)) || (a.contains("-" + v + "-"));
/* 321:    */     case 7: 
/* 322:397 */       AttributeCondition ac5 = (AttributeCondition)condition;
/* 323:398 */       String v2 = ac5.getValue();
/* 324:399 */       String a2 = element.getAttribute(ac5.getLocalName());
/* 325:400 */       return (a2.equals(v2)) || (a2.startsWith(v2 + " ")) || (a2.endsWith(" " + v2)) || (a2.contains(" " + v2 + " "));
/* 326:    */     case 1: 
/* 327:402 */       CombinatorCondition cc2 = (CombinatorCondition)condition;
/* 328:403 */       return (selects(browserVersion, cc2.getFirstCondition(), element)) || (selects(browserVersion, cc2.getSecondCondition(), element));
/* 329:    */     case 2: 
/* 330:406 */       NegativeCondition nc = (NegativeCondition)condition;
/* 331:407 */       return !selects(browserVersion, nc.getCondition(), element);
/* 332:    */     case 11: 
/* 333:409 */       return element.getParentNode().getChildNodes().getLength() == 1;
/* 334:    */     case 13: 
/* 335:411 */       ContentCondition cc = (ContentCondition)condition;
/* 336:412 */       return element.asText().contains(cc.getData());
/* 337:    */     case 6: 
/* 338:414 */       if (!browserVersion.hasFeature(BrowserVersionFeatures.CSS_SELECTOR_LANG)) {
/* 339:415 */         return false;
/* 340:    */       }
/* 341:417 */       String lcLang = ((LangCondition)condition).getLang();
/* 342:418 */       for (DomNode node = element; (node instanceof HtmlElement); node = node.getParentNode())
/* 343:    */       {
/* 344:419 */         String nodeLang = ((HtmlElement)node).getAttribute("lang");
/* 345:421 */         if ((nodeLang.startsWith(lcLang)) && ((nodeLang.length() == lcLang.length()) || ('-' == nodeLang.charAt(lcLang.length())))) {
/* 346:423 */           return true;
/* 347:    */         }
/* 348:    */       }
/* 349:426 */       return false;
/* 350:    */     case 12: 
/* 351:428 */       String tagName = element.getTagName();
/* 352:429 */       return ((HtmlPage)element.getPage()).getElementsByTagName(tagName).getLength() == 1;
/* 353:    */     case 10: 
/* 354:431 */       return selectsPseudoClass(browserVersion, (AttributeCondition)condition, element);
/* 355:    */     case 3: 
/* 356:433 */       return false;
/* 357:    */     }
/* 358:435 */     LOG.error("Unknown CSS condition type '" + condition.getConditionType() + "'.");
/* 359:436 */     return false;
/* 360:    */   }
/* 361:    */   
/* 362:    */   private static boolean selectsPseudoClass(BrowserVersion browserVersion, AttributeCondition condition, HtmlElement element)
/* 363:    */   {
/* 364:442 */     if (!browserVersion.hasFeature(BrowserVersionFeatures.CSS_SPECIAL_PSEUDO_CLASSES)) {
/* 365:443 */       return false;
/* 366:    */     }
/* 367:446 */     String value = condition.getValue();
/* 368:447 */     if ("root".equals(value)) {
/* 369:448 */       return element == element.getPage().getDocumentElement();
/* 370:    */     }
/* 371:450 */     if ("enabled".equals(value)) {
/* 372:451 */       return (((element instanceof HtmlInput)) && (!((HtmlInput)element).isDisabled())) || (((element instanceof HtmlSelect)) && (!((HtmlSelect)element).isDisabled()));
/* 373:    */     }
/* 374:454 */     if ("disabled".equals(value)) {
/* 375:455 */       return (((element instanceof HtmlInput)) && (((HtmlInput)element).isDisabled())) || (((element instanceof HtmlSelect)) && (((HtmlSelect)element).isDisabled()));
/* 376:    */     }
/* 377:458 */     if ("checked".equals(value)) {
/* 378:459 */       return (((element instanceof HtmlCheckBoxInput)) && (((HtmlCheckBoxInput)element).isChecked())) || (((element instanceof HtmlRadioButtonInput)) && (((HtmlRadioButtonInput)element).isChecked()));
/* 379:    */     }
/* 380:462 */     return false;
/* 381:    */   }
/* 382:    */   
/* 383:    */   private org.w3c.dom.css.CSSStyleSheet parseCSS(InputSource source)
/* 384:    */   {
/* 385:    */     org.w3c.dom.css.CSSStyleSheet ss;
/* 386:    */     try
/* 387:    */     {
/* 388:475 */       ErrorHandler errorHandler = getWindow().getWebWindow().getWebClient().getCssErrorHandler();
/* 389:476 */       CSSOMParser parser = new CSSOMParser(new SACParserCSS21());
/* 390:477 */       parser.setErrorHandler(errorHandler);
/* 391:478 */       ss = parser.parseStyleSheet(source, null, null);
/* 392:    */     }
/* 393:    */     catch (Exception e)
/* 394:    */     {
/* 395:481 */       LOG.error("Error parsing CSS from '" + toString(source) + "': " + e.getMessage(), e);
/* 396:482 */       ss = new CSSStyleSheetImpl();
/* 397:    */     }
/* 398:    */     catch (Error e)
/* 399:    */     {
/* 400:486 */       LOG.error("Error parsing CSS from '" + toString(source) + "': " + e.getMessage(), e);
/* 401:487 */       ss = new CSSStyleSheetImpl();
/* 402:    */     }
/* 403:489 */     return ss;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public SelectorList parseSelectors(InputSource source)
/* 407:    */   {
/* 408:    */     SelectorList selectors;
/* 409:    */     try
/* 410:    */     {
/* 411:502 */       ErrorHandler errorHandler = getWindow().getWebWindow().getWebClient().getCssErrorHandler();
/* 412:503 */       CSSOMParser parser = new CSSOMParser(new SACParserCSS21());
/* 413:504 */       parser.setErrorHandler(errorHandler);
/* 414:505 */       selectors = parser.parseSelectors(source);
/* 415:507 */       if (null == selectors) {
/* 416:508 */         selectors = new SelectorListImpl();
/* 417:    */       }
/* 418:    */     }
/* 419:    */     catch (Exception e)
/* 420:    */     {
/* 421:512 */       LOG.error("Error parsing CSS selectors from '" + toString(source) + "': " + e.getMessage(), e);
/* 422:513 */       selectors = new SelectorListImpl();
/* 423:    */     }
/* 424:    */     catch (Error e)
/* 425:    */     {
/* 426:517 */       LOG.error("Error parsing CSS selectors from '" + toString(source) + "': " + e.getMessage(), e);
/* 427:518 */       selectors = new SelectorListImpl();
/* 428:    */     }
/* 429:520 */     return selectors;
/* 430:    */   }
/* 431:    */   
/* 432:    */   private static String toString(InputSource source)
/* 433:    */   {
/* 434:    */     try
/* 435:    */     {
/* 436:530 */       Reader reader = source.getCharacterStream();
/* 437:531 */       if (null != reader)
/* 438:    */       {
/* 439:533 */         if ((reader instanceof StringReader))
/* 440:    */         {
/* 441:534 */           StringReader sr = (StringReader)reader;
/* 442:535 */           sr.reset();
/* 443:    */         }
/* 444:537 */         return IOUtils.toString(reader);
/* 445:    */       }
/* 446:539 */       InputStream is = source.getByteStream();
/* 447:540 */       if (null != is)
/* 448:    */       {
/* 449:542 */         if ((is instanceof ByteArrayInputStream))
/* 450:    */         {
/* 451:543 */           ByteArrayInputStream bis = (ByteArrayInputStream)is;
/* 452:544 */           bis.reset();
/* 453:    */         }
/* 454:546 */         return IOUtils.toString(is);
/* 455:    */       }
/* 456:548 */       return "";
/* 457:    */     }
/* 458:    */     catch (IOException e) {}
/* 459:551 */     return "";
/* 460:    */   }
/* 461:    */   
/* 462:    */   public HTMLElement jsxGet_ownerNode()
/* 463:    */   {
/* 464:560 */     return this.ownerNode_;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public HTMLElement jsxGet_owningElement()
/* 468:    */   {
/* 469:568 */     return this.ownerNode_;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public CSSRuleList jsxGet_rules()
/* 473:    */   {
/* 474:576 */     return jsxGet_cssRules();
/* 475:    */   }
/* 476:    */   
/* 477:    */   public CSSRuleList jsxGet_cssRules()
/* 478:    */   {
/* 479:584 */     if (this.cssRules_ == null) {
/* 480:585 */       this.cssRules_ = new CSSRuleList(this);
/* 481:    */     }
/* 482:587 */     return this.cssRules_;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public String jsxGet_href()
/* 486:    */   {
/* 487:595 */     BrowserVersion version = getBrowserVersion();
/* 488:597 */     if (this.ownerNode_ != null)
/* 489:    */     {
/* 490:598 */       DomNode node = this.ownerNode_.getDomNodeOrDie();
/* 491:599 */       if ((node instanceof HtmlLink))
/* 492:    */       {
/* 493:601 */         HtmlLink link = (HtmlLink)node;
/* 494:602 */         HtmlPage page = (HtmlPage)link.getPage();
/* 495:603 */         String href = link.getHrefAttribute();
/* 496:604 */         if (!version.hasFeature(BrowserVersionFeatures.STYLESHEET_HREF_EXPANDURL)) {
/* 497:606 */           return href;
/* 498:    */         }
/* 499:    */         try
/* 500:    */         {
/* 501:610 */           URL url = page.getFullyQualifiedUrl(href);
/* 502:611 */           return url.toExternalForm();
/* 503:    */         }
/* 504:    */         catch (MalformedURLException e)
/* 505:    */         {
/* 506:615 */           LOG.warn(e.getMessage(), e);
/* 507:    */         }
/* 508:    */       }
/* 509:    */     }
/* 510:621 */     if (version.hasFeature(BrowserVersionFeatures.STYLESHEET_HREF_STYLE_EMPTY)) {
/* 511:622 */       return "";
/* 512:    */     }
/* 513:624 */     if (version.hasFeature(BrowserVersionFeatures.STYLESHEET_HREF_STYLE_NULL)) {
/* 514:625 */       return null;
/* 515:    */     }
/* 516:628 */     DomNode node = this.ownerNode_.getDomNodeOrDie();
/* 517:629 */     HtmlPage page = (HtmlPage)node.getPage();
/* 518:630 */     URL url = page.getWebResponse().getWebRequest().getUrl();
/* 519:631 */     return url.toExternalForm();
/* 520:    */   }
/* 521:    */   
/* 522:    */   public int jsxFunction_insertRule(String rule, int position)
/* 523:    */   {
/* 524:643 */     return this.wrapped_.insertRule(rule.trim(), position);
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void jsxFunction_deleteRule(int position)
/* 528:    */   {
/* 529:652 */     this.wrapped_.deleteRule(position);
/* 530:    */   }
/* 531:    */   
/* 532:    */   public int jsxFunction_addRule(String selector, String rule)
/* 533:    */   {
/* 534:663 */     String completeRule = selector.trim() + " {" + rule + "}";
/* 535:664 */     this.wrapped_.insertRule(completeRule, this.wrapped_.getCssRules().getLength());
/* 536:665 */     return -1;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void jsxFunction_removeRule(int position)
/* 540:    */   {
/* 541:674 */     this.wrapped_.deleteRule(position);
/* 542:    */   }
/* 543:    */   
/* 544:    */   public String getUri()
/* 545:    */   {
/* 546:682 */     return this.uri_;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public boolean isActive()
/* 550:    */   {
/* 551:691 */     HtmlElement e = this.ownerNode_.getDomNodeOrNull();
/* 552:    */     String media;
/* 553:    */     String media;
/* 554:692 */     if ((e instanceof HtmlStyle))
/* 555:    */     {
/* 556:693 */       HtmlStyle style = (HtmlStyle)e;
/* 557:694 */       media = style.getMediaAttribute();
/* 558:    */     }
/* 559:    */     else
/* 560:    */     {
/* 561:    */       String media;
/* 562:696 */       if ((e instanceof HtmlLink))
/* 563:    */       {
/* 564:697 */         HtmlLink link = (HtmlLink)e;
/* 565:698 */         media = link.getMediaAttribute();
/* 566:    */       }
/* 567:    */       else
/* 568:    */       {
/* 569:701 */         media = "";
/* 570:    */       }
/* 571:    */     }
/* 572:703 */     return isActive(media);
/* 573:    */   }
/* 574:    */   
/* 575:    */   private static boolean isActive(String media)
/* 576:    */   {
/* 577:707 */     if (StringUtils.isBlank(media)) {
/* 578:708 */       return true;
/* 579:    */     }
/* 580:710 */     for (String s : StringUtils.split(media, ','))
/* 581:    */     {
/* 582:711 */       String mediaType = s.trim();
/* 583:712 */       if (("screen".equals(mediaType)) || ("all".equals(mediaType))) {
/* 584:713 */         return true;
/* 585:    */       }
/* 586:    */     }
/* 587:716 */     return false;
/* 588:    */   }
/* 589:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet
 * JD-Core Version:    0.7.0.1
 */