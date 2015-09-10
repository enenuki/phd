/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public class InputBufferEvent
/*  4:   */   extends Event
/*  5:   */ {
/*  6:   */   char c;
/*  7:   */   int lookaheadAmount;
/*  8:   */   public static final int CONSUME = 0;
/*  9:   */   public static final int LA = 1;
/* 10:   */   public static final int MARK = 2;
/* 11:   */   public static final int REWIND = 3;
/* 12:   */   
/* 13:   */   public InputBufferEvent(Object paramObject)
/* 14:   */   {
/* 15:17 */     super(paramObject);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public InputBufferEvent(Object paramObject, int paramInt1, char paramChar, int paramInt2)
/* 19:   */   {
/* 20:24 */     super(paramObject);
/* 21:25 */     setValues(paramInt1, paramChar, paramInt2);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public char getChar()
/* 25:   */   {
/* 26:28 */     return this.c;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getLookaheadAmount()
/* 30:   */   {
/* 31:31 */     return this.lookaheadAmount;
/* 32:   */   }
/* 33:   */   
/* 34:   */   void setChar(char paramChar)
/* 35:   */   {
/* 36:34 */     this.c = paramChar;
/* 37:   */   }
/* 38:   */   
/* 39:   */   void setLookaheadAmount(int paramInt)
/* 40:   */   {
/* 41:37 */     this.lookaheadAmount = paramInt;
/* 42:   */   }
/* 43:   */   
/* 44:   */   void setValues(int paramInt1, char paramChar, int paramInt2)
/* 45:   */   {
/* 46:41 */     super.setValues(paramInt1);
/* 47:42 */     setChar(paramChar);
/* 48:43 */     setLookaheadAmount(paramInt2);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String toString()
/* 52:   */   {
/* 53:46 */     return "CharBufferEvent [" + (getType() == 0 ? "CONSUME, " : "LA, ") + getChar() + "," + getLookaheadAmount() + "]";
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.InputBufferEvent
 * JD-Core Version:    0.7.0.1
 */