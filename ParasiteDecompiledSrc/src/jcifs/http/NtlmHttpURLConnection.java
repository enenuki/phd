/*   1:    */ package jcifs.http;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.net.Authenticator;
/*   8:    */ import java.net.HttpURLConnection;
/*   9:    */ import java.net.PasswordAuthentication;
/*  10:    */ import java.net.ProtocolException;
/*  11:    */ import java.net.URL;
/*  12:    */ import java.net.URLDecoder;
/*  13:    */ import java.security.Permission;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Collections;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import java.util.Map.Entry;
/*  21:    */ import java.util.Set;
/*  22:    */ import jcifs.Config;
/*  23:    */ import jcifs.ntlmssp.NtlmMessage;
/*  24:    */ import jcifs.ntlmssp.Type1Message;
/*  25:    */ import jcifs.ntlmssp.Type2Message;
/*  26:    */ import jcifs.ntlmssp.Type3Message;
/*  27:    */ import jcifs.util.Base64;
/*  28:    */ 
/*  29:    */ public class NtlmHttpURLConnection
/*  30:    */   extends HttpURLConnection
/*  31:    */ {
/*  32: 61 */   private static final int MAX_REDIRECTS = Integer.parseInt(System.getProperty("http.maxRedirects", "20"));
/*  33: 64 */   private static final int LM_COMPATIBILITY = Config.getInt("jcifs.smb.lmCompatibility", 0);
/*  34:    */   private static final String DEFAULT_DOMAIN;
/*  35:    */   private HttpURLConnection connection;
/*  36:    */   private Map requestProperties;
/*  37:    */   private Map headerFields;
/*  38:    */   private ByteArrayOutputStream cachedOutput;
/*  39:    */   private String authProperty;
/*  40:    */   private String authMethod;
/*  41:    */   private boolean handshakeComplete;
/*  42:    */   
/*  43:    */   static
/*  44:    */   {
/*  45: 84 */     String domain = System.getProperty("http.auth.ntlm.domain");
/*  46: 85 */     if (domain == null) {
/*  47: 85 */       domain = Type3Message.getDefaultDomain();
/*  48:    */     }
/*  49: 86 */     DEFAULT_DOMAIN = domain;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public NtlmHttpURLConnection(HttpURLConnection connection)
/*  53:    */   {
/*  54: 90 */     super(connection.getURL());
/*  55: 91 */     this.connection = connection;
/*  56: 92 */     this.requestProperties = new HashMap();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void connect()
/*  60:    */     throws IOException
/*  61:    */   {
/*  62: 96 */     if (this.connected) {
/*  63: 96 */       return;
/*  64:    */     }
/*  65: 97 */     this.connection.connect();
/*  66: 98 */     this.connected = true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void handshake()
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:102 */     if (this.handshakeComplete) {
/*  73:102 */       return;
/*  74:    */     }
/*  75:103 */     doHandshake();
/*  76:104 */     this.handshakeComplete = true;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public URL getURL()
/*  80:    */   {
/*  81:108 */     return this.connection.getURL();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getContentLength()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:113 */       handshake();
/*  89:    */     }
/*  90:    */     catch (IOException ex) {}
/*  91:115 */     return this.connection.getContentLength();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getContentType()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:120 */       handshake();
/*  99:    */     }
/* 100:    */     catch (IOException ex) {}
/* 101:122 */     return this.connection.getContentType();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getContentEncoding()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:127 */       handshake();
/* 109:    */     }
/* 110:    */     catch (IOException ex) {}
/* 111:129 */     return this.connection.getContentEncoding();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public long getExpiration()
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:134 */       handshake();
/* 119:    */     }
/* 120:    */     catch (IOException ex) {}
/* 121:136 */     return this.connection.getExpiration();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public long getDate()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:141 */       handshake();
/* 129:    */     }
/* 130:    */     catch (IOException ex) {}
/* 131:143 */     return this.connection.getDate();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public long getLastModified()
/* 135:    */   {
/* 136:    */     try
/* 137:    */     {
/* 138:148 */       handshake();
/* 139:    */     }
/* 140:    */     catch (IOException ex) {}
/* 141:150 */     return this.connection.getLastModified();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getHeaderField(String header)
/* 145:    */   {
/* 146:    */     try
/* 147:    */     {
/* 148:155 */       handshake();
/* 149:    */     }
/* 150:    */     catch (IOException ex) {}
/* 151:157 */     return this.connection.getHeaderField(header);
/* 152:    */   }
/* 153:    */   
/* 154:    */   private Map getHeaderFields0()
/* 155:    */   {
/* 156:161 */     if (this.headerFields != null) {
/* 157:161 */       return this.headerFields;
/* 158:    */     }
/* 159:162 */     Map map = new HashMap();
/* 160:163 */     String key = this.connection.getHeaderFieldKey(0);
/* 161:164 */     String value = this.connection.getHeaderField(0);
/* 162:165 */     for (int i = 1; (key != null) || (value != null); i++)
/* 163:    */     {
/* 164:166 */       List values = (List)map.get(key);
/* 165:167 */       if (values == null)
/* 166:    */       {
/* 167:168 */         values = new ArrayList();
/* 168:169 */         map.put(key, values);
/* 169:    */       }
/* 170:171 */       values.add(value);
/* 171:172 */       key = this.connection.getHeaderFieldKey(i);
/* 172:173 */       value = this.connection.getHeaderField(i);
/* 173:    */     }
/* 174:175 */     Iterator entries = map.entrySet().iterator();
/* 175:176 */     while (entries.hasNext())
/* 176:    */     {
/* 177:177 */       Map.Entry entry = (Map.Entry)entries.next();
/* 178:178 */       entry.setValue(Collections.unmodifiableList((List)entry.getValue()));
/* 179:    */     }
/* 180:181 */     return this.headerFields = Collections.unmodifiableMap(map);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Map getHeaderFields()
/* 184:    */   {
/* 185:185 */     if (this.headerFields != null) {
/* 186:185 */       return this.headerFields;
/* 187:    */     }
/* 188:    */     try
/* 189:    */     {
/* 190:187 */       handshake();
/* 191:    */     }
/* 192:    */     catch (IOException ex) {}
/* 193:189 */     return getHeaderFields0();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int getHeaderFieldInt(String header, int def)
/* 197:    */   {
/* 198:    */     try
/* 199:    */     {
/* 200:194 */       handshake();
/* 201:    */     }
/* 202:    */     catch (IOException ex) {}
/* 203:196 */     return this.connection.getHeaderFieldInt(header, def);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public long getHeaderFieldDate(String header, long def)
/* 207:    */   {
/* 208:    */     try
/* 209:    */     {
/* 210:201 */       handshake();
/* 211:    */     }
/* 212:    */     catch (IOException ex) {}
/* 213:203 */     return this.connection.getHeaderFieldDate(header, def);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getHeaderFieldKey(int index)
/* 217:    */   {
/* 218:    */     try
/* 219:    */     {
/* 220:208 */       handshake();
/* 221:    */     }
/* 222:    */     catch (IOException ex) {}
/* 223:210 */     return this.connection.getHeaderFieldKey(index);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String getHeaderField(int index)
/* 227:    */   {
/* 228:    */     try
/* 229:    */     {
/* 230:215 */       handshake();
/* 231:    */     }
/* 232:    */     catch (IOException ex) {}
/* 233:217 */     return this.connection.getHeaderField(index);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Object getContent()
/* 237:    */     throws IOException
/* 238:    */   {
/* 239:    */     try
/* 240:    */     {
/* 241:222 */       handshake();
/* 242:    */     }
/* 243:    */     catch (IOException ex) {}
/* 244:224 */     return this.connection.getContent();
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Object getContent(Class[] classes)
/* 248:    */     throws IOException
/* 249:    */   {
/* 250:    */     try
/* 251:    */     {
/* 252:229 */       handshake();
/* 253:    */     }
/* 254:    */     catch (IOException ex) {}
/* 255:231 */     return this.connection.getContent(classes);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Permission getPermission()
/* 259:    */     throws IOException
/* 260:    */   {
/* 261:235 */     return this.connection.getPermission();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public InputStream getInputStream()
/* 265:    */     throws IOException
/* 266:    */   {
/* 267:    */     try
/* 268:    */     {
/* 269:240 */       handshake();
/* 270:    */     }
/* 271:    */     catch (IOException ex) {}
/* 272:242 */     return this.connection.getInputStream();
/* 273:    */   }
/* 274:    */   
/* 275:    */   public OutputStream getOutputStream()
/* 276:    */     throws IOException
/* 277:    */   {
/* 278:    */     try
/* 279:    */     {
/* 280:247 */       connect();
/* 281:    */     }
/* 282:    */     catch (IOException ex) {}
/* 283:249 */     OutputStream output = this.connection.getOutputStream();
/* 284:250 */     this.cachedOutput = new ByteArrayOutputStream();
/* 285:251 */     return new CacheStream(output, this.cachedOutput);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public String toString()
/* 289:    */   {
/* 290:255 */     return this.connection.toString();
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setDoInput(boolean doInput)
/* 294:    */   {
/* 295:259 */     this.connection.setDoInput(doInput);
/* 296:260 */     this.doInput = doInput;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public boolean getDoInput()
/* 300:    */   {
/* 301:264 */     return this.connection.getDoInput();
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setDoOutput(boolean doOutput)
/* 305:    */   {
/* 306:268 */     this.connection.setDoOutput(doOutput);
/* 307:269 */     this.doOutput = doOutput;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean getDoOutput()
/* 311:    */   {
/* 312:273 */     return this.connection.getDoOutput();
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setAllowUserInteraction(boolean allowUserInteraction)
/* 316:    */   {
/* 317:277 */     this.connection.setAllowUserInteraction(allowUserInteraction);
/* 318:278 */     this.allowUserInteraction = allowUserInteraction;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public boolean getAllowUserInteraction()
/* 322:    */   {
/* 323:282 */     return this.connection.getAllowUserInteraction();
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setUseCaches(boolean useCaches)
/* 327:    */   {
/* 328:286 */     this.connection.setUseCaches(useCaches);
/* 329:287 */     this.useCaches = useCaches;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public boolean getUseCaches()
/* 333:    */   {
/* 334:291 */     return this.connection.getUseCaches();
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setIfModifiedSince(long ifModifiedSince)
/* 338:    */   {
/* 339:295 */     this.connection.setIfModifiedSince(ifModifiedSince);
/* 340:296 */     this.ifModifiedSince = ifModifiedSince;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public long getIfModifiedSince()
/* 344:    */   {
/* 345:300 */     return this.connection.getIfModifiedSince();
/* 346:    */   }
/* 347:    */   
/* 348:    */   public boolean getDefaultUseCaches()
/* 349:    */   {
/* 350:304 */     return this.connection.getDefaultUseCaches();
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setDefaultUseCaches(boolean defaultUseCaches)
/* 354:    */   {
/* 355:308 */     this.connection.setDefaultUseCaches(defaultUseCaches);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void setRequestProperty(String key, String value)
/* 359:    */   {
/* 360:312 */     if (key == null) {
/* 361:312 */       throw new NullPointerException();
/* 362:    */     }
/* 363:313 */     List values = new ArrayList();
/* 364:314 */     values.add(value);
/* 365:315 */     boolean found = false;
/* 366:316 */     Iterator entries = this.requestProperties.entrySet().iterator();
/* 367:317 */     while (entries.hasNext())
/* 368:    */     {
/* 369:318 */       Map.Entry entry = (Map.Entry)entries.next();
/* 370:319 */       if (key.equalsIgnoreCase((String)entry.getKey()))
/* 371:    */       {
/* 372:320 */         entry.setValue(values);
/* 373:321 */         found = true;
/* 374:322 */         break;
/* 375:    */       }
/* 376:    */     }
/* 377:325 */     if (!found) {
/* 378:325 */       this.requestProperties.put(key, values);
/* 379:    */     }
/* 380:326 */     this.connection.setRequestProperty(key, value);
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void addRequestProperty(String key, String value)
/* 384:    */   {
/* 385:330 */     if (key == null) {
/* 386:330 */       throw new NullPointerException();
/* 387:    */     }
/* 388:331 */     List values = null;
/* 389:332 */     Iterator entries = this.requestProperties.entrySet().iterator();
/* 390:333 */     while (entries.hasNext())
/* 391:    */     {
/* 392:334 */       Map.Entry entry = (Map.Entry)entries.next();
/* 393:335 */       if (key.equalsIgnoreCase((String)entry.getKey()))
/* 394:    */       {
/* 395:336 */         values = (List)entry.getValue();
/* 396:337 */         values.add(value);
/* 397:338 */         break;
/* 398:    */       }
/* 399:    */     }
/* 400:341 */     if (values == null)
/* 401:    */     {
/* 402:342 */       values = new ArrayList();
/* 403:343 */       values.add(value);
/* 404:344 */       this.requestProperties.put(key, values);
/* 405:    */     }
/* 406:347 */     StringBuffer buffer = new StringBuffer();
/* 407:348 */     Iterator propertyValues = values.iterator();
/* 408:349 */     while (propertyValues.hasNext())
/* 409:    */     {
/* 410:350 */       buffer.append(propertyValues.next());
/* 411:351 */       if (propertyValues.hasNext()) {
/* 412:352 */         buffer.append(", ");
/* 413:    */       }
/* 414:    */     }
/* 415:355 */     this.connection.setRequestProperty(key, buffer.toString());
/* 416:    */   }
/* 417:    */   
/* 418:    */   public String getRequestProperty(String key)
/* 419:    */   {
/* 420:359 */     return this.connection.getRequestProperty(key);
/* 421:    */   }
/* 422:    */   
/* 423:    */   public Map getRequestProperties()
/* 424:    */   {
/* 425:363 */     Map map = new HashMap();
/* 426:364 */     Iterator entries = this.requestProperties.entrySet().iterator();
/* 427:365 */     while (entries.hasNext())
/* 428:    */     {
/* 429:366 */       Map.Entry entry = (Map.Entry)entries.next();
/* 430:367 */       map.put(entry.getKey(), Collections.unmodifiableList((List)entry.getValue()));
/* 431:    */     }
/* 432:370 */     return Collections.unmodifiableMap(map);
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setInstanceFollowRedirects(boolean instanceFollowRedirects)
/* 436:    */   {
/* 437:374 */     this.connection.setInstanceFollowRedirects(instanceFollowRedirects);
/* 438:    */   }
/* 439:    */   
/* 440:    */   public boolean getInstanceFollowRedirects()
/* 441:    */   {
/* 442:378 */     return this.connection.getInstanceFollowRedirects();
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setRequestMethod(String requestMethod)
/* 446:    */     throws ProtocolException
/* 447:    */   {
/* 448:383 */     this.connection.setRequestMethod(requestMethod);
/* 449:384 */     this.method = requestMethod;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public String getRequestMethod()
/* 453:    */   {
/* 454:388 */     return this.connection.getRequestMethod();
/* 455:    */   }
/* 456:    */   
/* 457:    */   public int getResponseCode()
/* 458:    */     throws IOException
/* 459:    */   {
/* 460:    */     try
/* 461:    */     {
/* 462:393 */       handshake();
/* 463:    */     }
/* 464:    */     catch (IOException ex) {}
/* 465:395 */     return this.connection.getResponseCode();
/* 466:    */   }
/* 467:    */   
/* 468:    */   public String getResponseMessage()
/* 469:    */     throws IOException
/* 470:    */   {
/* 471:    */     try
/* 472:    */     {
/* 473:400 */       handshake();
/* 474:    */     }
/* 475:    */     catch (IOException ex) {}
/* 476:402 */     return this.connection.getResponseMessage();
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void disconnect()
/* 480:    */   {
/* 481:406 */     this.connection.disconnect();
/* 482:407 */     this.handshakeComplete = false;
/* 483:408 */     this.connected = false;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public boolean usingProxy()
/* 487:    */   {
/* 488:412 */     return this.connection.usingProxy();
/* 489:    */   }
/* 490:    */   
/* 491:    */   public InputStream getErrorStream()
/* 492:    */   {
/* 493:    */     try
/* 494:    */     {
/* 495:417 */       handshake();
/* 496:    */     }
/* 497:    */     catch (IOException ex) {}
/* 498:419 */     return this.connection.getErrorStream();
/* 499:    */   }
/* 500:    */   
/* 501:    */   private int parseResponseCode()
/* 502:    */     throws IOException
/* 503:    */   {
/* 504:    */     try
/* 505:    */     {
/* 506:424 */       String response = this.connection.getHeaderField(0);
/* 507:425 */       int index = response.indexOf(' ');
/* 508:426 */       while (response.charAt(index) == ' ') {
/* 509:426 */         index++;
/* 510:    */       }
/* 511:427 */       return Integer.parseInt(response.substring(index, index + 3));
/* 512:    */     }
/* 513:    */     catch (Exception ex)
/* 514:    */     {
/* 515:429 */       throw new IOException(ex.getMessage());
/* 516:    */     }
/* 517:    */   }
/* 518:    */   
/* 519:    */   private void doHandshake()
/* 520:    */     throws IOException
/* 521:    */   {
/* 522:434 */     connect();
/* 523:    */     try
/* 524:    */     {
/* 525:436 */       int response = parseResponseCode();
/* 526:437 */       if ((response != 401) && (response != 407)) {
/* 527:    */         return;
/* 528:    */       }
/* 529:440 */       Type1Message type1 = (Type1Message)attemptNegotiation(response);
/* 530:441 */       if (type1 == null) {
/* 531:    */         return;
/* 532:    */       }
/* 533:442 */       int attempt = 0;
/* 534:443 */       while (attempt < MAX_REDIRECTS)
/* 535:    */       {
/* 536:444 */         this.connection.setRequestProperty(this.authProperty, this.authMethod + ' ' + Base64.encode(type1.toByteArray()));
/* 537:    */         
/* 538:446 */         this.connection.connect();
/* 539:447 */         response = parseResponseCode();
/* 540:448 */         if ((response != 401) && (response != 407)) {
/* 541:    */           return;
/* 542:    */         }
/* 543:452 */         Type3Message type3 = (Type3Message)attemptNegotiation(response);
/* 544:454 */         if (type3 == null) {
/* 545:    */           return;
/* 546:    */         }
/* 547:455 */         this.connection.setRequestProperty(this.authProperty, this.authMethod + ' ' + Base64.encode(type3.toByteArray()));
/* 548:    */         
/* 549:457 */         this.connection.connect();
/* 550:458 */         if ((this.cachedOutput != null) && (this.doOutput))
/* 551:    */         {
/* 552:459 */           OutputStream output = this.connection.getOutputStream();
/* 553:460 */           this.cachedOutput.writeTo(output);
/* 554:461 */           output.flush();
/* 555:    */         }
/* 556:463 */         response = parseResponseCode();
/* 557:464 */         if ((response != 401) && (response != 407)) {
/* 558:    */           return;
/* 559:    */         }
/* 560:468 */         attempt++;
/* 561:469 */         if ((!this.allowUserInteraction) || (attempt >= MAX_REDIRECTS)) {
/* 562:    */           break;
/* 563:    */         }
/* 564:470 */         reconnect();
/* 565:    */       }
/* 566:475 */       throw new IOException("Unable to negotiate NTLM authentication.");
/* 567:    */     }
/* 568:    */     finally
/* 569:    */     {
/* 570:477 */       this.cachedOutput = null;
/* 571:    */     }
/* 572:    */   }
/* 573:    */   
/* 574:    */   private NtlmMessage attemptNegotiation(int response)
/* 575:    */     throws IOException
/* 576:    */   {
/* 577:482 */     this.authProperty = null;
/* 578:483 */     this.authMethod = null;
/* 579:484 */     InputStream errorStream = this.connection.getErrorStream();
/* 580:485 */     if ((errorStream != null) && (errorStream.available() != 0))
/* 581:    */     {
/* 582:487 */       byte[] buf = new byte[1024];
/* 583:    */       int count;
/* 584:488 */       while ((count = errorStream.read(buf, 0, 1024)) != -1) {}
/* 585:    */     }
/* 586:    */     String authHeader;
/* 587:491 */     if (response == 401)
/* 588:    */     {
/* 589:492 */       String authHeader = "WWW-Authenticate";
/* 590:493 */       this.authProperty = "Authorization";
/* 591:    */     }
/* 592:    */     else
/* 593:    */     {
/* 594:495 */       authHeader = "Proxy-Authenticate";
/* 595:496 */       this.authProperty = "Proxy-Authorization";
/* 596:    */     }
/* 597:498 */     String authorization = null;
/* 598:499 */     List methods = (List)getHeaderFields0().get(authHeader);
/* 599:500 */     if (methods == null) {
/* 600:500 */       return null;
/* 601:    */     }
/* 602:501 */     Iterator iterator = methods.iterator();
/* 603:502 */     while (iterator.hasNext())
/* 604:    */     {
/* 605:503 */       String currentAuthMethod = (String)iterator.next();
/* 606:504 */       if (currentAuthMethod.startsWith("NTLM"))
/* 607:    */       {
/* 608:505 */         if (currentAuthMethod.length() == 4)
/* 609:    */         {
/* 610:506 */           this.authMethod = "NTLM";
/* 611:507 */           break;
/* 612:    */         }
/* 613:509 */         if (currentAuthMethod.indexOf(' ') == 4)
/* 614:    */         {
/* 615:510 */           this.authMethod = "NTLM";
/* 616:511 */           authorization = currentAuthMethod.substring(5).trim();
/* 617:512 */           break;
/* 618:    */         }
/* 619:    */       }
/* 620:513 */       else if (currentAuthMethod.startsWith("Negotiate"))
/* 621:    */       {
/* 622:514 */         if (currentAuthMethod.length() == 9)
/* 623:    */         {
/* 624:515 */           this.authMethod = "Negotiate";
/* 625:516 */           break;
/* 626:    */         }
/* 627:518 */         if (currentAuthMethod.indexOf(' ') == 9)
/* 628:    */         {
/* 629:519 */           this.authMethod = "Negotiate";
/* 630:520 */           authorization = currentAuthMethod.substring(10).trim();
/* 631:521 */           break;
/* 632:    */         }
/* 633:    */       }
/* 634:    */     }
/* 635:524 */     if (this.authMethod == null) {
/* 636:524 */       return null;
/* 637:    */     }
/* 638:525 */     NtlmMessage message = authorization != null ? new Type2Message(Base64.decode(authorization)) : null;
/* 639:    */     
/* 640:527 */     reconnect();
/* 641:528 */     if (message == null)
/* 642:    */     {
/* 643:529 */       message = new Type1Message();
/* 644:530 */       if (LM_COMPATIBILITY > 2) {
/* 645:531 */         message.setFlag(4, true);
/* 646:    */       }
/* 647:    */     }
/* 648:    */     else
/* 649:    */     {
/* 650:534 */       String domain = DEFAULT_DOMAIN;
/* 651:535 */       String user = Type3Message.getDefaultUser();
/* 652:536 */       String password = Type3Message.getDefaultPassword();
/* 653:537 */       String userInfo = this.url.getUserInfo();
/* 654:538 */       if (userInfo != null)
/* 655:    */       {
/* 656:539 */         userInfo = URLDecoder.decode(userInfo);
/* 657:540 */         int index = userInfo.indexOf(':');
/* 658:541 */         user = index != -1 ? userInfo.substring(0, index) : userInfo;
/* 659:542 */         if (index != -1) {
/* 660:542 */           password = userInfo.substring(index + 1);
/* 661:    */         }
/* 662:543 */         index = user.indexOf('\\');
/* 663:544 */         if (index == -1) {
/* 664:544 */           index = user.indexOf('/');
/* 665:    */         }
/* 666:545 */         domain = index != -1 ? user.substring(0, index) : domain;
/* 667:546 */         user = index != -1 ? user.substring(index + 1) : user;
/* 668:    */       }
/* 669:548 */       if (user == null)
/* 670:    */       {
/* 671:549 */         if (!this.allowUserInteraction) {
/* 672:549 */           return null;
/* 673:    */         }
/* 674:    */         try
/* 675:    */         {
/* 676:551 */           URL url = getURL();
/* 677:552 */           String protocol = url.getProtocol();
/* 678:553 */           int port = url.getPort();
/* 679:554 */           if (port == -1) {
/* 680:555 */             port = "https".equalsIgnoreCase(protocol) ? 443 : 80;
/* 681:    */           }
/* 682:557 */           PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(null, port, protocol, "", this.authMethod);
/* 683:560 */           if (auth == null) {
/* 684:560 */             return null;
/* 685:    */           }
/* 686:561 */           user = auth.getUserName();
/* 687:562 */           password = new String(auth.getPassword());
/* 688:    */         }
/* 689:    */         catch (Exception ex) {}
/* 690:    */       }
/* 691:565 */       Type2Message type2 = (Type2Message)message;
/* 692:566 */       message = new Type3Message(type2, password, domain, user, Type3Message.getDefaultWorkstation(), 0);
/* 693:    */     }
/* 694:569 */     return message;
/* 695:    */   }
/* 696:    */   
/* 697:    */   private void reconnect()
/* 698:    */     throws IOException
/* 699:    */   {
/* 700:573 */     this.connection = ((HttpURLConnection)this.connection.getURL().openConnection());
/* 701:574 */     this.connection.setRequestMethod(this.method);
/* 702:575 */     this.headerFields = null;
/* 703:576 */     Iterator properties = this.requestProperties.entrySet().iterator();
/* 704:577 */     while (properties.hasNext())
/* 705:    */     {
/* 706:578 */       Map.Entry property = (Map.Entry)properties.next();
/* 707:579 */       String key = (String)property.getKey();
/* 708:580 */       StringBuffer value = new StringBuffer();
/* 709:581 */       Iterator values = ((List)property.getValue()).iterator();
/* 710:582 */       while (values.hasNext())
/* 711:    */       {
/* 712:583 */         value.append(values.next());
/* 713:584 */         if (values.hasNext()) {
/* 714:584 */           value.append(", ");
/* 715:    */         }
/* 716:    */       }
/* 717:586 */       this.connection.setRequestProperty(key, value.toString());
/* 718:    */     }
/* 719:588 */     this.connection.setAllowUserInteraction(this.allowUserInteraction);
/* 720:589 */     this.connection.setDoInput(this.doInput);
/* 721:590 */     this.connection.setDoOutput(this.doOutput);
/* 722:591 */     this.connection.setIfModifiedSince(this.ifModifiedSince);
/* 723:592 */     this.connection.setUseCaches(this.useCaches);
/* 724:    */   }
/* 725:    */   
/* 726:    */   private static class CacheStream
/* 727:    */     extends OutputStream
/* 728:    */   {
/* 729:    */     private final OutputStream stream;
/* 730:    */     private final OutputStream collector;
/* 731:    */     
/* 732:    */     public CacheStream(OutputStream stream, OutputStream collector)
/* 733:    */     {
/* 734:602 */       this.stream = stream;
/* 735:603 */       this.collector = collector;
/* 736:    */     }
/* 737:    */     
/* 738:    */     public void close()
/* 739:    */       throws IOException
/* 740:    */     {
/* 741:607 */       this.stream.close();
/* 742:608 */       this.collector.close();
/* 743:    */     }
/* 744:    */     
/* 745:    */     public void flush()
/* 746:    */       throws IOException
/* 747:    */     {
/* 748:612 */       this.stream.flush();
/* 749:613 */       this.collector.flush();
/* 750:    */     }
/* 751:    */     
/* 752:    */     public void write(byte[] b)
/* 753:    */       throws IOException
/* 754:    */     {
/* 755:617 */       this.stream.write(b);
/* 756:618 */       this.collector.write(b);
/* 757:    */     }
/* 758:    */     
/* 759:    */     public void write(byte[] b, int off, int len)
/* 760:    */       throws IOException
/* 761:    */     {
/* 762:622 */       this.stream.write(b, off, len);
/* 763:623 */       this.collector.write(b, off, len);
/* 764:    */     }
/* 765:    */     
/* 766:    */     public void write(int b)
/* 767:    */       throws IOException
/* 768:    */     {
/* 769:627 */       this.stream.write(b);
/* 770:628 */       this.collector.write(b);
/* 771:    */     }
/* 772:    */   }
/* 773:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.http.NtlmHttpURLConnection
 * JD-Core Version:    0.7.0.1
 */