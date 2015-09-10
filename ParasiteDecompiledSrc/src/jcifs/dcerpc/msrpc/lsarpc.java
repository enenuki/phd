/*   1:    */ package jcifs.dcerpc.msrpc;
/*   2:    */ 
/*   3:    */ import jcifs.dcerpc.DcerpcMessage;
/*   4:    */ import jcifs.dcerpc.ndr.NdrBuffer;
/*   5:    */ import jcifs.dcerpc.ndr.NdrException;
/*   6:    */ import jcifs.dcerpc.ndr.NdrObject;
/*   7:    */ import jcifs.dcerpc.ndr.NdrSmall;
/*   8:    */ import jcifs.dcerpc.rpc.policy_handle;
/*   9:    */ import jcifs.dcerpc.rpc.sid_t;
/*  10:    */ import jcifs.dcerpc.rpc.unicode_string;
/*  11:    */ import jcifs.dcerpc.rpc.uuid_t;
/*  12:    */ 
/*  13:    */ public class lsarpc
/*  14:    */ {
/*  15:    */   public static final int POLICY_INFO_AUDIT_EVENTS = 2;
/*  16:    */   public static final int POLICY_INFO_PRIMARY_DOMAIN = 3;
/*  17:    */   public static final int POLICY_INFO_ACCOUNT_DOMAIN = 5;
/*  18:    */   public static final int POLICY_INFO_SERVER_ROLE = 6;
/*  19:    */   public static final int POLICY_INFO_MODIFICATION = 9;
/*  20:    */   public static final int POLICY_INFO_DNS_DOMAIN = 12;
/*  21:    */   public static final int SID_NAME_USE_NONE = 0;
/*  22:    */   public static final int SID_NAME_USER = 1;
/*  23:    */   public static final int SID_NAME_DOM_GRP = 2;
/*  24:    */   public static final int SID_NAME_DOMAIN = 3;
/*  25:    */   public static final int SID_NAME_ALIAS = 4;
/*  26:    */   public static final int SID_NAME_WKN_GRP = 5;
/*  27:    */   public static final int SID_NAME_DELETED = 6;
/*  28:    */   public static final int SID_NAME_INVALID = 7;
/*  29:    */   public static final int SID_NAME_UNKNOWN = 8;
/*  30:    */   
/*  31:    */   public static String getSyntax()
/*  32:    */   {
/*  33:  9 */     return "12345778-1234-abcd-ef00-0123456789ab:0.0";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static class LsarQosInfo
/*  37:    */     extends NdrObject
/*  38:    */   {
/*  39:    */     public int length;
/*  40:    */     public short impersonation_level;
/*  41:    */     public byte context_mode;
/*  42:    */     public byte effective_only;
/*  43:    */     
/*  44:    */     public void encode(NdrBuffer _dst)
/*  45:    */       throws NdrException
/*  46:    */     {
/*  47: 20 */       _dst.align(4);
/*  48: 21 */       _dst.enc_ndr_long(this.length);
/*  49: 22 */       _dst.enc_ndr_short(this.impersonation_level);
/*  50: 23 */       _dst.enc_ndr_small(this.context_mode);
/*  51: 24 */       _dst.enc_ndr_small(this.effective_only);
/*  52:    */     }
/*  53:    */     
/*  54:    */     public void decode(NdrBuffer _src)
/*  55:    */       throws NdrException
/*  56:    */     {
/*  57: 28 */       _src.align(4);
/*  58: 29 */       this.length = _src.dec_ndr_long();
/*  59: 30 */       this.impersonation_level = ((short)_src.dec_ndr_short());
/*  60: 31 */       this.context_mode = ((byte)_src.dec_ndr_small());
/*  61: 32 */       this.effective_only = ((byte)_src.dec_ndr_small());
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static class LsarObjectAttributes
/*  66:    */     extends NdrObject
/*  67:    */   {
/*  68:    */     public int length;
/*  69:    */     public NdrSmall root_directory;
/*  70:    */     public rpc.unicode_string object_name;
/*  71:    */     public int attributes;
/*  72:    */     public int security_descriptor;
/*  73:    */     public lsarpc.LsarQosInfo security_quality_of_service;
/*  74:    */     
/*  75:    */     public void encode(NdrBuffer _dst)
/*  76:    */       throws NdrException
/*  77:    */     {
/*  78: 46 */       _dst.align(4);
/*  79: 47 */       _dst.enc_ndr_long(this.length);
/*  80: 48 */       _dst.enc_ndr_referent(this.root_directory, 1);
/*  81: 49 */       _dst.enc_ndr_referent(this.object_name, 1);
/*  82: 50 */       _dst.enc_ndr_long(this.attributes);
/*  83: 51 */       _dst.enc_ndr_long(this.security_descriptor);
/*  84: 52 */       _dst.enc_ndr_referent(this.security_quality_of_service, 1);
/*  85: 54 */       if (this.root_directory != null)
/*  86:    */       {
/*  87: 55 */         _dst = _dst.deferred;
/*  88: 56 */         this.root_directory.encode(_dst);
/*  89:    */       }
/*  90: 59 */       if (this.object_name != null)
/*  91:    */       {
/*  92: 60 */         _dst = _dst.deferred;
/*  93: 61 */         this.object_name.encode(_dst);
/*  94:    */       }
/*  95: 64 */       if (this.security_quality_of_service != null)
/*  96:    */       {
/*  97: 65 */         _dst = _dst.deferred;
/*  98: 66 */         this.security_quality_of_service.encode(_dst);
/*  99:    */       }
/* 100:    */     }
/* 101:    */     
/* 102:    */     public void decode(NdrBuffer _src)
/* 103:    */       throws NdrException
/* 104:    */     {
/* 105: 71 */       _src.align(4);
/* 106: 72 */       this.length = _src.dec_ndr_long();
/* 107: 73 */       int _root_directoryp = _src.dec_ndr_long();
/* 108: 74 */       int _object_namep = _src.dec_ndr_long();
/* 109: 75 */       this.attributes = _src.dec_ndr_long();
/* 110: 76 */       this.security_descriptor = _src.dec_ndr_long();
/* 111: 77 */       int _security_quality_of_servicep = _src.dec_ndr_long();
/* 112: 79 */       if (_root_directoryp != 0)
/* 113:    */       {
/* 114: 80 */         _src = _src.deferred;
/* 115: 81 */         this.root_directory.decode(_src);
/* 116:    */       }
/* 117: 84 */       if (_object_namep != 0)
/* 118:    */       {
/* 119: 85 */         if (this.object_name == null) {
/* 120: 86 */           this.object_name = new rpc.unicode_string();
/* 121:    */         }
/* 122: 88 */         _src = _src.deferred;
/* 123: 89 */         this.object_name.decode(_src);
/* 124:    */       }
/* 125: 92 */       if (_security_quality_of_servicep != 0)
/* 126:    */       {
/* 127: 93 */         if (this.security_quality_of_service == null) {
/* 128: 94 */           this.security_quality_of_service = new lsarpc.LsarQosInfo();
/* 129:    */         }
/* 130: 96 */         _src = _src.deferred;
/* 131: 97 */         this.security_quality_of_service.decode(_src);
/* 132:    */       }
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static class LsarDomainInfo
/* 137:    */     extends NdrObject
/* 138:    */   {
/* 139:    */     public rpc.unicode_string name;
/* 140:    */     public rpc.sid_t sid;
/* 141:    */     
/* 142:    */     public void encode(NdrBuffer _dst)
/* 143:    */       throws NdrException
/* 144:    */     {
/* 145:108 */       _dst.align(4);
/* 146:109 */       _dst.enc_ndr_short(this.name.length);
/* 147:110 */       _dst.enc_ndr_short(this.name.maximum_length);
/* 148:111 */       _dst.enc_ndr_referent(this.name.buffer, 1);
/* 149:112 */       _dst.enc_ndr_referent(this.sid, 1);
/* 150:114 */       if (this.name.buffer != null)
/* 151:    */       {
/* 152:115 */         _dst = _dst.deferred;
/* 153:116 */         int _name_bufferl = this.name.length / 2;
/* 154:117 */         int _name_buffers = this.name.maximum_length / 2;
/* 155:118 */         _dst.enc_ndr_long(_name_buffers);
/* 156:119 */         _dst.enc_ndr_long(0);
/* 157:120 */         _dst.enc_ndr_long(_name_bufferl);
/* 158:121 */         int _name_bufferi = _dst.index;
/* 159:122 */         _dst.advance(2 * _name_bufferl);
/* 160:    */         
/* 161:124 */         _dst = _dst.derive(_name_bufferi);
/* 162:125 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 163:126 */           _dst.enc_ndr_short(this.name.buffer[_i]);
/* 164:    */         }
/* 165:    */       }
/* 166:129 */       if (this.sid != null)
/* 167:    */       {
/* 168:130 */         _dst = _dst.deferred;
/* 169:131 */         this.sid.encode(_dst);
/* 170:    */       }
/* 171:    */     }
/* 172:    */     
/* 173:    */     public void decode(NdrBuffer _src)
/* 174:    */       throws NdrException
/* 175:    */     {
/* 176:136 */       _src.align(4);
/* 177:137 */       _src.align(4);
/* 178:138 */       if (this.name == null) {
/* 179:139 */         this.name = new rpc.unicode_string();
/* 180:    */       }
/* 181:141 */       this.name.length = ((short)_src.dec_ndr_short());
/* 182:142 */       this.name.maximum_length = ((short)_src.dec_ndr_short());
/* 183:143 */       int _name_bufferp = _src.dec_ndr_long();
/* 184:144 */       int _sidp = _src.dec_ndr_long();
/* 185:146 */       if (_name_bufferp != 0)
/* 186:    */       {
/* 187:147 */         _src = _src.deferred;
/* 188:148 */         int _name_buffers = _src.dec_ndr_long();
/* 189:149 */         _src.dec_ndr_long();
/* 190:150 */         int _name_bufferl = _src.dec_ndr_long();
/* 191:151 */         int _name_bufferi = _src.index;
/* 192:152 */         _src.advance(2 * _name_bufferl);
/* 193:154 */         if (this.name.buffer == null)
/* 194:    */         {
/* 195:155 */           if ((_name_buffers < 0) || (_name_buffers > 65535)) {
/* 196:155 */             throw new NdrException("invalid array conformance");
/* 197:    */           }
/* 198:156 */           this.name.buffer = new short[_name_buffers];
/* 199:    */         }
/* 200:158 */         _src = _src.derive(_name_bufferi);
/* 201:159 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 202:160 */           this.name.buffer[_i] = ((short)_src.dec_ndr_short());
/* 203:    */         }
/* 204:    */       }
/* 205:163 */       if (_sidp != 0)
/* 206:    */       {
/* 207:164 */         if (this.sid == null) {
/* 208:165 */           this.sid = new rpc.sid_t();
/* 209:    */         }
/* 210:167 */         _src = _src.deferred;
/* 211:168 */         this.sid.decode(_src);
/* 212:    */       }
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static class LsarDnsDomainInfo
/* 217:    */     extends NdrObject
/* 218:    */   {
/* 219:    */     public rpc.unicode_string name;
/* 220:    */     public rpc.unicode_string dns_domain;
/* 221:    */     public rpc.unicode_string dns_forest;
/* 222:    */     public rpc.uuid_t domain_guid;
/* 223:    */     public rpc.sid_t sid;
/* 224:    */     
/* 225:    */     public void encode(NdrBuffer _dst)
/* 226:    */       throws NdrException
/* 227:    */     {
/* 228:182 */       _dst.align(4);
/* 229:183 */       _dst.enc_ndr_short(this.name.length);
/* 230:184 */       _dst.enc_ndr_short(this.name.maximum_length);
/* 231:185 */       _dst.enc_ndr_referent(this.name.buffer, 1);
/* 232:186 */       _dst.enc_ndr_short(this.dns_domain.length);
/* 233:187 */       _dst.enc_ndr_short(this.dns_domain.maximum_length);
/* 234:188 */       _dst.enc_ndr_referent(this.dns_domain.buffer, 1);
/* 235:189 */       _dst.enc_ndr_short(this.dns_forest.length);
/* 236:190 */       _dst.enc_ndr_short(this.dns_forest.maximum_length);
/* 237:191 */       _dst.enc_ndr_referent(this.dns_forest.buffer, 1);
/* 238:192 */       _dst.enc_ndr_long(this.domain_guid.time_low);
/* 239:193 */       _dst.enc_ndr_short(this.domain_guid.time_mid);
/* 240:194 */       _dst.enc_ndr_short(this.domain_guid.time_hi_and_version);
/* 241:195 */       _dst.enc_ndr_small(this.domain_guid.clock_seq_hi_and_reserved);
/* 242:196 */       _dst.enc_ndr_small(this.domain_guid.clock_seq_low);
/* 243:197 */       int _domain_guid_nodes = 6;
/* 244:198 */       int _domain_guid_nodei = _dst.index;
/* 245:199 */       _dst.advance(1 * _domain_guid_nodes);
/* 246:200 */       _dst.enc_ndr_referent(this.sid, 1);
/* 247:202 */       if (this.name.buffer != null)
/* 248:    */       {
/* 249:203 */         _dst = _dst.deferred;
/* 250:204 */         int _name_bufferl = this.name.length / 2;
/* 251:205 */         int _name_buffers = this.name.maximum_length / 2;
/* 252:206 */         _dst.enc_ndr_long(_name_buffers);
/* 253:207 */         _dst.enc_ndr_long(0);
/* 254:208 */         _dst.enc_ndr_long(_name_bufferl);
/* 255:209 */         int _name_bufferi = _dst.index;
/* 256:210 */         _dst.advance(2 * _name_bufferl);
/* 257:    */         
/* 258:212 */         _dst = _dst.derive(_name_bufferi);
/* 259:213 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 260:214 */           _dst.enc_ndr_short(this.name.buffer[_i]);
/* 261:    */         }
/* 262:    */       }
/* 263:217 */       if (this.dns_domain.buffer != null)
/* 264:    */       {
/* 265:218 */         _dst = _dst.deferred;
/* 266:219 */         int _dns_domain_bufferl = this.dns_domain.length / 2;
/* 267:220 */         int _dns_domain_buffers = this.dns_domain.maximum_length / 2;
/* 268:221 */         _dst.enc_ndr_long(_dns_domain_buffers);
/* 269:222 */         _dst.enc_ndr_long(0);
/* 270:223 */         _dst.enc_ndr_long(_dns_domain_bufferl);
/* 271:224 */         int _dns_domain_bufferi = _dst.index;
/* 272:225 */         _dst.advance(2 * _dns_domain_bufferl);
/* 273:    */         
/* 274:227 */         _dst = _dst.derive(_dns_domain_bufferi);
/* 275:228 */         for (int _i = 0; _i < _dns_domain_bufferl; _i++) {
/* 276:229 */           _dst.enc_ndr_short(this.dns_domain.buffer[_i]);
/* 277:    */         }
/* 278:    */       }
/* 279:232 */       if (this.dns_forest.buffer != null)
/* 280:    */       {
/* 281:233 */         _dst = _dst.deferred;
/* 282:234 */         int _dns_forest_bufferl = this.dns_forest.length / 2;
/* 283:235 */         int _dns_forest_buffers = this.dns_forest.maximum_length / 2;
/* 284:236 */         _dst.enc_ndr_long(_dns_forest_buffers);
/* 285:237 */         _dst.enc_ndr_long(0);
/* 286:238 */         _dst.enc_ndr_long(_dns_forest_bufferl);
/* 287:239 */         int _dns_forest_bufferi = _dst.index;
/* 288:240 */         _dst.advance(2 * _dns_forest_bufferl);
/* 289:    */         
/* 290:242 */         _dst = _dst.derive(_dns_forest_bufferi);
/* 291:243 */         for (int _i = 0; _i < _dns_forest_bufferl; _i++) {
/* 292:244 */           _dst.enc_ndr_short(this.dns_forest.buffer[_i]);
/* 293:    */         }
/* 294:    */       }
/* 295:247 */       _dst = _dst.derive(_domain_guid_nodei);
/* 296:248 */       for (int _i = 0; _i < _domain_guid_nodes; _i++) {
/* 297:249 */         _dst.enc_ndr_small(this.domain_guid.node[_i]);
/* 298:    */       }
/* 299:251 */       if (this.sid != null)
/* 300:    */       {
/* 301:252 */         _dst = _dst.deferred;
/* 302:253 */         this.sid.encode(_dst);
/* 303:    */       }
/* 304:    */     }
/* 305:    */     
/* 306:    */     public void decode(NdrBuffer _src)
/* 307:    */       throws NdrException
/* 308:    */     {
/* 309:258 */       _src.align(4);
/* 310:259 */       _src.align(4);
/* 311:260 */       if (this.name == null) {
/* 312:261 */         this.name = new rpc.unicode_string();
/* 313:    */       }
/* 314:263 */       this.name.length = ((short)_src.dec_ndr_short());
/* 315:264 */       this.name.maximum_length = ((short)_src.dec_ndr_short());
/* 316:265 */       int _name_bufferp = _src.dec_ndr_long();
/* 317:266 */       _src.align(4);
/* 318:267 */       if (this.dns_domain == null) {
/* 319:268 */         this.dns_domain = new rpc.unicode_string();
/* 320:    */       }
/* 321:270 */       this.dns_domain.length = ((short)_src.dec_ndr_short());
/* 322:271 */       this.dns_domain.maximum_length = ((short)_src.dec_ndr_short());
/* 323:272 */       int _dns_domain_bufferp = _src.dec_ndr_long();
/* 324:273 */       _src.align(4);
/* 325:274 */       if (this.dns_forest == null) {
/* 326:275 */         this.dns_forest = new rpc.unicode_string();
/* 327:    */       }
/* 328:277 */       this.dns_forest.length = ((short)_src.dec_ndr_short());
/* 329:278 */       this.dns_forest.maximum_length = ((short)_src.dec_ndr_short());
/* 330:279 */       int _dns_forest_bufferp = _src.dec_ndr_long();
/* 331:280 */       _src.align(4);
/* 332:281 */       if (this.domain_guid == null) {
/* 333:282 */         this.domain_guid = new rpc.uuid_t();
/* 334:    */       }
/* 335:284 */       this.domain_guid.time_low = _src.dec_ndr_long();
/* 336:285 */       this.domain_guid.time_mid = ((short)_src.dec_ndr_short());
/* 337:286 */       this.domain_guid.time_hi_and_version = ((short)_src.dec_ndr_short());
/* 338:287 */       this.domain_guid.clock_seq_hi_and_reserved = ((byte)_src.dec_ndr_small());
/* 339:288 */       this.domain_guid.clock_seq_low = ((byte)_src.dec_ndr_small());
/* 340:289 */       int _domain_guid_nodes = 6;
/* 341:290 */       int _domain_guid_nodei = _src.index;
/* 342:291 */       _src.advance(1 * _domain_guid_nodes);
/* 343:292 */       int _sidp = _src.dec_ndr_long();
/* 344:294 */       if (_name_bufferp != 0)
/* 345:    */       {
/* 346:295 */         _src = _src.deferred;
/* 347:296 */         int _name_buffers = _src.dec_ndr_long();
/* 348:297 */         _src.dec_ndr_long();
/* 349:298 */         int _name_bufferl = _src.dec_ndr_long();
/* 350:299 */         int _name_bufferi = _src.index;
/* 351:300 */         _src.advance(2 * _name_bufferl);
/* 352:302 */         if (this.name.buffer == null)
/* 353:    */         {
/* 354:303 */           if ((_name_buffers < 0) || (_name_buffers > 65535)) {
/* 355:303 */             throw new NdrException("invalid array conformance");
/* 356:    */           }
/* 357:304 */           this.name.buffer = new short[_name_buffers];
/* 358:    */         }
/* 359:306 */         _src = _src.derive(_name_bufferi);
/* 360:307 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 361:308 */           this.name.buffer[_i] = ((short)_src.dec_ndr_short());
/* 362:    */         }
/* 363:    */       }
/* 364:311 */       if (_dns_domain_bufferp != 0)
/* 365:    */       {
/* 366:312 */         _src = _src.deferred;
/* 367:313 */         int _dns_domain_buffers = _src.dec_ndr_long();
/* 368:314 */         _src.dec_ndr_long();
/* 369:315 */         int _dns_domain_bufferl = _src.dec_ndr_long();
/* 370:316 */         int _dns_domain_bufferi = _src.index;
/* 371:317 */         _src.advance(2 * _dns_domain_bufferl);
/* 372:319 */         if (this.dns_domain.buffer == null)
/* 373:    */         {
/* 374:320 */           if ((_dns_domain_buffers < 0) || (_dns_domain_buffers > 65535)) {
/* 375:320 */             throw new NdrException("invalid array conformance");
/* 376:    */           }
/* 377:321 */           this.dns_domain.buffer = new short[_dns_domain_buffers];
/* 378:    */         }
/* 379:323 */         _src = _src.derive(_dns_domain_bufferi);
/* 380:324 */         for (int _i = 0; _i < _dns_domain_bufferl; _i++) {
/* 381:325 */           this.dns_domain.buffer[_i] = ((short)_src.dec_ndr_short());
/* 382:    */         }
/* 383:    */       }
/* 384:328 */       if (_dns_forest_bufferp != 0)
/* 385:    */       {
/* 386:329 */         _src = _src.deferred;
/* 387:330 */         int _dns_forest_buffers = _src.dec_ndr_long();
/* 388:331 */         _src.dec_ndr_long();
/* 389:332 */         int _dns_forest_bufferl = _src.dec_ndr_long();
/* 390:333 */         int _dns_forest_bufferi = _src.index;
/* 391:334 */         _src.advance(2 * _dns_forest_bufferl);
/* 392:336 */         if (this.dns_forest.buffer == null)
/* 393:    */         {
/* 394:337 */           if ((_dns_forest_buffers < 0) || (_dns_forest_buffers > 65535)) {
/* 395:337 */             throw new NdrException("invalid array conformance");
/* 396:    */           }
/* 397:338 */           this.dns_forest.buffer = new short[_dns_forest_buffers];
/* 398:    */         }
/* 399:340 */         _src = _src.derive(_dns_forest_bufferi);
/* 400:341 */         for (int _i = 0; _i < _dns_forest_bufferl; _i++) {
/* 401:342 */           this.dns_forest.buffer[_i] = ((short)_src.dec_ndr_short());
/* 402:    */         }
/* 403:    */       }
/* 404:345 */       if (this.domain_guid.node == null)
/* 405:    */       {
/* 406:346 */         if ((_domain_guid_nodes < 0) || (_domain_guid_nodes > 65535)) {
/* 407:346 */           throw new NdrException("invalid array conformance");
/* 408:    */         }
/* 409:347 */         this.domain_guid.node = new byte[_domain_guid_nodes];
/* 410:    */       }
/* 411:349 */       _src = _src.derive(_domain_guid_nodei);
/* 412:350 */       for (int _i = 0; _i < _domain_guid_nodes; _i++) {
/* 413:351 */         this.domain_guid.node[_i] = ((byte)_src.dec_ndr_small());
/* 414:    */       }
/* 415:353 */       if (_sidp != 0)
/* 416:    */       {
/* 417:354 */         if (this.sid == null) {
/* 418:355 */           this.sid = new rpc.sid_t();
/* 419:    */         }
/* 420:357 */         _src = _src.deferred;
/* 421:358 */         this.sid.decode(_src);
/* 422:    */       }
/* 423:    */     }
/* 424:    */   }
/* 425:    */   
/* 426:    */   public static class LsarSidPtr
/* 427:    */     extends NdrObject
/* 428:    */   {
/* 429:    */     public rpc.sid_t sid;
/* 430:    */     
/* 431:    */     public void encode(NdrBuffer _dst)
/* 432:    */       throws NdrException
/* 433:    */     {
/* 434:375 */       _dst.align(4);
/* 435:376 */       _dst.enc_ndr_referent(this.sid, 1);
/* 436:378 */       if (this.sid != null)
/* 437:    */       {
/* 438:379 */         _dst = _dst.deferred;
/* 439:380 */         this.sid.encode(_dst);
/* 440:    */       }
/* 441:    */     }
/* 442:    */     
/* 443:    */     public void decode(NdrBuffer _src)
/* 444:    */       throws NdrException
/* 445:    */     {
/* 446:385 */       _src.align(4);
/* 447:386 */       int _sidp = _src.dec_ndr_long();
/* 448:388 */       if (_sidp != 0)
/* 449:    */       {
/* 450:389 */         if (this.sid == null) {
/* 451:390 */           this.sid = new rpc.sid_t();
/* 452:    */         }
/* 453:392 */         _src = _src.deferred;
/* 454:393 */         this.sid.decode(_src);
/* 455:    */       }
/* 456:    */     }
/* 457:    */   }
/* 458:    */   
/* 459:    */   public static class LsarSidArray
/* 460:    */     extends NdrObject
/* 461:    */   {
/* 462:    */     public int num_sids;
/* 463:    */     public lsarpc.LsarSidPtr[] sids;
/* 464:    */     
/* 465:    */     public void encode(NdrBuffer _dst)
/* 466:    */       throws NdrException
/* 467:    */     {
/* 468:404 */       _dst.align(4);
/* 469:405 */       _dst.enc_ndr_long(this.num_sids);
/* 470:406 */       _dst.enc_ndr_referent(this.sids, 1);
/* 471:408 */       if (this.sids != null)
/* 472:    */       {
/* 473:409 */         _dst = _dst.deferred;
/* 474:410 */         int _sidss = this.num_sids;
/* 475:411 */         _dst.enc_ndr_long(_sidss);
/* 476:412 */         int _sidsi = _dst.index;
/* 477:413 */         _dst.advance(4 * _sidss);
/* 478:    */         
/* 479:415 */         _dst = _dst.derive(_sidsi);
/* 480:416 */         for (int _i = 0; _i < _sidss; _i++) {
/* 481:417 */           this.sids[_i].encode(_dst);
/* 482:    */         }
/* 483:    */       }
/* 484:    */     }
/* 485:    */     
/* 486:    */     public void decode(NdrBuffer _src)
/* 487:    */       throws NdrException
/* 488:    */     {
/* 489:422 */       _src.align(4);
/* 490:423 */       this.num_sids = _src.dec_ndr_long();
/* 491:424 */       int _sidsp = _src.dec_ndr_long();
/* 492:426 */       if (_sidsp != 0)
/* 493:    */       {
/* 494:427 */         _src = _src.deferred;
/* 495:428 */         int _sidss = _src.dec_ndr_long();
/* 496:429 */         int _sidsi = _src.index;
/* 497:430 */         _src.advance(4 * _sidss);
/* 498:432 */         if (this.sids == null)
/* 499:    */         {
/* 500:433 */           if ((_sidss < 0) || (_sidss > 65535)) {
/* 501:433 */             throw new NdrException("invalid array conformance");
/* 502:    */           }
/* 503:434 */           this.sids = new lsarpc.LsarSidPtr[_sidss];
/* 504:    */         }
/* 505:436 */         _src = _src.derive(_sidsi);
/* 506:437 */         for (int _i = 0; _i < _sidss; _i++)
/* 507:    */         {
/* 508:438 */           if (this.sids[_i] == null) {
/* 509:439 */             this.sids[_i] = new lsarpc.LsarSidPtr();
/* 510:    */           }
/* 511:441 */           this.sids[_i].decode(_src);
/* 512:    */         }
/* 513:    */       }
/* 514:    */     }
/* 515:    */   }
/* 516:    */   
/* 517:    */   public static class LsarTranslatedSid
/* 518:    */     extends NdrObject
/* 519:    */   {
/* 520:    */     public int sid_type;
/* 521:    */     public int rid;
/* 522:    */     public int sid_index;
/* 523:    */     
/* 524:    */     public void encode(NdrBuffer _dst)
/* 525:    */       throws NdrException
/* 526:    */     {
/* 527:463 */       _dst.align(4);
/* 528:464 */       _dst.enc_ndr_short(this.sid_type);
/* 529:465 */       _dst.enc_ndr_long(this.rid);
/* 530:466 */       _dst.enc_ndr_long(this.sid_index);
/* 531:    */     }
/* 532:    */     
/* 533:    */     public void decode(NdrBuffer _src)
/* 534:    */       throws NdrException
/* 535:    */     {
/* 536:470 */       _src.align(4);
/* 537:471 */       this.sid_type = _src.dec_ndr_short();
/* 538:472 */       this.rid = _src.dec_ndr_long();
/* 539:473 */       this.sid_index = _src.dec_ndr_long();
/* 540:    */     }
/* 541:    */   }
/* 542:    */   
/* 543:    */   public static class LsarTransSidArray
/* 544:    */     extends NdrObject
/* 545:    */   {
/* 546:    */     public int count;
/* 547:    */     public lsarpc.LsarTranslatedSid[] sids;
/* 548:    */     
/* 549:    */     public void encode(NdrBuffer _dst)
/* 550:    */       throws NdrException
/* 551:    */     {
/* 552:483 */       _dst.align(4);
/* 553:484 */       _dst.enc_ndr_long(this.count);
/* 554:485 */       _dst.enc_ndr_referent(this.sids, 1);
/* 555:487 */       if (this.sids != null)
/* 556:    */       {
/* 557:488 */         _dst = _dst.deferred;
/* 558:489 */         int _sidss = this.count;
/* 559:490 */         _dst.enc_ndr_long(_sidss);
/* 560:491 */         int _sidsi = _dst.index;
/* 561:492 */         _dst.advance(12 * _sidss);
/* 562:    */         
/* 563:494 */         _dst = _dst.derive(_sidsi);
/* 564:495 */         for (int _i = 0; _i < _sidss; _i++) {
/* 565:496 */           this.sids[_i].encode(_dst);
/* 566:    */         }
/* 567:    */       }
/* 568:    */     }
/* 569:    */     
/* 570:    */     public void decode(NdrBuffer _src)
/* 571:    */       throws NdrException
/* 572:    */     {
/* 573:501 */       _src.align(4);
/* 574:502 */       this.count = _src.dec_ndr_long();
/* 575:503 */       int _sidsp = _src.dec_ndr_long();
/* 576:505 */       if (_sidsp != 0)
/* 577:    */       {
/* 578:506 */         _src = _src.deferred;
/* 579:507 */         int _sidss = _src.dec_ndr_long();
/* 580:508 */         int _sidsi = _src.index;
/* 581:509 */         _src.advance(12 * _sidss);
/* 582:511 */         if (this.sids == null)
/* 583:    */         {
/* 584:512 */           if ((_sidss < 0) || (_sidss > 65535)) {
/* 585:512 */             throw new NdrException("invalid array conformance");
/* 586:    */           }
/* 587:513 */           this.sids = new lsarpc.LsarTranslatedSid[_sidss];
/* 588:    */         }
/* 589:515 */         _src = _src.derive(_sidsi);
/* 590:516 */         for (int _i = 0; _i < _sidss; _i++)
/* 591:    */         {
/* 592:517 */           if (this.sids[_i] == null) {
/* 593:518 */             this.sids[_i] = new lsarpc.LsarTranslatedSid();
/* 594:    */           }
/* 595:520 */           this.sids[_i].decode(_src);
/* 596:    */         }
/* 597:    */       }
/* 598:    */     }
/* 599:    */   }
/* 600:    */   
/* 601:    */   public static class LsarTrustInformation
/* 602:    */     extends NdrObject
/* 603:    */   {
/* 604:    */     public rpc.unicode_string name;
/* 605:    */     public rpc.sid_t sid;
/* 606:    */     
/* 607:    */     public void encode(NdrBuffer _dst)
/* 608:    */       throws NdrException
/* 609:    */     {
/* 610:531 */       _dst.align(4);
/* 611:532 */       _dst.enc_ndr_short(this.name.length);
/* 612:533 */       _dst.enc_ndr_short(this.name.maximum_length);
/* 613:534 */       _dst.enc_ndr_referent(this.name.buffer, 1);
/* 614:535 */       _dst.enc_ndr_referent(this.sid, 1);
/* 615:537 */       if (this.name.buffer != null)
/* 616:    */       {
/* 617:538 */         _dst = _dst.deferred;
/* 618:539 */         int _name_bufferl = this.name.length / 2;
/* 619:540 */         int _name_buffers = this.name.maximum_length / 2;
/* 620:541 */         _dst.enc_ndr_long(_name_buffers);
/* 621:542 */         _dst.enc_ndr_long(0);
/* 622:543 */         _dst.enc_ndr_long(_name_bufferl);
/* 623:544 */         int _name_bufferi = _dst.index;
/* 624:545 */         _dst.advance(2 * _name_bufferl);
/* 625:    */         
/* 626:547 */         _dst = _dst.derive(_name_bufferi);
/* 627:548 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 628:549 */           _dst.enc_ndr_short(this.name.buffer[_i]);
/* 629:    */         }
/* 630:    */       }
/* 631:552 */       if (this.sid != null)
/* 632:    */       {
/* 633:553 */         _dst = _dst.deferred;
/* 634:554 */         this.sid.encode(_dst);
/* 635:    */       }
/* 636:    */     }
/* 637:    */     
/* 638:    */     public void decode(NdrBuffer _src)
/* 639:    */       throws NdrException
/* 640:    */     {
/* 641:559 */       _src.align(4);
/* 642:560 */       _src.align(4);
/* 643:561 */       if (this.name == null) {
/* 644:562 */         this.name = new rpc.unicode_string();
/* 645:    */       }
/* 646:564 */       this.name.length = ((short)_src.dec_ndr_short());
/* 647:565 */       this.name.maximum_length = ((short)_src.dec_ndr_short());
/* 648:566 */       int _name_bufferp = _src.dec_ndr_long();
/* 649:567 */       int _sidp = _src.dec_ndr_long();
/* 650:569 */       if (_name_bufferp != 0)
/* 651:    */       {
/* 652:570 */         _src = _src.deferred;
/* 653:571 */         int _name_buffers = _src.dec_ndr_long();
/* 654:572 */         _src.dec_ndr_long();
/* 655:573 */         int _name_bufferl = _src.dec_ndr_long();
/* 656:574 */         int _name_bufferi = _src.index;
/* 657:575 */         _src.advance(2 * _name_bufferl);
/* 658:577 */         if (this.name.buffer == null)
/* 659:    */         {
/* 660:578 */           if ((_name_buffers < 0) || (_name_buffers > 65535)) {
/* 661:578 */             throw new NdrException("invalid array conformance");
/* 662:    */           }
/* 663:579 */           this.name.buffer = new short[_name_buffers];
/* 664:    */         }
/* 665:581 */         _src = _src.derive(_name_bufferi);
/* 666:582 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 667:583 */           this.name.buffer[_i] = ((short)_src.dec_ndr_short());
/* 668:    */         }
/* 669:    */       }
/* 670:586 */       if (_sidp != 0)
/* 671:    */       {
/* 672:587 */         if (this.sid == null) {
/* 673:588 */           this.sid = new rpc.sid_t();
/* 674:    */         }
/* 675:590 */         _src = _src.deferred;
/* 676:591 */         this.sid.decode(_src);
/* 677:    */       }
/* 678:    */     }
/* 679:    */   }
/* 680:    */   
/* 681:    */   public static class LsarRefDomainList
/* 682:    */     extends NdrObject
/* 683:    */   {
/* 684:    */     public int count;
/* 685:    */     public lsarpc.LsarTrustInformation[] domains;
/* 686:    */     public int max_count;
/* 687:    */     
/* 688:    */     public void encode(NdrBuffer _dst)
/* 689:    */       throws NdrException
/* 690:    */     {
/* 691:603 */       _dst.align(4);
/* 692:604 */       _dst.enc_ndr_long(this.count);
/* 693:605 */       _dst.enc_ndr_referent(this.domains, 1);
/* 694:606 */       _dst.enc_ndr_long(this.max_count);
/* 695:608 */       if (this.domains != null)
/* 696:    */       {
/* 697:609 */         _dst = _dst.deferred;
/* 698:610 */         int _domainss = this.count;
/* 699:611 */         _dst.enc_ndr_long(_domainss);
/* 700:612 */         int _domainsi = _dst.index;
/* 701:613 */         _dst.advance(12 * _domainss);
/* 702:    */         
/* 703:615 */         _dst = _dst.derive(_domainsi);
/* 704:616 */         for (int _i = 0; _i < _domainss; _i++) {
/* 705:617 */           this.domains[_i].encode(_dst);
/* 706:    */         }
/* 707:    */       }
/* 708:    */     }
/* 709:    */     
/* 710:    */     public void decode(NdrBuffer _src)
/* 711:    */       throws NdrException
/* 712:    */     {
/* 713:622 */       _src.align(4);
/* 714:623 */       this.count = _src.dec_ndr_long();
/* 715:624 */       int _domainsp = _src.dec_ndr_long();
/* 716:625 */       this.max_count = _src.dec_ndr_long();
/* 717:627 */       if (_domainsp != 0)
/* 718:    */       {
/* 719:628 */         _src = _src.deferred;
/* 720:629 */         int _domainss = _src.dec_ndr_long();
/* 721:630 */         int _domainsi = _src.index;
/* 722:631 */         _src.advance(12 * _domainss);
/* 723:633 */         if (this.domains == null)
/* 724:    */         {
/* 725:634 */           if ((_domainss < 0) || (_domainss > 65535)) {
/* 726:634 */             throw new NdrException("invalid array conformance");
/* 727:    */           }
/* 728:635 */           this.domains = new lsarpc.LsarTrustInformation[_domainss];
/* 729:    */         }
/* 730:637 */         _src = _src.derive(_domainsi);
/* 731:638 */         for (int _i = 0; _i < _domainss; _i++)
/* 732:    */         {
/* 733:639 */           if (this.domains[_i] == null) {
/* 734:640 */             this.domains[_i] = new lsarpc.LsarTrustInformation();
/* 735:    */           }
/* 736:642 */           this.domains[_i].decode(_src);
/* 737:    */         }
/* 738:    */       }
/* 739:    */     }
/* 740:    */   }
/* 741:    */   
/* 742:    */   public static class LsarTranslatedName
/* 743:    */     extends NdrObject
/* 744:    */   {
/* 745:    */     public short sid_type;
/* 746:    */     public rpc.unicode_string name;
/* 747:    */     public int sid_index;
/* 748:    */     
/* 749:    */     public void encode(NdrBuffer _dst)
/* 750:    */       throws NdrException
/* 751:    */     {
/* 752:654 */       _dst.align(4);
/* 753:655 */       _dst.enc_ndr_short(this.sid_type);
/* 754:656 */       _dst.enc_ndr_short(this.name.length);
/* 755:657 */       _dst.enc_ndr_short(this.name.maximum_length);
/* 756:658 */       _dst.enc_ndr_referent(this.name.buffer, 1);
/* 757:659 */       _dst.enc_ndr_long(this.sid_index);
/* 758:661 */       if (this.name.buffer != null)
/* 759:    */       {
/* 760:662 */         _dst = _dst.deferred;
/* 761:663 */         int _name_bufferl = this.name.length / 2;
/* 762:664 */         int _name_buffers = this.name.maximum_length / 2;
/* 763:665 */         _dst.enc_ndr_long(_name_buffers);
/* 764:666 */         _dst.enc_ndr_long(0);
/* 765:667 */         _dst.enc_ndr_long(_name_bufferl);
/* 766:668 */         int _name_bufferi = _dst.index;
/* 767:669 */         _dst.advance(2 * _name_bufferl);
/* 768:    */         
/* 769:671 */         _dst = _dst.derive(_name_bufferi);
/* 770:672 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 771:673 */           _dst.enc_ndr_short(this.name.buffer[_i]);
/* 772:    */         }
/* 773:    */       }
/* 774:    */     }
/* 775:    */     
/* 776:    */     public void decode(NdrBuffer _src)
/* 777:    */       throws NdrException
/* 778:    */     {
/* 779:678 */       _src.align(4);
/* 780:679 */       this.sid_type = ((short)_src.dec_ndr_short());
/* 781:680 */       _src.align(4);
/* 782:681 */       if (this.name == null) {
/* 783:682 */         this.name = new rpc.unicode_string();
/* 784:    */       }
/* 785:684 */       this.name.length = ((short)_src.dec_ndr_short());
/* 786:685 */       this.name.maximum_length = ((short)_src.dec_ndr_short());
/* 787:686 */       int _name_bufferp = _src.dec_ndr_long();
/* 788:687 */       this.sid_index = _src.dec_ndr_long();
/* 789:689 */       if (_name_bufferp != 0)
/* 790:    */       {
/* 791:690 */         _src = _src.deferred;
/* 792:691 */         int _name_buffers = _src.dec_ndr_long();
/* 793:692 */         _src.dec_ndr_long();
/* 794:693 */         int _name_bufferl = _src.dec_ndr_long();
/* 795:694 */         int _name_bufferi = _src.index;
/* 796:695 */         _src.advance(2 * _name_bufferl);
/* 797:697 */         if (this.name.buffer == null)
/* 798:    */         {
/* 799:698 */           if ((_name_buffers < 0) || (_name_buffers > 65535)) {
/* 800:698 */             throw new NdrException("invalid array conformance");
/* 801:    */           }
/* 802:699 */           this.name.buffer = new short[_name_buffers];
/* 803:    */         }
/* 804:701 */         _src = _src.derive(_name_bufferi);
/* 805:702 */         for (int _i = 0; _i < _name_bufferl; _i++) {
/* 806:703 */           this.name.buffer[_i] = ((short)_src.dec_ndr_short());
/* 807:    */         }
/* 808:    */       }
/* 809:    */     }
/* 810:    */   }
/* 811:    */   
/* 812:    */   public static class LsarTransNameArray
/* 813:    */     extends NdrObject
/* 814:    */   {
/* 815:    */     public int count;
/* 816:    */     public lsarpc.LsarTranslatedName[] names;
/* 817:    */     
/* 818:    */     public void encode(NdrBuffer _dst)
/* 819:    */       throws NdrException
/* 820:    */     {
/* 821:714 */       _dst.align(4);
/* 822:715 */       _dst.enc_ndr_long(this.count);
/* 823:716 */       _dst.enc_ndr_referent(this.names, 1);
/* 824:718 */       if (this.names != null)
/* 825:    */       {
/* 826:719 */         _dst = _dst.deferred;
/* 827:720 */         int _namess = this.count;
/* 828:721 */         _dst.enc_ndr_long(_namess);
/* 829:722 */         int _namesi = _dst.index;
/* 830:723 */         _dst.advance(16 * _namess);
/* 831:    */         
/* 832:725 */         _dst = _dst.derive(_namesi);
/* 833:726 */         for (int _i = 0; _i < _namess; _i++) {
/* 834:727 */           this.names[_i].encode(_dst);
/* 835:    */         }
/* 836:    */       }
/* 837:    */     }
/* 838:    */     
/* 839:    */     public void decode(NdrBuffer _src)
/* 840:    */       throws NdrException
/* 841:    */     {
/* 842:732 */       _src.align(4);
/* 843:733 */       this.count = _src.dec_ndr_long();
/* 844:734 */       int _namesp = _src.dec_ndr_long();
/* 845:736 */       if (_namesp != 0)
/* 846:    */       {
/* 847:737 */         _src = _src.deferred;
/* 848:738 */         int _namess = _src.dec_ndr_long();
/* 849:739 */         int _namesi = _src.index;
/* 850:740 */         _src.advance(16 * _namess);
/* 851:742 */         if (this.names == null)
/* 852:    */         {
/* 853:743 */           if ((_namess < 0) || (_namess > 65535)) {
/* 854:743 */             throw new NdrException("invalid array conformance");
/* 855:    */           }
/* 856:744 */           this.names = new lsarpc.LsarTranslatedName[_namess];
/* 857:    */         }
/* 858:746 */         _src = _src.derive(_namesi);
/* 859:747 */         for (int _i = 0; _i < _namess; _i++)
/* 860:    */         {
/* 861:748 */           if (this.names[_i] == null) {
/* 862:749 */             this.names[_i] = new lsarpc.LsarTranslatedName();
/* 863:    */           }
/* 864:751 */           this.names[_i].decode(_src);
/* 865:    */         }
/* 866:    */       }
/* 867:    */     }
/* 868:    */   }
/* 869:    */   
/* 870:    */   public static class LsarClose
/* 871:    */     extends DcerpcMessage
/* 872:    */   {
/* 873:    */     public int retval;
/* 874:    */     public rpc.policy_handle handle;
/* 875:    */     
/* 876:    */     public int getOpnum()
/* 877:    */     {
/* 878:758 */       return 0;
/* 879:    */     }
/* 880:    */     
/* 881:    */     public LsarClose(rpc.policy_handle handle)
/* 882:    */     {
/* 883:764 */       this.handle = handle;
/* 884:    */     }
/* 885:    */     
/* 886:    */     public void encode_in(NdrBuffer _dst)
/* 887:    */       throws NdrException
/* 888:    */     {
/* 889:768 */       this.handle.encode(_dst);
/* 890:    */     }
/* 891:    */     
/* 892:    */     public void decode_out(NdrBuffer _src)
/* 893:    */       throws NdrException
/* 894:    */     {
/* 895:771 */       this.handle.decode(_src);
/* 896:772 */       this.retval = _src.dec_ndr_long();
/* 897:    */     }
/* 898:    */   }
/* 899:    */   
/* 900:    */   public static class LsarQueryInformationPolicy
/* 901:    */     extends DcerpcMessage
/* 902:    */   {
/* 903:    */     public int retval;
/* 904:    */     public rpc.policy_handle handle;
/* 905:    */     public short level;
/* 906:    */     public NdrObject info;
/* 907:    */     
/* 908:    */     public int getOpnum()
/* 909:    */     {
/* 910:777 */       return 7;
/* 911:    */     }
/* 912:    */     
/* 913:    */     public LsarQueryInformationPolicy(rpc.policy_handle handle, short level, NdrObject info)
/* 914:    */     {
/* 915:785 */       this.handle = handle;
/* 916:786 */       this.level = level;
/* 917:787 */       this.info = info;
/* 918:    */     }
/* 919:    */     
/* 920:    */     public void encode_in(NdrBuffer _dst)
/* 921:    */       throws NdrException
/* 922:    */     {
/* 923:791 */       this.handle.encode(_dst);
/* 924:792 */       _dst.enc_ndr_short(this.level);
/* 925:    */     }
/* 926:    */     
/* 927:    */     public void decode_out(NdrBuffer _src)
/* 928:    */       throws NdrException
/* 929:    */     {
/* 930:795 */       int _infop = _src.dec_ndr_long();
/* 931:796 */       if (_infop != 0)
/* 932:    */       {
/* 933:797 */         _src.dec_ndr_short();
/* 934:798 */         this.info.decode(_src);
/* 935:    */       }
/* 936:801 */       this.retval = _src.dec_ndr_long();
/* 937:    */     }
/* 938:    */   }
/* 939:    */   
/* 940:    */   public static class LsarLookupSids
/* 941:    */     extends DcerpcMessage
/* 942:    */   {
/* 943:    */     public int retval;
/* 944:    */     public rpc.policy_handle handle;
/* 945:    */     public lsarpc.LsarSidArray sids;
/* 946:    */     public lsarpc.LsarRefDomainList domains;
/* 947:    */     public lsarpc.LsarTransNameArray names;
/* 948:    */     public short level;
/* 949:    */     public int count;
/* 950:    */     
/* 951:    */     public int getOpnum()
/* 952:    */     {
/* 953:806 */       return 15;
/* 954:    */     }
/* 955:    */     
/* 956:    */     public LsarLookupSids(rpc.policy_handle handle, lsarpc.LsarSidArray sids, lsarpc.LsarRefDomainList domains, lsarpc.LsarTransNameArray names, short level, int count)
/* 957:    */     {
/* 958:822 */       this.handle = handle;
/* 959:823 */       this.sids = sids;
/* 960:824 */       this.domains = domains;
/* 961:825 */       this.names = names;
/* 962:826 */       this.level = level;
/* 963:827 */       this.count = count;
/* 964:    */     }
/* 965:    */     
/* 966:    */     public void encode_in(NdrBuffer _dst)
/* 967:    */       throws NdrException
/* 968:    */     {
/* 969:831 */       this.handle.encode(_dst);
/* 970:832 */       this.sids.encode(_dst);
/* 971:833 */       this.names.encode(_dst);
/* 972:834 */       _dst.enc_ndr_short(this.level);
/* 973:835 */       _dst.enc_ndr_long(this.count);
/* 974:    */     }
/* 975:    */     
/* 976:    */     public void decode_out(NdrBuffer _src)
/* 977:    */       throws NdrException
/* 978:    */     {
/* 979:838 */       int _domainsp = _src.dec_ndr_long();
/* 980:839 */       if (_domainsp != 0)
/* 981:    */       {
/* 982:840 */         if (this.domains == null) {
/* 983:841 */           this.domains = new lsarpc.LsarRefDomainList();
/* 984:    */         }
/* 985:843 */         this.domains.decode(_src);
/* 986:    */       }
/* 987:846 */       this.names.decode(_src);
/* 988:847 */       this.count = _src.dec_ndr_long();
/* 989:848 */       this.retval = _src.dec_ndr_long();
/* 990:    */     }
/* 991:    */   }
/* 992:    */   
/* 993:    */   public static class LsarOpenPolicy2
/* 994:    */     extends DcerpcMessage
/* 995:    */   {
/* 996:    */     public int retval;
/* 997:    */     public String system_name;
/* 998:    */     public lsarpc.LsarObjectAttributes object_attributes;
/* 999:    */     public int desired_access;
/* :00:    */     public rpc.policy_handle policy_handle;
/* :01:    */     
/* :02:    */     public int getOpnum()
/* :03:    */     {
/* :04:853 */       return 44;
/* :05:    */     }
/* :06:    */     
/* :07:    */     public LsarOpenPolicy2(String system_name, lsarpc.LsarObjectAttributes object_attributes, int desired_access, rpc.policy_handle policy_handle)
/* :08:    */     {
/* :09:865 */       this.system_name = system_name;
/* :10:866 */       this.object_attributes = object_attributes;
/* :11:867 */       this.desired_access = desired_access;
/* :12:868 */       this.policy_handle = policy_handle;
/* :13:    */     }
/* :14:    */     
/* :15:    */     public void encode_in(NdrBuffer _dst)
/* :16:    */       throws NdrException
/* :17:    */     {
/* :18:872 */       _dst.enc_ndr_referent(this.system_name, 1);
/* :19:873 */       if (this.system_name != null) {
/* :20:874 */         _dst.enc_ndr_string(this.system_name);
/* :21:    */       }
/* :22:877 */       this.object_attributes.encode(_dst);
/* :23:878 */       _dst.enc_ndr_long(this.desired_access);
/* :24:    */     }
/* :25:    */     
/* :26:    */     public void decode_out(NdrBuffer _src)
/* :27:    */       throws NdrException
/* :28:    */     {
/* :29:881 */       this.policy_handle.decode(_src);
/* :30:882 */       this.retval = _src.dec_ndr_long();
/* :31:    */     }
/* :32:    */   }
/* :33:    */   
/* :34:    */   public static class LsarQueryInformationPolicy2
/* :35:    */     extends DcerpcMessage
/* :36:    */   {
/* :37:    */     public int retval;
/* :38:    */     public rpc.policy_handle handle;
/* :39:    */     public short level;
/* :40:    */     public NdrObject info;
/* :41:    */     
/* :42:    */     public int getOpnum()
/* :43:    */     {
/* :44:887 */       return 46;
/* :45:    */     }
/* :46:    */     
/* :47:    */     public LsarQueryInformationPolicy2(rpc.policy_handle handle, short level, NdrObject info)
/* :48:    */     {
/* :49:895 */       this.handle = handle;
/* :50:896 */       this.level = level;
/* :51:897 */       this.info = info;
/* :52:    */     }
/* :53:    */     
/* :54:    */     public void encode_in(NdrBuffer _dst)
/* :55:    */       throws NdrException
/* :56:    */     {
/* :57:901 */       this.handle.encode(_dst);
/* :58:902 */       _dst.enc_ndr_short(this.level);
/* :59:    */     }
/* :60:    */     
/* :61:    */     public void decode_out(NdrBuffer _src)
/* :62:    */       throws NdrException
/* :63:    */     {
/* :64:905 */       int _infop = _src.dec_ndr_long();
/* :65:906 */       if (_infop != 0)
/* :66:    */       {
/* :67:907 */         _src.dec_ndr_short();
/* :68:908 */         this.info.decode(_src);
/* :69:    */       }
/* :70:911 */       this.retval = _src.dec_ndr_long();
/* :71:    */     }
/* :72:    */   }
/* :73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.lsarpc
 * JD-Core Version:    0.7.0.1
 */