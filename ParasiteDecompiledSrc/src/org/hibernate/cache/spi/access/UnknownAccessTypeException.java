/*  1:   */ package org.hibernate.cache.spi.access;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class UnknownAccessTypeException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public UnknownAccessTypeException(String accessTypeName)
/*  9:   */   {
/* 10:33 */     super("Unknown access type [" + accessTypeName + "]");
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.access.UnknownAccessTypeException
 * JD-Core Version:    0.7.0.1
 */