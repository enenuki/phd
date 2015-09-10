/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlBaseFont
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "basefont";
/* 10:   */   
/* 11:   */   HtmlBaseFont(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:44 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String getIdAttribute()
/* 17:   */   {
/* 18:55 */     return getAttribute("id");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public final String getSizeAttribute()
/* 22:   */   {
/* 23:66 */     return getAttribute("size");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final String getColorAttribute()
/* 27:   */   {
/* 28:77 */     return getAttribute("color");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public final String getFaceAttribute()
/* 32:   */   {
/* 33:88 */     return getAttribute("face");
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlBaseFont
 * JD-Core Version:    0.7.0.1
 */