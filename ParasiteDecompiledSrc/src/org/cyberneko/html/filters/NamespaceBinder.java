/*   1:    */ package org.cyberneko.html.filters;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.xerces.xni.Augmentations;
/*   6:    */ import org.apache.xerces.xni.NamespaceContext;
/*   7:    */ import org.apache.xerces.xni.QName;
/*   8:    */ import org.apache.xerces.xni.XMLAttributes;
/*   9:    */ import org.apache.xerces.xni.XMLLocator;
/*  10:    */ import org.apache.xerces.xni.XNIException;
/*  11:    */ import org.apache.xerces.xni.parser.XMLComponentManager;
/*  12:    */ import org.apache.xerces.xni.parser.XMLConfigurationException;
/*  13:    */ import org.cyberneko.html.HTMLElements;
/*  14:    */ import org.cyberneko.html.xercesbridge.XercesBridge;
/*  15:    */ 
/*  16:    */ public class NamespaceBinder
/*  17:    */   extends DefaultFilter
/*  18:    */ {
/*  19:    */   public static final String XHTML_1_0_URI = "http://www.w3.org/1999/xhtml";
/*  20:    */   public static final String XML_URI = "http://www.w3.org/XML/1998/namespace";
/*  21:    */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
/*  22:    */   protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
/*  23:    */   protected static final String OVERRIDE_NAMESPACES = "http://cyberneko.org/html/features/override-namespaces";
/*  24:    */   protected static final String INSERT_NAMESPACES = "http://cyberneko.org/html/features/insert-namespaces";
/*  25: 77 */   private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/namespaces", "http://cyberneko.org/html/features/override-namespaces", "http://cyberneko.org/html/features/insert-namespaces" };
/*  26: 84 */   private static final Boolean[] FEATURE_DEFAULTS = { null, Boolean.FALSE, Boolean.FALSE };
/*  27:    */   protected static final String NAMES_ELEMS = "http://cyberneko.org/html/properties/names/elems";
/*  28:    */   protected static final String NAMES_ATTRS = "http://cyberneko.org/html/properties/names/attrs";
/*  29:    */   protected static final String NAMESPACES_URI = "http://cyberneko.org/html/properties/namespaces-uri";
/*  30:102 */   private static final String[] RECOGNIZED_PROPERTIES = { "http://cyberneko.org/html/properties/names/elems", "http://cyberneko.org/html/properties/names/attrs", "http://cyberneko.org/html/properties/namespaces-uri" };
/*  31:109 */   private static final Object[] PROPERTY_DEFAULTS = { null, null, "http://www.w3.org/1999/xhtml" };
/*  32:    */   protected static final short NAMES_NO_CHANGE = 0;
/*  33:    */   protected static final short NAMES_UPPERCASE = 1;
/*  34:    */   protected static final short NAMES_LOWERCASE = 2;
/*  35:    */   protected boolean fNamespaces;
/*  36:    */   protected boolean fNamespacePrefixes;
/*  37:    */   protected boolean fOverrideNamespaces;
/*  38:    */   protected boolean fInsertNamespaces;
/*  39:    */   protected short fNamesElems;
/*  40:    */   protected short fNamesAttrs;
/*  41:    */   protected String fNamespacesURI;
/*  42:    */   protected final NamespaceSupport fNamespaceContext;
/*  43:    */   private final QName fQName;
/*  44:    */   
/*  45:    */   public NamespaceBinder()
/*  46:    */   {
/*  47:158 */     this.fNamespaceContext = new NamespaceSupport();
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:163 */     this.fQName = new QName();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String[] getRecognizedFeatures()
/*  56:    */   {
/*  57:175 */     return merge(super.getRecognizedFeatures(), RECOGNIZED_FEATURES);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Boolean getFeatureDefault(String featureId)
/*  61:    */   {
/*  62:184 */     for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
/*  63:185 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/*  64:186 */         return FEATURE_DEFAULTS[i];
/*  65:    */       }
/*  66:    */     }
/*  67:189 */     return super.getFeatureDefault(featureId);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String[] getRecognizedProperties()
/*  71:    */   {
/*  72:198 */     return merge(super.getRecognizedProperties(), RECOGNIZED_PROPERTIES);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Object getPropertyDefault(String propertyId)
/*  76:    */   {
/*  77:207 */     for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
/*  78:208 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/*  79:209 */         return PROPERTY_DEFAULTS[i];
/*  80:    */       }
/*  81:    */     }
/*  82:212 */     return super.getPropertyDefault(propertyId);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void reset(XMLComponentManager manager)
/*  86:    */     throws XMLConfigurationException
/*  87:    */   {
/*  88:226 */     super.reset(manager);
/*  89:    */     
/*  90:    */ 
/*  91:229 */     this.fNamespaces = manager.getFeature("http://xml.org/sax/features/namespaces");
/*  92:230 */     this.fOverrideNamespaces = manager.getFeature("http://cyberneko.org/html/features/override-namespaces");
/*  93:231 */     this.fInsertNamespaces = manager.getFeature("http://cyberneko.org/html/features/insert-namespaces");
/*  94:    */     
/*  95:    */ 
/*  96:234 */     this.fNamesElems = getNamesValue(String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/names/elems")));
/*  97:235 */     this.fNamesAttrs = getNamesValue(String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/names/attrs")));
/*  98:236 */     this.fNamespacesURI = String.valueOf(manager.getProperty("http://cyberneko.org/html/properties/namespaces-uri"));
/*  99:    */     
/* 100:    */ 
/* 101:239 */     this.fNamespaceContext.reset();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/* 105:    */     throws XNIException
/* 106:    */   {
/* 107:254 */     super.startDocument(locator, encoding, this.fNamespaceContext, augs);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void startElement(QName element, XMLAttributes attrs, Augmentations augs)
/* 111:    */     throws XNIException
/* 112:    */   {
/* 113:263 */     if (this.fNamespaces)
/* 114:    */     {
/* 115:264 */       this.fNamespaceContext.pushContext();
/* 116:265 */       bindNamespaces(element, attrs);
/* 117:    */       
/* 118:267 */       int dcount = this.fNamespaceContext.getDeclaredPrefixCount();
/* 119:268 */       if ((this.fDocumentHandler != null) && (dcount > 0)) {
/* 120:269 */         for (int i = 0; i < dcount; i++)
/* 121:    */         {
/* 122:270 */           String prefix = this.fNamespaceContext.getDeclaredPrefixAt(i);
/* 123:271 */           String uri = this.fNamespaceContext.getURI(prefix);
/* 124:272 */           XercesBridge.getInstance().XMLDocumentHandler_startPrefixMapping(this.fDocumentHandler, prefix, uri, augs);
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:278 */     super.startElement(element, attrs, augs);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void emptyElement(QName element, XMLAttributes attrs, Augmentations augs)
/* 132:    */     throws XNIException
/* 133:    */   {
/* 134:287 */     if (this.fNamespaces)
/* 135:    */     {
/* 136:288 */       this.fNamespaceContext.pushContext();
/* 137:289 */       bindNamespaces(element, attrs);
/* 138:    */       
/* 139:291 */       int dcount = this.fNamespaceContext.getDeclaredPrefixCount();
/* 140:292 */       if ((this.fDocumentHandler != null) && (dcount > 0)) {
/* 141:293 */         for (int i = 0; i < dcount; i++)
/* 142:    */         {
/* 143:294 */           String prefix = this.fNamespaceContext.getDeclaredPrefixAt(i);
/* 144:295 */           String uri = this.fNamespaceContext.getURI(prefix);
/* 145:296 */           XercesBridge.getInstance().XMLDocumentHandler_startPrefixMapping(this.fDocumentHandler, prefix, uri, augs);
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:302 */     super.emptyElement(element, attrs, augs);
/* 150:305 */     if (this.fNamespaces)
/* 151:    */     {
/* 152:306 */       int dcount = this.fNamespaceContext.getDeclaredPrefixCount();
/* 153:307 */       if ((this.fDocumentHandler != null) && (dcount > 0)) {
/* 154:308 */         for (int i = dcount - 1; i >= 0; i--)
/* 155:    */         {
/* 156:309 */           String prefix = this.fNamespaceContext.getDeclaredPrefixAt(i);
/* 157:310 */           XercesBridge.getInstance().XMLDocumentHandler_endPrefixMapping(this.fDocumentHandler, prefix, augs);
/* 158:    */         }
/* 159:    */       }
/* 160:314 */       this.fNamespaceContext.popContext();
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void endElement(QName element, Augmentations augs)
/* 165:    */     throws XNIException
/* 166:    */   {
/* 167:324 */     if (this.fNamespaces) {
/* 168:325 */       bindNamespaces(element, null);
/* 169:    */     }
/* 170:329 */     super.endElement(element, augs);
/* 171:332 */     if (this.fNamespaces)
/* 172:    */     {
/* 173:333 */       int dcount = this.fNamespaceContext.getDeclaredPrefixCount();
/* 174:334 */       if ((this.fDocumentHandler != null) && (dcount > 0)) {
/* 175:335 */         for (int i = dcount - 1; i >= 0; i--)
/* 176:    */         {
/* 177:336 */           String prefix = this.fNamespaceContext.getDeclaredPrefixAt(i);
/* 178:337 */           XercesBridge.getInstance().XMLDocumentHandler_endPrefixMapping(this.fDocumentHandler, prefix, augs);
/* 179:    */         }
/* 180:    */       }
/* 181:341 */       this.fNamespaceContext.popContext();
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   protected static void splitQName(QName qname)
/* 186:    */   {
/* 187:352 */     int index = qname.rawname.indexOf(':');
/* 188:353 */     if (index != -1)
/* 189:    */     {
/* 190:354 */       qname.prefix = qname.rawname.substring(0, index);
/* 191:355 */       qname.localpart = qname.rawname.substring(index + 1);
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected static final short getNamesValue(String value)
/* 196:    */   {
/* 197:367 */     if (value.equals("lower")) {
/* 198:367 */       return 2;
/* 199:    */     }
/* 200:368 */     if (value.equals("upper")) {
/* 201:368 */       return 1;
/* 202:    */     }
/* 203:369 */     return 0;
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected static final String modifyName(String name, short mode)
/* 207:    */   {
/* 208:374 */     switch (mode)
/* 209:    */     {
/* 210:    */     case 1: 
/* 211:375 */       return name.toUpperCase();
/* 212:    */     case 2: 
/* 213:376 */       return name.toLowerCase();
/* 214:    */     }
/* 215:378 */     return name;
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected void bindNamespaces(QName element, XMLAttributes attrs)
/* 219:    */   {
/* 220:389 */     splitQName(element);
/* 221:    */     
/* 222:    */ 
/* 223:392 */     int attrCount = attrs != null ? attrs.getLength() : 0;
/* 224:393 */     for (int i = attrCount - 1; i >= 0; i--)
/* 225:    */     {
/* 226:394 */       attrs.getName(i, this.fQName);
/* 227:395 */       String aname = this.fQName.rawname;
/* 228:396 */       String ANAME = aname.toUpperCase();
/* 229:397 */       if ((ANAME.startsWith("XMLNS:")) || (ANAME.equals("XMLNS")))
/* 230:    */       {
/* 231:398 */         int anamelen = aname.length();
/* 232:    */         
/* 233:    */ 
/* 234:401 */         String aprefix = anamelen > 5 ? aname.substring(0, 5) : null;
/* 235:402 */         String alocal = anamelen > 5 ? aname.substring(6) : aname;
/* 236:403 */         String avalue = attrs.getValue(i);
/* 237:406 */         if (anamelen > 5)
/* 238:    */         {
/* 239:407 */           aprefix = modifyName(aprefix, (short)2);
/* 240:408 */           alocal = modifyName(alocal, this.fNamesElems);
/* 241:409 */           aname = aprefix + ':' + alocal;
/* 242:    */         }
/* 243:    */         else
/* 244:    */         {
/* 245:412 */           alocal = modifyName(alocal, (short)2);
/* 246:413 */           aname = alocal;
/* 247:    */         }
/* 248:415 */         this.fQName.setValues(aprefix, alocal, aname, null);
/* 249:416 */         attrs.setName(i, this.fQName);
/* 250:    */         
/* 251:    */ 
/* 252:419 */         String prefix = alocal != aname ? alocal : "";
/* 253:420 */         String uri = avalue.length() > 0 ? avalue : null;
/* 254:421 */         if ((this.fOverrideNamespaces) && (prefix.equals(element.prefix)) && (HTMLElements.getElement(element.localpart, null) != null)) {
/* 255:424 */           uri = this.fNamespacesURI;
/* 256:    */         }
/* 257:426 */         this.fNamespaceContext.declarePrefix(prefix, uri);
/* 258:    */       }
/* 259:    */     }
/* 260:431 */     String prefix = element.prefix != null ? element.prefix : "";
/* 261:432 */     element.uri = this.fNamespaceContext.getURI(prefix);
/* 262:438 */     if ((element.uri != null) && (element.prefix == null)) {
/* 263:439 */       element.prefix = "";
/* 264:    */     }
/* 265:443 */     if ((this.fInsertNamespaces) && (attrs != null) && (HTMLElements.getElement(element.localpart, null) != null)) {
/* 266:445 */       if ((element.prefix == null) || (this.fNamespaceContext.getURI(element.prefix) == null))
/* 267:    */       {
/* 268:447 */         String xmlns = "xmlns" + (element.prefix != null ? ":" + element.prefix : "");
/* 269:    */         
/* 270:449 */         this.fQName.setValues(null, xmlns, xmlns, null);
/* 271:450 */         attrs.addAttribute(this.fQName, "CDATA", this.fNamespacesURI);
/* 272:451 */         bindNamespaces(element, attrs);
/* 273:452 */         return;
/* 274:    */       }
/* 275:    */     }
/* 276:457 */     attrCount = attrs != null ? attrs.getLength() : 0;
/* 277:458 */     for (int i = 0; i < attrCount; i++)
/* 278:    */     {
/* 279:459 */       attrs.getName(i, this.fQName);
/* 280:460 */       splitQName(this.fQName);
/* 281:461 */       prefix = !this.fQName.rawname.equals("xmlns") ? "" : this.fQName.prefix != null ? this.fQName.prefix : "xmlns";
/* 282:464 */       if (!prefix.equals("")) {
/* 283:465 */         this.fQName.uri = (prefix.equals("xml") ? "http://www.w3.org/XML/1998/namespace" : this.fNamespaceContext.getURI(prefix));
/* 284:    */       }
/* 285:469 */       if ((prefix.equals("xmlns")) && (this.fQName.uri == null)) {
/* 286:470 */         this.fQName.uri = "http://www.w3.org/2000/xmlns/";
/* 287:    */       }
/* 288:472 */       attrs.setName(i, this.fQName);
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public static class NamespaceSupport
/* 293:    */     implements NamespaceContext
/* 294:    */   {
/* 295:494 */     protected int fTop = 0;
/* 296:497 */     protected int[] fLevels = new int[10];
/* 297:500 */     protected Entry[] fEntries = new Entry[10];
/* 298:    */     
/* 299:    */     public NamespaceSupport()
/* 300:    */     {
/* 301:508 */       pushContext();
/* 302:509 */       declarePrefix("xml", NamespaceContext.XML_URI);
/* 303:510 */       declarePrefix("xmlns", NamespaceContext.XMLNS_URI);
/* 304:    */     }
/* 305:    */     
/* 306:    */     public String getURI(String prefix)
/* 307:    */     {
/* 308:521 */       for (int i = this.fLevels[this.fTop] - 1; i >= 0; i--)
/* 309:    */       {
/* 310:522 */         Entry entry = this.fEntries[i];
/* 311:523 */         if (entry.prefix.equals(prefix)) {
/* 312:524 */           return entry.uri;
/* 313:    */         }
/* 314:    */       }
/* 315:527 */       return null;
/* 316:    */     }
/* 317:    */     
/* 318:    */     public int getDeclaredPrefixCount()
/* 319:    */     {
/* 320:532 */       return this.fLevels[this.fTop] - this.fLevels[(this.fTop - 1)];
/* 321:    */     }
/* 322:    */     
/* 323:    */     public String getDeclaredPrefixAt(int index)
/* 324:    */     {
/* 325:537 */       return this.fEntries[(this.fLevels[(this.fTop - 1)] + index)].prefix;
/* 326:    */     }
/* 327:    */     
/* 328:    */     public NamespaceContext getParentContext()
/* 329:    */     {
/* 330:542 */       return this;
/* 331:    */     }
/* 332:    */     
/* 333:    */     public void reset()
/* 334:    */     {
/* 335:549 */       int tmp6_5 = 1;this.fTop = tmp6_5;this.fLevels[tmp6_5] = this.fLevels[(this.fTop - 1)];
/* 336:    */     }
/* 337:    */     
/* 338:    */     public void pushContext()
/* 339:    */     {
/* 340:554 */       if (++this.fTop == this.fLevels.length)
/* 341:    */       {
/* 342:555 */         int[] iarray = new int[this.fLevels.length + 10];
/* 343:556 */         System.arraycopy(this.fLevels, 0, iarray, 0, this.fLevels.length);
/* 344:557 */         this.fLevels = iarray;
/* 345:    */       }
/* 346:559 */       this.fLevels[this.fTop] = this.fLevels[(this.fTop - 1)];
/* 347:    */     }
/* 348:    */     
/* 349:    */     public void popContext()
/* 350:    */     {
/* 351:564 */       if (this.fTop > 1) {
/* 352:565 */         this.fTop -= 1;
/* 353:    */       }
/* 354:    */     }
/* 355:    */     
/* 356:    */     public boolean declarePrefix(String prefix, String uri)
/* 357:    */     {
/* 358:571 */       int count = getDeclaredPrefixCount();
/* 359:572 */       for (int i = 0; i < count; i++)
/* 360:    */       {
/* 361:573 */         String dprefix = getDeclaredPrefixAt(i);
/* 362:574 */         if (dprefix.equals(prefix)) {
/* 363:575 */           return false;
/* 364:    */         }
/* 365:    */       }
/* 366:578 */       Entry entry = new Entry(prefix, uri);
/* 367:579 */       if (this.fLevels[this.fTop] == this.fEntries.length)
/* 368:    */       {
/* 369:580 */         Entry[] earray = new Entry[this.fEntries.length + 10];
/* 370:581 */         System.arraycopy(this.fEntries, 0, earray, 0, this.fEntries.length);
/* 371:582 */         this.fEntries = earray;
/* 372:    */       }
/* 373:584 */       int tmp114_111 = this.fTop; int[] tmp114_107 = this.fLevels; int tmp116_115 = tmp114_107[tmp114_111];tmp114_107[tmp114_111] = (tmp116_115 + 1);this.fEntries[tmp116_115] = entry;
/* 374:585 */       return true;
/* 375:    */     }
/* 376:    */     
/* 377:    */     public String getPrefix(String uri)
/* 378:    */     {
/* 379:590 */       for (int i = this.fLevels[this.fTop] - 1; i >= 0; i--)
/* 380:    */       {
/* 381:591 */         Entry entry = this.fEntries[i];
/* 382:592 */         if (entry.uri.equals(uri)) {
/* 383:593 */           return entry.prefix;
/* 384:    */         }
/* 385:    */       }
/* 386:596 */       return null;
/* 387:    */     }
/* 388:    */     
/* 389:    */     public Enumeration getAllPrefixes()
/* 390:    */     {
/* 391:601 */       Vector prefixes = new Vector();
/* 392:602 */       for (int i = this.fLevels[1]; i < this.fLevels[this.fTop]; i++)
/* 393:    */       {
/* 394:603 */         String prefix = this.fEntries[i].prefix;
/* 395:604 */         if (!prefixes.contains(prefix)) {
/* 396:605 */           prefixes.addElement(prefix);
/* 397:    */         }
/* 398:    */       }
/* 399:608 */       return prefixes.elements();
/* 400:    */     }
/* 401:    */     
/* 402:    */     static class Entry
/* 403:    */     {
/* 404:    */       public String prefix;
/* 405:    */       public String uri;
/* 406:    */       
/* 407:    */       public Entry(String prefix, String uri)
/* 408:    */       {
/* 409:634 */         this.prefix = prefix;
/* 410:635 */         this.uri = uri;
/* 411:    */       }
/* 412:    */     }
/* 413:    */   }
/* 414:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.filters.NamespaceBinder
 * JD-Core Version:    0.7.0.1
 */