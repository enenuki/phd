/*    1:     */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    5:     */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*    6:     */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*    7:     */ import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
/*    8:     */ import com.gargoylesoftware.htmlunit.javascript.host.Text;
/*    9:     */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   10:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLBodyElement;
/*   11:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCanvasElement;
/*   12:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   13:     */ import java.util.Arrays;
/*   14:     */ import java.util.Collections;
/*   15:     */ import java.util.HashMap;
/*   16:     */ import java.util.HashSet;
/*   17:     */ import java.util.Map;
/*   18:     */ import java.util.Set;
/*   19:     */ import java.util.SortedMap;
/*   20:     */ import java.util.TreeMap;
/*   21:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   22:     */ import org.apache.commons.lang.StringUtils;
/*   23:     */ import org.w3c.css.sac.Selector;
/*   24:     */ 
/*   25:     */ public class ComputedCSSStyleDeclaration
/*   26:     */   extends CSSStyleDeclaration
/*   27:     */ {
/*   28:     */   private static final int PIXELS_PER_CHAR = 10;
/*   29:  58 */   private static final Set<String> INHERITABLE_ATTRIBUTES = new HashSet(Arrays.asList(new String[] { "azimuth", "border-collapse", "border-spacing", "caption-side", "color", "cursor", "direction", "elevation", "empty-cells", "font-family", "font-size", "font-style", "font-variant", "font-weight", "font", "letter-spacing", "line-height", "list-style-image", "list-style-position", "list-style-type", "list-style", "orphans", "pitch-range", "pitch", "quotes", "richness", "speak-header", "speak-numeral", "speak-punctuation", "speak", "speech-rate", "stress", "text-align", "text-indent", "text-transform", "visibility", "voice-fFamily", "volume", "white-space", "widows", "word-spacing" }));
/*   30: 105 */   private SortedMap<String, CSSStyleDeclaration.StyleElement> localModifications_ = new TreeMap();
/*   31:     */   private Map<String, String> defaultDisplays_;
/*   32:     */   private Integer width_;
/*   33:     */   private Integer height_;
/*   34:     */   private Integer height2_;
/*   35:     */   private Integer paddingHorizontal_;
/*   36:     */   private Integer paddingVertical_;
/*   37:     */   private Integer borderHorizontal_;
/*   38:     */   private Integer borderVertical_;
/*   39:     */   
/*   40:     */   public ComputedCSSStyleDeclaration() {}
/*   41:     */   
/*   42:     */   public ComputedCSSStyleDeclaration(CSSStyleDeclaration style)
/*   43:     */   {
/*   44: 148 */     super(style.getElement());
/*   45: 149 */     getElement().setDefaults(this);
/*   46:     */   }
/*   47:     */   
/*   48:     */   protected String getStyleAttribute(String name, Map<String, CSSStyleDeclaration.StyleElement> styleMap)
/*   49:     */   {
/*   50: 159 */     String s = super.getStyleAttribute(name, null);
/*   51: 160 */     if ((s.length() == 0) && (isInheritable(name)))
/*   52:     */     {
/*   53: 161 */       HTMLElement parent = getElement().getParentHTMLElement();
/*   54: 162 */       if (parent != null) {
/*   55: 163 */         s = getWindow().jsxFunction_getComputedStyle(parent, null).getStyleAttribute(name, null);
/*   56:     */       }
/*   57:     */     }
/*   58: 166 */     return s;
/*   59:     */   }
/*   60:     */   
/*   61:     */   private boolean isInheritable(String name)
/*   62:     */   {
/*   63: 176 */     return INHERITABLE_ATTRIBUTES.contains(name);
/*   64:     */   }
/*   65:     */   
/*   66:     */   protected void setStyleAttribute(String name, String newValue) {}
/*   67:     */   
/*   68:     */   public void applyStyleFromSelector(org.w3c.dom.css.CSSStyleDeclaration declaration, Selector selector)
/*   69:     */   {
/*   70: 196 */     SelectorSpecificity specificity = new SelectorSpecificity(selector);
/*   71: 197 */     for (int k = 0; k < declaration.getLength(); k++)
/*   72:     */     {
/*   73: 198 */       String name = declaration.item(k);
/*   74: 199 */       String value = declaration.getPropertyValue(name);
/*   75: 200 */       String priority = declaration.getPropertyPriority(name);
/*   76: 201 */       applyLocalStyleAttribute(name, value, priority, specificity);
/*   77:     */     }
/*   78:     */   }
/*   79:     */   
/*   80:     */   private void applyLocalStyleAttribute(String name, String newValue, String priority, SelectorSpecificity specificity)
/*   81:     */   {
/*   82: 207 */     if (!"important".equals(priority))
/*   83:     */     {
/*   84: 208 */       CSSStyleDeclaration.StyleElement existingElement = (CSSStyleDeclaration.StyleElement)this.localModifications_.get(name);
/*   85: 209 */       if (existingElement != null)
/*   86:     */       {
/*   87: 210 */         if ("important".equals(existingElement.getPriority())) {
/*   88: 211 */           return;
/*   89:     */         }
/*   90: 213 */         if (specificity.compareTo(existingElement.getSpecificity()) < 0) {
/*   91: 214 */           return;
/*   92:     */         }
/*   93:     */       }
/*   94:     */     }
/*   95: 218 */     CSSStyleDeclaration.StyleElement element = new CSSStyleDeclaration.StyleElement(name, newValue, priority, specificity, getCurrentElementIndex());
/*   96: 219 */     this.localModifications_.put(name, element);
/*   97:     */   }
/*   98:     */   
/*   99:     */   public void setDefaultLocalStyleAttribute(String name, String newValue)
/*  100:     */   {
/*  101: 231 */     CSSStyleDeclaration.StyleElement element = new CSSStyleDeclaration.StyleElement(name, newValue);
/*  102: 232 */     this.localModifications_.put(name, element);
/*  103:     */   }
/*  104:     */   
/*  105:     */   protected Map<String, CSSStyleDeclaration.StyleElement> getStyleMap()
/*  106:     */   {
/*  107: 240 */     Map<String, CSSStyleDeclaration.StyleElement> styleMap = super.getStyleMap();
/*  108: 241 */     if (this.localModifications_ != null) {
/*  109: 242 */       for (CSSStyleDeclaration.StyleElement e : this.localModifications_.values())
/*  110:     */       {
/*  111: 243 */         String key = e.getName();
/*  112: 244 */         CSSStyleDeclaration.StyleElement existent = (CSSStyleDeclaration.StyleElement)styleMap.get(key);
/*  113: 245 */         if (existent == null)
/*  114:     */         {
/*  115: 246 */           CSSStyleDeclaration.StyleElement element = new CSSStyleDeclaration.StyleElement(key, e.getValue(), e.getIndex());
/*  116:     */           
/*  117:     */ 
/*  118:     */ 
/*  119: 250 */           styleMap.put(key, element);
/*  120:     */         }
/*  121:     */       }
/*  122:     */     }
/*  123: 254 */     return styleMap;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public String jsxGet_backgroundAttachment()
/*  127:     */   {
/*  128: 262 */     return StringUtils.defaultIfEmpty(super.jsxGet_backgroundAttachment(), "scroll");
/*  129:     */   }
/*  130:     */   
/*  131:     */   public String jsxGet_backgroundColor()
/*  132:     */   {
/*  133: 270 */     String value = super.jsxGet_backgroundColor();
/*  134: 271 */     if (StringUtils.isEmpty(value)) {
/*  135: 272 */       value = "transparent";
/*  136: 274 */     } else if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_GET_BACKGROUND_COLOR_FOR_COMPUTED_STYLE_RETURNS_RGB)) {
/*  137: 276 */       value = toRGBColor(value);
/*  138:     */     }
/*  139: 278 */     return value;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public String jsxGet_backgroundImage()
/*  143:     */   {
/*  144: 286 */     return StringUtils.defaultIfEmpty(super.jsxGet_backgroundImage(), "none");
/*  145:     */   }
/*  146:     */   
/*  147:     */   public String jsxGet_backgroundPosition()
/*  148:     */   {
/*  149: 294 */     String bg = super.jsxGet_backgroundPosition();
/*  150: 295 */     if (StringUtils.isNotBlank(bg))
/*  151:     */     {
/*  152: 296 */       bg = StringUtils.replace(bg, "left", "0%");
/*  153: 297 */       bg = StringUtils.replace(bg, "right", "100%");
/*  154: 298 */       bg = StringUtils.replace(bg, "center", "50%");
/*  155:     */       
/*  156: 300 */       bg = StringUtils.replace(bg, "top", "0%");
/*  157: 301 */       bg = StringUtils.replace(bg, "bottom", "100%");
/*  158:     */     }
/*  159: 304 */     return StringUtils.defaultIfEmpty(bg, "0% 0%");
/*  160:     */   }
/*  161:     */   
/*  162:     */   public String jsxGet_backgroundRepeat()
/*  163:     */   {
/*  164: 312 */     return StringUtils.defaultIfEmpty(super.jsxGet_backgroundRepeat(), "repeat");
/*  165:     */   }
/*  166:     */   
/*  167:     */   public String jsxGet_borderBottomColor()
/*  168:     */   {
/*  169: 320 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderBottomColor(), "rgb(0, 0, 0)");
/*  170:     */   }
/*  171:     */   
/*  172:     */   public String jsxGet_borderBottomStyle()
/*  173:     */   {
/*  174: 328 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderBottomStyle(), "none");
/*  175:     */   }
/*  176:     */   
/*  177:     */   public String jsxGet_borderBottomWidth()
/*  178:     */   {
/*  179: 336 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_borderBottomWidth(), "0px"));
/*  180:     */   }
/*  181:     */   
/*  182:     */   public String jsxGet_borderCollapse()
/*  183:     */   {
/*  184: 344 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderCollapse(), "separate");
/*  185:     */   }
/*  186:     */   
/*  187:     */   public String jsxGet_borderLeftColor()
/*  188:     */   {
/*  189: 352 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderLeftColor(), "rgb(0, 0, 0)");
/*  190:     */   }
/*  191:     */   
/*  192:     */   public String jsxGet_borderLeftStyle()
/*  193:     */   {
/*  194: 360 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderLeftStyle(), "none");
/*  195:     */   }
/*  196:     */   
/*  197:     */   public String jsxGet_borderLeftWidth()
/*  198:     */   {
/*  199: 368 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_borderLeftWidth(), "0px"));
/*  200:     */   }
/*  201:     */   
/*  202:     */   public String jsxGet_borderRightColor()
/*  203:     */   {
/*  204: 376 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderRightColor(), "rgb(0, 0, 0)");
/*  205:     */   }
/*  206:     */   
/*  207:     */   public String jsxGet_borderRightStyle()
/*  208:     */   {
/*  209: 384 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderRightStyle(), "none");
/*  210:     */   }
/*  211:     */   
/*  212:     */   public String jsxGet_borderRightWidth()
/*  213:     */   {
/*  214: 392 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_borderRightWidth(), "0px"));
/*  215:     */   }
/*  216:     */   
/*  217:     */   public String jsxGet_borderSpacing()
/*  218:     */   {
/*  219: 400 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderSpacing(), "0px 0px");
/*  220:     */   }
/*  221:     */   
/*  222:     */   public String jsxGet_borderTopColor()
/*  223:     */   {
/*  224: 408 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderTopColor(), "rgb(0, 0, 0)");
/*  225:     */   }
/*  226:     */   
/*  227:     */   public String jsxGet_borderTopStyle()
/*  228:     */   {
/*  229: 416 */     return StringUtils.defaultIfEmpty(super.jsxGet_borderTopStyle(), "none");
/*  230:     */   }
/*  231:     */   
/*  232:     */   public String jsxGet_borderTopWidth()
/*  233:     */   {
/*  234: 424 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_borderTopWidth(), "0px"));
/*  235:     */   }
/*  236:     */   
/*  237:     */   public String jsxGet_bottom()
/*  238:     */   {
/*  239: 432 */     return StringUtils.defaultIfEmpty(super.jsxGet_bottom(), "auto");
/*  240:     */   }
/*  241:     */   
/*  242:     */   public String jsxGet_captionSide()
/*  243:     */   {
/*  244: 440 */     return StringUtils.defaultIfEmpty(super.jsxGet_captionSide(), "top");
/*  245:     */   }
/*  246:     */   
/*  247:     */   public String jsxGet_clear()
/*  248:     */   {
/*  249: 448 */     return StringUtils.defaultIfEmpty(super.jsxGet_clear(), "none");
/*  250:     */   }
/*  251:     */   
/*  252:     */   public String jsxGet_clip()
/*  253:     */   {
/*  254: 456 */     return StringUtils.defaultIfEmpty(super.jsxGet_clip(), "auto");
/*  255:     */   }
/*  256:     */   
/*  257:     */   public String jsxGet_color()
/*  258:     */   {
/*  259: 464 */     return StringUtils.defaultIfEmpty(super.jsxGet_color(), "rgb(0, 0, 0)");
/*  260:     */   }
/*  261:     */   
/*  262:     */   public String jsxGet_counterIncrement()
/*  263:     */   {
/*  264: 472 */     return StringUtils.defaultIfEmpty(super.jsxGet_counterIncrement(), "none");
/*  265:     */   }
/*  266:     */   
/*  267:     */   public String jsxGet_counterReset()
/*  268:     */   {
/*  269: 480 */     return StringUtils.defaultIfEmpty(super.jsxGet_counterReset(), "none");
/*  270:     */   }
/*  271:     */   
/*  272:     */   public String jsxGet_cssFloat()
/*  273:     */   {
/*  274: 488 */     return StringUtils.defaultIfEmpty(super.jsxGet_cssFloat(), "none");
/*  275:     */   }
/*  276:     */   
/*  277:     */   public String jsxGet_cursor()
/*  278:     */   {
/*  279: 496 */     return StringUtils.defaultIfEmpty(super.jsxGet_cursor(), "auto");
/*  280:     */   }
/*  281:     */   
/*  282:     */   public String jsxGet_direction()
/*  283:     */   {
/*  284: 504 */     return StringUtils.defaultIfEmpty(super.jsxGet_direction(), "ltr");
/*  285:     */   }
/*  286:     */   
/*  287:     */   public String jsxGet_display()
/*  288:     */   {
/*  289: 512 */     return StringUtils.defaultIfEmpty(super.jsxGet_display(), getDefaultStyleDisplay());
/*  290:     */   }
/*  291:     */   
/*  292:     */   private String getDefaultStyleDisplay()
/*  293:     */   {
/*  294: 516 */     if (this.defaultDisplays_ == null)
/*  295:     */     {
/*  296: 517 */       Map<String, String> map = new HashMap();
/*  297: 518 */       map.put("A", "inline");
/*  298: 519 */       map.put("CODE", "inline");
/*  299: 520 */       map.put("SPAN", "inline");
/*  300: 521 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_DISPLAY_DEFAULT))
/*  301:     */       {
/*  302: 522 */         map.put("LI", "list-item");
/*  303: 523 */         map.put("TABLE", "table");
/*  304: 524 */         map.put("TBODY", "table-row-group");
/*  305: 525 */         map.put("TD", "table-cell");
/*  306: 526 */         map.put("TH", "table-cell");
/*  307: 527 */         map.put("THEAD", "table-header-group");
/*  308: 528 */         map.put("TR", "table-row");
/*  309:     */       }
/*  310: 530 */       this.defaultDisplays_ = Collections.unmodifiableMap(map);
/*  311:     */     }
/*  312: 532 */     String defaultValue = (String)this.defaultDisplays_.get(getElement().jsxGet_tagName());
/*  313: 533 */     if (defaultValue == null) {
/*  314: 534 */       return "block";
/*  315:     */     }
/*  316: 536 */     return defaultValue;
/*  317:     */   }
/*  318:     */   
/*  319:     */   public String jsxGet_emptyCells()
/*  320:     */   {
/*  321: 544 */     return StringUtils.defaultIfEmpty(super.jsxGet_emptyCells(), "-moz-show-background");
/*  322:     */   }
/*  323:     */   
/*  324:     */   public String jsxGet_fontFamily()
/*  325:     */   {
/*  326: 552 */     return StringUtils.defaultIfEmpty(super.jsxGet_fontFamily(), "serif");
/*  327:     */   }
/*  328:     */   
/*  329:     */   public String jsxGet_fontSize()
/*  330:     */   {
/*  331: 560 */     String value = super.jsxGet_fontSize();
/*  332: 561 */     if (value.length() == 0)
/*  333:     */     {
/*  334: 562 */       HTMLElement parent = getElement().getParentHTMLElement();
/*  335: 563 */       if (parent != null) {
/*  336: 564 */         value = parent.jsxGet_currentStyle().jsxGet_fontSize();
/*  337:     */       }
/*  338:     */     }
/*  339: 567 */     if (value.length() == 0) {
/*  340: 568 */       value = "16px";
/*  341:     */     }
/*  342: 570 */     return value;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public String jsxGet_fontSizeAdjust()
/*  346:     */   {
/*  347: 578 */     return StringUtils.defaultIfEmpty(super.jsxGet_fontSizeAdjust(), "none");
/*  348:     */   }
/*  349:     */   
/*  350:     */   public String jsxGet_fontStyle()
/*  351:     */   {
/*  352: 586 */     return StringUtils.defaultIfEmpty(super.jsxGet_fontStyle(), "normal");
/*  353:     */   }
/*  354:     */   
/*  355:     */   public String jsxGet_fontVariant()
/*  356:     */   {
/*  357: 594 */     return StringUtils.defaultIfEmpty(super.jsxGet_fontVariant(), "normal");
/*  358:     */   }
/*  359:     */   
/*  360:     */   public String jsxGet_fontWeight()
/*  361:     */   {
/*  362: 602 */     return StringUtils.defaultIfEmpty(super.jsxGet_fontWeight(), "400");
/*  363:     */   }
/*  364:     */   
/*  365:     */   public String jsxGet_height()
/*  366:     */   {
/*  367: 610 */     pixelString(getElement(), new CSSStyleDeclaration.CssValue(605)
/*  368:     */     {
/*  369:     */       public String get(ComputedCSSStyleDeclaration style)
/*  370:     */       {
/*  371: 612 */         return StringUtils.defaultIfEmpty(style.getStyleAttribute("height", null), "363px");
/*  372:     */       }
/*  373:     */     });
/*  374:     */   }
/*  375:     */   
/*  376:     */   public String jsxGet_left()
/*  377:     */   {
/*  378: 622 */     return StringUtils.defaultIfEmpty(super.jsxGet_left(), "auto");
/*  379:     */   }
/*  380:     */   
/*  381:     */   public String jsxGet_letterSpacing()
/*  382:     */   {
/*  383: 630 */     return StringUtils.defaultIfEmpty(super.jsxGet_letterSpacing(), "normal");
/*  384:     */   }
/*  385:     */   
/*  386:     */   public String jsxGet_lineHeight()
/*  387:     */   {
/*  388: 638 */     return StringUtils.defaultIfEmpty(super.jsxGet_lineHeight(), "normal");
/*  389:     */   }
/*  390:     */   
/*  391:     */   public String jsxGet_listStyleImage()
/*  392:     */   {
/*  393: 646 */     return StringUtils.defaultIfEmpty(super.jsxGet_listStyleImage(), "none");
/*  394:     */   }
/*  395:     */   
/*  396:     */   public String jsxGet_listStylePosition()
/*  397:     */   {
/*  398: 654 */     return StringUtils.defaultIfEmpty(super.jsxGet_listStylePosition(), "outside");
/*  399:     */   }
/*  400:     */   
/*  401:     */   public String jsxGet_listStyleType()
/*  402:     */   {
/*  403: 662 */     return StringUtils.defaultIfEmpty(super.jsxGet_listStyleType(), "disc");
/*  404:     */   }
/*  405:     */   
/*  406:     */   public String jsxGet_marginBottom()
/*  407:     */   {
/*  408: 670 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_marginBottom(), "0px"));
/*  409:     */   }
/*  410:     */   
/*  411:     */   public String jsxGet_marginLeft()
/*  412:     */   {
/*  413: 678 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_marginLeft(), "0px"));
/*  414:     */   }
/*  415:     */   
/*  416:     */   public String jsxGet_marginRight()
/*  417:     */   {
/*  418: 686 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_marginRight(), "0px"));
/*  419:     */   }
/*  420:     */   
/*  421:     */   public String jsxGet_marginTop()
/*  422:     */   {
/*  423: 694 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_marginTop(), "0px"));
/*  424:     */   }
/*  425:     */   
/*  426:     */   public String jsxGet_markerOffset()
/*  427:     */   {
/*  428: 702 */     return StringUtils.defaultIfEmpty(super.jsxGet_markerOffset(), "none");
/*  429:     */   }
/*  430:     */   
/*  431:     */   public String jsxGet_maxHeight()
/*  432:     */   {
/*  433: 710 */     return StringUtils.defaultIfEmpty(super.jsxGet_maxHeight(), "none");
/*  434:     */   }
/*  435:     */   
/*  436:     */   public String jsxGet_maxWidth()
/*  437:     */   {
/*  438: 718 */     return StringUtils.defaultIfEmpty(super.jsxGet_maxWidth(), "none");
/*  439:     */   }
/*  440:     */   
/*  441:     */   public String jsxGet_minHeight()
/*  442:     */   {
/*  443: 726 */     return StringUtils.defaultIfEmpty(super.jsxGet_minHeight(), "0px");
/*  444:     */   }
/*  445:     */   
/*  446:     */   public String jsxGet_minWidth()
/*  447:     */   {
/*  448: 734 */     return StringUtils.defaultIfEmpty(super.jsxGet_minWidth(), "0px");
/*  449:     */   }
/*  450:     */   
/*  451:     */   public String jsxGet_MozAppearance()
/*  452:     */   {
/*  453: 742 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozAppearance(), "none");
/*  454:     */   }
/*  455:     */   
/*  456:     */   public String jsxGet_MozBackgroundClip()
/*  457:     */   {
/*  458: 750 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBackgroundClip(), "border");
/*  459:     */   }
/*  460:     */   
/*  461:     */   public String jsxGet_MozBackgroundInlinePolicy()
/*  462:     */   {
/*  463: 758 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBackgroundInlinePolicy(), "continuous");
/*  464:     */   }
/*  465:     */   
/*  466:     */   public String jsxGet_MozBackgroundOrigin()
/*  467:     */   {
/*  468: 766 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBackgroundOrigin(), "padding");
/*  469:     */   }
/*  470:     */   
/*  471:     */   public String jsxGet_MozBinding()
/*  472:     */   {
/*  473: 774 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBinding(), "none");
/*  474:     */   }
/*  475:     */   
/*  476:     */   public String jsxGet_MozBorderBottomColors()
/*  477:     */   {
/*  478: 782 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBorderBottomColors(), "none");
/*  479:     */   }
/*  480:     */   
/*  481:     */   public String jsxGet_MozBorderLeftColors()
/*  482:     */   {
/*  483: 790 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBorderLeftColors(), "none");
/*  484:     */   }
/*  485:     */   
/*  486:     */   public String jsxGet_MozBorderRadiusBottomleft()
/*  487:     */   {
/*  488: 798 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBorderRadiusBottomleft(), "0px");
/*  489:     */   }
/*  490:     */   
/*  491:     */   public String jsxGet_MozBorderRadiusBottomright()
/*  492:     */   {
/*  493: 806 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBorderRadiusBottomright(), "0px");
/*  494:     */   }
/*  495:     */   
/*  496:     */   public String jsxGet_MozBorderRadiusTopleft()
/*  497:     */   {
/*  498: 814 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBorderRadiusTopleft(), "0px");
/*  499:     */   }
/*  500:     */   
/*  501:     */   public String jsxGet_MozBorderRadiusTopright()
/*  502:     */   {
/*  503: 822 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBorderRadiusTopright(), "0px");
/*  504:     */   }
/*  505:     */   
/*  506:     */   public String jsxGet_MozBorderRightColors()
/*  507:     */   {
/*  508: 830 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBorderRightColors(), "none");
/*  509:     */   }
/*  510:     */   
/*  511:     */   public String jsxGet_MozBorderTopColors()
/*  512:     */   {
/*  513: 838 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBorderTopColors(), "none");
/*  514:     */   }
/*  515:     */   
/*  516:     */   public String jsxGet_MozBoxAlign()
/*  517:     */   {
/*  518: 846 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBoxAlign(), "stretch");
/*  519:     */   }
/*  520:     */   
/*  521:     */   public String jsxGet_MozBoxDirection()
/*  522:     */   {
/*  523: 854 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBoxDirection(), "normal");
/*  524:     */   }
/*  525:     */   
/*  526:     */   public String jsxGet_MozBoxFlex()
/*  527:     */   {
/*  528: 862 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBoxFlex(), "0");
/*  529:     */   }
/*  530:     */   
/*  531:     */   public String jsxGet_MozBoxOrdinalGroup()
/*  532:     */   {
/*  533: 870 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBoxOrdinalGroup(), "1");
/*  534:     */   }
/*  535:     */   
/*  536:     */   public String jsxGet_MozBoxOrient()
/*  537:     */   {
/*  538: 878 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBoxOrient(), "horizontal");
/*  539:     */   }
/*  540:     */   
/*  541:     */   public String jsxGet_MozBoxPack()
/*  542:     */   {
/*  543: 886 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBoxPack(), "start");
/*  544:     */   }
/*  545:     */   
/*  546:     */   public String jsxGet_MozBoxSizing()
/*  547:     */   {
/*  548: 894 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozBoxSizing(), "content-box");
/*  549:     */   }
/*  550:     */   
/*  551:     */   public String jsxGet_MozColumnCount()
/*  552:     */   {
/*  553: 902 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozColumnCount(), "auto");
/*  554:     */   }
/*  555:     */   
/*  556:     */   public String jsxGet_MozColumnGap()
/*  557:     */   {
/*  558: 910 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozColumnGap(), "0px");
/*  559:     */   }
/*  560:     */   
/*  561:     */   public String jsxGet_MozColumnWidth()
/*  562:     */   {
/*  563: 918 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozColumnWidth(), "auto");
/*  564:     */   }
/*  565:     */   
/*  566:     */   public String jsxGet_MozFloatEdge()
/*  567:     */   {
/*  568: 926 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozFloatEdge(), "content-box");
/*  569:     */   }
/*  570:     */   
/*  571:     */   public String jsxGet_MozImageRegion()
/*  572:     */   {
/*  573: 934 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozImageRegion(), "auto");
/*  574:     */   }
/*  575:     */   
/*  576:     */   public String jsxGet_MozOpacity()
/*  577:     */   {
/*  578: 942 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOpacity(), "1");
/*  579:     */   }
/*  580:     */   
/*  581:     */   public String jsxGet_MozOutlineColor()
/*  582:     */   {
/*  583: 950 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOutlineColor(), "rgb(0, 0, 0)");
/*  584:     */   }
/*  585:     */   
/*  586:     */   public String jsxGet_MozOutlineOffset()
/*  587:     */   {
/*  588: 958 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOutlineOffset(), "0px");
/*  589:     */   }
/*  590:     */   
/*  591:     */   public String jsxGet_MozOutlineRadiusBottomleft()
/*  592:     */   {
/*  593: 966 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOutlineRadiusBottomleft(), "0px");
/*  594:     */   }
/*  595:     */   
/*  596:     */   public String jsxGet_MozOutlineRadiusBottomright()
/*  597:     */   {
/*  598: 974 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOutlineRadiusBottomright(), "0px");
/*  599:     */   }
/*  600:     */   
/*  601:     */   public String jsxGet_MozOutlineRadiusTopleft()
/*  602:     */   {
/*  603: 982 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOutlineRadiusTopleft(), "0px");
/*  604:     */   }
/*  605:     */   
/*  606:     */   public String jsxGet_MozOutlineRadiusTopright()
/*  607:     */   {
/*  608: 990 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOutlineRadiusTopright(), "0px");
/*  609:     */   }
/*  610:     */   
/*  611:     */   public String jsxGet_MozOutlineStyle()
/*  612:     */   {
/*  613: 998 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOutlineStyle(), "none");
/*  614:     */   }
/*  615:     */   
/*  616:     */   public String jsxGet_MozOutlineWidth()
/*  617:     */   {
/*  618:1006 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozOutlineWidth(), "0px");
/*  619:     */   }
/*  620:     */   
/*  621:     */   public String jsxGet_MozUserFocus()
/*  622:     */   {
/*  623:1014 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozUserFocus(), "none");
/*  624:     */   }
/*  625:     */   
/*  626:     */   public String jsxGet_MozUserInput()
/*  627:     */   {
/*  628:1022 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozUserInput(), "auto");
/*  629:     */   }
/*  630:     */   
/*  631:     */   public String jsxGet_MozUserModify()
/*  632:     */   {
/*  633:1030 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozUserModify(), "read-only");
/*  634:     */   }
/*  635:     */   
/*  636:     */   public String jsxGet_MozUserSelect()
/*  637:     */   {
/*  638:1038 */     return StringUtils.defaultIfEmpty(super.jsxGet_MozUserSelect(), "auto");
/*  639:     */   }
/*  640:     */   
/*  641:     */   public String jsxGet_opacity()
/*  642:     */   {
/*  643:1046 */     return StringUtils.defaultIfEmpty(super.jsxGet_opacity(), "1");
/*  644:     */   }
/*  645:     */   
/*  646:     */   public String jsxGet_outlineColor()
/*  647:     */   {
/*  648:1054 */     return StringUtils.defaultIfEmpty(super.jsxGet_outlineColor(), "rgb(0, 0, 0)");
/*  649:     */   }
/*  650:     */   
/*  651:     */   public String jsxGet_outlineOffset()
/*  652:     */   {
/*  653:1062 */     return StringUtils.defaultIfEmpty(super.jsxGet_outlineOffset(), "0px");
/*  654:     */   }
/*  655:     */   
/*  656:     */   public String jsxGet_outlineStyle()
/*  657:     */   {
/*  658:1070 */     return StringUtils.defaultIfEmpty(super.jsxGet_outlineStyle(), "none");
/*  659:     */   }
/*  660:     */   
/*  661:     */   public String jsxGet_outlineWidth()
/*  662:     */   {
/*  663:1078 */     return StringUtils.defaultIfEmpty(super.jsxGet_outlineWidth(), "0px");
/*  664:     */   }
/*  665:     */   
/*  666:     */   public String jsxGet_overflow()
/*  667:     */   {
/*  668:1086 */     return StringUtils.defaultIfEmpty(super.jsxGet_overflow(), "visible");
/*  669:     */   }
/*  670:     */   
/*  671:     */   public String jsxGet_overflowX()
/*  672:     */   {
/*  673:1094 */     return StringUtils.defaultIfEmpty(super.jsxGet_overflowX(), "visible");
/*  674:     */   }
/*  675:     */   
/*  676:     */   public String jsxGet_overflowY()
/*  677:     */   {
/*  678:1102 */     return StringUtils.defaultIfEmpty(super.jsxGet_overflowY(), "visible");
/*  679:     */   }
/*  680:     */   
/*  681:     */   public String jsxGet_paddingBottom()
/*  682:     */   {
/*  683:1110 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_paddingBottom(), "0px"));
/*  684:     */   }
/*  685:     */   
/*  686:     */   public String jsxGet_paddingLeft()
/*  687:     */   {
/*  688:1118 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_paddingLeft(), "0px"));
/*  689:     */   }
/*  690:     */   
/*  691:     */   public String jsxGet_paddingRight()
/*  692:     */   {
/*  693:1126 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_paddingRight(), "0px"));
/*  694:     */   }
/*  695:     */   
/*  696:     */   public String jsxGet_paddingTop()
/*  697:     */   {
/*  698:1134 */     return pixelString(StringUtils.defaultIfEmpty(super.jsxGet_paddingTop(), "0px"));
/*  699:     */   }
/*  700:     */   
/*  701:     */   public String jsxGet_position()
/*  702:     */   {
/*  703:1142 */     return StringUtils.defaultIfEmpty(super.jsxGet_position(), "static");
/*  704:     */   }
/*  705:     */   
/*  706:     */   public String jsxGet_right()
/*  707:     */   {
/*  708:1150 */     return StringUtils.defaultIfEmpty(super.jsxGet_right(), "auto");
/*  709:     */   }
/*  710:     */   
/*  711:     */   public String jsxGet_tableLayout()
/*  712:     */   {
/*  713:1158 */     return StringUtils.defaultIfEmpty(super.jsxGet_tableLayout(), "auto");
/*  714:     */   }
/*  715:     */   
/*  716:     */   public String jsxGet_textAlign()
/*  717:     */   {
/*  718:1166 */     return StringUtils.defaultIfEmpty(super.jsxGet_textAlign(), "start");
/*  719:     */   }
/*  720:     */   
/*  721:     */   public String jsxGet_textDecoration()
/*  722:     */   {
/*  723:1174 */     return StringUtils.defaultIfEmpty(super.jsxGet_textDecoration(), "none");
/*  724:     */   }
/*  725:     */   
/*  726:     */   public String jsxGet_textIndent()
/*  727:     */   {
/*  728:1182 */     return StringUtils.defaultIfEmpty(super.jsxGet_textIndent(), "0px");
/*  729:     */   }
/*  730:     */   
/*  731:     */   public String jsxGet_textTransform()
/*  732:     */   {
/*  733:1190 */     return StringUtils.defaultIfEmpty(super.jsxGet_textTransform(), "none");
/*  734:     */   }
/*  735:     */   
/*  736:     */   public String jsxGet_top()
/*  737:     */   {
/*  738:1198 */     return StringUtils.defaultIfEmpty(super.jsxGet_top(), "auto");
/*  739:     */   }
/*  740:     */   
/*  741:     */   public String jsxGet_unicodeBidi()
/*  742:     */   {
/*  743:1206 */     return StringUtils.defaultIfEmpty(super.jsxGet_unicodeBidi(), "normal");
/*  744:     */   }
/*  745:     */   
/*  746:     */   public String jsxGet_verticalAlign()
/*  747:     */   {
/*  748:1214 */     return StringUtils.defaultIfEmpty(super.jsxGet_verticalAlign(), "baseline");
/*  749:     */   }
/*  750:     */   
/*  751:     */   public String jsxGet_visibility()
/*  752:     */   {
/*  753:1222 */     return StringUtils.defaultIfEmpty(super.jsxGet_visibility(), "visible");
/*  754:     */   }
/*  755:     */   
/*  756:     */   public String jsxGet_whiteSpace()
/*  757:     */   {
/*  758:1230 */     return StringUtils.defaultIfEmpty(super.jsxGet_whiteSpace(), "normal");
/*  759:     */   }
/*  760:     */   
/*  761:     */   public String jsxGet_width()
/*  762:     */   {
/*  763:1238 */     if ("none".equals(jsxGet_display())) {
/*  764:1239 */       return "auto";
/*  765:     */     }
/*  766:     */     String defaultWidth;
/*  767:     */     final String defaultWidth;
/*  768:1242 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_DEFAULT_WIDTH_AUTO)) {
/*  769:1243 */       defaultWidth = "auto";
/*  770:     */     } else {
/*  771:1246 */       defaultWidth = "1256px";
/*  772:     */     }
/*  773:1248 */     pixelString(getElement(), new CSSStyleDeclaration.CssValue(1256)
/*  774:     */     {
/*  775:     */       public String get(ComputedCSSStyleDeclaration style)
/*  776:     */       {
/*  777:1250 */         return StringUtils.defaultIfEmpty(style.getStyleAttribute("width", null), defaultWidth);
/*  778:     */       }
/*  779:     */     });
/*  780:     */   }
/*  781:     */   
/*  782:     */   public int getCalculatedWidth(boolean includeBorder, boolean includePadding)
/*  783:     */   {
/*  784:1262 */     int width = getCalculatedWidth();
/*  785:1263 */     if (includeBorder) {
/*  786:1264 */       width += getBorderHorizontal();
/*  787:     */     }
/*  788:1266 */     if (includePadding) {
/*  789:1267 */       width += getPaddingHorizontal();
/*  790:     */     }
/*  791:1269 */     return width;
/*  792:     */   }
/*  793:     */   
/*  794:     */   private int getCalculatedWidth()
/*  795:     */   {
/*  796:1273 */     if (this.width_ != null) {
/*  797:1274 */       return this.width_.intValue();
/*  798:     */     }
/*  799:1277 */     DomNode node = getElement().getDomNodeOrDie();
/*  800:1278 */     if (!node.mayBeDisplayed())
/*  801:     */     {
/*  802:1279 */       this.width_ = Integer.valueOf(0);
/*  803:1280 */       return 0;
/*  804:     */     }
/*  805:1283 */     String display = jsxGet_display();
/*  806:1284 */     if ("none".equals(display))
/*  807:     */     {
/*  808:1285 */       this.width_ = Integer.valueOf(0);
/*  809:1286 */       return 0;
/*  810:     */     }
/*  811:1290 */     String styleWidth = super.jsxGet_width();
/*  812:1291 */     DomNode parent = node.getParentNode();
/*  813:     */     int width;
/*  814:     */     int width;
/*  815:1292 */     if ((StringUtils.isEmpty(styleWidth)) && ((parent instanceof HtmlElement)))
/*  816:     */     {
/*  817:1294 */       if ((getElement() instanceof HTMLCanvasElement)) {
/*  818:1295 */         return 300;
/*  819:     */       }
/*  820:1299 */       String cssFloat = jsxGet_cssFloat();
/*  821:     */       int width;
/*  822:1300 */       if (("right".equals(cssFloat)) || ("left".equals(cssFloat)))
/*  823:     */       {
/*  824:1302 */         width = getDomNodeOrDie().getTextContent().length() * 10;
/*  825:     */       }
/*  826:1304 */       else if ("block".equals(display))
/*  827:     */       {
/*  828:1306 */         HTMLElement parentJS = (HTMLElement)parent.getScriptObject();
/*  829:1307 */         String parentWidth = getWindow().jsxFunction_getComputedStyle(parentJS, null).jsxGet_width();
/*  830:     */         int width;
/*  831:     */         int width;
/*  832:1308 */         if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_DEFAULT_WIDTH_AUTO)) && ("auto".equals(parentWidth))) {
/*  833:1310 */           width = 1256;
/*  834:     */         } else {
/*  835:1313 */           width = pixelValue(parentJS, new CSSStyleDeclaration.CssValue(1256)
/*  836:     */           {
/*  837:     */             public String get(ComputedCSSStyleDeclaration style)
/*  838:     */             {
/*  839:1315 */               return style.jsxGet_width();
/*  840:     */             }
/*  841:     */           });
/*  842:     */         }
/*  843:1319 */         width -= getBorderHorizontal() + getPaddingHorizontal();
/*  844:     */       }
/*  845:     */       else
/*  846:     */       {
/*  847:1323 */         width = getContentWidth();
/*  848:     */       }
/*  849:     */     }
/*  850:     */     else
/*  851:     */     {
/*  852:1328 */       width = pixelValue(getElement(), new CSSStyleDeclaration.CssValue(1256)
/*  853:     */       {
/*  854:     */         public String get(ComputedCSSStyleDeclaration style)
/*  855:     */         {
/*  856:1330 */           return style.getStyleAttribute("width", null);
/*  857:     */         }
/*  858:     */       });
/*  859:     */     }
/*  860:1335 */     this.width_ = Integer.valueOf(width);
/*  861:1336 */     return width;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public int getContentWidth()
/*  865:     */   {
/*  866:1344 */     int width = 0;
/*  867:1345 */     for (DomNode child : getDomNodeOrDie().getChildren()) {
/*  868:1346 */       if ((child.getScriptObject() instanceof HTMLElement))
/*  869:     */       {
/*  870:1347 */         HTMLElement e = (HTMLElement)child.getScriptObject();
/*  871:1348 */         int w = e.jsxGet_currentStyle().getCalculatedWidth(true, true);
/*  872:1349 */         width += w;
/*  873:     */       }
/*  874:1351 */       else if ((child.getScriptObject() instanceof Text))
/*  875:     */       {
/*  876:1352 */         width += child.getTextContent().length() * 10;
/*  877:     */       }
/*  878:     */     }
/*  879:1355 */     return width;
/*  880:     */   }
/*  881:     */   
/*  882:     */   public int getCalculatedHeight(boolean includeBorder, boolean includePadding)
/*  883:     */   {
/*  884:1365 */     int height = getCalculatedHeight();
/*  885:1366 */     if (includeBorder) {
/*  886:1367 */       height += getBorderVertical();
/*  887:     */     }
/*  888:1369 */     if (includePadding) {
/*  889:1370 */       height += getPaddingVertical();
/*  890:     */     }
/*  891:1372 */     return height;
/*  892:     */   }
/*  893:     */   
/*  894:     */   private int getCalculatedHeight()
/*  895:     */   {
/*  896:1380 */     if (this.height_ != null) {
/*  897:1381 */       return this.height_.intValue();
/*  898:     */     }
/*  899:1384 */     int elementHeight = getEmptyHeight();
/*  900:1385 */     if (elementHeight == 0)
/*  901:     */     {
/*  902:1386 */       this.height_ = Integer.valueOf(elementHeight);
/*  903:1387 */       return elementHeight;
/*  904:     */     }
/*  905:1390 */     int contentHeight = getContentHeight();
/*  906:1391 */     boolean useDefaultHeight = getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_DEFAULT_ELMENT_HEIGHT_MARKS_MIN);
/*  907:     */     
/*  908:1393 */     boolean explicitHeightSpecified = super.jsxGet_height().length() > 0;
/*  909:     */     int height;
/*  910:     */     int height;
/*  911:1396 */     if ((contentHeight > 0) && (((useDefaultHeight) && (contentHeight > elementHeight)) || ((!useDefaultHeight) && (!explicitHeightSpecified)))) {
/*  912:1399 */       height = contentHeight;
/*  913:     */     } else {
/*  914:1402 */       height = elementHeight;
/*  915:     */     }
/*  916:1405 */     this.height_ = Integer.valueOf(height);
/*  917:1406 */     return height;
/*  918:     */   }
/*  919:     */   
/*  920:     */   private int getEmptyHeight()
/*  921:     */   {
/*  922:1417 */     if (this.height2_ != null) {
/*  923:1418 */       return this.height2_.intValue();
/*  924:     */     }
/*  925:1421 */     DomNode node = getElement().getDomNodeOrDie();
/*  926:1422 */     if (!node.mayBeDisplayed())
/*  927:     */     {
/*  928:1423 */       this.height2_ = Integer.valueOf(0);
/*  929:1424 */       return 0;
/*  930:     */     }
/*  931:1427 */     if ("none".equals(jsxGet_display()))
/*  932:     */     {
/*  933:1428 */       this.height2_ = Integer.valueOf(0);
/*  934:1429 */       return 0;
/*  935:     */     }
/*  936:1432 */     if ((getElement() instanceof HTMLBodyElement))
/*  937:     */     {
/*  938:1433 */       this.height2_ = Integer.valueOf(605);
/*  939:1434 */       return 605;
/*  940:     */     }
/*  941:1437 */     boolean explicitHeightSpecified = super.jsxGet_height().length() > 0;
/*  942:     */     
/*  943:1439 */     int defaultHeight = 20;
/*  944:1440 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_DEFAULT_ELMENT_HEIGHT_15)) {
/*  945:1441 */       defaultHeight = 15;
/*  946:     */     }
/*  947:1444 */     int defaultValue = (getElement() instanceof HTMLCanvasElement) ? 150 : 605;
/*  948:     */     
/*  949:1446 */     int height = pixelValue(getElement(), new CSSStyleDeclaration.CssValue(defaultValue)
/*  950:     */     {
/*  951:     */       public String get(ComputedCSSStyleDeclaration style)
/*  952:     */       {
/*  953:1448 */         return style.getStyleAttribute("height", null);
/*  954:     */       }
/*  955:1451 */     });
/*  956:1452 */     boolean useDefaultHeight = getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_DEFAULT_ELMENT_HEIGHT_MARKS_MIN);
/*  957:1454 */     if (((height == 0) && (!explicitHeightSpecified)) || ((useDefaultHeight) && (height < defaultHeight))) {
/*  958:1455 */       height = defaultHeight;
/*  959:     */     }
/*  960:1458 */     this.height2_ = Integer.valueOf(height);
/*  961:1459 */     return height;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public int getContentHeight()
/*  965:     */   {
/*  966:1471 */     DomNode node = getElement().getDomNodeOrDie();
/*  967:1472 */     if (!node.mayBeDisplayed()) {
/*  968:1473 */       return 0;
/*  969:     */     }
/*  970:1476 */     HTMLElement lastFlowing = null;
/*  971:1477 */     Set<HTMLElement> independent = new HashSet();
/*  972:1478 */     for (DomNode child : node.getChildren()) {
/*  973:1479 */       if ((child.mayBeDisplayed()) && ((child.getScriptObject() instanceof HTMLElement)))
/*  974:     */       {
/*  975:1480 */         HTMLElement e = (HTMLElement)child.getScriptObject();
/*  976:1481 */         ComputedCSSStyleDeclaration style = e.jsxGet_currentStyle();
/*  977:1482 */         String pos = style.getPositionWithInheritance();
/*  978:1483 */         if (("static".equals(pos)) || ("relative".equals(pos))) {
/*  979:1484 */           lastFlowing = e;
/*  980:1486 */         } else if ("absolute".equals(pos)) {
/*  981:1487 */           independent.add(e);
/*  982:     */         }
/*  983:     */       }
/*  984:     */     }
/*  985:1492 */     Set<HTMLElement> relevant = new HashSet();
/*  986:1493 */     relevant.addAll(independent);
/*  987:1494 */     if (lastFlowing != null) {
/*  988:1495 */       relevant.add(lastFlowing);
/*  989:     */     }
/*  990:1498 */     int max = 0;
/*  991:1499 */     for (HTMLElement e : relevant)
/*  992:     */     {
/*  993:1500 */       ComputedCSSStyleDeclaration style = e.jsxGet_currentStyle();
/*  994:1501 */       int h = style.getTop(true, false, false) + style.getCalculatedHeight(true, true);
/*  995:1502 */       if (h > max) {
/*  996:1503 */         max = h;
/*  997:     */       }
/*  998:     */     }
/*  999:1506 */     return max;
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public boolean isScrollable(boolean horizontal)
/* 1003:     */   {
/* 1004:1517 */     HTMLElement node = getElement();
/* 1005:1518 */     String overflow = jsxGet_overflow();
/* 1006:     */     boolean scrollable;
/* 1007:     */     boolean scrollable;
/* 1008:1519 */     if (horizontal) {
/* 1009:1521 */       scrollable = (((node instanceof HTMLBodyElement)) || ("scroll".equals(overflow)) || ("auto".equals(overflow))) && (getContentWidth() > getCalculatedWidth());
/* 1010:     */     } else {
/* 1011:1526 */       scrollable = (((node instanceof HTMLBodyElement)) || ("scroll".equals(overflow)) || ("auto".equals(overflow))) && (getContentHeight() > getEmptyHeight());
/* 1012:     */     }
/* 1013:1529 */     return scrollable;
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public int getTop(boolean includeMargin, boolean includeBorder, boolean includePadding)
/* 1017:     */   {
/* 1018:1540 */     String p = getPositionWithInheritance();
/* 1019:1541 */     String t = getTopWithInheritance();
/* 1020:1542 */     String b = getBottomWithInheritance();
/* 1021:     */     int top;
/* 1022:     */     int top;
/* 1023:1545 */     if (("absolute".equals(p)) && (!"auto".equals(t)))
/* 1024:     */     {
/* 1025:1547 */       top = pixelValue(t);
/* 1026:     */     }
/* 1027:1549 */     else if (("absolute".equals(p)) && (!"auto".equals(b)))
/* 1028:     */     {
/* 1029:1553 */       int top = 0;
/* 1030:1554 */       DomNode child = getElement().getDomNodeOrDie().getParentNode().getFirstChild();
/* 1031:1555 */       while (child != null)
/* 1032:     */       {
/* 1033:1556 */         if (((child instanceof HtmlElement)) && (child.mayBeDisplayed())) {
/* 1034:1557 */           top += 20;
/* 1035:     */         }
/* 1036:1559 */         child = child.getNextSibling();
/* 1037:     */       }
/* 1038:1561 */       top -= pixelValue(b);
/* 1039:     */     }
/* 1040:     */     else
/* 1041:     */     {
/* 1042:1565 */       top = 0;
/* 1043:1566 */       DomNode prev = getElement().getDomNodeOrDie().getPreviousSibling();
/* 1044:1567 */       while ((prev != null) && (!(prev instanceof HtmlElement))) {
/* 1045:1568 */         prev = prev.getPreviousSibling();
/* 1046:     */       }
/* 1047:1570 */       if (prev != null)
/* 1048:     */       {
/* 1049:1571 */         HTMLElement e = (HTMLElement)((HtmlElement)prev).getScriptObject();
/* 1050:1572 */         ComputedCSSStyleDeclaration style = e.jsxGet_currentStyle();
/* 1051:1573 */         top = style.getTop(true, false, false) + style.getCalculatedHeight(true, true);
/* 1052:     */       }
/* 1053:1576 */       if ("relative".equals(p)) {
/* 1054:1577 */         top += pixelValue(t);
/* 1055:     */       }
/* 1056:     */     }
/* 1057:1581 */     if (includeMargin)
/* 1058:     */     {
/* 1059:1582 */       int margin = pixelValue(jsxGet_marginTop());
/* 1060:1583 */       top += margin;
/* 1061:     */     }
/* 1062:1586 */     if (includeBorder)
/* 1063:     */     {
/* 1064:1587 */       int border = pixelValue(jsxGet_borderTopWidth());
/* 1065:1588 */       top += border;
/* 1066:     */     }
/* 1067:1591 */     if (includePadding)
/* 1068:     */     {
/* 1069:1592 */       int padding = getPaddingTop();
/* 1070:1593 */       top += padding;
/* 1071:     */     }
/* 1072:1596 */     return top;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public int getLeft(boolean includeMargin, boolean includeBorder, boolean includePadding)
/* 1076:     */   {
/* 1077:1607 */     String p = getPositionWithInheritance();
/* 1078:1608 */     String l = getLeftWithInheritance();
/* 1079:1609 */     String r = getRightWithInheritance();
/* 1080:1611 */     if (("fixed".equals(p)) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.TREATS_POSITION_FIXED_LIKE_POSITION_STATIC))) {
/* 1081:1613 */       p = "static";
/* 1082:     */     }
/* 1083:     */     int left;
/* 1084:     */     int left;
/* 1085:1617 */     if (("absolute".equals(p)) && (!"auto".equals(l)))
/* 1086:     */     {
/* 1087:1619 */       left = pixelValue(l);
/* 1088:     */     }
/* 1089:     */     else
/* 1090:     */     {
/* 1091:     */       int left;
/* 1092:1621 */       if (("absolute".equals(p)) && (!"auto".equals(r)))
/* 1093:     */       {
/* 1094:1623 */         HTMLElement parent = getElement().getParentHTMLElement();
/* 1095:1624 */         int parentWidth = parent.jsxGet_currentStyle().getCalculatedWidth(false, false);
/* 1096:1625 */         left = parentWidth - pixelValue(r);
/* 1097:     */       }
/* 1098:     */       else
/* 1099:     */       {
/* 1100:     */         int left;
/* 1101:1627 */         if (("fixed".equals(p)) && ("auto".equals(l)))
/* 1102:     */         {
/* 1103:1629 */           HTMLElement parent = getElement().getParentHTMLElement();
/* 1104:1630 */           left = pixelValue(parent.jsxGet_currentStyle().getLeftWithInheritance());
/* 1105:     */         }
/* 1106:1632 */         else if ("static".equals(p))
/* 1107:     */         {
/* 1108:1634 */           int left = 0;
/* 1109:1635 */           for (DomNode n = getDomNodeOrDie(); n != null; n = n.getPreviousSibling())
/* 1110:     */           {
/* 1111:1636 */             if ((n.getScriptObject() instanceof HTMLElement))
/* 1112:     */             {
/* 1113:1637 */               HTMLElement e = (HTMLElement)n.getScriptObject();
/* 1114:1638 */               String d = e.jsxGet_currentStyle().jsxGet_display();
/* 1115:1639 */               if ("block".equals(d)) {
/* 1116:     */                 break;
/* 1117:     */               }
/* 1118:1642 */               if (!"none".equals(d)) {
/* 1119:1643 */                 left += e.jsxGet_currentStyle().getCalculatedWidth(true, true);
/* 1120:     */               }
/* 1121:     */             }
/* 1122:1646 */             else if ((n.getScriptObject() instanceof Text))
/* 1123:     */             {
/* 1124:1647 */               left += n.getTextContent().length() * 10;
/* 1125:     */             }
/* 1126:1649 */             if ((n instanceof HtmlTableRow)) {
/* 1127:     */               break;
/* 1128:     */             }
/* 1129:     */           }
/* 1130:     */         }
/* 1131:     */         else
/* 1132:     */         {
/* 1133:1656 */           left = pixelValue(l);
/* 1134:     */         }
/* 1135:     */       }
/* 1136:     */     }
/* 1137:1659 */     if (includeMargin)
/* 1138:     */     {
/* 1139:1660 */       int margin = getMarginLeft();
/* 1140:1661 */       left += margin;
/* 1141:     */     }
/* 1142:1664 */     if (includeBorder)
/* 1143:     */     {
/* 1144:1665 */       int border = pixelValue(jsxGet_borderLeftWidth());
/* 1145:1666 */       left += border;
/* 1146:     */     }
/* 1147:1669 */     if (includePadding)
/* 1148:     */     {
/* 1149:1670 */       int padding = getPaddingLeft();
/* 1150:1671 */       left += padding;
/* 1151:     */     }
/* 1152:1674 */     return left;
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   public String getPositionWithInheritance()
/* 1156:     */   {
/* 1157:1682 */     String p = jsxGet_position();
/* 1158:1683 */     if ("inherit".equals(p)) {
/* 1159:1684 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CAN_INHERIT_CSS_PROPERTY_VALUES))
/* 1160:     */       {
/* 1161:1685 */         HTMLElement parent = getElement().getParentHTMLElement();
/* 1162:1686 */         p = parent != null ? parent.jsxGet_currentStyle().getPositionWithInheritance() : "static";
/* 1163:     */       }
/* 1164:     */       else
/* 1165:     */       {
/* 1166:1689 */         p = "static";
/* 1167:     */       }
/* 1168:     */     }
/* 1169:1692 */     return p;
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public String getLeftWithInheritance()
/* 1173:     */   {
/* 1174:1700 */     String left = jsxGet_left();
/* 1175:1701 */     if ("inherit".equals(left)) {
/* 1176:1702 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CAN_INHERIT_CSS_PROPERTY_VALUES))
/* 1177:     */       {
/* 1178:1703 */         HTMLElement parent = getElement().getParentHTMLElement();
/* 1179:1704 */         left = parent != null ? parent.jsxGet_currentStyle().getLeftWithInheritance() : "auto";
/* 1180:     */       }
/* 1181:     */       else
/* 1182:     */       {
/* 1183:1707 */         left = "auto";
/* 1184:     */       }
/* 1185:     */     }
/* 1186:1710 */     return left;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public String getRightWithInheritance()
/* 1190:     */   {
/* 1191:1718 */     String right = jsxGet_right();
/* 1192:1719 */     if ("inherit".equals(right)) {
/* 1193:1720 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CAN_INHERIT_CSS_PROPERTY_VALUES))
/* 1194:     */       {
/* 1195:1721 */         HTMLElement parent = getElement().getParentHTMLElement();
/* 1196:1722 */         right = parent != null ? parent.jsxGet_currentStyle().getRightWithInheritance() : "auto";
/* 1197:     */       }
/* 1198:     */       else
/* 1199:     */       {
/* 1200:1725 */         right = "auto";
/* 1201:     */       }
/* 1202:     */     }
/* 1203:1728 */     return right;
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   public String getTopWithInheritance()
/* 1207:     */   {
/* 1208:1736 */     String top = jsxGet_top();
/* 1209:1737 */     if ("inherit".equals(top)) {
/* 1210:1738 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CAN_INHERIT_CSS_PROPERTY_VALUES))
/* 1211:     */       {
/* 1212:1739 */         HTMLElement parent = getElement().getParentHTMLElement();
/* 1213:1740 */         top = parent != null ? parent.jsxGet_currentStyle().getTopWithInheritance() : "auto";
/* 1214:     */       }
/* 1215:     */       else
/* 1216:     */       {
/* 1217:1743 */         top = "auto";
/* 1218:     */       }
/* 1219:     */     }
/* 1220:1746 */     return top;
/* 1221:     */   }
/* 1222:     */   
/* 1223:     */   public String getBottomWithInheritance()
/* 1224:     */   {
/* 1225:1754 */     String bottom = jsxGet_bottom();
/* 1226:1755 */     if ("inherit".equals(bottom)) {
/* 1227:1756 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CAN_INHERIT_CSS_PROPERTY_VALUES))
/* 1228:     */       {
/* 1229:1757 */         HTMLElement parent = getElement().getParentHTMLElement();
/* 1230:1758 */         bottom = parent != null ? parent.jsxGet_currentStyle().getBottomWithInheritance() : "auto";
/* 1231:     */       }
/* 1232:     */       else
/* 1233:     */       {
/* 1234:1761 */         bottom = "auto";
/* 1235:     */       }
/* 1236:     */     }
/* 1237:1764 */     return bottom;
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public int getMarginLeft()
/* 1241:     */   {
/* 1242:1772 */     return pixelValue(jsxGet_marginLeft());
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   public int getMarginRight()
/* 1246:     */   {
/* 1247:1780 */     return pixelValue(jsxGet_marginRight());
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public int getMarginTop()
/* 1251:     */   {
/* 1252:1788 */     return pixelValue(jsxGet_marginTop());
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public int getMarginBottom()
/* 1256:     */   {
/* 1257:1796 */     return pixelValue(jsxGet_marginBottom());
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   public int getPaddingLeft()
/* 1261:     */   {
/* 1262:1804 */     return pixelValue(jsxGet_paddingLeft());
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   public int getPaddingRight()
/* 1266:     */   {
/* 1267:1812 */     return pixelValue(jsxGet_paddingRight());
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   public int getPaddingTop()
/* 1271:     */   {
/* 1272:1820 */     return pixelValue(jsxGet_paddingTop());
/* 1273:     */   }
/* 1274:     */   
/* 1275:     */   public int getPaddingBottom()
/* 1276:     */   {
/* 1277:1828 */     return pixelValue(jsxGet_paddingBottom());
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   private int getPaddingHorizontal()
/* 1281:     */   {
/* 1282:1832 */     if (this.paddingHorizontal_ == null) {
/* 1283:1833 */       this.paddingHorizontal_ = Integer.valueOf("none".equals(jsxGet_display()) ? 0 : getPaddingLeft() + getPaddingRight());
/* 1284:     */     }
/* 1285:1836 */     return this.paddingHorizontal_.intValue();
/* 1286:     */   }
/* 1287:     */   
/* 1288:     */   private int getPaddingVertical()
/* 1289:     */   {
/* 1290:1840 */     if (this.paddingVertical_ == null) {
/* 1291:1841 */       this.paddingVertical_ = Integer.valueOf("none".equals(jsxGet_display()) ? 0 : getPaddingTop() + getPaddingBottom());
/* 1292:     */     }
/* 1293:1844 */     return this.paddingVertical_.intValue();
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   public int getBorderLeft()
/* 1297:     */   {
/* 1298:1852 */     return pixelValue(jsxGet_borderLeftWidth());
/* 1299:     */   }
/* 1300:     */   
/* 1301:     */   public int getBorderRight()
/* 1302:     */   {
/* 1303:1860 */     return pixelValue(jsxGet_borderRightWidth());
/* 1304:     */   }
/* 1305:     */   
/* 1306:     */   public int getBorderTop()
/* 1307:     */   {
/* 1308:1868 */     return pixelValue(jsxGet_borderTopWidth());
/* 1309:     */   }
/* 1310:     */   
/* 1311:     */   public int getBorderBottom()
/* 1312:     */   {
/* 1313:1876 */     return pixelValue(jsxGet_borderBottomWidth());
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   private int getBorderHorizontal()
/* 1317:     */   {
/* 1318:1880 */     if (this.borderHorizontal_ == null) {
/* 1319:1881 */       this.borderHorizontal_ = Integer.valueOf("none".equals(jsxGet_display()) ? 0 : getBorderLeft() + getBorderRight());
/* 1320:     */     }
/* 1321:1884 */     return this.borderHorizontal_.intValue();
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   private int getBorderVertical()
/* 1325:     */   {
/* 1326:1888 */     if (this.borderVertical_ == null) {
/* 1327:1889 */       this.borderVertical_ = Integer.valueOf("none".equals(jsxGet_display()) ? 0 : getBorderTop() + getBorderBottom());
/* 1328:     */     }
/* 1329:1892 */     return this.borderVertical_.intValue();
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   public String jsxGet_wordSpacing()
/* 1333:     */   {
/* 1334:1900 */     return StringUtils.defaultIfEmpty(super.jsxGet_wordSpacing(), "normal");
/* 1335:     */   }
/* 1336:     */   
/* 1337:     */   public Object jsxGet_zIndex()
/* 1338:     */   {
/* 1339:1908 */     Object response = super.jsxGet_zIndex();
/* 1340:1909 */     if (response.toString().length() == 0) {
/* 1341:1910 */       return "auto";
/* 1342:     */     }
/* 1343:1912 */     return response;
/* 1344:     */   }
/* 1345:     */   
/* 1346:     */   public String jsxFunction_getPropertyValue(String name)
/* 1347:     */   {
/* 1348:1921 */     String response = Context.toString(getProperty(this, camelize(name)));
/* 1349:1922 */     if (response == NOT_FOUND) {
/* 1350:1923 */       return super.jsxFunction_getPropertyValue(name);
/* 1351:     */     }
/* 1352:1925 */     return response;
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   protected String pixelString(String value)
/* 1356:     */   {
/* 1357:1937 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_LENGTH_WITHOUT_PX)) {
/* 1358:1938 */       return value;
/* 1359:     */     }
/* 1360:1940 */     if (value.endsWith("px")) {
/* 1361:1941 */       return value;
/* 1362:     */     }
/* 1363:1943 */     return pixelValue(value) + "px";
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   protected String pixelString(HTMLElement element, CSSStyleDeclaration.CssValue value)
/* 1367:     */   {
/* 1368:1956 */     String s = value.get(element);
/* 1369:1957 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_LENGTH_WITHOUT_PX)) {
/* 1370:1958 */       return s;
/* 1371:     */     }
/* 1372:1960 */     if (s.endsWith("px")) {
/* 1373:1961 */       return s;
/* 1374:     */     }
/* 1375:1963 */     return pixelValue(element, value) + "px";
/* 1376:     */   }
/* 1377:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.ComputedCSSStyleDeclaration
 * JD-Core Version:    0.7.0.1
 */