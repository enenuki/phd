/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.CookieManager;
/*   4:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   8:    */ import com.gargoylesoftware.htmlunit.util.Cookie;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.regex.Pattern;
/*  13:    */ 
/*  14:    */ public class HtmlMeta
/*  15:    */   extends HtmlElement
/*  16:    */ {
/*  17: 36 */   private static final Pattern COOKIES_SPLIT_PATTERN = Pattern.compile("\\s*;\\s*");
/*  18:    */   public static final String TAG_NAME = "meta";
/*  19:    */   
/*  20:    */   HtmlMeta(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  21:    */   {
/*  22: 51 */     super(namespaceURI, qualifiedName, page, attributes);
/*  23: 53 */     if ("set-cookie".equalsIgnoreCase(getHttpEquivAttribute())) {
/*  24: 54 */       performSetCookie();
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected void performSetCookie()
/*  29:    */   {
/*  30: 63 */     String[] parts = COOKIES_SPLIT_PATTERN.split(getContentAttribute(), 0);
/*  31: 64 */     String name = org.apache.commons.lang.StringUtils.substringBefore(parts[0], "=");
/*  32: 65 */     String value = org.apache.commons.lang.StringUtils.substringAfter(parts[0], "=");
/*  33: 66 */     URL url = getPage().getWebResponse().getWebRequest().getUrl();
/*  34: 67 */     String host = url.getHost();
/*  35: 68 */     boolean secure = "https".equals(url.getProtocol());
/*  36: 69 */     String path = null;
/*  37: 70 */     Date expires = null;
/*  38: 71 */     for (int i = 1; i < parts.length; i++)
/*  39:    */     {
/*  40: 72 */       String partName = org.apache.commons.lang.StringUtils.substringBefore(parts[i], "=").trim().toLowerCase();
/*  41: 73 */       String partValue = org.apache.commons.lang.StringUtils.substringAfter(parts[i], "=").trim();
/*  42: 74 */       if ("path".equals(partName)) {
/*  43: 75 */         path = partValue;
/*  44: 77 */       } else if ("expires".equals(partName)) {
/*  45: 78 */         expires = com.gargoylesoftware.htmlunit.util.StringUtils.parseHttpDate(partValue);
/*  46:    */       } else {
/*  47: 81 */         notifyIncorrectness("set-cookie http-equiv meta tag: unknown attribute '" + partName + "'.");
/*  48:    */       }
/*  49:    */     }
/*  50: 84 */     Cookie cookie = new Cookie(host, name, value, path, expires, secure);
/*  51: 85 */     getPage().getWebClient().getCookieManager().addCookie(cookie);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean mayBeDisplayed()
/*  55:    */   {
/*  56: 93 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final String getHttpEquivAttribute()
/*  60:    */   {
/*  61:105 */     return getAttribute("http-equiv");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final String getNameAttribute()
/*  65:    */   {
/*  66:117 */     return getAttribute("name");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public final String getContentAttribute()
/*  70:    */   {
/*  71:129 */     return getAttribute("content");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final String getSchemeAttribute()
/*  75:    */   {
/*  76:141 */     return getAttribute("scheme");
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlMeta
 * JD-Core Version:    0.7.0.1
 */