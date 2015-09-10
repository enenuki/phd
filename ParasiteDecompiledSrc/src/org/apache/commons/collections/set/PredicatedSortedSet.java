/*   1:    */ package org.apache.commons.collections.set;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.SortedSet;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public class PredicatedSortedSet
/*   8:    */   extends PredicatedSet
/*   9:    */   implements SortedSet
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -9110948148132275052L;
/*  12:    */   
/*  13:    */   public static SortedSet decorate(SortedSet set, Predicate predicate)
/*  14:    */   {
/*  15: 60 */     return new PredicatedSortedSet(set, predicate);
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected PredicatedSortedSet(SortedSet set, Predicate predicate)
/*  19:    */   {
/*  20: 76 */     super(set, predicate);
/*  21:    */   }
/*  22:    */   
/*  23:    */   private SortedSet getSortedSet()
/*  24:    */   {
/*  25: 85 */     return (SortedSet)getCollection();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public SortedSet subSet(Object fromElement, Object toElement)
/*  29:    */   {
/*  30: 90 */     SortedSet sub = getSortedSet().subSet(fromElement, toElement);
/*  31: 91 */     return new PredicatedSortedSet(sub, this.predicate);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public SortedSet headSet(Object toElement)
/*  35:    */   {
/*  36: 95 */     SortedSet sub = getSortedSet().headSet(toElement);
/*  37: 96 */     return new PredicatedSortedSet(sub, this.predicate);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public SortedSet tailSet(Object fromElement)
/*  41:    */   {
/*  42:100 */     SortedSet sub = getSortedSet().tailSet(fromElement);
/*  43:101 */     return new PredicatedSortedSet(sub, this.predicate);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object first()
/*  47:    */   {
/*  48:105 */     return getSortedSet().first();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Object last()
/*  52:    */   {
/*  53:109 */     return getSortedSet().last();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Comparator comparator()
/*  57:    */   {
/*  58:113 */     return getSortedSet().comparator();
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.PredicatedSortedSet
 * JD-Core Version:    0.7.0.1
 */