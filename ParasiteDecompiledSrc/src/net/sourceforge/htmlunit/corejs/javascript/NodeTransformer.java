/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.AstRoot;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.Jump;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.Scope;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*  11:    */ 
/*  12:    */ public class NodeTransformer
/*  13:    */ {
/*  14:    */   private ObjArray loops;
/*  15:    */   private ObjArray loopEnds;
/*  16:    */   private boolean hasFinally;
/*  17:    */   
/*  18:    */   public final void transform(ScriptNode tree)
/*  19:    */   {
/*  20: 70 */     transformCompilationUnit(tree);
/*  21: 71 */     for (int i = 0; i != tree.getFunctionCount(); i++)
/*  22:    */     {
/*  23: 72 */       FunctionNode fn = tree.getFunctionNode(i);
/*  24: 73 */       transform(fn);
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   private void transformCompilationUnit(ScriptNode tree)
/*  29:    */   {
/*  30: 79 */     this.loops = new ObjArray();
/*  31: 80 */     this.loopEnds = new ObjArray();
/*  32:    */     
/*  33:    */ 
/*  34: 83 */     this.hasFinally = false;
/*  35:    */     
/*  36:    */ 
/*  37: 86 */     boolean createScopeObjects = (tree.getType() != 109) || (((FunctionNode)tree).requiresActivation());
/*  38:    */     
/*  39: 88 */     tree.flattenSymbolTable(!createScopeObjects);
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43: 92 */     boolean inStrictMode = ((tree instanceof AstRoot)) && (((AstRoot)tree).isInStrictMode());
/*  44:    */     
/*  45: 94 */     transformCompilationUnit_r(tree, tree, tree, createScopeObjects, inStrictMode);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void transformCompilationUnit_r(ScriptNode tree, Node parent, Scope scope, boolean createScopeObjects, boolean inStrictMode)
/*  49:    */   {
/*  50:104 */     Node node = null;
/*  51:    */     for (;;)
/*  52:    */     {
/*  53:107 */       Node previous = null;
/*  54:108 */       if (node == null)
/*  55:    */       {
/*  56:109 */         node = parent.getFirstChild();
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60:111 */         previous = node;
/*  61:112 */         node = node.getNext();
/*  62:    */       }
/*  63:114 */       if (node == null) {
/*  64:    */         break;
/*  65:    */       }
/*  66:118 */       int type = node.getType();
/*  67:119 */       if ((createScopeObjects) && ((type == 129) || (type == 132) || (type == 157)) && ((node instanceof Scope)))
/*  68:    */       {
/*  69:124 */         Scope newScope = (Scope)node;
/*  70:125 */         if (newScope.getSymbolTable() != null)
/*  71:    */         {
/*  72:128 */           Node let = new Node(type == 157 ? 158 : 153);
/*  73:    */           
/*  74:130 */           Node innerLet = new Node(153);
/*  75:131 */           let.addChildToBack(innerLet);
/*  76:132 */           for (String name : newScope.getSymbolTable().keySet()) {
/*  77:133 */             innerLet.addChildToBack(Node.newString(39, name));
/*  78:    */           }
/*  79:135 */           newScope.setSymbolTable(null);
/*  80:136 */           Node oldNode = node;
/*  81:137 */           node = replaceCurrent(parent, previous, node, let);
/*  82:138 */           type = node.getType();
/*  83:139 */           let.addChildToBack(oldNode);
/*  84:    */         }
/*  85:    */       }
/*  86:143 */       switch (type)
/*  87:    */       {
/*  88:    */       case 114: 
/*  89:    */       case 130: 
/*  90:    */       case 132: 
/*  91:148 */         this.loops.push(node);
/*  92:149 */         this.loopEnds.push(((Jump)node).target);
/*  93:150 */         break;
/*  94:    */       case 123: 
/*  95:154 */         this.loops.push(node);
/*  96:155 */         Node leave = node.getNext();
/*  97:156 */         if (leave.getType() != 3) {
/*  98:157 */           Kit.codeBug();
/*  99:    */         }
/* 100:159 */         this.loopEnds.push(leave);
/* 101:160 */         break;
/* 102:    */       case 81: 
/* 103:165 */         Jump jump = (Jump)node;
/* 104:166 */         Node finallytarget = jump.getFinally();
/* 105:167 */         if (finallytarget != null)
/* 106:    */         {
/* 107:168 */           this.hasFinally = true;
/* 108:169 */           this.loops.push(node);
/* 109:170 */           this.loopEnds.push(finallytarget);
/* 110:    */         }
/* 111:    */         break;
/* 112:    */       case 3: 
/* 113:    */       case 131: 
/* 114:177 */         if ((!this.loopEnds.isEmpty()) && (this.loopEnds.peek() == node))
/* 115:    */         {
/* 116:178 */           this.loopEnds.pop();
/* 117:179 */           this.loops.pop();
/* 118:    */         }
/* 119:    */         break;
/* 120:    */       case 72: 
/* 121:184 */         ((FunctionNode)tree).addResumptionPoint(node);
/* 122:185 */         break;
/* 123:    */       case 4: 
/* 124:189 */         boolean isGenerator = (tree.getType() == 109) && (((FunctionNode)tree).isGenerator());
/* 125:191 */         if (isGenerator) {
/* 126:192 */           node.putIntProp(20, 1);
/* 127:    */         }
/* 128:201 */         if (this.hasFinally)
/* 129:    */         {
/* 130:203 */           Node unwindBlock = null;
/* 131:204 */           for (int i = this.loops.size() - 1; i >= 0; i--)
/* 132:    */           {
/* 133:205 */             Node n = (Node)this.loops.get(i);
/* 134:206 */             int elemtype = n.getType();
/* 135:207 */             if ((elemtype == 81) || (elemtype == 123))
/* 136:    */             {
/* 137:    */               Node unwind;
/* 138:    */               Node unwind;
/* 139:209 */               if (elemtype == 81)
/* 140:    */               {
/* 141:210 */                 Jump jsrnode = new Jump(135);
/* 142:211 */                 Node jsrtarget = ((Jump)n).getFinally();
/* 143:212 */                 jsrnode.target = jsrtarget;
/* 144:213 */                 unwind = jsrnode;
/* 145:    */               }
/* 146:    */               else
/* 147:    */               {
/* 148:215 */                 unwind = new Node(3);
/* 149:    */               }
/* 150:217 */               if (unwindBlock == null) {
/* 151:218 */                 unwindBlock = new Node(129, node.getLineno());
/* 152:    */               }
/* 153:221 */               unwindBlock.addChildToBack(unwind);
/* 154:    */             }
/* 155:    */           }
/* 156:224 */           if (unwindBlock != null)
/* 157:    */           {
/* 158:225 */             Node returnNode = node;
/* 159:226 */             Node returnExpr = returnNode.getFirstChild();
/* 160:227 */             node = replaceCurrent(parent, previous, node, unwindBlock);
/* 161:228 */             if ((returnExpr == null) || (isGenerator))
/* 162:    */             {
/* 163:229 */               unwindBlock.addChildToBack(returnNode); continue;
/* 164:    */             }
/* 165:231 */             Node store = new Node(134, returnExpr);
/* 166:232 */             unwindBlock.addChildToFront(store);
/* 167:233 */             returnNode = new Node(64);
/* 168:234 */             unwindBlock.addChildToBack(returnNode);
/* 169:    */             
/* 170:236 */             transformCompilationUnit_r(tree, store, scope, createScopeObjects, inStrictMode);
/* 171:    */           }
/* 172:    */         }
/* 173:241 */         break;
/* 174:    */       case 120: 
/* 175:    */       case 121: 
/* 176:249 */         Jump jump = (Jump)node;
/* 177:250 */         Jump jumpStatement = jump.getJumpStatement();
/* 178:251 */         if (jumpStatement == null) {
/* 179:251 */           Kit.codeBug();
/* 180:    */         }
/* 181:253 */         int i = this.loops.size();
/* 182:    */         for (;;)
/* 183:    */         {
/* 184:254 */           if (i == 0) {
/* 185:258 */             throw Kit.codeBug();
/* 186:    */           }
/* 187:260 */           i--;
/* 188:261 */           Node n = (Node)this.loops.get(i);
/* 189:262 */           if (n == jumpStatement) {
/* 190:    */             break;
/* 191:    */           }
/* 192:266 */           int elemtype = n.getType();
/* 193:267 */           if (elemtype == 123)
/* 194:    */           {
/* 195:268 */             Node leave = new Node(3);
/* 196:269 */             previous = addBeforeCurrent(parent, previous, node, leave);
/* 197:    */           }
/* 198:271 */           else if (elemtype == 81)
/* 199:    */           {
/* 200:272 */             Jump tryNode = (Jump)n;
/* 201:273 */             Jump jsrFinally = new Jump(135);
/* 202:274 */             jsrFinally.target = tryNode.getFinally();
/* 203:275 */             previous = addBeforeCurrent(parent, previous, node, jsrFinally);
/* 204:    */           }
/* 205:    */         }
/* 206:280 */         if (type == 120) {
/* 207:281 */           jump.target = jumpStatement.target;
/* 208:    */         } else {
/* 209:283 */           jump.target = jumpStatement.getContinue();
/* 210:    */         }
/* 211:285 */         jump.setType(5);
/* 212:    */         
/* 213:287 */         break;
/* 214:    */       case 38: 
/* 215:291 */         visitCall(node, tree);
/* 216:292 */         break;
/* 217:    */       case 30: 
/* 218:295 */         visitNew(node, tree);
/* 219:296 */         break;
/* 220:    */       case 153: 
/* 221:    */       case 158: 
/* 222:300 */         Node child = node.getFirstChild();
/* 223:301 */         if (child.getType() == 153)
/* 224:    */         {
/* 225:304 */           boolean createWith = (tree.getType() != 109) || (((FunctionNode)tree).requiresActivation());
/* 226:    */           
/* 227:306 */           node = visitLet(createWith, parent, previous, node);
/* 228:    */         }
/* 229:307 */         break;
/* 230:    */       case 122: 
/* 231:    */       case 154: 
/* 232:316 */         Node result = new Node(129);
/* 233:317 */         for (Node cursor = node.getFirstChild(); cursor != null;)
/* 234:    */         {
/* 235:320 */           Node n = cursor;
/* 236:321 */           cursor = cursor.getNext();
/* 237:322 */           if (n.getType() == 39)
/* 238:    */           {
/* 239:323 */             if (!n.hasChildren()) {
/* 240:    */               continue;
/* 241:    */             }
/* 242:325 */             Node init = n.getFirstChild();
/* 243:326 */             n.removeChild(init);
/* 244:327 */             n.setType(49);
/* 245:328 */             n = new Node(type == 154 ? 155 : 8, n, init);
/* 246:    */           }
/* 247:335 */           else if (n.getType() != 158)
/* 248:    */           {
/* 249:336 */             throw Kit.codeBug();
/* 250:    */           }
/* 251:338 */           Node pop = new Node(133, n, node.getLineno());
/* 252:339 */           result.addChildToBack(pop);
/* 253:    */         }
/* 254:341 */         node = replaceCurrent(parent, previous, node, result);
/* 255:342 */         break;
/* 256:    */       case 137: 
/* 257:346 */         Scope defining = scope.getDefiningScope(node.getString());
/* 258:347 */         if (defining != null) {
/* 259:348 */           node.setScope(defining);
/* 260:    */         }
/* 261:351 */         break;
/* 262:    */       case 7: 
/* 263:    */       case 32: 
/* 264:359 */         Node child = node.getFirstChild();
/* 265:360 */         if (type == 7)
/* 266:    */         {
/* 267:361 */           while (child.getType() == 26) {
/* 268:362 */             child = child.getFirstChild();
/* 269:    */           }
/* 270:364 */           if ((child.getType() == 12) || (child.getType() == 13))
/* 271:    */           {
/* 272:367 */             Node first = child.getFirstChild();
/* 273:368 */             Node last = child.getLastChild();
/* 274:369 */             if ((first.getType() == 39) && (first.getString().equals("undefined"))) {
/* 275:371 */               child = last;
/* 276:372 */             } else if ((last.getType() == 39) && (last.getString().equals("undefined"))) {
/* 277:374 */               child = first;
/* 278:    */             }
/* 279:    */           }
/* 280:    */         }
/* 281:377 */         if (child.getType() == 33) {
/* 282:378 */           child.setType(34);
/* 283:    */         }
/* 284:    */         break;
/* 285:    */       case 8: 
/* 286:383 */         if (inStrictMode) {
/* 287:384 */           node.setType(73);
/* 288:    */         }
/* 289:    */       case 31: 
/* 290:    */       case 39: 
/* 291:    */       case 155: 
/* 292:392 */         if (!createScopeObjects)
/* 293:    */         {
/* 294:    */           Node nameSource;
/* 295:    */           Node nameSource;
/* 296:396 */           if (type == 39)
/* 297:    */           {
/* 298:397 */             nameSource = node;
/* 299:    */           }
/* 300:    */           else
/* 301:    */           {
/* 302:399 */             nameSource = node.getFirstChild();
/* 303:400 */             if (nameSource.getType() != 49)
/* 304:    */             {
/* 305:401 */               if (type == 31) {
/* 306:    */                 break label1734;
/* 307:    */               }
/* 308:404 */               throw Kit.codeBug();
/* 309:    */             }
/* 310:    */           }
/* 311:407 */           if (nameSource.getScope() == null)
/* 312:    */           {
/* 313:410 */             String name = nameSource.getString();
/* 314:411 */             Scope defining = scope.getDefiningScope(name);
/* 315:412 */             if (defining != null)
/* 316:    */             {
/* 317:413 */               nameSource.setScope(defining);
/* 318:414 */               if (type == 39)
/* 319:    */               {
/* 320:415 */                 node.setType(55);
/* 321:    */               }
/* 322:416 */               else if ((type == 8) || (type == 73))
/* 323:    */               {
/* 324:418 */                 node.setType(56);
/* 325:419 */                 nameSource.setType(41);
/* 326:    */               }
/* 327:420 */               else if (type == 155)
/* 328:    */               {
/* 329:421 */                 node.setType(156);
/* 330:422 */                 nameSource.setType(41);
/* 331:    */               }
/* 332:423 */               else if (type == 31)
/* 333:    */               {
/* 334:425 */                 Node n = new Node(44);
/* 335:426 */                 node = replaceCurrent(parent, previous, node, n);
/* 336:    */               }
/* 337:    */               else
/* 338:    */               {
/* 339:428 */                 throw Kit.codeBug();
/* 340:    */               }
/* 341:    */             }
/* 342:    */           }
/* 343:    */         }
/* 344:    */       default: 
/* 345:    */         label1734:
/* 346:435 */         transformCompilationUnit_r(tree, node, (node instanceof Scope) ? (Scope)node : scope, createScopeObjects, inStrictMode);
/* 347:    */       }
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   protected void visitNew(Node node, ScriptNode tree) {}
/* 352:    */   
/* 353:    */   protected void visitCall(Node node, ScriptNode tree) {}
/* 354:    */   
/* 355:    */   protected Node visitLet(boolean createWith, Node parent, Node previous, Node scopeNode)
/* 356:    */   {
/* 357:450 */     Node vars = scopeNode.getFirstChild();
/* 358:451 */     Node body = vars.getNext();
/* 359:452 */     scopeNode.removeChild(vars);
/* 360:453 */     scopeNode.removeChild(body);
/* 361:454 */     boolean isExpression = scopeNode.getType() == 158;
/* 362:    */     Node result;
/* 363:457 */     if (createWith)
/* 364:    */     {
/* 365:458 */       Node result = new Node(isExpression ? 159 : 129);
/* 366:459 */       result = replaceCurrent(parent, previous, scopeNode, result);
/* 367:460 */       ArrayList<Object> list = new ArrayList();
/* 368:461 */       Node objectLiteral = new Node(66);
/* 369:462 */       for (Node v = vars.getFirstChild(); v != null; v = v.getNext())
/* 370:    */       {
/* 371:463 */         Node current = v;
/* 372:464 */         if (current.getType() == 158)
/* 373:    */         {
/* 374:466 */           List<?> destructuringNames = (List)current.getProp(22);
/* 375:    */           
/* 376:468 */           Node c = current.getFirstChild();
/* 377:469 */           if (c.getType() != 153) {
/* 378:469 */             throw Kit.codeBug();
/* 379:    */           }
/* 380:471 */           if (isExpression) {
/* 381:472 */             body = new Node(89, c.getNext(), body);
/* 382:    */           } else {
/* 383:474 */             body = new Node(129, new Node(133, c.getNext()), body);
/* 384:    */           }
/* 385:480 */           if (destructuringNames != null)
/* 386:    */           {
/* 387:481 */             list.addAll(destructuringNames);
/* 388:482 */             for (int i = 0; i < destructuringNames.size(); i++) {
/* 389:483 */               objectLiteral.addChildToBack(new Node(126, Node.newNumber(0.0D)));
/* 390:    */             }
/* 391:    */           }
/* 392:487 */           current = c.getFirstChild();
/* 393:    */         }
/* 394:489 */         if (current.getType() != 39) {
/* 395:489 */           throw Kit.codeBug();
/* 396:    */         }
/* 397:490 */         list.add(ScriptRuntime.getIndexObject(current.getString()));
/* 398:491 */         Node init = current.getFirstChild();
/* 399:492 */         if (init == null) {
/* 400:493 */           init = new Node(126, Node.newNumber(0.0D));
/* 401:    */         }
/* 402:495 */         objectLiteral.addChildToBack(init);
/* 403:    */       }
/* 404:497 */       objectLiteral.putProp(12, list.toArray());
/* 405:498 */       Node newVars = new Node(2, objectLiteral);
/* 406:499 */       result.addChildToBack(newVars);
/* 407:500 */       result.addChildToBack(new Node(123, body));
/* 408:501 */       result.addChildToBack(new Node(3));
/* 409:    */     }
/* 410:    */     else
/* 411:    */     {
/* 412:503 */       result = new Node(isExpression ? 89 : 129);
/* 413:504 */       result = replaceCurrent(parent, previous, scopeNode, result);
/* 414:505 */       Node newVars = new Node(89);
/* 415:506 */       for (Node v = vars.getFirstChild(); v != null; v = v.getNext())
/* 416:    */       {
/* 417:507 */         Node current = v;
/* 418:508 */         if (current.getType() == 158)
/* 419:    */         {
/* 420:510 */           Node c = current.getFirstChild();
/* 421:511 */           if (c.getType() != 153) {
/* 422:511 */             throw Kit.codeBug();
/* 423:    */           }
/* 424:513 */           if (isExpression) {
/* 425:514 */             body = new Node(89, c.getNext(), body);
/* 426:    */           } else {
/* 427:516 */             body = new Node(129, new Node(133, c.getNext()), body);
/* 428:    */           }
/* 429:521 */           Scope.joinScopes((Scope)current, (Scope)scopeNode);
/* 430:    */           
/* 431:523 */           current = c.getFirstChild();
/* 432:    */         }
/* 433:525 */         if (current.getType() != 39) {
/* 434:525 */           throw Kit.codeBug();
/* 435:    */         }
/* 436:526 */         Node stringNode = Node.newString(current.getString());
/* 437:527 */         stringNode.setScope((Scope)scopeNode);
/* 438:528 */         Node init = current.getFirstChild();
/* 439:529 */         if (init == null) {
/* 440:530 */           init = new Node(126, Node.newNumber(0.0D));
/* 441:    */         }
/* 442:532 */         newVars.addChildToBack(new Node(56, stringNode, init));
/* 443:    */       }
/* 444:534 */       if (isExpression)
/* 445:    */       {
/* 446:535 */         result.addChildToBack(newVars);
/* 447:536 */         scopeNode.setType(89);
/* 448:537 */         result.addChildToBack(scopeNode);
/* 449:538 */         scopeNode.addChildToBack(body);
/* 450:539 */         if ((body instanceof Scope))
/* 451:    */         {
/* 452:540 */           Scope scopeParent = ((Scope)body).getParentScope();
/* 453:541 */           ((Scope)body).setParentScope((Scope)scopeNode);
/* 454:542 */           ((Scope)scopeNode).setParentScope(scopeParent);
/* 455:    */         }
/* 456:    */       }
/* 457:    */       else
/* 458:    */       {
/* 459:545 */         result.addChildToBack(new Node(133, newVars));
/* 460:546 */         scopeNode.setType(129);
/* 461:547 */         result.addChildToBack(scopeNode);
/* 462:548 */         scopeNode.addChildrenToBack(body);
/* 463:549 */         if ((body instanceof Scope))
/* 464:    */         {
/* 465:550 */           Scope scopeParent = ((Scope)body).getParentScope();
/* 466:551 */           ((Scope)body).setParentScope((Scope)scopeNode);
/* 467:552 */           ((Scope)scopeNode).setParentScope(scopeParent);
/* 468:    */         }
/* 469:    */       }
/* 470:    */     }
/* 471:556 */     return result;
/* 472:    */   }
/* 473:    */   
/* 474:    */   private static Node addBeforeCurrent(Node parent, Node previous, Node current, Node toAdd)
/* 475:    */   {
/* 476:562 */     if (previous == null)
/* 477:    */     {
/* 478:563 */       if (current != parent.getFirstChild()) {
/* 479:563 */         Kit.codeBug();
/* 480:    */       }
/* 481:564 */       parent.addChildToFront(toAdd);
/* 482:    */     }
/* 483:    */     else
/* 484:    */     {
/* 485:566 */       if (current != previous.getNext()) {
/* 486:566 */         Kit.codeBug();
/* 487:    */       }
/* 488:567 */       parent.addChildAfter(toAdd, previous);
/* 489:    */     }
/* 490:569 */     return toAdd;
/* 491:    */   }
/* 492:    */   
/* 493:    */   private static Node replaceCurrent(Node parent, Node previous, Node current, Node replacement)
/* 494:    */   {
/* 495:575 */     if (previous == null)
/* 496:    */     {
/* 497:576 */       if (current != parent.getFirstChild()) {
/* 498:576 */         Kit.codeBug();
/* 499:    */       }
/* 500:577 */       parent.replaceChild(current, replacement);
/* 501:    */     }
/* 502:578 */     else if (previous.next == current)
/* 503:    */     {
/* 504:581 */       parent.replaceChildAfter(previous, replacement);
/* 505:    */     }
/* 506:    */     else
/* 507:    */     {
/* 508:583 */       parent.replaceChild(current, replacement);
/* 509:    */     }
/* 510:585 */     return replacement;
/* 511:    */   }
/* 512:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NodeTransformer
 * JD-Core Version:    0.7.0.1
 */