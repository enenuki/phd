/*  1:   */ package org.apache.http.message;
/*  2:   */ 
/*  3:   */ import org.apache.http.Header;
/*  4:   */ import org.apache.http.HttpEntity;
/*  5:   */ import org.apache.http.HttpEntityEnclosingRequest;
/*  6:   */ import org.apache.http.ProtocolVersion;
/*  7:   */ import org.apache.http.RequestLine;
/*  8:   */ 
/*  9:   */ public class BasicHttpEntityEnclosingRequest
/* 10:   */   extends BasicHttpRequest
/* 11:   */   implements HttpEntityEnclosingRequest
/* 12:   */ {
/* 13:   */   private HttpEntity entity;
/* 14:   */   
/* 15:   */   public BasicHttpEntityEnclosingRequest(String method, String uri)
/* 16:   */   {
/* 17:48 */     super(method, uri);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public BasicHttpEntityEnclosingRequest(String method, String uri, ProtocolVersion ver)
/* 21:   */   {
/* 22:53 */     super(method, uri, ver);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public BasicHttpEntityEnclosingRequest(RequestLine requestline)
/* 26:   */   {
/* 27:57 */     super(requestline);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public HttpEntity getEntity()
/* 31:   */   {
/* 32:61 */     return this.entity;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setEntity(HttpEntity entity)
/* 36:   */   {
/* 37:65 */     this.entity = entity;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean expectContinue()
/* 41:   */   {
/* 42:69 */     Header expect = getFirstHeader("Expect");
/* 43:70 */     return (expect != null) && ("100-continue".equalsIgnoreCase(expect.getValue()));
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHttpEntityEnclosingRequest
 * JD-Core Version:    0.7.0.1
 */