/*  1:   */ package javassist.runtime;
/*  2:   */ 
/*  3:   */ public class DotClass
/*  4:   */ {
/*  5:   */   public static NoClassDefFoundError fail(ClassNotFoundException e)
/*  6:   */   {
/*  7:26 */     return new NoClassDefFoundError(e.getMessage());
/*  8:   */   }
/*  9:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.runtime.DotClass
 * JD-Core Version:    0.7.0.1
 */