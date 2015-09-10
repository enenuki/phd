/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlPreformattedText
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "pre";
/* 10:   */   
/* 11:   */   HtmlPreformattedText(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:45 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getWidthAttribute()
/* 17:   */   {
/* 18:57 */     return getAttribute("width");
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlPreformattedText
 * JD-Core Version:    0.7.0.1
 */