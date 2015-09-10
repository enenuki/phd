/*   1:    */ package jcifs;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.UnknownHostException;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import jcifs.netbios.Lmhosts;
/*   8:    */ import jcifs.netbios.NbtAddress;
/*   9:    */ import jcifs.util.LogStream;
/*  10:    */ 
/*  11:    */ public class UniAddress
/*  12:    */ {
/*  13:    */   private static final int RESOLVER_WINS = 0;
/*  14:    */   private static final int RESOLVER_BCAST = 1;
/*  15:    */   private static final int RESOLVER_DNS = 2;
/*  16:    */   private static final int RESOLVER_LMHOSTS = 3;
/*  17:    */   private static int[] resolveOrder;
/*  18:    */   private static InetAddress baddr;
/*  19: 58 */   private static LogStream log = ;
/*  20:    */   Object addr;
/*  21:    */   String calledName;
/*  22:    */   
/*  23:    */   static
/*  24:    */   {
/*  25: 61 */     String ro = Config.getProperty("jcifs.resolveOrder");
/*  26: 62 */     InetAddress nbns = NbtAddress.getWINSAddress();
/*  27:    */     try
/*  28:    */     {
/*  29: 65 */       baddr = Config.getInetAddress("jcifs.netbios.baddr", InetAddress.getByName("255.255.255.255"));
/*  30:    */     }
/*  31:    */     catch (UnknownHostException uhe) {}
/*  32: 70 */     if ((ro == null) || (ro.length() == 0))
/*  33:    */     {
/*  34: 78 */       if (nbns == null)
/*  35:    */       {
/*  36: 79 */         resolveOrder = new int[3];
/*  37: 80 */         resolveOrder[0] = 3;
/*  38: 81 */         resolveOrder[1] = 2;
/*  39: 82 */         resolveOrder[2] = 1;
/*  40:    */       }
/*  41:    */       else
/*  42:    */       {
/*  43: 84 */         resolveOrder = new int[4];
/*  44: 85 */         resolveOrder[0] = 3;
/*  45: 86 */         resolveOrder[1] = 0;
/*  46: 87 */         resolveOrder[2] = 2;
/*  47: 88 */         resolveOrder[3] = 1;
/*  48:    */       }
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52: 91 */       int[] tmp = new int[4];
/*  53: 92 */       StringTokenizer st = new StringTokenizer(ro, ",");
/*  54: 93 */       int i = 0;
/*  55: 94 */       while (st.hasMoreTokens())
/*  56:    */       {
/*  57: 95 */         String s = st.nextToken().trim();
/*  58: 96 */         if (s.equalsIgnoreCase("LMHOSTS")) {
/*  59: 97 */           tmp[(i++)] = 3;
/*  60: 98 */         } else if (s.equalsIgnoreCase("WINS"))
/*  61:    */         {
/*  62: 99 */           if (nbns == null)
/*  63:    */           {
/*  64:100 */             if (LogStream.level > 1) {
/*  65:101 */               log.println("UniAddress resolveOrder specifies WINS however the jcifs.netbios.wins property has not been set");
/*  66:    */             }
/*  67:    */           }
/*  68:    */           else {
/*  69:106 */             tmp[(i++)] = 0;
/*  70:    */           }
/*  71:    */         }
/*  72:107 */         else if (s.equalsIgnoreCase("BCAST")) {
/*  73:108 */           tmp[(i++)] = 1;
/*  74:109 */         } else if (s.equalsIgnoreCase("DNS")) {
/*  75:110 */           tmp[(i++)] = 2;
/*  76:111 */         } else if (LogStream.level > 1) {
/*  77:112 */           log.println("unknown resolver method: " + s);
/*  78:    */         }
/*  79:    */       }
/*  80:115 */       resolveOrder = new int[i];
/*  81:116 */       System.arraycopy(tmp, 0, resolveOrder, 0, i);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   static class Sem
/*  86:    */   {
/*  87:    */     int count;
/*  88:    */     
/*  89:    */     Sem(int count)
/*  90:    */     {
/*  91:122 */       this.count = count;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   static class QueryThread
/*  96:    */     extends Thread
/*  97:    */   {
/*  98:    */     UniAddress.Sem sem;
/*  99:    */     String host;
/* 100:    */     String scope;
/* 101:    */     int type;
/* 102:132 */     NbtAddress ans = null;
/* 103:    */     InetAddress svr;
/* 104:    */     UnknownHostException uhe;
/* 105:    */     
/* 106:    */     QueryThread(UniAddress.Sem sem, String host, int type, String scope, InetAddress svr)
/* 107:    */     {
/* 108:138 */       super();
/* 109:139 */       this.sem = sem;
/* 110:140 */       this.host = host;
/* 111:141 */       this.type = type;
/* 112:142 */       this.scope = scope;
/* 113:143 */       this.svr = svr;
/* 114:    */     }
/* 115:    */     
/* 116:    */     public void run()
/* 117:    */     {
/* 118:    */       try
/* 119:    */       {
/* 120:147 */         this.ans = NbtAddress.getByName(this.host, this.type, this.scope, this.svr);
/* 121:    */       }
/* 122:    */       catch (UnknownHostException uhe)
/* 123:    */       {
/* 124:149 */         this.uhe = uhe;
/* 125:    */       }
/* 126:    */       catch (Exception ex)
/* 127:    */       {
/* 128:151 */         this.uhe = new UnknownHostException(ex.getMessage());
/* 129:    */       }
/* 130:    */       finally
/* 131:    */       {
/* 132:153 */         synchronized (this.sem)
/* 133:    */         {
/* 134:154 */           this.sem.count -= 1;
/* 135:155 */           this.sem.notify();
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   static NbtAddress lookupServerOrWorkgroup(String name, InetAddress svr)
/* 142:    */     throws UnknownHostException
/* 143:    */   {
/* 144:163 */     Sem sem = new Sem(2);
/* 145:164 */     int type = NbtAddress.isWINS(svr) ? 27 : 29;
/* 146:    */     
/* 147:166 */     QueryThread q1x = new QueryThread(sem, name, type, null, svr);
/* 148:167 */     QueryThread q20 = new QueryThread(sem, name, 32, null, svr);
/* 149:168 */     q1x.setDaemon(true);
/* 150:169 */     q20.setDaemon(true);
/* 151:    */     try
/* 152:    */     {
/* 153:171 */       synchronized (sem)
/* 154:    */       {
/* 155:172 */         q1x.start();
/* 156:173 */         q20.start();
/* 157:175 */         while ((sem.count > 0) && (q1x.ans == null) && (q20.ans == null)) {
/* 158:176 */           sem.wait();
/* 159:    */         }
/* 160:    */       }
/* 161:    */     }
/* 162:    */     catch (InterruptedException ie)
/* 163:    */     {
/* 164:180 */       throw new UnknownHostException(name);
/* 165:    */     }
/* 166:182 */     if (q1x.ans != null) {
/* 167:183 */       return q1x.ans;
/* 168:    */     }
/* 169:184 */     if (q20.ans != null) {
/* 170:185 */       return q20.ans;
/* 171:    */     }
/* 172:187 */     throw q1x.uhe;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static UniAddress getByName(String hostname)
/* 176:    */     throws UnknownHostException
/* 177:    */   {
/* 178:201 */     return getByName(hostname, false);
/* 179:    */   }
/* 180:    */   
/* 181:    */   static boolean isDotQuadIP(String hostname)
/* 182:    */   {
/* 183:205 */     if (Character.isDigit(hostname.charAt(0)))
/* 184:    */     {
/* 185:    */       int dots;
/* 186:209 */       int i = dots = 0;
/* 187:210 */       int len = hostname.length();
/* 188:211 */       char[] data = hostname.toCharArray();
/* 189:212 */       while ((i < len) && (Character.isDigit(data[(i++)])))
/* 190:    */       {
/* 191:213 */         if ((i == len) && (dots == 3)) {
/* 192:215 */           return true;
/* 193:    */         }
/* 194:217 */         if ((i < len) && (data[i] == '.'))
/* 195:    */         {
/* 196:218 */           dots++;
/* 197:219 */           i++;
/* 198:    */         }
/* 199:    */       }
/* 200:    */     }
/* 201:224 */     return false;
/* 202:    */   }
/* 203:    */   
/* 204:    */   static boolean isAllDigits(String hostname)
/* 205:    */   {
/* 206:228 */     for (int i = 0; i < hostname.length(); i++) {
/* 207:229 */       if (!Character.isDigit(hostname.charAt(i))) {
/* 208:230 */         return false;
/* 209:    */       }
/* 210:    */     }
/* 211:233 */     return true;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static UniAddress getByName(String hostname, boolean possibleNTDomainOrWorkgroup)
/* 215:    */     throws UnknownHostException
/* 216:    */   {
/* 217:245 */     UniAddress[] addrs = getAllByName(hostname, possibleNTDomainOrWorkgroup);
/* 218:246 */     return addrs[0];
/* 219:    */   }
/* 220:    */   
/* 221:    */   public static UniAddress[] getAllByName(String hostname, boolean possibleNTDomainOrWorkgroup)
/* 222:    */     throws UnknownHostException
/* 223:    */   {
/* 224:254 */     if ((hostname == null) || (hostname.length() == 0)) {
/* 225:255 */       throw new UnknownHostException();
/* 226:    */     }
/* 227:258 */     if (isDotQuadIP(hostname))
/* 228:    */     {
/* 229:259 */       UniAddress[] addrs = new UniAddress[1];
/* 230:260 */       addrs[0] = new UniAddress(NbtAddress.getByName(hostname));
/* 231:261 */       return addrs;
/* 232:    */     }
/* 233:264 */     for (int i = 0; i < resolveOrder.length; i++) {
/* 234:    */       try
/* 235:    */       {
/* 236:    */         Object addr;
/* 237:    */         Object addr;
/* 238:266 */         switch (resolveOrder[i])
/* 239:    */         {
/* 240:    */         case 3: 
/* 241:    */           Object addr;
/* 242:268 */           if ((addr = Lmhosts.getByName(hostname)) != null) {
/* 243:    */             break;
/* 244:    */           }
/* 245:269 */           break;
/* 246:    */         case 0: 
/* 247:273 */           if ((hostname != "\001\002__MSBROWSE__\002") && (hostname.length() > 15)) {
/* 248:    */             continue;
/* 249:    */           }
/* 250:    */           Object addr;
/* 251:278 */           if (possibleNTDomainOrWorkgroup) {
/* 252:279 */             addr = lookupServerOrWorkgroup(hostname, NbtAddress.getWINSAddress());
/* 253:    */           } else {
/* 254:281 */             addr = NbtAddress.getByName(hostname, 32, null, NbtAddress.getWINSAddress());
/* 255:    */           }
/* 256:283 */           break;
/* 257:    */         case 1: 
/* 258:285 */           if (hostname.length() > 15) {
/* 259:    */             continue;
/* 260:    */           }
/* 261:    */           Object addr;
/* 262:289 */           if (possibleNTDomainOrWorkgroup) {
/* 263:290 */             addr = lookupServerOrWorkgroup(hostname, baddr);
/* 264:    */           } else {
/* 265:292 */             addr = NbtAddress.getByName(hostname, 32, null, baddr);
/* 266:    */           }
/* 267:294 */           break;
/* 268:    */         case 2: 
/* 269:296 */           if (isAllDigits(hostname)) {
/* 270:297 */             throw new UnknownHostException(hostname);
/* 271:    */           }
/* 272:299 */           InetAddress[] iaddrs = InetAddress.getAllByName(hostname);
/* 273:300 */           UniAddress[] addrs = new UniAddress[iaddrs.length];
/* 274:301 */           for (int ii = 0; ii < iaddrs.length; ii++) {
/* 275:302 */             addrs[ii] = new UniAddress(iaddrs[ii]);
/* 276:    */           }
/* 277:304 */           return addrs;
/* 278:    */         default: 
/* 279:306 */           throw new UnknownHostException(hostname);
/* 280:    */         }
/* 281:308 */         UniAddress[] addrs = new UniAddress[1];
/* 282:309 */         addrs[0] = new UniAddress(addr);
/* 283:310 */         return addrs;
/* 284:    */       }
/* 285:    */       catch (IOException ioe) {}
/* 286:    */     }
/* 287:315 */     throw new UnknownHostException(hostname);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public UniAddress(Object addr)
/* 291:    */   {
/* 292:363 */     if (addr == null) {
/* 293:364 */       throw new IllegalArgumentException();
/* 294:    */     }
/* 295:366 */     this.addr = addr;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public int hashCode()
/* 299:    */   {
/* 300:374 */     return this.addr.hashCode();
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean equals(Object obj)
/* 304:    */   {
/* 305:382 */     return ((obj instanceof UniAddress)) && (this.addr.equals(((UniAddress)obj).addr));
/* 306:    */   }
/* 307:    */   
/* 308:    */   public String firstCalledName()
/* 309:    */   {
/* 310:396 */     if ((this.addr instanceof NbtAddress)) {
/* 311:397 */       return ((NbtAddress)this.addr).firstCalledName();
/* 312:    */     }
/* 313:399 */     this.calledName = ((InetAddress)this.addr).getHostName();
/* 314:400 */     if (isDotQuadIP(this.calledName))
/* 315:    */     {
/* 316:401 */       this.calledName = "*SMBSERVER     ";
/* 317:    */     }
/* 318:    */     else
/* 319:    */     {
/* 320:403 */       int i = this.calledName.indexOf('.');
/* 321:404 */       if ((i > 1) && (i < 15)) {
/* 322:405 */         this.calledName = this.calledName.substring(0, i).toUpperCase();
/* 323:406 */       } else if (this.calledName.length() > 15) {
/* 324:407 */         this.calledName = "*SMBSERVER     ";
/* 325:    */       } else {
/* 326:409 */         this.calledName = this.calledName.toUpperCase();
/* 327:    */       }
/* 328:    */     }
/* 329:414 */     return this.calledName;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public String nextCalledName()
/* 333:    */   {
/* 334:423 */     if ((this.addr instanceof NbtAddress)) {
/* 335:424 */       return ((NbtAddress)this.addr).nextCalledName();
/* 336:    */     }
/* 337:425 */     if (this.calledName != "*SMBSERVER     ")
/* 338:    */     {
/* 339:426 */       this.calledName = "*SMBSERVER     ";
/* 340:427 */       return this.calledName;
/* 341:    */     }
/* 342:429 */     return null;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public Object getAddress()
/* 346:    */   {
/* 347:437 */     return this.addr;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public String getHostName()
/* 351:    */   {
/* 352:445 */     if ((this.addr instanceof NbtAddress)) {
/* 353:446 */       return ((NbtAddress)this.addr).getHostName();
/* 354:    */     }
/* 355:448 */     return ((InetAddress)this.addr).getHostName();
/* 356:    */   }
/* 357:    */   
/* 358:    */   public String getHostAddress()
/* 359:    */   {
/* 360:456 */     if ((this.addr instanceof NbtAddress)) {
/* 361:457 */       return ((NbtAddress)this.addr).getHostAddress();
/* 362:    */     }
/* 363:459 */     return ((InetAddress)this.addr).getHostAddress();
/* 364:    */   }
/* 365:    */   
/* 366:    */   public String toString()
/* 367:    */   {
/* 368:467 */     return this.addr.toString();
/* 369:    */   }
/* 370:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.UniAddress
 * JD-Core Version:    0.7.0.1
 */