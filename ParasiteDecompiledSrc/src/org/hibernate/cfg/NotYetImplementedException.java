/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ 
/*  5:   */ public class NotYetImplementedException
/*  6:   */   extends MappingException
/*  7:   */ {
/*  8:   */   public NotYetImplementedException()
/*  9:   */   {
/* 10:34 */     this("Not yet implemented!");
/* 11:   */   }
/* 12:   */   
/* 13:   */   public NotYetImplementedException(String msg, Throwable root)
/* 14:   */   {
/* 15:38 */     super(msg, root);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public NotYetImplementedException(Throwable root)
/* 19:   */   {
/* 20:42 */     super(root);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public NotYetImplementedException(String s)
/* 24:   */   {
/* 25:46 */     super(s);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.NotYetImplementedException
 * JD-Core Version:    0.7.0.1
 */