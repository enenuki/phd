/*   1:    */ package antlr.debug;
/*   2:    */ 
/*   3:    */ import antlr.ANTLRStringBuffer;
/*   4:    */ import antlr.CharScanner;
/*   5:    */ import antlr.CharStreamException;
/*   6:    */ import antlr.InputBuffer;
/*   7:    */ import antlr.LexerSharedInputState;
/*   8:    */ import antlr.MismatchedCharException;
/*   9:    */ import antlr.Token;
/*  10:    */ import antlr.collections.impl.BitSet;
/*  11:    */ 
/*  12:    */ public abstract class DebuggingCharScanner
/*  13:    */   extends CharScanner
/*  14:    */   implements DebuggingParser
/*  15:    */ {
/*  16:  9 */   private ParserEventSupport parserEventSupport = new ParserEventSupport(this);
/*  17: 10 */   private boolean _notDebugMode = false;
/*  18:    */   protected String[] ruleNames;
/*  19:    */   protected String[] semPredNames;
/*  20:    */   
/*  21:    */   public DebuggingCharScanner(InputBuffer paramInputBuffer)
/*  22:    */   {
/*  23: 16 */     super(paramInputBuffer);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public DebuggingCharScanner(LexerSharedInputState paramLexerSharedInputState)
/*  27:    */   {
/*  28: 19 */     super(paramLexerSharedInputState);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void addMessageListener(MessageListener paramMessageListener)
/*  32:    */   {
/*  33: 22 */     this.parserEventSupport.addMessageListener(paramMessageListener);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void addNewLineListener(NewLineListener paramNewLineListener)
/*  37:    */   {
/*  38: 25 */     this.parserEventSupport.addNewLineListener(paramNewLineListener);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void addParserListener(ParserListener paramParserListener)
/*  42:    */   {
/*  43: 28 */     this.parserEventSupport.addParserListener(paramParserListener);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void addParserMatchListener(ParserMatchListener paramParserMatchListener)
/*  47:    */   {
/*  48: 31 */     this.parserEventSupport.addParserMatchListener(paramParserMatchListener);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void addParserTokenListener(ParserTokenListener paramParserTokenListener)
/*  52:    */   {
/*  53: 34 */     this.parserEventSupport.addParserTokenListener(paramParserTokenListener);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void addSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener)
/*  57:    */   {
/*  58: 37 */     this.parserEventSupport.addSemanticPredicateListener(paramSemanticPredicateListener);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void addSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener)
/*  62:    */   {
/*  63: 40 */     this.parserEventSupport.addSyntacticPredicateListener(paramSyntacticPredicateListener);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void addTraceListener(TraceListener paramTraceListener)
/*  67:    */   {
/*  68: 43 */     this.parserEventSupport.addTraceListener(paramTraceListener);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void consume()
/*  72:    */     throws CharStreamException
/*  73:    */   {
/*  74: 46 */     int i = -99;
/*  75:    */     try
/*  76:    */     {
/*  77: 47 */       i = LA(1);
/*  78:    */     }
/*  79:    */     catch (CharStreamException localCharStreamException) {}
/*  80: 49 */     super.consume();
/*  81: 50 */     this.parserEventSupport.fireConsume(i);
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void fireEnterRule(int paramInt1, int paramInt2)
/*  85:    */   {
/*  86: 53 */     if (isDebugMode()) {
/*  87: 54 */       this.parserEventSupport.fireEnterRule(paramInt1, this.inputState.guessing, paramInt2);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void fireExitRule(int paramInt1, int paramInt2)
/*  92:    */   {
/*  93: 57 */     if (isDebugMode()) {
/*  94: 58 */       this.parserEventSupport.fireExitRule(paramInt1, this.inputState.guessing, paramInt2);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected boolean fireSemanticPredicateEvaluated(int paramInt1, int paramInt2, boolean paramBoolean)
/*  99:    */   {
/* 100: 61 */     if (isDebugMode()) {
/* 101: 62 */       return this.parserEventSupport.fireSemanticPredicateEvaluated(paramInt1, paramInt2, paramBoolean, this.inputState.guessing);
/* 102:    */     }
/* 103: 64 */     return paramBoolean;
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected void fireSyntacticPredicateFailed()
/* 107:    */   {
/* 108: 67 */     if (isDebugMode()) {
/* 109: 68 */       this.parserEventSupport.fireSyntacticPredicateFailed(this.inputState.guessing);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected void fireSyntacticPredicateStarted()
/* 114:    */   {
/* 115: 71 */     if (isDebugMode()) {
/* 116: 72 */       this.parserEventSupport.fireSyntacticPredicateStarted(this.inputState.guessing);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected void fireSyntacticPredicateSucceeded()
/* 121:    */   {
/* 122: 75 */     if (isDebugMode()) {
/* 123: 76 */       this.parserEventSupport.fireSyntacticPredicateSucceeded(this.inputState.guessing);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getRuleName(int paramInt)
/* 128:    */   {
/* 129: 79 */     return this.ruleNames[paramInt];
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getSemPredName(int paramInt)
/* 133:    */   {
/* 134: 82 */     return this.semPredNames[paramInt];
/* 135:    */   }
/* 136:    */   
/* 137:    */   public synchronized void goToSleep()
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141: 85 */       wait();
/* 142:    */     }
/* 143:    */     catch (InterruptedException localInterruptedException) {}
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isDebugMode()
/* 147:    */   {
/* 148: 89 */     return !this._notDebugMode;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public char LA(int paramInt)
/* 152:    */     throws CharStreamException
/* 153:    */   {
/* 154: 92 */     char c = super.LA(paramInt);
/* 155: 93 */     this.parserEventSupport.fireLA(paramInt, c);
/* 156: 94 */     return c;
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected Token makeToken(int paramInt)
/* 160:    */   {
/* 161:111 */     return super.makeToken(paramInt);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void match(char paramChar)
/* 165:    */     throws MismatchedCharException, CharStreamException
/* 166:    */   {
/* 167:114 */     char c = LA(1);
/* 168:    */     try
/* 169:    */     {
/* 170:116 */       super.match(paramChar);
/* 171:117 */       this.parserEventSupport.fireMatch(paramChar, this.inputState.guessing);
/* 172:    */     }
/* 173:    */     catch (MismatchedCharException localMismatchedCharException)
/* 174:    */     {
/* 175:120 */       if (this.inputState.guessing == 0) {
/* 176:121 */         this.parserEventSupport.fireMismatch(c, paramChar, this.inputState.guessing);
/* 177:    */       }
/* 178:122 */       throw localMismatchedCharException;
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void match(BitSet paramBitSet)
/* 183:    */     throws MismatchedCharException, CharStreamException
/* 184:    */   {
/* 185:126 */     String str = this.text.toString();
/* 186:127 */     int i = LA(1);
/* 187:    */     try
/* 188:    */     {
/* 189:129 */       super.match(paramBitSet);
/* 190:130 */       this.parserEventSupport.fireMatch(i, paramBitSet, str, this.inputState.guessing);
/* 191:    */     }
/* 192:    */     catch (MismatchedCharException localMismatchedCharException)
/* 193:    */     {
/* 194:133 */       if (this.inputState.guessing == 0) {
/* 195:134 */         this.parserEventSupport.fireMismatch(i, paramBitSet, str, this.inputState.guessing);
/* 196:    */       }
/* 197:135 */       throw localMismatchedCharException;
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void match(String paramString)
/* 202:    */     throws MismatchedCharException, CharStreamException
/* 203:    */   {
/* 204:139 */     StringBuffer localStringBuffer = new StringBuffer("");
/* 205:140 */     int i = paramString.length();
/* 206:    */     try
/* 207:    */     {
/* 208:143 */       for (int j = 1; j <= i; j++) {
/* 209:144 */         localStringBuffer.append(super.LA(j));
/* 210:    */       }
/* 211:    */     }
/* 212:    */     catch (Exception localException) {}
/* 213:    */     try
/* 214:    */     {
/* 215:150 */       super.match(paramString);
/* 216:151 */       this.parserEventSupport.fireMatch(paramString, this.inputState.guessing);
/* 217:    */     }
/* 218:    */     catch (MismatchedCharException localMismatchedCharException)
/* 219:    */     {
/* 220:154 */       if (this.inputState.guessing == 0) {
/* 221:155 */         this.parserEventSupport.fireMismatch(localStringBuffer.toString(), paramString, this.inputState.guessing);
/* 222:    */       }
/* 223:156 */       throw localMismatchedCharException;
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void matchNot(char paramChar)
/* 228:    */     throws MismatchedCharException, CharStreamException
/* 229:    */   {
/* 230:161 */     char c = LA(1);
/* 231:    */     try
/* 232:    */     {
/* 233:163 */       super.matchNot(paramChar);
/* 234:164 */       this.parserEventSupport.fireMatchNot(c, paramChar, this.inputState.guessing);
/* 235:    */     }
/* 236:    */     catch (MismatchedCharException localMismatchedCharException)
/* 237:    */     {
/* 238:167 */       if (this.inputState.guessing == 0) {
/* 239:168 */         this.parserEventSupport.fireMismatchNot(c, paramChar, this.inputState.guessing);
/* 240:    */       }
/* 241:169 */       throw localMismatchedCharException;
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void matchRange(char paramChar1, char paramChar2)
/* 246:    */     throws MismatchedCharException, CharStreamException
/* 247:    */   {
/* 248:174 */     char c = LA(1);
/* 249:    */     try
/* 250:    */     {
/* 251:176 */       super.matchRange(paramChar1, paramChar2);
/* 252:177 */       this.parserEventSupport.fireMatch(c, "" + paramChar1 + paramChar2, this.inputState.guessing);
/* 253:    */     }
/* 254:    */     catch (MismatchedCharException localMismatchedCharException)
/* 255:    */     {
/* 256:180 */       if (this.inputState.guessing == 0) {
/* 257:181 */         this.parserEventSupport.fireMismatch(c, "" + paramChar1 + paramChar2, this.inputState.guessing);
/* 258:    */       }
/* 259:182 */       throw localMismatchedCharException;
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void newline()
/* 264:    */   {
/* 265:187 */     super.newline();
/* 266:188 */     this.parserEventSupport.fireNewLine(getLine());
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void removeMessageListener(MessageListener paramMessageListener)
/* 270:    */   {
/* 271:191 */     this.parserEventSupport.removeMessageListener(paramMessageListener);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void removeNewLineListener(NewLineListener paramNewLineListener)
/* 275:    */   {
/* 276:194 */     this.parserEventSupport.removeNewLineListener(paramNewLineListener);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void removeParserListener(ParserListener paramParserListener)
/* 280:    */   {
/* 281:197 */     this.parserEventSupport.removeParserListener(paramParserListener);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void removeParserMatchListener(ParserMatchListener paramParserMatchListener)
/* 285:    */   {
/* 286:200 */     this.parserEventSupport.removeParserMatchListener(paramParserMatchListener);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void removeParserTokenListener(ParserTokenListener paramParserTokenListener)
/* 290:    */   {
/* 291:203 */     this.parserEventSupport.removeParserTokenListener(paramParserTokenListener);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void removeSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener)
/* 295:    */   {
/* 296:206 */     this.parserEventSupport.removeSemanticPredicateListener(paramSemanticPredicateListener);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void removeSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener)
/* 300:    */   {
/* 301:209 */     this.parserEventSupport.removeSyntacticPredicateListener(paramSyntacticPredicateListener);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void removeTraceListener(TraceListener paramTraceListener)
/* 305:    */   {
/* 306:212 */     this.parserEventSupport.removeTraceListener(paramTraceListener);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void reportError(MismatchedCharException paramMismatchedCharException)
/* 310:    */   {
/* 311:216 */     this.parserEventSupport.fireReportError(paramMismatchedCharException);
/* 312:217 */     super.reportError(paramMismatchedCharException);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void reportError(String paramString)
/* 316:    */   {
/* 317:221 */     this.parserEventSupport.fireReportError(paramString);
/* 318:222 */     super.reportError(paramString);
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void reportWarning(String paramString)
/* 322:    */   {
/* 323:226 */     this.parserEventSupport.fireReportWarning(paramString);
/* 324:227 */     super.reportWarning(paramString);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setDebugMode(boolean paramBoolean)
/* 328:    */   {
/* 329:230 */     this._notDebugMode = (!paramBoolean);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setupDebugging() {}
/* 333:    */   
/* 334:    */   public synchronized void wakeUp()
/* 335:    */   {
/* 336:235 */     notify();
/* 337:    */   }
/* 338:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.DebuggingCharScanner
 * JD-Core Version:    0.7.0.1
 */