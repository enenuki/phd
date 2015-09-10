/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ 
/*   5:    */ public class TokenStreamHiddenTokenFilter
/*   6:    */   extends TokenStreamBasicFilter
/*   7:    */   implements TokenStream
/*   8:    */ {
/*   9:    */   protected BitSet hideMask;
/*  10:    */   protected CommonHiddenStreamToken nextMonitoredToken;
/*  11:    */   protected CommonHiddenStreamToken lastHiddenToken;
/*  12: 30 */   protected CommonHiddenStreamToken firstHidden = null;
/*  13:    */   
/*  14:    */   public TokenStreamHiddenTokenFilter(TokenStream paramTokenStream)
/*  15:    */   {
/*  16: 33 */     super(paramTokenStream);
/*  17: 34 */     this.hideMask = new BitSet();
/*  18:    */   }
/*  19:    */   
/*  20:    */   protected void consume()
/*  21:    */     throws TokenStreamException
/*  22:    */   {
/*  23: 38 */     this.nextMonitoredToken = ((CommonHiddenStreamToken)this.input.nextToken());
/*  24:    */   }
/*  25:    */   
/*  26:    */   private void consumeFirst()
/*  27:    */     throws TokenStreamException
/*  28:    */   {
/*  29: 42 */     consume();
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33: 46 */     CommonHiddenStreamToken localCommonHiddenStreamToken = null;
/*  34: 48 */     while ((this.hideMask.member(LA(1).getType())) || (this.discardMask.member(LA(1).getType())))
/*  35:    */     {
/*  36: 49 */       if (this.hideMask.member(LA(1).getType()))
/*  37:    */       {
/*  38: 50 */         if (localCommonHiddenStreamToken == null)
/*  39:    */         {
/*  40: 51 */           localCommonHiddenStreamToken = LA(1);
/*  41:    */         }
/*  42:    */         else
/*  43:    */         {
/*  44: 54 */           localCommonHiddenStreamToken.setHiddenAfter(LA(1));
/*  45: 55 */           LA(1).setHiddenBefore(localCommonHiddenStreamToken);
/*  46: 56 */           localCommonHiddenStreamToken = LA(1);
/*  47:    */         }
/*  48: 58 */         this.lastHiddenToken = localCommonHiddenStreamToken;
/*  49: 59 */         if (this.firstHidden == null) {
/*  50: 60 */           this.firstHidden = localCommonHiddenStreamToken;
/*  51:    */         }
/*  52:    */       }
/*  53: 63 */       consume();
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public BitSet getDiscardMask()
/*  58:    */   {
/*  59: 68 */     return this.discardMask;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public CommonHiddenStreamToken getHiddenAfter(CommonHiddenStreamToken paramCommonHiddenStreamToken)
/*  63:    */   {
/*  64: 75 */     return paramCommonHiddenStreamToken.getHiddenAfter();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public CommonHiddenStreamToken getHiddenBefore(CommonHiddenStreamToken paramCommonHiddenStreamToken)
/*  68:    */   {
/*  69: 82 */     return paramCommonHiddenStreamToken.getHiddenBefore();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public BitSet getHideMask()
/*  73:    */   {
/*  74: 86 */     return this.hideMask;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public CommonHiddenStreamToken getInitialHiddenToken()
/*  78:    */   {
/*  79: 93 */     return this.firstHidden;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void hide(int paramInt)
/*  83:    */   {
/*  84: 97 */     this.hideMask.add(paramInt);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void hide(BitSet paramBitSet)
/*  88:    */   {
/*  89:101 */     this.hideMask = paramBitSet;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected CommonHiddenStreamToken LA(int paramInt)
/*  93:    */   {
/*  94:105 */     return this.nextMonitoredToken;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Token nextToken()
/*  98:    */     throws TokenStreamException
/*  99:    */   {
/* 100:121 */     if (LA(1) == null) {
/* 101:122 */       consumeFirst();
/* 102:    */     }
/* 103:127 */     CommonHiddenStreamToken localCommonHiddenStreamToken1 = LA(1);
/* 104:    */     
/* 105:129 */     localCommonHiddenStreamToken1.setHiddenBefore(this.lastHiddenToken);
/* 106:130 */     this.lastHiddenToken = null;
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:134 */     consume();
/* 111:135 */     CommonHiddenStreamToken localCommonHiddenStreamToken2 = localCommonHiddenStreamToken1;
/* 112:137 */     while ((this.hideMask.member(LA(1).getType())) || (this.discardMask.member(LA(1).getType())))
/* 113:    */     {
/* 114:138 */       if (this.hideMask.member(LA(1).getType()))
/* 115:    */       {
/* 116:141 */         localCommonHiddenStreamToken2.setHiddenAfter(LA(1));
/* 117:143 */         if (localCommonHiddenStreamToken2 != localCommonHiddenStreamToken1) {
/* 118:144 */           LA(1).setHiddenBefore(localCommonHiddenStreamToken2);
/* 119:    */         }
/* 120:146 */         localCommonHiddenStreamToken2 = this.lastHiddenToken = LA(1);
/* 121:    */       }
/* 122:148 */       consume();
/* 123:    */     }
/* 124:150 */     return localCommonHiddenStreamToken1;
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenStreamHiddenTokenFilter
 * JD-Core Version:    0.7.0.1
 */