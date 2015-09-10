/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ import org.apache.http.cookie.ClientCookie;
/*   6:    */ import org.apache.http.cookie.Cookie;
/*   7:    */ import org.apache.http.cookie.CookieAttributeHandler;
/*   8:    */ import org.apache.http.cookie.CookieOrigin;
/*   9:    */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*  10:    */ import org.apache.http.cookie.MalformedCookieException;
/*  11:    */ import org.apache.http.cookie.SetCookie;
/*  12:    */ 
/*  13:    */ @Immutable
/*  14:    */ public class RFC2965DomainAttributeHandler
/*  15:    */   implements CookieAttributeHandler
/*  16:    */ {
/*  17:    */   public void parse(SetCookie cookie, String domain)
/*  18:    */     throws MalformedCookieException
/*  19:    */   {
/*  20: 60 */     if (cookie == null) {
/*  21: 61 */       throw new IllegalArgumentException("Cookie may not be null");
/*  22:    */     }
/*  23: 63 */     if (domain == null) {
/*  24: 64 */       throw new MalformedCookieException("Missing value for domain attribute");
/*  25:    */     }
/*  26: 67 */     if (domain.trim().length() == 0) {
/*  27: 68 */       throw new MalformedCookieException("Blank value for domain attribute");
/*  28:    */     }
/*  29: 71 */     domain = domain.toLowerCase(Locale.ENGLISH);
/*  30: 72 */     if (!domain.startsWith(".")) {
/*  31: 78 */       domain = '.' + domain;
/*  32:    */     }
/*  33: 80 */     cookie.setDomain(domain);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean domainMatch(String host, String domain)
/*  37:    */   {
/*  38: 99 */     boolean match = (host.equals(domain)) || ((domain.startsWith(".")) && (host.endsWith(domain)));
/*  39:    */     
/*  40:    */ 
/*  41:102 */     return match;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void validate(Cookie cookie, CookieOrigin origin)
/*  45:    */     throws MalformedCookieException
/*  46:    */   {
/*  47:110 */     if (cookie == null) {
/*  48:111 */       throw new IllegalArgumentException("Cookie may not be null");
/*  49:    */     }
/*  50:113 */     if (origin == null) {
/*  51:114 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  52:    */     }
/*  53:116 */     String host = origin.getHost().toLowerCase(Locale.ENGLISH);
/*  54:117 */     if (cookie.getDomain() == null) {
/*  55:118 */       throw new CookieRestrictionViolationException("Invalid cookie state: domain not specified");
/*  56:    */     }
/*  57:121 */     String cookieDomain = cookie.getDomain().toLowerCase(Locale.ENGLISH);
/*  58:123 */     if (((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("domain")))
/*  59:    */     {
/*  60:126 */       if (!cookieDomain.startsWith(".")) {
/*  61:127 */         throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2109: domain must start with a dot");
/*  62:    */       }
/*  63:133 */       int dotIndex = cookieDomain.indexOf('.', 1);
/*  64:134 */       if (((dotIndex < 0) || (dotIndex == cookieDomain.length() - 1)) && (!cookieDomain.equals(".local"))) {
/*  65:136 */         throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: the value contains no embedded dots " + "and the value is not .local");
/*  66:    */       }
/*  67:143 */       if (!domainMatch(host, cookieDomain)) {
/*  68:144 */         throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: effective host name does not " + "domain-match domain attribute.");
/*  69:    */       }
/*  70:151 */       String effectiveHostWithoutDomain = host.substring(0, host.length() - cookieDomain.length());
/*  71:153 */       if (effectiveHostWithoutDomain.indexOf('.') != -1) {
/*  72:154 */         throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: " + "effective host minus domain may not contain any dots");
/*  73:    */       }
/*  74:    */     }
/*  75:161 */     else if (!cookie.getDomain().equals(host))
/*  76:    */     {
/*  77:162 */       throw new CookieRestrictionViolationException("Illegal domain attribute: \"" + cookie.getDomain() + "\"." + "Domain of origin: \"" + host + "\"");
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*  82:    */   {
/*  83:174 */     if (cookie == null) {
/*  84:175 */       throw new IllegalArgumentException("Cookie may not be null");
/*  85:    */     }
/*  86:177 */     if (origin == null) {
/*  87:178 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  88:    */     }
/*  89:180 */     String host = origin.getHost().toLowerCase(Locale.ENGLISH);
/*  90:181 */     String cookieDomain = cookie.getDomain();
/*  91:185 */     if (!domainMatch(host, cookieDomain)) {
/*  92:186 */       return false;
/*  93:    */     }
/*  94:189 */     String effectiveHostWithoutDomain = host.substring(0, host.length() - cookieDomain.length());
/*  95:    */     
/*  96:191 */     return effectiveHostWithoutDomain.indexOf('.') == -1;
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.RFC2965DomainAttributeHandler
 * JD-Core Version:    0.7.0.1
 */