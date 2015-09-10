/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ 
/*  5:   */ public class HttpRequestHandlerRegistry
/*  6:   */   implements HttpRequestHandlerResolver
/*  7:   */ {
/*  8:   */   private final UriPatternMatcher matcher;
/*  9:   */   
/* 10:   */   public HttpRequestHandlerRegistry()
/* 11:   */   {
/* 12:54 */     this.matcher = new UriPatternMatcher();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void register(String pattern, HttpRequestHandler handler)
/* 16:   */   {
/* 17:65 */     if (pattern == null) {
/* 18:66 */       throw new IllegalArgumentException("URI request pattern may not be null");
/* 19:   */     }
/* 20:68 */     if (handler == null) {
/* 21:69 */       throw new IllegalArgumentException("Request handler may not be null");
/* 22:   */     }
/* 23:71 */     this.matcher.register(pattern, handler);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void unregister(String pattern)
/* 27:   */   {
/* 28:80 */     this.matcher.unregister(pattern);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setHandlers(Map map)
/* 32:   */   {
/* 33:88 */     this.matcher.setObjects(map);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public HttpRequestHandler lookup(String requestURI)
/* 37:   */   {
/* 38:92 */     return (HttpRequestHandler)this.matcher.lookup(requestURI);
/* 39:   */   }
/* 40:   */   
/* 41:   */   /**
/* 42:   */    * @deprecated
/* 43:   */    */
/* 44:   */   protected boolean matchUriRequestPattern(String pattern, String requestUri)
/* 45:   */   {
/* 46:99 */     return this.matcher.matchUriRequestPattern(pattern, requestUri);
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.HttpRequestHandlerRegistry
 * JD-Core Version:    0.7.0.1
 */