/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*    2:     */ 
/*    3:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*    4:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*    5:     */ import net.sourceforge.htmlunit.corejs.javascript.IdFunctionObject;
/*    6:     */ import net.sourceforge.htmlunit.corejs.javascript.IdScriptableObject;
/*    7:     */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*    8:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*    9:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   10:     */ import net.sourceforge.htmlunit.corejs.javascript.TopLevel.Builtins;
/*   11:     */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*   12:     */ 
/*   13:     */ public class NativeRegExp
/*   14:     */   extends IdScriptableObject
/*   15:     */   implements Function
/*   16:     */ {
/*   17:     */   static final long serialVersionUID = 4965263491464903264L;
/*   18:  77 */   private static final Object REGEXP_TAG = new Object();
/*   19:     */   public static final int JSREG_GLOB = 1;
/*   20:     */   public static final int JSREG_FOLD = 2;
/*   21:     */   public static final int JSREG_MULTILINE = 4;
/*   22:     */   public static final int TEST = 0;
/*   23:     */   public static final int MATCH = 1;
/*   24:     */   public static final int PREFIX = 2;
/*   25:     */   private static final boolean debug = false;
/*   26:     */   private static final byte REOP_EMPTY = 0;
/*   27:     */   private static final byte REOP_ALT = 1;
/*   28:     */   private static final byte REOP_BOL = 2;
/*   29:     */   private static final byte REOP_EOL = 3;
/*   30:     */   private static final byte REOP_WBDRY = 4;
/*   31:     */   private static final byte REOP_WNONBDRY = 5;
/*   32:     */   private static final byte REOP_QUANT = 6;
/*   33:     */   private static final byte REOP_STAR = 7;
/*   34:     */   private static final byte REOP_PLUS = 8;
/*   35:     */   private static final byte REOP_OPT = 9;
/*   36:     */   private static final byte REOP_LPAREN = 10;
/*   37:     */   private static final byte REOP_RPAREN = 11;
/*   38:     */   private static final byte REOP_DOT = 12;
/*   39:     */   private static final byte REOP_DIGIT = 14;
/*   40:     */   private static final byte REOP_NONDIGIT = 15;
/*   41:     */   private static final byte REOP_ALNUM = 16;
/*   42:     */   private static final byte REOP_NONALNUM = 17;
/*   43:     */   private static final byte REOP_SPACE = 18;
/*   44:     */   private static final byte REOP_NONSPACE = 19;
/*   45:     */   private static final byte REOP_BACKREF = 20;
/*   46:     */   private static final byte REOP_FLAT = 21;
/*   47:     */   private static final byte REOP_FLAT1 = 22;
/*   48:     */   private static final byte REOP_JUMP = 23;
/*   49:     */   private static final byte REOP_UCFLAT1 = 28;
/*   50:     */   private static final byte REOP_FLATi = 32;
/*   51:     */   private static final byte REOP_FLAT1i = 33;
/*   52:     */   private static final byte REOP_UCFLAT1i = 35;
/*   53:     */   private static final byte REOP_ASSERT = 41;
/*   54:     */   private static final byte REOP_ASSERT_NOT = 42;
/*   55:     */   private static final byte REOP_ASSERTTEST = 43;
/*   56:     */   private static final byte REOP_ASSERTNOTTEST = 44;
/*   57:     */   private static final byte REOP_MINIMALSTAR = 45;
/*   58:     */   private static final byte REOP_MINIMALPLUS = 46;
/*   59:     */   private static final byte REOP_MINIMALOPT = 47;
/*   60:     */   private static final byte REOP_MINIMALQUANT = 48;
/*   61:     */   private static final byte REOP_ENDCHILD = 49;
/*   62:     */   private static final byte REOP_CLASS = 50;
/*   63:     */   private static final byte REOP_REPEAT = 51;
/*   64:     */   private static final byte REOP_MINIMALREPEAT = 52;
/*   65:     */   private static final byte REOP_END = 53;
/*   66:     */   private static final int OFFSET_LEN = 2;
/*   67:     */   private static final int INDEX_LEN = 2;
/*   68:     */   private static final int Id_lastIndex = 1;
/*   69:     */   private static final int Id_source = 2;
/*   70:     */   private static final int Id_global = 3;
/*   71:     */   private static final int Id_ignoreCase = 4;
/*   72:     */   private static final int Id_multiline = 5;
/*   73:     */   private static final int MAX_INSTANCE_ID = 5;
/*   74:     */   private static final int Id_compile = 1;
/*   75:     */   private static final int Id_toString = 2;
/*   76:     */   private static final int Id_toSource = 3;
/*   77:     */   private static final int Id_exec = 4;
/*   78:     */   private static final int Id_test = 5;
/*   79:     */   private static final int Id_prefix = 6;
/*   80:     */   private static final int MAX_PROTOTYPE_ID = 6;
/*   81:     */   private RECompiled re;
/*   82:     */   double lastIndex;
/*   83:     */   
/*   84:     */   public static void init(Context cx, Scriptable scope, boolean sealed)
/*   85:     */   {
/*   86: 150 */     NativeRegExp proto = new NativeRegExp();
/*   87: 151 */     proto.re = ((RECompiled)compileRE(cx, "", null, false));
/*   88: 152 */     proto.activatePrototypeMap(6);
/*   89: 153 */     proto.setParentScope(scope);
/*   90: 154 */     proto.setPrototype(getObjectPrototype(scope));
/*   91:     */     
/*   92: 156 */     NativeRegExpCtor ctor = new NativeRegExpCtor();
/*   93:     */     
/*   94:     */ 
/*   95: 159 */     proto.defineProperty("constructor", ctor, 2);
/*   96:     */     
/*   97: 161 */     ScriptRuntime.setFunctionProtoAndParent(ctor, scope);
/*   98:     */     
/*   99: 163 */     ctor.setImmunePrototypeProperty(proto);
/*  100: 165 */     if (sealed)
/*  101:     */     {
/*  102: 166 */       proto.sealObject();
/*  103: 167 */       ctor.sealObject();
/*  104:     */     }
/*  105: 170 */     defineProperty(scope, "RegExp", ctor, 2);
/*  106:     */   }
/*  107:     */   
/*  108:     */   NativeRegExp(Scriptable scope, Object regexpCompiled)
/*  109:     */   {
/*  110: 175 */     this.re = ((RECompiled)regexpCompiled);
/*  111: 176 */     this.lastIndex = 0.0D;
/*  112: 177 */     ScriptRuntime.setBuiltinProtoAndParent(this, scope, TopLevel.Builtins.RegExp);
/*  113:     */   }
/*  114:     */   
/*  115:     */   public String getClassName()
/*  116:     */   {
/*  117: 183 */     return "RegExp";
/*  118:     */   }
/*  119:     */   
/*  120:     */   public String getTypeOf()
/*  121:     */   {
/*  122: 194 */     return "object";
/*  123:     */   }
/*  124:     */   
/*  125:     */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  126:     */   {
/*  127: 200 */     return execSub(cx, scope, args, 1);
/*  128:     */   }
/*  129:     */   
/*  130:     */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  131:     */   {
/*  132: 205 */     return (Scriptable)execSub(cx, scope, args, 1);
/*  133:     */   }
/*  134:     */   
/*  135:     */   Scriptable compile(Context cx, Scriptable scope, Object[] args)
/*  136:     */   {
/*  137: 210 */     if ((args.length > 0) && ((args[0] instanceof NativeRegExp)))
/*  138:     */     {
/*  139: 211 */       if ((args.length > 1) && (args[1] != Undefined.instance)) {
/*  140: 213 */         throw ScriptRuntime.typeError0("msg.bad.regexp.compile");
/*  141:     */       }
/*  142: 215 */       NativeRegExp thatObj = (NativeRegExp)args[0];
/*  143: 216 */       this.re = thatObj.re;
/*  144: 217 */       this.lastIndex = thatObj.lastIndex;
/*  145: 218 */       return this;
/*  146:     */     }
/*  147: 220 */     String s = args.length == 0 ? "" : ScriptRuntime.toString(args[0]);
/*  148: 221 */     String global = (args.length > 1) && (args[1] != Undefined.instance) ? ScriptRuntime.toString(args[1]) : null;
/*  149:     */     
/*  150:     */ 
/*  151: 224 */     this.re = ((RECompiled)compileRE(cx, s, global, false));
/*  152: 225 */     this.lastIndex = 0.0D;
/*  153: 226 */     return this;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public String toString()
/*  157:     */   {
/*  158: 232 */     StringBuffer buf = new StringBuffer();
/*  159: 233 */     buf.append('/');
/*  160: 234 */     if (this.re.source.length != 0) {
/*  161: 235 */       buf.append(this.re.source);
/*  162:     */     } else {
/*  163: 238 */       buf.append("(?:)");
/*  164:     */     }
/*  165: 240 */     buf.append('/');
/*  166: 241 */     if ((this.re.flags & 0x1) != 0) {
/*  167: 242 */       buf.append('g');
/*  168:     */     }
/*  169: 243 */     if ((this.re.flags & 0x2) != 0) {
/*  170: 244 */       buf.append('i');
/*  171:     */     }
/*  172: 245 */     if ((this.re.flags & 0x4) != 0) {
/*  173: 246 */       buf.append('m');
/*  174:     */     }
/*  175: 247 */     return buf.toString();
/*  176:     */   }
/*  177:     */   
/*  178:     */   NativeRegExp() {}
/*  179:     */   
/*  180:     */   private static RegExpImpl getImpl(Context cx)
/*  181:     */   {
/*  182: 254 */     return (RegExpImpl)ScriptRuntime.getRegExpProxy(cx);
/*  183:     */   }
/*  184:     */   
/*  185:     */   private Object execSub(Context cx, Scriptable scopeObj, Object[] args, int matchType)
/*  186:     */   {
/*  187: 260 */     RegExpImpl reImpl = getImpl(cx);
/*  188:     */     String str;
/*  189: 262 */     if (args.length == 0)
/*  190:     */     {
/*  191: 263 */       String str = reImpl.input;
/*  192: 264 */       if (str == null) {
/*  193: 265 */         reportError("msg.no.re.input.for", toString());
/*  194:     */       }
/*  195:     */     }
/*  196:     */     else
/*  197:     */     {
/*  198: 268 */       str = ScriptRuntime.toString(args[0]);
/*  199:     */     }
/*  200: 270 */     double d = (this.re.flags & 0x1) != 0 ? this.lastIndex : 0.0D;
/*  201:     */     Object rval;
/*  202:     */     Object rval;
/*  203: 273 */     if ((d < 0.0D) || (str.length() < d))
/*  204:     */     {
/*  205: 274 */       this.lastIndex = 0.0D;
/*  206: 275 */       rval = null;
/*  207:     */     }
/*  208:     */     else
/*  209:     */     {
/*  210: 278 */       int[] indexp = { (int)d };
/*  211: 279 */       rval = executeRegExp(cx, scopeObj, reImpl, str, indexp, matchType);
/*  212: 280 */       if ((this.re.flags & 0x1) != 0) {
/*  213: 281 */         this.lastIndex = ((rval == null) || (rval == Undefined.instance) ? 0.0D : indexp[0]);
/*  214:     */       }
/*  215:     */     }
/*  216: 285 */     return rval;
/*  217:     */   }
/*  218:     */   
/*  219:     */   static Object compileRE(Context cx, String str, String global, boolean flat)
/*  220:     */   {
/*  221: 290 */     RECompiled regexp = new RECompiled();
/*  222: 291 */     regexp.source = str.toCharArray();
/*  223: 292 */     int length = str.length();
/*  224:     */     
/*  225: 294 */     int flags = 0;
/*  226: 295 */     if (global != null) {
/*  227: 296 */       for (int i = 0; i < global.length(); i++)
/*  228:     */       {
/*  229: 297 */         char c = global.charAt(i);
/*  230: 298 */         if (c == 'g') {
/*  231: 299 */           flags |= 0x1;
/*  232: 300 */         } else if (c == 'i') {
/*  233: 301 */           flags |= 0x2;
/*  234: 302 */         } else if (c == 'm') {
/*  235: 303 */           flags |= 0x4;
/*  236:     */         } else {
/*  237: 305 */           reportError("msg.invalid.re.flag", String.valueOf(c));
/*  238:     */         }
/*  239:     */       }
/*  240:     */     }
/*  241: 309 */     regexp.flags = flags;
/*  242:     */     
/*  243: 311 */     CompilerState state = new CompilerState(cx, regexp.source, length, flags);
/*  244: 312 */     if ((flat) && (length > 0))
/*  245:     */     {
/*  246: 316 */       state.result = new RENode((byte)21);
/*  247: 317 */       state.result.chr = state.cpbegin[0];
/*  248: 318 */       state.result.length = length;
/*  249: 319 */       state.result.flatIndex = 0;
/*  250: 320 */       state.progLength += 5;
/*  251:     */     }
/*  252: 323 */     else if (!parseDisjunction(state))
/*  253:     */     {
/*  254: 324 */       return null;
/*  255:     */     }
/*  256: 326 */     regexp.program = new byte[state.progLength + 1];
/*  257: 327 */     if (state.classCount != 0)
/*  258:     */     {
/*  259: 328 */       regexp.classList = new RECharSet[state.classCount];
/*  260: 329 */       regexp.classCount = state.classCount;
/*  261:     */     }
/*  262: 331 */     int endPC = emitREBytecode(state, regexp, 0, state.result);
/*  263: 332 */     regexp.program[(endPC++)] = 53;
/*  264:     */     
/*  265:     */ 
/*  266:     */ 
/*  267:     */ 
/*  268:     */ 
/*  269:     */ 
/*  270:     */ 
/*  271:     */ 
/*  272:     */ 
/*  273: 342 */     regexp.parenCount = state.parenCount;
/*  274: 345 */     switch (regexp.program[0])
/*  275:     */     {
/*  276:     */     case 28: 
/*  277:     */     case 35: 
/*  278: 348 */       regexp.anchorCh = ((char)getIndex(regexp.program, 1));
/*  279: 349 */       break;
/*  280:     */     case 22: 
/*  281:     */     case 33: 
/*  282: 352 */       regexp.anchorCh = ((char)(regexp.program[1] & 0xFF));
/*  283: 353 */       break;
/*  284:     */     case 21: 
/*  285:     */     case 32: 
/*  286: 356 */       int k = getIndex(regexp.program, 1);
/*  287: 357 */       regexp.anchorCh = regexp.source[k];
/*  288:     */     }
/*  289: 366 */     return regexp;
/*  290:     */   }
/*  291:     */   
/*  292:     */   static boolean isDigit(char c)
/*  293:     */   {
/*  294: 371 */     return ('0' <= c) && (c <= '9');
/*  295:     */   }
/*  296:     */   
/*  297:     */   private static boolean isWord(char c)
/*  298:     */   {
/*  299: 376 */     return (Character.isLetter(c)) || (isDigit(c)) || (c == '_');
/*  300:     */   }
/*  301:     */   
/*  302:     */   private static boolean isLineTerm(char c)
/*  303:     */   {
/*  304: 381 */     return ScriptRuntime.isJSLineTerminator(c);
/*  305:     */   }
/*  306:     */   
/*  307:     */   private static boolean isREWhiteSpace(int c)
/*  308:     */   {
/*  309: 386 */     return ScriptRuntime.isJSWhitespaceOrLineTerminator(c);
/*  310:     */   }
/*  311:     */   
/*  312:     */   private static char upcase(char ch)
/*  313:     */   {
/*  314: 402 */     if (ch < '')
/*  315:     */     {
/*  316: 403 */       if (('a' <= ch) && (ch <= 'z')) {
/*  317: 404 */         return (char)(ch + '￠');
/*  318:     */       }
/*  319: 406 */       return ch;
/*  320:     */     }
/*  321: 408 */     char cu = Character.toUpperCase(ch);
/*  322: 409 */     if ((ch >= '') && (cu < '')) {
/*  323: 409 */       return ch;
/*  324:     */     }
/*  325: 410 */     return cu;
/*  326:     */   }
/*  327:     */   
/*  328:     */   private static char downcase(char ch)
/*  329:     */   {
/*  330: 415 */     if (ch < '')
/*  331:     */     {
/*  332: 416 */       if (('A' <= ch) && (ch <= 'Z')) {
/*  333: 417 */         return (char)(ch + ' ');
/*  334:     */       }
/*  335: 419 */       return ch;
/*  336:     */     }
/*  337: 421 */     char cl = Character.toLowerCase(ch);
/*  338: 422 */     if ((ch >= '') && (cl < '')) {
/*  339: 422 */       return ch;
/*  340:     */     }
/*  341: 423 */     return cl;
/*  342:     */   }
/*  343:     */   
/*  344:     */   private static int toASCIIHexDigit(int c)
/*  345:     */   {
/*  346: 431 */     if (c < 48) {
/*  347: 432 */       return -1;
/*  348:     */     }
/*  349: 433 */     if (c <= 57) {
/*  350: 434 */       return c - 48;
/*  351:     */     }
/*  352: 436 */     c |= 0x20;
/*  353: 437 */     if ((97 <= c) && (c <= 102)) {
/*  354: 438 */       return c - 97 + 10;
/*  355:     */     }
/*  356: 440 */     return -1;
/*  357:     */   }
/*  358:     */   
/*  359:     */   private static boolean parseDisjunction(CompilerState state)
/*  360:     */   {
/*  361: 451 */     if (!parseAlternative(state)) {
/*  362: 452 */       return false;
/*  363:     */     }
/*  364: 453 */     char[] source = state.cpbegin;
/*  365: 454 */     int index = state.cp;
/*  366: 455 */     if ((index != source.length) && (source[index] == '|'))
/*  367:     */     {
/*  368: 457 */       state.cp += 1;
/*  369: 458 */       RENode altResult = new RENode((byte)1);
/*  370: 459 */       altResult.kid = state.result;
/*  371: 460 */       if (!parseDisjunction(state)) {
/*  372: 461 */         return false;
/*  373:     */       }
/*  374: 462 */       altResult.kid2 = state.result;
/*  375: 463 */       state.result = altResult;
/*  376:     */       
/*  377: 465 */       state.progLength += 9;
/*  378:     */     }
/*  379: 467 */     return true;
/*  380:     */   }
/*  381:     */   
/*  382:     */   private static boolean parseAlternative(CompilerState state)
/*  383:     */   {
/*  384: 476 */     RENode headTerm = null;
/*  385: 477 */     RENode tailTerm = null;
/*  386: 478 */     char[] source = state.cpbegin;
/*  387:     */     for (;;)
/*  388:     */     {
/*  389: 480 */       if ((state.cp == state.cpend) || (source[state.cp] == '|') || ((state.parenNesting != 0) && (source[state.cp] == ')')))
/*  390:     */       {
/*  391: 483 */         if (headTerm == null) {
/*  392: 484 */           state.result = new RENode((byte)0);
/*  393:     */         } else {
/*  394: 487 */           state.result = headTerm;
/*  395:     */         }
/*  396: 488 */         return true;
/*  397:     */       }
/*  398: 490 */       if (!parseTerm(state)) {
/*  399: 491 */         return false;
/*  400:     */       }
/*  401: 492 */       if (headTerm == null)
/*  402:     */       {
/*  403: 493 */         headTerm = state.result;
/*  404:     */       }
/*  405: 495 */       else if (tailTerm == null)
/*  406:     */       {
/*  407: 496 */         headTerm.next = state.result;
/*  408: 497 */         tailTerm = state.result;
/*  409: 498 */         while (tailTerm.next != null) {
/*  410: 498 */           tailTerm = tailTerm.next;
/*  411:     */         }
/*  412:     */       }
/*  413:     */       else
/*  414:     */       {
/*  415: 501 */         tailTerm.next = state.result;
/*  416: 502 */         tailTerm = tailTerm.next;
/*  417: 503 */         while (tailTerm.next != null) {
/*  418: 503 */           tailTerm = tailTerm.next;
/*  419:     */         }
/*  420:     */       }
/*  421:     */     }
/*  422:     */   }
/*  423:     */   
/*  424:     */   private static boolean calculateBitmapSize(CompilerState state, RENode target, char[] src, int index, int end)
/*  425:     */   {
/*  426: 514 */     char rangeStart = '\000';
/*  427:     */     
/*  428:     */ 
/*  429:     */ 
/*  430:     */ 
/*  431: 519 */     int max = 0;
/*  432: 520 */     boolean inRange = false;
/*  433:     */     
/*  434: 522 */     target.bmsize = 0;
/*  435: 524 */     if (index == end) {
/*  436: 525 */       return true;
/*  437:     */     }
/*  438: 527 */     if (src[index] == '^') {
/*  439: 528 */       index++;
/*  440:     */     }
/*  441: 530 */     while (index != end)
/*  442:     */     {
/*  443: 531 */       int localMax = 0;
/*  444: 532 */       int nDigits = 2;
/*  445: 533 */       switch (src[index])
/*  446:     */       {
/*  447:     */       case '\\': 
/*  448: 535 */         index++;
/*  449: 536 */         char c = src[(index++)];
/*  450:     */         int n;
/*  451:     */         int i;
/*  452: 537 */         switch (c)
/*  453:     */         {
/*  454:     */         case 'b': 
/*  455: 539 */           localMax = 8;
/*  456: 540 */           break;
/*  457:     */         case 'f': 
/*  458: 542 */           localMax = 12;
/*  459: 543 */           break;
/*  460:     */         case 'n': 
/*  461: 545 */           localMax = 10;
/*  462: 546 */           break;
/*  463:     */         case 'r': 
/*  464: 548 */           localMax = 13;
/*  465: 549 */           break;
/*  466:     */         case 't': 
/*  467: 551 */           localMax = 9;
/*  468: 552 */           break;
/*  469:     */         case 'v': 
/*  470: 554 */           localMax = 11;
/*  471: 555 */           break;
/*  472:     */         case 'c': 
/*  473: 557 */           if ((index + 1 < end) && (Character.isLetter(src[(index + 1)]))) {
/*  474: 558 */             localMax = (char)(src[(index++)] & 0x1F);
/*  475:     */           } else {
/*  476: 560 */             localMax = 92;
/*  477:     */           }
/*  478: 561 */           break;
/*  479:     */         case 'u': 
/*  480: 563 */           nDigits += 2;
/*  481:     */         case 'x': 
/*  482: 566 */           n = 0;
/*  483: 567 */           for (i = 0; (i < nDigits) && (index < end); i++)
/*  484:     */           {
/*  485: 568 */             c = src[(index++)];
/*  486: 569 */             n = Kit.xDigitToInt(c, n);
/*  487: 570 */             if (n < 0)
/*  488:     */             {
/*  489: 573 */               index -= i + 1;
/*  490: 574 */               n = 92;
/*  491: 575 */               break;
/*  492:     */             }
/*  493:     */           }
/*  494: 578 */           localMax = n;
/*  495: 579 */           break;
/*  496:     */         case 'd': 
/*  497: 581 */           if (inRange)
/*  498:     */           {
/*  499: 582 */             reportError("msg.bad.range", "");
/*  500: 583 */             return false;
/*  501:     */           }
/*  502: 585 */           localMax = 57;
/*  503: 586 */           break;
/*  504:     */         case 'D': 
/*  505:     */         case 'S': 
/*  506:     */         case 'W': 
/*  507:     */         case 's': 
/*  508:     */         case 'w': 
/*  509: 592 */           if (inRange)
/*  510:     */           {
/*  511: 593 */             reportError("msg.bad.range", "");
/*  512: 594 */             return false;
/*  513:     */           }
/*  514: 596 */           target.bmsize = 65535;
/*  515: 597 */           return true;
/*  516:     */         case '0': 
/*  517:     */         case '1': 
/*  518:     */         case '2': 
/*  519:     */         case '3': 
/*  520:     */         case '4': 
/*  521:     */         case '5': 
/*  522:     */         case '6': 
/*  523:     */         case '7': 
/*  524: 612 */           n = c - '0';
/*  525: 613 */           c = src[index];
/*  526: 614 */           if (('0' <= c) && (c <= '7'))
/*  527:     */           {
/*  528: 615 */             index++;
/*  529: 616 */             n = 8 * n + (c - '0');
/*  530: 617 */             c = src[index];
/*  531: 618 */             if (('0' <= c) && (c <= '7'))
/*  532:     */             {
/*  533: 619 */               index++;
/*  534: 620 */               i = 8 * n + (c - '0');
/*  535: 621 */               if (i <= 255) {
/*  536: 622 */                 n = i;
/*  537:     */               } else {
/*  538: 624 */                 index--;
/*  539:     */               }
/*  540:     */             }
/*  541:     */           }
/*  542: 627 */           localMax = n;
/*  543: 628 */           break;
/*  544:     */         case '8': 
/*  545:     */         case '9': 
/*  546:     */         case ':': 
/*  547:     */         case ';': 
/*  548:     */         case '<': 
/*  549:     */         case '=': 
/*  550:     */         case '>': 
/*  551:     */         case '?': 
/*  552:     */         case '@': 
/*  553:     */         case 'A': 
/*  554:     */         case 'B': 
/*  555:     */         case 'C': 
/*  556:     */         case 'E': 
/*  557:     */         case 'F': 
/*  558:     */         case 'G': 
/*  559:     */         case 'H': 
/*  560:     */         case 'I': 
/*  561:     */         case 'J': 
/*  562:     */         case 'K': 
/*  563:     */         case 'L': 
/*  564:     */         case 'M': 
/*  565:     */         case 'N': 
/*  566:     */         case 'O': 
/*  567:     */         case 'P': 
/*  568:     */         case 'Q': 
/*  569:     */         case 'R': 
/*  570:     */         case 'T': 
/*  571:     */         case 'U': 
/*  572:     */         case 'V': 
/*  573:     */         case 'X': 
/*  574:     */         case 'Y': 
/*  575:     */         case 'Z': 
/*  576:     */         case '[': 
/*  577:     */         case '\\': 
/*  578:     */         case ']': 
/*  579:     */         case '^': 
/*  580:     */         case '_': 
/*  581:     */         case '`': 
/*  582:     */         case 'a': 
/*  583:     */         case 'e': 
/*  584:     */         case 'g': 
/*  585:     */         case 'h': 
/*  586:     */         case 'i': 
/*  587:     */         case 'j': 
/*  588:     */         case 'k': 
/*  589:     */         case 'l': 
/*  590:     */         case 'm': 
/*  591:     */         case 'o': 
/*  592:     */         case 'p': 
/*  593:     */         case 'q': 
/*  594:     */         default: 
/*  595: 631 */           localMax = c;
/*  596:     */         }
/*  597: 632 */         break;
/*  598:     */       default: 
/*  599: 636 */         localMax = src[(index++)];
/*  600:     */       }
/*  601: 639 */       if (inRange)
/*  602:     */       {
/*  603: 640 */         if (rangeStart > localMax)
/*  604:     */         {
/*  605: 641 */           reportError("msg.bad.range", "");
/*  606: 642 */           return false;
/*  607:     */         }
/*  608: 644 */         inRange = false;
/*  609:     */       }
/*  610: 647 */       else if ((index < end - 1) && 
/*  611: 648 */         (src[index] == '-'))
/*  612:     */       {
/*  613: 649 */         index++;
/*  614: 650 */         inRange = true;
/*  615: 651 */         rangeStart = (char)localMax;
/*  616: 652 */         continue;
/*  617:     */       }
/*  618: 656 */       if ((state.flags & 0x2) != 0)
/*  619:     */       {
/*  620: 657 */         char cu = upcase((char)localMax);
/*  621: 658 */         char cd = downcase((char)localMax);
/*  622: 659 */         localMax = cu >= cd ? cu : cd;
/*  623:     */       }
/*  624: 661 */       if (localMax > max) {
/*  625: 662 */         max = localMax;
/*  626:     */       }
/*  627:     */     }
/*  628: 664 */     target.bmsize = max;
/*  629: 665 */     return true;
/*  630:     */   }
/*  631:     */   
/*  632:     */   private static void doFlat(CompilerState state, char c)
/*  633:     */   {
/*  634: 724 */     state.result = new RENode((byte)21);
/*  635: 725 */     state.result.chr = c;
/*  636: 726 */     state.result.length = 1;
/*  637: 727 */     state.result.flatIndex = -1;
/*  638: 728 */     state.progLength += 3;
/*  639:     */   }
/*  640:     */   
/*  641:     */   private static int getDecimalValue(char c, CompilerState state, int maxValue, String overflowMessageId)
/*  642:     */   {
/*  643: 735 */     boolean overflow = false;
/*  644: 736 */     int start = state.cp;
/*  645: 737 */     char[] src = state.cpbegin;
/*  646: 738 */     int value = c - '0';
/*  647: 739 */     for (; state.cp != state.cpend; state.cp += 1)
/*  648:     */     {
/*  649: 740 */       c = src[state.cp];
/*  650: 741 */       if (!isDigit(c)) {
/*  651:     */         break;
/*  652:     */       }
/*  653: 744 */       if (!overflow)
/*  654:     */       {
/*  655: 745 */         int digit = c - '0';
/*  656: 746 */         if (value < (maxValue - digit) / 10)
/*  657:     */         {
/*  658: 747 */           value = value * 10 + digit;
/*  659:     */         }
/*  660:     */         else
/*  661:     */         {
/*  662: 749 */           overflow = true;
/*  663: 750 */           value = maxValue;
/*  664:     */         }
/*  665:     */       }
/*  666:     */     }
/*  667: 754 */     if (overflow) {
/*  668: 755 */       reportError(overflowMessageId, String.valueOf(src, start, state.cp - start));
/*  669:     */     }
/*  670: 758 */     return value;
/*  671:     */   }
/*  672:     */   
/*  673:     */   private static boolean parseTerm(CompilerState state)
/*  674:     */   {
/*  675: 764 */     char[] src = state.cpbegin;
/*  676: 765 */     char c = src[(state.cp++)];
/*  677: 766 */     int nDigits = 2;
/*  678: 767 */     int parenBaseCount = state.parenCount;
/*  679:     */     int termStart;
/*  680: 772 */     switch (c)
/*  681:     */     {
/*  682:     */     case '^': 
/*  683: 775 */       state.result = new RENode((byte)2);
/*  684: 776 */       state.progLength += 1;
/*  685: 777 */       return true;
/*  686:     */     case '$': 
/*  687: 779 */       state.result = new RENode((byte)3);
/*  688: 780 */       state.progLength += 1;
/*  689: 781 */       return true;
/*  690:     */     case '\\': 
/*  691: 783 */       if (state.cp < state.cpend)
/*  692:     */       {
/*  693: 784 */         c = src[(state.cp++)];
/*  694:     */         int num;
/*  695: 785 */         switch (c)
/*  696:     */         {
/*  697:     */         case 'b': 
/*  698: 788 */           state.result = new RENode((byte)4);
/*  699: 789 */           state.progLength += 1;
/*  700: 790 */           return true;
/*  701:     */         case 'B': 
/*  702: 792 */           state.result = new RENode((byte)5);
/*  703: 793 */           state.progLength += 1;
/*  704: 794 */           return true;
/*  705:     */         case '0': 
/*  706: 804 */           reportWarning(state.cx, "msg.bad.backref", "");
/*  707:     */           
/*  708: 806 */           num = 0;
/*  709: 807 */           while (state.cp < state.cpend)
/*  710:     */           {
/*  711: 808 */             c = src[state.cp];
/*  712: 809 */             if ((c < '0') || (c > '7')) {
/*  713:     */               break;
/*  714:     */             }
/*  715: 810 */             state.cp += 1;
/*  716: 811 */             int tmp = 8 * num + (c - '0');
/*  717: 812 */             if (tmp > 255) {
/*  718:     */               break;
/*  719:     */             }
/*  720: 814 */             num = tmp;
/*  721:     */           }
/*  722: 819 */           c = (char)num;
/*  723: 820 */           doFlat(state, c);
/*  724: 821 */           break;
/*  725:     */         case '1': 
/*  726:     */         case '2': 
/*  727:     */         case '3': 
/*  728:     */         case '4': 
/*  729:     */         case '5': 
/*  730:     */         case '6': 
/*  731:     */         case '7': 
/*  732:     */         case '8': 
/*  733:     */         case '9': 
/*  734: 831 */           termStart = state.cp - 1;
/*  735: 832 */           num = getDecimalValue(c, state, 65535, "msg.overlarge.backref");
/*  736: 834 */           if (num > state.parenCount) {
/*  737: 835 */             reportWarning(state.cx, "msg.bad.backref", "");
/*  738:     */           }
/*  739: 840 */           if ((num > 9) && (num > state.parenCount))
/*  740:     */           {
/*  741: 841 */             state.cp = termStart;
/*  742: 842 */             num = 0;
/*  743: 843 */             while (state.cp < state.cpend)
/*  744:     */             {
/*  745: 844 */               c = src[state.cp];
/*  746: 845 */               if ((c < '0') || (c > '7')) {
/*  747:     */                 break;
/*  748:     */               }
/*  749: 846 */               state.cp += 1;
/*  750: 847 */               int tmp = 8 * num + (c - '0');
/*  751: 848 */               if (tmp > 255) {
/*  752:     */                 break;
/*  753:     */               }
/*  754: 850 */               num = tmp;
/*  755:     */             }
/*  756: 855 */             c = (char)num;
/*  757: 856 */             doFlat(state, c);
/*  758:     */           }
/*  759:     */           else
/*  760:     */           {
/*  761: 860 */             state.result = new RENode((byte)20);
/*  762: 861 */             state.result.parenIndex = (num - 1);
/*  763: 862 */             state.progLength += 3;
/*  764:     */           }
/*  765: 863 */           break;
/*  766:     */         case 'f': 
/*  767: 866 */           c = '\f';
/*  768: 867 */           doFlat(state, c);
/*  769: 868 */           break;
/*  770:     */         case 'n': 
/*  771: 870 */           c = '\n';
/*  772: 871 */           doFlat(state, c);
/*  773: 872 */           break;
/*  774:     */         case 'r': 
/*  775: 874 */           c = '\r';
/*  776: 875 */           doFlat(state, c);
/*  777: 876 */           break;
/*  778:     */         case 't': 
/*  779: 878 */           c = '\t';
/*  780: 879 */           doFlat(state, c);
/*  781: 880 */           break;
/*  782:     */         case 'v': 
/*  783: 882 */           c = '\013';
/*  784: 883 */           doFlat(state, c);
/*  785: 884 */           break;
/*  786:     */         case 'c': 
/*  787: 887 */           if ((state.cp + 1 < state.cpend) && (Character.isLetter(src[(state.cp + 1)])))
/*  788:     */           {
/*  789: 889 */             c = (char)(src[(state.cp++)] & 0x1F);
/*  790:     */           }
/*  791:     */           else
/*  792:     */           {
/*  793: 892 */             state.cp -= 1;
/*  794: 893 */             c = '\\';
/*  795:     */           }
/*  796: 895 */           doFlat(state, c);
/*  797: 896 */           break;
/*  798:     */         case 'u': 
/*  799: 899 */           nDigits += 2;
/*  800:     */         case 'x': 
/*  801: 904 */           int n = 0;
/*  802: 906 */           for (int i = 0; (i < nDigits) && (state.cp < state.cpend); i++)
/*  803:     */           {
/*  804: 908 */             c = src[(state.cp++)];
/*  805: 909 */             n = Kit.xDigitToInt(c, n);
/*  806: 910 */             if (n < 0)
/*  807:     */             {
/*  808: 913 */               state.cp -= i + 2;
/*  809: 914 */               n = src[(state.cp++)];
/*  810: 915 */               break;
/*  811:     */             }
/*  812:     */           }
/*  813: 918 */           c = (char)n;
/*  814:     */           
/*  815: 920 */           doFlat(state, c);
/*  816: 921 */           break;
/*  817:     */         case 'd': 
/*  818: 924 */           state.result = new RENode((byte)14);
/*  819: 925 */           state.progLength += 1;
/*  820: 926 */           break;
/*  821:     */         case 'D': 
/*  822: 928 */           state.result = new RENode((byte)15);
/*  823: 929 */           state.progLength += 1;
/*  824: 930 */           break;
/*  825:     */         case 's': 
/*  826: 932 */           state.result = new RENode((byte)18);
/*  827: 933 */           state.progLength += 1;
/*  828: 934 */           break;
/*  829:     */         case 'S': 
/*  830: 936 */           state.result = new RENode((byte)19);
/*  831: 937 */           state.progLength += 1;
/*  832: 938 */           break;
/*  833:     */         case 'w': 
/*  834: 940 */           state.result = new RENode((byte)16);
/*  835: 941 */           state.progLength += 1;
/*  836: 942 */           break;
/*  837:     */         case 'W': 
/*  838: 944 */           state.result = new RENode((byte)17);
/*  839: 945 */           state.progLength += 1;
/*  840: 946 */           break;
/*  841:     */         case ':': 
/*  842:     */         case ';': 
/*  843:     */         case '<': 
/*  844:     */         case '=': 
/*  845:     */         case '>': 
/*  846:     */         case '?': 
/*  847:     */         case '@': 
/*  848:     */         case 'A': 
/*  849:     */         case 'C': 
/*  850:     */         case 'E': 
/*  851:     */         case 'F': 
/*  852:     */         case 'G': 
/*  853:     */         case 'H': 
/*  854:     */         case 'I': 
/*  855:     */         case 'J': 
/*  856:     */         case 'K': 
/*  857:     */         case 'L': 
/*  858:     */         case 'M': 
/*  859:     */         case 'N': 
/*  860:     */         case 'O': 
/*  861:     */         case 'P': 
/*  862:     */         case 'Q': 
/*  863:     */         case 'R': 
/*  864:     */         case 'T': 
/*  865:     */         case 'U': 
/*  866:     */         case 'V': 
/*  867:     */         case 'X': 
/*  868:     */         case 'Y': 
/*  869:     */         case 'Z': 
/*  870:     */         case '[': 
/*  871:     */         case '\\': 
/*  872:     */         case ']': 
/*  873:     */         case '^': 
/*  874:     */         case '_': 
/*  875:     */         case '`': 
/*  876:     */         case 'a': 
/*  877:     */         case 'e': 
/*  878:     */         case 'g': 
/*  879:     */         case 'h': 
/*  880:     */         case 'i': 
/*  881:     */         case 'j': 
/*  882:     */         case 'k': 
/*  883:     */         case 'l': 
/*  884:     */         case 'm': 
/*  885:     */         case 'o': 
/*  886:     */         case 'p': 
/*  887:     */         case 'q': 
/*  888:     */         default: 
/*  889: 949 */           state.result = new RENode((byte)21);
/*  890: 950 */           state.result.chr = c;
/*  891: 951 */           state.result.length = 1;
/*  892: 952 */           state.result.flatIndex = (state.cp - 1);
/*  893: 953 */           state.progLength += 3;
/*  894: 954 */           break;
/*  895:     */         }
/*  896:     */       }
/*  897:     */       else
/*  898:     */       {
/*  899: 960 */         reportError("msg.trail.backslash", "");
/*  900: 961 */         return false;
/*  901:     */       }
/*  902:     */       break;
/*  903:     */     case '(': 
/*  904: 964 */       RENode result = null;
/*  905: 965 */       termStart = state.cp;
/*  906: 966 */       if ((state.cp + 1 < state.cpend) && (src[state.cp] == '?') && (((c = src[(state.cp + 1)]) == '=') || (c == '!') || (c == ':')))
/*  907:     */       {
/*  908: 969 */         state.cp += 2;
/*  909: 970 */         if (c == '=')
/*  910:     */         {
/*  911: 971 */           result = new RENode((byte)41);
/*  912:     */           
/*  913: 973 */           state.progLength += 4;
/*  914:     */         }
/*  915: 974 */         else if (c == '!')
/*  916:     */         {
/*  917: 975 */           result = new RENode((byte)42);
/*  918:     */           
/*  919: 977 */           state.progLength += 4;
/*  920:     */         }
/*  921:     */       }
/*  922:     */       else
/*  923:     */       {
/*  924: 980 */         result = new RENode((byte)10);
/*  925:     */         
/*  926: 982 */         state.progLength += 6;
/*  927: 983 */         result.parenIndex = (state.parenCount++);
/*  928:     */       }
/*  929: 985 */       state.parenNesting += 1;
/*  930: 986 */       if (!parseDisjunction(state)) {
/*  931: 987 */         return false;
/*  932:     */       }
/*  933: 988 */       if ((state.cp == state.cpend) || (src[state.cp] != ')'))
/*  934:     */       {
/*  935: 989 */         reportError("msg.unterm.paren", "");
/*  936: 990 */         return false;
/*  937:     */       }
/*  938: 992 */       state.cp += 1;
/*  939: 993 */       state.parenNesting -= 1;
/*  940: 994 */       if (result != null)
/*  941:     */       {
/*  942: 995 */         result.kid = state.result;
/*  943: 996 */         state.result = result;
/*  944:     */       }
/*  945:     */       break;
/*  946:     */     case ')': 
/*  947:1001 */       reportError("msg.re.unmatched.right.paren", "");
/*  948:1002 */       return false;
/*  949:     */     case '[': 
/*  950:1004 */       state.result = new RENode((byte)50);
/*  951:1005 */       termStart = state.cp;
/*  952:1006 */       state.result.startIndex = termStart;
/*  953:     */       for (;;)
/*  954:     */       {
/*  955:1008 */         if (state.cp == state.cpend)
/*  956:     */         {
/*  957:1009 */           reportError("msg.unterm.class", "");
/*  958:1010 */           return false;
/*  959:     */         }
/*  960:1012 */         if (src[state.cp] == '\\')
/*  961:     */         {
/*  962:1013 */           state.cp += 1;
/*  963:     */         }
/*  964:1015 */         else if (src[state.cp] == ']')
/*  965:     */         {
/*  966:1016 */           state.result.kidlen = (state.cp - termStart);
/*  967:1017 */           break;
/*  968:     */         }
/*  969:1020 */         state.cp += 1;
/*  970:     */       }
/*  971:1022 */       state.result.index = (state.classCount++);
/*  972:1027 */       if (!calculateBitmapSize(state, state.result, src, termStart, state.cp++)) {
/*  973:1028 */         return false;
/*  974:     */       }
/*  975:1029 */       state.progLength += 3;
/*  976:1030 */       break;
/*  977:     */     case '.': 
/*  978:1033 */       state.result = new RENode((byte)12);
/*  979:1034 */       state.progLength += 1;
/*  980:1035 */       break;
/*  981:     */     case '*': 
/*  982:     */     case '+': 
/*  983:     */     case '?': 
/*  984:1039 */       reportError("msg.bad.quant", String.valueOf(src[(state.cp - 1)]));
/*  985:1040 */       return false;
/*  986:     */     default: 
/*  987:1042 */       state.result = new RENode((byte)21);
/*  988:1043 */       state.result.chr = c;
/*  989:1044 */       state.result.length = 1;
/*  990:1045 */       state.result.flatIndex = (state.cp - 1);
/*  991:1046 */       state.progLength += 3;
/*  992:     */     }
/*  993:1050 */     RENode term = state.result;
/*  994:1051 */     if (state.cp == state.cpend) {
/*  995:1052 */       return true;
/*  996:     */     }
/*  997:1054 */     boolean hasQ = false;
/*  998:1055 */     switch (src[state.cp])
/*  999:     */     {
/* 1000:     */     case '+': 
/* 1001:1057 */       state.result = new RENode((byte)6);
/* 1002:1058 */       state.result.min = 1;
/* 1003:1059 */       state.result.max = -1;
/* 1004:     */       
/* 1005:1061 */       state.progLength += 8;
/* 1006:1062 */       hasQ = true;
/* 1007:1063 */       break;
/* 1008:     */     case '*': 
/* 1009:1065 */       state.result = new RENode((byte)6);
/* 1010:1066 */       state.result.min = 0;
/* 1011:1067 */       state.result.max = -1;
/* 1012:     */       
/* 1013:1069 */       state.progLength += 8;
/* 1014:1070 */       hasQ = true;
/* 1015:1071 */       break;
/* 1016:     */     case '?': 
/* 1017:1073 */       state.result = new RENode((byte)6);
/* 1018:1074 */       state.result.min = 0;
/* 1019:1075 */       state.result.max = 1;
/* 1020:     */       
/* 1021:1077 */       state.progLength += 8;
/* 1022:1078 */       hasQ = true;
/* 1023:1079 */       break;
/* 1024:     */     case '{': 
/* 1025:1082 */       int min = 0;
/* 1026:1083 */       int max = -1;
/* 1027:1084 */       int leftCurl = state.cp;
/* 1028:     */       
/* 1029:     */ 
/* 1030:     */ 
/* 1031:     */ 
/* 1032:     */ 
/* 1033:     */ 
/* 1034:     */ 
/* 1035:1092 */       c = src[(++state.cp)];
/* 1036:1093 */       if (isDigit(c))
/* 1037:     */       {
/* 1038:1094 */         state.cp += 1;
/* 1039:1095 */         min = getDecimalValue(c, state, 65535, "msg.overlarge.min");
/* 1040:     */         
/* 1041:1097 */         c = src[state.cp];
/* 1042:1098 */         if (c == ',')
/* 1043:     */         {
/* 1044:1099 */           c = src[(++state.cp)];
/* 1045:1100 */           if (isDigit(c))
/* 1046:     */           {
/* 1047:1101 */             state.cp += 1;
/* 1048:1102 */             max = getDecimalValue(c, state, 65535, "msg.overlarge.max");
/* 1049:     */             
/* 1050:1104 */             c = src[state.cp];
/* 1051:1105 */             if (min > max)
/* 1052:     */             {
/* 1053:1106 */               reportError("msg.max.lt.min", String.valueOf(src[state.cp]));
/* 1054:     */               
/* 1055:1108 */               return false;
/* 1056:     */             }
/* 1057:     */           }
/* 1058:     */         }
/* 1059:     */         else
/* 1060:     */         {
/* 1061:1112 */           max = min;
/* 1062:     */         }
/* 1063:1115 */         if (c == '}')
/* 1064:     */         {
/* 1065:1116 */           state.result = new RENode((byte)6);
/* 1066:1117 */           state.result.min = min;
/* 1067:1118 */           state.result.max = max;
/* 1068:     */           
/* 1069:     */ 
/* 1070:1121 */           state.progLength += 12;
/* 1071:1122 */           hasQ = true;
/* 1072:     */         }
/* 1073:     */       }
/* 1074:1125 */       if (!hasQ) {
/* 1075:1126 */         state.cp = leftCurl;
/* 1076:     */       }
/* 1077:     */       break;
/* 1078:     */     }
/* 1079:1131 */     if (!hasQ) {
/* 1080:1132 */       return true;
/* 1081:     */     }
/* 1082:1134 */     state.cp += 1;
/* 1083:1135 */     state.result.kid = term;
/* 1084:1136 */     state.result.parenIndex = parenBaseCount;
/* 1085:1137 */     state.result.parenCount = (state.parenCount - parenBaseCount);
/* 1086:1138 */     if ((state.cp < state.cpend) && (src[state.cp] == '?'))
/* 1087:     */     {
/* 1088:1139 */       state.cp += 1;
/* 1089:1140 */       state.result.greedy = false;
/* 1090:     */     }
/* 1091:     */     else
/* 1092:     */     {
/* 1093:1143 */       state.result.greedy = true;
/* 1094:     */     }
/* 1095:1144 */     return true;
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   private static void resolveForwardJump(byte[] array, int from, int pc)
/* 1099:     */   {
/* 1100:1149 */     if (from > pc) {
/* 1101:1149 */       throw Kit.codeBug();
/* 1102:     */     }
/* 1103:1150 */     addIndex(array, from, pc - from);
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   private static int getOffset(byte[] array, int pc)
/* 1107:     */   {
/* 1108:1155 */     return getIndex(array, pc);
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   private static int addIndex(byte[] array, int pc, int index)
/* 1112:     */   {
/* 1113:1160 */     if (index < 0) {
/* 1114:1160 */       throw Kit.codeBug();
/* 1115:     */     }
/* 1116:1161 */     if (index > 65535) {
/* 1117:1162 */       throw Context.reportRuntimeError("Too complex regexp");
/* 1118:     */     }
/* 1119:1163 */     array[pc] = ((byte)(index >> 8));
/* 1120:1164 */     array[(pc + 1)] = ((byte)index);
/* 1121:1165 */     return pc + 2;
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   private static int getIndex(byte[] array, int pc)
/* 1125:     */   {
/* 1126:1170 */     return (array[pc] & 0xFF) << 8 | array[(pc + 1)] & 0xFF;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   private static int emitREBytecode(CompilerState state, RECompiled re, int pc, RENode t)
/* 1130:     */   {
/* 1131:1181 */     byte[] program = re.program;
/* 1132:1183 */     while (t != null)
/* 1133:     */     {
/* 1134:1184 */       program[(pc++)] = t.op;
/* 1135:     */       int nextTermFixup;
/* 1136:1185 */       switch (t.op)
/* 1137:     */       {
/* 1138:     */       case 0: 
/* 1139:1187 */         pc--;
/* 1140:1188 */         break;
/* 1141:     */       case 1: 
/* 1142:1190 */         RENode nextAlt = t.kid2;
/* 1143:1191 */         int nextAltFixup = pc;
/* 1144:1192 */         pc += 2;
/* 1145:1193 */         pc = emitREBytecode(state, re, pc, t.kid);
/* 1146:1194 */         program[(pc++)] = 23;
/* 1147:1195 */         nextTermFixup = pc;
/* 1148:1196 */         pc += 2;
/* 1149:1197 */         resolveForwardJump(program, nextAltFixup, pc);
/* 1150:1198 */         pc = emitREBytecode(state, re, pc, nextAlt);
/* 1151:     */         
/* 1152:1200 */         program[(pc++)] = 23;
/* 1153:1201 */         nextAltFixup = pc;
/* 1154:1202 */         pc += 2;
/* 1155:     */         
/* 1156:1204 */         resolveForwardJump(program, nextTermFixup, pc);
/* 1157:1205 */         resolveForwardJump(program, nextAltFixup, pc);
/* 1158:1206 */         break;
/* 1159:     */       case 21: 
/* 1160:1211 */         if (t.flatIndex != -1) {
/* 1161:1213 */           while ((t.next != null) && (t.next.op == 21) && (t.flatIndex + t.length == t.next.flatIndex))
/* 1162:     */           {
/* 1163:1215 */             t.length += t.next.length;
/* 1164:1216 */             t.next = t.next.next;
/* 1165:     */           }
/* 1166:     */         }
/* 1167:1219 */         if ((t.flatIndex != -1) && (t.length > 1))
/* 1168:     */         {
/* 1169:1220 */           if ((state.flags & 0x2) != 0) {
/* 1170:1221 */             program[(pc - 1)] = 32;
/* 1171:     */           } else {
/* 1172:1223 */             program[(pc - 1)] = 21;
/* 1173:     */           }
/* 1174:1224 */           pc = addIndex(program, pc, t.flatIndex);
/* 1175:1225 */           pc = addIndex(program, pc, t.length);
/* 1176:     */         }
/* 1177:1228 */         else if (t.chr < 'Ā')
/* 1178:     */         {
/* 1179:1229 */           if ((state.flags & 0x2) != 0) {
/* 1180:1230 */             program[(pc - 1)] = 33;
/* 1181:     */           } else {
/* 1182:1232 */             program[(pc - 1)] = 22;
/* 1183:     */           }
/* 1184:1233 */           program[(pc++)] = ((byte)t.chr);
/* 1185:     */         }
/* 1186:     */         else
/* 1187:     */         {
/* 1188:1236 */           if ((state.flags & 0x2) != 0) {
/* 1189:1237 */             program[(pc - 1)] = 35;
/* 1190:     */           } else {
/* 1191:1239 */             program[(pc - 1)] = 28;
/* 1192:     */           }
/* 1193:1240 */           pc = addIndex(program, pc, t.chr);
/* 1194:     */         }
/* 1195:1243 */         break;
/* 1196:     */       case 10: 
/* 1197:1245 */         pc = addIndex(program, pc, t.parenIndex);
/* 1198:1246 */         pc = emitREBytecode(state, re, pc, t.kid);
/* 1199:1247 */         program[(pc++)] = 11;
/* 1200:1248 */         pc = addIndex(program, pc, t.parenIndex);
/* 1201:1249 */         break;
/* 1202:     */       case 20: 
/* 1203:1251 */         pc = addIndex(program, pc, t.parenIndex);
/* 1204:1252 */         break;
/* 1205:     */       case 41: 
/* 1206:1254 */         nextTermFixup = pc;
/* 1207:1255 */         pc += 2;
/* 1208:1256 */         pc = emitREBytecode(state, re, pc, t.kid);
/* 1209:1257 */         program[(pc++)] = 43;
/* 1210:1258 */         resolveForwardJump(program, nextTermFixup, pc);
/* 1211:1259 */         break;
/* 1212:     */       case 42: 
/* 1213:1261 */         nextTermFixup = pc;
/* 1214:1262 */         pc += 2;
/* 1215:1263 */         pc = emitREBytecode(state, re, pc, t.kid);
/* 1216:1264 */         program[(pc++)] = 44;
/* 1217:1265 */         resolveForwardJump(program, nextTermFixup, pc);
/* 1218:1266 */         break;
/* 1219:     */       case 6: 
/* 1220:1268 */         if ((t.min == 0) && (t.max == -1))
/* 1221:     */         {
/* 1222:1269 */           program[(pc - 1)] = (t.greedy ? 7 : 45);
/* 1223:     */         }
/* 1224:1271 */         else if ((t.min == 0) && (t.max == 1))
/* 1225:     */         {
/* 1226:1272 */           program[(pc - 1)] = (t.greedy ? 9 : 47);
/* 1227:     */         }
/* 1228:1274 */         else if ((t.min == 1) && (t.max == -1))
/* 1229:     */         {
/* 1230:1275 */           program[(pc - 1)] = (t.greedy ? 8 : 46);
/* 1231:     */         }
/* 1232:     */         else
/* 1233:     */         {
/* 1234:1277 */           if (!t.greedy) {
/* 1235:1277 */             program[(pc - 1)] = 48;
/* 1236:     */           }
/* 1237:1278 */           pc = addIndex(program, pc, t.min);
/* 1238:     */           
/* 1239:1280 */           pc = addIndex(program, pc, t.max + 1);
/* 1240:     */         }
/* 1241:1282 */         pc = addIndex(program, pc, t.parenCount);
/* 1242:1283 */         pc = addIndex(program, pc, t.parenIndex);
/* 1243:1284 */         nextTermFixup = pc;
/* 1244:1285 */         pc += 2;
/* 1245:1286 */         pc = emitREBytecode(state, re, pc, t.kid);
/* 1246:1287 */         program[(pc++)] = 49;
/* 1247:1288 */         resolveForwardJump(program, nextTermFixup, pc);
/* 1248:1289 */         break;
/* 1249:     */       case 50: 
/* 1250:1291 */         pc = addIndex(program, pc, t.index);
/* 1251:1292 */         re.classList[t.index] = new RECharSet(t.bmsize, t.startIndex, t.kidlen);
/* 1252:     */         
/* 1253:1294 */         break;
/* 1254:     */       }
/* 1255:1298 */       t = t.next;
/* 1256:     */     }
/* 1257:1300 */     return pc;
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   private static void pushProgState(REGlobalData gData, int min, int max, REBackTrackData backTrackLastToSave, int continuation_pc, int continuation_op)
/* 1261:     */   {
/* 1262:1308 */     gData.stateStackTop = new REProgState(gData.stateStackTop, min, max, gData.cp, backTrackLastToSave, continuation_pc, continuation_op);
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   private static REProgState popProgState(REGlobalData gData)
/* 1266:     */   {
/* 1267:1317 */     REProgState state = gData.stateStackTop;
/* 1268:1318 */     gData.stateStackTop = state.previous;
/* 1269:1319 */     return state;
/* 1270:     */   }
/* 1271:     */   
/* 1272:     */   private static void pushBackTrackState(REGlobalData gData, byte op, int target)
/* 1273:     */   {
/* 1274:1325 */     gData.backTrackStackTop = new REBackTrackData(gData, op, target);
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   private static boolean flatNMatcher(REGlobalData gData, int matchChars, int length, String input, int end)
/* 1278:     */   {
/* 1279:1335 */     if (gData.cp + length > end) {
/* 1280:1336 */       return false;
/* 1281:     */     }
/* 1282:1337 */     for (int i = 0; i < length; i++) {
/* 1283:1338 */       if (gData.regexp.source[(matchChars + i)] != input.charAt(gData.cp + i)) {
/* 1284:1339 */         return false;
/* 1285:     */       }
/* 1286:     */     }
/* 1287:1342 */     gData.cp += length;
/* 1288:1343 */     return true;
/* 1289:     */   }
/* 1290:     */   
/* 1291:     */   private static boolean flatNIMatcher(REGlobalData gData, int matchChars, int length, String input, int end)
/* 1292:     */   {
/* 1293:1350 */     if (gData.cp + length > end) {
/* 1294:1351 */       return false;
/* 1295:     */     }
/* 1296:1352 */     for (int i = 0; i < length; i++) {
/* 1297:1353 */       if (upcase(gData.regexp.source[(matchChars + i)]) != upcase(input.charAt(gData.cp + i))) {
/* 1298:1356 */         return false;
/* 1299:     */       }
/* 1300:     */     }
/* 1301:1359 */     gData.cp += length;
/* 1302:1360 */     return true;
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   private static boolean backrefMatcher(REGlobalData gData, int parenIndex, String input, int end)
/* 1306:     */   {
/* 1307:1392 */     int parenContent = gData.parens_index(parenIndex);
/* 1308:1393 */     if (parenContent == -1) {
/* 1309:1394 */       return true;
/* 1310:     */     }
/* 1311:1396 */     int len = gData.parens_length(parenIndex);
/* 1312:1397 */     if (gData.cp + len > end) {
/* 1313:1398 */       return false;
/* 1314:     */     }
/* 1315:1400 */     if ((gData.regexp.flags & 0x2) != 0) {
/* 1316:1401 */       for (int i = 0; i < len; i++) {
/* 1317:1402 */         if (upcase(input.charAt(parenContent + i)) != upcase(input.charAt(gData.cp + i))) {
/* 1318:1403 */           return false;
/* 1319:     */         }
/* 1320:     */       }
/* 1321:     */     }
/* 1322:1407 */     for (int i = 0; i < len; i++) {
/* 1323:1408 */       if (input.charAt(parenContent + i) != input.charAt(gData.cp + i)) {
/* 1324:1409 */         return false;
/* 1325:     */       }
/* 1326:     */     }
/* 1327:1412 */     gData.cp += len;
/* 1328:1413 */     return true;
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   private static void addCharacterToCharSet(RECharSet cs, char c)
/* 1332:     */   {
/* 1333:1421 */     int byteIndex = c / '\b';
/* 1334:1422 */     if (c > cs.length) {
/* 1335:1423 */       throw new RuntimeException();
/* 1336:     */     }
/* 1337:1424 */     int tmp26_25 = byteIndex; byte[] tmp26_22 = cs.bits;tmp26_22[tmp26_25] = ((byte)(tmp26_22[tmp26_25] | '\001' << (c & 0x7)));
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   private static void addCharacterRangeToCharSet(RECharSet cs, char c1, char c2)
/* 1341:     */   {
/* 1342:1434 */     int byteIndex1 = c1 / '\b';
/* 1343:1435 */     int byteIndex2 = c2 / '\b';
/* 1344:1437 */     if ((c2 > cs.length) || (c1 > c2)) {
/* 1345:1438 */       throw new RuntimeException();
/* 1346:     */     }
/* 1347:1440 */     c1 = (char)(c1 & 0x7);
/* 1348:1441 */     c2 = (char)(c2 & 0x7);
/* 1349:1443 */     if (byteIndex1 == byteIndex2)
/* 1350:     */     {
/* 1351:1444 */       int tmp58_56 = byteIndex1; byte[] tmp58_53 = cs.bits;tmp58_53[tmp58_56] = ((byte)(tmp58_53[tmp58_56] | 255 >> 7 - (c2 - c1) << c1));
/* 1352:     */     }
/* 1353:     */     else
/* 1354:     */     {
/* 1355:1447 */       int tmp84_82 = byteIndex1; byte[] tmp84_79 = cs.bits;tmp84_79[tmp84_82] = ((byte)(tmp84_79[tmp84_82] | 'ÿ' << c1));
/* 1356:1448 */       for (int i = byteIndex1 + 1; i < byteIndex2; i++) {
/* 1357:1449 */         cs.bits[i] = -1;
/* 1358:     */       }
/* 1359:1450 */       int tmp124_122 = byteIndex2; byte[] tmp124_119 = cs.bits;tmp124_119[tmp124_122] = ((byte)(tmp124_119[tmp124_122] | 255 >> '\007' - c2));
/* 1360:     */     }
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   private static void processCharSet(REGlobalData gData, RECharSet charSet)
/* 1364:     */   {
/* 1365:1458 */     synchronized (charSet)
/* 1366:     */     {
/* 1367:1459 */       if (!charSet.converted)
/* 1368:     */       {
/* 1369:1460 */         processCharSetImpl(gData, charSet);
/* 1370:1461 */         charSet.converted = true;
/* 1371:     */       }
/* 1372:     */     }
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   private static void processCharSetImpl(REGlobalData gData, RECharSet charSet)
/* 1376:     */   {
/* 1377:1470 */     int src = charSet.startIndex;
/* 1378:1471 */     int end = src + charSet.strlength;
/* 1379:     */     
/* 1380:1473 */     char rangeStart = '\000';
/* 1381:     */     
/* 1382:     */ 
/* 1383:     */ 
/* 1384:     */ 
/* 1385:     */ 
/* 1386:1479 */     boolean inRange = false;
/* 1387:     */     
/* 1388:1481 */     charSet.sense = true;
/* 1389:1482 */     int byteLength = charSet.length / 8 + 1;
/* 1390:1483 */     charSet.bits = new byte[byteLength];
/* 1391:1485 */     if (src == end) {
/* 1392:1486 */       return;
/* 1393:     */     }
/* 1394:1488 */     if (gData.regexp.source[src] == '^')
/* 1395:     */     {
/* 1396:1489 */       charSet.sense = false;
/* 1397:1490 */       src++;
/* 1398:     */     }
/* 1399:1493 */     while (src != end)
/* 1400:     */     {
/* 1401:1494 */       int nDigits = 2;
/* 1402:     */       char thisCh;
/* 1403:1495 */       switch (gData.regexp.source[src])
/* 1404:     */       {
/* 1405:     */       case '\\': 
/* 1406:1497 */         src++;
/* 1407:1498 */         char c = gData.regexp.source[(src++)];
/* 1408:     */         char thisCh;
/* 1409:     */         int n;
/* 1410:     */         int i;
/* 1411:     */         int i;
/* 1412:1499 */         switch (c)
/* 1413:     */         {
/* 1414:     */         case 'b': 
/* 1415:1501 */           thisCh = '\b';
/* 1416:1502 */           break;
/* 1417:     */         case 'f': 
/* 1418:1504 */           thisCh = '\f';
/* 1419:1505 */           break;
/* 1420:     */         case 'n': 
/* 1421:1507 */           thisCh = '\n';
/* 1422:1508 */           break;
/* 1423:     */         case 'r': 
/* 1424:1510 */           thisCh = '\r';
/* 1425:1511 */           break;
/* 1426:     */         case 't': 
/* 1427:1513 */           thisCh = '\t';
/* 1428:1514 */           break;
/* 1429:     */         case 'v': 
/* 1430:1516 */           thisCh = '\013';
/* 1431:1517 */           break;
/* 1432:     */         case 'c': 
/* 1433:1519 */           if ((src + 1 < end) && (isWord(gData.regexp.source[(src + 1)])))
/* 1434:     */           {
/* 1435:1520 */             thisCh = (char)(gData.regexp.source[(src++)] & 0x1F);
/* 1436:     */           }
/* 1437:     */           else
/* 1438:     */           {
/* 1439:1522 */             src--;
/* 1440:1523 */             thisCh = '\\';
/* 1441:     */           }
/* 1442:1525 */           break;
/* 1443:     */         case 'u': 
/* 1444:1527 */           nDigits += 2;
/* 1445:     */         case 'x': 
/* 1446:1530 */           n = 0;
/* 1447:1531 */           for (i = 0; (i < nDigits) && (src < end); i++)
/* 1448:     */           {
/* 1449:1532 */             c = gData.regexp.source[(src++)];
/* 1450:1533 */             int digit = toASCIIHexDigit(c);
/* 1451:1534 */             if (digit < 0)
/* 1452:     */             {
/* 1453:1538 */               src -= i + 1;
/* 1454:1539 */               n = 92;
/* 1455:1540 */               break;
/* 1456:     */             }
/* 1457:1542 */             n = n << 4 | digit;
/* 1458:     */           }
/* 1459:1544 */           thisCh = (char)n;
/* 1460:1545 */           break;
/* 1461:     */         case '0': 
/* 1462:     */         case '1': 
/* 1463:     */         case '2': 
/* 1464:     */         case '3': 
/* 1465:     */         case '4': 
/* 1466:     */         case '5': 
/* 1467:     */         case '6': 
/* 1468:     */         case '7': 
/* 1469:1560 */           n = c - '0';
/* 1470:1561 */           c = gData.regexp.source[src];
/* 1471:1562 */           if (('0' <= c) && (c <= '7'))
/* 1472:     */           {
/* 1473:1563 */             src++;
/* 1474:1564 */             n = 8 * n + (c - '0');
/* 1475:1565 */             c = gData.regexp.source[src];
/* 1476:1566 */             if (('0' <= c) && (c <= '7'))
/* 1477:     */             {
/* 1478:1567 */               src++;
/* 1479:1568 */               i = 8 * n + (c - '0');
/* 1480:1569 */               if (i <= 255) {
/* 1481:1570 */                 n = i;
/* 1482:     */               } else {
/* 1483:1572 */                 src--;
/* 1484:     */               }
/* 1485:     */             }
/* 1486:     */           }
/* 1487:1575 */           thisCh = (char)n;
/* 1488:1576 */           break;
/* 1489:     */         case 'd': 
/* 1490:1579 */           addCharacterRangeToCharSet(charSet, '0', '9');
/* 1491:1580 */           break;
/* 1492:     */         case 'D': 
/* 1493:1582 */           addCharacterRangeToCharSet(charSet, '\000', '/');
/* 1494:1583 */           addCharacterRangeToCharSet(charSet, ':', (char)charSet.length);
/* 1495:     */           
/* 1496:1585 */           break;
/* 1497:     */         case 's': 
/* 1498:1587 */           for (i = charSet.length; i >= 0; i--) {
/* 1499:1588 */             if (isREWhiteSpace(i)) {
/* 1500:1589 */               addCharacterToCharSet(charSet, (char)i);
/* 1501:     */             }
/* 1502:     */           }
/* 1503:     */         case 'S': 
/* 1504:1592 */           for (i = charSet.length; i >= 0; i--) {
/* 1505:1593 */             if (!isREWhiteSpace(i)) {
/* 1506:1594 */               addCharacterToCharSet(charSet, (char)i);
/* 1507:     */             }
/* 1508:     */           }
/* 1509:     */         case 'w': 
/* 1510:1597 */           for (i = charSet.length; i >= 0; i--) {
/* 1511:1598 */             if (isWord((char)i)) {
/* 1512:1599 */               addCharacterToCharSet(charSet, (char)i);
/* 1513:     */             }
/* 1514:     */           }
/* 1515:     */         case 'W': 
/* 1516:1602 */           for (i = charSet.length; i >= 0; i--) {
/* 1517:1603 */             if (!isWord((char)i)) {
/* 1518:1604 */               addCharacterToCharSet(charSet, (char)i);
/* 1519:     */             }
/* 1520:     */           }
/* 1521:     */         case '8': 
/* 1522:     */         case '9': 
/* 1523:     */         case ':': 
/* 1524:     */         case ';': 
/* 1525:     */         case '<': 
/* 1526:     */         case '=': 
/* 1527:     */         case '>': 
/* 1528:     */         case '?': 
/* 1529:     */         case '@': 
/* 1530:     */         case 'A': 
/* 1531:     */         case 'B': 
/* 1532:     */         case 'C': 
/* 1533:     */         case 'E': 
/* 1534:     */         case 'F': 
/* 1535:     */         case 'G': 
/* 1536:     */         case 'H': 
/* 1537:     */         case 'I': 
/* 1538:     */         case 'J': 
/* 1539:     */         case 'K': 
/* 1540:     */         case 'L': 
/* 1541:     */         case 'M': 
/* 1542:     */         case 'N': 
/* 1543:     */         case 'O': 
/* 1544:     */         case 'P': 
/* 1545:     */         case 'Q': 
/* 1546:     */         case 'R': 
/* 1547:     */         case 'T': 
/* 1548:     */         case 'U': 
/* 1549:     */         case 'V': 
/* 1550:     */         case 'X': 
/* 1551:     */         case 'Y': 
/* 1552:     */         case 'Z': 
/* 1553:     */         case '[': 
/* 1554:     */         case '\\': 
/* 1555:     */         case ']': 
/* 1556:     */         case '^': 
/* 1557:     */         case '_': 
/* 1558:     */         case '`': 
/* 1559:     */         case 'a': 
/* 1560:     */         case 'e': 
/* 1561:     */         case 'g': 
/* 1562:     */         case 'h': 
/* 1563:     */         case 'i': 
/* 1564:     */         case 'j': 
/* 1565:     */         case 'k': 
/* 1566:     */         case 'l': 
/* 1567:     */         case 'm': 
/* 1568:     */         case 'o': 
/* 1569:     */         case 'p': 
/* 1570:     */         case 'q': 
/* 1571:     */         default: 
/* 1572:1607 */           thisCh = c;
/* 1573:     */         }
/* 1574:1608 */         break;
/* 1575:     */       default: 
/* 1576:1614 */         thisCh = gData.regexp.source[(src++)];
/* 1577:1618 */         if (inRange)
/* 1578:     */         {
/* 1579:1619 */           if ((gData.regexp.flags & 0x2) != 0)
/* 1580:     */           {
/* 1581:1620 */             addCharacterRangeToCharSet(charSet, upcase(rangeStart), upcase(thisCh));
/* 1582:     */             
/* 1583:     */ 
/* 1584:1623 */             addCharacterRangeToCharSet(charSet, downcase(rangeStart), downcase(thisCh));
/* 1585:     */           }
/* 1586:     */           else
/* 1587:     */           {
/* 1588:1627 */             addCharacterRangeToCharSet(charSet, rangeStart, thisCh);
/* 1589:     */           }
/* 1590:1629 */           inRange = false;
/* 1591:     */         }
/* 1592:     */         else
/* 1593:     */         {
/* 1594:1632 */           if ((gData.regexp.flags & 0x2) != 0)
/* 1595:     */           {
/* 1596:1633 */             addCharacterToCharSet(charSet, upcase(thisCh));
/* 1597:1634 */             addCharacterToCharSet(charSet, downcase(thisCh));
/* 1598:     */           }
/* 1599:     */           else
/* 1600:     */           {
/* 1601:1636 */             addCharacterToCharSet(charSet, thisCh);
/* 1602:     */           }
/* 1603:1638 */           if ((src < end - 1) && 
/* 1604:1639 */             (gData.regexp.source[src] == '-'))
/* 1605:     */           {
/* 1606:1640 */             src++;
/* 1607:1641 */             inRange = true;
/* 1608:1642 */             rangeStart = thisCh;
/* 1609:     */           }
/* 1610:     */         }
/* 1611:     */         break;
/* 1612:     */       }
/* 1613:     */     }
/* 1614:     */   }
/* 1615:     */   
/* 1616:     */   private static boolean classMatcher(REGlobalData gData, RECharSet charSet, char ch)
/* 1617:     */   {
/* 1618:1657 */     if (!charSet.converted) {
/* 1619:1658 */       processCharSet(gData, charSet);
/* 1620:     */     }
/* 1621:1661 */     int byteIndex = ch / '\b';
/* 1622:1662 */     if (charSet.sense)
/* 1623:     */     {
/* 1624:1663 */       if ((charSet.length == 0) || (ch > charSet.length) || ((charSet.bits[byteIndex] & '\001' << (ch & 0x7)) == 0)) {
/* 1625:1666 */         return false;
/* 1626:     */       }
/* 1627:     */     }
/* 1628:1668 */     else if ((charSet.length != 0) && (ch <= charSet.length) && ((charSet.bits[byteIndex] & '\001' << (ch & 0x7)) != 0)) {
/* 1629:1671 */       return false;
/* 1630:     */     }
/* 1631:1673 */     return true;
/* 1632:     */   }
/* 1633:     */   
/* 1634:     */   private static boolean executeREBytecode(REGlobalData gData, String input, int end)
/* 1635:     */   {
/* 1636:1679 */     int pc = 0;
/* 1637:1680 */     byte[] program = gData.regexp.program;
/* 1638:     */     
/* 1639:     */ 
/* 1640:1683 */     boolean result = false;
/* 1641:     */     
/* 1642:1685 */     int currentContinuation_pc = 0;
/* 1643:1686 */     int currentContinuation_op = 53;
/* 1644:     */     
/* 1645:     */ 
/* 1646:     */ 
/* 1647:1690 */     int op = program[(pc++)];
/* 1648:     */     for (;;)
/* 1649:     */     {
/* 1650:1695 */       switch (op)
/* 1651:     */       {
/* 1652:     */       case 0: 
/* 1653:1697 */         result = true;
/* 1654:1698 */         break;
/* 1655:     */       case 2: 
/* 1656:1700 */         if (gData.cp != 0) {
/* 1657:1701 */           if ((gData.multiline) || ((gData.regexp.flags & 0x4) != 0))
/* 1658:     */           {
/* 1659:1703 */             if (!isLineTerm(input.charAt(gData.cp - 1)))
/* 1660:     */             {
/* 1661:1704 */               result = false;
/* 1662:     */               break label2476;
/* 1663:     */             }
/* 1664:     */           }
/* 1665:     */           else
/* 1666:     */           {
/* 1667:1709 */             result = false;
/* 1668:     */             break label2476;
/* 1669:     */           }
/* 1670:     */         }
/* 1671:1713 */         result = true;
/* 1672:1714 */         break;
/* 1673:     */       case 3: 
/* 1674:1716 */         if (gData.cp != end) {
/* 1675:1717 */           if ((gData.multiline) || ((gData.regexp.flags & 0x4) != 0))
/* 1676:     */           {
/* 1677:1719 */             if (!isLineTerm(input.charAt(gData.cp)))
/* 1678:     */             {
/* 1679:1720 */               result = false;
/* 1680:     */               break label2476;
/* 1681:     */             }
/* 1682:     */           }
/* 1683:     */           else
/* 1684:     */           {
/* 1685:1725 */             result = false;
/* 1686:     */             break label2476;
/* 1687:     */           }
/* 1688:     */         }
/* 1689:1729 */         result = true;
/* 1690:1730 */         break;
/* 1691:     */       case 4: 
/* 1692:1732 */         result = ((gData.cp == 0) || (!isWord(input.charAt(gData.cp - 1))) ? 1 : 0) ^ ((gData.cp >= end) || (!isWord(input.charAt(gData.cp))) ? 1 : 0);
/* 1693:     */         
/* 1694:1734 */         break;
/* 1695:     */       case 5: 
/* 1696:1736 */         result = ((gData.cp == 0) || (!isWord(input.charAt(gData.cp - 1))) ? 1 : 0) ^ ((gData.cp < end) && (isWord(input.charAt(gData.cp))) ? 1 : 0);
/* 1697:     */         
/* 1698:1738 */         break;
/* 1699:     */       case 12: 
/* 1700:1740 */         result = (gData.cp != end) && (!isLineTerm(input.charAt(gData.cp)));
/* 1701:1741 */         if (result) {
/* 1702:1742 */           gData.cp += 1;
/* 1703:     */         }
/* 1704:     */         break;
/* 1705:     */       case 14: 
/* 1706:1746 */         result = (gData.cp != end) && (isDigit(input.charAt(gData.cp)));
/* 1707:1747 */         if (result) {
/* 1708:1748 */           gData.cp += 1;
/* 1709:     */         }
/* 1710:     */         break;
/* 1711:     */       case 15: 
/* 1712:1752 */         result = (gData.cp != end) && (!isDigit(input.charAt(gData.cp)));
/* 1713:1753 */         if (result) {
/* 1714:1754 */           gData.cp += 1;
/* 1715:     */         }
/* 1716:     */         break;
/* 1717:     */       case 18: 
/* 1718:1758 */         result = (gData.cp != end) && (isREWhiteSpace(input.charAt(gData.cp)));
/* 1719:1759 */         if (result) {
/* 1720:1760 */           gData.cp += 1;
/* 1721:     */         }
/* 1722:     */         break;
/* 1723:     */       case 19: 
/* 1724:1764 */         result = (gData.cp != end) && (!isREWhiteSpace(input.charAt(gData.cp)));
/* 1725:1765 */         if (result) {
/* 1726:1766 */           gData.cp += 1;
/* 1727:     */         }
/* 1728:     */         break;
/* 1729:     */       case 16: 
/* 1730:1770 */         result = (gData.cp != end) && (isWord(input.charAt(gData.cp)));
/* 1731:1771 */         if (result) {
/* 1732:1772 */           gData.cp += 1;
/* 1733:     */         }
/* 1734:     */         break;
/* 1735:     */       case 17: 
/* 1736:1776 */         result = (gData.cp != end) && (!isWord(input.charAt(gData.cp)));
/* 1737:1777 */         if (result) {
/* 1738:1778 */           gData.cp += 1;
/* 1739:     */         }
/* 1740:     */         break;
/* 1741:     */       case 21: 
/* 1742:1783 */         int offset = getIndex(program, pc);
/* 1743:1784 */         pc += 2;
/* 1744:1785 */         int length = getIndex(program, pc);
/* 1745:1786 */         pc += 2;
/* 1746:1787 */         result = flatNMatcher(gData, offset, length, input, end);
/* 1747:     */         
/* 1748:1789 */         break;
/* 1749:     */       case 32: 
/* 1750:1792 */         int offset = getIndex(program, pc);
/* 1751:1793 */         pc += 2;
/* 1752:1794 */         int length = getIndex(program, pc);
/* 1753:1795 */         pc += 2;
/* 1754:1796 */         result = flatNIMatcher(gData, offset, length, input, end);
/* 1755:     */         
/* 1756:1798 */         break;
/* 1757:     */       case 22: 
/* 1758:1801 */         char matchCh = (char)(program[(pc++)] & 0xFF);
/* 1759:1802 */         result = (gData.cp != end) && (input.charAt(gData.cp) == matchCh);
/* 1760:1803 */         if (result) {
/* 1761:1804 */           gData.cp += 1;
/* 1762:     */         }
/* 1763:1807 */         break;
/* 1764:     */       case 33: 
/* 1765:1810 */         char matchCh = (char)(program[(pc++)] & 0xFF);
/* 1766:1811 */         result = (gData.cp != end) && (upcase(input.charAt(gData.cp)) == upcase(matchCh));
/* 1767:1813 */         if (result) {
/* 1768:1814 */           gData.cp += 1;
/* 1769:     */         }
/* 1770:1817 */         break;
/* 1771:     */       case 28: 
/* 1772:1820 */         char matchCh = (char)getIndex(program, pc);
/* 1773:1821 */         pc += 2;
/* 1774:1822 */         result = (gData.cp != end) && (input.charAt(gData.cp) == matchCh);
/* 1775:1823 */         if (result) {
/* 1776:1824 */           gData.cp += 1;
/* 1777:     */         }
/* 1778:1827 */         break;
/* 1779:     */       case 35: 
/* 1780:1830 */         char matchCh = (char)getIndex(program, pc);
/* 1781:1831 */         pc += 2;
/* 1782:1832 */         result = (gData.cp != end) && (upcase(input.charAt(gData.cp)) == upcase(matchCh));
/* 1783:1834 */         if (result) {
/* 1784:1835 */           gData.cp += 1;
/* 1785:     */         }
/* 1786:1838 */         break;
/* 1787:     */       case 1: 
/* 1788:1843 */         pushProgState(gData, 0, 0, null, currentContinuation_pc, currentContinuation_op);
/* 1789:     */         
/* 1790:     */ 
/* 1791:1846 */         int nextpc = pc + getOffset(program, pc);
/* 1792:1847 */         byte nextop = program[(nextpc++)];
/* 1793:1848 */         pushBackTrackState(gData, nextop, nextpc);
/* 1794:1849 */         pc += 2;
/* 1795:1850 */         op = program[(pc++)];
/* 1796:     */         
/* 1797:1852 */         break;
/* 1798:     */       case 23: 
/* 1799:1857 */         REProgState state = popProgState(gData);
/* 1800:1858 */         currentContinuation_pc = state.continuation_pc;
/* 1801:1859 */         currentContinuation_op = state.continuation_op;
/* 1802:1860 */         int offset = getOffset(program, pc);
/* 1803:1861 */         pc += offset;
/* 1804:1862 */         op = program[(pc++)];
/* 1805:     */         
/* 1806:1864 */         break;
/* 1807:     */       case 10: 
/* 1808:1869 */         int parenIndex = getIndex(program, pc);
/* 1809:1870 */         pc += 2;
/* 1810:1871 */         gData.set_parens(parenIndex, gData.cp, 0);
/* 1811:1872 */         op = program[(pc++)];
/* 1812:     */         
/* 1813:1874 */         break;
/* 1814:     */       case 11: 
/* 1815:1878 */         int parenIndex = getIndex(program, pc);
/* 1816:1879 */         pc += 2;
/* 1817:1880 */         int cap_index = gData.parens_index(parenIndex);
/* 1818:1881 */         gData.set_parens(parenIndex, cap_index, gData.cp - cap_index);
/* 1819:1883 */         if (parenIndex > gData.lastParen) {
/* 1820:1884 */           gData.lastParen = parenIndex;
/* 1821:     */         }
/* 1822:1885 */         op = program[(pc++)];
/* 1823:     */         
/* 1824:1887 */         break;
/* 1825:     */       case 20: 
/* 1826:1890 */         int parenIndex = getIndex(program, pc);
/* 1827:1891 */         pc += 2;
/* 1828:1892 */         result = backrefMatcher(gData, parenIndex, input, end);
/* 1829:     */         
/* 1830:1894 */         break;
/* 1831:     */       case 50: 
/* 1832:1898 */         int index = getIndex(program, pc);
/* 1833:1899 */         pc += 2;
/* 1834:1900 */         if ((gData.cp != end) && 
/* 1835:1901 */           (classMatcher(gData, gData.regexp.classList[index], input.charAt(gData.cp))))
/* 1836:     */         {
/* 1837:1904 */           gData.cp += 1;
/* 1838:1905 */           result = true;
/* 1839:     */         }
/* 1840:     */         else
/* 1841:     */         {
/* 1842:1909 */           result = false;
/* 1843:     */         }
/* 1844:1911 */         break;
/* 1845:     */       case 41: 
/* 1846:     */       case 42: 
/* 1847:1917 */         pushProgState(gData, 0, 0, gData.backTrackStackTop, currentContinuation_pc, currentContinuation_op);
/* 1848:     */         byte testOp;
/* 1849:     */         byte testOp;
/* 1850:1920 */         if (op == 41) {
/* 1851:1921 */           testOp = 43;
/* 1852:     */         } else {
/* 1853:1923 */           testOp = 44;
/* 1854:     */         }
/* 1855:1925 */         pushBackTrackState(gData, testOp, pc + getOffset(program, pc));
/* 1856:     */         
/* 1857:1927 */         pc += 2;
/* 1858:1928 */         op = program[(pc++)];
/* 1859:     */         
/* 1860:1930 */         break;
/* 1861:     */       case 43: 
/* 1862:     */       case 44: 
/* 1863:1935 */         REProgState state = popProgState(gData);
/* 1864:1936 */         gData.cp = state.index;
/* 1865:1937 */         gData.backTrackStackTop = state.backTrack;
/* 1866:1938 */         currentContinuation_pc = state.continuation_pc;
/* 1867:1939 */         currentContinuation_op = state.continuation_op;
/* 1868:1940 */         if (result)
/* 1869:     */         {
/* 1870:1941 */           if (op == 43) {
/* 1871:1942 */             result = true;
/* 1872:     */           } else {
/* 1873:1944 */             result = false;
/* 1874:     */           }
/* 1875:     */         }
/* 1876:1947 */         else if (op != 43) {
/* 1877:1950 */           result = true;
/* 1878:     */         }
/* 1879:1954 */         break;
/* 1880:     */       case 6: 
/* 1881:     */       case 7: 
/* 1882:     */       case 8: 
/* 1883:     */       case 9: 
/* 1884:     */       case 45: 
/* 1885:     */       case 46: 
/* 1886:     */       case 47: 
/* 1887:     */       case 48: 
/* 1888:1966 */         boolean greedy = false;
/* 1889:     */         int min;
/* 1890:     */         int max;
/* 1891:1967 */         switch (op)
/* 1892:     */         {
/* 1893:     */         case 7: 
/* 1894:1969 */           greedy = true;
/* 1895:     */         case 45: 
/* 1896:1972 */           min = 0;
/* 1897:1973 */           max = -1;
/* 1898:1974 */           break;
/* 1899:     */         case 8: 
/* 1900:1976 */           greedy = true;
/* 1901:     */         case 46: 
/* 1902:1979 */           min = 1;
/* 1903:1980 */           max = -1;
/* 1904:1981 */           break;
/* 1905:     */         case 9: 
/* 1906:1983 */           greedy = true;
/* 1907:     */         case 47: 
/* 1908:1986 */           min = 0;
/* 1909:1987 */           max = 1;
/* 1910:1988 */           break;
/* 1911:     */         case 6: 
/* 1912:1990 */           greedy = true;
/* 1913:     */         case 48: 
/* 1914:1993 */           min = getOffset(program, pc);
/* 1915:1994 */           pc += 2;
/* 1916:     */           
/* 1917:1996 */           max = getOffset(program, pc) - 1;
/* 1918:1997 */           pc += 2;
/* 1919:1998 */           break;
/* 1920:     */         default: 
/* 1921:2000 */           throw Kit.codeBug();
/* 1922:     */         }
/* 1923:2002 */         pushProgState(gData, min, max, null, currentContinuation_pc, currentContinuation_op);
/* 1924:2005 */         if (greedy)
/* 1925:     */         {
/* 1926:2006 */           currentContinuation_op = 51;
/* 1927:2007 */           currentContinuation_pc = pc;
/* 1928:2008 */           pushBackTrackState(gData, (byte)51, pc);
/* 1929:     */           
/* 1930:2010 */           pc += 6;
/* 1931:2011 */           op = program[(pc++)];
/* 1932:     */         }
/* 1933:2013 */         else if (min != 0)
/* 1934:     */         {
/* 1935:2014 */           currentContinuation_op = 52;
/* 1936:2015 */           currentContinuation_pc = pc;
/* 1937:     */           
/* 1938:2017 */           pc += 6;
/* 1939:2018 */           op = program[(pc++)];
/* 1940:     */         }
/* 1941:     */         else
/* 1942:     */         {
/* 1943:2020 */           pushBackTrackState(gData, (byte)52, pc);
/* 1944:2021 */           popProgState(gData);
/* 1945:2022 */           pc += 4;
/* 1946:2023 */           pc += getOffset(program, pc);
/* 1947:2024 */           op = program[(pc++)];
/* 1948:     */         }
/* 1949:2028 */         break;
/* 1950:     */       case 49: 
/* 1951:2032 */         pc = currentContinuation_pc;
/* 1952:2033 */         op = currentContinuation_op;
/* 1953:2034 */         break;
/* 1954:     */       case 51: 
/* 1955:2038 */         REProgState state = popProgState(gData);
/* 1956:2039 */         if (!result)
/* 1957:     */         {
/* 1958:2044 */           if (state.min == 0) {
/* 1959:2045 */             result = true;
/* 1960:     */           }
/* 1961:2046 */           currentContinuation_pc = state.continuation_pc;
/* 1962:2047 */           currentContinuation_op = state.continuation_op;
/* 1963:2048 */           pc += 4;
/* 1964:2049 */           pc += getOffset(program, pc);
/* 1965:     */         }
/* 1966:2053 */         else if ((state.min == 0) && (gData.cp == state.index))
/* 1967:     */         {
/* 1968:2055 */           result = false;
/* 1969:2056 */           currentContinuation_pc = state.continuation_pc;
/* 1970:2057 */           currentContinuation_op = state.continuation_op;
/* 1971:2058 */           pc += 4;
/* 1972:2059 */           pc += getOffset(program, pc);
/* 1973:     */         }
/* 1974:     */         else
/* 1975:     */         {
/* 1976:2062 */           int new_min = state.min;int new_max = state.max;
/* 1977:2063 */           if (new_min != 0) {
/* 1978:2063 */             new_min--;
/* 1979:     */           }
/* 1980:2064 */           if (new_max != -1) {
/* 1981:2064 */             new_max--;
/* 1982:     */           }
/* 1983:2065 */           if (new_max == 0)
/* 1984:     */           {
/* 1985:2066 */             result = true;
/* 1986:2067 */             currentContinuation_pc = state.continuation_pc;
/* 1987:2068 */             currentContinuation_op = state.continuation_op;
/* 1988:2069 */             pc += 4;
/* 1989:2070 */             pc += getOffset(program, pc);
/* 1990:     */           }
/* 1991:     */           else
/* 1992:     */           {
/* 1993:2073 */             pushProgState(gData, new_min, new_max, null, state.continuation_pc, state.continuation_op);
/* 1994:     */             
/* 1995:     */ 
/* 1996:2076 */             currentContinuation_op = 51;
/* 1997:2077 */             currentContinuation_pc = pc;
/* 1998:2078 */             pushBackTrackState(gData, (byte)51, pc);
/* 1999:2079 */             int parenCount = getIndex(program, pc);
/* 2000:2080 */             pc += 2;
/* 2001:2081 */             int parenIndex = getIndex(program, pc);
/* 2002:2082 */             pc += 4;
/* 2003:2083 */             op = program[(pc++)];
/* 2004:2084 */             for (int k = 0; k < parenCount; k++) {
/* 2005:2085 */               gData.set_parens(parenIndex + k, -1, 0);
/* 2006:     */             }
/* 2007:     */           }
/* 2008:     */         }
/* 2009:2089 */         break;
/* 2010:     */       case 52: 
/* 2011:2093 */         REProgState state = popProgState(gData);
/* 2012:2094 */         if (!result)
/* 2013:     */         {
/* 2014:2098 */           if ((state.max == -1) || (state.max > 0))
/* 2015:     */           {
/* 2016:2099 */             pushProgState(gData, state.min, state.max, null, state.continuation_pc, state.continuation_op);
/* 2017:     */             
/* 2018:     */ 
/* 2019:2102 */             currentContinuation_op = 52;
/* 2020:2103 */             currentContinuation_pc = pc;
/* 2021:2104 */             int parenCount = getIndex(program, pc);
/* 2022:2105 */             pc += 2;
/* 2023:2106 */             int parenIndex = getIndex(program, pc);
/* 2024:2107 */             pc += 4;
/* 2025:2108 */             for (int k = 0; k < parenCount; k++) {
/* 2026:2109 */               gData.set_parens(parenIndex + k, -1, 0);
/* 2027:     */             }
/* 2028:2111 */             op = program[(pc++)];
/* 2029:2112 */             continue;
/* 2030:     */           }
/* 2031:2115 */           currentContinuation_pc = state.continuation_pc;
/* 2032:2116 */           currentContinuation_op = state.continuation_op;
/* 2033:     */         }
/* 2034:2120 */         else if ((state.min == 0) && (gData.cp == state.index))
/* 2035:     */         {
/* 2036:2122 */           result = false;
/* 2037:2123 */           currentContinuation_pc = state.continuation_pc;
/* 2038:2124 */           currentContinuation_op = state.continuation_op;
/* 2039:     */         }
/* 2040:     */         else
/* 2041:     */         {
/* 2042:2127 */           int new_min = state.min;int new_max = state.max;
/* 2043:2128 */           if (new_min != 0) {
/* 2044:2128 */             new_min--;
/* 2045:     */           }
/* 2046:2129 */           if (new_max != -1) {
/* 2047:2129 */             new_max--;
/* 2048:     */           }
/* 2049:2130 */           pushProgState(gData, new_min, new_max, null, state.continuation_pc, state.continuation_op);
/* 2050:2133 */           if (new_min != 0)
/* 2051:     */           {
/* 2052:2134 */             currentContinuation_op = 52;
/* 2053:2135 */             currentContinuation_pc = pc;
/* 2054:2136 */             int parenCount = getIndex(program, pc);
/* 2055:2137 */             pc += 2;
/* 2056:2138 */             int parenIndex = getIndex(program, pc);
/* 2057:2139 */             pc += 4;
/* 2058:2140 */             for (int k = 0; k < parenCount; k++) {
/* 2059:2141 */               gData.set_parens(parenIndex + k, -1, 0);
/* 2060:     */             }
/* 2061:2143 */             op = program[(pc++)];
/* 2062:2144 */             continue;
/* 2063:     */           }
/* 2064:2145 */           currentContinuation_pc = state.continuation_pc;
/* 2065:2146 */           currentContinuation_op = state.continuation_op;
/* 2066:2147 */           pushBackTrackState(gData, (byte)52, pc);
/* 2067:2148 */           popProgState(gData);
/* 2068:2149 */           pc += 4;
/* 2069:2150 */           pc += getOffset(program, pc);
/* 2070:2151 */           op = program[(pc++)];
/* 2071:     */         }
/* 2072:2153 */         break;
/* 2073:     */       case 53: 
/* 2074:2158 */         return true;
/* 2075:     */       case 13: 
/* 2076:     */       case 24: 
/* 2077:     */       case 25: 
/* 2078:     */       case 26: 
/* 2079:     */       case 27: 
/* 2080:     */       case 29: 
/* 2081:     */       case 30: 
/* 2082:     */       case 31: 
/* 2083:     */       case 34: 
/* 2084:     */       case 36: 
/* 2085:     */       case 37: 
/* 2086:     */       case 38: 
/* 2087:     */       case 39: 
/* 2088:     */       case 40: 
/* 2089:     */       default: 
/* 2090:2161 */         throw Kit.codeBug();
/* 2091:     */         label2476:
/* 2092:2168 */         if (!result)
/* 2093:     */         {
/* 2094:2169 */           REBackTrackData backTrackData = gData.backTrackStackTop;
/* 2095:2170 */           if (backTrackData != null)
/* 2096:     */           {
/* 2097:2171 */             gData.backTrackStackTop = backTrackData.previous;
/* 2098:     */             
/* 2099:2173 */             gData.lastParen = backTrackData.lastParen;
/* 2100:2177 */             if (backTrackData.parens != null) {
/* 2101:2178 */               gData.parens = ((long[])backTrackData.parens.clone());
/* 2102:     */             }
/* 2103:2181 */             gData.cp = backTrackData.cp;
/* 2104:     */             
/* 2105:2183 */             gData.stateStackTop = backTrackData.stateStackTop;
/* 2106:     */             
/* 2107:2185 */             currentContinuation_op = gData.stateStackTop.continuation_op;
/* 2108:     */             
/* 2109:2187 */             currentContinuation_pc = gData.stateStackTop.continuation_pc;
/* 2110:     */             
/* 2111:2189 */             pc = backTrackData.continuation_pc;
/* 2112:2190 */             op = backTrackData.continuation_op;
/* 2113:     */           }
/* 2114:     */           else
/* 2115:     */           {
/* 2116:2194 */             return false;
/* 2117:     */           }
/* 2118:     */         }
/* 2119:     */         else
/* 2120:     */         {
/* 2121:2197 */           op = program[(pc++)];
/* 2122:     */         }
/* 2123:     */         break;
/* 2124:     */       }
/* 2125:     */     }
/* 2126:     */   }
/* 2127:     */   
/* 2128:     */   private static boolean matchRegExp(REGlobalData gData, RECompiled re, String input, int start, int end, boolean multiline)
/* 2129:     */   {
/* 2130:2206 */     if (re.parenCount != 0) {
/* 2131:2207 */       gData.parens = new long[re.parenCount];
/* 2132:     */     } else {
/* 2133:2209 */       gData.parens = null;
/* 2134:     */     }
/* 2135:2212 */     gData.backTrackStackTop = null;
/* 2136:     */     
/* 2137:2214 */     gData.stateStackTop = null;
/* 2138:     */     
/* 2139:2216 */     gData.multiline = multiline;
/* 2140:2217 */     gData.regexp = re;
/* 2141:2218 */     gData.lastParen = 0;
/* 2142:     */     
/* 2143:2220 */     int anchorCh = gData.regexp.anchorCh;
/* 2144:2225 */     for (int i = start; i <= end; i++)
/* 2145:     */     {
/* 2146:2231 */       if (anchorCh >= 0) {
/* 2147:     */         for (;;)
/* 2148:     */         {
/* 2149:2233 */           if (i == end) {
/* 2150:2234 */             return false;
/* 2151:     */           }
/* 2152:2236 */           char matchCh = input.charAt(i);
/* 2153:2237 */           if ((matchCh == anchorCh) || (((gData.regexp.flags & 0x2) != 0) && (upcase(matchCh) == upcase((char)anchorCh)))) {
/* 2154:     */             break;
/* 2155:     */           }
/* 2156:2243 */           i++;
/* 2157:     */         }
/* 2158:     */       }
/* 2159:2246 */       gData.cp = i;
/* 2160:2247 */       for (int j = 0; j < re.parenCount; j++) {
/* 2161:2248 */         gData.set_parens(j, -1, 0);
/* 2162:     */       }
/* 2163:2250 */       boolean result = executeREBytecode(gData, input, end);
/* 2164:     */       
/* 2165:2252 */       gData.backTrackStackTop = null;
/* 2166:2253 */       gData.stateStackTop = null;
/* 2167:2254 */       if (result)
/* 2168:     */       {
/* 2169:2255 */         gData.skipped = (i - start);
/* 2170:2256 */         return true;
/* 2171:     */       }
/* 2172:     */     }
/* 2173:2259 */     return false;
/* 2174:     */   }
/* 2175:     */   
/* 2176:     */   Object executeRegExp(Context cx, Scriptable scope, RegExpImpl res, String str, int[] indexp, int matchType)
/* 2177:     */   {
/* 2178:2268 */     REGlobalData gData = new REGlobalData();
/* 2179:     */     
/* 2180:2270 */     int start = indexp[0];
/* 2181:2271 */     int end = str.length();
/* 2182:2272 */     if (start > end) {
/* 2183:2273 */       start = end;
/* 2184:     */     }
/* 2185:2277 */     boolean matches = matchRegExp(gData, this.re, str, start, end, res.multiline);
/* 2186:2279 */     if (!matches)
/* 2187:     */     {
/* 2188:2280 */       if (matchType != 2) {
/* 2189:2280 */         return null;
/* 2190:     */       }
/* 2191:2281 */       return Undefined.instance;
/* 2192:     */     }
/* 2193:2283 */     int index = gData.cp;
/* 2194:2284 */     int ep = indexp[0] = index;
/* 2195:2285 */     int matchlen = ep - (start + gData.skipped);
/* 2196:2286 */     index -= matchlen;
/* 2197:     */     Scriptable obj;
/* 2198:     */     Object result;
/* 2199:     */     Scriptable obj;
/* 2200:2290 */     if (matchType == 0)
/* 2201:     */     {
/* 2202:2295 */       Object result = Boolean.TRUE;
/* 2203:2296 */       obj = null;
/* 2204:     */     }
/* 2205:     */     else
/* 2206:     */     {
/* 2207:2305 */       result = cx.newArray(scope, 0);
/* 2208:2306 */       obj = (Scriptable)result;
/* 2209:     */       
/* 2210:2308 */       String matchstr = str.substring(index, index + matchlen);
/* 2211:2309 */       obj.put(0, obj, matchstr);
/* 2212:     */     }
/* 2213:2312 */     if (this.re.parenCount == 0)
/* 2214:     */     {
/* 2215:2313 */       res.parens = null;
/* 2216:2314 */       res.lastParen = SubString.emptySubString;
/* 2217:     */     }
/* 2218:     */     else
/* 2219:     */     {
/* 2220:2316 */       SubString parsub = null;
/* 2221:     */       
/* 2222:2318 */       res.parens = new SubString[this.re.parenCount];
/* 2223:2319 */       for (int num = 0; num < this.re.parenCount; num++)
/* 2224:     */       {
/* 2225:2320 */         int cap_index = gData.parens_index(num);
/* 2226:2322 */         if (cap_index != -1)
/* 2227:     */         {
/* 2228:2323 */           int cap_length = gData.parens_length(num);
/* 2229:2324 */           parsub = new SubString(str, cap_index, cap_length);
/* 2230:2325 */           res.parens[num] = parsub;
/* 2231:2326 */           if (matchType != 0)
/* 2232:     */           {
/* 2233:2327 */             String parstr = parsub.toString();
/* 2234:2328 */             obj.put(num + 1, obj, parstr);
/* 2235:     */           }
/* 2236:     */         }
/* 2237:2331 */         else if (matchType != 0)
/* 2238:     */         {
/* 2239:2332 */           obj.put(num + 1, obj, Undefined.instance);
/* 2240:     */         }
/* 2241:     */       }
/* 2242:2335 */       res.lastParen = parsub;
/* 2243:     */     }
/* 2244:2338 */     if (matchType != 0)
/* 2245:     */     {
/* 2246:2343 */       obj.put("index", obj, Integer.valueOf(start + gData.skipped));
/* 2247:2344 */       obj.put("input", obj, str);
/* 2248:     */     }
/* 2249:2347 */     if (res.lastMatch == null)
/* 2250:     */     {
/* 2251:2348 */       res.lastMatch = new SubString();
/* 2252:2349 */       res.leftContext = new SubString();
/* 2253:2350 */       res.rightContext = new SubString();
/* 2254:     */     }
/* 2255:2352 */     res.lastMatch.str = str;
/* 2256:2353 */     res.lastMatch.index = index;
/* 2257:2354 */     res.lastMatch.length = matchlen;
/* 2258:     */     
/* 2259:2356 */     res.leftContext.str = str;
/* 2260:2357 */     if (cx.getLanguageVersion() == 120)
/* 2261:     */     {
/* 2262:2371 */       res.leftContext.index = start;
/* 2263:2372 */       res.leftContext.length = gData.skipped;
/* 2264:     */     }
/* 2265:     */     else
/* 2266:     */     {
/* 2267:2379 */       res.leftContext.index = 0;
/* 2268:2380 */       res.leftContext.length = (start + gData.skipped);
/* 2269:     */     }
/* 2270:2383 */     res.rightContext.str = str;
/* 2271:2384 */     res.rightContext.index = ep;
/* 2272:2385 */     res.rightContext.length = (end - ep);
/* 2273:     */     
/* 2274:2387 */     return result;
/* 2275:     */   }
/* 2276:     */   
/* 2277:     */   int getFlags()
/* 2278:     */   {
/* 2279:2392 */     return this.re.flags;
/* 2280:     */   }
/* 2281:     */   
/* 2282:     */   private static void reportWarning(Context cx, String messageId, String arg)
/* 2283:     */   {
/* 2284:2397 */     if (cx.hasFeature(11))
/* 2285:     */     {
/* 2286:2398 */       String msg = ScriptRuntime.getMessage1(messageId, arg);
/* 2287:2399 */       Context.reportWarning(msg);
/* 2288:     */     }
/* 2289:     */   }
/* 2290:     */   
/* 2291:     */   private static void reportError(String messageId, String arg)
/* 2292:     */   {
/* 2293:2405 */     String msg = ScriptRuntime.getMessage1(messageId, arg);
/* 2294:2406 */     throw ScriptRuntime.constructError("SyntaxError", msg);
/* 2295:     */   }
/* 2296:     */   
/* 2297:     */   protected int getMaxInstanceId()
/* 2298:     */   {
/* 2299:2423 */     return 5;
/* 2300:     */   }
/* 2301:     */   
/* 2302:     */   protected int findInstanceIdInfo(String s)
/* 2303:     */   {
/* 2304:2431 */     int id = 0;String X = null;
/* 2305:2432 */     int s_length = s.length();
/* 2306:2433 */     if (s_length == 6)
/* 2307:     */     {
/* 2308:2434 */       int c = s.charAt(0);
/* 2309:2435 */       if (c == 103)
/* 2310:     */       {
/* 2311:2435 */         X = "global";id = 3;
/* 2312:     */       }
/* 2313:2436 */       else if (c == 115)
/* 2314:     */       {
/* 2315:2436 */         X = "source";id = 2;
/* 2316:     */       }
/* 2317:     */     }
/* 2318:2438 */     else if (s_length == 9)
/* 2319:     */     {
/* 2320:2439 */       int c = s.charAt(0);
/* 2321:2440 */       if (c == 108)
/* 2322:     */       {
/* 2323:2440 */         X = "lastIndex";id = 1;
/* 2324:     */       }
/* 2325:2441 */       else if (c == 109)
/* 2326:     */       {
/* 2327:2441 */         X = "multiline";id = 5;
/* 2328:     */       }
/* 2329:     */     }
/* 2330:2443 */     else if (s_length == 10)
/* 2331:     */     {
/* 2332:2443 */       X = "ignoreCase";id = 4;
/* 2333:     */     }
/* 2334:2444 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 2335:2444 */       id = 0;
/* 2336:     */     }
/* 2337:2450 */     if (id == 0) {
/* 2338:2450 */       return super.findInstanceIdInfo(s);
/* 2339:     */     }
/* 2340:     */     int attr;
/* 2341:2453 */     switch (id)
/* 2342:     */     {
/* 2343:     */     case 1: 
/* 2344:2455 */       attr = 6;
/* 2345:2456 */       break;
/* 2346:     */     case 2: 
/* 2347:     */     case 3: 
/* 2348:     */     case 4: 
/* 2349:     */     case 5: 
/* 2350:2461 */       attr = 7;
/* 2351:2462 */       break;
/* 2352:     */     default: 
/* 2353:2464 */       throw new IllegalStateException();
/* 2354:     */     }
/* 2355:2466 */     return instanceIdInfo(attr, id);
/* 2356:     */   }
/* 2357:     */   
/* 2358:     */   protected String getInstanceIdName(int id)
/* 2359:     */   {
/* 2360:2472 */     switch (id)
/* 2361:     */     {
/* 2362:     */     case 1: 
/* 2363:2473 */       return "lastIndex";
/* 2364:     */     case 2: 
/* 2365:2474 */       return "source";
/* 2366:     */     case 3: 
/* 2367:2475 */       return "global";
/* 2368:     */     case 4: 
/* 2369:2476 */       return "ignoreCase";
/* 2370:     */     case 5: 
/* 2371:2477 */       return "multiline";
/* 2372:     */     }
/* 2373:2479 */     return super.getInstanceIdName(id);
/* 2374:     */   }
/* 2375:     */   
/* 2376:     */   protected Object getInstanceIdValue(int id)
/* 2377:     */   {
/* 2378:2485 */     switch (id)
/* 2379:     */     {
/* 2380:     */     case 1: 
/* 2381:2487 */       return ScriptRuntime.wrapNumber(this.lastIndex);
/* 2382:     */     case 2: 
/* 2383:2489 */       return new String(this.re.source);
/* 2384:     */     case 3: 
/* 2385:2491 */       return ScriptRuntime.wrapBoolean((this.re.flags & 0x1) != 0);
/* 2386:     */     case 4: 
/* 2387:2493 */       return ScriptRuntime.wrapBoolean((this.re.flags & 0x2) != 0);
/* 2388:     */     case 5: 
/* 2389:2495 */       return ScriptRuntime.wrapBoolean((this.re.flags & 0x4) != 0);
/* 2390:     */     }
/* 2391:2497 */     return super.getInstanceIdValue(id);
/* 2392:     */   }
/* 2393:     */   
/* 2394:     */   protected void setInstanceIdValue(int id, Object value)
/* 2395:     */   {
/* 2396:2503 */     switch (id)
/* 2397:     */     {
/* 2398:     */     case 1: 
/* 2399:2505 */       this.lastIndex = ScriptRuntime.toNumber(value);
/* 2400:2506 */       return;
/* 2401:     */     case 2: 
/* 2402:     */     case 3: 
/* 2403:     */     case 4: 
/* 2404:     */     case 5: 
/* 2405:2511 */       return;
/* 2406:     */     }
/* 2407:2513 */     super.setInstanceIdValue(id, value);
/* 2408:     */   }
/* 2409:     */   
/* 2410:     */   protected void initPrototypeId(int id)
/* 2411:     */   {
/* 2412:     */     int arity;
/* 2413:     */     String s;
/* 2414:2521 */     switch (id)
/* 2415:     */     {
/* 2416:     */     case 1: 
/* 2417:2522 */       arity = 1;s = "compile"; break;
/* 2418:     */     case 2: 
/* 2419:2523 */       arity = 0;s = "toString"; break;
/* 2420:     */     case 3: 
/* 2421:2524 */       arity = 0;s = "toSource"; break;
/* 2422:     */     case 4: 
/* 2423:2525 */       arity = 1;s = "exec"; break;
/* 2424:     */     case 5: 
/* 2425:2526 */       arity = 1;s = "test"; break;
/* 2426:     */     case 6: 
/* 2427:2527 */       arity = 1;s = "prefix"; break;
/* 2428:     */     default: 
/* 2429:2528 */       throw new IllegalArgumentException(String.valueOf(id));
/* 2430:     */     }
/* 2431:2530 */     initPrototypeMethod(REGEXP_TAG, id, s, arity);
/* 2432:     */   }
/* 2433:     */   
/* 2434:     */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 2435:     */   {
/* 2436:2537 */     if (!f.hasTag(REGEXP_TAG)) {
/* 2437:2538 */       return super.execIdCall(f, cx, scope, thisObj, args);
/* 2438:     */     }
/* 2439:2540 */     int id = f.methodId();
/* 2440:2541 */     switch (id)
/* 2441:     */     {
/* 2442:     */     case 1: 
/* 2443:2543 */       return realThis(thisObj, f).compile(cx, scope, args);
/* 2444:     */     case 2: 
/* 2445:     */     case 3: 
/* 2446:2547 */       return realThis(thisObj, f).toString();
/* 2447:     */     case 4: 
/* 2448:2550 */       return realThis(thisObj, f).execSub(cx, scope, args, 1);
/* 2449:     */     case 5: 
/* 2450:2553 */       Object x = realThis(thisObj, f).execSub(cx, scope, args, 0);
/* 2451:2554 */       return Boolean.TRUE.equals(x) ? Boolean.TRUE : Boolean.FALSE;
/* 2452:     */     case 6: 
/* 2453:2558 */       return realThis(thisObj, f).execSub(cx, scope, args, 2);
/* 2454:     */     }
/* 2455:2560 */     throw new IllegalArgumentException(String.valueOf(id));
/* 2456:     */   }
/* 2457:     */   
/* 2458:     */   private static NativeRegExp realThis(Scriptable thisObj, IdFunctionObject f)
/* 2459:     */   {
/* 2460:2565 */     if (!(thisObj instanceof NativeRegExp)) {
/* 2461:2566 */       throw incompatibleCallError(f);
/* 2462:     */     }
/* 2463:2567 */     return (NativeRegExp)thisObj;
/* 2464:     */   }
/* 2465:     */   
/* 2466:     */   protected int findPrototypeId(String s)
/* 2467:     */   {
/* 2468:2576 */     int id = 0;String X = null;
/* 2469:     */     int c;
/* 2470:2577 */     switch (s.length())
/* 2471:     */     {
/* 2472:     */     case 4: 
/* 2473:2578 */       c = s.charAt(0);
/* 2474:2579 */       if (c == 101)
/* 2475:     */       {
/* 2476:2579 */         X = "exec";id = 4;
/* 2477:     */       }
/* 2478:2580 */       else if (c == 116)
/* 2479:     */       {
/* 2480:2580 */         X = "test";id = 5;
/* 2481:     */       }
/* 2482:     */       break;
/* 2483:     */     case 6: 
/* 2484:2582 */       X = "prefix";id = 6; break;
/* 2485:     */     case 7: 
/* 2486:2583 */       X = "compile";id = 1; break;
/* 2487:     */     case 8: 
/* 2488:2584 */       c = s.charAt(3);
/* 2489:2585 */       if (c == 111)
/* 2490:     */       {
/* 2491:2585 */         X = "toSource";id = 3;
/* 2492:     */       }
/* 2493:2586 */       else if (c == 116)
/* 2494:     */       {
/* 2495:2586 */         X = "toString";id = 2;
/* 2496:     */       }
/* 2497:     */       break;
/* 2498:     */     }
/* 2499:2589 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 2500:2589 */       id = 0;
/* 2501:     */     }
/* 2502:2593 */     return id;
/* 2503:     */   }
/* 2504:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.NativeRegExp
 * JD-Core Version:    0.7.0.1
 */