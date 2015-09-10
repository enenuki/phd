/*   1:    */ package org.apache.http.client.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.URI;
/*   5:    */ import java.net.URISyntaxException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.http.Header;
/*  12:    */ import org.apache.http.HttpException;
/*  13:    */ import org.apache.http.HttpHost;
/*  14:    */ import org.apache.http.HttpRequest;
/*  15:    */ import org.apache.http.HttpRequestInterceptor;
/*  16:    */ import org.apache.http.ProtocolException;
/*  17:    */ import org.apache.http.RequestLine;
/*  18:    */ import org.apache.http.annotation.Immutable;
/*  19:    */ import org.apache.http.client.CookieStore;
/*  20:    */ import org.apache.http.client.methods.HttpUriRequest;
/*  21:    */ import org.apache.http.client.params.HttpClientParams;
/*  22:    */ import org.apache.http.conn.HttpRoutedConnection;
/*  23:    */ import org.apache.http.conn.routing.HttpRoute;
/*  24:    */ import org.apache.http.cookie.Cookie;
/*  25:    */ import org.apache.http.cookie.CookieOrigin;
/*  26:    */ import org.apache.http.cookie.CookieSpec;
/*  27:    */ import org.apache.http.cookie.CookieSpecRegistry;
/*  28:    */ import org.apache.http.cookie.SetCookie2;
/*  29:    */ import org.apache.http.protocol.HttpContext;
/*  30:    */ 
/*  31:    */ @Immutable
/*  32:    */ public class RequestAddCookies
/*  33:    */   implements HttpRequestInterceptor
/*  34:    */ {
/*  35: 78 */   private final Log log = LogFactory.getLog(getClass());
/*  36:    */   
/*  37:    */   public void process(HttpRequest request, HttpContext context)
/*  38:    */     throws HttpException, IOException
/*  39:    */   {
/*  40: 86 */     if (request == null) {
/*  41: 87 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  42:    */     }
/*  43: 89 */     if (context == null) {
/*  44: 90 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  45:    */     }
/*  46: 93 */     String method = request.getRequestLine().getMethod();
/*  47: 94 */     if (method.equalsIgnoreCase("CONNECT")) {
/*  48: 95 */       return;
/*  49:    */     }
/*  50: 99 */     CookieStore cookieStore = (CookieStore)context.getAttribute("http.cookie-store");
/*  51:101 */     if (cookieStore == null)
/*  52:    */     {
/*  53:102 */       this.log.debug("Cookie store not specified in HTTP context");
/*  54:103 */       return;
/*  55:    */     }
/*  56:107 */     CookieSpecRegistry registry = (CookieSpecRegistry)context.getAttribute("http.cookiespec-registry");
/*  57:109 */     if (registry == null)
/*  58:    */     {
/*  59:110 */       this.log.debug("CookieSpec registry not specified in HTTP context");
/*  60:111 */       return;
/*  61:    */     }
/*  62:115 */     HttpHost targetHost = (HttpHost)context.getAttribute("http.target_host");
/*  63:117 */     if (targetHost == null)
/*  64:    */     {
/*  65:118 */       this.log.debug("Target host not set in the context");
/*  66:119 */       return;
/*  67:    */     }
/*  68:123 */     HttpRoutedConnection conn = (HttpRoutedConnection)context.getAttribute("http.connection");
/*  69:125 */     if (conn == null)
/*  70:    */     {
/*  71:126 */       this.log.debug("HTTP connection not set in the context");
/*  72:127 */       return;
/*  73:    */     }
/*  74:130 */     String policy = HttpClientParams.getCookiePolicy(request.getParams());
/*  75:131 */     if (this.log.isDebugEnabled()) {
/*  76:132 */       this.log.debug("CookieSpec selected: " + policy);
/*  77:    */     }
/*  78:    */     URI requestURI;
/*  79:    */     URI requestURI;
/*  80:136 */     if ((request instanceof HttpUriRequest)) {
/*  81:137 */       requestURI = ((HttpUriRequest)request).getURI();
/*  82:    */     } else {
/*  83:    */       try
/*  84:    */       {
/*  85:140 */         requestURI = new URI(request.getRequestLine().getUri());
/*  86:    */       }
/*  87:    */       catch (URISyntaxException ex)
/*  88:    */       {
/*  89:142 */         throw new ProtocolException("Invalid request URI: " + request.getRequestLine().getUri(), ex);
/*  90:    */       }
/*  91:    */     }
/*  92:147 */     String hostName = targetHost.getHostName();
/*  93:148 */     int port = targetHost.getPort();
/*  94:149 */     if (port < 0)
/*  95:    */     {
/*  96:150 */       HttpRoute route = conn.getRoute();
/*  97:151 */       if (route.getHopCount() == 1)
/*  98:    */       {
/*  99:152 */         port = conn.getRemotePort();
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:156 */         String scheme = targetHost.getSchemeName();
/* 104:157 */         if (scheme.equalsIgnoreCase("http")) {
/* 105:158 */           port = 80;
/* 106:159 */         } else if (scheme.equalsIgnoreCase("https")) {
/* 107:160 */           port = 443;
/* 108:    */         } else {
/* 109:162 */           port = 0;
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113:167 */     CookieOrigin cookieOrigin = new CookieOrigin(hostName, port, requestURI.getPath(), conn.isSecure());
/* 114:    */     
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:174 */     CookieSpec cookieSpec = registry.getCookieSpec(policy, request.getParams());
/* 121:    */     
/* 122:176 */     List<Cookie> cookies = new ArrayList(cookieStore.getCookies());
/* 123:    */     
/* 124:178 */     List<Cookie> matchedCookies = new ArrayList();
/* 125:179 */     Date now = new Date();
/* 126:180 */     for (Cookie cookie : cookies) {
/* 127:181 */       if (!cookie.isExpired(now))
/* 128:    */       {
/* 129:182 */         if (cookieSpec.match(cookie, cookieOrigin))
/* 130:    */         {
/* 131:183 */           if (this.log.isDebugEnabled()) {
/* 132:184 */             this.log.debug("Cookie " + cookie + " match " + cookieOrigin);
/* 133:    */           }
/* 134:186 */           matchedCookies.add(cookie);
/* 135:    */         }
/* 136:    */       }
/* 137:189 */       else if (this.log.isDebugEnabled()) {
/* 138:190 */         this.log.debug("Cookie " + cookie + " expired");
/* 139:    */       }
/* 140:    */     }
/* 141:195 */     if (!matchedCookies.isEmpty())
/* 142:    */     {
/* 143:196 */       List<Header> headers = cookieSpec.formatCookies(matchedCookies);
/* 144:197 */       for (Header header : headers) {
/* 145:198 */         request.addHeader(header);
/* 146:    */       }
/* 147:    */     }
/* 148:202 */     int ver = cookieSpec.getVersion();
/* 149:203 */     if (ver > 0)
/* 150:    */     {
/* 151:204 */       boolean needVersionHeader = false;
/* 152:205 */       for (Cookie cookie : matchedCookies) {
/* 153:206 */         if ((ver != cookie.getVersion()) || (!(cookie instanceof SetCookie2))) {
/* 154:207 */           needVersionHeader = true;
/* 155:    */         }
/* 156:    */       }
/* 157:211 */       if (needVersionHeader)
/* 158:    */       {
/* 159:212 */         Header header = cookieSpec.getVersionHeader();
/* 160:213 */         if (header != null) {
/* 161:215 */           request.addHeader(header);
/* 162:    */         }
/* 163:    */       }
/* 164:    */     }
/* 165:222 */     context.setAttribute("http.cookie-spec", cookieSpec);
/* 166:223 */     context.setAttribute("http.cookie-origin", cookieOrigin);
/* 167:    */   }
/* 168:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.RequestAddCookies
 * JD-Core Version:    0.7.0.1
 */