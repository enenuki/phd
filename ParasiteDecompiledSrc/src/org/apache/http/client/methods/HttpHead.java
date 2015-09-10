/*  1:   */ package org.apache.http.client.methods;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import org.apache.http.annotation.NotThreadSafe;
/*  5:   */ 
/*  6:   */ @NotThreadSafe
/*  7:   */ public class HttpHead
/*  8:   */   extends HttpRequestBase
/*  9:   */ {
/* 10:   */   public static final String METHOD_NAME = "HEAD";
/* 11:   */   
/* 12:   */   public HttpHead() {}
/* 13:   */   
/* 14:   */   public HttpHead(URI uri)
/* 15:   */   {
/* 16:64 */     setURI(uri);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public HttpHead(String uri)
/* 20:   */   {
/* 21:72 */     setURI(URI.create(uri));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getMethod()
/* 25:   */   {
/* 26:77 */     return "HEAD";
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.methods.HttpHead
 * JD-Core Version:    0.7.0.1
 */