/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class SerializationException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public SerializationException(String message, Exception root)
/*  9:   */   {
/* 10:34 */     super(message, root);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.SerializationException
 * JD-Core Version:    0.7.0.1
 */