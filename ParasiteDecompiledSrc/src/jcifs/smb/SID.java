/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.StringTokenizer;
/*   8:    */ import jcifs.dcerpc.DcerpcHandle;
/*   9:    */ import jcifs.dcerpc.UnicodeString;
/*  10:    */ import jcifs.dcerpc.msrpc.LsaPolicyHandle;
/*  11:    */ import jcifs.dcerpc.msrpc.MsrpcEnumerateAliasesInDomain;
/*  12:    */ import jcifs.dcerpc.msrpc.MsrpcGetMembersInAlias;
/*  13:    */ import jcifs.dcerpc.msrpc.MsrpcLookupSids;
/*  14:    */ import jcifs.dcerpc.msrpc.MsrpcQueryInformationPolicy;
/*  15:    */ import jcifs.dcerpc.msrpc.SamrAliasHandle;
/*  16:    */ import jcifs.dcerpc.msrpc.SamrDomainHandle;
/*  17:    */ import jcifs.dcerpc.msrpc.SamrPolicyHandle;
/*  18:    */ import jcifs.dcerpc.msrpc.lsarpc.LsarDomainInfo;
/*  19:    */ import jcifs.dcerpc.msrpc.lsarpc.LsarSidArray;
/*  20:    */ import jcifs.dcerpc.msrpc.lsarpc.LsarSidPtr;
/*  21:    */ import jcifs.dcerpc.msrpc.lsarpc.LsarTranslatedName;
/*  22:    */ import jcifs.dcerpc.msrpc.lsarpc.LsarTrustInformation;
/*  23:    */ import jcifs.dcerpc.msrpc.samr.SamrSamArray;
/*  24:    */ import jcifs.dcerpc.msrpc.samr.SamrSamEntry;
/*  25:    */ import jcifs.dcerpc.rpc.sid_t;
/*  26:    */ import jcifs.dcerpc.rpc.unicode_string;
/*  27:    */ import jcifs.util.Encdec;
/*  28:    */ import jcifs.util.Hexdump;
/*  29:    */ 
/*  30:    */ public class SID
/*  31:    */   extends rpc.sid_t
/*  32:    */ {
/*  33:    */   public static final int SID_TYPE_USE_NONE = 0;
/*  34:    */   public static final int SID_TYPE_USER = 1;
/*  35:    */   public static final int SID_TYPE_DOM_GRP = 2;
/*  36:    */   public static final int SID_TYPE_DOMAIN = 3;
/*  37:    */   public static final int SID_TYPE_ALIAS = 4;
/*  38:    */   public static final int SID_TYPE_WKN_GRP = 5;
/*  39:    */   public static final int SID_TYPE_DELETED = 6;
/*  40:    */   public static final int SID_TYPE_INVALID = 7;
/*  41:    */   public static final int SID_TYPE_UNKNOWN = 8;
/*  42: 59 */   static final String[] SID_TYPE_NAMES = { "0", "User", "Domain group", "Domain", "Local group", "Builtin group", "Deleted", "Invalid", "Unknown" };
/*  43:    */   public static final int SID_FLAG_RESOLVE_SIDS = 1;
/*  44: 73 */   public static SID EVERYONE = null;
/*  45: 74 */   public static SID CREATOR_OWNER = null;
/*  46: 75 */   public static SID SYSTEM = null;
/*  47:    */   
/*  48:    */   static
/*  49:    */   {
/*  50:    */     try
/*  51:    */     {
/*  52: 79 */       EVERYONE = new SID("S-1-1-0");
/*  53: 80 */       CREATOR_OWNER = new SID("S-1-3-0");
/*  54: 81 */       SYSTEM = new SID("S-1-5-18");
/*  55:    */     }
/*  56:    */     catch (SmbException se) {}
/*  57:    */   }
/*  58:    */   
/*  59: 86 */   static Map sid_cache = new HashMap();
/*  60:    */   int type;
/*  61:    */   
/*  62:    */   static void resolveSids(DcerpcHandle handle, LsaPolicyHandle policyHandle, SID[] sids)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 91 */     MsrpcLookupSids rpc = new MsrpcLookupSids(policyHandle, sids);
/*  66: 92 */     handle.sendrecv(rpc);
/*  67: 93 */     switch (rpc.retval)
/*  68:    */     {
/*  69:    */     case -1073741709: 
/*  70:    */     case 0: 
/*  71:    */     case 263: 
/*  72:    */       break;
/*  73:    */     default: 
/*  74: 99 */       throw new SmbException(rpc.retval, false);
/*  75:    */     }
/*  76:102 */     for (int si = 0; si < sids.length; si++)
/*  77:    */     {
/*  78:103 */       sids[si].type = rpc.names.names[si].sid_type;
/*  79:104 */       sids[si].domainName = null;
/*  80:106 */       switch (sids[si].type)
/*  81:    */       {
/*  82:    */       case 1: 
/*  83:    */       case 2: 
/*  84:    */       case 3: 
/*  85:    */       case 4: 
/*  86:    */       case 5: 
/*  87:112 */         int sid_index = rpc.names.names[si].sid_index;
/*  88:113 */         rpc.unicode_string ustr = rpc.domains.domains[sid_index].name;
/*  89:114 */         sids[si].domainName = new UnicodeString(ustr, false).toString();
/*  90:    */       }
/*  91:118 */       sids[si].acctName = new UnicodeString(rpc.names.names[si].name, false).toString();
/*  92:119 */       sids[si].origin_server = null;
/*  93:120 */       sids[si].origin_auth = null;
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   static void resolveSids0(String authorityServerName, NtlmPasswordAuthentication auth, SID[] sids)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:126 */     DcerpcHandle handle = null;
/* 101:127 */     LsaPolicyHandle policyHandle = null;
/* 102:129 */     synchronized (sid_cache)
/* 103:    */     {
/* 104:    */       try
/* 105:    */       {
/* 106:131 */         handle = DcerpcHandle.getHandle("ncacn_np:" + authorityServerName + "[\\PIPE\\lsarpc]", auth);
/* 107:    */         
/* 108:133 */         String server = authorityServerName;
/* 109:134 */         int dot = server.indexOf('.');
/* 110:135 */         if ((dot > 0) && (!Character.isDigit(server.charAt(0)))) {
/* 111:136 */           server = server.substring(0, dot);
/* 112:    */         }
/* 113:137 */         policyHandle = new LsaPolicyHandle(handle, "\\\\" + server, 2048);
/* 114:138 */         resolveSids(handle, policyHandle, sids);
/* 115:    */       }
/* 116:    */       finally
/* 117:    */       {
/* 118:140 */         if (handle != null)
/* 119:    */         {
/* 120:141 */           if (policyHandle != null) {
/* 121:142 */             policyHandle.close();
/* 122:    */           }
/* 123:144 */           handle.close();
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static void resolveSids(String authorityServerName, NtlmPasswordAuthentication auth, SID[] sids, int offset, int length)
/* 130:    */     throws IOException
/* 131:    */   {
/* 132:155 */     ArrayList list = new ArrayList(sids.length);
/* 133:158 */     synchronized (sid_cache)
/* 134:    */     {
/* 135:159 */       for (int si = 0; si < length; si++)
/* 136:    */       {
/* 137:160 */         SID sid = (SID)sid_cache.get(sids[(offset + si)]);
/* 138:161 */         if (sid != null)
/* 139:    */         {
/* 140:162 */           sids[(offset + si)].type = sid.type;
/* 141:163 */           sids[(offset + si)].domainName = sid.domainName;
/* 142:164 */           sids[(offset + si)].acctName = sid.acctName;
/* 143:    */         }
/* 144:    */         else
/* 145:    */         {
/* 146:166 */           list.add(sids[(offset + si)]);
/* 147:    */         }
/* 148:    */       }
/* 149:170 */       if (list.size() > 0)
/* 150:    */       {
/* 151:171 */         sids = (SID[])list.toArray(new SID[0]);
/* 152:172 */         resolveSids0(authorityServerName, auth, sids);
/* 153:173 */         for (si = 0; si < sids.length; si++) {
/* 154:174 */           sid_cache.put(sids[si], sids[si]);
/* 155:    */         }
/* 156:    */       }
/* 157:    */     }
/* 158:    */     int si;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static void resolveSids(String authorityServerName, NtlmPasswordAuthentication auth, SID[] sids)
/* 162:    */     throws IOException
/* 163:    */   {
/* 164:194 */     ArrayList list = new ArrayList(sids.length);
/* 165:197 */     synchronized (sid_cache)
/* 166:    */     {
/* 167:198 */       for (int si = 0; si < sids.length; si++)
/* 168:    */       {
/* 169:199 */         SID sid = (SID)sid_cache.get(sids[si]);
/* 170:200 */         if (sid != null)
/* 171:    */         {
/* 172:201 */           sids[si].type = sid.type;
/* 173:202 */           sids[si].domainName = sid.domainName;
/* 174:203 */           sids[si].acctName = sid.acctName;
/* 175:    */         }
/* 176:    */         else
/* 177:    */         {
/* 178:205 */           list.add(sids[si]);
/* 179:    */         }
/* 180:    */       }
/* 181:209 */       if (list.size() > 0)
/* 182:    */       {
/* 183:210 */         sids = (SID[])list.toArray(new SID[0]);
/* 184:211 */         resolveSids0(authorityServerName, auth, sids);
/* 185:212 */         for (si = 0; si < sids.length; si++) {
/* 186:213 */           sid_cache.put(sids[si], sids[si]);
/* 187:    */         }
/* 188:    */       }
/* 189:    */     }
/* 190:    */     int si;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static SID getServerSid(String server, NtlmPasswordAuthentication auth)
/* 194:    */     throws IOException
/* 195:    */   {
/* 196:220 */     DcerpcHandle handle = null;
/* 197:221 */     LsaPolicyHandle policyHandle = null;
/* 198:222 */     lsarpc.LsarDomainInfo info = new lsarpc.LsarDomainInfo();
/* 199:225 */     synchronized (sid_cache)
/* 200:    */     {
/* 201:    */       try
/* 202:    */       {
/* 203:227 */         handle = DcerpcHandle.getHandle("ncacn_np:" + server + "[\\PIPE\\lsarpc]", auth);
/* 204:    */         
/* 205:    */ 
/* 206:230 */         policyHandle = new LsaPolicyHandle(handle, null, 1);
/* 207:231 */         MsrpcQueryInformationPolicy rpc = new MsrpcQueryInformationPolicy(policyHandle, (short)5, info);
/* 208:    */         
/* 209:    */ 
/* 210:234 */         handle.sendrecv(rpc);
/* 211:235 */         if (rpc.retval != 0) {
/* 212:236 */           throw new SmbException(rpc.retval, false);
/* 213:    */         }
/* 214:238 */         SID localSID = new SID(info.sid, 3, new UnicodeString(info.name, false).toString(), null, false);jsr 17;return localSID;
/* 215:    */       }
/* 216:    */       finally
/* 217:    */       {
/* 218:244 */         jsr 6;
/* 219:    */       }
/* 220:244 */       localObject2 = returnAddress;
/* 221:244 */       if (handle != null)
/* 222:    */       {
/* 223:245 */         if (policyHandle != null) {
/* 224:246 */           policyHandle.close();
/* 225:    */         }
/* 226:248 */         handle.close();
/* 227:    */       }
/* 228:248 */       ret;
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static byte[] toByteArray(rpc.sid_t sid)
/* 233:    */   {
/* 234:254 */     byte[] dst = new byte[8 + sid.sub_authority_count * 4];
/* 235:255 */     int di = 0;
/* 236:256 */     dst[(di++)] = sid.revision;
/* 237:257 */     dst[(di++)] = sid.sub_authority_count;
/* 238:258 */     System.arraycopy(sid.identifier_authority, 0, dst, di, 6);
/* 239:259 */     di += 6;
/* 240:260 */     for (int ii = 0; ii < sid.sub_authority_count; ii++)
/* 241:    */     {
/* 242:261 */       Encdec.enc_uint32le(sid.sub_authority[ii], dst, di);
/* 243:262 */       di += 4;
/* 244:    */     }
/* 245:264 */     return dst;
/* 246:    */   }
/* 247:    */   
/* 248:268 */   String domainName = null;
/* 249:269 */   String acctName = null;
/* 250:270 */   String origin_server = null;
/* 251:271 */   NtlmPasswordAuthentication origin_auth = null;
/* 252:    */   
/* 253:    */   public SID(byte[] src, int si)
/* 254:    */   {
/* 255:277 */     this.revision = src[(si++)];
/* 256:278 */     this.sub_authority_count = src[(si++)];
/* 257:279 */     this.identifier_authority = new byte[6];
/* 258:280 */     System.arraycopy(src, si, this.identifier_authority, 0, 6);
/* 259:281 */     si += 6;
/* 260:282 */     if (this.sub_authority_count > 100) {
/* 261:283 */       throw new RuntimeException("Invalid SID sub_authority_count");
/* 262:    */     }
/* 263:284 */     this.sub_authority = new int[this.sub_authority_count];
/* 264:285 */     for (int i = 0; i < this.sub_authority_count; i++)
/* 265:    */     {
/* 266:286 */       this.sub_authority[i] = ServerMessageBlock.readInt4(src, si);
/* 267:287 */       si += 4;
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public SID(String textual)
/* 272:    */     throws SmbException
/* 273:    */   {
/* 274:295 */     StringTokenizer st = new StringTokenizer(textual, "-");
/* 275:296 */     if ((st.countTokens() < 3) || (!st.nextToken().equals("S"))) {
/* 276:298 */       throw new SmbException("Bad textual SID format: " + textual);
/* 277:    */     }
/* 278:300 */     this.revision = Byte.parseByte(st.nextToken());
/* 279:301 */     String tmp = st.nextToken();
/* 280:302 */     long id = 0L;
/* 281:303 */     if (tmp.startsWith("0x")) {
/* 282:304 */       id = Long.parseLong(tmp.substring(2), 16);
/* 283:    */     } else {
/* 284:306 */       id = Long.parseLong(tmp);
/* 285:    */     }
/* 286:308 */     this.identifier_authority = new byte[6];
/* 287:309 */     for (int i = 5; id > 0L; i--)
/* 288:    */     {
/* 289:310 */       this.identifier_authority[i] = ((byte)(int)(id % 256L));
/* 290:311 */       id >>= 8;
/* 291:    */     }
/* 292:314 */     this.sub_authority_count = ((byte)st.countTokens());
/* 293:315 */     if (this.sub_authority_count > 0)
/* 294:    */     {
/* 295:316 */       this.sub_authority = new int[this.sub_authority_count];
/* 296:317 */       for (int i = 0; i < this.sub_authority_count; i++) {
/* 297:318 */         this.sub_authority[i] = ((int)(Long.parseLong(st.nextToken()) & 0xFFFFFFFF));
/* 298:    */       }
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public SID(SID domsid, int rid)
/* 303:    */   {
/* 304:329 */     this.revision = domsid.revision;
/* 305:330 */     this.identifier_authority = domsid.identifier_authority;
/* 306:331 */     this.sub_authority_count = ((byte)(domsid.sub_authority_count + 1));
/* 307:332 */     this.sub_authority = new int[this.sub_authority_count];
/* 308:334 */     for (int i = 0; i < domsid.sub_authority_count; i++) {
/* 309:335 */       this.sub_authority[i] = domsid.sub_authority[i];
/* 310:    */     }
/* 311:337 */     this.sub_authority[i] = rid;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public SID(rpc.sid_t sid, int type, String domainName, String acctName, boolean decrementAuthority)
/* 315:    */   {
/* 316:344 */     this.revision = sid.revision;
/* 317:345 */     this.sub_authority_count = sid.sub_authority_count;
/* 318:346 */     this.identifier_authority = sid.identifier_authority;
/* 319:347 */     this.sub_authority = sid.sub_authority;
/* 320:348 */     this.type = type;
/* 321:349 */     this.domainName = domainName;
/* 322:350 */     this.acctName = acctName;
/* 323:352 */     if (decrementAuthority)
/* 324:    */     {
/* 325:353 */       this.sub_authority_count = ((byte)(this.sub_authority_count - 1));
/* 326:354 */       this.sub_authority = new int[this.sub_authority_count];
/* 327:355 */       for (int i = 0; i < this.sub_authority_count; i++) {
/* 328:356 */         this.sub_authority[i] = sid.sub_authority[i];
/* 329:    */       }
/* 330:    */     }
/* 331:    */   }
/* 332:    */   
/* 333:    */   public SID getDomainSid()
/* 334:    */   {
/* 335:362 */     return new SID(this, 3, this.domainName, null, getType() != 3);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public int getRid()
/* 339:    */   {
/* 340:369 */     if (getType() == 3) {
/* 341:370 */       throw new IllegalArgumentException("This SID is a domain sid");
/* 342:    */     }
/* 343:371 */     return this.sub_authority[(this.sub_authority_count - 1)];
/* 344:    */   }
/* 345:    */   
/* 346:    */   public int getType()
/* 347:    */   {
/* 348:394 */     if (this.origin_server != null) {
/* 349:395 */       resolveWeak();
/* 350:    */     }
/* 351:396 */     return this.type;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public String getTypeText()
/* 355:    */   {
/* 356:404 */     if (this.origin_server != null) {
/* 357:405 */       resolveWeak();
/* 358:    */     }
/* 359:406 */     return SID_TYPE_NAMES[this.type];
/* 360:    */   }
/* 361:    */   
/* 362:    */   public String getDomainName()
/* 363:    */   {
/* 364:414 */     if (this.origin_server != null) {
/* 365:415 */       resolveWeak();
/* 366:    */     }
/* 367:416 */     if (this.type == 8)
/* 368:    */     {
/* 369:417 */       String full = toString();
/* 370:418 */       return full.substring(0, full.length() - getAccountName().length() - 1);
/* 371:    */     }
/* 372:420 */     return this.domainName;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public String getAccountName()
/* 376:    */   {
/* 377:429 */     if (this.origin_server != null) {
/* 378:430 */       resolveWeak();
/* 379:    */     }
/* 380:431 */     if (this.type == 8) {
/* 381:432 */       return "" + this.sub_authority[(this.sub_authority_count - 1)];
/* 382:    */     }
/* 383:433 */     if (this.type == 3) {
/* 384:434 */       return "";
/* 385:    */     }
/* 386:435 */     return this.acctName;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public int hashCode()
/* 390:    */   {
/* 391:439 */     int hcode = this.identifier_authority[5];
/* 392:440 */     for (int i = 0; i < this.sub_authority_count; i++) {
/* 393:441 */       hcode += 65599 * this.sub_authority[i];
/* 394:    */     }
/* 395:443 */     return hcode;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public boolean equals(Object obj)
/* 399:    */   {
/* 400:446 */     if ((obj instanceof SID))
/* 401:    */     {
/* 402:447 */       SID sid = (SID)obj;
/* 403:448 */       if (sid == this) {
/* 404:449 */         return true;
/* 405:    */       }
/* 406:450 */       if (sid.sub_authority_count == this.sub_authority_count)
/* 407:    */       {
/* 408:451 */         int i = this.sub_authority_count;
/* 409:452 */         while (i-- > 0) {
/* 410:453 */           if (sid.sub_authority[i] != this.sub_authority[i]) {
/* 411:454 */             return false;
/* 412:    */           }
/* 413:    */         }
/* 414:457 */         for (i = 0; i < 6; i++) {
/* 415:458 */           if (sid.identifier_authority[i] != this.identifier_authority[i]) {
/* 416:459 */             return false;
/* 417:    */           }
/* 418:    */         }
/* 419:463 */         return sid.revision == this.revision;
/* 420:    */       }
/* 421:    */     }
/* 422:466 */     return false;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public String toString()
/* 426:    */   {
/* 427:474 */     String ret = "S-" + (this.revision & 0xFF) + "-";
/* 428:476 */     if ((this.identifier_authority[0] != 0) || (this.identifier_authority[1] != 0))
/* 429:    */     {
/* 430:477 */       ret = ret + "0x";
/* 431:478 */       ret = ret + Hexdump.toHexString(this.identifier_authority, 0, 6);
/* 432:    */     }
/* 433:    */     else
/* 434:    */     {
/* 435:480 */       long shift = 0L;
/* 436:481 */       long id = 0L;
/* 437:482 */       for (int i = 5; i > 1; i--)
/* 438:    */       {
/* 439:483 */         id += ((this.identifier_authority[i] & 0xFF) << (int)shift);
/* 440:484 */         shift += 8L;
/* 441:    */       }
/* 442:486 */       ret = ret + id;
/* 443:    */     }
/* 444:489 */     for (int i = 0; i < this.sub_authority_count; i++) {
/* 445:490 */       ret = ret + "-" + (this.sub_authority[i] & 0xFFFFFFFF);
/* 446:    */     }
/* 447:492 */     return ret;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public String toDisplayString()
/* 451:    */   {
/* 452:512 */     if (this.origin_server != null) {
/* 453:513 */       resolveWeak();
/* 454:    */     }
/* 455:514 */     if (this.domainName != null)
/* 456:    */     {
/* 457:    */       String str;
/* 458:    */       String str;
/* 459:517 */       if (this.type == 3)
/* 460:    */       {
/* 461:518 */         str = this.domainName;
/* 462:    */       }
/* 463:    */       else
/* 464:    */       {
/* 465:    */         String str;
/* 466:519 */         if ((this.type == 5) || (this.domainName.equals("BUILTIN")))
/* 467:    */         {
/* 468:    */           String str;
/* 469:521 */           if (this.type == 8) {
/* 470:522 */             str = toString();
/* 471:    */           } else {
/* 472:524 */             str = this.acctName;
/* 473:    */           }
/* 474:    */         }
/* 475:    */         else
/* 476:    */         {
/* 477:527 */           str = this.domainName + "\\" + this.acctName;
/* 478:    */         }
/* 479:    */       }
/* 480:530 */       return str;
/* 481:    */     }
/* 482:532 */     return toString();
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void resolve(String authorityServerName, NtlmPasswordAuthentication auth)
/* 486:    */     throws IOException
/* 487:    */   {
/* 488:547 */     SID[] sids = new SID[1];
/* 489:548 */     sids[0] = this;
/* 490:549 */     resolveSids(authorityServerName, auth, sids);
/* 491:    */   }
/* 492:    */   
/* 493:    */   void resolveWeak()
/* 494:    */   {
/* 495:553 */     if (this.origin_server != null) {
/* 496:    */       try
/* 497:    */       {
/* 498:555 */         resolve(this.origin_server, this.origin_auth);
/* 499:    */       }
/* 500:    */       catch (IOException ioe) {}finally
/* 501:    */       {
/* 502:558 */         this.origin_server = null;
/* 503:559 */         this.origin_auth = null;
/* 504:    */       }
/* 505:    */     }
/* 506:    */   }
/* 507:    */   
/* 508:    */   static SID[] getGroupMemberSids0(DcerpcHandle handle, SamrDomainHandle domainHandle, SID domsid, int rid, int flags)
/* 509:    */     throws IOException
/* 510:    */   {
/* 511:569 */     SamrAliasHandle aliasHandle = null;
/* 512:570 */     lsarpc.LsarSidArray sidarray = new lsarpc.LsarSidArray();
/* 513:571 */     MsrpcGetMembersInAlias rpc = null;
/* 514:    */     try
/* 515:    */     {
/* 516:574 */       aliasHandle = new SamrAliasHandle(handle, domainHandle, 131084, rid);
/* 517:575 */       rpc = new MsrpcGetMembersInAlias(aliasHandle, sidarray);
/* 518:576 */       handle.sendrecv(rpc);
/* 519:577 */       if (rpc.retval != 0) {
/* 520:578 */         throw new SmbException(rpc.retval, false);
/* 521:    */       }
/* 522:579 */       SID[] sids = new SID[rpc.sids.num_sids];
/* 523:    */       
/* 524:581 */       String origin_server = handle.getServer();
/* 525:582 */       NtlmPasswordAuthentication origin_auth = (NtlmPasswordAuthentication)handle.getPrincipal();
/* 526:585 */       for (int i = 0; i < sids.length; i++)
/* 527:    */       {
/* 528:586 */         sids[i] = new SID(rpc.sids.sids[i].sid, 0, null, null, false);
/* 529:    */         
/* 530:    */ 
/* 531:    */ 
/* 532:    */ 
/* 533:591 */         sids[i].origin_server = origin_server;
/* 534:592 */         sids[i].origin_auth = origin_auth;
/* 535:    */       }
/* 536:594 */       if ((sids.length > 0) && ((flags & 0x1) != 0)) {
/* 537:595 */         resolveSids(origin_server, origin_auth, sids);
/* 538:    */       }
/* 539:597 */       return sids;
/* 540:    */     }
/* 541:    */     finally
/* 542:    */     {
/* 543:599 */       if (aliasHandle != null) {
/* 544:600 */         aliasHandle.close();
/* 545:    */       }
/* 546:    */     }
/* 547:    */   }
/* 548:    */   
/* 549:    */   public SID[] getGroupMemberSids(String authorityServerName, NtlmPasswordAuthentication auth, int flags)
/* 550:    */     throws IOException
/* 551:    */   {
/* 552:608 */     if ((this.type != 2) && (this.type != 4)) {
/* 553:609 */       return new SID[0];
/* 554:    */     }
/* 555:611 */     DcerpcHandle handle = null;
/* 556:612 */     SamrPolicyHandle policyHandle = null;
/* 557:613 */     SamrDomainHandle domainHandle = null;
/* 558:614 */     SID domsid = getDomainSid();
/* 559:616 */     synchronized (sid_cache)
/* 560:    */     {
/* 561:    */       try
/* 562:    */       {
/* 563:618 */         handle = DcerpcHandle.getHandle("ncacn_np:" + authorityServerName + "[\\PIPE\\samr]", auth);
/* 564:    */         
/* 565:620 */         policyHandle = new SamrPolicyHandle(handle, authorityServerName, 48);
/* 566:621 */         domainHandle = new SamrDomainHandle(handle, policyHandle, 512, domsid);
/* 567:622 */         SID[] arrayOfSID = getGroupMemberSids0(handle, domainHandle, domsid, getRid(), flags);jsr 17;return arrayOfSID;
/* 568:    */       }
/* 569:    */       finally
/* 570:    */       {
/* 571:628 */         jsr 6;
/* 572:    */       }
/* 573:628 */       localObject2 = returnAddress;
/* 574:628 */       if (handle != null)
/* 575:    */       {
/* 576:629 */         if (policyHandle != null)
/* 577:    */         {
/* 578:630 */           if (domainHandle != null) {
/* 579:631 */             domainHandle.close();
/* 580:    */           }
/* 581:633 */           policyHandle.close();
/* 582:    */         }
/* 583:635 */         handle.close();
/* 584:    */       }
/* 585:635 */       ret;
/* 586:    */     }
/* 587:    */   }
/* 588:    */   
/* 589:    */   static Map getLocalGroupsMap(String authorityServerName, NtlmPasswordAuthentication auth, int flags)
/* 590:    */     throws IOException
/* 591:    */   {
/* 592:666 */     SID domsid = getServerSid(authorityServerName, auth);
/* 593:667 */     DcerpcHandle handle = null;
/* 594:668 */     SamrPolicyHandle policyHandle = null;
/* 595:669 */     SamrDomainHandle domainHandle = null;
/* 596:670 */     samr.SamrSamArray sam = new samr.SamrSamArray();
/* 597:673 */     synchronized (sid_cache)
/* 598:    */     {
/* 599:    */       try
/* 600:    */       {
/* 601:675 */         handle = DcerpcHandle.getHandle("ncacn_np:" + authorityServerName + "[\\PIPE\\samr]", auth);
/* 602:    */         
/* 603:677 */         policyHandle = new SamrPolicyHandle(handle, authorityServerName, 33554432);
/* 604:678 */         domainHandle = new SamrDomainHandle(handle, policyHandle, 33554432, domsid);
/* 605:679 */         MsrpcEnumerateAliasesInDomain rpc = new MsrpcEnumerateAliasesInDomain(domainHandle, 65535, sam);
/* 606:680 */         handle.sendrecv(rpc);
/* 607:681 */         if (rpc.retval != 0) {
/* 608:682 */           throw new SmbException(rpc.retval, false);
/* 609:    */         }
/* 610:684 */         Map map = new HashMap();
/* 611:686 */         for (int ei = 0; ei < rpc.sam.count; ei++)
/* 612:    */         {
/* 613:687 */           samr.SamrSamEntry entry = rpc.sam.entries[ei];
/* 614:    */           
/* 615:689 */           SID[] mems = getGroupMemberSids0(handle, domainHandle, domsid, entry.idx, flags);
/* 616:    */           
/* 617:    */ 
/* 618:    */ 
/* 619:    */ 
/* 620:694 */           SID groupSid = new SID(domsid, entry.idx);
/* 621:695 */           groupSid.type = 4;
/* 622:696 */           groupSid.domainName = domsid.getDomainName();
/* 623:697 */           groupSid.acctName = new UnicodeString(entry.name, false).toString();
/* 624:699 */           for (int mi = 0; mi < mems.length; mi++)
/* 625:    */           {
/* 626:700 */             ArrayList groups = (ArrayList)map.get(mems[mi]);
/* 627:701 */             if (groups == null)
/* 628:    */             {
/* 629:702 */               groups = new ArrayList();
/* 630:703 */               map.put(mems[mi], groups);
/* 631:    */             }
/* 632:705 */             if (!groups.contains(groupSid)) {
/* 633:706 */               groups.add(groupSid);
/* 634:    */             }
/* 635:    */           }
/* 636:    */         }
/* 637:710 */         ei = map;jsr 17;return ei;
/* 638:    */       }
/* 639:    */       finally
/* 640:    */       {
/* 641:712 */         jsr 6;
/* 642:    */       }
/* 643:712 */       localObject2 = returnAddress;
/* 644:712 */       if (handle != null)
/* 645:    */       {
/* 646:713 */         if (policyHandle != null)
/* 647:    */         {
/* 648:714 */           if (domainHandle != null) {
/* 649:715 */             domainHandle.close();
/* 650:    */           }
/* 651:717 */           policyHandle.close();
/* 652:    */         }
/* 653:719 */         handle.close();
/* 654:    */       }
/* 655:719 */       ret;
/* 656:    */     }
/* 657:    */   }
/* 658:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SID
 * JD-Core Version:    0.7.0.1
 */