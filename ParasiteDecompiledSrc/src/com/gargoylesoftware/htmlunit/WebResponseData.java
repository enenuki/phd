/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.zip.GZIPInputStream;
/*  10:    */ import java.util.zip.InflaterInputStream;
/*  11:    */ import org.apache.commons.io.IOUtils;
/*  12:    */ import org.apache.commons.lang.ArrayUtils;
/*  13:    */ import org.apache.commons.lang.StringUtils;
/*  14:    */ 
/*  15:    */ public class WebResponseData
/*  16:    */   implements Serializable
/*  17:    */ {
/*  18:    */   private final int statusCode_;
/*  19:    */   private final String statusMessage_;
/*  20:    */   private final List<NameValuePair> responseHeaders_;
/*  21:    */   private final DownloadedContent downloadedContent_;
/*  22:    */   
/*  23:    */   public WebResponseData(byte[] body, int statusCode, String statusMessage, List<NameValuePair> responseHeaders)
/*  24:    */     throws IOException
/*  25:    */   {
/*  26: 57 */     this(new DownloadedContent.InMemory(body), statusCode, statusMessage, responseHeaders);
/*  27:    */   }
/*  28:    */   
/*  29:    */   @Deprecated
/*  30:    */   public WebResponseData(InputStream bodyStream, int statusCode, String statusMessage, List<NameValuePair> responseHeaders)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 74 */     this(HttpWebConnection.downloadContent(bodyStream), statusCode, statusMessage, responseHeaders);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected WebResponseData(int statusCode, String statusMessage, List<NameValuePair> responseHeaders)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39: 88 */     this.statusCode_ = statusCode;
/*  40: 89 */     this.statusMessage_ = statusMessage;
/*  41: 90 */     this.responseHeaders_ = Collections.unmodifiableList(responseHeaders);
/*  42: 91 */     this.downloadedContent_ = new DownloadedContent.InMemory(ArrayUtils.EMPTY_BYTE_ARRAY);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public WebResponseData(DownloadedContent responseBody, int statusCode, String statusMessage, List<NameValuePair> responseHeaders)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:104 */     this.statusCode_ = statusCode;
/*  49:105 */     this.statusMessage_ = statusMessage;
/*  50:106 */     this.responseHeaders_ = Collections.unmodifiableList(responseHeaders);
/*  51:107 */     this.downloadedContent_ = responseBody;
/*  52:    */   }
/*  53:    */   
/*  54:    */   private InputStream getStream(InputStream stream, List<NameValuePair> headers)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:111 */     if (stream == null) {
/*  58:112 */       return null;
/*  59:    */     }
/*  60:114 */     String encoding = null;
/*  61:115 */     for (NameValuePair header : headers)
/*  62:    */     {
/*  63:116 */       String headerName = header.getName().trim();
/*  64:117 */       if ("content-encoding".equalsIgnoreCase(headerName))
/*  65:    */       {
/*  66:118 */         encoding = header.getValue();
/*  67:119 */         break;
/*  68:    */       }
/*  69:    */     }
/*  70:122 */     if ((encoding != null) && (StringUtils.contains(encoding, "gzip"))) {
/*  71:123 */       stream = new GZIPInputStream(stream);
/*  72:125 */     } else if ((encoding != null) && (StringUtils.contains(encoding, "deflate"))) {
/*  73:126 */       stream = new InflaterInputStream(stream);
/*  74:    */     }
/*  75:128 */     return stream;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public byte[] getBody()
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:138 */       return IOUtils.toByteArray(getInputStream());
/*  83:    */     }
/*  84:    */     catch (IOException e)
/*  85:    */     {
/*  86:141 */       throw new RuntimeException(e);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public InputStream getInputStream()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:151 */       return getStream(this.downloadedContent_.getInputStream(), getResponseHeaders());
/*  95:    */     }
/*  96:    */     catch (IOException e)
/*  97:    */     {
/*  98:154 */       throw new RuntimeException(e);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<NameValuePair> getResponseHeaders()
/* 103:    */   {
/* 104:162 */     return this.responseHeaders_;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getStatusCode()
/* 108:    */   {
/* 109:169 */     return this.statusCode_;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getStatusMessage()
/* 113:    */   {
/* 114:176 */     return this.statusMessage_;
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebResponseData
 * JD-Core Version:    0.7.0.1
 */