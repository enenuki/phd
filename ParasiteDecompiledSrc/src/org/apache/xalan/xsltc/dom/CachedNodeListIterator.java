/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.util.IntegerArray;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   6:    */ 
/*   7:    */ public final class CachedNodeListIterator
/*   8:    */   extends DTMAxisIteratorBase
/*   9:    */ {
/*  10:    */   private DTMAxisIterator _source;
/*  11: 40 */   private IntegerArray _nodes = new IntegerArray();
/*  12: 41 */   private int _numCachedNodes = 0;
/*  13: 42 */   private int _index = 0;
/*  14: 43 */   private boolean _isEnded = false;
/*  15:    */   
/*  16:    */   public CachedNodeListIterator(DTMAxisIterator source)
/*  17:    */   {
/*  18: 46 */     this._source = source;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setRestartable(boolean isRestartable) {}
/*  22:    */   
/*  23:    */   public DTMAxisIterator setStartNode(int node)
/*  24:    */   {
/*  25: 55 */     if (this._isRestartable)
/*  26:    */     {
/*  27: 56 */       this._startNode = node;
/*  28: 57 */       this._source.setStartNode(node);
/*  29: 58 */       resetPosition();
/*  30:    */       
/*  31: 60 */       this._isRestartable = false;
/*  32:    */     }
/*  33: 62 */     return this;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int next()
/*  37:    */   {
/*  38: 66 */     return getNode(this._index++);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getPosition()
/*  42:    */   {
/*  43: 70 */     return this._index == 0 ? 1 : this._index;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getNodeByPosition(int pos)
/*  47:    */   {
/*  48: 74 */     return getNode(pos);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getNode(int index)
/*  52:    */   {
/*  53: 78 */     if (index < this._numCachedNodes) {
/*  54: 79 */       return this._nodes.at(index);
/*  55:    */     }
/*  56: 81 */     if (!this._isEnded)
/*  57:    */     {
/*  58: 82 */       int node = this._source.next();
/*  59: 83 */       if (node != -1)
/*  60:    */       {
/*  61: 84 */         this._nodes.add(node);
/*  62: 85 */         this._numCachedNodes += 1;
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66: 88 */         this._isEnded = true;
/*  67:    */       }
/*  68: 90 */       return node;
/*  69:    */     }
/*  70: 93 */     return -1;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public DTMAxisIterator cloneIterator()
/*  74:    */   {
/*  75: 97 */     ClonedNodeListIterator clone = new ClonedNodeListIterator(this);
/*  76: 98 */     return clone;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public DTMAxisIterator reset()
/*  80:    */   {
/*  81:102 */     this._index = 0;
/*  82:103 */     return this;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setMark()
/*  86:    */   {
/*  87:107 */     this._source.setMark();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void gotoMark()
/*  91:    */   {
/*  92:111 */     this._source.gotoMark();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.CachedNodeListIterator
 * JD-Core Version:    0.7.0.1
 */