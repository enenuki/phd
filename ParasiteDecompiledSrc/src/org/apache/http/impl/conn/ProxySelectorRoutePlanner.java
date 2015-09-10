/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import java.net.InetSocketAddress;
/*   5:    */ import java.net.Proxy;
/*   6:    */ import java.net.Proxy.Type;
/*   7:    */ import java.net.ProxySelector;
/*   8:    */ import java.net.URI;
/*   9:    */ import java.net.URISyntaxException;
/*  10:    */ import java.util.List;
/*  11:    */ import org.apache.http.HttpException;
/*  12:    */ import org.apache.http.HttpHost;
/*  13:    */ import org.apache.http.HttpRequest;
/*  14:    */ import org.apache.http.annotation.NotThreadSafe;
/*  15:    */ import org.apache.http.conn.params.ConnRouteParams;
/*  16:    */ import org.apache.http.conn.routing.HttpRoute;
/*  17:    */ import org.apache.http.conn.routing.HttpRoutePlanner;
/*  18:    */ import org.apache.http.conn.scheme.Scheme;
/*  19:    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  20:    */ import org.apache.http.protocol.HttpContext;
/*  21:    */ 
/*  22:    */ @NotThreadSafe
/*  23:    */ public class ProxySelectorRoutePlanner
/*  24:    */   implements HttpRoutePlanner
/*  25:    */ {
/*  26:    */   protected final SchemeRegistry schemeRegistry;
/*  27:    */   protected ProxySelector proxySelector;
/*  28:    */   
/*  29:    */   public ProxySelectorRoutePlanner(SchemeRegistry schreg, ProxySelector prosel)
/*  30:    */   {
/*  31: 91 */     if (schreg == null) {
/*  32: 92 */       throw new IllegalArgumentException("SchemeRegistry must not be null.");
/*  33:    */     }
/*  34: 95 */     this.schemeRegistry = schreg;
/*  35: 96 */     this.proxySelector = prosel;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ProxySelector getProxySelector()
/*  39:    */   {
/*  40:105 */     return this.proxySelector;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setProxySelector(ProxySelector prosel)
/*  44:    */   {
/*  45:115 */     this.proxySelector = prosel;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
/*  49:    */     throws HttpException
/*  50:    */   {
/*  51:123 */     if (request == null) {
/*  52:124 */       throw new IllegalStateException("Request must not be null.");
/*  53:    */     }
/*  54:129 */     HttpRoute route = ConnRouteParams.getForcedRoute(request.getParams());
/*  55:131 */     if (route != null) {
/*  56:132 */       return route;
/*  57:    */     }
/*  58:137 */     if (target == null) {
/*  59:138 */       throw new IllegalStateException("Target host must not be null.");
/*  60:    */     }
/*  61:142 */     InetAddress local = ConnRouteParams.getLocalAddress(request.getParams());
/*  62:    */     
/*  63:144 */     HttpHost proxy = determineProxy(target, request, context);
/*  64:    */     
/*  65:146 */     Scheme schm = this.schemeRegistry.getScheme(target.getSchemeName());
/*  66:    */     
/*  67:    */ 
/*  68:    */ 
/*  69:150 */     boolean secure = schm.isLayered();
/*  70:152 */     if (proxy == null) {
/*  71:153 */       route = new HttpRoute(target, local, secure);
/*  72:    */     } else {
/*  73:155 */       route = new HttpRoute(target, local, proxy, secure);
/*  74:    */     }
/*  75:157 */     return route;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context)
/*  79:    */     throws HttpException
/*  80:    */   {
/*  81:178 */     ProxySelector psel = this.proxySelector;
/*  82:179 */     if (psel == null) {
/*  83:180 */       psel = ProxySelector.getDefault();
/*  84:    */     }
/*  85:181 */     if (psel == null) {
/*  86:182 */       return null;
/*  87:    */     }
/*  88:184 */     URI targetURI = null;
/*  89:    */     try
/*  90:    */     {
/*  91:186 */       targetURI = new URI(target.toURI());
/*  92:    */     }
/*  93:    */     catch (URISyntaxException usx)
/*  94:    */     {
/*  95:188 */       throw new HttpException("Cannot convert host to URI: " + target, usx);
/*  96:    */     }
/*  97:191 */     List<Proxy> proxies = psel.select(targetURI);
/*  98:    */     
/*  99:193 */     Proxy p = chooseProxy(proxies, target, request, context);
/* 100:    */     
/* 101:195 */     HttpHost result = null;
/* 102:196 */     if (p.type() == Proxy.Type.HTTP)
/* 103:    */     {
/* 104:198 */       if (!(p.address() instanceof InetSocketAddress)) {
/* 105:199 */         throw new HttpException("Unable to handle non-Inet proxy address: " + p.address());
/* 106:    */       }
/* 107:202 */       InetSocketAddress isa = (InetSocketAddress)p.address();
/* 108:    */       
/* 109:204 */       result = new HttpHost(getHost(isa), isa.getPort());
/* 110:    */     }
/* 111:207 */     return result;
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected String getHost(InetSocketAddress isa)
/* 115:    */   {
/* 116:225 */     return isa.isUnresolved() ? isa.getHostName() : isa.getAddress().getHostAddress();
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected Proxy chooseProxy(List<Proxy> proxies, HttpHost target, HttpRequest request, HttpContext context)
/* 120:    */   {
/* 121:251 */     if ((proxies == null) || (proxies.isEmpty())) {
/* 122:252 */       throw new IllegalArgumentException("Proxy list must not be empty.");
/* 123:    */     }
/* 124:256 */     Proxy result = null;
/* 125:259 */     for (int i = 0; (result == null) && (i < proxies.size()); i++)
/* 126:    */     {
/* 127:261 */       Proxy p = (Proxy)proxies.get(i);
/* 128:262 */       switch (1.$SwitchMap$java$net$Proxy$Type[p.type().ordinal()])
/* 129:    */       {
/* 130:    */       case 1: 
/* 131:    */       case 2: 
/* 132:266 */         result = p;
/* 133:    */       }
/* 134:    */     }
/* 135:276 */     if (result == null) {
/* 136:280 */       result = Proxy.NO_PROXY;
/* 137:    */     }
/* 138:283 */     return result;
/* 139:    */   }
/* 140:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.ProxySelectorRoutePlanner
 * JD-Core Version:    0.7.0.1
 */