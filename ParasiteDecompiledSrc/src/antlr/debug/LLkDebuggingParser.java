/*   1:    */ package antlr.debug;
/*   2:    */ 
/*   3:    */ import antlr.LLkParser;
/*   4:    */ import antlr.MismatchedTokenException;
/*   5:    */ import antlr.ParserSharedInputState;
/*   6:    */ import antlr.RecognitionException;
/*   7:    */ import antlr.Token;
/*   8:    */ import antlr.TokenBuffer;
/*   9:    */ import antlr.TokenStream;
/*  10:    */ import antlr.TokenStreamException;
/*  11:    */ import antlr.Utils;
/*  12:    */ import antlr.collections.impl.BitSet;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.lang.reflect.Constructor;
/*  15:    */ 
/*  16:    */ public class LLkDebuggingParser
/*  17:    */   extends LLkParser
/*  18:    */   implements DebuggingParser
/*  19:    */ {
/*  20: 13 */   protected ParserEventSupport parserEventSupport = new ParserEventSupport(this);
/*  21: 15 */   private boolean _notDebugMode = false;
/*  22:    */   protected String[] ruleNames;
/*  23:    */   protected String[] semPredNames;
/*  24:    */   
/*  25:    */   public LLkDebuggingParser(int paramInt)
/*  26:    */   {
/*  27: 21 */     super(paramInt);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public LLkDebuggingParser(ParserSharedInputState paramParserSharedInputState, int paramInt)
/*  31:    */   {
/*  32: 24 */     super(paramParserSharedInputState, paramInt);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public LLkDebuggingParser(TokenBuffer paramTokenBuffer, int paramInt)
/*  36:    */   {
/*  37: 27 */     super(paramTokenBuffer, paramInt);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public LLkDebuggingParser(TokenStream paramTokenStream, int paramInt)
/*  41:    */   {
/*  42: 30 */     super(paramTokenStream, paramInt);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void addMessageListener(MessageListener paramMessageListener)
/*  46:    */   {
/*  47: 33 */     this.parserEventSupport.addMessageListener(paramMessageListener);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void addParserListener(ParserListener paramParserListener)
/*  51:    */   {
/*  52: 36 */     this.parserEventSupport.addParserListener(paramParserListener);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void addParserMatchListener(ParserMatchListener paramParserMatchListener)
/*  56:    */   {
/*  57: 39 */     this.parserEventSupport.addParserMatchListener(paramParserMatchListener);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addParserTokenListener(ParserTokenListener paramParserTokenListener)
/*  61:    */   {
/*  62: 42 */     this.parserEventSupport.addParserTokenListener(paramParserTokenListener);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener)
/*  66:    */   {
/*  67: 45 */     this.parserEventSupport.addSemanticPredicateListener(paramSemanticPredicateListener);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void addSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener)
/*  71:    */   {
/*  72: 48 */     this.parserEventSupport.addSyntacticPredicateListener(paramSyntacticPredicateListener);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void addTraceListener(TraceListener paramTraceListener)
/*  76:    */   {
/*  77: 51 */     this.parserEventSupport.addTraceListener(paramTraceListener);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void consume()
/*  81:    */     throws TokenStreamException
/*  82:    */   {
/*  83: 55 */     int i = -99;
/*  84: 56 */     i = LA(1);
/*  85: 57 */     super.consume();
/*  86: 58 */     this.parserEventSupport.fireConsume(i);
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void fireEnterRule(int paramInt1, int paramInt2)
/*  90:    */   {
/*  91: 61 */     if (isDebugMode()) {
/*  92: 62 */       this.parserEventSupport.fireEnterRule(paramInt1, this.inputState.guessing, paramInt2);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void fireExitRule(int paramInt1, int paramInt2)
/*  97:    */   {
/*  98: 65 */     if (isDebugMode()) {
/*  99: 66 */       this.parserEventSupport.fireExitRule(paramInt1, this.inputState.guessing, paramInt2);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected boolean fireSemanticPredicateEvaluated(int paramInt1, int paramInt2, boolean paramBoolean)
/* 104:    */   {
/* 105: 69 */     if (isDebugMode()) {
/* 106: 70 */       return this.parserEventSupport.fireSemanticPredicateEvaluated(paramInt1, paramInt2, paramBoolean, this.inputState.guessing);
/* 107:    */     }
/* 108: 72 */     return paramBoolean;
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void fireSyntacticPredicateFailed()
/* 112:    */   {
/* 113: 75 */     if (isDebugMode()) {
/* 114: 76 */       this.parserEventSupport.fireSyntacticPredicateFailed(this.inputState.guessing);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void fireSyntacticPredicateStarted()
/* 119:    */   {
/* 120: 79 */     if (isDebugMode()) {
/* 121: 80 */       this.parserEventSupport.fireSyntacticPredicateStarted(this.inputState.guessing);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected void fireSyntacticPredicateSucceeded()
/* 126:    */   {
/* 127: 83 */     if (isDebugMode()) {
/* 128: 84 */       this.parserEventSupport.fireSyntacticPredicateSucceeded(this.inputState.guessing);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getRuleName(int paramInt)
/* 133:    */   {
/* 134: 87 */     return this.ruleNames[paramInt];
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getSemPredName(int paramInt)
/* 138:    */   {
/* 139: 90 */     return this.semPredNames[paramInt];
/* 140:    */   }
/* 141:    */   
/* 142:    */   public synchronized void goToSleep()
/* 143:    */   {
/* 144:    */     try
/* 145:    */     {
/* 146: 93 */       wait();
/* 147:    */     }
/* 148:    */     catch (InterruptedException localInterruptedException) {}
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isDebugMode()
/* 152:    */   {
/* 153: 97 */     return !this._notDebugMode;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isGuessing()
/* 157:    */   {
/* 158:100 */     return this.inputState.guessing > 0;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int LA(int paramInt)
/* 162:    */     throws TokenStreamException
/* 163:    */   {
/* 164:107 */     int i = super.LA(paramInt);
/* 165:108 */     this.parserEventSupport.fireLA(paramInt, i);
/* 166:109 */     return i;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void match(int paramInt)
/* 170:    */     throws MismatchedTokenException, TokenStreamException
/* 171:    */   {
/* 172:116 */     String str = LT(1).getText();
/* 173:117 */     int i = LA(1);
/* 174:    */     try
/* 175:    */     {
/* 176:119 */       super.match(paramInt);
/* 177:120 */       this.parserEventSupport.fireMatch(paramInt, str, this.inputState.guessing);
/* 178:    */     }
/* 179:    */     catch (MismatchedTokenException localMismatchedTokenException)
/* 180:    */     {
/* 181:123 */       if (this.inputState.guessing == 0) {
/* 182:124 */         this.parserEventSupport.fireMismatch(i, paramInt, str, this.inputState.guessing);
/* 183:    */       }
/* 184:125 */       throw localMismatchedTokenException;
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void match(BitSet paramBitSet)
/* 189:    */     throws MismatchedTokenException, TokenStreamException
/* 190:    */   {
/* 191:133 */     String str = LT(1).getText();
/* 192:134 */     int i = LA(1);
/* 193:    */     try
/* 194:    */     {
/* 195:136 */       super.match(paramBitSet);
/* 196:137 */       this.parserEventSupport.fireMatch(i, paramBitSet, str, this.inputState.guessing);
/* 197:    */     }
/* 198:    */     catch (MismatchedTokenException localMismatchedTokenException)
/* 199:    */     {
/* 200:140 */       if (this.inputState.guessing == 0) {
/* 201:141 */         this.parserEventSupport.fireMismatch(i, paramBitSet, str, this.inputState.guessing);
/* 202:    */       }
/* 203:142 */       throw localMismatchedTokenException;
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void matchNot(int paramInt)
/* 208:    */     throws MismatchedTokenException, TokenStreamException
/* 209:    */   {
/* 210:146 */     String str = LT(1).getText();
/* 211:147 */     int i = LA(1);
/* 212:    */     try
/* 213:    */     {
/* 214:149 */       super.matchNot(paramInt);
/* 215:150 */       this.parserEventSupport.fireMatchNot(i, paramInt, str, this.inputState.guessing);
/* 216:    */     }
/* 217:    */     catch (MismatchedTokenException localMismatchedTokenException)
/* 218:    */     {
/* 219:153 */       if (this.inputState.guessing == 0) {
/* 220:154 */         this.parserEventSupport.fireMismatchNot(i, paramInt, str, this.inputState.guessing);
/* 221:    */       }
/* 222:155 */       throw localMismatchedTokenException;
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void removeMessageListener(MessageListener paramMessageListener)
/* 227:    */   {
/* 228:159 */     this.parserEventSupport.removeMessageListener(paramMessageListener);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void removeParserListener(ParserListener paramParserListener)
/* 232:    */   {
/* 233:162 */     this.parserEventSupport.removeParserListener(paramParserListener);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void removeParserMatchListener(ParserMatchListener paramParserMatchListener)
/* 237:    */   {
/* 238:165 */     this.parserEventSupport.removeParserMatchListener(paramParserMatchListener);
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void removeParserTokenListener(ParserTokenListener paramParserTokenListener)
/* 242:    */   {
/* 243:168 */     this.parserEventSupport.removeParserTokenListener(paramParserTokenListener);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void removeSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener)
/* 247:    */   {
/* 248:171 */     this.parserEventSupport.removeSemanticPredicateListener(paramSemanticPredicateListener);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void removeSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener)
/* 252:    */   {
/* 253:174 */     this.parserEventSupport.removeSyntacticPredicateListener(paramSyntacticPredicateListener);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void removeTraceListener(TraceListener paramTraceListener)
/* 257:    */   {
/* 258:177 */     this.parserEventSupport.removeTraceListener(paramTraceListener);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void reportError(RecognitionException paramRecognitionException)
/* 262:    */   {
/* 263:181 */     this.parserEventSupport.fireReportError(paramRecognitionException);
/* 264:182 */     super.reportError(paramRecognitionException);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void reportError(String paramString)
/* 268:    */   {
/* 269:186 */     this.parserEventSupport.fireReportError(paramString);
/* 270:187 */     super.reportError(paramString);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void reportWarning(String paramString)
/* 274:    */   {
/* 275:191 */     this.parserEventSupport.fireReportWarning(paramString);
/* 276:192 */     super.reportWarning(paramString);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setDebugMode(boolean paramBoolean)
/* 280:    */   {
/* 281:195 */     this._notDebugMode = (!paramBoolean);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setupDebugging(TokenBuffer paramTokenBuffer)
/* 285:    */   {
/* 286:198 */     setupDebugging(null, paramTokenBuffer);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setupDebugging(TokenStream paramTokenStream)
/* 290:    */   {
/* 291:201 */     setupDebugging(paramTokenStream, null);
/* 292:    */   }
/* 293:    */   
/* 294:    */   protected void setupDebugging(TokenStream paramTokenStream, TokenBuffer paramTokenBuffer)
/* 295:    */   {
/* 296:205 */     setDebugMode(true);
/* 297:    */     try
/* 298:    */     {
/* 299:    */       try
/* 300:    */       {
/* 301:209 */         Utils.loadClass("javax.swing.JButton");
/* 302:    */       }
/* 303:    */       catch (ClassNotFoundException localClassNotFoundException)
/* 304:    */       {
/* 305:212 */         System.err.println("Swing is required to use ParseView, but is not present in your CLASSPATH");
/* 306:213 */         System.exit(1);
/* 307:    */       }
/* 308:215 */       Class localClass = Utils.loadClass("antlr.parseview.ParseView");
/* 309:216 */       Constructor localConstructor = localClass.getConstructor(new Class[] { LLkDebuggingParser.class, TokenStream.class, TokenBuffer.class });
/* 310:217 */       localConstructor.newInstance(new Object[] { this, paramTokenStream, paramTokenBuffer });
/* 311:    */     }
/* 312:    */     catch (Exception localException)
/* 313:    */     {
/* 314:220 */       System.err.println("Error initializing ParseView: " + localException);
/* 315:221 */       System.err.println("Please report this to Scott Stanchfield, thetick@magelang.com");
/* 316:222 */       System.exit(1);
/* 317:    */     }
/* 318:    */   }
/* 319:    */   
/* 320:    */   public synchronized void wakeUp()
/* 321:    */   {
/* 322:226 */     notify();
/* 323:    */   }
/* 324:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.LLkDebuggingParser
 * JD-Core Version:    0.7.0.1
 */