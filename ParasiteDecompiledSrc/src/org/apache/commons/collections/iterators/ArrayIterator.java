/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.commons.collections.ResettableIterator;
/*   6:    */ 
/*   7:    */ public class ArrayIterator
/*   8:    */   implements ResettableIterator
/*   9:    */ {
/*  10:    */   protected Object array;
/*  11: 49 */   protected int startIndex = 0;
/*  12: 51 */   protected int endIndex = 0;
/*  13: 53 */   protected int index = 0;
/*  14:    */   
/*  15:    */   public ArrayIterator() {}
/*  16:    */   
/*  17:    */   public ArrayIterator(Object array)
/*  18:    */   {
/*  19: 77 */     setArray(array);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ArrayIterator(Object array, int startIndex)
/*  23:    */   {
/*  24: 92 */     setArray(array);
/*  25: 93 */     checkBound(startIndex, "start");
/*  26: 94 */     this.startIndex = startIndex;
/*  27: 95 */     this.index = startIndex;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ArrayIterator(Object array, int startIndex, int endIndex)
/*  31:    */   {
/*  32:111 */     setArray(array);
/*  33:112 */     checkBound(startIndex, "start");
/*  34:113 */     checkBound(endIndex, "end");
/*  35:114 */     if (endIndex < startIndex) {
/*  36:115 */       throw new IllegalArgumentException("End index must not be less than start index.");
/*  37:    */     }
/*  38:117 */     this.startIndex = startIndex;
/*  39:118 */     this.endIndex = endIndex;
/*  40:119 */     this.index = startIndex;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void checkBound(int bound, String type)
/*  44:    */   {
/*  45:130 */     if (bound > this.endIndex) {
/*  46:131 */       throw new ArrayIndexOutOfBoundsException("Attempt to make an ArrayIterator that " + type + "s beyond the end of the array. ");
/*  47:    */     }
/*  48:136 */     if (bound < 0) {
/*  49:137 */       throw new ArrayIndexOutOfBoundsException("Attempt to make an ArrayIterator that " + type + "s before the start of the array. ");
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean hasNext()
/*  54:    */   {
/*  55:152 */     return this.index < this.endIndex;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Object next()
/*  59:    */   {
/*  60:163 */     if (!hasNext()) {
/*  61:164 */       throw new NoSuchElementException();
/*  62:    */     }
/*  63:166 */     return Array.get(this.array, this.index++);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void remove()
/*  67:    */   {
/*  68:175 */     throw new UnsupportedOperationException("remove() method is not supported");
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object getArray()
/*  72:    */   {
/*  73:188 */     return this.array;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setArray(Object array)
/*  77:    */   {
/*  78:210 */     this.endIndex = Array.getLength(array);
/*  79:211 */     this.startIndex = 0;
/*  80:212 */     this.array = array;
/*  81:213 */     this.index = 0;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void reset()
/*  85:    */   {
/*  86:220 */     this.index = this.startIndex;
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ArrayIterator
 * JD-Core Version:    0.7.0.1
 */