/*  1:   */ package javassist.bytecode.analysis;
/*  2:   */ 
/*  3:   */ import java.util.NoSuchElementException;
/*  4:   */ 
/*  5:   */ class IntQueue
/*  6:   */ {
/*  7:   */   private Entry head;
/*  8:   */   private Entry tail;
/*  9:   */   
/* 10:   */   private static class Entry
/* 11:   */   {
/* 12:   */     private Entry next;
/* 13:   */     private int value;
/* 14:   */     
/* 15:   */     Entry(int x0, IntQueue.1 x1)
/* 16:   */     {
/* 17:20 */       this(x0);
/* 18:   */     }
/* 19:   */     
/* 20:   */     private Entry(int value)
/* 21:   */     {
/* 22:24 */       this.value = value;
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   void add(int value)
/* 27:   */   {
/* 28:32 */     Entry entry = new Entry(value, null);
/* 29:33 */     if (this.tail != null) {
/* 30:34 */       this.tail.next = entry;
/* 31:   */     }
/* 32:35 */     this.tail = entry;
/* 33:37 */     if (this.head == null) {
/* 34:38 */       this.head = entry;
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   boolean isEmpty()
/* 39:   */   {
/* 40:42 */     return this.head == null;
/* 41:   */   }
/* 42:   */   
/* 43:   */   int take()
/* 44:   */   {
/* 45:46 */     if (this.head == null) {
/* 46:47 */       throw new NoSuchElementException();
/* 47:   */     }
/* 48:49 */     int value = this.head.value;
/* 49:50 */     this.head = this.head.next;
/* 50:51 */     if (this.head == null) {
/* 51:52 */       this.tail = null;
/* 52:   */     }
/* 53:54 */     return value;
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.IntQueue
 * JD-Core Version:    0.7.0.1
 */