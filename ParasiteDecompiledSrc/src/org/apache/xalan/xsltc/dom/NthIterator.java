/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   6:    */ 
/*   7:    */ public final class NthIterator
/*   8:    */   extends DTMAxisIteratorBase
/*   9:    */ {
/*  10:    */   private DTMAxisIterator _source;
/*  11:    */   private final int _position;
/*  12:    */   private boolean _ready;
/*  13:    */   
/*  14:    */   public NthIterator(DTMAxisIterator source, int n)
/*  15:    */   {
/*  16: 39 */     this._source = source;
/*  17: 40 */     this._position = n;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setRestartable(boolean isRestartable)
/*  21:    */   {
/*  22: 44 */     this._isRestartable = isRestartable;
/*  23: 45 */     this._source.setRestartable(isRestartable);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public DTMAxisIterator cloneIterator()
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 50 */       NthIterator clone = (NthIterator)super.clone();
/*  31: 51 */       clone._source = this._source.cloneIterator();
/*  32: 52 */       clone._isRestartable = false;
/*  33: 53 */       return clone;
/*  34:    */     }
/*  35:    */     catch (CloneNotSupportedException e)
/*  36:    */     {
/*  37: 56 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  38:    */     }
/*  39: 58 */     return null;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int next()
/*  43:    */   {
/*  44: 63 */     if (this._ready)
/*  45:    */     {
/*  46: 64 */       this._ready = false;
/*  47: 65 */       return this._source.getNodeByPosition(this._position);
/*  48:    */     }
/*  49: 67 */     return -1;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public DTMAxisIterator setStartNode(int node)
/*  53:    */   {
/*  54: 87 */     if (this._isRestartable)
/*  55:    */     {
/*  56: 88 */       this._source.setStartNode(node);
/*  57: 89 */       this._ready = true;
/*  58:    */     }
/*  59: 91 */     return this;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public DTMAxisIterator reset()
/*  63:    */   {
/*  64: 95 */     this._source.reset();
/*  65: 96 */     this._ready = true;
/*  66: 97 */     return this;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getLast()
/*  70:    */   {
/*  71:101 */     return 1;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getPosition()
/*  75:    */   {
/*  76:105 */     return 1;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setMark()
/*  80:    */   {
/*  81:109 */     this._source.setMark();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void gotoMark()
/*  85:    */   {
/*  86:113 */     this._source.gotoMark();
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.NthIterator
 * JD-Core Version:    0.7.0.1
 */