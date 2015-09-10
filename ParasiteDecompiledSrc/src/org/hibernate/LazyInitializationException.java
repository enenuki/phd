/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.CoreMessageLogger;
/*  4:   */ import org.jboss.logging.Logger;
/*  5:   */ 
/*  6:   */ public class LazyInitializationException
/*  7:   */   extends HibernateException
/*  8:   */ {
/*  9:41 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, LazyInitializationException.class.getName());
/* 10:   */   
/* 11:   */   public LazyInitializationException(String msg)
/* 12:   */   {
/* 13:44 */     super(msg);
/* 14:45 */     LOG.trace(msg, this);
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.LazyInitializationException
 * JD-Core Version:    0.7.0.1
 */