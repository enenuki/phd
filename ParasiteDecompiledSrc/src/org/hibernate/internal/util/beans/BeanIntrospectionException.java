/*  1:   */ package org.hibernate.internal.util.beans;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class BeanIntrospectionException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public BeanIntrospectionException(String string, Throwable root)
/*  9:   */   {
/* 10:36 */     super(string, root);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public BeanIntrospectionException(String s)
/* 14:   */   {
/* 15:40 */     super(s);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.beans.BeanIntrospectionException
 * JD-Core Version:    0.7.0.1
 */