/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlCaption
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "caption";
/* 10:   */   
/* 11:   */   HtmlCaption(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:45 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getAlignAttribute()
/* 17:   */   {
/* 18:56 */     return getAttribute("align");
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlCaption
 * JD-Core Version:    0.7.0.1
 */