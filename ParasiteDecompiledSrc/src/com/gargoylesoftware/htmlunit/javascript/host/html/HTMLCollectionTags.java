/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  4:   */ 
/*  5:   */ public class HTMLCollectionTags
/*  6:   */   extends HTMLCollection
/*  7:   */ {
/*  8:   */   @Deprecated
/*  9:   */   public HTMLCollectionTags() {}
/* 10:   */   
/* 11:   */   public HTMLCollectionTags(DomNode parentScope, String description)
/* 12:   */   {
/* 13:43 */     super(parentScope, false, description);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected Object equivalentValues(Object other)
/* 17:   */   {
/* 18:52 */     if (!(other instanceof HTMLCollectionTags)) {
/* 19:53 */       return Boolean.FALSE;
/* 20:   */     }
/* 21:55 */     return super.equivalentValues(other);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollectionTags
 * JD-Core Version:    0.7.0.1
 */