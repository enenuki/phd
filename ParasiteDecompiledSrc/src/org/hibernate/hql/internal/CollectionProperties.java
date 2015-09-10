/*  1:   */ package org.hibernate.hql.internal;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public final class CollectionProperties
/*  7:   */ {
/*  8:   */   public static final Map HQL_COLLECTION_PROPERTIES;
/*  9:39 */   private static final String COLLECTION_INDEX_LOWER = "index".toLowerCase();
/* 10:   */   
/* 11:   */   static
/* 12:   */   {
/* 13:42 */     HQL_COLLECTION_PROPERTIES = new HashMap();
/* 14:43 */     HQL_COLLECTION_PROPERTIES.put("elements".toLowerCase(), "elements");
/* 15:44 */     HQL_COLLECTION_PROPERTIES.put("indices".toLowerCase(), "indices");
/* 16:45 */     HQL_COLLECTION_PROPERTIES.put("size".toLowerCase(), "size");
/* 17:46 */     HQL_COLLECTION_PROPERTIES.put("maxIndex".toLowerCase(), "maxIndex");
/* 18:47 */     HQL_COLLECTION_PROPERTIES.put("minIndex".toLowerCase(), "minIndex");
/* 19:48 */     HQL_COLLECTION_PROPERTIES.put("maxElement".toLowerCase(), "maxElement");
/* 20:49 */     HQL_COLLECTION_PROPERTIES.put("minElement".toLowerCase(), "minElement");
/* 21:50 */     HQL_COLLECTION_PROPERTIES.put(COLLECTION_INDEX_LOWER, "index");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static boolean isCollectionProperty(String name)
/* 25:   */   {
/* 26:57 */     String key = name.toLowerCase();
/* 27:59 */     if (COLLECTION_INDEX_LOWER.equals(key)) {
/* 28:60 */       return false;
/* 29:   */     }
/* 30:63 */     return HQL_COLLECTION_PROPERTIES.containsKey(key);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static String getNormalizedPropertyName(String name)
/* 34:   */   {
/* 35:68 */     return (String)HQL_COLLECTION_PROPERTIES.get(name);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static boolean isAnyCollectionProperty(String name)
/* 39:   */   {
/* 40:72 */     String key = name.toLowerCase();
/* 41:73 */     return HQL_COLLECTION_PROPERTIES.containsKey(key);
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.CollectionProperties
 * JD-Core Version:    0.7.0.1
 */