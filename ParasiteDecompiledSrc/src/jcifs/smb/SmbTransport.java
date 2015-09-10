/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.net.ConnectException;
/*   7:    */ import java.net.InetAddress;
/*   8:    */ import java.net.NoRouteToHostException;
/*   9:    */ import java.net.Socket;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import java.util.ListIterator;
/*  13:    */ import jcifs.UniAddress;
/*  14:    */ import jcifs.netbios.Name;
/*  15:    */ import jcifs.netbios.NbtAddress;
/*  16:    */ import jcifs.netbios.NbtException;
/*  17:    */ import jcifs.netbios.SessionRequestPacket;
/*  18:    */ import jcifs.netbios.SessionServicePacket;
/*  19:    */ import jcifs.util.Encdec;
/*  20:    */ import jcifs.util.Hexdump;
/*  21:    */ import jcifs.util.LogStream;
/*  22:    */ import jcifs.util.transport.Request;
/*  23:    */ import jcifs.util.transport.Response;
/*  24:    */ import jcifs.util.transport.Transport;
/*  25:    */ import jcifs.util.transport.TransportException;
/*  26:    */ 
/*  27:    */ public class SmbTransport
/*  28:    */   extends Transport
/*  29:    */   implements SmbConstants
/*  30:    */ {
/*  31: 35 */   static final byte[] BUF = new byte[65535];
/*  32: 36 */   static final SmbComNegotiate NEGOTIATE_REQUEST = new SmbComNegotiate();
/*  33: 37 */   static LogStream log = LogStream.getInstance();
/*  34: 38 */   static HashMap dfsRoots = null;
/*  35:    */   InetAddress localAddr;
/*  36:    */   int localPort;
/*  37:    */   UniAddress address;
/*  38:    */   Socket socket;
/*  39:    */   int port;
/*  40:    */   int mid;
/*  41:    */   OutputStream out;
/*  42:    */   InputStream in;
/*  43:    */   
/*  44:    */   static synchronized SmbTransport getSmbTransport(UniAddress address, int port)
/*  45:    */   {
/*  46: 41 */     return getSmbTransport(address, port, SmbConstants.LADDR, SmbConstants.LPORT, null);
/*  47:    */   }
/*  48:    */   
/*  49:    */   static synchronized SmbTransport getSmbTransport(UniAddress address, int port, InetAddress localAddr, int localPort, String hostName)
/*  50:    */   {
/*  51: 47 */     synchronized (SmbConstants.CONNECTIONS)
/*  52:    */     {
/*  53: 48 */       if (SmbConstants.SSN_LIMIT != 1)
/*  54:    */       {
/*  55: 49 */         ListIterator iter = SmbConstants.CONNECTIONS.listIterator();
/*  56: 50 */         while (iter.hasNext())
/*  57:    */         {
/*  58: 51 */           SmbTransport conn = (SmbTransport)iter.next();
/*  59: 52 */           if ((conn.matches(address, port, localAddr, localPort, hostName)) && ((SmbConstants.SSN_LIMIT == 0) || (conn.sessions.size() < SmbConstants.SSN_LIMIT))) {
/*  60: 54 */             return conn;
/*  61:    */           }
/*  62:    */         }
/*  63:    */       }
/*  64: 59 */       SmbTransport conn = new SmbTransport(address, port, localAddr, localPort);
/*  65: 60 */       SmbConstants.CONNECTIONS.add(0, conn);
/*  66:    */     }
/*  67:    */     SmbTransport conn;
/*  68: 63 */     return conn;
/*  69:    */   }
/*  70:    */   
/*  71: 95 */   byte[] sbuf = new byte[512];
/*  72: 96 */   SmbComBlankResponse key = new SmbComBlankResponse();
/*  73: 97 */   long sessionExpiration = System.currentTimeMillis() + SmbConstants.SO_TIMEOUT;
/*  74: 98 */   LinkedList referrals = new LinkedList();
/*  75: 99 */   SigningDigest digest = null;
/*  76:100 */   LinkedList sessions = new LinkedList();
/*  77:101 */   ServerData server = new ServerData();
/*  78:103 */   int flags2 = SmbConstants.FLAGS2;
/*  79:104 */   int maxMpxCount = SmbConstants.MAX_MPX_COUNT;
/*  80:105 */   int snd_buf_size = SmbConstants.SND_BUF_SIZE;
/*  81:106 */   int rcv_buf_size = SmbConstants.RCV_BUF_SIZE;
/*  82:107 */   int capabilities = SmbConstants.CAPABILITIES;
/*  83:108 */   int sessionKey = 0;
/*  84:109 */   boolean useUnicode = SmbConstants.USE_UNICODE;
/*  85:110 */   String tconHostName = null;
/*  86:    */   
/*  87:    */   SmbTransport(UniAddress address, int port, InetAddress localAddr, int localPort)
/*  88:    */   {
/*  89:113 */     this.address = address;
/*  90:114 */     this.port = port;
/*  91:115 */     this.localAddr = localAddr;
/*  92:116 */     this.localPort = localPort;
/*  93:    */   }
/*  94:    */   
/*  95:    */   synchronized SmbSession getSmbSession()
/*  96:    */   {
/*  97:120 */     return getSmbSession(new NtlmPasswordAuthentication(null, null, null));
/*  98:    */   }
/*  99:    */   
/* 100:    */   synchronized SmbSession getSmbSession(NtlmPasswordAuthentication auth)
/* 101:    */   {
/* 102:126 */     ListIterator iter = this.sessions.listIterator();
/* 103:127 */     while (iter.hasNext())
/* 104:    */     {
/* 105:128 */       SmbSession ssn = (SmbSession)iter.next();
/* 106:129 */       if (ssn.matches(auth))
/* 107:    */       {
/* 108:130 */         ssn.auth = auth;
/* 109:131 */         return ssn;
/* 110:    */       }
/* 111:    */     }
/* 112:    */     long now;
/* 113:136 */     if ((SmbConstants.SO_TIMEOUT > 0) && (this.sessionExpiration < (now = System.currentTimeMillis())))
/* 114:    */     {
/* 115:137 */       this.sessionExpiration = (now + SmbConstants.SO_TIMEOUT);
/* 116:138 */       iter = this.sessions.listIterator();
/* 117:139 */       while (iter.hasNext())
/* 118:    */       {
/* 119:140 */         SmbSession ssn = (SmbSession)iter.next();
/* 120:141 */         if (ssn.expiration < now) {
/* 121:142 */           ssn.logoff(false);
/* 122:    */         }
/* 123:    */       }
/* 124:    */     }
/* 125:147 */     SmbSession ssn = new SmbSession(this.address, this.port, this.localAddr, this.localPort, auth);
/* 126:148 */     ssn.transport = this;
/* 127:149 */     this.sessions.add(ssn);
/* 128:    */     
/* 129:151 */     return ssn;
/* 130:    */   }
/* 131:    */   
/* 132:    */   boolean matches(UniAddress address, int port, InetAddress localAddr, int localPort, String hostName)
/* 133:    */   {
/* 134:154 */     if (hostName == null) {
/* 135:155 */       hostName = address.getHostName();
/* 136:    */     }
/* 137:156 */     return ((this.tconHostName == null) || (hostName.equalsIgnoreCase(this.tconHostName))) && (address.equals(this.address)) && ((port == 0) || (port == this.port) || ((port == 445) && (this.port == 139))) && ((localAddr == this.localAddr) || ((localAddr != null) && (localAddr.equals(this.localAddr)))) && (localPort == this.localPort);
/* 138:    */   }
/* 139:    */   
/* 140:    */   boolean hasCapability(int cap)
/* 141:    */     throws SmbException
/* 142:    */   {
/* 143:    */     try
/* 144:    */     {
/* 145:168 */       connect(SmbConstants.RESPONSE_TIMEOUT);
/* 146:    */     }
/* 147:    */     catch (IOException ioe)
/* 148:    */     {
/* 149:170 */       throw new SmbException(ioe.getMessage(), ioe);
/* 150:    */     }
/* 151:172 */     return (this.capabilities & cap) == cap;
/* 152:    */   }
/* 153:    */   
/* 154:    */   boolean isSignatureSetupRequired(NtlmPasswordAuthentication auth)
/* 155:    */   {
/* 156:175 */     return ((this.flags2 & 0x4) != 0) && (this.digest == null) && (auth != NtlmPasswordAuthentication.NULL) && (!NtlmPasswordAuthentication.NULL.equals(auth));
/* 157:    */   }
/* 158:    */   
/* 159:    */   void ssn139()
/* 160:    */     throws IOException
/* 161:    */   {
/* 162:182 */     Name calledName = new Name(this.address.firstCalledName(), 32, null);
/* 163:    */     do
/* 164:    */     {
/* 165:184 */       if (this.localAddr == null) {
/* 166:185 */         this.socket = new Socket(this.address.getHostAddress(), 139);
/* 167:    */       } else {
/* 168:187 */         this.socket = new Socket(this.address.getHostAddress(), 139, this.localAddr, this.localPort);
/* 169:    */       }
/* 170:189 */       this.socket.setSoTimeout(SmbConstants.SO_TIMEOUT);
/* 171:190 */       this.out = this.socket.getOutputStream();
/* 172:191 */       this.in = this.socket.getInputStream();
/* 173:    */       
/* 174:193 */       SessionServicePacket ssp = new SessionRequestPacket(calledName, NbtAddress.getLocalName());
/* 175:    */       
/* 176:195 */       this.out.write(this.sbuf, 0, ssp.writeWireFormat(this.sbuf, 0));
/* 177:196 */       if (readn(this.in, this.sbuf, 0, 4) < 4)
/* 178:    */       {
/* 179:    */         try
/* 180:    */         {
/* 181:198 */           this.socket.close();
/* 182:    */         }
/* 183:    */         catch (IOException ioe) {}
/* 184:201 */         throw new SmbException("EOF during NetBIOS session request");
/* 185:    */       }
/* 186:203 */       switch (this.sbuf[0] & 0xFF)
/* 187:    */       {
/* 188:    */       case 130: 
/* 189:205 */         if (LogStream.level >= 4) {
/* 190:206 */           log.println("session established ok with " + this.address);
/* 191:    */         }
/* 192:207 */         return;
/* 193:    */       case 131: 
/* 194:209 */         int errorCode = this.in.read() & 0xFF;
/* 195:210 */         switch (errorCode)
/* 196:    */         {
/* 197:    */         case 128: 
/* 198:    */         case 130: 
/* 199:213 */           this.socket.close();
/* 200:214 */           break;
/* 201:    */         default: 
/* 202:216 */           disconnect(true);
/* 203:217 */           throw new NbtException(2, errorCode);
/* 204:    */         }
/* 205:    */         break;
/* 206:    */       case -1: 
/* 207:221 */         disconnect(true);
/* 208:222 */         throw new NbtException(2, -1);
/* 209:    */       default: 
/* 210:225 */         disconnect(true);
/* 211:226 */         throw new NbtException(2, 0);
/* 212:    */       }
/* 213:228 */     } while ((calledName.name = this.address.nextCalledName()) != null);
/* 214:230 */     throw new IOException("Failed to establish session with " + this.address);
/* 215:    */   }
/* 216:    */   
/* 217:    */   private void negotiate(int port, ServerMessageBlock resp)
/* 218:    */     throws IOException
/* 219:    */   {
/* 220:238 */     synchronized (this.sbuf)
/* 221:    */     {
/* 222:239 */       if (port == 139)
/* 223:    */       {
/* 224:240 */         ssn139();
/* 225:    */       }
/* 226:    */       else
/* 227:    */       {
/* 228:242 */         if (port == 0) {
/* 229:243 */           port = 445;
/* 230:    */         }
/* 231:244 */         if (this.localAddr == null) {
/* 232:245 */           this.socket = new Socket(this.address.getHostAddress(), port);
/* 233:    */         } else {
/* 234:247 */           this.socket = new Socket(this.address.getHostAddress(), port, this.localAddr, this.localPort);
/* 235:    */         }
/* 236:249 */         this.socket.setSoTimeout(SmbConstants.SO_TIMEOUT);
/* 237:250 */         this.out = this.socket.getOutputStream();
/* 238:251 */         this.in = this.socket.getInputStream();
/* 239:    */       }
/* 240:254 */       if (++this.mid == 32000) {
/* 241:254 */         this.mid = 1;
/* 242:    */       }
/* 243:255 */       NEGOTIATE_REQUEST.mid = this.mid;
/* 244:256 */       int n = NEGOTIATE_REQUEST.encode(this.sbuf, 4);
/* 245:257 */       Encdec.enc_uint32be(n & 0xFFFF, this.sbuf, 0);
/* 246:259 */       if (LogStream.level >= 4)
/* 247:    */       {
/* 248:260 */         log.println(NEGOTIATE_REQUEST);
/* 249:261 */         if (LogStream.level >= 6) {
/* 250:262 */           Hexdump.hexdump(log, this.sbuf, 4, n);
/* 251:    */         }
/* 252:    */       }
/* 253:266 */       this.out.write(this.sbuf, 0, 4 + n);
/* 254:267 */       this.out.flush();
/* 255:271 */       if (peekKey() == null) {
/* 256:272 */         throw new IOException("transport closed in negotiate");
/* 257:    */       }
/* 258:273 */       int size = Encdec.dec_uint16be(this.sbuf, 2) & 0xFFFF;
/* 259:274 */       if ((size < 33) || (4 + size > this.sbuf.length)) {
/* 260:275 */         throw new IOException("Invalid payload size: " + size);
/* 261:    */       }
/* 262:277 */       readn(this.in, this.sbuf, 36, size - 32);
/* 263:278 */       resp.decode(this.sbuf, 4);
/* 264:280 */       if (LogStream.level >= 4)
/* 265:    */       {
/* 266:281 */         log.println(resp);
/* 267:282 */         if (LogStream.level >= 6) {
/* 268:283 */           Hexdump.hexdump(log, this.sbuf, 4, n);
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void connect()
/* 275:    */     throws SmbException
/* 276:    */   {
/* 277:    */     try
/* 278:    */     {
/* 279:290 */       super.connect(SmbConstants.RESPONSE_TIMEOUT);
/* 280:    */     }
/* 281:    */     catch (TransportException te)
/* 282:    */     {
/* 283:292 */       throw new SmbException("Failed to connect: " + this.address, te);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected void doConnect()
/* 288:    */     throws IOException
/* 289:    */   {
/* 290:300 */     SmbComNegotiateResponse resp = new SmbComNegotiateResponse(this.server);
/* 291:    */     try
/* 292:    */     {
/* 293:302 */       negotiate(this.port, resp);
/* 294:    */     }
/* 295:    */     catch (ConnectException ce)
/* 296:    */     {
/* 297:304 */       this.port = ((this.port == 0) || (this.port == 445) ? 139 : 445);
/* 298:305 */       negotiate(this.port, resp);
/* 299:    */     }
/* 300:    */     catch (NoRouteToHostException nr)
/* 301:    */     {
/* 302:307 */       this.port = ((this.port == 0) || (this.port == 445) ? 139 : 445);
/* 303:308 */       negotiate(this.port, resp);
/* 304:    */     }
/* 305:311 */     if (resp.dialectIndex > 10) {
/* 306:312 */       throw new SmbException("This client does not support the negotiated dialect.");
/* 307:    */     }
/* 308:314 */     if (((this.server.capabilities & 0x80000000) != -2147483648) && (this.server.encryptionKeyLength != 8) && (SmbConstants.LM_COMPATIBILITY == 0)) {
/* 309:317 */       throw new SmbException("Unexpected encryption key length: " + this.server.encryptionKeyLength);
/* 310:    */     }
/* 311:322 */     this.tconHostName = this.address.getHostName();
/* 312:323 */     if ((this.server.signaturesRequired) || ((this.server.signaturesEnabled) && (SmbConstants.SIGNPREF))) {
/* 313:324 */       this.flags2 |= 0x4;
/* 314:    */     } else {
/* 315:326 */       this.flags2 &= 0xFFFB;
/* 316:    */     }
/* 317:328 */     this.maxMpxCount = Math.min(this.maxMpxCount, this.server.maxMpxCount);
/* 318:329 */     if (this.maxMpxCount < 1) {
/* 319:329 */       this.maxMpxCount = 1;
/* 320:    */     }
/* 321:330 */     this.snd_buf_size = Math.min(this.snd_buf_size, this.server.maxBufferSize);
/* 322:331 */     this.capabilities &= this.server.capabilities;
/* 323:332 */     if ((this.server.capabilities & 0x80000000) == -2147483648) {
/* 324:333 */       this.capabilities |= 0x80000000;
/* 325:    */     }
/* 326:335 */     if ((this.capabilities & 0x4) == 0) {
/* 327:337 */       if (SmbConstants.FORCE_UNICODE)
/* 328:    */       {
/* 329:338 */         this.capabilities |= 0x4;
/* 330:    */       }
/* 331:    */       else
/* 332:    */       {
/* 333:340 */         this.useUnicode = false;
/* 334:341 */         this.flags2 &= 0x7FFF;
/* 335:    */       }
/* 336:    */     }
/* 337:    */   }
/* 338:    */   
/* 339:    */   protected void doDisconnect(boolean hard)
/* 340:    */     throws IOException
/* 341:    */   {
/* 342:346 */     ListIterator iter = this.sessions.listIterator();
/* 343:347 */     while (iter.hasNext())
/* 344:    */     {
/* 345:348 */       SmbSession ssn = (SmbSession)iter.next();
/* 346:349 */       ssn.logoff(hard);
/* 347:    */     }
/* 348:351 */     this.socket.shutdownOutput();
/* 349:352 */     this.out.close();
/* 350:353 */     this.in.close();
/* 351:354 */     this.socket.close();
/* 352:355 */     this.digest = null;
/* 353:    */   }
/* 354:    */   
/* 355:    */   protected void makeKey(Request request)
/* 356:    */     throws IOException
/* 357:    */   {
/* 358:360 */     if (++this.mid == 32000) {
/* 359:360 */       this.mid = 1;
/* 360:    */     }
/* 361:361 */     ((ServerMessageBlock)request).mid = this.mid;
/* 362:    */   }
/* 363:    */   
/* 364:    */   protected Request peekKey()
/* 365:    */     throws IOException
/* 366:    */   {
/* 367:    */     int n;
/* 368:    */     do
/* 369:    */     {
/* 370:366 */       if ((n = readn(this.in, this.sbuf, 0, 4)) < 4) {
/* 371:367 */         return null;
/* 372:    */       }
/* 373:368 */     } while (this.sbuf[0] == -123);
/* 374:370 */     if ((n = readn(this.in, this.sbuf, 4, 32)) < 32) {
/* 375:371 */       return null;
/* 376:    */     }
/* 377:372 */     if (LogStream.level >= 4)
/* 378:    */     {
/* 379:373 */       log.println("New data read: " + this);
/* 380:374 */       Hexdump.hexdump(log, this.sbuf, 4, 32);
/* 381:    */     }
/* 382:385 */     while ((this.sbuf[0] != 0) || (this.sbuf[1] != 0) || (this.sbuf[4] != -1) || (this.sbuf[5] != 83) || (this.sbuf[6] != 77) || (this.sbuf[7] != 66))
/* 383:    */     {
/* 384:395 */       for (int i = 0; i < 35; i++) {
/* 385:396 */         this.sbuf[i] = this.sbuf[(i + 1)];
/* 386:    */       }
/* 387:    */       int b;
/* 388:399 */       if ((b = this.in.read()) == -1) {
/* 389:399 */         return null;
/* 390:    */       }
/* 391:400 */       this.sbuf[35] = ((byte)b);
/* 392:    */     }
/* 393:403 */     this.key.mid = (Encdec.dec_uint16le(this.sbuf, 34) & 0xFFFF);
/* 394:    */     
/* 395:    */ 
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:    */ 
/* 400:    */ 
/* 401:411 */     return this.key;
/* 402:    */   }
/* 403:    */   
/* 404:    */   protected void doSend(Request request)
/* 405:    */     throws IOException
/* 406:    */   {
/* 407:415 */     synchronized (BUF)
/* 408:    */     {
/* 409:416 */       ServerMessageBlock smb = (ServerMessageBlock)request;
/* 410:417 */       int n = smb.encode(BUF, 4);
/* 411:418 */       Encdec.enc_uint32be(n & 0xFFFF, BUF, 0);
/* 412:419 */       if (LogStream.level >= 4)
/* 413:    */       {
/* 414:    */         do
/* 415:    */         {
/* 416:421 */           log.println(smb);
/* 417:423 */         } while (((smb instanceof AndXServerMessageBlock)) && ((smb = ((AndXServerMessageBlock)smb).andx) != null));
/* 418:424 */         if (LogStream.level >= 6) {
/* 419:425 */           Hexdump.hexdump(log, BUF, 4, n);
/* 420:    */         }
/* 421:    */       }
/* 422:431 */       this.out.write(BUF, 0, 4 + n);
/* 423:    */     }
/* 424:    */   }
/* 425:    */   
/* 426:    */   protected void doSend0(Request request)
/* 427:    */     throws IOException
/* 428:    */   {
/* 429:    */     try
/* 430:    */     {
/* 431:436 */       doSend(request);
/* 432:    */     }
/* 433:    */     catch (IOException ioe)
/* 434:    */     {
/* 435:438 */       if (LogStream.level > 2) {
/* 436:439 */         ioe.printStackTrace(log);
/* 437:    */       }
/* 438:    */       try
/* 439:    */       {
/* 440:441 */         disconnect(true);
/* 441:    */       }
/* 442:    */       catch (IOException ioe2)
/* 443:    */       {
/* 444:443 */         ioe2.printStackTrace(log);
/* 445:    */       }
/* 446:445 */       throw ioe;
/* 447:    */     }
/* 448:    */   }
/* 449:    */   
/* 450:    */   protected void doRecv(Response response)
/* 451:    */     throws IOException
/* 452:    */   {
/* 453:450 */     ServerMessageBlock resp = (ServerMessageBlock)response;
/* 454:451 */     resp.useUnicode = this.useUnicode;
/* 455:452 */     resp.extendedSecurity = ((this.capabilities & 0x80000000) == -2147483648);
/* 456:454 */     synchronized (BUF)
/* 457:    */     {
/* 458:455 */       System.arraycopy(this.sbuf, 0, BUF, 0, 36);
/* 459:456 */       int size = Encdec.dec_uint16be(BUF, 2) & 0xFFFF;
/* 460:457 */       if ((size < 33) || (4 + size > this.rcv_buf_size)) {
/* 461:458 */         throw new IOException("Invalid payload size: " + size);
/* 462:    */       }
/* 463:460 */       int errorCode = Encdec.dec_uint32le(BUF, 9) & 0xFFFFFFFF;
/* 464:461 */       if ((resp.command == 46) && ((errorCode == 0) || (errorCode == -2147483643)))
/* 465:    */       {
/* 466:464 */         SmbComReadAndXResponse r = (SmbComReadAndXResponse)resp;
/* 467:465 */         int off = 32;
/* 468:    */         
/* 469:467 */         readn(this.in, BUF, 4 + off, 27);off += 27;
/* 470:468 */         resp.decode(BUF, 4);
/* 471:    */         
/* 472:470 */         int pad = r.dataOffset - off;
/* 473:471 */         if ((r.byteCount > 0) && (pad > 0) && (pad < 4)) {
/* 474:472 */           readn(this.in, BUF, 4 + off, pad);
/* 475:    */         }
/* 476:474 */         if (r.dataLength > 0) {
/* 477:475 */           readn(this.in, r.b, r.off, r.dataLength);
/* 478:    */         }
/* 479:    */       }
/* 480:    */       else
/* 481:    */       {
/* 482:477 */         readn(this.in, BUF, 36, size - 32);
/* 483:478 */         resp.decode(BUF, 4);
/* 484:479 */         if ((resp instanceof SmbComTransactionResponse)) {
/* 485:480 */           ((SmbComTransactionResponse)resp).nextElement();
/* 486:    */         }
/* 487:    */       }
/* 488:488 */       if ((this.digest != null) && (resp.errorCode == 0)) {
/* 489:489 */         this.digest.verify(BUF, 4, resp);
/* 490:    */       }
/* 491:492 */       if (LogStream.level >= 4)
/* 492:    */       {
/* 493:493 */         log.println(response);
/* 494:494 */         if (LogStream.level >= 6) {
/* 495:495 */           Hexdump.hexdump(log, BUF, 4, size);
/* 496:    */         }
/* 497:    */       }
/* 498:    */     }
/* 499:    */   }
/* 500:    */   
/* 501:    */   protected void doSkip()
/* 502:    */     throws IOException
/* 503:    */   {
/* 504:501 */     int size = Encdec.dec_uint16be(this.sbuf, 2) & 0xFFFF;
/* 505:502 */     if ((size < 33) || (4 + size > this.rcv_buf_size)) {
/* 506:504 */       this.in.skip(this.in.available());
/* 507:    */     } else {
/* 508:506 */       this.in.skip(size - 32);
/* 509:    */     }
/* 510:    */   }
/* 511:    */   
/* 512:    */   void checkStatus(ServerMessageBlock req, ServerMessageBlock resp)
/* 513:    */     throws SmbException
/* 514:    */   {
/* 515:510 */     resp.errorCode = SmbException.getStatusByCode(resp.errorCode);
/* 516:511 */     switch (resp.errorCode)
/* 517:    */     {
/* 518:    */     case 0: 
/* 519:    */       break;
/* 520:    */     case -1073741790: 
/* 521:    */     case -1073741718: 
/* 522:    */     case -1073741715: 
/* 523:    */     case -1073741714: 
/* 524:    */     case -1073741713: 
/* 525:    */     case -1073741712: 
/* 526:    */     case -1073741711: 
/* 527:    */     case -1073741710: 
/* 528:    */     case -1073741428: 
/* 529:    */     case -1073741260: 
/* 530:524 */       throw new SmbAuthException(resp.errorCode);
/* 531:    */     case -1073741225: 
/* 532:526 */       if (req.auth == null) {
/* 533:527 */         throw new SmbException(resp.errorCode, null);
/* 534:    */       }
/* 535:530 */       DfsReferral dr = getDfsReferrals(req.auth, req.path, 1);
/* 536:531 */       if (dr == null) {
/* 537:532 */         throw new SmbException(resp.errorCode, null);
/* 538:    */       }
/* 539:534 */       SmbFile.dfs.insert(req.path, dr);
/* 540:535 */       throw dr;
/* 541:    */     case -2147483643: 
/* 542:    */       break;
/* 543:    */     case -1073741802: 
/* 544:    */       break;
/* 545:    */     default: 
/* 546:541 */       throw new SmbException(resp.errorCode, null);
/* 547:    */     }
/* 548:543 */     if (resp.verifyFailed) {
/* 549:544 */       throw new SmbException("Signature verification failed.");
/* 550:    */     }
/* 551:    */   }
/* 552:    */   
/* 553:    */   void send(ServerMessageBlock request, ServerMessageBlock response)
/* 554:    */     throws SmbException
/* 555:    */   {
/* 556:549 */     connect();
/* 557:    */     
/* 558:551 */     request.flags2 |= this.flags2;
/* 559:552 */     request.useUnicode = this.useUnicode;
/* 560:553 */     request.response = response;
/* 561:554 */     if (request.digest == null) {
/* 562:555 */       request.digest = this.digest;
/* 563:    */     }
/* 564:    */     try
/* 565:    */     {
/* 566:558 */       if (response == null)
/* 567:    */       {
/* 568:559 */         doSend0(request);
/* 569:560 */         return;
/* 570:    */       }
/* 571:561 */       if ((request instanceof SmbComTransaction))
/* 572:    */       {
/* 573:562 */         response.command = request.command;
/* 574:563 */         SmbComTransaction req = (SmbComTransaction)request;
/* 575:564 */         SmbComTransactionResponse resp = (SmbComTransactionResponse)response;
/* 576:    */         
/* 577:566 */         req.maxBufferSize = this.snd_buf_size;
/* 578:567 */         resp.reset();
/* 579:    */         try
/* 580:    */         {
/* 581:570 */           BufferCache.getBuffers(req, resp);
/* 582:    */           
/* 583:    */ 
/* 584:    */ 
/* 585:    */ 
/* 586:    */ 
/* 587:576 */           req.nextElement();
/* 588:577 */           if (req.hasMoreElements())
/* 589:    */           {
/* 590:578 */             SmbComBlankResponse interim = new SmbComBlankResponse();
/* 591:579 */             super.sendrecv(req, interim, SmbConstants.RESPONSE_TIMEOUT);
/* 592:580 */             if (interim.errorCode != 0) {
/* 593:581 */               checkStatus(req, interim);
/* 594:    */             }
/* 595:583 */             req.nextElement();
/* 596:    */           }
/* 597:    */           else
/* 598:    */           {
/* 599:585 */             makeKey(req);
/* 600:    */           }
/* 601:588 */           synchronized (this)
/* 602:    */           {
/* 603:589 */             response.received = false;
/* 604:590 */             resp.isReceived = false;
/* 605:    */             try
/* 606:    */             {
/* 607:592 */               this.response_map.put(req, resp);
/* 608:    */               do
/* 609:    */               {
/* 610:599 */                 doSend0(req);
/* 611:600 */               } while ((req.hasMoreElements()) && (req.nextElement() != null));
/* 612:606 */               long timeout = SmbConstants.RESPONSE_TIMEOUT;
/* 613:607 */               resp.expiration = (System.currentTimeMillis() + timeout);
/* 614:608 */               while (resp.hasMoreElements())
/* 615:    */               {
/* 616:609 */                 wait(timeout);
/* 617:610 */                 timeout = resp.expiration - System.currentTimeMillis();
/* 618:611 */                 if (timeout <= 0L) {
/* 619:612 */                   throw new TransportException(this + " timedout waiting for response to " + req);
/* 620:    */                 }
/* 621:    */               }
/* 622:617 */               if (response.errorCode != 0) {
/* 623:618 */                 checkStatus(req, resp);
/* 624:    */               }
/* 625:    */             }
/* 626:    */             catch (InterruptedException ie)
/* 627:    */             {
/* 628:621 */               throw new TransportException(ie);
/* 629:    */             }
/* 630:    */             finally
/* 631:    */             {
/* 632:623 */               this.response_map.remove(req);
/* 633:    */             }
/* 634:    */           }
/* 635:    */         }
/* 636:    */         finally
/* 637:    */         {
/* 638:627 */           BufferCache.releaseBuffer(req.txn_buf);
/* 639:628 */           BufferCache.releaseBuffer(resp.txn_buf);
/* 640:    */         }
/* 641:    */       }
/* 642:    */       else
/* 643:    */       {
/* 644:632 */         response.command = request.command;
/* 645:633 */         super.sendrecv(request, response, SmbConstants.RESPONSE_TIMEOUT);
/* 646:    */       }
/* 647:    */     }
/* 648:    */     catch (SmbException se)
/* 649:    */     {
/* 650:636 */       throw se;
/* 651:    */     }
/* 652:    */     catch (IOException ioe)
/* 653:    */     {
/* 654:638 */       throw new SmbException(ioe.getMessage(), ioe);
/* 655:    */     }
/* 656:641 */     checkStatus(request, response);
/* 657:    */   }
/* 658:    */   
/* 659:    */   public String toString()
/* 660:    */   {
/* 661:644 */     return super.toString() + "[" + this.address + ":" + this.port + "]";
/* 662:    */   }
/* 663:    */   
/* 664:    */   void dfsPathSplit(String path, String[] result)
/* 665:    */   {
/* 666:658 */     int ri = 0;int rlast = result.length - 1;
/* 667:659 */     int i = 0;int b = 0;int len = path.length();
/* 668:    */     do
/* 669:    */     {
/* 670:662 */       if (ri == rlast)
/* 671:    */       {
/* 672:663 */         result[rlast] = path.substring(b);
/* 673:664 */         return;
/* 674:    */       }
/* 675:666 */       if ((i == len) || (path.charAt(i) == '\\'))
/* 676:    */       {
/* 677:667 */         result[(ri++)] = path.substring(b, i);
/* 678:668 */         b = i + 1;
/* 679:    */       }
/* 680:670 */     } while (i++ < len);
/* 681:672 */     while (ri < result.length) {
/* 682:673 */       result[(ri++)] = "";
/* 683:    */     }
/* 684:    */   }
/* 685:    */   
/* 686:    */   DfsReferral getDfsReferrals(NtlmPasswordAuthentication auth, String path, int rn)
/* 687:    */     throws SmbException
/* 688:    */   {
/* 689:679 */     SmbTree ipc = getSmbSession(auth).getSmbTree("IPC$", null);
/* 690:680 */     Trans2GetDfsReferralResponse resp = new Trans2GetDfsReferralResponse();
/* 691:681 */     ipc.send(new Trans2GetDfsReferral(path), resp);
/* 692:683 */     if (resp.numReferrals == 0) {
/* 693:684 */       return null;
/* 694:    */     }
/* 695:685 */     if ((rn == 0) || (resp.numReferrals < rn)) {
/* 696:686 */       rn = resp.numReferrals;
/* 697:    */     }
/* 698:689 */     DfsReferral dr = new DfsReferral();
/* 699:    */     
/* 700:691 */     String[] arr = new String[4];
/* 701:692 */     long expiration = System.currentTimeMillis() + Dfs.TTL * 1000L;
/* 702:    */     
/* 703:694 */     int di = 0;
/* 704:    */     for (;;)
/* 705:    */     {
/* 706:698 */       dr.resolveHashes = auth.hashesExternal;
/* 707:699 */       dr.ttl = resp.referrals[di].ttl;
/* 708:700 */       dr.expiration = expiration;
/* 709:701 */       if (path.equals(""))
/* 710:    */       {
/* 711:702 */         dr.server = resp.referrals[di].path.substring(1).toLowerCase();
/* 712:    */       }
/* 713:    */       else
/* 714:    */       {
/* 715:704 */         dfsPathSplit(resp.referrals[di].node, arr);
/* 716:705 */         dr.server = arr[1];
/* 717:706 */         dr.share = arr[2];
/* 718:707 */         dr.path = arr[3];
/* 719:    */       }
/* 720:709 */       dr.pathConsumed = resp.pathConsumed;
/* 721:    */       
/* 722:711 */       di++;
/* 723:712 */       if (di == rn) {
/* 724:    */         break;
/* 725:    */       }
/* 726:715 */       dr.append(new DfsReferral());
/* 727:716 */       dr = dr.next;
/* 728:    */     }
/* 729:719 */     return dr.next;
/* 730:    */   }
/* 731:    */   
/* 732:    */   DfsReferral[] __getDfsReferrals(NtlmPasswordAuthentication auth, String path, int rn)
/* 733:    */     throws SmbException
/* 734:    */   {
/* 735:724 */     SmbTree ipc = getSmbSession(auth).getSmbTree("IPC$", null);
/* 736:725 */     Trans2GetDfsReferralResponse resp = new Trans2GetDfsReferralResponse();
/* 737:726 */     ipc.send(new Trans2GetDfsReferral(path), resp);
/* 738:728 */     if ((rn == 0) || (resp.numReferrals < rn)) {
/* 739:729 */       rn = resp.numReferrals;
/* 740:    */     }
/* 741:732 */     DfsReferral[] drs = new DfsReferral[rn];
/* 742:733 */     String[] arr = new String[4];
/* 743:734 */     long expiration = System.currentTimeMillis() + Dfs.TTL * 1000L;
/* 744:736 */     for (int di = 0; di < drs.length; di++)
/* 745:    */     {
/* 746:737 */       DfsReferral dr = new DfsReferral();
/* 747:    */       
/* 748:    */ 
/* 749:740 */       dr.resolveHashes = auth.hashesExternal;
/* 750:741 */       dr.ttl = resp.referrals[di].ttl;
/* 751:742 */       dr.expiration = expiration;
/* 752:743 */       if (path.equals(""))
/* 753:    */       {
/* 754:744 */         dr.server = resp.referrals[di].path.substring(1).toLowerCase();
/* 755:    */       }
/* 756:    */       else
/* 757:    */       {
/* 758:746 */         dfsPathSplit(resp.referrals[di].node, arr);
/* 759:747 */         dr.server = arr[1];
/* 760:748 */         dr.share = arr[2];
/* 761:749 */         dr.path = arr[3];
/* 762:    */       }
/* 763:751 */       dr.pathConsumed = resp.pathConsumed;
/* 764:752 */       drs[di] = dr;
/* 765:    */     }
/* 766:755 */     return drs;
/* 767:    */   }
/* 768:    */   
/* 769:    */   class ServerData
/* 770:    */   {
/* 771:    */     byte flags;
/* 772:    */     int flags2;
/* 773:    */     int maxMpxCount;
/* 774:    */     int maxBufferSize;
/* 775:    */     int sessionKey;
/* 776:    */     int capabilities;
/* 777:    */     String oemDomainName;
/* 778:    */     int securityMode;
/* 779:    */     int security;
/* 780:    */     boolean encryptedPasswords;
/* 781:    */     boolean signaturesEnabled;
/* 782:    */     boolean signaturesRequired;
/* 783:    */     int maxNumberVcs;
/* 784:    */     int maxRawSize;
/* 785:    */     long serverTime;
/* 786:    */     int serverTimeZone;
/* 787:    */     int encryptionKeyLength;
/* 788:    */     byte[] encryptionKey;
/* 789:    */     byte[] guid;
/* 790:    */     
/* 791:    */     ServerData() {}
/* 792:    */   }
/* 793:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbTransport
 * JD-Core Version:    0.7.0.1
 */