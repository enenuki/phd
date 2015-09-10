/*  1:   */ package org.hibernate.internal.jaxb;
/*  2:   */ 
/*  3:   */ public class JaxbRoot<T>
/*  4:   */ {
/*  5:   */   private final T root;
/*  6:   */   private final Origin origin;
/*  7:   */   
/*  8:   */   public JaxbRoot(T root, Origin origin)
/*  9:   */   {
/* 10:37 */     this.root = root;
/* 11:38 */     this.origin = origin;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public T getRoot()
/* 15:   */   {
/* 16:47 */     return this.root;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Origin getOrigin()
/* 20:   */   {
/* 21:56 */     return this.origin;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.JaxbRoot
 * JD-Core Version:    0.7.0.1
 */