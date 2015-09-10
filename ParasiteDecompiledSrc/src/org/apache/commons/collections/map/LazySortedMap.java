/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.SortedMap;
/*   5:    */ import org.apache.commons.collections.Factory;
/*   6:    */ import org.apache.commons.collections.Transformer;
/*   7:    */ 
/*   8:    */ public class LazySortedMap
/*   9:    */   extends LazyMap
/*  10:    */   implements SortedMap
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 2715322183617658933L;
/*  13:    */   
/*  14:    */   public static SortedMap decorate(SortedMap map, Factory factory)
/*  15:    */   {
/*  16: 76 */     return new LazySortedMap(map, factory);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static SortedMap decorate(SortedMap map, Transformer factory)
/*  20:    */   {
/*  21: 87 */     return new LazySortedMap(map, factory);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected LazySortedMap(SortedMap map, Factory factory)
/*  25:    */   {
/*  26: 99 */     super(map, factory);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected LazySortedMap(SortedMap map, Transformer factory)
/*  30:    */   {
/*  31:110 */     super(map, factory);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected SortedMap getSortedMap()
/*  35:    */   {
/*  36:120 */     return (SortedMap)this.map;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Object firstKey()
/*  40:    */   {
/*  41:125 */     return getSortedMap().firstKey();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object lastKey()
/*  45:    */   {
/*  46:129 */     return getSortedMap().lastKey();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Comparator comparator()
/*  50:    */   {
/*  51:133 */     return getSortedMap().comparator();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public SortedMap subMap(Object fromKey, Object toKey)
/*  55:    */   {
/*  56:137 */     SortedMap map = getSortedMap().subMap(fromKey, toKey);
/*  57:138 */     return new LazySortedMap(map, this.factory);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public SortedMap headMap(Object toKey)
/*  61:    */   {
/*  62:142 */     SortedMap map = getSortedMap().headMap(toKey);
/*  63:143 */     return new LazySortedMap(map, this.factory);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public SortedMap tailMap(Object fromKey)
/*  67:    */   {
/*  68:147 */     SortedMap map = getSortedMap().tailMap(fromKey);
/*  69:148 */     return new LazySortedMap(map, this.factory);
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.LazySortedMap
 * JD-Core Version:    0.7.0.1
 */