/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.ALOAD;
/*   6:    */ import org.apache.bcel.generic.BranchHandle;
/*   7:    */ import org.apache.bcel.generic.ClassGen;
/*   8:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   9:    */ import org.apache.bcel.generic.IF_ICMPEQ;
/*  10:    */ import org.apache.bcel.generic.ILOAD;
/*  11:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  12:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  13:    */ import org.apache.bcel.generic.InstructionConstants;
/*  14:    */ import org.apache.bcel.generic.InstructionHandle;
/*  15:    */ import org.apache.bcel.generic.InstructionList;
/*  16:    */ import org.apache.bcel.generic.PUSH;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  21:    */ 
/*  22:    */ final class Whitespace
/*  23:    */   extends TopLevelElement
/*  24:    */ {
/*  25:    */   public static final int USE_PREDICATE = 0;
/*  26:    */   public static final int STRIP_SPACE = 1;
/*  27:    */   public static final int PRESERVE_SPACE = 2;
/*  28:    */   public static final int RULE_NONE = 0;
/*  29:    */   public static final int RULE_ELEMENT = 1;
/*  30:    */   public static final int RULE_NAMESPACE = 2;
/*  31:    */   public static final int RULE_ALL = 3;
/*  32:    */   private String _elementList;
/*  33:    */   private int _action;
/*  34:    */   private int _importPrecedence;
/*  35:    */   
/*  36:    */   private static final class WhitespaceRule
/*  37:    */   {
/*  38:    */     private final int _action;
/*  39:    */     private String _namespace;
/*  40:    */     private String _element;
/*  41:    */     private int _type;
/*  42:    */     private int _priority;
/*  43:    */     
/*  44:    */     public WhitespaceRule(int action, String element, int precedence)
/*  45:    */     {
/*  46: 78 */       this._action = action;
/*  47:    */       
/*  48:    */ 
/*  49: 81 */       int colon = element.lastIndexOf(':');
/*  50: 82 */       if (colon >= 0)
/*  51:    */       {
/*  52: 83 */         this._namespace = element.substring(0, colon);
/*  53: 84 */         this._element = element.substring(colon + 1, element.length());
/*  54:    */       }
/*  55:    */       else
/*  56:    */       {
/*  57: 87 */         this._namespace = "";
/*  58: 88 */         this._element = element;
/*  59:    */       }
/*  60: 92 */       this._priority = (precedence << 2);
/*  61: 95 */       if (this._element.equals("*"))
/*  62:    */       {
/*  63: 96 */         if (this._namespace == "")
/*  64:    */         {
/*  65: 97 */           this._type = 3;
/*  66: 98 */           this._priority += 2;
/*  67:    */         }
/*  68:    */         else
/*  69:    */         {
/*  70:101 */           this._type = 2;
/*  71:102 */           this._priority += 1;
/*  72:    */         }
/*  73:    */       }
/*  74:    */       else {
/*  75:106 */         this._type = 1;
/*  76:    */       }
/*  77:    */     }
/*  78:    */     
/*  79:    */     public int compareTo(WhitespaceRule other)
/*  80:    */     {
/*  81:114 */       return this._priority > other._priority ? 1 : this._priority < other._priority ? -1 : 0;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public int getAction()
/*  85:    */     {
/*  86:119 */       return this._action;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public int getStrength()
/*  90:    */     {
/*  91:120 */       return this._type;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public int getPriority()
/*  95:    */     {
/*  96:121 */       return this._priority;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public String getElement()
/* 100:    */     {
/* 101:122 */       return this._element;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public String getNamespace()
/* 105:    */     {
/* 106:123 */       return this._namespace;
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void parseContents(Parser parser)
/* 111:    */   {
/* 112:132 */     this._action = (this._qname.getLocalPart().endsWith("strip-space") ? 1 : 2);
/* 113:    */     
/* 114:    */ 
/* 115:    */ 
/* 116:136 */     this._importPrecedence = parser.getCurrentImportPrecedence();
/* 117:    */     
/* 118:    */ 
/* 119:139 */     this._elementList = getAttribute("elements");
/* 120:140 */     if ((this._elementList == null) || (this._elementList.length() == 0))
/* 121:    */     {
/* 122:141 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "elements");
/* 123:142 */       return;
/* 124:    */     }
/* 125:145 */     SymbolTable stable = parser.getSymbolTable();
/* 126:146 */     StringTokenizer list = new StringTokenizer(this._elementList);
/* 127:147 */     StringBuffer elements = new StringBuffer("");
/* 128:149 */     while (list.hasMoreElements())
/* 129:    */     {
/* 130:150 */       String token = list.nextToken();
/* 131:    */       
/* 132:    */ 
/* 133:153 */       int col = token.indexOf(':');
/* 134:155 */       if (col != -1)
/* 135:    */       {
/* 136:156 */         String namespace = lookupNamespace(token.substring(0, col));
/* 137:157 */         if (namespace != null) {
/* 138:158 */           elements.append(namespace + ":" + token.substring(col + 1, token.length()));
/* 139:    */         } else {
/* 140:161 */           elements.append(token);
/* 141:    */         }
/* 142:    */       }
/* 143:    */       else
/* 144:    */       {
/* 145:164 */         elements.append(token);
/* 146:    */       }
/* 147:167 */       if (list.hasMoreElements()) {
/* 148:168 */         elements.append(" ");
/* 149:    */       }
/* 150:    */     }
/* 151:170 */     this._elementList = elements.toString();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Vector getRules()
/* 155:    */   {
/* 156:179 */     Vector rules = new Vector();
/* 157:    */     
/* 158:181 */     StringTokenizer list = new StringTokenizer(this._elementList);
/* 159:182 */     while (list.hasMoreElements()) {
/* 160:183 */       rules.add(new WhitespaceRule(this._action, list.nextToken(), this._importPrecedence));
/* 161:    */     }
/* 162:187 */     return rules;
/* 163:    */   }
/* 164:    */   
/* 165:    */   private static WhitespaceRule findContradictingRule(Vector rules, WhitespaceRule rule)
/* 166:    */   {
/* 167:197 */     for (int i = 0; i < rules.size(); i++)
/* 168:    */     {
/* 169:199 */       WhitespaceRule currentRule = (WhitespaceRule)rules.elementAt(i);
/* 170:201 */       if (currentRule == rule) {
/* 171:202 */         return null;
/* 172:    */       }
/* 173:210 */       switch (currentRule.getStrength())
/* 174:    */       {
/* 175:    */       case 3: 
/* 176:212 */         return currentRule;
/* 177:    */       case 1: 
/* 178:215 */         if (!rule.getElement().equals(currentRule.getElement())) {
/* 179:    */           break;
/* 180:    */         }
/* 181:    */       case 2: 
/* 182:220 */         if (rule.getNamespace().equals(currentRule.getNamespace())) {
/* 183:221 */           return currentRule;
/* 184:    */         }
/* 185:    */         break;
/* 186:    */       }
/* 187:    */     }
/* 188:226 */     return null;
/* 189:    */   }
/* 190:    */   
/* 191:    */   private static int prioritizeRules(Vector rules)
/* 192:    */   {
/* 193:236 */     int defaultAction = 2;
/* 194:    */     
/* 195:    */ 
/* 196:239 */     quicksort(rules, 0, rules.size() - 1);
/* 197:    */     
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:244 */     boolean strip = false;
/* 202:    */     WhitespaceRule currentRule;
/* 203:245 */     for (int i = 0; i < rules.size(); i++)
/* 204:    */     {
/* 205:246 */       currentRule = (WhitespaceRule)rules.elementAt(i);
/* 206:247 */       if (currentRule.getAction() == 1) {
/* 207:248 */         strip = true;
/* 208:    */       }
/* 209:    */     }
/* 210:252 */     if (!strip)
/* 211:    */     {
/* 212:253 */       rules.removeAllElements();
/* 213:254 */       return 2;
/* 214:    */     }
/* 215:258 */     for (int idx = 0; idx < rules.size();)
/* 216:    */     {
/* 217:259 */       currentRule = (WhitespaceRule)rules.elementAt(idx);
/* 218:262 */       if (findContradictingRule(rules, currentRule) != null)
/* 219:    */       {
/* 220:263 */         rules.remove(idx);
/* 221:    */       }
/* 222:    */       else
/* 223:    */       {
/* 224:267 */         if (currentRule.getStrength() == 3)
/* 225:    */         {
/* 226:268 */           defaultAction = currentRule.getAction();
/* 227:269 */           for (int i = idx; i < rules.size(); i++) {
/* 228:270 */             rules.removeElementAt(i);
/* 229:    */           }
/* 230:    */         }
/* 231:274 */         idx++;
/* 232:    */       }
/* 233:    */     }
/* 234:279 */     if (rules.size() == 0) {
/* 235:280 */       return defaultAction;
/* 236:    */     }
/* 237:    */     do
/* 238:    */     {
/* 239:286 */       currentRule = (WhitespaceRule)rules.lastElement();
/* 240:287 */       if (currentRule.getAction() != defaultAction) {
/* 241:    */         break;
/* 242:    */       }
/* 243:288 */       rules.removeElementAt(rules.size() - 1);
/* 244:293 */     } while (rules.size() > 0);
/* 245:296 */     return defaultAction;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public static void compileStripSpace(BranchHandle[] strip, int sCount, InstructionList il)
/* 249:    */   {
/* 250:302 */     InstructionHandle target = il.append(InstructionConstants.ICONST_1);
/* 251:303 */     il.append(InstructionConstants.IRETURN);
/* 252:304 */     for (int i = 0; i < sCount; i++) {
/* 253:305 */       strip[i].setTarget(target);
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public static void compilePreserveSpace(BranchHandle[] preserve, int pCount, InstructionList il)
/* 258:    */   {
/* 259:312 */     InstructionHandle target = il.append(InstructionConstants.ICONST_0);
/* 260:313 */     il.append(InstructionConstants.IRETURN);
/* 261:314 */     for (int i = 0; i < pCount; i++) {
/* 262:315 */       preserve[i].setTarget(target);
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   private static void compilePredicate(Vector rules, int defaultAction, ClassGenerator classGen)
/* 267:    */   {
/* 268:337 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 269:338 */     InstructionList il = new InstructionList();
/* 270:339 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/* 271:    */     
/* 272:    */ 
/* 273:342 */     MethodGenerator stripSpace = new MethodGenerator(17, org.apache.bcel.generic.Type.BOOLEAN, new org.apache.bcel.generic.Type[] { Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT }, new String[] { "dom", "node", "type" }, "stripSpace", classGen.getClassName(), il, cpg);
/* 274:    */     
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:353 */     classGen.addInterface("org/apache/xalan/xsltc/StripFilter");
/* 285:    */     
/* 286:355 */     int paramDom = stripSpace.getLocalIndex("dom");
/* 287:356 */     int paramCurrent = stripSpace.getLocalIndex("node");
/* 288:357 */     int paramType = stripSpace.getLocalIndex("type");
/* 289:    */     
/* 290:359 */     BranchHandle[] strip = new BranchHandle[rules.size()];
/* 291:360 */     BranchHandle[] preserve = new BranchHandle[rules.size()];
/* 292:361 */     int sCount = 0;
/* 293:362 */     int pCount = 0;
/* 294:365 */     for (int i = 0; i < rules.size(); i++)
/* 295:    */     {
/* 296:367 */       WhitespaceRule rule = (WhitespaceRule)rules.elementAt(i);
/* 297:    */       
/* 298:    */ 
/* 299:370 */       int gns = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNamespaceName", "(I)Ljava/lang/String;");
/* 300:    */       
/* 301:    */ 
/* 302:    */ 
/* 303:374 */       int strcmp = cpg.addMethodref("java/lang/String", "compareTo", "(Ljava/lang/String;)I");
/* 304:379 */       if (rule.getStrength() == 2)
/* 305:    */       {
/* 306:380 */         il.append(new ALOAD(paramDom));
/* 307:381 */         il.append(new ILOAD(paramCurrent));
/* 308:382 */         il.append(new INVOKEINTERFACE(gns, 2));
/* 309:383 */         il.append(new PUSH(cpg, rule.getNamespace()));
/* 310:384 */         il.append(new INVOKEVIRTUAL(strcmp));
/* 311:385 */         il.append(InstructionConstants.ICONST_0);
/* 312:387 */         if (rule.getAction() == 1) {
/* 313:388 */           strip[(sCount++)] = il.append(new IF_ICMPEQ(null));
/* 314:    */         } else {
/* 315:391 */           preserve[(pCount++)] = il.append(new IF_ICMPEQ(null));
/* 316:    */         }
/* 317:    */       }
/* 318:395 */       else if (rule.getStrength() == 1)
/* 319:    */       {
/* 320:397 */         Parser parser = classGen.getParser();
/* 321:    */         QName qname;
/* 322:399 */         if (rule.getNamespace() != "") {
/* 323:400 */           qname = parser.getQName(rule.getNamespace(), null, rule.getElement());
/* 324:    */         } else {
/* 325:403 */           qname = parser.getQName(rule.getElement());
/* 326:    */         }
/* 327:406 */         int elementType = xsltc.registerElement(qname);
/* 328:407 */         il.append(new ILOAD(paramType));
/* 329:408 */         il.append(new PUSH(cpg, elementType));
/* 330:411 */         if (rule.getAction() == 1) {
/* 331:412 */           strip[(sCount++)] = il.append(new IF_ICMPEQ(null));
/* 332:    */         } else {
/* 333:414 */           preserve[(pCount++)] = il.append(new IF_ICMPEQ(null));
/* 334:    */         }
/* 335:    */       }
/* 336:    */     }
/* 337:418 */     if (defaultAction == 1)
/* 338:    */     {
/* 339:419 */       compileStripSpace(strip, sCount, il);
/* 340:420 */       compilePreserveSpace(preserve, pCount, il);
/* 341:    */     }
/* 342:    */     else
/* 343:    */     {
/* 344:423 */       compilePreserveSpace(preserve, pCount, il);
/* 345:424 */       compileStripSpace(strip, sCount, il);
/* 346:    */     }
/* 347:427 */     classGen.addMethod(stripSpace);
/* 348:    */   }
/* 349:    */   
/* 350:    */   private static void compileDefault(int defaultAction, ClassGenerator classGen)
/* 351:    */   {
/* 352:435 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 353:436 */     InstructionList il = new InstructionList();
/* 354:437 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/* 355:    */     
/* 356:    */ 
/* 357:440 */     MethodGenerator stripSpace = new MethodGenerator(17, org.apache.bcel.generic.Type.BOOLEAN, new org.apache.bcel.generic.Type[] { Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT }, new String[] { "dom", "node", "type" }, "stripSpace", classGen.getClassName(), il, cpg);
/* 358:    */     
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:451 */     classGen.addInterface("org/apache/xalan/xsltc/StripFilter");
/* 369:453 */     if (defaultAction == 1) {
/* 370:454 */       il.append(InstructionConstants.ICONST_1);
/* 371:    */     } else {
/* 372:456 */       il.append(InstructionConstants.ICONST_0);
/* 373:    */     }
/* 374:457 */     il.append(InstructionConstants.IRETURN);
/* 375:    */     
/* 376:459 */     classGen.addMethod(stripSpace);
/* 377:    */   }
/* 378:    */   
/* 379:    */   public static int translateRules(Vector rules, ClassGenerator classGen)
/* 380:    */   {
/* 381:474 */     int defaultAction = prioritizeRules(rules);
/* 382:476 */     if (rules.size() == 0)
/* 383:    */     {
/* 384:477 */       compileDefault(defaultAction, classGen);
/* 385:478 */       return defaultAction;
/* 386:    */     }
/* 387:481 */     compilePredicate(rules, defaultAction, classGen);
/* 388:    */     
/* 389:483 */     return 0;
/* 390:    */   }
/* 391:    */   
/* 392:    */   private static void quicksort(Vector rules, int p, int r)
/* 393:    */   {
/* 394:490 */     while (p < r)
/* 395:    */     {
/* 396:491 */       int q = partition(rules, p, r);
/* 397:492 */       quicksort(rules, p, q);
/* 398:493 */       p = q + 1;
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   private static int partition(Vector rules, int p, int r)
/* 403:    */   {
/* 404:501 */     WhitespaceRule x = (WhitespaceRule)rules.elementAt(p + r >>> 1);
/* 405:502 */     int i = p - 1;int j = r + 1;
/* 406:    */     for (;;)
/* 407:    */     {
/* 408:504 */       if (x.compareTo((WhitespaceRule)rules.elementAt(--j)) >= 0)
/* 409:    */       {
/* 410:506 */         while ((goto 51) || (x.compareTo((WhitespaceRule)rules.elementAt(++i)) > 0)) {}
/* 411:508 */         if (i >= j) {
/* 412:    */           break;
/* 413:    */         }
/* 414:509 */         WhitespaceRule tmp = (WhitespaceRule)rules.elementAt(i);
/* 415:510 */         rules.setElementAt(rules.elementAt(j), i);
/* 416:511 */         rules.setElementAt(tmp, j);
/* 417:    */       }
/* 418:    */     }
/* 419:514 */     return j;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public org.apache.xalan.xsltc.compiler.util.Type typeCheck(SymbolTable stable)
/* 423:    */     throws TypeCheckError
/* 424:    */   {
/* 425:523 */     return org.apache.xalan.xsltc.compiler.util.Type.Void;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/* 429:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Whitespace
 * JD-Core Version:    0.7.0.1
 */