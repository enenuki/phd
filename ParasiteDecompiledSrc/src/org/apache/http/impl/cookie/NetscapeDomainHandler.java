/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import java.util.StringTokenizer;
/*   5:    */ import org.apache.http.annotation.Immutable;
/*   6:    */ import org.apache.http.cookie.Cookie;
/*   7:    */ import org.apache.http.cookie.CookieOrigin;
/*   8:    */ import org.apache.http.cookie.CookieRestrictionViolationException;
/*   9:    */ import org.apache.http.cookie.MalformedCookieException;
/*  10:    */ 
/*  11:    */ @Immutable
/*  12:    */ public class NetscapeDomainHandler
/*  13:    */   extends BasicDomainHandler
/*  14:    */ {
/*  15:    */   public void validate(Cookie cookie, CookieOrigin origin)
/*  16:    */     throws MalformedCookieException
/*  17:    */   {
/*  18: 53 */     super.validate(cookie, origin);
/*  19:    */     
/*  20: 55 */     String host = origin.getHost();
/*  21: 56 */     String domain = cookie.getDomain();
/*  22: 57 */     if (host.contains("."))
/*  23:    */     {
/*  24: 58 */       int domainParts = new StringTokenizer(domain, ".").countTokens();
/*  25: 60 */       if (isSpecialDomain(domain))
/*  26:    */       {
/*  27: 61 */         if (domainParts < 2) {
/*  28: 62 */           throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification for " + "special domains");
/*  29:    */         }
/*  30:    */       }
/*  31: 68 */       else if (domainParts < 3) {
/*  32: 69 */         throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification");
/*  33:    */       }
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   private static boolean isSpecialDomain(String domain)
/*  38:    */   {
/*  39: 84 */     String ucDomain = domain.toUpperCase(Locale.ENGLISH);
/*  40: 85 */     return (ucDomain.endsWith(".COM")) || (ucDomain.endsWith(".EDU")) || (ucDomain.endsWith(".NET")) || (ucDomain.endsWith(".GOV")) || (ucDomain.endsWith(".MIL")) || (ucDomain.endsWith(".ORG")) || (ucDomain.endsWith(".INT"));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*  44:    */   {
/*  45: 96 */     if (cookie == null) {
/*  46: 97 */       throw new IllegalArgumentException("Cookie may not be null");
/*  47:    */     }
/*  48: 99 */     if (origin == null) {
/*  49:100 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*  50:    */     }
/*  51:102 */     String host = origin.getHost();
/*  52:103 */     String domain = cookie.getDomain();
/*  53:104 */     if (domain == null) {
/*  54:105 */       return false;
/*  55:    */     }
/*  56:107 */     return host.endsWith(domain);
/*  57:    */   }
/*  58:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.NetscapeDomainHandler
 * JD-Core Version:    0.7.0.1
 */