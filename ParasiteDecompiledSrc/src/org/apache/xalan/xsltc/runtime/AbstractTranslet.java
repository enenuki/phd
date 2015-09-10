/*   1:    */ package org.apache.xalan.xsltc.runtime;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.text.DecimalFormat;
/*   7:    */ import java.text.DecimalFormatSymbols;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Enumeration;
/*  10:    */ import java.util.Vector;
/*  11:    */ import javax.xml.parsers.DocumentBuilder;
/*  12:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  13:    */ import javax.xml.parsers.ParserConfigurationException;
/*  14:    */ import javax.xml.transform.Templates;
/*  15:    */ import org.apache.xalan.xsltc.DOM;
/*  16:    */ import org.apache.xalan.xsltc.DOMCache;
/*  17:    */ import org.apache.xalan.xsltc.DOMEnhancedForDTM;
/*  18:    */ import org.apache.xalan.xsltc.Translet;
/*  19:    */ import org.apache.xalan.xsltc.TransletException;
/*  20:    */ import org.apache.xalan.xsltc.dom.DOMAdapter;
/*  21:    */ import org.apache.xalan.xsltc.dom.KeyIndex;
/*  22:    */ import org.apache.xalan.xsltc.runtime.output.TransletOutputHandlerFactory;
/*  23:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*  24:    */ import org.apache.xml.serializer.ExtendedContentHandler;
/*  25:    */ import org.apache.xml.serializer.SerializationHandler;
/*  26:    */ import org.w3c.dom.DOMImplementation;
/*  27:    */ import org.w3c.dom.Document;
/*  28:    */ import org.xml.sax.ContentHandler;
/*  29:    */ 
/*  30:    */ public abstract class AbstractTranslet
/*  31:    */   implements Translet
/*  32:    */ {
/*  33: 61 */   public String _version = "1.0";
/*  34: 62 */   public String _method = null;
/*  35: 63 */   public String _encoding = "UTF-8";
/*  36: 64 */   public boolean _omitHeader = false;
/*  37: 65 */   public String _standalone = null;
/*  38: 66 */   public String _doctypePublic = null;
/*  39: 67 */   public String _doctypeSystem = null;
/*  40: 68 */   public boolean _indent = false;
/*  41: 69 */   public String _mediaType = null;
/*  42: 70 */   public Vector _cdata = null;
/*  43: 71 */   public int _indentamount = -1;
/*  44:    */   public static final int FIRST_TRANSLET_VERSION = 100;
/*  45:    */   public static final int VER_SPLIT_NAMES_ARRAY = 101;
/*  46:    */   public static final int CURRENT_TRANSLET_VERSION = 101;
/*  47: 82 */   protected int transletVersion = 100;
/*  48:    */   protected String[] namesArray;
/*  49:    */   protected String[] urisArray;
/*  50:    */   protected int[] typesArray;
/*  51:    */   protected String[] namespaceArray;
/*  52: 91 */   protected Templates _templates = null;
/*  53: 94 */   protected boolean _hasIdCall = false;
/*  54: 97 */   protected StringValueHandler stringValueHandler = new StringValueHandler();
/*  55:    */   private static final String EMPTYSTRING = "";
/*  56:    */   private static final String ID_INDEX_NAME = "##id";
/*  57:    */   
/*  58:    */   public void printInternalState()
/*  59:    */   {
/*  60:110 */     System.out.println("-------------------------------------");
/*  61:111 */     System.out.println("AbstractTranslet this = " + this);
/*  62:112 */     System.out.println("pbase = " + this.pbase);
/*  63:113 */     System.out.println("vframe = " + this.pframe);
/*  64:114 */     System.out.println("paramsStack.size() = " + this.paramsStack.size());
/*  65:115 */     System.out.println("namesArray.size = " + this.namesArray.length);
/*  66:116 */     System.out.println("namespaceArray.size = " + this.namespaceArray.length);
/*  67:117 */     System.out.println("");
/*  68:118 */     System.out.println("Total memory = " + Runtime.getRuntime().totalMemory());
/*  69:    */   }
/*  70:    */   
/*  71:    */   public final DOMAdapter makeDOMAdapter(DOM dom)
/*  72:    */     throws TransletException
/*  73:    */   {
/*  74:128 */     setRootForKeys(dom.getDocument());
/*  75:129 */     return new DOMAdapter(dom, this.namesArray, this.urisArray, this.typesArray, this.namespaceArray);
/*  76:    */   }
/*  77:    */   
/*  78:138 */   protected int pbase = 0;
/*  79:138 */   protected int pframe = 0;
/*  80:139 */   protected ArrayList paramsStack = new ArrayList();
/*  81:    */   
/*  82:    */   public final void pushParamFrame()
/*  83:    */   {
/*  84:145 */     this.paramsStack.add(this.pframe, new Integer(this.pbase));
/*  85:146 */     this.pbase = (++this.pframe);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public final void popParamFrame()
/*  89:    */   {
/*  90:153 */     if (this.pbase > 0)
/*  91:    */     {
/*  92:154 */       int oldpbase = ((Integer)this.paramsStack.get(--this.pbase)).intValue();
/*  93:155 */       for (int i = this.pframe - 1; i >= this.pbase; i--) {
/*  94:156 */         this.paramsStack.remove(i);
/*  95:    */       }
/*  96:158 */       this.pframe = this.pbase;this.pbase = oldpbase;
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public final Object addParameter(String name, Object value)
/* 101:    */   {
/* 102:171 */     name = BasisLibrary.mapQNameToJavaName(name);
/* 103:172 */     return addParameter(name, value, false);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public final Object addParameter(String name, Object value, boolean isDefault)
/* 107:    */   {
/* 108:185 */     for (int i = this.pframe - 1; i >= this.pbase; i--)
/* 109:    */     {
/* 110:186 */       Parameter param = (Parameter)this.paramsStack.get(i);
/* 111:188 */       if (param._name.equals(name))
/* 112:    */       {
/* 113:191 */         if ((param._isDefault) || (!isDefault))
/* 114:    */         {
/* 115:192 */           param._value = value;
/* 116:193 */           param._isDefault = isDefault;
/* 117:194 */           return value;
/* 118:    */         }
/* 119:196 */         return param._value;
/* 120:    */       }
/* 121:    */     }
/* 122:201 */     this.paramsStack.add(this.pframe++, new Parameter(name, value, isDefault));
/* 123:202 */     return value;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void clearParameters()
/* 127:    */   {
/* 128:209 */     this.pbase = (this.pframe = 0);
/* 129:210 */     this.paramsStack.clear();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public final Object getParameter(String name)
/* 133:    */   {
/* 134:219 */     name = BasisLibrary.mapQNameToJavaName(name);
/* 135:221 */     for (int i = this.pframe - 1; i >= this.pbase; i--)
/* 136:    */     {
/* 137:222 */       Parameter param = (Parameter)this.paramsStack.get(i);
/* 138:223 */       if (param._name.equals(name)) {
/* 139:223 */         return param._value;
/* 140:    */       }
/* 141:    */     }
/* 142:225 */     return null;
/* 143:    */   }
/* 144:    */   
/* 145:235 */   private MessageHandler _msgHandler = null;
/* 146:    */   
/* 147:    */   public final void setMessageHandler(MessageHandler handler)
/* 148:    */   {
/* 149:241 */     this._msgHandler = handler;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public final void displayMessage(String msg)
/* 153:    */   {
/* 154:248 */     if (this._msgHandler == null) {
/* 155:249 */       System.err.println(msg);
/* 156:    */     } else {
/* 157:252 */       this._msgHandler.displayMessage(msg);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:261 */   public Hashtable _formatSymbols = null;
/* 162:    */   
/* 163:    */   public void addDecimalFormat(String name, DecimalFormatSymbols symbols)
/* 164:    */   {
/* 165:269 */     if (this._formatSymbols == null) {
/* 166:269 */       this._formatSymbols = new Hashtable();
/* 167:    */     }
/* 168:272 */     if (name == null) {
/* 169:272 */       name = "";
/* 170:    */     }
/* 171:275 */     DecimalFormat df = new DecimalFormat();
/* 172:276 */     if (symbols != null) {
/* 173:277 */       df.setDecimalFormatSymbols(symbols);
/* 174:    */     }
/* 175:279 */     this._formatSymbols.put(name, df);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public final DecimalFormat getDecimalFormat(String name)
/* 179:    */   {
/* 180:287 */     if (this._formatSymbols != null)
/* 181:    */     {
/* 182:289 */       if (name == null) {
/* 183:289 */         name = "";
/* 184:    */       }
/* 185:291 */       DecimalFormat df = (DecimalFormat)this._formatSymbols.get(name);
/* 186:292 */       if (df == null) {
/* 187:292 */         df = (DecimalFormat)this._formatSymbols.get("");
/* 188:    */       }
/* 189:293 */       return df;
/* 190:    */     }
/* 191:295 */     return null;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public final void prepassDocument(DOM document)
/* 195:    */   {
/* 196:305 */     setIndexSize(document.getSize());
/* 197:306 */     buildIDIndex(document);
/* 198:    */   }
/* 199:    */   
/* 200:    */   private final void buildIDIndex(DOM document)
/* 201:    */   {
/* 202:315 */     setRootForKeys(document.getDocument());
/* 203:317 */     if ((document instanceof DOMEnhancedForDTM))
/* 204:    */     {
/* 205:318 */       DOMEnhancedForDTM enhancedDOM = (DOMEnhancedForDTM)document;
/* 206:323 */       if (enhancedDOM.hasDOMSource())
/* 207:    */       {
/* 208:324 */         buildKeyIndex("##id", document);
/* 209:325 */         return;
/* 210:    */       }
/* 211:328 */       Hashtable elementsByID = enhancedDOM.getElementsWithIDs();
/* 212:330 */       if (elementsByID == null) {
/* 213:331 */         return;
/* 214:    */       }
/* 215:337 */       Enumeration idValues = elementsByID.keys();
/* 216:338 */       boolean hasIDValues = false;
/* 217:340 */       while (idValues.hasMoreElements())
/* 218:    */       {
/* 219:341 */         Object idValue = idValues.nextElement();
/* 220:342 */         int element = document.getNodeHandle(((Integer)elementsByID.get(idValue)).intValue());
/* 221:    */         
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:347 */         buildKeyIndex("##id", element, idValue);
/* 226:348 */         hasIDValues = true;
/* 227:    */       }
/* 228:351 */       if (hasIDValues) {
/* 229:352 */         setKeyIndexDom("##id", document);
/* 230:    */       }
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public final void postInitialization()
/* 235:    */   {
/* 236:365 */     if (this.transletVersion < 101)
/* 237:    */     {
/* 238:366 */       int arraySize = this.namesArray.length;
/* 239:367 */       String[] newURIsArray = new String[arraySize];
/* 240:368 */       String[] newNamesArray = new String[arraySize];
/* 241:369 */       int[] newTypesArray = new int[arraySize];
/* 242:371 */       for (int i = 0; i < arraySize; i++)
/* 243:    */       {
/* 244:372 */         String name = this.namesArray[i];
/* 245:373 */         int colonIndex = name.lastIndexOf(':');
/* 246:374 */         int lNameStartIdx = colonIndex + 1;
/* 247:376 */         if (colonIndex > -1) {
/* 248:377 */           newURIsArray[i] = name.substring(0, colonIndex);
/* 249:    */         }
/* 250:382 */         if (name.charAt(lNameStartIdx) == '@')
/* 251:    */         {
/* 252:383 */           lNameStartIdx++;
/* 253:384 */           newTypesArray[i] = 2;
/* 254:    */         }
/* 255:385 */         else if (name.charAt(lNameStartIdx) == '?')
/* 256:    */         {
/* 257:386 */           lNameStartIdx++;
/* 258:387 */           newTypesArray[i] = 13;
/* 259:    */         }
/* 260:    */         else
/* 261:    */         {
/* 262:389 */           newTypesArray[i] = 1;
/* 263:    */         }
/* 264:391 */         newNamesArray[i] = (lNameStartIdx == 0 ? name : name.substring(lNameStartIdx));
/* 265:    */       }
/* 266:396 */       this.namesArray = newNamesArray;
/* 267:397 */       this.urisArray = newURIsArray;
/* 268:398 */       this.typesArray = newTypesArray;
/* 269:    */     }
/* 270:404 */     if (this.transletVersion > 101) {
/* 271:405 */       BasisLibrary.runTimeError("UNKNOWN_TRANSLET_VERSION_ERR", getClass().getName());
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:415 */   private Hashtable _keyIndexes = null;
/* 276:416 */   private KeyIndex _emptyKeyIndex = null;
/* 277:417 */   private int _indexSize = 0;
/* 278:418 */   private int _currentRootForKeys = 0;
/* 279:    */   
/* 280:    */   public void setIndexSize(int size)
/* 281:    */   {
/* 282:425 */     if (size > this._indexSize) {
/* 283:425 */       this._indexSize = size;
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public KeyIndex createKeyIndex()
/* 288:    */   {
/* 289:432 */     return new KeyIndex(this._indexSize);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void buildKeyIndex(String name, int node, Object value)
/* 293:    */   {
/* 294:442 */     if (this._keyIndexes == null) {
/* 295:442 */       this._keyIndexes = new Hashtable();
/* 296:    */     }
/* 297:444 */     KeyIndex index = (KeyIndex)this._keyIndexes.get(name);
/* 298:445 */     if (index == null) {
/* 299:446 */       this._keyIndexes.put(name, index = new KeyIndex(this._indexSize));
/* 300:    */     }
/* 301:448 */     index.add(value, node, this._currentRootForKeys);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void buildKeyIndex(String name, DOM dom)
/* 305:    */   {
/* 306:457 */     if (this._keyIndexes == null) {
/* 307:457 */       this._keyIndexes = new Hashtable();
/* 308:    */     }
/* 309:459 */     KeyIndex index = (KeyIndex)this._keyIndexes.get(name);
/* 310:460 */     if (index == null) {
/* 311:461 */       this._keyIndexes.put(name, index = new KeyIndex(this._indexSize));
/* 312:    */     }
/* 313:463 */     index.setDom(dom);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public KeyIndex getKeyIndex(String name)
/* 317:    */   {
/* 318:472 */     if (this._keyIndexes == null) {
/* 319:473 */       return this._emptyKeyIndex = new KeyIndex(1);
/* 320:    */     }
/* 321:479 */     KeyIndex index = (KeyIndex)this._keyIndexes.get(name);
/* 322:482 */     if (index == null) {
/* 323:483 */       return this._emptyKeyIndex = new KeyIndex(1);
/* 324:    */     }
/* 325:488 */     return index;
/* 326:    */   }
/* 327:    */   
/* 328:    */   private void setRootForKeys(int root)
/* 329:    */   {
/* 330:492 */     this._currentRootForKeys = root;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void buildKeys(DOM document, DTMAxisIterator iterator, SerializationHandler handler, int root)
/* 334:    */     throws TransletException
/* 335:    */   {}
/* 336:    */   
/* 337:    */   public void setKeyIndexDom(String name, DOM document)
/* 338:    */   {
/* 339:510 */     getKeyIndex(name).setDom(document);
/* 340:    */   }
/* 341:    */   
/* 342:519 */   private DOMCache _domCache = null;
/* 343:    */   
/* 344:    */   public void setDOMCache(DOMCache cache)
/* 345:    */   {
/* 346:526 */     this._domCache = cache;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public DOMCache getDOMCache()
/* 350:    */   {
/* 351:534 */     return this._domCache;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public SerializationHandler openOutputHandler(String filename, boolean append)
/* 355:    */     throws TransletException
/* 356:    */   {
/* 357:    */     try
/* 358:    */     {
/* 359:546 */       TransletOutputHandlerFactory factory = TransletOutputHandlerFactory.newInstance();
/* 360:    */       
/* 361:    */ 
/* 362:549 */       String dirStr = new File(filename).getParent();
/* 363:550 */       if ((null != dirStr) && (dirStr.length() > 0))
/* 364:    */       {
/* 365:551 */         File dir = new File(dirStr);
/* 366:552 */         dir.mkdirs();
/* 367:    */       }
/* 368:555 */       factory.setEncoding(this._encoding);
/* 369:556 */       factory.setOutputMethod(this._method);
/* 370:557 */       factory.setWriter(new FileWriter(filename, append));
/* 371:558 */       factory.setOutputType(0);
/* 372:    */       
/* 373:560 */       SerializationHandler handler = factory.getSerializationHandler();
/* 374:    */       
/* 375:    */ 
/* 376:563 */       transferOutputSettings(handler);
/* 377:564 */       handler.startDocument();
/* 378:565 */       return handler;
/* 379:    */     }
/* 380:    */     catch (Exception e)
/* 381:    */     {
/* 382:568 */       throw new TransletException(e);
/* 383:    */     }
/* 384:    */   }
/* 385:    */   
/* 386:    */   public SerializationHandler openOutputHandler(String filename)
/* 387:    */     throws TransletException
/* 388:    */   {
/* 389:575 */     return openOutputHandler(filename, false);
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void closeOutputHandler(SerializationHandler handler)
/* 393:    */   {
/* 394:    */     try
/* 395:    */     {
/* 396:580 */       handler.endDocument();
/* 397:581 */       handler.close();
/* 398:    */     }
/* 399:    */     catch (Exception e) {}
/* 400:    */   }
/* 401:    */   
/* 402:    */   public abstract void transform(DOM paramDOM, DTMAxisIterator paramDTMAxisIterator, SerializationHandler paramSerializationHandler)
/* 403:    */     throws TransletException;
/* 404:    */   
/* 405:    */   public final void transform(DOM document, SerializationHandler handler)
/* 406:    */     throws TransletException
/* 407:    */   {
/* 408:    */     try
/* 409:    */     {
/* 410:605 */       transform(document, document.getIterator(), handler);
/* 411:    */     }
/* 412:    */     finally
/* 413:    */     {
/* 414:607 */       this._keyIndexes = null;
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   public final void characters(String string, SerializationHandler handler)
/* 419:    */     throws TransletException
/* 420:    */   {
/* 421:618 */     if (string != null) {
/* 422:    */       try
/* 423:    */       {
/* 424:621 */         handler.characters(string);
/* 425:    */       }
/* 426:    */       catch (Exception e)
/* 427:    */       {
/* 428:623 */         throw new TransletException(e);
/* 429:    */       }
/* 430:    */     }
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void addCdataElement(String name)
/* 434:    */   {
/* 435:632 */     if (this._cdata == null) {
/* 436:633 */       this._cdata = new Vector();
/* 437:    */     }
/* 438:636 */     int lastColon = name.lastIndexOf(':');
/* 439:638 */     if (lastColon > 0)
/* 440:    */     {
/* 441:639 */       String uri = name.substring(0, lastColon);
/* 442:640 */       String localName = name.substring(lastColon + 1);
/* 443:641 */       this._cdata.addElement(uri);
/* 444:642 */       this._cdata.addElement(localName);
/* 445:    */     }
/* 446:    */     else
/* 447:    */     {
/* 448:644 */       this._cdata.addElement(null);
/* 449:645 */       this._cdata.addElement(name);
/* 450:    */     }
/* 451:    */   }
/* 452:    */   
/* 453:    */   protected void transferOutputSettings(SerializationHandler handler)
/* 454:    */   {
/* 455:653 */     if (this._method != null)
/* 456:    */     {
/* 457:654 */       if (this._method.equals("xml"))
/* 458:    */       {
/* 459:655 */         if (this._standalone != null) {
/* 460:656 */           handler.setStandalone(this._standalone);
/* 461:    */         }
/* 462:658 */         if (this._omitHeader) {
/* 463:659 */           handler.setOmitXMLDeclaration(true);
/* 464:    */         }
/* 465:661 */         handler.setCdataSectionElements(this._cdata);
/* 466:662 */         if (this._version != null) {
/* 467:663 */           handler.setVersion(this._version);
/* 468:    */         }
/* 469:665 */         handler.setIndent(this._indent);
/* 470:666 */         handler.setIndentAmount(this._indentamount);
/* 471:667 */         if (this._doctypeSystem != null) {
/* 472:668 */           handler.setDoctype(this._doctypeSystem, this._doctypePublic);
/* 473:    */         }
/* 474:    */       }
/* 475:671 */       else if (this._method.equals("html"))
/* 476:    */       {
/* 477:672 */         handler.setIndent(this._indent);
/* 478:673 */         handler.setDoctype(this._doctypeSystem, this._doctypePublic);
/* 479:674 */         if (this._mediaType != null) {
/* 480:675 */           handler.setMediaType(this._mediaType);
/* 481:    */         }
/* 482:    */       }
/* 483:    */     }
/* 484:    */     else
/* 485:    */     {
/* 486:680 */       handler.setCdataSectionElements(this._cdata);
/* 487:681 */       if (this._version != null) {
/* 488:682 */         handler.setVersion(this._version);
/* 489:    */       }
/* 490:684 */       if (this._standalone != null) {
/* 491:685 */         handler.setStandalone(this._standalone);
/* 492:    */       }
/* 493:687 */       if (this._omitHeader) {
/* 494:688 */         handler.setOmitXMLDeclaration(true);
/* 495:    */       }
/* 496:690 */       handler.setIndent(this._indent);
/* 497:691 */       handler.setDoctype(this._doctypeSystem, this._doctypePublic);
/* 498:    */     }
/* 499:    */   }
/* 500:    */   
/* 501:695 */   private Hashtable _auxClasses = null;
/* 502:    */   
/* 503:    */   public void addAuxiliaryClass(Class auxClass)
/* 504:    */   {
/* 505:698 */     if (this._auxClasses == null) {
/* 506:698 */       this._auxClasses = new Hashtable();
/* 507:    */     }
/* 508:699 */     this._auxClasses.put(auxClass.getName(), auxClass);
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setAuxiliaryClasses(Hashtable auxClasses)
/* 512:    */   {
/* 513:703 */     this._auxClasses = auxClasses;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public Class getAuxiliaryClass(String className)
/* 517:    */   {
/* 518:707 */     if (this._auxClasses == null) {
/* 519:707 */       return null;
/* 520:    */     }
/* 521:708 */     return (Class)this._auxClasses.get(className);
/* 522:    */   }
/* 523:    */   
/* 524:    */   public String[] getNamesArray()
/* 525:    */   {
/* 526:713 */     return this.namesArray;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public String[] getUrisArray()
/* 530:    */   {
/* 531:717 */     return this.urisArray;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public int[] getTypesArray()
/* 535:    */   {
/* 536:721 */     return this.typesArray;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public String[] getNamespaceArray()
/* 540:    */   {
/* 541:725 */     return this.namespaceArray;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public boolean hasIdCall()
/* 545:    */   {
/* 546:729 */     return this._hasIdCall;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public Templates getTemplates()
/* 550:    */   {
/* 551:733 */     return this._templates;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void setTemplates(Templates templates)
/* 555:    */   {
/* 556:737 */     this._templates = templates;
/* 557:    */   }
/* 558:    */   
/* 559:743 */   protected DOMImplementation _domImplementation = null;
/* 560:    */   
/* 561:    */   public Document newDocument(String uri, String qname)
/* 562:    */     throws ParserConfigurationException
/* 563:    */   {
/* 564:748 */     if (this._domImplementation == null) {
/* 565:749 */       this._domImplementation = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
/* 566:    */     }
/* 567:752 */     return this._domImplementation.createDocument(uri, qname, null);
/* 568:    */   }
/* 569:    */   
/* 570:    */   public abstract void transform(DOM paramDOM, SerializationHandler[] paramArrayOfSerializationHandler)
/* 571:    */     throws TransletException;
/* 572:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.AbstractTranslet
 * JD-Core Version:    0.7.0.1
 */