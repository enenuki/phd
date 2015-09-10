/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   4:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   5:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   6:    */ 
/*   7:    */ public final class SortingIterator
/*   8:    */   extends DTMAxisIteratorBase
/*   9:    */ {
/*  10:    */   private static final int INIT_DATA_SIZE = 16;
/*  11:    */   private DTMAxisIterator _source;
/*  12:    */   private NodeSortRecordFactory _factory;
/*  13:    */   private NodeSortRecord[] _data;
/*  14: 40 */   private int _free = 0;
/*  15:    */   private int _current;
/*  16:    */   
/*  17:    */   public SortingIterator(DTMAxisIterator source, NodeSortRecordFactory factory)
/*  18:    */   {
/*  19: 45 */     this._source = source;
/*  20: 46 */     this._factory = factory;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int next()
/*  24:    */   {
/*  25: 50 */     return this._current < this._free ? this._data[(this._current++)].getNode() : -1;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public DTMAxisIterator setStartNode(int node)
/*  29:    */   {
/*  30:    */     try
/*  31:    */     {
/*  32: 55 */       this._source.setStartNode(this._startNode = node);
/*  33: 56 */       this._data = new NodeSortRecord[16];
/*  34: 57 */       this._free = 0;
/*  35: 60 */       while ((node = this._source.next()) != -1) {
/*  36: 61 */         addRecord(this._factory.makeNodeSortRecord(node, this._free));
/*  37:    */       }
/*  38: 64 */       quicksort(0, this._free - 1);
/*  39:    */       
/*  40: 66 */       this._current = 0;
/*  41: 67 */       return this;
/*  42:    */     }
/*  43:    */     catch (Exception e) {}
/*  44: 70 */     return this;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getPosition()
/*  48:    */   {
/*  49: 75 */     return this._current == 0 ? 1 : this._current;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getLast()
/*  53:    */   {
/*  54: 79 */     return this._free;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setMark()
/*  58:    */   {
/*  59: 83 */     this._source.setMark();
/*  60: 84 */     this._markedNode = this._current;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void gotoMark()
/*  64:    */   {
/*  65: 88 */     this._source.gotoMark();
/*  66: 89 */     this._current = this._markedNode;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public DTMAxisIterator cloneIterator()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73: 99 */       SortingIterator clone = (SortingIterator)super.clone();
/*  74:100 */       clone._source = this._source.cloneIterator();
/*  75:101 */       clone._factory = this._factory;
/*  76:102 */       clone._data = this._data;
/*  77:103 */       clone._free = this._free;
/*  78:104 */       clone._current = this._current;
/*  79:105 */       clone.setRestartable(false);
/*  80:106 */       return clone.reset();
/*  81:    */     }
/*  82:    */     catch (CloneNotSupportedException e)
/*  83:    */     {
/*  84:109 */       BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  85:    */     }
/*  86:111 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void addRecord(NodeSortRecord record)
/*  90:    */   {
/*  91:116 */     if (this._free == this._data.length)
/*  92:    */     {
/*  93:117 */       NodeSortRecord[] newArray = new NodeSortRecord[this._data.length * 2];
/*  94:118 */       System.arraycopy(this._data, 0, newArray, 0, this._free);
/*  95:119 */       this._data = newArray;
/*  96:    */     }
/*  97:121 */     this._data[(this._free++)] = record;
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void quicksort(int p, int r)
/* 101:    */   {
/* 102:125 */     while (p < r)
/* 103:    */     {
/* 104:126 */       int q = partition(p, r);
/* 105:127 */       quicksort(p, q);
/* 106:128 */       p = q + 1;
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   private int partition(int p, int r)
/* 111:    */   {
/* 112:133 */     NodeSortRecord x = this._data[(p + r >>> 1)];
/* 113:134 */     int i = p - 1;
/* 114:135 */     int j = r + 1;
/* 115:    */     for (;;)
/* 116:    */     {
/* 117:137 */       if (x.compareTo(this._data[(--j)]) >= 0)
/* 118:    */       {
/* 119:138 */         while ((goto 47) || (x.compareTo(this._data[(++i)]) > 0)) {}
/* 120:139 */         if (i >= j) {
/* 121:    */           break;
/* 122:    */         }
/* 123:140 */         NodeSortRecord t = this._data[i];
/* 124:141 */         this._data[i] = this._data[j];
/* 125:142 */         this._data[j] = t;
/* 126:    */       }
/* 127:    */     }
/* 128:145 */     return j;
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.SortingIterator
 * JD-Core Version:    0.7.0.1
 */