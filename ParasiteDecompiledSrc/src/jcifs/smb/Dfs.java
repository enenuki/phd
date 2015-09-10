/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import jcifs.Config;
/*   8:    */ import jcifs.UniAddress;
/*   9:    */ import jcifs.util.LogStream;
/*  10:    */ 
/*  11:    */ public class Dfs
/*  12:    */ {
/*  13:    */   static class CacheEntry
/*  14:    */   {
/*  15:    */     long expiration;
/*  16:    */     HashMap map;
/*  17:    */     
/*  18:    */     CacheEntry(long ttl)
/*  19:    */     {
/*  20: 35 */       if (ttl == 0L) {
/*  21: 36 */         ttl = Dfs.TTL;
/*  22:    */       }
/*  23: 37 */       this.expiration = (System.currentTimeMillis() + ttl * 1000L);
/*  24: 38 */       this.map = new HashMap();
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28: 42 */   static LogStream log = ;
/*  29: 43 */   static final boolean strictView = Config.getBoolean("jcifs.smb.client.dfs.strictView", false);
/*  30: 44 */   static final long TTL = Config.getLong("jcifs.smb.client.dfs.ttl", 300L);
/*  31: 45 */   static final boolean DISABLED = Config.getBoolean("jcifs.smb.client.dfs.disabled", false);
/*  32: 47 */   protected static CacheEntry FALSE_ENTRY = new CacheEntry(0L);
/*  33: 49 */   protected CacheEntry _domains = null;
/*  34: 50 */   protected CacheEntry referrals = null;
/*  35:    */   
/*  36:    */   public HashMap getTrustedDomains(NtlmPasswordAuthentication auth)
/*  37:    */     throws SmbAuthException
/*  38:    */   {
/*  39: 53 */     if ((DISABLED) || (auth.domain == "?")) {
/*  40: 54 */       return null;
/*  41:    */     }
/*  42: 56 */     if ((this._domains != null) && (System.currentTimeMillis() > this._domains.expiration)) {
/*  43: 57 */       this._domains = null;
/*  44:    */     }
/*  45: 59 */     if (this._domains != null) {
/*  46: 60 */       return this._domains.map;
/*  47:    */     }
/*  48:    */     try
/*  49:    */     {
/*  50: 62 */       UniAddress addr = UniAddress.getByName(auth.domain, true);
/*  51: 63 */       SmbTransport trans = SmbTransport.getSmbTransport(addr, 0);
/*  52: 64 */       CacheEntry entry = new CacheEntry(TTL * 10L);
/*  53:    */       
/*  54: 66 */       DfsReferral dr = trans.getDfsReferrals(auth, "", 0);
/*  55: 67 */       if (dr != null)
/*  56:    */       {
/*  57: 68 */         DfsReferral start = dr;
/*  58:    */         do
/*  59:    */         {
/*  60: 70 */           String domain = dr.server.toLowerCase();
/*  61: 71 */           entry.map.put(domain, new HashMap());
/*  62: 72 */           dr = dr.next;
/*  63: 73 */         } while (dr != start);
/*  64: 75 */         this._domains = entry;
/*  65: 76 */         return this._domains.map;
/*  66:    */       }
/*  67:    */     }
/*  68:    */     catch (IOException ioe)
/*  69:    */     {
/*  70: 79 */       if (LogStream.level >= 3) {
/*  71: 80 */         ioe.printStackTrace(log);
/*  72:    */       }
/*  73: 81 */       if ((strictView) && ((ioe instanceof SmbAuthException))) {
/*  74: 82 */         throw ((SmbAuthException)ioe);
/*  75:    */       }
/*  76:    */     }
/*  77: 85 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isTrustedDomain(String domain, NtlmPasswordAuthentication auth)
/*  81:    */     throws SmbAuthException
/*  82:    */   {
/*  83: 90 */     HashMap domains = getTrustedDomains(auth);
/*  84: 91 */     if (domains == null) {
/*  85: 92 */       return false;
/*  86:    */     }
/*  87: 93 */     domain = domain.toLowerCase();
/*  88: 94 */     return domains.get(domain) != null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public SmbTransport getDc(String domain, NtlmPasswordAuthentication auth)
/*  92:    */     throws SmbAuthException
/*  93:    */   {
/*  94: 98 */     if (DISABLED) {
/*  95: 99 */       return null;
/*  96:    */     }
/*  97:    */     try
/*  98:    */     {
/*  99:102 */       UniAddress addr = UniAddress.getByName(domain, true);
/* 100:103 */       SmbTransport trans = SmbTransport.getSmbTransport(addr, 0);
/* 101:104 */       DfsReferral dr = trans.getDfsReferrals(auth, "\\" + domain, 1);
/* 102:105 */       if (dr != null)
/* 103:    */       {
/* 104:106 */         DfsReferral start = dr;
/* 105:107 */         IOException e = null;
/* 106:    */         do
/* 107:    */         {
/* 108:    */           try
/* 109:    */           {
/* 110:111 */             addr = UniAddress.getByName(dr.server);
/* 111:112 */             return SmbTransport.getSmbTransport(addr, 0);
/* 112:    */           }
/* 113:    */           catch (IOException ioe)
/* 114:    */           {
/* 115:114 */             e = ioe;
/* 116:    */             
/* 117:    */ 
/* 118:117 */             dr = dr.next;
/* 119:    */           }
/* 120:118 */         } while (dr != start);
/* 121:120 */         throw e;
/* 122:    */       }
/* 123:    */     }
/* 124:    */     catch (IOException ioe)
/* 125:    */     {
/* 126:123 */       if (LogStream.level >= 3) {
/* 127:124 */         ioe.printStackTrace(log);
/* 128:    */       }
/* 129:125 */       if ((strictView) && ((ioe instanceof SmbAuthException))) {
/* 130:126 */         throw ((SmbAuthException)ioe);
/* 131:    */       }
/* 132:    */     }
/* 133:129 */     return null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DfsReferral getReferral(SmbTransport trans, String domain, String root, String path, NtlmPasswordAuthentication auth)
/* 137:    */     throws SmbAuthException
/* 138:    */   {
/* 139:136 */     if (DISABLED) {
/* 140:137 */       return null;
/* 141:    */     }
/* 142:    */     try
/* 143:    */     {
/* 144:140 */       String p = "\\" + domain + "\\" + root;
/* 145:141 */       if (path != null) {
/* 146:142 */         p = p + path;
/* 147:    */       }
/* 148:143 */       DfsReferral dr = trans.getDfsReferrals(auth, p, 0);
/* 149:144 */       if (dr != null) {
/* 150:145 */         return dr;
/* 151:    */       }
/* 152:    */     }
/* 153:    */     catch (IOException ioe)
/* 154:    */     {
/* 155:147 */       if (LogStream.level >= 4) {
/* 156:148 */         ioe.printStackTrace(log);
/* 157:    */       }
/* 158:149 */       if ((strictView) && ((ioe instanceof SmbAuthException))) {
/* 159:150 */         throw ((SmbAuthException)ioe);
/* 160:    */       }
/* 161:    */     }
/* 162:153 */     return null;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public synchronized DfsReferral resolve(String domain, String root, String path, NtlmPasswordAuthentication auth)
/* 166:    */     throws SmbAuthException
/* 167:    */   {
/* 168:159 */     DfsReferral dr = null;
/* 169:160 */     long now = System.currentTimeMillis();
/* 170:162 */     if ((DISABLED) || (root.equals("IPC$"))) {
/* 171:163 */       return null;
/* 172:    */     }
/* 173:167 */     HashMap domains = getTrustedDomains(auth);
/* 174:168 */     if (domains != null)
/* 175:    */     {
/* 176:169 */       domain = domain.toLowerCase();
/* 177:    */       
/* 178:    */ 
/* 179:172 */       HashMap roots = (HashMap)domains.get(domain);
/* 180:173 */       if (roots != null)
/* 181:    */       {
/* 182:174 */         SmbTransport trans = null;
/* 183:    */         
/* 184:176 */         root = root.toLowerCase();
/* 185:    */         
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:181 */         CacheEntry links = (CacheEntry)roots.get(root);
/* 190:182 */         if ((links != null) && (now > links.expiration))
/* 191:    */         {
/* 192:183 */           roots.remove(root);
/* 193:184 */           links = null;
/* 194:    */         }
/* 195:187 */         if (links == null)
/* 196:    */         {
/* 197:188 */           if ((trans = getDc(domain, auth)) == null) {
/* 198:189 */             return null;
/* 199:    */           }
/* 200:191 */           dr = getReferral(trans, domain, root, path, auth);
/* 201:192 */           if (dr != null)
/* 202:    */           {
/* 203:193 */             int len = 1 + domain.length() + 1 + root.length();
/* 204:    */             
/* 205:195 */             links = new CacheEntry(0L);
/* 206:    */             
/* 207:197 */             DfsReferral tmp = dr;
/* 208:    */             do
/* 209:    */             {
/* 210:199 */               if (path == null)
/* 211:    */               {
/* 212:206 */                 tmp.map = links.map;
/* 213:207 */                 tmp.key = "\\";
/* 214:    */               }
/* 215:209 */               tmp.pathConsumed -= len;
/* 216:210 */               tmp = tmp.next;
/* 217:211 */             } while (tmp != dr);
/* 218:213 */             if (dr.key != null) {
/* 219:214 */               links.map.put(dr.key, dr);
/* 220:    */             }
/* 221:216 */             roots.put(root, links);
/* 222:    */           }
/* 223:217 */           else if (path == null)
/* 224:    */           {
/* 225:218 */             roots.put(root, FALSE_ENTRY);
/* 226:    */           }
/* 227:    */         }
/* 228:220 */         else if (links == FALSE_ENTRY)
/* 229:    */         {
/* 230:221 */           links = null;
/* 231:    */         }
/* 232:224 */         if (links != null)
/* 233:    */         {
/* 234:225 */           String link = "\\";
/* 235:    */           
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:230 */           dr = (DfsReferral)links.map.get(link);
/* 240:231 */           if ((dr != null) && (now > dr.expiration))
/* 241:    */           {
/* 242:232 */             links.map.remove(link);
/* 243:233 */             dr = null;
/* 244:    */           }
/* 245:236 */           if (dr == null)
/* 246:    */           {
/* 247:237 */             if ((trans == null) && 
/* 248:238 */               ((trans = getDc(domain, auth)) == null)) {
/* 249:239 */               return null;
/* 250:    */             }
/* 251:240 */             dr = getReferral(trans, domain, root, path, auth);
/* 252:241 */             if (dr != null)
/* 253:    */             {
/* 254:242 */               dr.pathConsumed -= 1 + domain.length() + 1 + root.length();
/* 255:243 */               dr.link = link;
/* 256:244 */               links.map.put(link, dr);
/* 257:    */             }
/* 258:    */           }
/* 259:    */         }
/* 260:    */       }
/* 261:    */     }
/* 262:251 */     if ((dr == null) && (path != null))
/* 263:    */     {
/* 264:255 */       if ((this.referrals != null) && (now > this.referrals.expiration)) {
/* 265:256 */         this.referrals = null;
/* 266:    */       }
/* 267:258 */       if (this.referrals == null) {
/* 268:259 */         this.referrals = new CacheEntry(0L);
/* 269:    */       }
/* 270:261 */       String key = "\\" + domain + "\\" + root;
/* 271:262 */       if (!path.equals("\\")) {
/* 272:263 */         key = key + path;
/* 273:    */       }
/* 274:264 */       key = key.toLowerCase();
/* 275:    */       
/* 276:266 */       Iterator iter = this.referrals.map.keySet().iterator();
/* 277:267 */       while (iter.hasNext())
/* 278:    */       {
/* 279:268 */         String _key = (String)iter.next();
/* 280:269 */         int _klen = _key.length();
/* 281:270 */         boolean match = false;
/* 282:272 */         if (_klen == key.length()) {
/* 283:273 */           match = _key.equals(key);
/* 284:274 */         } else if (_klen < key.length()) {
/* 285:275 */           match = (_key.regionMatches(0, key, 0, _klen)) && (key.charAt(_klen) == '\\');
/* 286:    */         }
/* 287:278 */         if (match) {
/* 288:279 */           dr = (DfsReferral)this.referrals.map.get(_key);
/* 289:    */         }
/* 290:    */       }
/* 291:    */     }
/* 292:283 */     return dr;
/* 293:    */   }
/* 294:    */   
/* 295:    */   synchronized void insert(String path, DfsReferral dr)
/* 296:    */   {
/* 297:289 */     if (DISABLED) {
/* 298:290 */       return;
/* 299:    */     }
/* 300:292 */     int s1 = path.indexOf('\\', 1);
/* 301:293 */     int s2 = path.indexOf('\\', s1 + 1);
/* 302:294 */     String server = path.substring(1, s1);
/* 303:295 */     String share = path.substring(s1 + 1, s2);
/* 304:    */     
/* 305:297 */     String key = path.substring(0, dr.pathConsumed).toLowerCase();
/* 306:    */     
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:305 */     int ki = key.length();
/* 314:306 */     while ((ki > 1) && (key.charAt(ki - 1) == '\\')) {
/* 315:307 */       ki--;
/* 316:    */     }
/* 317:309 */     if (ki < key.length()) {
/* 318:310 */       key = key.substring(0, ki);
/* 319:    */     }
/* 320:317 */     dr.pathConsumed -= 1 + server.length() + 1 + share.length();
/* 321:319 */     if ((this.referrals != null) && (System.currentTimeMillis() + 10000L > this.referrals.expiration)) {
/* 322:320 */       this.referrals = null;
/* 323:    */     }
/* 324:322 */     if (this.referrals == null) {
/* 325:323 */       this.referrals = new CacheEntry(0L);
/* 326:    */     }
/* 327:325 */     this.referrals.map.put(key, dr);
/* 328:    */   }
/* 329:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Dfs
 * JD-Core Version:    0.7.0.1
 */