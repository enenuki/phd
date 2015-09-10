/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.ListIterator;
/*   5:    */ import org.apache.commons.collections.ResettableListIterator;
/*   6:    */ 
/*   7:    */ public class ReverseListIterator
/*   8:    */   implements ResettableListIterator
/*   9:    */ {
/*  10:    */   private final List list;
/*  11:    */   private ListIterator iterator;
/*  12: 48 */   private boolean validForUpdate = true;
/*  13:    */   
/*  14:    */   public ReverseListIterator(List list)
/*  15:    */   {
/*  16: 58 */     this.list = list;
/*  17: 59 */     this.iterator = list.listIterator(list.size());
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean hasNext()
/*  21:    */   {
/*  22: 69 */     return this.iterator.hasPrevious();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Object next()
/*  26:    */   {
/*  27: 79 */     Object obj = this.iterator.previous();
/*  28: 80 */     this.validForUpdate = true;
/*  29: 81 */     return obj;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int nextIndex()
/*  33:    */   {
/*  34: 90 */     return this.iterator.previousIndex();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean hasPrevious()
/*  38:    */   {
/*  39: 99 */     return this.iterator.hasNext();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object previous()
/*  43:    */   {
/*  44:109 */     Object obj = this.iterator.next();
/*  45:110 */     this.validForUpdate = true;
/*  46:111 */     return obj;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int previousIndex()
/*  50:    */   {
/*  51:120 */     return this.iterator.nextIndex();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void remove()
/*  55:    */   {
/*  56:130 */     if (!this.validForUpdate) {
/*  57:131 */       throw new IllegalStateException("Cannot remove from list until next() or previous() called");
/*  58:    */     }
/*  59:133 */     this.iterator.remove();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void set(Object obj)
/*  63:    */   {
/*  64:144 */     if (!this.validForUpdate) {
/*  65:145 */       throw new IllegalStateException("Cannot set to list until next() or previous() called");
/*  66:    */     }
/*  67:147 */     this.iterator.set(obj);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void add(Object obj)
/*  71:    */   {
/*  72:160 */     if (!this.validForUpdate) {
/*  73:161 */       throw new IllegalStateException("Cannot add to list until next() or previous() called");
/*  74:    */     }
/*  75:163 */     this.validForUpdate = false;
/*  76:164 */     this.iterator.add(obj);
/*  77:165 */     this.iterator.previous();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void reset()
/*  81:    */   {
/*  82:173 */     this.iterator = this.list.listIterator(this.list.size());
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.ReverseListIterator
 * JD-Core Version:    0.7.0.1
 */