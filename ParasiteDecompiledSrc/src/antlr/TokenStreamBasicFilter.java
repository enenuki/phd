/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.ASdebug.ASDebugStream;
/*  4:   */ import antlr.ASdebug.IASDebugStream;
/*  5:   */ import antlr.ASdebug.TokenOffsetInfo;
/*  6:   */ import antlr.collections.impl.BitSet;
/*  7:   */ 
/*  8:   */ public class TokenStreamBasicFilter
/*  9:   */   implements TokenStream, IASDebugStream
/* 10:   */ {
/* 11:   */   protected BitSet discardMask;
/* 12:   */   protected TokenStream input;
/* 13:   */   
/* 14:   */   public TokenStreamBasicFilter(TokenStream paramTokenStream)
/* 15:   */   {
/* 16:27 */     this.input = paramTokenStream;
/* 17:28 */     this.discardMask = new BitSet();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void discard(int paramInt)
/* 21:   */   {
/* 22:32 */     this.discardMask.add(paramInt);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void discard(BitSet paramBitSet)
/* 26:   */   {
/* 27:36 */     this.discardMask = paramBitSet;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Token nextToken()
/* 31:   */     throws TokenStreamException
/* 32:   */   {
/* 33:40 */     Token localToken = this.input.nextToken();
/* 34:41 */     while ((localToken != null) && (this.discardMask.member(localToken.getType()))) {
/* 35:42 */       localToken = this.input.nextToken();
/* 36:   */     }
/* 37:44 */     return localToken;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getEntireText()
/* 41:   */   {
/* 42:49 */     return ASDebugStream.getEntireText(this.input);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public TokenOffsetInfo getOffsetInfo(Token paramToken)
/* 46:   */   {
/* 47:54 */     return ASDebugStream.getOffsetInfo(this.input, paramToken);
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenStreamBasicFilter
 * JD-Core Version:    0.7.0.1
 */