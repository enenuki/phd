/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.ListIterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.commons.collections.ResettableListIterator;
/*   6:    */ 
/*   7:    */ public class ObjectArrayListIterator
/*   8:    */   extends ObjectArrayIterator
/*   9:    */   implements ListIterator, ResettableListIterator
/*  10:    */ {
/*  11: 53 */   protected int lastItemIndex = -1;
/*  12:    */   
/*  13:    */   public ObjectArrayListIterator() {}
/*  14:    */   
/*  15:    */   public ObjectArrayListIterator(Object[] array)
/*  16:    */   {
/*  17: 73 */     super(array);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ObjectArrayListIterator(Object[] array, int start)
/*  21:    */   {
/*  22: 86 */     super(array, start);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ObjectArrayListIterator(Object[] array, int start, int end)
/*  26:    */   {
/*  27:101 */     super(array, start, end);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean hasPrevious()
/*  31:    */   {
/*  32:113 */     return this.index > this.startIndex;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object previous()
/*  36:    */   {
/*  37:123 */     if (!hasPrevious()) {
/*  38:124 */       throw new NoSuchElementException();
/*  39:    */     }
/*  40:126 */     this.lastItemIndex = (--this.index);
/*  41:127 */     return this.array[this.index];
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object next()
/*  45:    */   {
/*  46:137 */     if (!hasNext()) {
/*  47:138 */       throw new NoSuchElementException();
/*  48:    */     }
/*  49:140 */     this.lastItemIndex = this.index;
/*  50:141 */     return this.array[(this.index++)];
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int nextIndex()
/*  54:    */   {
/*  55:150 */     return this.index - this.startIndex;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int previousIndex()
/*  59:    */   {
/*  60:159 */     return this.index - this.startIndex - 1;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void add(Object obj)
/*  64:    */   {
/*  65:170 */     throw new UnsupportedOperationException("add() method is not supported");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void set(Object obj)
/*  69:    */   {
/*  70:191 */     if (this.lastItemIndex == -1) {
/*  71:192 */       throw new IllegalStateException("must call next() or previous() before a call to set()");
/*  72:    */     }
/*  73:195 */     this.array[this.lastItemIndex] = obj;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void reset()
/*  77:    */   {
/*  78:202 */     super.reset();
/*  79:203 */     this.lastItemIndex = -1;
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ObjectArrayListIterator
 * JD-Core Version:    0.7.0.1
 */