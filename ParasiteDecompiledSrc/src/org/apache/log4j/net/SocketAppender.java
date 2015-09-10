/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InterruptedIOException;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.net.ConnectException;
/*   7:    */ import java.net.InetAddress;
/*   8:    */ import java.net.Socket;
/*   9:    */ import org.apache.log4j.AppenderSkeleton;
/*  10:    */ import org.apache.log4j.helpers.LogLog;
/*  11:    */ import org.apache.log4j.spi.ErrorHandler;
/*  12:    */ import org.apache.log4j.spi.LoggingEvent;
/*  13:    */ 
/*  14:    */ public class SocketAppender
/*  15:    */   extends AppenderSkeleton
/*  16:    */ {
/*  17:    */   public static final int DEFAULT_PORT = 4560;
/*  18:    */   static final int DEFAULT_RECONNECTION_DELAY = 30000;
/*  19:    */   String remoteHost;
/*  20:    */   public static final String ZONE = "_log4j_obj_tcpconnect_appender.local.";
/*  21:    */   InetAddress address;
/*  22:127 */   int port = 4560;
/*  23:    */   ObjectOutputStream oos;
/*  24:129 */   int reconnectionDelay = 30000;
/*  25:130 */   boolean locationInfo = false;
/*  26:    */   private String application;
/*  27:    */   private Connector connector;
/*  28:135 */   int counter = 0;
/*  29:    */   private static final int RESET_FREQUENCY = 1;
/*  30:    */   private boolean advertiseViaMulticastDNS;
/*  31:    */   private ZeroConfSupport zeroConf;
/*  32:    */   
/*  33:    */   public SocketAppender() {}
/*  34:    */   
/*  35:    */   public SocketAppender(InetAddress address, int port)
/*  36:    */   {
/*  37:150 */     this.address = address;
/*  38:151 */     this.remoteHost = address.getHostName();
/*  39:152 */     this.port = port;
/*  40:153 */     connect(address, port);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public SocketAppender(String host, int port)
/*  44:    */   {
/*  45:160 */     this.port = port;
/*  46:161 */     this.address = getAddressByName(host);
/*  47:162 */     this.remoteHost = host;
/*  48:163 */     connect(this.address, port);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void activateOptions()
/*  52:    */   {
/*  53:170 */     if (this.advertiseViaMulticastDNS)
/*  54:    */     {
/*  55:171 */       this.zeroConf = new ZeroConfSupport("_log4j_obj_tcpconnect_appender.local.", this.port, getName());
/*  56:172 */       this.zeroConf.advertise();
/*  57:    */     }
/*  58:174 */     connect(this.address, this.port);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public synchronized void close()
/*  62:    */   {
/*  63:184 */     if (this.closed) {
/*  64:185 */       return;
/*  65:    */     }
/*  66:187 */     this.closed = true;
/*  67:188 */     if (this.advertiseViaMulticastDNS) {
/*  68:189 */       this.zeroConf.unadvertise();
/*  69:    */     }
/*  70:192 */     cleanUp();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void cleanUp()
/*  74:    */   {
/*  75:200 */     if (this.oos != null)
/*  76:    */     {
/*  77:    */       try
/*  78:    */       {
/*  79:202 */         this.oos.close();
/*  80:    */       }
/*  81:    */       catch (IOException e)
/*  82:    */       {
/*  83:204 */         if ((e instanceof InterruptedIOException)) {
/*  84:205 */           Thread.currentThread().interrupt();
/*  85:    */         }
/*  86:207 */         LogLog.error("Could not close oos.", e);
/*  87:    */       }
/*  88:209 */       this.oos = null;
/*  89:    */     }
/*  90:211 */     if (this.connector != null)
/*  91:    */     {
/*  92:213 */       this.connector.interrupted = true;
/*  93:214 */       this.connector = null;
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   void connect(InetAddress address, int port)
/*  98:    */   {
/*  99:219 */     if (this.address == null) {
/* 100:220 */       return;
/* 101:    */     }
/* 102:    */     try
/* 103:    */     {
/* 104:223 */       cleanUp();
/* 105:224 */       this.oos = new ObjectOutputStream(new Socket(address, port).getOutputStream());
/* 106:    */     }
/* 107:    */     catch (IOException e)
/* 108:    */     {
/* 109:226 */       if ((e instanceof InterruptedIOException)) {
/* 110:227 */         Thread.currentThread().interrupt();
/* 111:    */       }
/* 112:229 */       String msg = "Could not connect to remote log4j server at [" + address.getHostName() + "].";
/* 113:231 */       if (this.reconnectionDelay > 0)
/* 114:    */       {
/* 115:232 */         msg = msg + " We will try again later.";
/* 116:233 */         fireConnector();
/* 117:    */       }
/* 118:    */       else
/* 119:    */       {
/* 120:235 */         msg = msg + " We are not retrying.";
/* 121:236 */         this.errorHandler.error(msg, e, 0);
/* 122:    */       }
/* 123:238 */       LogLog.error(msg);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void append(LoggingEvent event)
/* 128:    */   {
/* 129:244 */     if (event == null) {
/* 130:245 */       return;
/* 131:    */     }
/* 132:247 */     if (this.address == null)
/* 133:    */     {
/* 134:248 */       this.errorHandler.error("No remote host is set for SocketAppender named \"" + this.name + "\".");
/* 135:    */       
/* 136:250 */       return;
/* 137:    */     }
/* 138:253 */     if (this.oos != null) {
/* 139:    */       try
/* 140:    */       {
/* 141:256 */         if (this.locationInfo) {
/* 142:257 */           event.getLocationInformation();
/* 143:    */         }
/* 144:259 */         if (this.application != null) {
/* 145:260 */           event.setProperty("application", this.application);
/* 146:    */         }
/* 147:262 */         event.getNDC();
/* 148:263 */         event.getThreadName();
/* 149:264 */         event.getMDCCopy();
/* 150:265 */         event.getRenderedMessage();
/* 151:266 */         event.getThrowableStrRep();
/* 152:    */         
/* 153:268 */         this.oos.writeObject(event);
/* 154:    */         
/* 155:270 */         this.oos.flush();
/* 156:271 */         if (++this.counter >= 1)
/* 157:    */         {
/* 158:272 */           this.counter = 0;
/* 159:    */           
/* 160:    */ 
/* 161:    */ 
/* 162:276 */           this.oos.reset();
/* 163:    */         }
/* 164:    */       }
/* 165:    */       catch (IOException e)
/* 166:    */       {
/* 167:279 */         if ((e instanceof InterruptedIOException)) {
/* 168:280 */           Thread.currentThread().interrupt();
/* 169:    */         }
/* 170:282 */         this.oos = null;
/* 171:283 */         LogLog.warn("Detected problem with connection: " + e);
/* 172:284 */         if (this.reconnectionDelay > 0) {
/* 173:285 */           fireConnector();
/* 174:    */         } else {
/* 175:287 */           this.errorHandler.error("Detected problem with connection, not reconnecting.", e, 0);
/* 176:    */         }
/* 177:    */       }
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setAdvertiseViaMulticastDNS(boolean advertiseViaMulticastDNS)
/* 182:    */   {
/* 183:295 */     this.advertiseViaMulticastDNS = advertiseViaMulticastDNS;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean isAdvertiseViaMulticastDNS()
/* 187:    */   {
/* 188:299 */     return this.advertiseViaMulticastDNS;
/* 189:    */   }
/* 190:    */   
/* 191:    */   void fireConnector()
/* 192:    */   {
/* 193:303 */     if (this.connector == null)
/* 194:    */     {
/* 195:304 */       LogLog.debug("Starting a new connector thread.");
/* 196:305 */       this.connector = new Connector();
/* 197:306 */       this.connector.setDaemon(true);
/* 198:307 */       this.connector.setPriority(1);
/* 199:308 */       this.connector.start();
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   static InetAddress getAddressByName(String host)
/* 204:    */   {
/* 205:    */     try
/* 206:    */     {
/* 207:315 */       return InetAddress.getByName(host);
/* 208:    */     }
/* 209:    */     catch (Exception e)
/* 210:    */     {
/* 211:317 */       if (((e instanceof InterruptedIOException)) || ((e instanceof InterruptedException))) {
/* 212:318 */         Thread.currentThread().interrupt();
/* 213:    */       }
/* 214:320 */       LogLog.error("Could not find address of [" + host + "].", e);
/* 215:    */     }
/* 216:321 */     return null;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean requiresLayout()
/* 220:    */   {
/* 221:330 */     return false;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setRemoteHost(String host)
/* 225:    */   {
/* 226:339 */     this.address = getAddressByName(host);
/* 227:340 */     this.remoteHost = host;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String getRemoteHost()
/* 231:    */   {
/* 232:347 */     return this.remoteHost;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setPort(int port)
/* 236:    */   {
/* 237:355 */     this.port = port;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public int getPort()
/* 241:    */   {
/* 242:362 */     return this.port;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setLocationInfo(boolean locationInfo)
/* 246:    */   {
/* 247:371 */     this.locationInfo = locationInfo;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public boolean getLocationInfo()
/* 251:    */   {
/* 252:378 */     return this.locationInfo;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setApplication(String lapp)
/* 256:    */   {
/* 257:388 */     this.application = lapp;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String getApplication()
/* 261:    */   {
/* 262:396 */     return this.application;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setReconnectionDelay(int delay)
/* 266:    */   {
/* 267:409 */     this.reconnectionDelay = delay;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public int getReconnectionDelay()
/* 271:    */   {
/* 272:416 */     return this.reconnectionDelay;
/* 273:    */   }
/* 274:    */   
/* 275:    */   class Connector
/* 276:    */     extends Thread
/* 277:    */   {
/* 278:433 */     boolean interrupted = false;
/* 279:    */     
/* 280:    */     Connector() {}
/* 281:    */     
/* 282:    */     public void run()
/* 283:    */     {
/* 284:438 */       while (!this.interrupted) {
/* 285:    */         try
/* 286:    */         {
/* 287:440 */           Thread.sleep(SocketAppender.this.reconnectionDelay);
/* 288:441 */           LogLog.debug("Attempting connection to " + SocketAppender.this.address.getHostName());
/* 289:442 */           Socket socket = new Socket(SocketAppender.this.address, SocketAppender.this.port);
/* 290:443 */           synchronized (this)
/* 291:    */           {
/* 292:444 */             SocketAppender.this.oos = new ObjectOutputStream(socket.getOutputStream());
/* 293:445 */             SocketAppender.this.connector = null;
/* 294:446 */             LogLog.debug("Connection established. Exiting connector thread.");
/* 295:    */           }
/* 296:    */         }
/* 297:    */         catch (InterruptedException e)
/* 298:    */         {
/* 299:450 */           LogLog.debug("Connector interrupted. Leaving loop.");
/* 300:451 */           return;
/* 301:    */         }
/* 302:    */         catch (ConnectException e)
/* 303:    */         {
/* 304:453 */           LogLog.debug("Remote host " + SocketAppender.this.address.getHostName() + " refused connection.");
/* 305:    */         }
/* 306:    */         catch (IOException e)
/* 307:    */         {
/* 308:456 */           if ((e instanceof InterruptedIOException)) {
/* 309:457 */             Thread.currentThread().interrupt();
/* 310:    */           }
/* 311:459 */           LogLog.debug("Could not connect to " + SocketAppender.this.address.getHostName() + ". Exception is " + e);
/* 312:    */         }
/* 313:    */       }
/* 314:    */     }
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.SocketAppender
 * JD-Core Version:    0.7.0.1
 */