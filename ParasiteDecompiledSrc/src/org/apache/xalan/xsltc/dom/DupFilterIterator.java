/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   4:    */ import org.apache.xalan.xsltc.util.IntegerArray;
/*   5:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   6:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   7:    */ 
/*   8:    */ public final class DupFilterIterator
/*   9:    */   extends DTMAxisIteratorBase
/*  10:    */ {
/*  11:    */   private DTMAxisIterator _source;
/*  12: 46 */   private IntegerArray _nodes = new IntegerArray();
/*  13: 51 */   private int _current = 0;
/*  14: 56 */   private int _nodesSize = 0;
/*  15: 61 */   private int _lastNext = -1;
/*  16: 66 */   private int _markedLastNext = -1;
/*  17:    */   
/*  18:    */   public DupFilterIterator(DTMAxisIterator source)
/*  19:    */   {
/*  20: 69 */     this._source = source;
/*  21: 75 */     if ((source instanceof KeyIndex)) {
/*  22: 76 */       setStartNode(0);
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public DTMAxisIterator setStartNode(int node)
/*  27:    */   {
/*  28: 86 */     if (this._isRestartable)
/*  29:    */     {
/*  30: 89 */       boolean sourceIsKeyIndex = this._source instanceof KeyIndex;
/*  31: 91 */       if ((sourceIsKeyIndex) && (this._startNode == 0)) {
/*  32: 93 */         return this;
/*  33:    */       }
/*  34: 96 */       if (node != this._startNode)
/*  35:    */       {
/*  36: 97 */         this._source.setStartNode(this._startNode = node);
/*  37:    */         
/*  38: 99 */         this._nodes.clear();
/*  39:100 */         while ((node = this._source.next()) != -1) {
/*  40:101 */           this._nodes.add(node);
/*  41:    */         }
/*  42:106 */         if (!sourceIsKeyIndex) {
/*  43:107 */           this._nodes.sort();
/*  44:    */         }
/*  45:110 */         this._nodesSize = this._nodes.cardinality();
/*  46:111 */         this._current = 0;
/*  47:112 */         this._lastNext = -1;
/*  48:113 */         resetPosition();
/*  49:    */       }
/*  50:    */     }
/*  51:116 */     return this;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int next()
/*  55:    */   {
/*  56:120 */     while (this._current < this._nodesSize)
/*  57:    */     {
/*  58:121 */       int next = this._nodes.at(this._current++);
/*  59:122 */       if (next != this._lastNext) {
/*  60:123 */         return returnNode(this._lastNext = next);
/*  61:    */       }
/*  62:    */     }
/*  63:126 */     return -1;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public DTMAxisIterator cloneIterator()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:131 */       DupFilterIterator clone = (DupFilterIterator)super.clone();
/*  71:    */       
/*  72:133 */       clone._nodes = ((IntegerArray)this._nodes.clone());
/*  73:134 */       clone._source = this._source.cloneIterator();
/*  74:135 */       clone._isRestartable = false;
/*  75:136 */       return clone.reset();
/*  76:    */     }
/*  77:    */     catch (CloneNotSupportedException e)
/*  78:    */     {
/*  79:139 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  80:    */     }
/*  81:141 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setRestartable(boolean isRestartable)
/*  85:    */   {
/*  86:146 */     this._isRestartable = isRestartable;
/*  87:147 */     this._source.setRestartable(isRestartable);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setMark()
/*  91:    */   {
/*  92:151 */     this._markedNode = this._current;
/*  93:152 */     this._markedLastNext = this._lastNext;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void gotoMark()
/*  97:    */   {
/*  98:156 */     this._current = this._markedNode;
/*  99:157 */     this._lastNext = this._markedLastNext;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public DTMAxisIterator reset()
/* 103:    */   {
/* 104:161 */     this._current = 0;
/* 105:162 */     this._lastNext = -1;
/* 106:163 */     return resetPosition();
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.DupFilterIterator
 * JD-Core Version:    0.7.0.1
 */