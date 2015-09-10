/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   4:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   5:    */ 
/*   6:    */ public abstract class DTMAxisIteratorBase
/*   7:    */   implements DTMAxisIterator
/*   8:    */ {
/*   9: 36 */   protected int _last = -1;
/*  10: 41 */   protected int _position = 0;
/*  11:    */   protected int _markedNode;
/*  12: 52 */   protected int _startNode = -1;
/*  13: 57 */   protected boolean _includeSelf = false;
/*  14: 63 */   protected boolean _isRestartable = true;
/*  15:    */   
/*  16:    */   public int getStartNode()
/*  17:    */   {
/*  18: 73 */     return this._startNode;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public DTMAxisIterator reset()
/*  22:    */   {
/*  23: 83 */     boolean temp = this._isRestartable;
/*  24:    */     
/*  25: 85 */     this._isRestartable = true;
/*  26:    */     
/*  27: 87 */     setStartNode(this._startNode);
/*  28:    */     
/*  29: 89 */     this._isRestartable = temp;
/*  30:    */     
/*  31: 91 */     return this;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DTMAxisIterator includeSelf()
/*  35:    */   {
/*  36:106 */     this._includeSelf = true;
/*  37:    */     
/*  38:108 */     return this;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getLast()
/*  42:    */   {
/*  43:125 */     if (this._last == -1)
/*  44:    */     {
/*  45:134 */       int temp = this._position;
/*  46:135 */       setMark();
/*  47:    */       
/*  48:137 */       reset();
/*  49:    */       do
/*  50:    */       {
/*  51:140 */         this._last += 1;
/*  52:142 */       } while (next() != -1);
/*  53:144 */       gotoMark();
/*  54:145 */       this._position = temp;
/*  55:    */     }
/*  56:148 */     return this._last;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getPosition()
/*  60:    */   {
/*  61:157 */     return this._position == 0 ? 1 : this._position;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isReverse()
/*  65:    */   {
/*  66:165 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public DTMAxisIterator cloneIterator()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:180 */       DTMAxisIteratorBase clone = (DTMAxisIteratorBase)super.clone();
/*  74:    */       
/*  75:182 */       clone._isRestartable = false;
/*  76:    */       
/*  77:    */ 
/*  78:185 */       return clone;
/*  79:    */     }
/*  80:    */     catch (CloneNotSupportedException e)
/*  81:    */     {
/*  82:189 */       throw new WrappedRuntimeException(e);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected final int returnNode(int node)
/*  87:    */   {
/*  88:213 */     this._position += 1;
/*  89:    */     
/*  90:215 */     return node;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected final DTMAxisIterator resetPosition()
/*  94:    */   {
/*  95:229 */     this._position = 0;
/*  96:    */     
/*  97:231 */     return this;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isDocOrdered()
/* 101:    */   {
/* 102:242 */     return true;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getAxis()
/* 106:    */   {
/* 107:253 */     return -1;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setRestartable(boolean isRestartable)
/* 111:    */   {
/* 112:257 */     this._isRestartable = isRestartable;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getNodeByPosition(int position)
/* 116:    */   {
/* 117:268 */     if (position > 0)
/* 118:    */     {
/* 119:269 */       int pos = isReverse() ? getLast() - position + 1 : position;
/* 120:    */       int node;
/* 121:272 */       while ((node = next()) != -1) {
/* 122:273 */         if (pos == getPosition())
/* 123:    */         {
/* 124:    */           int i;
/* 125:274 */           return i;
/* 126:    */         }
/* 127:    */       }
/* 128:    */     }
/* 129:278 */     return -1;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public abstract DTMAxisIterator setStartNode(int paramInt);
/* 133:    */   
/* 134:    */   public abstract void gotoMark();
/* 135:    */   
/* 136:    */   public abstract void setMark();
/* 137:    */   
/* 138:    */   public abstract int next();
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMAxisIteratorBase
 * JD-Core Version:    0.7.0.1
 */