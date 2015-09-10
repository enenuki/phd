/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class AnnotationException
/*  4:   */   extends MappingException
/*  5:   */ {
/*  6:   */   public AnnotationException(String msg, Throwable root)
/*  7:   */   {
/*  8:37 */     super(msg, root);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public AnnotationException(Throwable root)
/* 12:   */   {
/* 13:41 */     super(root);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public AnnotationException(String s)
/* 17:   */   {
/* 18:45 */     super(s);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.AnnotationException
 * JD-Core Version:    0.7.0.1
 */