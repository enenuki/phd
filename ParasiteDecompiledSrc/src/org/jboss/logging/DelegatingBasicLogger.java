/*   1:    */ package org.jboss.logging;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class DelegatingBasicLogger
/*   6:    */   implements BasicLogger, Serializable
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = -5774903162389601853L;
/*   9: 39 */   private static final String FQCN = DelegatingBasicLogger.class.getName();
/*  10:    */   protected final Logger log;
/*  11:    */   
/*  12:    */   public DelegatingBasicLogger(Logger log)
/*  13:    */   {
/*  14: 52 */     this.log = log;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public boolean isTraceEnabled()
/*  18:    */   {
/*  19: 57 */     return this.log.isTraceEnabled();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void trace(Object message)
/*  23:    */   {
/*  24: 62 */     this.log.trace(FQCN, message, null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void trace(Object message, Throwable t)
/*  28:    */   {
/*  29: 67 */     this.log.trace(FQCN, message, t);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void trace(String loggerFqcn, Object message, Throwable t)
/*  33:    */   {
/*  34: 72 */     this.log.trace(loggerFqcn, message, t);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void trace(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  38:    */   {
/*  39: 77 */     this.log.trace(loggerFqcn, message, params, t);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void tracev(String format, Object... params)
/*  43:    */   {
/*  44: 82 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, params);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void tracev(String format, Object param1)
/*  48:    */   {
/*  49: 87 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void tracev(String format, Object param1, Object param2)
/*  53:    */   {
/*  54: 92 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1, param2);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void tracev(String format, Object param1, Object param2, Object param3)
/*  58:    */   {
/*  59: 97 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1, param2, param3);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void tracev(Throwable t, String format, Object... params)
/*  63:    */   {
/*  64:102 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, params);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void tracev(Throwable t, String format, Object param1)
/*  68:    */   {
/*  69:107 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void tracev(Throwable t, String format, Object param1, Object param2)
/*  73:    */   {
/*  74:112 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1, param2);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void tracev(Throwable t, String format, Object param1, Object param2, Object param3)
/*  78:    */   {
/*  79:117 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1, param2, param3);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void tracef(String format, Object... params)
/*  83:    */   {
/*  84:122 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, params);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void tracef(String format, Object param1)
/*  88:    */   {
/*  89:127 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void tracef(String format, Object param1, Object param2)
/*  93:    */   {
/*  94:132 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1, param2);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void tracef(String format, Object param1, Object param2, Object param3)
/*  98:    */   {
/*  99:137 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1, param2, param3);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void tracef(Throwable t, String format, Object... params)
/* 103:    */   {
/* 104:142 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, params);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void tracef(Throwable t, String format, Object param1)
/* 108:    */   {
/* 109:147 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void tracef(Throwable t, String format, Object param1, Object param2)
/* 113:    */   {
/* 114:152 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1, param2);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void tracef(Throwable t, String format, Object param1, Object param2, Object param3)
/* 118:    */   {
/* 119:157 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1, param2, param3);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isDebugEnabled()
/* 123:    */   {
/* 124:162 */     return this.log.isDebugEnabled();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void debug(Object message)
/* 128:    */   {
/* 129:167 */     this.log.debug(FQCN, message, null);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void debug(Object message, Throwable t)
/* 133:    */   {
/* 134:172 */     this.log.debug(FQCN, message, t);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void debug(String loggerFqcn, Object message, Throwable t)
/* 138:    */   {
/* 139:177 */     this.log.debug(loggerFqcn, message, t);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void debug(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 143:    */   {
/* 144:182 */     this.log.debug(loggerFqcn, message, params, t);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void debugv(String format, Object... params)
/* 148:    */   {
/* 149:187 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, params);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void debugv(String format, Object param1)
/* 153:    */   {
/* 154:192 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void debugv(String format, Object param1, Object param2)
/* 158:    */   {
/* 159:197 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1, param2);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void debugv(String format, Object param1, Object param2, Object param3)
/* 163:    */   {
/* 164:202 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1, param2, param3);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void debugv(Throwable t, String format, Object... params)
/* 168:    */   {
/* 169:207 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, params);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void debugv(Throwable t, String format, Object param1)
/* 173:    */   {
/* 174:212 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void debugv(Throwable t, String format, Object param1, Object param2)
/* 178:    */   {
/* 179:217 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1, param2);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void debugv(Throwable t, String format, Object param1, Object param2, Object param3)
/* 183:    */   {
/* 184:222 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1, param2, param3);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void debugf(String format, Object... params)
/* 188:    */   {
/* 189:227 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, params);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void debugf(String format, Object param1)
/* 193:    */   {
/* 194:232 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void debugf(String format, Object param1, Object param2)
/* 198:    */   {
/* 199:237 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1, param2);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void debugf(String format, Object param1, Object param2, Object param3)
/* 203:    */   {
/* 204:242 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1, param2, param3);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void debugf(Throwable t, String format, Object... params)
/* 208:    */   {
/* 209:247 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, params);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void debugf(Throwable t, String format, Object param1)
/* 213:    */   {
/* 214:252 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void debugf(Throwable t, String format, Object param1, Object param2)
/* 218:    */   {
/* 219:257 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1, param2);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void debugf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 223:    */   {
/* 224:262 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1, param2, param3);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean isInfoEnabled()
/* 228:    */   {
/* 229:267 */     return this.log.isInfoEnabled();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void info(Object message)
/* 233:    */   {
/* 234:272 */     this.log.info(FQCN, message, null);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void info(Object message, Throwable t)
/* 238:    */   {
/* 239:277 */     this.log.info(FQCN, message, t);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void info(String loggerFqcn, Object message, Throwable t)
/* 243:    */   {
/* 244:282 */     this.log.info(loggerFqcn, message, t);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void info(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 248:    */   {
/* 249:287 */     this.log.info(loggerFqcn, message, params, t);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void infov(String format, Object... params)
/* 253:    */   {
/* 254:292 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, params);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void infov(String format, Object param1)
/* 258:    */   {
/* 259:297 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void infov(String format, Object param1, Object param2)
/* 263:    */   {
/* 264:302 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1, param2);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void infov(String format, Object param1, Object param2, Object param3)
/* 268:    */   {
/* 269:307 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1, param2, param3);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void infov(Throwable t, String format, Object... params)
/* 273:    */   {
/* 274:312 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, params);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void infov(Throwable t, String format, Object param1)
/* 278:    */   {
/* 279:317 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void infov(Throwable t, String format, Object param1, Object param2)
/* 283:    */   {
/* 284:322 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1, param2);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void infov(Throwable t, String format, Object param1, Object param2, Object param3)
/* 288:    */   {
/* 289:327 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1, param2, param3);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void infof(String format, Object... params)
/* 293:    */   {
/* 294:332 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, params);
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void infof(String format, Object param1)
/* 298:    */   {
/* 299:337 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void infof(String format, Object param1, Object param2)
/* 303:    */   {
/* 304:342 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1, param2);
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void infof(String format, Object param1, Object param2, Object param3)
/* 308:    */   {
/* 309:347 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1, param2, param3);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void infof(Throwable t, String format, Object... params)
/* 313:    */   {
/* 314:352 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, params);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void infof(Throwable t, String format, Object param1)
/* 318:    */   {
/* 319:357 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1);
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void infof(Throwable t, String format, Object param1, Object param2)
/* 323:    */   {
/* 324:362 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1, param2);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void infof(Throwable t, String format, Object param1, Object param2, Object param3)
/* 328:    */   {
/* 329:367 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1, param2, param3);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void warn(Object message)
/* 333:    */   {
/* 334:372 */     this.log.warn(FQCN, message, null);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void warn(Object message, Throwable t)
/* 338:    */   {
/* 339:377 */     this.log.warn(FQCN, message, t);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void warn(String loggerFqcn, Object message, Throwable t)
/* 343:    */   {
/* 344:382 */     this.log.warn(loggerFqcn, message, t);
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void warn(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 348:    */   {
/* 349:387 */     this.log.warn(loggerFqcn, message, params, t);
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void warnv(String format, Object... params)
/* 353:    */   {
/* 354:392 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, params);
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void warnv(String format, Object param1)
/* 358:    */   {
/* 359:397 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1);
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void warnv(String format, Object param1, Object param2)
/* 363:    */   {
/* 364:402 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1, param2);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void warnv(String format, Object param1, Object param2, Object param3)
/* 368:    */   {
/* 369:407 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1, param2, param3);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void warnv(Throwable t, String format, Object... params)
/* 373:    */   {
/* 374:412 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, params);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void warnv(Throwable t, String format, Object param1)
/* 378:    */   {
/* 379:417 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void warnv(Throwable t, String format, Object param1, Object param2)
/* 383:    */   {
/* 384:422 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1, param2);
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void warnv(Throwable t, String format, Object param1, Object param2, Object param3)
/* 388:    */   {
/* 389:427 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1, param2, param3);
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void warnf(String format, Object... params)
/* 393:    */   {
/* 394:432 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, params);
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void warnf(String format, Object param1)
/* 398:    */   {
/* 399:437 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1);
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void warnf(String format, Object param1, Object param2)
/* 403:    */   {
/* 404:442 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1, param2);
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void warnf(String format, Object param1, Object param2, Object param3)
/* 408:    */   {
/* 409:447 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1, param2, param3);
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void warnf(Throwable t, String format, Object... params)
/* 413:    */   {
/* 414:452 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, params);
/* 415:    */   }
/* 416:    */   
/* 417:    */   public void warnf(Throwable t, String format, Object param1)
/* 418:    */   {
/* 419:457 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1);
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void warnf(Throwable t, String format, Object param1, Object param2)
/* 423:    */   {
/* 424:462 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1, param2);
/* 425:    */   }
/* 426:    */   
/* 427:    */   public void warnf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 428:    */   {
/* 429:467 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1, param2, param3);
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void error(Object message)
/* 433:    */   {
/* 434:472 */     this.log.error(FQCN, message, null);
/* 435:    */   }
/* 436:    */   
/* 437:    */   public void error(Object message, Throwable t)
/* 438:    */   {
/* 439:477 */     this.log.error(FQCN, message, t);
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void error(String loggerFqcn, Object message, Throwable t)
/* 443:    */   {
/* 444:482 */     this.log.error(loggerFqcn, message, t);
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void error(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 448:    */   {
/* 449:487 */     this.log.error(loggerFqcn, message, params, t);
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void errorv(String format, Object... params)
/* 453:    */   {
/* 454:492 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, params);
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void errorv(String format, Object param1)
/* 458:    */   {
/* 459:497 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1);
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void errorv(String format, Object param1, Object param2)
/* 463:    */   {
/* 464:502 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1, param2);
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void errorv(String format, Object param1, Object param2, Object param3)
/* 468:    */   {
/* 469:507 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1, param2, param3);
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void errorv(Throwable t, String format, Object... params)
/* 473:    */   {
/* 474:512 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, params);
/* 475:    */   }
/* 476:    */   
/* 477:    */   public void errorv(Throwable t, String format, Object param1)
/* 478:    */   {
/* 479:517 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1);
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void errorv(Throwable t, String format, Object param1, Object param2)
/* 483:    */   {
/* 484:522 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1, param2);
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void errorv(Throwable t, String format, Object param1, Object param2, Object param3)
/* 488:    */   {
/* 489:527 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1, param2, param3);
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void errorf(String format, Object... params)
/* 493:    */   {
/* 494:532 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, params);
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void errorf(String format, Object param1)
/* 498:    */   {
/* 499:537 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1);
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void errorf(String format, Object param1, Object param2)
/* 503:    */   {
/* 504:542 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1, param2);
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void errorf(String format, Object param1, Object param2, Object param3)
/* 508:    */   {
/* 509:547 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1, param2, param3);
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void errorf(Throwable t, String format, Object... params)
/* 513:    */   {
/* 514:552 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, params);
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void errorf(Throwable t, String format, Object param1)
/* 518:    */   {
/* 519:557 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1);
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void errorf(Throwable t, String format, Object param1, Object param2)
/* 523:    */   {
/* 524:562 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1, param2);
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void errorf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 528:    */   {
/* 529:567 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1, param2, param3);
/* 530:    */   }
/* 531:    */   
/* 532:    */   public void fatal(Object message)
/* 533:    */   {
/* 534:572 */     this.log.fatal(FQCN, message, null);
/* 535:    */   }
/* 536:    */   
/* 537:    */   public void fatal(Object message, Throwable t)
/* 538:    */   {
/* 539:577 */     this.log.fatal(FQCN, message, t);
/* 540:    */   }
/* 541:    */   
/* 542:    */   public void fatal(String loggerFqcn, Object message, Throwable t)
/* 543:    */   {
/* 544:582 */     this.log.fatal(loggerFqcn, message, t);
/* 545:    */   }
/* 546:    */   
/* 547:    */   public void fatal(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 548:    */   {
/* 549:587 */     this.log.fatal(loggerFqcn, message, params, t);
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void fatalv(String format, Object... params)
/* 553:    */   {
/* 554:592 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, params);
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void fatalv(String format, Object param1)
/* 558:    */   {
/* 559:597 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1);
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void fatalv(String format, Object param1, Object param2)
/* 563:    */   {
/* 564:602 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1, param2);
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void fatalv(String format, Object param1, Object param2, Object param3)
/* 568:    */   {
/* 569:607 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1, param2, param3);
/* 570:    */   }
/* 571:    */   
/* 572:    */   public void fatalv(Throwable t, String format, Object... params)
/* 573:    */   {
/* 574:612 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, params);
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void fatalv(Throwable t, String format, Object param1)
/* 578:    */   {
/* 579:617 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1);
/* 580:    */   }
/* 581:    */   
/* 582:    */   public void fatalv(Throwable t, String format, Object param1, Object param2)
/* 583:    */   {
/* 584:622 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1, param2);
/* 585:    */   }
/* 586:    */   
/* 587:    */   public void fatalv(Throwable t, String format, Object param1, Object param2, Object param3)
/* 588:    */   {
/* 589:627 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1, param2, param3);
/* 590:    */   }
/* 591:    */   
/* 592:    */   public void fatalf(String format, Object... params)
/* 593:    */   {
/* 594:632 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, params);
/* 595:    */   }
/* 596:    */   
/* 597:    */   public void fatalf(String format, Object param1)
/* 598:    */   {
/* 599:637 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1);
/* 600:    */   }
/* 601:    */   
/* 602:    */   public void fatalf(String format, Object param1, Object param2)
/* 603:    */   {
/* 604:642 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1, param2);
/* 605:    */   }
/* 606:    */   
/* 607:    */   public void fatalf(String format, Object param1, Object param2, Object param3)
/* 608:    */   {
/* 609:647 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1, param2, param3);
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void fatalf(Throwable t, String format, Object... params)
/* 613:    */   {
/* 614:652 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, params);
/* 615:    */   }
/* 616:    */   
/* 617:    */   public void fatalf(Throwable t, String format, Object param1)
/* 618:    */   {
/* 619:657 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1);
/* 620:    */   }
/* 621:    */   
/* 622:    */   public void fatalf(Throwable t, String format, Object param1, Object param2)
/* 623:    */   {
/* 624:662 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1, param2);
/* 625:    */   }
/* 626:    */   
/* 627:    */   public void fatalf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 628:    */   {
/* 629:667 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1, param2, param3);
/* 630:    */   }
/* 631:    */   
/* 632:    */   public void log(Logger.Level level, Object message)
/* 633:    */   {
/* 634:673 */     this.log.log(FQCN, level, message, null, null);
/* 635:    */   }
/* 636:    */   
/* 637:    */   public void log(Logger.Level level, Object message, Throwable t)
/* 638:    */   {
/* 639:678 */     this.log.log(FQCN, level, message, null, t);
/* 640:    */   }
/* 641:    */   
/* 642:    */   public void log(Logger.Level level, String loggerFqcn, Object message, Throwable t)
/* 643:    */   {
/* 644:683 */     this.log.log(level, loggerFqcn, message, t);
/* 645:    */   }
/* 646:    */   
/* 647:    */   public void log(String loggerFqcn, Logger.Level level, Object message, Object[] params, Throwable t)
/* 648:    */   {
/* 649:688 */     this.log.log(loggerFqcn, level, message, params, t);
/* 650:    */   }
/* 651:    */   
/* 652:    */   public void logv(Logger.Level level, String format, Object... params)
/* 653:    */   {
/* 654:693 */     this.log.logv(FQCN, level, null, format, params);
/* 655:    */   }
/* 656:    */   
/* 657:    */   public void logv(Logger.Level level, String format, Object param1)
/* 658:    */   {
/* 659:698 */     this.log.logv(FQCN, level, null, format, param1);
/* 660:    */   }
/* 661:    */   
/* 662:    */   public void logv(Logger.Level level, String format, Object param1, Object param2)
/* 663:    */   {
/* 664:703 */     this.log.logv(FQCN, level, null, format, param1, param2);
/* 665:    */   }
/* 666:    */   
/* 667:    */   public void logv(Logger.Level level, String format, Object param1, Object param2, Object param3)
/* 668:    */   {
/* 669:708 */     this.log.logv(FQCN, level, null, format, param1, param2, param3);
/* 670:    */   }
/* 671:    */   
/* 672:    */   public void logv(Logger.Level level, Throwable t, String format, Object... params)
/* 673:    */   {
/* 674:713 */     this.log.logv(FQCN, level, t, format, params);
/* 675:    */   }
/* 676:    */   
/* 677:    */   public void logv(Logger.Level level, Throwable t, String format, Object param1)
/* 678:    */   {
/* 679:718 */     this.log.logv(FQCN, level, t, format, param1);
/* 680:    */   }
/* 681:    */   
/* 682:    */   public void logv(Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 683:    */   {
/* 684:723 */     this.log.logv(FQCN, level, t, format, param1, param2);
/* 685:    */   }
/* 686:    */   
/* 687:    */   public void logv(Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 688:    */   {
/* 689:728 */     this.log.logv(FQCN, level, t, format, param1, param2, param3);
/* 690:    */   }
/* 691:    */   
/* 692:    */   public void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object... params)
/* 693:    */   {
/* 694:733 */     this.log.logv(loggerFqcn, level, t, format, params);
/* 695:    */   }
/* 696:    */   
/* 697:    */   public void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1)
/* 698:    */   {
/* 699:738 */     this.log.logv(loggerFqcn, level, t, format, param1);
/* 700:    */   }
/* 701:    */   
/* 702:    */   public void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 703:    */   {
/* 704:743 */     this.log.logv(loggerFqcn, level, t, format, param1, param2);
/* 705:    */   }
/* 706:    */   
/* 707:    */   public void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 708:    */   {
/* 709:748 */     this.log.logv(loggerFqcn, level, t, format, param1, param2, param3);
/* 710:    */   }
/* 711:    */   
/* 712:    */   public void logf(Logger.Level level, String format, Object... params)
/* 713:    */   {
/* 714:753 */     this.log.logf(FQCN, level, null, format, params);
/* 715:    */   }
/* 716:    */   
/* 717:    */   public void logf(Logger.Level level, String format, Object param1)
/* 718:    */   {
/* 719:758 */     this.log.logf(FQCN, level, null, format, param1);
/* 720:    */   }
/* 721:    */   
/* 722:    */   public void logf(Logger.Level level, String format, Object param1, Object param2)
/* 723:    */   {
/* 724:763 */     this.log.logf(FQCN, level, null, format, param1, param2);
/* 725:    */   }
/* 726:    */   
/* 727:    */   public void logf(Logger.Level level, String format, Object param1, Object param2, Object param3)
/* 728:    */   {
/* 729:768 */     this.log.logf(FQCN, level, null, format, param1, param2, param3);
/* 730:    */   }
/* 731:    */   
/* 732:    */   public void logf(Logger.Level level, Throwable t, String format, Object... params)
/* 733:    */   {
/* 734:773 */     this.log.logf(FQCN, level, t, format, params);
/* 735:    */   }
/* 736:    */   
/* 737:    */   public void logf(Logger.Level level, Throwable t, String format, Object param1)
/* 738:    */   {
/* 739:778 */     this.log.logf(FQCN, level, t, format, param1);
/* 740:    */   }
/* 741:    */   
/* 742:    */   public void logf(Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 743:    */   {
/* 744:783 */     this.log.logf(FQCN, level, t, format, param1, param2);
/* 745:    */   }
/* 746:    */   
/* 747:    */   public void logf(Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 748:    */   {
/* 749:788 */     this.log.logf(FQCN, level, t, format, param1, param2, param3);
/* 750:    */   }
/* 751:    */   
/* 752:    */   public void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1)
/* 753:    */   {
/* 754:793 */     this.log.logf(loggerFqcn, level, t, format, param1);
/* 755:    */   }
/* 756:    */   
/* 757:    */   public void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 758:    */   {
/* 759:798 */     this.log.logf(loggerFqcn, level, t, format, param1, param2);
/* 760:    */   }
/* 761:    */   
/* 762:    */   public void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 763:    */   {
/* 764:803 */     this.log.logf(loggerFqcn, level, t, format, param1, param2, param3);
/* 765:    */   }
/* 766:    */   
/* 767:    */   public void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object... params)
/* 768:    */   {
/* 769:808 */     this.log.logf(loggerFqcn, level, t, format, params);
/* 770:    */   }
/* 771:    */   
/* 772:    */   public boolean isEnabled(Logger.Level level)
/* 773:    */   {
/* 774:813 */     return this.log.isEnabled(level);
/* 775:    */   }
/* 776:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.DelegatingBasicLogger
 * JD-Core Version:    0.7.0.1
 */