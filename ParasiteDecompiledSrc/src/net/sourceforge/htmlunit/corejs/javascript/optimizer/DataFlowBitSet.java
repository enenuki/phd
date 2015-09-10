/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*   2:    */ 
/*   3:    */ class DataFlowBitSet
/*   4:    */ {
/*   5:    */   private int[] itsBits;
/*   6:    */   private int itsSize;
/*   7:    */   
/*   8:    */   DataFlowBitSet(int size)
/*   9:    */   {
/*  10: 49 */     this.itsSize = size;
/*  11: 50 */     this.itsBits = new int[size + 31 >> 5];
/*  12:    */   }
/*  13:    */   
/*  14:    */   void set(int n)
/*  15:    */   {
/*  16: 55 */     if ((0 > n) || (n >= this.itsSize)) {
/*  17: 55 */       badIndex(n);
/*  18:    */     }
/*  19: 56 */     this.itsBits[(n >> 5)] |= 1 << (n & 0x1F);
/*  20:    */   }
/*  21:    */   
/*  22:    */   boolean test(int n)
/*  23:    */   {
/*  24: 61 */     if ((0 > n) || (n >= this.itsSize)) {
/*  25: 61 */       badIndex(n);
/*  26:    */     }
/*  27: 62 */     return (this.itsBits[(n >> 5)] & 1 << (n & 0x1F)) != 0;
/*  28:    */   }
/*  29:    */   
/*  30:    */   void not()
/*  31:    */   {
/*  32: 67 */     int bitsLength = this.itsBits.length;
/*  33: 68 */     for (int i = 0; i < bitsLength; i++) {
/*  34: 69 */       this.itsBits[i] ^= 0xFFFFFFFF;
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   void clear(int n)
/*  39:    */   {
/*  40: 74 */     if ((0 > n) || (n >= this.itsSize)) {
/*  41: 74 */       badIndex(n);
/*  42:    */     }
/*  43: 75 */     this.itsBits[(n >> 5)] &= (1 << (n & 0x1F) ^ 0xFFFFFFFF);
/*  44:    */   }
/*  45:    */   
/*  46:    */   void clear()
/*  47:    */   {
/*  48: 80 */     int bitsLength = this.itsBits.length;
/*  49: 81 */     for (int i = 0; i < bitsLength; i++) {
/*  50: 82 */       this.itsBits[i] = 0;
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   void or(DataFlowBitSet b)
/*  55:    */   {
/*  56: 87 */     int bitsLength = this.itsBits.length;
/*  57: 88 */     for (int i = 0; i < bitsLength; i++) {
/*  58: 89 */       this.itsBits[i] |= b.itsBits[i];
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String toString()
/*  63:    */   {
/*  64: 95 */     StringBuffer sb = new StringBuffer();
/*  65: 96 */     sb.append("DataFlowBitSet, size = ");
/*  66: 97 */     sb.append(this.itsSize);
/*  67: 98 */     sb.append('\n');
/*  68: 99 */     int bitsLength = this.itsBits.length;
/*  69:100 */     for (int i = 0; i < bitsLength; i++)
/*  70:    */     {
/*  71:101 */       sb.append(Integer.toHexString(this.itsBits[i]));
/*  72:102 */       sb.append(' ');
/*  73:    */     }
/*  74:104 */     return sb.toString();
/*  75:    */   }
/*  76:    */   
/*  77:    */   boolean df(DataFlowBitSet in, DataFlowBitSet gen, DataFlowBitSet notKill)
/*  78:    */   {
/*  79:109 */     int bitsLength = this.itsBits.length;
/*  80:110 */     boolean changed = false;
/*  81:111 */     for (int i = 0; i < bitsLength; i++)
/*  82:    */     {
/*  83:112 */       int oldBits = this.itsBits[i];
/*  84:113 */       this.itsBits[i] = ((in.itsBits[i] | gen.itsBits[i]) & notKill.itsBits[i]);
/*  85:114 */       changed |= oldBits != this.itsBits[i];
/*  86:    */     }
/*  87:116 */     return changed;
/*  88:    */   }
/*  89:    */   
/*  90:    */   boolean df2(DataFlowBitSet in, DataFlowBitSet gen, DataFlowBitSet notKill)
/*  91:    */   {
/*  92:121 */     int bitsLength = this.itsBits.length;
/*  93:122 */     boolean changed = false;
/*  94:123 */     for (int i = 0; i < bitsLength; i++)
/*  95:    */     {
/*  96:124 */       int oldBits = this.itsBits[i];
/*  97:125 */       this.itsBits[i] = (in.itsBits[i] & notKill.itsBits[i] | gen.itsBits[i]);
/*  98:126 */       changed |= oldBits != this.itsBits[i];
/*  99:    */     }
/* 100:128 */     return changed;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void badIndex(int n)
/* 104:    */   {
/* 105:133 */     throw new RuntimeException("DataFlowBitSet bad index " + n);
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.DataFlowBitSet
 * JD-Core Version:    0.7.0.1
 */