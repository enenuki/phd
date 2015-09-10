/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.DTMFilter;
/*   6:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   7:    */ 
/*   8:    */ public final class FilterIterator
/*   9:    */   extends DTMAxisIteratorBase
/*  10:    */ {
/*  11:    */   private DTMAxisIterator _source;
/*  12:    */   private final DTMFilter _filter;
/*  13:    */   private final boolean _isReverse;
/*  14:    */   
/*  15:    */   public FilterIterator(DTMAxisIterator source, DTMFilter filter)
/*  16:    */   {
/*  17: 56 */     this._source = source;
/*  18:    */     
/*  19: 58 */     this._filter = filter;
/*  20: 59 */     this._isReverse = source.isReverse();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean isReverse()
/*  24:    */   {
/*  25: 63 */     return this._isReverse;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setRestartable(boolean isRestartable)
/*  29:    */   {
/*  30: 68 */     this._isRestartable = isRestartable;
/*  31: 69 */     this._source.setRestartable(isRestartable);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DTMAxisIterator cloneIterator()
/*  35:    */   {
/*  36:    */     try
/*  37:    */     {
/*  38: 75 */       FilterIterator clone = (FilterIterator)super.clone();
/*  39: 76 */       clone._source = this._source.cloneIterator();
/*  40: 77 */       clone._isRestartable = false;
/*  41: 78 */       return clone.reset();
/*  42:    */     }
/*  43:    */     catch (CloneNotSupportedException e)
/*  44:    */     {
/*  45: 81 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  46:    */     }
/*  47: 83 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public DTMAxisIterator reset()
/*  51:    */   {
/*  52: 88 */     this._source.reset();
/*  53: 89 */     return resetPosition();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int next()
/*  57:    */   {
/*  58:    */     int node;
/*  59: 94 */     while ((node = this._source.next()) != -1)
/*  60:    */     {
/*  61:    */       int i;
/*  62: 95 */       if (this._filter.acceptNode(i, -1) == 1) {
/*  63: 96 */         return returnNode(i);
/*  64:    */       }
/*  65:    */     }
/*  66: 99 */     return -1;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public DTMAxisIterator setStartNode(int node)
/*  70:    */   {
/*  71:103 */     if (this._isRestartable)
/*  72:    */     {
/*  73:104 */       this._source.setStartNode(this._startNode = node);
/*  74:105 */       return resetPosition();
/*  75:    */     }
/*  76:107 */     return this;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setMark()
/*  80:    */   {
/*  81:111 */     this._source.setMark();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void gotoMark()
/*  85:    */   {
/*  86:115 */     this._source.gotoMark();
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.FilterIterator
 * JD-Core Version:    0.7.0.1
 */