/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.util.ListIterator;
/*   5:    */ import java.util.NoSuchElementException;
/*   6:    */ import org.apache.commons.collections.ResettableListIterator;
/*   7:    */ 
/*   8:    */ public class ArrayListIterator
/*   9:    */   extends ArrayIterator
/*  10:    */   implements ListIterator, ResettableListIterator
/*  11:    */ {
/*  12: 57 */   protected int lastItemIndex = -1;
/*  13:    */   
/*  14:    */   public ArrayListIterator() {}
/*  15:    */   
/*  16:    */   public ArrayListIterator(Object array)
/*  17:    */   {
/*  18: 80 */     super(array);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ArrayListIterator(Object array, int startIndex)
/*  22:    */   {
/*  23: 94 */     super(array, startIndex);
/*  24: 95 */     this.startIndex = startIndex;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ArrayListIterator(Object array, int startIndex, int endIndex)
/*  28:    */   {
/*  29:111 */     super(array, startIndex, endIndex);
/*  30:112 */     this.startIndex = startIndex;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean hasPrevious()
/*  34:    */   {
/*  35:123 */     return this.index > this.startIndex;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object previous()
/*  39:    */   {
/*  40:133 */     if (!hasPrevious()) {
/*  41:134 */       throw new NoSuchElementException();
/*  42:    */     }
/*  43:136 */     this.lastItemIndex = (--this.index);
/*  44:137 */     return Array.get(this.array, this.index);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object next()
/*  48:    */   {
/*  49:147 */     if (!hasNext()) {
/*  50:148 */       throw new NoSuchElementException();
/*  51:    */     }
/*  52:150 */     this.lastItemIndex = this.index;
/*  53:151 */     return Array.get(this.array, this.index++);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int nextIndex()
/*  57:    */   {
/*  58:160 */     return this.index - this.startIndex;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int previousIndex()
/*  62:    */   {
/*  63:169 */     return this.index - this.startIndex - 1;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void add(Object o)
/*  67:    */   {
/*  68:180 */     throw new UnsupportedOperationException("add() method is not supported");
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void set(Object o)
/*  72:    */   {
/*  73:199 */     if (this.lastItemIndex == -1) {
/*  74:200 */       throw new IllegalStateException("must call next() or previous() before a call to set()");
/*  75:    */     }
/*  76:203 */     Array.set(this.array, this.lastItemIndex, o);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void reset()
/*  80:    */   {
/*  81:210 */     super.reset();
/*  82:211 */     this.lastItemIndex = -1;
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ArrayListIterator
 * JD-Core Version:    0.7.0.1
 */