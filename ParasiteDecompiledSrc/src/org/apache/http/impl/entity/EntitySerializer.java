/*   1:    */ package org.apache.http.impl.entity;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import org.apache.http.HttpEntity;
/*   6:    */ import org.apache.http.HttpException;
/*   7:    */ import org.apache.http.HttpMessage;
/*   8:    */ import org.apache.http.entity.ContentLengthStrategy;
/*   9:    */ import org.apache.http.impl.io.ChunkedOutputStream;
/*  10:    */ import org.apache.http.impl.io.ContentLengthOutputStream;
/*  11:    */ import org.apache.http.impl.io.IdentityOutputStream;
/*  12:    */ import org.apache.http.io.SessionOutputBuffer;
/*  13:    */ 
/*  14:    */ public class EntitySerializer
/*  15:    */ {
/*  16:    */   private final ContentLengthStrategy lenStrategy;
/*  17:    */   
/*  18:    */   public EntitySerializer(ContentLengthStrategy lenStrategy)
/*  19:    */   {
/*  20: 63 */     if (lenStrategy == null) {
/*  21: 64 */       throw new IllegalArgumentException("Content length strategy may not be null");
/*  22:    */     }
/*  23: 66 */     this.lenStrategy = lenStrategy;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected OutputStream doSerialize(SessionOutputBuffer outbuffer, HttpMessage message)
/*  27:    */     throws HttpException, IOException
/*  28:    */   {
/*  29: 86 */     long len = this.lenStrategy.determineLength(message);
/*  30: 87 */     if (len == -2L) {
/*  31: 88 */       return new ChunkedOutputStream(outbuffer);
/*  32:    */     }
/*  33: 89 */     if (len == -1L) {
/*  34: 90 */       return new IdentityOutputStream(outbuffer);
/*  35:    */     }
/*  36: 92 */     return new ContentLengthOutputStream(outbuffer, len);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void serialize(SessionOutputBuffer outbuffer, HttpMessage message, HttpEntity entity)
/*  40:    */     throws HttpException, IOException
/*  41:    */   {
/*  42:110 */     if (outbuffer == null) {
/*  43:111 */       throw new IllegalArgumentException("Session output buffer may not be null");
/*  44:    */     }
/*  45:113 */     if (message == null) {
/*  46:114 */       throw new IllegalArgumentException("HTTP message may not be null");
/*  47:    */     }
/*  48:116 */     if (entity == null) {
/*  49:117 */       throw new IllegalArgumentException("HTTP entity may not be null");
/*  50:    */     }
/*  51:119 */     OutputStream outstream = doSerialize(outbuffer, message);
/*  52:120 */     entity.writeTo(outstream);
/*  53:121 */     outstream.close();
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.entity.EntitySerializer
 * JD-Core Version:    0.7.0.1
 */