/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlFrameSet
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "frameset";
/* 10:   */   
/* 11:   */   HtmlFrameSet(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:45 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */     
/* 15:   */ 
/* 16:48 */     getScriptObject();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public final String getRowsAttribute()
/* 20:   */   {
/* 21:59 */     return getAttribute("rows");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public final String getColsAttribute()
/* 25:   */   {
/* 26:70 */     return getAttribute("cols");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public final String getOnLoadAttribute()
/* 30:   */   {
/* 31:81 */     return getAttribute("onload");
/* 32:   */   }
/* 33:   */   
/* 34:   */   public final String getOnUnloadAttribute()
/* 35:   */   {
/* 36:92 */     return getAttribute("onunload");
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlFrameSet
 * JD-Core Version:    0.7.0.1
 */