/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.http.FormattedHeader;
/*   6:    */ import org.apache.http.Header;
/*   7:    */ import org.apache.http.HeaderElement;
/*   8:    */ import org.apache.http.annotation.NotThreadSafe;
/*   9:    */ import org.apache.http.cookie.Cookie;
/*  10:    */ import org.apache.http.cookie.CookieOrigin;
/*  11:    */ import org.apache.http.cookie.MalformedCookieException;
/*  12:    */ import org.apache.http.message.BufferedHeader;
/*  13:    */ import org.apache.http.message.ParserCursor;
/*  14:    */ import org.apache.http.util.CharArrayBuffer;
/*  15:    */ 
/*  16:    */ @NotThreadSafe
/*  17:    */ public class BrowserCompatSpec
/*  18:    */   extends CookieSpecBase
/*  19:    */ {
/*  20:    */   @Deprecated
/*  21: 59 */   protected static final String[] DATE_PATTERNS = { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z" };
/*  22: 76 */   private static final String[] DEFAULT_DATE_PATTERNS = { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z" };
/*  23:    */   private final String[] datepatterns;
/*  24:    */   
/*  25:    */   public BrowserCompatSpec(String[] datepatterns)
/*  26:    */   {
/*  27: 98 */     if (datepatterns != null) {
/*  28: 99 */       this.datepatterns = ((String[])datepatterns.clone());
/*  29:    */     } else {
/*  30:101 */       this.datepatterns = DEFAULT_DATE_PATTERNS;
/*  31:    */     }
/*  32:103 */     registerAttribHandler("path", new BasicPathHandler());
/*  33:104 */     registerAttribHandler("domain", new BasicDomainHandler());
/*  34:105 */     registerAttribHandler("max-age", new BasicMaxAgeHandler());
/*  35:106 */     registerAttribHandler("secure", new BasicSecureHandler());
/*  36:107 */     registerAttribHandler("comment", new BasicCommentHandler());
/*  37:108 */     registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public BrowserCompatSpec()
/*  41:    */   {
/*  42:114 */     this(null);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*  46:    */     throws MalformedCookieException
/*  47:    */   {
/*  48:119 */     if (header == null) {
/*  49:120 */       throw new IllegalArgumentException("Header may not be null");
/*  50:    */     }
/*  51:122 */     if (origin == null) {
/*  52:123 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  53:    */     }
/*  54:125 */     String headername = header.getName();
/*  55:126 */     if (!headername.equalsIgnoreCase("Set-Cookie")) {
/*  56:127 */       throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
/*  57:    */     }
/*  58:130 */     HeaderElement[] helems = header.getElements();
/*  59:131 */     boolean versioned = false;
/*  60:132 */     boolean netscape = false;
/*  61:133 */     for (HeaderElement helem : helems)
/*  62:    */     {
/*  63:134 */       if (helem.getParameterByName("version") != null) {
/*  64:135 */         versioned = true;
/*  65:    */       }
/*  66:137 */       if (helem.getParameterByName("expires") != null) {
/*  67:138 */         netscape = true;
/*  68:    */       }
/*  69:    */     }
/*  70:141 */     if ((netscape) || (!versioned))
/*  71:    */     {
/*  72:144 */       NetscapeDraftHeaderParser parser = NetscapeDraftHeaderParser.DEFAULT;
/*  73:    */       ParserCursor cursor;
/*  74:    */       CharArrayBuffer buffer;
/*  75:    */       ParserCursor cursor;
/*  76:147 */       if ((header instanceof FormattedHeader))
/*  77:    */       {
/*  78:148 */         CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/*  79:149 */         cursor = new ParserCursor(((FormattedHeader)header).getValuePos(), buffer.length());
/*  80:    */       }
/*  81:    */       else
/*  82:    */       {
/*  83:153 */         String s = header.getValue();
/*  84:154 */         if (s == null) {
/*  85:155 */           throw new MalformedCookieException("Header value is null");
/*  86:    */         }
/*  87:157 */         buffer = new CharArrayBuffer(s.length());
/*  88:158 */         buffer.append(s);
/*  89:159 */         cursor = new ParserCursor(0, buffer.length());
/*  90:    */       }
/*  91:161 */       helems = new HeaderElement[] { parser.parseHeader(buffer, cursor) };
/*  92:    */     }
/*  93:163 */     return parse(helems, origin);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List<Header> formatCookies(List<Cookie> cookies)
/*  97:    */   {
/*  98:167 */     if (cookies == null) {
/*  99:168 */       throw new IllegalArgumentException("List of cookies may not be null");
/* 100:    */     }
/* 101:170 */     if (cookies.isEmpty()) {
/* 102:171 */       throw new IllegalArgumentException("List of cookies may not be empty");
/* 103:    */     }
/* 104:173 */     CharArrayBuffer buffer = new CharArrayBuffer(20 * cookies.size());
/* 105:174 */     buffer.append("Cookie");
/* 106:175 */     buffer.append(": ");
/* 107:176 */     for (int i = 0; i < cookies.size(); i++)
/* 108:    */     {
/* 109:177 */       Cookie cookie = (Cookie)cookies.get(i);
/* 110:178 */       if (i > 0) {
/* 111:179 */         buffer.append("; ");
/* 112:    */       }
/* 113:181 */       buffer.append(cookie.getName());
/* 114:182 */       buffer.append("=");
/* 115:183 */       String s = cookie.getValue();
/* 116:184 */       if (s != null) {
/* 117:185 */         buffer.append(s);
/* 118:    */       }
/* 119:    */     }
/* 120:188 */     List<Header> headers = new ArrayList(1);
/* 121:189 */     headers.add(new BufferedHeader(buffer));
/* 122:190 */     return headers;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getVersion()
/* 126:    */   {
/* 127:194 */     return 0;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Header getVersionHeader()
/* 131:    */   {
/* 132:198 */     return null;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String toString()
/* 136:    */   {
/* 137:203 */     return "compatibility";
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BrowserCompatSpec
 * JD-Core Version:    0.7.0.1
 */