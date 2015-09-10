/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.apache.http.client.utils.Punycode;
/*   7:    */ import org.apache.http.cookie.Cookie;
/*   8:    */ import org.apache.http.cookie.CookieAttributeHandler;
/*   9:    */ import org.apache.http.cookie.CookieOrigin;
/*  10:    */ import org.apache.http.cookie.MalformedCookieException;
/*  11:    */ import org.apache.http.cookie.SetCookie;
/*  12:    */ 
/*  13:    */ public class PublicSuffixFilter
/*  14:    */   implements CookieAttributeHandler
/*  15:    */ {
/*  16:    */   private final CookieAttributeHandler wrapped;
/*  17:    */   private Set<String> exceptions;
/*  18:    */   private Set<String> suffixes;
/*  19:    */   
/*  20:    */   public PublicSuffixFilter(CookieAttributeHandler wrapped)
/*  21:    */   {
/*  22: 61 */     this.wrapped = wrapped;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setPublicSuffixes(Collection<String> suffixes)
/*  26:    */   {
/*  27: 71 */     this.suffixes = new HashSet(suffixes);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setExceptions(Collection<String> exceptions)
/*  31:    */   {
/*  32: 80 */     this.exceptions = new HashSet(exceptions);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*  36:    */   {
/*  37: 87 */     if (isForPublicSuffix(cookie)) {
/*  38: 87 */       return false;
/*  39:    */     }
/*  40: 88 */     return this.wrapped.match(cookie, origin);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void parse(SetCookie cookie, String value)
/*  44:    */     throws MalformedCookieException
/*  45:    */   {
/*  46: 92 */     this.wrapped.parse(cookie, value);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void validate(Cookie cookie, CookieOrigin origin)
/*  50:    */     throws MalformedCookieException
/*  51:    */   {
/*  52: 96 */     this.wrapped.validate(cookie, origin);
/*  53:    */   }
/*  54:    */   
/*  55:    */   private boolean isForPublicSuffix(Cookie cookie)
/*  56:    */   {
/*  57:100 */     String domain = cookie.getDomain();
/*  58:101 */     if (domain.startsWith(".")) {
/*  59:101 */       domain = domain.substring(1);
/*  60:    */     }
/*  61:102 */     domain = Punycode.toUnicode(domain);
/*  62:105 */     if ((this.exceptions != null) && 
/*  63:106 */       (this.exceptions.contains(domain))) {
/*  64:106 */       return false;
/*  65:    */     }
/*  66:110 */     if (this.suffixes == null) {
/*  67:110 */       return false;
/*  68:    */     }
/*  69:    */     do
/*  70:    */     {
/*  71:113 */       if (this.suffixes.contains(domain)) {
/*  72:113 */         return true;
/*  73:    */       }
/*  74:115 */       if (domain.startsWith("*.")) {
/*  75:115 */         domain = domain.substring(2);
/*  76:    */       }
/*  77:116 */       int nextdot = domain.indexOf('.');
/*  78:117 */       if (nextdot == -1) {
/*  79:    */         break;
/*  80:    */       }
/*  81:118 */       domain = "*" + domain.substring(nextdot);
/*  82:119 */     } while (domain.length() > 0);
/*  83:121 */     return false;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.PublicSuffixFilter
 * JD-Core Version:    0.7.0.1
 */