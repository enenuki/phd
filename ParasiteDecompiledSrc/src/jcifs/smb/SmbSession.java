/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.UnknownHostException;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import java.util.Vector;
/*   8:    */ import jcifs.Config;
/*   9:    */ import jcifs.UniAddress;
/*  10:    */ import jcifs.netbios.NbtAddress;
/*  11:    */ import jcifs.util.LogStream;
/*  12:    */ 
/*  13:    */ public final class SmbSession
/*  14:    */ {
/*  15: 37 */   private static final String LOGON_SHARE = Config.getProperty("jcifs.smb.client.logonShare", null);
/*  16: 39 */   private static final int LOOKUP_RESP_LIMIT = Config.getInt("jcifs.netbios.lookupRespLimit", 3);
/*  17: 41 */   private static final String DOMAIN = Config.getProperty("jcifs.smb.client.domain", null);
/*  18: 43 */   private static final String USERNAME = Config.getProperty("jcifs.smb.client.username", null);
/*  19: 45 */   private static final int CACHE_POLICY = Config.getInt("jcifs.netbios.cachePolicy", 600) * 60;
/*  20: 48 */   static NbtAddress[] dc_list = null;
/*  21:    */   static long dc_list_expiration;
/*  22:    */   static int dc_list_counter;
/*  23:    */   int connectionState;
/*  24:    */   int uid;
/*  25:    */   Vector trees;
/*  26:    */   private UniAddress address;
/*  27:    */   private int port;
/*  28:    */   private int localPort;
/*  29:    */   private InetAddress localAddr;
/*  30:    */   
/*  31:    */   private static NtlmChallenge interrogate(NbtAddress addr)
/*  32:    */     throws SmbException
/*  33:    */   {
/*  34: 53 */     UniAddress dc = new UniAddress(addr);
/*  35: 54 */     SmbTransport trans = SmbTransport.getSmbTransport(dc, 0);
/*  36: 55 */     if (USERNAME == null)
/*  37:    */     {
/*  38: 56 */       trans.connect();
/*  39: 57 */       if (LogStream.level >= 3) {
/*  40: 58 */         SmbTransport.log.println("Default credentials (jcifs.smb.client.username/password) not specified. SMB signing may not work propertly.  Skipping DC interrogation.");
/*  41:    */       }
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45: 63 */       SmbSession ssn = trans.getSmbSession(NtlmPasswordAuthentication.DEFAULT);
/*  46: 64 */       ssn.getSmbTree(LOGON_SHARE, null).treeConnect(null, null);
/*  47:    */     }
/*  48: 66 */     return new NtlmChallenge(trans.server.encryptionKey, dc);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static NtlmChallenge getChallengeForDomain()
/*  52:    */     throws SmbException, UnknownHostException
/*  53:    */   {
/*  54: 70 */     if (DOMAIN == null) {
/*  55: 71 */       throw new SmbException("A domain was not specified");
/*  56:    */     }
/*  57: 73 */     synchronized (DOMAIN)
/*  58:    */     {
/*  59: 74 */       long now = System.currentTimeMillis();
/*  60: 75 */       int retry = 1;
/*  61:    */       do
/*  62:    */       {
/*  63: 78 */         if (dc_list_expiration < now)
/*  64:    */         {
/*  65: 79 */           NbtAddress[] list = NbtAddress.getAllByName(DOMAIN, 28, null, null);
/*  66: 80 */           dc_list_expiration = now + CACHE_POLICY * 1000L;
/*  67: 81 */           if ((list != null) && (list.length > 0))
/*  68:    */           {
/*  69: 82 */             dc_list = list;
/*  70:    */           }
/*  71:    */           else
/*  72:    */           {
/*  73: 84 */             dc_list_expiration = now + 900000L;
/*  74: 85 */             if (LogStream.level >= 2) {
/*  75: 86 */               SmbTransport.log.println("Failed to retrieve DC list from WINS");
/*  76:    */             }
/*  77:    */           }
/*  78:    */         }
/*  79: 91 */         int max = Math.min(dc_list.length, LOOKUP_RESP_LIMIT);
/*  80: 92 */         for (int j = 0; j < max; j++)
/*  81:    */         {
/*  82: 93 */           int i = dc_list_counter++ % max;
/*  83: 94 */           if (dc_list[i] != null) {
/*  84:    */             try
/*  85:    */             {
/*  86: 96 */               return interrogate(dc_list[i]);
/*  87:    */             }
/*  88:    */             catch (SmbException se)
/*  89:    */             {
/*  90: 98 */               if (LogStream.level >= 2)
/*  91:    */               {
/*  92: 99 */                 SmbTransport.log.println("Failed validate DC: " + dc_list[i]);
/*  93:100 */                 if (LogStream.level > 2) {
/*  94:101 */                   se.printStackTrace(SmbTransport.log);
/*  95:    */                 }
/*  96:    */               }
/*  97:104 */               dc_list[i] = null;
/*  98:    */             }
/*  99:    */           }
/* 100:    */         }
/* 101:110 */         dc_list_expiration = 0L;
/* 102:111 */       } while (retry-- > 0);
/* 103:113 */       dc_list_expiration = now + 900000L;
/* 104:    */     }
/* 105:116 */     throw new UnknownHostException("Failed to negotiate with a suitable domain controller for " + DOMAIN);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static byte[] getChallenge(UniAddress dc)
/* 109:    */     throws SmbException, UnknownHostException
/* 110:    */   {
/* 111:122 */     return getChallenge(dc, 0);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static byte[] getChallenge(UniAddress dc, int port)
/* 115:    */     throws SmbException, UnknownHostException
/* 116:    */   {
/* 117:127 */     SmbTransport trans = SmbTransport.getSmbTransport(dc, port);
/* 118:128 */     trans.connect();
/* 119:129 */     return trans.server.encryptionKey;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static void logon(UniAddress dc, NtlmPasswordAuthentication auth)
/* 123:    */     throws SmbException
/* 124:    */   {
/* 125:146 */     logon(dc, 0, auth);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static void logon(UniAddress dc, int port, NtlmPasswordAuthentication auth)
/* 129:    */     throws SmbException
/* 130:    */   {
/* 131:151 */     SmbTree tree = SmbTransport.getSmbTransport(dc, port).getSmbSession(auth).getSmbTree(LOGON_SHARE, null);
/* 132:152 */     if (LOGON_SHARE == null)
/* 133:    */     {
/* 134:153 */       tree.treeConnect(null, null);
/* 135:    */     }
/* 136:    */     else
/* 137:    */     {
/* 138:155 */       Trans2FindFirst2 req = new Trans2FindFirst2("\\", "*", 16);
/* 139:156 */       Trans2FindFirst2Response resp = new Trans2FindFirst2Response();
/* 140:157 */       tree.send(req, resp);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:174 */   SmbTransport transport = null;
/* 145:    */   NtlmPasswordAuthentication auth;
/* 146:    */   long expiration;
/* 147:177 */   String netbiosName = null;
/* 148:    */   
/* 149:    */   SmbSession(UniAddress address, int port, InetAddress localAddr, int localPort, NtlmPasswordAuthentication auth)
/* 150:    */   {
/* 151:182 */     this.address = address;
/* 152:183 */     this.port = port;
/* 153:184 */     this.localAddr = localAddr;
/* 154:185 */     this.localPort = localPort;
/* 155:186 */     this.auth = auth;
/* 156:187 */     this.trees = new Vector();
/* 157:188 */     this.connectionState = 0;
/* 158:    */   }
/* 159:    */   
/* 160:    */   synchronized SmbTree getSmbTree(String share, String service)
/* 161:    */   {
/* 162:194 */     if (share == null) {
/* 163:195 */       share = "IPC$";
/* 164:    */     }
/* 165:197 */     for (Enumeration e = this.trees.elements(); e.hasMoreElements();)
/* 166:    */     {
/* 167:198 */       SmbTree t = (SmbTree)e.nextElement();
/* 168:199 */       if (t.matches(share, service)) {
/* 169:200 */         return t;
/* 170:    */       }
/* 171:    */     }
/* 172:203 */     SmbTree t = new SmbTree(this, share, service);
/* 173:204 */     this.trees.addElement(t);
/* 174:205 */     return t;
/* 175:    */   }
/* 176:    */   
/* 177:    */   boolean matches(NtlmPasswordAuthentication auth)
/* 178:    */   {
/* 179:208 */     return (this.auth == auth) || (this.auth.equals(auth));
/* 180:    */   }
/* 181:    */   
/* 182:    */   synchronized SmbTransport transport()
/* 183:    */   {
/* 184:211 */     if (this.transport == null) {
/* 185:212 */       this.transport = SmbTransport.getSmbTransport(this.address, this.port, this.localAddr, this.localPort, null);
/* 186:    */     }
/* 187:214 */     return this.transport;
/* 188:    */   }
/* 189:    */   
/* 190:    */   void send(ServerMessageBlock request, ServerMessageBlock response)
/* 191:    */     throws SmbException
/* 192:    */   {
/* 193:218 */     synchronized (transport())
/* 194:    */     {
/* 195:219 */       if (response != null) {
/* 196:220 */         response.received = false;
/* 197:    */       }
/* 198:223 */       this.expiration = (System.currentTimeMillis() + SmbConstants.SO_TIMEOUT);
/* 199:224 */       sessionSetup(request, response);
/* 200:225 */       if ((response != null) && (response.received)) {
/* 201:226 */         return;
/* 202:    */       }
/* 203:229 */       if ((request instanceof SmbComTreeConnectAndX))
/* 204:    */       {
/* 205:230 */         SmbComTreeConnectAndX tcax = (SmbComTreeConnectAndX)request;
/* 206:231 */         if ((this.netbiosName != null) && (tcax.path.endsWith("\\IPC$"))) {
/* 207:237 */           tcax.path = ("\\\\" + this.netbiosName + "\\IPC$");
/* 208:    */         }
/* 209:    */       }
/* 210:241 */       request.uid = this.uid;
/* 211:242 */       request.auth = this.auth;
/* 212:    */       try
/* 213:    */       {
/* 214:244 */         this.transport.send(request, response);
/* 215:    */       }
/* 216:    */       catch (SmbException se)
/* 217:    */       {
/* 218:246 */         if ((request instanceof SmbComTreeConnectAndX)) {
/* 219:247 */           logoff(true);
/* 220:    */         }
/* 221:249 */         request.digest = null;
/* 222:250 */         throw se;
/* 223:    */       }
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   void sessionSetup(ServerMessageBlock andx, ServerMessageBlock andxResponse)
/* 228:    */     throws SmbException
/* 229:    */   {
/* 230:256 */     synchronized (transport())
/* 231:    */     {
/* 232:257 */       NtlmContext nctx = null;
/* 233:258 */       SmbException ex = null;
/* 234:    */       
/* 235:    */ 
/* 236:261 */       byte[] token = new byte[0];
/* 237:262 */       int state = 10;
/* 238:264 */       while (this.connectionState != 0)
/* 239:    */       {
/* 240:265 */         if ((this.connectionState == 2) || (this.connectionState == 3)) {
/* 241:266 */           return;
/* 242:    */         }
/* 243:    */         try
/* 244:    */         {
/* 245:268 */           this.transport.wait();
/* 246:    */         }
/* 247:    */         catch (InterruptedException ie)
/* 248:    */         {
/* 249:270 */           throw new SmbException(ie.getMessage(), ie);
/* 250:    */         }
/* 251:    */       }
/* 252:273 */       this.connectionState = 1;
/* 253:    */       try
/* 254:    */       {
/* 255:276 */         this.transport.connect();
/* 256:282 */         if (LogStream.level >= 4) {
/* 257:283 */           SmbTransport.log.println("sessionSetup: accountName=" + this.auth.username + ",primaryDomain=" + this.auth.domain);
/* 258:    */         }
/* 259:290 */         this.uid = 0;
/* 260:    */         do
/* 261:    */         {
/* 262:293 */           switch (state)
/* 263:    */           {
/* 264:    */           case 10: 
/* 265:295 */             if ((this.auth != NtlmPasswordAuthentication.ANONYMOUS) && (this.transport.hasCapability(-2147483648)))
/* 266:    */             {
/* 267:297 */               state = 20;
/* 268:    */             }
/* 269:    */             else
/* 270:    */             {
/* 271:301 */               SmbComSessionSetupAndX request = new SmbComSessionSetupAndX(this, andx, this.auth);
/* 272:302 */               SmbComSessionSetupAndXResponse response = new SmbComSessionSetupAndXResponse(andxResponse);
/* 273:308 */               if (this.transport.isSignatureSetupRequired(this.auth)) {
/* 274:309 */                 if ((this.auth.hashesExternal) && (NtlmPasswordAuthentication.DEFAULT_PASSWORD != ""))
/* 275:    */                 {
/* 276:312 */                   this.transport.getSmbSession(NtlmPasswordAuthentication.DEFAULT).getSmbTree(LOGON_SHARE, null).treeConnect(null, null);
/* 277:    */                 }
/* 278:    */                 else
/* 279:    */                 {
/* 280:314 */                   byte[] signingKey = this.auth.getSigningKey(this.transport.server.encryptionKey);
/* 281:315 */                   request.digest = new SigningDigest(signingKey, false);
/* 282:    */                 }
/* 283:    */               }
/* 284:319 */               request.auth = this.auth;
/* 285:    */               try
/* 286:    */               {
/* 287:322 */                 this.transport.send(request, response);
/* 288:    */               }
/* 289:    */               catch (SmbAuthException sae)
/* 290:    */               {
/* 291:324 */                 throw sae;
/* 292:    */               }
/* 293:    */               catch (SmbException se)
/* 294:    */               {
/* 295:326 */                 ex = se;
/* 296:    */               }
/* 297:329 */               if ((response.isLoggedInAsGuest) && (!"GUEST".equalsIgnoreCase(this.auth.username)) && (this.transport.server.security != 0)) {
/* 298:332 */                 throw new SmbAuthException(-1073741715);
/* 299:    */               }
/* 300:335 */               if (ex != null) {
/* 301:336 */                 throw ex;
/* 302:    */               }
/* 303:338 */               this.uid = response.uid;
/* 304:340 */               if (request.digest != null) {
/* 305:342 */                 this.transport.digest = request.digest;
/* 306:    */               }
/* 307:345 */               this.connectionState = 2;
/* 308:    */               
/* 309:347 */               state = 0;
/* 310:    */             }
/* 311:349 */             break;
/* 312:    */           case 20: 
/* 313:351 */             if (nctx == null)
/* 314:    */             {
/* 315:352 */               boolean doSigning = (this.transport.flags2 & 0x4) != 0;
/* 316:353 */               nctx = new NtlmContext(this.auth, doSigning);
/* 317:    */             }
/* 318:356 */             if (LogStream.level >= 4) {
/* 319:357 */               SmbTransport.log.println(nctx);
/* 320:    */             }
/* 321:359 */             if (nctx.isEstablished())
/* 322:    */             {
/* 323:361 */               this.netbiosName = nctx.getNetbiosName();
/* 324:    */               
/* 325:363 */               this.connectionState = 2;
/* 326:    */               
/* 327:365 */               state = 0;
/* 328:    */             }
/* 329:    */             else
/* 330:    */             {
/* 331:    */               try
/* 332:    */               {
/* 333:370 */                 token = nctx.initSecContext(token, 0, token.length);
/* 334:    */               }
/* 335:    */               catch (SmbException se)
/* 336:    */               {
/* 337:    */                 try
/* 338:    */                 {
/* 339:376 */                   this.transport.disconnect(true);
/* 340:    */                 }
/* 341:    */                 catch (IOException ioe) {}
/* 342:377 */                 this.uid = 0;
/* 343:378 */                 throw se;
/* 344:    */               }
/* 345:381 */               if (token != null)
/* 346:    */               {
/* 347:382 */                 SmbComSessionSetupAndX request = new SmbComSessionSetupAndX(this, null, token);
/* 348:383 */                 SmbComSessionSetupAndXResponse response = new SmbComSessionSetupAndXResponse(null);
/* 349:385 */                 if (this.transport.isSignatureSetupRequired(this.auth))
/* 350:    */                 {
/* 351:386 */                   byte[] signingKey = nctx.getSigningKey();
/* 352:387 */                   if (signingKey != null) {
/* 353:388 */                     request.digest = new SigningDigest(signingKey, true);
/* 354:    */                   }
/* 355:    */                 }
/* 356:391 */                 request.uid = this.uid;
/* 357:392 */                 this.uid = 0;
/* 358:    */                 try
/* 359:    */                 {
/* 360:395 */                   this.transport.send(request, response);
/* 361:    */                 }
/* 362:    */                 catch (SmbAuthException sae)
/* 363:    */                 {
/* 364:397 */                   throw sae;
/* 365:    */                 }
/* 366:    */                 catch (SmbException se)
/* 367:    */                 {
/* 368:399 */                   ex = se;
/* 369:    */                   try
/* 370:    */                   {
/* 371:406 */                     this.transport.disconnect(true);
/* 372:    */                   }
/* 373:    */                   catch (Exception e) {}
/* 374:    */                 }
/* 375:409 */                 if ((response.isLoggedInAsGuest) && (!"GUEST".equalsIgnoreCase(this.auth.username))) {
/* 376:411 */                   throw new SmbAuthException(-1073741715);
/* 377:    */                 }
/* 378:414 */                 if (ex != null) {
/* 379:415 */                   throw ex;
/* 380:    */                 }
/* 381:417 */                 this.uid = response.uid;
/* 382:419 */                 if (request.digest != null) {
/* 383:421 */                   this.transport.digest = request.digest;
/* 384:    */                 }
/* 385:424 */                 token = response.blob;
/* 386:    */               }
/* 387:    */             }
/* 388:    */             break;
/* 389:    */           default: 
/* 390:429 */             throw new SmbException("Unexpected session setup state: " + state);
/* 391:    */           }
/* 392:431 */         } while (state != 0);
/* 393:    */       }
/* 394:    */       catch (SmbException se)
/* 395:    */       {
/* 396:433 */         logoff(true);
/* 397:434 */         this.connectionState = 0;
/* 398:435 */         throw se;
/* 399:    */       }
/* 400:    */       finally
/* 401:    */       {
/* 402:437 */         this.transport.notifyAll();
/* 403:    */       }
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   void logoff(boolean inError)
/* 408:    */   {
/* 409:442 */     synchronized (transport())
/* 410:    */     {
/* 411:444 */       if (this.connectionState != 2) {
/* 412:445 */         return;
/* 413:    */       }
/* 414:446 */       this.connectionState = 3;
/* 415:    */       
/* 416:448 */       this.netbiosName = null;
/* 417:450 */       for (Enumeration e = this.trees.elements(); e.hasMoreElements();)
/* 418:    */       {
/* 419:451 */         SmbTree t = (SmbTree)e.nextElement();
/* 420:452 */         t.treeDisconnect(inError);
/* 421:    */       }
/* 422:455 */       if ((!inError) && (this.transport.server.security != 0))
/* 423:    */       {
/* 424:460 */         SmbComLogoffAndX request = new SmbComLogoffAndX(null);
/* 425:461 */         request.uid = this.uid;
/* 426:    */         try
/* 427:    */         {
/* 428:463 */           this.transport.send(request, null);
/* 429:    */         }
/* 430:    */         catch (SmbException se) {}
/* 431:466 */         this.uid = 0;
/* 432:    */       }
/* 433:469 */       this.connectionState = 0;
/* 434:470 */       this.transport.notifyAll();
/* 435:    */     }
/* 436:    */   }
/* 437:    */   
/* 438:    */   public String toString()
/* 439:    */   {
/* 440:474 */     return "SmbSession[accountName=" + this.auth.username + ",primaryDomain=" + this.auth.domain + ",uid=" + this.uid + ",connectionState=" + this.connectionState + "]";
/* 441:    */   }
/* 442:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbSession
 * JD-Core Version:    0.7.0.1
 */