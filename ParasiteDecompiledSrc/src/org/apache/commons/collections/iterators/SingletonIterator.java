/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.commons.collections.ResettableIterator;
/*   6:    */ 
/*   7:    */ public class SingletonIterator
/*   8:    */   implements Iterator, ResettableIterator
/*   9:    */ {
/*  10:    */   private final boolean removeAllowed;
/*  11: 41 */   private boolean beforeFirst = true;
/*  12: 43 */   private boolean removed = false;
/*  13:    */   private Object object;
/*  14:    */   
/*  15:    */   public SingletonIterator(Object object)
/*  16:    */   {
/*  17: 54 */     this(object, true);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public SingletonIterator(Object object, boolean removeAllowed)
/*  21:    */   {
/*  22: 67 */     this.object = object;
/*  23: 68 */     this.removeAllowed = removeAllowed;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean hasNext()
/*  27:    */   {
/*  28: 80 */     return (this.beforeFirst) && (!this.removed);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object next()
/*  32:    */   {
/*  33: 93 */     if ((!this.beforeFirst) || (this.removed)) {
/*  34: 94 */       throw new NoSuchElementException();
/*  35:    */     }
/*  36: 96 */     this.beforeFirst = false;
/*  37: 97 */     return this.object;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void remove()
/*  41:    */   {
/*  42:110 */     if (this.removeAllowed)
/*  43:    */     {
/*  44:111 */       if ((this.removed) || (this.beforeFirst)) {
/*  45:112 */         throw new IllegalStateException();
/*  46:    */       }
/*  47:114 */       this.object = null;
/*  48:115 */       this.removed = true;
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52:118 */       throw new UnsupportedOperationException();
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void reset()
/*  57:    */   {
/*  58:126 */     this.beforeFirst = true;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.SingletonIterator
 * JD-Core Version:    0.7.0.1
 */