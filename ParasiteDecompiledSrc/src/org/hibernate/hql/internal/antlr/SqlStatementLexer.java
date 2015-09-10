/*   1:    */ package org.hibernate.hql.internal.antlr;
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
/*  18:    */ import antlr.collections.impl.BitSet;
/*  19:    */ import java.io.InputStream;
/*  20:    */ import java.io.Reader;
/*  21:    */ import java.util.Hashtable;
/*  22:    */ 
/*  23:    */ public class SqlStatementLexer
/*  24:    */   extends CharScanner
/*  25:    */   implements SqlStatementParserTokenTypes, TokenStream
/*  26:    */ {
/*  27:    */   public SqlStatementLexer(InputStream in)
/*  28:    */   {
/*  29: 38 */     this(new ByteBuffer(in));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public SqlStatementLexer(Reader in)
/*  33:    */   {
/*  34: 41 */     this(new CharBuffer(in));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public SqlStatementLexer(InputBuffer ib)
/*  38:    */   {
/*  39: 44 */     this(new LexerSharedInputState(ib));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public SqlStatementLexer(LexerSharedInputState state)
/*  43:    */   {
/*  44: 47 */     super(state);
/*  45: 48 */     this.caseSensitiveLiterals = true;
/*  46: 49 */     setCaseSensitive(true);
/*  47: 50 */     this.literals = new Hashtable();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Token nextToken()
/*  51:    */     throws TokenStreamException
/*  52:    */   {
/*  53: 54 */     Token theRetToken = null;
/*  54:    */     for (;;)
/*  55:    */     {
/*  56: 57 */       Token _token = null;
/*  57: 58 */       int _ttype = 0;
/*  58: 59 */       resetText();
/*  59:    */       try
/*  60:    */       {
/*  61: 62 */         if ((LA(1) == '\'') && (LA(2) >= 0) && (LA(2) <= 65534))
/*  62:    */         {
/*  63: 63 */           mQUOTED_STRING(true);
/*  64: 64 */           theRetToken = this._returnToken;
/*  65:    */         }
/*  66: 66 */         else if (((LA(1) == '-') || (LA(1) == '/')) && ((LA(2) == '-') || (LA(2) == '/')))
/*  67:    */         {
/*  68: 67 */           mLINE_COMMENT(true);
/*  69: 68 */           theRetToken = this._returnToken;
/*  70:    */         }
/*  71: 70 */         else if ((LA(1) == '/') && (LA(2) == '*'))
/*  72:    */         {
/*  73: 71 */           mMULTILINE_COMMENT(true);
/*  74: 72 */           theRetToken = this._returnToken;
/*  75:    */         }
/*  76: 74 */         else if (LA(1) == ';')
/*  77:    */         {
/*  78: 75 */           mSTMT_END(true);
/*  79: 76 */           theRetToken = this._returnToken;
/*  80:    */         }
/*  81: 78 */         else if (_tokenSet_0.member(LA(1)))
/*  82:    */         {
/*  83: 79 */           mNOT_STMT_END(true);
/*  84: 80 */           theRetToken = this._returnToken;
/*  85:    */         }
/*  86: 83 */         else if (LA(1) == 65535)
/*  87:    */         {
/*  88: 83 */           uponEOF();this._returnToken = makeToken(1);
/*  89:    */         }
/*  90:    */         else
/*  91:    */         {
/*  92: 84 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*  93:    */         }
/*  94: 87 */         if (this._returnToken == null) {
/*  95:    */           continue;
/*  96:    */         }
/*  97: 88 */         _ttype = this._returnToken.getType();
/*  98: 89 */         _ttype = testLiteralsTable(_ttype);
/*  99: 90 */         this._returnToken.setType(_ttype);
/* 100: 91 */         return this._returnToken;
/* 101:    */       }
/* 102:    */       catch (RecognitionException e)
/* 103:    */       {
/* 104: 94 */         throw new TokenStreamRecognitionException(e);
/* 105:    */       }
/* 106:    */       catch (CharStreamException cse)
/* 107:    */       {
/* 108: 98 */         if ((cse instanceof CharStreamIOException)) {
/* 109: 99 */           throw new TokenStreamIOException(((CharStreamIOException)cse).io);
/* 110:    */         }
/* 111:102 */         throw new TokenStreamException(cse.getMessage());
/* 112:    */       }
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public final void mSTMT_END(boolean _createToken)
/* 117:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 118:    */   {
/* 119:109 */     Token _token = null;int _begin = this.text.length();
/* 120:110 */     int _ttype = 6;
/* 121:    */     
/* 122:    */ 
/* 123:113 */     match(';');
/* 124:114 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 125:    */     {
/* 126:115 */       _token = makeToken(_ttype);
/* 127:116 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 128:    */     }
/* 129:118 */     this._returnToken = _token;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public final void mNOT_STMT_END(boolean _createToken)
/* 133:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 134:    */   {
/* 135:122 */     Token _token = null;int _begin = this.text.length();
/* 136:123 */     int _ttype = 4;
/* 137:    */     
/* 138:    */ 
/* 139:    */ 
/* 140:127 */     match(_tokenSet_0);
/* 141:129 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 142:    */     {
/* 143:130 */       _token = makeToken(_ttype);
/* 144:131 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 145:    */     }
/* 146:133 */     this._returnToken = _token;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public final void mQUOTED_STRING(boolean _createToken)
/* 150:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 151:    */   {
/* 152:137 */     Token _token = null;int _begin = this.text.length();
/* 153:138 */     int _ttype = 5;
/* 154:    */     
/* 155:    */ 
/* 156:141 */     match('\'');
/* 157:    */     for (;;)
/* 158:    */     {
/* 159:145 */       boolean synPredMatched13 = false;
/* 160:146 */       if ((LA(1) == '\'') && (LA(2) == '\''))
/* 161:    */       {
/* 162:147 */         int _m13 = mark();
/* 163:148 */         synPredMatched13 = true;
/* 164:149 */         this.inputState.guessing += 1;
/* 165:    */         try
/* 166:    */         {
/* 167:152 */           mESCqs(false);
/* 168:    */         }
/* 169:    */         catch (RecognitionException pe)
/* 170:    */         {
/* 171:156 */           synPredMatched13 = false;
/* 172:    */         }
/* 173:158 */         rewind(_m13);
/* 174:159 */         this.inputState.guessing -= 1;
/* 175:    */       }
/* 176:161 */       if (synPredMatched13)
/* 177:    */       {
/* 178:162 */         mESCqs(false);
/* 179:    */       }
/* 180:    */       else
/* 181:    */       {
/* 182:164 */         if (!_tokenSet_1.member(LA(1))) {
/* 183:    */           break;
/* 184:    */         }
/* 185:165 */         matchNot('\'');
/* 186:    */       }
/* 187:    */     }
/* 188:173 */     match('\'');
/* 189:174 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 190:    */     {
/* 191:175 */       _token = makeToken(_ttype);
/* 192:176 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 193:    */     }
/* 194:178 */     this._returnToken = _token;
/* 195:    */   }
/* 196:    */   
/* 197:    */   protected final void mESCqs(boolean _createToken)
/* 198:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 199:    */   {
/* 200:182 */     Token _token = null;int _begin = this.text.length();
/* 201:183 */     int _ttype = 7;
/* 202:    */     
/* 203:    */ 
/* 204:186 */     match('\'');
/* 205:187 */     match('\'');
/* 206:188 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 207:    */     {
/* 208:189 */       _token = makeToken(_ttype);
/* 209:190 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 210:    */     }
/* 211:192 */     this._returnToken = _token;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public final void mLINE_COMMENT(boolean _createToken)
/* 215:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 216:    */   {
/* 217:196 */     Token _token = null;int _begin = this.text.length();
/* 218:197 */     int _ttype = 8;
/* 219:201 */     switch (LA(1))
/* 220:    */     {
/* 221:    */     case '/': 
/* 222:204 */       match("//");
/* 223:205 */       break;
/* 224:    */     case '-': 
/* 225:209 */       match("--");
/* 226:210 */       break;
/* 227:    */     default: 
/* 228:214 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 229:    */     }
/* 230:221 */     while (_tokenSet_2.member(LA(1))) {
/* 231:223 */       match(_tokenSet_2);
/* 232:    */     }
/* 233:232 */     if (this.inputState.guessing == 0) {
/* 234:233 */       _ttype = -1;
/* 235:    */     }
/* 236:235 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 237:    */     {
/* 238:236 */       _token = makeToken(_ttype);
/* 239:237 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 240:    */     }
/* 241:239 */     this._returnToken = _token;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public final void mMULTILINE_COMMENT(boolean _createToken)
/* 245:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 246:    */   {
/* 247:243 */     Token _token = null;int _begin = this.text.length();
/* 248:244 */     int _ttype = 9;
/* 249:    */     
/* 250:    */ 
/* 251:247 */     match("/*");
/* 252:252 */     while (((LA(1) != '*') || (LA(2) != '/')) && 
/* 253:253 */       (LA(1) >= 0) && (LA(1) <= 65534) && (LA(2) >= 0) && (LA(2) <= 65534)) {
/* 254:254 */       matchNot(65535);
/* 255:    */     }
/* 256:262 */     match("*/");
/* 257:263 */     if (this.inputState.guessing == 0) {
/* 258:264 */       _ttype = -1;
/* 259:    */     }
/* 260:266 */     if ((_createToken) && (_token == null) && (_ttype != -1))
/* 261:    */     {
/* 262:267 */       _token = makeToken(_ttype);
/* 263:268 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 264:    */     }
/* 265:270 */     this._returnToken = _token;
/* 266:    */   }
/* 267:    */   
/* 268:    */   private static final long[] mk_tokenSet_0()
/* 269:    */   {
/* 270:275 */     long[] data = new long[2048];
/* 271:276 */     data[0] = -576460752303423489L;
/* 272:277 */     for (int i = 1; i <= 1022; i++) {
/* 273:277 */       data[i] = -1L;
/* 274:    */     }
/* 275:278 */     data[1023] = 9223372036854775807L;
/* 276:279 */     return data;
/* 277:    */   }
/* 278:    */   
/* 279:281 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 280:    */   
/* 281:    */   private static final long[] mk_tokenSet_1()
/* 282:    */   {
/* 283:283 */     long[] data = new long[2048];
/* 284:284 */     data[0] = -549755813889L;
/* 285:285 */     for (int i = 1; i <= 1022; i++) {
/* 286:285 */       data[i] = -1L;
/* 287:    */     }
/* 288:286 */     data[1023] = 9223372036854775807L;
/* 289:287 */     return data;
/* 290:    */   }
/* 291:    */   
/* 292:289 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 293:    */   
/* 294:    */   private static final long[] mk_tokenSet_2()
/* 295:    */   {
/* 296:291 */     long[] data = new long[2048];
/* 297:292 */     data[0] = -9217L;
/* 298:293 */     for (int i = 1; i <= 1022; i++) {
/* 299:293 */       data[i] = -1L;
/* 300:    */     }
/* 301:294 */     data[1023] = 9223372036854775807L;
/* 302:295 */     return data;
/* 303:    */   }
/* 304:    */   
/* 305:297 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 306:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.antlr.SqlStatementLexer
 * JD-Core Version:    0.7.0.1
 */