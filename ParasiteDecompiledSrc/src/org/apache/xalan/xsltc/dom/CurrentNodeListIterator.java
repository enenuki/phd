/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*   4:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   5:    */ import org.apache.xalan.xsltc.util.IntegerArray;
/*   6:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   7:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   8:    */ 
/*   9:    */ public final class CurrentNodeListIterator
/*  10:    */   extends DTMAxisIteratorBase
/*  11:    */ {
/*  12:    */   private boolean _docOrder;
/*  13:    */   private DTMAxisIterator _source;
/*  14:    */   private final CurrentNodeListFilter _filter;
/*  15: 63 */   private IntegerArray _nodes = new IntegerArray();
/*  16:    */   private int _currentIndex;
/*  17:    */   private final int _currentNode;
/*  18:    */   private AbstractTranslet _translet;
/*  19:    */   
/*  20:    */   public CurrentNodeListIterator(DTMAxisIterator source, CurrentNodeListFilter filter, int currentNode, AbstractTranslet translet)
/*  21:    */   {
/*  22: 85 */     this(source, !source.isReverse(), filter, currentNode, translet);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public CurrentNodeListIterator(DTMAxisIterator source, boolean docOrder, CurrentNodeListFilter filter, int currentNode, AbstractTranslet translet)
/*  26:    */   {
/*  27: 93 */     this._source = source;
/*  28: 94 */     this._filter = filter;
/*  29: 95 */     this._translet = translet;
/*  30: 96 */     this._docOrder = docOrder;
/*  31: 97 */     this._currentNode = currentNode;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DTMAxisIterator forceNaturalOrder()
/*  35:    */   {
/*  36:101 */     this._docOrder = true;
/*  37:102 */     return this;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setRestartable(boolean isRestartable)
/*  41:    */   {
/*  42:106 */     this._isRestartable = isRestartable;
/*  43:107 */     this._source.setRestartable(isRestartable);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isReverse()
/*  47:    */   {
/*  48:111 */     return !this._docOrder;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public DTMAxisIterator cloneIterator()
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55:116 */       CurrentNodeListIterator clone = (CurrentNodeListIterator)super.clone();
/*  56:    */       
/*  57:118 */       clone._nodes = ((IntegerArray)this._nodes.clone());
/*  58:119 */       clone._source = this._source.cloneIterator();
/*  59:120 */       clone._isRestartable = false;
/*  60:121 */       return clone.reset();
/*  61:    */     }
/*  62:    */     catch (CloneNotSupportedException e)
/*  63:    */     {
/*  64:124 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  65:    */     }
/*  66:126 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public DTMAxisIterator reset()
/*  70:    */   {
/*  71:131 */     this._currentIndex = 0;
/*  72:132 */     return resetPosition();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int next()
/*  76:    */   {
/*  77:136 */     int last = this._nodes.cardinality();
/*  78:137 */     int currentNode = this._currentNode;
/*  79:138 */     AbstractTranslet translet = this._translet;
/*  80:140 */     for (int index = this._currentIndex; index < last;)
/*  81:    */     {
/*  82:141 */       int position = this._docOrder ? index + 1 : last - index;
/*  83:142 */       int node = this._nodes.at(index++);
/*  84:144 */       if (this._filter.test(node, position, last, currentNode, translet, this))
/*  85:    */       {
/*  86:146 */         this._currentIndex = index;
/*  87:147 */         return returnNode(node);
/*  88:    */       }
/*  89:    */     }
/*  90:150 */     return -1;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public DTMAxisIterator setStartNode(int node)
/*  94:    */   {
/*  95:154 */     if (this._isRestartable)
/*  96:    */     {
/*  97:155 */       this._source.setStartNode(this._startNode = node);
/*  98:    */       
/*  99:157 */       this._nodes.clear();
/* 100:158 */       while ((node = this._source.next()) != -1) {
/* 101:159 */         this._nodes.add(node);
/* 102:    */       }
/* 103:161 */       this._currentIndex = 0;
/* 104:162 */       resetPosition();
/* 105:    */     }
/* 106:164 */     return this;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getLast()
/* 110:    */   {
/* 111:168 */     if (this._last == -1) {
/* 112:169 */       this._last = computePositionOfLast();
/* 113:    */     }
/* 114:171 */     return this._last;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setMark()
/* 118:    */   {
/* 119:175 */     this._markedNode = this._currentIndex;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void gotoMark()
/* 123:    */   {
/* 124:179 */     this._currentIndex = this._markedNode;
/* 125:    */   }
/* 126:    */   
/* 127:    */   private int computePositionOfLast()
/* 128:    */   {
/* 129:183 */     int last = this._nodes.cardinality();
/* 130:184 */     int currNode = this._currentNode;
/* 131:185 */     AbstractTranslet translet = this._translet;
/* 132:    */     
/* 133:187 */     int lastPosition = this._position;
/* 134:188 */     for (int index = this._currentIndex; index < last;)
/* 135:    */     {
/* 136:189 */       int position = this._docOrder ? index + 1 : last - index;
/* 137:190 */       int nodeIndex = this._nodes.at(index++);
/* 138:192 */       if (this._filter.test(nodeIndex, position, last, currNode, translet, this)) {
/* 139:194 */         lastPosition++;
/* 140:    */       }
/* 141:    */     }
/* 142:197 */     return lastPosition;
/* 143:    */   }
/* 144:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.CurrentNodeListIterator
 * JD-Core Version:    0.7.0.1
 */