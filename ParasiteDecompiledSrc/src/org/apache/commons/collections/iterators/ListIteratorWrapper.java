/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.NoSuchElementException;
/*   7:    */ import org.apache.commons.collections.ResettableListIterator;
/*   8:    */ 
/*   9:    */ public class ListIteratorWrapper
/*  10:    */   implements ResettableListIterator
/*  11:    */ {
/*  12:    */   private static final String UNSUPPORTED_OPERATION_MESSAGE = "ListIteratorWrapper does not support optional operations of ListIterator.";
/*  13:    */   private final Iterator iterator;
/*  14: 54 */   private final List list = new ArrayList();
/*  15: 57 */   private int currentIndex = 0;
/*  16: 59 */   private int wrappedIteratorIndex = 0;
/*  17:    */   
/*  18:    */   public ListIteratorWrapper(Iterator iterator)
/*  19:    */   {
/*  20: 72 */     if (iterator == null) {
/*  21: 73 */       throw new NullPointerException("Iterator must not be null");
/*  22:    */     }
/*  23: 75 */     this.iterator = iterator;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void add(Object obj)
/*  27:    */     throws UnsupportedOperationException
/*  28:    */   {
/*  29: 87 */     throw new UnsupportedOperationException("ListIteratorWrapper does not support optional operations of ListIterator.");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean hasNext()
/*  33:    */   {
/*  34: 96 */     if (this.currentIndex == this.wrappedIteratorIndex) {
/*  35: 97 */       return this.iterator.hasNext();
/*  36:    */     }
/*  37: 99 */     return true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean hasPrevious()
/*  41:    */   {
/*  42:108 */     if (this.currentIndex == 0) {
/*  43:109 */       return false;
/*  44:    */     }
/*  45:111 */     return true;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Object next()
/*  49:    */     throws NoSuchElementException
/*  50:    */   {
/*  51:121 */     if (this.currentIndex < this.wrappedIteratorIndex)
/*  52:    */     {
/*  53:122 */       this.currentIndex += 1;
/*  54:123 */       return this.list.get(this.currentIndex - 1);
/*  55:    */     }
/*  56:126 */     Object retval = this.iterator.next();
/*  57:127 */     this.list.add(retval);
/*  58:128 */     this.currentIndex += 1;
/*  59:129 */     this.wrappedIteratorIndex += 1;
/*  60:130 */     return retval;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int nextIndex()
/*  64:    */   {
/*  65:139 */     return this.currentIndex;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object previous()
/*  69:    */     throws NoSuchElementException
/*  70:    */   {
/*  71:149 */     if (this.currentIndex == 0) {
/*  72:150 */       throw new NoSuchElementException();
/*  73:    */     }
/*  74:152 */     this.currentIndex -= 1;
/*  75:153 */     return this.list.get(this.currentIndex);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int previousIndex()
/*  79:    */   {
/*  80:162 */     return this.currentIndex - 1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void remove()
/*  84:    */     throws UnsupportedOperationException
/*  85:    */   {
/*  86:171 */     throw new UnsupportedOperationException("ListIteratorWrapper does not support optional operations of ListIterator.");
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void set(Object obj)
/*  90:    */     throws UnsupportedOperationException
/*  91:    */   {
/*  92:181 */     throw new UnsupportedOperationException("ListIteratorWrapper does not support optional operations of ListIterator.");
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void reset()
/*  96:    */   {
/*  97:193 */     this.currentIndex = 0;
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ListIteratorWrapper
 * JD-Core Version:    0.7.0.1
 */