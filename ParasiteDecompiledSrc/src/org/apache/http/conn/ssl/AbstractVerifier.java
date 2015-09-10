/*   1:    */ package org.apache.http.conn.ssl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.security.cert.Certificate;
/*   6:    */ import java.security.cert.CertificateParsingException;
/*   7:    */ import java.security.cert.X509Certificate;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Locale;
/*  14:    */ import java.util.StringTokenizer;
/*  15:    */ import java.util.logging.Level;
/*  16:    */ import java.util.logging.Logger;
/*  17:    */ import javax.net.ssl.SSLException;
/*  18:    */ import javax.net.ssl.SSLSession;
/*  19:    */ import javax.net.ssl.SSLSocket;
/*  20:    */ import javax.security.auth.x500.X500Principal;
/*  21:    */ import org.apache.http.annotation.Immutable;
/*  22:    */ import org.apache.http.conn.util.InetAddressUtils;
/*  23:    */ 
/*  24:    */ @Immutable
/*  25:    */ public abstract class AbstractVerifier
/*  26:    */   implements X509HostnameVerifier
/*  27:    */ {
/*  28: 72 */   private static final String[] BAD_COUNTRY_2LDS = { "ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org" };
/*  29:    */   
/*  30:    */   static
/*  31:    */   {
/*  32: 78 */     Arrays.sort(BAD_COUNTRY_2LDS);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final void verify(String host, SSLSocket ssl)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 87 */     if (host == null) {
/*  39: 88 */       throw new NullPointerException("host to verify is null");
/*  40:    */     }
/*  41: 91 */     SSLSession session = ssl.getSession();
/*  42: 92 */     if (session == null)
/*  43:    */     {
/*  44: 96 */       InputStream in = ssl.getInputStream();
/*  45: 97 */       in.available();
/*  46:    */       
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:116 */       session = ssl.getSession();
/*  65:117 */       if (session == null)
/*  66:    */       {
/*  67:120 */         ssl.startHandshake();
/*  68:    */         
/*  69:    */ 
/*  70:    */ 
/*  71:124 */         session = ssl.getSession();
/*  72:    */       }
/*  73:    */     }
/*  74:128 */     Certificate[] certs = session.getPeerCertificates();
/*  75:129 */     X509Certificate x509 = (X509Certificate)certs[0];
/*  76:130 */     verify(host, x509);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public final boolean verify(String host, SSLSession session)
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:135 */       Certificate[] certs = session.getPeerCertificates();
/*  84:136 */       X509Certificate x509 = (X509Certificate)certs[0];
/*  85:137 */       verify(host, x509);
/*  86:138 */       return true;
/*  87:    */     }
/*  88:    */     catch (SSLException e) {}
/*  89:141 */     return false;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final void verify(String host, X509Certificate cert)
/*  93:    */     throws SSLException
/*  94:    */   {
/*  95:147 */     String[] cns = getCNs(cert);
/*  96:148 */     String[] subjectAlts = getSubjectAlts(cert, host);
/*  97:149 */     verify(host, cns, subjectAlts);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public final void verify(String host, String[] cns, String[] subjectAlts, boolean strictWithSubDomains)
/* 101:    */     throws SSLException
/* 102:    */   {
/* 103:161 */     LinkedList<String> names = new LinkedList();
/* 104:162 */     if ((cns != null) && (cns.length > 0) && (cns[0] != null)) {
/* 105:163 */       names.add(cns[0]);
/* 106:    */     }
/* 107:165 */     if (subjectAlts != null) {
/* 108:166 */       for (String subjectAlt : subjectAlts) {
/* 109:167 */         if (subjectAlt != null) {
/* 110:168 */           names.add(subjectAlt);
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:173 */     if (names.isEmpty())
/* 115:    */     {
/* 116:174 */       String msg = "Certificate for <" + host + "> doesn't contain CN or DNS subjectAlt";
/* 117:175 */       throw new SSLException(msg);
/* 118:    */     }
/* 119:179 */     StringBuilder buf = new StringBuilder();
/* 120:    */     
/* 121:    */ 
/* 122:    */ 
/* 123:183 */     String hostName = host.trim().toLowerCase(Locale.ENGLISH);
/* 124:184 */     boolean match = false;
/* 125:185 */     for (Iterator<String> it = names.iterator(); it.hasNext();)
/* 126:    */     {
/* 127:187 */       String cn = (String)it.next();
/* 128:188 */       cn = cn.toLowerCase(Locale.ENGLISH);
/* 129:    */       
/* 130:190 */       buf.append(" <");
/* 131:191 */       buf.append(cn);
/* 132:192 */       buf.append('>');
/* 133:193 */       if (it.hasNext()) {
/* 134:194 */         buf.append(" OR");
/* 135:    */       }
/* 136:200 */       String[] parts = cn.split("\\.");
/* 137:201 */       boolean doWildcard = (parts.length >= 3) && (parts[0].endsWith("*")) && (acceptableCountryWildcard(cn)) && (!isIPAddress(host));
/* 138:206 */       if (doWildcard)
/* 139:    */       {
/* 140:207 */         if (parts[0].length() > 1)
/* 141:    */         {
/* 142:208 */           String prefix = parts[0].substring(0, parts.length - 2);
/* 143:209 */           String suffix = cn.substring(parts[0].length());
/* 144:210 */           String hostSuffix = hostName.substring(prefix.length());
/* 145:211 */           match = (hostName.startsWith(prefix)) && (hostSuffix.endsWith(suffix));
/* 146:    */         }
/* 147:    */         else
/* 148:    */         {
/* 149:213 */           match = hostName.endsWith(cn.substring(1));
/* 150:    */         }
/* 151:215 */         if ((match) && (strictWithSubDomains)) {
/* 152:218 */           match = countDots(hostName) == countDots(cn);
/* 153:    */         }
/* 154:    */       }
/* 155:    */       else
/* 156:    */       {
/* 157:221 */         match = hostName.equals(cn);
/* 158:    */       }
/* 159:223 */       if (match) {
/* 160:    */         break;
/* 161:    */       }
/* 162:    */     }
/* 163:227 */     if (!match) {
/* 164:228 */       throw new SSLException("hostname in certificate didn't match: <" + host + "> !=" + buf);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static boolean acceptableCountryWildcard(String cn)
/* 169:    */   {
/* 170:233 */     String[] parts = cn.split("\\.");
/* 171:234 */     if ((parts.length != 3) || (parts[2].length() != 2)) {
/* 172:235 */       return true;
/* 173:    */     }
/* 174:237 */     return Arrays.binarySearch(BAD_COUNTRY_2LDS, parts[1]) < 0;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static String[] getCNs(X509Certificate cert)
/* 178:    */   {
/* 179:241 */     LinkedList<String> cnList = new LinkedList();
/* 180:    */     
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:264 */     String subjectPrincipal = cert.getSubjectX500Principal().toString();
/* 203:265 */     StringTokenizer st = new StringTokenizer(subjectPrincipal, ",");
/* 204:266 */     while (st.hasMoreTokens())
/* 205:    */     {
/* 206:267 */       String tok = st.nextToken();
/* 207:268 */       int x = tok.indexOf("CN=");
/* 208:269 */       if (x >= 0) {
/* 209:270 */         cnList.add(tok.substring(x + 3));
/* 210:    */       }
/* 211:    */     }
/* 212:273 */     if (!cnList.isEmpty())
/* 213:    */     {
/* 214:274 */       String[] cns = new String[cnList.size()];
/* 215:275 */       cnList.toArray(cns);
/* 216:276 */       return cns;
/* 217:    */     }
/* 218:278 */     return null;
/* 219:    */   }
/* 220:    */   
/* 221:    */   private static String[] getSubjectAlts(X509Certificate cert, String hostname)
/* 222:    */   {
/* 223:    */     int subjectType;
/* 224:    */     int subjectType;
/* 225:293 */     if (isIPAddress(hostname)) {
/* 226:294 */       subjectType = 7;
/* 227:    */     } else {
/* 228:296 */       subjectType = 2;
/* 229:    */     }
/* 230:299 */     LinkedList<String> subjectAltList = new LinkedList();
/* 231:300 */     Collection<List<?>> c = null;
/* 232:    */     try
/* 233:    */     {
/* 234:302 */       c = cert.getSubjectAlternativeNames();
/* 235:    */     }
/* 236:    */     catch (CertificateParsingException cpe)
/* 237:    */     {
/* 238:305 */       Logger.getLogger(AbstractVerifier.class.getName()).log(Level.FINE, "Error parsing certificate.", cpe);
/* 239:    */     }
/* 240:308 */     if (c != null) {
/* 241:309 */       for (List<?> aC : c)
/* 242:    */       {
/* 243:310 */         List<?> list = aC;
/* 244:311 */         int type = ((Integer)list.get(0)).intValue();
/* 245:312 */         if (type == subjectType)
/* 246:    */         {
/* 247:313 */           String s = (String)list.get(1);
/* 248:314 */           subjectAltList.add(s);
/* 249:    */         }
/* 250:    */       }
/* 251:    */     }
/* 252:318 */     if (!subjectAltList.isEmpty())
/* 253:    */     {
/* 254:319 */       String[] subjectAlts = new String[subjectAltList.size()];
/* 255:320 */       subjectAltList.toArray(subjectAlts);
/* 256:321 */       return subjectAlts;
/* 257:    */     }
/* 258:323 */     return null;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public static String[] getDNSSubjectAlts(X509Certificate cert)
/* 262:    */   {
/* 263:342 */     return getSubjectAlts(cert, null);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public static int countDots(String s)
/* 267:    */   {
/* 268:351 */     int count = 0;
/* 269:352 */     for (int i = 0; i < s.length(); i++) {
/* 270:353 */       if (s.charAt(i) == '.') {
/* 271:354 */         count++;
/* 272:    */       }
/* 273:    */     }
/* 274:357 */     return count;
/* 275:    */   }
/* 276:    */   
/* 277:    */   private static boolean isIPAddress(String hostname)
/* 278:    */   {
/* 279:361 */     return (hostname != null) && ((InetAddressUtils.isIPv4Address(hostname)) || (InetAddressUtils.isIPv6Address(hostname)));
/* 280:    */   }
/* 281:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ssl.AbstractVerifier
 * JD-Core Version:    0.7.0.1
 */