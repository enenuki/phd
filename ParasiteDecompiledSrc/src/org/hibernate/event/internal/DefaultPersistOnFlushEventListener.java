/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.CascadingAction;
/*  4:   */ 
/*  5:   */ public class DefaultPersistOnFlushEventListener
/*  6:   */   extends DefaultPersistEventListener
/*  7:   */ {
/*  8:   */   protected CascadingAction getCascadeAction()
/*  9:   */   {
/* 10:34 */     return CascadingAction.PERSIST_ON_FLUSH;
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultPersistOnFlushEventListener
 * JD-Core Version:    0.7.0.1
 */