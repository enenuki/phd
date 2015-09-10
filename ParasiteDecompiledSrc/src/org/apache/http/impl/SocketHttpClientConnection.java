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
/*  15:    */ public class SocketHttpClientConnection
/*  16:    */   extends AbstractHttpClientConnection
/*  17:    */   implements HttpInetConnection
/*  18:    */ {
/*  19:    */   private volatile boolean open;
/*  20: 64 */   private volatile Socket socket = null;
/*  21:    */   
/*  22:    */   protected void assertNotOpen()
/*  23:    */   {
/*  24: 71 */     if (this.open) {
/*  25: 72 */       throw new IllegalStateException("Connection is already open");
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void assertOpen()
/*  30:    */   {
/*  31: 77 */     if (!this.open) {
/*  32: 78 */       throw new IllegalStateException("Connection is not open");
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected SessionInputBuffer createSessionInputBuffer(Socket socket, int buffersize, HttpParams params)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39:101 */     return new SocketInputBuffer(socket, buffersize, params);
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected SessionOutputBuffer createSessionOutputBuffer(Socket socket, int buffersize, HttpParams params)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45:123 */     return new SocketOutputBuffer(socket, buffersize, params);
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected void bind(Socket socket, HttpParams params)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:147 */     if (socket == null) {
/*  52:148 */       throw new IllegalArgumentException("Socket may not be null");
/*  53:    */     }
/*  54:150 */     if (params == null) {
/*  55:151 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  56:    */     }
/*  57:153 */     this.socket = socket;
/*  58:    */     
/*  59:155 */     int buffersize = HttpConnectionParams.getSocketBufferSize(params);
/*  60:    */     
/*  61:157 */     init(createSessionInputBuffer(socket, buffersize, params), createSessionOutputBuffer(socket, buffersize, params), params);
/*  62:    */     
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:162 */     this.open = true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isOpen()
/*  70:    */   {
/*  71:166 */     return this.open;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected Socket getSocket()
/*  75:    */   {
/*  76:170 */     return this.socket;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public InetAddress getLocalAddress()
/*  80:    */   {
/*  81:174 */     if (this.socket != null) {
/*  82:175 */       return this.socket.getLocalAddress();
/*  83:    */     }
/*  84:177 */     return null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getLocalPort()
/*  88:    */   {
/*  89:182 */     if (this.socket != null) {
/*  90:183 */       return this.socket.getLocalPort();
/*  91:    */     }
/*  92:185 */     return -1;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public InetAddress getRemoteAddress()
/*  96:    */   {
/*  97:190 */     if (this.socket != null) {
/*  98:191 */       return this.socket.getInetAddress();
/*  99:    */     }
/* 100:193 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getRemotePort()
/* 104:    */   {
/* 105:198 */     if (this.socket != null) {
/* 106:199 */       return this.socket.getPort();
/* 107:    */     }
/* 108:201 */     return -1;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setSocketTimeout(int timeout)
/* 112:    */   {
/* 113:206 */     assertOpen();
/* 114:207 */     if (this.socket != null) {
/* 115:    */       try
/* 116:    */       {
/* 117:209 */         this.socket.setSoTimeout(timeout);
/* 118:    */       }
/* 119:    */       catch (SocketException ignore) {}
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getSocketTimeout()
/* 124:    */   {
/* 125:219 */     if (this.socket != null) {
/* 126:    */       try
/* 127:    */       {
/* 128:221 */         return this.socket.getSoTimeout();
/* 129:    */       }
/* 130:    */       catch (SocketException ignore)
/* 131:    */       {
/* 132:223 */         return -1;
/* 133:    */       }
/* 134:    */     }
/* 135:226 */     return -1;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void shutdown()
/* 139:    */     throws IOException
/* 140:    */   {
/* 141:231 */     this.open = false;
/* 142:232 */     Socket tmpsocket = this.socket;
/* 143:233 */     if (tmpsocket != null) {
/* 144:234 */       tmpsocket.close();
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void close()
/* 149:    */     throws IOException
/* 150:    */   {
/* 151:239 */     if (!this.open) {
/* 152:240 */       return;
/* 153:    */     }
/* 154:242 */     this.open = false;
/* 155:243 */     Socket sock = this.socket;
/* 156:    */     try
/* 157:    */     {
/* 158:245 */       doFlush();
/* 159:    */       try
/* 160:    */       {
/* 161:    */         try {}catch (IOException ignore) {}
/* 162:    */         try {}catch (IOException ignore) {}
/* 163:    */       }
/* 164:    */       catch (UnsupportedOperationException ignore) {}
/* 165:    */     }
/* 166:    */     finally
/* 167:    */     {
/* 168:259 */       sock.close();
/* 169:    */     }
/* 170:    */   }
/* 171:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.SocketHttpClientConnection
 * JD-Core Version:    0.7.0.1
 */