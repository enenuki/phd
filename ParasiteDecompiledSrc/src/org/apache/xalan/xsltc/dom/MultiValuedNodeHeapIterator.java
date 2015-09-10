/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   6:    */ 
/*   7:    */ public abstract class MultiValuedNodeHeapIterator
/*   8:    */   extends DTMAxisIteratorBase
/*   9:    */ {
/*  10:    */   private static final int InitSize = 8;
/*  11:    */   
/*  12:    */   public abstract class HeapNode
/*  13:    */     implements Cloneable
/*  14:    */   {
/*  15:    */     protected int _node;
/*  16:    */     protected int _markedNode;
/*  17: 57 */     protected boolean _isStartSet = false;
/*  18:    */     
/*  19:    */     public HeapNode() {}
/*  20:    */     
/*  21:    */     public abstract int step();
/*  22:    */     
/*  23:    */     public HeapNode cloneHeapNode()
/*  24:    */     {
/*  25:    */       HeapNode clone;
/*  26:    */       try
/*  27:    */       {
/*  28: 77 */         clone = (HeapNode)super.clone();
/*  29:    */       }
/*  30:    */       catch (CloneNotSupportedException e)
/*  31:    */       {
/*  32: 79 */         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  33:    */         
/*  34: 81 */         return null;
/*  35:    */       }
/*  36: 84 */       clone._node = this._node;
/*  37: 85 */       clone._markedNode = this._node;
/*  38:    */       
/*  39: 87 */       return clone;
/*  40:    */     }
/*  41:    */     
/*  42:    */     public void setMark()
/*  43:    */     {
/*  44: 94 */       this._markedNode = this._node;
/*  45:    */     }
/*  46:    */     
/*  47:    */     public void gotoMark()
/*  48:    */     {
/*  49:101 */       this._node = this._markedNode;
/*  50:    */     }
/*  51:    */     
/*  52:    */     public abstract boolean isLessThan(HeapNode paramHeapNode);
/*  53:    */     
/*  54:    */     public abstract HeapNode setStartNode(int paramInt);
/*  55:    */     
/*  56:    */     public abstract HeapNode reset();
/*  57:    */   }
/*  58:    */   
/*  59:134 */   private int _heapSize = 0;
/*  60:135 */   private int _size = 8;
/*  61:136 */   private HeapNode[] _heap = new HeapNode[8];
/*  62:137 */   private int _free = 0;
/*  63:    */   private int _returnedLast;
/*  64:144 */   private int _cachedReturnedLast = -1;
/*  65:    */   private int _cachedHeapSize;
/*  66:    */   
/*  67:    */   public DTMAxisIterator cloneIterator()
/*  68:    */   {
/*  69:151 */     this._isRestartable = false;
/*  70:152 */     HeapNode[] heapCopy = new HeapNode[this._heap.length];
/*  71:    */     try
/*  72:    */     {
/*  73:154 */       MultiValuedNodeHeapIterator clone = (MultiValuedNodeHeapIterator)super.clone();
/*  74:157 */       for (int i = 0; i < this._free; i++) {
/*  75:158 */         heapCopy[i] = this._heap[i].cloneHeapNode();
/*  76:    */       }
/*  77:160 */       clone.setRestartable(false);
/*  78:161 */       clone._heap = heapCopy;
/*  79:162 */       return clone.reset();
/*  80:    */     }
/*  81:    */     catch (CloneNotSupportedException e)
/*  82:    */     {
/*  83:165 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  84:    */     }
/*  85:167 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void addHeapNode(HeapNode node)
/*  89:    */   {
/*  90:172 */     if (this._free == this._size)
/*  91:    */     {
/*  92:173 */       HeapNode[] newArray = new HeapNode[this._size *= 2];
/*  93:174 */       System.arraycopy(this._heap, 0, newArray, 0, this._free);
/*  94:175 */       this._heap = newArray;
/*  95:    */     }
/*  96:177 */     this._heapSize += 1;
/*  97:178 */     this._heap[(this._free++)] = node;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int next()
/* 101:    */   {
/* 102:182 */     while (this._heapSize > 0)
/* 103:    */     {
/* 104:183 */       int smallest = this._heap[0]._node;
/* 105:184 */       if (smallest == -1)
/* 106:    */       {
/* 107:185 */         if (this._heapSize > 1)
/* 108:    */         {
/* 109:187 */           HeapNode temp = this._heap[0];
/* 110:188 */           this._heap[0] = this._heap[(--this._heapSize)];
/* 111:189 */           this._heap[this._heapSize] = temp;
/* 112:    */         }
/* 113:    */         else
/* 114:    */         {
/* 115:192 */           return -1;
/* 116:    */         }
/* 117:    */       }
/* 118:195 */       else if (smallest == this._returnedLast)
/* 119:    */       {
/* 120:196 */         this._heap[0].step();
/* 121:    */       }
/* 122:    */       else
/* 123:    */       {
/* 124:199 */         this._heap[0].step();
/* 125:200 */         heapify(0);
/* 126:201 */         return returnNode(this._returnedLast = smallest);
/* 127:    */       }
/* 128:204 */       heapify(0);
/* 129:    */     }
/* 130:206 */     return -1;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public DTMAxisIterator setStartNode(int node)
/* 134:    */   {
/* 135:210 */     if (this._isRestartable)
/* 136:    */     {
/* 137:211 */       this._startNode = node;
/* 138:212 */       for (int i = 0; i < this._free; i++) {
/* 139:213 */         if (!this._heap[i]._isStartSet)
/* 140:    */         {
/* 141:214 */           this._heap[i].setStartNode(node);
/* 142:215 */           this._heap[i].step();
/* 143:216 */           this._heap[i]._isStartSet = true;
/* 144:    */         }
/* 145:    */       }
/* 146:220 */       for (int i = (this._heapSize = this._free) / 2; i >= 0; i--) {
/* 147:221 */         heapify(i);
/* 148:    */       }
/* 149:223 */       this._returnedLast = -1;
/* 150:224 */       return resetPosition();
/* 151:    */     }
/* 152:226 */     return this;
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected void init()
/* 156:    */   {
/* 157:230 */     for (int i = 0; i < this._free; i++) {
/* 158:231 */       this._heap[i] = null;
/* 159:    */     }
/* 160:234 */     this._heapSize = 0;
/* 161:235 */     this._free = 0;
/* 162:    */   }
/* 163:    */   
/* 164:    */   private void heapify(int i)
/* 165:    */   {
/* 166:    */     for (;;)
/* 167:    */     {
/* 168:243 */       int r = i + 1 << 1;int l = r - 1;
/* 169:244 */       int smallest = (l < this._heapSize) && (this._heap[l].isLessThan(this._heap[i])) ? l : i;
/* 170:246 */       if ((r < this._heapSize) && (this._heap[r].isLessThan(this._heap[smallest]))) {
/* 171:247 */         smallest = r;
/* 172:    */       }
/* 173:249 */       if (smallest == i) {
/* 174:    */         break;
/* 175:    */       }
/* 176:250 */       HeapNode temp = this._heap[smallest];
/* 177:251 */       this._heap[smallest] = this._heap[i];
/* 178:252 */       this._heap[i] = temp;
/* 179:253 */       i = smallest;
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setMark()
/* 184:    */   {
/* 185:261 */     for (int i = 0; i < this._free; i++) {
/* 186:262 */       this._heap[i].setMark();
/* 187:    */     }
/* 188:264 */     this._cachedReturnedLast = this._returnedLast;
/* 189:265 */     this._cachedHeapSize = this._heapSize;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void gotoMark()
/* 193:    */   {
/* 194:269 */     for (int i = 0; i < this._free; i++) {
/* 195:270 */       this._heap[i].gotoMark();
/* 196:    */     }
/* 197:273 */     for (int i = (this._heapSize = this._cachedHeapSize) / 2; i >= 0; i--) {
/* 198:274 */       heapify(i);
/* 199:    */     }
/* 200:276 */     this._returnedLast = this._cachedReturnedLast;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public DTMAxisIterator reset()
/* 204:    */   {
/* 205:280 */     for (int i = 0; i < this._free; i++)
/* 206:    */     {
/* 207:281 */       this._heap[i].reset();
/* 208:282 */       this._heap[i].step();
/* 209:    */     }
/* 210:286 */     for (int i = (this._heapSize = this._free) / 2; i >= 0; i--) {
/* 211:287 */       heapify(i);
/* 212:    */     }
/* 213:290 */     this._returnedLast = -1;
/* 214:291 */     return resetPosition();
/* 215:    */   }
/* 216:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.MultiValuedNodeHeapIterator
 * JD-Core Version:    0.7.0.1
 */