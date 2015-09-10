/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.ListIterator;
/*   5:    */ import java.util.NoSuchElementException;
/*   6:    */ import org.apache.commons.collections.ResettableListIterator;
/*   7:    */ 
/*   8:    */ public class LoopingListIterator
/*   9:    */   implements ResettableListIterator
/*  10:    */ {
/*  11:    */   private List list;
/*  12:    */   private ListIterator iterator;
/*  13:    */   
/*  14:    */   public LoopingListIterator(List list)
/*  15:    */   {
/*  16: 60 */     if (list == null) {
/*  17: 61 */       throw new NullPointerException("The list must not be null");
/*  18:    */     }
/*  19: 63 */     this.list = list;
/*  20: 64 */     reset();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean hasNext()
/*  24:    */   {
/*  25: 76 */     return !this.list.isEmpty();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object next()
/*  29:    */   {
/*  30: 88 */     if (this.list.isEmpty()) {
/*  31: 89 */       throw new NoSuchElementException("There are no elements for this iterator to loop on");
/*  32:    */     }
/*  33: 92 */     if (!this.iterator.hasNext()) {
/*  34: 93 */       reset();
/*  35:    */     }
/*  36: 95 */     return this.iterator.next();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int nextIndex()
/*  40:    */   {
/*  41:110 */     if (this.list.isEmpty()) {
/*  42:111 */       throw new NoSuchElementException("There are no elements for this iterator to loop on");
/*  43:    */     }
/*  44:114 */     if (!this.iterator.hasNext()) {
/*  45:115 */       return 0;
/*  46:    */     }
/*  47:117 */     return this.iterator.nextIndex();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean hasPrevious()
/*  51:    */   {
/*  52:130 */     return !this.list.isEmpty();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object previous()
/*  56:    */   {
/*  57:143 */     if (this.list.isEmpty()) {
/*  58:144 */       throw new NoSuchElementException("There are no elements for this iterator to loop on");
/*  59:    */     }
/*  60:147 */     if (!this.iterator.hasPrevious())
/*  61:    */     {
/*  62:148 */       Object result = null;
/*  63:149 */       while (this.iterator.hasNext()) {
/*  64:150 */         result = this.iterator.next();
/*  65:    */       }
/*  66:152 */       this.iterator.previous();
/*  67:153 */       return result;
/*  68:    */     }
/*  69:155 */     return this.iterator.previous();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int previousIndex()
/*  73:    */   {
/*  74:171 */     if (this.list.isEmpty()) {
/*  75:172 */       throw new NoSuchElementException("There are no elements for this iterator to loop on");
/*  76:    */     }
/*  77:175 */     if (!this.iterator.hasPrevious()) {
/*  78:176 */       return this.list.size() - 1;
/*  79:    */     }
/*  80:178 */     return this.iterator.previousIndex();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void remove()
/*  84:    */   {
/*  85:201 */     this.iterator.remove();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void add(Object obj)
/*  89:    */   {
/*  90:220 */     this.iterator.add(obj);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void set(Object obj)
/*  94:    */   {
/*  95:236 */     this.iterator.set(obj);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void reset()
/*  99:    */   {
/* 100:243 */     this.iterator = this.list.listIterator();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int size()
/* 104:    */   {
/* 105:252 */     return this.list.size();
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.LoopingListIterator
 * JD-Core Version:    0.7.0.1
 */