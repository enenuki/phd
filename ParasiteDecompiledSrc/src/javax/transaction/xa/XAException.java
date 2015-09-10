/*  1:   */ package javax.transaction.xa;
/*  2:   */ 
/*  3:   */ public class XAException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:34 */   public int errorCode = 0;
/*  7:   */   public static final int XAER_ASYNC = -2;
/*  8:   */   public static final int XAER_RMERR = -3;
/*  9:   */   public static final int XAER_NOTA = -4;
/* 10:   */   public static final int XAER_INVAL = -5;
/* 11:   */   public static final int XAER_PROTO = -6;
/* 12:   */   public static final int XAER_RMFAIL = -7;
/* 13:   */   public static final int XAER_DUPID = -8;
/* 14:   */   public static final int XAER_OUTSIDE = -9;
/* 15:   */   public static final int XA_RDONLY = 3;
/* 16:   */   public static final int XA_RETRY = 4;
/* 17:   */   public static final int XA_HEURMIX = 5;
/* 18:   */   public static final int XA_HEURRB = 6;
/* 19:   */   public static final int XA_HEURCOM = 7;
/* 20:   */   public static final int XA_HEURHAZ = 8;
/* 21:   */   public static final int XA_NOMIGRATE = 9;
/* 22:   */   public static final int XA_RBBASE = 100;
/* 23:   */   public static final int XA_RBROLLBACK = 100;
/* 24:   */   public static final int XA_RBCOMMFAIL = 101;
/* 25:   */   public static final int XA_RBDEADLOCK = 102;
/* 26:   */   public static final int XA_RBINTEGRITY = 103;
/* 27:   */   public static final int XA_RBOTHER = 104;
/* 28:   */   public static final int XA_RBPROTO = 105;
/* 29:   */   public static final int XA_RBTIMEOUT = 106;
/* 30:   */   public static final int XA_RBTRANSIENT = 107;
/* 31:   */   public static final int XA_RBEND = 107;
/* 32:   */   
/* 33:   */   public XAException() {}
/* 34:   */   
/* 35:   */   public XAException(String msg)
/* 36:   */   {
/* 37:49 */     super(msg);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public XAException(int errorCode)
/* 41:   */   {
/* 42:59 */     this.errorCode = errorCode;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.xa.XAException
 * JD-Core Version:    0.7.0.1
 */