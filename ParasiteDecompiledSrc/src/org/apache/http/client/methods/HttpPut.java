/*  1:   */ package org.apache.http.client.methods;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import org.apache.http.annotation.NotThreadSafe;
/*  5:   */ 
/*  6:   */ @NotThreadSafe
/*  7:   */ public class HttpPut
/*  8:   */   extends HttpEntityEnclosingRequestBase
/*  9:   */ {
/* 10:   */   public static final String METHOD_NAME = "PUT";
/* 11:   */   
/* 12:   */   public HttpPut() {}
/* 13:   */   
/* 14:   */   public HttpPut(URI uri)
/* 15:   */   {
/* 16:60 */     setURI(uri);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public HttpPut(String uri)
/* 20:   */   {
/* 21:68 */     setURI(URI.create(uri));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getMethod()
/* 25:   */   {
/* 26:73 */     return "PUT";
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.methods.HttpPut
 * JD-Core Version:    0.7.0.1
 */