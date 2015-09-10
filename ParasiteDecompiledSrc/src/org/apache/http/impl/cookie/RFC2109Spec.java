/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.http.Header;
/*   7:    */ import org.apache.http.HeaderElement;
/*   8:    */ import org.apache.http.annotation.NotThreadSafe;
/*   9:    */ import org.apache.http.cookie.ClientCookie;
/*  10:    */ import org.apache.http.cookie.Cookie;
/*  11:    */ import org.apache.http.cookie.CookieOrigin;
/*  12:    */ import org.apache.http.cookie.CookiePathComparator;
/*  13:    */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*  14:    */ import org.apache.http.cookie.MalformedCookieException;
/*  15:    */ import org.apache.http.message.BufferedHeader;
/*  16:    */ import org.apache.http.util.CharArrayBuffer;
/*  17:    */ 
/*  18:    */ @NotThreadSafe
/*  19:    */ public class RFC2109Spec
/*  20:    */   extends CookieSpecBase
/*  21:    */ {
/*  22: 61 */   private static final CookiePathComparator PATH_COMPARATOR = new CookiePathComparator();
/*  23: 63 */   private static final String[] DATE_PATTERNS = { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy" };
/*  24:    */   private final String[] datepatterns;
/*  25:    */   private final boolean oneHeader;
/*  26:    */   
/*  27:    */   public RFC2109Spec(String[] datepatterns, boolean oneHeader)
/*  28:    */   {
/*  29: 75 */     if (datepatterns != null) {
/*  30: 76 */       this.datepatterns = ((String[])datepatterns.clone());
/*  31:    */     } else {
/*  32: 78 */       this.datepatterns = DATE_PATTERNS;
/*  33:    */     }
/*  34: 80 */     this.oneHeader = oneHeader;
/*  35: 81 */     registerAttribHandler("version", new RFC2109VersionHandler());
/*  36: 82 */     registerAttribHandler("path", new BasicPathHandler());
/*  37: 83 */     registerAttribHandler("domain", new RFC2109DomainHandler());
/*  38: 84 */     registerAttribHandler("max-age", new BasicMaxAgeHandler());
/*  39: 85 */     registerAttribHandler("secure", new BasicSecureHandler());
/*  40: 86 */     registerAttribHandler("comment", new BasicCommentHandler());
/*  41: 87 */     registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
/*  42:    */   }
/*  43:    */   
/*  44:    */   public RFC2109Spec()
/*  45:    */   {
/*  46: 93 */     this(null, false);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*  50:    */     throws MalformedCookieException
/*  51:    */   {
/*  52: 98 */     if (header == null) {
/*  53: 99 */       throw new IllegalArgumentException("Header may not be null");
/*  54:    */     }
/*  55:101 */     if (origin == null) {
/*  56:102 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  57:    */     }
/*  58:104 */     if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
/*  59:105 */       throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
/*  60:    */     }
/*  61:108 */     HeaderElement[] elems = header.getElements();
/*  62:109 */     return parse(elems, origin);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void validate(Cookie cookie, CookieOrigin origin)
/*  66:    */     throws MalformedCookieException
/*  67:    */   {
/*  68:115 */     if (cookie == null) {
/*  69:116 */       throw new IllegalArgumentException("Cookie may not be null");
/*  70:    */     }
/*  71:118 */     String name = cookie.getName();
/*  72:119 */     if (name.indexOf(' ') != -1) {
/*  73:120 */       throw new CookieRestrictionViolationException("Cookie name may not contain blanks");
/*  74:    */     }
/*  75:122 */     if (name.startsWith("$")) {
/*  76:123 */       throw new CookieRestrictionViolationException("Cookie name may not start with $");
/*  77:    */     }
/*  78:125 */     super.validate(cookie, origin);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List<Header> formatCookies(List<Cookie> cookies)
/*  82:    */   {
/*  83:129 */     if (cookies == null) {
/*  84:130 */       throw new IllegalArgumentException("List of cookies may not be null");
/*  85:    */     }
/*  86:132 */     if (cookies.isEmpty()) {
/*  87:133 */       throw new IllegalArgumentException("List of cookies may not be empty");
/*  88:    */     }
/*  89:135 */     if (cookies.size() > 1)
/*  90:    */     {
/*  91:137 */       cookies = new ArrayList(cookies);
/*  92:138 */       Collections.sort(cookies, PATH_COMPARATOR);
/*  93:    */     }
/*  94:140 */     if (this.oneHeader) {
/*  95:141 */       return doFormatOneHeader(cookies);
/*  96:    */     }
/*  97:143 */     return doFormatManyHeaders(cookies);
/*  98:    */   }
/*  99:    */   
/* 100:    */   private List<Header> doFormatOneHeader(List<Cookie> cookies)
/* 101:    */   {
/* 102:148 */     int version = 2147483647;
/* 103:150 */     for (Cookie cookie : cookies) {
/* 104:151 */       if (cookie.getVersion() < version) {
/* 105:152 */         version = cookie.getVersion();
/* 106:    */       }
/* 107:    */     }
/* 108:155 */     CharArrayBuffer buffer = new CharArrayBuffer(40 * cookies.size());
/* 109:156 */     buffer.append("Cookie");
/* 110:157 */     buffer.append(": ");
/* 111:158 */     buffer.append("$Version=");
/* 112:159 */     buffer.append(Integer.toString(version));
/* 113:160 */     for (Cookie cooky : cookies)
/* 114:    */     {
/* 115:161 */       buffer.append("; ");
/* 116:162 */       Cookie cookie = cooky;
/* 117:163 */       formatCookieAsVer(buffer, cookie, version);
/* 118:    */     }
/* 119:165 */     List<Header> headers = new ArrayList(1);
/* 120:166 */     headers.add(new BufferedHeader(buffer));
/* 121:167 */     return headers;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private List<Header> doFormatManyHeaders(List<Cookie> cookies)
/* 125:    */   {
/* 126:171 */     List<Header> headers = new ArrayList(cookies.size());
/* 127:172 */     for (Cookie cookie : cookies)
/* 128:    */     {
/* 129:173 */       int version = cookie.getVersion();
/* 130:174 */       CharArrayBuffer buffer = new CharArrayBuffer(40);
/* 131:175 */       buffer.append("Cookie: ");
/* 132:176 */       buffer.append("$Version=");
/* 133:177 */       buffer.append(Integer.toString(version));
/* 134:178 */       buffer.append("; ");
/* 135:179 */       formatCookieAsVer(buffer, cookie, version);
/* 136:180 */       headers.add(new BufferedHeader(buffer));
/* 137:    */     }
/* 138:182 */     return headers;
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected void formatParamAsVer(CharArrayBuffer buffer, String name, String value, int version)
/* 142:    */   {
/* 143:196 */     buffer.append(name);
/* 144:197 */     buffer.append("=");
/* 145:198 */     if (value != null) {
/* 146:199 */       if (version > 0)
/* 147:    */       {
/* 148:200 */         buffer.append('"');
/* 149:201 */         buffer.append(value);
/* 150:202 */         buffer.append('"');
/* 151:    */       }
/* 152:    */       else
/* 153:    */       {
/* 154:204 */         buffer.append(value);
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected void formatCookieAsVer(CharArrayBuffer buffer, Cookie cookie, int version)
/* 160:    */   {
/* 161:218 */     formatParamAsVer(buffer, cookie.getName(), cookie.getValue(), version);
/* 162:219 */     if ((cookie.getPath() != null) && 
/* 163:220 */       ((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("path")))
/* 164:    */     {
/* 165:222 */       buffer.append("; ");
/* 166:223 */       formatParamAsVer(buffer, "$Path", cookie.getPath(), version);
/* 167:    */     }
/* 168:226 */     if ((cookie.getDomain() != null) && 
/* 169:227 */       ((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("domain")))
/* 170:    */     {
/* 171:229 */       buffer.append("; ");
/* 172:230 */       formatParamAsVer(buffer, "$Domain", cookie.getDomain(), version);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getVersion()
/* 177:    */   {
/* 178:236 */     return 1;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Header getVersionHeader()
/* 182:    */   {
/* 183:240 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String toString()
/* 187:    */   {
/* 188:245 */     return "rfc2109";
/* 189:    */   }
/* 190:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.RFC2109Spec
 * JD-Core Version:    0.7.0.1
 */