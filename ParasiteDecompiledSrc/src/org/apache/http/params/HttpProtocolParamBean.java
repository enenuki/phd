/*  1:   */ package org.apache.http.params;
/*  2:   */ 
/*  3:   */ import org.apache.http.HttpVersion;
/*  4:   */ 
/*  5:   */ public class HttpProtocolParamBean
/*  6:   */   extends HttpAbstractParamBean
/*  7:   */ {
/*  8:   */   public HttpProtocolParamBean(HttpParams params)
/*  9:   */   {
/* 10:42 */     super(params);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void setHttpElementCharset(String httpElementCharset)
/* 14:   */   {
/* 15:46 */     HttpProtocolParams.setHttpElementCharset(this.params, httpElementCharset);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setContentCharset(String contentCharset)
/* 19:   */   {
/* 20:50 */     HttpProtocolParams.setContentCharset(this.params, contentCharset);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setVersion(HttpVersion version)
/* 24:   */   {
/* 25:54 */     HttpProtocolParams.setVersion(this.params, version);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setUserAgent(String userAgent)
/* 29:   */   {
/* 30:58 */     HttpProtocolParams.setUserAgent(this.params, userAgent);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setUseExpectContinue(boolean useExpectContinue)
/* 34:   */   {
/* 35:62 */     HttpProtocolParams.setUseExpectContinue(this.params, useExpectContinue);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.HttpProtocolParamBean
 * JD-Core Version:    0.7.0.1
 */