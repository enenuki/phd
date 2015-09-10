/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.http.Header;
/*   7:    */ import org.apache.http.cookie.Cookie;
/*   8:    */ import org.apache.http.cookie.CookieOrigin;
/*   9:    */ import org.apache.http.cookie.CookiePathComparator;
/*  10:    */ import org.apache.http.cookie.MalformedCookieException;
/*  11:    */ import org.apache.http.impl.cookie.BasicClientCookie;
/*  12:    */ import org.apache.http.impl.cookie.BasicPathHandler;
/*  13:    */ import org.apache.http.impl.cookie.BrowserCompatSpec;
/*  14:    */ 
/*  15:    */ class HtmlUnitBrowserCompatCookieSpec
/*  16:    */   extends BrowserCompatSpec
/*  17:    */ {
/*  18:684 */   private static final Comparator<Cookie> COOKIE_COMPARATOR = new CookiePathComparator();
/*  19:    */   
/*  20:    */   HtmlUnitBrowserCompatCookieSpec()
/*  21:    */   {
/*  22:688 */     BasicPathHandler pathHandler = new BasicPathHandler()
/*  23:    */     {
/*  24:    */       public void validate(Cookie cookie, CookieOrigin origin)
/*  25:    */         throws MalformedCookieException
/*  26:    */       {}
/*  27:693 */     };
/*  28:694 */     registerAttribHandler("path", pathHandler);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*  32:    */     throws MalformedCookieException
/*  33:    */   {
/*  34:702 */     List<Cookie> cookies = super.parse(header, origin);
/*  35:703 */     for (Cookie c : cookies) {
/*  36:705 */       if (header.getValue().contains(c.getName() + "=\"" + c.getValue())) {
/*  37:706 */         ((BasicClientCookie)c).setValue('"' + c.getValue() + '"');
/*  38:    */       }
/*  39:    */     }
/*  40:709 */     return cookies;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<Header> formatCookies(List<Cookie> cookies)
/*  44:    */   {
/*  45:714 */     Collections.sort(cookies, COOKIE_COMPARATOR);
/*  46:    */     
/*  47:716 */     return super.formatCookies(cookies);
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.HtmlUnitBrowserCompatCookieSpec
 * JD-Core Version:    0.7.0.1
 */