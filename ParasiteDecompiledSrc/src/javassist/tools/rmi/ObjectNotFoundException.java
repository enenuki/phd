/*  1:   */ package javassist.tools.rmi;
/*  2:   */ 
/*  3:   */ public class ObjectNotFoundException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   public ObjectNotFoundException(String name)
/*  7:   */   {
/*  8:20 */     super(name + " is not exported");
/*  9:   */   }
/* 10:   */   
/* 11:   */   public ObjectNotFoundException(String name, Exception e)
/* 12:   */   {
/* 13:24 */     super(name + " because of " + e.toString());
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.rmi.ObjectNotFoundException
 * JD-Core Version:    0.7.0.1
 */