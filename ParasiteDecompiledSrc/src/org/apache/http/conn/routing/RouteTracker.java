/*   1:    */ package org.apache.http.conn.routing;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import org.apache.http.HttpHost;
/*   5:    */ import org.apache.http.annotation.NotThreadSafe;
/*   6:    */ import org.apache.http.util.LangUtils;
/*   7:    */ 
/*   8:    */ @NotThreadSafe
/*   9:    */ public final class RouteTracker
/*  10:    */   implements RouteInfo, Cloneable
/*  11:    */ {
/*  12:    */   private final HttpHost targetHost;
/*  13:    */   private final InetAddress localAddress;
/*  14:    */   private boolean connected;
/*  15:    */   private HttpHost[] proxyChain;
/*  16:    */   private RouteInfo.TunnelType tunnelled;
/*  17:    */   private RouteInfo.LayerType layered;
/*  18:    */   private boolean secure;
/*  19:    */   
/*  20:    */   public RouteTracker(HttpHost target, InetAddress local)
/*  21:    */   {
/*  22: 81 */     if (target == null) {
/*  23: 82 */       throw new IllegalArgumentException("Target host may not be null.");
/*  24:    */     }
/*  25: 84 */     this.targetHost = target;
/*  26: 85 */     this.localAddress = local;
/*  27: 86 */     this.tunnelled = RouteInfo.TunnelType.PLAIN;
/*  28: 87 */     this.layered = RouteInfo.LayerType.PLAIN;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public RouteTracker(HttpRoute route)
/*  32:    */   {
/*  33: 99 */     this(route.getTargetHost(), route.getLocalAddress());
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final void connectTarget(boolean secure)
/*  37:    */   {
/*  38:109 */     if (this.connected) {
/*  39:110 */       throw new IllegalStateException("Already connected.");
/*  40:    */     }
/*  41:112 */     this.connected = true;
/*  42:113 */     this.secure = secure;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final void connectProxy(HttpHost proxy, boolean secure)
/*  46:    */   {
/*  47:124 */     if (proxy == null) {
/*  48:125 */       throw new IllegalArgumentException("Proxy host may not be null.");
/*  49:    */     }
/*  50:127 */     if (this.connected) {
/*  51:128 */       throw new IllegalStateException("Already connected.");
/*  52:    */     }
/*  53:130 */     this.connected = true;
/*  54:131 */     this.proxyChain = new HttpHost[] { proxy };
/*  55:132 */     this.secure = secure;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final void tunnelTarget(boolean secure)
/*  59:    */   {
/*  60:142 */     if (!this.connected) {
/*  61:143 */       throw new IllegalStateException("No tunnel unless connected.");
/*  62:    */     }
/*  63:145 */     if (this.proxyChain == null) {
/*  64:146 */       throw new IllegalStateException("No tunnel without proxy.");
/*  65:    */     }
/*  66:148 */     this.tunnelled = RouteInfo.TunnelType.TUNNELLED;
/*  67:149 */     this.secure = secure;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final void tunnelProxy(HttpHost proxy, boolean secure)
/*  71:    */   {
/*  72:162 */     if (proxy == null) {
/*  73:163 */       throw new IllegalArgumentException("Proxy host may not be null.");
/*  74:    */     }
/*  75:165 */     if (!this.connected) {
/*  76:166 */       throw new IllegalStateException("No tunnel unless connected.");
/*  77:    */     }
/*  78:168 */     if (this.proxyChain == null) {
/*  79:169 */       throw new IllegalStateException("No proxy tunnel without proxy.");
/*  80:    */     }
/*  81:173 */     HttpHost[] proxies = new HttpHost[this.proxyChain.length + 1];
/*  82:174 */     System.arraycopy(this.proxyChain, 0, proxies, 0, this.proxyChain.length);
/*  83:    */     
/*  84:176 */     proxies[(proxies.length - 1)] = proxy;
/*  85:    */     
/*  86:178 */     this.proxyChain = proxies;
/*  87:179 */     this.secure = secure;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public final void layerProtocol(boolean secure)
/*  91:    */   {
/*  92:191 */     if (!this.connected) {
/*  93:192 */       throw new IllegalStateException("No layered protocol unless connected.");
/*  94:    */     }
/*  95:195 */     this.layered = RouteInfo.LayerType.LAYERED;
/*  96:196 */     this.secure = secure;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public final HttpHost getTargetHost()
/* 100:    */   {
/* 101:200 */     return this.targetHost;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public final InetAddress getLocalAddress()
/* 105:    */   {
/* 106:204 */     return this.localAddress;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public final int getHopCount()
/* 110:    */   {
/* 111:208 */     int hops = 0;
/* 112:209 */     if (this.connected) {
/* 113:210 */       if (this.proxyChain == null) {
/* 114:211 */         hops = 1;
/* 115:    */       } else {
/* 116:213 */         hops = this.proxyChain.length + 1;
/* 117:    */       }
/* 118:    */     }
/* 119:215 */     return hops;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final HttpHost getHopTarget(int hop)
/* 123:    */   {
/* 124:219 */     if (hop < 0) {
/* 125:220 */       throw new IllegalArgumentException("Hop index must not be negative: " + hop);
/* 126:    */     }
/* 127:222 */     int hopcount = getHopCount();
/* 128:223 */     if (hop >= hopcount) {
/* 129:224 */       throw new IllegalArgumentException("Hop index " + hop + " exceeds tracked route length " + hopcount + ".");
/* 130:    */     }
/* 131:229 */     HttpHost result = null;
/* 132:230 */     if (hop < hopcount - 1) {
/* 133:231 */       result = this.proxyChain[hop];
/* 134:    */     } else {
/* 135:233 */       result = this.targetHost;
/* 136:    */     }
/* 137:235 */     return result;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public final HttpHost getProxyHost()
/* 141:    */   {
/* 142:239 */     return this.proxyChain == null ? null : this.proxyChain[0];
/* 143:    */   }
/* 144:    */   
/* 145:    */   public final boolean isConnected()
/* 146:    */   {
/* 147:243 */     return this.connected;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public final RouteInfo.TunnelType getTunnelType()
/* 151:    */   {
/* 152:247 */     return this.tunnelled;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public final boolean isTunnelled()
/* 156:    */   {
/* 157:251 */     return this.tunnelled == RouteInfo.TunnelType.TUNNELLED;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public final RouteInfo.LayerType getLayerType()
/* 161:    */   {
/* 162:255 */     return this.layered;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public final boolean isLayered()
/* 166:    */   {
/* 167:259 */     return this.layered == RouteInfo.LayerType.LAYERED;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public final boolean isSecure()
/* 171:    */   {
/* 172:263 */     return this.secure;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public final HttpRoute toRoute()
/* 176:    */   {
/* 177:275 */     return !this.connected ? null : new HttpRoute(this.targetHost, this.localAddress, this.proxyChain, this.secure, this.tunnelled, this.layered);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public final boolean equals(Object o)
/* 181:    */   {
/* 182:291 */     if (o == this) {
/* 183:292 */       return true;
/* 184:    */     }
/* 185:293 */     if (!(o instanceof RouteTracker)) {
/* 186:294 */       return false;
/* 187:    */     }
/* 188:296 */     RouteTracker that = (RouteTracker)o;
/* 189:297 */     return (this.connected == that.connected) && (this.secure == that.secure) && (this.tunnelled == that.tunnelled) && (this.layered == that.layered) && (LangUtils.equals(this.targetHost, that.targetHost)) && (LangUtils.equals(this.localAddress, that.localAddress)) && (LangUtils.equals(this.proxyChain, that.proxyChain));
/* 190:    */   }
/* 191:    */   
/* 192:    */   public final int hashCode()
/* 193:    */   {
/* 194:318 */     int hash = 17;
/* 195:319 */     hash = LangUtils.hashCode(hash, this.targetHost);
/* 196:320 */     hash = LangUtils.hashCode(hash, this.localAddress);
/* 197:321 */     if (this.proxyChain != null) {
/* 198:322 */       for (int i = 0; i < this.proxyChain.length; i++) {
/* 199:323 */         hash = LangUtils.hashCode(hash, this.proxyChain[i]);
/* 200:    */       }
/* 201:    */     }
/* 202:326 */     hash = LangUtils.hashCode(hash, this.connected);
/* 203:327 */     hash = LangUtils.hashCode(hash, this.secure);
/* 204:328 */     hash = LangUtils.hashCode(hash, this.tunnelled);
/* 205:329 */     hash = LangUtils.hashCode(hash, this.layered);
/* 206:330 */     return hash;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public final String toString()
/* 210:    */   {
/* 211:340 */     StringBuilder cab = new StringBuilder(50 + getHopCount() * 30);
/* 212:    */     
/* 213:342 */     cab.append("RouteTracker[");
/* 214:343 */     if (this.localAddress != null)
/* 215:    */     {
/* 216:344 */       cab.append(this.localAddress);
/* 217:345 */       cab.append("->");
/* 218:    */     }
/* 219:347 */     cab.append('{');
/* 220:348 */     if (this.connected) {
/* 221:349 */       cab.append('c');
/* 222:    */     }
/* 223:350 */     if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED) {
/* 224:351 */       cab.append('t');
/* 225:    */     }
/* 226:352 */     if (this.layered == RouteInfo.LayerType.LAYERED) {
/* 227:353 */       cab.append('l');
/* 228:    */     }
/* 229:354 */     if (this.secure) {
/* 230:355 */       cab.append('s');
/* 231:    */     }
/* 232:356 */     cab.append("}->");
/* 233:357 */     if (this.proxyChain != null) {
/* 234:358 */       for (int i = 0; i < this.proxyChain.length; i++)
/* 235:    */       {
/* 236:359 */         cab.append(this.proxyChain[i]);
/* 237:360 */         cab.append("->");
/* 238:    */       }
/* 239:    */     }
/* 240:363 */     cab.append(this.targetHost);
/* 241:364 */     cab.append(']');
/* 242:    */     
/* 243:366 */     return cab.toString();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Object clone()
/* 247:    */     throws CloneNotSupportedException
/* 248:    */   {
/* 249:373 */     return super.clone();
/* 250:    */   }
/* 251:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.routing.RouteTracker
 * JD-Core Version:    0.7.0.1
 */