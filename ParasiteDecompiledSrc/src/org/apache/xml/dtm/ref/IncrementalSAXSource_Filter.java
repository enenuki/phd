/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import org.apache.xml.res.XMLMessages;
/*   6:    */ import org.apache.xml.utils.ThreadControllerWrapper;
/*   7:    */ import org.xml.sax.Attributes;
/*   8:    */ import org.xml.sax.ContentHandler;
/*   9:    */ import org.xml.sax.DTDHandler;
/*  10:    */ import org.xml.sax.ErrorHandler;
/*  11:    */ import org.xml.sax.InputSource;
/*  12:    */ import org.xml.sax.Locator;
/*  13:    */ import org.xml.sax.SAXException;
/*  14:    */ import org.xml.sax.SAXNotRecognizedException;
/*  15:    */ import org.xml.sax.SAXNotSupportedException;
/*  16:    */ import org.xml.sax.SAXParseException;
/*  17:    */ import org.xml.sax.XMLReader;
/*  18:    */ import org.xml.sax.ext.LexicalHandler;
/*  19:    */ 
/*  20:    */ public class IncrementalSAXSource_Filter
/*  21:    */   implements IncrementalSAXSource, ContentHandler, DTDHandler, LexicalHandler, ErrorHandler, Runnable
/*  22:    */ {
/*  23: 70 */   boolean DEBUG = false;
/*  24: 75 */   private CoroutineManager fCoroutineManager = null;
/*  25: 76 */   private int fControllerCoroutineID = -1;
/*  26: 77 */   private int fSourceCoroutineID = -1;
/*  27: 79 */   private ContentHandler clientContentHandler = null;
/*  28: 80 */   private LexicalHandler clientLexicalHandler = null;
/*  29: 81 */   private DTDHandler clientDTDHandler = null;
/*  30: 82 */   private ErrorHandler clientErrorHandler = null;
/*  31:    */   private int eventcounter;
/*  32: 84 */   private int frequency = 5;
/*  33: 89 */   private boolean fNoMoreEvents = false;
/*  34: 92 */   private XMLReader fXMLReader = null;
/*  35: 93 */   private InputSource fXMLReaderInputSource = null;
/*  36:    */   
/*  37:    */   public IncrementalSAXSource_Filter()
/*  38:    */   {
/*  39:100 */     init(new CoroutineManager(), -1, -1);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public IncrementalSAXSource_Filter(CoroutineManager co, int controllerCoroutineID)
/*  43:    */   {
/*  44:108 */     init(co, controllerCoroutineID, -1);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static IncrementalSAXSource createIncrementalSAXSource(CoroutineManager co, int controllerCoroutineID)
/*  48:    */   {
/*  49:115 */     return new IncrementalSAXSource_Filter(co, controllerCoroutineID);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void init(CoroutineManager co, int controllerCoroutineID, int sourceCoroutineID)
/*  53:    */   {
/*  54:125 */     if (co == null) {
/*  55:126 */       co = new CoroutineManager();
/*  56:    */     }
/*  57:127 */     this.fCoroutineManager = co;
/*  58:128 */     this.fControllerCoroutineID = co.co_joinCoroutineSet(controllerCoroutineID);
/*  59:129 */     this.fSourceCoroutineID = co.co_joinCoroutineSet(sourceCoroutineID);
/*  60:130 */     if ((this.fControllerCoroutineID == -1) || (this.fSourceCoroutineID == -1)) {
/*  61:131 */       throw new RuntimeException(XMLMessages.createXMLMessage("ER_COJOINROUTINESET_FAILED", null));
/*  62:    */     }
/*  63:133 */     this.fNoMoreEvents = false;
/*  64:134 */     this.eventcounter = this.frequency;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setXMLReader(XMLReader eventsource)
/*  68:    */   {
/*  69:144 */     this.fXMLReader = eventsource;
/*  70:145 */     eventsource.setContentHandler(this);
/*  71:146 */     eventsource.setDTDHandler(this);
/*  72:147 */     eventsource.setErrorHandler(this);
/*  73:    */     try
/*  74:    */     {
/*  75:152 */       eventsource.setProperty("http://xml.org/sax/properties/lexical-handler", this);
/*  76:    */     }
/*  77:    */     catch (SAXNotRecognizedException e) {}catch (SAXNotSupportedException e) {}
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setContentHandler(ContentHandler handler)
/*  81:    */   {
/*  82:172 */     this.clientContentHandler = handler;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setDTDHandler(DTDHandler handler)
/*  86:    */   {
/*  87:177 */     this.clientDTDHandler = handler;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setLexicalHandler(LexicalHandler handler)
/*  91:    */   {
/*  92:185 */     this.clientLexicalHandler = handler;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setErrHandler(ErrorHandler handler)
/*  96:    */   {
/*  97:191 */     this.clientErrorHandler = handler;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setReturnFrequency(int events)
/* 101:    */   {
/* 102:198 */     if (events < 1) {
/* 103:198 */       events = 1;
/* 104:    */     }
/* 105:199 */     this.frequency = (this.eventcounter = events);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void characters(char[] ch, int start, int length)
/* 109:    */     throws SAXException
/* 110:    */   {
/* 111:224 */     if (--this.eventcounter <= 0)
/* 112:    */     {
/* 113:226 */       co_yield(true);
/* 114:227 */       this.eventcounter = this.frequency;
/* 115:    */     }
/* 116:229 */     if (this.clientContentHandler != null) {
/* 117:230 */       this.clientContentHandler.characters(ch, start, length);
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void endDocument()
/* 122:    */     throws SAXException
/* 123:    */   {
/* 124:236 */     if (this.clientContentHandler != null) {
/* 125:237 */       this.clientContentHandler.endDocument();
/* 126:    */     }
/* 127:239 */     this.eventcounter = 0;
/* 128:240 */     co_yield(false);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void endElement(String namespaceURI, String localName, String qName)
/* 132:    */     throws SAXException
/* 133:    */   {
/* 134:246 */     if (--this.eventcounter <= 0)
/* 135:    */     {
/* 136:248 */       co_yield(true);
/* 137:249 */       this.eventcounter = this.frequency;
/* 138:    */     }
/* 139:251 */     if (this.clientContentHandler != null) {
/* 140:252 */       this.clientContentHandler.endElement(namespaceURI, localName, qName);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void endPrefixMapping(String prefix)
/* 145:    */     throws SAXException
/* 146:    */   {
/* 147:257 */     if (--this.eventcounter <= 0)
/* 148:    */     {
/* 149:259 */       co_yield(true);
/* 150:260 */       this.eventcounter = this.frequency;
/* 151:    */     }
/* 152:262 */     if (this.clientContentHandler != null) {
/* 153:263 */       this.clientContentHandler.endPrefixMapping(prefix);
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void ignorableWhitespace(char[] ch, int start, int length)
/* 158:    */     throws SAXException
/* 159:    */   {
/* 160:268 */     if (--this.eventcounter <= 0)
/* 161:    */     {
/* 162:270 */       co_yield(true);
/* 163:271 */       this.eventcounter = this.frequency;
/* 164:    */     }
/* 165:273 */     if (this.clientContentHandler != null) {
/* 166:274 */       this.clientContentHandler.ignorableWhitespace(ch, start, length);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void processingInstruction(String target, String data)
/* 171:    */     throws SAXException
/* 172:    */   {
/* 173:279 */     if (--this.eventcounter <= 0)
/* 174:    */     {
/* 175:281 */       co_yield(true);
/* 176:282 */       this.eventcounter = this.frequency;
/* 177:    */     }
/* 178:284 */     if (this.clientContentHandler != null) {
/* 179:285 */       this.clientContentHandler.processingInstruction(target, data);
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setDocumentLocator(Locator locator)
/* 184:    */   {
/* 185:289 */     if (--this.eventcounter <= 0) {
/* 186:293 */       this.eventcounter = this.frequency;
/* 187:    */     }
/* 188:295 */     if (this.clientContentHandler != null) {
/* 189:296 */       this.clientContentHandler.setDocumentLocator(locator);
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void skippedEntity(String name)
/* 194:    */     throws SAXException
/* 195:    */   {
/* 196:301 */     if (--this.eventcounter <= 0)
/* 197:    */     {
/* 198:303 */       co_yield(true);
/* 199:304 */       this.eventcounter = this.frequency;
/* 200:    */     }
/* 201:306 */     if (this.clientContentHandler != null) {
/* 202:307 */       this.clientContentHandler.skippedEntity(name);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void startDocument()
/* 207:    */     throws SAXException
/* 208:    */   {
/* 209:312 */     co_entry_pause();
/* 210:315 */     if (--this.eventcounter <= 0)
/* 211:    */     {
/* 212:317 */       co_yield(true);
/* 213:318 */       this.eventcounter = this.frequency;
/* 214:    */     }
/* 215:320 */     if (this.clientContentHandler != null) {
/* 216:321 */       this.clientContentHandler.startDocument();
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
/* 221:    */     throws SAXException
/* 222:    */   {
/* 223:327 */     if (--this.eventcounter <= 0)
/* 224:    */     {
/* 225:329 */       co_yield(true);
/* 226:330 */       this.eventcounter = this.frequency;
/* 227:    */     }
/* 228:332 */     if (this.clientContentHandler != null) {
/* 229:333 */       this.clientContentHandler.startElement(namespaceURI, localName, qName, atts);
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void startPrefixMapping(String prefix, String uri)
/* 234:    */     throws SAXException
/* 235:    */   {
/* 236:338 */     if (--this.eventcounter <= 0)
/* 237:    */     {
/* 238:340 */       co_yield(true);
/* 239:341 */       this.eventcounter = this.frequency;
/* 240:    */     }
/* 241:343 */     if (this.clientContentHandler != null) {
/* 242:344 */       this.clientContentHandler.startPrefixMapping(prefix, uri);
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void comment(char[] ch, int start, int length)
/* 247:    */     throws SAXException
/* 248:    */   {
/* 249:360 */     if (null != this.clientLexicalHandler) {
/* 250:361 */       this.clientLexicalHandler.comment(ch, start, length);
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void endCDATA()
/* 255:    */     throws SAXException
/* 256:    */   {
/* 257:366 */     if (null != this.clientLexicalHandler) {
/* 258:367 */       this.clientLexicalHandler.endCDATA();
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void endDTD()
/* 263:    */     throws SAXException
/* 264:    */   {
/* 265:372 */     if (null != this.clientLexicalHandler) {
/* 266:373 */       this.clientLexicalHandler.endDTD();
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void endEntity(String name)
/* 271:    */     throws SAXException
/* 272:    */   {
/* 273:378 */     if (null != this.clientLexicalHandler) {
/* 274:379 */       this.clientLexicalHandler.endEntity(name);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void startCDATA()
/* 279:    */     throws SAXException
/* 280:    */   {
/* 281:384 */     if (null != this.clientLexicalHandler) {
/* 282:385 */       this.clientLexicalHandler.startCDATA();
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void startDTD(String name, String publicId, String systemId)
/* 287:    */     throws SAXException
/* 288:    */   {
/* 289:391 */     if (null != this.clientLexicalHandler) {
/* 290:392 */       this.clientLexicalHandler.startDTD(name, publicId, systemId);
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void startEntity(String name)
/* 295:    */     throws SAXException
/* 296:    */   {
/* 297:397 */     if (null != this.clientLexicalHandler) {
/* 298:398 */       this.clientLexicalHandler.startEntity(name);
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void notationDecl(String a, String b, String c)
/* 303:    */     throws SAXException
/* 304:    */   {
/* 305:406 */     if (null != this.clientDTDHandler) {
/* 306:407 */       this.clientDTDHandler.notationDecl(a, b, c);
/* 307:    */     }
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void unparsedEntityDecl(String a, String b, String c, String d)
/* 311:    */     throws SAXException
/* 312:    */   {
/* 313:411 */     if (null != this.clientDTDHandler) {
/* 314:412 */       this.clientDTDHandler.unparsedEntityDecl(a, b, c, d);
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void error(SAXParseException exception)
/* 319:    */     throws SAXException
/* 320:    */   {
/* 321:432 */     if (null != this.clientErrorHandler) {
/* 322:433 */       this.clientErrorHandler.error(exception);
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void fatalError(SAXParseException exception)
/* 327:    */     throws SAXException
/* 328:    */   {
/* 329:440 */     if (null != this.clientErrorHandler) {
/* 330:441 */       this.clientErrorHandler.error(exception);
/* 331:    */     }
/* 332:443 */     this.eventcounter = 0;
/* 333:444 */     co_yield(false);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void warning(SAXParseException exception)
/* 337:    */     throws SAXException
/* 338:    */   {
/* 339:450 */     if (null != this.clientErrorHandler) {
/* 340:451 */       this.clientErrorHandler.error(exception);
/* 341:    */     }
/* 342:    */   }
/* 343:    */   
/* 344:    */   public int getSourceCoroutineID()
/* 345:    */   {
/* 346:460 */     return this.fSourceCoroutineID;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public int getControllerCoroutineID()
/* 350:    */   {
/* 351:463 */     return this.fControllerCoroutineID;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public CoroutineManager getCoroutineManager()
/* 355:    */   {
/* 356:473 */     return this.fCoroutineManager;
/* 357:    */   }
/* 358:    */   
/* 359:    */   protected void count_and_yield(boolean moreExpected)
/* 360:    */     throws SAXException
/* 361:    */   {
/* 362:489 */     if (!moreExpected) {
/* 363:489 */       this.eventcounter = 0;
/* 364:    */     }
/* 365:491 */     if (--this.eventcounter <= 0)
/* 366:    */     {
/* 367:493 */       co_yield(true);
/* 368:494 */       this.eventcounter = this.frequency;
/* 369:    */     }
/* 370:    */   }
/* 371:    */   
/* 372:    */   private void co_entry_pause()
/* 373:    */     throws SAXException
/* 374:    */   {
/* 375:507 */     if (this.fCoroutineManager == null) {
/* 376:510 */       init(null, -1, -1);
/* 377:    */     }
/* 378:    */     try
/* 379:    */     {
/* 380:515 */       Object arg = this.fCoroutineManager.co_entry_pause(this.fSourceCoroutineID);
/* 381:516 */       if (arg == Boolean.FALSE) {
/* 382:517 */         co_yield(false);
/* 383:    */       }
/* 384:    */     }
/* 385:    */     catch (NoSuchMethodException e)
/* 386:    */     {
/* 387:523 */       if (this.DEBUG) {
/* 388:523 */         e.printStackTrace();
/* 389:    */       }
/* 390:524 */       throw new SAXException(e);
/* 391:    */     }
/* 392:    */   }
/* 393:    */   
/* 394:    */   private void co_yield(boolean moreRemains)
/* 395:    */     throws SAXException
/* 396:    */   {
/* 397:553 */     if (this.fNoMoreEvents) {
/* 398:554 */       return;
/* 399:    */     }
/* 400:    */     try
/* 401:    */     {
/* 402:558 */       Object arg = Boolean.FALSE;
/* 403:559 */       if (moreRemains) {
/* 404:562 */         arg = this.fCoroutineManager.co_resume(Boolean.TRUE, this.fSourceCoroutineID, this.fControllerCoroutineID);
/* 405:    */       }
/* 406:568 */       if (arg == Boolean.FALSE)
/* 407:    */       {
/* 408:570 */         this.fNoMoreEvents = true;
/* 409:572 */         if (this.fXMLReader != null) {
/* 410:573 */           throw new StopException();
/* 411:    */         }
/* 412:576 */         this.fCoroutineManager.co_exit_to(Boolean.FALSE, this.fSourceCoroutineID, this.fControllerCoroutineID);
/* 413:    */       }
/* 414:    */     }
/* 415:    */     catch (NoSuchMethodException e)
/* 416:    */     {
/* 417:584 */       this.fNoMoreEvents = true;
/* 418:585 */       this.fCoroutineManager.co_exit(this.fSourceCoroutineID);
/* 419:586 */       throw new SAXException(e);
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void startParse(InputSource source)
/* 424:    */     throws SAXException
/* 425:    */   {
/* 426:605 */     if (this.fNoMoreEvents) {
/* 427:606 */       throw new SAXException(XMLMessages.createXMLMessage("ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", null));
/* 428:    */     }
/* 429:607 */     if (this.fXMLReader == null) {
/* 430:608 */       throw new SAXException(XMLMessages.createXMLMessage("ER_XMLRDR_NOT_BEFORE_STARTPARSE", null));
/* 431:    */     }
/* 432:610 */     this.fXMLReaderInputSource = source;
/* 433:    */     
/* 434:    */ 
/* 435:    */ 
/* 436:614 */     ThreadControllerWrapper.runThread(this, -1);
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void run()
/* 440:    */   {
/* 441:622 */     if (this.fXMLReader == null) {
/* 442:622 */       return;
/* 443:    */     }
/* 444:624 */     if (this.DEBUG) {
/* 445:624 */       System.out.println("IncrementalSAXSource_Filter parse thread launched");
/* 446:    */     }
/* 447:627 */     Object arg = Boolean.FALSE;
/* 448:    */     try
/* 449:    */     {
/* 450:635 */       this.fXMLReader.parse(this.fXMLReaderInputSource);
/* 451:    */     }
/* 452:    */     catch (IOException ex)
/* 453:    */     {
/* 454:639 */       arg = ex;
/* 455:    */     }
/* 456:    */     catch (StopException ex)
/* 457:    */     {
/* 458:644 */       if (this.DEBUG) {
/* 459:644 */         System.out.println("Active IncrementalSAXSource_Filter normal stop exception");
/* 460:    */       }
/* 461:    */     }
/* 462:    */     catch (SAXException ex)
/* 463:    */     {
/* 464:648 */       Exception inner = ex.getException();
/* 465:649 */       if ((inner instanceof StopException))
/* 466:    */       {
/* 467:651 */         if (this.DEBUG) {
/* 468:651 */           System.out.println("Active IncrementalSAXSource_Filter normal stop exception");
/* 469:    */         }
/* 470:    */       }
/* 471:    */       else
/* 472:    */       {
/* 473:656 */         if (this.DEBUG)
/* 474:    */         {
/* 475:658 */           System.out.println("Active IncrementalSAXSource_Filter UNEXPECTED SAX exception: " + inner);
/* 476:659 */           inner.printStackTrace();
/* 477:    */         }
/* 478:661 */         arg = ex;
/* 479:    */       }
/* 480:    */     }
/* 481:666 */     this.fXMLReader = null;
/* 482:    */     try
/* 483:    */     {
/* 484:671 */       this.fNoMoreEvents = true;
/* 485:672 */       this.fCoroutineManager.co_exit_to(arg, this.fSourceCoroutineID, this.fControllerCoroutineID);
/* 486:    */     }
/* 487:    */     catch (NoSuchMethodException e)
/* 488:    */     {
/* 489:679 */       e.printStackTrace(System.err);
/* 490:680 */       this.fCoroutineManager.co_exit(this.fSourceCoroutineID);
/* 491:    */     }
/* 492:    */   }
/* 493:    */   
/* 494:    */   public Object deliverMoreNodes(boolean parsemore)
/* 495:    */   {
/* 496:711 */     if (this.fNoMoreEvents) {
/* 497:712 */       return Boolean.FALSE;
/* 498:    */     }
/* 499:    */     try
/* 500:    */     {
/* 501:716 */       Object result = this.fCoroutineManager.co_resume(parsemore ? Boolean.TRUE : Boolean.FALSE, this.fControllerCoroutineID, this.fSourceCoroutineID);
/* 502:719 */       if (result == Boolean.FALSE) {
/* 503:720 */         this.fCoroutineManager.co_exit(this.fControllerCoroutineID);
/* 504:    */       }
/* 505:722 */       return result;
/* 506:    */     }
/* 507:    */     catch (NoSuchMethodException e)
/* 508:    */     {
/* 509:730 */       return e;
/* 510:    */     }
/* 511:    */   }
/* 512:    */   
/* 513:    */   class StopException
/* 514:    */     extends RuntimeException
/* 515:    */   {
/* 516:    */     static final long serialVersionUID = -1129245796185754956L;
/* 517:    */     
/* 518:    */     StopException() {}
/* 519:    */   }
/* 520:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.IncrementalSAXSource_Filter
 * JD-Core Version:    0.7.0.1
 */