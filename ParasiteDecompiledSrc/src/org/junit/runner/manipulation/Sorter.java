/*  1:   */ package org.junit.runner.manipulation;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import org.junit.runner.Description;
/*  5:   */ 
/*  6:   */ public class Sorter
/*  7:   */   implements Comparator<Description>
/*  8:   */ {
/*  9:17 */   public static Sorter NULL = new Sorter(new Comparator()
/* 10:   */   {
/* 11:   */     public int compare(Description o1, Description o2)
/* 12:   */     {
/* 13:19 */       return 0;
/* 14:   */     }
/* 15:17 */   });
/* 16:   */   private final Comparator<Description> fComparator;
/* 17:   */   
/* 18:   */   public Sorter(Comparator<Description> comparator)
/* 19:   */   {
/* 20:29 */     this.fComparator = comparator;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void apply(Object object)
/* 24:   */   {
/* 25:37 */     if ((object instanceof Sortable))
/* 26:   */     {
/* 27:38 */       Sortable sortable = (Sortable)object;
/* 28:39 */       sortable.sort(this);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int compare(Description o1, Description o2)
/* 33:   */   {
/* 34:44 */     return this.fComparator.compare(o1, o2);
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.manipulation.Sorter
 * JD-Core Version:    0.7.0.1
 */