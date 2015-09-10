/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.ListIterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.commons.collections.ResettableListIterator;
/*   6:    */ 
/*   7:    */ public class SingletonListIterator
/*   8:    */   implements ListIterator, ResettableListIterator
/*   9:    */ {
/*  10: 36 */   private boolean beforeFirst = true;
/*  11: 37 */   private boolean nextCalled = false;
/*  12: 38 */   private boolean removed = false;
/*  13:    */   private Object object;
/*  14:    */   
/*  15:    */   public SingletonListIterator(Object object)
/*  16:    */   {
/*  17: 48 */     this.object = object;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean hasNext()
/*  21:    */   {
/*  22: 59 */     return (this.beforeFirst) && (!this.removed);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean hasPrevious()
/*  26:    */   {
/*  27: 70 */     return (!this.beforeFirst) && (!this.removed);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int nextIndex()
/*  31:    */   {
/*  32: 80 */     return this.beforeFirst ? 0 : 1;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int previousIndex()
/*  36:    */   {
/*  37: 91 */     return this.beforeFirst ? -1 : 0;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object next()
/*  41:    */   {
/*  42:104 */     if ((!this.beforeFirst) || (this.removed)) {
/*  43:105 */       throw new NoSuchElementException();
/*  44:    */     }
/*  45:107 */     this.beforeFirst = false;
/*  46:108 */     this.nextCalled = true;
/*  47:109 */     return this.object;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object previous()
/*  51:    */   {
/*  52:122 */     if ((this.beforeFirst) || (this.removed)) {
/*  53:123 */       throw new NoSuchElementException();
/*  54:    */     }
/*  55:125 */     this.beforeFirst = true;
/*  56:126 */     return this.object;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void remove()
/*  60:    */   {
/*  61:137 */     if ((!this.nextCalled) || (this.removed)) {
/*  62:138 */       throw new IllegalStateException();
/*  63:    */     }
/*  64:140 */     this.object = null;
/*  65:141 */     this.removed = true;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void add(Object obj)
/*  69:    */   {
/*  70:151 */     throw new UnsupportedOperationException("add() is not supported by this iterator");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void set(Object obj)
/*  74:    */   {
/*  75:162 */     if ((!this.nextCalled) || (this.removed)) {
/*  76:163 */       throw new IllegalStateException();
/*  77:    */     }
/*  78:165 */     this.object = obj;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void reset()
/*  82:    */   {
/*  83:172 */     this.beforeFirst = true;
/*  84:173 */     this.nextCalled = false;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.SingletonListIterator
 * JD-Core Version:    0.7.0.1
 */