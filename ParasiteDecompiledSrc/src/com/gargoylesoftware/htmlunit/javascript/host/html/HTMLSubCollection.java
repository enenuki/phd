/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ class HTMLSubCollection
/*   8:    */   extends HTMLCollection
/*   9:    */ {
/*  10:    */   private final HTMLCollection mainCollection_;
/*  11:    */   
/*  12:    */   public HTMLSubCollection(HTMLCollection mainCollection, String subDescription)
/*  13:    */   {
/*  14:658 */     super(mainCollection.getDomNodeOrDie(), false, mainCollection.toString() + subDescription);
/*  15:659 */     this.mainCollection_ = mainCollection;
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected List<Object> computeElements()
/*  19:    */   {
/*  20:664 */     List<Object> list = new ArrayList();
/*  21:665 */     for (Object o : this.mainCollection_.getElements()) {
/*  22:666 */       if (isMatching((DomNode)o)) {
/*  23:667 */         list.add(o);
/*  24:    */       }
/*  25:    */     }
/*  26:670 */     return list;
/*  27:    */   }
/*  28:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLSubCollection
 * JD-Core Version:    0.7.0.1
 */