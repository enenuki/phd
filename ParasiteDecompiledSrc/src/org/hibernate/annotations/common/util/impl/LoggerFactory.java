/*  1:   */ package org.hibernate.annotations.common.util.impl;
/*  2:   */ 
/*  3:   */ import org.jboss.logging.Logger;
/*  4:   */ 
/*  5:   */ public class LoggerFactory
/*  6:   */ {
/*  7:   */   public static Log make(String category)
/*  8:   */   {
/*  9:34 */     return (Log)Logger.getMessageLogger(Log.class, category);
/* 10:   */   }
/* 11:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.util.impl.LoggerFactory
 * JD-Core Version:    0.7.0.1
 */