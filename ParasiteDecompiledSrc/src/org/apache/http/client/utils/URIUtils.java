/*   1:    */ package org.apache.http.client.utils;
/*   2:    */ 
/*   3:    */ import java.net.URI;
/*   4:    */ import java.net.URISyntaxException;
/*   5:    */ import java.util.Stack;
/*   6:    */ import org.apache.http.HttpHost;
/*   7:    */ import org.apache.http.annotation.Immutable;
/*   8:    */ 
/*   9:    */ @Immutable
/*  10:    */ public class URIUtils
/*  11:    */ {
/*  12:    */   public static URI createURI(String scheme, String host, int port, String path, String query, String fragment)
/*  13:    */     throws URISyntaxException
/*  14:    */   {
/*  15: 80 */     StringBuilder buffer = new StringBuilder();
/*  16: 81 */     if (host != null)
/*  17:    */     {
/*  18: 82 */       if (scheme != null)
/*  19:    */       {
/*  20: 83 */         buffer.append(scheme);
/*  21: 84 */         buffer.append("://");
/*  22:    */       }
/*  23: 86 */       buffer.append(host);
/*  24: 87 */       if (port > 0)
/*  25:    */       {
/*  26: 88 */         buffer.append(':');
/*  27: 89 */         buffer.append(port);
/*  28:    */       }
/*  29:    */     }
/*  30: 92 */     if ((path == null) || (!path.startsWith("/"))) {
/*  31: 93 */       buffer.append('/');
/*  32:    */     }
/*  33: 95 */     if (path != null) {
/*  34: 96 */       buffer.append(path);
/*  35:    */     }
/*  36: 98 */     if (query != null)
/*  37:    */     {
/*  38: 99 */       buffer.append('?');
/*  39:100 */       buffer.append(query);
/*  40:    */     }
/*  41:102 */     if (fragment != null)
/*  42:    */     {
/*  43:103 */       buffer.append('#');
/*  44:104 */       buffer.append(fragment);
/*  45:    */     }
/*  46:106 */     return new URI(buffer.toString());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static URI rewriteURI(URI uri, HttpHost target, boolean dropFragment)
/*  50:    */     throws URISyntaxException
/*  51:    */   {
/*  52:129 */     if (uri == null) {
/*  53:130 */       throw new IllegalArgumentException("URI may nor be null");
/*  54:    */     }
/*  55:132 */     if (target != null) {
/*  56:133 */       return createURI(target.getSchemeName(), target.getHostName(), target.getPort(), normalizePath(uri.getRawPath()), uri.getRawQuery(), dropFragment ? null : uri.getRawFragment());
/*  57:    */     }
/*  58:141 */     return createURI(null, null, -1, normalizePath(uri.getRawPath()), uri.getRawQuery(), dropFragment ? null : uri.getRawFragment());
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static String normalizePath(String path)
/*  62:    */   {
/*  63:152 */     if (path == null) {
/*  64:153 */       return null;
/*  65:    */     }
/*  66:155 */     for (int n = 0; n < path.length(); n++) {
/*  67:157 */       if (path.charAt(n) != '/') {
/*  68:    */         break;
/*  69:    */       }
/*  70:    */     }
/*  71:161 */     if (n > 1) {
/*  72:162 */       path = path.substring(n - 1);
/*  73:    */     }
/*  74:164 */     return path;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static URI rewriteURI(URI uri, HttpHost target)
/*  78:    */     throws URISyntaxException
/*  79:    */   {
/*  80:175 */     return rewriteURI(uri, target, false);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static URI resolve(URI baseURI, String reference)
/*  84:    */   {
/*  85:187 */     return resolve(baseURI, URI.create(reference));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static URI resolve(URI baseURI, URI reference)
/*  89:    */   {
/*  90:199 */     if (baseURI == null) {
/*  91:200 */       throw new IllegalArgumentException("Base URI may nor be null");
/*  92:    */     }
/*  93:202 */     if (reference == null) {
/*  94:203 */       throw new IllegalArgumentException("Reference URI may nor be null");
/*  95:    */     }
/*  96:205 */     String s = reference.toString();
/*  97:206 */     if (s.startsWith("?")) {
/*  98:207 */       return resolveReferenceStartingWithQueryString(baseURI, reference);
/*  99:    */     }
/* 100:209 */     boolean emptyReference = s.length() == 0;
/* 101:210 */     if (emptyReference) {
/* 102:211 */       reference = URI.create("#");
/* 103:    */     }
/* 104:213 */     URI resolved = baseURI.resolve(reference);
/* 105:214 */     if (emptyReference)
/* 106:    */     {
/* 107:215 */       String resolvedString = resolved.toString();
/* 108:216 */       resolved = URI.create(resolvedString.substring(0, resolvedString.indexOf('#')));
/* 109:    */     }
/* 110:219 */     return removeDotSegments(resolved);
/* 111:    */   }
/* 112:    */   
/* 113:    */   private static URI resolveReferenceStartingWithQueryString(URI baseURI, URI reference)
/* 114:    */   {
/* 115:231 */     String baseUri = baseURI.toString();
/* 116:232 */     baseUri = baseUri.indexOf('?') > -1 ? baseUri.substring(0, baseUri.indexOf('?')) : baseUri;
/* 117:    */     
/* 118:234 */     return URI.create(baseUri + reference.toString());
/* 119:    */   }
/* 120:    */   
/* 121:    */   private static URI removeDotSegments(URI uri)
/* 122:    */   {
/* 123:244 */     String path = uri.getPath();
/* 124:245 */     if ((path == null) || (path.indexOf("/.") == -1)) {
/* 125:247 */       return uri;
/* 126:    */     }
/* 127:249 */     String[] inputSegments = path.split("/");
/* 128:250 */     Stack<String> outputSegments = new Stack();
/* 129:251 */     for (int i = 0; i < inputSegments.length; i++) {
/* 130:252 */       if ((inputSegments[i].length() != 0) && (!".".equals(inputSegments[i]))) {
/* 131:255 */         if ("..".equals(inputSegments[i]))
/* 132:    */         {
/* 133:256 */           if (!outputSegments.isEmpty()) {
/* 134:257 */             outputSegments.pop();
/* 135:    */           }
/* 136:    */         }
/* 137:    */         else {
/* 138:260 */           outputSegments.push(inputSegments[i]);
/* 139:    */         }
/* 140:    */       }
/* 141:    */     }
/* 142:263 */     StringBuilder outputBuffer = new StringBuilder();
/* 143:264 */     for (String outputSegment : outputSegments) {
/* 144:265 */       outputBuffer.append('/').append(outputSegment);
/* 145:    */     }
/* 146:    */     try
/* 147:    */     {
/* 148:268 */       return new URI(uri.getScheme(), uri.getAuthority(), outputBuffer.toString(), uri.getQuery(), uri.getFragment());
/* 149:    */     }
/* 150:    */     catch (URISyntaxException e)
/* 151:    */     {
/* 152:271 */       throw new IllegalArgumentException(e);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static HttpHost extractHost(URI uri)
/* 157:    */   {
/* 158:285 */     if (uri == null) {
/* 159:286 */       return null;
/* 160:    */     }
/* 161:288 */     HttpHost target = null;
/* 162:289 */     if (uri.isAbsolute())
/* 163:    */     {
/* 164:290 */       int port = uri.getPort();
/* 165:291 */       String host = uri.getHost();
/* 166:292 */       if (host == null)
/* 167:    */       {
/* 168:294 */         host = uri.getAuthority();
/* 169:295 */         if (host != null)
/* 170:    */         {
/* 171:297 */           int at = host.indexOf('@');
/* 172:298 */           if (at >= 0) {
/* 173:299 */             if (host.length() > at + 1) {
/* 174:300 */               host = host.substring(at + 1);
/* 175:    */             } else {
/* 176:302 */               host = null;
/* 177:    */             }
/* 178:    */           }
/* 179:306 */           if (host != null)
/* 180:    */           {
/* 181:307 */             int colon = host.indexOf(':');
/* 182:308 */             if (colon >= 0)
/* 183:    */             {
/* 184:309 */               if (colon + 1 < host.length()) {
/* 185:310 */                 port = Integer.parseInt(host.substring(colon + 1));
/* 186:    */               }
/* 187:312 */               host = host.substring(0, colon);
/* 188:    */             }
/* 189:    */           }
/* 190:    */         }
/* 191:    */       }
/* 192:317 */       String scheme = uri.getScheme();
/* 193:318 */       if (host != null) {
/* 194:319 */         target = new HttpHost(host, port, scheme);
/* 195:    */       }
/* 196:    */     }
/* 197:322 */     return target;
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.utils.URIUtils
 * JD-Core Version:    0.7.0.1
 */