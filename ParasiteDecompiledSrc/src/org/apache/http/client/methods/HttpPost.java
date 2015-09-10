/*  1:   */ package org.apache.http.client.methods;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import org.apache.http.annotation.NotThreadSafe;
/*  5:   */ 
/*  6:   */ @NotThreadSafe
/*  7:   */ public class HttpPost
/*  8:   */   extends HttpEntityEnclosingRequestBase
/*  9:   */ {
/* 10:   */   public static final String METHOD_NAME = "POST";
/* 11:   */   
/* 12:   */   public HttpPost() {}
/* 13:   */   
/* 14:   */   public HttpPost(URI uri)
/* 15:   */   {
/* 16:68 */     setURI(uri);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public HttpPost(String uri)
/* 20:   */   {
/* 21:76 */     setURI(URI.create(uri));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getMethod()
/* 25:   */   {
/* 26:81 */     return "POST";
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.methods.HttpPost
 * JD-Core Version:    0.7.0.1
 */