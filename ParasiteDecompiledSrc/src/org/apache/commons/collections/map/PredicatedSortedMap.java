/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.SortedMap;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public class PredicatedSortedMap
/*   8:    */   extends PredicatedMap
/*   9:    */   implements SortedMap
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 3359846175935304332L;
/*  12:    */   
/*  13:    */   public static SortedMap decorate(SortedMap map, Predicate keyPredicate, Predicate valuePredicate)
/*  14:    */   {
/*  15: 68 */     return new PredicatedSortedMap(map, keyPredicate, valuePredicate);
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected PredicatedSortedMap(SortedMap map, Predicate keyPredicate, Predicate valuePredicate)
/*  19:    */   {
/*  20: 81 */     super(map, keyPredicate, valuePredicate);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected SortedMap getSortedMap()
/*  24:    */   {
/*  25: 91 */     return (SortedMap)this.map;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object firstKey()
/*  29:    */   {
/*  30: 96 */     return getSortedMap().firstKey();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object lastKey()
/*  34:    */   {
/*  35:100 */     return getSortedMap().lastKey();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Comparator comparator()
/*  39:    */   {
/*  40:104 */     return getSortedMap().comparator();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public SortedMap subMap(Object fromKey, Object toKey)
/*  44:    */   {
/*  45:108 */     SortedMap map = getSortedMap().subMap(fromKey, toKey);
/*  46:109 */     return new PredicatedSortedMap(map, this.keyPredicate, this.valuePredicate);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public SortedMap headMap(Object toKey)
/*  50:    */   {
/*  51:113 */     SortedMap map = getSortedMap().headMap(toKey);
/*  52:114 */     return new PredicatedSortedMap(map, this.keyPredicate, this.valuePredicate);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public SortedMap tailMap(Object fromKey)
/*  56:    */   {
/*  57:118 */     SortedMap map = getSortedMap().tailMap(fromKey);
/*  58:119 */     return new PredicatedSortedMap(map, this.keyPredicate, this.valuePredicate);
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.PredicatedSortedMap
 * JD-Core Version:    0.7.0.1
 */