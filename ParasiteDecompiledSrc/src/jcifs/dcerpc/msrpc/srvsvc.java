/*   1:    */ package jcifs.dcerpc.msrpc;
/*   2:    */ 
/*   3:    */ import jcifs.dcerpc.DcerpcMessage;
/*   4:    */ import jcifs.dcerpc.ndr.NdrBuffer;
/*   5:    */ import jcifs.dcerpc.ndr.NdrException;
/*   6:    */ import jcifs.dcerpc.ndr.NdrObject;
/*   7:    */ 
/*   8:    */ public class srvsvc
/*   9:    */ {
/*  10:    */   public static String getSyntax()
/*  11:    */   {
/*  12: 11 */     return "4b324fc8-1670-01d3-1278-5a47bf6ee188:3.0";
/*  13:    */   }
/*  14:    */   
/*  15:    */   public static class ShareInfo0
/*  16:    */     extends NdrObject
/*  17:    */   {
/*  18:    */     public String netname;
/*  19:    */     
/*  20:    */     public void encode(NdrBuffer _dst)
/*  21:    */       throws NdrException
/*  22:    */     {
/*  23: 19 */       _dst.align(4);
/*  24: 20 */       _dst.enc_ndr_referent(this.netname, 1);
/*  25: 22 */       if (this.netname != null)
/*  26:    */       {
/*  27: 23 */         _dst = _dst.deferred;
/*  28: 24 */         _dst.enc_ndr_string(this.netname);
/*  29:    */       }
/*  30:    */     }
/*  31:    */     
/*  32:    */     public void decode(NdrBuffer _src)
/*  33:    */       throws NdrException
/*  34:    */     {
/*  35: 29 */       _src.align(4);
/*  36: 30 */       int _netnamep = _src.dec_ndr_long();
/*  37: 32 */       if (_netnamep != 0)
/*  38:    */       {
/*  39: 33 */         _src = _src.deferred;
/*  40: 34 */         this.netname = _src.dec_ndr_string();
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static class ShareInfoCtr0
/*  46:    */     extends NdrObject
/*  47:    */   {
/*  48:    */     public int count;
/*  49:    */     public srvsvc.ShareInfo0[] array;
/*  50:    */     
/*  51:    */     public void encode(NdrBuffer _dst)
/*  52:    */       throws NdrException
/*  53:    */     {
/*  54: 45 */       _dst.align(4);
/*  55: 46 */       _dst.enc_ndr_long(this.count);
/*  56: 47 */       _dst.enc_ndr_referent(this.array, 1);
/*  57: 49 */       if (this.array != null)
/*  58:    */       {
/*  59: 50 */         _dst = _dst.deferred;
/*  60: 51 */         int _arrays = this.count;
/*  61: 52 */         _dst.enc_ndr_long(_arrays);
/*  62: 53 */         int _arrayi = _dst.index;
/*  63: 54 */         _dst.advance(4 * _arrays);
/*  64:    */         
/*  65: 56 */         _dst = _dst.derive(_arrayi);
/*  66: 57 */         for (int _i = 0; _i < _arrays; _i++) {
/*  67: 58 */           this.array[_i].encode(_dst);
/*  68:    */         }
/*  69:    */       }
/*  70:    */     }
/*  71:    */     
/*  72:    */     public void decode(NdrBuffer _src)
/*  73:    */       throws NdrException
/*  74:    */     {
/*  75: 63 */       _src.align(4);
/*  76: 64 */       this.count = _src.dec_ndr_long();
/*  77: 65 */       int _arrayp = _src.dec_ndr_long();
/*  78: 67 */       if (_arrayp != 0)
/*  79:    */       {
/*  80: 68 */         _src = _src.deferred;
/*  81: 69 */         int _arrays = _src.dec_ndr_long();
/*  82: 70 */         int _arrayi = _src.index;
/*  83: 71 */         _src.advance(4 * _arrays);
/*  84: 73 */         if (this.array == null)
/*  85:    */         {
/*  86: 74 */           if ((_arrays < 0) || (_arrays > 65535)) {
/*  87: 74 */             throw new NdrException("invalid array conformance");
/*  88:    */           }
/*  89: 75 */           this.array = new srvsvc.ShareInfo0[_arrays];
/*  90:    */         }
/*  91: 77 */         _src = _src.derive(_arrayi);
/*  92: 78 */         for (int _i = 0; _i < _arrays; _i++)
/*  93:    */         {
/*  94: 79 */           if (this.array[_i] == null) {
/*  95: 80 */             this.array[_i] = new srvsvc.ShareInfo0();
/*  96:    */           }
/*  97: 82 */           this.array[_i].decode(_src);
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static class ShareInfo1
/* 104:    */     extends NdrObject
/* 105:    */   {
/* 106:    */     public String netname;
/* 107:    */     public int type;
/* 108:    */     public String remark;
/* 109:    */     
/* 110:    */     public void encode(NdrBuffer _dst)
/* 111:    */       throws NdrException
/* 112:    */     {
/* 113: 94 */       _dst.align(4);
/* 114: 95 */       _dst.enc_ndr_referent(this.netname, 1);
/* 115: 96 */       _dst.enc_ndr_long(this.type);
/* 116: 97 */       _dst.enc_ndr_referent(this.remark, 1);
/* 117: 99 */       if (this.netname != null)
/* 118:    */       {
/* 119:100 */         _dst = _dst.deferred;
/* 120:101 */         _dst.enc_ndr_string(this.netname);
/* 121:    */       }
/* 122:104 */       if (this.remark != null)
/* 123:    */       {
/* 124:105 */         _dst = _dst.deferred;
/* 125:106 */         _dst.enc_ndr_string(this.remark);
/* 126:    */       }
/* 127:    */     }
/* 128:    */     
/* 129:    */     public void decode(NdrBuffer _src)
/* 130:    */       throws NdrException
/* 131:    */     {
/* 132:111 */       _src.align(4);
/* 133:112 */       int _netnamep = _src.dec_ndr_long();
/* 134:113 */       this.type = _src.dec_ndr_long();
/* 135:114 */       int _remarkp = _src.dec_ndr_long();
/* 136:116 */       if (_netnamep != 0)
/* 137:    */       {
/* 138:117 */         _src = _src.deferred;
/* 139:118 */         this.netname = _src.dec_ndr_string();
/* 140:    */       }
/* 141:121 */       if (_remarkp != 0)
/* 142:    */       {
/* 143:122 */         _src = _src.deferred;
/* 144:123 */         this.remark = _src.dec_ndr_string();
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static class ShareInfoCtr1
/* 150:    */     extends NdrObject
/* 151:    */   {
/* 152:    */     public int count;
/* 153:    */     public srvsvc.ShareInfo1[] array;
/* 154:    */     
/* 155:    */     public void encode(NdrBuffer _dst)
/* 156:    */       throws NdrException
/* 157:    */     {
/* 158:134 */       _dst.align(4);
/* 159:135 */       _dst.enc_ndr_long(this.count);
/* 160:136 */       _dst.enc_ndr_referent(this.array, 1);
/* 161:138 */       if (this.array != null)
/* 162:    */       {
/* 163:139 */         _dst = _dst.deferred;
/* 164:140 */         int _arrays = this.count;
/* 165:141 */         _dst.enc_ndr_long(_arrays);
/* 166:142 */         int _arrayi = _dst.index;
/* 167:143 */         _dst.advance(12 * _arrays);
/* 168:    */         
/* 169:145 */         _dst = _dst.derive(_arrayi);
/* 170:146 */         for (int _i = 0; _i < _arrays; _i++) {
/* 171:147 */           this.array[_i].encode(_dst);
/* 172:    */         }
/* 173:    */       }
/* 174:    */     }
/* 175:    */     
/* 176:    */     public void decode(NdrBuffer _src)
/* 177:    */       throws NdrException
/* 178:    */     {
/* 179:152 */       _src.align(4);
/* 180:153 */       this.count = _src.dec_ndr_long();
/* 181:154 */       int _arrayp = _src.dec_ndr_long();
/* 182:156 */       if (_arrayp != 0)
/* 183:    */       {
/* 184:157 */         _src = _src.deferred;
/* 185:158 */         int _arrays = _src.dec_ndr_long();
/* 186:159 */         int _arrayi = _src.index;
/* 187:160 */         _src.advance(12 * _arrays);
/* 188:162 */         if (this.array == null)
/* 189:    */         {
/* 190:163 */           if ((_arrays < 0) || (_arrays > 65535)) {
/* 191:163 */             throw new NdrException("invalid array conformance");
/* 192:    */           }
/* 193:164 */           this.array = new srvsvc.ShareInfo1[_arrays];
/* 194:    */         }
/* 195:166 */         _src = _src.derive(_arrayi);
/* 196:167 */         for (int _i = 0; _i < _arrays; _i++)
/* 197:    */         {
/* 198:168 */           if (this.array[_i] == null) {
/* 199:169 */             this.array[_i] = new srvsvc.ShareInfo1();
/* 200:    */           }
/* 201:171 */           this.array[_i].decode(_src);
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static class ShareInfo502
/* 208:    */     extends NdrObject
/* 209:    */   {
/* 210:    */     public String netname;
/* 211:    */     public int type;
/* 212:    */     public String remark;
/* 213:    */     public int permissions;
/* 214:    */     public int max_uses;
/* 215:    */     public int current_uses;
/* 216:    */     public String path;
/* 217:    */     public String password;
/* 218:    */     public int sd_size;
/* 219:    */     public byte[] security_descriptor;
/* 220:    */     
/* 221:    */     public void encode(NdrBuffer _dst)
/* 222:    */       throws NdrException
/* 223:    */     {
/* 224:190 */       _dst.align(4);
/* 225:191 */       _dst.enc_ndr_referent(this.netname, 1);
/* 226:192 */       _dst.enc_ndr_long(this.type);
/* 227:193 */       _dst.enc_ndr_referent(this.remark, 1);
/* 228:194 */       _dst.enc_ndr_long(this.permissions);
/* 229:195 */       _dst.enc_ndr_long(this.max_uses);
/* 230:196 */       _dst.enc_ndr_long(this.current_uses);
/* 231:197 */       _dst.enc_ndr_referent(this.path, 1);
/* 232:198 */       _dst.enc_ndr_referent(this.password, 1);
/* 233:199 */       _dst.enc_ndr_long(this.sd_size);
/* 234:200 */       _dst.enc_ndr_referent(this.security_descriptor, 1);
/* 235:202 */       if (this.netname != null)
/* 236:    */       {
/* 237:203 */         _dst = _dst.deferred;
/* 238:204 */         _dst.enc_ndr_string(this.netname);
/* 239:    */       }
/* 240:207 */       if (this.remark != null)
/* 241:    */       {
/* 242:208 */         _dst = _dst.deferred;
/* 243:209 */         _dst.enc_ndr_string(this.remark);
/* 244:    */       }
/* 245:212 */       if (this.path != null)
/* 246:    */       {
/* 247:213 */         _dst = _dst.deferred;
/* 248:214 */         _dst.enc_ndr_string(this.path);
/* 249:    */       }
/* 250:217 */       if (this.password != null)
/* 251:    */       {
/* 252:218 */         _dst = _dst.deferred;
/* 253:219 */         _dst.enc_ndr_string(this.password);
/* 254:    */       }
/* 255:222 */       if (this.security_descriptor != null)
/* 256:    */       {
/* 257:223 */         _dst = _dst.deferred;
/* 258:224 */         int _security_descriptors = this.sd_size;
/* 259:225 */         _dst.enc_ndr_long(_security_descriptors);
/* 260:226 */         int _security_descriptori = _dst.index;
/* 261:227 */         _dst.advance(1 * _security_descriptors);
/* 262:    */         
/* 263:229 */         _dst = _dst.derive(_security_descriptori);
/* 264:230 */         for (int _i = 0; _i < _security_descriptors; _i++) {
/* 265:231 */           _dst.enc_ndr_small(this.security_descriptor[_i]);
/* 266:    */         }
/* 267:    */       }
/* 268:    */     }
/* 269:    */     
/* 270:    */     public void decode(NdrBuffer _src)
/* 271:    */       throws NdrException
/* 272:    */     {
/* 273:236 */       _src.align(4);
/* 274:237 */       int _netnamep = _src.dec_ndr_long();
/* 275:238 */       this.type = _src.dec_ndr_long();
/* 276:239 */       int _remarkp = _src.dec_ndr_long();
/* 277:240 */       this.permissions = _src.dec_ndr_long();
/* 278:241 */       this.max_uses = _src.dec_ndr_long();
/* 279:242 */       this.current_uses = _src.dec_ndr_long();
/* 280:243 */       int _pathp = _src.dec_ndr_long();
/* 281:244 */       int _passwordp = _src.dec_ndr_long();
/* 282:245 */       this.sd_size = _src.dec_ndr_long();
/* 283:246 */       int _security_descriptorp = _src.dec_ndr_long();
/* 284:248 */       if (_netnamep != 0)
/* 285:    */       {
/* 286:249 */         _src = _src.deferred;
/* 287:250 */         this.netname = _src.dec_ndr_string();
/* 288:    */       }
/* 289:253 */       if (_remarkp != 0)
/* 290:    */       {
/* 291:254 */         _src = _src.deferred;
/* 292:255 */         this.remark = _src.dec_ndr_string();
/* 293:    */       }
/* 294:258 */       if (_pathp != 0)
/* 295:    */       {
/* 296:259 */         _src = _src.deferred;
/* 297:260 */         this.path = _src.dec_ndr_string();
/* 298:    */       }
/* 299:263 */       if (_passwordp != 0)
/* 300:    */       {
/* 301:264 */         _src = _src.deferred;
/* 302:265 */         this.password = _src.dec_ndr_string();
/* 303:    */       }
/* 304:268 */       if (_security_descriptorp != 0)
/* 305:    */       {
/* 306:269 */         _src = _src.deferred;
/* 307:270 */         int _security_descriptors = _src.dec_ndr_long();
/* 308:271 */         int _security_descriptori = _src.index;
/* 309:272 */         _src.advance(1 * _security_descriptors);
/* 310:274 */         if (this.security_descriptor == null)
/* 311:    */         {
/* 312:275 */           if ((_security_descriptors < 0) || (_security_descriptors > 65535)) {
/* 313:275 */             throw new NdrException("invalid array conformance");
/* 314:    */           }
/* 315:276 */           this.security_descriptor = new byte[_security_descriptors];
/* 316:    */         }
/* 317:278 */         _src = _src.derive(_security_descriptori);
/* 318:279 */         for (int _i = 0; _i < _security_descriptors; _i++) {
/* 319:280 */           this.security_descriptor[_i] = ((byte)_src.dec_ndr_small());
/* 320:    */         }
/* 321:    */       }
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   public static class ShareInfoCtr502
/* 326:    */     extends NdrObject
/* 327:    */   {
/* 328:    */     public int count;
/* 329:    */     public srvsvc.ShareInfo502[] array;
/* 330:    */     
/* 331:    */     public void encode(NdrBuffer _dst)
/* 332:    */       throws NdrException
/* 333:    */     {
/* 334:291 */       _dst.align(4);
/* 335:292 */       _dst.enc_ndr_long(this.count);
/* 336:293 */       _dst.enc_ndr_referent(this.array, 1);
/* 337:295 */       if (this.array != null)
/* 338:    */       {
/* 339:296 */         _dst = _dst.deferred;
/* 340:297 */         int _arrays = this.count;
/* 341:298 */         _dst.enc_ndr_long(_arrays);
/* 342:299 */         int _arrayi = _dst.index;
/* 343:300 */         _dst.advance(40 * _arrays);
/* 344:    */         
/* 345:302 */         _dst = _dst.derive(_arrayi);
/* 346:303 */         for (int _i = 0; _i < _arrays; _i++) {
/* 347:304 */           this.array[_i].encode(_dst);
/* 348:    */         }
/* 349:    */       }
/* 350:    */     }
/* 351:    */     
/* 352:    */     public void decode(NdrBuffer _src)
/* 353:    */       throws NdrException
/* 354:    */     {
/* 355:309 */       _src.align(4);
/* 356:310 */       this.count = _src.dec_ndr_long();
/* 357:311 */       int _arrayp = _src.dec_ndr_long();
/* 358:313 */       if (_arrayp != 0)
/* 359:    */       {
/* 360:314 */         _src = _src.deferred;
/* 361:315 */         int _arrays = _src.dec_ndr_long();
/* 362:316 */         int _arrayi = _src.index;
/* 363:317 */         _src.advance(40 * _arrays);
/* 364:319 */         if (this.array == null)
/* 365:    */         {
/* 366:320 */           if ((_arrays < 0) || (_arrays > 65535)) {
/* 367:320 */             throw new NdrException("invalid array conformance");
/* 368:    */           }
/* 369:321 */           this.array = new srvsvc.ShareInfo502[_arrays];
/* 370:    */         }
/* 371:323 */         _src = _src.derive(_arrayi);
/* 372:324 */         for (int _i = 0; _i < _arrays; _i++)
/* 373:    */         {
/* 374:325 */           if (this.array[_i] == null) {
/* 375:326 */             this.array[_i] = new srvsvc.ShareInfo502();
/* 376:    */           }
/* 377:328 */           this.array[_i].decode(_src);
/* 378:    */         }
/* 379:    */       }
/* 380:    */     }
/* 381:    */   }
/* 382:    */   
/* 383:    */   public static class ShareEnumAll
/* 384:    */     extends DcerpcMessage
/* 385:    */   {
/* 386:    */     public int retval;
/* 387:    */     public String servername;
/* 388:    */     public int level;
/* 389:    */     public NdrObject info;
/* 390:    */     public int prefmaxlen;
/* 391:    */     public int totalentries;
/* 392:    */     public int resume_handle;
/* 393:    */     
/* 394:    */     public int getOpnum()
/* 395:    */     {
/* 396:335 */       return 15;
/* 397:    */     }
/* 398:    */     
/* 399:    */     public ShareEnumAll(String servername, int level, NdrObject info, int prefmaxlen, int totalentries, int resume_handle)
/* 400:    */     {
/* 401:351 */       this.servername = servername;
/* 402:352 */       this.level = level;
/* 403:353 */       this.info = info;
/* 404:354 */       this.prefmaxlen = prefmaxlen;
/* 405:355 */       this.totalentries = totalentries;
/* 406:356 */       this.resume_handle = resume_handle;
/* 407:    */     }
/* 408:    */     
/* 409:    */     public void encode_in(NdrBuffer _dst)
/* 410:    */       throws NdrException
/* 411:    */     {
/* 412:360 */       _dst.enc_ndr_referent(this.servername, 1);
/* 413:361 */       if (this.servername != null) {
/* 414:362 */         _dst.enc_ndr_string(this.servername);
/* 415:    */       }
/* 416:365 */       _dst.enc_ndr_long(this.level);
/* 417:366 */       int _descr = this.level;
/* 418:367 */       _dst.enc_ndr_long(_descr);
/* 419:368 */       _dst.enc_ndr_referent(this.info, 1);
/* 420:369 */       if (this.info != null)
/* 421:    */       {
/* 422:370 */         _dst = _dst.deferred;
/* 423:371 */         this.info.encode(_dst);
/* 424:    */       }
/* 425:374 */       _dst.enc_ndr_long(this.prefmaxlen);
/* 426:375 */       _dst.enc_ndr_long(this.resume_handle);
/* 427:    */     }
/* 428:    */     
/* 429:    */     public void decode_out(NdrBuffer _src)
/* 430:    */       throws NdrException
/* 431:    */     {
/* 432:378 */       this.level = _src.dec_ndr_long();
/* 433:379 */       _src.dec_ndr_long();
/* 434:380 */       int _infop = _src.dec_ndr_long();
/* 435:381 */       if (_infop != 0)
/* 436:    */       {
/* 437:382 */         if (this.info == null) {
/* 438:383 */           this.info = new srvsvc.ShareInfoCtr0();
/* 439:    */         }
/* 440:385 */         _src = _src.deferred;
/* 441:386 */         this.info.decode(_src);
/* 442:    */       }
/* 443:389 */       this.totalentries = _src.dec_ndr_long();
/* 444:390 */       this.resume_handle = _src.dec_ndr_long();
/* 445:391 */       this.retval = _src.dec_ndr_long();
/* 446:    */     }
/* 447:    */   }
/* 448:    */   
/* 449:    */   public static class ShareGetInfo
/* 450:    */     extends DcerpcMessage
/* 451:    */   {
/* 452:    */     public int retval;
/* 453:    */     public String servername;
/* 454:    */     public String sharename;
/* 455:    */     public int level;
/* 456:    */     public NdrObject info;
/* 457:    */     
/* 458:    */     public int getOpnum()
/* 459:    */     {
/* 460:396 */       return 16;
/* 461:    */     }
/* 462:    */     
/* 463:    */     public ShareGetInfo(String servername, String sharename, int level, NdrObject info)
/* 464:    */     {
/* 465:408 */       this.servername = servername;
/* 466:409 */       this.sharename = sharename;
/* 467:410 */       this.level = level;
/* 468:411 */       this.info = info;
/* 469:    */     }
/* 470:    */     
/* 471:    */     public void encode_in(NdrBuffer _dst)
/* 472:    */       throws NdrException
/* 473:    */     {
/* 474:415 */       _dst.enc_ndr_referent(this.servername, 1);
/* 475:416 */       if (this.servername != null) {
/* 476:417 */         _dst.enc_ndr_string(this.servername);
/* 477:    */       }
/* 478:420 */       _dst.enc_ndr_string(this.sharename);
/* 479:421 */       _dst.enc_ndr_long(this.level);
/* 480:    */     }
/* 481:    */     
/* 482:    */     public void decode_out(NdrBuffer _src)
/* 483:    */       throws NdrException
/* 484:    */     {
/* 485:424 */       _src.dec_ndr_long();
/* 486:425 */       int _infop = _src.dec_ndr_long();
/* 487:426 */       if (_infop != 0)
/* 488:    */       {
/* 489:427 */         if (this.info == null) {
/* 490:428 */           this.info = new srvsvc.ShareInfo0();
/* 491:    */         }
/* 492:430 */         _src = _src.deferred;
/* 493:431 */         this.info.decode(_src);
/* 494:    */       }
/* 495:434 */       this.retval = _src.dec_ndr_long();
/* 496:    */     }
/* 497:    */   }
/* 498:    */   
/* 499:    */   public static class ServerInfo100
/* 500:    */     extends NdrObject
/* 501:    */   {
/* 502:    */     public int platform_id;
/* 503:    */     public String name;
/* 504:    */     
/* 505:    */     public void encode(NdrBuffer _dst)
/* 506:    */       throws NdrException
/* 507:    */     {
/* 508:443 */       _dst.align(4);
/* 509:444 */       _dst.enc_ndr_long(this.platform_id);
/* 510:445 */       _dst.enc_ndr_referent(this.name, 1);
/* 511:447 */       if (this.name != null)
/* 512:    */       {
/* 513:448 */         _dst = _dst.deferred;
/* 514:449 */         _dst.enc_ndr_string(this.name);
/* 515:    */       }
/* 516:    */     }
/* 517:    */     
/* 518:    */     public void decode(NdrBuffer _src)
/* 519:    */       throws NdrException
/* 520:    */     {
/* 521:454 */       _src.align(4);
/* 522:455 */       this.platform_id = _src.dec_ndr_long();
/* 523:456 */       int _namep = _src.dec_ndr_long();
/* 524:458 */       if (_namep != 0)
/* 525:    */       {
/* 526:459 */         _src = _src.deferred;
/* 527:460 */         this.name = _src.dec_ndr_string();
/* 528:    */       }
/* 529:    */     }
/* 530:    */   }
/* 531:    */   
/* 532:    */   public static class ServerGetInfo
/* 533:    */     extends DcerpcMessage
/* 534:    */   {
/* 535:    */     public int retval;
/* 536:    */     public String servername;
/* 537:    */     public int level;
/* 538:    */     public NdrObject info;
/* 539:    */     
/* 540:    */     public int getOpnum()
/* 541:    */     {
/* 542:467 */       return 21;
/* 543:    */     }
/* 544:    */     
/* 545:    */     public ServerGetInfo(String servername, int level, NdrObject info)
/* 546:    */     {
/* 547:475 */       this.servername = servername;
/* 548:476 */       this.level = level;
/* 549:477 */       this.info = info;
/* 550:    */     }
/* 551:    */     
/* 552:    */     public void encode_in(NdrBuffer _dst)
/* 553:    */       throws NdrException
/* 554:    */     {
/* 555:481 */       _dst.enc_ndr_referent(this.servername, 1);
/* 556:482 */       if (this.servername != null) {
/* 557:483 */         _dst.enc_ndr_string(this.servername);
/* 558:    */       }
/* 559:486 */       _dst.enc_ndr_long(this.level);
/* 560:    */     }
/* 561:    */     
/* 562:    */     public void decode_out(NdrBuffer _src)
/* 563:    */       throws NdrException
/* 564:    */     {
/* 565:489 */       _src.dec_ndr_long();
/* 566:490 */       int _infop = _src.dec_ndr_long();
/* 567:491 */       if (_infop != 0)
/* 568:    */       {
/* 569:492 */         if (this.info == null) {
/* 570:493 */           this.info = new srvsvc.ServerInfo100();
/* 571:    */         }
/* 572:495 */         _src = _src.deferred;
/* 573:496 */         this.info.decode(_src);
/* 574:    */       }
/* 575:499 */       this.retval = _src.dec_ndr_long();
/* 576:    */     }
/* 577:    */   }
/* 578:    */   
/* 579:    */   public static class TimeOfDayInfo
/* 580:    */     extends NdrObject
/* 581:    */   {
/* 582:    */     public int elapsedt;
/* 583:    */     public int msecs;
/* 584:    */     public int hours;
/* 585:    */     public int mins;
/* 586:    */     public int secs;
/* 587:    */     public int hunds;
/* 588:    */     public int timezone;
/* 589:    */     public int tinterval;
/* 590:    */     public int day;
/* 591:    */     public int month;
/* 592:    */     public int year;
/* 593:    */     public int weekday;
/* 594:    */     
/* 595:    */     public void encode(NdrBuffer _dst)
/* 596:    */       throws NdrException
/* 597:    */     {
/* 598:518 */       _dst.align(4);
/* 599:519 */       _dst.enc_ndr_long(this.elapsedt);
/* 600:520 */       _dst.enc_ndr_long(this.msecs);
/* 601:521 */       _dst.enc_ndr_long(this.hours);
/* 602:522 */       _dst.enc_ndr_long(this.mins);
/* 603:523 */       _dst.enc_ndr_long(this.secs);
/* 604:524 */       _dst.enc_ndr_long(this.hunds);
/* 605:525 */       _dst.enc_ndr_long(this.timezone);
/* 606:526 */       _dst.enc_ndr_long(this.tinterval);
/* 607:527 */       _dst.enc_ndr_long(this.day);
/* 608:528 */       _dst.enc_ndr_long(this.month);
/* 609:529 */       _dst.enc_ndr_long(this.year);
/* 610:530 */       _dst.enc_ndr_long(this.weekday);
/* 611:    */     }
/* 612:    */     
/* 613:    */     public void decode(NdrBuffer _src)
/* 614:    */       throws NdrException
/* 615:    */     {
/* 616:534 */       _src.align(4);
/* 617:535 */       this.elapsedt = _src.dec_ndr_long();
/* 618:536 */       this.msecs = _src.dec_ndr_long();
/* 619:537 */       this.hours = _src.dec_ndr_long();
/* 620:538 */       this.mins = _src.dec_ndr_long();
/* 621:539 */       this.secs = _src.dec_ndr_long();
/* 622:540 */       this.hunds = _src.dec_ndr_long();
/* 623:541 */       this.timezone = _src.dec_ndr_long();
/* 624:542 */       this.tinterval = _src.dec_ndr_long();
/* 625:543 */       this.day = _src.dec_ndr_long();
/* 626:544 */       this.month = _src.dec_ndr_long();
/* 627:545 */       this.year = _src.dec_ndr_long();
/* 628:546 */       this.weekday = _src.dec_ndr_long();
/* 629:    */     }
/* 630:    */   }
/* 631:    */   
/* 632:    */   public static class RemoteTOD
/* 633:    */     extends DcerpcMessage
/* 634:    */   {
/* 635:    */     public int retval;
/* 636:    */     public String servername;
/* 637:    */     public srvsvc.TimeOfDayInfo info;
/* 638:    */     
/* 639:    */     public int getOpnum()
/* 640:    */     {
/* 641:552 */       return 28;
/* 642:    */     }
/* 643:    */     
/* 644:    */     public RemoteTOD(String servername, srvsvc.TimeOfDayInfo info)
/* 645:    */     {
/* 646:559 */       this.servername = servername;
/* 647:560 */       this.info = info;
/* 648:    */     }
/* 649:    */     
/* 650:    */     public void encode_in(NdrBuffer _dst)
/* 651:    */       throws NdrException
/* 652:    */     {
/* 653:564 */       _dst.enc_ndr_referent(this.servername, 1);
/* 654:565 */       if (this.servername != null) {
/* 655:566 */         _dst.enc_ndr_string(this.servername);
/* 656:    */       }
/* 657:    */     }
/* 658:    */     
/* 659:    */     public void decode_out(NdrBuffer _src)
/* 660:    */       throws NdrException
/* 661:    */     {
/* 662:571 */       int _infop = _src.dec_ndr_long();
/* 663:572 */       if (_infop != 0)
/* 664:    */       {
/* 665:573 */         if (this.info == null) {
/* 666:574 */           this.info = new srvsvc.TimeOfDayInfo();
/* 667:    */         }
/* 668:576 */         this.info.decode(_src);
/* 669:    */       }
/* 670:579 */       this.retval = _src.dec_ndr_long();
/* 671:    */     }
/* 672:    */   }
/* 673:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.srvsvc
 * JD-Core Version:    0.7.0.1
 */