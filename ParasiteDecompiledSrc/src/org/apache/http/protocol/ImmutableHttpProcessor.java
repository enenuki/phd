/*   1:    */ package org.apache.http.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.HttpException;
/*   5:    */ import org.apache.http.HttpRequest;
/*   6:    */ import org.apache.http.HttpRequestInterceptor;
/*   7:    */ import org.apache.http.HttpResponse;
/*   8:    */ import org.apache.http.HttpResponseInterceptor;
/*   9:    */ 
/*  10:    */ public final class ImmutableHttpProcessor
/*  11:    */   implements HttpProcessor
/*  12:    */ {
/*  13:    */   private final HttpRequestInterceptor[] requestInterceptors;
/*  14:    */   private final HttpResponseInterceptor[] responseInterceptors;
/*  15:    */   
/*  16:    */   public ImmutableHttpProcessor(HttpRequestInterceptor[] requestInterceptors, HttpResponseInterceptor[] responseInterceptors)
/*  17:    */   {
/*  18: 52 */     if (requestInterceptors != null)
/*  19:    */     {
/*  20: 53 */       int count = requestInterceptors.length;
/*  21: 54 */       this.requestInterceptors = new HttpRequestInterceptor[count];
/*  22: 55 */       for (int i = 0; i < count; i++) {
/*  23: 56 */         this.requestInterceptors[i] = requestInterceptors[i];
/*  24:    */       }
/*  25:    */     }
/*  26:    */     else
/*  27:    */     {
/*  28: 59 */       this.requestInterceptors = new HttpRequestInterceptor[0];
/*  29:    */     }
/*  30: 61 */     if (responseInterceptors != null)
/*  31:    */     {
/*  32: 62 */       int count = responseInterceptors.length;
/*  33: 63 */       this.responseInterceptors = new HttpResponseInterceptor[count];
/*  34: 64 */       for (int i = 0; i < count; i++) {
/*  35: 65 */         this.responseInterceptors[i] = responseInterceptors[i];
/*  36:    */       }
/*  37:    */     }
/*  38:    */     else
/*  39:    */     {
/*  40: 68 */       this.responseInterceptors = new HttpResponseInterceptor[0];
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ImmutableHttpProcessor(HttpRequestInterceptorList requestInterceptors, HttpResponseInterceptorList responseInterceptors)
/*  45:    */   {
/*  46: 76 */     if (requestInterceptors != null)
/*  47:    */     {
/*  48: 77 */       int count = requestInterceptors.getRequestInterceptorCount();
/*  49: 78 */       this.requestInterceptors = new HttpRequestInterceptor[count];
/*  50: 79 */       for (int i = 0; i < count; i++) {
/*  51: 80 */         this.requestInterceptors[i] = requestInterceptors.getRequestInterceptor(i);
/*  52:    */       }
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 83 */       this.requestInterceptors = new HttpRequestInterceptor[0];
/*  57:    */     }
/*  58: 85 */     if (responseInterceptors != null)
/*  59:    */     {
/*  60: 86 */       int count = responseInterceptors.getResponseInterceptorCount();
/*  61: 87 */       this.responseInterceptors = new HttpResponseInterceptor[count];
/*  62: 88 */       for (int i = 0; i < count; i++) {
/*  63: 89 */         this.responseInterceptors[i] = responseInterceptors.getResponseInterceptor(i);
/*  64:    */       }
/*  65:    */     }
/*  66:    */     else
/*  67:    */     {
/*  68: 92 */       this.responseInterceptors = new HttpResponseInterceptor[0];
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ImmutableHttpProcessor(HttpRequestInterceptor[] requestInterceptors)
/*  73:    */   {
/*  74: 97 */     this(requestInterceptors, null);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public ImmutableHttpProcessor(HttpResponseInterceptor[] responseInterceptors)
/*  78:    */   {
/*  79:101 */     this(null, responseInterceptors);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void process(HttpRequest request, HttpContext context)
/*  83:    */     throws IOException, HttpException
/*  84:    */   {
/*  85:107 */     for (int i = 0; i < this.requestInterceptors.length; i++) {
/*  86:108 */       this.requestInterceptors[i].process(request, context);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void process(HttpResponse response, HttpContext context)
/*  91:    */     throws IOException, HttpException
/*  92:    */   {
/*  93:115 */     for (int i = 0; i < this.responseInterceptors.length; i++) {
/*  94:116 */       this.responseInterceptors[i].process(response, context);
/*  95:    */     }
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.ImmutableHttpProcessor
 * JD-Core Version:    0.7.0.1
 */