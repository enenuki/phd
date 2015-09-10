/*   1:    */ package jcifs.http;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PrintWriter;
/*   6:    */ import java.text.SimpleDateFormat;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.Enumeration;
/*   9:    */ import java.util.GregorianCalendar;
/*  10:    */ import java.util.LinkedList;
/*  11:    */ import java.util.ListIterator;
/*  12:    */ import javax.servlet.ServletException;
/*  13:    */ import javax.servlet.ServletOutputStream;
/*  14:    */ import javax.servlet.http.HttpServlet;
/*  15:    */ import javax.servlet.http.HttpServletRequest;
/*  16:    */ import javax.servlet.http.HttpServletResponse;
/*  17:    */ import javax.servlet.http.HttpSession;
/*  18:    */ import jcifs.Config;
/*  19:    */ import jcifs.UniAddress;
/*  20:    */ import jcifs.netbios.NbtAddress;
/*  21:    */ import jcifs.smb.DfsReferral;
/*  22:    */ import jcifs.smb.NtlmPasswordAuthentication;
/*  23:    */ import jcifs.smb.SmbAuthException;
/*  24:    */ import jcifs.smb.SmbException;
/*  25:    */ import jcifs.smb.SmbFile;
/*  26:    */ import jcifs.smb.SmbFileInputStream;
/*  27:    */ import jcifs.smb.SmbSession;
/*  28:    */ import jcifs.util.Base64;
/*  29:    */ import jcifs.util.LogStream;
/*  30:    */ import jcifs.util.MimeMap;
/*  31:    */ 
/*  32:    */ public class NetworkExplorer
/*  33:    */   extends HttpServlet
/*  34:    */ {
/*  35: 44 */   private static LogStream log = ;
/*  36:    */   private MimeMap mimeMap;
/*  37:    */   private String style;
/*  38:    */   private NtlmSsp ntlmSsp;
/*  39:    */   private boolean credentialsSupplied;
/*  40:    */   private boolean enableBasic;
/*  41:    */   private boolean insecureBasic;
/*  42:    */   private String realm;
/*  43:    */   private String defaultDomain;
/*  44:    */   
/*  45:    */   public void init()
/*  46:    */     throws ServletException
/*  47:    */   {
/*  48: 56 */     StringBuffer sb = new StringBuffer();
/*  49: 57 */     byte[] buf = new byte[1024];
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53: 61 */     Config.setProperty("jcifs.smb.client.soTimeout", "600000");
/*  54: 62 */     Config.setProperty("jcifs.smb.client.attrExpirationPeriod", "300000");
/*  55:    */     
/*  56: 64 */     Enumeration e = getInitParameterNames();
/*  57: 65 */     while (e.hasMoreElements())
/*  58:    */     {
/*  59: 66 */       String name = (String)e.nextElement();
/*  60: 67 */       if (name.startsWith("jcifs.")) {
/*  61: 68 */         Config.setProperty(name, getInitParameter(name));
/*  62:    */       }
/*  63:    */     }
/*  64: 72 */     if (Config.getProperty("jcifs.smb.client.username") == null) {
/*  65: 73 */       this.ntlmSsp = new NtlmSsp();
/*  66:    */     } else {
/*  67: 75 */       this.credentialsSupplied = true;
/*  68:    */     }
/*  69:    */     try
/*  70:    */     {
/*  71: 79 */       this.mimeMap = new MimeMap();
/*  72: 80 */       InputStream is = getClass().getClassLoader().getResourceAsStream("jcifs/http/ne.css");
/*  73:    */       int n;
/*  74: 81 */       while ((n = is.read(buf)) != -1) {
/*  75: 82 */         sb.append(new String(buf, 0, n, "ISO8859_1"));
/*  76:    */       }
/*  77: 84 */       this.style = sb.toString();
/*  78:    */     }
/*  79:    */     catch (IOException ioe)
/*  80:    */     {
/*  81: 86 */       throw new ServletException(ioe.getMessage());
/*  82:    */     }
/*  83:    */     int n;
/*  84:    */     InputStream is;
/*  85: 89 */     this.enableBasic = Config.getBoolean("jcifs.http.enableBasic", false);
/*  86: 90 */     this.insecureBasic = Config.getBoolean("jcifs.http.insecureBasic", false);
/*  87: 91 */     this.realm = Config.getProperty("jcifs.http.basicRealm");
/*  88: 92 */     if (this.realm == null) {
/*  89: 92 */       this.realm = "jCIFS";
/*  90:    */     }
/*  91: 93 */     this.defaultDomain = Config.getProperty("jcifs.smb.client.domain");
/*  92:    */     int level;
/*  93: 95 */     if ((level = Config.getInt("jcifs.util.loglevel", -1)) != -1) {
/*  94: 96 */       LogStream.setLevel(level);
/*  95:    */     }
/*  96: 98 */     if (LogStream.level > 2) {
/*  97:    */       try
/*  98:    */       {
/*  99:100 */         Config.store(log, "JCIFS PROPERTIES");
/* 100:    */       }
/* 101:    */       catch (IOException ioe) {}
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected void doFile(HttpServletRequest req, HttpServletResponse resp, SmbFile file)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:108 */     byte[] buf = new byte[8192];
/* 109:    */     
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:114 */     SmbFileInputStream in = new SmbFileInputStream(file);
/* 115:115 */     ServletOutputStream out = resp.getOutputStream();
/* 116:116 */     String url = file.getPath();
/* 117:    */     
/* 118:118 */     resp.setContentType("text/plain");
/* 119:    */     int n;
/* 120:    */     String type;
/* 121:120 */     if (((n = url.lastIndexOf('.')) > 0) && ((type = url.substring(n + 1)) != null) && (type.length() > 1) && (type.length() < 6)) {
/* 122:123 */       resp.setContentType(this.mimeMap.getMimeType(type));
/* 123:    */     }
/* 124:125 */     resp.setHeader("Content-Length", file.length() + "");
/* 125:126 */     resp.setHeader("Accept-Ranges", "Bytes");
/* 126:128 */     while ((n = in.read(buf)) != -1) {
/* 127:129 */       out.write(buf, 0, n);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected int compareNames(SmbFile f1, String f1name, SmbFile f2)
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:133 */     if (f1.isDirectory() != f2.isDirectory()) {
/* 135:134 */       return f1.isDirectory() ? -1 : 1;
/* 136:    */     }
/* 137:136 */     return f1name.compareToIgnoreCase(f2.getName());
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected int compareSizes(SmbFile f1, String f1name, SmbFile f2)
/* 141:    */     throws IOException
/* 142:    */   {
/* 143:141 */     if (f1.isDirectory() != f2.isDirectory()) {
/* 144:142 */       return f1.isDirectory() ? -1 : 1;
/* 145:    */     }
/* 146:144 */     if (f1.isDirectory()) {
/* 147:145 */       return f1name.compareToIgnoreCase(f2.getName());
/* 148:    */     }
/* 149:147 */     long diff = f1.length() - f2.length();
/* 150:148 */     if (diff == 0L) {
/* 151:149 */       return f1name.compareToIgnoreCase(f2.getName());
/* 152:    */     }
/* 153:151 */     return diff > 0L ? -1 : 1;
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected int compareTypes(SmbFile f1, String f1name, SmbFile f2)
/* 157:    */     throws IOException
/* 158:    */   {
/* 159:157 */     if (f1.isDirectory() != f2.isDirectory()) {
/* 160:158 */       return f1.isDirectory() ? -1 : 1;
/* 161:    */     }
/* 162:160 */     String f2name = f2.getName();
/* 163:161 */     if (f1.isDirectory()) {
/* 164:162 */       return f1name.compareToIgnoreCase(f2name);
/* 165:    */     }
/* 166:164 */     int i = f1name.lastIndexOf('.');
/* 167:165 */     String t1 = i == -1 ? "" : f1name.substring(i + 1);
/* 168:166 */     i = f2name.lastIndexOf('.');
/* 169:167 */     String t2 = i == -1 ? "" : f2name.substring(i + 1);
/* 170:    */     
/* 171:169 */     i = t1.compareToIgnoreCase(t2);
/* 172:170 */     if (i == 0) {
/* 173:171 */       return f1name.compareToIgnoreCase(f2name);
/* 174:    */     }
/* 175:173 */     return i;
/* 176:    */   }
/* 177:    */   
/* 178:    */   protected int compareDates(SmbFile f1, String f1name, SmbFile f2)
/* 179:    */     throws IOException
/* 180:    */   {
/* 181:176 */     if (f1.isDirectory() != f2.isDirectory()) {
/* 182:177 */       return f1.isDirectory() ? -1 : 1;
/* 183:    */     }
/* 184:179 */     if (f1.isDirectory()) {
/* 185:180 */       return f1name.compareToIgnoreCase(f2.getName());
/* 186:    */     }
/* 187:182 */     return f1.lastModified() > f2.lastModified() ? -1 : 1;
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected void doDirectory(HttpServletRequest req, HttpServletResponse resp, SmbFile dir)
/* 191:    */     throws IOException
/* 192:    */   {
/* 193:192 */     SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yy h:mm a");
/* 194:193 */     GregorianCalendar cal = new GregorianCalendar();
/* 195:    */     
/* 196:195 */     sdf.setCalendar(cal);
/* 197:    */     
/* 198:197 */     SmbFile[] dirents = dir.listFiles();
/* 199:198 */     if (LogStream.level > 2) {
/* 200:199 */       log.println(dirents.length + " items listed");
/* 201:    */     }
/* 202:200 */     LinkedList sorted = new LinkedList();
/* 203:    */     String fmt;
/* 204:201 */     if ((fmt = req.getParameter("fmt")) == null) {
/* 205:202 */       fmt = "col";
/* 206:    */     }
/* 207:204 */     int sort = 0;
/* 208:    */     String str;
/* 209:205 */     if (((str = req.getParameter("sort")) == null) || (str.equals("name"))) {
/* 210:206 */       sort = 0;
/* 211:207 */     } else if (str.equals("size")) {
/* 212:208 */       sort = 1;
/* 213:209 */     } else if (str.equals("type")) {
/* 214:210 */       sort = 2;
/* 215:211 */     } else if (str.equals("date")) {
/* 216:212 */       sort = 3;
/* 217:    */     }
/* 218:    */     int fileCount;
/* 219:214 */     int dirCount = fileCount = 0;
/* 220:215 */     int maxLen = 28;
/* 221:216 */     for (int i = 0; i < dirents.length; i++)
/* 222:    */     {
/* 223:    */       try
/* 224:    */       {
/* 225:218 */         if (dirents[i].getType() == 16) {
/* 226:    */           continue;
/* 227:    */         }
/* 228:    */       }
/* 229:    */       catch (SmbAuthException sae)
/* 230:    */       {
/* 231:222 */         if (LogStream.level > 2) {
/* 232:223 */           sae.printStackTrace(log);
/* 233:    */         }
/* 234:    */       }
/* 235:    */       catch (SmbException se)
/* 236:    */       {
/* 237:225 */         if (LogStream.level > 2) {
/* 238:226 */           se.printStackTrace(log);
/* 239:    */         }
/* 240:227 */         if (se.getNtStatus() != -1073741823) {
/* 241:228 */           throw se;
/* 242:    */         }
/* 243:    */       }
/* 244:231 */       if (dirents[i].isDirectory()) {
/* 245:232 */         dirCount++;
/* 246:    */       } else {
/* 247:234 */         fileCount++;
/* 248:    */       }
/* 249:237 */       String name = dirents[i].getName();
/* 250:238 */       if (LogStream.level > 3) {
/* 251:239 */         log.println(i + ": " + name);
/* 252:    */       }
/* 253:240 */       int len = name.length();
/* 254:241 */       if (len > maxLen) {
/* 255:242 */         maxLen = len;
/* 256:    */       }
/* 257:245 */       ListIterator iter = sorted.listIterator();
/* 258:246 */       for (int j = 0; iter.hasNext(); j++) {
/* 259:247 */         if (sort == 0 ? 
/* 260:248 */           compareNames(dirents[i], name, (SmbFile)iter.next()) >= 0 : 
/* 261:    */           
/* 262:    */ 
/* 263:251 */           sort == 1 ? 
/* 264:252 */           compareSizes(dirents[i], name, (SmbFile)iter.next()) >= 0 : 
/* 265:    */           
/* 266:    */ 
/* 267:255 */           sort == 2 ? 
/* 268:256 */           compareTypes(dirents[i], name, (SmbFile)iter.next()) >= 0 : 
/* 269:    */           
/* 270:    */ 
/* 271:259 */           (sort == 3) && 
/* 272:260 */           (compareDates(dirents[i], name, (SmbFile)iter.next()) < 0)) {
/* 273:    */           break;
/* 274:    */         }
/* 275:    */       }
/* 276:265 */       sorted.add(j, dirents[i]);
/* 277:    */     }
/* 278:267 */     if (maxLen > 50) {
/* 279:268 */       maxLen = 50;
/* 280:    */     }
/* 281:270 */     maxLen *= 9;
/* 282:    */     
/* 283:272 */     PrintWriter out = resp.getWriter();
/* 284:    */     
/* 285:274 */     resp.setContentType("text/html");
/* 286:    */     
/* 287:276 */     out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
/* 288:277 */     out.println("<html><head><title>Network Explorer</title>");
/* 289:278 */     out.println("<meta HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">");
/* 290:279 */     out.println("<style TYPE=\"text/css\">");
/* 291:    */     
/* 292:281 */     out.println(this.style);
/* 293:283 */     if (dirents.length < 200)
/* 294:    */     {
/* 295:284 */       out.println("    a:hover {");
/* 296:285 */       out.println("        background: #a2ff01;");
/* 297:286 */       out.println("    }");
/* 298:    */     }
/* 299:289 */     out.println("</STYLE>");
/* 300:290 */     out.println("</head><body>");
/* 301:    */     
/* 302:292 */     out.print("<a class=\"sort\" style=\"width: " + maxLen + ";\" href=\"?fmt=detail&sort=name\">Name</a>");
/* 303:293 */     out.println("<a class=\"sort\" href=\"?fmt=detail&sort=size\">Size</a>");
/* 304:294 */     out.println("<a class=\"sort\" href=\"?fmt=detail&sort=type\">Type</a>");
/* 305:295 */     out.println("<a class=\"sort\" style=\"width: 180\" href=\"?fmt=detail&sort=date\">Modified</a><br clear='all'><p>");
/* 306:    */     
/* 307:297 */     String path = dir.getCanonicalPath();
/* 308:299 */     if (path.length() < 7)
/* 309:    */     {
/* 310:300 */       out.println("<b><big>smb://</big></b><br>");
/* 311:301 */       path = ".";
/* 312:    */     }
/* 313:    */     else
/* 314:    */     {
/* 315:303 */       out.println("<b><big>" + path + "</big></b><br>");
/* 316:304 */       path = "../";
/* 317:    */     }
/* 318:306 */     out.println(dirCount + fileCount + " objects (" + dirCount + " directories, " + fileCount + " files)<br>");
/* 319:307 */     out.println("<b><a class=\"plain\" href=\".\">normal</a> | <a class=\"plain\" href=\"?fmt=detail\">detailed</a></b>");
/* 320:308 */     out.println("<p><table border='0' cellspacing='0' cellpadding='0'><tr><td>");
/* 321:    */     
/* 322:310 */     out.print("<A style=\"width: " + maxLen);
/* 323:311 */     out.print("; height: 18;\" HREF=\"");
/* 324:312 */     out.print(path);
/* 325:313 */     out.println("\"><b>&uarr;</b></a>");
/* 326:314 */     if (fmt.equals("detail")) {
/* 327:315 */       out.println("<br clear='all'>");
/* 328:    */     }
/* 329:318 */     if ((path.length() == 1) || (dir.getType() != 2)) {
/* 330:319 */       path = "";
/* 331:    */     }
/* 332:322 */     ListIterator iter = sorted.listIterator();
/* 333:323 */     while (iter.hasNext())
/* 334:    */     {
/* 335:324 */       SmbFile f = (SmbFile)iter.next();
/* 336:325 */       String name = f.getName();
/* 337:327 */       if (fmt.equals("detail"))
/* 338:    */       {
/* 339:328 */         out.print("<A style=\"width: " + maxLen);
/* 340:329 */         out.print("; height: 18;\" HREF=\"");
/* 341:330 */         out.print(path);
/* 342:331 */         out.print(name);
/* 343:333 */         if (f.isDirectory())
/* 344:    */         {
/* 345:334 */           out.print("?fmt=detail\"><b>");
/* 346:335 */           out.print(name);
/* 347:336 */           out.print("</b></a>");
/* 348:    */         }
/* 349:    */         else
/* 350:    */         {
/* 351:338 */           out.print("\"><b>");
/* 352:339 */           out.print(name);
/* 353:340 */           out.print("</b></a><div align='right'>");
/* 354:341 */           out.print(f.length() / 1024L + " KB </div><div>");
/* 355:342 */           i = name.lastIndexOf('.') + 1;
/* 356:343 */           if ((i > 1) && (name.length() - i < 6)) {
/* 357:344 */             out.print(name.substring(i).toUpperCase() + "</div class='ext'>");
/* 358:    */           } else {
/* 359:346 */             out.print("&nbsp;</div>");
/* 360:    */           }
/* 361:348 */           out.print("<div style='width: 180'>");
/* 362:349 */           out.print(sdf.format(new Date(f.lastModified())));
/* 363:350 */           out.print("</div>");
/* 364:    */         }
/* 365:352 */         out.println("<br clear='all'>");
/* 366:    */       }
/* 367:    */       else
/* 368:    */       {
/* 369:354 */         out.print("<A style=\"width: " + maxLen);
/* 370:355 */         if (f.isDirectory())
/* 371:    */         {
/* 372:356 */           out.print("; height: 18;\" HREF=\"");
/* 373:357 */           out.print(path);
/* 374:358 */           out.print(name);
/* 375:359 */           out.print("\"><b>");
/* 376:360 */           out.print(name);
/* 377:361 */           out.print("</b></a>");
/* 378:    */         }
/* 379:    */         else
/* 380:    */         {
/* 381:363 */           out.print(";\" HREF=\"");
/* 382:364 */           out.print(path);
/* 383:365 */           out.print(name);
/* 384:366 */           out.print("\"><b>");
/* 385:367 */           out.print(name);
/* 386:368 */           out.print("</b><br><small>");
/* 387:369 */           out.print(f.length() / 1024L + "KB <br>");
/* 388:370 */           out.print(sdf.format(new Date(f.lastModified())));
/* 389:371 */           out.print("</small>");
/* 390:372 */           out.println("</a>");
/* 391:    */         }
/* 392:    */       }
/* 393:    */     }
/* 394:377 */     out.println("</td></tr></table>");
/* 395:378 */     out.println("</BODY></HTML>");
/* 396:379 */     out.close();
/* 397:    */   }
/* 398:    */   
/* 399:    */   private String parseServerAndShare(String pathInfo)
/* 400:    */   {
/* 401:382 */     char[] out = new char[256];
/* 402:386 */     if (pathInfo == null) {
/* 403:387 */       return null;
/* 404:    */     }
/* 405:389 */     int len = pathInfo.length();
/* 406:    */     int i;
/* 407:391 */     int p = i = 0;
/* 408:392 */     while ((p < len) && (pathInfo.charAt(p) == '/')) {
/* 409:393 */       p++;
/* 410:    */     }
/* 411:395 */     if (p == len) {
/* 412:396 */       return null;
/* 413:    */     }
/* 414:    */     char ch;
/* 415:400 */     while ((p < len) && ((ch = pathInfo.charAt(p)) != '/'))
/* 416:    */     {
/* 417:401 */       out[(i++)] = ch;
/* 418:402 */       p++;
/* 419:    */     }
/* 420:404 */     while ((p < len) && (pathInfo.charAt(p) == '/')) {
/* 421:405 */       p++;
/* 422:    */     }
/* 423:407 */     if (p < len)
/* 424:    */     {
/* 425:408 */       out[(i++)] = '/';
/* 426:    */       char ch;
/* 427:    */       do
/* 428:    */       {
/* 429:410 */         char tmp146_143 = pathInfo.charAt(p++);ch = tmp146_143;out[(i++)] = tmp146_143;
/* 430:411 */       } while ((p < len) && (ch != '/'));
/* 431:    */     }
/* 432:413 */     return new String(out, 0, i);
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void doGet(HttpServletRequest req, HttpServletResponse resp)
/* 436:    */     throws IOException, ServletException
/* 437:    */   {
/* 438:418 */     String server = null;
/* 439:419 */     boolean possibleWorkgroup = true;
/* 440:420 */     NtlmPasswordAuthentication ntlm = null;
/* 441:421 */     HttpSession ssn = req.getSession(false);
/* 442:    */     String pathInfo;
/* 443:423 */     if ((pathInfo = req.getPathInfo()) != null)
/* 444:    */     {
/* 445:425 */       server = parseServerAndShare(pathInfo);
/* 446:    */       int i;
/* 447:426 */       if ((server != null) && ((i = server.indexOf('/')) > 0))
/* 448:    */       {
/* 449:427 */         server = server.substring(0, i).toLowerCase();
/* 450:428 */         possibleWorkgroup = false;
/* 451:    */       }
/* 452:    */     }
/* 453:432 */     String msg = req.getHeader("Authorization");
/* 454:433 */     boolean offerBasic = (this.enableBasic) && ((this.insecureBasic) || (req.isSecure()));
/* 455:435 */     if ((msg != null) && ((msg.startsWith("NTLM ")) || ((offerBasic) && (msg.startsWith("Basic ")))))
/* 456:    */     {
/* 457:438 */       if (msg.startsWith("NTLM "))
/* 458:    */       {
/* 459:    */         UniAddress dc;
/* 460:    */         UniAddress dc;
/* 461:441 */         if ((pathInfo == null) || (server == null))
/* 462:    */         {
/* 463:442 */           String mb = NbtAddress.getByName("\001\002__MSBROWSE__\002", 1, null).getHostAddress();
/* 464:443 */           dc = UniAddress.getByName(mb);
/* 465:    */         }
/* 466:    */         else
/* 467:    */         {
/* 468:445 */           dc = UniAddress.getByName(server, possibleWorkgroup);
/* 469:    */         }
/* 470:448 */         req.getSession();
/* 471:449 */         byte[] challenge = SmbSession.getChallenge(dc);
/* 472:450 */         if ((ntlm = NtlmSsp.authenticate(req, resp, challenge)) == null) {
/* 473:451 */           return;
/* 474:    */         }
/* 475:    */       }
/* 476:    */       else
/* 477:    */       {
/* 478:454 */         String auth = new String(Base64.decode(msg.substring(6)), "US-ASCII");
/* 479:455 */         int index = auth.indexOf(':');
/* 480:456 */         String user = index != -1 ? auth.substring(0, index) : auth;
/* 481:457 */         String password = index != -1 ? auth.substring(index + 1) : "";
/* 482:458 */         index = user.indexOf('\\');
/* 483:459 */         if (index == -1) {
/* 484:459 */           index = user.indexOf('/');
/* 485:    */         }
/* 486:460 */         String domain = index != -1 ? user.substring(0, index) : this.defaultDomain;
/* 487:461 */         user = index != -1 ? user.substring(index + 1) : user;
/* 488:462 */         ntlm = new NtlmPasswordAuthentication(domain, user, password);
/* 489:    */       }
/* 490:465 */       req.getSession().setAttribute("npa-" + server, ntlm);
/* 491:    */     }
/* 492:467 */     else if (!this.credentialsSupplied)
/* 493:    */     {
/* 494:468 */       if (ssn != null) {
/* 495:469 */         ntlm = (NtlmPasswordAuthentication)ssn.getAttribute("npa-" + server);
/* 496:    */       }
/* 497:471 */       if (ntlm == null)
/* 498:    */       {
/* 499:472 */         resp.setHeader("WWW-Authenticate", "NTLM");
/* 500:473 */         if (offerBasic) {
/* 501:474 */           resp.addHeader("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
/* 502:    */         }
/* 503:476 */         resp.setHeader("Connection", "close");
/* 504:477 */         resp.setStatus(401);
/* 505:478 */         resp.flushBuffer();
/* 506:479 */         return;
/* 507:    */       }
/* 508:    */     }
/* 509:    */     try
/* 510:    */     {
/* 511:    */       SmbFile file;
/* 512:    */       SmbFile file;
/* 513:486 */       if (ntlm != null)
/* 514:    */       {
/* 515:487 */         file = new SmbFile("smb:/" + pathInfo, ntlm);
/* 516:    */       }
/* 517:    */       else
/* 518:    */       {
/* 519:    */         SmbFile file;
/* 520:488 */         if (server == null) {
/* 521:489 */           file = new SmbFile("smb://");
/* 522:    */         } else {
/* 523:491 */           file = new SmbFile("smb:/" + pathInfo);
/* 524:    */         }
/* 525:    */       }
/* 526:494 */       if (file.isDirectory()) {
/* 527:495 */         doDirectory(req, resp, file);
/* 528:    */       } else {
/* 529:497 */         doFile(req, resp, file);
/* 530:    */       }
/* 531:    */     }
/* 532:    */     catch (SmbAuthException sae)
/* 533:    */     {
/* 534:500 */       if (ssn != null) {
/* 535:501 */         ssn.removeAttribute("npa-" + server);
/* 536:    */       }
/* 537:503 */       if (sae.getNtStatus() == -1073741819)
/* 538:    */       {
/* 539:507 */         resp.sendRedirect(req.getRequestURL().toString());
/* 540:508 */         return;
/* 541:    */       }
/* 542:510 */       resp.setHeader("WWW-Authenticate", "NTLM");
/* 543:511 */       if (offerBasic) {
/* 544:512 */         resp.addHeader("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
/* 545:    */       }
/* 546:514 */       resp.setHeader("Connection", "close");
/* 547:515 */       resp.setStatus(401);
/* 548:516 */       resp.flushBuffer();
/* 549:517 */       return;
/* 550:    */     }
/* 551:    */     catch (DfsReferral dr)
/* 552:    */     {
/* 553:519 */       StringBuffer redir = req.getRequestURL();
/* 554:520 */       String qs = req.getQueryString();
/* 555:521 */       redir = new StringBuffer(redir.substring(0, redir.length() - req.getPathInfo().length()));
/* 556:522 */       redir.append('/');
/* 557:523 */       redir.append(dr.server);
/* 558:524 */       redir.append('/');
/* 559:525 */       redir.append(dr.share);
/* 560:526 */       redir.append('/');
/* 561:527 */       if (qs != null) {
/* 562:528 */         redir.append(req.getQueryString());
/* 563:    */       }
/* 564:530 */       resp.sendRedirect(redir.toString());
/* 565:531 */       resp.flushBuffer();
/* 566:532 */       return;
/* 567:    */     }
/* 568:    */   }
/* 569:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.http.NetworkExplorer
 * JD-Core Version:    0.7.0.1
 */