/*   1:    */ package org.hibernate.internal.util.collections;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.concurrent.ConcurrentHashMap;
/*  11:    */ 
/*  12:    */ public final class CollectionHelper
/*  13:    */ {
/*  14:    */   public static final int MINIMUM_INITIAL_CAPACITY = 16;
/*  15:    */   public static final float LOAD_FACTOR = 0.75F;
/*  16: 45 */   public static final List EMPTY_LIST = Collections.unmodifiableList(new ArrayList(0));
/*  17: 46 */   public static final Collection EMPTY_COLLECTION = Collections.unmodifiableCollection(new ArrayList(0));
/*  18: 47 */   public static final Map EMPTY_MAP = Collections.unmodifiableMap(new HashMap(0));
/*  19:    */   
/*  20:    */   public static <K, V> Map<K, V> mapOfSize(int size)
/*  21:    */   {
/*  22: 61 */     return new HashMap(determineProperSizing(size), 0.75F);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static int determineProperSizing(Map original)
/*  26:    */   {
/*  27: 72 */     return determineProperSizing(original.size());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static int determineProperSizing(Set original)
/*  31:    */   {
/*  32: 83 */     return determineProperSizing(original.size());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static int determineProperSizing(int numberOfElements)
/*  36:    */   {
/*  37: 94 */     int actual = (int)(numberOfElements / 0.75F) + 1;
/*  38: 95 */     return Math.max(actual, 16);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static <K, V> ConcurrentHashMap<K, V> concurrentMap(int expectedNumberOfElements)
/*  42:    */   {
/*  43:108 */     return concurrentMap(expectedNumberOfElements, 0.75F);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static <K, V> ConcurrentHashMap<K, V> concurrentMap(int expectedNumberOfElements, float loadFactor)
/*  47:    */   {
/*  48:123 */     int size = expectedNumberOfElements + 1 + (int)(expectedNumberOfElements * loadFactor);
/*  49:124 */     return new ConcurrentHashMap(size, loadFactor);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static <T> List<T> arrayList(int anticipatedSize)
/*  53:    */   {
/*  54:128 */     return new ArrayList(anticipatedSize);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static boolean isEmpty(Collection collection)
/*  58:    */   {
/*  59:132 */     return (collection == null) || (collection.isEmpty());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static boolean isEmpty(Map map)
/*  63:    */   {
/*  64:136 */     return (map == null) || (map.isEmpty());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static boolean isNotEmpty(Collection collection)
/*  68:    */   {
/*  69:140 */     return !isEmpty(collection);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static boolean isNotEmpty(Map map)
/*  73:    */   {
/*  74:144 */     return !isEmpty(map);
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.CollectionHelper
 * JD-Core Version:    0.7.0.1
 */