/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import java.util.Vector;
/*   7:    */ import org.apache.bcel.generic.ANEWARRAY;
/*   8:    */ import org.apache.bcel.generic.CHECKCAST;
/*   9:    */ import org.apache.bcel.generic.ClassGen;
/*  10:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*  11:    */ import org.apache.bcel.generic.DUP_X1;
/*  12:    */ import org.apache.bcel.generic.GETFIELD;
/*  13:    */ import org.apache.bcel.generic.ICONST;
/*  14:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  15:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  16:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  17:    */ import org.apache.bcel.generic.InstructionConstants;
/*  18:    */ import org.apache.bcel.generic.InstructionList;
/*  19:    */ import org.apache.bcel.generic.MethodGen;
/*  20:    */ import org.apache.bcel.generic.NEW;
/*  21:    */ import org.apache.bcel.generic.NEWARRAY;
/*  22:    */ import org.apache.bcel.generic.PUSH;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  24:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  25:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  26:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  27:    */ import org.apache.xalan.xsltc.runtime.AttributeList;
/*  28:    */ import org.xml.sax.Attributes;
/*  29:    */ 
/*  30:    */ public abstract class SyntaxTreeNode
/*  31:    */   implements Constants
/*  32:    */ {
/*  33:    */   private Parser _parser;
/*  34:    */   protected SyntaxTreeNode _parent;
/*  35:    */   private Stylesheet _stylesheet;
/*  36:    */   private Template _template;
/*  37: 72 */   private final Vector _contents = new Vector(2);
/*  38:    */   protected QName _qname;
/*  39:    */   private int _line;
/*  40: 77 */   protected AttributeList _attributes = null;
/*  41: 78 */   private Hashtable _prefixMapping = null;
/*  42:    */   public static final int UNKNOWN_STYLESHEET_NODE_ID = -1;
/*  43: 84 */   private int _nodeIDForStylesheetNSLookup = -1;
/*  44: 87 */   static final SyntaxTreeNode Dummy = new AbsolutePathPattern(null);
/*  45:    */   protected static final int IndentIncrement = 4;
/*  46: 91 */   private static final char[] _spaces = "                                                       ".toCharArray();
/*  47:    */   
/*  48:    */   public SyntaxTreeNode()
/*  49:    */   {
/*  50: 99 */     this._line = 0;
/*  51:100 */     this._qname = null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public SyntaxTreeNode(int line)
/*  55:    */   {
/*  56:108 */     this._line = line;
/*  57:109 */     this._qname = null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public SyntaxTreeNode(String uri, String prefix, String local)
/*  61:    */   {
/*  62:119 */     this._line = 0;
/*  63:120 */     setQName(uri, prefix, local);
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected final void setLineNumber(int line)
/*  67:    */   {
/*  68:128 */     this._line = line;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public final int getLineNumber()
/*  72:    */   {
/*  73:138 */     if (this._line > 0) {
/*  74:138 */       return this._line;
/*  75:    */     }
/*  76:139 */     SyntaxTreeNode parent = getParent();
/*  77:140 */     return parent != null ? parent.getLineNumber() : 0;
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void setQName(QName qname)
/*  81:    */   {
/*  82:148 */     this._qname = qname;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void setQName(String uri, String prefix, String localname)
/*  86:    */   {
/*  87:158 */     this._qname = new QName(uri, prefix, localname);
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected QName getQName()
/*  91:    */   {
/*  92:166 */     return this._qname;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void setAttributes(AttributeList attributes)
/*  96:    */   {
/*  97:175 */     this._attributes = attributes;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected String getAttribute(String qname)
/* 101:    */   {
/* 102:184 */     if (this._attributes == null) {
/* 103:185 */       return "";
/* 104:    */     }
/* 105:187 */     String value = this._attributes.getValue(qname);
/* 106:188 */     return (value == null) || (value.equals("")) ? "" : value;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected String getAttribute(String prefix, String localName)
/* 110:    */   {
/* 111:193 */     return getAttribute(prefix + ':' + localName);
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected boolean hasAttribute(String qname)
/* 115:    */   {
/* 116:197 */     return (this._attributes != null) && (this._attributes.getValue(qname) != null);
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void addAttribute(String qname, String value)
/* 120:    */   {
/* 121:201 */     this._attributes.add(qname, value);
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected Attributes getAttributes()
/* 125:    */   {
/* 126:210 */     return this._attributes;
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected void setPrefixMapping(Hashtable mapping)
/* 130:    */   {
/* 131:222 */     this._prefixMapping = mapping;
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected Hashtable getPrefixMapping()
/* 135:    */   {
/* 136:233 */     return this._prefixMapping;
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected void addPrefixMapping(String prefix, String uri)
/* 140:    */   {
/* 141:242 */     if (this._prefixMapping == null) {
/* 142:243 */       this._prefixMapping = new Hashtable();
/* 143:    */     }
/* 144:244 */     this._prefixMapping.put(prefix, uri);
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected String lookupNamespace(String prefix)
/* 148:    */   {
/* 149:257 */     String uri = null;
/* 150:260 */     if (this._prefixMapping != null) {
/* 151:261 */       uri = (String)this._prefixMapping.get(prefix);
/* 152:    */     }
/* 153:263 */     if ((uri == null) && (this._parent != null))
/* 154:    */     {
/* 155:264 */       uri = this._parent.lookupNamespace(prefix);
/* 156:265 */       if ((prefix == "") && (uri == null)) {
/* 157:266 */         uri = "";
/* 158:    */       }
/* 159:    */     }
/* 160:269 */     return uri;
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected String lookupPrefix(String uri)
/* 164:    */   {
/* 165:284 */     String prefix = null;
/* 166:287 */     if ((this._prefixMapping != null) && (this._prefixMapping.contains(uri)))
/* 167:    */     {
/* 168:289 */       Enumeration prefixes = this._prefixMapping.keys();
/* 169:290 */       while (prefixes.hasMoreElements())
/* 170:    */       {
/* 171:291 */         prefix = (String)prefixes.nextElement();
/* 172:292 */         String mapsTo = (String)this._prefixMapping.get(prefix);
/* 173:293 */         if (mapsTo.equals(uri)) {
/* 174:293 */           return prefix;
/* 175:    */         }
/* 176:    */       }
/* 177:    */     }
/* 178:297 */     else if (this._parent != null)
/* 179:    */     {
/* 180:298 */       prefix = this._parent.lookupPrefix(uri);
/* 181:299 */       if ((uri == "") && (prefix == null)) {
/* 182:300 */         prefix = "";
/* 183:    */       }
/* 184:    */     }
/* 185:302 */     return prefix;
/* 186:    */   }
/* 187:    */   
/* 188:    */   protected void setParser(Parser parser)
/* 189:    */   {
/* 190:311 */     this._parser = parser;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public final Parser getParser()
/* 194:    */   {
/* 195:319 */     return this._parser;
/* 196:    */   }
/* 197:    */   
/* 198:    */   protected void setParent(SyntaxTreeNode parent)
/* 199:    */   {
/* 200:327 */     if (this._parent == null) {
/* 201:328 */       this._parent = parent;
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected final SyntaxTreeNode getParent()
/* 206:    */   {
/* 207:336 */     return this._parent;
/* 208:    */   }
/* 209:    */   
/* 210:    */   protected final boolean isDummy()
/* 211:    */   {
/* 212:344 */     return this == Dummy;
/* 213:    */   }
/* 214:    */   
/* 215:    */   protected int getImportPrecedence()
/* 216:    */   {
/* 217:353 */     Stylesheet stylesheet = getStylesheet();
/* 218:354 */     if (stylesheet == null) {
/* 219:354 */       return -2147483648;
/* 220:    */     }
/* 221:355 */     return stylesheet.getImportPrecedence();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Stylesheet getStylesheet()
/* 225:    */   {
/* 226:364 */     if (this._stylesheet == null)
/* 227:    */     {
/* 228:365 */       SyntaxTreeNode parent = this;
/* 229:366 */       while (parent != null)
/* 230:    */       {
/* 231:367 */         if ((parent instanceof Stylesheet)) {
/* 232:368 */           return (Stylesheet)parent;
/* 233:    */         }
/* 234:369 */         parent = parent.getParent();
/* 235:    */       }
/* 236:371 */       this._stylesheet = ((Stylesheet)parent);
/* 237:    */     }
/* 238:373 */     return this._stylesheet;
/* 239:    */   }
/* 240:    */   
/* 241:    */   protected Template getTemplate()
/* 242:    */   {
/* 243:383 */     if (this._template == null)
/* 244:    */     {
/* 245:384 */       SyntaxTreeNode parent = this;
/* 246:385 */       while ((parent != null) && (!(parent instanceof Template))) {
/* 247:386 */         parent = parent.getParent();
/* 248:    */       }
/* 249:387 */       this._template = ((Template)parent);
/* 250:    */     }
/* 251:389 */     return this._template;
/* 252:    */   }
/* 253:    */   
/* 254:    */   protected final XSLTC getXSLTC()
/* 255:    */   {
/* 256:397 */     return this._parser.getXSLTC();
/* 257:    */   }
/* 258:    */   
/* 259:    */   protected final SymbolTable getSymbolTable()
/* 260:    */   {
/* 261:405 */     return this._parser == null ? null : this._parser.getSymbolTable();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void parseContents(Parser parser)
/* 265:    */   {
/* 266:416 */     parseChildren(parser);
/* 267:    */   }
/* 268:    */   
/* 269:    */   protected final void parseChildren(Parser parser)
/* 270:    */   {
/* 271:426 */     Vector locals = null;
/* 272:    */     
/* 273:428 */     int count = this._contents.size();
/* 274:429 */     for (int i = 0; i < count; i++)
/* 275:    */     {
/* 276:430 */       SyntaxTreeNode child = (SyntaxTreeNode)this._contents.elementAt(i);
/* 277:431 */       parser.getSymbolTable().setCurrentNode(child);
/* 278:432 */       child.parseContents(parser);
/* 279:    */       
/* 280:434 */       QName varOrParamName = updateScope(parser, child);
/* 281:435 */       if (varOrParamName != null)
/* 282:    */       {
/* 283:436 */         if (locals == null) {
/* 284:437 */           locals = new Vector(2);
/* 285:    */         }
/* 286:439 */         locals.addElement(varOrParamName);
/* 287:    */       }
/* 288:    */     }
/* 289:443 */     parser.getSymbolTable().setCurrentNode(this);
/* 290:446 */     if (locals != null)
/* 291:    */     {
/* 292:447 */       int nLocals = locals.size();
/* 293:448 */       for (int i = 0; i < nLocals; i++) {
/* 294:449 */         parser.removeVariable((QName)locals.elementAt(i));
/* 295:    */       }
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   protected QName updateScope(Parser parser, SyntaxTreeNode node)
/* 300:    */   {
/* 301:459 */     if ((node instanceof Variable))
/* 302:    */     {
/* 303:460 */       Variable var = (Variable)node;
/* 304:461 */       parser.addVariable(var);
/* 305:462 */       return var.getName();
/* 306:    */     }
/* 307:464 */     if ((node instanceof Param))
/* 308:    */     {
/* 309:465 */       Param param = (Param)node;
/* 310:466 */       parser.addParameter(param);
/* 311:467 */       return param.getName();
/* 312:    */     }
/* 313:470 */     return null;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public abstract org.apache.xalan.xsltc.compiler.util.Type typeCheck(SymbolTable paramSymbolTable)
/* 317:    */     throws TypeCheckError;
/* 318:    */   
/* 319:    */   protected org.apache.xalan.xsltc.compiler.util.Type typeCheckContents(SymbolTable stable)
/* 320:    */     throws TypeCheckError
/* 321:    */   {
/* 322:486 */     int n = elementCount();
/* 323:487 */     for (int i = 0; i < n; i++)
/* 324:    */     {
/* 325:488 */       SyntaxTreeNode item = (SyntaxTreeNode)this._contents.elementAt(i);
/* 326:489 */       item.typeCheck(stable);
/* 327:    */     }
/* 328:491 */     return org.apache.xalan.xsltc.compiler.util.Type.Void;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public abstract void translate(ClassGenerator paramClassGenerator, MethodGenerator paramMethodGenerator);
/* 332:    */   
/* 333:    */   protected void translateContents(ClassGenerator classGen, MethodGenerator methodGen)
/* 334:    */   {
/* 335:510 */     int n = elementCount();
/* 336:512 */     for (int i = 0; i < n; i++)
/* 337:    */     {
/* 338:513 */       methodGen.markChunkStart();
/* 339:514 */       SyntaxTreeNode item = (SyntaxTreeNode)this._contents.elementAt(i);
/* 340:515 */       item.translate(classGen, methodGen);
/* 341:516 */       methodGen.markChunkEnd();
/* 342:    */     }
/* 343:524 */     for (int i = 0; i < n; i++) {
/* 344:525 */       if ((this._contents.elementAt(i) instanceof VariableBase))
/* 345:    */       {
/* 346:526 */         VariableBase var = (VariableBase)this._contents.elementAt(i);
/* 347:527 */         var.unmapRegister(methodGen);
/* 348:    */       }
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   private boolean isSimpleRTF(SyntaxTreeNode node)
/* 353:    */   {
/* 354:542 */     Vector contents = node.getContents();
/* 355:543 */     for (int i = 0; i < contents.size(); i++)
/* 356:    */     {
/* 357:544 */       SyntaxTreeNode item = (SyntaxTreeNode)contents.elementAt(i);
/* 358:545 */       if (!isTextElement(item, false)) {
/* 359:546 */         return false;
/* 360:    */       }
/* 361:    */     }
/* 362:549 */     return true;
/* 363:    */   }
/* 364:    */   
/* 365:    */   private boolean isAdaptiveRTF(SyntaxTreeNode node)
/* 366:    */   {
/* 367:563 */     Vector contents = node.getContents();
/* 368:564 */     for (int i = 0; i < contents.size(); i++)
/* 369:    */     {
/* 370:565 */       SyntaxTreeNode item = (SyntaxTreeNode)contents.elementAt(i);
/* 371:566 */       if (!isTextElement(item, true)) {
/* 372:567 */         return false;
/* 373:    */       }
/* 374:    */     }
/* 375:570 */     return true;
/* 376:    */   }
/* 377:    */   
/* 378:    */   private boolean isTextElement(SyntaxTreeNode node, boolean doExtendedCheck)
/* 379:    */   {
/* 380:590 */     if (((node instanceof ValueOf)) || ((node instanceof Number)) || ((node instanceof Text))) {
/* 381:593 */       return true;
/* 382:    */     }
/* 383:595 */     if ((node instanceof If)) {
/* 384:596 */       return doExtendedCheck ? isAdaptiveRTF(node) : isSimpleRTF(node);
/* 385:    */     }
/* 386:598 */     if ((node instanceof Choose))
/* 387:    */     {
/* 388:599 */       Vector contents = node.getContents();
/* 389:600 */       for (int i = 0; i < contents.size(); i++)
/* 390:    */       {
/* 391:601 */         SyntaxTreeNode item = (SyntaxTreeNode)contents.elementAt(i);
/* 392:602 */         if ((!(item instanceof Text)) && (((!(item instanceof When)) && (!(item instanceof Otherwise))) || (((!doExtendedCheck) || (!isAdaptiveRTF(item))) && ((doExtendedCheck) || (!isSimpleRTF(item)))))) {
/* 393:608 */           return false;
/* 394:    */         }
/* 395:    */       }
/* 396:610 */       return true;
/* 397:    */     }
/* 398:612 */     if ((doExtendedCheck) && (((node instanceof CallTemplate)) || ((node instanceof ApplyTemplates)))) {
/* 399:615 */       return true;
/* 400:    */     }
/* 401:617 */     return false;
/* 402:    */   }
/* 403:    */   
/* 404:    */   protected void compileResultTree(ClassGenerator classGen, MethodGenerator methodGen)
/* 405:    */   {
/* 406:628 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 407:629 */     InstructionList il = methodGen.getInstructionList();
/* 408:630 */     Stylesheet stylesheet = classGen.getStylesheet();
/* 409:    */     
/* 410:632 */     boolean isSimple = isSimpleRTF(this);
/* 411:633 */     boolean isAdaptive = false;
/* 412:634 */     if (!isSimple) {
/* 413:635 */       isAdaptive = isAdaptiveRTF(this);
/* 414:    */     }
/* 415:638 */     int rtfType = isAdaptive ? 1 : isSimple ? 0 : 2;
/* 416:    */     
/* 417:    */ 
/* 418:    */ 
/* 419:642 */     il.append(methodGen.loadHandler());
/* 420:    */     
/* 421:644 */     String DOM_CLASS = classGen.getDOMClass();
/* 422:    */     
/* 423:    */ 
/* 424:    */ 
/* 425:    */ 
/* 426:    */ 
/* 427:650 */     il.append(methodGen.loadDOM());
/* 428:651 */     int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getResultTreeFrag", "(IIZ)Lorg/apache/xalan/xsltc/DOM;");
/* 429:    */     
/* 430:    */ 
/* 431:654 */     il.append(new PUSH(cpg, 32));
/* 432:655 */     il.append(new PUSH(cpg, rtfType));
/* 433:656 */     il.append(new PUSH(cpg, stylesheet.callsNodeset()));
/* 434:657 */     il.append(new INVOKEINTERFACE(index, 4));
/* 435:    */     
/* 436:659 */     il.append(InstructionConstants.DUP);
/* 437:    */     
/* 438:    */ 
/* 439:662 */     index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getOutputDomBuilder", "()" + Constants.TRANSLET_OUTPUT_SIG);
/* 440:    */     
/* 441:    */ 
/* 442:    */ 
/* 443:666 */     il.append(new INVOKEINTERFACE(index, 1));
/* 444:667 */     il.append(InstructionConstants.DUP);
/* 445:668 */     il.append(methodGen.storeHandler());
/* 446:    */     
/* 447:    */ 
/* 448:671 */     il.append(methodGen.startDocument());
/* 449:    */     
/* 450:    */ 
/* 451:674 */     translateContents(classGen, methodGen);
/* 452:    */     
/* 453:    */ 
/* 454:677 */     il.append(methodGen.loadHandler());
/* 455:678 */     il.append(methodGen.endDocument());
/* 456:683 */     if ((stylesheet.callsNodeset()) && (!DOM_CLASS.equals("org/apache/xalan/xsltc/DOM")))
/* 457:    */     {
/* 458:686 */       index = cpg.addMethodref("org/apache/xalan/xsltc/dom/DOMAdapter", "<init>", "(Lorg/apache/xalan/xsltc/DOM;[Ljava/lang/String;[Ljava/lang/String;[I[Ljava/lang/String;)V");
/* 459:    */       
/* 460:    */ 
/* 461:    */ 
/* 462:    */ 
/* 463:    */ 
/* 464:    */ 
/* 465:693 */       il.append(new NEW(cpg.addClass("org/apache/xalan/xsltc/dom/DOMAdapter")));
/* 466:694 */       il.append(new DUP_X1());
/* 467:695 */       il.append(InstructionConstants.SWAP);
/* 468:701 */       if (!stylesheet.callsNodeset())
/* 469:    */       {
/* 470:702 */         il.append(new ICONST(0));
/* 471:703 */         il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 472:704 */         il.append(InstructionConstants.DUP);
/* 473:705 */         il.append(InstructionConstants.DUP);
/* 474:706 */         il.append(new ICONST(0));
/* 475:707 */         il.append(new NEWARRAY(org.apache.bcel.generic.Type.INT));
/* 476:708 */         il.append(InstructionConstants.SWAP);
/* 477:709 */         il.append(new INVOKESPECIAL(index));
/* 478:    */       }
/* 479:    */       else
/* 480:    */       {
/* 481:713 */         il.append(InstructionConstants.ALOAD_0);
/* 482:714 */         il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "namesArray", "[Ljava/lang/String;")));
/* 483:    */         
/* 484:    */ 
/* 485:717 */         il.append(InstructionConstants.ALOAD_0);
/* 486:718 */         il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "urisArray", "[Ljava/lang/String;")));
/* 487:    */         
/* 488:    */ 
/* 489:721 */         il.append(InstructionConstants.ALOAD_0);
/* 490:722 */         il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "typesArray", "[I")));
/* 491:    */         
/* 492:    */ 
/* 493:725 */         il.append(InstructionConstants.ALOAD_0);
/* 494:726 */         il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "namespaceArray", "[Ljava/lang/String;")));
/* 495:    */         
/* 496:    */ 
/* 497:    */ 
/* 498:    */ 
/* 499:731 */         il.append(new INVOKESPECIAL(index));
/* 500:    */         
/* 501:    */ 
/* 502:734 */         il.append(InstructionConstants.DUP);
/* 503:735 */         il.append(methodGen.loadDOM());
/* 504:736 */         il.append(new CHECKCAST(cpg.addClass(classGen.getDOMClass())));
/* 505:737 */         il.append(InstructionConstants.SWAP);
/* 506:738 */         index = cpg.addMethodref("org.apache.xalan.xsltc.dom.MultiDOM", "addDOMAdapter", "(Lorg/apache/xalan/xsltc/dom/DOMAdapter;)I");
/* 507:    */         
/* 508:    */ 
/* 509:741 */         il.append(new INVOKEVIRTUAL(index));
/* 510:742 */         il.append(InstructionConstants.POP);
/* 511:    */       }
/* 512:    */     }
/* 513:747 */     il.append(InstructionConstants.SWAP);
/* 514:748 */     il.append(methodGen.storeHandler());
/* 515:    */   }
/* 516:    */   
/* 517:    */   protected final int getNodeIDForStylesheetNSLookup()
/* 518:    */   {
/* 519:758 */     if (this._nodeIDForStylesheetNSLookup == -1)
/* 520:    */     {
/* 521:759 */       Hashtable prefixMapping = getPrefixMapping();
/* 522:760 */       int parentNodeID = this._parent != null ? this._parent.getNodeIDForStylesheetNSLookup() : -1;
/* 523:767 */       if (prefixMapping == null) {
/* 524:768 */         this._nodeIDForStylesheetNSLookup = parentNodeID;
/* 525:    */       } else {
/* 526:772 */         this._nodeIDForStylesheetNSLookup = getXSLTC().registerStylesheetPrefixMappingForRuntime(prefixMapping, parentNodeID);
/* 527:    */       }
/* 528:    */     }
/* 529:778 */     return this._nodeIDForStylesheetNSLookup;
/* 530:    */   }
/* 531:    */   
/* 532:    */   protected boolean contextDependent()
/* 533:    */   {
/* 534:788 */     return true;
/* 535:    */   }
/* 536:    */   
/* 537:    */   protected boolean dependentContents()
/* 538:    */   {
/* 539:797 */     int n = elementCount();
/* 540:798 */     for (int i = 0; i < n; i++)
/* 541:    */     {
/* 542:799 */       SyntaxTreeNode item = (SyntaxTreeNode)this._contents.elementAt(i);
/* 543:800 */       if (item.contextDependent()) {
/* 544:801 */         return true;
/* 545:    */       }
/* 546:    */     }
/* 547:804 */     return false;
/* 548:    */   }
/* 549:    */   
/* 550:    */   protected final void addElement(SyntaxTreeNode element)
/* 551:    */   {
/* 552:812 */     this._contents.addElement(element);
/* 553:813 */     element.setParent(this);
/* 554:    */   }
/* 555:    */   
/* 556:    */   protected final void setFirstElement(SyntaxTreeNode element)
/* 557:    */   {
/* 558:822 */     this._contents.insertElementAt(element, 0);
/* 559:823 */     element.setParent(this);
/* 560:    */   }
/* 561:    */   
/* 562:    */   protected final void removeElement(SyntaxTreeNode element)
/* 563:    */   {
/* 564:831 */     this._contents.remove(element);
/* 565:832 */     element.setParent(null);
/* 566:    */   }
/* 567:    */   
/* 568:    */   protected final Vector getContents()
/* 569:    */   {
/* 570:840 */     return this._contents;
/* 571:    */   }
/* 572:    */   
/* 573:    */   protected final boolean hasContents()
/* 574:    */   {
/* 575:848 */     return elementCount() > 0;
/* 576:    */   }
/* 577:    */   
/* 578:    */   protected final int elementCount()
/* 579:    */   {
/* 580:856 */     return this._contents.size();
/* 581:    */   }
/* 582:    */   
/* 583:    */   protected final Enumeration elements()
/* 584:    */   {
/* 585:864 */     return this._contents.elements();
/* 586:    */   }
/* 587:    */   
/* 588:    */   protected final Object elementAt(int pos)
/* 589:    */   {
/* 590:873 */     return this._contents.elementAt(pos);
/* 591:    */   }
/* 592:    */   
/* 593:    */   protected final SyntaxTreeNode lastChild()
/* 594:    */   {
/* 595:881 */     if (this._contents.size() == 0) {
/* 596:881 */       return null;
/* 597:    */     }
/* 598:882 */     return (SyntaxTreeNode)this._contents.lastElement();
/* 599:    */   }
/* 600:    */   
/* 601:    */   public void display(int indent)
/* 602:    */   {
/* 603:892 */     displayContents(indent);
/* 604:    */   }
/* 605:    */   
/* 606:    */   protected void displayContents(int indent)
/* 607:    */   {
/* 608:901 */     int n = elementCount();
/* 609:902 */     for (int i = 0; i < n; i++)
/* 610:    */     {
/* 611:903 */       SyntaxTreeNode item = (SyntaxTreeNode)this._contents.elementAt(i);
/* 612:904 */       item.display(indent);
/* 613:    */     }
/* 614:    */   }
/* 615:    */   
/* 616:    */   protected final void indent(int indent)
/* 617:    */   {
/* 618:913 */     System.out.print(new String(_spaces, 0, indent));
/* 619:    */   }
/* 620:    */   
/* 621:    */   protected void reportError(SyntaxTreeNode element, Parser parser, String errorCode, String message)
/* 622:    */   {
/* 623:926 */     ErrorMsg error = new ErrorMsg(errorCode, message, element);
/* 624:927 */     parser.reportError(3, error);
/* 625:    */   }
/* 626:    */   
/* 627:    */   protected void reportWarning(SyntaxTreeNode element, Parser parser, String errorCode, String message)
/* 628:    */   {
/* 629:940 */     ErrorMsg error = new ErrorMsg(errorCode, message, element);
/* 630:941 */     parser.reportError(4, error);
/* 631:    */   }
/* 632:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.SyntaxTreeNode
 * JD-Core Version:    0.7.0.1
 */