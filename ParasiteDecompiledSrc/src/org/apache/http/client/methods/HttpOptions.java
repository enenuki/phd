/*  1:   */ package org.apache.http.client.methods;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import java.util.HashSet;
/*  5:   */ import java.util.Set;
/*  6:   */ import org.apache.http.Header;
/*  7:   */ import org.apache.http.HeaderElement;
/*  8:   */ import org.apache.http.HeaderIterator;
/*  9:   */ import org.apache.http.HttpResponse;
/* 10:   */ import org.apache.http.annotation.NotThreadSafe;
/* 11:   */ 
/* 12:   */ @NotThreadSafe
/* 13:   */ public class HttpOptions
/* 14:   */   extends HttpRequestBase
/* 15:   */ {
/* 16:   */   public static final String METHOD_NAME = "OPTIONS";
/* 17:   */   
/* 18:   */   public HttpOptions() {}
/* 19:   */   
/* 20:   */   public HttpOptions(URI uri)
/* 21:   */   {
/* 22:69 */     setURI(uri);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public HttpOptions(String uri)
/* 26:   */   {
/* 27:77 */     setURI(URI.create(uri));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getMethod()
/* 31:   */   {
/* 32:82 */     return "OPTIONS";
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Set<String> getAllowedMethods(HttpResponse response)
/* 36:   */   {
/* 37:86 */     if (response == null) {
/* 38:87 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 39:   */     }
/* 40:90 */     HeaderIterator it = response.headerIterator("Allow");
/* 41:91 */     Set<String> methods = new HashSet();
/* 42:92 */     while (it.hasNext())
/* 43:   */     {
/* 44:93 */       Header header = it.nextHeader();
/* 45:94 */       HeaderElement[] elements = header.getElements();
/* 46:95 */       for (HeaderElement element : elements) {
/* 47:96 */         methods.add(element.getName());
/* 48:   */       }
/* 49:   */     }
/* 50:99 */     return methods;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.methods.HttpOptions
 * JD-Core Version:    0.7.0.1
 */