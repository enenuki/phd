/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlSpan
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "span";
/* 10:   */   
/* 11:   */   HtmlSpan(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:47 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected boolean isTrimmedText()
/* 17:   */   {
/* 18:55 */     return false;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected boolean isEmptyXmlTagExpanded()
/* 22:   */   {
/* 23:63 */     return true;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlSpan
 * JD-Core Version:    0.7.0.1
 */