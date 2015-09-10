/*  1:   */ package org.hibernate.persister.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class UnknownPersisterException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public UnknownPersisterException(String s)
/*  9:   */   {
/* 10:35 */     super(s);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public UnknownPersisterException(String string, Throwable root)
/* 14:   */   {
/* 15:39 */     super(string, root);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.spi.UnknownPersisterException
 * JD-Core Version:    0.7.0.1
 */