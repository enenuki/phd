/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlFont
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "font";
/* 10:   */   
/* 11:   */   HtmlFont(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:46 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getSizeAttribute()
/* 17:   */   {
/* 18:57 */     return getAttribute("size");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public final String getColorAttribute()
/* 22:   */   {
/* 23:68 */     return getAttribute("color");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final String getFaceAttribute()
/* 27:   */   {
/* 28:79 */     return getAttribute("face");
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected boolean isTrimmedText()
/* 32:   */   {
/* 33:87 */     return false;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlFont
 * JD-Core Version:    0.7.0.1
 */