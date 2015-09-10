/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.ConnectException;
/*   5:    */ import java.net.InetAddress;
/*   6:    */ import java.net.InetSocketAddress;
/*   7:    */ import java.net.Socket;
/*   8:    */ import java.net.UnknownHostException;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.http.HttpHost;
/*  12:    */ import org.apache.http.annotation.ThreadSafe;
/*  13:    */ import org.apache.http.conn.ClientConnectionOperator;
/*  14:    */ import org.apache.http.conn.ConnectTimeoutException;
/*  15:    */ import org.apache.http.conn.HttpHostConnectException;
/*  16:    */ import org.apache.http.conn.OperatedClientConnection;
/*  17:    */ import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
/*  18:    */ import org.apache.http.conn.scheme.Scheme;
/*  19:    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  20:    */ import org.apache.http.conn.scheme.SchemeSocketFactory;
/*  21:    */ import org.apache.http.params.HttpConnectionParams;
/*  22:    */ import org.apache.http.params.HttpParams;
/*  23:    */ import org.apache.http.protocol.HttpContext;
/*  24:    */ 
/*  25:    */ @ThreadSafe
/*  26:    */ public class DefaultClientConnectionOperator
/*  27:    */   implements ClientConnectionOperator
/*  28:    */ {
/*  29: 87 */   private final Log log = LogFactory.getLog(getClass());
/*  30:    */   protected final SchemeRegistry schemeRegistry;
/*  31:    */   
/*  32:    */   public DefaultClientConnectionOperator(SchemeRegistry schemes)
/*  33:    */   {
/*  34: 98 */     if (schemes == null) {
/*  35: 99 */       throw new IllegalArgumentException("Scheme registry amy not be null");
/*  36:    */     }
/*  37:101 */     this.schemeRegistry = schemes;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public OperatedClientConnection createConnection()
/*  41:    */   {
/*  42:105 */     return new DefaultClientConnection();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void openConnection(OperatedClientConnection conn, HttpHost target, InetAddress local, HttpContext context, HttpParams params)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:114 */     if (conn == null) {
/*  49:115 */       throw new IllegalArgumentException("Connection may not be null");
/*  50:    */     }
/*  51:117 */     if (target == null) {
/*  52:118 */       throw new IllegalArgumentException("Target host may not be null");
/*  53:    */     }
/*  54:120 */     if (params == null) {
/*  55:121 */       throw new IllegalArgumentException("Parameters may not be null");
/*  56:    */     }
/*  57:123 */     if (conn.isOpen()) {
/*  58:124 */       throw new IllegalStateException("Connection must not be open");
/*  59:    */     }
/*  60:127 */     Scheme schm = this.schemeRegistry.getScheme(target.getSchemeName());
/*  61:128 */     SchemeSocketFactory sf = schm.getSchemeSocketFactory();
/*  62:    */     
/*  63:130 */     InetAddress[] addresses = resolveHostname(target.getHostName());
/*  64:131 */     int port = schm.resolvePort(target.getPort());
/*  65:132 */     for (int i = 0; i < addresses.length; i++)
/*  66:    */     {
/*  67:133 */       InetAddress address = addresses[i];
/*  68:134 */       boolean last = i == addresses.length - 1;
/*  69:    */       
/*  70:136 */       Socket sock = sf.createSocket(params);
/*  71:137 */       conn.opening(sock, target);
/*  72:    */       
/*  73:139 */       InetSocketAddress remoteAddress = new HttpInetSocketAddress(target, address, port);
/*  74:140 */       InetSocketAddress localAddress = null;
/*  75:141 */       if (local != null) {
/*  76:142 */         localAddress = new InetSocketAddress(local, 0);
/*  77:    */       }
/*  78:144 */       if (this.log.isDebugEnabled()) {
/*  79:145 */         this.log.debug("Connecting to " + remoteAddress);
/*  80:    */       }
/*  81:    */       try
/*  82:    */       {
/*  83:148 */         Socket connsock = sf.connectSocket(sock, remoteAddress, localAddress, params);
/*  84:149 */         if (sock != connsock)
/*  85:    */         {
/*  86:150 */           sock = connsock;
/*  87:151 */           conn.opening(sock, target);
/*  88:    */         }
/*  89:153 */         prepareSocket(sock, context, params);
/*  90:154 */         conn.openCompleted(sf.isSecure(sock), params);
/*  91:155 */         return;
/*  92:    */       }
/*  93:    */       catch (ConnectException ex)
/*  94:    */       {
/*  95:157 */         if (last) {
/*  96:158 */           throw new HttpHostConnectException(target, ex);
/*  97:    */         }
/*  98:    */       }
/*  99:    */       catch (ConnectTimeoutException ex)
/* 100:    */       {
/* 101:161 */         if (last) {
/* 102:162 */           throw ex;
/* 103:    */         }
/* 104:    */       }
/* 105:165 */       if (this.log.isDebugEnabled()) {
/* 106:166 */         this.log.debug("Connect to " + remoteAddress + " timed out. " + "Connection will be retried using another IP address");
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void updateSecureConnection(OperatedClientConnection conn, HttpHost target, HttpContext context, HttpParams params)
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:177 */     if (conn == null) {
/* 115:178 */       throw new IllegalArgumentException("Connection may not be null");
/* 116:    */     }
/* 117:180 */     if (target == null) {
/* 118:181 */       throw new IllegalArgumentException("Target host may not be null");
/* 119:    */     }
/* 120:183 */     if (params == null) {
/* 121:184 */       throw new IllegalArgumentException("Parameters may not be null");
/* 122:    */     }
/* 123:186 */     if (!conn.isOpen()) {
/* 124:187 */       throw new IllegalStateException("Connection must be open");
/* 125:    */     }
/* 126:190 */     Scheme schm = this.schemeRegistry.getScheme(target.getSchemeName());
/* 127:191 */     if (!(schm.getSchemeSocketFactory() instanceof LayeredSchemeSocketFactory)) {
/* 128:192 */       throw new IllegalArgumentException("Target scheme (" + schm.getName() + ") must have layered socket factory.");
/* 129:    */     }
/* 130:197 */     LayeredSchemeSocketFactory lsf = (LayeredSchemeSocketFactory)schm.getSchemeSocketFactory();
/* 131:    */     Socket sock;
/* 132:    */     try
/* 133:    */     {
/* 134:200 */       sock = lsf.createLayeredSocket(conn.getSocket(), target.getHostName(), target.getPort(), true);
/* 135:    */     }
/* 136:    */     catch (ConnectException ex)
/* 137:    */     {
/* 138:203 */       throw new HttpHostConnectException(target, ex);
/* 139:    */     }
/* 140:205 */     prepareSocket(sock, context, params);
/* 141:206 */     conn.update(sock, target, lsf.isSecure(sock), params);
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected void prepareSocket(Socket sock, HttpContext context, HttpParams params)
/* 145:    */     throws IOException
/* 146:    */   {
/* 147:222 */     sock.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(params));
/* 148:223 */     sock.setSoTimeout(HttpConnectionParams.getSoTimeout(params));
/* 149:    */     
/* 150:225 */     int linger = HttpConnectionParams.getLinger(params);
/* 151:226 */     if (linger >= 0) {
/* 152:227 */       sock.setSoLinger(linger > 0, linger);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected InetAddress[] resolveHostname(String host)
/* 157:    */     throws UnknownHostException
/* 158:    */   {
/* 159:242 */     return InetAddress.getAllByName(host);
/* 160:    */   }
/* 161:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.DefaultClientConnectionOperator
 * JD-Core Version:    0.7.0.1
 */