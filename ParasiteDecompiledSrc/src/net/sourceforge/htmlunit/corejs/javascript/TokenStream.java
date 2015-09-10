/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.Reader;
/*    5:     */ 
/*    6:     */ class TokenStream
/*    7:     */ {
/*    8:     */   private static final int EOF_CHAR = -1;
/*    9:     */   private static final char BYTE_ORDER_MARK = '﻿';
/*   10:     */   private boolean dirtyLine;
/*   11:     */   String regExpFlags;
/*   12:     */   
/*   13:     */   TokenStream(Parser parser, Reader sourceReader, String sourceString, int lineno)
/*   14:     */   {
/*   15:  77 */     this.parser = parser;
/*   16:  78 */     this.lineno = lineno;
/*   17:  79 */     if (sourceReader != null)
/*   18:     */     {
/*   19:  80 */       if (sourceString != null) {
/*   20:  80 */         Kit.codeBug();
/*   21:     */       }
/*   22:  81 */       this.sourceReader = sourceReader;
/*   23:  82 */       this.sourceBuffer = new char[512];
/*   24:  83 */       this.sourceEnd = 0;
/*   25:     */     }
/*   26:     */     else
/*   27:     */     {
/*   28:  85 */       if (sourceString == null) {
/*   29:  85 */         Kit.codeBug();
/*   30:     */       }
/*   31:  86 */       this.sourceString = sourceString;
/*   32:  87 */       this.sourceEnd = sourceString.length();
/*   33:     */     }
/*   34:  89 */     this.sourceCursor = (this.cursor = 0);
/*   35:     */   }
/*   36:     */   
/*   37:     */   String tokenToString(int token)
/*   38:     */   {
/*   39: 113 */     return "";
/*   40:     */   }
/*   41:     */   
/*   42:     */   static boolean isKeyword(String s)
/*   43:     */   {
/*   44: 118 */     return 0 != stringToKeyword(s);
/*   45:     */   }
/*   46:     */   
/*   47:     */   private static int stringToKeyword(String name)
/*   48:     */   {
/*   49: 126 */     int Id_break = 120;
/*   50: 127 */     int Id_case = 115;
/*   51: 128 */     int Id_continue = 121;
/*   52: 129 */     int Id_default = 116;
/*   53: 130 */     int Id_delete = 31;
/*   54: 131 */     int Id_do = 118;
/*   55: 132 */     int Id_else = 113;
/*   56: 133 */     int Id_export = 127;
/*   57: 134 */     int Id_false = 44;
/*   58: 135 */     int Id_for = 119;
/*   59: 136 */     int Id_function = 109;
/*   60: 137 */     int Id_if = 112;
/*   61: 138 */     int Id_in = 52;
/*   62: 139 */     int Id_let = 153;
/*   63: 140 */     int Id_new = 30;
/*   64: 141 */     int Id_null = 42;
/*   65: 142 */     int Id_return = 4;
/*   66: 143 */     int Id_switch = 114;
/*   67: 144 */     int Id_this = 43;
/*   68: 145 */     int Id_true = 45;
/*   69: 146 */     int Id_typeof = 32;
/*   70: 147 */     int Id_var = 122;
/*   71: 148 */     int Id_void = 126;
/*   72: 149 */     int Id_while = 117;
/*   73: 150 */     int Id_with = 123;
/*   74: 151 */     int Id_yield = 72;
/*   75:     */     
/*   76:     */ 
/*   77: 154 */     int Id_abstract = 127;
/*   78: 155 */     int Id_boolean = 127;
/*   79: 156 */     int Id_byte = 127;
/*   80: 157 */     int Id_catch = 124;
/*   81: 158 */     int Id_char = 127;
/*   82: 159 */     int Id_class = 127;
/*   83: 160 */     int Id_const = 154;
/*   84: 161 */     int Id_debugger = 160;
/*   85: 162 */     int Id_double = 127;
/*   86: 163 */     int Id_enum = 127;
/*   87: 164 */     int Id_extends = 127;
/*   88: 165 */     int Id_final = 127;
/*   89: 166 */     int Id_finally = 125;
/*   90: 167 */     int Id_float = 127;
/*   91: 168 */     int Id_goto = 127;
/*   92: 169 */     int Id_implements = 127;
/*   93: 170 */     int Id_import = 127;
/*   94: 171 */     int Id_instanceof = 53;
/*   95: 172 */     int Id_int = 127;
/*   96: 173 */     int Id_interface = 127;
/*   97: 174 */     int Id_long = 127;
/*   98: 175 */     int Id_native = 127;
/*   99: 176 */     int Id_package = 127;
/*  100: 177 */     int Id_private = 127;
/*  101: 178 */     int Id_protected = 127;
/*  102: 179 */     int Id_public = 127;
/*  103: 180 */     int Id_short = 127;
/*  104: 181 */     int Id_static = 127;
/*  105: 182 */     int Id_super = 127;
/*  106: 183 */     int Id_synchronized = 127;
/*  107: 184 */     int Id_throw = 50;
/*  108: 185 */     int Id_throws = 127;
/*  109: 186 */     int Id_transient = 127;
/*  110: 187 */     int Id_try = 81;
/*  111: 188 */     int Id_volatile = 127;
/*  112:     */     
/*  113:     */ 
/*  114: 191 */     String s = name;
/*  115:     */     
/*  116: 193 */     int id = 0;String X = null;
/*  117:     */     int c;
/*  118: 194 */     switch (s.length())
/*  119:     */     {
/*  120:     */     case 2: 
/*  121: 195 */       c = s.charAt(1);
/*  122: 196 */       if (c == 102)
/*  123:     */       {
/*  124: 196 */         if (s.charAt(0) == 'i')
/*  125:     */         {
/*  126: 196 */           id = 112;
/*  127:     */           break label2041;
/*  128:     */         }
/*  129:     */       }
/*  130: 197 */       else if (c == 110)
/*  131:     */       {
/*  132: 197 */         if (s.charAt(0) == 'i')
/*  133:     */         {
/*  134: 197 */           id = 52;
/*  135:     */           break label2041;
/*  136:     */         }
/*  137:     */       }
/*  138: 198 */       else if ((c == 111) && (s.charAt(0) == 'd')) {
/*  139: 198 */         id = 118;
/*  140:     */       }
/*  141:     */       break;
/*  142:     */     case 3: 
/*  143: 200 */       switch (s.charAt(0))
/*  144:     */       {
/*  145:     */       case 'f': 
/*  146: 201 */         if ((s.charAt(2) == 'r') && (s.charAt(1) == 'o')) {
/*  147: 201 */           id = 119;
/*  148:     */         }
/*  149:     */         break;
/*  150:     */       case 'i': 
/*  151: 202 */         if ((s.charAt(2) == 't') && (s.charAt(1) == 'n')) {
/*  152: 202 */           id = 127;
/*  153:     */         }
/*  154:     */         break;
/*  155:     */       case 'l': 
/*  156: 203 */         if ((s.charAt(2) == 't') && (s.charAt(1) == 'e')) {
/*  157: 203 */           id = 153;
/*  158:     */         }
/*  159:     */         break;
/*  160:     */       case 'n': 
/*  161: 204 */         if ((s.charAt(2) == 'w') && (s.charAt(1) == 'e')) {
/*  162: 204 */           id = 30;
/*  163:     */         }
/*  164:     */         break;
/*  165:     */       case 't': 
/*  166: 205 */         if ((s.charAt(2) == 'y') && (s.charAt(1) == 'r')) {
/*  167: 205 */           id = 81;
/*  168:     */         }
/*  169:     */         break;
/*  170:     */       case 'v': 
/*  171: 206 */         if ((s.charAt(2) == 'r') && (s.charAt(1) == 'a'))
/*  172:     */         {
/*  173: 206 */           id = 122;
/*  174:     */           break label2041;
/*  175:     */         }
/*  176:     */         break;
/*  177:     */       }
/*  178: 207 */       break;
/*  179:     */     case 4: 
/*  180: 208 */       switch (s.charAt(0))
/*  181:     */       {
/*  182:     */       case 'b': 
/*  183: 209 */         X = "byte";id = 127; break;
/*  184:     */       case 'c': 
/*  185: 210 */         c = s.charAt(3);
/*  186: 211 */         if (c == 101)
/*  187:     */         {
/*  188: 211 */           if ((s.charAt(2) == 's') && (s.charAt(1) == 'a'))
/*  189:     */           {
/*  190: 211 */             id = 115;
/*  191:     */             break label2041;
/*  192:     */           }
/*  193:     */         }
/*  194: 212 */         else if ((c == 114) && (s.charAt(2) == 'a') && (s.charAt(1) == 'h')) {
/*  195: 212 */           id = 127;
/*  196:     */         }
/*  197:     */         break;
/*  198:     */       case 'e': 
/*  199: 214 */         c = s.charAt(3);
/*  200: 215 */         if (c == 101)
/*  201:     */         {
/*  202: 215 */           if ((s.charAt(2) == 's') && (s.charAt(1) == 'l'))
/*  203:     */           {
/*  204: 215 */             id = 113;
/*  205:     */             break label2041;
/*  206:     */           }
/*  207:     */         }
/*  208: 216 */         else if ((c == 109) && (s.charAt(2) == 'u') && (s.charAt(1) == 'n')) {
/*  209: 216 */           id = 127;
/*  210:     */         }
/*  211:     */         break;
/*  212:     */       case 'g': 
/*  213: 218 */         X = "goto";id = 127; break;
/*  214:     */       case 'l': 
/*  215: 219 */         X = "long";id = 127; break;
/*  216:     */       case 'n': 
/*  217: 220 */         X = "null";id = 42; break;
/*  218:     */       case 't': 
/*  219: 221 */         c = s.charAt(3);
/*  220: 222 */         if (c == 101)
/*  221:     */         {
/*  222: 222 */           if ((s.charAt(2) == 'u') && (s.charAt(1) == 'r'))
/*  223:     */           {
/*  224: 222 */             id = 45;
/*  225:     */             break label2041;
/*  226:     */           }
/*  227:     */         }
/*  228: 223 */         else if ((c == 115) && (s.charAt(2) == 'i') && (s.charAt(1) == 'h')) {
/*  229: 223 */           id = 43;
/*  230:     */         }
/*  231:     */         break;
/*  232:     */       case 'v': 
/*  233: 225 */         X = "void";id = 126; break;
/*  234:     */       case 'w': 
/*  235: 226 */         X = "with";id = 123;
/*  236:     */       }
/*  237: 227 */       break;
/*  238:     */     case 5: 
/*  239: 228 */       switch (s.charAt(2))
/*  240:     */       {
/*  241:     */       case 'a': 
/*  242: 229 */         X = "class";id = 127; break;
/*  243:     */       case 'e': 
/*  244: 230 */         c = s.charAt(0);
/*  245: 231 */         if (c == 98)
/*  246:     */         {
/*  247: 231 */           X = "break";id = 120;
/*  248:     */         }
/*  249: 232 */         else if (c == 121)
/*  250:     */         {
/*  251: 232 */           X = "yield";id = 72;
/*  252:     */         }
/*  253:     */         break;
/*  254:     */       case 'i': 
/*  255: 234 */         X = "while";id = 117; break;
/*  256:     */       case 'l': 
/*  257: 235 */         X = "false";id = 44; break;
/*  258:     */       case 'n': 
/*  259: 236 */         c = s.charAt(0);
/*  260: 237 */         if (c == 99)
/*  261:     */         {
/*  262: 237 */           X = "const";id = 154;
/*  263:     */         }
/*  264: 238 */         else if (c == 102)
/*  265:     */         {
/*  266: 238 */           X = "final";id = 127;
/*  267:     */         }
/*  268:     */         break;
/*  269:     */       case 'o': 
/*  270: 240 */         c = s.charAt(0);
/*  271: 241 */         if (c == 102)
/*  272:     */         {
/*  273: 241 */           X = "float";id = 127;
/*  274:     */         }
/*  275: 242 */         else if (c == 115)
/*  276:     */         {
/*  277: 242 */           X = "short";id = 127;
/*  278:     */         }
/*  279:     */         break;
/*  280:     */       case 'p': 
/*  281: 244 */         X = "super";id = 127; break;
/*  282:     */       case 'r': 
/*  283: 245 */         X = "throw";id = 50; break;
/*  284:     */       case 't': 
/*  285: 246 */         X = "catch";id = 124;
/*  286:     */       }
/*  287: 247 */       break;
/*  288:     */     case 6: 
/*  289: 248 */       switch (s.charAt(1))
/*  290:     */       {
/*  291:     */       case 'a': 
/*  292: 249 */         X = "native";id = 127; break;
/*  293:     */       case 'e': 
/*  294: 250 */         c = s.charAt(0);
/*  295: 251 */         if (c == 100)
/*  296:     */         {
/*  297: 251 */           X = "delete";id = 31;
/*  298:     */         }
/*  299: 252 */         else if (c == 114)
/*  300:     */         {
/*  301: 252 */           X = "return";id = 4;
/*  302:     */         }
/*  303:     */         break;
/*  304:     */       case 'h': 
/*  305: 254 */         X = "throws";id = 127; break;
/*  306:     */       case 'm': 
/*  307: 255 */         X = "import";id = 127; break;
/*  308:     */       case 'o': 
/*  309: 256 */         X = "double";id = 127; break;
/*  310:     */       case 't': 
/*  311: 257 */         X = "static";id = 127; break;
/*  312:     */       case 'u': 
/*  313: 258 */         X = "public";id = 127; break;
/*  314:     */       case 'w': 
/*  315: 259 */         X = "switch";id = 114; break;
/*  316:     */       case 'x': 
/*  317: 260 */         X = "export";id = 127; break;
/*  318:     */       case 'y': 
/*  319: 261 */         X = "typeof";id = 32;
/*  320:     */       }
/*  321: 262 */       break;
/*  322:     */     case 7: 
/*  323: 263 */       switch (s.charAt(1))
/*  324:     */       {
/*  325:     */       case 'a': 
/*  326: 264 */         X = "package";id = 127; break;
/*  327:     */       case 'e': 
/*  328: 265 */         X = "default";id = 116; break;
/*  329:     */       case 'i': 
/*  330: 266 */         X = "finally";id = 125; break;
/*  331:     */       case 'o': 
/*  332: 267 */         X = "boolean";id = 127; break;
/*  333:     */       case 'r': 
/*  334: 268 */         X = "private";id = 127; break;
/*  335:     */       case 'x': 
/*  336: 269 */         X = "extends";id = 127;
/*  337:     */       }
/*  338: 270 */       break;
/*  339:     */     case 8: 
/*  340: 271 */       switch (s.charAt(0))
/*  341:     */       {
/*  342:     */       case 'a': 
/*  343: 272 */         X = "abstract";id = 127; break;
/*  344:     */       case 'c': 
/*  345: 273 */         X = "continue";id = 121; break;
/*  346:     */       case 'd': 
/*  347: 274 */         X = "debugger";id = 160; break;
/*  348:     */       case 'f': 
/*  349: 275 */         X = "function";id = 109; break;
/*  350:     */       case 'v': 
/*  351: 276 */         X = "volatile";id = 127;
/*  352:     */       }
/*  353: 277 */       break;
/*  354:     */     case 9: 
/*  355: 278 */       c = s.charAt(0);
/*  356: 279 */       if (c == 105)
/*  357:     */       {
/*  358: 279 */         X = "interface";id = 127;
/*  359:     */       }
/*  360: 280 */       else if (c == 112)
/*  361:     */       {
/*  362: 280 */         X = "protected";id = 127;
/*  363:     */       }
/*  364: 281 */       else if (c == 116)
/*  365:     */       {
/*  366: 281 */         X = "transient";id = 127;
/*  367:     */       }
/*  368:     */       break;
/*  369:     */     case 10: 
/*  370: 283 */       c = s.charAt(1);
/*  371: 284 */       if (c == 109)
/*  372:     */       {
/*  373: 284 */         X = "implements";id = 127;
/*  374:     */       }
/*  375: 285 */       else if (c == 110)
/*  376:     */       {
/*  377: 285 */         X = "instanceof";id = 53;
/*  378:     */       }
/*  379:     */       break;
/*  380:     */     case 12: 
/*  381: 287 */       X = "synchronized";id = 127; break;
/*  382:     */     }
/*  383: 289 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/*  384: 289 */       id = 0;
/*  385:     */     }
/*  386:     */     label2041:
/*  387: 293 */     if (id == 0) {
/*  388: 293 */       return 0;
/*  389:     */     }
/*  390: 294 */     return id & 0xFF;
/*  391:     */   }
/*  392:     */   
/*  393:     */   final String getSourceString()
/*  394:     */   {
/*  395: 297 */     return this.sourceString;
/*  396:     */   }
/*  397:     */   
/*  398:     */   final int getLineno()
/*  399:     */   {
/*  400: 299 */     return this.lineno;
/*  401:     */   }
/*  402:     */   
/*  403:     */   final String getString()
/*  404:     */   {
/*  405: 301 */     return this.string;
/*  406:     */   }
/*  407:     */   
/*  408:     */   final char getQuoteChar()
/*  409:     */   {
/*  410: 304 */     return (char)this.quoteChar;
/*  411:     */   }
/*  412:     */   
/*  413:     */   final double getNumber()
/*  414:     */   {
/*  415: 307 */     return this.number;
/*  416:     */   }
/*  417:     */   
/*  418:     */   final boolean isNumberOctal()
/*  419:     */   {
/*  420: 308 */     return this.isOctal;
/*  421:     */   }
/*  422:     */   
/*  423:     */   final boolean eof()
/*  424:     */   {
/*  425: 310 */     return this.hitEOF;
/*  426:     */   }
/*  427:     */   
/*  428:     */   final int getToken()
/*  429:     */     throws IOException
/*  430:     */   {
/*  431:     */     int c;
/*  432:     */     do
/*  433:     */     {
/*  434: 320 */       c = getChar();
/*  435: 321 */       if (c == -1)
/*  436:     */       {
/*  437: 322 */         this.tokenBeg = (this.cursor - 1);
/*  438: 323 */         this.tokenEnd = this.cursor;
/*  439: 324 */         return 0;
/*  440:     */       }
/*  441: 325 */       if (c == 10)
/*  442:     */       {
/*  443: 326 */         this.dirtyLine = false;
/*  444: 327 */         this.tokenBeg = (this.cursor - 1);
/*  445: 328 */         this.tokenEnd = this.cursor;
/*  446: 329 */         return 1;
/*  447:     */       }
/*  448: 330 */     } while (isJSSpace(c));
/*  449: 331 */     if (c != 45) {
/*  450: 332 */       this.dirtyLine = true;
/*  451:     */     }
/*  452: 339 */     this.tokenBeg = (this.cursor - 1);
/*  453: 340 */     this.tokenEnd = this.cursor;
/*  454: 342 */     if (c == 64) {
/*  455: 342 */       return 147;
/*  456:     */     }
/*  457: 347 */     boolean isUnicodeEscapeStart = false;
/*  458:     */     boolean identifierStart;
/*  459: 348 */     if (c == 92)
/*  460:     */     {
/*  461: 349 */       c = getChar();
/*  462: 350 */       if (c == 117)
/*  463:     */       {
/*  464: 351 */         boolean identifierStart = true;
/*  465: 352 */         isUnicodeEscapeStart = true;
/*  466: 353 */         this.stringBufferTop = 0;
/*  467:     */       }
/*  468:     */       else
/*  469:     */       {
/*  470: 355 */         boolean identifierStart = false;
/*  471: 356 */         ungetChar(c);
/*  472: 357 */         c = 92;
/*  473:     */       }
/*  474:     */     }
/*  475:     */     else
/*  476:     */     {
/*  477: 360 */       identifierStart = Character.isJavaIdentifierStart((char)c);
/*  478: 361 */       if (identifierStart)
/*  479:     */       {
/*  480: 362 */         this.stringBufferTop = 0;
/*  481: 363 */         addToString(c);
/*  482:     */       }
/*  483:     */     }
/*  484: 367 */     if (identifierStart)
/*  485:     */     {
/*  486: 368 */       boolean containsEscape = isUnicodeEscapeStart;
/*  487:     */       for (;;)
/*  488:     */       {
/*  489: 370 */         if (isUnicodeEscapeStart)
/*  490:     */         {
/*  491: 377 */           int escapeVal = 0;
/*  492: 378 */           for (int i = 0; i != 4; i++)
/*  493:     */           {
/*  494: 379 */             c = getChar();
/*  495: 380 */             escapeVal = Kit.xDigitToInt(c, escapeVal);
/*  496: 382 */             if (escapeVal < 0) {
/*  497:     */               break;
/*  498:     */             }
/*  499:     */           }
/*  500: 384 */           if (escapeVal < 0)
/*  501:     */           {
/*  502: 385 */             this.parser.addError("msg.invalid.escape");
/*  503: 386 */             return -1;
/*  504:     */           }
/*  505: 388 */           addToString(escapeVal);
/*  506: 389 */           isUnicodeEscapeStart = false;
/*  507:     */         }
/*  508:     */         else
/*  509:     */         {
/*  510: 391 */           c = getChar();
/*  511: 392 */           if (c == 92)
/*  512:     */           {
/*  513: 393 */             c = getChar();
/*  514: 394 */             if (c == 117)
/*  515:     */             {
/*  516: 395 */               isUnicodeEscapeStart = true;
/*  517: 396 */               containsEscape = true;
/*  518:     */             }
/*  519:     */             else
/*  520:     */             {
/*  521: 398 */               this.parser.addError("msg.illegal.character");
/*  522: 399 */               return -1;
/*  523:     */             }
/*  524:     */           }
/*  525:     */           else
/*  526:     */           {
/*  527: 402 */             if ((c == -1) || (c == 65279) || (!Character.isJavaIdentifierPart((char)c))) {
/*  528:     */               break;
/*  529:     */             }
/*  530: 407 */             addToString(c);
/*  531:     */           }
/*  532:     */         }
/*  533:     */       }
/*  534: 411 */       ungetChar(c);
/*  535:     */       
/*  536: 413 */       String str = getStringFromBuffer();
/*  537: 414 */       if (!containsEscape)
/*  538:     */       {
/*  539: 419 */         int result = stringToKeyword(str);
/*  540: 420 */         if (result != 0)
/*  541:     */         {
/*  542: 421 */           if (((result == 153) || (result == 72)) && (this.parser.compilerEnv.getLanguageVersion() < 170))
/*  543:     */           {
/*  544: 426 */             this.string = (result == 153 ? "let" : "yield");
/*  545: 427 */             result = 39;
/*  546:     */           }
/*  547: 429 */           if (result != 127) {
/*  548: 430 */             return result;
/*  549:     */           }
/*  550: 431 */           if (!this.parser.compilerEnv.isReservedKeywordAsIdentifier()) {
/*  551: 434 */             return result;
/*  552:     */           }
/*  553:     */         }
/*  554:     */       }
/*  555: 438 */       this.string = ((String)this.allStrings.intern(str));
/*  556: 439 */       return 39;
/*  557:     */     }
/*  558: 443 */     if ((isDigit(c)) || ((c == 46) && (isDigit(peekChar()))))
/*  559:     */     {
/*  560: 444 */       this.isOctal = false;
/*  561: 445 */       this.stringBufferTop = 0;
/*  562: 446 */       int base = 10;
/*  563: 448 */       if (c == 48)
/*  564:     */       {
/*  565: 449 */         c = getChar();
/*  566: 450 */         if ((c == 120) || (c == 88))
/*  567:     */         {
/*  568: 451 */           base = 16;
/*  569: 452 */           c = getChar();
/*  570:     */         }
/*  571: 453 */         else if (isDigit(c))
/*  572:     */         {
/*  573: 454 */           base = 8;
/*  574: 455 */           this.isOctal = true;
/*  575:     */         }
/*  576:     */         else
/*  577:     */         {
/*  578: 457 */           addToString(48);
/*  579:     */         }
/*  580:     */       }
/*  581: 461 */       if (base == 16) {
/*  582: 462 */         while (0 <= Kit.xDigitToInt(c, 0))
/*  583:     */         {
/*  584: 463 */           addToString(c);
/*  585: 464 */           c = getChar();
/*  586:     */         }
/*  587:     */       }
/*  588: 467 */       while ((48 <= c) && (c <= 57))
/*  589:     */       {
/*  590: 474 */         if ((base == 8) && (c >= 56))
/*  591:     */         {
/*  592: 475 */           this.parser.addWarning("msg.bad.octal.literal", c == 56 ? "8" : "9");
/*  593:     */           
/*  594: 477 */           base = 10;
/*  595:     */         }
/*  596: 479 */         addToString(c);
/*  597: 480 */         c = getChar();
/*  598:     */       }
/*  599: 484 */       boolean isInteger = true;
/*  600: 486 */       if ((base == 10) && ((c == 46) || (c == 101) || (c == 69)))
/*  601:     */       {
/*  602: 487 */         isInteger = false;
/*  603: 488 */         if (c == 46) {
/*  604:     */           do
/*  605:     */           {
/*  606: 490 */             addToString(c);
/*  607: 491 */             c = getChar();
/*  608: 492 */           } while (isDigit(c));
/*  609:     */         }
/*  610: 494 */         if ((c == 101) || (c == 69))
/*  611:     */         {
/*  612: 495 */           addToString(c);
/*  613: 496 */           c = getChar();
/*  614: 497 */           if ((c == 43) || (c == 45))
/*  615:     */           {
/*  616: 498 */             addToString(c);
/*  617: 499 */             c = getChar();
/*  618:     */           }
/*  619: 501 */           if (!isDigit(c))
/*  620:     */           {
/*  621: 502 */             this.parser.addError("msg.missing.exponent");
/*  622: 503 */             return -1;
/*  623:     */           }
/*  624:     */           do
/*  625:     */           {
/*  626: 506 */             addToString(c);
/*  627: 507 */             c = getChar();
/*  628: 508 */           } while (isDigit(c));
/*  629:     */         }
/*  630:     */       }
/*  631: 511 */       ungetChar(c);
/*  632: 512 */       String numString = getStringFromBuffer();
/*  633: 513 */       this.string = numString;
/*  634:     */       double dval;
/*  635: 516 */       if ((base == 10) && (!isInteger)) {
/*  636:     */         try
/*  637:     */         {
/*  638: 519 */           dval = Double.valueOf(numString).doubleValue();
/*  639:     */         }
/*  640:     */         catch (NumberFormatException ex)
/*  641:     */         {
/*  642: 522 */           this.parser.addError("msg.caught.nfe");
/*  643: 523 */           return -1;
/*  644:     */         }
/*  645:     */       } else {
/*  646: 526 */         dval = ScriptRuntime.stringToNumber(numString, 0, base);
/*  647:     */       }
/*  648: 529 */       this.number = dval;
/*  649: 530 */       return 40;
/*  650:     */     }
/*  651: 534 */     if ((c == 34) || (c == 39))
/*  652:     */     {
/*  653: 540 */       this.quoteChar = c;
/*  654: 541 */       this.stringBufferTop = 0;
/*  655:     */       
/*  656: 543 */       c = getChar();
/*  657: 544 */       while (c != this.quoteChar)
/*  658:     */       {
/*  659: 545 */         if ((c == 10) || (c == -1))
/*  660:     */         {
/*  661: 546 */           ungetChar(c);
/*  662: 547 */           this.tokenEnd = this.cursor;
/*  663: 548 */           this.parser.addError("msg.unterminated.string.lit");
/*  664: 549 */           return -1;
/*  665:     */         }
/*  666: 552 */         if (c == 92)
/*  667:     */         {
/*  668: 556 */           c = getChar();
/*  669:     */           int escapeVal;
/*  670: 557 */           switch (c)
/*  671:     */           {
/*  672:     */           case 98: 
/*  673: 558 */             c = 8; break;
/*  674:     */           case 102: 
/*  675: 559 */             c = 12; break;
/*  676:     */           case 110: 
/*  677: 560 */             c = 10; break;
/*  678:     */           case 114: 
/*  679: 561 */             c = 13; break;
/*  680:     */           case 116: 
/*  681: 562 */             c = 9; break;
/*  682:     */           case 118: 
/*  683: 566 */             c = 11; break;
/*  684:     */           case 117: 
/*  685: 572 */             int escapeStart = this.stringBufferTop;
/*  686: 573 */             addToString(117);
/*  687: 574 */             escapeVal = 0;
/*  688: 575 */             for (int i = 0;; i++)
/*  689:     */             {
/*  690: 575 */               if (i == 4) {
/*  691:     */                 break label1104;
/*  692:     */               }
/*  693: 576 */               c = getChar();
/*  694: 577 */               escapeVal = Kit.xDigitToInt(c, escapeVal);
/*  695: 578 */               if (escapeVal < 0) {
/*  696:     */                 break;
/*  697:     */               }
/*  698: 581 */               addToString(c);
/*  699:     */             }
/*  700: 585 */             this.stringBufferTop = escapeStart;
/*  701: 586 */             c = escapeVal;
/*  702: 587 */             break;
/*  703:     */           case 120: 
/*  704: 591 */             c = getChar();
/*  705: 592 */             escapeVal = Kit.xDigitToInt(c, 0);
/*  706: 593 */             if (escapeVal < 0)
/*  707:     */             {
/*  708: 594 */               addToString(120);
/*  709: 595 */               continue;
/*  710:     */             }
/*  711: 597 */             int c1 = c;
/*  712: 598 */             c = getChar();
/*  713: 599 */             escapeVal = Kit.xDigitToInt(c, escapeVal);
/*  714: 600 */             if (escapeVal < 0)
/*  715:     */             {
/*  716: 601 */               addToString(120);
/*  717: 602 */               addToString(c1);
/*  718: 603 */               continue;
/*  719:     */             }
/*  720: 606 */             c = escapeVal;
/*  721:     */             
/*  722:     */ 
/*  723: 609 */             break;
/*  724:     */           case 10: 
/*  725: 614 */             c = getChar();
/*  726: 615 */             break;
/*  727:     */           default: 
/*  728:     */             label1104:
/*  729: 618 */             if ((48 <= c) && (c < 56))
/*  730:     */             {
/*  731: 619 */               int val = c - 48;
/*  732: 620 */               c = getChar();
/*  733: 621 */               if ((48 <= c) && (c < 56))
/*  734:     */               {
/*  735: 622 */                 val = 8 * val + c - 48;
/*  736: 623 */                 c = getChar();
/*  737: 624 */                 if ((48 <= c) && (c < 56) && (val <= 31))
/*  738:     */                 {
/*  739: 627 */                   val = 8 * val + c - 48;
/*  740: 628 */                   c = getChar();
/*  741:     */                 }
/*  742:     */               }
/*  743: 631 */               ungetChar(c);
/*  744: 632 */               c = val;
/*  745:     */             }
/*  746:     */             break;
/*  747:     */           }
/*  748:     */         }
/*  749:     */         else
/*  750:     */         {
/*  751: 636 */           addToString(c);
/*  752: 637 */           c = getChar();
/*  753:     */         }
/*  754:     */       }
/*  755: 640 */       String str = getStringFromBuffer();
/*  756: 641 */       this.string = ((String)this.allStrings.intern(str));
/*  757: 642 */       return 41;
/*  758:     */     }
/*  759: 645 */     switch (c)
/*  760:     */     {
/*  761:     */     case 59: 
/*  762: 646 */       return 82;
/*  763:     */     case 91: 
/*  764: 647 */       return 83;
/*  765:     */     case 93: 
/*  766: 648 */       return 84;
/*  767:     */     case 123: 
/*  768: 649 */       return 85;
/*  769:     */     case 125: 
/*  770: 650 */       return 86;
/*  771:     */     case 40: 
/*  772: 651 */       return 87;
/*  773:     */     case 41: 
/*  774: 652 */       return 88;
/*  775:     */     case 44: 
/*  776: 653 */       return 89;
/*  777:     */     case 63: 
/*  778: 654 */       return 102;
/*  779:     */     case 58: 
/*  780: 656 */       if (matchChar(58)) {
/*  781: 657 */         return 144;
/*  782:     */       }
/*  783: 659 */       return 103;
/*  784:     */     case 46: 
/*  785: 662 */       if (matchChar(46)) {
/*  786: 663 */         return 143;
/*  787:     */       }
/*  788: 664 */       if (matchChar(40)) {
/*  789: 665 */         return 146;
/*  790:     */       }
/*  791: 667 */       return 108;
/*  792:     */     case 124: 
/*  793: 671 */       if (matchChar(124)) {
/*  794: 672 */         return 104;
/*  795:     */       }
/*  796: 673 */       if (matchChar(61)) {
/*  797: 674 */         return 91;
/*  798:     */       }
/*  799: 676 */       return 9;
/*  800:     */     case 94: 
/*  801: 680 */       if (matchChar(61)) {
/*  802: 681 */         return 92;
/*  803:     */       }
/*  804: 683 */       return 10;
/*  805:     */     case 38: 
/*  806: 687 */       if (matchChar(38)) {
/*  807: 688 */         return 105;
/*  808:     */       }
/*  809: 689 */       if (matchChar(61)) {
/*  810: 690 */         return 93;
/*  811:     */       }
/*  812: 692 */       return 11;
/*  813:     */     case 61: 
/*  814: 696 */       if (matchChar(61))
/*  815:     */       {
/*  816: 697 */         if (matchChar(61)) {
/*  817: 698 */           return 46;
/*  818:     */         }
/*  819: 700 */         return 12;
/*  820:     */       }
/*  821: 703 */       return 90;
/*  822:     */     case 33: 
/*  823: 707 */       if (matchChar(61))
/*  824:     */       {
/*  825: 708 */         if (matchChar(61)) {
/*  826: 709 */           return 47;
/*  827:     */         }
/*  828: 711 */         return 13;
/*  829:     */       }
/*  830: 714 */       return 26;
/*  831:     */     case 60: 
/*  832: 719 */       if (matchChar(33))
/*  833:     */       {
/*  834: 720 */         if (matchChar(45))
/*  835:     */         {
/*  836: 721 */           if (matchChar(45))
/*  837:     */           {
/*  838: 722 */             this.tokenBeg = (this.cursor - 4);
/*  839: 723 */             skipLine();
/*  840: 724 */             this.commentType = Token.CommentType.HTML;
/*  841: 725 */             return 161;
/*  842:     */           }
/*  843: 727 */           ungetCharIgnoreLineEnd(45);
/*  844:     */         }
/*  845: 729 */         ungetCharIgnoreLineEnd(33);
/*  846:     */       }
/*  847: 731 */       if (matchChar(60))
/*  848:     */       {
/*  849: 732 */         if (matchChar(61)) {
/*  850: 733 */           return 94;
/*  851:     */         }
/*  852: 735 */         return 18;
/*  853:     */       }
/*  854: 738 */       if (matchChar(61)) {
/*  855: 739 */         return 15;
/*  856:     */       }
/*  857: 741 */       return 14;
/*  858:     */     case 62: 
/*  859: 746 */       if (matchChar(62))
/*  860:     */       {
/*  861: 747 */         if (matchChar(62))
/*  862:     */         {
/*  863: 748 */           if (matchChar(61)) {
/*  864: 749 */             return 96;
/*  865:     */           }
/*  866: 751 */           return 20;
/*  867:     */         }
/*  868: 754 */         if (matchChar(61)) {
/*  869: 755 */           return 95;
/*  870:     */         }
/*  871: 757 */         return 19;
/*  872:     */       }
/*  873: 761 */       if (matchChar(61)) {
/*  874: 762 */         return 17;
/*  875:     */       }
/*  876: 764 */       return 16;
/*  877:     */     case 42: 
/*  878: 769 */       if (matchChar(61)) {
/*  879: 770 */         return 99;
/*  880:     */       }
/*  881: 772 */       return 23;
/*  882:     */     case 47: 
/*  883: 776 */       markCommentStart();
/*  884: 778 */       if (matchChar(47))
/*  885:     */       {
/*  886: 779 */         this.tokenBeg = (this.cursor - 2);
/*  887: 780 */         skipLine();
/*  888: 781 */         this.commentType = Token.CommentType.LINE;
/*  889: 782 */         return 161;
/*  890:     */       }
/*  891: 785 */       if (matchChar(42))
/*  892:     */       {
/*  893: 786 */         boolean lookForSlash = false;
/*  894: 787 */         this.tokenBeg = (this.cursor - 2);
/*  895: 788 */         if (matchChar(42))
/*  896:     */         {
/*  897: 789 */           lookForSlash = true;
/*  898: 790 */           this.commentType = Token.CommentType.JSDOC;
/*  899:     */         }
/*  900:     */         else
/*  901:     */         {
/*  902: 792 */           this.commentType = Token.CommentType.BLOCK_COMMENT;
/*  903:     */         }
/*  904:     */         for (;;)
/*  905:     */         {
/*  906: 795 */           c = getChar();
/*  907: 796 */           if (c == -1)
/*  908:     */           {
/*  909: 797 */             this.tokenEnd = (this.cursor - 1);
/*  910: 798 */             this.parser.addError("msg.unterminated.comment");
/*  911: 799 */             return 161;
/*  912:     */           }
/*  913: 800 */           if (c == 42)
/*  914:     */           {
/*  915: 801 */             lookForSlash = true;
/*  916:     */           }
/*  917: 802 */           else if (c == 47)
/*  918:     */           {
/*  919: 803 */             if (lookForSlash)
/*  920:     */             {
/*  921: 804 */               this.tokenEnd = this.cursor;
/*  922: 805 */               return 161;
/*  923:     */             }
/*  924:     */           }
/*  925:     */           else
/*  926:     */           {
/*  927: 808 */             lookForSlash = false;
/*  928: 809 */             this.tokenEnd = this.cursor;
/*  929:     */           }
/*  930:     */         }
/*  931:     */       }
/*  932: 814 */       if (matchChar(61)) {
/*  933: 815 */         return 100;
/*  934:     */       }
/*  935: 817 */       return 24;
/*  936:     */     case 37: 
/*  937: 821 */       if (matchChar(61)) {
/*  938: 822 */         return 101;
/*  939:     */       }
/*  940: 824 */       return 25;
/*  941:     */     case 126: 
/*  942: 828 */       return 27;
/*  943:     */     case 43: 
/*  944: 831 */       if (matchChar(61)) {
/*  945: 832 */         return 97;
/*  946:     */       }
/*  947: 833 */       if (matchChar(43)) {
/*  948: 834 */         return 106;
/*  949:     */       }
/*  950: 836 */       return 21;
/*  951:     */     case 45: 
/*  952: 840 */       if (matchChar(61))
/*  953:     */       {
/*  954: 841 */         c = 98;
/*  955:     */       }
/*  956: 842 */       else if (matchChar(45))
/*  957:     */       {
/*  958: 843 */         if (!this.dirtyLine) {
/*  959: 846 */           if (matchChar(62))
/*  960:     */           {
/*  961: 847 */             markCommentStart("--");
/*  962: 848 */             skipLine();
/*  963: 849 */             this.commentType = Token.CommentType.HTML;
/*  964: 850 */             return 161;
/*  965:     */           }
/*  966:     */         }
/*  967: 853 */         c = 107;
/*  968:     */       }
/*  969:     */       else
/*  970:     */       {
/*  971: 855 */         c = 22;
/*  972:     */       }
/*  973: 857 */       this.dirtyLine = true;
/*  974: 858 */       return c;
/*  975:     */     }
/*  976: 861 */     this.parser.addError("msg.illegal.character");
/*  977: 862 */     return -1;
/*  978:     */   }
/*  979:     */   
/*  980:     */   private static boolean isAlpha(int c)
/*  981:     */   {
/*  982: 870 */     if (c <= 90) {
/*  983: 871 */       return 65 <= c;
/*  984:     */     }
/*  985: 873 */     return (97 <= c) && (c <= 122);
/*  986:     */   }
/*  987:     */   
/*  988:     */   static boolean isDigit(int c)
/*  989:     */   {
/*  990: 879 */     return (48 <= c) && (c <= 57);
/*  991:     */   }
/*  992:     */   
/*  993:     */   static boolean isJSSpace(int c)
/*  994:     */   {
/*  995: 888 */     if (c <= 127) {
/*  996: 889 */       return (c == 32) || (c == 9) || (c == 12) || (c == 11);
/*  997:     */     }
/*  998: 891 */     return (c == 160) || (c == 65279) || (Character.getType((char)c) == 12);
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   private static boolean isJSFormatChar(int c)
/* 1002:     */   {
/* 1003: 898 */     return (c > 127) && (Character.getType((char)c) == 16);
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   void readRegExp(int startToken)
/* 1007:     */     throws IOException
/* 1008:     */   {
/* 1009: 907 */     int start = this.tokenBeg;
/* 1010: 908 */     this.stringBufferTop = 0;
/* 1011: 909 */     if (startToken == 100) {
/* 1012: 911 */       addToString(61);
/* 1013: 913 */     } else if (startToken != 24) {
/* 1014: 913 */       Kit.codeBug();
/* 1015:     */     }
/* 1016: 916 */     boolean inCharSet = false;
/* 1017:     */     int c;
/* 1018: 918 */     while (((c = getChar()) != 47) || (inCharSet))
/* 1019:     */     {
/* 1020: 919 */       if ((c == 10) || (c == -1))
/* 1021:     */       {
/* 1022: 920 */         ungetChar(c);
/* 1023: 921 */         this.tokenEnd = (this.cursor - 1);
/* 1024: 922 */         this.string = new String(this.stringBuffer, 0, this.stringBufferTop);
/* 1025: 923 */         this.parser.reportError("msg.unterminated.re.lit");
/* 1026: 924 */         return;
/* 1027:     */       }
/* 1028: 926 */       if (c == 92)
/* 1029:     */       {
/* 1030: 927 */         addToString(c);
/* 1031: 928 */         c = getChar();
/* 1032:     */       }
/* 1033: 929 */       else if (c == 91)
/* 1034:     */       {
/* 1035: 930 */         inCharSet = true;
/* 1036:     */       }
/* 1037: 931 */       else if (c == 93)
/* 1038:     */       {
/* 1039: 932 */         inCharSet = false;
/* 1040:     */       }
/* 1041: 934 */       addToString(c);
/* 1042:     */     }
/* 1043: 936 */     int reEnd = this.stringBufferTop;
/* 1044:     */     for (;;)
/* 1045:     */     {
/* 1046: 939 */       if (matchChar(103))
/* 1047:     */       {
/* 1048: 940 */         addToString(103);
/* 1049:     */       }
/* 1050: 941 */       else if (matchChar(105))
/* 1051:     */       {
/* 1052: 942 */         addToString(105);
/* 1053:     */       }
/* 1054: 943 */       else if (matchChar(109))
/* 1055:     */       {
/* 1056: 944 */         addToString(109);
/* 1057:     */       }
/* 1058:     */       else
/* 1059:     */       {
/* 1060: 945 */         if (!matchChar(121)) {
/* 1061:     */           break;
/* 1062:     */         }
/* 1063: 946 */         addToString(121);
/* 1064:     */       }
/* 1065:     */     }
/* 1066: 950 */     this.tokenEnd = (start + this.stringBufferTop + 2);
/* 1067: 952 */     if (isAlpha(peekChar())) {
/* 1068: 953 */       this.parser.reportError("msg.invalid.re.flag");
/* 1069:     */     }
/* 1070: 956 */     this.string = new String(this.stringBuffer, 0, reEnd);
/* 1071: 957 */     this.regExpFlags = new String(this.stringBuffer, reEnd, this.stringBufferTop - reEnd);
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   String readAndClearRegExpFlags()
/* 1075:     */   {
/* 1076: 962 */     String flags = this.regExpFlags;
/* 1077: 963 */     this.regExpFlags = null;
/* 1078: 964 */     return flags;
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   boolean isXMLAttribute()
/* 1082:     */   {
/* 1083: 969 */     return this.xmlIsAttribute;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   int getFirstXMLToken()
/* 1087:     */     throws IOException
/* 1088:     */   {
/* 1089: 974 */     this.xmlOpenTagsCount = 0;
/* 1090: 975 */     this.xmlIsAttribute = false;
/* 1091: 976 */     this.xmlIsTagContent = false;
/* 1092: 977 */     if (!canUngetChar()) {
/* 1093: 978 */       return -1;
/* 1094:     */     }
/* 1095: 979 */     ungetChar(60);
/* 1096: 980 */     return getNextXMLToken();
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   int getNextXMLToken()
/* 1100:     */     throws IOException
/* 1101:     */   {
/* 1102: 985 */     this.tokenBeg = this.cursor;
/* 1103: 986 */     this.stringBufferTop = 0;
/* 1104: 988 */     for (int c = getChar(); c != -1; c = getChar()) {
/* 1105: 989 */       if (this.xmlIsTagContent)
/* 1106:     */       {
/* 1107: 990 */         switch (c)
/* 1108:     */         {
/* 1109:     */         case 62: 
/* 1110: 992 */           addToString(c);
/* 1111: 993 */           this.xmlIsTagContent = false;
/* 1112: 994 */           this.xmlIsAttribute = false;
/* 1113: 995 */           break;
/* 1114:     */         case 47: 
/* 1115: 997 */           addToString(c);
/* 1116: 998 */           if (peekChar() == 62)
/* 1117:     */           {
/* 1118: 999 */             c = getChar();
/* 1119:1000 */             addToString(c);
/* 1120:1001 */             this.xmlIsTagContent = false;
/* 1121:1002 */             this.xmlOpenTagsCount -= 1;
/* 1122:     */           }
/* 1123:     */           break;
/* 1124:     */         case 123: 
/* 1125:1006 */           ungetChar(c);
/* 1126:1007 */           this.string = getStringFromBuffer();
/* 1127:1008 */           return 145;
/* 1128:     */         case 34: 
/* 1129:     */         case 39: 
/* 1130:1011 */           addToString(c);
/* 1131:1012 */           if (!readQuotedString(c)) {
/* 1132:1012 */             return -1;
/* 1133:     */           }
/* 1134:     */           break;
/* 1135:     */         case 61: 
/* 1136:1015 */           addToString(c);
/* 1137:1016 */           this.xmlIsAttribute = true;
/* 1138:1017 */           break;
/* 1139:     */         case 9: 
/* 1140:     */         case 10: 
/* 1141:     */         case 13: 
/* 1142:     */         case 32: 
/* 1143:1022 */           addToString(c);
/* 1144:1023 */           break;
/* 1145:     */         default: 
/* 1146:1025 */           addToString(c);
/* 1147:1026 */           this.xmlIsAttribute = false;
/* 1148:     */         }
/* 1149:1030 */         if ((!this.xmlIsTagContent) && (this.xmlOpenTagsCount == 0))
/* 1150:     */         {
/* 1151:1031 */           this.string = getStringFromBuffer();
/* 1152:1032 */           return 148;
/* 1153:     */         }
/* 1154:     */       }
/* 1155:     */       else
/* 1156:     */       {
/* 1157:1035 */         switch (c)
/* 1158:     */         {
/* 1159:     */         case 60: 
/* 1160:1037 */           addToString(c);
/* 1161:1038 */           c = peekChar();
/* 1162:1039 */           switch (c)
/* 1163:     */           {
/* 1164:     */           case 33: 
/* 1165:1041 */             c = getChar();
/* 1166:1042 */             addToString(c);
/* 1167:1043 */             c = peekChar();
/* 1168:1044 */             switch (c)
/* 1169:     */             {
/* 1170:     */             case 45: 
/* 1171:1046 */               c = getChar();
/* 1172:1047 */               addToString(c);
/* 1173:1048 */               c = getChar();
/* 1174:1049 */               if (c == 45)
/* 1175:     */               {
/* 1176:1050 */                 addToString(c);
/* 1177:1051 */                 if (!readXmlComment()) {
/* 1178:1051 */                   return -1;
/* 1179:     */                 }
/* 1180:     */               }
/* 1181:     */               else
/* 1182:     */               {
/* 1183:1054 */                 this.stringBufferTop = 0;
/* 1184:1055 */                 this.string = null;
/* 1185:1056 */                 this.parser.addError("msg.XML.bad.form");
/* 1186:1057 */                 return -1;
/* 1187:     */               }
/* 1188:     */               break;
/* 1189:     */             case 91: 
/* 1190:1061 */               c = getChar();
/* 1191:1062 */               addToString(c);
/* 1192:1063 */               if ((getChar() == 67) && (getChar() == 68) && (getChar() == 65) && (getChar() == 84) && (getChar() == 65) && (getChar() == 91))
/* 1193:     */               {
/* 1194:1070 */                 addToString(67);
/* 1195:1071 */                 addToString(68);
/* 1196:1072 */                 addToString(65);
/* 1197:1073 */                 addToString(84);
/* 1198:1074 */                 addToString(65);
/* 1199:1075 */                 addToString(91);
/* 1200:1076 */                 if (!readCDATA()) {
/* 1201:1076 */                   return -1;
/* 1202:     */                 }
/* 1203:     */               }
/* 1204:     */               else
/* 1205:     */               {
/* 1206:1080 */                 this.stringBufferTop = 0;
/* 1207:1081 */                 this.string = null;
/* 1208:1082 */                 this.parser.addError("msg.XML.bad.form");
/* 1209:1083 */                 return -1;
/* 1210:     */               }
/* 1211:     */               break;
/* 1212:     */             default: 
/* 1213:1087 */               if (!readEntity()) {
/* 1214:1087 */                 return -1;
/* 1215:     */               }
/* 1216:     */               break;
/* 1217:     */             }
/* 1218:     */             break;
/* 1219:     */           case 63: 
/* 1220:1092 */             c = getChar();
/* 1221:1093 */             addToString(c);
/* 1222:1094 */             if (!readPI()) {
/* 1223:1094 */               return -1;
/* 1224:     */             }
/* 1225:     */             break;
/* 1226:     */           case 47: 
/* 1227:1098 */             c = getChar();
/* 1228:1099 */             addToString(c);
/* 1229:1100 */             if (this.xmlOpenTagsCount == 0)
/* 1230:     */             {
/* 1231:1102 */               this.stringBufferTop = 0;
/* 1232:1103 */               this.string = null;
/* 1233:1104 */               this.parser.addError("msg.XML.bad.form");
/* 1234:1105 */               return -1;
/* 1235:     */             }
/* 1236:1107 */             this.xmlIsTagContent = true;
/* 1237:1108 */             this.xmlOpenTagsCount -= 1;
/* 1238:1109 */             break;
/* 1239:     */           default: 
/* 1240:1112 */             this.xmlIsTagContent = true;
/* 1241:1113 */             this.xmlOpenTagsCount += 1;
/* 1242:     */           }
/* 1243:1114 */           break;
/* 1244:     */         case 123: 
/* 1245:1118 */           ungetChar(c);
/* 1246:1119 */           this.string = getStringFromBuffer();
/* 1247:1120 */           return 145;
/* 1248:     */         default: 
/* 1249:1122 */           addToString(c);
/* 1250:     */         }
/* 1251:     */       }
/* 1252:     */     }
/* 1253:1128 */     this.tokenEnd = this.cursor;
/* 1254:1129 */     this.stringBufferTop = 0;
/* 1255:1130 */     this.string = null;
/* 1256:1131 */     this.parser.addError("msg.XML.bad.form");
/* 1257:1132 */     return -1;
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   private boolean readQuotedString(int quote)
/* 1261:     */     throws IOException
/* 1262:     */   {
/* 1263:1140 */     for (int c = getChar(); c != -1; c = getChar())
/* 1264:     */     {
/* 1265:1141 */       addToString(c);
/* 1266:1142 */       if (c == quote) {
/* 1267:1142 */         return true;
/* 1268:     */       }
/* 1269:     */     }
/* 1270:1145 */     this.stringBufferTop = 0;
/* 1271:1146 */     this.string = null;
/* 1272:1147 */     this.parser.addError("msg.XML.bad.form");
/* 1273:1148 */     return false;
/* 1274:     */   }
/* 1275:     */   
/* 1276:     */   private boolean readXmlComment()
/* 1277:     */     throws IOException
/* 1278:     */   {
/* 1279:1156 */     for (int c = getChar(); c != -1;)
/* 1280:     */     {
/* 1281:1157 */       addToString(c);
/* 1282:1158 */       if ((c == 45) && (peekChar() == 45))
/* 1283:     */       {
/* 1284:1159 */         c = getChar();
/* 1285:1160 */         addToString(c);
/* 1286:1161 */         if (peekChar() == 62)
/* 1287:     */         {
/* 1288:1162 */           c = getChar();
/* 1289:1163 */           addToString(c);
/* 1290:1164 */           return true;
/* 1291:     */         }
/* 1292:     */       }
/* 1293:     */       else
/* 1294:     */       {
/* 1295:1169 */         c = getChar();
/* 1296:     */       }
/* 1297:     */     }
/* 1298:1172 */     this.stringBufferTop = 0;
/* 1299:1173 */     this.string = null;
/* 1300:1174 */     this.parser.addError("msg.XML.bad.form");
/* 1301:1175 */     return false;
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   private boolean readCDATA()
/* 1305:     */     throws IOException
/* 1306:     */   {
/* 1307:1183 */     for (int c = getChar(); c != -1;)
/* 1308:     */     {
/* 1309:1184 */       addToString(c);
/* 1310:1185 */       if ((c == 93) && (peekChar() == 93))
/* 1311:     */       {
/* 1312:1186 */         c = getChar();
/* 1313:1187 */         addToString(c);
/* 1314:1188 */         if (peekChar() == 62)
/* 1315:     */         {
/* 1316:1189 */           c = getChar();
/* 1317:1190 */           addToString(c);
/* 1318:1191 */           return true;
/* 1319:     */         }
/* 1320:     */       }
/* 1321:     */       else
/* 1322:     */       {
/* 1323:1196 */         c = getChar();
/* 1324:     */       }
/* 1325:     */     }
/* 1326:1199 */     this.stringBufferTop = 0;
/* 1327:1200 */     this.string = null;
/* 1328:1201 */     this.parser.addError("msg.XML.bad.form");
/* 1329:1202 */     return false;
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   private boolean readEntity()
/* 1333:     */     throws IOException
/* 1334:     */   {
/* 1335:1210 */     int declTags = 1;
/* 1336:1211 */     for (int c = getChar(); c != -1; c = getChar())
/* 1337:     */     {
/* 1338:1212 */       addToString(c);
/* 1339:1213 */       switch (c)
/* 1340:     */       {
/* 1341:     */       case 60: 
/* 1342:1215 */         declTags++;
/* 1343:1216 */         break;
/* 1344:     */       case 62: 
/* 1345:1218 */         declTags--;
/* 1346:1219 */         if (declTags == 0) {
/* 1347:1219 */           return true;
/* 1348:     */         }
/* 1349:     */         break;
/* 1350:     */       }
/* 1351:     */     }
/* 1352:1224 */     this.stringBufferTop = 0;
/* 1353:1225 */     this.string = null;
/* 1354:1226 */     this.parser.addError("msg.XML.bad.form");
/* 1355:1227 */     return false;
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   private boolean readPI()
/* 1359:     */     throws IOException
/* 1360:     */   {
/* 1361:1235 */     for (int c = getChar(); c != -1; c = getChar())
/* 1362:     */     {
/* 1363:1236 */       addToString(c);
/* 1364:1237 */       if ((c == 63) && (peekChar() == 62))
/* 1365:     */       {
/* 1366:1238 */         c = getChar();
/* 1367:1239 */         addToString(c);
/* 1368:1240 */         return true;
/* 1369:     */       }
/* 1370:     */     }
/* 1371:1244 */     this.stringBufferTop = 0;
/* 1372:1245 */     this.string = null;
/* 1373:1246 */     this.parser.addError("msg.XML.bad.form");
/* 1374:1247 */     return false;
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   private String getStringFromBuffer()
/* 1378:     */   {
/* 1379:1252 */     this.tokenEnd = this.cursor;
/* 1380:1253 */     return new String(this.stringBuffer, 0, this.stringBufferTop);
/* 1381:     */   }
/* 1382:     */   
/* 1383:     */   private void addToString(int c)
/* 1384:     */   {
/* 1385:1258 */     int N = this.stringBufferTop;
/* 1386:1259 */     if (N == this.stringBuffer.length)
/* 1387:     */     {
/* 1388:1260 */       char[] tmp = new char[this.stringBuffer.length * 2];
/* 1389:1261 */       System.arraycopy(this.stringBuffer, 0, tmp, 0, N);
/* 1390:1262 */       this.stringBuffer = tmp;
/* 1391:     */     }
/* 1392:1264 */     this.stringBuffer[N] = ((char)c);
/* 1393:1265 */     this.stringBufferTop = (N + 1);
/* 1394:     */   }
/* 1395:     */   
/* 1396:     */   private boolean canUngetChar()
/* 1397:     */   {
/* 1398:1269 */     return (this.ungetCursor == 0) || (this.ungetBuffer[(this.ungetCursor - 1)] != 10);
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   private void ungetChar(int c)
/* 1402:     */   {
/* 1403:1275 */     if ((this.ungetCursor != 0) && (this.ungetBuffer[(this.ungetCursor - 1)] == 10)) {
/* 1404:1276 */       Kit.codeBug();
/* 1405:     */     }
/* 1406:1277 */     this.ungetBuffer[(this.ungetCursor++)] = c;
/* 1407:1278 */     this.cursor -= 1;
/* 1408:     */   }
/* 1409:     */   
/* 1410:     */   private boolean matchChar(int test)
/* 1411:     */     throws IOException
/* 1412:     */   {
/* 1413:1283 */     int c = getCharIgnoreLineEnd();
/* 1414:1284 */     if (c == test)
/* 1415:     */     {
/* 1416:1285 */       this.tokenEnd = this.cursor;
/* 1417:1286 */       return true;
/* 1418:     */     }
/* 1419:1288 */     ungetCharIgnoreLineEnd(c);
/* 1420:1289 */     return false;
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   private int peekChar()
/* 1424:     */     throws IOException
/* 1425:     */   {
/* 1426:1295 */     int c = getChar();
/* 1427:1296 */     ungetChar(c);
/* 1428:1297 */     return c;
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   private int getChar()
/* 1432:     */     throws IOException
/* 1433:     */   {
/* 1434:1302 */     if (this.ungetCursor != 0)
/* 1435:     */     {
/* 1436:1303 */       this.cursor += 1;
/* 1437:1304 */       return this.ungetBuffer[(--this.ungetCursor)];
/* 1438:     */     }
/* 1439:     */     int c;
/* 1440:     */     label199:
/* 1441:     */     do
/* 1442:     */     {
/* 1443:     */       for (;;)
/* 1444:     */       {
/* 1445:     */         int c;
/* 1446:1309 */         if (this.sourceString != null)
/* 1447:     */         {
/* 1448:1310 */           if (this.sourceCursor == this.sourceEnd)
/* 1449:     */           {
/* 1450:1311 */             this.hitEOF = true;
/* 1451:1312 */             return -1;
/* 1452:     */           }
/* 1453:1314 */           this.cursor += 1;
/* 1454:1315 */           c = this.sourceString.charAt(this.sourceCursor++);
/* 1455:     */         }
/* 1456:     */         else
/* 1457:     */         {
/* 1458:1317 */           if ((this.sourceCursor == this.sourceEnd) && 
/* 1459:1318 */             (!fillSourceBuffer()))
/* 1460:     */           {
/* 1461:1319 */             this.hitEOF = true;
/* 1462:1320 */             return -1;
/* 1463:     */           }
/* 1464:1323 */           this.cursor += 1;
/* 1465:1324 */           c = this.sourceBuffer[(this.sourceCursor++)];
/* 1466:     */         }
/* 1467:1327 */         if (this.lineEndChar < 0) {
/* 1468:     */           break label199;
/* 1469:     */         }
/* 1470:1328 */         if ((this.lineEndChar != 13) || (c != 10)) {
/* 1471:     */           break;
/* 1472:     */         }
/* 1473:1329 */         this.lineEndChar = 10;
/* 1474:     */       }
/* 1475:1332 */       this.lineEndChar = -1;
/* 1476:1333 */       this.lineStart = (this.sourceCursor - 1);
/* 1477:1334 */       this.lineno += 1;
/* 1478:1337 */       if (c <= 127)
/* 1479:     */       {
/* 1480:1338 */         if ((c != 10) && (c != 13)) {
/* 1481:     */           break;
/* 1482:     */         }
/* 1483:1339 */         this.lineEndChar = c;
/* 1484:1340 */         c = 10; break;
/* 1485:     */       }
/* 1486:1343 */       if (c == 65279) {
/* 1487:1343 */         return c;
/* 1488:     */       }
/* 1489:1344 */     } while (isJSFormatChar(c));
/* 1490:1347 */     if (ScriptRuntime.isJSLineTerminator(c))
/* 1491:     */     {
/* 1492:1348 */       this.lineEndChar = c;
/* 1493:1349 */       c = 10;
/* 1494:     */     }
/* 1495:1352 */     return c;
/* 1496:     */   }
/* 1497:     */   
/* 1498:     */   private int getCharIgnoreLineEnd()
/* 1499:     */     throws IOException
/* 1500:     */   {
/* 1501:1358 */     if (this.ungetCursor != 0)
/* 1502:     */     {
/* 1503:1359 */       this.cursor += 1;
/* 1504:1360 */       return this.ungetBuffer[(--this.ungetCursor)];
/* 1505:     */     }
/* 1506:     */     int c;
/* 1507:     */     do
/* 1508:     */     {
/* 1509:     */       int c;
/* 1510:1365 */       if (this.sourceString != null)
/* 1511:     */       {
/* 1512:1366 */         if (this.sourceCursor == this.sourceEnd)
/* 1513:     */         {
/* 1514:1367 */           this.hitEOF = true;
/* 1515:1368 */           return -1;
/* 1516:     */         }
/* 1517:1370 */         this.cursor += 1;
/* 1518:1371 */         c = this.sourceString.charAt(this.sourceCursor++);
/* 1519:     */       }
/* 1520:     */       else
/* 1521:     */       {
/* 1522:1373 */         if ((this.sourceCursor == this.sourceEnd) && 
/* 1523:1374 */           (!fillSourceBuffer()))
/* 1524:     */         {
/* 1525:1375 */           this.hitEOF = true;
/* 1526:1376 */           return -1;
/* 1527:     */         }
/* 1528:1379 */         this.cursor += 1;
/* 1529:1380 */         c = this.sourceBuffer[(this.sourceCursor++)];
/* 1530:     */       }
/* 1531:1383 */       if (c <= 127)
/* 1532:     */       {
/* 1533:1384 */         if ((c != 10) && (c != 13)) {
/* 1534:     */           break;
/* 1535:     */         }
/* 1536:1385 */         this.lineEndChar = c;
/* 1537:1386 */         c = 10; break;
/* 1538:     */       }
/* 1539:1389 */       if (c == 65279) {
/* 1540:1389 */         return c;
/* 1541:     */       }
/* 1542:1390 */     } while (isJSFormatChar(c));
/* 1543:1393 */     if (ScriptRuntime.isJSLineTerminator(c))
/* 1544:     */     {
/* 1545:1394 */       this.lineEndChar = c;
/* 1546:1395 */       c = 10;
/* 1547:     */     }
/* 1548:1398 */     return c;
/* 1549:     */   }
/* 1550:     */   
/* 1551:     */   private void ungetCharIgnoreLineEnd(int c)
/* 1552:     */   {
/* 1553:1404 */     this.ungetBuffer[(this.ungetCursor++)] = c;
/* 1554:1405 */     this.cursor -= 1;
/* 1555:     */   }
/* 1556:     */   
/* 1557:     */   private void skipLine()
/* 1558:     */     throws IOException
/* 1559:     */   {
/* 1560:     */     int c;
/* 1561:1412 */     while (((c = getChar()) != -1) && (c != 10)) {}
/* 1562:1413 */     ungetChar(c);
/* 1563:1414 */     this.tokenEnd = this.cursor;
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   final int getOffset()
/* 1567:     */   {
/* 1568:1422 */     int n = this.sourceCursor - this.lineStart;
/* 1569:1423 */     if (this.lineEndChar >= 0) {
/* 1570:1423 */       n--;
/* 1571:     */     }
/* 1572:1424 */     return n;
/* 1573:     */   }
/* 1574:     */   
/* 1575:     */   final String getLine()
/* 1576:     */   {
/* 1577:1429 */     if (this.sourceString != null)
/* 1578:     */     {
/* 1579:1431 */       int lineEnd = this.sourceCursor;
/* 1580:1432 */       if (this.lineEndChar >= 0) {
/* 1581:1433 */         lineEnd--;
/* 1582:     */       } else {
/* 1583:1435 */         for (; lineEnd != this.sourceEnd; lineEnd++)
/* 1584:     */         {
/* 1585:1436 */           int c = this.sourceString.charAt(lineEnd);
/* 1586:1437 */           if (ScriptRuntime.isJSLineTerminator(c)) {
/* 1587:     */             break;
/* 1588:     */           }
/* 1589:     */         }
/* 1590:     */       }
/* 1591:1442 */       return this.sourceString.substring(this.lineStart, lineEnd);
/* 1592:     */     }
/* 1593:1445 */     int lineLength = this.sourceCursor - this.lineStart;
/* 1594:1446 */     if (this.lineEndChar >= 0) {
/* 1595:1447 */       lineLength--;
/* 1596:     */     } else {
/* 1597:1450 */       for (;; lineLength++)
/* 1598:     */       {
/* 1599:1451 */         int i = this.lineStart + lineLength;
/* 1600:1452 */         if (i == this.sourceEnd)
/* 1601:     */         {
/* 1602:     */           try
/* 1603:     */           {
/* 1604:1454 */             if (!fillSourceBuffer()) {
/* 1605:     */               break;
/* 1606:     */             }
/* 1607:     */           }
/* 1608:     */           catch (IOException ioe)
/* 1609:     */           {
/* 1610:     */             break;
/* 1611:     */           }
/* 1612:1461 */           i = this.lineStart + lineLength;
/* 1613:     */         }
/* 1614:1463 */         int c = this.sourceBuffer[i];
/* 1615:1464 */         if (ScriptRuntime.isJSLineTerminator(c)) {
/* 1616:     */           break;
/* 1617:     */         }
/* 1618:     */       }
/* 1619:     */     }
/* 1620:1469 */     return new String(this.sourceBuffer, this.lineStart, lineLength);
/* 1621:     */   }
/* 1622:     */   
/* 1623:     */   private boolean fillSourceBuffer()
/* 1624:     */     throws IOException
/* 1625:     */   {
/* 1626:1475 */     if (this.sourceString != null) {
/* 1627:1475 */       Kit.codeBug();
/* 1628:     */     }
/* 1629:1476 */     if (this.sourceEnd == this.sourceBuffer.length) {
/* 1630:1477 */       if ((this.lineStart != 0) && (!isMarkingComment()))
/* 1631:     */       {
/* 1632:1478 */         System.arraycopy(this.sourceBuffer, this.lineStart, this.sourceBuffer, 0, this.sourceEnd - this.lineStart);
/* 1633:     */         
/* 1634:1480 */         this.sourceEnd -= this.lineStart;
/* 1635:1481 */         this.sourceCursor -= this.lineStart;
/* 1636:1482 */         this.lineStart = 0;
/* 1637:     */       }
/* 1638:     */       else
/* 1639:     */       {
/* 1640:1484 */         char[] tmp = new char[this.sourceBuffer.length * 2];
/* 1641:1485 */         System.arraycopy(this.sourceBuffer, 0, tmp, 0, this.sourceEnd);
/* 1642:1486 */         this.sourceBuffer = tmp;
/* 1643:     */       }
/* 1644:     */     }
/* 1645:1489 */     int n = this.sourceReader.read(this.sourceBuffer, this.sourceEnd, this.sourceBuffer.length - this.sourceEnd);
/* 1646:1491 */     if (n < 0) {
/* 1647:1492 */       return false;
/* 1648:     */     }
/* 1649:1494 */     this.sourceEnd += n;
/* 1650:1495 */     return true;
/* 1651:     */   }
/* 1652:     */   
/* 1653:     */   public int getCursor()
/* 1654:     */   {
/* 1655:1502 */     return this.cursor;
/* 1656:     */   }
/* 1657:     */   
/* 1658:     */   public int getTokenBeg()
/* 1659:     */   {
/* 1660:1509 */     return this.tokenBeg;
/* 1661:     */   }
/* 1662:     */   
/* 1663:     */   public int getTokenEnd()
/* 1664:     */   {
/* 1665:1516 */     return this.tokenEnd;
/* 1666:     */   }
/* 1667:     */   
/* 1668:     */   public int getTokenLength()
/* 1669:     */   {
/* 1670:1523 */     return this.tokenEnd - this.tokenBeg;
/* 1671:     */   }
/* 1672:     */   
/* 1673:     */   public Token.CommentType getCommentType()
/* 1674:     */   {
/* 1675:1531 */     return this.commentType;
/* 1676:     */   }
/* 1677:     */   
/* 1678:     */   private void markCommentStart()
/* 1679:     */   {
/* 1680:1535 */     markCommentStart("");
/* 1681:     */   }
/* 1682:     */   
/* 1683:     */   private void markCommentStart(String prefix)
/* 1684:     */   {
/* 1685:1539 */     if ((this.parser.compilerEnv.isRecordingComments()) && (this.sourceReader != null))
/* 1686:     */     {
/* 1687:1540 */       this.commentPrefix = prefix;
/* 1688:1541 */       this.commentCursor = (this.sourceCursor - 1);
/* 1689:     */     }
/* 1690:     */   }
/* 1691:     */   
/* 1692:     */   private boolean isMarkingComment()
/* 1693:     */   {
/* 1694:1546 */     return this.commentCursor != -1;
/* 1695:     */   }
/* 1696:     */   
/* 1697:     */   final String getAndResetCurrentComment()
/* 1698:     */   {
/* 1699:1550 */     if (this.sourceString != null)
/* 1700:     */     {
/* 1701:1551 */       if (isMarkingComment()) {
/* 1702:1551 */         Kit.codeBug();
/* 1703:     */       }
/* 1704:1552 */       return this.sourceString.substring(this.tokenBeg, this.tokenEnd);
/* 1705:     */     }
/* 1706:1554 */     if (!isMarkingComment()) {
/* 1707:1554 */       Kit.codeBug();
/* 1708:     */     }
/* 1709:1555 */     StringBuilder comment = new StringBuilder(this.commentPrefix);
/* 1710:1556 */     comment.append(this.sourceBuffer, this.commentCursor, getTokenLength() - this.commentPrefix.length());
/* 1711:     */     
/* 1712:1558 */     this.commentCursor = -1;
/* 1713:1559 */     return comment.toString();
/* 1714:     */   }
/* 1715:     */   
/* 1716:1572 */   private String string = "";
/* 1717:     */   private double number;
/* 1718:     */   private boolean isOctal;
/* 1719:     */   private int quoteChar;
/* 1720:1579 */   private char[] stringBuffer = new char[''];
/* 1721:     */   private int stringBufferTop;
/* 1722:1581 */   private ObjToIntMap allStrings = new ObjToIntMap(50);
/* 1723:1584 */   private final int[] ungetBuffer = new int[3];
/* 1724:     */   private int ungetCursor;
/* 1725:1587 */   private boolean hitEOF = false;
/* 1726:1589 */   private int lineStart = 0;
/* 1727:1590 */   private int lineEndChar = -1;
/* 1728:     */   int lineno;
/* 1729:     */   private String sourceString;
/* 1730:     */   private Reader sourceReader;
/* 1731:     */   private char[] sourceBuffer;
/* 1732:     */   private int sourceEnd;
/* 1733:     */   int sourceCursor;
/* 1734:     */   int cursor;
/* 1735:     */   int tokenBeg;
/* 1736:     */   int tokenEnd;
/* 1737:     */   Token.CommentType commentType;
/* 1738:     */   private boolean xmlIsAttribute;
/* 1739:     */   private boolean xmlIsTagContent;
/* 1740:     */   private int xmlOpenTagsCount;
/* 1741:     */   private Parser parser;
/* 1742:1621 */   private String commentPrefix = "";
/* 1743:1622 */   private int commentCursor = -1;
/* 1744:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.TokenStream
 * JD-Core Version:    0.7.0.1
 */