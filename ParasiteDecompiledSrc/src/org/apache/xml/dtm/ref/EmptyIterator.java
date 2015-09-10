/*  1:   */ package org.apache.xml.dtm.ref;
/*  2:   */ 
/*  3:   */ import org.apache.xml.dtm.DTMAxisIterator;
/*  4:   */ 
/*  5:   */ public final class EmptyIterator
/*  6:   */   implements DTMAxisIterator
/*  7:   */ {
/*  8:33 */   private static final EmptyIterator INSTANCE = new EmptyIterator();
/*  9:   */   
/* 10:   */   public static DTMAxisIterator getInstance()
/* 11:   */   {
/* 12:35 */     return INSTANCE;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public final int next()
/* 16:   */   {
/* 17:39 */     return -1;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public final DTMAxisIterator reset()
/* 21:   */   {
/* 22:41 */     return this;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public final int getLast()
/* 26:   */   {
/* 27:43 */     return 0;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public final int getPosition()
/* 31:   */   {
/* 32:45 */     return 1;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public final void setMark() {}
/* 36:   */   
/* 37:   */   public final void gotoMark() {}
/* 38:   */   
/* 39:   */   public final DTMAxisIterator setStartNode(int node)
/* 40:   */   {
/* 41:51 */     return this;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public final int getStartNode()
/* 45:   */   {
/* 46:53 */     return -1;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public final boolean isReverse()
/* 50:   */   {
/* 51:55 */     return false;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public final DTMAxisIterator cloneIterator()
/* 55:   */   {
/* 56:57 */     return this;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public final void setRestartable(boolean isRestartable) {}
/* 60:   */   
/* 61:   */   public final int getNodeByPosition(int position)
/* 62:   */   {
/* 63:61 */     return -1;
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.EmptyIterator
 * JD-Core Version:    0.7.0.1
 */