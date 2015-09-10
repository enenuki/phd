/*   1:    */ package org.cyberneko.html;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.text.MessageFormat;
/*   6:    */ import java.util.Locale;
/*   7:    */ import java.util.MissingResourceException;
/*   8:    */ import java.util.ResourceBundle;
/*   9:    */ import java.util.Vector;
/*  10:    */ import org.apache.xerces.util.DefaultErrorHandler;
/*  11:    */ import org.apache.xerces.util.ParserConfigurationSettings;
/*  12:    */ import org.apache.xerces.xni.XMLDTDContentModelHandler;
/*  13:    */ import org.apache.xerces.xni.XMLDTDHandler;
/*  14:    */ import org.apache.xerces.xni.XMLDocumentHandler;
/*  15:    */ import org.apache.xerces.xni.XNIException;
/*  16:    */ import org.apache.xerces.xni.parser.XMLConfigurationException;
/*  17:    */ import org.apache.xerces.xni.parser.XMLDocumentFilter;
/*  18:    */ import org.apache.xerces.xni.parser.XMLDocumentSource;
/*  19:    */ import org.apache.xerces.xni.parser.XMLEntityResolver;
/*  20:    */ import org.apache.xerces.xni.parser.XMLErrorHandler;
/*  21:    */ import org.apache.xerces.xni.parser.XMLInputSource;
/*  22:    */ import org.apache.xerces.xni.parser.XMLParseException;
/*  23:    */ import org.apache.xerces.xni.parser.XMLPullParserConfiguration;
/*  24:    */ import org.cyberneko.html.filters.NamespaceBinder;
/*  25:    */ import org.cyberneko.html.xercesbridge.XercesBridge;
/*  26:    */ 
/*  27:    */ public class HTMLConfiguration
/*  28:    */   extends ParserConfigurationSettings
/*  29:    */   implements XMLPullParserConfiguration
/*  30:    */ {
/*  31:    */   protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
/*  32:    */   protected static final String AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";
/*  33:    */   protected static final String REPORT_ERRORS = "http://cyberneko.org/html/features/report-errors";
/*  34:    */   protected static final String SIMPLE_ERROR_FORMAT = "http://cyberneko.org/html/features/report-errors/simple";
/*  35:    */   protected static final String BALANCE_TAGS = "http://cyberneko.org/html/features/balance-tags";
/*  36:    */   protected static final String NAMES_ELEMS = "http://cyberneko.org/html/properties/names/elems";
/*  37:    */   protected static final String NAMES_ATTRS = "http://cyberneko.org/html/properties/names/attrs";
/*  38:    */   protected static final String FILTERS = "http://cyberneko.org/html/properties/filters";
/*  39:    */   protected static final String ERROR_REPORTER = "http://cyberneko.org/html/properties/error-reporter";
/*  40:    */   protected static final String ERROR_DOMAIN = "http://cyberneko.org/html";
/*  41:126 */   private static final Class[] DOCSOURCE = { XMLDocumentSource.class };
/*  42:    */   protected XMLDocumentHandler fDocumentHandler;
/*  43:    */   protected XMLDTDHandler fDTDHandler;
/*  44:    */   protected XMLDTDContentModelHandler fDTDContentModelHandler;
/*  45:144 */   protected XMLErrorHandler fErrorHandler = new DefaultErrorHandler();
/*  46:    */   protected XMLEntityResolver fEntityResolver;
/*  47:152 */   protected Locale fLocale = Locale.getDefault();
/*  48:    */   protected boolean fCloseStream;
/*  49:165 */   protected final Vector fHTMLComponents = new Vector(2);
/*  50:170 */   protected final HTMLScanner fDocumentScanner = createDocumentScanner();
/*  51:173 */   protected final HTMLTagBalancer fTagBalancer = new HTMLTagBalancer();
/*  52:176 */   protected final NamespaceBinder fNamespaceBinder = new NamespaceBinder();
/*  53:181 */   protected final HTMLErrorReporter fErrorReporter = new ErrorReporter();
/*  54:186 */   protected static boolean XERCES_2_0_0 = false;
/*  55:189 */   protected static boolean XERCES_2_0_1 = false;
/*  56:192 */   protected static boolean XML4J_4_0_x = false;
/*  57:    */   
/*  58:    */   static
/*  59:    */   {
/*  60:    */     try
/*  61:    */     {
/*  62:200 */       String VERSION = "org.apache.xerces.impl.Version";
/*  63:201 */       Object version = ObjectFactory.createObject(VERSION, VERSION);
/*  64:202 */       Field field = version.getClass().getField("fVersion");
/*  65:203 */       String versionStr = String.valueOf(field.get(version));
/*  66:204 */       XERCES_2_0_0 = versionStr.equals("Xerces-J 2.0.0");
/*  67:205 */       XERCES_2_0_1 = versionStr.equals("Xerces-J 2.0.1");
/*  68:206 */       XML4J_4_0_x = versionStr.startsWith("XML4J 4.0.");
/*  69:    */     }
/*  70:    */     catch (Throwable e) {}
/*  71:    */   }
/*  72:    */   
/*  73:    */   public HTMLConfiguration()
/*  74:    */   {
/*  75:221 */     addComponent(this.fDocumentScanner);
/*  76:222 */     addComponent(this.fTagBalancer);
/*  77:223 */     addComponent(this.fNamespaceBinder);
/*  78:    */     
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:230 */     String VALIDATION = "http://xml.org/sax/features/validation";
/*  85:231 */     String[] recognizedFeatures = { "http://cyberneko.org/html/features/augmentations", "http://xml.org/sax/features/namespaces", VALIDATION, "http://cyberneko.org/html/features/report-errors", "http://cyberneko.org/html/features/report-errors/simple", "http://cyberneko.org/html/features/balance-tags" };
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:239 */     addRecognizedFeatures(recognizedFeatures);
/*  94:240 */     setFeature("http://cyberneko.org/html/features/augmentations", false);
/*  95:241 */     setFeature("http://xml.org/sax/features/namespaces", true);
/*  96:242 */     setFeature(VALIDATION, false);
/*  97:243 */     setFeature("http://cyberneko.org/html/features/report-errors", false);
/*  98:244 */     setFeature("http://cyberneko.org/html/features/report-errors/simple", false);
/*  99:245 */     setFeature("http://cyberneko.org/html/features/balance-tags", true);
/* 100:248 */     if (XERCES_2_0_0)
/* 101:    */     {
/* 102:252 */       recognizedFeatures = new String[] { "http://apache.org/xml/features/scanner/notify-builtin-refs" };
/* 103:    */       
/* 104:    */ 
/* 105:255 */       addRecognizedFeatures(recognizedFeatures);
/* 106:    */     }
/* 107:259 */     if ((XERCES_2_0_0) || (XERCES_2_0_1) || (XML4J_4_0_x))
/* 108:    */     {
/* 109:263 */       recognizedFeatures = new String[] { "http://apache.org/xml/features/validation/schema/normalized-value", "http://apache.org/xml/features/scanner/notify-char-refs" };
/* 110:    */       
/* 111:    */ 
/* 112:    */ 
/* 113:267 */       addRecognizedFeatures(recognizedFeatures);
/* 114:    */     }
/* 115:275 */     String[] recognizedProperties = { "http://cyberneko.org/html/properties/names/elems", "http://cyberneko.org/html/properties/names/attrs", "http://cyberneko.org/html/properties/filters", "http://cyberneko.org/html/properties/error-reporter" };
/* 116:    */     
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:281 */     addRecognizedProperties(recognizedProperties);
/* 122:282 */     setProperty("http://cyberneko.org/html/properties/names/elems", "upper");
/* 123:283 */     setProperty("http://cyberneko.org/html/properties/names/attrs", "lower");
/* 124:284 */     setProperty("http://cyberneko.org/html/properties/error-reporter", this.fErrorReporter);
/* 125:287 */     if (XERCES_2_0_0)
/* 126:    */     {
/* 127:293 */       String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/* 128:294 */       recognizedProperties = new String[] { SYMBOL_TABLE };
/* 129:    */       
/* 130:    */ 
/* 131:297 */       addRecognizedProperties(recognizedProperties);
/* 132:298 */       Object symbolTable = ObjectFactory.createObject("org.apache.xerces.util.SymbolTable", "org.apache.xerces.util.SymbolTable");
/* 133:    */       
/* 134:300 */       setProperty(SYMBOL_TABLE, symbolTable);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected HTMLScanner createDocumentScanner()
/* 139:    */   {
/* 140:306 */     return new HTMLScanner();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void pushInputSource(XMLInputSource inputSource)
/* 144:    */   {
/* 145:330 */     this.fDocumentScanner.pushInputSource(inputSource);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void evaluateInputSource(XMLInputSource inputSource)
/* 149:    */   {
/* 150:342 */     this.fDocumentScanner.evaluateInputSource(inputSource);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setFeature(String featureId, boolean state)
/* 154:    */     throws XMLConfigurationException
/* 155:    */   {
/* 156:351 */     super.setFeature(featureId, state);
/* 157:352 */     int size = this.fHTMLComponents.size();
/* 158:353 */     for (int i = 0; i < size; i++)
/* 159:    */     {
/* 160:354 */       HTMLComponent component = (HTMLComponent)this.fHTMLComponents.elementAt(i);
/* 161:355 */       component.setFeature(featureId, state);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setProperty(String propertyId, Object value)
/* 166:    */     throws XMLConfigurationException
/* 167:    */   {
/* 168:362 */     super.setProperty(propertyId, value);
/* 169:364 */     if (propertyId.equals("http://cyberneko.org/html/properties/filters"))
/* 170:    */     {
/* 171:365 */       XMLDocumentFilter[] filters = (XMLDocumentFilter[])getProperty("http://cyberneko.org/html/properties/filters");
/* 172:366 */       if (filters != null) {
/* 173:367 */         for (int i = 0; i < filters.length; i++)
/* 174:    */         {
/* 175:368 */           XMLDocumentFilter filter = filters[i];
/* 176:369 */           if ((filter instanceof HTMLComponent)) {
/* 177:370 */             addComponent((HTMLComponent)filter);
/* 178:    */           }
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:376 */     int size = this.fHTMLComponents.size();
/* 183:377 */     for (int i = 0; i < size; i++)
/* 184:    */     {
/* 185:378 */       HTMLComponent component = (HTMLComponent)this.fHTMLComponents.elementAt(i);
/* 186:379 */       component.setProperty(propertyId, value);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setDocumentHandler(XMLDocumentHandler handler)
/* 191:    */   {
/* 192:385 */     this.fDocumentHandler = handler;
/* 193:386 */     if ((handler instanceof HTMLTagBalancingListener)) {
/* 194:387 */       this.fTagBalancer.setTagBalancingListener((HTMLTagBalancingListener)handler);
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public XMLDocumentHandler getDocumentHandler()
/* 199:    */   {
/* 200:393 */     return this.fDocumentHandler;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setDTDHandler(XMLDTDHandler handler)
/* 204:    */   {
/* 205:398 */     this.fDTDHandler = handler;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public XMLDTDHandler getDTDHandler()
/* 209:    */   {
/* 210:403 */     return this.fDTDHandler;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setDTDContentModelHandler(XMLDTDContentModelHandler handler)
/* 214:    */   {
/* 215:408 */     this.fDTDContentModelHandler = handler;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public XMLDTDContentModelHandler getDTDContentModelHandler()
/* 219:    */   {
/* 220:413 */     return this.fDTDContentModelHandler;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setErrorHandler(XMLErrorHandler handler)
/* 224:    */   {
/* 225:418 */     this.fErrorHandler = handler;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public XMLErrorHandler getErrorHandler()
/* 229:    */   {
/* 230:423 */     return this.fErrorHandler;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setEntityResolver(XMLEntityResolver resolver)
/* 234:    */   {
/* 235:428 */     this.fEntityResolver = resolver;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public XMLEntityResolver getEntityResolver()
/* 239:    */   {
/* 240:433 */     return this.fEntityResolver;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setLocale(Locale locale)
/* 244:    */   {
/* 245:438 */     if (locale == null) {
/* 246:439 */       locale = Locale.getDefault();
/* 247:    */     }
/* 248:441 */     this.fLocale = locale;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public Locale getLocale()
/* 252:    */   {
/* 253:446 */     return this.fLocale;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void parse(XMLInputSource source)
/* 257:    */     throws XNIException, IOException
/* 258:    */   {
/* 259:451 */     setInputSource(source);
/* 260:452 */     parse(true);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setInputSource(XMLInputSource inputSource)
/* 264:    */     throws XMLConfigurationException, IOException
/* 265:    */   {
/* 266:475 */     reset();
/* 267:476 */     this.fCloseStream = ((inputSource.getByteStream() == null) && (inputSource.getCharacterStream() == null));
/* 268:    */     
/* 269:478 */     this.fDocumentScanner.setInputSource(inputSource);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public boolean parse(boolean complete)
/* 273:    */     throws XNIException, IOException
/* 274:    */   {
/* 275:    */     try
/* 276:    */     {
/* 277:499 */       boolean more = this.fDocumentScanner.scanDocument(complete);
/* 278:500 */       if (!more) {
/* 279:501 */         cleanup();
/* 280:    */       }
/* 281:503 */       return more;
/* 282:    */     }
/* 283:    */     catch (XNIException e)
/* 284:    */     {
/* 285:506 */       cleanup();
/* 286:507 */       throw e;
/* 287:    */     }
/* 288:    */     catch (IOException e)
/* 289:    */     {
/* 290:510 */       cleanup();
/* 291:511 */       throw e;
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void cleanup()
/* 296:    */   {
/* 297:521 */     this.fDocumentScanner.cleanup(this.fCloseStream);
/* 298:    */   }
/* 299:    */   
/* 300:    */   protected void addComponent(HTMLComponent component)
/* 301:    */   {
/* 302:532 */     this.fHTMLComponents.addElement(component);
/* 303:    */     
/* 304:    */ 
/* 305:535 */     String[] features = component.getRecognizedFeatures();
/* 306:536 */     addRecognizedFeatures(features);
/* 307:537 */     int featureCount = features != null ? features.length : 0;
/* 308:538 */     for (int i = 0; i < featureCount; i++)
/* 309:    */     {
/* 310:539 */       Boolean state = component.getFeatureDefault(features[i]);
/* 311:540 */       if (state != null) {
/* 312:541 */         setFeature(features[i], state.booleanValue());
/* 313:    */       }
/* 314:    */     }
/* 315:546 */     String[] properties = component.getRecognizedProperties();
/* 316:547 */     addRecognizedProperties(properties);
/* 317:548 */     int propertyCount = properties != null ? properties.length : 0;
/* 318:549 */     for (int i = 0; i < propertyCount; i++)
/* 319:    */     {
/* 320:550 */       Object value = component.getPropertyDefault(properties[i]);
/* 321:551 */       if (value != null) {
/* 322:552 */         setProperty(properties[i], value);
/* 323:    */       }
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   protected void reset()
/* 328:    */     throws XMLConfigurationException
/* 329:    */   {
/* 330:562 */     int size = this.fHTMLComponents.size();
/* 331:563 */     for (int i = 0; i < size; i++)
/* 332:    */     {
/* 333:564 */       HTMLComponent component = (HTMLComponent)this.fHTMLComponents.elementAt(i);
/* 334:565 */       component.reset(this);
/* 335:    */     }
/* 336:569 */     XMLDocumentSource lastSource = this.fDocumentScanner;
/* 337:570 */     if (getFeature("http://xml.org/sax/features/namespaces"))
/* 338:    */     {
/* 339:571 */       lastSource.setDocumentHandler(this.fNamespaceBinder);
/* 340:572 */       this.fNamespaceBinder.setDocumentSource(this.fTagBalancer);
/* 341:573 */       lastSource = this.fNamespaceBinder;
/* 342:    */     }
/* 343:575 */     if (getFeature("http://cyberneko.org/html/features/balance-tags"))
/* 344:    */     {
/* 345:576 */       lastSource.setDocumentHandler(this.fTagBalancer);
/* 346:577 */       this.fTagBalancer.setDocumentSource(this.fDocumentScanner);
/* 347:578 */       lastSource = this.fTagBalancer;
/* 348:    */     }
/* 349:580 */     XMLDocumentFilter[] filters = (XMLDocumentFilter[])getProperty("http://cyberneko.org/html/properties/filters");
/* 350:581 */     if (filters != null) {
/* 351:582 */       for (int i = 0; i < filters.length; i++)
/* 352:    */       {
/* 353:583 */         XMLDocumentFilter filter = filters[i];
/* 354:584 */         XercesBridge.getInstance().XMLDocumentFilter_setDocumentSource(filter, lastSource);
/* 355:585 */         lastSource.setDocumentHandler(filter);
/* 356:586 */         lastSource = filter;
/* 357:    */       }
/* 358:    */     }
/* 359:589 */     lastSource.setDocumentHandler(this.fDocumentHandler);
/* 360:    */   }
/* 361:    */   
/* 362:    */   protected class ErrorReporter
/* 363:    */     implements HTMLErrorReporter
/* 364:    */   {
/* 365:    */     protected Locale fLastLocale;
/* 366:    */     protected ResourceBundle fErrorMessages;
/* 367:    */     
/* 368:    */     protected ErrorReporter() {}
/* 369:    */     
/* 370:    */     public String formatMessage(String key, Object[] args)
/* 371:    */     {
/* 372:634 */       if (!HTMLConfiguration.this.getFeature("http://cyberneko.org/html/features/report-errors/simple"))
/* 373:    */       {
/* 374:635 */         if (!HTMLConfiguration.this.fLocale.equals(this.fLastLocale))
/* 375:    */         {
/* 376:636 */           this.fErrorMessages = null;
/* 377:637 */           this.fLastLocale = HTMLConfiguration.this.fLocale;
/* 378:    */         }
/* 379:639 */         if (this.fErrorMessages == null) {
/* 380:640 */           this.fErrorMessages = ResourceBundle.getBundle("org/cyberneko/html/res/ErrorMessages", HTMLConfiguration.this.fLocale);
/* 381:    */         }
/* 382:    */         try
/* 383:    */         {
/* 384:645 */           String value = this.fErrorMessages.getString(key);
/* 385:646 */           return MessageFormat.format(value, args);
/* 386:    */         }
/* 387:    */         catch (MissingResourceException e) {}
/* 388:    */       }
/* 389:653 */       return formatSimpleMessage(key, args);
/* 390:    */     }
/* 391:    */     
/* 392:    */     public void reportWarning(String key, Object[] args)
/* 393:    */       throws XMLParseException
/* 394:    */     {
/* 395:659 */       if (HTMLConfiguration.this.fErrorHandler != null) {
/* 396:660 */         HTMLConfiguration.this.fErrorHandler.warning("http://cyberneko.org/html", key, createException(key, args));
/* 397:    */       }
/* 398:    */     }
/* 399:    */     
/* 400:    */     public void reportError(String key, Object[] args)
/* 401:    */       throws XMLParseException
/* 402:    */     {
/* 403:667 */       if (HTMLConfiguration.this.fErrorHandler != null) {
/* 404:668 */         HTMLConfiguration.this.fErrorHandler.error("http://cyberneko.org/html", key, createException(key, args));
/* 405:    */       }
/* 406:    */     }
/* 407:    */     
/* 408:    */     protected XMLParseException createException(String key, Object[] args)
/* 409:    */     {
/* 410:678 */       String message = formatMessage(key, args);
/* 411:679 */       return new XMLParseException(HTMLConfiguration.this.fDocumentScanner, message);
/* 412:    */     }
/* 413:    */     
/* 414:    */     protected String formatSimpleMessage(String key, Object[] args)
/* 415:    */     {
/* 416:684 */       StringBuffer str = new StringBuffer();
/* 417:685 */       str.append("http://cyberneko.org/html");
/* 418:686 */       str.append('#');
/* 419:687 */       str.append(key);
/* 420:688 */       if ((args != null) && (args.length > 0))
/* 421:    */       {
/* 422:689 */         str.append('\t');
/* 423:690 */         for (int i = 0; i < args.length; i++)
/* 424:    */         {
/* 425:691 */           if (i > 0) {
/* 426:692 */             str.append('\t');
/* 427:    */           }
/* 428:694 */           str.append(String.valueOf(args[i]));
/* 429:    */         }
/* 430:    */       }
/* 431:697 */       return str.toString();
/* 432:    */     }
/* 433:    */   }
/* 434:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLConfiguration
 * JD-Core Version:    0.7.0.1
 */