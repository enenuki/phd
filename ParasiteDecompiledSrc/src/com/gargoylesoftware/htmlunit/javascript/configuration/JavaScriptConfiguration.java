/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.configuration;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HtmlAbbreviated;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlAcronym;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlAddress;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlBackgroundSound;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlBidirectionalOverride;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlBig;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.HtmlBlink;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.HtmlBlockQuote;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.HtmlBold;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.HtmlCenter;
/*  14:    */ import com.gargoylesoftware.htmlunit.html.HtmlCitation;
/*  15:    */ import com.gargoylesoftware.htmlunit.html.HtmlCode;
/*  16:    */ import com.gargoylesoftware.htmlunit.html.HtmlDefinition;
/*  17:    */ import com.gargoylesoftware.htmlunit.html.HtmlDefinitionDescription;
/*  18:    */ import com.gargoylesoftware.htmlunit.html.HtmlDefinitionTerm;
/*  19:    */ import com.gargoylesoftware.htmlunit.html.HtmlDivision;
/*  20:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  21:    */ import com.gargoylesoftware.htmlunit.html.HtmlEmphasis;
/*  22:    */ import com.gargoylesoftware.htmlunit.html.HtmlExample;
/*  23:    */ import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
/*  24:    */ import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
/*  25:    */ import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
/*  26:    */ import com.gargoylesoftware.htmlunit.html.HtmlHeading4;
/*  27:    */ import com.gargoylesoftware.htmlunit.html.HtmlHeading5;
/*  28:    */ import com.gargoylesoftware.htmlunit.html.HtmlHeading6;
/*  29:    */ import com.gargoylesoftware.htmlunit.html.HtmlInlineQuotation;
/*  30:    */ import com.gargoylesoftware.htmlunit.html.HtmlItalic;
/*  31:    */ import com.gargoylesoftware.htmlunit.html.HtmlKeyboard;
/*  32:    */ import com.gargoylesoftware.htmlunit.html.HtmlListing;
/*  33:    */ import com.gargoylesoftware.htmlunit.html.HtmlMarquee;
/*  34:    */ import com.gargoylesoftware.htmlunit.html.HtmlMultiColumn;
/*  35:    */ import com.gargoylesoftware.htmlunit.html.HtmlNoBreak;
/*  36:    */ import com.gargoylesoftware.htmlunit.html.HtmlNoEmbed;
/*  37:    */ import com.gargoylesoftware.htmlunit.html.HtmlNoFrames;
/*  38:    */ import com.gargoylesoftware.htmlunit.html.HtmlNoScript;
/*  39:    */ import com.gargoylesoftware.htmlunit.html.HtmlPlainText;
/*  40:    */ import com.gargoylesoftware.htmlunit.html.HtmlS;
/*  41:    */ import com.gargoylesoftware.htmlunit.html.HtmlSample;
/*  42:    */ import com.gargoylesoftware.htmlunit.html.HtmlSmall;
/*  43:    */ import com.gargoylesoftware.htmlunit.html.HtmlSpan;
/*  44:    */ import com.gargoylesoftware.htmlunit.html.HtmlStrike;
/*  45:    */ import com.gargoylesoftware.htmlunit.html.HtmlStrong;
/*  46:    */ import com.gargoylesoftware.htmlunit.html.HtmlSubscript;
/*  47:    */ import com.gargoylesoftware.htmlunit.html.HtmlSuperscript;
/*  48:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
/*  49:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableColumn;
/*  50:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableColumnGroup;
/*  51:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableFooter;
/*  52:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableHeader;
/*  53:    */ import com.gargoylesoftware.htmlunit.html.HtmlTeletype;
/*  54:    */ import com.gargoylesoftware.htmlunit.html.HtmlUnderlined;
/*  55:    */ import com.gargoylesoftware.htmlunit.html.HtmlVariable;
/*  56:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  57:    */ import com.gargoylesoftware.htmlunit.javascript.StrictErrorHandler;
/*  58:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDivElement;
/*  59:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLHeadingElement;
/*  60:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLQuoteElement;
/*  61:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLSpanElement;
/*  62:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableColElement;
/*  63:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableSectionElement;
/*  64:    */ import java.io.File;
/*  65:    */ import java.io.FileInputStream;
/*  66:    */ import java.io.IOException;
/*  67:    */ import java.io.InputStream;
/*  68:    */ import java.io.InputStreamReader;
/*  69:    */ import java.io.Reader;
/*  70:    */ import java.util.Collections;
/*  71:    */ import java.util.HashMap;
/*  72:    */ import java.util.Map;
/*  73:    */ import java.util.WeakHashMap;
/*  74:    */ import javax.xml.parsers.DocumentBuilder;
/*  75:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  76:    */ import org.apache.commons.logging.Log;
/*  77:    */ import org.apache.commons.logging.LogFactory;
/*  78:    */ import org.w3c.dom.Document;
/*  79:    */ import org.w3c.dom.Element;
/*  80:    */ import org.w3c.dom.Node;
/*  81:    */ import org.xml.sax.InputSource;
/*  82:    */ import org.xml.sax.SAXParseException;
/*  83:    */ 
/*  84:    */ public final class JavaScriptConfiguration
/*  85:    */ {
/*  86:112 */   private static final Log LOG = LogFactory.getLog(JavaScriptConfiguration.class);
/*  87:    */   private static Document XmlDocument_;
/*  88:    */   public static final int ENABLED = 1;
/*  89:    */   public static final int DISABLED = 2;
/*  90:    */   public static final int NOT_FOUND = 3;
/*  91:127 */   private static Map<BrowserVersion, JavaScriptConfiguration> ConfigurationMap_ = new WeakHashMap(11);
/*  92:130 */   private static Map<String, String> ClassnameMap_ = new HashMap();
/*  93:    */   private Map<Class<? extends HtmlElement>, Class<? extends SimpleScriptable>> htmlJavaScriptMap_;
/*  94:    */   private final Map<String, ClassConfiguration> configuration_;
/*  95:    */   
/*  96:    */   private JavaScriptConfiguration(BrowserVersion browser)
/*  97:    */   {
/*  98:141 */     if (XmlDocument_ == null) {
/*  99:142 */       loadConfiguration();
/* 100:    */     }
/* 101:144 */     if (XmlDocument_ == null) {
/* 102:145 */       throw new IllegalStateException("Configuration was not initialized - see log for details");
/* 103:    */     }
/* 104:147 */     this.configuration_ = buildUsageMap(browser);
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected static boolean isDocumentLoaded()
/* 108:    */   {
/* 109:156 */     return XmlDocument_ != null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected static void resetClassForTesting()
/* 113:    */   {
/* 114:163 */     XmlDocument_ = null;
/* 115:164 */     ConfigurationMap_ = new WeakHashMap(11);
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected static void setXmlDocument(Document document)
/* 119:    */   {
/* 120:172 */     XmlDocument_ = document;
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected static void loadConfiguration()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:180 */       Reader reader = getConfigurationFileAsReader();
/* 128:181 */       if (reader == null)
/* 129:    */       {
/* 130:182 */         LOG.error("Unable to load JavaScriptConfiguration.xml");
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:185 */         loadConfiguration(reader);
/* 135:186 */         reader.close();
/* 136:    */       }
/* 137:    */     }
/* 138:    */     catch (Exception e)
/* 139:    */     {
/* 140:190 */       LOG.error("Error when loading JavascriptConfiguration.xml", e);
/* 141:191 */       e.printStackTrace();
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected static void loadConfiguration(Reader configurationReader)
/* 146:    */   {
/* 147:201 */     InputSource inputSource = new InputSource(configurationReader);
/* 148:    */     try
/* 149:    */     {
/* 150:204 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 151:205 */       factory.setNamespaceAware(true);
/* 152:206 */       factory.setValidating(false);
/* 153:    */       
/* 154:208 */       DocumentBuilder documentBuilder = factory.newDocumentBuilder();
/* 155:209 */       documentBuilder.setErrorHandler(new StrictErrorHandler());
/* 156:    */       
/* 157:211 */       XmlDocument_ = documentBuilder.parse(inputSource);
/* 158:    */     }
/* 159:    */     catch (SAXParseException parseException)
/* 160:    */     {
/* 161:214 */       LOG.error("line=[" + parseException.getLineNumber() + "] columnNumber=[" + parseException.getColumnNumber() + "] systemId=[" + parseException.getSystemId() + "] publicId=[" + parseException.getPublicId() + "]", parseException);
/* 162:    */     }
/* 163:    */     catch (Exception e)
/* 164:    */     {
/* 165:220 */       LOG.error("Error when loading JavascriptConfiguration.xml", e);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static synchronized JavaScriptConfiguration getInstance(BrowserVersion browserVersion)
/* 170:    */   {
/* 171:231 */     if (browserVersion == null) {
/* 172:232 */       throw new IllegalStateException("BrowserVersion must be defined");
/* 173:    */     }
/* 174:234 */     JavaScriptConfiguration configuration = (JavaScriptConfiguration)ConfigurationMap_.get(browserVersion);
/* 175:236 */     if (configuration == null)
/* 176:    */     {
/* 177:237 */       configuration = new JavaScriptConfiguration(browserVersion);
/* 178:238 */       ConfigurationMap_.put(browserVersion, configuration);
/* 179:    */     }
/* 180:240 */     return configuration;
/* 181:    */   }
/* 182:    */   
/* 183:    */   static JavaScriptConfiguration getAllEntries()
/* 184:    */   {
/* 185:249 */     JavaScriptConfiguration configuration = new JavaScriptConfiguration(null);
/* 186:250 */     return configuration;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private static Reader getConfigurationFileAsReader()
/* 190:    */   {
/* 191:254 */     Class<?> clazz = JavaScriptConfiguration.class;
/* 192:255 */     String name = clazz.getPackage().getName().replace('.', '/') + '/' + "JavaScriptConfiguration.xml";
/* 193:256 */     InputStream inputStream = clazz.getClassLoader().getResourceAsStream(name);
/* 194:257 */     if (inputStream == null) {
/* 195:    */       try
/* 196:    */       {
/* 197:259 */         String localizedName = name.replace('/', File.separatorChar);
/* 198:260 */         inputStream = new FileInputStream(localizedName);
/* 199:    */       }
/* 200:    */       catch (IOException e) {}
/* 201:    */     }
/* 202:268 */     if (inputStream == null) {
/* 203:    */       try
/* 204:    */       {
/* 205:270 */         String localizedName = ("./src/java" + name).replace('/', File.separatorChar);
/* 206:271 */         inputStream = new FileInputStream(localizedName);
/* 207:    */       }
/* 208:    */       catch (IOException e) {}
/* 209:    */     }
/* 210:277 */     return new InputStreamReader(inputStream);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Iterable<ClassConfiguration> getAll()
/* 214:    */   {
/* 215:285 */     return this.configuration_.values();
/* 216:    */   }
/* 217:    */   
/* 218:    */   private Map<String, ClassConfiguration> buildUsageMap(BrowserVersion browser)
/* 219:    */   {
/* 220:289 */     Map<String, ClassConfiguration> classMap = new HashMap(30);
/* 221:290 */     Node node = XmlDocument_.getDocumentElement().getFirstChild();
/* 222:    */     
/* 223:292 */     Map<String, ClassConfiguration> virtualClasses = new HashMap();
/* 224:294 */     while (node != null)
/* 225:    */     {
/* 226:295 */       if ((node instanceof Element))
/* 227:    */       {
/* 228:296 */         Element element = (Element)node;
/* 229:297 */         if (("class".equals(element.getTagName())) && 
/* 230:298 */           (!testToExcludeElement(element, browser)))
/* 231:    */         {
/* 232:299 */           String hostClassName = element.getAttribute("classname");
/* 233:300 */           if (hostClassName.startsWith("#"))
/* 234:    */           {
/* 235:302 */             String extendsClassName = element.getAttribute("extends");
/* 236:303 */             ClassConfiguration parentConfig = (ClassConfiguration)classMap.get(extendsClassName);
/* 237:304 */             if (parentConfig == null) {
/* 238:305 */               throw new RuntimeException(extendsClassName + " should be specified before " + hostClassName);
/* 239:    */             }
/* 240:308 */             element.setAttribute("classname", parentConfig.getHostClass().getName());
/* 241:    */           }
/* 242:    */           try
/* 243:    */           {
/* 244:312 */             ClassConfiguration config = parseClassElement(element, browser);
/* 245:313 */             if (config != null) {
/* 246:314 */               if (hostClassName.startsWith("#"))
/* 247:    */               {
/* 248:315 */                 virtualClasses.put(hostClassName, config);
/* 249:316 */                 element.setAttribute("classname", hostClassName);
/* 250:    */               }
/* 251:    */               else
/* 252:    */               {
/* 253:319 */                 classMap.put(config.getHostClass().getSimpleName(), config);
/* 254:    */               }
/* 255:    */             }
/* 256:    */           }
/* 257:    */           catch (ClassNotFoundException e)
/* 258:    */           {
/* 259:324 */             throw new IllegalStateException("The class was not found for '" + element.getAttribute("classname") + "'");
/* 260:    */           }
/* 261:    */         }
/* 262:    */       }
/* 263:330 */       node = node.getNextSibling();
/* 264:    */     }
/* 265:334 */     for (ClassConfiguration config : classMap.values())
/* 266:    */     {
/* 267:335 */       String extendsClassName = config.getExtendedClassName();
/* 268:336 */       if (extendsClassName.startsWith("#"))
/* 269:    */       {
/* 270:337 */         ClassConfiguration virtualClassConfig = (ClassConfiguration)virtualClasses.get(extendsClassName);
/* 271:338 */         if (virtualClassConfig == null) {
/* 272:339 */           throw new RuntimeException("Virtual config >" + extendsClassName + "< doesn't exist!");
/* 273:    */         }
/* 274:342 */         config.addAllDefinitions(virtualClassConfig);
/* 275:    */         
/* 276:344 */         config.setExtendedClassName(virtualClassConfig.getExtendedClassName());
/* 277:    */       }
/* 278:    */     }
/* 279:348 */     return Collections.unmodifiableMap(classMap);
/* 280:    */   }
/* 281:    */   
/* 282:    */   private ClassConfiguration parseClassElement(Element element, BrowserVersion browser)
/* 283:    */     throws ClassNotFoundException
/* 284:    */   {
/* 285:361 */     String notImplemented = element.getAttribute("notImplemented");
/* 286:362 */     if ("true".equalsIgnoreCase(notImplemented)) {
/* 287:363 */       return null;
/* 288:    */     }
/* 289:365 */     String hostClassName = element.getAttribute("classname");
/* 290:366 */     String jsConstructor = element.getAttribute("jsConstructor");
/* 291:367 */     String extendsClassName = element.getAttribute("extends");
/* 292:368 */     String htmlClassName = element.getAttribute("htmlClass");
/* 293:369 */     boolean jsObjectFlag = false;
/* 294:370 */     String jsObjectStr = element.getAttribute("JSObject");
/* 295:371 */     if ("true".equalsIgnoreCase(jsObjectStr)) {
/* 296:372 */       jsObjectFlag = true;
/* 297:    */     }
/* 298:374 */     ClassConfiguration classConfiguration = new ClassConfiguration(hostClassName, jsConstructor, extendsClassName, htmlClassName, jsObjectFlag);
/* 299:    */     
/* 300:376 */     String simpleClassName = hostClassName.substring(hostClassName.lastIndexOf('.') + 1);
/* 301:377 */     ClassnameMap_.put(hostClassName, simpleClassName);
/* 302:378 */     Node node = element.getFirstChild();
/* 303:379 */     while (node != null)
/* 304:    */     {
/* 305:380 */       if ((node instanceof Element))
/* 306:    */       {
/* 307:381 */         Element childElement = (Element)node;
/* 308:382 */         String tagName = childElement.getTagName();
/* 309:383 */         if ("property".equals(tagName)) {
/* 310:384 */           parsePropertyElement(classConfiguration, childElement, browser);
/* 311:386 */         } else if ("function".equals(tagName)) {
/* 312:387 */           parseFunctionElement(classConfiguration, childElement, browser);
/* 313:389 */         } else if ("constant".equals(tagName)) {
/* 314:390 */           parseConstantElement(classConfiguration, childElement, browser);
/* 315:392 */         } else if ("browser".equals(tagName))
/* 316:    */         {
/* 317:393 */           if (LOG.isDebugEnabled()) {
/* 318:394 */             LOG.debug("browser tag not yet handled for class " + hostClassName);
/* 319:    */           }
/* 320:    */         }
/* 321:397 */         else if (!"doclink".equals(tagName)) {
/* 322:401 */           throw new IllegalStateException("Do not understand element type '" + tagName + "' in '" + hostClassName + "'");
/* 323:    */         }
/* 324:    */       }
/* 325:405 */       node = node.getNextSibling();
/* 326:    */     }
/* 327:407 */     return classConfiguration;
/* 328:    */   }
/* 329:    */   
/* 330:    */   private void parsePropertyElement(ClassConfiguration classConfiguration, Element element, BrowserVersion browser)
/* 331:    */   {
/* 332:419 */     String notImplemented = element.getAttribute("notImplemented");
/* 333:420 */     if ("true".equalsIgnoreCase(notImplemented)) {
/* 334:421 */       return;
/* 335:    */     }
/* 336:423 */     if (testToExcludeElement(element, browser)) {
/* 337:424 */       return;
/* 338:    */     }
/* 339:426 */     String propertyName = element.getAttribute("name");
/* 340:427 */     boolean readable = false;
/* 341:428 */     boolean writable = false;
/* 342:429 */     String readFlag = element.getAttribute("readable");
/* 343:430 */     if ("true".equalsIgnoreCase(readFlag)) {
/* 344:431 */       readable = true;
/* 345:    */     }
/* 346:433 */     String writeFlag = element.getAttribute("writable");
/* 347:434 */     if ("true".equalsIgnoreCase(writeFlag)) {
/* 348:435 */       writable = true;
/* 349:    */     }
/* 350:437 */     classConfiguration.addProperty(propertyName, readable, writable);
/* 351:    */   }
/* 352:    */   
/* 353:    */   private void parseFunctionElement(ClassConfiguration classConfiguration, Element element, BrowserVersion browser)
/* 354:    */   {
/* 355:449 */     String notImplemented = element.getAttribute("notImplemented");
/* 356:450 */     if ("true".equalsIgnoreCase(notImplemented)) {
/* 357:451 */       return;
/* 358:    */     }
/* 359:453 */     String propertyName = element.getAttribute("name");
/* 360:454 */     if (testToExcludeElement(element, browser)) {
/* 361:455 */       return;
/* 362:    */     }
/* 363:457 */     classConfiguration.addFunction(propertyName);
/* 364:    */   }
/* 365:    */   
/* 366:    */   private void parseConstantElement(ClassConfiguration classConfiguration, Element element, BrowserVersion browser)
/* 367:    */   {
/* 368:469 */     if (testToExcludeElement(element, browser)) {
/* 369:470 */       return;
/* 370:    */     }
/* 371:472 */     String constantName = element.getAttribute("name");
/* 372:473 */     classConfiguration.addConstant(constantName);
/* 373:    */   }
/* 374:    */   
/* 375:    */   private boolean testToExcludeElement(Element element, BrowserVersion browser)
/* 376:    */   {
/* 377:484 */     if (browser == null) {
/* 378:485 */       return false;
/* 379:    */     }
/* 380:487 */     Node node = element.getFirstChild();
/* 381:488 */     boolean browserConstraint = false;
/* 382:489 */     boolean allowBrowser = false;
/* 383:490 */     while (node != null)
/* 384:    */     {
/* 385:491 */       if ((node instanceof Element))
/* 386:    */       {
/* 387:492 */         Element childElement = (Element)node;
/* 388:493 */         if ("browser".equals(childElement.getTagName()))
/* 389:    */         {
/* 390:494 */           browserConstraint = true;
/* 391:495 */           if (testToIncludeForBrowserConstraint(childElement, browser)) {
/* 392:496 */             allowBrowser = true;
/* 393:    */           }
/* 394:    */         }
/* 395:    */       }
/* 396:500 */       node = node.getNextSibling();
/* 397:    */     }
/* 398:502 */     if ((browserConstraint) && (!allowBrowser)) {
/* 399:503 */       return true;
/* 400:    */     }
/* 401:505 */     return false;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public ClassConfiguration getClassConfiguration(String classname)
/* 405:    */   {
/* 406:514 */     return (ClassConfiguration)this.configuration_.get(classname);
/* 407:    */   }
/* 408:    */   
/* 409:    */   private boolean testToIncludeForBrowserConstraint(Element element, BrowserVersion browser)
/* 410:    */   {
/* 411:518 */     if (((!browser.isIE()) || (!"Internet Explorer".equals(element.getAttribute("name")))) && ((!browser.isFirefox()) || (!"Firefox".equals(element.getAttribute("name"))))) {
/* 412:520 */       return false;
/* 413:    */     }
/* 414:522 */     String max = element.getAttribute("max-version");
/* 415:    */     float maxVersion;
/* 416:    */     float maxVersion;
/* 417:524 */     if (max.length() == 0) {
/* 418:525 */       maxVersion = 0.0F;
/* 419:    */     } else {
/* 420:528 */       maxVersion = Float.parseFloat(max);
/* 421:    */     }
/* 422:530 */     if ((maxVersion > 0.0F) && (browser.getBrowserVersionNumeric() > maxVersion)) {
/* 423:531 */       return false;
/* 424:    */     }
/* 425:535 */     String min = element.getAttribute("min-version");
/* 426:    */     float minVersion;
/* 427:    */     float minVersion;
/* 428:536 */     if (min.length() == 0) {
/* 429:537 */       minVersion = 0.0F;
/* 430:    */     } else {
/* 431:540 */       minVersion = Float.parseFloat(min);
/* 432:    */     }
/* 433:542 */     if ((minVersion > 0.0F) && (browser.getBrowserVersionNumeric() < minVersion)) {
/* 434:543 */       return false;
/* 435:    */     }
/* 436:545 */     return true;
/* 437:    */   }
/* 438:    */   
/* 439:    */   String getClassnameForClass(Class<?> clazz)
/* 440:    */   {
/* 441:557 */     String name = (String)ClassnameMap_.get(clazz.getName());
/* 442:558 */     if (name == null) {
/* 443:559 */       throw new IllegalStateException("Did not find the mapping of the class to the classname for " + clazz.getName());
/* 444:    */     }
/* 445:562 */     return name;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public Map<Class<? extends HtmlElement>, Class<? extends SimpleScriptable>> getHtmlJavaScriptMapping()
/* 449:    */   {
/* 450:574 */     if (this.htmlJavaScriptMap_ != null) {
/* 451:575 */       return this.htmlJavaScriptMap_;
/* 452:    */     }
/* 453:578 */     Map<Class<? extends HtmlElement>, Class<? extends SimpleScriptable>> map = new HashMap();
/* 454:581 */     for (String jsClassname : this.configuration_.keySet())
/* 455:    */     {
/* 456:582 */       ClassConfiguration classConfig = getClassConfiguration(jsClassname);
/* 457:583 */       String htmlClassname = classConfig.getHtmlClassname();
/* 458:584 */       if (htmlClassname != null) {
/* 459:    */         try
/* 460:    */         {
/* 461:586 */           Class<? extends HtmlElement> htmlClass = Class.forName(htmlClassname);
/* 462:589 */           if (LOG.isDebugEnabled()) {
/* 463:590 */             LOG.debug("Mapping " + htmlClass.getName() + " to " + jsClassname);
/* 464:    */           }
/* 465:592 */           while (!classConfig.isJsObject())
/* 466:    */           {
/* 467:593 */             jsClassname = classConfig.getExtendedClassName();
/* 468:594 */             classConfig = getClassConfiguration(jsClassname);
/* 469:    */           }
/* 470:596 */           map.put(htmlClass, classConfig.getHostClass());
/* 471:    */         }
/* 472:    */         catch (ClassNotFoundException e)
/* 473:    */         {
/* 474:599 */           throw new NoClassDefFoundError(e.getMessage());
/* 475:    */         }
/* 476:    */       }
/* 477:    */     }
/* 478:603 */     map.put(HtmlHeading1.class, HTMLHeadingElement.class);
/* 479:604 */     map.put(HtmlHeading2.class, HTMLHeadingElement.class);
/* 480:605 */     map.put(HtmlHeading3.class, HTMLHeadingElement.class);
/* 481:606 */     map.put(HtmlHeading4.class, HTMLHeadingElement.class);
/* 482:607 */     map.put(HtmlHeading5.class, HTMLHeadingElement.class);
/* 483:608 */     map.put(HtmlHeading6.class, HTMLHeadingElement.class);
/* 484:    */     
/* 485:610 */     map.put(HtmlInlineQuotation.class, HTMLQuoteElement.class);
/* 486:611 */     map.put(HtmlBlockQuote.class, HTMLQuoteElement.class);
/* 487:    */     
/* 488:613 */     map.put(HtmlAbbreviated.class, HTMLSpanElement.class);
/* 489:614 */     map.put(HtmlAcronym.class, HTMLSpanElement.class);
/* 490:615 */     map.put(HtmlAddress.class, HTMLSpanElement.class);
/* 491:616 */     map.put(HtmlBackgroundSound.class, HTMLSpanElement.class);
/* 492:617 */     map.put(HtmlBidirectionalOverride.class, HTMLSpanElement.class);
/* 493:618 */     map.put(HtmlBig.class, HTMLSpanElement.class);
/* 494:619 */     map.put(HtmlBold.class, HTMLSpanElement.class);
/* 495:620 */     map.put(HtmlBlink.class, HTMLSpanElement.class);
/* 496:621 */     map.put(HtmlCenter.class, HTMLSpanElement.class);
/* 497:622 */     map.put(HtmlCitation.class, HTMLSpanElement.class);
/* 498:623 */     map.put(HtmlCode.class, HTMLSpanElement.class);
/* 499:624 */     map.put(HtmlDefinition.class, HTMLSpanElement.class);
/* 500:625 */     map.put(HtmlDefinitionDescription.class, HTMLSpanElement.class);
/* 501:626 */     map.put(HtmlDefinitionTerm.class, HTMLSpanElement.class);
/* 502:627 */     map.put(HtmlEmphasis.class, HTMLSpanElement.class);
/* 503:628 */     map.put(HtmlItalic.class, HTMLSpanElement.class);
/* 504:629 */     map.put(HtmlKeyboard.class, HTMLSpanElement.class);
/* 505:630 */     map.put(HtmlListing.class, HTMLSpanElement.class);
/* 506:631 */     map.put(HtmlMultiColumn.class, HTMLSpanElement.class);
/* 507:632 */     map.put(HtmlNoBreak.class, HTMLSpanElement.class);
/* 508:633 */     map.put(HtmlPlainText.class, HTMLSpanElement.class);
/* 509:634 */     map.put(HtmlS.class, HTMLSpanElement.class);
/* 510:635 */     map.put(HtmlSample.class, HTMLSpanElement.class);
/* 511:636 */     map.put(HtmlSmall.class, HTMLSpanElement.class);
/* 512:637 */     map.put(HtmlSpan.class, HTMLSpanElement.class);
/* 513:638 */     map.put(HtmlStrike.class, HTMLSpanElement.class);
/* 514:639 */     map.put(HtmlStrong.class, HTMLSpanElement.class);
/* 515:640 */     map.put(HtmlSubscript.class, HTMLSpanElement.class);
/* 516:641 */     map.put(HtmlSuperscript.class, HTMLSpanElement.class);
/* 517:642 */     map.put(HtmlTeletype.class, HTMLSpanElement.class);
/* 518:643 */     map.put(HtmlUnderlined.class, HTMLSpanElement.class);
/* 519:644 */     map.put(HtmlVariable.class, HTMLSpanElement.class);
/* 520:645 */     map.put(HtmlExample.class, HTMLSpanElement.class);
/* 521:    */     
/* 522:647 */     map.put(HtmlDivision.class, HTMLDivElement.class);
/* 523:648 */     map.put(HtmlMarquee.class, HTMLDivElement.class);
/* 524:649 */     map.put(HtmlNoEmbed.class, HTMLDivElement.class);
/* 525:650 */     map.put(HtmlNoFrames.class, HTMLDivElement.class);
/* 526:651 */     map.put(HtmlNoScript.class, HTMLDivElement.class);
/* 527:    */     
/* 528:653 */     map.put(HtmlTableBody.class, HTMLTableSectionElement.class);
/* 529:654 */     map.put(HtmlTableHeader.class, HTMLTableSectionElement.class);
/* 530:655 */     map.put(HtmlTableFooter.class, HTMLTableSectionElement.class);
/* 531:    */     
/* 532:657 */     map.put(HtmlTableColumn.class, HTMLTableColElement.class);
/* 533:658 */     map.put(HtmlTableColumnGroup.class, HTMLTableColElement.class);
/* 534:    */     
/* 535:660 */     this.htmlJavaScriptMap_ = Collections.unmodifiableMap(map);
/* 536:    */     
/* 537:662 */     return this.htmlJavaScriptMap_;
/* 538:    */   }
/* 539:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.configuration.JavaScriptConfiguration
 * JD-Core Version:    0.7.0.1
 */