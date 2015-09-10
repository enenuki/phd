/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import java.net.URI;
/*   4:    */ import java.net.URISyntaxException;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.http.Header;
/*   8:    */ import org.apache.http.HttpHost;
/*   9:    */ import org.apache.http.HttpRequest;
/*  10:    */ import org.apache.http.HttpResponse;
/*  11:    */ import org.apache.http.ProtocolException;
/*  12:    */ import org.apache.http.RequestLine;
/*  13:    */ import org.apache.http.StatusLine;
/*  14:    */ import org.apache.http.annotation.Immutable;
/*  15:    */ import org.apache.http.client.CircularRedirectException;
/*  16:    */ import org.apache.http.client.RedirectHandler;
/*  17:    */ import org.apache.http.client.utils.URIUtils;
/*  18:    */ import org.apache.http.params.HttpParams;
/*  19:    */ import org.apache.http.protocol.HttpContext;
/*  20:    */ 
/*  21:    */ @Deprecated
/*  22:    */ @Immutable
/*  23:    */ public class DefaultRedirectHandler
/*  24:    */   implements RedirectHandler
/*  25:    */ {
/*  26: 64 */   private final Log log = LogFactory.getLog(getClass());
/*  27:    */   private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
/*  28:    */   
/*  29:    */   public boolean isRedirectRequested(HttpResponse response, HttpContext context)
/*  30:    */   {
/*  31: 75 */     if (response == null) {
/*  32: 76 */       throw new IllegalArgumentException("HTTP response may not be null");
/*  33:    */     }
/*  34: 79 */     int statusCode = response.getStatusLine().getStatusCode();
/*  35: 80 */     switch (statusCode)
/*  36:    */     {
/*  37:    */     case 301: 
/*  38:    */     case 302: 
/*  39:    */     case 307: 
/*  40: 84 */       HttpRequest request = (HttpRequest)context.getAttribute("http.request");
/*  41:    */       
/*  42: 86 */       String method = request.getRequestLine().getMethod();
/*  43: 87 */       return (method.equalsIgnoreCase("GET")) || (method.equalsIgnoreCase("HEAD"));
/*  44:    */     case 303: 
/*  45: 90 */       return true;
/*  46:    */     }
/*  47: 92 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public URI getLocationURI(HttpResponse response, HttpContext context)
/*  51:    */     throws ProtocolException
/*  52:    */   {
/*  53: 99 */     if (response == null) {
/*  54:100 */       throw new IllegalArgumentException("HTTP response may not be null");
/*  55:    */     }
/*  56:103 */     Header locationHeader = response.getFirstHeader("location");
/*  57:104 */     if (locationHeader == null) {
/*  58:106 */       throw new ProtocolException("Received redirect response " + response.getStatusLine() + " but no location header");
/*  59:    */     }
/*  60:110 */     String location = locationHeader.getValue();
/*  61:111 */     if (this.log.isDebugEnabled()) {
/*  62:112 */       this.log.debug("Redirect requested to location '" + location + "'");
/*  63:    */     }
/*  64:    */     URI uri;
/*  65:    */     try
/*  66:    */     {
/*  67:117 */       uri = new URI(location);
/*  68:    */     }
/*  69:    */     catch (URISyntaxException ex)
/*  70:    */     {
/*  71:119 */       throw new ProtocolException("Invalid redirect URI: " + location, ex);
/*  72:    */     }
/*  73:122 */     HttpParams params = response.getParams();
/*  74:125 */     if (!uri.isAbsolute())
/*  75:    */     {
/*  76:126 */       if (params.isParameterTrue("http.protocol.reject-relative-redirect")) {
/*  77:127 */         throw new ProtocolException("Relative redirect location '" + uri + "' not allowed");
/*  78:    */       }
/*  79:131 */       HttpHost target = (HttpHost)context.getAttribute("http.target_host");
/*  80:133 */       if (target == null) {
/*  81:134 */         throw new IllegalStateException("Target host not available in the HTTP context");
/*  82:    */       }
/*  83:138 */       HttpRequest request = (HttpRequest)context.getAttribute("http.request");
/*  84:    */       try
/*  85:    */       {
/*  86:142 */         URI requestURI = new URI(request.getRequestLine().getUri());
/*  87:143 */         URI absoluteRequestURI = URIUtils.rewriteURI(requestURI, target, true);
/*  88:144 */         uri = URIUtils.resolve(absoluteRequestURI, uri);
/*  89:    */       }
/*  90:    */       catch (URISyntaxException ex)
/*  91:    */       {
/*  92:146 */         throw new ProtocolException(ex.getMessage(), ex);
/*  93:    */       }
/*  94:    */     }
/*  95:150 */     if (params.isParameterFalse("http.protocol.allow-circular-redirects"))
/*  96:    */     {
/*  97:152 */       RedirectLocations redirectLocations = (RedirectLocations)context.getAttribute("http.protocol.redirect-locations");
/*  98:155 */       if (redirectLocations == null)
/*  99:    */       {
/* 100:156 */         redirectLocations = new RedirectLocations();
/* 101:157 */         context.setAttribute("http.protocol.redirect-locations", redirectLocations);
/* 102:    */       }
/* 103:    */       URI redirectURI;
/* 104:161 */       if (uri.getFragment() != null) {
/* 105:    */         try
/* 106:    */         {
/* 107:163 */           HttpHost target = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
/* 108:    */           
/* 109:    */ 
/* 110:    */ 
/* 111:167 */           redirectURI = URIUtils.rewriteURI(uri, target, true);
/* 112:    */         }
/* 113:    */         catch (URISyntaxException ex)
/* 114:    */         {
/* 115:169 */           throw new ProtocolException(ex.getMessage(), ex);
/* 116:    */         }
/* 117:    */       } else {
/* 118:172 */         redirectURI = uri;
/* 119:    */       }
/* 120:175 */       if (redirectLocations.contains(redirectURI)) {
/* 121:176 */         throw new CircularRedirectException("Circular redirect to '" + redirectURI + "'");
/* 122:    */       }
/* 123:179 */       redirectLocations.add(redirectURI);
/* 124:    */     }
/* 125:183 */     return uri;
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultRedirectHandler
 * JD-Core Version:    0.7.0.1
 */