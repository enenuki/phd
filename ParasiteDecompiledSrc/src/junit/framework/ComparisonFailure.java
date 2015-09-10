/*  1:   */ package junit.framework;
/*  2:   */ 
/*  3:   */ public class ComparisonFailure
/*  4:   */   extends AssertionFailedError
/*  5:   */ {
/*  6:   */   private static final int MAX_CONTEXT_LENGTH = 20;
/*  7:   */   private static final long serialVersionUID = 1L;
/*  8:   */   private String fExpected;
/*  9:   */   private String fActual;
/* 10:   */   
/* 11:   */   public ComparisonFailure(String message, String expected, String actual)
/* 12:   */   {
/* 13:22 */     super(message);
/* 14:23 */     this.fExpected = expected;
/* 15:24 */     this.fActual = actual;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getMessage()
/* 19:   */   {
/* 20:35 */     return new ComparisonCompactor(20, this.fExpected, this.fActual).compact(super.getMessage());
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getActual()
/* 24:   */   {
/* 25:43 */     return this.fActual;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getExpected()
/* 29:   */   {
/* 30:50 */     return this.fExpected;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.ComparisonFailure
 * JD-Core Version:    0.7.0.1
 */