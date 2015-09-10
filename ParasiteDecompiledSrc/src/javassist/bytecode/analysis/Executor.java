/*    1:     */ package javassist.bytecode.analysis;
/*    2:     */ 
/*    3:     */ import javassist.ClassPool;
/*    4:     */ import javassist.CtClass;
/*    5:     */ import javassist.NotFoundException;
/*    6:     */ import javassist.bytecode.BadBytecode;
/*    7:     */ import javassist.bytecode.CodeIterator;
/*    8:     */ import javassist.bytecode.ConstPool;
/*    9:     */ import javassist.bytecode.Descriptor;
/*   10:     */ import javassist.bytecode.MethodInfo;
/*   11:     */ import javassist.bytecode.Opcode;
/*   12:     */ 
/*   13:     */ public class Executor
/*   14:     */   implements Opcode
/*   15:     */ {
/*   16:     */   private final ConstPool constPool;
/*   17:     */   private final ClassPool classPool;
/*   18:     */   private final Type STRING_TYPE;
/*   19:     */   private final Type CLASS_TYPE;
/*   20:     */   private final Type THROWABLE_TYPE;
/*   21:     */   private int lastPos;
/*   22:     */   
/*   23:     */   public Executor(ClassPool classPool, ConstPool constPool)
/*   24:     */   {
/*   25:  41 */     this.constPool = constPool;
/*   26:  42 */     this.classPool = classPool;
/*   27:     */     try
/*   28:     */     {
/*   29:  45 */       this.STRING_TYPE = getType("java.lang.String");
/*   30:  46 */       this.CLASS_TYPE = getType("java.lang.Class");
/*   31:  47 */       this.THROWABLE_TYPE = getType("java.lang.Throwable");
/*   32:     */     }
/*   33:     */     catch (Exception e)
/*   34:     */     {
/*   35:  49 */       throw new RuntimeException(e);
/*   36:     */     }
/*   37:     */   }
/*   38:     */   
/*   39:     */   public void execute(MethodInfo method, int pos, CodeIterator iter, Frame frame, Subroutine subroutine)
/*   40:     */     throws BadBytecode
/*   41:     */   {
/*   42:  67 */     this.lastPos = pos;
/*   43:  68 */     int opcode = iter.byteAt(pos);
/*   44:  72 */     switch (opcode)
/*   45:     */     {
/*   46:     */     case 0: 
/*   47:     */       break;
/*   48:     */     case 1: 
/*   49:  76 */       frame.push(Type.UNINIT);
/*   50:  77 */       break;
/*   51:     */     case 2: 
/*   52:     */     case 3: 
/*   53:     */     case 4: 
/*   54:     */     case 5: 
/*   55:     */     case 6: 
/*   56:     */     case 7: 
/*   57:     */     case 8: 
/*   58:  85 */       frame.push(Type.INTEGER);
/*   59:  86 */       break;
/*   60:     */     case 9: 
/*   61:     */     case 10: 
/*   62:  89 */       frame.push(Type.LONG);
/*   63:  90 */       frame.push(Type.TOP);
/*   64:  91 */       break;
/*   65:     */     case 11: 
/*   66:     */     case 12: 
/*   67:     */     case 13: 
/*   68:  95 */       frame.push(Type.FLOAT);
/*   69:  96 */       break;
/*   70:     */     case 14: 
/*   71:     */     case 15: 
/*   72:  99 */       frame.push(Type.DOUBLE);
/*   73: 100 */       frame.push(Type.TOP);
/*   74: 101 */       break;
/*   75:     */     case 16: 
/*   76:     */     case 17: 
/*   77: 104 */       frame.push(Type.INTEGER);
/*   78: 105 */       break;
/*   79:     */     case 18: 
/*   80: 107 */       evalLDC(iter.byteAt(pos + 1), frame);
/*   81: 108 */       break;
/*   82:     */     case 19: 
/*   83:     */     case 20: 
/*   84: 111 */       evalLDC(iter.u16bitAt(pos + 1), frame);
/*   85: 112 */       break;
/*   86:     */     case 21: 
/*   87: 114 */       evalLoad(Type.INTEGER, iter.byteAt(pos + 1), frame, subroutine);
/*   88: 115 */       break;
/*   89:     */     case 22: 
/*   90: 117 */       evalLoad(Type.LONG, iter.byteAt(pos + 1), frame, subroutine);
/*   91: 118 */       break;
/*   92:     */     case 23: 
/*   93: 120 */       evalLoad(Type.FLOAT, iter.byteAt(pos + 1), frame, subroutine);
/*   94: 121 */       break;
/*   95:     */     case 24: 
/*   96: 123 */       evalLoad(Type.DOUBLE, iter.byteAt(pos + 1), frame, subroutine);
/*   97: 124 */       break;
/*   98:     */     case 25: 
/*   99: 126 */       evalLoad(Type.OBJECT, iter.byteAt(pos + 1), frame, subroutine);
/*  100: 127 */       break;
/*  101:     */     case 26: 
/*  102:     */     case 27: 
/*  103:     */     case 28: 
/*  104:     */     case 29: 
/*  105: 132 */       evalLoad(Type.INTEGER, opcode - 26, frame, subroutine);
/*  106: 133 */       break;
/*  107:     */     case 30: 
/*  108:     */     case 31: 
/*  109:     */     case 32: 
/*  110:     */     case 33: 
/*  111: 138 */       evalLoad(Type.LONG, opcode - 30, frame, subroutine);
/*  112: 139 */       break;
/*  113:     */     case 34: 
/*  114:     */     case 35: 
/*  115:     */     case 36: 
/*  116:     */     case 37: 
/*  117: 144 */       evalLoad(Type.FLOAT, opcode - 34, frame, subroutine);
/*  118: 145 */       break;
/*  119:     */     case 38: 
/*  120:     */     case 39: 
/*  121:     */     case 40: 
/*  122:     */     case 41: 
/*  123: 150 */       evalLoad(Type.DOUBLE, opcode - 38, frame, subroutine);
/*  124: 151 */       break;
/*  125:     */     case 42: 
/*  126:     */     case 43: 
/*  127:     */     case 44: 
/*  128:     */     case 45: 
/*  129: 156 */       evalLoad(Type.OBJECT, opcode - 42, frame, subroutine);
/*  130: 157 */       break;
/*  131:     */     case 46: 
/*  132: 159 */       evalArrayLoad(Type.INTEGER, frame);
/*  133: 160 */       break;
/*  134:     */     case 47: 
/*  135: 162 */       evalArrayLoad(Type.LONG, frame);
/*  136: 163 */       break;
/*  137:     */     case 48: 
/*  138: 165 */       evalArrayLoad(Type.FLOAT, frame);
/*  139: 166 */       break;
/*  140:     */     case 49: 
/*  141: 168 */       evalArrayLoad(Type.DOUBLE, frame);
/*  142: 169 */       break;
/*  143:     */     case 50: 
/*  144: 171 */       evalArrayLoad(Type.OBJECT, frame);
/*  145: 172 */       break;
/*  146:     */     case 51: 
/*  147:     */     case 52: 
/*  148:     */     case 53: 
/*  149: 176 */       evalArrayLoad(Type.INTEGER, frame);
/*  150: 177 */       break;
/*  151:     */     case 54: 
/*  152: 179 */       evalStore(Type.INTEGER, iter.byteAt(pos + 1), frame, subroutine);
/*  153: 180 */       break;
/*  154:     */     case 55: 
/*  155: 182 */       evalStore(Type.LONG, iter.byteAt(pos + 1), frame, subroutine);
/*  156: 183 */       break;
/*  157:     */     case 56: 
/*  158: 185 */       evalStore(Type.FLOAT, iter.byteAt(pos + 1), frame, subroutine);
/*  159: 186 */       break;
/*  160:     */     case 57: 
/*  161: 188 */       evalStore(Type.DOUBLE, iter.byteAt(pos + 1), frame, subroutine);
/*  162: 189 */       break;
/*  163:     */     case 58: 
/*  164: 191 */       evalStore(Type.OBJECT, iter.byteAt(pos + 1), frame, subroutine);
/*  165: 192 */       break;
/*  166:     */     case 59: 
/*  167:     */     case 60: 
/*  168:     */     case 61: 
/*  169:     */     case 62: 
/*  170: 197 */       evalStore(Type.INTEGER, opcode - 59, frame, subroutine);
/*  171: 198 */       break;
/*  172:     */     case 63: 
/*  173:     */     case 64: 
/*  174:     */     case 65: 
/*  175:     */     case 66: 
/*  176: 203 */       evalStore(Type.LONG, opcode - 63, frame, subroutine);
/*  177: 204 */       break;
/*  178:     */     case 67: 
/*  179:     */     case 68: 
/*  180:     */     case 69: 
/*  181:     */     case 70: 
/*  182: 209 */       evalStore(Type.FLOAT, opcode - 67, frame, subroutine);
/*  183: 210 */       break;
/*  184:     */     case 71: 
/*  185:     */     case 72: 
/*  186:     */     case 73: 
/*  187:     */     case 74: 
/*  188: 215 */       evalStore(Type.DOUBLE, opcode - 71, frame, subroutine);
/*  189: 216 */       break;
/*  190:     */     case 75: 
/*  191:     */     case 76: 
/*  192:     */     case 77: 
/*  193:     */     case 78: 
/*  194: 221 */       evalStore(Type.OBJECT, opcode - 75, frame, subroutine);
/*  195: 222 */       break;
/*  196:     */     case 79: 
/*  197: 224 */       evalArrayStore(Type.INTEGER, frame);
/*  198: 225 */       break;
/*  199:     */     case 80: 
/*  200: 227 */       evalArrayStore(Type.LONG, frame);
/*  201: 228 */       break;
/*  202:     */     case 81: 
/*  203: 230 */       evalArrayStore(Type.FLOAT, frame);
/*  204: 231 */       break;
/*  205:     */     case 82: 
/*  206: 233 */       evalArrayStore(Type.DOUBLE, frame);
/*  207: 234 */       break;
/*  208:     */     case 83: 
/*  209: 236 */       evalArrayStore(Type.OBJECT, frame);
/*  210: 237 */       break;
/*  211:     */     case 84: 
/*  212:     */     case 85: 
/*  213:     */     case 86: 
/*  214: 241 */       evalArrayStore(Type.INTEGER, frame);
/*  215: 242 */       break;
/*  216:     */     case 87: 
/*  217: 244 */       if (frame.pop() == Type.TOP) {
/*  218: 245 */         throw new BadBytecode("POP can not be used with a category 2 value, pos = " + pos);
/*  219:     */       }
/*  220:     */       break;
/*  221:     */     case 88: 
/*  222: 248 */       frame.pop();
/*  223: 249 */       frame.pop();
/*  224: 250 */       break;
/*  225:     */     case 89: 
/*  226: 252 */       Type type = frame.peek();
/*  227: 253 */       if (type == Type.TOP) {
/*  228: 254 */         throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + pos);
/*  229:     */       }
/*  230: 256 */       frame.push(frame.peek());
/*  231: 257 */       break;
/*  232:     */     case 90: 
/*  233:     */     case 91: 
/*  234: 261 */       Type type = frame.peek();
/*  235: 262 */       if (type == Type.TOP) {
/*  236: 263 */         throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + pos);
/*  237:     */       }
/*  238: 264 */       int end = frame.getTopIndex();
/*  239: 265 */       int insert = end - (opcode - 90) - 1;
/*  240: 266 */       frame.push(type);
/*  241: 268 */       while (end > insert)
/*  242:     */       {
/*  243: 269 */         frame.setStack(end, frame.getStack(end - 1));
/*  244: 270 */         end--;
/*  245:     */       }
/*  246: 272 */       frame.setStack(insert, type);
/*  247: 273 */       break;
/*  248:     */     case 92: 
/*  249: 276 */       frame.push(frame.getStack(frame.getTopIndex() - 1));
/*  250: 277 */       frame.push(frame.getStack(frame.getTopIndex() - 1));
/*  251: 278 */       break;
/*  252:     */     case 93: 
/*  253:     */     case 94: 
/*  254: 281 */       int end = frame.getTopIndex();
/*  255: 282 */       int insert = end - (opcode - 93) - 1;
/*  256: 283 */       Type type1 = frame.getStack(frame.getTopIndex() - 1);
/*  257: 284 */       Type type2 = frame.peek();
/*  258: 285 */       frame.push(type1);
/*  259: 286 */       frame.push(type2);
/*  260: 287 */       while (end > insert)
/*  261:     */       {
/*  262: 288 */         frame.setStack(end, frame.getStack(end - 2));
/*  263: 289 */         end--;
/*  264:     */       }
/*  265: 291 */       frame.setStack(insert, type2);
/*  266: 292 */       frame.setStack(insert - 1, type1);
/*  267: 293 */       break;
/*  268:     */     case 95: 
/*  269: 296 */       Type type1 = frame.pop();
/*  270: 297 */       Type type2 = frame.pop();
/*  271: 298 */       if ((type1.getSize() == 2) || (type2.getSize() == 2)) {
/*  272: 299 */         throw new BadBytecode("Swap can not be used with category 2 values, pos = " + pos);
/*  273:     */       }
/*  274: 300 */       frame.push(type1);
/*  275: 301 */       frame.push(type2);
/*  276: 302 */       break;
/*  277:     */     case 96: 
/*  278: 307 */       evalBinaryMath(Type.INTEGER, frame);
/*  279: 308 */       break;
/*  280:     */     case 97: 
/*  281: 310 */       evalBinaryMath(Type.LONG, frame);
/*  282: 311 */       break;
/*  283:     */     case 98: 
/*  284: 313 */       evalBinaryMath(Type.FLOAT, frame);
/*  285: 314 */       break;
/*  286:     */     case 99: 
/*  287: 316 */       evalBinaryMath(Type.DOUBLE, frame);
/*  288: 317 */       break;
/*  289:     */     case 100: 
/*  290: 319 */       evalBinaryMath(Type.INTEGER, frame);
/*  291: 320 */       break;
/*  292:     */     case 101: 
/*  293: 322 */       evalBinaryMath(Type.LONG, frame);
/*  294: 323 */       break;
/*  295:     */     case 102: 
/*  296: 325 */       evalBinaryMath(Type.FLOAT, frame);
/*  297: 326 */       break;
/*  298:     */     case 103: 
/*  299: 328 */       evalBinaryMath(Type.DOUBLE, frame);
/*  300: 329 */       break;
/*  301:     */     case 104: 
/*  302: 331 */       evalBinaryMath(Type.INTEGER, frame);
/*  303: 332 */       break;
/*  304:     */     case 105: 
/*  305: 334 */       evalBinaryMath(Type.LONG, frame);
/*  306: 335 */       break;
/*  307:     */     case 106: 
/*  308: 337 */       evalBinaryMath(Type.FLOAT, frame);
/*  309: 338 */       break;
/*  310:     */     case 107: 
/*  311: 340 */       evalBinaryMath(Type.DOUBLE, frame);
/*  312: 341 */       break;
/*  313:     */     case 108: 
/*  314: 343 */       evalBinaryMath(Type.INTEGER, frame);
/*  315: 344 */       break;
/*  316:     */     case 109: 
/*  317: 346 */       evalBinaryMath(Type.LONG, frame);
/*  318: 347 */       break;
/*  319:     */     case 110: 
/*  320: 349 */       evalBinaryMath(Type.FLOAT, frame);
/*  321: 350 */       break;
/*  322:     */     case 111: 
/*  323: 352 */       evalBinaryMath(Type.DOUBLE, frame);
/*  324: 353 */       break;
/*  325:     */     case 112: 
/*  326: 355 */       evalBinaryMath(Type.INTEGER, frame);
/*  327: 356 */       break;
/*  328:     */     case 113: 
/*  329: 358 */       evalBinaryMath(Type.LONG, frame);
/*  330: 359 */       break;
/*  331:     */     case 114: 
/*  332: 361 */       evalBinaryMath(Type.FLOAT, frame);
/*  333: 362 */       break;
/*  334:     */     case 115: 
/*  335: 364 */       evalBinaryMath(Type.DOUBLE, frame);
/*  336: 365 */       break;
/*  337:     */     case 116: 
/*  338: 369 */       verifyAssignable(Type.INTEGER, simplePeek(frame));
/*  339: 370 */       break;
/*  340:     */     case 117: 
/*  341: 372 */       verifyAssignable(Type.LONG, simplePeek(frame));
/*  342: 373 */       break;
/*  343:     */     case 118: 
/*  344: 375 */       verifyAssignable(Type.FLOAT, simplePeek(frame));
/*  345: 376 */       break;
/*  346:     */     case 119: 
/*  347: 378 */       verifyAssignable(Type.DOUBLE, simplePeek(frame));
/*  348: 379 */       break;
/*  349:     */     case 120: 
/*  350: 383 */       evalShift(Type.INTEGER, frame);
/*  351: 384 */       break;
/*  352:     */     case 121: 
/*  353: 386 */       evalShift(Type.LONG, frame);
/*  354: 387 */       break;
/*  355:     */     case 122: 
/*  356: 389 */       evalShift(Type.INTEGER, frame);
/*  357: 390 */       break;
/*  358:     */     case 123: 
/*  359: 392 */       evalShift(Type.LONG, frame);
/*  360: 393 */       break;
/*  361:     */     case 124: 
/*  362: 395 */       evalShift(Type.INTEGER, frame);
/*  363: 396 */       break;
/*  364:     */     case 125: 
/*  365: 398 */       evalShift(Type.LONG, frame);
/*  366: 399 */       break;
/*  367:     */     case 126: 
/*  368: 403 */       evalBinaryMath(Type.INTEGER, frame);
/*  369: 404 */       break;
/*  370:     */     case 127: 
/*  371: 406 */       evalBinaryMath(Type.LONG, frame);
/*  372: 407 */       break;
/*  373:     */     case 128: 
/*  374: 409 */       evalBinaryMath(Type.INTEGER, frame);
/*  375: 410 */       break;
/*  376:     */     case 129: 
/*  377: 412 */       evalBinaryMath(Type.LONG, frame);
/*  378: 413 */       break;
/*  379:     */     case 130: 
/*  380: 415 */       evalBinaryMath(Type.INTEGER, frame);
/*  381: 416 */       break;
/*  382:     */     case 131: 
/*  383: 418 */       evalBinaryMath(Type.LONG, frame);
/*  384: 419 */       break;
/*  385:     */     case 132: 
/*  386: 422 */       int index = iter.byteAt(pos + 1);
/*  387: 423 */       verifyAssignable(Type.INTEGER, frame.getLocal(index));
/*  388: 424 */       access(index, Type.INTEGER, subroutine);
/*  389: 425 */       break;
/*  390:     */     case 133: 
/*  391: 430 */       verifyAssignable(Type.INTEGER, simplePop(frame));
/*  392: 431 */       simplePush(Type.LONG, frame);
/*  393: 432 */       break;
/*  394:     */     case 134: 
/*  395: 434 */       verifyAssignable(Type.INTEGER, simplePop(frame));
/*  396: 435 */       simplePush(Type.FLOAT, frame);
/*  397: 436 */       break;
/*  398:     */     case 135: 
/*  399: 438 */       verifyAssignable(Type.INTEGER, simplePop(frame));
/*  400: 439 */       simplePush(Type.DOUBLE, frame);
/*  401: 440 */       break;
/*  402:     */     case 136: 
/*  403: 442 */       verifyAssignable(Type.LONG, simplePop(frame));
/*  404: 443 */       simplePush(Type.INTEGER, frame);
/*  405: 444 */       break;
/*  406:     */     case 137: 
/*  407: 446 */       verifyAssignable(Type.LONG, simplePop(frame));
/*  408: 447 */       simplePush(Type.FLOAT, frame);
/*  409: 448 */       break;
/*  410:     */     case 138: 
/*  411: 450 */       verifyAssignable(Type.LONG, simplePop(frame));
/*  412: 451 */       simplePush(Type.DOUBLE, frame);
/*  413: 452 */       break;
/*  414:     */     case 139: 
/*  415: 454 */       verifyAssignable(Type.FLOAT, simplePop(frame));
/*  416: 455 */       simplePush(Type.INTEGER, frame);
/*  417: 456 */       break;
/*  418:     */     case 140: 
/*  419: 458 */       verifyAssignable(Type.FLOAT, simplePop(frame));
/*  420: 459 */       simplePush(Type.LONG, frame);
/*  421: 460 */       break;
/*  422:     */     case 141: 
/*  423: 462 */       verifyAssignable(Type.FLOAT, simplePop(frame));
/*  424: 463 */       simplePush(Type.DOUBLE, frame);
/*  425: 464 */       break;
/*  426:     */     case 142: 
/*  427: 466 */       verifyAssignable(Type.DOUBLE, simplePop(frame));
/*  428: 467 */       simplePush(Type.INTEGER, frame);
/*  429: 468 */       break;
/*  430:     */     case 143: 
/*  431: 470 */       verifyAssignable(Type.DOUBLE, simplePop(frame));
/*  432: 471 */       simplePush(Type.LONG, frame);
/*  433: 472 */       break;
/*  434:     */     case 144: 
/*  435: 474 */       verifyAssignable(Type.DOUBLE, simplePop(frame));
/*  436: 475 */       simplePush(Type.FLOAT, frame);
/*  437: 476 */       break;
/*  438:     */     case 145: 
/*  439:     */     case 146: 
/*  440:     */     case 147: 
/*  441: 480 */       verifyAssignable(Type.INTEGER, frame.peek());
/*  442: 481 */       break;
/*  443:     */     case 148: 
/*  444: 483 */       verifyAssignable(Type.LONG, simplePop(frame));
/*  445: 484 */       verifyAssignable(Type.LONG, simplePop(frame));
/*  446: 485 */       frame.push(Type.INTEGER);
/*  447: 486 */       break;
/*  448:     */     case 149: 
/*  449:     */     case 150: 
/*  450: 489 */       verifyAssignable(Type.FLOAT, simplePop(frame));
/*  451: 490 */       verifyAssignable(Type.FLOAT, simplePop(frame));
/*  452: 491 */       frame.push(Type.INTEGER);
/*  453: 492 */       break;
/*  454:     */     case 151: 
/*  455:     */     case 152: 
/*  456: 495 */       verifyAssignable(Type.DOUBLE, simplePop(frame));
/*  457: 496 */       verifyAssignable(Type.DOUBLE, simplePop(frame));
/*  458: 497 */       frame.push(Type.INTEGER);
/*  459: 498 */       break;
/*  460:     */     case 153: 
/*  461:     */     case 154: 
/*  462:     */     case 155: 
/*  463:     */     case 156: 
/*  464:     */     case 157: 
/*  465:     */     case 158: 
/*  466: 507 */       verifyAssignable(Type.INTEGER, simplePop(frame));
/*  467: 508 */       break;
/*  468:     */     case 159: 
/*  469:     */     case 160: 
/*  470:     */     case 161: 
/*  471:     */     case 162: 
/*  472:     */     case 163: 
/*  473:     */     case 164: 
/*  474: 515 */       verifyAssignable(Type.INTEGER, simplePop(frame));
/*  475: 516 */       verifyAssignable(Type.INTEGER, simplePop(frame));
/*  476: 517 */       break;
/*  477:     */     case 165: 
/*  478:     */     case 166: 
/*  479: 520 */       verifyAssignable(Type.OBJECT, simplePop(frame));
/*  480: 521 */       verifyAssignable(Type.OBJECT, simplePop(frame));
/*  481: 522 */       break;
/*  482:     */     case 167: 
/*  483:     */       break;
/*  484:     */     case 168: 
/*  485: 526 */       frame.push(Type.RETURN_ADDRESS);
/*  486: 527 */       break;
/*  487:     */     case 169: 
/*  488: 529 */       verifyAssignable(Type.RETURN_ADDRESS, frame.getLocal(iter.byteAt(pos + 1)));
/*  489: 530 */       break;
/*  490:     */     case 170: 
/*  491:     */     case 171: 
/*  492:     */     case 172: 
/*  493: 534 */       verifyAssignable(Type.INTEGER, simplePop(frame));
/*  494: 535 */       break;
/*  495:     */     case 173: 
/*  496: 537 */       verifyAssignable(Type.LONG, simplePop(frame));
/*  497: 538 */       break;
/*  498:     */     case 174: 
/*  499: 540 */       verifyAssignable(Type.FLOAT, simplePop(frame));
/*  500: 541 */       break;
/*  501:     */     case 175: 
/*  502: 543 */       verifyAssignable(Type.DOUBLE, simplePop(frame));
/*  503: 544 */       break;
/*  504:     */     case 176: 
/*  505:     */       try
/*  506:     */       {
/*  507: 547 */         CtClass returnType = Descriptor.getReturnType(method.getDescriptor(), this.classPool);
/*  508: 548 */         verifyAssignable(Type.get(returnType), simplePop(frame));
/*  509:     */       }
/*  510:     */       catch (NotFoundException e)
/*  511:     */       {
/*  512: 550 */         throw new RuntimeException(e);
/*  513:     */       }
/*  514:     */     case 177: 
/*  515:     */       break;
/*  516:     */     case 178: 
/*  517: 556 */       evalGetField(opcode, iter.u16bitAt(pos + 1), frame);
/*  518: 557 */       break;
/*  519:     */     case 179: 
/*  520: 559 */       evalPutField(opcode, iter.u16bitAt(pos + 1), frame);
/*  521: 560 */       break;
/*  522:     */     case 180: 
/*  523: 562 */       evalGetField(opcode, iter.u16bitAt(pos + 1), frame);
/*  524: 563 */       break;
/*  525:     */     case 181: 
/*  526: 565 */       evalPutField(opcode, iter.u16bitAt(pos + 1), frame);
/*  527: 566 */       break;
/*  528:     */     case 182: 
/*  529:     */     case 183: 
/*  530:     */     case 184: 
/*  531: 570 */       evalInvokeMethod(opcode, iter.u16bitAt(pos + 1), frame);
/*  532: 571 */       break;
/*  533:     */     case 185: 
/*  534: 573 */       evalInvokeIntfMethod(opcode, iter.u16bitAt(pos + 1), frame);
/*  535: 574 */       break;
/*  536:     */     case 186: 
/*  537: 576 */       throw new RuntimeException("Bad opcode 186");
/*  538:     */     case 187: 
/*  539: 578 */       frame.push(resolveClassInfo(this.constPool.getClassInfo(iter.u16bitAt(pos + 1))));
/*  540: 579 */       break;
/*  541:     */     case 188: 
/*  542: 581 */       evalNewArray(pos, iter, frame);
/*  543: 582 */       break;
/*  544:     */     case 189: 
/*  545: 584 */       evalNewObjectArray(pos, iter, frame);
/*  546: 585 */       break;
/*  547:     */     case 190: 
/*  548: 587 */       Type array = simplePop(frame);
/*  549: 588 */       if ((!array.isArray()) && (array != Type.UNINIT)) {
/*  550: 589 */         throw new BadBytecode("Array length passed a non-array [pos = " + pos + "]: " + array);
/*  551:     */       }
/*  552: 590 */       frame.push(Type.INTEGER);
/*  553: 591 */       break;
/*  554:     */     case 191: 
/*  555: 594 */       verifyAssignable(this.THROWABLE_TYPE, simplePop(frame));
/*  556: 595 */       break;
/*  557:     */     case 192: 
/*  558: 597 */       verifyAssignable(Type.OBJECT, simplePop(frame));
/*  559: 598 */       frame.push(typeFromDesc(this.constPool.getClassInfo(iter.u16bitAt(pos + 1))));
/*  560: 599 */       break;
/*  561:     */     case 193: 
/*  562: 601 */       verifyAssignable(Type.OBJECT, simplePop(frame));
/*  563: 602 */       frame.push(Type.INTEGER);
/*  564: 603 */       break;
/*  565:     */     case 194: 
/*  566:     */     case 195: 
/*  567: 606 */       verifyAssignable(Type.OBJECT, simplePop(frame));
/*  568: 607 */       break;
/*  569:     */     case 196: 
/*  570: 609 */       evalWide(pos, iter, frame, subroutine);
/*  571: 610 */       break;
/*  572:     */     case 197: 
/*  573: 612 */       evalNewObjectArray(pos, iter, frame);
/*  574: 613 */       break;
/*  575:     */     case 198: 
/*  576:     */     case 199: 
/*  577: 616 */       verifyAssignable(Type.OBJECT, simplePop(frame));
/*  578: 617 */       break;
/*  579:     */     case 200: 
/*  580:     */       break;
/*  581:     */     case 201: 
/*  582: 621 */       frame.push(Type.RETURN_ADDRESS);
/*  583:     */     }
/*  584:     */   }
/*  585:     */   
/*  586:     */   private Type zeroExtend(Type type)
/*  587:     */   {
/*  588: 627 */     if ((type == Type.SHORT) || (type == Type.BYTE) || (type == Type.CHAR) || (type == Type.BOOLEAN)) {
/*  589: 628 */       return Type.INTEGER;
/*  590:     */     }
/*  591: 630 */     return type;
/*  592:     */   }
/*  593:     */   
/*  594:     */   private void evalArrayLoad(Type expectedComponent, Frame frame)
/*  595:     */     throws BadBytecode
/*  596:     */   {
/*  597: 634 */     Type index = frame.pop();
/*  598: 635 */     Type array = frame.pop();
/*  599: 639 */     if (array == Type.UNINIT)
/*  600:     */     {
/*  601: 640 */       verifyAssignable(Type.INTEGER, index);
/*  602: 641 */       if (expectedComponent == Type.OBJECT) {
/*  603: 642 */         simplePush(Type.UNINIT, frame);
/*  604:     */       } else {
/*  605: 644 */         simplePush(expectedComponent, frame);
/*  606:     */       }
/*  607: 646 */       return;
/*  608:     */     }
/*  609: 649 */     Type component = array.getComponent();
/*  610: 651 */     if (component == null) {
/*  611: 652 */       throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + component);
/*  612:     */     }
/*  613: 654 */     component = zeroExtend(component);
/*  614:     */     
/*  615: 656 */     verifyAssignable(expectedComponent, component);
/*  616: 657 */     verifyAssignable(Type.INTEGER, index);
/*  617: 658 */     simplePush(component, frame);
/*  618:     */   }
/*  619:     */   
/*  620:     */   private void evalArrayStore(Type expectedComponent, Frame frame)
/*  621:     */     throws BadBytecode
/*  622:     */   {
/*  623: 662 */     Type value = simplePop(frame);
/*  624: 663 */     Type index = frame.pop();
/*  625: 664 */     Type array = frame.pop();
/*  626: 666 */     if (array == Type.UNINIT)
/*  627:     */     {
/*  628: 667 */       verifyAssignable(Type.INTEGER, index);
/*  629: 668 */       return;
/*  630:     */     }
/*  631: 671 */     Type component = array.getComponent();
/*  632: 673 */     if (component == null) {
/*  633: 674 */       throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + component);
/*  634:     */     }
/*  635: 676 */     component = zeroExtend(component);
/*  636:     */     
/*  637: 678 */     verifyAssignable(expectedComponent, component);
/*  638: 679 */     verifyAssignable(Type.INTEGER, index);
/*  639: 687 */     if (expectedComponent == Type.OBJECT) {
/*  640: 688 */       verifyAssignable(expectedComponent, value);
/*  641:     */     } else {
/*  642: 690 */       verifyAssignable(component, value);
/*  643:     */     }
/*  644:     */   }
/*  645:     */   
/*  646:     */   private void evalBinaryMath(Type expected, Frame frame)
/*  647:     */     throws BadBytecode
/*  648:     */   {
/*  649: 695 */     Type value2 = simplePop(frame);
/*  650: 696 */     Type value1 = simplePop(frame);
/*  651:     */     
/*  652: 698 */     verifyAssignable(expected, value2);
/*  653: 699 */     verifyAssignable(expected, value1);
/*  654: 700 */     simplePush(value1, frame);
/*  655:     */   }
/*  656:     */   
/*  657:     */   private void evalGetField(int opcode, int index, Frame frame)
/*  658:     */     throws BadBytecode
/*  659:     */   {
/*  660: 704 */     String desc = this.constPool.getFieldrefType(index);
/*  661: 705 */     Type type = zeroExtend(typeFromDesc(desc));
/*  662: 707 */     if (opcode == 180)
/*  663:     */     {
/*  664: 708 */       Type objectType = resolveClassInfo(this.constPool.getFieldrefClassName(index));
/*  665: 709 */       verifyAssignable(objectType, simplePop(frame));
/*  666:     */     }
/*  667: 712 */     simplePush(type, frame);
/*  668:     */   }
/*  669:     */   
/*  670:     */   private void evalInvokeIntfMethod(int opcode, int index, Frame frame)
/*  671:     */     throws BadBytecode
/*  672:     */   {
/*  673: 716 */     String desc = this.constPool.getInterfaceMethodrefType(index);
/*  674: 717 */     Type[] types = paramTypesFromDesc(desc);
/*  675: 718 */     int i = types.length;
/*  676: 720 */     while (i > 0) {
/*  677: 721 */       verifyAssignable(zeroExtend(types[(--i)]), simplePop(frame));
/*  678:     */     }
/*  679: 723 */     String classInfo = this.constPool.getInterfaceMethodrefClassName(index);
/*  680: 724 */     Type objectType = resolveClassInfo(classInfo);
/*  681: 725 */     verifyAssignable(objectType, simplePop(frame));
/*  682:     */     
/*  683: 727 */     Type returnType = returnTypeFromDesc(desc);
/*  684: 728 */     if (returnType != Type.VOID) {
/*  685: 729 */       simplePush(zeroExtend(returnType), frame);
/*  686:     */     }
/*  687:     */   }
/*  688:     */   
/*  689:     */   private void evalInvokeMethod(int opcode, int index, Frame frame)
/*  690:     */     throws BadBytecode
/*  691:     */   {
/*  692: 733 */     String desc = this.constPool.getMethodrefType(index);
/*  693: 734 */     Type[] types = paramTypesFromDesc(desc);
/*  694: 735 */     int i = types.length;
/*  695: 737 */     while (i > 0) {
/*  696: 738 */       verifyAssignable(zeroExtend(types[(--i)]), simplePop(frame));
/*  697:     */     }
/*  698: 740 */     if (opcode != 184)
/*  699:     */     {
/*  700: 741 */       Type objectType = resolveClassInfo(this.constPool.getMethodrefClassName(index));
/*  701: 742 */       verifyAssignable(objectType, simplePop(frame));
/*  702:     */     }
/*  703: 745 */     Type returnType = returnTypeFromDesc(desc);
/*  704: 746 */     if (returnType != Type.VOID) {
/*  705: 747 */       simplePush(zeroExtend(returnType), frame);
/*  706:     */     }
/*  707:     */   }
/*  708:     */   
/*  709:     */   private void evalLDC(int index, Frame frame)
/*  710:     */     throws BadBytecode
/*  711:     */   {
/*  712: 752 */     int tag = this.constPool.getTag(index);
/*  713:     */     Type type;
/*  714: 754 */     switch (tag)
/*  715:     */     {
/*  716:     */     case 8: 
/*  717: 756 */       type = this.STRING_TYPE;
/*  718: 757 */       break;
/*  719:     */     case 3: 
/*  720: 759 */       type = Type.INTEGER;
/*  721: 760 */       break;
/*  722:     */     case 4: 
/*  723: 762 */       type = Type.FLOAT;
/*  724: 763 */       break;
/*  725:     */     case 5: 
/*  726: 765 */       type = Type.LONG;
/*  727: 766 */       break;
/*  728:     */     case 6: 
/*  729: 768 */       type = Type.DOUBLE;
/*  730: 769 */       break;
/*  731:     */     case 7: 
/*  732: 771 */       type = this.CLASS_TYPE;
/*  733: 772 */       break;
/*  734:     */     default: 
/*  735: 774 */       throw new BadBytecode("bad LDC [pos = " + this.lastPos + "]: " + tag);
/*  736:     */     }
/*  737: 777 */     simplePush(type, frame);
/*  738:     */   }
/*  739:     */   
/*  740:     */   private void evalLoad(Type expected, int index, Frame frame, Subroutine subroutine)
/*  741:     */     throws BadBytecode
/*  742:     */   {
/*  743: 781 */     Type type = frame.getLocal(index);
/*  744:     */     
/*  745: 783 */     verifyAssignable(expected, type);
/*  746:     */     
/*  747: 785 */     simplePush(type, frame);
/*  748: 786 */     access(index, type, subroutine);
/*  749:     */   }
/*  750:     */   
/*  751:     */   private void evalNewArray(int pos, CodeIterator iter, Frame frame)
/*  752:     */     throws BadBytecode
/*  753:     */   {
/*  754: 790 */     verifyAssignable(Type.INTEGER, simplePop(frame));
/*  755: 791 */     Type type = null;
/*  756: 792 */     int typeInfo = iter.byteAt(pos + 1);
/*  757: 793 */     switch (typeInfo)
/*  758:     */     {
/*  759:     */     case 4: 
/*  760: 795 */       type = getType("boolean[]");
/*  761: 796 */       break;
/*  762:     */     case 5: 
/*  763: 798 */       type = getType("char[]");
/*  764: 799 */       break;
/*  765:     */     case 8: 
/*  766: 801 */       type = getType("byte[]");
/*  767: 802 */       break;
/*  768:     */     case 9: 
/*  769: 804 */       type = getType("short[]");
/*  770: 805 */       break;
/*  771:     */     case 10: 
/*  772: 807 */       type = getType("int[]");
/*  773: 808 */       break;
/*  774:     */     case 11: 
/*  775: 810 */       type = getType("long[]");
/*  776: 811 */       break;
/*  777:     */     case 6: 
/*  778: 813 */       type = getType("float[]");
/*  779: 814 */       break;
/*  780:     */     case 7: 
/*  781: 816 */       type = getType("double[]");
/*  782: 817 */       break;
/*  783:     */     default: 
/*  784: 819 */       throw new BadBytecode("Invalid array type [pos = " + pos + "]: " + typeInfo);
/*  785:     */     }
/*  786: 823 */     frame.push(type);
/*  787:     */   }
/*  788:     */   
/*  789:     */   private void evalNewObjectArray(int pos, CodeIterator iter, Frame frame)
/*  790:     */     throws BadBytecode
/*  791:     */   {
/*  792: 828 */     Type type = resolveClassInfo(this.constPool.getClassInfo(iter.u16bitAt(pos + 1)));
/*  793: 829 */     String name = type.getCtClass().getName();
/*  794: 830 */     int opcode = iter.byteAt(pos);
/*  795:     */     int dimensions;
/*  796:     */     int dimensions;
/*  797: 833 */     if (opcode == 197)
/*  798:     */     {
/*  799: 834 */       dimensions = iter.byteAt(pos + 3);
/*  800:     */     }
/*  801:     */     else
/*  802:     */     {
/*  803: 836 */       name = name + "[]";
/*  804: 837 */       dimensions = 1;
/*  805:     */     }
/*  806: 840 */     while (dimensions-- > 0) {
/*  807: 841 */       verifyAssignable(Type.INTEGER, simplePop(frame));
/*  808:     */     }
/*  809: 844 */     simplePush(getType(name), frame);
/*  810:     */   }
/*  811:     */   
/*  812:     */   private void evalPutField(int opcode, int index, Frame frame)
/*  813:     */     throws BadBytecode
/*  814:     */   {
/*  815: 848 */     String desc = this.constPool.getFieldrefType(index);
/*  816: 849 */     Type type = zeroExtend(typeFromDesc(desc));
/*  817:     */     
/*  818: 851 */     verifyAssignable(type, simplePop(frame));
/*  819: 853 */     if (opcode == 181)
/*  820:     */     {
/*  821: 854 */       Type objectType = resolveClassInfo(this.constPool.getFieldrefClassName(index));
/*  822: 855 */       verifyAssignable(objectType, simplePop(frame));
/*  823:     */     }
/*  824:     */   }
/*  825:     */   
/*  826:     */   private void evalShift(Type expected, Frame frame)
/*  827:     */     throws BadBytecode
/*  828:     */   {
/*  829: 860 */     Type value2 = simplePop(frame);
/*  830: 861 */     Type value1 = simplePop(frame);
/*  831:     */     
/*  832: 863 */     verifyAssignable(Type.INTEGER, value2);
/*  833: 864 */     verifyAssignable(expected, value1);
/*  834: 865 */     simplePush(value1, frame);
/*  835:     */   }
/*  836:     */   
/*  837:     */   private void evalStore(Type expected, int index, Frame frame, Subroutine subroutine)
/*  838:     */     throws BadBytecode
/*  839:     */   {
/*  840: 869 */     Type type = simplePop(frame);
/*  841: 872 */     if ((expected != Type.OBJECT) || (type != Type.RETURN_ADDRESS)) {
/*  842: 873 */       verifyAssignable(expected, type);
/*  843:     */     }
/*  844: 874 */     simpleSetLocal(index, type, frame);
/*  845: 875 */     access(index, type, subroutine);
/*  846:     */   }
/*  847:     */   
/*  848:     */   private void evalWide(int pos, CodeIterator iter, Frame frame, Subroutine subroutine)
/*  849:     */     throws BadBytecode
/*  850:     */   {
/*  851: 879 */     int opcode = iter.byteAt(pos + 1);
/*  852: 880 */     int index = iter.u16bitAt(pos + 2);
/*  853: 881 */     switch (opcode)
/*  854:     */     {
/*  855:     */     case 21: 
/*  856: 883 */       evalLoad(Type.INTEGER, index, frame, subroutine);
/*  857: 884 */       break;
/*  858:     */     case 22: 
/*  859: 886 */       evalLoad(Type.LONG, index, frame, subroutine);
/*  860: 887 */       break;
/*  861:     */     case 23: 
/*  862: 889 */       evalLoad(Type.FLOAT, index, frame, subroutine);
/*  863: 890 */       break;
/*  864:     */     case 24: 
/*  865: 892 */       evalLoad(Type.DOUBLE, index, frame, subroutine);
/*  866: 893 */       break;
/*  867:     */     case 25: 
/*  868: 895 */       evalLoad(Type.OBJECT, index, frame, subroutine);
/*  869: 896 */       break;
/*  870:     */     case 54: 
/*  871: 898 */       evalStore(Type.INTEGER, index, frame, subroutine);
/*  872: 899 */       break;
/*  873:     */     case 55: 
/*  874: 901 */       evalStore(Type.LONG, index, frame, subroutine);
/*  875: 902 */       break;
/*  876:     */     case 56: 
/*  877: 904 */       evalStore(Type.FLOAT, index, frame, subroutine);
/*  878: 905 */       break;
/*  879:     */     case 57: 
/*  880: 907 */       evalStore(Type.DOUBLE, index, frame, subroutine);
/*  881: 908 */       break;
/*  882:     */     case 58: 
/*  883: 910 */       evalStore(Type.OBJECT, index, frame, subroutine);
/*  884: 911 */       break;
/*  885:     */     case 132: 
/*  886: 913 */       verifyAssignable(Type.INTEGER, frame.getLocal(index));
/*  887: 914 */       break;
/*  888:     */     case 169: 
/*  889: 916 */       verifyAssignable(Type.RETURN_ADDRESS, frame.getLocal(index));
/*  890: 917 */       break;
/*  891:     */     default: 
/*  892: 919 */       throw new BadBytecode("Invalid WIDE operand [pos = " + pos + "]: " + opcode);
/*  893:     */     }
/*  894:     */   }
/*  895:     */   
/*  896:     */   private Type getType(String name)
/*  897:     */     throws BadBytecode
/*  898:     */   {
/*  899:     */     try
/*  900:     */     {
/*  901: 926 */       return Type.get(this.classPool.get(name));
/*  902:     */     }
/*  903:     */     catch (NotFoundException e)
/*  904:     */     {
/*  905: 928 */       throw new BadBytecode("Could not find class [pos = " + this.lastPos + "]: " + name);
/*  906:     */     }
/*  907:     */   }
/*  908:     */   
/*  909:     */   private Type[] paramTypesFromDesc(String desc)
/*  910:     */     throws BadBytecode
/*  911:     */   {
/*  912: 933 */     CtClass[] classes = null;
/*  913:     */     try
/*  914:     */     {
/*  915: 935 */       classes = Descriptor.getParameterTypes(desc, this.classPool);
/*  916:     */     }
/*  917:     */     catch (NotFoundException e)
/*  918:     */     {
/*  919: 937 */       throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
/*  920:     */     }
/*  921: 940 */     if (classes == null) {
/*  922: 941 */       throw new BadBytecode("Could not obtain parameters for descriptor [pos = " + this.lastPos + "]: " + desc);
/*  923:     */     }
/*  924: 943 */     Type[] types = new Type[classes.length];
/*  925: 944 */     for (int i = 0; i < types.length; i++) {
/*  926: 945 */       types[i] = Type.get(classes[i]);
/*  927:     */     }
/*  928: 947 */     return types;
/*  929:     */   }
/*  930:     */   
/*  931:     */   private Type returnTypeFromDesc(String desc)
/*  932:     */     throws BadBytecode
/*  933:     */   {
/*  934: 951 */     CtClass clazz = null;
/*  935:     */     try
/*  936:     */     {
/*  937: 953 */       clazz = Descriptor.getReturnType(desc, this.classPool);
/*  938:     */     }
/*  939:     */     catch (NotFoundException e)
/*  940:     */     {
/*  941: 955 */       throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
/*  942:     */     }
/*  943: 958 */     if (clazz == null) {
/*  944: 959 */       throw new BadBytecode("Could not obtain return type for descriptor [pos = " + this.lastPos + "]: " + desc);
/*  945:     */     }
/*  946: 961 */     return Type.get(clazz);
/*  947:     */   }
/*  948:     */   
/*  949:     */   private Type simplePeek(Frame frame)
/*  950:     */   {
/*  951: 965 */     Type type = frame.peek();
/*  952: 966 */     return type == Type.TOP ? frame.getStack(frame.getTopIndex() - 1) : type;
/*  953:     */   }
/*  954:     */   
/*  955:     */   private Type simplePop(Frame frame)
/*  956:     */   {
/*  957: 970 */     Type type = frame.pop();
/*  958: 971 */     return type == Type.TOP ? frame.pop() : type;
/*  959:     */   }
/*  960:     */   
/*  961:     */   private void simplePush(Type type, Frame frame)
/*  962:     */   {
/*  963: 975 */     frame.push(type);
/*  964: 976 */     if (type.getSize() == 2) {
/*  965: 977 */       frame.push(Type.TOP);
/*  966:     */     }
/*  967:     */   }
/*  968:     */   
/*  969:     */   private void access(int index, Type type, Subroutine subroutine)
/*  970:     */   {
/*  971: 981 */     if (subroutine == null) {
/*  972: 982 */       return;
/*  973:     */     }
/*  974: 983 */     subroutine.access(index);
/*  975: 984 */     if (type.getSize() == 2) {
/*  976: 985 */       subroutine.access(index + 1);
/*  977:     */     }
/*  978:     */   }
/*  979:     */   
/*  980:     */   private void simpleSetLocal(int index, Type type, Frame frame)
/*  981:     */   {
/*  982: 989 */     frame.setLocal(index, type);
/*  983: 990 */     if (type.getSize() == 2) {
/*  984: 991 */       frame.setLocal(index + 1, Type.TOP);
/*  985:     */     }
/*  986:     */   }
/*  987:     */   
/*  988:     */   private Type resolveClassInfo(String info)
/*  989:     */     throws BadBytecode
/*  990:     */   {
/*  991: 995 */     CtClass clazz = null;
/*  992:     */     try
/*  993:     */     {
/*  994: 997 */       if (info.charAt(0) == '[') {
/*  995: 998 */         clazz = Descriptor.toCtClass(info, this.classPool);
/*  996:     */       } else {
/*  997:1000 */         clazz = this.classPool.get(info);
/*  998:     */       }
/*  999:     */     }
/* 1000:     */     catch (NotFoundException e)
/* 1001:     */     {
/* 1002:1004 */       throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
/* 1003:     */     }
/* 1004:1007 */     if (clazz == null) {
/* 1005:1008 */       throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + info);
/* 1006:     */     }
/* 1007:1010 */     return Type.get(clazz);
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   private Type typeFromDesc(String desc)
/* 1011:     */     throws BadBytecode
/* 1012:     */   {
/* 1013:1014 */     CtClass clazz = null;
/* 1014:     */     try
/* 1015:     */     {
/* 1016:1016 */       clazz = Descriptor.toCtClass(desc, this.classPool);
/* 1017:     */     }
/* 1018:     */     catch (NotFoundException e)
/* 1019:     */     {
/* 1020:1018 */       throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
/* 1021:     */     }
/* 1022:1021 */     if (clazz == null) {
/* 1023:1022 */       throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + desc);
/* 1024:     */     }
/* 1025:1024 */     return Type.get(clazz);
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   private void verifyAssignable(Type expected, Type type)
/* 1029:     */     throws BadBytecode
/* 1030:     */   {
/* 1031:1028 */     if (!expected.isAssignableFrom(type)) {
/* 1032:1029 */       throw new BadBytecode("Expected type: " + expected + " Got: " + type + " [pos = " + this.lastPos + "]");
/* 1033:     */     }
/* 1034:     */   }
/* 1035:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.Executor
 * JD-Core Version:    0.7.0.1
 */