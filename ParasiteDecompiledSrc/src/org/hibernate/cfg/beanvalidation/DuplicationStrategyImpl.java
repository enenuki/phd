/*  1:   */ package org.hibernate.cfg.beanvalidation;
/*  2:   */ 
/*  3:   */ import org.hibernate.event.service.spi.DuplicationStrategy;
/*  4:   */ import org.hibernate.event.service.spi.DuplicationStrategy.Action;
/*  5:   */ 
/*  6:   */ public class DuplicationStrategyImpl
/*  7:   */   implements DuplicationStrategy
/*  8:   */ {
/*  9:32 */   public static final DuplicationStrategyImpl INSTANCE = new DuplicationStrategyImpl();
/* 10:   */   
/* 11:   */   public boolean areMatch(Object listener, Object original)
/* 12:   */   {
/* 13:36 */     return (listener.getClass().equals(original.getClass())) && (BeanValidationEventListener.class.equals(listener.getClass()));
/* 14:   */   }
/* 15:   */   
/* 16:   */   public DuplicationStrategy.Action getAction()
/* 17:   */   {
/* 18:42 */     return DuplicationStrategy.Action.KEEP_ORIGINAL;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.beanvalidation.DuplicationStrategyImpl
 * JD-Core Version:    0.7.0.1
 */