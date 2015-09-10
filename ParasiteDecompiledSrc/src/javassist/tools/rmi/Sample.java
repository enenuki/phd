/*  1:   */ package javassist.tools.rmi;
/*  2:   */ 
/*  3:   */ public class Sample
/*  4:   */ {
/*  5:   */   private ObjectImporter importer;
/*  6:   */   private int objectId;
/*  7:   */   
/*  8:   */   public Object forward(Object[] args, int identifier)
/*  9:   */   {
/* 10:28 */     return this.importer.call(this.objectId, identifier, args);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static Object forwardStatic(Object[] args, int identifier)
/* 14:   */     throws RemoteException
/* 15:   */   {
/* 16:34 */     throw new RemoteException("cannot call a static method.");
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.rmi.Sample
 * JD-Core Version:    0.7.0.1
 */