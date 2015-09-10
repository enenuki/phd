/*   1:    */ package org.apache.http.impl;
/*   2:    */ 
/*   3:    */ import org.apache.http.HttpRequest;
/*   4:    */ import org.apache.http.HttpRequestFactory;
/*   5:    */ import org.apache.http.MethodNotSupportedException;
/*   6:    */ import org.apache.http.RequestLine;
/*   7:    */ import org.apache.http.message.BasicHttpEntityEnclosingRequest;
/*   8:    */ import org.apache.http.message.BasicHttpRequest;
/*   9:    */ 
/*  10:    */ public class DefaultHttpRequestFactory
/*  11:    */   implements HttpRequestFactory
/*  12:    */ {
/*  13: 44 */   private static final String[] RFC2616_COMMON_METHODS = { "GET" };
/*  14: 48 */   private static final String[] RFC2616_ENTITY_ENC_METHODS = { "POST", "PUT" };
/*  15: 53 */   private static final String[] RFC2616_SPECIAL_METHODS = { "HEAD", "OPTIONS", "DELETE", "TRACE", "CONNECT" };
/*  16:    */   
/*  17:    */   private static boolean isOneOf(String[] methods, String method)
/*  18:    */   {
/*  19: 67 */     for (int i = 0; i < methods.length; i++) {
/*  20: 68 */       if (methods[i].equalsIgnoreCase(method)) {
/*  21: 69 */         return true;
/*  22:    */       }
/*  23:    */     }
/*  24: 72 */     return false;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public HttpRequest newHttpRequest(RequestLine requestline)
/*  28:    */     throws MethodNotSupportedException
/*  29:    */   {
/*  30: 77 */     if (requestline == null) {
/*  31: 78 */       throw new IllegalArgumentException("Request line may not be null");
/*  32:    */     }
/*  33: 80 */     String method = requestline.getMethod();
/*  34: 81 */     if (isOneOf(RFC2616_COMMON_METHODS, method)) {
/*  35: 82 */       return new BasicHttpRequest(requestline);
/*  36:    */     }
/*  37: 83 */     if (isOneOf(RFC2616_ENTITY_ENC_METHODS, method)) {
/*  38: 84 */       return new BasicHttpEntityEnclosingRequest(requestline);
/*  39:    */     }
/*  40: 85 */     if (isOneOf(RFC2616_SPECIAL_METHODS, method)) {
/*  41: 86 */       return new BasicHttpRequest(requestline);
/*  42:    */     }
/*  43: 88 */     throw new MethodNotSupportedException(method + " method not supported");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public HttpRequest newHttpRequest(String method, String uri)
/*  47:    */     throws MethodNotSupportedException
/*  48:    */   {
/*  49: 94 */     if (isOneOf(RFC2616_COMMON_METHODS, method)) {
/*  50: 95 */       return new BasicHttpRequest(method, uri);
/*  51:    */     }
/*  52: 96 */     if (isOneOf(RFC2616_ENTITY_ENC_METHODS, method)) {
/*  53: 97 */       return new BasicHttpEntityEnclosingRequest(method, uri);
/*  54:    */     }
/*  55: 98 */     if (isOneOf(RFC2616_SPECIAL_METHODS, method)) {
/*  56: 99 */       return new BasicHttpRequest(method, uri);
/*  57:    */     }
/*  58:101 */     throw new MethodNotSupportedException(method + " method not supported");
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.DefaultHttpRequestFactory
 * JD-Core Version:    0.7.0.1
 */