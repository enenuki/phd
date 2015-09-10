/*   1:    */ package org.cyberneko.html.filters;
/*   2:    */ 
/*   3:    */ import java.io.OutputStream;
/*   4:    */ import java.io.OutputStreamWriter;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.io.UnsupportedEncodingException;
/*   8:    */ import java.util.Vector;
/*   9:    */ import org.apache.xerces.xni.Augmentations;
/*  10:    */ import org.apache.xerces.xni.NamespaceContext;
/*  11:    */ import org.apache.xerces.xni.QName;
/*  12:    */ import org.apache.xerces.xni.XMLAttributes;
/*  13:    */ import org.apache.xerces.xni.XMLLocator;
/*  14:    */ import org.apache.xerces.xni.XMLResourceIdentifier;
/*  15:    */ import org.apache.xerces.xni.XMLString;
/*  16:    */ import org.apache.xerces.xni.XNIException;
/*  17:    */ import org.apache.xerces.xni.parser.XMLDocumentFilter;
/*  18:    */ import org.apache.xerces.xni.parser.XMLInputSource;
/*  19:    */ import org.apache.xerces.xni.parser.XMLParserConfiguration;
/*  20:    */ import org.cyberneko.html.HTMLConfiguration;
/*  21:    */ import org.cyberneko.html.HTMLElements;
/*  22:    */ import org.cyberneko.html.HTMLElements.Element;
/*  23:    */ import org.cyberneko.html.HTMLEntities;
/*  24:    */ 
/*  25:    */ public class Writer
/*  26:    */   extends DefaultFilter
/*  27:    */ {
/*  28:    */   public static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
/*  29:    */   public static final String NOTIFY_HTML_BUILTIN_REFS = "http://cyberneko.org/html/features/scanner/notify-builtin-refs";
/*  30:    */   protected static final String AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";
/*  31:    */   protected static final String FILTERS = "http://cyberneko.org/html/properties/filters";
/*  32:    */   protected String fEncoding;
/*  33:    */   protected PrintWriter fPrinter;
/*  34:    */   protected boolean fSeenRootElement;
/*  35:    */   protected boolean fSeenHttpEquiv;
/*  36:    */   protected int fElementDepth;
/*  37:    */   protected boolean fNormalize;
/*  38:    */   protected boolean fPrintChars;
/*  39:    */   
/*  40:    */   public Writer()
/*  41:    */   {
/*  42:    */     try
/*  43:    */     {
/*  44:121 */       this.fEncoding = "UTF-8";
/*  45:122 */       this.fPrinter = new PrintWriter(new OutputStreamWriter(System.out, this.fEncoding));
/*  46:    */     }
/*  47:    */     catch (UnsupportedEncodingException e)
/*  48:    */     {
/*  49:125 */       throw new RuntimeException(e.getMessage());
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Writer(OutputStream outputStream, String encoding)
/*  54:    */     throws UnsupportedEncodingException
/*  55:    */   {
/*  56:139 */     this(new OutputStreamWriter(outputStream, encoding), encoding);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Writer(java.io.Writer writer, String encoding)
/*  60:    */   {
/*  61:151 */     this.fEncoding = encoding;
/*  62:152 */     if ((writer instanceof PrintWriter)) {
/*  63:153 */       this.fPrinter = ((PrintWriter)writer);
/*  64:    */     } else {
/*  65:156 */       this.fPrinter = new PrintWriter(writer);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/*  70:    */     throws XNIException
/*  71:    */   {
/*  72:170 */     this.fSeenRootElement = false;
/*  73:171 */     this.fSeenHttpEquiv = false;
/*  74:172 */     this.fElementDepth = 0;
/*  75:173 */     this.fNormalize = true;
/*  76:174 */     this.fPrintChars = true;
/*  77:175 */     super.startDocument(locator, encoding, nscontext, augs);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void startDocument(XMLLocator locator, String encoding, Augmentations augs)
/*  81:    */     throws XNIException
/*  82:    */   {
/*  83:183 */     startDocument(locator, encoding, null, augs);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void comment(XMLString text, Augmentations augs)
/*  87:    */     throws XNIException
/*  88:    */   {
/*  89:189 */     if ((this.fSeenRootElement) && (this.fElementDepth <= 0)) {
/*  90:190 */       this.fPrinter.println();
/*  91:    */     }
/*  92:192 */     this.fPrinter.print("<!--");
/*  93:193 */     printCharacters(text, false);
/*  94:194 */     this.fPrinter.print("-->");
/*  95:195 */     if (!this.fSeenRootElement) {
/*  96:196 */       this.fPrinter.println();
/*  97:    */     }
/*  98:198 */     this.fPrinter.flush();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void startElement(QName element, XMLAttributes attributes, Augmentations augs)
/* 102:    */     throws XNIException
/* 103:    */   {
/* 104:204 */     this.fSeenRootElement = true;
/* 105:205 */     this.fElementDepth += 1;
/* 106:206 */     this.fNormalize = (!HTMLElements.getElement(element.rawname).isSpecial());
/* 107:207 */     printStartElement(element, attributes);
/* 108:208 */     super.startElement(element, attributes, augs);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
/* 112:    */     throws XNIException
/* 113:    */   {
/* 114:214 */     this.fSeenRootElement = true;
/* 115:215 */     printStartElement(element, attributes);
/* 116:216 */     super.emptyElement(element, attributes, augs);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void characters(XMLString text, Augmentations augs)
/* 120:    */     throws XNIException
/* 121:    */   {
/* 122:222 */     if (this.fPrintChars) {
/* 123:223 */       printCharacters(text, this.fNormalize);
/* 124:    */     }
/* 125:225 */     super.characters(text, augs);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void endElement(QName element, Augmentations augs)
/* 129:    */     throws XNIException
/* 130:    */   {
/* 131:231 */     this.fElementDepth -= 1;
/* 132:232 */     this.fNormalize = true;
/* 133:    */     
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:249 */     printEndElement(element);
/* 150:250 */     super.endElement(element, augs);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void startGeneralEntity(String name, XMLResourceIdentifier id, String encoding, Augmentations augs)
/* 154:    */     throws XNIException
/* 155:    */   {
/* 156:256 */     this.fPrintChars = false;
/* 157:257 */     if (name.startsWith("#")) {
/* 158:    */       try
/* 159:    */       {
/* 160:259 */         boolean hex = name.startsWith("#x");
/* 161:260 */         int offset = hex ? 2 : 1;
/* 162:261 */         int base = hex ? 16 : 10;
/* 163:262 */         int value = Integer.parseInt(name.substring(offset), base);
/* 164:263 */         String entity = HTMLEntities.get(value);
/* 165:264 */         if (entity != null) {
/* 166:265 */           name = entity;
/* 167:    */         }
/* 168:    */       }
/* 169:    */       catch (NumberFormatException e) {}
/* 170:    */     }
/* 171:272 */     printEntity(name);
/* 172:273 */     super.startGeneralEntity(name, id, encoding, augs);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void endGeneralEntity(String name, Augmentations augs)
/* 176:    */     throws XNIException
/* 177:    */   {
/* 178:279 */     this.fPrintChars = true;
/* 179:280 */     super.endGeneralEntity(name, augs);
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected void printAttributeValue(String text)
/* 183:    */   {
/* 184:289 */     int length = text.length();
/* 185:290 */     for (int j = 0; j < length; j++)
/* 186:    */     {
/* 187:291 */       char c = text.charAt(j);
/* 188:292 */       if (c == '"') {
/* 189:293 */         this.fPrinter.print("&quot;");
/* 190:    */       } else {
/* 191:296 */         this.fPrinter.print(c);
/* 192:    */       }
/* 193:    */     }
/* 194:299 */     this.fPrinter.flush();
/* 195:    */   }
/* 196:    */   
/* 197:    */   protected void printCharacters(XMLString text, boolean normalize)
/* 198:    */   {
/* 199:304 */     if (normalize) {
/* 200:305 */       for (int i = 0; i < text.length; i++)
/* 201:    */       {
/* 202:306 */         char c = text.ch[(text.offset + i)];
/* 203:307 */         if (c != '\n')
/* 204:    */         {
/* 205:308 */           String entity = HTMLEntities.get(c);
/* 206:309 */           if (entity != null) {
/* 207:310 */             printEntity(entity);
/* 208:    */           } else {
/* 209:313 */             this.fPrinter.print(c);
/* 210:    */           }
/* 211:    */         }
/* 212:    */         else
/* 213:    */         {
/* 214:317 */           this.fPrinter.println();
/* 215:    */         }
/* 216:    */       }
/* 217:    */     } else {
/* 218:322 */       for (int i = 0; i < text.length; i++)
/* 219:    */       {
/* 220:323 */         char c = text.ch[(text.offset + i)];
/* 221:324 */         this.fPrinter.print(c);
/* 222:    */       }
/* 223:    */     }
/* 224:327 */     this.fPrinter.flush();
/* 225:    */   }
/* 226:    */   
/* 227:    */   protected void printStartElement(QName element, XMLAttributes attributes)
/* 228:    */   {
/* 229:334 */     int contentIndex = -1;
/* 230:335 */     String originalContent = null;
/* 231:336 */     if (element.rawname.toLowerCase().equals("meta"))
/* 232:    */     {
/* 233:337 */       String httpEquiv = null;
/* 234:338 */       int length = attributes.getLength();
/* 235:339 */       for (int i = 0; i < length; i++)
/* 236:    */       {
/* 237:340 */         String aname = attributes.getQName(i).toLowerCase();
/* 238:341 */         if (aname.equals("http-equiv")) {
/* 239:342 */           httpEquiv = attributes.getValue(i);
/* 240:344 */         } else if (aname.equals("content")) {
/* 241:345 */           contentIndex = i;
/* 242:    */         }
/* 243:    */       }
/* 244:348 */       if ((httpEquiv != null) && (httpEquiv.toLowerCase().equals("content-type")))
/* 245:    */       {
/* 246:349 */         this.fSeenHttpEquiv = true;
/* 247:350 */         String content = null;
/* 248:351 */         if (contentIndex != -1)
/* 249:    */         {
/* 250:352 */           originalContent = attributes.getValue(contentIndex);
/* 251:353 */           content = originalContent.toLowerCase();
/* 252:    */         }
/* 253:355 */         if (content != null)
/* 254:    */         {
/* 255:356 */           int charsetIndex = content.indexOf("charset=");
/* 256:357 */           if (charsetIndex != -1) {
/* 257:358 */             content = content.substring(0, charsetIndex + 8);
/* 258:    */           } else {
/* 259:361 */             content = content + ";charset=";
/* 260:    */           }
/* 261:363 */           content = content + this.fEncoding;
/* 262:364 */           attributes.setValue(contentIndex, content);
/* 263:    */         }
/* 264:    */       }
/* 265:    */     }
/* 266:370 */     this.fPrinter.print('<');
/* 267:371 */     this.fPrinter.print(element.rawname);
/* 268:372 */     int attrCount = attributes != null ? attributes.getLength() : 0;
/* 269:373 */     for (int i = 0; i < attrCount; i++)
/* 270:    */     {
/* 271:374 */       String aname = attributes.getQName(i);
/* 272:375 */       String avalue = attributes.getValue(i);
/* 273:376 */       this.fPrinter.print(' ');
/* 274:377 */       this.fPrinter.print(aname);
/* 275:378 */       this.fPrinter.print("=\"");
/* 276:379 */       printAttributeValue(avalue);
/* 277:380 */       this.fPrinter.print('"');
/* 278:    */     }
/* 279:382 */     this.fPrinter.print('>');
/* 280:383 */     this.fPrinter.flush();
/* 281:386 */     if ((contentIndex != -1) && (originalContent != null)) {
/* 282:387 */       attributes.setValue(contentIndex, originalContent);
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   protected void printEndElement(QName element)
/* 287:    */   {
/* 288:394 */     this.fPrinter.print("</");
/* 289:395 */     this.fPrinter.print(element.rawname);
/* 290:396 */     this.fPrinter.print('>');
/* 291:397 */     this.fPrinter.flush();
/* 292:    */   }
/* 293:    */   
/* 294:    */   protected void printEntity(String name)
/* 295:    */   {
/* 296:402 */     this.fPrinter.print('&');
/* 297:403 */     this.fPrinter.print(name);
/* 298:404 */     this.fPrinter.print(';');
/* 299:405 */     this.fPrinter.flush();
/* 300:    */   }
/* 301:    */   
/* 302:    */   public static void main(String[] argv)
/* 303:    */     throws Exception
/* 304:    */   {
/* 305:414 */     if (argv.length == 0)
/* 306:    */     {
/* 307:415 */       printUsage();
/* 308:416 */       System.exit(1);
/* 309:    */     }
/* 310:418 */     XMLParserConfiguration parser = new HTMLConfiguration();
/* 311:419 */     parser.setFeature("http://apache.org/xml/features/scanner/notify-char-refs", true);
/* 312:420 */     parser.setFeature("http://cyberneko.org/html/features/scanner/notify-builtin-refs", true);
/* 313:421 */     String iencoding = null;
/* 314:422 */     String oencoding = "Windows-1252";
/* 315:423 */     boolean identity = false;
/* 316:424 */     boolean purify = false;
/* 317:425 */     for (int i = 0; i < argv.length; i++)
/* 318:    */     {
/* 319:426 */       String arg = argv[i];
/* 320:427 */       if (arg.equals("-ie"))
/* 321:    */       {
/* 322:428 */         iencoding = argv[(++i)];
/* 323:    */       }
/* 324:431 */       else if ((arg.equals("-e")) || (arg.equals("-oe")))
/* 325:    */       {
/* 326:432 */         oencoding = argv[(++i)];
/* 327:    */       }
/* 328:435 */       else if (arg.equals("-i"))
/* 329:    */       {
/* 330:436 */         identity = true;
/* 331:    */       }
/* 332:439 */       else if (arg.equals("-p"))
/* 333:    */       {
/* 334:440 */         purify = true;
/* 335:    */       }
/* 336:    */       else
/* 337:    */       {
/* 338:443 */         if (arg.equals("-h"))
/* 339:    */         {
/* 340:444 */           printUsage();
/* 341:445 */           System.exit(1);
/* 342:    */         }
/* 343:447 */         Vector filtersVector = new Vector(2);
/* 344:448 */         if (identity) {
/* 345:449 */           filtersVector.addElement(new Identity());
/* 346:451 */         } else if (purify) {
/* 347:452 */           filtersVector.addElement(new Purifier());
/* 348:    */         }
/* 349:454 */         filtersVector.addElement(new Writer(System.out, oencoding));
/* 350:455 */         XMLDocumentFilter[] filters = new XMLDocumentFilter[filtersVector.size()];
/* 351:    */         
/* 352:457 */         filtersVector.copyInto(filters);
/* 353:458 */         parser.setProperty("http://cyberneko.org/html/properties/filters", filters);
/* 354:459 */         XMLInputSource source = new XMLInputSource(null, arg, null);
/* 355:460 */         source.setEncoding(iencoding);
/* 356:461 */         parser.parse(source);
/* 357:    */       }
/* 358:    */     }
/* 359:    */   }
/* 360:    */   
/* 361:    */   private static void printUsage()
/* 362:    */   {
/* 363:467 */     System.err.println("usage: java " + Writer.class.getName() + " (options) file ...");
/* 364:468 */     System.err.println();
/* 365:469 */     System.err.println("options:");
/* 366:470 */     System.err.println("  -ie name  Specify IANA name of input encoding.");
/* 367:471 */     System.err.println("  -oe name  Specify IANA name of output encoding.");
/* 368:472 */     System.err.println("  -i        Perform identity transform.");
/* 369:473 */     System.err.println("  -p        Purify output to ensure XML well-formedness.");
/* 370:474 */     System.err.println("  -h        Display help screen.");
/* 371:475 */     System.err.println();
/* 372:476 */     System.err.println("notes:");
/* 373:477 */     System.err.println("  The -i and -p options are mutually exclusive.");
/* 374:478 */     System.err.println("  The -e option has been replaced with -oe.");
/* 375:    */   }
/* 376:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.filters.Writer
 * JD-Core Version:    0.7.0.1
 */