/*  1:   */ package org.apache.xalan.xsltc.dom;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.DOM;
/*  4:   */ import org.apache.xml.dtm.DTMAxisIterator;
/*  5:   */ 
/*  6:   */ public final class UnionIterator
/*  7:   */   extends MultiValuedNodeHeapIterator
/*  8:   */ {
/*  9:   */   private final DOM _dom;
/* 10:   */   
/* 11:   */   private final class LookAheadIterator
/* 12:   */     extends MultiValuedNodeHeapIterator.HeapNode
/* 13:   */   {
/* 14:   */     public DTMAxisIterator iterator;
/* 15:   */     
/* 16:   */     public LookAheadIterator(DTMAxisIterator iterator)
/* 17:   */     {
/* 18:49 */       super();
/* 19:50 */       this.iterator = iterator;
/* 20:   */     }
/* 21:   */     
/* 22:   */     public int step()
/* 23:   */     {
/* 24:54 */       this._node = this.iterator.next();
/* 25:55 */       return this._node;
/* 26:   */     }
/* 27:   */     
/* 28:   */     public MultiValuedNodeHeapIterator.HeapNode cloneHeapNode()
/* 29:   */     {
/* 30:59 */       LookAheadIterator clone = (LookAheadIterator)super.cloneHeapNode();
/* 31:60 */       clone.iterator = this.iterator.cloneIterator();
/* 32:61 */       return clone;
/* 33:   */     }
/* 34:   */     
/* 35:   */     public void setMark()
/* 36:   */     {
/* 37:65 */       super.setMark();
/* 38:66 */       this.iterator.setMark();
/* 39:   */     }
/* 40:   */     
/* 41:   */     public void gotoMark()
/* 42:   */     {
/* 43:70 */       super.gotoMark();
/* 44:71 */       this.iterator.gotoMark();
/* 45:   */     }
/* 46:   */     
/* 47:   */     public boolean isLessThan(MultiValuedNodeHeapIterator.HeapNode heapNode)
/* 48:   */     {
/* 49:75 */       LookAheadIterator comparand = (LookAheadIterator)heapNode;
/* 50:76 */       return UnionIterator.this._dom.lessThan(this._node, heapNode._node);
/* 51:   */     }
/* 52:   */     
/* 53:   */     public MultiValuedNodeHeapIterator.HeapNode setStartNode(int node)
/* 54:   */     {
/* 55:80 */       this.iterator.setStartNode(node);
/* 56:81 */       return this;
/* 57:   */     }
/* 58:   */     
/* 59:   */     public MultiValuedNodeHeapIterator.HeapNode reset()
/* 60:   */     {
/* 61:85 */       this.iterator.reset();
/* 62:86 */       return this;
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   public UnionIterator(DOM dom)
/* 67:   */   {
/* 68:91 */     this._dom = dom;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public UnionIterator addIterator(DTMAxisIterator iterator)
/* 72:   */   {
/* 73:95 */     addHeapNode(new LookAheadIterator(iterator));
/* 74:96 */     return this;
/* 75:   */   }
/* 76:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.UnionIterator
 * JD-Core Version:    0.7.0.1
 */