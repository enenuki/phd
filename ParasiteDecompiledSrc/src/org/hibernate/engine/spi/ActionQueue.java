/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Set;
/*  14:    */ import org.hibernate.AssertionFailure;
/*  15:    */ import org.hibernate.HibernateException;
/*  16:    */ import org.hibernate.action.internal.BulkOperationCleanupAction;
/*  17:    */ import org.hibernate.action.internal.CollectionAction;
/*  18:    */ import org.hibernate.action.internal.CollectionRecreateAction;
/*  19:    */ import org.hibernate.action.internal.CollectionRemoveAction;
/*  20:    */ import org.hibernate.action.internal.CollectionUpdateAction;
/*  21:    */ import org.hibernate.action.internal.EntityAction;
/*  22:    */ import org.hibernate.action.internal.EntityDeleteAction;
/*  23:    */ import org.hibernate.action.internal.EntityIdentityInsertAction;
/*  24:    */ import org.hibernate.action.internal.EntityInsertAction;
/*  25:    */ import org.hibernate.action.internal.EntityUpdateAction;
/*  26:    */ import org.hibernate.action.spi.AfterTransactionCompletionProcess;
/*  27:    */ import org.hibernate.action.spi.BeforeTransactionCompletionProcess;
/*  28:    */ import org.hibernate.action.spi.Executable;
/*  29:    */ import org.hibernate.cache.CacheException;
/*  30:    */ import org.hibernate.cache.spi.UpdateTimestampsCache;
/*  31:    */ import org.hibernate.cfg.Settings;
/*  32:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  33:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  34:    */ import org.hibernate.internal.CoreMessageLogger;
/*  35:    */ import org.hibernate.metadata.ClassMetadata;
/*  36:    */ import org.hibernate.persister.entity.EntityPersister;
/*  37:    */ import org.hibernate.type.Type;
/*  38:    */ import org.jboss.logging.Logger;
/*  39:    */ 
/*  40:    */ public class ActionQueue
/*  41:    */ {
/*  42: 69 */   static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ActionQueue.class.getName());
/*  43:    */   private static final int INIT_QUEUE_LIST_SIZE = 5;
/*  44:    */   private SessionImplementor session;
/*  45:    */   private ArrayList insertions;
/*  46:    */   private ArrayList deletions;
/*  47:    */   private ArrayList updates;
/*  48:    */   private ArrayList collectionCreations;
/*  49:    */   private ArrayList collectionUpdates;
/*  50:    */   private ArrayList collectionRemovals;
/*  51:    */   private AfterTransactionCompletionProcessQueue afterTransactionProcesses;
/*  52:    */   private BeforeTransactionCompletionProcessQueue beforeTransactionProcesses;
/*  53:    */   
/*  54:    */   public ActionQueue(SessionImplementor session)
/*  55:    */   {
/*  56: 97 */     this.session = session;
/*  57: 98 */     init();
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void init()
/*  61:    */   {
/*  62:102 */     this.insertions = new ArrayList(5);
/*  63:103 */     this.deletions = new ArrayList(5);
/*  64:104 */     this.updates = new ArrayList(5);
/*  65:    */     
/*  66:106 */     this.collectionCreations = new ArrayList(5);
/*  67:107 */     this.collectionRemovals = new ArrayList(5);
/*  68:108 */     this.collectionUpdates = new ArrayList(5);
/*  69:    */     
/*  70:110 */     this.afterTransactionProcesses = new AfterTransactionCompletionProcessQueue(this.session, null);
/*  71:111 */     this.beforeTransactionProcesses = new BeforeTransactionCompletionProcessQueue(this.session, null);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void clear()
/*  75:    */   {
/*  76:115 */     this.updates.clear();
/*  77:116 */     this.insertions.clear();
/*  78:117 */     this.deletions.clear();
/*  79:    */     
/*  80:119 */     this.collectionCreations.clear();
/*  81:120 */     this.collectionRemovals.clear();
/*  82:121 */     this.collectionUpdates.clear();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void addAction(EntityInsertAction action)
/*  86:    */   {
/*  87:126 */     this.insertions.add(action);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void addAction(EntityDeleteAction action)
/*  91:    */   {
/*  92:131 */     this.deletions.add(action);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void addAction(EntityUpdateAction action)
/*  96:    */   {
/*  97:136 */     this.updates.add(action);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void addAction(CollectionRecreateAction action)
/* 101:    */   {
/* 102:141 */     this.collectionCreations.add(action);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void addAction(CollectionRemoveAction action)
/* 106:    */   {
/* 107:146 */     this.collectionRemovals.add(action);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void addAction(CollectionUpdateAction action)
/* 111:    */   {
/* 112:151 */     this.collectionUpdates.add(action);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void addAction(EntityIdentityInsertAction insert)
/* 116:    */   {
/* 117:156 */     this.insertions.add(insert);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void addAction(BulkOperationCleanupAction cleanupAction)
/* 121:    */   {
/* 122:160 */     registerCleanupActions(cleanupAction);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void registerProcess(AfterTransactionCompletionProcess process)
/* 126:    */   {
/* 127:164 */     this.afterTransactionProcesses.register(process);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void registerProcess(BeforeTransactionCompletionProcess process)
/* 131:    */   {
/* 132:168 */     this.beforeTransactionProcesses.register(process);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void executeInserts()
/* 136:    */     throws HibernateException
/* 137:    */   {
/* 138:177 */     executeActions(this.insertions);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void executeActions()
/* 142:    */     throws HibernateException
/* 143:    */   {
/* 144:186 */     executeActions(this.insertions);
/* 145:187 */     executeActions(this.updates);
/* 146:188 */     executeActions(this.collectionRemovals);
/* 147:189 */     executeActions(this.collectionUpdates);
/* 148:190 */     executeActions(this.collectionCreations);
/* 149:191 */     executeActions(this.deletions);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void prepareActions()
/* 153:    */     throws HibernateException
/* 154:    */   {
/* 155:200 */     prepareActions(this.collectionRemovals);
/* 156:201 */     prepareActions(this.collectionUpdates);
/* 157:202 */     prepareActions(this.collectionCreations);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void afterTransactionCompletion(boolean success)
/* 161:    */   {
/* 162:211 */     this.afterTransactionProcesses.afterTransactionCompletion(success);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void beforeTransactionCompletion()
/* 166:    */   {
/* 167:218 */     this.beforeTransactionProcesses.beforeTransactionCompletion();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean areTablesToBeUpdated(Set tables)
/* 171:    */   {
/* 172:231 */     return (areTablesToUpdated(this.updates, tables)) || (areTablesToUpdated(this.insertions, tables)) || (areTablesToUpdated(this.deletions, tables)) || (areTablesToUpdated(this.collectionUpdates, tables)) || (areTablesToUpdated(this.collectionCreations, tables)) || (areTablesToUpdated(this.collectionRemovals, tables));
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean areInsertionsOrDeletionsQueued()
/* 176:    */   {
/* 177:245 */     return (this.insertions.size() > 0) || (this.deletions.size() > 0);
/* 178:    */   }
/* 179:    */   
/* 180:    */   private static boolean areTablesToUpdated(List actions, Set tableSpaces)
/* 181:    */   {
/* 182:250 */     for (Executable action : actions)
/* 183:    */     {
/* 184:251 */       Serializable[] spaces = action.getPropertySpaces();
/* 185:252 */       for (Serializable space : spaces) {
/* 186:253 */         if (tableSpaces.contains(space))
/* 187:    */         {
/* 188:254 */           LOG.debugf("Changes must be flushed to space: %s", space);
/* 189:255 */           return true;
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:259 */     return false;
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void executeActions(List list)
/* 197:    */     throws HibernateException
/* 198:    */   {
/* 199:263 */     int size = list.size();
/* 200:264 */     for (int i = 0; i < size; i++) {
/* 201:265 */       execute((Executable)list.get(i));
/* 202:    */     }
/* 203:267 */     list.clear();
/* 204:268 */     this.session.getTransactionCoordinator().getJdbcCoordinator().executeBatch();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void execute(Executable executable)
/* 208:    */   {
/* 209:    */     try
/* 210:    */     {
/* 211:273 */       executable.execute();
/* 212:    */     }
/* 213:    */     finally
/* 214:    */     {
/* 215:276 */       registerCleanupActions(executable);
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   private void registerCleanupActions(Executable executable)
/* 220:    */   {
/* 221:281 */     this.beforeTransactionProcesses.register(executable.getBeforeTransactionCompletionProcess());
/* 222:282 */     if (this.session.getFactory().getSettings().isQueryCacheEnabled())
/* 223:    */     {
/* 224:283 */       String[] spaces = (String[])executable.getPropertySpaces();
/* 225:284 */       if ((spaces != null) && (spaces.length > 0))
/* 226:    */       {
/* 227:285 */         this.afterTransactionProcesses.addSpacesToInvalidate(spaces);
/* 228:286 */         this.session.getFactory().getUpdateTimestampsCache().preinvalidate(spaces);
/* 229:    */       }
/* 230:    */     }
/* 231:289 */     this.afterTransactionProcesses.register(executable.getAfterTransactionCompletionProcess());
/* 232:    */   }
/* 233:    */   
/* 234:    */   private void prepareActions(List queue)
/* 235:    */     throws HibernateException
/* 236:    */   {
/* 237:294 */     for (Executable executable : queue) {
/* 238:295 */       executable.beforeExecutions();
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String toString()
/* 243:    */   {
/* 244:306 */     return "ActionQueue[insertions=" + this.insertions + " updates=" + this.updates + " deletions=" + this.deletions + " collectionCreations=" + this.collectionCreations + " collectionRemovals=" + this.collectionRemovals + " collectionUpdates=" + this.collectionUpdates + "]";
/* 245:    */   }
/* 246:    */   
/* 247:    */   public int numberOfCollectionRemovals()
/* 248:    */   {
/* 249:318 */     return this.collectionRemovals.size();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public int numberOfCollectionUpdates()
/* 253:    */   {
/* 254:322 */     return this.collectionUpdates.size();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public int numberOfCollectionCreations()
/* 258:    */   {
/* 259:326 */     return this.collectionCreations.size();
/* 260:    */   }
/* 261:    */   
/* 262:    */   public int numberOfDeletions()
/* 263:    */   {
/* 264:330 */     return this.deletions.size();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public int numberOfUpdates()
/* 268:    */   {
/* 269:334 */     return this.updates.size();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public int numberOfInsertions()
/* 273:    */   {
/* 274:338 */     return this.insertions.size();
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void sortCollectionActions()
/* 278:    */   {
/* 279:343 */     if (this.session.getFactory().getSettings().isOrderUpdatesEnabled())
/* 280:    */     {
/* 281:345 */       Collections.sort(this.collectionCreations);
/* 282:346 */       Collections.sort(this.collectionUpdates);
/* 283:347 */       Collections.sort(this.collectionRemovals);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void sortActions()
/* 288:    */   {
/* 289:353 */     if (this.session.getFactory().getSettings().isOrderUpdatesEnabled()) {
/* 290:355 */       Collections.sort(this.updates);
/* 291:    */     }
/* 292:357 */     if (this.session.getFactory().getSettings().isOrderInsertsEnabled()) {
/* 293:358 */       sortInsertActions();
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   private void sortInsertActions()
/* 298:    */   {
/* 299:372 */     new InsertActionSorter().sort();
/* 300:    */   }
/* 301:    */   
/* 302:    */   public ArrayList cloneDeletions()
/* 303:    */   {
/* 304:377 */     return (ArrayList)this.deletions.clone();
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void clearFromFlushNeededCheck(int previousCollectionRemovalSize)
/* 308:    */   {
/* 309:381 */     this.collectionCreations.clear();
/* 310:382 */     this.collectionUpdates.clear();
/* 311:383 */     this.updates.clear();
/* 312:386 */     for (int i = this.collectionRemovals.size() - 1; i >= previousCollectionRemovalSize; i--) {
/* 313:387 */       this.collectionRemovals.remove(i);
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   public boolean hasAfterTransactionActions()
/* 318:    */   {
/* 319:393 */     return this.afterTransactionProcesses.processes.size() > 0;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public boolean hasBeforeTransactionActions()
/* 323:    */   {
/* 324:397 */     return this.beforeTransactionProcesses.processes.size() > 0;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean hasAnyQueuedActions()
/* 328:    */   {
/* 329:401 */     return (this.updates.size() > 0) || (this.insertions.size() > 0) || (this.deletions.size() > 0) || (this.collectionUpdates.size() > 0) || (this.collectionRemovals.size() > 0) || (this.collectionCreations.size() > 0);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void serialize(ObjectOutputStream oos)
/* 333:    */     throws IOException
/* 334:    */   {
/* 335:418 */     LOG.trace("Serializing action-queue");
/* 336:    */     
/* 337:420 */     int queueSize = this.insertions.size();
/* 338:421 */     LOG.tracev("Starting serialization of [{0}] insertions entries", Integer.valueOf(queueSize));
/* 339:422 */     oos.writeInt(queueSize);
/* 340:423 */     for (int i = 0; i < queueSize; i++) {
/* 341:424 */       oos.writeObject(this.insertions.get(i));
/* 342:    */     }
/* 343:427 */     queueSize = this.deletions.size();
/* 344:428 */     LOG.tracev("Starting serialization of [{0}] deletions entries", Integer.valueOf(queueSize));
/* 345:429 */     oos.writeInt(queueSize);
/* 346:430 */     for (int i = 0; i < queueSize; i++) {
/* 347:431 */       oos.writeObject(this.deletions.get(i));
/* 348:    */     }
/* 349:434 */     queueSize = this.updates.size();
/* 350:435 */     LOG.tracev("Starting serialization of [{0}] updates entries", Integer.valueOf(queueSize));
/* 351:436 */     oos.writeInt(queueSize);
/* 352:437 */     for (int i = 0; i < queueSize; i++) {
/* 353:438 */       oos.writeObject(this.updates.get(i));
/* 354:    */     }
/* 355:441 */     queueSize = this.collectionUpdates.size();
/* 356:442 */     LOG.tracev("Starting serialization of [{0}] collectionUpdates entries", Integer.valueOf(queueSize));
/* 357:443 */     oos.writeInt(queueSize);
/* 358:444 */     for (int i = 0; i < queueSize; i++) {
/* 359:445 */       oos.writeObject(this.collectionUpdates.get(i));
/* 360:    */     }
/* 361:448 */     queueSize = this.collectionRemovals.size();
/* 362:449 */     LOG.tracev("Starting serialization of [{0}] collectionRemovals entries", Integer.valueOf(queueSize));
/* 363:450 */     oos.writeInt(queueSize);
/* 364:451 */     for (int i = 0; i < queueSize; i++) {
/* 365:452 */       oos.writeObject(this.collectionRemovals.get(i));
/* 366:    */     }
/* 367:455 */     queueSize = this.collectionCreations.size();
/* 368:456 */     LOG.tracev("Starting serialization of [{0}] collectionCreations entries", Integer.valueOf(queueSize));
/* 369:457 */     oos.writeInt(queueSize);
/* 370:458 */     for (int i = 0; i < queueSize; i++) {
/* 371:459 */       oos.writeObject(this.collectionCreations.get(i));
/* 372:    */     }
/* 373:    */   }
/* 374:    */   
/* 375:    */   public static ActionQueue deserialize(ObjectInputStream ois, SessionImplementor session)
/* 376:    */     throws IOException, ClassNotFoundException
/* 377:    */   {
/* 378:479 */     LOG.trace("Dedeserializing action-queue");
/* 379:480 */     ActionQueue rtn = new ActionQueue(session);
/* 380:    */     
/* 381:482 */     int queueSize = ois.readInt();
/* 382:483 */     LOG.tracev("Starting deserialization of [{0}] insertions entries", Integer.valueOf(queueSize));
/* 383:484 */     rtn.insertions = new ArrayList(queueSize);
/* 384:485 */     for (int i = 0; i < queueSize; i++)
/* 385:    */     {
/* 386:486 */       EntityAction action = (EntityAction)ois.readObject();
/* 387:487 */       action.afterDeserialize(session);
/* 388:488 */       rtn.insertions.add(action);
/* 389:    */     }
/* 390:491 */     queueSize = ois.readInt();
/* 391:492 */     LOG.tracev("Starting deserialization of [{0}] deletions entries", Integer.valueOf(queueSize));
/* 392:493 */     rtn.deletions = new ArrayList(queueSize);
/* 393:494 */     for (int i = 0; i < queueSize; i++)
/* 394:    */     {
/* 395:495 */       EntityAction action = (EntityAction)ois.readObject();
/* 396:496 */       action.afterDeserialize(session);
/* 397:497 */       rtn.deletions.add(action);
/* 398:    */     }
/* 399:500 */     queueSize = ois.readInt();
/* 400:501 */     LOG.tracev("Starting deserialization of [{0}] updates entries", Integer.valueOf(queueSize));
/* 401:502 */     rtn.updates = new ArrayList(queueSize);
/* 402:503 */     for (int i = 0; i < queueSize; i++)
/* 403:    */     {
/* 404:504 */       EntityAction action = (EntityAction)ois.readObject();
/* 405:505 */       action.afterDeserialize(session);
/* 406:506 */       rtn.updates.add(action);
/* 407:    */     }
/* 408:509 */     queueSize = ois.readInt();
/* 409:510 */     LOG.tracev("Starting deserialization of [{0}] collectionUpdates entries", Integer.valueOf(queueSize));
/* 410:511 */     rtn.collectionUpdates = new ArrayList(queueSize);
/* 411:512 */     for (int i = 0; i < queueSize; i++)
/* 412:    */     {
/* 413:513 */       CollectionAction action = (CollectionAction)ois.readObject();
/* 414:514 */       action.afterDeserialize(session);
/* 415:515 */       rtn.collectionUpdates.add(action);
/* 416:    */     }
/* 417:518 */     queueSize = ois.readInt();
/* 418:519 */     LOG.tracev("Starting deserialization of [{0}] collectionRemovals entries", Integer.valueOf(queueSize));
/* 419:520 */     rtn.collectionRemovals = new ArrayList(queueSize);
/* 420:521 */     for (int i = 0; i < queueSize; i++)
/* 421:    */     {
/* 422:522 */       CollectionAction action = (CollectionAction)ois.readObject();
/* 423:523 */       action.afterDeserialize(session);
/* 424:524 */       rtn.collectionRemovals.add(action);
/* 425:    */     }
/* 426:527 */     queueSize = ois.readInt();
/* 427:528 */     LOG.tracev("Starting deserialization of [{0}] collectionCreations entries", Integer.valueOf(queueSize));
/* 428:529 */     rtn.collectionCreations = new ArrayList(queueSize);
/* 429:530 */     for (int i = 0; i < queueSize; i++)
/* 430:    */     {
/* 431:531 */       CollectionAction action = (CollectionAction)ois.readObject();
/* 432:532 */       action.afterDeserialize(session);
/* 433:533 */       rtn.collectionCreations.add(action);
/* 434:    */     }
/* 435:535 */     return rtn;
/* 436:    */   }
/* 437:    */   
/* 438:    */   private static class BeforeTransactionCompletionProcessQueue
/* 439:    */   {
/* 440:    */     private SessionImplementor session;
/* 441:540 */     private List<BeforeTransactionCompletionProcess> processes = new ArrayList();
/* 442:    */     
/* 443:    */     private BeforeTransactionCompletionProcessQueue(SessionImplementor session)
/* 444:    */     {
/* 445:543 */       this.session = session;
/* 446:    */     }
/* 447:    */     
/* 448:    */     public void register(BeforeTransactionCompletionProcess process)
/* 449:    */     {
/* 450:547 */       if (process == null) {
/* 451:548 */         return;
/* 452:    */       }
/* 453:550 */       this.processes.add(process);
/* 454:    */     }
/* 455:    */     
/* 456:    */     public void beforeTransactionCompletion()
/* 457:    */     {
/* 458:554 */       int size = this.processes.size();
/* 459:555 */       for (int i = 0; i < size; i++) {
/* 460:    */         try
/* 461:    */         {
/* 462:557 */           BeforeTransactionCompletionProcess process = (BeforeTransactionCompletionProcess)this.processes.get(i);
/* 463:558 */           process.doBeforeTransactionCompletion(this.session);
/* 464:    */         }
/* 465:    */         catch (HibernateException he)
/* 466:    */         {
/* 467:561 */           throw he;
/* 468:    */         }
/* 469:    */         catch (Exception e)
/* 470:    */         {
/* 471:564 */           throw new AssertionFailure("Unable to perform beforeTransactionCompletion callback", e);
/* 472:    */         }
/* 473:    */       }
/* 474:567 */       this.processes.clear();
/* 475:    */     }
/* 476:    */   }
/* 477:    */   
/* 478:    */   private static class AfterTransactionCompletionProcessQueue
/* 479:    */   {
/* 480:    */     private SessionImplementor session;
/* 481:573 */     private Set<String> querySpacesToInvalidate = new HashSet();
/* 482:574 */     private List<AfterTransactionCompletionProcess> processes = new ArrayList(15);
/* 483:    */     
/* 484:    */     private AfterTransactionCompletionProcessQueue(SessionImplementor session)
/* 485:    */     {
/* 486:578 */       this.session = session;
/* 487:    */     }
/* 488:    */     
/* 489:    */     public void addSpacesToInvalidate(String[] spaces)
/* 490:    */     {
/* 491:582 */       for (String space : spaces) {
/* 492:583 */         addSpaceToInvalidate(space);
/* 493:    */       }
/* 494:    */     }
/* 495:    */     
/* 496:    */     public void addSpaceToInvalidate(String space)
/* 497:    */     {
/* 498:588 */       this.querySpacesToInvalidate.add(space);
/* 499:    */     }
/* 500:    */     
/* 501:    */     public void register(AfterTransactionCompletionProcess process)
/* 502:    */     {
/* 503:592 */       if (process == null) {
/* 504:593 */         return;
/* 505:    */       }
/* 506:595 */       this.processes.add(process);
/* 507:    */     }
/* 508:    */     
/* 509:    */     public void afterTransactionCompletion(boolean success)
/* 510:    */     {
/* 511:599 */       for (AfterTransactionCompletionProcess process : this.processes) {
/* 512:    */         try
/* 513:    */         {
/* 514:601 */           process.doAfterTransactionCompletion(success, this.session);
/* 515:    */         }
/* 516:    */         catch (CacheException ce)
/* 517:    */         {
/* 518:604 */           ActionQueue.LOG.unableToReleaseCacheLock(ce);
/* 519:    */         }
/* 520:    */         catch (Exception e)
/* 521:    */         {
/* 522:608 */           throw new AssertionFailure("Exception releasing cache locks", e);
/* 523:    */         }
/* 524:    */       }
/* 525:611 */       this.processes.clear();
/* 526:613 */       if (this.session.getFactory().getSettings().isQueryCacheEnabled()) {
/* 527:614 */         this.session.getFactory().getUpdateTimestampsCache().invalidate((Serializable[])this.querySpacesToInvalidate.toArray(new String[this.querySpacesToInvalidate.size()]));
/* 528:    */       }
/* 529:618 */       this.querySpacesToInvalidate.clear();
/* 530:    */     }
/* 531:    */   }
/* 532:    */   
/* 533:    */   private class InsertActionSorter
/* 534:    */   {
/* 535:629 */     private HashMap<String, Integer> latestBatches = new HashMap();
/* 536:    */     private HashMap<Object, Integer> entityBatchNumber;
/* 537:633 */     private HashMap<Integer, List<EntityInsertAction>> actionBatches = new HashMap();
/* 538:    */     
/* 539:    */     public InsertActionSorter()
/* 540:    */     {
/* 541:637 */       this.entityBatchNumber = new HashMap(ActionQueue.this.insertions.size() + 1, 1.0F);
/* 542:    */     }
/* 543:    */     
/* 544:    */     public void sort()
/* 545:    */     {
/* 546:646 */       for (EntityInsertAction action : ActionQueue.this.insertions)
/* 547:    */       {
/* 548:648 */         String entityName = action.getEntityName();
/* 549:    */         
/* 550:    */ 
/* 551:651 */         Object currentEntity = action.getInstance();
/* 552:    */         Integer batchNumber;
/* 553:    */         Integer batchNumber;
/* 554:654 */         if (this.latestBatches.containsKey(entityName))
/* 555:    */         {
/* 556:657 */           batchNumber = findBatchNumber(action, entityName);
/* 557:    */         }
/* 558:    */         else
/* 559:    */         {
/* 560:667 */           batchNumber = Integer.valueOf(this.actionBatches.size());
/* 561:668 */           this.latestBatches.put(entityName, batchNumber);
/* 562:    */         }
/* 563:670 */         this.entityBatchNumber.put(currentEntity, batchNumber);
/* 564:671 */         addToBatch(batchNumber, action);
/* 565:    */       }
/* 566:673 */       ActionQueue.this.insertions.clear();
/* 567:676 */       for (int i = 0; i < this.actionBatches.size(); i++)
/* 568:    */       {
/* 569:677 */         List<EntityInsertAction> batch = (List)this.actionBatches.get(Integer.valueOf(i));
/* 570:678 */         for (EntityInsertAction action : batch) {
/* 571:679 */           ActionQueue.this.insertions.add(action);
/* 572:    */         }
/* 573:    */       }
/* 574:    */     }
/* 575:    */     
/* 576:    */     private Integer findBatchNumber(EntityInsertAction action, String entityName)
/* 577:    */     {
/* 578:701 */       Integer latestBatchNumberForType = (Integer)this.latestBatches.get(entityName);
/* 579:    */       
/* 580:    */ 
/* 581:    */ 
/* 582:705 */       Object[] propertyValues = action.getState();
/* 583:706 */       Type[] propertyTypes = action.getPersister().getClassMetadata().getPropertyTypes();
/* 584:709 */       for (int i = 0; i < propertyValues.length; i++)
/* 585:    */       {
/* 586:710 */         Object value = propertyValues[i];
/* 587:711 */         Type type = propertyTypes[i];
/* 588:712 */         if ((type.isEntityType()) && (value != null))
/* 589:    */         {
/* 590:714 */           Integer associationBatchNumber = (Integer)this.entityBatchNumber.get(value);
/* 591:715 */           if ((associationBatchNumber != null) && (associationBatchNumber.compareTo(latestBatchNumberForType) > 0))
/* 592:    */           {
/* 593:717 */             latestBatchNumberForType = Integer.valueOf(this.actionBatches.size());
/* 594:718 */             this.latestBatches.put(entityName, latestBatchNumberForType);
/* 595:    */             
/* 596:    */ 
/* 597:    */ 
/* 598:722 */             break;
/* 599:    */           }
/* 600:    */         }
/* 601:    */       }
/* 602:726 */       return latestBatchNumberForType;
/* 603:    */     }
/* 604:    */     
/* 605:    */     private void addToBatch(Integer batchNumber, EntityInsertAction action)
/* 606:    */     {
/* 607:731 */       List<EntityInsertAction> actions = (List)this.actionBatches.get(batchNumber);
/* 608:733 */       if (actions == null)
/* 609:    */       {
/* 610:734 */         actions = new LinkedList();
/* 611:735 */         this.actionBatches.put(batchNumber, actions);
/* 612:    */       }
/* 613:737 */       actions.add(action);
/* 614:    */     }
/* 615:    */   }
/* 616:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.ActionQueue
 * JD-Core Version:    0.7.0.1
 */