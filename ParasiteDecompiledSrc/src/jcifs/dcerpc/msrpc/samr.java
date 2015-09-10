/*   1:    */ package jcifs.dcerpc.msrpc;
/*   2:    */ 
/*   3:    */ import jcifs.dcerpc.DcerpcMessage;
/*   4:    */ import jcifs.dcerpc.ndr.NdrBuffer;
/*   5:    */ import jcifs.dcerpc.ndr.NdrException;
/*   6:    */ import jcifs.dcerpc.ndr.NdrObject;
/*   7:    */ import jcifs.dcerpc.rpc.policy_handle;
/*   8:    */ import jcifs.dcerpc.rpc.sid_t;
/*   9:    */ import jcifs.dcerpc.rpc.unicode_string;
/*  10:    */ 
/*  11:    */ public class samr
/*  12:    */ {
/*  13:    */   public static final int ACB_DISABLED = 1;
/*  14:    */   public static final int ACB_HOMDIRREQ = 2;
/*  15:    */   public static final int ACB_PWNOTREQ = 4;
/*  16:    */   public static final int ACB_TEMPDUP = 8;
/*  17:    */   public static final int ACB_NORMAL = 16;
/*  18:    */   public static final int ACB_MNS = 32;
/*  19:    */   public static final int ACB_DOMTRUST = 64;
/*  20:    */   public static final int ACB_WSTRUST = 128;
/*  21:    */   public static final int ACB_SVRTRUST = 256;
/*  22:    */   public static final int ACB_PWNOEXP = 512;
/*  23:    */   public static final int ACB_AUTOLOCK = 1024;
/*  24:    */   public static final int ACB_ENC_TXT_PWD_ALLOWED = 2048;
/*  25:    */   public static final int ACB_SMARTCARD_REQUIRED = 4096;
/*  26:    */   public static final int ACB_TRUSTED_FOR_DELEGATION = 8192;
/*  27:    */   public static final int ACB_NOT_DELEGATED = 16384;
/*  28:    */   public static final int ACB_USE_DES_KEY_ONLY = 32768;
/*  29:    */   public static final int ACB_DONT_REQUIRE_PREAUTH = 65536;
/*  30:    */   public static final int SE_GROUP_MANDATORY = 1;
/*  31:    */   public static final int SE_GROUP_ENABLED_BY_DEFAULT = 2;
/*  32:    */   public static final int SE_GROUP_ENABLED = 4;
/*  33:    */   public static final int SE_GROUP_OWNER = 8;
/*  34:    */   public static final int SE_GROUP_USE_FOR_DENY_ONLY = 16;
/*  35:    */   public static final int SE_GROUP_RESOURCE = 536870912;
/*  36:    */   public static final int SE_GROUP_LOGON_ID = -1073741824;
/*  37:    */   
/*  38:    */   public static String getSyntax()
/*  39:    */   {
/*  40:  9 */     return "12345778-1234-abcd-ef00-0123456789ac:1.0";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static class SamrCloseHandle
/*  44:    */     extends DcerpcMessage
/*  45:    */   {
/*  46:    */     public int retval;
/*  47:    */     public rpc.policy_handle handle;
/*  48:    */     
/*  49:    */     public int getOpnum()
/*  50:    */     {
/*  51: 32 */       return 1;
/*  52:    */     }
/*  53:    */     
/*  54:    */     public SamrCloseHandle(rpc.policy_handle handle)
/*  55:    */     {
/*  56: 38 */       this.handle = handle;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public void encode_in(NdrBuffer _dst)
/*  60:    */       throws NdrException
/*  61:    */     {
/*  62: 42 */       this.handle.encode(_dst);
/*  63:    */     }
/*  64:    */     
/*  65:    */     public void decode_out(NdrBuffer _src)
/*  66:    */       throws NdrException
/*  67:    */     {
/*  68: 45 */       this.retval = _src.dec_ndr_long();
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static class SamrConnect2
/*  73:    */     extends DcerpcMessage
/*  74:    */   {
/*  75:    */     public int retval;
/*  76:    */     public String system_name;
/*  77:    */     public int access_mask;
/*  78:    */     public rpc.policy_handle handle;
/*  79:    */     
/*  80:    */     public int getOpnum()
/*  81:    */     {
/*  82: 50 */       return 57;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public SamrConnect2(String system_name, int access_mask, rpc.policy_handle handle)
/*  86:    */     {
/*  87: 58 */       this.system_name = system_name;
/*  88: 59 */       this.access_mask = access_mask;
/*  89: 60 */       this.handle = handle;
/*  90:    */     }
/*  91:    */     
/*  92:    */     public void encode_in(NdrBuffer _dst)
/*  93:    */       throws NdrException
/*  94:    */     {
/*  95: 64 */       _dst.enc_ndr_referent(this.system_name, 1);
/*  96: 65 */       if (this.system_name != null) {
/*  97: 66 */         _dst.enc_ndr_string(this.system_name);
/*  98:    */       }
/*  99: 69 */       _dst.enc_ndr_long(this.access_mask);
/* 100:    */     }
/* 101:    */     
/* 102:    */     public void decode_out(NdrBuffer _src)
/* 103:    */       throws NdrException
/* 104:    */     {
/* 105: 72 */       this.handle.decode(_src);
/* 106: 73 */       this.retval = _src.dec_ndr_long();
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static class SamrConnect4
/* 111:    */     extends DcerpcMessage
/* 112:    */   {
/* 113:    */     public int retval;
/* 114:    */     public String system_name;
/* 115:    */     public int unknown;
/* 116:    */     public int access_mask;
/* 117:    */     public rpc.policy_handle handle;
/* 118:    */     
/* 119:    */     public int getOpnum()
/* 120:    */     {
/* 121: 78 */       return 62;
/* 122:    */     }
/* 123:    */     
/* 124:    */     public SamrConnect4(String system_name, int unknown, int access_mask, rpc.policy_handle handle)
/* 125:    */     {
/* 126: 90 */       this.system_name = system_name;
/* 127: 91 */       this.unknown = unknown;
/* 128: 92 */       this.access_mask = access_mask;
/* 129: 93 */       this.handle = handle;
/* 130:    */     }
/* 131:    */     
/* 132:    */     public void encode_in(NdrBuffer _dst)
/* 133:    */       throws NdrException
/* 134:    */     {
/* 135: 97 */       _dst.enc_ndr_referent(this.system_name, 1);
/* 136: 98 */       if (this.system_name != null) {
/* 137: 99 */         _dst.enc_ndr_string(this.system_name);
/* 138:    */       }
/* 139:102 */       _dst.enc_ndr_long(this.unknown);
/* 140:103 */       _dst.enc_ndr_long(this.access_mask);
/* 141:    */     }
/* 142:    */     
/* 143:    */     public void decode_out(NdrBuffer _src)
/* 144:    */       throws NdrException
/* 145:    */     {
/* 146:106 */       this.handle.decode(_src);
/* 147:107 */       this.retval = _src.dec_ndr_long();
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static class SamrOpenDomain
/* 152:    */     extends DcerpcMessage
/* 153:    */   {
/* 154:    */     public int retval;
/* 155:    */     public rpc.policy_handle handle;
/* 156:    */     public int access_mask;
/* 157:    */     public rpc.sid_t sid;
/* 158:    */     public rpc.policy_handle domain_handle;
/* 159:    */     
/* 160:    */     public int getOpnum()
/* 161:    */     {
/* 162:112 */       return 7;
/* 163:    */     }
/* 164:    */     
/* 165:    */     public SamrOpenDomain(rpc.policy_handle handle, int access_mask, rpc.sid_t sid, rpc.policy_handle domain_handle)
/* 166:    */     {
/* 167:124 */       this.handle = handle;
/* 168:125 */       this.access_mask = access_mask;
/* 169:126 */       this.sid = sid;
/* 170:127 */       this.domain_handle = domain_handle;
/* 171:    */     }
/* 172:    */     
/* 173:    */     public void encode_in(NdrBuffer _dst)
/* 174:    */       throws NdrException
/* 175:    */     {
/* 176:131 */       this.handle.encode(_dst);
/* 177:132 */       _dst.enc_ndr_long(this.access_mask);
/* 178:133 */       this.sid.encode(_dst);
/* 179:    */     }
/* 180:    */     
/* 181:    */     public void decode_out(NdrBuffer _src)
/* 182:    */       throws NdrException
/* 183:    */     {
/* 184:136 */       this.domain_handle.decode(_src);
/* 185:137 */       this.retval = _src.dec_ndr_long();
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static class SamrSamEntry
/* 190:    */     extends NdrObject
/* 191:    */   {
/* 192:    */     public int idx;
/* 193:    */     public rpc.unicode_string name;
/* 194:    */     
/* 195:    */     public void encode(NdrBuffer _dst)
/* 196:    */       throws NdrException
/* 197:    */     {
/* 198:146 */       _dst.align(4);
/* 199:147 */       _dst.enc_ndr_long(this.idx);
/* 200:148 */       _dst.enc_ndr_short(this.name.length);
/* 201:149 */       _dst.enc_ndr_short(this.name.maximum_length);
/* 202:150 */       _dst.enc_ndr_referent(this.name.buffer, 1);
/* 203:152 */       if (this.name.buffer != null)
/* 204:    */       {
/* 205:153 */         _dst = _dst.deferred;
/* 206:154 */         int _name_bufferl = this.name.length / 2;
/* 207:155 */         int _name_buffers = this.name.maximum_length / 2;
/* 208:156 */         _dst.enc_ndr_long(_name_buffers);
/* 209:157 */         _dst.enc_ndr_long(0);
/* 210:158 */         _dst.enc_ndr_long(_name_bufferl);
/* 211:159 */         int _name_bufferi = _dst.index;
/* 212:160 */         _dst.advance(2 * _name_bufferl);
/* 213:    */         
/* 214:162 */         _dst = _dst.derive(_name_bufferi);
/* 215:163 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 216:164 */           _dst.enc_ndr_short(this.name.buffer[_i]);
/* 217:    */         }
/* 218:    */       }
/* 219:    */     }
/* 220:    */     
/* 221:    */     public void decode(NdrBuffer _src)
/* 222:    */       throws NdrException
/* 223:    */     {
/* 224:169 */       _src.align(4);
/* 225:170 */       this.idx = _src.dec_ndr_long();
/* 226:171 */       _src.align(4);
/* 227:172 */       if (this.name == null) {
/* 228:173 */         this.name = new rpc.unicode_string();
/* 229:    */       }
/* 230:175 */       this.name.length = ((short)_src.dec_ndr_short());
/* 231:176 */       this.name.maximum_length = ((short)_src.dec_ndr_short());
/* 232:177 */       int _name_bufferp = _src.dec_ndr_long();
/* 233:179 */       if (_name_bufferp != 0)
/* 234:    */       {
/* 235:180 */         _src = _src.deferred;
/* 236:181 */         int _name_buffers = _src.dec_ndr_long();
/* 237:182 */         _src.dec_ndr_long();
/* 238:183 */         int _name_bufferl = _src.dec_ndr_long();
/* 239:184 */         int _name_bufferi = _src.index;
/* 240:185 */         _src.advance(2 * _name_bufferl);
/* 241:187 */         if (this.name.buffer == null)
/* 242:    */         {
/* 243:188 */           if ((_name_buffers < 0) || (_name_buffers > 65535)) {
/* 244:188 */             throw new NdrException("invalid array conformance");
/* 245:    */           }
/* 246:189 */           this.name.buffer = new short[_name_buffers];
/* 247:    */         }
/* 248:191 */         _src = _src.derive(_name_bufferi);
/* 249:192 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 250:193 */           this.name.buffer[_i] = ((short)_src.dec_ndr_short());
/* 251:    */         }
/* 252:    */       }
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public static class SamrSamArray
/* 257:    */     extends NdrObject
/* 258:    */   {
/* 259:    */     public int count;
/* 260:    */     public samr.SamrSamEntry[] entries;
/* 261:    */     
/* 262:    */     public void encode(NdrBuffer _dst)
/* 263:    */       throws NdrException
/* 264:    */     {
/* 265:204 */       _dst.align(4);
/* 266:205 */       _dst.enc_ndr_long(this.count);
/* 267:206 */       _dst.enc_ndr_referent(this.entries, 1);
/* 268:208 */       if (this.entries != null)
/* 269:    */       {
/* 270:209 */         _dst = _dst.deferred;
/* 271:210 */         int _entriess = this.count;
/* 272:211 */         _dst.enc_ndr_long(_entriess);
/* 273:212 */         int _entriesi = _dst.index;
/* 274:213 */         _dst.advance(12 * _entriess);
/* 275:    */         
/* 276:215 */         _dst = _dst.derive(_entriesi);
/* 277:216 */         for (int _i = 0; _i < _entriess; _i++) {
/* 278:217 */           this.entries[_i].encode(_dst);
/* 279:    */         }
/* 280:    */       }
/* 281:    */     }
/* 282:    */     
/* 283:    */     public void decode(NdrBuffer _src)
/* 284:    */       throws NdrException
/* 285:    */     {
/* 286:222 */       _src.align(4);
/* 287:223 */       this.count = _src.dec_ndr_long();
/* 288:224 */       int _entriesp = _src.dec_ndr_long();
/* 289:226 */       if (_entriesp != 0)
/* 290:    */       {
/* 291:227 */         _src = _src.deferred;
/* 292:228 */         int _entriess = _src.dec_ndr_long();
/* 293:229 */         int _entriesi = _src.index;
/* 294:230 */         _src.advance(12 * _entriess);
/* 295:232 */         if (this.entries == null)
/* 296:    */         {
/* 297:233 */           if ((_entriess < 0) || (_entriess > 65535)) {
/* 298:233 */             throw new NdrException("invalid array conformance");
/* 299:    */           }
/* 300:234 */           this.entries = new samr.SamrSamEntry[_entriess];
/* 301:    */         }
/* 302:236 */         _src = _src.derive(_entriesi);
/* 303:237 */         for (int _i = 0; _i < _entriess; _i++)
/* 304:    */         {
/* 305:238 */           if (this.entries[_i] == null) {
/* 306:239 */             this.entries[_i] = new samr.SamrSamEntry();
/* 307:    */           }
/* 308:241 */           this.entries[_i].decode(_src);
/* 309:    */         }
/* 310:    */       }
/* 311:    */     }
/* 312:    */   }
/* 313:    */   
/* 314:    */   public static class SamrEnumerateAliasesInDomain
/* 315:    */     extends DcerpcMessage
/* 316:    */   {
/* 317:    */     public int retval;
/* 318:    */     public rpc.policy_handle domain_handle;
/* 319:    */     public int resume_handle;
/* 320:    */     public int acct_flags;
/* 321:    */     public samr.SamrSamArray sam;
/* 322:    */     public int num_entries;
/* 323:    */     
/* 324:    */     public int getOpnum()
/* 325:    */     {
/* 326:248 */       return 15;
/* 327:    */     }
/* 328:    */     
/* 329:    */     public SamrEnumerateAliasesInDomain(rpc.policy_handle domain_handle, int resume_handle, int acct_flags, samr.SamrSamArray sam, int num_entries)
/* 330:    */     {
/* 331:262 */       this.domain_handle = domain_handle;
/* 332:263 */       this.resume_handle = resume_handle;
/* 333:264 */       this.acct_flags = acct_flags;
/* 334:265 */       this.sam = sam;
/* 335:266 */       this.num_entries = num_entries;
/* 336:    */     }
/* 337:    */     
/* 338:    */     public void encode_in(NdrBuffer _dst)
/* 339:    */       throws NdrException
/* 340:    */     {
/* 341:270 */       this.domain_handle.encode(_dst);
/* 342:271 */       _dst.enc_ndr_long(this.resume_handle);
/* 343:272 */       _dst.enc_ndr_long(this.acct_flags);
/* 344:    */     }
/* 345:    */     
/* 346:    */     public void decode_out(NdrBuffer _src)
/* 347:    */       throws NdrException
/* 348:    */     {
/* 349:275 */       this.resume_handle = _src.dec_ndr_long();
/* 350:276 */       int _samp = _src.dec_ndr_long();
/* 351:277 */       if (_samp != 0)
/* 352:    */       {
/* 353:278 */         if (this.sam == null) {
/* 354:279 */           this.sam = new samr.SamrSamArray();
/* 355:    */         }
/* 356:281 */         this.sam.decode(_src);
/* 357:    */       }
/* 358:284 */       this.num_entries = _src.dec_ndr_long();
/* 359:285 */       this.retval = _src.dec_ndr_long();
/* 360:    */     }
/* 361:    */   }
/* 362:    */   
/* 363:    */   public static class SamrOpenAlias
/* 364:    */     extends DcerpcMessage
/* 365:    */   {
/* 366:    */     public int retval;
/* 367:    */     public rpc.policy_handle domain_handle;
/* 368:    */     public int access_mask;
/* 369:    */     public int rid;
/* 370:    */     public rpc.policy_handle alias_handle;
/* 371:    */     
/* 372:    */     public int getOpnum()
/* 373:    */     {
/* 374:290 */       return 27;
/* 375:    */     }
/* 376:    */     
/* 377:    */     public SamrOpenAlias(rpc.policy_handle domain_handle, int access_mask, int rid, rpc.policy_handle alias_handle)
/* 378:    */     {
/* 379:302 */       this.domain_handle = domain_handle;
/* 380:303 */       this.access_mask = access_mask;
/* 381:304 */       this.rid = rid;
/* 382:305 */       this.alias_handle = alias_handle;
/* 383:    */     }
/* 384:    */     
/* 385:    */     public void encode_in(NdrBuffer _dst)
/* 386:    */       throws NdrException
/* 387:    */     {
/* 388:309 */       this.domain_handle.encode(_dst);
/* 389:310 */       _dst.enc_ndr_long(this.access_mask);
/* 390:311 */       _dst.enc_ndr_long(this.rid);
/* 391:    */     }
/* 392:    */     
/* 393:    */     public void decode_out(NdrBuffer _src)
/* 394:    */       throws NdrException
/* 395:    */     {
/* 396:314 */       this.alias_handle.decode(_src);
/* 397:315 */       this.retval = _src.dec_ndr_long();
/* 398:    */     }
/* 399:    */   }
/* 400:    */   
/* 401:    */   public static class SamrGetMembersInAlias
/* 402:    */     extends DcerpcMessage
/* 403:    */   {
/* 404:    */     public int retval;
/* 405:    */     public rpc.policy_handle alias_handle;
/* 406:    */     public lsarpc.LsarSidArray sids;
/* 407:    */     
/* 408:    */     public int getOpnum()
/* 409:    */     {
/* 410:320 */       return 33;
/* 411:    */     }
/* 412:    */     
/* 413:    */     public SamrGetMembersInAlias(rpc.policy_handle alias_handle, lsarpc.LsarSidArray sids)
/* 414:    */     {
/* 415:327 */       this.alias_handle = alias_handle;
/* 416:328 */       this.sids = sids;
/* 417:    */     }
/* 418:    */     
/* 419:    */     public void encode_in(NdrBuffer _dst)
/* 420:    */       throws NdrException
/* 421:    */     {
/* 422:332 */       this.alias_handle.encode(_dst);
/* 423:    */     }
/* 424:    */     
/* 425:    */     public void decode_out(NdrBuffer _src)
/* 426:    */       throws NdrException
/* 427:    */     {
/* 428:335 */       this.sids.decode(_src);
/* 429:336 */       this.retval = _src.dec_ndr_long();
/* 430:    */     }
/* 431:    */   }
/* 432:    */   
/* 433:    */   public static class SamrRidWithAttribute
/* 434:    */     extends NdrObject
/* 435:    */   {
/* 436:    */     public int rid;
/* 437:    */     public int attributes;
/* 438:    */     
/* 439:    */     public void encode(NdrBuffer _dst)
/* 440:    */       throws NdrException
/* 441:    */     {
/* 442:353 */       _dst.align(4);
/* 443:354 */       _dst.enc_ndr_long(this.rid);
/* 444:355 */       _dst.enc_ndr_long(this.attributes);
/* 445:    */     }
/* 446:    */     
/* 447:    */     public void decode(NdrBuffer _src)
/* 448:    */       throws NdrException
/* 449:    */     {
/* 450:359 */       _src.align(4);
/* 451:360 */       this.rid = _src.dec_ndr_long();
/* 452:361 */       this.attributes = _src.dec_ndr_long();
/* 453:    */     }
/* 454:    */   }
/* 455:    */   
/* 456:    */   public static class SamrRidWithAttributeArray
/* 457:    */     extends NdrObject
/* 458:    */   {
/* 459:    */     public int count;
/* 460:    */     public samr.SamrRidWithAttribute[] rids;
/* 461:    */     
/* 462:    */     public void encode(NdrBuffer _dst)
/* 463:    */       throws NdrException
/* 464:    */     {
/* 465:371 */       _dst.align(4);
/* 466:372 */       _dst.enc_ndr_long(this.count);
/* 467:373 */       _dst.enc_ndr_referent(this.rids, 1);
/* 468:375 */       if (this.rids != null)
/* 469:    */       {
/* 470:376 */         _dst = _dst.deferred;
/* 471:377 */         int _ridss = this.count;
/* 472:378 */         _dst.enc_ndr_long(_ridss);
/* 473:379 */         int _ridsi = _dst.index;
/* 474:380 */         _dst.advance(8 * _ridss);
/* 475:    */         
/* 476:382 */         _dst = _dst.derive(_ridsi);
/* 477:383 */         for (int _i = 0; _i < _ridss; _i++) {
/* 478:384 */           this.rids[_i].encode(_dst);
/* 479:    */         }
/* 480:    */       }
/* 481:    */     }
/* 482:    */     
/* 483:    */     public void decode(NdrBuffer _src)
/* 484:    */       throws NdrException
/* 485:    */     {
/* 486:389 */       _src.align(4);
/* 487:390 */       this.count = _src.dec_ndr_long();
/* 488:391 */       int _ridsp = _src.dec_ndr_long();
/* 489:393 */       if (_ridsp != 0)
/* 490:    */       {
/* 491:394 */         _src = _src.deferred;
/* 492:395 */         int _ridss = _src.dec_ndr_long();
/* 493:396 */         int _ridsi = _src.index;
/* 494:397 */         _src.advance(8 * _ridss);
/* 495:399 */         if (this.rids == null)
/* 496:    */         {
/* 497:400 */           if ((_ridss < 0) || (_ridss > 65535)) {
/* 498:400 */             throw new NdrException("invalid array conformance");
/* 499:    */           }
/* 500:401 */           this.rids = new samr.SamrRidWithAttribute[_ridss];
/* 501:    */         }
/* 502:403 */         _src = _src.derive(_ridsi);
/* 503:404 */         for (int _i = 0; _i < _ridss; _i++)
/* 504:    */         {
/* 505:405 */           if (this.rids[_i] == null) {
/* 506:406 */             this.rids[_i] = new samr.SamrRidWithAttribute();
/* 507:    */           }
/* 508:408 */           this.rids[_i].decode(_src);
/* 509:    */         }
/* 510:    */       }
/* 511:    */     }
/* 512:    */   }
/* 513:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.samr
 * JD-Core Version:    0.7.0.1
 */