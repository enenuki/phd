/*  1:   */ package org.apache.http.params;
/*  2:   */ 
/*  3:   */ public class HttpConnectionParamBean
/*  4:   */   extends HttpAbstractParamBean
/*  5:   */ {
/*  6:   */   public HttpConnectionParamBean(HttpParams params)
/*  7:   */   {
/*  8:40 */     super(params);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void setSoTimeout(int soTimeout)
/* 12:   */   {
/* 13:44 */     HttpConnectionParams.setSoTimeout(this.params, soTimeout);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setTcpNoDelay(boolean tcpNoDelay)
/* 17:   */   {
/* 18:48 */     HttpConnectionParams.setTcpNoDelay(this.params, tcpNoDelay);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setSocketBufferSize(int socketBufferSize)
/* 22:   */   {
/* 23:52 */     HttpConnectionParams.setSocketBufferSize(this.params, socketBufferSize);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setLinger(int linger)
/* 27:   */   {
/* 28:56 */     HttpConnectionParams.setLinger(this.params, linger);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setConnectionTimeout(int connectionTimeout)
/* 32:   */   {
/* 33:60 */     HttpConnectionParams.setConnectionTimeout(this.params, connectionTimeout);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setStaleCheckingEnabled(boolean staleCheckingEnabled)
/* 37:   */   {
/* 38:64 */     HttpConnectionParams.setStaleCheckingEnabled(this.params, staleCheckingEnabled);
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.HttpConnectionParamBean
 * JD-Core Version:    0.7.0.1
 */