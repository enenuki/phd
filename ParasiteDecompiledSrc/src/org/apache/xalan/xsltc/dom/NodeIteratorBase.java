/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.NodeIterator;
/*   4:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   5:    */ 
/*   6:    */ public abstract class NodeIteratorBase
/*   7:    */   implements NodeIterator
/*   8:    */ {
/*   9: 37 */   protected int _last = -1;
/*  10: 43 */   protected int _position = 0;
/*  11:    */   protected int _markedNode;
/*  12: 53 */   protected int _startNode = -1;
/*  13: 58 */   protected boolean _includeSelf = false;
/*  14: 63 */   protected boolean _isRestartable = true;
/*  15:    */   
/*  16:    */   public void setRestartable(boolean isRestartable)
/*  17:    */   {
/*  18: 69 */     this._isRestartable = isRestartable;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public abstract NodeIterator setStartNode(int paramInt);
/*  22:    */   
/*  23:    */   public NodeIterator reset()
/*  24:    */   {
/*  25: 84 */     boolean temp = this._isRestartable;
/*  26: 85 */     this._isRestartable = true;
/*  27:    */     
/*  28: 87 */     setStartNode(this._includeSelf ? this._startNode + 1 : this._startNode);
/*  29: 88 */     this._isRestartable = temp;
/*  30: 89 */     return this;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public NodeIterator includeSelf()
/*  34:    */   {
/*  35: 96 */     this._includeSelf = true;
/*  36: 97 */     return this;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getLast()
/*  40:    */   {
/*  41:106 */     if (this._last == -1)
/*  42:    */     {
/*  43:107 */       int temp = this._position;
/*  44:108 */       setMark();
/*  45:109 */       reset();
/*  46:    */       do
/*  47:    */       {
/*  48:111 */         this._last += 1;
/*  49:112 */       } while (next() != -1);
/*  50:113 */       gotoMark();
/*  51:114 */       this._position = temp;
/*  52:    */     }
/*  53:116 */     return this._last;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getPosition()
/*  57:    */   {
/*  58:123 */     return this._position == 0 ? 1 : this._position;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isReverse()
/*  62:    */   {
/*  63:132 */     return false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public NodeIterator cloneIterator()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:143 */       NodeIteratorBase clone = (NodeIteratorBase)super.clone();
/*  71:144 */       clone._isRestartable = false;
/*  72:145 */       return clone.reset();
/*  73:    */     }
/*  74:    */     catch (CloneNotSupportedException e)
/*  75:    */     {
/*  76:148 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  77:    */     }
/*  78:150 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected final int returnNode(int node)
/*  82:    */   {
/*  83:159 */     this._position += 1;
/*  84:160 */     return node;
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected final NodeIterator resetPosition()
/*  88:    */   {
/*  89:167 */     this._position = 0;
/*  90:168 */     return this;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public abstract void gotoMark();
/*  94:    */   
/*  95:    */   public abstract void setMark();
/*  96:    */   
/*  97:    */   public abstract int next();
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.NodeIteratorBase
 * JD-Core Version:    0.7.0.1
 */