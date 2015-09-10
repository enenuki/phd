/*   1:    */ package org.apache.http.conn.ssl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.InetSocketAddress;
/*   6:    */ import java.net.Socket;
/*   7:    */ import java.net.SocketTimeoutException;
/*   8:    */ import java.net.UnknownHostException;
/*   9:    */ import java.security.KeyManagementException;
/*  10:    */ import java.security.KeyStore;
/*  11:    */ import java.security.KeyStoreException;
/*  12:    */ import java.security.NoSuchAlgorithmException;
/*  13:    */ import java.security.SecureRandom;
/*  14:    */ import java.security.UnrecoverableKeyException;
/*  15:    */ import javax.net.ssl.KeyManager;
/*  16:    */ import javax.net.ssl.KeyManagerFactory;
/*  17:    */ import javax.net.ssl.SSLContext;
/*  18:    */ import javax.net.ssl.SSLSocket;
/*  19:    */ import javax.net.ssl.TrustManager;
/*  20:    */ import javax.net.ssl.TrustManagerFactory;
/*  21:    */ import javax.net.ssl.X509TrustManager;
/*  22:    */ import org.apache.http.annotation.ThreadSafe;
/*  23:    */ import org.apache.http.conn.ConnectTimeoutException;
/*  24:    */ import org.apache.http.conn.scheme.HostNameResolver;
/*  25:    */ import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
/*  26:    */ import org.apache.http.conn.scheme.LayeredSocketFactory;
/*  27:    */ import org.apache.http.params.HttpConnectionParams;
/*  28:    */ import org.apache.http.params.HttpParams;
/*  29:    */ 
/*  30:    */ @ThreadSafe
/*  31:    */ public class SSLSocketFactory
/*  32:    */   implements LayeredSchemeSocketFactory, LayeredSocketFactory
/*  33:    */ {
/*  34:    */   public static final String TLS = "TLS";
/*  35:    */   public static final String SSL = "SSL";
/*  36:    */   public static final String SSLV2 = "SSLv2";
/*  37:149 */   public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
/*  38:152 */   public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
/*  39:155 */   public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
/*  40:    */   private final javax.net.ssl.SSLSocketFactory socketfactory;
/*  41:    */   private final HostNameResolver nameResolver;
/*  42:    */   private volatile X509HostnameVerifier hostnameVerifier;
/*  43:    */   
/*  44:    */   public static SSLSocketFactory getSocketFactory()
/*  45:    */   {
/*  46:165 */     return new SSLSocketFactory();
/*  47:    */   }
/*  48:    */   
/*  49:    */   private static SSLContext createSSLContext(String algorithm, KeyStore keystore, String keystorePassword, KeyStore truststore, SecureRandom random, TrustStrategy trustStrategy)
/*  50:    */     throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException
/*  51:    */   {
/*  52:181 */     if (algorithm == null) {
/*  53:182 */       algorithm = "TLS";
/*  54:    */     }
/*  55:184 */     KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/*  56:    */     
/*  57:186 */     kmfactory.init(keystore, keystorePassword != null ? keystorePassword.toCharArray() : null);
/*  58:187 */     KeyManager[] keymanagers = kmfactory.getKeyManagers();
/*  59:188 */     TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/*  60:    */     
/*  61:190 */     tmfactory.init(truststore);
/*  62:191 */     TrustManager[] trustmanagers = tmfactory.getTrustManagers();
/*  63:192 */     if ((trustmanagers != null) && (trustStrategy != null)) {
/*  64:193 */       for (int i = 0; i < trustmanagers.length; i++)
/*  65:    */       {
/*  66:194 */         TrustManager tm = trustmanagers[i];
/*  67:195 */         if ((tm instanceof X509TrustManager)) {
/*  68:196 */           trustmanagers[i] = new TrustManagerDecorator((X509TrustManager)tm, trustStrategy);
/*  69:    */         }
/*  70:    */       }
/*  71:    */     }
/*  72:202 */     SSLContext sslcontext = SSLContext.getInstance(algorithm);
/*  73:203 */     sslcontext.init(keymanagers, trustmanagers, random);
/*  74:204 */     return sslcontext;
/*  75:    */   }
/*  76:    */   
/*  77:    */   private static SSLContext createDefaultSSLContext()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:209 */       return createSSLContext("TLS", null, null, null, null, null);
/*  82:    */     }
/*  83:    */     catch (Exception ex)
/*  84:    */     {
/*  85:211 */       throw new IllegalStateException("Failure initializing default SSL context", ex);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   @Deprecated
/*  90:    */   public SSLSocketFactory(String algorithm, KeyStore keystore, String keystorePassword, KeyStore truststore, SecureRandom random, HostNameResolver nameResolver)
/*  91:    */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/*  92:    */   {
/*  93:227 */     this(createSSLContext(algorithm, keystore, keystorePassword, truststore, random, null), nameResolver);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public SSLSocketFactory(String algorithm, KeyStore keystore, String keystorePassword, KeyStore truststore, SecureRandom random, X509HostnameVerifier hostnameVerifier)
/*  97:    */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/*  98:    */   {
/*  99:243 */     this(createSSLContext(algorithm, keystore, keystorePassword, truststore, random, null), hostnameVerifier);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public SSLSocketFactory(String algorithm, KeyStore keystore, String keystorePassword, KeyStore truststore, SecureRandom random, TrustStrategy trustStrategy, X509HostnameVerifier hostnameVerifier)
/* 103:    */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/* 104:    */   {
/* 105:260 */     this(createSSLContext(algorithm, keystore, keystorePassword, truststore, random, trustStrategy), hostnameVerifier);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public SSLSocketFactory(KeyStore keystore, String keystorePassword, KeyStore truststore)
/* 109:    */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/* 110:    */   {
/* 111:270 */     this("TLS", keystore, keystorePassword, truststore, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public SSLSocketFactory(KeyStore keystore, String keystorePassword)
/* 115:    */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/* 116:    */   {
/* 117:277 */     this("TLS", keystore, keystorePassword, null, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public SSLSocketFactory(KeyStore truststore)
/* 121:    */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/* 122:    */   {
/* 123:283 */     this("TLS", null, null, truststore, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public SSLSocketFactory(TrustStrategy trustStrategy, X509HostnameVerifier hostnameVerifier)
/* 127:    */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/* 128:    */   {
/* 129:293 */     this("TLS", null, null, null, null, trustStrategy, hostnameVerifier);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public SSLSocketFactory(TrustStrategy trustStrategy)
/* 133:    */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/* 134:    */   {
/* 135:302 */     this("TLS", null, null, null, null, trustStrategy, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public SSLSocketFactory(SSLContext sslContext)
/* 139:    */   {
/* 140:306 */     this(sslContext, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
/* 141:    */   }
/* 142:    */   
/* 143:    */   @Deprecated
/* 144:    */   public SSLSocketFactory(SSLContext sslContext, HostNameResolver nameResolver)
/* 145:    */   {
/* 146:316 */     this.socketfactory = sslContext.getSocketFactory();
/* 147:317 */     this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
/* 148:318 */     this.nameResolver = nameResolver;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public SSLSocketFactory(SSLContext sslContext, X509HostnameVerifier hostnameVerifier)
/* 152:    */   {
/* 153:327 */     this.socketfactory = sslContext.getSocketFactory();
/* 154:328 */     this.hostnameVerifier = hostnameVerifier;
/* 155:329 */     this.nameResolver = null;
/* 156:    */   }
/* 157:    */   
/* 158:    */   private SSLSocketFactory()
/* 159:    */   {
/* 160:333 */     this(createDefaultSSLContext());
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Socket createSocket(HttpParams params)
/* 164:    */     throws IOException
/* 165:    */   {
/* 166:342 */     return this.socketfactory.createSocket();
/* 167:    */   }
/* 168:    */   
/* 169:    */   @Deprecated
/* 170:    */   public Socket createSocket()
/* 171:    */     throws IOException
/* 172:    */   {
/* 173:347 */     return this.socketfactory.createSocket();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Socket connectSocket(Socket socket, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpParams params)
/* 177:    */     throws IOException, UnknownHostException, ConnectTimeoutException
/* 178:    */   {
/* 179:358 */     if (remoteAddress == null) {
/* 180:359 */       throw new IllegalArgumentException("Remote address may not be null");
/* 181:    */     }
/* 182:361 */     if (params == null) {
/* 183:362 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 184:    */     }
/* 185:364 */     Socket sock = socket != null ? socket : new Socket();
/* 186:365 */     if (localAddress != null)
/* 187:    */     {
/* 188:366 */       sock.setReuseAddress(HttpConnectionParams.getSoReuseaddr(params));
/* 189:367 */       sock.bind(localAddress);
/* 190:    */     }
/* 191:370 */     int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
/* 192:371 */     int soTimeout = HttpConnectionParams.getSoTimeout(params);
/* 193:    */     try
/* 194:    */     {
/* 195:374 */       sock.setSoTimeout(soTimeout);
/* 196:375 */       sock.connect(remoteAddress, connTimeout);
/* 197:    */     }
/* 198:    */     catch (SocketTimeoutException ex)
/* 199:    */     {
/* 200:377 */       throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
/* 201:    */     }
/* 202:381 */     String hostname = remoteAddress.toString();
/* 203:382 */     int port = remoteAddress.getPort();
/* 204:383 */     String s = ":" + port;
/* 205:384 */     if (hostname.endsWith(s)) {
/* 206:385 */       hostname = hostname.substring(0, hostname.length() - s.length());
/* 207:    */     }
/* 208:    */     SSLSocket sslsock;
/* 209:    */     SSLSocket sslsock;
/* 210:390 */     if ((sock instanceof SSLSocket)) {
/* 211:391 */       sslsock = (SSLSocket)sock;
/* 212:    */     } else {
/* 213:393 */       sslsock = (SSLSocket)this.socketfactory.createSocket(sock, hostname, port, true);
/* 214:    */     }
/* 215:395 */     if (this.hostnameVerifier != null) {
/* 216:    */       try
/* 217:    */       {
/* 218:397 */         this.hostnameVerifier.verify(hostname, sslsock);
/* 219:    */       }
/* 220:    */       catch (IOException iox)
/* 221:    */       {
/* 222:    */         try
/* 223:    */         {
/* 224:401 */           sslsock.close();
/* 225:    */         }
/* 226:    */         catch (Exception x) {}
/* 227:402 */         throw iox;
/* 228:    */       }
/* 229:    */     }
/* 230:405 */     return sslsock;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isSecure(Socket sock)
/* 234:    */     throws IllegalArgumentException
/* 235:    */   {
/* 236:424 */     if (sock == null) {
/* 237:425 */       throw new IllegalArgumentException("Socket may not be null");
/* 238:    */     }
/* 239:428 */     if (!(sock instanceof SSLSocket)) {
/* 240:429 */       throw new IllegalArgumentException("Socket not created by this factory");
/* 241:    */     }
/* 242:432 */     if (sock.isClosed()) {
/* 243:433 */       throw new IllegalArgumentException("Socket is closed");
/* 244:    */     }
/* 245:435 */     return true;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public Socket createLayeredSocket(Socket socket, String host, int port, boolean autoClose)
/* 249:    */     throws IOException, UnknownHostException
/* 250:    */   {
/* 251:446 */     SSLSocket sslSocket = (SSLSocket)this.socketfactory.createSocket(socket, host, port, autoClose);
/* 252:452 */     if (this.hostnameVerifier != null) {
/* 253:453 */       this.hostnameVerifier.verify(host, sslSocket);
/* 254:    */     }
/* 255:456 */     return sslSocket;
/* 256:    */   }
/* 257:    */   
/* 258:    */   @Deprecated
/* 259:    */   public void setHostnameVerifier(X509HostnameVerifier hostnameVerifier)
/* 260:    */   {
/* 261:461 */     if (hostnameVerifier == null) {
/* 262:462 */       throw new IllegalArgumentException("Hostname verifier may not be null");
/* 263:    */     }
/* 264:464 */     this.hostnameVerifier = hostnameVerifier;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public X509HostnameVerifier getHostnameVerifier()
/* 268:    */   {
/* 269:468 */     return this.hostnameVerifier;
/* 270:    */   }
/* 271:    */   
/* 272:    */   @Deprecated
/* 273:    */   public Socket connectSocket(Socket socket, String host, int port, InetAddress localAddress, int localPort, HttpParams params)
/* 274:    */     throws IOException, UnknownHostException, ConnectTimeoutException
/* 275:    */   {
/* 276:480 */     InetSocketAddress local = null;
/* 277:481 */     if ((localAddress != null) || (localPort > 0))
/* 278:    */     {
/* 279:483 */       if (localPort < 0) {
/* 280:484 */         localPort = 0;
/* 281:    */       }
/* 282:486 */       local = new InetSocketAddress(localAddress, localPort);
/* 283:    */     }
/* 284:    */     InetAddress remoteAddress;
/* 285:    */     InetAddress remoteAddress;
/* 286:489 */     if (this.nameResolver != null) {
/* 287:490 */       remoteAddress = this.nameResolver.resolve(host);
/* 288:    */     } else {
/* 289:492 */       remoteAddress = InetAddress.getByName(host);
/* 290:    */     }
/* 291:494 */     InetSocketAddress remote = new InetSocketAddress(remoteAddress, port);
/* 292:495 */     return connectSocket(socket, remote, local, params);
/* 293:    */   }
/* 294:    */   
/* 295:    */   @Deprecated
/* 296:    */   public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
/* 297:    */     throws IOException, UnknownHostException
/* 298:    */   {
/* 299:506 */     return createLayeredSocket(socket, host, port, autoClose);
/* 300:    */   }
/* 301:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ssl.SSLSocketFactory
 * JD-Core Version:    0.7.0.1
 */