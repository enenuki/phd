/*   1:    */ package antlr.actions.python;
/*   2:    */ 
/*   3:    */ import antlr.ANTLRStringBuffer;
/*   4:    */ import antlr.ByteBuffer;
/*   5:    */ import antlr.CharBuffer;
/*   6:    */ import antlr.CharScanner;
/*   7:    */ import antlr.CharStreamException;
/*   8:    */ import antlr.CharStreamIOException;
/*   9:    */ import antlr.InputBuffer;
/*  10:    */ import antlr.LexerSharedInputState;
/*  11:    */ import antlr.NoViableAltForCharException;
/*  12:    */ import antlr.RecognitionException;
/*  13:    */ import antlr.Token;
/*  14:    */ import antlr.TokenStream;
/*  15:    */ import antlr.TokenStreamException;
/*  16:    */ import antlr.TokenStreamIOException;
/*  17:    */ import antlr.TokenStreamRecognitionException;
/*  18:    */ import antlr.Tool;
/*  19:    */ import antlr.collections.impl.BitSet;
/*  20:    */ import java.io.InputStream;
/*  21:    */ import java.io.Reader;
/*  22:    */ import java.io.StringReader;
/*  23:    */ import java.util.Hashtable;
/*  24:    */ 
/*  25:    */ public class CodeLexer
/*  26:    */   extends CharScanner
/*  27:    */   implements CodeLexerTokenTypes, TokenStream
/*  28:    */ {
/*  29: 36 */   protected int lineOffset = 0;
/*  30:    */   private Tool antlrTool;
/*  31:    */   
/*  32:    */   public CodeLexer(String paramString1, String paramString2, int paramInt, Tool paramTool)
/*  33:    */   {
/*  34: 46 */     this(new StringReader(paramString1));
/*  35: 47 */     setLine(paramInt);
/*  36: 48 */     setFilename(paramString2);
/*  37: 49 */     this.antlrTool = paramTool;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setLineOffset(int paramInt)
/*  41:    */   {
/*  42: 53 */     setLine(paramInt);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void reportError(RecognitionException paramRecognitionException)
/*  46:    */   {
/*  47: 58 */     this.antlrTool.error("Syntax error in action: " + paramRecognitionException, getFilename(), getLine(), getColumn());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void reportError(String paramString)
/*  51:    */   {
/*  52: 65 */     this.antlrTool.error(paramString, getFilename(), getLine(), getColumn());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void reportWarning(String paramString)
/*  56:    */   {
/*  57: 70 */     if (getFilename() == null) {
/*  58: 71 */       this.antlrTool.warning(paramString);
/*  59:    */     } else {
/*  60: 74 */       this.antlrTool.warning(paramString, getFilename(), getLine(), getColumn());
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public CodeLexer(InputStream paramInputStream)
/*  65:    */   {
/*  66: 78 */     this(new ByteBuffer(paramInputStream));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CodeLexer(Reader paramReader)
/*  70:    */   {
/*  71: 81 */     this(new CharBuffer(paramReader));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public CodeLexer(InputBuffer paramInputBuffer)
/*  75:    */   {
/*  76: 84 */     this(new LexerSharedInputState(paramInputBuffer));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public CodeLexer(LexerSharedInputState paramLexerSharedInputState)
/*  80:    */   {
/*  81: 87 */     super(paramLexerSharedInputState);
/*  82: 88 */     this.caseSensitiveLiterals = true;
/*  83: 89 */     setCaseSensitive(true);
/*  84: 90 */     this.literals = new Hashtable();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Token nextToken()
/*  88:    */     throws TokenStreamException
/*  89:    */   {
/*  90: 94 */     Token localToken = null;
/*  91:    */     for (;;)
/*  92:    */     {
/*  93: 97 */       Object localObject = null;
/*  94: 98 */       int i = 0;
/*  95: 99 */       resetText();
/*  96:    */       try
/*  97:    */       {
/*  98:103 */         mACTION(true);
/*  99:104 */         localToken = this._returnToken;
/* 100:107 */         if (this._returnToken == null) {
/* 101:    */           continue;
/* 102:    */         }
/* 103:108 */         i = this._returnToken.getType();
/* 104:109 */         this._returnToken.setType(i);
/* 105:110 */         return this._returnToken;
/* 106:    */       }
/* 107:    */       catch (RecognitionException localRecognitionException)
/* 108:    */       {
/* 109:113 */         throw new TokenStreamRecognitionException(localRecognitionException);
/* 110:    */       }
/* 111:    */       catch (CharStreamException localCharStreamException)
/* 112:    */       {
/* 113:117 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/* 114:118 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/* 115:    */         }
/* 116:121 */         throw new TokenStreamException(localCharStreamException.getMessage());
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public final void mACTION(boolean paramBoolean)
/* 122:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 123:    */   {
/* 124:128 */     Token localToken = null;int j = this.text.length();
/* 125:129 */     int i = 4;
/* 126:135 */     while ((LA(1) >= '\003') && (LA(1) <= 'ÿ')) {
/* 127:136 */       mSTUFF(false);
/* 128:    */     }
/* 129:144 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 130:    */     {
/* 131:145 */       localToken = makeToken(i);
/* 132:146 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 133:    */     }
/* 134:148 */     this._returnToken = localToken;
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected final void mSTUFF(boolean paramBoolean)
/* 138:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 139:    */   {
/* 140:152 */     Token localToken = null;int j = this.text.length();
/* 141:153 */     int i = 5;
/* 142:156 */     if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/')))
/* 143:    */     {
/* 144:157 */       mCOMMENT(false);
/* 145:    */     }
/* 146:159 */     else if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 147:    */     {
/* 148:160 */       match("\r\n");
/* 149:161 */       newline();
/* 150:    */     }
/* 151:163 */     else if ((LA(1) == '/') && (_tokenSet_0.member(LA(2))))
/* 152:    */     {
/* 153:164 */       match('/');
/* 154:    */       
/* 155:166 */       match(_tokenSet_0);
/* 156:    */     }
/* 157:169 */     else if (LA(1) == '\r')
/* 158:    */     {
/* 159:170 */       match('\r');
/* 160:171 */       newline();
/* 161:    */     }
/* 162:173 */     else if (LA(1) == '\n')
/* 163:    */     {
/* 164:174 */       match('\n');
/* 165:175 */       newline();
/* 166:    */     }
/* 167:177 */     else if (_tokenSet_1.member(LA(1)))
/* 168:    */     {
/* 169:179 */       match(_tokenSet_1);
/* 170:    */     }
/* 171:    */     else
/* 172:    */     {
/* 173:183 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 174:    */     }
/* 175:186 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 176:    */     {
/* 177:187 */       localToken = makeToken(i);
/* 178:188 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 179:    */     }
/* 180:190 */     this._returnToken = localToken;
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected final void mCOMMENT(boolean paramBoolean)
/* 184:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 185:    */   {
/* 186:194 */     Token localToken = null;int j = this.text.length();
/* 187:195 */     int i = 6;
/* 188:198 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/* 189:199 */       mSL_COMMENT(false);
/* 190:201 */     } else if ((LA(1) == '/') && (LA(2) == '*')) {
/* 191:202 */       mML_COMMENT(false);
/* 192:    */     } else {
/* 193:205 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 194:    */     }
/* 195:208 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 196:    */     {
/* 197:209 */       localToken = makeToken(i);
/* 198:210 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 199:    */     }
/* 200:212 */     this._returnToken = localToken;
/* 201:    */   }
/* 202:    */   
/* 203:    */   protected final void mSL_COMMENT(boolean paramBoolean)
/* 204:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 205:    */   {
/* 206:216 */     Token localToken = null;int j = this.text.length();
/* 207:217 */     int i = 7;
/* 208:    */     
/* 209:    */ 
/* 210:220 */     int k = this.text.length();
/* 211:221 */     match("//");
/* 212:222 */     this.text.setLength(k);
/* 213:    */     
/* 214:    */ 
/* 215:225 */     this.text.append("#");
/* 216:231 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 217:232 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 218:233 */       matchNot(65535);
/* 219:    */     }
/* 220:242 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 221:243 */       match("\r\n");
/* 222:245 */     } else if (LA(1) == '\n') {
/* 223:246 */       match('\n');
/* 224:248 */     } else if (LA(1) == '\r') {
/* 225:249 */       match('\r');
/* 226:    */     } else {
/* 227:252 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 228:    */     }
/* 229:257 */     newline();
/* 230:259 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 231:    */     {
/* 232:260 */       localToken = makeToken(i);
/* 233:261 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 234:    */     }
/* 235:263 */     this._returnToken = localToken;
/* 236:    */   }
/* 237:    */   
/* 238:    */   protected final void mML_COMMENT(boolean paramBoolean)
/* 239:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 240:    */   {
/* 241:267 */     Token localToken = null;int j = this.text.length();
/* 242:268 */     int i = 9;
/* 243:    */     
/* 244:    */ 
/* 245:271 */     int m = 0;
/* 246:    */     
/* 247:    */ 
/* 248:274 */     int k = this.text.length();
/* 249:275 */     match("/*");
/* 250:276 */     this.text.setLength(k);
/* 251:    */     
/* 252:    */ 
/* 253:279 */     this.text.append("#");
/* 254:285 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 255:286 */       if ((LA(1) == '\r') && (LA(2) == '\n'))
/* 256:    */       {
/* 257:287 */         match('\r');
/* 258:288 */         match('\n');
/* 259:289 */         k = this.text.length();
/* 260:290 */         mIGNWS(false);
/* 261:291 */         this.text.setLength(k);
/* 262:    */         
/* 263:293 */         newline();
/* 264:294 */         this.text.append("# ");
/* 265:    */       }
/* 266:297 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 267:    */       {
/* 268:298 */         match('\r');
/* 269:299 */         k = this.text.length();
/* 270:300 */         mIGNWS(false);
/* 271:301 */         this.text.setLength(k);
/* 272:    */         
/* 273:303 */         newline();
/* 274:304 */         this.text.append("# ");
/* 275:    */       }
/* 276:307 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 277:    */       {
/* 278:308 */         match('\n');
/* 279:309 */         k = this.text.length();
/* 280:310 */         mIGNWS(false);
/* 281:311 */         this.text.setLength(k);
/* 282:    */         
/* 283:313 */         newline();
/* 284:314 */         this.text.append("# ");
/* 285:    */       }
/* 286:    */       else
/* 287:    */       {
/* 288:317 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/* 289:    */           break;
/* 290:    */         }
/* 291:318 */         matchNot(65535);
/* 292:    */       }
/* 293:    */     }
/* 294:328 */     this.text.append("\n");
/* 295:    */     
/* 296:330 */     k = this.text.length();
/* 297:331 */     match("*/");
/* 298:332 */     this.text.setLength(k);
/* 299:333 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 300:    */     {
/* 301:334 */       localToken = makeToken(i);
/* 302:335 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 303:    */     }
/* 304:337 */     this._returnToken = localToken;
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected final void mIGNWS(boolean paramBoolean)
/* 308:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 309:    */   {
/* 310:341 */     Token localToken = null;int j = this.text.length();
/* 311:342 */     int i = 8;
/* 312:    */     for (;;)
/* 313:    */     {
/* 314:348 */       if ((LA(1) == ' ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 315:    */       {
/* 316:349 */         match(' ');
/* 317:    */       }
/* 318:    */       else
/* 319:    */       {
/* 320:351 */         if ((LA(1) != '\t') || (LA(2) < '\003') || (LA(2) > 'ÿ')) {
/* 321:    */           break;
/* 322:    */         }
/* 323:352 */         match('\t');
/* 324:    */       }
/* 325:    */     }
/* 326:360 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 327:    */     {
/* 328:361 */       localToken = makeToken(i);
/* 329:362 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 330:    */     }
/* 331:364 */     this._returnToken = localToken;
/* 332:    */   }
/* 333:    */   
/* 334:    */   private static final long[] mk_tokenSet_0()
/* 335:    */   {
/* 336:369 */     long[] arrayOfLong = new long[8];
/* 337:370 */     arrayOfLong[0] = -145135534866440L;
/* 338:371 */     for (int i = 1; i <= 3; i++) {
/* 339:371 */       arrayOfLong[i] = -1L;
/* 340:    */     }
/* 341:372 */     return arrayOfLong;
/* 342:    */   }
/* 343:    */   
/* 344:374 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 345:    */   
/* 346:    */   private static final long[] mk_tokenSet_1()
/* 347:    */   {
/* 348:376 */     long[] arrayOfLong = new long[8];
/* 349:377 */     arrayOfLong[0] = -140737488364552L;
/* 350:378 */     for (int i = 1; i <= 3; i++) {
/* 351:378 */       arrayOfLong[i] = -1L;
/* 352:    */     }
/* 353:379 */     return arrayOfLong;
/* 354:    */   }
/* 355:    */   
/* 356:381 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 357:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.actions.python.CodeLexer
 * JD-Core Version:    0.7.0.1
 */