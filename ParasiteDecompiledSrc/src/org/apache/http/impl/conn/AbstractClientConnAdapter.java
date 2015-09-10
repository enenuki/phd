/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InterruptedIOException;
/*   5:    */ import java.net.InetAddress;
/*   6:    */ import java.net.Socket;
/*   7:    */ import java.util.concurrent.TimeUnit;
/*   8:    */ import javax.net.ssl.SSLSession;
/*   9:    */ import javax.net.ssl.SSLSocket;
/*  10:    */ import org.apache.http.HttpConnectionMetrics;
/*  11:    */ import org.apache.http.HttpEntityEnclosingRequest;
/*  12:    */ import org.apache.http.HttpException;
/*  13:    */ import org.apache.http.HttpRequest;
/*  14:    */ import org.apache.http.HttpResponse;
/*  15:    */ import org.apache.http.conn.ClientConnectionManager;
/*  16:    */ import org.apache.http.conn.ManagedClientConnection;
/*  17:    */ import org.apache.http.conn.OperatedClientConnection;
/*  18:    */ import org.apache.http.protocol.HttpContext;
/*  19:    */ 
/*  20:    */ public abstract class AbstractClientConnAdapter
/*  21:    */   implements ManagedClientConnection, HttpContext
/*  22:    */ {
/*  23:    */   private volatile ClientConnectionManager connManager;
/*  24:    */   private volatile OperatedClientConnection wrappedConnection;
/*  25:    */   private volatile boolean markedReusable;
/*  26:    */   private volatile boolean released;
/*  27:    */   private volatile long duration;
/*  28:    */   
/*  29:    */   protected AbstractClientConnAdapter(ClientConnectionManager mgr, OperatedClientConnection conn)
/*  30:    */   {
/*  31:102 */     this.connManager = mgr;
/*  32:103 */     this.wrappedConnection = conn;
/*  33:104 */     this.markedReusable = false;
/*  34:105 */     this.released = false;
/*  35:106 */     this.duration = 9223372036854775807L;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected synchronized void detach()
/*  39:    */   {
/*  40:114 */     this.wrappedConnection = null;
/*  41:115 */     this.connManager = null;
/*  42:116 */     this.duration = 9223372036854775807L;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected OperatedClientConnection getWrappedConnection()
/*  46:    */   {
/*  47:120 */     return this.wrappedConnection;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected ClientConnectionManager getManager()
/*  51:    */   {
/*  52:124 */     return this.connManager;
/*  53:    */   }
/*  54:    */   
/*  55:    */   @Deprecated
/*  56:    */   protected final void assertNotAborted()
/*  57:    */     throws InterruptedIOException
/*  58:    */   {
/*  59:132 */     if (isReleased()) {
/*  60:133 */       throw new InterruptedIOException("Connection has been shut down");
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected boolean isReleased()
/*  65:    */   {
/*  66:142 */     return this.released;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected final void assertValid(OperatedClientConnection wrappedConn)
/*  70:    */     throws ConnectionShutdownException
/*  71:    */   {
/*  72:153 */     if ((isReleased()) || (wrappedConn == null)) {
/*  73:154 */       throw new ConnectionShutdownException();
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isOpen()
/*  78:    */   {
/*  79:159 */     OperatedClientConnection conn = getWrappedConnection();
/*  80:160 */     if (conn == null) {
/*  81:161 */       return false;
/*  82:    */     }
/*  83:163 */     return conn.isOpen();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isStale()
/*  87:    */   {
/*  88:167 */     if (isReleased()) {
/*  89:168 */       return true;
/*  90:    */     }
/*  91:169 */     OperatedClientConnection conn = getWrappedConnection();
/*  92:170 */     if (conn == null) {
/*  93:171 */       return true;
/*  94:    */     }
/*  95:173 */     return conn.isStale();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setSocketTimeout(int timeout)
/*  99:    */   {
/* 100:177 */     OperatedClientConnection conn = getWrappedConnection();
/* 101:178 */     assertValid(conn);
/* 102:179 */     conn.setSocketTimeout(timeout);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getSocketTimeout()
/* 106:    */   {
/* 107:183 */     OperatedClientConnection conn = getWrappedConnection();
/* 108:184 */     assertValid(conn);
/* 109:185 */     return conn.getSocketTimeout();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public HttpConnectionMetrics getMetrics()
/* 113:    */   {
/* 114:189 */     OperatedClientConnection conn = getWrappedConnection();
/* 115:190 */     assertValid(conn);
/* 116:191 */     return conn.getMetrics();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void flush()
/* 120:    */     throws IOException
/* 121:    */   {
/* 122:195 */     OperatedClientConnection conn = getWrappedConnection();
/* 123:196 */     assertValid(conn);
/* 124:197 */     conn.flush();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isResponseAvailable(int timeout)
/* 128:    */     throws IOException
/* 129:    */   {
/* 130:201 */     OperatedClientConnection conn = getWrappedConnection();
/* 131:202 */     assertValid(conn);
/* 132:203 */     return conn.isResponseAvailable(timeout);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void receiveResponseEntity(HttpResponse response)
/* 136:    */     throws HttpException, IOException
/* 137:    */   {
/* 138:208 */     OperatedClientConnection conn = getWrappedConnection();
/* 139:209 */     assertValid(conn);
/* 140:210 */     unmarkReusable();
/* 141:211 */     conn.receiveResponseEntity(response);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public HttpResponse receiveResponseHeader()
/* 145:    */     throws HttpException, IOException
/* 146:    */   {
/* 147:216 */     OperatedClientConnection conn = getWrappedConnection();
/* 148:217 */     assertValid(conn);
/* 149:218 */     unmarkReusable();
/* 150:219 */     return conn.receiveResponseHeader();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void sendRequestEntity(HttpEntityEnclosingRequest request)
/* 154:    */     throws HttpException, IOException
/* 155:    */   {
/* 156:224 */     OperatedClientConnection conn = getWrappedConnection();
/* 157:225 */     assertValid(conn);
/* 158:226 */     unmarkReusable();
/* 159:227 */     conn.sendRequestEntity(request);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void sendRequestHeader(HttpRequest request)
/* 163:    */     throws HttpException, IOException
/* 164:    */   {
/* 165:232 */     OperatedClientConnection conn = getWrappedConnection();
/* 166:233 */     assertValid(conn);
/* 167:234 */     unmarkReusable();
/* 168:235 */     conn.sendRequestHeader(request);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public InetAddress getLocalAddress()
/* 172:    */   {
/* 173:239 */     OperatedClientConnection conn = getWrappedConnection();
/* 174:240 */     assertValid(conn);
/* 175:241 */     return conn.getLocalAddress();
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getLocalPort()
/* 179:    */   {
/* 180:245 */     OperatedClientConnection conn = getWrappedConnection();
/* 181:246 */     assertValid(conn);
/* 182:247 */     return conn.getLocalPort();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public InetAddress getRemoteAddress()
/* 186:    */   {
/* 187:251 */     OperatedClientConnection conn = getWrappedConnection();
/* 188:252 */     assertValid(conn);
/* 189:253 */     return conn.getRemoteAddress();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public int getRemotePort()
/* 193:    */   {
/* 194:257 */     OperatedClientConnection conn = getWrappedConnection();
/* 195:258 */     assertValid(conn);
/* 196:259 */     return conn.getRemotePort();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean isSecure()
/* 200:    */   {
/* 201:263 */     OperatedClientConnection conn = getWrappedConnection();
/* 202:264 */     assertValid(conn);
/* 203:265 */     return conn.isSecure();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public SSLSession getSSLSession()
/* 207:    */   {
/* 208:269 */     OperatedClientConnection conn = getWrappedConnection();
/* 209:270 */     assertValid(conn);
/* 210:271 */     if (!isOpen()) {
/* 211:272 */       return null;
/* 212:    */     }
/* 213:274 */     SSLSession result = null;
/* 214:275 */     Socket sock = conn.getSocket();
/* 215:276 */     if ((sock instanceof SSLSocket)) {
/* 216:277 */       result = ((SSLSocket)sock).getSession();
/* 217:    */     }
/* 218:279 */     return result;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void markReusable()
/* 222:    */   {
/* 223:283 */     this.markedReusable = true;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void unmarkReusable()
/* 227:    */   {
/* 228:287 */     this.markedReusable = false;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean isMarkedReusable()
/* 232:    */   {
/* 233:291 */     return this.markedReusable;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIdleDuration(long duration, TimeUnit unit)
/* 237:    */   {
/* 238:295 */     if (duration > 0L) {
/* 239:296 */       this.duration = unit.toMillis(duration);
/* 240:    */     } else {
/* 241:298 */       this.duration = -1L;
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public synchronized void releaseConnection()
/* 246:    */   {
/* 247:303 */     if (this.released) {
/* 248:304 */       return;
/* 249:    */     }
/* 250:306 */     this.released = true;
/* 251:307 */     if (this.connManager != null) {
/* 252:308 */       this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public synchronized void abortConnection()
/* 257:    */   {
/* 258:313 */     if (this.released) {
/* 259:314 */       return;
/* 260:    */     }
/* 261:316 */     this.released = true;
/* 262:317 */     unmarkReusable();
/* 263:    */     try
/* 264:    */     {
/* 265:319 */       shutdown();
/* 266:    */     }
/* 267:    */     catch (IOException ignore) {}
/* 268:322 */     if (this.connManager != null) {
/* 269:323 */       this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public synchronized Object getAttribute(String id)
/* 274:    */   {
/* 275:328 */     OperatedClientConnection conn = getWrappedConnection();
/* 276:329 */     assertValid(conn);
/* 277:330 */     if ((conn instanceof HttpContext)) {
/* 278:331 */       return ((HttpContext)conn).getAttribute(id);
/* 279:    */     }
/* 280:333 */     return null;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public synchronized Object removeAttribute(String id)
/* 284:    */   {
/* 285:338 */     OperatedClientConnection conn = getWrappedConnection();
/* 286:339 */     assertValid(conn);
/* 287:340 */     if ((conn instanceof HttpContext)) {
/* 288:341 */       return ((HttpContext)conn).removeAttribute(id);
/* 289:    */     }
/* 290:343 */     return null;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public synchronized void setAttribute(String id, Object obj)
/* 294:    */   {
/* 295:348 */     OperatedClientConnection conn = getWrappedConnection();
/* 296:349 */     assertValid(conn);
/* 297:350 */     if ((conn instanceof HttpContext)) {
/* 298:351 */       ((HttpContext)conn).setAttribute(id, obj);
/* 299:    */     }
/* 300:    */   }
/* 301:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.AbstractClientConnAdapter
 * JD-Core Version:    0.7.0.1
 */