/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public class MessageEvent
/*  4:   */   extends Event
/*  5:   */ {
/*  6:   */   private String text;
/*  7: 5 */   public static int WARNING = 0;
/*  8: 6 */   public static int ERROR = 1;
/*  9:   */   
/* 10:   */   public MessageEvent(Object paramObject)
/* 11:   */   {
/* 12:10 */     super(paramObject);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public MessageEvent(Object paramObject, int paramInt, String paramString)
/* 16:   */   {
/* 17:13 */     super(paramObject);
/* 18:14 */     setValues(paramInt, paramString);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getText()
/* 22:   */   {
/* 23:17 */     return this.text;
/* 24:   */   }
/* 25:   */   
/* 26:   */   void setText(String paramString)
/* 27:   */   {
/* 28:20 */     this.text = paramString;
/* 29:   */   }
/* 30:   */   
/* 31:   */   void setValues(int paramInt, String paramString)
/* 32:   */   {
/* 33:24 */     super.setValues(paramInt);
/* 34:25 */     setText(paramString);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String toString()
/* 38:   */   {
/* 39:28 */     return "ParserMessageEvent [" + (getType() == WARNING ? "warning," : "error,") + getText() + "]";
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.MessageEvent
 * JD-Core Version:    0.7.0.1
 */