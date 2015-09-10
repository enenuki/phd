/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.ObjArray;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.ObjToIntMap;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.ObjToIntMap.Iterator;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.Jump;
/*  11:    */ 
/*  12:    */ class Block
/*  13:    */ {
/*  14:    */   private Block[] itsSuccessors;
/*  15:    */   private Block[] itsPredecessors;
/*  16:    */   private int itsStartNodeIndex;
/*  17:    */   private int itsEndNodeIndex;
/*  18:    */   private int itsBlockID;
/*  19:    */   private DataFlowBitSet itsLiveOnEntrySet;
/*  20:    */   private DataFlowBitSet itsLiveOnExitSet;
/*  21:    */   private DataFlowBitSet itsUseBeforeDefSet;
/*  22:    */   private DataFlowBitSet itsNotDefSet;
/*  23:    */   static final boolean DEBUG = false;
/*  24:    */   private static int debug_blockCount;
/*  25:    */   
/*  26:    */   private static class FatBlock
/*  27:    */   {
/*  28:    */     private static Block[] reduceToArray(ObjToIntMap map)
/*  29:    */     {
/*  30: 59 */       Block[] result = null;
/*  31: 60 */       if (!map.isEmpty())
/*  32:    */       {
/*  33: 61 */         result = new Block[map.size()];
/*  34: 62 */         int i = 0;
/*  35: 63 */         ObjToIntMap.Iterator iter = map.newIterator();
/*  36: 64 */         for (iter.start(); !iter.done(); iter.next())
/*  37:    */         {
/*  38: 65 */           FatBlock fb = (FatBlock)iter.getKey();
/*  39: 66 */           result[(i++)] = fb.realBlock;
/*  40:    */         }
/*  41:    */       }
/*  42: 69 */       return result;
/*  43:    */     }
/*  44:    */     
/*  45:    */     void addSuccessor(FatBlock b)
/*  46:    */     {
/*  47: 72 */       this.successors.put(b, 0);
/*  48:    */     }
/*  49:    */     
/*  50:    */     void addPredecessor(FatBlock b)
/*  51:    */     {
/*  52: 73 */       this.predecessors.put(b, 0);
/*  53:    */     }
/*  54:    */     
/*  55:    */     Block[] getSuccessors()
/*  56:    */     {
/*  57: 75 */       return reduceToArray(this.successors);
/*  58:    */     }
/*  59:    */     
/*  60:    */     Block[] getPredecessors()
/*  61:    */     {
/*  62: 76 */       return reduceToArray(this.predecessors);
/*  63:    */     }
/*  64:    */     
/*  65: 79 */     private ObjToIntMap successors = new ObjToIntMap();
/*  66: 81 */     private ObjToIntMap predecessors = new ObjToIntMap();
/*  67:    */     Block realBlock;
/*  68:    */   }
/*  69:    */   
/*  70:    */   Block(int startNodeIndex, int endNodeIndex)
/*  71:    */   {
/*  72: 88 */     this.itsStartNodeIndex = startNodeIndex;
/*  73: 89 */     this.itsEndNodeIndex = endNodeIndex;
/*  74:    */   }
/*  75:    */   
/*  76:    */   static void runFlowAnalyzes(OptFunctionNode fn, Node[] statementNodes)
/*  77:    */   {
/*  78: 94 */     int paramCount = fn.fnode.getParamCount();
/*  79: 95 */     int varCount = fn.fnode.getParamAndVarCount();
/*  80: 96 */     int[] varTypes = new int[varCount];
/*  81: 98 */     for (int i = 0; i != paramCount; i++) {
/*  82: 99 */       varTypes[i] = 3;
/*  83:    */     }
/*  84:103 */     for (int i = paramCount; i != varCount; i++) {
/*  85:104 */       varTypes[i] = 0;
/*  86:    */     }
/*  87:107 */     Block[] theBlocks = buildBlocks(statementNodes);
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:115 */     reachingDefDataFlow(fn, statementNodes, theBlocks, varTypes);
/*  96:116 */     typeFlow(fn, statementNodes, theBlocks, varTypes);
/*  97:129 */     for (int i = paramCount; i != varCount; i++) {
/*  98:130 */       if (varTypes[i] == 1) {
/*  99:131 */         fn.setIsNumberVar(i);
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private static Block[] buildBlocks(Node[] statementNodes)
/* 105:    */   {
/* 106:140 */     Map<Node, FatBlock> theTargetBlocks = new HashMap();
/* 107:141 */     ObjArray theBlocks = new ObjArray();
/* 108:    */     
/* 109:    */ 
/* 110:144 */     int beginNodeIndex = 0;
/* 111:146 */     for (int i = 0; i < statementNodes.length; i++) {
/* 112:147 */       switch (statementNodes[i].getType())
/* 113:    */       {
/* 114:    */       case 131: 
/* 115:150 */         if (i != beginNodeIndex)
/* 116:    */         {
/* 117:151 */           FatBlock fb = newFatBlock(beginNodeIndex, i - 1);
/* 118:152 */           if (statementNodes[beginNodeIndex].getType() == 131) {
/* 119:154 */             theTargetBlocks.put(statementNodes[beginNodeIndex], fb);
/* 120:    */           }
/* 121:155 */           theBlocks.add(fb);
/* 122:    */           
/* 123:157 */           beginNodeIndex = i;
/* 124:    */         }
/* 125:158 */         break;
/* 126:    */       case 5: 
/* 127:    */       case 6: 
/* 128:    */       case 7: 
/* 129:165 */         FatBlock fb = newFatBlock(beginNodeIndex, i);
/* 130:166 */         if (statementNodes[beginNodeIndex].getType() == 131) {
/* 131:168 */           theTargetBlocks.put(statementNodes[beginNodeIndex], fb);
/* 132:    */         }
/* 133:169 */         theBlocks.add(fb);
/* 134:    */         
/* 135:171 */         beginNodeIndex = i + 1;
/* 136:    */       }
/* 137:    */     }
/* 138:177 */     if (beginNodeIndex != statementNodes.length)
/* 139:    */     {
/* 140:178 */       FatBlock fb = newFatBlock(beginNodeIndex, statementNodes.length - 1);
/* 141:179 */       if (statementNodes[beginNodeIndex].getType() == 131) {
/* 142:180 */         theTargetBlocks.put(statementNodes[beginNodeIndex], fb);
/* 143:    */       }
/* 144:181 */       theBlocks.add(fb);
/* 145:    */     }
/* 146:186 */     for (int i = 0; i < theBlocks.size(); i++)
/* 147:    */     {
/* 148:187 */       FatBlock fb = (FatBlock)theBlocks.get(i);
/* 149:    */       
/* 150:189 */       Node blockEndNode = statementNodes[fb.realBlock.itsEndNodeIndex];
/* 151:190 */       int blockEndNodeType = blockEndNode.getType();
/* 152:192 */       if ((blockEndNodeType != 5) && (i < theBlocks.size() - 1))
/* 153:    */       {
/* 154:194 */         FatBlock fallThruTarget = (FatBlock)theBlocks.get(i + 1);
/* 155:195 */         fb.addSuccessor(fallThruTarget);
/* 156:196 */         fallThruTarget.addPredecessor(fb);
/* 157:    */       }
/* 158:200 */       if ((blockEndNodeType == 7) || (blockEndNodeType == 6) || (blockEndNodeType == 5))
/* 159:    */       {
/* 160:203 */         Node target = ((Jump)blockEndNode).target;
/* 161:204 */         FatBlock branchTargetBlock = (FatBlock)theTargetBlocks.get(target);
/* 162:205 */         target.putProp(6, branchTargetBlock.realBlock);
/* 163:    */         
/* 164:207 */         fb.addSuccessor(branchTargetBlock);
/* 165:208 */         branchTargetBlock.addPredecessor(fb);
/* 166:    */       }
/* 167:    */     }
/* 168:212 */     Block[] result = new Block[theBlocks.size()];
/* 169:214 */     for (int i = 0; i < theBlocks.size(); i++)
/* 170:    */     {
/* 171:215 */       FatBlock fb = (FatBlock)theBlocks.get(i);
/* 172:216 */       Block b = fb.realBlock;
/* 173:217 */       b.itsSuccessors = fb.getSuccessors();
/* 174:218 */       b.itsPredecessors = fb.getPredecessors();
/* 175:219 */       b.itsBlockID = i;
/* 176:220 */       result[i] = b;
/* 177:    */     }
/* 178:223 */     return result;
/* 179:    */   }
/* 180:    */   
/* 181:    */   private static FatBlock newFatBlock(int startNodeIndex, int endNodeIndex)
/* 182:    */   {
/* 183:228 */     FatBlock fb = new FatBlock(null);
/* 184:229 */     fb.realBlock = new Block(startNodeIndex, endNodeIndex);
/* 185:230 */     return fb;
/* 186:    */   }
/* 187:    */   
/* 188:    */   private static String toString(Block[] blockList, Node[] statementNodes)
/* 189:    */   {
/* 190:235 */     return null;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private static void reachingDefDataFlow(OptFunctionNode fn, Node[] statementNodes, Block[] theBlocks, int[] varTypes)
/* 194:    */   {
/* 195:277 */     for (int i = 0; i < theBlocks.length; i++) {
/* 196:278 */       theBlocks[i].initLiveOnEntrySets(fn, statementNodes);
/* 197:    */     }
/* 198:285 */     boolean[] visit = new boolean[theBlocks.length];
/* 199:286 */     boolean[] doneOnce = new boolean[theBlocks.length];
/* 200:287 */     int vIndex = theBlocks.length - 1;
/* 201:288 */     boolean needRescan = false;
/* 202:289 */     visit[vIndex] = true;
/* 203:    */     for (;;)
/* 204:    */     {
/* 205:291 */       if ((visit[vIndex] != 0) || (doneOnce[vIndex] == 0))
/* 206:    */       {
/* 207:292 */         doneOnce[vIndex] = true;
/* 208:293 */         visit[vIndex] = false;
/* 209:294 */         if (theBlocks[vIndex].doReachedUseDataFlow())
/* 210:    */         {
/* 211:295 */           Block[] pred = theBlocks[vIndex].itsPredecessors;
/* 212:296 */           if (pred != null) {
/* 213:297 */             for (int i = 0; i < pred.length; i++)
/* 214:    */             {
/* 215:298 */               int index = pred[i].itsBlockID;
/* 216:299 */               visit[index] = true;
/* 217:300 */               needRescan |= index > vIndex;
/* 218:    */             }
/* 219:    */           }
/* 220:    */         }
/* 221:    */       }
/* 222:305 */       if (vIndex == 0)
/* 223:    */       {
/* 224:306 */         if (!needRescan) {
/* 225:    */           break;
/* 226:    */         }
/* 227:307 */         vIndex = theBlocks.length - 1;
/* 228:308 */         needRescan = false;
/* 229:    */       }
/* 230:    */       else
/* 231:    */       {
/* 232:314 */         vIndex--;
/* 233:    */       }
/* 234:    */     }
/* 235:322 */     theBlocks[0].markAnyTypeVariables(varTypes);
/* 236:    */   }
/* 237:    */   
/* 238:    */   private static void typeFlow(OptFunctionNode fn, Node[] statementNodes, Block[] theBlocks, int[] varTypes)
/* 239:    */   {
/* 240:327 */     boolean[] visit = new boolean[theBlocks.length];
/* 241:328 */     boolean[] doneOnce = new boolean[theBlocks.length];
/* 242:329 */     int vIndex = 0;
/* 243:330 */     boolean needRescan = false;
/* 244:331 */     visit[vIndex] = true;
/* 245:    */     for (;;)
/* 246:    */     {
/* 247:333 */       if ((visit[vIndex] != 0) || (doneOnce[vIndex] == 0))
/* 248:    */       {
/* 249:334 */         doneOnce[vIndex] = true;
/* 250:335 */         visit[vIndex] = false;
/* 251:336 */         if (theBlocks[vIndex].doTypeFlow(fn, statementNodes, varTypes))
/* 252:    */         {
/* 253:338 */           Block[] succ = theBlocks[vIndex].itsSuccessors;
/* 254:339 */           if (succ != null) {
/* 255:340 */             for (int i = 0; i < succ.length; i++)
/* 256:    */             {
/* 257:341 */               int index = succ[i].itsBlockID;
/* 258:342 */               visit[index] = true;
/* 259:343 */               needRescan |= index < vIndex;
/* 260:    */             }
/* 261:    */           }
/* 262:    */         }
/* 263:    */       }
/* 264:348 */       if (vIndex == theBlocks.length - 1)
/* 265:    */       {
/* 266:349 */         if (!needRescan) {
/* 267:    */           break;
/* 268:    */         }
/* 269:350 */         vIndex = 0;
/* 270:351 */         needRescan = false;
/* 271:    */       }
/* 272:    */       else
/* 273:    */       {
/* 274:357 */         vIndex++;
/* 275:    */       }
/* 276:    */     }
/* 277:    */   }
/* 278:    */   
/* 279:    */   private static boolean assignType(int[] varTypes, int index, int type)
/* 280:    */   {
/* 281:363 */     return type != (varTypes[index] |= type);
/* 282:    */   }
/* 283:    */   
/* 284:    */   private void markAnyTypeVariables(int[] varTypes)
/* 285:    */   {
/* 286:368 */     for (int i = 0; i != varTypes.length; i++) {
/* 287:369 */       if (this.itsLiveOnEntrySet.test(i)) {
/* 288:370 */         assignType(varTypes, i, 3);
/* 289:    */       }
/* 290:    */     }
/* 291:    */   }
/* 292:    */   
/* 293:    */   private void lookForVariableAccess(OptFunctionNode fn, Node n)
/* 294:    */   {
/* 295:386 */     switch (n.getType())
/* 296:    */     {
/* 297:    */     case 106: 
/* 298:    */     case 107: 
/* 299:390 */       Node child = n.getFirstChild();
/* 300:391 */       if (child.getType() == 55)
/* 301:    */       {
/* 302:392 */         int varIndex = fn.getVarIndex(child);
/* 303:393 */         if (!this.itsNotDefSet.test(varIndex)) {
/* 304:394 */           this.itsUseBeforeDefSet.set(varIndex);
/* 305:    */         }
/* 306:395 */         this.itsNotDefSet.set(varIndex);
/* 307:    */       }
/* 308:398 */       break;
/* 309:    */     case 56: 
/* 310:401 */       Node lhs = n.getFirstChild();
/* 311:402 */       Node rhs = lhs.getNext();
/* 312:403 */       lookForVariableAccess(fn, rhs);
/* 313:404 */       this.itsNotDefSet.set(fn.getVarIndex(n));
/* 314:    */       
/* 315:406 */       break;
/* 316:    */     case 55: 
/* 317:409 */       int varIndex = fn.getVarIndex(n);
/* 318:410 */       if (!this.itsNotDefSet.test(varIndex)) {
/* 319:411 */         this.itsUseBeforeDefSet.set(varIndex);
/* 320:    */       }
/* 321:413 */       break;
/* 322:    */     default: 
/* 323:415 */       Node child = n.getFirstChild();
/* 324:416 */       while (child != null)
/* 325:    */       {
/* 326:417 */         lookForVariableAccess(fn, child);
/* 327:418 */         child = child.getNext();
/* 328:    */       }
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   private void initLiveOnEntrySets(OptFunctionNode fn, Node[] statementNodes)
/* 333:    */   {
/* 334:431 */     int listLength = fn.getVarCount();
/* 335:432 */     this.itsUseBeforeDefSet = new DataFlowBitSet(listLength);
/* 336:433 */     this.itsNotDefSet = new DataFlowBitSet(listLength);
/* 337:434 */     this.itsLiveOnEntrySet = new DataFlowBitSet(listLength);
/* 338:435 */     this.itsLiveOnExitSet = new DataFlowBitSet(listLength);
/* 339:436 */     for (int i = this.itsStartNodeIndex; i <= this.itsEndNodeIndex; i++)
/* 340:    */     {
/* 341:437 */       Node n = statementNodes[i];
/* 342:438 */       lookForVariableAccess(fn, n);
/* 343:    */     }
/* 344:440 */     this.itsNotDefSet.not();
/* 345:    */   }
/* 346:    */   
/* 347:    */   private boolean doReachedUseDataFlow()
/* 348:    */   {
/* 349:451 */     this.itsLiveOnExitSet.clear();
/* 350:452 */     if (this.itsSuccessors != null) {
/* 351:453 */       for (int i = 0; i < this.itsSuccessors.length; i++) {
/* 352:454 */         this.itsLiveOnExitSet.or(this.itsSuccessors[i].itsLiveOnEntrySet);
/* 353:    */       }
/* 354:    */     }
/* 355:455 */     return this.itsLiveOnEntrySet.df2(this.itsLiveOnExitSet, this.itsUseBeforeDefSet, this.itsNotDefSet);
/* 356:    */   }
/* 357:    */   
/* 358:    */   private static int findExpressionType(OptFunctionNode fn, Node n, int[] varTypes)
/* 359:    */   {
/* 360:468 */     switch (n.getType())
/* 361:    */     {
/* 362:    */     case 40: 
/* 363:470 */       return 1;
/* 364:    */     case 30: 
/* 365:    */     case 38: 
/* 366:    */     case 70: 
/* 367:475 */       return 3;
/* 368:    */     case 36: 
/* 369:478 */       return 3;
/* 370:    */     case 55: 
/* 371:481 */       return varTypes[fn.getVarIndex(n)];
/* 372:    */     case 9: 
/* 373:    */     case 10: 
/* 374:    */     case 11: 
/* 375:    */     case 18: 
/* 376:    */     case 19: 
/* 377:    */     case 20: 
/* 378:    */     case 22: 
/* 379:    */     case 23: 
/* 380:    */     case 24: 
/* 381:    */     case 25: 
/* 382:    */     case 28: 
/* 383:    */     case 29: 
/* 384:    */     case 106: 
/* 385:    */     case 107: 
/* 386:497 */       return 1;
/* 387:    */     case 65: 
/* 388:    */     case 66: 
/* 389:501 */       return 3;
/* 390:    */     case 21: 
/* 391:507 */       Node child = n.getFirstChild();
/* 392:508 */       int lType = findExpressionType(fn, child, varTypes);
/* 393:509 */       int rType = findExpressionType(fn, child.getNext(), varTypes);
/* 394:510 */       return lType | rType;
/* 395:    */     }
/* 396:514 */     Node child = n.getFirstChild();
/* 397:515 */     if (child == null) {
/* 398:516 */       return 3;
/* 399:    */     }
/* 400:518 */     int result = 0;
/* 401:519 */     while (child != null)
/* 402:    */     {
/* 403:520 */       result |= findExpressionType(fn, child, varTypes);
/* 404:521 */       child = child.getNext();
/* 405:    */     }
/* 406:523 */     return result;
/* 407:    */   }
/* 408:    */   
/* 409:    */   private static boolean findDefPoints(OptFunctionNode fn, Node n, int[] varTypes)
/* 410:    */   {
/* 411:530 */     boolean result = false;
/* 412:531 */     Node child = n.getFirstChild();
/* 413:532 */     switch (n.getType())
/* 414:    */     {
/* 415:    */     }
/* 416:534 */     while (child != null)
/* 417:    */     {
/* 418:535 */       result |= findDefPoints(fn, child, varTypes);
/* 419:536 */       child = child.getNext(); continue;
/* 420:541 */       if (child.getType() == 55)
/* 421:    */       {
/* 422:543 */         int i = fn.getVarIndex(child);
/* 423:544 */         result |= assignType(varTypes, i, 1);
/* 424:545 */         break;
/* 425:549 */         if (child.getType() == 55)
/* 426:    */         {
/* 427:550 */           int i = fn.getVarIndex(child);
/* 428:551 */           assignType(varTypes, i, 3);
/* 429:    */         }
/* 430:553 */         while (child != null)
/* 431:    */         {
/* 432:554 */           result |= findDefPoints(fn, child, varTypes);
/* 433:555 */           child = child.getNext(); continue;
/* 434:    */           
/* 435:    */ 
/* 436:    */ 
/* 437:559 */           Node rValue = child.getNext();
/* 438:560 */           int theType = findExpressionType(fn, rValue, varTypes);
/* 439:561 */           int i = fn.getVarIndex(n);
/* 440:562 */           result |= assignType(varTypes, i, theType);
/* 441:    */         }
/* 442:    */       }
/* 443:    */     }
/* 444:566 */     return result;
/* 445:    */   }
/* 446:    */   
/* 447:    */   private boolean doTypeFlow(OptFunctionNode fn, Node[] statementNodes, int[] varTypes)
/* 448:    */   {
/* 449:572 */     boolean changed = false;
/* 450:574 */     for (int i = this.itsStartNodeIndex; i <= this.itsEndNodeIndex; i++)
/* 451:    */     {
/* 452:575 */       Node n = statementNodes[i];
/* 453:576 */       if (n != null) {
/* 454:577 */         changed |= findDefPoints(fn, n, varTypes);
/* 455:    */       }
/* 456:    */     }
/* 457:580 */     return changed;
/* 458:    */   }
/* 459:    */   
/* 460:    */   private void printLiveOnEntrySet(OptFunctionNode fn) {}
/* 461:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.Block
 * JD-Core Version:    0.7.0.1
 */