/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlUnorderedList
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "ul";
/* 10:   */   
/* 11:   */   HtmlUnorderedList(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
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
/* 26:   */   protected boolean isBlock()
/* 27:   */   {
/* 28:77 */     return true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected boolean isEmptyXmlTagExpanded()
/* 32:   */   {
/* 33:85 */     return true;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlUnorderedList
 * JD-Core Version:    0.7.0.1
 */