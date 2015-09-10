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
/*  16:    */ import org.apache.http.client.RedirectStrategy;
/*  17:    */ import org.apache.http.client.methods.HttpGet;
/*  18:    */ import org.apache.http.client.methods.HttpHead;
/*  19:    */ import org.apache.http.client.methods.HttpUriRequest;
/*  20:    */ import org.apache.http.client.utils.URIUtils;
/*  21:    */ import org.apache.http.params.HttpParams;
/*  22:    */ import org.apache.http.protocol.HttpContext;
/*  23:    */ 
/*  24:    */ @Immutable
/*  25:    */ public class DefaultRedirectStrategy
/*  26:    */   implements RedirectStrategy
/*  27:    */ {
/*  28: 62 */   private final Log log = LogFactory.getLog(getClass());
/*  29:    */   public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
/*  30:    */   
/*  31:    */   public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)
/*  32:    */     throws ProtocolException
/*  33:    */   {
/*  34: 74 */     if (response == null) {
/*  35: 75 */       throw new IllegalArgumentException("HTTP response may not be null");
/*  36:    */     }
/*  37: 78 */     int statusCode = response.getStatusLine().getStatusCode();
/*  38: 79 */     String method = request.getRequestLine().getMethod();
/*  39: 80 */     Header locationHeader = response.getFirstHeader("location");
/*  40: 81 */     switch (statusCode)
/*  41:    */     {
/*  42:    */     case 302: 
/*  43: 83 */       return ((method.equalsIgnoreCase("GET")) || (method.equalsIgnoreCase("HEAD"))) && (locationHeader != null);
/*  44:    */     case 301: 
/*  45:    */     case 307: 
/*  46: 87 */       return (method.equalsIgnoreCase("GET")) || (method.equalsIgnoreCase("HEAD"));
/*  47:    */     case 303: 
/*  48: 90 */       return true;
/*  49:    */     }
/*  50: 92 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public URI getLocationURI(HttpRequest request, HttpResponse response, HttpContext context)
/*  54:    */     throws ProtocolException
/*  55:    */   {
/*  56:100 */     if (response == null) {
/*  57:101 */       throw new IllegalArgumentException("HTTP response may not be null");
/*  58:    */     }
/*  59:104 */     Header locationHeader = response.getFirstHeader("location");
/*  60:105 */     if (locationHeader == null) {
/*  61:107 */       throw new ProtocolException("Received redirect response " + response.getStatusLine() + " but no location header");
/*  62:    */     }
/*  63:111 */     String location = locationHeader.getValue();
/*  64:112 */     if (this.log.isDebugEnabled()) {
/*  65:113 */       this.log.debug("Redirect requested to location '" + location + "'");
/*  66:    */     }
/*  67:116 */     URI uri = createLocationURI(location);
/*  68:    */     
/*  69:118 */     HttpParams params = response.getParams();
/*  70:121 */     if (!uri.isAbsolute())
/*  71:    */     {
/*  72:122 */       if (params.isParameterTrue("http.protocol.reject-relative-redirect")) {
/*  73:123 */         throw new ProtocolException("Relative redirect location '" + uri + "' not allowed");
/*  74:    */       }
/*  75:127 */       HttpHost target = (HttpHost)context.getAttribute("http.target_host");
/*  76:129 */       if (target == null) {
/*  77:130 */         throw new IllegalStateException("Target host not available in the HTTP context");
/*  78:    */       }
/*  79:    */       try
/*  80:    */       {
/*  81:134 */         URI requestURI = new URI(request.getRequestLine().getUri());
/*  82:135 */         URI absoluteRequestURI = URIUtils.rewriteURI(requestURI, target, true);
/*  83:136 */         uri = URIUtils.resolve(absoluteRequestURI, uri);
/*  84:    */       }
/*  85:    */       catch (URISyntaxException ex)
/*  86:    */       {
/*  87:138 */         throw new ProtocolException(ex.getMessage(), ex);
/*  88:    */       }
/*  89:    */     }
/*  90:142 */     if (params.isParameterFalse("http.protocol.allow-circular-redirects"))
/*  91:    */     {
/*  92:144 */       RedirectLocations redirectLocations = (RedirectLocations)context.getAttribute("http.protocol.redirect-locations");
/*  93:147 */       if (redirectLocations == null)
/*  94:    */       {
/*  95:148 */         redirectLocations = new RedirectLocations();
/*  96:149 */         context.setAttribute("http.protocol.redirect-locations", redirectLocations);
/*  97:    */       }
/*  98:    */       URI redirectURI;
/*  99:153 */       if (uri.getFragment() != null) {
/* 100:    */         try
/* 101:    */         {
/* 102:155 */           HttpHost target = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
/* 103:    */           
/* 104:    */ 
/* 105:    */ 
/* 106:159 */           redirectURI = URIUtils.rewriteURI(uri, target, true);
/* 107:    */         }
/* 108:    */         catch (URISyntaxException ex)
/* 109:    */         {
/* 110:161 */           throw new ProtocolException(ex.getMessage(), ex);
/* 111:    */         }
/* 112:    */       } else {
/* 113:164 */         redirectURI = uri;
/* 114:    */       }
/* 115:167 */       if (redirectLocations.contains(redirectURI)) {
/* 116:168 */         throw new CircularRedirectException("Circular redirect to '" + redirectURI + "'");
/* 117:    */       }
/* 118:171 */       redirectLocations.add(redirectURI);
/* 119:    */     }
/* 120:175 */     return uri;
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected URI createLocationURI(String location)
/* 124:    */     throws ProtocolException
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:183 */       return new URI(location);
/* 129:    */     }
/* 130:    */     catch (URISyntaxException ex)
/* 131:    */     {
/* 132:185 */       throw new ProtocolException("Invalid redirect URI: " + location, ex);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context)
/* 137:    */     throws ProtocolException
/* 138:    */   {
/* 139:193 */     URI uri = getLocationURI(request, response, context);
/* 140:194 */     String method = request.getRequestLine().getMethod();
/* 141:195 */     if (method.equalsIgnoreCase("HEAD")) {
/* 142:196 */       return new HttpHead(uri);
/* 143:    */     }
/* 144:198 */     return new HttpGet(uri);
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultRedirectStrategy
 * JD-Core Version:    0.7.0.1
 */