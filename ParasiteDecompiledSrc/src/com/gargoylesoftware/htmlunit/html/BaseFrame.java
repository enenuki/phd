/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
/*   4:    */ import com.gargoylesoftware.htmlunit.Page;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.PostponedAction;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.net.MalformedURLException;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.util.Map;
/*  16:    */ import org.apache.commons.logging.Log;
/*  17:    */ import org.apache.commons.logging.LogFactory;
/*  18:    */ 
/*  19:    */ public abstract class BaseFrame
/*  20:    */   extends HtmlElement
/*  21:    */ {
/*  22: 50 */   private static final Log LOG = LogFactory.getLog(BaseFrame.class);
/*  23:    */   private final WebWindow enclosedWindow_;
/*  24: 52 */   private boolean contentLoaded_ = false;
/*  25:    */   
/*  26:    */   protected BaseFrame(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  27:    */   {
/*  28: 64 */     super(namespaceURI, qualifiedName, page, attributes);
/*  29:    */     
/*  30: 66 */     WebWindow enclosedWindow = null;
/*  31:    */     try
/*  32:    */     {
/*  33: 68 */       if ((getPage() instanceof HtmlPage))
/*  34:    */       {
/*  35: 69 */         enclosedWindow = new FrameWindow(this);
/*  36:    */         
/*  37:    */ 
/*  38: 72 */         WebClient webClient = getPage().getEnclosingWindow().getWebClient();
/*  39: 73 */         HtmlPage temporaryPage = (HtmlPage)webClient.getPage(enclosedWindow, new WebRequest(WebClient.URL_ABOUT_BLANK));
/*  40:    */         
/*  41: 75 */         temporaryPage.setReadyState("loading");
/*  42:    */       }
/*  43:    */     }
/*  44:    */     catch (FailingHttpStatusCodeException e) {}catch (IOException e) {}
/*  45: 84 */     this.enclosedWindow_ = enclosedWindow;
/*  46:    */   }
/*  47:    */   
/*  48:    */   void loadInnerPage()
/*  49:    */     throws FailingHttpStatusCodeException
/*  50:    */   {
/*  51: 94 */     String source = getSrcAttribute();
/*  52: 95 */     if (source.length() == 0) {
/*  53: 96 */       source = "about:blank";
/*  54:    */     }
/*  55: 99 */     loadInnerPageIfPossible(source);
/*  56:    */     
/*  57:101 */     Page enclosedPage = getEnclosedPage();
/*  58:102 */     if ((enclosedPage instanceof HtmlPage)) {
/*  59:103 */       ((HtmlPage)enclosedPage).setReadyState("complete");
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   boolean isContentLoaded()
/*  64:    */   {
/*  65:114 */     return this.contentLoaded_;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected void setContentLoaded()
/*  69:    */   {
/*  70:123 */     this.contentLoaded_ = true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void loadInnerPageIfPossible(String src)
/*  74:    */     throws FailingHttpStatusCodeException
/*  75:    */   {
/*  76:131 */     setContentLoaded();
/*  77:132 */     if (src.length() != 0)
/*  78:    */     {
/*  79:    */       URL url;
/*  80:    */       try
/*  81:    */       {
/*  82:135 */         url = ((HtmlPage)getPage()).getFullyQualifiedUrl(src);
/*  83:    */       }
/*  84:    */       catch (MalformedURLException e)
/*  85:    */       {
/*  86:138 */         notifyIncorrectness("Invalid src attribute of " + getTagName() + ": url=[" + src + "]. Ignored.");
/*  87:139 */         return;
/*  88:    */       }
/*  89:141 */       if (isAlreadyLoadedByAncestor(url))
/*  90:    */       {
/*  91:142 */         notifyIncorrectness("Recursive src attribute of " + getTagName() + ": url=[" + src + "]. Ignored.");
/*  92:143 */         return;
/*  93:    */       }
/*  94:    */       try
/*  95:    */       {
/*  96:146 */         WebRequest request = new WebRequest(url);
/*  97:147 */         request.setAdditionalHeader("Referer", getPage().getWebResponse().getWebRequest().getUrl().toExternalForm());
/*  98:    */         
/*  99:149 */         getPage().getEnclosingWindow().getWebClient().getPage(this.enclosedWindow_, request);
/* 100:    */       }
/* 101:    */       catch (IOException e)
/* 102:    */       {
/* 103:152 */         LOG.error("IOException when getting content for " + getTagName() + ": url=[" + url + "]", e);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private boolean isAlreadyLoadedByAncestor(URL url)
/* 109:    */   {
/* 110:163 */     WebWindow window = getPage().getEnclosingWindow();
/* 111:164 */     while (window != null)
/* 112:    */     {
/* 113:165 */       if (url.sameFile(window.getEnclosedPage().getWebResponse().getWebRequest().getUrl())) {
/* 114:166 */         return true;
/* 115:    */       }
/* 116:168 */       if (window == window.getParentWindow()) {
/* 117:170 */         window = null;
/* 118:    */       } else {
/* 119:173 */         window = window.getParentWindow();
/* 120:    */       }
/* 121:    */     }
/* 122:176 */     return false;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public final String getLongDescAttribute()
/* 126:    */   {
/* 127:187 */     return getAttribute("longdesc");
/* 128:    */   }
/* 129:    */   
/* 130:    */   public final String getNameAttribute()
/* 131:    */   {
/* 132:198 */     return getAttribute("name");
/* 133:    */   }
/* 134:    */   
/* 135:    */   public final void setNameAttribute(String name)
/* 136:    */   {
/* 137:207 */     setAttribute("name", name);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public final String getSrcAttribute()
/* 141:    */   {
/* 142:218 */     return getAttribute("src");
/* 143:    */   }
/* 144:    */   
/* 145:    */   public final String getFrameBorderAttribute()
/* 146:    */   {
/* 147:229 */     return getAttribute("frameborder");
/* 148:    */   }
/* 149:    */   
/* 150:    */   public final String getMarginWidthAttribute()
/* 151:    */   {
/* 152:240 */     return getAttribute("marginwidth");
/* 153:    */   }
/* 154:    */   
/* 155:    */   public final String getMarginHeightAttribute()
/* 156:    */   {
/* 157:251 */     return getAttribute("marginheight");
/* 158:    */   }
/* 159:    */   
/* 160:    */   public final String getNoResizeAttribute()
/* 161:    */   {
/* 162:262 */     return getAttribute("noresize");
/* 163:    */   }
/* 164:    */   
/* 165:    */   public final String getScrollingAttribute()
/* 166:    */   {
/* 167:273 */     return getAttribute("scrolling");
/* 168:    */   }
/* 169:    */   
/* 170:    */   public final String getOnLoadAttribute()
/* 171:    */   {
/* 172:284 */     return getAttribute("onload");
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Page getEnclosedPage()
/* 176:    */   {
/* 177:294 */     return getEnclosedWindow().getEnclosedPage();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public WebWindow getEnclosedWindow()
/* 181:    */   {
/* 182:302 */     return this.enclosedWindow_;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public final void setSrcAttribute(String attribute)
/* 186:    */   {
/* 187:310 */     setAttribute("src", attribute);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/* 191:    */   {
/* 192:318 */     if ("src".equals(qualifiedName)) {
/* 193:319 */       attributeValue = attributeValue.trim();
/* 194:    */     }
/* 195:322 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/* 196:324 */     if ("src".equals(qualifiedName))
/* 197:    */     {
/* 198:325 */       JavaScriptEngine jsEngine = getPage().getWebClient().getJavaScriptEngine();
/* 199:329 */       if ((!jsEngine.isScriptRunning()) || (attributeValue.startsWith("javascript:")))
/* 200:    */       {
/* 201:330 */         loadInnerPageIfPossible(attributeValue);
/* 202:    */       }
/* 203:    */       else
/* 204:    */       {
/* 205:333 */         final String src = attributeValue;
/* 206:334 */         PostponedAction action = new PostponedAction(getPage())
/* 207:    */         {
/* 208:    */           public void execute()
/* 209:    */             throws Exception
/* 210:    */           {
/* 211:337 */             if (BaseFrame.this.getSrcAttribute().equals(src)) {
/* 212:338 */               BaseFrame.this.loadInnerPage();
/* 213:    */             }
/* 214:    */           }
/* 215:341 */         };
/* 216:342 */         jsEngine.addPostponedAction(action);
/* 217:    */       }
/* 218:    */     }
/* 219:    */   }
/* 220:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.BaseFrame
 * JD-Core Version:    0.7.0.1
 */