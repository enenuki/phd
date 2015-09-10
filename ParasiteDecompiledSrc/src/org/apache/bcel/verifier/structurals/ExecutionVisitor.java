/*    1:     */ package org.apache.bcel.verifier.structurals;
/*    2:     */ 
/*    3:     */ import org.apache.bcel.classfile.Constant;
/*    4:     */ import org.apache.bcel.classfile.ConstantDouble;
/*    5:     */ import org.apache.bcel.classfile.ConstantFloat;
/*    6:     */ import org.apache.bcel.classfile.ConstantInteger;
/*    7:     */ import org.apache.bcel.classfile.ConstantLong;
/*    8:     */ import org.apache.bcel.classfile.ConstantString;
/*    9:     */ import org.apache.bcel.generic.AALOAD;
/*   10:     */ import org.apache.bcel.generic.AASTORE;
/*   11:     */ import org.apache.bcel.generic.ACONST_NULL;
/*   12:     */ import org.apache.bcel.generic.ALOAD;
/*   13:     */ import org.apache.bcel.generic.ANEWARRAY;
/*   14:     */ import org.apache.bcel.generic.ARETURN;
/*   15:     */ import org.apache.bcel.generic.ARRAYLENGTH;
/*   16:     */ import org.apache.bcel.generic.ASTORE;
/*   17:     */ import org.apache.bcel.generic.ATHROW;
/*   18:     */ import org.apache.bcel.generic.ArrayType;
/*   19:     */ import org.apache.bcel.generic.BALOAD;
/*   20:     */ import org.apache.bcel.generic.BASTORE;
/*   21:     */ import org.apache.bcel.generic.BIPUSH;
/*   22:     */ import org.apache.bcel.generic.CALOAD;
/*   23:     */ import org.apache.bcel.generic.CASTORE;
/*   24:     */ import org.apache.bcel.generic.CHECKCAST;
/*   25:     */ import org.apache.bcel.generic.CPInstruction;
/*   26:     */ import org.apache.bcel.generic.ConstantPoolGen;
/*   27:     */ import org.apache.bcel.generic.D2F;
/*   28:     */ import org.apache.bcel.generic.D2I;
/*   29:     */ import org.apache.bcel.generic.D2L;
/*   30:     */ import org.apache.bcel.generic.DADD;
/*   31:     */ import org.apache.bcel.generic.DALOAD;
/*   32:     */ import org.apache.bcel.generic.DASTORE;
/*   33:     */ import org.apache.bcel.generic.DCMPG;
/*   34:     */ import org.apache.bcel.generic.DCMPL;
/*   35:     */ import org.apache.bcel.generic.DCONST;
/*   36:     */ import org.apache.bcel.generic.DDIV;
/*   37:     */ import org.apache.bcel.generic.DLOAD;
/*   38:     */ import org.apache.bcel.generic.DMUL;
/*   39:     */ import org.apache.bcel.generic.DNEG;
/*   40:     */ import org.apache.bcel.generic.DREM;
/*   41:     */ import org.apache.bcel.generic.DRETURN;
/*   42:     */ import org.apache.bcel.generic.DSTORE;
/*   43:     */ import org.apache.bcel.generic.DSUB;
/*   44:     */ import org.apache.bcel.generic.DUP;
/*   45:     */ import org.apache.bcel.generic.DUP2;
/*   46:     */ import org.apache.bcel.generic.DUP2_X1;
/*   47:     */ import org.apache.bcel.generic.DUP2_X2;
/*   48:     */ import org.apache.bcel.generic.DUP_X1;
/*   49:     */ import org.apache.bcel.generic.DUP_X2;
/*   50:     */ import org.apache.bcel.generic.EmptyVisitor;
/*   51:     */ import org.apache.bcel.generic.F2D;
/*   52:     */ import org.apache.bcel.generic.F2I;
/*   53:     */ import org.apache.bcel.generic.F2L;
/*   54:     */ import org.apache.bcel.generic.FADD;
/*   55:     */ import org.apache.bcel.generic.FALOAD;
/*   56:     */ import org.apache.bcel.generic.FASTORE;
/*   57:     */ import org.apache.bcel.generic.FCMPG;
/*   58:     */ import org.apache.bcel.generic.FCMPL;
/*   59:     */ import org.apache.bcel.generic.FCONST;
/*   60:     */ import org.apache.bcel.generic.FDIV;
/*   61:     */ import org.apache.bcel.generic.FLOAD;
/*   62:     */ import org.apache.bcel.generic.FMUL;
/*   63:     */ import org.apache.bcel.generic.FNEG;
/*   64:     */ import org.apache.bcel.generic.FREM;
/*   65:     */ import org.apache.bcel.generic.FRETURN;
/*   66:     */ import org.apache.bcel.generic.FSTORE;
/*   67:     */ import org.apache.bcel.generic.FSUB;
/*   68:     */ import org.apache.bcel.generic.FieldInstruction;
/*   69:     */ import org.apache.bcel.generic.GETFIELD;
/*   70:     */ import org.apache.bcel.generic.GETSTATIC;
/*   71:     */ import org.apache.bcel.generic.GOTO;
/*   72:     */ import org.apache.bcel.generic.GOTO_W;
/*   73:     */ import org.apache.bcel.generic.I2B;
/*   74:     */ import org.apache.bcel.generic.I2C;
/*   75:     */ import org.apache.bcel.generic.I2D;
/*   76:     */ import org.apache.bcel.generic.I2F;
/*   77:     */ import org.apache.bcel.generic.I2L;
/*   78:     */ import org.apache.bcel.generic.I2S;
/*   79:     */ import org.apache.bcel.generic.IADD;
/*   80:     */ import org.apache.bcel.generic.IALOAD;
/*   81:     */ import org.apache.bcel.generic.IAND;
/*   82:     */ import org.apache.bcel.generic.IASTORE;
/*   83:     */ import org.apache.bcel.generic.ICONST;
/*   84:     */ import org.apache.bcel.generic.IDIV;
/*   85:     */ import org.apache.bcel.generic.IFEQ;
/*   86:     */ import org.apache.bcel.generic.IFGE;
/*   87:     */ import org.apache.bcel.generic.IFGT;
/*   88:     */ import org.apache.bcel.generic.IFLE;
/*   89:     */ import org.apache.bcel.generic.IFLT;
/*   90:     */ import org.apache.bcel.generic.IFNE;
/*   91:     */ import org.apache.bcel.generic.IFNONNULL;
/*   92:     */ import org.apache.bcel.generic.IFNULL;
/*   93:     */ import org.apache.bcel.generic.IF_ACMPEQ;
/*   94:     */ import org.apache.bcel.generic.IF_ACMPNE;
/*   95:     */ import org.apache.bcel.generic.IF_ICMPEQ;
/*   96:     */ import org.apache.bcel.generic.IF_ICMPGE;
/*   97:     */ import org.apache.bcel.generic.IF_ICMPGT;
/*   98:     */ import org.apache.bcel.generic.IF_ICMPLE;
/*   99:     */ import org.apache.bcel.generic.IF_ICMPLT;
/*  100:     */ import org.apache.bcel.generic.IF_ICMPNE;
/*  101:     */ import org.apache.bcel.generic.IINC;
/*  102:     */ import org.apache.bcel.generic.ILOAD;
/*  103:     */ import org.apache.bcel.generic.IMUL;
/*  104:     */ import org.apache.bcel.generic.INEG;
/*  105:     */ import org.apache.bcel.generic.INSTANCEOF;
/*  106:     */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  107:     */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  108:     */ import org.apache.bcel.generic.INVOKESTATIC;
/*  109:     */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  110:     */ import org.apache.bcel.generic.IOR;
/*  111:     */ import org.apache.bcel.generic.IREM;
/*  112:     */ import org.apache.bcel.generic.IRETURN;
/*  113:     */ import org.apache.bcel.generic.ISHL;
/*  114:     */ import org.apache.bcel.generic.ISHR;
/*  115:     */ import org.apache.bcel.generic.ISTORE;
/*  116:     */ import org.apache.bcel.generic.ISUB;
/*  117:     */ import org.apache.bcel.generic.IUSHR;
/*  118:     */ import org.apache.bcel.generic.IXOR;
/*  119:     */ import org.apache.bcel.generic.InvokeInstruction;
/*  120:     */ import org.apache.bcel.generic.JSR;
/*  121:     */ import org.apache.bcel.generic.JSR_W;
/*  122:     */ import org.apache.bcel.generic.JsrInstruction;
/*  123:     */ import org.apache.bcel.generic.L2D;
/*  124:     */ import org.apache.bcel.generic.L2F;
/*  125:     */ import org.apache.bcel.generic.L2I;
/*  126:     */ import org.apache.bcel.generic.LADD;
/*  127:     */ import org.apache.bcel.generic.LALOAD;
/*  128:     */ import org.apache.bcel.generic.LAND;
/*  129:     */ import org.apache.bcel.generic.LASTORE;
/*  130:     */ import org.apache.bcel.generic.LCMP;
/*  131:     */ import org.apache.bcel.generic.LCONST;
/*  132:     */ import org.apache.bcel.generic.LDC;
/*  133:     */ import org.apache.bcel.generic.LDC2_W;
/*  134:     */ import org.apache.bcel.generic.LDC_W;
/*  135:     */ import org.apache.bcel.generic.LDIV;
/*  136:     */ import org.apache.bcel.generic.LLOAD;
/*  137:     */ import org.apache.bcel.generic.LMUL;
/*  138:     */ import org.apache.bcel.generic.LNEG;
/*  139:     */ import org.apache.bcel.generic.LOOKUPSWITCH;
/*  140:     */ import org.apache.bcel.generic.LOR;
/*  141:     */ import org.apache.bcel.generic.LREM;
/*  142:     */ import org.apache.bcel.generic.LRETURN;
/*  143:     */ import org.apache.bcel.generic.LSHL;
/*  144:     */ import org.apache.bcel.generic.LSHR;
/*  145:     */ import org.apache.bcel.generic.LSTORE;
/*  146:     */ import org.apache.bcel.generic.LSUB;
/*  147:     */ import org.apache.bcel.generic.LUSHR;
/*  148:     */ import org.apache.bcel.generic.LXOR;
/*  149:     */ import org.apache.bcel.generic.LocalVariableInstruction;
/*  150:     */ import org.apache.bcel.generic.MONITORENTER;
/*  151:     */ import org.apache.bcel.generic.MONITOREXIT;
/*  152:     */ import org.apache.bcel.generic.MULTIANEWARRAY;
/*  153:     */ import org.apache.bcel.generic.NEW;
/*  154:     */ import org.apache.bcel.generic.NEWARRAY;
/*  155:     */ import org.apache.bcel.generic.NOP;
/*  156:     */ import org.apache.bcel.generic.ObjectType;
/*  157:     */ import org.apache.bcel.generic.POP;
/*  158:     */ import org.apache.bcel.generic.POP2;
/*  159:     */ import org.apache.bcel.generic.PUTFIELD;
/*  160:     */ import org.apache.bcel.generic.PUTSTATIC;
/*  161:     */ import org.apache.bcel.generic.RET;
/*  162:     */ import org.apache.bcel.generic.RETURN;
/*  163:     */ import org.apache.bcel.generic.ReturnaddressType;
/*  164:     */ import org.apache.bcel.generic.SALOAD;
/*  165:     */ import org.apache.bcel.generic.SASTORE;
/*  166:     */ import org.apache.bcel.generic.SIPUSH;
/*  167:     */ import org.apache.bcel.generic.SWAP;
/*  168:     */ import org.apache.bcel.generic.TABLESWITCH;
/*  169:     */ import org.apache.bcel.generic.Type;
/*  170:     */ import org.apache.bcel.generic.Visitor;
/*  171:     */ 
/*  172:     */ public class ExecutionVisitor
/*  173:     */   extends EmptyVisitor
/*  174:     */   implements Visitor
/*  175:     */ {
/*  176: 105 */   private Frame frame = null;
/*  177: 111 */   private ConstantPoolGen cpg = null;
/*  178:     */   
/*  179:     */   private OperandStack stack()
/*  180:     */   {
/*  181: 123 */     return this.frame.getStack();
/*  182:     */   }
/*  183:     */   
/*  184:     */   private LocalVariables locals()
/*  185:     */   {
/*  186: 131 */     return this.frame.getLocals();
/*  187:     */   }
/*  188:     */   
/*  189:     */   public void setConstantPoolGen(ConstantPoolGen cpg)
/*  190:     */   {
/*  191: 138 */     this.cpg = cpg;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void setFrame(Frame f)
/*  195:     */   {
/*  196: 148 */     this.frame = f;
/*  197:     */   }
/*  198:     */   
/*  199:     */   public void visitAALOAD(AALOAD o)
/*  200:     */   {
/*  201: 163 */     stack().pop();
/*  202:     */     
/*  203: 165 */     Type t = stack().pop();
/*  204: 166 */     if (t == Type.NULL)
/*  205:     */     {
/*  206: 167 */       stack().push(Type.NULL);
/*  207:     */     }
/*  208:     */     else
/*  209:     */     {
/*  210: 170 */       ArrayType at = (ArrayType)t;
/*  211: 171 */       stack().push(at.getElementType());
/*  212:     */     }
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void visitAASTORE(AASTORE o)
/*  216:     */   {
/*  217: 176 */     stack().pop();
/*  218: 177 */     stack().pop();
/*  219: 178 */     stack().pop();
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void visitACONST_NULL(ACONST_NULL o)
/*  223:     */   {
/*  224: 182 */     stack().push(Type.NULL);
/*  225:     */   }
/*  226:     */   
/*  227:     */   public void visitALOAD(ALOAD o)
/*  228:     */   {
/*  229: 186 */     stack().push(locals().get(o.getIndex()));
/*  230:     */   }
/*  231:     */   
/*  232:     */   public void visitANEWARRAY(ANEWARRAY o)
/*  233:     */   {
/*  234: 190 */     stack().pop();
/*  235: 191 */     stack().push(new ArrayType(o.getType(this.cpg), 1));
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void visitARETURN(ARETURN o)
/*  239:     */   {
/*  240: 195 */     stack().pop();
/*  241:     */   }
/*  242:     */   
/*  243:     */   public void visitARRAYLENGTH(ARRAYLENGTH o)
/*  244:     */   {
/*  245: 199 */     stack().pop();
/*  246: 200 */     stack().push(Type.INT);
/*  247:     */   }
/*  248:     */   
/*  249:     */   public void visitASTORE(ASTORE o)
/*  250:     */   {
/*  251: 205 */     locals().set(o.getIndex(), stack().pop());
/*  252:     */   }
/*  253:     */   
/*  254:     */   public void visitATHROW(ATHROW o)
/*  255:     */   {
/*  256: 211 */     Type t = stack().pop();
/*  257: 212 */     stack().clear();
/*  258: 213 */     if (t.equals(Type.NULL)) {
/*  259: 214 */       stack().push(Type.getType("Ljava/lang/NullPointerException;"));
/*  260:     */     } else {
/*  261: 216 */       stack().push(t);
/*  262:     */     }
/*  263:     */   }
/*  264:     */   
/*  265:     */   public void visitBALOAD(BALOAD o)
/*  266:     */   {
/*  267: 221 */     stack().pop();
/*  268: 222 */     stack().pop();
/*  269: 223 */     stack().push(Type.INT);
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void visitBASTORE(BASTORE o)
/*  273:     */   {
/*  274: 228 */     stack().pop();
/*  275: 229 */     stack().pop();
/*  276: 230 */     stack().pop();
/*  277:     */   }
/*  278:     */   
/*  279:     */   public void visitBIPUSH(BIPUSH o)
/*  280:     */   {
/*  281: 235 */     stack().push(Type.INT);
/*  282:     */   }
/*  283:     */   
/*  284:     */   public void visitCALOAD(CALOAD o)
/*  285:     */   {
/*  286: 240 */     stack().pop();
/*  287: 241 */     stack().pop();
/*  288: 242 */     stack().push(Type.INT);
/*  289:     */   }
/*  290:     */   
/*  291:     */   public void visitCASTORE(CASTORE o)
/*  292:     */   {
/*  293: 246 */     stack().pop();
/*  294: 247 */     stack().pop();
/*  295: 248 */     stack().pop();
/*  296:     */   }
/*  297:     */   
/*  298:     */   public void visitCHECKCAST(CHECKCAST o)
/*  299:     */   {
/*  300: 257 */     stack().pop();
/*  301: 258 */     stack().push(o.getType(this.cpg));
/*  302:     */   }
/*  303:     */   
/*  304:     */   public void visitD2F(D2F o)
/*  305:     */   {
/*  306: 263 */     stack().pop();
/*  307: 264 */     stack().push(Type.FLOAT);
/*  308:     */   }
/*  309:     */   
/*  310:     */   public void visitD2I(D2I o)
/*  311:     */   {
/*  312: 268 */     stack().pop();
/*  313: 269 */     stack().push(Type.INT);
/*  314:     */   }
/*  315:     */   
/*  316:     */   public void visitD2L(D2L o)
/*  317:     */   {
/*  318: 273 */     stack().pop();
/*  319: 274 */     stack().push(Type.LONG);
/*  320:     */   }
/*  321:     */   
/*  322:     */   public void visitDADD(DADD o)
/*  323:     */   {
/*  324: 278 */     stack().pop();
/*  325: 279 */     stack().pop();
/*  326: 280 */     stack().push(Type.DOUBLE);
/*  327:     */   }
/*  328:     */   
/*  329:     */   public void visitDALOAD(DALOAD o)
/*  330:     */   {
/*  331: 284 */     stack().pop();
/*  332: 285 */     stack().pop();
/*  333: 286 */     stack().push(Type.DOUBLE);
/*  334:     */   }
/*  335:     */   
/*  336:     */   public void visitDASTORE(DASTORE o)
/*  337:     */   {
/*  338: 290 */     stack().pop();
/*  339: 291 */     stack().pop();
/*  340: 292 */     stack().pop();
/*  341:     */   }
/*  342:     */   
/*  343:     */   public void visitDCMPG(DCMPG o)
/*  344:     */   {
/*  345: 296 */     stack().pop();
/*  346: 297 */     stack().pop();
/*  347: 298 */     stack().push(Type.INT);
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void visitDCMPL(DCMPL o)
/*  351:     */   {
/*  352: 302 */     stack().pop();
/*  353: 303 */     stack().pop();
/*  354: 304 */     stack().push(Type.INT);
/*  355:     */   }
/*  356:     */   
/*  357:     */   public void visitDCONST(DCONST o)
/*  358:     */   {
/*  359: 308 */     stack().push(Type.DOUBLE);
/*  360:     */   }
/*  361:     */   
/*  362:     */   public void visitDDIV(DDIV o)
/*  363:     */   {
/*  364: 312 */     stack().pop();
/*  365: 313 */     stack().pop();
/*  366: 314 */     stack().push(Type.DOUBLE);
/*  367:     */   }
/*  368:     */   
/*  369:     */   public void visitDLOAD(DLOAD o)
/*  370:     */   {
/*  371: 318 */     stack().push(Type.DOUBLE);
/*  372:     */   }
/*  373:     */   
/*  374:     */   public void visitDMUL(DMUL o)
/*  375:     */   {
/*  376: 322 */     stack().pop();
/*  377: 323 */     stack().pop();
/*  378: 324 */     stack().push(Type.DOUBLE);
/*  379:     */   }
/*  380:     */   
/*  381:     */   public void visitDNEG(DNEG o)
/*  382:     */   {
/*  383: 328 */     stack().pop();
/*  384: 329 */     stack().push(Type.DOUBLE);
/*  385:     */   }
/*  386:     */   
/*  387:     */   public void visitDREM(DREM o)
/*  388:     */   {
/*  389: 333 */     stack().pop();
/*  390: 334 */     stack().pop();
/*  391: 335 */     stack().push(Type.DOUBLE);
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void visitDRETURN(DRETURN o)
/*  395:     */   {
/*  396: 339 */     stack().pop();
/*  397:     */   }
/*  398:     */   
/*  399:     */   public void visitDSTORE(DSTORE o)
/*  400:     */   {
/*  401: 343 */     locals().set(o.getIndex(), stack().pop());
/*  402: 344 */     locals().set(o.getIndex() + 1, Type.UNKNOWN);
/*  403:     */   }
/*  404:     */   
/*  405:     */   public void visitDSUB(DSUB o)
/*  406:     */   {
/*  407: 348 */     stack().pop();
/*  408: 349 */     stack().pop();
/*  409: 350 */     stack().push(Type.DOUBLE);
/*  410:     */   }
/*  411:     */   
/*  412:     */   public void visitDUP(DUP o)
/*  413:     */   {
/*  414: 354 */     Type t = stack().pop();
/*  415: 355 */     stack().push(t);
/*  416: 356 */     stack().push(t);
/*  417:     */   }
/*  418:     */   
/*  419:     */   public void visitDUP_X1(DUP_X1 o)
/*  420:     */   {
/*  421: 360 */     Type w1 = stack().pop();
/*  422: 361 */     Type w2 = stack().pop();
/*  423: 362 */     stack().push(w1);
/*  424: 363 */     stack().push(w2);
/*  425: 364 */     stack().push(w1);
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void visitDUP_X2(DUP_X2 o)
/*  429:     */   {
/*  430: 368 */     Type w1 = stack().pop();
/*  431: 369 */     Type w2 = stack().pop();
/*  432: 370 */     if (w2.getSize() == 2)
/*  433:     */     {
/*  434: 371 */       stack().push(w1);
/*  435: 372 */       stack().push(w2);
/*  436: 373 */       stack().push(w1);
/*  437:     */     }
/*  438:     */     else
/*  439:     */     {
/*  440: 376 */       Type w3 = stack().pop();
/*  441: 377 */       stack().push(w1);
/*  442: 378 */       stack().push(w3);
/*  443: 379 */       stack().push(w2);
/*  444: 380 */       stack().push(w1);
/*  445:     */     }
/*  446:     */   }
/*  447:     */   
/*  448:     */   public void visitDUP2(DUP2 o)
/*  449:     */   {
/*  450: 385 */     Type t = stack().pop();
/*  451: 386 */     if (t.getSize() == 2)
/*  452:     */     {
/*  453: 387 */       stack().push(t);
/*  454: 388 */       stack().push(t);
/*  455:     */     }
/*  456:     */     else
/*  457:     */     {
/*  458: 391 */       Type u = stack().pop();
/*  459: 392 */       stack().push(u);
/*  460: 393 */       stack().push(t);
/*  461: 394 */       stack().push(u);
/*  462: 395 */       stack().push(t);
/*  463:     */     }
/*  464:     */   }
/*  465:     */   
/*  466:     */   public void visitDUP2_X1(DUP2_X1 o)
/*  467:     */   {
/*  468: 400 */     Type t = stack().pop();
/*  469: 401 */     if (t.getSize() == 2)
/*  470:     */     {
/*  471: 402 */       Type u = stack().pop();
/*  472: 403 */       stack().push(t);
/*  473: 404 */       stack().push(u);
/*  474: 405 */       stack().push(t);
/*  475:     */     }
/*  476:     */     else
/*  477:     */     {
/*  478: 408 */       Type u = stack().pop();
/*  479: 409 */       Type v = stack().pop();
/*  480: 410 */       stack().push(u);
/*  481: 411 */       stack().push(t);
/*  482: 412 */       stack().push(v);
/*  483: 413 */       stack().push(u);
/*  484: 414 */       stack().push(t);
/*  485:     */     }
/*  486:     */   }
/*  487:     */   
/*  488:     */   public void visitDUP2_X2(DUP2_X2 o)
/*  489:     */   {
/*  490: 419 */     Type t = stack().pop();
/*  491: 420 */     if (t.getSize() == 2)
/*  492:     */     {
/*  493: 421 */       Type u = stack().pop();
/*  494: 422 */       if (u.getSize() == 2)
/*  495:     */       {
/*  496: 423 */         stack().push(t);
/*  497: 424 */         stack().push(u);
/*  498: 425 */         stack().push(t);
/*  499:     */       }
/*  500:     */       else
/*  501:     */       {
/*  502: 427 */         Type v = stack().pop();
/*  503: 428 */         stack().push(t);
/*  504: 429 */         stack().push(v);
/*  505: 430 */         stack().push(u);
/*  506: 431 */         stack().push(t);
/*  507:     */       }
/*  508:     */     }
/*  509:     */     else
/*  510:     */     {
/*  511: 435 */       Type u = stack().pop();
/*  512: 436 */       Type v = stack().pop();
/*  513: 437 */       if (v.getSize() == 2)
/*  514:     */       {
/*  515: 438 */         stack().push(u);
/*  516: 439 */         stack().push(t);
/*  517: 440 */         stack().push(v);
/*  518: 441 */         stack().push(u);
/*  519: 442 */         stack().push(t);
/*  520:     */       }
/*  521:     */       else
/*  522:     */       {
/*  523: 444 */         Type w = stack().pop();
/*  524: 445 */         stack().push(u);
/*  525: 446 */         stack().push(t);
/*  526: 447 */         stack().push(w);
/*  527: 448 */         stack().push(v);
/*  528: 449 */         stack().push(u);
/*  529: 450 */         stack().push(t);
/*  530:     */       }
/*  531:     */     }
/*  532:     */   }
/*  533:     */   
/*  534:     */   public void visitF2D(F2D o)
/*  535:     */   {
/*  536: 456 */     stack().pop();
/*  537: 457 */     stack().push(Type.DOUBLE);
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void visitF2I(F2I o)
/*  541:     */   {
/*  542: 461 */     stack().pop();
/*  543: 462 */     stack().push(Type.INT);
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void visitF2L(F2L o)
/*  547:     */   {
/*  548: 466 */     stack().pop();
/*  549: 467 */     stack().push(Type.LONG);
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void visitFADD(FADD o)
/*  553:     */   {
/*  554: 471 */     stack().pop();
/*  555: 472 */     stack().pop();
/*  556: 473 */     stack().push(Type.FLOAT);
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void visitFALOAD(FALOAD o)
/*  560:     */   {
/*  561: 477 */     stack().pop();
/*  562: 478 */     stack().pop();
/*  563: 479 */     stack().push(Type.FLOAT);
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void visitFASTORE(FASTORE o)
/*  567:     */   {
/*  568: 483 */     stack().pop();
/*  569: 484 */     stack().pop();
/*  570: 485 */     stack().pop();
/*  571:     */   }
/*  572:     */   
/*  573:     */   public void visitFCMPG(FCMPG o)
/*  574:     */   {
/*  575: 489 */     stack().pop();
/*  576: 490 */     stack().pop();
/*  577: 491 */     stack().push(Type.INT);
/*  578:     */   }
/*  579:     */   
/*  580:     */   public void visitFCMPL(FCMPL o)
/*  581:     */   {
/*  582: 495 */     stack().pop();
/*  583: 496 */     stack().pop();
/*  584: 497 */     stack().push(Type.INT);
/*  585:     */   }
/*  586:     */   
/*  587:     */   public void visitFCONST(FCONST o)
/*  588:     */   {
/*  589: 501 */     stack().push(Type.FLOAT);
/*  590:     */   }
/*  591:     */   
/*  592:     */   public void visitFDIV(FDIV o)
/*  593:     */   {
/*  594: 505 */     stack().pop();
/*  595: 506 */     stack().pop();
/*  596: 507 */     stack().push(Type.FLOAT);
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void visitFLOAD(FLOAD o)
/*  600:     */   {
/*  601: 511 */     stack().push(Type.FLOAT);
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void visitFMUL(FMUL o)
/*  605:     */   {
/*  606: 515 */     stack().pop();
/*  607: 516 */     stack().pop();
/*  608: 517 */     stack().push(Type.FLOAT);
/*  609:     */   }
/*  610:     */   
/*  611:     */   public void visitFNEG(FNEG o)
/*  612:     */   {
/*  613: 521 */     stack().pop();
/*  614: 522 */     stack().push(Type.FLOAT);
/*  615:     */   }
/*  616:     */   
/*  617:     */   public void visitFREM(FREM o)
/*  618:     */   {
/*  619: 526 */     stack().pop();
/*  620: 527 */     stack().pop();
/*  621: 528 */     stack().push(Type.FLOAT);
/*  622:     */   }
/*  623:     */   
/*  624:     */   public void visitFRETURN(FRETURN o)
/*  625:     */   {
/*  626: 532 */     stack().pop();
/*  627:     */   }
/*  628:     */   
/*  629:     */   public void visitFSTORE(FSTORE o)
/*  630:     */   {
/*  631: 536 */     locals().set(o.getIndex(), stack().pop());
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void visitFSUB(FSUB o)
/*  635:     */   {
/*  636: 540 */     stack().pop();
/*  637: 541 */     stack().pop();
/*  638: 542 */     stack().push(Type.FLOAT);
/*  639:     */   }
/*  640:     */   
/*  641:     */   public void visitGETFIELD(GETFIELD o)
/*  642:     */   {
/*  643: 546 */     stack().pop();
/*  644: 547 */     Type t = o.getFieldType(this.cpg);
/*  645: 548 */     if ((t.equals(Type.BOOLEAN)) || (t.equals(Type.CHAR)) || (t.equals(Type.BYTE)) || (t.equals(Type.SHORT))) {
/*  646: 552 */       t = Type.INT;
/*  647:     */     }
/*  648: 553 */     stack().push(t);
/*  649:     */   }
/*  650:     */   
/*  651:     */   public void visitGETSTATIC(GETSTATIC o)
/*  652:     */   {
/*  653: 557 */     Type t = o.getFieldType(this.cpg);
/*  654: 558 */     if ((t.equals(Type.BOOLEAN)) || (t.equals(Type.CHAR)) || (t.equals(Type.BYTE)) || (t.equals(Type.SHORT))) {
/*  655: 562 */       t = Type.INT;
/*  656:     */     }
/*  657: 563 */     stack().push(t);
/*  658:     */   }
/*  659:     */   
/*  660:     */   public void visitGOTO(GOTO o) {}
/*  661:     */   
/*  662:     */   public void visitGOTO_W(GOTO_W o) {}
/*  663:     */   
/*  664:     */   public void visitI2B(I2B o)
/*  665:     */   {
/*  666: 575 */     stack().pop();
/*  667: 576 */     stack().push(Type.INT);
/*  668:     */   }
/*  669:     */   
/*  670:     */   public void visitI2C(I2C o)
/*  671:     */   {
/*  672: 580 */     stack().pop();
/*  673: 581 */     stack().push(Type.INT);
/*  674:     */   }
/*  675:     */   
/*  676:     */   public void visitI2D(I2D o)
/*  677:     */   {
/*  678: 585 */     stack().pop();
/*  679: 586 */     stack().push(Type.DOUBLE);
/*  680:     */   }
/*  681:     */   
/*  682:     */   public void visitI2F(I2F o)
/*  683:     */   {
/*  684: 590 */     stack().pop();
/*  685: 591 */     stack().push(Type.FLOAT);
/*  686:     */   }
/*  687:     */   
/*  688:     */   public void visitI2L(I2L o)
/*  689:     */   {
/*  690: 595 */     stack().pop();
/*  691: 596 */     stack().push(Type.LONG);
/*  692:     */   }
/*  693:     */   
/*  694:     */   public void visitI2S(I2S o)
/*  695:     */   {
/*  696: 600 */     stack().pop();
/*  697: 601 */     stack().push(Type.INT);
/*  698:     */   }
/*  699:     */   
/*  700:     */   public void visitIADD(IADD o)
/*  701:     */   {
/*  702: 605 */     stack().pop();
/*  703: 606 */     stack().pop();
/*  704: 607 */     stack().push(Type.INT);
/*  705:     */   }
/*  706:     */   
/*  707:     */   public void visitIALOAD(IALOAD o)
/*  708:     */   {
/*  709: 611 */     stack().pop();
/*  710: 612 */     stack().pop();
/*  711: 613 */     stack().push(Type.INT);
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void visitIAND(IAND o)
/*  715:     */   {
/*  716: 617 */     stack().pop();
/*  717: 618 */     stack().pop();
/*  718: 619 */     stack().push(Type.INT);
/*  719:     */   }
/*  720:     */   
/*  721:     */   public void visitIASTORE(IASTORE o)
/*  722:     */   {
/*  723: 623 */     stack().pop();
/*  724: 624 */     stack().pop();
/*  725: 625 */     stack().pop();
/*  726:     */   }
/*  727:     */   
/*  728:     */   public void visitICONST(ICONST o)
/*  729:     */   {
/*  730: 629 */     stack().push(Type.INT);
/*  731:     */   }
/*  732:     */   
/*  733:     */   public void visitIDIV(IDIV o)
/*  734:     */   {
/*  735: 633 */     stack().pop();
/*  736: 634 */     stack().pop();
/*  737: 635 */     stack().push(Type.INT);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void visitIF_ACMPEQ(IF_ACMPEQ o)
/*  741:     */   {
/*  742: 639 */     stack().pop();
/*  743: 640 */     stack().pop();
/*  744:     */   }
/*  745:     */   
/*  746:     */   public void visitIF_ACMPNE(IF_ACMPNE o)
/*  747:     */   {
/*  748: 644 */     stack().pop();
/*  749: 645 */     stack().pop();
/*  750:     */   }
/*  751:     */   
/*  752:     */   public void visitIF_ICMPEQ(IF_ICMPEQ o)
/*  753:     */   {
/*  754: 649 */     stack().pop();
/*  755: 650 */     stack().pop();
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void visitIF_ICMPGE(IF_ICMPGE o)
/*  759:     */   {
/*  760: 654 */     stack().pop();
/*  761: 655 */     stack().pop();
/*  762:     */   }
/*  763:     */   
/*  764:     */   public void visitIF_ICMPGT(IF_ICMPGT o)
/*  765:     */   {
/*  766: 659 */     stack().pop();
/*  767: 660 */     stack().pop();
/*  768:     */   }
/*  769:     */   
/*  770:     */   public void visitIF_ICMPLE(IF_ICMPLE o)
/*  771:     */   {
/*  772: 664 */     stack().pop();
/*  773: 665 */     stack().pop();
/*  774:     */   }
/*  775:     */   
/*  776:     */   public void visitIF_ICMPLT(IF_ICMPLT o)
/*  777:     */   {
/*  778: 669 */     stack().pop();
/*  779: 670 */     stack().pop();
/*  780:     */   }
/*  781:     */   
/*  782:     */   public void visitIF_ICMPNE(IF_ICMPNE o)
/*  783:     */   {
/*  784: 674 */     stack().pop();
/*  785: 675 */     stack().pop();
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void visitIFEQ(IFEQ o)
/*  789:     */   {
/*  790: 679 */     stack().pop();
/*  791:     */   }
/*  792:     */   
/*  793:     */   public void visitIFGE(IFGE o)
/*  794:     */   {
/*  795: 683 */     stack().pop();
/*  796:     */   }
/*  797:     */   
/*  798:     */   public void visitIFGT(IFGT o)
/*  799:     */   {
/*  800: 687 */     stack().pop();
/*  801:     */   }
/*  802:     */   
/*  803:     */   public void visitIFLE(IFLE o)
/*  804:     */   {
/*  805: 691 */     stack().pop();
/*  806:     */   }
/*  807:     */   
/*  808:     */   public void visitIFLT(IFLT o)
/*  809:     */   {
/*  810: 695 */     stack().pop();
/*  811:     */   }
/*  812:     */   
/*  813:     */   public void visitIFNE(IFNE o)
/*  814:     */   {
/*  815: 699 */     stack().pop();
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void visitIFNONNULL(IFNONNULL o)
/*  819:     */   {
/*  820: 703 */     stack().pop();
/*  821:     */   }
/*  822:     */   
/*  823:     */   public void visitIFNULL(IFNULL o)
/*  824:     */   {
/*  825: 707 */     stack().pop();
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void visitIINC(IINC o) {}
/*  829:     */   
/*  830:     */   public void visitILOAD(ILOAD o)
/*  831:     */   {
/*  832: 715 */     stack().push(Type.INT);
/*  833:     */   }
/*  834:     */   
/*  835:     */   public void visitIMUL(IMUL o)
/*  836:     */   {
/*  837: 719 */     stack().pop();
/*  838: 720 */     stack().pop();
/*  839: 721 */     stack().push(Type.INT);
/*  840:     */   }
/*  841:     */   
/*  842:     */   public void visitINEG(INEG o)
/*  843:     */   {
/*  844: 725 */     stack().pop();
/*  845: 726 */     stack().push(Type.INT);
/*  846:     */   }
/*  847:     */   
/*  848:     */   public void visitINSTANCEOF(INSTANCEOF o)
/*  849:     */   {
/*  850: 730 */     stack().pop();
/*  851: 731 */     stack().push(Type.INT);
/*  852:     */   }
/*  853:     */   
/*  854:     */   public void visitINVOKEINTERFACE(INVOKEINTERFACE o)
/*  855:     */   {
/*  856: 735 */     stack().pop();
/*  857: 736 */     for (int i = 0; i < o.getArgumentTypes(this.cpg).length; i++) {
/*  858: 737 */       stack().pop();
/*  859:     */     }
/*  860: 743 */     if (o.getReturnType(this.cpg) != Type.VOID)
/*  861:     */     {
/*  862: 744 */       Type t = o.getReturnType(this.cpg);
/*  863: 745 */       if ((t.equals(Type.BOOLEAN)) || (t.equals(Type.CHAR)) || (t.equals(Type.BYTE)) || (t.equals(Type.SHORT))) {
/*  864: 749 */         t = Type.INT;
/*  865:     */       }
/*  866: 750 */       stack().push(t);
/*  867:     */     }
/*  868:     */   }
/*  869:     */   
/*  870:     */   public void visitINVOKESPECIAL(INVOKESPECIAL o)
/*  871:     */   {
/*  872: 755 */     if (o.getMethodName(this.cpg).equals("<init>"))
/*  873:     */     {
/*  874: 756 */       UninitializedObjectType t = (UninitializedObjectType)stack().peek(o.getArgumentTypes(this.cpg).length);
/*  875: 757 */       if (t == Frame._this) {
/*  876: 758 */         Frame._this = null;
/*  877:     */       }
/*  878: 760 */       stack().initializeObject(t);
/*  879: 761 */       locals().initializeObject(t);
/*  880:     */     }
/*  881: 763 */     stack().pop();
/*  882: 764 */     for (int i = 0; i < o.getArgumentTypes(this.cpg).length; i++) {
/*  883: 765 */       stack().pop();
/*  884:     */     }
/*  885: 771 */     if (o.getReturnType(this.cpg) != Type.VOID)
/*  886:     */     {
/*  887: 772 */       Type t = o.getReturnType(this.cpg);
/*  888: 773 */       if ((t.equals(Type.BOOLEAN)) || (t.equals(Type.CHAR)) || (t.equals(Type.BYTE)) || (t.equals(Type.SHORT))) {
/*  889: 777 */         t = Type.INT;
/*  890:     */       }
/*  891: 778 */       stack().push(t);
/*  892:     */     }
/*  893:     */   }
/*  894:     */   
/*  895:     */   public void visitINVOKESTATIC(INVOKESTATIC o)
/*  896:     */   {
/*  897: 783 */     for (int i = 0; i < o.getArgumentTypes(this.cpg).length; i++) {
/*  898: 784 */       stack().pop();
/*  899:     */     }
/*  900: 790 */     if (o.getReturnType(this.cpg) != Type.VOID)
/*  901:     */     {
/*  902: 791 */       Type t = o.getReturnType(this.cpg);
/*  903: 792 */       if ((t.equals(Type.BOOLEAN)) || (t.equals(Type.CHAR)) || (t.equals(Type.BYTE)) || (t.equals(Type.SHORT))) {
/*  904: 796 */         t = Type.INT;
/*  905:     */       }
/*  906: 797 */       stack().push(t);
/*  907:     */     }
/*  908:     */   }
/*  909:     */   
/*  910:     */   public void visitINVOKEVIRTUAL(INVOKEVIRTUAL o)
/*  911:     */   {
/*  912: 802 */     stack().pop();
/*  913: 803 */     for (int i = 0; i < o.getArgumentTypes(this.cpg).length; i++) {
/*  914: 804 */       stack().pop();
/*  915:     */     }
/*  916: 810 */     if (o.getReturnType(this.cpg) != Type.VOID)
/*  917:     */     {
/*  918: 811 */       Type t = o.getReturnType(this.cpg);
/*  919: 812 */       if ((t.equals(Type.BOOLEAN)) || (t.equals(Type.CHAR)) || (t.equals(Type.BYTE)) || (t.equals(Type.SHORT))) {
/*  920: 816 */         t = Type.INT;
/*  921:     */       }
/*  922: 817 */       stack().push(t);
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   public void visitIOR(IOR o)
/*  927:     */   {
/*  928: 822 */     stack().pop();
/*  929: 823 */     stack().pop();
/*  930: 824 */     stack().push(Type.INT);
/*  931:     */   }
/*  932:     */   
/*  933:     */   public void visitIREM(IREM o)
/*  934:     */   {
/*  935: 828 */     stack().pop();
/*  936: 829 */     stack().pop();
/*  937: 830 */     stack().push(Type.INT);
/*  938:     */   }
/*  939:     */   
/*  940:     */   public void visitIRETURN(IRETURN o)
/*  941:     */   {
/*  942: 834 */     stack().pop();
/*  943:     */   }
/*  944:     */   
/*  945:     */   public void visitISHL(ISHL o)
/*  946:     */   {
/*  947: 838 */     stack().pop();
/*  948: 839 */     stack().pop();
/*  949: 840 */     stack().push(Type.INT);
/*  950:     */   }
/*  951:     */   
/*  952:     */   public void visitISHR(ISHR o)
/*  953:     */   {
/*  954: 844 */     stack().pop();
/*  955: 845 */     stack().pop();
/*  956: 846 */     stack().push(Type.INT);
/*  957:     */   }
/*  958:     */   
/*  959:     */   public void visitISTORE(ISTORE o)
/*  960:     */   {
/*  961: 850 */     locals().set(o.getIndex(), stack().pop());
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void visitISUB(ISUB o)
/*  965:     */   {
/*  966: 854 */     stack().pop();
/*  967: 855 */     stack().pop();
/*  968: 856 */     stack().push(Type.INT);
/*  969:     */   }
/*  970:     */   
/*  971:     */   public void visitIUSHR(IUSHR o)
/*  972:     */   {
/*  973: 860 */     stack().pop();
/*  974: 861 */     stack().pop();
/*  975: 862 */     stack().push(Type.INT);
/*  976:     */   }
/*  977:     */   
/*  978:     */   public void visitIXOR(IXOR o)
/*  979:     */   {
/*  980: 866 */     stack().pop();
/*  981: 867 */     stack().pop();
/*  982: 868 */     stack().push(Type.INT);
/*  983:     */   }
/*  984:     */   
/*  985:     */   public void visitJSR(JSR o)
/*  986:     */   {
/*  987: 873 */     stack().push(new ReturnaddressType(o.physicalSuccessor()));
/*  988:     */   }
/*  989:     */   
/*  990:     */   public void visitJSR_W(JSR_W o)
/*  991:     */   {
/*  992: 879 */     stack().push(new ReturnaddressType(o.physicalSuccessor()));
/*  993:     */   }
/*  994:     */   
/*  995:     */   public void visitL2D(L2D o)
/*  996:     */   {
/*  997: 884 */     stack().pop();
/*  998: 885 */     stack().push(Type.DOUBLE);
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public void visitL2F(L2F o)
/* 1002:     */   {
/* 1003: 889 */     stack().pop();
/* 1004: 890 */     stack().push(Type.FLOAT);
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public void visitL2I(L2I o)
/* 1008:     */   {
/* 1009: 894 */     stack().pop();
/* 1010: 895 */     stack().push(Type.INT);
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public void visitLADD(LADD o)
/* 1014:     */   {
/* 1015: 899 */     stack().pop();
/* 1016: 900 */     stack().pop();
/* 1017: 901 */     stack().push(Type.LONG);
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public void visitLALOAD(LALOAD o)
/* 1021:     */   {
/* 1022: 905 */     stack().pop();
/* 1023: 906 */     stack().pop();
/* 1024: 907 */     stack().push(Type.LONG);
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public void visitLAND(LAND o)
/* 1028:     */   {
/* 1029: 911 */     stack().pop();
/* 1030: 912 */     stack().pop();
/* 1031: 913 */     stack().push(Type.LONG);
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public void visitLASTORE(LASTORE o)
/* 1035:     */   {
/* 1036: 917 */     stack().pop();
/* 1037: 918 */     stack().pop();
/* 1038: 919 */     stack().pop();
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public void visitLCMP(LCMP o)
/* 1042:     */   {
/* 1043: 923 */     stack().pop();
/* 1044: 924 */     stack().pop();
/* 1045: 925 */     stack().push(Type.INT);
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public void visitLCONST(LCONST o)
/* 1049:     */   {
/* 1050: 929 */     stack().push(Type.LONG);
/* 1051:     */   }
/* 1052:     */   
/* 1053:     */   public void visitLDC(LDC o)
/* 1054:     */   {
/* 1055: 933 */     Constant c = this.cpg.getConstant(o.getIndex());
/* 1056: 934 */     if ((c instanceof ConstantInteger)) {
/* 1057: 935 */       stack().push(Type.INT);
/* 1058:     */     }
/* 1059: 937 */     if ((c instanceof ConstantFloat)) {
/* 1060: 938 */       stack().push(Type.FLOAT);
/* 1061:     */     }
/* 1062: 940 */     if ((c instanceof ConstantString)) {
/* 1063: 941 */       stack().push(Type.STRING);
/* 1064:     */     }
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public void visitLDC_W(LDC_W o)
/* 1068:     */   {
/* 1069: 946 */     Constant c = this.cpg.getConstant(o.getIndex());
/* 1070: 947 */     if ((c instanceof ConstantInteger)) {
/* 1071: 948 */       stack().push(Type.INT);
/* 1072:     */     }
/* 1073: 950 */     if ((c instanceof ConstantFloat)) {
/* 1074: 951 */       stack().push(Type.FLOAT);
/* 1075:     */     }
/* 1076: 953 */     if ((c instanceof ConstantString)) {
/* 1077: 954 */       stack().push(Type.STRING);
/* 1078:     */     }
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public void visitLDC2_W(LDC2_W o)
/* 1082:     */   {
/* 1083: 959 */     Constant c = this.cpg.getConstant(o.getIndex());
/* 1084: 960 */     if ((c instanceof ConstantLong)) {
/* 1085: 961 */       stack().push(Type.LONG);
/* 1086:     */     }
/* 1087: 963 */     if ((c instanceof ConstantDouble)) {
/* 1088: 964 */       stack().push(Type.DOUBLE);
/* 1089:     */     }
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public void visitLDIV(LDIV o)
/* 1093:     */   {
/* 1094: 969 */     stack().pop();
/* 1095: 970 */     stack().pop();
/* 1096: 971 */     stack().push(Type.LONG);
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   public void visitLLOAD(LLOAD o)
/* 1100:     */   {
/* 1101: 975 */     stack().push(locals().get(o.getIndex()));
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public void visitLMUL(LMUL o)
/* 1105:     */   {
/* 1106: 979 */     stack().pop();
/* 1107: 980 */     stack().pop();
/* 1108: 981 */     stack().push(Type.LONG);
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public void visitLNEG(LNEG o)
/* 1112:     */   {
/* 1113: 985 */     stack().pop();
/* 1114: 986 */     stack().push(Type.LONG);
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public void visitLOOKUPSWITCH(LOOKUPSWITCH o)
/* 1118:     */   {
/* 1119: 990 */     stack().pop();
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public void visitLOR(LOR o)
/* 1123:     */   {
/* 1124: 994 */     stack().pop();
/* 1125: 995 */     stack().pop();
/* 1126: 996 */     stack().push(Type.LONG);
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public void visitLREM(LREM o)
/* 1130:     */   {
/* 1131:1000 */     stack().pop();
/* 1132:1001 */     stack().pop();
/* 1133:1002 */     stack().push(Type.LONG);
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public void visitLRETURN(LRETURN o)
/* 1137:     */   {
/* 1138:1006 */     stack().pop();
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public void visitLSHL(LSHL o)
/* 1142:     */   {
/* 1143:1010 */     stack().pop();
/* 1144:1011 */     stack().pop();
/* 1145:1012 */     stack().push(Type.LONG);
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public void visitLSHR(LSHR o)
/* 1149:     */   {
/* 1150:1016 */     stack().pop();
/* 1151:1017 */     stack().pop();
/* 1152:1018 */     stack().push(Type.LONG);
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   public void visitLSTORE(LSTORE o)
/* 1156:     */   {
/* 1157:1022 */     locals().set(o.getIndex(), stack().pop());
/* 1158:1023 */     locals().set(o.getIndex() + 1, Type.UNKNOWN);
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   public void visitLSUB(LSUB o)
/* 1162:     */   {
/* 1163:1027 */     stack().pop();
/* 1164:1028 */     stack().pop();
/* 1165:1029 */     stack().push(Type.LONG);
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   public void visitLUSHR(LUSHR o)
/* 1169:     */   {
/* 1170:1033 */     stack().pop();
/* 1171:1034 */     stack().pop();
/* 1172:1035 */     stack().push(Type.LONG);
/* 1173:     */   }
/* 1174:     */   
/* 1175:     */   public void visitLXOR(LXOR o)
/* 1176:     */   {
/* 1177:1039 */     stack().pop();
/* 1178:1040 */     stack().pop();
/* 1179:1041 */     stack().push(Type.LONG);
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   public void visitMONITORENTER(MONITORENTER o)
/* 1183:     */   {
/* 1184:1045 */     stack().pop();
/* 1185:     */   }
/* 1186:     */   
/* 1187:     */   public void visitMONITOREXIT(MONITOREXIT o)
/* 1188:     */   {
/* 1189:1049 */     stack().pop();
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   public void visitMULTIANEWARRAY(MULTIANEWARRAY o)
/* 1193:     */   {
/* 1194:1053 */     for (int i = 0; i < o.getDimensions(); i++) {
/* 1195:1054 */       stack().pop();
/* 1196:     */     }
/* 1197:1056 */     stack().push(o.getType(this.cpg));
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public void visitNEW(NEW o)
/* 1201:     */   {
/* 1202:1060 */     stack().push(new UninitializedObjectType((ObjectType)o.getType(this.cpg)));
/* 1203:     */   }
/* 1204:     */   
/* 1205:     */   public void visitNEWARRAY(NEWARRAY o)
/* 1206:     */   {
/* 1207:1064 */     stack().pop();
/* 1208:1065 */     stack().push(o.getType());
/* 1209:     */   }
/* 1210:     */   
/* 1211:     */   public void visitNOP(NOP o) {}
/* 1212:     */   
/* 1213:     */   public void visitPOP(POP o)
/* 1214:     */   {
/* 1215:1072 */     stack().pop();
/* 1216:     */   }
/* 1217:     */   
/* 1218:     */   public void visitPOP2(POP2 o)
/* 1219:     */   {
/* 1220:1076 */     Type t = stack().pop();
/* 1221:1077 */     if (t.getSize() == 1) {
/* 1222:1078 */       stack().pop();
/* 1223:     */     }
/* 1224:     */   }
/* 1225:     */   
/* 1226:     */   public void visitPUTFIELD(PUTFIELD o)
/* 1227:     */   {
/* 1228:1083 */     stack().pop();
/* 1229:1084 */     stack().pop();
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public void visitPUTSTATIC(PUTSTATIC o)
/* 1233:     */   {
/* 1234:1088 */     stack().pop();
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   public void visitRET(RET o) {}
/* 1238:     */   
/* 1239:     */   public void visitRETURN(RETURN o) {}
/* 1240:     */   
/* 1241:     */   public void visitSALOAD(SALOAD o)
/* 1242:     */   {
/* 1243:1101 */     stack().pop();
/* 1244:1102 */     stack().pop();
/* 1245:1103 */     stack().push(Type.INT);
/* 1246:     */   }
/* 1247:     */   
/* 1248:     */   public void visitSASTORE(SASTORE o)
/* 1249:     */   {
/* 1250:1107 */     stack().pop();
/* 1251:1108 */     stack().pop();
/* 1252:1109 */     stack().pop();
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public void visitSIPUSH(SIPUSH o)
/* 1256:     */   {
/* 1257:1113 */     stack().push(Type.INT);
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   public void visitSWAP(SWAP o)
/* 1261:     */   {
/* 1262:1117 */     Type t = stack().pop();
/* 1263:1118 */     Type u = stack().pop();
/* 1264:1119 */     stack().push(t);
/* 1265:1120 */     stack().push(u);
/* 1266:     */   }
/* 1267:     */   
/* 1268:     */   public void visitTABLESWITCH(TABLESWITCH o)
/* 1269:     */   {
/* 1270:1124 */     stack().pop();
/* 1271:     */   }
/* 1272:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.ExecutionVisitor
 * JD-Core Version:    0.7.0.1
 */