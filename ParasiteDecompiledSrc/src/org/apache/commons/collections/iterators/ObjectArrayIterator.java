/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.commons.collections.ResettableIterator;
/*   6:    */ 
/*   7:    */ public class ObjectArrayIterator
/*   8:    */   implements Iterator, ResettableIterator
/*   9:    */ {
/*  10: 47 */   protected Object[] array = null;
/*  11: 49 */   protected int startIndex = 0;
/*  12: 51 */   protected int endIndex = 0;
/*  13: 53 */   protected int index = 0;
/*  14:    */   
/*  15:    */   public ObjectArrayIterator() {}
/*  16:    */   
/*  17:    */   public ObjectArrayIterator(Object[] array)
/*  18:    */   {
/*  19: 73 */     this(array, 0, array.length);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ObjectArrayIterator(Object[] array, int start)
/*  23:    */   {
/*  24: 86 */     this(array, start, array.length);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ObjectArrayIterator(Object[] array, int start, int end)
/*  28:    */   {
/*  29:102 */     if (start < 0) {
/*  30:103 */       throw new ArrayIndexOutOfBoundsException("Start index must not be less than zero");
/*  31:    */     }
/*  32:105 */     if (end > array.length) {
/*  33:106 */       throw new ArrayIndexOutOfBoundsException("End index must not be greater than the array length");
/*  34:    */     }
/*  35:108 */     if (start > array.length) {
/*  36:109 */       throw new ArrayIndexOutOfBoundsException("Start index must not be greater than the array length");
/*  37:    */     }
/*  38:111 */     if (end < start) {
/*  39:112 */       throw new IllegalArgumentException("End index must not be less than start index");
/*  40:    */     }
/*  41:114 */     this.array = array;
/*  42:115 */     this.startIndex = start;
/*  43:116 */     this.endIndex = end;
/*  44:117 */     this.index = start;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean hasNext()
/*  48:    */   {
/*  49:129 */     return this.index < this.endIndex;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object next()
/*  53:    */   {
/*  54:140 */     if (!hasNext()) {
/*  55:141 */       throw new NoSuchElementException();
/*  56:    */     }
/*  57:143 */     return this.array[(this.index++)];
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void remove()
/*  61:    */   {
/*  62:152 */     throw new UnsupportedOperationException("remove() method is not supported for an ObjectArrayIterator");
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object[] getArray()
/*  66:    */   {
/*  67:166 */     return this.array;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setArray(Object[] array)
/*  71:    */   {
/*  72:182 */     if (this.array != null) {
/*  73:183 */       throw new IllegalStateException("The array to iterate over has already been set");
/*  74:    */     }
/*  75:185 */     this.array = array;
/*  76:186 */     this.startIndex = 0;
/*  77:187 */     this.endIndex = array.length;
/*  78:188 */     this.index = 0;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getStartIndex()
/*  82:    */   {
/*  83:197 */     return this.startIndex;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getEndIndex()
/*  87:    */   {
/*  88:206 */     return this.endIndex;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void reset()
/*  92:    */   {
/*  93:213 */     this.index = this.startIndex;
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ObjectArrayIterator
 * JD-Core Version:    0.7.0.1
 */