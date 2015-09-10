/*   1:    */ package jcifs.dcerpc.msrpc;
/*   2:    */ 
/*   3:    */ import jcifs.dcerpc.DcerpcMessage;
/*   4:    */ import jcifs.dcerpc.ndr.NdrBuffer;
/*   5:    */ import jcifs.dcerpc.ndr.NdrException;
/*   6:    */ import jcifs.dcerpc.ndr.NdrLong;
/*   7:    */ import jcifs.dcerpc.ndr.NdrObject;
/*   8:    */ 
/*   9:    */ public class netdfs
/*  10:    */ {
/*  11:    */   public static final int DFS_VOLUME_FLAVOR_STANDALONE = 256;
/*  12:    */   public static final int DFS_VOLUME_FLAVOR_AD_BLOB = 512;
/*  13:    */   public static final int DFS_STORAGE_STATE_OFFLINE = 1;
/*  14:    */   public static final int DFS_STORAGE_STATE_ONLINE = 2;
/*  15:    */   public static final int DFS_STORAGE_STATE_ACTIVE = 4;
/*  16:    */   
/*  17:    */   public static String getSyntax()
/*  18:    */   {
/*  19:  9 */     return "4fc742e0-4a10-11cf-8273-00aa004ae673:3.0";
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static class DfsInfo1
/*  23:    */     extends NdrObject
/*  24:    */   {
/*  25:    */     public String entry_path;
/*  26:    */     
/*  27:    */     public void encode(NdrBuffer _dst)
/*  28:    */       throws NdrException
/*  29:    */     {
/*  30: 22 */       _dst.align(4);
/*  31: 23 */       _dst.enc_ndr_referent(this.entry_path, 1);
/*  32: 25 */       if (this.entry_path != null)
/*  33:    */       {
/*  34: 26 */         _dst = _dst.deferred;
/*  35: 27 */         _dst.enc_ndr_string(this.entry_path);
/*  36:    */       }
/*  37:    */     }
/*  38:    */     
/*  39:    */     public void decode(NdrBuffer _src)
/*  40:    */       throws NdrException
/*  41:    */     {
/*  42: 32 */       _src.align(4);
/*  43: 33 */       int _entry_pathp = _src.dec_ndr_long();
/*  44: 35 */       if (_entry_pathp != 0)
/*  45:    */       {
/*  46: 36 */         _src = _src.deferred;
/*  47: 37 */         this.entry_path = _src.dec_ndr_string();
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static class DfsEnumArray1
/*  53:    */     extends NdrObject
/*  54:    */   {
/*  55:    */     public int count;
/*  56:    */     public netdfs.DfsInfo1[] s;
/*  57:    */     
/*  58:    */     public void encode(NdrBuffer _dst)
/*  59:    */       throws NdrException
/*  60:    */     {
/*  61: 48 */       _dst.align(4);
/*  62: 49 */       _dst.enc_ndr_long(this.count);
/*  63: 50 */       _dst.enc_ndr_referent(this.s, 1);
/*  64: 52 */       if (this.s != null)
/*  65:    */       {
/*  66: 53 */         _dst = _dst.deferred;
/*  67: 54 */         int _ss = this.count;
/*  68: 55 */         _dst.enc_ndr_long(_ss);
/*  69: 56 */         int _si = _dst.index;
/*  70: 57 */         _dst.advance(4 * _ss);
/*  71:    */         
/*  72: 59 */         _dst = _dst.derive(_si);
/*  73: 60 */         for (int _i = 0; _i < _ss; _i++) {
/*  74: 61 */           this.s[_i].encode(_dst);
/*  75:    */         }
/*  76:    */       }
/*  77:    */     }
/*  78:    */     
/*  79:    */     public void decode(NdrBuffer _src)
/*  80:    */       throws NdrException
/*  81:    */     {
/*  82: 66 */       _src.align(4);
/*  83: 67 */       this.count = _src.dec_ndr_long();
/*  84: 68 */       int _sp = _src.dec_ndr_long();
/*  85: 70 */       if (_sp != 0)
/*  86:    */       {
/*  87: 71 */         _src = _src.deferred;
/*  88: 72 */         int _ss = _src.dec_ndr_long();
/*  89: 73 */         int _si = _src.index;
/*  90: 74 */         _src.advance(4 * _ss);
/*  91: 76 */         if (this.s == null)
/*  92:    */         {
/*  93: 77 */           if ((_ss < 0) || (_ss > 65535)) {
/*  94: 77 */             throw new NdrException("invalid array conformance");
/*  95:    */           }
/*  96: 78 */           this.s = new netdfs.DfsInfo1[_ss];
/*  97:    */         }
/*  98: 80 */         _src = _src.derive(_si);
/*  99: 81 */         for (int _i = 0; _i < _ss; _i++)
/* 100:    */         {
/* 101: 82 */           if (this.s[_i] == null) {
/* 102: 83 */             this.s[_i] = new netdfs.DfsInfo1();
/* 103:    */           }
/* 104: 85 */           this.s[_i].decode(_src);
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static class DfsStorageInfo
/* 111:    */     extends NdrObject
/* 112:    */   {
/* 113:    */     public int state;
/* 114:    */     public String server_name;
/* 115:    */     public String share_name;
/* 116:    */     
/* 117:    */     public void encode(NdrBuffer _dst)
/* 118:    */       throws NdrException
/* 119:    */     {
/* 120: 97 */       _dst.align(4);
/* 121: 98 */       _dst.enc_ndr_long(this.state);
/* 122: 99 */       _dst.enc_ndr_referent(this.server_name, 1);
/* 123:100 */       _dst.enc_ndr_referent(this.share_name, 1);
/* 124:102 */       if (this.server_name != null)
/* 125:    */       {
/* 126:103 */         _dst = _dst.deferred;
/* 127:104 */         _dst.enc_ndr_string(this.server_name);
/* 128:    */       }
/* 129:107 */       if (this.share_name != null)
/* 130:    */       {
/* 131:108 */         _dst = _dst.deferred;
/* 132:109 */         _dst.enc_ndr_string(this.share_name);
/* 133:    */       }
/* 134:    */     }
/* 135:    */     
/* 136:    */     public void decode(NdrBuffer _src)
/* 137:    */       throws NdrException
/* 138:    */     {
/* 139:114 */       _src.align(4);
/* 140:115 */       this.state = _src.dec_ndr_long();
/* 141:116 */       int _server_namep = _src.dec_ndr_long();
/* 142:117 */       int _share_namep = _src.dec_ndr_long();
/* 143:119 */       if (_server_namep != 0)
/* 144:    */       {
/* 145:120 */         _src = _src.deferred;
/* 146:121 */         this.server_name = _src.dec_ndr_string();
/* 147:    */       }
/* 148:124 */       if (_share_namep != 0)
/* 149:    */       {
/* 150:125 */         _src = _src.deferred;
/* 151:126 */         this.share_name = _src.dec_ndr_string();
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static class DfsInfo3
/* 157:    */     extends NdrObject
/* 158:    */   {
/* 159:    */     public String path;
/* 160:    */     public String comment;
/* 161:    */     public int state;
/* 162:    */     public int num_stores;
/* 163:    */     public netdfs.DfsStorageInfo[] stores;
/* 164:    */     
/* 165:    */     public void encode(NdrBuffer _dst)
/* 166:    */       throws NdrException
/* 167:    */     {
/* 168:140 */       _dst.align(4);
/* 169:141 */       _dst.enc_ndr_referent(this.path, 1);
/* 170:142 */       _dst.enc_ndr_referent(this.comment, 1);
/* 171:143 */       _dst.enc_ndr_long(this.state);
/* 172:144 */       _dst.enc_ndr_long(this.num_stores);
/* 173:145 */       _dst.enc_ndr_referent(this.stores, 1);
/* 174:147 */       if (this.path != null)
/* 175:    */       {
/* 176:148 */         _dst = _dst.deferred;
/* 177:149 */         _dst.enc_ndr_string(this.path);
/* 178:    */       }
/* 179:152 */       if (this.comment != null)
/* 180:    */       {
/* 181:153 */         _dst = _dst.deferred;
/* 182:154 */         _dst.enc_ndr_string(this.comment);
/* 183:    */       }
/* 184:157 */       if (this.stores != null)
/* 185:    */       {
/* 186:158 */         _dst = _dst.deferred;
/* 187:159 */         int _storess = this.num_stores;
/* 188:160 */         _dst.enc_ndr_long(_storess);
/* 189:161 */         int _storesi = _dst.index;
/* 190:162 */         _dst.advance(12 * _storess);
/* 191:    */         
/* 192:164 */         _dst = _dst.derive(_storesi);
/* 193:165 */         for (int _i = 0; _i < _storess; _i++) {
/* 194:166 */           this.stores[_i].encode(_dst);
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:    */     
/* 199:    */     public void decode(NdrBuffer _src)
/* 200:    */       throws NdrException
/* 201:    */     {
/* 202:171 */       _src.align(4);
/* 203:172 */       int _pathp = _src.dec_ndr_long();
/* 204:173 */       int _commentp = _src.dec_ndr_long();
/* 205:174 */       this.state = _src.dec_ndr_long();
/* 206:175 */       this.num_stores = _src.dec_ndr_long();
/* 207:176 */       int _storesp = _src.dec_ndr_long();
/* 208:178 */       if (_pathp != 0)
/* 209:    */       {
/* 210:179 */         _src = _src.deferred;
/* 211:180 */         this.path = _src.dec_ndr_string();
/* 212:    */       }
/* 213:183 */       if (_commentp != 0)
/* 214:    */       {
/* 215:184 */         _src = _src.deferred;
/* 216:185 */         this.comment = _src.dec_ndr_string();
/* 217:    */       }
/* 218:188 */       if (_storesp != 0)
/* 219:    */       {
/* 220:189 */         _src = _src.deferred;
/* 221:190 */         int _storess = _src.dec_ndr_long();
/* 222:191 */         int _storesi = _src.index;
/* 223:192 */         _src.advance(12 * _storess);
/* 224:194 */         if (this.stores == null)
/* 225:    */         {
/* 226:195 */           if ((_storess < 0) || (_storess > 65535)) {
/* 227:195 */             throw new NdrException("invalid array conformance");
/* 228:    */           }
/* 229:196 */           this.stores = new netdfs.DfsStorageInfo[_storess];
/* 230:    */         }
/* 231:198 */         _src = _src.derive(_storesi);
/* 232:199 */         for (int _i = 0; _i < _storess; _i++)
/* 233:    */         {
/* 234:200 */           if (this.stores[_i] == null) {
/* 235:201 */             this.stores[_i] = new netdfs.DfsStorageInfo();
/* 236:    */           }
/* 237:203 */           this.stores[_i].decode(_src);
/* 238:    */         }
/* 239:    */       }
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public static class DfsEnumArray3
/* 244:    */     extends NdrObject
/* 245:    */   {
/* 246:    */     public int count;
/* 247:    */     public netdfs.DfsInfo3[] s;
/* 248:    */     
/* 249:    */     public void encode(NdrBuffer _dst)
/* 250:    */       throws NdrException
/* 251:    */     {
/* 252:214 */       _dst.align(4);
/* 253:215 */       _dst.enc_ndr_long(this.count);
/* 254:216 */       _dst.enc_ndr_referent(this.s, 1);
/* 255:218 */       if (this.s != null)
/* 256:    */       {
/* 257:219 */         _dst = _dst.deferred;
/* 258:220 */         int _ss = this.count;
/* 259:221 */         _dst.enc_ndr_long(_ss);
/* 260:222 */         int _si = _dst.index;
/* 261:223 */         _dst.advance(20 * _ss);
/* 262:    */         
/* 263:225 */         _dst = _dst.derive(_si);
/* 264:226 */         for (int _i = 0; _i < _ss; _i++) {
/* 265:227 */           this.s[_i].encode(_dst);
/* 266:    */         }
/* 267:    */       }
/* 268:    */     }
/* 269:    */     
/* 270:    */     public void decode(NdrBuffer _src)
/* 271:    */       throws NdrException
/* 272:    */     {
/* 273:232 */       _src.align(4);
/* 274:233 */       this.count = _src.dec_ndr_long();
/* 275:234 */       int _sp = _src.dec_ndr_long();
/* 276:236 */       if (_sp != 0)
/* 277:    */       {
/* 278:237 */         _src = _src.deferred;
/* 279:238 */         int _ss = _src.dec_ndr_long();
/* 280:239 */         int _si = _src.index;
/* 281:240 */         _src.advance(20 * _ss);
/* 282:242 */         if (this.s == null)
/* 283:    */         {
/* 284:243 */           if ((_ss < 0) || (_ss > 65535)) {
/* 285:243 */             throw new NdrException("invalid array conformance");
/* 286:    */           }
/* 287:244 */           this.s = new netdfs.DfsInfo3[_ss];
/* 288:    */         }
/* 289:246 */         _src = _src.derive(_si);
/* 290:247 */         for (int _i = 0; _i < _ss; _i++)
/* 291:    */         {
/* 292:248 */           if (this.s[_i] == null) {
/* 293:249 */             this.s[_i] = new netdfs.DfsInfo3();
/* 294:    */           }
/* 295:251 */           this.s[_i].decode(_src);
/* 296:    */         }
/* 297:    */       }
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public static class DfsInfo200
/* 302:    */     extends NdrObject
/* 303:    */   {
/* 304:    */     public String dfs_name;
/* 305:    */     
/* 306:    */     public void encode(NdrBuffer _dst)
/* 307:    */       throws NdrException
/* 308:    */     {
/* 309:261 */       _dst.align(4);
/* 310:262 */       _dst.enc_ndr_referent(this.dfs_name, 1);
/* 311:264 */       if (this.dfs_name != null)
/* 312:    */       {
/* 313:265 */         _dst = _dst.deferred;
/* 314:266 */         _dst.enc_ndr_string(this.dfs_name);
/* 315:    */       }
/* 316:    */     }
/* 317:    */     
/* 318:    */     public void decode(NdrBuffer _src)
/* 319:    */       throws NdrException
/* 320:    */     {
/* 321:271 */       _src.align(4);
/* 322:272 */       int _dfs_namep = _src.dec_ndr_long();
/* 323:274 */       if (_dfs_namep != 0)
/* 324:    */       {
/* 325:275 */         _src = _src.deferred;
/* 326:276 */         this.dfs_name = _src.dec_ndr_string();
/* 327:    */       }
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public static class DfsEnumArray200
/* 332:    */     extends NdrObject
/* 333:    */   {
/* 334:    */     public int count;
/* 335:    */     public netdfs.DfsInfo200[] s;
/* 336:    */     
/* 337:    */     public void encode(NdrBuffer _dst)
/* 338:    */       throws NdrException
/* 339:    */     {
/* 340:287 */       _dst.align(4);
/* 341:288 */       _dst.enc_ndr_long(this.count);
/* 342:289 */       _dst.enc_ndr_referent(this.s, 1);
/* 343:291 */       if (this.s != null)
/* 344:    */       {
/* 345:292 */         _dst = _dst.deferred;
/* 346:293 */         int _ss = this.count;
/* 347:294 */         _dst.enc_ndr_long(_ss);
/* 348:295 */         int _si = _dst.index;
/* 349:296 */         _dst.advance(4 * _ss);
/* 350:    */         
/* 351:298 */         _dst = _dst.derive(_si);
/* 352:299 */         for (int _i = 0; _i < _ss; _i++) {
/* 353:300 */           this.s[_i].encode(_dst);
/* 354:    */         }
/* 355:    */       }
/* 356:    */     }
/* 357:    */     
/* 358:    */     public void decode(NdrBuffer _src)
/* 359:    */       throws NdrException
/* 360:    */     {
/* 361:305 */       _src.align(4);
/* 362:306 */       this.count = _src.dec_ndr_long();
/* 363:307 */       int _sp = _src.dec_ndr_long();
/* 364:309 */       if (_sp != 0)
/* 365:    */       {
/* 366:310 */         _src = _src.deferred;
/* 367:311 */         int _ss = _src.dec_ndr_long();
/* 368:312 */         int _si = _src.index;
/* 369:313 */         _src.advance(4 * _ss);
/* 370:315 */         if (this.s == null)
/* 371:    */         {
/* 372:316 */           if ((_ss < 0) || (_ss > 65535)) {
/* 373:316 */             throw new NdrException("invalid array conformance");
/* 374:    */           }
/* 375:317 */           this.s = new netdfs.DfsInfo200[_ss];
/* 376:    */         }
/* 377:319 */         _src = _src.derive(_si);
/* 378:320 */         for (int _i = 0; _i < _ss; _i++)
/* 379:    */         {
/* 380:321 */           if (this.s[_i] == null) {
/* 381:322 */             this.s[_i] = new netdfs.DfsInfo200();
/* 382:    */           }
/* 383:324 */           this.s[_i].decode(_src);
/* 384:    */         }
/* 385:    */       }
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   public static class DfsInfo300
/* 390:    */     extends NdrObject
/* 391:    */   {
/* 392:    */     public int flags;
/* 393:    */     public String dfs_name;
/* 394:    */     
/* 395:    */     public void encode(NdrBuffer _dst)
/* 396:    */       throws NdrException
/* 397:    */     {
/* 398:335 */       _dst.align(4);
/* 399:336 */       _dst.enc_ndr_long(this.flags);
/* 400:337 */       _dst.enc_ndr_referent(this.dfs_name, 1);
/* 401:339 */       if (this.dfs_name != null)
/* 402:    */       {
/* 403:340 */         _dst = _dst.deferred;
/* 404:341 */         _dst.enc_ndr_string(this.dfs_name);
/* 405:    */       }
/* 406:    */     }
/* 407:    */     
/* 408:    */     public void decode(NdrBuffer _src)
/* 409:    */       throws NdrException
/* 410:    */     {
/* 411:346 */       _src.align(4);
/* 412:347 */       this.flags = _src.dec_ndr_long();
/* 413:348 */       int _dfs_namep = _src.dec_ndr_long();
/* 414:350 */       if (_dfs_namep != 0)
/* 415:    */       {
/* 416:351 */         _src = _src.deferred;
/* 417:352 */         this.dfs_name = _src.dec_ndr_string();
/* 418:    */       }
/* 419:    */     }
/* 420:    */   }
/* 421:    */   
/* 422:    */   public static class DfsEnumArray300
/* 423:    */     extends NdrObject
/* 424:    */   {
/* 425:    */     public int count;
/* 426:    */     public netdfs.DfsInfo300[] s;
/* 427:    */     
/* 428:    */     public void encode(NdrBuffer _dst)
/* 429:    */       throws NdrException
/* 430:    */     {
/* 431:363 */       _dst.align(4);
/* 432:364 */       _dst.enc_ndr_long(this.count);
/* 433:365 */       _dst.enc_ndr_referent(this.s, 1);
/* 434:367 */       if (this.s != null)
/* 435:    */       {
/* 436:368 */         _dst = _dst.deferred;
/* 437:369 */         int _ss = this.count;
/* 438:370 */         _dst.enc_ndr_long(_ss);
/* 439:371 */         int _si = _dst.index;
/* 440:372 */         _dst.advance(8 * _ss);
/* 441:    */         
/* 442:374 */         _dst = _dst.derive(_si);
/* 443:375 */         for (int _i = 0; _i < _ss; _i++) {
/* 444:376 */           this.s[_i].encode(_dst);
/* 445:    */         }
/* 446:    */       }
/* 447:    */     }
/* 448:    */     
/* 449:    */     public void decode(NdrBuffer _src)
/* 450:    */       throws NdrException
/* 451:    */     {
/* 452:381 */       _src.align(4);
/* 453:382 */       this.count = _src.dec_ndr_long();
/* 454:383 */       int _sp = _src.dec_ndr_long();
/* 455:385 */       if (_sp != 0)
/* 456:    */       {
/* 457:386 */         _src = _src.deferred;
/* 458:387 */         int _ss = _src.dec_ndr_long();
/* 459:388 */         int _si = _src.index;
/* 460:389 */         _src.advance(8 * _ss);
/* 461:391 */         if (this.s == null)
/* 462:    */         {
/* 463:392 */           if ((_ss < 0) || (_ss > 65535)) {
/* 464:392 */             throw new NdrException("invalid array conformance");
/* 465:    */           }
/* 466:393 */           this.s = new netdfs.DfsInfo300[_ss];
/* 467:    */         }
/* 468:395 */         _src = _src.derive(_si);
/* 469:396 */         for (int _i = 0; _i < _ss; _i++)
/* 470:    */         {
/* 471:397 */           if (this.s[_i] == null) {
/* 472:398 */             this.s[_i] = new netdfs.DfsInfo300();
/* 473:    */           }
/* 474:400 */           this.s[_i].decode(_src);
/* 475:    */         }
/* 476:    */       }
/* 477:    */     }
/* 478:    */   }
/* 479:    */   
/* 480:    */   public static class DfsEnumStruct
/* 481:    */     extends NdrObject
/* 482:    */   {
/* 483:    */     public int level;
/* 484:    */     public NdrObject e;
/* 485:    */     
/* 486:    */     public void encode(NdrBuffer _dst)
/* 487:    */       throws NdrException
/* 488:    */     {
/* 489:411 */       _dst.align(4);
/* 490:412 */       _dst.enc_ndr_long(this.level);
/* 491:413 */       int _descr = this.level;
/* 492:414 */       _dst.enc_ndr_long(_descr);
/* 493:415 */       _dst.enc_ndr_referent(this.e, 1);
/* 494:417 */       if (this.e != null)
/* 495:    */       {
/* 496:418 */         _dst = _dst.deferred;
/* 497:419 */         this.e.encode(_dst);
/* 498:    */       }
/* 499:    */     }
/* 500:    */     
/* 501:    */     public void decode(NdrBuffer _src)
/* 502:    */       throws NdrException
/* 503:    */     {
/* 504:424 */       _src.align(4);
/* 505:425 */       this.level = _src.dec_ndr_long();
/* 506:426 */       _src.dec_ndr_long();
/* 507:427 */       int _ep = _src.dec_ndr_long();
/* 508:429 */       if (_ep != 0)
/* 509:    */       {
/* 510:430 */         if (this.e == null) {
/* 511:431 */           this.e = new netdfs.DfsEnumArray1();
/* 512:    */         }
/* 513:433 */         _src = _src.deferred;
/* 514:434 */         this.e.decode(_src);
/* 515:    */       }
/* 516:    */     }
/* 517:    */   }
/* 518:    */   
/* 519:    */   public static class NetrDfsEnumEx
/* 520:    */     extends DcerpcMessage
/* 521:    */   {
/* 522:    */     public int retval;
/* 523:    */     public String dfs_name;
/* 524:    */     public int level;
/* 525:    */     public int prefmaxlen;
/* 526:    */     public netdfs.DfsEnumStruct info;
/* 527:    */     public NdrLong totalentries;
/* 528:    */     
/* 529:    */     public int getOpnum()
/* 530:    */     {
/* 531:441 */       return 21;
/* 532:    */     }
/* 533:    */     
/* 534:    */     public NetrDfsEnumEx(String dfs_name, int level, int prefmaxlen, netdfs.DfsEnumStruct info, NdrLong totalentries)
/* 535:    */     {
/* 536:455 */       this.dfs_name = dfs_name;
/* 537:456 */       this.level = level;
/* 538:457 */       this.prefmaxlen = prefmaxlen;
/* 539:458 */       this.info = info;
/* 540:459 */       this.totalentries = totalentries;
/* 541:    */     }
/* 542:    */     
/* 543:    */     public void encode_in(NdrBuffer _dst)
/* 544:    */       throws NdrException
/* 545:    */     {
/* 546:463 */       _dst.enc_ndr_string(this.dfs_name);
/* 547:464 */       _dst.enc_ndr_long(this.level);
/* 548:465 */       _dst.enc_ndr_long(this.prefmaxlen);
/* 549:466 */       _dst.enc_ndr_referent(this.info, 1);
/* 550:467 */       if (this.info != null) {
/* 551:468 */         this.info.encode(_dst);
/* 552:    */       }
/* 553:471 */       _dst.enc_ndr_referent(this.totalentries, 1);
/* 554:472 */       if (this.totalentries != null) {
/* 555:473 */         this.totalentries.encode(_dst);
/* 556:    */       }
/* 557:    */     }
/* 558:    */     
/* 559:    */     public void decode_out(NdrBuffer _src)
/* 560:    */       throws NdrException
/* 561:    */     {
/* 562:478 */       int _infop = _src.dec_ndr_long();
/* 563:479 */       if (_infop != 0)
/* 564:    */       {
/* 565:480 */         if (this.info == null) {
/* 566:481 */           this.info = new netdfs.DfsEnumStruct();
/* 567:    */         }
/* 568:483 */         this.info.decode(_src);
/* 569:    */       }
/* 570:486 */       int _totalentriesp = _src.dec_ndr_long();
/* 571:487 */       if (_totalentriesp != 0) {
/* 572:488 */         this.totalentries.decode(_src);
/* 573:    */       }
/* 574:491 */       this.retval = _src.dec_ndr_long();
/* 575:    */     }
/* 576:    */   }
/* 577:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.netdfs
 * JD-Core Version:    0.7.0.1
 */