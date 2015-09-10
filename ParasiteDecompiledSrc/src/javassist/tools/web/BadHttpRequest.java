/*  1:   */ package javassist.tools.web;
/*  2:   */ 
/*  3:   */ public class BadHttpRequest
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   private Exception e;
/*  7:   */   
/*  8:   */   public BadHttpRequest()
/*  9:   */   {
/* 10:24 */     this.e = null;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public BadHttpRequest(Exception _e)
/* 14:   */   {
/* 15:26 */     this.e = _e;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toString()
/* 19:   */   {
/* 20:29 */     if (this.e == null) {
/* 21:30 */       return super.toString();
/* 22:   */     }
/* 23:32 */     return this.e.toString();
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.web.BadHttpRequest
 * JD-Core Version:    0.7.0.1
 */