/*  1:   */ package org.hibernate.id;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class IdentifierGenerationException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public IdentifierGenerationException(String msg)
/*  9:   */   {
/* 10:39 */     super(msg);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public IdentifierGenerationException(String msg, Throwable t)
/* 14:   */   {
/* 15:43 */     super(msg, t);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.IdentifierGenerationException
 * JD-Core Version:    0.7.0.1
 */