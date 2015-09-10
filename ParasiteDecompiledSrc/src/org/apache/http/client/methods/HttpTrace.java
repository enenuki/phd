/*  1:   */ package org.apache.http.client.methods;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import org.apache.http.annotation.NotThreadSafe;
/*  5:   */ 
/*  6:   */ @NotThreadSafe
/*  7:   */ public class HttpTrace
/*  8:   */   extends HttpRequestBase
/*  9:   */ {
/* 10:   */   public static final String METHOD_NAME = "TRACE";
/* 11:   */   
/* 12:   */   public HttpTrace() {}
/* 13:   */   
/* 14:   */   public HttpTrace(URI uri)
/* 15:   */   {
/* 16:63 */     setURI(uri);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public HttpTrace(String uri)
/* 20:   */   {
/* 21:71 */     setURI(URI.create(uri));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getMethod()
/* 25:   */   {
/* 26:76 */     return "TRACE";
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.methods.HttpTrace
 * JD-Core Version:    0.7.0.1
 */