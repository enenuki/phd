/*  1:   */ package org.apache.bcel.verifier.statics;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class IntList
/*  6:   */ {
/*  7:   */   private ArrayList theList;
/*  8:   */   
/*  9:   */   IntList()
/* 10:   */   {
/* 11:70 */     this.theList = new ArrayList();
/* 12:   */   }
/* 13:   */   
/* 14:   */   void add(int i)
/* 15:   */   {
/* 16:74 */     this.theList.add(new Integer(i));
/* 17:   */   }
/* 18:   */   
/* 19:   */   boolean contains(int i)
/* 20:   */   {
/* 21:78 */     Integer[] ints = new Integer[this.theList.size()];
/* 22:79 */     this.theList.toArray(ints);
/* 23:80 */     for (int j = 0; j < ints.length; j++) {
/* 24:81 */       if (i == ints[j].intValue()) {
/* 25:81 */         return true;
/* 26:   */       }
/* 27:   */     }
/* 28:83 */     return false;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.statics.IntList
 * JD-Core Version:    0.7.0.1
 */