/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.Page;
/*   6:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  11:    */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.net.MalformedURLException;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.util.Map;
/*  16:    */ import org.apache.commons.lang.StringUtils;
/*  17:    */ import org.apache.commons.logging.Log;
/*  18:    */ import org.apache.commons.logging.LogFactory;
/*  19:    */ 
/*  20:    */ public class HtmlAnchor
/*  21:    */   extends HtmlElement
/*  22:    */ {
/*  23: 46 */   private static final Log LOG = LogFactory.getLog(HtmlAnchor.class);
/*  24:    */   public static final String TAG_NAME = "a";
/*  25:    */   
/*  26:    */   HtmlAnchor(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  27:    */   {
/*  28: 61 */     super(namespaceURI, qualifiedName, page, attributes);
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void doClickAction(String hrefSuffix)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 72 */     String href = (getHrefAttribute() + hrefSuffix).trim();
/*  35: 73 */     if (LOG.isDebugEnabled())
/*  36:    */     {
/*  37: 74 */       String w = getPage().getEnclosingWindow().getName();
/*  38: 75 */       LOG.debug("do click action in window '" + w + "', using href '" + href + "'");
/*  39:    */     }
/*  40: 77 */     if (ATTRIBUTE_NOT_DEFINED == getHrefAttribute()) {
/*  41: 78 */       return;
/*  42:    */     }
/*  43: 80 */     HtmlPage page = (HtmlPage)getPage();
/*  44: 81 */     if (StringUtils.startsWithIgnoreCase(href, "javascript:"))
/*  45:    */     {
/*  46: 82 */       StringBuilder builder = new StringBuilder(href.length());
/*  47: 83 */       builder.append("javascript:");
/*  48: 84 */       for (int i = "javascript:".length(); i < href.length(); i++)
/*  49:    */       {
/*  50: 85 */         char ch = href.charAt(i);
/*  51: 86 */         if ((ch == '%') && (i + 2 < href.length()))
/*  52:    */         {
/*  53: 87 */           char ch1 = Character.toUpperCase(href.charAt(i + 1));
/*  54: 88 */           char ch2 = Character.toUpperCase(href.charAt(i + 2));
/*  55: 89 */           if (((Character.isDigit(ch1)) || ((ch1 >= 'A') && (ch1 <= 'F'))) && ((Character.isDigit(ch2)) || ((ch2 >= 'A') && (ch2 <= 'F'))))
/*  56:    */           {
/*  57: 91 */             builder.append((char)Integer.parseInt(href.substring(i + 1, i + 3), 16));
/*  58: 92 */             i += 2;
/*  59: 93 */             continue;
/*  60:    */           }
/*  61:    */         }
/*  62: 96 */         builder.append(ch);
/*  63:    */       }
/*  64: 98 */       page.executeJavaScriptIfPossible(builder.toString(), "javascript url", getStartLineNumber());
/*  65: 99 */       return;
/*  66:    */     }
/*  67:101 */     URL url = page.getFullyQualifiedUrl(href);
/*  68:103 */     if (StringUtils.isEmpty(href))
/*  69:    */     {
/*  70:104 */       boolean dropFilename = page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.ANCHOR_EMPTY_HREF_NO_FILENAME);
/*  71:106 */       if (dropFilename)
/*  72:    */       {
/*  73:107 */         String path = url.getPath();
/*  74:108 */         path = path.substring(0, path.lastIndexOf('/') + 1);
/*  75:109 */         url = UrlUtils.getUrlWithNewPath(url, path);
/*  76:110 */         url = UrlUtils.getUrlWithNewRef(url, null);
/*  77:    */       }
/*  78:    */       else
/*  79:    */       {
/*  80:113 */         url = UrlUtils.getUrlWithNewRef(url, null);
/*  81:    */       }
/*  82:    */     }
/*  83:117 */     WebRequest webRequest = new WebRequest(url);
/*  84:118 */     webRequest.setAdditionalHeader("Referer", page.getWebResponse().getWebRequest().getUrl().toExternalForm());
/*  85:119 */     if (LOG.isDebugEnabled()) {
/*  86:120 */       LOG.debug("Getting page for " + url.toExternalForm() + ", derived from href '" + href + "', using the originating URL " + page.getWebResponse().getWebRequest().getUrl());
/*  87:    */     }
/*  88:126 */     page.getWebClient().download(page.getEnclosingWindow(), page.getResolvedTarget(getTargetAttribute()), webRequest, href.endsWith("#"), "Link click");
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void doClickAction()
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:141 */     doClickAction("");
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final String getCharsetAttribute()
/*  98:    */   {
/*  99:152 */     return getAttribute("charset");
/* 100:    */   }
/* 101:    */   
/* 102:    */   public final String getTypeAttribute()
/* 103:    */   {
/* 104:163 */     return getAttribute("type");
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final String getNameAttribute()
/* 108:    */   {
/* 109:174 */     return getAttribute("name");
/* 110:    */   }
/* 111:    */   
/* 112:    */   public final String getHrefAttribute()
/* 113:    */   {
/* 114:185 */     return getAttribute("href").trim();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public final String getHrefLangAttribute()
/* 118:    */   {
/* 119:196 */     return getAttribute("hreflang");
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final String getRelAttribute()
/* 123:    */   {
/* 124:207 */     return getAttribute("rel");
/* 125:    */   }
/* 126:    */   
/* 127:    */   public final String getRevAttribute()
/* 128:    */   {
/* 129:218 */     return getAttribute("rev");
/* 130:    */   }
/* 131:    */   
/* 132:    */   public final String getAccessKeyAttribute()
/* 133:    */   {
/* 134:229 */     return getAttribute("accesskey");
/* 135:    */   }
/* 136:    */   
/* 137:    */   public final String getShapeAttribute()
/* 138:    */   {
/* 139:240 */     return getAttribute("shape");
/* 140:    */   }
/* 141:    */   
/* 142:    */   public final String getCoordsAttribute()
/* 143:    */   {
/* 144:251 */     return getAttribute("coords");
/* 145:    */   }
/* 146:    */   
/* 147:    */   public final String getTabIndexAttribute()
/* 148:    */   {
/* 149:262 */     return getAttribute("tabindex");
/* 150:    */   }
/* 151:    */   
/* 152:    */   public final String getOnFocusAttribute()
/* 153:    */   {
/* 154:273 */     return getAttribute("onfocus");
/* 155:    */   }
/* 156:    */   
/* 157:    */   public final String getOnBlurAttribute()
/* 158:    */   {
/* 159:284 */     return getAttribute("onblur");
/* 160:    */   }
/* 161:    */   
/* 162:    */   public final String getTargetAttribute()
/* 163:    */   {
/* 164:295 */     return getAttribute("target");
/* 165:    */   }
/* 166:    */   
/* 167:    */   public final Page openLinkInNewWindow()
/* 168:    */     throws MalformedURLException
/* 169:    */   {
/* 170:310 */     URL target = ((HtmlPage)getPage()).getFullyQualifiedUrl(getHrefAttribute());
/* 171:311 */     String windowName = "HtmlAnchor.openLinkInNewWindow() target";
/* 172:312 */     WebWindow newWindow = getPage().getWebClient().openWindow(target, "HtmlAnchor.openLinkInNewWindow() target");
/* 173:313 */     return newWindow.getEnclosedPage();
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlAnchor
 * JD-Core Version:    0.7.0.1
 */