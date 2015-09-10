/*  1:   */ package org.apache.commons.collections.bag;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import org.apache.commons.collections.SortedBag;
/*  5:   */ import org.apache.commons.collections.Transformer;
/*  6:   */ 
/*  7:   */ public class TransformedSortedBag
/*  8:   */   extends TransformedBag
/*  9:   */   implements SortedBag
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -251737742649401930L;
/* 12:   */   
/* 13:   */   public static SortedBag decorate(SortedBag bag, Transformer transformer)
/* 14:   */   {
/* 15:57 */     return new TransformedSortedBag(bag, transformer);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected TransformedSortedBag(SortedBag bag, Transformer transformer)
/* 19:   */   {
/* 20:72 */     super(bag, transformer);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected SortedBag getSortedBag()
/* 24:   */   {
/* 25:81 */     return (SortedBag)this.collection;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Object first()
/* 29:   */   {
/* 30:86 */     return getSortedBag().first();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Object last()
/* 34:   */   {
/* 35:90 */     return getSortedBag().last();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Comparator comparator()
/* 39:   */   {
/* 40:94 */     return getSortedBag().comparator();
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.TransformedSortedBag
 * JD-Core Version:    0.7.0.1
 */