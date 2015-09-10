/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class TokenQueue
/*  4:   */ {
/*  5:   */   private Token[] buffer;
/*  6:   */   private int sizeLessOne;
/*  7:   */   private int offset;
/*  8:   */   protected int nbrEntries;
/*  9:   */   
/* 10:   */   public TokenQueue(int paramInt)
/* 11:   */   {
/* 12:24 */     if (paramInt < 0)
/* 13:   */     {
/* 14:25 */       init(16);
/* 15:26 */       return;
/* 16:   */     }
/* 17:29 */     if (paramInt >= 1073741823)
/* 18:   */     {
/* 19:30 */       init(2147483647);
/* 20:31 */       return;
/* 21:   */     }
/* 22:33 */     for (int i = 2; i < paramInt; i *= 2) {}
/* 23:36 */     init(i);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final void append(Token paramToken)
/* 27:   */   {
/* 28:43 */     if (this.nbrEntries == this.buffer.length) {
/* 29:44 */       expand();
/* 30:   */     }
/* 31:46 */     this.buffer[(this.offset + this.nbrEntries & this.sizeLessOne)] = paramToken;
/* 32:47 */     this.nbrEntries += 1;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public final Token elementAt(int paramInt)
/* 36:   */   {
/* 37:54 */     return this.buffer[(this.offset + paramInt & this.sizeLessOne)];
/* 38:   */   }
/* 39:   */   
/* 40:   */   private final void expand()
/* 41:   */   {
/* 42:59 */     Token[] arrayOfToken = new Token[this.buffer.length * 2];
/* 43:63 */     for (int i = 0; i < this.buffer.length; i++) {
/* 44:64 */       arrayOfToken[i] = elementAt(i);
/* 45:   */     }
/* 46:67 */     this.buffer = arrayOfToken;
/* 47:68 */     this.sizeLessOne = (this.buffer.length - 1);
/* 48:69 */     this.offset = 0;
/* 49:   */   }
/* 50:   */   
/* 51:   */   private final void init(int paramInt)
/* 52:   */   {
/* 53:77 */     this.buffer = new Token[paramInt];
/* 54:   */     
/* 55:79 */     this.sizeLessOne = (paramInt - 1);
/* 56:80 */     this.offset = 0;
/* 57:81 */     this.nbrEntries = 0;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public final void reset()
/* 61:   */   {
/* 62:87 */     this.offset = 0;
/* 63:88 */     this.nbrEntries = 0;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public final void removeFirst()
/* 67:   */   {
/* 68:93 */     this.offset = (this.offset + 1 & this.sizeLessOne);
/* 69:94 */     this.nbrEntries -= 1;
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenQueue
 * JD-Core Version:    0.7.0.1
 */