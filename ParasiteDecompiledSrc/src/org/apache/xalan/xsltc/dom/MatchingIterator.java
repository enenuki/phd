/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   6:    */ 
/*   7:    */ public final class MatchingIterator
/*   8:    */   extends DTMAxisIteratorBase
/*   9:    */ {
/*  10:    */   private DTMAxisIterator _source;
/*  11:    */   private final int _match;
/*  12:    */   
/*  13:    */   public MatchingIterator(int match, DTMAxisIterator source)
/*  14:    */   {
/*  15: 61 */     this._source = source;
/*  16: 62 */     this._match = match;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void setRestartable(boolean isRestartable)
/*  20:    */   {
/*  21: 67 */     this._isRestartable = isRestartable;
/*  22: 68 */     this._source.setRestartable(isRestartable);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public DTMAxisIterator cloneIterator()
/*  26:    */   {
/*  27:    */     try
/*  28:    */     {
/*  29: 74 */       MatchingIterator clone = (MatchingIterator)super.clone();
/*  30: 75 */       clone._source = this._source.cloneIterator();
/*  31: 76 */       clone._isRestartable = false;
/*  32: 77 */       return clone.reset();
/*  33:    */     }
/*  34:    */     catch (CloneNotSupportedException e)
/*  35:    */     {
/*  36: 80 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  37:    */     }
/*  38: 82 */     return null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public DTMAxisIterator setStartNode(int node)
/*  42:    */   {
/*  43: 87 */     if (this._isRestartable)
/*  44:    */     {
/*  45: 89 */       this._source.setStartNode(node);
/*  46:    */       
/*  47:    */ 
/*  48: 92 */       this._position = 1;
/*  49: 93 */       while (((node = this._source.next()) != -1) && (node != this._match)) {
/*  50: 94 */         this._position += 1;
/*  51:    */       }
/*  52:    */     }
/*  53: 97 */     return this;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public DTMAxisIterator reset()
/*  57:    */   {
/*  58:101 */     this._source.reset();
/*  59:102 */     return resetPosition();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int next()
/*  63:    */   {
/*  64:106 */     return this._source.next();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getLast()
/*  68:    */   {
/*  69:110 */     if (this._last == -1) {
/*  70:111 */       this._last = this._source.getLast();
/*  71:    */     }
/*  72:113 */     return this._last;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getPosition()
/*  76:    */   {
/*  77:117 */     return this._position;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setMark()
/*  81:    */   {
/*  82:121 */     this._source.setMark();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void gotoMark()
/*  86:    */   {
/*  87:125 */     this._source.gotoMark();
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.MatchingIterator
 * JD-Core Version:    0.7.0.1
 */