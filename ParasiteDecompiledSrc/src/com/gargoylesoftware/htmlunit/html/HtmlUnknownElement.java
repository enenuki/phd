/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlUnknownElement
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   HtmlUnknownElement(SgmlPage page, String tagName, Map<String, DomAttr> attributes)
/* 10:   */   {
/* 11:41 */     this(page, null, tagName, attributes);
/* 12:   */   }
/* 13:   */   
/* 14:   */   HtmlUnknownElement(SgmlPage page, String namespaceURI, String qualifiedName, Map<String, DomAttr> attributes)
/* 15:   */   {
/* 16:54 */     super(namespaceURI, qualifiedName, page, attributes);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected boolean isTrimmedText()
/* 20:   */   {
/* 21:62 */     return false;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlUnknownElement
 * JD-Core Version:    0.7.0.1
 */