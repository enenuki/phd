/*  1:   */ package org.apache.xalan.xsltc.dom;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*  4:   */ import org.apache.xml.dtm.DTMAxisIterator;
/*  5:   */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*  6:   */ 
/*  7:   */ public final class AbsoluteIterator
/*  8:   */   extends DTMAxisIteratorBase
/*  9:   */ {
/* 10:   */   private DTMAxisIterator _source;
/* 11:   */   
/* 12:   */   public AbsoluteIterator(DTMAxisIterator source)
/* 13:   */   {
/* 14:50 */     this._source = source;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setRestartable(boolean isRestartable)
/* 18:   */   {
/* 19:55 */     this._isRestartable = isRestartable;
/* 20:56 */     this._source.setRestartable(isRestartable);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public DTMAxisIterator setStartNode(int node)
/* 24:   */   {
/* 25:60 */     this._startNode = 0;
/* 26:61 */     if (this._isRestartable)
/* 27:   */     {
/* 28:62 */       this._source.setStartNode(this._startNode);
/* 29:63 */       resetPosition();
/* 30:   */     }
/* 31:65 */     return this;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int next()
/* 35:   */   {
/* 36:69 */     return returnNode(this._source.next());
/* 37:   */   }
/* 38:   */   
/* 39:   */   public DTMAxisIterator cloneIterator()
/* 40:   */   {
/* 41:   */     try
/* 42:   */     {
/* 43:74 */       AbsoluteIterator clone = (AbsoluteIterator)super.clone();
/* 44:75 */       clone._source = this._source.cloneIterator();
/* 45:76 */       clone.resetPosition();
/* 46:77 */       clone._isRestartable = false;
/* 47:78 */       return clone;
/* 48:   */     }
/* 49:   */     catch (CloneNotSupportedException e)
/* 50:   */     {
/* 51:81 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/* 52:   */     }
/* 53:83 */     return null;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public DTMAxisIterator reset()
/* 57:   */   {
/* 58:88 */     this._source.reset();
/* 59:89 */     return resetPosition();
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setMark()
/* 63:   */   {
/* 64:93 */     this._source.setMark();
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void gotoMark()
/* 68:   */   {
/* 69:97 */     this._source.gotoMark();
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.AbsoluteIterator
 * JD-Core Version:    0.7.0.1
 */