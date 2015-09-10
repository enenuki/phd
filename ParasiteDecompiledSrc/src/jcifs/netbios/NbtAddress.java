/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import java.net.UnknownHostException;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import jcifs.Config;
/*   7:    */ import jcifs.util.Hexdump;
/*   8:    */ 
/*   9:    */ public final class NbtAddress
/*  10:    */ {
/*  11:    */   static final String ANY_HOSTS_NAME = "";
/*  12:    */   public static final String MASTER_BROWSER_NAME = "\001\002__MSBROWSE__\002";
/*  13:    */   public static final String SMBSERVER_NAME = "*SMBSERVER     ";
/*  14:    */   public static final int B_NODE = 0;
/*  15:    */   public static final int P_NODE = 1;
/*  16:    */   public static final int M_NODE = 2;
/*  17:    */   public static final int H_NODE = 3;
/*  18:133 */   static final InetAddress[] NBNS = Config.getInetAddressArray("jcifs.netbios.wins", ",", new InetAddress[0]);
/*  19:140 */   private static final NameServiceClient CLIENT = new NameServiceClient();
/*  20:    */   private static final int DEFAULT_CACHE_POLICY = 30;
/*  21:143 */   private static final int CACHE_POLICY = Config.getInt("jcifs.netbios.cachePolicy", 30);
/*  22:    */   private static final int FOREVER = -1;
/*  23:145 */   private static int nbnsIndex = 0;
/*  24:147 */   private static final HashMap ADDRESS_CACHE = new HashMap();
/*  25:148 */   private static final HashMap LOOKUP_TABLE = new HashMap();
/*  26:150 */   static final Name UNKNOWN_NAME = new Name("0.0.0.0", 0, null);
/*  27:151 */   static final NbtAddress UNKNOWN_ADDRESS = new NbtAddress(UNKNOWN_NAME, 0, false, 0);
/*  28:152 */   static final byte[] UNKNOWN_MAC_ADDRESS = { 0, 0, 0, 0, 0, 0 };
/*  29:    */   static NbtAddress localhost;
/*  30:    */   Name hostName;
/*  31:    */   int address;
/*  32:    */   int nodeType;
/*  33:    */   boolean groupName;
/*  34:    */   boolean isBeingDeleted;
/*  35:    */   boolean isInConflict;
/*  36:    */   boolean isActive;
/*  37:    */   boolean isPermanent;
/*  38:    */   boolean isDataFromNodeStatus;
/*  39:    */   byte[] macAddress;
/*  40:    */   String calledName;
/*  41:    */   
/*  42:    */   static final class CacheEntry
/*  43:    */   {
/*  44:    */     Name hostName;
/*  45:    */     NbtAddress address;
/*  46:    */     long expiration;
/*  47:    */     
/*  48:    */     CacheEntry(Name hostName, NbtAddress address, long expiration)
/*  49:    */     {
/*  50:163 */       this.hostName = hostName;
/*  51:164 */       this.address = address;
/*  52:165 */       this.expiration = expiration;
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   static
/*  57:    */   {
/*  58:179 */     ADDRESS_CACHE.put(UNKNOWN_NAME, new CacheEntry(UNKNOWN_NAME, UNKNOWN_ADDRESS, -1L));
/*  59:    */     
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:184 */     InetAddress localInetAddress = CLIENT.laddr;
/*  64:185 */     if (localInetAddress == null) {
/*  65:    */       try
/*  66:    */       {
/*  67:187 */         localInetAddress = InetAddress.getLocalHost();
/*  68:    */       }
/*  69:    */       catch (UnknownHostException uhe)
/*  70:    */       {
/*  71:    */         try
/*  72:    */         {
/*  73:195 */           localInetAddress = InetAddress.getByName("127.0.0.1");
/*  74:    */         }
/*  75:    */         catch (UnknownHostException ignored) {}
/*  76:    */       }
/*  77:    */     }
/*  78:206 */     String localHostname = Config.getProperty("jcifs.netbios.hostname", null);
/*  79:207 */     if ((localHostname == null) || (localHostname.length() == 0))
/*  80:    */     {
/*  81:208 */       byte[] addr = localInetAddress.getAddress();
/*  82:209 */       localHostname = "JCIFS" + (addr[2] & 0xFF) + "_" + (addr[3] & 0xFF) + "_" + Hexdump.toHexString((int)(Math.random() * 255.0D), 2);
/*  83:    */     }
/*  84:219 */     Name localName = new Name(localHostname, 0, Config.getProperty("jcifs.netbios.scope", null));
/*  85:    */     
/*  86:221 */     localhost = new NbtAddress(localName, localInetAddress.hashCode(), false, 0, false, false, true, false, UNKNOWN_MAC_ADDRESS);
/*  87:    */     
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:227 */     cacheAddress(localName, localhost, -1L);
/*  93:    */   }
/*  94:    */   
/*  95:    */   static void cacheAddress(Name hostName, NbtAddress addr)
/*  96:    */   {
/*  97:231 */     if (CACHE_POLICY == 0) {
/*  98:232 */       return;
/*  99:    */     }
/* 100:234 */     long expiration = -1L;
/* 101:235 */     if (CACHE_POLICY != -1) {
/* 102:236 */       expiration = System.currentTimeMillis() + CACHE_POLICY * 1000;
/* 103:    */     }
/* 104:238 */     cacheAddress(hostName, addr, expiration);
/* 105:    */   }
/* 106:    */   
/* 107:    */   static void cacheAddress(Name hostName, NbtAddress addr, long expiration)
/* 108:    */   {
/* 109:241 */     if (CACHE_POLICY == 0) {
/* 110:242 */       return;
/* 111:    */     }
/* 112:244 */     synchronized (ADDRESS_CACHE)
/* 113:    */     {
/* 114:245 */       CacheEntry entry = (CacheEntry)ADDRESS_CACHE.get(hostName);
/* 115:246 */       if (entry == null)
/* 116:    */       {
/* 117:247 */         entry = new CacheEntry(hostName, addr, expiration);
/* 118:248 */         ADDRESS_CACHE.put(hostName, entry);
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:250 */         entry.address = addr;
/* 123:251 */         entry.expiration = expiration;
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   static void cacheAddressArray(NbtAddress[] addrs)
/* 129:    */   {
/* 130:256 */     if (CACHE_POLICY == 0) {
/* 131:257 */       return;
/* 132:    */     }
/* 133:259 */     long expiration = -1L;
/* 134:260 */     if (CACHE_POLICY != -1) {
/* 135:261 */       expiration = System.currentTimeMillis() + CACHE_POLICY * 1000;
/* 136:    */     }
/* 137:263 */     synchronized (ADDRESS_CACHE)
/* 138:    */     {
/* 139:264 */       for (int i = 0; i < addrs.length; i++)
/* 140:    */       {
/* 141:265 */         CacheEntry entry = (CacheEntry)ADDRESS_CACHE.get(addrs[i].hostName);
/* 142:266 */         if (entry == null)
/* 143:    */         {
/* 144:267 */           entry = new CacheEntry(addrs[i].hostName, addrs[i], expiration);
/* 145:268 */           ADDRESS_CACHE.put(addrs[i].hostName, entry);
/* 146:    */         }
/* 147:    */         else
/* 148:    */         {
/* 149:270 */           entry.address = addrs[i];
/* 150:271 */           entry.expiration = expiration;
/* 151:    */         }
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   static NbtAddress getCachedAddress(Name hostName)
/* 157:    */   {
/* 158:277 */     if (CACHE_POLICY == 0) {
/* 159:278 */       return null;
/* 160:    */     }
/* 161:280 */     synchronized (ADDRESS_CACHE)
/* 162:    */     {
/* 163:281 */       CacheEntry entry = (CacheEntry)ADDRESS_CACHE.get(hostName);
/* 164:282 */       if ((entry != null) && (entry.expiration < System.currentTimeMillis()) && (entry.expiration >= 0L)) {
/* 165:284 */         entry = null;
/* 166:    */       }
/* 167:286 */       return entry != null ? entry.address : null;
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   static NbtAddress doNameQuery(Name name, InetAddress svr)
/* 172:    */     throws UnknownHostException
/* 173:    */   {
/* 174:294 */     if ((name.hexCode == 29) && (svr == null)) {
/* 175:295 */       svr = CLIENT.baddr;
/* 176:    */     }
/* 177:297 */     name.srcHashCode = (svr != null ? svr.hashCode() : 0);
/* 178:298 */     NbtAddress addr = getCachedAddress(name);
/* 179:300 */     if (addr == null) {
/* 180:305 */       if ((addr = (NbtAddress)checkLookupTable(name)) == null) {
/* 181:    */         try
/* 182:    */         {
/* 183:307 */           addr = CLIENT.getByName(name, svr);
/* 184:    */         }
/* 185:    */         catch (UnknownHostException uhe)
/* 186:    */         {
/* 187:309 */           addr = UNKNOWN_ADDRESS;
/* 188:    */         }
/* 189:    */         finally
/* 190:    */         {
/* 191:311 */           cacheAddress(name, addr);
/* 192:312 */           updateLookupTable(name);
/* 193:    */         }
/* 194:    */       }
/* 195:    */     }
/* 196:316 */     if (addr == UNKNOWN_ADDRESS) {
/* 197:317 */       throw new UnknownHostException(name.toString());
/* 198:    */     }
/* 199:319 */     return addr;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private static Object checkLookupTable(Name name)
/* 203:    */   {
/* 204:325 */     synchronized (LOOKUP_TABLE)
/* 205:    */     {
/* 206:326 */       if (!LOOKUP_TABLE.containsKey(name))
/* 207:    */       {
/* 208:327 */         LOOKUP_TABLE.put(name, name);
/* 209:328 */         return null;
/* 210:    */       }
/* 211:330 */       while (LOOKUP_TABLE.containsKey(name)) {
/* 212:    */         try
/* 213:    */         {
/* 214:332 */           LOOKUP_TABLE.wait();
/* 215:    */         }
/* 216:    */         catch (InterruptedException e) {}
/* 217:    */       }
/* 218:    */     }
/* 219:337 */     Object obj = getCachedAddress(name);
/* 220:338 */     if (obj == null) {
/* 221:339 */       synchronized (LOOKUP_TABLE)
/* 222:    */       {
/* 223:340 */         LOOKUP_TABLE.put(name, name);
/* 224:    */       }
/* 225:    */     }
/* 226:344 */     return obj;
/* 227:    */   }
/* 228:    */   
/* 229:    */   private static void updateLookupTable(Name name)
/* 230:    */   {
/* 231:347 */     synchronized (LOOKUP_TABLE)
/* 232:    */     {
/* 233:348 */       LOOKUP_TABLE.remove(name);
/* 234:349 */       LOOKUP_TABLE.notifyAll();
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   public static NbtAddress getLocalHost()
/* 239:    */     throws UnknownHostException
/* 240:    */   {
/* 241:361 */     return localhost;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public static Name getLocalName()
/* 245:    */   {
/* 246:364 */     return localhost.hostName;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public static NbtAddress getByName(String host)
/* 250:    */     throws UnknownHostException
/* 251:    */   {
/* 252:379 */     return getByName(host, 0, null);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public static NbtAddress getByName(String host, int type, String scope)
/* 256:    */     throws UnknownHostException
/* 257:    */   {
/* 258:403 */     return getByName(host, type, scope, null);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public static NbtAddress getByName(String host, int type, String scope, InetAddress svr)
/* 262:    */     throws UnknownHostException
/* 263:    */   {
/* 264:418 */     if ((host == null) || (host.length() == 0)) {
/* 265:419 */       return getLocalHost();
/* 266:    */     }
/* 267:421 */     if (!Character.isDigit(host.charAt(0))) {
/* 268:422 */       return doNameQuery(new Name(host, type, scope), svr);
/* 269:    */     }
/* 270:424 */     int IP = 0;
/* 271:425 */     int hitDots = 0;
/* 272:426 */     char[] data = host.toCharArray();
/* 273:428 */     for (int i = 0; i < data.length; i++)
/* 274:    */     {
/* 275:429 */       char c = data[i];
/* 276:430 */       if ((c < '0') || (c > '9')) {
/* 277:431 */         return doNameQuery(new Name(host, type, scope), svr);
/* 278:    */       }
/* 279:433 */       int b = 0;
/* 280:434 */       while (c != '.')
/* 281:    */       {
/* 282:435 */         if ((c < '0') || (c > '9')) {
/* 283:436 */           return doNameQuery(new Name(host, type, scope), svr);
/* 284:    */         }
/* 285:438 */         b = b * 10 + c - 48;
/* 286:    */         
/* 287:440 */         i++;
/* 288:440 */         if (i >= data.length) {
/* 289:    */           break;
/* 290:    */         }
/* 291:443 */         c = data[i];
/* 292:    */       }
/* 293:445 */       if (b > 255) {
/* 294:446 */         return doNameQuery(new Name(host, type, scope), svr);
/* 295:    */       }
/* 296:448 */       IP = (IP << 8) + b;
/* 297:449 */       hitDots++;
/* 298:    */     }
/* 299:451 */     if ((hitDots != 4) || (host.endsWith("."))) {
/* 300:452 */       return doNameQuery(new Name(host, type, scope), svr);
/* 301:    */     }
/* 302:454 */     return new NbtAddress(UNKNOWN_NAME, IP, false, 0);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static NbtAddress[] getAllByName(String host, int type, String scope, InetAddress svr)
/* 306:    */     throws UnknownHostException
/* 307:    */   {
/* 308:463 */     return CLIENT.getAllByName(new Name(host, type, scope), svr);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public static NbtAddress[] getAllByAddress(String host)
/* 312:    */     throws UnknownHostException
/* 313:    */   {
/* 314:479 */     return getAllByAddress(getByName(host, 0, null));
/* 315:    */   }
/* 316:    */   
/* 317:    */   public static NbtAddress[] getAllByAddress(String host, int type, String scope)
/* 318:    */     throws UnknownHostException
/* 319:    */   {
/* 320:502 */     return getAllByAddress(getByName(host, type, scope));
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static NbtAddress[] getAllByAddress(NbtAddress addr)
/* 324:    */     throws UnknownHostException
/* 325:    */   {
/* 326:    */     try
/* 327:    */     {
/* 328:519 */       NbtAddress[] addrs = CLIENT.getNodeStatus(addr);
/* 329:520 */       cacheAddressArray(addrs);
/* 330:521 */       return addrs;
/* 331:    */     }
/* 332:    */     catch (UnknownHostException uhe)
/* 333:    */     {
/* 334:523 */       throw new UnknownHostException("no name with type 0x" + Hexdump.toHexString(addr.hostName.hexCode, 2) + ((addr.hostName.scope == null) || (addr.hostName.scope.length() == 0) ? " with no scope" : new StringBuffer().append(" with scope ").append(addr.hostName.scope).toString()) + " for host " + addr.getHostAddress());
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   public static InetAddress getWINSAddress()
/* 339:    */   {
/* 340:533 */     return NBNS.length == 0 ? null : NBNS[nbnsIndex];
/* 341:    */   }
/* 342:    */   
/* 343:    */   public static boolean isWINS(InetAddress svr)
/* 344:    */   {
/* 345:536 */     for (int i = 0; (svr != null) && (i < NBNS.length); i++) {
/* 346:537 */       if (svr.hashCode() == NBNS[i].hashCode()) {
/* 347:538 */         return true;
/* 348:    */       }
/* 349:    */     }
/* 350:541 */     return false;
/* 351:    */   }
/* 352:    */   
/* 353:    */   static InetAddress switchWINS()
/* 354:    */   {
/* 355:544 */     nbnsIndex = nbnsIndex + 1 < NBNS.length ? nbnsIndex + 1 : 0;
/* 356:545 */     return NBNS.length == 0 ? null : NBNS[nbnsIndex];
/* 357:    */   }
/* 358:    */   
/* 359:    */   NbtAddress(Name hostName, int address, boolean groupName, int nodeType)
/* 360:    */   {
/* 361:560 */     this.hostName = hostName;
/* 362:561 */     this.address = address;
/* 363:562 */     this.groupName = groupName;
/* 364:563 */     this.nodeType = nodeType;
/* 365:    */   }
/* 366:    */   
/* 367:    */   NbtAddress(Name hostName, int address, boolean groupName, int nodeType, boolean isBeingDeleted, boolean isInConflict, boolean isActive, boolean isPermanent, byte[] macAddress)
/* 368:    */   {
/* 369:580 */     this.hostName = hostName;
/* 370:581 */     this.address = address;
/* 371:582 */     this.groupName = groupName;
/* 372:583 */     this.nodeType = nodeType;
/* 373:584 */     this.isBeingDeleted = isBeingDeleted;
/* 374:585 */     this.isInConflict = isInConflict;
/* 375:586 */     this.isActive = isActive;
/* 376:587 */     this.isPermanent = isPermanent;
/* 377:588 */     this.macAddress = macAddress;
/* 378:589 */     this.isDataFromNodeStatus = true;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public String firstCalledName()
/* 382:    */   {
/* 383:598 */     this.calledName = this.hostName.name;
/* 384:600 */     if (Character.isDigit(this.calledName.charAt(0)))
/* 385:    */     {
/* 386:    */       int dots;
/* 387:604 */       int i = dots = 0;
/* 388:605 */       int len = this.calledName.length();
/* 389:606 */       char[] data = this.calledName.toCharArray();
/* 390:607 */       while ((i < len) && (Character.isDigit(data[(i++)])))
/* 391:    */       {
/* 392:608 */         if ((i == len) && (dots == 3))
/* 393:    */         {
/* 394:610 */           this.calledName = "*SMBSERVER     ";
/* 395:611 */           break;
/* 396:    */         }
/* 397:613 */         if ((i < len) && (data[i] == '.'))
/* 398:    */         {
/* 399:614 */           dots++;
/* 400:615 */           i++;
/* 401:    */         }
/* 402:    */       }
/* 403:    */     }
/* 404:    */     else
/* 405:    */     {
/* 406:619 */       switch (this.hostName.hexCode)
/* 407:    */       {
/* 408:    */       case 27: 
/* 409:    */       case 28: 
/* 410:    */       case 29: 
/* 411:623 */         this.calledName = "*SMBSERVER     ";
/* 412:    */       }
/* 413:    */     }
/* 414:627 */     return this.calledName;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public String nextCalledName()
/* 418:    */   {
/* 419:631 */     if (this.calledName == this.hostName.name) {
/* 420:632 */       this.calledName = "*SMBSERVER     ";
/* 421:633 */     } else if (this.calledName == "*SMBSERVER     ") {
/* 422:    */       try
/* 423:    */       {
/* 424:637 */         NbtAddress[] addrs = CLIENT.getNodeStatus(this);
/* 425:638 */         if (this.hostName.hexCode == 29)
/* 426:    */         {
/* 427:639 */           for (int i = 0; i < addrs.length; i++) {
/* 428:640 */             if (addrs[i].hostName.hexCode == 32) {
/* 429:641 */               return addrs[i].hostName.name;
/* 430:    */             }
/* 431:    */           }
/* 432:644 */           return null;
/* 433:    */         }
/* 434:645 */         if (this.isDataFromNodeStatus)
/* 435:    */         {
/* 436:649 */           this.calledName = null;
/* 437:650 */           return this.hostName.name;
/* 438:    */         }
/* 439:    */       }
/* 440:    */       catch (UnknownHostException uhe)
/* 441:    */       {
/* 442:653 */         this.calledName = null;
/* 443:    */       }
/* 444:    */     } else {
/* 445:656 */       this.calledName = null;
/* 446:    */     }
/* 447:659 */     return this.calledName;
/* 448:    */   }
/* 449:    */   
/* 450:    */   void checkData()
/* 451:    */     throws UnknownHostException
/* 452:    */   {
/* 453:690 */     if (this.hostName == UNKNOWN_NAME) {
/* 454:691 */       getAllByAddress(this);
/* 455:    */     }
/* 456:    */   }
/* 457:    */   
/* 458:    */   void checkNodeStatusData()
/* 459:    */     throws UnknownHostException
/* 460:    */   {
/* 461:695 */     if (!this.isDataFromNodeStatus) {
/* 462:696 */       getAllByAddress(this);
/* 463:    */     }
/* 464:    */   }
/* 465:    */   
/* 466:    */   public boolean isGroupAddress()
/* 467:    */     throws UnknownHostException
/* 468:    */   {
/* 469:708 */     checkData();
/* 470:709 */     return this.groupName;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public int getNodeType()
/* 474:    */     throws UnknownHostException
/* 475:    */   {
/* 476:722 */     checkData();
/* 477:723 */     return this.nodeType;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public boolean isBeingDeleted()
/* 481:    */     throws UnknownHostException
/* 482:    */   {
/* 483:733 */     checkNodeStatusData();
/* 484:734 */     return this.isBeingDeleted;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public boolean isInConflict()
/* 488:    */     throws UnknownHostException
/* 489:    */   {
/* 490:744 */     checkNodeStatusData();
/* 491:745 */     return this.isInConflict;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public boolean isActive()
/* 495:    */     throws UnknownHostException
/* 496:    */   {
/* 497:755 */     checkNodeStatusData();
/* 498:756 */     return this.isActive;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public boolean isPermanent()
/* 502:    */     throws UnknownHostException
/* 503:    */   {
/* 504:766 */     checkNodeStatusData();
/* 505:767 */     return this.isPermanent;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public byte[] getMacAddress()
/* 509:    */     throws UnknownHostException
/* 510:    */   {
/* 511:779 */     checkNodeStatusData();
/* 512:780 */     return this.macAddress;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public String getHostName()
/* 516:    */   {
/* 517:797 */     if (this.hostName == UNKNOWN_NAME) {
/* 518:798 */       return getHostAddress();
/* 519:    */     }
/* 520:800 */     return this.hostName.name;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public byte[] getAddress()
/* 524:    */   {
/* 525:812 */     byte[] addr = new byte[4];
/* 526:    */     
/* 527:814 */     addr[0] = ((byte)(this.address >>> 24 & 0xFF));
/* 528:815 */     addr[1] = ((byte)(this.address >>> 16 & 0xFF));
/* 529:816 */     addr[2] = ((byte)(this.address >>> 8 & 0xFF));
/* 530:817 */     addr[3] = ((byte)(this.address & 0xFF));
/* 531:818 */     return addr;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public InetAddress getInetAddress()
/* 535:    */     throws UnknownHostException
/* 536:    */   {
/* 537:828 */     return InetAddress.getByName(getHostAddress());
/* 538:    */   }
/* 539:    */   
/* 540:    */   public String getHostAddress()
/* 541:    */   {
/* 542:836 */     return (this.address >>> 24 & 0xFF) + "." + (this.address >>> 16 & 0xFF) + "." + (this.address >>> 8 & 0xFF) + "." + (this.address >>> 0 & 0xFF);
/* 543:    */   }
/* 544:    */   
/* 545:    */   public int getNameType()
/* 546:    */   {
/* 547:847 */     return this.hostName.hexCode;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public int hashCode()
/* 551:    */   {
/* 552:858 */     return this.address;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public boolean equals(Object obj)
/* 556:    */   {
/* 557:868 */     return (obj != null) && ((obj instanceof NbtAddress)) && (((NbtAddress)obj).address == this.address);
/* 558:    */   }
/* 559:    */   
/* 560:    */   public String toString()
/* 561:    */   {
/* 562:877 */     return this.hostName.toString() + "/" + getHostAddress();
/* 563:    */   }
/* 564:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NbtAddress
 * JD-Core Version:    0.7.0.1
 */