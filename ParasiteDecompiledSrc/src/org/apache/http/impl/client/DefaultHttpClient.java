/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import org.apache.http.HttpVersion;
/*   4:    */ import org.apache.http.annotation.ThreadSafe;
/*   5:    */ import org.apache.http.client.protocol.RequestAddCookies;
/*   6:    */ import org.apache.http.client.protocol.RequestAuthCache;
/*   7:    */ import org.apache.http.client.protocol.RequestClientConnControl;
/*   8:    */ import org.apache.http.client.protocol.RequestDefaultHeaders;
/*   9:    */ import org.apache.http.client.protocol.RequestProxyAuthentication;
/*  10:    */ import org.apache.http.client.protocol.RequestTargetAuthentication;
/*  11:    */ import org.apache.http.client.protocol.ResponseAuthCache;
/*  12:    */ import org.apache.http.client.protocol.ResponseProcessCookies;
/*  13:    */ import org.apache.http.conn.ClientConnectionManager;
/*  14:    */ import org.apache.http.params.HttpConnectionParams;
/*  15:    */ import org.apache.http.params.HttpParams;
/*  16:    */ import org.apache.http.params.HttpProtocolParams;
/*  17:    */ import org.apache.http.params.SyncBasicHttpParams;
/*  18:    */ import org.apache.http.protocol.BasicHttpProcessor;
/*  19:    */ import org.apache.http.protocol.RequestContent;
/*  20:    */ import org.apache.http.protocol.RequestExpectContinue;
/*  21:    */ import org.apache.http.protocol.RequestTargetHost;
/*  22:    */ import org.apache.http.protocol.RequestUserAgent;
/*  23:    */ import org.apache.http.util.VersionInfo;
/*  24:    */ 
/*  25:    */ @ThreadSafe
/*  26:    */ public class DefaultHttpClient
/*  27:    */   extends AbstractHttpClient
/*  28:    */ {
/*  29:    */   public DefaultHttpClient(ClientConnectionManager conman, HttpParams params)
/*  30:    */   {
/*  31:131 */     super(conman, params);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DefaultHttpClient(ClientConnectionManager conman)
/*  35:    */   {
/*  36:140 */     super(conman, null);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public DefaultHttpClient(HttpParams params)
/*  40:    */   {
/*  41:145 */     super(null, params);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public DefaultHttpClient()
/*  45:    */   {
/*  46:150 */     super(null, null);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected HttpParams createHttpParams()
/*  50:    */   {
/*  51:161 */     HttpParams params = new SyncBasicHttpParams();
/*  52:162 */     setDefaultHttpParams(params);
/*  53:163 */     return params;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void setDefaultHttpParams(HttpParams params)
/*  57:    */   {
/*  58:178 */     HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
/*  59:179 */     HttpProtocolParams.setContentCharset(params, "ISO-8859-1");
/*  60:180 */     HttpConnectionParams.setTcpNoDelay(params, true);
/*  61:181 */     HttpConnectionParams.setSocketBufferSize(params, 8192);
/*  62:    */     
/*  63:    */ 
/*  64:184 */     VersionInfo vi = VersionInfo.loadVersionInfo("org.apache.http.client", DefaultHttpClient.class.getClassLoader());
/*  65:    */     
/*  66:186 */     String release = vi != null ? vi.getRelease() : "UNAVAILABLE";
/*  67:    */     
/*  68:188 */     HttpProtocolParams.setUserAgent(params, "Apache-HttpClient/" + release + " (java 1.5)");
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected BasicHttpProcessor createHttpProcessor()
/*  72:    */   {
/*  73:195 */     BasicHttpProcessor httpproc = new BasicHttpProcessor();
/*  74:196 */     httpproc.addInterceptor(new RequestDefaultHeaders());
/*  75:    */     
/*  76:198 */     httpproc.addInterceptor(new RequestContent());
/*  77:199 */     httpproc.addInterceptor(new RequestTargetHost());
/*  78:    */     
/*  79:201 */     httpproc.addInterceptor(new RequestClientConnControl());
/*  80:202 */     httpproc.addInterceptor(new RequestUserAgent());
/*  81:203 */     httpproc.addInterceptor(new RequestExpectContinue());
/*  82:    */     
/*  83:205 */     httpproc.addInterceptor(new RequestAddCookies());
/*  84:206 */     httpproc.addInterceptor(new ResponseProcessCookies());
/*  85:    */     
/*  86:208 */     httpproc.addInterceptor(new RequestAuthCache());
/*  87:209 */     httpproc.addInterceptor(new ResponseAuthCache());
/*  88:210 */     httpproc.addInterceptor(new RequestTargetAuthentication());
/*  89:211 */     httpproc.addInterceptor(new RequestProxyAuthentication());
/*  90:212 */     return httpproc;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultHttpClient
 * JD-Core Version:    0.7.0.1
 */