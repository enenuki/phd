/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlInsertedText
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "ins";
/* 10:   */   
/* 11:   */   HtmlInsertedText(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:45 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getCiteAttribute()
/* 17:   */   {
/* 18:57 */     return getAttribute("cite");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public final String getDateTimeAttribute()
/* 22:   */   {
/* 23:69 */     return getAttribute("datetime");
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlInsertedText
 * JD-Core Version:    0.7.0.1
 */