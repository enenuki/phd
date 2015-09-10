/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   6:    */ 
/*   7:    */ public class StepIterator
/*   8:    */   extends DTMAxisIteratorBase
/*   9:    */ {
/*  10:    */   protected DTMAxisIterator _source;
/*  11:    */   protected DTMAxisIterator _iterator;
/*  12: 57 */   private int _pos = -1;
/*  13:    */   
/*  14:    */   public StepIterator(DTMAxisIterator source, DTMAxisIterator iterator)
/*  15:    */   {
/*  16: 60 */     this._source = source;
/*  17: 61 */     this._iterator = iterator;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setRestartable(boolean isRestartable)
/*  21:    */   {
/*  22: 68 */     this._isRestartable = isRestartable;
/*  23: 69 */     this._source.setRestartable(isRestartable);
/*  24: 70 */     this._iterator.setRestartable(true);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public DTMAxisIterator cloneIterator()
/*  28:    */   {
/*  29: 74 */     this._isRestartable = false;
/*  30:    */     try
/*  31:    */     {
/*  32: 76 */       StepIterator clone = (StepIterator)super.clone();
/*  33: 77 */       clone._source = this._source.cloneIterator();
/*  34: 78 */       clone._iterator = this._iterator.cloneIterator();
/*  35: 79 */       clone._iterator.setRestartable(true);
/*  36: 80 */       clone._isRestartable = false;
/*  37: 81 */       return clone.reset();
/*  38:    */     }
/*  39:    */     catch (CloneNotSupportedException e)
/*  40:    */     {
/*  41: 84 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  42:    */     }
/*  43: 86 */     return null;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public DTMAxisIterator setStartNode(int node)
/*  47:    */   {
/*  48: 91 */     if (this._isRestartable)
/*  49:    */     {
/*  50: 93 */       this._source.setStartNode(this._startNode = node);
/*  51:    */       
/*  52:    */ 
/*  53:    */ 
/*  54: 97 */       this._iterator.setStartNode(this._includeSelf ? this._startNode : this._source.next());
/*  55: 98 */       return resetPosition();
/*  56:    */     }
/*  57:100 */     return this;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public DTMAxisIterator reset()
/*  61:    */   {
/*  62:104 */     this._source.reset();
/*  63:    */     
/*  64:106 */     this._iterator.setStartNode(this._includeSelf ? this._startNode : this._source.next());
/*  65:107 */     return resetPosition();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int next()
/*  69:    */   {
/*  70:    */     for (;;)
/*  71:    */     {
/*  72:    */       int node;
/*  73:113 */       if ((node = this._iterator.next()) != -1) {
/*  74:114 */         return returnNode(node);
/*  75:    */       }
/*  76:117 */       if ((node = this._source.next()) == -1) {
/*  77:118 */         return -1;
/*  78:    */       }
/*  79:122 */       this._iterator.setStartNode(node);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setMark()
/*  84:    */   {
/*  85:128 */     this._source.setMark();
/*  86:129 */     this._iterator.setMark();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void gotoMark()
/*  90:    */   {
/*  91:134 */     this._source.gotoMark();
/*  92:135 */     this._iterator.gotoMark();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.StepIterator
 * JD-Core Version:    0.7.0.1
 */