/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlHorizontalRule
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "hr";
/* 10:   */   
/* 11:   */   HtmlHorizontalRule(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:45 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getAlignAttribute()
/* 17:   */   {
/* 18:56 */     return getAttribute("align");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public final String getNoShadeAttribute()
/* 22:   */   {
/* 23:67 */     return getAttribute("noshade");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final String getSizeAttribute()
/* 27:   */   {
/* 28:78 */     return getAttribute("size");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public final String getWidthAttribute()
/* 32:   */   {
/* 33:89 */     return getAttribute("width");
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlHorizontalRule
 * JD-Core Version:    0.7.0.1
 */