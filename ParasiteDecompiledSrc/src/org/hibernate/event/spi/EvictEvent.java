/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ public class EvictEvent
/*  4:   */   extends AbstractEvent
/*  5:   */ {
/*  6:   */   private Object object;
/*  7:   */   
/*  8:   */   public EvictEvent(Object object, EventSource source)
/*  9:   */   {
/* 10:36 */     super(source);
/* 11:37 */     this.object = object;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Object getObject()
/* 15:   */   {
/* 16:41 */     return this.object;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setObject(Object object)
/* 20:   */   {
/* 21:45 */     this.object = object;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.EvictEvent
 * JD-Core Version:    0.7.0.1
 */