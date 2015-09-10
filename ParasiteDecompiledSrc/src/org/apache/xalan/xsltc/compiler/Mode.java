/*    1:     */ package org.apache.xalan.xsltc.compiler;
/*    2:     */ 
/*    3:     */ import java.util.Enumeration;
/*    4:     */ import java.util.Hashtable;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.Vector;
/*    7:     */ import org.apache.bcel.generic.ALOAD;
/*    8:     */ import org.apache.bcel.generic.BranchHandle;
/*    9:     */ import org.apache.bcel.generic.ClassGen;
/*   10:     */ import org.apache.bcel.generic.ConstantPoolGen;
/*   11:     */ import org.apache.bcel.generic.DUP;
/*   12:     */ import org.apache.bcel.generic.GOTO_W;
/*   13:     */ import org.apache.bcel.generic.IFLT;
/*   14:     */ import org.apache.bcel.generic.ILOAD;
/*   15:     */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   16:     */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   17:     */ import org.apache.bcel.generic.ISTORE;
/*   18:     */ import org.apache.bcel.generic.Instruction;
/*   19:     */ import org.apache.bcel.generic.InstructionConstants;
/*   20:     */ import org.apache.bcel.generic.InstructionHandle;
/*   21:     */ import org.apache.bcel.generic.InstructionList;
/*   22:     */ import org.apache.bcel.generic.LocalVariableGen;
/*   23:     */ import org.apache.bcel.generic.LocalVariableInstruction;
/*   24:     */ import org.apache.bcel.generic.MethodGen;
/*   25:     */ import org.apache.bcel.generic.SWITCH;
/*   26:     */ import org.apache.bcel.generic.TargetLostException;
/*   27:     */ import org.apache.bcel.generic.Type;
/*   28:     */ import org.apache.bcel.util.InstructionFinder;
/*   29:     */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   30:     */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*   31:     */ import org.apache.xalan.xsltc.compiler.util.NamedMethodGenerator;
/*   32:     */ import org.apache.xalan.xsltc.compiler.util.Util;
/*   33:     */ 
/*   34:     */ final class Mode
/*   35:     */   implements Constants
/*   36:     */ {
/*   37:     */   private final QName _name;
/*   38:     */   private final Stylesheet _stylesheet;
/*   39:     */   private final String _methodName;
/*   40:     */   private Vector _templates;
/*   41:  88 */   private Vector _childNodeGroup = null;
/*   42:  93 */   private TestSeq _childNodeTestSeq = null;
/*   43:  98 */   private Vector _attribNodeGroup = null;
/*   44: 103 */   private TestSeq _attribNodeTestSeq = null;
/*   45: 108 */   private Vector _idxGroup = null;
/*   46: 113 */   private TestSeq _idxTestSeq = null;
/*   47:     */   private Vector[] _patternGroups;
/*   48:     */   private TestSeq[] _testSeq;
/*   49: 129 */   private Hashtable _neededTemplates = new Hashtable();
/*   50: 134 */   private Hashtable _namedTemplates = new Hashtable();
/*   51: 139 */   private Hashtable _templateIHs = new Hashtable();
/*   52: 144 */   private Hashtable _templateILs = new Hashtable();
/*   53: 149 */   private LocationPathPattern _rootPattern = null;
/*   54: 155 */   private Hashtable _importLevels = null;
/*   55: 160 */   private Hashtable _keys = null;
/*   56:     */   private int _currentIndex;
/*   57:     */   
/*   58:     */   public Mode(QName name, Stylesheet stylesheet, String suffix)
/*   59:     */   {
/*   60: 176 */     this._name = name;
/*   61: 177 */     this._stylesheet = stylesheet;
/*   62: 178 */     this._methodName = ("applyTemplates" + suffix);
/*   63: 179 */     this._templates = new Vector();
/*   64: 180 */     this._patternGroups = new Vector[32];
/*   65:     */   }
/*   66:     */   
/*   67:     */   public String functionName()
/*   68:     */   {
/*   69: 191 */     return this._methodName;
/*   70:     */   }
/*   71:     */   
/*   72:     */   public String functionName(int min, int max)
/*   73:     */   {
/*   74: 195 */     if (this._importLevels == null) {
/*   75: 196 */       this._importLevels = new Hashtable();
/*   76:     */     }
/*   77: 198 */     this._importLevels.put(new Integer(max), new Integer(min));
/*   78: 199 */     return this._methodName + '_' + max;
/*   79:     */   }
/*   80:     */   
/*   81:     */   private String getClassName()
/*   82:     */   {
/*   83: 206 */     return this._stylesheet.getClassName();
/*   84:     */   }
/*   85:     */   
/*   86:     */   public Stylesheet getStylesheet()
/*   87:     */   {
/*   88: 210 */     return this._stylesheet;
/*   89:     */   }
/*   90:     */   
/*   91:     */   public void addTemplate(Template template)
/*   92:     */   {
/*   93: 214 */     this._templates.addElement(template);
/*   94:     */   }
/*   95:     */   
/*   96:     */   private Vector quicksort(Vector templates, int p, int r)
/*   97:     */   {
/*   98: 218 */     if (p < r)
/*   99:     */     {
/*  100: 219 */       int q = partition(templates, p, r);
/*  101: 220 */       quicksort(templates, p, q);
/*  102: 221 */       quicksort(templates, q + 1, r);
/*  103:     */     }
/*  104: 223 */     return templates;
/*  105:     */   }
/*  106:     */   
/*  107:     */   private int partition(Vector templates, int p, int r)
/*  108:     */   {
/*  109: 227 */     Template x = (Template)templates.elementAt(p);
/*  110: 228 */     int i = p - 1;
/*  111: 229 */     int j = r + 1;
/*  112:     */     for (;;)
/*  113:     */     {
/*  114: 231 */       if (x.compareTo((Template)templates.elementAt(--j)) <= 0)
/*  115:     */       {
/*  116: 232 */         while ((goto 49) || (x.compareTo((Template)templates.elementAt(++i)) < 0)) {}
/*  117: 233 */         if (i >= j) {
/*  118:     */           break;
/*  119:     */         }
/*  120: 234 */         templates.set(j, templates.set(i, templates.elementAt(j)));
/*  121:     */       }
/*  122:     */     }
/*  123: 237 */     return j;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public void processPatterns(Hashtable keys)
/*  127:     */   {
/*  128: 246 */     this._keys = keys;
/*  129:     */     
/*  130:     */ 
/*  131:     */ 
/*  132:     */ 
/*  133:     */ 
/*  134:     */ 
/*  135:     */ 
/*  136:     */ 
/*  137:     */ 
/*  138:     */ 
/*  139:     */ 
/*  140: 258 */     this._templates = quicksort(this._templates, 0, this._templates.size() - 1);
/*  141:     */     
/*  142:     */ 
/*  143:     */ 
/*  144:     */ 
/*  145:     */ 
/*  146:     */ 
/*  147:     */ 
/*  148:     */ 
/*  149:     */ 
/*  150:     */ 
/*  151:     */ 
/*  152:     */ 
/*  153: 271 */     Enumeration templates = this._templates.elements();
/*  154: 272 */     while (templates.hasMoreElements())
/*  155:     */     {
/*  156: 274 */       Template template = (Template)templates.nextElement();
/*  157: 281 */       if ((template.isNamed()) && (!template.disabled())) {
/*  158: 282 */         this._namedTemplates.put(template, this);
/*  159:     */       }
/*  160: 286 */       Pattern pattern = template.getPattern();
/*  161: 287 */       if (pattern != null) {
/*  162: 288 */         flattenAlternative(pattern, template, keys);
/*  163:     */       }
/*  164:     */     }
/*  165: 291 */     prepareTestSequences();
/*  166:     */   }
/*  167:     */   
/*  168:     */   private void flattenAlternative(Pattern pattern, Template template, Hashtable keys)
/*  169:     */   {
/*  170: 305 */     if ((pattern instanceof IdKeyPattern))
/*  171:     */     {
/*  172: 306 */       IdKeyPattern idkey = (IdKeyPattern)pattern;
/*  173: 307 */       idkey.setTemplate(template);
/*  174: 308 */       if (this._idxGroup == null) {
/*  175: 308 */         this._idxGroup = new Vector();
/*  176:     */       }
/*  177: 309 */       this._idxGroup.add(pattern);
/*  178:     */     }
/*  179: 312 */     else if ((pattern instanceof AlternativePattern))
/*  180:     */     {
/*  181: 313 */       AlternativePattern alt = (AlternativePattern)pattern;
/*  182: 314 */       flattenAlternative(alt.getLeft(), template, keys);
/*  183: 315 */       flattenAlternative(alt.getRight(), template, keys);
/*  184:     */     }
/*  185: 318 */     else if ((pattern instanceof LocationPathPattern))
/*  186:     */     {
/*  187: 319 */       LocationPathPattern lpp = (LocationPathPattern)pattern;
/*  188: 320 */       lpp.setTemplate(template);
/*  189: 321 */       addPatternToGroup(lpp);
/*  190:     */     }
/*  191:     */   }
/*  192:     */   
/*  193:     */   private void addPatternToGroup(LocationPathPattern lpp)
/*  194:     */   {
/*  195: 331 */     if ((lpp instanceof IdKeyPattern))
/*  196:     */     {
/*  197: 332 */       addPattern(-1, lpp);
/*  198:     */     }
/*  199:     */     else
/*  200:     */     {
/*  201: 337 */       StepPattern kernel = lpp.getKernelPattern();
/*  202: 338 */       if (kernel != null) {
/*  203: 339 */         addPattern(kernel.getNodeType(), lpp);
/*  204: 341 */       } else if ((this._rootPattern == null) || (lpp.noSmallerThan(this._rootPattern))) {
/*  205: 343 */         this._rootPattern = lpp;
/*  206:     */       }
/*  207:     */     }
/*  208:     */   }
/*  209:     */   
/*  210:     */   private void addPattern(int kernelType, LocationPathPattern pattern)
/*  211:     */   {
/*  212: 353 */     int oldLength = this._patternGroups.length;
/*  213: 354 */     if (kernelType >= oldLength)
/*  214:     */     {
/*  215: 355 */       Vector[] newGroups = new Vector[kernelType * 2];
/*  216: 356 */       System.arraycopy(this._patternGroups, 0, newGroups, 0, oldLength);
/*  217: 357 */       this._patternGroups = newGroups;
/*  218:     */     }
/*  219:     */     Vector patterns;
/*  220: 363 */     if (kernelType == -1)
/*  221:     */     {
/*  222: 364 */       if (pattern.getAxis() == 2) {
/*  223: 365 */         patterns = this._attribNodeGroup == null ? (this._attribNodeGroup = new Vector(2)) : this._attribNodeGroup;
/*  224:     */       } else {
/*  225: 369 */         patterns = this._childNodeGroup == null ? (this._childNodeGroup = new Vector(2)) : this._childNodeGroup;
/*  226:     */       }
/*  227:     */     }
/*  228:     */     else {
/*  229: 374 */       patterns = this._patternGroups[kernelType] == null ? (this._patternGroups[kernelType] =  = new Vector(2)) : this._patternGroups[kernelType];
/*  230:     */     }
/*  231: 379 */     if (patterns.size() == 0)
/*  232:     */     {
/*  233: 380 */       patterns.addElement(pattern);
/*  234:     */     }
/*  235:     */     else
/*  236:     */     {
/*  237: 383 */       boolean inserted = false;
/*  238: 384 */       for (int i = 0; i < patterns.size(); i++)
/*  239:     */       {
/*  240: 385 */         LocationPathPattern lppToCompare = (LocationPathPattern)patterns.elementAt(i);
/*  241: 388 */         if (pattern.noSmallerThan(lppToCompare))
/*  242:     */         {
/*  243: 389 */           inserted = true;
/*  244: 390 */           patterns.insertElementAt(pattern, i);
/*  245: 391 */           break;
/*  246:     */         }
/*  247:     */       }
/*  248: 394 */       if (!inserted) {
/*  249: 395 */         patterns.addElement(pattern);
/*  250:     */       }
/*  251:     */     }
/*  252:     */   }
/*  253:     */   
/*  254:     */   private void completeTestSequences(int nodeType, Vector patterns)
/*  255:     */   {
/*  256: 405 */     if (patterns != null) {
/*  257: 406 */       if (this._patternGroups[nodeType] == null)
/*  258:     */       {
/*  259: 407 */         this._patternGroups[nodeType] = patterns;
/*  260:     */       }
/*  261:     */       else
/*  262:     */       {
/*  263: 410 */         int m = patterns.size();
/*  264: 411 */         for (int j = 0; j < m; j++) {
/*  265: 412 */           addPattern(nodeType, (LocationPathPattern)patterns.elementAt(j));
/*  266:     */         }
/*  267:     */       }
/*  268:     */     }
/*  269:     */   }
/*  270:     */   
/*  271:     */   private void prepareTestSequences()
/*  272:     */   {
/*  273: 425 */     Vector starGroup = this._patternGroups[1];
/*  274: 426 */     Vector atStarGroup = this._patternGroups[2];
/*  275:     */     
/*  276:     */ 
/*  277: 429 */     completeTestSequences(3, this._childNodeGroup);
/*  278:     */     
/*  279:     */ 
/*  280: 432 */     completeTestSequences(1, this._childNodeGroup);
/*  281:     */     
/*  282:     */ 
/*  283: 435 */     completeTestSequences(7, this._childNodeGroup);
/*  284:     */     
/*  285:     */ 
/*  286: 438 */     completeTestSequences(8, this._childNodeGroup);
/*  287:     */     
/*  288:     */ 
/*  289: 441 */     completeTestSequences(2, this._attribNodeGroup);
/*  290:     */     
/*  291: 443 */     Vector names = this._stylesheet.getXSLTC().getNamesIndex();
/*  292: 444 */     if ((starGroup != null) || (atStarGroup != null) || (this._childNodeGroup != null) || (this._attribNodeGroup != null))
/*  293:     */     {
/*  294: 447 */       int n = this._patternGroups.length;
/*  295: 450 */       for (int i = 14; i < n; i++) {
/*  296: 451 */         if (this._patternGroups[i] != null)
/*  297:     */         {
/*  298: 453 */           String name = (String)names.elementAt(i - 14);
/*  299: 455 */           if (isAttributeName(name))
/*  300:     */           {
/*  301: 457 */             completeTestSequences(i, atStarGroup);
/*  302:     */             
/*  303:     */ 
/*  304: 460 */             completeTestSequences(i, this._attribNodeGroup);
/*  305:     */           }
/*  306:     */           else
/*  307:     */           {
/*  308: 464 */             completeTestSequences(i, starGroup);
/*  309:     */             
/*  310:     */ 
/*  311: 467 */             completeTestSequences(i, this._childNodeGroup);
/*  312:     */           }
/*  313:     */         }
/*  314:     */       }
/*  315:     */     }
/*  316: 472 */     this._testSeq = new TestSeq[14 + names.size()];
/*  317:     */     
/*  318: 474 */     int n = this._patternGroups.length;
/*  319: 475 */     for (int i = 0; i < n; i++)
/*  320:     */     {
/*  321: 476 */       Vector patterns = this._patternGroups[i];
/*  322: 477 */       if (patterns != null)
/*  323:     */       {
/*  324: 478 */         TestSeq testSeq = new TestSeq(patterns, i, this);
/*  325:     */         
/*  326: 480 */         testSeq.reduce();
/*  327: 481 */         this._testSeq[i] = testSeq;
/*  328: 482 */         testSeq.findTemplates(this._neededTemplates);
/*  329:     */       }
/*  330:     */     }
/*  331: 486 */     if ((this._childNodeGroup != null) && (this._childNodeGroup.size() > 0))
/*  332:     */     {
/*  333: 487 */       this._childNodeTestSeq = new TestSeq(this._childNodeGroup, -1, this);
/*  334: 488 */       this._childNodeTestSeq.reduce();
/*  335: 489 */       this._childNodeTestSeq.findTemplates(this._neededTemplates);
/*  336:     */     }
/*  337: 500 */     if ((this._idxGroup != null) && (this._idxGroup.size() > 0))
/*  338:     */     {
/*  339: 501 */       this._idxTestSeq = new TestSeq(this._idxGroup, this);
/*  340: 502 */       this._idxTestSeq.reduce();
/*  341: 503 */       this._idxTestSeq.findTemplates(this._neededTemplates);
/*  342:     */     }
/*  343: 506 */     if (this._rootPattern != null) {
/*  344: 508 */       this._neededTemplates.put(this._rootPattern.getTemplate(), this);
/*  345:     */     }
/*  346:     */   }
/*  347:     */   
/*  348:     */   private void compileNamedTemplate(Template template, ClassGenerator classGen)
/*  349:     */   {
/*  350: 514 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  351: 515 */     InstructionList il = new InstructionList();
/*  352: 516 */     String methodName = Util.escape(template.getName().toString());
/*  353:     */     
/*  354: 518 */     int numParams = 0;
/*  355: 519 */     if (template.isSimpleNamedTemplate())
/*  356:     */     {
/*  357: 520 */       Vector parameters = template.getParameters();
/*  358: 521 */       numParams = parameters.size();
/*  359:     */     }
/*  360: 525 */     Type[] types = new Type[4 + numParams];
/*  361:     */     
/*  362: 527 */     String[] names = new String[4 + numParams];
/*  363: 528 */     types[0] = Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;");
/*  364: 529 */     types[1] = Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  365: 530 */     types[2] = Util.getJCRefType(Constants.TRANSLET_OUTPUT_SIG);
/*  366: 531 */     types[3] = Type.INT;
/*  367: 532 */     names[0] = "document";
/*  368: 533 */     names[1] = "iterator";
/*  369: 534 */     names[2] = "handler";
/*  370: 535 */     names[3] = "node";
/*  371: 540 */     for (int i = 4; i < 4 + numParams; i++)
/*  372:     */     {
/*  373: 541 */       types[i] = Util.getJCRefType("Ljava/lang/Object;");
/*  374: 542 */       names[i] = ("param" + String.valueOf(i - 4));
/*  375:     */     }
/*  376: 545 */     NamedMethodGenerator methodGen = new NamedMethodGenerator(1, Type.VOID, types, names, methodName, getClassName(), il, cpg);
/*  377:     */     
/*  378:     */ 
/*  379:     */ 
/*  380:     */ 
/*  381:     */ 
/*  382: 551 */     il.append(template.compile(classGen, methodGen));
/*  383: 552 */     il.append(InstructionConstants.RETURN);
/*  384:     */     
/*  385: 554 */     classGen.addMethod(methodGen);
/*  386:     */   }
/*  387:     */   
/*  388:     */   private void compileTemplates(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle next)
/*  389:     */   {
/*  390: 561 */     Enumeration templates = this._namedTemplates.keys();
/*  391: 562 */     while (templates.hasMoreElements())
/*  392:     */     {
/*  393: 563 */       Template template = (Template)templates.nextElement();
/*  394: 564 */       compileNamedTemplate(template, classGen);
/*  395:     */     }
/*  396: 567 */     templates = this._neededTemplates.keys();
/*  397: 568 */     while (templates.hasMoreElements())
/*  398:     */     {
/*  399: 569 */       Template template = (Template)templates.nextElement();
/*  400: 570 */       if (template.hasContents())
/*  401:     */       {
/*  402: 572 */         InstructionList til = template.compile(classGen, methodGen);
/*  403: 573 */         til.append(new GOTO_W(next));
/*  404: 574 */         this._templateILs.put(template, til);
/*  405: 575 */         this._templateIHs.put(template, til.getStart());
/*  406:     */       }
/*  407:     */       else
/*  408:     */       {
/*  409: 579 */         this._templateIHs.put(template, next);
/*  410:     */       }
/*  411:     */     }
/*  412:     */   }
/*  413:     */   
/*  414:     */   private void appendTemplateCode(InstructionList body)
/*  415:     */   {
/*  416: 585 */     Enumeration templates = this._neededTemplates.keys();
/*  417: 586 */     while (templates.hasMoreElements())
/*  418:     */     {
/*  419: 587 */       Object iList = this._templateILs.get(templates.nextElement());
/*  420: 589 */       if (iList != null) {
/*  421: 590 */         body.append((InstructionList)iList);
/*  422:     */       }
/*  423:     */     }
/*  424:     */   }
/*  425:     */   
/*  426:     */   private void appendTestSequences(InstructionList body)
/*  427:     */   {
/*  428: 596 */     int n = this._testSeq.length;
/*  429: 597 */     for (int i = 0; i < n; i++)
/*  430:     */     {
/*  431: 598 */       TestSeq testSeq = this._testSeq[i];
/*  432: 599 */       if (testSeq != null)
/*  433:     */       {
/*  434: 600 */         InstructionList il = testSeq.getInstructionList();
/*  435: 601 */         if (il != null) {
/*  436: 602 */           body.append(il);
/*  437:     */         }
/*  438:     */       }
/*  439:     */     }
/*  440:     */   }
/*  441:     */   
/*  442:     */   public static void compileGetChildren(ClassGenerator classGen, MethodGenerator methodGen, int node)
/*  443:     */   {
/*  444: 611 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  445: 612 */     InstructionList il = methodGen.getInstructionList();
/*  446: 613 */     int git = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getChildren", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  447:     */     
/*  448:     */ 
/*  449: 616 */     il.append(methodGen.loadDOM());
/*  450: 617 */     il.append(new ILOAD(node));
/*  451: 618 */     il.append(new INVOKEINTERFACE(git, 2));
/*  452:     */   }
/*  453:     */   
/*  454:     */   private InstructionList compileDefaultRecursion(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle next)
/*  455:     */   {
/*  456: 627 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  457: 628 */     InstructionList il = new InstructionList();
/*  458: 629 */     String applyTemplatesSig = classGen.getApplyTemplatesSig();
/*  459: 630 */     int git = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getChildren", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  460:     */     
/*  461:     */ 
/*  462: 633 */     int applyTemplates = cpg.addMethodref(getClassName(), functionName(), applyTemplatesSig);
/*  463:     */     
/*  464:     */ 
/*  465: 636 */     il.append(classGen.loadTranslet());
/*  466: 637 */     il.append(methodGen.loadDOM());
/*  467:     */     
/*  468: 639 */     il.append(methodGen.loadDOM());
/*  469: 640 */     il.append(new ILOAD(this._currentIndex));
/*  470: 641 */     il.append(new INVOKEINTERFACE(git, 2));
/*  471: 642 */     il.append(methodGen.loadHandler());
/*  472: 643 */     il.append(new INVOKEVIRTUAL(applyTemplates));
/*  473: 644 */     il.append(new GOTO_W(next));
/*  474: 645 */     return il;
/*  475:     */   }
/*  476:     */   
/*  477:     */   private InstructionList compileDefaultText(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle next)
/*  478:     */   {
/*  479: 655 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  480: 656 */     InstructionList il = new InstructionList();
/*  481:     */     
/*  482: 658 */     int chars = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "characters", Constants.CHARACTERS_SIG);
/*  483:     */     
/*  484:     */ 
/*  485: 661 */     il.append(methodGen.loadDOM());
/*  486: 662 */     il.append(new ILOAD(this._currentIndex));
/*  487: 663 */     il.append(methodGen.loadHandler());
/*  488: 664 */     il.append(new INVOKEINTERFACE(chars, 3));
/*  489: 665 */     il.append(new GOTO_W(next));
/*  490: 666 */     return il;
/*  491:     */   }
/*  492:     */   
/*  493:     */   private InstructionList compileNamespaces(ClassGenerator classGen, MethodGenerator methodGen, boolean[] isNamespace, boolean[] isAttribute, boolean attrFlag, InstructionHandle defaultTarget)
/*  494:     */   {
/*  495: 675 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/*  496: 676 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  497:     */     
/*  498:     */ 
/*  499: 679 */     Vector namespaces = xsltc.getNamespaceIndex();
/*  500: 680 */     Vector names = xsltc.getNamesIndex();
/*  501: 681 */     int namespaceCount = namespaces.size() + 1;
/*  502: 682 */     int namesCount = names.size();
/*  503:     */     
/*  504: 684 */     InstructionList il = new InstructionList();
/*  505: 685 */     int[] types = new int[namespaceCount];
/*  506: 686 */     InstructionHandle[] targets = new InstructionHandle[types.length];
/*  507: 688 */     if (namespaceCount > 0)
/*  508:     */     {
/*  509: 689 */       boolean compiled = false;
/*  510: 692 */       for (int i = 0; i < namespaceCount; i++)
/*  511:     */       {
/*  512: 693 */         targets[i] = defaultTarget;
/*  513: 694 */         types[i] = i;
/*  514:     */       }
/*  515: 698 */       for (int i = 14; i < 14 + namesCount; i++) {
/*  516: 699 */         if ((isNamespace[i] != 0) && (isAttribute[i] == attrFlag))
/*  517:     */         {
/*  518: 700 */           String name = (String)names.elementAt(i - 14);
/*  519: 701 */           String namespace = name.substring(0, name.lastIndexOf(':'));
/*  520: 702 */           int type = xsltc.registerNamespace(namespace);
/*  521: 704 */           if ((i < this._testSeq.length) && (this._testSeq[i] != null))
/*  522:     */           {
/*  523: 706 */             targets[type] = this._testSeq[i].compile(classGen, methodGen, defaultTarget);
/*  524:     */             
/*  525:     */ 
/*  526:     */ 
/*  527: 710 */             compiled = true;
/*  528:     */           }
/*  529:     */         }
/*  530:     */       }
/*  531: 716 */       if (!compiled) {
/*  532: 716 */         return null;
/*  533:     */       }
/*  534: 719 */       int getNS = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNamespaceType", "(I)I");
/*  535:     */       
/*  536:     */ 
/*  537: 722 */       il.append(methodGen.loadDOM());
/*  538: 723 */       il.append(new ILOAD(this._currentIndex));
/*  539: 724 */       il.append(new INVOKEINTERFACE(getNS, 2));
/*  540: 725 */       il.append(new SWITCH(types, targets, defaultTarget));
/*  541: 726 */       return il;
/*  542:     */     }
/*  543: 729 */     return null;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void compileApplyTemplates(ClassGenerator classGen)
/*  547:     */   {
/*  548: 738 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/*  549: 739 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  550: 740 */     Vector names = xsltc.getNamesIndex();
/*  551:     */     
/*  552:     */ 
/*  553: 743 */     Type[] argTypes = new Type[3];
/*  554:     */     
/*  555: 745 */     argTypes[0] = Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;");
/*  556: 746 */     argTypes[1] = Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  557: 747 */     argTypes[2] = Util.getJCRefType(Constants.TRANSLET_OUTPUT_SIG);
/*  558:     */     
/*  559: 749 */     String[] argNames = new String[3];
/*  560: 750 */     argNames[0] = "document";
/*  561: 751 */     argNames[1] = "iterator";
/*  562: 752 */     argNames[2] = "handler";
/*  563:     */     
/*  564: 754 */     InstructionList mainIL = new InstructionList();
/*  565:     */     
/*  566: 756 */     MethodGenerator methodGen = new MethodGenerator(17, Type.VOID, argTypes, argNames, functionName(), getClassName(), mainIL, classGen.getConstantPool());
/*  567:     */     
/*  568:     */ 
/*  569:     */ 
/*  570:     */ 
/*  571:     */ 
/*  572: 762 */     methodGen.addException("org.apache.xalan.xsltc.TransletException");
/*  573:     */     
/*  574:     */ 
/*  575:     */ 
/*  576: 766 */     mainIL.append(InstructionConstants.NOP);
/*  577:     */     
/*  578:     */ 
/*  579:     */ 
/*  580: 770 */     LocalVariableGen current = methodGen.addLocalVariable2("current", Type.INT, null);
/*  581:     */     
/*  582:     */ 
/*  583: 773 */     this._currentIndex = current.getIndex();
/*  584:     */     
/*  585:     */ 
/*  586:     */ 
/*  587: 777 */     InstructionList body = new InstructionList();
/*  588: 778 */     body.append(InstructionConstants.NOP);
/*  589:     */     
/*  590:     */ 
/*  591:     */ 
/*  592: 782 */     InstructionList ilLoop = new InstructionList();
/*  593: 783 */     ilLoop.append(methodGen.loadIterator());
/*  594: 784 */     ilLoop.append(methodGen.nextNode());
/*  595: 785 */     ilLoop.append(InstructionConstants.DUP);
/*  596: 786 */     ilLoop.append(new ISTORE(this._currentIndex));
/*  597:     */     
/*  598:     */ 
/*  599:     */ 
/*  600: 790 */     BranchHandle ifeq = ilLoop.append(new IFLT(null));
/*  601: 791 */     BranchHandle loop = ilLoop.append(new GOTO_W(null));
/*  602: 792 */     ifeq.setTarget(ilLoop.append(InstructionConstants.RETURN));
/*  603: 793 */     InstructionHandle ihLoop = ilLoop.getStart();
/*  604:     */     
/*  605: 795 */     current.setStart(mainIL.append(new GOTO_W(ihLoop)));
/*  606:     */     
/*  607:     */ 
/*  608: 798 */     current.setEnd(loop);
/*  609:     */     
/*  610:     */ 
/*  611: 801 */     InstructionList ilRecurse = compileDefaultRecursion(classGen, methodGen, ihLoop);
/*  612:     */     
/*  613: 803 */     InstructionHandle ihRecurse = ilRecurse.getStart();
/*  614:     */     
/*  615:     */ 
/*  616: 806 */     InstructionList ilText = compileDefaultText(classGen, methodGen, ihLoop);
/*  617:     */     
/*  618: 808 */     InstructionHandle ihText = ilText.getStart();
/*  619:     */     
/*  620:     */ 
/*  621: 811 */     int[] types = new int[14 + names.size()];
/*  622: 812 */     for (int i = 0; i < types.length; i++) {
/*  623: 813 */       types[i] = i;
/*  624:     */     }
/*  625: 817 */     boolean[] isAttribute = new boolean[types.length];
/*  626: 818 */     boolean[] isNamespace = new boolean[types.length];
/*  627: 819 */     for (int i = 0; i < names.size(); i++)
/*  628:     */     {
/*  629: 820 */       String name = (String)names.elementAt(i);
/*  630: 821 */       isAttribute[(i + 14)] = isAttributeName(name);
/*  631: 822 */       isNamespace[(i + 14)] = isNamespaceName(name);
/*  632:     */     }
/*  633: 826 */     compileTemplates(classGen, methodGen, ihLoop);
/*  634:     */     
/*  635:     */ 
/*  636: 829 */     TestSeq elemTest = this._testSeq[1];
/*  637: 830 */     InstructionHandle ihElem = ihRecurse;
/*  638: 831 */     if (elemTest != null) {
/*  639: 832 */       ihElem = elemTest.compile(classGen, methodGen, ihRecurse);
/*  640:     */     }
/*  641: 835 */     TestSeq attrTest = this._testSeq[2];
/*  642: 836 */     InstructionHandle ihAttr = ihText;
/*  643: 837 */     if (attrTest != null) {
/*  644: 838 */       ihAttr = attrTest.compile(classGen, methodGen, ihAttr);
/*  645:     */     }
/*  646: 841 */     InstructionList ilKey = null;
/*  647: 842 */     if (this._idxTestSeq != null)
/*  648:     */     {
/*  649: 843 */       loop.setTarget(this._idxTestSeq.compile(classGen, methodGen, body.getStart()));
/*  650: 844 */       ilKey = this._idxTestSeq.getInstructionList();
/*  651:     */     }
/*  652:     */     else
/*  653:     */     {
/*  654: 847 */       loop.setTarget(body.getStart());
/*  655:     */     }
/*  656: 852 */     if (this._childNodeTestSeq != null)
/*  657:     */     {
/*  658: 854 */       double nodePrio = this._childNodeTestSeq.getPriority();
/*  659: 855 */       int nodePos = this._childNodeTestSeq.getPosition();
/*  660: 856 */       double elemPrio = -1.797693134862316E+308D;
/*  661: 857 */       int elemPos = -2147483648;
/*  662: 859 */       if (elemTest != null)
/*  663:     */       {
/*  664: 860 */         elemPrio = elemTest.getPriority();
/*  665: 861 */         elemPos = elemTest.getPosition();
/*  666:     */       }
/*  667: 863 */       if ((elemPrio == (0.0D / 0.0D)) || (elemPrio < nodePrio) || ((elemPrio == nodePrio) && (elemPos < nodePos))) {
/*  668: 866 */         ihElem = this._childNodeTestSeq.compile(classGen, methodGen, ihLoop);
/*  669:     */       }
/*  670: 870 */       TestSeq textTest = this._testSeq[3];
/*  671: 871 */       double textPrio = -1.797693134862316E+308D;
/*  672: 872 */       int textPos = -2147483648;
/*  673: 874 */       if (textTest != null)
/*  674:     */       {
/*  675: 875 */         textPrio = textTest.getPriority();
/*  676: 876 */         textPos = textTest.getPosition();
/*  677:     */       }
/*  678: 878 */       if ((textPrio == (0.0D / 0.0D)) || (textPrio < nodePrio) || ((textPrio == nodePrio) && (textPos < nodePos)))
/*  679:     */       {
/*  680: 881 */         ihText = this._childNodeTestSeq.compile(classGen, methodGen, ihLoop);
/*  681: 882 */         this._testSeq[3] = this._childNodeTestSeq;
/*  682:     */       }
/*  683:     */     }
/*  684: 887 */     InstructionHandle elemNamespaceHandle = ihElem;
/*  685: 888 */     InstructionList nsElem = compileNamespaces(classGen, methodGen, isNamespace, isAttribute, false, ihElem);
/*  686: 891 */     if (nsElem != null) {
/*  687: 891 */       elemNamespaceHandle = nsElem.getStart();
/*  688:     */     }
/*  689: 894 */     InstructionHandle attrNamespaceHandle = ihAttr;
/*  690: 895 */     InstructionList nsAttr = compileNamespaces(classGen, methodGen, isNamespace, isAttribute, true, ihAttr);
/*  691: 898 */     if (nsAttr != null) {
/*  692: 898 */       attrNamespaceHandle = nsAttr.getStart();
/*  693:     */     }
/*  694: 901 */     InstructionHandle[] targets = new InstructionHandle[types.length];
/*  695: 902 */     for (int i = 14; i < targets.length; i++)
/*  696:     */     {
/*  697: 903 */       TestSeq testSeq = this._testSeq[i];
/*  698: 905 */       if (isNamespace[i] != 0)
/*  699:     */       {
/*  700: 906 */         if (isAttribute[i] != 0) {
/*  701: 907 */           targets[i] = attrNamespaceHandle;
/*  702:     */         } else {
/*  703: 909 */           targets[i] = elemNamespaceHandle;
/*  704:     */         }
/*  705:     */       }
/*  706: 912 */       else if (testSeq != null)
/*  707:     */       {
/*  708: 913 */         if (isAttribute[i] != 0) {
/*  709: 914 */           targets[i] = testSeq.compile(classGen, methodGen, attrNamespaceHandle);
/*  710:     */         } else {
/*  711: 917 */           targets[i] = testSeq.compile(classGen, methodGen, elemNamespaceHandle);
/*  712:     */         }
/*  713:     */       }
/*  714:     */       else {
/*  715: 921 */         targets[i] = ihLoop;
/*  716:     */       }
/*  717:     */     }
/*  718: 927 */     targets[0] = (this._rootPattern != null ? getTemplateInstructionHandle(this._rootPattern.getTemplate()) : ihRecurse);
/*  719:     */     
/*  720:     */ 
/*  721:     */ 
/*  722:     */ 
/*  723: 932 */     targets[9] = (this._rootPattern != null ? getTemplateInstructionHandle(this._rootPattern.getTemplate()) : ihRecurse);
/*  724:     */     
/*  725:     */ 
/*  726:     */ 
/*  727:     */ 
/*  728: 937 */     targets[3] = (this._testSeq[3] != null ? this._testSeq[3].compile(classGen, methodGen, ihText) : ihText);
/*  729:     */     
/*  730:     */ 
/*  731:     */ 
/*  732:     */ 
/*  733: 942 */     targets[13] = ihLoop;
/*  734:     */     
/*  735:     */ 
/*  736: 945 */     targets[1] = elemNamespaceHandle;
/*  737:     */     
/*  738:     */ 
/*  739: 948 */     targets[2] = attrNamespaceHandle;
/*  740:     */     
/*  741:     */ 
/*  742: 951 */     InstructionHandle ihPI = ihLoop;
/*  743: 952 */     if (this._childNodeTestSeq != null) {
/*  744: 952 */       ihPI = ihElem;
/*  745:     */     }
/*  746: 953 */     if (this._testSeq[7] != null) {
/*  747: 954 */       targets[7] = this._testSeq[7].compile(classGen, methodGen, ihPI);
/*  748:     */     } else {
/*  749: 958 */       targets[7] = ihPI;
/*  750:     */     }
/*  751: 961 */     InstructionHandle ihComment = ihLoop;
/*  752: 962 */     if (this._childNodeTestSeq != null) {
/*  753: 962 */       ihComment = ihElem;
/*  754:     */     }
/*  755: 963 */     targets[8] = (this._testSeq[8] != null ? this._testSeq[8].compile(classGen, methodGen, ihComment) : ihComment);
/*  756:     */     
/*  757:     */ 
/*  758:     */ 
/*  759:     */ 
/*  760: 968 */     targets[4] = ihLoop;
/*  761:     */     
/*  762:     */ 
/*  763: 971 */     targets[11] = ihLoop;
/*  764:     */     
/*  765:     */ 
/*  766: 974 */     targets[10] = ihLoop;
/*  767:     */     
/*  768:     */ 
/*  769: 977 */     targets[6] = ihLoop;
/*  770:     */     
/*  771:     */ 
/*  772: 980 */     targets[5] = ihLoop;
/*  773:     */     
/*  774:     */ 
/*  775: 983 */     targets[12] = ihLoop;
/*  776: 987 */     for (int i = 14; i < targets.length; i++)
/*  777:     */     {
/*  778: 988 */       TestSeq testSeq = this._testSeq[i];
/*  779: 990 */       if ((testSeq == null) || (isNamespace[i] != 0))
/*  780:     */       {
/*  781: 991 */         if (isAttribute[i] != 0) {
/*  782: 992 */           targets[i] = attrNamespaceHandle;
/*  783:     */         } else {
/*  784: 994 */           targets[i] = elemNamespaceHandle;
/*  785:     */         }
/*  786:     */       }
/*  787: 998 */       else if (isAttribute[i] != 0) {
/*  788: 999 */         targets[i] = testSeq.compile(classGen, methodGen, attrNamespaceHandle);
/*  789:     */       } else {
/*  790:1002 */         targets[i] = testSeq.compile(classGen, methodGen, elemNamespaceHandle);
/*  791:     */       }
/*  792:     */     }
/*  793:1007 */     if (ilKey != null) {
/*  794:1007 */       body.insert(ilKey);
/*  795:     */     }
/*  796:1010 */     int getType = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getExpandedTypeID", "(I)I");
/*  797:     */     
/*  798:     */ 
/*  799:1013 */     body.append(methodGen.loadDOM());
/*  800:1014 */     body.append(new ILOAD(this._currentIndex));
/*  801:1015 */     body.append(new INVOKEINTERFACE(getType, 2));
/*  802:     */     
/*  803:     */ 
/*  804:1018 */     InstructionHandle disp = body.append(new SWITCH(types, targets, ihLoop));
/*  805:     */     
/*  806:     */ 
/*  807:1021 */     appendTestSequences(body);
/*  808:     */     
/*  809:1023 */     appendTemplateCode(body);
/*  810:1026 */     if (nsElem != null) {
/*  811:1026 */       body.append(nsElem);
/*  812:     */     }
/*  813:1028 */     if (nsAttr != null) {
/*  814:1028 */       body.append(nsAttr);
/*  815:     */     }
/*  816:1031 */     body.append(ilRecurse);
/*  817:     */     
/*  818:1033 */     body.append(ilText);
/*  819:     */     
/*  820:     */ 
/*  821:1036 */     mainIL.append(body);
/*  822:     */     
/*  823:1038 */     mainIL.append(ilLoop);
/*  824:     */     
/*  825:1040 */     peepHoleOptimization(methodGen);
/*  826:     */     
/*  827:1042 */     classGen.addMethod(methodGen);
/*  828:1045 */     if (this._importLevels != null)
/*  829:     */     {
/*  830:1046 */       Enumeration levels = this._importLevels.keys();
/*  831:1047 */       while (levels.hasMoreElements())
/*  832:     */       {
/*  833:1048 */         Integer max = (Integer)levels.nextElement();
/*  834:1049 */         Integer min = (Integer)this._importLevels.get(max);
/*  835:1050 */         compileApplyImports(classGen, min.intValue(), max.intValue());
/*  836:     */       }
/*  837:     */     }
/*  838:     */   }
/*  839:     */   
/*  840:     */   private void compileTemplateCalls(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle next, int min, int max)
/*  841:     */   {
/*  842:1058 */     Enumeration templates = this._neededTemplates.keys();
/*  843:1059 */     while (templates.hasMoreElements())
/*  844:     */     {
/*  845:1060 */       Template template = (Template)templates.nextElement();
/*  846:1061 */       int prec = template.getImportPrecedence();
/*  847:1062 */       if ((prec >= min) && (prec < max)) {
/*  848:1063 */         if (template.hasContents())
/*  849:     */         {
/*  850:1064 */           InstructionList til = template.compile(classGen, methodGen);
/*  851:1065 */           til.append(new GOTO_W(next));
/*  852:1066 */           this._templateILs.put(template, til);
/*  853:1067 */           this._templateIHs.put(template, til.getStart());
/*  854:     */         }
/*  855:     */         else
/*  856:     */         {
/*  857:1071 */           this._templateIHs.put(template, next);
/*  858:     */         }
/*  859:     */       }
/*  860:     */     }
/*  861:     */   }
/*  862:     */   
/*  863:     */   public void compileApplyImports(ClassGenerator classGen, int min, int max)
/*  864:     */   {
/*  865:1079 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/*  866:1080 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  867:1081 */     Vector names = xsltc.getNamesIndex();
/*  868:     */     
/*  869:     */ 
/*  870:1084 */     this._namedTemplates = new Hashtable();
/*  871:1085 */     this._neededTemplates = new Hashtable();
/*  872:1086 */     this._templateIHs = new Hashtable();
/*  873:1087 */     this._templateILs = new Hashtable();
/*  874:1088 */     this._patternGroups = new Vector[32];
/*  875:1089 */     this._rootPattern = null;
/*  876:     */     
/*  877:     */ 
/*  878:1092 */     Vector oldTemplates = this._templates;
/*  879:     */     
/*  880:     */ 
/*  881:1095 */     this._templates = new Vector();
/*  882:1096 */     Enumeration templates = oldTemplates.elements();
/*  883:     */     Template template;
/*  884:     */     label144:
/*  885:1097 */     for (; templates.hasMoreElements(); addTemplate(template))
/*  886:     */     {
/*  887:1098 */       template = (Template)templates.nextElement();
/*  888:1099 */       int prec = template.getImportPrecedence();
/*  889:1100 */       if ((prec < min) || (prec >= max)) {
/*  890:     */         break label144;
/*  891:     */       }
/*  892:     */     }
/*  893:1104 */     processPatterns(this._keys);
/*  894:     */     
/*  895:     */ 
/*  896:1107 */     Type[] argTypes = new Type[4];
/*  897:     */     
/*  898:1109 */     argTypes[0] = Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;");
/*  899:1110 */     argTypes[1] = Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  900:1111 */     argTypes[2] = Util.getJCRefType(Constants.TRANSLET_OUTPUT_SIG);
/*  901:1112 */     argTypes[3] = Type.INT;
/*  902:     */     
/*  903:1114 */     String[] argNames = new String[4];
/*  904:1115 */     argNames[0] = "document";
/*  905:1116 */     argNames[1] = "iterator";
/*  906:1117 */     argNames[2] = "handler";
/*  907:1118 */     argNames[3] = "node";
/*  908:     */     
/*  909:1120 */     InstructionList mainIL = new InstructionList();
/*  910:1121 */     MethodGenerator methodGen = new MethodGenerator(17, Type.VOID, argTypes, argNames, functionName() + '_' + max, getClassName(), mainIL, classGen.getConstantPool());
/*  911:     */     
/*  912:     */ 
/*  913:     */ 
/*  914:     */ 
/*  915:     */ 
/*  916:1127 */     methodGen.addException("org.apache.xalan.xsltc.TransletException");
/*  917:     */     
/*  918:     */ 
/*  919:     */ 
/*  920:1131 */     LocalVariableGen current = methodGen.addLocalVariable2("current", Type.INT, null);
/*  921:     */     
/*  922:     */ 
/*  923:1134 */     this._currentIndex = current.getIndex();
/*  924:     */     
/*  925:1136 */     mainIL.append(new ILOAD(methodGen.getLocalIndex("node")));
/*  926:1137 */     current.setStart(mainIL.append(new ISTORE(this._currentIndex)));
/*  927:     */     
/*  928:     */ 
/*  929:     */ 
/*  930:1141 */     InstructionList body = new InstructionList();
/*  931:1142 */     body.append(InstructionConstants.NOP);
/*  932:     */     
/*  933:     */ 
/*  934:     */ 
/*  935:1146 */     InstructionList ilLoop = new InstructionList();
/*  936:1147 */     ilLoop.append(InstructionConstants.RETURN);
/*  937:1148 */     InstructionHandle ihLoop = ilLoop.getStart();
/*  938:     */     
/*  939:     */ 
/*  940:1151 */     InstructionList ilRecurse = compileDefaultRecursion(classGen, methodGen, ihLoop);
/*  941:     */     
/*  942:1153 */     InstructionHandle ihRecurse = ilRecurse.getStart();
/*  943:     */     
/*  944:     */ 
/*  945:1156 */     InstructionList ilText = compileDefaultText(classGen, methodGen, ihLoop);
/*  946:     */     
/*  947:1158 */     InstructionHandle ihText = ilText.getStart();
/*  948:     */     
/*  949:     */ 
/*  950:1161 */     int[] types = new int[14 + names.size()];
/*  951:1162 */     for (int i = 0; i < types.length; i++) {
/*  952:1163 */       types[i] = i;
/*  953:     */     }
/*  954:1166 */     boolean[] isAttribute = new boolean[types.length];
/*  955:1167 */     boolean[] isNamespace = new boolean[types.length];
/*  956:1168 */     for (int i = 0; i < names.size(); i++)
/*  957:     */     {
/*  958:1169 */       String name = (String)names.elementAt(i);
/*  959:1170 */       isAttribute[(i + 14)] = isAttributeName(name);
/*  960:1171 */       isNamespace[(i + 14)] = isNamespaceName(name);
/*  961:     */     }
/*  962:1175 */     compileTemplateCalls(classGen, methodGen, ihLoop, min, max);
/*  963:     */     
/*  964:     */ 
/*  965:1178 */     TestSeq elemTest = this._testSeq[1];
/*  966:1179 */     InstructionHandle ihElem = ihRecurse;
/*  967:1180 */     if (elemTest != null) {
/*  968:1181 */       ihElem = elemTest.compile(classGen, methodGen, ihLoop);
/*  969:     */     }
/*  970:1185 */     TestSeq attrTest = this._testSeq[2];
/*  971:1186 */     InstructionHandle ihAttr = ihLoop;
/*  972:1187 */     if (attrTest != null) {
/*  973:1188 */       ihAttr = attrTest.compile(classGen, methodGen, ihAttr);
/*  974:     */     }
/*  975:1192 */     InstructionList ilKey = null;
/*  976:1193 */     if (this._idxTestSeq != null) {
/*  977:1194 */       ilKey = this._idxTestSeq.getInstructionList();
/*  978:     */     }
/*  979:1199 */     if (this._childNodeTestSeq != null)
/*  980:     */     {
/*  981:1201 */       double nodePrio = this._childNodeTestSeq.getPriority();
/*  982:1202 */       int nodePos = this._childNodeTestSeq.getPosition();
/*  983:1203 */       double elemPrio = -1.797693134862316E+308D;
/*  984:1204 */       int elemPos = -2147483648;
/*  985:1206 */       if (elemTest != null)
/*  986:     */       {
/*  987:1207 */         elemPrio = elemTest.getPriority();
/*  988:1208 */         elemPos = elemTest.getPosition();
/*  989:     */       }
/*  990:1211 */       if ((elemPrio == (0.0D / 0.0D)) || (elemPrio < nodePrio) || ((elemPrio == nodePrio) && (elemPos < nodePos))) {
/*  991:1214 */         ihElem = this._childNodeTestSeq.compile(classGen, methodGen, ihLoop);
/*  992:     */       }
/*  993:1218 */       TestSeq textTest = this._testSeq[3];
/*  994:1219 */       double textPrio = -1.797693134862316E+308D;
/*  995:1220 */       int textPos = -2147483648;
/*  996:1222 */       if (textTest != null)
/*  997:     */       {
/*  998:1223 */         textPrio = textTest.getPriority();
/*  999:1224 */         textPos = textTest.getPosition();
/* 1000:     */       }
/* 1001:1227 */       if ((textPrio == (0.0D / 0.0D)) || (textPrio < nodePrio) || ((textPrio == nodePrio) && (textPos < nodePos)))
/* 1002:     */       {
/* 1003:1230 */         ihText = this._childNodeTestSeq.compile(classGen, methodGen, ihLoop);
/* 1004:1231 */         this._testSeq[3] = this._childNodeTestSeq;
/* 1005:     */       }
/* 1006:     */     }
/* 1007:1236 */     InstructionHandle elemNamespaceHandle = ihElem;
/* 1008:1237 */     InstructionList nsElem = compileNamespaces(classGen, methodGen, isNamespace, isAttribute, false, ihElem);
/* 1009:1240 */     if (nsElem != null) {
/* 1010:1240 */       elemNamespaceHandle = nsElem.getStart();
/* 1011:     */     }
/* 1012:1243 */     InstructionList nsAttr = compileNamespaces(classGen, methodGen, isNamespace, isAttribute, true, ihAttr);
/* 1013:     */     
/* 1014:     */ 
/* 1015:1246 */     InstructionHandle attrNamespaceHandle = ihAttr;
/* 1016:1247 */     if (nsAttr != null) {
/* 1017:1247 */       attrNamespaceHandle = nsAttr.getStart();
/* 1018:     */     }
/* 1019:1250 */     InstructionHandle[] targets = new InstructionHandle[types.length];
/* 1020:1251 */     for (int i = 14; i < targets.length; i++)
/* 1021:     */     {
/* 1022:1252 */       TestSeq testSeq = this._testSeq[i];
/* 1023:1254 */       if (isNamespace[i] != 0)
/* 1024:     */       {
/* 1025:1255 */         if (isAttribute[i] != 0) {
/* 1026:1256 */           targets[i] = attrNamespaceHandle;
/* 1027:     */         } else {
/* 1028:1258 */           targets[i] = elemNamespaceHandle;
/* 1029:     */         }
/* 1030:     */       }
/* 1031:1261 */       else if (testSeq != null)
/* 1032:     */       {
/* 1033:1262 */         if (isAttribute[i] != 0) {
/* 1034:1263 */           targets[i] = testSeq.compile(classGen, methodGen, attrNamespaceHandle);
/* 1035:     */         } else {
/* 1036:1266 */           targets[i] = testSeq.compile(classGen, methodGen, elemNamespaceHandle);
/* 1037:     */         }
/* 1038:     */       }
/* 1039:     */       else {
/* 1040:1270 */         targets[i] = ihLoop;
/* 1041:     */       }
/* 1042:     */     }
/* 1043:1275 */     targets[0] = (this._rootPattern != null ? getTemplateInstructionHandle(this._rootPattern.getTemplate()) : ihRecurse);
/* 1044:     */     
/* 1045:     */ 
/* 1046:     */ 
/* 1047:1279 */     targets[9] = (this._rootPattern != null ? getTemplateInstructionHandle(this._rootPattern.getTemplate()) : ihRecurse);
/* 1048:     */     
/* 1049:     */ 
/* 1050:     */ 
/* 1051:     */ 
/* 1052:1284 */     targets[3] = (this._testSeq[3] != null ? this._testSeq[3].compile(classGen, methodGen, ihText) : ihText);
/* 1053:     */     
/* 1054:     */ 
/* 1055:     */ 
/* 1056:     */ 
/* 1057:1289 */     targets[13] = ihLoop;
/* 1058:     */     
/* 1059:     */ 
/* 1060:1292 */     targets[1] = elemNamespaceHandle;
/* 1061:     */     
/* 1062:     */ 
/* 1063:1295 */     targets[2] = attrNamespaceHandle;
/* 1064:     */     
/* 1065:     */ 
/* 1066:1298 */     InstructionHandle ihPI = ihLoop;
/* 1067:1299 */     if (this._childNodeTestSeq != null) {
/* 1068:1299 */       ihPI = ihElem;
/* 1069:     */     }
/* 1070:1300 */     if (this._testSeq[7] != null) {
/* 1071:1301 */       targets[7] = this._testSeq[7].compile(classGen, methodGen, ihPI);
/* 1072:     */     } else {
/* 1073:1306 */       targets[7] = ihPI;
/* 1074:     */     }
/* 1075:1310 */     InstructionHandle ihComment = ihLoop;
/* 1076:1311 */     if (this._childNodeTestSeq != null) {
/* 1077:1311 */       ihComment = ihElem;
/* 1078:     */     }
/* 1079:1312 */     targets[8] = (this._testSeq[8] != null ? this._testSeq[8].compile(classGen, methodGen, ihComment) : ihComment);
/* 1080:     */     
/* 1081:     */ 
/* 1082:     */ 
/* 1083:     */ 
/* 1084:1317 */     targets[4] = ihLoop;
/* 1085:     */     
/* 1086:     */ 
/* 1087:1320 */     targets[11] = ihLoop;
/* 1088:     */     
/* 1089:     */ 
/* 1090:1323 */     targets[10] = ihLoop;
/* 1091:     */     
/* 1092:     */ 
/* 1093:1326 */     targets[6] = ihLoop;
/* 1094:     */     
/* 1095:     */ 
/* 1096:1329 */     targets[5] = ihLoop;
/* 1097:     */     
/* 1098:     */ 
/* 1099:1332 */     targets[12] = ihLoop;
/* 1100:1337 */     for (int i = 14; i < targets.length; i++)
/* 1101:     */     {
/* 1102:1338 */       TestSeq testSeq = this._testSeq[i];
/* 1103:1340 */       if ((testSeq == null) || (isNamespace[i] != 0))
/* 1104:     */       {
/* 1105:1341 */         if (isAttribute[i] != 0) {
/* 1106:1342 */           targets[i] = attrNamespaceHandle;
/* 1107:     */         } else {
/* 1108:1344 */           targets[i] = elemNamespaceHandle;
/* 1109:     */         }
/* 1110:     */       }
/* 1111:1348 */       else if (isAttribute[i] != 0) {
/* 1112:1349 */         targets[i] = testSeq.compile(classGen, methodGen, attrNamespaceHandle);
/* 1113:     */       } else {
/* 1114:1352 */         targets[i] = testSeq.compile(classGen, methodGen, elemNamespaceHandle);
/* 1115:     */       }
/* 1116:     */     }
/* 1117:1357 */     if (ilKey != null) {
/* 1118:1357 */       body.insert(ilKey);
/* 1119:     */     }
/* 1120:1360 */     int getType = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getExpandedTypeID", "(I)I");
/* 1121:     */     
/* 1122:     */ 
/* 1123:1363 */     body.append(methodGen.loadDOM());
/* 1124:1364 */     body.append(new ILOAD(this._currentIndex));
/* 1125:1365 */     body.append(new INVOKEINTERFACE(getType, 2));
/* 1126:     */     
/* 1127:     */ 
/* 1128:1368 */     InstructionHandle disp = body.append(new SWITCH(types, targets, ihLoop));
/* 1129:     */     
/* 1130:     */ 
/* 1131:1371 */     appendTestSequences(body);
/* 1132:     */     
/* 1133:1373 */     appendTemplateCode(body);
/* 1134:1376 */     if (nsElem != null) {
/* 1135:1376 */       body.append(nsElem);
/* 1136:     */     }
/* 1137:1378 */     if (nsAttr != null) {
/* 1138:1378 */       body.append(nsAttr);
/* 1139:     */     }
/* 1140:1381 */     body.append(ilRecurse);
/* 1141:     */     
/* 1142:1383 */     body.append(ilText);
/* 1143:     */     
/* 1144:     */ 
/* 1145:1386 */     mainIL.append(body);
/* 1146:     */     
/* 1147:     */ 
/* 1148:1389 */     current.setEnd(body.getEnd());
/* 1149:     */     
/* 1150:     */ 
/* 1151:1392 */     mainIL.append(ilLoop);
/* 1152:     */     
/* 1153:1394 */     peepHoleOptimization(methodGen);
/* 1154:     */     
/* 1155:1396 */     classGen.addMethod(methodGen);
/* 1156:     */     
/* 1157:     */ 
/* 1158:1399 */     this._templates = oldTemplates;
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   private void peepHoleOptimization(MethodGenerator methodGen)
/* 1162:     */   {
/* 1163:1406 */     InstructionList il = methodGen.getInstructionList();
/* 1164:1407 */     InstructionFinder find = new InstructionFinder(il);
/* 1165:     */     
/* 1166:     */ 
/* 1167:     */ 
/* 1168:     */ 
/* 1169:1412 */     String pattern = "LoadInstruction POP";
/* 1170:1413 */     for (Iterator iter = find.search(pattern); iter.hasNext();)
/* 1171:     */     {
/* 1172:1414 */       InstructionHandle[] match = (InstructionHandle[])iter.next();
/* 1173:     */       try
/* 1174:     */       {
/* 1175:1416 */         if ((!match[0].hasTargeters()) && (!match[1].hasTargeters())) {
/* 1176:1417 */           il.delete(match[0], match[1]);
/* 1177:     */         }
/* 1178:     */       }
/* 1179:     */       catch (TargetLostException e) {}
/* 1180:     */     }
/* 1181:1426 */     pattern = "ILOAD ILOAD SWAP ISTORE";
/* 1182:1427 */     for (Iterator iter = find.search(pattern); iter.hasNext();)
/* 1183:     */     {
/* 1184:1428 */       InstructionHandle[] match = (InstructionHandle[])iter.next();
/* 1185:     */       try
/* 1186:     */       {
/* 1187:1430 */         ILOAD iload1 = (ILOAD)match[0].getInstruction();
/* 1188:     */         
/* 1189:1432 */         ILOAD iload2 = (ILOAD)match[1].getInstruction();
/* 1190:     */         
/* 1191:1434 */         ISTORE istore = (ISTORE)match[3].getInstruction();
/* 1192:1437 */         if ((!match[1].hasTargeters()) && (!match[2].hasTargeters()) && (!match[3].hasTargeters()) && (iload1.getIndex() == iload2.getIndex()) && (iload2.getIndex() == istore.getIndex())) {
/* 1193:1443 */           il.delete(match[1], match[3]);
/* 1194:     */         }
/* 1195:     */       }
/* 1196:     */       catch (TargetLostException e) {}
/* 1197:     */     }
/* 1198:1452 */     pattern = "LoadInstruction LoadInstruction SWAP";
/* 1199:1453 */     for (Iterator iter = find.search(pattern); iter.hasNext();)
/* 1200:     */     {
/* 1201:1454 */       InstructionHandle[] match = (InstructionHandle[])iter.next();
/* 1202:     */       try
/* 1203:     */       {
/* 1204:1456 */         if ((!match[0].hasTargeters()) && (!match[1].hasTargeters()) && (!match[2].hasTargeters()))
/* 1205:     */         {
/* 1206:1460 */           Instruction load_m = match[1].getInstruction();
/* 1207:1461 */           il.insert(match[0], load_m);
/* 1208:1462 */           il.delete(match[1], match[2]);
/* 1209:     */         }
/* 1210:     */       }
/* 1211:     */       catch (TargetLostException e) {}
/* 1212:     */     }
/* 1213:1471 */     pattern = "ALOAD ALOAD";
/* 1214:1472 */     for (Iterator iter = find.search(pattern); iter.hasNext();)
/* 1215:     */     {
/* 1216:1473 */       InstructionHandle[] match = (InstructionHandle[])iter.next();
/* 1217:     */       try
/* 1218:     */       {
/* 1219:1475 */         if (!match[1].hasTargeters())
/* 1220:     */         {
/* 1221:1476 */           ALOAD aload1 = (ALOAD)match[0].getInstruction();
/* 1222:     */           
/* 1223:1478 */           ALOAD aload2 = (ALOAD)match[1].getInstruction();
/* 1224:1481 */           if (aload1.getIndex() == aload2.getIndex())
/* 1225:     */           {
/* 1226:1482 */             il.insert(match[1], new DUP());
/* 1227:1483 */             il.delete(match[1]);
/* 1228:     */           }
/* 1229:     */         }
/* 1230:     */       }
/* 1231:     */       catch (TargetLostException e) {}
/* 1232:     */     }
/* 1233:     */   }
/* 1234:     */   
/* 1235:     */   public InstructionHandle getTemplateInstructionHandle(Template template)
/* 1236:     */   {
/* 1237:1494 */     return (InstructionHandle)this._templateIHs.get(template);
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   private static boolean isAttributeName(String qname)
/* 1241:     */   {
/* 1242:1501 */     int col = qname.lastIndexOf(':') + 1;
/* 1243:1502 */     return qname.charAt(col) == '@';
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   private static boolean isNamespaceName(String qname)
/* 1247:     */   {
/* 1248:1510 */     int col = qname.lastIndexOf(':');
/* 1249:1511 */     return (col > -1) && (qname.charAt(qname.length() - 1) == '*');
/* 1250:     */   }
/* 1251:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Mode
 * JD-Core Version:    0.7.0.1
 */