/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.io.FilterWriter;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InterruptedIOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.net.InetAddress;
/*   8:    */ import java.net.UnknownHostException;
/*   9:    */ import java.text.DateFormat;
/*  10:    */ import java.text.SimpleDateFormat;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.Locale;
/*  13:    */ import org.apache.log4j.AppenderSkeleton;
/*  14:    */ import org.apache.log4j.Layout;
/*  15:    */ import org.apache.log4j.Priority;
/*  16:    */ import org.apache.log4j.helpers.SyslogQuietWriter;
/*  17:    */ import org.apache.log4j.helpers.SyslogWriter;
/*  18:    */ import org.apache.log4j.spi.ErrorHandler;
/*  19:    */ import org.apache.log4j.spi.LoggingEvent;
/*  20:    */ 
/*  21:    */ public class SyslogAppender
/*  22:    */   extends AppenderSkeleton
/*  23:    */ {
/*  24:    */   public static final int LOG_KERN = 0;
/*  25:    */   public static final int LOG_USER = 8;
/*  26:    */   public static final int LOG_MAIL = 16;
/*  27:    */   public static final int LOG_DAEMON = 24;
/*  28:    */   public static final int LOG_AUTH = 32;
/*  29:    */   public static final int LOG_SYSLOG = 40;
/*  30:    */   public static final int LOG_LPR = 48;
/*  31:    */   public static final int LOG_NEWS = 56;
/*  32:    */   public static final int LOG_UUCP = 64;
/*  33:    */   public static final int LOG_CRON = 72;
/*  34:    */   public static final int LOG_AUTHPRIV = 80;
/*  35:    */   public static final int LOG_FTP = 88;
/*  36:    */   public static final int LOG_LOCAL0 = 128;
/*  37:    */   public static final int LOG_LOCAL1 = 136;
/*  38:    */   public static final int LOG_LOCAL2 = 144;
/*  39:    */   public static final int LOG_LOCAL3 = 152;
/*  40:    */   public static final int LOG_LOCAL4 = 160;
/*  41:    */   public static final int LOG_LOCAL5 = 168;
/*  42:    */   public static final int LOG_LOCAL6 = 176;
/*  43:    */   public static final int LOG_LOCAL7 = 184;
/*  44:    */   protected static final int SYSLOG_HOST_OI = 0;
/*  45:    */   protected static final int FACILITY_OI = 1;
/*  46:    */   static final String TAB = "    ";
/*  47: 97 */   int syslogFacility = 8;
/*  48:    */   String facilityStr;
/*  49: 99 */   boolean facilityPrinting = false;
/*  50:    */   SyslogQuietWriter sqw;
/*  51:    */   String syslogHost;
/*  52:110 */   private boolean header = false;
/*  53:115 */   private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss ", Locale.ENGLISH);
/*  54:    */   private String localHostname;
/*  55:125 */   private boolean layoutHeaderChecked = false;
/*  56:    */   
/*  57:    */   public SyslogAppender()
/*  58:    */   {
/*  59:129 */     initSyslogFacilityStr();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public SyslogAppender(Layout layout, int syslogFacility)
/*  63:    */   {
/*  64:134 */     this.layout = layout;
/*  65:135 */     this.syslogFacility = syslogFacility;
/*  66:136 */     initSyslogFacilityStr();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public SyslogAppender(Layout layout, String syslogHost, int syslogFacility)
/*  70:    */   {
/*  71:141 */     this(layout, syslogFacility);
/*  72:142 */     setSyslogHost(syslogHost);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public synchronized void close()
/*  76:    */   {
/*  77:153 */     this.closed = true;
/*  78:154 */     if (this.sqw != null) {
/*  79:    */       try
/*  80:    */       {
/*  81:156 */         if ((this.layoutHeaderChecked) && (this.layout != null) && (this.layout.getFooter() != null)) {
/*  82:157 */           sendLayoutMessage(this.layout.getFooter());
/*  83:    */         }
/*  84:159 */         this.sqw.close();
/*  85:160 */         this.sqw = null;
/*  86:    */       }
/*  87:    */       catch (InterruptedIOException e)
/*  88:    */       {
/*  89:162 */         Thread.currentThread().interrupt();
/*  90:163 */         this.sqw = null;
/*  91:    */       }
/*  92:    */       catch (IOException e)
/*  93:    */       {
/*  94:165 */         this.sqw = null;
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void initSyslogFacilityStr()
/* 100:    */   {
/* 101:172 */     this.facilityStr = getFacilityString(this.syslogFacility);
/* 102:174 */     if (this.facilityStr == null)
/* 103:    */     {
/* 104:175 */       System.err.println("\"" + this.syslogFacility + "\" is an unknown syslog facility. Defaulting to \"USER\".");
/* 105:    */       
/* 106:177 */       this.syslogFacility = 8;
/* 107:178 */       this.facilityStr = "user:";
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:180 */       this.facilityStr += ":";
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static String getFacilityString(int syslogFacility)
/* 116:    */   {
/* 117:191 */     switch (syslogFacility)
/* 118:    */     {
/* 119:    */     case 0: 
/* 120:192 */       return "kern";
/* 121:    */     case 8: 
/* 122:193 */       return "user";
/* 123:    */     case 16: 
/* 124:194 */       return "mail";
/* 125:    */     case 24: 
/* 126:195 */       return "daemon";
/* 127:    */     case 32: 
/* 128:196 */       return "auth";
/* 129:    */     case 40: 
/* 130:197 */       return "syslog";
/* 131:    */     case 48: 
/* 132:198 */       return "lpr";
/* 133:    */     case 56: 
/* 134:199 */       return "news";
/* 135:    */     case 64: 
/* 136:200 */       return "uucp";
/* 137:    */     case 72: 
/* 138:201 */       return "cron";
/* 139:    */     case 80: 
/* 140:202 */       return "authpriv";
/* 141:    */     case 88: 
/* 142:203 */       return "ftp";
/* 143:    */     case 128: 
/* 144:204 */       return "local0";
/* 145:    */     case 136: 
/* 146:205 */       return "local1";
/* 147:    */     case 144: 
/* 148:206 */       return "local2";
/* 149:    */     case 152: 
/* 150:207 */       return "local3";
/* 151:    */     case 160: 
/* 152:208 */       return "local4";
/* 153:    */     case 168: 
/* 154:209 */       return "local5";
/* 155:    */     case 176: 
/* 156:210 */       return "local6";
/* 157:    */     case 184: 
/* 158:211 */       return "local7";
/* 159:    */     }
/* 160:212 */     return null;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static int getFacility(String facilityName)
/* 164:    */   {
/* 165:230 */     if (facilityName != null) {
/* 166:231 */       facilityName = facilityName.trim();
/* 167:    */     }
/* 168:233 */     if ("KERN".equalsIgnoreCase(facilityName)) {
/* 169:234 */       return 0;
/* 170:    */     }
/* 171:235 */     if ("USER".equalsIgnoreCase(facilityName)) {
/* 172:236 */       return 8;
/* 173:    */     }
/* 174:237 */     if ("MAIL".equalsIgnoreCase(facilityName)) {
/* 175:238 */       return 16;
/* 176:    */     }
/* 177:239 */     if ("DAEMON".equalsIgnoreCase(facilityName)) {
/* 178:240 */       return 24;
/* 179:    */     }
/* 180:241 */     if ("AUTH".equalsIgnoreCase(facilityName)) {
/* 181:242 */       return 32;
/* 182:    */     }
/* 183:243 */     if ("SYSLOG".equalsIgnoreCase(facilityName)) {
/* 184:244 */       return 40;
/* 185:    */     }
/* 186:245 */     if ("LPR".equalsIgnoreCase(facilityName)) {
/* 187:246 */       return 48;
/* 188:    */     }
/* 189:247 */     if ("NEWS".equalsIgnoreCase(facilityName)) {
/* 190:248 */       return 56;
/* 191:    */     }
/* 192:249 */     if ("UUCP".equalsIgnoreCase(facilityName)) {
/* 193:250 */       return 64;
/* 194:    */     }
/* 195:251 */     if ("CRON".equalsIgnoreCase(facilityName)) {
/* 196:252 */       return 72;
/* 197:    */     }
/* 198:253 */     if ("AUTHPRIV".equalsIgnoreCase(facilityName)) {
/* 199:254 */       return 80;
/* 200:    */     }
/* 201:255 */     if ("FTP".equalsIgnoreCase(facilityName)) {
/* 202:256 */       return 88;
/* 203:    */     }
/* 204:257 */     if ("LOCAL0".equalsIgnoreCase(facilityName)) {
/* 205:258 */       return 128;
/* 206:    */     }
/* 207:259 */     if ("LOCAL1".equalsIgnoreCase(facilityName)) {
/* 208:260 */       return 136;
/* 209:    */     }
/* 210:261 */     if ("LOCAL2".equalsIgnoreCase(facilityName)) {
/* 211:262 */       return 144;
/* 212:    */     }
/* 213:263 */     if ("LOCAL3".equalsIgnoreCase(facilityName)) {
/* 214:264 */       return 152;
/* 215:    */     }
/* 216:265 */     if ("LOCAL4".equalsIgnoreCase(facilityName)) {
/* 217:266 */       return 160;
/* 218:    */     }
/* 219:267 */     if ("LOCAL5".equalsIgnoreCase(facilityName)) {
/* 220:268 */       return 168;
/* 221:    */     }
/* 222:269 */     if ("LOCAL6".equalsIgnoreCase(facilityName)) {
/* 223:270 */       return 176;
/* 224:    */     }
/* 225:271 */     if ("LOCAL7".equalsIgnoreCase(facilityName)) {
/* 226:272 */       return 184;
/* 227:    */     }
/* 228:274 */     return -1;
/* 229:    */   }
/* 230:    */   
/* 231:    */   private void splitPacket(String header, String packet)
/* 232:    */   {
/* 233:280 */     int byteCount = packet.getBytes().length;
/* 234:286 */     if (byteCount <= 1019)
/* 235:    */     {
/* 236:287 */       this.sqw.write(packet);
/* 237:    */     }
/* 238:    */     else
/* 239:    */     {
/* 240:289 */       int split = header.length() + (packet.length() - header.length()) / 2;
/* 241:290 */       splitPacket(header, packet.substring(0, split) + "...");
/* 242:291 */       splitPacket(header, header + "..." + packet.substring(split));
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void append(LoggingEvent event)
/* 247:    */   {
/* 248:298 */     if (!isAsSevereAsThreshold(event.getLevel())) {
/* 249:299 */       return;
/* 250:    */     }
/* 251:302 */     if (this.sqw == null)
/* 252:    */     {
/* 253:303 */       this.errorHandler.error("No syslog host is set for SyslogAppedender named \"" + this.name + "\".");
/* 254:    */       
/* 255:305 */       return;
/* 256:    */     }
/* 257:308 */     if (!this.layoutHeaderChecked)
/* 258:    */     {
/* 259:309 */       if ((this.layout != null) && (this.layout.getHeader() != null)) {
/* 260:310 */         sendLayoutMessage(this.layout.getHeader());
/* 261:    */       }
/* 262:312 */       this.layoutHeaderChecked = true;
/* 263:    */     }
/* 264:315 */     String hdr = getPacketHeader(event.timeStamp);
/* 265:    */     String packet;
/* 266:    */     String packet;
/* 267:317 */     if (this.layout == null) {
/* 268:318 */       packet = String.valueOf(event.getMessage());
/* 269:    */     } else {
/* 270:320 */       packet = this.layout.format(event);
/* 271:    */     }
/* 272:322 */     if ((this.facilityPrinting) || (hdr.length() > 0))
/* 273:    */     {
/* 274:323 */       StringBuffer buf = new StringBuffer(hdr);
/* 275:324 */       if (this.facilityPrinting) {
/* 276:325 */         buf.append(this.facilityStr);
/* 277:    */       }
/* 278:327 */       buf.append(packet);
/* 279:328 */       packet = buf.toString();
/* 280:    */     }
/* 281:331 */     this.sqw.setLevel(event.getLevel().getSyslogEquivalent());
/* 282:335 */     if (packet.length() > 256) {
/* 283:336 */       splitPacket(hdr, packet);
/* 284:    */     } else {
/* 285:338 */       this.sqw.write(packet);
/* 286:    */     }
/* 287:341 */     if ((this.layout == null) || (this.layout.ignoresThrowable()))
/* 288:    */     {
/* 289:342 */       String[] s = event.getThrowableStrRep();
/* 290:343 */       if (s != null) {
/* 291:344 */         for (int i = 0; i < s.length; i++) {
/* 292:345 */           if (s[i].startsWith("\t")) {
/* 293:346 */             this.sqw.write(hdr + "    " + s[i].substring(1));
/* 294:    */           } else {
/* 295:348 */             this.sqw.write(hdr + s[i]);
/* 296:    */           }
/* 297:    */         }
/* 298:    */       }
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void activateOptions()
/* 303:    */   {
/* 304:361 */     if (this.header) {
/* 305:362 */       getLocalHostname();
/* 306:    */     }
/* 307:364 */     if ((this.layout != null) && (this.layout.getHeader() != null)) {
/* 308:365 */       sendLayoutMessage(this.layout.getHeader());
/* 309:    */     }
/* 310:367 */     this.layoutHeaderChecked = true;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public boolean requiresLayout()
/* 314:    */   {
/* 315:377 */     return true;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setSyslogHost(String syslogHost)
/* 319:    */   {
/* 320:391 */     this.sqw = new SyslogQuietWriter(new SyslogWriter(syslogHost), this.syslogFacility, this.errorHandler);
/* 321:    */     
/* 322:    */ 
/* 323:394 */     this.syslogHost = syslogHost;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public String getSyslogHost()
/* 327:    */   {
/* 328:402 */     return this.syslogHost;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setFacility(String facilityName)
/* 332:    */   {
/* 333:416 */     if (facilityName == null) {
/* 334:417 */       return;
/* 335:    */     }
/* 336:419 */     this.syslogFacility = getFacility(facilityName);
/* 337:420 */     if (this.syslogFacility == -1)
/* 338:    */     {
/* 339:421 */       System.err.println("[" + facilityName + "] is an unknown syslog facility. Defaulting to [USER].");
/* 340:    */       
/* 341:423 */       this.syslogFacility = 8;
/* 342:    */     }
/* 343:426 */     initSyslogFacilityStr();
/* 344:429 */     if (this.sqw != null) {
/* 345:430 */       this.sqw.setSyslogFacility(this.syslogFacility);
/* 346:    */     }
/* 347:    */   }
/* 348:    */   
/* 349:    */   public String getFacility()
/* 350:    */   {
/* 351:439 */     return getFacilityString(this.syslogFacility);
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setFacilityPrinting(boolean on)
/* 355:    */   {
/* 356:449 */     this.facilityPrinting = on;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public boolean getFacilityPrinting()
/* 360:    */   {
/* 361:457 */     return this.facilityPrinting;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public final boolean getHeader()
/* 365:    */   {
/* 366:467 */     return this.header;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public final void setHeader(boolean val)
/* 370:    */   {
/* 371:476 */     this.header = val;
/* 372:    */   }
/* 373:    */   
/* 374:    */   private String getLocalHostname()
/* 375:    */   {
/* 376:485 */     if (this.localHostname == null) {
/* 377:    */       try
/* 378:    */       {
/* 379:487 */         InetAddress addr = InetAddress.getLocalHost();
/* 380:488 */         this.localHostname = addr.getHostName();
/* 381:    */       }
/* 382:    */       catch (UnknownHostException uhe)
/* 383:    */       {
/* 384:490 */         this.localHostname = "UNKNOWN_HOST";
/* 385:    */       }
/* 386:    */     }
/* 387:493 */     return this.localHostname;
/* 388:    */   }
/* 389:    */   
/* 390:    */   private String getPacketHeader(long timeStamp)
/* 391:    */   {
/* 392:503 */     if (this.header)
/* 393:    */     {
/* 394:504 */       StringBuffer buf = new StringBuffer(this.dateFormat.format(new Date(timeStamp)));
/* 395:506 */       if (buf.charAt(4) == '0') {
/* 396:507 */         buf.setCharAt(4, ' ');
/* 397:    */       }
/* 398:509 */       buf.append(getLocalHostname());
/* 399:510 */       buf.append(' ');
/* 400:511 */       return buf.toString();
/* 401:    */     }
/* 402:513 */     return "";
/* 403:    */   }
/* 404:    */   
/* 405:    */   private void sendLayoutMessage(String msg)
/* 406:    */   {
/* 407:521 */     if (this.sqw != null)
/* 408:    */     {
/* 409:522 */       String packet = msg;
/* 410:523 */       String hdr = getPacketHeader(new Date().getTime());
/* 411:524 */       if ((this.facilityPrinting) || (hdr.length() > 0))
/* 412:    */       {
/* 413:525 */         StringBuffer buf = new StringBuffer(hdr);
/* 414:526 */         if (this.facilityPrinting) {
/* 415:527 */           buf.append(this.facilityStr);
/* 416:    */         }
/* 417:529 */         buf.append(msg);
/* 418:530 */         packet = buf.toString();
/* 419:    */       }
/* 420:532 */       this.sqw.setLevel(6);
/* 421:533 */       this.sqw.write(packet);
/* 422:    */     }
/* 423:    */   }
/* 424:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.SyslogAppender
 * JD-Core Version:    0.7.0.1
 */