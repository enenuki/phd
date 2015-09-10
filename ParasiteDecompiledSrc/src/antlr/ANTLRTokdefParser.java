/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ 
/*   5:    */ public class ANTLRTokdefParser
/*   6:    */   extends LLkParser
/*   7:    */   implements ANTLRTokdefParserTokenTypes
/*   8:    */ {
/*   9:    */   private Tool antlrTool;
/*  10:    */   
/*  11:    */   public void setTool(Tool paramTool)
/*  12:    */   {
/*  13: 34 */     if (this.antlrTool == null) {
/*  14: 35 */       this.antlrTool = paramTool;
/*  15:    */     } else {
/*  16: 38 */       throw new IllegalStateException("antlr.Tool already registered");
/*  17:    */     }
/*  18:    */   }
/*  19:    */   
/*  20:    */   protected Tool getTool()
/*  21:    */   {
/*  22: 44 */     return this.antlrTool;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void reportError(String paramString)
/*  26:    */   {
/*  27: 52 */     if (getTool() != null) {
/*  28: 53 */       getTool().error(paramString, getFilename(), -1, -1);
/*  29:    */     } else {
/*  30: 56 */       super.reportError(paramString);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void reportError(RecognitionException paramRecognitionException)
/*  35:    */   {
/*  36: 65 */     if (getTool() != null) {
/*  37: 66 */       getTool().error(paramRecognitionException.getErrorMessage(), paramRecognitionException.getFilename(), paramRecognitionException.getLine(), paramRecognitionException.getColumn());
/*  38:    */     } else {
/*  39: 69 */       super.reportError(paramRecognitionException);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void reportWarning(String paramString)
/*  44:    */   {
/*  45: 78 */     if (getTool() != null) {
/*  46: 79 */       getTool().warning(paramString, getFilename(), -1, -1);
/*  47:    */     } else {
/*  48: 82 */       super.reportWarning(paramString);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected ANTLRTokdefParser(TokenBuffer paramTokenBuffer, int paramInt)
/*  53:    */   {
/*  54: 87 */     super(paramTokenBuffer, paramInt);
/*  55: 88 */     this.tokenNames = _tokenNames;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ANTLRTokdefParser(TokenBuffer paramTokenBuffer)
/*  59:    */   {
/*  60: 92 */     this(paramTokenBuffer, 3);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected ANTLRTokdefParser(TokenStream paramTokenStream, int paramInt)
/*  64:    */   {
/*  65: 96 */     super(paramTokenStream, paramInt);
/*  66: 97 */     this.tokenNames = _tokenNames;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ANTLRTokdefParser(TokenStream paramTokenStream)
/*  70:    */   {
/*  71:101 */     this(paramTokenStream, 3);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ANTLRTokdefParser(ParserSharedInputState paramParserSharedInputState)
/*  75:    */   {
/*  76:105 */     super(paramParserSharedInputState, 3);
/*  77:106 */     this.tokenNames = _tokenNames;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public final void file(ImportVocabTokenManager paramImportVocabTokenManager)
/*  81:    */     throws RecognitionException, TokenStreamException
/*  82:    */   {
/*  83:113 */     Token localToken = null;
/*  84:    */     try
/*  85:    */     {
/*  86:116 */       localToken = LT(1);
/*  87:117 */       match(4);
/*  88:121 */       while ((LA(1) == 4) || (LA(1) == 5)) {
/*  89:122 */         line(paramImportVocabTokenManager);
/*  90:    */       }
/*  91:    */     }
/*  92:    */     catch (RecognitionException localRecognitionException)
/*  93:    */     {
/*  94:132 */       reportError(localRecognitionException);
/*  95:133 */       consume();
/*  96:134 */       consumeUntil(_tokenSet_0);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public final void line(ImportVocabTokenManager paramImportVocabTokenManager)
/* 101:    */     throws RecognitionException, TokenStreamException
/* 102:    */   {
/* 103:142 */     Token localToken1 = null;
/* 104:143 */     Token localToken2 = null;
/* 105:144 */     Token localToken3 = null;
/* 106:145 */     Token localToken4 = null;
/* 107:146 */     Token localToken5 = null;
/* 108:147 */     Token localToken6 = null;
/* 109:148 */     Token localToken7 = null;
/* 110:149 */     Token localToken8 = null;Token localToken9 = null;
/* 111:    */     try
/* 112:    */     {
/* 113:153 */       if (LA(1) == 5)
/* 114:    */       {
/* 115:154 */         localToken1 = LT(1);
/* 116:155 */         match(5);
/* 117:156 */         localToken9 = localToken1;
/* 118:    */       }
/* 119:158 */       else if ((LA(1) == 4) && (LA(2) == 6) && (LA(3) == 5))
/* 120:    */       {
/* 121:159 */         localToken2 = LT(1);
/* 122:160 */         match(4);
/* 123:161 */         localToken8 = localToken2;
/* 124:162 */         match(6);
/* 125:163 */         localToken3 = LT(1);
/* 126:164 */         match(5);
/* 127:165 */         localToken9 = localToken3;
/* 128:    */       }
/* 129:167 */       else if ((LA(1) == 4) && (LA(2) == 7))
/* 130:    */       {
/* 131:168 */         localToken4 = LT(1);
/* 132:169 */         match(4);
/* 133:170 */         localToken8 = localToken4;
/* 134:171 */         match(7);
/* 135:172 */         localToken5 = LT(1);
/* 136:173 */         match(5);
/* 137:174 */         match(8);
/* 138:    */       }
/* 139:176 */       else if ((LA(1) == 4) && (LA(2) == 6) && (LA(3) == 9))
/* 140:    */       {
/* 141:177 */         localToken6 = LT(1);
/* 142:178 */         match(4);
/* 143:179 */         localToken8 = localToken6;
/* 144:    */       }
/* 145:    */       else
/* 146:    */       {
/* 147:182 */         throw new NoViableAltException(LT(1), getFilename());
/* 148:    */       }
/* 149:186 */       match(6);
/* 150:187 */       localToken7 = LT(1);
/* 151:188 */       match(9);
/* 152:    */       
/* 153:190 */       Integer localInteger = Integer.valueOf(localToken7.getText());
/* 154:    */       Object localObject;
/* 155:192 */       if (localToken9 != null)
/* 156:    */       {
/* 157:193 */         paramImportVocabTokenManager.define(localToken9.getText(), localInteger.intValue());
/* 158:195 */         if (localToken8 != null)
/* 159:    */         {
/* 160:196 */           localObject = (StringLiteralSymbol)paramImportVocabTokenManager.getTokenSymbol(localToken9.getText());
/* 161:    */           
/* 162:198 */           ((StringLiteralSymbol)localObject).setLabel(localToken8.getText());
/* 163:199 */           paramImportVocabTokenManager.mapToTokenSymbol(localToken8.getText(), (TokenSymbol)localObject);
/* 164:    */         }
/* 165:    */       }
/* 166:203 */       else if (localToken8 != null)
/* 167:    */       {
/* 168:204 */         paramImportVocabTokenManager.define(localToken8.getText(), localInteger.intValue());
/* 169:205 */         if (localToken5 != null)
/* 170:    */         {
/* 171:206 */           localObject = paramImportVocabTokenManager.getTokenSymbol(localToken8.getText());
/* 172:207 */           ((TokenSymbol)localObject).setParaphrase(localToken5.getText());
/* 173:    */         }
/* 174:    */       }
/* 175:    */     }
/* 176:    */     catch (RecognitionException localRecognitionException)
/* 177:    */     {
/* 178:215 */       reportError(localRecognitionException);
/* 179:216 */       consume();
/* 180:217 */       consumeUntil(_tokenSet_1);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:222 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "ID", "STRING", "ASSIGN", "LPAREN", "RPAREN", "INT", "WS", "SL_COMMENT", "ML_COMMENT", "ESC", "DIGIT", "XDIGIT" };
/* 185:    */   
/* 186:    */   private static final long[] mk_tokenSet_0()
/* 187:    */   {
/* 188:242 */     long[] arrayOfLong = { 2L, 0L };
/* 189:243 */     return arrayOfLong;
/* 190:    */   }
/* 191:    */   
/* 192:245 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 193:    */   
/* 194:    */   private static final long[] mk_tokenSet_1()
/* 195:    */   {
/* 196:247 */     long[] arrayOfLong = { 50L, 0L };
/* 197:248 */     return arrayOfLong;
/* 198:    */   }
/* 199:    */   
/* 200:250 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 201:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ANTLRTokdefParser
 * JD-Core Version:    0.7.0.1
 */