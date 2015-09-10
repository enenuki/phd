/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.OutputStreamWriter;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import java.io.Writer;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.Hashtable;
/*   9:    */ import java.util.Properties;
/*  10:    */ import javax.mail.Authenticator;
/*  11:    */ import javax.mail.Message;
/*  12:    */ import javax.mail.Message.RecipientType;
/*  13:    */ import javax.mail.MessagingException;
/*  14:    */ import javax.mail.Multipart;
/*  15:    */ import javax.mail.Part;
/*  16:    */ import javax.mail.PasswordAuthentication;
/*  17:    */ import javax.mail.Session;
/*  18:    */ import javax.mail.Transport;
/*  19:    */ import javax.mail.internet.AddressException;
/*  20:    */ import javax.mail.internet.InternetAddress;
/*  21:    */ import javax.mail.internet.InternetHeaders;
/*  22:    */ import javax.mail.internet.MimeBodyPart;
/*  23:    */ import javax.mail.internet.MimeMessage;
/*  24:    */ import javax.mail.internet.MimeMultipart;
/*  25:    */ import javax.mail.internet.MimeUtility;
/*  26:    */ import org.apache.log4j.AppenderSkeleton;
/*  27:    */ import org.apache.log4j.Layout;
/*  28:    */ import org.apache.log4j.helpers.CyclicBuffer;
/*  29:    */ import org.apache.log4j.helpers.LogLog;
/*  30:    */ import org.apache.log4j.helpers.OptionConverter;
/*  31:    */ import org.apache.log4j.spi.ErrorHandler;
/*  32:    */ import org.apache.log4j.spi.LoggingEvent;
/*  33:    */ import org.apache.log4j.spi.OptionHandler;
/*  34:    */ import org.apache.log4j.spi.TriggeringEventEvaluator;
/*  35:    */ import org.apache.log4j.xml.DOMConfigurator;
/*  36:    */ import org.apache.log4j.xml.UnrecognizedElementHandler;
/*  37:    */ import org.w3c.dom.Element;
/*  38:    */ import org.w3c.dom.Node;
/*  39:    */ 
/*  40:    */ public class SMTPAppender
/*  41:    */   extends AppenderSkeleton
/*  42:    */   implements UnrecognizedElementHandler
/*  43:    */ {
/*  44:    */   private String to;
/*  45:    */   private String cc;
/*  46:    */   private String bcc;
/*  47:    */   private String from;
/*  48:    */   private String replyTo;
/*  49:    */   private String subject;
/*  50:    */   private String smtpHost;
/*  51:    */   private String smtpUsername;
/*  52:    */   private String smtpPassword;
/*  53:    */   private String smtpProtocol;
/*  54:100 */   private int smtpPort = -1;
/*  55:101 */   private boolean smtpDebug = false;
/*  56:102 */   private int bufferSize = 512;
/*  57:103 */   private boolean locationInfo = false;
/*  58:104 */   private boolean sendOnClose = false;
/*  59:106 */   protected CyclicBuffer cb = new CyclicBuffer(this.bufferSize);
/*  60:    */   protected Message msg;
/*  61:    */   protected TriggeringEventEvaluator evaluator;
/*  62:    */   
/*  63:    */   public SMTPAppender()
/*  64:    */   {
/*  65:119 */     this(new DefaultEvaluator());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public SMTPAppender(TriggeringEventEvaluator evaluator)
/*  69:    */   {
/*  70:128 */     this.evaluator = evaluator;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void activateOptions()
/*  74:    */   {
/*  75:137 */     Session session = createSession();
/*  76:138 */     this.msg = new MimeMessage(session);
/*  77:    */     try
/*  78:    */     {
/*  79:141 */       addressMessage(this.msg);
/*  80:142 */       if (this.subject != null) {
/*  81:    */         try
/*  82:    */         {
/*  83:144 */           this.msg.setSubject(MimeUtility.encodeText(this.subject, "UTF-8", null));
/*  84:    */         }
/*  85:    */         catch (UnsupportedEncodingException ex)
/*  86:    */         {
/*  87:146 */           LogLog.error("Unable to encode SMTP subject", ex);
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91:    */     catch (MessagingException e)
/*  92:    */     {
/*  93:150 */       LogLog.error("Could not activate SMTPAppender options.", e);
/*  94:    */     }
/*  95:153 */     if ((this.evaluator instanceof OptionHandler)) {
/*  96:154 */       ((OptionHandler)this.evaluator).activateOptions();
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected void addressMessage(Message msg)
/* 101:    */     throws MessagingException
/* 102:    */   {
/* 103:165 */     if (this.from != null) {
/* 104:166 */       msg.setFrom(getAddress(this.from));
/* 105:    */     } else {
/* 106:168 */       msg.setFrom();
/* 107:    */     }
/* 108:172 */     if ((this.replyTo != null) && (this.replyTo.length() > 0)) {
/* 109:173 */       msg.setReplyTo(parseAddress(this.replyTo));
/* 110:    */     }
/* 111:176 */     if ((this.to != null) && (this.to.length() > 0)) {
/* 112:177 */       msg.setRecipients(Message.RecipientType.TO, parseAddress(this.to));
/* 113:    */     }
/* 114:181 */     if ((this.cc != null) && (this.cc.length() > 0)) {
/* 115:182 */       msg.setRecipients(Message.RecipientType.CC, parseAddress(this.cc));
/* 116:    */     }
/* 117:186 */     if ((this.bcc != null) && (this.bcc.length() > 0)) {
/* 118:187 */       msg.setRecipients(Message.RecipientType.BCC, parseAddress(this.bcc));
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected Session createSession()
/* 123:    */   {
/* 124:197 */     Properties props = null;
/* 125:    */     try
/* 126:    */     {
/* 127:199 */       props = new Properties(System.getProperties());
/* 128:    */     }
/* 129:    */     catch (SecurityException ex)
/* 130:    */     {
/* 131:201 */       props = new Properties();
/* 132:    */     }
/* 133:204 */     String prefix = "mail.smtp";
/* 134:205 */     if (this.smtpProtocol != null)
/* 135:    */     {
/* 136:206 */       props.put("mail.transport.protocol", this.smtpProtocol);
/* 137:207 */       prefix = "mail." + this.smtpProtocol;
/* 138:    */     }
/* 139:209 */     if (this.smtpHost != null) {
/* 140:210 */       props.put(prefix + ".host", this.smtpHost);
/* 141:    */     }
/* 142:212 */     if (this.smtpPort > 0) {
/* 143:213 */       props.put(prefix + ".port", String.valueOf(this.smtpPort));
/* 144:    */     }
/* 145:216 */     Authenticator auth = null;
/* 146:217 */     if ((this.smtpPassword != null) && (this.smtpUsername != null))
/* 147:    */     {
/* 148:218 */       props.put(prefix + ".auth", "true");
/* 149:219 */       auth = new Authenticator()
/* 150:    */       {
/* 151:    */         protected PasswordAuthentication getPasswordAuthentication()
/* 152:    */         {
/* 153:221 */           return new PasswordAuthentication(SMTPAppender.this.smtpUsername, SMTPAppender.this.smtpPassword);
/* 154:    */         }
/* 155:    */       };
/* 156:    */     }
/* 157:225 */     Session session = Session.getInstance(props, auth);
/* 158:226 */     if (this.smtpProtocol != null) {
/* 159:227 */       session.setProtocolForAddress("rfc822", this.smtpProtocol);
/* 160:    */     }
/* 161:229 */     if (this.smtpDebug) {
/* 162:230 */       session.setDebug(this.smtpDebug);
/* 163:    */     }
/* 164:232 */     return session;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void append(LoggingEvent event)
/* 168:    */   {
/* 169:242 */     if (!checkEntryConditions()) {
/* 170:243 */       return;
/* 171:    */     }
/* 172:246 */     event.getThreadName();
/* 173:247 */     event.getNDC();
/* 174:248 */     event.getMDCCopy();
/* 175:249 */     if (this.locationInfo) {
/* 176:250 */       event.getLocationInformation();
/* 177:    */     }
/* 178:252 */     event.getRenderedMessage();
/* 179:253 */     event.getThrowableStrRep();
/* 180:254 */     this.cb.add(event);
/* 181:255 */     if (this.evaluator.isTriggeringEvent(event)) {
/* 182:256 */       sendBuffer();
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected boolean checkEntryConditions()
/* 187:    */   {
/* 188:268 */     if (this.msg == null)
/* 189:    */     {
/* 190:269 */       this.errorHandler.error("Message object not configured.");
/* 191:270 */       return false;
/* 192:    */     }
/* 193:273 */     if (this.evaluator == null)
/* 194:    */     {
/* 195:274 */       this.errorHandler.error("No TriggeringEventEvaluator is set for appender [" + this.name + "].");
/* 196:    */       
/* 197:276 */       return false;
/* 198:    */     }
/* 199:280 */     if (this.layout == null)
/* 200:    */     {
/* 201:281 */       this.errorHandler.error("No layout set for appender named [" + this.name + "].");
/* 202:282 */       return false;
/* 203:    */     }
/* 204:284 */     return true;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public synchronized void close()
/* 208:    */   {
/* 209:291 */     this.closed = true;
/* 210:292 */     if ((this.sendOnClose) && (this.cb.length() > 0)) {
/* 211:293 */       sendBuffer();
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   InternetAddress getAddress(String addressStr)
/* 216:    */   {
/* 217:    */     try
/* 218:    */     {
/* 219:299 */       return new InternetAddress(addressStr);
/* 220:    */     }
/* 221:    */     catch (AddressException e)
/* 222:    */     {
/* 223:301 */       this.errorHandler.error("Could not parse address [" + addressStr + "].", e, 6);
/* 224:    */     }
/* 225:303 */     return null;
/* 226:    */   }
/* 227:    */   
/* 228:    */   InternetAddress[] parseAddress(String addressStr)
/* 229:    */   {
/* 230:    */     try
/* 231:    */     {
/* 232:309 */       return InternetAddress.parse(addressStr, true);
/* 233:    */     }
/* 234:    */     catch (AddressException e)
/* 235:    */     {
/* 236:311 */       this.errorHandler.error("Could not parse address [" + addressStr + "].", e, 6);
/* 237:    */     }
/* 238:313 */     return null;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getTo()
/* 242:    */   {
/* 243:322 */     return this.to;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public boolean requiresLayout()
/* 247:    */   {
/* 248:331 */     return true;
/* 249:    */   }
/* 250:    */   
/* 251:    */   protected String formatBody()
/* 252:    */   {
/* 253:343 */     StringBuffer sbuf = new StringBuffer();
/* 254:344 */     String t = this.layout.getHeader();
/* 255:345 */     if (t != null) {
/* 256:346 */       sbuf.append(t);
/* 257:    */     }
/* 258:347 */     int len = this.cb.length();
/* 259:348 */     for (int i = 0; i < len; i++)
/* 260:    */     {
/* 261:350 */       LoggingEvent event = this.cb.get();
/* 262:351 */       sbuf.append(this.layout.format(event));
/* 263:352 */       if (this.layout.ignoresThrowable())
/* 264:    */       {
/* 265:353 */         String[] s = event.getThrowableStrRep();
/* 266:354 */         if (s != null) {
/* 267:355 */           for (int j = 0; j < s.length; j++)
/* 268:    */           {
/* 269:356 */             sbuf.append(s[j]);
/* 270:357 */             sbuf.append(Layout.LINE_SEP);
/* 271:    */           }
/* 272:    */         }
/* 273:    */       }
/* 274:    */     }
/* 275:362 */     t = this.layout.getFooter();
/* 276:363 */     if (t != null) {
/* 277:364 */       sbuf.append(t);
/* 278:    */     }
/* 279:367 */     return sbuf.toString();
/* 280:    */   }
/* 281:    */   
/* 282:    */   protected void sendBuffer()
/* 283:    */   {
/* 284:    */     try
/* 285:    */     {
/* 286:377 */       String s = formatBody();
/* 287:378 */       boolean allAscii = true;
/* 288:379 */       for (int i = 0; (i < s.length()) && (allAscii); i++) {
/* 289:380 */         allAscii = s.charAt(i) <= '';
/* 290:    */       }
/* 291:    */       MimeBodyPart part;
/* 292:383 */       if (allAscii)
/* 293:    */       {
/* 294:384 */         MimeBodyPart part = new MimeBodyPart();
/* 295:385 */         part.setContent(s, this.layout.getContentType());
/* 296:    */       }
/* 297:    */       else
/* 298:    */       {
/* 299:    */         try
/* 300:    */         {
/* 301:388 */           ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 302:389 */           Writer writer = new OutputStreamWriter(MimeUtility.encode(os, "quoted-printable"), "UTF-8");
/* 303:    */           
/* 304:391 */           writer.write(s);
/* 305:392 */           writer.close();
/* 306:393 */           InternetHeaders headers = new InternetHeaders();
/* 307:394 */           headers.setHeader("Content-Type", this.layout.getContentType() + "; charset=UTF-8");
/* 308:395 */           headers.setHeader("Content-Transfer-Encoding", "quoted-printable");
/* 309:396 */           part = new MimeBodyPart(headers, os.toByteArray());
/* 310:    */         }
/* 311:    */         catch (Exception ex)
/* 312:    */         {
/* 313:398 */           StringBuffer sbuf = new StringBuffer(s);
/* 314:399 */           for (int i = 0; i < sbuf.length(); i++) {
/* 315:400 */             if (sbuf.charAt(i) >= '') {
/* 316:401 */               sbuf.setCharAt(i, '?');
/* 317:    */             }
/* 318:    */           }
/* 319:404 */           part = new MimeBodyPart();
/* 320:405 */           part.setContent(sbuf.toString(), this.layout.getContentType());
/* 321:    */         }
/* 322:    */       }
/* 323:411 */       Multipart mp = new MimeMultipart();
/* 324:412 */       mp.addBodyPart(part);
/* 325:413 */       this.msg.setContent(mp);
/* 326:    */       
/* 327:415 */       this.msg.setSentDate(new Date());
/* 328:416 */       Transport.send(this.msg);
/* 329:    */     }
/* 330:    */     catch (MessagingException e)
/* 331:    */     {
/* 332:418 */       LogLog.error("Error occured while sending e-mail notification.", e);
/* 333:    */     }
/* 334:    */     catch (RuntimeException e)
/* 335:    */     {
/* 336:420 */       LogLog.error("Error occured while sending e-mail notification.", e);
/* 337:    */     }
/* 338:    */   }
/* 339:    */   
/* 340:    */   public String getEvaluatorClass()
/* 341:    */   {
/* 342:431 */     return this.evaluator == null ? null : this.evaluator.getClass().getName();
/* 343:    */   }
/* 344:    */   
/* 345:    */   public String getFrom()
/* 346:    */   {
/* 347:439 */     return this.from;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public String getReplyTo()
/* 351:    */   {
/* 352:449 */     return this.replyTo;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public String getSubject()
/* 356:    */   {
/* 357:457 */     return this.subject;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setFrom(String from)
/* 361:    */   {
/* 362:466 */     this.from = from;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setReplyTo(String addresses)
/* 366:    */   {
/* 367:476 */     this.replyTo = addresses;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setSubject(String subject)
/* 371:    */   {
/* 372:486 */     this.subject = subject;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setBufferSize(int bufferSize)
/* 376:    */   {
/* 377:499 */     this.bufferSize = bufferSize;
/* 378:500 */     this.cb.resize(bufferSize);
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setSMTPHost(String smtpHost)
/* 382:    */   {
/* 383:509 */     this.smtpHost = smtpHost;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public String getSMTPHost()
/* 387:    */   {
/* 388:517 */     return this.smtpHost;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void setTo(String to)
/* 392:    */   {
/* 393:526 */     this.to = to;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public int getBufferSize()
/* 397:    */   {
/* 398:536 */     return this.bufferSize;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void setEvaluatorClass(String value)
/* 402:    */   {
/* 403:548 */     this.evaluator = ((TriggeringEventEvaluator)OptionConverter.instantiateByClassName(value, TriggeringEventEvaluator.class, this.evaluator));
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setLocationInfo(boolean locationInfo)
/* 407:    */   {
/* 408:568 */     this.locationInfo = locationInfo;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public boolean getLocationInfo()
/* 412:    */   {
/* 413:576 */     return this.locationInfo;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setCc(String addresses)
/* 417:    */   {
/* 418:585 */     this.cc = addresses;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public String getCc()
/* 422:    */   {
/* 423:594 */     return this.cc;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setBcc(String addresses)
/* 427:    */   {
/* 428:603 */     this.bcc = addresses;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public String getBcc()
/* 432:    */   {
/* 433:612 */     return this.bcc;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setSMTPPassword(String password)
/* 437:    */   {
/* 438:622 */     this.smtpPassword = password;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setSMTPUsername(String username)
/* 442:    */   {
/* 443:632 */     this.smtpUsername = username;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setSMTPDebug(boolean debug)
/* 447:    */   {
/* 448:643 */     this.smtpDebug = debug;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public String getSMTPPassword()
/* 452:    */   {
/* 453:652 */     return this.smtpPassword;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public String getSMTPUsername()
/* 457:    */   {
/* 458:661 */     return this.smtpUsername;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public boolean getSMTPDebug()
/* 462:    */   {
/* 463:670 */     return this.smtpDebug;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public final void setEvaluator(TriggeringEventEvaluator trigger)
/* 467:    */   {
/* 468:679 */     if (trigger == null) {
/* 469:680 */       throw new NullPointerException("trigger");
/* 470:    */     }
/* 471:682 */     this.evaluator = trigger;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public final TriggeringEventEvaluator getEvaluator()
/* 475:    */   {
/* 476:691 */     return this.evaluator;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public boolean parseUnrecognizedElement(Element element, Properties props)
/* 480:    */     throws Exception
/* 481:    */   {
/* 482:699 */     if ("triggeringPolicy".equals(element.getNodeName()))
/* 483:    */     {
/* 484:700 */       Object triggerPolicy = DOMConfigurator.parseElement(element, props, TriggeringEventEvaluator.class);
/* 485:703 */       if ((triggerPolicy instanceof TriggeringEventEvaluator)) {
/* 486:704 */         setEvaluator((TriggeringEventEvaluator)triggerPolicy);
/* 487:    */       }
/* 488:706 */       return true;
/* 489:    */     }
/* 490:709 */     return false;
/* 491:    */   }
/* 492:    */   
/* 493:    */   public final String getSMTPProtocol()
/* 494:    */   {
/* 495:720 */     return this.smtpProtocol;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public final void setSMTPProtocol(String val)
/* 499:    */   {
/* 500:731 */     this.smtpProtocol = val;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public final int getSMTPPort()
/* 504:    */   {
/* 505:741 */     return this.smtpPort;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public final void setSMTPPort(int val)
/* 509:    */   {
/* 510:751 */     this.smtpPort = val;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public final boolean getSendOnClose()
/* 514:    */   {
/* 515:761 */     return this.sendOnClose;
/* 516:    */   }
/* 517:    */   
/* 518:    */   public final void setSendOnClose(boolean val)
/* 519:    */   {
/* 520:771 */     this.sendOnClose = val;
/* 521:    */   }
/* 522:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.SMTPAppender
 * JD-Core Version:    0.7.0.1
 */