/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.DOM;
/*   4:    */ import org.apache.xalan.xsltc.Translet;
/*   5:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   6:    */ 
/*   7:    */ public abstract class AnyNodeCounter
/*   8:    */   extends NodeCounter
/*   9:    */ {
/*  10:    */   public AnyNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  11:    */   {
/*  12: 35 */     super(translet, document, iterator);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public NodeCounter setStartNode(int node)
/*  16:    */   {
/*  17: 39 */     this._node = node;
/*  18: 40 */     this._nodeType = this._document.getExpandedTypeID(node);
/*  19: 41 */     return this;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getCounter()
/*  23:    */   {
/*  24: 46 */     if (this._value != -2147483648.0D)
/*  25:    */     {
/*  26: 48 */       if (this._value == 0.0D) {
/*  27: 48 */         return "0";
/*  28:    */       }
/*  29: 49 */       if (Double.isNaN(this._value)) {
/*  30: 49 */         return "NaN";
/*  31:    */       }
/*  32: 50 */       if ((this._value < 0.0D) && (Double.isInfinite(this._value))) {
/*  33: 50 */         return "-Infinity";
/*  34:    */       }
/*  35: 51 */       if (Double.isInfinite(this._value)) {
/*  36: 51 */         return "Infinity";
/*  37:    */       }
/*  38: 52 */       return formatNumbers((int)this._value);
/*  39:    */     }
/*  40: 55 */     int next = this._node;
/*  41: 56 */     int root = this._document.getDocument();
/*  42: 57 */     int result = 0;
/*  43: 58 */     while ((next >= root) && (!matchesFrom(next)))
/*  44:    */     {
/*  45: 59 */       if (matchesCount(next)) {
/*  46: 60 */         result++;
/*  47:    */       }
/*  48: 62 */       next--;
/*  49:    */     }
/*  50: 75 */     return formatNumbers(result);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static NodeCounter getDefaultNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  54:    */   {
/*  55: 81 */     return new DefaultAnyNodeCounter(translet, document, iterator);
/*  56:    */   }
/*  57:    */   
/*  58:    */   static class DefaultAnyNodeCounter
/*  59:    */     extends AnyNodeCounter
/*  60:    */   {
/*  61:    */     public DefaultAnyNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  62:    */     {
/*  63: 87 */       super(document, iterator);
/*  64:    */     }
/*  65:    */     
/*  66:    */     public String getCounter()
/*  67:    */     {
/*  68:    */       int result;
/*  69: 92 */       if (this._value != -2147483648.0D)
/*  70:    */       {
/*  71: 94 */         if (this._value == 0.0D) {
/*  72: 94 */           return "0";
/*  73:    */         }
/*  74: 95 */         if (Double.isNaN(this._value)) {
/*  75: 95 */           return "NaN";
/*  76:    */         }
/*  77: 96 */         if ((this._value < 0.0D) && (Double.isInfinite(this._value))) {
/*  78: 96 */           return "-Infinity";
/*  79:    */         }
/*  80: 97 */         if (Double.isInfinite(this._value)) {
/*  81: 97 */           return "Infinity";
/*  82:    */         }
/*  83: 98 */         result = (int)this._value;
/*  84:    */       }
/*  85:    */       else
/*  86:    */       {
/*  87:101 */         int next = this._node;
/*  88:102 */         result = 0;
/*  89:103 */         int ntype = this._document.getExpandedTypeID(this._node);
/*  90:104 */         int root = this._document.getDocument();
/*  91:105 */         while (next >= 0)
/*  92:    */         {
/*  93:106 */           if (ntype == this._document.getExpandedTypeID(next)) {
/*  94:107 */             result++;
/*  95:    */           }
/*  96:111 */           if (next == root) {
/*  97:    */             break;
/*  98:    */           }
/*  99:115 */           next--;
/* 100:    */         }
/* 101:    */       }
/* 102:119 */       return formatNumbers(result);
/* 103:    */     }
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.AnyNodeCounter
 * JD-Core Version:    0.7.0.1
 */