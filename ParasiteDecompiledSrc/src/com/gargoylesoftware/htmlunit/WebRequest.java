/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   4:    */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.regex.Matcher;
/*  12:    */ import java.util.regex.Pattern;
/*  13:    */ import org.apache.commons.lang.ClassUtils;
/*  14:    */ import org.apache.http.auth.Credentials;
/*  15:    */ import org.apache.http.auth.UsernamePasswordCredentials;
/*  16:    */ 
/*  17:    */ public class WebRequest
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20: 46 */   private static final Pattern DOT_PATTERN = Pattern.compile("/\\./");
/*  21: 47 */   private static final Pattern DOT_DOT_PATTERN = Pattern.compile("/(?!\\.\\.)[^/]*/\\.\\./");
/*  22: 48 */   private static final Pattern REMOVE_DOTS_PATTERN = Pattern.compile("^/(\\.\\.?/)*");
/*  23:    */   private String url_;
/*  24:    */   private String proxyHost_;
/*  25:    */   private int proxyPort_;
/*  26:    */   private boolean isSocksProxy_;
/*  27: 54 */   private HttpMethod httpMethod_ = HttpMethod.GET;
/*  28: 55 */   private FormEncodingType encodingType_ = FormEncodingType.URL_ENCODED;
/*  29: 56 */   private Map<String, String> additionalHeaders_ = new HashMap();
/*  30:    */   private Credentials urlCredentials_;
/*  31:    */   private Credentials credentials_;
/*  32: 59 */   private String charset_ = "ISO-8859-1";
/*  33: 62 */   private List<NameValuePair> requestParameters_ = Collections.emptyList();
/*  34:    */   private String requestBody_;
/*  35:    */   
/*  36:    */   public WebRequest(URL url)
/*  37:    */   {
/*  38: 70 */     setUrl(url);
/*  39: 71 */     setAdditionalHeader("Accept", "*/*");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public WebRequest(WebRequest originalRequest, URL url)
/*  43:    */   {
/*  44: 81 */     this(url);
/*  45: 82 */     setProxyHost(originalRequest.getProxyHost());
/*  46: 83 */     setProxyPort(originalRequest.getProxyPort());
/*  47: 84 */     setSocksProxy(originalRequest.isSocksProxy());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public WebRequest(URL url, HttpMethod submitMethod)
/*  51:    */   {
/*  52: 93 */     this(url);
/*  53: 94 */     setHttpMethod(submitMethod);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public URL getUrl()
/*  57:    */   {
/*  58:102 */     return UrlUtils.toUrlSafe(this.url_);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setUrl(URL url)
/*  62:    */   {
/*  63:111 */     if (url != null)
/*  64:    */     {
/*  65:112 */       String path = url.getPath();
/*  66:113 */       if (path.length() == 0)
/*  67:    */       {
/*  68:114 */         url = buildUrlWithNewFile(url, "/" + url.getFile());
/*  69:    */       }
/*  70:116 */       else if (path.contains("/."))
/*  71:    */       {
/*  72:117 */         String query = url.getQuery() != null ? "?" + url.getQuery() : "";
/*  73:118 */         url = buildUrlWithNewFile(url, removeDots(path) + query);
/*  74:    */       }
/*  75:120 */       this.url_ = url.toExternalForm();
/*  76:    */       
/*  77:    */ 
/*  78:123 */       String userInfo = url.getUserInfo();
/*  79:124 */       if (userInfo != null)
/*  80:    */       {
/*  81:125 */         int splitPos = userInfo.indexOf(':');
/*  82:126 */         if (splitPos == -1)
/*  83:    */         {
/*  84:127 */           this.urlCredentials_ = new UsernamePasswordCredentials(userInfo, "");
/*  85:    */         }
/*  86:    */         else
/*  87:    */         {
/*  88:130 */           String username = userInfo.substring(0, splitPos);
/*  89:131 */           String password = userInfo.substring(splitPos + 1);
/*  90:132 */           this.urlCredentials_ = new UsernamePasswordCredentials(username, password);
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:137 */       this.url_ = null;
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private String removeDots(String path)
/* 101:    */   {
/* 102:150 */     String newPath = path;
/* 103:    */     
/* 104:    */ 
/* 105:153 */     newPath = REMOVE_DOTS_PATTERN.matcher(newPath).replaceAll("/");
/* 106:154 */     if ("/..".equals(newPath)) {
/* 107:155 */       newPath = "/";
/* 108:    */     }
/* 109:159 */     while (DOT_PATTERN.matcher(newPath).find()) {
/* 110:160 */       newPath = DOT_PATTERN.matcher(newPath).replaceAll("/");
/* 111:    */     }
/* 112:165 */     while (DOT_DOT_PATTERN.matcher(newPath).find()) {
/* 113:166 */       newPath = DOT_DOT_PATTERN.matcher(newPath).replaceAll("/");
/* 114:    */     }
/* 115:169 */     return newPath;
/* 116:    */   }
/* 117:    */   
/* 118:    */   private URL buildUrlWithNewFile(URL url, String newFile)
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:174 */       if (url.getRef() != null) {
/* 123:175 */         newFile = newFile + '#' + url.getRef();
/* 124:    */       }
/* 125:177 */       url = new URL(url.getProtocol(), url.getHost(), url.getPort(), newFile);
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:180 */       throw new RuntimeException("Cannot set URL: " + url.toExternalForm(), e);
/* 130:    */     }
/* 131:182 */     return url;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getProxyHost()
/* 135:    */   {
/* 136:190 */     return this.proxyHost_;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setProxyHost(String proxyHost)
/* 140:    */   {
/* 141:198 */     this.proxyHost_ = proxyHost;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int getProxyPort()
/* 145:    */   {
/* 146:206 */     return this.proxyPort_;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setProxyPort(int proxyPort)
/* 150:    */   {
/* 151:214 */     this.proxyPort_ = proxyPort;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isSocksProxy()
/* 155:    */   {
/* 156:222 */     return this.isSocksProxy_;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setSocksProxy(boolean isSocksProxy)
/* 160:    */   {
/* 161:230 */     this.isSocksProxy_ = isSocksProxy;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public FormEncodingType getEncodingType()
/* 165:    */   {
/* 166:238 */     return this.encodingType_;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setEncodingType(FormEncodingType encodingType)
/* 170:    */   {
/* 171:246 */     this.encodingType_ = encodingType;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public List<NameValuePair> getRequestParameters()
/* 175:    */   {
/* 176:256 */     return this.requestParameters_;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setRequestParameters(List<NameValuePair> requestParameters)
/* 180:    */     throws RuntimeException
/* 181:    */   {
/* 182:267 */     if (this.requestBody_ != null)
/* 183:    */     {
/* 184:268 */       String msg = "Trying to set the request parameters, but the request body has already been specified;the two are mutually exclusive!";
/* 185:    */       
/* 186:270 */       throw new RuntimeException("Trying to set the request parameters, but the request body has already been specified;the two are mutually exclusive!");
/* 187:    */     }
/* 188:272 */     this.requestParameters_ = requestParameters;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getRequestBody()
/* 192:    */   {
/* 193:281 */     return this.requestBody_;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setRequestBody(String requestBody)
/* 197:    */     throws RuntimeException
/* 198:    */   {
/* 199:293 */     if ((this.requestParameters_ != null) && (!this.requestParameters_.isEmpty()))
/* 200:    */     {
/* 201:294 */       String msg = "Trying to set the request body, but the request parameters have already been specified;the two are mutually exclusive!";
/* 202:    */       
/* 203:296 */       throw new RuntimeException("Trying to set the request body, but the request parameters have already been specified;the two are mutually exclusive!");
/* 204:    */     }
/* 205:298 */     if ((this.httpMethod_ != HttpMethod.POST) && (this.httpMethod_ != HttpMethod.PUT))
/* 206:    */     {
/* 207:299 */       String msg = "The request body may only be set for POST or PUT requests!";
/* 208:300 */       throw new RuntimeException("The request body may only be set for POST or PUT requests!");
/* 209:    */     }
/* 210:302 */     this.requestBody_ = requestBody;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public HttpMethod getHttpMethod()
/* 214:    */   {
/* 215:310 */     return this.httpMethod_;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setHttpMethod(HttpMethod submitMethod)
/* 219:    */   {
/* 220:318 */     this.httpMethod_ = submitMethod;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Map<String, String> getAdditionalHeaders()
/* 224:    */   {
/* 225:326 */     return this.additionalHeaders_;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setAdditionalHeaders(Map<String, String> additionalHeaders)
/* 229:    */   {
/* 230:334 */     this.additionalHeaders_ = additionalHeaders;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isAdditionalHeader(String name)
/* 234:    */   {
/* 235:343 */     for (String key : this.additionalHeaders_.keySet()) {
/* 236:344 */       if (name.equalsIgnoreCase(key)) {
/* 237:345 */         return true;
/* 238:    */       }
/* 239:    */     }
/* 240:348 */     return false;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setAdditionalHeader(String name, String value)
/* 244:    */   {
/* 245:357 */     for (String key : this.additionalHeaders_.keySet()) {
/* 246:358 */       if (name.equalsIgnoreCase(key))
/* 247:    */       {
/* 248:359 */         name = key;
/* 249:360 */         break;
/* 250:    */       }
/* 251:    */     }
/* 252:363 */     this.additionalHeaders_.put(name, value);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void removeAdditionalHeader(String name)
/* 256:    */   {
/* 257:371 */     for (String key : this.additionalHeaders_.keySet()) {
/* 258:372 */       if (name.equalsIgnoreCase(key))
/* 259:    */       {
/* 260:373 */         name = key;
/* 261:374 */         break;
/* 262:    */       }
/* 263:    */     }
/* 264:377 */     this.additionalHeaders_.remove(name);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Credentials getUrlCredentials()
/* 268:    */   {
/* 269:385 */     return this.urlCredentials_;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Credentials getCredentials()
/* 273:    */   {
/* 274:393 */     return this.credentials_;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setCredentials(Credentials credentials)
/* 278:    */   {
/* 279:401 */     this.credentials_ = credentials;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String getCharset()
/* 283:    */   {
/* 284:409 */     return this.charset_;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setCharset(String charset)
/* 288:    */   {
/* 289:418 */     this.charset_ = charset;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String toString()
/* 293:    */   {
/* 294:427 */     StringBuilder buffer = new StringBuilder();
/* 295:428 */     buffer.append(ClassUtils.getShortClassName(getClass()));
/* 296:429 */     buffer.append("[<");
/* 297:430 */     buffer.append("url=\"" + this.url_ + '"');
/* 298:431 */     buffer.append(", " + this.httpMethod_);
/* 299:432 */     buffer.append(", " + this.encodingType_);
/* 300:433 */     buffer.append(", " + this.requestParameters_);
/* 301:434 */     buffer.append(", " + this.additionalHeaders_);
/* 302:435 */     buffer.append(", " + this.credentials_);
/* 303:436 */     buffer.append(">]");
/* 304:437 */     return buffer.toString();
/* 305:    */   }
/* 306:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebRequest
 * JD-Core Version:    0.7.0.1
 */