/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ public class DirtyCheckEvent
/*  4:   */   extends FlushEvent
/*  5:   */ {
/*  6:   */   private boolean dirty;
/*  7:   */   
/*  8:   */   public DirtyCheckEvent(EventSource source)
/*  9:   */   {
/* 10:35 */     super(source);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean isDirty()
/* 14:   */   {
/* 15:39 */     return this.dirty;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setDirty(boolean dirty)
/* 19:   */   {
/* 20:43 */     this.dirty = dirty;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.DirtyCheckEvent
 * JD-Core Version:    0.7.0.1
 */