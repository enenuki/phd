/*   1:    */ package jcifs.util.transport;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import jcifs.util.LogStream;
/*   7:    */ 
/*   8:    */ public abstract class Transport
/*   9:    */   implements Runnable
/*  10:    */ {
/*  11: 19 */   static int id = 0;
/*  12: 20 */   static LogStream log = LogStream.getInstance();
/*  13:    */   
/*  14:    */   public static int readn(InputStream in, byte[] b, int off, int len)
/*  15:    */     throws IOException
/*  16:    */   {
/*  17: 26 */     int i = 0;int n = -5;
/*  18: 28 */     while (i < len)
/*  19:    */     {
/*  20: 29 */       n = in.read(b, off + i, len - i);
/*  21: 30 */       if (n <= 0) {
/*  22:    */         break;
/*  23:    */       }
/*  24: 33 */       i += n;
/*  25:    */     }
/*  26: 36 */     return i;
/*  27:    */   }
/*  28:    */   
/*  29: 46 */   int state = 0;
/*  30: 48 */   String name = "Transport" + id++;
/*  31:    */   Thread thread;
/*  32:    */   TransportException te;
/*  33: 52 */   protected HashMap response_map = new HashMap(4);
/*  34:    */   
/*  35:    */   protected abstract void makeKey(Request paramRequest)
/*  36:    */     throws IOException;
/*  37:    */   
/*  38:    */   protected abstract Request peekKey()
/*  39:    */     throws IOException;
/*  40:    */   
/*  41:    */   protected abstract void doSend(Request paramRequest)
/*  42:    */     throws IOException;
/*  43:    */   
/*  44:    */   protected abstract void doRecv(Response paramResponse)
/*  45:    */     throws IOException;
/*  46:    */   
/*  47:    */   protected abstract void doSkip()
/*  48:    */     throws IOException;
/*  49:    */   
/*  50:    */   public synchronized void sendrecv(Request request, Response response, long timeout)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53: 63 */     makeKey(request);
/*  54: 64 */     response.isReceived = false;
/*  55:    */     try
/*  56:    */     {
/*  57: 66 */       this.response_map.put(request, response);
/*  58: 67 */       doSend(request);
/*  59: 68 */       response.expiration = (System.currentTimeMillis() + timeout);
/*  60: 69 */       while (!response.isReceived)
/*  61:    */       {
/*  62: 70 */         wait(timeout);
/*  63: 71 */         timeout = response.expiration - System.currentTimeMillis();
/*  64: 72 */         if (timeout <= 0L) {
/*  65: 73 */           throw new TransportException(this.name + " timedout waiting for response to " + request);
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */     catch (IOException ioe)
/*  70:    */     {
/*  71: 79 */       if (LogStream.level > 2) {
/*  72: 80 */         ioe.printStackTrace(log);
/*  73:    */       }
/*  74:    */       try
/*  75:    */       {
/*  76: 82 */         disconnect(true);
/*  77:    */       }
/*  78:    */       catch (IOException ioe2)
/*  79:    */       {
/*  80: 84 */         ioe2.printStackTrace(log);
/*  81:    */       }
/*  82: 86 */       throw ioe;
/*  83:    */     }
/*  84:    */     catch (InterruptedException ie)
/*  85:    */     {
/*  86: 88 */       throw new TransportException(ie);
/*  87:    */     }
/*  88:    */     finally
/*  89:    */     {
/*  90: 90 */       this.response_map.remove(request);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void loop()
/*  95:    */   {
/*  96: 94 */     while (this.thread == Thread.currentThread()) {
/*  97:    */       try
/*  98:    */       {
/*  99: 96 */         Request key = peekKey();
/* 100: 97 */         if (key == null) {
/* 101: 98 */           throw new IOException("end of stream");
/* 102:    */         }
/* 103: 99 */         synchronized (this)
/* 104:    */         {
/* 105:100 */           Response response = (Response)this.response_map.get(key);
/* 106:101 */           if (response == null)
/* 107:    */           {
/* 108:102 */             if (LogStream.level >= 4) {
/* 109:103 */               log.println("Invalid key, skipping message");
/* 110:    */             }
/* 111:104 */             doSkip();
/* 112:    */           }
/* 113:    */           else
/* 114:    */           {
/* 115:106 */             doRecv(response);
/* 116:107 */             response.isReceived = true;
/* 117:108 */             notifyAll();
/* 118:    */           }
/* 119:    */         }
/* 120:    */       }
/* 121:    */       catch (Exception ex)
/* 122:    */       {
/* 123:112 */         String msg = ex.getMessage();
/* 124:113 */         boolean timeout = (msg != null) && (msg.equals("Read timed out"));
/* 125:    */         
/* 126:    */ 
/* 127:116 */         boolean hard = !timeout;
/* 128:118 */         if ((!timeout) && (LogStream.level >= 3)) {
/* 129:119 */           ex.printStackTrace(log);
/* 130:    */         }
/* 131:    */         try
/* 132:    */         {
/* 133:122 */           disconnect(hard);
/* 134:    */         }
/* 135:    */         catch (IOException ioe)
/* 136:    */         {
/* 137:124 */           ioe.printStackTrace(log);
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected abstract void doConnect()
/* 144:    */     throws Exception;
/* 145:    */   
/* 146:    */   protected abstract void doDisconnect(boolean paramBoolean)
/* 147:    */     throws IOException;
/* 148:    */   
/* 149:    */   /* Error */
/* 150:    */   public synchronized void connect(long timeout)
/* 151:    */     throws TransportException
/* 152:    */   {
/* 153:    */     // Byte code:
/* 154:    */     //   0: aload_0
/* 155:    */     //   1: getfield 2	jcifs/util/transport/Transport:state	I
/* 156:    */     //   4: tableswitch	default:+62 -> 66, 0:+36->40, 1:+62->66, 2:+62->66, 3:+39->43, 4:+43->47
/* 157:    */     //   41: nop
/* 158:    */     //   42: lstore_0
/* 159:    */     //   43: jsr +244 -> 287
/* 160:    */     //   46: return
/* 161:    */     //   47: aload_0
/* 162:    */     //   48: iconst_0
/* 163:    */     //   49: putfield 2	jcifs/util/transport/Transport:state	I
/* 164:    */     //   52: new 22	jcifs/util/transport/TransportException
/* 165:    */     //   55: dup
/* 166:    */     //   56: ldc 51
/* 167:    */     //   58: aload_0
/* 168:    */     //   59: getfield 52	jcifs/util/transport/Transport:te	Ljcifs/util/transport/TransportException;
/* 169:    */     //   62: invokespecial 53	jcifs/util/transport/TransportException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 170:    */     //   65: athrow
/* 171:    */     //   66: new 22	jcifs/util/transport/TransportException
/* 172:    */     //   69: dup
/* 173:    */     //   70: new 3	java/lang/StringBuffer
/* 174:    */     //   73: dup
/* 175:    */     //   74: invokespecial 4	java/lang/StringBuffer:<init>	()V
/* 176:    */     //   77: ldc 54
/* 177:    */     //   79: invokevirtual 6	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/* 178:    */     //   82: aload_0
/* 179:    */     //   83: getfield 2	jcifs/util/transport/Transport:state	I
/* 180:    */     //   86: invokevirtual 8	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
/* 181:    */     //   89: invokevirtual 9	java/lang/StringBuffer:toString	()Ljava/lang/String;
/* 182:    */     //   92: invokespecial 25	jcifs/util/transport/TransportException:<init>	(Ljava/lang/String;)V
/* 183:    */     //   95: astore_3
/* 184:    */     //   96: aload_0
/* 185:    */     //   97: iconst_0
/* 186:    */     //   98: putfield 2	jcifs/util/transport/Transport:state	I
/* 187:    */     //   101: aload_3
/* 188:    */     //   102: athrow
/* 189:    */     //   103: aload_0
/* 190:    */     //   104: iconst_1
/* 191:    */     //   105: putfield 2	jcifs/util/transport/Transport:state	I
/* 192:    */     //   108: aload_0
/* 193:    */     //   109: aconst_null
/* 194:    */     //   110: putfield 52	jcifs/util/transport/Transport:te	Ljcifs/util/transport/TransportException;
/* 195:    */     //   113: aload_0
/* 196:    */     //   114: new 55	java/lang/Thread
/* 197:    */     //   117: dup
/* 198:    */     //   118: aload_0
/* 199:    */     //   119: aload_0
/* 200:    */     //   120: getfield 10	jcifs/util/transport/Transport:name	Ljava/lang/String;
/* 201:    */     //   123: invokespecial 56	java/lang/Thread:<init>	(Ljava/lang/Runnable;Ljava/lang/String;)V
/* 202:    */     //   126: putfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 203:    */     //   129: aload_0
/* 204:    */     //   130: getfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 205:    */     //   133: iconst_1
/* 206:    */     //   134: invokevirtual 57	java/lang/Thread:setDaemon	(Z)V
/* 207:    */     //   137: aload_0
/* 208:    */     //   138: getfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 209:    */     //   141: dup
/* 210:    */     //   142: astore_3
/* 211:    */     //   143: monitorenter
/* 212:    */     //   144: aload_0
/* 213:    */     //   145: getfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 214:    */     //   148: invokevirtual 58	java/lang/Thread:start	()V
/* 215:    */     //   151: aload_0
/* 216:    */     //   152: getfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 217:    */     //   155: lload_1
/* 218:    */     //   156: invokevirtual 21	java/lang/Object:wait	(J)V
/* 219:    */     //   159: aload_0
/* 220:    */     //   160: getfield 2	jcifs/util/transport/Transport:state	I
/* 221:    */     //   163: lookupswitch	default:+78->241, 1:+25->188, 2:+45->208
/* 222:    */     //   189: iconst_0
/* 223:    */     //   190: putfield 2	jcifs/util/transport/Transport:state	I
/* 224:    */     //   193: aload_0
/* 225:    */     //   194: aconst_null
/* 226:    */     //   195: putfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 227:    */     //   198: new 22	jcifs/util/transport/TransportException
/* 228:    */     //   201: dup
/* 229:    */     //   202: ldc 59
/* 230:    */     //   204: invokespecial 25	jcifs/util/transport/TransportException:<init>	(Ljava/lang/String;)V
/* 231:    */     //   207: athrow
/* 232:    */     //   208: aload_0
/* 233:    */     //   209: getfield 52	jcifs/util/transport/Transport:te	Ljcifs/util/transport/TransportException;
/* 234:    */     //   212: ifnull +18 -> 230
/* 235:    */     //   215: aload_0
/* 236:    */     //   216: iconst_4
/* 237:    */     //   217: putfield 2	jcifs/util/transport/Transport:state	I
/* 238:    */     //   220: aload_0
/* 239:    */     //   221: aconst_null
/* 240:    */     //   222: putfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 241:    */     //   225: aload_0
/* 242:    */     //   226: getfield 52	jcifs/util/transport/Transport:te	Ljcifs/util/transport/TransportException;
/* 243:    */     //   229: athrow
/* 244:    */     //   230: aload_0
/* 245:    */     //   231: iconst_3
/* 246:    */     //   232: putfield 2	jcifs/util/transport/Transport:state	I
/* 247:    */     //   235: aload_3
/* 248:    */     //   236: monitorexit
/* 249:    */     //   237: jsr +50 -> 287
/* 250:    */     //   240: return
/* 251:    */     //   241: aload_3
/* 252:    */     //   242: monitorexit
/* 253:    */     //   243: goto +10 -> 253
/* 254:    */     //   246: astore 4
/* 255:    */     //   248: aload_3
/* 256:    */     //   249: monitorexit
/* 257:    */     //   250: aload 4
/* 258:    */     //   252: athrow
/* 259:    */     //   253: jsr +34 -> 287
/* 260:    */     //   256: goto +107 -> 363
/* 261:    */     //   259: astore_3
/* 262:    */     //   260: aload_0
/* 263:    */     //   261: iconst_0
/* 264:    */     //   262: putfield 2	jcifs/util/transport/Transport:state	I
/* 265:    */     //   265: aload_0
/* 266:    */     //   266: aconst_null
/* 267:    */     //   267: putfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 268:    */     //   270: new 22	jcifs/util/transport/TransportException
/* 269:    */     //   273: dup
/* 270:    */     //   274: aload_3
/* 271:    */     //   275: invokespecial 33	jcifs/util/transport/TransportException:<init>	(Ljava/lang/Throwable;)V
/* 272:    */     //   278: athrow
/* 273:    */     //   279: astore 5
/* 274:    */     //   281: jsr +6 -> 287
/* 275:    */     //   284: aload 5
/* 276:    */     //   286: athrow
/* 277:    */     //   287: astore 6
/* 278:    */     //   289: aload_0
/* 279:    */     //   290: getfield 2	jcifs/util/transport/Transport:state	I
/* 280:    */     //   293: ifeq +68 -> 361
/* 281:    */     //   296: aload_0
/* 282:    */     //   297: getfield 2	jcifs/util/transport/Transport:state	I
/* 283:    */     //   300: iconst_3
/* 284:    */     //   301: if_icmpeq +60 -> 361
/* 285:    */     //   304: aload_0
/* 286:    */     //   305: getfield 2	jcifs/util/transport/Transport:state	I
/* 287:    */     //   308: iconst_4
/* 288:    */     //   309: if_icmpeq +52 -> 361
/* 289:    */     //   312: getstatic 28	jcifs/util/transport/Transport:log	Ljcifs/util/LogStream;
/* 290:    */     //   315: pop
/* 291:    */     //   316: getstatic 29	jcifs/util/LogStream:level	I
/* 292:    */     //   319: iconst_1
/* 293:    */     //   320: if_icmplt +31 -> 351
/* 294:    */     //   323: getstatic 28	jcifs/util/transport/Transport:log	Ljcifs/util/LogStream;
/* 295:    */     //   326: new 3	java/lang/StringBuffer
/* 296:    */     //   329: dup
/* 297:    */     //   330: invokespecial 4	java/lang/StringBuffer:<init>	()V
/* 298:    */     //   333: ldc 54
/* 299:    */     //   335: invokevirtual 6	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/* 300:    */     //   338: aload_0
/* 301:    */     //   339: getfield 2	jcifs/util/transport/Transport:state	I
/* 302:    */     //   342: invokevirtual 8	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
/* 303:    */     //   345: invokevirtual 9	java/lang/StringBuffer:toString	()Ljava/lang/String;
/* 304:    */     //   348: invokevirtual 42	jcifs/util/LogStream:println	(Ljava/lang/String;)V
/* 305:    */     //   351: aload_0
/* 306:    */     //   352: iconst_0
/* 307:    */     //   353: putfield 2	jcifs/util/transport/Transport:state	I
/* 308:    */     //   356: aload_0
/* 309:    */     //   357: aconst_null
/* 310:    */     //   358: putfield 34	jcifs/util/transport/Transport:thread	Ljava/lang/Thread;
/* 311:    */     //   361: ret 6
/* 312:    */     //   363: return
/* 313:    */     // Line number table:
/* 314:    */     //   Java source line #147	-> byte code offset #0
/* 315:    */     //   Java source line #149	-> byte code offset #40
/* 316:    */     //   Java source line #151	-> byte code offset #43
/* 317:    */     //   Java source line #153	-> byte code offset #47
/* 318:    */     //   Java source line #154	-> byte code offset #52
/* 319:    */     //   Java source line #156	-> byte code offset #66
/* 320:    */     //   Java source line #157	-> byte code offset #96
/* 321:    */     //   Java source line #158	-> byte code offset #101
/* 322:    */     //   Java source line #161	-> byte code offset #103
/* 323:    */     //   Java source line #162	-> byte code offset #108
/* 324:    */     //   Java source line #163	-> byte code offset #113
/* 325:    */     //   Java source line #164	-> byte code offset #129
/* 326:    */     //   Java source line #166	-> byte code offset #137
/* 327:    */     //   Java source line #167	-> byte code offset #144
/* 328:    */     //   Java source line #168	-> byte code offset #151
/* 329:    */     //   Java source line #170	-> byte code offset #159
/* 330:    */     //   Java source line #172	-> byte code offset #188
/* 331:    */     //   Java source line #173	-> byte code offset #193
/* 332:    */     //   Java source line #174	-> byte code offset #198
/* 333:    */     //   Java source line #176	-> byte code offset #208
/* 334:    */     //   Java source line #177	-> byte code offset #215
/* 335:    */     //   Java source line #178	-> byte code offset #220
/* 336:    */     //   Java source line #179	-> byte code offset #225
/* 337:    */     //   Java source line #181	-> byte code offset #230
/* 338:    */     //   Java source line #182	-> byte code offset #235
/* 339:    */     //   Java source line #184	-> byte code offset #241
/* 340:    */     //   Java source line #185	-> byte code offset #253
/* 341:    */     //   Java source line #198	-> byte code offset #256
/* 342:    */     //   Java source line #185	-> byte code offset #259
/* 343:    */     //   Java source line #186	-> byte code offset #260
/* 344:    */     //   Java source line #187	-> byte code offset #265
/* 345:    */     //   Java source line #188	-> byte code offset #270
/* 346:    */     //   Java source line #192	-> byte code offset #279
/* 347:    */     //   Java source line #193	-> byte code offset #312
/* 348:    */     //   Java source line #194	-> byte code offset #323
/* 349:    */     //   Java source line #195	-> byte code offset #351
/* 350:    */     //   Java source line #196	-> byte code offset #356
/* 351:    */     //   Java source line #199	-> byte code offset #363
/* 352:    */     // Local variable table:
/* 353:    */     //   start	length	slot	name	signature
/* 354:    */     //   0	364	0	this	Transport
/* 355:    */     //   0	364	1	timeout	long
/* 356:    */     //   95	7	3	te	TransportException
/* 357:    */     //   259	16	3	ie	InterruptedException
/* 358:    */     //   246	5	4	localObject1	Object
/* 359:    */     //   279	6	5	localObject2	Object
/* 360:    */     //   287	1	6	localObject3	Object
/* 361:    */     // Exception table:
/* 362:    */     //   from	to	target	type
/* 363:    */     //   144	237	246	finally
/* 364:    */     //   241	243	246	finally
/* 365:    */     //   246	250	246	finally
/* 366:    */     //   0	46	259	java/lang/InterruptedException
/* 367:    */     //   47	240	259	java/lang/InterruptedException
/* 368:    */     //   241	253	259	java/lang/InterruptedException
/* 369:    */     //   0	46	279	finally
/* 370:    */     //   47	240	279	finally
/* 371:    */     //   241	256	279	finally
/* 372:    */     //   259	284	279	finally
/* 373:    */   }
/* 374:    */   
/* 375:    */   public synchronized void disconnect(boolean hard)
/* 376:    */     throws IOException
/* 377:    */   {
/* 378:201 */     switch (this.state)
/* 379:    */     {
/* 380:    */     case 0: 
/* 381:    */     case 2: 
/* 382:205 */       hard = true;
/* 383:    */     case 3: 
/* 384:207 */       if ((this.response_map.size() == 0) || (hard)) {
/* 385:210 */         doDisconnect(hard);
/* 386:    */       }
/* 387:    */       break;
/* 388:    */     case 4: 
/* 389:212 */       this.thread = null;
/* 390:213 */       this.state = 0;
/* 391:214 */       break;
/* 392:    */     case 1: 
/* 393:    */     default: 
/* 394:216 */       if (LogStream.level >= 1) {
/* 395:217 */         log.println("Invalid state: " + this.state);
/* 396:    */       }
/* 397:218 */       this.thread = null;
/* 398:219 */       this.state = 0;
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void run()
/* 403:    */   {
/* 404:224 */     Thread run_thread = Thread.currentThread();
/* 405:225 */     Exception ex0 = null;
/* 406:    */     try
/* 407:    */     {
/* 408:232 */       doConnect();
/* 409:    */     }
/* 410:    */     catch (Exception ex)
/* 411:    */     {
/* 412:234 */       ex0 = ex;
/* 413:235 */       return;
/* 414:    */     }
/* 415:    */     finally
/* 416:    */     {
/* 417:237 */       synchronized (run_thread)
/* 418:    */       {
/* 419:238 */         if (run_thread != this.thread)
/* 420:    */         {
/* 421:242 */           if ((ex0 != null) && 
/* 422:243 */             (LogStream.level >= 2)) {
/* 423:244 */             ex0.printStackTrace(log);
/* 424:    */           }
/* 425:246 */           return;
/* 426:    */         }
/* 427:248 */         if (ex0 != null) {
/* 428:249 */           this.te = new TransportException(ex0);
/* 429:    */         }
/* 430:251 */         this.state = 2;
/* 431:252 */         run_thread.notify();
/* 432:    */       }
/* 433:    */     }
/* 434:258 */     loop();
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String toString()
/* 438:    */   {
/* 439:262 */     return this.name;
/* 440:    */   }
/* 441:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.transport.Transport
 * JD-Core Version:    0.7.0.1
 */