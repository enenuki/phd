/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  6:   */ import com.gargoylesoftware.htmlunit.WebClient;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.w3c.dom.Node;
/*  9:   */ 
/* 10:   */ public class HtmlNoScript
/* 11:   */   extends HtmlElement
/* 12:   */ {
/* 13:   */   public static final String TAG_NAME = "noscript";
/* 14:   */   
/* 15:   */   HtmlNoScript(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 16:   */   {
/* 17:49 */     super(namespaceURI, qualifiedName, page, attributes);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public DomNode appendChild(Node node)
/* 21:   */   {
/* 22:54 */     WebClient webClient = getPage().getWebClient();
/* 23:55 */     if ((!webClient.isJavaScriptEnabled()) || (webClient.getBrowserVersion().hasFeature(BrowserVersionFeatures.NOSCRIPT_BODY_AS_TEXT))) {
/* 24:57 */       return super.appendChild(node);
/* 25:   */     }
/* 26:59 */     return null;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlNoScript
 * JD-Core Version:    0.7.0.1
 */