/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import java.util.AbstractList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.w3c.dom.Node;
/*  6:   */ 
/*  7:   */ class StaticDomNodeList
/*  8:   */   extends AbstractList<DomNode>
/*  9:   */   implements DomNodeList<DomNode>
/* 10:   */ {
/* 11:   */   private List<DomNode> elements_;
/* 12:   */   
/* 13:   */   public StaticDomNodeList(List<DomNode> elements)
/* 14:   */   {
/* 15:33 */     this.elements_ = elements;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getLength()
/* 19:   */   {
/* 20:40 */     return this.elements_.size();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int size()
/* 24:   */   {
/* 25:48 */     return getLength();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Node item(int index)
/* 29:   */   {
/* 30:55 */     return get(index);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public DomNode get(int index)
/* 34:   */   {
/* 35:63 */     return (DomNode)this.elements_.get(index);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.StaticDomNodeList
 * JD-Core Version:    0.7.0.1
 */