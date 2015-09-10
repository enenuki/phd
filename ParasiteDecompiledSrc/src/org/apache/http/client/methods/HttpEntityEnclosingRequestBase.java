/*  1:   */ package org.apache.http.client.methods;
/*  2:   */ 
/*  3:   */ import org.apache.http.Header;
/*  4:   */ import org.apache.http.HttpEntity;
/*  5:   */ import org.apache.http.HttpEntityEnclosingRequest;
/*  6:   */ import org.apache.http.annotation.NotThreadSafe;
/*  7:   */ import org.apache.http.client.utils.CloneUtils;
/*  8:   */ 
/*  9:   */ @NotThreadSafe
/* 10:   */ public abstract class HttpEntityEnclosingRequestBase
/* 11:   */   extends HttpRequestBase
/* 12:   */   implements HttpEntityEnclosingRequest
/* 13:   */ {
/* 14:   */   private HttpEntity entity;
/* 15:   */   
/* 16:   */   public HttpEntity getEntity()
/* 17:   */   {
/* 18:55 */     return this.entity;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setEntity(HttpEntity entity)
/* 22:   */   {
/* 23:59 */     this.entity = entity;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean expectContinue()
/* 27:   */   {
/* 28:63 */     Header expect = getFirstHeader("Expect");
/* 29:64 */     return (expect != null) && ("100-continue".equalsIgnoreCase(expect.getValue()));
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Object clone()
/* 33:   */     throws CloneNotSupportedException
/* 34:   */   {
/* 35:69 */     HttpEntityEnclosingRequestBase clone = (HttpEntityEnclosingRequestBase)super.clone();
/* 36:71 */     if (this.entity != null) {
/* 37:72 */       clone.entity = ((HttpEntity)CloneUtils.clone(this.entity));
/* 38:   */     }
/* 39:74 */     return clone;
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.methods.HttpEntityEnclosingRequestBase
 * JD-Core Version:    0.7.0.1
 */