/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ 
/*   6:    */ abstract class NodeIterator
/*   7:    */   implements Iterator<DomNode>
/*   8:    */ {
/*   9:    */   private DomNode node_;
/*  10:    */   
/*  11:    */   public NodeIterator(DomNode contextNode)
/*  12:    */   {
/*  13:161 */     this.node_ = getFirstNode(contextNode);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public boolean hasNext()
/*  17:    */   {
/*  18:166 */     return this.node_ != null;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public DomNode next()
/*  22:    */   {
/*  23:171 */     if (this.node_ == null) {
/*  24:172 */       throw new NoSuchElementException();
/*  25:    */     }
/*  26:174 */     DomNode ret = this.node_;
/*  27:175 */     this.node_ = getNextNode(this.node_);
/*  28:176 */     return ret;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void remove()
/*  32:    */   {
/*  33:181 */     throw new UnsupportedOperationException();
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected abstract DomNode getFirstNode(DomNode paramDomNode);
/*  37:    */   
/*  38:    */   protected abstract DomNode getNextNode(DomNode paramDomNode);
/*  39:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.NodeIterator
 * JD-Core Version:    0.7.0.1
 */