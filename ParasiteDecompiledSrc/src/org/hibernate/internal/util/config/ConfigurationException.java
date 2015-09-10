/*  1:   */ package org.hibernate.internal.util.config;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class ConfigurationException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public ConfigurationException(String string, Throwable root)
/*  9:   */   {
/* 10:34 */     super(string, root);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ConfigurationException(String s)
/* 14:   */   {
/* 15:38 */     super(s);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.config.ConfigurationException
 * JD-Core Version:    0.7.0.1
 */