/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.net.MalformedURLException;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.util.Map;
/*  11:    */ 
/*  12:    */ public class HtmlLink
/*  13:    */   extends HtmlElement
/*  14:    */ {
/*  15:    */   public static final String TAG_NAME = "link";
/*  16:    */   private WebResponse cachedWebResponse_;
/*  17:    */   
/*  18:    */   HtmlLink(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  19:    */   {
/*  20: 54 */     super(namespaceURI, qualifiedName, page, attributes);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public final String getCharsetAttribute()
/*  24:    */   {
/*  25: 66 */     return getAttribute("charset");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public final String getHrefAttribute()
/*  29:    */   {
/*  30: 78 */     return getAttribute("href");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public final String getHrefLangAttribute()
/*  34:    */   {
/*  35: 90 */     return getAttribute("hreflang");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public final String getTypeAttribute()
/*  39:    */   {
/*  40:102 */     return getAttribute("type");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final String getRelAttribute()
/*  44:    */   {
/*  45:114 */     return getAttribute("rel");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final String getRevAttribute()
/*  49:    */   {
/*  50:126 */     return getAttribute("rev");
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final String getMediaAttribute()
/*  54:    */   {
/*  55:138 */     return getAttribute("media");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final String getTargetAttribute()
/*  59:    */   {
/*  60:150 */     return getAttribute("target");
/*  61:    */   }
/*  62:    */   
/*  63:    */   public WebResponse getWebResponse(boolean downloadIfNeeded)
/*  64:    */     throws IOException
/*  65:    */   {
/*  66:164 */     if ((downloadIfNeeded) && (this.cachedWebResponse_ == null))
/*  67:    */     {
/*  68:165 */       WebClient webclient = getPage().getWebClient();
/*  69:166 */       this.cachedWebResponse_ = webclient.loadWebResponse(getWebRequest());
/*  70:    */     }
/*  71:168 */     return this.cachedWebResponse_;
/*  72:    */   }
/*  73:    */   
/*  74:    */   @Deprecated
/*  75:    */   public WebRequest getWebRequestSettings()
/*  76:    */     throws MalformedURLException
/*  77:    */   {
/*  78:179 */     return getWebRequest();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public WebRequest getWebRequest()
/*  82:    */     throws MalformedURLException
/*  83:    */   {
/*  84:188 */     HtmlPage page = (HtmlPage)getPage();
/*  85:189 */     URL url = page.getFullyQualifiedUrl(getHrefAttribute());
/*  86:190 */     WebRequest request = new WebRequest(url);
/*  87:191 */     request.setAdditionalHeader("Referer", page.getWebResponse().getWebRequest().getUrl().toExternalForm());
/*  88:192 */     return request;
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlLink
 * JD-Core Version:    0.7.0.1
 */