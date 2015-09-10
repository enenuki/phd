/*   1:    */ package javax.persistence;
/*   2:    */ 
/*   3:    */ public class QueryTimeoutException
/*   4:    */   extends PersistenceException
/*   5:    */ {
/*   6:    */   Query query;
/*   7:    */   
/*   8:    */   public QueryTimeoutException() {}
/*   9:    */   
/*  10:    */   public QueryTimeoutException(String message)
/*  11:    */   {
/*  12: 49 */     super(message);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public QueryTimeoutException(String message, Throwable cause)
/*  16:    */   {
/*  17: 60 */     super(message, cause);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public QueryTimeoutException(Throwable cause)
/*  21:    */   {
/*  22: 70 */     super(cause);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public QueryTimeoutException(Query query)
/*  26:    */   {
/*  27: 81 */     this.query = query;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public QueryTimeoutException(String message, Throwable cause, Query query)
/*  31:    */   {
/*  32: 93 */     super(message, cause);
/*  33: 94 */     this.query = query;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Query getQuery()
/*  37:    */   {
/*  38:103 */     return this.query;
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.QueryTimeoutException
 * JD-Core Version:    0.7.0.1
 */