/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.io.BufferedOutputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.Enumeration;
/*  12:    */ import java.util.Hashtable;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Map.Entry;
/*  16:    */ import java.util.Properties;
/*  17:    */ import java.util.Set;
/*  18:    */ import java.util.Vector;
/*  19:    */ import java.util.jar.Attributes;
/*  20:    */ import java.util.jar.Attributes.Name;
/*  21:    */ import java.util.jar.JarEntry;
/*  22:    */ import java.util.jar.JarOutputStream;
/*  23:    */ import java.util.jar.Manifest;
/*  24:    */ import java.util.zip.ZipOutputStream;
/*  25:    */ import org.apache.bcel.classfile.JavaClass;
/*  26:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  27:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  28:    */ import org.xml.sax.InputSource;
/*  29:    */ import org.xml.sax.XMLReader;
/*  30:    */ 
/*  31:    */ public final class XSLTC
/*  32:    */ {
/*  33:    */   private Parser _parser;
/*  34: 63 */   private XMLReader _reader = null;
/*  35: 66 */   private SourceLoader _loader = null;
/*  36:    */   private Stylesheet _stylesheet;
/*  37: 73 */   private int _modeSerial = 1;
/*  38: 74 */   private int _stylesheetSerial = 1;
/*  39: 75 */   private int _stepPatternSerial = 1;
/*  40: 76 */   private int _helperClassSerial = 0;
/*  41: 77 */   private int _attributeSetSerial = 0;
/*  42:    */   private int[] _numberFieldIndexes;
/*  43:    */   private int _nextGType;
/*  44:    */   private Vector _namesIndex;
/*  45:    */   private Hashtable _elements;
/*  46:    */   private Hashtable _attributes;
/*  47:    */   private int _nextNSType;
/*  48:    */   private Vector _namespaceIndex;
/*  49:    */   private Hashtable _namespaces;
/*  50:    */   private Hashtable _namespacePrefixes;
/*  51:    */   private Vector m_characterData;
/*  52:    */   public static final int FILE_OUTPUT = 0;
/*  53:    */   public static final int JAR_OUTPUT = 1;
/*  54:    */   public static final int BYTEARRAY_OUTPUT = 2;
/*  55:    */   public static final int CLASSLOADER_OUTPUT = 3;
/*  56:    */   public static final int BYTEARRAY_AND_FILE_OUTPUT = 4;
/*  57:    */   public static final int BYTEARRAY_AND_JAR_OUTPUT = 5;
/*  58:107 */   private boolean _debug = false;
/*  59:108 */   private String _jarFileName = null;
/*  60:109 */   private String _className = null;
/*  61:110 */   private String _packageName = null;
/*  62:111 */   private File _destDir = null;
/*  63:112 */   private int _outputType = 0;
/*  64:    */   private Vector _classes;
/*  65:    */   private Vector _bcelClasses;
/*  66:116 */   private boolean _callsNodeset = false;
/*  67:117 */   private boolean _multiDocument = false;
/*  68:118 */   private boolean _hasIdCall = false;
/*  69:    */   private Vector _stylesheetNSAncestorPointers;
/*  70:    */   private Vector _prefixURIPairs;
/*  71:    */   private Vector _prefixURIPairsIdx;
/*  72:130 */   private boolean _templateInlining = false;
/*  73:135 */   private boolean _isSecureProcessing = false;
/*  74:    */   
/*  75:    */   public XSLTC()
/*  76:    */   {
/*  77:141 */     this._parser = new Parser(this);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setSecureProcessing(boolean flag)
/*  81:    */   {
/*  82:148 */     this._isSecureProcessing = flag;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isSecureProcessing()
/*  86:    */   {
/*  87:155 */     return this._isSecureProcessing;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Parser getParser()
/*  91:    */   {
/*  92:162 */     return this._parser;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setOutputType(int type)
/*  96:    */   {
/*  97:169 */     this._outputType = type;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Properties getOutputProperties()
/* 101:    */   {
/* 102:176 */     return this._parser.getOutputProperties();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void init()
/* 106:    */   {
/* 107:183 */     reset();
/* 108:184 */     this._reader = null;
/* 109:185 */     this._classes = new Vector();
/* 110:186 */     this._bcelClasses = new Vector();
/* 111:    */   }
/* 112:    */   
/* 113:    */   private void reset()
/* 114:    */   {
/* 115:193 */     this._nextGType = 14;
/* 116:194 */     this._elements = new Hashtable();
/* 117:195 */     this._attributes = new Hashtable();
/* 118:196 */     this._namespaces = new Hashtable();
/* 119:197 */     this._namespaces.put("", new Integer(this._nextNSType));
/* 120:198 */     this._namesIndex = new Vector(128);
/* 121:199 */     this._namespaceIndex = new Vector(32);
/* 122:200 */     this._namespacePrefixes = new Hashtable();
/* 123:201 */     this._stylesheet = null;
/* 124:202 */     this._parser.init();
/* 125:    */     
/* 126:204 */     this._modeSerial = 1;
/* 127:205 */     this._stylesheetSerial = 1;
/* 128:206 */     this._stepPatternSerial = 1;
/* 129:207 */     this._helperClassSerial = 0;
/* 130:208 */     this._attributeSetSerial = 0;
/* 131:209 */     this._multiDocument = false;
/* 132:210 */     this._hasIdCall = false;
/* 133:211 */     this._stylesheetNSAncestorPointers = null;
/* 134:212 */     this._prefixURIPairs = null;
/* 135:213 */     this._prefixURIPairsIdx = null;
/* 136:214 */     this._numberFieldIndexes = new int[] { -1, -1, -1 };
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setSourceLoader(SourceLoader loader)
/* 140:    */   {
/* 141:227 */     this._loader = loader;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setTemplateInlining(boolean templateInlining)
/* 145:    */   {
/* 146:237 */     this._templateInlining = templateInlining;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean getTemplateInlining()
/* 150:    */   {
/* 151:244 */     return this._templateInlining;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setPIParameters(String media, String title, String charset)
/* 155:    */   {
/* 156:257 */     this._parser.setPIParameters(media, title, charset);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean compile(URL url)
/* 160:    */   {
/* 161:    */     try
/* 162:    */     {
/* 163:267 */       InputStream stream = url.openStream();
/* 164:268 */       InputSource input = new InputSource(stream);
/* 165:269 */       input.setSystemId(url.toString());
/* 166:270 */       return compile(input, this._className);
/* 167:    */     }
/* 168:    */     catch (IOException e)
/* 169:    */     {
/* 170:273 */       this._parser.reportError(2, new ErrorMsg(e));
/* 171:    */     }
/* 172:274 */     return false;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean compile(URL url, String name)
/* 176:    */   {
/* 177:    */     try
/* 178:    */     {
/* 179:286 */       InputStream stream = url.openStream();
/* 180:287 */       InputSource input = new InputSource(stream);
/* 181:288 */       input.setSystemId(url.toString());
/* 182:289 */       return compile(input, name);
/* 183:    */     }
/* 184:    */     catch (IOException e)
/* 185:    */     {
/* 186:292 */       this._parser.reportError(2, new ErrorMsg(e));
/* 187:    */     }
/* 188:293 */     return false;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean compile(InputStream stream, String name)
/* 192:    */   {
/* 193:304 */     InputSource input = new InputSource(stream);
/* 194:305 */     input.setSystemId(name);
/* 195:306 */     return compile(input, name);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean compile(InputSource input, String name)
/* 199:    */   {
/* 200:    */     try
/* 201:    */     {
/* 202:318 */       reset();
/* 203:    */       
/* 204:    */ 
/* 205:321 */       String systemId = null;
/* 206:322 */       if (input != null) {
/* 207:323 */         systemId = input.getSystemId();
/* 208:    */       }
/* 209:327 */       if (this._className == null)
/* 210:    */       {
/* 211:328 */         if (name != null) {
/* 212:329 */           setClassName(name);
/* 213:331 */         } else if ((systemId != null) && (!systemId.equals(""))) {
/* 214:332 */           setClassName(Util.baseName(systemId));
/* 215:    */         }
/* 216:336 */         if ((this._className == null) || (this._className.length() == 0)) {
/* 217:337 */           setClassName("GregorSamsa");
/* 218:    */         }
/* 219:    */       }
/* 220:342 */       SyntaxTreeNode element = null;
/* 221:343 */       if (this._reader == null) {
/* 222:344 */         element = this._parser.parse(input);
/* 223:    */       } else {
/* 224:347 */         element = this._parser.parse(this._reader, input);
/* 225:    */       }
/* 226:351 */       if ((!this._parser.errorsFound()) && (element != null))
/* 227:    */       {
/* 228:353 */         this._stylesheet = this._parser.makeStylesheet(element);
/* 229:354 */         this._stylesheet.setSourceLoader(this._loader);
/* 230:355 */         this._stylesheet.setSystemId(systemId);
/* 231:356 */         this._stylesheet.setParentStylesheet(null);
/* 232:357 */         this._stylesheet.setTemplateInlining(this._templateInlining);
/* 233:358 */         this._parser.setCurrentStylesheet(this._stylesheet);
/* 234:    */         
/* 235:    */ 
/* 236:361 */         this._parser.createAST(this._stylesheet);
/* 237:    */       }
/* 238:364 */       if ((!this._parser.errorsFound()) && (this._stylesheet != null))
/* 239:    */       {
/* 240:365 */         this._stylesheet.setCallsNodeset(this._callsNodeset);
/* 241:366 */         this._stylesheet.setMultiDocument(this._multiDocument);
/* 242:367 */         this._stylesheet.setHasIdCall(this._hasIdCall);
/* 243:370 */         synchronized (getClass())
/* 244:    */         {
/* 245:371 */           this._stylesheet.translate();
/* 246:    */         }
/* 247:    */       }
/* 248:    */     }
/* 249:    */     catch (Exception e)
/* 250:    */     {
/* 251:376 */       e.printStackTrace();
/* 252:377 */       this._parser.reportError(2, new ErrorMsg(e));
/* 253:    */     }
/* 254:    */     catch (Error e)
/* 255:    */     {
/* 256:380 */       if (this._debug) {
/* 257:380 */         e.printStackTrace();
/* 258:    */       }
/* 259:381 */       this._parser.reportError(2, new ErrorMsg(e));
/* 260:    */     }
/* 261:    */     finally
/* 262:    */     {
/* 263:384 */       this._reader = null;
/* 264:    */     }
/* 265:386 */     return !this._parser.errorsFound();
/* 266:    */   }
/* 267:    */   
/* 268:    */   public boolean compile(Vector stylesheets)
/* 269:    */   {
/* 270:396 */     int count = stylesheets.size();
/* 271:399 */     if (count == 0) {
/* 272:399 */       return true;
/* 273:    */     }
/* 274:403 */     if (count == 1)
/* 275:    */     {
/* 276:404 */       Object url = stylesheets.firstElement();
/* 277:405 */       if ((url instanceof URL)) {
/* 278:406 */         return compile((URL)url);
/* 279:    */       }
/* 280:408 */       return false;
/* 281:    */     }
/* 282:412 */     Enumeration urls = stylesheets.elements();
/* 283:413 */     while (urls.hasMoreElements())
/* 284:    */     {
/* 285:414 */       this._className = null;
/* 286:415 */       Object url = urls.nextElement();
/* 287:416 */       if (((url instanceof URL)) && 
/* 288:417 */         (!compile((URL)url))) {
/* 289:417 */         return false;
/* 290:    */       }
/* 291:    */     }
/* 292:421 */     return true;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public byte[][] getBytecodes()
/* 296:    */   {
/* 297:429 */     int count = this._classes.size();
/* 298:430 */     byte[][] result = new byte[count][1];
/* 299:431 */     for (int i = 0; i < count; i++) {
/* 300:432 */       result[i] = ((byte[])this._classes.elementAt(i));
/* 301:    */     }
/* 302:433 */     return result;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public byte[][] compile(String name, InputSource input, int outputType)
/* 306:    */   {
/* 307:445 */     this._outputType = outputType;
/* 308:446 */     if (compile(input, name)) {
/* 309:447 */       return getBytecodes();
/* 310:    */     }
/* 311:449 */     return null;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public byte[][] compile(String name, InputSource input)
/* 315:    */   {
/* 316:460 */     return compile(name, input, 2);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setXMLReader(XMLReader reader)
/* 320:    */   {
/* 321:468 */     this._reader = reader;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public XMLReader getXMLReader()
/* 325:    */   {
/* 326:475 */     return this._reader;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public Vector getErrors()
/* 330:    */   {
/* 331:483 */     return this._parser.getErrors();
/* 332:    */   }
/* 333:    */   
/* 334:    */   public Vector getWarnings()
/* 335:    */   {
/* 336:491 */     return this._parser.getWarnings();
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void printErrors()
/* 340:    */   {
/* 341:498 */     this._parser.printErrors();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void printWarnings()
/* 345:    */   {
/* 346:505 */     this._parser.printWarnings();
/* 347:    */   }
/* 348:    */   
/* 349:    */   protected void setMultiDocument(boolean flag)
/* 350:    */   {
/* 351:513 */     this._multiDocument = flag;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean isMultiDocument()
/* 355:    */   {
/* 356:517 */     return this._multiDocument;
/* 357:    */   }
/* 358:    */   
/* 359:    */   protected void setCallsNodeset(boolean flag)
/* 360:    */   {
/* 361:525 */     if (flag) {
/* 362:525 */       setMultiDocument(flag);
/* 363:    */     }
/* 364:526 */     this._callsNodeset = flag;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public boolean callsNodeset()
/* 368:    */   {
/* 369:530 */     return this._callsNodeset;
/* 370:    */   }
/* 371:    */   
/* 372:    */   protected void setHasIdCall(boolean flag)
/* 373:    */   {
/* 374:534 */     this._hasIdCall = flag;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public boolean hasIdCall()
/* 378:    */   {
/* 379:538 */     return this._hasIdCall;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setClassName(String className)
/* 383:    */   {
/* 384:548 */     String base = Util.baseName(className);
/* 385:549 */     String noext = Util.noExtName(base);
/* 386:550 */     String name = Util.toJavaName(noext);
/* 387:552 */     if (this._packageName == null) {
/* 388:553 */       this._className = name;
/* 389:    */     } else {
/* 390:555 */       this._className = (this._packageName + '.' + name);
/* 391:    */     }
/* 392:    */   }
/* 393:    */   
/* 394:    */   public String getClassName()
/* 395:    */   {
/* 396:562 */     return this._className;
/* 397:    */   }
/* 398:    */   
/* 399:    */   private String classFileName(String className)
/* 400:    */   {
/* 401:570 */     return className.replace('.', File.separatorChar) + ".class";
/* 402:    */   }
/* 403:    */   
/* 404:    */   private File getOutputFile(String className)
/* 405:    */   {
/* 406:577 */     if (this._destDir != null) {
/* 407:578 */       return new File(this._destDir, classFileName(className));
/* 408:    */     }
/* 409:580 */     return new File(classFileName(className));
/* 410:    */   }
/* 411:    */   
/* 412:    */   public boolean setDestDirectory(String dstDirName)
/* 413:    */   {
/* 414:588 */     File dir = new File(dstDirName);
/* 415:589 */     if ((dir.exists()) || (dir.mkdirs()))
/* 416:    */     {
/* 417:590 */       this._destDir = dir;
/* 418:591 */       return true;
/* 419:    */     }
/* 420:594 */     this._destDir = null;
/* 421:595 */     return false;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setPackageName(String packageName)
/* 425:    */   {
/* 426:603 */     this._packageName = packageName;
/* 427:604 */     if (this._className != null) {
/* 428:604 */       setClassName(this._className);
/* 429:    */     }
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setJarFileName(String jarFileName)
/* 433:    */   {
/* 434:612 */     String JAR_EXT = ".jar";
/* 435:613 */     if (jarFileName.endsWith(".jar")) {
/* 436:614 */       this._jarFileName = jarFileName;
/* 437:    */     } else {
/* 438:616 */       this._jarFileName = (jarFileName + ".jar");
/* 439:    */     }
/* 440:617 */     this._outputType = 1;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public String getJarFileName()
/* 444:    */   {
/* 445:621 */     return this._jarFileName;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setStylesheet(Stylesheet stylesheet)
/* 449:    */   {
/* 450:628 */     if (this._stylesheet == null) {
/* 451:628 */       this._stylesheet = stylesheet;
/* 452:    */     }
/* 453:    */   }
/* 454:    */   
/* 455:    */   public Stylesheet getStylesheet()
/* 456:    */   {
/* 457:635 */     return this._stylesheet;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public int registerAttribute(QName name)
/* 461:    */   {
/* 462:643 */     Integer code = (Integer)this._attributes.get(name.toString());
/* 463:644 */     if (code == null)
/* 464:    */     {
/* 465:645 */       code = new Integer(this._nextGType++);
/* 466:646 */       this._attributes.put(name.toString(), code);
/* 467:647 */       String uri = name.getNamespace();
/* 468:648 */       String local = "@" + name.getLocalPart();
/* 469:649 */       if ((uri != null) && (!uri.equals(""))) {
/* 470:650 */         this._namesIndex.addElement(uri + ":" + local);
/* 471:    */       } else {
/* 472:652 */         this._namesIndex.addElement(local);
/* 473:    */       }
/* 474:653 */       if (name.getLocalPart().equals("*")) {
/* 475:654 */         registerNamespace(name.getNamespace());
/* 476:    */       }
/* 477:    */     }
/* 478:657 */     return code.intValue();
/* 479:    */   }
/* 480:    */   
/* 481:    */   public int registerElement(QName name)
/* 482:    */   {
/* 483:666 */     Integer code = (Integer)this._elements.get(name.toString());
/* 484:667 */     if (code == null)
/* 485:    */     {
/* 486:668 */       this._elements.put(name.toString(), code = new Integer(this._nextGType++));
/* 487:669 */       this._namesIndex.addElement(name.toString());
/* 488:    */     }
/* 489:671 */     if (name.getLocalPart().equals("*")) {
/* 490:672 */       registerNamespace(name.getNamespace());
/* 491:    */     }
/* 492:674 */     return code.intValue();
/* 493:    */   }
/* 494:    */   
/* 495:    */   public int registerNamespacePrefix(QName name)
/* 496:    */   {
/* 497:684 */     Integer code = (Integer)this._namespacePrefixes.get(name.toString());
/* 498:685 */     if (code == null)
/* 499:    */     {
/* 500:686 */       code = new Integer(this._nextGType++);
/* 501:687 */       this._namespacePrefixes.put(name.toString(), code);
/* 502:688 */       String uri = name.getNamespace();
/* 503:689 */       if ((uri != null) && (!uri.equals(""))) {
/* 504:691 */         this._namesIndex.addElement("?");
/* 505:    */       } else {
/* 506:693 */         this._namesIndex.addElement("?" + name.getLocalPart());
/* 507:    */       }
/* 508:    */     }
/* 509:696 */     return code.intValue();
/* 510:    */   }
/* 511:    */   
/* 512:    */   public int registerNamespacePrefix(String name)
/* 513:    */   {
/* 514:704 */     Integer code = (Integer)this._namespacePrefixes.get(name);
/* 515:705 */     if (code == null)
/* 516:    */     {
/* 517:706 */       code = new Integer(this._nextGType++);
/* 518:707 */       this._namespacePrefixes.put(name, code);
/* 519:708 */       this._namesIndex.addElement("?" + name);
/* 520:    */     }
/* 521:710 */     return code.intValue();
/* 522:    */   }
/* 523:    */   
/* 524:    */   public int registerNamespace(String namespaceURI)
/* 525:    */   {
/* 526:718 */     Integer code = (Integer)this._namespaces.get(namespaceURI);
/* 527:719 */     if (code == null)
/* 528:    */     {
/* 529:720 */       code = new Integer(this._nextNSType++);
/* 530:721 */       this._namespaces.put(namespaceURI, code);
/* 531:722 */       this._namespaceIndex.addElement(namespaceURI);
/* 532:    */     }
/* 533:724 */     return code.intValue();
/* 534:    */   }
/* 535:    */   
/* 536:    */   public int registerStylesheetPrefixMappingForRuntime(Hashtable prefixMap, int ancestorID)
/* 537:    */   {
/* 538:744 */     if (this._stylesheetNSAncestorPointers == null) {
/* 539:745 */       this._stylesheetNSAncestorPointers = new Vector();
/* 540:    */     }
/* 541:748 */     if (this._prefixURIPairs == null) {
/* 542:749 */       this._prefixURIPairs = new Vector();
/* 543:    */     }
/* 544:752 */     if (this._prefixURIPairsIdx == null) {
/* 545:753 */       this._prefixURIPairsIdx = new Vector();
/* 546:    */     }
/* 547:756 */     int currentNodeID = this._stylesheetNSAncestorPointers.size();
/* 548:757 */     this._stylesheetNSAncestorPointers.add(new Integer(ancestorID));
/* 549:    */     
/* 550:759 */     Iterator prefixMapIterator = prefixMap.entrySet().iterator();
/* 551:760 */     int prefixNSPairStartIdx = this._prefixURIPairs.size();
/* 552:761 */     this._prefixURIPairsIdx.add(new Integer(prefixNSPairStartIdx));
/* 553:763 */     while (prefixMapIterator.hasNext())
/* 554:    */     {
/* 555:764 */       Map.Entry entry = (Map.Entry)prefixMapIterator.next();
/* 556:765 */       String prefix = (String)entry.getKey();
/* 557:766 */       String uri = (String)entry.getValue();
/* 558:767 */       this._prefixURIPairs.add(prefix);
/* 559:768 */       this._prefixURIPairs.add(uri);
/* 560:    */     }
/* 561:771 */     return currentNodeID;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public Vector getNSAncestorPointers()
/* 565:    */   {
/* 566:775 */     return this._stylesheetNSAncestorPointers;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public Vector getPrefixURIPairs()
/* 570:    */   {
/* 571:779 */     return this._prefixURIPairs;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public Vector getPrefixURIPairsIdx()
/* 575:    */   {
/* 576:783 */     return this._prefixURIPairsIdx;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public int nextModeSerial()
/* 580:    */   {
/* 581:787 */     return this._modeSerial++;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public int nextStylesheetSerial()
/* 585:    */   {
/* 586:791 */     return this._stylesheetSerial++;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public int nextStepPatternSerial()
/* 590:    */   {
/* 591:795 */     return this._stepPatternSerial++;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public int[] getNumberFieldIndexes()
/* 595:    */   {
/* 596:799 */     return this._numberFieldIndexes;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public int nextHelperClassSerial()
/* 600:    */   {
/* 601:803 */     return this._helperClassSerial++;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public int nextAttributeSetSerial()
/* 605:    */   {
/* 606:807 */     return this._attributeSetSerial++;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public Vector getNamesIndex()
/* 610:    */   {
/* 611:811 */     return this._namesIndex;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public Vector getNamespaceIndex()
/* 615:    */   {
/* 616:815 */     return this._namespaceIndex;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public String getHelperClassName()
/* 620:    */   {
/* 621:823 */     return getClassName() + '$' + this._helperClassSerial++;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public void dumpClass(JavaClass clazz)
/* 625:    */   {
/* 626:828 */     if ((this._outputType == 0) || (this._outputType == 4))
/* 627:    */     {
/* 628:831 */       File outFile = getOutputFile(clazz.getClassName());
/* 629:832 */       String parentDir = outFile.getParent();
/* 630:833 */       if (parentDir != null)
/* 631:    */       {
/* 632:834 */         File parentFile = new File(parentDir);
/* 633:835 */         if (!parentFile.exists()) {
/* 634:836 */           parentFile.mkdirs();
/* 635:    */         }
/* 636:    */       }
/* 637:    */     }
/* 638:    */     try
/* 639:    */     {
/* 640:841 */       switch (this._outputType)
/* 641:    */       {
/* 642:    */       case 0: 
/* 643:843 */         clazz.dump(new BufferedOutputStream(new FileOutputStream(getOutputFile(clazz.getClassName()))));
/* 644:    */         
/* 645:    */ 
/* 646:    */ 
/* 647:847 */         break;
/* 648:    */       case 1: 
/* 649:849 */         this._bcelClasses.addElement(clazz);
/* 650:850 */         break;
/* 651:    */       case 2: 
/* 652:    */       case 3: 
/* 653:    */       case 4: 
/* 654:    */       case 5: 
/* 655:855 */         ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
/* 656:856 */         clazz.dump(out);
/* 657:857 */         this._classes.addElement(out.toByteArray());
/* 658:859 */         if (this._outputType == 4) {
/* 659:860 */           clazz.dump(new BufferedOutputStream(new FileOutputStream(getOutputFile(clazz.getClassName()))));
/* 660:862 */         } else if (this._outputType == 5) {
/* 661:863 */           this._bcelClasses.addElement(clazz);
/* 662:    */         }
/* 663:    */         break;
/* 664:    */       }
/* 665:    */     }
/* 666:    */     catch (Exception e)
/* 667:    */     {
/* 668:869 */       e.printStackTrace();
/* 669:    */     }
/* 670:    */   }
/* 671:    */   
/* 672:    */   private String entryName(File f)
/* 673:    */     throws IOException
/* 674:    */   {
/* 675:877 */     return f.getName().replace(File.separatorChar, '/');
/* 676:    */   }
/* 677:    */   
/* 678:    */   public void outputToJar()
/* 679:    */     throws IOException
/* 680:    */   {
/* 681:885 */     Manifest manifest = new Manifest();
/* 682:886 */     Attributes atrs = manifest.getMainAttributes();
/* 683:887 */     atrs.put(Attributes.Name.MANIFEST_VERSION, "1.2");
/* 684:    */     
/* 685:889 */     Map map = manifest.getEntries();
/* 686:    */     
/* 687:891 */     Enumeration classes = this._bcelClasses.elements();
/* 688:892 */     String now = new Date().toString();
/* 689:893 */     Attributes.Name dateAttr = new Attributes.Name("Date");
/* 690:895 */     while (classes.hasMoreElements())
/* 691:    */     {
/* 692:896 */       JavaClass clazz = (JavaClass)classes.nextElement();
/* 693:897 */       String className = clazz.getClassName().replace('.', '/');
/* 694:898 */       Attributes attr = new Attributes();
/* 695:899 */       attr.put(dateAttr, now);
/* 696:900 */       map.put(className + ".class", attr);
/* 697:    */     }
/* 698:903 */     File jarFile = new File(this._destDir, this._jarFileName);
/* 699:904 */     JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile), manifest);
/* 700:    */     
/* 701:906 */     classes = this._bcelClasses.elements();
/* 702:907 */     while (classes.hasMoreElements())
/* 703:    */     {
/* 704:908 */       JavaClass clazz = (JavaClass)classes.nextElement();
/* 705:909 */       String className = clazz.getClassName().replace('.', '/');
/* 706:910 */       jos.putNextEntry(new JarEntry(className + ".class"));
/* 707:911 */       ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
/* 708:912 */       clazz.dump(out);
/* 709:913 */       out.writeTo(jos);
/* 710:    */     }
/* 711:915 */     jos.close();
/* 712:    */   }
/* 713:    */   
/* 714:    */   public void setDebug(boolean debug)
/* 715:    */   {
/* 716:922 */     this._debug = debug;
/* 717:    */   }
/* 718:    */   
/* 719:    */   public boolean debug()
/* 720:    */   {
/* 721:929 */     return this._debug;
/* 722:    */   }
/* 723:    */   
/* 724:    */   public String getCharacterData(int index)
/* 725:    */   {
/* 726:942 */     return ((StringBuffer)this.m_characterData.elementAt(index)).toString();
/* 727:    */   }
/* 728:    */   
/* 729:    */   public int getCharacterDataCount()
/* 730:    */   {
/* 731:950 */     return this.m_characterData != null ? this.m_characterData.size() : 0;
/* 732:    */   }
/* 733:    */   
/* 734:    */   public int addCharacterData(String newData)
/* 735:    */   {
/* 736:    */     StringBuffer currData;
/* 737:962 */     if (this.m_characterData == null)
/* 738:    */     {
/* 739:963 */       this.m_characterData = new Vector();
/* 740:964 */       currData = new StringBuffer();
/* 741:965 */       this.m_characterData.addElement(currData);
/* 742:    */     }
/* 743:    */     else
/* 744:    */     {
/* 745:967 */       currData = (StringBuffer)this.m_characterData.elementAt(this.m_characterData.size() - 1);
/* 746:    */     }
/* 747:975 */     if (newData.length() + currData.length() > 21845)
/* 748:    */     {
/* 749:976 */       currData = new StringBuffer();
/* 750:977 */       this.m_characterData.addElement(currData);
/* 751:    */     }
/* 752:980 */     int newDataOffset = currData.length();
/* 753:981 */     currData.append(newData);
/* 754:    */     
/* 755:983 */     return newDataOffset;
/* 756:    */   }
/* 757:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.XSLTC
 * JD-Core Version:    0.7.0.1
 */