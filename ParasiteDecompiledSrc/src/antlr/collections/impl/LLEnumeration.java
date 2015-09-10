/*  1:   */ package antlr.collections.impl;
/*  2:   */ 
/*  3:   */ import java.util.Enumeration;
/*  4:   */ import java.util.NoSuchElementException;
/*  5:   */ 
/*  6:   */ final class LLEnumeration
/*  7:   */   implements Enumeration
/*  8:   */ {
/*  9:   */   LLCell cursor;
/* 10:   */   LList list;
/* 11:   */   
/* 12:   */   public LLEnumeration(LList paramLList)
/* 13:   */   {
/* 14:29 */     this.list = paramLList;
/* 15:30 */     this.cursor = this.list.head;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean hasMoreElements()
/* 19:   */   {
/* 20:37 */     if (this.cursor != null) {
/* 21:38 */       return true;
/* 22:   */     }
/* 23:40 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object nextElement()
/* 27:   */   {
/* 28:49 */     if (!hasMoreElements()) {
/* 29:49 */       throw new NoSuchElementException();
/* 30:   */     }
/* 31:50 */     LLCell localLLCell = this.cursor;
/* 32:51 */     this.cursor = this.cursor.next;
/* 33:52 */     return localLLCell.data;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.LLEnumeration
 * JD-Core Version:    0.7.0.1
 */