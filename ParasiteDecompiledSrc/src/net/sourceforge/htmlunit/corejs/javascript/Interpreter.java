/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.io.Serializable;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.List;
/*    7:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*    8:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebugFrame;
/*    9:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.Debugger;
/*   10:     */ 
/*   11:     */ public final class Interpreter
/*   12:     */   extends Icode
/*   13:     */   implements Evaluator
/*   14:     */ {
/*   15:     */   InterpreterData itsData;
/*   16:     */   static final int EXCEPTION_TRY_START_SLOT = 0;
/*   17:     */   static final int EXCEPTION_TRY_END_SLOT = 1;
/*   18:     */   static final int EXCEPTION_HANDLER_SLOT = 2;
/*   19:     */   static final int EXCEPTION_TYPE_SLOT = 3;
/*   20:     */   static final int EXCEPTION_LOCAL_SLOT = 4;
/*   21:     */   static final int EXCEPTION_SCOPE_SLOT = 5;
/*   22:     */   static final int EXCEPTION_SLOT_SIZE = 6;
/*   23:     */   
/*   24:     */   private static class CallFrame
/*   25:     */     implements Cloneable, Serializable
/*   26:     */   {
/*   27:     */     static final long serialVersionUID = -2843792508994958978L;
/*   28:     */     CallFrame parentFrame;
/*   29:     */     int frameIndex;
/*   30:     */     boolean frozen;
/*   31:     */     InterpretedFunction fnOrScript;
/*   32:     */     InterpreterData idata;
/*   33:     */     Object[] stack;
/*   34:     */     int[] stackAttributes;
/*   35:     */     double[] sDbl;
/*   36:     */     CallFrame varSource;
/*   37:     */     int localShift;
/*   38:     */     int emptyStackTop;
/*   39:     */     DebugFrame debuggerFrame;
/*   40:     */     boolean useActivation;
/*   41:     */     boolean isContinuationsTopFrame;
/*   42:     */     Scriptable thisObj;
/*   43:     */     Scriptable[] scriptRegExps;
/*   44:     */     Object result;
/*   45:     */     double resultDbl;
/*   46:     */     int pc;
/*   47:     */     int pcPrevBranch;
/*   48:     */     int pcSourceLineStart;
/*   49:     */     Scriptable scope;
/*   50:     */     int savedStackTop;
/*   51:     */     int savedCallOp;
/*   52:     */     Object throwable;
/*   53:     */     
/*   54:     */     CallFrame cloneFrozen()
/*   55:     */     {
/*   56: 125 */       if (!this.frozen) {
/*   57: 125 */         Kit.codeBug();
/*   58:     */       }
/*   59:     */       CallFrame copy;
/*   60:     */       try
/*   61:     */       {
/*   62: 129 */         copy = (CallFrame)clone();
/*   63:     */       }
/*   64:     */       catch (CloneNotSupportedException ex)
/*   65:     */       {
/*   66: 131 */         throw new IllegalStateException();
/*   67:     */       }
/*   68: 137 */       copy.stack = ((Object[])this.stack.clone());
/*   69: 138 */       copy.stackAttributes = ((int[])this.stackAttributes.clone());
/*   70: 139 */       copy.sDbl = ((double[])this.sDbl.clone());
/*   71:     */       
/*   72: 141 */       copy.frozen = false;
/*   73: 142 */       return copy;
/*   74:     */     }
/*   75:     */   }
/*   76:     */   
/*   77:     */   private static final class ContinuationJump
/*   78:     */     implements Serializable
/*   79:     */   {
/*   80:     */     static final long serialVersionUID = 7687739156004308247L;
/*   81:     */     Interpreter.CallFrame capturedFrame;
/*   82:     */     Interpreter.CallFrame branchFrame;
/*   83:     */     Object result;
/*   84:     */     double resultDbl;
/*   85:     */     
/*   86:     */     ContinuationJump(NativeContinuation c, Interpreter.CallFrame current)
/*   87:     */     {
/*   88: 157 */       this.capturedFrame = ((Interpreter.CallFrame)c.getImplementation());
/*   89: 158 */       if ((this.capturedFrame == null) || (current == null))
/*   90:     */       {
/*   91: 162 */         this.branchFrame = null;
/*   92:     */       }
/*   93:     */       else
/*   94:     */       {
/*   95: 166 */         Interpreter.CallFrame chain1 = this.capturedFrame;
/*   96: 167 */         Interpreter.CallFrame chain2 = current;
/*   97:     */         
/*   98:     */ 
/*   99:     */ 
/*  100: 171 */         int diff = chain1.frameIndex - chain2.frameIndex;
/*  101: 172 */         if (diff != 0)
/*  102:     */         {
/*  103: 173 */           if (diff < 0)
/*  104:     */           {
/*  105: 176 */             chain1 = current;
/*  106: 177 */             chain2 = this.capturedFrame;
/*  107: 178 */             diff = -diff;
/*  108:     */           }
/*  109:     */           do
/*  110:     */           {
/*  111: 181 */             chain1 = chain1.parentFrame;
/*  112: 182 */             diff--;
/*  113: 182 */           } while (diff != 0);
/*  114: 183 */           if (chain1.frameIndex != chain2.frameIndex) {
/*  115: 183 */             Kit.codeBug();
/*  116:     */           }
/*  117:     */         }
/*  118: 188 */         while ((chain1 != chain2) && (chain1 != null))
/*  119:     */         {
/*  120: 189 */           chain1 = chain1.parentFrame;
/*  121: 190 */           chain2 = chain2.parentFrame;
/*  122:     */         }
/*  123: 193 */         this.branchFrame = chain1;
/*  124: 194 */         if ((this.branchFrame != null) && (!this.branchFrame.frozen)) {
/*  125: 195 */           Kit.codeBug();
/*  126:     */         }
/*  127:     */       }
/*  128:     */     }
/*  129:     */   }
/*  130:     */   
/*  131:     */   private static CallFrame captureFrameForGenerator(CallFrame frame)
/*  132:     */   {
/*  133: 201 */     frame.frozen = true;
/*  134: 202 */     CallFrame result = frame.cloneFrozen();
/*  135: 203 */     frame.frozen = false;
/*  136:     */     
/*  137:     */ 
/*  138: 206 */     result.parentFrame = null;
/*  139: 207 */     result.frameIndex = 0;
/*  140:     */     
/*  141: 209 */     return result;
/*  142:     */   }
/*  143:     */   
/*  144:     */   public Object compile(CompilerEnvirons compilerEnv, ScriptNode tree, String encodedSource, boolean returnFunction)
/*  145:     */   {
/*  146: 232 */     CodeGenerator cgen = new CodeGenerator();
/*  147: 233 */     this.itsData = cgen.compile(compilerEnv, tree, encodedSource, returnFunction);
/*  148: 234 */     return this.itsData;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public Script createScriptObject(Object bytecode, Object staticSecurityDomain)
/*  152:     */   {
/*  153: 239 */     if (bytecode != this.itsData) {
/*  154: 241 */       Kit.codeBug();
/*  155:     */     }
/*  156: 243 */     return InterpretedFunction.createScript(this.itsData, staticSecurityDomain);
/*  157:     */   }
/*  158:     */   
/*  159:     */   public void setEvalScriptFlag(Script script)
/*  160:     */   {
/*  161: 248 */     ((InterpretedFunction)script).idata.evalScriptFlag = true;
/*  162:     */   }
/*  163:     */   
/*  164:     */   public Function createFunctionObject(Context cx, Scriptable scope, Object bytecode, Object staticSecurityDomain)
/*  165:     */   {
/*  166: 255 */     if (bytecode != this.itsData) {
/*  167: 257 */       Kit.codeBug();
/*  168:     */     }
/*  169: 259 */     return InterpretedFunction.createFunction(cx, scope, this.itsData, staticSecurityDomain);
/*  170:     */   }
/*  171:     */   
/*  172:     */   private static int getShort(byte[] iCode, int pc)
/*  173:     */   {
/*  174: 264 */     return iCode[pc] << 8 | iCode[(pc + 1)] & 0xFF;
/*  175:     */   }
/*  176:     */   
/*  177:     */   private static int getIndex(byte[] iCode, int pc)
/*  178:     */   {
/*  179: 268 */     return (iCode[pc] & 0xFF) << 8 | iCode[(pc + 1)] & 0xFF;
/*  180:     */   }
/*  181:     */   
/*  182:     */   private static int getInt(byte[] iCode, int pc)
/*  183:     */   {
/*  184: 272 */     return iCode[pc] << 24 | (iCode[(pc + 1)] & 0xFF) << 16 | (iCode[(pc + 2)] & 0xFF) << 8 | iCode[(pc + 3)] & 0xFF;
/*  185:     */   }
/*  186:     */   
/*  187:     */   private static int getExceptionHandler(CallFrame frame, boolean onlyFinally)
/*  188:     */   {
/*  189: 279 */     int[] exceptionTable = frame.idata.itsExceptionTable;
/*  190: 280 */     if (exceptionTable == null) {
/*  191: 282 */       return -1;
/*  192:     */     }
/*  193: 288 */     int pc = frame.pc - 1;
/*  194:     */     
/*  195:     */ 
/*  196: 291 */     int best = -1;int bestStart = 0;int bestEnd = 0;
/*  197: 292 */     for (int i = 0; i != exceptionTable.length; i += 6)
/*  198:     */     {
/*  199: 293 */       int start = exceptionTable[(i + 0)];
/*  200: 294 */       int end = exceptionTable[(i + 1)];
/*  201: 295 */       if ((start <= pc) && (pc < end)) {
/*  202: 298 */         if ((!onlyFinally) || (exceptionTable[(i + 3)] == 1))
/*  203:     */         {
/*  204: 301 */           if (best >= 0)
/*  205:     */           {
/*  206: 305 */             if (bestEnd < end) {
/*  207:     */               continue;
/*  208:     */             }
/*  209: 309 */             if (bestStart > start) {
/*  210: 309 */               Kit.codeBug();
/*  211:     */             }
/*  212: 310 */             if (bestEnd == end) {
/*  213: 310 */               Kit.codeBug();
/*  214:     */             }
/*  215:     */           }
/*  216: 312 */           best = i;
/*  217: 313 */           bestStart = start;
/*  218: 314 */           bestEnd = end;
/*  219:     */         }
/*  220:     */       }
/*  221:     */     }
/*  222: 316 */     return best;
/*  223:     */   }
/*  224:     */   
/*  225:     */   static void dumpICode(InterpreterData idata) {}
/*  226:     */   
/*  227:     */   private static int bytecodeSpan(int bytecode)
/*  228:     */   {
/*  229: 532 */     switch (bytecode)
/*  230:     */     {
/*  231:     */     case -63: 
/*  232:     */     case -62: 
/*  233:     */     case 50: 
/*  234:     */     case 72: 
/*  235: 538 */       return 3;
/*  236:     */     case -54: 
/*  237:     */     case -23: 
/*  238:     */     case -6: 
/*  239:     */     case 5: 
/*  240:     */     case 6: 
/*  241:     */     case 7: 
/*  242: 547 */       return 3;
/*  243:     */     case -21: 
/*  244: 553 */       return 5;
/*  245:     */     case 57: 
/*  246: 557 */       return 2;
/*  247:     */     case -11: 
/*  248:     */     case -10: 
/*  249:     */     case -9: 
/*  250:     */     case -8: 
/*  251:     */     case -7: 
/*  252: 565 */       return 2;
/*  253:     */     case -27: 
/*  254: 569 */       return 3;
/*  255:     */     case -28: 
/*  256: 573 */       return 5;
/*  257:     */     case -38: 
/*  258: 577 */       return 2;
/*  259:     */     case -39: 
/*  260: 581 */       return 3;
/*  261:     */     case -40: 
/*  262: 585 */       return 5;
/*  263:     */     case -45: 
/*  264: 589 */       return 2;
/*  265:     */     case -46: 
/*  266: 593 */       return 3;
/*  267:     */     case -47: 
/*  268: 597 */       return 5;
/*  269:     */     case -61: 
/*  270:     */     case -49: 
/*  271:     */     case -48: 
/*  272: 603 */       return 2;
/*  273:     */     case -26: 
/*  274: 607 */       return 3;
/*  275:     */     }
/*  276: 609 */     if (!validBytecode(bytecode)) {
/*  277: 609 */       throw Kit.codeBug();
/*  278:     */     }
/*  279: 610 */     return 1;
/*  280:     */   }
/*  281:     */   
/*  282:     */   static int[] getLineNumbers(InterpreterData data)
/*  283:     */   {
/*  284: 615 */     UintMap presentLines = new UintMap();
/*  285:     */     
/*  286: 617 */     byte[] iCode = data.itsICode;
/*  287: 618 */     int iCodeLength = iCode.length;
/*  288: 619 */     for (int pc = 0; pc != iCodeLength;)
/*  289:     */     {
/*  290: 620 */       int bytecode = iCode[pc];
/*  291: 621 */       int span = bytecodeSpan(bytecode);
/*  292: 622 */       if (bytecode == -26)
/*  293:     */       {
/*  294: 623 */         if (span != 3) {
/*  295: 623 */           Kit.codeBug();
/*  296:     */         }
/*  297: 624 */         int line = getIndex(iCode, pc + 1);
/*  298: 625 */         presentLines.put(line, 0);
/*  299:     */       }
/*  300: 627 */       pc += span;
/*  301:     */     }
/*  302: 630 */     return presentLines.getKeys();
/*  303:     */   }
/*  304:     */   
/*  305:     */   public void captureStackInfo(RhinoException ex)
/*  306:     */   {
/*  307: 635 */     Context cx = Context.getCurrentContext();
/*  308: 636 */     if ((cx == null) || (cx.lastInterpreterFrame == null))
/*  309:     */     {
/*  310: 638 */       ex.interpreterStackInfo = null;
/*  311: 639 */       ex.interpreterLineData = null; return;
/*  312:     */     }
/*  313:     */     CallFrame[] array;
/*  314:     */     CallFrame[] array;
/*  315: 644 */     if ((cx.previousInterpreterInvocations == null) || (cx.previousInterpreterInvocations.size() == 0))
/*  316:     */     {
/*  317: 647 */       array = new CallFrame[1];
/*  318:     */     }
/*  319:     */     else
/*  320:     */     {
/*  321: 649 */       int previousCount = cx.previousInterpreterInvocations.size();
/*  322: 650 */       if (cx.previousInterpreterInvocations.peek() == cx.lastInterpreterFrame) {
/*  323: 657 */         previousCount--;
/*  324:     */       }
/*  325: 659 */       array = new CallFrame[previousCount + 1];
/*  326: 660 */       cx.previousInterpreterInvocations.toArray(array);
/*  327:     */     }
/*  328: 662 */     array[(array.length - 1)] = ((CallFrame)cx.lastInterpreterFrame);
/*  329:     */     
/*  330: 664 */     int interpreterFrameCount = 0;
/*  331: 665 */     for (int i = 0; i != array.length; i++) {
/*  332: 666 */       interpreterFrameCount += 1 + array[i].frameIndex;
/*  333:     */     }
/*  334: 669 */     int[] linePC = new int[interpreterFrameCount];
/*  335:     */     
/*  336:     */ 
/*  337: 672 */     int linePCIndex = interpreterFrameCount;
/*  338: 673 */     for (int i = array.length; i != 0;)
/*  339:     */     {
/*  340: 674 */       i--;
/*  341: 675 */       CallFrame frame = array[i];
/*  342: 676 */       while (frame != null)
/*  343:     */       {
/*  344: 677 */         linePCIndex--;
/*  345: 678 */         linePC[linePCIndex] = frame.pcSourceLineStart;
/*  346: 679 */         frame = frame.parentFrame;
/*  347:     */       }
/*  348:     */     }
/*  349: 682 */     if (linePCIndex != 0) {
/*  350: 682 */       Kit.codeBug();
/*  351:     */     }
/*  352: 684 */     ex.interpreterStackInfo = array;
/*  353: 685 */     ex.interpreterLineData = linePC;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public String getSourcePositionFromStack(Context cx, int[] linep)
/*  357:     */   {
/*  358: 690 */     CallFrame frame = (CallFrame)cx.lastInterpreterFrame;
/*  359: 691 */     InterpreterData idata = frame.idata;
/*  360: 692 */     if (frame.pcSourceLineStart >= 0) {
/*  361: 693 */       linep[0] = getIndex(idata.itsICode, frame.pcSourceLineStart);
/*  362:     */     } else {
/*  363: 695 */       linep[0] = 0;
/*  364:     */     }
/*  365: 697 */     return idata.itsSourceFile;
/*  366:     */   }
/*  367:     */   
/*  368:     */   public String getPatchedStack(RhinoException ex, String nativeStackTrace)
/*  369:     */   {
/*  370: 703 */     String tag = "net.sourceforge.htmlunit.corejs.javascript.Interpreter.interpretLoop";
/*  371: 704 */     StringBuffer sb = new StringBuffer(nativeStackTrace.length() + 1000);
/*  372: 705 */     String lineSeparator = SecurityUtilities.getSystemProperty("line.separator");
/*  373:     */     
/*  374: 707 */     CallFrame[] array = (CallFrame[])ex.interpreterStackInfo;
/*  375: 708 */     int[] linePC = ex.interpreterLineData;
/*  376: 709 */     int arrayIndex = array.length;
/*  377: 710 */     int linePCIndex = linePC.length;
/*  378: 711 */     int offset = 0;
/*  379: 712 */     while (arrayIndex != 0)
/*  380:     */     {
/*  381: 713 */       arrayIndex--;
/*  382: 714 */       int pos = nativeStackTrace.indexOf(tag, offset);
/*  383: 715 */       if (pos < 0) {
/*  384:     */         break;
/*  385:     */       }
/*  386: 720 */       pos += tag.length();
/*  387: 722 */       for (; pos != nativeStackTrace.length(); pos++)
/*  388:     */       {
/*  389: 723 */         char c = nativeStackTrace.charAt(pos);
/*  390: 724 */         if ((c == '\n') || (c == '\r')) {
/*  391:     */           break;
/*  392:     */         }
/*  393:     */       }
/*  394: 728 */       sb.append(nativeStackTrace.substring(offset, pos));
/*  395: 729 */       offset = pos;
/*  396:     */       
/*  397: 731 */       CallFrame frame = array[arrayIndex];
/*  398: 732 */       while (frame != null)
/*  399:     */       {
/*  400: 733 */         if (linePCIndex == 0) {
/*  401: 733 */           Kit.codeBug();
/*  402:     */         }
/*  403: 734 */         linePCIndex--;
/*  404: 735 */         InterpreterData idata = frame.idata;
/*  405: 736 */         sb.append(lineSeparator);
/*  406: 737 */         sb.append("\tat script");
/*  407: 738 */         if ((idata.itsName != null) && (idata.itsName.length() != 0))
/*  408:     */         {
/*  409: 739 */           sb.append('.');
/*  410: 740 */           sb.append(idata.itsName);
/*  411:     */         }
/*  412: 742 */         sb.append('(');
/*  413: 743 */         sb.append(idata.itsSourceFile);
/*  414: 744 */         int pc = linePC[linePCIndex];
/*  415: 745 */         if (pc >= 0)
/*  416:     */         {
/*  417: 747 */           sb.append(':');
/*  418: 748 */           sb.append(getIndex(idata.itsICode, pc));
/*  419:     */         }
/*  420: 750 */         sb.append(')');
/*  421: 751 */         frame = frame.parentFrame;
/*  422:     */       }
/*  423:     */     }
/*  424: 754 */     sb.append(nativeStackTrace.substring(offset));
/*  425:     */     
/*  426: 756 */     return sb.toString();
/*  427:     */   }
/*  428:     */   
/*  429:     */   public List<String> getScriptStack(RhinoException ex)
/*  430:     */   {
/*  431: 760 */     ScriptStackElement[][] stack = getScriptStackElements(ex);
/*  432: 761 */     List<String> list = new ArrayList(stack.length);
/*  433: 762 */     String lineSeparator = SecurityUtilities.getSystemProperty("line.separator");
/*  434: 764 */     for (ScriptStackElement[] group : stack)
/*  435:     */     {
/*  436: 765 */       StringBuilder sb = new StringBuilder();
/*  437: 766 */       for (ScriptStackElement elem : group)
/*  438:     */       {
/*  439: 767 */         elem.renderJavaStyle(sb);
/*  440: 768 */         sb.append(lineSeparator);
/*  441:     */       }
/*  442: 770 */       list.add(sb.toString());
/*  443:     */     }
/*  444: 772 */     return list;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public ScriptStackElement[][] getScriptStackElements(RhinoException ex)
/*  448:     */   {
/*  449: 777 */     if (ex.interpreterStackInfo == null) {
/*  450: 778 */       return (ScriptStackElement[][])null;
/*  451:     */     }
/*  452: 781 */     List<ScriptStackElement[]> list = new ArrayList();
/*  453:     */     
/*  454: 783 */     CallFrame[] array = (CallFrame[])ex.interpreterStackInfo;
/*  455: 784 */     int[] linePC = ex.interpreterLineData;
/*  456: 785 */     int arrayIndex = array.length;
/*  457: 786 */     int linePCIndex = linePC.length;
/*  458: 787 */     while (arrayIndex != 0)
/*  459:     */     {
/*  460: 788 */       arrayIndex--;
/*  461: 789 */       CallFrame frame = array[arrayIndex];
/*  462: 790 */       List<ScriptStackElement> group = new ArrayList();
/*  463: 791 */       while (frame != null)
/*  464:     */       {
/*  465: 792 */         if (linePCIndex == 0) {
/*  466: 792 */           Kit.codeBug();
/*  467:     */         }
/*  468: 793 */         linePCIndex--;
/*  469: 794 */         InterpreterData idata = frame.idata;
/*  470: 795 */         String fileName = idata.itsSourceFile;
/*  471: 796 */         String functionName = null;
/*  472: 797 */         int lineNumber = -1;
/*  473: 798 */         int pc = linePC[linePCIndex];
/*  474: 799 */         if (pc >= 0) {
/*  475: 800 */           lineNumber = getIndex(idata.itsICode, pc);
/*  476:     */         }
/*  477: 802 */         if ((idata.itsName != null) && (idata.itsName.length() != 0)) {
/*  478: 803 */           functionName = idata.itsName;
/*  479:     */         }
/*  480: 805 */         frame = frame.parentFrame;
/*  481: 806 */         group.add(new ScriptStackElement(fileName, functionName, lineNumber));
/*  482:     */       }
/*  483: 808 */       list.add(group.toArray(new ScriptStackElement[group.size()]));
/*  484:     */     }
/*  485: 810 */     return (ScriptStackElement[][])list.toArray(new ScriptStackElement[list.size()][]);
/*  486:     */   }
/*  487:     */   
/*  488:     */   static String getEncodedSource(InterpreterData idata)
/*  489:     */   {
/*  490: 815 */     if (idata.encodedSource == null) {
/*  491: 816 */       return null;
/*  492:     */     }
/*  493: 818 */     return idata.encodedSource.substring(idata.encodedSourceStart, idata.encodedSourceEnd);
/*  494:     */   }
/*  495:     */   
/*  496:     */   private static void initFunction(Context cx, Scriptable scope, InterpretedFunction parent, int index)
/*  497:     */   {
/*  498: 826 */     InterpretedFunction fn = InterpretedFunction.createFunction(cx, scope, parent, index);
/*  499: 827 */     ScriptRuntime.initFunction(cx, scope, fn, fn.idata.itsFunctionType, parent.idata.evalScriptFlag);
/*  500:     */   }
/*  501:     */   
/*  502:     */   static Object interpret(InterpretedFunction ifun, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  503:     */   {
/*  504: 835 */     if (!ScriptRuntime.hasTopCall(cx)) {
/*  505: 835 */       Kit.codeBug();
/*  506:     */     }
/*  507: 837 */     if (cx.interpreterSecurityDomain != ifun.securityDomain)
/*  508:     */     {
/*  509: 838 */       Object savedDomain = cx.interpreterSecurityDomain;
/*  510: 839 */       cx.interpreterSecurityDomain = ifun.securityDomain;
/*  511:     */       try
/*  512:     */       {
/*  513: 841 */         return ifun.securityController.callWithDomain(ifun.securityDomain, cx, ifun, scope, thisObj, args);
/*  514:     */       }
/*  515:     */       finally
/*  516:     */       {
/*  517: 844 */         cx.interpreterSecurityDomain = savedDomain;
/*  518:     */       }
/*  519:     */     }
/*  520: 848 */     CallFrame frame = new CallFrame(null);
/*  521: 849 */     initFrame(cx, scope, thisObj, args, null, 0, args.length, ifun, null, frame);
/*  522:     */     
/*  523: 851 */     frame.isContinuationsTopFrame = cx.isContinuationsTopCall;
/*  524: 852 */     cx.isContinuationsTopCall = false;
/*  525:     */     
/*  526: 854 */     return interpretLoop(cx, frame, null);
/*  527:     */   }
/*  528:     */   
/*  529:     */   static class GeneratorState
/*  530:     */   {
/*  531:     */     int operation;
/*  532:     */     Object value;
/*  533:     */     RuntimeException returnedException;
/*  534:     */     
/*  535:     */     GeneratorState(int operation, Object value)
/*  536:     */     {
/*  537: 859 */       this.operation = operation;
/*  538: 860 */       this.value = value;
/*  539:     */     }
/*  540:     */   }
/*  541:     */   
/*  542:     */   public static Object resumeGenerator(Context cx, Scriptable scope, int operation, Object savedState, Object value)
/*  543:     */   {
/*  544: 873 */     CallFrame frame = (CallFrame)savedState;
/*  545: 874 */     GeneratorState generatorState = new GeneratorState(operation, value);
/*  546: 875 */     if (operation == 2) {
/*  547:     */       try
/*  548:     */       {
/*  549: 877 */         return interpretLoop(cx, frame, generatorState);
/*  550:     */       }
/*  551:     */       catch (RuntimeException e)
/*  552:     */       {
/*  553: 880 */         if (e != value) {
/*  554: 881 */           throw e;
/*  555:     */         }
/*  556: 883 */         return Undefined.instance;
/*  557:     */       }
/*  558:     */     }
/*  559: 885 */     Object result = interpretLoop(cx, frame, generatorState);
/*  560: 886 */     if (generatorState.returnedException != null) {
/*  561: 887 */       throw generatorState.returnedException;
/*  562:     */     }
/*  563: 888 */     return result;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public static Object restartContinuation(NativeContinuation c, Context cx, Scriptable scope, Object[] args)
/*  567:     */   {
/*  568: 894 */     if (!ScriptRuntime.hasTopCall(cx)) {
/*  569: 895 */       return ScriptRuntime.doTopCall(c, cx, scope, null, args);
/*  570:     */     }
/*  571:     */     Object arg;
/*  572:     */     Object arg;
/*  573: 899 */     if (args.length == 0) {
/*  574: 900 */       arg = Undefined.instance;
/*  575:     */     } else {
/*  576: 902 */       arg = args[0];
/*  577:     */     }
/*  578: 905 */     CallFrame capturedFrame = (CallFrame)c.getImplementation();
/*  579: 906 */     if (capturedFrame == null) {
/*  580: 908 */       return arg;
/*  581:     */     }
/*  582: 911 */     ContinuationJump cjump = new ContinuationJump(c, null);
/*  583:     */     
/*  584: 913 */     cjump.result = arg;
/*  585: 914 */     return interpretLoop(cx, null, cjump);
/*  586:     */   }
/*  587:     */   
/*  588:     */   private static Object interpretLoop(Context cx, CallFrame frame, Object throwable)
/*  589:     */   {
/*  590: 924 */     Object DBL_MRK = UniqueTag.DOUBLE_MARK;
/*  591: 925 */     Object undefined = Undefined.instance;
/*  592:     */     
/*  593: 927 */     boolean instructionCounting = cx.instructionThreshold != 0;
/*  594:     */     
/*  595:     */ 
/*  596: 930 */     int INVOCATION_COST = 100;
/*  597:     */     
/*  598: 932 */     int EXCEPTION_COST = 100;
/*  599:     */     
/*  600: 934 */     String stringReg = null;
/*  601: 935 */     int indexReg = -1;
/*  602: 937 */     if (cx.lastInterpreterFrame != null)
/*  603:     */     {
/*  604: 940 */       if (cx.previousInterpreterInvocations == null) {
/*  605: 941 */         cx.previousInterpreterInvocations = new ObjArray();
/*  606:     */       }
/*  607: 943 */       cx.previousInterpreterInvocations.push(cx.lastInterpreterFrame);
/*  608:     */     }
/*  609: 953 */     GeneratorState generatorState = null;
/*  610: 954 */     if (throwable != null) {
/*  611: 955 */       if ((throwable instanceof GeneratorState))
/*  612:     */       {
/*  613: 956 */         generatorState = (GeneratorState)throwable;
/*  614:     */         
/*  615:     */ 
/*  616: 959 */         enterFrame(cx, frame, ScriptRuntime.emptyArgs, true);
/*  617: 960 */         throwable = null;
/*  618:     */       }
/*  619: 961 */       else if (!(throwable instanceof ContinuationJump))
/*  620:     */       {
/*  621: 963 */         Kit.codeBug();
/*  622:     */       }
/*  623:     */     }
/*  624: 967 */     Object interpreterResult = null;
/*  625: 968 */     double interpreterResultDbl = 0.0D;
/*  626:     */     try
/*  627:     */     {
/*  628:     */       for (;;)
/*  629:     */       {
/*  630: 973 */         if (throwable != null)
/*  631:     */         {
/*  632: 977 */           frame = processThrowable(cx, throwable, frame, indexReg, instructionCounting);
/*  633:     */           
/*  634: 979 */           throwable = frame.throwable;
/*  635: 980 */           frame.throwable = null;
/*  636:     */         }
/*  637: 982 */         else if ((generatorState == null) && (frame.frozen))
/*  638:     */         {
/*  639: 982 */           Kit.codeBug();
/*  640:     */         }
/*  641: 987 */         Object[] stack = frame.stack;
/*  642: 988 */         double[] sDbl = frame.sDbl;
/*  643: 989 */         Object[] vars = frame.varSource.stack;
/*  644: 990 */         double[] varDbls = frame.varSource.sDbl;
/*  645: 991 */         int[] varAttributes = frame.varSource.stackAttributes;
/*  646: 992 */         byte[] iCode = frame.idata.itsICode;
/*  647: 993 */         String[] strings = frame.idata.itsStringTable;
/*  648:     */         
/*  649:     */ 
/*  650:     */ 
/*  651:     */ 
/*  652:     */ 
/*  653: 999 */         int stackTop = frame.savedStackTop;
/*  654:     */         
/*  655:     */ 
/*  656:1002 */         cx.lastInterpreterFrame = frame;
/*  657:     */         for (;;)
/*  658:     */         {
/*  659:1009 */           int op = iCode[(frame.pc++)];
/*  660:1013 */           switch (op)
/*  661:     */           {
/*  662:     */           case -62: 
/*  663:1015 */             if (!frame.frozen)
/*  664:     */             {
/*  665:1018 */               frame.pc -= 1;
/*  666:1019 */               CallFrame generatorFrame = captureFrameForGenerator(frame);
/*  667:1020 */               generatorFrame.frozen = true;
/*  668:1021 */               NativeGenerator generator = new NativeGenerator(frame.scope, generatorFrame.fnOrScript, generatorFrame);
/*  669:     */               
/*  670:1023 */               frame.result = generator;
/*  671:     */             }
/*  672:1024 */             break;
/*  673:     */           case 72: 
/*  674:1031 */             if (!frame.frozen) {
/*  675:1032 */               return freezeGenerator(cx, frame, stackTop, generatorState);
/*  676:     */             }
/*  677:1034 */             Object obj = thawGenerator(frame, stackTop, generatorState, op);
/*  678:1035 */             if (obj != Scriptable.NOT_FOUND) {
/*  679:1036 */               throwable = obj;
/*  680:     */             }
/*  681:1037 */             break;
/*  682:     */           case -63: 
/*  683:1044 */             frame.frozen = true;
/*  684:1045 */             int sourceLine = getIndex(iCode, frame.pc);
/*  685:1046 */             generatorState.returnedException = new JavaScriptException(NativeIterator.getStopIterationObject(frame.scope), frame.idata.itsSourceFile, sourceLine);
/*  686:     */             
/*  687:     */ 
/*  688:1049 */             break;
/*  689:     */           case 50: 
/*  690:1052 */             Object value = stack[stackTop];
/*  691:1053 */             if (value == DBL_MRK) {
/*  692:1053 */               value = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/*  693:     */             }
/*  694:1054 */             stackTop--;
/*  695:     */             
/*  696:1056 */             int sourceLine = getIndex(iCode, frame.pc);
/*  697:1057 */             throwable = new JavaScriptException(value, frame.idata.itsSourceFile, sourceLine);
/*  698:     */             
/*  699:     */ 
/*  700:1060 */             break;
/*  701:     */           case 51: 
/*  702:1063 */             indexReg += frame.localShift;
/*  703:1064 */             throwable = stack[indexReg];
/*  704:1065 */             break;
/*  705:     */           case 14: 
/*  706:     */           case 15: 
/*  707:     */           case 16: 
/*  708:     */           case 17: 
/*  709:1071 */             stackTop--;
/*  710:1072 */             Object rhs = stack[(stackTop + 1)];
/*  711:1073 */             Object lhs = stack[stackTop];
/*  712:     */             double lDbl;
/*  713:     */             double rDbl;
/*  714:     */             double lDbl;
/*  715:1080 */             if (rhs == DBL_MRK)
/*  716:     */             {
/*  717:1081 */               double rDbl = sDbl[(stackTop + 1)];
/*  718:1082 */               lDbl = stack_double(frame, stackTop);
/*  719:     */             }
/*  720:     */             else
/*  721:     */             {
/*  722:1083 */               if (lhs != DBL_MRK) {
/*  723:     */                 break label1552;
/*  724:     */               }
/*  725:1084 */               rDbl = ScriptRuntime.toNumber(rhs);
/*  726:1085 */               lDbl = sDbl[stackTop];
/*  727:     */             }
/*  728:     */             boolean valBln;
/*  729:1089 */             switch (op)
/*  730:     */             {
/*  731:     */             case 17: 
/*  732:1091 */               valBln = lDbl >= rDbl;
/*  733:1092 */               break;
/*  734:     */             case 15: 
/*  735:1094 */               valBln = lDbl <= rDbl;
/*  736:1095 */               break;
/*  737:     */             case 16: 
/*  738:1097 */               valBln = lDbl > rDbl;
/*  739:1098 */               break;
/*  740:     */             case 14: 
/*  741:1100 */               valBln = lDbl < rDbl;
/*  742:1101 */               break;
/*  743:     */             default: 
/*  744:1103 */               throw Kit.codeBug();
/*  745:     */             }
/*  746:1106 */             switch (op)
/*  747:     */             {
/*  748:     */             case 17: 
/*  749:1108 */               valBln = ScriptRuntime.cmp_LE(rhs, lhs);
/*  750:1109 */               break;
/*  751:     */             case 15: 
/*  752:1111 */               valBln = ScriptRuntime.cmp_LE(lhs, rhs);
/*  753:1112 */               break;
/*  754:     */             case 16: 
/*  755:1114 */               valBln = ScriptRuntime.cmp_LT(rhs, lhs);
/*  756:1115 */               break;
/*  757:     */             case 14: 
/*  758:1117 */               valBln = ScriptRuntime.cmp_LT(lhs, rhs);
/*  759:1118 */               break;
/*  760:     */             default: 
/*  761:1120 */               throw Kit.codeBug();
/*  762:     */             }
/*  763:1123 */             stack[stackTop] = ScriptRuntime.wrapBoolean(valBln);
/*  764:1124 */             break;
/*  765:     */           case 52: 
/*  766:     */           case 53: 
/*  767:1128 */             Object rhs = stack[stackTop];
/*  768:1129 */             if (rhs == DBL_MRK) {
/*  769:1129 */               rhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/*  770:     */             }
/*  771:1130 */             stackTop--;
/*  772:1131 */             Object lhs = stack[stackTop];
/*  773:1132 */             if (lhs == DBL_MRK) {
/*  774:1132 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/*  775:     */             }
/*  776:     */             boolean valBln;
/*  777:     */             boolean valBln;
/*  778:1134 */             if (op == 52) {
/*  779:1135 */               valBln = ScriptRuntime.in(lhs, rhs, cx);
/*  780:     */             } else {
/*  781:1137 */               valBln = ScriptRuntime.instanceOf(lhs, rhs, cx);
/*  782:     */             }
/*  783:1139 */             stack[stackTop] = ScriptRuntime.wrapBoolean(valBln);
/*  784:1140 */             break;
/*  785:     */           case 12: 
/*  786:     */           case 13: 
/*  787:1144 */             stackTop--;
/*  788:     */             
/*  789:1146 */             Object rhs = stack[(stackTop + 1)];
/*  790:1147 */             Object lhs = stack[stackTop];
/*  791:     */             boolean valBln;
/*  792:     */             boolean valBln;
/*  793:1148 */             if (rhs == DBL_MRK)
/*  794:     */             {
/*  795:     */               boolean valBln;
/*  796:1149 */               if (lhs == DBL_MRK) {
/*  797:1150 */                 valBln = sDbl[stackTop] == sDbl[(stackTop + 1)];
/*  798:     */               } else {
/*  799:1152 */                 valBln = ScriptRuntime.eqNumber(sDbl[(stackTop + 1)], lhs);
/*  800:     */               }
/*  801:     */             }
/*  802:     */             else
/*  803:     */             {
/*  804:     */               boolean valBln;
/*  805:1155 */               if (lhs == DBL_MRK) {
/*  806:1156 */                 valBln = ScriptRuntime.eqNumber(sDbl[stackTop], rhs);
/*  807:     */               } else {
/*  808:1158 */                 valBln = ScriptRuntime.eq(lhs, rhs);
/*  809:     */               }
/*  810:     */             }
/*  811:1161 */             valBln ^= op == 13;
/*  812:1162 */             stack[stackTop] = ScriptRuntime.wrapBoolean(valBln);
/*  813:1163 */             break;
/*  814:     */           case 46: 
/*  815:     */           case 47: 
/*  816:1167 */             stackTop--;
/*  817:1168 */             boolean valBln = shallowEquals(stack, sDbl, stackTop);
/*  818:1169 */             valBln ^= op == 47;
/*  819:1170 */             stack[stackTop] = ScriptRuntime.wrapBoolean(valBln);
/*  820:1171 */             break;
/*  821:     */           case 7: 
/*  822:1174 */             if (stack_boolean(frame, stackTop--)) {
/*  823:1175 */               frame.pc += 2;
/*  824:     */             }
/*  825:1176 */             break;
/*  826:     */           case 6: 
/*  827:1180 */             if (!stack_boolean(frame, stackTop--)) {
/*  828:1181 */               frame.pc += 2;
/*  829:     */             }
/*  830:1182 */             break;
/*  831:     */           case -6: 
/*  832:1186 */             if (!stack_boolean(frame, stackTop--)) {
/*  833:1187 */               frame.pc += 2;
/*  834:     */             } else {
/*  835:1190 */               stack[(stackTop--)] = null;
/*  836:     */             }
/*  837:1191 */             break;
/*  838:     */           case 5: 
/*  839:     */             break;
/*  840:     */           case -23: 
/*  841:1195 */             stackTop++;
/*  842:1196 */             stack[stackTop] = DBL_MRK;
/*  843:1197 */             sDbl[stackTop] = (frame.pc + 2);
/*  844:1198 */             break;
/*  845:     */           case -24: 
/*  846:1200 */             if (stackTop == frame.emptyStackTop + 1)
/*  847:     */             {
/*  848:1202 */               indexReg += frame.localShift;
/*  849:1203 */               stack[indexReg] = stack[stackTop];
/*  850:1204 */               sDbl[indexReg] = sDbl[stackTop];
/*  851:1205 */               stackTop--;
/*  852:     */             }
/*  853:1209 */             else if (stackTop != frame.emptyStackTop)
/*  854:     */             {
/*  855:1209 */               Kit.codeBug();
/*  856:     */             }
/*  857:     */             break;
/*  858:     */           case -25: 
/*  859:1214 */             if (instructionCounting) {
/*  860:1215 */               addInstructionCount(cx, frame, 0);
/*  861:     */             }
/*  862:1217 */             indexReg += frame.localShift;
/*  863:1218 */             Object value = stack[indexReg];
/*  864:1219 */             if (value != DBL_MRK)
/*  865:     */             {
/*  866:1221 */               throwable = value;
/*  867:     */               break label7530;
/*  868:     */             }
/*  869:1225 */             frame.pc = ((int)sDbl[indexReg]);
/*  870:1226 */             if (instructionCounting) {
/*  871:1227 */               frame.pcPrevBranch = frame.pc;
/*  872:     */             }
/*  873:     */             break;
/*  874:     */           case -4: 
/*  875:1232 */             stack[stackTop] = null;
/*  876:1233 */             stackTop--;
/*  877:1234 */             break;
/*  878:     */           case -5: 
/*  879:1236 */             frame.result = stack[stackTop];
/*  880:1237 */             frame.resultDbl = sDbl[stackTop];
/*  881:1238 */             stack[stackTop] = null;
/*  882:1239 */             stackTop--;
/*  883:1240 */             break;
/*  884:     */           case -1: 
/*  885:1242 */             stack[(stackTop + 1)] = stack[stackTop];
/*  886:1243 */             sDbl[(stackTop + 1)] = sDbl[stackTop];
/*  887:1244 */             stackTop++;
/*  888:1245 */             break;
/*  889:     */           case -2: 
/*  890:1247 */             stack[(stackTop + 1)] = stack[(stackTop - 1)];
/*  891:1248 */             sDbl[(stackTop + 1)] = sDbl[(stackTop - 1)];
/*  892:1249 */             stack[(stackTop + 2)] = stack[stackTop];
/*  893:1250 */             sDbl[(stackTop + 2)] = sDbl[stackTop];
/*  894:1251 */             stackTop += 2;
/*  895:1252 */             break;
/*  896:     */           case -3: 
/*  897:1254 */             Object o = stack[stackTop];
/*  898:1255 */             stack[stackTop] = stack[(stackTop - 1)];
/*  899:1256 */             stack[(stackTop - 1)] = o;
/*  900:1257 */             double d = sDbl[stackTop];
/*  901:1258 */             sDbl[stackTop] = sDbl[(stackTop - 1)];
/*  902:1259 */             sDbl[(stackTop - 1)] = d;
/*  903:1260 */             break;
/*  904:     */           case 4: 
/*  905:1263 */             frame.result = stack[stackTop];
/*  906:1264 */             frame.resultDbl = sDbl[stackTop];
/*  907:1265 */             stackTop--;
/*  908:1266 */             break;
/*  909:     */           case 64: 
/*  910:     */             break;
/*  911:     */           case -22: 
/*  912:1270 */             frame.result = undefined;
/*  913:1271 */             break;
/*  914:     */           case 27: 
/*  915:1273 */             int rIntValue = stack_int32(frame, stackTop);
/*  916:1274 */             stack[stackTop] = DBL_MRK;
/*  917:1275 */             sDbl[stackTop] = (rIntValue ^ 0xFFFFFFFF);
/*  918:1276 */             break;
/*  919:     */           case 9: 
/*  920:     */           case 10: 
/*  921:     */           case 11: 
/*  922:     */           case 18: 
/*  923:     */           case 19: 
/*  924:1283 */             int lIntValue = stack_int32(frame, stackTop - 1);
/*  925:1284 */             int rIntValue = stack_int32(frame, stackTop);
/*  926:1285 */             stack[(--stackTop)] = DBL_MRK;
/*  927:1286 */             switch (op)
/*  928:     */             {
/*  929:     */             case 11: 
/*  930:1288 */               lIntValue &= rIntValue;
/*  931:1289 */               break;
/*  932:     */             case 9: 
/*  933:1291 */               lIntValue |= rIntValue;
/*  934:1292 */               break;
/*  935:     */             case 10: 
/*  936:1294 */               lIntValue ^= rIntValue;
/*  937:1295 */               break;
/*  938:     */             case 18: 
/*  939:1297 */               lIntValue <<= rIntValue;
/*  940:1298 */               break;
/*  941:     */             case 19: 
/*  942:1300 */               lIntValue >>= rIntValue;
/*  943:     */             }
/*  944:1303 */             sDbl[stackTop] = lIntValue;
/*  945:1304 */             break;
/*  946:     */           case 20: 
/*  947:1307 */             double lDbl = stack_double(frame, stackTop - 1);
/*  948:1308 */             int rIntValue = stack_int32(frame, stackTop) & 0x1F;
/*  949:1309 */             stack[(--stackTop)] = DBL_MRK;
/*  950:1310 */             sDbl[stackTop] = (ScriptRuntime.toUint32(lDbl) >>> rIntValue);
/*  951:1311 */             break;
/*  952:     */           case 28: 
/*  953:     */           case 29: 
/*  954:1315 */             double rDbl = stack_double(frame, stackTop);
/*  955:1316 */             stack[stackTop] = DBL_MRK;
/*  956:1317 */             if (op == 29) {
/*  957:1318 */               rDbl = -rDbl;
/*  958:     */             }
/*  959:1320 */             sDbl[stackTop] = rDbl;
/*  960:1321 */             break;
/*  961:     */           case 21: 
/*  962:1324 */             stackTop--;
/*  963:1325 */             do_add(stack, sDbl, stackTop, cx);
/*  964:1326 */             break;
/*  965:     */           case 22: 
/*  966:     */           case 23: 
/*  967:     */           case 24: 
/*  968:     */           case 25: 
/*  969:1331 */             double rDbl = stack_double(frame, stackTop);
/*  970:1332 */             stackTop--;
/*  971:1333 */             double lDbl = stack_double(frame, stackTop);
/*  972:1334 */             stack[stackTop] = DBL_MRK;
/*  973:1335 */             switch (op)
/*  974:     */             {
/*  975:     */             case 22: 
/*  976:1337 */               lDbl -= rDbl;
/*  977:1338 */               break;
/*  978:     */             case 23: 
/*  979:1340 */               lDbl *= rDbl;
/*  980:1341 */               break;
/*  981:     */             case 24: 
/*  982:1343 */               lDbl /= rDbl;
/*  983:1344 */               break;
/*  984:     */             case 25: 
/*  985:1346 */               lDbl %= rDbl;
/*  986:     */             }
/*  987:1349 */             sDbl[stackTop] = lDbl;
/*  988:1350 */             break;
/*  989:     */           case 26: 
/*  990:1353 */             stack[stackTop] = ScriptRuntime.wrapBoolean(!stack_boolean(frame, stackTop) ? 1 : false);
/*  991:     */             
/*  992:1355 */             break;
/*  993:     */           case 49: 
/*  994:1357 */             stack[(++stackTop)] = ScriptRuntime.bind(cx, frame.scope, stringReg);
/*  995:1358 */             break;
/*  996:     */           case 8: 
/*  997:     */           case 73: 
/*  998:1361 */             Object rhs = stack[stackTop];
/*  999:1362 */             if (rhs == DBL_MRK) {
/* 1000:1362 */               rhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1001:     */             }
/* 1002:1363 */             stackTop--;
/* 1003:1364 */             Scriptable lhs = (Scriptable)stack[stackTop];
/* 1004:1365 */             stack[stackTop] = (op == 8 ? ScriptRuntime.setName(lhs, rhs, cx, frame.scope, stringReg) : ScriptRuntime.strictSetName(lhs, rhs, cx, frame.scope, stringReg));
/* 1005:     */             
/* 1006:     */ 
/* 1007:     */ 
/* 1008:     */ 
/* 1009:1370 */             break;
/* 1010:     */           case -59: 
/* 1011:1373 */             Object rhs = stack[stackTop];
/* 1012:1374 */             if (rhs == DBL_MRK) {
/* 1013:1374 */               rhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1014:     */             }
/* 1015:1375 */             stackTop--;
/* 1016:1376 */             Scriptable lhs = (Scriptable)stack[stackTop];
/* 1017:1377 */             stack[stackTop] = ScriptRuntime.setConst(lhs, rhs, cx, stringReg);
/* 1018:1378 */             break;
/* 1019:     */           case 31: 
/* 1020:1381 */             Object rhs = stack[stackTop];
/* 1021:1382 */             if (rhs == DBL_MRK) {
/* 1022:1382 */               rhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1023:     */             }
/* 1024:1383 */             stackTop--;
/* 1025:1384 */             Object lhs = stack[stackTop];
/* 1026:1385 */             if (lhs == DBL_MRK) {
/* 1027:1385 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1028:     */             }
/* 1029:1386 */             stack[stackTop] = ScriptRuntime.delete(lhs, rhs, cx);
/* 1030:1387 */             break;
/* 1031:     */           case 34: 
/* 1032:1390 */             Object lhs = stack[stackTop];
/* 1033:1391 */             if (lhs == DBL_MRK) {
/* 1034:1391 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1035:     */             }
/* 1036:1392 */             stack[stackTop] = ScriptRuntime.getObjectPropNoWarn(lhs, stringReg, cx);
/* 1037:1393 */             break;
/* 1038:     */           case 33: 
/* 1039:1396 */             Object lhs = stack[stackTop];
/* 1040:1397 */             if (lhs == DBL_MRK) {
/* 1041:1397 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1042:     */             }
/* 1043:1398 */             stack[stackTop] = ScriptRuntime.getObjectProp(lhs, stringReg, cx, frame.scope);
/* 1044:1399 */             break;
/* 1045:     */           case 35: 
/* 1046:1402 */             Object rhs = stack[stackTop];
/* 1047:1403 */             if (rhs == DBL_MRK) {
/* 1048:1403 */               rhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1049:     */             }
/* 1050:1404 */             stackTop--;
/* 1051:1405 */             Object lhs = stack[stackTop];
/* 1052:1406 */             if (lhs == DBL_MRK) {
/* 1053:1406 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1054:     */             }
/* 1055:1407 */             stack[stackTop] = ScriptRuntime.setObjectProp(lhs, stringReg, rhs, cx);
/* 1056:     */             
/* 1057:1409 */             break;
/* 1058:     */           case -9: 
/* 1059:1412 */             Object lhs = stack[stackTop];
/* 1060:1413 */             if (lhs == DBL_MRK) {
/* 1061:1413 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1062:     */             }
/* 1063:1414 */             stack[stackTop] = ScriptRuntime.propIncrDecr(lhs, stringReg, cx, iCode[frame.pc]);
/* 1064:     */             
/* 1065:1416 */             frame.pc += 1;
/* 1066:1417 */             break;
/* 1067:     */           case 36: 
/* 1068:1420 */             stackTop--;
/* 1069:1421 */             Object lhs = stack[stackTop];
/* 1070:1422 */             if (lhs == DBL_MRK) {
/* 1071:1423 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1072:     */             }
/* 1073:1426 */             Object id = stack[(stackTop + 1)];
/* 1074:     */             Object value;
/* 1075:     */             Object value;
/* 1076:1427 */             if (id != DBL_MRK)
/* 1077:     */             {
/* 1078:1428 */               value = ScriptRuntime.getObjectElem(lhs, id, cx, frame.scope);
/* 1079:     */             }
/* 1080:     */             else
/* 1081:     */             {
/* 1082:1430 */               double d = sDbl[(stackTop + 1)];
/* 1083:1431 */               value = ScriptRuntime.getObjectIndex(lhs, d, cx);
/* 1084:     */             }
/* 1085:1433 */             stack[stackTop] = value;
/* 1086:1434 */             break;
/* 1087:     */           case 37: 
/* 1088:1437 */             stackTop -= 2;
/* 1089:1438 */             Object rhs = stack[(stackTop + 2)];
/* 1090:1439 */             if (rhs == DBL_MRK) {
/* 1091:1440 */               rhs = ScriptRuntime.wrapNumber(sDbl[(stackTop + 2)]);
/* 1092:     */             }
/* 1093:1442 */             Object lhs = stack[stackTop];
/* 1094:1443 */             if (lhs == DBL_MRK) {
/* 1095:1444 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1096:     */             }
/* 1097:1447 */             Object id = stack[(stackTop + 1)];
/* 1098:     */             Object value;
/* 1099:     */             Object value;
/* 1100:1448 */             if (id != DBL_MRK)
/* 1101:     */             {
/* 1102:1449 */               value = ScriptRuntime.setObjectElem(lhs, id, rhs, cx);
/* 1103:     */             }
/* 1104:     */             else
/* 1105:     */             {
/* 1106:1451 */               double d = sDbl[(stackTop + 1)];
/* 1107:1452 */               value = ScriptRuntime.setObjectIndex(lhs, d, rhs, cx);
/* 1108:     */             }
/* 1109:1454 */             stack[stackTop] = value;
/* 1110:1455 */             break;
/* 1111:     */           case -10: 
/* 1112:1458 */             Object rhs = stack[stackTop];
/* 1113:1459 */             if (rhs == DBL_MRK) {
/* 1114:1459 */               rhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1115:     */             }
/* 1116:1460 */             stackTop--;
/* 1117:1461 */             Object lhs = stack[stackTop];
/* 1118:1462 */             if (lhs == DBL_MRK) {
/* 1119:1462 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1120:     */             }
/* 1121:1463 */             stack[stackTop] = ScriptRuntime.elemIncrDecr(lhs, rhs, cx, iCode[frame.pc]);
/* 1122:     */             
/* 1123:1465 */             frame.pc += 1;
/* 1124:1466 */             break;
/* 1125:     */           case 67: 
/* 1126:1469 */             Ref ref = (Ref)stack[stackTop];
/* 1127:1470 */             stack[stackTop] = ScriptRuntime.refGet(ref, cx);
/* 1128:1471 */             break;
/* 1129:     */           case 68: 
/* 1130:1474 */             Object value = stack[stackTop];
/* 1131:1475 */             if (value == DBL_MRK) {
/* 1132:1475 */               value = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1133:     */             }
/* 1134:1476 */             stackTop--;
/* 1135:1477 */             Ref ref = (Ref)stack[stackTop];
/* 1136:1478 */             stack[stackTop] = ScriptRuntime.refSet(ref, value, cx);
/* 1137:1479 */             break;
/* 1138:     */           case 69: 
/* 1139:1482 */             Ref ref = (Ref)stack[stackTop];
/* 1140:1483 */             stack[stackTop] = ScriptRuntime.refDel(ref, cx);
/* 1141:1484 */             break;
/* 1142:     */           case -11: 
/* 1143:1487 */             Ref ref = (Ref)stack[stackTop];
/* 1144:1488 */             stack[stackTop] = ScriptRuntime.refIncrDecr(ref, cx, iCode[frame.pc]);
/* 1145:1489 */             frame.pc += 1;
/* 1146:1490 */             break;
/* 1147:     */           case 54: 
/* 1148:1493 */             stackTop++;
/* 1149:1494 */             indexReg += frame.localShift;
/* 1150:1495 */             stack[stackTop] = stack[indexReg];
/* 1151:1496 */             sDbl[stackTop] = sDbl[indexReg];
/* 1152:1497 */             break;
/* 1153:     */           case -56: 
/* 1154:1499 */             indexReg += frame.localShift;
/* 1155:1500 */             stack[indexReg] = null;
/* 1156:1501 */             break;
/* 1157:     */           case -15: 
/* 1158:1504 */             stackTop++;
/* 1159:1505 */             stack[stackTop] = ScriptRuntime.getNameFunctionAndThis(stringReg, cx, frame.scope);
/* 1160:     */             
/* 1161:1507 */             stackTop++;
/* 1162:1508 */             stack[stackTop] = ScriptRuntime.lastStoredScriptable(cx);
/* 1163:1509 */             break;
/* 1164:     */           case -16: 
/* 1165:1511 */             Object obj = stack[stackTop];
/* 1166:1512 */             if (obj == DBL_MRK) {
/* 1167:1512 */               obj = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1168:     */             }
/* 1169:1514 */             stack[stackTop] = ScriptRuntime.getPropFunctionAndThis(obj, stringReg, cx, frame.scope);
/* 1170:     */             
/* 1171:1516 */             stackTop++;
/* 1172:1517 */             stack[stackTop] = ScriptRuntime.lastStoredScriptable(cx);
/* 1173:1518 */             break;
/* 1174:     */           case -17: 
/* 1175:1521 */             Object obj = stack[(stackTop - 1)];
/* 1176:1522 */             if (obj == DBL_MRK) {
/* 1177:1522 */               obj = ScriptRuntime.wrapNumber(sDbl[(stackTop - 1)]);
/* 1178:     */             }
/* 1179:1523 */             Object id = stack[stackTop];
/* 1180:1524 */             if (id == DBL_MRK) {
/* 1181:1524 */               id = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1182:     */             }
/* 1183:1525 */             stack[(stackTop - 1)] = ScriptRuntime.getElemFunctionAndThis(obj, id, cx);
/* 1184:1526 */             stack[stackTop] = ScriptRuntime.lastStoredScriptable(cx);
/* 1185:1527 */             break;
/* 1186:     */           case -18: 
/* 1187:1530 */             Object value = stack[stackTop];
/* 1188:1531 */             if (value == DBL_MRK) {
/* 1189:1531 */               value = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1190:     */             }
/* 1191:1532 */             stack[stackTop] = ScriptRuntime.getValueFunctionAndThis(value, cx);
/* 1192:1533 */             stackTop++;
/* 1193:1534 */             stack[stackTop] = ScriptRuntime.lastStoredScriptable(cx);
/* 1194:1535 */             break;
/* 1195:     */           case -21: 
/* 1196:1538 */             if (instructionCounting) {
/* 1197:1539 */               cx.instructionCount += 100;
/* 1198:     */             }
/* 1199:1541 */             int callType = iCode[frame.pc] & 0xFF;
/* 1200:1542 */             boolean isNew = iCode[(frame.pc + 1)] != 0;
/* 1201:1543 */             int sourceLine = getIndex(iCode, frame.pc + 2);
/* 1202:1546 */             if (isNew)
/* 1203:     */             {
/* 1204:1548 */               stackTop -= indexReg;
/* 1205:     */               
/* 1206:1550 */               Object function = stack[stackTop];
/* 1207:1551 */               if (function == DBL_MRK) {
/* 1208:1552 */                 function = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1209:     */               }
/* 1210:1553 */               Object[] outArgs = getArgsArray(stack, sDbl, stackTop + 1, indexReg);
/* 1211:     */               
/* 1212:1555 */               stack[stackTop] = ScriptRuntime.newSpecial(cx, function, outArgs, frame.scope, callType);
/* 1213:     */             }
/* 1214:     */             else
/* 1215:     */             {
/* 1216:1559 */               stackTop -= 1 + indexReg;
/* 1217:     */               
/* 1218:     */ 
/* 1219:     */ 
/* 1220:1563 */               Scriptable functionThis = (Scriptable)stack[(stackTop + 1)];
/* 1221:1564 */               Callable function = (Callable)stack[stackTop];
/* 1222:1565 */               Object[] outArgs = getArgsArray(stack, sDbl, stackTop + 2, indexReg);
/* 1223:     */               
/* 1224:1567 */               stack[stackTop] = ScriptRuntime.callSpecial(cx, function, functionThis, outArgs, frame.scope, frame.thisObj, callType, frame.idata.itsSourceFile, sourceLine);
/* 1225:     */             }
/* 1226:1572 */             frame.pc += 4;
/* 1227:1573 */             break;
/* 1228:     */           case -55: 
/* 1229:     */           case 38: 
/* 1230:     */           case 70: 
/* 1231:1578 */             if (instructionCounting) {
/* 1232:1579 */               cx.instructionCount += 100;
/* 1233:     */             }
/* 1234:1583 */             stackTop -= 1 + indexReg;
/* 1235:     */             
/* 1236:     */ 
/* 1237:     */ 
/* 1238:1587 */             Callable fun = (Callable)stack[stackTop];
/* 1239:1588 */             Scriptable funThisObj = (Scriptable)stack[(stackTop + 1)];
/* 1240:1589 */             if (op == 70)
/* 1241:     */             {
/* 1242:1590 */               Object[] outArgs = getArgsArray(stack, sDbl, stackTop + 2, indexReg);
/* 1243:     */               
/* 1244:1592 */               stack[stackTop] = ScriptRuntime.callRef(fun, funThisObj, outArgs, cx);
/* 1245:     */             }
/* 1246:     */             else
/* 1247:     */             {
/* 1248:1596 */               Scriptable calleeScope = frame.scope;
/* 1249:1597 */               if (frame.useActivation) {
/* 1250:1598 */                 calleeScope = ScriptableObject.getTopLevelScope(frame.scope);
/* 1251:     */               }
/* 1252:1600 */               if ((fun instanceof InterpretedFunction))
/* 1253:     */               {
/* 1254:1601 */                 InterpretedFunction ifun = (InterpretedFunction)fun;
/* 1255:1602 */                 if (frame.fnOrScript.securityDomain == ifun.securityDomain)
/* 1256:     */                 {
/* 1257:1603 */                   CallFrame callParentFrame = frame;
/* 1258:1604 */                   CallFrame calleeFrame = new CallFrame(null);
/* 1259:1605 */                   if (op == -55)
/* 1260:     */                   {
/* 1261:1621 */                     callParentFrame = frame.parentFrame;
/* 1262:     */                     
/* 1263:     */ 
/* 1264:1624 */                     exitFrame(cx, frame, null);
/* 1265:     */                   }
/* 1266:1626 */                   initFrame(cx, calleeScope, funThisObj, stack, sDbl, stackTop + 2, indexReg, ifun, callParentFrame, calleeFrame);
/* 1267:1629 */                   if (op != -55)
/* 1268:     */                   {
/* 1269:1630 */                     frame.savedStackTop = stackTop;
/* 1270:1631 */                     frame.savedCallOp = op;
/* 1271:     */                   }
/* 1272:1633 */                   frame = calleeFrame;
/* 1273:1634 */                   break;
/* 1274:     */                 }
/* 1275:     */               }
/* 1276:1638 */               if ((fun instanceof NativeContinuation))
/* 1277:     */               {
/* 1278:1641 */                 ContinuationJump cjump = new ContinuationJump((NativeContinuation)fun, frame);
/* 1279:1645 */                 if (indexReg == 0)
/* 1280:     */                 {
/* 1281:1646 */                   cjump.result = undefined;
/* 1282:     */                 }
/* 1283:     */                 else
/* 1284:     */                 {
/* 1285:1648 */                   cjump.result = stack[(stackTop + 2)];
/* 1286:1649 */                   cjump.resultDbl = sDbl[(stackTop + 2)];
/* 1287:     */                 }
/* 1288:1653 */                 throwable = cjump;
/* 1289:     */                 break label7530;
/* 1290:     */               }
/* 1291:1657 */               if ((fun instanceof IdFunctionObject))
/* 1292:     */               {
/* 1293:1658 */                 IdFunctionObject ifun = (IdFunctionObject)fun;
/* 1294:1659 */                 if (NativeContinuation.isContinuationConstructor(ifun))
/* 1295:     */                 {
/* 1296:1660 */                   frame.stack[stackTop] = captureContinuation(cx, frame.parentFrame, false);
/* 1297:     */                   
/* 1298:1662 */                   continue;
/* 1299:     */                 }
/* 1300:1666 */                 if (BaseFunction.isApplyOrCall(ifun))
/* 1301:     */                 {
/* 1302:1667 */                   Callable applyCallable = ScriptRuntime.getCallable(funThisObj);
/* 1303:1668 */                   if ((applyCallable instanceof InterpretedFunction))
/* 1304:     */                   {
/* 1305:1669 */                     InterpretedFunction iApplyCallable = (InterpretedFunction)applyCallable;
/* 1306:1670 */                     if (frame.fnOrScript.securityDomain == iApplyCallable.securityDomain)
/* 1307:     */                     {
/* 1308:1671 */                       frame = initFrameForApplyOrCall(cx, frame, indexReg, stack, sDbl, stackTop, op, calleeScope, ifun, iApplyCallable);
/* 1309:     */                       
/* 1310:     */ 
/* 1311:1674 */                       break;
/* 1312:     */                     }
/* 1313:     */                   }
/* 1314:     */                 }
/* 1315:     */               }
/* 1316:1682 */               if ((fun instanceof ScriptRuntime.NoSuchMethodShim))
/* 1317:     */               {
/* 1318:1684 */                 ScriptRuntime.NoSuchMethodShim noSuchMethodShim = (ScriptRuntime.NoSuchMethodShim)fun;
/* 1319:1685 */                 Callable noSuchMethodMethod = noSuchMethodShim.noSuchMethodMethod;
/* 1320:1687 */                 if ((noSuchMethodMethod instanceof InterpretedFunction))
/* 1321:     */                 {
/* 1322:1688 */                   InterpretedFunction ifun = (InterpretedFunction)noSuchMethodMethod;
/* 1323:1689 */                   if (frame.fnOrScript.securityDomain == ifun.securityDomain)
/* 1324:     */                   {
/* 1325:1690 */                     frame = initFrameForNoSuchMethod(cx, frame, indexReg, stack, sDbl, stackTop, op, funThisObj, calleeScope, noSuchMethodShim, ifun);
/* 1326:     */                     
/* 1327:     */ 
/* 1328:1693 */                     break;
/* 1329:     */                   }
/* 1330:     */                 }
/* 1331:     */               }
/* 1332:1698 */               cx.lastInterpreterFrame = frame;
/* 1333:1699 */               frame.savedCallOp = op;
/* 1334:1700 */               frame.savedStackTop = stackTop;
/* 1335:1701 */               stack[stackTop] = fun.call(cx, calleeScope, funThisObj, getArgsArray(stack, sDbl, stackTop + 2, indexReg));
/* 1336:     */             }
/* 1337:1704 */             break;
/* 1338:     */           case 30: 
/* 1339:1707 */             if (instructionCounting) {
/* 1340:1708 */               cx.instructionCount += 100;
/* 1341:     */             }
/* 1342:1712 */             stackTop -= indexReg;
/* 1343:     */             
/* 1344:1714 */             Object lhs = stack[stackTop];
/* 1345:1715 */             if ((lhs instanceof InterpretedFunction))
/* 1346:     */             {
/* 1347:1716 */               InterpretedFunction f = (InterpretedFunction)lhs;
/* 1348:1717 */               if (frame.fnOrScript.securityDomain == f.securityDomain)
/* 1349:     */               {
/* 1350:1718 */                 Scriptable newInstance = f.createObject(cx, frame.scope);
/* 1351:1719 */                 CallFrame calleeFrame = new CallFrame(null);
/* 1352:1720 */                 initFrame(cx, frame.scope, newInstance, stack, sDbl, stackTop + 1, indexReg, f, frame, calleeFrame);
/* 1353:     */                 
/* 1354:     */ 
/* 1355:     */ 
/* 1356:1724 */                 stack[stackTop] = newInstance;
/* 1357:1725 */                 frame.savedStackTop = stackTop;
/* 1358:1726 */                 frame.savedCallOp = op;
/* 1359:1727 */                 frame = calleeFrame;
/* 1360:1728 */                 break;
/* 1361:     */               }
/* 1362:     */             }
/* 1363:1731 */             if (!(lhs instanceof Function))
/* 1364:     */             {
/* 1365:1732 */               if (lhs == DBL_MRK) {
/* 1366:1732 */                 lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1367:     */               }
/* 1368:1733 */               throw ScriptRuntime.notFunctionError(lhs);
/* 1369:     */             }
/* 1370:1735 */             Function fun = (Function)lhs;
/* 1371:1737 */             if ((fun instanceof IdFunctionObject))
/* 1372:     */             {
/* 1373:1738 */               IdFunctionObject ifun = (IdFunctionObject)fun;
/* 1374:1739 */               if (NativeContinuation.isContinuationConstructor(ifun))
/* 1375:     */               {
/* 1376:1740 */                 frame.stack[stackTop] = captureContinuation(cx, frame.parentFrame, false);
/* 1377:     */                 
/* 1378:1742 */                 continue;
/* 1379:     */               }
/* 1380:     */             }
/* 1381:1746 */             Object[] outArgs = getArgsArray(stack, sDbl, stackTop + 1, indexReg);
/* 1382:1747 */             stack[stackTop] = fun.construct(cx, frame.scope, outArgs);
/* 1383:1748 */             break;
/* 1384:     */           case 32: 
/* 1385:1751 */             Object lhs = stack[stackTop];
/* 1386:1752 */             if (lhs == DBL_MRK) {
/* 1387:1752 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1388:     */             }
/* 1389:1753 */             stack[stackTop] = ScriptRuntime.typeof(lhs);
/* 1390:1754 */             break;
/* 1391:     */           case -14: 
/* 1392:1757 */             stack[(++stackTop)] = ScriptRuntime.typeofName(frame.scope, stringReg);
/* 1393:1758 */             break;
/* 1394:     */           case 41: 
/* 1395:1760 */             stack[(++stackTop)] = stringReg;
/* 1396:1761 */             break;
/* 1397:     */           case -27: 
/* 1398:1763 */             stackTop++;
/* 1399:1764 */             stack[stackTop] = DBL_MRK;
/* 1400:1765 */             sDbl[stackTop] = getShort(iCode, frame.pc);
/* 1401:1766 */             frame.pc += 2;
/* 1402:1767 */             break;
/* 1403:     */           case -28: 
/* 1404:1769 */             stackTop++;
/* 1405:1770 */             stack[stackTop] = DBL_MRK;
/* 1406:1771 */             sDbl[stackTop] = getInt(iCode, frame.pc);
/* 1407:1772 */             frame.pc += 4;
/* 1408:1773 */             break;
/* 1409:     */           case 40: 
/* 1410:1775 */             stackTop++;
/* 1411:1776 */             stack[stackTop] = DBL_MRK;
/* 1412:1777 */             sDbl[stackTop] = frame.idata.itsDoubleTable[indexReg];
/* 1413:1778 */             break;
/* 1414:     */           case 39: 
/* 1415:1780 */             stack[(++stackTop)] = ScriptRuntime.name(cx, frame.scope, stringReg);
/* 1416:1781 */             break;
/* 1417:     */           case -8: 
/* 1418:1783 */             stack[(++stackTop)] = ScriptRuntime.nameIncrDecr(frame.scope, stringReg, cx, iCode[frame.pc]);
/* 1419:     */             
/* 1420:1785 */             frame.pc += 1;
/* 1421:1786 */             break;
/* 1422:     */           case -61: 
/* 1423:1788 */             indexReg = iCode[(frame.pc++)];
/* 1424:     */           case 156: 
/* 1425:1791 */             if (!frame.useActivation)
/* 1426:     */             {
/* 1427:1792 */               if ((varAttributes[indexReg] & 0x1) == 0) {
/* 1428:1793 */                 throw Context.reportRuntimeError1("msg.var.redecl", frame.idata.argNames[indexReg]);
/* 1429:     */               }
/* 1430:1796 */               if ((varAttributes[indexReg] & 0x8) != 0)
/* 1431:     */               {
/* 1432:1799 */                 vars[indexReg] = stack[stackTop];
/* 1433:1800 */                 varAttributes[indexReg] &= 0xFFFFFFF7;
/* 1434:1801 */                 varDbls[indexReg] = sDbl[stackTop];
/* 1435:     */               }
/* 1436:     */             }
/* 1437:     */             else
/* 1438:     */             {
/* 1439:1804 */               Object val = stack[stackTop];
/* 1440:1805 */               if (val == DBL_MRK) {
/* 1441:1805 */                 val = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1442:     */               }
/* 1443:1806 */               stringReg = frame.idata.argNames[indexReg];
/* 1444:1807 */               if ((frame.scope instanceof ConstProperties))
/* 1445:     */               {
/* 1446:1808 */                 ConstProperties cp = (ConstProperties)frame.scope;
/* 1447:1809 */                 cp.putConst(stringReg, frame.scope, val);
/* 1448:     */               }
/* 1449:     */               else
/* 1450:     */               {
/* 1451:1811 */                 throw Kit.codeBug();
/* 1452:     */               }
/* 1453:     */             }
/* 1454:1813 */             break;
/* 1455:     */           case -49: 
/* 1456:1815 */             indexReg = iCode[(frame.pc++)];
/* 1457:     */           case 56: 
/* 1458:1818 */             if (!frame.useActivation)
/* 1459:     */             {
/* 1460:1819 */               if ((varAttributes[indexReg] & 0x1) == 0)
/* 1461:     */               {
/* 1462:1820 */                 vars[indexReg] = stack[stackTop];
/* 1463:1821 */                 varDbls[indexReg] = sDbl[stackTop];
/* 1464:     */               }
/* 1465:     */             }
/* 1466:     */             else
/* 1467:     */             {
/* 1468:1824 */               Object val = stack[stackTop];
/* 1469:1825 */               if (val == DBL_MRK) {
/* 1470:1825 */                 val = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1471:     */               }
/* 1472:1826 */               stringReg = frame.idata.argNames[indexReg];
/* 1473:1827 */               frame.scope.put(stringReg, frame.scope, val);
/* 1474:     */             }
/* 1475:1829 */             break;
/* 1476:     */           case -48: 
/* 1477:1831 */             indexReg = iCode[(frame.pc++)];
/* 1478:     */           case 55: 
/* 1479:1834 */             stackTop++;
/* 1480:1835 */             if (!frame.useActivation)
/* 1481:     */             {
/* 1482:1836 */               stack[stackTop] = vars[indexReg];
/* 1483:1837 */               sDbl[stackTop] = varDbls[indexReg];
/* 1484:     */             }
/* 1485:     */             else
/* 1486:     */             {
/* 1487:1839 */               stringReg = frame.idata.argNames[indexReg];
/* 1488:1840 */               stack[stackTop] = frame.scope.get(stringReg, frame.scope);
/* 1489:     */             }
/* 1490:1842 */             break;
/* 1491:     */           case -7: 
/* 1492:1845 */             stackTop++;
/* 1493:1846 */             int incrDecrMask = iCode[frame.pc];
/* 1494:1847 */             if (!frame.useActivation)
/* 1495:     */             {
/* 1496:1848 */               stack[stackTop] = DBL_MRK;
/* 1497:1849 */               Object varValue = vars[indexReg];
/* 1498:     */               double d;
/* 1499:     */               double d;
/* 1500:1851 */               if (varValue == DBL_MRK)
/* 1501:     */               {
/* 1502:1852 */                 d = varDbls[indexReg];
/* 1503:     */               }
/* 1504:     */               else
/* 1505:     */               {
/* 1506:1854 */                 d = ScriptRuntime.toNumber(varValue);
/* 1507:1855 */                 vars[indexReg] = DBL_MRK;
/* 1508:     */               }
/* 1509:1857 */               double d2 = (incrDecrMask & 0x1) == 0 ? d + 1.0D : d - 1.0D;
/* 1510:     */               
/* 1511:1859 */               varDbls[indexReg] = d2;
/* 1512:1860 */               sDbl[stackTop] = ((incrDecrMask & 0x2) == 0 ? d2 : d);
/* 1513:     */             }
/* 1514:     */             else
/* 1515:     */             {
/* 1516:1862 */               String varName = frame.idata.argNames[indexReg];
/* 1517:1863 */               stack[stackTop] = ScriptRuntime.nameIncrDecr(frame.scope, varName, cx, incrDecrMask);
/* 1518:     */             }
/* 1519:1866 */             frame.pc += 1;
/* 1520:1867 */             break;
/* 1521:     */           case -51: 
/* 1522:1870 */             stackTop++;
/* 1523:1871 */             stack[stackTop] = DBL_MRK;
/* 1524:1872 */             sDbl[stackTop] = 0.0D;
/* 1525:1873 */             break;
/* 1526:     */           case -52: 
/* 1527:1875 */             stackTop++;
/* 1528:1876 */             stack[stackTop] = DBL_MRK;
/* 1529:1877 */             sDbl[stackTop] = 1.0D;
/* 1530:1878 */             break;
/* 1531:     */           case 42: 
/* 1532:1880 */             stack[(++stackTop)] = null;
/* 1533:1881 */             break;
/* 1534:     */           case 43: 
/* 1535:1883 */             stack[(++stackTop)] = frame.thisObj;
/* 1536:1884 */             break;
/* 1537:     */           case 63: 
/* 1538:1886 */             stack[(++stackTop)] = frame.fnOrScript;
/* 1539:1887 */             break;
/* 1540:     */           case 44: 
/* 1541:1889 */             stack[(++stackTop)] = Boolean.FALSE;
/* 1542:1890 */             break;
/* 1543:     */           case 45: 
/* 1544:1892 */             stack[(++stackTop)] = Boolean.TRUE;
/* 1545:1893 */             break;
/* 1546:     */           case -50: 
/* 1547:1895 */             stack[(++stackTop)] = undefined;
/* 1548:1896 */             break;
/* 1549:     */           case 2: 
/* 1550:1898 */             Object lhs = stack[stackTop];
/* 1551:1899 */             if (lhs == DBL_MRK) {
/* 1552:1899 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1553:     */             }
/* 1554:1900 */             stackTop--;
/* 1555:1901 */             frame.scope = ScriptRuntime.enterWith(lhs, cx, frame.scope);
/* 1556:1902 */             break;
/* 1557:     */           case 3: 
/* 1558:1905 */             frame.scope = ScriptRuntime.leaveWith(frame.scope);
/* 1559:1906 */             break;
/* 1560:     */           case 57: 
/* 1561:1911 */             stackTop--;
/* 1562:1912 */             indexReg += frame.localShift;
/* 1563:     */             
/* 1564:1914 */             boolean afterFirstScope = frame.idata.itsICode[frame.pc] != 0;
/* 1565:1915 */             Throwable caughtException = (Throwable)stack[(stackTop + 1)];
/* 1566:     */             Scriptable lastCatchScope;
/* 1567:     */             Scriptable lastCatchScope;
/* 1568:1917 */             if (!afterFirstScope) {
/* 1569:1918 */               lastCatchScope = null;
/* 1570:     */             } else {
/* 1571:1920 */               lastCatchScope = (Scriptable)stack[indexReg];
/* 1572:     */             }
/* 1573:1922 */             stack[indexReg] = ScriptRuntime.newCatchScope(caughtException, lastCatchScope, stringReg, cx, frame.scope);
/* 1574:     */             
/* 1575:     */ 
/* 1576:1925 */             frame.pc += 1;
/* 1577:1926 */             break;
/* 1578:     */           case 58: 
/* 1579:     */           case 59: 
/* 1580:     */           case 60: 
/* 1581:1931 */             Object lhs = stack[stackTop];
/* 1582:1932 */             if (lhs == DBL_MRK) {
/* 1583:1932 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1584:     */             }
/* 1585:1933 */             stackTop--;
/* 1586:1934 */             indexReg += frame.localShift;
/* 1587:1935 */             int enumType = op == 59 ? 1 : op == 58 ? 0 : 2;
/* 1588:     */             
/* 1589:     */ 
/* 1590:     */ 
/* 1591:     */ 
/* 1592:1940 */             stack[indexReg] = ScriptRuntime.enumInit(lhs, cx, enumType);
/* 1593:1941 */             break;
/* 1594:     */           case 61: 
/* 1595:     */           case 62: 
/* 1596:1945 */             indexReg += frame.localShift;
/* 1597:1946 */             Object val = stack[indexReg];
/* 1598:1947 */             stackTop++;
/* 1599:1948 */             stack[stackTop] = (op == 61 ? ScriptRuntime.enumNext(val) : ScriptRuntime.enumId(val, cx));
/* 1600:     */             
/* 1601:     */ 
/* 1602:1951 */             break;
/* 1603:     */           case 71: 
/* 1604:1955 */             Object obj = stack[stackTop];
/* 1605:1956 */             if (obj == DBL_MRK) {
/* 1606:1956 */               obj = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1607:     */             }
/* 1608:1957 */             stack[stackTop] = ScriptRuntime.specialRef(obj, stringReg, cx);
/* 1609:1958 */             break;
/* 1610:     */           case 77: 
/* 1611:1962 */             Object elem = stack[stackTop];
/* 1612:1963 */             if (elem == DBL_MRK) {
/* 1613:1963 */               elem = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1614:     */             }
/* 1615:1964 */             stackTop--;
/* 1616:1965 */             Object obj = stack[stackTop];
/* 1617:1966 */             if (obj == DBL_MRK) {
/* 1618:1966 */               obj = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1619:     */             }
/* 1620:1967 */             stack[stackTop] = ScriptRuntime.memberRef(obj, elem, cx, indexReg);
/* 1621:1968 */             break;
/* 1622:     */           case 78: 
/* 1623:1972 */             Object elem = stack[stackTop];
/* 1624:1973 */             if (elem == DBL_MRK) {
/* 1625:1973 */               elem = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1626:     */             }
/* 1627:1974 */             stackTop--;
/* 1628:1975 */             Object ns = stack[stackTop];
/* 1629:1976 */             if (ns == DBL_MRK) {
/* 1630:1976 */               ns = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1631:     */             }
/* 1632:1977 */             stackTop--;
/* 1633:1978 */             Object obj = stack[stackTop];
/* 1634:1979 */             if (obj == DBL_MRK) {
/* 1635:1979 */               obj = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1636:     */             }
/* 1637:1980 */             stack[stackTop] = ScriptRuntime.memberRef(obj, ns, elem, cx, indexReg);
/* 1638:1981 */             break;
/* 1639:     */           case 79: 
/* 1640:1985 */             Object name = stack[stackTop];
/* 1641:1986 */             if (name == DBL_MRK) {
/* 1642:1986 */               name = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1643:     */             }
/* 1644:1987 */             stack[stackTop] = ScriptRuntime.nameRef(name, cx, frame.scope, indexReg);
/* 1645:     */             
/* 1646:1989 */             break;
/* 1647:     */           case 80: 
/* 1648:1993 */             Object name = stack[stackTop];
/* 1649:1994 */             if (name == DBL_MRK) {
/* 1650:1994 */               name = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1651:     */             }
/* 1652:1995 */             stackTop--;
/* 1653:1996 */             Object ns = stack[stackTop];
/* 1654:1997 */             if (ns == DBL_MRK) {
/* 1655:1997 */               ns = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1656:     */             }
/* 1657:1998 */             stack[stackTop] = ScriptRuntime.nameRef(ns, name, cx, frame.scope, indexReg);
/* 1658:     */             
/* 1659:2000 */             break;
/* 1660:     */           case -12: 
/* 1661:2003 */             indexReg += frame.localShift;
/* 1662:2004 */             frame.scope = ((Scriptable)stack[indexReg]);
/* 1663:2005 */             break;
/* 1664:     */           case -13: 
/* 1665:2007 */             indexReg += frame.localShift;
/* 1666:2008 */             stack[indexReg] = frame.scope;
/* 1667:2009 */             break;
/* 1668:     */           case -19: 
/* 1669:2011 */             stack[(++stackTop)] = InterpretedFunction.createFunction(cx, frame.scope, frame.fnOrScript, indexReg);
/* 1670:     */             
/* 1671:     */ 
/* 1672:2014 */             break;
/* 1673:     */           case -20: 
/* 1674:2016 */             initFunction(cx, frame.scope, frame.fnOrScript, indexReg);
/* 1675:2017 */             break;
/* 1676:     */           case 48: 
/* 1677:2019 */             stack[(++stackTop)] = frame.scriptRegExps[indexReg];
/* 1678:2020 */             break;
/* 1679:     */           case -29: 
/* 1680:2023 */             stackTop++;
/* 1681:2024 */             stack[stackTop] = new int[indexReg];
/* 1682:2025 */             stackTop++;
/* 1683:2026 */             stack[stackTop] = new Object[indexReg];
/* 1684:2027 */             sDbl[stackTop] = 0.0D;
/* 1685:2028 */             break;
/* 1686:     */           case -30: 
/* 1687:2030 */             Object value = stack[stackTop];
/* 1688:2031 */             if (value == DBL_MRK) {
/* 1689:2031 */               value = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1690:     */             }
/* 1691:2032 */             stackTop--;
/* 1692:2033 */             int i = (int)sDbl[stackTop];
/* 1693:2034 */             ((Object[])stack[stackTop])[i] = value;
/* 1694:2035 */             sDbl[stackTop] = (i + 1);
/* 1695:2036 */             break;
/* 1696:     */           case -57: 
/* 1697:2039 */             Object value = stack[stackTop];
/* 1698:2040 */             stackTop--;
/* 1699:2041 */             int i = (int)sDbl[stackTop];
/* 1700:2042 */             ((Object[])stack[stackTop])[i] = value;
/* 1701:2043 */             ((int[])stack[(stackTop - 1)])[i] = -1;
/* 1702:2044 */             sDbl[stackTop] = (i + 1);
/* 1703:2045 */             break;
/* 1704:     */           case -58: 
/* 1705:2048 */             Object value = stack[stackTop];
/* 1706:2049 */             stackTop--;
/* 1707:2050 */             int i = (int)sDbl[stackTop];
/* 1708:2051 */             ((Object[])stack[stackTop])[i] = value;
/* 1709:2052 */             ((int[])stack[(stackTop - 1)])[i] = 1;
/* 1710:2053 */             sDbl[stackTop] = (i + 1);
/* 1711:2054 */             break;
/* 1712:     */           case -31: 
/* 1713:     */           case 65: 
/* 1714:     */           case 66: 
/* 1715:2059 */             Object[] data = (Object[])stack[stackTop];
/* 1716:2060 */             stackTop--;
/* 1717:2061 */             int[] getterSetters = (int[])stack[stackTop];
/* 1718:     */             Object val;
/* 1719:     */             Object val;
/* 1720:2063 */             if (op == 66)
/* 1721:     */             {
/* 1722:2064 */               Object[] ids = (Object[])frame.idata.literalIds[indexReg];
/* 1723:2065 */               val = ScriptRuntime.newObjectLiteral(ids, data, getterSetters, cx, frame.scope);
/* 1724:     */             }
/* 1725:     */             else
/* 1726:     */             {
/* 1727:2068 */               int[] skipIndexces = null;
/* 1728:2069 */               if (op == -31) {
/* 1729:2070 */                 skipIndexces = (int[])frame.idata.literalIds[indexReg];
/* 1730:     */               }
/* 1731:2072 */               val = ScriptRuntime.newArrayLiteral(data, skipIndexces, cx, frame.scope);
/* 1732:     */             }
/* 1733:2075 */             stack[stackTop] = val;
/* 1734:2076 */             break;
/* 1735:     */           case -53: 
/* 1736:2079 */             Object lhs = stack[stackTop];
/* 1737:2080 */             if (lhs == DBL_MRK) {
/* 1738:2080 */               lhs = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1739:     */             }
/* 1740:2081 */             stackTop--;
/* 1741:2082 */             frame.scope = ScriptRuntime.enterDotQuery(lhs, frame.scope);
/* 1742:2083 */             break;
/* 1743:     */           case -54: 
/* 1744:2086 */             boolean valBln = stack_boolean(frame, stackTop);
/* 1745:2087 */             Object x = ScriptRuntime.updateDotQuery(valBln, frame.scope);
/* 1746:2088 */             if (x != null)
/* 1747:     */             {
/* 1748:2089 */               stack[stackTop] = x;
/* 1749:2090 */               frame.scope = ScriptRuntime.leaveDotQuery(frame.scope);
/* 1750:2091 */               frame.pc += 2;
/* 1751:     */             }
/* 1752:     */             else
/* 1753:     */             {
/* 1754:2095 */               stackTop--;
/* 1755:     */             }
/* 1756:2096 */             break;
/* 1757:     */           case 74: 
/* 1758:2099 */             Object value = stack[stackTop];
/* 1759:2100 */             if (value == DBL_MRK) {
/* 1760:2100 */               value = ScriptRuntime.wrapNumber(sDbl[stackTop]);
/* 1761:     */             }
/* 1762:2101 */             stack[stackTop] = ScriptRuntime.setDefaultNamespace(value, cx);
/* 1763:2102 */             break;
/* 1764:     */           case 75: 
/* 1765:2105 */             Object value = stack[stackTop];
/* 1766:2106 */             if (value != DBL_MRK) {
/* 1767:2107 */               stack[stackTop] = ScriptRuntime.escapeAttributeValue(value, cx);
/* 1768:     */             }
/* 1769:     */             break;
/* 1770:     */           case 76: 
/* 1771:2112 */             Object value = stack[stackTop];
/* 1772:2113 */             if (value != DBL_MRK) {
/* 1773:2114 */               stack[stackTop] = ScriptRuntime.escapeTextValue(value, cx);
/* 1774:     */             }
/* 1775:     */             break;
/* 1776:     */           case -64: 
/* 1777:2119 */             if (frame.debuggerFrame != null) {
/* 1778:2120 */               frame.debuggerFrame.onDebuggerStatement(cx);
/* 1779:     */             }
/* 1780:     */             break;
/* 1781:     */           case -26: 
/* 1782:2124 */             frame.pcSourceLineStart = frame.pc;
/* 1783:2125 */             if (frame.debuggerFrame != null)
/* 1784:     */             {
/* 1785:2126 */               int line = getIndex(iCode, frame.pc);
/* 1786:2127 */               frame.debuggerFrame.onLineChange(cx, line);
/* 1787:     */             }
/* 1788:2129 */             frame.pc += 2;
/* 1789:2130 */             break;
/* 1790:     */           case -32: 
/* 1791:2132 */             indexReg = 0;
/* 1792:2133 */             break;
/* 1793:     */           case -33: 
/* 1794:2135 */             indexReg = 1;
/* 1795:2136 */             break;
/* 1796:     */           case -34: 
/* 1797:2138 */             indexReg = 2;
/* 1798:2139 */             break;
/* 1799:     */           case -35: 
/* 1800:2141 */             indexReg = 3;
/* 1801:2142 */             break;
/* 1802:     */           case -36: 
/* 1803:2144 */             indexReg = 4;
/* 1804:2145 */             break;
/* 1805:     */           case -37: 
/* 1806:2147 */             indexReg = 5;
/* 1807:2148 */             break;
/* 1808:     */           case -38: 
/* 1809:2150 */             indexReg = 0xFF & iCode[frame.pc];
/* 1810:2151 */             frame.pc += 1;
/* 1811:2152 */             break;
/* 1812:     */           case -39: 
/* 1813:2154 */             indexReg = getIndex(iCode, frame.pc);
/* 1814:2155 */             frame.pc += 2;
/* 1815:2156 */             break;
/* 1816:     */           case -40: 
/* 1817:2158 */             indexReg = getInt(iCode, frame.pc);
/* 1818:2159 */             frame.pc += 4;
/* 1819:2160 */             break;
/* 1820:     */           case -41: 
/* 1821:2162 */             stringReg = strings[0];
/* 1822:2163 */             break;
/* 1823:     */           case -42: 
/* 1824:2165 */             stringReg = strings[1];
/* 1825:2166 */             break;
/* 1826:     */           case -43: 
/* 1827:2168 */             stringReg = strings[2];
/* 1828:2169 */             break;
/* 1829:     */           case -44: 
/* 1830:2171 */             stringReg = strings[3];
/* 1831:2172 */             break;
/* 1832:     */           case -45: 
/* 1833:2174 */             stringReg = strings[(0xFF & iCode[frame.pc])];
/* 1834:2175 */             frame.pc += 1;
/* 1835:2176 */             break;
/* 1836:     */           case -46: 
/* 1837:2178 */             stringReg = strings[getIndex(iCode, frame.pc)];
/* 1838:2179 */             frame.pc += 2;
/* 1839:2180 */             break;
/* 1840:     */           case -47: 
/* 1841:2182 */             stringReg = strings[getInt(iCode, frame.pc)];
/* 1842:2183 */             frame.pc += 4;
/* 1843:2184 */             break;
/* 1844:     */           case -60: 
/* 1845:     */           case 0: 
/* 1846:     */           case 1: 
/* 1847:     */           case 81: 
/* 1848:     */           case 82: 
/* 1849:     */           case 83: 
/* 1850:     */           case 84: 
/* 1851:     */           case 85: 
/* 1852:     */           case 86: 
/* 1853:     */           case 87: 
/* 1854:     */           case 88: 
/* 1855:     */           case 89: 
/* 1856:     */           case 90: 
/* 1857:     */           case 91: 
/* 1858:     */           case 92: 
/* 1859:     */           case 93: 
/* 1860:     */           case 94: 
/* 1861:     */           case 95: 
/* 1862:     */           case 96: 
/* 1863:     */           case 97: 
/* 1864:     */           case 98: 
/* 1865:     */           case 99: 
/* 1866:     */           case 100: 
/* 1867:     */           case 101: 
/* 1868:     */           case 102: 
/* 1869:     */           case 103: 
/* 1870:     */           case 104: 
/* 1871:     */           case 105: 
/* 1872:     */           case 106: 
/* 1873:     */           case 107: 
/* 1874:     */           case 108: 
/* 1875:     */           case 109: 
/* 1876:     */           case 110: 
/* 1877:     */           case 111: 
/* 1878:     */           case 112: 
/* 1879:     */           case 113: 
/* 1880:     */           case 114: 
/* 1881:     */           case 115: 
/* 1882:     */           case 116: 
/* 1883:     */           case 117: 
/* 1884:     */           case 118: 
/* 1885:     */           case 119: 
/* 1886:     */           case 120: 
/* 1887:     */           case 121: 
/* 1888:     */           case 122: 
/* 1889:     */           case 123: 
/* 1890:     */           case 124: 
/* 1891:     */           case 125: 
/* 1892:     */           case 126: 
/* 1893:     */           case 127: 
/* 1894:     */           case 128: 
/* 1895:     */           case 129: 
/* 1896:     */           case 130: 
/* 1897:     */           case 131: 
/* 1898:     */           case 132: 
/* 1899:     */           case 133: 
/* 1900:     */           case 134: 
/* 1901:     */           case 135: 
/* 1902:     */           case 136: 
/* 1903:     */           case 137: 
/* 1904:     */           case 138: 
/* 1905:     */           case 139: 
/* 1906:     */           case 140: 
/* 1907:     */           case 141: 
/* 1908:     */           case 142: 
/* 1909:     */           case 143: 
/* 1910:     */           case 144: 
/* 1911:     */           case 145: 
/* 1912:     */           case 146: 
/* 1913:     */           case 147: 
/* 1914:     */           case 148: 
/* 1915:     */           case 149: 
/* 1916:     */           case 150: 
/* 1917:     */           case 151: 
/* 1918:     */           case 152: 
/* 1919:     */           case 153: 
/* 1920:     */           case 154: 
/* 1921:     */           case 155: 
/* 1922:     */           default: 
/* 1923:     */             label1552:
/* 1924:2186 */             dumpICode(frame.idata);
/* 1925:2187 */             throw new RuntimeException("Unknown icode : " + op + " @ pc : " + (frame.pc - 1));
/* 1926:2195 */             if (instructionCounting) {
/* 1927:2196 */               addInstructionCount(cx, frame, 2);
/* 1928:     */             }
/* 1929:2198 */             int offset = getShort(iCode, frame.pc);
/* 1930:2199 */             if (offset != 0) {
/* 1931:2201 */               frame.pc += offset - 1;
/* 1932:     */             } else {
/* 1933:2203 */               frame.pc = frame.idata.longJumps.getExistingInt(frame.pc);
/* 1934:     */             }
/* 1935:2206 */             if (instructionCounting) {
/* 1936:2207 */               frame.pcPrevBranch = frame.pc;
/* 1937:     */             }
/* 1938:     */             break;
/* 1939:     */           }
/* 1940:     */         }
/* 1941:2213 */         exitFrame(cx, frame, null);
/* 1942:2214 */         interpreterResult = frame.result;
/* 1943:2215 */         interpreterResultDbl = frame.resultDbl;
/* 1944:2216 */         if (frame.parentFrame == null) {
/* 1945:     */           break;
/* 1946:     */         }
/* 1947:2217 */         frame = frame.parentFrame;
/* 1948:2218 */         if (frame.frozen) {
/* 1949:2219 */           frame = frame.cloneFrozen();
/* 1950:     */         }
/* 1951:2221 */         setCallResult(frame, interpreterResult, interpreterResultDbl);
/* 1952:     */         
/* 1953:2223 */         interpreterResult = null;
/* 1954:     */       }
/* 1955:     */     }
/* 1956:     */     catch (Throwable ex)
/* 1957:     */     {
/* 1958:     */       label7530:
/* 1959:     */       ContinuationJump cjump;
/* 1960:     */       for (;;)
/* 1961:     */       {
/* 1962:2230 */         if (throwable != null)
/* 1963:     */         {
/* 1964:2232 */           ex.printStackTrace(System.err);
/* 1965:2233 */           throw new IllegalStateException();
/* 1966:     */         }
/* 1967:2235 */         throwable = ex;
/* 1968:2241 */         if (throwable == null) {
/* 1969:2241 */           Kit.codeBug();
/* 1970:     */         }
/* 1971:2244 */         int EX_CATCH_STATE = 2;
/* 1972:2245 */         int EX_FINALLY_STATE = 1;
/* 1973:2246 */         int EX_NO_JS_STATE = 0;
/* 1974:     */         
/* 1975:     */ 
/* 1976:2249 */         cjump = null;
/* 1977:     */         int exState;
/* 1978:     */         int exState;
/* 1979:2251 */         if ((generatorState != null) && (generatorState.operation == 2) && (throwable == generatorState.value))
/* 1980:     */         {
/* 1981:2255 */           exState = 1;
/* 1982:     */         }
/* 1983:     */         else
/* 1984:     */         {
/* 1985:     */           int exState;
/* 1986:2256 */           if ((throwable instanceof JavaScriptException))
/* 1987:     */           {
/* 1988:2257 */             exState = 2;
/* 1989:     */           }
/* 1990:     */           else
/* 1991:     */           {
/* 1992:     */             int exState;
/* 1993:2258 */             if ((throwable instanceof EcmaError))
/* 1994:     */             {
/* 1995:2260 */               exState = 2;
/* 1996:     */             }
/* 1997:     */             else
/* 1998:     */             {
/* 1999:     */               int exState;
/* 2000:2261 */               if ((throwable instanceof EvaluatorException))
/* 2001:     */               {
/* 2002:2262 */                 exState = 2;
/* 2003:     */               }
/* 2004:     */               else
/* 2005:     */               {
/* 2006:     */                 int exState;
/* 2007:2263 */                 if ((throwable instanceof RuntimeException))
/* 2008:     */                 {
/* 2009:2264 */                   exState = cx.hasFeature(13) ? 2 : 1;
/* 2010:     */                 }
/* 2011:     */                 else
/* 2012:     */                 {
/* 2013:     */                   int exState;
/* 2014:2267 */                   if ((throwable instanceof Error))
/* 2015:     */                   {
/* 2016:2268 */                     exState = cx.hasFeature(13) ? 2 : 0;
/* 2017:     */                   }
/* 2018:2271 */                   else if ((throwable instanceof ContinuationJump))
/* 2019:     */                   {
/* 2020:2273 */                     int exState = 1;
/* 2021:2274 */                     cjump = (ContinuationJump)throwable;
/* 2022:     */                   }
/* 2023:     */                   else
/* 2024:     */                   {
/* 2025:2276 */                     exState = cx.hasFeature(13) ? 2 : 1;
/* 2026:     */                   }
/* 2027:     */                 }
/* 2028:     */               }
/* 2029:     */             }
/* 2030:     */           }
/* 2031:     */         }
/* 2032:2281 */         if (instructionCounting) {
/* 2033:     */           try
/* 2034:     */           {
/* 2035:2283 */             addInstructionCount(cx, frame, 100);
/* 2036:     */           }
/* 2037:     */           catch (RuntimeException ex)
/* 2038:     */           {
/* 2039:2285 */             throwable = ex;
/* 2040:2286 */             exState = 1;
/* 2041:     */           }
/* 2042:     */           catch (Error ex)
/* 2043:     */           {
/* 2044:2290 */             throwable = ex;
/* 2045:2291 */             cjump = null;
/* 2046:2292 */             exState = 0;
/* 2047:     */           }
/* 2048:     */         }
/* 2049:2295 */         if ((frame.debuggerFrame != null) && ((throwable instanceof RuntimeException)))
/* 2050:     */         {
/* 2051:2299 */           RuntimeException rex = (RuntimeException)throwable;
/* 2052:     */           try
/* 2053:     */           {
/* 2054:2301 */             frame.debuggerFrame.onExceptionThrown(cx, rex);
/* 2055:     */           }
/* 2056:     */           catch (Throwable ex)
/* 2057:     */           {
/* 2058:2305 */             throwable = ex;
/* 2059:2306 */             cjump = null;
/* 2060:2307 */             exState = 0;
/* 2061:     */           }
/* 2062:     */         }
/* 2063:     */         do
/* 2064:     */         {
/* 2065:2312 */           if (exState != 0)
/* 2066:     */           {
/* 2067:2313 */             boolean onlyFinally = exState != 2;
/* 2068:2314 */             indexReg = getExceptionHandler(frame, onlyFinally);
/* 2069:2315 */             if (indexReg >= 0) {
/* 2070:     */               break;
/* 2071:     */             }
/* 2072:     */           }
/* 2073:2325 */           exitFrame(cx, frame, throwable);
/* 2074:     */           
/* 2075:2327 */           frame = frame.parentFrame;
/* 2076:2328 */           if (frame == null) {
/* 2077:     */             break label7860;
/* 2078:     */           }
/* 2079:2329 */         } while ((cjump == null) || (cjump.branchFrame != frame));
/* 2080:2332 */         indexReg = -1;
/* 2081:2333 */         continue;
/* 2082:     */         label7860:
/* 2083:2338 */         if (cjump == null) {
/* 2084:     */           break label7910;
/* 2085:     */         }
/* 2086:2339 */         if (cjump.branchFrame != null) {
/* 2087:2341 */           Kit.codeBug();
/* 2088:     */         }
/* 2089:2343 */         if (cjump.capturedFrame == null) {
/* 2090:     */           break;
/* 2091:     */         }
/* 2092:2345 */         indexReg = -1;
/* 2093:     */       }
/* 2094:2349 */       interpreterResult = cjump.result;
/* 2095:2350 */       interpreterResultDbl = cjump.resultDbl;
/* 2096:2351 */       throwable = null;
/* 2097:     */     }
/* 2098:     */     label7910:
/* 2099:2359 */     if ((cx.previousInterpreterInvocations != null) && (cx.previousInterpreterInvocations.size() != 0))
/* 2100:     */     {
/* 2101:2362 */       cx.lastInterpreterFrame = cx.previousInterpreterInvocations.pop();
/* 2102:     */     }
/* 2103:     */     else
/* 2104:     */     {
/* 2105:2366 */       cx.lastInterpreterFrame = null;
/* 2106:     */       
/* 2107:2368 */       cx.previousInterpreterInvocations = null;
/* 2108:     */     }
/* 2109:2371 */     if (throwable != null)
/* 2110:     */     {
/* 2111:2372 */       if ((throwable instanceof RuntimeException)) {
/* 2112:2373 */         throw ((RuntimeException)throwable);
/* 2113:     */       }
/* 2114:2376 */       throw ((Error)throwable);
/* 2115:     */     }
/* 2116:2380 */     return interpreterResult != DBL_MRK ? interpreterResult : ScriptRuntime.wrapNumber(interpreterResultDbl);
/* 2117:     */   }
/* 2118:     */   
/* 2119:     */   private static CallFrame initFrameForNoSuchMethod(Context cx, CallFrame frame, int indexReg, Object[] stack, double[] sDbl, int stackTop, int op, Scriptable funThisObj, Scriptable calleeScope, ScriptRuntime.NoSuchMethodShim noSuchMethodShim, InterpretedFunction ifun)
/* 2120:     */   {
/* 2121:2394 */     Object[] argsArray = null;
/* 2122:     */     
/* 2123:     */ 
/* 2124:2397 */     int shift = stackTop + 2;
/* 2125:2398 */     Object[] elements = new Object[indexReg];
/* 2126:2399 */     for (int i = 0; i < indexReg; shift++)
/* 2127:     */     {
/* 2128:2400 */       Object val = stack[shift];
/* 2129:2401 */       if (val == UniqueTag.DOUBLE_MARK) {
/* 2130:2402 */         val = ScriptRuntime.wrapNumber(sDbl[shift]);
/* 2131:     */       }
/* 2132:2404 */       elements[i] = val;i++;
/* 2133:     */     }
/* 2134:2406 */     argsArray = new Object[2];
/* 2135:2407 */     argsArray[0] = noSuchMethodShim.methodName;
/* 2136:2408 */     argsArray[1] = cx.newArray(calleeScope, elements);
/* 2137:     */     
/* 2138:     */ 
/* 2139:2411 */     CallFrame callParentFrame = frame;
/* 2140:2412 */     CallFrame calleeFrame = new CallFrame(null);
/* 2141:2413 */     if (op == -55)
/* 2142:     */     {
/* 2143:2414 */       callParentFrame = frame.parentFrame;
/* 2144:2415 */       exitFrame(cx, frame, null);
/* 2145:     */     }
/* 2146:2419 */     initFrame(cx, calleeScope, funThisObj, argsArray, null, 0, 2, ifun, callParentFrame, calleeFrame);
/* 2147:2421 */     if (op != -55)
/* 2148:     */     {
/* 2149:2422 */       frame.savedStackTop = stackTop;
/* 2150:2423 */       frame.savedCallOp = op;
/* 2151:     */     }
/* 2152:2425 */     return calleeFrame;
/* 2153:     */   }
/* 2154:     */   
/* 2155:     */   private static boolean shallowEquals(Object[] stack, double[] sDbl, int stackTop)
/* 2156:     */   {
/* 2157:2431 */     Object rhs = stack[(stackTop + 1)];
/* 2158:2432 */     Object lhs = stack[stackTop];
/* 2159:2433 */     Object DBL_MRK = UniqueTag.DOUBLE_MARK;
/* 2160:2435 */     if (rhs == DBL_MRK)
/* 2161:     */     {
/* 2162:2436 */       double rdbl = sDbl[(stackTop + 1)];
/* 2163:     */       double ldbl;
/* 2164:2437 */       if (lhs == DBL_MRK)
/* 2165:     */       {
/* 2166:2438 */         ldbl = sDbl[stackTop];
/* 2167:     */       }
/* 2168:     */       else
/* 2169:     */       {
/* 2170:     */         double ldbl;
/* 2171:2439 */         if ((lhs instanceof Number)) {
/* 2172:2440 */           ldbl = ((Number)lhs).doubleValue();
/* 2173:     */         } else {
/* 2174:2442 */           return false;
/* 2175:     */         }
/* 2176:     */       }
/* 2177:     */     }
/* 2178:2444 */     else if (lhs == DBL_MRK)
/* 2179:     */     {
/* 2180:2445 */       double ldbl = sDbl[stackTop];
/* 2181:     */       double rdbl;
/* 2182:2446 */       if (rhs == DBL_MRK)
/* 2183:     */       {
/* 2184:2447 */         rdbl = sDbl[(stackTop + 1)];
/* 2185:     */       }
/* 2186:     */       else
/* 2187:     */       {
/* 2188:     */         double rdbl;
/* 2189:2448 */         if ((rhs instanceof Number)) {
/* 2190:2449 */           rdbl = ((Number)rhs).doubleValue();
/* 2191:     */         } else {
/* 2192:2451 */           return false;
/* 2193:     */         }
/* 2194:     */       }
/* 2195:     */     }
/* 2196:     */     else
/* 2197:     */     {
/* 2198:2454 */       return ScriptRuntime.shallowEq(lhs, rhs);
/* 2199:     */     }
/* 2200:     */     double ldbl;
/* 2201:     */     double rdbl;
/* 2202:2456 */     return ldbl == rdbl;
/* 2203:     */   }
/* 2204:     */   
/* 2205:     */   private static CallFrame processThrowable(Context cx, Object throwable, CallFrame frame, int indexReg, boolean instructionCounting)
/* 2206:     */   {
/* 2207:2466 */     if (indexReg >= 0)
/* 2208:     */     {
/* 2209:2470 */       if (frame.frozen) {
/* 2210:2472 */         frame = frame.cloneFrozen();
/* 2211:     */       }
/* 2212:2475 */       int[] table = frame.idata.itsExceptionTable;
/* 2213:     */       
/* 2214:2477 */       frame.pc = table[(indexReg + 2)];
/* 2215:2478 */       if (instructionCounting) {
/* 2216:2479 */         frame.pcPrevBranch = frame.pc;
/* 2217:     */       }
/* 2218:2482 */       frame.savedStackTop = frame.emptyStackTop;
/* 2219:2483 */       int scopeLocal = frame.localShift + table[(indexReg + 5)];
/* 2220:     */       
/* 2221:     */ 
/* 2222:2486 */       int exLocal = frame.localShift + table[(indexReg + 4)];
/* 2223:     */       
/* 2224:     */ 
/* 2225:2489 */       frame.scope = ((Scriptable)frame.stack[scopeLocal]);
/* 2226:2490 */       frame.stack[exLocal] = throwable;
/* 2227:     */       
/* 2228:2492 */       throwable = null;
/* 2229:     */     }
/* 2230:     */     else
/* 2231:     */     {
/* 2232:2495 */       ContinuationJump cjump = (ContinuationJump)throwable;
/* 2233:     */       
/* 2234:     */ 
/* 2235:2498 */       throwable = null;
/* 2236:2500 */       if (cjump.branchFrame != frame) {
/* 2237:2500 */         Kit.codeBug();
/* 2238:     */       }
/* 2239:2505 */       if (cjump.capturedFrame == null) {
/* 2240:2505 */         Kit.codeBug();
/* 2241:     */       }
/* 2242:2509 */       int rewindCount = cjump.capturedFrame.frameIndex + 1;
/* 2243:2510 */       if (cjump.branchFrame != null) {
/* 2244:2511 */         rewindCount -= cjump.branchFrame.frameIndex;
/* 2245:     */       }
/* 2246:2514 */       int enterCount = 0;
/* 2247:2515 */       CallFrame[] enterFrames = null;
/* 2248:     */       
/* 2249:2517 */       CallFrame x = cjump.capturedFrame;
/* 2250:2518 */       for (int i = 0; i != rewindCount; i++)
/* 2251:     */       {
/* 2252:2519 */         if (!x.frozen) {
/* 2253:2519 */           Kit.codeBug();
/* 2254:     */         }
/* 2255:2520 */         if (isFrameEnterExitRequired(x))
/* 2256:     */         {
/* 2257:2521 */           if (enterFrames == null) {
/* 2258:2525 */             enterFrames = new CallFrame[rewindCount - i];
/* 2259:     */           }
/* 2260:2528 */           enterFrames[enterCount] = x;
/* 2261:2529 */           enterCount++;
/* 2262:     */         }
/* 2263:2531 */         x = x.parentFrame;
/* 2264:     */       }
/* 2265:2534 */       while (enterCount != 0)
/* 2266:     */       {
/* 2267:2538 */         enterCount--;
/* 2268:2539 */         x = enterFrames[enterCount];
/* 2269:2540 */         enterFrame(cx, x, ScriptRuntime.emptyArgs, true);
/* 2270:     */       }
/* 2271:2547 */       frame = cjump.capturedFrame.cloneFrozen();
/* 2272:2548 */       setCallResult(frame, cjump.result, cjump.resultDbl);
/* 2273:     */     }
/* 2274:2551 */     frame.throwable = throwable;
/* 2275:2552 */     return frame;
/* 2276:     */   }
/* 2277:     */   
/* 2278:     */   private static Object freezeGenerator(Context cx, CallFrame frame, int stackTop, GeneratorState generatorState)
/* 2279:     */   {
/* 2280:2559 */     if (generatorState.operation == 2) {
/* 2281:2561 */       throw ScriptRuntime.typeError0("msg.yield.closing");
/* 2282:     */     }
/* 2283:2564 */     frame.frozen = true;
/* 2284:2565 */     frame.result = frame.stack[stackTop];
/* 2285:2566 */     frame.resultDbl = frame.sDbl[stackTop];
/* 2286:2567 */     frame.savedStackTop = stackTop;
/* 2287:2568 */     frame.pc -= 1;
/* 2288:2569 */     ScriptRuntime.exitActivationFunction(cx);
/* 2289:2570 */     return frame.result != UniqueTag.DOUBLE_MARK ? frame.result : ScriptRuntime.wrapNumber(frame.resultDbl);
/* 2290:     */   }
/* 2291:     */   
/* 2292:     */   private static Object thawGenerator(CallFrame frame, int stackTop, GeneratorState generatorState, int op)
/* 2293:     */   {
/* 2294:2579 */     frame.frozen = false;
/* 2295:2580 */     int sourceLine = getIndex(frame.idata.itsICode, frame.pc);
/* 2296:2581 */     frame.pc += 2;
/* 2297:2582 */     if (generatorState.operation == 1) {
/* 2298:2585 */       return new JavaScriptException(generatorState.value, frame.idata.itsSourceFile, sourceLine);
/* 2299:     */     }
/* 2300:2589 */     if (generatorState.operation == 2) {
/* 2301:2590 */       return generatorState.value;
/* 2302:     */     }
/* 2303:2592 */     if (generatorState.operation != 0) {
/* 2304:2593 */       throw Kit.codeBug();
/* 2305:     */     }
/* 2306:2594 */     if (op == 72) {
/* 2307:2595 */       frame.stack[stackTop] = generatorState.value;
/* 2308:     */     }
/* 2309:2596 */     return Scriptable.NOT_FOUND;
/* 2310:     */   }
/* 2311:     */   
/* 2312:     */   private static CallFrame initFrameForApplyOrCall(Context cx, CallFrame frame, int indexReg, Object[] stack, double[] sDbl, int stackTop, int op, Scriptable calleeScope, IdFunctionObject ifun, InterpretedFunction iApplyCallable)
/* 2313:     */   {
/* 2314:     */     Scriptable applyThis;
/* 2315:     */     Scriptable applyThis;
/* 2316:2605 */     if (indexReg != 0)
/* 2317:     */     {
/* 2318:2606 */       Object obj = stack[(stackTop + 2)];
/* 2319:2607 */       if (obj == UniqueTag.DOUBLE_MARK) {
/* 2320:2608 */         obj = ScriptRuntime.wrapNumber(sDbl[(stackTop + 2)]);
/* 2321:     */       }
/* 2322:2609 */       applyThis = ScriptRuntime.toObjectOrNull(cx, obj);
/* 2323:     */     }
/* 2324:     */     else
/* 2325:     */     {
/* 2326:2612 */       applyThis = null;
/* 2327:     */     }
/* 2328:2614 */     if (applyThis == null) {
/* 2329:2616 */       applyThis = ScriptRuntime.getTopCallScope(cx);
/* 2330:     */     }
/* 2331:2618 */     if (op == -55)
/* 2332:     */     {
/* 2333:2619 */       exitFrame(cx, frame, null);
/* 2334:2620 */       frame = frame.parentFrame;
/* 2335:     */     }
/* 2336:     */     else
/* 2337:     */     {
/* 2338:2623 */       frame.savedStackTop = stackTop;
/* 2339:2624 */       frame.savedCallOp = op;
/* 2340:     */     }
/* 2341:2626 */     CallFrame calleeFrame = new CallFrame(null);
/* 2342:2627 */     if (BaseFunction.isApply(ifun))
/* 2343:     */     {
/* 2344:2628 */       Object[] callArgs = indexReg < 2 ? ScriptRuntime.emptyArgs : ScriptRuntime.getApplyArguments(cx, stack[(stackTop + 3)]);
/* 2345:     */       
/* 2346:2630 */       initFrame(cx, calleeScope, applyThis, callArgs, null, 0, callArgs.length, iApplyCallable, frame, calleeFrame);
/* 2347:     */     }
/* 2348:     */     else
/* 2349:     */     {
/* 2350:2635 */       for (int i = 1; i < indexReg; i++)
/* 2351:     */       {
/* 2352:2636 */         stack[(stackTop + 1 + i)] = stack[(stackTop + 2 + i)];
/* 2353:2637 */         sDbl[(stackTop + 1 + i)] = sDbl[(stackTop + 2 + i)];
/* 2354:     */       }
/* 2355:2639 */       int argCount = indexReg < 2 ? 0 : indexReg - 1;
/* 2356:2640 */       initFrame(cx, calleeScope, applyThis, stack, sDbl, stackTop + 2, argCount, iApplyCallable, frame, calleeFrame);
/* 2357:     */     }
/* 2358:2644 */     frame = calleeFrame;
/* 2359:2645 */     return frame;
/* 2360:     */   }
/* 2361:     */   
/* 2362:     */   private static void initFrame(Context cx, Scriptable callerScope, Scriptable thisObj, Object[] args, double[] argsDbl, int argShift, int argCount, InterpretedFunction fnOrScript, CallFrame parentFrame, CallFrame frame)
/* 2363:     */   {
/* 2364:2655 */     InterpreterData idata = fnOrScript.idata;
/* 2365:     */     
/* 2366:2657 */     boolean useActivation = idata.itsNeedsActivation;
/* 2367:2658 */     DebugFrame debuggerFrame = null;
/* 2368:2659 */     if (cx.debugger != null)
/* 2369:     */     {
/* 2370:2660 */       debuggerFrame = cx.debugger.getFrame(cx, idata);
/* 2371:2661 */       if (debuggerFrame != null) {
/* 2372:2662 */         useActivation = true;
/* 2373:     */       }
/* 2374:     */     }
/* 2375:2666 */     if (useActivation)
/* 2376:     */     {
/* 2377:2669 */       if (argsDbl != null) {
/* 2378:2670 */         args = getArgsArray(args, argsDbl, argShift, argCount);
/* 2379:     */       }
/* 2380:2672 */       argShift = 0;
/* 2381:2673 */       argsDbl = null;
/* 2382:     */     }
/* 2383:     */     Scriptable scope;
/* 2384:2677 */     if (idata.itsFunctionType != 0)
/* 2385:     */     {
/* 2386:     */       Scriptable scope;
/* 2387:     */       Scriptable scope;
/* 2388:2678 */       if (!idata.useDynamicScope) {
/* 2389:2679 */         scope = fnOrScript.getParentScope();
/* 2390:     */       } else {
/* 2391:2681 */         scope = callerScope;
/* 2392:     */       }
/* 2393:2684 */       if (useActivation) {
/* 2394:2685 */         scope = ScriptRuntime.createFunctionActivation(fnOrScript, scope, args);
/* 2395:     */       }
/* 2396:     */     }
/* 2397:     */     else
/* 2398:     */     {
/* 2399:2689 */       scope = callerScope;
/* 2400:2690 */       ScriptRuntime.initScript(fnOrScript, thisObj, cx, scope, fnOrScript.idata.evalScriptFlag);
/* 2401:     */     }
/* 2402:2694 */     if (idata.itsNestedFunctions != null)
/* 2403:     */     {
/* 2404:2695 */       if ((idata.itsFunctionType != 0) && (!idata.itsNeedsActivation)) {
/* 2405:2696 */         Kit.codeBug();
/* 2406:     */       }
/* 2407:2697 */       for (int i = 0; i < idata.itsNestedFunctions.length; i++)
/* 2408:     */       {
/* 2409:2698 */         InterpreterData fdata = idata.itsNestedFunctions[i];
/* 2410:2699 */         if (fdata.itsFunctionType == 1) {
/* 2411:2700 */           initFunction(cx, scope, fnOrScript, i);
/* 2412:     */         }
/* 2413:     */       }
/* 2414:     */     }
/* 2415:2705 */     Scriptable[] scriptRegExps = null;
/* 2416:2706 */     if (idata.itsRegExpLiterals != null) {
/* 2417:2711 */       if (idata.itsFunctionType != 0) {
/* 2418:2712 */         scriptRegExps = fnOrScript.functionRegExps;
/* 2419:     */       } else {
/* 2420:2714 */         scriptRegExps = fnOrScript.createRegExpWraps(cx, scope);
/* 2421:     */       }
/* 2422:     */     }
/* 2423:2720 */     int emptyStackTop = idata.itsMaxVars + idata.itsMaxLocals - 1;
/* 2424:2721 */     int maxFrameArray = idata.itsMaxFrameArray;
/* 2425:2722 */     if (maxFrameArray != emptyStackTop + idata.itsMaxStack + 1) {
/* 2426:2723 */       Kit.codeBug();
/* 2427:     */     }
/* 2428:     */     double[] sDbl;
/* 2429:     */     boolean stackReuse;
/* 2430:     */     Object[] stack;
/* 2431:     */     int[] stackAttributes;
/* 2432:     */     double[] sDbl;
/* 2433:2729 */     if ((frame.stack != null) && (maxFrameArray <= frame.stack.length))
/* 2434:     */     {
/* 2435:2731 */       boolean stackReuse = true;
/* 2436:2732 */       Object[] stack = frame.stack;
/* 2437:2733 */       int[] stackAttributes = frame.stackAttributes;
/* 2438:2734 */       sDbl = frame.sDbl;
/* 2439:     */     }
/* 2440:     */     else
/* 2441:     */     {
/* 2442:2736 */       stackReuse = false;
/* 2443:2737 */       stack = new Object[maxFrameArray];
/* 2444:2738 */       stackAttributes = new int[maxFrameArray];
/* 2445:2739 */       sDbl = new double[maxFrameArray];
/* 2446:     */     }
/* 2447:2742 */     int varCount = idata.getParamAndVarCount();
/* 2448:2743 */     for (int i = 0; i < varCount; i++) {
/* 2449:2744 */       if (idata.getParamOrVarConst(i)) {
/* 2450:2745 */         stackAttributes[i] = 13;
/* 2451:     */       }
/* 2452:     */     }
/* 2453:2747 */     int definedArgs = idata.argCount;
/* 2454:2748 */     if (definedArgs > argCount) {
/* 2455:2748 */       definedArgs = argCount;
/* 2456:     */     }
/* 2457:2752 */     frame.parentFrame = parentFrame;
/* 2458:2753 */     frame.frameIndex = (parentFrame == null ? 0 : parentFrame.frameIndex + 1);
/* 2459:2755 */     if (frame.frameIndex > cx.getMaximumInterpreterStackDepth()) {
/* 2460:2757 */       throw Context.reportRuntimeError("Exceeded maximum stack depth");
/* 2461:     */     }
/* 2462:2759 */     frame.frozen = false;
/* 2463:     */     
/* 2464:2761 */     frame.fnOrScript = fnOrScript;
/* 2465:2762 */     frame.idata = idata;
/* 2466:     */     
/* 2467:2764 */     frame.stack = stack;
/* 2468:2765 */     frame.stackAttributes = stackAttributes;
/* 2469:2766 */     frame.sDbl = sDbl;
/* 2470:2767 */     frame.varSource = frame;
/* 2471:2768 */     frame.localShift = idata.itsMaxVars;
/* 2472:2769 */     frame.emptyStackTop = emptyStackTop;
/* 2473:     */     
/* 2474:2771 */     frame.debuggerFrame = debuggerFrame;
/* 2475:2772 */     frame.useActivation = useActivation;
/* 2476:     */     
/* 2477:2774 */     frame.thisObj = thisObj;
/* 2478:2775 */     frame.scriptRegExps = scriptRegExps;
/* 2479:     */     
/* 2480:     */ 
/* 2481:     */ 
/* 2482:2779 */     frame.result = Undefined.instance;
/* 2483:2780 */     frame.pc = 0;
/* 2484:2781 */     frame.pcPrevBranch = 0;
/* 2485:2782 */     frame.pcSourceLineStart = idata.firstLinePC;
/* 2486:2783 */     frame.scope = scope;
/* 2487:     */     
/* 2488:2785 */     frame.savedStackTop = emptyStackTop;
/* 2489:2786 */     frame.savedCallOp = 0;
/* 2490:     */     
/* 2491:2788 */     System.arraycopy(args, argShift, stack, 0, definedArgs);
/* 2492:2789 */     if (argsDbl != null) {
/* 2493:2790 */       System.arraycopy(argsDbl, argShift, sDbl, 0, definedArgs);
/* 2494:     */     }
/* 2495:2792 */     for (int i = definedArgs; i != idata.itsMaxVars; i++) {
/* 2496:2793 */       stack[i] = Undefined.instance;
/* 2497:     */     }
/* 2498:2795 */     if (stackReuse) {
/* 2499:2798 */       for (int i = emptyStackTop + 1; i != stack.length; i++) {
/* 2500:2799 */         stack[i] = null;
/* 2501:     */       }
/* 2502:     */     }
/* 2503:2803 */     enterFrame(cx, frame, args, false);
/* 2504:     */   }
/* 2505:     */   
/* 2506:     */   private static boolean isFrameEnterExitRequired(CallFrame frame)
/* 2507:     */   {
/* 2508:2808 */     return (frame.debuggerFrame != null) || (frame.idata.itsNeedsActivation);
/* 2509:     */   }
/* 2510:     */   
/* 2511:     */   private static void enterFrame(Context cx, CallFrame frame, Object[] args, boolean continuationRestart)
/* 2512:     */   {
/* 2513:2814 */     if ((frame.parentFrame != null) && (!frame.parentFrame.fnOrScript.isScript())) {
/* 2514:2815 */       frame.fnOrScript.defaultPut("caller", frame.parentFrame.fnOrScript);
/* 2515:     */     }
/* 2516:2817 */     boolean usesActivation = frame.idata.itsNeedsActivation;
/* 2517:2818 */     boolean isDebugged = frame.debuggerFrame != null;
/* 2518:2819 */     if ((usesActivation) || (isDebugged))
/* 2519:     */     {
/* 2520:2820 */       Scriptable scope = frame.scope;
/* 2521:2821 */       if (scope == null) {
/* 2522:2822 */         Kit.codeBug();
/* 2523:2823 */       } else if (continuationRestart) {
/* 2524:2833 */         while ((scope instanceof NativeWith))
/* 2525:     */         {
/* 2526:2834 */           scope = scope.getParentScope();
/* 2527:2835 */           if ((scope == null) || ((frame.parentFrame != null) && (frame.parentFrame.scope == scope))) {
/* 2528:2841 */             Kit.codeBug();
/* 2529:     */           }
/* 2530:     */         }
/* 2531:     */       }
/* 2532:2850 */       if (isDebugged) {
/* 2533:2851 */         frame.debuggerFrame.onEnter(cx, scope, frame.thisObj, args);
/* 2534:     */       }
/* 2535:2856 */       if (usesActivation) {
/* 2536:2857 */         ScriptRuntime.enterActivationFunction(cx, scope);
/* 2537:     */       }
/* 2538:     */     }
/* 2539:     */   }
/* 2540:     */   
/* 2541:     */   private static void exitFrame(Context cx, CallFrame frame, Object throwable)
/* 2542:     */   {
/* 2543:2865 */     frame.fnOrScript.delete("caller");
/* 2544:2867 */     if (frame.idata.itsNeedsActivation) {
/* 2545:2868 */       ScriptRuntime.exitActivationFunction(cx);
/* 2546:     */     }
/* 2547:2871 */     if (frame.debuggerFrame != null) {
/* 2548:     */       try
/* 2549:     */       {
/* 2550:2873 */         if ((throwable instanceof Throwable))
/* 2551:     */         {
/* 2552:2874 */           frame.debuggerFrame.onExit(cx, true, throwable);
/* 2553:     */         }
/* 2554:     */         else
/* 2555:     */         {
/* 2556:2877 */           ContinuationJump cjump = (ContinuationJump)throwable;
/* 2557:     */           Object result;
/* 2558:     */           Object result;
/* 2559:2878 */           if (cjump == null) {
/* 2560:2879 */             result = frame.result;
/* 2561:     */           } else {
/* 2562:2881 */             result = cjump.result;
/* 2563:     */           }
/* 2564:2883 */           if (result == UniqueTag.DOUBLE_MARK)
/* 2565:     */           {
/* 2566:     */             double resultDbl;
/* 2567:     */             double resultDbl;
/* 2568:2885 */             if (cjump == null) {
/* 2569:2886 */               resultDbl = frame.resultDbl;
/* 2570:     */             } else {
/* 2571:2888 */               resultDbl = cjump.resultDbl;
/* 2572:     */             }
/* 2573:2890 */             result = ScriptRuntime.wrapNumber(resultDbl);
/* 2574:     */           }
/* 2575:2892 */           frame.debuggerFrame.onExit(cx, false, result);
/* 2576:     */         }
/* 2577:     */       }
/* 2578:     */       catch (Throwable ex)
/* 2579:     */       {
/* 2580:2895 */         System.err.println("RHINO USAGE WARNING: onExit terminated with exception");
/* 2581:     */         
/* 2582:2897 */         ex.printStackTrace(System.err);
/* 2583:     */       }
/* 2584:     */     }
/* 2585:     */   }
/* 2586:     */   
/* 2587:     */   private static void setCallResult(CallFrame frame, Object callResult, double callResultDbl)
/* 2588:     */   {
/* 2589:2906 */     if (frame.savedCallOp == 38)
/* 2590:     */     {
/* 2591:2907 */       frame.stack[frame.savedStackTop] = callResult;
/* 2592:2908 */       frame.sDbl[frame.savedStackTop] = callResultDbl;
/* 2593:     */     }
/* 2594:2909 */     else if (frame.savedCallOp == 30)
/* 2595:     */     {
/* 2596:2913 */       if ((callResult instanceof Scriptable)) {
/* 2597:2914 */         frame.stack[frame.savedStackTop] = callResult;
/* 2598:     */       }
/* 2599:     */     }
/* 2600:     */     else
/* 2601:     */     {
/* 2602:2917 */       Kit.codeBug();
/* 2603:     */     }
/* 2604:2919 */     frame.savedCallOp = 0;
/* 2605:     */   }
/* 2606:     */   
/* 2607:     */   public static NativeContinuation captureContinuation(Context cx)
/* 2608:     */   {
/* 2609:2923 */     if ((cx.lastInterpreterFrame == null) || (!(cx.lastInterpreterFrame instanceof CallFrame))) {
/* 2610:2926 */       throw new IllegalStateException("Interpreter frames not found");
/* 2611:     */     }
/* 2612:2928 */     return captureContinuation(cx, (CallFrame)cx.lastInterpreterFrame, true);
/* 2613:     */   }
/* 2614:     */   
/* 2615:     */   private static NativeContinuation captureContinuation(Context cx, CallFrame frame, boolean requireContinuationsTopFrame)
/* 2616:     */   {
/* 2617:2934 */     NativeContinuation c = new NativeContinuation();
/* 2618:2935 */     ScriptRuntime.setObjectProtoAndParent(c, ScriptRuntime.getTopCallScope(cx));
/* 2619:     */     
/* 2620:     */ 
/* 2621:     */ 
/* 2622:2939 */     CallFrame x = frame;
/* 2623:2940 */     CallFrame outermost = frame;
/* 2624:2941 */     while ((x != null) && (!x.frozen))
/* 2625:     */     {
/* 2626:2942 */       x.frozen = true;
/* 2627:2944 */       for (int i = x.savedStackTop + 1; i != x.stack.length; i++)
/* 2628:     */       {
/* 2629:2946 */         x.stack[i] = null;
/* 2630:2947 */         x.stackAttributes[i] = 0;
/* 2631:     */       }
/* 2632:2949 */       if (x.savedCallOp == 38) {
/* 2633:2951 */         x.stack[x.savedStackTop] = null;
/* 2634:2953 */       } else if (x.savedCallOp != 30) {
/* 2635:2953 */         Kit.codeBug();
/* 2636:     */       }
/* 2637:2958 */       outermost = x;
/* 2638:2959 */       x = x.parentFrame;
/* 2639:     */     }
/* 2640:2962 */     if (requireContinuationsTopFrame)
/* 2641:     */     {
/* 2642:2963 */       while (outermost.parentFrame != null) {
/* 2643:2964 */         outermost = outermost.parentFrame;
/* 2644:     */       }
/* 2645:2966 */       if (!outermost.isContinuationsTopFrame) {
/* 2646:2967 */         throw new IllegalStateException("Cannot capture continuation from JavaScript code not called directly by executeScriptWithContinuations or callFunctionWithContinuations");
/* 2647:     */       }
/* 2648:     */     }
/* 2649:2974 */     c.initImplementation(frame);
/* 2650:2975 */     return c;
/* 2651:     */   }
/* 2652:     */   
/* 2653:     */   private static int stack_int32(CallFrame frame, int i)
/* 2654:     */   {
/* 2655:2980 */     Object x = frame.stack[i];
/* 2656:     */     double value;
/* 2657:     */     double value;
/* 2658:2982 */     if (x == UniqueTag.DOUBLE_MARK) {
/* 2659:2983 */       value = frame.sDbl[i];
/* 2660:     */     } else {
/* 2661:2985 */       value = ScriptRuntime.toNumber(x);
/* 2662:     */     }
/* 2663:2987 */     return ScriptRuntime.toInt32(value);
/* 2664:     */   }
/* 2665:     */   
/* 2666:     */   private static double stack_double(CallFrame frame, int i)
/* 2667:     */   {
/* 2668:2992 */     Object x = frame.stack[i];
/* 2669:2993 */     if (x != UniqueTag.DOUBLE_MARK) {
/* 2670:2994 */       return ScriptRuntime.toNumber(x);
/* 2671:     */     }
/* 2672:2996 */     return frame.sDbl[i];
/* 2673:     */   }
/* 2674:     */   
/* 2675:     */   private static boolean stack_boolean(CallFrame frame, int i)
/* 2676:     */   {
/* 2677:3002 */     Object x = frame.stack[i];
/* 2678:3003 */     if (x == Boolean.TRUE) {
/* 2679:3004 */       return true;
/* 2680:     */     }
/* 2681:3005 */     if (x == Boolean.FALSE) {
/* 2682:3006 */       return false;
/* 2683:     */     }
/* 2684:3007 */     if (x == UniqueTag.DOUBLE_MARK)
/* 2685:     */     {
/* 2686:3008 */       double d = frame.sDbl[i];
/* 2687:3009 */       return (d == d) && (d != 0.0D);
/* 2688:     */     }
/* 2689:3010 */     if ((x == null) || (x == Undefined.instance)) {
/* 2690:3011 */       return false;
/* 2691:     */     }
/* 2692:3012 */     if ((x instanceof Number))
/* 2693:     */     {
/* 2694:3013 */       double d = ((Number)x).doubleValue();
/* 2695:3014 */       return (d == d) && (d != 0.0D);
/* 2696:     */     }
/* 2697:3015 */     if ((x instanceof Boolean)) {
/* 2698:3016 */       return ((Boolean)x).booleanValue();
/* 2699:     */     }
/* 2700:3018 */     return ScriptRuntime.toBoolean(x);
/* 2701:     */   }
/* 2702:     */   
/* 2703:     */   private static void do_add(Object[] stack, double[] sDbl, int stackTop, Context cx)
/* 2704:     */   {
/* 2705:3025 */     Object rhs = stack[(stackTop + 1)];
/* 2706:3026 */     Object lhs = stack[stackTop];
/* 2707:     */     boolean leftRightOrder;
/* 2708:3029 */     if (rhs == UniqueTag.DOUBLE_MARK)
/* 2709:     */     {
/* 2710:3030 */       double d = sDbl[(stackTop + 1)];
/* 2711:3031 */       if (lhs == UniqueTag.DOUBLE_MARK)
/* 2712:     */       {
/* 2713:3032 */         sDbl[stackTop] += d;
/* 2714:3033 */         return;
/* 2715:     */       }
/* 2716:3035 */       leftRightOrder = true;
/* 2717:     */     }
/* 2718:     */     else
/* 2719:     */     {
/* 2720:     */       boolean leftRightOrder;
/* 2721:3037 */       if (lhs == UniqueTag.DOUBLE_MARK)
/* 2722:     */       {
/* 2723:3038 */         double d = sDbl[stackTop];
/* 2724:3039 */         lhs = rhs;
/* 2725:3040 */         leftRightOrder = false;
/* 2726:     */       }
/* 2727:     */       else
/* 2728:     */       {
/* 2729:3043 */         if (((lhs instanceof Scriptable)) || ((rhs instanceof Scriptable)))
/* 2730:     */         {
/* 2731:3044 */           stack[stackTop] = ScriptRuntime.add(lhs, rhs, cx);
/* 2732:     */         }
/* 2733:3045 */         else if ((lhs instanceof String))
/* 2734:     */         {
/* 2735:3046 */           String lstr = (String)lhs;
/* 2736:3047 */           String rstr = ScriptRuntime.toString(rhs);
/* 2737:3048 */           stack[stackTop] = lstr.concat(rstr);
/* 2738:     */         }
/* 2739:3049 */         else if ((rhs instanceof String))
/* 2740:     */         {
/* 2741:3050 */           String lstr = ScriptRuntime.toString(lhs);
/* 2742:3051 */           String rstr = (String)rhs;
/* 2743:3052 */           stack[stackTop] = lstr.concat(rstr);
/* 2744:     */         }
/* 2745:     */         else
/* 2746:     */         {
/* 2747:3054 */           double lDbl = (lhs instanceof Number) ? ((Number)lhs).doubleValue() : ScriptRuntime.toNumber(lhs);
/* 2748:     */           
/* 2749:3056 */           double rDbl = (rhs instanceof Number) ? ((Number)rhs).doubleValue() : ScriptRuntime.toNumber(rhs);
/* 2750:     */           
/* 2751:3058 */           stack[stackTop] = UniqueTag.DOUBLE_MARK;
/* 2752:3059 */           sDbl[stackTop] = (lDbl + rDbl);
/* 2753:     */         }
/* 2754:     */         return;
/* 2755:     */       }
/* 2756:     */     }
/* 2757:     */     boolean leftRightOrder;
/* 2758:     */     double d;
/* 2759:3065 */     if ((lhs instanceof Scriptable))
/* 2760:     */     {
/* 2761:3066 */       rhs = ScriptRuntime.wrapNumber(d);
/* 2762:3067 */       if (!leftRightOrder)
/* 2763:     */       {
/* 2764:3068 */         Object tmp = lhs;
/* 2765:3069 */         lhs = rhs;
/* 2766:3070 */         rhs = tmp;
/* 2767:     */       }
/* 2768:3072 */       stack[stackTop] = ScriptRuntime.add(lhs, rhs, cx);
/* 2769:     */     }
/* 2770:3073 */     else if ((lhs instanceof String))
/* 2771:     */     {
/* 2772:3074 */       String lstr = (String)lhs;
/* 2773:3075 */       String rstr = ScriptRuntime.toString(d);
/* 2774:3076 */       if (leftRightOrder) {
/* 2775:3077 */         stack[stackTop] = lstr.concat(rstr);
/* 2776:     */       } else {
/* 2777:3079 */         stack[stackTop] = rstr.concat(lstr);
/* 2778:     */       }
/* 2779:     */     }
/* 2780:     */     else
/* 2781:     */     {
/* 2782:3082 */       double lDbl = (lhs instanceof Number) ? ((Number)lhs).doubleValue() : ScriptRuntime.toNumber(lhs);
/* 2783:     */       
/* 2784:3084 */       stack[stackTop] = UniqueTag.DOUBLE_MARK;
/* 2785:3085 */       sDbl[stackTop] = (lDbl + d);
/* 2786:     */     }
/* 2787:     */   }
/* 2788:     */   
/* 2789:     */   private static Object[] getArgsArray(Object[] stack, double[] sDbl, int shift, int count)
/* 2790:     */   {
/* 2791:3092 */     if (count == 0) {
/* 2792:3093 */       return ScriptRuntime.emptyArgs;
/* 2793:     */     }
/* 2794:3095 */     Object[] args = new Object[count];
/* 2795:3096 */     for (int i = 0; i != count; shift++)
/* 2796:     */     {
/* 2797:3097 */       Object val = stack[shift];
/* 2798:3098 */       if (val == UniqueTag.DOUBLE_MARK) {
/* 2799:3099 */         val = ScriptRuntime.wrapNumber(sDbl[shift]);
/* 2800:     */       }
/* 2801:3101 */       args[i] = val;i++;
/* 2802:     */     }
/* 2803:3103 */     return args;
/* 2804:     */   }
/* 2805:     */   
/* 2806:     */   private static void addInstructionCount(Context cx, CallFrame frame, int extra)
/* 2807:     */   {
/* 2808:3109 */     cx.instructionCount += frame.pc - frame.pcPrevBranch + extra;
/* 2809:3110 */     if (cx.instructionCount > cx.instructionThreshold)
/* 2810:     */     {
/* 2811:3111 */       cx.observeInstructionCount(cx.instructionCount);
/* 2812:3112 */       cx.instructionCount = 0;
/* 2813:     */     }
/* 2814:     */   }
/* 2815:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Interpreter
 * JD-Core Version:    0.7.0.1
 */