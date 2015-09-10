/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.DatagramPacket;
/*   5:    */ import java.net.DatagramSocket;
/*   6:    */ import java.net.InetAddress;
/*   7:    */ import java.net.SocketTimeoutException;
/*   8:    */ import java.net.UnknownHostException;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.StringTokenizer;
/*  11:    */ import jcifs.Config;
/*  12:    */ import jcifs.util.Hexdump;
/*  13:    */ import jcifs.util.LogStream;
/*  14:    */ 
/*  15:    */ class NameServiceClient
/*  16:    */   implements Runnable
/*  17:    */ {
/*  18:    */   static final int DEFAULT_SO_TIMEOUT = 5000;
/*  19:    */   static final int DEFAULT_RCV_BUF_SIZE = 576;
/*  20:    */   static final int DEFAULT_SND_BUF_SIZE = 576;
/*  21:    */   static final int NAME_SERVICE_UDP_PORT = 137;
/*  22:    */   static final int DEFAULT_RETRY_COUNT = 2;
/*  23:    */   static final int DEFAULT_RETRY_TIMEOUT = 3000;
/*  24:    */   static final int RESOLVER_LMHOSTS = 1;
/*  25:    */   static final int RESOLVER_BCAST = 2;
/*  26:    */   static final int RESOLVER_WINS = 3;
/*  27: 42 */   private static final int SND_BUF_SIZE = Config.getInt("jcifs.netbios.snd_buf_size", 576);
/*  28: 43 */   private static final int RCV_BUF_SIZE = Config.getInt("jcifs.netbios.rcv_buf_size", 576);
/*  29: 44 */   private static final int SO_TIMEOUT = Config.getInt("jcifs.netbios.soTimeout", 5000);
/*  30: 45 */   private static final int RETRY_COUNT = Config.getInt("jcifs.netbios.retryCount", 2);
/*  31: 46 */   private static final int RETRY_TIMEOUT = Config.getInt("jcifs.netbios.retryTimeout", 3000);
/*  32: 47 */   private static final int LPORT = Config.getInt("jcifs.netbios.lport", 0);
/*  33: 48 */   private static final InetAddress LADDR = Config.getInetAddress("jcifs.netbios.laddr", null);
/*  34: 49 */   private static final String RO = Config.getProperty("jcifs.resolveOrder");
/*  35: 51 */   private static LogStream log = LogStream.getInstance();
/*  36: 53 */   private final Object LOCK = new Object();
/*  37:    */   private int lport;
/*  38:    */   private int closeTimeout;
/*  39:    */   private byte[] snd_buf;
/*  40:    */   private byte[] rcv_buf;
/*  41:    */   private DatagramSocket socket;
/*  42:    */   private DatagramPacket in;
/*  43:    */   private DatagramPacket out;
/*  44: 59 */   private HashMap responseTable = new HashMap();
/*  45:    */   private Thread thread;
/*  46: 61 */   private int nextNameTrnId = 0;
/*  47:    */   private int[] resolveOrder;
/*  48:    */   InetAddress laddr;
/*  49:    */   InetAddress baddr;
/*  50:    */   
/*  51:    */   NameServiceClient()
/*  52:    */   {
/*  53: 67 */     this(LPORT, LADDR);
/*  54:    */   }
/*  55:    */   
/*  56:    */   NameServiceClient(int lport, InetAddress laddr)
/*  57:    */   {
/*  58: 70 */     this.lport = lport;
/*  59: 71 */     this.laddr = laddr;
/*  60:    */     try
/*  61:    */     {
/*  62: 74 */       this.baddr = Config.getInetAddress("jcifs.netbios.baddr", InetAddress.getByName("255.255.255.255"));
/*  63:    */     }
/*  64:    */     catch (UnknownHostException uhe) {}
/*  65: 79 */     this.snd_buf = new byte[SND_BUF_SIZE];
/*  66: 80 */     this.rcv_buf = new byte[RCV_BUF_SIZE];
/*  67: 81 */     this.out = new DatagramPacket(this.snd_buf, SND_BUF_SIZE, this.baddr, 137);
/*  68: 82 */     this.in = new DatagramPacket(this.rcv_buf, RCV_BUF_SIZE);
/*  69: 84 */     if ((RO == null) || (RO.length() == 0))
/*  70:    */     {
/*  71: 92 */       if (NbtAddress.getWINSAddress() == null)
/*  72:    */       {
/*  73: 93 */         this.resolveOrder = new int[2];
/*  74: 94 */         this.resolveOrder[0] = 1;
/*  75: 95 */         this.resolveOrder[1] = 2;
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79: 97 */         this.resolveOrder = new int[3];
/*  80: 98 */         this.resolveOrder[0] = 1;
/*  81: 99 */         this.resolveOrder[1] = 3;
/*  82:100 */         this.resolveOrder[2] = 2;
/*  83:    */       }
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87:103 */       int[] tmp = new int[3];
/*  88:104 */       StringTokenizer st = new StringTokenizer(RO, ",");
/*  89:105 */       int i = 0;
/*  90:106 */       while (st.hasMoreTokens())
/*  91:    */       {
/*  92:107 */         String s = st.nextToken().trim();
/*  93:108 */         if (s.equalsIgnoreCase("LMHOSTS")) {
/*  94:109 */           tmp[(i++)] = 1;
/*  95:110 */         } else if (s.equalsIgnoreCase("WINS"))
/*  96:    */         {
/*  97:111 */           if (NbtAddress.getWINSAddress() == null)
/*  98:    */           {
/*  99:112 */             if (LogStream.level > 1) {
/* 100:113 */               log.println("NetBIOS resolveOrder specifies WINS however the jcifs.netbios.wins property has not been set");
/* 101:    */             }
/* 102:    */           }
/* 103:    */           else {
/* 104:118 */             tmp[(i++)] = 3;
/* 105:    */           }
/* 106:    */         }
/* 107:119 */         else if (s.equalsIgnoreCase("BCAST")) {
/* 108:120 */           tmp[(i++)] = 2;
/* 109:121 */         } else if (!s.equalsIgnoreCase("DNS")) {
/* 110:123 */           if (LogStream.level > 1) {
/* 111:124 */             log.println("unknown resolver method: " + s);
/* 112:    */           }
/* 113:    */         }
/* 114:    */       }
/* 115:127 */       this.resolveOrder = new int[i];
/* 116:128 */       System.arraycopy(tmp, 0, this.resolveOrder, 0, i);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   int getNextNameTrnId()
/* 121:    */   {
/* 122:133 */     if ((++this.nextNameTrnId & 0xFFFF) == 0) {
/* 123:134 */       this.nextNameTrnId = 1;
/* 124:    */     }
/* 125:136 */     return this.nextNameTrnId;
/* 126:    */   }
/* 127:    */   
/* 128:    */   void ensureOpen(int timeout)
/* 129:    */     throws IOException
/* 130:    */   {
/* 131:139 */     this.closeTimeout = 0;
/* 132:140 */     if (SO_TIMEOUT != 0) {
/* 133:141 */       this.closeTimeout = Math.max(SO_TIMEOUT, timeout);
/* 134:    */     }
/* 135:145 */     if (this.socket == null)
/* 136:    */     {
/* 137:146 */       this.socket = new DatagramSocket(this.lport, this.laddr);
/* 138:147 */       this.thread = new Thread(this, "JCIFS-NameServiceClient");
/* 139:148 */       this.thread.setDaemon(true);
/* 140:149 */       this.thread.start();
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   void tryClose()
/* 145:    */   {
/* 146:153 */     synchronized (this.LOCK)
/* 147:    */     {
/* 148:163 */       if (this.socket != null)
/* 149:    */       {
/* 150:164 */         this.socket.close();
/* 151:165 */         this.socket = null;
/* 152:    */       }
/* 153:167 */       this.thread = null;
/* 154:168 */       this.responseTable.clear();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void run()
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:176 */       while (this.thread == Thread.currentThread())
/* 163:    */       {
/* 164:177 */         this.in.setLength(RCV_BUF_SIZE);
/* 165:    */         
/* 166:179 */         this.socket.setSoTimeout(this.closeTimeout);
/* 167:180 */         this.socket.receive(this.in);
/* 168:182 */         if (LogStream.level > 3) {
/* 169:183 */           log.println("NetBIOS: new data read from socket");
/* 170:    */         }
/* 171:185 */         int nameTrnId = NameServicePacket.readNameTrnId(this.rcv_buf, 0);
/* 172:186 */         NameServicePacket response = (NameServicePacket)this.responseTable.get(new Integer(nameTrnId));
/* 173:187 */         if ((response != null) && (!response.received)) {
/* 174:190 */           synchronized (response)
/* 175:    */           {
/* 176:191 */             response.readWireFormat(this.rcv_buf, 0);
/* 177:192 */             response.received = true;
/* 178:194 */             if (LogStream.level > 3)
/* 179:    */             {
/* 180:195 */               log.println(response);
/* 181:196 */               Hexdump.hexdump(log, this.rcv_buf, 0, this.in.getLength());
/* 182:    */             }
/* 183:    */           }
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */     catch (SocketTimeoutException ste) {}catch (Exception ex)
/* 188:    */     {
/* 189:204 */       if (LogStream.level > 2) {
/* 190:205 */         ex.printStackTrace(log);
/* 191:    */       }
/* 192:    */     }
/* 193:    */     finally
/* 194:    */     {
/* 195:207 */       tryClose();
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   void send(NameServicePacket request, NameServicePacket response, int timeout)
/* 200:    */     throws IOException
/* 201:    */   {
/* 202:212 */     Integer nid = null;
/* 203:213 */     int max = NbtAddress.NBNS.length;
/* 204:215 */     if (max == 0) {
/* 205:216 */       max = 1;
/* 206:    */     }
/* 207:218 */     synchronized (response)
/* 208:    */     {
/* 209:219 */       while (max-- > 0)
/* 210:    */       {
/* 211:    */         try
/* 212:    */         {
/* 213:221 */           synchronized (this.LOCK)
/* 214:    */           {
/* 215:222 */             request.nameTrnId = getNextNameTrnId();
/* 216:223 */             nid = new Integer(request.nameTrnId);
/* 217:    */             
/* 218:225 */             this.out.setAddress(request.addr);
/* 219:226 */             this.out.setLength(request.writeWireFormat(this.snd_buf, 0));
/* 220:227 */             response.received = false;
/* 221:    */             
/* 222:229 */             this.responseTable.put(nid, response);
/* 223:230 */             ensureOpen(timeout + 1000);
/* 224:231 */             this.socket.send(this.out);
/* 225:233 */             if (LogStream.level > 3)
/* 226:    */             {
/* 227:234 */               log.println(request);
/* 228:235 */               Hexdump.hexdump(log, this.snd_buf, 0, this.out.getLength());
/* 229:    */             }
/* 230:    */           }
/* 231:239 */           long start = System.currentTimeMillis();
/* 232:240 */           while (timeout > 0)
/* 233:    */           {
/* 234:241 */             response.wait(timeout);
/* 235:248 */             if ((response.received) && (request.questionType == response.recordType))
/* 236:    */             {
/* 237:258 */               this.responseTable.remove(nid); return;
/* 238:    */             }
/* 239:251 */             response.received = false;
/* 240:252 */             timeout = (int)(timeout - (System.currentTimeMillis() - start));
/* 241:    */           }
/* 242:    */         }
/* 243:    */         catch (InterruptedException ie)
/* 244:    */         {
/* 245:256 */           throw new IOException(ie.getMessage());
/* 246:    */         }
/* 247:    */         finally
/* 248:    */         {
/* 249:258 */           this.responseTable.remove(nid);
/* 250:    */         }
/* 251:261 */         synchronized (this.LOCK)
/* 252:    */         {
/* 253:262 */           if (NbtAddress.isWINS(request.addr))
/* 254:    */           {
/* 255:268 */             if (request.addr == NbtAddress.getWINSAddress()) {
/* 256:269 */               NbtAddress.switchWINS();
/* 257:    */             }
/* 258:270 */             request.addr = NbtAddress.getWINSAddress();
/* 259:    */           }
/* 260:    */         }
/* 261:    */       }
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   NbtAddress[] getAllByName(Name name, InetAddress addr)
/* 266:    */     throws UnknownHostException
/* 267:    */   {
/* 268:279 */     NameQueryRequest request = new NameQueryRequest(name);
/* 269:280 */     NameQueryResponse response = new NameQueryResponse();
/* 270:    */     
/* 271:282 */     request.addr = (addr != null ? addr : NbtAddress.getWINSAddress());
/* 272:283 */     request.isBroadcast = (request.addr == null);
/* 273:    */     int n;
/* 274:    */     int n;
/* 275:285 */     if (request.isBroadcast)
/* 276:    */     {
/* 277:286 */       request.addr = this.baddr;
/* 278:287 */       n = RETRY_COUNT;
/* 279:    */     }
/* 280:    */     else
/* 281:    */     {
/* 282:289 */       request.isBroadcast = false;
/* 283:290 */       n = 1;
/* 284:    */     }
/* 285:    */     do
/* 286:    */     {
/* 287:    */       try
/* 288:    */       {
/* 289:295 */         send(request, response, RETRY_TIMEOUT);
/* 290:    */       }
/* 291:    */       catch (IOException ioe)
/* 292:    */       {
/* 293:297 */         if (LogStream.level > 1) {
/* 294:298 */           ioe.printStackTrace(log);
/* 295:    */         }
/* 296:299 */         throw new UnknownHostException(name.name);
/* 297:    */       }
/* 298:302 */       if ((response.received) && (response.resultCode == 0)) {
/* 299:303 */         return response.addrEntry;
/* 300:    */       }
/* 301:305 */       n--;
/* 302:305 */     } while ((n > 0) && (request.isBroadcast));
/* 303:307 */     throw new UnknownHostException(name.name);
/* 304:    */   }
/* 305:    */   
/* 306:    */   NbtAddress getByName(Name name, InetAddress addr)
/* 307:    */     throws UnknownHostException
/* 308:    */   {
/* 309:312 */     NameQueryRequest request = new NameQueryRequest(name);
/* 310:313 */     NameQueryResponse response = new NameQueryResponse();
/* 311:315 */     if (addr != null)
/* 312:    */     {
/* 313:318 */       request.addr = addr;
/* 314:319 */       request.isBroadcast = (addr.getAddress()[3] == -1);
/* 315:    */       
/* 316:321 */       int n = RETRY_COUNT;
/* 317:    */       do
/* 318:    */       {
/* 319:    */         try
/* 320:    */         {
/* 321:324 */           send(request, response, RETRY_TIMEOUT);
/* 322:    */         }
/* 323:    */         catch (IOException ioe)
/* 324:    */         {
/* 325:326 */           if (LogStream.level > 1) {
/* 326:327 */             ioe.printStackTrace(log);
/* 327:    */           }
/* 328:328 */           throw new UnknownHostException(name.name);
/* 329:    */         }
/* 330:331 */         if ((response.received) && (response.resultCode == 0))
/* 331:    */         {
/* 332:332 */           int last = response.addrEntry.length - 1;
/* 333:333 */           response.addrEntry[last].hostName.srcHashCode = addr.hashCode();
/* 334:334 */           return response.addrEntry[last];
/* 335:    */         }
/* 336:336 */         n--;
/* 337:336 */       } while ((n > 0) && (request.isBroadcast));
/* 338:338 */       throw new UnknownHostException(name.name);
/* 339:    */     }
/* 340:345 */     for (int i = 0; i < this.resolveOrder.length; i++) {
/* 341:    */       try
/* 342:    */       {
/* 343:347 */         switch (this.resolveOrder[i])
/* 344:    */         {
/* 345:    */         case 1: 
/* 346:349 */           NbtAddress ans = Lmhosts.getByName(name);
/* 347:350 */           if (ans != null)
/* 348:    */           {
/* 349:351 */             ans.hostName.srcHashCode = 0;
/* 350:    */             
/* 351:353 */             return ans;
/* 352:    */           }
/* 353:    */           break;
/* 354:    */         case 2: 
/* 355:    */         case 3: 
/* 356:358 */           if ((this.resolveOrder[i] == 3) && (name.name != "\001\002__MSBROWSE__\002") && (name.hexCode != 29))
/* 357:    */           {
/* 358:361 */             request.addr = NbtAddress.getWINSAddress();
/* 359:362 */             request.isBroadcast = false;
/* 360:    */           }
/* 361:    */           else
/* 362:    */           {
/* 363:364 */             request.addr = this.baddr;
/* 364:365 */             request.isBroadcast = true;
/* 365:    */           }
/* 366:368 */           int n = RETRY_COUNT;
/* 367:369 */           while (n-- > 0)
/* 368:    */           {
/* 369:    */             try
/* 370:    */             {
/* 371:371 */               send(request, response, RETRY_TIMEOUT);
/* 372:    */             }
/* 373:    */             catch (IOException ioe)
/* 374:    */             {
/* 375:373 */               if (LogStream.level > 1) {
/* 376:374 */                 ioe.printStackTrace(log);
/* 377:    */               }
/* 378:375 */               throw new UnknownHostException(name.name);
/* 379:    */             }
/* 380:377 */             if ((response.received) && (response.resultCode == 0))
/* 381:    */             {
/* 382:384 */               response.addrEntry[0].hostName.srcHashCode = request.addr.hashCode();
/* 383:    */               
/* 384:386 */               return response.addrEntry[0];
/* 385:    */             }
/* 386:387 */             if (this.resolveOrder[i] == 3) {
/* 387:    */               break;
/* 388:    */             }
/* 389:    */           }
/* 390:    */         }
/* 391:    */       }
/* 392:    */       catch (IOException ioe) {}
/* 393:    */     }
/* 394:398 */     throw new UnknownHostException(name.name);
/* 395:    */   }
/* 396:    */   
/* 397:    */   NbtAddress[] getNodeStatus(NbtAddress addr)
/* 398:    */     throws UnknownHostException
/* 399:    */   {
/* 400:405 */     NodeStatusResponse response = new NodeStatusResponse(addr);
/* 401:406 */     NodeStatusRequest request = new NodeStatusRequest(new Name("", 0, null));
/* 402:    */     
/* 403:408 */     request.addr = addr.getInetAddress();
/* 404:    */     
/* 405:410 */     int n = RETRY_COUNT;
/* 406:411 */     while (n-- > 0)
/* 407:    */     {
/* 408:    */       try
/* 409:    */       {
/* 410:413 */         send(request, response, RETRY_TIMEOUT);
/* 411:    */       }
/* 412:    */       catch (IOException ioe)
/* 413:    */       {
/* 414:415 */         if (LogStream.level > 1) {
/* 415:416 */           ioe.printStackTrace(log);
/* 416:    */         }
/* 417:417 */         throw new UnknownHostException(addr.toString());
/* 418:    */       }
/* 419:419 */       if ((response.received) && (response.resultCode == 0))
/* 420:    */       {
/* 421:434 */         int srcHashCode = request.addr.hashCode();
/* 422:435 */         for (int i = 0; i < response.addressArray.length; i++) {
/* 423:436 */           response.addressArray[i].hostName.srcHashCode = srcHashCode;
/* 424:    */         }
/* 425:438 */         return response.addressArray;
/* 426:    */       }
/* 427:    */     }
/* 428:441 */     throw new UnknownHostException(addr.hostName.name);
/* 429:    */   }
/* 430:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NameServiceClient
 * JD-Core Version:    0.7.0.1
 */