/*    1:     */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    5:     */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*    6:     */ import com.gargoylesoftware.htmlunit.WebAssert;
/*    7:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*    8:     */ import com.gargoylesoftware.htmlunit.WebRequest;
/*    9:     */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   10:     */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   11:     */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   12:     */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   13:     */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   14:     */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   15:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCanvasElement;
/*   16:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   17:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLHtmlElement;
/*   18:     */ import com.steadystate.css.dom.CSSValueImpl;
/*   19:     */ import com.steadystate.css.parser.CSSOMParser;
/*   20:     */ import com.steadystate.css.parser.SACParserCSS21;
/*   21:     */ import java.awt.Color;
/*   22:     */ import java.io.IOException;
/*   23:     */ import java.io.StringReader;
/*   24:     */ import java.net.URL;
/*   25:     */ import java.text.MessageFormat;
/*   26:     */ import java.text.ParseException;
/*   27:     */ import java.util.HashMap;
/*   28:     */ import java.util.LinkedHashMap;
/*   29:     */ import java.util.Map;
/*   30:     */ import java.util.SortedSet;
/*   31:     */ import java.util.TreeSet;
/*   32:     */ import java.util.regex.Matcher;
/*   33:     */ import java.util.regex.Pattern;
/*   34:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   35:     */ import net.sourceforge.htmlunit.corejs.javascript.EvaluatorException;
/*   36:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   37:     */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*   38:     */ import net.sourceforge.htmlunit.corejs.javascript.WrappedException;
/*   39:     */ import org.apache.commons.lang.math.NumberUtils;
/*   40:     */ import org.apache.commons.logging.Log;
/*   41:     */ import org.apache.commons.logging.LogFactory;
/*   42:     */ import org.w3c.css.sac.ErrorHandler;
/*   43:     */ import org.w3c.css.sac.InputSource;
/*   44:     */ 
/*   45:     */ public class CSSStyleDeclaration
/*   46:     */   extends SimpleScriptable
/*   47:     */ {
/*   48:     */   private static final String AZIMUTH = "azimuth";
/*   49:     */   private static final String BACKGROUND = "background";
/*   50:     */   private static final String BACKGROUND_ATTACHMENT = "background-attachment";
/*   51:     */   private static final String BACKGROUND_COLOR = "background-color";
/*   52:     */   private static final String BACKGROUND_IMAGE = "background-image";
/*   53:     */   private static final String BACKGROUND_POSITION = "background-position";
/*   54:     */   private static final String BACKGROUND_POSITION_X = "background-position-x";
/*   55:     */   private static final String BACKGROUND_POSITION_Y = "background-position-y";
/*   56:     */   private static final String BACKGROUND_REPEAT = "background-repeat";
/*   57:     */   private static final String BEHAVIOR = "behavior";
/*   58:     */   private static final String BORDER = "border";
/*   59:     */   private static final String BORDER_BOTTOM = "border-bottom";
/*   60:     */   private static final String BORDER_BOTTOM_COLOR = "border-bottom-color";
/*   61:     */   private static final String BORDER_BOTTOM_STYLE = "border-bottom-style";
/*   62:     */   private static final String BORDER_BOTTOM_WIDTH = "border-bottom-width";
/*   63:     */   private static final String BORDER_COLLAPSE = "border-collapse";
/*   64:     */   private static final String BORDER_COLOR = "border-color";
/*   65:     */   private static final String BORDER_LEFT = "border-left";
/*   66:     */   private static final String BORDER_LEFT_COLOR = "border-left-color";
/*   67:     */   private static final String BORDER_LEFT_STYLE = "border-left-style";
/*   68:     */   private static final String BORDER_WIDTH = "border-width";
/*   69:     */   private static final String BORDER_LEFT_WIDTH = "border-left-width";
/*   70:     */   private static final String BORDER_RIGHT = "border-right";
/*   71:     */   private static final String BORDER_RIGHT_COLOR = "border-right-color";
/*   72:     */   private static final String BORDER_RIGHT_STYLE = "border-right-style";
/*   73:     */   private static final String BORDER_RIGHT_WIDTH = "border-right-width";
/*   74:     */   private static final String BORDER_SPACING = "border-spacing";
/*   75:     */   private static final String BORDER_STYLE = "border-style";
/*   76:     */   private static final String BORDER_TOP = "border-top";
/*   77:     */   private static final String BORDER_TOP_COLOR = "border-top-color";
/*   78:     */   private static final String BORDER_TOP_STYLE = "border-top-style";
/*   79:     */   private static final String BORDER_TOP_WIDTH = "border-top-width";
/*   80:     */   private static final String BOTTOM = "bottom";
/*   81:     */   private static final String CAPTION_SIDE = "caption-side";
/*   82:     */   private static final String CLEAR = "clear";
/*   83:     */   private static final String CLIP = "clip";
/*   84:     */   private static final String COLOR = "color";
/*   85:     */   private static final String CONTENT = "content";
/*   86:     */   private static final String COUNTER_INCREMENT = "counter-increment";
/*   87:     */   private static final String COUNTER_RESET = "counter-reset";
/*   88:     */   private static final String CUE = "cue";
/*   89:     */   private static final String CUE_AFTER = "cue-after";
/*   90:     */   private static final String CUE_BEFORE = "cue-before";
/*   91:     */   private static final String CURSOR = "cursor";
/*   92:     */   private static final String DIRECTION = "direction";
/*   93:     */   private static final String DISPLAY = "display";
/*   94:     */   private static final String ELEVATION = "elevation";
/*   95:     */   private static final String EMPTY_CELLS = "empty-cells";
/*   96:     */   private static final String FILTER = "filter";
/*   97:     */   private static final String FONT = "font";
/*   98:     */   private static final String FONT_FAMILY = "font-family";
/*   99:     */   private static final String FONT_SIZE = "font-size";
/*  100:     */   private static final String FONT_SIZE_ADJUST = "font-size-adjust";
/*  101:     */   private static final String FONT_STRETCH = "font-stretch";
/*  102:     */   private static final String FONT_STYLE = "font-style";
/*  103:     */   private static final String FONT_VARIANT = "font-variant";
/*  104:     */   private static final String FONT_WEIGHT = "font-weight";
/*  105:     */   private static final String HEIGHT = "height";
/*  106:     */   private static final String IME_MODE = "ime-mode";
/*  107:     */   private static final String LAYOUT_FLOW = "layout-flow";
/*  108:     */   private static final String LAYOUT_GRID = "layout-grid";
/*  109:     */   private static final String LAYOUT_GRID_CHAR = "layout-grid-char";
/*  110:     */   private static final String LAYOUT_GRID_LINE = "layout-grid-line";
/*  111:     */   private static final String LAYOUT_GRID_MODE = "layout-grid-mode";
/*  112:     */   private static final String LAYOUT_GRID_TYPE = "layout-grid-type";
/*  113:     */   private static final String LEFT = "left";
/*  114:     */   private static final String LETTER_SPACING = "letter-spacing";
/*  115:     */   private static final String LINE_BREAK = "line-break";
/*  116:     */   private static final String LINE_HEIGHT = "line-height";
/*  117:     */   private static final String LIST_STYLE = "list-style";
/*  118:     */   private static final String LIST_STYLE_IMAGE = "list-style-image";
/*  119:     */   private static final String LIST_STYLE_POSITION = "list-style-position";
/*  120:     */   private static final String LIST_STYLE_TYPE = "list-style-type";
/*  121:     */   private static final String MARGIN_BOTTOM = "margin-bottom";
/*  122:     */   private static final String MARGIN_LEFT = "margin-left";
/*  123:     */   private static final String MARGIN_RIGHT = "margin-right";
/*  124:     */   private static final String MARGIN = "margin";
/*  125:     */   private static final String MARGIN_TOP = "margin-top";
/*  126:     */   private static final String MARKER_OFFSET = "marker-offset";
/*  127:     */   private static final String MARKS = "marks";
/*  128:     */   private static final String MAX_HEIGHT = "max-height";
/*  129:     */   private static final String MAX_WIDTH = "max-width";
/*  130:     */   private static final String MIN_HEIGHT = "min-height";
/*  131:     */   private static final String MIN_WIDTH = "min-width";
/*  132:     */   private static final String MOZ_APPEARANCE = "-moz-appearance";
/*  133:     */   private static final String MOZ_BACKGROUND_CLIP = "-moz-background-clip";
/*  134:     */   private static final String MOZ_BACKGROUND_INLINE_POLICY = "-moz-background-inline-policy";
/*  135:     */   private static final String MOZ_BACKGROUND_ORIGIN = "-moz-background-origin";
/*  136:     */   private static final String MOZ_BINDING = "-moz-binding";
/*  137:     */   private static final String MOZ_BORDER_BOTTOM_COLORS = "-moz-border-bottom-colors";
/*  138:     */   private static final String MOZ_BORDER_LEFT_COLORS = "-moz-border-left-colors";
/*  139:     */   private static final String MOZ_BORDER_RADIUS = "-moz-border-radius";
/*  140:     */   private static final String MOZ_BORDER_RADIUS_BOTTOMLEFT = "-moz-border-radius-bottomleft";
/*  141:     */   private static final String MOZ_BORDER_RADIUS_BOTTOMRIGHT = "-moz-border-radius-bottomright";
/*  142:     */   private static final String MOZ_BORDER_RADIUS_TOPLEFT = "-moz-border-radius-topleft";
/*  143:     */   private static final String MOZ_BORDER_RADIUS_TOPRIGHT = "-moz-border-radius-topright";
/*  144:     */   private static final String MOZ_BORDER_RIGHT_COLORS = "-moz-border-right-colors";
/*  145:     */   private static final String MOZ_BORDER_TOP_COLORS = "-moz-border-top-colors";
/*  146:     */   private static final String MOZ_BOX_ALIGN = "-moz-box-align";
/*  147:     */   private static final String MOZ_BOX_DIRECTION = "-moz-box-direction";
/*  148:     */   private static final String MOZ_BOX_FLEX = "-moz-box-flex";
/*  149:     */   private static final String MOZ_BOX_ORDINAL_GROUP = "-moz-box-ordinal-group";
/*  150:     */   private static final String MOZ_BOX_ORIENT = "-moz-box-orient";
/*  151:     */   private static final String MOZ_BOX_PACK = "-moz-box-pack";
/*  152:     */   private static final String MOZ_BOX_SIZING = "-moz-box-sizing";
/*  153:     */   private static final String MOZ_COLUMN_COUNT = "-moz-column-count";
/*  154:     */   private static final String MOZ_COLUMN_GAP = "-moz-column-gap";
/*  155:     */   private static final String MOZ_COLUMN_WIDTH = "-moz-column-width";
/*  156:     */   private static final String MOZ_FLOAT_EDGE = "-moz-float-edge";
/*  157:     */   private static final String MOZ_FORCE_BROKEN_IMAGE_ICON = "-moz-force-broken-image-icon";
/*  158:     */   private static final String MOZ_IMAGE_REGION = "-moz-image-region";
/*  159:     */   private static final String MOZ_MARGIN_END = "-moz-margin-end";
/*  160:     */   private static final String MOZ_MARGIN_START = "-moz-margin-start";
/*  161:     */   private static final String MOZ_OPACITY = "-moz-opacity";
/*  162:     */   private static final String MOZ_OUTLINE = "-moz-outline";
/*  163:     */   private static final String MOZ_OUTLINE_COLOR = "-moz-outline-color";
/*  164:     */   private static final String MOZ_OUTLINE_OFFSET = "-moz-outline-offset";
/*  165:     */   private static final String MOZ_OUTLINE_RADIUS = "-mz-outline-radius";
/*  166:     */   private static final String MOZ_OUTLINE_RADIUS_BOTTOMLEFT = "-moz-outline-radius-bottomleft";
/*  167:     */   private static final String MOZ_OUTLINE_RADIUS_BOTTOMRIGHT = "-moz-outline-radius-bottomright";
/*  168:     */   private static final String MOZ_OUTLINE_RADIUS_TOPLEFT = "-moz-outline-radius-topleft";
/*  169:     */   private static final String MOZ_OUTLINE_RADIUS_TOPRIGHT = "-moz-outline-radius-topright";
/*  170:     */   private static final String MOZ_OUTLINE_STYLE = "-moz-outline-style";
/*  171:     */   private static final String MOZ_OUTLINE_WIDTH = "-moz-outline-width";
/*  172:     */   private static final String MOZ_PADDING_END = "-moz-padding-end";
/*  173:     */   private static final String MOZ_PADDING_START = "-moz-padding-start";
/*  174:     */   private static final String MOZ_USER_FOCUS = "-moz-user-focus";
/*  175:     */   private static final String MOZ_USER_INPUT = "-moz-user-input";
/*  176:     */   private static final String MOZ_USER_MODIFY = "-moz-user-modify";
/*  177:     */   private static final String MOZ_USER_SELECT = "-moz-user-select";
/*  178:     */   private static final String MS_INTERPOLATION_MODE = "ms-interpolation-mode";
/*  179:     */   private static final String OPACITY = "opacity";
/*  180:     */   private static final String ORPHANS = "orphans";
/*  181:     */   private static final String OUTLINE = "outline";
/*  182:     */   private static final String OUTLINE_COLOR = "outline-color";
/*  183:     */   private static final String OUTLINE_OFFSET = "outline-offset";
/*  184:     */   private static final String OUTLINE_STYLE = "outline-style";
/*  185:     */   private static final String OUTLINE_WIDTH = "outline-width";
/*  186:     */   private static final String OVERFLOW = "overflow";
/*  187:     */   private static final String OVERFLOW_X = "overflow-x";
/*  188:     */   private static final String OVERFLOW_Y = "overflow-y";
/*  189:     */   private static final String PADDING_BOTTOM = "padding-bottom";
/*  190:     */   private static final String PADDING_LEFT = "padding-left";
/*  191:     */   private static final String PADDING_RIGHT = "padding-right";
/*  192:     */   private static final String PADDING = "padding";
/*  193:     */   private static final String PADDING_TOP = "padding-top";
/*  194:     */   private static final String PAGE = "page";
/*  195:     */   private static final String PAGE_BREAK_AFTER = "page-break-after";
/*  196:     */   private static final String PAGE_BREAK_BEFORE = "page-break-before";
/*  197:     */   private static final String PAGE_BREAK_INSIDE = "page-break-inside";
/*  198:     */   private static final String PAUSE = "pause";
/*  199:     */   private static final String PAUSE_AFTER = "pause-after";
/*  200:     */   private static final String PAUSE_BEFORE = "pause-before";
/*  201:     */   private static final String PITCH = "pitch";
/*  202:     */   private static final String PITCH_RANGE = "pitch-range";
/*  203:     */   private static final String POSITION = "position";
/*  204:     */   private static final String QUOTES = "quotes";
/*  205:     */   private static final String RICHNESS = "richness";
/*  206:     */   private static final String RIGHT = "right";
/*  207:     */   private static final String RUBY_ALIGN = "ruby-align";
/*  208:     */   private static final String RUBY_OVERHANG = "ruby-overhang";
/*  209:     */   private static final String RUBY_POSITION = "ruby-position";
/*  210:     */   private static final String SCROLLBAR3D_LIGHT_COLOR = "scrollbar3d-light-color";
/*  211:     */   private static final String SCROLLBAR_ARROW_COLOR = "scrollbar-arrow-color";
/*  212:     */   private static final String SCROLLBAR_BASE_COLOR = "scrollbar-base-color";
/*  213:     */   private static final String SCROLLBAR_DARK_SHADOW_COLOR = "scrollbar-dark-shadow-color";
/*  214:     */   private static final String SCROLLBAR_FACE_COLOR = "scrollbar-face-color";
/*  215:     */   private static final String SCROLLBAR_HIGHLIGHT_COLOR = "scrollbar-highlight-color";
/*  216:     */   private static final String SCROLLBAR_SHADOW_COLOR = "scrollbar-shadow-color";
/*  217:     */   private static final String SCROLLBAR_TRACK_COLOR = "scrollbar-track-color";
/*  218:     */   private static final String SIZE = "size";
/*  219:     */   private static final String SPEAK = "speak";
/*  220:     */   private static final String SPEAK_HEADER = "speak-header";
/*  221:     */   private static final String SPEAK_NUMERAL = "speak-numeral";
/*  222:     */   private static final String SPEAK_PUNCTUATION = "speak-punctuation";
/*  223:     */   private static final String SPEECH_RATE = "speech-rate";
/*  224:     */   private static final String STRESS = "stress";
/*  225:     */   private static final String FLOAT = "float";
/*  226:     */   private static final String TABLE_LAYOUT = "table-layout";
/*  227:     */   private static final String TEXT_ALIGN = "text-align";
/*  228:     */   private static final String TEXT_ALIGN_LAST = "text-align-last";
/*  229:     */   private static final String TEXT_AUTOSPACE = "text-autospace";
/*  230:     */   private static final String TEXT_DECORATION = "text-decoration";
/*  231:     */   private static final String TEXT_INDENT = "text-indent";
/*  232:     */   private static final String TEXT_JUSTIFY = "text-justify";
/*  233:     */   private static final String TEXT_JUSTIFY_TRIM = "text-justify-trim";
/*  234:     */   private static final String TEXT_KASHIDA = "text-kashida";
/*  235:     */   private static final String TEXT_KASHIDA_SPACE = "text-kashida-space";
/*  236:     */   private static final String TEXT_OVERFLOW = "text-overflow";
/*  237:     */   private static final String TEXT_SHADOW = "text-shadow";
/*  238:     */   private static final String TEXT_TRANSFORM = "text-transform";
/*  239:     */   private static final String TEXT_UNDERLINE_POSITION = "text-underline-position";
/*  240:     */   private static final String TOP = "top";
/*  241:     */   private static final String UNICODE_BIDI = "unicode-bidi";
/*  242:     */   private static final String VERTICAL_ALIGN = "vertical-align";
/*  243:     */   private static final String VISIBILITY = "visibility";
/*  244:     */   private static final String VOICE_FAMILY = "voice-family";
/*  245:     */   private static final String VOLUME = "volume";
/*  246:     */   private static final String WHITE_SPACE = "white-space";
/*  247:     */   private static final String WIDOWS = "widows";
/*  248:     */   private static final String WORD_BREAK = "word-break";
/*  249:     */   private static final String WORD_SPACING = "word-spacing";
/*  250:     */   private static final String WORD_WRAP = "word-wrap";
/*  251:     */   private static final String WRITING_MODE = "writing-mode";
/*  252:     */   private static final String Z_INDEX = "z-index";
/*  253:     */   private static final String ZOOM = "zoom";
/*  254:     */   protected static final String WIDTH = "width";
/*  255: 278 */   private static final Pattern VALUES_SPLIT_PATTERN = Pattern.compile("\\s+");
/*  256: 279 */   private static final Pattern TO_INT_PATTERN = Pattern.compile("(\\d+).*");
/*  257: 280 */   private static final Pattern URL_PATTERN = Pattern.compile("url\\(\\s*[\"']?(.*?)[\"']?\\s*\\)");
/*  258: 282 */   private static final Pattern POSITION_PATTERN = Pattern.compile("(\\d+\\s*(%|px|cm|mm|in|pt|pc|em|ex))\\s*(\\d+\\s*(%|px|cm|mm|in|pt|pc|em|ex)|top|bottom|center)");
/*  259: 285 */   private static final Pattern POSITION_PATTERN2 = Pattern.compile("(left|right|center)\\s*(\\d+\\s*(%|px|cm|mm|in|pt|pc|em|ex)|top|bottom|center)");
/*  260: 287 */   private static final Pattern POSITION_PATTERN3 = Pattern.compile("(top|bottom|center)\\s*(\\d+\\s*(%|px|cm|mm|in|pt|pc|em|ex)|left|right|center)");
/*  261: 290 */   private static final Log LOG = LogFactory.getLog(CSSStyleDeclaration.class);
/*  262: 291 */   private static Map<String, String> CSSColors_ = new HashMap();
/*  263: 292 */   private static Map<String, String> CamelizeCache_ = new HashMap();
/*  264:     */   
/*  265:     */   private static enum Shorthand
/*  266:     */   {
/*  267: 296 */     TOP("top"),  RIGHT("right"),  BOTTOM("bottom"),  LEFT("left");
/*  268:     */     
/*  269:     */     private final String string_;
/*  270:     */     
/*  271:     */     private Shorthand(String stringRepresentation)
/*  272:     */     {
/*  273: 304 */       this.string_ = stringRepresentation;
/*  274:     */     }
/*  275:     */     
/*  276:     */     public String toString()
/*  277:     */     {
/*  278: 309 */       return this.string_;
/*  279:     */     }
/*  280:     */   }
/*  281:     */   
/*  282: 314 */   private static final MessageFormat URL_FORMAT = new MessageFormat("url({0})");
/*  283:     */   private HTMLElement jsElement_;
/*  284:     */   private org.w3c.dom.css.CSSStyleDeclaration styleDeclaration_;
/*  285:     */   private long currentElementIndex_;
/*  286:     */   
/*  287:     */   static
/*  288:     */   {
/*  289: 326 */     CSSColors_.put("aqua", "rgb(0, 255, 255)");
/*  290: 327 */     CSSColors_.put("black", "rgb(0, 0, 0)");
/*  291: 328 */     CSSColors_.put("blue", "rgb(0, 0, 255)");
/*  292: 329 */     CSSColors_.put("fuchsia", "rgb(255, 0, 255)");
/*  293: 330 */     CSSColors_.put("gray", "rgb(128, 128, 128)");
/*  294: 331 */     CSSColors_.put("green", "rgb(0, 128, 0)");
/*  295: 332 */     CSSColors_.put("lime", "rgb(0, 255, 0)");
/*  296: 333 */     CSSColors_.put("maroon", "rgb(128, 0, 0)");
/*  297: 334 */     CSSColors_.put("navy", "rgb(0, 0, 128)");
/*  298: 335 */     CSSColors_.put("olive", "rgb(128, 128, 0)");
/*  299: 336 */     CSSColors_.put("purple", "rgb(128, 0, 128)");
/*  300: 337 */     CSSColors_.put("red", "rgb(255, 0, 0)");
/*  301: 338 */     CSSColors_.put("silver", "rgb(192, 192, 192)");
/*  302: 339 */     CSSColors_.put("teal", "rgb(0, 128, 128)");
/*  303: 340 */     CSSColors_.put("white", "rgb(255, 255, 255)");
/*  304: 341 */     CSSColors_.put("yellow", "rgb(255, 255, 0)");
/*  305:     */   }
/*  306:     */   
/*  307:     */   public CSSStyleDeclaration(HTMLElement element)
/*  308:     */   {
/*  309: 356 */     setParentScope(element.getParentScope());
/*  310: 357 */     setPrototype(getPrototype(getClass()));
/*  311: 358 */     initialize(element);
/*  312:     */   }
/*  313:     */   
/*  314:     */   CSSStyleDeclaration(Scriptable parentScope, org.w3c.dom.css.CSSStyleDeclaration styleDeclaration)
/*  315:     */   {
/*  316: 367 */     setParentScope(parentScope);
/*  317: 368 */     setPrototype(getPrototype(getClass()));
/*  318: 369 */     this.styleDeclaration_ = styleDeclaration;
/*  319:     */   }
/*  320:     */   
/*  321:     */   void initialize(HTMLElement htmlElement)
/*  322:     */   {
/*  323: 378 */     WebAssert.notNull("htmlElement", htmlElement);
/*  324: 379 */     this.jsElement_ = htmlElement;
/*  325: 380 */     setDomNode(htmlElement.getDomNodeOrNull(), false);
/*  326: 382 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_SUPPORTS_BEHAVIOR_PROPERTY)) {
/*  327: 383 */       for (StyleElement element : getStyleMap().values()) {
/*  328: 384 */         if ("behavior".equals(element.getName())) {
/*  329:     */           try
/*  330:     */           {
/*  331: 386 */             Object[] url = URL_FORMAT.parse(element.getValue());
/*  332: 387 */             if (url.length > 0)
/*  333:     */             {
/*  334: 388 */               this.jsElement_.jsxFunction_addBehavior((String)url[0]);
/*  335: 389 */               break;
/*  336:     */             }
/*  337:     */           }
/*  338:     */           catch (ParseException e)
/*  339:     */           {
/*  340: 393 */             LOG.warn("Invalid behavior: '" + element.getValue() + "'.");
/*  341:     */           }
/*  342:     */         }
/*  343:     */       }
/*  344:     */     }
/*  345:     */   }
/*  346:     */   
/*  347:     */   protected HTMLElement getElement()
/*  348:     */   {
/*  349: 405 */     return this.jsElement_;
/*  350:     */   }
/*  351:     */   
/*  352:     */   protected String getStyleAttribute(String name, Map<String, StyleElement> styleMap)
/*  353:     */   {
/*  354: 417 */     if (this.styleDeclaration_ != null) {
/*  355: 418 */       return this.styleDeclaration_.getPropertyValue(name);
/*  356:     */     }
/*  357: 420 */     Map<String, StyleElement> style = styleMap;
/*  358: 421 */     if (null == style) {
/*  359: 422 */       style = getStyleMap();
/*  360:     */     }
/*  361: 424 */     StyleElement element = (StyleElement)style.get(name);
/*  362: 425 */     if ((element != null) && (element.getValue() != null)) {
/*  363: 426 */       return element.getValue();
/*  364:     */     }
/*  365: 428 */     return "";
/*  366:     */   }
/*  367:     */   
/*  368:     */   private String getStyleAttribute(String name1, String name2, Shorthand shorthand)
/*  369:     */   {
/*  370:     */     String value;
/*  371:     */     String value;
/*  372: 453 */     if (this.styleDeclaration_ != null)
/*  373:     */     {
/*  374: 454 */       String value1 = this.styleDeclaration_.getPropertyValue(name1);
/*  375: 455 */       String value2 = this.styleDeclaration_.getPropertyValue(name2);
/*  376: 457 */       if (("".equals(value1)) && ("".equals(value2))) {
/*  377: 458 */         return "";
/*  378:     */       }
/*  379: 460 */       if ((!"".equals(value1)) && ("".equals(value2))) {
/*  380: 461 */         return value1;
/*  381:     */       }
/*  382: 463 */       value = value2;
/*  383:     */     }
/*  384:     */     else
/*  385:     */     {
/*  386: 466 */       Map<String, StyleElement> styleMap = getStyleMap();
/*  387: 467 */       StyleElement element1 = (StyleElement)styleMap.get(name1);
/*  388: 468 */       StyleElement element2 = (StyleElement)styleMap.get(name2);
/*  389: 470 */       if ((element1 == null) && (element2 == null)) {
/*  390: 471 */         return "";
/*  391:     */       }
/*  392: 473 */       if ((element1 != null) && (element2 == null)) {
/*  393: 474 */         return element1.getValue();
/*  394:     */       }
/*  395:     */       String value;
/*  396: 476 */       if ((element1 == null) && (element2 != null))
/*  397:     */       {
/*  398: 477 */         value = element2.getValue();
/*  399:     */       }
/*  400:     */       else
/*  401:     */       {
/*  402: 479 */         if (element1.getIndex() > element2.getIndex()) {
/*  403: 480 */           return element1.getValue();
/*  404:     */         }
/*  405: 483 */         value = element2.getValue();
/*  406:     */       }
/*  407:     */     }
/*  408: 487 */     String[] values = VALUES_SPLIT_PATTERN.split(value, 0);
/*  409: 488 */     switch (1.$SwitchMap$com$gargoylesoftware$htmlunit$javascript$host$css$CSSStyleDeclaration$Shorthand[shorthand.ordinal()])
/*  410:     */     {
/*  411:     */     case 1: 
/*  412: 490 */       return values[0];
/*  413:     */     case 2: 
/*  414: 492 */       if (values.length > 1) {
/*  415: 493 */         return values[1];
/*  416:     */       }
/*  417: 495 */       return values[0];
/*  418:     */     case 3: 
/*  419: 497 */       if (values.length > 2) {
/*  420: 498 */         return values[2];
/*  421:     */       }
/*  422: 500 */       return values[0];
/*  423:     */     case 4: 
/*  424: 502 */       if (values.length > 3) {
/*  425: 503 */         return values[3];
/*  426:     */       }
/*  427: 505 */       if (values.length > 1) {
/*  428: 506 */         return values[1];
/*  429:     */       }
/*  430: 509 */       return values[0];
/*  431:     */     }
/*  432: 512 */     throw new IllegalStateException("Unknown shorthand value: " + shorthand);
/*  433:     */   }
/*  434:     */   
/*  435:     */   protected void setStyleAttribute(String name, String newValue)
/*  436:     */   {
/*  437: 522 */     if (this.styleDeclaration_ != null)
/*  438:     */     {
/*  439: 523 */       this.styleDeclaration_.setProperty(name, newValue, null);
/*  440: 524 */       return;
/*  441:     */     }
/*  442: 527 */     replaceStyleAttribute(name, newValue);
/*  443:     */   }
/*  444:     */   
/*  445:     */   private void replaceStyleAttribute(String name, String value)
/*  446:     */   {
/*  447: 538 */     if (org.apache.commons.lang.StringUtils.isBlank(value))
/*  448:     */     {
/*  449: 539 */       removeStyleAttribute(name);
/*  450:     */     }
/*  451:     */     else
/*  452:     */     {
/*  453: 542 */       Map<String, StyleElement> styleMap = getStyleMap();
/*  454: 543 */       StyleElement old = (StyleElement)styleMap.get(name);
/*  455:     */       long index;
/*  456:     */       long index;
/*  457: 545 */       if (old != null) {
/*  458: 546 */         index = old.getIndex();
/*  459:     */       } else {
/*  460: 549 */         index = getCurrentElementIndex();
/*  461:     */       }
/*  462: 551 */       StyleElement element = new StyleElement(name, value, index);
/*  463: 552 */       styleMap.put(name, element);
/*  464: 553 */       writeToElement(styleMap);
/*  465:     */     }
/*  466:     */   }
/*  467:     */   
/*  468:     */   private void removeStyleAttribute(String name)
/*  469:     */   {
/*  470: 562 */     if (null != this.styleDeclaration_)
/*  471:     */     {
/*  472: 563 */       this.styleDeclaration_.removeProperty(name);
/*  473: 564 */       return;
/*  474:     */     }
/*  475: 567 */     Map<String, StyleElement> styleMap = getStyleMap();
/*  476: 568 */     if (!styleMap.containsKey(name)) {
/*  477: 569 */       return;
/*  478:     */     }
/*  479: 571 */     styleMap.remove(name);
/*  480: 572 */     writeToElement(styleMap);
/*  481:     */   }
/*  482:     */   
/*  483:     */   protected Map<String, StyleElement> getStyleMap()
/*  484:     */   {
/*  485: 582 */     Map<String, StyleElement> styleMap = new LinkedHashMap();
/*  486: 583 */     String styleAttribute = this.jsElement_.getDomNodeOrDie().getAttribute("style");
/*  487: 584 */     for (String token : org.apache.commons.lang.StringUtils.split(styleAttribute, ';'))
/*  488:     */     {
/*  489: 585 */       int index = token.indexOf(":");
/*  490: 586 */       if (index != -1)
/*  491:     */       {
/*  492: 587 */         String key = token.substring(0, index).trim().toLowerCase();
/*  493: 588 */         String value = token.substring(index + 1).trim();
/*  494: 589 */         StyleElement element = new StyleElement(key, value, getCurrentElementIndex());
/*  495: 590 */         styleMap.put(key, element);
/*  496:     */       }
/*  497:     */     }
/*  498: 593 */     return styleMap;
/*  499:     */   }
/*  500:     */   
/*  501:     */   private void writeToElement(Map<String, StyleElement> styleMap)
/*  502:     */   {
/*  503: 597 */     StringBuilder buffer = new StringBuilder();
/*  504: 598 */     SortedSet<StyleElement> sortedValues = new TreeSet(styleMap.values());
/*  505: 599 */     for (StyleElement e : sortedValues)
/*  506:     */     {
/*  507: 600 */       if (buffer.length() > 0) {
/*  508: 601 */         buffer.append(" ");
/*  509:     */       }
/*  510: 603 */       buffer.append(e.getName());
/*  511: 604 */       buffer.append(": ");
/*  512: 605 */       buffer.append(e.getValue());
/*  513: 606 */       buffer.append(";");
/*  514:     */     }
/*  515: 608 */     this.jsElement_.getDomNodeOrDie().setAttribute("style", buffer.toString());
/*  516:     */   }
/*  517:     */   
/*  518:     */   protected long getCurrentElementIndex()
/*  519:     */   {
/*  520: 620 */     return this.currentElementIndex_++;
/*  521:     */   }
/*  522:     */   
/*  523:     */   protected static String camelize(String string)
/*  524:     */   {
/*  525: 630 */     if (string == null) {
/*  526: 631 */       return null;
/*  527:     */     }
/*  528: 634 */     String result = (String)CamelizeCache_.get(string);
/*  529: 635 */     if (null != result) {
/*  530: 636 */       return result;
/*  531:     */     }
/*  532: 639 */     int pos = string.indexOf('-');
/*  533: 640 */     if ((pos == -1) || (pos >= string.length() - 1)) {
/*  534: 641 */       return string;
/*  535:     */     }
/*  536: 644 */     StringBuilder buffer = new StringBuilder(string);
/*  537: 645 */     buffer.deleteCharAt(pos);
/*  538: 646 */     buffer.setCharAt(pos, Character.toUpperCase(buffer.charAt(pos)));
/*  539: 648 */     for (int i = pos + 1; i < buffer.length() - 1; i++) {
/*  540: 649 */       if (buffer.charAt(i) == '-')
/*  541:     */       {
/*  542: 650 */         buffer.deleteCharAt(i);
/*  543: 651 */         buffer.setCharAt(i, Character.toUpperCase(buffer.charAt(i)));
/*  544:     */       }
/*  545:     */     }
/*  546: 654 */     result = buffer.toString();
/*  547: 655 */     CamelizeCache_.put(string, result);
/*  548:     */     
/*  549: 657 */     return result;
/*  550:     */   }
/*  551:     */   
/*  552:     */   public String jsxGet_azimuth()
/*  553:     */   {
/*  554: 665 */     return getStyleAttribute("azimuth", null);
/*  555:     */   }
/*  556:     */   
/*  557:     */   public void jsxSet_azimuth(String azimuth)
/*  558:     */   {
/*  559: 673 */     setStyleAttribute("azimuth", azimuth);
/*  560:     */   }
/*  561:     */   
/*  562:     */   public String jsxGet_background()
/*  563:     */   {
/*  564: 681 */     return getStyleAttribute("background", null);
/*  565:     */   }
/*  566:     */   
/*  567:     */   public void jsxSet_background(String background)
/*  568:     */   {
/*  569: 689 */     setStyleAttribute("background", background);
/*  570:     */   }
/*  571:     */   
/*  572:     */   public String jsxGet_backgroundAttachment()
/*  573:     */   {
/*  574: 697 */     Map<String, StyleElement> style = null;
/*  575: 698 */     if (this.styleDeclaration_ == null) {
/*  576: 699 */       style = getStyleMap();
/*  577:     */     }
/*  578: 702 */     String value = getStyleAttribute("background-attachment", style);
/*  579: 703 */     if (org.apache.commons.lang.StringUtils.isBlank(value))
/*  580:     */     {
/*  581: 704 */       String bg = getStyleAttribute("background", style);
/*  582: 705 */       if (org.apache.commons.lang.StringUtils.isNotBlank(bg))
/*  583:     */       {
/*  584: 706 */         value = findAttachment(bg);
/*  585: 707 */         if (value == null) {
/*  586: 708 */           return "scroll";
/*  587:     */         }
/*  588: 710 */         return value;
/*  589:     */       }
/*  590: 712 */       return "";
/*  591:     */     }
/*  592: 715 */     return value;
/*  593:     */   }
/*  594:     */   
/*  595:     */   public void jsxSet_backgroundAttachment(String backgroundAttachment)
/*  596:     */   {
/*  597: 723 */     setStyleAttribute("background-attachment", backgroundAttachment);
/*  598:     */   }
/*  599:     */   
/*  600:     */   public String jsxGet_backgroundColor()
/*  601:     */   {
/*  602: 731 */     Map<String, StyleElement> style = null;
/*  603: 732 */     if (this.styleDeclaration_ == null) {
/*  604: 733 */       style = getStyleMap();
/*  605:     */     }
/*  606: 736 */     String value = getStyleAttribute("background-color", style);
/*  607: 737 */     if (org.apache.commons.lang.StringUtils.isBlank(value))
/*  608:     */     {
/*  609: 738 */       String bg = getStyleAttribute("background", style);
/*  610: 739 */       if (org.apache.commons.lang.StringUtils.isBlank(bg)) {
/*  611: 740 */         return "";
/*  612:     */       }
/*  613: 742 */       value = findColor(bg);
/*  614: 743 */       if (value == null) {
/*  615: 744 */         return "transparent";
/*  616:     */       }
/*  617: 746 */       return value;
/*  618:     */     }
/*  619: 748 */     if (org.apache.commons.lang.StringUtils.isBlank(value)) {
/*  620: 749 */       return "";
/*  621:     */     }
/*  622: 751 */     return value;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public void jsxSet_backgroundColor(String backgroundColor)
/*  626:     */   {
/*  627: 759 */     setStyleAttribute("background-color", backgroundColor);
/*  628:     */   }
/*  629:     */   
/*  630:     */   public String jsxGet_backgroundImage()
/*  631:     */   {
/*  632: 767 */     Map<String, StyleElement> style = null;
/*  633: 768 */     if (this.styleDeclaration_ == null) {
/*  634: 769 */       style = getStyleMap();
/*  635:     */     }
/*  636: 772 */     String value = getStyleAttribute("background-image", style);
/*  637: 773 */     if (org.apache.commons.lang.StringUtils.isBlank(value))
/*  638:     */     {
/*  639: 774 */       String bg = getStyleAttribute("background", style);
/*  640: 775 */       if (org.apache.commons.lang.StringUtils.isNotBlank(bg))
/*  641:     */       {
/*  642: 776 */         value = findImageUrl(bg);
/*  643: 777 */         if (value == null) {
/*  644: 778 */           return "none";
/*  645:     */         }
/*  646: 780 */         return value;
/*  647:     */       }
/*  648: 782 */       return "";
/*  649:     */     }
/*  650: 785 */     return value;
/*  651:     */   }
/*  652:     */   
/*  653:     */   public void jsxSet_backgroundImage(String backgroundImage)
/*  654:     */   {
/*  655: 793 */     setStyleAttribute("background-image", backgroundImage);
/*  656:     */   }
/*  657:     */   
/*  658:     */   public String jsxGet_backgroundPosition()
/*  659:     */   {
/*  660: 801 */     Map<String, StyleElement> style = null;
/*  661: 802 */     if (this.styleDeclaration_ == null) {
/*  662: 803 */       style = getStyleMap();
/*  663:     */     }
/*  664: 806 */     String value = getStyleAttribute("background-position", style);
/*  665: 807 */     if (value == null) {
/*  666: 808 */       return null;
/*  667:     */     }
/*  668: 810 */     if (org.apache.commons.lang.StringUtils.isBlank(value))
/*  669:     */     {
/*  670: 811 */       String bg = getStyleAttribute("background", style);
/*  671: 812 */       if (bg == null) {
/*  672: 813 */         return null;
/*  673:     */       }
/*  674: 815 */       if (org.apache.commons.lang.StringUtils.isNotBlank(bg))
/*  675:     */       {
/*  676: 816 */         value = findPosition(bg);
/*  677: 817 */         if (value == null) {
/*  678: 818 */           return "0% 0%";
/*  679:     */         }
/*  680: 820 */         return value;
/*  681:     */       }
/*  682: 822 */       return "";
/*  683:     */     }
/*  684: 825 */     return value;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public void jsxSet_backgroundPosition(String backgroundPosition)
/*  688:     */   {
/*  689: 833 */     setStyleAttribute("background-position", backgroundPosition);
/*  690:     */   }
/*  691:     */   
/*  692:     */   public String jsxGet_backgroundPositionX()
/*  693:     */   {
/*  694: 841 */     return getStyleAttribute("background-position-x", null);
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void jsxSet_backgroundPositionX(String backgroundPositionX)
/*  698:     */   {
/*  699: 849 */     setStyleAttribute("background-position-x", backgroundPositionX);
/*  700:     */   }
/*  701:     */   
/*  702:     */   public String jsxGet_backgroundPositionY()
/*  703:     */   {
/*  704: 857 */     return getStyleAttribute("background-position-y", null);
/*  705:     */   }
/*  706:     */   
/*  707:     */   public void jsxSet_backgroundPositionY(String backgroundPositionY)
/*  708:     */   {
/*  709: 865 */     setStyleAttribute("background-position-y", backgroundPositionY);
/*  710:     */   }
/*  711:     */   
/*  712:     */   public String jsxGet_backgroundRepeat()
/*  713:     */   {
/*  714: 873 */     Map<String, StyleElement> style = null;
/*  715: 874 */     if (this.styleDeclaration_ == null) {
/*  716: 875 */       style = getStyleMap();
/*  717:     */     }
/*  718: 878 */     String value = getStyleAttribute("background-repeat", style);
/*  719: 879 */     if (org.apache.commons.lang.StringUtils.isBlank(value))
/*  720:     */     {
/*  721: 880 */       String bg = getStyleAttribute("background", style);
/*  722: 881 */       if (org.apache.commons.lang.StringUtils.isNotBlank(bg))
/*  723:     */       {
/*  724: 882 */         value = findRepeat(bg);
/*  725: 883 */         if (value == null) {
/*  726: 884 */           return "repeat";
/*  727:     */         }
/*  728: 886 */         return value;
/*  729:     */       }
/*  730: 888 */       return "";
/*  731:     */     }
/*  732: 891 */     return value;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void jsxSet_backgroundRepeat(String backgroundRepeat)
/*  736:     */   {
/*  737: 899 */     setStyleAttribute("background-repeat", backgroundRepeat);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public String jsxGet_behavior()
/*  741:     */   {
/*  742: 907 */     return getStyleAttribute("behavior", null);
/*  743:     */   }
/*  744:     */   
/*  745:     */   public void jsxSet_behavior(String behavior)
/*  746:     */   {
/*  747: 915 */     setStyleAttribute("behavior", behavior);
/*  748: 916 */     this.jsElement_.jsxFunction_removeBehavior(0);
/*  749: 917 */     this.jsElement_.jsxFunction_removeBehavior(1);
/*  750: 918 */     this.jsElement_.jsxFunction_removeBehavior(2);
/*  751: 919 */     if (behavior.length() != 0) {
/*  752:     */       try
/*  753:     */       {
/*  754: 921 */         Object[] url = URL_FORMAT.parse(behavior);
/*  755: 922 */         if (url.length > 0) {
/*  756: 923 */           this.jsElement_.jsxFunction_addBehavior((String)url[0]);
/*  757:     */         }
/*  758:     */       }
/*  759:     */       catch (ParseException e)
/*  760:     */       {
/*  761: 927 */         LOG.warn("Invalid behavior: '" + behavior + "'.");
/*  762:     */       }
/*  763:     */     }
/*  764:     */   }
/*  765:     */   
/*  766:     */   public String jsxGet_border()
/*  767:     */   {
/*  768: 937 */     return getStyleAttribute("border", null);
/*  769:     */   }
/*  770:     */   
/*  771:     */   public void jsxSet_border(String border)
/*  772:     */   {
/*  773: 945 */     setStyleAttribute("border", border);
/*  774:     */   }
/*  775:     */   
/*  776:     */   public String jsxGet_borderBottom()
/*  777:     */   {
/*  778: 953 */     return getStyleAttribute("border-bottom", null);
/*  779:     */   }
/*  780:     */   
/*  781:     */   public void jsxSet_borderBottom(String borderBottom)
/*  782:     */   {
/*  783: 961 */     setStyleAttribute("border-bottom", borderBottom);
/*  784:     */   }
/*  785:     */   
/*  786:     */   public String jsxGet_borderBottomColor()
/*  787:     */   {
/*  788: 969 */     Map<String, StyleElement> style = null;
/*  789: 970 */     if (this.styleDeclaration_ == null) {
/*  790: 971 */       style = getStyleMap();
/*  791:     */     }
/*  792: 974 */     String value = getStyleAttribute("border-bottom-color", style);
/*  793: 975 */     if (value.length() == 0)
/*  794:     */     {
/*  795: 976 */       value = findColor(getStyleAttribute("border-bottom", style));
/*  796: 977 */       if (value == null) {
/*  797: 978 */         value = findColor(getStyleAttribute("border", style));
/*  798:     */       }
/*  799: 980 */       if (value == null) {
/*  800: 981 */         value = "";
/*  801:     */       }
/*  802:     */     }
/*  803: 984 */     return value;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public void jsxSet_borderBottomColor(String borderBottomColor)
/*  807:     */   {
/*  808: 992 */     setStyleAttribute("border-bottom-color", borderBottomColor);
/*  809:     */   }
/*  810:     */   
/*  811:     */   public String jsxGet_borderBottomStyle()
/*  812:     */   {
/*  813:1000 */     Map<String, StyleElement> style = null;
/*  814:1001 */     if (this.styleDeclaration_ == null) {
/*  815:1002 */       style = getStyleMap();
/*  816:     */     }
/*  817:1005 */     String value = getStyleAttribute("border-bottom-style", style);
/*  818:1006 */     if (value.length() == 0)
/*  819:     */     {
/*  820:1007 */       value = findBorderStyle(getStyleAttribute("border-bottom", style));
/*  821:1008 */       if (value == null) {
/*  822:1009 */         value = findBorderStyle(getStyleAttribute("border", style));
/*  823:     */       }
/*  824:1011 */       if (value == null) {
/*  825:1012 */         value = "";
/*  826:     */       }
/*  827:     */     }
/*  828:1015 */     return value;
/*  829:     */   }
/*  830:     */   
/*  831:     */   public void jsxSet_borderBottomStyle(String borderBottomStyle)
/*  832:     */   {
/*  833:1023 */     setStyleAttribute("border-bottom-style", borderBottomStyle);
/*  834:     */   }
/*  835:     */   
/*  836:     */   public String jsxGet_borderBottomWidth()
/*  837:     */   {
/*  838:1031 */     return getBorderWidth(Shorthand.BOTTOM);
/*  839:     */   }
/*  840:     */   
/*  841:     */   public void jsxSet_borderBottomWidth(String borderBottomWidth)
/*  842:     */   {
/*  843:1039 */     setStyleAttributePixelInt("border-bottom-width", borderBottomWidth);
/*  844:     */   }
/*  845:     */   
/*  846:     */   public String jsxGet_borderCollapse()
/*  847:     */   {
/*  848:1047 */     return getStyleAttribute("border-collapse", null);
/*  849:     */   }
/*  850:     */   
/*  851:     */   public void jsxSet_borderCollapse(String borderCollapse)
/*  852:     */   {
/*  853:1055 */     setStyleAttribute("border-collapse", borderCollapse);
/*  854:     */   }
/*  855:     */   
/*  856:     */   public String jsxGet_borderColor()
/*  857:     */   {
/*  858:1063 */     return getStyleAttribute("border-color", null);
/*  859:     */   }
/*  860:     */   
/*  861:     */   public void jsxSet_borderColor(String borderColor)
/*  862:     */   {
/*  863:1071 */     setStyleAttribute("border-color", borderColor);
/*  864:     */   }
/*  865:     */   
/*  866:     */   public String jsxGet_borderLeft()
/*  867:     */   {
/*  868:1079 */     return getStyleAttribute("border-left", null);
/*  869:     */   }
/*  870:     */   
/*  871:     */   public void jsxSet_borderLeft(String borderLeft)
/*  872:     */   {
/*  873:1087 */     setStyleAttribute("border-left", borderLeft);
/*  874:     */   }
/*  875:     */   
/*  876:     */   public String jsxGet_borderLeftColor()
/*  877:     */   {
/*  878:1095 */     Map<String, StyleElement> style = null;
/*  879:1096 */     if (this.styleDeclaration_ == null) {
/*  880:1097 */       style = getStyleMap();
/*  881:     */     }
/*  882:1100 */     String value = getStyleAttribute("border-left-color", style);
/*  883:1101 */     if (value.length() == 0)
/*  884:     */     {
/*  885:1102 */       value = findColor(getStyleAttribute("border-left", style));
/*  886:1103 */       if (value == null) {
/*  887:1104 */         value = findColor(getStyleAttribute("border", style));
/*  888:     */       }
/*  889:1106 */       if (value == null) {
/*  890:1107 */         value = "";
/*  891:     */       }
/*  892:     */     }
/*  893:1110 */     return value;
/*  894:     */   }
/*  895:     */   
/*  896:     */   public void jsxSet_borderLeftColor(String borderLeftColor)
/*  897:     */   {
/*  898:1118 */     setStyleAttribute("border-left-color", borderLeftColor);
/*  899:     */   }
/*  900:     */   
/*  901:     */   public String jsxGet_borderLeftStyle()
/*  902:     */   {
/*  903:1126 */     Map<String, StyleElement> style = null;
/*  904:1127 */     if (this.styleDeclaration_ == null) {
/*  905:1128 */       style = getStyleMap();
/*  906:     */     }
/*  907:1131 */     String value = getStyleAttribute("border-left-style", style);
/*  908:1132 */     if (value.length() == 0)
/*  909:     */     {
/*  910:1133 */       value = findBorderStyle(getStyleAttribute("border-left", style));
/*  911:1134 */       if (value == null) {
/*  912:1135 */         value = findBorderStyle(getStyleAttribute("border", style));
/*  913:     */       }
/*  914:1137 */       if (value == null) {
/*  915:1138 */         value = "";
/*  916:     */       }
/*  917:     */     }
/*  918:1141 */     return value;
/*  919:     */   }
/*  920:     */   
/*  921:     */   public void jsxSet_borderLeftStyle(String borderLeftStyle)
/*  922:     */   {
/*  923:1149 */     setStyleAttribute("border-left-style", borderLeftStyle);
/*  924:     */   }
/*  925:     */   
/*  926:     */   public String jsxGet_borderLeftWidth()
/*  927:     */   {
/*  928:1157 */     return getBorderWidth(Shorthand.LEFT);
/*  929:     */   }
/*  930:     */   
/*  931:     */   private String getBorderWidth(Shorthand side)
/*  932:     */   {
/*  933:1167 */     Map<String, StyleElement> style = null;
/*  934:1168 */     if (this.styleDeclaration_ == null) {
/*  935:1169 */       style = getStyleMap();
/*  936:     */     }
/*  937:1172 */     String value = getStyleAttribute("border-" + side + "-width", style);
/*  938:1173 */     if (value.length() == 0)
/*  939:     */     {
/*  940:1174 */       value = findBorderWidth(getStyleAttribute("border-" + side, style));
/*  941:1175 */       if (value == null)
/*  942:     */       {
/*  943:1176 */         String borderWidth = getStyleAttribute("border-width", style);
/*  944:1177 */         if (!org.apache.commons.lang.StringUtils.isEmpty(borderWidth))
/*  945:     */         {
/*  946:1178 */           String[] values = VALUES_SPLIT_PATTERN.split(borderWidth, 0);
/*  947:1179 */           if (values.length > side.ordinal()) {
/*  948:1180 */             value = values[side.ordinal()];
/*  949:     */           }
/*  950:     */         }
/*  951:     */       }
/*  952:1184 */       if (value == null) {
/*  953:1185 */         value = findBorderWidth(getStyleAttribute("border", style));
/*  954:     */       }
/*  955:1187 */       if (value == null) {
/*  956:1188 */         value = "";
/*  957:     */       }
/*  958:     */     }
/*  959:1191 */     return value;
/*  960:     */   }
/*  961:     */   
/*  962:     */   public void jsxSet_borderLeftWidth(String borderLeftWidth)
/*  963:     */   {
/*  964:1199 */     setStyleAttributePixelInt("border-left-width", borderLeftWidth);
/*  965:     */   }
/*  966:     */   
/*  967:     */   public String jsxGet_borderRight()
/*  968:     */   {
/*  969:1207 */     return getStyleAttribute("border-right", null);
/*  970:     */   }
/*  971:     */   
/*  972:     */   public void jsxSet_borderRight(String borderRight)
/*  973:     */   {
/*  974:1215 */     setStyleAttribute("border-right", borderRight);
/*  975:     */   }
/*  976:     */   
/*  977:     */   public String jsxGet_borderRightColor()
/*  978:     */   {
/*  979:1223 */     Map<String, StyleElement> style = null;
/*  980:1224 */     if (this.styleDeclaration_ == null) {
/*  981:1225 */       style = getStyleMap();
/*  982:     */     }
/*  983:1228 */     String value = getStyleAttribute("border-right-color", style);
/*  984:1229 */     if (value.length() == 0)
/*  985:     */     {
/*  986:1230 */       value = findColor(getStyleAttribute("border-right", style));
/*  987:1231 */       if (value == null) {
/*  988:1232 */         value = findColor(getStyleAttribute("border", style));
/*  989:     */       }
/*  990:1234 */       if (value == null) {
/*  991:1235 */         value = "";
/*  992:     */       }
/*  993:     */     }
/*  994:1238 */     return value;
/*  995:     */   }
/*  996:     */   
/*  997:     */   public void jsxSet_borderRightColor(String borderRightColor)
/*  998:     */   {
/*  999:1246 */     setStyleAttribute("border-right-color", borderRightColor);
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public String jsxGet_borderRightStyle()
/* 1003:     */   {
/* 1004:1254 */     Map<String, StyleElement> style = null;
/* 1005:1255 */     if (this.styleDeclaration_ == null) {
/* 1006:1256 */       style = getStyleMap();
/* 1007:     */     }
/* 1008:1259 */     String value = getStyleAttribute("border-right-style", style);
/* 1009:1260 */     if (value.length() == 0)
/* 1010:     */     {
/* 1011:1261 */       value = findBorderStyle(getStyleAttribute("border-right", style));
/* 1012:1262 */       if (value == null) {
/* 1013:1263 */         value = findBorderStyle(getStyleAttribute("border", style));
/* 1014:     */       }
/* 1015:1265 */       if (value == null) {
/* 1016:1266 */         value = "";
/* 1017:     */       }
/* 1018:     */     }
/* 1019:1269 */     return value;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public void jsxSet_borderRightStyle(String borderRightStyle)
/* 1023:     */   {
/* 1024:1277 */     setStyleAttribute("border-right-style", borderRightStyle);
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public String jsxGet_borderRightWidth()
/* 1028:     */   {
/* 1029:1285 */     return getBorderWidth(Shorthand.RIGHT);
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   public void jsxSet_borderRightWidth(String borderRightWidth)
/* 1033:     */   {
/* 1034:1293 */     setStyleAttributePixelInt("border-right-width", borderRightWidth);
/* 1035:     */   }
/* 1036:     */   
/* 1037:     */   public String jsxGet_borderSpacing()
/* 1038:     */   {
/* 1039:1301 */     return getStyleAttribute("border-spacing", null);
/* 1040:     */   }
/* 1041:     */   
/* 1042:     */   public void jsxSet_borderSpacing(String borderSpacing)
/* 1043:     */   {
/* 1044:1309 */     setStyleAttribute("border-spacing", borderSpacing);
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public String jsxGet_borderStyle()
/* 1048:     */   {
/* 1049:1317 */     return getStyleAttribute("border-style", null);
/* 1050:     */   }
/* 1051:     */   
/* 1052:     */   public void jsxSet_borderStyle(String borderStyle)
/* 1053:     */   {
/* 1054:1325 */     setStyleAttribute("border-style", borderStyle);
/* 1055:     */   }
/* 1056:     */   
/* 1057:     */   public String jsxGet_borderTop()
/* 1058:     */   {
/* 1059:1333 */     return getStyleAttribute("border-top", null);
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public void jsxSet_borderTop(String borderTop)
/* 1063:     */   {
/* 1064:1341 */     setStyleAttribute("border-top", borderTop);
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public String jsxGet_borderTopColor()
/* 1068:     */   {
/* 1069:1349 */     Map<String, StyleElement> style = null;
/* 1070:1350 */     if (this.styleDeclaration_ == null) {
/* 1071:1351 */       style = getStyleMap();
/* 1072:     */     }
/* 1073:1354 */     String value = getStyleAttribute("border-top-color", style);
/* 1074:1355 */     if (value.length() == 0)
/* 1075:     */     {
/* 1076:1356 */       value = findColor(getStyleAttribute("border-top", style));
/* 1077:1357 */       if (value == null) {
/* 1078:1358 */         value = findColor(getStyleAttribute("border", style));
/* 1079:     */       }
/* 1080:1360 */       if (value == null) {
/* 1081:1361 */         value = "";
/* 1082:     */       }
/* 1083:     */     }
/* 1084:1364 */     return value;
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   public void jsxSet_borderTopColor(String borderTopColor)
/* 1088:     */   {
/* 1089:1372 */     setStyleAttribute("border-top-color", borderTopColor);
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public String jsxGet_borderTopStyle()
/* 1093:     */   {
/* 1094:1380 */     Map<String, StyleElement> style = null;
/* 1095:1381 */     if (this.styleDeclaration_ == null) {
/* 1096:1382 */       style = getStyleMap();
/* 1097:     */     }
/* 1098:1385 */     String value = getStyleAttribute("border-top-style", style);
/* 1099:1386 */     if (value.length() == 0)
/* 1100:     */     {
/* 1101:1387 */       value = findBorderStyle(getStyleAttribute("border-top", style));
/* 1102:1388 */       if (value == null) {
/* 1103:1389 */         value = findBorderStyle(getStyleAttribute("border", style));
/* 1104:     */       }
/* 1105:1391 */       if (value == null) {
/* 1106:1392 */         value = "";
/* 1107:     */       }
/* 1108:     */     }
/* 1109:1395 */     return value;
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   public void jsxSet_borderTopStyle(String borderTopStyle)
/* 1113:     */   {
/* 1114:1403 */     setStyleAttribute("border-top-style", borderTopStyle);
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public String jsxGet_borderTopWidth()
/* 1118:     */   {
/* 1119:1411 */     return getBorderWidth(Shorthand.TOP);
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public void jsxSet_borderTopWidth(String borderTopWidth)
/* 1123:     */   {
/* 1124:1419 */     setStyleAttributePixelInt("border-top-width", borderTopWidth);
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public String jsxGet_borderWidth()
/* 1128:     */   {
/* 1129:1427 */     return getStyleAttribute("border-width", null);
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   public void jsxSet_borderWidth(String borderWidth)
/* 1133:     */   {
/* 1134:1435 */     setStyleAttribute("border-width", borderWidth);
/* 1135:     */   }
/* 1136:     */   
/* 1137:     */   public String jsxGet_bottom()
/* 1138:     */   {
/* 1139:1443 */     return getStyleAttribute("bottom", null);
/* 1140:     */   }
/* 1141:     */   
/* 1142:     */   public void jsxSet_bottom(String bottom)
/* 1143:     */   {
/* 1144:1451 */     setStyleAttributePixelInt("bottom", bottom);
/* 1145:     */   }
/* 1146:     */   
/* 1147:     */   public String jsxGet_captionSide()
/* 1148:     */   {
/* 1149:1459 */     return getStyleAttribute("caption-side", null);
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   public void jsxSet_captionSide(String captionSide)
/* 1153:     */   {
/* 1154:1467 */     setStyleAttribute("caption-side", captionSide);
/* 1155:     */   }
/* 1156:     */   
/* 1157:     */   public String jsxGet_clear()
/* 1158:     */   {
/* 1159:1475 */     return getStyleAttribute("clear", null);
/* 1160:     */   }
/* 1161:     */   
/* 1162:     */   public void jsxSet_clear(String clear)
/* 1163:     */   {
/* 1164:1483 */     setStyleAttribute("clear", clear);
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public String jsxGet_clip()
/* 1168:     */   {
/* 1169:1491 */     return getStyleAttribute("clip", null);
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public void jsxSet_clip(String clip)
/* 1173:     */   {
/* 1174:1499 */     setStyleAttribute("clip", clip);
/* 1175:     */   }
/* 1176:     */   
/* 1177:     */   public String jsxGet_color()
/* 1178:     */   {
/* 1179:1507 */     return getStyleAttribute("color", null);
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   public void jsxSet_color(String color)
/* 1183:     */   {
/* 1184:1515 */     setStyleAttribute("color", color);
/* 1185:     */   }
/* 1186:     */   
/* 1187:     */   public String jsxGet_content()
/* 1188:     */   {
/* 1189:1523 */     return getStyleAttribute("content", null);
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   public void jsxSet_content(String content)
/* 1193:     */   {
/* 1194:1531 */     setStyleAttribute("content", content);
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   public String jsxGet_counterIncrement()
/* 1198:     */   {
/* 1199:1539 */     return getStyleAttribute("counter-increment", null);
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public void jsxSet_counterIncrement(String counterIncrement)
/* 1203:     */   {
/* 1204:1547 */     setStyleAttribute("counter-increment", counterIncrement);
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public String jsxGet_counterReset()
/* 1208:     */   {
/* 1209:1555 */     return getStyleAttribute("counter-reset", null);
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public void jsxSet_counterReset(String counterReset)
/* 1213:     */   {
/* 1214:1563 */     setStyleAttribute("counter-reset", counterReset);
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public String jsxGet_cssFloat()
/* 1218:     */   {
/* 1219:1571 */     return getStyleAttribute("float", null);
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   public void jsxSet_cssFloat(String value)
/* 1223:     */   {
/* 1224:1579 */     setStyleAttribute("float", value);
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   public String jsxGet_cssText()
/* 1228:     */   {
/* 1229:1587 */     return this.jsElement_.getDomNodeOrDie().getAttribute("style");
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public void jsxSet_cssText(String value)
/* 1233:     */   {
/* 1234:1595 */     this.jsElement_.getDomNodeOrDie().setAttribute("style", value);
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   public String jsxGet_cue()
/* 1238:     */   {
/* 1239:1603 */     return getStyleAttribute("cue", null);
/* 1240:     */   }
/* 1241:     */   
/* 1242:     */   public void jsxSet_cue(String cue)
/* 1243:     */   {
/* 1244:1611 */     setStyleAttribute("cue", cue);
/* 1245:     */   }
/* 1246:     */   
/* 1247:     */   public String jsxGet_cueAfter()
/* 1248:     */   {
/* 1249:1619 */     return getStyleAttribute("cue-after", null);
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   public void jsxSet_cueAfter(String cueAfter)
/* 1253:     */   {
/* 1254:1627 */     setStyleAttribute("cue-after", cueAfter);
/* 1255:     */   }
/* 1256:     */   
/* 1257:     */   public String jsxGet_cueBefore()
/* 1258:     */   {
/* 1259:1635 */     return getStyleAttribute("cue-before", null);
/* 1260:     */   }
/* 1261:     */   
/* 1262:     */   public void jsxSet_cueBefore(String cueBefore)
/* 1263:     */   {
/* 1264:1643 */     setStyleAttribute("cue-before", cueBefore);
/* 1265:     */   }
/* 1266:     */   
/* 1267:     */   public String jsxGet_cursor()
/* 1268:     */   {
/* 1269:1651 */     return getStyleAttribute("cursor", null);
/* 1270:     */   }
/* 1271:     */   
/* 1272:     */   public void jsxSet_cursor(String cursor)
/* 1273:     */   {
/* 1274:1659 */     setStyleAttribute("cursor", cursor);
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   public String jsxGet_direction()
/* 1278:     */   {
/* 1279:1667 */     return getStyleAttribute("direction", null);
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   public void jsxSet_direction(String direction)
/* 1283:     */   {
/* 1284:1675 */     setStyleAttribute("direction", direction);
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   public String jsxGet_display()
/* 1288:     */   {
/* 1289:1683 */     return getStyleAttribute("display", null);
/* 1290:     */   }
/* 1291:     */   
/* 1292:     */   public void jsxSet_display(String display)
/* 1293:     */   {
/* 1294:1691 */     setStyleAttribute("display", display);
/* 1295:     */   }
/* 1296:     */   
/* 1297:     */   public String jsxGet_elevation()
/* 1298:     */   {
/* 1299:1699 */     return getStyleAttribute("elevation", null);
/* 1300:     */   }
/* 1301:     */   
/* 1302:     */   public void jsxSet_elevation(String elevation)
/* 1303:     */   {
/* 1304:1707 */     setStyleAttribute("elevation", elevation);
/* 1305:     */   }
/* 1306:     */   
/* 1307:     */   public String jsxGet_emptyCells()
/* 1308:     */   {
/* 1309:1715 */     return getStyleAttribute("empty-cells", null);
/* 1310:     */   }
/* 1311:     */   
/* 1312:     */   public void jsxSet_emptyCells(String emptyCells)
/* 1313:     */   {
/* 1314:1723 */     setStyleAttribute("empty-cells", emptyCells);
/* 1315:     */   }
/* 1316:     */   
/* 1317:     */   public String jsxGet_filter()
/* 1318:     */   {
/* 1319:1733 */     return getStyleAttribute("filter", null);
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   public void jsxSet_filter(String filter)
/* 1323:     */   {
/* 1324:1743 */     setStyleAttribute("filter", filter);
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public String jsxGet_font()
/* 1328:     */   {
/* 1329:1751 */     return getStyleAttribute("font", null);
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   public void jsxSet_font(String font)
/* 1333:     */   {
/* 1334:1759 */     setStyleAttribute("font", font);
/* 1335:     */   }
/* 1336:     */   
/* 1337:     */   public String jsxGet_fontFamily()
/* 1338:     */   {
/* 1339:1767 */     return getStyleAttribute("font-family", null);
/* 1340:     */   }
/* 1341:     */   
/* 1342:     */   public void jsxSet_fontFamily(String fontFamily)
/* 1343:     */   {
/* 1344:1775 */     setStyleAttribute("font-family", fontFamily);
/* 1345:     */   }
/* 1346:     */   
/* 1347:     */   public String jsxGet_fontSize()
/* 1348:     */   {
/* 1349:1783 */     return getStyleAttribute("font-size", null);
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   public void jsxSet_fontSize(String fontSize)
/* 1353:     */   {
/* 1354:1791 */     setStyleAttributePixelInt("font-size", fontSize);
/* 1355:     */   }
/* 1356:     */   
/* 1357:     */   public String jsxGet_fontSizeAdjust()
/* 1358:     */   {
/* 1359:1799 */     return getStyleAttribute("font-size-adjust", null);
/* 1360:     */   }
/* 1361:     */   
/* 1362:     */   public void jsxSet_fontSizeAdjust(String fontSizeAdjust)
/* 1363:     */   {
/* 1364:1807 */     setStyleAttribute("font-size-adjust", fontSizeAdjust);
/* 1365:     */   }
/* 1366:     */   
/* 1367:     */   public String jsxGet_fontStretch()
/* 1368:     */   {
/* 1369:1815 */     return getStyleAttribute("font-stretch", null);
/* 1370:     */   }
/* 1371:     */   
/* 1372:     */   public void jsxSet_fontStretch(String fontStretch)
/* 1373:     */   {
/* 1374:1823 */     setStyleAttribute("font-stretch", fontStretch);
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   public String jsxGet_fontStyle()
/* 1378:     */   {
/* 1379:1831 */     return getStyleAttribute("font-style", null);
/* 1380:     */   }
/* 1381:     */   
/* 1382:     */   public void jsxSet_fontStyle(String fontStyle)
/* 1383:     */   {
/* 1384:1839 */     setStyleAttribute("font-style", fontStyle);
/* 1385:     */   }
/* 1386:     */   
/* 1387:     */   public String jsxGet_fontVariant()
/* 1388:     */   {
/* 1389:1847 */     return getStyleAttribute("font-variant", null);
/* 1390:     */   }
/* 1391:     */   
/* 1392:     */   public void jsxSet_fontVariant(String fontVariant)
/* 1393:     */   {
/* 1394:1855 */     setStyleAttribute("font-variant", fontVariant);
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   public String jsxGet_fontWeight()
/* 1398:     */   {
/* 1399:1863 */     return getStyleAttribute("font-weight", null);
/* 1400:     */   }
/* 1401:     */   
/* 1402:     */   public void jsxSet_fontWeight(String fontWeight)
/* 1403:     */   {
/* 1404:1871 */     setStyleAttribute("font-weight", fontWeight);
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   public String jsxGet_height()
/* 1408:     */   {
/* 1409:1879 */     return getStyleAttribute("height", null);
/* 1410:     */   }
/* 1411:     */   
/* 1412:     */   public void jsxSet_height(String height)
/* 1413:     */   {
/* 1414:1887 */     setStyleAttributePixelInt("height", height);
/* 1415:     */   }
/* 1416:     */   
/* 1417:     */   public String jsxGet_imeMode()
/* 1418:     */   {
/* 1419:1895 */     return getStyleAttribute("ime-mode", null);
/* 1420:     */   }
/* 1421:     */   
/* 1422:     */   public void jsxSet_imeMode(String imeMode)
/* 1423:     */   {
/* 1424:1903 */     setStyleAttribute("ime-mode", imeMode);
/* 1425:     */   }
/* 1426:     */   
/* 1427:     */   public String jsxGet_layoutFlow()
/* 1428:     */   {
/* 1429:1911 */     return getStyleAttribute("layout-flow", null);
/* 1430:     */   }
/* 1431:     */   
/* 1432:     */   public void jsxSet_layoutFlow(String layoutFlow)
/* 1433:     */   {
/* 1434:1919 */     setStyleAttribute("layout-flow", layoutFlow);
/* 1435:     */   }
/* 1436:     */   
/* 1437:     */   public String jsxGet_layoutGrid()
/* 1438:     */   {
/* 1439:1927 */     return getStyleAttribute("layout-grid", null);
/* 1440:     */   }
/* 1441:     */   
/* 1442:     */   public void jsxSet_layoutGrid(String layoutGrid)
/* 1443:     */   {
/* 1444:1935 */     setStyleAttribute("layout-grid-char", layoutGrid);
/* 1445:     */   }
/* 1446:     */   
/* 1447:     */   public String jsxGet_layoutGridChar()
/* 1448:     */   {
/* 1449:1943 */     return getStyleAttribute("layout-grid-char", null);
/* 1450:     */   }
/* 1451:     */   
/* 1452:     */   public void jsxSet_layoutGridChar(String layoutGridChar)
/* 1453:     */   {
/* 1454:1951 */     setStyleAttribute("layout-grid-char", layoutGridChar);
/* 1455:     */   }
/* 1456:     */   
/* 1457:     */   public String jsxGet_layoutGridLine()
/* 1458:     */   {
/* 1459:1959 */     return getStyleAttribute("layout-grid-line", null);
/* 1460:     */   }
/* 1461:     */   
/* 1462:     */   public void jsxSet_layoutGridLine(String layoutGridLine)
/* 1463:     */   {
/* 1464:1967 */     setStyleAttribute("layout-grid-line", layoutGridLine);
/* 1465:     */   }
/* 1466:     */   
/* 1467:     */   public String jsxGet_layoutGridMode()
/* 1468:     */   {
/* 1469:1975 */     return getStyleAttribute("layout-grid-mode", null);
/* 1470:     */   }
/* 1471:     */   
/* 1472:     */   public void jsxSet_layoutGridMode(String layoutGridMode)
/* 1473:     */   {
/* 1474:1983 */     setStyleAttribute("layout-grid-mode", layoutGridMode);
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   public String jsxGet_layoutGridType()
/* 1478:     */   {
/* 1479:1991 */     return getStyleAttribute("layout-grid-type", null);
/* 1480:     */   }
/* 1481:     */   
/* 1482:     */   public void jsxSet_layoutGridType(String layoutGridType)
/* 1483:     */   {
/* 1484:1999 */     setStyleAttribute("layout-grid-type", layoutGridType);
/* 1485:     */   }
/* 1486:     */   
/* 1487:     */   public String jsxGet_left()
/* 1488:     */   {
/* 1489:2007 */     return getStyleAttribute("left", null);
/* 1490:     */   }
/* 1491:     */   
/* 1492:     */   public void jsxSet_left(String left)
/* 1493:     */   {
/* 1494:2015 */     setStyleAttributePixelInt("left", left);
/* 1495:     */   }
/* 1496:     */   
/* 1497:     */   public int jsxGet_length()
/* 1498:     */   {
/* 1499:2023 */     return 0;
/* 1500:     */   }
/* 1501:     */   
/* 1502:     */   public String jsxGet_letterSpacing()
/* 1503:     */   {
/* 1504:2031 */     return getStyleAttribute("letter-spacing", null);
/* 1505:     */   }
/* 1506:     */   
/* 1507:     */   public void jsxSet_letterSpacing(String letterSpacing)
/* 1508:     */   {
/* 1509:2039 */     setStyleAttributePixelInt("letter-spacing", letterSpacing);
/* 1510:     */   }
/* 1511:     */   
/* 1512:     */   public String jsxGet_lineBreak()
/* 1513:     */   {
/* 1514:2047 */     return getStyleAttribute("line-break", null);
/* 1515:     */   }
/* 1516:     */   
/* 1517:     */   public void jsxSet_lineBreak(String lineBreak)
/* 1518:     */   {
/* 1519:2055 */     setStyleAttribute("line-break", lineBreak);
/* 1520:     */   }
/* 1521:     */   
/* 1522:     */   public String jsxGet_lineHeight()
/* 1523:     */   {
/* 1524:2063 */     return getStyleAttribute("line-height", null);
/* 1525:     */   }
/* 1526:     */   
/* 1527:     */   public void jsxSet_lineHeight(String lineHeight)
/* 1528:     */   {
/* 1529:2071 */     setStyleAttribute("line-height", lineHeight);
/* 1530:     */   }
/* 1531:     */   
/* 1532:     */   public String jsxGet_listStyle()
/* 1533:     */   {
/* 1534:2079 */     return getStyleAttribute("list-style", null);
/* 1535:     */   }
/* 1536:     */   
/* 1537:     */   public void jsxSet_listStyle(String listStyle)
/* 1538:     */   {
/* 1539:2087 */     setStyleAttribute("list-style", listStyle);
/* 1540:     */   }
/* 1541:     */   
/* 1542:     */   public String jsxGet_listStyleImage()
/* 1543:     */   {
/* 1544:2095 */     return getStyleAttribute("list-style-image", null);
/* 1545:     */   }
/* 1546:     */   
/* 1547:     */   public void jsxSet_listStyleImage(String listStyleImage)
/* 1548:     */   {
/* 1549:2103 */     setStyleAttribute("list-style-image", listStyleImage);
/* 1550:     */   }
/* 1551:     */   
/* 1552:     */   public String jsxGet_listStylePosition()
/* 1553:     */   {
/* 1554:2111 */     return getStyleAttribute("list-style-position", null);
/* 1555:     */   }
/* 1556:     */   
/* 1557:     */   public void jsxSet_listStylePosition(String listStylePosition)
/* 1558:     */   {
/* 1559:2119 */     setStyleAttribute("list-style-position", listStylePosition);
/* 1560:     */   }
/* 1561:     */   
/* 1562:     */   public String jsxGet_listStyleType()
/* 1563:     */   {
/* 1564:2127 */     return getStyleAttribute("list-style-type", null);
/* 1565:     */   }
/* 1566:     */   
/* 1567:     */   public void jsxSet_listStyleType(String listStyleType)
/* 1568:     */   {
/* 1569:2135 */     setStyleAttribute("list-style-type", listStyleType);
/* 1570:     */   }
/* 1571:     */   
/* 1572:     */   public String jsxGet_margin()
/* 1573:     */   {
/* 1574:2143 */     return getStyleAttribute("margin", null);
/* 1575:     */   }
/* 1576:     */   
/* 1577:     */   public void jsxSet_margin(String margin)
/* 1578:     */   {
/* 1579:2151 */     setStyleAttribute("margin", margin);
/* 1580:     */   }
/* 1581:     */   
/* 1582:     */   public String jsxGet_marginBottom()
/* 1583:     */   {
/* 1584:2159 */     return getStyleAttribute("margin-bottom", "margin", Shorthand.BOTTOM);
/* 1585:     */   }
/* 1586:     */   
/* 1587:     */   public void jsxSet_marginBottom(String marginBottom)
/* 1588:     */   {
/* 1589:2167 */     setStyleAttributePixelInt("margin-bottom", marginBottom);
/* 1590:     */   }
/* 1591:     */   
/* 1592:     */   public String jsxGet_marginLeft()
/* 1593:     */   {
/* 1594:2175 */     return getStyleAttribute("margin-left", "margin", Shorthand.LEFT);
/* 1595:     */   }
/* 1596:     */   
/* 1597:     */   public void jsxSet_marginLeft(String marginLeft)
/* 1598:     */   {
/* 1599:2183 */     setStyleAttributePixelInt("margin-left", marginLeft);
/* 1600:     */   }
/* 1601:     */   
/* 1602:     */   public String jsxGet_marginRight()
/* 1603:     */   {
/* 1604:2191 */     return getStyleAttribute("margin-right", "margin", Shorthand.RIGHT);
/* 1605:     */   }
/* 1606:     */   
/* 1607:     */   public void jsxSet_marginRight(String marginRight)
/* 1608:     */   {
/* 1609:2199 */     setStyleAttributePixelInt("margin-right", marginRight);
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   public String jsxGet_marginTop()
/* 1613:     */   {
/* 1614:2207 */     return getStyleAttribute("margin-top", "margin", Shorthand.TOP);
/* 1615:     */   }
/* 1616:     */   
/* 1617:     */   public void jsxSet_marginTop(String marginTop)
/* 1618:     */   {
/* 1619:2215 */     setStyleAttributePixelInt("margin-top", marginTop);
/* 1620:     */   }
/* 1621:     */   
/* 1622:     */   public String jsxGet_markerOffset()
/* 1623:     */   {
/* 1624:2223 */     return getStyleAttribute("marker-offset", null);
/* 1625:     */   }
/* 1626:     */   
/* 1627:     */   public void jsxSet_markerOffset(String markerOffset)
/* 1628:     */   {
/* 1629:2231 */     setStyleAttribute("marker-offset", markerOffset);
/* 1630:     */   }
/* 1631:     */   
/* 1632:     */   public String jsxGet_marks()
/* 1633:     */   {
/* 1634:2239 */     return getStyleAttribute("marks", null);
/* 1635:     */   }
/* 1636:     */   
/* 1637:     */   public void jsxSet_marks(String marks)
/* 1638:     */   {
/* 1639:2247 */     setStyleAttribute("marks", marks);
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public String jsxGet_maxHeight()
/* 1643:     */   {
/* 1644:2255 */     return getStyleAttribute("max-height", null);
/* 1645:     */   }
/* 1646:     */   
/* 1647:     */   public void jsxSet_maxHeight(String maxHeight)
/* 1648:     */   {
/* 1649:2263 */     setStyleAttributePixelInt("max-height", maxHeight);
/* 1650:     */   }
/* 1651:     */   
/* 1652:     */   public String jsxGet_maxWidth()
/* 1653:     */   {
/* 1654:2271 */     return getStyleAttribute("max-width", null);
/* 1655:     */   }
/* 1656:     */   
/* 1657:     */   public void jsxSet_maxWidth(String maxWidth)
/* 1658:     */   {
/* 1659:2279 */     setStyleAttributePixelInt("max-width", maxWidth);
/* 1660:     */   }
/* 1661:     */   
/* 1662:     */   public String jsxGet_minHeight()
/* 1663:     */   {
/* 1664:2287 */     return getStyleAttribute("min-height", null);
/* 1665:     */   }
/* 1666:     */   
/* 1667:     */   public void jsxSet_minHeight(String minHeight)
/* 1668:     */   {
/* 1669:2295 */     setStyleAttributePixelInt("min-height", minHeight);
/* 1670:     */   }
/* 1671:     */   
/* 1672:     */   public String jsxGet_minWidth()
/* 1673:     */   {
/* 1674:2303 */     return getStyleAttribute("min-width", null);
/* 1675:     */   }
/* 1676:     */   
/* 1677:     */   public void jsxSet_minWidth(String minWidth)
/* 1678:     */   {
/* 1679:2311 */     setStyleAttributePixelInt("min-width", minWidth);
/* 1680:     */   }
/* 1681:     */   
/* 1682:     */   public String jsxGet_MozAppearance()
/* 1683:     */   {
/* 1684:2319 */     return getStyleAttribute("-moz-appearance", null);
/* 1685:     */   }
/* 1686:     */   
/* 1687:     */   public void jsxSet_MozAppearance(String mozAppearance)
/* 1688:     */   {
/* 1689:2327 */     setStyleAttribute("-moz-appearance", mozAppearance);
/* 1690:     */   }
/* 1691:     */   
/* 1692:     */   public String jsxGet_MozBackgroundClip()
/* 1693:     */   {
/* 1694:2335 */     return getStyleAttribute("-moz-background-clip", null);
/* 1695:     */   }
/* 1696:     */   
/* 1697:     */   public void jsxSet_MozBackgroundClip(String mozBackgroundClip)
/* 1698:     */   {
/* 1699:2343 */     setStyleAttribute("-moz-background-clip", mozBackgroundClip);
/* 1700:     */   }
/* 1701:     */   
/* 1702:     */   public String jsxGet_MozBackgroundInlinePolicy()
/* 1703:     */   {
/* 1704:2351 */     return getStyleAttribute("-moz-background-inline-policy", null);
/* 1705:     */   }
/* 1706:     */   
/* 1707:     */   public void jsxSet_MozBackgroundInlinePolicy(String mozBackgroundInlinePolicy)
/* 1708:     */   {
/* 1709:2359 */     setStyleAttribute("-moz-background-inline-policy", mozBackgroundInlinePolicy);
/* 1710:     */   }
/* 1711:     */   
/* 1712:     */   public String jsxGet_MozBackgroundOrigin()
/* 1713:     */   {
/* 1714:2367 */     return getStyleAttribute("-moz-background-origin", null);
/* 1715:     */   }
/* 1716:     */   
/* 1717:     */   public void jsxSet_MozBackgroundOrigin(String mozBackgroundOrigin)
/* 1718:     */   {
/* 1719:2375 */     setStyleAttribute("-moz-background-origin", mozBackgroundOrigin);
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   public String jsxGet_MozBinding()
/* 1723:     */   {
/* 1724:2383 */     return getStyleAttribute("-moz-binding", null);
/* 1725:     */   }
/* 1726:     */   
/* 1727:     */   public void jsxSet_MozBinding(String mozBinding)
/* 1728:     */   {
/* 1729:2391 */     setStyleAttribute("-moz-binding", mozBinding);
/* 1730:     */   }
/* 1731:     */   
/* 1732:     */   public String jsxGet_MozBorderBottomColors()
/* 1733:     */   {
/* 1734:2399 */     return getStyleAttribute("-moz-border-bottom-colors", null);
/* 1735:     */   }
/* 1736:     */   
/* 1737:     */   public void jsxSet_MozBorderBottomColors(String mozBorderBottomColors)
/* 1738:     */   {
/* 1739:2407 */     setStyleAttribute("-moz-border-bottom-colors", mozBorderBottomColors);
/* 1740:     */   }
/* 1741:     */   
/* 1742:     */   public String jsxGet_MozBorderLeftColors()
/* 1743:     */   {
/* 1744:2415 */     return getStyleAttribute("-moz-border-left-colors", null);
/* 1745:     */   }
/* 1746:     */   
/* 1747:     */   public void jsxSet_MozBorderLeftColors(String mozBorderLeftColors)
/* 1748:     */   {
/* 1749:2423 */     setStyleAttribute("-moz-border-left-colors", mozBorderLeftColors);
/* 1750:     */   }
/* 1751:     */   
/* 1752:     */   public String jsxGet_MozBorderRadius()
/* 1753:     */   {
/* 1754:2431 */     return getStyleAttribute("-moz-border-radius", null);
/* 1755:     */   }
/* 1756:     */   
/* 1757:     */   public void jsxSet_MozBorderRadius(String mozBorderRadius)
/* 1758:     */   {
/* 1759:2439 */     setStyleAttribute("-moz-border-radius", mozBorderRadius);
/* 1760:     */   }
/* 1761:     */   
/* 1762:     */   public String jsxGet_MozBorderRadiusBottomleft()
/* 1763:     */   {
/* 1764:2447 */     return getStyleAttribute("-moz-border-radius-bottomleft", null);
/* 1765:     */   }
/* 1766:     */   
/* 1767:     */   public void jsxSet_MozBorderRadiusBottomleft(String mozBorderRadiusBottomleft)
/* 1768:     */   {
/* 1769:2455 */     setStyleAttribute("-moz-border-radius-bottomleft", mozBorderRadiusBottomleft);
/* 1770:     */   }
/* 1771:     */   
/* 1772:     */   public String jsxGet_MozBorderRadiusBottomright()
/* 1773:     */   {
/* 1774:2463 */     return getStyleAttribute("-moz-border-radius-bottomright", null);
/* 1775:     */   }
/* 1776:     */   
/* 1777:     */   public void jsxSet_MozBorderRadiusBottomright(String mozBorderRadiusBottomright)
/* 1778:     */   {
/* 1779:2471 */     setStyleAttribute("-moz-border-radius-bottomright", mozBorderRadiusBottomright);
/* 1780:     */   }
/* 1781:     */   
/* 1782:     */   public String jsxGet_MozBorderRadiusTopleft()
/* 1783:     */   {
/* 1784:2479 */     return getStyleAttribute("-moz-border-radius-topleft", null);
/* 1785:     */   }
/* 1786:     */   
/* 1787:     */   public void jsxSet_MozBorderRadiusTopleft(String mozBorderRadiusTopleft)
/* 1788:     */   {
/* 1789:2487 */     setStyleAttribute("-moz-border-radius-topleft", mozBorderRadiusTopleft);
/* 1790:     */   }
/* 1791:     */   
/* 1792:     */   public String jsxGet_MozBorderRadiusTopright()
/* 1793:     */   {
/* 1794:2495 */     return getStyleAttribute("-moz-border-radius-topright", null);
/* 1795:     */   }
/* 1796:     */   
/* 1797:     */   public void jsxSet_MozBorderRadiusTopright(String mozBorderRadiusTopright)
/* 1798:     */   {
/* 1799:2503 */     setStyleAttribute("-moz-border-radius-topright", mozBorderRadiusTopright);
/* 1800:     */   }
/* 1801:     */   
/* 1802:     */   public String jsxGet_MozBorderRightColors()
/* 1803:     */   {
/* 1804:2511 */     return getStyleAttribute("-moz-border-right-colors", null);
/* 1805:     */   }
/* 1806:     */   
/* 1807:     */   public void jsxSet_MozBorderRightColors(String mozBorderRightColors)
/* 1808:     */   {
/* 1809:2519 */     setStyleAttribute("-moz-border-right-colors", mozBorderRightColors);
/* 1810:     */   }
/* 1811:     */   
/* 1812:     */   public String jsxGet_MozBorderTopColors()
/* 1813:     */   {
/* 1814:2527 */     return getStyleAttribute("-moz-border-top-colors", null);
/* 1815:     */   }
/* 1816:     */   
/* 1817:     */   public void jsxSet_MozBorderTopColors(String mozBorderTopColors)
/* 1818:     */   {
/* 1819:2535 */     setStyleAttribute("-moz-border-top-colors", mozBorderTopColors);
/* 1820:     */   }
/* 1821:     */   
/* 1822:     */   public String jsxGet_MozBoxAlign()
/* 1823:     */   {
/* 1824:2543 */     return getStyleAttribute("-moz-box-align", null);
/* 1825:     */   }
/* 1826:     */   
/* 1827:     */   public void jsxSet_MozBoxAlign(String mozBoxAlign)
/* 1828:     */   {
/* 1829:2551 */     setStyleAttribute("-moz-box-align", mozBoxAlign);
/* 1830:     */   }
/* 1831:     */   
/* 1832:     */   public String jsxGet_MozBoxDirection()
/* 1833:     */   {
/* 1834:2559 */     return getStyleAttribute("-moz-box-direction", null);
/* 1835:     */   }
/* 1836:     */   
/* 1837:     */   public void jsxSet_MozBoxDirection(String mozBoxDirection)
/* 1838:     */   {
/* 1839:2567 */     setStyleAttribute("-moz-box-direction", mozBoxDirection);
/* 1840:     */   }
/* 1841:     */   
/* 1842:     */   public String jsxGet_MozBoxFlex()
/* 1843:     */   {
/* 1844:2575 */     return getStyleAttribute("-moz-box-flex", null);
/* 1845:     */   }
/* 1846:     */   
/* 1847:     */   public void jsxSet_MozBoxFlex(String mozBoxFlex)
/* 1848:     */   {
/* 1849:2583 */     setStyleAttribute("-moz-box-flex", mozBoxFlex);
/* 1850:     */   }
/* 1851:     */   
/* 1852:     */   public String jsxGet_MozBoxOrdinalGroup()
/* 1853:     */   {
/* 1854:2591 */     return getStyleAttribute("-moz-box-ordinal-group", null);
/* 1855:     */   }
/* 1856:     */   
/* 1857:     */   public void jsxSet_MozBoxOrdinalGroup(String mozBoxOrdinalGroup)
/* 1858:     */   {
/* 1859:2599 */     setStyleAttribute("-moz-box-ordinal-group", mozBoxOrdinalGroup);
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   public String jsxGet_MozBoxOrient()
/* 1863:     */   {
/* 1864:2607 */     return getStyleAttribute("-moz-box-orient", null);
/* 1865:     */   }
/* 1866:     */   
/* 1867:     */   public void jsxSet_MozBoxOrient(String mozBoxOrient)
/* 1868:     */   {
/* 1869:2615 */     setStyleAttribute("-moz-box-orient", mozBoxOrient);
/* 1870:     */   }
/* 1871:     */   
/* 1872:     */   public String jsxGet_MozBoxPack()
/* 1873:     */   {
/* 1874:2623 */     return getStyleAttribute("-moz-box-pack", null);
/* 1875:     */   }
/* 1876:     */   
/* 1877:     */   public void jsxSet_MozBoxPack(String mozBoxPack)
/* 1878:     */   {
/* 1879:2631 */     setStyleAttribute("-moz-box-pack", mozBoxPack);
/* 1880:     */   }
/* 1881:     */   
/* 1882:     */   public String jsxGet_MozBoxSizing()
/* 1883:     */   {
/* 1884:2639 */     return getStyleAttribute("-moz-box-sizing", null);
/* 1885:     */   }
/* 1886:     */   
/* 1887:     */   public void jsxSet_MozBoxSizing(String mozBoxSizing)
/* 1888:     */   {
/* 1889:2647 */     setStyleAttribute("-moz-box-sizing", mozBoxSizing);
/* 1890:     */   }
/* 1891:     */   
/* 1892:     */   public String jsxGet_MozColumnCount()
/* 1893:     */   {
/* 1894:2655 */     return getStyleAttribute("-moz-column-count", null);
/* 1895:     */   }
/* 1896:     */   
/* 1897:     */   public void jsxSet_MozColumnCount(String mozColumnCount)
/* 1898:     */   {
/* 1899:2663 */     setStyleAttribute("-moz-column-count", mozColumnCount);
/* 1900:     */   }
/* 1901:     */   
/* 1902:     */   public String jsxGet_MozColumnGap()
/* 1903:     */   {
/* 1904:2671 */     return getStyleAttribute("-moz-column-gap", null);
/* 1905:     */   }
/* 1906:     */   
/* 1907:     */   public void jsxSet_MozColumnGap(String mozColumnGap)
/* 1908:     */   {
/* 1909:2679 */     setStyleAttribute("-moz-column-gap", mozColumnGap);
/* 1910:     */   }
/* 1911:     */   
/* 1912:     */   public String jsxGet_MozColumnWidth()
/* 1913:     */   {
/* 1914:2687 */     return getStyleAttribute("-moz-column-width", null);
/* 1915:     */   }
/* 1916:     */   
/* 1917:     */   public void jsxSet_MozColumnWidth(String mozColumnWidth)
/* 1918:     */   {
/* 1919:2695 */     setStyleAttribute("-moz-column-width", mozColumnWidth);
/* 1920:     */   }
/* 1921:     */   
/* 1922:     */   public String jsxGet_MozFloatEdge()
/* 1923:     */   {
/* 1924:2703 */     return getStyleAttribute("-moz-float-edge", null);
/* 1925:     */   }
/* 1926:     */   
/* 1927:     */   public void jsxSet_MozFloatEdge(String mozFloatEdge)
/* 1928:     */   {
/* 1929:2711 */     setStyleAttribute("-moz-float-edge", mozFloatEdge);
/* 1930:     */   }
/* 1931:     */   
/* 1932:     */   public String jsxGet_MozForceBrokenImageIcon()
/* 1933:     */   {
/* 1934:2719 */     return getStyleAttribute("-moz-force-broken-image-icon", null);
/* 1935:     */   }
/* 1936:     */   
/* 1937:     */   public void jsxSet_MozForceBrokenImageIcon(String mozForceBrokenImageIcon)
/* 1938:     */   {
/* 1939:2727 */     setStyleAttribute("-moz-force-broken-image-icon", mozForceBrokenImageIcon);
/* 1940:     */   }
/* 1941:     */   
/* 1942:     */   public String jsxGet_MozImageRegion()
/* 1943:     */   {
/* 1944:2735 */     return getStyleAttribute("-moz-image-region", null);
/* 1945:     */   }
/* 1946:     */   
/* 1947:     */   public void jsxSet_MozImageRegion(String mozImageRegion)
/* 1948:     */   {
/* 1949:2743 */     setStyleAttribute("-moz-image-region", mozImageRegion);
/* 1950:     */   }
/* 1951:     */   
/* 1952:     */   public String jsxGet_MozMarginEnd()
/* 1953:     */   {
/* 1954:2751 */     return getStyleAttribute("-moz-margin-end", null);
/* 1955:     */   }
/* 1956:     */   
/* 1957:     */   public void jsxSet_MozMarginEnd(String mozMarginEnd)
/* 1958:     */   {
/* 1959:2759 */     setStyleAttribute("-moz-margin-end", mozMarginEnd);
/* 1960:     */   }
/* 1961:     */   
/* 1962:     */   public String jsxGet_MozMarginStart()
/* 1963:     */   {
/* 1964:2767 */     return getStyleAttribute("-moz-margin-start", null);
/* 1965:     */   }
/* 1966:     */   
/* 1967:     */   public void jsxSet_MozMarginStart(String mozMarginStart)
/* 1968:     */   {
/* 1969:2775 */     setStyleAttribute("-moz-margin-start", mozMarginStart);
/* 1970:     */   }
/* 1971:     */   
/* 1972:     */   public String jsxGet_MozOpacity()
/* 1973:     */   {
/* 1974:2783 */     return getStyleAttribute("-moz-opacity", null);
/* 1975:     */   }
/* 1976:     */   
/* 1977:     */   public void jsxSet_MozOpacity(String mozOpacity)
/* 1978:     */   {
/* 1979:2791 */     setStyleAttribute("-moz-opacity", mozOpacity);
/* 1980:     */   }
/* 1981:     */   
/* 1982:     */   public String jsxGet_MozOutline()
/* 1983:     */   {
/* 1984:2799 */     return getStyleAttribute("-moz-outline", null);
/* 1985:     */   }
/* 1986:     */   
/* 1987:     */   public void jsxSet_MozOutline(String mozOutline)
/* 1988:     */   {
/* 1989:2807 */     setStyleAttribute("-moz-outline", mozOutline);
/* 1990:     */   }
/* 1991:     */   
/* 1992:     */   public String jsxGet_MozOutlineColor()
/* 1993:     */   {
/* 1994:2815 */     return getStyleAttribute("-moz-outline-color", null);
/* 1995:     */   }
/* 1996:     */   
/* 1997:     */   public void jsxSet_MozOutlineColor(String mozOutlineColor)
/* 1998:     */   {
/* 1999:2823 */     setStyleAttribute("-moz-outline-color", mozOutlineColor);
/* 2000:     */   }
/* 2001:     */   
/* 2002:     */   public String jsxGet_MozOutlineOffset()
/* 2003:     */   {
/* 2004:2831 */     return getStyleAttribute("-moz-outline-offset", null);
/* 2005:     */   }
/* 2006:     */   
/* 2007:     */   public void jsxSet_MozOutlineOffset(String mozOutlineOffset)
/* 2008:     */   {
/* 2009:2839 */     setStyleAttribute("-moz-outline-offset", mozOutlineOffset);
/* 2010:     */   }
/* 2011:     */   
/* 2012:     */   public String jsxGet_MozOutlineRadius()
/* 2013:     */   {
/* 2014:2847 */     return getStyleAttribute("-mz-outline-radius", null);
/* 2015:     */   }
/* 2016:     */   
/* 2017:     */   public void jsxSet_MozOutlineRadius(String mozOutlineRadius)
/* 2018:     */   {
/* 2019:2855 */     setStyleAttribute("-mz-outline-radius", mozOutlineRadius);
/* 2020:     */   }
/* 2021:     */   
/* 2022:     */   public String jsxGet_MozOutlineRadiusBottomleft()
/* 2023:     */   {
/* 2024:2863 */     return getStyleAttribute("-moz-outline-radius-bottomleft", null);
/* 2025:     */   }
/* 2026:     */   
/* 2027:     */   public void jsxSet_MozOutlineRadiusBottomleft(String mozOutlineRadiusBottomleft)
/* 2028:     */   {
/* 2029:2871 */     setStyleAttribute("-moz-outline-radius-bottomleft", mozOutlineRadiusBottomleft);
/* 2030:     */   }
/* 2031:     */   
/* 2032:     */   public String jsxGet_MozOutlineRadiusBottomright()
/* 2033:     */   {
/* 2034:2879 */     return getStyleAttribute("-moz-outline-radius-bottomright", null);
/* 2035:     */   }
/* 2036:     */   
/* 2037:     */   public void jsxSet_MozOutlineRadiusBottomright(String mozOutlineRadiusBottomright)
/* 2038:     */   {
/* 2039:2887 */     setStyleAttribute("-moz-outline-radius-bottomright", mozOutlineRadiusBottomright);
/* 2040:     */   }
/* 2041:     */   
/* 2042:     */   public String jsxGet_MozOutlineRadiusTopleft()
/* 2043:     */   {
/* 2044:2895 */     return getStyleAttribute("-moz-outline-radius-topleft", null);
/* 2045:     */   }
/* 2046:     */   
/* 2047:     */   public void jsxSet_MozOutlineRadiusTopleft(String mozOutlineRadiusTopleft)
/* 2048:     */   {
/* 2049:2903 */     setStyleAttribute("-moz-outline-radius-topleft", mozOutlineRadiusTopleft);
/* 2050:     */   }
/* 2051:     */   
/* 2052:     */   public String jsxGet_MozOutlineRadiusTopright()
/* 2053:     */   {
/* 2054:2911 */     return getStyleAttribute("-moz-outline-radius-topright", null);
/* 2055:     */   }
/* 2056:     */   
/* 2057:     */   public void jsxSet_MozOutlineRadiusTopright(String mozOutlineRadiusTopright)
/* 2058:     */   {
/* 2059:2919 */     setStyleAttribute("-moz-outline-radius-topright", mozOutlineRadiusTopright);
/* 2060:     */   }
/* 2061:     */   
/* 2062:     */   public String jsxGet_MozOutlineStyle()
/* 2063:     */   {
/* 2064:2927 */     return getStyleAttribute("-moz-outline-style", null);
/* 2065:     */   }
/* 2066:     */   
/* 2067:     */   public void jsxSet_MozOutlineStyle(String mozOutlineStyle)
/* 2068:     */   {
/* 2069:2935 */     setStyleAttribute("-moz-outline-style", mozOutlineStyle);
/* 2070:     */   }
/* 2071:     */   
/* 2072:     */   public String jsxGet_MozOutlineWidth()
/* 2073:     */   {
/* 2074:2943 */     return getStyleAttribute("-moz-outline-width", null);
/* 2075:     */   }
/* 2076:     */   
/* 2077:     */   public void jsxSet_MozOutlineWidth(String mozOutlineWidth)
/* 2078:     */   {
/* 2079:2951 */     setStyleAttribute("-moz-outline-width", mozOutlineWidth);
/* 2080:     */   }
/* 2081:     */   
/* 2082:     */   public String jsxGet_MozPaddingEnd()
/* 2083:     */   {
/* 2084:2959 */     return getStyleAttribute("-moz-padding-end", null);
/* 2085:     */   }
/* 2086:     */   
/* 2087:     */   public void jsxSet_MozPaddingEnd(String mozPaddingEnd)
/* 2088:     */   {
/* 2089:2967 */     setStyleAttribute("-moz-padding-end", mozPaddingEnd);
/* 2090:     */   }
/* 2091:     */   
/* 2092:     */   public String jsxGet_MozPaddingStart()
/* 2093:     */   {
/* 2094:2975 */     return getStyleAttribute("-moz-padding-start", null);
/* 2095:     */   }
/* 2096:     */   
/* 2097:     */   public void jsxSet_MozPaddingStart(String mozPaddingStart)
/* 2098:     */   {
/* 2099:2983 */     setStyleAttribute("-moz-padding-start", mozPaddingStart);
/* 2100:     */   }
/* 2101:     */   
/* 2102:     */   public String jsxGet_MozUserFocus()
/* 2103:     */   {
/* 2104:2991 */     return getStyleAttribute("-moz-user-focus", null);
/* 2105:     */   }
/* 2106:     */   
/* 2107:     */   public void jsxSet_MozUserFocus(String mozUserFocus)
/* 2108:     */   {
/* 2109:2999 */     setStyleAttribute("-moz-user-focus", mozUserFocus);
/* 2110:     */   }
/* 2111:     */   
/* 2112:     */   public String jsxGet_MozUserInput()
/* 2113:     */   {
/* 2114:3007 */     return getStyleAttribute("-moz-user-input", null);
/* 2115:     */   }
/* 2116:     */   
/* 2117:     */   public void jsxSet_MozUserInput(String mozUserInput)
/* 2118:     */   {
/* 2119:3015 */     setStyleAttribute("-moz-user-input", mozUserInput);
/* 2120:     */   }
/* 2121:     */   
/* 2122:     */   public String jsxGet_MozUserModify()
/* 2123:     */   {
/* 2124:3023 */     return getStyleAttribute("-moz-user-modify", null);
/* 2125:     */   }
/* 2126:     */   
/* 2127:     */   public void jsxSet_MozUserModify(String mozUserModify)
/* 2128:     */   {
/* 2129:3031 */     setStyleAttribute("-moz-user-modify", mozUserModify);
/* 2130:     */   }
/* 2131:     */   
/* 2132:     */   public String jsxGet_MozUserSelect()
/* 2133:     */   {
/* 2134:3039 */     return getStyleAttribute("-moz-user-select", null);
/* 2135:     */   }
/* 2136:     */   
/* 2137:     */   public void jsxSet_MozUserSelect(String mozUserSelect)
/* 2138:     */   {
/* 2139:3047 */     setStyleAttribute("-moz-user-select", mozUserSelect);
/* 2140:     */   }
/* 2141:     */   
/* 2142:     */   public String jsxGet_msInterpolationMode()
/* 2143:     */   {
/* 2144:3055 */     return getStyleAttribute("ms-interpolation-mode", null);
/* 2145:     */   }
/* 2146:     */   
/* 2147:     */   public void jsxSet_msInterpolationMode(String msInterpolationMode)
/* 2148:     */   {
/* 2149:3063 */     setStyleAttribute("ms-interpolation-mode", msInterpolationMode);
/* 2150:     */   }
/* 2151:     */   
/* 2152:     */   public String jsxGet_opacity()
/* 2153:     */   {
/* 2154:3071 */     return getStyleAttribute("opacity", null);
/* 2155:     */   }
/* 2156:     */   
/* 2157:     */   public void jsxSet_opacity(String opacity)
/* 2158:     */   {
/* 2159:3079 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_23)) {
/* 2160:3080 */       setStyleAttribute("opacity", opacity);
/* 2161:3082 */     } else if ((com.gargoylesoftware.htmlunit.util.StringUtils.isFloat(opacity, true)) || (opacity.length() == 0)) {
/* 2162:3083 */       setStyleAttribute("opacity", opacity.trim());
/* 2163:     */     }
/* 2164:     */   }
/* 2165:     */   
/* 2166:     */   public String jsxGet_orphans()
/* 2167:     */   {
/* 2168:3092 */     return getStyleAttribute("orphans", null);
/* 2169:     */   }
/* 2170:     */   
/* 2171:     */   public void jsxSet_orphans(String orphans)
/* 2172:     */   {
/* 2173:3100 */     setStyleAttribute("orphans", orphans);
/* 2174:     */   }
/* 2175:     */   
/* 2176:     */   public String jsxGet_outline()
/* 2177:     */   {
/* 2178:3108 */     return getStyleAttribute("outline", null);
/* 2179:     */   }
/* 2180:     */   
/* 2181:     */   public void jsxSet_outline(String outline)
/* 2182:     */   {
/* 2183:3116 */     setStyleAttribute("outline", outline);
/* 2184:     */   }
/* 2185:     */   
/* 2186:     */   public String jsxGet_outlineColor()
/* 2187:     */   {
/* 2188:3124 */     return getStyleAttribute("outline-color", null);
/* 2189:     */   }
/* 2190:     */   
/* 2191:     */   public void jsxSet_outlineColor(String outlineColor)
/* 2192:     */   {
/* 2193:3132 */     setStyleAttribute("outline-color", outlineColor);
/* 2194:     */   }
/* 2195:     */   
/* 2196:     */   public String jsxGet_outlineOffset()
/* 2197:     */   {
/* 2198:3140 */     return getStyleAttribute("outline-offset", null);
/* 2199:     */   }
/* 2200:     */   
/* 2201:     */   public void jsxSet_outlineOffset(String outlineOffset)
/* 2202:     */   {
/* 2203:3148 */     setStyleAttribute("outline-offset", outlineOffset);
/* 2204:     */   }
/* 2205:     */   
/* 2206:     */   public String jsxGet_outlineStyle()
/* 2207:     */   {
/* 2208:3156 */     return getStyleAttribute("outline-style", null);
/* 2209:     */   }
/* 2210:     */   
/* 2211:     */   public void jsxSet_outlineStyle(String outlineStyle)
/* 2212:     */   {
/* 2213:3164 */     setStyleAttribute("outline-style", outlineStyle);
/* 2214:     */   }
/* 2215:     */   
/* 2216:     */   public String jsxGet_outlineWidth()
/* 2217:     */   {
/* 2218:3172 */     return getStyleAttribute("outline-width", null);
/* 2219:     */   }
/* 2220:     */   
/* 2221:     */   public void jsxSet_outlineWidth(String outlineWidth)
/* 2222:     */   {
/* 2223:3180 */     setStyleAttributePixelInt("outline-width", outlineWidth);
/* 2224:     */   }
/* 2225:     */   
/* 2226:     */   public String jsxGet_overflow()
/* 2227:     */   {
/* 2228:3188 */     return getStyleAttribute("overflow", null);
/* 2229:     */   }
/* 2230:     */   
/* 2231:     */   public void jsxSet_overflow(String overflow)
/* 2232:     */   {
/* 2233:3196 */     setStyleAttribute("overflow", overflow);
/* 2234:     */   }
/* 2235:     */   
/* 2236:     */   public String jsxGet_overflowX()
/* 2237:     */   {
/* 2238:3204 */     return getStyleAttribute("overflow-x", null);
/* 2239:     */   }
/* 2240:     */   
/* 2241:     */   public void jsxSet_overflowX(String overflowX)
/* 2242:     */   {
/* 2243:3212 */     setStyleAttribute("overflow-x", overflowX);
/* 2244:     */   }
/* 2245:     */   
/* 2246:     */   public String jsxGet_overflowY()
/* 2247:     */   {
/* 2248:3220 */     return getStyleAttribute("overflow-y", null);
/* 2249:     */   }
/* 2250:     */   
/* 2251:     */   public void jsxSet_overflowY(String overflowY)
/* 2252:     */   {
/* 2253:3228 */     setStyleAttribute("overflow-y", overflowY);
/* 2254:     */   }
/* 2255:     */   
/* 2256:     */   public String jsxGet_padding()
/* 2257:     */   {
/* 2258:3236 */     return getStyleAttribute("padding", null);
/* 2259:     */   }
/* 2260:     */   
/* 2261:     */   public void jsxSet_padding(String padding)
/* 2262:     */   {
/* 2263:3244 */     setStyleAttribute("padding", padding);
/* 2264:     */   }
/* 2265:     */   
/* 2266:     */   public String jsxGet_paddingBottom()
/* 2267:     */   {
/* 2268:3252 */     return getStyleAttribute("padding-bottom", "padding", Shorthand.BOTTOM);
/* 2269:     */   }
/* 2270:     */   
/* 2271:     */   public void jsxSet_paddingBottom(String paddingBottom)
/* 2272:     */   {
/* 2273:3260 */     setStyleAttributePixelInt("padding-bottom", paddingBottom);
/* 2274:     */   }
/* 2275:     */   
/* 2276:     */   public String jsxGet_paddingLeft()
/* 2277:     */   {
/* 2278:3268 */     return getStyleAttribute("padding-left", "padding", Shorthand.LEFT);
/* 2279:     */   }
/* 2280:     */   
/* 2281:     */   public void jsxSet_paddingLeft(String paddingLeft)
/* 2282:     */   {
/* 2283:3276 */     setStyleAttributePixelInt("padding-left", paddingLeft);
/* 2284:     */   }
/* 2285:     */   
/* 2286:     */   public String jsxGet_paddingRight()
/* 2287:     */   {
/* 2288:3284 */     return getStyleAttribute("padding-right", "padding", Shorthand.RIGHT);
/* 2289:     */   }
/* 2290:     */   
/* 2291:     */   public void jsxSet_paddingRight(String paddingRight)
/* 2292:     */   {
/* 2293:3292 */     setStyleAttributePixelInt("padding-right", paddingRight);
/* 2294:     */   }
/* 2295:     */   
/* 2296:     */   public String jsxGet_paddingTop()
/* 2297:     */   {
/* 2298:3300 */     return getStyleAttribute("padding-top", "padding", Shorthand.TOP);
/* 2299:     */   }
/* 2300:     */   
/* 2301:     */   public void jsxSet_paddingTop(String paddingTop)
/* 2302:     */   {
/* 2303:3308 */     setStyleAttributePixelInt("padding-top", paddingTop);
/* 2304:     */   }
/* 2305:     */   
/* 2306:     */   public String jsxGet_page()
/* 2307:     */   {
/* 2308:3316 */     return getStyleAttribute("page", null);
/* 2309:     */   }
/* 2310:     */   
/* 2311:     */   public void jsxSet_page(String page)
/* 2312:     */   {
/* 2313:3324 */     setStyleAttribute("page", page);
/* 2314:     */   }
/* 2315:     */   
/* 2316:     */   public String jsxGet_pageBreakAfter()
/* 2317:     */   {
/* 2318:3332 */     return getStyleAttribute("page-break-after", null);
/* 2319:     */   }
/* 2320:     */   
/* 2321:     */   public void jsxSet_pageBreakAfter(String pageBreakAfter)
/* 2322:     */   {
/* 2323:3340 */     setStyleAttribute("page-break-after", pageBreakAfter);
/* 2324:     */   }
/* 2325:     */   
/* 2326:     */   public String jsxGet_pageBreakBefore()
/* 2327:     */   {
/* 2328:3348 */     return getStyleAttribute("page-break-before", null);
/* 2329:     */   }
/* 2330:     */   
/* 2331:     */   public void jsxSet_pageBreakBefore(String pageBreakBefore)
/* 2332:     */   {
/* 2333:3356 */     setStyleAttribute("page-break-before", pageBreakBefore);
/* 2334:     */   }
/* 2335:     */   
/* 2336:     */   public String jsxGet_pageBreakInside()
/* 2337:     */   {
/* 2338:3364 */     return getStyleAttribute("page-break-inside", null);
/* 2339:     */   }
/* 2340:     */   
/* 2341:     */   public void jsxSet_pageBreakInside(String pageBreakInside)
/* 2342:     */   {
/* 2343:3372 */     setStyleAttribute("page-break-inside", pageBreakInside);
/* 2344:     */   }
/* 2345:     */   
/* 2346:     */   public String jsxGet_pause()
/* 2347:     */   {
/* 2348:3380 */     return getStyleAttribute("pause", null);
/* 2349:     */   }
/* 2350:     */   
/* 2351:     */   public void jsxSet_pause(String pause)
/* 2352:     */   {
/* 2353:3388 */     setStyleAttribute("pause", pause);
/* 2354:     */   }
/* 2355:     */   
/* 2356:     */   public String jsxGet_pauseAfter()
/* 2357:     */   {
/* 2358:3396 */     return getStyleAttribute("pause-after", null);
/* 2359:     */   }
/* 2360:     */   
/* 2361:     */   public void jsxSet_pauseAfter(String pauseAfter)
/* 2362:     */   {
/* 2363:3404 */     setStyleAttribute("pause-after", pauseAfter);
/* 2364:     */   }
/* 2365:     */   
/* 2366:     */   public String jsxGet_pauseBefore()
/* 2367:     */   {
/* 2368:3412 */     return getStyleAttribute("pause-before", null);
/* 2369:     */   }
/* 2370:     */   
/* 2371:     */   public void jsxSet_pauseBefore(String pauseBefore)
/* 2372:     */   {
/* 2373:3420 */     setStyleAttribute("pause-before", pauseBefore);
/* 2374:     */   }
/* 2375:     */   
/* 2376:     */   public String jsxGet_pitch()
/* 2377:     */   {
/* 2378:3428 */     return getStyleAttribute("pitch", null);
/* 2379:     */   }
/* 2380:     */   
/* 2381:     */   public void jsxSet_pitch(String pitch)
/* 2382:     */   {
/* 2383:3436 */     setStyleAttribute("pitch", pitch);
/* 2384:     */   }
/* 2385:     */   
/* 2386:     */   public String jsxGet_pitchRange()
/* 2387:     */   {
/* 2388:3444 */     return getStyleAttribute("pitch-range", null);
/* 2389:     */   }
/* 2390:     */   
/* 2391:     */   public void jsxSet_pitchRange(String pitchRange)
/* 2392:     */   {
/* 2393:3452 */     setStyleAttribute("pitch-range", pitchRange);
/* 2394:     */   }
/* 2395:     */   
/* 2396:     */   public int jsxGet_pixelBottom()
/* 2397:     */   {
/* 2398:3460 */     return pixelValue(jsxGet_bottom());
/* 2399:     */   }
/* 2400:     */   
/* 2401:     */   public void jsxSet_pixelBottom(int pixelBottom)
/* 2402:     */   {
/* 2403:3468 */     jsxSet_bottom(pixelBottom + "px");
/* 2404:     */   }
/* 2405:     */   
/* 2406:     */   public int jsxGet_pixelLeft()
/* 2407:     */   {
/* 2408:3476 */     return pixelValue(jsxGet_left());
/* 2409:     */   }
/* 2410:     */   
/* 2411:     */   public void jsxSet_pixelLeft(int pixelLeft)
/* 2412:     */   {
/* 2413:3484 */     jsxSet_left(pixelLeft + "px");
/* 2414:     */   }
/* 2415:     */   
/* 2416:     */   public int jsxGet_pixelRight()
/* 2417:     */   {
/* 2418:3492 */     return pixelValue(jsxGet_right());
/* 2419:     */   }
/* 2420:     */   
/* 2421:     */   public void jsxSet_pixelRight(int pixelRight)
/* 2422:     */   {
/* 2423:3500 */     jsxSet_right(pixelRight + "px");
/* 2424:     */   }
/* 2425:     */   
/* 2426:     */   public int jsxGet_pixelTop()
/* 2427:     */   {
/* 2428:3508 */     return pixelValue(jsxGet_top());
/* 2429:     */   }
/* 2430:     */   
/* 2431:     */   public void jsxSet_pixelTop(int pixelTop)
/* 2432:     */   {
/* 2433:3516 */     jsxSet_top(pixelTop + "px");
/* 2434:     */   }
/* 2435:     */   
/* 2436:     */   public int jsxGet_posBottom()
/* 2437:     */   {
/* 2438:3524 */     return 0;
/* 2439:     */   }
/* 2440:     */   
/* 2441:     */   public int jsxGet_posHeight()
/* 2442:     */   {
/* 2443:3540 */     return 0;
/* 2444:     */   }
/* 2445:     */   
/* 2446:     */   public String jsxGet_position()
/* 2447:     */   {
/* 2448:3556 */     return getStyleAttribute("position", null);
/* 2449:     */   }
/* 2450:     */   
/* 2451:     */   public void jsxSet_position(String position)
/* 2452:     */   {
/* 2453:3564 */     setStyleAttribute("position", position);
/* 2454:     */   }
/* 2455:     */   
/* 2456:     */   public int jsxGet_posLeft()
/* 2457:     */   {
/* 2458:3572 */     return 0;
/* 2459:     */   }
/* 2460:     */   
/* 2461:     */   public int jsxGet_posRight()
/* 2462:     */   {
/* 2463:3588 */     return 0;
/* 2464:     */   }
/* 2465:     */   
/* 2466:     */   public int jsxGet_posTop()
/* 2467:     */   {
/* 2468:3604 */     return 0;
/* 2469:     */   }
/* 2470:     */   
/* 2471:     */   public int jsxGet_posWidth()
/* 2472:     */   {
/* 2473:3620 */     return 0;
/* 2474:     */   }
/* 2475:     */   
/* 2476:     */   public String jsxGet_quotes()
/* 2477:     */   {
/* 2478:3636 */     return getStyleAttribute("quotes", null);
/* 2479:     */   }
/* 2480:     */   
/* 2481:     */   public void jsxSet_quotes(String quotes)
/* 2482:     */   {
/* 2483:3644 */     setStyleAttribute("quotes", quotes);
/* 2484:     */   }
/* 2485:     */   
/* 2486:     */   public String jsxGet_richness()
/* 2487:     */   {
/* 2488:3652 */     return getStyleAttribute("richness", null);
/* 2489:     */   }
/* 2490:     */   
/* 2491:     */   public void jsxSet_richness(String richness)
/* 2492:     */   {
/* 2493:3660 */     setStyleAttribute("richness", richness);
/* 2494:     */   }
/* 2495:     */   
/* 2496:     */   public String jsxGet_right()
/* 2497:     */   {
/* 2498:3668 */     return getStyleAttribute("right", null);
/* 2499:     */   }
/* 2500:     */   
/* 2501:     */   public void jsxSet_right(String right)
/* 2502:     */   {
/* 2503:3676 */     setStyleAttributePixelInt("right", right);
/* 2504:     */   }
/* 2505:     */   
/* 2506:     */   public String jsxGet_rubyAlign()
/* 2507:     */   {
/* 2508:3684 */     return getStyleAttribute("ruby-align", null);
/* 2509:     */   }
/* 2510:     */   
/* 2511:     */   public void jsxSet_rubyAlign(String rubyAlign)
/* 2512:     */   {
/* 2513:3692 */     setStyleAttribute("ruby-align", rubyAlign);
/* 2514:     */   }
/* 2515:     */   
/* 2516:     */   public String jsxGet_rubyOverhang()
/* 2517:     */   {
/* 2518:3700 */     return getStyleAttribute("ruby-overhang", null);
/* 2519:     */   }
/* 2520:     */   
/* 2521:     */   public void jsxSet_rubyOverhang(String rubyOverhang)
/* 2522:     */   {
/* 2523:3708 */     setStyleAttribute("ruby-overhang", rubyOverhang);
/* 2524:     */   }
/* 2525:     */   
/* 2526:     */   public String jsxGet_rubyPosition()
/* 2527:     */   {
/* 2528:3716 */     return getStyleAttribute("ruby-position", null);
/* 2529:     */   }
/* 2530:     */   
/* 2531:     */   public void jsxSet_rubyPosition(String rubyPosition)
/* 2532:     */   {
/* 2533:3724 */     setStyleAttribute("ruby-position", rubyPosition);
/* 2534:     */   }
/* 2535:     */   
/* 2536:     */   public String jsxGet_scrollbar3dLightColor()
/* 2537:     */   {
/* 2538:3732 */     return getStyleAttribute("scrollbar3d-light-color", null);
/* 2539:     */   }
/* 2540:     */   
/* 2541:     */   public void jsxSet_scrollbar3dLightColor(String scrollbar3dLightColor)
/* 2542:     */   {
/* 2543:3740 */     setStyleAttribute("scrollbar3d-light-color", scrollbar3dLightColor);
/* 2544:     */   }
/* 2545:     */   
/* 2546:     */   public String jsxGet_scrollbarArrowColor()
/* 2547:     */   {
/* 2548:3748 */     return getStyleAttribute("scrollbar-arrow-color", null);
/* 2549:     */   }
/* 2550:     */   
/* 2551:     */   public void jsxSet_scrollbarArrowColor(String scrollbarArrowColor)
/* 2552:     */   {
/* 2553:3756 */     setStyleAttribute("scrollbar-arrow-color", scrollbarArrowColor);
/* 2554:     */   }
/* 2555:     */   
/* 2556:     */   public String jsxGet_scrollbarBaseColor()
/* 2557:     */   {
/* 2558:3764 */     return getStyleAttribute("scrollbar-base-color", null);
/* 2559:     */   }
/* 2560:     */   
/* 2561:     */   public void jsxSet_scrollbarBaseColor(String scrollbarBaseColor)
/* 2562:     */   {
/* 2563:3772 */     setStyleAttribute("scrollbar-base-color", scrollbarBaseColor);
/* 2564:     */   }
/* 2565:     */   
/* 2566:     */   public String jsxGet_scrollbarDarkShadowColor()
/* 2567:     */   {
/* 2568:3780 */     return getStyleAttribute("scrollbar-dark-shadow-color", null);
/* 2569:     */   }
/* 2570:     */   
/* 2571:     */   public void jsxSet_scrollbarDarkShadowColor(String scrollbarDarkShadowColor)
/* 2572:     */   {
/* 2573:3788 */     setStyleAttribute("scrollbar-dark-shadow-color", scrollbarDarkShadowColor);
/* 2574:     */   }
/* 2575:     */   
/* 2576:     */   public String jsxGet_scrollbarFaceColor()
/* 2577:     */   {
/* 2578:3796 */     return getStyleAttribute("scrollbar-face-color", null);
/* 2579:     */   }
/* 2580:     */   
/* 2581:     */   public void jsxSet_scrollbarFaceColor(String scrollbarFaceColor)
/* 2582:     */   {
/* 2583:3804 */     setStyleAttribute("scrollbar-face-color", scrollbarFaceColor);
/* 2584:     */   }
/* 2585:     */   
/* 2586:     */   public String jsxGet_scrollbarHighlightColor()
/* 2587:     */   {
/* 2588:3812 */     return getStyleAttribute("scrollbar-highlight-color", null);
/* 2589:     */   }
/* 2590:     */   
/* 2591:     */   public void jsxSet_scrollbarHighlightColor(String scrollbarHighlightColor)
/* 2592:     */   {
/* 2593:3820 */     setStyleAttribute("scrollbar-highlight-color", scrollbarHighlightColor);
/* 2594:     */   }
/* 2595:     */   
/* 2596:     */   public String jsxGet_scrollbarShadowColor()
/* 2597:     */   {
/* 2598:3828 */     return getStyleAttribute("scrollbar-shadow-color", null);
/* 2599:     */   }
/* 2600:     */   
/* 2601:     */   public void jsxSet_scrollbarShadowColor(String scrollbarShadowColor)
/* 2602:     */   {
/* 2603:3836 */     setStyleAttribute("scrollbar-shadow-color", scrollbarShadowColor);
/* 2604:     */   }
/* 2605:     */   
/* 2606:     */   public String jsxGet_scrollbarTrackColor()
/* 2607:     */   {
/* 2608:3844 */     return getStyleAttribute("scrollbar-track-color", null);
/* 2609:     */   }
/* 2610:     */   
/* 2611:     */   public void jsxSet_scrollbarTrackColor(String scrollbarTrackColor)
/* 2612:     */   {
/* 2613:3852 */     setStyleAttribute("scrollbar-track-color", scrollbarTrackColor);
/* 2614:     */   }
/* 2615:     */   
/* 2616:     */   public String jsxGet_size()
/* 2617:     */   {
/* 2618:3860 */     return getStyleAttribute("size", null);
/* 2619:     */   }
/* 2620:     */   
/* 2621:     */   public void jsxSet_size(String size)
/* 2622:     */   {
/* 2623:3868 */     setStyleAttribute("size", size);
/* 2624:     */   }
/* 2625:     */   
/* 2626:     */   public String jsxGet_speak()
/* 2627:     */   {
/* 2628:3876 */     return getStyleAttribute("speak", null);
/* 2629:     */   }
/* 2630:     */   
/* 2631:     */   public void jsxSet_speak(String speak)
/* 2632:     */   {
/* 2633:3884 */     setStyleAttribute("speak", speak);
/* 2634:     */   }
/* 2635:     */   
/* 2636:     */   public String jsxGet_speakHeader()
/* 2637:     */   {
/* 2638:3892 */     return getStyleAttribute("speak-header", null);
/* 2639:     */   }
/* 2640:     */   
/* 2641:     */   public void jsxSet_speakHeader(String speakHeader)
/* 2642:     */   {
/* 2643:3900 */     setStyleAttribute("speak-header", speakHeader);
/* 2644:     */   }
/* 2645:     */   
/* 2646:     */   public String jsxGet_speakNumeral()
/* 2647:     */   {
/* 2648:3908 */     return getStyleAttribute("speak-numeral", null);
/* 2649:     */   }
/* 2650:     */   
/* 2651:     */   public void jsxSet_speakNumeral(String speakNumeral)
/* 2652:     */   {
/* 2653:3916 */     setStyleAttribute("speak-numeral", speakNumeral);
/* 2654:     */   }
/* 2655:     */   
/* 2656:     */   public String jsxGet_speakPunctuation()
/* 2657:     */   {
/* 2658:3924 */     return getStyleAttribute("speak-punctuation", null);
/* 2659:     */   }
/* 2660:     */   
/* 2661:     */   public void jsxSet_speakPunctuation(String speakPunctuation)
/* 2662:     */   {
/* 2663:3932 */     setStyleAttribute("speak-punctuation", speakPunctuation);
/* 2664:     */   }
/* 2665:     */   
/* 2666:     */   public String jsxGet_speechRate()
/* 2667:     */   {
/* 2668:3940 */     return getStyleAttribute("speech-rate", null);
/* 2669:     */   }
/* 2670:     */   
/* 2671:     */   public void jsxSet_speechRate(String speechRate)
/* 2672:     */   {
/* 2673:3948 */     setStyleAttribute("speech-rate", speechRate);
/* 2674:     */   }
/* 2675:     */   
/* 2676:     */   public String jsxGet_stress()
/* 2677:     */   {
/* 2678:3956 */     return getStyleAttribute("stress", null);
/* 2679:     */   }
/* 2680:     */   
/* 2681:     */   public void jsxSet_stress(String stress)
/* 2682:     */   {
/* 2683:3964 */     setStyleAttribute("stress", stress);
/* 2684:     */   }
/* 2685:     */   
/* 2686:     */   public String jsxGet_styleFloat()
/* 2687:     */   {
/* 2688:3972 */     return getStyleAttribute("float", null);
/* 2689:     */   }
/* 2690:     */   
/* 2691:     */   public void jsxSet_styleFloat(String value)
/* 2692:     */   {
/* 2693:3980 */     setStyleAttribute("float", value);
/* 2694:     */   }
/* 2695:     */   
/* 2696:     */   public String jsxGet_tableLayout()
/* 2697:     */   {
/* 2698:3988 */     return getStyleAttribute("table-layout", null);
/* 2699:     */   }
/* 2700:     */   
/* 2701:     */   public void jsxSet_tableLayout(String tableLayout)
/* 2702:     */   {
/* 2703:3996 */     setStyleAttribute("table-layout", tableLayout);
/* 2704:     */   }
/* 2705:     */   
/* 2706:     */   public String jsxGet_textAlign()
/* 2707:     */   {
/* 2708:4004 */     return getStyleAttribute("text-align", null);
/* 2709:     */   }
/* 2710:     */   
/* 2711:     */   public void jsxSet_textAlign(String textAlign)
/* 2712:     */   {
/* 2713:4012 */     setStyleAttribute("text-align", textAlign);
/* 2714:     */   }
/* 2715:     */   
/* 2716:     */   public String jsxGet_textAlignLast()
/* 2717:     */   {
/* 2718:4020 */     return getStyleAttribute("text-align-last", null);
/* 2719:     */   }
/* 2720:     */   
/* 2721:     */   public void jsxSet_textAlignLast(String textAlignLast)
/* 2722:     */   {
/* 2723:4028 */     setStyleAttribute("text-align-last", textAlignLast);
/* 2724:     */   }
/* 2725:     */   
/* 2726:     */   public String jsxGet_textAutospace()
/* 2727:     */   {
/* 2728:4036 */     return getStyleAttribute("text-autospace", null);
/* 2729:     */   }
/* 2730:     */   
/* 2731:     */   public void jsxSet_textAutospace(String textAutospace)
/* 2732:     */   {
/* 2733:4044 */     setStyleAttribute("text-autospace", textAutospace);
/* 2734:     */   }
/* 2735:     */   
/* 2736:     */   public String jsxGet_textDecoration()
/* 2737:     */   {
/* 2738:4052 */     return getStyleAttribute("text-decoration", null);
/* 2739:     */   }
/* 2740:     */   
/* 2741:     */   public void jsxSet_textDecoration(String textDecoration)
/* 2742:     */   {
/* 2743:4060 */     setStyleAttribute("text-decoration", textDecoration);
/* 2744:     */   }
/* 2745:     */   
/* 2746:     */   public boolean jsxGet_textDecorationBlink()
/* 2747:     */   {
/* 2748:4068 */     return false;
/* 2749:     */   }
/* 2750:     */   
/* 2751:     */   public boolean jsxGet_textDecorationLineThrough()
/* 2752:     */   {
/* 2753:4084 */     return false;
/* 2754:     */   }
/* 2755:     */   
/* 2756:     */   public boolean jsxGet_textDecorationNone()
/* 2757:     */   {
/* 2758:4100 */     return false;
/* 2759:     */   }
/* 2760:     */   
/* 2761:     */   public boolean jsxGet_textDecorationOverline()
/* 2762:     */   {
/* 2763:4116 */     return false;
/* 2764:     */   }
/* 2765:     */   
/* 2766:     */   public boolean jsxGet_textDecorationUnderline()
/* 2767:     */   {
/* 2768:4132 */     return false;
/* 2769:     */   }
/* 2770:     */   
/* 2771:     */   public String jsxGet_textIndent()
/* 2772:     */   {
/* 2773:4148 */     return getStyleAttribute("text-indent", null);
/* 2774:     */   }
/* 2775:     */   
/* 2776:     */   public void jsxSet_textIndent(String textIndent)
/* 2777:     */   {
/* 2778:4156 */     setStyleAttributePixelInt("text-indent", textIndent);
/* 2779:     */   }
/* 2780:     */   
/* 2781:     */   public String jsxGet_textJustify()
/* 2782:     */   {
/* 2783:4164 */     return getStyleAttribute("text-justify", null);
/* 2784:     */   }
/* 2785:     */   
/* 2786:     */   public void jsxSet_textJustify(String textJustify)
/* 2787:     */   {
/* 2788:4172 */     setStyleAttribute("text-justify", textJustify);
/* 2789:     */   }
/* 2790:     */   
/* 2791:     */   public String jsxGet_textJustifyTrim()
/* 2792:     */   {
/* 2793:4180 */     return getStyleAttribute("text-justify-trim", null);
/* 2794:     */   }
/* 2795:     */   
/* 2796:     */   public void jsxSet_textJustifyTrim(String textJustifyTrim)
/* 2797:     */   {
/* 2798:4188 */     setStyleAttribute("text-justify-trim", textJustifyTrim);
/* 2799:     */   }
/* 2800:     */   
/* 2801:     */   public String jsxGet_textKashida()
/* 2802:     */   {
/* 2803:4196 */     return getStyleAttribute("text-kashida", null);
/* 2804:     */   }
/* 2805:     */   
/* 2806:     */   public void jsxSet_textKashida(String textKashida)
/* 2807:     */   {
/* 2808:4204 */     setStyleAttribute("text-kashida", textKashida);
/* 2809:     */   }
/* 2810:     */   
/* 2811:     */   public String jsxGet_textKashidaSpace()
/* 2812:     */   {
/* 2813:4212 */     return getStyleAttribute("text-kashida-space", null);
/* 2814:     */   }
/* 2815:     */   
/* 2816:     */   public void jsxSet_textKashidaSpace(String textKashidaSpace)
/* 2817:     */   {
/* 2818:4220 */     setStyleAttribute("text-kashida-space", textKashidaSpace);
/* 2819:     */   }
/* 2820:     */   
/* 2821:     */   public String jsxGet_textOverflow()
/* 2822:     */   {
/* 2823:4228 */     return getStyleAttribute("text-overflow", null);
/* 2824:     */   }
/* 2825:     */   
/* 2826:     */   public void jsxSet_textOverflow(String textOverflow)
/* 2827:     */   {
/* 2828:4236 */     setStyleAttribute("text-overflow", textOverflow);
/* 2829:     */   }
/* 2830:     */   
/* 2831:     */   public String jsxGet_textShadow()
/* 2832:     */   {
/* 2833:4244 */     return getStyleAttribute("text-shadow", null);
/* 2834:     */   }
/* 2835:     */   
/* 2836:     */   public void jsxSet_textShadow(String textShadow)
/* 2837:     */   {
/* 2838:4252 */     setStyleAttribute("text-shadow", textShadow);
/* 2839:     */   }
/* 2840:     */   
/* 2841:     */   public String jsxGet_textTransform()
/* 2842:     */   {
/* 2843:4260 */     return getStyleAttribute("text-transform", null);
/* 2844:     */   }
/* 2845:     */   
/* 2846:     */   public void jsxSet_textTransform(String textTransform)
/* 2847:     */   {
/* 2848:4268 */     setStyleAttribute("text-transform", textTransform);
/* 2849:     */   }
/* 2850:     */   
/* 2851:     */   public String jsxGet_textUnderlinePosition()
/* 2852:     */   {
/* 2853:4276 */     return getStyleAttribute("text-underline-position", null);
/* 2854:     */   }
/* 2855:     */   
/* 2856:     */   public void jsxSet_textUnderlinePosition(String textUnderlinePosition)
/* 2857:     */   {
/* 2858:4284 */     setStyleAttribute("text-underline-position", textUnderlinePosition);
/* 2859:     */   }
/* 2860:     */   
/* 2861:     */   public String jsxGet_top()
/* 2862:     */   {
/* 2863:4292 */     return getStyleAttribute("top", null);
/* 2864:     */   }
/* 2865:     */   
/* 2866:     */   public void jsxSet_top(String top)
/* 2867:     */   {
/* 2868:4300 */     setStyleAttributePixelInt("top", top);
/* 2869:     */   }
/* 2870:     */   
/* 2871:     */   public String jsxGet_unicodeBidi()
/* 2872:     */   {
/* 2873:4308 */     return getStyleAttribute("unicode-bidi", null);
/* 2874:     */   }
/* 2875:     */   
/* 2876:     */   public void jsxSet_unicodeBidi(String unicodeBidi)
/* 2877:     */   {
/* 2878:4316 */     setStyleAttribute("unicode-bidi", unicodeBidi);
/* 2879:     */   }
/* 2880:     */   
/* 2881:     */   public String jsxGet_verticalAlign()
/* 2882:     */   {
/* 2883:4324 */     return getStyleAttribute("vertical-align", null);
/* 2884:     */   }
/* 2885:     */   
/* 2886:     */   public void jsxSet_verticalAlign(String verticalAlign)
/* 2887:     */   {
/* 2888:4332 */     setStyleAttributePixelInt("vertical-align", verticalAlign);
/* 2889:     */   }
/* 2890:     */   
/* 2891:     */   public String jsxGet_visibility()
/* 2892:     */   {
/* 2893:4340 */     return getStyleAttribute("visibility", null);
/* 2894:     */   }
/* 2895:     */   
/* 2896:     */   public void jsxSet_visibility(String visibility)
/* 2897:     */   {
/* 2898:4348 */     setStyleAttribute("visibility", visibility);
/* 2899:     */   }
/* 2900:     */   
/* 2901:     */   public String jsxGet_voiceFamily()
/* 2902:     */   {
/* 2903:4356 */     return getStyleAttribute("voice-family", null);
/* 2904:     */   }
/* 2905:     */   
/* 2906:     */   public void jsxSet_voiceFamily(String voiceFamily)
/* 2907:     */   {
/* 2908:4364 */     setStyleAttribute("voice-family", voiceFamily);
/* 2909:     */   }
/* 2910:     */   
/* 2911:     */   public String jsxGet_volume()
/* 2912:     */   {
/* 2913:4372 */     return getStyleAttribute("volume", null);
/* 2914:     */   }
/* 2915:     */   
/* 2916:     */   public void jsxSet_volume(String volume)
/* 2917:     */   {
/* 2918:4380 */     setStyleAttribute("volume", volume);
/* 2919:     */   }
/* 2920:     */   
/* 2921:     */   public String jsxGet_whiteSpace()
/* 2922:     */   {
/* 2923:4388 */     return getStyleAttribute("white-space", null);
/* 2924:     */   }
/* 2925:     */   
/* 2926:     */   public void jsxSet_whiteSpace(String whiteSpace)
/* 2927:     */   {
/* 2928:4396 */     setStyleAttribute("white-space", whiteSpace);
/* 2929:     */   }
/* 2930:     */   
/* 2931:     */   public String jsxGet_widows()
/* 2932:     */   {
/* 2933:4404 */     return getStyleAttribute("widows", null);
/* 2934:     */   }
/* 2935:     */   
/* 2936:     */   public void jsxSet_widows(String widows)
/* 2937:     */   {
/* 2938:4412 */     setStyleAttribute("widows", widows);
/* 2939:     */   }
/* 2940:     */   
/* 2941:     */   public String jsxGet_width()
/* 2942:     */   {
/* 2943:4420 */     return getStyleAttribute("width", null);
/* 2944:     */   }
/* 2945:     */   
/* 2946:     */   public void jsxSet_width(String width)
/* 2947:     */   {
/* 2948:4428 */     setStyleAttributePixelInt("width", width);
/* 2949:     */   }
/* 2950:     */   
/* 2951:     */   public String jsxGet_wordBreak()
/* 2952:     */   {
/* 2953:4436 */     return getStyleAttribute("word-break", null);
/* 2954:     */   }
/* 2955:     */   
/* 2956:     */   public void jsxSet_wordBreak(String wordBreak)
/* 2957:     */   {
/* 2958:4444 */     setStyleAttribute("word-break", wordBreak);
/* 2959:     */   }
/* 2960:     */   
/* 2961:     */   public String jsxGet_wordSpacing()
/* 2962:     */   {
/* 2963:4452 */     return getStyleAttribute("word-spacing", null);
/* 2964:     */   }
/* 2965:     */   
/* 2966:     */   public void jsxSet_wordSpacing(String wordSpacing)
/* 2967:     */   {
/* 2968:4460 */     setStyleAttributePixelInt("word-spacing", wordSpacing);
/* 2969:     */   }
/* 2970:     */   
/* 2971:     */   public String jsxGet_wordWrap()
/* 2972:     */   {
/* 2973:4468 */     return getStyleAttribute("word-wrap", null);
/* 2974:     */   }
/* 2975:     */   
/* 2976:     */   public void jsxSet_wordWrap(String wordWrap)
/* 2977:     */   {
/* 2978:4476 */     setStyleAttribute("word-wrap", wordWrap);
/* 2979:     */   }
/* 2980:     */   
/* 2981:     */   public String jsxGet_writingMode()
/* 2982:     */   {
/* 2983:4484 */     return getStyleAttribute("writing-mode", null);
/* 2984:     */   }
/* 2985:     */   
/* 2986:     */   public void jsxSet_writingMode(String writingMode)
/* 2987:     */   {
/* 2988:4492 */     setStyleAttribute("writing-mode", writingMode);
/* 2989:     */   }
/* 2990:     */   
/* 2991:     */   public Object jsxGet_zIndex()
/* 2992:     */   {
/* 2993:4500 */     String value = getStyleAttribute("z-index", null);
/* 2994:4501 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_ZINDEX_TYPE_NUMBER))
/* 2995:     */     {
/* 2996:4502 */       if ((value == null) || (Context.getUndefinedValue().equals(value)) || (org.apache.commons.lang.StringUtils.isEmpty(value.toString()))) {
/* 2997:4505 */         return Integer.valueOf(0);
/* 2998:     */       }
/* 2999:     */       try
/* 3000:     */       {
/* 3001:4508 */         Double numericValue = Double.valueOf(value);
/* 3002:4509 */         return Integer.valueOf(numericValue.intValue());
/* 3003:     */       }
/* 3004:     */       catch (NumberFormatException e)
/* 3005:     */       {
/* 3006:4512 */         return Integer.valueOf(0);
/* 3007:     */       }
/* 3008:     */     }
/* 3009:     */     try
/* 3010:     */     {
/* 3011:4518 */       Integer.parseInt(value);
/* 3012:4519 */       return value;
/* 3013:     */     }
/* 3014:     */     catch (NumberFormatException e) {}
/* 3015:4522 */     return "";
/* 3016:     */   }
/* 3017:     */   
/* 3018:     */   public void jsxSet_zIndex(Object zIndex)
/* 3019:     */   {
/* 3020:4531 */     if ((zIndex == null) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_ZINDEX_UNDEFINED_OR_NULL_THROWS_ERROR))) {
/* 3021:4533 */       throw new EvaluatorException("Null is invalid for z-index.");
/* 3022:     */     }
/* 3023:4537 */     if ((zIndex == null) || (org.apache.commons.lang.StringUtils.isEmpty(zIndex.toString())))
/* 3024:     */     {
/* 3025:4538 */       setStyleAttribute("z-index", "");
/* 3026:4539 */       return;
/* 3027:     */     }
/* 3028:4542 */     if (Context.getUndefinedValue().equals(zIndex))
/* 3029:     */     {
/* 3030:4543 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_ZINDEX_UNDEFINED_OR_NULL_THROWS_ERROR)) {
/* 3031:4544 */         throw new EvaluatorException("Undefind is invalid for z-index.");
/* 3032:     */       }
/* 3033:4546 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_ZINDEX_UNDEFINED_FORCES_RESET)) {
/* 3034:4547 */         setStyleAttribute("z-index", "");
/* 3035:     */       }
/* 3036:4549 */       return;
/* 3037:     */     }
/* 3038:4553 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_ZINDEX_TYPE_NUMBER))
/* 3039:     */     {
/* 3040:     */       Double d;
/* 3041:     */       Double d;
/* 3042:4555 */       if ((zIndex instanceof Double)) {
/* 3043:4556 */         d = (Double)zIndex;
/* 3044:     */       } else {
/* 3045:     */         try
/* 3046:     */         {
/* 3047:4560 */           d = Double.valueOf(zIndex.toString());
/* 3048:     */         }
/* 3049:     */         catch (NumberFormatException e)
/* 3050:     */         {
/* 3051:4563 */           throw new WrappedException(e);
/* 3052:     */         }
/* 3053:     */       }
/* 3054:4566 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_ZINDEX_ROUNDED)) {
/* 3055:4567 */         setStyleAttribute("z-index", Integer.toString(Math.round(d.floatValue() - 1.0E-005F)));
/* 3056:     */       } else {
/* 3057:4570 */         setStyleAttribute("z-index", Integer.toString(d.intValue()));
/* 3058:     */       }
/* 3059:4572 */       return;
/* 3060:     */     }
/* 3061:4576 */     if ((zIndex instanceof Number))
/* 3062:     */     {
/* 3063:4577 */       Number number = (Number)zIndex;
/* 3064:4578 */       if (number.doubleValue() % 1.0D == 0.0D) {
/* 3065:4579 */         setStyleAttribute("z-index", Integer.toString(number.intValue()));
/* 3066:     */       }
/* 3067:4581 */       return;
/* 3068:     */     }
/* 3069:     */     try
/* 3070:     */     {
/* 3071:4584 */       int i = Integer.parseInt(zIndex.toString());
/* 3072:4585 */       setStyleAttribute("z-index", Integer.toString(i));
/* 3073:     */     }
/* 3074:     */     catch (NumberFormatException e) {}
/* 3075:     */   }
/* 3076:     */   
/* 3077:     */   public String jsxGet_zoom()
/* 3078:     */   {
/* 3079:4597 */     return getStyleAttribute("zoom", null);
/* 3080:     */   }
/* 3081:     */   
/* 3082:     */   public void jsxSet_zoom(String zoom)
/* 3083:     */   {
/* 3084:4605 */     setStyleAttribute("zoom", zoom);
/* 3085:     */   }
/* 3086:     */   
/* 3087:     */   public String jsxFunction_getPropertyValue(String name)
/* 3088:     */   {
/* 3089:4614 */     if ((name != null) && (name.contains("-")))
/* 3090:     */     {
/* 3091:4615 */       Object value = getProperty(this, camelize(name));
/* 3092:4616 */       if ((value instanceof String)) {
/* 3093:4617 */         return (String)value;
/* 3094:     */       }
/* 3095:     */     }
/* 3096:4620 */     return getStyleAttribute(name, null);
/* 3097:     */   }
/* 3098:     */   
/* 3099:     */   public CSSValue jsxFunction_getPropertyCSSValue(String name)
/* 3100:     */   {
/* 3101:4629 */     LOG.info("getPropertyCSSValue(" + name + "): getPropertyCSSValue support is experimental");
/* 3102:4632 */     if (this.styleDeclaration_ == null)
/* 3103:     */     {
/* 3104:4633 */       String uri = getDomNodeOrDie().getPage().getWebResponse().getWebRequest().getUrl().toExternalForm();
/* 3105:     */       
/* 3106:4635 */       String styleAttribute = this.jsElement_.getDomNodeOrDie().getAttribute("style");
/* 3107:4636 */       InputSource source = new InputSource(new StringReader(styleAttribute));
/* 3108:4637 */       source.setURI(uri);
/* 3109:4638 */       ErrorHandler errorHandler = getWindow().getWebWindow().getWebClient().getCssErrorHandler();
/* 3110:4639 */       CSSOMParser parser = new CSSOMParser(new SACParserCSS21());
/* 3111:4640 */       parser.setErrorHandler(errorHandler);
/* 3112:     */       try
/* 3113:     */       {
/* 3114:4642 */         this.styleDeclaration_ = parser.parseStyleDeclaration(source);
/* 3115:     */       }
/* 3116:     */       catch (IOException e)
/* 3117:     */       {
/* 3118:4645 */         throw new RuntimeException(e);
/* 3119:     */       }
/* 3120:     */     }
/* 3121:4648 */     org.w3c.dom.css.CSSValue cssValue = this.styleDeclaration_.getPropertyCSSValue(name);
/* 3122:4649 */     if (cssValue == null)
/* 3123:     */     {
/* 3124:4650 */       CSSValueImpl newValue = new CSSValueImpl();
/* 3125:4651 */       newValue.setFloatValue((short)5, 0.0F);
/* 3126:4652 */       cssValue = newValue;
/* 3127:     */     }
/* 3128:4656 */     String cssText = cssValue.getCssText();
/* 3129:4657 */     if (cssText.startsWith("rgb("))
/* 3130:     */     {
/* 3131:4658 */       String formatedCssText = org.apache.commons.lang.StringUtils.replace(cssText, ",", ", ");
/* 3132:4659 */       cssValue.setCssText(formatedCssText);
/* 3133:     */     }
/* 3134:4662 */     return new CSSPrimitiveValue(this.jsElement_, (org.w3c.dom.css.CSSPrimitiveValue)cssValue);
/* 3135:     */   }
/* 3136:     */   
/* 3137:     */   public boolean jsxFunction_removeExpression(String propertyName)
/* 3138:     */   {
/* 3139:4685 */     return true;
/* 3140:     */   }
/* 3141:     */   
/* 3142:     */   public Object jsxFunction_getAttribute(String name, int flag)
/* 3143:     */   {
/* 3144:4698 */     if (flag == 1) {
/* 3145:4700 */       return getStyleAttribute(name, null);
/* 3146:     */     }
/* 3147:4704 */     Map<String, StyleElement> map = getStyleMap();
/* 3148:4705 */     for (String key : map.keySet()) {
/* 3149:4706 */       if (key.equalsIgnoreCase(name)) {
/* 3150:4707 */         return ((StyleElement)map.get(key)).getValue();
/* 3151:     */       }
/* 3152:     */     }
/* 3153:4710 */     return "";
/* 3154:     */   }
/* 3155:     */   
/* 3156:     */   public void jsxFunction_setAttribute(String name, String value, Object flag)
/* 3157:     */   {
/* 3158:     */     int flagInt;
/* 3159:     */     int flagInt;
/* 3160:4723 */     if (flag == Undefined.instance) {
/* 3161:4724 */       flagInt = 1;
/* 3162:     */     } else {
/* 3163:4727 */       flagInt = (int)Context.toNumber(flag);
/* 3164:     */     }
/* 3165:4729 */     if (flagInt == 0)
/* 3166:     */     {
/* 3167:4731 */       Map<String, StyleElement> map = getStyleMap();
/* 3168:4732 */       for (String key : map.keySet()) {
/* 3169:4733 */         if (key.equalsIgnoreCase(name)) {
/* 3170:4734 */           setStyleAttribute(key, value);
/* 3171:     */         }
/* 3172:     */       }
/* 3173:     */     }
/* 3174:4740 */     else if (getStyleAttribute(name, null).length() > 0)
/* 3175:     */     {
/* 3176:4741 */       setStyleAttribute(name, value);
/* 3177:     */     }
/* 3178:     */   }
/* 3179:     */   
/* 3180:     */   public boolean jsxFunction_removeAttribute(String name, Object flag)
/* 3181:     */   {
/* 3182:     */     int flagInt;
/* 3183:     */     int flagInt;
/* 3184:4756 */     if (flag == Undefined.instance) {
/* 3185:4757 */       flagInt = 1;
/* 3186:     */     } else {
/* 3187:4760 */       flagInt = (int)Context.toNumber(flag);
/* 3188:     */     }
/* 3189:4762 */     if (flagInt == 0)
/* 3190:     */     {
/* 3191:4764 */       String lastName = null;
/* 3192:4765 */       Map<String, StyleElement> map = getStyleMap();
/* 3193:4766 */       for (String key : map.keySet()) {
/* 3194:4767 */         if (key.equalsIgnoreCase(name)) {
/* 3195:4768 */           lastName = key;
/* 3196:     */         }
/* 3197:     */       }
/* 3198:4771 */       if (lastName != null)
/* 3199:     */       {
/* 3200:4772 */         removeStyleAttribute(lastName);
/* 3201:4773 */         return true;
/* 3202:     */       }
/* 3203:4775 */       return false;
/* 3204:     */     }
/* 3205:4779 */     String s = getStyleAttribute(name, null);
/* 3206:4780 */     if (s.length() > 0)
/* 3207:     */     {
/* 3208:4781 */       removeStyleAttribute(name);
/* 3209:4782 */       return true;
/* 3210:     */     }
/* 3211:4784 */     return false;
/* 3212:     */   }
/* 3213:     */   
/* 3214:     */   private String findColor(String text)
/* 3215:     */   {
/* 3216:4793 */     Color tmpColor = com.gargoylesoftware.htmlunit.util.StringUtils.findColorRGB(text);
/* 3217:4794 */     if (tmpColor != null) {
/* 3218:4795 */       return com.gargoylesoftware.htmlunit.util.StringUtils.formatColor(tmpColor);
/* 3219:     */     }
/* 3220:4798 */     String[] tokens = org.apache.commons.lang.StringUtils.split(text, ' ');
/* 3221:4799 */     for (String token : tokens)
/* 3222:     */     {
/* 3223:4800 */       if (isColorKeyword(token)) {
/* 3224:4801 */         return token;
/* 3225:     */       }
/* 3226:4804 */       tmpColor = com.gargoylesoftware.htmlunit.util.StringUtils.asColorHexadecimal(token);
/* 3227:4805 */       if (tmpColor != null)
/* 3228:     */       {
/* 3229:4806 */         if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_GET_BACKGROUND_COLOR_FOR_COMPUTED_STYLE_RETURNS_RGB)) {
/* 3230:4808 */           return com.gargoylesoftware.htmlunit.util.StringUtils.formatColor(tmpColor);
/* 3231:     */         }
/* 3232:4810 */         return token;
/* 3233:     */       }
/* 3234:     */     }
/* 3235:4813 */     return null;
/* 3236:     */   }
/* 3237:     */   
/* 3238:     */   private String findImageUrl(String text)
/* 3239:     */   {
/* 3240:4822 */     Matcher m = URL_PATTERN.matcher(text);
/* 3241:4823 */     if (m.find())
/* 3242:     */     {
/* 3243:4824 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.CSS_IMAGE_URL_QUOTED)) {
/* 3244:4825 */         return "url(\"" + m.group(1) + "\")";
/* 3245:     */       }
/* 3246:4827 */       return "url(" + m.group(1) + ")";
/* 3247:     */     }
/* 3248:4829 */     return null;
/* 3249:     */   }
/* 3250:     */   
/* 3251:     */   private static String findPosition(String text)
/* 3252:     */   {
/* 3253:4838 */     Matcher m = POSITION_PATTERN.matcher(text);
/* 3254:4839 */     if (m.find()) {
/* 3255:4840 */       return m.group(1) + " " + m.group(3);
/* 3256:     */     }
/* 3257:4842 */     m = POSITION_PATTERN2.matcher(text);
/* 3258:4843 */     if (m.find()) {
/* 3259:4844 */       return m.group(1) + " " + m.group(2);
/* 3260:     */     }
/* 3261:4846 */     m = POSITION_PATTERN3.matcher(text);
/* 3262:4847 */     if (m.find()) {
/* 3263:4848 */       return m.group(2) + " " + m.group(1);
/* 3264:     */     }
/* 3265:4850 */     return null;
/* 3266:     */   }
/* 3267:     */   
/* 3268:     */   private static String findRepeat(String text)
/* 3269:     */   {
/* 3270:4859 */     if (text.contains("repeat-x")) {
/* 3271:4860 */       return "repeat-x";
/* 3272:     */     }
/* 3273:4862 */     if (text.contains("repeat-y")) {
/* 3274:4863 */       return "repeat-y";
/* 3275:     */     }
/* 3276:4865 */     if (text.contains("no-repeat")) {
/* 3277:4866 */       return "no-repeat";
/* 3278:     */     }
/* 3279:4868 */     if (text.contains("repeat")) {
/* 3280:4869 */       return "repeat";
/* 3281:     */     }
/* 3282:4871 */     return null;
/* 3283:     */   }
/* 3284:     */   
/* 3285:     */   private static String findAttachment(String text)
/* 3286:     */   {
/* 3287:4880 */     if (text.contains("scroll")) {
/* 3288:4881 */       return "scroll";
/* 3289:     */     }
/* 3290:4883 */     if (text.contains("fixed")) {
/* 3291:4884 */       return "fixed";
/* 3292:     */     }
/* 3293:4886 */     return null;
/* 3294:     */   }
/* 3295:     */   
/* 3296:     */   private static String findBorderStyle(String text)
/* 3297:     */   {
/* 3298:4895 */     for (String token : org.apache.commons.lang.StringUtils.split(text, ' ')) {
/* 3299:4896 */       if (isBorderStyle(token)) {
/* 3300:4897 */         return token;
/* 3301:     */       }
/* 3302:     */     }
/* 3303:4900 */     return null;
/* 3304:     */   }
/* 3305:     */   
/* 3306:     */   private static String findBorderWidth(String text)
/* 3307:     */   {
/* 3308:4909 */     for (String token : org.apache.commons.lang.StringUtils.split(text, ' ')) {
/* 3309:4910 */       if (isBorderWidth(token)) {
/* 3310:4911 */         return token;
/* 3311:     */       }
/* 3312:     */     }
/* 3313:4914 */     return null;
/* 3314:     */   }
/* 3315:     */   
/* 3316:     */   private static boolean isColorKeyword(String token)
/* 3317:     */   {
/* 3318:4923 */     return CSSColors_.containsKey(token.toLowerCase());
/* 3319:     */   }
/* 3320:     */   
/* 3321:     */   public static String toRGBColor(String color)
/* 3322:     */   {
/* 3323:4933 */     String rgbValue = (String)CSSColors_.get(color.toLowerCase());
/* 3324:4934 */     if (rgbValue != null) {
/* 3325:4935 */       return rgbValue;
/* 3326:     */     }
/* 3327:4937 */     return color;
/* 3328:     */   }
/* 3329:     */   
/* 3330:     */   private static boolean isBorderStyle(String token)
/* 3331:     */   {
/* 3332:4946 */     return ("none".equalsIgnoreCase(token)) || ("hidden".equalsIgnoreCase(token)) || ("dotted".equalsIgnoreCase(token)) || ("dashed".equalsIgnoreCase(token)) || ("solid".equalsIgnoreCase(token)) || ("double".equalsIgnoreCase(token)) || ("groove".equalsIgnoreCase(token)) || ("ridge".equalsIgnoreCase(token)) || ("inset".equalsIgnoreCase(token)) || ("outset".equalsIgnoreCase(token));
/* 3333:     */   }
/* 3334:     */   
/* 3335:     */   private static boolean isBorderWidth(String token)
/* 3336:     */   {
/* 3337:4959 */     return ("thin".equalsIgnoreCase(token)) || ("medium".equalsIgnoreCase(token)) || ("thick".equalsIgnoreCase(token)) || (isLength(token));
/* 3338:     */   }
/* 3339:     */   
/* 3340:     */   private static boolean isLength(String token)
/* 3341:     */   {
/* 3342:4969 */     if ((token.endsWith("em")) || (token.endsWith("ex")) || (token.endsWith("px")) || (token.endsWith("in")) || (token.endsWith("cm")) || (token.endsWith("mm")) || (token.endsWith("pt")) || (token.endsWith("pc")) || (token.endsWith("%")))
/* 3343:     */     {
/* 3344:4973 */       if (token.endsWith("%")) {
/* 3345:4974 */         token = token.substring(0, token.length() - 1);
/* 3346:     */       } else {
/* 3347:4977 */         token = token.substring(0, token.length() - 2);
/* 3348:     */       }
/* 3349:     */       try
/* 3350:     */       {
/* 3351:4980 */         Float.parseFloat(token);
/* 3352:4981 */         return true;
/* 3353:     */       }
/* 3354:     */       catch (NumberFormatException e) {}
/* 3355:     */     }
/* 3356:4987 */     return false;
/* 3357:     */   }
/* 3358:     */   
/* 3359:     */   protected static int pixelValue(HTMLElement element, CssValue value)
/* 3360:     */   {
/* 3361:5000 */     String s = value.get(element);
/* 3362:5001 */     if ((s.endsWith("%")) || ((s.length() == 0) && ((element instanceof HTMLHtmlElement))))
/* 3363:     */     {
/* 3364:5002 */       int i = NumberUtils.toInt(TO_INT_PATTERN.matcher(s).replaceAll("$1"), 100);
/* 3365:5003 */       HTMLElement parent = element.getParentHTMLElement();
/* 3366:5004 */       int absoluteValue = parent == null ? value.getWindowDefaultValue() : pixelValue(parent, value);
/* 3367:5005 */       return (int)(i / 100.0D * absoluteValue);
/* 3368:     */     }
/* 3369:5007 */     if ((s.length() == 0) && ((element instanceof HTMLCanvasElement))) {
/* 3370:5008 */       return value.getWindowDefaultValue();
/* 3371:     */     }
/* 3372:5010 */     return pixelValue(s);
/* 3373:     */   }
/* 3374:     */   
/* 3375:     */   protected static int pixelValue(String value)
/* 3376:     */   {
/* 3377:5023 */     int i = NumberUtils.toInt(TO_INT_PATTERN.matcher(value).replaceAll("$1"), 0);
/* 3378:5024 */     if (value.endsWith("px")) {
/* 3379:5025 */       return i;
/* 3380:     */     }
/* 3381:5027 */     if (value.endsWith("em")) {
/* 3382:5028 */       return i * 16;
/* 3383:     */     }
/* 3384:5030 */     if (value.endsWith("ex")) {
/* 3385:5031 */       return i * 10;
/* 3386:     */     }
/* 3387:5033 */     if (value.endsWith("in")) {
/* 3388:5034 */       return i * 150;
/* 3389:     */     }
/* 3390:5036 */     if (value.endsWith("cm")) {
/* 3391:5037 */       return i * 50;
/* 3392:     */     }
/* 3393:5039 */     if (value.endsWith("mm")) {
/* 3394:5040 */       return i * 5;
/* 3395:     */     }
/* 3396:5042 */     if (value.endsWith("pt")) {
/* 3397:5043 */       return i * 2;
/* 3398:     */     }
/* 3399:5045 */     if (value.endsWith("pc")) {
/* 3400:5046 */       return i * 24;
/* 3401:     */     }
/* 3402:5049 */     return i;
/* 3403:     */   }
/* 3404:     */   
/* 3405:     */   protected static abstract class CssValue
/* 3406:     */   {
/* 3407:     */     private final int windowDefaultValue_;
/* 3408:     */     
/* 3409:     */     public CssValue(int windowDefaultValue)
/* 3410:     */     {
/* 3411:5064 */       this.windowDefaultValue_ = windowDefaultValue;
/* 3412:     */     }
/* 3413:     */     
/* 3414:     */     public int getWindowDefaultValue()
/* 3415:     */     {
/* 3416:5072 */       return this.windowDefaultValue_;
/* 3417:     */     }
/* 3418:     */     
/* 3419:     */     public final String get(HTMLElement element)
/* 3420:     */     {
/* 3421:5081 */       ComputedCSSStyleDeclaration style = element.jsxGet_currentStyle();
/* 3422:5082 */       String value = get(style);
/* 3423:5083 */       return value;
/* 3424:     */     }
/* 3425:     */     
/* 3426:     */     public abstract String get(ComputedCSSStyleDeclaration paramComputedCSSStyleDeclaration);
/* 3427:     */   }
/* 3428:     */   
/* 3429:     */   public String toString()
/* 3430:     */   {
/* 3431:5098 */     if (this.jsElement_ == null) {
/* 3432:5099 */       return "CSSStyleDeclaration for 'null'";
/* 3433:     */     }
/* 3434:5101 */     String style = this.jsElement_.getDomNodeOrDie().getAttribute("style");
/* 3435:5102 */     return "CSSStyleDeclaration for '" + style + "'";
/* 3436:     */   }
/* 3437:     */   
/* 3438:     */   protected static class StyleElement
/* 3439:     */     implements Comparable<StyleElement>
/* 3440:     */   {
/* 3441:     */     private final String name_;
/* 3442:     */     private final String value_;
/* 3443:     */     private final String priority_;
/* 3444:     */     private final long index_;
/* 3445:     */     private final SelectorSpecificity specificity_;
/* 3446:     */     
/* 3447:     */     protected StyleElement(String name, String value, String priority, SelectorSpecificity specificity, long index)
/* 3448:     */     {
/* 3449:5126 */       this.name_ = name;
/* 3450:5127 */       this.value_ = value;
/* 3451:5128 */       this.priority_ = priority;
/* 3452:5129 */       this.index_ = index;
/* 3453:5130 */       this.specificity_ = specificity;
/* 3454:     */     }
/* 3455:     */     
/* 3456:     */     protected StyleElement(String name, String value, long index)
/* 3457:     */     {
/* 3458:5140 */       this(name, value, "", SelectorSpecificity.FROM_STYLE_ATTRIBUTE, index);
/* 3459:     */     }
/* 3460:     */     
/* 3461:     */     protected StyleElement(String name, String value)
/* 3462:     */     {
/* 3463:5149 */       this(name, value, -9223372036854775808L);
/* 3464:     */     }
/* 3465:     */     
/* 3466:     */     public String getName()
/* 3467:     */     {
/* 3468:5157 */       return this.name_;
/* 3469:     */     }
/* 3470:     */     
/* 3471:     */     public String getValue()
/* 3472:     */     {
/* 3473:5165 */       return this.value_;
/* 3474:     */     }
/* 3475:     */     
/* 3476:     */     public String getPriority()
/* 3477:     */     {
/* 3478:5173 */       return this.priority_;
/* 3479:     */     }
/* 3480:     */     
/* 3481:     */     public SelectorSpecificity getSpecificity()
/* 3482:     */     {
/* 3483:5181 */       return this.specificity_;
/* 3484:     */     }
/* 3485:     */     
/* 3486:     */     public long getIndex()
/* 3487:     */     {
/* 3488:5189 */       return this.index_;
/* 3489:     */     }
/* 3490:     */     
/* 3491:     */     public boolean isDefault()
/* 3492:     */     {
/* 3493:5201 */       return this.index_ == -9223372036854775808L;
/* 3494:     */     }
/* 3495:     */     
/* 3496:     */     public String toString()
/* 3497:     */     {
/* 3498:5209 */       return "[" + this.index_ + "]" + this.name_ + "=" + this.value_;
/* 3499:     */     }
/* 3500:     */     
/* 3501:     */     public int compareTo(StyleElement e)
/* 3502:     */     {
/* 3503:5216 */       if (e != null)
/* 3504:     */       {
/* 3505:5217 */         long styleIndex = e.index_;
/* 3506:     */         
/* 3507:5219 */         return this.index_ == styleIndex ? 0 : this.index_ < styleIndex ? -1 : 1;
/* 3508:     */       }
/* 3509:5221 */       return 1;
/* 3510:     */     }
/* 3511:     */   }
/* 3512:     */   
/* 3513:     */   protected void setStyleAttributePixelInt(String name, String value)
/* 3514:     */   {
/* 3515:5231 */     if (value.endsWith("px")) {
/* 3516:5232 */       value = value.substring(0, value.length() - 2);
/* 3517:     */     }
/* 3518:     */     try
/* 3519:     */     {
/* 3520:5235 */       float floatValue = Float.parseFloat(value);
/* 3521:5236 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_26)) {
/* 3522:5237 */         value = Integer.toString((int)floatValue) + "px";
/* 3523:5240 */       } else if (floatValue % 1.0F == 0.0F) {
/* 3524:5241 */         value = Integer.toString((int)floatValue) + "px";
/* 3525:     */       } else {
/* 3526:5244 */         value = Float.toString(floatValue) + "px";
/* 3527:     */       }
/* 3528:     */     }
/* 3529:     */     catch (Exception e) {}
/* 3530:5251 */     setStyleAttribute(name, value);
/* 3531:     */   }
/* 3532:     */   
/* 3533:     */   public CSSStyleDeclaration() {}
/* 3534:     */   
/* 3535:     */   public void jsxSet_posBottom(int posBottom) {}
/* 3536:     */   
/* 3537:     */   public void jsxSet_posHeight(int posHeight) {}
/* 3538:     */   
/* 3539:     */   public void jsxSet_posLeft(int posLeft) {}
/* 3540:     */   
/* 3541:     */   public void jsxSet_posRight(int posRight) {}
/* 3542:     */   
/* 3543:     */   public void jsxSet_posTop(int posTop) {}
/* 3544:     */   
/* 3545:     */   public void jsxSet_posWidth(int posWidth) {}
/* 3546:     */   
/* 3547:     */   public void jsxSet_textDecorationBlink(boolean textDecorationBlink) {}
/* 3548:     */   
/* 3549:     */   public void jsxSet_textDecorationLineThrough(boolean textDecorationLineThrough) {}
/* 3550:     */   
/* 3551:     */   public void jsxSet_textDecorationNone(boolean textDecorationNone) {}
/* 3552:     */   
/* 3553:     */   public void jsxSet_textDecorationOverline(boolean textDecorationOverline) {}
/* 3554:     */   
/* 3555:     */   public void jsxSet_textDecorationUnderline(boolean textDecorationUnderline) {}
/* 3556:     */   
/* 3557:     */   public void jsxFunction_setExpression(String propertyName, String expression, String language) {}
/* 3558:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleDeclaration
 * JD-Core Version:    0.7.0.1
 */