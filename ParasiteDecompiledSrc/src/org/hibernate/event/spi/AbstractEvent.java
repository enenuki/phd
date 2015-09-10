/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public abstract class AbstractEvent
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private final EventSource session;
/*  9:   */   
/* 10:   */   public AbstractEvent(EventSource source)
/* 11:   */   {
/* 12:43 */     this.session = source;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public final EventSource getSession()
/* 16:   */   {
/* 17:53 */     return this.session;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.AbstractEvent
 * JD-Core Version:    0.7.0.1
 */