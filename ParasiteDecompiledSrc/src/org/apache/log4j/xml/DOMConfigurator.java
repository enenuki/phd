/*    1:     */ package org.apache.log4j.xml;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.InputStream;
/*    6:     */ import java.io.InterruptedIOException;
/*    7:     */ import java.io.Reader;
/*    8:     */ import java.lang.reflect.InvocationTargetException;
/*    9:     */ import java.lang.reflect.Method;
/*   10:     */ import java.net.URL;
/*   11:     */ import java.net.URLConnection;
/*   12:     */ import java.util.Hashtable;
/*   13:     */ import java.util.Properties;
/*   14:     */ import javax.xml.parsers.DocumentBuilder;
/*   15:     */ import javax.xml.parsers.DocumentBuilderFactory;
/*   16:     */ import javax.xml.parsers.FactoryConfigurationError;
/*   17:     */ import org.apache.log4j.Appender;
/*   18:     */ import org.apache.log4j.Category;
/*   19:     */ import org.apache.log4j.Layout;
/*   20:     */ import org.apache.log4j.Level;
/*   21:     */ import org.apache.log4j.LogManager;
/*   22:     */ import org.apache.log4j.Logger;
/*   23:     */ import org.apache.log4j.config.PropertySetter;
/*   24:     */ import org.apache.log4j.helpers.FileWatchdog;
/*   25:     */ import org.apache.log4j.helpers.Loader;
/*   26:     */ import org.apache.log4j.helpers.LogLog;
/*   27:     */ import org.apache.log4j.helpers.OptionConverter;
/*   28:     */ import org.apache.log4j.or.RendererMap;
/*   29:     */ import org.apache.log4j.spi.AppenderAttachable;
/*   30:     */ import org.apache.log4j.spi.Configurator;
/*   31:     */ import org.apache.log4j.spi.ErrorHandler;
/*   32:     */ import org.apache.log4j.spi.Filter;
/*   33:     */ import org.apache.log4j.spi.LoggerFactory;
/*   34:     */ import org.apache.log4j.spi.LoggerRepository;
/*   35:     */ import org.apache.log4j.spi.RendererSupport;
/*   36:     */ import org.apache.log4j.spi.ThrowableRenderer;
/*   37:     */ import org.apache.log4j.spi.ThrowableRendererSupport;
/*   38:     */ import org.w3c.dom.Document;
/*   39:     */ import org.w3c.dom.Element;
/*   40:     */ import org.w3c.dom.NamedNodeMap;
/*   41:     */ import org.w3c.dom.Node;
/*   42:     */ import org.w3c.dom.NodeList;
/*   43:     */ import org.xml.sax.InputSource;
/*   44:     */ import org.xml.sax.SAXException;
/*   45:     */ 
/*   46:     */ public class DOMConfigurator
/*   47:     */   implements Configurator
/*   48:     */ {
/*   49:     */   static final String CONFIGURATION_TAG = "log4j:configuration";
/*   50:     */   static final String OLD_CONFIGURATION_TAG = "configuration";
/*   51:     */   static final String RENDERER_TAG = "renderer";
/*   52:     */   private static final String THROWABLE_RENDERER_TAG = "throwableRenderer";
/*   53:     */   static final String APPENDER_TAG = "appender";
/*   54:     */   static final String APPENDER_REF_TAG = "appender-ref";
/*   55:     */   static final String PARAM_TAG = "param";
/*   56:     */   static final String LAYOUT_TAG = "layout";
/*   57:     */   static final String CATEGORY = "category";
/*   58:     */   static final String LOGGER = "logger";
/*   59:     */   static final String LOGGER_REF = "logger-ref";
/*   60:     */   static final String CATEGORY_FACTORY_TAG = "categoryFactory";
/*   61:     */   static final String LOGGER_FACTORY_TAG = "loggerFactory";
/*   62:     */   static final String NAME_ATTR = "name";
/*   63:     */   static final String CLASS_ATTR = "class";
/*   64:     */   static final String VALUE_ATTR = "value";
/*   65:     */   static final String ROOT_TAG = "root";
/*   66:     */   static final String ROOT_REF = "root-ref";
/*   67:     */   static final String LEVEL_TAG = "level";
/*   68:     */   static final String PRIORITY_TAG = "priority";
/*   69:     */   static final String FILTER_TAG = "filter";
/*   70:     */   static final String ERROR_HANDLER_TAG = "errorHandler";
/*   71:     */   static final String REF_ATTR = "ref";
/*   72:     */   static final String ADDITIVITY_ATTR = "additivity";
/*   73:     */   static final String THRESHOLD_ATTR = "threshold";
/*   74:     */   static final String CONFIG_DEBUG_ATTR = "configDebug";
/*   75:     */   static final String INTERNAL_DEBUG_ATTR = "debug";
/*   76:     */   private static final String RESET_ATTR = "reset";
/*   77:     */   static final String RENDERING_CLASS_ATTR = "renderingClass";
/*   78:     */   static final String RENDERED_CLASS_ATTR = "renderedClass";
/*   79:     */   static final String EMPTY_STR = "";
/*   80: 124 */   static final Class[] ONE_STRING_PARAM = { String.class };
/*   81:     */   static final String dbfKey = "javax.xml.parsers.DocumentBuilderFactory";
/*   82:     */   Hashtable appenderBag;
/*   83:     */   Properties props;
/*   84:     */   LoggerRepository repository;
/*   85: 135 */   protected LoggerFactory catFactory = null;
/*   86:     */   
/*   87:     */   public DOMConfigurator()
/*   88:     */   {
/*   89: 142 */     this.appenderBag = new Hashtable();
/*   90:     */   }
/*   91:     */   
/*   92:     */   protected Appender findAppenderByName(Document doc, String appenderName)
/*   93:     */   {
/*   94: 150 */     Appender appender = (Appender)this.appenderBag.get(appenderName);
/*   95: 152 */     if (appender != null) {
/*   96: 153 */       return appender;
/*   97:     */     }
/*   98: 159 */     Element element = null;
/*   99: 160 */     NodeList list = doc.getElementsByTagName("appender");
/*  100: 161 */     for (int t = 0; t < list.getLength(); t++)
/*  101:     */     {
/*  102: 162 */       Node node = list.item(t);
/*  103: 163 */       NamedNodeMap map = node.getAttributes();
/*  104: 164 */       Node attrNode = map.getNamedItem("name");
/*  105: 165 */       if (appenderName.equals(attrNode.getNodeValue()))
/*  106:     */       {
/*  107: 166 */         element = (Element)node;
/*  108: 167 */         break;
/*  109:     */       }
/*  110:     */     }
/*  111: 172 */     if (element == null)
/*  112:     */     {
/*  113: 173 */       LogLog.error("No appender named [" + appenderName + "] could be found.");
/*  114: 174 */       return null;
/*  115:     */     }
/*  116: 176 */     appender = parseAppender(element);
/*  117: 177 */     if (appender != null) {
/*  118: 178 */       this.appenderBag.put(appenderName, appender);
/*  119:     */     }
/*  120: 180 */     return appender;
/*  121:     */   }
/*  122:     */   
/*  123:     */   protected Appender findAppenderByReference(Element appenderRef)
/*  124:     */   {
/*  125: 189 */     String appenderName = subst(appenderRef.getAttribute("ref"));
/*  126: 190 */     Document doc = appenderRef.getOwnerDocument();
/*  127: 191 */     return findAppenderByName(doc, appenderName);
/*  128:     */   }
/*  129:     */   
/*  130:     */   private static void parseUnrecognizedElement(Object instance, Element element, Properties props)
/*  131:     */     throws Exception
/*  132:     */   {
/*  133: 207 */     boolean recognized = false;
/*  134: 208 */     if ((instance instanceof UnrecognizedElementHandler)) {
/*  135: 209 */       recognized = ((UnrecognizedElementHandler)instance).parseUnrecognizedElement(element, props);
/*  136:     */     }
/*  137: 212 */     if (!recognized) {
/*  138: 213 */       LogLog.warn("Unrecognized element " + element.getNodeName());
/*  139:     */     }
/*  140:     */   }
/*  141:     */   
/*  142:     */   private static void quietParseUnrecognizedElement(Object instance, Element element, Properties props)
/*  143:     */   {
/*  144:     */     try
/*  145:     */     {
/*  146: 230 */       parseUnrecognizedElement(instance, element, props);
/*  147:     */     }
/*  148:     */     catch (Exception ex)
/*  149:     */     {
/*  150: 232 */       if (((ex instanceof InterruptedException)) || ((ex instanceof InterruptedIOException))) {
/*  151: 233 */         Thread.currentThread().interrupt();
/*  152:     */       }
/*  153: 235 */       LogLog.error("Error in extension content: ", ex);
/*  154:     */     }
/*  155:     */   }
/*  156:     */   
/*  157:     */   protected Appender parseAppender(Element appenderElement)
/*  158:     */   {
/*  159: 244 */     String className = subst(appenderElement.getAttribute("class"));
/*  160: 245 */     LogLog.debug("Class name: [" + className + ']');
/*  161:     */     try
/*  162:     */     {
/*  163: 247 */       Object instance = Loader.loadClass(className).newInstance();
/*  164: 248 */       Appender appender = (Appender)instance;
/*  165: 249 */       PropertySetter propSetter = new PropertySetter(appender);
/*  166:     */       
/*  167: 251 */       appender.setName(subst(appenderElement.getAttribute("name")));
/*  168:     */       
/*  169: 253 */       NodeList children = appenderElement.getChildNodes();
/*  170: 254 */       int length = children.getLength();
/*  171: 256 */       for (int loop = 0; loop < length; loop++)
/*  172:     */       {
/*  173: 257 */         Node currentNode = children.item(loop);
/*  174: 260 */         if (currentNode.getNodeType() == 1)
/*  175:     */         {
/*  176: 261 */           Element currentElement = (Element)currentNode;
/*  177: 264 */           if (currentElement.getTagName().equals("param"))
/*  178:     */           {
/*  179: 265 */             setParameter(currentElement, propSetter);
/*  180:     */           }
/*  181: 268 */           else if (currentElement.getTagName().equals("layout"))
/*  182:     */           {
/*  183: 269 */             appender.setLayout(parseLayout(currentElement));
/*  184:     */           }
/*  185: 272 */           else if (currentElement.getTagName().equals("filter"))
/*  186:     */           {
/*  187: 273 */             parseFilters(currentElement, appender);
/*  188:     */           }
/*  189: 275 */           else if (currentElement.getTagName().equals("errorHandler"))
/*  190:     */           {
/*  191: 276 */             parseErrorHandler(currentElement, appender);
/*  192:     */           }
/*  193: 278 */           else if (currentElement.getTagName().equals("appender-ref"))
/*  194:     */           {
/*  195: 279 */             String refName = subst(currentElement.getAttribute("ref"));
/*  196: 280 */             if ((appender instanceof AppenderAttachable))
/*  197:     */             {
/*  198: 281 */               AppenderAttachable aa = (AppenderAttachable)appender;
/*  199: 282 */               LogLog.debug("Attaching appender named [" + refName + "] to appender named [" + appender.getName() + "].");
/*  200:     */               
/*  201: 284 */               aa.addAppender(findAppenderByReference(currentElement));
/*  202:     */             }
/*  203:     */             else
/*  204:     */             {
/*  205: 286 */               LogLog.error("Requesting attachment of appender named [" + refName + "] to appender named [" + appender.getName() + "] which does not implement org.apache.log4j.spi.AppenderAttachable.");
/*  206:     */             }
/*  207:     */           }
/*  208:     */           else
/*  209:     */           {
/*  210: 291 */             parseUnrecognizedElement(instance, currentElement, this.props);
/*  211:     */           }
/*  212:     */         }
/*  213:     */       }
/*  214: 295 */       propSetter.activate();
/*  215: 296 */       return appender;
/*  216:     */     }
/*  217:     */     catch (Exception oops)
/*  218:     */     {
/*  219: 301 */       if (((oops instanceof InterruptedException)) || ((oops instanceof InterruptedIOException))) {
/*  220: 302 */         Thread.currentThread().interrupt();
/*  221:     */       }
/*  222: 304 */       LogLog.error("Could not create an Appender. Reported error follows.", oops);
/*  223:     */     }
/*  224: 306 */     return null;
/*  225:     */   }
/*  226:     */   
/*  227:     */   protected void parseErrorHandler(Element element, Appender appender)
/*  228:     */   {
/*  229: 315 */     ErrorHandler eh = (ErrorHandler)OptionConverter.instantiateByClassName(subst(element.getAttribute("class")), ErrorHandler.class, null);
/*  230: 320 */     if (eh != null)
/*  231:     */     {
/*  232: 321 */       eh.setAppender(appender);
/*  233:     */       
/*  234: 323 */       PropertySetter propSetter = new PropertySetter(eh);
/*  235: 324 */       NodeList children = element.getChildNodes();
/*  236: 325 */       int length = children.getLength();
/*  237: 327 */       for (int loop = 0; loop < length; loop++)
/*  238:     */       {
/*  239: 328 */         Node currentNode = children.item(loop);
/*  240: 329 */         if (currentNode.getNodeType() == 1)
/*  241:     */         {
/*  242: 330 */           Element currentElement = (Element)currentNode;
/*  243: 331 */           String tagName = currentElement.getTagName();
/*  244: 332 */           if (tagName.equals("param"))
/*  245:     */           {
/*  246: 333 */             setParameter(currentElement, propSetter);
/*  247:     */           }
/*  248: 334 */           else if (tagName.equals("appender-ref"))
/*  249:     */           {
/*  250: 335 */             eh.setBackupAppender(findAppenderByReference(currentElement));
/*  251:     */           }
/*  252: 336 */           else if (tagName.equals("logger-ref"))
/*  253:     */           {
/*  254: 337 */             String loggerName = currentElement.getAttribute("ref");
/*  255: 338 */             Logger logger = this.catFactory == null ? this.repository.getLogger(loggerName) : this.repository.getLogger(loggerName, this.catFactory);
/*  256:     */             
/*  257: 340 */             eh.setLogger(logger);
/*  258:     */           }
/*  259: 341 */           else if (tagName.equals("root-ref"))
/*  260:     */           {
/*  261: 342 */             Logger root = this.repository.getRootLogger();
/*  262: 343 */             eh.setLogger(root);
/*  263:     */           }
/*  264:     */           else
/*  265:     */           {
/*  266: 345 */             quietParseUnrecognizedElement(eh, currentElement, this.props);
/*  267:     */           }
/*  268:     */         }
/*  269:     */       }
/*  270: 349 */       propSetter.activate();
/*  271: 350 */       appender.setErrorHandler(eh);
/*  272:     */     }
/*  273:     */   }
/*  274:     */   
/*  275:     */   protected void parseFilters(Element element, Appender appender)
/*  276:     */   {
/*  277: 359 */     String clazz = subst(element.getAttribute("class"));
/*  278: 360 */     Filter filter = (Filter)OptionConverter.instantiateByClassName(clazz, Filter.class, null);
/*  279: 363 */     if (filter != null)
/*  280:     */     {
/*  281: 364 */       PropertySetter propSetter = new PropertySetter(filter);
/*  282: 365 */       NodeList children = element.getChildNodes();
/*  283: 366 */       int length = children.getLength();
/*  284: 368 */       for (int loop = 0; loop < length; loop++)
/*  285:     */       {
/*  286: 369 */         Node currentNode = children.item(loop);
/*  287: 370 */         if (currentNode.getNodeType() == 1)
/*  288:     */         {
/*  289: 371 */           Element currentElement = (Element)currentNode;
/*  290: 372 */           String tagName = currentElement.getTagName();
/*  291: 373 */           if (tagName.equals("param")) {
/*  292: 374 */             setParameter(currentElement, propSetter);
/*  293:     */           } else {
/*  294: 376 */             quietParseUnrecognizedElement(filter, currentElement, this.props);
/*  295:     */           }
/*  296:     */         }
/*  297:     */       }
/*  298: 380 */       propSetter.activate();
/*  299: 381 */       LogLog.debug("Adding filter of type [" + filter.getClass() + "] to appender named [" + appender.getName() + "].");
/*  300:     */       
/*  301: 383 */       appender.addFilter(filter);
/*  302:     */     }
/*  303:     */   }
/*  304:     */   
/*  305:     */   protected void parseCategory(Element loggerElement)
/*  306:     */   {
/*  307: 393 */     String catName = subst(loggerElement.getAttribute("name"));
/*  308:     */     
/*  309:     */ 
/*  310:     */ 
/*  311: 397 */     String className = subst(loggerElement.getAttribute("class"));
/*  312:     */     Logger cat;
/*  313:     */     Logger cat;
/*  314: 400 */     if ("".equals(className))
/*  315:     */     {
/*  316: 401 */       LogLog.debug("Retreiving an instance of org.apache.log4j.Logger.");
/*  317: 402 */       cat = this.catFactory == null ? this.repository.getLogger(catName) : this.repository.getLogger(catName, this.catFactory);
/*  318:     */     }
/*  319:     */     else
/*  320:     */     {
/*  321: 405 */       LogLog.debug("Desired logger sub-class: [" + className + ']');
/*  322:     */       try
/*  323:     */       {
/*  324: 407 */         Class clazz = Loader.loadClass(className);
/*  325: 408 */         Method getInstanceMethod = clazz.getMethod("getLogger", ONE_STRING_PARAM);
/*  326:     */         
/*  327: 410 */         cat = (Logger)getInstanceMethod.invoke(null, new Object[] { catName });
/*  328:     */       }
/*  329:     */       catch (InvocationTargetException oops)
/*  330:     */       {
/*  331: 412 */         if (((oops.getTargetException() instanceof InterruptedException)) || ((oops.getTargetException() instanceof InterruptedIOException))) {
/*  332: 414 */           Thread.currentThread().interrupt();
/*  333:     */         }
/*  334: 416 */         LogLog.error("Could not retrieve category [" + catName + "]. Reported error follows.", oops);
/*  335:     */         
/*  336: 418 */         return;
/*  337:     */       }
/*  338:     */       catch (Exception oops)
/*  339:     */       {
/*  340: 420 */         LogLog.error("Could not retrieve category [" + catName + "]. Reported error follows.", oops);
/*  341:     */         
/*  342: 422 */         return;
/*  343:     */       }
/*  344:     */     }
/*  345: 429 */     synchronized (cat)
/*  346:     */     {
/*  347: 430 */       boolean additivity = OptionConverter.toBoolean(subst(loggerElement.getAttribute("additivity")), true);
/*  348:     */       
/*  349:     */ 
/*  350:     */ 
/*  351: 434 */       LogLog.debug("Setting [" + cat.getName() + "] additivity to [" + additivity + "].");
/*  352: 435 */       cat.setAdditivity(additivity);
/*  353: 436 */       parseChildrenOfLoggerElement(loggerElement, cat, false);
/*  354:     */     }
/*  355:     */   }
/*  356:     */   
/*  357:     */   protected void parseCategoryFactory(Element factoryElement)
/*  358:     */   {
/*  359: 446 */     String className = subst(factoryElement.getAttribute("class"));
/*  360: 448 */     if ("".equals(className))
/*  361:     */     {
/*  362: 449 */       LogLog.error("Category Factory tag class attribute not found.");
/*  363: 450 */       LogLog.debug("No Category Factory configured.");
/*  364:     */     }
/*  365:     */     else
/*  366:     */     {
/*  367: 453 */       LogLog.debug("Desired category factory: [" + className + ']');
/*  368: 454 */       Object factory = OptionConverter.instantiateByClassName(className, LoggerFactory.class, null);
/*  369: 457 */       if ((factory instanceof LoggerFactory)) {
/*  370: 458 */         this.catFactory = ((LoggerFactory)factory);
/*  371:     */       } else {
/*  372: 460 */         LogLog.error("Category Factory class " + className + " does not implement org.apache.log4j.LoggerFactory");
/*  373:     */       }
/*  374: 462 */       PropertySetter propSetter = new PropertySetter(factory);
/*  375:     */       
/*  376: 464 */       Element currentElement = null;
/*  377: 465 */       Node currentNode = null;
/*  378: 466 */       NodeList children = factoryElement.getChildNodes();
/*  379: 467 */       int length = children.getLength();
/*  380: 469 */       for (int loop = 0; loop < length; loop++)
/*  381:     */       {
/*  382: 470 */         currentNode = children.item(loop);
/*  383: 471 */         if (currentNode.getNodeType() == 1)
/*  384:     */         {
/*  385: 472 */           currentElement = (Element)currentNode;
/*  386: 473 */           if (currentElement.getTagName().equals("param")) {
/*  387: 474 */             setParameter(currentElement, propSetter);
/*  388:     */           } else {
/*  389: 476 */             quietParseUnrecognizedElement(factory, currentElement, this.props);
/*  390:     */           }
/*  391:     */         }
/*  392:     */       }
/*  393:     */     }
/*  394:     */   }
/*  395:     */   
/*  396:     */   protected void parseRoot(Element rootElement)
/*  397:     */   {
/*  398: 489 */     Logger root = this.repository.getRootLogger();
/*  399: 491 */     synchronized (root)
/*  400:     */     {
/*  401: 492 */       parseChildrenOfLoggerElement(rootElement, root, true);
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   protected void parseChildrenOfLoggerElement(Element catElement, Logger cat, boolean isRoot)
/*  406:     */   {
/*  407: 504 */     PropertySetter propSetter = new PropertySetter(cat);
/*  408:     */     
/*  409:     */ 
/*  410:     */ 
/*  411: 508 */     cat.removeAllAppenders();
/*  412:     */     
/*  413:     */ 
/*  414: 511 */     NodeList children = catElement.getChildNodes();
/*  415: 512 */     int length = children.getLength();
/*  416: 514 */     for (int loop = 0; loop < length; loop++)
/*  417:     */     {
/*  418: 515 */       Node currentNode = children.item(loop);
/*  419: 517 */       if (currentNode.getNodeType() == 1)
/*  420:     */       {
/*  421: 518 */         Element currentElement = (Element)currentNode;
/*  422: 519 */         String tagName = currentElement.getTagName();
/*  423: 521 */         if (tagName.equals("appender-ref"))
/*  424:     */         {
/*  425: 522 */           Element appenderRef = (Element)currentNode;
/*  426: 523 */           Appender appender = findAppenderByReference(appenderRef);
/*  427: 524 */           String refName = subst(appenderRef.getAttribute("ref"));
/*  428: 525 */           if (appender != null) {
/*  429: 526 */             LogLog.debug("Adding appender named [" + refName + "] to category [" + cat.getName() + "].");
/*  430:     */           } else {
/*  431: 529 */             LogLog.debug("Appender named [" + refName + "] not found.");
/*  432:     */           }
/*  433: 531 */           cat.addAppender(appender);
/*  434:     */         }
/*  435: 533 */         else if (tagName.equals("level"))
/*  436:     */         {
/*  437: 534 */           parseLevel(currentElement, cat, isRoot);
/*  438:     */         }
/*  439: 535 */         else if (tagName.equals("priority"))
/*  440:     */         {
/*  441: 536 */           parseLevel(currentElement, cat, isRoot);
/*  442:     */         }
/*  443: 537 */         else if (tagName.equals("param"))
/*  444:     */         {
/*  445: 538 */           setParameter(currentElement, propSetter);
/*  446:     */         }
/*  447:     */         else
/*  448:     */         {
/*  449: 540 */           quietParseUnrecognizedElement(cat, currentElement, this.props);
/*  450:     */         }
/*  451:     */       }
/*  452:     */     }
/*  453: 544 */     propSetter.activate();
/*  454:     */   }
/*  455:     */   
/*  456:     */   protected Layout parseLayout(Element layout_element)
/*  457:     */   {
/*  458: 552 */     String className = subst(layout_element.getAttribute("class"));
/*  459: 553 */     LogLog.debug("Parsing layout of class: \"" + className + "\"");
/*  460:     */     try
/*  461:     */     {
/*  462: 555 */       Object instance = Loader.loadClass(className).newInstance();
/*  463: 556 */       Layout layout = (Layout)instance;
/*  464: 557 */       PropertySetter propSetter = new PropertySetter(layout);
/*  465:     */       
/*  466: 559 */       NodeList params = layout_element.getChildNodes();
/*  467: 560 */       int length = params.getLength();
/*  468: 562 */       for (int loop = 0; loop < length; loop++)
/*  469:     */       {
/*  470: 563 */         Node currentNode = params.item(loop);
/*  471: 564 */         if (currentNode.getNodeType() == 1)
/*  472:     */         {
/*  473: 565 */           Element currentElement = (Element)currentNode;
/*  474: 566 */           String tagName = currentElement.getTagName();
/*  475: 567 */           if (tagName.equals("param")) {
/*  476: 568 */             setParameter(currentElement, propSetter);
/*  477:     */           } else {
/*  478: 570 */             parseUnrecognizedElement(instance, currentElement, this.props);
/*  479:     */           }
/*  480:     */         }
/*  481:     */       }
/*  482: 575 */       propSetter.activate();
/*  483: 576 */       return layout;
/*  484:     */     }
/*  485:     */     catch (Exception oops)
/*  486:     */     {
/*  487: 579 */       if (((oops instanceof InterruptedException)) || ((oops instanceof InterruptedIOException))) {
/*  488: 580 */         Thread.currentThread().interrupt();
/*  489:     */       }
/*  490: 582 */       LogLog.error("Could not create the Layout. Reported error follows.", oops);
/*  491:     */     }
/*  492: 584 */     return null;
/*  493:     */   }
/*  494:     */   
/*  495:     */   protected void parseRenderer(Element element)
/*  496:     */   {
/*  497: 590 */     String renderingClass = subst(element.getAttribute("renderingClass"));
/*  498: 591 */     String renderedClass = subst(element.getAttribute("renderedClass"));
/*  499: 592 */     if ((this.repository instanceof RendererSupport)) {
/*  500: 593 */       RendererMap.addRenderer((RendererSupport)this.repository, renderedClass, renderingClass);
/*  501:     */     }
/*  502:     */   }
/*  503:     */   
/*  504:     */   protected ThrowableRenderer parseThrowableRenderer(Element element)
/*  505:     */   {
/*  506: 605 */     String className = subst(element.getAttribute("class"));
/*  507: 606 */     LogLog.debug("Parsing throwableRenderer of class: \"" + className + "\"");
/*  508:     */     try
/*  509:     */     {
/*  510: 608 */       Object instance = Loader.loadClass(className).newInstance();
/*  511: 609 */       ThrowableRenderer tr = (ThrowableRenderer)instance;
/*  512: 610 */       PropertySetter propSetter = new PropertySetter(tr);
/*  513:     */       
/*  514: 612 */       NodeList params = element.getChildNodes();
/*  515: 613 */       int length = params.getLength();
/*  516: 615 */       for (int loop = 0; loop < length; loop++)
/*  517:     */       {
/*  518: 616 */         Node currentNode = params.item(loop);
/*  519: 617 */         if (currentNode.getNodeType() == 1)
/*  520:     */         {
/*  521: 618 */           Element currentElement = (Element)currentNode;
/*  522: 619 */           String tagName = currentElement.getTagName();
/*  523: 620 */           if (tagName.equals("param")) {
/*  524: 621 */             setParameter(currentElement, propSetter);
/*  525:     */           } else {
/*  526: 623 */             parseUnrecognizedElement(instance, currentElement, this.props);
/*  527:     */           }
/*  528:     */         }
/*  529:     */       }
/*  530: 628 */       propSetter.activate();
/*  531: 629 */       return tr;
/*  532:     */     }
/*  533:     */     catch (Exception oops)
/*  534:     */     {
/*  535: 632 */       if (((oops instanceof InterruptedException)) || ((oops instanceof InterruptedIOException))) {
/*  536: 633 */         Thread.currentThread().interrupt();
/*  537:     */       }
/*  538: 635 */       LogLog.error("Could not create the ThrowableRenderer. Reported error follows.", oops);
/*  539:     */     }
/*  540: 637 */     return null;
/*  541:     */   }
/*  542:     */   
/*  543:     */   protected void parseLevel(Element element, Logger logger, boolean isRoot)
/*  544:     */   {
/*  545: 646 */     String catName = logger.getName();
/*  546: 647 */     if (isRoot) {
/*  547: 648 */       catName = "root";
/*  548:     */     }
/*  549: 651 */     String priStr = subst(element.getAttribute("value"));
/*  550: 652 */     LogLog.debug("Level value for " + catName + " is  [" + priStr + "].");
/*  551: 654 */     if (("inherited".equalsIgnoreCase(priStr)) || ("null".equalsIgnoreCase(priStr)))
/*  552:     */     {
/*  553: 655 */       if (isRoot) {
/*  554: 656 */         LogLog.error("Root level cannot be inherited. Ignoring directive.");
/*  555:     */       } else {
/*  556: 658 */         logger.setLevel(null);
/*  557:     */       }
/*  558:     */     }
/*  559:     */     else
/*  560:     */     {
/*  561: 661 */       String className = subst(element.getAttribute("class"));
/*  562: 662 */       if ("".equals(className))
/*  563:     */       {
/*  564: 663 */         logger.setLevel(OptionConverter.toLevel(priStr, Level.DEBUG));
/*  565:     */       }
/*  566:     */       else
/*  567:     */       {
/*  568: 665 */         LogLog.debug("Desired Level sub-class: [" + className + ']');
/*  569:     */         try
/*  570:     */         {
/*  571: 667 */           Class clazz = Loader.loadClass(className);
/*  572: 668 */           Method toLevelMethod = clazz.getMethod("toLevel", ONE_STRING_PARAM);
/*  573:     */           
/*  574: 670 */           Level pri = (Level)toLevelMethod.invoke(null, new Object[] { priStr });
/*  575:     */           
/*  576: 672 */           logger.setLevel(pri);
/*  577:     */         }
/*  578:     */         catch (Exception oops)
/*  579:     */         {
/*  580: 674 */           if (((oops instanceof InterruptedException)) || ((oops instanceof InterruptedIOException))) {
/*  581: 675 */             Thread.currentThread().interrupt();
/*  582:     */           }
/*  583: 677 */           LogLog.error("Could not create level [" + priStr + "]. Reported error follows.", oops);
/*  584:     */           
/*  585: 679 */           return;
/*  586:     */         }
/*  587:     */       }
/*  588:     */     }
/*  589: 683 */     LogLog.debug(catName + " level set to " + logger.getLevel());
/*  590:     */   }
/*  591:     */   
/*  592:     */   protected void setParameter(Element elem, PropertySetter propSetter)
/*  593:     */   {
/*  594: 688 */     String name = subst(elem.getAttribute("name"));
/*  595: 689 */     String value = elem.getAttribute("value");
/*  596: 690 */     value = subst(OptionConverter.convertSpecialChars(value));
/*  597: 691 */     propSetter.setProperty(name, value);
/*  598:     */   }
/*  599:     */   
/*  600:     */   public static void configure(Element element)
/*  601:     */   {
/*  602: 703 */     DOMConfigurator configurator = new DOMConfigurator();
/*  603: 704 */     configurator.doConfigure(element, LogManager.getLoggerRepository());
/*  604:     */   }
/*  605:     */   
/*  606:     */   public static void configureAndWatch(String configFilename)
/*  607:     */   {
/*  608: 718 */     configureAndWatch(configFilename, 60000L);
/*  609:     */   }
/*  610:     */   
/*  611:     */   public static void configureAndWatch(String configFilename, long delay)
/*  612:     */   {
/*  613: 735 */     XMLWatchdog xdog = new XMLWatchdog(configFilename);
/*  614: 736 */     xdog.setDelay(delay);
/*  615: 737 */     xdog.start();
/*  616:     */   }
/*  617:     */   
/*  618:     */   public void doConfigure(String filename, LoggerRepository repository)
/*  619:     */   {
/*  620: 747 */     ParseAction action = new ParseAction()
/*  621:     */     {
/*  622:     */       private final String val$filename;
/*  623:     */       
/*  624:     */       public Document parse(DocumentBuilder parser)
/*  625:     */         throws SAXException, IOException
/*  626:     */       {
/*  627: 749 */         return parser.parse(new File(this.val$filename));
/*  628:     */       }
/*  629:     */       
/*  630:     */       public String toString()
/*  631:     */       {
/*  632: 752 */         return "file [" + this.val$filename + "]";
/*  633:     */       }
/*  634: 754 */     };
/*  635: 755 */     doConfigure(action, repository);
/*  636:     */   }
/*  637:     */   
/*  638:     */   public void doConfigure(URL url, LoggerRepository repository)
/*  639:     */   {
/*  640: 761 */     ParseAction action = new ParseAction()
/*  641:     */     {
/*  642:     */       private final URL val$url;
/*  643:     */       
/*  644:     */       public Document parse(DocumentBuilder parser)
/*  645:     */         throws SAXException, IOException
/*  646:     */       {
/*  647: 763 */         URLConnection uConn = this.val$url.openConnection();
/*  648: 764 */         uConn.setUseCaches(false);
/*  649: 765 */         InputSource src = new InputSource(uConn.getInputStream());
/*  650: 766 */         src.setSystemId(this.val$url.toString());
/*  651: 767 */         return parser.parse(src);
/*  652:     */       }
/*  653:     */       
/*  654:     */       public String toString()
/*  655:     */       {
/*  656: 770 */         return "url [" + this.val$url.toString() + "]";
/*  657:     */       }
/*  658: 772 */     };
/*  659: 773 */     doConfigure(action, repository);
/*  660:     */   }
/*  661:     */   
/*  662:     */   public void doConfigure(InputStream inputStream, LoggerRepository repository)
/*  663:     */     throws FactoryConfigurationError
/*  664:     */   {
/*  665: 784 */     ParseAction action = new ParseAction()
/*  666:     */     {
/*  667:     */       private final InputStream val$inputStream;
/*  668:     */       
/*  669:     */       public Document parse(DocumentBuilder parser)
/*  670:     */         throws SAXException, IOException
/*  671:     */       {
/*  672: 786 */         InputSource inputSource = new InputSource(this.val$inputStream);
/*  673: 787 */         inputSource.setSystemId("dummy://log4j.dtd");
/*  674: 788 */         return parser.parse(inputSource);
/*  675:     */       }
/*  676:     */       
/*  677:     */       public String toString()
/*  678:     */       {
/*  679: 791 */         return "input stream [" + this.val$inputStream.toString() + "]";
/*  680:     */       }
/*  681: 793 */     };
/*  682: 794 */     doConfigure(action, repository);
/*  683:     */   }
/*  684:     */   
/*  685:     */   public void doConfigure(Reader reader, LoggerRepository repository)
/*  686:     */     throws FactoryConfigurationError
/*  687:     */   {
/*  688: 805 */     ParseAction action = new ParseAction()
/*  689:     */     {
/*  690:     */       private final Reader val$reader;
/*  691:     */       
/*  692:     */       public Document parse(DocumentBuilder parser)
/*  693:     */         throws SAXException, IOException
/*  694:     */       {
/*  695: 807 */         InputSource inputSource = new InputSource(this.val$reader);
/*  696: 808 */         inputSource.setSystemId("dummy://log4j.dtd");
/*  697: 809 */         return parser.parse(inputSource);
/*  698:     */       }
/*  699:     */       
/*  700:     */       public String toString()
/*  701:     */       {
/*  702: 812 */         return "reader [" + this.val$reader.toString() + "]";
/*  703:     */       }
/*  704: 814 */     };
/*  705: 815 */     doConfigure(action, repository);
/*  706:     */   }
/*  707:     */   
/*  708:     */   protected void doConfigure(InputSource inputSource, LoggerRepository repository)
/*  709:     */     throws FactoryConfigurationError
/*  710:     */   {
/*  711: 826 */     if (inputSource.getSystemId() == null) {
/*  712: 827 */       inputSource.setSystemId("dummy://log4j.dtd");
/*  713:     */     }
/*  714: 829 */     ParseAction action = new ParseAction()
/*  715:     */     {
/*  716:     */       private final InputSource val$inputSource;
/*  717:     */       
/*  718:     */       public Document parse(DocumentBuilder parser)
/*  719:     */         throws SAXException, IOException
/*  720:     */       {
/*  721: 831 */         return parser.parse(this.val$inputSource);
/*  722:     */       }
/*  723:     */       
/*  724:     */       public String toString()
/*  725:     */       {
/*  726: 834 */         return "input source [" + this.val$inputSource.toString() + "]";
/*  727:     */       }
/*  728: 836 */     };
/*  729: 837 */     doConfigure(action, repository);
/*  730:     */   }
/*  731:     */   
/*  732:     */   private final void doConfigure(ParseAction action, LoggerRepository repository)
/*  733:     */     throws FactoryConfigurationError
/*  734:     */   {
/*  735: 843 */     DocumentBuilderFactory dbf = null;
/*  736: 844 */     this.repository = repository;
/*  737:     */     try
/*  738:     */     {
/*  739: 846 */       LogLog.debug("System property is :" + OptionConverter.getSystemProperty("javax.xml.parsers.DocumentBuilderFactory", null));
/*  740:     */       
/*  741:     */ 
/*  742: 849 */       dbf = DocumentBuilderFactory.newInstance();
/*  743: 850 */       LogLog.debug("Standard DocumentBuilderFactory search succeded.");
/*  744: 851 */       LogLog.debug("DocumentBuilderFactory is: " + dbf.getClass().getName());
/*  745:     */     }
/*  746:     */     catch (FactoryConfigurationError fce)
/*  747:     */     {
/*  748: 853 */       Exception e = fce.getException();
/*  749: 854 */       LogLog.debug("Could not instantiate a DocumentBuilderFactory.", e);
/*  750: 855 */       throw fce;
/*  751:     */     }
/*  752:     */     try
/*  753:     */     {
/*  754: 859 */       dbf.setValidating(true);
/*  755:     */       
/*  756: 861 */       DocumentBuilder docBuilder = dbf.newDocumentBuilder();
/*  757:     */       
/*  758: 863 */       docBuilder.setErrorHandler(new SAXErrorHandler());
/*  759: 864 */       docBuilder.setEntityResolver(new Log4jEntityResolver());
/*  760:     */       
/*  761: 866 */       Document doc = action.parse(docBuilder);
/*  762: 867 */       parse(doc.getDocumentElement());
/*  763:     */     }
/*  764:     */     catch (Exception e)
/*  765:     */     {
/*  766: 869 */       if (((e instanceof InterruptedException)) || ((e instanceof InterruptedIOException))) {
/*  767: 870 */         Thread.currentThread().interrupt();
/*  768:     */       }
/*  769: 873 */       LogLog.error("Could not parse " + action.toString() + ".", e);
/*  770:     */     }
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void doConfigure(Element element, LoggerRepository repository)
/*  774:     */   {
/*  775: 881 */     this.repository = repository;
/*  776: 882 */     parse(element);
/*  777:     */   }
/*  778:     */   
/*  779:     */   public static void configure(String filename)
/*  780:     */     throws FactoryConfigurationError
/*  781:     */   {
/*  782: 891 */     new DOMConfigurator().doConfigure(filename, LogManager.getLoggerRepository());
/*  783:     */   }
/*  784:     */   
/*  785:     */   public static void configure(URL url)
/*  786:     */     throws FactoryConfigurationError
/*  787:     */   {
/*  788: 901 */     new DOMConfigurator().doConfigure(url, LogManager.getLoggerRepository());
/*  789:     */   }
/*  790:     */   
/*  791:     */   protected void parse(Element element)
/*  792:     */   {
/*  793: 913 */     String rootElementName = element.getTagName();
/*  794: 915 */     if (!rootElementName.equals("log4j:configuration")) {
/*  795: 916 */       if (rootElementName.equals("configuration"))
/*  796:     */       {
/*  797: 917 */         LogLog.warn("The <configuration> element has been deprecated.");
/*  798:     */         
/*  799: 919 */         LogLog.warn("Use the <log4j:configuration> element instead.");
/*  800:     */       }
/*  801:     */       else
/*  802:     */       {
/*  803: 921 */         LogLog.error("DOM element is - not a <log4j:configuration> element.");
/*  804: 922 */         return;
/*  805:     */       }
/*  806:     */     }
/*  807: 927 */     String debugAttrib = subst(element.getAttribute("debug"));
/*  808:     */     
/*  809: 929 */     LogLog.debug("debug attribute= \"" + debugAttrib + "\".");
/*  810: 932 */     if ((!debugAttrib.equals("")) && (!debugAttrib.equals("null"))) {
/*  811: 933 */       LogLog.setInternalDebugging(OptionConverter.toBoolean(debugAttrib, true));
/*  812:     */     } else {
/*  813: 935 */       LogLog.debug("Ignoring debug attribute.");
/*  814:     */     }
/*  815: 942 */     String resetAttrib = subst(element.getAttribute("reset"));
/*  816: 943 */     LogLog.debug("reset attribute= \"" + resetAttrib + "\".");
/*  817: 944 */     if ((!"".equals(resetAttrib)) && 
/*  818: 945 */       (OptionConverter.toBoolean(resetAttrib, false))) {
/*  819: 946 */       this.repository.resetConfiguration();
/*  820:     */     }
/*  821: 952 */     String confDebug = subst(element.getAttribute("configDebug"));
/*  822: 953 */     if ((!confDebug.equals("")) && (!confDebug.equals("null")))
/*  823:     */     {
/*  824: 954 */       LogLog.warn("The \"configDebug\" attribute is deprecated.");
/*  825: 955 */       LogLog.warn("Use the \"debug\" attribute instead.");
/*  826: 956 */       LogLog.setInternalDebugging(OptionConverter.toBoolean(confDebug, true));
/*  827:     */     }
/*  828: 959 */     String thresholdStr = subst(element.getAttribute("threshold"));
/*  829: 960 */     LogLog.debug("Threshold =\"" + thresholdStr + "\".");
/*  830: 961 */     if ((!"".equals(thresholdStr)) && (!"null".equals(thresholdStr))) {
/*  831: 962 */       this.repository.setThreshold(thresholdStr);
/*  832:     */     }
/*  833: 974 */     String tagName = null;
/*  834: 975 */     Element currentElement = null;
/*  835: 976 */     Node currentNode = null;
/*  836: 977 */     NodeList children = element.getChildNodes();
/*  837: 978 */     int length = children.getLength();
/*  838: 980 */     for (int loop = 0; loop < length; loop++)
/*  839:     */     {
/*  840: 981 */       currentNode = children.item(loop);
/*  841: 982 */       if (currentNode.getNodeType() == 1)
/*  842:     */       {
/*  843: 983 */         currentElement = (Element)currentNode;
/*  844: 984 */         tagName = currentElement.getTagName();
/*  845: 986 */         if ((tagName.equals("categoryFactory")) || (tagName.equals("loggerFactory"))) {
/*  846: 987 */           parseCategoryFactory(currentElement);
/*  847:     */         }
/*  848:     */       }
/*  849:     */     }
/*  850: 992 */     for (int loop = 0; loop < length; loop++)
/*  851:     */     {
/*  852: 993 */       currentNode = children.item(loop);
/*  853: 994 */       if (currentNode.getNodeType() == 1)
/*  854:     */       {
/*  855: 995 */         currentElement = (Element)currentNode;
/*  856: 996 */         tagName = currentElement.getTagName();
/*  857: 998 */         if ((tagName.equals("category")) || (tagName.equals("logger"))) {
/*  858: 999 */           parseCategory(currentElement);
/*  859:1000 */         } else if (tagName.equals("root")) {
/*  860:1001 */           parseRoot(currentElement);
/*  861:1002 */         } else if (tagName.equals("renderer")) {
/*  862:1003 */           parseRenderer(currentElement);
/*  863:1004 */         } else if (tagName.equals("throwableRenderer"))
/*  864:     */         {
/*  865:1005 */           if ((this.repository instanceof ThrowableRendererSupport))
/*  866:     */           {
/*  867:1006 */             ThrowableRenderer tr = parseThrowableRenderer(currentElement);
/*  868:1007 */             if (tr != null) {
/*  869:1008 */               ((ThrowableRendererSupport)this.repository).setThrowableRenderer(tr);
/*  870:     */             }
/*  871:     */           }
/*  872:     */         }
/*  873:1011 */         else if ((!tagName.equals("appender")) && (!tagName.equals("categoryFactory")) && (!tagName.equals("loggerFactory"))) {
/*  874:1014 */           quietParseUnrecognizedElement(this.repository, currentElement, this.props);
/*  875:     */         }
/*  876:     */       }
/*  877:     */     }
/*  878:     */   }
/*  879:     */   
/*  880:     */   protected String subst(String value)
/*  881:     */   {
/*  882:1023 */     return subst(value, this.props);
/*  883:     */   }
/*  884:     */   
/*  885:     */   public static String subst(String value, Properties props)
/*  886:     */   {
/*  887:     */     try
/*  888:     */     {
/*  889:1038 */       return OptionConverter.substVars(value, props);
/*  890:     */     }
/*  891:     */     catch (IllegalArgumentException e)
/*  892:     */     {
/*  893:1040 */       LogLog.warn("Could not perform variable substitution.", e);
/*  894:     */     }
/*  895:1041 */     return value;
/*  896:     */   }
/*  897:     */   
/*  898:     */   public static void setParameter(Element elem, PropertySetter propSetter, Properties props)
/*  899:     */   {
/*  900:1057 */     String name = subst(elem.getAttribute("name"), props);
/*  901:1058 */     String value = elem.getAttribute("value");
/*  902:1059 */     value = subst(OptionConverter.convertSpecialChars(value), props);
/*  903:1060 */     propSetter.setProperty(name, value);
/*  904:     */   }
/*  905:     */   
/*  906:     */   public static Object parseElement(Element element, Properties props, Class expectedClass)
/*  907:     */     throws Exception
/*  908:     */   {
/*  909:1080 */     String clazz = subst(element.getAttribute("class"), props);
/*  910:1081 */     Object instance = OptionConverter.instantiateByClassName(clazz, expectedClass, null);
/*  911:1084 */     if (instance != null)
/*  912:     */     {
/*  913:1085 */       PropertySetter propSetter = new PropertySetter(instance);
/*  914:1086 */       NodeList children = element.getChildNodes();
/*  915:1087 */       int length = children.getLength();
/*  916:1089 */       for (int loop = 0; loop < length; loop++)
/*  917:     */       {
/*  918:1090 */         Node currentNode = children.item(loop);
/*  919:1091 */         if (currentNode.getNodeType() == 1)
/*  920:     */         {
/*  921:1092 */           Element currentElement = (Element)currentNode;
/*  922:1093 */           String tagName = currentElement.getTagName();
/*  923:1094 */           if (tagName.equals("param")) {
/*  924:1095 */             setParameter(currentElement, propSetter, props);
/*  925:     */           } else {
/*  926:1097 */             parseUnrecognizedElement(instance, currentElement, props);
/*  927:     */           }
/*  928:     */         }
/*  929:     */       }
/*  930:1101 */       return instance;
/*  931:     */     }
/*  932:1103 */     return null;
/*  933:     */   }
/*  934:     */   
/*  935:     */   private static abstract interface ParseAction
/*  936:     */   {
/*  937:     */     public abstract Document parse(DocumentBuilder paramDocumentBuilder)
/*  938:     */       throws SAXException, IOException;
/*  939:     */   }
/*  940:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.xml.DOMConfigurator
 * JD-Core Version:    0.7.0.1
 */