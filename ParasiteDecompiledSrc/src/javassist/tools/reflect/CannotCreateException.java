/*  1:   */ package javassist.tools.reflect;
/*  2:   */ 
/*  3:   */ public class CannotCreateException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   public CannotCreateException(String s)
/*  7:   */   {
/*  8:23 */     super(s);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public CannotCreateException(Exception e)
/* 12:   */   {
/* 13:27 */     super("by " + e.toString());
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.CannotCreateException
 * JD-Core Version:    0.7.0.1
 */