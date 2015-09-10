/*  1:   */ package javassist;
/*  2:   */ 
/*  3:   */ public class NotFoundException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   public NotFoundException(String msg)
/*  7:   */   {
/*  8:23 */     super(msg);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public NotFoundException(String msg, Exception e)
/* 12:   */   {
/* 13:27 */     super(msg + " because of " + e.toString());
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.NotFoundException
 * JD-Core Version:    0.7.0.1
 */