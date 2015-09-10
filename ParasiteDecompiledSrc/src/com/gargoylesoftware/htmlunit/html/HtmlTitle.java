/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class HtmlTitle
/*  7:   */   extends HtmlElement
/*  8:   */ {
/*  9:   */   public static final String TAG_NAME = "title";
/* 10:   */   
/* 11:   */   HtmlTitle(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 12:   */   {
/* 13:46 */     super(namespaceURI, qualifiedName, page, attributes);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setNodeValue(String message)
/* 17:   */   {
/* 18:55 */     DomNode child = getFirstChild();
/* 19:56 */     if (child == null)
/* 20:   */     {
/* 21:57 */       DomNode textNode = new DomText(getPage(), message);
/* 22:58 */       appendChild(textNode);
/* 23:   */     }
/* 24:60 */     else if ((child instanceof DomText))
/* 25:   */     {
/* 26:61 */       ((DomText)child).setData(message);
/* 27:   */     }
/* 28:   */     else
/* 29:   */     {
/* 30:64 */       throw new IllegalStateException("For title tag, this should be a text node");
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected boolean isEmptyXmlTagExpanded()
/* 35:   */   {
/* 36:75 */     return true;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlTitle
 * JD-Core Version:    0.7.0.1
 */