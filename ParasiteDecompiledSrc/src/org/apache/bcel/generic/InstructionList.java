/*    1:     */ package org.apache.bcel.generic;
/*    2:     */ 
/*    3:     */ import java.io.ByteArrayOutputStream;
/*    4:     */ import java.io.DataOutputStream;
/*    5:     */ import java.io.FilterInputStream;
/*    6:     */ import java.io.IOException;
/*    7:     */ import java.io.PrintStream;
/*    8:     */ import java.io.Serializable;
/*    9:     */ import java.util.AbstractCollection;
/*   10:     */ import java.util.AbstractList;
/*   11:     */ import java.util.ArrayList;
/*   12:     */ import java.util.HashMap;
/*   13:     */ import java.util.Iterator;
/*   14:     */ import org.apache.bcel.classfile.Constant;
/*   15:     */ import org.apache.bcel.util.ByteSequence;
/*   16:     */ 
/*   17:     */ public class InstructionList
/*   18:     */   implements Serializable
/*   19:     */ {
/*   20:  85 */   private InstructionHandle end = null;
/*   21:  85 */   private InstructionHandle start = null;
/*   22:  86 */   private int length = 0;
/*   23:     */   private int[] byte_positions;
/*   24:     */   private ArrayList observers;
/*   25:     */   
/*   26:     */   public InstructionList() {}
/*   27:     */   
/*   28:     */   public InstructionList(Instruction i)
/*   29:     */   {
/*   30:  99 */     append(i);
/*   31:     */   }
/*   32:     */   
/*   33:     */   public InstructionList(BranchInstruction i)
/*   34:     */   {
/*   35: 107 */     append(i);
/*   36:     */   }
/*   37:     */   
/*   38:     */   public InstructionList(CompoundInstruction c)
/*   39:     */   {
/*   40: 117 */     append(c.getInstructionList());
/*   41:     */   }
/*   42:     */   
/*   43:     */   public boolean isEmpty()
/*   44:     */   {
/*   45: 123 */     return this.start == null;
/*   46:     */   }
/*   47:     */   
/*   48:     */   public static InstructionHandle findHandle(InstructionHandle[] ihs, int[] pos, int count, int target)
/*   49:     */   {
/*   50: 138 */     int l = 0;int r = count - 1;
/*   51:     */     do
/*   52:     */     {
/*   53: 143 */       int i = (l + r) / 2;
/*   54: 144 */       int j = pos[i];
/*   55: 146 */       if (j == target) {
/*   56: 147 */         return ihs[i];
/*   57:     */       }
/*   58: 148 */       if (target < j) {
/*   59: 149 */         r = i - 1;
/*   60:     */       } else {
/*   61: 151 */         l = i + 1;
/*   62:     */       }
/*   63: 152 */     } while (l <= r);
/*   64: 154 */     return null;
/*   65:     */   }
/*   66:     */   
/*   67:     */   public InstructionHandle findHandle(int pos)
/*   68:     */   {
/*   69: 166 */     InstructionHandle[] ihs = getInstructionHandles();
/*   70: 167 */     return findHandle(ihs, this.byte_positions, this.length, pos);
/*   71:     */   }
/*   72:     */   
/*   73:     */   public InstructionList(byte[] code)
/*   74:     */   {
/*   75: 176 */     ByteSequence bytes = new ByteSequence(code);
/*   76: 177 */     InstructionHandle[] ihs = new InstructionHandle[code.length];
/*   77: 178 */     int[] pos = new int[code.length];
/*   78: 179 */     int count = 0;
/*   79:     */     try
/*   80:     */     {
/*   81: 185 */       while (bytes.available() > 0)
/*   82:     */       {
/*   83: 187 */         int off = bytes.getIndex();
/*   84: 188 */         pos[count] = off;
/*   85:     */         
/*   86:     */ 
/*   87:     */ 
/*   88:     */ 
/*   89: 193 */         Instruction i = Instruction.readInstruction(bytes);
/*   90:     */         InstructionHandle ih;
/*   91: 195 */         if ((i instanceof BranchInstruction)) {
/*   92: 196 */           ih = append((BranchInstruction)i);
/*   93:     */         } else {
/*   94: 198 */           ih = append(i);
/*   95:     */         }
/*   96: 200 */         ih.setPosition(off);
/*   97: 201 */         ihs[count] = ih;
/*   98:     */         
/*   99: 203 */         count++;
/*  100:     */       }
/*  101:     */     }
/*  102:     */     catch (IOException e)
/*  103:     */     {
/*  104: 205 */       throw new ClassGenException(e.toString());
/*  105:     */     }
/*  106: 207 */     this.byte_positions = new int[count];
/*  107: 208 */     System.arraycopy(pos, 0, this.byte_positions, 0, count);
/*  108: 213 */     for (int i = 0; i < count; i++) {
/*  109: 214 */       if ((ihs[i] instanceof BranchHandle))
/*  110:     */       {
/*  111: 215 */         BranchInstruction bi = (BranchInstruction)ihs[i].instruction;
/*  112: 216 */         int target = bi.position + bi.getIndex();
/*  113:     */         
/*  114:     */ 
/*  115: 219 */         InstructionHandle ih = findHandle(ihs, pos, count, target);
/*  116: 221 */         if (ih == null) {
/*  117: 222 */           throw new ClassGenException("Couldn't find target for branch: " + bi);
/*  118:     */         }
/*  119: 224 */         bi.setTarget(ih);
/*  120: 227 */         if ((bi instanceof Select))
/*  121:     */         {
/*  122: 228 */           Select s = (Select)bi;
/*  123: 229 */           int[] indices = s.getIndices();
/*  124: 231 */           for (int j = 0; j < indices.length; j++)
/*  125:     */           {
/*  126: 232 */             target = bi.position + indices[j];
/*  127: 233 */             ih = findHandle(ihs, pos, count, target);
/*  128: 235 */             if (ih == null) {
/*  129: 236 */               throw new ClassGenException("Couldn't find target for switch: " + bi);
/*  130:     */             }
/*  131: 238 */             s.setTarget(j, ih);
/*  132:     */           }
/*  133:     */         }
/*  134:     */       }
/*  135:     */     }
/*  136:     */   }
/*  137:     */   
/*  138:     */   public InstructionHandle append(InstructionHandle ih, InstructionList il)
/*  139:     */   {
/*  140: 254 */     if (il == null) {
/*  141: 255 */       throw new ClassGenException("Appending null InstructionList");
/*  142:     */     }
/*  143: 257 */     if (il.isEmpty()) {
/*  144: 258 */       return ih;
/*  145:     */     }
/*  146: 260 */     InstructionHandle next = ih.next;InstructionHandle ret = il.start;
/*  147:     */     
/*  148: 262 */     ih.next = il.start;
/*  149: 263 */     il.start.prev = ih;
/*  150:     */     
/*  151: 265 */     il.end.next = next;
/*  152: 267 */     if (next != null) {
/*  153: 268 */       next.prev = il.end;
/*  154:     */     } else {
/*  155: 270 */       this.end = il.end;
/*  156:     */     }
/*  157: 272 */     this.length += il.length;
/*  158:     */     
/*  159: 274 */     il.clear();
/*  160:     */     
/*  161: 276 */     return ret;
/*  162:     */   }
/*  163:     */   
/*  164:     */   public InstructionHandle append(Instruction i, InstructionList il)
/*  165:     */   {
/*  166:     */     InstructionHandle ih;
/*  167: 290 */     if ((ih = findInstruction2(i)) == null) {
/*  168: 291 */       throw new ClassGenException("Instruction " + i + " is not contained in this list.");
/*  169:     */     }
/*  170: 294 */     return append(ih, il);
/*  171:     */   }
/*  172:     */   
/*  173:     */   public InstructionHandle append(InstructionList il)
/*  174:     */   {
/*  175: 305 */     if (il == null) {
/*  176: 306 */       throw new ClassGenException("Appending null InstructionList");
/*  177:     */     }
/*  178: 308 */     if (il.isEmpty()) {
/*  179: 309 */       return null;
/*  180:     */     }
/*  181: 311 */     if (isEmpty())
/*  182:     */     {
/*  183: 312 */       this.start = il.start;
/*  184: 313 */       this.end = il.end;
/*  185: 314 */       this.length = il.length;
/*  186:     */       
/*  187: 316 */       il.clear();
/*  188:     */       
/*  189: 318 */       return this.start;
/*  190:     */     }
/*  191: 320 */     return append(this.end, il);
/*  192:     */   }
/*  193:     */   
/*  194:     */   private void append(InstructionHandle ih)
/*  195:     */   {
/*  196: 329 */     if (isEmpty())
/*  197:     */     {
/*  198: 330 */       this.start = (this.end = ih);
/*  199: 331 */       ih.next = (ih.prev = null);
/*  200:     */     }
/*  201:     */     else
/*  202:     */     {
/*  203: 334 */       this.end.next = ih;
/*  204: 335 */       ih.prev = this.end;
/*  205: 336 */       ih.next = null;
/*  206: 337 */       this.end = ih;
/*  207:     */     }
/*  208: 340 */     this.length += 1;
/*  209:     */   }
/*  210:     */   
/*  211:     */   public InstructionHandle append(Instruction i)
/*  212:     */   {
/*  213: 350 */     InstructionHandle ih = InstructionHandle.getInstructionHandle(i);
/*  214: 351 */     append(ih);
/*  215:     */     
/*  216: 353 */     return ih;
/*  217:     */   }
/*  218:     */   
/*  219:     */   public BranchHandle append(BranchInstruction i)
/*  220:     */   {
/*  221: 363 */     BranchHandle ih = BranchHandle.getBranchHandle(i);
/*  222: 364 */     append(ih);
/*  223:     */     
/*  224: 366 */     return ih;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public InstructionHandle append(Instruction i, Instruction j)
/*  228:     */   {
/*  229: 378 */     return append(i, new InstructionList(j));
/*  230:     */   }
/*  231:     */   
/*  232:     */   public InstructionHandle append(Instruction i, CompoundInstruction c)
/*  233:     */   {
/*  234: 389 */     return append(i, c.getInstructionList());
/*  235:     */   }
/*  236:     */   
/*  237:     */   public InstructionHandle append(CompoundInstruction c)
/*  238:     */   {
/*  239: 399 */     return append(c.getInstructionList());
/*  240:     */   }
/*  241:     */   
/*  242:     */   public InstructionHandle append(InstructionHandle ih, CompoundInstruction c)
/*  243:     */   {
/*  244: 410 */     return append(ih, c.getInstructionList());
/*  245:     */   }
/*  246:     */   
/*  247:     */   public InstructionHandle append(InstructionHandle ih, Instruction i)
/*  248:     */   {
/*  249: 421 */     return append(ih, new InstructionList(i));
/*  250:     */   }
/*  251:     */   
/*  252:     */   public BranchHandle append(InstructionHandle ih, BranchInstruction i)
/*  253:     */   {
/*  254: 432 */     BranchHandle bh = BranchHandle.getBranchHandle(i);
/*  255: 433 */     InstructionList il = new InstructionList();
/*  256: 434 */     il.append(bh);
/*  257:     */     
/*  258: 436 */     append(ih, il);
/*  259:     */     
/*  260: 438 */     return bh;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public InstructionHandle insert(InstructionHandle ih, InstructionList il)
/*  264:     */   {
/*  265: 450 */     if (il == null) {
/*  266: 451 */       throw new ClassGenException("Inserting null InstructionList");
/*  267:     */     }
/*  268: 453 */     if (il.isEmpty()) {
/*  269: 454 */       return ih;
/*  270:     */     }
/*  271: 456 */     InstructionHandle prev = ih.prev;InstructionHandle ret = il.start;
/*  272:     */     
/*  273: 458 */     ih.prev = il.end;
/*  274: 459 */     il.end.next = ih;
/*  275:     */     
/*  276: 461 */     il.start.prev = prev;
/*  277: 463 */     if (prev != null) {
/*  278: 464 */       prev.next = il.start;
/*  279:     */     } else {
/*  280: 466 */       this.start = il.start;
/*  281:     */     }
/*  282: 468 */     this.length += il.length;
/*  283:     */     
/*  284: 470 */     il.clear();
/*  285:     */     
/*  286: 472 */     return ret;
/*  287:     */   }
/*  288:     */   
/*  289:     */   public InstructionHandle insert(InstructionList il)
/*  290:     */   {
/*  291: 482 */     if (isEmpty())
/*  292:     */     {
/*  293: 483 */       append(il);
/*  294: 484 */       return this.start;
/*  295:     */     }
/*  296: 487 */     return insert(this.start, il);
/*  297:     */   }
/*  298:     */   
/*  299:     */   private void insert(InstructionHandle ih)
/*  300:     */   {
/*  301: 496 */     if (isEmpty())
/*  302:     */     {
/*  303: 497 */       this.start = (this.end = ih);
/*  304: 498 */       ih.next = (ih.prev = null);
/*  305:     */     }
/*  306:     */     else
/*  307:     */     {
/*  308: 500 */       this.start.prev = ih;
/*  309: 501 */       ih.next = this.start;
/*  310: 502 */       ih.prev = null;
/*  311: 503 */       this.start = ih;
/*  312:     */     }
/*  313: 506 */     this.length += 1;
/*  314:     */   }
/*  315:     */   
/*  316:     */   public InstructionHandle insert(Instruction i, InstructionList il)
/*  317:     */   {
/*  318:     */     InstructionHandle ih;
/*  319: 521 */     if ((ih = findInstruction1(i)) == null) {
/*  320: 522 */       throw new ClassGenException("Instruction " + i + " is not contained in this list.");
/*  321:     */     }
/*  322: 525 */     return insert(ih, il);
/*  323:     */   }
/*  324:     */   
/*  325:     */   public InstructionHandle insert(Instruction i)
/*  326:     */   {
/*  327: 535 */     InstructionHandle ih = InstructionHandle.getInstructionHandle(i);
/*  328: 536 */     insert(ih);
/*  329:     */     
/*  330: 538 */     return ih;
/*  331:     */   }
/*  332:     */   
/*  333:     */   public BranchHandle insert(BranchInstruction i)
/*  334:     */   {
/*  335: 548 */     BranchHandle ih = BranchHandle.getBranchHandle(i);
/*  336: 549 */     insert(ih);
/*  337: 550 */     return ih;
/*  338:     */   }
/*  339:     */   
/*  340:     */   public InstructionHandle insert(Instruction i, Instruction j)
/*  341:     */   {
/*  342: 562 */     return insert(i, new InstructionList(j));
/*  343:     */   }
/*  344:     */   
/*  345:     */   public InstructionHandle insert(Instruction i, CompoundInstruction c)
/*  346:     */   {
/*  347: 573 */     return insert(i, c.getInstructionList());
/*  348:     */   }
/*  349:     */   
/*  350:     */   public InstructionHandle insert(CompoundInstruction c)
/*  351:     */   {
/*  352: 583 */     return insert(c.getInstructionList());
/*  353:     */   }
/*  354:     */   
/*  355:     */   public InstructionHandle insert(InstructionHandle ih, Instruction i)
/*  356:     */   {
/*  357: 594 */     return insert(ih, new InstructionList(i));
/*  358:     */   }
/*  359:     */   
/*  360:     */   public InstructionHandle insert(InstructionHandle ih, CompoundInstruction c)
/*  361:     */   {
/*  362: 605 */     return insert(ih, c.getInstructionList());
/*  363:     */   }
/*  364:     */   
/*  365:     */   public BranchHandle insert(InstructionHandle ih, BranchInstruction i)
/*  366:     */   {
/*  367: 616 */     BranchHandle bh = BranchHandle.getBranchHandle(i);
/*  368: 617 */     InstructionList il = new InstructionList();
/*  369: 618 */     il.append(bh);
/*  370:     */     
/*  371: 620 */     insert(ih, il);
/*  372:     */     
/*  373: 622 */     return bh;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void move(InstructionHandle start, InstructionHandle end, InstructionHandle target)
/*  377:     */   {
/*  378: 639 */     if ((start == null) || (end == null)) {
/*  379: 640 */       throw new ClassGenException("Invalid null handle: From " + start + " to " + end);
/*  380:     */     }
/*  381: 642 */     if ((target == start) || (target == end)) {
/*  382: 643 */       throw new ClassGenException("Invalid range: From " + start + " to " + end + " contains target " + target);
/*  383:     */     }
/*  384: 646 */     for (InstructionHandle ih = start; ih != end.next; ih = ih.next)
/*  385:     */     {
/*  386: 647 */       if (ih == null) {
/*  387: 648 */         throw new ClassGenException("Invalid range: From " + start + " to " + end);
/*  388:     */       }
/*  389: 649 */       if (ih == target) {
/*  390: 650 */         throw new ClassGenException("Invalid range: From " + start + " to " + end + " contains target " + target);
/*  391:     */       }
/*  392:     */     }
/*  393: 656 */     InstructionHandle prev = start.prev;InstructionHandle next = end.next;
/*  394: 658 */     if (prev != null) {
/*  395: 659 */       prev.next = next;
/*  396:     */     } else {
/*  397: 661 */       this.start = next;
/*  398:     */     }
/*  399: 663 */     if (next != null) {
/*  400: 664 */       next.prev = prev;
/*  401:     */     } else {
/*  402: 666 */       this.end = prev;
/*  403:     */     }
/*  404: 668 */     start.prev = (end.next = null);
/*  405: 672 */     if (target == null)
/*  406:     */     {
/*  407: 673 */       end.next = this.start;
/*  408: 674 */       this.start = start;
/*  409:     */     }
/*  410:     */     else
/*  411:     */     {
/*  412: 676 */       next = target.next;
/*  413:     */       
/*  414: 678 */       target.next = start;
/*  415: 679 */       start.prev = target;
/*  416: 680 */       end.next = next;
/*  417: 682 */       if (next != null) {
/*  418: 683 */         next.prev = end;
/*  419:     */       }
/*  420:     */     }
/*  421:     */   }
/*  422:     */   
/*  423:     */   public void move(InstructionHandle ih, InstructionHandle target)
/*  424:     */   {
/*  425: 694 */     move(ih, ih, target);
/*  426:     */   }
/*  427:     */   
/*  428:     */   private void remove(InstructionHandle prev, InstructionHandle next)
/*  429:     */     throws TargetLostException
/*  430:     */   {
/*  431:     */     InstructionHandle last;
/*  432:     */     InstructionHandle first;
/*  433: 710 */     if ((prev == null) && (next == null))
/*  434:     */     {
/*  435: 711 */       first = last = this.start;
/*  436: 712 */       this.start = (this.end = null);
/*  437:     */     }
/*  438:     */     else
/*  439:     */     {
/*  440: 714 */       if (prev == null)
/*  441:     */       {
/*  442: 715 */         first = this.start;
/*  443: 716 */         this.start = next;
/*  444:     */       }
/*  445:     */       else
/*  446:     */       {
/*  447: 718 */         first = prev.next;
/*  448: 719 */         prev.next = next;
/*  449:     */       }
/*  450: 722 */       if (next == null)
/*  451:     */       {
/*  452: 723 */         last = this.end;
/*  453: 724 */         this.end = prev;
/*  454:     */       }
/*  455:     */       else
/*  456:     */       {
/*  457: 726 */         last = next.prev;
/*  458: 727 */         next.prev = prev;
/*  459:     */       }
/*  460:     */     }
/*  461: 731 */     first.prev = null;
/*  462: 732 */     last.next = null;
/*  463:     */     
/*  464: 734 */     ArrayList target_vec = new ArrayList();
/*  465: 736 */     for (InstructionHandle ih = first; ih != null; ih = ih.next) {
/*  466: 737 */       ih.getInstruction().dispose();
/*  467:     */     }
/*  468: 739 */     StringBuffer buf = new StringBuffer("{ ");
/*  469: 740 */     for (InstructionHandle ih = first; ih != null; ih = next)
/*  470:     */     {
/*  471: 741 */       next = ih.next;
/*  472: 742 */       this.length -= 1;
/*  473: 744 */       if (ih.hasTargeters())
/*  474:     */       {
/*  475: 745 */         target_vec.add(ih);
/*  476: 746 */         buf.append(ih.toString(true) + " ");
/*  477: 747 */         ih.next = (ih.prev = null);
/*  478:     */       }
/*  479:     */       else
/*  480:     */       {
/*  481: 749 */         ih.dispose();
/*  482:     */       }
/*  483:     */     }
/*  484: 752 */     buf.append("}");
/*  485: 754 */     if (!target_vec.isEmpty())
/*  486:     */     {
/*  487: 755 */       InstructionHandle[] targeted = new InstructionHandle[target_vec.size()];
/*  488: 756 */       target_vec.toArray(targeted);
/*  489: 757 */       throw new TargetLostException(targeted, buf.toString());
/*  490:     */     }
/*  491:     */   }
/*  492:     */   
/*  493:     */   public void delete(InstructionHandle ih)
/*  494:     */     throws TargetLostException
/*  495:     */   {
/*  496: 768 */     remove(ih.prev, ih.next);
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void delete(Instruction i)
/*  500:     */     throws TargetLostException
/*  501:     */   {
/*  502:     */     InstructionHandle ih;
/*  503: 780 */     if ((ih = findInstruction1(i)) == null) {
/*  504: 781 */       throw new ClassGenException("Instruction " + i + " is not contained in this list.");
/*  505:     */     }
/*  506: 783 */     delete(ih);
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void delete(InstructionHandle from, InstructionHandle to)
/*  510:     */     throws TargetLostException
/*  511:     */   {
/*  512: 797 */     remove(from.prev, to.next);
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void delete(Instruction from, Instruction to)
/*  516:     */     throws TargetLostException
/*  517:     */   {
/*  518:     */     InstructionHandle from_ih;
/*  519: 811 */     if ((from_ih = findInstruction1(from)) == null) {
/*  520: 812 */       throw new ClassGenException("Instruction " + from + " is not contained in this list.");
/*  521:     */     }
/*  522:     */     InstructionHandle to_ih;
/*  523: 815 */     if ((to_ih = findInstruction2(to)) == null) {
/*  524: 816 */       throw new ClassGenException("Instruction " + to + " is not contained in this list.");
/*  525:     */     }
/*  526: 818 */     delete(from_ih, to_ih);
/*  527:     */   }
/*  528:     */   
/*  529:     */   private InstructionHandle findInstruction1(Instruction i)
/*  530:     */   {
/*  531: 828 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next) {
/*  532: 829 */       if (ih.instruction == i) {
/*  533: 830 */         return ih;
/*  534:     */       }
/*  535:     */     }
/*  536: 832 */     return null;
/*  537:     */   }
/*  538:     */   
/*  539:     */   private InstructionHandle findInstruction2(Instruction i)
/*  540:     */   {
/*  541: 842 */     for (InstructionHandle ih = this.end; ih != null; ih = ih.prev) {
/*  542: 843 */       if (ih.instruction == i) {
/*  543: 844 */         return ih;
/*  544:     */       }
/*  545:     */     }
/*  546: 846 */     return null;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public boolean contains(InstructionHandle i)
/*  550:     */   {
/*  551: 850 */     if (i == null) {
/*  552: 851 */       return false;
/*  553:     */     }
/*  554: 853 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next) {
/*  555: 854 */       if (ih == i) {
/*  556: 855 */         return true;
/*  557:     */       }
/*  558:     */     }
/*  559: 857 */     return false;
/*  560:     */   }
/*  561:     */   
/*  562:     */   public boolean contains(Instruction i)
/*  563:     */   {
/*  564: 861 */     return findInstruction1(i) != null;
/*  565:     */   }
/*  566:     */   
/*  567:     */   public void setPositions()
/*  568:     */   {
/*  569: 865 */     setPositions(false);
/*  570:     */   }
/*  571:     */   
/*  572:     */   public void setPositions(boolean check)
/*  573:     */   {
/*  574: 876 */     int max_additional_bytes = 0;int additional_bytes = 0;
/*  575: 877 */     int index = 0;int count = 0;
/*  576: 878 */     int[] pos = new int[this.length];
/*  577: 882 */     if (check) {
/*  578: 883 */       for (InstructionHandle ih = this.start; ih != null; ih = ih.next)
/*  579:     */       {
/*  580: 884 */         Instruction i = ih.instruction;
/*  581: 886 */         if ((i instanceof BranchInstruction))
/*  582:     */         {
/*  583: 887 */           Instruction inst = ((BranchInstruction)i).getTarget().instruction;
/*  584: 888 */           if (!contains(inst)) {
/*  585: 889 */             throw new ClassGenException("Branch target of " + org.apache.bcel.Constants.OPCODE_NAMES[i.opcode] + ":" + inst + " not in instruction list");
/*  586:     */           }
/*  587: 893 */           if ((i instanceof Select))
/*  588:     */           {
/*  589: 894 */             InstructionHandle[] targets = ((Select)i).getTargets();
/*  590: 896 */             for (int j = 0; j < targets.length; j++)
/*  591:     */             {
/*  592: 897 */               inst = targets[j].instruction;
/*  593: 898 */               if (!contains(inst)) {
/*  594: 899 */                 throw new ClassGenException("Branch target of " + org.apache.bcel.Constants.OPCODE_NAMES[i.opcode] + ":" + inst + " not in instruction list");
/*  595:     */               }
/*  596:     */             }
/*  597:     */           }
/*  598: 905 */           if (!(ih instanceof BranchHandle)) {
/*  599: 906 */             throw new ClassGenException("Branch instruction " + org.apache.bcel.Constants.OPCODE_NAMES[i.opcode] + ":" + inst + " not contained in BranchHandle.");
/*  600:     */           }
/*  601:     */         }
/*  602:     */       }
/*  603:     */     }
/*  604: 917 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next)
/*  605:     */     {
/*  606: 918 */       Instruction i = ih.instruction;
/*  607:     */       
/*  608: 920 */       ih.setPosition(index);
/*  609: 921 */       pos[(count++)] = index;
/*  610: 928 */       switch (i.getOpcode())
/*  611:     */       {
/*  612:     */       case 167: 
/*  613:     */       case 168: 
/*  614: 930 */         max_additional_bytes += 2;
/*  615: 931 */         break;
/*  616:     */       case 170: 
/*  617:     */       case 171: 
/*  618: 934 */         max_additional_bytes += 3;
/*  619:     */       }
/*  620: 938 */       index += i.getLength();
/*  621:     */     }
/*  622: 945 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next) {
/*  623: 946 */       additional_bytes += ih.updatePosition(additional_bytes, max_additional_bytes);
/*  624:     */     }
/*  625: 951 */     index = count = 0;
/*  626: 952 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next)
/*  627:     */     {
/*  628: 953 */       Instruction i = ih.instruction;
/*  629:     */       
/*  630: 955 */       ih.setPosition(index);
/*  631: 956 */       pos[(count++)] = index;
/*  632: 957 */       index += i.getLength();
/*  633:     */     }
/*  634: 960 */     this.byte_positions = new int[count];
/*  635: 961 */     System.arraycopy(pos, 0, this.byte_positions, 0, count);
/*  636:     */   }
/*  637:     */   
/*  638:     */   public byte[] getByteCode()
/*  639:     */   {
/*  640: 972 */     setPositions();
/*  641:     */     
/*  642: 974 */     ByteArrayOutputStream b = new ByteArrayOutputStream();
/*  643: 975 */     DataOutputStream out = new DataOutputStream(b);
/*  644:     */     try
/*  645:     */     {
/*  646: 978 */       for (InstructionHandle ih = this.start; ih != null; ih = ih.next)
/*  647:     */       {
/*  648: 979 */         Instruction i = ih.instruction;
/*  649: 980 */         i.dump(out);
/*  650:     */       }
/*  651:     */     }
/*  652:     */     catch (IOException e)
/*  653:     */     {
/*  654: 983 */       System.err.println(e);
/*  655: 984 */       return null;
/*  656:     */     }
/*  657: 987 */     return b.toByteArray();
/*  658:     */   }
/*  659:     */   
/*  660:     */   public Instruction[] getInstructions()
/*  661:     */   {
/*  662: 994 */     ByteSequence bytes = new ByteSequence(getByteCode());
/*  663: 995 */     ArrayList instructions = new ArrayList();
/*  664:     */     try
/*  665:     */     {
/*  666: 998 */       while (bytes.available() > 0) {
/*  667: 999 */         instructions.add(Instruction.readInstruction(bytes));
/*  668:     */       }
/*  669:     */     }
/*  670:     */     catch (IOException e)
/*  671:     */     {
/*  672:1001 */       throw new ClassGenException(e.toString());
/*  673:     */     }
/*  674:1003 */     Instruction[] result = new Instruction[instructions.size()];
/*  675:1004 */     instructions.toArray(result);
/*  676:1005 */     return result;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public String toString()
/*  680:     */   {
/*  681:1009 */     return toString(true);
/*  682:     */   }
/*  683:     */   
/*  684:     */   public String toString(boolean verbose)
/*  685:     */   {
/*  686:1017 */     StringBuffer buf = new StringBuffer();
/*  687:1019 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next) {
/*  688:1020 */       buf.append(ih.toString(verbose) + "\n");
/*  689:     */     }
/*  690:1023 */     return buf.toString();
/*  691:     */   }
/*  692:     */   
/*  693:     */   public Iterator iterator()
/*  694:     */   {
/*  695:1030 */     new Iterator()
/*  696:     */     {
/*  697:1031 */       private InstructionHandle ih = InstructionList.this.start;
/*  698:     */       
/*  699:     */       public Object next()
/*  700:     */       {
/*  701:1034 */         InstructionHandle i = this.ih;
/*  702:1035 */         this.ih = this.ih.next;
/*  703:1036 */         return i;
/*  704:     */       }
/*  705:     */       
/*  706:     */       public void remove()
/*  707:     */       {
/*  708:1040 */         throw new UnsupportedOperationException();
/*  709:     */       }
/*  710:     */       
/*  711:     */       public boolean hasNext()
/*  712:     */       {
/*  713:1043 */         return this.ih != null;
/*  714:     */       }
/*  715:     */     };
/*  716:     */   }
/*  717:     */   
/*  718:     */   public InstructionHandle[] getInstructionHandles()
/*  719:     */   {
/*  720:1051 */     InstructionHandle[] ihs = new InstructionHandle[this.length];
/*  721:1052 */     InstructionHandle ih = this.start;
/*  722:1054 */     for (int i = 0; i < this.length; i++)
/*  723:     */     {
/*  724:1055 */       ihs[i] = ih;
/*  725:1056 */       ih = ih.next;
/*  726:     */     }
/*  727:1059 */     return ihs;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public int[] getInstructionPositions()
/*  731:     */   {
/*  732:1069 */     return this.byte_positions;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public InstructionList copy()
/*  736:     */   {
/*  737:1075 */     HashMap map = new HashMap();
/*  738:1076 */     InstructionList il = new InstructionList();
/*  739:1082 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next)
/*  740:     */     {
/*  741:1083 */       Instruction i = ih.instruction;
/*  742:1084 */       Instruction c = i.copy();
/*  743:1086 */       if ((c instanceof BranchInstruction)) {
/*  744:1087 */         map.put(ih, il.append((BranchInstruction)c));
/*  745:     */       } else {
/*  746:1089 */         map.put(ih, il.append(c));
/*  747:     */       }
/*  748:     */     }
/*  749:1094 */     InstructionHandle ih = this.start;
/*  750:1095 */     InstructionHandle ch = il.start;
/*  751:1097 */     while (ih != null)
/*  752:     */     {
/*  753:1098 */       Instruction i = ih.instruction;
/*  754:1099 */       Instruction c = ch.instruction;
/*  755:1101 */       if ((i instanceof BranchInstruction))
/*  756:     */       {
/*  757:1102 */         BranchInstruction bi = (BranchInstruction)i;
/*  758:1103 */         BranchInstruction bc = (BranchInstruction)c;
/*  759:1104 */         InstructionHandle itarget = bi.getTarget();
/*  760:     */         
/*  761:     */ 
/*  762:1107 */         bc.setTarget((InstructionHandle)map.get(itarget));
/*  763:1109 */         if ((bi instanceof Select))
/*  764:     */         {
/*  765:1110 */           InstructionHandle[] itargets = ((Select)bi).getTargets();
/*  766:1111 */           InstructionHandle[] ctargets = ((Select)bc).getTargets();
/*  767:1113 */           for (int j = 0; j < itargets.length; j++) {
/*  768:1114 */             ctargets[j] = ((InstructionHandle)map.get(itargets[j]));
/*  769:     */           }
/*  770:     */         }
/*  771:     */       }
/*  772:1119 */       ih = ih.next;
/*  773:1120 */       ch = ch.next;
/*  774:     */     }
/*  775:1123 */     return il;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public void replaceConstantPool(ConstantPoolGen old_cp, ConstantPoolGen new_cp)
/*  779:     */   {
/*  780:1130 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next)
/*  781:     */     {
/*  782:1131 */       Instruction i = ih.instruction;
/*  783:1133 */       if ((i instanceof CPInstruction))
/*  784:     */       {
/*  785:1134 */         CPInstruction ci = (CPInstruction)i;
/*  786:1135 */         Constant c = old_cp.getConstant(ci.getIndex());
/*  787:1136 */         ci.setIndex(new_cp.addConstant(c, old_cp));
/*  788:     */       }
/*  789:     */     }
/*  790:     */   }
/*  791:     */   
/*  792:     */   private void clear()
/*  793:     */   {
/*  794:1142 */     this.start = (this.end = null);
/*  795:1143 */     this.length = 0;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public void dispose()
/*  799:     */   {
/*  800:1154 */     for (InstructionHandle ih = this.end; ih != null; ih = ih.prev) {
/*  801:1158 */       ih.dispose();
/*  802:     */     }
/*  803:1160 */     clear();
/*  804:     */   }
/*  805:     */   
/*  806:     */   public InstructionHandle getStart()
/*  807:     */   {
/*  808:1166 */     return this.start;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public InstructionHandle getEnd()
/*  812:     */   {
/*  813:1171 */     return this.end;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public int getLength()
/*  817:     */   {
/*  818:1176 */     return this.length;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public int size()
/*  822:     */   {
/*  823:1181 */     return this.length;
/*  824:     */   }
/*  825:     */   
/*  826:     */   public void redirectBranches(InstructionHandle old_target, InstructionHandle new_target)
/*  827:     */   {
/*  828:1192 */     for (InstructionHandle ih = this.start; ih != null; ih = ih.next)
/*  829:     */     {
/*  830:1193 */       Instruction i = ih.getInstruction();
/*  831:1195 */       if ((i instanceof BranchInstruction))
/*  832:     */       {
/*  833:1196 */         BranchInstruction b = (BranchInstruction)i;
/*  834:1197 */         InstructionHandle target = b.getTarget();
/*  835:1199 */         if (target == old_target) {
/*  836:1200 */           b.setTarget(new_target);
/*  837:     */         }
/*  838:1202 */         if ((b instanceof Select))
/*  839:     */         {
/*  840:1203 */           InstructionHandle[] targets = ((Select)b).getTargets();
/*  841:1205 */           for (int j = 0; j < targets.length; j++) {
/*  842:1206 */             if (targets[j] == old_target) {
/*  843:1207 */               ((Select)b).setTarget(j, new_target);
/*  844:     */             }
/*  845:     */           }
/*  846:     */         }
/*  847:     */       }
/*  848:     */     }
/*  849:     */   }
/*  850:     */   
/*  851:     */   public void redirectLocalVariables(LocalVariableGen[] lg, InstructionHandle old_target, InstructionHandle new_target)
/*  852:     */   {
/*  853:1224 */     for (int i = 0; i < lg.length; i++)
/*  854:     */     {
/*  855:1225 */       InstructionHandle start = lg[i].getStart();
/*  856:1226 */       InstructionHandle end = lg[i].getEnd();
/*  857:1228 */       if (start == old_target) {
/*  858:1229 */         lg[i].setStart(new_target);
/*  859:     */       }
/*  860:1231 */       if (end == old_target) {
/*  861:1232 */         lg[i].setEnd(new_target);
/*  862:     */       }
/*  863:     */     }
/*  864:     */   }
/*  865:     */   
/*  866:     */   public void redirectExceptionHandlers(CodeExceptionGen[] exceptions, InstructionHandle old_target, InstructionHandle new_target)
/*  867:     */   {
/*  868:1247 */     for (int i = 0; i < exceptions.length; i++)
/*  869:     */     {
/*  870:1248 */       if (exceptions[i].getStartPC() == old_target) {
/*  871:1249 */         exceptions[i].setStartPC(new_target);
/*  872:     */       }
/*  873:1251 */       if (exceptions[i].getEndPC() == old_target) {
/*  874:1252 */         exceptions[i].setEndPC(new_target);
/*  875:     */       }
/*  876:1254 */       if (exceptions[i].getHandlerPC() == old_target) {
/*  877:1255 */         exceptions[i].setHandlerPC(new_target);
/*  878:     */       }
/*  879:     */     }
/*  880:     */   }
/*  881:     */   
/*  882:     */   public void addObserver(InstructionListObserver o)
/*  883:     */   {
/*  884:1264 */     if (this.observers == null) {
/*  885:1265 */       this.observers = new ArrayList();
/*  886:     */     }
/*  887:1267 */     this.observers.add(o);
/*  888:     */   }
/*  889:     */   
/*  890:     */   public void removeObserver(InstructionListObserver o)
/*  891:     */   {
/*  892:1273 */     if (this.observers != null) {
/*  893:1274 */       this.observers.remove(o);
/*  894:     */     }
/*  895:     */   }
/*  896:     */   
/*  897:     */   public void update()
/*  898:     */   {
/*  899:1282 */     if (this.observers != null) {
/*  900:1283 */       for (Iterator e = this.observers.iterator(); e.hasNext();) {
/*  901:1284 */         ((InstructionListObserver)e.next()).notify(this);
/*  902:     */       }
/*  903:     */     }
/*  904:     */   }
/*  905:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.InstructionList
 * JD-Core Version:    0.7.0.1
 */