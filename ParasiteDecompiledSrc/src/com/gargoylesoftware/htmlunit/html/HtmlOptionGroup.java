/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  6:   */ import com.gargoylesoftware.htmlunit.WebClient;
/*  7:   */ import java.util.Map;
/*  8:   */ 
/*  9:   */ public class HtmlOptionGroup
/* 10:   */   extends HtmlElement
/* 11:   */   implements DisabledElement
/* 12:   */ {
/* 13:   */   public static final String TAG_NAME = "optgroup";
/* 14:   */   
/* 15:   */   HtmlOptionGroup(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 16:   */   {
/* 17:48 */     super(namespaceURI, qualifiedName, page, attributes);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public final boolean isDisabled()
/* 21:   */   {
/* 22:60 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLOPTIONGROUP_NO_DISABLED)) {
/* 23:62 */       return false;
/* 24:   */     }
/* 25:64 */     return hasAttribute("disabled");
/* 26:   */   }
/* 27:   */   
/* 28:   */   public final String getDisabledAttribute()
/* 29:   */   {
/* 30:71 */     return getAttribute("disabled");
/* 31:   */   }
/* 32:   */   
/* 33:   */   public final String getLabelAttribute()
/* 34:   */   {
/* 35:82 */     return getAttribute("label");
/* 36:   */   }
/* 37:   */   
/* 38:   */   public HtmlSelect getEnclosingSelect()
/* 39:   */   {
/* 40:90 */     return (HtmlSelect)getEnclosingElement("select");
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlOptionGroup
 * JD-Core Version:    0.7.0.1
 */