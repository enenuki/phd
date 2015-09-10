/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.DOM;
/*   4:    */ import org.apache.xalan.xsltc.Translet;
/*   5:    */ import org.apache.xalan.xsltc.util.IntegerArray;
/*   6:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   7:    */ 
/*   8:    */ public abstract class MultipleNodeCounter
/*   9:    */   extends NodeCounter
/*  10:    */ {
/*  11: 35 */   private DTMAxisIterator _precSiblings = null;
/*  12:    */   
/*  13:    */   public MultipleNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  14:    */   {
/*  15: 39 */     super(translet, document, iterator);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public NodeCounter setStartNode(int node)
/*  19:    */   {
/*  20: 43 */     this._node = node;
/*  21: 44 */     this._nodeType = this._document.getExpandedTypeID(node);
/*  22: 45 */     this._precSiblings = this._document.getAxisIterator(12);
/*  23: 46 */     return this;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getCounter()
/*  27:    */   {
/*  28: 50 */     if (this._value != -2147483648.0D)
/*  29:    */     {
/*  30: 52 */       if (this._value == 0.0D) {
/*  31: 52 */         return "0";
/*  32:    */       }
/*  33: 53 */       if (Double.isNaN(this._value)) {
/*  34: 53 */         return "NaN";
/*  35:    */       }
/*  36: 54 */       if ((this._value < 0.0D) && (Double.isInfinite(this._value))) {
/*  37: 54 */         return "-Infinity";
/*  38:    */       }
/*  39: 55 */       if (Double.isInfinite(this._value)) {
/*  40: 55 */         return "Infinity";
/*  41:    */       }
/*  42: 56 */       return formatNumbers((int)this._value);
/*  43:    */     }
/*  44: 59 */     IntegerArray ancestors = new IntegerArray();
/*  45:    */     
/*  46:    */ 
/*  47: 62 */     int next = this._node;
/*  48: 63 */     ancestors.add(next);
/*  49: 64 */     while (((next = this._document.getParent(next)) > -1) && (!matchesFrom(next))) {
/*  50: 66 */       ancestors.add(next);
/*  51:    */     }
/*  52: 70 */     int nAncestors = ancestors.cardinality();
/*  53: 71 */     int[] counters = new int[nAncestors];
/*  54: 72 */     for (int i = 0; i < nAncestors; i++) {
/*  55: 73 */       counters[i] = -2147483648;
/*  56:    */     }
/*  57: 77 */     int j = 0;
/*  58: 77 */     for (int i = nAncestors - 1; i >= 0; j++)
/*  59:    */     {
/*  60: 78 */       int counter = counters[j];
/*  61: 79 */       int ancestor = ancestors.at(i);
/*  62: 81 */       if (matchesCount(ancestor))
/*  63:    */       {
/*  64: 82 */         this._precSiblings.setStartNode(ancestor);
/*  65: 83 */         while ((next = this._precSiblings.next()) != -1) {
/*  66: 84 */           if (matchesCount(next)) {
/*  67: 85 */             counters[j] = (counters[j] == -2147483648 ? 1 : counters[j] + 1);
/*  68:    */           }
/*  69:    */         }
/*  70: 90 */         counters[j] = (counters[j] == -2147483648 ? 1 : counters[j] + 1);
/*  71:    */       }
/*  72: 77 */       i--;
/*  73:    */     }
/*  74: 95 */     return formatNumbers(counters);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static NodeCounter getDefaultNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  78:    */   {
/*  79:101 */     return new DefaultMultipleNodeCounter(translet, document, iterator);
/*  80:    */   }
/*  81:    */   
/*  82:    */   static class DefaultMultipleNodeCounter
/*  83:    */     extends MultipleNodeCounter
/*  84:    */   {
/*  85:    */     public DefaultMultipleNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  86:    */     {
/*  87:108 */       super(document, iterator);
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.MultipleNodeCounter
 * JD-Core Version:    0.7.0.1
 */