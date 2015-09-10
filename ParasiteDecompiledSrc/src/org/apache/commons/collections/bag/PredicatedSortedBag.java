/*   1:    */ package org.apache.commons.collections.bag;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import org.apache.commons.collections.Predicate;
/*   5:    */ import org.apache.commons.collections.SortedBag;
/*   6:    */ 
/*   7:    */ public class PredicatedSortedBag
/*   8:    */   extends PredicatedBag
/*   9:    */   implements SortedBag
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 3448581314086406616L;
/*  12:    */   
/*  13:    */   public static SortedBag decorate(SortedBag bag, Predicate predicate)
/*  14:    */   {
/*  15: 62 */     return new PredicatedSortedBag(bag, predicate);
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected PredicatedSortedBag(SortedBag bag, Predicate predicate)
/*  19:    */   {
/*  20: 78 */     super(bag, predicate);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected SortedBag getSortedBag()
/*  24:    */   {
/*  25: 87 */     return (SortedBag)getCollection();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object first()
/*  29:    */   {
/*  30: 92 */     return getSortedBag().first();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object last()
/*  34:    */   {
/*  35: 96 */     return getSortedBag().last();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Comparator comparator()
/*  39:    */   {
/*  40:100 */     return getSortedBag().comparator();
/*  41:    */   }
/*  42:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.PredicatedSortedBag
 * JD-Core Version:    0.7.0.1
 */