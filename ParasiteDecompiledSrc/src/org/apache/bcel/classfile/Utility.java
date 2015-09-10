/*    1:     */ package org.apache.bcel.classfile;
/*    2:     */ 
/*    3:     */ import java.io.ByteArrayInputStream;
/*    4:     */ import java.io.ByteArrayOutputStream;
/*    5:     */ import java.io.CharArrayReader;
/*    6:     */ import java.io.CharArrayWriter;
/*    7:     */ import java.io.DataInputStream;
/*    8:     */ import java.io.FilterInputStream;
/*    9:     */ import java.io.FilterReader;
/*   10:     */ import java.io.FilterWriter;
/*   11:     */ import java.io.IOException;
/*   12:     */ import java.io.PrintStream;
/*   13:     */ import java.io.PrintWriter;
/*   14:     */ import java.io.Reader;
/*   15:     */ import java.io.Writer;
/*   16:     */ import java.util.ArrayList;
/*   17:     */ import java.util.zip.GZIPInputStream;
/*   18:     */ import java.util.zip.GZIPOutputStream;
/*   19:     */ import java.util.zip.InflaterInputStream;
/*   20:     */ import org.apache.bcel.Constants;
/*   21:     */ import org.apache.bcel.util.ByteSequence;
/*   22:     */ 
/*   23:     */ public abstract class Utility
/*   24:     */ {
/*   25:     */   private static int consumed_chars;
/*   26:  75 */   private static boolean wide = false;
/*   27:     */   private static final int FREE_CHARS = 48;
/*   28:     */   
/*   29:     */   public static final String accessToString(int access_flags)
/*   30:     */   {
/*   31:  91 */     return accessToString(access_flags, false);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public static final String accessToString(int access_flags, boolean for_class)
/*   35:     */   {
/*   36: 109 */     StringBuffer buf = new StringBuffer();
/*   37:     */     
/*   38: 111 */     int p = 0;
/*   39: 112 */     for (int i = 0; p < 1024; i++)
/*   40:     */     {
/*   41: 113 */       p = pow2(i);
/*   42: 115 */       if ((access_flags & p) != 0) {
/*   43: 121 */         if ((!for_class) || ((p != 32) && (p != 512))) {
/*   44: 124 */           buf.append(Constants.ACCESS_NAMES[i] + " ");
/*   45:     */         }
/*   46:     */       }
/*   47:     */     }
/*   48: 128 */     return buf.toString().trim();
/*   49:     */   }
/*   50:     */   
/*   51:     */   public static final String classOrInterface(int access_flags)
/*   52:     */   {
/*   53: 135 */     return (access_flags & 0x200) != 0 ? "interface" : "class";
/*   54:     */   }
/*   55:     */   
/*   56:     */   public static final String codeToString(byte[] code, ConstantPool constant_pool, int index, int length, boolean verbose)
/*   57:     */   {
/*   58: 156 */     StringBuffer buf = new StringBuffer(code.length * 20);
/*   59: 157 */     ByteSequence stream = new ByteSequence(code);
/*   60:     */     try
/*   61:     */     {
/*   62: 160 */       for (int i = 0; i < index; i++) {
/*   63: 161 */         codeToString(stream, constant_pool, verbose);
/*   64:     */       }
/*   65: 163 */       for (int i = 0; stream.available() > 0; i++) {
/*   66: 164 */         if ((length < 0) || (i < length))
/*   67:     */         {
/*   68: 165 */           String indices = fillup(stream.getIndex() + ":", 6, true, ' ');
/*   69: 166 */           buf.append(indices + codeToString(stream, constant_pool, verbose) + '\n');
/*   70:     */         }
/*   71:     */       }
/*   72:     */     }
/*   73:     */     catch (IOException e)
/*   74:     */     {
/*   75: 170 */       System.out.println(buf.toString());
/*   76: 171 */       e.printStackTrace();
/*   77: 172 */       throw new ClassFormatError("Byte code error: " + e);
/*   78:     */     }
/*   79: 175 */     return buf.toString();
/*   80:     */   }
/*   81:     */   
/*   82:     */   public static final String codeToString(byte[] code, ConstantPool constant_pool, int index, int length)
/*   83:     */   {
/*   84: 181 */     return codeToString(code, constant_pool, index, length, true);
/*   85:     */   }
/*   86:     */   
/*   87:     */   public static final String codeToString(ByteSequence bytes, ConstantPool constant_pool, boolean verbose)
/*   88:     */     throws IOException
/*   89:     */   {
/*   90: 197 */     short opcode = (short)bytes.readUnsignedByte();
/*   91: 198 */     int default_offset = 0;
/*   92:     */     
/*   93:     */ 
/*   94: 201 */     int no_pad_bytes = 0;
/*   95: 202 */     StringBuffer buf = new StringBuffer(Constants.OPCODE_NAMES[opcode]);
/*   96: 207 */     if ((opcode == 170) || (opcode == 171))
/*   97:     */     {
/*   98: 208 */       int remainder = bytes.getIndex() % 4;
/*   99: 209 */       no_pad_bytes = remainder == 0 ? 0 : 4 - remainder;
/*  100: 211 */       for (int i = 0; i < no_pad_bytes; i++)
/*  101:     */       {
/*  102:     */         byte b;
/*  103: 214 */         if ((b = bytes.readByte()) != 0) {
/*  104: 215 */           System.err.println("Warning: Padding byte != 0 in " + Constants.OPCODE_NAMES[opcode] + ":" + b);
/*  105:     */         }
/*  106:     */       }
/*  107: 220 */       default_offset = bytes.readInt();
/*  108:     */     }
/*  109:     */     int offset;
/*  110:     */     int[] jump_table;
/*  111:     */     int vindex;
/*  112:     */     int index;
/*  113: 223 */     switch (opcode)
/*  114:     */     {
/*  115:     */     case 170: 
/*  116: 227 */       int low = bytes.readInt();
/*  117: 228 */       int high = bytes.readInt();
/*  118:     */       
/*  119: 230 */       offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
/*  120: 231 */       default_offset += offset;
/*  121:     */       
/*  122: 233 */       buf.append("\tdefault = " + default_offset + ", low = " + low + ", high = " + high + "(");
/*  123:     */       
/*  124:     */ 
/*  125: 236 */       jump_table = new int[high - low + 1];
/*  126: 237 */       for (int i = 0; i < jump_table.length; i++)
/*  127:     */       {
/*  128: 238 */         jump_table[i] = (offset + bytes.readInt());
/*  129: 239 */         buf.append(jump_table[i]);
/*  130: 241 */         if (i < jump_table.length - 1) {
/*  131: 242 */           buf.append(", ");
/*  132:     */         }
/*  133:     */       }
/*  134: 244 */       buf.append(")");
/*  135:     */       
/*  136: 246 */       break;
/*  137:     */     case 171: 
/*  138: 252 */       int npairs = bytes.readInt();
/*  139: 253 */       offset = bytes.getIndex() - 8 - no_pad_bytes - 1;
/*  140:     */       
/*  141: 255 */       int[] match = new int[npairs];
/*  142: 256 */       jump_table = new int[npairs];
/*  143: 257 */       default_offset += offset;
/*  144:     */       
/*  145: 259 */       buf.append("\tdefault = " + default_offset + ", npairs = " + npairs + " (");
/*  146: 262 */       for (int i = 0; i < npairs; i++)
/*  147:     */       {
/*  148: 263 */         match[i] = bytes.readInt();
/*  149:     */         
/*  150: 265 */         jump_table[i] = (offset + bytes.readInt());
/*  151:     */         
/*  152: 267 */         buf.append("(" + match[i] + ", " + jump_table[i] + ")");
/*  153: 269 */         if (i < npairs - 1) {
/*  154: 270 */           buf.append(", ");
/*  155:     */         }
/*  156:     */       }
/*  157: 272 */       buf.append(")");
/*  158:     */       
/*  159: 274 */       break;
/*  160:     */     case 153: 
/*  161:     */     case 154: 
/*  162:     */     case 155: 
/*  163:     */     case 156: 
/*  164:     */     case 157: 
/*  165:     */     case 158: 
/*  166:     */     case 159: 
/*  167:     */     case 160: 
/*  168:     */     case 161: 
/*  169:     */     case 162: 
/*  170:     */     case 163: 
/*  171:     */     case 164: 
/*  172:     */     case 165: 
/*  173:     */     case 166: 
/*  174:     */     case 167: 
/*  175:     */     case 168: 
/*  176:     */     case 198: 
/*  177:     */     case 199: 
/*  178: 284 */       buf.append("\t\t#" + (bytes.getIndex() - 1 + bytes.readShort()));
/*  179: 285 */       break;
/*  180:     */     case 200: 
/*  181:     */     case 201: 
/*  182: 290 */       buf.append("\t\t#" + (bytes.getIndex() - 1 + bytes.readInt()));
/*  183: 291 */       break;
/*  184:     */     case 21: 
/*  185:     */     case 22: 
/*  186:     */     case 23: 
/*  187:     */     case 24: 
/*  188:     */     case 25: 
/*  189:     */     case 54: 
/*  190:     */     case 55: 
/*  191:     */     case 56: 
/*  192:     */     case 57: 
/*  193:     */     case 58: 
/*  194:     */     case 169: 
/*  195: 298 */       if (wide)
/*  196:     */       {
/*  197: 299 */         vindex = bytes.readUnsignedShort();
/*  198: 300 */         wide = false;
/*  199:     */       }
/*  200:     */       else
/*  201:     */       {
/*  202: 303 */         vindex = bytes.readUnsignedByte();
/*  203:     */       }
/*  204: 305 */       buf.append("\t\t%" + vindex);
/*  205: 306 */       break;
/*  206:     */     case 196: 
/*  207: 314 */       wide = true;
/*  208: 315 */       buf.append("\t(wide)");
/*  209: 316 */       break;
/*  210:     */     case 188: 
/*  211: 321 */       buf.append("\t\t<" + Constants.TYPE_NAMES[bytes.readByte()] + ">");
/*  212: 322 */       break;
/*  213:     */     case 178: 
/*  214:     */     case 179: 
/*  215:     */     case 180: 
/*  216:     */     case 181: 
/*  217: 327 */       index = bytes.readUnsignedShort();
/*  218: 328 */       buf.append("\t\t" + constant_pool.constantToString(index, (byte)9) + (verbose ? " (" + index + ")" : ""));
/*  219:     */       
/*  220:     */ 
/*  221: 331 */       break;
/*  222:     */     case 187: 
/*  223:     */     case 192: 
/*  224: 337 */       buf.append("\t");
/*  225:     */     case 193: 
/*  226: 339 */       index = bytes.readUnsignedShort();
/*  227: 340 */       buf.append("\t<" + constant_pool.constantToString(index, (byte)7) + ">" + (verbose ? " (" + index + ")" : ""));
/*  228:     */       
/*  229:     */ 
/*  230: 343 */       break;
/*  231:     */     case 182: 
/*  232:     */     case 183: 
/*  233:     */     case 184: 
/*  234: 348 */       index = bytes.readUnsignedShort();
/*  235: 349 */       buf.append("\t" + constant_pool.constantToString(index, (byte)10) + (verbose ? " (" + index + ")" : ""));
/*  236:     */       
/*  237:     */ 
/*  238: 352 */       break;
/*  239:     */     case 185: 
/*  240: 355 */       index = bytes.readUnsignedShort();
/*  241: 356 */       int nargs = bytes.readUnsignedByte();
/*  242: 357 */       buf.append("\t" + constant_pool.constantToString(index, (byte)11) + (verbose ? " (" + index + ")\t" : "") + nargs + "\t" + bytes.readUnsignedByte());
/*  243:     */       
/*  244:     */ 
/*  245:     */ 
/*  246:     */ 
/*  247: 362 */       break;
/*  248:     */     case 19: 
/*  249:     */     case 20: 
/*  250: 367 */       index = bytes.readUnsignedShort();
/*  251:     */       
/*  252: 369 */       buf.append("\t\t" + constant_pool.constantToString(index, constant_pool.getConstant(index).getTag()) + (verbose ? " (" + index + ")" : ""));
/*  253:     */       
/*  254:     */ 
/*  255: 372 */       break;
/*  256:     */     case 18: 
/*  257: 375 */       index = bytes.readUnsignedByte();
/*  258:     */       
/*  259: 377 */       buf.append("\t\t" + constant_pool.constantToString(index, constant_pool.getConstant(index).getTag()) + (verbose ? " (" + index + ")" : ""));
/*  260:     */       
/*  261:     */ 
/*  262:     */ 
/*  263: 381 */       break;
/*  264:     */     case 189: 
/*  265: 386 */       index = bytes.readUnsignedShort();
/*  266:     */       
/*  267: 388 */       buf.append("\t\t<" + compactClassName(constant_pool.getConstantString(index, (byte)7), false) + ">" + (verbose ? " (" + index + ")" : ""));
/*  268:     */       
/*  269:     */ 
/*  270: 391 */       break;
/*  271:     */     case 197: 
/*  272: 396 */       index = bytes.readUnsignedShort();
/*  273: 397 */       int dimensions = bytes.readUnsignedByte();
/*  274:     */       
/*  275: 399 */       buf.append("\t<" + compactClassName(constant_pool.getConstantString(index, (byte)7), false) + ">\t" + dimensions + (verbose ? " (" + index + ")" : ""));
/*  276:     */       
/*  277:     */ 
/*  278:     */ 
/*  279: 403 */       break;
/*  280:     */     case 132: 
/*  281:     */       int constant;
/*  282: 408 */       if (wide)
/*  283:     */       {
/*  284: 409 */         vindex = bytes.readUnsignedShort();
/*  285: 410 */         constant = bytes.readShort();
/*  286: 411 */         wide = false;
/*  287:     */       }
/*  288:     */       else
/*  289:     */       {
/*  290: 414 */         vindex = bytes.readUnsignedByte();
/*  291: 415 */         constant = bytes.readByte();
/*  292:     */       }
/*  293: 417 */       buf.append("\t\t%" + vindex + "\t" + constant);
/*  294: 418 */       break;
/*  295:     */     case 26: 
/*  296:     */     case 27: 
/*  297:     */     case 28: 
/*  298:     */     case 29: 
/*  299:     */     case 30: 
/*  300:     */     case 31: 
/*  301:     */     case 32: 
/*  302:     */     case 33: 
/*  303:     */     case 34: 
/*  304:     */     case 35: 
/*  305:     */     case 36: 
/*  306:     */     case 37: 
/*  307:     */     case 38: 
/*  308:     */     case 39: 
/*  309:     */     case 40: 
/*  310:     */     case 41: 
/*  311:     */     case 42: 
/*  312:     */     case 43: 
/*  313:     */     case 44: 
/*  314:     */     case 45: 
/*  315:     */     case 46: 
/*  316:     */     case 47: 
/*  317:     */     case 48: 
/*  318:     */     case 49: 
/*  319:     */     case 50: 
/*  320:     */     case 51: 
/*  321:     */     case 52: 
/*  322:     */     case 53: 
/*  323:     */     case 59: 
/*  324:     */     case 60: 
/*  325:     */     case 61: 
/*  326:     */     case 62: 
/*  327:     */     case 63: 
/*  328:     */     case 64: 
/*  329:     */     case 65: 
/*  330:     */     case 66: 
/*  331:     */     case 67: 
/*  332:     */     case 68: 
/*  333:     */     case 69: 
/*  334:     */     case 70: 
/*  335:     */     case 71: 
/*  336:     */     case 72: 
/*  337:     */     case 73: 
/*  338:     */     case 74: 
/*  339:     */     case 75: 
/*  340:     */     case 76: 
/*  341:     */     case 77: 
/*  342:     */     case 78: 
/*  343:     */     case 79: 
/*  344:     */     case 80: 
/*  345:     */     case 81: 
/*  346:     */     case 82: 
/*  347:     */     case 83: 
/*  348:     */     case 84: 
/*  349:     */     case 85: 
/*  350:     */     case 86: 
/*  351:     */     case 87: 
/*  352:     */     case 88: 
/*  353:     */     case 89: 
/*  354:     */     case 90: 
/*  355:     */     case 91: 
/*  356:     */     case 92: 
/*  357:     */     case 93: 
/*  358:     */     case 94: 
/*  359:     */     case 95: 
/*  360:     */     case 96: 
/*  361:     */     case 97: 
/*  362:     */     case 98: 
/*  363:     */     case 99: 
/*  364:     */     case 100: 
/*  365:     */     case 101: 
/*  366:     */     case 102: 
/*  367:     */     case 103: 
/*  368:     */     case 104: 
/*  369:     */     case 105: 
/*  370:     */     case 106: 
/*  371:     */     case 107: 
/*  372:     */     case 108: 
/*  373:     */     case 109: 
/*  374:     */     case 110: 
/*  375:     */     case 111: 
/*  376:     */     case 112: 
/*  377:     */     case 113: 
/*  378:     */     case 114: 
/*  379:     */     case 115: 
/*  380:     */     case 116: 
/*  381:     */     case 117: 
/*  382:     */     case 118: 
/*  383:     */     case 119: 
/*  384:     */     case 120: 
/*  385:     */     case 121: 
/*  386:     */     case 122: 
/*  387:     */     case 123: 
/*  388:     */     case 124: 
/*  389:     */     case 125: 
/*  390:     */     case 126: 
/*  391:     */     case 127: 
/*  392:     */     case 128: 
/*  393:     */     case 129: 
/*  394:     */     case 130: 
/*  395:     */     case 131: 
/*  396:     */     case 133: 
/*  397:     */     case 134: 
/*  398:     */     case 135: 
/*  399:     */     case 136: 
/*  400:     */     case 137: 
/*  401:     */     case 138: 
/*  402:     */     case 139: 
/*  403:     */     case 140: 
/*  404:     */     case 141: 
/*  405:     */     case 142: 
/*  406:     */     case 143: 
/*  407:     */     case 144: 
/*  408:     */     case 145: 
/*  409:     */     case 146: 
/*  410:     */     case 147: 
/*  411:     */     case 148: 
/*  412:     */     case 149: 
/*  413:     */     case 150: 
/*  414:     */     case 151: 
/*  415:     */     case 152: 
/*  416:     */     case 172: 
/*  417:     */     case 173: 
/*  418:     */     case 174: 
/*  419:     */     case 175: 
/*  420:     */     case 176: 
/*  421:     */     case 177: 
/*  422:     */     case 186: 
/*  423:     */     case 190: 
/*  424:     */     case 191: 
/*  425:     */     case 194: 
/*  426:     */     case 195: 
/*  427:     */     default: 
/*  428: 421 */       if (Constants.NO_OF_OPERANDS[opcode] > 0) {
/*  429: 422 */         for (int i = 0; i < Constants.TYPE_OF_OPERANDS[opcode].length; i++)
/*  430:     */         {
/*  431: 423 */           buf.append("\t\t");
/*  432: 424 */           switch (Constants.TYPE_OF_OPERANDS[opcode][i])
/*  433:     */           {
/*  434:     */           case 8: 
/*  435: 425 */             buf.append(bytes.readByte()); break;
/*  436:     */           case 9: 
/*  437: 426 */             buf.append(bytes.readShort()); break;
/*  438:     */           case 10: 
/*  439: 427 */             buf.append(bytes.readInt()); break;
/*  440:     */           default: 
/*  441: 430 */             System.err.println("Unreachable default case reached!");
/*  442: 431 */             System.exit(-1);
/*  443:     */           }
/*  444:     */         }
/*  445:     */       }
/*  446:     */       break;
/*  447:     */     }
/*  448: 437 */     return buf.toString();
/*  449:     */   }
/*  450:     */   
/*  451:     */   public static final String codeToString(ByteSequence bytes, ConstantPool constant_pool)
/*  452:     */     throws IOException
/*  453:     */   {
/*  454: 443 */     return codeToString(bytes, constant_pool, true);
/*  455:     */   }
/*  456:     */   
/*  457:     */   public static final String compactClassName(String str)
/*  458:     */   {
/*  459: 454 */     return compactClassName(str, true);
/*  460:     */   }
/*  461:     */   
/*  462:     */   public static final String compactClassName(String str, String prefix, boolean chopit)
/*  463:     */   {
/*  464: 472 */     int len = prefix.length();
/*  465:     */     
/*  466: 474 */     str = str.replace('/', '.');
/*  467: 476 */     if (chopit) {
/*  468: 478 */       if ((str.startsWith(prefix)) && (str.substring(len).indexOf('.') == -1)) {
/*  469: 480 */         str = str.substring(len);
/*  470:     */       }
/*  471:     */     }
/*  472: 483 */     return str;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public static final String compactClassName(String str, boolean chopit)
/*  476:     */   {
/*  477: 497 */     return compactClassName(str, "java.lang.", chopit);
/*  478:     */   }
/*  479:     */   
/*  480:     */   private static final boolean is_digit(char ch)
/*  481:     */   {
/*  482: 501 */     return (ch >= '0') && (ch <= '9');
/*  483:     */   }
/*  484:     */   
/*  485:     */   private static final boolean is_space(char ch)
/*  486:     */   {
/*  487: 505 */     return (ch == ' ') || (ch == '\t') || (ch == '\r') || (ch == '\n');
/*  488:     */   }
/*  489:     */   
/*  490:     */   public static final int setBit(int flag, int i)
/*  491:     */   {
/*  492: 512 */     return flag | pow2(i);
/*  493:     */   }
/*  494:     */   
/*  495:     */   public static final int clearBit(int flag, int i)
/*  496:     */   {
/*  497: 519 */     int bit = pow2(i);
/*  498: 520 */     return (flag & bit) == 0 ? flag : flag ^ bit;
/*  499:     */   }
/*  500:     */   
/*  501:     */   public static final boolean isSet(int flag, int i)
/*  502:     */   {
/*  503: 527 */     return (flag & pow2(i)) != 0;
/*  504:     */   }
/*  505:     */   
/*  506:     */   public static final String methodTypeToSignature(String ret, String[] argv)
/*  507:     */     throws ClassFormatError
/*  508:     */   {
/*  509: 541 */     StringBuffer buf = new StringBuffer("(");
/*  510: 544 */     if (argv != null) {
/*  511: 545 */       for (int i = 0; i < argv.length; i++)
/*  512:     */       {
/*  513: 546 */         str = getSignature(argv[i]);
/*  514: 548 */         if (str.endsWith("V")) {
/*  515: 549 */           throw new ClassFormatError("Invalid type: " + argv[i]);
/*  516:     */         }
/*  517: 551 */         buf.append(str);
/*  518:     */       }
/*  519:     */     }
/*  520: 554 */     String str = getSignature(ret);
/*  521:     */     
/*  522: 556 */     buf.append(")" + str);
/*  523:     */     
/*  524: 558 */     return buf.toString();
/*  525:     */   }
/*  526:     */   
/*  527:     */   public static final String[] methodSignatureArgumentTypes(String signature)
/*  528:     */     throws ClassFormatError
/*  529:     */   {
/*  530: 569 */     return methodSignatureArgumentTypes(signature, true);
/*  531:     */   }
/*  532:     */   
/*  533:     */   public static final String[] methodSignatureArgumentTypes(String signature, boolean chopit)
/*  534:     */     throws ClassFormatError
/*  535:     */   {
/*  536: 582 */     ArrayList vec = new ArrayList();
/*  537:     */     try
/*  538:     */     {
/*  539: 587 */       if (signature.charAt(0) != '(') {
/*  540: 588 */         throw new ClassFormatError("Invalid method signature: " + signature);
/*  541:     */       }
/*  542: 590 */       int index = 1;
/*  543: 592 */       while (signature.charAt(index) != ')')
/*  544:     */       {
/*  545: 593 */         vec.add(signatureToString(signature.substring(index), chopit));
/*  546: 594 */         index += consumed_chars;
/*  547:     */       }
/*  548:     */     }
/*  549:     */     catch (StringIndexOutOfBoundsException e)
/*  550:     */     {
/*  551: 597 */       throw new ClassFormatError("Invalid method signature: " + signature);
/*  552:     */     }
/*  553: 600 */     String[] types = new String[vec.size()];
/*  554: 601 */     vec.toArray(types);
/*  555: 602 */     return types;
/*  556:     */   }
/*  557:     */   
/*  558:     */   public static final String methodSignatureReturnType(String signature)
/*  559:     */     throws ClassFormatError
/*  560:     */   {
/*  561: 612 */     return methodSignatureReturnType(signature, true);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public static final String methodSignatureReturnType(String signature, boolean chopit)
/*  565:     */     throws ClassFormatError
/*  566:     */   {
/*  567:     */     String type;
/*  568:     */     try
/*  569:     */     {
/*  570: 629 */       int index = signature.lastIndexOf(')') + 1;
/*  571: 630 */       type = signatureToString(signature.substring(index), chopit);
/*  572:     */     }
/*  573:     */     catch (StringIndexOutOfBoundsException e)
/*  574:     */     {
/*  575: 632 */       throw new ClassFormatError("Invalid method signature: " + signature);
/*  576:     */     }
/*  577: 635 */     return type;
/*  578:     */   }
/*  579:     */   
/*  580:     */   public static final String methodSignatureToString(String signature, String name, String access)
/*  581:     */   {
/*  582: 649 */     return methodSignatureToString(signature, name, access, true);
/*  583:     */   }
/*  584:     */   
/*  585:     */   public static final String methodSignatureToString(String signature, String name, String access, boolean chopit)
/*  586:     */   {
/*  587: 656 */     return methodSignatureToString(signature, name, access, chopit, null);
/*  588:     */   }
/*  589:     */   
/*  590:     */   public static final String methodSignatureToString(String signature, String name, String access, boolean chopit, LocalVariableTable vars)
/*  591:     */     throws ClassFormatError
/*  592:     */   {
/*  593: 693 */     StringBuffer buf = new StringBuffer("(");
/*  594:     */     
/*  595:     */ 
/*  596: 696 */     int var_index = access.indexOf("static") >= 0 ? 0 : 1;
/*  597:     */     String type;
/*  598:     */     try
/*  599:     */     {
/*  600: 699 */       if (signature.charAt(0) != '(') {
/*  601: 700 */         throw new ClassFormatError("Invalid method signature: " + signature);
/*  602:     */       }
/*  603: 702 */       int index = 1;
/*  604: 704 */       while (signature.charAt(index) != ')')
/*  605:     */       {
/*  606: 705 */         buf.append(signatureToString(signature.substring(index), chopit));
/*  607: 707 */         if (vars != null)
/*  608:     */         {
/*  609: 708 */           LocalVariable l = vars.getLocalVariable(var_index);
/*  610: 710 */           if (l != null) {
/*  611: 711 */             buf.append(" " + l.getName());
/*  612:     */           }
/*  613:     */         }
/*  614:     */         else
/*  615:     */         {
/*  616: 713 */           buf.append(" arg" + var_index);
/*  617:     */         }
/*  618: 715 */         var_index++;
/*  619: 716 */         buf.append(", ");
/*  620: 717 */         index += consumed_chars;
/*  621:     */       }
/*  622: 720 */       index++;
/*  623:     */       
/*  624:     */ 
/*  625: 723 */       type = signatureToString(signature.substring(index), chopit);
/*  626:     */     }
/*  627:     */     catch (StringIndexOutOfBoundsException e)
/*  628:     */     {
/*  629: 726 */       throw new ClassFormatError("Invalid method signature: " + signature);
/*  630:     */     }
/*  631: 729 */     if (buf.length() > 1) {
/*  632: 730 */       buf.setLength(buf.length() - 2);
/*  633:     */     }
/*  634: 732 */     buf.append(")");
/*  635:     */     
/*  636: 734 */     return access + (access.length() > 0 ? " " : "") + type + " " + name + buf.toString();
/*  637:     */   }
/*  638:     */   
/*  639:     */   private static final int pow2(int n)
/*  640:     */   {
/*  641: 740 */     return 1 << n;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public static final String replace(String str, String old, String new_)
/*  645:     */   {
/*  646: 753 */     StringBuffer buf = new StringBuffer();
/*  647:     */     try
/*  648:     */     {
/*  649:     */       int index;
/*  650: 756 */       if ((index = str.indexOf(old)) != -1)
/*  651:     */       {
/*  652: 757 */         int old_index = 0;
/*  653: 760 */         while ((index = str.indexOf(old, old_index)) != -1)
/*  654:     */         {
/*  655: 761 */           buf.append(str.substring(old_index, index));
/*  656: 762 */           buf.append(new_);
/*  657:     */           
/*  658: 764 */           old_index = index + old.length();
/*  659:     */         }
/*  660: 767 */         buf.append(str.substring(old_index));
/*  661: 768 */         str = buf.toString();
/*  662:     */       }
/*  663:     */     }
/*  664:     */     catch (StringIndexOutOfBoundsException e)
/*  665:     */     {
/*  666: 771 */       System.err.println(e);
/*  667:     */     }
/*  668: 774 */     return str;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public static final String signatureToString(String signature)
/*  672:     */   {
/*  673: 784 */     return signatureToString(signature, true);
/*  674:     */   }
/*  675:     */   
/*  676:     */   public static final String signatureToString(String signature, boolean chopit)
/*  677:     */   {
/*  678: 824 */     consumed_chars = 1;
/*  679:     */     try
/*  680:     */     {
/*  681: 827 */       switch (signature.charAt(0))
/*  682:     */       {
/*  683:     */       case 'B': 
/*  684: 828 */         return "byte";
/*  685:     */       case 'C': 
/*  686: 829 */         return "char";
/*  687:     */       case 'D': 
/*  688: 830 */         return "double";
/*  689:     */       case 'F': 
/*  690: 831 */         return "float";
/*  691:     */       case 'I': 
/*  692: 832 */         return "int";
/*  693:     */       case 'J': 
/*  694: 833 */         return "long";
/*  695:     */       case 'L': 
/*  696: 836 */         int index = signature.indexOf(';');
/*  697: 838 */         if (index < 0) {
/*  698: 839 */           throw new ClassFormatError("Invalid signature: " + signature);
/*  699:     */         }
/*  700: 841 */         consumed_chars = index + 1;
/*  701:     */         
/*  702: 843 */         return compactClassName(signature.substring(1, index), chopit);
/*  703:     */       case 'S': 
/*  704: 846 */         return "short";
/*  705:     */       case 'Z': 
/*  706: 847 */         return "boolean";
/*  707:     */       case '[': 
/*  708: 856 */         StringBuffer brackets = new StringBuffer();
/*  709: 859 */         for (int n = 0; signature.charAt(n) == '['; n++) {
/*  710: 860 */           brackets.append("[]");
/*  711:     */         }
/*  712: 862 */         int consumed_chars = n;
/*  713:     */         
/*  714:     */ 
/*  715: 865 */         String type = signatureToString(signature.substring(n), chopit);
/*  716:     */         
/*  717: 867 */         consumed_chars += consumed_chars;
/*  718: 868 */         return type + brackets.toString();
/*  719:     */       case 'V': 
/*  720: 871 */         return "void";
/*  721:     */       }
/*  722: 873 */       throw new ClassFormatError("Invalid signature: `" + signature + "'");
/*  723:     */     }
/*  724:     */     catch (StringIndexOutOfBoundsException e)
/*  725:     */     {
/*  726: 877 */       throw new ClassFormatError("Invalid signature: " + e + ":" + signature);
/*  727:     */     }
/*  728:     */   }
/*  729:     */   
/*  730:     */   public static String getSignature(String type)
/*  731:     */   {
/*  732: 888 */     StringBuffer buf = new StringBuffer();
/*  733: 889 */     char[] chars = type.toCharArray();
/*  734: 890 */     boolean char_found = false;boolean delim = false;
/*  735: 891 */     int index = -1;
/*  736: 894 */     for (int i = 0; i < chars.length; i++) {
/*  737: 895 */       switch (chars[i])
/*  738:     */       {
/*  739:     */       case '\t': 
/*  740:     */       case '\n': 
/*  741:     */       case '\f': 
/*  742:     */       case '\r': 
/*  743:     */       case ' ': 
/*  744: 897 */         if (char_found) {
/*  745: 898 */           delim = true;
/*  746:     */         }
/*  747:     */         break;
/*  748:     */       case '[': 
/*  749: 902 */         if (!char_found) {
/*  750: 903 */           throw new RuntimeException("Illegal type: " + type);
/*  751:     */         }
/*  752: 905 */         index = i;
/*  753: 906 */         break;
/*  754:     */       default: 
/*  755: 909 */         char_found = true;
/*  756: 910 */         if (!delim) {
/*  757: 911 */           buf.append(chars[i]);
/*  758:     */         }
/*  759:     */         break;
/*  760:     */       }
/*  761:     */     }
/*  762: 915 */     int brackets = 0;
/*  763: 917 */     if (index > 0) {
/*  764: 918 */       brackets = countBrackets(type.substring(index));
/*  765:     */     }
/*  766: 920 */     type = buf.toString();
/*  767: 921 */     buf.setLength(0);
/*  768: 923 */     for (int i = 0; i < brackets; i++) {
/*  769: 924 */       buf.append('[');
/*  770:     */     }
/*  771: 926 */     boolean found = false;
/*  772: 928 */     for (int i = 4; (i <= 12) && (!found); i++) {
/*  773: 929 */       if (Constants.TYPE_NAMES[i].equals(type))
/*  774:     */       {
/*  775: 930 */         found = true;
/*  776: 931 */         buf.append(Constants.SHORT_TYPE_NAMES[i]);
/*  777:     */       }
/*  778:     */     }
/*  779: 935 */     if (!found) {
/*  780: 936 */       buf.append('L' + type.replace('.', '/') + ';');
/*  781:     */     }
/*  782: 938 */     return buf.toString();
/*  783:     */   }
/*  784:     */   
/*  785:     */   private static int countBrackets(String brackets)
/*  786:     */   {
/*  787: 942 */     char[] chars = brackets.toCharArray();
/*  788: 943 */     int count = 0;
/*  789: 944 */     boolean open = false;
/*  790: 946 */     for (int i = 0; i < chars.length; i++) {
/*  791: 947 */       switch (chars[i])
/*  792:     */       {
/*  793:     */       case '[': 
/*  794: 949 */         if (open) {
/*  795: 950 */           throw new RuntimeException("Illegally nested brackets:" + brackets);
/*  796:     */         }
/*  797: 951 */         open = true;
/*  798: 952 */         break;
/*  799:     */       case ']': 
/*  800: 955 */         if (!open) {
/*  801: 956 */           throw new RuntimeException("Illegally nested brackets:" + brackets);
/*  802:     */         }
/*  803: 957 */         open = false;
/*  804: 958 */         count++;
/*  805:     */       }
/*  806:     */     }
/*  807: 966 */     if (open) {
/*  808: 967 */       throw new RuntimeException("Illegally nested brackets:" + brackets);
/*  809:     */     }
/*  810: 969 */     return count;
/*  811:     */   }
/*  812:     */   
/*  813:     */   public static final byte typeOfMethodSignature(String signature)
/*  814:     */     throws ClassFormatError
/*  815:     */   {
/*  816:     */     try
/*  817:     */     {
/*  818: 985 */       if (signature.charAt(0) != '(') {
/*  819: 986 */         throw new ClassFormatError("Invalid method signature: " + signature);
/*  820:     */       }
/*  821: 988 */       int index = signature.lastIndexOf(')') + 1;
/*  822: 989 */       return typeOfSignature(signature.substring(index));
/*  823:     */     }
/*  824:     */     catch (StringIndexOutOfBoundsException e)
/*  825:     */     {
/*  826: 991 */       throw new ClassFormatError("Invalid method signature: " + signature);
/*  827:     */     }
/*  828:     */   }
/*  829:     */   
/*  830:     */   public static final byte typeOfSignature(String signature)
/*  831:     */     throws ClassFormatError
/*  832:     */   {
/*  833:     */     try
/*  834:     */     {
/*  835:1006 */       switch (signature.charAt(0))
/*  836:     */       {
/*  837:     */       case 'B': 
/*  838:1007 */         return 8;
/*  839:     */       case 'C': 
/*  840:1008 */         return 5;
/*  841:     */       case 'D': 
/*  842:1009 */         return 7;
/*  843:     */       case 'F': 
/*  844:1010 */         return 6;
/*  845:     */       case 'I': 
/*  846:1011 */         return 10;
/*  847:     */       case 'J': 
/*  848:1012 */         return 11;
/*  849:     */       case 'L': 
/*  850:1013 */         return 14;
/*  851:     */       case '[': 
/*  852:1014 */         return 13;
/*  853:     */       case 'V': 
/*  854:1015 */         return 12;
/*  855:     */       case 'Z': 
/*  856:1016 */         return 4;
/*  857:     */       case 'S': 
/*  858:1017 */         return 9;
/*  859:     */       }
/*  860:1019 */       throw new ClassFormatError("Invalid method signature: " + signature);
/*  861:     */     }
/*  862:     */     catch (StringIndexOutOfBoundsException e)
/*  863:     */     {
/*  864:1022 */       throw new ClassFormatError("Invalid method signature: " + signature);
/*  865:     */     }
/*  866:     */   }
/*  867:     */   
/*  868:     */   public static short searchOpcode(String name)
/*  869:     */   {
/*  870:1029 */     name = name.toLowerCase();
/*  871:1031 */     for (short i = 0; i < Constants.OPCODE_NAMES.length; i = (short)(i + 1)) {
/*  872:1032 */       if (Constants.OPCODE_NAMES[i].equals(name)) {
/*  873:1033 */         return i;
/*  874:     */       }
/*  875:     */     }
/*  876:1035 */     return -1;
/*  877:     */   }
/*  878:     */   
/*  879:     */   private static final short byteToShort(byte b)
/*  880:     */   {
/*  881:1043 */     return b < 0 ? (short)(256 + b) : (short)b;
/*  882:     */   }
/*  883:     */   
/*  884:     */   public static final String toHexString(byte[] bytes)
/*  885:     */   {
/*  886:1051 */     StringBuffer buf = new StringBuffer();
/*  887:1053 */     for (int i = 0; i < bytes.length; i++)
/*  888:     */     {
/*  889:1054 */       short b = byteToShort(bytes[i]);
/*  890:1055 */       String hex = Integer.toString(b, 16);
/*  891:1057 */       if (b < 16) {
/*  892:1058 */         buf.append('0');
/*  893:     */       }
/*  894:1060 */       buf.append(hex);
/*  895:1062 */       if (i < bytes.length - 1) {
/*  896:1063 */         buf.append(' ');
/*  897:     */       }
/*  898:     */     }
/*  899:1066 */     return buf.toString();
/*  900:     */   }
/*  901:     */   
/*  902:     */   public static final String format(int i, int length, boolean left_justify, char fill)
/*  903:     */   {
/*  904:1080 */     return fillup(Integer.toString(i), length, left_justify, fill);
/*  905:     */   }
/*  906:     */   
/*  907:     */   public static final String fillup(String str, int length, boolean left_justify, char fill)
/*  908:     */   {
/*  909:1093 */     int len = length - str.length();
/*  910:1094 */     char[] buf = new char[len < 0 ? 0 : len];
/*  911:1096 */     for (int j = 0; j < buf.length; j++) {
/*  912:1097 */       buf[j] = fill;
/*  913:     */     }
/*  914:1099 */     if (left_justify) {
/*  915:1100 */       return str + new String(buf);
/*  916:     */     }
/*  917:1102 */     return new String(buf) + str;
/*  918:     */   }
/*  919:     */   
/*  920:     */   static final boolean equals(byte[] a, byte[] b)
/*  921:     */   {
/*  922:     */     int size;
/*  923:1108 */     if ((size = a.length) != b.length) {
/*  924:1109 */       return false;
/*  925:     */     }
/*  926:1111 */     for (int i = 0; i < size; i++) {
/*  927:1112 */       if (a[i] != b[i]) {
/*  928:1113 */         return false;
/*  929:     */       }
/*  930:     */     }
/*  931:1115 */     return true;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public static final void printArray(PrintStream out, Object[] obj)
/*  935:     */   {
/*  936:1119 */     out.println(printArray(obj, true));
/*  937:     */   }
/*  938:     */   
/*  939:     */   public static final void printArray(PrintWriter out, Object[] obj)
/*  940:     */   {
/*  941:1123 */     out.println(printArray(obj, true));
/*  942:     */   }
/*  943:     */   
/*  944:     */   public static final String printArray(Object[] obj)
/*  945:     */   {
/*  946:1127 */     return printArray(obj, true);
/*  947:     */   }
/*  948:     */   
/*  949:     */   public static final String printArray(Object[] obj, boolean braces)
/*  950:     */   {
/*  951:1131 */     if (obj == null) {
/*  952:1132 */       return null;
/*  953:     */     }
/*  954:1134 */     StringBuffer buf = new StringBuffer();
/*  955:1135 */     if (braces) {
/*  956:1136 */       buf.append('{');
/*  957:     */     }
/*  958:1138 */     for (int i = 0; i < obj.length; i++)
/*  959:     */     {
/*  960:1139 */       if (obj[i] != null) {
/*  961:1140 */         buf.append(obj[i].toString());
/*  962:     */       } else {
/*  963:1142 */         buf.append("null");
/*  964:     */       }
/*  965:1144 */       if (i < obj.length - 1) {
/*  966:1145 */         buf.append(", ");
/*  967:     */       }
/*  968:     */     }
/*  969:1148 */     if (braces) {
/*  970:1149 */       buf.append('}');
/*  971:     */     }
/*  972:1151 */     return buf.toString();
/*  973:     */   }
/*  974:     */   
/*  975:     */   public static boolean isJavaIdentifierPart(char ch)
/*  976:     */   {
/*  977:1157 */     return ((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || ((ch >= '0') && (ch <= '9')) || (ch == '_');
/*  978:     */   }
/*  979:     */   
/*  980:     */   public static String encode(byte[] bytes, boolean compress)
/*  981:     */     throws IOException
/*  982:     */   {
/*  983:1180 */     if (compress)
/*  984:     */     {
/*  985:1181 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  986:1182 */       GZIPOutputStream gos = new GZIPOutputStream(baos);
/*  987:     */       
/*  988:1184 */       gos.write(bytes, 0, bytes.length);
/*  989:1185 */       gos.close();
/*  990:1186 */       baos.close();
/*  991:     */       
/*  992:1188 */       bytes = baos.toByteArray();
/*  993:     */     }
/*  994:1191 */     CharArrayWriter caw = new CharArrayWriter();
/*  995:1192 */     JavaWriter jw = new JavaWriter(caw);
/*  996:1194 */     for (int i = 0; i < bytes.length; i++)
/*  997:     */     {
/*  998:1195 */       int in = bytes[i] & 0xFF;
/*  999:1196 */       jw.write(in);
/* 1000:     */     }
/* 1001:1199 */     return caw.toString();
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public static byte[] decode(String s, boolean uncompress)
/* 1005:     */     throws IOException
/* 1006:     */   {
/* 1007:1208 */     char[] chars = s.toCharArray();
/* 1008:     */     
/* 1009:1210 */     CharArrayReader car = new CharArrayReader(chars);
/* 1010:1211 */     JavaReader jr = new JavaReader(car);
/* 1011:     */     
/* 1012:1213 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 1013:     */     int ch;
/* 1014:1217 */     while ((ch = jr.read()) >= 0)
/* 1015:     */     {
/* 1016:     */       int i;
/* 1017:1218 */       bos.write(i);
/* 1018:     */     }
/* 1019:1221 */     bos.close();
/* 1020:1222 */     car.close();
/* 1021:1223 */     jr.close();
/* 1022:     */     
/* 1023:1225 */     byte[] bytes = bos.toByteArray();
/* 1024:1227 */     if (uncompress)
/* 1025:     */     {
/* 1026:1228 */       GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
/* 1027:     */       
/* 1028:1230 */       byte[] tmp = new byte[bytes.length * 3];
/* 1029:1231 */       int count = 0;
/* 1030:     */       int b;
/* 1031:1234 */       while ((b = gis.read()) >= 0)
/* 1032:     */       {
/* 1033:     */         int j;
/* 1034:1235 */         tmp[(count++)] = ((byte)j);
/* 1035:     */       }
/* 1036:1237 */       bytes = new byte[count];
/* 1037:1238 */       System.arraycopy(tmp, 0, bytes, 0, count);
/* 1038:     */     }
/* 1039:1241 */     return bytes;
/* 1040:     */   }
/* 1041:     */   
/* 1042:1246 */   private static int[] CHAR_MAP = new int[48];
/* 1043:1247 */   private static int[] MAP_CHAR = new int[256];
/* 1044:     */   private static final char ESCAPE_CHAR = '$';
/* 1045:     */   
/* 1046:     */   static
/* 1047:     */   {
/* 1048:1251 */     int j = 0;int k = 0;
/* 1049:1252 */     for (int i = 65; i <= 90; i++)
/* 1050:     */     {
/* 1051:1253 */       CHAR_MAP[j] = i;
/* 1052:1254 */       MAP_CHAR[i] = j;
/* 1053:1255 */       j++;
/* 1054:     */     }
/* 1055:1258 */     for (int i = 103; i <= 122; i++)
/* 1056:     */     {
/* 1057:1259 */       CHAR_MAP[j] = i;
/* 1058:1260 */       MAP_CHAR[i] = j;
/* 1059:1261 */       j++;
/* 1060:     */     }
/* 1061:1264 */     CHAR_MAP[j] = 36;
/* 1062:1265 */     MAP_CHAR[36] = j;
/* 1063:1266 */     j++;
/* 1064:     */     
/* 1065:1268 */     CHAR_MAP[j] = 95;
/* 1066:1269 */     MAP_CHAR[95] = j;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   private static class JavaReader
/* 1070:     */     extends FilterReader
/* 1071:     */   {
/* 1072:     */     public JavaReader(Reader in)
/* 1073:     */     {
/* 1074:1277 */       super();
/* 1075:     */     }
/* 1076:     */     
/* 1077:     */     public int read()
/* 1078:     */       throws IOException
/* 1079:     */     {
/* 1080:1281 */       int b = this.in.read();
/* 1081:1283 */       if (b != 36) {
/* 1082:1284 */         return b;
/* 1083:     */       }
/* 1084:1286 */       int i = this.in.read();
/* 1085:1288 */       if (i < 0) {
/* 1086:1289 */         return -1;
/* 1087:     */       }
/* 1088:1291 */       if (((i >= 48) && (i <= 57)) || ((i >= 97) && (i <= 102)))
/* 1089:     */       {
/* 1090:1292 */         int j = this.in.read();
/* 1091:1294 */         if (j < 0) {
/* 1092:1295 */           return -1;
/* 1093:     */         }
/* 1094:1297 */         char[] tmp = { (char)i, (char)j };
/* 1095:1298 */         int s = Integer.parseInt(new String(tmp), 16);
/* 1096:     */         
/* 1097:1300 */         return s;
/* 1098:     */       }
/* 1099:1302 */       return Utility.MAP_CHAR[i];
/* 1100:     */     }
/* 1101:     */     
/* 1102:     */     public int read(char[] cbuf, int off, int len)
/* 1103:     */       throws IOException
/* 1104:     */     {
/* 1105:1308 */       for (int i = 0; i < len; i++) {
/* 1106:1309 */         cbuf[(off + i)] = ((char)read());
/* 1107:     */       }
/* 1108:1311 */       return len;
/* 1109:     */     }
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   private static class JavaWriter
/* 1113:     */     extends FilterWriter
/* 1114:     */   {
/* 1115:     */     public JavaWriter(Writer out)
/* 1116:     */     {
/* 1117:1320 */       super();
/* 1118:     */     }
/* 1119:     */     
/* 1120:     */     public void write(int b)
/* 1121:     */       throws IOException
/* 1122:     */     {
/* 1123:1324 */       if ((Utility.isJavaIdentifierPart((char)b)) && (b != 36))
/* 1124:     */       {
/* 1125:1325 */         this.out.write(b);
/* 1126:     */       }
/* 1127:     */       else
/* 1128:     */       {
/* 1129:1327 */         this.out.write(36);
/* 1130:1330 */         if ((b >= 0) && (b < 48))
/* 1131:     */         {
/* 1132:1331 */           this.out.write(Utility.CHAR_MAP[b]);
/* 1133:     */         }
/* 1134:     */         else
/* 1135:     */         {
/* 1136:1333 */           char[] tmp = Integer.toHexString(b).toCharArray();
/* 1137:1335 */           if (tmp.length == 1)
/* 1138:     */           {
/* 1139:1336 */             this.out.write(48);
/* 1140:1337 */             this.out.write(tmp[0]);
/* 1141:     */           }
/* 1142:     */           else
/* 1143:     */           {
/* 1144:1339 */             this.out.write(tmp[0]);
/* 1145:1340 */             this.out.write(tmp[1]);
/* 1146:     */           }
/* 1147:     */         }
/* 1148:     */       }
/* 1149:     */     }
/* 1150:     */     
/* 1151:     */     public void write(char[] cbuf, int off, int len)
/* 1152:     */       throws IOException
/* 1153:     */     {
/* 1154:1347 */       for (int i = 0; i < len; i++) {
/* 1155:1348 */         write(cbuf[(off + i)]);
/* 1156:     */       }
/* 1157:     */     }
/* 1158:     */     
/* 1159:     */     public void write(String str, int off, int len)
/* 1160:     */       throws IOException
/* 1161:     */     {
/* 1162:1352 */       write(str.toCharArray(), off, len);
/* 1163:     */     }
/* 1164:     */   }
/* 1165:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Utility
 * JD-Core Version:    0.7.0.1
 */