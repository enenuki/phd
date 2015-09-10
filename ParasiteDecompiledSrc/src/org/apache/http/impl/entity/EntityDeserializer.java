/*   1:    */ package org.apache.http.impl.entity;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.Header;
/*   5:    */ import org.apache.http.HttpEntity;
/*   6:    */ import org.apache.http.HttpException;
/*   7:    */ import org.apache.http.HttpMessage;
/*   8:    */ import org.apache.http.entity.BasicHttpEntity;
/*   9:    */ import org.apache.http.entity.ContentLengthStrategy;
/*  10:    */ import org.apache.http.impl.io.ChunkedInputStream;
/*  11:    */ import org.apache.http.impl.io.ContentLengthInputStream;
/*  12:    */ import org.apache.http.impl.io.IdentityInputStream;
/*  13:    */ import org.apache.http.io.SessionInputBuffer;
/*  14:    */ 
/*  15:    */ public class EntityDeserializer
/*  16:    */ {
/*  17:    */   private final ContentLengthStrategy lenStrategy;
/*  18:    */   
/*  19:    */   public EntityDeserializer(ContentLengthStrategy lenStrategy)
/*  20:    */   {
/*  21: 66 */     if (lenStrategy == null) {
/*  22: 67 */       throw new IllegalArgumentException("Content length strategy may not be null");
/*  23:    */     }
/*  24: 69 */     this.lenStrategy = lenStrategy;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected BasicHttpEntity doDeserialize(SessionInputBuffer inbuffer, HttpMessage message)
/*  28:    */     throws HttpException, IOException
/*  29:    */   {
/*  30: 90 */     BasicHttpEntity entity = new BasicHttpEntity();
/*  31:    */     
/*  32: 92 */     long len = this.lenStrategy.determineLength(message);
/*  33: 93 */     if (len == -2L)
/*  34:    */     {
/*  35: 94 */       entity.setChunked(true);
/*  36: 95 */       entity.setContentLength(-1L);
/*  37: 96 */       entity.setContent(new ChunkedInputStream(inbuffer));
/*  38:    */     }
/*  39: 97 */     else if (len == -1L)
/*  40:    */     {
/*  41: 98 */       entity.setChunked(false);
/*  42: 99 */       entity.setContentLength(-1L);
/*  43:100 */       entity.setContent(new IdentityInputStream(inbuffer));
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47:102 */       entity.setChunked(false);
/*  48:103 */       entity.setContentLength(len);
/*  49:104 */       entity.setContent(new ContentLengthInputStream(inbuffer, len));
/*  50:    */     }
/*  51:107 */     Header contentTypeHeader = message.getFirstHeader("Content-Type");
/*  52:108 */     if (contentTypeHeader != null) {
/*  53:109 */       entity.setContentType(contentTypeHeader);
/*  54:    */     }
/*  55:111 */     Header contentEncodingHeader = message.getFirstHeader("Content-Encoding");
/*  56:112 */     if (contentEncodingHeader != null) {
/*  57:113 */       entity.setContentEncoding(contentEncodingHeader);
/*  58:    */     }
/*  59:115 */     return entity;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public HttpEntity deserialize(SessionInputBuffer inbuffer, HttpMessage message)
/*  63:    */     throws HttpException, IOException
/*  64:    */   {
/*  65:135 */     if (inbuffer == null) {
/*  66:136 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*  67:    */     }
/*  68:138 */     if (message == null) {
/*  69:139 */       throw new IllegalArgumentException("HTTP message may not be null");
/*  70:    */     }
/*  71:141 */     return doDeserialize(inbuffer, message);
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.entity.EntityDeserializer
 * JD-Core Version:    0.7.0.1
 */