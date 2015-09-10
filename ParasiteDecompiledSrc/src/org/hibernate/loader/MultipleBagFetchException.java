/*  1:   */ package org.hibernate.loader;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ 
/*  6:   */ public class MultipleBagFetchException
/*  7:   */   extends HibernateException
/*  8:   */ {
/*  9:   */   private final List bagRoles;
/* 10:   */   
/* 11:   */   public MultipleBagFetchException(List bagRoles)
/* 12:   */   {
/* 13:39 */     super("cannot simultaneously fetch multiple bags");
/* 14:40 */     this.bagRoles = bagRoles;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public List getBagRoles()
/* 18:   */   {
/* 19:49 */     return this.bagRoles;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.MultipleBagFetchException
 * JD-Core Version:    0.7.0.1
 */