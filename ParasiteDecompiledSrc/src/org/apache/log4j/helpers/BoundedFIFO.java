/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.spi.LoggingEvent;
/*   4:    */ 
/*   5:    */ public class BoundedFIFO
/*   6:    */ {
/*   7:    */   LoggingEvent[] buf;
/*   8: 34 */   int numElements = 0;
/*   9: 35 */   int first = 0;
/*  10: 36 */   int next = 0;
/*  11:    */   int maxSize;
/*  12:    */   
/*  13:    */   public BoundedFIFO(int maxSize)
/*  14:    */   {
/*  15: 44 */     if (maxSize < 1) {
/*  16: 45 */       throw new IllegalArgumentException("The maxSize argument (" + maxSize + ") is not a positive integer.");
/*  17:    */     }
/*  18: 48 */     this.maxSize = maxSize;
/*  19: 49 */     this.buf = new LoggingEvent[maxSize];
/*  20:    */   }
/*  21:    */   
/*  22:    */   public LoggingEvent get()
/*  23:    */   {
/*  24: 57 */     if (this.numElements == 0) {
/*  25: 58 */       return null;
/*  26:    */     }
/*  27: 60 */     LoggingEvent r = this.buf[this.first];
/*  28: 61 */     this.buf[this.first] = null;
/*  29: 63 */     if (++this.first == this.maxSize) {
/*  30: 64 */       this.first = 0;
/*  31:    */     }
/*  32: 66 */     this.numElements -= 1;
/*  33: 67 */     return r;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void put(LoggingEvent o)
/*  37:    */   {
/*  38: 76 */     if (this.numElements != this.maxSize)
/*  39:    */     {
/*  40: 77 */       this.buf[this.next] = o;
/*  41: 78 */       if (++this.next == this.maxSize) {
/*  42: 79 */         this.next = 0;
/*  43:    */       }
/*  44: 81 */       this.numElements += 1;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getMaxSize()
/*  49:    */   {
/*  50: 90 */     return this.maxSize;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isFull()
/*  54:    */   {
/*  55: 98 */     return this.numElements == this.maxSize;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int length()
/*  59:    */   {
/*  60:108 */     return this.numElements;
/*  61:    */   }
/*  62:    */   
/*  63:    */   int min(int a, int b)
/*  64:    */   {
/*  65:113 */     return a < b ? a : b;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public synchronized void resize(int newSize)
/*  69:    */   {
/*  70:126 */     if (newSize == this.maxSize) {
/*  71:127 */       return;
/*  72:    */     }
/*  73:130 */     LoggingEvent[] tmp = new LoggingEvent[newSize];
/*  74:    */     
/*  75:    */ 
/*  76:133 */     int len1 = this.maxSize - this.first;
/*  77:    */     
/*  78:    */ 
/*  79:136 */     len1 = min(len1, newSize);
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:140 */     len1 = min(len1, this.numElements);
/*  84:    */     
/*  85:    */ 
/*  86:143 */     System.arraycopy(this.buf, this.first, tmp, 0, len1);
/*  87:    */     
/*  88:    */ 
/*  89:146 */     int len2 = 0;
/*  90:147 */     if ((len1 < this.numElements) && (len1 < newSize))
/*  91:    */     {
/*  92:148 */       len2 = this.numElements - len1;
/*  93:149 */       len2 = min(len2, newSize - len1);
/*  94:150 */       System.arraycopy(this.buf, 0, tmp, len1, len2);
/*  95:    */     }
/*  96:153 */     this.buf = tmp;
/*  97:154 */     this.maxSize = newSize;
/*  98:155 */     this.first = 0;
/*  99:156 */     this.numElements = (len1 + len2);
/* 100:157 */     this.next = this.numElements;
/* 101:158 */     if (this.next == this.maxSize) {
/* 102:159 */       this.next = 0;
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean wasEmpty()
/* 107:    */   {
/* 108:169 */     return this.numElements == 1;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean wasFull()
/* 112:    */   {
/* 113:178 */     return this.numElements + 1 == this.maxSize;
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.BoundedFIFO
 * JD-Core Version:    0.7.0.1
 */