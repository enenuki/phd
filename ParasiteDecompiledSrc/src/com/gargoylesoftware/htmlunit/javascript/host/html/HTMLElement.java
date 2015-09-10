/*    1:     */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    5:     */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*    6:     */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*    7:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*    8:     */ import com.gargoylesoftware.htmlunit.WebRequest;
/*    9:     */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   10:     */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   11:     */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*   12:     */ import com.gargoylesoftware.htmlunit.html.DomCharacterData;
/*   13:     */ import com.gargoylesoftware.htmlunit.html.DomComment;
/*   14:     */ import com.gargoylesoftware.htmlunit.html.DomDocumentFragment;
/*   15:     */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   16:     */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   17:     */ import com.gargoylesoftware.htmlunit.html.DomText;
/*   18:     */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*   19:     */ import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
/*   20:     */ import com.gargoylesoftware.htmlunit.html.HtmlBody;
/*   21:     */ import com.gargoylesoftware.htmlunit.html.HtmlDivision;
/*   22:     */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   23:     */ import com.gargoylesoftware.htmlunit.html.HtmlFrameSet;
/*   24:     */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   25:     */ import com.gargoylesoftware.htmlunit.html.HtmlTable;
/*   26:     */ import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
/*   27:     */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*   28:     */ import com.gargoylesoftware.htmlunit.javascript.NamedNodeMap;
/*   29:     */ import com.gargoylesoftware.htmlunit.javascript.ScriptableWithFallbackGetter;
/*   30:     */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJob;
/*   31:     */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
/*   32:     */ import com.gargoylesoftware.htmlunit.javascript.host.Attr;
/*   33:     */ import com.gargoylesoftware.htmlunit.javascript.host.BoxObject;
/*   34:     */ import com.gargoylesoftware.htmlunit.javascript.host.Element;
/*   35:     */ import com.gargoylesoftware.htmlunit.javascript.host.Event;
/*   36:     */ import com.gargoylesoftware.htmlunit.javascript.host.EventHandler;
/*   37:     */ import com.gargoylesoftware.htmlunit.javascript.host.EventListenersContainer;
/*   38:     */ import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;
/*   39:     */ import com.gargoylesoftware.htmlunit.javascript.host.Navigator;
/*   40:     */ import com.gargoylesoftware.htmlunit.javascript.host.Screen;
/*   41:     */ import com.gargoylesoftware.htmlunit.javascript.host.StaticNodeList;
/*   42:     */ import com.gargoylesoftware.htmlunit.javascript.host.TextRange;
/*   43:     */ import com.gargoylesoftware.htmlunit.javascript.host.TextRectangle;
/*   44:     */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   45:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleDeclaration;
/*   46:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.ComputedCSSStyleDeclaration;
/*   47:     */ import java.io.IOException;
/*   48:     */ import java.lang.reflect.InvocationTargetException;
/*   49:     */ import java.lang.reflect.Method;
/*   50:     */ import java.net.MalformedURLException;
/*   51:     */ import java.net.URL;
/*   52:     */ import java.util.ArrayList;
/*   53:     */ import java.util.Arrays;
/*   54:     */ import java.util.HashSet;
/*   55:     */ import java.util.List;
/*   56:     */ import java.util.Map;
/*   57:     */ import java.util.Map.Entry;
/*   58:     */ import java.util.Set;
/*   59:     */ import java.util.regex.Matcher;
/*   60:     */ import java.util.regex.Pattern;
/*   61:     */ import net.sourceforge.htmlunit.corejs.javascript.BaseFunction;
/*   62:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   63:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*   64:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*   65:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   66:     */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*   67:     */ import net.sourceforge.htmlunit.corejs.javascript.NativeArray;
/*   68:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   69:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   70:     */ import org.apache.commons.lang.ArrayUtils;
/*   71:     */ import org.apache.commons.logging.Log;
/*   72:     */ import org.apache.commons.logging.LogFactory;
/*   73:     */ import org.xml.sax.SAXException;
/*   74:     */ import org.xml.sax.helpers.AttributesImpl;
/*   75:     */ 
/*   76:     */ public class HTMLElement
/*   77:     */   extends Element
/*   78:     */   implements ScriptableWithFallbackGetter
/*   79:     */ {
/*   80: 107 */   private static final Pattern PERCENT_VALUE = Pattern.compile("\\d+%");
/*   81: 109 */   private static final Log LOG = LogFactory.getLog(HTMLElement.class);
/*   82:     */   private static final int BEHAVIOR_ID_UNKNOWN = -1;
/*   83:     */   public static final int BEHAVIOR_ID_CLIENT_CAPS = 0;
/*   84:     */   public static final int BEHAVIOR_ID_HOMEPAGE = 1;
/*   85:     */   public static final int BEHAVIOR_ID_DOWNLOAD = 2;
/*   86:     */   private static final String BEHAVIOR_CLIENT_CAPS = "#default#clientCaps";
/*   87:     */   private static final String BEHAVIOR_HOMEPAGE = "#default#homePage";
/*   88:     */   private static final String BEHAVIOR_DOWNLOAD = "#default#download";
/*   89: 123 */   private static final Pattern CLASS_NAMES_SPLIT_PATTERN = Pattern.compile("\\s");
/*   90: 124 */   private static final Pattern PRINT_NODE_PATTERN = Pattern.compile("  ");
/*   91: 125 */   private static final Pattern PRINT_NODE_QUOTE_PATTERN = Pattern.compile("\"");
/*   92:     */   static final String POSITION_BEFORE_BEGIN = "beforeBegin";
/*   93:     */   static final String POSITION_AFTER_BEGIN = "afterBegin";
/*   94:     */   static final String POSITION_BEFORE_END = "beforeEnd";
/*   95:     */   static final String POSITION_AFTER_END = "afterEnd";
/*   96: 135 */   private static int UniqueID_Counter_ = 1;
/*   97:     */   private final Set<String> behaviors_;
/*   98:     */   private BoxObject boxObject_;
/*   99:     */   private HTMLCollection all_;
/*  100:     */   private int scrollLeft_;
/*  101:     */   private int scrollTop_;
/*  102:     */   private String uniqueID_;
/*  103:     */   private CSSStyleDeclaration style_;
/*  104:     */   private String ch_;
/*  105:     */   private String chOff_;
/*  106:     */   
/*  107:     */   public HTMLElement()
/*  108:     */   {
/*  109: 137 */     this.behaviors_ = new HashSet();
/*  110:     */     
/*  111:     */ 
/*  112:     */ 
/*  113:     */ 
/*  114:     */ 
/*  115:     */ 
/*  116:     */ 
/*  117:     */ 
/*  118:     */ 
/*  119:     */ 
/*  120:     */ 
/*  121: 149 */     this.ch_ = "";
/*  122:     */     
/*  123:     */ 
/*  124:     */ 
/*  125:     */ 
/*  126:     */ 
/*  127: 155 */     this.chOff_ = "";
/*  128:     */   }
/*  129:     */   
/*  130: 160 */   private static final List<String> INNER_HTML_READONLY_IN_IE = Arrays.asList(new String[] { "col", "colgroup", "frameset", "head", "html", "style", "table", "tbody", "tfoot", "thead", "title", "tr" });
/*  131: 168 */   private static final List<String> INNER_TEXT_READONLY = Arrays.asList(new String[] { "html", "table", "tbody", "tfoot", "thead", "tr" });
/*  132: 174 */   private static final List<String> OUTER_HTML_READONLY = Arrays.asList(new String[] { "caption", "col", "colgroup", "frameset", "html", "tbody", "td", "tfoot", "th", "thead", "tr" });
/*  133:     */   
/*  134:     */   public HTMLCollection jsxGet_all()
/*  135:     */   {
/*  136: 184 */     if (this.all_ == null) {
/*  137: 185 */       this.all_ = new HTMLCollection(getDomNodeOrDie(), false, "HTMLElement.all")
/*  138:     */       {
/*  139:     */         protected boolean isMatching(DomNode node)
/*  140:     */         {
/*  141: 188 */           return true;
/*  142:     */         }
/*  143:     */       };
/*  144:     */     }
/*  145: 192 */     return this.all_;
/*  146:     */   }
/*  147:     */   
/*  148:     */   public CSSStyleDeclaration jsxGet_style()
/*  149:     */   {
/*  150: 200 */     return this.style_;
/*  151:     */   }
/*  152:     */   
/*  153:     */   public ComputedCSSStyleDeclaration jsxGet_currentStyle()
/*  154:     */   {
/*  155: 208 */     return getWindow().jsxFunction_getComputedStyle(this, null);
/*  156:     */   }
/*  157:     */   
/*  158:     */   public void setDefaults(ComputedCSSStyleDeclaration style) {}
/*  159:     */   
/*  160:     */   public CSSStyleDeclaration jsxGet_runtimeStyle()
/*  161:     */   {
/*  162: 227 */     return this.style_;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public void setDomNode(DomNode domNode)
/*  166:     */   {
/*  167: 236 */     super.setDomNode(domNode);
/*  168:     */     
/*  169: 238 */     this.style_ = new CSSStyleDeclaration(this);
/*  170:     */     
/*  171:     */ 
/*  172:     */ 
/*  173:     */ 
/*  174:     */ 
/*  175: 244 */     HtmlElement htmlElt = (HtmlElement)domNode;
/*  176: 245 */     for (DomAttr attr : htmlElt.getAttributesMap().values())
/*  177:     */     {
/*  178: 246 */       String eventName = attr.getName();
/*  179: 247 */       if (eventName.startsWith("on")) {
/*  180: 248 */         createEventHandler(eventName, attr.getValue());
/*  181:     */       }
/*  182:     */     }
/*  183:     */   }
/*  184:     */   
/*  185:     */   protected void createEventHandler(String eventName, String attrValue)
/*  186:     */   {
/*  187: 259 */     HtmlElement htmlElt = getDomNodeOrDie();
/*  188:     */     
/*  189: 261 */     BaseFunction eventHandler = new EventHandler(htmlElt, eventName, attrValue);
/*  190: 262 */     setEventHandler(eventName, eventHandler);
/*  191: 264 */     if (((htmlElt instanceof HtmlBody)) || ((htmlElt instanceof HtmlFrameSet))) {
/*  192: 265 */       getWindow().getEventListenersContainer().setEventHandlerProp(eventName.substring(2), eventHandler);
/*  193:     */     }
/*  194:     */   }
/*  195:     */   
/*  196:     */   public String jsxGet_id()
/*  197:     */   {
/*  198: 275 */     return getDomNodeOrDie().getId();
/*  199:     */   }
/*  200:     */   
/*  201:     */   public void jsxSet_id(String newId)
/*  202:     */   {
/*  203: 283 */     getDomNodeOrDie().setId(newId);
/*  204:     */   }
/*  205:     */   
/*  206:     */   public String jsxGet_title()
/*  207:     */   {
/*  208: 291 */     return getDomNodeOrDie().getAttribute("title");
/*  209:     */   }
/*  210:     */   
/*  211:     */   public void jsxSet_title(String newTitle)
/*  212:     */   {
/*  213: 299 */     getDomNodeOrDie().setAttribute("title", newTitle);
/*  214:     */   }
/*  215:     */   
/*  216:     */   public boolean jsxGet_disabled()
/*  217:     */   {
/*  218: 307 */     return getDomNodeOrDie().hasAttribute("disabled");
/*  219:     */   }
/*  220:     */   
/*  221:     */   public DocumentProxy jsxGet_document()
/*  222:     */   {
/*  223: 315 */     return getWindow().jsxGet_document();
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void jsxSet_disabled(boolean disabled)
/*  227:     */   {
/*  228: 323 */     HtmlElement element = getDomNodeOrDie();
/*  229: 324 */     if (disabled) {
/*  230: 325 */       element.setAttribute("disabled", "disabled");
/*  231:     */     } else {
/*  232: 328 */       element.removeAttribute("disabled");
/*  233:     */     }
/*  234:     */   }
/*  235:     */   
/*  236:     */   public String jsxGet_namespaceURI()
/*  237:     */   {
/*  238: 337 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_65)) {
/*  239: 338 */       return getDomNodeOrDie().getNamespaceURI();
/*  240:     */     }
/*  241: 340 */     if ((getDomNodeOrDie().getPage() instanceof HtmlPage)) {
/*  242: 341 */       return null;
/*  243:     */     }
/*  244: 343 */     return getDomNodeOrDie().getNamespaceURI();
/*  245:     */   }
/*  246:     */   
/*  247:     */   public String jsxGet_localName()
/*  248:     */   {
/*  249: 351 */     DomNode domNode = getDomNodeOrDie();
/*  250: 352 */     if ((domNode.getPage() instanceof HtmlPage))
/*  251:     */     {
/*  252: 353 */       StringBuilder localName = new StringBuilder();
/*  253: 354 */       String prefix = domNode.getPrefix();
/*  254: 355 */       if (prefix != null)
/*  255:     */       {
/*  256: 356 */         localName.append(prefix);
/*  257: 357 */         localName.append(':');
/*  258:     */       }
/*  259: 359 */       localName.append(domNode.getLocalName());
/*  260: 360 */       return localName.toString().toUpperCase();
/*  261:     */     }
/*  262: 362 */     return domNode.getLocalName();
/*  263:     */   }
/*  264:     */   
/*  265:     */   public Object getWithFallback(String name)
/*  266:     */   {
/*  267: 370 */     if (!"class".equals(name))
/*  268:     */     {
/*  269: 371 */       HtmlElement htmlElement = getDomNodeOrNull();
/*  270: 372 */       if ((htmlElement != null) && (isAttributeName(name)))
/*  271:     */       {
/*  272: 373 */         String value = htmlElement.getAttribute(name);
/*  273: 374 */         if (DomElement.ATTRIBUTE_NOT_DEFINED != value)
/*  274:     */         {
/*  275: 375 */           if (LOG.isDebugEnabled()) {
/*  276: 376 */             LOG.debug("Found attribute for evaluation of property \"" + name + "\" for of " + this);
/*  277:     */           }
/*  278: 379 */           return value;
/*  279:     */         }
/*  280:     */       }
/*  281:     */     }
/*  282: 384 */     return NOT_FOUND;
/*  283:     */   }
/*  284:     */   
/*  285:     */   protected boolean isAttributeName(String name)
/*  286:     */   {
/*  287: 397 */     return name.toLowerCase().equals(name);
/*  288:     */   }
/*  289:     */   
/*  290:     */   protected String fixAttributeName(String attributeName)
/*  291:     */   {
/*  292: 407 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_66))
/*  293:     */     {
/*  294: 408 */       if ("className".equals(attributeName)) {
/*  295: 409 */         return "class";
/*  296:     */       }
/*  297: 411 */       if ("class".equals(attributeName)) {
/*  298: 412 */         return "_class";
/*  299:     */       }
/*  300:     */     }
/*  301: 416 */     return attributeName;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public void jsxFunction_clearAttributes()
/*  305:     */   {
/*  306: 423 */     HtmlElement node = getDomNodeOrDie();
/*  307:     */     
/*  308:     */ 
/*  309: 426 */     List<String> removals = new ArrayList();
/*  310: 427 */     for (String attributeName : node.getAttributesMap().keySet()) {
/*  311: 430 */       if (!ScriptableObject.hasProperty(getPrototype(), attributeName)) {
/*  312: 431 */         removals.add(attributeName);
/*  313:     */       }
/*  314:     */     }
/*  315: 434 */     for (String attributeName : removals) {
/*  316: 435 */       node.removeAttribute(attributeName);
/*  317:     */     }
/*  318: 439 */     for (Object id : getAllIds()) {
/*  319: 440 */       if ((id instanceof Integer))
/*  320:     */       {
/*  321: 441 */         int i = ((Integer)id).intValue();
/*  322: 442 */         delete(i);
/*  323:     */       }
/*  324: 444 */       else if ((id instanceof String))
/*  325:     */       {
/*  326: 445 */         delete((String)id);
/*  327:     */       }
/*  328:     */     }
/*  329:     */   }
/*  330:     */   
/*  331:     */   public void jsxFunction_mergeAttributes(HTMLElement source, Object preserveIdentity)
/*  332:     */   {
/*  333: 457 */     HtmlElement src = source.getDomNodeOrDie();
/*  334: 458 */     HtmlElement target = getDomNodeOrDie();
/*  335: 461 */     for (Map.Entry<String, DomAttr> attribute : src.getAttributesMap().entrySet())
/*  336:     */     {
/*  337: 464 */       String attributeName = (String)attribute.getKey();
/*  338: 465 */       if (!ScriptableObject.hasProperty(getPrototype(), attributeName))
/*  339:     */       {
/*  340: 466 */         String attributeValue = ((DomAttr)attribute.getValue()).getNodeValue();
/*  341: 467 */         target.setAttribute(attributeName, attributeValue);
/*  342:     */       }
/*  343:     */     }
/*  344: 472 */     for (Object id : source.getAllIds()) {
/*  345: 473 */       if ((id instanceof Integer))
/*  346:     */       {
/*  347: 474 */         int i = ((Integer)id).intValue();
/*  348: 475 */         put(i, this, source.get(i, source));
/*  349:     */       }
/*  350: 477 */       else if ((id instanceof String))
/*  351:     */       {
/*  352: 478 */         String s = (String)id;
/*  353: 479 */         put(s, this, source.get(s, source));
/*  354:     */       }
/*  355:     */     }
/*  356: 484 */     if (((preserveIdentity instanceof Boolean)) && (!((Boolean)preserveIdentity).booleanValue()))
/*  357:     */     {
/*  358: 485 */       target.setId(src.getId());
/*  359: 486 */       target.setAttribute("name", src.getAttribute("name"));
/*  360:     */     }
/*  361:     */   }
/*  362:     */   
/*  363:     */   public Object jsxFunction_getAttributeNodeNS(String namespaceURI, String localName)
/*  364:     */   {
/*  365: 497 */     return getDomNodeOrDie().getAttributeNodeNS(namespaceURI, localName).getScriptObject();
/*  366:     */   }
/*  367:     */   
/*  368:     */   public String jsxFunction_getAttributeNS(String namespaceURI, String localName)
/*  369:     */   {
/*  370: 507 */     return getDomNodeOrDie().getAttributeNS(namespaceURI, localName);
/*  371:     */   }
/*  372:     */   
/*  373:     */   public boolean jsxFunction_hasAttributeNS(String namespaceURI, String localName)
/*  374:     */   {
/*  375: 520 */     return getDomNodeOrDie().hasAttributeNS(namespaceURI, localName);
/*  376:     */   }
/*  377:     */   
/*  378:     */   public void jsxFunction_setAttribute(String name, String value)
/*  379:     */   {
/*  380: 533 */     name = fixAttributeName(name);
/*  381: 534 */     getDomNodeOrDie().setAttribute(name, value);
/*  382: 537 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_167)) {
/*  383:     */       try
/*  384:     */       {
/*  385: 539 */         Method method = getClass().getMethod("jsxSet_" + name, new Class[] { Object.class });
/*  386: 540 */         String source = "function(){" + value + "}";
/*  387: 541 */         method.invoke(this, new Object[] { Context.getCurrentContext().compileFunction(getWindow(), source, "", 0, null) });
/*  388:     */       }
/*  389:     */       catch (NoSuchMethodException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e)
/*  390:     */       {
/*  391: 551 */         throw new RuntimeException(e.getCause());
/*  392:     */       }
/*  393:     */     }
/*  394:     */   }
/*  395:     */   
/*  396:     */   public void jsxFunction_setAttributeNS(String namespaceURI, String qualifiedName, String value)
/*  397:     */   {
/*  398: 563 */     getDomNodeOrDie().setAttributeNS(namespaceURI, qualifiedName, value);
/*  399:     */   }
/*  400:     */   
/*  401:     */   public void jsxFunction_removeAttributeNS(String namespaceURI, String localName)
/*  402:     */   {
/*  403: 572 */     getDomNodeOrDie().removeAttributeNS(namespaceURI, localName);
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void jsxFunction_removeAttributeNode(Attr attribute)
/*  407:     */   {
/*  408: 580 */     String name = attribute.jsxGet_name();
/*  409: 581 */     String namespaceUri = attribute.jsxGet_namespaceURI();
/*  410: 582 */     jsxFunction_removeAttributeNS(namespaceUri, name);
/*  411:     */   }
/*  412:     */   
/*  413:     */   public HTMLElement jsxFunction_removeNode(boolean removeChildren)
/*  414:     */   {
/*  415: 591 */     HTMLElement parent = jsxGet_parentElement();
/*  416: 592 */     if (parent != null)
/*  417:     */     {
/*  418: 593 */       parent.jsxFunction_removeChild(this);
/*  419: 594 */       if (!removeChildren)
/*  420:     */       {
/*  421: 595 */         HTMLCollection collection = jsxGet_childNodes();
/*  422: 596 */         int length = collection.jsxGet_length();
/*  423: 597 */         for (int i = 0; i < length; i++)
/*  424:     */         {
/*  425: 598 */           com.gargoylesoftware.htmlunit.javascript.host.Node object = (com.gargoylesoftware.htmlunit.javascript.host.Node)collection.jsxFunction_item(Integer.valueOf(0));
/*  426: 599 */           parent.jsxFunction_appendChild(object);
/*  427:     */         }
/*  428:     */       }
/*  429:     */     }
/*  430: 603 */     return this;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public Object jsxFunction_getAttributeNode(String attributeName)
/*  434:     */   {
/*  435: 613 */     return ((NamedNodeMap)jsxGet_attributes()).jsxFunction_getNamedItem(attributeName);
/*  436:     */   }
/*  437:     */   
/*  438:     */   public Attr jsxFunction_setAttributeNode(Attr newAtt)
/*  439:     */   {
/*  440: 622 */     String name = newAtt.jsxGet_name();
/*  441: 623 */     String value = newAtt.jsxGet_value();
/*  442: 624 */     Attr replacedAtt = (Attr)jsxFunction_getAttributeNode(name);
/*  443: 625 */     if (replacedAtt != null) {
/*  444: 626 */       replacedAtt.detachFromParent();
/*  445:     */     }
/*  446: 628 */     getDomNodeOrDie().setAttribute(name, value);
/*  447: 629 */     return replacedAtt;
/*  448:     */   }
/*  449:     */   
/*  450:     */   public HTMLCollection jsxFunction_getElementsByClassName(String className)
/*  451:     */   {
/*  452: 638 */     HtmlElement elt = getDomNodeOrDie();
/*  453: 639 */     String description = "HTMLElement.getElementsByClassName('" + className + "')";
/*  454: 640 */     final String[] classNames = CLASS_NAMES_SPLIT_PATTERN.split(className, 0);
/*  455:     */     
/*  456: 642 */     HTMLCollection collection = new HTMLCollection(elt, true, description)
/*  457:     */     {
/*  458:     */       protected boolean isMatching(DomNode node)
/*  459:     */       {
/*  460: 644 */         if (!(node instanceof HtmlElement)) {
/*  461: 645 */           return false;
/*  462:     */         }
/*  463: 647 */         HtmlElement elt = (HtmlElement)node;
/*  464: 648 */         String classAttribute = elt.getAttribute("class");
/*  465: 649 */         if (classAttribute == DomElement.ATTRIBUTE_NOT_DEFINED) {
/*  466: 650 */           return false;
/*  467:     */         }
/*  468: 653 */         classAttribute = " " + classAttribute + " ";
/*  469: 654 */         for (String aClassName : classNames) {
/*  470: 655 */           if (!classAttribute.contains(" " + aClassName + " ")) {
/*  471: 656 */             return false;
/*  472:     */           }
/*  473:     */         }
/*  474: 659 */         return true;
/*  475:     */       }
/*  476: 662 */     };
/*  477: 663 */     return collection;
/*  478:     */   }
/*  479:     */   
/*  480:     */   public Object jsxGet_className()
/*  481:     */   {
/*  482: 671 */     return getDomNodeOrDie().getAttribute("class");
/*  483:     */   }
/*  484:     */   
/*  485:     */   public int jsxGet_clientHeight()
/*  486:     */   {
/*  487: 679 */     boolean includePadding = !getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_67);
/*  488: 680 */     ComputedCSSStyleDeclaration style = getWindow().jsxFunction_getComputedStyle(this, null);
/*  489: 681 */     return style.getCalculatedHeight(false, includePadding);
/*  490:     */   }
/*  491:     */   
/*  492:     */   public int jsxGet_clientWidth()
/*  493:     */   {
/*  494: 689 */     boolean includePadding = !getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_68);
/*  495: 690 */     ComputedCSSStyleDeclaration style = getWindow().jsxFunction_getComputedStyle(this, null);
/*  496: 691 */     return style.getCalculatedWidth(false, includePadding);
/*  497:     */   }
/*  498:     */   
/*  499:     */   public int jsxGet_clientLeft()
/*  500:     */   {
/*  501: 699 */     return 2;
/*  502:     */   }
/*  503:     */   
/*  504:     */   public int jsxGet_clientTop()
/*  505:     */   {
/*  506: 707 */     return 2;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void jsxSet_className(String className)
/*  510:     */   {
/*  511: 715 */     getDomNodeOrDie().setAttribute("class", className);
/*  512:     */   }
/*  513:     */   
/*  514:     */   public String jsxGet_innerHTML()
/*  515:     */   {
/*  516: 723 */     StringBuilder buf = new StringBuilder();
/*  517:     */     
/*  518: 725 */     printChildren(buf, getDomNodeOrDie(), !"SCRIPT".equals(jsxGet_tagName()));
/*  519: 726 */     return buf.toString();
/*  520:     */   }
/*  521:     */   
/*  522:     */   public String jsxGet_innerText()
/*  523:     */   {
/*  524: 734 */     StringBuilder buf = new StringBuilder();
/*  525:     */     
/*  526: 736 */     printChildren(buf, getDomNodeOrDie(), false);
/*  527: 737 */     return buf.toString();
/*  528:     */   }
/*  529:     */   
/*  530:     */   public String jsxGet_outerHTML()
/*  531:     */   {
/*  532: 746 */     StringBuilder buf = new StringBuilder();
/*  533:     */     
/*  534: 748 */     printNode(buf, getDomNodeOrDie(), true);
/*  535: 749 */     return buf.toString();
/*  536:     */   }
/*  537:     */   
/*  538:     */   private void printChildren(StringBuilder buffer, DomNode node, boolean html)
/*  539:     */   {
/*  540: 753 */     for (DomNode child : node.getChildren()) {
/*  541: 754 */       printNode(buffer, child, html);
/*  542:     */     }
/*  543:     */   }
/*  544:     */   
/*  545:     */   private void printNode(StringBuilder buffer, DomNode node, boolean html)
/*  546:     */   {
/*  547: 759 */     if ((node instanceof DomComment))
/*  548:     */     {
/*  549: 760 */       if (html)
/*  550:     */       {
/*  551: 762 */         String s = PRINT_NODE_PATTERN.matcher(node.getNodeValue()).replaceAll(" ");
/*  552: 763 */         buffer.append("<!--").append(s).append("-->");
/*  553:     */       }
/*  554:     */     }
/*  555: 766 */     else if ((node instanceof DomCharacterData))
/*  556:     */     {
/*  557: 768 */       String s = node.getNodeValue();
/*  558: 769 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_INNER_HTML_REDUCE_WHITESPACES)) {
/*  559: 770 */         s = PRINT_NODE_PATTERN.matcher(s).replaceAll(" ");
/*  560:     */       }
/*  561: 772 */       if (html) {
/*  562: 773 */         s = com.gargoylesoftware.htmlunit.util.StringUtils.escapeXmlChars(s);
/*  563:     */       }
/*  564: 775 */       buffer.append(s);
/*  565:     */     }
/*  566: 777 */     else if (html)
/*  567:     */     {
/*  568: 779 */       HtmlElement element = (HtmlElement)node;
/*  569: 780 */       boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_69);
/*  570: 781 */       String tag = element.getTagName();
/*  571: 782 */       if (ie) {
/*  572: 783 */         tag = tag.toUpperCase();
/*  573:     */       }
/*  574: 785 */       buffer.append("<").append(tag);
/*  575: 787 */       for (DomAttr attr : element.getAttributesMap().values())
/*  576:     */       {
/*  577: 788 */         String name = attr.getName();
/*  578: 789 */         String value = PRINT_NODE_QUOTE_PATTERN.matcher(attr.getValue()).replaceAll("&quot;");
/*  579: 790 */         boolean quote = (!ie) || (com.gargoylesoftware.htmlunit.util.StringUtils.containsWhitespace(value)) || (value.length() == 0) || (((element instanceof HtmlAnchor)) && ("href".equals(name)));
/*  580:     */         
/*  581:     */ 
/*  582:     */ 
/*  583: 794 */         buffer.append(' ').append(name).append("=");
/*  584: 795 */         if (quote) {
/*  585: 796 */           buffer.append("\"");
/*  586:     */         }
/*  587: 798 */         buffer.append(value);
/*  588: 799 */         if (quote) {
/*  589: 800 */           buffer.append("\"");
/*  590:     */         }
/*  591:     */       }
/*  592: 803 */       buffer.append(">");
/*  593:     */       
/*  594: 805 */       printChildren(buffer, node, html);
/*  595:     */       
/*  596: 807 */       buffer.append("</").append(tag).append(">");
/*  597:     */     }
/*  598:     */     else
/*  599:     */     {
/*  600: 810 */       HtmlElement element = (HtmlElement)node;
/*  601: 811 */       if ("p".equals(element.getTagName())) {
/*  602: 812 */         buffer.append("\r\n");
/*  603:     */       }
/*  604: 814 */       if (!"script".equals(element.getTagName())) {
/*  605: 815 */         printChildren(buffer, node, html);
/*  606:     */       }
/*  607:     */     }
/*  608:     */   }
/*  609:     */   
/*  610:     */   public void jsxSet_innerHTML(Object value)
/*  611:     */   {
/*  612: 825 */     DomNode domNode = getDomNodeOrDie();
/*  613: 826 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_70);
/*  614: 828 */     if ((ie) && (INNER_HTML_READONLY_IN_IE.contains(domNode.getNodeName()))) {
/*  615: 829 */       throw Context.reportRuntimeError("innerHTML is read-only for tag " + domNode.getNodeName());
/*  616:     */     }
/*  617: 832 */     domNode.removeAllChildren();
/*  618: 837 */     if (((value == null) && (ie)) || ((value != null) && (!"".equals(value))))
/*  619:     */     {
/*  620: 839 */       String valueAsString = Context.toString(value);
/*  621: 840 */       parseHtmlSnippet(domNode, true, valueAsString);
/*  622: 844 */       if ((domNode.getParentNode() == null) && (ie))
/*  623:     */       {
/*  624: 845 */         DomDocumentFragment fragment = ((HtmlPage)domNode.getPage()).createDomDocumentFragment();
/*  625: 846 */         fragment.appendChild(domNode);
/*  626:     */       }
/*  627:     */     }
/*  628:     */   }
/*  629:     */   
/*  630:     */   public void jsxSet_innerText(String value)
/*  631:     */   {
/*  632: 856 */     setInnerText(Context.toString(value));
/*  633:     */   }
/*  634:     */   
/*  635:     */   private void setInnerText(String value)
/*  636:     */   {
/*  637: 860 */     DomNode domNode = getDomNodeOrDie();
/*  638: 862 */     if (INNER_TEXT_READONLY.contains(domNode.getNodeName())) {
/*  639: 863 */       throw Context.reportRuntimeError("innerText is read-only for tag " + domNode.getNodeName());
/*  640:     */     }
/*  641: 866 */     domNode.removeAllChildren();
/*  642: 868 */     if ((value != null) && (value.length() != 0)) {
/*  643: 869 */       domNode.appendChild(new DomText(domNode.getPage(), Context.toString(value)));
/*  644:     */     }
/*  645: 874 */     if ((domNode.getParentNode() == null) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_71)))
/*  646:     */     {
/*  647: 875 */       DomDocumentFragment fragment = ((HtmlPage)domNode.getPage()).createDomDocumentFragment();
/*  648: 876 */       fragment.appendChild(domNode);
/*  649:     */     }
/*  650:     */   }
/*  651:     */   
/*  652:     */   public void jsxSet_textContent(Object value)
/*  653:     */   {
/*  654: 886 */     setInnerText(value == null ? null : Context.toString(value));
/*  655:     */   }
/*  656:     */   
/*  657:     */   public void jsxSet_outerHTML(String value)
/*  658:     */   {
/*  659: 896 */     DomNode domNode = getDomNodeOrDie();
/*  660: 898 */     if (OUTER_HTML_READONLY.contains(domNode.getNodeName())) {
/*  661: 899 */       throw Context.reportRuntimeError("outerHTML is read-only for tag " + domNode.getNodeName());
/*  662:     */     }
/*  663: 902 */     DomNode proxyNode = new ProxyDomNode(domNode.getPage(), domNode, false);
/*  664: 903 */     parseHtmlSnippet(proxyNode, false, value);
/*  665: 904 */     domNode.remove();
/*  666:     */   }
/*  667:     */   
/*  668:     */   public static void parseHtmlSnippet(DomNode target, boolean append, String source)
/*  669:     */   {
/*  670:     */     try
/*  671:     */     {
/*  672: 916 */       HTMLParser.parseFragment(target, source);
/*  673:     */     }
/*  674:     */     catch (IOException e)
/*  675:     */     {
/*  676: 919 */       LogFactory.getLog(HtmlElement.class).error("Unexpected exception occurred while parsing HTML snippet", e);
/*  677: 920 */       throw Context.reportRuntimeError("Unexpected exception occurred while parsing HTML snippet: " + e.getMessage());
/*  678:     */     }
/*  679:     */     catch (SAXException e)
/*  680:     */     {
/*  681: 924 */       LogFactory.getLog(HtmlElement.class).error("Unexpected exception occurred while parsing HTML snippet", e);
/*  682: 925 */       throw Context.reportRuntimeError("Unexpected exception occurred while parsing HTML snippet: " + e.getMessage());
/*  683:     */     }
/*  684:     */   }
/*  685:     */   
/*  686:     */   public static class ProxyDomNode
/*  687:     */     extends HtmlDivision
/*  688:     */   {
/*  689:     */     private final DomNode target_;
/*  690:     */     private boolean append_;
/*  691:     */     
/*  692:     */     public ProxyDomNode(SgmlPage page, DomNode target, boolean append)
/*  693:     */     {
/*  694: 945 */       super("div", page, null);
/*  695: 946 */       this.target_ = target;
/*  696: 947 */       this.append_ = append;
/*  697:     */     }
/*  698:     */     
/*  699:     */     public DomNode appendChild(org.w3c.dom.Node node)
/*  700:     */     {
/*  701: 955 */       DomNode domNode = (DomNode)node;
/*  702: 956 */       if (this.append_) {
/*  703: 957 */         return this.target_.appendChild(domNode);
/*  704:     */       }
/*  705: 959 */       this.target_.insertBefore(domNode);
/*  706: 960 */       return domNode;
/*  707:     */     }
/*  708:     */     
/*  709:     */     public DomNode getDomNode()
/*  710:     */     {
/*  711: 968 */       return this.target_;
/*  712:     */     }
/*  713:     */     
/*  714:     */     public boolean isAppend()
/*  715:     */     {
/*  716: 976 */       return this.append_;
/*  717:     */     }
/*  718:     */   }
/*  719:     */   
/*  720:     */   protected AttributesImpl readAttributes(HtmlElement element)
/*  721:     */   {
/*  722: 986 */     AttributesImpl attributes = new AttributesImpl();
/*  723: 987 */     for (DomAttr entry : element.getAttributesMap().values())
/*  724:     */     {
/*  725: 988 */       String name = entry.getName();
/*  726: 989 */       String value = entry.getValue();
/*  727: 990 */       attributes.addAttribute(null, name, name, null, value);
/*  728:     */     }
/*  729: 993 */     return attributes;
/*  730:     */   }
/*  731:     */   
/*  732:     */   public void jsxFunction_insertAdjacentHTML(String where, String text)
/*  733:     */   {
/*  734:1005 */     Object[] values = getInsertAdjacentLocation(where);
/*  735:1006 */     DomNode node = (DomNode)values[0];
/*  736:1007 */     boolean append = ((Boolean)values[1]).booleanValue();
/*  737:     */     
/*  738:     */ 
/*  739:1010 */     DomNode proxyNode = new ProxyDomNode(node.getPage(), node, append);
/*  740:1011 */     parseHtmlSnippet(proxyNode, append, text);
/*  741:     */   }
/*  742:     */   
/*  743:     */   public Object jsxFunction_insertAdjacentElement(String where, Object object)
/*  744:     */   {
/*  745:1024 */     if ((object instanceof com.gargoylesoftware.htmlunit.javascript.host.Node))
/*  746:     */     {
/*  747:1025 */       DomNode childNode = ((com.gargoylesoftware.htmlunit.javascript.host.Node)object).getDomNodeOrDie();
/*  748:1026 */       Object[] values = getInsertAdjacentLocation(where);
/*  749:1027 */       DomNode node = (DomNode)values[0];
/*  750:1028 */       boolean append = ((Boolean)values[1]).booleanValue();
/*  751:1030 */       if (append) {
/*  752:1031 */         node.appendChild(childNode);
/*  753:     */       } else {
/*  754:1034 */         node.insertBefore(childNode);
/*  755:     */       }
/*  756:1036 */       return object;
/*  757:     */     }
/*  758:1038 */     throw Context.reportRuntimeError("Passed object is not an element: " + object);
/*  759:     */   }
/*  760:     */   
/*  761:     */   private Object[] getInsertAdjacentLocation(String where)
/*  762:     */   {
/*  763:1052 */     DomNode currentNode = getDomNodeOrDie();
/*  764:     */     boolean append;
/*  765:1057 */     if ("afterBegin".equalsIgnoreCase(where))
/*  766:     */     {
/*  767:     */       boolean append;
/*  768:1058 */       if (currentNode.getFirstChild() == null)
/*  769:     */       {
/*  770:1060 */         DomNode node = currentNode;
/*  771:1061 */         append = true;
/*  772:     */       }
/*  773:     */       else
/*  774:     */       {
/*  775:1065 */         DomNode node = currentNode.getFirstChild();
/*  776:1066 */         append = false;
/*  777:     */       }
/*  778:     */     }
/*  779:     */     else
/*  780:     */     {
/*  781:     */       boolean append;
/*  782:1069 */       if ("beforeBegin".equalsIgnoreCase(where))
/*  783:     */       {
/*  784:1071 */         DomNode node = currentNode;
/*  785:1072 */         append = false;
/*  786:     */       }
/*  787:     */       else
/*  788:     */       {
/*  789:     */         boolean append;
/*  790:1074 */         if ("beforeEnd".equalsIgnoreCase(where))
/*  791:     */         {
/*  792:1076 */           DomNode node = currentNode;
/*  793:1077 */           append = true;
/*  794:     */         }
/*  795:     */         else
/*  796:     */         {
/*  797:     */           boolean append;
/*  798:1079 */           if ("afterEnd".equalsIgnoreCase(where))
/*  799:     */           {
/*  800:     */             boolean append;
/*  801:1080 */             if (currentNode.getNextSibling() == null)
/*  802:     */             {
/*  803:1082 */               DomNode node = currentNode.getParentNode();
/*  804:1083 */               append = true;
/*  805:     */             }
/*  806:     */             else
/*  807:     */             {
/*  808:1087 */               DomNode node = currentNode.getNextSibling();
/*  809:1088 */               append = false;
/*  810:     */             }
/*  811:     */           }
/*  812:     */           else
/*  813:     */           {
/*  814:1092 */             throw Context.reportRuntimeError("Illegal position value: \"" + where + "\"");
/*  815:     */           }
/*  816:     */         }
/*  817:     */       }
/*  818:     */     }
/*  819:     */     boolean append;
/*  820:     */     DomNode node;
/*  821:1095 */     if (append) {
/*  822:1096 */       return new Object[] { node, Boolean.TRUE };
/*  823:     */     }
/*  824:1098 */     return new Object[] { node, Boolean.FALSE };
/*  825:     */   }
/*  826:     */   
/*  827:     */   public int jsxFunction_addBehavior(String behavior)
/*  828:     */   {
/*  829:1114 */     if (this.behaviors_.contains(behavior)) {
/*  830:1115 */       return 0;
/*  831:     */     }
/*  832:1118 */     Class<? extends HTMLElement> c = getClass();
/*  833:1119 */     if ("#default#clientCaps".equalsIgnoreCase(behavior))
/*  834:     */     {
/*  835:1120 */       defineProperty("availHeight", c, 0);
/*  836:1121 */       defineProperty("availWidth", c, 0);
/*  837:1122 */       defineProperty("bufferDepth", c, 0);
/*  838:1123 */       defineProperty("colorDepth", c, 0);
/*  839:1124 */       defineProperty("connectionType", c, 0);
/*  840:1125 */       defineProperty("cookieEnabled", c, 0);
/*  841:1126 */       defineProperty("cpuClass", c, 0);
/*  842:1127 */       defineProperty("height", c, 0);
/*  843:1128 */       defineProperty("javaEnabled", c, 0);
/*  844:1129 */       defineProperty("platform", c, 0);
/*  845:1130 */       defineProperty("systemLanguage", c, 0);
/*  846:1131 */       defineProperty("userLanguage", c, 0);
/*  847:1132 */       defineProperty("width", c, 0);
/*  848:1133 */       defineFunctionProperties(new String[] { "addComponentRequest" }, c, 0);
/*  849:1134 */       defineFunctionProperties(new String[] { "clearComponentRequest" }, c, 0);
/*  850:1135 */       defineFunctionProperties(new String[] { "compareVersions" }, c, 0);
/*  851:1136 */       defineFunctionProperties(new String[] { "doComponentRequest" }, c, 0);
/*  852:1137 */       defineFunctionProperties(new String[] { "getComponentVersion" }, c, 0);
/*  853:1138 */       defineFunctionProperties(new String[] { "isComponentInstalled" }, c, 0);
/*  854:1139 */       this.behaviors_.add("#default#clientCaps");
/*  855:1140 */       return 0;
/*  856:     */     }
/*  857:1142 */     if ("#default#homePage".equalsIgnoreCase(behavior))
/*  858:     */     {
/*  859:1143 */       defineFunctionProperties(new String[] { "isHomePage" }, c, 0);
/*  860:1144 */       defineFunctionProperties(new String[] { "setHomePage" }, c, 0);
/*  861:1145 */       defineFunctionProperties(new String[] { "navigateHomePage" }, c, 0);
/*  862:1146 */       this.behaviors_.add("#default#clientCaps");
/*  863:1147 */       return 1;
/*  864:     */     }
/*  865:1149 */     if ("#default#download".equalsIgnoreCase(behavior))
/*  866:     */     {
/*  867:1150 */       defineFunctionProperties(new String[] { "startDownload" }, c, 0);
/*  868:1151 */       this.behaviors_.add("#default#download");
/*  869:1152 */       return 2;
/*  870:     */     }
/*  871:1155 */     LOG.warn("Unimplemented behavior: " + behavior);
/*  872:1156 */     return -1;
/*  873:     */   }
/*  874:     */   
/*  875:     */   public void jsxFunction_removeBehavior(int id)
/*  876:     */   {
/*  877:1165 */     switch (id)
/*  878:     */     {
/*  879:     */     case 0: 
/*  880:1167 */       delete("availHeight");
/*  881:1168 */       delete("availWidth");
/*  882:1169 */       delete("bufferDepth");
/*  883:1170 */       delete("colorDepth");
/*  884:1171 */       delete("connectionType");
/*  885:1172 */       delete("cookieEnabled");
/*  886:1173 */       delete("cpuClass");
/*  887:1174 */       delete("height");
/*  888:1175 */       delete("javaEnabled");
/*  889:1176 */       delete("platform");
/*  890:1177 */       delete("systemLanguage");
/*  891:1178 */       delete("userLanguage");
/*  892:1179 */       delete("width");
/*  893:1180 */       delete("addComponentRequest");
/*  894:1181 */       delete("clearComponentRequest");
/*  895:1182 */       delete("compareVersions");
/*  896:1183 */       delete("doComponentRequest");
/*  897:1184 */       delete("getComponentVersion");
/*  898:1185 */       delete("isComponentInstalled");
/*  899:1186 */       this.behaviors_.remove("#default#clientCaps");
/*  900:1187 */       break;
/*  901:     */     case 1: 
/*  902:1189 */       delete("isHomePage");
/*  903:1190 */       delete("setHomePage");
/*  904:1191 */       delete("navigateHomePage");
/*  905:1192 */       this.behaviors_.remove("#default#homePage");
/*  906:1193 */       break;
/*  907:     */     case 2: 
/*  908:1195 */       delete("startDownload");
/*  909:1196 */       this.behaviors_.remove("#default#download");
/*  910:1197 */       break;
/*  911:     */     default: 
/*  912:1199 */       LOG.warn("Unexpected behavior id: " + id + ". Ignoring.");
/*  913:     */     }
/*  914:     */   }
/*  915:     */   
/*  916:     */   public int getAvailHeight()
/*  917:     */   {
/*  918:1211 */     return getWindow().jsxGet_screen().jsxGet_availHeight();
/*  919:     */   }
/*  920:     */   
/*  921:     */   public int getAvailWidth()
/*  922:     */   {
/*  923:1220 */     return getWindow().jsxGet_screen().jsxGet_availWidth();
/*  924:     */   }
/*  925:     */   
/*  926:     */   public int getBufferDepth()
/*  927:     */   {
/*  928:1229 */     return getWindow().jsxGet_screen().jsxGet_bufferDepth();
/*  929:     */   }
/*  930:     */   
/*  931:     */   public BoxObject getBoxObject()
/*  932:     */   {
/*  933:1237 */     if (this.boxObject_ == null)
/*  934:     */     {
/*  935:1238 */       this.boxObject_ = new BoxObject(this);
/*  936:1239 */       this.boxObject_.setParentScope(getWindow());
/*  937:1240 */       this.boxObject_.setPrototype(getPrototype(this.boxObject_.getClass()));
/*  938:     */     }
/*  939:1242 */     return this.boxObject_;
/*  940:     */   }
/*  941:     */   
/*  942:     */   public int getColorDepth()
/*  943:     */   {
/*  944:1251 */     return getWindow().jsxGet_screen().jsxGet_colorDepth();
/*  945:     */   }
/*  946:     */   
/*  947:     */   public String getConnectionType()
/*  948:     */   {
/*  949:1261 */     return "modem";
/*  950:     */   }
/*  951:     */   
/*  952:     */   public boolean getCookieEnabled()
/*  953:     */   {
/*  954:1270 */     return getWindow().jsxGet_navigator().jsxGet_cookieEnabled();
/*  955:     */   }
/*  956:     */   
/*  957:     */   public String getCpuClass()
/*  958:     */   {
/*  959:1279 */     return getWindow().jsxGet_navigator().jsxGet_cpuClass();
/*  960:     */   }
/*  961:     */   
/*  962:     */   public int getHeight()
/*  963:     */   {
/*  964:1288 */     return getWindow().jsxGet_screen().jsxGet_height();
/*  965:     */   }
/*  966:     */   
/*  967:     */   public boolean getJavaEnabled()
/*  968:     */   {
/*  969:1297 */     return getWindow().jsxGet_navigator().jsxFunction_javaEnabled();
/*  970:     */   }
/*  971:     */   
/*  972:     */   public String getPlatform()
/*  973:     */   {
/*  974:1306 */     return getWindow().jsxGet_navigator().jsxGet_platform();
/*  975:     */   }
/*  976:     */   
/*  977:     */   public String getSystemLanguage()
/*  978:     */   {
/*  979:1315 */     return getWindow().jsxGet_navigator().jsxGet_systemLanguage();
/*  980:     */   }
/*  981:     */   
/*  982:     */   public String getUserLanguage()
/*  983:     */   {
/*  984:1324 */     return getWindow().jsxGet_navigator().jsxGet_userLanguage();
/*  985:     */   }
/*  986:     */   
/*  987:     */   public int getWidth()
/*  988:     */   {
/*  989:1333 */     return getWindow().jsxGet_screen().jsxGet_width();
/*  990:     */   }
/*  991:     */   
/*  992:     */   public void addComponentRequest(String id, String idType, String minVersion)
/*  993:     */   {
/*  994:1345 */     if (LOG.isDebugEnabled()) {
/*  995:1346 */       LOG.debug("Call to addComponentRequest(" + id + ", " + idType + ", " + minVersion + ") ignored.");
/*  996:     */     }
/*  997:     */   }
/*  998:     */   
/*  999:     */   public void clearComponentRequest()
/* 1000:     */   {
/* 1001:1356 */     if (LOG.isDebugEnabled()) {
/* 1002:1357 */       LOG.debug("Call to clearComponentRequest() ignored.");
/* 1003:     */     }
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public int compareVersions(String v1, String v2)
/* 1007:     */   {
/* 1008:1369 */     int i = v1.compareTo(v2);
/* 1009:1370 */     if (i == 0) {
/* 1010:1370 */       return 0;
/* 1011:     */     }
/* 1012:1371 */     if (i < 0) {
/* 1013:1371 */       return -1;
/* 1014:     */     }
/* 1015:1372 */     return 1;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public boolean doComponentRequest()
/* 1019:     */   {
/* 1020:1381 */     return false;
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public String getComponentVersion(String id, String idType)
/* 1024:     */   {
/* 1025:1391 */     if ("{E5D12C4E-7B4F-11D3-B5C9-0050045C3C96}".equals(id)) {
/* 1026:1393 */       return "";
/* 1027:     */     }
/* 1028:1396 */     return "1.0";
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public boolean isComponentInstalled(String id, String idType, String minVersion)
/* 1032:     */   {
/* 1033:1407 */     return false;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void startDownload(String uri, Function callback)
/* 1037:     */     throws MalformedURLException
/* 1038:     */   {
/* 1039:1420 */     HtmlPage page = (HtmlPage)getWindow().getWebWindow().getEnclosedPage();
/* 1040:1421 */     URL url = page.getFullyQualifiedUrl(uri);
/* 1041:1422 */     if (!page.getWebResponse().getWebRequest().getUrl().getHost().equals(url.getHost())) {
/* 1042:1423 */       throw Context.reportRuntimeError("Not authorized url: " + url);
/* 1043:     */     }
/* 1044:1425 */     JavaScriptJob job = new DownloadBehaviorJob(url, callback, null);
/* 1045:1426 */     page.getEnclosingWindow().getJobManager().addJob(job, page);
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   private final class DownloadBehaviorJob
/* 1049:     */     extends JavaScriptJob
/* 1050:     */   {
/* 1051:     */     private final URL url_;
/* 1052:     */     private final Function callback_;
/* 1053:     */     
/* 1054:     */     private DownloadBehaviorJob(URL url, Function callback)
/* 1055:     */     {
/* 1056:1447 */       this.url_ = url;
/* 1057:1448 */       this.callback_ = callback;
/* 1058:     */     }
/* 1059:     */     
/* 1060:     */     public void run()
/* 1061:     */     {
/* 1062:1455 */       WebClient client = HTMLElement.this.getWindow().getWebWindow().getWebClient();
/* 1063:1456 */       final Scriptable scope = this.callback_.getParentScope();
/* 1064:1457 */       WebRequest request = new WebRequest(this.url_);
/* 1065:     */       try
/* 1066:     */       {
/* 1067:1459 */         WebResponse webResponse = client.loadWebResponse(request);
/* 1068:1460 */         String content = webResponse.getContentAsString();
/* 1069:1461 */         if (HTMLElement.LOG.isDebugEnabled()) {
/* 1070:1462 */           HTMLElement.LOG.debug("Downloaded content: " + org.apache.commons.lang.StringUtils.abbreviate(content, 512));
/* 1071:     */         }
/* 1072:1464 */         final Object[] args = { content };
/* 1073:1465 */         ContextAction action = new ContextAction()
/* 1074:     */         {
/* 1075:     */           public Object run(Context cx)
/* 1076:     */           {
/* 1077:1467 */             HTMLElement.DownloadBehaviorJob.this.callback_.call(cx, scope, scope, args);
/* 1078:1468 */             return null;
/* 1079:     */           }
/* 1080:1470 */         };
/* 1081:1471 */         ContextFactory cf = client.getJavaScriptEngine().getContextFactory();
/* 1082:1472 */         cf.call(action);
/* 1083:     */       }
/* 1084:     */       catch (IOException e)
/* 1085:     */       {
/* 1086:1475 */         HTMLElement.LOG.error("Behavior #default#download: Cannot download " + this.url_, e);
/* 1087:     */       }
/* 1088:     */     }
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public boolean isHomePage(String url)
/* 1092:     */   {
/* 1093:     */     try
/* 1094:     */     {
/* 1095:1494 */       URL newUrl = new URL(url);
/* 1096:1495 */       URL currentUrl = getDomNodeOrDie().getPage().getWebResponse().getWebRequest().getUrl();
/* 1097:1496 */       String home = getDomNodeOrDie().getPage().getEnclosingWindow().getWebClient().getHomePage();
/* 1098:1497 */       boolean sameDomains = newUrl.getHost().equalsIgnoreCase(currentUrl.getHost());
/* 1099:1498 */       boolean isHomePage = (home != null) && (home.equals(url));
/* 1100:1499 */       return (sameDomains) && (isHomePage);
/* 1101:     */     }
/* 1102:     */     catch (MalformedURLException e) {}
/* 1103:1502 */     return false;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public void setHomePage(String url)
/* 1107:     */   {
/* 1108:1512 */     getDomNodeOrDie().getPage().getEnclosingWindow().getWebClient().setHomePage(url);
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public void navigateHomePage()
/* 1112:     */     throws IOException
/* 1113:     */   {
/* 1114:1521 */     WebClient webClient = getDomNodeOrDie().getPage().getEnclosingWindow().getWebClient();
/* 1115:1522 */     webClient.getPage(webClient.getHomePage());
/* 1116:     */   }
/* 1117:     */   
/* 1118:     */   public HTMLCollection jsxGet_children()
/* 1119:     */   {
/* 1120:1533 */     final HtmlElement node = getDomNodeOrDie();
/* 1121:1534 */     HTMLCollection collection = new HTMLCollection(node, false, "HTMLElement.children")
/* 1122:     */     {
/* 1123:     */       protected List<Object> computeElements()
/* 1124:     */       {
/* 1125:1536 */         return new ArrayList(node.getChildNodes());
/* 1126:     */       }
/* 1127:1538 */     };
/* 1128:1539 */     return collection;
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public int jsxGet_offsetHeight()
/* 1132:     */   {
/* 1133:1551 */     MouseEvent event = MouseEvent.getCurrentMouseEvent();
/* 1134:1552 */     if (isAncestorOfEventTarget(event)) {
/* 1135:1554 */       return event.jsxGet_clientY() - getPosY() + 50;
/* 1136:     */     }
/* 1137:1556 */     return jsxGet_currentStyle().getCalculatedHeight(true, true);
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public int jsxGet_offsetWidth()
/* 1141:     */   {
/* 1142:1568 */     MouseEvent event = MouseEvent.getCurrentMouseEvent();
/* 1143:1569 */     if (isAncestorOfEventTarget(event)) {
/* 1144:1571 */       return event.jsxGet_clientX() - getPosX() + 50;
/* 1145:     */     }
/* 1146:1573 */     return jsxGet_currentStyle().getCalculatedWidth(true, true);
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   protected boolean isAncestorOfEventTarget(MouseEvent event)
/* 1150:     */   {
/* 1151:1582 */     if (event == null) {
/* 1152:1583 */       return false;
/* 1153:     */     }
/* 1154:1585 */     if (!(event.jsxGet_srcElement() instanceof HTMLElement)) {
/* 1155:1586 */       return false;
/* 1156:     */     }
/* 1157:1588 */     HTMLElement srcElement = (HTMLElement)event.jsxGet_srcElement();
/* 1158:1589 */     return getDomNodeOrDie().isAncestorOf(srcElement.getDomNodeOrDie());
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   public int getPosX()
/* 1162:     */   {
/* 1163:1597 */     int cumulativeOffset = 0;
/* 1164:1598 */     HTMLElement element = this;
/* 1165:1599 */     while (element != null)
/* 1166:     */     {
/* 1167:1600 */       cumulativeOffset += element.jsxGet_offsetLeft();
/* 1168:1601 */       if (element != this) {
/* 1169:1602 */         cumulativeOffset += element.jsxGet_currentStyle().getBorderLeft();
/* 1170:     */       }
/* 1171:1604 */       element = element.getOffsetParent();
/* 1172:     */     }
/* 1173:1606 */     return cumulativeOffset;
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   private HTMLElement getOffsetParent()
/* 1177:     */   {
/* 1178:1614 */     Object offsetParent = jsxGet_offsetParent();
/* 1179:1615 */     if ((offsetParent instanceof HTMLElement)) {
/* 1180:1616 */       return (HTMLElement)offsetParent;
/* 1181:     */     }
/* 1182:1618 */     return null;
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public int getPosY()
/* 1186:     */   {
/* 1187:1626 */     int cumulativeOffset = 0;
/* 1188:1627 */     HTMLElement element = this;
/* 1189:1628 */     while (element != null)
/* 1190:     */     {
/* 1191:1629 */       cumulativeOffset += element.jsxGet_offsetTop();
/* 1192:1630 */       if (element != this) {
/* 1193:1631 */         cumulativeOffset += element.jsxGet_currentStyle().getBorderTop();
/* 1194:     */       }
/* 1195:1633 */       element = element.getOffsetParent();
/* 1196:     */     }
/* 1197:1635 */     return cumulativeOffset;
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public int jsxGet_offsetLeft()
/* 1201:     */   {
/* 1202:1648 */     if ((this instanceof HTMLBodyElement)) {
/* 1203:1649 */       return 0;
/* 1204:     */     }
/* 1205:1652 */     int left = 0;
/* 1206:1653 */     HTMLElement offsetParent = getOffsetParent();
/* 1207:     */     
/* 1208:     */ 
/* 1209:1656 */     DomNode node = getDomNodeOrDie();
/* 1210:1657 */     HTMLElement element = (HTMLElement)node.getScriptObject();
/* 1211:1658 */     left += element.jsxGet_currentStyle().getLeft(true, false, false);
/* 1212:     */     
/* 1213:     */ 
/* 1214:1661 */     String position = element.jsxGet_currentStyle().getPositionWithInheritance();
/* 1215:1662 */     if ("absolute".equals(position)) {
/* 1216:1663 */       return left;
/* 1217:     */     }
/* 1218:1667 */     node = node.getParentNode();
/* 1219:1668 */     while ((node != null) && (node.getScriptObject() != offsetParent))
/* 1220:     */     {
/* 1221:1669 */       if ((node.getScriptObject() instanceof HTMLElement))
/* 1222:     */       {
/* 1223:1670 */         element = (HTMLElement)node.getScriptObject();
/* 1224:1671 */         left += element.jsxGet_currentStyle().getLeft(true, true, true);
/* 1225:     */       }
/* 1226:1673 */       node = node.getParentNode();
/* 1227:     */     }
/* 1228:1676 */     if (offsetParent != null)
/* 1229:     */     {
/* 1230:1677 */       left += offsetParent.jsxGet_currentStyle().getMarginLeft();
/* 1231:1678 */       left += offsetParent.jsxGet_currentStyle().getPaddingLeft();
/* 1232:     */     }
/* 1233:1681 */     return left;
/* 1234:     */   }
/* 1235:     */   
/* 1236:     */   public int jsxGet_offsetTop()
/* 1237:     */   {
/* 1238:1694 */     if ((this instanceof HTMLBodyElement)) {
/* 1239:1695 */       return 0;
/* 1240:     */     }
/* 1241:1698 */     int top = 0;
/* 1242:1699 */     HTMLElement offsetParent = getOffsetParent();
/* 1243:     */     
/* 1244:     */ 
/* 1245:1702 */     DomNode node = getDomNodeOrDie();
/* 1246:1703 */     HTMLElement element = (HTMLElement)node.getScriptObject();
/* 1247:1704 */     top += element.jsxGet_currentStyle().getTop(true, false, false);
/* 1248:     */     
/* 1249:     */ 
/* 1250:1707 */     String position = element.jsxGet_currentStyle().getPositionWithInheritance();
/* 1251:1708 */     if ("absolute".equals(position)) {
/* 1252:1709 */       return top;
/* 1253:     */     }
/* 1254:1713 */     node = node.getParentNode();
/* 1255:1714 */     while ((node != null) && (node.getScriptObject() != offsetParent))
/* 1256:     */     {
/* 1257:1715 */       if ((node.getScriptObject() instanceof HTMLElement))
/* 1258:     */       {
/* 1259:1716 */         element = (HTMLElement)node.getScriptObject();
/* 1260:1717 */         top += element.jsxGet_currentStyle().getTop(false, true, true);
/* 1261:     */       }
/* 1262:1719 */       node = node.getParentNode();
/* 1263:     */     }
/* 1264:1722 */     if (offsetParent != null)
/* 1265:     */     {
/* 1266:1723 */       HTMLElement thiz = (HTMLElement)getDomNodeOrDie().getScriptObject();
/* 1267:1724 */       boolean thisElementHasTopMargin = thiz.jsxGet_currentStyle().getMarginTop() != 0;
/* 1268:1725 */       if (!thisElementHasTopMargin) {
/* 1269:1726 */         top += offsetParent.jsxGet_currentStyle().getMarginTop();
/* 1270:     */       }
/* 1271:1728 */       top += offsetParent.jsxGet_currentStyle().getPaddingTop();
/* 1272:     */     }
/* 1273:1731 */     return top;
/* 1274:     */   }
/* 1275:     */   
/* 1276:     */   public Object jsxGet_offsetParent()
/* 1277:     */   {
/* 1278:1747 */     DomNode currentElement = getDomNodeOrDie();
/* 1279:1749 */     if (currentElement.getParentNode() == null)
/* 1280:     */     {
/* 1281:1750 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_OFFSET_PARENT_THROWS_NOT_ATTACHED)) {
/* 1282:1751 */         throw Context.reportRuntimeError("Unspecified error");
/* 1283:     */       }
/* 1284:1753 */       return null;
/* 1285:     */     }
/* 1286:1756 */     Object offsetParent = null;
/* 1287:1757 */     HTMLElement htmlElement = (HTMLElement)currentElement.getScriptObject();
/* 1288:1758 */     ComputedCSSStyleDeclaration style = htmlElement.jsxGet_currentStyle();
/* 1289:1759 */     String position = style.getPositionWithInheritance();
/* 1290:1760 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_72);
/* 1291:1761 */     boolean staticPos = "static".equals(position);
/* 1292:1762 */     boolean fixedPos = "fixed".equals(position);
/* 1293:1763 */     boolean useTables = ((ie) && ((staticPos) || (fixedPos))) || ((!ie) && (staticPos));
/* 1294:1765 */     while (currentElement != null)
/* 1295:     */     {
/* 1296:1767 */       DomNode parentNode = currentElement.getParentNode();
/* 1297:1768 */       if (((parentNode instanceof HtmlBody)) || ((useTables) && ((parentNode instanceof HtmlTableDataCell))) || ((useTables) && ((parentNode instanceof HtmlTable))))
/* 1298:     */       {
/* 1299:1771 */         offsetParent = parentNode.getScriptObject();
/* 1300:1772 */         break;
/* 1301:     */       }
/* 1302:1775 */       if ((parentNode != null) && ((parentNode.getScriptObject() instanceof HTMLElement)))
/* 1303:     */       {
/* 1304:1776 */         HTMLElement parentElement = (HTMLElement)parentNode.getScriptObject();
/* 1305:1777 */         ComputedCSSStyleDeclaration parentStyle = parentElement.jsxGet_currentStyle();
/* 1306:1778 */         String parentPosition = parentStyle.getPositionWithInheritance();
/* 1307:1779 */         boolean parentIsStatic = "static".equals(parentPosition);
/* 1308:1780 */         boolean parentIsFixed = "fixed".equals(parentPosition);
/* 1309:1781 */         if (((ie) && (!parentIsStatic) && (!parentIsFixed)) || ((!ie) && (!parentIsStatic)))
/* 1310:     */         {
/* 1311:1782 */           offsetParent = parentNode.getScriptObject();
/* 1312:1783 */           break;
/* 1313:     */         }
/* 1314:     */       }
/* 1315:1787 */       currentElement = currentElement.getParentNode();
/* 1316:     */     }
/* 1317:1790 */     return offsetParent;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public String toString()
/* 1321:     */   {
/* 1322:1798 */     return "HTMLElement for " + getDomNodeOrNull();
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public int jsxGet_scrollTop()
/* 1326:     */   {
/* 1327:1809 */     if (this.scrollTop_ < 0) {
/* 1328:1810 */       this.scrollTop_ = 0;
/* 1329:1812 */     } else if ((this.scrollTop_ > 0) && 
/* 1330:1813 */       (!jsxGet_currentStyle().isScrollable(false))) {
/* 1331:1814 */       this.scrollTop_ = 0;
/* 1332:     */     }
/* 1333:1817 */     return this.scrollTop_;
/* 1334:     */   }
/* 1335:     */   
/* 1336:     */   public void jsxSet_scrollTop(int scroll)
/* 1337:     */   {
/* 1338:1825 */     this.scrollTop_ = scroll;
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   public int jsxGet_scrollLeft()
/* 1342:     */   {
/* 1343:1836 */     if (this.scrollLeft_ < 0) {
/* 1344:1837 */       this.scrollLeft_ = 0;
/* 1345:1839 */     } else if ((this.scrollLeft_ > 0) && 
/* 1346:1840 */       (!jsxGet_currentStyle().isScrollable(true))) {
/* 1347:1841 */       this.scrollLeft_ = 0;
/* 1348:     */     }
/* 1349:1844 */     return this.scrollLeft_;
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   public void jsxSet_scrollLeft(int scroll)
/* 1353:     */   {
/* 1354:1852 */     this.scrollLeft_ = scroll;
/* 1355:     */   }
/* 1356:     */   
/* 1357:     */   public int jsxGet_scrollHeight()
/* 1358:     */   {
/* 1359:1861 */     return 10;
/* 1360:     */   }
/* 1361:     */   
/* 1362:     */   public int jsxGet_scrollWidth()
/* 1363:     */   {
/* 1364:1870 */     return 10;
/* 1365:     */   }
/* 1366:     */   
/* 1367:     */   public String jsxGet_scopeName()
/* 1368:     */   {
/* 1369:1879 */     String prefix = getDomNodeOrDie().getPrefix();
/* 1370:1880 */     return prefix != null ? prefix : "HTML";
/* 1371:     */   }
/* 1372:     */   
/* 1373:     */   public String jsxGet_tagUrn()
/* 1374:     */   {
/* 1375:1889 */     String urn = getDomNodeOrDie().getNamespaceURI();
/* 1376:1890 */     return urn != null ? urn : "";
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   public void jsxSet_tagUrn(String tagUrn)
/* 1380:     */   {
/* 1381:1899 */     throw Context.reportRuntimeError("Error trying to set tagUrn to '" + tagUrn + "'.");
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   public HTMLElement jsxGet_parentElement()
/* 1385:     */   {
/* 1386:1908 */     com.gargoylesoftware.htmlunit.javascript.host.Node parent = getParent();
/* 1387:1909 */     if (!(parent instanceof HTMLElement)) {
/* 1388:1910 */       return null;
/* 1389:     */     }
/* 1390:1912 */     return (HTMLElement)parent;
/* 1391:     */   }
/* 1392:     */   
/* 1393:     */   public HTMLElement getParentHTMLElement()
/* 1394:     */   {
/* 1395:1922 */     com.gargoylesoftware.htmlunit.javascript.host.Node parent = getParent();
/* 1396:1923 */     while ((parent != null) && (!(parent instanceof HTMLElement))) {
/* 1397:1924 */       parent = parent.getParent();
/* 1398:     */     }
/* 1399:1926 */     return (HTMLElement)parent;
/* 1400:     */   }
/* 1401:     */   
/* 1402:     */   public void jsxFunction_scrollIntoView() {}
/* 1403:     */   
/* 1404:     */   public TextRectangle jsxFunction_getBoundingClientRect()
/* 1405:     */   {
/* 1406:1942 */     TextRectangle textRectangle = new TextRectangle(0, getPosX() + jsxGet_clientLeft(), 0, getPosY() + jsxGet_clientTop());
/* 1407:     */     
/* 1408:1944 */     textRectangle.setParentScope(getWindow());
/* 1409:1945 */     textRectangle.setPrototype(getPrototype(textRectangle.getClass()));
/* 1410:1946 */     return textRectangle;
/* 1411:     */   }
/* 1412:     */   
/* 1413:     */   public Object jsxFunction_getClientRects()
/* 1414:     */   {
/* 1415:1955 */     return new NativeArray(0L);
/* 1416:     */   }
/* 1417:     */   
/* 1418:     */   public void jsxFunction_setExpression(String propertyName, String expression, String language) {}
/* 1419:     */   
/* 1420:     */   public boolean jsxFunction_removeExpression(String propertyName)
/* 1421:     */   {
/* 1422:1978 */     return true;
/* 1423:     */   }
/* 1424:     */   
/* 1425:     */   public String jsxGet_uniqueID()
/* 1426:     */   {
/* 1427:1987 */     if (this.uniqueID_ == null) {
/* 1428:1988 */       this.uniqueID_ = ("ms__id" + UniqueID_Counter_++);
/* 1429:     */     }
/* 1430:1990 */     return this.uniqueID_;
/* 1431:     */   }
/* 1432:     */   
/* 1433:     */   public boolean jsxFunction_dispatchEvent(Event event)
/* 1434:     */   {
/* 1435:2003 */     event.setTarget(this);
/* 1436:2004 */     HtmlElement element = getDomNodeOrDie();
/* 1437:2005 */     ScriptResult result = null;
/* 1438:2006 */     if (event.jsxGet_type().equals("click")) {
/* 1439:     */       try
/* 1440:     */       {
/* 1441:2008 */         element.click(event);
/* 1442:     */       }
/* 1443:     */       catch (IOException e)
/* 1444:     */       {
/* 1445:2011 */         throw Context.reportRuntimeError("Error calling click(): " + e.getMessage());
/* 1446:     */       }
/* 1447:     */     } else {
/* 1448:2015 */       result = fireEvent(event);
/* 1449:     */     }
/* 1450:2017 */     return !event.isAborted(result);
/* 1451:     */   }
/* 1452:     */   
/* 1453:     */   public final HtmlElement getDomNodeOrDie()
/* 1454:     */   {
/* 1455:2025 */     return (HtmlElement)super.getDomNodeOrDie();
/* 1456:     */   }
/* 1457:     */   
/* 1458:     */   public HtmlElement getDomNodeOrNull()
/* 1459:     */   {
/* 1460:2034 */     return (HtmlElement)super.getDomNodeOrNull();
/* 1461:     */   }
/* 1462:     */   
/* 1463:     */   public void jsxFunction_blur()
/* 1464:     */   {
/* 1465:2041 */     getDomNodeOrDie().blur();
/* 1466:     */   }
/* 1467:     */   
/* 1468:     */   public Object jsxFunction_createTextRange()
/* 1469:     */   {
/* 1470:2049 */     TextRange range = new TextRange(this);
/* 1471:2050 */     range.setParentScope(getParentScope());
/* 1472:2051 */     range.setPrototype(getPrototype(range.getClass()));
/* 1473:2052 */     return range;
/* 1474:     */   }
/* 1475:     */   
/* 1476:     */   public boolean jsxFunction_contains(HTMLElement element)
/* 1477:     */   {
/* 1478:2061 */     for (HTMLElement parent = element; parent != null; parent = parent.jsxGet_parentElement()) {
/* 1479:2062 */       if (this == parent) {
/* 1480:2063 */         return true;
/* 1481:     */       }
/* 1482:     */     }
/* 1483:2066 */     return false;
/* 1484:     */   }
/* 1485:     */   
/* 1486:     */   public void jsxFunction_focus()
/* 1487:     */   {
/* 1488:2073 */     getDomNodeOrDie().focus();
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public void jsxFunction_setActive()
/* 1492:     */   {
/* 1493:2081 */     Window window = getWindow();
/* 1494:2082 */     HTMLDocument document = (HTMLDocument)window.getDocument();
/* 1495:2083 */     document.setActiveElement(this);
/* 1496:2084 */     if (window.getWebWindow() == window.getWebWindow().getWebClient().getCurrentWindow())
/* 1497:     */     {
/* 1498:2085 */       HtmlElement element = getDomNodeOrDie();
/* 1499:2086 */       ((HtmlPage)element.getPage()).setFocusedElement(element);
/* 1500:     */     }
/* 1501:     */   }
/* 1502:     */   
/* 1503:     */   public StaticNodeList jsxFunction_querySelectorAll(String selectors)
/* 1504:     */   {
/* 1505:2098 */     List<com.gargoylesoftware.htmlunit.javascript.host.Node> nodes = new ArrayList();
/* 1506:2099 */     for (DomNode domNode : getDomNodeOrDie().querySelectorAll(selectors)) {
/* 1507:2100 */       nodes.add((com.gargoylesoftware.htmlunit.javascript.host.Node)domNode.getScriptObject());
/* 1508:     */     }
/* 1509:2102 */     return new StaticNodeList(nodes, this);
/* 1510:     */   }
/* 1511:     */   
/* 1512:     */   public com.gargoylesoftware.htmlunit.javascript.host.Node jsxFunction_querySelector(String selectors)
/* 1513:     */   {
/* 1514:2111 */     DomNode node = getDomNodeOrDie().querySelector(selectors);
/* 1515:2112 */     if (node != null) {
/* 1516:2113 */       return (com.gargoylesoftware.htmlunit.javascript.host.Node)node.getScriptObject();
/* 1517:     */     }
/* 1518:2115 */     return null;
/* 1519:     */   }
/* 1520:     */   
/* 1521:     */   public Object get(String name, Scriptable start)
/* 1522:     */   {
/* 1523:2123 */     Object response = super.get(name, start);
/* 1524:2127 */     if (((response instanceof FunctionObject)) && (("querySelectorAll".equals(name)) || ("querySelector".equals(name))) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.QUERYSELECTORALL_NOT_IN_QUIRKS)))
/* 1525:     */     {
/* 1526:2131 */       DomNode domNode = getDomNodeOrNull();
/* 1527:2132 */       if (null == domNode) {
/* 1528:2133 */         return response;
/* 1529:     */       }
/* 1530:2135 */       SgmlPage sgmlPage = domNode.getPage();
/* 1531:2136 */       if (((sgmlPage instanceof HtmlPage)) && (((HtmlPage)sgmlPage).isQuirksMode())) {
/* 1532:2137 */         return NOT_FOUND;
/* 1533:     */       }
/* 1534:     */     }
/* 1535:2141 */     return response;
/* 1536:     */   }
/* 1537:     */   
/* 1538:     */   public String jsxGet_nodeName()
/* 1539:     */   {
/* 1540:2149 */     DomNode domNode = getDomNodeOrDie();
/* 1541:2150 */     String nodeName = domNode.getNodeName();
/* 1542:2151 */     if ((domNode.getPage() instanceof HtmlPage)) {
/* 1543:2152 */       nodeName = nodeName.toUpperCase();
/* 1544:     */     }
/* 1545:2154 */     return nodeName;
/* 1546:     */   }
/* 1547:     */   
/* 1548:     */   public String jsxGet_prefix()
/* 1549:     */   {
/* 1550:2162 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_73)) {
/* 1551:2163 */       return "";
/* 1552:     */     }
/* 1553:2165 */     return null;
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   public Object jsxGet_filters()
/* 1557:     */   {
/* 1558:2174 */     return this;
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   public void jsxFunction_click()
/* 1562:     */     throws IOException
/* 1563:     */   {
/* 1564:2182 */     getDomNodeOrDie().click();
/* 1565:     */   }
/* 1566:     */   
/* 1567:     */   public boolean jsxGet_spellcheck()
/* 1568:     */   {
/* 1569:2190 */     return Context.toBoolean(getDomNodeOrDie().getAttribute("spellcheck"));
/* 1570:     */   }
/* 1571:     */   
/* 1572:     */   public void jsxSet_spellcheck(boolean spellcheck)
/* 1573:     */   {
/* 1574:2198 */     getDomNodeOrDie().setAttribute("spellcheck", Boolean.toString(spellcheck));
/* 1575:     */   }
/* 1576:     */   
/* 1577:     */   public String jsxGet_lang()
/* 1578:     */   {
/* 1579:2206 */     return getDomNodeOrDie().getAttribute("lang");
/* 1580:     */   }
/* 1581:     */   
/* 1582:     */   public void jsxSet_lang(String lang)
/* 1583:     */   {
/* 1584:2214 */     getDomNodeOrDie().setAttribute("lang", lang);
/* 1585:     */   }
/* 1586:     */   
/* 1587:     */   public String jsxGet_language()
/* 1588:     */   {
/* 1589:2222 */     return getDomNodeOrDie().getAttribute("language");
/* 1590:     */   }
/* 1591:     */   
/* 1592:     */   public void jsxSet_language(String language)
/* 1593:     */   {
/* 1594:2230 */     getDomNodeOrDie().setAttribute("language", language);
/* 1595:     */   }
/* 1596:     */   
/* 1597:     */   public String jsxGet_dir()
/* 1598:     */   {
/* 1599:2238 */     return getDomNodeOrDie().getAttribute("dir");
/* 1600:     */   }
/* 1601:     */   
/* 1602:     */   public void jsxSet_dir(String dir)
/* 1603:     */   {
/* 1604:2246 */     getDomNodeOrDie().setAttribute("dir", dir);
/* 1605:     */   }
/* 1606:     */   
/* 1607:     */   public int jsxGet_tabIndex()
/* 1608:     */   {
/* 1609:2254 */     return (int)Context.toNumber(getDomNodeOrDie().getAttribute("tabindex"));
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   public void jsxSet_tabIndex(int tabIndex)
/* 1613:     */   {
/* 1614:2262 */     getDomNodeOrDie().setAttribute("tabindex", Integer.toString(tabIndex));
/* 1615:     */   }
/* 1616:     */   
/* 1617:     */   public void jsxFunction_doScroll(String scrollAction)
/* 1618:     */   {
/* 1619:2270 */     if (((HtmlPage)getDomNodeOrDie().getPage()).isBeingParsed()) {
/* 1620:2271 */       throw Context.reportRuntimeError("The data necessary to complete this operation is not yet available.");
/* 1621:     */     }
/* 1622:     */   }
/* 1623:     */   
/* 1624:     */   public String jsxGet_accessKey()
/* 1625:     */   {
/* 1626:2281 */     return getDomNodeOrDie().getAttribute("accesskey");
/* 1627:     */   }
/* 1628:     */   
/* 1629:     */   public void jsxSet_accessKey(String accessKey)
/* 1630:     */   {
/* 1631:2289 */     getDomNodeOrDie().setAttribute("accesskey", accessKey);
/* 1632:     */   }
/* 1633:     */   
/* 1634:     */   protected String getWidthOrHeight(String attributeName, Boolean returnNegativeValues)
/* 1635:     */   {
/* 1636:2301 */     String s = getDomNodeOrDie().getAttribute(attributeName);
/* 1637:2302 */     if (!PERCENT_VALUE.matcher(s).matches()) {
/* 1638:     */       try
/* 1639:     */       {
/* 1640:2304 */         Float f = Float.valueOf(s);
/* 1641:2305 */         int i = f.intValue();
/* 1642:2306 */         if (i < 0)
/* 1643:     */         {
/* 1644:2307 */           if (returnNegativeValues == null) {
/* 1645:2308 */             s = "0";
/* 1646:2310 */           } else if (!returnNegativeValues.booleanValue()) {
/* 1647:2311 */             s = "";
/* 1648:     */           } else {
/* 1649:2314 */             s = Integer.toString(i);
/* 1650:     */           }
/* 1651:     */         }
/* 1652:     */         else {
/* 1653:2318 */           s = Integer.toString(i);
/* 1654:     */         }
/* 1655:     */       }
/* 1656:     */       catch (NumberFormatException e)
/* 1657:     */       {
/* 1658:2322 */         if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_74)) {
/* 1659:2323 */           s = "";
/* 1660:     */         }
/* 1661:     */       }
/* 1662:     */     }
/* 1663:2327 */     return s;
/* 1664:     */   }
/* 1665:     */   
/* 1666:     */   protected void setWidthOrHeight(String attributeName, String value, Boolean allowNegativeValues)
/* 1667:     */   {
/* 1668:2339 */     if (value.endsWith("px")) {
/* 1669:2340 */       value = value.substring(0, value.length() - 2);
/* 1670:     */     }
/* 1671:2342 */     if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_75)) && (value.length() > 0))
/* 1672:     */     {
/* 1673:2343 */       boolean error = false;
/* 1674:2344 */       if (!PERCENT_VALUE.matcher(value).matches()) {
/* 1675:     */         try
/* 1676:     */         {
/* 1677:2346 */           Float f = Float.valueOf(value);
/* 1678:2347 */           int i = f.intValue();
/* 1679:2348 */           if (i < 0) {
/* 1680:2349 */             if (allowNegativeValues == null) {
/* 1681:2350 */               value = "0";
/* 1682:2352 */             } else if (!allowNegativeValues.booleanValue()) {
/* 1683:2353 */               error = true;
/* 1684:     */             }
/* 1685:     */           }
/* 1686:     */         }
/* 1687:     */         catch (NumberFormatException e)
/* 1688:     */         {
/* 1689:2358 */           error = true;
/* 1690:     */         }
/* 1691:     */       }
/* 1692:2361 */       if (error)
/* 1693:     */       {
/* 1694:2362 */         Exception e = new Exception("Cannot set the width property to invalid value: " + value);
/* 1695:2363 */         Context.throwAsScriptRuntimeEx(e);
/* 1696:     */       }
/* 1697:     */     }
/* 1698:2366 */     getDomNodeOrDie().setAttribute(attributeName, value);
/* 1699:     */   }
/* 1700:     */   
/* 1701:     */   protected void setColorAttribute(String name, String value)
/* 1702:     */   {
/* 1703:     */     String s;
/* 1704:     */     String s;
/* 1705:2376 */     if (com.gargoylesoftware.htmlunit.util.StringUtils.isColorHexadecimal(value)) {
/* 1706:2377 */       s = value;
/* 1707:     */     } else {
/* 1708:2380 */       s = "#000000";
/* 1709:     */     }
/* 1710:2382 */     getDomNodeOrDie().setAttribute(name, s);
/* 1711:     */   }
/* 1712:     */   
/* 1713:     */   protected String getAlign(boolean returnInvalidValues)
/* 1714:     */   {
/* 1715:2392 */     String align = getDomNodeOrDie().getAttribute("align");
/* 1716:2393 */     if (("center".equals(align)) || ("justify".equals(align)) || ("left".equals(align)) || ("right".equals(align)) || (returnInvalidValues)) {
/* 1717:2398 */       return align;
/* 1718:     */     }
/* 1719:2400 */     return "";
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   protected void setAlign(String align, boolean ignoreIfNoError)
/* 1723:     */   {
/* 1724:2410 */     align = align.toLowerCase();
/* 1725:2411 */     boolean ff = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_168);
/* 1726:2412 */     if ((ff) || ("center".equals(align)) || ("justify".equals(align)) || ("left".equals(align)) || ("right".equals(align)))
/* 1727:     */     {
/* 1728:2417 */       if (!ignoreIfNoError) {
/* 1729:2418 */         getDomNodeOrDie().setAttribute("align", align);
/* 1730:     */       }
/* 1731:     */     }
/* 1732:     */     else {
/* 1733:2422 */       throw Context.reportRuntimeError("Cannot set the align property to invalid value: " + align);
/* 1734:     */     }
/* 1735:     */   }
/* 1736:     */   
/* 1737:     */   protected String getVAlign(String[] valid, String defaultValue)
/* 1738:     */   {
/* 1739:2433 */     String valign = getDomNodeOrDie().getAttribute("valign");
/* 1740:2434 */     if ((valid == null) || (ArrayUtils.contains(valid, valign))) {
/* 1741:2435 */       return valign;
/* 1742:     */     }
/* 1743:2437 */     return defaultValue;
/* 1744:     */   }
/* 1745:     */   
/* 1746:     */   protected void setVAlign(Object vAlign, String[] valid)
/* 1747:     */   {
/* 1748:2446 */     String s = Context.toString(vAlign).toLowerCase();
/* 1749:2447 */     if ((valid == null) || (ArrayUtils.contains(valid, s))) {
/* 1750:2448 */       getDomNodeOrDie().setAttribute("valign", s);
/* 1751:     */     } else {
/* 1752:2451 */       throw Context.reportRuntimeError("Cannot set the vAlign property to invalid value: " + vAlign);
/* 1753:     */     }
/* 1754:     */   }
/* 1755:     */   
/* 1756:     */   protected String getCh()
/* 1757:     */   {
/* 1758:2460 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_76);
/* 1759:2461 */     if (ie) {
/* 1760:2462 */       return this.ch_;
/* 1761:     */     }
/* 1762:2464 */     String ch = getDomNodeOrDie().getAttribute("char");
/* 1763:2465 */     if (ch == DomElement.ATTRIBUTE_NOT_DEFINED) {
/* 1764:2466 */       ch = ".";
/* 1765:     */     }
/* 1766:2468 */     return ch;
/* 1767:     */   }
/* 1768:     */   
/* 1769:     */   protected void setCh(String ch)
/* 1770:     */   {
/* 1771:2476 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_77);
/* 1772:2477 */     if (ie) {
/* 1773:2478 */       this.ch_ = ch;
/* 1774:     */     } else {
/* 1775:2481 */       getDomNodeOrDie().setAttribute("char", ch);
/* 1776:     */     }
/* 1777:     */   }
/* 1778:     */   
/* 1779:     */   protected String getChOff()
/* 1780:     */   {
/* 1781:2490 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_78);
/* 1782:2491 */     if (ie) {
/* 1783:2492 */       return this.chOff_;
/* 1784:     */     }
/* 1785:2494 */     return getDomNodeOrDie().getAttribute("charOff");
/* 1786:     */   }
/* 1787:     */   
/* 1788:     */   protected void setChOff(String chOff)
/* 1789:     */   {
/* 1790:2502 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_79);
/* 1791:2503 */     if (ie)
/* 1792:     */     {
/* 1793:2504 */       this.chOff_ = chOff;
/* 1794:     */     }
/* 1795:     */     else
/* 1796:     */     {
/* 1797:     */       try
/* 1798:     */       {
/* 1799:2508 */         Float f = Float.valueOf(chOff);
/* 1800:2509 */         if (f.floatValue() < 0.0F) {
/* 1801:2510 */           f = Float.valueOf(0.0F);
/* 1802:     */         }
/* 1803:2512 */         chOff = Integer.toString(f.intValue());
/* 1804:     */       }
/* 1805:     */       catch (NumberFormatException e) {}
/* 1806:2517 */       getDomNodeOrDie().setAttribute("charOff", chOff);
/* 1807:     */     }
/* 1808:     */   }
/* 1809:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement
 * JD-Core Version:    0.7.0.1
 */