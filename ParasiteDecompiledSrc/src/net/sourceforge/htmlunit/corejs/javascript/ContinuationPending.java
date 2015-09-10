/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ public class ContinuationPending
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 4956008116771118856L;
/*  7:   */   private NativeContinuation continuationState;
/*  8:   */   private Object applicationState;
/*  9:   */   
/* 10:   */   ContinuationPending(NativeContinuation continuationState)
/* 11:   */   {
/* 12:64 */     this.continuationState = continuationState;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Object getContinuation()
/* 16:   */   {
/* 17:74 */     return this.continuationState;
/* 18:   */   }
/* 19:   */   
/* 20:   */   NativeContinuation getContinuationState()
/* 21:   */   {
/* 22:81 */     return this.continuationState;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setApplicationState(Object applicationState)
/* 26:   */   {
/* 27:90 */     this.applicationState = applicationState;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Object getApplicationState()
/* 31:   */   {
/* 32:97 */     return this.applicationState;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ContinuationPending
 * JD-Core Version:    0.7.0.1
 */