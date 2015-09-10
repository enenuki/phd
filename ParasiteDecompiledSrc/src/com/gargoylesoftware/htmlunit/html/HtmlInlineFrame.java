/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlInlineFrame
/*  7:   */   extends BaseFrame
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "iframe";
/* 10:   */   
/* 11:   */   HtmlInlineFrame(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:46 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected boolean isEmptyXmlTagExpanded()
/* 17:   */   {
/* 18:56 */     return true;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlInlineFrame
 * JD-Core Version:    0.7.0.1
 */