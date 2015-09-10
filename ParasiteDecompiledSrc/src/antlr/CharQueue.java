/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class CharQueue
/*  4:   */ {
/*  5:   */   protected char[] buffer;
/*  6:   */   private int sizeLessOne;
/*  7:   */   private int offset;
/*  8:   */   protected int nbrEntries;
/*  9:   */   
/* 10:   */   public CharQueue(int paramInt)
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
/* 23:35 */     init(i);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final void append(char paramChar)
/* 27:   */   {
/* 28:42 */     if (this.nbrEntries == this.buffer.length) {
/* 29:43 */       expand();
/* 30:   */     }
/* 31:45 */     this.buffer[(this.offset + this.nbrEntries & this.sizeLessOne)] = paramChar;
/* 32:46 */     this.nbrEntries += 1;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public final char elementAt(int paramInt)
/* 36:   */   {
/* 37:53 */     return this.buffer[(this.offset + paramInt & this.sizeLessOne)];
/* 38:   */   }
/* 39:   */   
/* 40:   */   private final void expand()
/* 41:   */   {
/* 42:58 */     char[] arrayOfChar = new char[this.buffer.length * 2];
/* 43:62 */     for (int i = 0; i < this.buffer.length; i++) {
/* 44:63 */       arrayOfChar[i] = elementAt(i);
/* 45:   */     }
/* 46:66 */     this.buffer = arrayOfChar;
/* 47:67 */     this.sizeLessOne = (this.buffer.length - 1);
/* 48:68 */     this.offset = 0;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void init(int paramInt)
/* 52:   */   {
/* 53:76 */     this.buffer = new char[paramInt];
/* 54:   */     
/* 55:78 */     this.sizeLessOne = (paramInt - 1);
/* 56:79 */     this.offset = 0;
/* 57:80 */     this.nbrEntries = 0;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public final void reset()
/* 61:   */   {
/* 62:86 */     this.offset = 0;
/* 63:87 */     this.nbrEntries = 0;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public final void removeFirst()
/* 67:   */   {
/* 68:92 */     this.offset = (this.offset + 1 & this.sizeLessOne);
/* 69:93 */     this.nbrEntries -= 1;
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CharQueue
 * JD-Core Version:    0.7.0.1
 */