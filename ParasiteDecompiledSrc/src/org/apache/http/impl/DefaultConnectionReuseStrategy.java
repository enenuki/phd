/*   1:    */ package org.apache.http.impl;
/*   2:    */ 
/*   3:    */ import org.apache.http.ConnectionReuseStrategy;
/*   4:    */ import org.apache.http.HeaderIterator;
/*   5:    */ import org.apache.http.HttpConnection;
/*   6:    */ import org.apache.http.HttpEntity;
/*   7:    */ import org.apache.http.HttpResponse;
/*   8:    */ import org.apache.http.HttpVersion;
/*   9:    */ import org.apache.http.ParseException;
/*  10:    */ import org.apache.http.ProtocolVersion;
/*  11:    */ import org.apache.http.StatusLine;
/*  12:    */ import org.apache.http.TokenIterator;
/*  13:    */ import org.apache.http.message.BasicTokenIterator;
/*  14:    */ import org.apache.http.protocol.HttpContext;
/*  15:    */ 
/*  16:    */ public class DefaultConnectionReuseStrategy
/*  17:    */   implements ConnectionReuseStrategy
/*  18:    */ {
/*  19:    */   public boolean keepAlive(HttpResponse response, HttpContext context)
/*  20:    */   {
/*  21: 71 */     if (response == null) {
/*  22: 72 */       throw new IllegalArgumentException("HTTP response may not be null.");
/*  23:    */     }
/*  24: 75 */     if (context == null) {
/*  25: 76 */       throw new IllegalArgumentException("HTTP context may not be null.");
/*  26:    */     }
/*  27: 80 */     HttpConnection conn = (HttpConnection)context.getAttribute("http.connection");
/*  28: 83 */     if ((conn != null) && (!conn.isOpen())) {
/*  29: 84 */       return false;
/*  30:    */     }
/*  31: 89 */     HttpEntity entity = response.getEntity();
/*  32: 90 */     ProtocolVersion ver = response.getStatusLine().getProtocolVersion();
/*  33: 91 */     if ((entity != null) && 
/*  34: 92 */       (entity.getContentLength() < 0L) && (
/*  35: 93 */       (!entity.isChunked()) || (ver.lessEquals(HttpVersion.HTTP_1_0)))) {
/*  36: 97 */       return false;
/*  37:    */     }
/*  38:105 */     HeaderIterator hit = response.headerIterator("Connection");
/*  39:106 */     if (!hit.hasNext()) {
/*  40:107 */       hit = response.headerIterator("Proxy-Connection");
/*  41:    */     }
/*  42:132 */     if (hit.hasNext()) {
/*  43:    */       try
/*  44:    */       {
/*  45:134 */         TokenIterator ti = createTokenIterator(hit);
/*  46:135 */         boolean keepalive = false;
/*  47:136 */         while (ti.hasNext())
/*  48:    */         {
/*  49:137 */           String token = ti.nextToken();
/*  50:138 */           if ("Close".equalsIgnoreCase(token)) {
/*  51:139 */             return false;
/*  52:    */           }
/*  53:140 */           if ("Keep-Alive".equalsIgnoreCase(token)) {
/*  54:142 */             keepalive = true;
/*  55:    */           }
/*  56:    */         }
/*  57:145 */         if (keepalive) {
/*  58:146 */           return true;
/*  59:    */         }
/*  60:    */       }
/*  61:    */       catch (ParseException px)
/*  62:    */       {
/*  63:152 */         return false;
/*  64:    */       }
/*  65:    */     }
/*  66:157 */     return !ver.lessEquals(HttpVersion.HTTP_1_0);
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected TokenIterator createTokenIterator(HeaderIterator hit)
/*  70:    */   {
/*  71:171 */     return new BasicTokenIterator(hit);
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.DefaultConnectionReuseStrategy
 * JD-Core Version:    0.7.0.1
 */