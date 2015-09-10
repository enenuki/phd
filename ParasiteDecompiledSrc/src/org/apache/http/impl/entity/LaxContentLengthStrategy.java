/*   1:    */ package org.apache.http.impl.entity;
/*   2:    */ 
/*   3:    */ import org.apache.http.Header;
/*   4:    */ import org.apache.http.HeaderElement;
/*   5:    */ import org.apache.http.HttpException;
/*   6:    */ import org.apache.http.HttpMessage;
/*   7:    */ import org.apache.http.ParseException;
/*   8:    */ import org.apache.http.ProtocolException;
/*   9:    */ import org.apache.http.entity.ContentLengthStrategy;
/*  10:    */ import org.apache.http.params.HttpParams;
/*  11:    */ 
/*  12:    */ public class LaxContentLengthStrategy
/*  13:    */   implements ContentLengthStrategy
/*  14:    */ {
/*  15:    */   public long determineLength(HttpMessage message)
/*  16:    */     throws HttpException
/*  17:    */   {
/*  18: 63 */     if (message == null) {
/*  19: 64 */       throw new IllegalArgumentException("HTTP message may not be null");
/*  20:    */     }
/*  21: 67 */     HttpParams params = message.getParams();
/*  22: 68 */     boolean strict = params.isParameterTrue("http.protocol.strict-transfer-encoding");
/*  23:    */     
/*  24: 70 */     Header transferEncodingHeader = message.getFirstHeader("Transfer-Encoding");
/*  25: 71 */     Header contentLengthHeader = message.getFirstHeader("Content-Length");
/*  26: 74 */     if (transferEncodingHeader != null)
/*  27:    */     {
/*  28: 75 */       HeaderElement[] encodings = null;
/*  29:    */       try
/*  30:    */       {
/*  31: 77 */         encodings = transferEncodingHeader.getElements();
/*  32:    */       }
/*  33:    */       catch (ParseException px)
/*  34:    */       {
/*  35: 79 */         throw new ProtocolException("Invalid Transfer-Encoding header value: " + transferEncodingHeader, px);
/*  36:    */       }
/*  37: 83 */       if (strict) {
/*  38: 85 */         for (int i = 0; i < encodings.length; i++)
/*  39:    */         {
/*  40: 86 */           String encoding = encodings[i].getName();
/*  41: 87 */           if ((encoding != null) && (encoding.length() > 0) && (!encoding.equalsIgnoreCase("chunked")) && (!encoding.equalsIgnoreCase("identity"))) {
/*  42: 90 */             throw new ProtocolException("Unsupported transfer encoding: " + encoding);
/*  43:    */           }
/*  44:    */         }
/*  45:    */       }
/*  46: 95 */       int len = encodings.length;
/*  47: 96 */       if ("identity".equalsIgnoreCase(transferEncodingHeader.getValue())) {
/*  48: 97 */         return -1L;
/*  49:    */       }
/*  50: 98 */       if ((len > 0) && ("chunked".equalsIgnoreCase(encodings[(len - 1)].getName()))) {
/*  51:100 */         return -2L;
/*  52:    */       }
/*  53:102 */       if (strict) {
/*  54:103 */         throw new ProtocolException("Chunk-encoding must be the last one applied");
/*  55:    */       }
/*  56:105 */       return -1L;
/*  57:    */     }
/*  58:107 */     if (contentLengthHeader != null)
/*  59:    */     {
/*  60:108 */       long contentlen = -1L;
/*  61:109 */       Header[] headers = message.getHeaders("Content-Length");
/*  62:110 */       if ((strict) && (headers.length > 1)) {
/*  63:111 */         throw new ProtocolException("Multiple content length headers");
/*  64:    */       }
/*  65:113 */       for (int i = headers.length - 1; i >= 0; i--)
/*  66:    */       {
/*  67:114 */         Header header = headers[i];
/*  68:    */         try
/*  69:    */         {
/*  70:116 */           contentlen = Long.parseLong(header.getValue());
/*  71:    */         }
/*  72:    */         catch (NumberFormatException e)
/*  73:    */         {
/*  74:119 */           if (strict) {
/*  75:120 */             throw new ProtocolException("Invalid content length: " + header.getValue());
/*  76:    */           }
/*  77:    */         }
/*  78:    */       }
/*  79:125 */       if (contentlen >= 0L) {
/*  80:126 */         return contentlen;
/*  81:    */       }
/*  82:128 */       return -1L;
/*  83:    */     }
/*  84:131 */     return -1L;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.entity.LaxContentLengthStrategy
 * JD-Core Version:    0.7.0.1
 */