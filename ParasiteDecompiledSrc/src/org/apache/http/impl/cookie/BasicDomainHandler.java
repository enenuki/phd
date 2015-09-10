/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import org.apache.http.annotation.Immutable;
/*   4:    */ import org.apache.http.cookie.Cookie;
/*   5:    */ import org.apache.http.cookie.CookieAttributeHandler;
/*   6:    */ import org.apache.http.cookie.CookieOrigin;
/*   7:    */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*   8:    */ import org.apache.http.cookie.MalformedCookieException;
/*   9:    */ import org.apache.http.cookie.SetCookie;
/*  10:    */ 
/*  11:    */ @Immutable
/*  12:    */ public class BasicDomainHandler
/*  13:    */   implements CookieAttributeHandler
/*  14:    */ {
/*  15:    */   public void parse(SetCookie cookie, String value)
/*  16:    */     throws MalformedCookieException
/*  17:    */   {
/*  18: 51 */     if (cookie == null) {
/*  19: 52 */       throw new IllegalArgumentException("Cookie may not be null");
/*  20:    */     }
/*  21: 54 */     if (value == null) {
/*  22: 55 */       throw new MalformedCookieException("Missing value for domain attribute");
/*  23:    */     }
/*  24: 57 */     if (value.trim().length() == 0) {
/*  25: 58 */       throw new MalformedCookieException("Blank value for domain attribute");
/*  26:    */     }
/*  27: 60 */     cookie.setDomain(value);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void validate(Cookie cookie, CookieOrigin origin)
/*  31:    */     throws MalformedCookieException
/*  32:    */   {
/*  33: 65 */     if (cookie == null) {
/*  34: 66 */       throw new IllegalArgumentException("Cookie may not be null");
/*  35:    */     }
/*  36: 68 */     if (origin == null) {
/*  37: 69 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  38:    */     }
/*  39: 76 */     String host = origin.getHost();
/*  40: 77 */     String domain = cookie.getDomain();
/*  41: 78 */     if (domain == null) {
/*  42: 79 */       throw new CookieRestrictionViolationException("Cookie domain may not be null");
/*  43:    */     }
/*  44: 81 */     if (host.contains("."))
/*  45:    */     {
/*  46: 86 */       if (!host.endsWith(domain))
/*  47:    */       {
/*  48: 87 */         if (domain.startsWith(".")) {
/*  49: 88 */           domain = domain.substring(1, domain.length());
/*  50:    */         }
/*  51: 90 */         if (!host.equals(domain)) {
/*  52: 91 */           throw new CookieRestrictionViolationException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
/*  53:    */         }
/*  54:    */       }
/*  55:    */     }
/*  56: 97 */     else if (!host.equals(domain)) {
/*  57: 98 */       throw new CookieRestrictionViolationException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*  62:    */   {
/*  63:106 */     if (cookie == null) {
/*  64:107 */       throw new IllegalArgumentException("Cookie may not be null");
/*  65:    */     }
/*  66:109 */     if (origin == null) {
/*  67:110 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  68:    */     }
/*  69:112 */     String host = origin.getHost();
/*  70:113 */     String domain = cookie.getDomain();
/*  71:114 */     if (domain == null) {
/*  72:115 */       return false;
/*  73:    */     }
/*  74:117 */     if (host.equals(domain)) {
/*  75:118 */       return true;
/*  76:    */     }
/*  77:120 */     if (!domain.startsWith(".")) {
/*  78:121 */       domain = '.' + domain;
/*  79:    */     }
/*  80:123 */     return (host.endsWith(domain)) || (host.equals(domain.substring(1)));
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BasicDomainHandler
 * JD-Core Version:    0.7.0.1
 */