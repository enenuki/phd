/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import org.hibernate.AnnotationException;
/*  4:   */ 
/*  5:   */ public class RecoverableException
/*  6:   */   extends AnnotationException
/*  7:   */ {
/*  8:   */   public RecoverableException(String msg, Throwable root)
/*  9:   */   {
/* 10:36 */     super(msg, root);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public RecoverableException(Throwable root)
/* 14:   */   {
/* 15:40 */     super(root);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public RecoverableException(String s)
/* 19:   */   {
/* 20:44 */     super(s);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.RecoverableException
 * JD-Core Version:    0.7.0.1
 */