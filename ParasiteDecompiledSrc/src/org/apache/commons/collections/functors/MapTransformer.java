/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.apache.commons.collections.Transformer;
/*  6:   */ 
/*  7:   */ public final class MapTransformer
/*  8:   */   implements Transformer, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 862391807045468939L;
/* 11:   */   private final Map iMap;
/* 12:   */   
/* 13:   */   public static Transformer getInstance(Map map)
/* 14:   */   {
/* 15:50 */     if (map == null) {
/* 16:51 */       return ConstantTransformer.NULL_INSTANCE;
/* 17:   */     }
/* 18:53 */     return new MapTransformer(map);
/* 19:   */   }
/* 20:   */   
/* 21:   */   private MapTransformer(Map map)
/* 22:   */   {
/* 23:64 */     this.iMap = map;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object transform(Object input)
/* 27:   */   {
/* 28:74 */     return this.iMap.get(input);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Map getMap()
/* 32:   */   {
/* 33:84 */     return this.iMap;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.MapTransformer
 * JD-Core Version:    0.7.0.1
 */