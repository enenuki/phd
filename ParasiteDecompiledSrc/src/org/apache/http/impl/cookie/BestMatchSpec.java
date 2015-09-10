/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.apache.http.FormattedHeader;
/*   5:    */ import org.apache.http.Header;
/*   6:    */ import org.apache.http.HeaderElement;
/*   7:    */ import org.apache.http.annotation.NotThreadSafe;
/*   8:    */ import org.apache.http.cookie.Cookie;
/*   9:    */ import org.apache.http.cookie.CookieOrigin;
/*  10:    */ import org.apache.http.cookie.CookieSpec;
/*  11:    */ import org.apache.http.cookie.MalformedCookieException;
/*  12:    */ import org.apache.http.cookie.SetCookie2;
/*  13:    */ import org.apache.http.message.ParserCursor;
/*  14:    */ import org.apache.http.util.CharArrayBuffer;
/*  15:    */ 
/*  16:    */ @NotThreadSafe
/*  17:    */ public class BestMatchSpec
/*  18:    */   implements CookieSpec
/*  19:    */ {
/*  20:    */   private final String[] datepatterns;
/*  21:    */   private final boolean oneHeader;
/*  22:    */   private RFC2965Spec strict;
/*  23:    */   private RFC2109Spec obsoleteStrict;
/*  24:    */   private BrowserCompatSpec compat;
/*  25:    */   
/*  26:    */   public BestMatchSpec(String[] datepatterns, boolean oneHeader)
/*  27:    */   {
/*  28: 65 */     this.datepatterns = (datepatterns == null ? null : (String[])datepatterns.clone());
/*  29: 66 */     this.oneHeader = oneHeader;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public BestMatchSpec()
/*  33:    */   {
/*  34: 70 */     this(null, false);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private RFC2965Spec getStrict()
/*  38:    */   {
/*  39: 74 */     if (this.strict == null) {
/*  40: 75 */       this.strict = new RFC2965Spec(this.datepatterns, this.oneHeader);
/*  41:    */     }
/*  42: 77 */     return this.strict;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private RFC2109Spec getObsoleteStrict()
/*  46:    */   {
/*  47: 81 */     if (this.obsoleteStrict == null) {
/*  48: 82 */       this.obsoleteStrict = new RFC2109Spec(this.datepatterns, this.oneHeader);
/*  49:    */     }
/*  50: 84 */     return this.obsoleteStrict;
/*  51:    */   }
/*  52:    */   
/*  53:    */   private BrowserCompatSpec getCompat()
/*  54:    */   {
/*  55: 88 */     if (this.compat == null) {
/*  56: 89 */       this.compat = new BrowserCompatSpec(this.datepatterns);
/*  57:    */     }
/*  58: 91 */     return this.compat;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*  62:    */     throws MalformedCookieException
/*  63:    */   {
/*  64: 97 */     if (header == null) {
/*  65: 98 */       throw new IllegalArgumentException("Header may not be null");
/*  66:    */     }
/*  67:100 */     if (origin == null) {
/*  68:101 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  69:    */     }
/*  70:103 */     HeaderElement[] helems = header.getElements();
/*  71:104 */     boolean versioned = false;
/*  72:105 */     boolean netscape = false;
/*  73:106 */     for (HeaderElement helem : helems)
/*  74:    */     {
/*  75:107 */       if (helem.getParameterByName("version") != null) {
/*  76:108 */         versioned = true;
/*  77:    */       }
/*  78:110 */       if (helem.getParameterByName("expires") != null) {
/*  79:111 */         netscape = true;
/*  80:    */       }
/*  81:    */     }
/*  82:114 */     if ((netscape) || (!versioned))
/*  83:    */     {
/*  84:117 */       NetscapeDraftHeaderParser parser = NetscapeDraftHeaderParser.DEFAULT;
/*  85:    */       ParserCursor cursor;
/*  86:    */       CharArrayBuffer buffer;
/*  87:    */       ParserCursor cursor;
/*  88:120 */       if ((header instanceof FormattedHeader))
/*  89:    */       {
/*  90:121 */         CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/*  91:122 */         cursor = new ParserCursor(((FormattedHeader)header).getValuePos(), buffer.length());
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:126 */         String s = header.getValue();
/*  96:127 */         if (s == null) {
/*  97:128 */           throw new MalformedCookieException("Header value is null");
/*  98:    */         }
/*  99:130 */         buffer = new CharArrayBuffer(s.length());
/* 100:131 */         buffer.append(s);
/* 101:132 */         cursor = new ParserCursor(0, buffer.length());
/* 102:    */       }
/* 103:134 */       helems = new HeaderElement[] { parser.parseHeader(buffer, cursor) };
/* 104:135 */       return getCompat().parse(helems, origin);
/* 105:    */     }
/* 106:137 */     if ("Set-Cookie2".equals(header.getName())) {
/* 107:138 */       return getStrict().parse(helems, origin);
/* 108:    */     }
/* 109:140 */     return getObsoleteStrict().parse(helems, origin);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void validate(Cookie cookie, CookieOrigin origin)
/* 113:    */     throws MalformedCookieException
/* 114:    */   {
/* 115:148 */     if (cookie == null) {
/* 116:149 */       throw new IllegalArgumentException("Cookie may not be null");
/* 117:    */     }
/* 118:151 */     if (origin == null) {
/* 119:152 */       throw new IllegalArgumentException("Cookie origin may not be null");
/* 120:    */     }
/* 121:154 */     if (cookie.getVersion() > 0)
/* 122:    */     {
/* 123:155 */       if ((cookie instanceof SetCookie2)) {
/* 124:156 */         getStrict().validate(cookie, origin);
/* 125:    */       } else {
/* 126:158 */         getObsoleteStrict().validate(cookie, origin);
/* 127:    */       }
/* 128:    */     }
/* 129:    */     else {
/* 130:161 */       getCompat().validate(cookie, origin);
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/* 135:    */   {
/* 136:166 */     if (cookie == null) {
/* 137:167 */       throw new IllegalArgumentException("Cookie may not be null");
/* 138:    */     }
/* 139:169 */     if (origin == null) {
/* 140:170 */       throw new IllegalArgumentException("Cookie origin may not be null");
/* 141:    */     }
/* 142:172 */     if (cookie.getVersion() > 0)
/* 143:    */     {
/* 144:173 */       if ((cookie instanceof SetCookie2)) {
/* 145:174 */         return getStrict().match(cookie, origin);
/* 146:    */       }
/* 147:176 */       return getObsoleteStrict().match(cookie, origin);
/* 148:    */     }
/* 149:179 */     return getCompat().match(cookie, origin);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<Header> formatCookies(List<Cookie> cookies)
/* 153:    */   {
/* 154:184 */     if (cookies == null) {
/* 155:185 */       throw new IllegalArgumentException("List of cookies may not be null");
/* 156:    */     }
/* 157:187 */     int version = 2147483647;
/* 158:188 */     boolean isSetCookie2 = true;
/* 159:189 */     for (Cookie cookie : cookies)
/* 160:    */     {
/* 161:190 */       if (!(cookie instanceof SetCookie2)) {
/* 162:191 */         isSetCookie2 = false;
/* 163:    */       }
/* 164:193 */       if (cookie.getVersion() < version) {
/* 165:194 */         version = cookie.getVersion();
/* 166:    */       }
/* 167:    */     }
/* 168:197 */     if (version > 0)
/* 169:    */     {
/* 170:198 */       if (isSetCookie2) {
/* 171:199 */         return getStrict().formatCookies(cookies);
/* 172:    */       }
/* 173:201 */       return getObsoleteStrict().formatCookies(cookies);
/* 174:    */     }
/* 175:204 */     return getCompat().formatCookies(cookies);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getVersion()
/* 179:    */   {
/* 180:209 */     return getStrict().getVersion();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Header getVersionHeader()
/* 184:    */   {
/* 185:213 */     return getStrict().getVersionHeader();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String toString()
/* 189:    */   {
/* 190:218 */     return "best-match";
/* 191:    */   }
/* 192:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BestMatchSpec
 * JD-Core Version:    0.7.0.1
 */