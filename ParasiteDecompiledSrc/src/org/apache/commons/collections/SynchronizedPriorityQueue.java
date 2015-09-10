/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.NoSuchElementException;
/*   4:    */ 
/*   5:    */ /**
/*   6:    */  * @deprecated
/*   7:    */  */
/*   8:    */ public final class SynchronizedPriorityQueue
/*   9:    */   implements PriorityQueue
/*  10:    */ {
/*  11:    */   protected final PriorityQueue m_priorityQueue;
/*  12:    */   
/*  13:    */   public SynchronizedPriorityQueue(PriorityQueue priorityQueue)
/*  14:    */   {
/*  15: 46 */     this.m_priorityQueue = priorityQueue;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public synchronized void clear()
/*  19:    */   {
/*  20: 53 */     this.m_priorityQueue.clear();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public synchronized boolean isEmpty()
/*  24:    */   {
/*  25: 62 */     return this.m_priorityQueue.isEmpty();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public synchronized void insert(Object element)
/*  29:    */   {
/*  30: 71 */     this.m_priorityQueue.insert(element);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public synchronized Object peek()
/*  34:    */     throws NoSuchElementException
/*  35:    */   {
/*  36: 81 */     return this.m_priorityQueue.peek();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public synchronized Object pop()
/*  40:    */     throws NoSuchElementException
/*  41:    */   {
/*  42: 91 */     return this.m_priorityQueue.pop();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public synchronized String toString()
/*  46:    */   {
/*  47:100 */     return this.m_priorityQueue.toString();
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.SynchronizedPriorityQueue
 * JD-Core Version:    0.7.0.1
 */