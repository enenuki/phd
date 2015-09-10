/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.ObjArray;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*   7:    */ 
/*   8:    */ class Optimizer
/*   9:    */ {
/*  10:    */   static final int NoType = 0;
/*  11:    */   static final int NumberType = 1;
/*  12:    */   static final int AnyType = 3;
/*  13:    */   private boolean inDirectCallFunction;
/*  14:    */   OptFunctionNode theFunction;
/*  15:    */   private boolean parameterUsedInNumberContext;
/*  16:    */   
/*  17:    */   void optimize(ScriptNode scriptOrFn)
/*  18:    */   {
/*  19: 57 */     int functionCount = scriptOrFn.getFunctionCount();
/*  20: 58 */     for (int i = 0; i != functionCount; i++)
/*  21:    */     {
/*  22: 59 */       OptFunctionNode f = OptFunctionNode.get(scriptOrFn, i);
/*  23: 60 */       optimizeFunction(f);
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   private void optimizeFunction(OptFunctionNode theFunction)
/*  28:    */   {
/*  29: 66 */     if (theFunction.fnode.requiresActivation()) {
/*  30: 66 */       return;
/*  31:    */     }
/*  32: 68 */     this.inDirectCallFunction = theFunction.isTargetOfDirectCall();
/*  33: 69 */     this.theFunction = theFunction;
/*  34:    */     
/*  35: 71 */     ObjArray statementsArray = new ObjArray();
/*  36: 72 */     buildStatementList_r(theFunction.fnode, statementsArray);
/*  37: 73 */     Node[] theStatementNodes = new Node[statementsArray.size()];
/*  38: 74 */     statementsArray.toArray(theStatementNodes);
/*  39:    */     
/*  40: 76 */     Block.runFlowAnalyzes(theFunction, theStatementNodes);
/*  41: 78 */     if (!theFunction.fnode.requiresActivation())
/*  42:    */     {
/*  43: 86 */       this.parameterUsedInNumberContext = false;
/*  44: 87 */       for (int i = 0; i < theStatementNodes.length; i++) {
/*  45: 88 */         rewriteForNumberVariables(theStatementNodes[i], 1);
/*  46:    */       }
/*  47: 90 */       theFunction.setParameterNumberContext(this.parameterUsedInNumberContext);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   private void markDCPNumberContext(Node n)
/*  52:    */   {
/*  53:129 */     if ((this.inDirectCallFunction) && (n.getType() == 55))
/*  54:    */     {
/*  55:130 */       int varIndex = this.theFunction.getVarIndex(n);
/*  56:131 */       if (this.theFunction.isParameter(varIndex)) {
/*  57:132 */         this.parameterUsedInNumberContext = true;
/*  58:    */       }
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private boolean convertParameter(Node n)
/*  63:    */   {
/*  64:139 */     if ((this.inDirectCallFunction) && (n.getType() == 55))
/*  65:    */     {
/*  66:140 */       int varIndex = this.theFunction.getVarIndex(n);
/*  67:141 */       if (this.theFunction.isParameter(varIndex))
/*  68:    */       {
/*  69:142 */         n.removeProp(8);
/*  70:143 */         return true;
/*  71:    */       }
/*  72:    */     }
/*  73:146 */     return false;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private int rewriteForNumberVariables(Node n, int desired)
/*  77:    */   {
/*  78:151 */     switch (n.getType())
/*  79:    */     {
/*  80:    */     case 133: 
/*  81:153 */       Node child = n.getFirstChild();
/*  82:154 */       int type = rewriteForNumberVariables(child, 1);
/*  83:155 */       if (type == 1) {
/*  84:156 */         n.putIntProp(8, 0);
/*  85:    */       }
/*  86:157 */       return 0;
/*  87:    */     case 40: 
/*  88:160 */       n.putIntProp(8, 0);
/*  89:161 */       return 1;
/*  90:    */     case 55: 
/*  91:165 */       int varIndex = this.theFunction.getVarIndex(n);
/*  92:166 */       if ((this.inDirectCallFunction) && (this.theFunction.isParameter(varIndex)) && (desired == 1))
/*  93:    */       {
/*  94:170 */         n.putIntProp(8, 0);
/*  95:171 */         return 1;
/*  96:    */       }
/*  97:173 */       if (this.theFunction.isNumberVar(varIndex))
/*  98:    */       {
/*  99:174 */         n.putIntProp(8, 0);
/* 100:175 */         return 1;
/* 101:    */       }
/* 102:177 */       return 0;
/* 103:    */     case 106: 
/* 104:    */     case 107: 
/* 105:182 */       Node child = n.getFirstChild();
/* 106:184 */       if (child.getType() == 55)
/* 107:    */       {
/* 108:185 */         if ((rewriteForNumberVariables(child, 1) == 1) && (!convertParameter(child)))
/* 109:    */         {
/* 110:188 */           n.putIntProp(8, 0);
/* 111:189 */           markDCPNumberContext(child);
/* 112:190 */           return 1;
/* 113:    */         }
/* 114:192 */         return 0;
/* 115:    */       }
/* 116:194 */       if (child.getType() == 36) {
/* 117:195 */         return rewriteForNumberVariables(child, 1);
/* 118:    */       }
/* 119:197 */       return 0;
/* 120:    */     case 56: 
/* 121:200 */       Node lChild = n.getFirstChild();
/* 122:201 */       Node rChild = lChild.getNext();
/* 123:202 */       int rType = rewriteForNumberVariables(rChild, 1);
/* 124:203 */       int varIndex = this.theFunction.getVarIndex(n);
/* 125:204 */       if ((this.inDirectCallFunction) && (this.theFunction.isParameter(varIndex)))
/* 126:    */       {
/* 127:207 */         if (rType == 1)
/* 128:    */         {
/* 129:208 */           if (!convertParameter(rChild))
/* 130:    */           {
/* 131:209 */             n.putIntProp(8, 0);
/* 132:210 */             return 1;
/* 133:    */           }
/* 134:212 */           markDCPNumberContext(rChild);
/* 135:213 */           return 0;
/* 136:    */         }
/* 137:216 */         return rType;
/* 138:    */       }
/* 139:218 */       if (this.theFunction.isNumberVar(varIndex))
/* 140:    */       {
/* 141:219 */         if (rType != 1)
/* 142:    */         {
/* 143:220 */           n.removeChild(rChild);
/* 144:221 */           n.addChildToBack(new Node(150, rChild));
/* 145:    */         }
/* 146:224 */         n.putIntProp(8, 0);
/* 147:225 */         markDCPNumberContext(rChild);
/* 148:226 */         return 1;
/* 149:    */       }
/* 150:229 */       if ((rType == 1) && 
/* 151:230 */         (!convertParameter(rChild)))
/* 152:    */       {
/* 153:231 */         n.removeChild(rChild);
/* 154:232 */         n.addChildToBack(new Node(149, rChild));
/* 155:    */       }
/* 156:236 */       return 0;
/* 157:    */     case 14: 
/* 158:    */     case 15: 
/* 159:    */     case 16: 
/* 160:    */     case 17: 
/* 161:243 */       Node lChild = n.getFirstChild();
/* 162:244 */       Node rChild = lChild.getNext();
/* 163:245 */       int lType = rewriteForNumberVariables(lChild, 1);
/* 164:246 */       int rType = rewriteForNumberVariables(rChild, 1);
/* 165:247 */       markDCPNumberContext(lChild);
/* 166:248 */       markDCPNumberContext(rChild);
/* 167:250 */       if (convertParameter(lChild))
/* 168:    */       {
/* 169:251 */         if (convertParameter(rChild)) {
/* 170:252 */           return 0;
/* 171:    */         }
/* 172:253 */         if (rType == 1) {
/* 173:254 */           n.putIntProp(8, 2);
/* 174:    */         }
/* 175:    */       }
/* 176:257 */       else if (convertParameter(rChild))
/* 177:    */       {
/* 178:258 */         if (lType == 1) {
/* 179:259 */           n.putIntProp(8, 1);
/* 180:    */         }
/* 181:    */       }
/* 182:263 */       else if (lType == 1)
/* 183:    */       {
/* 184:264 */         if (rType == 1) {
/* 185:265 */           n.putIntProp(8, 0);
/* 186:    */         } else {
/* 187:268 */           n.putIntProp(8, 1);
/* 188:    */         }
/* 189:    */       }
/* 190:272 */       else if (rType == 1)
/* 191:    */       {
/* 192:273 */         n.putIntProp(8, 2);
/* 193:    */       }
/* 194:278 */       return 0;
/* 195:    */     case 21: 
/* 196:282 */       Node lChild = n.getFirstChild();
/* 197:283 */       Node rChild = lChild.getNext();
/* 198:284 */       int lType = rewriteForNumberVariables(lChild, 1);
/* 199:285 */       int rType = rewriteForNumberVariables(rChild, 1);
/* 200:288 */       if (convertParameter(lChild))
/* 201:    */       {
/* 202:289 */         if (convertParameter(rChild)) {
/* 203:290 */           return 0;
/* 204:    */         }
/* 205:293 */         if (rType == 1) {
/* 206:294 */           n.putIntProp(8, 2);
/* 207:    */         }
/* 208:    */       }
/* 209:299 */       else if (convertParameter(rChild))
/* 210:    */       {
/* 211:300 */         if (lType == 1) {
/* 212:301 */           n.putIntProp(8, 1);
/* 213:    */         }
/* 214:    */       }
/* 215:305 */       else if (lType == 1)
/* 216:    */       {
/* 217:306 */         if (rType == 1)
/* 218:    */         {
/* 219:307 */           n.putIntProp(8, 0);
/* 220:308 */           return 1;
/* 221:    */         }
/* 222:311 */         n.putIntProp(8, 1);
/* 223:    */       }
/* 224:315 */       else if (rType == 1)
/* 225:    */       {
/* 226:316 */         n.putIntProp(8, 2);
/* 227:    */       }
/* 228:322 */       return 0;
/* 229:    */     case 9: 
/* 230:    */     case 10: 
/* 231:    */     case 11: 
/* 232:    */     case 18: 
/* 233:    */     case 19: 
/* 234:    */     case 22: 
/* 235:    */     case 23: 
/* 236:    */     case 24: 
/* 237:    */     case 25: 
/* 238:334 */       Node lChild = n.getFirstChild();
/* 239:335 */       Node rChild = lChild.getNext();
/* 240:336 */       int lType = rewriteForNumberVariables(lChild, 1);
/* 241:337 */       int rType = rewriteForNumberVariables(rChild, 1);
/* 242:338 */       markDCPNumberContext(lChild);
/* 243:339 */       markDCPNumberContext(rChild);
/* 244:340 */       if (lType == 1)
/* 245:    */       {
/* 246:341 */         if (rType == 1)
/* 247:    */         {
/* 248:342 */           n.putIntProp(8, 0);
/* 249:343 */           return 1;
/* 250:    */         }
/* 251:346 */         if (!convertParameter(rChild))
/* 252:    */         {
/* 253:347 */           n.removeChild(rChild);
/* 254:348 */           n.addChildToBack(new Node(150, rChild));
/* 255:    */           
/* 256:350 */           n.putIntProp(8, 0);
/* 257:    */         }
/* 258:352 */         return 1;
/* 259:    */       }
/* 260:356 */       if (rType == 1)
/* 261:    */       {
/* 262:357 */         if (!convertParameter(lChild))
/* 263:    */         {
/* 264:358 */           n.removeChild(lChild);
/* 265:359 */           n.addChildToFront(new Node(150, lChild));
/* 266:    */           
/* 267:361 */           n.putIntProp(8, 0);
/* 268:    */         }
/* 269:363 */         return 1;
/* 270:    */       }
/* 271:366 */       if (!convertParameter(lChild))
/* 272:    */       {
/* 273:367 */         n.removeChild(lChild);
/* 274:368 */         n.addChildToFront(new Node(150, lChild));
/* 275:    */       }
/* 276:371 */       if (!convertParameter(rChild))
/* 277:    */       {
/* 278:372 */         n.removeChild(rChild);
/* 279:373 */         n.addChildToBack(new Node(150, rChild));
/* 280:    */       }
/* 281:376 */       n.putIntProp(8, 0);
/* 282:377 */       return 1;
/* 283:    */     case 37: 
/* 284:    */     case 140: 
/* 285:383 */       Node arrayBase = n.getFirstChild();
/* 286:384 */       Node arrayIndex = arrayBase.getNext();
/* 287:385 */       Node rValue = arrayIndex.getNext();
/* 288:386 */       int baseType = rewriteForNumberVariables(arrayBase, 1);
/* 289:387 */       if ((baseType == 1) && 
/* 290:388 */         (!convertParameter(arrayBase)))
/* 291:    */       {
/* 292:389 */         n.removeChild(arrayBase);
/* 293:390 */         n.addChildToFront(new Node(149, arrayBase));
/* 294:    */       }
/* 295:394 */       int indexType = rewriteForNumberVariables(arrayIndex, 1);
/* 296:395 */       if ((indexType == 1) && 
/* 297:396 */         (!convertParameter(arrayIndex))) {
/* 298:400 */         n.putIntProp(8, 1);
/* 299:    */       }
/* 300:403 */       int rValueType = rewriteForNumberVariables(rValue, 1);
/* 301:404 */       if ((rValueType == 1) && 
/* 302:405 */         (!convertParameter(rValue)))
/* 303:    */       {
/* 304:406 */         n.removeChild(rValue);
/* 305:407 */         n.addChildToBack(new Node(149, rValue));
/* 306:    */       }
/* 307:411 */       return 0;
/* 308:    */     case 36: 
/* 309:414 */       Node arrayBase = n.getFirstChild();
/* 310:415 */       Node arrayIndex = arrayBase.getNext();
/* 311:416 */       int baseType = rewriteForNumberVariables(arrayBase, 1);
/* 312:417 */       if ((baseType == 1) && 
/* 313:418 */         (!convertParameter(arrayBase)))
/* 314:    */       {
/* 315:419 */         n.removeChild(arrayBase);
/* 316:420 */         n.addChildToFront(new Node(149, arrayBase));
/* 317:    */       }
/* 318:424 */       int indexType = rewriteForNumberVariables(arrayIndex, 1);
/* 319:425 */       if ((indexType == 1) && 
/* 320:426 */         (!convertParameter(arrayIndex))) {
/* 321:430 */         n.putIntProp(8, 2);
/* 322:    */       }
/* 323:433 */       return 0;
/* 324:    */     case 38: 
/* 325:437 */       Node child = n.getFirstChild();
/* 326:    */       
/* 327:439 */       rewriteAsObjectChildren(child, child.getFirstChild());
/* 328:440 */       child = child.getNext();
/* 329:    */       
/* 330:442 */       OptFunctionNode target = (OptFunctionNode)n.getProp(9);
/* 331:444 */       if (target != null) {
/* 332:449 */         while (child != null)
/* 333:    */         {
/* 334:450 */           int type = rewriteForNumberVariables(child, 1);
/* 335:451 */           if (type == 1) {
/* 336:452 */             markDCPNumberContext(child);
/* 337:    */           }
/* 338:454 */           child = child.getNext();
/* 339:    */         }
/* 340:    */       }
/* 341:457 */       rewriteAsObjectChildren(n, child);
/* 342:    */       
/* 343:459 */       return 0;
/* 344:    */     }
/* 345:462 */     rewriteAsObjectChildren(n, n.getFirstChild());
/* 346:463 */     return 0;
/* 347:    */   }
/* 348:    */   
/* 349:    */   private void rewriteAsObjectChildren(Node n, Node child)
/* 350:    */   {
/* 351:471 */     while (child != null)
/* 352:    */     {
/* 353:472 */       Node nextChild = child.getNext();
/* 354:473 */       int type = rewriteForNumberVariables(child, 0);
/* 355:474 */       if ((type == 1) && 
/* 356:475 */         (!convertParameter(child)))
/* 357:    */       {
/* 358:476 */         n.removeChild(child);
/* 359:477 */         Node nuChild = new Node(149, child);
/* 360:478 */         if (nextChild == null) {
/* 361:479 */           n.addChildToBack(nuChild);
/* 362:    */         } else {
/* 363:481 */           n.addChildBefore(nuChild, nextChild);
/* 364:    */         }
/* 365:    */       }
/* 366:484 */       child = nextChild;
/* 367:    */     }
/* 368:    */   }
/* 369:    */   
/* 370:    */   private static void buildStatementList_r(Node node, ObjArray statements)
/* 371:    */   {
/* 372:490 */     int type = node.getType();
/* 373:491 */     if ((type == 129) || (type == 141) || (type == 132) || (type == 109))
/* 374:    */     {
/* 375:496 */       Node child = node.getFirstChild();
/* 376:497 */       while (child != null)
/* 377:    */       {
/* 378:498 */         buildStatementList_r(child, statements);
/* 379:499 */         child = child.getNext();
/* 380:    */       }
/* 381:    */     }
/* 382:    */     else
/* 383:    */     {
/* 384:502 */       statements.add(node);
/* 385:    */     }
/* 386:    */   }
/* 387:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.Optimizer
 * JD-Core Version:    0.7.0.1
 */