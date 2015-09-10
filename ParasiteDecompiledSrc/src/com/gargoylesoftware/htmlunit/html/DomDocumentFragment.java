/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import org.w3c.dom.DocumentFragment;
/*  5:   */ 
/*  6:   */ public class DomDocumentFragment
/*  7:   */   extends DomNode
/*  8:   */   implements DocumentFragment
/*  9:   */ {
/* 10:   */   public static final String NODE_NAME = "#document-fragment";
/* 11:   */   
/* 12:   */   public DomDocumentFragment(SgmlPage page)
/* 13:   */   {
/* 14:37 */     super(page);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getNodeName()
/* 18:   */   {
/* 19:46 */     return "#document-fragment";
/* 20:   */   }
/* 21:   */   
/* 22:   */   public short getNodeType()
/* 23:   */   {
/* 24:55 */     return 11;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String asXml()
/* 28:   */   {
/* 29:63 */     StringBuilder sb = new StringBuilder();
/* 30:64 */     for (DomNode node : getChildren()) {
/* 31:65 */       sb.append(node.asXml());
/* 32:   */     }
/* 33:67 */     return sb.toString();
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected boolean isDirectlyAttachedToPage()
/* 37:   */   {
/* 38:75 */     return false;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomDocumentFragment
 * JD-Core Version:    0.7.0.1
 */