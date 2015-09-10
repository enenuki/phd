/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlHiddenInput
/*  7:   */   extends HtmlInput
/*  8:   */ {
/*  9:   */   HtmlHiddenInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 10:   */   {
/* 11:44 */     super(namespaceURI, qualifiedName, page, attributes);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String asText()
/* 15:   */   {
/* 16:54 */     return "";
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlHiddenInput
 * JD-Core Version:    0.7.0.1
 */