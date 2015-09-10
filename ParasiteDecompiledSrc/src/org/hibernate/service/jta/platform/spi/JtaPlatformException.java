/*  1:   */ package org.hibernate.service.jta.platform.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class JtaPlatformException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public JtaPlatformException(String s)
/*  9:   */   {
/* 10:35 */     super(s);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public JtaPlatformException(String string, Throwable root)
/* 14:   */   {
/* 15:39 */     super(string, root);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.spi.JtaPlatformException
 * JD-Core Version:    0.7.0.1
 */