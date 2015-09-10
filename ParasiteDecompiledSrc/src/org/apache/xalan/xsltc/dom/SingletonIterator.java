/*  1:   */ package org.apache.xalan.xsltc.dom;
/*  2:   */ 
/*  3:   */ import org.apache.xml.dtm.DTMAxisIterator;
/*  4:   */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*  5:   */ 
/*  6:   */ public class SingletonIterator
/*  7:   */   extends DTMAxisIteratorBase
/*  8:   */ {
/*  9:   */   private int _node;
/* 10:   */   private final boolean _isConstant;
/* 11:   */   
/* 12:   */   public SingletonIterator()
/* 13:   */   {
/* 14:36 */     this(-2147483648, false);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public SingletonIterator(int node)
/* 18:   */   {
/* 19:40 */     this(node, false);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public SingletonIterator(int node, boolean constant)
/* 23:   */   {
/* 24:44 */     this._node = (this._startNode = node);
/* 25:45 */     this._isConstant = constant;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public DTMAxisIterator setStartNode(int node)
/* 29:   */   {
/* 30:53 */     if (this._isConstant)
/* 31:   */     {
/* 32:54 */       this._node = this._startNode;
/* 33:55 */       return resetPosition();
/* 34:   */     }
/* 35:57 */     if (this._isRestartable)
/* 36:   */     {
/* 37:58 */       if (this._node <= 0) {
/* 38:59 */         this._node = (this._startNode = node);
/* 39:   */       }
/* 40:60 */       return resetPosition();
/* 41:   */     }
/* 42:62 */     return this;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public DTMAxisIterator reset()
/* 46:   */   {
/* 47:66 */     if (this._isConstant)
/* 48:   */     {
/* 49:67 */       this._node = this._startNode;
/* 50:68 */       return resetPosition();
/* 51:   */     }
/* 52:71 */     boolean temp = this._isRestartable;
/* 53:72 */     this._isRestartable = true;
/* 54:73 */     setStartNode(this._startNode);
/* 55:74 */     this._isRestartable = temp;
/* 56:   */     
/* 57:76 */     return this;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int next()
/* 61:   */   {
/* 62:80 */     int result = this._node;
/* 63:81 */     this._node = -1;
/* 64:82 */     return returnNode(result);
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void setMark()
/* 68:   */   {
/* 69:86 */     this._markedNode = this._node;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void gotoMark()
/* 73:   */   {
/* 74:90 */     this._node = this._markedNode;
/* 75:   */   }
/* 76:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.SingletonIterator
 * JD-Core Version:    0.7.0.1
 */