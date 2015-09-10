/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   6:    */ 
/*   7:    */ /**
/*   8:    */  * @deprecated
/*   9:    */  */
/*  10:    */ public final class ForwardPositionIterator
/*  11:    */   extends DTMAxisIteratorBase
/*  12:    */ {
/*  13:    */   private DTMAxisIterator _source;
/*  14:    */   
/*  15:    */   public ForwardPositionIterator(DTMAxisIterator source)
/*  16:    */   {
/*  17: 68 */     this._source = source;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public DTMAxisIterator cloneIterator()
/*  21:    */   {
/*  22:    */     try
/*  23:    */     {
/*  24: 73 */       ForwardPositionIterator clone = (ForwardPositionIterator)super.clone();
/*  25:    */       
/*  26: 75 */       clone._source = this._source.cloneIterator();
/*  27: 76 */       clone._isRestartable = false;
/*  28: 77 */       return clone.reset();
/*  29:    */     }
/*  30:    */     catch (CloneNotSupportedException e)
/*  31:    */     {
/*  32: 80 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  33:    */     }
/*  34: 82 */     return null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int next()
/*  38:    */   {
/*  39: 87 */     return returnNode(this._source.next());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public DTMAxisIterator setStartNode(int node)
/*  43:    */   {
/*  44: 91 */     this._source.setStartNode(node);
/*  45: 92 */     return this;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public DTMAxisIterator reset()
/*  49:    */   {
/*  50: 96 */     this._source.reset();
/*  51: 97 */     return resetPosition();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setMark()
/*  55:    */   {
/*  56:101 */     this._source.setMark();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void gotoMark()
/*  60:    */   {
/*  61:105 */     this._source.gotoMark();
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.ForwardPositionIterator
 * JD-Core Version:    0.7.0.1
 */