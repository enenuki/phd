/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import antlr.collections.impl.Vector;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.PrintWriter;
/*   8:    */ 
/*   9:    */ public abstract class CodeGenerator
/*  10:    */ {
/*  11:    */   protected Tool antlrTool;
/*  12: 54 */   protected int tabs = 0;
/*  13:    */   protected transient PrintWriter currentOutput;
/*  14: 60 */   protected Grammar grammar = null;
/*  15:    */   protected Vector bitsetsUsed;
/*  16:    */   protected DefineGrammarSymbols behavior;
/*  17:    */   protected LLkGrammarAnalyzer analyzer;
/*  18:    */   protected CharFormatter charFormatter;
/*  19: 77 */   protected boolean DEBUG_CODE_GENERATOR = false;
/*  20:    */   protected static final int DEFAULT_MAKE_SWITCH_THRESHOLD = 2;
/*  21:    */   protected static final int DEFAULT_BITSET_TEST_THRESHOLD = 4;
/*  22:    */   protected static final int BITSET_OPTIMIZE_INIT_THRESHOLD = 8;
/*  23: 94 */   protected int makeSwitchThreshold = 2;
/*  24:102 */   protected int bitsetTestThreshold = 4;
/*  25:104 */   private static boolean OLD_ACTION_TRANSLATOR = true;
/*  26:106 */   public static String TokenTypesFileSuffix = "TokenTypes";
/*  27:107 */   public static String TokenTypesFileExt = ".txt";
/*  28:    */   
/*  29:    */   protected void _print(String paramString)
/*  30:    */   {
/*  31:118 */     if (paramString != null) {
/*  32:119 */       this.currentOutput.print(paramString);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void _printAction(String paramString)
/*  37:    */   {
/*  38:129 */     if (paramString == null) {
/*  39:130 */       return;
/*  40:    */     }
/*  41:134 */     int i = 0;
/*  42:135 */     while ((i < paramString.length()) && (Character.isSpaceChar(paramString.charAt(i)))) {
/*  43:136 */       i++;
/*  44:    */     }
/*  45:140 */     int j = paramString.length() - 1;
/*  46:141 */     while ((j > i) && (Character.isSpaceChar(paramString.charAt(j)))) {
/*  47:142 */       j--;
/*  48:    */     }
/*  49:145 */     char c = '\000';
/*  50:146 */     for (int k = i; k <= j;)
/*  51:    */     {
/*  52:147 */       c = paramString.charAt(k);
/*  53:148 */       k++;
/*  54:149 */       int m = 0;
/*  55:150 */       switch (c)
/*  56:    */       {
/*  57:    */       case '\n': 
/*  58:152 */         m = 1;
/*  59:153 */         break;
/*  60:    */       case '\r': 
/*  61:155 */         if ((k <= j) && (paramString.charAt(k) == '\n')) {
/*  62:156 */           k++;
/*  63:    */         }
/*  64:158 */         m = 1;
/*  65:159 */         break;
/*  66:    */       default: 
/*  67:161 */         this.currentOutput.print(c);
/*  68:    */       }
/*  69:164 */       if (m != 0)
/*  70:    */       {
/*  71:165 */         this.currentOutput.println();
/*  72:166 */         printTabs();
/*  73:168 */         while ((k <= j) && (Character.isSpaceChar(paramString.charAt(k)))) {
/*  74:169 */           k++;
/*  75:    */         }
/*  76:171 */         m = 0;
/*  77:    */       }
/*  78:    */     }
/*  79:174 */     this.currentOutput.println();
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void _println(String paramString)
/*  83:    */   {
/*  84:182 */     if (paramString != null) {
/*  85:183 */       this.currentOutput.println(paramString);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static boolean elementsAreRange(int[] paramArrayOfInt)
/*  90:    */   {
/*  91:192 */     if (paramArrayOfInt.length == 0) {
/*  92:193 */       return false;
/*  93:    */     }
/*  94:195 */     int i = paramArrayOfInt[0];
/*  95:196 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/*  96:197 */     if (paramArrayOfInt.length <= 2) {
/*  97:199 */       return false;
/*  98:    */     }
/*  99:201 */     if (j - i + 1 > paramArrayOfInt.length) {
/* 100:203 */       return false;
/* 101:    */     }
/* 102:205 */     int k = i + 1;
/* 103:206 */     for (int m = 1; m < paramArrayOfInt.length - 1; m++)
/* 104:    */     {
/* 105:207 */       if (k != paramArrayOfInt[m]) {
/* 106:209 */         return false;
/* 107:    */       }
/* 108:211 */       k++;
/* 109:    */     }
/* 110:213 */     return true;
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected String extractIdOfAction(Token paramToken)
/* 114:    */   {
/* 115:224 */     return extractIdOfAction(paramToken.getText(), paramToken.getLine(), paramToken.getColumn());
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected String extractIdOfAction(String paramString, int paramInt1, int paramInt2)
/* 119:    */   {
/* 120:237 */     paramString = removeAssignmentFromDeclaration(paramString);
/* 121:240 */     for (int i = paramString.length() - 2; i >= 0; i--) {
/* 122:242 */       if ((!Character.isLetterOrDigit(paramString.charAt(i))) && (paramString.charAt(i) != '_')) {
/* 123:244 */         return paramString.substring(i + 1);
/* 124:    */       }
/* 125:    */     }
/* 126:249 */     this.antlrTool.warning("Ill-formed action", this.grammar.getFilename(), paramInt1, paramInt2);
/* 127:250 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected String extractTypeOfAction(Token paramToken)
/* 131:    */   {
/* 132:261 */     return extractTypeOfAction(paramToken.getText(), paramToken.getLine(), paramToken.getColumn());
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected String extractTypeOfAction(String paramString, int paramInt1, int paramInt2)
/* 136:    */   {
/* 137:273 */     paramString = removeAssignmentFromDeclaration(paramString);
/* 138:276 */     for (int i = paramString.length() - 2; i >= 0; i--) {
/* 139:278 */       if ((!Character.isLetterOrDigit(paramString.charAt(i))) && (paramString.charAt(i) != '_')) {
/* 140:280 */         return paramString.substring(0, i + 1);
/* 141:    */       }
/* 142:    */     }
/* 143:285 */     this.antlrTool.warning("Ill-formed action", this.grammar.getFilename(), paramInt1, paramInt2);
/* 144:286 */     return "";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public abstract void gen();
/* 148:    */   
/* 149:    */   public abstract void gen(ActionElement paramActionElement);
/* 150:    */   
/* 151:    */   public abstract void gen(AlternativeBlock paramAlternativeBlock);
/* 152:    */   
/* 153:    */   public abstract void gen(BlockEndElement paramBlockEndElement);
/* 154:    */   
/* 155:    */   public abstract void gen(CharLiteralElement paramCharLiteralElement);
/* 156:    */   
/* 157:    */   public abstract void gen(CharRangeElement paramCharRangeElement);
/* 158:    */   
/* 159:    */   public abstract void gen(LexerGrammar paramLexerGrammar)
/* 160:    */     throws IOException;
/* 161:    */   
/* 162:    */   public abstract void gen(OneOrMoreBlock paramOneOrMoreBlock);
/* 163:    */   
/* 164:    */   public abstract void gen(ParserGrammar paramParserGrammar)
/* 165:    */     throws IOException;
/* 166:    */   
/* 167:    */   public abstract void gen(RuleRefElement paramRuleRefElement);
/* 168:    */   
/* 169:    */   public abstract void gen(StringLiteralElement paramStringLiteralElement);
/* 170:    */   
/* 171:    */   public abstract void gen(TokenRangeElement paramTokenRangeElement);
/* 172:    */   
/* 173:    */   public abstract void gen(TokenRefElement paramTokenRefElement);
/* 174:    */   
/* 175:    */   public abstract void gen(TreeElement paramTreeElement);
/* 176:    */   
/* 177:    */   public abstract void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/* 178:    */     throws IOException;
/* 179:    */   
/* 180:    */   public abstract void gen(WildcardElement paramWildcardElement);
/* 181:    */   
/* 182:    */   public abstract void gen(ZeroOrMoreBlock paramZeroOrMoreBlock);
/* 183:    */   
/* 184:    */   protected void genTokenInterchange(TokenManager paramTokenManager)
/* 185:    */     throws IOException
/* 186:    */   {
/* 187:372 */     String str1 = paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt;
/* 188:373 */     this.currentOutput = this.antlrTool.openOutputFile(str1);
/* 189:    */     
/* 190:375 */     println("// $ANTLR " + Tool.version + ": " + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + " -> " + str1 + "$");
/* 191:    */     
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:381 */     this.tabs = 0;
/* 197:    */     
/* 198:    */ 
/* 199:384 */     println(paramTokenManager.getName() + "    // output token vocab name");
/* 200:    */     
/* 201:    */ 
/* 202:387 */     Vector localVector = paramTokenManager.getVocabulary();
/* 203:388 */     for (int i = 4; i < localVector.size(); i++)
/* 204:    */     {
/* 205:389 */       String str2 = (String)localVector.elementAt(i);
/* 206:390 */       if (this.DEBUG_CODE_GENERATOR) {
/* 207:391 */         System.out.println("gen persistence file entry for: " + str2);
/* 208:    */       }
/* 209:393 */       if ((str2 != null) && (!str2.startsWith("<")))
/* 210:    */       {
/* 211:    */         Object localObject;
/* 212:395 */         if (str2.startsWith("\""))
/* 213:    */         {
/* 214:396 */           localObject = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str2);
/* 215:397 */           if ((localObject != null) && (((StringLiteralSymbol)localObject).label != null)) {
/* 216:398 */             print(((StringLiteralSymbol)localObject).label + "=");
/* 217:    */           }
/* 218:400 */           println(str2 + "=" + i);
/* 219:    */         }
/* 220:    */         else
/* 221:    */         {
/* 222:403 */           print(str2);
/* 223:    */           
/* 224:405 */           localObject = paramTokenManager.getTokenSymbol(str2);
/* 225:406 */           if (localObject == null) {
/* 226:407 */             this.antlrTool.warning("undefined token symbol: " + str2);
/* 227:410 */           } else if (((TokenSymbol)localObject).getParaphrase() != null) {
/* 228:411 */             print("(" + ((TokenSymbol)localObject).getParaphrase() + ")");
/* 229:    */           }
/* 230:414 */           println("=" + i);
/* 231:    */         }
/* 232:    */       }
/* 233:    */     }
/* 234:420 */     this.currentOutput.close();
/* 235:421 */     this.currentOutput = null;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String processStringForASTConstructor(String paramString)
/* 239:    */   {
/* 240:430 */     return paramString;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public abstract String getASTCreateString(Vector paramVector);
/* 244:    */   
/* 245:    */   public abstract String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString);
/* 246:    */   
/* 247:    */   protected String getBitsetName(int paramInt)
/* 248:    */   {
/* 249:449 */     return "_tokenSet_" + paramInt;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public static String encodeLexerRuleName(String paramString)
/* 253:    */   {
/* 254:453 */     return "m" + paramString;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public static String decodeLexerRuleName(String paramString)
/* 258:    */   {
/* 259:457 */     if (paramString == null) {
/* 260:458 */       return null;
/* 261:    */     }
/* 262:460 */     return paramString.substring(1, paramString.length());
/* 263:    */   }
/* 264:    */   
/* 265:    */   public abstract String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo);
/* 266:    */   
/* 267:    */   protected int markBitsetForGen(BitSet paramBitSet)
/* 268:    */   {
/* 269:484 */     for (int i = 0; i < this.bitsetsUsed.size(); i++)
/* 270:    */     {
/* 271:485 */       BitSet localBitSet = (BitSet)this.bitsetsUsed.elementAt(i);
/* 272:486 */       if (paramBitSet.equals(localBitSet)) {
/* 273:488 */         return i;
/* 274:    */       }
/* 275:    */     }
/* 276:493 */     this.bitsetsUsed.appendElement(paramBitSet.clone());
/* 277:494 */     return this.bitsetsUsed.size() - 1;
/* 278:    */   }
/* 279:    */   
/* 280:    */   protected void print(String paramString)
/* 281:    */   {
/* 282:502 */     if (paramString != null)
/* 283:    */     {
/* 284:503 */       printTabs();
/* 285:504 */       this.currentOutput.print(paramString);
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   protected void printAction(String paramString)
/* 290:    */   {
/* 291:514 */     if (paramString != null)
/* 292:    */     {
/* 293:515 */       printTabs();
/* 294:516 */       _printAction(paramString);
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   protected void println(String paramString)
/* 299:    */   {
/* 300:525 */     if (paramString != null)
/* 301:    */     {
/* 302:526 */       printTabs();
/* 303:527 */       this.currentOutput.println(paramString);
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected void printTabs()
/* 308:    */   {
/* 309:535 */     for (int i = 1; i <= this.tabs; i++) {
/* 310:536 */       this.currentOutput.print("\t");
/* 311:    */     }
/* 312:    */   }
/* 313:    */   
/* 314:    */   protected abstract String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo);
/* 315:    */   
/* 316:    */   public String getFOLLOWBitSet(String paramString, int paramInt)
/* 317:    */   {
/* 318:550 */     GrammarSymbol localGrammarSymbol = this.grammar.getSymbol(paramString);
/* 319:551 */     if (!(localGrammarSymbol instanceof RuleSymbol)) {
/* 320:552 */       return null;
/* 321:    */     }
/* 322:554 */     RuleBlock localRuleBlock = ((RuleSymbol)localGrammarSymbol).getBlock();
/* 323:555 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(paramInt, localRuleBlock.endNode);
/* 324:556 */     String str = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 325:557 */     return str;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String getFIRSTBitSet(String paramString, int paramInt)
/* 329:    */   {
/* 330:561 */     GrammarSymbol localGrammarSymbol = this.grammar.getSymbol(paramString);
/* 331:562 */     if (!(localGrammarSymbol instanceof RuleSymbol)) {
/* 332:563 */       return null;
/* 333:    */     }
/* 334:565 */     RuleBlock localRuleBlock = ((RuleSymbol)localGrammarSymbol).getBlock();
/* 335:566 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.look(paramInt, localRuleBlock);
/* 336:567 */     String str = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 337:568 */     return str;
/* 338:    */   }
/* 339:    */   
/* 340:    */   protected String removeAssignmentFromDeclaration(String paramString)
/* 341:    */   {
/* 342:579 */     if (paramString.indexOf('=') >= 0) {
/* 343:579 */       paramString = paramString.substring(0, paramString.indexOf('=')).trim();
/* 344:    */     }
/* 345:580 */     return paramString;
/* 346:    */   }
/* 347:    */   
/* 348:    */   private void reset()
/* 349:    */   {
/* 350:585 */     this.tabs = 0;
/* 351:    */     
/* 352:587 */     this.bitsetsUsed = new Vector();
/* 353:588 */     this.currentOutput = null;
/* 354:589 */     this.grammar = null;
/* 355:590 */     this.DEBUG_CODE_GENERATOR = false;
/* 356:591 */     this.makeSwitchThreshold = 2;
/* 357:592 */     this.bitsetTestThreshold = 4;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public static String reverseLexerRuleName(String paramString)
/* 361:    */   {
/* 362:596 */     return paramString.substring(1, paramString.length());
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setAnalyzer(LLkGrammarAnalyzer paramLLkGrammarAnalyzer)
/* 366:    */   {
/* 367:600 */     this.analyzer = paramLLkGrammarAnalyzer;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setBehavior(DefineGrammarSymbols paramDefineGrammarSymbols)
/* 371:    */   {
/* 372:604 */     this.behavior = paramDefineGrammarSymbols;
/* 373:    */   }
/* 374:    */   
/* 375:    */   protected void setGrammar(Grammar paramGrammar)
/* 376:    */   {
/* 377:609 */     reset();
/* 378:610 */     this.grammar = paramGrammar;
/* 379:    */     Token localToken2;
/* 380:612 */     if (this.grammar.hasOption("codeGenMakeSwitchThreshold")) {
/* 381:    */       try
/* 382:    */       {
/* 383:614 */         this.makeSwitchThreshold = this.grammar.getIntegerOption("codeGenMakeSwitchThreshold");
/* 384:    */       }
/* 385:    */       catch (NumberFormatException localNumberFormatException1)
/* 386:    */       {
/* 387:618 */         localToken2 = this.grammar.getOption("codeGenMakeSwitchThreshold");
/* 388:619 */         this.antlrTool.error("option 'codeGenMakeSwitchThreshold' must be an integer", this.grammar.getClassName(), localToken2.getLine(), localToken2.getColumn());
/* 389:    */       }
/* 390:    */     }
/* 391:628 */     if (this.grammar.hasOption("codeGenBitsetTestThreshold")) {
/* 392:    */       try
/* 393:    */       {
/* 394:630 */         this.bitsetTestThreshold = this.grammar.getIntegerOption("codeGenBitsetTestThreshold");
/* 395:    */       }
/* 396:    */       catch (NumberFormatException localNumberFormatException2)
/* 397:    */       {
/* 398:634 */         localToken2 = this.grammar.getOption("codeGenBitsetTestThreshold");
/* 399:635 */         this.antlrTool.error("option 'codeGenBitsetTestThreshold' must be an integer", this.grammar.getClassName(), localToken2.getLine(), localToken2.getColumn());
/* 400:    */       }
/* 401:    */     }
/* 402:644 */     if (this.grammar.hasOption("codeGenDebug"))
/* 403:    */     {
/* 404:645 */       Token localToken1 = this.grammar.getOption("codeGenDebug");
/* 405:646 */       if (localToken1.getText().equals("true")) {
/* 406:648 */         this.DEBUG_CODE_GENERATOR = true;
/* 407:650 */       } else if (localToken1.getText().equals("false")) {
/* 408:652 */         this.DEBUG_CODE_GENERATOR = false;
/* 409:    */       } else {
/* 410:655 */         this.antlrTool.error("option 'codeGenDebug' must be true or false", this.grammar.getClassName(), localToken1.getLine(), localToken1.getColumn());
/* 411:    */       }
/* 412:    */     }
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void setTool(Tool paramTool)
/* 416:    */   {
/* 417:661 */     this.antlrTool = paramTool;
/* 418:    */   }
/* 419:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CodeGenerator
 * JD-Core Version:    0.7.0.1
 */