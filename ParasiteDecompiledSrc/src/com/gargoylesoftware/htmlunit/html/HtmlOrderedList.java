/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlOrderedList
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "ol";
/* 10:   */   
/* 11:   */   HtmlOrderedList(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:45 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getTypeAttribute()
/* 17:   */   {
/* 18:57 */     return getAttribute("type");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public final String getCompactAttribute()
/* 22:   */   {
/* 23:69 */     return getAttribute("compact");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final String getStartAttribute()
/* 27:   */   {
/* 28:81 */     return getAttribute("start");
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected boolean isBlock()
/* 32:   */   {
/* 33:89 */     return true;
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected boolean isEmptyXmlTagExpanded()
/* 37:   */   {
/* 38:97 */     return true;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlOrderedList
 * JD-Core Version:    0.7.0.1
 */