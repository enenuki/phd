/*    1:     */ package org.apache.xml.serializer.dom3;
/*    2:     */ 
/*    3:     */ import java.io.FileOutputStream;
/*    4:     */ import java.io.OutputStream;
/*    5:     */ import java.io.StringWriter;
/*    6:     */ import java.io.UnsupportedEncodingException;
/*    7:     */ import java.io.Writer;
/*    8:     */ import java.lang.reflect.Method;
/*    9:     */ import java.net.HttpURLConnection;
/*   10:     */ import java.net.URL;
/*   11:     */ import java.net.URLConnection;
/*   12:     */ import java.security.AccessController;
/*   13:     */ import java.security.PrivilegedAction;
/*   14:     */ import java.util.Properties;
/*   15:     */ import java.util.StringTokenizer;
/*   16:     */ import org.apache.xml.serializer.DOM3Serializer;
/*   17:     */ import org.apache.xml.serializer.Encodings;
/*   18:     */ import org.apache.xml.serializer.OutputPropertiesFactory;
/*   19:     */ import org.apache.xml.serializer.Serializer;
/*   20:     */ import org.apache.xml.serializer.SerializerFactory;
/*   21:     */ import org.apache.xml.serializer.utils.Messages;
/*   22:     */ import org.apache.xml.serializer.utils.SystemIDResolver;
/*   23:     */ import org.apache.xml.serializer.utils.Utils;
/*   24:     */ import org.w3c.dom.DOMConfiguration;
/*   25:     */ import org.w3c.dom.DOMErrorHandler;
/*   26:     */ import org.w3c.dom.DOMException;
/*   27:     */ import org.w3c.dom.DOMImplementation;
/*   28:     */ import org.w3c.dom.DOMStringList;
/*   29:     */ import org.w3c.dom.Document;
/*   30:     */ import org.w3c.dom.Node;
/*   31:     */ import org.w3c.dom.ls.LSException;
/*   32:     */ import org.w3c.dom.ls.LSOutput;
/*   33:     */ import org.w3c.dom.ls.LSSerializer;
/*   34:     */ import org.w3c.dom.ls.LSSerializerFilter;
/*   35:     */ 
/*   36:     */ public final class LSSerializerImpl
/*   37:     */   implements DOMConfiguration, LSSerializer
/*   38:     */ {
/*   39:     */   private static final String DEFAULT_END_OF_LINE;
/*   40:     */   
/*   41:     */   static
/*   42:     */   {
/*   43:  75 */     String lineSeparator = (String)AccessController.doPrivileged(new PrivilegedAction()
/*   44:     */     {
/*   45:     */       public Object run()
/*   46:     */       {
/*   47:     */         try
/*   48:     */         {
/*   49:  78 */           return System.getProperty("line.separator");
/*   50:     */         }
/*   51:     */         catch (SecurityException ex) {}
/*   52:  81 */         return null;
/*   53:     */       }
/*   54:  86 */     });
/*   55:  87 */     DEFAULT_END_OF_LINE = (lineSeparator != null) && ((lineSeparator.equals("\r\n")) || (lineSeparator.equals("\r"))) ? lineSeparator : "\n";
/*   56:     */   }
/*   57:     */   
/*   58:  92 */   private Serializer fXMLSerializer = null;
/*   59:  95 */   protected int fFeatures = 0;
/*   60:  98 */   private DOM3Serializer fDOMSerializer = null;
/*   61: 101 */   private LSSerializerFilter fSerializerFilter = null;
/*   62: 104 */   private Node fVisitedNode = null;
/*   63: 107 */   private String fEndOfLine = DEFAULT_END_OF_LINE;
/*   64: 110 */   private DOMErrorHandler fDOMErrorHandler = null;
/*   65: 113 */   private Properties fDOMConfigProperties = null;
/*   66:     */   private String fEncoding;
/*   67:     */   private static final int CANONICAL = 1;
/*   68:     */   private static final int CDATA = 2;
/*   69:     */   private static final int CHARNORMALIZE = 4;
/*   70:     */   private static final int COMMENTS = 8;
/*   71:     */   private static final int DTNORMALIZE = 16;
/*   72:     */   private static final int ELEM_CONTENT_WHITESPACE = 32;
/*   73:     */   private static final int ENTITIES = 64;
/*   74:     */   private static final int INFOSET = 128;
/*   75:     */   private static final int NAMESPACES = 256;
/*   76:     */   private static final int NAMESPACEDECLS = 512;
/*   77:     */   private static final int NORMALIZECHARS = 1024;
/*   78:     */   private static final int SPLITCDATA = 2048;
/*   79:     */   private static final int VALIDATE = 4096;
/*   80:     */   private static final int SCHEMAVALIDATE = 8192;
/*   81:     */   private static final int WELLFORMED = 16384;
/*   82:     */   private static final int DISCARDDEFAULT = 32768;
/*   83:     */   private static final int PRETTY_PRINT = 65536;
/*   84:     */   private static final int IGNORE_CHAR_DENORMALIZE = 131072;
/*   85:     */   private static final int XMLDECL = 262144;
/*   86: 182 */   private String[] fRecognizedParameters = { "canonical-form", "cdata-sections", "check-character-normalization", "comments", "datatype-normalization", "element-content-whitespace", "entities", "infoset", "namespaces", "namespace-declarations", "split-cdata-sections", "validate", "validate-if-schema", "well-formed", "discard-default-content", "format-pretty-print", "ignore-unknown-character-denormalizations", "xml-declaration", "error-handler" };
/*   87:     */   
/*   88:     */   public LSSerializerImpl()
/*   89:     */   {
/*   90: 215 */     this.fFeatures |= 0x2;
/*   91: 216 */     this.fFeatures |= 0x8;
/*   92: 217 */     this.fFeatures |= 0x20;
/*   93: 218 */     this.fFeatures |= 0x40;
/*   94: 219 */     this.fFeatures |= 0x100;
/*   95: 220 */     this.fFeatures |= 0x200;
/*   96: 221 */     this.fFeatures |= 0x800;
/*   97: 222 */     this.fFeatures |= 0x4000;
/*   98: 223 */     this.fFeatures |= 0x8000;
/*   99: 224 */     this.fFeatures |= 0x40000;
/*  100:     */     
/*  101:     */ 
/*  102: 227 */     this.fDOMConfigProperties = new Properties();
/*  103:     */     
/*  104:     */ 
/*  105: 230 */     initializeSerializerProps();
/*  106:     */     
/*  107:     */ 
/*  108: 233 */     Properties configProps = OutputPropertiesFactory.getDefaultMethodProperties("xml");
/*  109:     */     
/*  110:     */ 
/*  111:     */ 
/*  112:     */ 
/*  113:     */ 
/*  114:     */ 
/*  115: 240 */     this.fXMLSerializer = SerializerFactory.getSerializer(configProps);
/*  116:     */     
/*  117:     */ 
/*  118: 243 */     this.fXMLSerializer.setOutputFormat(this.fDOMConfigProperties);
/*  119:     */   }
/*  120:     */   
/*  121:     */   public void initializeSerializerProps()
/*  122:     */   {
/*  123: 255 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}canonical-form", "default:no");
/*  124:     */     
/*  125:     */ 
/*  126:     */ 
/*  127: 259 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "default:yes");
/*  128:     */     
/*  129:     */ 
/*  130:     */ 
/*  131: 263 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}check-character-normalization", "default:no");
/*  132:     */     
/*  133:     */ 
/*  134:     */ 
/*  135:     */ 
/*  136: 268 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "default:yes");
/*  137:     */     
/*  138:     */ 
/*  139:     */ 
/*  140: 272 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}datatype-normalization", "default:no");
/*  141:     */     
/*  142:     */ 
/*  143:     */ 
/*  144:     */ 
/*  145: 277 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "default:yes");
/*  146:     */     
/*  147:     */ 
/*  148:     */ 
/*  149:     */ 
/*  150: 282 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "default:yes");
/*  151:     */     
/*  152:     */ 
/*  153: 285 */     this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "default:yes");
/*  154: 297 */     if ((this.fFeatures & 0x80) != 0)
/*  155:     */     {
/*  156: 298 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "default:yes");
/*  157:     */       
/*  158: 300 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "default:yes");
/*  159:     */       
/*  160:     */ 
/*  161: 303 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "default:yes");
/*  162:     */       
/*  163: 305 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "default:yes");
/*  164:     */       
/*  165:     */ 
/*  166: 308 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "default:yes");
/*  167:     */       
/*  168: 310 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "default:no");
/*  169:     */       
/*  170:     */ 
/*  171: 313 */       this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "default:no");
/*  172:     */       
/*  173: 315 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "default:no");
/*  174:     */       
/*  175:     */ 
/*  176: 318 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate-if-schema", "default:no");
/*  177:     */       
/*  178:     */ 
/*  179: 321 */       this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}datatype-normalization", "default:no");
/*  180:     */     }
/*  181: 327 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "default:yes");
/*  182:     */     
/*  183:     */ 
/*  184:     */ 
/*  185: 331 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "default:yes");
/*  186:     */     
/*  187:     */ 
/*  188:     */ 
/*  189:     */ 
/*  190:     */ 
/*  191:     */ 
/*  192:     */ 
/*  193:     */ 
/*  194:     */ 
/*  195:     */ 
/*  196:     */ 
/*  197: 343 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}split-cdata-sections", "default:yes");
/*  198:     */     
/*  199:     */ 
/*  200:     */ 
/*  201: 347 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate", "default:no");
/*  202:     */     
/*  203:     */ 
/*  204:     */ 
/*  205: 351 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate-if-schema", "default:no");
/*  206:     */     
/*  207:     */ 
/*  208:     */ 
/*  209:     */ 
/*  210: 356 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "default:yes");
/*  211:     */     
/*  212:     */ 
/*  213:     */ 
/*  214: 360 */     this.fDOMConfigProperties.setProperty("indent", "default:yes");
/*  215:     */     
/*  216:     */ 
/*  217: 363 */     this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xalan}indent-amount", Integer.toString(3));
/*  218:     */     
/*  219:     */ 
/*  220:     */ 
/*  221:     */ 
/*  222:     */ 
/*  223: 369 */     this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}discard-default-content", "default:yes");
/*  224:     */     
/*  225:     */ 
/*  226:     */ 
/*  227:     */ 
/*  228: 374 */     this.fDOMConfigProperties.setProperty("omit-xml-declaration", "no");
/*  229:     */   }
/*  230:     */   
/*  231:     */   public boolean canSetParameter(String name, Object value)
/*  232:     */   {
/*  233: 391 */     if ((value instanceof Boolean))
/*  234:     */     {
/*  235: 392 */       if ((name.equalsIgnoreCase("cdata-sections")) || (name.equalsIgnoreCase("comments")) || (name.equalsIgnoreCase("entities")) || (name.equalsIgnoreCase("infoset")) || (name.equalsIgnoreCase("element-content-whitespace")) || (name.equalsIgnoreCase("namespaces")) || (name.equalsIgnoreCase("namespace-declarations")) || (name.equalsIgnoreCase("split-cdata-sections")) || (name.equalsIgnoreCase("well-formed")) || (name.equalsIgnoreCase("discard-default-content")) || (name.equalsIgnoreCase("format-pretty-print")) || (name.equalsIgnoreCase("xml-declaration"))) {
/*  236: 405 */         return true;
/*  237:     */       }
/*  238: 407 */       if ((name.equalsIgnoreCase("canonical-form")) || (name.equalsIgnoreCase("check-character-normalization")) || (name.equalsIgnoreCase("datatype-normalization")) || (name.equalsIgnoreCase("validate-if-schema")) || (name.equalsIgnoreCase("validate"))) {
/*  239: 415 */         return !((Boolean)value).booleanValue();
/*  240:     */       }
/*  241: 417 */       if (name.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
/*  242: 419 */         return ((Boolean)value).booleanValue();
/*  243:     */       }
/*  244:     */     }
/*  245: 422 */     else if (((name.equalsIgnoreCase("error-handler")) && (value == null)) || ((value instanceof DOMErrorHandler)))
/*  246:     */     {
/*  247: 424 */       return true;
/*  248:     */     }
/*  249: 426 */     return false;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public Object getParameter(String name)
/*  253:     */     throws DOMException
/*  254:     */   {
/*  255: 438 */     if (name.equalsIgnoreCase("comments")) {
/*  256: 439 */       return (this.fFeatures & 0x8) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  257:     */     }
/*  258: 440 */     if (name.equalsIgnoreCase("cdata-sections")) {
/*  259: 441 */       return (this.fFeatures & 0x2) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  260:     */     }
/*  261: 442 */     if (name.equalsIgnoreCase("entities")) {
/*  262: 443 */       return (this.fFeatures & 0x40) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  263:     */     }
/*  264: 444 */     if (name.equalsIgnoreCase("namespaces")) {
/*  265: 445 */       return (this.fFeatures & 0x100) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  266:     */     }
/*  267: 446 */     if (name.equalsIgnoreCase("namespace-declarations")) {
/*  268: 447 */       return (this.fFeatures & 0x200) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  269:     */     }
/*  270: 448 */     if (name.equalsIgnoreCase("split-cdata-sections")) {
/*  271: 449 */       return (this.fFeatures & 0x800) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  272:     */     }
/*  273: 450 */     if (name.equalsIgnoreCase("well-formed")) {
/*  274: 451 */       return (this.fFeatures & 0x4000) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  275:     */     }
/*  276: 452 */     if (name.equalsIgnoreCase("discard-default-content")) {
/*  277: 453 */       return (this.fFeatures & 0x8000) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  278:     */     }
/*  279: 454 */     if (name.equalsIgnoreCase("format-pretty-print")) {
/*  280: 455 */       return (this.fFeatures & 0x10000) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  281:     */     }
/*  282: 456 */     if (name.equalsIgnoreCase("xml-declaration")) {
/*  283: 457 */       return (this.fFeatures & 0x40000) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  284:     */     }
/*  285: 458 */     if (name.equalsIgnoreCase("element-content-whitespace")) {
/*  286: 459 */       return (this.fFeatures & 0x20) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  287:     */     }
/*  288: 460 */     if (name.equalsIgnoreCase("format-pretty-print")) {
/*  289: 461 */       return (this.fFeatures & 0x10000) != 0 ? Boolean.TRUE : Boolean.FALSE;
/*  290:     */     }
/*  291: 462 */     if (name.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
/*  292: 463 */       return Boolean.TRUE;
/*  293:     */     }
/*  294: 464 */     if ((name.equalsIgnoreCase("canonical-form")) || (name.equalsIgnoreCase("check-character-normalization")) || (name.equalsIgnoreCase("datatype-normalization")) || (name.equalsIgnoreCase("validate")) || (name.equalsIgnoreCase("validate-if-schema"))) {
/*  295: 470 */       return Boolean.FALSE;
/*  296:     */     }
/*  297: 471 */     if (name.equalsIgnoreCase("infoset"))
/*  298:     */     {
/*  299: 472 */       if (((this.fFeatures & 0x40) == 0) && ((this.fFeatures & 0x2) == 0) && ((this.fFeatures & 0x20) != 0) && ((this.fFeatures & 0x100) != 0) && ((this.fFeatures & 0x200) != 0) && ((this.fFeatures & 0x4000) != 0) && ((this.fFeatures & 0x8) != 0)) {
/*  300: 479 */         return Boolean.TRUE;
/*  301:     */       }
/*  302: 481 */       return Boolean.FALSE;
/*  303:     */     }
/*  304: 482 */     if (name.equalsIgnoreCase("error-handler")) {
/*  305: 483 */       return this.fDOMErrorHandler;
/*  306:     */     }
/*  307: 484 */     if ((name.equalsIgnoreCase("schema-location")) || (name.equalsIgnoreCase("schema-type"))) {
/*  308: 487 */       return null;
/*  309:     */     }
/*  310: 490 */     String msg = Utils.messages.createMessage("FEATURE_NOT_FOUND", new Object[] { name });
/*  311:     */     
/*  312:     */ 
/*  313: 493 */     throw new DOMException((short)8, msg);
/*  314:     */   }
/*  315:     */   
/*  316:     */   public DOMStringList getParameterNames()
/*  317:     */   {
/*  318: 507 */     return new DOMStringListImpl(this.fRecognizedParameters);
/*  319:     */   }
/*  320:     */   
/*  321:     */   public void setParameter(String name, Object value)
/*  322:     */     throws DOMException
/*  323:     */   {
/*  324: 520 */     if ((value instanceof Boolean))
/*  325:     */     {
/*  326: 521 */       boolean state = ((Boolean)value).booleanValue();
/*  327: 523 */       if (name.equalsIgnoreCase("comments"))
/*  328:     */       {
/*  329: 524 */         this.fFeatures = (state ? this.fFeatures | 0x8 : this.fFeatures & 0xFFFFFFF7);
/*  330: 527 */         if (state) {
/*  331: 528 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "explicit:yes");
/*  332:     */         } else {
/*  333: 531 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "explicit:no");
/*  334:     */         }
/*  335:     */       }
/*  336: 534 */       else if (name.equalsIgnoreCase("cdata-sections"))
/*  337:     */       {
/*  338: 535 */         this.fFeatures = (state ? this.fFeatures | 0x2 : this.fFeatures & 0xFFFFFFFD);
/*  339: 538 */         if (state) {
/*  340: 539 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "explicit:yes");
/*  341:     */         } else {
/*  342: 542 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "explicit:no");
/*  343:     */         }
/*  344:     */       }
/*  345: 545 */       else if (name.equalsIgnoreCase("entities"))
/*  346:     */       {
/*  347: 546 */         this.fFeatures = (state ? this.fFeatures | 0x40 : this.fFeatures & 0xFFFFFFBF);
/*  348: 549 */         if (state)
/*  349:     */         {
/*  350: 550 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "explicit:yes");
/*  351:     */           
/*  352: 552 */           this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "explicit:yes");
/*  353:     */         }
/*  354:     */         else
/*  355:     */         {
/*  356: 556 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "explicit:no");
/*  357:     */           
/*  358: 558 */           this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "explicit:no");
/*  359:     */         }
/*  360:     */       }
/*  361: 562 */       else if (name.equalsIgnoreCase("namespaces"))
/*  362:     */       {
/*  363: 563 */         this.fFeatures = (state ? this.fFeatures | 0x100 : this.fFeatures & 0xFFFFFEFF);
/*  364: 566 */         if (state) {
/*  365: 567 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "explicit:yes");
/*  366:     */         } else {
/*  367: 570 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "explicit:no");
/*  368:     */         }
/*  369:     */       }
/*  370: 573 */       else if (name.equalsIgnoreCase("namespace-declarations"))
/*  371:     */       {
/*  372: 575 */         this.fFeatures = (state ? this.fFeatures | 0x200 : this.fFeatures & 0xFFFFFDFF);
/*  373: 578 */         if (state) {
/*  374: 579 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "explicit:yes");
/*  375:     */         } else {
/*  376: 582 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "explicit:no");
/*  377:     */         }
/*  378:     */       }
/*  379: 585 */       else if (name.equalsIgnoreCase("split-cdata-sections"))
/*  380:     */       {
/*  381: 586 */         this.fFeatures = (state ? this.fFeatures | 0x800 : this.fFeatures & 0xFFFFF7FF);
/*  382: 589 */         if (state) {
/*  383: 590 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}split-cdata-sections", "explicit:yes");
/*  384:     */         } else {
/*  385: 593 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}split-cdata-sections", "explicit:no");
/*  386:     */         }
/*  387:     */       }
/*  388: 596 */       else if (name.equalsIgnoreCase("well-formed"))
/*  389:     */       {
/*  390: 597 */         this.fFeatures = (state ? this.fFeatures | 0x4000 : this.fFeatures & 0xFFFFBFFF);
/*  391: 600 */         if (state) {
/*  392: 601 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "explicit:yes");
/*  393:     */         } else {
/*  394: 604 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "explicit:no");
/*  395:     */         }
/*  396:     */       }
/*  397: 607 */       else if (name.equalsIgnoreCase("discard-default-content"))
/*  398:     */       {
/*  399: 609 */         this.fFeatures = (state ? this.fFeatures | 0x8000 : this.fFeatures & 0xFFFF7FFF);
/*  400: 612 */         if (state) {
/*  401: 613 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}discard-default-content", "explicit:yes");
/*  402:     */         } else {
/*  403: 616 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}discard-default-content", "explicit:no");
/*  404:     */         }
/*  405:     */       }
/*  406: 619 */       else if (name.equalsIgnoreCase("format-pretty-print"))
/*  407:     */       {
/*  408: 620 */         this.fFeatures = (state ? this.fFeatures | 0x10000 : this.fFeatures & 0xFFFEFFFF);
/*  409: 623 */         if (state) {
/*  410: 624 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}format-pretty-print", "explicit:yes");
/*  411:     */         } else {
/*  412: 628 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}format-pretty-print", "explicit:no");
/*  413:     */         }
/*  414:     */       }
/*  415: 631 */       else if (name.equalsIgnoreCase("xml-declaration"))
/*  416:     */       {
/*  417: 632 */         this.fFeatures = (state ? this.fFeatures | 0x40000 : this.fFeatures & 0xFFFBFFFF);
/*  418: 634 */         if (state) {
/*  419: 635 */           this.fDOMConfigProperties.setProperty("omit-xml-declaration", "no");
/*  420:     */         } else {
/*  421: 637 */           this.fDOMConfigProperties.setProperty("omit-xml-declaration", "yes");
/*  422:     */         }
/*  423:     */       }
/*  424: 639 */       else if (name.equalsIgnoreCase("element-content-whitespace"))
/*  425:     */       {
/*  426: 640 */         this.fFeatures = (state ? this.fFeatures | 0x20 : this.fFeatures & 0xFFFFFFDF);
/*  427: 643 */         if (state) {
/*  428: 644 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "explicit:yes");
/*  429:     */         } else {
/*  430: 647 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "explicit:no");
/*  431:     */         }
/*  432:     */       }
/*  433: 650 */       else if (name.equalsIgnoreCase("ignore-unknown-character-denormalizations"))
/*  434:     */       {
/*  435: 652 */         if (!state)
/*  436:     */         {
/*  437: 654 */           String msg = Utils.messages.createMessage("FEATURE_NOT_SUPPORTED", new Object[] { name });
/*  438:     */           
/*  439:     */ 
/*  440: 657 */           throw new DOMException((short)9, msg);
/*  441:     */         }
/*  442: 659 */         this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}ignore-unknown-character-denormalizations", "explicit:yes");
/*  443:     */       }
/*  444: 662 */       else if ((name.equalsIgnoreCase("canonical-form")) || (name.equalsIgnoreCase("validate-if-schema")) || (name.equalsIgnoreCase("validate")) || (name.equalsIgnoreCase("check-character-normalization")) || (name.equalsIgnoreCase("datatype-normalization")))
/*  445:     */       {
/*  446: 670 */         if (state)
/*  447:     */         {
/*  448: 671 */           String msg = Utils.messages.createMessage("FEATURE_NOT_SUPPORTED", new Object[] { name });
/*  449:     */           
/*  450:     */ 
/*  451: 674 */           throw new DOMException((short)9, msg);
/*  452:     */         }
/*  453: 676 */         if (name.equalsIgnoreCase("canonical-form")) {
/*  454: 677 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}canonical-form", "explicit:no");
/*  455: 679 */         } else if (name.equalsIgnoreCase("validate-if-schema")) {
/*  456: 680 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate-if-schema", "explicit:no");
/*  457: 682 */         } else if (name.equalsIgnoreCase("validate")) {
/*  458: 683 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate", "explicit:no");
/*  459: 685 */         } else if (name.equalsIgnoreCase("validate-if-schema")) {
/*  460: 686 */           this.fDOMConfigProperties.setProperty("check-character-normalizationcheck-character-normalization", "explicit:no");
/*  461: 688 */         } else if (name.equalsIgnoreCase("datatype-normalization")) {
/*  462: 689 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}datatype-normalization", "explicit:no");
/*  463:     */         }
/*  464:     */       }
/*  465: 696 */       else if (name.equalsIgnoreCase("infoset"))
/*  466:     */       {
/*  467: 698 */         if (state)
/*  468:     */         {
/*  469: 699 */           this.fFeatures &= 0xFFFFFFBF;
/*  470: 700 */           this.fFeatures &= 0xFFFFFFFD;
/*  471: 701 */           this.fFeatures &= 0xFFFFDFFF;
/*  472: 702 */           this.fFeatures &= 0xFFFFFFEF;
/*  473: 703 */           this.fFeatures |= 0x100;
/*  474: 704 */           this.fFeatures |= 0x200;
/*  475: 705 */           this.fFeatures |= 0x4000;
/*  476: 706 */           this.fFeatures |= 0x20;
/*  477: 707 */           this.fFeatures |= 0x8;
/*  478:     */           
/*  479: 709 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "explicit:yes");
/*  480:     */           
/*  481: 711 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "explicit:yes");
/*  482:     */           
/*  483: 713 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "explicit:yes");
/*  484:     */           
/*  485: 715 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "explicit:yes");
/*  486:     */           
/*  487: 717 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "explicit:yes");
/*  488:     */           
/*  489:     */ 
/*  490: 720 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "explicit:no");
/*  491:     */           
/*  492: 722 */           this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "explicit:no");
/*  493:     */           
/*  494:     */ 
/*  495: 725 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "explicit:no");
/*  496:     */           
/*  497: 727 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate-if-schema", "explicit:no");
/*  498:     */           
/*  499: 729 */           this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}datatype-normalization", "explicit:no");
/*  500:     */         }
/*  501:     */       }
/*  502:     */       else
/*  503:     */       {
/*  504: 734 */         if ((name.equalsIgnoreCase("error-handler")) || (name.equalsIgnoreCase("schema-location")) || (name.equalsIgnoreCase("schema-type")))
/*  505:     */         {
/*  506: 737 */           String msg = Utils.messages.createMessage("TYPE_MISMATCH_ERR", new Object[] { name });
/*  507:     */           
/*  508:     */ 
/*  509: 740 */           throw new DOMException((short)17, msg);
/*  510:     */         }
/*  511: 744 */         String msg = Utils.messages.createMessage("FEATURE_NOT_FOUND", new Object[] { name });
/*  512:     */         
/*  513:     */ 
/*  514: 747 */         throw new DOMException((short)8, msg);
/*  515:     */       }
/*  516:     */     }
/*  517: 750 */     else if (name.equalsIgnoreCase("error-handler"))
/*  518:     */     {
/*  519: 751 */       if ((value == null) || ((value instanceof DOMErrorHandler)))
/*  520:     */       {
/*  521: 752 */         this.fDOMErrorHandler = ((DOMErrorHandler)value);
/*  522:     */       }
/*  523:     */       else
/*  524:     */       {
/*  525: 754 */         String msg = Utils.messages.createMessage("TYPE_MISMATCH_ERR", new Object[] { name });
/*  526:     */         
/*  527:     */ 
/*  528: 757 */         throw new DOMException((short)17, msg);
/*  529:     */       }
/*  530:     */     }
/*  531: 759 */     else if ((name.equalsIgnoreCase("schema-location")) || (name.equalsIgnoreCase("schema-type")))
/*  532:     */     {
/*  533: 762 */       if (value != null)
/*  534:     */       {
/*  535: 763 */         if (!(value instanceof String))
/*  536:     */         {
/*  537: 764 */           String msg = Utils.messages.createMessage("TYPE_MISMATCH_ERR", new Object[] { name });
/*  538:     */           
/*  539:     */ 
/*  540: 767 */           throw new DOMException((short)17, msg);
/*  541:     */         }
/*  542: 769 */         String msg = Utils.messages.createMessage("FEATURE_NOT_SUPPORTED", new Object[] { name });
/*  543:     */         
/*  544:     */ 
/*  545: 772 */         throw new DOMException((short)9, msg);
/*  546:     */       }
/*  547:     */     }
/*  548:     */     else
/*  549:     */     {
/*  550: 776 */       if ((name.equalsIgnoreCase("comments")) || (name.equalsIgnoreCase("cdata-sections")) || (name.equalsIgnoreCase("entities")) || (name.equalsIgnoreCase("namespaces")) || (name.equalsIgnoreCase("namespace-declarations")) || (name.equalsIgnoreCase("split-cdata-sections")) || (name.equalsIgnoreCase("well-formed")) || (name.equalsIgnoreCase("discard-default-content")) || (name.equalsIgnoreCase("format-pretty-print")) || (name.equalsIgnoreCase("xml-declaration")) || (name.equalsIgnoreCase("element-content-whitespace")) || (name.equalsIgnoreCase("ignore-unknown-character-denormalizations")) || (name.equalsIgnoreCase("canonical-form")) || (name.equalsIgnoreCase("validate-if-schema")) || (name.equalsIgnoreCase("validate")) || (name.equalsIgnoreCase("check-character-normalization")) || (name.equalsIgnoreCase("datatype-normalization")) || (name.equalsIgnoreCase("infoset")))
/*  551:     */       {
/*  552: 794 */         String msg = Utils.messages.createMessage("TYPE_MISMATCH_ERR", new Object[] { name });
/*  553:     */         
/*  554:     */ 
/*  555: 797 */         throw new DOMException((short)17, msg);
/*  556:     */       }
/*  557: 801 */       String msg = Utils.messages.createMessage("FEATURE_NOT_FOUND", new Object[] { name });
/*  558:     */       
/*  559:     */ 
/*  560: 804 */       throw new DOMException((short)8, msg);
/*  561:     */     }
/*  562:     */   }
/*  563:     */   
/*  564:     */   public DOMConfiguration getDomConfig()
/*  565:     */   {
/*  566: 822 */     return this;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public LSSerializerFilter getFilter()
/*  570:     */   {
/*  571: 833 */     return this.fSerializerFilter;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public String getNewLine()
/*  575:     */   {
/*  576: 846 */     return this.fEndOfLine;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void setFilter(LSSerializerFilter filter)
/*  580:     */   {
/*  581: 859 */     this.fSerializerFilter = filter;
/*  582:     */   }
/*  583:     */   
/*  584:     */   public void setNewLine(String newLine)
/*  585:     */   {
/*  586: 873 */     this.fEndOfLine = (newLine != null ? newLine : DEFAULT_END_OF_LINE);
/*  587:     */   }
/*  588:     */   
/*  589:     */   public boolean write(Node nodeArg, LSOutput destination)
/*  590:     */     throws LSException
/*  591:     */   {
/*  592: 889 */     if (destination == null)
/*  593:     */     {
/*  594: 890 */       String msg = Utils.messages.createMessage("no-output-specified", null);
/*  595: 894 */       if (this.fDOMErrorHandler != null) {
/*  596: 895 */         this.fDOMErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "no-output-specified"));
/*  597:     */       }
/*  598: 899 */       throw new LSException((short)82, msg);
/*  599:     */     }
/*  600: 903 */     if (nodeArg == null) {
/*  601: 904 */       return false;
/*  602:     */     }
/*  603: 909 */     Serializer serializer = this.fXMLSerializer;
/*  604: 910 */     serializer.reset();
/*  605: 913 */     if (nodeArg != this.fVisitedNode)
/*  606:     */     {
/*  607: 915 */       String xmlVersion = getXMLVersion(nodeArg);
/*  608:     */       
/*  609:     */ 
/*  610: 918 */       this.fEncoding = destination.getEncoding();
/*  611: 919 */       if (this.fEncoding == null)
/*  612:     */       {
/*  613: 920 */         this.fEncoding = getInputEncoding(nodeArg);
/*  614: 921 */         this.fEncoding = (getXMLEncoding(nodeArg) == null ? "UTF-8" : this.fEncoding != null ? this.fEncoding : getXMLEncoding(nodeArg));
/*  615:     */       }
/*  616: 926 */       if (!Encodings.isRecognizedEncoding(this.fEncoding))
/*  617:     */       {
/*  618: 927 */         String msg = Utils.messages.createMessage("unsupported-encoding", null);
/*  619: 931 */         if (this.fDOMErrorHandler != null) {
/*  620: 932 */           this.fDOMErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "unsupported-encoding"));
/*  621:     */         }
/*  622: 936 */         throw new LSException((short)82, msg);
/*  623:     */       }
/*  624: 939 */       serializer.getOutputFormat().setProperty("version", xmlVersion);
/*  625:     */       
/*  626:     */ 
/*  627: 942 */       this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}xml-version", xmlVersion);
/*  628: 943 */       this.fDOMConfigProperties.setProperty("encoding", this.fEncoding);
/*  629: 949 */       if (((nodeArg.getNodeType() != 9) || (nodeArg.getNodeType() != 1) || (nodeArg.getNodeType() != 6)) && ((this.fFeatures & 0x40000) != 0)) {
/*  630: 953 */         this.fDOMConfigProperties.setProperty("omit-xml-declaration", "default:no");
/*  631:     */       }
/*  632: 958 */       this.fVisitedNode = nodeArg;
/*  633:     */     }
/*  634: 962 */     this.fXMLSerializer.setOutputFormat(this.fDOMConfigProperties);
/*  635:     */     try
/*  636:     */     {
/*  637: 973 */       Writer writer = destination.getCharacterStream();
/*  638: 974 */       if (writer == null)
/*  639:     */       {
/*  640: 977 */         OutputStream outputStream = destination.getByteStream();
/*  641: 978 */         if (outputStream == null)
/*  642:     */         {
/*  643: 981 */           String uri = destination.getSystemId();
/*  644: 982 */           if (uri == null)
/*  645:     */           {
/*  646: 983 */             String msg = Utils.messages.createMessage("no-output-specified", null);
/*  647: 987 */             if (this.fDOMErrorHandler != null) {
/*  648: 988 */               this.fDOMErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "no-output-specified"));
/*  649:     */             }
/*  650: 992 */             throw new LSException((short)82, msg);
/*  651:     */           }
/*  652: 996 */           String absoluteURI = SystemIDResolver.getAbsoluteURI(uri);
/*  653:     */           
/*  654: 998 */           URL url = new URL(absoluteURI);
/*  655: 999 */           OutputStream urlOutStream = null;
/*  656:1000 */           String protocol = url.getProtocol();
/*  657:1001 */           String host = url.getHost();
/*  658:1011 */           if ((protocol.equalsIgnoreCase("file")) && ((host == null) || (host.length() == 0) || (host.equals("localhost"))))
/*  659:     */           {
/*  660:1014 */             urlOutStream = new FileOutputStream(getPathWithoutEscapes(url.getPath()));
/*  661:     */           }
/*  662:     */           else
/*  663:     */           {
/*  664:1020 */             URLConnection urlCon = url.openConnection();
/*  665:1021 */             urlCon.setDoInput(false);
/*  666:1022 */             urlCon.setDoOutput(true);
/*  667:1023 */             urlCon.setUseCaches(false);
/*  668:1024 */             urlCon.setAllowUserInteraction(false);
/*  669:1027 */             if ((urlCon instanceof HttpURLConnection))
/*  670:     */             {
/*  671:1028 */               HttpURLConnection httpCon = (HttpURLConnection)urlCon;
/*  672:1029 */               httpCon.setRequestMethod("PUT");
/*  673:     */             }
/*  674:1031 */             urlOutStream = urlCon.getOutputStream();
/*  675:     */           }
/*  676:1034 */           serializer.setOutputStream(urlOutStream);
/*  677:     */         }
/*  678:     */         else
/*  679:     */         {
/*  680:1038 */           serializer.setOutputStream(outputStream);
/*  681:     */         }
/*  682:     */       }
/*  683:     */       else
/*  684:     */       {
/*  685:1042 */         serializer.setWriter(writer);
/*  686:     */       }
/*  687:1050 */       if (this.fDOMSerializer == null) {
/*  688:1051 */         this.fDOMSerializer = ((DOM3Serializer)serializer.asDOM3Serializer());
/*  689:     */       }
/*  690:1055 */       if (this.fDOMErrorHandler != null) {
/*  691:1056 */         this.fDOMSerializer.setErrorHandler(this.fDOMErrorHandler);
/*  692:     */       }
/*  693:1060 */       if (this.fSerializerFilter != null) {
/*  694:1061 */         this.fDOMSerializer.setNodeFilter(this.fSerializerFilter);
/*  695:     */       }
/*  696:1065 */       this.fDOMSerializer.setNewLine(this.fEndOfLine.toCharArray());
/*  697:     */       
/*  698:     */ 
/*  699:     */ 
/*  700:1069 */       this.fDOMSerializer.serializeDOM3(nodeArg);
/*  701:     */     }
/*  702:     */     catch (UnsupportedEncodingException ue)
/*  703:     */     {
/*  704:1073 */       String msg = Utils.messages.createMessage("unsupported-encoding", null);
/*  705:1077 */       if (this.fDOMErrorHandler != null) {
/*  706:1078 */         this.fDOMErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "unsupported-encoding", ue));
/*  707:     */       }
/*  708:1082 */       throw ((LSException)createLSException((short)82, ue).fillInStackTrace());
/*  709:     */     }
/*  710:     */     catch (LSException lse)
/*  711:     */     {
/*  712:1085 */       throw lse;
/*  713:     */     }
/*  714:     */     catch (RuntimeException e)
/*  715:     */     {
/*  716:1087 */       throw ((LSException)createLSException((short)82, e).fillInStackTrace());
/*  717:     */     }
/*  718:     */     catch (Exception e)
/*  719:     */     {
/*  720:1089 */       if (this.fDOMErrorHandler != null) {
/*  721:1090 */         this.fDOMErrorHandler.handleError(new DOMErrorImpl((short)3, e.getMessage(), null, e));
/*  722:     */       }
/*  723:1094 */       throw ((LSException)createLSException((short)82, e).fillInStackTrace());
/*  724:     */     }
/*  725:1096 */     return true;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public String writeToString(Node nodeArg)
/*  729:     */     throws DOMException, LSException
/*  730:     */   {
/*  731:1112 */     if (nodeArg == null) {
/*  732:1113 */       return null;
/*  733:     */     }
/*  734:1118 */     Serializer serializer = this.fXMLSerializer;
/*  735:1119 */     serializer.reset();
/*  736:1121 */     if (nodeArg != this.fVisitedNode)
/*  737:     */     {
/*  738:1123 */       String xmlVersion = getXMLVersion(nodeArg);
/*  739:     */       
/*  740:1125 */       serializer.getOutputFormat().setProperty("version", xmlVersion);
/*  741:     */       
/*  742:     */ 
/*  743:1128 */       this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}xml-version", xmlVersion);
/*  744:1129 */       this.fDOMConfigProperties.setProperty("encoding", "UTF-16");
/*  745:1135 */       if (((nodeArg.getNodeType() != 9) || (nodeArg.getNodeType() != 1) || (nodeArg.getNodeType() != 6)) && ((this.fFeatures & 0x40000) != 0)) {
/*  746:1139 */         this.fDOMConfigProperties.setProperty("omit-xml-declaration", "default:no");
/*  747:     */       }
/*  748:1144 */       this.fVisitedNode = nodeArg;
/*  749:     */     }
/*  750:1147 */     this.fXMLSerializer.setOutputFormat(this.fDOMConfigProperties);
/*  751:     */     
/*  752:     */ 
/*  753:1150 */     StringWriter output = new StringWriter();
/*  754:     */     try
/*  755:     */     {
/*  756:1156 */       serializer.setWriter(output);
/*  757:1160 */       if (this.fDOMSerializer == null) {
/*  758:1161 */         this.fDOMSerializer = ((DOM3Serializer)serializer.asDOM3Serializer());
/*  759:     */       }
/*  760:1165 */       if (this.fDOMErrorHandler != null) {
/*  761:1166 */         this.fDOMSerializer.setErrorHandler(this.fDOMErrorHandler);
/*  762:     */       }
/*  763:1170 */       if (this.fSerializerFilter != null) {
/*  764:1171 */         this.fDOMSerializer.setNodeFilter(this.fSerializerFilter);
/*  765:     */       }
/*  766:1175 */       this.fDOMSerializer.setNewLine(this.fEndOfLine.toCharArray());
/*  767:     */       
/*  768:     */ 
/*  769:1178 */       this.fDOMSerializer.serializeDOM3(nodeArg);
/*  770:     */     }
/*  771:     */     catch (LSException lse)
/*  772:     */     {
/*  773:1181 */       throw lse;
/*  774:     */     }
/*  775:     */     catch (RuntimeException e)
/*  776:     */     {
/*  777:1183 */       throw ((LSException)createLSException((short)82, e).fillInStackTrace());
/*  778:     */     }
/*  779:     */     catch (Exception e)
/*  780:     */     {
/*  781:1185 */       if (this.fDOMErrorHandler != null) {
/*  782:1186 */         this.fDOMErrorHandler.handleError(new DOMErrorImpl((short)3, e.getMessage(), null, e));
/*  783:     */       }
/*  784:1190 */       throw ((LSException)createLSException((short)82, e).fillInStackTrace());
/*  785:     */     }
/*  786:1194 */     return output.toString();
/*  787:     */   }
/*  788:     */   
/*  789:     */   public boolean writeToURI(Node nodeArg, String uri)
/*  790:     */     throws LSException
/*  791:     */   {
/*  792:1210 */     if (nodeArg == null) {
/*  793:1211 */       return false;
/*  794:     */     }
/*  795:1215 */     Serializer serializer = this.fXMLSerializer;
/*  796:1216 */     serializer.reset();
/*  797:1218 */     if (nodeArg != this.fVisitedNode)
/*  798:     */     {
/*  799:1220 */       String xmlVersion = getXMLVersion(nodeArg);
/*  800:     */       
/*  801:     */ 
/*  802:     */ 
/*  803:1224 */       this.fEncoding = getInputEncoding(nodeArg);
/*  804:1225 */       if (this.fEncoding == null) {
/*  805:1226 */         this.fEncoding = (getXMLEncoding(nodeArg) == null ? "UTF-8" : this.fEncoding != null ? this.fEncoding : getXMLEncoding(nodeArg));
/*  806:     */       }
/*  807:1229 */       serializer.getOutputFormat().setProperty("version", xmlVersion);
/*  808:     */       
/*  809:     */ 
/*  810:1232 */       this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}xml-version", xmlVersion);
/*  811:1233 */       this.fDOMConfigProperties.setProperty("encoding", this.fEncoding);
/*  812:1239 */       if (((nodeArg.getNodeType() != 9) || (nodeArg.getNodeType() != 1) || (nodeArg.getNodeType() != 6)) && ((this.fFeatures & 0x40000) != 0)) {
/*  813:1243 */         this.fDOMConfigProperties.setProperty("omit-xml-declaration", "default:no");
/*  814:     */       }
/*  815:1248 */       this.fVisitedNode = nodeArg;
/*  816:     */     }
/*  817:1252 */     this.fXMLSerializer.setOutputFormat(this.fDOMConfigProperties);
/*  818:     */     try
/*  819:     */     {
/*  820:1258 */       if (uri == null)
/*  821:     */       {
/*  822:1259 */         String msg = Utils.messages.createMessage("no-output-specified", null);
/*  823:1261 */         if (this.fDOMErrorHandler != null) {
/*  824:1262 */           this.fDOMErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "no-output-specified"));
/*  825:     */         }
/*  826:1266 */         throw new LSException((short)82, msg);
/*  827:     */       }
/*  828:1270 */       String absoluteURI = SystemIDResolver.getAbsoluteURI(uri);
/*  829:     */       
/*  830:1272 */       URL url = new URL(absoluteURI);
/*  831:1273 */       OutputStream urlOutStream = null;
/*  832:1274 */       String protocol = url.getProtocol();
/*  833:1275 */       String host = url.getHost();
/*  834:1286 */       if ((protocol.equalsIgnoreCase("file")) && ((host == null) || (host.length() == 0) || (host.equals("localhost"))))
/*  835:     */       {
/*  836:1290 */         urlOutStream = new FileOutputStream(getPathWithoutEscapes(url.getPath()));
/*  837:     */       }
/*  838:     */       else
/*  839:     */       {
/*  840:1296 */         URLConnection urlCon = url.openConnection();
/*  841:1297 */         urlCon.setDoInput(false);
/*  842:1298 */         urlCon.setDoOutput(true);
/*  843:1299 */         urlCon.setUseCaches(false);
/*  844:1300 */         urlCon.setAllowUserInteraction(false);
/*  845:1303 */         if ((urlCon instanceof HttpURLConnection))
/*  846:     */         {
/*  847:1304 */           HttpURLConnection httpCon = (HttpURLConnection)urlCon;
/*  848:1305 */           httpCon.setRequestMethod("PUT");
/*  849:     */         }
/*  850:1307 */         urlOutStream = urlCon.getOutputStream();
/*  851:     */       }
/*  852:1310 */       serializer.setOutputStream(urlOutStream);
/*  853:1315 */       if (this.fDOMSerializer == null) {
/*  854:1316 */         this.fDOMSerializer = ((DOM3Serializer)serializer.asDOM3Serializer());
/*  855:     */       }
/*  856:1320 */       if (this.fDOMErrorHandler != null) {
/*  857:1321 */         this.fDOMSerializer.setErrorHandler(this.fDOMErrorHandler);
/*  858:     */       }
/*  859:1325 */       if (this.fSerializerFilter != null) {
/*  860:1326 */         this.fDOMSerializer.setNodeFilter(this.fSerializerFilter);
/*  861:     */       }
/*  862:1330 */       this.fDOMSerializer.setNewLine(this.fEndOfLine.toCharArray());
/*  863:     */       
/*  864:     */ 
/*  865:     */ 
/*  866:     */ 
/*  867:1335 */       this.fDOMSerializer.serializeDOM3(nodeArg);
/*  868:     */     }
/*  869:     */     catch (LSException lse)
/*  870:     */     {
/*  871:1339 */       throw lse;
/*  872:     */     }
/*  873:     */     catch (RuntimeException e)
/*  874:     */     {
/*  875:1341 */       throw ((LSException)createLSException((short)82, e).fillInStackTrace());
/*  876:     */     }
/*  877:     */     catch (Exception e)
/*  878:     */     {
/*  879:1343 */       if (this.fDOMErrorHandler != null) {
/*  880:1344 */         this.fDOMErrorHandler.handleError(new DOMErrorImpl((short)3, e.getMessage(), null, e));
/*  881:     */       }
/*  882:1348 */       throw ((LSException)createLSException((short)82, e).fillInStackTrace());
/*  883:     */     }
/*  884:1351 */     return true;
/*  885:     */   }
/*  886:     */   
/*  887:     */   protected String getXMLVersion(Node nodeArg)
/*  888:     */   {
/*  889:1370 */     Document doc = null;
/*  890:1373 */     if (nodeArg != null)
/*  891:     */     {
/*  892:1374 */       if (nodeArg.getNodeType() == 9) {
/*  893:1376 */         doc = (Document)nodeArg;
/*  894:     */       } else {
/*  895:1379 */         doc = nodeArg.getOwnerDocument();
/*  896:     */       }
/*  897:1383 */       if ((doc != null) && (doc.getImplementation().hasFeature("Core", "3.0"))) {
/*  898:1384 */         return doc.getXmlVersion();
/*  899:     */       }
/*  900:     */     }
/*  901:1390 */     return "1.0";
/*  902:     */   }
/*  903:     */   
/*  904:     */   protected String getXMLEncoding(Node nodeArg)
/*  905:     */   {
/*  906:1402 */     Document doc = null;
/*  907:1405 */     if (nodeArg != null)
/*  908:     */     {
/*  909:1406 */       if (nodeArg.getNodeType() == 9) {
/*  910:1408 */         doc = (Document)nodeArg;
/*  911:     */       } else {
/*  912:1411 */         doc = nodeArg.getOwnerDocument();
/*  913:     */       }
/*  914:1415 */       if ((doc != null) && (doc.getImplementation().hasFeature("Core", "3.0"))) {
/*  915:1416 */         return doc.getXmlEncoding();
/*  916:     */       }
/*  917:     */     }
/*  918:1420 */     return "UTF-8";
/*  919:     */   }
/*  920:     */   
/*  921:     */   protected String getInputEncoding(Node nodeArg)
/*  922:     */   {
/*  923:1431 */     Document doc = null;
/*  924:1434 */     if (nodeArg != null)
/*  925:     */     {
/*  926:1435 */       if (nodeArg.getNodeType() == 9) {
/*  927:1437 */         doc = (Document)nodeArg;
/*  928:     */       } else {
/*  929:1440 */         doc = nodeArg.getOwnerDocument();
/*  930:     */       }
/*  931:1444 */       if ((doc != null) && (doc.getImplementation().hasFeature("Core", "3.0"))) {
/*  932:1445 */         return doc.getInputEncoding();
/*  933:     */       }
/*  934:     */     }
/*  935:1449 */     return null;
/*  936:     */   }
/*  937:     */   
/*  938:     */   public DOMErrorHandler getErrorHandler()
/*  939:     */   {
/*  940:1458 */     return this.fDOMErrorHandler;
/*  941:     */   }
/*  942:     */   
/*  943:     */   private static String getPathWithoutEscapes(String origPath)
/*  944:     */   {
/*  945:1465 */     if ((origPath != null) && (origPath.length() != 0) && (origPath.indexOf('%') != -1))
/*  946:     */     {
/*  947:1467 */       StringTokenizer tokenizer = new StringTokenizer(origPath, "%");
/*  948:1468 */       StringBuffer result = new StringBuffer(origPath.length());
/*  949:1469 */       int size = tokenizer.countTokens();
/*  950:1470 */       result.append(tokenizer.nextToken());
/*  951:1471 */       for (int i = 1; i < size; i++)
/*  952:     */       {
/*  953:1472 */         String token = tokenizer.nextToken();
/*  954:1473 */         if ((token.length() >= 2) && (isHexDigit(token.charAt(0))) && (isHexDigit(token.charAt(1))))
/*  955:     */         {
/*  956:1476 */           result.append((char)Integer.valueOf(token.substring(0, 2), 16).intValue());
/*  957:1477 */           token = token.substring(2);
/*  958:     */         }
/*  959:1479 */         result.append(token);
/*  960:     */       }
/*  961:1481 */       return result.toString();
/*  962:     */     }
/*  963:1483 */     return origPath;
/*  964:     */   }
/*  965:     */   
/*  966:     */   private static boolean isHexDigit(char c)
/*  967:     */   {
/*  968:1490 */     return ((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F'));
/*  969:     */   }
/*  970:     */   
/*  971:     */   private static LSException createLSException(short code, Throwable cause)
/*  972:     */   {
/*  973:1499 */     LSException lse = new LSException(code, cause != null ? cause.getMessage() : null);
/*  974:1500 */     if ((cause != null) && (ThrowableMethods.fgThrowableMethodsAvailable)) {
/*  975:     */       try
/*  976:     */       {
/*  977:1502 */         ThrowableMethods.fgThrowableInitCauseMethod.invoke(lse, new Object[] { cause });
/*  978:     */       }
/*  979:     */       catch (Exception e) {}
/*  980:     */     }
/*  981:1507 */     return lse;
/*  982:     */   }
/*  983:     */   
/*  984:     */   static class ThrowableMethods
/*  985:     */   {
/*  986:1516 */     private static Method fgThrowableInitCauseMethod = null;
/*  987:1519 */     private static boolean fgThrowableMethodsAvailable = false;
/*  988:     */     
/*  989:     */     static
/*  990:     */     {
/*  991:     */       try
/*  992:     */       {
/*  993:1526 */         fgThrowableInitCauseMethod = class$java$lang$Throwable.getMethod("initCause", new Class[] { Throwable.class });
/*  994:1527 */         fgThrowableMethodsAvailable = true;
/*  995:     */       }
/*  996:     */       catch (Exception exc)
/*  997:     */       {
/*  998:1532 */         fgThrowableInitCauseMethod = null;
/*  999:1533 */         fgThrowableMethodsAvailable = false;
/* 1000:     */       }
/* 1001:     */     }
/* 1002:     */   }
/* 1003:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.LSSerializerImpl
 * JD-Core Version:    0.7.0.1
 */