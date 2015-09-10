/*  1:   */ package javax.transaction;
/*  2:   */ 
/*  3:   */ public class SystemException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   public int errorCode;
/*  7:   */   
/*  8:   */   public SystemException() {}
/*  9:   */   
/* 10:   */   public SystemException(String msg)
/* 11:   */   {
/* 12:54 */     super(msg);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public SystemException(int errcode)
/* 16:   */   {
/* 17:65 */     this.errorCode = errcode;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.SystemException
 * JD-Core Version:    0.7.0.1
 */