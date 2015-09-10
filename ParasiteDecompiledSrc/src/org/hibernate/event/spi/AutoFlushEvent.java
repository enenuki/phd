/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ 
/*  5:   */ public class AutoFlushEvent
/*  6:   */   extends FlushEvent
/*  7:   */ {
/*  8:   */   private Set querySpaces;
/*  9:   */   private boolean flushRequired;
/* 10:   */   
/* 11:   */   public AutoFlushEvent(Set querySpaces, EventSource source)
/* 12:   */   {
/* 13:38 */     super(source);
/* 14:39 */     this.querySpaces = querySpaces;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Set getQuerySpaces()
/* 18:   */   {
/* 19:43 */     return this.querySpaces;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setQuerySpaces(Set querySpaces)
/* 23:   */   {
/* 24:47 */     this.querySpaces = querySpaces;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isFlushRequired()
/* 28:   */   {
/* 29:51 */     return this.flushRequired;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setFlushRequired(boolean dirty)
/* 33:   */   {
/* 34:55 */     this.flushRequired = dirty;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.AutoFlushEvent
 * JD-Core Version:    0.7.0.1
 */