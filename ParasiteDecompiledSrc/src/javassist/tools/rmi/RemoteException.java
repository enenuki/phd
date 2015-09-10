/*  1:   */ package javassist.tools.rmi;
/*  2:   */ 
/*  3:   */ public class RemoteException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public RemoteException(String msg)
/*  7:   */   {
/*  8:24 */     super(msg);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public RemoteException(Exception e)
/* 12:   */   {
/* 13:28 */     super("by " + e.toString());
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.rmi.RemoteException
 * JD-Core Version:    0.7.0.1
 */