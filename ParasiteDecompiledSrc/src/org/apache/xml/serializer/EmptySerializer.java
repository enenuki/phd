/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import java.util.Properties;
/*   8:    */ import java.util.Vector;
/*   9:    */ import javax.xml.transform.SourceLocator;
/*  10:    */ import javax.xml.transform.Transformer;
/*  11:    */ import org.w3c.dom.Node;
/*  12:    */ import org.xml.sax.Attributes;
/*  13:    */ import org.xml.sax.ContentHandler;
/*  14:    */ import org.xml.sax.Locator;
/*  15:    */ import org.xml.sax.SAXException;
/*  16:    */ import org.xml.sax.SAXParseException;
/*  17:    */ 
/*  18:    */ public class EmptySerializer
/*  19:    */   implements SerializationHandler
/*  20:    */ {
/*  21:    */   protected static final String ERR = "EmptySerializer method not over-ridden";
/*  22:    */   
/*  23:    */   protected void couldThrowIOException()
/*  24:    */     throws IOException
/*  25:    */   {}
/*  26:    */   
/*  27:    */   protected void couldThrowSAXException()
/*  28:    */     throws SAXException
/*  29:    */   {}
/*  30:    */   
/*  31:    */   protected void couldThrowSAXException(char[] chars, int off, int len)
/*  32:    */     throws SAXException
/*  33:    */   {}
/*  34:    */   
/*  35:    */   protected void couldThrowSAXException(String elemQName)
/*  36:    */     throws SAXException
/*  37:    */   {}
/*  38:    */   
/*  39:    */   protected void couldThrowException()
/*  40:    */     throws Exception
/*  41:    */   {}
/*  42:    */   
/*  43:    */   void aMethodIsCalled() {}
/*  44:    */   
/*  45:    */   public ContentHandler asContentHandler()
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 94 */     couldThrowIOException();
/*  49: 95 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setContentHandler(ContentHandler ch)
/*  53:    */   {
/*  54:102 */     aMethodIsCalled();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void close()
/*  58:    */   {
/*  59:109 */     aMethodIsCalled();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Properties getOutputFormat()
/*  63:    */   {
/*  64:116 */     aMethodIsCalled();
/*  65:117 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public OutputStream getOutputStream()
/*  69:    */   {
/*  70:124 */     aMethodIsCalled();
/*  71:125 */     return null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Writer getWriter()
/*  75:    */   {
/*  76:132 */     aMethodIsCalled();
/*  77:133 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean reset()
/*  81:    */   {
/*  82:140 */     aMethodIsCalled();
/*  83:141 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void serialize(Node node)
/*  87:    */     throws IOException
/*  88:    */   {
/*  89:148 */     couldThrowIOException();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setCdataSectionElements(Vector URI_and_localNames)
/*  93:    */   {
/*  94:155 */     aMethodIsCalled();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean setEscaping(boolean escape)
/*  98:    */     throws SAXException
/*  99:    */   {
/* 100:162 */     couldThrowSAXException();
/* 101:163 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIndent(boolean indent)
/* 105:    */   {
/* 106:170 */     aMethodIsCalled();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIndentAmount(int spaces)
/* 110:    */   {
/* 111:177 */     aMethodIsCalled();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setOutputFormat(Properties format)
/* 115:    */   {
/* 116:184 */     aMethodIsCalled();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setOutputStream(OutputStream output)
/* 120:    */   {
/* 121:191 */     aMethodIsCalled();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setVersion(String version)
/* 125:    */   {
/* 126:198 */     aMethodIsCalled();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setWriter(Writer writer)
/* 130:    */   {
/* 131:205 */     aMethodIsCalled();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setTransformer(Transformer transformer)
/* 135:    */   {
/* 136:212 */     aMethodIsCalled();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Transformer getTransformer()
/* 140:    */   {
/* 141:219 */     aMethodIsCalled();
/* 142:220 */     return null;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void flushPending()
/* 146:    */     throws SAXException
/* 147:    */   {
/* 148:227 */     couldThrowSAXException();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute)
/* 152:    */     throws SAXException
/* 153:    */   {
/* 154:241 */     couldThrowSAXException();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void addAttributes(Attributes atts)
/* 158:    */     throws SAXException
/* 159:    */   {
/* 160:248 */     couldThrowSAXException();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void addAttribute(String name, String value)
/* 164:    */   {
/* 165:255 */     aMethodIsCalled();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void characters(String chars)
/* 169:    */     throws SAXException
/* 170:    */   {
/* 171:263 */     couldThrowSAXException();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void endElement(String elemName)
/* 175:    */     throws SAXException
/* 176:    */   {
/* 177:270 */     couldThrowSAXException();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void startDocument()
/* 181:    */     throws SAXException
/* 182:    */   {
/* 183:277 */     couldThrowSAXException();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void startElement(String uri, String localName, String qName)
/* 187:    */     throws SAXException
/* 188:    */   {
/* 189:285 */     couldThrowSAXException(qName);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void startElement(String qName)
/* 193:    */     throws SAXException
/* 194:    */   {
/* 195:292 */     couldThrowSAXException(qName);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void namespaceAfterStartElement(String uri, String prefix)
/* 199:    */     throws SAXException
/* 200:    */   {
/* 201:300 */     couldThrowSAXException();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush)
/* 205:    */     throws SAXException
/* 206:    */   {
/* 207:311 */     couldThrowSAXException();
/* 208:312 */     return false;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void entityReference(String entityName)
/* 212:    */     throws SAXException
/* 213:    */   {
/* 214:319 */     couldThrowSAXException();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public NamespaceMappings getNamespaceMappings()
/* 218:    */   {
/* 219:326 */     aMethodIsCalled();
/* 220:327 */     return null;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String getPrefix(String uri)
/* 224:    */   {
/* 225:334 */     aMethodIsCalled();
/* 226:335 */     return null;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String getNamespaceURI(String name, boolean isElement)
/* 230:    */   {
/* 231:342 */     aMethodIsCalled();
/* 232:343 */     return null;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String getNamespaceURIFromPrefix(String prefix)
/* 236:    */   {
/* 237:350 */     aMethodIsCalled();
/* 238:351 */     return null;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setDocumentLocator(Locator arg0)
/* 242:    */   {
/* 243:358 */     aMethodIsCalled();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void endDocument()
/* 247:    */     throws SAXException
/* 248:    */   {
/* 249:365 */     couldThrowSAXException();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void startPrefixMapping(String arg0, String arg1)
/* 253:    */     throws SAXException
/* 254:    */   {
/* 255:373 */     couldThrowSAXException();
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void endPrefixMapping(String arg0)
/* 259:    */     throws SAXException
/* 260:    */   {
/* 261:380 */     couldThrowSAXException();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void startElement(String arg0, String arg1, String arg2, Attributes arg3)
/* 265:    */     throws SAXException
/* 266:    */   {
/* 267:392 */     couldThrowSAXException();
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void endElement(String arg0, String arg1, String arg2)
/* 271:    */     throws SAXException
/* 272:    */   {
/* 273:400 */     couldThrowSAXException();
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void characters(char[] arg0, int arg1, int arg2)
/* 277:    */     throws SAXException
/* 278:    */   {
/* 279:407 */     couldThrowSAXException(arg0, arg1, arg2);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
/* 283:    */     throws SAXException
/* 284:    */   {
/* 285:415 */     couldThrowSAXException();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void processingInstruction(String arg0, String arg1)
/* 289:    */     throws SAXException
/* 290:    */   {
/* 291:423 */     couldThrowSAXException();
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void skippedEntity(String arg0)
/* 295:    */     throws SAXException
/* 296:    */   {
/* 297:430 */     couldThrowSAXException();
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void comment(String comment)
/* 301:    */     throws SAXException
/* 302:    */   {
/* 303:437 */     couldThrowSAXException();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void startDTD(String arg0, String arg1, String arg2)
/* 307:    */     throws SAXException
/* 308:    */   {
/* 309:445 */     couldThrowSAXException();
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void endDTD()
/* 313:    */     throws SAXException
/* 314:    */   {
/* 315:452 */     couldThrowSAXException();
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void startEntity(String arg0)
/* 319:    */     throws SAXException
/* 320:    */   {
/* 321:459 */     couldThrowSAXException();
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void endEntity(String arg0)
/* 325:    */     throws SAXException
/* 326:    */   {
/* 327:466 */     couldThrowSAXException();
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void startCDATA()
/* 331:    */     throws SAXException
/* 332:    */   {
/* 333:473 */     couldThrowSAXException();
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void endCDATA()
/* 337:    */     throws SAXException
/* 338:    */   {
/* 339:480 */     couldThrowSAXException();
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void comment(char[] arg0, int arg1, int arg2)
/* 343:    */     throws SAXException
/* 344:    */   {
/* 345:487 */     couldThrowSAXException();
/* 346:    */   }
/* 347:    */   
/* 348:    */   public String getDoctypePublic()
/* 349:    */   {
/* 350:494 */     aMethodIsCalled();
/* 351:495 */     return null;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public String getDoctypeSystem()
/* 355:    */   {
/* 356:502 */     aMethodIsCalled();
/* 357:503 */     return null;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public String getEncoding()
/* 361:    */   {
/* 362:510 */     aMethodIsCalled();
/* 363:511 */     return null;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public boolean getIndent()
/* 367:    */   {
/* 368:518 */     aMethodIsCalled();
/* 369:519 */     return false;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public int getIndentAmount()
/* 373:    */   {
/* 374:526 */     aMethodIsCalled();
/* 375:527 */     return 0;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public String getMediaType()
/* 379:    */   {
/* 380:534 */     aMethodIsCalled();
/* 381:535 */     return null;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public boolean getOmitXMLDeclaration()
/* 385:    */   {
/* 386:542 */     aMethodIsCalled();
/* 387:543 */     return false;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public String getStandalone()
/* 391:    */   {
/* 392:550 */     aMethodIsCalled();
/* 393:551 */     return null;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public String getVersion()
/* 397:    */   {
/* 398:558 */     aMethodIsCalled();
/* 399:559 */     return null;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setCdataSectionElements(Hashtable h)
/* 403:    */     throws Exception
/* 404:    */   {
/* 405:566 */     couldThrowException();
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void setDoctype(String system, String pub)
/* 409:    */   {
/* 410:573 */     aMethodIsCalled();
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setDoctypePublic(String doctype)
/* 414:    */   {
/* 415:580 */     aMethodIsCalled();
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void setDoctypeSystem(String doctype)
/* 419:    */   {
/* 420:587 */     aMethodIsCalled();
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setEncoding(String encoding)
/* 424:    */   {
/* 425:594 */     aMethodIsCalled();
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void setMediaType(String mediatype)
/* 429:    */   {
/* 430:601 */     aMethodIsCalled();
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setOmitXMLDeclaration(boolean b)
/* 434:    */   {
/* 435:608 */     aMethodIsCalled();
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void setStandalone(String standalone)
/* 439:    */   {
/* 440:615 */     aMethodIsCalled();
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void elementDecl(String arg0, String arg1)
/* 444:    */     throws SAXException
/* 445:    */   {
/* 446:622 */     couldThrowSAXException();
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void attributeDecl(String arg0, String arg1, String arg2, String arg3, String arg4)
/* 450:    */     throws SAXException
/* 451:    */   {
/* 452:635 */     couldThrowSAXException();
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void internalEntityDecl(String arg0, String arg1)
/* 456:    */     throws SAXException
/* 457:    */   {
/* 458:643 */     couldThrowSAXException();
/* 459:    */   }
/* 460:    */   
/* 461:    */   public void externalEntityDecl(String arg0, String arg1, String arg2)
/* 462:    */     throws SAXException
/* 463:    */   {
/* 464:651 */     couldThrowSAXException();
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void warning(SAXParseException arg0)
/* 468:    */     throws SAXException
/* 469:    */   {
/* 470:658 */     couldThrowSAXException();
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void error(SAXParseException arg0)
/* 474:    */     throws SAXException
/* 475:    */   {
/* 476:665 */     couldThrowSAXException();
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void fatalError(SAXParseException arg0)
/* 480:    */     throws SAXException
/* 481:    */   {
/* 482:672 */     couldThrowSAXException();
/* 483:    */   }
/* 484:    */   
/* 485:    */   public DOMSerializer asDOMSerializer()
/* 486:    */     throws IOException
/* 487:    */   {
/* 488:679 */     couldThrowIOException();
/* 489:680 */     return null;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setNamespaceMappings(NamespaceMappings mappings)
/* 493:    */   {
/* 494:687 */     aMethodIsCalled();
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void setSourceLocator(SourceLocator locator)
/* 498:    */   {
/* 499:695 */     aMethodIsCalled();
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void addUniqueAttribute(String name, String value, int flags)
/* 503:    */     throws SAXException
/* 504:    */   {
/* 505:704 */     couldThrowSAXException();
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void characters(Node node)
/* 509:    */     throws SAXException
/* 510:    */   {
/* 511:712 */     couldThrowSAXException();
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void addXSLAttribute(String qName, String value, String uri)
/* 515:    */   {
/* 516:720 */     aMethodIsCalled();
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void addAttribute(String uri, String localName, String rawName, String type, String value)
/* 520:    */     throws SAXException
/* 521:    */   {
/* 522:728 */     couldThrowSAXException();
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void notationDecl(String arg0, String arg1, String arg2)
/* 526:    */     throws SAXException
/* 527:    */   {
/* 528:735 */     couldThrowSAXException();
/* 529:    */   }
/* 530:    */   
/* 531:    */   public void unparsedEntityDecl(String arg0, String arg1, String arg2, String arg3)
/* 532:    */     throws SAXException
/* 533:    */   {
/* 534:747 */     couldThrowSAXException();
/* 535:    */   }
/* 536:    */   
/* 537:    */   public void setDTDEntityExpansion(boolean expand)
/* 538:    */   {
/* 539:754 */     aMethodIsCalled();
/* 540:    */   }
/* 541:    */   
/* 542:    */   public String getOutputProperty(String name)
/* 543:    */   {
/* 544:760 */     aMethodIsCalled();
/* 545:761 */     return null;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public String getOutputPropertyDefault(String name)
/* 549:    */   {
/* 550:765 */     aMethodIsCalled();
/* 551:766 */     return null;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void setOutputProperty(String name, String val)
/* 555:    */   {
/* 556:770 */     aMethodIsCalled();
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void setOutputPropertyDefault(String name, String val)
/* 560:    */   {
/* 561:775 */     aMethodIsCalled();
/* 562:    */   }
/* 563:    */   
/* 564:    */   public Object asDOM3Serializer()
/* 565:    */     throws IOException
/* 566:    */   {
/* 567:784 */     couldThrowIOException();
/* 568:785 */     return null;
/* 569:    */   }
/* 570:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.EmptySerializer
 * JD-Core Version:    0.7.0.1
 */