/*  1:   */ package org.apache.http.params;
/*  2:   */ 
/*  3:   */ public abstract class HttpAbstractParamBean
/*  4:   */ {
/*  5:   */   protected final HttpParams params;
/*  6:   */   
/*  7:   */   public HttpAbstractParamBean(HttpParams params)
/*  8:   */   {
/*  9:38 */     if (params == null) {
/* 10:39 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 11:   */     }
/* 12:40 */     this.params = params;
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.HttpAbstractParamBean
 * JD-Core Version:    0.7.0.1
 */