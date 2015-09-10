/*  1:   */ package org.apache.http.impl.entity;
/*  2:   */ 
/*  3:   */ import org.apache.http.Header;
/*  4:   */ import org.apache.http.HttpException;
/*  5:   */ import org.apache.http.HttpMessage;
/*  6:   */ import org.apache.http.HttpVersion;
/*  7:   */ import org.apache.http.ProtocolException;
/*  8:   */ import org.apache.http.ProtocolVersion;
/*  9:   */ import org.apache.http.entity.ContentLengthStrategy;
/* 10:   */ 
/* 11:   */ public class StrictContentLengthStrategy
/* 12:   */   implements ContentLengthStrategy
/* 13:   */ {
/* 14:   */   public long determineLength(HttpMessage message)
/* 15:   */     throws HttpException
/* 16:   */   {
/* 17:55 */     if (message == null) {
/* 18:56 */       throw new IllegalArgumentException("HTTP message may not be null");
/* 19:   */     }
/* 20:61 */     Header transferEncodingHeader = message.getFirstHeader("Transfer-Encoding");
/* 21:62 */     Header contentLengthHeader = message.getFirstHeader("Content-Length");
/* 22:63 */     if (transferEncodingHeader != null)
/* 23:   */     {
/* 24:64 */       String s = transferEncodingHeader.getValue();
/* 25:65 */       if ("chunked".equalsIgnoreCase(s))
/* 26:   */       {
/* 27:66 */         if (message.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
/* 28:67 */           throw new ProtocolException("Chunked transfer encoding not allowed for " + message.getProtocolVersion());
/* 29:   */         }
/* 30:71 */         return -2L;
/* 31:   */       }
/* 32:72 */       if ("identity".equalsIgnoreCase(s)) {
/* 33:73 */         return -1L;
/* 34:   */       }
/* 35:75 */       throw new ProtocolException("Unsupported transfer encoding: " + s);
/* 36:   */     }
/* 37:78 */     if (contentLengthHeader != null)
/* 38:   */     {
/* 39:79 */       String s = contentLengthHeader.getValue();
/* 40:   */       try
/* 41:   */       {
/* 42:81 */         return Long.parseLong(s);
/* 43:   */       }
/* 44:   */       catch (NumberFormatException e)
/* 45:   */       {
/* 46:84 */         throw new ProtocolException("Invalid content length: " + s);
/* 47:   */       }
/* 48:   */     }
/* 49:87 */     return -1L;
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.entity.StrictContentLengthStrategy
 * JD-Core Version:    0.7.0.1
 */