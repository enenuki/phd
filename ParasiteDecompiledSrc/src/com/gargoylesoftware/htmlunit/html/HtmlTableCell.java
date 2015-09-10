/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public abstract class HtmlTableCell
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   protected HtmlTableCell(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 10:   */   {
/* 11:44 */     super(namespaceURI, qualifiedName, page, attributes);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int getColumnSpan()
/* 15:   */   {
/* 16:52 */     String spanString = getAttribute("colspan");
/* 17:53 */     if ((spanString == null) || (spanString.length() == 0)) {
/* 18:54 */       return 1;
/* 19:   */     }
/* 20:56 */     return Integer.parseInt(spanString);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getRowSpan()
/* 24:   */   {
/* 25:64 */     String spanString = getAttribute("rowspan");
/* 26:65 */     if ((spanString == null) || (spanString.length() == 0)) {
/* 27:66 */       return 1;
/* 28:   */     }
/* 29:68 */     return Integer.parseInt(spanString);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public HtmlTableRow getEnclosingRow()
/* 33:   */   {
/* 34:76 */     return (HtmlTableRow)getEnclosingElement("tr");
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlTableCell
 * JD-Core Version:    0.7.0.1
 */