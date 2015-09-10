/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import antlr.collections.impl.BitSet;
/*   5:    */ import antlr.debug.MessageListener;
/*   6:    */ import antlr.debug.ParserListener;
/*   7:    */ import antlr.debug.ParserMatchListener;
/*   8:    */ import antlr.debug.ParserTokenListener;
/*   9:    */ import antlr.debug.SemanticPredicateListener;
/*  10:    */ import antlr.debug.SyntacticPredicateListener;
/*  11:    */ import antlr.debug.TraceListener;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.Hashtable;
/*  14:    */ 
/*  15:    */ public abstract class Parser
/*  16:    */ {
/*  17:    */   protected ParserSharedInputState inputState;
/*  18:    */   protected String[] tokenNames;
/*  19:    */   protected AST returnAST;
/*  20: 74 */   protected ASTFactory astFactory = null;
/*  21: 79 */   protected Hashtable tokenTypeToASTClassMap = null;
/*  22: 81 */   private boolean ignoreInvalidDebugCalls = false;
/*  23: 84 */   protected int traceDepth = 0;
/*  24:    */   
/*  25:    */   public Parser()
/*  26:    */   {
/*  27: 87 */     this(new ParserSharedInputState());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Parser(ParserSharedInputState paramParserSharedInputState)
/*  31:    */   {
/*  32: 91 */     this.inputState = paramParserSharedInputState;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Hashtable getTokenTypeToASTClassMap()
/*  36:    */   {
/*  37: 99 */     return this.tokenTypeToASTClassMap;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void addMessageListener(MessageListener paramMessageListener)
/*  41:    */   {
/*  42:103 */     if (!this.ignoreInvalidDebugCalls) {
/*  43:104 */       throw new IllegalArgumentException("addMessageListener() is only valid if parser built for debugging");
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void addParserListener(ParserListener paramParserListener)
/*  48:    */   {
/*  49:108 */     if (!this.ignoreInvalidDebugCalls) {
/*  50:109 */       throw new IllegalArgumentException("addParserListener() is only valid if parser built for debugging");
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addParserMatchListener(ParserMatchListener paramParserMatchListener)
/*  55:    */   {
/*  56:113 */     if (!this.ignoreInvalidDebugCalls) {
/*  57:114 */       throw new IllegalArgumentException("addParserMatchListener() is only valid if parser built for debugging");
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void addParserTokenListener(ParserTokenListener paramParserTokenListener)
/*  62:    */   {
/*  63:118 */     if (!this.ignoreInvalidDebugCalls) {
/*  64:119 */       throw new IllegalArgumentException("addParserTokenListener() is only valid if parser built for debugging");
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void addSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener)
/*  69:    */   {
/*  70:123 */     if (!this.ignoreInvalidDebugCalls) {
/*  71:124 */       throw new IllegalArgumentException("addSemanticPredicateListener() is only valid if parser built for debugging");
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void addSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener)
/*  76:    */   {
/*  77:128 */     if (!this.ignoreInvalidDebugCalls) {
/*  78:129 */       throw new IllegalArgumentException("addSyntacticPredicateListener() is only valid if parser built for debugging");
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void addTraceListener(TraceListener paramTraceListener)
/*  83:    */   {
/*  84:133 */     if (!this.ignoreInvalidDebugCalls) {
/*  85:134 */       throw new IllegalArgumentException("addTraceListener() is only valid if parser built for debugging");
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public abstract void consume()
/*  90:    */     throws TokenStreamException;
/*  91:    */   
/*  92:    */   public void consumeUntil(int paramInt)
/*  93:    */     throws TokenStreamException
/*  94:    */   {
/*  95:142 */     while ((LA(1) != 1) && (LA(1) != paramInt)) {
/*  96:143 */       consume();
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void consumeUntil(BitSet paramBitSet)
/* 101:    */     throws TokenStreamException
/* 102:    */   {
/* 103:149 */     while ((LA(1) != 1) && (!paramBitSet.member(LA(1)))) {
/* 104:150 */       consume();
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void defaultDebuggingSetup(TokenStream paramTokenStream, TokenBuffer paramTokenBuffer) {}
/* 109:    */   
/* 110:    */   public AST getAST()
/* 111:    */   {
/* 112:160 */     return this.returnAST;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public ASTFactory getASTFactory()
/* 116:    */   {
/* 117:164 */     return this.astFactory;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getFilename()
/* 121:    */   {
/* 122:168 */     return this.inputState.filename;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public ParserSharedInputState getInputState()
/* 126:    */   {
/* 127:172 */     return this.inputState;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setInputState(ParserSharedInputState paramParserSharedInputState)
/* 131:    */   {
/* 132:176 */     this.inputState = paramParserSharedInputState;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getTokenName(int paramInt)
/* 136:    */   {
/* 137:180 */     return this.tokenNames[paramInt];
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String[] getTokenNames()
/* 141:    */   {
/* 142:184 */     return this.tokenNames;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isDebugMode()
/* 146:    */   {
/* 147:188 */     return false;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public abstract int LA(int paramInt)
/* 151:    */     throws TokenStreamException;
/* 152:    */   
/* 153:    */   public abstract Token LT(int paramInt)
/* 154:    */     throws TokenStreamException;
/* 155:    */   
/* 156:    */   public int mark()
/* 157:    */   {
/* 158:202 */     return this.inputState.input.mark();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void match(int paramInt)
/* 162:    */     throws MismatchedTokenException, TokenStreamException
/* 163:    */   {
/* 164:210 */     if (LA(1) != paramInt) {
/* 165:211 */       throw new MismatchedTokenException(this.tokenNames, LT(1), paramInt, false, getFilename());
/* 166:    */     }
/* 167:214 */     consume();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void match(BitSet paramBitSet)
/* 171:    */     throws MismatchedTokenException, TokenStreamException
/* 172:    */   {
/* 173:222 */     if (!paramBitSet.member(LA(1))) {
/* 174:223 */       throw new MismatchedTokenException(this.tokenNames, LT(1), paramBitSet, false, getFilename());
/* 175:    */     }
/* 176:226 */     consume();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void matchNot(int paramInt)
/* 180:    */     throws MismatchedTokenException, TokenStreamException
/* 181:    */   {
/* 182:230 */     if (LA(1) == paramInt) {
/* 183:232 */       throw new MismatchedTokenException(this.tokenNames, LT(1), paramInt, true, getFilename());
/* 184:    */     }
/* 185:235 */     consume();
/* 186:    */   }
/* 187:    */   
/* 188:    */   /**
/* 189:    */    * @deprecated
/* 190:    */    */
/* 191:    */   public static void panic()
/* 192:    */   {
/* 193:245 */     System.err.println("Parser: panic");
/* 194:246 */     System.exit(1);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void removeMessageListener(MessageListener paramMessageListener)
/* 198:    */   {
/* 199:250 */     if (!this.ignoreInvalidDebugCalls) {
/* 200:251 */       throw new RuntimeException("removeMessageListener() is only valid if parser built for debugging");
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void removeParserListener(ParserListener paramParserListener)
/* 205:    */   {
/* 206:255 */     if (!this.ignoreInvalidDebugCalls) {
/* 207:256 */       throw new RuntimeException("removeParserListener() is only valid if parser built for debugging");
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void removeParserMatchListener(ParserMatchListener paramParserMatchListener)
/* 212:    */   {
/* 213:260 */     if (!this.ignoreInvalidDebugCalls) {
/* 214:261 */       throw new RuntimeException("removeParserMatchListener() is only valid if parser built for debugging");
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void removeParserTokenListener(ParserTokenListener paramParserTokenListener)
/* 219:    */   {
/* 220:265 */     if (!this.ignoreInvalidDebugCalls) {
/* 221:266 */       throw new RuntimeException("removeParserTokenListener() is only valid if parser built for debugging");
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void removeSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener)
/* 226:    */   {
/* 227:270 */     if (!this.ignoreInvalidDebugCalls) {
/* 228:271 */       throw new IllegalArgumentException("removeSemanticPredicateListener() is only valid if parser built for debugging");
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void removeSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener)
/* 233:    */   {
/* 234:275 */     if (!this.ignoreInvalidDebugCalls) {
/* 235:276 */       throw new IllegalArgumentException("removeSyntacticPredicateListener() is only valid if parser built for debugging");
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void removeTraceListener(TraceListener paramTraceListener)
/* 240:    */   {
/* 241:280 */     if (!this.ignoreInvalidDebugCalls) {
/* 242:281 */       throw new RuntimeException("removeTraceListener() is only valid if parser built for debugging");
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void reportError(RecognitionException paramRecognitionException)
/* 247:    */   {
/* 248:286 */     System.err.println(paramRecognitionException);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void reportError(String paramString)
/* 252:    */   {
/* 253:291 */     if (getFilename() == null) {
/* 254:292 */       System.err.println("error: " + paramString);
/* 255:    */     } else {
/* 256:295 */       System.err.println(getFilename() + ": error: " + paramString);
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void reportWarning(String paramString)
/* 261:    */   {
/* 262:301 */     if (getFilename() == null) {
/* 263:302 */       System.err.println("warning: " + paramString);
/* 264:    */     } else {
/* 265:305 */       System.err.println(getFilename() + ": warning: " + paramString);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void recover(RecognitionException paramRecognitionException, BitSet paramBitSet)
/* 270:    */     throws TokenStreamException
/* 271:    */   {
/* 272:311 */     consume();
/* 273:312 */     consumeUntil(paramBitSet);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void rewind(int paramInt)
/* 277:    */   {
/* 278:316 */     this.inputState.input.rewind(paramInt);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setASTFactory(ASTFactory paramASTFactory)
/* 282:    */   {
/* 283:324 */     this.astFactory = paramASTFactory;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setASTNodeClass(String paramString)
/* 287:    */   {
/* 288:328 */     this.astFactory.setASTNodeType(paramString);
/* 289:    */   }
/* 290:    */   
/* 291:    */   /**
/* 292:    */    * @deprecated
/* 293:    */    */
/* 294:    */   public void setASTNodeType(String paramString)
/* 295:    */   {
/* 296:336 */     setASTNodeClass(paramString);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setDebugMode(boolean paramBoolean)
/* 300:    */   {
/* 301:340 */     if (!this.ignoreInvalidDebugCalls) {
/* 302:341 */       throw new RuntimeException("setDebugMode() only valid if parser built for debugging");
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setFilename(String paramString)
/* 307:    */   {
/* 308:345 */     this.inputState.filename = paramString;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setIgnoreInvalidDebugCalls(boolean paramBoolean)
/* 312:    */   {
/* 313:349 */     this.ignoreInvalidDebugCalls = paramBoolean;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setTokenBuffer(TokenBuffer paramTokenBuffer)
/* 317:    */   {
/* 318:354 */     this.inputState.input = paramTokenBuffer;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void traceIndent()
/* 322:    */   {
/* 323:358 */     for (int i = 0; i < this.traceDepth; i++) {
/* 324:359 */       System.out.print(" ");
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void traceIn(String paramString)
/* 329:    */     throws TokenStreamException
/* 330:    */   {
/* 331:363 */     this.traceDepth += 1;
/* 332:364 */     traceIndent();
/* 333:365 */     System.out.println("> " + paramString + "; LA(1)==" + LT(1).getText() + (this.inputState.guessing > 0 ? " [guessing]" : ""));
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void traceOut(String paramString)
/* 337:    */     throws TokenStreamException
/* 338:    */   {
/* 339:370 */     traceIndent();
/* 340:371 */     System.out.println("< " + paramString + "; LA(1)==" + LT(1).getText() + (this.inputState.guessing > 0 ? " [guessing]" : ""));
/* 341:    */     
/* 342:373 */     this.traceDepth -= 1;
/* 343:    */   }
/* 344:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.Parser
 * JD-Core Version:    0.7.0.1
 */