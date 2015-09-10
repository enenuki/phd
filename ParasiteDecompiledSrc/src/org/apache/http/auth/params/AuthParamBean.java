/*  1:   */ package org.apache.http.auth.params;
/*  2:   */ 
/*  3:   */ import org.apache.http.params.HttpAbstractParamBean;
/*  4:   */ import org.apache.http.params.HttpParams;
/*  5:   */ 
/*  6:   */ public class AuthParamBean
/*  7:   */   extends HttpAbstractParamBean
/*  8:   */ {
/*  9:   */   public AuthParamBean(HttpParams params)
/* 10:   */   {
/* 11:43 */     super(params);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void setCredentialCharset(String charset)
/* 15:   */   {
/* 16:47 */     AuthParams.setCredentialCharset(this.params, charset);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.params.AuthParamBean
 * JD-Core Version:    0.7.0.1
 */