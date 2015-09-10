/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlHeading2
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "h2";
/* 10:   */   
/* 11:   */   HtmlHeading2(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:45 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getAlignAttribute()
/* 17:   */   {
/* 18:56 */     return getAttribute("align");
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected boolean isBlock()
/* 22:   */   {
/* 23:64 */     return true;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlHeading2
 * JD-Core Version:    0.7.0.1
 */