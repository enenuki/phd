/*  1:   */ package org.apache.xalan.xsltc.dom;
/*  2:   */ 
/*  3:   */ import org.apache.xml.dtm.DTMAxisIterator;
/*  4:   */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*  5:   */ 
/*  6:   */ public final class ClonedNodeListIterator
/*  7:   */   extends DTMAxisIteratorBase
/*  8:   */ {
/*  9:   */   private CachedNodeListIterator _source;
/* 10:38 */   private int _index = 0;
/* 11:   */   
/* 12:   */   public ClonedNodeListIterator(CachedNodeListIterator source)
/* 13:   */   {
/* 14:41 */     this._source = source;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setRestartable(boolean isRestartable) {}
/* 18:   */   
/* 19:   */   public DTMAxisIterator setStartNode(int node)
/* 20:   */   {
/* 21:50 */     return this;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int next()
/* 25:   */   {
/* 26:54 */     return this._source.getNode(this._index++);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getPosition()
/* 30:   */   {
/* 31:58 */     return this._index == 0 ? 1 : this._index;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getNodeByPosition(int pos)
/* 35:   */   {
/* 36:62 */     return this._source.getNode(pos);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public DTMAxisIterator cloneIterator()
/* 40:   */   {
/* 41:66 */     return this._source.cloneIterator();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public DTMAxisIterator reset()
/* 45:   */   {
/* 46:70 */     this._index = 0;
/* 47:71 */     return this;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setMark()
/* 51:   */   {
/* 52:75 */     this._source.setMark();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void gotoMark()
/* 56:   */   {
/* 57:79 */     this._source.gotoMark();
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.ClonedNodeListIterator
 * JD-Core Version:    0.7.0.1
 */