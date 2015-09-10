/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.DOM;
/*   4:    */ import org.apache.xalan.xsltc.Translet;
/*   5:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   6:    */ 
/*   7:    */ public abstract class SingleNodeCounter
/*   8:    */   extends NodeCounter
/*   9:    */ {
/*  10: 35 */   private static final int[] EmptyArray = new int[0];
/*  11: 36 */   DTMAxisIterator _countSiblings = null;
/*  12:    */   
/*  13:    */   public SingleNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  14:    */   {
/*  15: 41 */     super(translet, document, iterator);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public NodeCounter setStartNode(int node)
/*  19:    */   {
/*  20: 45 */     this._node = node;
/*  21: 46 */     this._nodeType = this._document.getExpandedTypeID(node);
/*  22: 47 */     this._countSiblings = this._document.getAxisIterator(12);
/*  23: 48 */     return this;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getCounter()
/*  27:    */   {
/*  28:    */     int result;
/*  29: 53 */     if (this._value != -2147483648.0D)
/*  30:    */     {
/*  31: 55 */       if (this._value == 0.0D) {
/*  32: 55 */         return "0";
/*  33:    */       }
/*  34: 56 */       if (Double.isNaN(this._value)) {
/*  35: 56 */         return "NaN";
/*  36:    */       }
/*  37: 57 */       if ((this._value < 0.0D) && (Double.isInfinite(this._value))) {
/*  38: 57 */         return "-Infinity";
/*  39:    */       }
/*  40: 58 */       if (Double.isInfinite(this._value)) {
/*  41: 58 */         return "Infinity";
/*  42:    */       }
/*  43: 59 */       result = (int)this._value;
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47: 62 */       int next = this._node;
/*  48: 63 */       result = 0;
/*  49: 64 */       if (!matchesCount(next)) {
/*  50: 65 */         while ((next = this._document.getParent(next)) > -1)
/*  51:    */         {
/*  52: 66 */           if (matchesCount(next)) {
/*  53:    */             break;
/*  54:    */           }
/*  55: 69 */           if (matchesFrom(next))
/*  56:    */           {
/*  57: 70 */             next = -1;
/*  58: 71 */             break;
/*  59:    */           }
/*  60:    */         }
/*  61:    */       }
/*  62: 76 */       if (next != -1)
/*  63:    */       {
/*  64: 77 */         this._countSiblings.setStartNode(next);
/*  65:    */         do
/*  66:    */         {
/*  67: 79 */           if (matchesCount(next)) {
/*  68: 79 */             result++;
/*  69:    */           }
/*  70: 80 */         } while ((next = this._countSiblings.next()) != -1);
/*  71:    */       }
/*  72:    */       else
/*  73:    */       {
/*  74: 84 */         return formatNumbers(EmptyArray);
/*  75:    */       }
/*  76:    */     }
/*  77: 87 */     return formatNumbers(result);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static NodeCounter getDefaultNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  81:    */   {
/*  82: 93 */     return new DefaultSingleNodeCounter(translet, document, iterator);
/*  83:    */   }
/*  84:    */   
/*  85:    */   static class DefaultSingleNodeCounter
/*  86:    */     extends SingleNodeCounter
/*  87:    */   {
/*  88:    */     public DefaultSingleNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  89:    */     {
/*  90: 99 */       super(document, iterator);
/*  91:    */     }
/*  92:    */     
/*  93:    */     public NodeCounter setStartNode(int node)
/*  94:    */     {
/*  95:103 */       this._node = node;
/*  96:104 */       this._nodeType = this._document.getExpandedTypeID(node);
/*  97:105 */       this._countSiblings = this._document.getTypedAxisIterator(12, this._document.getExpandedTypeID(node));
/*  98:    */       
/*  99:    */ 
/* 100:108 */       return this;
/* 101:    */     }
/* 102:    */     
/* 103:    */     public String getCounter()
/* 104:    */     {
/* 105:    */       int result;
/* 106:113 */       if (this._value != -2147483648.0D)
/* 107:    */       {
/* 108:115 */         if (this._value == 0.0D) {
/* 109:115 */           return "0";
/* 110:    */         }
/* 111:116 */         if (Double.isNaN(this._value)) {
/* 112:116 */           return "NaN";
/* 113:    */         }
/* 114:117 */         if ((this._value < 0.0D) && (Double.isInfinite(this._value))) {
/* 115:117 */           return "-Infinity";
/* 116:    */         }
/* 117:118 */         if (Double.isInfinite(this._value)) {
/* 118:118 */           return "Infinity";
/* 119:    */         }
/* 120:119 */         result = (int)this._value;
/* 121:    */       }
/* 122:    */       else
/* 123:    */       {
/* 124:123 */         result = 1;
/* 125:124 */         this._countSiblings.setStartNode(this._node);
/* 126:    */         int next;
/* 127:125 */         while ((next = this._countSiblings.next()) != -1) {
/* 128:126 */           result++;
/* 129:    */         }
/* 130:    */       }
/* 131:129 */       return formatNumbers(result);
/* 132:    */     }
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.SingleNodeCounter
 * JD-Core Version:    0.7.0.1
 */