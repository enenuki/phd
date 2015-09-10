/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InterruptedIOException;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.net.InetAddress;
/*   7:    */ import java.net.ServerSocket;
/*   8:    */ import java.net.Socket;
/*   9:    */ import java.net.SocketException;
/*  10:    */ import java.util.Vector;
/*  11:    */ import org.apache.log4j.AppenderSkeleton;
/*  12:    */ import org.apache.log4j.helpers.CyclicBuffer;
/*  13:    */ import org.apache.log4j.helpers.LogLog;
/*  14:    */ import org.apache.log4j.spi.LoggingEvent;
/*  15:    */ 
/*  16:    */ public class SocketHubAppender
/*  17:    */   extends AppenderSkeleton
/*  18:    */ {
/*  19:    */   static final int DEFAULT_PORT = 4560;
/*  20:114 */   private int port = 4560;
/*  21:115 */   private Vector oosList = new Vector();
/*  22:116 */   private ServerMonitor serverMonitor = null;
/*  23:117 */   private boolean locationInfo = false;
/*  24:118 */   private CyclicBuffer buffer = null;
/*  25:    */   private String application;
/*  26:    */   private boolean advertiseViaMulticastDNS;
/*  27:    */   private ZeroConfSupport zeroConf;
/*  28:    */   public static final String ZONE = "_log4j_obj_tcpaccept_appender.local.";
/*  29:    */   
/*  30:    */   public SocketHubAppender() {}
/*  31:    */   
/*  32:    */   public SocketHubAppender(int _port)
/*  33:    */   {
/*  34:135 */     this.port = _port;
/*  35:136 */     startServer();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void activateOptions()
/*  39:    */   {
/*  40:143 */     if (this.advertiseViaMulticastDNS)
/*  41:    */     {
/*  42:144 */       this.zeroConf = new ZeroConfSupport("_log4j_obj_tcpaccept_appender.local.", this.port, getName());
/*  43:145 */       this.zeroConf.advertise();
/*  44:    */     }
/*  45:147 */     startServer();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public synchronized void close()
/*  49:    */   {
/*  50:157 */     if (this.closed) {
/*  51:158 */       return;
/*  52:    */     }
/*  53:160 */     LogLog.debug("closing SocketHubAppender " + getName());
/*  54:161 */     this.closed = true;
/*  55:162 */     if (this.advertiseViaMulticastDNS) {
/*  56:163 */       this.zeroConf.unadvertise();
/*  57:    */     }
/*  58:165 */     cleanUp();
/*  59:    */     
/*  60:167 */     LogLog.debug("SocketHubAppender " + getName() + " closed");
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void cleanUp()
/*  64:    */   {
/*  65:176 */     LogLog.debug("stopping ServerSocket");
/*  66:177 */     this.serverMonitor.stopMonitor();
/*  67:178 */     this.serverMonitor = null;
/*  68:    */     
/*  69:    */ 
/*  70:181 */     LogLog.debug("closing client connections");
/*  71:182 */     while (this.oosList.size() != 0)
/*  72:    */     {
/*  73:183 */       ObjectOutputStream oos = (ObjectOutputStream)this.oosList.elementAt(0);
/*  74:184 */       if (oos != null)
/*  75:    */       {
/*  76:    */         try
/*  77:    */         {
/*  78:186 */           oos.close();
/*  79:    */         }
/*  80:    */         catch (InterruptedIOException e)
/*  81:    */         {
/*  82:188 */           Thread.currentThread().interrupt();
/*  83:189 */           LogLog.error("could not close oos.", e);
/*  84:    */         }
/*  85:    */         catch (IOException e)
/*  86:    */         {
/*  87:191 */           LogLog.error("could not close oos.", e);
/*  88:    */         }
/*  89:194 */         this.oosList.removeElementAt(0);
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void append(LoggingEvent event)
/*  95:    */   {
/*  96:203 */     if (event != null)
/*  97:    */     {
/*  98:205 */       if (this.locationInfo) {
/*  99:206 */         event.getLocationInformation();
/* 100:    */       }
/* 101:208 */       if (this.application != null) {
/* 102:209 */         event.setProperty("application", this.application);
/* 103:    */       }
/* 104:211 */       event.getNDC();
/* 105:212 */       event.getThreadName();
/* 106:213 */       event.getMDCCopy();
/* 107:214 */       event.getRenderedMessage();
/* 108:215 */       event.getThrowableStrRep();
/* 109:217 */       if (this.buffer != null) {
/* 110:218 */         this.buffer.add(event);
/* 111:    */       }
/* 112:    */     }
/* 113:223 */     if ((event == null) || (this.oosList.size() == 0)) {
/* 114:224 */       return;
/* 115:    */     }
/* 116:228 */     for (int streamCount = 0; streamCount < this.oosList.size(); streamCount++)
/* 117:    */     {
/* 118:230 */       ObjectOutputStream oos = null;
/* 119:    */       try
/* 120:    */       {
/* 121:232 */         oos = (ObjectOutputStream)this.oosList.elementAt(streamCount);
/* 122:    */       }
/* 123:    */       catch (ArrayIndexOutOfBoundsException e) {}
/* 124:241 */       if (oos == null) {
/* 125:    */         break;
/* 126:    */       }
/* 127:    */       try
/* 128:    */       {
/* 129:245 */         oos.writeObject(event);
/* 130:246 */         oos.flush();
/* 131:    */         
/* 132:    */ 
/* 133:    */ 
/* 134:250 */         oos.reset();
/* 135:    */       }
/* 136:    */       catch (IOException e)
/* 137:    */       {
/* 138:253 */         if ((e instanceof InterruptedIOException)) {
/* 139:254 */           Thread.currentThread().interrupt();
/* 140:    */         }
/* 141:257 */         this.oosList.removeElementAt(streamCount);
/* 142:258 */         LogLog.debug("dropped connection");
/* 143:    */         
/* 144:    */ 
/* 145:261 */         streamCount--;
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean requiresLayout()
/* 151:    */   {
/* 152:271 */     return false;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPort(int _port)
/* 156:    */   {
/* 157:279 */     this.port = _port;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setApplication(String lapp)
/* 161:    */   {
/* 162:288 */     this.application = lapp;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getApplication()
/* 166:    */   {
/* 167:296 */     return this.application;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int getPort()
/* 171:    */   {
/* 172:303 */     return this.port;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setBufferSize(int _bufferSize)
/* 176:    */   {
/* 177:312 */     this.buffer = new CyclicBuffer(_bufferSize);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getBufferSize()
/* 181:    */   {
/* 182:320 */     if (this.buffer == null) {
/* 183:321 */       return 0;
/* 184:    */     }
/* 185:323 */     return this.buffer.getMaxSize();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setLocationInfo(boolean _locationInfo)
/* 189:    */   {
/* 190:333 */     this.locationInfo = _locationInfo;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public boolean getLocationInfo()
/* 194:    */   {
/* 195:340 */     return this.locationInfo;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setAdvertiseViaMulticastDNS(boolean advertiseViaMulticastDNS)
/* 199:    */   {
/* 200:344 */     this.advertiseViaMulticastDNS = advertiseViaMulticastDNS;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean isAdvertiseViaMulticastDNS()
/* 204:    */   {
/* 205:348 */     return this.advertiseViaMulticastDNS;
/* 206:    */   }
/* 207:    */   
/* 208:    */   private void startServer()
/* 209:    */   {
/* 210:355 */     this.serverMonitor = new ServerMonitor(this.port, this.oosList);
/* 211:    */   }
/* 212:    */   
/* 213:    */   protected ServerSocket createServerSocket(int socketPort)
/* 214:    */     throws IOException
/* 215:    */   {
/* 216:365 */     return new ServerSocket(socketPort);
/* 217:    */   }
/* 218:    */   
/* 219:    */   private class ServerMonitor
/* 220:    */     implements Runnable
/* 221:    */   {
/* 222:    */     private int port;
/* 223:    */     private Vector oosList;
/* 224:    */     private boolean keepRunning;
/* 225:    */     private Thread monitorThread;
/* 226:    */     
/* 227:    */     public ServerMonitor(int _port, Vector _oosList)
/* 228:    */     {
/* 229:383 */       this.port = _port;
/* 230:384 */       this.oosList = _oosList;
/* 231:385 */       this.keepRunning = true;
/* 232:386 */       this.monitorThread = new Thread(this);
/* 233:387 */       this.monitorThread.setDaemon(true);
/* 234:388 */       this.monitorThread.setName("SocketHubAppender-Monitor-" + this.port);
/* 235:389 */       this.monitorThread.start();
/* 236:    */     }
/* 237:    */     
/* 238:    */     public synchronized void stopMonitor()
/* 239:    */     {
/* 240:398 */       if (this.keepRunning)
/* 241:    */       {
/* 242:399 */         LogLog.debug("server monitor thread shutting down");
/* 243:400 */         this.keepRunning = false;
/* 244:    */         try
/* 245:    */         {
/* 246:402 */           this.monitorThread.join();
/* 247:    */         }
/* 248:    */         catch (InterruptedException e)
/* 249:    */         {
/* 250:405 */           Thread.currentThread().interrupt();
/* 251:    */         }
/* 252:410 */         this.monitorThread = null;
/* 253:411 */         LogLog.debug("server monitor thread shut down");
/* 254:    */       }
/* 255:    */     }
/* 256:    */     
/* 257:    */     private void sendCachedEvents(ObjectOutputStream stream)
/* 258:    */       throws IOException
/* 259:    */     {
/* 260:417 */       if (SocketHubAppender.this.buffer != null)
/* 261:    */       {
/* 262:418 */         for (int i = 0; i < SocketHubAppender.this.buffer.length(); i++) {
/* 263:419 */           stream.writeObject(SocketHubAppender.this.buffer.get(i));
/* 264:    */         }
/* 265:421 */         stream.flush();
/* 266:422 */         stream.reset();
/* 267:    */       }
/* 268:    */     }
/* 269:    */     
/* 270:    */     public void run()
/* 271:    */     {
/* 272:431 */       ServerSocket serverSocket = null;
/* 273:    */       try
/* 274:    */       {
/* 275:433 */         serverSocket = SocketHubAppender.this.createServerSocket(this.port);
/* 276:434 */         serverSocket.setSoTimeout(1000);
/* 277:    */       }
/* 278:    */       catch (Exception e)
/* 279:    */       {
/* 280:437 */         if (((e instanceof InterruptedIOException)) || ((e instanceof InterruptedException))) {
/* 281:438 */           Thread.currentThread().interrupt();
/* 282:    */         }
/* 283:440 */         LogLog.error("exception setting timeout, shutting down server socket.", e);
/* 284:441 */         this.keepRunning = false;
/* 285:442 */         return;
/* 286:    */       }
/* 287:    */       try
/* 288:    */       {
/* 289:    */         try
/* 290:    */         {
/* 291:447 */           serverSocket.setSoTimeout(1000);
/* 292:    */         }
/* 293:    */         catch (SocketException e)
/* 294:    */         {
/* 295:450 */           LogLog.error("exception setting timeout, shutting down server socket.", e); return;
/* 296:    */         }
/* 297:454 */         while (this.keepRunning)
/* 298:    */         {
/* 299:455 */           Socket socket = null;
/* 300:    */           try
/* 301:    */           {
/* 302:457 */             socket = serverSocket.accept();
/* 303:    */           }
/* 304:    */           catch (InterruptedIOException e) {}catch (SocketException e)
/* 305:    */           {
/* 306:463 */             LogLog.error("exception accepting socket, shutting down server socket.", e);
/* 307:464 */             this.keepRunning = false;
/* 308:    */           }
/* 309:    */           catch (IOException e)
/* 310:    */           {
/* 311:467 */             LogLog.error("exception accepting socket.", e);
/* 312:    */           }
/* 313:471 */           if (socket != null) {
/* 314:    */             try
/* 315:    */             {
/* 316:473 */               InetAddress remoteAddress = socket.getInetAddress();
/* 317:474 */               LogLog.debug("accepting connection from " + remoteAddress.getHostName() + " (" + remoteAddress.getHostAddress() + ")");
/* 318:    */               
/* 319:    */ 
/* 320:    */ 
/* 321:478 */               ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
/* 322:479 */               if ((SocketHubAppender.this.buffer != null) && (SocketHubAppender.this.buffer.length() > 0)) {
/* 323:480 */                 sendCachedEvents(oos);
/* 324:    */               }
/* 325:484 */               this.oosList.addElement(oos);
/* 326:    */             }
/* 327:    */             catch (IOException e)
/* 328:    */             {
/* 329:486 */               if ((e instanceof InterruptedIOException)) {
/* 330:487 */                 Thread.currentThread().interrupt();
/* 331:    */               }
/* 332:489 */               LogLog.error("exception creating output stream on socket.", e);
/* 333:    */             }
/* 334:    */           }
/* 335:    */         }
/* 336:    */         return;
/* 337:    */       }
/* 338:    */       finally
/* 339:    */       {
/* 340:    */         try
/* 341:    */         {
/* 342:497 */           serverSocket.close();
/* 343:    */         }
/* 344:    */         catch (InterruptedIOException e)
/* 345:    */         {
/* 346:499 */           Thread.currentThread().interrupt();
/* 347:    */         }
/* 348:    */         catch (IOException e) {}
/* 349:    */       }
/* 350:    */     }
/* 351:    */   }
/* 352:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.SocketHubAppender
 * JD-Core Version:    0.7.0.1
 */