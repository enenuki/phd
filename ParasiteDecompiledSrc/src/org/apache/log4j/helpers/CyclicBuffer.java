/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.spi.LoggingEvent;
/*   4:    */ 
/*   5:    */ public class CyclicBuffer
/*   6:    */ {
/*   7:    */   LoggingEvent[] ea;
/*   8:    */   int first;
/*   9:    */   int last;
/*  10:    */   int numElems;
/*  11:    */   int maxSize;
/*  12:    */   
/*  13:    */   public CyclicBuffer(int maxSize)
/*  14:    */     throws IllegalArgumentException
/*  15:    */   {
/*  16: 50 */     if (maxSize < 1) {
/*  17: 51 */       throw new IllegalArgumentException("The maxSize argument (" + maxSize + ") is not a positive integer.");
/*  18:    */     }
/*  19: 54 */     this.maxSize = maxSize;
/*  20: 55 */     this.ea = new LoggingEvent[maxSize];
/*  21: 56 */     this.first = 0;
/*  22: 57 */     this.last = 0;
/*  23: 58 */     this.numElems = 0;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void add(LoggingEvent event)
/*  27:    */   {
/*  28: 67 */     this.ea[this.last] = event;
/*  29: 68 */     if (++this.last == this.maxSize) {
/*  30: 69 */       this.last = 0;
/*  31:    */     }
/*  32: 71 */     if (this.numElems < this.maxSize) {
/*  33: 72 */       this.numElems += 1;
/*  34: 73 */     } else if (++this.first == this.maxSize) {
/*  35: 74 */       this.first = 0;
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public LoggingEvent get(int i)
/*  40:    */   {
/*  41: 87 */     if ((i < 0) || (i >= this.numElems)) {
/*  42: 88 */       return null;
/*  43:    */     }
/*  44: 90 */     return this.ea[((this.first + i) % this.maxSize)];
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getMaxSize()
/*  48:    */   {
/*  49: 95 */     return this.maxSize;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public LoggingEvent get()
/*  53:    */   {
/*  54:104 */     LoggingEvent r = null;
/*  55:105 */     if (this.numElems > 0)
/*  56:    */     {
/*  57:106 */       this.numElems -= 1;
/*  58:107 */       r = this.ea[this.first];
/*  59:108 */       this.ea[this.first] = null;
/*  60:109 */       if (++this.first == this.maxSize) {
/*  61:110 */         this.first = 0;
/*  62:    */       }
/*  63:    */     }
/*  64:112 */     return r;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int length()
/*  68:    */   {
/*  69:122 */     return this.numElems;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void resize(int newSize)
/*  73:    */   {
/*  74:132 */     if (newSize < 0) {
/*  75:133 */       throw new IllegalArgumentException("Negative array size [" + newSize + "] not allowed.");
/*  76:    */     }
/*  77:136 */     if (newSize == this.numElems) {
/*  78:137 */       return;
/*  79:    */     }
/*  80:139 */     LoggingEvent[] temp = new LoggingEvent[newSize];
/*  81:    */     
/*  82:141 */     int loopLen = newSize < this.numElems ? newSize : this.numElems;
/*  83:143 */     for (int i = 0; i < loopLen; i++)
/*  84:    */     {
/*  85:144 */       temp[i] = this.ea[this.first];
/*  86:145 */       this.ea[this.first] = null;
/*  87:146 */       if (++this.first == this.numElems) {
/*  88:147 */         this.first = 0;
/*  89:    */       }
/*  90:    */     }
/*  91:149 */     this.ea = temp;
/*  92:150 */     this.first = 0;
/*  93:151 */     this.numElems = loopLen;
/*  94:152 */     this.maxSize = newSize;
/*  95:153 */     if (loopLen == newSize) {
/*  96:154 */       this.last = 0;
/*  97:    */     } else {
/*  98:156 */       this.last = loopLen;
/*  99:    */     }
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.CyclicBuffer
 * JD-Core Version:    0.7.0.1
 */