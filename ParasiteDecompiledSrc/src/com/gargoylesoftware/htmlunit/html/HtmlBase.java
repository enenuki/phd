/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlBase
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "base";
/* 10:   */   
/* 11:   */   HtmlBase(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:44 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getHrefAttribute()
/* 17:   */   {
/* 18:55 */     return getAttribute("href");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public final String getTargetAttribute()
/* 22:   */   {
/* 23:66 */     return getAttribute("target");
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlBase
 * JD-Core Version:    0.7.0.1
 */