/*    1:     */ package org.apache.xalan.xsltc.compiler.util;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Collections;
/*    5:     */ import java.util.HashMap;
/*    6:     */ import java.util.Hashtable;
/*    7:     */ import java.util.Iterator;
/*    8:     */ import java.util.Map.Entry;
/*    9:     */ import java.util.Set;
/*   10:     */ import java.util.Stack;
/*   11:     */ import org.apache.bcel.classfile.AccessFlags;
/*   12:     */ import org.apache.bcel.classfile.Field;
/*   13:     */ import org.apache.bcel.classfile.Method;
/*   14:     */ import org.apache.bcel.generic.ALOAD;
/*   15:     */ import org.apache.bcel.generic.ASTORE;
/*   16:     */ import org.apache.bcel.generic.BranchHandle;
/*   17:     */ import org.apache.bcel.generic.BranchInstruction;
/*   18:     */ import org.apache.bcel.generic.ClassGen;
/*   19:     */ import org.apache.bcel.generic.ConstantPoolGen;
/*   20:     */ import org.apache.bcel.generic.DLOAD;
/*   21:     */ import org.apache.bcel.generic.DSTORE;
/*   22:     */ import org.apache.bcel.generic.FLOAD;
/*   23:     */ import org.apache.bcel.generic.FSTORE;
/*   24:     */ import org.apache.bcel.generic.FieldGenOrMethodGen;
/*   25:     */ import org.apache.bcel.generic.GETFIELD;
/*   26:     */ import org.apache.bcel.generic.GOTO;
/*   27:     */ import org.apache.bcel.generic.ICONST;
/*   28:     */ import org.apache.bcel.generic.ILOAD;
/*   29:     */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   30:     */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   31:     */ import org.apache.bcel.generic.INVOKESTATIC;
/*   32:     */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   33:     */ import org.apache.bcel.generic.ISTORE;
/*   34:     */ import org.apache.bcel.generic.IfInstruction;
/*   35:     */ import org.apache.bcel.generic.IndexedInstruction;
/*   36:     */ import org.apache.bcel.generic.Instruction;
/*   37:     */ import org.apache.bcel.generic.InstructionConstants;
/*   38:     */ import org.apache.bcel.generic.InstructionHandle;
/*   39:     */ import org.apache.bcel.generic.InstructionList;
/*   40:     */ import org.apache.bcel.generic.InstructionTargeter;
/*   41:     */ import org.apache.bcel.generic.LLOAD;
/*   42:     */ import org.apache.bcel.generic.LSTORE;
/*   43:     */ import org.apache.bcel.generic.LocalVariableGen;
/*   44:     */ import org.apache.bcel.generic.LocalVariableInstruction;
/*   45:     */ import org.apache.bcel.generic.MethodGen;
/*   46:     */ import org.apache.bcel.generic.NEW;
/*   47:     */ import org.apache.bcel.generic.PUTFIELD;
/*   48:     */ import org.apache.bcel.generic.RET;
/*   49:     */ import org.apache.bcel.generic.Select;
/*   50:     */ import org.apache.bcel.generic.TargetLostException;
/*   51:     */ import org.apache.bcel.generic.Type;
/*   52:     */ import org.apache.xalan.xsltc.compiler.Constants;
/*   53:     */ import org.apache.xalan.xsltc.compiler.Parser;
/*   54:     */ import org.apache.xalan.xsltc.compiler.Pattern;
/*   55:     */ import org.apache.xalan.xsltc.compiler.Stylesheet;
/*   56:     */ import org.apache.xalan.xsltc.compiler.XSLTC;
/*   57:     */ 
/*   58:     */ public class MethodGenerator
/*   59:     */   extends MethodGen
/*   60:     */   implements Constants
/*   61:     */ {
/*   62:     */   protected static final int INVALID_INDEX = -1;
/*   63:     */   private static final String START_ELEMENT_SIG = "(Ljava/lang/String;)V";
/*   64:     */   private static final String END_ELEMENT_SIG = "(Ljava/lang/String;)V";
/*   65:     */   private InstructionList _mapTypeSub;
/*   66:     */   private static final int DOM_INDEX = 1;
/*   67:     */   private static final int ITERATOR_INDEX = 2;
/*   68:     */   private static final int HANDLER_INDEX = 3;
/*   69:     */   private static final int MAX_METHOD_SIZE = 65535;
/*   70:     */   private static final int MAX_BRANCH_TARGET_OFFSET = 32767;
/*   71:     */   private static final int MIN_BRANCH_TARGET_OFFSET = -32768;
/*   72:     */   private static final int TARGET_METHOD_SIZE = 60000;
/*   73:     */   private static final int MINIMUM_OUTLINEABLE_CHUNK_SIZE = 1000;
/*   74:     */   private Instruction _iloadCurrent;
/*   75:     */   private Instruction _istoreCurrent;
/*   76:     */   private final Instruction _astoreHandler;
/*   77:     */   private final Instruction _aloadHandler;
/*   78:     */   private final Instruction _astoreIterator;
/*   79:     */   private final Instruction _aloadIterator;
/*   80:     */   private final Instruction _aloadDom;
/*   81:     */   private final Instruction _astoreDom;
/*   82:     */   private final Instruction _startElement;
/*   83:     */   private final Instruction _endElement;
/*   84:     */   private final Instruction _startDocument;
/*   85:     */   private final Instruction _endDocument;
/*   86:     */   private final Instruction _attribute;
/*   87:     */   private final Instruction _uniqueAttribute;
/*   88:     */   private final Instruction _namespace;
/*   89:     */   private final Instruction _setStartNode;
/*   90:     */   private final Instruction _reset;
/*   91:     */   private final Instruction _nextNode;
/*   92:     */   private SlotAllocator _slotAllocator;
/*   93: 123 */   private boolean _allocatorInit = false;
/*   94:     */   private LocalVariableRegistry _localVariableRegistry;
/*   95: 132 */   private Hashtable _preCompiled = new Hashtable();
/*   96:     */   
/*   97:     */   public MethodGenerator(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cpg)
/*   98:     */   {
/*   99: 138 */     super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cpg);
/*  100:     */     
/*  101:     */ 
/*  102: 141 */     this._astoreHandler = new ASTORE(3);
/*  103: 142 */     this._aloadHandler = new ALOAD(3);
/*  104: 143 */     this._astoreIterator = new ASTORE(2);
/*  105: 144 */     this._aloadIterator = new ALOAD(2);
/*  106: 145 */     this._aloadDom = new ALOAD(1);
/*  107: 146 */     this._astoreDom = new ASTORE(1);
/*  108:     */     
/*  109: 148 */     int startElement = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "startElement", "(Ljava/lang/String;)V");
/*  110:     */     
/*  111:     */ 
/*  112:     */ 
/*  113: 152 */     this._startElement = new INVOKEINTERFACE(startElement, 2);
/*  114:     */     
/*  115: 154 */     int endElement = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "endElement", "(Ljava/lang/String;)V");
/*  116:     */     
/*  117:     */ 
/*  118:     */ 
/*  119: 158 */     this._endElement = new INVOKEINTERFACE(endElement, 2);
/*  120:     */     
/*  121: 160 */     int attribute = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "addAttribute", "(Ljava/lang/String;Ljava/lang/String;)V");
/*  122:     */     
/*  123:     */ 
/*  124:     */ 
/*  125:     */ 
/*  126:     */ 
/*  127:     */ 
/*  128: 167 */     this._attribute = new INVOKEINTERFACE(attribute, 3);
/*  129:     */     
/*  130: 169 */     int uniqueAttribute = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "addUniqueAttribute", "(Ljava/lang/String;Ljava/lang/String;I)V");
/*  131:     */     
/*  132:     */ 
/*  133:     */ 
/*  134:     */ 
/*  135:     */ 
/*  136:     */ 
/*  137: 176 */     this._uniqueAttribute = new INVOKEINTERFACE(uniqueAttribute, 4);
/*  138:     */     
/*  139: 178 */     int namespace = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "namespaceAfterStartElement", "(Ljava/lang/String;Ljava/lang/String;)V");
/*  140:     */     
/*  141:     */ 
/*  142:     */ 
/*  143:     */ 
/*  144:     */ 
/*  145:     */ 
/*  146: 185 */     this._namespace = new INVOKEINTERFACE(namespace, 3);
/*  147:     */     
/*  148: 187 */     int index = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "startDocument", "()V");
/*  149:     */     
/*  150:     */ 
/*  151: 190 */     this._startDocument = new INVOKEINTERFACE(index, 1);
/*  152:     */     
/*  153: 192 */     index = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "endDocument", "()V");
/*  154:     */     
/*  155:     */ 
/*  156: 195 */     this._endDocument = new INVOKEINTERFACE(index, 1);
/*  157:     */     
/*  158:     */ 
/*  159: 198 */     index = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "setStartNode", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  160:     */     
/*  161:     */ 
/*  162: 201 */     this._setStartNode = new INVOKEINTERFACE(index, 2);
/*  163:     */     
/*  164: 203 */     index = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "reset", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  165:     */     
/*  166: 205 */     this._reset = new INVOKEINTERFACE(index, 1);
/*  167:     */     
/*  168: 207 */     index = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "next", "()I");
/*  169: 208 */     this._nextNode = new INVOKEINTERFACE(index, 1);
/*  170:     */     
/*  171: 210 */     this._slotAllocator = new SlotAllocator();
/*  172: 211 */     this._slotAllocator.initialize(getLocalVariableRegistry().getLocals(false));
/*  173: 212 */     this._allocatorInit = true;
/*  174:     */   }
/*  175:     */   
/*  176:     */   public LocalVariableGen addLocalVariable(String name, Type type, InstructionHandle start, InstructionHandle end)
/*  177:     */   {
/*  178:     */     LocalVariableGen lvg;
/*  179: 227 */     if (this._allocatorInit)
/*  180:     */     {
/*  181: 228 */       lvg = addLocalVariable2(name, type, start);
/*  182:     */     }
/*  183:     */     else
/*  184:     */     {
/*  185: 230 */       lvg = super.addLocalVariable(name, type, start, end);
/*  186: 231 */       getLocalVariableRegistry().registerLocalVariable(lvg);
/*  187:     */     }
/*  188: 234 */     return lvg;
/*  189:     */   }
/*  190:     */   
/*  191:     */   public LocalVariableGen addLocalVariable2(String name, Type type, InstructionHandle start)
/*  192:     */   {
/*  193: 240 */     LocalVariableGen lvg = super.addLocalVariable(name, type, this._slotAllocator.allocateSlot(type), start, null);
/*  194:     */     
/*  195:     */ 
/*  196: 243 */     getLocalVariableRegistry().registerLocalVariable(lvg);
/*  197: 244 */     return lvg;
/*  198:     */   }
/*  199:     */   
/*  200:     */   private LocalVariableRegistry getLocalVariableRegistry()
/*  201:     */   {
/*  202: 248 */     if (this._localVariableRegistry == null) {
/*  203: 249 */       this._localVariableRegistry = new LocalVariableRegistry();
/*  204:     */     }
/*  205: 252 */     return this._localVariableRegistry;
/*  206:     */   }
/*  207:     */   
/*  208:     */   protected class LocalVariableRegistry
/*  209:     */   {
/*  210: 284 */     protected ArrayList _variables = new ArrayList();
/*  211: 289 */     protected HashMap _nameToLVGMap = new HashMap();
/*  212:     */     
/*  213:     */     protected LocalVariableRegistry() {}
/*  214:     */     
/*  215:     */     protected void registerLocalVariable(LocalVariableGen lvg)
/*  216:     */     {
/*  217: 304 */       int slot = lvg.getIndex();
/*  218:     */       
/*  219: 306 */       int registrySize = this._variables.size();
/*  220: 311 */       if (slot >= registrySize)
/*  221:     */       {
/*  222: 312 */         for (int i = registrySize; i < slot; i++) {
/*  223: 313 */           this._variables.add(null);
/*  224:     */         }
/*  225: 315 */         this._variables.add(lvg);
/*  226:     */       }
/*  227:     */       else
/*  228:     */       {
/*  229: 322 */         Object localsInSlot = this._variables.get(slot);
/*  230: 323 */         if (localsInSlot != null)
/*  231:     */         {
/*  232: 324 */           if ((localsInSlot instanceof LocalVariableGen))
/*  233:     */           {
/*  234: 325 */             ArrayList listOfLocalsInSlot = new ArrayList();
/*  235: 326 */             listOfLocalsInSlot.add(localsInSlot);
/*  236: 327 */             listOfLocalsInSlot.add(lvg);
/*  237: 328 */             this._variables.set(slot, listOfLocalsInSlot);
/*  238:     */           }
/*  239:     */           else
/*  240:     */           {
/*  241: 330 */             ((ArrayList)localsInSlot).add(lvg);
/*  242:     */           }
/*  243:     */         }
/*  244:     */         else {
/*  245: 333 */           this._variables.set(slot, lvg);
/*  246:     */         }
/*  247:     */       }
/*  248: 337 */       registerByName(lvg);
/*  249:     */     }
/*  250:     */     
/*  251:     */     protected LocalVariableGen lookupRegisteredLocalVariable(int slot, int offset)
/*  252:     */     {
/*  253: 358 */       Object localsInSlot = this._variables != null ? this._variables.get(slot) : null;
/*  254: 367 */       if (localsInSlot != null) {
/*  255: 368 */         if ((localsInSlot instanceof LocalVariableGen))
/*  256:     */         {
/*  257: 369 */           LocalVariableGen lvg = (LocalVariableGen)localsInSlot;
/*  258: 370 */           if (MethodGenerator.this.offsetInLocalVariableGenRange(lvg, offset)) {
/*  259: 371 */             return lvg;
/*  260:     */           }
/*  261:     */         }
/*  262:     */         else
/*  263:     */         {
/*  264: 374 */           ArrayList listOfLocalsInSlot = (ArrayList)localsInSlot;
/*  265: 375 */           int size = listOfLocalsInSlot.size();
/*  266: 377 */           for (int i = 0; i < size; i++)
/*  267:     */           {
/*  268: 378 */             LocalVariableGen lvg = (LocalVariableGen)listOfLocalsInSlot.get(i);
/*  269: 380 */             if (MethodGenerator.this.offsetInLocalVariableGenRange(lvg, offset)) {
/*  270: 381 */               return lvg;
/*  271:     */             }
/*  272:     */           }
/*  273:     */         }
/*  274:     */       }
/*  275: 388 */       return null;
/*  276:     */     }
/*  277:     */     
/*  278:     */     protected void registerByName(LocalVariableGen lvg)
/*  279:     */     {
/*  280: 409 */       Object duplicateNameEntry = this._nameToLVGMap.get(lvg.getName());
/*  281: 411 */       if (duplicateNameEntry == null)
/*  282:     */       {
/*  283: 412 */         this._nameToLVGMap.put(lvg.getName(), lvg);
/*  284:     */       }
/*  285:     */       else
/*  286:     */       {
/*  287:     */         ArrayList sameNameList;
/*  288: 416 */         if ((duplicateNameEntry instanceof ArrayList))
/*  289:     */         {
/*  290: 417 */           sameNameList = (ArrayList)duplicateNameEntry;
/*  291: 418 */           sameNameList.add(lvg);
/*  292:     */         }
/*  293:     */         else
/*  294:     */         {
/*  295: 420 */           sameNameList = new ArrayList();
/*  296: 421 */           sameNameList.add(duplicateNameEntry);
/*  297: 422 */           sameNameList.add(lvg);
/*  298:     */         }
/*  299: 425 */         this._nameToLVGMap.put(lvg.getName(), sameNameList);
/*  300:     */       }
/*  301:     */     }
/*  302:     */     
/*  303:     */     protected void removeByNameTracking(LocalVariableGen lvg)
/*  304:     */     {
/*  305: 437 */       Object duplicateNameEntry = this._nameToLVGMap.get(lvg.getName());
/*  306: 439 */       if ((duplicateNameEntry instanceof ArrayList))
/*  307:     */       {
/*  308: 440 */         ArrayList sameNameList = (ArrayList)duplicateNameEntry;
/*  309: 441 */         for (int i = 0; i < sameNameList.size(); i++) {
/*  310: 442 */           if (sameNameList.get(i) == lvg)
/*  311:     */           {
/*  312: 443 */             sameNameList.remove(i);
/*  313: 444 */             break;
/*  314:     */           }
/*  315:     */         }
/*  316:     */       }
/*  317:     */       else
/*  318:     */       {
/*  319: 448 */         this._nameToLVGMap.remove(lvg);
/*  320:     */       }
/*  321:     */     }
/*  322:     */     
/*  323:     */     protected LocalVariableGen lookUpByName(String name)
/*  324:     */     {
/*  325: 461 */       LocalVariableGen lvg = null;
/*  326: 462 */       Object duplicateNameEntry = this._nameToLVGMap.get(name);
/*  327: 464 */       if ((duplicateNameEntry instanceof ArrayList))
/*  328:     */       {
/*  329: 465 */         ArrayList sameNameList = (ArrayList)duplicateNameEntry;
/*  330: 467 */         for (int i = 0; i < sameNameList.size(); i++)
/*  331:     */         {
/*  332: 468 */           lvg = (LocalVariableGen)sameNameList.get(i);
/*  333: 469 */           if (lvg.getName() == name) {
/*  334:     */             break;
/*  335:     */           }
/*  336:     */         }
/*  337:     */       }
/*  338:     */       else
/*  339:     */       {
/*  340: 474 */         lvg = (LocalVariableGen)duplicateNameEntry;
/*  341:     */       }
/*  342: 477 */       return lvg;
/*  343:     */     }
/*  344:     */     
/*  345:     */     protected LocalVariableGen[] getLocals(boolean includeRemoved)
/*  346:     */     {
/*  347: 495 */       LocalVariableGen[] locals = null;
/*  348: 496 */       ArrayList allVarsEverDeclared = new ArrayList();
/*  349: 498 */       if (includeRemoved)
/*  350:     */       {
/*  351: 499 */         int slotCount = allVarsEverDeclared.size();
/*  352: 501 */         for (int i = 0; i < slotCount; i++)
/*  353:     */         {
/*  354: 502 */           Object slotEntries = this._variables.get(i);
/*  355: 503 */           if (slotEntries != null) {
/*  356: 504 */             if ((slotEntries instanceof ArrayList))
/*  357:     */             {
/*  358: 505 */               ArrayList slotList = (ArrayList)slotEntries;
/*  359: 507 */               for (int j = 0; j < slotList.size(); j++) {
/*  360: 508 */                 allVarsEverDeclared.add(slotList.get(i));
/*  361:     */               }
/*  362:     */             }
/*  363:     */             else
/*  364:     */             {
/*  365: 511 */               allVarsEverDeclared.add(slotEntries);
/*  366:     */             }
/*  367:     */           }
/*  368:     */         }
/*  369:     */       }
/*  370:     */       else
/*  371:     */       {
/*  372: 516 */         Iterator nameVarsPairsIter = this._nameToLVGMap.entrySet().iterator();
/*  373: 518 */         while (nameVarsPairsIter.hasNext())
/*  374:     */         {
/*  375: 519 */           Map.Entry nameVarsPair = (Map.Entry)nameVarsPairsIter.next();
/*  376:     */           
/*  377: 521 */           Object vars = nameVarsPair.getValue();
/*  378: 522 */           if (vars != null) {
/*  379: 523 */             if ((vars instanceof ArrayList))
/*  380:     */             {
/*  381: 524 */               ArrayList varsList = (ArrayList)vars;
/*  382: 525 */               for (int i = 0; i < varsList.size(); i++) {
/*  383: 526 */                 allVarsEverDeclared.add(varsList.get(i));
/*  384:     */               }
/*  385:     */             }
/*  386:     */             else
/*  387:     */             {
/*  388: 529 */               allVarsEverDeclared.add(vars);
/*  389:     */             }
/*  390:     */           }
/*  391:     */         }
/*  392:     */       }
/*  393: 535 */       locals = new LocalVariableGen[allVarsEverDeclared.size()];
/*  394: 536 */       allVarsEverDeclared.toArray(locals);
/*  395:     */       
/*  396: 538 */       return locals;
/*  397:     */     }
/*  398:     */   }
/*  399:     */   
/*  400:     */   boolean offsetInLocalVariableGenRange(LocalVariableGen lvg, int offset)
/*  401:     */   {
/*  402: 556 */     InstructionHandle lvgStart = lvg.getStart();
/*  403: 557 */     InstructionHandle lvgEnd = lvg.getEnd();
/*  404: 561 */     if (lvgStart == null) {
/*  405: 562 */       lvgStart = getInstructionList().getStart();
/*  406:     */     }
/*  407: 567 */     if (lvgEnd == null) {
/*  408: 568 */       lvgEnd = getInstructionList().getEnd();
/*  409:     */     }
/*  410: 577 */     return (lvgStart.getPosition() <= offset) && (lvgEnd.getPosition() + lvgEnd.getInstruction().getLength() >= offset);
/*  411:     */   }
/*  412:     */   
/*  413:     */   public void removeLocalVariable(LocalVariableGen lvg)
/*  414:     */   {
/*  415: 583 */     this._slotAllocator.releaseSlot(lvg);
/*  416: 584 */     getLocalVariableRegistry().removeByNameTracking(lvg);
/*  417: 585 */     super.removeLocalVariable(lvg);
/*  418:     */   }
/*  419:     */   
/*  420:     */   public Instruction loadDOM()
/*  421:     */   {
/*  422: 589 */     return this._aloadDom;
/*  423:     */   }
/*  424:     */   
/*  425:     */   public Instruction storeDOM()
/*  426:     */   {
/*  427: 593 */     return this._astoreDom;
/*  428:     */   }
/*  429:     */   
/*  430:     */   public Instruction storeHandler()
/*  431:     */   {
/*  432: 597 */     return this._astoreHandler;
/*  433:     */   }
/*  434:     */   
/*  435:     */   public Instruction loadHandler()
/*  436:     */   {
/*  437: 601 */     return this._aloadHandler;
/*  438:     */   }
/*  439:     */   
/*  440:     */   public Instruction storeIterator()
/*  441:     */   {
/*  442: 605 */     return this._astoreIterator;
/*  443:     */   }
/*  444:     */   
/*  445:     */   public Instruction loadIterator()
/*  446:     */   {
/*  447: 609 */     return this._aloadIterator;
/*  448:     */   }
/*  449:     */   
/*  450:     */   public final Instruction setStartNode()
/*  451:     */   {
/*  452: 613 */     return this._setStartNode;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public final Instruction reset()
/*  456:     */   {
/*  457: 617 */     return this._reset;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public final Instruction nextNode()
/*  461:     */   {
/*  462: 621 */     return this._nextNode;
/*  463:     */   }
/*  464:     */   
/*  465:     */   public final Instruction startElement()
/*  466:     */   {
/*  467: 625 */     return this._startElement;
/*  468:     */   }
/*  469:     */   
/*  470:     */   public final Instruction endElement()
/*  471:     */   {
/*  472: 629 */     return this._endElement;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public final Instruction startDocument()
/*  476:     */   {
/*  477: 633 */     return this._startDocument;
/*  478:     */   }
/*  479:     */   
/*  480:     */   public final Instruction endDocument()
/*  481:     */   {
/*  482: 637 */     return this._endDocument;
/*  483:     */   }
/*  484:     */   
/*  485:     */   public final Instruction attribute()
/*  486:     */   {
/*  487: 641 */     return this._attribute;
/*  488:     */   }
/*  489:     */   
/*  490:     */   public final Instruction uniqueAttribute()
/*  491:     */   {
/*  492: 645 */     return this._uniqueAttribute;
/*  493:     */   }
/*  494:     */   
/*  495:     */   public final Instruction namespace()
/*  496:     */   {
/*  497: 649 */     return this._namespace;
/*  498:     */   }
/*  499:     */   
/*  500:     */   public Instruction loadCurrentNode()
/*  501:     */   {
/*  502: 653 */     if (this._iloadCurrent == null)
/*  503:     */     {
/*  504: 654 */       int idx = getLocalIndex("current");
/*  505: 655 */       if (idx > 0) {
/*  506: 656 */         this._iloadCurrent = new ILOAD(idx);
/*  507:     */       } else {
/*  508: 658 */         this._iloadCurrent = new ICONST(0);
/*  509:     */       }
/*  510:     */     }
/*  511: 660 */     return this._iloadCurrent;
/*  512:     */   }
/*  513:     */   
/*  514:     */   public Instruction storeCurrentNode()
/*  515:     */   {
/*  516: 664 */     return this._istoreCurrent = new ISTORE(getLocalIndex("current"));
/*  517:     */   }
/*  518:     */   
/*  519:     */   public Instruction loadContextNode()
/*  520:     */   {
/*  521: 671 */     return loadCurrentNode();
/*  522:     */   }
/*  523:     */   
/*  524:     */   public Instruction storeContextNode()
/*  525:     */   {
/*  526: 675 */     return storeCurrentNode();
/*  527:     */   }
/*  528:     */   
/*  529:     */   public int getLocalIndex(String name)
/*  530:     */   {
/*  531: 679 */     return getLocalVariable(name).getIndex();
/*  532:     */   }
/*  533:     */   
/*  534:     */   public LocalVariableGen getLocalVariable(String name)
/*  535:     */   {
/*  536: 683 */     return getLocalVariableRegistry().lookUpByName(name);
/*  537:     */   }
/*  538:     */   
/*  539:     */   public void setMaxLocals()
/*  540:     */   {
/*  541: 689 */     int maxLocals = super.getMaxLocals();
/*  542:     */     
/*  543:     */ 
/*  544: 692 */     LocalVariableGen[] localVars = super.getLocalVariables();
/*  545: 693 */     if ((localVars != null) && 
/*  546: 694 */       (localVars.length > maxLocals)) {
/*  547: 695 */       maxLocals = localVars.length;
/*  548:     */     }
/*  549: 699 */     if (maxLocals < 5) {
/*  550: 699 */       maxLocals = 5;
/*  551:     */     }
/*  552: 701 */     super.setMaxLocals(maxLocals);
/*  553:     */   }
/*  554:     */   
/*  555:     */   public void addInstructionList(Pattern pattern, InstructionList ilist)
/*  556:     */   {
/*  557: 708 */     this._preCompiled.put(pattern, ilist);
/*  558:     */   }
/*  559:     */   
/*  560:     */   public InstructionList getInstructionList(Pattern pattern)
/*  561:     */   {
/*  562: 716 */     return (InstructionList)this._preCompiled.get(pattern);
/*  563:     */   }
/*  564:     */   
/*  565:     */   private class Chunk
/*  566:     */     implements Comparable
/*  567:     */   {
/*  568:     */     private InstructionHandle m_start;
/*  569:     */     private InstructionHandle m_end;
/*  570:     */     private int m_size;
/*  571:     */     
/*  572:     */     Chunk(InstructionHandle start, InstructionHandle end)
/*  573:     */     {
/*  574: 757 */       this.m_start = start;
/*  575: 758 */       this.m_end = end;
/*  576: 759 */       this.m_size = (end.getPosition() - start.getPosition());
/*  577:     */     }
/*  578:     */     
/*  579:     */     boolean isAdjacentTo(Chunk neighbour)
/*  580:     */     {
/*  581: 773 */       return getChunkEnd().getNext() == neighbour.getChunkStart();
/*  582:     */     }
/*  583:     */     
/*  584:     */     InstructionHandle getChunkStart()
/*  585:     */     {
/*  586: 782 */       return this.m_start;
/*  587:     */     }
/*  588:     */     
/*  589:     */     InstructionHandle getChunkEnd()
/*  590:     */     {
/*  591: 790 */       return this.m_end;
/*  592:     */     }
/*  593:     */     
/*  594:     */     int getChunkSize()
/*  595:     */     {
/*  596: 799 */       return this.m_size;
/*  597:     */     }
/*  598:     */     
/*  599:     */     public int compareTo(Object comparand)
/*  600:     */     {
/*  601: 815 */       return getChunkSize() - ((Chunk)comparand).getChunkSize();
/*  602:     */     }
/*  603:     */   }
/*  604:     */   
/*  605:     */   private ArrayList getCandidateChunks(ClassGenerator classGen, int totalMethodSize)
/*  606:     */   {
/*  607: 830 */     Iterator instructions = getInstructionList().iterator();
/*  608: 831 */     ArrayList candidateChunks = new ArrayList();
/*  609: 832 */     ArrayList currLevelChunks = new ArrayList();
/*  610: 833 */     Stack subChunkStack = new Stack();
/*  611: 834 */     boolean openChunkAtCurrLevel = false;
/*  612: 835 */     boolean firstInstruction = true;
/*  613: 839 */     if (this.m_openChunks != 0)
/*  614:     */     {
/*  615: 840 */       String msg = new ErrorMsg("OUTLINE_ERR_UNBALANCED_MARKERS").toString();
/*  616:     */       
/*  617:     */ 
/*  618: 843 */       throw new InternalError(msg);
/*  619:     */     }
/*  620:     */     InstructionHandle currentHandle;
/*  621:     */     do
/*  622:     */     {
/*  623: 864 */       currentHandle = instructions.hasNext() ? (InstructionHandle)instructions.next() : null;
/*  624:     */       
/*  625:     */ 
/*  626: 867 */       Instruction inst = currentHandle != null ? currentHandle.getInstruction() : null;
/*  627: 874 */       if (firstInstruction)
/*  628:     */       {
/*  629: 875 */         openChunkAtCurrLevel = true;
/*  630: 876 */         currLevelChunks.add(currentHandle);
/*  631: 877 */         firstInstruction = false;
/*  632:     */       }
/*  633: 881 */       if ((inst instanceof OutlineableChunkStart))
/*  634:     */       {
/*  635: 886 */         if (openChunkAtCurrLevel)
/*  636:     */         {
/*  637: 887 */           subChunkStack.push(currLevelChunks);
/*  638: 888 */           currLevelChunks = new ArrayList();
/*  639:     */         }
/*  640: 891 */         openChunkAtCurrLevel = true;
/*  641: 892 */         currLevelChunks.add(currentHandle);
/*  642:     */       }
/*  643: 894 */       else if ((currentHandle == null) || ((inst instanceof OutlineableChunkEnd)))
/*  644:     */       {
/*  645: 896 */         ArrayList nestedSubChunks = null;
/*  646: 903 */         if (!openChunkAtCurrLevel)
/*  647:     */         {
/*  648: 904 */           nestedSubChunks = currLevelChunks;
/*  649: 905 */           currLevelChunks = (ArrayList)subChunkStack.pop();
/*  650:     */         }
/*  651: 910 */         InstructionHandle chunkStart = (InstructionHandle)currLevelChunks.get(currLevelChunks.size() - 1);
/*  652:     */         
/*  653:     */ 
/*  654:     */ 
/*  655: 914 */         int chunkEndPosition = currentHandle != null ? currentHandle.getPosition() : totalMethodSize;
/*  656:     */         
/*  657:     */ 
/*  658: 917 */         int chunkSize = chunkEndPosition - chunkStart.getPosition();
/*  659: 931 */         if (chunkSize <= 60000)
/*  660:     */         {
/*  661: 932 */           currLevelChunks.add(currentHandle);
/*  662:     */         }
/*  663:     */         else
/*  664:     */         {
/*  665: 934 */           if (!openChunkAtCurrLevel)
/*  666:     */           {
/*  667: 935 */             int childChunkCount = nestedSubChunks.size() / 2;
/*  668: 936 */             if (childChunkCount > 0)
/*  669:     */             {
/*  670: 937 */               Chunk[] childChunks = new Chunk[childChunkCount];
/*  671: 940 */               for (int i = 0; i < childChunkCount; i++)
/*  672:     */               {
/*  673: 941 */                 InstructionHandle start = (InstructionHandle)nestedSubChunks.get(i * 2);
/*  674:     */                 
/*  675:     */ 
/*  676: 944 */                 InstructionHandle end = (InstructionHandle)nestedSubChunks.get(i * 2 + 1);
/*  677:     */                 
/*  678:     */ 
/*  679:     */ 
/*  680: 948 */                 childChunks[i] = new Chunk(start, end);
/*  681:     */               }
/*  682: 952 */               ArrayList mergedChildChunks = mergeAdjacentChunks(childChunks);
/*  683: 957 */               for (int i = 0; i < mergedChildChunks.size(); i++)
/*  684:     */               {
/*  685: 958 */                 Chunk mergedChunk = (Chunk)mergedChildChunks.get(i);
/*  686:     */                 
/*  687: 960 */                 int mergedSize = mergedChunk.getChunkSize();
/*  688: 962 */                 if ((mergedSize >= 1000) && (mergedSize <= 60000)) {
/*  689: 964 */                   candidateChunks.add(mergedChunk);
/*  690:     */                 }
/*  691:     */               }
/*  692:     */             }
/*  693:     */           }
/*  694: 971 */           currLevelChunks.remove(currLevelChunks.size() - 1);
/*  695:     */         }
/*  696: 977 */         openChunkAtCurrLevel = (currLevelChunks.size() & 0x1) == 1;
/*  697:     */       }
/*  698: 980 */     } while (currentHandle != null);
/*  699: 982 */     return candidateChunks;
/*  700:     */   }
/*  701:     */   
/*  702:     */   private ArrayList mergeAdjacentChunks(Chunk[] chunks)
/*  703:     */   {
/*  704: 995 */     int[] adjacencyRunStart = new int[chunks.length];
/*  705: 996 */     int[] adjacencyRunLength = new int[chunks.length];
/*  706: 997 */     boolean[] chunkWasMerged = new boolean[chunks.length];
/*  707:     */     
/*  708: 999 */     int maximumRunOfChunks = 0;
/*  709:     */     
/*  710:1001 */     int numAdjacentRuns = 0;
/*  711:     */     
/*  712:1003 */     ArrayList mergedChunks = new ArrayList();
/*  713:     */     
/*  714:1005 */     int startOfCurrentRun = 0;
/*  715:1013 */     for (int i = 1; i < chunks.length; i++) {
/*  716:1014 */       if (!chunks[(i - 1)].isAdjacentTo(chunks[i]))
/*  717:     */       {
/*  718:1015 */         int lengthOfRun = i - startOfCurrentRun;
/*  719:1018 */         if (maximumRunOfChunks < lengthOfRun) {
/*  720:1019 */           maximumRunOfChunks = lengthOfRun;
/*  721:     */         }
/*  722:1022 */         if (lengthOfRun > 1)
/*  723:     */         {
/*  724:1023 */           adjacencyRunLength[numAdjacentRuns] = lengthOfRun;
/*  725:1024 */           adjacencyRunStart[numAdjacentRuns] = startOfCurrentRun;
/*  726:1025 */           numAdjacentRuns++;
/*  727:     */         }
/*  728:1028 */         startOfCurrentRun = i;
/*  729:     */       }
/*  730:     */     }
/*  731:1032 */     if (chunks.length - startOfCurrentRun > 1)
/*  732:     */     {
/*  733:1033 */       int lengthOfRun = chunks.length - startOfCurrentRun;
/*  734:1036 */       if (maximumRunOfChunks < lengthOfRun) {
/*  735:1037 */         maximumRunOfChunks = lengthOfRun;
/*  736:     */       }
/*  737:1040 */       adjacencyRunLength[numAdjacentRuns] = (chunks.length - startOfCurrentRun);
/*  738:     */       
/*  739:1042 */       adjacencyRunStart[numAdjacentRuns] = startOfCurrentRun;
/*  740:1043 */       numAdjacentRuns++;
/*  741:     */     }
/*  742:1057 */     for (int numToMerge = maximumRunOfChunks; numToMerge > 1; numToMerge--) {
/*  743:1059 */       for (int run = 0; run < numAdjacentRuns; run++)
/*  744:     */       {
/*  745:1060 */         int runStart = adjacencyRunStart[run];
/*  746:1061 */         int runEnd = runStart + adjacencyRunLength[run] - 1;
/*  747:     */         
/*  748:1063 */         boolean foundChunksToMerge = false;
/*  749:1068 */         for (int mergeStart = runStart; (mergeStart + numToMerge - 1 <= runEnd) && (!foundChunksToMerge); mergeStart++)
/*  750:     */         {
/*  751:1071 */           int mergeEnd = mergeStart + numToMerge - 1;
/*  752:1072 */           int mergeSize = 0;
/*  753:1075 */           for (int j = mergeStart; j <= mergeEnd; j++) {
/*  754:1076 */             mergeSize += chunks[j].getChunkSize();
/*  755:     */           }
/*  756:1081 */           if (mergeSize <= 60000)
/*  757:     */           {
/*  758:1082 */             foundChunksToMerge = true;
/*  759:1084 */             for (int j = mergeStart; j <= mergeEnd; j++) {
/*  760:1085 */               chunkWasMerged[j] = true;
/*  761:     */             }
/*  762:1088 */             mergedChunks.add(new Chunk(chunks[mergeStart].getChunkStart(), chunks[mergeEnd].getChunkEnd()));
/*  763:     */             
/*  764:     */ 
/*  765:     */ 
/*  766:     */ 
/*  767:     */ 
/*  768:1094 */             adjacencyRunStart[run] -= mergeStart;
/*  769:     */             
/*  770:     */ 
/*  771:1097 */             int trailingRunLength = runEnd - mergeEnd;
/*  772:1102 */             if (trailingRunLength >= 2)
/*  773:     */             {
/*  774:1103 */               adjacencyRunStart[numAdjacentRuns] = (mergeEnd + 1);
/*  775:1104 */               adjacencyRunLength[numAdjacentRuns] = trailingRunLength;
/*  776:     */               
/*  777:1106 */               numAdjacentRuns++;
/*  778:     */             }
/*  779:     */           }
/*  780:     */         }
/*  781:     */       }
/*  782:     */     }
/*  783:1115 */     for (int i = 0; i < chunks.length; i++) {
/*  784:1116 */       if (chunkWasMerged[i] == 0) {
/*  785:1117 */         mergedChunks.add(chunks[i]);
/*  786:     */       }
/*  787:     */     }
/*  788:1121 */     return mergedChunks;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public Method[] outlineChunks(ClassGenerator classGen, int originalMethodSize)
/*  792:     */   {
/*  793:1137 */     ArrayList methodsOutlined = new ArrayList();
/*  794:1138 */     int currentMethodSize = originalMethodSize;
/*  795:     */     
/*  796:1140 */     int outlinedCount = 0;
/*  797:     */     
/*  798:1142 */     String originalMethodName = getName();
/*  799:1147 */     if (originalMethodName.equals("<init>")) {
/*  800:1148 */       originalMethodName = "$lt$init$gt$";
/*  801:1149 */     } else if (originalMethodName.equals("<clinit>")) {
/*  802:1150 */       originalMethodName = "$lt$clinit$gt$";
/*  803:     */     }
/*  804:     */     boolean moreMethodsOutlined;
/*  805:     */     do
/*  806:     */     {
/*  807:1158 */       ArrayList candidateChunks = getCandidateChunks(classGen, currentMethodSize);
/*  808:     */       
/*  809:1160 */       Collections.sort(candidateChunks);
/*  810:     */       
/*  811:1162 */       moreMethodsOutlined = false;
/*  812:1168 */       for (int i = candidateChunks.size() - 1; (i >= 0) && (currentMethodSize > 60000); i--)
/*  813:     */       {
/*  814:1171 */         Chunk chunkToOutline = (Chunk)candidateChunks.get(i);
/*  815:     */         
/*  816:1173 */         methodsOutlined.add(outline(chunkToOutline.getChunkStart(), chunkToOutline.getChunkEnd(), originalMethodName + "$outline$" + outlinedCount, classGen));
/*  817:     */         
/*  818:     */ 
/*  819:     */ 
/*  820:     */ 
/*  821:1178 */         outlinedCount++;
/*  822:1179 */         moreMethodsOutlined = true;
/*  823:     */         
/*  824:1181 */         InstructionList il = getInstructionList();
/*  825:1182 */         InstructionHandle lastInst = il.getEnd();
/*  826:1183 */         il.setPositions();
/*  827:     */         
/*  828:     */ 
/*  829:1186 */         currentMethodSize = lastInst.getPosition() + lastInst.getInstruction().getLength();
/*  830:     */       }
/*  831:1190 */     } while ((moreMethodsOutlined) && (currentMethodSize > 60000));
/*  832:1194 */     if (currentMethodSize > 65535)
/*  833:     */     {
/*  834:1195 */       String msg = new ErrorMsg("OUTLINE_ERR_METHOD_TOO_BIG").toString();
/*  835:     */       
/*  836:1197 */       throw new InternalError(msg);
/*  837:     */     }
/*  838:1200 */     Method[] methodsArr = new Method[methodsOutlined.size() + 1];
/*  839:1201 */     methodsOutlined.toArray(methodsArr);
/*  840:     */     
/*  841:1203 */     methodsArr[methodsOutlined.size()] = getThisMethod();
/*  842:     */     
/*  843:1205 */     return methodsArr;
/*  844:     */   }
/*  845:     */   
/*  846:     */   private Method outline(InstructionHandle first, InstructionHandle last, String outlinedMethodName, ClassGenerator classGen)
/*  847:     */   {
/*  848:1226 */     if (getExceptionHandlers().length != 0)
/*  849:     */     {
/*  850:1227 */       String msg = new ErrorMsg("OUTLINE_ERR_TRY_CATCH").toString();
/*  851:     */       
/*  852:1229 */       throw new InternalError(msg);
/*  853:     */     }
/*  854:1232 */     int outlineChunkStartOffset = first.getPosition();
/*  855:1233 */     int outlineChunkEndOffset = last.getPosition() + last.getInstruction().getLength();
/*  856:     */     
/*  857:     */ 
/*  858:1236 */     ConstantPoolGen cpg = getConstantPool();
/*  859:     */     
/*  860:     */ 
/*  861:     */ 
/*  862:     */ 
/*  863:     */ 
/*  864:     */ 
/*  865:     */ 
/*  866:     */ 
/*  867:     */ 
/*  868:     */ 
/*  869:     */ 
/*  870:     */ 
/*  871:     */ 
/*  872:     */ 
/*  873:1251 */     InstructionList newIL = new InstructionList();
/*  874:     */     
/*  875:1253 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/*  876:1254 */     String argTypeName = xsltc.getHelperClassName();
/*  877:1255 */     Type[] argTypes = { new ObjectType(argTypeName).toJCType() };
/*  878:     */     
/*  879:1257 */     String argName = "copyLocals";
/*  880:1258 */     String[] argNames = { "copyLocals" };
/*  881:     */     
/*  882:1260 */     int methodAttributes = 18;
/*  883:1261 */     boolean isStaticMethod = (getAccessFlags() & 0x8) != 0;
/*  884:1263 */     if (isStaticMethod) {
/*  885:1264 */       methodAttributes |= 0x8;
/*  886:     */     }
/*  887:1267 */     MethodGenerator outlinedMethodGen = new MethodGenerator(methodAttributes, Type.VOID, argTypes, argNames, outlinedMethodName, getClassName(), newIL, cpg);
/*  888:     */     
/*  889:     */ 
/*  890:     */ 
/*  891:     */ 
/*  892:     */ 
/*  893:     */ 
/*  894:     */ 
/*  895:     */ 
/*  896:1276 */     ClassGenerator copyAreaCG = new ClassGenerator(argTypeName, "java.lang.Object", argTypeName + ".java", 49, null, classGen.getStylesheet())
/*  897:     */     {
/*  898:     */       public boolean isExternal()
/*  899:     */       {
/*  900:1281 */         return true;
/*  901:     */       }
/*  902:1283 */     };
/*  903:1284 */     ConstantPoolGen copyAreaCPG = copyAreaCG.getConstantPool();
/*  904:1285 */     copyAreaCG.addEmptyConstructor(1);
/*  905:     */     
/*  906:     */ 
/*  907:1288 */     int copyAreaFieldCount = 0;
/*  908:     */     
/*  909:     */ 
/*  910:     */ 
/*  911:     */ 
/*  912:     */ 
/*  913:     */ 
/*  914:1295 */     InstructionHandle limit = last.getNext();
/*  915:     */     
/*  916:     */ 
/*  917:     */ 
/*  918:     */ 
/*  919:     */ 
/*  920:     */ 
/*  921:     */ 
/*  922:     */ 
/*  923:     */ 
/*  924:     */ 
/*  925:     */ 
/*  926:1307 */     InstructionList oldMethCopyInIL = new InstructionList();
/*  927:1308 */     InstructionList oldMethCopyOutIL = new InstructionList();
/*  928:1309 */     InstructionList newMethCopyInIL = new InstructionList();
/*  929:1310 */     InstructionList newMethCopyOutIL = new InstructionList();
/*  930:     */     
/*  931:     */ 
/*  932:     */ 
/*  933:     */ 
/*  934:1315 */     InstructionHandle outlinedMethodCallSetup = oldMethCopyInIL.append(new NEW(cpg.addClass(argTypeName)));
/*  935:     */     
/*  936:1317 */     oldMethCopyInIL.append(InstructionConstants.DUP);
/*  937:1318 */     oldMethCopyInIL.append(InstructionConstants.DUP);
/*  938:1319 */     oldMethCopyInIL.append(new INVOKESPECIAL(cpg.addMethodref(argTypeName, "<init>", "()V")));
/*  939:     */     InstructionHandle outlinedMethodRef;
/*  940:1326 */     if (isStaticMethod)
/*  941:     */     {
/*  942:1327 */       outlinedMethodRef = oldMethCopyOutIL.append(new INVOKESTATIC(cpg.addMethodref(classGen.getClassName(), outlinedMethodName, outlinedMethodGen.getSignature())));
/*  943:     */     }
/*  944:     */     else
/*  945:     */     {
/*  946:1334 */       oldMethCopyOutIL.append(InstructionConstants.THIS);
/*  947:1335 */       oldMethCopyOutIL.append(InstructionConstants.SWAP);
/*  948:1336 */       outlinedMethodRef = oldMethCopyOutIL.append(new INVOKEVIRTUAL(cpg.addMethodref(classGen.getClassName(), outlinedMethodName, outlinedMethodGen.getSignature())));
/*  949:     */     }
/*  950:1346 */     boolean chunkStartTargetMappingsPending = false;
/*  951:1347 */     InstructionHandle pendingTargetMappingHandle = null;
/*  952:     */     
/*  953:     */ 
/*  954:1350 */     InstructionHandle lastCopyHandle = null;
/*  955:     */     
/*  956:     */ 
/*  957:     */ 
/*  958:     */ 
/*  959:     */ 
/*  960:1356 */     HashMap targetMap = new HashMap();
/*  961:     */     
/*  962:     */ 
/*  963:     */ 
/*  964:1360 */     HashMap localVarMap = new HashMap();
/*  965:     */     
/*  966:1362 */     HashMap revisedLocalVarStart = new HashMap();
/*  967:1363 */     HashMap revisedLocalVarEnd = new HashMap();
/*  968:1375 */     for (InstructionHandle ih = first; ih != limit; ih = ih.getNext())
/*  969:     */     {
/*  970:1376 */       Instruction inst = ih.getInstruction();
/*  971:1383 */       if ((inst instanceof MarkerInstruction))
/*  972:     */       {
/*  973:1384 */         if (ih.hasTargeters()) {
/*  974:1385 */           if ((inst instanceof OutlineableChunkEnd))
/*  975:     */           {
/*  976:1386 */             targetMap.put(ih, lastCopyHandle);
/*  977:     */           }
/*  978:1388 */           else if (!chunkStartTargetMappingsPending)
/*  979:     */           {
/*  980:1389 */             chunkStartTargetMappingsPending = true;
/*  981:1390 */             pendingTargetMappingHandle = ih;
/*  982:     */           }
/*  983:     */         }
/*  984:     */       }
/*  985:     */       else
/*  986:     */       {
/*  987:1397 */         Instruction c = inst.copy();
/*  988:1399 */         if ((c instanceof BranchInstruction)) {
/*  989:1400 */           lastCopyHandle = newIL.append((BranchInstruction)c);
/*  990:     */         } else {
/*  991:1402 */           lastCopyHandle = newIL.append(c);
/*  992:     */         }
/*  993:1405 */         if (((c instanceof LocalVariableInstruction)) || ((c instanceof RET)))
/*  994:     */         {
/*  995:1413 */           IndexedInstruction lvi = (IndexedInstruction)c;
/*  996:1414 */           int oldLocalVarIndex = lvi.getIndex();
/*  997:1415 */           LocalVariableGen oldLVG = getLocalVariableRegistry().lookupRegisteredLocalVariable(oldLocalVarIndex, ih.getPosition());
/*  998:     */           
/*  999:     */ 
/* 1000:     */ 
/* 1001:1419 */           LocalVariableGen newLVG = (LocalVariableGen)localVarMap.get(oldLVG);
/* 1002:1424 */           if (localVarMap.get(oldLVG) == null)
/* 1003:     */           {
/* 1004:1436 */             boolean copyInLocalValue = offsetInLocalVariableGenRange(oldLVG, outlineChunkStartOffset != 0 ? outlineChunkStartOffset - 1 : 0);
/* 1005:     */             
/* 1006:     */ 
/* 1007:     */ 
/* 1008:     */ 
/* 1009:1441 */             boolean copyOutLocalValue = offsetInLocalVariableGenRange(oldLVG, outlineChunkEndOffset + 1);
/* 1010:1449 */             if ((copyInLocalValue) || (copyOutLocalValue))
/* 1011:     */             {
/* 1012:1450 */               String varName = oldLVG.getName();
/* 1013:1451 */               Type varType = oldLVG.getType();
/* 1014:1452 */               newLVG = outlinedMethodGen.addLocalVariable(varName, varType, null, null);
/* 1015:     */               
/* 1016:     */ 
/* 1017:     */ 
/* 1018:1456 */               int newLocalVarIndex = newLVG.getIndex();
/* 1019:1457 */               String varSignature = varType.getSignature();
/* 1020:     */               
/* 1021:     */ 
/* 1022:1460 */               localVarMap.put(oldLVG, newLVG);
/* 1023:     */               
/* 1024:1462 */               copyAreaFieldCount++;
/* 1025:1463 */               String copyAreaFieldName = "field" + copyAreaFieldCount;
/* 1026:     */               
/* 1027:1465 */               copyAreaCG.addField(new Field(1, copyAreaCPG.addUtf8(copyAreaFieldName), copyAreaCPG.addUtf8(varSignature), null, copyAreaCPG.getConstantPool()));
/* 1028:     */               
/* 1029:     */ 
/* 1030:     */ 
/* 1031:     */ 
/* 1032:     */ 
/* 1033:1471 */               int fieldRef = cpg.addFieldref(argTypeName, copyAreaFieldName, varSignature);
/* 1034:1475 */               if (copyInLocalValue)
/* 1035:     */               {
/* 1036:1480 */                 oldMethCopyInIL.append(InstructionConstants.DUP);
/* 1037:     */                 
/* 1038:1482 */                 InstructionHandle copyInLoad = oldMethCopyInIL.append(loadLocal(oldLocalVarIndex, varType));
/* 1039:     */                 
/* 1040:     */ 
/* 1041:1485 */                 oldMethCopyInIL.append(new PUTFIELD(fieldRef));
/* 1042:1491 */                 if (!copyOutLocalValue) {
/* 1043:1492 */                   revisedLocalVarEnd.put(oldLVG, copyInLoad);
/* 1044:     */                 }
/* 1045:1499 */                 newMethCopyInIL.append(InstructionConstants.ALOAD_1);
/* 1046:     */                 
/* 1047:1501 */                 newMethCopyInIL.append(new GETFIELD(fieldRef));
/* 1048:1502 */                 newMethCopyInIL.append(storeLocal(newLocalVarIndex, varType));
/* 1049:     */               }
/* 1050:1506 */               if (copyOutLocalValue)
/* 1051:     */               {
/* 1052:1511 */                 newMethCopyOutIL.append(InstructionConstants.ALOAD_1);
/* 1053:     */                 
/* 1054:1513 */                 newMethCopyOutIL.append(loadLocal(newLocalVarIndex, varType));
/* 1055:     */                 
/* 1056:1515 */                 newMethCopyOutIL.append(new PUTFIELD(fieldRef));
/* 1057:     */                 
/* 1058:     */ 
/* 1059:     */ 
/* 1060:     */ 
/* 1061:     */ 
/* 1062:1521 */                 oldMethCopyOutIL.append(InstructionConstants.DUP);
/* 1063:     */                 
/* 1064:1523 */                 oldMethCopyOutIL.append(new GETFIELD(fieldRef));
/* 1065:1524 */                 InstructionHandle copyOutStore = oldMethCopyOutIL.append(storeLocal(oldLocalVarIndex, varType));
/* 1066:1532 */                 if (!copyInLocalValue) {
/* 1067:1533 */                   revisedLocalVarStart.put(oldLVG, copyOutStore);
/* 1068:     */                 }
/* 1069:     */               }
/* 1070:     */             }
/* 1071:     */           }
/* 1072:     */         }
/* 1073:1541 */         if (ih.hasTargeters()) {
/* 1074:1542 */           targetMap.put(ih, lastCopyHandle);
/* 1075:     */         }
/* 1076:1549 */         if (chunkStartTargetMappingsPending)
/* 1077:     */         {
/* 1078:     */           do
/* 1079:     */           {
/* 1080:1551 */             targetMap.put(pendingTargetMappingHandle, lastCopyHandle);
/* 1081:     */             
/* 1082:1553 */             pendingTargetMappingHandle = pendingTargetMappingHandle.getNext();
/* 1083:1555 */           } while (pendingTargetMappingHandle != ih);
/* 1084:1557 */           chunkStartTargetMappingsPending = false;
/* 1085:     */         }
/* 1086:     */       }
/* 1087:     */     }
/* 1088:1564 */     InstructionHandle ih = first;
/* 1089:1565 */     InstructionHandle ch = newIL.getStart();
/* 1090:1567 */     while (ch != null)
/* 1091:     */     {
/* 1092:1569 */       Instruction i = ih.getInstruction();
/* 1093:1570 */       Instruction c = ch.getInstruction();
/* 1094:1572 */       if ((i instanceof BranchInstruction))
/* 1095:     */       {
/* 1096:1573 */         BranchInstruction bc = (BranchInstruction)c;
/* 1097:1574 */         BranchInstruction bi = (BranchInstruction)i;
/* 1098:1575 */         InstructionHandle itarget = bi.getTarget();
/* 1099:     */         
/* 1100:     */ 
/* 1101:1578 */         InstructionHandle newTarget = (InstructionHandle)targetMap.get(itarget);
/* 1102:     */         
/* 1103:     */ 
/* 1104:1581 */         bc.setTarget(newTarget);
/* 1105:1585 */         if ((bi instanceof Select))
/* 1106:     */         {
/* 1107:1586 */           InstructionHandle[] itargets = ((Select)bi).getTargets();
/* 1108:1587 */           InstructionHandle[] ctargets = ((Select)bc).getTargets();
/* 1109:1590 */           for (int j = 0; j < itargets.length; j++) {
/* 1110:1591 */             ctargets[j] = ((InstructionHandle)targetMap.get(itargets[j]));
/* 1111:     */           }
/* 1112:     */         }
/* 1113:     */       }
/* 1114:1595 */       else if (((i instanceof LocalVariableInstruction)) || ((i instanceof RET)))
/* 1115:     */       {
/* 1116:1600 */         IndexedInstruction lvi = (IndexedInstruction)c;
/* 1117:1601 */         int oldLocalVarIndex = lvi.getIndex();
/* 1118:1602 */         LocalVariableGen oldLVG = getLocalVariableRegistry().lookupRegisteredLocalVariable(oldLocalVarIndex, ih.getPosition());
/* 1119:     */         
/* 1120:     */ 
/* 1121:     */ 
/* 1122:1606 */         LocalVariableGen newLVG = (LocalVariableGen)localVarMap.get(oldLVG);
/* 1123:     */         int newLocalVarIndex;
/* 1124:1610 */         if (newLVG == null)
/* 1125:     */         {
/* 1126:1615 */           String varName = oldLVG.getName();
/* 1127:1616 */           Type varType = oldLVG.getType();
/* 1128:1617 */           newLVG = outlinedMethodGen.addLocalVariable(varName, varType, null, null);
/* 1129:     */           
/* 1130:     */ 
/* 1131:     */ 
/* 1132:1621 */           newLocalVarIndex = newLVG.getIndex();
/* 1133:1622 */           localVarMap.put(oldLVG, newLVG);
/* 1134:     */           
/* 1135:     */ 
/* 1136:     */ 
/* 1137:     */ 
/* 1138:     */ 
/* 1139:     */ 
/* 1140:1629 */           revisedLocalVarStart.put(oldLVG, outlinedMethodRef);
/* 1141:1630 */           revisedLocalVarEnd.put(oldLVG, outlinedMethodRef);
/* 1142:     */         }
/* 1143:     */         else
/* 1144:     */         {
/* 1145:1632 */           newLocalVarIndex = newLVG.getIndex();
/* 1146:     */         }
/* 1147:1634 */         lvi.setIndex(newLocalVarIndex);
/* 1148:     */       }
/* 1149:1641 */       if (ih.hasTargeters())
/* 1150:     */       {
/* 1151:1642 */         InstructionTargeter[] targeters = ih.getTargeters();
/* 1152:1644 */         for (int idx = 0; idx < targeters.length; idx++)
/* 1153:     */         {
/* 1154:1645 */           InstructionTargeter targeter = targeters[idx];
/* 1155:1647 */           if (((targeter instanceof LocalVariableGen)) && (((LocalVariableGen)targeter).getEnd() == ih))
/* 1156:     */           {
/* 1157:1649 */             Object newLVG = localVarMap.get(targeter);
/* 1158:1650 */             if (newLVG != null) {
/* 1159:1651 */               outlinedMethodGen.removeLocalVariable((LocalVariableGen)newLVG);
/* 1160:     */             }
/* 1161:     */           }
/* 1162:     */         }
/* 1163:     */       }
/* 1164:1661 */       if (!(i instanceof MarkerInstruction)) {
/* 1165:1662 */         ch = ch.getNext();
/* 1166:     */       }
/* 1167:1664 */       ih = ih.getNext();
/* 1168:     */     }
/* 1169:1669 */     oldMethCopyOutIL.append(InstructionConstants.POP);
/* 1170:     */     
/* 1171:     */ 
/* 1172:     */ 
/* 1173:1673 */     Iterator revisedLocalVarStartPairIter = revisedLocalVarStart.entrySet().iterator();
/* 1174:1675 */     while (revisedLocalVarStartPairIter.hasNext())
/* 1175:     */     {
/* 1176:1676 */       Map.Entry lvgRangeStartPair = (Map.Entry)revisedLocalVarStartPairIter.next();
/* 1177:     */       
/* 1178:1678 */       LocalVariableGen lvg = (LocalVariableGen)lvgRangeStartPair.getKey();
/* 1179:1679 */       InstructionHandle startInst = (InstructionHandle)lvgRangeStartPair.getValue();
/* 1180:     */       
/* 1181:     */ 
/* 1182:1682 */       lvg.setStart(startInst);
/* 1183:     */     }
/* 1184:1686 */     Iterator revisedLocalVarEndPairIter = revisedLocalVarEnd.entrySet().iterator();
/* 1185:1688 */     while (revisedLocalVarEndPairIter.hasNext())
/* 1186:     */     {
/* 1187:1689 */       Map.Entry lvgRangeEndPair = (Map.Entry)revisedLocalVarEndPairIter.next();
/* 1188:     */       
/* 1189:1691 */       LocalVariableGen lvg = (LocalVariableGen)lvgRangeEndPair.getKey();
/* 1190:1692 */       InstructionHandle endInst = (InstructionHandle)lvgRangeEndPair.getValue();
/* 1191:     */       
/* 1192:     */ 
/* 1193:1695 */       lvg.setEnd(endInst);
/* 1194:     */     }
/* 1195:1698 */     xsltc.dumpClass(copyAreaCG.getJavaClass());
/* 1196:     */     
/* 1197:     */ 
/* 1198:     */ 
/* 1199:1702 */     InstructionList oldMethodIL = getInstructionList();
/* 1200:     */     
/* 1201:1704 */     oldMethodIL.insert(first, oldMethCopyInIL);
/* 1202:1705 */     oldMethodIL.insert(first, oldMethCopyOutIL);
/* 1203:     */     
/* 1204:     */ 
/* 1205:1708 */     newIL.insert(newMethCopyInIL);
/* 1206:1709 */     newIL.append(newMethCopyOutIL);
/* 1207:1710 */     newIL.append(InstructionConstants.RETURN);
/* 1208:     */     InstructionHandle[] targets;
/* 1209:     */     int i;
/* 1210:     */     try
/* 1211:     */     {
/* 1212:1714 */       oldMethodIL.delete(first, last);
/* 1213:     */     }
/* 1214:     */     catch (TargetLostException e)
/* 1215:     */     {
/* 1216:1716 */       targets = e.getTargets();
/* 1217:     */       
/* 1218:     */ 
/* 1219:     */ 
/* 1220:     */ 
/* 1221:     */ 
/* 1222:     */ 
/* 1223:     */ 
/* 1224:1724 */       i = 0;
/* 1225:     */     }
/* 1226:1724 */     for (; i < targets.length; i++)
/* 1227:     */     {
/* 1228:1725 */       InstructionHandle lostTarget = targets[i];
/* 1229:1726 */       InstructionTargeter[] targeters = lostTarget.getTargeters();
/* 1230:1727 */       for (int j = 0; j < targeters.length; j++) {
/* 1231:1728 */         if ((targeters[j] instanceof LocalVariableGen))
/* 1232:     */         {
/* 1233:1729 */           LocalVariableGen lvgTargeter = (LocalVariableGen)targeters[j];
/* 1234:1735 */           if (lvgTargeter.getStart() == lostTarget) {
/* 1235:1736 */             lvgTargeter.setStart(outlinedMethodRef);
/* 1236:     */           }
/* 1237:1738 */           if (lvgTargeter.getEnd() == lostTarget) {
/* 1238:1739 */             lvgTargeter.setEnd(outlinedMethodRef);
/* 1239:     */           }
/* 1240:     */         }
/* 1241:     */         else
/* 1242:     */         {
/* 1243:1742 */           targeters[j].updateTarget(lostTarget, outlinedMethodCallSetup);
/* 1244:     */         }
/* 1245:     */       }
/* 1246:     */     }
/* 1247:1750 */     String[] exceptions = getExceptions();
/* 1248:1751 */     for (int i = 0; i < exceptions.length; i++) {
/* 1249:1752 */       outlinedMethodGen.addException(exceptions[i]);
/* 1250:     */     }
/* 1251:1755 */     return outlinedMethodGen.getThisMethod();
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   private static Instruction loadLocal(int index, Type type)
/* 1255:     */   {
/* 1256:1768 */     if (type == Type.BOOLEAN) {
/* 1257:1769 */       return new ILOAD(index);
/* 1258:     */     }
/* 1259:1770 */     if (type == Type.INT) {
/* 1260:1771 */       return new ILOAD(index);
/* 1261:     */     }
/* 1262:1772 */     if (type == Type.SHORT) {
/* 1263:1773 */       return new ILOAD(index);
/* 1264:     */     }
/* 1265:1774 */     if (type == Type.LONG) {
/* 1266:1775 */       return new LLOAD(index);
/* 1267:     */     }
/* 1268:1776 */     if (type == Type.BYTE) {
/* 1269:1777 */       return new ILOAD(index);
/* 1270:     */     }
/* 1271:1778 */     if (type == Type.CHAR) {
/* 1272:1779 */       return new ILOAD(index);
/* 1273:     */     }
/* 1274:1780 */     if (type == Type.FLOAT) {
/* 1275:1781 */       return new FLOAD(index);
/* 1276:     */     }
/* 1277:1782 */     if (type == Type.DOUBLE) {
/* 1278:1783 */       return new DLOAD(index);
/* 1279:     */     }
/* 1280:1785 */     return new ALOAD(index);
/* 1281:     */   }
/* 1282:     */   
/* 1283:     */   private static Instruction storeLocal(int index, Type type)
/* 1284:     */   {
/* 1285:1799 */     if (type == Type.BOOLEAN) {
/* 1286:1800 */       return new ISTORE(index);
/* 1287:     */     }
/* 1288:1801 */     if (type == Type.INT) {
/* 1289:1802 */       return new ISTORE(index);
/* 1290:     */     }
/* 1291:1803 */     if (type == Type.SHORT) {
/* 1292:1804 */       return new ISTORE(index);
/* 1293:     */     }
/* 1294:1805 */     if (type == Type.LONG) {
/* 1295:1806 */       return new LSTORE(index);
/* 1296:     */     }
/* 1297:1807 */     if (type == Type.BYTE) {
/* 1298:1808 */       return new ISTORE(index);
/* 1299:     */     }
/* 1300:1809 */     if (type == Type.CHAR) {
/* 1301:1810 */       return new ISTORE(index);
/* 1302:     */     }
/* 1303:1811 */     if (type == Type.FLOAT) {
/* 1304:1812 */       return new FSTORE(index);
/* 1305:     */     }
/* 1306:1813 */     if (type == Type.DOUBLE) {
/* 1307:1814 */       return new DSTORE(index);
/* 1308:     */     }
/* 1309:1816 */     return new ASTORE(index);
/* 1310:     */   }
/* 1311:     */   
/* 1312:1823 */   private int m_totalChunks = 0;
/* 1313:1829 */   private int m_openChunks = 0;
/* 1314:     */   
/* 1315:     */   public void markChunkStart()
/* 1316:     */   {
/* 1317:1841 */     getInstructionList().append(OutlineableChunkStart.OUTLINEABLECHUNKSTART);
/* 1318:     */     
/* 1319:1843 */     this.m_totalChunks += 1;
/* 1320:1844 */     this.m_openChunks += 1;
/* 1321:     */   }
/* 1322:     */   
/* 1323:     */   public void markChunkEnd()
/* 1324:     */   {
/* 1325:1853 */     getInstructionList().append(OutlineableChunkEnd.OUTLINEABLECHUNKEND);
/* 1326:     */     
/* 1327:1855 */     this.m_openChunks -= 1;
/* 1328:1856 */     if (this.m_openChunks < 0)
/* 1329:     */     {
/* 1330:1857 */       String msg = new ErrorMsg("OUTLINE_ERR_UNBALANCED_MARKERS").toString();
/* 1331:     */       
/* 1332:1859 */       throw new InternalError(msg);
/* 1333:     */     }
/* 1334:     */   }
/* 1335:     */   
/* 1336:     */   Method[] getGeneratedMethods(ClassGenerator classGen)
/* 1337:     */   {
/* 1338:1879 */     InstructionList il = getInstructionList();
/* 1339:1880 */     InstructionHandle last = il.getEnd();
/* 1340:     */     
/* 1341:1882 */     il.setPositions();
/* 1342:     */     
/* 1343:1884 */     int instructionListSize = last.getPosition() + last.getInstruction().getLength();
/* 1344:1889 */     if (instructionListSize > 32767)
/* 1345:     */     {
/* 1346:1890 */       boolean ilChanged = widenConditionalBranchTargetOffsets();
/* 1347:1894 */       if (ilChanged)
/* 1348:     */       {
/* 1349:1895 */         il.setPositions();
/* 1350:1896 */         last = il.getEnd();
/* 1351:1897 */         instructionListSize = last.getPosition() + last.getInstruction().getLength();
/* 1352:     */       }
/* 1353:     */     }
/* 1354:     */     Method[] generatedMethods;
/* 1355:1902 */     if (instructionListSize > 65535) {
/* 1356:1903 */       generatedMethods = outlineChunks(classGen, instructionListSize);
/* 1357:     */     } else {
/* 1358:1905 */       generatedMethods = new Method[] { getThisMethod() };
/* 1359:     */     }
/* 1360:1907 */     return generatedMethods;
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   protected Method getThisMethod()
/* 1364:     */   {
/* 1365:1911 */     stripAttributes(true);
/* 1366:1912 */     setMaxLocals();
/* 1367:1913 */     setMaxStack();
/* 1368:1914 */     removeNOPs();
/* 1369:     */     
/* 1370:1916 */     return getMethod();
/* 1371:     */   }
/* 1372:     */   
/* 1373:     */   boolean widenConditionalBranchTargetOffsets()
/* 1374:     */   {
/* 1375:1979 */     boolean ilChanged = false;
/* 1376:1980 */     int maxOffsetChange = 0;
/* 1377:1981 */     InstructionList il = getInstructionList();
/* 1378:1993 */     for (InstructionHandle ih = il.getStart(); ih != null; ih = ih.getNext())
/* 1379:     */     {
/* 1380:1996 */       Instruction inst = ih.getInstruction();
/* 1381:1998 */       switch (inst.getOpcode())
/* 1382:     */       {
/* 1383:     */       case 167: 
/* 1384:     */       case 168: 
/* 1385:2003 */         maxOffsetChange += 2;
/* 1386:2004 */         break;
/* 1387:     */       case 170: 
/* 1388:     */       case 171: 
/* 1389:2012 */         maxOffsetChange += 3;
/* 1390:2013 */         break;
/* 1391:     */       case 153: 
/* 1392:     */       case 154: 
/* 1393:     */       case 155: 
/* 1394:     */       case 156: 
/* 1395:     */       case 157: 
/* 1396:     */       case 158: 
/* 1397:     */       case 159: 
/* 1398:     */       case 160: 
/* 1399:     */       case 161: 
/* 1400:     */       case 162: 
/* 1401:     */       case 163: 
/* 1402:     */       case 164: 
/* 1403:     */       case 165: 
/* 1404:     */       case 166: 
/* 1405:     */       case 198: 
/* 1406:     */       case 199: 
/* 1407:2033 */         maxOffsetChange += 5;
/* 1408:     */       }
/* 1409:     */     }
/* 1410:2041 */     for (InstructionHandle ih = il.getStart(); ih != null; ih = ih.getNext())
/* 1411:     */     {
/* 1412:2044 */       Instruction inst = ih.getInstruction();
/* 1413:2046 */       if ((inst instanceof IfInstruction))
/* 1414:     */       {
/* 1415:2047 */         IfInstruction oldIfInst = (IfInstruction)inst;
/* 1416:2048 */         BranchHandle oldIfHandle = (BranchHandle)ih;
/* 1417:2049 */         InstructionHandle target = oldIfInst.getTarget();
/* 1418:2050 */         int relativeTargetOffset = target.getPosition() - oldIfHandle.getPosition();
/* 1419:2058 */         if ((relativeTargetOffset - maxOffsetChange < -32768) || (relativeTargetOffset + maxOffsetChange > 32767))
/* 1420:     */         {
/* 1421:2065 */           InstructionHandle nextHandle = oldIfHandle.getNext();
/* 1422:2066 */           IfInstruction invertedIfInst = oldIfInst.negate();
/* 1423:2067 */           BranchHandle invertedIfHandle = il.append(oldIfHandle, invertedIfInst);
/* 1424:     */           
/* 1425:     */ 
/* 1426:     */ 
/* 1427:     */ 
/* 1428:2072 */           BranchHandle gotoHandle = il.append(invertedIfHandle, new GOTO(target));
/* 1429:2078 */           if (nextHandle == null) {
/* 1430:2079 */             nextHandle = il.append(gotoHandle, InstructionConstants.NOP);
/* 1431:     */           }
/* 1432:2083 */           invertedIfHandle.updateTarget(target, nextHandle);
/* 1433:2088 */           if (oldIfHandle.hasTargeters())
/* 1434:     */           {
/* 1435:2089 */             InstructionTargeter[] targeters = oldIfHandle.getTargeters();
/* 1436:2092 */             for (int i = 0; i < targeters.length; i++)
/* 1437:     */             {
/* 1438:2093 */               InstructionTargeter targeter = targeters[i];
/* 1439:2107 */               if ((targeter instanceof LocalVariableGen))
/* 1440:     */               {
/* 1441:2108 */                 LocalVariableGen lvg = (LocalVariableGen)targeter;
/* 1442:2110 */                 if (lvg.getStart() == oldIfHandle) {
/* 1443:2111 */                   lvg.setStart(invertedIfHandle);
/* 1444:2112 */                 } else if (lvg.getEnd() == oldIfHandle) {
/* 1445:2113 */                   lvg.setEnd(gotoHandle);
/* 1446:     */                 }
/* 1447:     */               }
/* 1448:     */               else
/* 1449:     */               {
/* 1450:2116 */                 targeter.updateTarget(oldIfHandle, invertedIfHandle);
/* 1451:     */               }
/* 1452:     */             }
/* 1453:     */           }
/* 1454:     */           try
/* 1455:     */           {
/* 1456:2123 */             il.delete(oldIfHandle);
/* 1457:     */           }
/* 1458:     */           catch (TargetLostException tle)
/* 1459:     */           {
/* 1460:2128 */             String msg = new ErrorMsg("OUTLINE_ERR_DELETED_TARGET", tle.getMessage()).toString();
/* 1461:     */             
/* 1462:     */ 
/* 1463:2131 */             throw new InternalError(msg);
/* 1464:     */           }
/* 1465:2136 */           ih = gotoHandle;
/* 1466:     */           
/* 1467:     */ 
/* 1468:2139 */           ilChanged = true;
/* 1469:     */         }
/* 1470:     */       }
/* 1471:     */     }
/* 1472:2145 */     return ilChanged;
/* 1473:     */   }
/* 1474:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.MethodGenerator
 * JD-Core Version:    0.7.0.1
 */