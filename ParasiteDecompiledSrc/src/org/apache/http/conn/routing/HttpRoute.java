/*   1:    */ package org.apache.http.conn.routing;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import org.apache.http.HttpHost;
/*   5:    */ import org.apache.http.annotation.Immutable;
/*   6:    */ import org.apache.http.util.LangUtils;
/*   7:    */ 
/*   8:    */ @Immutable
/*   9:    */ public final class HttpRoute
/*  10:    */   implements RouteInfo, Cloneable
/*  11:    */ {
/*  12: 47 */   private static final HttpHost[] EMPTY_HTTP_HOST_ARRAY = new HttpHost[0];
/*  13:    */   private final HttpHost targetHost;
/*  14:    */   private final InetAddress localAddress;
/*  15:    */   private final HttpHost[] proxyChain;
/*  16:    */   private final RouteInfo.TunnelType tunnelled;
/*  17:    */   private final RouteInfo.LayerType layered;
/*  18:    */   private final boolean secure;
/*  19:    */   
/*  20:    */   private HttpRoute(InetAddress local, HttpHost target, HttpHost[] proxies, boolean secure, RouteInfo.TunnelType tunnelled, RouteInfo.LayerType layered)
/*  21:    */   {
/*  22: 95 */     if (target == null) {
/*  23: 96 */       throw new IllegalArgumentException("Target host may not be null.");
/*  24:    */     }
/*  25: 99 */     if (proxies == null) {
/*  26:100 */       throw new IllegalArgumentException("Proxies may not be null.");
/*  27:    */     }
/*  28:103 */     if ((tunnelled == RouteInfo.TunnelType.TUNNELLED) && (proxies.length == 0)) {
/*  29:104 */       throw new IllegalArgumentException("Proxy required if tunnelled.");
/*  30:    */     }
/*  31:109 */     if (tunnelled == null) {
/*  32:110 */       tunnelled = RouteInfo.TunnelType.PLAIN;
/*  33:    */     }
/*  34:111 */     if (layered == null) {
/*  35:112 */       layered = RouteInfo.LayerType.PLAIN;
/*  36:    */     }
/*  37:114 */     this.targetHost = target;
/*  38:115 */     this.localAddress = local;
/*  39:116 */     this.proxyChain = proxies;
/*  40:117 */     this.secure = secure;
/*  41:118 */     this.tunnelled = tunnelled;
/*  42:119 */     this.layered = layered;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public HttpRoute(HttpHost target, InetAddress local, HttpHost[] proxies, boolean secure, RouteInfo.TunnelType tunnelled, RouteInfo.LayerType layered)
/*  46:    */   {
/*  47:138 */     this(local, target, toChain(proxies), secure, tunnelled, layered);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public HttpRoute(HttpHost target, InetAddress local, HttpHost proxy, boolean secure, RouteInfo.TunnelType tunnelled, RouteInfo.LayerType layered)
/*  51:    */   {
/*  52:161 */     this(local, target, toChain(proxy), secure, tunnelled, layered);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public HttpRoute(HttpHost target, InetAddress local, boolean secure)
/*  56:    */   {
/*  57:176 */     this(local, target, EMPTY_HTTP_HOST_ARRAY, secure, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public HttpRoute(HttpHost target)
/*  61:    */   {
/*  62:186 */     this(null, target, EMPTY_HTTP_HOST_ARRAY, false, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public HttpRoute(HttpHost target, InetAddress local, HttpHost proxy, boolean secure)
/*  66:    */   {
/*  67:205 */     this(local, target, toChain(proxy), secure, secure ? RouteInfo.TunnelType.TUNNELLED : RouteInfo.TunnelType.PLAIN, secure ? RouteInfo.LayerType.LAYERED : RouteInfo.LayerType.PLAIN);
/*  68:208 */     if (proxy == null) {
/*  69:209 */       throw new IllegalArgumentException("Proxy host may not be null.");
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static HttpHost[] toChain(HttpHost proxy)
/*  74:    */   {
/*  75:223 */     if (proxy == null) {
/*  76:224 */       return EMPTY_HTTP_HOST_ARRAY;
/*  77:    */     }
/*  78:226 */     return new HttpHost[] { proxy };
/*  79:    */   }
/*  80:    */   
/*  81:    */   private static HttpHost[] toChain(HttpHost[] proxies)
/*  82:    */   {
/*  83:239 */     if ((proxies == null) || (proxies.length < 1)) {
/*  84:240 */       return EMPTY_HTTP_HOST_ARRAY;
/*  85:    */     }
/*  86:242 */     for (HttpHost proxy : proxies) {
/*  87:243 */       if (proxy == null) {
/*  88:244 */         throw new IllegalArgumentException("Proxy chain may not contain null elements.");
/*  89:    */       }
/*  90:    */     }
/*  91:249 */     HttpHost[] result = new HttpHost[proxies.length];
/*  92:250 */     System.arraycopy(proxies, 0, result, 0, proxies.length);
/*  93:    */     
/*  94:252 */     return result;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final HttpHost getTargetHost()
/*  98:    */   {
/*  99:259 */     return this.targetHost;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public final InetAddress getLocalAddress()
/* 103:    */   {
/* 104:265 */     return this.localAddress;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final int getHopCount()
/* 108:    */   {
/* 109:270 */     return this.proxyChain.length + 1;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public final HttpHost getHopTarget(int hop)
/* 113:    */   {
/* 114:275 */     if (hop < 0) {
/* 115:276 */       throw new IllegalArgumentException("Hop index must not be negative: " + hop);
/* 116:    */     }
/* 117:278 */     int hopcount = getHopCount();
/* 118:279 */     if (hop >= hopcount) {
/* 119:280 */       throw new IllegalArgumentException("Hop index " + hop + " exceeds route length " + hopcount);
/* 120:    */     }
/* 121:284 */     HttpHost result = null;
/* 122:285 */     if (hop < hopcount - 1) {
/* 123:286 */       result = this.proxyChain[hop];
/* 124:    */     } else {
/* 125:288 */       result = this.targetHost;
/* 126:    */     }
/* 127:290 */     return result;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public final HttpHost getProxyHost()
/* 131:    */   {
/* 132:295 */     return this.proxyChain.length == 0 ? null : this.proxyChain[0];
/* 133:    */   }
/* 134:    */   
/* 135:    */   public final RouteInfo.TunnelType getTunnelType()
/* 136:    */   {
/* 137:300 */     return this.tunnelled;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public final boolean isTunnelled()
/* 141:    */   {
/* 142:305 */     return this.tunnelled == RouteInfo.TunnelType.TUNNELLED;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public final RouteInfo.LayerType getLayerType()
/* 146:    */   {
/* 147:310 */     return this.layered;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public final boolean isLayered()
/* 151:    */   {
/* 152:315 */     return this.layered == RouteInfo.LayerType.LAYERED;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public final boolean isSecure()
/* 156:    */   {
/* 157:320 */     return this.secure;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public final boolean equals(Object obj)
/* 161:    */   {
/* 162:334 */     if (this == obj) {
/* 163:334 */       return true;
/* 164:    */     }
/* 165:335 */     if ((obj instanceof HttpRoute))
/* 166:    */     {
/* 167:336 */       HttpRoute that = (HttpRoute)obj;
/* 168:337 */       return (this.secure == that.secure) && (this.tunnelled == that.tunnelled) && (this.layered == that.layered) && (LangUtils.equals(this.targetHost, that.targetHost)) && (LangUtils.equals(this.localAddress, that.localAddress)) && (LangUtils.equals(this.proxyChain, that.proxyChain));
/* 169:    */     }
/* 170:346 */     return false;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public final int hashCode()
/* 174:    */   {
/* 175:358 */     int hash = 17;
/* 176:359 */     hash = LangUtils.hashCode(hash, this.targetHost);
/* 177:360 */     hash = LangUtils.hashCode(hash, this.localAddress);
/* 178:361 */     for (int i = 0; i < this.proxyChain.length; i++) {
/* 179:362 */       hash = LangUtils.hashCode(hash, this.proxyChain[i]);
/* 180:    */     }
/* 181:364 */     hash = LangUtils.hashCode(hash, this.secure);
/* 182:365 */     hash = LangUtils.hashCode(hash, this.tunnelled);
/* 183:366 */     hash = LangUtils.hashCode(hash, this.layered);
/* 184:367 */     return hash;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public final String toString()
/* 188:    */   {
/* 189:378 */     StringBuilder cab = new StringBuilder(50 + getHopCount() * 30);
/* 190:    */     
/* 191:380 */     cab.append("HttpRoute[");
/* 192:381 */     if (this.localAddress != null)
/* 193:    */     {
/* 194:382 */       cab.append(this.localAddress);
/* 195:383 */       cab.append("->");
/* 196:    */     }
/* 197:385 */     cab.append('{');
/* 198:386 */     if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED) {
/* 199:387 */       cab.append('t');
/* 200:    */     }
/* 201:388 */     if (this.layered == RouteInfo.LayerType.LAYERED) {
/* 202:389 */       cab.append('l');
/* 203:    */     }
/* 204:390 */     if (this.secure) {
/* 205:391 */       cab.append('s');
/* 206:    */     }
/* 207:392 */     cab.append("}->");
/* 208:393 */     for (HttpHost aProxyChain : this.proxyChain)
/* 209:    */     {
/* 210:394 */       cab.append(aProxyChain);
/* 211:395 */       cab.append("->");
/* 212:    */     }
/* 213:397 */     cab.append(this.targetHost);
/* 214:398 */     cab.append(']');
/* 215:    */     
/* 216:400 */     return cab.toString();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Object clone()
/* 220:    */     throws CloneNotSupportedException
/* 221:    */   {
/* 222:407 */     return super.clone();
/* 223:    */   }
/* 224:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.routing.HttpRoute
 * JD-Core Version:    0.7.0.1
 */