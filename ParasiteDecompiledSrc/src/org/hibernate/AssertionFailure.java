/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.CoreMessageLogger;
/*  4:   */ import org.jboss.logging.Logger;
/*  5:   */ 
/*  6:   */ public class AssertionFailure
/*  7:   */   extends RuntimeException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:39 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AssertionFailure.class.getName());
/* 11:   */   
/* 12:   */   public AssertionFailure(String s)
/* 13:   */   {
/* 14:42 */     super(s);
/* 15:43 */     LOG.failed(this);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public AssertionFailure(String s, Throwable t)
/* 19:   */   {
/* 20:48 */     super(s, t);
/* 21:49 */     LOG.failed(t);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.AssertionFailure
 * JD-Core Version:    0.7.0.1
 */