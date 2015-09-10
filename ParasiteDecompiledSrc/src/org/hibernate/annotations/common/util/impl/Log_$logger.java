/*   1:    */ package org.hibernate.annotations.common.util.impl;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.jboss.logging.BasicLogger;
/*   5:    */ import org.jboss.logging.Logger;
/*   6:    */ import org.jboss.logging.Logger.Level;
/*   7:    */ 
/*   8:    */ public class Log_$logger
/*   9:    */   implements Serializable, Log, BasicLogger
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 1L;
/*  12:    */   private static final String projectCode = "HCANN";
/*  13: 21 */   private static final String FQCN = logger.class.getName();
/*  14:    */   protected final Logger log;
/*  15:    */   private static final String assertionFailure = "An assertion failure occurred (this may indicate a bug in Hibernate)";
/*  16:    */   private static final String version = "Hibernate Commons Annotations {%1$s}";
/*  17:    */   
/*  18:    */   public Log_$logger(Logger log)
/*  19:    */   {
/*  20: 27 */     this.log = log;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public final boolean isTraceEnabled()
/*  24:    */   {
/*  25: 32 */     return this.log.isTraceEnabled();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public final void trace(Object message)
/*  29:    */   {
/*  30: 37 */     this.log.trace(FQCN, message, null);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public final void trace(Object message, Throwable t)
/*  34:    */   {
/*  35: 42 */     this.log.trace(FQCN, message, t);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public final void trace(String loggerFqcn, Object message, Throwable t)
/*  39:    */   {
/*  40: 47 */     this.log.trace(loggerFqcn, message, t);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final void trace(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  44:    */   {
/*  45: 52 */     this.log.trace(loggerFqcn, message, params, t);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final void tracev(String format, Object... params)
/*  49:    */   {
/*  50: 57 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, params);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final void tracev(String format, Object param1)
/*  54:    */   {
/*  55: 62 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final void tracev(String format, Object param1, Object param2)
/*  59:    */   {
/*  60: 67 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1, param2);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public final void tracev(String format, Object param1, Object param2, Object param3)
/*  64:    */   {
/*  65: 72 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1, param2, param3);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final void tracev(Throwable t, String format, Object... params)
/*  69:    */   {
/*  70: 77 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, params);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final void tracev(Throwable t, String format, Object param1)
/*  74:    */   {
/*  75: 82 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final void tracev(Throwable t, String format, Object param1, Object param2)
/*  79:    */   {
/*  80: 87 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1, param2);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public final void tracev(Throwable t, String format, Object param1, Object param2, Object param3)
/*  84:    */   {
/*  85: 92 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1, param2, param3);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public final void tracef(String format, Object... params)
/*  89:    */   {
/*  90: 97 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, params);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public final void tracef(String format, Object param1)
/*  94:    */   {
/*  95:102 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public final void tracef(String format, Object param1, Object param2)
/*  99:    */   {
/* 100:107 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1, param2);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public final void tracef(String format, Object param1, Object param2, Object param3)
/* 104:    */   {
/* 105:112 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1, param2, param3);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public final void tracef(Throwable t, String format, Object... params)
/* 109:    */   {
/* 110:117 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, params);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public final void tracef(Throwable t, String format, Object param1)
/* 114:    */   {
/* 115:122 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public final void tracef(Throwable t, String format, Object param1, Object param2)
/* 119:    */   {
/* 120:127 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1, param2);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public final void tracef(Throwable t, String format, Object param1, Object param2, Object param3)
/* 124:    */   {
/* 125:132 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1, param2, param3);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public final boolean isDebugEnabled()
/* 129:    */   {
/* 130:137 */     return this.log.isDebugEnabled();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public final void debug(Object message)
/* 134:    */   {
/* 135:142 */     this.log.debug(FQCN, message, null);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public final void debug(Object message, Throwable t)
/* 139:    */   {
/* 140:147 */     this.log.debug(FQCN, message, t);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public final void debug(String loggerFqcn, Object message, Throwable t)
/* 144:    */   {
/* 145:152 */     this.log.debug(loggerFqcn, message, t);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public final void debug(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 149:    */   {
/* 150:157 */     this.log.debug(loggerFqcn, message, params, t);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final void debugv(String format, Object... params)
/* 154:    */   {
/* 155:162 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, params);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public final void debugv(String format, Object param1)
/* 159:    */   {
/* 160:167 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public final void debugv(String format, Object param1, Object param2)
/* 164:    */   {
/* 165:172 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1, param2);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public final void debugv(String format, Object param1, Object param2, Object param3)
/* 169:    */   {
/* 170:177 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1, param2, param3);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public final void debugv(Throwable t, String format, Object... params)
/* 174:    */   {
/* 175:182 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, params);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public final void debugv(Throwable t, String format, Object param1)
/* 179:    */   {
/* 180:187 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public final void debugv(Throwable t, String format, Object param1, Object param2)
/* 184:    */   {
/* 185:192 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1, param2);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public final void debugv(Throwable t, String format, Object param1, Object param2, Object param3)
/* 189:    */   {
/* 190:197 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1, param2, param3);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public final void debugf(String format, Object... params)
/* 194:    */   {
/* 195:202 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, params);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public final void debugf(String format, Object param1)
/* 199:    */   {
/* 200:207 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public final void debugf(String format, Object param1, Object param2)
/* 204:    */   {
/* 205:212 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1, param2);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public final void debugf(String format, Object param1, Object param2, Object param3)
/* 209:    */   {
/* 210:217 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1, param2, param3);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public final void debugf(Throwable t, String format, Object... params)
/* 214:    */   {
/* 215:222 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, params);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public final void debugf(Throwable t, String format, Object param1)
/* 219:    */   {
/* 220:227 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public final void debugf(Throwable t, String format, Object param1, Object param2)
/* 224:    */   {
/* 225:232 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1, param2);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public final void debugf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 229:    */   {
/* 230:237 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1, param2, param3);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public final boolean isInfoEnabled()
/* 234:    */   {
/* 235:242 */     return this.log.isInfoEnabled();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public final void info(Object message)
/* 239:    */   {
/* 240:247 */     this.log.info(FQCN, message, null);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public final void info(Object message, Throwable t)
/* 244:    */   {
/* 245:252 */     this.log.info(FQCN, message, t);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public final void info(String loggerFqcn, Object message, Throwable t)
/* 249:    */   {
/* 250:257 */     this.log.info(loggerFqcn, message, t);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public final void info(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 254:    */   {
/* 255:262 */     this.log.info(loggerFqcn, message, params, t);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public final void infov(String format, Object... params)
/* 259:    */   {
/* 260:267 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, params);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public final void infov(String format, Object param1)
/* 264:    */   {
/* 265:272 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public final void infov(String format, Object param1, Object param2)
/* 269:    */   {
/* 270:277 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1, param2);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public final void infov(String format, Object param1, Object param2, Object param3)
/* 274:    */   {
/* 275:282 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1, param2, param3);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public final void infov(Throwable t, String format, Object... params)
/* 279:    */   {
/* 280:287 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, params);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public final void infov(Throwable t, String format, Object param1)
/* 284:    */   {
/* 285:292 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public final void infov(Throwable t, String format, Object param1, Object param2)
/* 289:    */   {
/* 290:297 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1, param2);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public final void infov(Throwable t, String format, Object param1, Object param2, Object param3)
/* 294:    */   {
/* 295:302 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1, param2, param3);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public final void infof(String format, Object... params)
/* 299:    */   {
/* 300:307 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, params);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public final void infof(String format, Object param1)
/* 304:    */   {
/* 305:312 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public final void infof(String format, Object param1, Object param2)
/* 309:    */   {
/* 310:317 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1, param2);
/* 311:    */   }
/* 312:    */   
/* 313:    */   public final void infof(String format, Object param1, Object param2, Object param3)
/* 314:    */   {
/* 315:322 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1, param2, param3);
/* 316:    */   }
/* 317:    */   
/* 318:    */   public final void infof(Throwable t, String format, Object... params)
/* 319:    */   {
/* 320:327 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, params);
/* 321:    */   }
/* 322:    */   
/* 323:    */   public final void infof(Throwable t, String format, Object param1)
/* 324:    */   {
/* 325:332 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public final void infof(Throwable t, String format, Object param1, Object param2)
/* 329:    */   {
/* 330:337 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1, param2);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public final void infof(Throwable t, String format, Object param1, Object param2, Object param3)
/* 334:    */   {
/* 335:342 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1, param2, param3);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public final void warn(Object message)
/* 339:    */   {
/* 340:347 */     this.log.warn(FQCN, message, null);
/* 341:    */   }
/* 342:    */   
/* 343:    */   public final void warn(Object message, Throwable t)
/* 344:    */   {
/* 345:352 */     this.log.warn(FQCN, message, t);
/* 346:    */   }
/* 347:    */   
/* 348:    */   public final void warn(String loggerFqcn, Object message, Throwable t)
/* 349:    */   {
/* 350:357 */     this.log.warn(loggerFqcn, message, t);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public final void warn(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 354:    */   {
/* 355:362 */     this.log.warn(loggerFqcn, message, params, t);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public final void warnv(String format, Object... params)
/* 359:    */   {
/* 360:367 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, params);
/* 361:    */   }
/* 362:    */   
/* 363:    */   public final void warnv(String format, Object param1)
/* 364:    */   {
/* 365:372 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1);
/* 366:    */   }
/* 367:    */   
/* 368:    */   public final void warnv(String format, Object param1, Object param2)
/* 369:    */   {
/* 370:377 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1, param2);
/* 371:    */   }
/* 372:    */   
/* 373:    */   public final void warnv(String format, Object param1, Object param2, Object param3)
/* 374:    */   {
/* 375:382 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1, param2, param3);
/* 376:    */   }
/* 377:    */   
/* 378:    */   public final void warnv(Throwable t, String format, Object... params)
/* 379:    */   {
/* 380:387 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, params);
/* 381:    */   }
/* 382:    */   
/* 383:    */   public final void warnv(Throwable t, String format, Object param1)
/* 384:    */   {
/* 385:392 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1);
/* 386:    */   }
/* 387:    */   
/* 388:    */   public final void warnv(Throwable t, String format, Object param1, Object param2)
/* 389:    */   {
/* 390:397 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1, param2);
/* 391:    */   }
/* 392:    */   
/* 393:    */   public final void warnv(Throwable t, String format, Object param1, Object param2, Object param3)
/* 394:    */   {
/* 395:402 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1, param2, param3);
/* 396:    */   }
/* 397:    */   
/* 398:    */   public final void warnf(String format, Object... params)
/* 399:    */   {
/* 400:407 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, params);
/* 401:    */   }
/* 402:    */   
/* 403:    */   public final void warnf(String format, Object param1)
/* 404:    */   {
/* 405:412 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1);
/* 406:    */   }
/* 407:    */   
/* 408:    */   public final void warnf(String format, Object param1, Object param2)
/* 409:    */   {
/* 410:417 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1, param2);
/* 411:    */   }
/* 412:    */   
/* 413:    */   public final void warnf(String format, Object param1, Object param2, Object param3)
/* 414:    */   {
/* 415:422 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1, param2, param3);
/* 416:    */   }
/* 417:    */   
/* 418:    */   public final void warnf(Throwable t, String format, Object... params)
/* 419:    */   {
/* 420:427 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, params);
/* 421:    */   }
/* 422:    */   
/* 423:    */   public final void warnf(Throwable t, String format, Object param1)
/* 424:    */   {
/* 425:432 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1);
/* 426:    */   }
/* 427:    */   
/* 428:    */   public final void warnf(Throwable t, String format, Object param1, Object param2)
/* 429:    */   {
/* 430:437 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1, param2);
/* 431:    */   }
/* 432:    */   
/* 433:    */   public final void warnf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 434:    */   {
/* 435:442 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1, param2, param3);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public final void error(Object message)
/* 439:    */   {
/* 440:447 */     this.log.error(FQCN, message, null);
/* 441:    */   }
/* 442:    */   
/* 443:    */   public final void error(Object message, Throwable t)
/* 444:    */   {
/* 445:452 */     this.log.error(FQCN, message, t);
/* 446:    */   }
/* 447:    */   
/* 448:    */   public final void error(String loggerFqcn, Object message, Throwable t)
/* 449:    */   {
/* 450:457 */     this.log.error(loggerFqcn, message, t);
/* 451:    */   }
/* 452:    */   
/* 453:    */   public final void error(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 454:    */   {
/* 455:462 */     this.log.error(loggerFqcn, message, params, t);
/* 456:    */   }
/* 457:    */   
/* 458:    */   public final void errorv(String format, Object... params)
/* 459:    */   {
/* 460:467 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, params);
/* 461:    */   }
/* 462:    */   
/* 463:    */   public final void errorv(String format, Object param1)
/* 464:    */   {
/* 465:472 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1);
/* 466:    */   }
/* 467:    */   
/* 468:    */   public final void errorv(String format, Object param1, Object param2)
/* 469:    */   {
/* 470:477 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1, param2);
/* 471:    */   }
/* 472:    */   
/* 473:    */   public final void errorv(String format, Object param1, Object param2, Object param3)
/* 474:    */   {
/* 475:482 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1, param2, param3);
/* 476:    */   }
/* 477:    */   
/* 478:    */   public final void errorv(Throwable t, String format, Object... params)
/* 479:    */   {
/* 480:487 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, params);
/* 481:    */   }
/* 482:    */   
/* 483:    */   public final void errorv(Throwable t, String format, Object param1)
/* 484:    */   {
/* 485:492 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1);
/* 486:    */   }
/* 487:    */   
/* 488:    */   public final void errorv(Throwable t, String format, Object param1, Object param2)
/* 489:    */   {
/* 490:497 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1, param2);
/* 491:    */   }
/* 492:    */   
/* 493:    */   public final void errorv(Throwable t, String format, Object param1, Object param2, Object param3)
/* 494:    */   {
/* 495:502 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1, param2, param3);
/* 496:    */   }
/* 497:    */   
/* 498:    */   public final void errorf(String format, Object... params)
/* 499:    */   {
/* 500:507 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, params);
/* 501:    */   }
/* 502:    */   
/* 503:    */   public final void errorf(String format, Object param1)
/* 504:    */   {
/* 505:512 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1);
/* 506:    */   }
/* 507:    */   
/* 508:    */   public final void errorf(String format, Object param1, Object param2)
/* 509:    */   {
/* 510:517 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1, param2);
/* 511:    */   }
/* 512:    */   
/* 513:    */   public final void errorf(String format, Object param1, Object param2, Object param3)
/* 514:    */   {
/* 515:522 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1, param2, param3);
/* 516:    */   }
/* 517:    */   
/* 518:    */   public final void errorf(Throwable t, String format, Object... params)
/* 519:    */   {
/* 520:527 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, params);
/* 521:    */   }
/* 522:    */   
/* 523:    */   public final void errorf(Throwable t, String format, Object param1)
/* 524:    */   {
/* 525:532 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1);
/* 526:    */   }
/* 527:    */   
/* 528:    */   public final void errorf(Throwable t, String format, Object param1, Object param2)
/* 529:    */   {
/* 530:537 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1, param2);
/* 531:    */   }
/* 532:    */   
/* 533:    */   public final void errorf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 534:    */   {
/* 535:542 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1, param2, param3);
/* 536:    */   }
/* 537:    */   
/* 538:    */   public final void fatal(Object message)
/* 539:    */   {
/* 540:547 */     this.log.fatal(FQCN, message, null);
/* 541:    */   }
/* 542:    */   
/* 543:    */   public final void fatal(Object message, Throwable t)
/* 544:    */   {
/* 545:552 */     this.log.fatal(FQCN, message, t);
/* 546:    */   }
/* 547:    */   
/* 548:    */   public final void fatal(String loggerFqcn, Object message, Throwable t)
/* 549:    */   {
/* 550:557 */     this.log.fatal(loggerFqcn, message, t);
/* 551:    */   }
/* 552:    */   
/* 553:    */   public final void fatal(String loggerFqcn, Object message, Object[] params, Throwable t)
/* 554:    */   {
/* 555:562 */     this.log.fatal(loggerFqcn, message, params, t);
/* 556:    */   }
/* 557:    */   
/* 558:    */   public final void fatalv(String format, Object... params)
/* 559:    */   {
/* 560:567 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, params);
/* 561:    */   }
/* 562:    */   
/* 563:    */   public final void fatalv(String format, Object param1)
/* 564:    */   {
/* 565:572 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1);
/* 566:    */   }
/* 567:    */   
/* 568:    */   public final void fatalv(String format, Object param1, Object param2)
/* 569:    */   {
/* 570:577 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1, param2);
/* 571:    */   }
/* 572:    */   
/* 573:    */   public final void fatalv(String format, Object param1, Object param2, Object param3)
/* 574:    */   {
/* 575:582 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1, param2, param3);
/* 576:    */   }
/* 577:    */   
/* 578:    */   public final void fatalv(Throwable t, String format, Object... params)
/* 579:    */   {
/* 580:587 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, params);
/* 581:    */   }
/* 582:    */   
/* 583:    */   public final void fatalv(Throwable t, String format, Object param1)
/* 584:    */   {
/* 585:592 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1);
/* 586:    */   }
/* 587:    */   
/* 588:    */   public final void fatalv(Throwable t, String format, Object param1, Object param2)
/* 589:    */   {
/* 590:597 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1, param2);
/* 591:    */   }
/* 592:    */   
/* 593:    */   public final void fatalv(Throwable t, String format, Object param1, Object param2, Object param3)
/* 594:    */   {
/* 595:602 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1, param2, param3);
/* 596:    */   }
/* 597:    */   
/* 598:    */   public final void fatalf(String format, Object... params)
/* 599:    */   {
/* 600:607 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, params);
/* 601:    */   }
/* 602:    */   
/* 603:    */   public final void fatalf(String format, Object param1)
/* 604:    */   {
/* 605:612 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1);
/* 606:    */   }
/* 607:    */   
/* 608:    */   public final void fatalf(String format, Object param1, Object param2)
/* 609:    */   {
/* 610:617 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1, param2);
/* 611:    */   }
/* 612:    */   
/* 613:    */   public final void fatalf(String format, Object param1, Object param2, Object param3)
/* 614:    */   {
/* 615:622 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1, param2, param3);
/* 616:    */   }
/* 617:    */   
/* 618:    */   public final void fatalf(Throwable t, String format, Object... params)
/* 619:    */   {
/* 620:627 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, params);
/* 621:    */   }
/* 622:    */   
/* 623:    */   public final void fatalf(Throwable t, String format, Object param1)
/* 624:    */   {
/* 625:632 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1);
/* 626:    */   }
/* 627:    */   
/* 628:    */   public final void fatalf(Throwable t, String format, Object param1, Object param2)
/* 629:    */   {
/* 630:637 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1, param2);
/* 631:    */   }
/* 632:    */   
/* 633:    */   public final void fatalf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 634:    */   {
/* 635:642 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1, param2, param3);
/* 636:    */   }
/* 637:    */   
/* 638:    */   public final boolean isEnabled(Logger.Level level)
/* 639:    */   {
/* 640:646 */     return this.log.isEnabled(level);
/* 641:    */   }
/* 642:    */   
/* 643:    */   public final void log(Logger.Level level, Object message)
/* 644:    */   {
/* 645:651 */     this.log.log(FQCN, level, message, null, null);
/* 646:    */   }
/* 647:    */   
/* 648:    */   public final void log(Logger.Level level, Object message, Throwable t)
/* 649:    */   {
/* 650:656 */     this.log.log(FQCN, level, message, null, t);
/* 651:    */   }
/* 652:    */   
/* 653:    */   public final void log(Logger.Level level, String loggerFqcn, Object message, Throwable t)
/* 654:    */   {
/* 655:661 */     this.log.log(level, loggerFqcn, message, t);
/* 656:    */   }
/* 657:    */   
/* 658:    */   public final void log(String loggerFqcn, Logger.Level level, Object message, Object[] params, Throwable t)
/* 659:    */   {
/* 660:666 */     this.log.log(loggerFqcn, level, message, params, t);
/* 661:    */   }
/* 662:    */   
/* 663:    */   public final void logv(Logger.Level level, String format, Object... params)
/* 664:    */   {
/* 665:671 */     this.log.logv(FQCN, level, null, format, params);
/* 666:    */   }
/* 667:    */   
/* 668:    */   public final void logv(Logger.Level level, String format, Object param1)
/* 669:    */   {
/* 670:676 */     this.log.logv(FQCN, level, null, format, param1);
/* 671:    */   }
/* 672:    */   
/* 673:    */   public final void logv(Logger.Level level, String format, Object param1, Object param2)
/* 674:    */   {
/* 675:681 */     this.log.logv(FQCN, level, null, format, param1, param2);
/* 676:    */   }
/* 677:    */   
/* 678:    */   public final void logv(Logger.Level level, String format, Object param1, Object param2, Object param3)
/* 679:    */   {
/* 680:686 */     this.log.logv(FQCN, level, null, format, param1, param2, param3);
/* 681:    */   }
/* 682:    */   
/* 683:    */   public final void logv(Logger.Level level, Throwable t, String format, Object... params)
/* 684:    */   {
/* 685:691 */     this.log.logv(FQCN, level, t, format, params);
/* 686:    */   }
/* 687:    */   
/* 688:    */   public final void logv(Logger.Level level, Throwable t, String format, Object param1)
/* 689:    */   {
/* 690:696 */     this.log.logv(FQCN, level, t, format, param1);
/* 691:    */   }
/* 692:    */   
/* 693:    */   public final void logv(Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 694:    */   {
/* 695:701 */     this.log.logv(FQCN, level, t, format, param1, param2);
/* 696:    */   }
/* 697:    */   
/* 698:    */   public final void logv(Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 699:    */   {
/* 700:706 */     this.log.logv(FQCN, level, t, format, param1, param2, param3);
/* 701:    */   }
/* 702:    */   
/* 703:    */   public final void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object... params)
/* 704:    */   {
/* 705:711 */     this.log.logv(loggerFqcn, level, t, format, params);
/* 706:    */   }
/* 707:    */   
/* 708:    */   public final void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1)
/* 709:    */   {
/* 710:716 */     this.log.logv(loggerFqcn, level, t, format, param1);
/* 711:    */   }
/* 712:    */   
/* 713:    */   public final void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 714:    */   {
/* 715:721 */     this.log.logv(loggerFqcn, level, t, format, param1, param2);
/* 716:    */   }
/* 717:    */   
/* 718:    */   public final void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 719:    */   {
/* 720:726 */     this.log.logv(loggerFqcn, level, t, format, param1, param2, param3);
/* 721:    */   }
/* 722:    */   
/* 723:    */   public final void logf(Logger.Level level, String format, Object... params)
/* 724:    */   {
/* 725:731 */     this.log.logf(FQCN, level, null, format, params);
/* 726:    */   }
/* 727:    */   
/* 728:    */   public final void logf(Logger.Level level, String format, Object param1)
/* 729:    */   {
/* 730:736 */     this.log.logf(FQCN, level, null, format, param1);
/* 731:    */   }
/* 732:    */   
/* 733:    */   public final void logf(Logger.Level level, String format, Object param1, Object param2)
/* 734:    */   {
/* 735:741 */     this.log.logf(FQCN, level, null, format, param1, param2);
/* 736:    */   }
/* 737:    */   
/* 738:    */   public final void logf(Logger.Level level, String format, Object param1, Object param2, Object param3)
/* 739:    */   {
/* 740:746 */     this.log.logf(FQCN, level, null, format, param1, param2, param3);
/* 741:    */   }
/* 742:    */   
/* 743:    */   public final void logf(Logger.Level level, Throwable t, String format, Object... params)
/* 744:    */   {
/* 745:751 */     this.log.logf(FQCN, level, t, format, params);
/* 746:    */   }
/* 747:    */   
/* 748:    */   public final void logf(Logger.Level level, Throwable t, String format, Object param1)
/* 749:    */   {
/* 750:756 */     this.log.logf(FQCN, level, t, format, param1);
/* 751:    */   }
/* 752:    */   
/* 753:    */   public final void logf(Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 754:    */   {
/* 755:761 */     this.log.logf(FQCN, level, t, format, param1, param2);
/* 756:    */   }
/* 757:    */   
/* 758:    */   public final void logf(Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 759:    */   {
/* 760:766 */     this.log.logf(FQCN, level, t, format, param1, param2, param3);
/* 761:    */   }
/* 762:    */   
/* 763:    */   public final void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object... params)
/* 764:    */   {
/* 765:771 */     this.log.logf(loggerFqcn, level, t, format, params);
/* 766:    */   }
/* 767:    */   
/* 768:    */   public final void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1)
/* 769:    */   {
/* 770:776 */     this.log.logf(loggerFqcn, level, t, format, param1);
/* 771:    */   }
/* 772:    */   
/* 773:    */   public final void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 774:    */   {
/* 775:781 */     this.log.logf(loggerFqcn, level, t, format, param1, param2);
/* 776:    */   }
/* 777:    */   
/* 778:    */   public final void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 779:    */   {
/* 780:786 */     this.log.logf(loggerFqcn, level, t, format, param1, param2, param3);
/* 781:    */   }
/* 782:    */   
/* 783:    */   public final void assertionFailure(Throwable t)
/* 784:    */   {
/* 785:791 */     this.log.logf(FQCN, Logger.Level.ERROR, t, "HCANN000002: " + assertionFailure$str(), new Object[0]);
/* 786:    */   }
/* 787:    */   
/* 788:    */   protected String assertionFailure$str()
/* 789:    */   {
/* 790:795 */     return "An assertion failure occurred (this may indicate a bug in Hibernate)";
/* 791:    */   }
/* 792:    */   
/* 793:    */   public final void version(String version)
/* 794:    */   {
/* 795:800 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HCANN000001: " + version$str(), version);
/* 796:    */   }
/* 797:    */   
/* 798:    */   protected String version$str()
/* 799:    */   {
/* 800:804 */     return "Hibernate Commons Annotations {%1$s}";
/* 801:    */   }
/* 802:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.util.impl.Log_.logger
 * JD-Core Version:    0.7.0.1
 */