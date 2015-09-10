/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import org.apache.http.HttpRequest;
/*  5:   */ import org.apache.http.HttpResponse;
/*  6:   */ import org.apache.http.ProtocolException;
/*  7:   */ import org.apache.http.RequestLine;
/*  8:   */ import org.apache.http.annotation.Immutable;
/*  9:   */ import org.apache.http.client.RedirectHandler;
/* 10:   */ import org.apache.http.client.RedirectStrategy;
/* 11:   */ import org.apache.http.client.methods.HttpGet;
/* 12:   */ import org.apache.http.client.methods.HttpHead;
/* 13:   */ import org.apache.http.client.methods.HttpUriRequest;
/* 14:   */ import org.apache.http.protocol.HttpContext;
/* 15:   */ 
/* 16:   */ @Deprecated
/* 17:   */ @Immutable
/* 18:   */ class DefaultRedirectStrategyAdaptor
/* 19:   */   implements RedirectStrategy
/* 20:   */ {
/* 21:   */   private final RedirectHandler handler;
/* 22:   */   
/* 23:   */   @Deprecated
/* 24:   */   public DefaultRedirectStrategyAdaptor(RedirectHandler handler)
/* 25:   */   {
/* 26:54 */     this.handler = handler;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)
/* 30:   */     throws ProtocolException
/* 31:   */   {
/* 32:61 */     return this.handler.isRedirectRequested(response, context);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context)
/* 36:   */     throws ProtocolException
/* 37:   */   {
/* 38:68 */     URI uri = this.handler.getLocationURI(response, context);
/* 39:69 */     String method = request.getRequestLine().getMethod();
/* 40:70 */     if (method.equalsIgnoreCase("HEAD")) {
/* 41:71 */       return new HttpHead(uri);
/* 42:   */     }
/* 43:73 */     return new HttpGet(uri);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultRedirectStrategyAdaptor
 * JD-Core Version:    0.7.0.1
 */