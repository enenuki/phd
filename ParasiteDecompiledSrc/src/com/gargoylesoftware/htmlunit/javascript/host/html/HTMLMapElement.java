/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.HtmlArea;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlMap;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ 
/*  9:   */ public class HTMLMapElement
/* 10:   */   extends HTMLElement
/* 11:   */ {
/* 12:   */   private HTMLCollection areas_;
/* 13:   */   
/* 14:   */   public HTMLCollection jsxGet_areas()
/* 15:   */   {
/* 16:45 */     if (this.areas_ == null)
/* 17:   */     {
/* 18:46 */       final HtmlMap map = (HtmlMap)getDomNodeOrDie();
/* 19:47 */       this.areas_ = new HTMLCollection(map, false, "HTMLMapElement.areas")
/* 20:   */       {
/* 21:   */         protected List<Object> computeElements()
/* 22:   */         {
/* 23:50 */           List<Object> list = new ArrayList();
/* 24:51 */           for (DomNode node : map.getChildElements()) {
/* 25:52 */             if ((node instanceof HtmlArea)) {
/* 26:53 */               list.add(node);
/* 27:   */             }
/* 28:   */           }
/* 29:56 */           return list;
/* 30:   */         }
/* 31:   */       };
/* 32:   */     }
/* 33:60 */     return this.areas_;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLMapElement
 * JD-Core Version:    0.7.0.1
 */