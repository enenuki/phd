/*  1:   */ package org.hibernate.annotations.common;
/*  2:   */ 
/*  3:   */ import org.hibernate.annotations.common.util.impl.Log;
/*  4:   */ import org.hibernate.annotations.common.util.impl.LoggerFactory;
/*  5:   */ 
/*  6:   */ public class AssertionFailure
/*  7:   */   extends RuntimeException
/*  8:   */ {
/*  9:38 */   private static final Log log = LoggerFactory.make(AssertionFailure.class.getName());
/* 10:   */   
/* 11:   */   public AssertionFailure(String s)
/* 12:   */   {
/* 13:41 */     super(s);
/* 14:42 */     log.assertionFailure(this);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public AssertionFailure(String s, Throwable t)
/* 18:   */   {
/* 19:46 */     super(s, t);
/* 20:47 */     log.assertionFailure(this);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.AssertionFailure
 * JD-Core Version:    0.7.0.1
 */