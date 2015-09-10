/*  1:   */ package org.apache.http.client.methods;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import org.apache.http.annotation.NotThreadSafe;
/*  5:   */ 
/*  6:   */ @NotThreadSafe
/*  7:   */ public class HttpGet
/*  8:   */   extends HttpRequestBase
/*  9:   */ {
/* 10:   */   public static final String METHOD_NAME = "GET";
/* 11:   */   
/* 12:   */   public HttpGet() {}
/* 13:   */   
/* 14:   */   public HttpGet(URI uri)
/* 15:   */   {
/* 16:61 */     setURI(uri);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public HttpGet(String uri)
/* 20:   */   {
/* 21:69 */     setURI(URI.create(uri));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getMethod()
/* 25:   */   {
/* 26:74 */     return "GET";
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.methods.HttpGet
 * JD-Core Version:    0.7.0.1
 */