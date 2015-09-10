/*  1:   */ package org.apache.commons.collections.map;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.apache.commons.collections.functors.InstanceofPredicate;
/*  5:   */ 
/*  6:   */ public class TypedMap
/*  7:   */ {
/*  8:   */   public static Map decorate(Map map, Class keyType, Class valueType)
/*  9:   */   {
/* 10:60 */     return new PredicatedMap(map, InstanceofPredicate.getInstance(keyType), InstanceofPredicate.getInstance(valueType));
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.TypedMap
 * JD-Core Version:    0.7.0.1
 */