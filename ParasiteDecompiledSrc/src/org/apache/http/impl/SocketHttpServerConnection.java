/*   1:    */ package org.apache.http.impl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.Socket;
/*   6:    */ import java.net.SocketException;
/*   7:    */ import org.apache.http.HttpInetConnection;
/*   8:    */ import org.apache.http.impl.io.SocketInputBuffer;
/*   9:    */ import org.apache.http.impl.io.SocketOutputBuffer;
/*  10:    */ import org.apache.http.io.SessionInputBuffer;
/*  11:    */ import org.apache.http.io.SessionOutputBuffer;
/*  12:    */ import org.apache.http.params.HttpConnectionParams;
/*  13:    */ import org.apache.http.params.HttpParams;
/*  14:    */ 
/*  15:    */ public class SocketHttpServerConnection
/*  16:    */   extends AbstractHttpServerConnection
/*  17:    */   implements HttpInetConnection
/*  18:    */ {
/*  19:    */   private volatile boolean open;
/*  20: 63 */   private volatile Socket socket = null;
/*  21:    */   
/*  22:    */   protected void assertNotOpen()
/*  23:    */   {
/*  24: 70 */     if (this.open) {
/*  25: 71 */       throw new IllegalStateException("Connection is already open");
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void assertOpen()
/*  30:    */   {
/*  31: 76 */     if (!this.open) {
/*  32: 77 */       throw new IllegalStateException("Connection is not open");
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   /**
/*  37:    */    * @deprecated
/*  38:    */    */
/*  39:    */   protected SessionInputBuffer createHttpDataReceiver(Socket socket, int buffersize, HttpParams params)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 88 */     return createSessionInputBuffer(socket, buffersize, params);
/*  43:    */   }
/*  44:    */   
/*  45:    */   /**
/*  46:    */    * @deprecated
/*  47:    */    */
/*  48:    */   protected SessionOutputBuffer createHttpDataTransmitter(Socket socket, int buffersize, HttpParams params)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 98 */     return createSessionOutputBuffer(socket, buffersize, params);
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected SessionInputBuffer createSessionInputBuffer(Socket socket, int buffersize, HttpParams params)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:120 */     return new SocketInputBuffer(socket, buffersize, params);
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected SessionOutputBuffer createSessionOutputBuffer(Socket socket, int buffersize, HttpParams params)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:142 */     return new SocketOutputBuffer(socket, buffersize, params);
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void bind(Socket socket, HttpParams params)
/*  67:    */     throws IOException
/*  68:    */   {
/*  69:164 */     if (socket == null) {
/*  70:165 */       throw new IllegalArgumentException("Socket may not be null");
/*  71:    */     }
/*  72:167 */     if (params == null) {
/*  73:168 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  74:    */     }
/*  75:170 */     this.socket = socket;
/*  76:    */     
/*  77:172 */     int buffersize = HttpConnectionParams.getSocketBufferSize(params);
/*  78:    */     
/*  79:174 */     init(createHttpDataReceiver(socket, buffersize, params), createHttpDataTransmitter(socket, buffersize, params), params);
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:179 */     this.open = true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected Socket getSocket()
/*  88:    */   {
/*  89:183 */     return this.socket;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isOpen()
/*  93:    */   {
/*  94:187 */     return this.open;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public InetAddress getLocalAddress()
/*  98:    */   {
/*  99:191 */     if (this.socket != null) {
/* 100:192 */       return this.socket.getLocalAddress();
/* 101:    */     }
/* 102:194 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getLocalPort()
/* 106:    */   {
/* 107:199 */     if (this.socket != null) {
/* 108:200 */       return this.socket.getLocalPort();
/* 109:    */     }
/* 110:202 */     return -1;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public InetAddress getRemoteAddress()
/* 114:    */   {
/* 115:207 */     if (this.socket != null) {
/* 116:208 */       return this.socket.getInetAddress();
/* 117:    */     }
/* 118:210 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getRemotePort()
/* 122:    */   {
/* 123:215 */     if (this.socket != null) {
/* 124:216 */       return this.socket.getPort();
/* 125:    */     }
/* 126:218 */     return -1;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setSocketTimeout(int timeout)
/* 130:    */   {
/* 131:223 */     assertOpen();
/* 132:224 */     if (this.socket != null) {
/* 133:    */       try
/* 134:    */       {
/* 135:226 */         this.socket.setSoTimeout(timeout);
/* 136:    */       }
/* 137:    */       catch (SocketException ignore) {}
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getSocketTimeout()
/* 142:    */   {
/* 143:236 */     if (this.socket != null) {
/* 144:    */       try
/* 145:    */       {
/* 146:238 */         return this.socket.getSoTimeout();
/* 147:    */       }
/* 148:    */       catch (SocketException ignore)
/* 149:    */       {
/* 150:240 */         return -1;
/* 151:    */       }
/* 152:    */     }
/* 153:243 */     return -1;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void shutdown()
/* 157:    */     throws IOException
/* 158:    */   {
/* 159:248 */     this.open = false;
/* 160:249 */     Socket tmpsocket = this.socket;
/* 161:250 */     if (tmpsocket != null) {
/* 162:251 */       tmpsocket.close();
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void close()
/* 167:    */     throws IOException
/* 168:    */   {
/* 169:256 */     if (!this.open) {
/* 170:257 */       return;
/* 171:    */     }
/* 172:259 */     this.open = false;
/* 173:260 */     this.open = false;
/* 174:261 */     Socket sock = this.socket;
/* 175:    */     try
/* 176:    */     {
/* 177:263 */       doFlush();
/* 178:    */       try
/* 179:    */       {
/* 180:    */         try {}catch (IOException ignore) {}
/* 181:    */         try {}catch (IOException ignore) {}
/* 182:    */       }
/* 183:    */       catch (UnsupportedOperationException ignore) {}
/* 184:    */     }
/* 185:    */     finally
/* 186:    */     {
/* 187:277 */       sock.close();
/* 188:    */     }
/* 189:    */   }
/* 190:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.SocketHttpServerConnection
 * JD-Core Version:    0.7.0.1
 */