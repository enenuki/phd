/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.net.HttpURLConnection;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import org.apache.commons.io.IOUtils;
/*  14:    */ import org.apache.commons.logging.Log;
/*  15:    */ import org.apache.commons.logging.LogFactory;
/*  16:    */ import org.apache.http.client.utils.URLEncodedUtils;
/*  17:    */ 
/*  18:    */ public class UrlFetchWebConnection
/*  19:    */   implements WebConnection
/*  20:    */ {
/*  21: 50 */   private static final Log LOG = LogFactory.getLog(UrlFetchWebConnection.class);
/*  22: 52 */   private static final String[] GAE_URL_HACKS = { "http://gaeHack_javascript/", "http://gaeHack_data/", "http://gaeHack_about/" };
/*  23:    */   private final WebClient webClient_;
/*  24:    */   
/*  25:    */   public UrlFetchWebConnection(WebClient webClient)
/*  26:    */   {
/*  27: 62 */     this.webClient_ = webClient;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public WebResponse getResponse(WebRequest webRequest)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 69 */     long startTime = System.currentTimeMillis();
/*  34: 70 */     URL url = webRequest.getUrl();
/*  35: 71 */     if (LOG.isTraceEnabled()) {
/*  36: 72 */       LOG.trace("about to fetch URL " + url);
/*  37:    */     }
/*  38: 76 */     WebResponse response = produceWebResponseForGAEProcolHack(url);
/*  39: 77 */     if (response != null) {
/*  40: 78 */       return response;
/*  41:    */     }
/*  42:    */     try
/*  43:    */     {
/*  44: 83 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/*  45:    */       
/*  46: 85 */       connection.setConnectTimeout(this.webClient_.getTimeout());
/*  47:    */       
/*  48: 87 */       connection.addRequestProperty("User-Agent", this.webClient_.getBrowserVersion().getUserAgent());
/*  49: 90 */       for (Map.Entry<String, String> header : webRequest.getAdditionalHeaders().entrySet()) {
/*  50: 91 */         connection.addRequestProperty((String)header.getKey(), (String)header.getValue());
/*  51:    */       }
/*  52: 94 */       HttpMethod httpMethod = webRequest.getHttpMethod();
/*  53: 95 */       connection.setRequestMethod(httpMethod.name());
/*  54: 96 */       if ((HttpMethod.POST == httpMethod) || (HttpMethod.PUT == httpMethod))
/*  55:    */       {
/*  56: 97 */         connection.setDoOutput(true);
/*  57: 98 */         String charset = webRequest.getCharset();
/*  58: 99 */         connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*  59:100 */         OutputStream outputStream = connection.getOutputStream();
/*  60:    */         try
/*  61:    */         {
/*  62:102 */           List<com.gargoylesoftware.htmlunit.util.NameValuePair> pairs = webRequest.getRequestParameters();
/*  63:103 */           org.apache.http.NameValuePair[] httpClientPairs = com.gargoylesoftware.htmlunit.util.NameValuePair.toHttpClient(pairs);
/*  64:104 */           String query = URLEncodedUtils.format(Arrays.asList(httpClientPairs), charset);
/*  65:105 */           outputStream.write(query.getBytes(charset));
/*  66:106 */           if (webRequest.getRequestBody() != null) {
/*  67:107 */             IOUtils.write(webRequest.getRequestBody().getBytes(charset), outputStream);
/*  68:    */           }
/*  69:    */         }
/*  70:    */         finally
/*  71:    */         {
/*  72:111 */           outputStream.close();
/*  73:    */         }
/*  74:    */       }
/*  75:115 */       int responseCode = connection.getResponseCode();
/*  76:116 */       if (LOG.isTraceEnabled()) {
/*  77:117 */         LOG.trace("fetched URL " + url);
/*  78:    */       }
/*  79:120 */       List<com.gargoylesoftware.htmlunit.util.NameValuePair> headers = new ArrayList();
/*  80:121 */       for (Map.Entry<String, List<String>> headerEntry : connection.getHeaderFields().entrySet())
/*  81:    */       {
/*  82:122 */         String headerKey = (String)headerEntry.getKey();
/*  83:123 */         if (headerKey != null)
/*  84:    */         {
/*  85:124 */           StringBuilder sb = new StringBuilder();
/*  86:125 */           for (String headerValue : (List)headerEntry.getValue())
/*  87:    */           {
/*  88:126 */             if (sb.length() > 0) {
/*  89:127 */               sb.append(", ");
/*  90:    */             }
/*  91:129 */             sb.append(headerValue);
/*  92:    */           }
/*  93:131 */           headers.add(new com.gargoylesoftware.htmlunit.util.NameValuePair(headerKey, sb.toString()));
/*  94:    */         }
/*  95:    */       }
/*  96:135 */       InputStream is = responseCode < 400 ? connection.getInputStream() : connection.getErrorStream();
/*  97:    */       byte[] byteArray;
/*  98:    */       try
/*  99:    */       {
/* 100:138 */         byteArray = IOUtils.toByteArray(is);
/* 101:    */       }
/* 102:    */       finally
/* 103:    */       {
/* 104:141 */         is.close();
/* 105:    */       }
/* 106:143 */       long duration = System.currentTimeMillis() - startTime;
/* 107:144 */       WebResponseData responseData = new WebResponseData(byteArray, responseCode, connection.getResponseMessage(), headers);
/* 108:    */       
/* 109:146 */       return new WebResponse(responseData, webRequest, duration);
/* 110:    */     }
/* 111:    */     catch (IOException e)
/* 112:    */     {
/* 113:149 */       LOG.error("Exception while tyring to fetch " + url, e);
/* 114:150 */       throw new RuntimeException(e);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private WebResponse produceWebResponseForGAEProcolHack(URL url)
/* 119:    */   {
/* 120:155 */     String externalForm = url.toExternalForm();
/* 121:156 */     for (String pattern : GAE_URL_HACKS)
/* 122:    */     {
/* 123:157 */       int index = externalForm.indexOf(pattern);
/* 124:158 */       if (index == 0)
/* 125:    */       {
/* 126:159 */         String contentString = externalForm.substring(pattern.length());
/* 127:160 */         if ((contentString.startsWith("'")) && (contentString.endsWith("'"))) {
/* 128:161 */           contentString = contentString.substring(1, contentString.length() - 1);
/* 129:    */         }
/* 130:163 */         if (LOG.isDebugEnabled()) {
/* 131:164 */           LOG.debug("special handling of URL, returning (" + contentString + ") for URL " + url);
/* 132:    */         }
/* 133:166 */         return new StringWebResponse(contentString, url);
/* 134:    */       }
/* 135:    */     }
/* 136:169 */     return null;
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.UrlFetchWebConnection
 * JD-Core Version:    0.7.0.1
 */