/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.HashMap;
/*    5:     */ import java.util.List;
/*    6:     */ import java.util.Map;
/*    7:     */ import net.sourceforge.htmlunit.corejs.classfile.ClassFileWriter;
/*    8:     */ import net.sourceforge.htmlunit.corejs.javascript.CompilerEnvirons;
/*    9:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   10:     */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*   11:     */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   12:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*   13:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Jump;
/*   14:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*   15:     */ 
/*   16:     */ class BodyCodegen
/*   17:     */ {
/*   18:     */   private static final int JAVASCRIPT_EXCEPTION = 0;
/*   19:     */   private static final int EVALUATOR_EXCEPTION = 1;
/*   20:     */   private static final int ECMAERROR_EXCEPTION = 2;
/*   21:     */   private static final int THROWABLE_EXCEPTION = 3;
/*   22:     */   static final int GENERATOR_TERMINATE = -1;
/*   23:     */   static final int GENERATOR_START = 0;
/*   24:     */   static final int GENERATOR_YIELD_START = 1;
/*   25:     */   ClassFileWriter cfw;
/*   26:     */   Codegen codegen;
/*   27:     */   CompilerEnvirons compilerEnv;
/*   28:     */   ScriptNode scriptOrFn;
/*   29:     */   public int scriptOrFnIndex;
/*   30:     */   private int savedCodeOffset;
/*   31:     */   private OptFunctionNode fnCurrent;
/*   32:     */   private boolean isTopLevel;
/*   33:     */   private static final int MAX_LOCALS = 256;
/*   34:     */   private int[] locals;
/*   35:     */   private short firstFreeLocal;
/*   36:     */   private short localsMax;
/*   37:     */   private int itsLineNumber;
/*   38:     */   private boolean hasVarsInRegs;
/*   39:     */   private short[] varRegisters;
/*   40:     */   private boolean inDirectCallFunction;
/*   41:     */   private boolean itsForcedObjectParameters;
/*   42:     */   private int enterAreaStartLabel;
/*   43:     */   private int epilogueLabel;
/*   44:     */   private short variableObjectLocal;
/*   45:     */   private short popvLocal;
/*   46:     */   private short contextLocal;
/*   47:     */   private short argsLocal;
/*   48:     */   private short operationLocal;
/*   49:     */   private short thisObjLocal;
/*   50:     */   private short funObjLocal;
/*   51:     */   private short itsZeroArgArray;
/*   52:     */   private short itsOneArgArray;
/*   53:     */   private short scriptRegexpLocal;
/*   54:     */   private short generatorStateLocal;
/*   55:     */   private boolean isGenerator;
/*   56:     */   private int generatorSwitch;
/*   57:     */   private int maxLocals;
/*   58:     */   private int maxStack;
/*   59:     */   private Map<Node, FinallyReturnPoint> finallys;
/*   60:     */   
/*   61:     */   void generateBodyCode()
/*   62:     */   {
/*   63:1363 */     this.isGenerator = Codegen.isGenerator(this.scriptOrFn);
/*   64:     */     
/*   65:     */ 
/*   66:1366 */     initBodyGeneration();
/*   67:1368 */     if (this.isGenerator)
/*   68:     */     {
/*   69:1372 */       String type = "(" + this.codegen.mainClassSignature + "Lnet/sourceforge/htmlunit/corejs/javascript/Context;" + "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;" + "Ljava/lang/Object;" + "Ljava/lang/Object;I)Ljava/lang/Object;";
/*   70:     */       
/*   71:     */ 
/*   72:     */ 
/*   73:     */ 
/*   74:     */ 
/*   75:1378 */       this.cfw.startMethod(this.codegen.getBodyMethodName(this.scriptOrFn) + "_gen", type, (short)10);
/*   76:     */     }
/*   77:     */     else
/*   78:     */     {
/*   79:1383 */       this.cfw.startMethod(this.codegen.getBodyMethodName(this.scriptOrFn), this.codegen.getBodyMethodSignature(this.scriptOrFn), (short)10);
/*   80:     */     }
/*   81:1389 */     generatePrologue();
/*   82:     */     Node treeTop;
/*   83:     */     Node treeTop;
/*   84:1391 */     if (this.fnCurrent != null) {
/*   85:1392 */       treeTop = this.scriptOrFn.getLastChild();
/*   86:     */     } else {
/*   87:1394 */       treeTop = this.scriptOrFn;
/*   88:     */     }
/*   89:1396 */     generateStatement(treeTop);
/*   90:1397 */     generateEpilogue();
/*   91:     */     
/*   92:1399 */     this.cfw.stopMethod((short)(this.localsMax + 1));
/*   93:1401 */     if (this.isGenerator) {
/*   94:1404 */       generateGenerator();
/*   95:     */     }
/*   96:     */   }
/*   97:     */   
/*   98:     */   private void generateGenerator()
/*   99:     */   {
/*  100:1412 */     this.cfw.startMethod(this.codegen.getBodyMethodName(this.scriptOrFn), this.codegen.getBodyMethodSignature(this.scriptOrFn), (short)10);
/*  101:     */     
/*  102:     */ 
/*  103:     */ 
/*  104:     */ 
/*  105:1417 */     initBodyGeneration();
/*  106:1418 */     this.argsLocal = (this.firstFreeLocal++);
/*  107:1419 */     this.localsMax = this.firstFreeLocal;
/*  108:1422 */     if ((this.fnCurrent != null) && (!this.inDirectCallFunction) && ((!this.compilerEnv.isUseDynamicScope()) || (this.fnCurrent.fnode.getIgnoreDynamicScope())))
/*  109:     */     {
/*  110:1428 */       this.cfw.addALoad(this.funObjLocal);
/*  111:1429 */       this.cfw.addInvoke(185, "net/sourceforge/htmlunit/corejs/javascript/Scriptable", "getParentScope", "()Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  112:     */       
/*  113:     */ 
/*  114:     */ 
/*  115:1433 */       this.cfw.addAStore(this.variableObjectLocal);
/*  116:     */     }
/*  117:1437 */     this.cfw.addALoad(this.funObjLocal);
/*  118:1438 */     this.cfw.addALoad(this.variableObjectLocal);
/*  119:1439 */     this.cfw.addALoad(this.argsLocal);
/*  120:1440 */     addScriptRuntimeInvoke("createFunctionActivation", "(Lnet/sourceforge/htmlunit/corejs/javascript/NativeFunction;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  121:     */     
/*  122:     */ 
/*  123:     */ 
/*  124:     */ 
/*  125:1445 */     this.cfw.addAStore(this.variableObjectLocal);
/*  126:     */     
/*  127:     */ 
/*  128:1448 */     this.cfw.add(187, this.codegen.mainClassName);
/*  129:     */     
/*  130:1450 */     this.cfw.add(89);
/*  131:1451 */     this.cfw.addALoad(this.variableObjectLocal);
/*  132:1452 */     this.cfw.addALoad(this.contextLocal);
/*  133:1453 */     this.cfw.addPush(this.scriptOrFnIndex);
/*  134:1454 */     this.cfw.addInvoke(183, this.codegen.mainClassName, "<init>", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)V");
/*  135:     */     
/*  136:     */ 
/*  137:     */ 
/*  138:1458 */     this.cfw.add(89);
/*  139:1459 */     if (this.isTopLevel) {
/*  140:1459 */       Kit.codeBug();
/*  141:     */     }
/*  142:1460 */     this.cfw.add(42);
/*  143:1461 */     this.cfw.add(180, this.codegen.mainClassName, "_dcp", this.codegen.mainClassSignature);
/*  144:     */     
/*  145:     */ 
/*  146:     */ 
/*  147:1465 */     this.cfw.add(181, this.codegen.mainClassName, "_dcp", this.codegen.mainClassSignature);
/*  148:     */     
/*  149:     */ 
/*  150:     */ 
/*  151:     */ 
/*  152:1470 */     generateNestedFunctionInits();
/*  153:     */     
/*  154:     */ 
/*  155:1473 */     this.cfw.addALoad(this.variableObjectLocal);
/*  156:1474 */     this.cfw.addALoad(this.thisObjLocal);
/*  157:1475 */     this.cfw.addLoadConstant(this.maxLocals);
/*  158:1476 */     this.cfw.addLoadConstant(this.maxStack);
/*  159:1477 */     addOptRuntimeInvoke("createNativeGenerator", "(Lnet/sourceforge/htmlunit/corejs/javascript/NativeFunction;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;II)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  160:     */     
/*  161:     */ 
/*  162:     */ 
/*  163:     */ 
/*  164:     */ 
/*  165:1483 */     this.cfw.add(176);
/*  166:1484 */     this.cfw.stopMethod((short)(this.localsMax + 1));
/*  167:     */   }
/*  168:     */   
/*  169:     */   private void generateNestedFunctionInits()
/*  170:     */   {
/*  171:1489 */     int functionCount = this.scriptOrFn.getFunctionCount();
/*  172:1490 */     for (int i = 0; i != functionCount; i++)
/*  173:     */     {
/*  174:1491 */       OptFunctionNode ofn = OptFunctionNode.get(this.scriptOrFn, i);
/*  175:1492 */       if (ofn.fnode.getFunctionType() == 1) {
/*  176:1495 */         visitFunction(ofn, 1);
/*  177:     */       }
/*  178:     */     }
/*  179:     */   }
/*  180:     */   
/*  181:     */   private void initBodyGeneration()
/*  182:     */   {
/*  183:1502 */     this.isTopLevel = (this.scriptOrFn == this.codegen.scriptOrFnNodes[0]);
/*  184:     */     
/*  185:1504 */     this.varRegisters = null;
/*  186:1505 */     if (this.scriptOrFn.getType() == 109)
/*  187:     */     {
/*  188:1506 */       this.fnCurrent = OptFunctionNode.get(this.scriptOrFn);
/*  189:1507 */       this.hasVarsInRegs = (!this.fnCurrent.fnode.requiresActivation());
/*  190:1508 */       if (this.hasVarsInRegs)
/*  191:     */       {
/*  192:1509 */         int n = this.fnCurrent.fnode.getParamAndVarCount();
/*  193:1510 */         if (n != 0) {
/*  194:1511 */           this.varRegisters = new short[n];
/*  195:     */         }
/*  196:     */       }
/*  197:1514 */       this.inDirectCallFunction = this.fnCurrent.isTargetOfDirectCall();
/*  198:1515 */       if ((this.inDirectCallFunction) && (!this.hasVarsInRegs)) {
/*  199:1515 */         Codegen.badTree();
/*  200:     */       }
/*  201:     */     }
/*  202:     */     else
/*  203:     */     {
/*  204:1517 */       this.fnCurrent = null;
/*  205:1518 */       this.hasVarsInRegs = false;
/*  206:1519 */       this.inDirectCallFunction = false;
/*  207:     */     }
/*  208:1522 */     this.locals = new int[256];
/*  209:     */     
/*  210:1524 */     this.funObjLocal = 0;
/*  211:1525 */     this.contextLocal = 1;
/*  212:1526 */     this.variableObjectLocal = 2;
/*  213:1527 */     this.thisObjLocal = 3;
/*  214:1528 */     this.localsMax = 4;
/*  215:1529 */     this.firstFreeLocal = 4;
/*  216:     */     
/*  217:1531 */     this.popvLocal = -1;
/*  218:1532 */     this.argsLocal = -1;
/*  219:1533 */     this.itsZeroArgArray = -1;
/*  220:1534 */     this.itsOneArgArray = -1;
/*  221:1535 */     this.scriptRegexpLocal = -1;
/*  222:1536 */     this.epilogueLabel = -1;
/*  223:1537 */     this.enterAreaStartLabel = -1;
/*  224:1538 */     this.generatorStateLocal = -1;
/*  225:     */   }
/*  226:     */   
/*  227:     */   private void generatePrologue()
/*  228:     */   {
/*  229:1546 */     if (this.inDirectCallFunction)
/*  230:     */     {
/*  231:1547 */       int directParameterCount = this.scriptOrFn.getParamCount();
/*  232:1552 */       if (this.firstFreeLocal != 4) {
/*  233:1552 */         Kit.codeBug();
/*  234:     */       }
/*  235:1553 */       for (int i = 0; i != directParameterCount; i++)
/*  236:     */       {
/*  237:1554 */         this.varRegisters[i] = this.firstFreeLocal;
/*  238:     */         
/*  239:1556 */         this.firstFreeLocal = ((short)(this.firstFreeLocal + 3));
/*  240:     */       }
/*  241:1558 */       if (!this.fnCurrent.getParameterNumberContext())
/*  242:     */       {
/*  243:1560 */         this.itsForcedObjectParameters = true;
/*  244:1561 */         for (int i = 0; i != directParameterCount; i++)
/*  245:     */         {
/*  246:1562 */           short reg = this.varRegisters[i];
/*  247:1563 */           this.cfw.addALoad(reg);
/*  248:1564 */           this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/*  249:     */           
/*  250:     */ 
/*  251:     */ 
/*  252:1568 */           int isObjectLabel = this.cfw.acquireLabel();
/*  253:1569 */           this.cfw.add(166, isObjectLabel);
/*  254:1570 */           this.cfw.addDLoad(reg + 1);
/*  255:1571 */           addDoubleWrap();
/*  256:1572 */           this.cfw.addAStore(reg);
/*  257:1573 */           this.cfw.markLabel(isObjectLabel);
/*  258:     */         }
/*  259:     */       }
/*  260:     */     }
/*  261:1578 */     if ((this.fnCurrent != null) && (!this.inDirectCallFunction) && ((!this.compilerEnv.isUseDynamicScope()) || (this.fnCurrent.fnode.getIgnoreDynamicScope())))
/*  262:     */     {
/*  263:1584 */       this.cfw.addALoad(this.funObjLocal);
/*  264:1585 */       this.cfw.addInvoke(185, "net/sourceforge/htmlunit/corejs/javascript/Scriptable", "getParentScope", "()Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  265:     */       
/*  266:     */ 
/*  267:     */ 
/*  268:1589 */       this.cfw.addAStore(this.variableObjectLocal);
/*  269:     */     }
/*  270:1593 */     this.argsLocal = (this.firstFreeLocal++);
/*  271:1594 */     this.localsMax = this.firstFreeLocal;
/*  272:1597 */     if (this.isGenerator)
/*  273:     */     {
/*  274:1600 */       this.operationLocal = (this.firstFreeLocal++);
/*  275:1601 */       this.localsMax = this.firstFreeLocal;
/*  276:     */       
/*  277:     */ 
/*  278:     */ 
/*  279:     */ 
/*  280:     */ 
/*  281:1607 */       this.cfw.addALoad(this.thisObjLocal);
/*  282:1608 */       this.generatorStateLocal = (this.firstFreeLocal++);
/*  283:1609 */       this.localsMax = this.firstFreeLocal;
/*  284:1610 */       this.cfw.add(192, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime$GeneratorState");
/*  285:1611 */       this.cfw.add(89);
/*  286:1612 */       this.cfw.addAStore(this.generatorStateLocal);
/*  287:1613 */       this.cfw.add(180, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime$GeneratorState", "thisObj", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  288:     */       
/*  289:     */ 
/*  290:     */ 
/*  291:1617 */       this.cfw.addAStore(this.thisObjLocal);
/*  292:1619 */       if (this.epilogueLabel == -1) {
/*  293:1620 */         this.epilogueLabel = this.cfw.acquireLabel();
/*  294:     */       }
/*  295:1623 */       List<Node> targets = ((FunctionNode)this.scriptOrFn).getResumptionPoints();
/*  296:1624 */       if (targets != null)
/*  297:     */       {
/*  298:1626 */         generateGetGeneratorResumptionPoint();
/*  299:     */         
/*  300:     */ 
/*  301:1629 */         this.generatorSwitch = this.cfw.addTableSwitch(0, targets.size() + 0);
/*  302:     */         
/*  303:1631 */         generateCheckForThrowOrClose(-1, false, 0);
/*  304:     */       }
/*  305:     */     }
/*  306:1635 */     if (this.fnCurrent == null) {
/*  307:1637 */       if (this.scriptOrFn.getRegexpCount() != 0)
/*  308:     */       {
/*  309:1638 */         this.scriptRegexpLocal = getNewWordLocal();
/*  310:1639 */         this.codegen.pushRegExpArray(this.cfw, this.scriptOrFn, this.contextLocal, this.variableObjectLocal);
/*  311:     */         
/*  312:1641 */         this.cfw.addAStore(this.scriptRegexpLocal);
/*  313:     */       }
/*  314:     */     }
/*  315:1645 */     if (this.compilerEnv.isGenerateObserverCount()) {
/*  316:1646 */       saveCurrentCodeOffset();
/*  317:     */     }
/*  318:1648 */     if (this.hasVarsInRegs)
/*  319:     */     {
/*  320:1650 */       int parmCount = this.scriptOrFn.getParamCount();
/*  321:1651 */       if ((parmCount > 0) && (!this.inDirectCallFunction))
/*  322:     */       {
/*  323:1654 */         this.cfw.addALoad(this.argsLocal);
/*  324:1655 */         this.cfw.add(190);
/*  325:1656 */         this.cfw.addPush(parmCount);
/*  326:1657 */         int label = this.cfw.acquireLabel();
/*  327:1658 */         this.cfw.add(162, label);
/*  328:1659 */         this.cfw.addALoad(this.argsLocal);
/*  329:1660 */         this.cfw.addPush(parmCount);
/*  330:1661 */         addScriptRuntimeInvoke("padArguments", "([Ljava/lang/Object;I)[Ljava/lang/Object;");
/*  331:     */         
/*  332:     */ 
/*  333:1664 */         this.cfw.addAStore(this.argsLocal);
/*  334:1665 */         this.cfw.markLabel(label);
/*  335:     */       }
/*  336:1668 */       int paramCount = this.fnCurrent.fnode.getParamCount();
/*  337:1669 */       int varCount = this.fnCurrent.fnode.getParamAndVarCount();
/*  338:1670 */       boolean[] constDeclarations = this.fnCurrent.fnode.getParamAndVarConst();
/*  339:     */       
/*  340:     */ 
/*  341:     */ 
/*  342:1674 */       short firstUndefVar = -1;
/*  343:1675 */       for (int i = 0; i != varCount; i++)
/*  344:     */       {
/*  345:1676 */         short reg = -1;
/*  346:1677 */         if (i < paramCount)
/*  347:     */         {
/*  348:1678 */           if (!this.inDirectCallFunction)
/*  349:     */           {
/*  350:1679 */             reg = getNewWordLocal();
/*  351:1680 */             this.cfw.addALoad(this.argsLocal);
/*  352:1681 */             this.cfw.addPush(i);
/*  353:1682 */             this.cfw.add(50);
/*  354:1683 */             this.cfw.addAStore(reg);
/*  355:     */           }
/*  356:     */         }
/*  357:1685 */         else if (this.fnCurrent.isNumberVar(i))
/*  358:     */         {
/*  359:1686 */           reg = getNewWordPairLocal(constDeclarations[i]);
/*  360:1687 */           this.cfw.addPush(0.0D);
/*  361:1688 */           this.cfw.addDStore(reg);
/*  362:     */         }
/*  363:     */         else
/*  364:     */         {
/*  365:1690 */           reg = getNewWordLocal(constDeclarations[i]);
/*  366:1691 */           if (firstUndefVar == -1)
/*  367:     */           {
/*  368:1692 */             Codegen.pushUndefined(this.cfw);
/*  369:1693 */             firstUndefVar = reg;
/*  370:     */           }
/*  371:     */           else
/*  372:     */           {
/*  373:1695 */             this.cfw.addALoad(firstUndefVar);
/*  374:     */           }
/*  375:1697 */           this.cfw.addAStore(reg);
/*  376:     */         }
/*  377:1699 */         if (reg >= 0)
/*  378:     */         {
/*  379:1700 */           if (constDeclarations[i] != 0)
/*  380:     */           {
/*  381:1701 */             this.cfw.addPush(0);
/*  382:1702 */             this.cfw.addIStore(reg + (this.fnCurrent.isNumberVar(i) ? 2 : 1));
/*  383:     */           }
/*  384:1704 */           this.varRegisters[i] = reg;
/*  385:     */         }
/*  386:1708 */         if (this.compilerEnv.isGenerateDebugInfo())
/*  387:     */         {
/*  388:1709 */           String name = this.fnCurrent.fnode.getParamOrVarName(i);
/*  389:1710 */           String type = this.fnCurrent.isNumberVar(i) ? "D" : "Ljava/lang/Object;";
/*  390:     */           
/*  391:1712 */           int startPC = this.cfw.getCurrentCodeOffset();
/*  392:1713 */           if (reg < 0) {
/*  393:1714 */             reg = this.varRegisters[i];
/*  394:     */           }
/*  395:1716 */           this.cfw.addVariableDescriptor(name, type, startPC, reg);
/*  396:     */         }
/*  397:     */       }
/*  398:1721 */       return;
/*  399:     */     }
/*  400:1727 */     if (this.isGenerator) {
/*  401:     */       return;
/*  402:     */     }
/*  403:     */     String debugVariableName;
/*  404:1732 */     if (this.fnCurrent != null)
/*  405:     */     {
/*  406:1733 */       String debugVariableName = "activation";
/*  407:1734 */       this.cfw.addALoad(this.funObjLocal);
/*  408:1735 */       this.cfw.addALoad(this.variableObjectLocal);
/*  409:1736 */       this.cfw.addALoad(this.argsLocal);
/*  410:1737 */       addScriptRuntimeInvoke("createFunctionActivation", "(Lnet/sourceforge/htmlunit/corejs/javascript/NativeFunction;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  411:     */       
/*  412:     */ 
/*  413:     */ 
/*  414:     */ 
/*  415:1742 */       this.cfw.addAStore(this.variableObjectLocal);
/*  416:1743 */       this.cfw.addALoad(this.contextLocal);
/*  417:1744 */       this.cfw.addALoad(this.variableObjectLocal);
/*  418:1745 */       addScriptRuntimeInvoke("enterActivationFunction", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)V");
/*  419:     */     }
/*  420:     */     else
/*  421:     */     {
/*  422:1750 */       debugVariableName = "global";
/*  423:1751 */       this.cfw.addALoad(this.funObjLocal);
/*  424:1752 */       this.cfw.addALoad(this.thisObjLocal);
/*  425:1753 */       this.cfw.addALoad(this.contextLocal);
/*  426:1754 */       this.cfw.addALoad(this.variableObjectLocal);
/*  427:1755 */       this.cfw.addPush(0);
/*  428:1756 */       addScriptRuntimeInvoke("initScript", "(Lnet/sourceforge/htmlunit/corejs/javascript/NativeFunction;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Z)V");
/*  429:     */     }
/*  430:1765 */     this.enterAreaStartLabel = this.cfw.acquireLabel();
/*  431:1766 */     this.epilogueLabel = this.cfw.acquireLabel();
/*  432:1767 */     this.cfw.markLabel(this.enterAreaStartLabel);
/*  433:     */     
/*  434:1769 */     generateNestedFunctionInits();
/*  435:1772 */     if (this.compilerEnv.isGenerateDebugInfo()) {
/*  436:1773 */       this.cfw.addVariableDescriptor(debugVariableName, "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;", this.cfw.getCurrentCodeOffset(), this.variableObjectLocal);
/*  437:     */     }
/*  438:1778 */     if (this.fnCurrent == null)
/*  439:     */     {
/*  440:1780 */       this.popvLocal = getNewWordLocal();
/*  441:1781 */       Codegen.pushUndefined(this.cfw);
/*  442:1782 */       this.cfw.addAStore(this.popvLocal);
/*  443:     */       
/*  444:1784 */       int linenum = this.scriptOrFn.getEndLineno();
/*  445:1785 */       if (linenum != -1) {
/*  446:1786 */         this.cfw.addLineNumberEntry((short)linenum);
/*  447:     */       }
/*  448:     */     }
/*  449:     */     else
/*  450:     */     {
/*  451:1789 */       if (this.fnCurrent.itsContainsCalls0)
/*  452:     */       {
/*  453:1790 */         this.itsZeroArgArray = getNewWordLocal();
/*  454:1791 */         this.cfw.add(178, "net/sourceforge/htmlunit/corejs/javascript/ScriptRuntime", "emptyArgs", "[Ljava/lang/Object;");
/*  455:     */         
/*  456:     */ 
/*  457:1794 */         this.cfw.addAStore(this.itsZeroArgArray);
/*  458:     */       }
/*  459:1796 */       if (this.fnCurrent.itsContainsCalls1)
/*  460:     */       {
/*  461:1797 */         this.itsOneArgArray = getNewWordLocal();
/*  462:1798 */         this.cfw.addPush(1);
/*  463:1799 */         this.cfw.add(189, "java/lang/Object");
/*  464:1800 */         this.cfw.addAStore(this.itsOneArgArray);
/*  465:     */       }
/*  466:     */     }
/*  467:     */   }
/*  468:     */   
/*  469:     */   private void generateGetGeneratorResumptionPoint()
/*  470:     */   {
/*  471:1807 */     this.cfw.addALoad(this.generatorStateLocal);
/*  472:1808 */     this.cfw.add(180, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime$GeneratorState", "resumptionPoint", "I");
/*  473:     */   }
/*  474:     */   
/*  475:     */   private void generateSetGeneratorResumptionPoint(int nextState)
/*  476:     */   {
/*  477:1816 */     this.cfw.addALoad(this.generatorStateLocal);
/*  478:1817 */     this.cfw.addLoadConstant(nextState);
/*  479:1818 */     this.cfw.add(181, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime$GeneratorState", "resumptionPoint", "I");
/*  480:     */   }
/*  481:     */   
/*  482:     */   private void generateGetGeneratorStackState()
/*  483:     */   {
/*  484:1826 */     this.cfw.addALoad(this.generatorStateLocal);
/*  485:1827 */     addOptRuntimeInvoke("getGeneratorStackState", "(Ljava/lang/Object;)[Ljava/lang/Object;");
/*  486:     */   }
/*  487:     */   
/*  488:     */   private void generateEpilogue()
/*  489:     */   {
/*  490:1833 */     if (this.compilerEnv.isGenerateObserverCount()) {
/*  491:1834 */       addInstructionCount();
/*  492:     */     }
/*  493:1835 */     if (this.isGenerator)
/*  494:     */     {
/*  495:1837 */       Map<Node, int[]> liveLocals = ((FunctionNode)this.scriptOrFn).getLiveLocals();
/*  496:1838 */       if (liveLocals != null)
/*  497:     */       {
/*  498:1839 */         List<Node> nodes = ((FunctionNode)this.scriptOrFn).getResumptionPoints();
/*  499:1840 */         for (int i = 0; i < nodes.size(); i++)
/*  500:     */         {
/*  501:1841 */           Node node = (Node)nodes.get(i);
/*  502:1842 */           int[] live = (int[])liveLocals.get(node);
/*  503:1843 */           if (live != null)
/*  504:     */           {
/*  505:1844 */             this.cfw.markTableSwitchCase(this.generatorSwitch, getNextGeneratorState(node));
/*  506:     */             
/*  507:1846 */             generateGetGeneratorLocalsState();
/*  508:1847 */             for (int j = 0; j < live.length; j++)
/*  509:     */             {
/*  510:1848 */               this.cfw.add(89);
/*  511:1849 */               this.cfw.addLoadConstant(j);
/*  512:1850 */               this.cfw.add(50);
/*  513:1851 */               this.cfw.addAStore(live[j]);
/*  514:     */             }
/*  515:1853 */             this.cfw.add(87);
/*  516:1854 */             this.cfw.add(167, getTargetLabel(node));
/*  517:     */           }
/*  518:     */         }
/*  519:     */       }
/*  520:1860 */       if (this.finallys != null) {
/*  521:1861 */         for (Node n : this.finallys.keySet()) {
/*  522:1862 */           if (n.getType() == 125)
/*  523:     */           {
/*  524:1863 */             FinallyReturnPoint ret = (FinallyReturnPoint)this.finallys.get(n);
/*  525:     */             
/*  526:1865 */             this.cfw.markLabel(ret.tableLabel, (short)1);
/*  527:     */             
/*  528:     */ 
/*  529:1868 */             int startSwitch = this.cfw.addTableSwitch(0, ret.jsrPoints.size() - 1);
/*  530:     */             
/*  531:1870 */             int c = 0;
/*  532:1871 */             this.cfw.markTableSwitchDefault(startSwitch);
/*  533:1872 */             for (int i = 0; i < ret.jsrPoints.size(); i++)
/*  534:     */             {
/*  535:1874 */               this.cfw.markTableSwitchCase(startSwitch, c);
/*  536:1875 */               this.cfw.add(167, ((Integer)ret.jsrPoints.get(i)).intValue());
/*  537:     */               
/*  538:1877 */               c++;
/*  539:     */             }
/*  540:     */           }
/*  541:     */         }
/*  542:     */       }
/*  543:     */     }
/*  544:1884 */     if (this.epilogueLabel != -1) {
/*  545:1885 */       this.cfw.markLabel(this.epilogueLabel);
/*  546:     */     }
/*  547:1888 */     if (this.hasVarsInRegs)
/*  548:     */     {
/*  549:1889 */       this.cfw.add(176);
/*  550:1890 */       return;
/*  551:     */     }
/*  552:1891 */     if (this.isGenerator)
/*  553:     */     {
/*  554:1892 */       if (((FunctionNode)this.scriptOrFn).getResumptionPoints() != null) {
/*  555:1893 */         this.cfw.markTableSwitchDefault(this.generatorSwitch);
/*  556:     */       }
/*  557:1897 */       generateSetGeneratorResumptionPoint(-1);
/*  558:     */       
/*  559:     */ 
/*  560:1900 */       this.cfw.addALoad(this.variableObjectLocal);
/*  561:1901 */       addOptRuntimeInvoke("throwStopIteration", "(Ljava/lang/Object;)V");
/*  562:     */       
/*  563:     */ 
/*  564:1904 */       Codegen.pushUndefined(this.cfw);
/*  565:1905 */       this.cfw.add(176);
/*  566:     */     }
/*  567:1907 */     else if (this.fnCurrent == null)
/*  568:     */     {
/*  569:1908 */       this.cfw.addALoad(this.popvLocal);
/*  570:1909 */       this.cfw.add(176);
/*  571:     */     }
/*  572:     */     else
/*  573:     */     {
/*  574:1911 */       generateActivationExit();
/*  575:1912 */       this.cfw.add(176);
/*  576:     */       
/*  577:     */ 
/*  578:     */ 
/*  579:     */ 
/*  580:1917 */       int finallyHandler = this.cfw.acquireLabel();
/*  581:1918 */       this.cfw.markHandler(finallyHandler);
/*  582:1919 */       short exceptionObject = getNewWordLocal();
/*  583:1920 */       this.cfw.addAStore(exceptionObject);
/*  584:     */       
/*  585:     */ 
/*  586:     */ 
/*  587:1924 */       generateActivationExit();
/*  588:     */       
/*  589:1926 */       this.cfw.addALoad(exceptionObject);
/*  590:1927 */       releaseWordLocal(exceptionObject);
/*  591:     */       
/*  592:1929 */       this.cfw.add(191);
/*  593:     */       
/*  594:     */ 
/*  595:1932 */       this.cfw.addExceptionHandler(this.enterAreaStartLabel, this.epilogueLabel, finallyHandler, null);
/*  596:     */     }
/*  597:     */   }
/*  598:     */   
/*  599:     */   private void generateGetGeneratorLocalsState()
/*  600:     */   {
/*  601:1938 */     this.cfw.addALoad(this.generatorStateLocal);
/*  602:1939 */     addOptRuntimeInvoke("getGeneratorLocalsState", "(Ljava/lang/Object;)[Ljava/lang/Object;");
/*  603:     */   }
/*  604:     */   
/*  605:     */   private void generateActivationExit()
/*  606:     */   {
/*  607:1945 */     if ((this.fnCurrent == null) || (this.hasVarsInRegs)) {
/*  608:1945 */       throw Kit.codeBug();
/*  609:     */     }
/*  610:1946 */     this.cfw.addALoad(this.contextLocal);
/*  611:1947 */     addScriptRuntimeInvoke("exitActivationFunction", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;)V");
/*  612:     */   }
/*  613:     */   
/*  614:     */   private void generateStatement(Node node)
/*  615:     */   {
/*  616:1953 */     updateLineNumber(node);
/*  617:1954 */     int type = node.getType();
/*  618:1955 */     Node child = node.getFirstChild();
/*  619:1956 */     switch (type)
/*  620:     */     {
/*  621:     */     case 123: 
/*  622:     */     case 128: 
/*  623:     */     case 129: 
/*  624:     */     case 130: 
/*  625:     */     case 132: 
/*  626:     */     case 136: 
/*  627:1964 */       if (this.compilerEnv.isGenerateObserverCount()) {
/*  628:1967 */         addInstructionCount(1);
/*  629:     */       }
/*  630:     */       break;
/*  631:     */     }
/*  632:1969 */     while (child != null)
/*  633:     */     {
/*  634:1970 */       generateStatement(child);
/*  635:1971 */       child = child.getNext(); continue;
/*  636:     */       
/*  637:     */ 
/*  638:     */ 
/*  639:     */ 
/*  640:1976 */       int local = getNewWordLocal();
/*  641:1977 */       if (this.isGenerator)
/*  642:     */       {
/*  643:1978 */         this.cfw.add(1);
/*  644:1979 */         this.cfw.addAStore(local);
/*  645:     */       }
/*  646:1981 */       node.putIntProp(2, local);
/*  647:1982 */       while (child != null)
/*  648:     */       {
/*  649:1983 */         generateStatement(child);
/*  650:1984 */         child = child.getNext();
/*  651:     */       }
/*  652:1986 */       releaseWordLocal((short)local);
/*  653:1987 */       node.removeProp(2);
/*  654:1988 */       break;
/*  655:     */       
/*  656:     */ 
/*  657:     */ 
/*  658:1992 */       int fnIndex = node.getExistingIntProp(1);
/*  659:1993 */       OptFunctionNode ofn = OptFunctionNode.get(this.scriptOrFn, fnIndex);
/*  660:1994 */       int t = ofn.fnode.getFunctionType();
/*  661:1995 */       if (t == 3)
/*  662:     */       {
/*  663:1996 */         visitFunction(ofn, t);
/*  664:     */       }
/*  665:1998 */       else if (t != 1)
/*  666:     */       {
/*  667:1999 */         throw Codegen.badTree();
/*  668:     */         
/*  669:     */ 
/*  670:     */ 
/*  671:     */ 
/*  672:     */ 
/*  673:     */ 
/*  674:2006 */         visitTryCatchFinally((Jump)node, child);
/*  675:2007 */         break;
/*  676:     */         
/*  677:     */ 
/*  678:     */ 
/*  679:     */ 
/*  680:2012 */         this.cfw.setStackTop((short)0);
/*  681:     */         
/*  682:2014 */         int local = getLocalBlockRegister(node);
/*  683:2015 */         int scopeIndex = node.getExistingIntProp(14);
/*  684:     */         
/*  685:     */ 
/*  686:2018 */         String name = child.getString();
/*  687:2019 */         child = child.getNext();
/*  688:2020 */         generateExpression(child, node);
/*  689:2021 */         if (scopeIndex == 0) {
/*  690:2022 */           this.cfw.add(1);
/*  691:     */         } else {
/*  692:2025 */           this.cfw.addALoad(local);
/*  693:     */         }
/*  694:2027 */         this.cfw.addPush(name);
/*  695:2028 */         this.cfw.addALoad(this.contextLocal);
/*  696:2029 */         this.cfw.addALoad(this.variableObjectLocal);
/*  697:     */         
/*  698:2031 */         addScriptRuntimeInvoke("newCatchScope", "(Ljava/lang/Throwable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  699:     */         
/*  700:     */ 
/*  701:     */ 
/*  702:     */ 
/*  703:     */ 
/*  704:     */ 
/*  705:     */ 
/*  706:2039 */         this.cfw.addAStore(local);
/*  707:     */         
/*  708:2041 */         break;
/*  709:     */         
/*  710:     */ 
/*  711:2044 */         generateExpression(child, node);
/*  712:2045 */         if (this.compilerEnv.isGenerateObserverCount()) {
/*  713:2046 */           addInstructionCount();
/*  714:     */         }
/*  715:2047 */         generateThrowJavaScriptException();
/*  716:2048 */         break;
/*  717:2051 */         if (this.compilerEnv.isGenerateObserverCount()) {
/*  718:2052 */           addInstructionCount();
/*  719:     */         }
/*  720:2053 */         this.cfw.addALoad(getLocalBlockRegister(node));
/*  721:2054 */         this.cfw.add(191);
/*  722:2055 */         break;
/*  723:2059 */         if (!this.isGenerator) {
/*  724:2060 */           if (child != null)
/*  725:     */           {
/*  726:2061 */             generateExpression(child, node);
/*  727:     */           }
/*  728:2062 */           else if (type == 4)
/*  729:     */           {
/*  730:2063 */             Codegen.pushUndefined(this.cfw);
/*  731:     */           }
/*  732:     */           else
/*  733:     */           {
/*  734:2065 */             if (this.popvLocal < 0) {
/*  735:2065 */               throw Codegen.badTree();
/*  736:     */             }
/*  737:2066 */             this.cfw.addALoad(this.popvLocal);
/*  738:     */           }
/*  739:     */         }
/*  740:2069 */         if (this.compilerEnv.isGenerateObserverCount()) {
/*  741:2070 */           addInstructionCount();
/*  742:     */         }
/*  743:2071 */         if (this.epilogueLabel == -1)
/*  744:     */         {
/*  745:2072 */           if (!this.hasVarsInRegs) {
/*  746:2072 */             throw Codegen.badTree();
/*  747:     */           }
/*  748:2073 */           this.epilogueLabel = this.cfw.acquireLabel();
/*  749:     */         }
/*  750:2075 */         this.cfw.add(167, this.epilogueLabel);
/*  751:2076 */         break;
/*  752:2079 */         if (this.compilerEnv.isGenerateObserverCount()) {
/*  753:2080 */           addInstructionCount();
/*  754:     */         }
/*  755:2081 */         visitSwitch((Jump)node, child);
/*  756:2082 */         break;
/*  757:     */         
/*  758:     */ 
/*  759:2085 */         generateExpression(child, node);
/*  760:2086 */         this.cfw.addALoad(this.contextLocal);
/*  761:2087 */         this.cfw.addALoad(this.variableObjectLocal);
/*  762:2088 */         addScriptRuntimeInvoke("enterWith", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  763:     */         
/*  764:     */ 
/*  765:     */ 
/*  766:     */ 
/*  767:     */ 
/*  768:2094 */         this.cfw.addAStore(this.variableObjectLocal);
/*  769:2095 */         incReferenceWordLocal(this.variableObjectLocal);
/*  770:2096 */         break;
/*  771:     */         
/*  772:     */ 
/*  773:2099 */         this.cfw.addALoad(this.variableObjectLocal);
/*  774:2100 */         addScriptRuntimeInvoke("leaveWith", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  775:     */         
/*  776:     */ 
/*  777:     */ 
/*  778:2104 */         this.cfw.addAStore(this.variableObjectLocal);
/*  779:2105 */         decReferenceWordLocal(this.variableObjectLocal);
/*  780:2106 */         break;
/*  781:     */         
/*  782:     */ 
/*  783:     */ 
/*  784:     */ 
/*  785:2111 */         generateExpression(child, node);
/*  786:2112 */         this.cfw.addALoad(this.contextLocal);
/*  787:2113 */         int enumType = type == 59 ? 1 : type == 58 ? 0 : 2;
/*  788:     */         
/*  789:     */ 
/*  790:     */ 
/*  791:     */ 
/*  792:2118 */         this.cfw.addPush(enumType);
/*  793:2119 */         addScriptRuntimeInvoke("enumInit", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)Ljava/lang/Object;");
/*  794:     */         
/*  795:     */ 
/*  796:     */ 
/*  797:     */ 
/*  798:2124 */         this.cfw.addAStore(getLocalBlockRegister(node));
/*  799:2125 */         break;
/*  800:2128 */         if (child.getType() == 56)
/*  801:     */         {
/*  802:2131 */           visitSetVar(child, child.getFirstChild(), false);
/*  803:     */         }
/*  804:2133 */         else if (child.getType() == 156)
/*  805:     */         {
/*  806:2136 */           visitSetConstVar(child, child.getFirstChild(), false);
/*  807:     */         }
/*  808:2138 */         else if (child.getType() == 72)
/*  809:     */         {
/*  810:2139 */           generateYieldPoint(child, false);
/*  811:     */         }
/*  812:     */         else
/*  813:     */         {
/*  814:2142 */           generateExpression(child, node);
/*  815:2143 */           if (node.getIntProp(8, -1) != -1)
/*  816:     */           {
/*  817:2144 */             this.cfw.add(88);
/*  818:     */           }
/*  819:     */           else
/*  820:     */           {
/*  821:2146 */             this.cfw.add(87);
/*  822:     */             
/*  823:2148 */             break;
/*  824:     */             
/*  825:     */ 
/*  826:2151 */             generateExpression(child, node);
/*  827:2152 */             if (this.popvLocal < 0) {
/*  828:2153 */               this.popvLocal = getNewWordLocal();
/*  829:     */             }
/*  830:2155 */             this.cfw.addAStore(this.popvLocal);
/*  831:2156 */             break;
/*  832:2160 */             if (this.compilerEnv.isGenerateObserverCount()) {
/*  833:2161 */               addInstructionCount();
/*  834:     */             }
/*  835:2162 */             int label = getTargetLabel(node);
/*  836:2163 */             this.cfw.markLabel(label);
/*  837:2164 */             if (this.compilerEnv.isGenerateObserverCount()) {
/*  838:2165 */               saveCurrentCodeOffset();
/*  839:     */             }
/*  840:2167 */             break;
/*  841:2173 */             if (this.compilerEnv.isGenerateObserverCount()) {
/*  842:2174 */               addInstructionCount();
/*  843:     */             }
/*  844:2175 */             visitGoto((Jump)node, type, child);
/*  845:2176 */             break;
/*  846:2180 */             if (this.compilerEnv.isGenerateObserverCount()) {
/*  847:2181 */               saveCurrentCodeOffset();
/*  848:     */             }
/*  849:2184 */             this.cfw.setStackTop((short)1);
/*  850:     */             
/*  851:     */ 
/*  852:2187 */             int finallyRegister = getNewWordLocal();
/*  853:2188 */             if (this.isGenerator) {
/*  854:2189 */               generateIntegerWrap();
/*  855:     */             }
/*  856:2190 */             this.cfw.addAStore(finallyRegister);
/*  857:2192 */             while (child != null)
/*  858:     */             {
/*  859:2193 */               generateStatement(child);
/*  860:2194 */               child = child.getNext();
/*  861:     */             }
/*  862:2196 */             if (this.isGenerator)
/*  863:     */             {
/*  864:2197 */               this.cfw.addALoad(finallyRegister);
/*  865:2198 */               this.cfw.add(192, "java/lang/Integer");
/*  866:2199 */               generateIntegerUnwrap();
/*  867:2200 */               FinallyReturnPoint ret = (FinallyReturnPoint)this.finallys.get(node);
/*  868:2201 */               ret.tableLabel = this.cfw.acquireLabel();
/*  869:2202 */               this.cfw.add(167, ret.tableLabel);
/*  870:     */             }
/*  871:     */             else
/*  872:     */             {
/*  873:2204 */               this.cfw.add(169, finallyRegister);
/*  874:     */             }
/*  875:2206 */             releaseWordLocal((short)finallyRegister);
/*  876:     */             
/*  877:2208 */             break;
/*  878:     */             
/*  879:     */ 
/*  880:2211 */             break;
/*  881:     */             
/*  882:     */ 
/*  883:2214 */             throw Codegen.badTree();
/*  884:     */           }
/*  885:     */         }
/*  886:     */       }
/*  887:     */     }
/*  888:     */   }
/*  889:     */   
/*  890:     */   private void generateIntegerWrap()
/*  891:     */   {
/*  892:2221 */     this.cfw.addInvoke(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
/*  893:     */   }
/*  894:     */   
/*  895:     */   private void generateIntegerUnwrap()
/*  896:     */   {
/*  897:2228 */     this.cfw.addInvoke(182, "java/lang/Integer", "intValue", "()I");
/*  898:     */   }
/*  899:     */   
/*  900:     */   private void generateThrowJavaScriptException()
/*  901:     */   {
/*  902:2235 */     this.cfw.add(187, "net/sourceforge/htmlunit/corejs/javascript/JavaScriptException");
/*  903:     */     
/*  904:2237 */     this.cfw.add(90);
/*  905:2238 */     this.cfw.add(95);
/*  906:2239 */     this.cfw.addPush(this.scriptOrFn.getSourceName());
/*  907:2240 */     this.cfw.addPush(this.itsLineNumber);
/*  908:2241 */     this.cfw.addInvoke(183, "net/sourceforge/htmlunit/corejs/javascript/JavaScriptException", "<init>", "(Ljava/lang/Object;Ljava/lang/String;I)V");
/*  909:     */     
/*  910:     */ 
/*  911:     */ 
/*  912:     */ 
/*  913:2246 */     this.cfw.add(191);
/*  914:     */   }
/*  915:     */   
/*  916:     */   private int getNextGeneratorState(Node node)
/*  917:     */   {
/*  918:2251 */     int nodeIndex = ((FunctionNode)this.scriptOrFn).getResumptionPoints().indexOf(node);
/*  919:     */     
/*  920:2253 */     return nodeIndex + 1;
/*  921:     */   }
/*  922:     */   
/*  923:     */   private void generateExpression(Node node, Node parent)
/*  924:     */   {
/*  925:2258 */     int type = node.getType();
/*  926:2259 */     Node child = node.getFirstChild();
/*  927:2260 */     switch (type)
/*  928:     */     {
/*  929:     */     case 138: 
/*  930:     */       break;
/*  931:     */     case 109: 
/*  932:2265 */       if ((this.fnCurrent != null) || (parent.getType() != 136))
/*  933:     */       {
/*  934:2266 */         int fnIndex = node.getExistingIntProp(1);
/*  935:2267 */         OptFunctionNode ofn = OptFunctionNode.get(this.scriptOrFn, fnIndex);
/*  936:     */         
/*  937:2269 */         int t = ofn.fnode.getFunctionType();
/*  938:2270 */         if (t != 2) {
/*  939:2271 */           throw Codegen.badTree();
/*  940:     */         }
/*  941:2273 */         visitFunction(ofn, t);
/*  942:     */       }
/*  943:2274 */       break;
/*  944:     */     case 39: 
/*  945:2279 */       this.cfw.addALoad(this.contextLocal);
/*  946:2280 */       this.cfw.addALoad(this.variableObjectLocal);
/*  947:2281 */       this.cfw.addPush(node.getString());
/*  948:2282 */       addScriptRuntimeInvoke("name", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/Object;");
/*  949:     */       
/*  950:     */ 
/*  951:     */ 
/*  952:     */ 
/*  953:     */ 
/*  954:     */ 
/*  955:2289 */       break;
/*  956:     */     case 30: 
/*  957:     */     case 38: 
/*  958:2294 */       int specialType = node.getIntProp(10, 0);
/*  959:2296 */       if (specialType == 0)
/*  960:     */       {
/*  961:2298 */         OptFunctionNode target = (OptFunctionNode)node.getProp(9);
/*  962:2301 */         if (target != null) {
/*  963:2302 */           visitOptimizedCall(node, target, type, child);
/*  964:2303 */         } else if (type == 38) {
/*  965:2304 */           visitStandardCall(node, child);
/*  966:     */         } else {
/*  967:2306 */           visitStandardNew(node, child);
/*  968:     */         }
/*  969:     */       }
/*  970:     */       else
/*  971:     */       {
/*  972:2309 */         visitSpecialCall(node, type, specialType, child);
/*  973:     */       }
/*  974:2312 */       break;
/*  975:     */     case 70: 
/*  976:2315 */       generateFunctionAndThisObj(child, node);
/*  977:     */       
/*  978:2317 */       child = child.getNext();
/*  979:2318 */       generateCallArgArray(node, child, false);
/*  980:2319 */       this.cfw.addALoad(this.contextLocal);
/*  981:2320 */       addScriptRuntimeInvoke("callRef", "(Lnet/sourceforge/htmlunit/corejs/javascript/Callable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Lnet/sourceforge/htmlunit/corejs/javascript/Ref;");
/*  982:     */       
/*  983:     */ 
/*  984:     */ 
/*  985:     */ 
/*  986:     */ 
/*  987:     */ 
/*  988:2327 */       break;
/*  989:     */     case 40: 
/*  990:2331 */       double num = node.getDouble();
/*  991:2332 */       if (node.getIntProp(8, -1) != -1) {
/*  992:2333 */         this.cfw.addPush(num);
/*  993:     */       } else {
/*  994:2335 */         this.codegen.pushNumberAsObject(this.cfw, num);
/*  995:     */       }
/*  996:2338 */       break;
/*  997:     */     case 41: 
/*  998:2341 */       this.cfw.addPush(node.getString());
/*  999:2342 */       break;
/* 1000:     */     case 43: 
/* 1001:2345 */       this.cfw.addALoad(this.thisObjLocal);
/* 1002:2346 */       break;
/* 1003:     */     case 63: 
/* 1004:2349 */       this.cfw.add(42);
/* 1005:2350 */       break;
/* 1006:     */     case 42: 
/* 1007:2353 */       this.cfw.add(1);
/* 1008:2354 */       break;
/* 1009:     */     case 45: 
/* 1010:2357 */       this.cfw.add(178, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
/* 1011:     */       
/* 1012:2359 */       break;
/* 1013:     */     case 44: 
/* 1014:2362 */       this.cfw.add(178, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
/* 1015:     */       
/* 1016:2364 */       break;
/* 1017:     */     case 48: 
/* 1018:2368 */       int i = node.getExistingIntProp(4);
/* 1019:2374 */       if (this.fnCurrent == null)
/* 1020:     */       {
/* 1021:2375 */         this.cfw.addALoad(this.scriptRegexpLocal);
/* 1022:     */       }
/* 1023:     */       else
/* 1024:     */       {
/* 1025:2377 */         this.cfw.addALoad(this.funObjLocal);
/* 1026:2378 */         this.cfw.add(180, this.codegen.mainClassName, "_re", "[Ljava/lang/Object;");
/* 1027:     */       }
/* 1028:2382 */       this.cfw.addPush(i);
/* 1029:2383 */       this.cfw.add(50);
/* 1030:     */       
/* 1031:2385 */       break;
/* 1032:     */     case 89: 
/* 1033:2388 */       Node next = child.getNext();
/* 1034:2389 */       while (next != null)
/* 1035:     */       {
/* 1036:2390 */         generateExpression(child, node);
/* 1037:2391 */         this.cfw.add(87);
/* 1038:2392 */         child = next;
/* 1039:2393 */         next = next.getNext();
/* 1040:     */       }
/* 1041:2395 */       generateExpression(child, node);
/* 1042:2396 */       break;
/* 1043:     */     case 61: 
/* 1044:     */     case 62: 
/* 1045:2401 */       int local = getLocalBlockRegister(node);
/* 1046:2402 */       this.cfw.addALoad(local);
/* 1047:2403 */       if (type == 61)
/* 1048:     */       {
/* 1049:2404 */         addScriptRuntimeInvoke("enumNext", "(Ljava/lang/Object;)Ljava/lang/Boolean;");
/* 1050:     */       }
/* 1051:     */       else
/* 1052:     */       {
/* 1053:2407 */         this.cfw.addALoad(this.contextLocal);
/* 1054:2408 */         addScriptRuntimeInvoke("enumId", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1055:     */       }
/* 1056:2413 */       break;
/* 1057:     */     case 65: 
/* 1058:2417 */       visitArrayLiteral(node, child);
/* 1059:2418 */       break;
/* 1060:     */     case 66: 
/* 1061:2421 */       visitObjectLiteral(node, child);
/* 1062:2422 */       break;
/* 1063:     */     case 26: 
/* 1064:2425 */       int trueTarget = this.cfw.acquireLabel();
/* 1065:2426 */       int falseTarget = this.cfw.acquireLabel();
/* 1066:2427 */       int beyond = this.cfw.acquireLabel();
/* 1067:2428 */       generateIfJump(child, node, trueTarget, falseTarget);
/* 1068:     */       
/* 1069:2430 */       this.cfw.markLabel(trueTarget);
/* 1070:2431 */       this.cfw.add(178, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
/* 1071:     */       
/* 1072:2433 */       this.cfw.add(167, beyond);
/* 1073:2434 */       this.cfw.markLabel(falseTarget);
/* 1074:2435 */       this.cfw.add(178, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
/* 1075:     */       
/* 1076:2437 */       this.cfw.markLabel(beyond);
/* 1077:2438 */       this.cfw.adjustStackTop(-1);
/* 1078:2439 */       break;
/* 1079:     */     case 27: 
/* 1080:2443 */       generateExpression(child, node);
/* 1081:2444 */       addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
/* 1082:2445 */       this.cfw.addPush(-1);
/* 1083:2446 */       this.cfw.add(130);
/* 1084:2447 */       this.cfw.add(135);
/* 1085:2448 */       addDoubleWrap();
/* 1086:2449 */       break;
/* 1087:     */     case 126: 
/* 1088:2452 */       generateExpression(child, node);
/* 1089:2453 */       this.cfw.add(87);
/* 1090:2454 */       Codegen.pushUndefined(this.cfw);
/* 1091:2455 */       break;
/* 1092:     */     case 32: 
/* 1093:2458 */       generateExpression(child, node);
/* 1094:2459 */       addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)Ljava/lang/String;");
/* 1095:     */       
/* 1096:     */ 
/* 1097:2462 */       break;
/* 1098:     */     case 137: 
/* 1099:2465 */       visitTypeofname(node);
/* 1100:2466 */       break;
/* 1101:     */     case 106: 
/* 1102:     */     case 107: 
/* 1103:2470 */       visitIncDec(node);
/* 1104:2471 */       break;
/* 1105:     */     case 104: 
/* 1106:     */     case 105: 
/* 1107:2475 */       generateExpression(child, node);
/* 1108:2476 */       this.cfw.add(89);
/* 1109:2477 */       addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)Z");
/* 1110:     */       
/* 1111:2479 */       int falseTarget = this.cfw.acquireLabel();
/* 1112:2480 */       if (type == 105) {
/* 1113:2481 */         this.cfw.add(153, falseTarget);
/* 1114:     */       } else {
/* 1115:2483 */         this.cfw.add(154, falseTarget);
/* 1116:     */       }
/* 1117:2484 */       this.cfw.add(87);
/* 1118:2485 */       generateExpression(child.getNext(), node);
/* 1119:2486 */       this.cfw.markLabel(falseTarget);
/* 1120:     */       
/* 1121:2488 */       break;
/* 1122:     */     case 102: 
/* 1123:2491 */       Node ifThen = child.getNext();
/* 1124:2492 */       Node ifElse = ifThen.getNext();
/* 1125:2493 */       generateExpression(child, node);
/* 1126:2494 */       addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)Z");
/* 1127:     */       
/* 1128:2496 */       int elseTarget = this.cfw.acquireLabel();
/* 1129:2497 */       this.cfw.add(153, elseTarget);
/* 1130:2498 */       short stack = this.cfw.getStackTop();
/* 1131:2499 */       generateExpression(ifThen, node);
/* 1132:2500 */       int afterHook = this.cfw.acquireLabel();
/* 1133:2501 */       this.cfw.add(167, afterHook);
/* 1134:2502 */       this.cfw.markLabel(elseTarget, stack);
/* 1135:2503 */       generateExpression(ifElse, node);
/* 1136:2504 */       this.cfw.markLabel(afterHook);
/* 1137:     */       
/* 1138:2506 */       break;
/* 1139:     */     case 21: 
/* 1140:2509 */       generateExpression(child, node);
/* 1141:2510 */       generateExpression(child.getNext(), node);
/* 1142:2511 */       switch (node.getIntProp(8, -1))
/* 1143:     */       {
/* 1144:     */       case 0: 
/* 1145:2513 */         this.cfw.add(99);
/* 1146:2514 */         break;
/* 1147:     */       case 1: 
/* 1148:2516 */         addOptRuntimeInvoke("add", "(DLjava/lang/Object;)Ljava/lang/Object;");
/* 1149:     */         
/* 1150:2518 */         break;
/* 1151:     */       case 2: 
/* 1152:2520 */         addOptRuntimeInvoke("add", "(Ljava/lang/Object;D)Ljava/lang/Object;");
/* 1153:     */         
/* 1154:2522 */         break;
/* 1155:     */       default: 
/* 1156:2524 */         if (child.getType() == 41)
/* 1157:     */         {
/* 1158:2525 */           addScriptRuntimeInvoke("add", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
/* 1159:     */         }
/* 1160:2529 */         else if (child.getNext().getType() == 41)
/* 1161:     */         {
/* 1162:2530 */           addScriptRuntimeInvoke("add", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/String;");
/* 1163:     */         }
/* 1164:     */         else
/* 1165:     */         {
/* 1166:2535 */           this.cfw.addALoad(this.contextLocal);
/* 1167:2536 */           addScriptRuntimeInvoke("add", "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1168:     */         }
/* 1169:     */         break;
/* 1170:     */       }
/* 1171:2544 */       break;
/* 1172:     */     case 23: 
/* 1173:2547 */       visitArithmetic(node, 107, child, parent);
/* 1174:2548 */       break;
/* 1175:     */     case 22: 
/* 1176:2551 */       visitArithmetic(node, 103, child, parent);
/* 1177:2552 */       break;
/* 1178:     */     case 24: 
/* 1179:     */     case 25: 
/* 1180:2556 */       visitArithmetic(node, type == 24 ? 111 : 115, child, parent);
/* 1181:     */       
/* 1182:     */ 
/* 1183:2559 */       break;
/* 1184:     */     case 9: 
/* 1185:     */     case 10: 
/* 1186:     */     case 11: 
/* 1187:     */     case 18: 
/* 1188:     */     case 19: 
/* 1189:     */     case 20: 
/* 1190:2567 */       visitBitOp(node, type, child);
/* 1191:2568 */       break;
/* 1192:     */     case 28: 
/* 1193:     */     case 29: 
/* 1194:2572 */       generateExpression(child, node);
/* 1195:2573 */       addObjectToDouble();
/* 1196:2574 */       if (type == 29) {
/* 1197:2575 */         this.cfw.add(119);
/* 1198:     */       }
/* 1199:2577 */       addDoubleWrap();
/* 1200:2578 */       break;
/* 1201:     */     case 150: 
/* 1202:2582 */       generateExpression(child, node);
/* 1203:2583 */       addObjectToDouble();
/* 1204:2584 */       break;
/* 1205:     */     case 149: 
/* 1206:2588 */       int prop = -1;
/* 1207:2589 */       if (child.getType() == 40) {
/* 1208:2590 */         prop = child.getIntProp(8, -1);
/* 1209:     */       }
/* 1210:2592 */       if (prop != -1)
/* 1211:     */       {
/* 1212:2593 */         child.removeProp(8);
/* 1213:2594 */         generateExpression(child, node);
/* 1214:2595 */         child.putIntProp(8, prop);
/* 1215:     */       }
/* 1216:     */       else
/* 1217:     */       {
/* 1218:2597 */         generateExpression(child, node);
/* 1219:2598 */         addDoubleWrap();
/* 1220:     */       }
/* 1221:2600 */       break;
/* 1222:     */     case 14: 
/* 1223:     */     case 15: 
/* 1224:     */     case 16: 
/* 1225:     */     case 17: 
/* 1226:     */     case 52: 
/* 1227:     */     case 53: 
/* 1228:2609 */       int trueGOTO = this.cfw.acquireLabel();
/* 1229:2610 */       int falseGOTO = this.cfw.acquireLabel();
/* 1230:2611 */       visitIfJumpRelOp(node, child, trueGOTO, falseGOTO);
/* 1231:2612 */       addJumpedBooleanWrap(trueGOTO, falseGOTO);
/* 1232:2613 */       break;
/* 1233:     */     case 12: 
/* 1234:     */     case 13: 
/* 1235:     */     case 46: 
/* 1236:     */     case 47: 
/* 1237:2620 */       int trueGOTO = this.cfw.acquireLabel();
/* 1238:2621 */       int falseGOTO = this.cfw.acquireLabel();
/* 1239:2622 */       visitIfJumpEqOp(node, child, trueGOTO, falseGOTO);
/* 1240:2623 */       addJumpedBooleanWrap(trueGOTO, falseGOTO);
/* 1241:2624 */       break;
/* 1242:     */     case 33: 
/* 1243:     */     case 34: 
/* 1244:2629 */       visitGetProp(node, child);
/* 1245:2630 */       break;
/* 1246:     */     case 36: 
/* 1247:2633 */       generateExpression(child, node);
/* 1248:2634 */       generateExpression(child.getNext(), node);
/* 1249:2635 */       this.cfw.addALoad(this.contextLocal);
/* 1250:2636 */       if (node.getIntProp(8, -1) != -1)
/* 1251:     */       {
/* 1252:2637 */         addScriptRuntimeInvoke("getObjectIndex", "(Ljava/lang/Object;DLnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1253:     */       }
/* 1254:     */       else
/* 1255:     */       {
/* 1256:2644 */         this.cfw.addALoad(this.variableObjectLocal);
/* 1257:2645 */         addScriptRuntimeInvoke("getObjectElem", "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;");
/* 1258:     */       }
/* 1259:2653 */       break;
/* 1260:     */     case 67: 
/* 1261:2656 */       generateExpression(child, node);
/* 1262:2657 */       this.cfw.addALoad(this.contextLocal);
/* 1263:2658 */       addScriptRuntimeInvoke("refGet", "(Lnet/sourceforge/htmlunit/corejs/javascript/Ref;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1264:     */       
/* 1265:     */ 
/* 1266:     */ 
/* 1267:     */ 
/* 1268:2663 */       break;
/* 1269:     */     case 55: 
/* 1270:2666 */       visitGetVar(node);
/* 1271:2667 */       break;
/* 1272:     */     case 56: 
/* 1273:2670 */       visitSetVar(node, child, true);
/* 1274:2671 */       break;
/* 1275:     */     case 8: 
/* 1276:2674 */       visitSetName(node, child);
/* 1277:2675 */       break;
/* 1278:     */     case 73: 
/* 1279:2678 */       visitStrictSetName(node, child);
/* 1280:2679 */       break;
/* 1281:     */     case 155: 
/* 1282:2682 */       visitSetConst(node, child);
/* 1283:2683 */       break;
/* 1284:     */     case 156: 
/* 1285:2686 */       visitSetConstVar(node, child, true);
/* 1286:2687 */       break;
/* 1287:     */     case 35: 
/* 1288:     */     case 139: 
/* 1289:2691 */       visitSetProp(type, node, child);
/* 1290:2692 */       break;
/* 1291:     */     case 37: 
/* 1292:     */     case 140: 
/* 1293:2696 */       visitSetElem(type, node, child);
/* 1294:2697 */       break;
/* 1295:     */     case 68: 
/* 1296:     */     case 142: 
/* 1297:2702 */       generateExpression(child, node);
/* 1298:2703 */       child = child.getNext();
/* 1299:2704 */       if (type == 142)
/* 1300:     */       {
/* 1301:2705 */         this.cfw.add(89);
/* 1302:2706 */         this.cfw.addALoad(this.contextLocal);
/* 1303:2707 */         addScriptRuntimeInvoke("refGet", "(Lnet/sourceforge/htmlunit/corejs/javascript/Ref;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1304:     */       }
/* 1305:2713 */       generateExpression(child, node);
/* 1306:2714 */       this.cfw.addALoad(this.contextLocal);
/* 1307:2715 */       addScriptRuntimeInvoke("refSet", "(Lnet/sourceforge/htmlunit/corejs/javascript/Ref;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1308:     */       
/* 1309:     */ 
/* 1310:     */ 
/* 1311:     */ 
/* 1312:     */ 
/* 1313:     */ 
/* 1314:2722 */       break;
/* 1315:     */     case 69: 
/* 1316:2725 */       generateExpression(child, node);
/* 1317:2726 */       this.cfw.addALoad(this.contextLocal);
/* 1318:2727 */       addScriptRuntimeInvoke("refDel", "(Lnet/sourceforge/htmlunit/corejs/javascript/Ref;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1319:     */       
/* 1320:     */ 
/* 1321:     */ 
/* 1322:2731 */       break;
/* 1323:     */     case 31: 
/* 1324:2734 */       generateExpression(child, node);
/* 1325:2735 */       child = child.getNext();
/* 1326:2736 */       generateExpression(child, node);
/* 1327:2737 */       this.cfw.addALoad(this.contextLocal);
/* 1328:2738 */       addScriptRuntimeInvoke("delete", "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1329:     */       
/* 1330:     */ 
/* 1331:     */ 
/* 1332:     */ 
/* 1333:2743 */       break;
/* 1334:     */     case 49: 
/* 1335:2747 */       while (child != null)
/* 1336:     */       {
/* 1337:2748 */         generateExpression(child, node);
/* 1338:2749 */         child = child.getNext();
/* 1339:     */       }
/* 1340:2752 */       this.cfw.addALoad(this.contextLocal);
/* 1341:2753 */       this.cfw.addALoad(this.variableObjectLocal);
/* 1342:2754 */       this.cfw.addPush(node.getString());
/* 1343:2755 */       addScriptRuntimeInvoke("bind", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 1344:     */       
/* 1345:     */ 
/* 1346:     */ 
/* 1347:     */ 
/* 1348:     */ 
/* 1349:     */ 
/* 1350:2762 */       break;
/* 1351:     */     case 54: 
/* 1352:2765 */       this.cfw.addALoad(getLocalBlockRegister(node));
/* 1353:2766 */       break;
/* 1354:     */     case 71: 
/* 1355:2770 */       String special = (String)node.getProp(17);
/* 1356:2771 */       generateExpression(child, node);
/* 1357:2772 */       this.cfw.addPush(special);
/* 1358:2773 */       this.cfw.addALoad(this.contextLocal);
/* 1359:2774 */       addScriptRuntimeInvoke("specialRef", "(Ljava/lang/Object;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Lnet/sourceforge/htmlunit/corejs/javascript/Ref;");
/* 1360:     */       
/* 1361:     */ 
/* 1362:     */ 
/* 1363:     */ 
/* 1364:     */ 
/* 1365:     */ 
/* 1366:2781 */       break;
/* 1367:     */     case 77: 
/* 1368:     */     case 78: 
/* 1369:     */     case 79: 
/* 1370:     */     case 80: 
/* 1371:2788 */       int memberTypeFlags = node.getIntProp(16, 0);
/* 1372:     */       do
/* 1373:     */       {
/* 1374:2792 */         generateExpression(child, node);
/* 1375:2793 */         child = child.getNext();
/* 1376:2794 */       } while (child != null);
/* 1377:2795 */       this.cfw.addALoad(this.contextLocal);
/* 1378:     */       String methodName;
/* 1379:     */       String signature;
/* 1380:2797 */       switch (type)
/* 1381:     */       {
/* 1382:     */       case 77: 
/* 1383:2799 */         methodName = "memberRef";
/* 1384:2800 */         signature = "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)Lnet/sourceforge/htmlunit/corejs/javascript/Ref;";
/* 1385:     */         
/* 1386:     */ 
/* 1387:     */ 
/* 1388:     */ 
/* 1389:2805 */         break;
/* 1390:     */       case 78: 
/* 1391:2807 */         methodName = "memberRef";
/* 1392:2808 */         signature = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)Lnet/sourceforge/htmlunit/corejs/javascript/Ref;";
/* 1393:     */         
/* 1394:     */ 
/* 1395:     */ 
/* 1396:     */ 
/* 1397:     */ 
/* 1398:2814 */         break;
/* 1399:     */       case 79: 
/* 1400:2816 */         methodName = "nameRef";
/* 1401:2817 */         signature = "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;I)Lnet/sourceforge/htmlunit/corejs/javascript/Ref;";
/* 1402:     */         
/* 1403:     */ 
/* 1404:     */ 
/* 1405:     */ 
/* 1406:2822 */         this.cfw.addALoad(this.variableObjectLocal);
/* 1407:2823 */         break;
/* 1408:     */       case 80: 
/* 1409:2825 */         methodName = "nameRef";
/* 1410:2826 */         signature = "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;I)Lnet/sourceforge/htmlunit/corejs/javascript/Ref;";
/* 1411:     */         
/* 1412:     */ 
/* 1413:     */ 
/* 1414:     */ 
/* 1415:     */ 
/* 1416:2832 */         this.cfw.addALoad(this.variableObjectLocal);
/* 1417:2833 */         break;
/* 1418:     */       default: 
/* 1419:2835 */         throw Kit.codeBug();
/* 1420:     */       }
/* 1421:2837 */       this.cfw.addPush(memberTypeFlags);
/* 1422:2838 */       addScriptRuntimeInvoke(methodName, signature);
/* 1423:     */       
/* 1424:2840 */       break;
/* 1425:     */     case 146: 
/* 1426:2843 */       visitDotQuery(node, child);
/* 1427:2844 */       break;
/* 1428:     */     case 75: 
/* 1429:2847 */       generateExpression(child, node);
/* 1430:2848 */       this.cfw.addALoad(this.contextLocal);
/* 1431:2849 */       addScriptRuntimeInvoke("escapeAttributeValue", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/String;");
/* 1432:     */       
/* 1433:     */ 
/* 1434:     */ 
/* 1435:2853 */       break;
/* 1436:     */     case 76: 
/* 1437:2856 */       generateExpression(child, node);
/* 1438:2857 */       this.cfw.addALoad(this.contextLocal);
/* 1439:2858 */       addScriptRuntimeInvoke("escapeTextValue", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/String;");
/* 1440:     */       
/* 1441:     */ 
/* 1442:     */ 
/* 1443:2862 */       break;
/* 1444:     */     case 74: 
/* 1445:2865 */       generateExpression(child, node);
/* 1446:2866 */       this.cfw.addALoad(this.contextLocal);
/* 1447:2867 */       addScriptRuntimeInvoke("setDefaultNamespace", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 1448:     */       
/* 1449:     */ 
/* 1450:     */ 
/* 1451:2871 */       break;
/* 1452:     */     case 72: 
/* 1453:2874 */       generateYieldPoint(node, true);
/* 1454:2875 */       break;
/* 1455:     */     case 159: 
/* 1456:2878 */       Node enterWith = child;
/* 1457:2879 */       Node with = enterWith.getNext();
/* 1458:2880 */       Node leaveWith = with.getNext();
/* 1459:2881 */       generateStatement(enterWith);
/* 1460:2882 */       generateExpression(with.getFirstChild(), with);
/* 1461:2883 */       generateStatement(leaveWith);
/* 1462:2884 */       break;
/* 1463:     */     case 157: 
/* 1464:2888 */       Node initStmt = child;
/* 1465:2889 */       Node expr = child.getNext();
/* 1466:2890 */       generateStatement(initStmt);
/* 1467:2891 */       generateExpression(expr, node);
/* 1468:2892 */       break;
/* 1469:     */     case 50: 
/* 1470:     */     case 51: 
/* 1471:     */     case 57: 
/* 1472:     */     case 58: 
/* 1473:     */     case 59: 
/* 1474:     */     case 60: 
/* 1475:     */     case 64: 
/* 1476:     */     case 81: 
/* 1477:     */     case 82: 
/* 1478:     */     case 83: 
/* 1479:     */     case 84: 
/* 1480:     */     case 85: 
/* 1481:     */     case 86: 
/* 1482:     */     case 87: 
/* 1483:     */     case 88: 
/* 1484:     */     case 90: 
/* 1485:     */     case 91: 
/* 1486:     */     case 92: 
/* 1487:     */     case 93: 
/* 1488:     */     case 94: 
/* 1489:     */     case 95: 
/* 1490:     */     case 96: 
/* 1491:     */     case 97: 
/* 1492:     */     case 98: 
/* 1493:     */     case 99: 
/* 1494:     */     case 100: 
/* 1495:     */     case 101: 
/* 1496:     */     case 103: 
/* 1497:     */     case 108: 
/* 1498:     */     case 110: 
/* 1499:     */     case 111: 
/* 1500:     */     case 112: 
/* 1501:     */     case 113: 
/* 1502:     */     case 114: 
/* 1503:     */     case 115: 
/* 1504:     */     case 116: 
/* 1505:     */     case 117: 
/* 1506:     */     case 118: 
/* 1507:     */     case 119: 
/* 1508:     */     case 120: 
/* 1509:     */     case 121: 
/* 1510:     */     case 122: 
/* 1511:     */     case 123: 
/* 1512:     */     case 124: 
/* 1513:     */     case 125: 
/* 1514:     */     case 127: 
/* 1515:     */     case 128: 
/* 1516:     */     case 129: 
/* 1517:     */     case 130: 
/* 1518:     */     case 131: 
/* 1519:     */     case 132: 
/* 1520:     */     case 133: 
/* 1521:     */     case 134: 
/* 1522:     */     case 135: 
/* 1523:     */     case 136: 
/* 1524:     */     case 141: 
/* 1525:     */     case 143: 
/* 1526:     */     case 144: 
/* 1527:     */     case 145: 
/* 1528:     */     case 147: 
/* 1529:     */     case 148: 
/* 1530:     */     case 151: 
/* 1531:     */     case 152: 
/* 1532:     */     case 153: 
/* 1533:     */     case 154: 
/* 1534:     */     case 158: 
/* 1535:     */     default: 
/* 1536:2896 */       throw new RuntimeException("Unexpected node type " + type);
/* 1537:     */     }
/* 1538:     */   }
/* 1539:     */   
/* 1540:     */   private void generateYieldPoint(Node node, boolean exprContext)
/* 1541:     */   {
/* 1542:2903 */     int top = this.cfw.getStackTop();
/* 1543:2904 */     this.maxStack = (this.maxStack > top ? this.maxStack : top);
/* 1544:2905 */     if (this.cfw.getStackTop() != 0)
/* 1545:     */     {
/* 1546:2906 */       generateGetGeneratorStackState();
/* 1547:2907 */       for (int i = 0; i < top; i++)
/* 1548:     */       {
/* 1549:2908 */         this.cfw.add(90);
/* 1550:2909 */         this.cfw.add(95);
/* 1551:2910 */         this.cfw.addLoadConstant(i);
/* 1552:2911 */         this.cfw.add(95);
/* 1553:2912 */         this.cfw.add(83);
/* 1554:     */       }
/* 1555:2915 */       this.cfw.add(87);
/* 1556:     */     }
/* 1557:2919 */     Node child = node.getFirstChild();
/* 1558:2920 */     if (child != null) {
/* 1559:2921 */       generateExpression(child, node);
/* 1560:     */     } else {
/* 1561:2923 */       Codegen.pushUndefined(this.cfw);
/* 1562:     */     }
/* 1563:2926 */     int nextState = getNextGeneratorState(node);
/* 1564:2927 */     generateSetGeneratorResumptionPoint(nextState);
/* 1565:     */     
/* 1566:2929 */     boolean hasLocals = generateSaveLocals(node);
/* 1567:     */     
/* 1568:2931 */     this.cfw.add(176);
/* 1569:     */     
/* 1570:2933 */     generateCheckForThrowOrClose(getTargetLabel(node), hasLocals, nextState);
/* 1571:2937 */     if (top != 0)
/* 1572:     */     {
/* 1573:2938 */       generateGetGeneratorStackState();
/* 1574:2939 */       for (int i = 0; i < top; i++)
/* 1575:     */       {
/* 1576:2940 */         this.cfw.add(89);
/* 1577:2941 */         this.cfw.addLoadConstant(top - i - 1);
/* 1578:2942 */         this.cfw.add(50);
/* 1579:2943 */         this.cfw.add(95);
/* 1580:     */       }
/* 1581:2945 */       this.cfw.add(87);
/* 1582:     */     }
/* 1583:2949 */     if (exprContext) {
/* 1584:2950 */       this.cfw.addALoad(this.argsLocal);
/* 1585:     */     }
/* 1586:     */   }
/* 1587:     */   
/* 1588:     */   private void generateCheckForThrowOrClose(int label, boolean hasLocals, int nextState)
/* 1589:     */   {
/* 1590:2957 */     int throwLabel = this.cfw.acquireLabel();
/* 1591:2958 */     int closeLabel = this.cfw.acquireLabel();
/* 1592:     */     
/* 1593:     */ 
/* 1594:2961 */     this.cfw.markLabel(throwLabel);
/* 1595:2962 */     this.cfw.addALoad(this.argsLocal);
/* 1596:2963 */     generateThrowJavaScriptException();
/* 1597:     */     
/* 1598:     */ 
/* 1599:2966 */     this.cfw.markLabel(closeLabel);
/* 1600:2967 */     this.cfw.addALoad(this.argsLocal);
/* 1601:2968 */     this.cfw.add(192, "java/lang/Throwable");
/* 1602:2969 */     this.cfw.add(191);
/* 1603:2973 */     if (label != -1) {
/* 1604:2974 */       this.cfw.markLabel(label);
/* 1605:     */     }
/* 1606:2975 */     if (!hasLocals) {
/* 1607:2977 */       this.cfw.markTableSwitchCase(this.generatorSwitch, nextState);
/* 1608:     */     }
/* 1609:2981 */     this.cfw.addILoad(this.operationLocal);
/* 1610:2982 */     this.cfw.addLoadConstant(2);
/* 1611:2983 */     this.cfw.add(159, closeLabel);
/* 1612:2984 */     this.cfw.addILoad(this.operationLocal);
/* 1613:2985 */     this.cfw.addLoadConstant(1);
/* 1614:2986 */     this.cfw.add(159, throwLabel);
/* 1615:     */   }
/* 1616:     */   
/* 1617:     */   private void generateIfJump(Node node, Node parent, int trueLabel, int falseLabel)
/* 1618:     */   {
/* 1619:2994 */     int type = node.getType();
/* 1620:2995 */     Node child = node.getFirstChild();
/* 1621:2997 */     switch (type)
/* 1622:     */     {
/* 1623:     */     case 26: 
/* 1624:2999 */       generateIfJump(child, node, falseLabel, trueLabel);
/* 1625:3000 */       break;
/* 1626:     */     case 104: 
/* 1627:     */     case 105: 
/* 1628:3004 */       int interLabel = this.cfw.acquireLabel();
/* 1629:3005 */       if (type == 105) {
/* 1630:3006 */         generateIfJump(child, node, interLabel, falseLabel);
/* 1631:     */       } else {
/* 1632:3009 */         generateIfJump(child, node, trueLabel, interLabel);
/* 1633:     */       }
/* 1634:3011 */       this.cfw.markLabel(interLabel);
/* 1635:3012 */       child = child.getNext();
/* 1636:3013 */       generateIfJump(child, node, trueLabel, falseLabel);
/* 1637:3014 */       break;
/* 1638:     */     case 14: 
/* 1639:     */     case 15: 
/* 1640:     */     case 16: 
/* 1641:     */     case 17: 
/* 1642:     */     case 52: 
/* 1643:     */     case 53: 
/* 1644:3023 */       visitIfJumpRelOp(node, child, trueLabel, falseLabel);
/* 1645:3024 */       break;
/* 1646:     */     case 12: 
/* 1647:     */     case 13: 
/* 1648:     */     case 46: 
/* 1649:     */     case 47: 
/* 1650:3030 */       visitIfJumpEqOp(node, child, trueLabel, falseLabel);
/* 1651:3031 */       break;
/* 1652:     */     default: 
/* 1653:3035 */       generateExpression(node, parent);
/* 1654:3036 */       addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)Z");
/* 1655:3037 */       this.cfw.add(154, trueLabel);
/* 1656:3038 */       this.cfw.add(167, falseLabel);
/* 1657:     */     }
/* 1658:     */   }
/* 1659:     */   
/* 1660:     */   private void visitFunction(OptFunctionNode ofn, int functionType)
/* 1661:     */   {
/* 1662:3044 */     int fnIndex = this.codegen.getIndex(ofn.fnode);
/* 1663:3045 */     this.cfw.add(187, this.codegen.mainClassName);
/* 1664:     */     
/* 1665:3047 */     this.cfw.add(89);
/* 1666:3048 */     this.cfw.addALoad(this.variableObjectLocal);
/* 1667:3049 */     this.cfw.addALoad(this.contextLocal);
/* 1668:3050 */     this.cfw.addPush(fnIndex);
/* 1669:3051 */     this.cfw.addInvoke(183, this.codegen.mainClassName, "<init>", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)V");
/* 1670:     */     
/* 1671:     */ 
/* 1672:     */ 
/* 1673:3055 */     this.cfw.add(89);
/* 1674:3056 */     if (this.isTopLevel)
/* 1675:     */     {
/* 1676:3057 */       this.cfw.add(42);
/* 1677:     */     }
/* 1678:     */     else
/* 1679:     */     {
/* 1680:3059 */       this.cfw.add(42);
/* 1681:3060 */       this.cfw.add(180, this.codegen.mainClassName, "_dcp", this.codegen.mainClassSignature);
/* 1682:     */     }
/* 1683:3065 */     this.cfw.add(181, this.codegen.mainClassName, "_dcp", this.codegen.mainClassSignature);
/* 1684:     */     
/* 1685:     */ 
/* 1686:     */ 
/* 1687:     */ 
/* 1688:3070 */     int directTargetIndex = ofn.getDirectTargetIndex();
/* 1689:3071 */     if (directTargetIndex >= 0)
/* 1690:     */     {
/* 1691:3072 */       this.cfw.add(89);
/* 1692:3073 */       if (this.isTopLevel)
/* 1693:     */       {
/* 1694:3074 */         this.cfw.add(42);
/* 1695:     */       }
/* 1696:     */       else
/* 1697:     */       {
/* 1698:3076 */         this.cfw.add(42);
/* 1699:3077 */         this.cfw.add(180, this.codegen.mainClassName, "_dcp", this.codegen.mainClassSignature);
/* 1700:     */       }
/* 1701:3082 */       this.cfw.add(95);
/* 1702:3083 */       this.cfw.add(181, this.codegen.mainClassName, Codegen.getDirectTargetFieldName(directTargetIndex), this.codegen.mainClassSignature);
/* 1703:     */     }
/* 1704:3089 */     if (functionType == 2) {
/* 1705:3092 */       return;
/* 1706:     */     }
/* 1707:3094 */     this.cfw.addPush(functionType);
/* 1708:3095 */     this.cfw.addALoad(this.variableObjectLocal);
/* 1709:3096 */     this.cfw.addALoad(this.contextLocal);
/* 1710:3097 */     addOptRuntimeInvoke("initFunction", "(Lnet/sourceforge/htmlunit/corejs/javascript/NativeFunction;ILnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)V");
/* 1711:     */   }
/* 1712:     */   
/* 1713:     */   private int getTargetLabel(Node target)
/* 1714:     */   {
/* 1715:3107 */     int labelId = target.labelId();
/* 1716:3108 */     if (labelId == -1)
/* 1717:     */     {
/* 1718:3109 */       labelId = this.cfw.acquireLabel();
/* 1719:3110 */       target.labelId(labelId);
/* 1720:     */     }
/* 1721:3112 */     return labelId;
/* 1722:     */   }
/* 1723:     */   
/* 1724:     */   private void visitGoto(Jump node, int type, Node child)
/* 1725:     */   {
/* 1726:3117 */     Node target = node.target;
/* 1727:3118 */     if ((type == 6) || (type == 7))
/* 1728:     */     {
/* 1729:3119 */       if (child == null) {
/* 1730:3119 */         throw Codegen.badTree();
/* 1731:     */       }
/* 1732:3120 */       int targetLabel = getTargetLabel(target);
/* 1733:3121 */       int fallThruLabel = this.cfw.acquireLabel();
/* 1734:3122 */       if (type == 6) {
/* 1735:3123 */         generateIfJump(child, node, targetLabel, fallThruLabel);
/* 1736:     */       } else {
/* 1737:3125 */         generateIfJump(child, node, fallThruLabel, targetLabel);
/* 1738:     */       }
/* 1739:3126 */       this.cfw.markLabel(fallThruLabel);
/* 1740:     */     }
/* 1741:3128 */     else if (type == 135)
/* 1742:     */     {
/* 1743:3129 */       if (this.isGenerator) {
/* 1744:3130 */         addGotoWithReturn(target);
/* 1745:     */       } else {
/* 1746:3132 */         addGoto(target, 168);
/* 1747:     */       }
/* 1748:     */     }
/* 1749:     */     else
/* 1750:     */     {
/* 1751:3135 */       addGoto(target, 167);
/* 1752:     */     }
/* 1753:     */   }
/* 1754:     */   
/* 1755:     */   private void addGotoWithReturn(Node target)
/* 1756:     */   {
/* 1757:3141 */     FinallyReturnPoint ret = (FinallyReturnPoint)this.finallys.get(target);
/* 1758:3142 */     this.cfw.addLoadConstant(ret.jsrPoints.size());
/* 1759:3143 */     addGoto(target, 167);
/* 1760:3144 */     int retLabel = this.cfw.acquireLabel();
/* 1761:3145 */     this.cfw.markLabel(retLabel);
/* 1762:3146 */     ret.jsrPoints.add(Integer.valueOf(retLabel));
/* 1763:     */   }
/* 1764:     */   
/* 1765:     */   private void visitArrayLiteral(Node node, Node child)
/* 1766:     */   {
/* 1767:3151 */     int count = 0;
/* 1768:3152 */     for (Node cursor = child; cursor != null; cursor = cursor.getNext()) {
/* 1769:3153 */       count++;
/* 1770:     */     }
/* 1771:3156 */     addNewObjectArray(count);
/* 1772:3157 */     for (int i = 0; i != count; i++)
/* 1773:     */     {
/* 1774:3158 */       this.cfw.add(89);
/* 1775:3159 */       this.cfw.addPush(i);
/* 1776:3160 */       generateExpression(child, node);
/* 1777:3161 */       this.cfw.add(83);
/* 1778:3162 */       child = child.getNext();
/* 1779:     */     }
/* 1780:3164 */     int[] skipIndexes = (int[])node.getProp(11);
/* 1781:3165 */     if (skipIndexes == null)
/* 1782:     */     {
/* 1783:3166 */       this.cfw.add(1);
/* 1784:3167 */       this.cfw.add(3);
/* 1785:     */     }
/* 1786:     */     else
/* 1787:     */     {
/* 1788:3169 */       this.cfw.addPush(OptRuntime.encodeIntArray(skipIndexes));
/* 1789:3170 */       this.cfw.addPush(skipIndexes.length);
/* 1790:     */     }
/* 1791:3172 */     this.cfw.addALoad(this.contextLocal);
/* 1792:3173 */     this.cfw.addALoad(this.variableObjectLocal);
/* 1793:3174 */     addOptRuntimeInvoke("newArrayLiteral", "([Ljava/lang/Object;Ljava/lang/String;ILnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 1794:     */   }
/* 1795:     */   
/* 1796:     */   private void visitObjectLiteral(Node node, Node child)
/* 1797:     */   {
/* 1798:3185 */     Object[] properties = (Object[])node.getProp(12);
/* 1799:3186 */     int count = properties.length;
/* 1800:     */     
/* 1801:     */ 
/* 1802:3189 */     addNewObjectArray(count);
/* 1803:3190 */     for (int i = 0; i != count; i++)
/* 1804:     */     {
/* 1805:3191 */       this.cfw.add(89);
/* 1806:3192 */       this.cfw.addPush(i);
/* 1807:3193 */       Object id = properties[i];
/* 1808:3194 */       if ((id instanceof String))
/* 1809:     */       {
/* 1810:3195 */         this.cfw.addPush((String)id);
/* 1811:     */       }
/* 1812:     */       else
/* 1813:     */       {
/* 1814:3197 */         this.cfw.addPush(((Integer)id).intValue());
/* 1815:3198 */         addScriptRuntimeInvoke("wrapInt", "(I)Ljava/lang/Integer;");
/* 1816:     */       }
/* 1817:3200 */       this.cfw.add(83);
/* 1818:     */     }
/* 1819:3203 */     addNewObjectArray(count);
/* 1820:3204 */     Node child2 = child;
/* 1821:3205 */     for (int i = 0; i != count; i++)
/* 1822:     */     {
/* 1823:3206 */       this.cfw.add(89);
/* 1824:3207 */       this.cfw.addPush(i);
/* 1825:3208 */       int childType = child.getType();
/* 1826:3209 */       if (childType == 151) {
/* 1827:3210 */         generateExpression(child.getFirstChild(), node);
/* 1828:3211 */       } else if (childType == 152) {
/* 1829:3212 */         generateExpression(child.getFirstChild(), node);
/* 1830:     */       } else {
/* 1831:3214 */         generateExpression(child, node);
/* 1832:     */       }
/* 1833:3216 */       this.cfw.add(83);
/* 1834:3217 */       child = child.getNext();
/* 1835:     */     }
/* 1836:3220 */     this.cfw.addPush(count);
/* 1837:3221 */     this.cfw.add(188, 10);
/* 1838:3222 */     for (int i = 0; i != count; i++)
/* 1839:     */     {
/* 1840:3223 */       this.cfw.add(89);
/* 1841:3224 */       this.cfw.addPush(i);
/* 1842:3225 */       int childType = child2.getType();
/* 1843:3226 */       if (childType == 151) {
/* 1844:3227 */         this.cfw.add(2);
/* 1845:3228 */       } else if (childType == 152) {
/* 1846:3229 */         this.cfw.add(4);
/* 1847:     */       } else {
/* 1848:3231 */         this.cfw.add(3);
/* 1849:     */       }
/* 1850:3233 */       this.cfw.add(79);
/* 1851:3234 */       child2 = child2.getNext();
/* 1852:     */     }
/* 1853:3237 */     this.cfw.addALoad(this.contextLocal);
/* 1854:3238 */     this.cfw.addALoad(this.variableObjectLocal);
/* 1855:3239 */     addScriptRuntimeInvoke("newObjectLiteral", "([Ljava/lang/Object;[Ljava/lang/Object;[ILnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 1856:     */   }
/* 1857:     */   
/* 1858:     */   private void visitSpecialCall(Node node, int type, int specialType, Node child)
/* 1859:     */   {
/* 1860:3251 */     this.cfw.addALoad(this.contextLocal);
/* 1861:3253 */     if (type == 30) {
/* 1862:3254 */       generateExpression(child, node);
/* 1863:     */     } else {
/* 1864:3257 */       generateFunctionAndThisObj(child, node);
/* 1865:     */     }
/* 1866:3260 */     child = child.getNext();
/* 1867:     */     
/* 1868:3262 */     generateCallArgArray(node, child, false);
/* 1869:     */     String methodName;
/* 1870:     */     String callSignature;
/* 1871:3267 */     if (type == 30)
/* 1872:     */     {
/* 1873:3268 */       String methodName = "newObjectSpecial";
/* 1874:3269 */       String callSignature = "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Ljava/lang/Object;[Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;I)Ljava/lang/Object;";
/* 1875:     */       
/* 1876:     */ 
/* 1877:     */ 
/* 1878:     */ 
/* 1879:     */ 
/* 1880:     */ 
/* 1881:3276 */       this.cfw.addALoad(this.variableObjectLocal);
/* 1882:3277 */       this.cfw.addALoad(this.thisObjLocal);
/* 1883:3278 */       this.cfw.addPush(specialType);
/* 1884:     */     }
/* 1885:     */     else
/* 1886:     */     {
/* 1887:3280 */       methodName = "callSpecial";
/* 1888:3281 */       callSignature = "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Callable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;ILjava/lang/String;I)Ljava/lang/Object;";
/* 1889:     */       
/* 1890:     */ 
/* 1891:     */ 
/* 1892:     */ 
/* 1893:     */ 
/* 1894:     */ 
/* 1895:     */ 
/* 1896:     */ 
/* 1897:3290 */       this.cfw.addALoad(this.variableObjectLocal);
/* 1898:3291 */       this.cfw.addALoad(this.thisObjLocal);
/* 1899:3292 */       this.cfw.addPush(specialType);
/* 1900:3293 */       String sourceName = this.scriptOrFn.getSourceName();
/* 1901:3294 */       this.cfw.addPush(sourceName == null ? "" : sourceName);
/* 1902:3295 */       this.cfw.addPush(this.itsLineNumber);
/* 1903:     */     }
/* 1904:3298 */     addOptRuntimeInvoke(methodName, callSignature);
/* 1905:     */   }
/* 1906:     */   
/* 1907:     */   private void visitStandardCall(Node node, Node child)
/* 1908:     */   {
/* 1909:3303 */     if (node.getType() != 38) {
/* 1910:3303 */       throw Codegen.badTree();
/* 1911:     */     }
/* 1912:3305 */     Node firstArgChild = child.getNext();
/* 1913:3306 */     int childType = child.getType();
/* 1914:     */     String signature;
/* 1915:     */     String methodName;
/* 1916:     */     String signature;
/* 1917:3311 */     if (firstArgChild == null)
/* 1918:     */     {
/* 1919:     */       String signature;
/* 1920:3312 */       if (childType == 39)
/* 1921:     */       {
/* 1922:3314 */         String name = child.getString();
/* 1923:3315 */         this.cfw.addPush(name);
/* 1924:3316 */         String methodName = "callName0";
/* 1925:3317 */         signature = "(Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;";
/* 1926:     */       }
/* 1927:     */       else
/* 1928:     */       {
/* 1929:     */         String signature;
/* 1930:3321 */         if (childType == 33)
/* 1931:     */         {
/* 1932:3323 */           Node propTarget = child.getFirstChild();
/* 1933:3324 */           generateExpression(propTarget, node);
/* 1934:3325 */           Node id = propTarget.getNext();
/* 1935:3326 */           String property = id.getString();
/* 1936:3327 */           this.cfw.addPush(property);
/* 1937:3328 */           String methodName = "callProp0";
/* 1938:3329 */           signature = "(Ljava/lang/Object;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;";
/* 1939:     */         }
/* 1940:     */         else
/* 1941:     */         {
/* 1942:3334 */           if (childType == 34) {
/* 1943:3335 */             throw Kit.codeBug();
/* 1944:     */           }
/* 1945:3337 */           generateFunctionAndThisObj(child, node);
/* 1946:3338 */           String methodName = "call0";
/* 1947:3339 */           signature = "(Lnet/sourceforge/htmlunit/corejs/javascript/Callable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;";
/* 1948:     */         }
/* 1949:     */       }
/* 1950:     */     }
/* 1951:     */     else
/* 1952:     */     {
/* 1953:     */       String signature;
/* 1954:3346 */       if (childType == 39)
/* 1955:     */       {
/* 1956:3351 */         String name = child.getString();
/* 1957:3352 */         generateCallArgArray(node, firstArgChild, false);
/* 1958:3353 */         this.cfw.addPush(name);
/* 1959:3354 */         String methodName = "callName";
/* 1960:3355 */         signature = "([Ljava/lang/Object;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;";
/* 1961:     */       }
/* 1962:     */       else
/* 1963:     */       {
/* 1964:3361 */         int argCount = 0;
/* 1965:3362 */         for (Node arg = firstArgChild; arg != null; arg = arg.getNext()) {
/* 1966:3363 */           argCount++;
/* 1967:     */         }
/* 1968:3365 */         generateFunctionAndThisObj(child, node);
/* 1969:     */         String signature;
/* 1970:3367 */         if (argCount == 1)
/* 1971:     */         {
/* 1972:3368 */           generateExpression(firstArgChild, node);
/* 1973:3369 */           String methodName = "call1";
/* 1974:3370 */           signature = "(Lnet/sourceforge/htmlunit/corejs/javascript/Callable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;";
/* 1975:     */         }
/* 1976:     */         else
/* 1977:     */         {
/* 1978:     */           String signature;
/* 1979:3376 */           if (argCount == 2)
/* 1980:     */           {
/* 1981:3377 */             generateExpression(firstArgChild, node);
/* 1982:3378 */             generateExpression(firstArgChild.getNext(), node);
/* 1983:3379 */             String methodName = "call2";
/* 1984:3380 */             signature = "(Lnet/sourceforge/htmlunit/corejs/javascript/Callable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;";
/* 1985:     */           }
/* 1986:     */           else
/* 1987:     */           {
/* 1988:3388 */             generateCallArgArray(node, firstArgChild, false);
/* 1989:3389 */             methodName = "callN";
/* 1990:3390 */             signature = "(Lnet/sourceforge/htmlunit/corejs/javascript/Callable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;";
/* 1991:     */           }
/* 1992:     */         }
/* 1993:     */       }
/* 1994:     */     }
/* 1995:3399 */     this.cfw.addALoad(this.contextLocal);
/* 1996:3400 */     this.cfw.addALoad(this.variableObjectLocal);
/* 1997:3401 */     addOptRuntimeInvoke(methodName, signature);
/* 1998:     */   }
/* 1999:     */   
/* 2000:     */   private void visitStandardNew(Node node, Node child)
/* 2001:     */   {
/* 2002:3406 */     if (node.getType() != 30) {
/* 2003:3406 */       throw Codegen.badTree();
/* 2004:     */     }
/* 2005:3408 */     Node firstArgChild = child.getNext();
/* 2006:     */     
/* 2007:3410 */     generateExpression(child, node);
/* 2008:     */     
/* 2009:3412 */     this.cfw.addALoad(this.contextLocal);
/* 2010:3413 */     this.cfw.addALoad(this.variableObjectLocal);
/* 2011:     */     
/* 2012:3415 */     generateCallArgArray(node, firstArgChild, false);
/* 2013:3416 */     addScriptRuntimeInvoke("newObject", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 2014:     */   }
/* 2015:     */   
/* 2016:     */   private void visitOptimizedCall(Node node, OptFunctionNode target, int type, Node child)
/* 2017:     */   {
/* 2018:3428 */     Node firstArgChild = child.getNext();
/* 2019:     */     
/* 2020:3430 */     short thisObjLocal = 0;
/* 2021:3431 */     if (type == 30)
/* 2022:     */     {
/* 2023:3432 */       generateExpression(child, node);
/* 2024:     */     }
/* 2025:     */     else
/* 2026:     */     {
/* 2027:3434 */       generateFunctionAndThisObj(child, node);
/* 2028:3435 */       thisObjLocal = getNewWordLocal();
/* 2029:3436 */       this.cfw.addAStore(thisObjLocal);
/* 2030:     */     }
/* 2031:3440 */     int beyond = this.cfw.acquireLabel();
/* 2032:     */     
/* 2033:3442 */     int directTargetIndex = target.getDirectTargetIndex();
/* 2034:3443 */     if (this.isTopLevel)
/* 2035:     */     {
/* 2036:3444 */       this.cfw.add(42);
/* 2037:     */     }
/* 2038:     */     else
/* 2039:     */     {
/* 2040:3446 */       this.cfw.add(42);
/* 2041:3447 */       this.cfw.add(180, this.codegen.mainClassName, "_dcp", this.codegen.mainClassSignature);
/* 2042:     */     }
/* 2043:3451 */     this.cfw.add(180, this.codegen.mainClassName, Codegen.getDirectTargetFieldName(directTargetIndex), this.codegen.mainClassSignature);
/* 2044:     */     
/* 2045:     */ 
/* 2046:     */ 
/* 2047:3455 */     this.cfw.add(92);
/* 2048:     */     
/* 2049:     */ 
/* 2050:3458 */     int regularCall = this.cfw.acquireLabel();
/* 2051:3459 */     this.cfw.add(166, regularCall);
/* 2052:     */     
/* 2053:     */ 
/* 2054:3462 */     short stackHeight = this.cfw.getStackTop();
/* 2055:3463 */     this.cfw.add(95);
/* 2056:3464 */     this.cfw.add(87);
/* 2057:3466 */     if (this.compilerEnv.isUseDynamicScope())
/* 2058:     */     {
/* 2059:3467 */       this.cfw.addALoad(this.contextLocal);
/* 2060:3468 */       this.cfw.addALoad(this.variableObjectLocal);
/* 2061:     */     }
/* 2062:     */     else
/* 2063:     */     {
/* 2064:3470 */       this.cfw.add(89);
/* 2065:     */       
/* 2066:3472 */       this.cfw.addInvoke(185, "net/sourceforge/htmlunit/corejs/javascript/Scriptable", "getParentScope", "()Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 2067:     */       
/* 2068:     */ 
/* 2069:     */ 
/* 2070:     */ 
/* 2071:3477 */       this.cfw.addALoad(this.contextLocal);
/* 2072:     */       
/* 2073:3479 */       this.cfw.add(95);
/* 2074:     */     }
/* 2075:3483 */     if (type == 30) {
/* 2076:3484 */       this.cfw.add(1);
/* 2077:     */     } else {
/* 2078:3486 */       this.cfw.addALoad(thisObjLocal);
/* 2079:     */     }
/* 2080:3496 */     Node argChild = firstArgChild;
/* 2081:3497 */     while (argChild != null)
/* 2082:     */     {
/* 2083:3498 */       int dcp_register = nodeIsDirectCallParameter(argChild);
/* 2084:3499 */       if (dcp_register >= 0)
/* 2085:     */       {
/* 2086:3500 */         this.cfw.addALoad(dcp_register);
/* 2087:3501 */         this.cfw.addDLoad(dcp_register + 1);
/* 2088:     */       }
/* 2089:3502 */       else if (argChild.getIntProp(8, -1) == 0)
/* 2090:     */       {
/* 2091:3505 */         this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/* 2092:     */         
/* 2093:     */ 
/* 2094:     */ 
/* 2095:3509 */         generateExpression(argChild, node);
/* 2096:     */       }
/* 2097:     */       else
/* 2098:     */       {
/* 2099:3511 */         generateExpression(argChild, node);
/* 2100:3512 */         this.cfw.addPush(0.0D);
/* 2101:     */       }
/* 2102:3514 */       argChild = argChild.getNext();
/* 2103:     */     }
/* 2104:3517 */     this.cfw.add(178, "net/sourceforge/htmlunit/corejs/javascript/ScriptRuntime", "emptyArgs", "[Ljava/lang/Object;");
/* 2105:     */     
/* 2106:     */ 
/* 2107:3520 */     this.cfw.addInvoke(184, this.codegen.mainClassName, type == 30 ? this.codegen.getDirectCtorName(target.fnode) : this.codegen.getBodyMethodName(target.fnode), this.codegen.getBodyMethodSignature(target.fnode));
/* 2108:     */     
/* 2109:     */ 
/* 2110:     */ 
/* 2111:     */ 
/* 2112:     */ 
/* 2113:     */ 
/* 2114:3527 */     this.cfw.add(167, beyond);
/* 2115:     */     
/* 2116:3529 */     this.cfw.markLabel(regularCall, stackHeight);
/* 2117:     */     
/* 2118:3531 */     this.cfw.add(87);
/* 2119:3532 */     this.cfw.addALoad(this.contextLocal);
/* 2120:3533 */     this.cfw.addALoad(this.variableObjectLocal);
/* 2121:3535 */     if (type != 30)
/* 2122:     */     {
/* 2123:3536 */       this.cfw.addALoad(thisObjLocal);
/* 2124:3537 */       releaseWordLocal(thisObjLocal);
/* 2125:     */     }
/* 2126:3542 */     generateCallArgArray(node, firstArgChild, true);
/* 2127:3544 */     if (type == 30) {
/* 2128:3545 */       addScriptRuntimeInvoke("newObject", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 2129:     */     } else {
/* 2130:3553 */       this.cfw.addInvoke(185, "net/sourceforge/htmlunit/corejs/javascript/Callable", "call", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;");
/* 2131:     */     }
/* 2132:3563 */     this.cfw.markLabel(beyond);
/* 2133:     */   }
/* 2134:     */   
/* 2135:     */   private void generateCallArgArray(Node node, Node argChild, boolean directCall)
/* 2136:     */   {
/* 2137:3568 */     int argCount = 0;
/* 2138:3569 */     for (Node child = argChild; child != null; child = child.getNext()) {
/* 2139:3570 */       argCount++;
/* 2140:     */     }
/* 2141:3573 */     if ((argCount == 1) && (this.itsOneArgArray >= 0)) {
/* 2142:3574 */       this.cfw.addALoad(this.itsOneArgArray);
/* 2143:     */     } else {
/* 2144:3576 */       addNewObjectArray(argCount);
/* 2145:     */     }
/* 2146:3579 */     for (int i = 0; i != argCount; i++)
/* 2147:     */     {
/* 2148:3583 */       if (!this.isGenerator)
/* 2149:     */       {
/* 2150:3584 */         this.cfw.add(89);
/* 2151:3585 */         this.cfw.addPush(i);
/* 2152:     */       }
/* 2153:3588 */       if (!directCall)
/* 2154:     */       {
/* 2155:3589 */         generateExpression(argChild, node);
/* 2156:     */       }
/* 2157:     */       else
/* 2158:     */       {
/* 2159:3596 */         int dcp_register = nodeIsDirectCallParameter(argChild);
/* 2160:3597 */         if (dcp_register >= 0)
/* 2161:     */         {
/* 2162:3598 */           dcpLoadAsObject(dcp_register);
/* 2163:     */         }
/* 2164:     */         else
/* 2165:     */         {
/* 2166:3600 */           generateExpression(argChild, node);
/* 2167:3601 */           int childNumberFlag = argChild.getIntProp(8, -1);
/* 2168:3603 */           if (childNumberFlag == 0) {
/* 2169:3604 */             addDoubleWrap();
/* 2170:     */           }
/* 2171:     */         }
/* 2172:     */       }
/* 2173:3612 */       if (this.isGenerator)
/* 2174:     */       {
/* 2175:3613 */         short tempLocal = getNewWordLocal();
/* 2176:3614 */         this.cfw.addAStore(tempLocal);
/* 2177:3615 */         this.cfw.add(192, "[Ljava/lang/Object;");
/* 2178:3616 */         this.cfw.add(89);
/* 2179:3617 */         this.cfw.addPush(i);
/* 2180:3618 */         this.cfw.addALoad(tempLocal);
/* 2181:3619 */         releaseWordLocal(tempLocal);
/* 2182:     */       }
/* 2183:3622 */       this.cfw.add(83);
/* 2184:     */       
/* 2185:3624 */       argChild = argChild.getNext();
/* 2186:     */     }
/* 2187:     */   }
/* 2188:     */   
/* 2189:     */   private void generateFunctionAndThisObj(Node node, Node parent)
/* 2190:     */   {
/* 2191:3631 */     int type = node.getType();
/* 2192:3632 */     switch (node.getType())
/* 2193:     */     {
/* 2194:     */     case 34: 
/* 2195:3634 */       throw Kit.codeBug();
/* 2196:     */     case 33: 
/* 2197:     */     case 36: 
/* 2198:3638 */       Node target = node.getFirstChild();
/* 2199:3639 */       generateExpression(target, node);
/* 2200:3640 */       Node id = target.getNext();
/* 2201:3641 */       if (type == 33)
/* 2202:     */       {
/* 2203:3642 */         String property = id.getString();
/* 2204:3643 */         this.cfw.addPush(property);
/* 2205:3644 */         this.cfw.addALoad(this.contextLocal);
/* 2206:3645 */         this.cfw.addALoad(this.variableObjectLocal);
/* 2207:3646 */         addScriptRuntimeInvoke("getPropFunctionAndThis", "(Ljava/lang/Object;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Callable;");
/* 2208:     */       }
/* 2209:     */       else
/* 2210:     */       {
/* 2211:3655 */         if (node.getIntProp(8, -1) != -1) {
/* 2212:3656 */           throw Codegen.badTree();
/* 2213:     */         }
/* 2214:3657 */         generateExpression(id, node);
/* 2215:3658 */         this.cfw.addALoad(this.contextLocal);
/* 2216:3659 */         addScriptRuntimeInvoke("getElemFunctionAndThis", "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Lnet/sourceforge/htmlunit/corejs/javascript/Callable;");
/* 2217:     */       }
/* 2218:3666 */       break;
/* 2219:     */     case 39: 
/* 2220:3670 */       String name = node.getString();
/* 2221:3671 */       this.cfw.addPush(name);
/* 2222:3672 */       this.cfw.addALoad(this.contextLocal);
/* 2223:3673 */       this.cfw.addALoad(this.variableObjectLocal);
/* 2224:3674 */       addScriptRuntimeInvoke("getNameFunctionAndThis", "(Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Callable;");
/* 2225:     */       
/* 2226:     */ 
/* 2227:     */ 
/* 2228:     */ 
/* 2229:     */ 
/* 2230:3680 */       break;
/* 2231:     */     case 35: 
/* 2232:     */     case 37: 
/* 2233:     */     case 38: 
/* 2234:     */     default: 
/* 2235:3684 */       generateExpression(node, parent);
/* 2236:3685 */       this.cfw.addALoad(this.contextLocal);
/* 2237:3686 */       addScriptRuntimeInvoke("getValueFunctionAndThis", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Lnet/sourceforge/htmlunit/corejs/javascript/Callable;");
/* 2238:     */     }
/* 2239:3694 */     this.cfw.addALoad(this.contextLocal);
/* 2240:3695 */     addScriptRuntimeInvoke("lastStoredScriptable", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 2241:     */   }
/* 2242:     */   
/* 2243:     */   private void updateLineNumber(Node node)
/* 2244:     */   {
/* 2245:3703 */     this.itsLineNumber = node.getLineno();
/* 2246:3704 */     if (this.itsLineNumber == -1) {
/* 2247:3705 */       return;
/* 2248:     */     }
/* 2249:3706 */     this.cfw.addLineNumberEntry((short)this.itsLineNumber);
/* 2250:     */   }
/* 2251:     */   
/* 2252:     */   private void visitTryCatchFinally(Jump node, Node child)
/* 2253:     */   {
/* 2254:3723 */     short savedVariableObject = getNewWordLocal();
/* 2255:3724 */     this.cfw.addALoad(this.variableObjectLocal);
/* 2256:3725 */     this.cfw.addAStore(savedVariableObject);
/* 2257:     */     
/* 2258:     */ 
/* 2259:     */ 
/* 2260:     */ 
/* 2261:     */ 
/* 2262:     */ 
/* 2263:3732 */     int startLabel = this.cfw.acquireLabel();
/* 2264:3733 */     this.cfw.markLabel(startLabel, (short)0);
/* 2265:     */     
/* 2266:3735 */     Node catchTarget = node.target;
/* 2267:3736 */     Node finallyTarget = node.getFinally();
/* 2268:3739 */     if ((this.isGenerator) && (finallyTarget != null))
/* 2269:     */     {
/* 2270:3740 */       FinallyReturnPoint ret = new FinallyReturnPoint();
/* 2271:3741 */       if (this.finallys == null) {
/* 2272:3742 */         this.finallys = new HashMap();
/* 2273:     */       }
/* 2274:3745 */       this.finallys.put(finallyTarget, ret);
/* 2275:     */       
/* 2276:3747 */       this.finallys.put(finallyTarget.getNext(), ret);
/* 2277:     */     }
/* 2278:3750 */     while (child != null)
/* 2279:     */     {
/* 2280:3751 */       generateStatement(child);
/* 2281:3752 */       child = child.getNext();
/* 2282:     */     }
/* 2283:3756 */     int realEnd = this.cfw.acquireLabel();
/* 2284:3757 */     this.cfw.add(167, realEnd);
/* 2285:     */     
/* 2286:3759 */     int exceptionLocal = getLocalBlockRegister(node);
/* 2287:3762 */     if (catchTarget != null)
/* 2288:     */     {
/* 2289:3764 */       int catchLabel = catchTarget.labelId();
/* 2290:     */       
/* 2291:3766 */       generateCatchBlock(0, savedVariableObject, catchLabel, startLabel, exceptionLocal);
/* 2292:     */       
/* 2293:     */ 
/* 2294:     */ 
/* 2295:     */ 
/* 2296:     */ 
/* 2297:3772 */       generateCatchBlock(1, savedVariableObject, catchLabel, startLabel, exceptionLocal);
/* 2298:     */       
/* 2299:     */ 
/* 2300:     */ 
/* 2301:     */ 
/* 2302:     */ 
/* 2303:     */ 
/* 2304:3779 */       generateCatchBlock(2, savedVariableObject, catchLabel, startLabel, exceptionLocal);
/* 2305:     */       
/* 2306:     */ 
/* 2307:3782 */       Context cx = Context.getCurrentContext();
/* 2308:3783 */       if ((cx != null) && (cx.hasFeature(13))) {
/* 2309:3786 */         generateCatchBlock(3, savedVariableObject, catchLabel, startLabel, exceptionLocal);
/* 2310:     */       }
/* 2311:     */     }
/* 2312:3793 */     if (finallyTarget != null)
/* 2313:     */     {
/* 2314:3794 */       int finallyHandler = this.cfw.acquireLabel();
/* 2315:3795 */       this.cfw.markHandler(finallyHandler);
/* 2316:3796 */       this.cfw.addAStore(exceptionLocal);
/* 2317:     */       
/* 2318:     */ 
/* 2319:3799 */       this.cfw.addALoad(savedVariableObject);
/* 2320:3800 */       this.cfw.addAStore(this.variableObjectLocal);
/* 2321:     */       
/* 2322:     */ 
/* 2323:3803 */       int finallyLabel = finallyTarget.labelId();
/* 2324:3804 */       if (this.isGenerator) {
/* 2325:3805 */         addGotoWithReturn(finallyTarget);
/* 2326:     */       } else {
/* 2327:3807 */         this.cfw.add(168, finallyLabel);
/* 2328:     */       }
/* 2329:3810 */       this.cfw.addALoad(exceptionLocal);
/* 2330:3811 */       if (this.isGenerator) {
/* 2331:3812 */         this.cfw.add(192, "java/lang/Throwable");
/* 2332:     */       }
/* 2333:3813 */       this.cfw.add(191);
/* 2334:     */       
/* 2335:     */ 
/* 2336:3816 */       this.cfw.addExceptionHandler(startLabel, finallyLabel, finallyHandler, null);
/* 2337:     */     }
/* 2338:3819 */     releaseWordLocal(savedVariableObject);
/* 2339:3820 */     this.cfw.markLabel(realEnd);
/* 2340:     */   }
/* 2341:     */   
/* 2342:     */   private void generateCatchBlock(int exceptionType, short savedVariableObject, int catchLabel, int startLabel, int exceptionLocal)
/* 2343:     */   {
/* 2344:3833 */     int handler = this.cfw.acquireLabel();
/* 2345:3834 */     this.cfw.markHandler(handler);
/* 2346:     */     
/* 2347:     */ 
/* 2348:3837 */     this.cfw.addAStore(exceptionLocal);
/* 2349:     */     
/* 2350:     */ 
/* 2351:3840 */     this.cfw.addALoad(savedVariableObject);
/* 2352:3841 */     this.cfw.addAStore(this.variableObjectLocal);
/* 2353:     */     String exceptionName;
/* 2354:3844 */     if (exceptionType == 0)
/* 2355:     */     {
/* 2356:3845 */       exceptionName = "net/sourceforge/htmlunit/corejs/javascript/JavaScriptException";
/* 2357:     */     }
/* 2358:     */     else
/* 2359:     */     {
/* 2360:     */       String exceptionName;
/* 2361:3846 */       if (exceptionType == 1)
/* 2362:     */       {
/* 2363:3847 */         exceptionName = "net/sourceforge/htmlunit/corejs/javascript/EvaluatorException";
/* 2364:     */       }
/* 2365:     */       else
/* 2366:     */       {
/* 2367:     */         String exceptionName;
/* 2368:3848 */         if (exceptionType == 2)
/* 2369:     */         {
/* 2370:3849 */           exceptionName = "net/sourceforge/htmlunit/corejs/javascript/EcmaError";
/* 2371:     */         }
/* 2372:     */         else
/* 2373:     */         {
/* 2374:     */           String exceptionName;
/* 2375:3850 */           if (exceptionType == 3) {
/* 2376:3851 */             exceptionName = "java/lang/Throwable";
/* 2377:     */           } else {
/* 2378:3853 */             throw Kit.codeBug();
/* 2379:     */           }
/* 2380:     */         }
/* 2381:     */       }
/* 2382:     */     }
/* 2383:     */     String exceptionName;
/* 2384:3857 */     this.cfw.addExceptionHandler(startLabel, catchLabel, handler, exceptionName);
/* 2385:     */     
/* 2386:     */ 
/* 2387:3860 */     this.cfw.add(167, catchLabel);
/* 2388:     */   }
/* 2389:     */   
/* 2390:     */   private boolean generateSaveLocals(Node node)
/* 2391:     */   {
/* 2392:3866 */     int count = 0;
/* 2393:3867 */     for (int i = 0; i < this.firstFreeLocal; i++) {
/* 2394:3868 */       if (this.locals[i] != 0) {
/* 2395:3869 */         count++;
/* 2396:     */       }
/* 2397:     */     }
/* 2398:3872 */     if (count == 0)
/* 2399:     */     {
/* 2400:3873 */       ((FunctionNode)this.scriptOrFn).addLiveLocals(node, null);
/* 2401:3874 */       return false;
/* 2402:     */     }
/* 2403:3878 */     this.maxLocals = (this.maxLocals > count ? this.maxLocals : count);
/* 2404:     */     
/* 2405:     */ 
/* 2406:3881 */     int[] ls = new int[count];
/* 2407:3882 */     int s = 0;
/* 2408:3883 */     for (int i = 0; i < this.firstFreeLocal; i++) {
/* 2409:3884 */       if (this.locals[i] != 0)
/* 2410:     */       {
/* 2411:3885 */         ls[s] = i;
/* 2412:3886 */         s++;
/* 2413:     */       }
/* 2414:     */     }
/* 2415:3891 */     ((FunctionNode)this.scriptOrFn).addLiveLocals(node, ls);
/* 2416:     */     
/* 2417:     */ 
/* 2418:3894 */     generateGetGeneratorLocalsState();
/* 2419:3895 */     for (int i = 0; i < count; i++)
/* 2420:     */     {
/* 2421:3896 */       this.cfw.add(89);
/* 2422:3897 */       this.cfw.addLoadConstant(i);
/* 2423:3898 */       this.cfw.addALoad(ls[i]);
/* 2424:3899 */       this.cfw.add(83);
/* 2425:     */     }
/* 2426:3902 */     this.cfw.add(87);
/* 2427:     */     
/* 2428:3904 */     return true;
/* 2429:     */   }
/* 2430:     */   
/* 2431:     */   private void visitSwitch(Jump switchNode, Node child)
/* 2432:     */   {
/* 2433:3912 */     generateExpression(child, switchNode);
/* 2434:     */     
/* 2435:3914 */     short selector = getNewWordLocal();
/* 2436:3915 */     this.cfw.addAStore(selector);
/* 2437:3917 */     for (Jump caseNode = (Jump)child.getNext(); caseNode != null; caseNode = (Jump)caseNode.getNext())
/* 2438:     */     {
/* 2439:3921 */       if (caseNode.getType() != 115) {
/* 2440:3922 */         throw Codegen.badTree();
/* 2441:     */       }
/* 2442:3923 */       Node test = caseNode.getFirstChild();
/* 2443:3924 */       generateExpression(test, caseNode);
/* 2444:3925 */       this.cfw.addALoad(selector);
/* 2445:3926 */       addScriptRuntimeInvoke("shallowEq", "(Ljava/lang/Object;Ljava/lang/Object;)Z");
/* 2446:     */       
/* 2447:     */ 
/* 2448:     */ 
/* 2449:3930 */       addGoto(caseNode.target, 154);
/* 2450:     */     }
/* 2451:3932 */     releaseWordLocal(selector);
/* 2452:     */   }
/* 2453:     */   
/* 2454:     */   private void visitTypeofname(Node node)
/* 2455:     */   {
/* 2456:3937 */     if (this.hasVarsInRegs)
/* 2457:     */     {
/* 2458:3938 */       int varIndex = this.fnCurrent.fnode.getIndexForNameNode(node);
/* 2459:3939 */       if (varIndex >= 0)
/* 2460:     */       {
/* 2461:3940 */         if (this.fnCurrent.isNumberVar(varIndex))
/* 2462:     */         {
/* 2463:3941 */           this.cfw.addPush("number");
/* 2464:     */         }
/* 2465:3942 */         else if (varIsDirectCallParameter(varIndex))
/* 2466:     */         {
/* 2467:3943 */           int dcp_register = this.varRegisters[varIndex];
/* 2468:3944 */           this.cfw.addALoad(dcp_register);
/* 2469:3945 */           this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/* 2470:     */           
/* 2471:3947 */           int isNumberLabel = this.cfw.acquireLabel();
/* 2472:3948 */           this.cfw.add(165, isNumberLabel);
/* 2473:3949 */           short stack = this.cfw.getStackTop();
/* 2474:3950 */           this.cfw.addALoad(dcp_register);
/* 2475:3951 */           addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)Ljava/lang/String;");
/* 2476:     */           
/* 2477:     */ 
/* 2478:3954 */           int beyond = this.cfw.acquireLabel();
/* 2479:3955 */           this.cfw.add(167, beyond);
/* 2480:3956 */           this.cfw.markLabel(isNumberLabel, stack);
/* 2481:3957 */           this.cfw.addPush("number");
/* 2482:3958 */           this.cfw.markLabel(beyond);
/* 2483:     */         }
/* 2484:     */         else
/* 2485:     */         {
/* 2486:3960 */           this.cfw.addALoad(this.varRegisters[varIndex]);
/* 2487:3961 */           addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)Ljava/lang/String;");
/* 2488:     */         }
/* 2489:3965 */         return;
/* 2490:     */       }
/* 2491:     */     }
/* 2492:3968 */     this.cfw.addALoad(this.variableObjectLocal);
/* 2493:3969 */     this.cfw.addPush(node.getString());
/* 2494:3970 */     addScriptRuntimeInvoke("typeofName", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/String;");
/* 2495:     */   }
/* 2496:     */   
/* 2497:     */   private void saveCurrentCodeOffset()
/* 2498:     */   {
/* 2499:3982 */     this.savedCodeOffset = this.cfw.getCurrentCodeOffset();
/* 2500:     */   }
/* 2501:     */   
/* 2502:     */   private void addInstructionCount()
/* 2503:     */   {
/* 2504:3992 */     int count = this.cfw.getCurrentCodeOffset() - this.savedCodeOffset;
/* 2505:     */     
/* 2506:     */ 
/* 2507:     */ 
/* 2508:3996 */     addInstructionCount(Math.max(count, 1));
/* 2509:     */   }
/* 2510:     */   
/* 2511:     */   private void addInstructionCount(int count)
/* 2512:     */   {
/* 2513:4008 */     this.cfw.addALoad(this.contextLocal);
/* 2514:4009 */     this.cfw.addPush(count);
/* 2515:4010 */     addScriptRuntimeInvoke("addInstructionCount", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)V");
/* 2516:     */   }
/* 2517:     */   
/* 2518:     */   private void visitIncDec(Node node)
/* 2519:     */   {
/* 2520:4017 */     int incrDecrMask = node.getExistingIntProp(13);
/* 2521:4018 */     Node child = node.getFirstChild();
/* 2522:4019 */     switch (child.getType())
/* 2523:     */     {
/* 2524:     */     case 55: 
/* 2525:4021 */       if (!this.hasVarsInRegs) {
/* 2526:4021 */         Kit.codeBug();
/* 2527:     */       }
/* 2528:4022 */       if (node.getIntProp(8, -1) != -1)
/* 2529:     */       {
/* 2530:4023 */         boolean post = (incrDecrMask & 0x2) != 0;
/* 2531:4024 */         int varIndex = this.fnCurrent.getVarIndex(child);
/* 2532:4025 */         short reg = this.varRegisters[varIndex];
/* 2533:4026 */         int offset = varIsDirectCallParameter(varIndex) ? 1 : 0;
/* 2534:4027 */         this.cfw.addDLoad(reg + offset);
/* 2535:4028 */         if (post) {
/* 2536:4029 */           this.cfw.add(92);
/* 2537:     */         }
/* 2538:4031 */         this.cfw.addPush(1.0D);
/* 2539:4032 */         if ((incrDecrMask & 0x1) == 0) {
/* 2540:4033 */           this.cfw.add(99);
/* 2541:     */         } else {
/* 2542:4035 */           this.cfw.add(103);
/* 2543:     */         }
/* 2544:4037 */         if (!post) {
/* 2545:4038 */           this.cfw.add(92);
/* 2546:     */         }
/* 2547:4040 */         this.cfw.addDStore(reg + offset);
/* 2548:     */       }
/* 2549:     */       else
/* 2550:     */       {
/* 2551:4042 */         boolean post = (incrDecrMask & 0x2) != 0;
/* 2552:4043 */         int varIndex = this.fnCurrent.getVarIndex(child);
/* 2553:4044 */         short reg = this.varRegisters[varIndex];
/* 2554:4045 */         this.cfw.addALoad(reg);
/* 2555:4046 */         if (post) {
/* 2556:4047 */           this.cfw.add(89);
/* 2557:     */         }
/* 2558:4049 */         addObjectToDouble();
/* 2559:4050 */         this.cfw.addPush(1.0D);
/* 2560:4051 */         if ((incrDecrMask & 0x1) == 0) {
/* 2561:4052 */           this.cfw.add(99);
/* 2562:     */         } else {
/* 2563:4054 */           this.cfw.add(103);
/* 2564:     */         }
/* 2565:4056 */         addDoubleWrap();
/* 2566:4057 */         if (!post) {
/* 2567:4058 */           this.cfw.add(89);
/* 2568:     */         }
/* 2569:4060 */         this.cfw.addAStore(reg);
/* 2570:     */       }
/* 2571:4061 */       break;
/* 2572:     */     case 39: 
/* 2573:4065 */       this.cfw.addALoad(this.variableObjectLocal);
/* 2574:4066 */       this.cfw.addPush(child.getString());
/* 2575:4067 */       this.cfw.addALoad(this.contextLocal);
/* 2576:4068 */       this.cfw.addPush(incrDecrMask);
/* 2577:4069 */       addScriptRuntimeInvoke("nameIncrDecr", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)Ljava/lang/Object;");
/* 2578:     */       
/* 2579:     */ 
/* 2580:     */ 
/* 2581:     */ 
/* 2582:4074 */       break;
/* 2583:     */     case 34: 
/* 2584:4076 */       throw Kit.codeBug();
/* 2585:     */     case 33: 
/* 2586:4078 */       Node getPropChild = child.getFirstChild();
/* 2587:4079 */       generateExpression(getPropChild, node);
/* 2588:4080 */       generateExpression(getPropChild.getNext(), node);
/* 2589:4081 */       this.cfw.addALoad(this.contextLocal);
/* 2590:4082 */       this.cfw.addPush(incrDecrMask);
/* 2591:4083 */       addScriptRuntimeInvoke("propIncrDecr", "(Ljava/lang/Object;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)Ljava/lang/Object;");
/* 2592:     */       
/* 2593:     */ 
/* 2594:     */ 
/* 2595:     */ 
/* 2596:4088 */       break;
/* 2597:     */     case 36: 
/* 2598:4091 */       Node elemChild = child.getFirstChild();
/* 2599:4092 */       generateExpression(elemChild, node);
/* 2600:4093 */       generateExpression(elemChild.getNext(), node);
/* 2601:4094 */       this.cfw.addALoad(this.contextLocal);
/* 2602:4095 */       this.cfw.addPush(incrDecrMask);
/* 2603:4096 */       if (elemChild.getNext().getIntProp(8, -1) != -1) {
/* 2604:4097 */         addOptRuntimeInvoke("elemIncrDecr", "(Ljava/lang/Object;DLnet/sourceforge/htmlunit/corejs/javascript/Context;I)Ljava/lang/Object;");
/* 2605:     */       } else {
/* 2606:4104 */         addScriptRuntimeInvoke("elemIncrDecr", "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)Ljava/lang/Object;");
/* 2607:     */       }
/* 2608:4111 */       break;
/* 2609:     */     case 67: 
/* 2610:4114 */       Node refChild = child.getFirstChild();
/* 2611:4115 */       generateExpression(refChild, node);
/* 2612:4116 */       this.cfw.addALoad(this.contextLocal);
/* 2613:4117 */       this.cfw.addPush(incrDecrMask);
/* 2614:4118 */       addScriptRuntimeInvoke("refIncrDecr", "(Lnet/sourceforge/htmlunit/corejs/javascript/Ref;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)Ljava/lang/Object;");
/* 2615:     */       
/* 2616:     */ 
/* 2617:     */ 
/* 2618:     */ 
/* 2619:4123 */       break;
/* 2620:     */     default: 
/* 2621:4126 */       Codegen.badTree();
/* 2622:     */     }
/* 2623:     */   }
/* 2624:     */   
/* 2625:     */   private static boolean isArithmeticNode(Node node)
/* 2626:     */   {
/* 2627:4132 */     int type = node.getType();
/* 2628:4133 */     return (type == 22) || (type == 25) || (type == 24) || (type == 23);
/* 2629:     */   }
/* 2630:     */   
/* 2631:     */   private void visitArithmetic(Node node, int opCode, Node child, Node parent)
/* 2632:     */   {
/* 2633:4142 */     int childNumberFlag = node.getIntProp(8, -1);
/* 2634:4143 */     if (childNumberFlag != -1)
/* 2635:     */     {
/* 2636:4144 */       generateExpression(child, node);
/* 2637:4145 */       generateExpression(child.getNext(), node);
/* 2638:4146 */       this.cfw.add(opCode);
/* 2639:     */     }
/* 2640:     */     else
/* 2641:     */     {
/* 2642:4149 */       boolean childOfArithmetic = isArithmeticNode(parent);
/* 2643:4150 */       generateExpression(child, node);
/* 2644:4151 */       if (!isArithmeticNode(child)) {
/* 2645:4152 */         addObjectToDouble();
/* 2646:     */       }
/* 2647:4153 */       generateExpression(child.getNext(), node);
/* 2648:4154 */       if (!isArithmeticNode(child.getNext())) {
/* 2649:4155 */         addObjectToDouble();
/* 2650:     */       }
/* 2651:4156 */       this.cfw.add(opCode);
/* 2652:4157 */       if (!childOfArithmetic) {
/* 2653:4158 */         addDoubleWrap();
/* 2654:     */       }
/* 2655:     */     }
/* 2656:     */   }
/* 2657:     */   
/* 2658:     */   private void visitBitOp(Node node, int type, Node child)
/* 2659:     */   {
/* 2660:4165 */     int childNumberFlag = node.getIntProp(8, -1);
/* 2661:4166 */     generateExpression(child, node);
/* 2662:4171 */     if (type == 20)
/* 2663:     */     {
/* 2664:4172 */       addScriptRuntimeInvoke("toUint32", "(Ljava/lang/Object;)J");
/* 2665:4173 */       generateExpression(child.getNext(), node);
/* 2666:4174 */       addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
/* 2667:     */       
/* 2668:     */ 
/* 2669:4177 */       this.cfw.addPush(31);
/* 2670:4178 */       this.cfw.add(126);
/* 2671:4179 */       this.cfw.add(125);
/* 2672:4180 */       this.cfw.add(138);
/* 2673:4181 */       addDoubleWrap();
/* 2674:4182 */       return;
/* 2675:     */     }
/* 2676:4184 */     if (childNumberFlag == -1)
/* 2677:     */     {
/* 2678:4185 */       addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
/* 2679:4186 */       generateExpression(child.getNext(), node);
/* 2680:4187 */       addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
/* 2681:     */     }
/* 2682:     */     else
/* 2683:     */     {
/* 2684:4190 */       addScriptRuntimeInvoke("toInt32", "(D)I");
/* 2685:4191 */       generateExpression(child.getNext(), node);
/* 2686:4192 */       addScriptRuntimeInvoke("toInt32", "(D)I");
/* 2687:     */     }
/* 2688:4194 */     switch (type)
/* 2689:     */     {
/* 2690:     */     case 9: 
/* 2691:4196 */       this.cfw.add(128);
/* 2692:4197 */       break;
/* 2693:     */     case 10: 
/* 2694:4199 */       this.cfw.add(130);
/* 2695:4200 */       break;
/* 2696:     */     case 11: 
/* 2697:4202 */       this.cfw.add(126);
/* 2698:4203 */       break;
/* 2699:     */     case 19: 
/* 2700:4205 */       this.cfw.add(122);
/* 2701:4206 */       break;
/* 2702:     */     case 18: 
/* 2703:4208 */       this.cfw.add(120);
/* 2704:4209 */       break;
/* 2705:     */     case 12: 
/* 2706:     */     case 13: 
/* 2707:     */     case 14: 
/* 2708:     */     case 15: 
/* 2709:     */     case 16: 
/* 2710:     */     case 17: 
/* 2711:     */     default: 
/* 2712:4211 */       throw Codegen.badTree();
/* 2713:     */     }
/* 2714:4213 */     this.cfw.add(135);
/* 2715:4214 */     if (childNumberFlag == -1) {
/* 2716:4215 */       addDoubleWrap();
/* 2717:     */     }
/* 2718:     */   }
/* 2719:     */   
/* 2720:     */   private int nodeIsDirectCallParameter(Node node)
/* 2721:     */   {
/* 2722:4221 */     if ((node.getType() == 55) && (this.inDirectCallFunction) && (!this.itsForcedObjectParameters))
/* 2723:     */     {
/* 2724:4224 */       int varIndex = this.fnCurrent.getVarIndex(node);
/* 2725:4225 */       if (this.fnCurrent.isParameter(varIndex)) {
/* 2726:4226 */         return this.varRegisters[varIndex];
/* 2727:     */       }
/* 2728:     */     }
/* 2729:4229 */     return -1;
/* 2730:     */   }
/* 2731:     */   
/* 2732:     */   private boolean varIsDirectCallParameter(int varIndex)
/* 2733:     */   {
/* 2734:4234 */     return (this.fnCurrent.isParameter(varIndex)) && (this.inDirectCallFunction) && (!this.itsForcedObjectParameters);
/* 2735:     */   }
/* 2736:     */   
/* 2737:     */   private void genSimpleCompare(int type, int trueGOTO, int falseGOTO)
/* 2738:     */   {
/* 2739:4240 */     if (trueGOTO == -1) {
/* 2740:4240 */       throw Codegen.badTree();
/* 2741:     */     }
/* 2742:4241 */     switch (type)
/* 2743:     */     {
/* 2744:     */     case 15: 
/* 2745:4243 */       this.cfw.add(152);
/* 2746:4244 */       this.cfw.add(158, trueGOTO);
/* 2747:4245 */       break;
/* 2748:     */     case 17: 
/* 2749:4247 */       this.cfw.add(151);
/* 2750:4248 */       this.cfw.add(156, trueGOTO);
/* 2751:4249 */       break;
/* 2752:     */     case 14: 
/* 2753:4251 */       this.cfw.add(152);
/* 2754:4252 */       this.cfw.add(155, trueGOTO);
/* 2755:4253 */       break;
/* 2756:     */     case 16: 
/* 2757:4255 */       this.cfw.add(151);
/* 2758:4256 */       this.cfw.add(157, trueGOTO);
/* 2759:4257 */       break;
/* 2760:     */     default: 
/* 2761:4259 */       throw Codegen.badTree();
/* 2762:     */     }
/* 2763:4262 */     if (falseGOTO != -1) {
/* 2764:4263 */       this.cfw.add(167, falseGOTO);
/* 2765:     */     }
/* 2766:     */   }
/* 2767:     */   
/* 2768:     */   private void visitIfJumpRelOp(Node node, Node child, int trueGOTO, int falseGOTO)
/* 2769:     */   {
/* 2770:4269 */     if ((trueGOTO == -1) || (falseGOTO == -1)) {
/* 2771:4269 */       throw Codegen.badTree();
/* 2772:     */     }
/* 2773:4270 */     int type = node.getType();
/* 2774:4271 */     Node rChild = child.getNext();
/* 2775:4272 */     if ((type == 53) || (type == 52))
/* 2776:     */     {
/* 2777:4273 */       generateExpression(child, node);
/* 2778:4274 */       generateExpression(rChild, node);
/* 2779:4275 */       this.cfw.addALoad(this.contextLocal);
/* 2780:4276 */       addScriptRuntimeInvoke(type == 53 ? "instanceOf" : "in", "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Z");
/* 2781:     */       
/* 2782:     */ 
/* 2783:     */ 
/* 2784:     */ 
/* 2785:     */ 
/* 2786:4282 */       this.cfw.add(154, trueGOTO);
/* 2787:4283 */       this.cfw.add(167, falseGOTO);
/* 2788:4284 */       return;
/* 2789:     */     }
/* 2790:4286 */     int childNumberFlag = node.getIntProp(8, -1);
/* 2791:4287 */     int left_dcp_register = nodeIsDirectCallParameter(child);
/* 2792:4288 */     int right_dcp_register = nodeIsDirectCallParameter(rChild);
/* 2793:4289 */     if (childNumberFlag != -1)
/* 2794:     */     {
/* 2795:4293 */       if (childNumberFlag != 2)
/* 2796:     */       {
/* 2797:4295 */         generateExpression(child, node);
/* 2798:     */       }
/* 2799:4296 */       else if (left_dcp_register != -1)
/* 2800:     */       {
/* 2801:4297 */         dcpLoadAsNumber(left_dcp_register);
/* 2802:     */       }
/* 2803:     */       else
/* 2804:     */       {
/* 2805:4299 */         generateExpression(child, node);
/* 2806:4300 */         addObjectToDouble();
/* 2807:     */       }
/* 2808:4303 */       if (childNumberFlag != 1)
/* 2809:     */       {
/* 2810:4305 */         generateExpression(rChild, node);
/* 2811:     */       }
/* 2812:4306 */       else if (right_dcp_register != -1)
/* 2813:     */       {
/* 2814:4307 */         dcpLoadAsNumber(right_dcp_register);
/* 2815:     */       }
/* 2816:     */       else
/* 2817:     */       {
/* 2818:4309 */         generateExpression(rChild, node);
/* 2819:4310 */         addObjectToDouble();
/* 2820:     */       }
/* 2821:4313 */       genSimpleCompare(type, trueGOTO, falseGOTO);
/* 2822:     */     }
/* 2823:     */     else
/* 2824:     */     {
/* 2825:4316 */       if ((left_dcp_register != -1) && (right_dcp_register != -1))
/* 2826:     */       {
/* 2827:4319 */         short stack = this.cfw.getStackTop();
/* 2828:4320 */         int leftIsNotNumber = this.cfw.acquireLabel();
/* 2829:4321 */         this.cfw.addALoad(left_dcp_register);
/* 2830:4322 */         this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/* 2831:     */         
/* 2832:     */ 
/* 2833:     */ 
/* 2834:4326 */         this.cfw.add(166, leftIsNotNumber);
/* 2835:4327 */         this.cfw.addDLoad(left_dcp_register + 1);
/* 2836:4328 */         dcpLoadAsNumber(right_dcp_register);
/* 2837:4329 */         genSimpleCompare(type, trueGOTO, falseGOTO);
/* 2838:4330 */         if (stack != this.cfw.getStackTop()) {
/* 2839:4330 */           throw Codegen.badTree();
/* 2840:     */         }
/* 2841:4332 */         this.cfw.markLabel(leftIsNotNumber);
/* 2842:4333 */         int rightIsNotNumber = this.cfw.acquireLabel();
/* 2843:4334 */         this.cfw.addALoad(right_dcp_register);
/* 2844:4335 */         this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/* 2845:     */         
/* 2846:     */ 
/* 2847:     */ 
/* 2848:4339 */         this.cfw.add(166, rightIsNotNumber);
/* 2849:4340 */         this.cfw.addALoad(left_dcp_register);
/* 2850:4341 */         addObjectToDouble();
/* 2851:4342 */         this.cfw.addDLoad(right_dcp_register + 1);
/* 2852:4343 */         genSimpleCompare(type, trueGOTO, falseGOTO);
/* 2853:4344 */         if (stack != this.cfw.getStackTop()) {
/* 2854:4344 */           throw Codegen.badTree();
/* 2855:     */         }
/* 2856:4346 */         this.cfw.markLabel(rightIsNotNumber);
/* 2857:     */         
/* 2858:4348 */         this.cfw.addALoad(left_dcp_register);
/* 2859:4349 */         this.cfw.addALoad(right_dcp_register);
/* 2860:     */       }
/* 2861:     */       else
/* 2862:     */       {
/* 2863:4352 */         generateExpression(child, node);
/* 2864:4353 */         generateExpression(rChild, node);
/* 2865:     */       }
/* 2866:4356 */       if ((type == 17) || (type == 16)) {
/* 2867:4357 */         this.cfw.add(95);
/* 2868:     */       }
/* 2869:4359 */       String routine = (type == 14) || (type == 16) ? "cmp_LT" : "cmp_LE";
/* 2870:     */       
/* 2871:4361 */       addScriptRuntimeInvoke(routine, "(Ljava/lang/Object;Ljava/lang/Object;)Z");
/* 2872:     */       
/* 2873:     */ 
/* 2874:     */ 
/* 2875:4365 */       this.cfw.add(154, trueGOTO);
/* 2876:4366 */       this.cfw.add(167, falseGOTO);
/* 2877:     */     }
/* 2878:     */   }
/* 2879:     */   
/* 2880:     */   private void visitIfJumpEqOp(Node node, Node child, int trueGOTO, int falseGOTO)
/* 2881:     */   {
/* 2882:4373 */     if ((trueGOTO == -1) || (falseGOTO == -1)) {
/* 2883:4373 */       throw Codegen.badTree();
/* 2884:     */     }
/* 2885:4375 */     short stackInitial = this.cfw.getStackTop();
/* 2886:4376 */     int type = node.getType();
/* 2887:4377 */     Node rChild = child.getNext();
/* 2888:4380 */     if ((child.getType() == 42) || (rChild.getType() == 42))
/* 2889:     */     {
/* 2890:4382 */       if (child.getType() == 42) {
/* 2891:4383 */         child = rChild;
/* 2892:     */       }
/* 2893:4385 */       generateExpression(child, node);
/* 2894:4386 */       if ((type == 46) || (type == 47))
/* 2895:     */       {
/* 2896:4387 */         int testCode = type == 46 ? 198 : 199;
/* 2897:     */         
/* 2898:4389 */         this.cfw.add(testCode, trueGOTO);
/* 2899:     */       }
/* 2900:     */       else
/* 2901:     */       {
/* 2902:4391 */         if (type != 12)
/* 2903:     */         {
/* 2904:4393 */           if (type != 13) {
/* 2905:4393 */             throw Codegen.badTree();
/* 2906:     */           }
/* 2907:4394 */           int tmp = trueGOTO;
/* 2908:4395 */           trueGOTO = falseGOTO;
/* 2909:4396 */           falseGOTO = tmp;
/* 2910:     */         }
/* 2911:4398 */         this.cfw.add(89);
/* 2912:4399 */         int undefCheckLabel = this.cfw.acquireLabel();
/* 2913:4400 */         this.cfw.add(199, undefCheckLabel);
/* 2914:4401 */         short stack = this.cfw.getStackTop();
/* 2915:4402 */         this.cfw.add(87);
/* 2916:4403 */         this.cfw.add(167, trueGOTO);
/* 2917:4404 */         this.cfw.markLabel(undefCheckLabel, stack);
/* 2918:4405 */         Codegen.pushUndefined(this.cfw);
/* 2919:4406 */         this.cfw.add(165, trueGOTO);
/* 2920:     */       }
/* 2921:4408 */       this.cfw.add(167, falseGOTO);
/* 2922:     */     }
/* 2923:     */     else
/* 2924:     */     {
/* 2925:4410 */       int child_dcp_register = nodeIsDirectCallParameter(child);
/* 2926:4411 */       if ((child_dcp_register != -1) && (rChild.getType() == 149))
/* 2927:     */       {
/* 2928:4414 */         Node convertChild = rChild.getFirstChild();
/* 2929:4415 */         if (convertChild.getType() == 40)
/* 2930:     */         {
/* 2931:4416 */           this.cfw.addALoad(child_dcp_register);
/* 2932:4417 */           this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/* 2933:     */           
/* 2934:     */ 
/* 2935:     */ 
/* 2936:4421 */           int notNumbersLabel = this.cfw.acquireLabel();
/* 2937:4422 */           this.cfw.add(166, notNumbersLabel);
/* 2938:4423 */           this.cfw.addDLoad(child_dcp_register + 1);
/* 2939:4424 */           this.cfw.addPush(convertChild.getDouble());
/* 2940:4425 */           this.cfw.add(151);
/* 2941:4426 */           if (type == 12) {
/* 2942:4427 */             this.cfw.add(153, trueGOTO);
/* 2943:     */           } else {
/* 2944:4429 */             this.cfw.add(154, trueGOTO);
/* 2945:     */           }
/* 2946:4430 */           this.cfw.add(167, falseGOTO);
/* 2947:4431 */           this.cfw.markLabel(notNumbersLabel);
/* 2948:     */         }
/* 2949:     */       }
/* 2950:4436 */       generateExpression(child, node);
/* 2951:4437 */       generateExpression(rChild, node);
/* 2952:     */       String name;
/* 2953:     */       int testCode;
/* 2954:4441 */       switch (type)
/* 2955:     */       {
/* 2956:     */       case 12: 
/* 2957:4443 */         name = "eq";
/* 2958:4444 */         testCode = 154;
/* 2959:4445 */         break;
/* 2960:     */       case 13: 
/* 2961:4447 */         name = "eq";
/* 2962:4448 */         testCode = 153;
/* 2963:4449 */         break;
/* 2964:     */       case 46: 
/* 2965:4451 */         name = "shallowEq";
/* 2966:4452 */         testCode = 154;
/* 2967:4453 */         break;
/* 2968:     */       case 47: 
/* 2969:4455 */         name = "shallowEq";
/* 2970:4456 */         testCode = 153;
/* 2971:4457 */         break;
/* 2972:     */       default: 
/* 2973:4459 */         throw Codegen.badTree();
/* 2974:     */       }
/* 2975:4461 */       addScriptRuntimeInvoke(name, "(Ljava/lang/Object;Ljava/lang/Object;)Z");
/* 2976:     */       
/* 2977:     */ 
/* 2978:     */ 
/* 2979:4465 */       this.cfw.add(testCode, trueGOTO);
/* 2980:4466 */       this.cfw.add(167, falseGOTO);
/* 2981:     */     }
/* 2982:4468 */     if (stackInitial != this.cfw.getStackTop()) {
/* 2983:4468 */       throw Codegen.badTree();
/* 2984:     */     }
/* 2985:     */   }
/* 2986:     */   
/* 2987:     */   private void visitSetName(Node node, Node child)
/* 2988:     */   {
/* 2989:4473 */     String name = node.getFirstChild().getString();
/* 2990:4474 */     while (child != null)
/* 2991:     */     {
/* 2992:4475 */       generateExpression(child, node);
/* 2993:4476 */       child = child.getNext();
/* 2994:     */     }
/* 2995:4478 */     this.cfw.addALoad(this.contextLocal);
/* 2996:4479 */     this.cfw.addALoad(this.variableObjectLocal);
/* 2997:4480 */     this.cfw.addPush(name);
/* 2998:4481 */     addScriptRuntimeInvoke("setName", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/Object;");
/* 2999:     */   }
/* 3000:     */   
/* 3001:     */   private void visitStrictSetName(Node node, Node child)
/* 3002:     */   {
/* 3003:4493 */     String name = node.getFirstChild().getString();
/* 3004:4494 */     while (child != null)
/* 3005:     */     {
/* 3006:4495 */       generateExpression(child, node);
/* 3007:4496 */       child = child.getNext();
/* 3008:     */     }
/* 3009:4498 */     this.cfw.addALoad(this.contextLocal);
/* 3010:4499 */     this.cfw.addALoad(this.variableObjectLocal);
/* 3011:4500 */     this.cfw.addPush(name);
/* 3012:4501 */     addScriptRuntimeInvoke("strictSetName", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/Object;");
/* 3013:     */   }
/* 3014:     */   
/* 3015:     */   private void visitSetConst(Node node, Node child)
/* 3016:     */   {
/* 3017:4513 */     String name = node.getFirstChild().getString();
/* 3018:4514 */     while (child != null)
/* 3019:     */     {
/* 3020:4515 */       generateExpression(child, node);
/* 3021:4516 */       child = child.getNext();
/* 3022:     */     }
/* 3023:4518 */     this.cfw.addALoad(this.contextLocal);
/* 3024:4519 */     this.cfw.addPush(name);
/* 3025:4520 */     addScriptRuntimeInvoke("setConst", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Ljava/lang/String;)Ljava/lang/Object;");
/* 3026:     */   }
/* 3027:     */   
/* 3028:     */   private void visitGetVar(Node node)
/* 3029:     */   {
/* 3030:4531 */     if (!this.hasVarsInRegs) {
/* 3031:4531 */       Kit.codeBug();
/* 3032:     */     }
/* 3033:4532 */     int varIndex = this.fnCurrent.getVarIndex(node);
/* 3034:4533 */     short reg = this.varRegisters[varIndex];
/* 3035:4534 */     if (varIsDirectCallParameter(varIndex))
/* 3036:     */     {
/* 3037:4539 */       if (node.getIntProp(8, -1) != -1) {
/* 3038:4540 */         dcpLoadAsNumber(reg);
/* 3039:     */       } else {
/* 3040:4542 */         dcpLoadAsObject(reg);
/* 3041:     */       }
/* 3042:     */     }
/* 3043:4544 */     else if (this.fnCurrent.isNumberVar(varIndex)) {
/* 3044:4545 */       this.cfw.addDLoad(reg);
/* 3045:     */     } else {
/* 3046:4547 */       this.cfw.addALoad(reg);
/* 3047:     */     }
/* 3048:     */   }
/* 3049:     */   
/* 3050:     */   private void visitSetVar(Node node, Node child, boolean needValue)
/* 3051:     */   {
/* 3052:4553 */     if (!this.hasVarsInRegs) {
/* 3053:4553 */       Kit.codeBug();
/* 3054:     */     }
/* 3055:4554 */     int varIndex = this.fnCurrent.getVarIndex(node);
/* 3056:4555 */     generateExpression(child.getNext(), node);
/* 3057:4556 */     boolean isNumber = node.getIntProp(8, -1) != -1;
/* 3058:4557 */     short reg = this.varRegisters[varIndex];
/* 3059:4558 */     boolean[] constDeclarations = this.fnCurrent.fnode.getParamAndVarConst();
/* 3060:4559 */     if (constDeclarations[varIndex] != 0)
/* 3061:     */     {
/* 3062:4560 */       if (!needValue) {
/* 3063:4561 */         if (isNumber) {
/* 3064:4562 */           this.cfw.add(88);
/* 3065:     */         } else {
/* 3066:4564 */           this.cfw.add(87);
/* 3067:     */         }
/* 3068:     */       }
/* 3069:     */     }
/* 3070:4567 */     else if (varIsDirectCallParameter(varIndex))
/* 3071:     */     {
/* 3072:4568 */       if (isNumber)
/* 3073:     */       {
/* 3074:4569 */         if (needValue) {
/* 3075:4569 */           this.cfw.add(92);
/* 3076:     */         }
/* 3077:4570 */         this.cfw.addALoad(reg);
/* 3078:4571 */         this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/* 3079:     */         
/* 3080:     */ 
/* 3081:     */ 
/* 3082:4575 */         int isNumberLabel = this.cfw.acquireLabel();
/* 3083:4576 */         int beyond = this.cfw.acquireLabel();
/* 3084:4577 */         this.cfw.add(165, isNumberLabel);
/* 3085:4578 */         short stack = this.cfw.getStackTop();
/* 3086:4579 */         addDoubleWrap();
/* 3087:4580 */         this.cfw.addAStore(reg);
/* 3088:4581 */         this.cfw.add(167, beyond);
/* 3089:4582 */         this.cfw.markLabel(isNumberLabel, stack);
/* 3090:4583 */         this.cfw.addDStore(reg + 1);
/* 3091:4584 */         this.cfw.markLabel(beyond);
/* 3092:     */       }
/* 3093:     */       else
/* 3094:     */       {
/* 3095:4587 */         if (needValue) {
/* 3096:4587 */           this.cfw.add(89);
/* 3097:     */         }
/* 3098:4588 */         this.cfw.addAStore(reg);
/* 3099:     */       }
/* 3100:     */     }
/* 3101:     */     else
/* 3102:     */     {
/* 3103:4591 */       boolean isNumberVar = this.fnCurrent.isNumberVar(varIndex);
/* 3104:4592 */       if (isNumber)
/* 3105:     */       {
/* 3106:4593 */         if (isNumberVar)
/* 3107:     */         {
/* 3108:4594 */           this.cfw.addDStore(reg);
/* 3109:4595 */           if (needValue) {
/* 3110:4595 */             this.cfw.addDLoad(reg);
/* 3111:     */           }
/* 3112:     */         }
/* 3113:     */         else
/* 3114:     */         {
/* 3115:4597 */           if (needValue) {
/* 3116:4597 */             this.cfw.add(92);
/* 3117:     */           }
/* 3118:4600 */           addDoubleWrap();
/* 3119:4601 */           this.cfw.addAStore(reg);
/* 3120:     */         }
/* 3121:     */       }
/* 3122:     */       else
/* 3123:     */       {
/* 3124:4604 */         if (isNumberVar) {
/* 3125:4604 */           Kit.codeBug();
/* 3126:     */         }
/* 3127:4605 */         this.cfw.addAStore(reg);
/* 3128:4606 */         if (needValue) {
/* 3129:4606 */           this.cfw.addALoad(reg);
/* 3130:     */         }
/* 3131:     */       }
/* 3132:     */     }
/* 3133:     */   }
/* 3134:     */   
/* 3135:     */   private void visitSetConstVar(Node node, Node child, boolean needValue)
/* 3136:     */   {
/* 3137:4613 */     if (!this.hasVarsInRegs) {
/* 3138:4613 */       Kit.codeBug();
/* 3139:     */     }
/* 3140:4614 */     int varIndex = this.fnCurrent.getVarIndex(node);
/* 3141:4615 */     generateExpression(child.getNext(), node);
/* 3142:4616 */     boolean isNumber = node.getIntProp(8, -1) != -1;
/* 3143:4617 */     short reg = this.varRegisters[varIndex];
/* 3144:4618 */     int beyond = this.cfw.acquireLabel();
/* 3145:4619 */     int noAssign = this.cfw.acquireLabel();
/* 3146:4620 */     if (isNumber)
/* 3147:     */     {
/* 3148:4621 */       this.cfw.addILoad(reg + 2);
/* 3149:4622 */       this.cfw.add(154, noAssign);
/* 3150:4623 */       short stack = this.cfw.getStackTop();
/* 3151:4624 */       this.cfw.addPush(1);
/* 3152:4625 */       this.cfw.addIStore(reg + 2);
/* 3153:4626 */       this.cfw.addDStore(reg);
/* 3154:4627 */       if (needValue)
/* 3155:     */       {
/* 3156:4628 */         this.cfw.addDLoad(reg);
/* 3157:4629 */         this.cfw.markLabel(noAssign, stack);
/* 3158:     */       }
/* 3159:     */       else
/* 3160:     */       {
/* 3161:4631 */         this.cfw.add(167, beyond);
/* 3162:4632 */         this.cfw.markLabel(noAssign, stack);
/* 3163:4633 */         this.cfw.add(88);
/* 3164:     */       }
/* 3165:     */     }
/* 3166:     */     else
/* 3167:     */     {
/* 3168:4637 */       this.cfw.addILoad(reg + 1);
/* 3169:4638 */       this.cfw.add(154, noAssign);
/* 3170:4639 */       short stack = this.cfw.getStackTop();
/* 3171:4640 */       this.cfw.addPush(1);
/* 3172:4641 */       this.cfw.addIStore(reg + 1);
/* 3173:4642 */       this.cfw.addAStore(reg);
/* 3174:4643 */       if (needValue)
/* 3175:     */       {
/* 3176:4644 */         this.cfw.addALoad(reg);
/* 3177:4645 */         this.cfw.markLabel(noAssign, stack);
/* 3178:     */       }
/* 3179:     */       else
/* 3180:     */       {
/* 3181:4647 */         this.cfw.add(167, beyond);
/* 3182:4648 */         this.cfw.markLabel(noAssign, stack);
/* 3183:4649 */         this.cfw.add(87);
/* 3184:     */       }
/* 3185:     */     }
/* 3186:4652 */     this.cfw.markLabel(beyond);
/* 3187:     */   }
/* 3188:     */   
/* 3189:     */   private void visitGetProp(Node node, Node child)
/* 3190:     */   {
/* 3191:4657 */     generateExpression(child, node);
/* 3192:4658 */     Node nameChild = child.getNext();
/* 3193:4659 */     generateExpression(nameChild, node);
/* 3194:4660 */     if (node.getType() == 34)
/* 3195:     */     {
/* 3196:4661 */       this.cfw.addALoad(this.contextLocal);
/* 3197:4662 */       addScriptRuntimeInvoke("getObjectPropNoWarn", "(Ljava/lang/Object;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3198:     */       
/* 3199:     */ 
/* 3200:     */ 
/* 3201:     */ 
/* 3202:     */ 
/* 3203:4668 */       return;
/* 3204:     */     }
/* 3205:4674 */     int childType = child.getType();
/* 3206:4675 */     if ((childType == 43) && (nameChild.getType() == 41))
/* 3207:     */     {
/* 3208:4676 */       this.cfw.addALoad(this.contextLocal);
/* 3209:4677 */       addScriptRuntimeInvoke("getObjectProp", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3210:     */     }
/* 3211:     */     else
/* 3212:     */     {
/* 3213:4684 */       this.cfw.addALoad(this.contextLocal);
/* 3214:4685 */       this.cfw.addALoad(this.variableObjectLocal);
/* 3215:4686 */       addScriptRuntimeInvoke("getObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;");
/* 3216:     */     }
/* 3217:     */   }
/* 3218:     */   
/* 3219:     */   private void visitSetProp(int type, Node node, Node child)
/* 3220:     */   {
/* 3221:4698 */     Node objectChild = child;
/* 3222:4699 */     generateExpression(child, node);
/* 3223:4700 */     child = child.getNext();
/* 3224:4701 */     if (type == 139) {
/* 3225:4702 */       this.cfw.add(89);
/* 3226:     */     }
/* 3227:4704 */     Node nameChild = child;
/* 3228:4705 */     generateExpression(child, node);
/* 3229:4706 */     child = child.getNext();
/* 3230:4707 */     if (type == 139)
/* 3231:     */     {
/* 3232:4709 */       this.cfw.add(90);
/* 3233:4712 */       if ((objectChild.getType() == 43) && (nameChild.getType() == 41))
/* 3234:     */       {
/* 3235:4715 */         this.cfw.addALoad(this.contextLocal);
/* 3236:4716 */         addScriptRuntimeInvoke("getObjectProp", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3237:     */       }
/* 3238:     */       else
/* 3239:     */       {
/* 3240:4723 */         this.cfw.addALoad(this.contextLocal);
/* 3241:4724 */         addScriptRuntimeInvoke("getObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3242:     */       }
/* 3243:     */     }
/* 3244:4732 */     generateExpression(child, node);
/* 3245:4733 */     this.cfw.addALoad(this.contextLocal);
/* 3246:4734 */     addScriptRuntimeInvoke("setObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3247:     */   }
/* 3248:     */   
/* 3249:     */   private void visitSetElem(int type, Node node, Node child)
/* 3250:     */   {
/* 3251:4745 */     generateExpression(child, node);
/* 3252:4746 */     child = child.getNext();
/* 3253:4747 */     if (type == 140) {
/* 3254:4748 */       this.cfw.add(89);
/* 3255:     */     }
/* 3256:4750 */     generateExpression(child, node);
/* 3257:4751 */     child = child.getNext();
/* 3258:4752 */     boolean indexIsNumber = node.getIntProp(8, -1) != -1;
/* 3259:4753 */     if (type == 140) {
/* 3260:4754 */       if (indexIsNumber)
/* 3261:     */       {
/* 3262:4757 */         this.cfw.add(93);
/* 3263:4758 */         this.cfw.addALoad(this.contextLocal);
/* 3264:4759 */         addOptRuntimeInvoke("getObjectIndex", "(Ljava/lang/Object;DLnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3265:     */       }
/* 3266:     */       else
/* 3267:     */       {
/* 3268:4767 */         this.cfw.add(90);
/* 3269:4768 */         this.cfw.addALoad(this.contextLocal);
/* 3270:4769 */         addScriptRuntimeInvoke("getObjectElem", "(Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3271:     */       }
/* 3272:     */     }
/* 3273:4777 */     generateExpression(child, node);
/* 3274:4778 */     this.cfw.addALoad(this.contextLocal);
/* 3275:4779 */     if (indexIsNumber) {
/* 3276:4780 */       addScriptRuntimeInvoke("setObjectIndex", "(Ljava/lang/Object;DLjava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3277:     */     } else {
/* 3278:4788 */       addScriptRuntimeInvoke("setObjectElem", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;");
/* 3279:     */     }
/* 3280:     */   }
/* 3281:     */   
/* 3282:     */   private void visitDotQuery(Node node, Node child)
/* 3283:     */   {
/* 3284:4800 */     updateLineNumber(node);
/* 3285:4801 */     generateExpression(child, node);
/* 3286:4802 */     this.cfw.addALoad(this.variableObjectLocal);
/* 3287:4803 */     addScriptRuntimeInvoke("enterDotQuery", "(Ljava/lang/Object;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 3288:     */     
/* 3289:     */ 
/* 3290:     */ 
/* 3291:4807 */     this.cfw.addAStore(this.variableObjectLocal);
/* 3292:     */     
/* 3293:     */ 
/* 3294:     */ 
/* 3295:     */ 
/* 3296:4812 */     this.cfw.add(1);
/* 3297:4813 */     int queryLoopStart = this.cfw.acquireLabel();
/* 3298:4814 */     this.cfw.markLabel(queryLoopStart);
/* 3299:4815 */     this.cfw.add(87);
/* 3300:     */     
/* 3301:4817 */     generateExpression(child.getNext(), node);
/* 3302:4818 */     addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)Z");
/* 3303:4819 */     this.cfw.addALoad(this.variableObjectLocal);
/* 3304:4820 */     addScriptRuntimeInvoke("updateDotQuery", "(ZLnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;");
/* 3305:     */     
/* 3306:     */ 
/* 3307:     */ 
/* 3308:4824 */     this.cfw.add(89);
/* 3309:4825 */     this.cfw.add(198, queryLoopStart);
/* 3310:     */     
/* 3311:4827 */     this.cfw.addALoad(this.variableObjectLocal);
/* 3312:4828 */     addScriptRuntimeInvoke("leaveDotQuery", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 3313:     */     
/* 3314:     */ 
/* 3315:4831 */     this.cfw.addAStore(this.variableObjectLocal);
/* 3316:     */   }
/* 3317:     */   
/* 3318:     */   private int getLocalBlockRegister(Node node)
/* 3319:     */   {
/* 3320:4836 */     Node localBlock = (Node)node.getProp(3);
/* 3321:4837 */     int localSlot = localBlock.getExistingIntProp(2);
/* 3322:4838 */     return localSlot;
/* 3323:     */   }
/* 3324:     */   
/* 3325:     */   private void dcpLoadAsNumber(int dcp_register)
/* 3326:     */   {
/* 3327:4843 */     this.cfw.addALoad(dcp_register);
/* 3328:4844 */     this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/* 3329:     */     
/* 3330:     */ 
/* 3331:     */ 
/* 3332:4848 */     int isNumberLabel = this.cfw.acquireLabel();
/* 3333:4849 */     this.cfw.add(165, isNumberLabel);
/* 3334:4850 */     short stack = this.cfw.getStackTop();
/* 3335:4851 */     this.cfw.addALoad(dcp_register);
/* 3336:4852 */     addObjectToDouble();
/* 3337:4853 */     int beyond = this.cfw.acquireLabel();
/* 3338:4854 */     this.cfw.add(167, beyond);
/* 3339:4855 */     this.cfw.markLabel(isNumberLabel, stack);
/* 3340:4856 */     this.cfw.addDLoad(dcp_register + 1);
/* 3341:4857 */     this.cfw.markLabel(beyond);
/* 3342:     */   }
/* 3343:     */   
/* 3344:     */   private void dcpLoadAsObject(int dcp_register)
/* 3345:     */   {
/* 3346:4862 */     this.cfw.addALoad(dcp_register);
/* 3347:4863 */     this.cfw.add(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
/* 3348:     */     
/* 3349:     */ 
/* 3350:     */ 
/* 3351:4867 */     int isNumberLabel = this.cfw.acquireLabel();
/* 3352:4868 */     this.cfw.add(165, isNumberLabel);
/* 3353:4869 */     short stack = this.cfw.getStackTop();
/* 3354:4870 */     this.cfw.addALoad(dcp_register);
/* 3355:4871 */     int beyond = this.cfw.acquireLabel();
/* 3356:4872 */     this.cfw.add(167, beyond);
/* 3357:4873 */     this.cfw.markLabel(isNumberLabel, stack);
/* 3358:4874 */     this.cfw.addDLoad(dcp_register + 1);
/* 3359:4875 */     addDoubleWrap();
/* 3360:4876 */     this.cfw.markLabel(beyond);
/* 3361:     */   }
/* 3362:     */   
/* 3363:     */   private void addGoto(Node target, int jumpcode)
/* 3364:     */   {
/* 3365:4881 */     int targetLabel = getTargetLabel(target);
/* 3366:4882 */     this.cfw.add(jumpcode, targetLabel);
/* 3367:     */   }
/* 3368:     */   
/* 3369:     */   private void addObjectToDouble()
/* 3370:     */   {
/* 3371:4887 */     addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)D");
/* 3372:     */   }
/* 3373:     */   
/* 3374:     */   private void addNewObjectArray(int size)
/* 3375:     */   {
/* 3376:4892 */     if (size == 0)
/* 3377:     */     {
/* 3378:4893 */       if (this.itsZeroArgArray >= 0) {
/* 3379:4894 */         this.cfw.addALoad(this.itsZeroArgArray);
/* 3380:     */       } else {
/* 3381:4896 */         this.cfw.add(178, "net/sourceforge/htmlunit/corejs/javascript/ScriptRuntime", "emptyArgs", "[Ljava/lang/Object;");
/* 3382:     */       }
/* 3383:     */     }
/* 3384:     */     else
/* 3385:     */     {
/* 3386:4901 */       this.cfw.addPush(size);
/* 3387:4902 */       this.cfw.add(189, "java/lang/Object");
/* 3388:     */     }
/* 3389:     */   }
/* 3390:     */   
/* 3391:     */   private void addScriptRuntimeInvoke(String methodName, String methodSignature)
/* 3392:     */   {
/* 3393:4909 */     this.cfw.addInvoke(184, "net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime", methodName, methodSignature);
/* 3394:     */   }
/* 3395:     */   
/* 3396:     */   private void addOptRuntimeInvoke(String methodName, String methodSignature)
/* 3397:     */   {
/* 3398:4918 */     this.cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime", methodName, methodSignature);
/* 3399:     */   }
/* 3400:     */   
/* 3401:     */   private void addJumpedBooleanWrap(int trueLabel, int falseLabel)
/* 3402:     */   {
/* 3403:4926 */     this.cfw.markLabel(falseLabel);
/* 3404:4927 */     int skip = this.cfw.acquireLabel();
/* 3405:4928 */     this.cfw.add(178, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
/* 3406:     */     
/* 3407:4930 */     this.cfw.add(167, skip);
/* 3408:4931 */     this.cfw.markLabel(trueLabel);
/* 3409:4932 */     this.cfw.add(178, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
/* 3410:     */     
/* 3411:4934 */     this.cfw.markLabel(skip);
/* 3412:4935 */     this.cfw.adjustStackTop(-1);
/* 3413:     */   }
/* 3414:     */   
/* 3415:     */   private void addDoubleWrap()
/* 3416:     */   {
/* 3417:4940 */     addOptRuntimeInvoke("wrapDouble", "(D)Ljava/lang/Double;");
/* 3418:     */   }
/* 3419:     */   
/* 3420:     */   private short getNewWordPairLocal(boolean isConst)
/* 3421:     */   {
/* 3422:4951 */     short result = getConsecutiveSlots(2, isConst);
/* 3423:4952 */     if (result < 255)
/* 3424:     */     {
/* 3425:4953 */       this.locals[result] = 1;
/* 3426:4954 */       this.locals[(result + 1)] = 1;
/* 3427:4955 */       if (isConst) {
/* 3428:4956 */         this.locals[(result + 2)] = 1;
/* 3429:     */       }
/* 3430:4957 */       if (result == this.firstFreeLocal) {
/* 3431:4958 */         for (int i = this.firstFreeLocal + 2; i < 256; i++) {
/* 3432:4959 */           if (this.locals[i] == 0)
/* 3433:     */           {
/* 3434:4960 */             this.firstFreeLocal = ((short)i);
/* 3435:4961 */             if (this.localsMax < this.firstFreeLocal) {
/* 3436:4962 */               this.localsMax = this.firstFreeLocal;
/* 3437:     */             }
/* 3438:4963 */             return result;
/* 3439:     */           }
/* 3440:     */         }
/* 3441:     */       } else {
/* 3442:4968 */         return result;
/* 3443:     */       }
/* 3444:     */     }
/* 3445:4971 */     throw Context.reportRuntimeError("Program too complex (out of locals)");
/* 3446:     */   }
/* 3447:     */   
/* 3448:     */   private short getNewWordLocal(boolean isConst)
/* 3449:     */   {
/* 3450:4977 */     short result = getConsecutiveSlots(1, isConst);
/* 3451:4978 */     if (result < 255)
/* 3452:     */     {
/* 3453:4979 */       this.locals[result] = 1;
/* 3454:4980 */       if (isConst) {
/* 3455:4981 */         this.locals[(result + 1)] = 1;
/* 3456:     */       }
/* 3457:4982 */       if (result == this.firstFreeLocal) {
/* 3458:4983 */         for (int i = this.firstFreeLocal + 2; i < 256; i++) {
/* 3459:4984 */           if (this.locals[i] == 0)
/* 3460:     */           {
/* 3461:4985 */             this.firstFreeLocal = ((short)i);
/* 3462:4986 */             if (this.localsMax < this.firstFreeLocal) {
/* 3463:4987 */               this.localsMax = this.firstFreeLocal;
/* 3464:     */             }
/* 3465:4988 */             return result;
/* 3466:     */           }
/* 3467:     */         }
/* 3468:     */       } else {
/* 3469:4993 */         return result;
/* 3470:     */       }
/* 3471:     */     }
/* 3472:4996 */     throw Context.reportRuntimeError("Program too complex (out of locals)");
/* 3473:     */   }
/* 3474:     */   
/* 3475:     */   private short getNewWordLocal()
/* 3476:     */   {
/* 3477:5002 */     short result = this.firstFreeLocal;
/* 3478:5003 */     this.locals[result] = 1;
/* 3479:5004 */     for (int i = this.firstFreeLocal + 1; i < 256; i++) {
/* 3480:5005 */       if (this.locals[i] == 0)
/* 3481:     */       {
/* 3482:5006 */         this.firstFreeLocal = ((short)i);
/* 3483:5007 */         if (this.localsMax < this.firstFreeLocal) {
/* 3484:5008 */           this.localsMax = this.firstFreeLocal;
/* 3485:     */         }
/* 3486:5009 */         return result;
/* 3487:     */       }
/* 3488:     */     }
/* 3489:5012 */     throw Context.reportRuntimeError("Program too complex (out of locals)");
/* 3490:     */   }
/* 3491:     */   
/* 3492:     */   private short getConsecutiveSlots(int count, boolean isConst)
/* 3493:     */   {
/* 3494:5017 */     if (isConst) {
/* 3495:5018 */       count++;
/* 3496:     */     }
/* 3497:5019 */     short result = this.firstFreeLocal;
/* 3498:5021 */     while (result < 255)
/* 3499:     */     {
/* 3500:5024 */       for (int i = 0; i < count; i++) {
/* 3501:5025 */         if (this.locals[(result + i)] != 0) {
/* 3502:     */           break;
/* 3503:     */         }
/* 3504:     */       }
/* 3505:5027 */       if (i >= count) {
/* 3506:     */         break;
/* 3507:     */       }
/* 3508:5029 */       result = (short)(result + 1);
/* 3509:     */     }
/* 3510:5031 */     return result;
/* 3511:     */   }
/* 3512:     */   
/* 3513:     */   private void incReferenceWordLocal(short local)
/* 3514:     */   {
/* 3515:5037 */     this.locals[local] += 1;
/* 3516:     */   }
/* 3517:     */   
/* 3518:     */   private void decReferenceWordLocal(short local)
/* 3519:     */   {
/* 3520:5043 */     this.locals[local] -= 1;
/* 3521:     */   }
/* 3522:     */   
/* 3523:     */   private void releaseWordLocal(short local)
/* 3524:     */   {
/* 3525:5048 */     if (local < this.firstFreeLocal) {
/* 3526:5049 */       this.firstFreeLocal = local;
/* 3527:     */     }
/* 3528:5050 */     this.locals[local] = 0;
/* 3529:     */   }
/* 3530:     */   
/* 3531:     */   BodyCodegen()
/* 3532:     */   {
/* 3533:5098 */     this.maxLocals = 0;
/* 3534:5099 */     this.maxStack = 0;
/* 3535:     */   }
/* 3536:     */   
/* 3537:     */   static class FinallyReturnPoint
/* 3538:     */   {
/* 3539:5104 */     public List<Integer> jsrPoints = new ArrayList();
/* 3540:5105 */     public int tableLabel = 0;
/* 3541:     */   }
/* 3542:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.BodyCodegen
 * JD-Core Version:    0.7.0.1
 */