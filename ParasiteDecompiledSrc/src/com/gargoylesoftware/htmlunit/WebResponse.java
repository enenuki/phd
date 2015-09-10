/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.util.EncodingSniffer;
/*   4:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.io.UnsupportedEncodingException;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.util.List;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ 
/*  14:    */ public class WebResponse
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17: 42 */   private static final Log LOG = LogFactory.getLog(WebResponse.class);
/*  18:    */   private long loadTime_;
/*  19:    */   private WebResponseData responseData_;
/*  20:    */   private WebRequest request_;
/*  21:    */   
/*  22:    */   public WebResponse(WebResponseData responseData, URL url, HttpMethod requestMethod, long loadTime)
/*  23:    */   {
/*  24: 58 */     this(responseData, new WebRequest(url, requestMethod), loadTime);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public WebResponse(WebResponseData responseData, WebRequest request, long loadTime)
/*  28:    */   {
/*  29: 70 */     this.responseData_ = responseData;
/*  30: 71 */     this.request_ = request;
/*  31: 72 */     this.loadTime_ = loadTime;
/*  32:    */   }
/*  33:    */   
/*  34:    */   @Deprecated
/*  35:    */   public WebRequest getRequestSettings()
/*  36:    */   {
/*  37: 82 */     return this.request_;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public WebRequest getWebRequest()
/*  41:    */   {
/*  42: 90 */     return this.request_;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<NameValuePair> getResponseHeaders()
/*  46:    */   {
/*  47: 98 */     return this.responseData_.getResponseHeaders();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getResponseHeaderValue(String headerName)
/*  51:    */   {
/*  52:107 */     for (NameValuePair pair : this.responseData_.getResponseHeaders()) {
/*  53:108 */       if (pair.getName().equalsIgnoreCase(headerName)) {
/*  54:109 */         return pair.getValue();
/*  55:    */       }
/*  56:    */     }
/*  57:112 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getStatusCode()
/*  61:    */   {
/*  62:120 */     return this.responseData_.getStatusCode();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getStatusMessage()
/*  66:    */   {
/*  67:128 */     return this.responseData_.getStatusMessage();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getContentType()
/*  71:    */   {
/*  72:136 */     String contentTypeHeader = getResponseHeaderValue("content-type");
/*  73:137 */     if (contentTypeHeader == null) {
/*  74:139 */       return "";
/*  75:    */     }
/*  76:141 */     int index = contentTypeHeader.indexOf(';');
/*  77:142 */     if (index == -1) {
/*  78:143 */       return contentTypeHeader;
/*  79:    */     }
/*  80:145 */     return contentTypeHeader.substring(0, index);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getContentCharsetOrNull()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:156 */       return EncodingSniffer.sniffEncoding(getResponseHeaders(), getContentAsStream());
/*  88:    */     }
/*  89:    */     catch (IOException e)
/*  90:    */     {
/*  91:159 */       LOG.warn("Error trying to sniff encoding.", e);
/*  92:    */     }
/*  93:160 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getContentCharset()
/*  97:    */   {
/*  98:172 */     String charset = getContentCharsetOrNull();
/*  99:173 */     if (charset == null) {
/* 100:174 */       charset = getWebRequest().getCharset();
/* 101:    */     }
/* 102:176 */     if (charset == null) {
/* 103:177 */       charset = "ISO-8859-1";
/* 104:    */     }
/* 105:179 */     return charset;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getContentAsString()
/* 109:    */   {
/* 110:187 */     return getContentAsString(getContentCharset());
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getContentAsString(String encoding)
/* 114:    */   {
/* 115:198 */     byte[] body = this.responseData_.getBody();
/* 116:199 */     if (body != null) {
/* 117:    */       try
/* 118:    */       {
/* 119:201 */         return new String(body, encoding);
/* 120:    */       }
/* 121:    */       catch (UnsupportedEncodingException e)
/* 122:    */       {
/* 123:204 */         LOG.warn("Attempted to use unsupported encoding '" + encoding + "'; using default system encoding.");
/* 124:    */         
/* 125:206 */         return new String(body);
/* 126:    */       }
/* 127:    */     }
/* 128:209 */     return null;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public InputStream getContentAsStream()
/* 132:    */   {
/* 133:217 */     return this.responseData_.getInputStream();
/* 134:    */   }
/* 135:    */   
/* 136:    */   @Deprecated
/* 137:    */   public byte[] getContentAsBytes()
/* 138:    */   {
/* 139:227 */     return this.responseData_.getBody();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public long getLoadTime()
/* 143:    */   {
/* 144:235 */     return this.loadTime_;
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebResponse
 * JD-Core Version:    0.7.0.1
 */