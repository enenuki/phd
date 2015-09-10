/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ import java.util.EventObject;
/*  4:   */ 
/*  5:   */ public abstract class Event
/*  6:   */   extends EventObject
/*  7:   */ {
/*  8:   */   private int type;
/*  9:   */   
/* 10:   */   public Event(Object paramObject)
/* 11:   */   {
/* 12:10 */     super(paramObject);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Event(Object paramObject, int paramInt)
/* 16:   */   {
/* 17:13 */     super(paramObject);
/* 18:14 */     setType(paramInt);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getType()
/* 22:   */   {
/* 23:17 */     return this.type;
/* 24:   */   }
/* 25:   */   
/* 26:   */   void setType(int paramInt)
/* 27:   */   {
/* 28:20 */     this.type = paramInt;
/* 29:   */   }
/* 30:   */   
/* 31:   */   void setValues(int paramInt)
/* 32:   */   {
/* 33:24 */     setType(paramInt);
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.Event
 * JD-Core Version:    0.7.0.1
 */