/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ObjectDeletedException
/*  6:   */   extends UnresolvableObjectException
/*  7:   */ {
/*  8:   */   public ObjectDeletedException(String message, Serializable identifier, String clazz)
/*  9:   */   {
/* 10:37 */     super(message, identifier, clazz);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.ObjectDeletedException
 * JD-Core Version:    0.7.0.1
 */