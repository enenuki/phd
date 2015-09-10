/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ import org.apache.http.cookie.Cookie;
/*   6:    */ import org.apache.http.cookie.CookieAttributeHandler;
/*   7:    */ import org.apache.http.cookie.CookieOrigin;
/*   8:    */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*   9:    */ import org.apache.http.cookie.MalformedCookieException;
/*  10:    */ import org.apache.http.cookie.SetCookie;
/*  11:    */ 
/*  12:    */ @Immutable
/*  13:    */ public class RFC2109DomainHandler
/*  14:    */   implements CookieAttributeHandler
/*  15:    */ {
/*  16:    */   public void parse(SetCookie cookie, String value)
/*  17:    */     throws MalformedCookieException
/*  18:    */   {
/*  19: 53 */     if (cookie == null) {
/*  20: 54 */       throw new IllegalArgumentException("Cookie may not be null");
/*  21:    */     }
/*  22: 56 */     if (value == null) {
/*  23: 57 */       throw new MalformedCookieException("Missing value for domain attribute");
/*  24:    */     }
/*  25: 59 */     if (value.trim().length() == 0) {
/*  26: 60 */       throw new MalformedCookieException("Blank value for domain attribute");
/*  27:    */     }
/*  28: 62 */     cookie.setDomain(value);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void validate(Cookie cookie, CookieOrigin origin)
/*  32:    */     throws MalformedCookieException
/*  33:    */   {
/*  34: 67 */     if (cookie == null) {
/*  35: 68 */       throw new IllegalArgumentException("Cookie may not be null");
/*  36:    */     }
/*  37: 70 */     if (origin == null) {
/*  38: 71 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  39:    */     }
/*  40: 73 */     String host = origin.getHost();
/*  41: 74 */     String domain = cookie.getDomain();
/*  42: 75 */     if (domain == null) {
/*  43: 76 */       throw new CookieRestrictionViolationException("Cookie domain may not be null");
/*  44:    */     }
/*  45: 78 */     if (!domain.equals(host))
/*  46:    */     {
/*  47: 79 */       int dotIndex = domain.indexOf('.');
/*  48: 80 */       if (dotIndex == -1) {
/*  49: 81 */         throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" does not match the host \"" + host + "\"");
/*  50:    */       }
/*  51: 87 */       if (!domain.startsWith(".")) {
/*  52: 88 */         throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must start with a dot");
/*  53:    */       }
/*  54: 93 */       dotIndex = domain.indexOf('.', 1);
/*  55: 94 */       if ((dotIndex < 0) || (dotIndex == domain.length() - 1)) {
/*  56: 95 */         throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must contain an embedded dot");
/*  57:    */       }
/*  58: 99 */       host = host.toLowerCase(Locale.ENGLISH);
/*  59:100 */       if (!host.endsWith(domain)) {
/*  60:101 */         throw new CookieRestrictionViolationException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
/*  61:    */       }
/*  62:106 */       String hostWithoutDomain = host.substring(0, host.length() - domain.length());
/*  63:107 */       if (hostWithoutDomain.indexOf('.') != -1) {
/*  64:108 */         throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: host minus domain may not contain any dots");
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*  70:    */   {
/*  71:116 */     if (cookie == null) {
/*  72:117 */       throw new IllegalArgumentException("Cookie may not be null");
/*  73:    */     }
/*  74:119 */     if (origin == null) {
/*  75:120 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  76:    */     }
/*  77:122 */     String host = origin.getHost();
/*  78:123 */     String domain = cookie.getDomain();
/*  79:124 */     if (domain == null) {
/*  80:125 */       return false;
/*  81:    */     }
/*  82:127 */     return (host.equals(domain)) || ((domain.startsWith(".")) && (host.endsWith(domain)));
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.RFC2109DomainHandler
 * JD-Core Version:    0.7.0.1
 */