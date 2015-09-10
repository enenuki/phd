/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlDivision
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "div";
/* 10:   */   
/* 11:   */   public HtmlDivision(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:47 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getAlignAttribute()
/* 17:   */   {
/* 18:58 */     return getAttribute("align");
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected boolean isEmptyXmlTagExpanded()
/* 22:   */   {
/* 23:67 */     return true;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected boolean isBlock()
/* 27:   */   {
/* 28:75 */     return true;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlDivision
 * JD-Core Version:    0.7.0.1
 */