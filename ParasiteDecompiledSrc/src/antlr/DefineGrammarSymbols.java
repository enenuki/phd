/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ 
/*   6:    */ public class DefineGrammarSymbols
/*   7:    */   implements ANTLRGrammarParseBehavior
/*   8:    */ {
/*   9: 22 */   protected Hashtable grammars = new Hashtable();
/*  10: 24 */   protected Hashtable tokenManagers = new Hashtable();
/*  11:    */   protected Grammar grammar;
/*  12:    */   protected Tool tool;
/*  13:    */   LLkAnalyzer analyzer;
/*  14:    */   String[] args;
/*  15:    */   static final String DEFAULT_TOKENMANAGER_NAME = "*default";
/*  16: 38 */   protected Hashtable headerActions = new Hashtable();
/*  17: 40 */   Token thePreambleAction = new CommonToken(0, "");
/*  18: 42 */   String language = "Java";
/*  19: 44 */   protected int numLexers = 0;
/*  20: 45 */   protected int numParsers = 0;
/*  21: 46 */   protected int numTreeParsers = 0;
/*  22:    */   
/*  23:    */   public DefineGrammarSymbols(Tool paramTool, String[] paramArrayOfString, LLkAnalyzer paramLLkAnalyzer)
/*  24:    */   {
/*  25: 49 */     this.tool = paramTool;
/*  26: 50 */     this.args = paramArrayOfString;
/*  27: 51 */     this.analyzer = paramLLkAnalyzer;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void _refStringLiteral(Token paramToken1, Token paramToken2, int paramInt, boolean paramBoolean)
/*  31:    */   {
/*  32: 55 */     if (!(this.grammar instanceof LexerGrammar))
/*  33:    */     {
/*  34: 57 */       String str = paramToken1.getText();
/*  35: 58 */       if (this.grammar.tokenManager.getTokenSymbol(str) != null) {
/*  36: 60 */         return;
/*  37:    */       }
/*  38: 62 */       StringLiteralSymbol localStringLiteralSymbol = new StringLiteralSymbol(str);
/*  39: 63 */       int i = this.grammar.tokenManager.nextTokenType();
/*  40: 64 */       localStringLiteralSymbol.setTokenType(i);
/*  41: 65 */       this.grammar.tokenManager.define(localStringLiteralSymbol);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void _refToken(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
/*  46:    */   {
/*  47: 77 */     String str = paramToken2.getText();
/*  48: 78 */     if (!this.grammar.tokenManager.tokenDefined(str))
/*  49:    */     {
/*  50: 84 */       int i = this.grammar.tokenManager.nextTokenType();
/*  51: 85 */       TokenSymbol localTokenSymbol = new TokenSymbol(str);
/*  52: 86 */       localTokenSymbol.setTokenType(i);
/*  53: 87 */       this.grammar.tokenManager.define(localTokenSymbol);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void abortGrammar()
/*  58:    */   {
/*  59: 93 */     if ((this.grammar != null) && (this.grammar.getClassName() != null)) {
/*  60: 94 */       this.grammars.remove(this.grammar.getClassName());
/*  61:    */     }
/*  62: 96 */     this.grammar = null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void beginAlt(boolean paramBoolean) {}
/*  66:    */   
/*  67:    */   public void beginChildList() {}
/*  68:    */   
/*  69:    */   public void beginExceptionGroup() {}
/*  70:    */   
/*  71:    */   public void beginExceptionSpec(Token paramToken) {}
/*  72:    */   
/*  73:    */   public void beginSubRule(Token paramToken1, Token paramToken2, boolean paramBoolean) {}
/*  74:    */   
/*  75:    */   public void beginTree(Token paramToken)
/*  76:    */     throws SemanticException
/*  77:    */   {}
/*  78:    */   
/*  79:    */   public void defineRuleName(Token paramToken, String paramString1, boolean paramBoolean, String paramString2)
/*  80:    */     throws SemanticException
/*  81:    */   {
/*  82:124 */     String str = paramToken.getText();
/*  83:127 */     if (paramToken.type == 24)
/*  84:    */     {
/*  85:129 */       str = CodeGenerator.encodeLexerRuleName(str);
/*  86:131 */       if (!this.grammar.tokenManager.tokenDefined(paramToken.getText()))
/*  87:    */       {
/*  88:132 */         int i = this.grammar.tokenManager.nextTokenType();
/*  89:133 */         TokenSymbol localTokenSymbol = new TokenSymbol(paramToken.getText());
/*  90:134 */         localTokenSymbol.setTokenType(i);
/*  91:135 */         this.grammar.tokenManager.define(localTokenSymbol);
/*  92:    */       }
/*  93:    */     }
/*  94:    */     RuleSymbol localRuleSymbol;
/*  95:140 */     if (this.grammar.isDefined(str))
/*  96:    */     {
/*  97:142 */       localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/*  98:144 */       if (localRuleSymbol.isDefined()) {
/*  99:145 */         this.tool.error("redefinition of rule " + str, this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 100:    */       }
/* 101:    */     }
/* 102:    */     else
/* 103:    */     {
/* 104:149 */       localRuleSymbol = new RuleSymbol(str);
/* 105:150 */       this.grammar.define(localRuleSymbol);
/* 106:    */     }
/* 107:152 */     localRuleSymbol.setDefined();
/* 108:153 */     localRuleSymbol.access = paramString1;
/* 109:154 */     localRuleSymbol.comment = paramString2;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void defineToken(Token paramToken1, Token paramToken2)
/* 113:    */   {
/* 114:161 */     String str1 = null;
/* 115:162 */     String str2 = null;
/* 116:163 */     if (paramToken1 != null) {
/* 117:164 */       str1 = paramToken1.getText();
/* 118:    */     }
/* 119:166 */     if (paramToken2 != null) {
/* 120:167 */       str2 = paramToken2.getText();
/* 121:    */     }
/* 122:171 */     if (str2 != null)
/* 123:    */     {
/* 124:172 */       StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)this.grammar.tokenManager.getTokenSymbol(str2);
/* 125:173 */       if (localStringLiteralSymbol != null)
/* 126:    */       {
/* 127:179 */         if ((str1 == null) || (localStringLiteralSymbol.getLabel() != null))
/* 128:    */         {
/* 129:180 */           this.tool.warning("Redefinition of literal in tokens {...}: " + str2, this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 130:181 */           return;
/* 131:    */         }
/* 132:183 */         if (str1 != null)
/* 133:    */         {
/* 134:185 */           localStringLiteralSymbol.setLabel(str1);
/* 135:    */           
/* 136:187 */           this.grammar.tokenManager.mapToTokenSymbol(str1, localStringLiteralSymbol);
/* 137:    */         }
/* 138:    */       }
/* 139:192 */       if (str1 != null)
/* 140:    */       {
/* 141:193 */         TokenSymbol localTokenSymbol1 = this.grammar.tokenManager.getTokenSymbol(str1);
/* 142:194 */         if (localTokenSymbol1 != null)
/* 143:    */         {
/* 144:197 */           if ((localTokenSymbol1 instanceof StringLiteralSymbol))
/* 145:    */           {
/* 146:198 */             this.tool.warning("Redefinition of token in tokens {...}: " + str1, this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 147:199 */             return;
/* 148:    */           }
/* 149:207 */           int k = localTokenSymbol1.getTokenType();
/* 150:    */           
/* 151:209 */           localStringLiteralSymbol = new StringLiteralSymbol(str2);
/* 152:210 */           localStringLiteralSymbol.setTokenType(k);
/* 153:211 */           localStringLiteralSymbol.setLabel(str1);
/* 154:    */           
/* 155:213 */           this.grammar.tokenManager.define(localStringLiteralSymbol);
/* 156:    */           
/* 157:215 */           this.grammar.tokenManager.mapToTokenSymbol(str1, localStringLiteralSymbol);
/* 158:216 */           return;
/* 159:    */         }
/* 160:    */       }
/* 161:220 */       localStringLiteralSymbol = new StringLiteralSymbol(str2);
/* 162:221 */       int j = this.grammar.tokenManager.nextTokenType();
/* 163:222 */       localStringLiteralSymbol.setTokenType(j);
/* 164:223 */       localStringLiteralSymbol.setLabel(str1);
/* 165:224 */       this.grammar.tokenManager.define(localStringLiteralSymbol);
/* 166:225 */       if (str1 != null) {
/* 167:227 */         this.grammar.tokenManager.mapToTokenSymbol(str1, localStringLiteralSymbol);
/* 168:    */       }
/* 169:    */     }
/* 170:    */     else
/* 171:    */     {
/* 172:233 */       if (this.grammar.tokenManager.tokenDefined(str1))
/* 173:    */       {
/* 174:234 */         this.tool.warning("Redefinition of token in tokens {...}: " + str1, this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 175:235 */         return;
/* 176:    */       }
/* 177:237 */       int i = this.grammar.tokenManager.nextTokenType();
/* 178:238 */       TokenSymbol localTokenSymbol2 = new TokenSymbol(str1);
/* 179:239 */       localTokenSymbol2.setTokenType(i);
/* 180:240 */       this.grammar.tokenManager.define(localTokenSymbol2);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void endAlt() {}
/* 185:    */   
/* 186:    */   public void endChildList() {}
/* 187:    */   
/* 188:    */   public void endExceptionGroup() {}
/* 189:    */   
/* 190:    */   public void endExceptionSpec() {}
/* 191:    */   
/* 192:    */   public void endGrammar() {}
/* 193:    */   
/* 194:    */   public void endOptions()
/* 195:    */   {
/* 196:    */     Object localObject;
/* 197:266 */     if ((this.grammar.exportVocab == null) && (this.grammar.importVocab == null))
/* 198:    */     {
/* 199:267 */       this.grammar.exportVocab = this.grammar.getClassName();
/* 200:269 */       if (this.tokenManagers.containsKey("*default"))
/* 201:    */       {
/* 202:271 */         this.grammar.exportVocab = "*default";
/* 203:272 */         localObject = (TokenManager)this.tokenManagers.get("*default");
/* 204:    */         
/* 205:274 */         this.grammar.setTokenManager((TokenManager)localObject);
/* 206:275 */         return;
/* 207:    */       }
/* 208:279 */       localObject = new SimpleTokenManager(this.grammar.exportVocab, this.tool);
/* 209:280 */       this.grammar.setTokenManager((TokenManager)localObject);
/* 210:    */       
/* 211:282 */       this.tokenManagers.put(this.grammar.exportVocab, localObject);
/* 212:    */       
/* 213:284 */       this.tokenManagers.put("*default", localObject); return;
/* 214:    */     }
/* 215:    */     TokenManager localTokenManager;
/* 216:289 */     if ((this.grammar.exportVocab == null) && (this.grammar.importVocab != null))
/* 217:    */     {
/* 218:290 */       this.grammar.exportVocab = this.grammar.getClassName();
/* 219:292 */       if (this.grammar.importVocab.equals(this.grammar.exportVocab))
/* 220:    */       {
/* 221:293 */         this.tool.warning("Grammar " + this.grammar.getClassName() + " cannot have importVocab same as default output vocab (grammar name); ignored.");
/* 222:    */         
/* 223:    */ 
/* 224:296 */         this.grammar.importVocab = null;
/* 225:297 */         endOptions();
/* 226:298 */         return;
/* 227:    */       }
/* 228:302 */       if (this.tokenManagers.containsKey(this.grammar.importVocab))
/* 229:    */       {
/* 230:306 */         localObject = (TokenManager)this.tokenManagers.get(this.grammar.importVocab);
/* 231:    */         
/* 232:308 */         localTokenManager = (TokenManager)((TokenManager)localObject).clone();
/* 233:309 */         localTokenManager.setName(this.grammar.exportVocab);
/* 234:    */         
/* 235:311 */         localTokenManager.setReadOnly(false);
/* 236:312 */         this.grammar.setTokenManager(localTokenManager);
/* 237:313 */         this.tokenManagers.put(this.grammar.exportVocab, localTokenManager);
/* 238:314 */         return;
/* 239:    */       }
/* 240:318 */       localObject = new ImportVocabTokenManager(this.grammar, this.grammar.importVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt, this.grammar.exportVocab, this.tool);
/* 241:    */       
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:323 */       ((ImportVocabTokenManager)localObject).setReadOnly(false);
/* 246:    */       
/* 247:325 */       this.tokenManagers.put(this.grammar.exportVocab, localObject);
/* 248:    */       
/* 249:    */ 
/* 250:328 */       this.grammar.setTokenManager((TokenManager)localObject);
/* 251:331 */       if (!this.tokenManagers.containsKey("*default")) {
/* 252:332 */         this.tokenManagers.put("*default", localObject);
/* 253:    */       }
/* 254:335 */       return;
/* 255:    */     }
/* 256:339 */     if ((this.grammar.exportVocab != null) && (this.grammar.importVocab == null))
/* 257:    */     {
/* 258:341 */       if (this.tokenManagers.containsKey(this.grammar.exportVocab))
/* 259:    */       {
/* 260:343 */         localObject = (TokenManager)this.tokenManagers.get(this.grammar.exportVocab);
/* 261:    */         
/* 262:345 */         this.grammar.setTokenManager((TokenManager)localObject);
/* 263:346 */         return;
/* 264:    */       }
/* 265:350 */       localObject = new SimpleTokenManager(this.grammar.exportVocab, this.tool);
/* 266:351 */       this.grammar.setTokenManager((TokenManager)localObject);
/* 267:    */       
/* 268:353 */       this.tokenManagers.put(this.grammar.exportVocab, localObject);
/* 269:355 */       if (!this.tokenManagers.containsKey("*default")) {
/* 270:356 */         this.tokenManagers.put("*default", localObject);
/* 271:    */       }
/* 272:358 */       return;
/* 273:    */     }
/* 274:362 */     if ((this.grammar.exportVocab != null) && (this.grammar.importVocab != null))
/* 275:    */     {
/* 276:364 */       if (this.grammar.importVocab.equals(this.grammar.exportVocab)) {
/* 277:365 */         this.tool.error("exportVocab of " + this.grammar.exportVocab + " same as importVocab; probably not what you want");
/* 278:    */       }
/* 279:368 */       if (this.tokenManagers.containsKey(this.grammar.importVocab))
/* 280:    */       {
/* 281:371 */         localObject = (TokenManager)this.tokenManagers.get(this.grammar.importVocab);
/* 282:    */         
/* 283:373 */         localTokenManager = (TokenManager)((TokenManager)localObject).clone();
/* 284:374 */         localTokenManager.setName(this.grammar.exportVocab);
/* 285:    */         
/* 286:376 */         localTokenManager.setReadOnly(false);
/* 287:377 */         this.grammar.setTokenManager(localTokenManager);
/* 288:378 */         this.tokenManagers.put(this.grammar.exportVocab, localTokenManager);
/* 289:379 */         return;
/* 290:    */       }
/* 291:382 */       localObject = new ImportVocabTokenManager(this.grammar, this.grammar.importVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt, this.grammar.exportVocab, this.tool);
/* 292:    */       
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:387 */       ((ImportVocabTokenManager)localObject).setReadOnly(false);
/* 297:    */       
/* 298:389 */       this.tokenManagers.put(this.grammar.exportVocab, localObject);
/* 299:    */       
/* 300:391 */       this.grammar.setTokenManager((TokenManager)localObject);
/* 301:394 */       if (!this.tokenManagers.containsKey("*default")) {
/* 302:395 */         this.tokenManagers.put("*default", localObject);
/* 303:    */       }
/* 304:398 */       return;
/* 305:    */     }
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void endRule(String paramString) {}
/* 309:    */   
/* 310:    */   public void endSubRule() {}
/* 311:    */   
/* 312:    */   public void endTree() {}
/* 313:    */   
/* 314:    */   public void hasError() {}
/* 315:    */   
/* 316:    */   public void noASTSubRule() {}
/* 317:    */   
/* 318:    */   public void oneOrMoreSubRule() {}
/* 319:    */   
/* 320:    */   public void optionalSubRule() {}
/* 321:    */   
/* 322:    */   public void setUserExceptions(String paramString) {}
/* 323:    */   
/* 324:    */   public void refAction(Token paramToken) {}
/* 325:    */   
/* 326:    */   public void refArgAction(Token paramToken) {}
/* 327:    */   
/* 328:    */   public void refCharLiteral(Token paramToken1, Token paramToken2, boolean paramBoolean1, int paramInt, boolean paramBoolean2) {}
/* 329:    */   
/* 330:    */   public void refCharRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean) {}
/* 331:    */   
/* 332:    */   public void refElementOption(Token paramToken1, Token paramToken2) {}
/* 333:    */   
/* 334:    */   public void refTokensSpecElementOption(Token paramToken1, Token paramToken2, Token paramToken3) {}
/* 335:    */   
/* 336:    */   public void refExceptionHandler(Token paramToken1, Token paramToken2) {}
/* 337:    */   
/* 338:    */   public void refHeaderAction(Token paramToken1, Token paramToken2)
/* 339:    */   {
/* 340:    */     String str;
/* 341:451 */     if (paramToken1 == null) {
/* 342:452 */       str = "";
/* 343:    */     } else {
/* 344:454 */       str = StringUtils.stripFrontBack(paramToken1.getText(), "\"", "\"");
/* 345:    */     }
/* 346:458 */     if (this.headerActions.containsKey(str)) {
/* 347:459 */       if (str.equals("")) {
/* 348:460 */         this.tool.error(paramToken2.getLine() + ": header action already defined");
/* 349:    */       } else {
/* 350:462 */         this.tool.error(paramToken2.getLine() + ": header action '" + str + "' already defined");
/* 351:    */       }
/* 352:    */     }
/* 353:464 */     this.headerActions.put(str, paramToken2);
/* 354:    */   }
/* 355:    */   
/* 356:    */   public String getHeaderAction(String paramString)
/* 357:    */   {
/* 358:468 */     Token localToken = (Token)this.headerActions.get(paramString);
/* 359:469 */     if (localToken == null) {
/* 360:470 */       return "";
/* 361:    */     }
/* 362:472 */     return localToken.getText();
/* 363:    */   }
/* 364:    */   
/* 365:    */   public int getHeaderActionLine(String paramString)
/* 366:    */   {
/* 367:476 */     Token localToken = (Token)this.headerActions.get(paramString);
/* 368:477 */     if (localToken == null) {
/* 369:478 */       return 0;
/* 370:    */     }
/* 371:480 */     return localToken.getLine();
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void refInitAction(Token paramToken) {}
/* 375:    */   
/* 376:    */   public void refMemberAction(Token paramToken) {}
/* 377:    */   
/* 378:    */   public void refPreambleAction(Token paramToken)
/* 379:    */   {
/* 380:490 */     this.thePreambleAction = paramToken;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void refReturnAction(Token paramToken) {}
/* 384:    */   
/* 385:    */   public void refRule(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, int paramInt)
/* 386:    */   {
/* 387:501 */     String str = paramToken2.getText();
/* 388:503 */     if (paramToken2.type == 24) {
/* 389:505 */       str = CodeGenerator.encodeLexerRuleName(str);
/* 390:    */     }
/* 391:507 */     if (!this.grammar.isDefined(str)) {
/* 392:508 */       this.grammar.define(new RuleSymbol(str));
/* 393:    */     }
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void refSemPred(Token paramToken) {}
/* 397:    */   
/* 398:    */   public void refStringLiteral(Token paramToken1, Token paramToken2, int paramInt, boolean paramBoolean)
/* 399:    */   {
/* 400:519 */     _refStringLiteral(paramToken1, paramToken2, paramInt, paramBoolean);
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void refToken(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
/* 404:    */   {
/* 405:525 */     _refToken(paramToken1, paramToken2, paramToken3, paramToken4, paramBoolean1, paramInt, paramBoolean2);
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void refTokenRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean)
/* 409:    */   {
/* 410:531 */     if (paramToken1.getText().charAt(0) == '"') {
/* 411:532 */       refStringLiteral(paramToken1, null, 1, paramBoolean);
/* 412:    */     } else {
/* 413:535 */       _refToken(null, paramToken1, null, null, false, 1, paramBoolean);
/* 414:    */     }
/* 415:537 */     if (paramToken2.getText().charAt(0) == '"') {
/* 416:538 */       _refStringLiteral(paramToken2, null, 1, paramBoolean);
/* 417:    */     } else {
/* 418:541 */       _refToken(null, paramToken2, null, null, false, 1, paramBoolean);
/* 419:    */     }
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void refTreeSpecifier(Token paramToken) {}
/* 423:    */   
/* 424:    */   public void refWildcard(Token paramToken1, Token paramToken2, int paramInt) {}
/* 425:    */   
/* 426:    */   public void reset()
/* 427:    */   {
/* 428:553 */     this.grammar = null;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setArgOfRuleRef(Token paramToken) {}
/* 432:    */   
/* 433:    */   public void setCharVocabulary(BitSet paramBitSet)
/* 434:    */   {
/* 435:562 */     ((LexerGrammar)this.grammar).setCharVocabulary(paramBitSet);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void setFileOption(Token paramToken1, Token paramToken2, String paramString)
/* 439:    */   {
/* 440:571 */     if (paramToken1.getText().equals("language"))
/* 441:    */     {
/* 442:572 */       if (paramToken2.getType() == 6) {
/* 443:573 */         this.language = StringUtils.stripBack(StringUtils.stripFront(paramToken2.getText(), '"'), '"');
/* 444:575 */       } else if ((paramToken2.getType() == 24) || (paramToken2.getType() == 41)) {
/* 445:576 */         this.language = paramToken2.getText();
/* 446:    */       } else {
/* 447:579 */         this.tool.error("language option must be string or identifier", paramString, paramToken2.getLine(), paramToken2.getColumn());
/* 448:    */       }
/* 449:    */     }
/* 450:582 */     else if (paramToken1.getText().equals("mangleLiteralPrefix"))
/* 451:    */     {
/* 452:583 */       if (paramToken2.getType() == 6) {
/* 453:584 */         this.tool.literalsPrefix = StringUtils.stripFrontBack(paramToken2.getText(), "\"", "\"");
/* 454:    */       } else {
/* 455:587 */         this.tool.error("mangleLiteralPrefix option must be string", paramString, paramToken2.getLine(), paramToken2.getColumn());
/* 456:    */       }
/* 457:    */     }
/* 458:590 */     else if (paramToken1.getText().equals("upperCaseMangledLiterals"))
/* 459:    */     {
/* 460:591 */       if (paramToken2.getText().equals("true")) {
/* 461:592 */         this.tool.upperCaseMangledLiterals = true;
/* 462:594 */       } else if (paramToken2.getText().equals("false")) {
/* 463:595 */         this.tool.upperCaseMangledLiterals = false;
/* 464:    */       } else {
/* 465:598 */         this.grammar.antlrTool.error("Value for upperCaseMangledLiterals must be true or false", paramString, paramToken1.getLine(), paramToken1.getColumn());
/* 466:    */       }
/* 467:    */     }
/* 468:601 */     else if ((paramToken1.getText().equals("namespaceStd")) || (paramToken1.getText().equals("namespaceAntlr")) || (paramToken1.getText().equals("genHashLines")))
/* 469:    */     {
/* 470:605 */       if (!this.language.equals("Cpp"))
/* 471:    */       {
/* 472:606 */         this.tool.error(paramToken1.getText() + " option only valid for C++", paramString, paramToken1.getLine(), paramToken1.getColumn());
/* 473:    */       }
/* 474:609 */       else if (paramToken1.getText().equals("noConstructors"))
/* 475:    */       {
/* 476:610 */         if ((!paramToken2.getText().equals("true")) && (!paramToken2.getText().equals("false"))) {
/* 477:611 */           this.tool.error("noConstructors option must be true or false", paramString, paramToken2.getLine(), paramToken2.getColumn());
/* 478:    */         }
/* 479:612 */         this.tool.noConstructors = paramToken2.getText().equals("true");
/* 480:    */       }
/* 481:613 */       else if (paramToken1.getText().equals("genHashLines"))
/* 482:    */       {
/* 483:614 */         if ((!paramToken2.getText().equals("true")) && (!paramToken2.getText().equals("false"))) {
/* 484:615 */           this.tool.error("genHashLines option must be true or false", paramString, paramToken2.getLine(), paramToken2.getColumn());
/* 485:    */         }
/* 486:616 */         this.tool.genHashLines = paramToken2.getText().equals("true");
/* 487:    */       }
/* 488:619 */       else if (paramToken2.getType() != 6)
/* 489:    */       {
/* 490:620 */         this.tool.error(paramToken1.getText() + " option must be a string", paramString, paramToken2.getLine(), paramToken2.getColumn());
/* 491:    */       }
/* 492:623 */       else if (paramToken1.getText().equals("namespaceStd"))
/* 493:    */       {
/* 494:624 */         this.tool.namespaceStd = paramToken2.getText();
/* 495:    */       }
/* 496:625 */       else if (paramToken1.getText().equals("namespaceAntlr"))
/* 497:    */       {
/* 498:626 */         this.tool.namespaceAntlr = paramToken2.getText();
/* 499:    */       }
/* 500:    */     }
/* 501:631 */     else if (paramToken1.getText().equals("namespace"))
/* 502:    */     {
/* 503:632 */       if ((!this.language.equals("Cpp")) && (!this.language.equals("CSharp"))) {
/* 504:634 */         this.tool.error(paramToken1.getText() + " option only valid for C++ and C# (a.k.a CSharp)", paramString, paramToken1.getLine(), paramToken1.getColumn());
/* 505:638 */       } else if (paramToken2.getType() != 6) {
/* 506:640 */         this.tool.error(paramToken1.getText() + " option must be a string", paramString, paramToken2.getLine(), paramToken2.getColumn());
/* 507:643 */       } else if (paramToken1.getText().equals("namespace")) {
/* 508:644 */         this.tool.setNameSpace(paramToken2.getText());
/* 509:    */       }
/* 510:    */     }
/* 511:    */     else {
/* 512:649 */       this.tool.error("Invalid file-level option: " + paramToken1.getText(), paramString, paramToken1.getLine(), paramToken2.getColumn());
/* 513:    */     }
/* 514:    */   }
/* 515:    */   
/* 516:    */   public void setGrammarOption(Token paramToken1, Token paramToken2)
/* 517:    */   {
/* 518:659 */     if ((paramToken1.getText().equals("tokdef")) || (paramToken1.getText().equals("tokenVocabulary"))) {
/* 519:660 */       this.tool.error("tokdef/tokenVocabulary options are invalid >= ANTLR 2.6.0.\n  Use importVocab/exportVocab instead.  Please see the documentation.\n  The previous options were so heinous that Terence changed the whole\n  vocabulary mechanism; it was better to change the names rather than\n  subtly change the functionality of the known options.  Sorry!", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 520:666 */     } else if ((paramToken1.getText().equals("literal")) && ((this.grammar instanceof LexerGrammar))) {
/* 521:668 */       this.tool.error("the literal option is invalid >= ANTLR 2.6.0.\n  Use the \"tokens {...}\" mechanism instead.", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 522:672 */     } else if (paramToken1.getText().equals("exportVocab"))
/* 523:    */     {
/* 524:674 */       if ((paramToken2.getType() == 41) || (paramToken2.getType() == 24)) {
/* 525:675 */         this.grammar.exportVocab = paramToken2.getText();
/* 526:    */       } else {
/* 527:678 */         this.tool.error("exportVocab must be an identifier", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 528:    */       }
/* 529:    */     }
/* 530:681 */     else if (paramToken1.getText().equals("importVocab"))
/* 531:    */     {
/* 532:682 */       if ((paramToken2.getType() == 41) || (paramToken2.getType() == 24)) {
/* 533:683 */         this.grammar.importVocab = paramToken2.getText();
/* 534:    */       } else {
/* 535:686 */         this.tool.error("importVocab must be an identifier", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 536:    */       }
/* 537:    */     }
/* 538:689 */     else if (paramToken1.getText().equals("k"))
/* 539:    */     {
/* 540:690 */       if (((this.grammar instanceof TreeWalkerGrammar)) && (!paramToken2.getText().equals("1"))) {
/* 541:692 */         this.tool.error("Treewalkers only support k=1", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/* 542:    */       } else {
/* 543:695 */         this.grammar.setOption(paramToken1.getText(), paramToken2);
/* 544:    */       }
/* 545:    */     }
/* 546:    */     else {
/* 547:700 */       this.grammar.setOption(paramToken1.getText(), paramToken2);
/* 548:    */     }
/* 549:    */   }
/* 550:    */   
/* 551:    */   public void setRuleOption(Token paramToken1, Token paramToken2) {}
/* 552:    */   
/* 553:    */   public void setSubruleOption(Token paramToken1, Token paramToken2) {}
/* 554:    */   
/* 555:    */   public void startLexer(String paramString1, Token paramToken, String paramString2, String paramString3)
/* 556:    */   {
/* 557:712 */     if (this.numLexers > 0) {
/* 558:713 */       this.tool.panic("You may only have one lexer per grammar file: class " + paramToken.getText());
/* 559:    */     }
/* 560:715 */     this.numLexers += 1;
/* 561:716 */     reset();
/* 562:    */     
/* 563:    */ 
/* 564:719 */     Grammar localGrammar = (Grammar)this.grammars.get(paramToken);
/* 565:720 */     if (localGrammar != null)
/* 566:    */     {
/* 567:721 */       if (!(localGrammar instanceof LexerGrammar)) {
/* 568:722 */         this.tool.panic("'" + paramToken.getText() + "' is already defined as a non-lexer");
/* 569:    */       } else {
/* 570:725 */         this.tool.panic("Lexer '" + paramToken.getText() + "' is already defined");
/* 571:    */       }
/* 572:    */     }
/* 573:    */     else
/* 574:    */     {
/* 575:730 */       LexerGrammar localLexerGrammar = new LexerGrammar(paramToken.getText(), this.tool, paramString2);
/* 576:731 */       localLexerGrammar.comment = paramString3;
/* 577:732 */       localLexerGrammar.processArguments(this.args);
/* 578:733 */       localLexerGrammar.setFilename(paramString1);
/* 579:734 */       this.grammars.put(localLexerGrammar.getClassName(), localLexerGrammar);
/* 580:    */       
/* 581:736 */       localLexerGrammar.preambleAction = this.thePreambleAction;
/* 582:737 */       this.thePreambleAction = new CommonToken(0, "");
/* 583:    */       
/* 584:739 */       this.grammar = localLexerGrammar;
/* 585:    */     }
/* 586:    */   }
/* 587:    */   
/* 588:    */   public void startParser(String paramString1, Token paramToken, String paramString2, String paramString3)
/* 589:    */   {
/* 590:745 */     if (this.numParsers > 0) {
/* 591:746 */       this.tool.panic("You may only have one parser per grammar file: class " + paramToken.getText());
/* 592:    */     }
/* 593:748 */     this.numParsers += 1;
/* 594:749 */     reset();
/* 595:    */     
/* 596:    */ 
/* 597:752 */     Grammar localGrammar = (Grammar)this.grammars.get(paramToken);
/* 598:753 */     if (localGrammar != null)
/* 599:    */     {
/* 600:754 */       if (!(localGrammar instanceof ParserGrammar)) {
/* 601:755 */         this.tool.panic("'" + paramToken.getText() + "' is already defined as a non-parser");
/* 602:    */       } else {
/* 603:758 */         this.tool.panic("Parser '" + paramToken.getText() + "' is already defined");
/* 604:    */       }
/* 605:    */     }
/* 606:    */     else
/* 607:    */     {
/* 608:763 */       this.grammar = new ParserGrammar(paramToken.getText(), this.tool, paramString2);
/* 609:764 */       this.grammar.comment = paramString3;
/* 610:765 */       this.grammar.processArguments(this.args);
/* 611:766 */       this.grammar.setFilename(paramString1);
/* 612:767 */       this.grammars.put(this.grammar.getClassName(), this.grammar);
/* 613:    */       
/* 614:769 */       this.grammar.preambleAction = this.thePreambleAction;
/* 615:770 */       this.thePreambleAction = new CommonToken(0, "");
/* 616:    */     }
/* 617:    */   }
/* 618:    */   
/* 619:    */   public void startTreeWalker(String paramString1, Token paramToken, String paramString2, String paramString3)
/* 620:    */   {
/* 621:776 */     if (this.numTreeParsers > 0) {
/* 622:777 */       this.tool.panic("You may only have one tree parser per grammar file: class " + paramToken.getText());
/* 623:    */     }
/* 624:779 */     this.numTreeParsers += 1;
/* 625:780 */     reset();
/* 626:    */     
/* 627:    */ 
/* 628:783 */     Grammar localGrammar = (Grammar)this.grammars.get(paramToken);
/* 629:784 */     if (localGrammar != null)
/* 630:    */     {
/* 631:785 */       if (!(localGrammar instanceof TreeWalkerGrammar)) {
/* 632:786 */         this.tool.panic("'" + paramToken.getText() + "' is already defined as a non-tree-walker");
/* 633:    */       } else {
/* 634:789 */         this.tool.panic("Tree-walker '" + paramToken.getText() + "' is already defined");
/* 635:    */       }
/* 636:    */     }
/* 637:    */     else
/* 638:    */     {
/* 639:794 */       this.grammar = new TreeWalkerGrammar(paramToken.getText(), this.tool, paramString2);
/* 640:795 */       this.grammar.comment = paramString3;
/* 641:796 */       this.grammar.processArguments(this.args);
/* 642:797 */       this.grammar.setFilename(paramString1);
/* 643:798 */       this.grammars.put(this.grammar.getClassName(), this.grammar);
/* 644:    */       
/* 645:800 */       this.grammar.preambleAction = this.thePreambleAction;
/* 646:801 */       this.thePreambleAction = new CommonToken(0, "");
/* 647:    */     }
/* 648:    */   }
/* 649:    */   
/* 650:    */   public void synPred() {}
/* 651:    */   
/* 652:    */   public void zeroOrMoreSubRule() {}
/* 653:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.DefineGrammarSymbols
 * JD-Core Version:    0.7.0.1
 */