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
/*  17:    */ public class NetscapeDraftSpec
/*  18:    */   extends CookieSpecBase
/*  19:    */ {
/*  20:    */   protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yy HH:mm:ss z";
/*  21:    */   private final String[] datepatterns;
/*  22:    */   
/*  23:    */   public NetscapeDraftSpec(String[] datepatterns)
/*  24:    */   {
/*  25: 65 */     if (datepatterns != null) {
/*  26: 66 */       this.datepatterns = ((String[])datepatterns.clone());
/*  27:    */     } else {
/*  28: 68 */       this.datepatterns = new String[] { "EEE, dd-MMM-yy HH:mm:ss z" };
/*  29:    */     }
/*  30: 70 */     registerAttribHandler("path", new BasicPathHandler());
/*  31: 71 */     registerAttribHandler("domain", new NetscapeDomainHandler());
/*  32: 72 */     registerAttribHandler("max-age", new BasicMaxAgeHandler());
/*  33: 73 */     registerAttribHandler("secure", new BasicSecureHandler());
/*  34: 74 */     registerAttribHandler("comment", new BasicCommentHandler());
/*  35: 75 */     registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public NetscapeDraftSpec()
/*  39:    */   {
/*  40: 81 */     this(null);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*  44:    */     throws MalformedCookieException
/*  45:    */   {
/*  46:110 */     if (header == null) {
/*  47:111 */       throw new IllegalArgumentException("Header may not be null");
/*  48:    */     }
/*  49:113 */     if (origin == null) {
/*  50:114 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  51:    */     }
/*  52:116 */     if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
/*  53:117 */       throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
/*  54:    */     }
/*  55:120 */     NetscapeDraftHeaderParser parser = NetscapeDraftHeaderParser.DEFAULT;
/*  56:    */     ParserCursor cursor;
/*  57:    */     CharArrayBuffer buffer;
/*  58:    */     ParserCursor cursor;
/*  59:123 */     if ((header instanceof FormattedHeader))
/*  60:    */     {
/*  61:124 */       CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/*  62:125 */       cursor = new ParserCursor(((FormattedHeader)header).getValuePos(), buffer.length());
/*  63:    */     }
/*  64:    */     else
/*  65:    */     {
/*  66:129 */       String s = header.getValue();
/*  67:130 */       if (s == null) {
/*  68:131 */         throw new MalformedCookieException("Header value is null");
/*  69:    */       }
/*  70:133 */       buffer = new CharArrayBuffer(s.length());
/*  71:134 */       buffer.append(s);
/*  72:135 */       cursor = new ParserCursor(0, buffer.length());
/*  73:    */     }
/*  74:137 */     return parse(new HeaderElement[] { parser.parseHeader(buffer, cursor) }, origin);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<Header> formatCookies(List<Cookie> cookies)
/*  78:    */   {
/*  79:141 */     if (cookies == null) {
/*  80:142 */       throw new IllegalArgumentException("List of cookies may not be null");
/*  81:    */     }
/*  82:144 */     if (cookies.isEmpty()) {
/*  83:145 */       throw new IllegalArgumentException("List of cookies may not be empty");
/*  84:    */     }
/*  85:147 */     CharArrayBuffer buffer = new CharArrayBuffer(20 * cookies.size());
/*  86:148 */     buffer.append("Cookie");
/*  87:149 */     buffer.append(": ");
/*  88:150 */     for (int i = 0; i < cookies.size(); i++)
/*  89:    */     {
/*  90:151 */       Cookie cookie = (Cookie)cookies.get(i);
/*  91:152 */       if (i > 0) {
/*  92:153 */         buffer.append("; ");
/*  93:    */       }
/*  94:155 */       buffer.append(cookie.getName());
/*  95:156 */       String s = cookie.getValue();
/*  96:157 */       if (s != null)
/*  97:    */       {
/*  98:158 */         buffer.append("=");
/*  99:159 */         buffer.append(s);
/* 100:    */       }
/* 101:    */     }
/* 102:162 */     List<Header> headers = new ArrayList(1);
/* 103:163 */     headers.add(new BufferedHeader(buffer));
/* 104:164 */     return headers;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getVersion()
/* 108:    */   {
/* 109:168 */     return 0;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Header getVersionHeader()
/* 113:    */   {
/* 114:172 */     return null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String toString()
/* 118:    */   {
/* 119:177 */     return "netscape";
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.NetscapeDraftSpec
 * JD-Core Version:    0.7.0.1
 */