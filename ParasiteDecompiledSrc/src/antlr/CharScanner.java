/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ 
/*   7:    */ public abstract class CharScanner
/*   8:    */   implements TokenStream
/*   9:    */ {
/*  10:    */   static final char NO_CHAR = '\000';
/*  11:    */   public static final char EOF_CHAR = 'èøø';
/*  12:    */   protected ANTLRStringBuffer text;
/*  13: 19 */   protected boolean saveConsumedInput = true;
/*  14:    */   protected Class tokenObjectClass;
/*  15: 21 */   protected boolean caseSensitive = true;
/*  16: 22 */   protected boolean caseSensitiveLiterals = true;
/*  17:    */   protected Hashtable literals;
/*  18: 28 */   protected int tabsize = 8;
/*  19: 30 */   protected Token _returnToken = null;
/*  20:    */   protected ANTLRHashString hashString;
/*  21:    */   protected LexerSharedInputState inputState;
/*  22: 41 */   protected boolean commitToPath = false;
/*  23: 44 */   protected int traceDepth = 0;
/*  24:    */   
/*  25:    */   public CharScanner()
/*  26:    */   {
/*  27: 47 */     this.text = new ANTLRStringBuffer();
/*  28: 48 */     this.hashString = new ANTLRHashString(this);
/*  29: 49 */     setTokenObjectClass("antlr.CommonToken");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public CharScanner(InputBuffer paramInputBuffer)
/*  33:    */   {
/*  34: 53 */     this();
/*  35: 54 */     this.inputState = new LexerSharedInputState(paramInputBuffer);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public CharScanner(LexerSharedInputState paramLexerSharedInputState)
/*  39:    */   {
/*  40: 58 */     this();
/*  41: 59 */     this.inputState = paramLexerSharedInputState;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void append(char paramChar)
/*  45:    */   {
/*  46: 63 */     if (this.saveConsumedInput) {
/*  47: 64 */       this.text.append(paramChar);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void append(String paramString)
/*  52:    */   {
/*  53: 69 */     if (this.saveConsumedInput) {
/*  54: 70 */       this.text.append(paramString);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void commit()
/*  59:    */   {
/*  60: 75 */     this.inputState.input.commit();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void consume()
/*  64:    */     throws CharStreamException
/*  65:    */   {
/*  66: 79 */     if (this.inputState.guessing == 0)
/*  67:    */     {
/*  68: 80 */       char c = LA(1);
/*  69: 81 */       if (this.caseSensitive) {
/*  70: 82 */         append(c);
/*  71:    */       } else {
/*  72: 87 */         append(this.inputState.input.LA(1));
/*  73:    */       }
/*  74: 89 */       if (c == '\t') {
/*  75: 90 */         tab();
/*  76:    */       } else {
/*  77: 93 */         this.inputState.column += 1;
/*  78:    */       }
/*  79:    */     }
/*  80: 96 */     this.inputState.input.consume();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void consumeUntil(int paramInt)
/*  84:    */     throws CharStreamException
/*  85:    */   {
/*  86:101 */     while ((LA(1) != 65535) && (LA(1) != paramInt)) {
/*  87:102 */       consume();
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void consumeUntil(BitSet paramBitSet)
/*  92:    */     throws CharStreamException
/*  93:    */   {
/*  94:108 */     while ((LA(1) != 65535) && (!paramBitSet.member(LA(1)))) {
/*  95:109 */       consume();
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean getCaseSensitive()
/* 100:    */   {
/* 101:114 */     return this.caseSensitive;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public final boolean getCaseSensitiveLiterals()
/* 105:    */   {
/* 106:118 */     return this.caseSensitiveLiterals;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getColumn()
/* 110:    */   {
/* 111:122 */     return this.inputState.column;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setColumn(int paramInt)
/* 115:    */   {
/* 116:126 */     this.inputState.column = paramInt;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean getCommitToPath()
/* 120:    */   {
/* 121:130 */     return this.commitToPath;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getFilename()
/* 125:    */   {
/* 126:134 */     return this.inputState.filename;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public InputBuffer getInputBuffer()
/* 130:    */   {
/* 131:138 */     return this.inputState.input;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public LexerSharedInputState getInputState()
/* 135:    */   {
/* 136:142 */     return this.inputState;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setInputState(LexerSharedInputState paramLexerSharedInputState)
/* 140:    */   {
/* 141:146 */     this.inputState = paramLexerSharedInputState;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int getLine()
/* 145:    */   {
/* 146:150 */     return this.inputState.line;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getText()
/* 150:    */   {
/* 151:155 */     return this.text.toString();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Token getTokenObject()
/* 155:    */   {
/* 156:159 */     return this._returnToken;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public char LA(int paramInt)
/* 160:    */     throws CharStreamException
/* 161:    */   {
/* 162:163 */     if (this.caseSensitive) {
/* 163:164 */       return this.inputState.input.LA(paramInt);
/* 164:    */     }
/* 165:167 */     return toLower(this.inputState.input.LA(paramInt));
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected Token makeToken(int paramInt)
/* 169:    */   {
/* 170:    */     try
/* 171:    */     {
/* 172:173 */       Token localToken = (Token)this.tokenObjectClass.newInstance();
/* 173:174 */       localToken.setType(paramInt);
/* 174:175 */       localToken.setColumn(this.inputState.tokenStartColumn);
/* 175:176 */       localToken.setLine(this.inputState.tokenStartLine);
/* 176:    */       
/* 177:178 */       return localToken;
/* 178:    */     }
/* 179:    */     catch (InstantiationException localInstantiationException)
/* 180:    */     {
/* 181:181 */       panic("can't instantiate token: " + this.tokenObjectClass);
/* 182:    */     }
/* 183:    */     catch (IllegalAccessException localIllegalAccessException)
/* 184:    */     {
/* 185:184 */       panic("Token class is not accessible" + this.tokenObjectClass);
/* 186:    */     }
/* 187:186 */     return Token.badToken;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int mark()
/* 191:    */   {
/* 192:190 */     return this.inputState.input.mark();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void match(char paramChar)
/* 196:    */     throws MismatchedCharException, CharStreamException
/* 197:    */   {
/* 198:194 */     if (LA(1) != paramChar) {
/* 199:195 */       throw new MismatchedCharException(LA(1), paramChar, false, this);
/* 200:    */     }
/* 201:197 */     consume();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void match(BitSet paramBitSet)
/* 205:    */     throws MismatchedCharException, CharStreamException
/* 206:    */   {
/* 207:201 */     if (!paramBitSet.member(LA(1))) {
/* 208:202 */       throw new MismatchedCharException(LA(1), paramBitSet, false, this);
/* 209:    */     }
/* 210:205 */     consume();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void match(String paramString)
/* 214:    */     throws MismatchedCharException, CharStreamException
/* 215:    */   {
/* 216:210 */     int i = paramString.length();
/* 217:211 */     for (int j = 0; j < i; j++)
/* 218:    */     {
/* 219:212 */       if (LA(1) != paramString.charAt(j)) {
/* 220:213 */         throw new MismatchedCharException(LA(1), paramString.charAt(j), false, this);
/* 221:    */       }
/* 222:215 */       consume();
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void matchNot(char paramChar)
/* 227:    */     throws MismatchedCharException, CharStreamException
/* 228:    */   {
/* 229:220 */     if (LA(1) == paramChar) {
/* 230:221 */       throw new MismatchedCharException(LA(1), paramChar, true, this);
/* 231:    */     }
/* 232:223 */     consume();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void matchRange(char paramChar1, char paramChar2)
/* 236:    */     throws MismatchedCharException, CharStreamException
/* 237:    */   {
/* 238:227 */     if ((LA(1) < paramChar1) || (LA(1) > paramChar2)) {
/* 239:227 */       throw new MismatchedCharException(LA(1), paramChar1, paramChar2, false, this);
/* 240:    */     }
/* 241:228 */     consume();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void newline()
/* 245:    */   {
/* 246:232 */     this.inputState.line += 1;
/* 247:233 */     this.inputState.column = 1;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void tab()
/* 251:    */   {
/* 252:240 */     int i = getColumn();
/* 253:241 */     int j = ((i - 1) / this.tabsize + 1) * this.tabsize + 1;
/* 254:242 */     setColumn(j);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setTabSize(int paramInt)
/* 258:    */   {
/* 259:246 */     this.tabsize = paramInt;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public int getTabSize()
/* 263:    */   {
/* 264:250 */     return this.tabsize;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void panic()
/* 268:    */   {
/* 269:256 */     System.err.println("CharScanner: panic");
/* 270:257 */     Utils.error("");
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void panic(String paramString)
/* 274:    */   {
/* 275:271 */     System.err.println("CharScanner; panic: " + paramString);
/* 276:272 */     Utils.error(paramString);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void reportError(RecognitionException paramRecognitionException)
/* 280:    */   {
/* 281:277 */     System.err.println(paramRecognitionException);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void reportError(String paramString)
/* 285:    */   {
/* 286:282 */     if (getFilename() == null) {
/* 287:283 */       System.err.println("error: " + paramString);
/* 288:    */     } else {
/* 289:286 */       System.err.println(getFilename() + ": error: " + paramString);
/* 290:    */     }
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void reportWarning(String paramString)
/* 294:    */   {
/* 295:292 */     if (getFilename() == null) {
/* 296:293 */       System.err.println("warning: " + paramString);
/* 297:    */     } else {
/* 298:296 */       System.err.println(getFilename() + ": warning: " + paramString);
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void resetText()
/* 303:    */   {
/* 304:301 */     this.text.setLength(0);
/* 305:302 */     this.inputState.tokenStartColumn = this.inputState.column;
/* 306:303 */     this.inputState.tokenStartLine = this.inputState.line;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void rewind(int paramInt)
/* 310:    */   {
/* 311:307 */     this.inputState.input.rewind(paramInt);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setCaseSensitive(boolean paramBoolean)
/* 315:    */   {
/* 316:313 */     this.caseSensitive = paramBoolean;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setCommitToPath(boolean paramBoolean)
/* 320:    */   {
/* 321:317 */     this.commitToPath = paramBoolean;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setFilename(String paramString)
/* 325:    */   {
/* 326:321 */     this.inputState.filename = paramString;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setLine(int paramInt)
/* 330:    */   {
/* 331:325 */     this.inputState.line = paramInt;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setText(String paramString)
/* 335:    */   {
/* 336:329 */     resetText();
/* 337:330 */     this.text.append(paramString);
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setTokenObjectClass(String paramString)
/* 341:    */   {
/* 342:    */     try
/* 343:    */     {
/* 344:335 */       this.tokenObjectClass = Utils.loadClass(paramString);
/* 345:    */     }
/* 346:    */     catch (ClassNotFoundException localClassNotFoundException)
/* 347:    */     {
/* 348:338 */       panic("ClassNotFoundException: " + paramString);
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   public int testLiteralsTable(int paramInt)
/* 353:    */   {
/* 354:345 */     this.hashString.setBuffer(this.text.getBuffer(), this.text.length());
/* 355:346 */     Integer localInteger = (Integer)this.literals.get(this.hashString);
/* 356:347 */     if (localInteger != null) {
/* 357:348 */       paramInt = localInteger.intValue();
/* 358:    */     }
/* 359:350 */     return paramInt;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public int testLiteralsTable(String paramString, int paramInt)
/* 363:    */   {
/* 364:359 */     ANTLRHashString localANTLRHashString = new ANTLRHashString(paramString, this);
/* 365:360 */     Integer localInteger = (Integer)this.literals.get(localANTLRHashString);
/* 366:361 */     if (localInteger != null) {
/* 367:362 */       paramInt = localInteger.intValue();
/* 368:    */     }
/* 369:364 */     return paramInt;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public char toLower(char paramChar)
/* 373:    */   {
/* 374:369 */     return Character.toLowerCase(paramChar);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void traceIndent()
/* 378:    */   {
/* 379:373 */     for (int i = 0; i < this.traceDepth; i++) {
/* 380:374 */       System.out.print(" ");
/* 381:    */     }
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void traceIn(String paramString)
/* 385:    */     throws CharStreamException
/* 386:    */   {
/* 387:378 */     this.traceDepth += 1;
/* 388:379 */     traceIndent();
/* 389:380 */     System.out.println("> lexer " + paramString + "; c==" + LA(1));
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void traceOut(String paramString)
/* 393:    */     throws CharStreamException
/* 394:    */   {
/* 395:384 */     traceIndent();
/* 396:385 */     System.out.println("< lexer " + paramString + "; c==" + LA(1));
/* 397:386 */     this.traceDepth -= 1;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void uponEOF()
/* 401:    */     throws TokenStreamException, CharStreamException
/* 402:    */   {}
/* 403:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CharScanner
 * JD-Core Version:    0.7.0.1
 */