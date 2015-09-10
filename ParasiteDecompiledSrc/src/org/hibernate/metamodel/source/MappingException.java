/*  1:   */ package org.hibernate.metamodel.source;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ import org.hibernate.internal.jaxb.Origin;
/*  5:   */ 
/*  6:   */ public class MappingException
/*  7:   */   extends HibernateException
/*  8:   */ {
/*  9:   */   private final Origin origin;
/* 10:   */   
/* 11:   */   public MappingException(String message, Origin origin)
/* 12:   */   {
/* 13:39 */     super(message);
/* 14:40 */     this.origin = origin;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public MappingException(String message, Throwable root, Origin origin)
/* 18:   */   {
/* 19:44 */     super(message, root);
/* 20:45 */     this.origin = origin;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Origin getOrigin()
/* 24:   */   {
/* 25:49 */     return this.origin;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.MappingException
 * JD-Core Version:    0.7.0.1
 */