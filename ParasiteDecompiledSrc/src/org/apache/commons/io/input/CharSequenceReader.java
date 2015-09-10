/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.Reader;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ public class CharSequenceReader
/*   7:    */   extends Reader
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private final CharSequence charSequence;
/*  11:    */   private int idx;
/*  12:    */   private int mark;
/*  13:    */   
/*  14:    */   public CharSequenceReader(CharSequence charSequence)
/*  15:    */   {
/*  16: 43 */     this.charSequence = (charSequence != null ? charSequence : "");
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void close()
/*  20:    */   {
/*  21: 51 */     this.idx = 0;
/*  22: 52 */     this.mark = 0;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void mark(int readAheadLimit)
/*  26:    */   {
/*  27: 62 */     this.mark = this.idx;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean markSupported()
/*  31:    */   {
/*  32: 72 */     return true;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int read()
/*  36:    */   {
/*  37: 83 */     if (this.idx >= this.charSequence.length()) {
/*  38: 84 */       return -1;
/*  39:    */     }
/*  40: 86 */     return this.charSequence.charAt(this.idx++);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int read(char[] array, int offset, int length)
/*  44:    */   {
/*  45:101 */     if (this.idx >= this.charSequence.length()) {
/*  46:102 */       return -1;
/*  47:    */     }
/*  48:104 */     if (array == null) {
/*  49:105 */       throw new NullPointerException("Character array is missing");
/*  50:    */     }
/*  51:107 */     if ((length < 0) || (offset + length > array.length)) {
/*  52:108 */       throw new IndexOutOfBoundsException("Array Size=" + array.length + ", offset=" + offset + ", length=" + length);
/*  53:    */     }
/*  54:111 */     int count = 0;
/*  55:112 */     for (int i = 0; i < length; i++)
/*  56:    */     {
/*  57:113 */       int c = read();
/*  58:114 */       if (c == -1) {
/*  59:115 */         return count;
/*  60:    */       }
/*  61:117 */       array[(offset + i)] = ((char)c);
/*  62:118 */       count++;
/*  63:    */     }
/*  64:120 */     return count;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void reset()
/*  68:    */   {
/*  69:129 */     this.idx = this.mark;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public long skip(long n)
/*  73:    */   {
/*  74:140 */     if (n < 0L) {
/*  75:141 */       throw new IllegalArgumentException("Number of characters to skip is less than zero: " + n);
/*  76:    */     }
/*  77:144 */     if (this.idx >= this.charSequence.length()) {
/*  78:145 */       return -1L;
/*  79:    */     }
/*  80:147 */     int dest = (int)Math.min(this.charSequence.length(), this.idx + n);
/*  81:148 */     int count = dest - this.idx;
/*  82:149 */     this.idx = dest;
/*  83:150 */     return count;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String toString()
/*  87:    */   {
/*  88:161 */     return this.charSequence.toString();
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.CharSequenceReader
 * JD-Core Version:    0.7.0.1
 */