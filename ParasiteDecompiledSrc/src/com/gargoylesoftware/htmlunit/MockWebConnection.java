/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ 
/*  14:    */ public class MockWebConnection
/*  15:    */   implements WebConnection
/*  16:    */ {
/*  17: 42 */   private static final Log LOG = LogFactory.getLog(MockWebConnection.class);
/*  18: 44 */   private final Map<String, WebResponseData> responseMap_ = new HashMap(10);
/*  19:    */   private WebResponseData defaultResponse_;
/*  20:    */   private WebRequest lastRequest_;
/*  21: 47 */   private int requestCount_ = 0;
/*  22: 48 */   private final List<URL> requestedUrls_ = Collections.synchronizedList(new ArrayList());
/*  23:    */   
/*  24:    */   public WebResponse getResponse(WebRequest request)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 54 */     URL url = request.getUrl();
/*  28: 56 */     if (LOG.isDebugEnabled()) {
/*  29: 57 */       LOG.debug("Getting response for " + url.toExternalForm());
/*  30:    */     }
/*  31: 60 */     this.lastRequest_ = request;
/*  32: 61 */     this.requestCount_ += 1;
/*  33: 62 */     this.requestedUrls_.add(url);
/*  34:    */     
/*  35: 64 */     WebResponseData response = (WebResponseData)this.responseMap_.get(url.toExternalForm());
/*  36: 65 */     if (response == null)
/*  37:    */     {
/*  38: 66 */       response = this.defaultResponse_;
/*  39: 67 */       if (response == null) {
/*  40: 68 */         throw new IllegalStateException("No response specified that can handle URL [" + url.toExternalForm() + "]");
/*  41:    */       }
/*  42:    */     }
/*  43: 74 */     return new WebResponse(response, request, 0L);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List<String> getRequestedUrls(URL relativeTo)
/*  47:    */   {
/*  48: 83 */     String baseUrl = relativeTo.toString();
/*  49: 84 */     List<String> response = new ArrayList();
/*  50: 85 */     for (URL url : this.requestedUrls_)
/*  51:    */     {
/*  52: 86 */       String s = url.toString();
/*  53: 87 */       if (s.startsWith(baseUrl)) {
/*  54: 88 */         s = s.substring(baseUrl.length());
/*  55:    */       }
/*  56: 90 */       response.add(s);
/*  57:    */     }
/*  58: 93 */     return response;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public HttpMethod getLastMethod()
/*  62:    */   {
/*  63:102 */     return this.lastRequest_.getHttpMethod();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List<NameValuePair> getLastParameters()
/*  67:    */   {
/*  68:111 */     return this.lastRequest_.getRequestParameters();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setResponse(URL url, String content, int statusCode, String statusMessage, String contentType, List<? extends NameValuePair> responseHeaders)
/*  72:    */   {
/*  73:127 */     setResponse(url, TextUtil.stringToByteArray(content), statusCode, statusMessage, contentType, responseHeaders);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setResponse(URL url, String content, int statusCode, String statusMessage, String contentType, String charset, List<? extends NameValuePair> responseHeaders)
/*  77:    */   {
/*  78:150 */     setResponse(url, TextUtil.stringToByteArray(content, charset), statusCode, statusMessage, contentType, responseHeaders);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setResponse(URL url, byte[] content, int statusCode, String statusMessage, String contentType, List<? extends NameValuePair> responseHeaders)
/*  82:    */   {
/*  83:172 */     List<NameValuePair> compiledHeaders = new ArrayList(responseHeaders);
/*  84:173 */     if (contentType != null) {
/*  85:174 */       compiledHeaders.add(new NameValuePair("Content-Type", contentType));
/*  86:    */     }
/*  87:176 */     WebResponseData responseEntry = buildWebResponseData(content, statusCode, statusMessage, compiledHeaders);
/*  88:177 */     this.responseMap_.put(url.toExternalForm(), responseEntry);
/*  89:    */   }
/*  90:    */   
/*  91:    */   private WebResponseData buildWebResponseData(byte[] content, int statusCode, String statusMessage, List<NameValuePair> compiledHeaders)
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:183 */       return new WebResponseData(content, statusCode, statusMessage, compiledHeaders);
/*  96:    */     }
/*  97:    */     catch (IOException e)
/*  98:    */     {
/*  99:186 */       throw new RuntimeException(e);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setResponse(URL url, String content)
/* 104:    */   {
/* 105:199 */     List<? extends NameValuePair> emptyList = Collections.emptyList();
/* 106:200 */     setResponse(url, content, 200, "OK", "text/html", emptyList);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setResponse(URL url, String content, String contentType)
/* 110:    */   {
/* 111:213 */     List<? extends NameValuePair> emptyList = Collections.emptyList();
/* 112:214 */     setResponse(url, content, 200, "OK", contentType, emptyList);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setResponse(URL url, String content, String contentType, String charset)
/* 116:    */   {
/* 117:228 */     List<? extends NameValuePair> emptyList = Collections.emptyList();
/* 118:229 */     setResponse(url, content, 200, "OK", contentType, charset, emptyList);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setResponseAsGenericHtml(URL url, String title)
/* 122:    */   {
/* 123:241 */     String content = "<html><head><title>" + title + "</title></head><body></body></html>";
/* 124:242 */     setResponse(url, content);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDefaultResponse(String content, int statusCode, String statusMessage, String contentType)
/* 128:    */   {
/* 129:257 */     setDefaultResponse(TextUtil.stringToByteArray(content), statusCode, statusMessage, contentType);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDefaultResponse(byte[] content, int statusCode, String statusMessage, String contentType)
/* 133:    */   {
/* 134:272 */     List<NameValuePair> compiledHeaders = new ArrayList();
/* 135:273 */     if (contentType != null) {
/* 136:274 */       compiledHeaders.add(new NameValuePair("Content-Type", contentType));
/* 137:    */     }
/* 138:276 */     WebResponseData responseEntry = buildWebResponseData(content, statusCode, statusMessage, compiledHeaders);
/* 139:277 */     this.defaultResponse_ = responseEntry;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDefaultResponse(String content)
/* 143:    */   {
/* 144:287 */     setDefaultResponse(content, 200, "OK", "text/html");
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDefaultResponse(String content, String contentType)
/* 148:    */   {
/* 149:298 */     List<? extends NameValuePair> emptyList = Collections.emptyList();
/* 150:299 */     setDefaultResponse(content, 200, "OK", contentType, emptyList);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setDefaultResponse(String content, String contentType, String charset)
/* 154:    */   {
/* 155:311 */     List<? extends NameValuePair> emptyList = Collections.emptyList();
/* 156:312 */     setDefaultResponse(content, 200, "OK", contentType, charset, emptyList);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setDefaultResponse(String content, int statusCode, String statusMessage, String contentType, List<? extends NameValuePair> responseHeaders)
/* 160:    */   {
/* 161:327 */     List<NameValuePair> compiledHeaders = new ArrayList(responseHeaders);
/* 162:328 */     if (contentType != null) {
/* 163:329 */       compiledHeaders.add(new NameValuePair("Content-Type", contentType));
/* 164:    */     }
/* 165:331 */     this.defaultResponse_ = buildWebResponseData(TextUtil.stringToByteArray(content), statusCode, statusMessage, compiledHeaders);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setDefaultResponse(String content, int statusCode, String statusMessage, String contentType, String charset, List<? extends NameValuePair> responseHeaders)
/* 169:    */   {
/* 170:348 */     List<NameValuePair> compiledHeaders = new ArrayList(responseHeaders);
/* 171:349 */     compiledHeaders.add(new NameValuePair("Content-Type", contentType));
/* 172:350 */     this.defaultResponse_ = buildWebResponseData(TextUtil.stringToByteArray(content, charset), statusCode, statusMessage, compiledHeaders);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Map<String, String> getLastAdditionalHeaders()
/* 176:    */   {
/* 177:361 */     return this.lastRequest_.getAdditionalHeaders();
/* 178:    */   }
/* 179:    */   
/* 180:    */   @Deprecated
/* 181:    */   public WebRequest getLastWebRequestSettings()
/* 182:    */   {
/* 183:373 */     return this.lastRequest_;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public WebRequest getLastWebRequest()
/* 187:    */   {
/* 188:383 */     return this.lastRequest_;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int getRequestCount()
/* 192:    */   {
/* 193:391 */     return this.requestCount_;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean hasResponse(URL url)
/* 197:    */   {
/* 198:400 */     return this.responseMap_.containsKey(url.toExternalForm());
/* 199:    */   }
/* 200:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.MockWebConnection
 * JD-Core Version:    0.7.0.1
 */