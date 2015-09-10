/*    1:     */ package org.cyberneko.html;
/*    2:     */ 
/*    3:     */ import java.io.EOFException;
/*    4:     */ import java.io.File;
/*    5:     */ import java.io.FilterInputStream;
/*    6:     */ import java.io.IOException;
/*    7:     */ import java.io.InputStream;
/*    8:     */ import java.io.InputStreamReader;
/*    9:     */ import java.io.Reader;
/*   10:     */ import java.io.UnsupportedEncodingException;
/*   11:     */ import java.net.URL;
/*   12:     */ import java.util.BitSet;
/*   13:     */ import java.util.Stack;
/*   14:     */ import org.apache.xerces.util.EncodingMap;
/*   15:     */ import org.apache.xerces.util.NamespaceSupport;
/*   16:     */ import org.apache.xerces.util.URI;
/*   17:     */ import org.apache.xerces.util.URI.MalformedURIException;
/*   18:     */ import org.apache.xerces.util.XMLAttributesImpl;
/*   19:     */ import org.apache.xerces.util.XMLResourceIdentifierImpl;
/*   20:     */ import org.apache.xerces.util.XMLStringBuffer;
/*   21:     */ import org.apache.xerces.xni.Augmentations;
/*   22:     */ import org.apache.xerces.xni.NamespaceContext;
/*   23:     */ import org.apache.xerces.xni.QName;
/*   24:     */ import org.apache.xerces.xni.XMLAttributes;
/*   25:     */ import org.apache.xerces.xni.XMLDocumentHandler;
/*   26:     */ import org.apache.xerces.xni.XMLLocator;
/*   27:     */ import org.apache.xerces.xni.XMLResourceIdentifier;
/*   28:     */ import org.apache.xerces.xni.XMLString;
/*   29:     */ import org.apache.xerces.xni.XNIException;
/*   30:     */ import org.apache.xerces.xni.parser.XMLComponentManager;
/*   31:     */ import org.apache.xerces.xni.parser.XMLConfigurationException;
/*   32:     */ import org.apache.xerces.xni.parser.XMLDocumentScanner;
/*   33:     */ import org.apache.xerces.xni.parser.XMLInputSource;
/*   34:     */ import org.cyberneko.html.xercesbridge.XercesBridge;
/*   35:     */ 
/*   36:     */ public class HTMLScanner
/*   37:     */   implements XMLDocumentScanner, XMLLocator, HTMLComponent
/*   38:     */ {
/*   39:     */   public static final String HTML_4_01_STRICT_PUBID = "-//W3C//DTD HTML 4.01//EN";
/*   40:     */   public static final String HTML_4_01_STRICT_SYSID = "http://www.w3.org/TR/html4/strict.dtd";
/*   41:     */   public static final String HTML_4_01_TRANSITIONAL_PUBID = "-//W3C//DTD HTML 4.01 Transitional//EN";
/*   42:     */   public static final String HTML_4_01_TRANSITIONAL_SYSID = "http://www.w3.org/TR/html4/loose.dtd";
/*   43:     */   public static final String HTML_4_01_FRAMESET_PUBID = "-//W3C//DTD HTML 4.01 Frameset//EN";
/*   44:     */   public static final String HTML_4_01_FRAMESET_SYSID = "http://www.w3.org/TR/html4/frameset.dtd";
/*   45:     */   protected static final String AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";
/*   46:     */   protected static final String REPORT_ERRORS = "http://cyberneko.org/html/features/report-errors";
/*   47:     */   public static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
/*   48:     */   public static final String NOTIFY_XML_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
/*   49:     */   public static final String NOTIFY_HTML_BUILTIN_REFS = "http://cyberneko.org/html/features/scanner/notify-builtin-refs";
/*   50:     */   public static final String FIX_MSWINDOWS_REFS = "http://cyberneko.org/html/features/scanner/fix-mswindows-refs";
/*   51:     */   public static final String SCRIPT_STRIP_COMMENT_DELIMS = "http://cyberneko.org/html/features/scanner/script/strip-comment-delims";
/*   52:     */   public static final String SCRIPT_STRIP_CDATA_DELIMS = "http://cyberneko.org/html/features/scanner/script/strip-cdata-delims";
/*   53:     */   public static final String STYLE_STRIP_COMMENT_DELIMS = "http://cyberneko.org/html/features/scanner/style/strip-comment-delims";
/*   54:     */   public static final String STYLE_STRIP_CDATA_DELIMS = "http://cyberneko.org/html/features/scanner/style/strip-cdata-delims";
/*   55:     */   public static final String IGNORE_SPECIFIED_CHARSET = "http://cyberneko.org/html/features/scanner/ignore-specified-charset";
/*   56:     */   public static final String CDATA_SECTIONS = "http://cyberneko.org/html/features/scanner/cdata-sections";
/*   57:     */   public static final String OVERRIDE_DOCTYPE = "http://cyberneko.org/html/features/override-doctype";
/*   58:     */   public static final String INSERT_DOCTYPE = "http://cyberneko.org/html/features/insert-doctype";
/*   59:     */   public static final String PARSE_NOSCRIPT_CONTENT = "http://cyberneko.org/html/features/parse-noscript-content";
/*   60:     */   public static final String ALLOW_SELFCLOSING_IFRAME = "http://cyberneko.org/html/features/scanner/allow-selfclosing-iframe";
/*   61:     */   protected static final String NORMALIZE_ATTRIBUTES = "http://cyberneko.org/html/features/scanner/normalize-attrs";
/*   62: 214 */   private static final String[] RECOGNIZED_FEATURES = { "http://cyberneko.org/html/features/augmentations", "http://cyberneko.org/html/features/report-errors", "http://apache.org/xml/features/scanner/notify-char-refs", "http://apache.org/xml/features/scanner/notify-builtin-refs", "http://cyberneko.org/html/features/scanner/notify-builtin-refs", "http://cyberneko.org/html/features/scanner/fix-mswindows-refs", "http://cyberneko.org/html/features/scanner/script/strip-cdata-delims", "http://cyberneko.org/html/features/scanner/script/strip-comment-delims", "http://cyberneko.org/html/features/scanner/style/strip-cdata-delims", "http://cyberneko.org/html/features/scanner/style/strip-comment-delims", "http://cyberneko.org/html/features/scanner/ignore-specified-charset", "http://cyberneko.org/html/features/scanner/cdata-sections", "http://cyberneko.org/html/features/override-doctype", "http://cyberneko.org/html/features/insert-doctype", "http://cyberneko.org/html/features/scanner/normalize-attrs", "http://cyberneko.org/html/features/parse-noscript-content", "http://cyberneko.org/html/features/scanner/allow-selfclosing-iframe" };
/*   63: 235 */   private static final Boolean[] RECOGNIZED_FEATURES_DEFAULTS = { null, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE };
/*   64:     */   protected static final String NAMES_ELEMS = "http://cyberneko.org/html/properties/names/elems";
/*   65:     */   protected static final String NAMES_ATTRS = "http://cyberneko.org/html/properties/names/attrs";
/*   66:     */   protected static final String DEFAULT_ENCODING = "http://cyberneko.org/html/properties/default-encoding";
/*   67:     */   protected static final String ERROR_REPORTER = "http://cyberneko.org/html/properties/error-reporter";
/*   68:     */   protected static final String DOCTYPE_PUBID = "http://cyberneko.org/html/properties/doctype/pubid";
/*   69:     */   protected static final String DOCTYPE_SYSID = "http://cyberneko.org/html/properties/doctype/sysid";
/*   70: 276 */   private static final String[] RECOGNIZED_PROPERTIES = { "http://cyberneko.org/html/properties/names/elems", "http://cyberneko.org/html/properties/names/attrs", "http://cyberneko.org/html/properties/default-encoding", "http://cyberneko.org/html/properties/error-reporter", "http://cyberneko.org/html/properties/doctype/pubid", "http://cyberneko.org/html/properties/doctype/sysid" };
/*   71: 286 */   private static final Object[] RECOGNIZED_PROPERTIES_DEFAULTS = { null, null, "Windows-1252", null, "-//W3C//DTD HTML 4.01 Transitional//EN", "http://www.w3.org/TR/html4/loose.dtd" };
/*   72:     */   protected static final short STATE_CONTENT = 0;
/*   73:     */   protected static final short STATE_MARKUP_BRACKET = 1;
/*   74:     */   protected static final short STATE_START_DOCUMENT = 10;
/*   75:     */   protected static final short STATE_END_DOCUMENT = 11;
/*   76:     */   protected static final short NAMES_NO_CHANGE = 0;
/*   77:     */   protected static final short NAMES_UPPERCASE = 1;
/*   78:     */   protected static final short NAMES_LOWERCASE = 2;
/*   79:     */   protected static final int DEFAULT_BUFFER_SIZE = 2048;
/*   80:     */   private static final boolean DEBUG_SCANNER = false;
/*   81:     */   private static final boolean DEBUG_SCANNER_STATE = false;
/*   82:     */   private static final boolean DEBUG_BUFFER = false;
/*   83:     */   private static final boolean DEBUG_CHARSET = false;
/*   84:     */   protected static final boolean DEBUG_CALLBACKS = false;
/*   85: 345 */   protected static final HTMLEventInfo SYNTHESIZED_ITEM = new HTMLEventInfo.SynthesizedItem();
/*   86: 348 */   private static final BitSet ENTITY_CHARS = new BitSet();
/*   87:     */   protected boolean fAugmentations;
/*   88:     */   protected boolean fReportErrors;
/*   89:     */   protected boolean fNotifyCharRefs;
/*   90:     */   protected boolean fNotifyXmlBuiltinRefs;
/*   91:     */   protected boolean fNotifyHtmlBuiltinRefs;
/*   92:     */   protected boolean fFixWindowsCharRefs;
/*   93:     */   protected boolean fScriptStripCDATADelims;
/*   94:     */   protected boolean fScriptStripCommentDelims;
/*   95:     */   protected boolean fStyleStripCDATADelims;
/*   96:     */   protected boolean fStyleStripCommentDelims;
/*   97:     */   protected boolean fIgnoreSpecifiedCharset;
/*   98:     */   protected boolean fCDATASections;
/*   99:     */   protected boolean fOverrideDoctype;
/*  100:     */   protected boolean fInsertDoctype;
/*  101:     */   protected boolean fNormalizeAttributes;
/*  102:     */   protected boolean fParseNoScriptContent;
/*  103:     */   protected boolean fParseNoFramesContent;
/*  104:     */   protected boolean fAllowSelfclosingIframe;
/*  105:     */   protected short fNamesElems;
/*  106:     */   protected short fNamesAttrs;
/*  107:     */   protected String fDefaultIANAEncoding;
/*  108:     */   protected HTMLErrorReporter fErrorReporter;
/*  109:     */   protected String fDoctypePubid;
/*  110:     */   protected String fDoctypeSysid;
/*  111:     */   protected int fBeginLineNumber;
/*  112:     */   protected int fBeginColumnNumber;
/*  113:     */   protected int fBeginCharacterOffset;
/*  114:     */   protected int fEndLineNumber;
/*  115:     */   protected int fEndColumnNumber;
/*  116:     */   protected int fEndCharacterOffset;
/*  117:     */   protected PlaybackInputStream fByteStream;
/*  118:     */   protected CurrentEntity fCurrentEntity;
/*  119:     */   protected final Stack fCurrentEntityStack;
/*  120:     */   protected Scanner fScanner;
/*  121:     */   protected short fScannerState;
/*  122:     */   protected XMLDocumentHandler fDocumentHandler;
/*  123:     */   protected String fIANAEncoding;
/*  124:     */   protected String fJavaEncoding;
/*  125:     */   protected boolean fIso8859Encoding;
/*  126:     */   protected int fElementCount;
/*  127:     */   protected int fElementDepth;
/*  128:     */   protected Scanner fContentScanner;
/*  129:     */   protected SpecialScanner fSpecialScanner;
/*  130:     */   protected final XMLStringBuffer fStringBuffer;
/*  131:     */   private final XMLStringBuffer fStringBuffer2;
/*  132:     */   private final XMLStringBuffer fNonNormAttr;
/*  133:     */   private final HTMLAugmentations fInfosetAugs;
/*  134:     */   private final LocationItem fLocationItem;
/*  135:     */   private final boolean[] fSingleBoolean;
/*  136:     */   private final XMLResourceIdentifierImpl fResourceId;
/*  137:     */   
/*  138:     */   static
/*  139:     */   {
/*  140: 350 */     String str = "-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
/*  141: 351 */     for (int i = 0; i < "-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".length(); i++)
/*  142:     */     {
/*  143: 352 */       char c = "-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt(i);
/*  144: 353 */       ENTITY_CHARS.set(c);
/*  145:     */     }
/*  146:     */   }
/*  147:     */   
/*  148:     */   public HTMLScanner()
/*  149:     */   {
/*  150: 465 */     this.fCurrentEntityStack = new Stack();
/*  151:     */     
/*  152:     */ 
/*  153:     */ 
/*  154:     */ 
/*  155:     */ 
/*  156:     */ 
/*  157:     */ 
/*  158:     */ 
/*  159:     */ 
/*  160:     */ 
/*  161:     */ 
/*  162:     */ 
/*  163:     */ 
/*  164:     */ 
/*  165:     */ 
/*  166:     */ 
/*  167:     */ 
/*  168:     */ 
/*  169:     */ 
/*  170:     */ 
/*  171:     */ 
/*  172:     */ 
/*  173:     */ 
/*  174:     */ 
/*  175:     */ 
/*  176:     */ 
/*  177:     */ 
/*  178:     */ 
/*  179: 494 */     this.fContentScanner = new ContentScanner();
/*  180:     */     
/*  181:     */ 
/*  182:     */ 
/*  183:     */ 
/*  184:     */ 
/*  185:     */ 
/*  186: 501 */     this.fSpecialScanner = new SpecialScanner();
/*  187:     */     
/*  188:     */ 
/*  189:     */ 
/*  190:     */ 
/*  191: 506 */     this.fStringBuffer = new XMLStringBuffer(1024);
/*  192:     */     
/*  193:     */ 
/*  194: 509 */     this.fStringBuffer2 = new XMLStringBuffer(1024);
/*  195:     */     
/*  196:     */ 
/*  197: 512 */     this.fNonNormAttr = new XMLStringBuffer(128);
/*  198:     */     
/*  199:     */ 
/*  200: 515 */     this.fInfosetAugs = new HTMLAugmentations();
/*  201:     */     
/*  202:     */ 
/*  203: 518 */     this.fLocationItem = new LocationItem();
/*  204:     */     
/*  205:     */ 
/*  206: 521 */     this.fSingleBoolean = new boolean[] { false };
/*  207:     */     
/*  208:     */ 
/*  209: 524 */     this.fResourceId = new XMLResourceIdentifierImpl();
/*  210:     */   }
/*  211:     */   
/*  212:     */   public void pushInputSource(XMLInputSource inputSource)
/*  213:     */   {
/*  214: 545 */     Reader reader = getReader(inputSource);
/*  215:     */     
/*  216: 547 */     this.fCurrentEntityStack.push(this.fCurrentEntity);
/*  217: 548 */     String encoding = inputSource.getEncoding();
/*  218: 549 */     String publicId = inputSource.getPublicId();
/*  219: 550 */     String baseSystemId = inputSource.getBaseSystemId();
/*  220: 551 */     String literalSystemId = inputSource.getSystemId();
/*  221: 552 */     String expandedSystemId = expandSystemId(literalSystemId, baseSystemId);
/*  222: 553 */     this.fCurrentEntity = new CurrentEntity(reader, encoding, publicId, baseSystemId, literalSystemId, expandedSystemId);
/*  223:     */   }
/*  224:     */   
/*  225:     */   private Reader getReader(XMLInputSource inputSource)
/*  226:     */   {
/*  227: 559 */     Reader reader = inputSource.getCharacterStream();
/*  228: 560 */     if (reader == null) {
/*  229:     */       try
/*  230:     */       {
/*  231: 562 */         return new InputStreamReader(inputSource.getByteStream(), this.fJavaEncoding);
/*  232:     */       }
/*  233:     */       catch (UnsupportedEncodingException e) {}
/*  234:     */     }
/*  235: 568 */     return reader;
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void evaluateInputSource(XMLInputSource inputSource)
/*  239:     */   {
/*  240: 579 */     Scanner previousScanner = this.fScanner;
/*  241: 580 */     short previousScannerState = this.fScannerState;
/*  242: 581 */     CurrentEntity previousEntity = this.fCurrentEntity;
/*  243: 582 */     Reader reader = getReader(inputSource);
/*  244:     */     
/*  245: 584 */     String encoding = inputSource.getEncoding();
/*  246: 585 */     String publicId = inputSource.getPublicId();
/*  247: 586 */     String baseSystemId = inputSource.getBaseSystemId();
/*  248: 587 */     String literalSystemId = inputSource.getSystemId();
/*  249: 588 */     String expandedSystemId = expandSystemId(literalSystemId, baseSystemId);
/*  250: 589 */     this.fCurrentEntity = new CurrentEntity(reader, encoding, publicId, baseSystemId, literalSystemId, expandedSystemId);
/*  251:     */     
/*  252:     */ 
/*  253: 592 */     setScanner(this.fContentScanner);
/*  254: 593 */     setScannerState((short)0);
/*  255:     */     try
/*  256:     */     {
/*  257:     */       do
/*  258:     */       {
/*  259: 596 */         this.fScanner.scan(false);
/*  260: 597 */       } while (this.fScannerState != 11);
/*  261:     */     }
/*  262:     */     catch (IOException e) {}
/*  263: 602 */     setScanner(previousScanner);
/*  264: 603 */     setScannerState(previousScannerState);
/*  265: 604 */     this.fCurrentEntity = previousEntity;
/*  266:     */   }
/*  267:     */   
/*  268:     */   public void cleanup(boolean closeall)
/*  269:     */   {
/*  270: 618 */     int size = this.fCurrentEntityStack.size();
/*  271: 619 */     if (size > 0)
/*  272:     */     {
/*  273: 621 */       if (this.fCurrentEntity != null) {
/*  274: 622 */         this.fCurrentEntity.closeQuietly();
/*  275:     */       }
/*  276: 625 */       for (int i = closeall ? 0 : 1; i < size; i++)
/*  277:     */       {
/*  278: 626 */         this.fCurrentEntity = ((CurrentEntity)this.fCurrentEntityStack.pop());
/*  279: 627 */         this.fCurrentEntity.closeQuietly();
/*  280:     */       }
/*  281:     */     }
/*  282: 630 */     else if ((closeall) && (this.fCurrentEntity != null))
/*  283:     */     {
/*  284: 631 */       this.fCurrentEntity.closeQuietly();
/*  285:     */     }
/*  286:     */   }
/*  287:     */   
/*  288:     */   public String getEncoding()
/*  289:     */   {
/*  290: 641 */     return this.fCurrentEntity != null ? this.fCurrentEntity.encoding : null;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public String getPublicId()
/*  294:     */   {
/*  295: 646 */     return this.fCurrentEntity != null ? this.fCurrentEntity.publicId : null;
/*  296:     */   }
/*  297:     */   
/*  298:     */   public String getBaseSystemId()
/*  299:     */   {
/*  300: 651 */     return this.fCurrentEntity != null ? this.fCurrentEntity.baseSystemId : null;
/*  301:     */   }
/*  302:     */   
/*  303:     */   public String getLiteralSystemId()
/*  304:     */   {
/*  305: 656 */     return this.fCurrentEntity != null ? this.fCurrentEntity.literalSystemId : null;
/*  306:     */   }
/*  307:     */   
/*  308:     */   public String getExpandedSystemId()
/*  309:     */   {
/*  310: 661 */     return this.fCurrentEntity != null ? this.fCurrentEntity.expandedSystemId : null;
/*  311:     */   }
/*  312:     */   
/*  313:     */   public int getLineNumber()
/*  314:     */   {
/*  315: 666 */     return this.fCurrentEntity != null ? this.fCurrentEntity.getLineNumber() : -1;
/*  316:     */   }
/*  317:     */   
/*  318:     */   public int getColumnNumber()
/*  319:     */   {
/*  320: 671 */     return this.fCurrentEntity != null ? this.fCurrentEntity.getColumnNumber() : -1;
/*  321:     */   }
/*  322:     */   
/*  323:     */   public String getXMLVersion()
/*  324:     */   {
/*  325: 676 */     this.fCurrentEntity.getClass();return this.fCurrentEntity != null ? "1.0" : null;
/*  326:     */   }
/*  327:     */   
/*  328:     */   public int getCharacterOffset()
/*  329:     */   {
/*  330: 681 */     return this.fCurrentEntity != null ? this.fCurrentEntity.getCharacterOffset() : -1;
/*  331:     */   }
/*  332:     */   
/*  333:     */   public Boolean getFeatureDefault(String featureId)
/*  334:     */   {
/*  335: 690 */     int length = RECOGNIZED_FEATURES != null ? RECOGNIZED_FEATURES.length : 0;
/*  336: 691 */     for (int i = 0; i < length; i++) {
/*  337: 692 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/*  338: 693 */         return RECOGNIZED_FEATURES_DEFAULTS[i];
/*  339:     */       }
/*  340:     */     }
/*  341: 696 */     return null;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public Object getPropertyDefault(String propertyId)
/*  345:     */   {
/*  346: 701 */     int length = RECOGNIZED_PROPERTIES != null ? RECOGNIZED_PROPERTIES.length : 0;
/*  347: 702 */     for (int i = 0; i < length; i++) {
/*  348: 703 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/*  349: 704 */         return RECOGNIZED_PROPERTIES_DEFAULTS[i];
/*  350:     */       }
/*  351:     */     }
/*  352: 707 */     return null;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public String[] getRecognizedFeatures()
/*  356:     */   {
/*  357: 716 */     return RECOGNIZED_FEATURES;
/*  358:     */   }
/*  359:     */   
/*  360:     */   public String[] getRecognizedProperties()
/*  361:     */   {
/*  362: 721 */     return RECOGNIZED_PROPERTIES;
/*  363:     */   }
/*  364:     */   
/*  365:     */   public void reset(XMLComponentManager manager)
/*  366:     */     throws XMLConfigurationException
/*  367:     */   {
/*  368: 729 */     this.fAugmentations = manager.getFeature("http://cyberneko.org/html/features/augmentations");
/*  369: 730 */     this.fReportErrors = manager.getFeature("http://cyberneko.org/html/features/report-errors");
/*  370: 731 */     this.fNotifyCharRefs = manager.getFeature("http://apache.org/xml/features/scanner/notify-char-refs");
/*  371: 732 */     this.fNotifyXmlBuiltinRefs = manager.getFeature("http://apache.org/xml/features/scanner/notify-builtin-refs");
/*  372: 733 */     this.fNotifyHtmlBuiltinRefs = manager.getFeature("http://cyberneko.org/html/features/scanner/notify-builtin-refs");
/*  373: 734 */     this.fFixWindowsCharRefs = manager.getFeature("http://cyberneko.org/html/features/scanner/fix-mswindows-refs");
/*  374: 735 */     this.fScriptStripCDATADelims = manager.getFeature("http://cyberneko.org/html/features/scanner/script/strip-cdata-delims");
/*  375: 736 */     this.fScriptStripCommentDelims = manager.getFeature("http://cyberneko.org/html/features/scanner/script/strip-comment-delims");
/*  376: 737 */     this.fStyleStripCDATADelims = manager.getFeature("http://cyberneko.org/html/features/scanner/style/strip-cdata-delims");
/*  377: 738 */     this.fStyleStripCommentDelims = manager.getFeature("http://cyberneko.org/html/features/scanner/style/strip-comment-delims");
/*  378: 739 */     this.fIgnoreSpecifiedCharset = manager.getFeature("http://cyberneko.org/html/features/scanner/ignore-specified-charset");
/*  379: 740 */     this.fCDATASections = manager.getFeature("http://cyberneko.org/html/features/scanner/cdata-sections");
/*  380: 741 */     this.fOverrideDoctype = manager.getFeature("http://cyberneko.org/html/features/override-doctype");
/*  381: 742 */     this.fInsertDoctype = manager.getFeature("http://cyberneko.org/html/features/insert-doctype");
/*  382: 743 */     this.fNormalizeAttributes = manager.getFeature("http://cyberneko.org/html/features/scanner/normalize-attrs");
/*  383: 744 */     this.fParseNoScriptContent = manager.getFeature("http://cyberneko.org/html/features/parse-noscript-content");
/*  384: 745 */     this.fAllowSelfclosingIframe = manager.getFeature("http://cyberneko.org/html/features/scanner/allow-selfclosing-iframe");
/*  385:     */     
/*  386:     */ 
/*  387: 748 */     this.fNamesElems = getNamesValue(String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/names/elems")));
/*  388: 749 */     this.fNamesAttrs = getNamesValue(String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/names/attrs")));
/*  389: 750 */     this.fDefaultIANAEncoding = String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/default-encoding"));
/*  390: 751 */     this.fErrorReporter = ((HTMLErrorReporter)manager.getProperty("http://cyberneko.org/html/properties/error-reporter"));
/*  391: 752 */     this.fDoctypePubid = String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/doctype/pubid"));
/*  392: 753 */     this.fDoctypeSysid = String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/doctype/sysid"));
/*  393:     */   }
/*  394:     */   
/*  395:     */   public void setFeature(String featureId, boolean state)
/*  396:     */     throws XMLConfigurationException
/*  397:     */   {
/*  398: 761 */     if (featureId.equals("http://cyberneko.org/html/features/augmentations")) {
/*  399: 762 */       this.fAugmentations = state;
/*  400: 764 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/ignore-specified-charset")) {
/*  401: 765 */       this.fIgnoreSpecifiedCharset = state;
/*  402: 767 */     } else if (featureId.equals("http://apache.org/xml/features/scanner/notify-char-refs")) {
/*  403: 768 */       this.fNotifyCharRefs = state;
/*  404: 770 */     } else if (featureId.equals("http://apache.org/xml/features/scanner/notify-builtin-refs")) {
/*  405: 771 */       this.fNotifyXmlBuiltinRefs = state;
/*  406: 773 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/notify-builtin-refs")) {
/*  407: 774 */       this.fNotifyHtmlBuiltinRefs = state;
/*  408: 776 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/fix-mswindows-refs")) {
/*  409: 777 */       this.fFixWindowsCharRefs = state;
/*  410: 779 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/script/strip-cdata-delims")) {
/*  411: 780 */       this.fScriptStripCDATADelims = state;
/*  412: 782 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/script/strip-comment-delims")) {
/*  413: 783 */       this.fScriptStripCommentDelims = state;
/*  414: 785 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/style/strip-cdata-delims")) {
/*  415: 786 */       this.fStyleStripCDATADelims = state;
/*  416: 788 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/style/strip-comment-delims")) {
/*  417: 789 */       this.fStyleStripCommentDelims = state;
/*  418: 791 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/ignore-specified-charset")) {
/*  419: 792 */       this.fIgnoreSpecifiedCharset = state;
/*  420: 794 */     } else if (featureId.equals("http://cyberneko.org/html/features/parse-noscript-content")) {
/*  421: 795 */       this.fParseNoScriptContent = state;
/*  422: 797 */     } else if (featureId.equals("http://cyberneko.org/html/features/scanner/allow-selfclosing-iframe")) {
/*  423: 798 */       this.fAllowSelfclosingIframe = state;
/*  424:     */     }
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void setProperty(String propertyId, Object value)
/*  428:     */     throws XMLConfigurationException
/*  429:     */   {
/*  430: 807 */     if (propertyId.equals("http://cyberneko.org/html/properties/names/elems"))
/*  431:     */     {
/*  432: 808 */       this.fNamesElems = getNamesValue(String.valueOf(value));
/*  433: 809 */       return;
/*  434:     */     }
/*  435: 812 */     if (propertyId.equals("http://cyberneko.org/html/properties/names/attrs"))
/*  436:     */     {
/*  437: 813 */       this.fNamesAttrs = getNamesValue(String.valueOf(value));
/*  438: 814 */       return;
/*  439:     */     }
/*  440: 817 */     if (propertyId.equals("http://cyberneko.org/html/properties/default-encoding"))
/*  441:     */     {
/*  442: 818 */       this.fDefaultIANAEncoding = String.valueOf(value);
/*  443: 819 */       return;
/*  444:     */     }
/*  445:     */   }
/*  446:     */   
/*  447:     */   public void setInputSource(XMLInputSource source)
/*  448:     */     throws IOException
/*  449:     */   {
/*  450: 832 */     this.fElementCount = 0;
/*  451: 833 */     this.fElementDepth = -1;
/*  452: 834 */     this.fByteStream = null;
/*  453: 835 */     this.fCurrentEntityStack.removeAllElements();
/*  454:     */     
/*  455: 837 */     this.fBeginLineNumber = 1;
/*  456: 838 */     this.fBeginColumnNumber = 1;
/*  457: 839 */     this.fBeginCharacterOffset = 0;
/*  458: 840 */     this.fEndLineNumber = this.fBeginLineNumber;
/*  459: 841 */     this.fEndColumnNumber = this.fBeginColumnNumber;
/*  460: 842 */     this.fEndCharacterOffset = this.fBeginCharacterOffset;
/*  461:     */     
/*  462:     */ 
/*  463: 845 */     this.fIANAEncoding = this.fDefaultIANAEncoding;
/*  464: 846 */     this.fJavaEncoding = this.fIANAEncoding;
/*  465:     */     
/*  466:     */ 
/*  467: 849 */     String encoding = source.getEncoding();
/*  468: 850 */     String publicId = source.getPublicId();
/*  469: 851 */     String baseSystemId = source.getBaseSystemId();
/*  470: 852 */     String literalSystemId = source.getSystemId();
/*  471: 853 */     String expandedSystemId = expandSystemId(literalSystemId, baseSystemId);
/*  472:     */     
/*  473:     */ 
/*  474: 856 */     Reader reader = source.getCharacterStream();
/*  475: 857 */     if (reader == null)
/*  476:     */     {
/*  477: 858 */       InputStream inputStream = source.getByteStream();
/*  478: 859 */       if (inputStream == null)
/*  479:     */       {
/*  480: 860 */         URL url = new URL(expandedSystemId);
/*  481: 861 */         inputStream = url.openStream();
/*  482:     */       }
/*  483: 863 */       this.fByteStream = new PlaybackInputStream(inputStream);
/*  484: 864 */       String[] encodings = new String[2];
/*  485: 865 */       if (encoding == null) {
/*  486: 866 */         this.fByteStream.detectEncoding(encodings);
/*  487:     */       } else {
/*  488: 869 */         encodings[0] = encoding;
/*  489:     */       }
/*  490: 871 */       if (encodings[0] == null)
/*  491:     */       {
/*  492: 872 */         encodings[0] = this.fDefaultIANAEncoding;
/*  493: 873 */         if (this.fReportErrors) {
/*  494: 874 */           this.fErrorReporter.reportWarning("HTML1000", null);
/*  495:     */         }
/*  496:     */       }
/*  497: 877 */       if (encodings[1] == null)
/*  498:     */       {
/*  499: 878 */         encodings[1] = EncodingMap.getIANA2JavaMapping(encodings[0].toUpperCase());
/*  500: 879 */         if (encodings[1] == null)
/*  501:     */         {
/*  502: 880 */           encodings[1] = encodings[0];
/*  503: 881 */           if (this.fReportErrors) {
/*  504: 882 */             this.fErrorReporter.reportWarning("HTML1001", new Object[] { encodings[0] });
/*  505:     */           }
/*  506:     */         }
/*  507:     */       }
/*  508: 886 */       this.fIANAEncoding = encodings[0];
/*  509: 887 */       this.fJavaEncoding = encodings[1];
/*  510:     */       
/*  511: 889 */       this.fIso8859Encoding = ((this.fIANAEncoding == null) || (this.fIANAEncoding.toUpperCase().startsWith("ISO-8859")) || (this.fIANAEncoding.equalsIgnoreCase(this.fDefaultIANAEncoding)));
/*  512:     */       
/*  513:     */ 
/*  514: 892 */       encoding = this.fIANAEncoding;
/*  515: 893 */       reader = new InputStreamReader(this.fByteStream, this.fJavaEncoding);
/*  516:     */     }
/*  517: 895 */     this.fCurrentEntity = new CurrentEntity(reader, encoding, publicId, baseSystemId, literalSystemId, expandedSystemId);
/*  518:     */     
/*  519:     */ 
/*  520:     */ 
/*  521:     */ 
/*  522: 900 */     setScanner(this.fContentScanner);
/*  523: 901 */     setScannerState((short)10);
/*  524:     */   }
/*  525:     */   
/*  526:     */   public boolean scanDocument(boolean complete)
/*  527:     */     throws XNIException, IOException
/*  528:     */   {
/*  529:     */     do
/*  530:     */     {
/*  531: 908 */       if (!this.fScanner.scan(complete)) {
/*  532: 909 */         return false;
/*  533:     */       }
/*  534: 911 */     } while (complete);
/*  535: 912 */     return true;
/*  536:     */   }
/*  537:     */   
/*  538:     */   public void setDocumentHandler(XMLDocumentHandler handler)
/*  539:     */   {
/*  540: 917 */     this.fDocumentHandler = handler;
/*  541:     */   }
/*  542:     */   
/*  543:     */   public XMLDocumentHandler getDocumentHandler()
/*  544:     */   {
/*  545: 924 */     return this.fDocumentHandler;
/*  546:     */   }
/*  547:     */   
/*  548:     */   protected static String getValue(XMLAttributes attrs, String aname)
/*  549:     */   {
/*  550: 933 */     int length = attrs != null ? attrs.getLength() : 0;
/*  551: 934 */     for (int i = 0; i < length; i++) {
/*  552: 935 */       if (attrs.getQName(i).equalsIgnoreCase(aname)) {
/*  553: 936 */         return attrs.getValue(i);
/*  554:     */       }
/*  555:     */     }
/*  556: 939 */     return null;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public static String expandSystemId(String systemId, String baseSystemId)
/*  560:     */   {
/*  561: 958 */     if ((systemId == null) || (systemId.length() == 0)) {
/*  562: 959 */       return systemId;
/*  563:     */     }
/*  564:     */     try
/*  565:     */     {
/*  566: 963 */       URI uri = new URI(systemId);
/*  567: 964 */       if (uri != null) {
/*  568: 965 */         return systemId;
/*  569:     */       }
/*  570:     */     }
/*  571:     */     catch (URI.MalformedURIException e) {}
/*  572: 972 */     String id = fixURI(systemId);
/*  573:     */     
/*  574:     */ 
/*  575: 975 */     URI base = null;
/*  576: 976 */     URI uri = null;
/*  577:     */     try
/*  578:     */     {
/*  579: 978 */       if ((baseSystemId == null) || (baseSystemId.length() == 0) || (baseSystemId.equals(systemId)))
/*  580:     */       {
/*  581:     */         String dir;
/*  582:     */         try
/*  583:     */         {
/*  584: 982 */           dir = fixURI(System.getProperty("user.dir"));
/*  585:     */         }
/*  586:     */         catch (SecurityException se)
/*  587:     */         {
/*  588: 985 */           dir = "";
/*  589:     */         }
/*  590: 987 */         if (!dir.endsWith("/")) {
/*  591: 988 */           dir = dir + "/";
/*  592:     */         }
/*  593: 990 */         base = new URI("file", "", dir, null, null);
/*  594:     */       }
/*  595:     */       else
/*  596:     */       {
/*  597:     */         try
/*  598:     */         {
/*  599: 994 */           base = new URI(fixURI(baseSystemId));
/*  600:     */         }
/*  601:     */         catch (URI.MalformedURIException e)
/*  602:     */         {
/*  603:     */           String dir;
/*  604:     */           try
/*  605:     */           {
/*  606: 999 */             dir = fixURI(System.getProperty("user.dir"));
/*  607:     */           }
/*  608:     */           catch (SecurityException se)
/*  609:     */           {
/*  610:1002 */             dir = "";
/*  611:     */           }
/*  612:1004 */           if (baseSystemId.indexOf(':') != -1)
/*  613:     */           {
/*  614:1007 */             base = new URI("file", "", fixURI(baseSystemId), null, null);
/*  615:     */           }
/*  616:     */           else
/*  617:     */           {
/*  618:1010 */             if (!dir.endsWith("/")) {
/*  619:1011 */               dir = dir + "/";
/*  620:     */             }
/*  621:1013 */             dir = dir + fixURI(baseSystemId);
/*  622:1014 */             base = new URI("file", "", dir, null, null);
/*  623:     */           }
/*  624:     */         }
/*  625:     */       }
/*  626:1019 */       uri = new URI(base, id);
/*  627:     */     }
/*  628:     */     catch (URI.MalformedURIException e) {}
/*  629:1025 */     if (uri == null) {
/*  630:1026 */       return systemId;
/*  631:     */     }
/*  632:1028 */     return uri.toString();
/*  633:     */   }
/*  634:     */   
/*  635:     */   protected static String fixURI(String str)
/*  636:     */   {
/*  637:1042 */     str = str.replace(File.separatorChar, '/');
/*  638:1045 */     if (str.length() >= 2)
/*  639:     */     {
/*  640:1046 */       char ch1 = str.charAt(1);
/*  641:1048 */       if (ch1 == ':')
/*  642:     */       {
/*  643:1049 */         char ch0 = Character.toUpperCase(str.charAt(0));
/*  644:1050 */         if ((ch0 >= 'A') && (ch0 <= 'Z')) {
/*  645:1051 */           str = "/" + str;
/*  646:     */         }
/*  647:     */       }
/*  648:1055 */       else if ((ch1 == '/') && (str.charAt(0) == '/'))
/*  649:     */       {
/*  650:1056 */         str = "file:" + str;
/*  651:     */       }
/*  652:     */     }
/*  653:1061 */     return str;
/*  654:     */   }
/*  655:     */   
/*  656:     */   protected static final String modifyName(String name, short mode)
/*  657:     */   {
/*  658:1067 */     switch (mode)
/*  659:     */     {
/*  660:     */     case 1: 
/*  661:1068 */       return name.toUpperCase();
/*  662:     */     case 2: 
/*  663:1069 */       return name.toLowerCase();
/*  664:     */     }
/*  665:1071 */     return name;
/*  666:     */   }
/*  667:     */   
/*  668:     */   protected static final short getNamesValue(String value)
/*  669:     */   {
/*  670:1082 */     if (value.equals("lower")) {
/*  671:1083 */       return 2;
/*  672:     */     }
/*  673:1085 */     if (value.equals("upper")) {
/*  674:1086 */       return 1;
/*  675:     */     }
/*  676:1088 */     return 0;
/*  677:     */   }
/*  678:     */   
/*  679:     */   protected int fixWindowsCharacter(int origChar)
/*  680:     */   {
/*  681:1099 */     switch (origChar)
/*  682:     */     {
/*  683:     */     case 130: 
/*  684:1100 */       return 8218;
/*  685:     */     case 131: 
/*  686:1101 */       return 402;
/*  687:     */     case 132: 
/*  688:1102 */       return 8222;
/*  689:     */     case 133: 
/*  690:1103 */       return 8230;
/*  691:     */     case 134: 
/*  692:1104 */       return 8224;
/*  693:     */     case 135: 
/*  694:1105 */       return 8225;
/*  695:     */     case 136: 
/*  696:1106 */       return 710;
/*  697:     */     case 137: 
/*  698:1107 */       return 8240;
/*  699:     */     case 138: 
/*  700:1108 */       return 352;
/*  701:     */     case 139: 
/*  702:1109 */       return 8249;
/*  703:     */     case 140: 
/*  704:1110 */       return 338;
/*  705:     */     case 145: 
/*  706:1111 */       return 8216;
/*  707:     */     case 146: 
/*  708:1112 */       return 8217;
/*  709:     */     case 147: 
/*  710:1113 */       return 8220;
/*  711:     */     case 148: 
/*  712:1114 */       return 8221;
/*  713:     */     case 149: 
/*  714:1115 */       return 8226;
/*  715:     */     case 150: 
/*  716:1116 */       return 8211;
/*  717:     */     case 151: 
/*  718:1117 */       return 8212;
/*  719:     */     case 152: 
/*  720:1118 */       return 732;
/*  721:     */     case 153: 
/*  722:1119 */       return 8482;
/*  723:     */     case 154: 
/*  724:1120 */       return 353;
/*  725:     */     case 155: 
/*  726:1121 */       return 8250;
/*  727:     */     case 156: 
/*  728:1122 */       return 339;
/*  729:     */     case 159: 
/*  730:1123 */       return 376;
/*  731:     */     }
/*  732:1125 */     return origChar;
/*  733:     */   }
/*  734:     */   
/*  735:     */   protected int read()
/*  736:     */     throws IOException
/*  737:     */   {
/*  738:1135 */     return this.fCurrentEntity.read();
/*  739:     */   }
/*  740:     */   
/*  741:     */   protected void setScanner(Scanner scanner)
/*  742:     */   {
/*  743:1143 */     this.fScanner = scanner;
/*  744:     */   }
/*  745:     */   
/*  746:     */   protected void setScannerState(short state)
/*  747:     */   {
/*  748:1153 */     this.fScannerState = state;
/*  749:     */   }
/*  750:     */   
/*  751:     */   protected void scanDoctype()
/*  752:     */     throws IOException
/*  753:     */   {
/*  754:1170 */     String root = null;
/*  755:1171 */     String pubid = null;
/*  756:1172 */     String sysid = null;
/*  757:1174 */     if (skipSpaces())
/*  758:     */     {
/*  759:1175 */       root = scanName();
/*  760:1176 */       if (root == null)
/*  761:     */       {
/*  762:1177 */         if (this.fReportErrors) {
/*  763:1178 */           this.fErrorReporter.reportError("HTML1014", null);
/*  764:     */         }
/*  765:     */       }
/*  766:     */       else {
/*  767:1182 */         root = modifyName(root, this.fNamesElems);
/*  768:     */       }
/*  769:1184 */       if (skipSpaces()) {
/*  770:1185 */         if (skip("PUBLIC", false))
/*  771:     */         {
/*  772:1186 */           skipSpaces();
/*  773:1187 */           pubid = scanLiteral();
/*  774:1188 */           if (skipSpaces()) {
/*  775:1189 */             sysid = scanLiteral();
/*  776:     */           }
/*  777:     */         }
/*  778:1192 */         else if (skip("SYSTEM", false))
/*  779:     */         {
/*  780:1193 */           skipSpaces();
/*  781:1194 */           sysid = scanLiteral();
/*  782:     */         }
/*  783:     */       }
/*  784:     */     }
/*  785:     */     int c;
/*  786:1199 */     while ((c = this.fCurrentEntity.read()) != -1) {
/*  787:1200 */       if (c == 60) {
/*  788:1201 */         this.fCurrentEntity.rewind();
/*  789:1204 */       } else if (c != 62) {
/*  790:1207 */         if (c == 91) {
/*  791:1208 */           skipMarkup(true);
/*  792:     */         }
/*  793:     */       }
/*  794:     */     }
/*  795:1213 */     if (this.fDocumentHandler != null)
/*  796:     */     {
/*  797:1214 */       if (this.fOverrideDoctype)
/*  798:     */       {
/*  799:1215 */         pubid = this.fDoctypePubid;
/*  800:1216 */         sysid = this.fDoctypeSysid;
/*  801:     */       }
/*  802:1218 */       this.fEndLineNumber = this.fCurrentEntity.getLineNumber();
/*  803:1219 */       this.fEndColumnNumber = this.fCurrentEntity.getColumnNumber();
/*  804:1220 */       this.fEndCharacterOffset = this.fCurrentEntity.getCharacterOffset();
/*  805:1221 */       this.fDocumentHandler.doctypeDecl(root, pubid, sysid, locationAugs());
/*  806:     */     }
/*  807:     */   }
/*  808:     */   
/*  809:     */   protected String scanLiteral()
/*  810:     */     throws IOException
/*  811:     */   {
/*  812:1228 */     int quote = this.fCurrentEntity.read();
/*  813:1229 */     if ((quote == 39) || (quote == 34))
/*  814:     */     {
/*  815:1230 */       StringBuffer str = new StringBuffer();
/*  816:     */       int c;
/*  817:1232 */       while (((c = this.fCurrentEntity.read()) != -1) && 
/*  818:1233 */         (c != quote)) {
/*  819:1236 */         if ((c == 13) || (c == 10))
/*  820:     */         {
/*  821:1237 */           this.fCurrentEntity.rewind();
/*  822:     */           
/*  823:     */ 
/*  824:1240 */           skipNewlines();
/*  825:1241 */           str.append(' ');
/*  826:     */         }
/*  827:     */         else
/*  828:     */         {
/*  829:1243 */           if (c == 60)
/*  830:     */           {
/*  831:1244 */             this.fCurrentEntity.rewind();
/*  832:1245 */             break;
/*  833:     */           }
/*  834:1248 */           str.append((char)c);
/*  835:     */         }
/*  836:     */       }
/*  837:1251 */       if (c == -1)
/*  838:     */       {
/*  839:1252 */         if (this.fReportErrors) {
/*  840:1253 */           this.fErrorReporter.reportError("HTML1007", null);
/*  841:     */         }
/*  842:1255 */         throw new EOFException();
/*  843:     */       }
/*  844:1257 */       return str.toString();
/*  845:     */     }
/*  846:1260 */     this.fCurrentEntity.rewind();
/*  847:     */     
/*  848:1262 */     return null;
/*  849:     */   }
/*  850:     */   
/*  851:     */   protected String scanName()
/*  852:     */     throws IOException
/*  853:     */   {
/*  854:1267 */     this.fCurrentEntity.debugBufferIfNeeded("(scanName: ");
/*  855:1268 */     if ((this.fCurrentEntity.offset == this.fCurrentEntity.length) && 
/*  856:1269 */       (this.fCurrentEntity.load(0) == -1))
/*  857:     */     {
/*  858:1270 */       this.fCurrentEntity.debugBufferIfNeeded(")scanName: ");
/*  859:1271 */       return null;
/*  860:     */     }
/*  861:1274 */     int offset = this.fCurrentEntity.offset;
/*  862:     */     for (;;)
/*  863:     */     {
/*  864:1276 */       if (this.fCurrentEntity.hasNext())
/*  865:     */       {
/*  866:1277 */         char c = this.fCurrentEntity.getNextChar();
/*  867:1278 */         if ((!Character.isLetterOrDigit(c)) && (c != '-') && (c != '.') && (c != ':') && (c != '_')) {
/*  868:1280 */           this.fCurrentEntity.rewind();
/*  869:     */         } else {
/*  870:     */           continue;
/*  871:     */         }
/*  872:     */       }
/*  873:1284 */       if (this.fCurrentEntity.offset != this.fCurrentEntity.length) {
/*  874:     */         break;
/*  875:     */       }
/*  876:1285 */       int length = this.fCurrentEntity.length - offset;
/*  877:1286 */       System.arraycopy(this.fCurrentEntity.buffer, offset, this.fCurrentEntity.buffer, 0, length);
/*  878:1287 */       int count = this.fCurrentEntity.load(length);
/*  879:1288 */       offset = 0;
/*  880:1289 */       if (count == -1) {
/*  881:     */         break;
/*  882:     */       }
/*  883:     */     }
/*  884:1297 */     int length = this.fCurrentEntity.offset - offset;
/*  885:1298 */     String name = length > 0 ? new String(this.fCurrentEntity.buffer, offset, length) : null;
/*  886:1299 */     this.fCurrentEntity.debugBufferIfNeeded(")scanName: ", " -> \"" + name + '"');
/*  887:1300 */     return name;
/*  888:     */   }
/*  889:     */   
/*  890:     */   protected int scanEntityRef(XMLStringBuffer str, boolean content)
/*  891:     */     throws IOException
/*  892:     */   {
/*  893:1306 */     str.clear();
/*  894:1307 */     str.append('&');
/*  895:1308 */     boolean endsWithSemicolon = false;
/*  896:     */     for (;;)
/*  897:     */     {
/*  898:1310 */       int c = this.fCurrentEntity.read();
/*  899:1311 */       if (c == 59)
/*  900:     */       {
/*  901:1312 */         str.append(';');
/*  902:1313 */         endsWithSemicolon = true;
/*  903:1314 */         break;
/*  904:     */       }
/*  905:1316 */       if (c == -1) {
/*  906:     */         break;
/*  907:     */       }
/*  908:1319 */       if ((!ENTITY_CHARS.get(c)) && (c != 35))
/*  909:     */       {
/*  910:1320 */         this.fCurrentEntity.rewind();
/*  911:1321 */         break;
/*  912:     */       }
/*  913:1323 */       str.append((char)c);
/*  914:     */     }
/*  915:1326 */     if ((!endsWithSemicolon) && 
/*  916:1327 */       (this.fReportErrors)) {
/*  917:1328 */       this.fErrorReporter.reportWarning("HTML1004", null);
/*  918:     */     }
/*  919:1331 */     if (str.length == 1)
/*  920:     */     {
/*  921:1332 */       if ((content) && (this.fDocumentHandler != null) && (this.fElementCount >= this.fElementDepth))
/*  922:     */       {
/*  923:1333 */         this.fEndLineNumber = this.fCurrentEntity.getLineNumber();
/*  924:1334 */         this.fEndColumnNumber = this.fCurrentEntity.getColumnNumber();
/*  925:1335 */         this.fEndCharacterOffset = this.fCurrentEntity.getCharacterOffset();
/*  926:1336 */         this.fDocumentHandler.characters(str, locationAugs());
/*  927:     */       }
/*  928:1338 */       return -1;
/*  929:     */     }
/*  930:     */     String name;
/*  931:     */     String name;
/*  932:1342 */     if (endsWithSemicolon) {
/*  933:1343 */       name = str.toString().substring(1, str.length - 1);
/*  934:     */     } else {
/*  935:1345 */       name = str.toString().substring(1);
/*  936:     */     }
/*  937:1347 */     if (name.startsWith("#"))
/*  938:     */     {
/*  939:1348 */       int value = -1;
/*  940:     */       try
/*  941:     */       {
/*  942:1350 */         if ((name.startsWith("#x")) || (name.startsWith("#X"))) {
/*  943:1351 */           value = Integer.parseInt(name.substring(2), 16);
/*  944:     */         } else {
/*  945:1354 */           value = Integer.parseInt(name.substring(1));
/*  946:     */         }
/*  947:1357 */         if ((this.fFixWindowsCharRefs) && (this.fIso8859Encoding)) {
/*  948:1358 */           value = fixWindowsCharacter(value);
/*  949:     */         }
/*  950:1360 */         if ((content) && (this.fDocumentHandler != null) && (this.fElementCount >= this.fElementDepth))
/*  951:     */         {
/*  952:1361 */           this.fEndLineNumber = this.fCurrentEntity.getLineNumber();
/*  953:1362 */           this.fEndColumnNumber = this.fCurrentEntity.getColumnNumber();
/*  954:1363 */           this.fEndCharacterOffset = this.fCurrentEntity.getCharacterOffset();
/*  955:1364 */           if (this.fNotifyCharRefs)
/*  956:     */           {
/*  957:1365 */             XMLResourceIdentifier id = resourceId();
/*  958:1366 */             String encoding = null;
/*  959:1367 */             this.fDocumentHandler.startGeneralEntity(name, id, encoding, locationAugs());
/*  960:     */           }
/*  961:1369 */           str.clear();
/*  962:1370 */           str.append((char)value);
/*  963:1371 */           this.fDocumentHandler.characters(str, locationAugs());
/*  964:1372 */           if (this.fNotifyCharRefs) {
/*  965:1373 */             this.fDocumentHandler.endGeneralEntity(name, locationAugs());
/*  966:     */           }
/*  967:     */         }
/*  968:     */       }
/*  969:     */       catch (NumberFormatException e)
/*  970:     */       {
/*  971:1378 */         if (this.fReportErrors) {
/*  972:1379 */           this.fErrorReporter.reportError("HTML1005", new Object[] { name });
/*  973:     */         }
/*  974:1381 */         if ((content) && (this.fDocumentHandler != null) && (this.fElementCount >= this.fElementDepth))
/*  975:     */         {
/*  976:1382 */           this.fEndLineNumber = this.fCurrentEntity.getLineNumber();
/*  977:1383 */           this.fEndColumnNumber = this.fCurrentEntity.getColumnNumber();
/*  978:1384 */           this.fEndCharacterOffset = this.fCurrentEntity.getCharacterOffset();
/*  979:1385 */           this.fDocumentHandler.characters(str, locationAugs());
/*  980:     */         }
/*  981:     */       }
/*  982:1388 */       return value;
/*  983:     */     }
/*  984:1391 */     int c = HTMLEntities.get(name);
/*  985:     */     
/*  986:     */ 
/*  987:     */ 
/*  988:1395 */     boolean invalidEntityInAttribute = (!content) && (!endsWithSemicolon) && (c > 256);
/*  989:1396 */     if ((c == -1) || (invalidEntityInAttribute))
/*  990:     */     {
/*  991:1397 */       if (this.fReportErrors) {
/*  992:1398 */         this.fErrorReporter.reportWarning("HTML1006", new Object[] { name });
/*  993:     */       }
/*  994:1400 */       if ((content) && (this.fDocumentHandler != null) && (this.fElementCount >= this.fElementDepth))
/*  995:     */       {
/*  996:1401 */         this.fEndLineNumber = this.fCurrentEntity.getLineNumber();
/*  997:1402 */         this.fEndColumnNumber = this.fCurrentEntity.getColumnNumber();
/*  998:1403 */         this.fEndCharacterOffset = this.fCurrentEntity.getCharacterOffset();
/*  999:1404 */         this.fDocumentHandler.characters(str, locationAugs());
/* 1000:     */       }
/* 1001:1406 */       return -1;
/* 1002:     */     }
/* 1003:1408 */     if ((content) && (this.fDocumentHandler != null) && (this.fElementCount >= this.fElementDepth))
/* 1004:     */     {
/* 1005:1409 */       this.fEndLineNumber = this.fCurrentEntity.getLineNumber();
/* 1006:1410 */       this.fEndColumnNumber = this.fCurrentEntity.getColumnNumber();
/* 1007:1411 */       this.fEndCharacterOffset = this.fCurrentEntity.getCharacterOffset();
/* 1008:1412 */       boolean notify = (this.fNotifyHtmlBuiltinRefs) || ((this.fNotifyXmlBuiltinRefs) && (builtinXmlRef(name)));
/* 1009:1413 */       if (notify)
/* 1010:     */       {
/* 1011:1414 */         XMLResourceIdentifier id = resourceId();
/* 1012:1415 */         String encoding = null;
/* 1013:1416 */         this.fDocumentHandler.startGeneralEntity(name, id, encoding, locationAugs());
/* 1014:     */       }
/* 1015:1418 */       str.clear();
/* 1016:1419 */       str.append((char)c);
/* 1017:1420 */       this.fDocumentHandler.characters(str, locationAugs());
/* 1018:1421 */       if (notify) {
/* 1019:1422 */         this.fDocumentHandler.endGeneralEntity(name, locationAugs());
/* 1020:     */       }
/* 1021:     */     }
/* 1022:1425 */     return c;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   protected boolean skip(String s, boolean caseSensitive)
/* 1026:     */     throws IOException
/* 1027:     */   {
/* 1028:1431 */     int length = s != null ? s.length() : 0;
/* 1029:1432 */     for (int i = 0; i < length; i++)
/* 1030:     */     {
/* 1031:1433 */       if (this.fCurrentEntity.offset == this.fCurrentEntity.length)
/* 1032:     */       {
/* 1033:1434 */         System.arraycopy(this.fCurrentEntity.buffer, this.fCurrentEntity.offset - i, this.fCurrentEntity.buffer, 0, i);
/* 1034:1435 */         if (this.fCurrentEntity.load(i) == -1)
/* 1035:     */         {
/* 1036:1436 */           this.fCurrentEntity.offset = 0;
/* 1037:1437 */           return false;
/* 1038:     */         }
/* 1039:     */       }
/* 1040:1440 */       char c0 = s.charAt(i);
/* 1041:1441 */       char c1 = this.fCurrentEntity.getNextChar();
/* 1042:1442 */       if (!caseSensitive)
/* 1043:     */       {
/* 1044:1443 */         c0 = Character.toUpperCase(c0);
/* 1045:1444 */         c1 = Character.toUpperCase(c1);
/* 1046:     */       }
/* 1047:1446 */       if (c0 != c1)
/* 1048:     */       {
/* 1049:1447 */         this.fCurrentEntity.rewind(i + 1);
/* 1050:1448 */         return false;
/* 1051:     */       }
/* 1052:     */     }
/* 1053:1451 */     return true;
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   protected boolean skipMarkup(boolean balance)
/* 1057:     */     throws IOException
/* 1058:     */   {
/* 1059:1456 */     this.fCurrentEntity.debugBufferIfNeeded("(skipMarkup: ");
/* 1060:1457 */     int depth = 1;
/* 1061:1458 */     boolean slashgt = false;
/* 1062:1460 */     while ((this.fCurrentEntity.offset != this.fCurrentEntity.length) || 
/* 1063:1461 */       (this.fCurrentEntity.load(0) != -1)) {
/* 1064:1465 */       while (this.fCurrentEntity.hasNext())
/* 1065:     */       {
/* 1066:1466 */         char c = this.fCurrentEntity.getNextChar();
/* 1067:1467 */         if ((balance) && (c == '<'))
/* 1068:     */         {
/* 1069:1468 */           depth++;
/* 1070:     */         }
/* 1071:1470 */         else if (c == '>')
/* 1072:     */         {
/* 1073:1471 */           depth--;
/* 1074:1472 */           if (depth == 0) {
/* 1075:     */             break label204;
/* 1076:     */           }
/* 1077:     */         }
/* 1078:1476 */         else if (c == '/')
/* 1079:     */         {
/* 1080:1477 */           if ((this.fCurrentEntity.offset == this.fCurrentEntity.length) && 
/* 1081:1478 */             (this.fCurrentEntity.load(0) == -1)) {
/* 1082:     */             break label204;
/* 1083:     */           }
/* 1084:1482 */           c = this.fCurrentEntity.getNextChar();
/* 1085:1483 */           if (c == '>')
/* 1086:     */           {
/* 1087:1484 */             slashgt = true;
/* 1088:1485 */             depth--;
/* 1089:1486 */             if (depth == 0) {
/* 1090:     */               break label204;
/* 1091:     */             }
/* 1092:     */           }
/* 1093:     */           else
/* 1094:     */           {
/* 1095:1491 */             this.fCurrentEntity.rewind();
/* 1096:     */           }
/* 1097:     */         }
/* 1098:1494 */         else if ((c == '\r') || (c == '\n'))
/* 1099:     */         {
/* 1100:1495 */           this.fCurrentEntity.rewind();
/* 1101:1496 */           skipNewlines();
/* 1102:     */         }
/* 1103:     */       }
/* 1104:     */     }
/* 1105:     */     label204:
/* 1106:1500 */     this.fCurrentEntity.debugBufferIfNeeded(")skipMarkup: ", " -> " + slashgt);
/* 1107:1501 */     return slashgt;
/* 1108:     */   }
/* 1109:     */   
/* 1110:     */   protected boolean skipSpaces()
/* 1111:     */     throws IOException
/* 1112:     */   {
/* 1113:1506 */     this.fCurrentEntity.debugBufferIfNeeded("(skipSpaces: ");
/* 1114:1507 */     boolean spaces = false;
/* 1115:1509 */     while ((this.fCurrentEntity.offset != this.fCurrentEntity.length) || 
/* 1116:1510 */       (this.fCurrentEntity.load(0) != -1))
/* 1117:     */     {
/* 1118:1514 */       char c = this.fCurrentEntity.getNextChar();
/* 1119:1515 */       if (!Character.isWhitespace(c))
/* 1120:     */       {
/* 1121:1516 */         this.fCurrentEntity.rewind();
/* 1122:1517 */         break;
/* 1123:     */       }
/* 1124:1519 */       spaces = true;
/* 1125:1520 */       if ((c == '\r') || (c == '\n'))
/* 1126:     */       {
/* 1127:1521 */         this.fCurrentEntity.rewind();
/* 1128:1522 */         skipNewlines();
/* 1129:     */       }
/* 1130:     */     }
/* 1131:1526 */     this.fCurrentEntity.debugBufferIfNeeded(")skipSpaces: ", " -> " + spaces);
/* 1132:1527 */     return spaces;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   protected int skipNewlines()
/* 1136:     */     throws IOException
/* 1137:     */   {
/* 1138:1532 */     this.fCurrentEntity.debugBufferIfNeeded("(skipNewlines: ");
/* 1139:1534 */     if ((!this.fCurrentEntity.hasNext()) && 
/* 1140:1535 */       (this.fCurrentEntity.load(0) == -1))
/* 1141:     */     {
/* 1142:1536 */       this.fCurrentEntity.debugBufferIfNeeded(")skipNewlines: ");
/* 1143:1537 */       return 0;
/* 1144:     */     }
/* 1145:1540 */     char c = this.fCurrentEntity.getCurrentChar();
/* 1146:1541 */     int newlines = 0;
/* 1147:1542 */     int offset = this.fCurrentEntity.offset;
/* 1148:1543 */     if ((c == '\n') || (c == '\r'))
/* 1149:     */     {
/* 1150:     */       do
/* 1151:     */       {
/* 1152:1545 */         c = this.fCurrentEntity.getNextChar();
/* 1153:1546 */         if (c == '\r')
/* 1154:     */         {
/* 1155:1547 */           newlines++;
/* 1156:1548 */           if (this.fCurrentEntity.offset == this.fCurrentEntity.length)
/* 1157:     */           {
/* 1158:1549 */             offset = 0;
/* 1159:1550 */             this.fCurrentEntity.offset = newlines;
/* 1160:1551 */             if (this.fCurrentEntity.load(newlines) == -1) {
/* 1161:     */               break;
/* 1162:     */             }
/* 1163:     */           }
/* 1164:1555 */           if (this.fCurrentEntity.getCurrentChar() == '\n')
/* 1165:     */           {
/* 1166:1556 */             this.fCurrentEntity.offset += 1;
/* 1167:1557 */             this.fCurrentEntity.characterOffset_ += 1;
/* 1168:1558 */             offset++;
/* 1169:     */           }
/* 1170:     */         }
/* 1171:1561 */         else if (c == '\n')
/* 1172:     */         {
/* 1173:1562 */           newlines++;
/* 1174:1563 */           if (this.fCurrentEntity.offset == this.fCurrentEntity.length)
/* 1175:     */           {
/* 1176:1564 */             offset = 0;
/* 1177:1565 */             this.fCurrentEntity.offset = newlines;
/* 1178:1566 */             if (this.fCurrentEntity.load(newlines) == -1) {
/* 1179:     */               break;
/* 1180:     */             }
/* 1181:     */           }
/* 1182:     */         }
/* 1183:     */         else
/* 1184:     */         {
/* 1185:1572 */           this.fCurrentEntity.rewind();
/* 1186:1573 */           break;
/* 1187:     */         }
/* 1188:1575 */       } while (this.fCurrentEntity.offset < this.fCurrentEntity.length - 1);
/* 1189:1576 */       this.fCurrentEntity.incLine(newlines);
/* 1190:     */     }
/* 1191:1578 */     this.fCurrentEntity.debugBufferIfNeeded(")skipNewlines: ", " -> " + newlines);
/* 1192:1579 */     return newlines;
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   protected final Augmentations locationAugs()
/* 1196:     */   {
/* 1197:1586 */     HTMLAugmentations augs = null;
/* 1198:1587 */     if (this.fAugmentations)
/* 1199:     */     {
/* 1200:1588 */       this.fLocationItem.setValues(this.fBeginLineNumber, this.fBeginColumnNumber, this.fBeginCharacterOffset, this.fEndLineNumber, this.fEndColumnNumber, this.fEndCharacterOffset);
/* 1201:     */       
/* 1202:     */ 
/* 1203:1591 */       augs = this.fInfosetAugs;
/* 1204:1592 */       augs.removeAllItems();
/* 1205:1593 */       augs.putItem("http://cyberneko.org/html/features/augmentations", this.fLocationItem);
/* 1206:     */     }
/* 1207:1595 */     return augs;
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   protected final Augmentations synthesizedAugs()
/* 1211:     */   {
/* 1212:1600 */     HTMLAugmentations augs = null;
/* 1213:1601 */     if (this.fAugmentations)
/* 1214:     */     {
/* 1215:1602 */       augs = this.fInfosetAugs;
/* 1216:1603 */       augs.removeAllItems();
/* 1217:1604 */       augs.putItem("http://cyberneko.org/html/features/augmentations", SYNTHESIZED_ITEM);
/* 1218:     */     }
/* 1219:1606 */     return augs;
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   protected final XMLResourceIdentifier resourceId()
/* 1223:     */   {
/* 1224:1612 */     this.fResourceId.clear();
/* 1225:1613 */     return this.fResourceId;
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   protected static boolean builtinXmlRef(String name)
/* 1229:     */   {
/* 1230:1628 */     return (name.equals("amp")) || (name.equals("lt")) || (name.equals("gt")) || (name.equals("quot")) || (name.equals("apos"));
/* 1231:     */   }
/* 1232:     */   
/* 1233:     */   public static abstract interface Scanner
/* 1234:     */   {
/* 1235:     */     public abstract boolean scan(boolean paramBoolean)
/* 1236:     */       throws IOException;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public static class CurrentEntity
/* 1240:     */   {
/* 1241:     */     private Reader stream_;
/* 1242:     */     private String encoding;
/* 1243:     */     public final String publicId;
/* 1244:     */     public final String baseSystemId;
/* 1245:     */     public final String literalSystemId;
/* 1246:     */     public final String expandedSystemId;
/* 1247:1700 */     public final String version = "1.0";
/* 1248:1703 */     private int lineNumber_ = 1;
/* 1249:1706 */     private int columnNumber_ = 1;
/* 1250:1709 */     public int characterOffset_ = 0;
/* 1251:1714 */     public char[] buffer = new char[2048];
/* 1252:1717 */     public int offset = 0;
/* 1253:1720 */     public int length = 0;
/* 1254:1722 */     private boolean endReached_ = false;
/* 1255:     */     
/* 1256:     */     public CurrentEntity(Reader stream, String encoding, String publicId, String baseSystemId, String literalSystemId, String expandedSystemId)
/* 1257:     */     {
/* 1258:1732 */       this.stream_ = stream;
/* 1259:1733 */       this.encoding = encoding;
/* 1260:1734 */       this.publicId = publicId;
/* 1261:1735 */       this.baseSystemId = baseSystemId;
/* 1262:1736 */       this.literalSystemId = literalSystemId;
/* 1263:1737 */       this.expandedSystemId = expandedSystemId;
/* 1264:     */     }
/* 1265:     */     
/* 1266:     */     private char getCurrentChar()
/* 1267:     */     {
/* 1268:1741 */       return this.buffer[this.offset];
/* 1269:     */     }
/* 1270:     */     
/* 1271:     */     private char getNextChar()
/* 1272:     */     {
/* 1273:1749 */       this.characterOffset_ += 1;
/* 1274:1750 */       this.columnNumber_ += 1;
/* 1275:1751 */       return this.buffer[(this.offset++)];
/* 1276:     */     }
/* 1277:     */     
/* 1278:     */     private void closeQuietly()
/* 1279:     */     {
/* 1280:     */       try
/* 1281:     */       {
/* 1282:1755 */         this.stream_.close();
/* 1283:     */       }
/* 1284:     */       catch (IOException e) {}
/* 1285:     */     }
/* 1286:     */     
/* 1287:     */     boolean hasNext()
/* 1288:     */     {
/* 1289:1766 */       return this.offset < this.length;
/* 1290:     */     }
/* 1291:     */     
/* 1292:     */     protected int load(int offset)
/* 1293:     */       throws IOException
/* 1294:     */     {
/* 1295:1776 */       debugBufferIfNeeded("(load: ");
/* 1296:1778 */       if (offset == this.buffer.length)
/* 1297:     */       {
/* 1298:1779 */         int adjust = this.buffer.length / 4;
/* 1299:1780 */         char[] array = new char[this.buffer.length + adjust];
/* 1300:1781 */         System.arraycopy(this.buffer, 0, array, 0, this.length);
/* 1301:1782 */         this.buffer = array;
/* 1302:     */       }
/* 1303:1785 */       int count = this.stream_.read(this.buffer, offset, this.buffer.length - offset);
/* 1304:1786 */       if (count == -1) {
/* 1305:1787 */         this.endReached_ = true;
/* 1306:     */       }
/* 1307:1789 */       this.length = (count != -1 ? count + offset : offset);
/* 1308:1790 */       this.offset = offset;
/* 1309:1791 */       debugBufferIfNeeded(")load: ", " -> " + count);
/* 1310:1792 */       return count;
/* 1311:     */     }
/* 1312:     */     
/* 1313:     */     protected int read()
/* 1314:     */       throws IOException
/* 1315:     */     {
/* 1316:1797 */       debugBufferIfNeeded("(read: ");
/* 1317:1798 */       if (this.offset == this.length)
/* 1318:     */       {
/* 1319:1799 */         if (this.endReached_) {
/* 1320:1800 */           return -1;
/* 1321:     */         }
/* 1322:1802 */         if (load(0) == -1) {
/* 1323:1806 */           return -1;
/* 1324:     */         }
/* 1325:     */       }
/* 1326:1809 */       char c = this.buffer[(this.offset++)];
/* 1327:1810 */       this.characterOffset_ += 1;
/* 1328:1811 */       this.columnNumber_ += 1;
/* 1329:     */       
/* 1330:1813 */       debugBufferIfNeeded(")read: ", " -> " + c);
/* 1331:1814 */       return c;
/* 1332:     */     }
/* 1333:     */     
/* 1334:     */     private void debugBufferIfNeeded(String prefix)
/* 1335:     */     {
/* 1336:1819 */       debugBufferIfNeeded(prefix, "");
/* 1337:     */     }
/* 1338:     */     
/* 1339:     */     private void debugBufferIfNeeded(String prefix, String suffix) {}
/* 1340:     */     
/* 1341:     */     private void setStream(InputStreamReader inputStreamReader)
/* 1342:     */     {
/* 1343:1870 */       this.stream_ = inputStreamReader;
/* 1344:1871 */       this.offset = (this.length = this.characterOffset_ = 0);
/* 1345:1872 */       this.lineNumber_ = (this.columnNumber_ = 1);
/* 1346:1873 */       this.encoding = inputStreamReader.getEncoding();
/* 1347:     */     }
/* 1348:     */     
/* 1349:     */     private void rewind()
/* 1350:     */     {
/* 1351:1880 */       this.offset -= 1;
/* 1352:1881 */       this.characterOffset_ -= 1;
/* 1353:1882 */       this.columnNumber_ -= 1;
/* 1354:     */     }
/* 1355:     */     
/* 1356:     */     private void rewind(int i)
/* 1357:     */     {
/* 1358:1885 */       this.offset -= i;
/* 1359:1886 */       this.characterOffset_ -= i;
/* 1360:1887 */       this.columnNumber_ -= i;
/* 1361:     */     }
/* 1362:     */     
/* 1363:     */     private void incLine()
/* 1364:     */     {
/* 1365:1891 */       this.lineNumber_ += 1;
/* 1366:1892 */       this.columnNumber_ = 1;
/* 1367:     */     }
/* 1368:     */     
/* 1369:     */     private void incLine(int nbLines)
/* 1370:     */     {
/* 1371:1896 */       this.lineNumber_ += nbLines;
/* 1372:1897 */       this.columnNumber_ = 1;
/* 1373:     */     }
/* 1374:     */     
/* 1375:     */     public int getLineNumber()
/* 1376:     */     {
/* 1377:1901 */       return this.lineNumber_;
/* 1378:     */     }
/* 1379:     */     
/* 1380:     */     private void resetBuffer(XMLStringBuffer buffer, int lineNumber, int columnNumber, int characterOffset)
/* 1381:     */     {
/* 1382:1906 */       this.lineNumber_ = lineNumber;
/* 1383:1907 */       this.columnNumber_ = columnNumber;
/* 1384:1908 */       this.characterOffset_ = characterOffset;
/* 1385:1909 */       this.buffer = buffer.ch;
/* 1386:1910 */       this.offset = buffer.offset;
/* 1387:1911 */       this.length = buffer.length;
/* 1388:     */     }
/* 1389:     */     
/* 1390:     */     private int getColumnNumber()
/* 1391:     */     {
/* 1392:1915 */       return this.columnNumber_;
/* 1393:     */     }
/* 1394:     */     
/* 1395:     */     private void restorePosition(int originalOffset, int originalColumnNumber, int originalCharacterOffset)
/* 1396:     */     {
/* 1397:1920 */       this.offset = originalOffset;
/* 1398:1921 */       this.columnNumber_ = originalColumnNumber;
/* 1399:1922 */       this.characterOffset_ = originalCharacterOffset;
/* 1400:     */     }
/* 1401:     */     
/* 1402:     */     private int getCharacterOffset()
/* 1403:     */     {
/* 1404:1926 */       return this.characterOffset_;
/* 1405:     */     }
/* 1406:     */   }
/* 1407:     */   
/* 1408:     */   public class ContentScanner
/* 1409:     */     implements HTMLScanner.Scanner
/* 1410:     */   {
/* 1411:1945 */     private final QName fQName = new QName();
/* 1412:1948 */     private final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
/* 1413:     */     
/* 1414:     */     public ContentScanner() {}
/* 1415:     */     
/* 1416:     */     public boolean scan(boolean complete)
/* 1417:     */       throws IOException
/* 1418:     */     {
/* 1419:     */       boolean next;
/* 1420:     */       do
/* 1421:     */       {
/* 1422:     */         try
/* 1423:     */         {
/* 1424:1959 */           next = false;
/* 1425:1960 */           switch (HTMLScanner.this.fScannerState)
/* 1426:     */           {
/* 1427:     */           case 0: 
/* 1428:1962 */             HTMLScanner.this.fBeginLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1429:1963 */             HTMLScanner.this.fBeginColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1430:1964 */             HTMLScanner.this.fBeginCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1431:1965 */             int c = HTMLScanner.this.fCurrentEntity.read();
/* 1432:1966 */             if (c == 60)
/* 1433:     */             {
/* 1434:1967 */               HTMLScanner.this.setScannerState((short)1);
/* 1435:1968 */               next = true;
/* 1436:     */             }
/* 1437:1970 */             else if (c == 38)
/* 1438:     */             {
/* 1439:1971 */               HTMLScanner.this.scanEntityRef(HTMLScanner.this.fStringBuffer, true);
/* 1440:     */             }
/* 1441:     */             else
/* 1442:     */             {
/* 1443:1973 */               if (c == -1) {
/* 1444:1974 */                 throw new EOFException();
/* 1445:     */               }
/* 1446:1977 */               HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1447:1978 */               scanCharacters();
/* 1448:     */             }
/* 1449:1980 */             break;
/* 1450:     */           case 1: 
/* 1451:1983 */             int c = HTMLScanner.this.fCurrentEntity.read();
/* 1452:1984 */             if (c == 33)
/* 1453:     */             {
/* 1454:1985 */               if (HTMLScanner.this.skip("--", false))
/* 1455:     */               {
/* 1456:1986 */                 scanComment();
/* 1457:     */               }
/* 1458:1988 */               else if (HTMLScanner.this.skip("[CDATA[", false))
/* 1459:     */               {
/* 1460:1989 */                 scanCDATA();
/* 1461:     */               }
/* 1462:1991 */               else if (HTMLScanner.this.skip("DOCTYPE", false))
/* 1463:     */               {
/* 1464:1992 */                 HTMLScanner.this.scanDoctype();
/* 1465:     */               }
/* 1466:     */               else
/* 1467:     */               {
/* 1468:1995 */                 if (HTMLScanner.this.fReportErrors) {
/* 1469:1996 */                   HTMLScanner.this.fErrorReporter.reportError("HTML1002", null);
/* 1470:     */                 }
/* 1471:1998 */                 HTMLScanner.this.skipMarkup(true);
/* 1472:     */               }
/* 1473:     */             }
/* 1474:2001 */             else if (c == 63)
/* 1475:     */             {
/* 1476:2002 */               scanPI();
/* 1477:     */             }
/* 1478:2004 */             else if (c == 47)
/* 1479:     */             {
/* 1480:2005 */               scanEndElement();
/* 1481:     */             }
/* 1482:     */             else
/* 1483:     */             {
/* 1484:2007 */               if (c == -1)
/* 1485:     */               {
/* 1486:2008 */                 if (HTMLScanner.this.fReportErrors) {
/* 1487:2009 */                   HTMLScanner.this.fErrorReporter.reportError("HTML1003", null);
/* 1488:     */                 }
/* 1489:2011 */                 if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 1490:     */                 {
/* 1491:2012 */                   HTMLScanner.this.fStringBuffer.clear();
/* 1492:2013 */                   HTMLScanner.this.fStringBuffer.append('<');
/* 1493:2014 */                   HTMLScanner.this.fDocumentHandler.characters(HTMLScanner.this.fStringBuffer, null);
/* 1494:     */                 }
/* 1495:2016 */                 throw new EOFException();
/* 1496:     */               }
/* 1497:2019 */               HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1498:2020 */               HTMLScanner.this.fElementCount += 1;
/* 1499:2021 */               HTMLScanner.this.fSingleBoolean[0] = 0;
/* 1500:2022 */               String ename = scanStartElement(HTMLScanner.this.fSingleBoolean);
/* 1501:2023 */               String enameLC = ename == null ? null : ename.toLowerCase();
/* 1502:2024 */               HTMLScanner.this.fBeginLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1503:2025 */               HTMLScanner.this.fBeginColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1504:2026 */               HTMLScanner.this.fBeginCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1505:2027 */               if ("script".equals(enameLC))
/* 1506:     */               {
/* 1507:2028 */                 scanScriptContent();
/* 1508:     */               }
/* 1509:2030 */               else if ((!HTMLScanner.this.fAllowSelfclosingIframe) && ("iframe".equals(enameLC)))
/* 1510:     */               {
/* 1511:2031 */                 scanUntilEndTag("iframe");
/* 1512:     */               }
/* 1513:2033 */               else if ((!HTMLScanner.this.fParseNoScriptContent) && ("noscript".equals(enameLC)))
/* 1514:     */               {
/* 1515:2034 */                 scanUntilEndTag("noscript");
/* 1516:     */               }
/* 1517:2036 */               else if ((!HTMLScanner.this.fParseNoFramesContent) && ("noframes".equals(enameLC)))
/* 1518:     */               {
/* 1519:2037 */                 scanUntilEndTag("noframes");
/* 1520:     */               }
/* 1521:2039 */               else if ((ename != null) && (HTMLScanner.this.fSingleBoolean[0] == 0) && (HTMLElements.getElement(enameLC).isSpecial()) && ((!ename.equalsIgnoreCase("TITLE")) || (isEnded(enameLC))))
/* 1522:     */               {
/* 1523:2042 */                 HTMLScanner.this.setScanner(HTMLScanner.this.fSpecialScanner.setElementName(ename));
/* 1524:2043 */                 HTMLScanner.this.setScannerState((short)0);
/* 1525:2044 */                 return true;
/* 1526:     */               }
/* 1527:     */             }
/* 1528:2047 */             HTMLScanner.this.setScannerState((short)0);
/* 1529:2048 */             break;
/* 1530:     */           case 10: 
/* 1531:2051 */             if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 1532:     */             {
/* 1533:2055 */               XMLLocator locator = HTMLScanner.this;
/* 1534:2056 */               String encoding = HTMLScanner.this.fIANAEncoding;
/* 1535:2057 */               Augmentations augs = HTMLScanner.this.locationAugs();
/* 1536:2058 */               NamespaceContext nscontext = new NamespaceSupport();
/* 1537:2059 */               XercesBridge.getInstance().XMLDocumentHandler_startDocument(HTMLScanner.this.fDocumentHandler, locator, encoding, nscontext, augs);
/* 1538:     */             }
/* 1539:2061 */             if ((HTMLScanner.this.fInsertDoctype) && (HTMLScanner.this.fDocumentHandler != null))
/* 1540:     */             {
/* 1541:2062 */               String root = HTMLElements.getElement((short)46).name;
/* 1542:2063 */               root = HTMLScanner.modifyName(root, HTMLScanner.this.fNamesElems);
/* 1543:2064 */               String pubid = HTMLScanner.this.fDoctypePubid;
/* 1544:2065 */               String sysid = HTMLScanner.this.fDoctypeSysid;
/* 1545:2066 */               HTMLScanner.this.fDocumentHandler.doctypeDecl(root, pubid, sysid, HTMLScanner.this.synthesizedAugs());
/* 1546:     */             }
/* 1547:2069 */             HTMLScanner.this.setScannerState((short)0);
/* 1548:2070 */             break;
/* 1549:     */           case 11: 
/* 1550:2073 */             if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth) && (complete))
/* 1551:     */             {
/* 1552:2077 */               HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1553:2078 */               HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1554:2079 */               HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1555:2080 */               HTMLScanner.this.fDocumentHandler.endDocument(HTMLScanner.this.locationAugs());
/* 1556:     */             }
/* 1557:2082 */             return false;
/* 1558:     */           default: 
/* 1559:2085 */             throw new RuntimeException("unknown scanner state: " + HTMLScanner.this.fScannerState);
/* 1560:     */           }
/* 1561:     */         }
/* 1562:     */         catch (EOFException e)
/* 1563:     */         {
/* 1564:2090 */           if (HTMLScanner.this.fCurrentEntityStack.empty()) {
/* 1565:2091 */             HTMLScanner.this.setScannerState((short)11);
/* 1566:     */           } else {
/* 1567:2094 */             HTMLScanner.this.fCurrentEntity = ((HTMLScanner.CurrentEntity)HTMLScanner.this.fCurrentEntityStack.pop());
/* 1568:     */           }
/* 1569:2096 */           next = true;
/* 1570:     */         }
/* 1571:2098 */       } while ((next) || (complete));
/* 1572:2099 */       return true;
/* 1573:     */     }
/* 1574:     */     
/* 1575:     */     private void scanUntilEndTag(String tagName)
/* 1576:     */       throws IOException
/* 1577:     */     {
/* 1578:2109 */       XMLStringBuffer buffer = new XMLStringBuffer();
/* 1579:2110 */       String end = "/" + tagName;
/* 1580:2111 */       int lengthToScan = tagName.length() + 2;
/* 1581:     */       for (;;)
/* 1582:     */       {
/* 1583:2114 */         int c = HTMLScanner.this.fCurrentEntity.read();
/* 1584:2115 */         if (c == -1) {
/* 1585:     */           break;
/* 1586:     */         }
/* 1587:2118 */         if (c == 60)
/* 1588:     */         {
/* 1589:2119 */           String next = nextContent(lengthToScan) + " ";
/* 1590:2120 */           if ((next.length() >= lengthToScan) && (end.equalsIgnoreCase(next.substring(0, end.length()))) && (('>' == next.charAt(lengthToScan - 1)) || (Character.isWhitespace(next.charAt(lengthToScan - 1)))))
/* 1591:     */           {
/* 1592:2122 */             HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1593:2123 */             break;
/* 1594:     */           }
/* 1595:     */         }
/* 1596:2126 */         if ((c == 13) || (c == 10))
/* 1597:     */         {
/* 1598:2127 */           HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1599:2128 */           int newlines = HTMLScanner.this.skipNewlines();
/* 1600:2129 */           for (int i = 0; i < newlines; i++) {
/* 1601:2130 */             buffer.append('\n');
/* 1602:     */           }
/* 1603:     */         }
/* 1604:     */         else
/* 1605:     */         {
/* 1606:2134 */           buffer.append((char)c);
/* 1607:     */         }
/* 1608:     */       }
/* 1609:2137 */       if ((buffer.length > 0) && (HTMLScanner.this.fDocumentHandler != null))
/* 1610:     */       {
/* 1611:2138 */         HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1612:2139 */         HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1613:2140 */         HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1614:2141 */         HTMLScanner.this.fDocumentHandler.characters(buffer, HTMLScanner.this.locationAugs());
/* 1615:     */       }
/* 1616:     */     }
/* 1617:     */     
/* 1618:     */     private void scanScriptContent()
/* 1619:     */       throws IOException
/* 1620:     */     {
/* 1621:2147 */       XMLStringBuffer buffer = new XMLStringBuffer();
/* 1622:2148 */       boolean waitForEndComment = false;
/* 1623:     */       for (;;)
/* 1624:     */       {
/* 1625:2150 */         int c = HTMLScanner.this.fCurrentEntity.read();
/* 1626:2151 */         if (c == -1) {
/* 1627:     */           break;
/* 1628:     */         }
/* 1629:2154 */         if ((c == 45) && (HTMLScanner.this.endsWith(buffer, "<!-")))
/* 1630:     */         {
/* 1631:2156 */           waitForEndComment = HTMLScanner.this.endCommentAvailable();
/* 1632:     */         }
/* 1633:2158 */         else if ((!waitForEndComment) && (c == 60))
/* 1634:     */         {
/* 1635:2159 */           String next = nextContent(8) + " ";
/* 1636:2160 */           if ((next.length() >= 8) && ("/script".equalsIgnoreCase(next.substring(0, 7))) && (('>' == next.charAt(7)) || (Character.isWhitespace(next.charAt(7)))))
/* 1637:     */           {
/* 1638:2162 */             HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1639:2163 */             break;
/* 1640:     */           }
/* 1641:     */         }
/* 1642:2166 */         else if ((c == 62) && (HTMLScanner.this.endsWith(buffer, "--")))
/* 1643:     */         {
/* 1644:2167 */           waitForEndComment = false;
/* 1645:     */         }
/* 1646:2170 */         if ((c == 13) || (c == 10))
/* 1647:     */         {
/* 1648:2171 */           HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1649:2172 */           int newlines = HTMLScanner.this.skipNewlines();
/* 1650:2173 */           for (int i = 0; i < newlines; i++) {
/* 1651:2174 */             buffer.append('\n');
/* 1652:     */           }
/* 1653:     */         }
/* 1654:     */         else
/* 1655:     */         {
/* 1656:2178 */           buffer.append((char)c);
/* 1657:     */         }
/* 1658:     */       }
/* 1659:2182 */       if (HTMLScanner.this.fScriptStripCommentDelims) {
/* 1660:2183 */         HTMLScanner.reduceToContent(buffer, "<!--", "-->");
/* 1661:     */       }
/* 1662:2185 */       if (HTMLScanner.this.fScriptStripCDATADelims) {
/* 1663:2186 */         HTMLScanner.reduceToContent(buffer, "<![CDATA[", "]]>");
/* 1664:     */       }
/* 1665:2189 */       if ((buffer.length > 0) && (HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 1666:     */       {
/* 1667:2193 */         HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1668:2194 */         HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1669:2195 */         HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1670:2196 */         HTMLScanner.this.fDocumentHandler.characters(buffer, HTMLScanner.this.locationAugs());
/* 1671:     */       }
/* 1672:     */     }
/* 1673:     */     
/* 1674:     */     protected String nextContent(int len)
/* 1675:     */       throws IOException
/* 1676:     */     {
/* 1677:2208 */       int originalOffset = HTMLScanner.this.fCurrentEntity.offset;
/* 1678:2209 */       int originalColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1679:2210 */       int originalCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1680:     */       
/* 1681:2212 */       char[] buff = new char[len];
/* 1682:2213 */       int nbRead = 0;
/* 1683:2214 */       for (nbRead = 0; nbRead < len; nbRead++)
/* 1684:     */       {
/* 1685:2216 */         if (HTMLScanner.this.fCurrentEntity.offset == HTMLScanner.this.fCurrentEntity.length)
/* 1686:     */         {
/* 1687:2217 */           if (HTMLScanner.this.fCurrentEntity.length != HTMLScanner.this.fCurrentEntity.buffer.length) {
/* 1688:     */             break;
/* 1689:     */           }
/* 1690:2218 */           HTMLScanner.this.fCurrentEntity.load(HTMLScanner.this.fCurrentEntity.buffer.length);
/* 1691:     */         }
/* 1692:2225 */         int c = HTMLScanner.this.fCurrentEntity.read();
/* 1693:2226 */         if (c == -1) {
/* 1694:     */           break;
/* 1695:     */         }
/* 1696:2230 */         buff[nbRead] = ((char)c);
/* 1697:     */       }
/* 1698:2233 */       HTMLScanner.CurrentEntity.access$1400(HTMLScanner.this.fCurrentEntity, originalOffset, originalColumnNumber, originalCharacterOffset);
/* 1699:2234 */       return new String(buff, 0, nbRead);
/* 1700:     */     }
/* 1701:     */     
/* 1702:     */     protected void scanCharacters()
/* 1703:     */       throws IOException
/* 1704:     */     {
/* 1705:2243 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, "(scanCharacters: ");
/* 1706:2244 */       HTMLScanner.this.fStringBuffer.clear();
/* 1707:     */       for (;;)
/* 1708:     */       {
/* 1709:2246 */         int newlines = HTMLScanner.this.skipNewlines();
/* 1710:2247 */         if ((newlines == 0) && (HTMLScanner.this.fCurrentEntity.offset == HTMLScanner.this.fCurrentEntity.length))
/* 1711:     */         {
/* 1712:2248 */           HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, ")scanCharacters: ");
/* 1713:     */         }
/* 1714:     */         else
/* 1715:     */         {
/* 1716:2252 */           int offset = HTMLScanner.this.fCurrentEntity.offset - newlines;
/* 1717:2253 */           for (int i = offset; i < HTMLScanner.this.fCurrentEntity.offset; i++) {
/* 1718:2254 */             HTMLScanner.this.fCurrentEntity.buffer[i] = '\n';
/* 1719:     */           }
/* 1720:2256 */           while (HTMLScanner.this.fCurrentEntity.hasNext())
/* 1721:     */           {
/* 1722:2257 */             char c = HTMLScanner.CurrentEntity.access$600(HTMLScanner.this.fCurrentEntity);
/* 1723:2258 */             if ((c == '<') || (c == '&') || (c == '\n') || (c == '\r')) {
/* 1724:2259 */               HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1725:     */             }
/* 1726:     */           }
/* 1727:2263 */           if ((HTMLScanner.this.fCurrentEntity.offset > offset) && (HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 1728:     */           {
/* 1729:2269 */             HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1730:2270 */             HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1731:2271 */             HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1732:2272 */             HTMLScanner.this.fStringBuffer.append(HTMLScanner.this.fCurrentEntity.buffer, offset, HTMLScanner.this.fCurrentEntity.offset - offset);
/* 1733:     */           }
/* 1734:2274 */           HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, ")scanCharacters: ");
/* 1735:     */           
/* 1736:2276 */           boolean hasNext = HTMLScanner.this.fCurrentEntity.offset < HTMLScanner.this.fCurrentEntity.buffer.length;
/* 1737:2277 */           int next = hasNext ? HTMLScanner.CurrentEntity.access$900(HTMLScanner.this.fCurrentEntity) : -1;
/* 1738:2279 */           if ((next == 38) || (next == 60) || (next == -1)) {
/* 1739:     */             break;
/* 1740:     */           }
/* 1741:     */         }
/* 1742:     */       }
/* 1743:2285 */       if (HTMLScanner.this.fStringBuffer.length != 0) {
/* 1744:2286 */         HTMLScanner.this.fDocumentHandler.characters(HTMLScanner.this.fStringBuffer, HTMLScanner.this.locationAugs());
/* 1745:     */       }
/* 1746:     */     }
/* 1747:     */     
/* 1748:     */     protected void scanCDATA()
/* 1749:     */       throws IOException
/* 1750:     */     {
/* 1751:2293 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, "(scanCDATA: ");
/* 1752:2294 */       HTMLScanner.this.fStringBuffer.clear();
/* 1753:2295 */       if (HTMLScanner.this.fCDATASections)
/* 1754:     */       {
/* 1755:2296 */         if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 1756:     */         {
/* 1757:2297 */           HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1758:2298 */           HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1759:2299 */           HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1760:     */           
/* 1761:     */ 
/* 1762:     */ 
/* 1763:2303 */           HTMLScanner.this.fDocumentHandler.startCDATA(HTMLScanner.this.locationAugs());
/* 1764:     */         }
/* 1765:     */       }
/* 1766:     */       else {
/* 1767:2307 */         HTMLScanner.this.fStringBuffer.append("[CDATA[");
/* 1768:     */       }
/* 1769:2309 */       boolean eof = scanMarkupContent(HTMLScanner.this.fStringBuffer, ']');
/* 1770:2310 */       if (!HTMLScanner.this.fCDATASections) {
/* 1771:2311 */         HTMLScanner.this.fStringBuffer.append("]]");
/* 1772:     */       }
/* 1773:2313 */       if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 1774:     */       {
/* 1775:2314 */         HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1776:2315 */         HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1777:2316 */         HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1778:2317 */         if (HTMLScanner.this.fCDATASections)
/* 1779:     */         {
/* 1780:2321 */           HTMLScanner.this.fDocumentHandler.characters(HTMLScanner.this.fStringBuffer, HTMLScanner.this.locationAugs());
/* 1781:     */           
/* 1782:     */ 
/* 1783:     */ 
/* 1784:2325 */           HTMLScanner.this.fDocumentHandler.endCDATA(HTMLScanner.this.locationAugs());
/* 1785:     */         }
/* 1786:     */         else
/* 1787:     */         {
/* 1788:2331 */           HTMLScanner.this.fDocumentHandler.comment(HTMLScanner.this.fStringBuffer, HTMLScanner.this.locationAugs());
/* 1789:     */         }
/* 1790:     */       }
/* 1791:2334 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, ")scanCDATA: ");
/* 1792:2335 */       if (eof) {
/* 1793:2336 */         throw new EOFException();
/* 1794:     */       }
/* 1795:     */     }
/* 1796:     */     
/* 1797:     */     protected void scanComment()
/* 1798:     */       throws IOException
/* 1799:     */     {
/* 1800:2342 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, "(scanComment: ");
/* 1801:2343 */       HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1802:2344 */       HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1803:2345 */       HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1804:2346 */       XMLStringBuffer buffer = new XMLStringBuffer();
/* 1805:2347 */       boolean eof = scanMarkupContent(buffer, '-');
/* 1806:2349 */       if (eof)
/* 1807:     */       {
/* 1808:2350 */         HTMLScanner.CurrentEntity.access$1500(HTMLScanner.this.fCurrentEntity, buffer, HTMLScanner.this.fEndLineNumber, HTMLScanner.this.fEndColumnNumber, HTMLScanner.this.fEndCharacterOffset);
/* 1809:2351 */         buffer = new XMLStringBuffer();
/* 1810:     */         for (;;)
/* 1811:     */         {
/* 1812:2353 */           int c = HTMLScanner.this.fCurrentEntity.read();
/* 1813:2354 */           if (c == -1)
/* 1814:     */           {
/* 1815:2355 */             if (HTMLScanner.this.fReportErrors) {
/* 1816:2356 */               HTMLScanner.this.fErrorReporter.reportError("HTML1007", null);
/* 1817:     */             }
/* 1818:2358 */             eof = true;
/* 1819:     */             break label245;
/* 1820:     */           }
/* 1821:2361 */           if (c != 62)
/* 1822:     */           {
/* 1823:2362 */             buffer.append((char)c);
/* 1824:     */           }
/* 1825:     */           else
/* 1826:     */           {
/* 1827:2365 */             if ((c != 10) && (c != 13)) {
/* 1828:     */               break;
/* 1829:     */             }
/* 1830:2366 */             HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1831:2367 */             int newlines = HTMLScanner.this.skipNewlines();
/* 1832:2368 */             for (int i = 0; i < newlines; i++) {
/* 1833:2369 */               buffer.append('\n');
/* 1834:     */             }
/* 1835:     */           }
/* 1836:     */         }
/* 1837:2373 */         eof = false;
/* 1838:     */       }
/* 1839:     */       label245:
/* 1840:2377 */       if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 1841:     */       {
/* 1842:2381 */         HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 1843:2382 */         HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 1844:2383 */         HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 1845:2384 */         HTMLScanner.this.fDocumentHandler.comment(buffer, HTMLScanner.this.locationAugs());
/* 1846:     */       }
/* 1847:2386 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, ")scanComment: ");
/* 1848:2387 */       if (eof) {
/* 1849:2388 */         throw new EOFException();
/* 1850:     */       }
/* 1851:     */     }
/* 1852:     */     
/* 1853:     */     protected boolean scanMarkupContent(XMLStringBuffer buffer, char cend)
/* 1854:     */       throws IOException
/* 1855:     */     {
/* 1856:2395 */       int c = -1;
/* 1857:     */       for (;;)
/* 1858:     */       {
/* 1859:2397 */         c = HTMLScanner.this.fCurrentEntity.read();
/* 1860:2398 */         if (c == cend)
/* 1861:     */         {
/* 1862:2399 */           int count = 1;
/* 1863:     */           for (;;)
/* 1864:     */           {
/* 1865:2401 */             c = HTMLScanner.this.fCurrentEntity.read();
/* 1866:2402 */             if (c != cend) {
/* 1867:     */               break;
/* 1868:     */             }
/* 1869:2403 */             count++;
/* 1870:     */           }
/* 1871:2408 */           if (c == -1)
/* 1872:     */           {
/* 1873:2409 */             if (!HTMLScanner.this.fReportErrors) {
/* 1874:     */               break;
/* 1875:     */             }
/* 1876:2410 */             HTMLScanner.this.fErrorReporter.reportError("HTML1007", null); break;
/* 1877:     */           }
/* 1878:2414 */           if (count < 2)
/* 1879:     */           {
/* 1880:2415 */             buffer.append(cend);
/* 1881:     */             
/* 1882:2417 */             HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1883:     */           }
/* 1884:2421 */           else if (c != 62)
/* 1885:     */           {
/* 1886:2422 */             for (int i = 0; i < count; i++) {
/* 1887:2423 */               buffer.append(cend);
/* 1888:     */             }
/* 1889:2425 */             HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1890:     */           }
/* 1891:     */           else
/* 1892:     */           {
/* 1893:2428 */             for (int i = 0; i < count - 2; i++) {
/* 1894:2429 */               buffer.append(cend);
/* 1895:     */             }
/* 1896:2431 */             break;
/* 1897:     */           }
/* 1898:     */         }
/* 1899:2433 */         else if ((c == 10) || (c == 13))
/* 1900:     */         {
/* 1901:2434 */           HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1902:2435 */           int newlines = HTMLScanner.this.skipNewlines();
/* 1903:2436 */           for (int i = 0; i < newlines; i++) {
/* 1904:2437 */             buffer.append('\n');
/* 1905:     */           }
/* 1906:     */         }
/* 1907:     */         else
/* 1908:     */         {
/* 1909:2441 */           if (c == -1)
/* 1910:     */           {
/* 1911:2442 */             if (!HTMLScanner.this.fReportErrors) {
/* 1912:     */               break;
/* 1913:     */             }
/* 1914:2443 */             HTMLScanner.this.fErrorReporter.reportError("HTML1007", null); break;
/* 1915:     */           }
/* 1916:2447 */           buffer.append((char)c);
/* 1917:     */         }
/* 1918:     */       }
/* 1919:2449 */       return c == -1;
/* 1920:     */     }
/* 1921:     */     
/* 1922:     */     protected void scanPI()
/* 1923:     */       throws IOException
/* 1924:     */     {
/* 1925:2454 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, "(scanPI: ");
/* 1926:2455 */       if (HTMLScanner.this.fReportErrors) {
/* 1927:2456 */         HTMLScanner.this.fErrorReporter.reportWarning("HTML1008", null);
/* 1928:     */       }
/* 1929:2460 */       String target = HTMLScanner.this.scanName();
/* 1930:2461 */       if ((target != null) && (!target.equalsIgnoreCase("xml")))
/* 1931:     */       {
/* 1932:     */         for (;;)
/* 1933:     */         {
/* 1934:2463 */           int c = HTMLScanner.this.fCurrentEntity.read();
/* 1935:2464 */           if ((c == 13) || (c == 10))
/* 1936:     */           {
/* 1937:2465 */             if (c == 13)
/* 1938:     */             {
/* 1939:2466 */               c = HTMLScanner.this.fCurrentEntity.read();
/* 1940:2467 */               if (c != 10)
/* 1941:     */               {
/* 1942:2468 */                 HTMLScanner.this.fCurrentEntity.offset -= 1;
/* 1943:2469 */                 HTMLScanner.this.fCurrentEntity.characterOffset_ -= 1;
/* 1944:     */               }
/* 1945:     */             }
/* 1946:2472 */             HTMLScanner.CurrentEntity.access$1600(HTMLScanner.this.fCurrentEntity);
/* 1947:     */           }
/* 1948:     */           else
/* 1949:     */           {
/* 1950:2475 */             if (c == -1) {
/* 1951:     */               break;
/* 1952:     */             }
/* 1953:2478 */             if ((c != 32) && (c != 9))
/* 1954:     */             {
/* 1955:2479 */               HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1956:2480 */               break;
/* 1957:     */             }
/* 1958:     */           }
/* 1959:     */         }
/* 1960:2483 */         HTMLScanner.this.fStringBuffer.clear();
/* 1961:     */         for (;;)
/* 1962:     */         {
/* 1963:2485 */           int c = HTMLScanner.this.fCurrentEntity.read();
/* 1964:2486 */           if ((c == 63) || (c == 47))
/* 1965:     */           {
/* 1966:2487 */             char c0 = (char)c;
/* 1967:2488 */             c = HTMLScanner.this.fCurrentEntity.read();
/* 1968:2489 */             if (c == 62) {
/* 1969:     */               break;
/* 1970:     */             }
/* 1971:2493 */             HTMLScanner.this.fStringBuffer.append(c0);
/* 1972:2494 */             HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 1973:     */           }
/* 1974:2498 */           else if ((c == 13) || (c == 10))
/* 1975:     */           {
/* 1976:2499 */             HTMLScanner.this.fStringBuffer.append('\n');
/* 1977:2500 */             if (c == 13)
/* 1978:     */             {
/* 1979:2501 */               c = HTMLScanner.this.fCurrentEntity.read();
/* 1980:2502 */               if (c != 10)
/* 1981:     */               {
/* 1982:2503 */                 HTMLScanner.this.fCurrentEntity.offset -= 1;
/* 1983:2504 */                 HTMLScanner.this.fCurrentEntity.characterOffset_ -= 1;
/* 1984:     */               }
/* 1985:     */             }
/* 1986:2507 */             HTMLScanner.CurrentEntity.access$1600(HTMLScanner.this.fCurrentEntity);
/* 1987:     */           }
/* 1988:     */           else
/* 1989:     */           {
/* 1990:2510 */             if (c == -1) {
/* 1991:     */               break;
/* 1992:     */             }
/* 1993:2514 */             HTMLScanner.this.fStringBuffer.append((char)c);
/* 1994:     */           }
/* 1995:     */         }
/* 1996:2517 */         XMLString data = HTMLScanner.this.fStringBuffer;
/* 1997:2518 */         if (HTMLScanner.this.fDocumentHandler != null)
/* 1998:     */         {
/* 1999:2519 */           HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2000:2520 */           HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2001:2521 */           HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2002:2522 */           HTMLScanner.this.fDocumentHandler.processingInstruction(target, data, HTMLScanner.this.locationAugs());
/* 2003:     */         }
/* 2004:     */       }
/* 2005:     */       else
/* 2006:     */       {
/* 2007:2528 */         int beginLineNumber = HTMLScanner.this.fBeginLineNumber;
/* 2008:2529 */         int beginColumnNumber = HTMLScanner.this.fBeginColumnNumber;
/* 2009:2530 */         int beginCharacterOffset = HTMLScanner.this.fBeginCharacterOffset;
/* 2010:2531 */         this.fAttributes.removeAllAttributes();
/* 2011:2532 */         int aindex = 0;
/* 2012:2533 */         while (scanPseudoAttribute(this.fAttributes)) {
/* 2013:2535 */           if (this.fAttributes.getValue(aindex).length() == 0)
/* 2014:     */           {
/* 2015:2536 */             this.fAttributes.removeAttributeAt(aindex);
/* 2016:     */           }
/* 2017:     */           else
/* 2018:     */           {
/* 2019:2539 */             this.fAttributes.getName(aindex, this.fQName);
/* 2020:2540 */             this.fQName.rawname = this.fQName.rawname.toLowerCase();
/* 2021:2541 */             this.fAttributes.setName(aindex, this.fQName);
/* 2022:2542 */             aindex++;
/* 2023:     */           }
/* 2024:     */         }
/* 2025:2545 */         if (HTMLScanner.this.fDocumentHandler != null)
/* 2026:     */         {
/* 2027:2546 */           String version = this.fAttributes.getValue("version");
/* 2028:2547 */           String encoding = this.fAttributes.getValue("encoding");
/* 2029:2548 */           String standalone = this.fAttributes.getValue("standalone");
/* 2030:     */           
/* 2031:     */ 
/* 2032:     */ 
/* 2033:2552 */           boolean xmlDeclNow = (HTMLScanner.this.fIgnoreSpecifiedCharset) || (!changeEncoding(encoding));
/* 2034:2553 */           if (xmlDeclNow)
/* 2035:     */           {
/* 2036:2554 */             HTMLScanner.this.fBeginLineNumber = beginLineNumber;
/* 2037:2555 */             HTMLScanner.this.fBeginColumnNumber = beginColumnNumber;
/* 2038:2556 */             HTMLScanner.this.fBeginCharacterOffset = beginCharacterOffset;
/* 2039:2557 */             HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2040:2558 */             HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2041:2559 */             HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2042:2560 */             HTMLScanner.this.fDocumentHandler.xmlDecl(version, encoding, standalone, HTMLScanner.this.locationAugs());
/* 2043:     */           }
/* 2044:     */         }
/* 2045:     */       }
/* 2046:2566 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, ")scanPI: ");
/* 2047:     */     }
/* 2048:     */     
/* 2049:     */     protected String scanStartElement(boolean[] empty)
/* 2050:     */       throws IOException
/* 2051:     */     {
/* 2052:2576 */       String ename = HTMLScanner.this.scanName();
/* 2053:2577 */       int length = ename != null ? ename.length() : 0;
/* 2054:2578 */       int c = length > 0 ? ename.charAt(0) : -1;
/* 2055:2579 */       if ((length == 0) || (((c < 97) || (c > 122)) && ((c < 65) || (c > 90))))
/* 2056:     */       {
/* 2057:2580 */         if (HTMLScanner.this.fReportErrors) {
/* 2058:2581 */           HTMLScanner.this.fErrorReporter.reportError("HTML1009", null);
/* 2059:     */         }
/* 2060:2583 */         if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 2061:     */         {
/* 2062:2584 */           HTMLScanner.this.fStringBuffer.clear();
/* 2063:2585 */           HTMLScanner.this.fStringBuffer.append('<');
/* 2064:2586 */           if (length > 0) {
/* 2065:2587 */             HTMLScanner.this.fStringBuffer.append(ename);
/* 2066:     */           }
/* 2067:2589 */           HTMLScanner.this.fDocumentHandler.characters(HTMLScanner.this.fStringBuffer, null);
/* 2068:     */         }
/* 2069:2591 */         return null;
/* 2070:     */       }
/* 2071:2593 */       ename = HTMLScanner.modifyName(ename, HTMLScanner.this.fNamesElems);
/* 2072:2594 */       this.fAttributes.removeAllAttributes();
/* 2073:2595 */       int beginLineNumber = HTMLScanner.this.fBeginLineNumber;
/* 2074:2596 */       int beginColumnNumber = HTMLScanner.this.fBeginColumnNumber;
/* 2075:2597 */       int beginCharacterOffset = HTMLScanner.this.fBeginCharacterOffset;
/* 2076:2598 */       while (scanAttribute(this.fAttributes, empty)) {}
/* 2077:2601 */       HTMLScanner.this.fBeginLineNumber = beginLineNumber;
/* 2078:2602 */       HTMLScanner.this.fBeginColumnNumber = beginColumnNumber;
/* 2079:2603 */       HTMLScanner.this.fBeginCharacterOffset = beginCharacterOffset;
/* 2080:2604 */       if ((HTMLScanner.this.fByteStream != null) && (HTMLScanner.this.fElementDepth == -1)) {
/* 2081:2605 */         if (ename.equalsIgnoreCase("META"))
/* 2082:     */         {
/* 2083:2609 */           String httpEquiv = HTMLScanner.getValue(this.fAttributes, "http-equiv");
/* 2084:2610 */           if ((httpEquiv != null) && (httpEquiv.equalsIgnoreCase("content-type")))
/* 2085:     */           {
/* 2086:2614 */             String content = HTMLScanner.getValue(this.fAttributes, "content");
/* 2087:2615 */             if (content != null)
/* 2088:     */             {
/* 2089:2616 */               content = removeSpaces(content);
/* 2090:2617 */               int index1 = content.toLowerCase().indexOf("charset=");
/* 2091:2618 */               if ((index1 != -1) && (!HTMLScanner.this.fIgnoreSpecifiedCharset))
/* 2092:     */               {
/* 2093:2619 */                 int index2 = content.indexOf(';', index1);
/* 2094:2620 */                 String charset = index2 != -1 ? content.substring(index1 + 8, index2) : content.substring(index1 + 8);
/* 2095:2621 */                 changeEncoding(charset);
/* 2096:     */               }
/* 2097:     */             }
/* 2098:     */           }
/* 2099:     */         }
/* 2100:2626 */         else if (ename.equalsIgnoreCase("BODY"))
/* 2101:     */         {
/* 2102:2627 */           HTMLScanner.this.fByteStream.clear();
/* 2103:2628 */           HTMLScanner.this.fByteStream = null;
/* 2104:     */         }
/* 2105:     */         else
/* 2106:     */         {
/* 2107:2631 */           HTMLElements.Element element = HTMLElements.getElement(ename);
/* 2108:2632 */           if ((element.parent != null) && (element.parent.length > 0) && 
/* 2109:2633 */             (element.parent[0].code == 14))
/* 2110:     */           {
/* 2111:2634 */             HTMLScanner.this.fByteStream.clear();
/* 2112:2635 */             HTMLScanner.this.fByteStream = null;
/* 2113:     */           }
/* 2114:     */         }
/* 2115:     */       }
/* 2116:2640 */       if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 2117:     */       {
/* 2118:2641 */         this.fQName.setValues(null, ename, ename, null);
/* 2119:     */         
/* 2120:     */ 
/* 2121:     */ 
/* 2122:2645 */         HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2123:2646 */         HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2124:2647 */         HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2125:2648 */         if (empty[0] != 0) {
/* 2126:2649 */           HTMLScanner.this.fDocumentHandler.emptyElement(this.fQName, this.fAttributes, HTMLScanner.this.locationAugs());
/* 2127:     */         } else {
/* 2128:2652 */           HTMLScanner.this.fDocumentHandler.startElement(this.fQName, this.fAttributes, HTMLScanner.this.locationAugs());
/* 2129:     */         }
/* 2130:     */       }
/* 2131:2655 */       return ename;
/* 2132:     */     }
/* 2133:     */     
/* 2134:     */     private String removeSpaces(String content)
/* 2135:     */     {
/* 2136:2662 */       StringBuffer sb = null;
/* 2137:2663 */       for (int i = content.length() - 1; i >= 0; i--) {
/* 2138:2664 */         if (Character.isWhitespace(content.charAt(i)))
/* 2139:     */         {
/* 2140:2665 */           if (sb == null) {
/* 2141:2666 */             sb = new StringBuffer(content);
/* 2142:     */           }
/* 2143:2668 */           sb.deleteCharAt(i);
/* 2144:     */         }
/* 2145:     */       }
/* 2146:2671 */       return sb == null ? content : sb.toString();
/* 2147:     */     }
/* 2148:     */     
/* 2149:     */     private boolean changeEncoding(String charset)
/* 2150:     */     {
/* 2151:2680 */       if ((charset == null) || (HTMLScanner.this.fByteStream == null)) {
/* 2152:2681 */         return false;
/* 2153:     */       }
/* 2154:2683 */       charset = charset.trim();
/* 2155:2684 */       boolean encodingChanged = false;
/* 2156:     */       try
/* 2157:     */       {
/* 2158:2686 */         String ianaEncoding = charset;
/* 2159:2687 */         String javaEncoding = EncodingMap.getIANA2JavaMapping(ianaEncoding.toUpperCase());
/* 2160:2692 */         if (javaEncoding == null)
/* 2161:     */         {
/* 2162:2693 */           javaEncoding = ianaEncoding;
/* 2163:2694 */           if (HTMLScanner.this.fReportErrors) {
/* 2164:2695 */             HTMLScanner.this.fErrorReporter.reportError("HTML1001", new Object[] { ianaEncoding });
/* 2165:     */           }
/* 2166:     */         }
/* 2167:2699 */         if (!javaEncoding.equals(HTMLScanner.this.fJavaEncoding)) {
/* 2168:2700 */           if (!HTMLScanner.this.isEncodingCompatible(javaEncoding, HTMLScanner.this.fJavaEncoding))
/* 2169:     */           {
/* 2170:2701 */             if (HTMLScanner.this.fReportErrors) {
/* 2171:2702 */               HTMLScanner.this.fErrorReporter.reportError("HTML1015", new Object[] { javaEncoding, HTMLScanner.this.fJavaEncoding });
/* 2172:     */             }
/* 2173:     */           }
/* 2174:     */           else
/* 2175:     */           {
/* 2176:2707 */             HTMLScanner.this.fIso8859Encoding = ((ianaEncoding == null) || (ianaEncoding.toUpperCase().startsWith("ISO-8859")) || (ianaEncoding.equalsIgnoreCase(HTMLScanner.this.fDefaultIANAEncoding)));
/* 2177:     */             
/* 2178:     */ 
/* 2179:2710 */             HTMLScanner.this.fJavaEncoding = javaEncoding;
/* 2180:2711 */             HTMLScanner.CurrentEntity.access$1700(HTMLScanner.this.fCurrentEntity, new InputStreamReader(HTMLScanner.this.fByteStream, javaEncoding));
/* 2181:2712 */             HTMLScanner.this.fByteStream.playback();
/* 2182:2713 */             HTMLScanner.this.fElementDepth = HTMLScanner.this.fElementCount;
/* 2183:2714 */             HTMLScanner.this.fElementCount = 0;
/* 2184:2715 */             encodingChanged = true;
/* 2185:     */           }
/* 2186:     */         }
/* 2187:     */       }
/* 2188:     */       catch (UnsupportedEncodingException e)
/* 2189:     */       {
/* 2190:2720 */         if (HTMLScanner.this.fReportErrors) {
/* 2191:2721 */           HTMLScanner.this.fErrorReporter.reportError("HTML1010", new Object[] { charset });
/* 2192:     */         }
/* 2193:2726 */         HTMLScanner.this.fByteStream.clear();
/* 2194:2727 */         HTMLScanner.this.fByteStream = null;
/* 2195:     */       }
/* 2196:2729 */       return encodingChanged;
/* 2197:     */     }
/* 2198:     */     
/* 2199:     */     protected boolean scanAttribute(XMLAttributesImpl attributes, boolean[] empty)
/* 2200:     */       throws IOException
/* 2201:     */     {
/* 2202:2743 */       return scanAttribute(attributes, empty, '/');
/* 2203:     */     }
/* 2204:     */     
/* 2205:     */     protected boolean scanPseudoAttribute(XMLAttributesImpl attributes)
/* 2206:     */       throws IOException
/* 2207:     */     {
/* 2208:2753 */       return scanAttribute(attributes, HTMLScanner.this.fSingleBoolean, '?');
/* 2209:     */     }
/* 2210:     */     
/* 2211:     */     protected boolean scanAttribute(XMLAttributesImpl attributes, boolean[] empty, char endc)
/* 2212:     */       throws IOException
/* 2213:     */     {
/* 2214:2769 */       boolean skippedSpaces = HTMLScanner.this.skipSpaces();
/* 2215:2770 */       HTMLScanner.this.fBeginLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2216:2771 */       HTMLScanner.this.fBeginColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2217:2772 */       HTMLScanner.this.fBeginCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2218:2773 */       int c = HTMLScanner.this.fCurrentEntity.read();
/* 2219:2774 */       if (c == -1)
/* 2220:     */       {
/* 2221:2775 */         if (HTMLScanner.this.fReportErrors) {
/* 2222:2776 */           HTMLScanner.this.fErrorReporter.reportError("HTML1007", null);
/* 2223:     */         }
/* 2224:2778 */         return false;
/* 2225:     */       }
/* 2226:2780 */       if (c == 62) {
/* 2227:2781 */         return false;
/* 2228:     */       }
/* 2229:2783 */       if (c == 60)
/* 2230:     */       {
/* 2231:2784 */         HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2232:2785 */         return false;
/* 2233:     */       }
/* 2234:2787 */       HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2235:2788 */       String aname = HTMLScanner.this.scanName();
/* 2236:2789 */       if (aname == null)
/* 2237:     */       {
/* 2238:2790 */         if (HTMLScanner.this.fReportErrors) {
/* 2239:2791 */           HTMLScanner.this.fErrorReporter.reportError("HTML1011", null);
/* 2240:     */         }
/* 2241:2793 */         empty[0] = HTMLScanner.this.skipMarkup(false);
/* 2242:2794 */         return false;
/* 2243:     */       }
/* 2244:2796 */       if ((!skippedSpaces) && (HTMLScanner.this.fReportErrors)) {
/* 2245:2797 */         HTMLScanner.this.fErrorReporter.reportError("HTML1013", new Object[] { aname });
/* 2246:     */       }
/* 2247:2799 */       aname = HTMLScanner.modifyName(aname, HTMLScanner.this.fNamesAttrs);
/* 2248:2800 */       HTMLScanner.this.skipSpaces();
/* 2249:2801 */       c = HTMLScanner.this.fCurrentEntity.read();
/* 2250:2802 */       if (c == -1)
/* 2251:     */       {
/* 2252:2803 */         if (HTMLScanner.this.fReportErrors) {
/* 2253:2804 */           HTMLScanner.this.fErrorReporter.reportError("HTML1007", null);
/* 2254:     */         }
/* 2255:2806 */         throw new EOFException();
/* 2256:     */       }
/* 2257:2808 */       if ((c == 47) || (c == 62))
/* 2258:     */       {
/* 2259:2809 */         this.fQName.setValues(null, aname, aname, null);
/* 2260:2810 */         attributes.addAttribute(this.fQName, "CDATA", "");
/* 2261:2811 */         attributes.setSpecified(attributes.getLength() - 1, true);
/* 2262:2812 */         if (HTMLScanner.this.fAugmentations) {
/* 2263:2813 */           addLocationItem(attributes, attributes.getLength() - 1);
/* 2264:     */         }
/* 2265:2815 */         if (c == 47)
/* 2266:     */         {
/* 2267:2816 */           HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2268:2817 */           empty[0] = HTMLScanner.this.skipMarkup(false);
/* 2269:     */         }
/* 2270:2819 */         return false;
/* 2271:     */       }
/* 2272:2838 */       if (c == 61)
/* 2273:     */       {
/* 2274:2839 */         HTMLScanner.this.skipSpaces();
/* 2275:2840 */         c = HTMLScanner.this.fCurrentEntity.read();
/* 2276:2841 */         if (c == -1)
/* 2277:     */         {
/* 2278:2842 */           if (HTMLScanner.this.fReportErrors) {
/* 2279:2843 */             HTMLScanner.this.fErrorReporter.reportError("HTML1007", null);
/* 2280:     */           }
/* 2281:2845 */           throw new EOFException();
/* 2282:     */         }
/* 2283:2848 */         if (c == 62)
/* 2284:     */         {
/* 2285:2849 */           this.fQName.setValues(null, aname, aname, null);
/* 2286:2850 */           attributes.addAttribute(this.fQName, "CDATA", "");
/* 2287:2851 */           attributes.setSpecified(attributes.getLength() - 1, true);
/* 2288:2852 */           if (HTMLScanner.this.fAugmentations) {
/* 2289:2853 */             addLocationItem(attributes, attributes.getLength() - 1);
/* 2290:     */           }
/* 2291:2855 */           return false;
/* 2292:     */         }
/* 2293:2857 */         HTMLScanner.this.fStringBuffer.clear();
/* 2294:2858 */         HTMLScanner.this.fNonNormAttr.clear();
/* 2295:2859 */         if ((c != 39) && (c != 34))
/* 2296:     */         {
/* 2297:2860 */           HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2298:     */           for (;;)
/* 2299:     */           {
/* 2300:2862 */             c = HTMLScanner.this.fCurrentEntity.read();
/* 2301:2864 */             if ((Character.isWhitespace((char)c)) || (c == 62))
/* 2302:     */             {
/* 2303:2866 */               HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2304:2867 */               break;
/* 2305:     */             }
/* 2306:2869 */             if (c == -1)
/* 2307:     */             {
/* 2308:2870 */               if (HTMLScanner.this.fReportErrors) {
/* 2309:2871 */                 HTMLScanner.this.fErrorReporter.reportError("HTML1007", null);
/* 2310:     */               }
/* 2311:2873 */               throw new EOFException();
/* 2312:     */             }
/* 2313:2875 */             if (c == 38)
/* 2314:     */             {
/* 2315:2876 */               int ce = HTMLScanner.this.scanEntityRef(HTMLScanner.this.fStringBuffer2, false);
/* 2316:2877 */               if (ce != -1) {
/* 2317:2878 */                 HTMLScanner.this.fStringBuffer.append((char)ce);
/* 2318:     */               } else {
/* 2319:2881 */                 HTMLScanner.this.fStringBuffer.append(HTMLScanner.this.fStringBuffer2);
/* 2320:     */               }
/* 2321:2883 */               HTMLScanner.this.fNonNormAttr.append(HTMLScanner.this.fStringBuffer2);
/* 2322:     */             }
/* 2323:     */             else
/* 2324:     */             {
/* 2325:2886 */               HTMLScanner.this.fStringBuffer.append((char)c);
/* 2326:2887 */               HTMLScanner.this.fNonNormAttr.append((char)c);
/* 2327:     */             }
/* 2328:     */           }
/* 2329:2890 */           this.fQName.setValues(null, aname, aname, null);
/* 2330:2891 */           String avalue = HTMLScanner.this.fStringBuffer.toString();
/* 2331:2892 */           attributes.addAttribute(this.fQName, "CDATA", avalue);
/* 2332:     */           
/* 2333:2894 */           int lastattr = attributes.getLength() - 1;
/* 2334:2895 */           attributes.setSpecified(lastattr, true);
/* 2335:2896 */           attributes.setNonNormalizedValue(lastattr, HTMLScanner.this.fNonNormAttr.toString());
/* 2336:2897 */           if (HTMLScanner.this.fAugmentations) {
/* 2337:2898 */             addLocationItem(attributes, attributes.getLength() - 1);
/* 2338:     */           }
/* 2339:2900 */           return true;
/* 2340:     */         }
/* 2341:2902 */         char quote = (char)c;
/* 2342:2903 */         boolean isStart = true;
/* 2343:2904 */         boolean prevSpace = false;
/* 2344:     */         do
/* 2345:     */         {
/* 2346:2906 */           boolean acceptSpace = (!HTMLScanner.this.fNormalizeAttributes) || ((!isStart) && (!prevSpace));
/* 2347:2907 */           c = HTMLScanner.this.fCurrentEntity.read();
/* 2348:2908 */           if (c == -1)
/* 2349:     */           {
/* 2350:2909 */             if (!HTMLScanner.this.fReportErrors) {
/* 2351:     */               break;
/* 2352:     */             }
/* 2353:2910 */             HTMLScanner.this.fErrorReporter.reportError("HTML1007", null); break;
/* 2354:     */           }
/* 2355:2915 */           if (c == 38)
/* 2356:     */           {
/* 2357:2916 */             isStart = false;
/* 2358:2917 */             int ce = HTMLScanner.this.scanEntityRef(HTMLScanner.this.fStringBuffer2, false);
/* 2359:2918 */             if (ce != -1) {
/* 2360:2919 */               HTMLScanner.this.fStringBuffer.append((char)ce);
/* 2361:     */             } else {
/* 2362:2922 */               HTMLScanner.this.fStringBuffer.append(HTMLScanner.this.fStringBuffer2);
/* 2363:     */             }
/* 2364:2924 */             HTMLScanner.this.fNonNormAttr.append(HTMLScanner.this.fStringBuffer2);
/* 2365:     */           }
/* 2366:2926 */           else if ((c == 32) || (c == 9))
/* 2367:     */           {
/* 2368:2927 */             if (acceptSpace) {
/* 2369:2928 */               HTMLScanner.this.fStringBuffer.append(HTMLScanner.this.fNormalizeAttributes ? ' ' : (char)c);
/* 2370:     */             }
/* 2371:2930 */             HTMLScanner.this.fNonNormAttr.append((char)c);
/* 2372:     */           }
/* 2373:2932 */           else if ((c == 13) || (c == 10))
/* 2374:     */           {
/* 2375:2933 */             if (c == 13)
/* 2376:     */             {
/* 2377:2934 */               int c2 = HTMLScanner.this.fCurrentEntity.read();
/* 2378:2935 */               if (c2 != 10)
/* 2379:     */               {
/* 2380:2936 */                 HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2381:     */               }
/* 2382:     */               else
/* 2383:     */               {
/* 2384:2939 */                 HTMLScanner.this.fNonNormAttr.append('\r');
/* 2385:2940 */                 c = c2;
/* 2386:     */               }
/* 2387:     */             }
/* 2388:2943 */             if (acceptSpace) {
/* 2389:2944 */               HTMLScanner.this.fStringBuffer.append(HTMLScanner.this.fNormalizeAttributes ? ' ' : '\n');
/* 2390:     */             }
/* 2391:2946 */             HTMLScanner.CurrentEntity.access$1600(HTMLScanner.this.fCurrentEntity);
/* 2392:2947 */             HTMLScanner.this.fNonNormAttr.append((char)c);
/* 2393:     */           }
/* 2394:2949 */           else if (c != quote)
/* 2395:     */           {
/* 2396:2950 */             isStart = false;
/* 2397:2951 */             HTMLScanner.this.fStringBuffer.append((char)c);
/* 2398:2952 */             HTMLScanner.this.fNonNormAttr.append((char)c);
/* 2399:     */           }
/* 2400:2954 */           prevSpace = (c == 32) || (c == 9) || (c == 13) || (c == 10);
/* 2401:2955 */           isStart = (isStart) && (prevSpace);
/* 2402:2956 */         } while (c != quote);
/* 2403:2958 */         if ((HTMLScanner.this.fNormalizeAttributes) && (HTMLScanner.this.fStringBuffer.length > 0)) {
/* 2404:2960 */           if (HTMLScanner.this.fStringBuffer.ch[(HTMLScanner.this.fStringBuffer.length - 1)] == ' ') {
/* 2405:2961 */             HTMLScanner.this.fStringBuffer.length -= 1;
/* 2406:     */           }
/* 2407:     */         }
/* 2408:2965 */         this.fQName.setValues(null, aname, aname, null);
/* 2409:2966 */         String avalue = HTMLScanner.this.fStringBuffer.toString();
/* 2410:2967 */         attributes.addAttribute(this.fQName, "CDATA", avalue);
/* 2411:     */         
/* 2412:2969 */         int lastattr = attributes.getLength() - 1;
/* 2413:2970 */         attributes.setSpecified(lastattr, true);
/* 2414:2971 */         attributes.setNonNormalizedValue(lastattr, HTMLScanner.this.fNonNormAttr.toString());
/* 2415:2972 */         if (HTMLScanner.this.fAugmentations) {
/* 2416:2973 */           addLocationItem(attributes, attributes.getLength() - 1);
/* 2417:     */         }
/* 2418:     */       }
/* 2419:     */       else
/* 2420:     */       {
/* 2421:2977 */         this.fQName.setValues(null, aname, aname, null);
/* 2422:2978 */         attributes.addAttribute(this.fQName, "CDATA", "");
/* 2423:2979 */         attributes.setSpecified(attributes.getLength() - 1, true);
/* 2424:2980 */         HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2425:2981 */         if (HTMLScanner.this.fAugmentations) {
/* 2426:2982 */           addLocationItem(attributes, attributes.getLength() - 1);
/* 2427:     */         }
/* 2428:     */       }
/* 2429:2985 */       return true;
/* 2430:     */     }
/* 2431:     */     
/* 2432:     */     protected void addLocationItem(XMLAttributes attributes, int index)
/* 2433:     */     {
/* 2434:2990 */       HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2435:2991 */       HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2436:2992 */       HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2437:2993 */       HTMLScanner.LocationItem locationItem = new HTMLScanner.LocationItem();
/* 2438:2994 */       locationItem.setValues(HTMLScanner.this.fBeginLineNumber, HTMLScanner.this.fBeginColumnNumber, HTMLScanner.this.fBeginCharacterOffset, HTMLScanner.this.fEndLineNumber, HTMLScanner.this.fEndColumnNumber, HTMLScanner.this.fEndCharacterOffset);
/* 2439:     */       
/* 2440:     */ 
/* 2441:2997 */       Augmentations augs = attributes.getAugmentations(index);
/* 2442:2998 */       augs.putItem("http://cyberneko.org/html/features/augmentations", locationItem);
/* 2443:     */     }
/* 2444:     */     
/* 2445:     */     protected void scanEndElement()
/* 2446:     */       throws IOException
/* 2447:     */     {
/* 2448:3003 */       String ename = HTMLScanner.this.scanName();
/* 2449:3004 */       if ((HTMLScanner.this.fReportErrors) && (ename == null)) {
/* 2450:3005 */         HTMLScanner.this.fErrorReporter.reportError("HTML1012", null);
/* 2451:     */       }
/* 2452:3007 */       HTMLScanner.this.skipMarkup(false);
/* 2453:3008 */       if (ename != null)
/* 2454:     */       {
/* 2455:3009 */         ename = HTMLScanner.modifyName(ename, HTMLScanner.this.fNamesElems);
/* 2456:3010 */         if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 2457:     */         {
/* 2458:3011 */           this.fQName.setValues(null, ename, ename, null);
/* 2459:     */           
/* 2460:     */ 
/* 2461:     */ 
/* 2462:3015 */           HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2463:3016 */           HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2464:3017 */           HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2465:3018 */           HTMLScanner.this.fDocumentHandler.endElement(this.fQName, HTMLScanner.this.locationAugs());
/* 2466:     */         }
/* 2467:     */       }
/* 2468:     */     }
/* 2469:     */     
/* 2470:     */     private boolean isEnded(String ename)
/* 2471:     */     {
/* 2472:3031 */       String content = new String(HTMLScanner.this.fCurrentEntity.buffer, HTMLScanner.this.fCurrentEntity.offset, HTMLScanner.this.fCurrentEntity.length - HTMLScanner.this.fCurrentEntity.offset);
/* 2473:     */       
/* 2474:3033 */       return content.toLowerCase().indexOf("</" + ename.toLowerCase() + ">") != -1;
/* 2475:     */     }
/* 2476:     */   }
/* 2477:     */   
/* 2478:     */   public class SpecialScanner
/* 2479:     */     implements HTMLScanner.Scanner
/* 2480:     */   {
/* 2481:     */     protected String fElementName;
/* 2482:     */     protected boolean fStyle;
/* 2483:     */     protected boolean fTextarea;
/* 2484:     */     protected boolean fTitle;
/* 2485:3067 */     private final QName fQName = new QName();
/* 2486:3070 */     private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/* 2487:     */     
/* 2488:     */     public SpecialScanner() {}
/* 2489:     */     
/* 2490:     */     public HTMLScanner.Scanner setElementName(String ename)
/* 2491:     */     {
/* 2492:3078 */       this.fElementName = ename;
/* 2493:3079 */       this.fStyle = this.fElementName.equalsIgnoreCase("STYLE");
/* 2494:3080 */       this.fTextarea = this.fElementName.equalsIgnoreCase("TEXTAREA");
/* 2495:3081 */       this.fTitle = this.fElementName.equalsIgnoreCase("TITLE");
/* 2496:3082 */       return this;
/* 2497:     */     }
/* 2498:     */     
/* 2499:     */     public boolean scan(boolean complete)
/* 2500:     */       throws IOException
/* 2501:     */     {
/* 2502:     */       boolean next;
/* 2503:     */       do
/* 2504:     */       {
/* 2505:     */         try
/* 2506:     */         {
/* 2507:3094 */           next = false;
/* 2508:3095 */           switch (HTMLScanner.this.fScannerState)
/* 2509:     */           {
/* 2510:     */           case 0: 
/* 2511:3097 */             HTMLScanner.this.fBeginLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2512:3098 */             HTMLScanner.this.fBeginColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2513:3099 */             HTMLScanner.this.fBeginCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2514:3100 */             int c = HTMLScanner.this.fCurrentEntity.read();
/* 2515:3101 */             if (c == 60)
/* 2516:     */             {
/* 2517:3102 */               HTMLScanner.this.setScannerState((short)1);
/* 2518:     */             }
/* 2519:     */             else
/* 2520:     */             {
/* 2521:3105 */               if (c == 38)
/* 2522:     */               {
/* 2523:3106 */                 if ((this.fTextarea) || (this.fTitle))
/* 2524:     */                 {
/* 2525:3107 */                   HTMLScanner.this.scanEntityRef(this.fStringBuffer, true);
/* 2526:3108 */                   continue;
/* 2527:     */                 }
/* 2528:3110 */                 this.fStringBuffer.clear();
/* 2529:3111 */                 this.fStringBuffer.append('&');
/* 2530:     */               }
/* 2531:     */               else
/* 2532:     */               {
/* 2533:3113 */                 if (c == -1)
/* 2534:     */                 {
/* 2535:3114 */                   if (HTMLScanner.this.fReportErrors) {
/* 2536:3115 */                     HTMLScanner.this.fErrorReporter.reportError("HTML1007", null);
/* 2537:     */                   }
/* 2538:3117 */                   throw new EOFException();
/* 2539:     */                 }
/* 2540:3120 */                 HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2541:3121 */                 this.fStringBuffer.clear();
/* 2542:     */               }
/* 2543:3123 */               scanCharacters(this.fStringBuffer, -1);
/* 2544:     */             }
/* 2545:3124 */             break;
/* 2546:     */           case 1: 
/* 2547:3127 */             int delimiter = -1;
/* 2548:3128 */             int c = HTMLScanner.this.fCurrentEntity.read();
/* 2549:3129 */             if (c == 47)
/* 2550:     */             {
/* 2551:3130 */               String ename = HTMLScanner.this.scanName();
/* 2552:3131 */               if (ename != null)
/* 2553:     */               {
/* 2554:3132 */                 if (ename.equalsIgnoreCase(this.fElementName))
/* 2555:     */                 {
/* 2556:3133 */                   if (HTMLScanner.this.fCurrentEntity.read() == 62)
/* 2557:     */                   {
/* 2558:3134 */                     ename = HTMLScanner.modifyName(ename, HTMLScanner.this.fNamesElems);
/* 2559:3135 */                     if ((HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 2560:     */                     {
/* 2561:3136 */                       this.fQName.setValues(null, ename, ename, null);
/* 2562:     */                       
/* 2563:     */ 
/* 2564:     */ 
/* 2565:3140 */                       HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2566:3141 */                       HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2567:3142 */                       HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2568:3143 */                       HTMLScanner.this.fDocumentHandler.endElement(this.fQName, HTMLScanner.this.locationAugs());
/* 2569:     */                     }
/* 2570:3145 */                     HTMLScanner.this.setScanner(HTMLScanner.this.fContentScanner);
/* 2571:3146 */                     HTMLScanner.this.setScannerState((short)0);
/* 2572:3147 */                     return true;
/* 2573:     */                   }
/* 2574:3150 */                   HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2575:     */                 }
/* 2576:3153 */                 this.fStringBuffer.clear();
/* 2577:3154 */                 this.fStringBuffer.append("</");
/* 2578:3155 */                 this.fStringBuffer.append(ename);
/* 2579:     */               }
/* 2580:     */               else
/* 2581:     */               {
/* 2582:3158 */                 this.fStringBuffer.clear();
/* 2583:3159 */                 this.fStringBuffer.append("</");
/* 2584:     */               }
/* 2585:     */             }
/* 2586:     */             else
/* 2587:     */             {
/* 2588:3163 */               this.fStringBuffer.clear();
/* 2589:3164 */               this.fStringBuffer.append('<');
/* 2590:3165 */               this.fStringBuffer.append((char)c);
/* 2591:     */             }
/* 2592:3167 */             scanCharacters(this.fStringBuffer, delimiter);
/* 2593:3168 */             HTMLScanner.this.setScannerState((short)0);
/* 2594:     */           }
/* 2595:     */         }
/* 2596:     */         catch (EOFException e)
/* 2597:     */         {
/* 2598:3174 */           HTMLScanner.this.setScanner(HTMLScanner.this.fContentScanner);
/* 2599:3175 */           if (HTMLScanner.this.fCurrentEntityStack.empty())
/* 2600:     */           {
/* 2601:3176 */             HTMLScanner.this.setScannerState((short)11);
/* 2602:     */           }
/* 2603:     */           else
/* 2604:     */           {
/* 2605:3179 */             HTMLScanner.this.fCurrentEntity = ((HTMLScanner.CurrentEntity)HTMLScanner.this.fCurrentEntityStack.pop());
/* 2606:3180 */             HTMLScanner.this.setScannerState((short)0);
/* 2607:     */           }
/* 2608:3182 */           return true;
/* 2609:     */         }
/* 2610:3185 */       } while ((next) || (complete));
/* 2611:3186 */       return true;
/* 2612:     */     }
/* 2613:     */     
/* 2614:     */     protected void scanCharacters(XMLStringBuffer buffer, int delimiter)
/* 2615:     */       throws IOException
/* 2616:     */     {
/* 2617:3196 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, "(scanCharacters, delimiter=" + delimiter + ": ");
/* 2618:     */       for (;;)
/* 2619:     */       {
/* 2620:3199 */         int c = HTMLScanner.this.fCurrentEntity.read();
/* 2621:3201 */         if ((c == -1) || (c == 60) || (c == 38))
/* 2622:     */         {
/* 2623:3202 */           if (c == -1) {
/* 2624:     */             break;
/* 2625:     */           }
/* 2626:3203 */           HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity); break;
/* 2627:     */         }
/* 2628:3208 */         if ((c == 13) || (c == 10))
/* 2629:     */         {
/* 2630:3209 */           HTMLScanner.CurrentEntity.access$400(HTMLScanner.this.fCurrentEntity);
/* 2631:3210 */           int newlines = HTMLScanner.this.skipNewlines();
/* 2632:3211 */           for (int i = 0; i < newlines; i++) {
/* 2633:3212 */             buffer.append('\n');
/* 2634:     */           }
/* 2635:     */         }
/* 2636:     */         else
/* 2637:     */         {
/* 2638:3216 */           buffer.append((char)c);
/* 2639:3217 */           if (c == 10) {
/* 2640:3218 */             HTMLScanner.CurrentEntity.access$1600(HTMLScanner.this.fCurrentEntity);
/* 2641:     */           }
/* 2642:     */         }
/* 2643:     */       }
/* 2644:3223 */       if (this.fStyle)
/* 2645:     */       {
/* 2646:3224 */         if (HTMLScanner.this.fStyleStripCommentDelims) {
/* 2647:3225 */           HTMLScanner.reduceToContent(buffer, "<!--", "-->");
/* 2648:     */         }
/* 2649:3227 */         if (HTMLScanner.this.fStyleStripCDATADelims) {
/* 2650:3228 */           HTMLScanner.reduceToContent(buffer, "<![CDATA[", "]]>");
/* 2651:     */         }
/* 2652:     */       }
/* 2653:3232 */       if ((buffer.length > 0) && (HTMLScanner.this.fDocumentHandler != null) && (HTMLScanner.this.fElementCount >= HTMLScanner.this.fElementDepth))
/* 2654:     */       {
/* 2655:3236 */         HTMLScanner.this.fEndLineNumber = HTMLScanner.this.fCurrentEntity.getLineNumber();
/* 2656:3237 */         HTMLScanner.this.fEndColumnNumber = HTMLScanner.CurrentEntity.access$200(HTMLScanner.this.fCurrentEntity);
/* 2657:3238 */         HTMLScanner.this.fEndCharacterOffset = HTMLScanner.CurrentEntity.access$300(HTMLScanner.this.fCurrentEntity);
/* 2658:3239 */         HTMLScanner.this.fDocumentHandler.characters(buffer, HTMLScanner.this.locationAugs());
/* 2659:     */       }
/* 2660:3241 */       HTMLScanner.CurrentEntity.access$500(HTMLScanner.this.fCurrentEntity, ")scanCharacters: ");
/* 2661:     */     }
/* 2662:     */   }
/* 2663:     */   
/* 2664:     */   public static class PlaybackInputStream
/* 2665:     */     extends FilterInputStream
/* 2666:     */   {
/* 2667:     */     private static final boolean DEBUG_PLAYBACK = false;
/* 2668:3285 */     protected boolean fPlayback = false;
/* 2669:3288 */     protected boolean fCleared = false;
/* 2670:3291 */     protected boolean fDetected = false;
/* 2671:3296 */     protected byte[] fByteBuffer = new byte[1024];
/* 2672:3299 */     protected int fByteOffset = 0;
/* 2673:3302 */     protected int fByteLength = 0;
/* 2674:3305 */     public int fPushbackOffset = 0;
/* 2675:3308 */     public int fPushbackLength = 0;
/* 2676:     */     
/* 2677:     */     public PlaybackInputStream(InputStream in)
/* 2678:     */     {
/* 2679:3316 */       super();
/* 2680:     */     }
/* 2681:     */     
/* 2682:     */     public void detectEncoding(String[] encodings)
/* 2683:     */       throws IOException
/* 2684:     */     {
/* 2685:3325 */       if (this.fDetected) {
/* 2686:3326 */         throw new IOException("Should not detect encoding twice.");
/* 2687:     */       }
/* 2688:3328 */       this.fDetected = true;
/* 2689:3329 */       int b1 = read();
/* 2690:3330 */       if (b1 == -1) {
/* 2691:3331 */         return;
/* 2692:     */       }
/* 2693:3333 */       int b2 = read();
/* 2694:3334 */       if (b2 == -1)
/* 2695:     */       {
/* 2696:3335 */         this.fPushbackLength = 1;
/* 2697:3336 */         return;
/* 2698:     */       }
/* 2699:3339 */       if ((b1 == 239) && (b2 == 187))
/* 2700:     */       {
/* 2701:3340 */         int b3 = read();
/* 2702:3341 */         if (b3 == 191)
/* 2703:     */         {
/* 2704:3342 */           this.fPushbackOffset = 3;
/* 2705:3343 */           encodings[0] = "UTF-8";
/* 2706:3344 */           encodings[1] = "UTF8";
/* 2707:3345 */           return;
/* 2708:     */         }
/* 2709:3347 */         this.fPushbackLength = 3;
/* 2710:     */       }
/* 2711:3350 */       if ((b1 == 255) && (b2 == 254))
/* 2712:     */       {
/* 2713:3351 */         encodings[0] = "UTF-16";
/* 2714:3352 */         encodings[1] = "UnicodeLittleUnmarked";
/* 2715:3353 */         return;
/* 2716:     */       }
/* 2717:3356 */       if ((b1 == 254) && (b2 == 255))
/* 2718:     */       {
/* 2719:3357 */         encodings[0] = "UTF-16";
/* 2720:3358 */         encodings[1] = "UnicodeBigUnmarked";
/* 2721:3359 */         return;
/* 2722:     */       }
/* 2723:3362 */       this.fPushbackLength = 2;
/* 2724:     */     }
/* 2725:     */     
/* 2726:     */     public void playback()
/* 2727:     */     {
/* 2728:3367 */       this.fPlayback = true;
/* 2729:     */     }
/* 2730:     */     
/* 2731:     */     public void clear()
/* 2732:     */     {
/* 2733:3379 */       if (!this.fPlayback)
/* 2734:     */       {
/* 2735:3380 */         this.fCleared = true;
/* 2736:3381 */         this.fByteBuffer = null;
/* 2737:     */       }
/* 2738:     */     }
/* 2739:     */     
/* 2740:     */     public int read()
/* 2741:     */       throws IOException
/* 2742:     */     {
/* 2743:3394 */       if (this.fPushbackOffset < this.fPushbackLength) {
/* 2744:3395 */         return this.fByteBuffer[(this.fPushbackOffset++)];
/* 2745:     */       }
/* 2746:3397 */       if (this.fCleared) {
/* 2747:3398 */         return this.in.read();
/* 2748:     */       }
/* 2749:3400 */       if (this.fPlayback)
/* 2750:     */       {
/* 2751:3401 */         int c = this.fByteBuffer[(this.fByteOffset++)];
/* 2752:3402 */         if (this.fByteOffset == this.fByteLength)
/* 2753:     */         {
/* 2754:3403 */           this.fCleared = true;
/* 2755:3404 */           this.fByteBuffer = null;
/* 2756:     */         }
/* 2757:3409 */         return c;
/* 2758:     */       }
/* 2759:3411 */       int c = this.in.read();
/* 2760:3412 */       if (c != -1)
/* 2761:     */       {
/* 2762:3413 */         if (this.fByteLength == this.fByteBuffer.length)
/* 2763:     */         {
/* 2764:3414 */           byte[] newarray = new byte[this.fByteLength + 1024];
/* 2765:3415 */           System.arraycopy(this.fByteBuffer, 0, newarray, 0, this.fByteLength);
/* 2766:3416 */           this.fByteBuffer = newarray;
/* 2767:     */         }
/* 2768:3418 */         this.fByteBuffer[(this.fByteLength++)] = ((byte)c);
/* 2769:     */       }
/* 2770:3423 */       return c;
/* 2771:     */     }
/* 2772:     */     
/* 2773:     */     public int read(byte[] array)
/* 2774:     */       throws IOException
/* 2775:     */     {
/* 2776:3428 */       return read(array, 0, array.length);
/* 2777:     */     }
/* 2778:     */     
/* 2779:     */     public int read(byte[] array, int offset, int length)
/* 2780:     */       throws IOException
/* 2781:     */     {
/* 2782:3436 */       if (this.fPushbackOffset < this.fPushbackLength)
/* 2783:     */       {
/* 2784:3437 */         int count = this.fPushbackLength - this.fPushbackOffset;
/* 2785:3438 */         if (count > length) {
/* 2786:3439 */           count = length;
/* 2787:     */         }
/* 2788:3441 */         System.arraycopy(this.fByteBuffer, this.fPushbackOffset, array, offset, count);
/* 2789:3442 */         this.fPushbackOffset += count;
/* 2790:3443 */         return count;
/* 2791:     */       }
/* 2792:3445 */       if (this.fCleared) {
/* 2793:3446 */         return this.in.read(array, offset, length);
/* 2794:     */       }
/* 2795:3448 */       if (this.fPlayback)
/* 2796:     */       {
/* 2797:3449 */         if (this.fByteOffset + length > this.fByteLength) {
/* 2798:3450 */           length = this.fByteLength - this.fByteOffset;
/* 2799:     */         }
/* 2800:3452 */         System.arraycopy(this.fByteBuffer, this.fByteOffset, array, offset, length);
/* 2801:3453 */         this.fByteOffset += length;
/* 2802:3454 */         if (this.fByteOffset == this.fByteLength)
/* 2803:     */         {
/* 2804:3455 */           this.fCleared = true;
/* 2805:3456 */           this.fByteBuffer = null;
/* 2806:     */         }
/* 2807:3458 */         return length;
/* 2808:     */       }
/* 2809:3460 */       int count = this.in.read(array, offset, length);
/* 2810:3461 */       if (count != -1)
/* 2811:     */       {
/* 2812:3462 */         if (this.fByteLength + count > this.fByteBuffer.length)
/* 2813:     */         {
/* 2814:3463 */           byte[] newarray = new byte[this.fByteLength + count + 512];
/* 2815:3464 */           System.arraycopy(this.fByteBuffer, 0, newarray, 0, this.fByteLength);
/* 2816:3465 */           this.fByteBuffer = newarray;
/* 2817:     */         }
/* 2818:3467 */         System.arraycopy(array, offset, this.fByteBuffer, this.fByteLength, count);
/* 2819:3468 */         this.fByteLength += count;
/* 2820:     */       }
/* 2821:3473 */       return count;
/* 2822:     */     }
/* 2823:     */   }
/* 2824:     */   
/* 2825:     */   protected static class LocationItem
/* 2826:     */     implements HTMLEventInfo, Cloneable
/* 2827:     */   {
/* 2828:     */     protected int fBeginLineNumber;
/* 2829:     */     protected int fBeginColumnNumber;
/* 2830:     */     protected int fBeginCharacterOffset;
/* 2831:     */     protected int fEndLineNumber;
/* 2832:     */     protected int fEndColumnNumber;
/* 2833:     */     protected int fEndCharacterOffset;
/* 2834:     */     
/* 2835:     */     public LocationItem() {}
/* 2836:     */     
/* 2837:     */     LocationItem(LocationItem other)
/* 2838:     */     {
/* 2839:3515 */       setValues(other.fBeginLineNumber, other.fBeginColumnNumber, other.fBeginCharacterOffset, other.fEndLineNumber, other.fEndColumnNumber, other.fEndCharacterOffset);
/* 2840:     */     }
/* 2841:     */     
/* 2842:     */     public void setValues(int beginLine, int beginColumn, int beginOffset, int endLine, int endColumn, int endOffset)
/* 2843:     */     {
/* 2844:3522 */       this.fBeginLineNumber = beginLine;
/* 2845:3523 */       this.fBeginColumnNumber = beginColumn;
/* 2846:3524 */       this.fBeginCharacterOffset = beginOffset;
/* 2847:3525 */       this.fEndLineNumber = endLine;
/* 2848:3526 */       this.fEndColumnNumber = endColumn;
/* 2849:3527 */       this.fEndCharacterOffset = endOffset;
/* 2850:     */     }
/* 2851:     */     
/* 2852:     */     public int getBeginLineNumber()
/* 2853:     */     {
/* 2854:3538 */       return this.fBeginLineNumber;
/* 2855:     */     }
/* 2856:     */     
/* 2857:     */     public int getBeginColumnNumber()
/* 2858:     */     {
/* 2859:3543 */       return this.fBeginColumnNumber;
/* 2860:     */     }
/* 2861:     */     
/* 2862:     */     public int getBeginCharacterOffset()
/* 2863:     */     {
/* 2864:3548 */       return this.fBeginCharacterOffset;
/* 2865:     */     }
/* 2866:     */     
/* 2867:     */     public int getEndLineNumber()
/* 2868:     */     {
/* 2869:3553 */       return this.fEndLineNumber;
/* 2870:     */     }
/* 2871:     */     
/* 2872:     */     public int getEndColumnNumber()
/* 2873:     */     {
/* 2874:3558 */       return this.fEndColumnNumber;
/* 2875:     */     }
/* 2876:     */     
/* 2877:     */     public int getEndCharacterOffset()
/* 2878:     */     {
/* 2879:3563 */       return this.fEndCharacterOffset;
/* 2880:     */     }
/* 2881:     */     
/* 2882:     */     public boolean isSynthesized()
/* 2883:     */     {
/* 2884:3570 */       return false;
/* 2885:     */     }
/* 2886:     */     
/* 2887:     */     public String toString()
/* 2888:     */     {
/* 2889:3579 */       StringBuffer str = new StringBuffer();
/* 2890:3580 */       str.append(this.fBeginLineNumber);
/* 2891:3581 */       str.append(':');
/* 2892:3582 */       str.append(this.fBeginColumnNumber);
/* 2893:3583 */       str.append(':');
/* 2894:3584 */       str.append(this.fBeginCharacterOffset);
/* 2895:3585 */       str.append(':');
/* 2896:3586 */       str.append(this.fEndLineNumber);
/* 2897:3587 */       str.append(':');
/* 2898:3588 */       str.append(this.fEndColumnNumber);
/* 2899:3589 */       str.append(':');
/* 2900:3590 */       str.append(this.fEndCharacterOffset);
/* 2901:3591 */       return str.toString();
/* 2902:     */     }
/* 2903:     */   }
/* 2904:     */   
/* 2905:     */   boolean isEncodingCompatible(String encoding1, String encoding2)
/* 2906:     */   {
/* 2907:3602 */     String reference = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=";
/* 2908:     */     try
/* 2909:     */     {
/* 2910:3604 */       byte[] bytesEncoding1 = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=".getBytes(encoding1);
/* 2911:3605 */       String referenceWithEncoding2 = new String(bytesEncoding1, encoding2);
/* 2912:3606 */       return "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=".equals(referenceWithEncoding2);
/* 2913:     */     }
/* 2914:     */     catch (UnsupportedEncodingException e) {}
/* 2915:3609 */     return false;
/* 2916:     */   }
/* 2917:     */   
/* 2918:     */   private boolean endsWith(XMLStringBuffer buffer, String string)
/* 2919:     */   {
/* 2920:3614 */     int l = string.length();
/* 2921:3615 */     if (buffer.length < l) {
/* 2922:3616 */       return false;
/* 2923:     */     }
/* 2924:3619 */     String s = new String(buffer.ch, buffer.length - l, l);
/* 2925:3620 */     return string.equals(s);
/* 2926:     */   }
/* 2927:     */   
/* 2928:     */   protected int readPreservingBufferContent()
/* 2929:     */     throws IOException
/* 2930:     */   {
/* 2931:3626 */     this.fCurrentEntity.debugBufferIfNeeded("(read: ");
/* 2932:3627 */     if ((this.fCurrentEntity.offset == this.fCurrentEntity.length) && 
/* 2933:3628 */       (this.fCurrentEntity.load(this.fCurrentEntity.length) < 1)) {
/* 2934:3632 */       return -1;
/* 2935:     */     }
/* 2936:3635 */     char c = this.fCurrentEntity.getNextChar();
/* 2937:3636 */     this.fCurrentEntity.debugBufferIfNeeded(")read: ", " -> " + c);
/* 2938:3637 */     return c;
/* 2939:     */   }
/* 2940:     */   
/* 2941:     */   private boolean endCommentAvailable()
/* 2942:     */     throws IOException
/* 2943:     */   {
/* 2944:3644 */     int nbCaret = 0;
/* 2945:3645 */     int originalOffset = this.fCurrentEntity.offset;
/* 2946:3646 */     int originalColumnNumber = this.fCurrentEntity.getColumnNumber();
/* 2947:3647 */     int originalCharacterOffset = this.fCurrentEntity.getCharacterOffset();
/* 2948:     */     for (;;)
/* 2949:     */     {
/* 2950:3650 */       int c = readPreservingBufferContent();
/* 2951:3651 */       if (c == -1)
/* 2952:     */       {
/* 2953:3652 */         this.fCurrentEntity.restorePosition(originalOffset, originalColumnNumber, originalCharacterOffset);
/* 2954:3653 */         return false;
/* 2955:     */       }
/* 2956:3655 */       if ((c == 62) && (nbCaret >= 2))
/* 2957:     */       {
/* 2958:3656 */         this.fCurrentEntity.restorePosition(originalOffset, originalColumnNumber, originalCharacterOffset);
/* 2959:3657 */         return true;
/* 2960:     */       }
/* 2961:3659 */       if (c == 45) {
/* 2962:3660 */         nbCaret++;
/* 2963:     */       } else {
/* 2964:3663 */         nbCaret = 0;
/* 2965:     */       }
/* 2966:     */     }
/* 2967:     */   }
/* 2968:     */   
/* 2969:     */   static void reduceToContent(XMLStringBuffer buffer, String startMarker, String endMarker)
/* 2970:     */   {
/* 2971:3673 */     int i = 0;
/* 2972:3674 */     int startContent = -1;
/* 2973:3675 */     int l1 = startMarker.length();
/* 2974:3676 */     int l2 = endMarker.length();
/* 2975:3677 */     while (i < buffer.length - l1 - l2)
/* 2976:     */     {
/* 2977:3678 */       char c = buffer.ch[(buffer.offset + i)];
/* 2978:3679 */       if (Character.isWhitespace(c))
/* 2979:     */       {
/* 2980:3680 */         i++;
/* 2981:     */       }
/* 2982:     */       else
/* 2983:     */       {
/* 2984:3682 */         if ((c == startMarker.charAt(0)) && (startMarker.equals(new String(buffer.ch, buffer.offset + i, l1))))
/* 2985:     */         {
/* 2986:3684 */           startContent = buffer.offset + i + l1;
/* 2987:3685 */           break;
/* 2988:     */         }
/* 2989:3688 */         return;
/* 2990:     */       }
/* 2991:     */     }
/* 2992:3691 */     if (startContent == -1) {
/* 2993:3692 */       return;
/* 2994:     */     }
/* 2995:3695 */     i = buffer.length - 1;
/* 2996:3696 */     while (i > startContent + l2)
/* 2997:     */     {
/* 2998:3697 */       char c = buffer.ch[(buffer.offset + i)];
/* 2999:3698 */       if (Character.isWhitespace(c))
/* 3000:     */       {
/* 3001:3699 */         i--;
/* 3002:     */       }
/* 3003:     */       else
/* 3004:     */       {
/* 3005:3701 */         if ((c == endMarker.charAt(l2 - 1)) && (endMarker.equals(new String(buffer.ch, buffer.offset + i - l2 + 1, l2))))
/* 3006:     */         {
/* 3007:3704 */           buffer.length = (buffer.offset + i - startContent - 2);
/* 3008:3705 */           buffer.offset = startContent;
/* 3009:3706 */           return;
/* 3010:     */         }
/* 3011:3709 */         return;
/* 3012:     */       }
/* 3013:     */     }
/* 3014:     */   }
/* 3015:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLScanner
 * JD-Core Version:    0.7.0.1
 */