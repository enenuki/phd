/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.Socket;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.http.Header;
/*  10:    */ import org.apache.http.HttpException;
/*  11:    */ import org.apache.http.HttpHost;
/*  12:    */ import org.apache.http.HttpRequest;
/*  13:    */ import org.apache.http.HttpResponse;
/*  14:    */ import org.apache.http.HttpResponseFactory;
/*  15:    */ import org.apache.http.annotation.NotThreadSafe;
/*  16:    */ import org.apache.http.conn.OperatedClientConnection;
/*  17:    */ import org.apache.http.impl.SocketHttpClientConnection;
/*  18:    */ import org.apache.http.io.HttpMessageParser;
/*  19:    */ import org.apache.http.io.SessionInputBuffer;
/*  20:    */ import org.apache.http.io.SessionOutputBuffer;
/*  21:    */ import org.apache.http.params.HttpParams;
/*  22:    */ import org.apache.http.params.HttpProtocolParams;
/*  23:    */ import org.apache.http.protocol.HttpContext;
/*  24:    */ 
/*  25:    */ @NotThreadSafe
/*  26:    */ public class DefaultClientConnection
/*  27:    */   extends SocketHttpClientConnection
/*  28:    */   implements OperatedClientConnection, HttpContext
/*  29:    */ {
/*  30: 74 */   private final Log log = LogFactory.getLog(getClass());
/*  31: 75 */   private final Log headerLog = LogFactory.getLog("org.apache.http.headers");
/*  32: 76 */   private final Log wireLog = LogFactory.getLog("org.apache.http.wire");
/*  33:    */   private volatile Socket socket;
/*  34:    */   private HttpHost targetHost;
/*  35:    */   private boolean connSecure;
/*  36:    */   private volatile boolean shutdown;
/*  37:    */   private final Map<String, Object> attributes;
/*  38:    */   
/*  39:    */   public DefaultClientConnection()
/*  40:    */   {
/*  41: 95 */     this.attributes = new HashMap();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final HttpHost getTargetHost()
/*  45:    */   {
/*  46: 99 */     return this.targetHost;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final boolean isSecure()
/*  50:    */   {
/*  51:103 */     return this.connSecure;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final Socket getSocket()
/*  55:    */   {
/*  56:108 */     return this.socket;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void opening(Socket sock, HttpHost target)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62:112 */     assertNotOpen();
/*  63:113 */     this.socket = sock;
/*  64:114 */     this.targetHost = target;
/*  65:117 */     if (this.shutdown)
/*  66:    */     {
/*  67:118 */       sock.close();
/*  68:    */       
/*  69:120 */       throw new IOException("Connection already shutdown");
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void openCompleted(boolean secure, HttpParams params)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:125 */     assertNotOpen();
/*  77:126 */     if (params == null) {
/*  78:127 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  79:    */     }
/*  80:130 */     this.connSecure = secure;
/*  81:131 */     bind(this.socket, params);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void shutdown()
/*  85:    */     throws IOException
/*  86:    */   {
/*  87:149 */     this.shutdown = true;
/*  88:    */     try
/*  89:    */     {
/*  90:151 */       super.shutdown();
/*  91:152 */       this.log.debug("Connection shut down");
/*  92:153 */       Socket sock = this.socket;
/*  93:154 */       if (sock != null) {
/*  94:155 */         sock.close();
/*  95:    */       }
/*  96:    */     }
/*  97:    */     catch (IOException ex)
/*  98:    */     {
/*  99:157 */       this.log.debug("I/O error shutting down connection", ex);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void close()
/* 104:    */     throws IOException
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:164 */       super.close();
/* 109:165 */       this.log.debug("Connection closed");
/* 110:    */     }
/* 111:    */     catch (IOException ex)
/* 112:    */     {
/* 113:167 */       this.log.debug("I/O error closing connection", ex);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected SessionInputBuffer createSessionInputBuffer(Socket socket, int buffersize, HttpParams params)
/* 118:    */     throws IOException
/* 119:    */   {
/* 120:176 */     if (buffersize == -1) {
/* 121:177 */       buffersize = 8192;
/* 122:    */     }
/* 123:179 */     SessionInputBuffer inbuffer = super.createSessionInputBuffer(socket, buffersize, params);
/* 124:183 */     if (this.wireLog.isDebugEnabled()) {
/* 125:184 */       inbuffer = new LoggingSessionInputBuffer(inbuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(params));
/* 126:    */     }
/* 127:189 */     return inbuffer;
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected SessionOutputBuffer createSessionOutputBuffer(Socket socket, int buffersize, HttpParams params)
/* 131:    */     throws IOException
/* 132:    */   {
/* 133:197 */     if (buffersize == -1) {
/* 134:198 */       buffersize = 8192;
/* 135:    */     }
/* 136:200 */     SessionOutputBuffer outbuffer = super.createSessionOutputBuffer(socket, buffersize, params);
/* 137:204 */     if (this.wireLog.isDebugEnabled()) {
/* 138:205 */       outbuffer = new LoggingSessionOutputBuffer(outbuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(params));
/* 139:    */     }
/* 140:210 */     return outbuffer;
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected HttpMessageParser createResponseParser(SessionInputBuffer buffer, HttpResponseFactory responseFactory, HttpParams params)
/* 144:    */   {
/* 145:219 */     return new DefaultResponseParser(buffer, null, responseFactory, params);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void update(Socket sock, HttpHost target, boolean secure, HttpParams params)
/* 149:    */     throws IOException
/* 150:    */   {
/* 151:227 */     assertOpen();
/* 152:228 */     if (target == null) {
/* 153:229 */       throw new IllegalArgumentException("Target host must not be null.");
/* 154:    */     }
/* 155:232 */     if (params == null) {
/* 156:233 */       throw new IllegalArgumentException("Parameters must not be null.");
/* 157:    */     }
/* 158:237 */     if (sock != null)
/* 159:    */     {
/* 160:238 */       this.socket = sock;
/* 161:239 */       bind(sock, params);
/* 162:    */     }
/* 163:241 */     this.targetHost = target;
/* 164:242 */     this.connSecure = secure;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public HttpResponse receiveResponseHeader()
/* 168:    */     throws HttpException, IOException
/* 169:    */   {
/* 170:247 */     HttpResponse response = super.receiveResponseHeader();
/* 171:248 */     if (this.log.isDebugEnabled()) {
/* 172:249 */       this.log.debug("Receiving response: " + response.getStatusLine());
/* 173:    */     }
/* 174:251 */     if (this.headerLog.isDebugEnabled())
/* 175:    */     {
/* 176:252 */       this.headerLog.debug("<< " + response.getStatusLine().toString());
/* 177:253 */       Header[] headers = response.getAllHeaders();
/* 178:254 */       for (Header header : headers) {
/* 179:255 */         this.headerLog.debug("<< " + header.toString());
/* 180:    */       }
/* 181:    */     }
/* 182:258 */     return response;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void sendRequestHeader(HttpRequest request)
/* 186:    */     throws HttpException, IOException
/* 187:    */   {
/* 188:263 */     if (this.log.isDebugEnabled()) {
/* 189:264 */       this.log.debug("Sending request: " + request.getRequestLine());
/* 190:    */     }
/* 191:266 */     super.sendRequestHeader(request);
/* 192:267 */     if (this.headerLog.isDebugEnabled())
/* 193:    */     {
/* 194:268 */       this.headerLog.debug(">> " + request.getRequestLine().toString());
/* 195:269 */       Header[] headers = request.getAllHeaders();
/* 196:270 */       for (Header header : headers) {
/* 197:271 */         this.headerLog.debug(">> " + header.toString());
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Object getAttribute(String id)
/* 203:    */   {
/* 204:277 */     return this.attributes.get(id);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Object removeAttribute(String id)
/* 208:    */   {
/* 209:281 */     return this.attributes.remove(id);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setAttribute(String id, Object obj)
/* 213:    */   {
/* 214:285 */     this.attributes.put(id, obj);
/* 215:    */   }
/* 216:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.DefaultClientConnection
 * JD-Core Version:    0.7.0.1
 */