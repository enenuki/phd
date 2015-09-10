/*   1:    */ package org.apache.http.impl.auth;
/*   2:    */ 
/*   3:    */ import java.security.MessageDigest;
/*   4:    */ import java.security.Principal;
/*   5:    */ import java.security.SecureRandom;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Formatter;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Locale;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.StringTokenizer;
/*  12:    */ import org.apache.http.Header;
/*  13:    */ import org.apache.http.HttpRequest;
/*  14:    */ import org.apache.http.RequestLine;
/*  15:    */ import org.apache.http.annotation.NotThreadSafe;
/*  16:    */ import org.apache.http.auth.AuthenticationException;
/*  17:    */ import org.apache.http.auth.Credentials;
/*  18:    */ import org.apache.http.auth.MalformedChallengeException;
/*  19:    */ import org.apache.http.auth.params.AuthParams;
/*  20:    */ import org.apache.http.message.BasicHeaderValueFormatter;
/*  21:    */ import org.apache.http.message.BasicNameValuePair;
/*  22:    */ import org.apache.http.message.BufferedHeader;
/*  23:    */ import org.apache.http.util.CharArrayBuffer;
/*  24:    */ import org.apache.http.util.EncodingUtils;
/*  25:    */ 
/*  26:    */ @NotThreadSafe
/*  27:    */ public class DigestScheme
/*  28:    */   extends RFC2617Scheme
/*  29:    */ {
/*  30: 86 */   private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*  31:    */   private boolean complete;
/*  32:    */   private static final int QOP_UNKNOWN = -1;
/*  33:    */   private static final int QOP_MISSING = 0;
/*  34:    */   private static final int QOP_AUTH_INT = 1;
/*  35:    */   private static final int QOP_AUTH = 2;
/*  36:    */   private String lastNonce;
/*  37:    */   private long nounceCount;
/*  38:    */   private String cnonce;
/*  39:    */   private String a1;
/*  40:    */   private String a2;
/*  41:    */   
/*  42:    */   public DigestScheme()
/*  43:    */   {
/*  44:110 */     this.complete = false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void processChallenge(Header header)
/*  48:    */     throws MalformedChallengeException
/*  49:    */   {
/*  50:124 */     super.processChallenge(header);
/*  51:126 */     if (getParameter("realm") == null) {
/*  52:127 */       throw new MalformedChallengeException("missing realm in challenge");
/*  53:    */     }
/*  54:129 */     if (getParameter("nonce") == null) {
/*  55:130 */       throw new MalformedChallengeException("missing nonce in challenge");
/*  56:    */     }
/*  57:132 */     this.complete = true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isComplete()
/*  61:    */   {
/*  62:142 */     String s = getParameter("stale");
/*  63:143 */     if ("true".equalsIgnoreCase(s)) {
/*  64:144 */       return false;
/*  65:    */     }
/*  66:146 */     return this.complete;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getSchemeName()
/*  70:    */   {
/*  71:156 */     return "digest";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean isConnectionBased()
/*  75:    */   {
/*  76:165 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void overrideParamter(String name, String value)
/*  80:    */   {
/*  81:169 */     getParameters().put(name, value);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Header authenticate(Credentials credentials, HttpRequest request)
/*  85:    */     throws AuthenticationException
/*  86:    */   {
/*  87:190 */     if (credentials == null) {
/*  88:191 */       throw new IllegalArgumentException("Credentials may not be null");
/*  89:    */     }
/*  90:193 */     if (request == null) {
/*  91:194 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  92:    */     }
/*  93:198 */     getParameters().put("methodname", request.getRequestLine().getMethod());
/*  94:199 */     getParameters().put("uri", request.getRequestLine().getUri());
/*  95:200 */     String charset = getParameter("charset");
/*  96:201 */     if (charset == null)
/*  97:    */     {
/*  98:202 */       charset = AuthParams.getCredentialCharset(request.getParams());
/*  99:203 */       getParameters().put("charset", charset);
/* 100:    */     }
/* 101:205 */     return createDigestHeader(credentials);
/* 102:    */   }
/* 103:    */   
/* 104:    */   private static MessageDigest createMessageDigest(String digAlg)
/* 105:    */     throws UnsupportedDigestAlgorithmException
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:211 */       return MessageDigest.getInstance(digAlg);
/* 110:    */     }
/* 111:    */     catch (Exception e)
/* 112:    */     {
/* 113:213 */       throw new UnsupportedDigestAlgorithmException("Unsupported algorithm in HTTP Digest authentication: " + digAlg);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   private Header createDigestHeader(Credentials credentials)
/* 118:    */     throws AuthenticationException
/* 119:    */   {
/* 120:228 */     String uri = getParameter("uri");
/* 121:229 */     String realm = getParameter("realm");
/* 122:230 */     String nonce = getParameter("nonce");
/* 123:231 */     String opaque = getParameter("opaque");
/* 124:232 */     String method = getParameter("methodname");
/* 125:233 */     String algorithm = getParameter("algorithm");
/* 126:234 */     if (uri == null) {
/* 127:235 */       throw new IllegalStateException("URI may not be null");
/* 128:    */     }
/* 129:237 */     if (realm == null) {
/* 130:238 */       throw new IllegalStateException("Realm may not be null");
/* 131:    */     }
/* 132:240 */     if (nonce == null) {
/* 133:241 */       throw new IllegalStateException("Nonce may not be null");
/* 134:    */     }
/* 135:245 */     int qop = -1;
/* 136:246 */     String qoplist = getParameter("qop");
/* 137:247 */     if (qoplist != null)
/* 138:    */     {
/* 139:248 */       StringTokenizer tok = new StringTokenizer(qoplist, ",");
/* 140:249 */       while (tok.hasMoreTokens())
/* 141:    */       {
/* 142:250 */         String variant = tok.nextToken().trim();
/* 143:251 */         if (variant.equals("auth"))
/* 144:    */         {
/* 145:252 */           qop = 2;
/* 146:253 */           break;
/* 147:    */         }
/* 148:    */       }
/* 149:    */     }
/* 150:    */     else
/* 151:    */     {
/* 152:257 */       qop = 0;
/* 153:    */     }
/* 154:260 */     if (qop == -1) {
/* 155:261 */       throw new AuthenticationException("None of the qop methods is supported: " + qoplist);
/* 156:    */     }
/* 157:265 */     if (algorithm == null) {
/* 158:266 */       algorithm = "MD5";
/* 159:    */     }
/* 160:269 */     String charset = getParameter("charset");
/* 161:270 */     if (charset == null) {
/* 162:271 */       charset = "ISO-8859-1";
/* 163:    */     }
/* 164:274 */     String digAlg = algorithm;
/* 165:275 */     if (digAlg.equalsIgnoreCase("MD5-sess")) {
/* 166:276 */       digAlg = "MD5";
/* 167:    */     }
/* 168:    */     MessageDigest digester;
/* 169:    */     try
/* 170:    */     {
/* 171:281 */       digester = createMessageDigest(digAlg);
/* 172:    */     }
/* 173:    */     catch (UnsupportedDigestAlgorithmException ex)
/* 174:    */     {
/* 175:283 */       throw new AuthenticationException("Unsuppported digest algorithm: " + digAlg);
/* 176:    */     }
/* 177:286 */     String uname = credentials.getUserPrincipal().getName();
/* 178:287 */     String pwd = credentials.getPassword();
/* 179:289 */     if (nonce.equals(this.lastNonce))
/* 180:    */     {
/* 181:290 */       this.nounceCount += 1L;
/* 182:    */     }
/* 183:    */     else
/* 184:    */     {
/* 185:292 */       this.nounceCount = 1L;
/* 186:293 */       this.cnonce = null;
/* 187:294 */       this.lastNonce = nonce;
/* 188:    */     }
/* 189:296 */     StringBuilder sb = new StringBuilder(256);
/* 190:297 */     Formatter formatter = new Formatter(sb, Locale.US);
/* 191:298 */     formatter.format("%08x", new Object[] { Long.valueOf(this.nounceCount) });
/* 192:299 */     String nc = sb.toString();
/* 193:301 */     if (this.cnonce == null) {
/* 194:302 */       this.cnonce = createCnonce();
/* 195:    */     }
/* 196:305 */     this.a1 = null;
/* 197:306 */     this.a2 = null;
/* 198:308 */     if (algorithm.equalsIgnoreCase("MD5-sess"))
/* 199:    */     {
/* 200:314 */       sb.setLength(0);
/* 201:315 */       sb.append(uname).append(':').append(realm).append(':').append(pwd);
/* 202:316 */       String checksum = encode(digester.digest(EncodingUtils.getBytes(sb.toString(), charset)));
/* 203:317 */       sb.setLength(0);
/* 204:318 */       sb.append(checksum).append(':').append(nonce).append(':').append(this.cnonce);
/* 205:319 */       this.a1 = sb.toString();
/* 206:    */     }
/* 207:    */     else
/* 208:    */     {
/* 209:322 */       sb.setLength(0);
/* 210:323 */       sb.append(uname).append(':').append(realm).append(':').append(pwd);
/* 211:324 */       this.a1 = sb.toString();
/* 212:    */     }
/* 213:327 */     String hasha1 = encode(digester.digest(EncodingUtils.getBytes(this.a1, charset)));
/* 214:329 */     if (qop == 2)
/* 215:    */     {
/* 216:331 */       this.a2 = (method + ':' + uri);
/* 217:    */     }
/* 218:    */     else
/* 219:    */     {
/* 220:332 */       if (qop == 1) {
/* 221:335 */         throw new AuthenticationException("qop-int method is not suppported");
/* 222:    */       }
/* 223:337 */       this.a2 = (method + ':' + uri);
/* 224:    */     }
/* 225:340 */     String hasha2 = encode(digester.digest(EncodingUtils.getBytes(this.a2, charset)));
/* 226:    */     String digestValue;
/* 227:    */     String digestValue;
/* 228:345 */     if (qop == 0)
/* 229:    */     {
/* 230:346 */       sb.setLength(0);
/* 231:347 */       sb.append(hasha1).append(':').append(nonce).append(':').append(hasha2);
/* 232:348 */       digestValue = sb.toString();
/* 233:    */     }
/* 234:    */     else
/* 235:    */     {
/* 236:350 */       sb.setLength(0);
/* 237:351 */       sb.append(hasha1).append(':').append(nonce).append(':').append(nc).append(':').append(this.cnonce).append(':').append(qop == 1 ? "auth-int" : "auth").append(':').append(hasha2);
/* 238:    */       
/* 239:    */ 
/* 240:354 */       digestValue = sb.toString();
/* 241:    */     }
/* 242:357 */     String digest = encode(digester.digest(EncodingUtils.getAsciiBytes(digestValue)));
/* 243:    */     
/* 244:359 */     CharArrayBuffer buffer = new CharArrayBuffer(128);
/* 245:360 */     if (isProxy()) {
/* 246:361 */       buffer.append("Proxy-Authorization");
/* 247:    */     } else {
/* 248:363 */       buffer.append("Authorization");
/* 249:    */     }
/* 250:365 */     buffer.append(": Digest ");
/* 251:    */     
/* 252:367 */     List<BasicNameValuePair> params = new ArrayList(20);
/* 253:368 */     params.add(new BasicNameValuePair("username", uname));
/* 254:369 */     params.add(new BasicNameValuePair("realm", realm));
/* 255:370 */     params.add(new BasicNameValuePair("nonce", nonce));
/* 256:371 */     params.add(new BasicNameValuePair("uri", uri));
/* 257:372 */     params.add(new BasicNameValuePair("response", digest));
/* 258:374 */     if (qop != 0)
/* 259:    */     {
/* 260:375 */       params.add(new BasicNameValuePair("qop", qop == 1 ? "auth-int" : "auth"));
/* 261:376 */       params.add(new BasicNameValuePair("nc", nc));
/* 262:377 */       params.add(new BasicNameValuePair("cnonce", this.cnonce));
/* 263:    */     }
/* 264:379 */     if (algorithm != null) {
/* 265:380 */       params.add(new BasicNameValuePair("algorithm", algorithm));
/* 266:    */     }
/* 267:382 */     if (opaque != null) {
/* 268:383 */       params.add(new BasicNameValuePair("opaque", opaque));
/* 269:    */     }
/* 270:386 */     for (int i = 0; i < params.size(); i++)
/* 271:    */     {
/* 272:387 */       BasicNameValuePair param = (BasicNameValuePair)params.get(i);
/* 273:388 */       if (i > 0) {
/* 274:389 */         buffer.append(", ");
/* 275:    */       }
/* 276:391 */       boolean noQuotes = ("nc".equals(param.getName())) || ("qop".equals(param.getName()));
/* 277:392 */       BasicHeaderValueFormatter.DEFAULT.formatNameValuePair(buffer, param, !noQuotes);
/* 278:    */     }
/* 279:394 */     return new BufferedHeader(buffer);
/* 280:    */   }
/* 281:    */   
/* 282:    */   String getCnonce()
/* 283:    */   {
/* 284:398 */     return this.cnonce;
/* 285:    */   }
/* 286:    */   
/* 287:    */   String getA1()
/* 288:    */   {
/* 289:402 */     return this.a1;
/* 290:    */   }
/* 291:    */   
/* 292:    */   String getA2()
/* 293:    */   {
/* 294:406 */     return this.a2;
/* 295:    */   }
/* 296:    */   
/* 297:    */   private static String encode(byte[] binaryData)
/* 298:    */   {
/* 299:417 */     int n = binaryData.length;
/* 300:418 */     char[] buffer = new char[n * 2];
/* 301:419 */     for (int i = 0; i < n; i++)
/* 302:    */     {
/* 303:420 */       int low = binaryData[i] & 0xF;
/* 304:421 */       int high = (binaryData[i] & 0xF0) >> 4;
/* 305:422 */       buffer[(i * 2)] = HEXADECIMAL[high];
/* 306:423 */       buffer[(i * 2 + 1)] = HEXADECIMAL[low];
/* 307:    */     }
/* 308:426 */     return new String(buffer);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public static String createCnonce()
/* 312:    */   {
/* 313:436 */     SecureRandom rnd = new SecureRandom();
/* 314:437 */     byte[] tmp = new byte[8];
/* 315:438 */     rnd.nextBytes(tmp);
/* 316:439 */     return encode(tmp);
/* 317:    */   }
/* 318:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.DigestScheme
 * JD-Core Version:    0.7.0.1
 */