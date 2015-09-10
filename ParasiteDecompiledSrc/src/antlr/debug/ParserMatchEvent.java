/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public class ParserMatchEvent
/*  4:   */   extends GuessingEvent
/*  5:   */ {
/*  6: 6 */   public static int TOKEN = 0;
/*  7: 7 */   public static int BITSET = 1;
/*  8: 8 */   public static int CHAR = 2;
/*  9: 9 */   public static int CHAR_BITSET = 3;
/* 10:10 */   public static int STRING = 4;
/* 11:11 */   public static int CHAR_RANGE = 5;
/* 12:   */   private boolean inverse;
/* 13:   */   private boolean matched;
/* 14:   */   private Object target;
/* 15:   */   private int value;
/* 16:   */   private String text;
/* 17:   */   
/* 18:   */   public ParserMatchEvent(Object paramObject)
/* 19:   */   {
/* 20:20 */     super(paramObject);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ParserMatchEvent(Object paramObject1, int paramInt1, int paramInt2, Object paramObject2, String paramString, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/* 24:   */   {
/* 25:25 */     super(paramObject1);
/* 26:26 */     setValues(paramInt1, paramInt2, paramObject2, paramString, paramInt3, paramBoolean1, paramBoolean2);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Object getTarget()
/* 30:   */   {
/* 31:29 */     return this.target;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getText()
/* 35:   */   {
/* 36:32 */     return this.text;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int getValue()
/* 40:   */   {
/* 41:35 */     return this.value;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean isInverse()
/* 45:   */   {
/* 46:38 */     return this.inverse;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean isMatched()
/* 50:   */   {
/* 51:41 */     return this.matched;
/* 52:   */   }
/* 53:   */   
/* 54:   */   void setInverse(boolean paramBoolean)
/* 55:   */   {
/* 56:44 */     this.inverse = paramBoolean;
/* 57:   */   }
/* 58:   */   
/* 59:   */   void setMatched(boolean paramBoolean)
/* 60:   */   {
/* 61:47 */     this.matched = paramBoolean;
/* 62:   */   }
/* 63:   */   
/* 64:   */   void setTarget(Object paramObject)
/* 65:   */   {
/* 66:50 */     this.target = paramObject;
/* 67:   */   }
/* 68:   */   
/* 69:   */   void setText(String paramString)
/* 70:   */   {
/* 71:53 */     this.text = paramString;
/* 72:   */   }
/* 73:   */   
/* 74:   */   void setValue(int paramInt)
/* 75:   */   {
/* 76:56 */     this.value = paramInt;
/* 77:   */   }
/* 78:   */   
/* 79:   */   void setValues(int paramInt1, int paramInt2, Object paramObject, String paramString, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/* 80:   */   {
/* 81:60 */     super.setValues(paramInt1, paramInt3);
/* 82:61 */     setValue(paramInt2);
/* 83:62 */     setTarget(paramObject);
/* 84:63 */     setInverse(paramBoolean1);
/* 85:64 */     setMatched(paramBoolean2);
/* 86:65 */     setText(paramString);
/* 87:   */   }
/* 88:   */   
/* 89:   */   public String toString()
/* 90:   */   {
/* 91:68 */     return "ParserMatchEvent [" + (isMatched() ? "ok," : "bad,") + (isInverse() ? "NOT " : "") + (getType() == TOKEN ? "token," : "bitset,") + getValue() + "," + getTarget() + "," + getGuessing() + "]";
/* 92:   */   }
/* 93:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParserMatchEvent
 * JD-Core Version:    0.7.0.1
 */