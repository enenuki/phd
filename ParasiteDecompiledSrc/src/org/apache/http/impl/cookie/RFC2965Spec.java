/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Locale;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import org.apache.http.Header;
/*  10:    */ import org.apache.http.HeaderElement;
/*  11:    */ import org.apache.http.NameValuePair;
/*  12:    */ import org.apache.http.annotation.NotThreadSafe;
/*  13:    */ import org.apache.http.cookie.ClientCookie;
/*  14:    */ import org.apache.http.cookie.Cookie;
/*  15:    */ import org.apache.http.cookie.CookieAttributeHandler;
/*  16:    */ import org.apache.http.cookie.CookieOrigin;
/*  17:    */ import org.apache.http.cookie.MalformedCookieException;
/*  18:    */ import org.apache.http.message.BufferedHeader;
/*  19:    */ import org.apache.http.util.CharArrayBuffer;
/*  20:    */ 
/*  21:    */ @NotThreadSafe
/*  22:    */ public class RFC2965Spec
/*  23:    */   extends RFC2109Spec
/*  24:    */ {
/*  25:    */   public RFC2965Spec()
/*  26:    */   {
/*  27: 63 */     this(null, false);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public RFC2965Spec(String[] datepatterns, boolean oneHeader)
/*  31:    */   {
/*  32: 67 */     super(datepatterns, oneHeader);
/*  33: 68 */     registerAttribHandler("domain", new RFC2965DomainAttributeHandler());
/*  34: 69 */     registerAttribHandler("port", new RFC2965PortAttributeHandler());
/*  35: 70 */     registerAttribHandler("commenturl", new RFC2965CommentUrlAttributeHandler());
/*  36: 71 */     registerAttribHandler("discard", new RFC2965DiscardAttributeHandler());
/*  37: 72 */     registerAttribHandler("version", new RFC2965VersionAttributeHandler());
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*  41:    */     throws MalformedCookieException
/*  42:    */   {
/*  43: 79 */     if (header == null) {
/*  44: 80 */       throw new IllegalArgumentException("Header may not be null");
/*  45:    */     }
/*  46: 82 */     if (origin == null) {
/*  47: 83 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  48:    */     }
/*  49: 85 */     if (!header.getName().equalsIgnoreCase("Set-Cookie2")) {
/*  50: 86 */       throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
/*  51:    */     }
/*  52: 89 */     origin = adjustEffectiveHost(origin);
/*  53: 90 */     HeaderElement[] elems = header.getElements();
/*  54: 91 */     return createCookies(elems, origin);
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected List<Cookie> parse(HeaderElement[] elems, CookieOrigin origin)
/*  58:    */     throws MalformedCookieException
/*  59:    */   {
/*  60: 98 */     origin = adjustEffectiveHost(origin);
/*  61: 99 */     return createCookies(elems, origin);
/*  62:    */   }
/*  63:    */   
/*  64:    */   private List<Cookie> createCookies(HeaderElement[] elems, CookieOrigin origin)
/*  65:    */     throws MalformedCookieException
/*  66:    */   {
/*  67:105 */     List<Cookie> cookies = new ArrayList(elems.length);
/*  68:106 */     for (HeaderElement headerelement : elems)
/*  69:    */     {
/*  70:107 */       String name = headerelement.getName();
/*  71:108 */       String value = headerelement.getValue();
/*  72:109 */       if ((name == null) || (name.length() == 0)) {
/*  73:110 */         throw new MalformedCookieException("Cookie name may not be empty");
/*  74:    */       }
/*  75:113 */       BasicClientCookie2 cookie = new BasicClientCookie2(name, value);
/*  76:114 */       cookie.setPath(getDefaultPath(origin));
/*  77:115 */       cookie.setDomain(getDefaultDomain(origin));
/*  78:116 */       cookie.setPorts(new int[] { origin.getPort() });
/*  79:    */       
/*  80:118 */       NameValuePair[] attribs = headerelement.getParameters();
/*  81:    */       
/*  82:    */ 
/*  83:    */ 
/*  84:122 */       Map<String, NameValuePair> attribmap = new HashMap(attribs.length);
/*  85:124 */       for (int j = attribs.length - 1; j >= 0; j--)
/*  86:    */       {
/*  87:125 */         NameValuePair param = attribs[j];
/*  88:126 */         attribmap.put(param.getName().toLowerCase(Locale.ENGLISH), param);
/*  89:    */       }
/*  90:128 */       for (Map.Entry<String, NameValuePair> entry : attribmap.entrySet())
/*  91:    */       {
/*  92:129 */         NameValuePair attrib = (NameValuePair)entry.getValue();
/*  93:130 */         String s = attrib.getName().toLowerCase(Locale.ENGLISH);
/*  94:    */         
/*  95:132 */         cookie.setAttribute(s, attrib.getValue());
/*  96:    */         
/*  97:134 */         CookieAttributeHandler handler = findAttribHandler(s);
/*  98:135 */         if (handler != null) {
/*  99:136 */           handler.parse(cookie, attrib.getValue());
/* 100:    */         }
/* 101:    */       }
/* 102:139 */       cookies.add(cookie);
/* 103:    */     }
/* 104:141 */     return cookies;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void validate(Cookie cookie, CookieOrigin origin)
/* 108:    */     throws MalformedCookieException
/* 109:    */   {
/* 110:147 */     if (cookie == null) {
/* 111:148 */       throw new IllegalArgumentException("Cookie may not be null");
/* 112:    */     }
/* 113:150 */     if (origin == null) {
/* 114:151 */       throw new IllegalArgumentException("Cookie origin may not be null");
/* 115:    */     }
/* 116:153 */     origin = adjustEffectiveHost(origin);
/* 117:154 */     super.validate(cookie, origin);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/* 121:    */   {
/* 122:159 */     if (cookie == null) {
/* 123:160 */       throw new IllegalArgumentException("Cookie may not be null");
/* 124:    */     }
/* 125:162 */     if (origin == null) {
/* 126:163 */       throw new IllegalArgumentException("Cookie origin may not be null");
/* 127:    */     }
/* 128:165 */     origin = adjustEffectiveHost(origin);
/* 129:166 */     return super.match(cookie, origin);
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected void formatCookieAsVer(CharArrayBuffer buffer, Cookie cookie, int version)
/* 133:    */   {
/* 134:175 */     super.formatCookieAsVer(buffer, cookie, version);
/* 135:177 */     if ((cookie instanceof ClientCookie))
/* 136:    */     {
/* 137:179 */       String s = ((ClientCookie)cookie).getAttribute("port");
/* 138:180 */       if (s != null)
/* 139:    */       {
/* 140:181 */         buffer.append("; $Port");
/* 141:182 */         buffer.append("=\"");
/* 142:183 */         if (s.trim().length() > 0)
/* 143:    */         {
/* 144:184 */           int[] ports = cookie.getPorts();
/* 145:185 */           if (ports != null)
/* 146:    */           {
/* 147:186 */             int i = 0;
/* 148:186 */             for (int len = ports.length; i < len; i++)
/* 149:    */             {
/* 150:187 */               if (i > 0) {
/* 151:188 */                 buffer.append(",");
/* 152:    */               }
/* 153:190 */               buffer.append(Integer.toString(ports[i]));
/* 154:    */             }
/* 155:    */           }
/* 156:    */         }
/* 157:194 */         buffer.append("\"");
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   private static CookieOrigin adjustEffectiveHost(CookieOrigin origin)
/* 163:    */   {
/* 164:211 */     String host = origin.getHost();
/* 165:    */     
/* 166:    */ 
/* 167:    */ 
/* 168:215 */     boolean isLocalHost = true;
/* 169:216 */     for (int i = 0; i < host.length(); i++)
/* 170:    */     {
/* 171:217 */       char ch = host.charAt(i);
/* 172:218 */       if ((ch == '.') || (ch == ':'))
/* 173:    */       {
/* 174:219 */         isLocalHost = false;
/* 175:220 */         break;
/* 176:    */       }
/* 177:    */     }
/* 178:223 */     if (isLocalHost)
/* 179:    */     {
/* 180:224 */       host = host + ".local";
/* 181:225 */       return new CookieOrigin(host, origin.getPort(), origin.getPath(), origin.isSecure());
/* 182:    */     }
/* 183:231 */     return origin;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int getVersion()
/* 187:    */   {
/* 188:237 */     return 1;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Header getVersionHeader()
/* 192:    */   {
/* 193:242 */     CharArrayBuffer buffer = new CharArrayBuffer(40);
/* 194:243 */     buffer.append("Cookie2");
/* 195:244 */     buffer.append(": ");
/* 196:245 */     buffer.append("$Version=");
/* 197:246 */     buffer.append(Integer.toString(getVersion()));
/* 198:247 */     return new BufferedHeader(buffer);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String toString()
/* 202:    */   {
/* 203:252 */     return "rfc2965";
/* 204:    */   }
/* 205:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.RFC2965Spec
 * JD-Core Version:    0.7.0.1
 */