/*   1:    */ package org.hibernate.internal.util.collections;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class JoinedIterable<T>
/*   7:    */   implements Iterable<T>
/*   8:    */ {
/*   9:    */   private final JoinedIterable<T>.TypeSafeJoinedIterator<T> iterator;
/*  10:    */   
/*  11:    */   public JoinedIterable(List<Iterable<T>> iterables)
/*  12:    */   {
/*  13: 46 */     if (iterables == null) {
/*  14: 47 */       throw new NullPointerException("Unexpected null iterables argument");
/*  15:    */     }
/*  16: 49 */     this.iterator = new TypeSafeJoinedIterator(iterables);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Iterator<T> iterator()
/*  20:    */   {
/*  21: 53 */     return this.iterator;
/*  22:    */   }
/*  23:    */   
/*  24:    */   private class TypeSafeJoinedIterator<T>
/*  25:    */     implements Iterator<T>
/*  26:    */   {
/*  27:    */     private List<Iterable<T>> iterables;
/*  28:    */     private int currentIterableIndex;
/*  29:    */     private Iterator<T> currentIterator;
/*  30:    */     private Iterator<T> lastUsedIterator;
/*  31:    */     
/*  32:    */     public TypeSafeJoinedIterator()
/*  33:    */     {
/*  34: 71 */       this.iterables = iterables;
/*  35:    */     }
/*  36:    */     
/*  37:    */     public boolean hasNext()
/*  38:    */     {
/*  39: 75 */       updateCurrentIterator();
/*  40: 76 */       return this.currentIterator.hasNext();
/*  41:    */     }
/*  42:    */     
/*  43:    */     public T next()
/*  44:    */     {
/*  45: 80 */       updateCurrentIterator();
/*  46: 81 */       return this.currentIterator.next();
/*  47:    */     }
/*  48:    */     
/*  49:    */     public void remove()
/*  50:    */     {
/*  51: 85 */       updateCurrentIterator();
/*  52: 86 */       this.lastUsedIterator.remove();
/*  53:    */     }
/*  54:    */     
/*  55:    */     protected void updateCurrentIterator()
/*  56:    */     {
/*  57: 94 */       if (this.currentIterator == null)
/*  58:    */       {
/*  59: 95 */         if (this.iterables.size() == 0) {
/*  60: 96 */           this.currentIterator = EmptyIterator.INSTANCE;
/*  61:    */         } else {
/*  62: 99 */           this.currentIterator = ((Iterable)this.iterables.get(0)).iterator();
/*  63:    */         }
/*  64:103 */         this.lastUsedIterator = this.currentIterator;
/*  65:    */       }
/*  66:106 */       while ((!this.currentIterator.hasNext()) && (this.currentIterableIndex < this.iterables.size() - 1))
/*  67:    */       {
/*  68:107 */         this.currentIterableIndex += 1;
/*  69:108 */         this.currentIterator = ((Iterable)this.iterables.get(this.currentIterableIndex)).iterator();
/*  70:    */       }
/*  71:    */     }
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.JoinedIterable
 * JD-Core Version:    0.7.0.1
 */