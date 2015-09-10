/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.util.ArrayDeque;
/*  4:   */ 
/*  5:   */ abstract class AbstractLoggerProvider
/*  6:   */ {
/*  7:   */   private final ThreadLocal<ArrayDeque<Entry>> ndcStack;
/*  8:   */   
/*  9:   */   AbstractLoggerProvider()
/* 10:   */   {
/* 11:29 */     this.ndcStack = new ThreadLocal();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void clearNdc()
/* 15:   */   {
/* 16:32 */     ArrayDeque<Entry> stack = (ArrayDeque)this.ndcStack.get();
/* 17:33 */     if (stack != null) {
/* 18:34 */       stack.clear();
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getNdc()
/* 23:   */   {
/* 24:38 */     ArrayDeque<Entry> stack = (ArrayDeque)this.ndcStack.get();
/* 25:39 */     return (stack == null) || (stack.isEmpty()) ? null : ((Entry)stack.peek()).merged;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getNdcDepth()
/* 29:   */   {
/* 30:43 */     ArrayDeque<Entry> stack = (ArrayDeque)this.ndcStack.get();
/* 31:44 */     return stack == null ? 0 : stack.size();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String peekNdc()
/* 35:   */   {
/* 36:48 */     ArrayDeque<Entry> stack = (ArrayDeque)this.ndcStack.get();
/* 37:49 */     return (stack == null) || (stack.isEmpty()) ? "" : ((Entry)stack.peek()).current;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String popNdc()
/* 41:   */   {
/* 42:53 */     ArrayDeque<Entry> stack = (ArrayDeque)this.ndcStack.get();
/* 43:54 */     return (stack == null) || (stack.isEmpty()) ? "" : ((Entry)stack.pop()).current;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void pushNdc(String message)
/* 47:   */   {
/* 48:58 */     ArrayDeque<Entry> stack = (ArrayDeque)this.ndcStack.get();
/* 49:59 */     if (stack == null)
/* 50:   */     {
/* 51:60 */       stack = new ArrayDeque();
/* 52:61 */       this.ndcStack.set(stack);
/* 53:   */     }
/* 54:63 */     stack.push(stack.isEmpty() ? new Entry(message) : new Entry((Entry)stack.peek(), message));
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void setNdcMaxDepth(int maxDepth)
/* 58:   */   {
/* 59:67 */     ArrayDeque<Entry> stack = (ArrayDeque)this.ndcStack.get();
/* 60:68 */     for (stack == null; stack.size() > maxDepth; stack.pop()) {}
/* 61:   */   }
/* 62:   */   
/* 63:   */   private static class Entry
/* 64:   */   {
/* 65:   */     private String merged;
/* 66:   */     private String current;
/* 67:   */     
/* 68:   */     Entry(String current)
/* 69:   */     {
/* 70:77 */       this.merged = current;
/* 71:78 */       this.current = current;
/* 72:   */     }
/* 73:   */     
/* 74:   */     Entry(Entry parent, String current)
/* 75:   */     {
/* 76:82 */       this.merged = (parent.merged + ' ' + current);
/* 77:83 */       this.current = current;
/* 78:   */     }
/* 79:   */   }
/* 80:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.AbstractLoggerProvider
 * JD-Core Version:    0.7.0.1
 */