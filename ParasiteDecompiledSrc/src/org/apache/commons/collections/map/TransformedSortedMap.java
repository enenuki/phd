/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.SortedMap;
/*   6:    */ import org.apache.commons.collections.Transformer;
/*   7:    */ 
/*   8:    */ public class TransformedSortedMap
/*   9:    */   extends TransformedMap
/*  10:    */   implements SortedMap
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -8751771676410385778L;
/*  13:    */   
/*  14:    */   public static SortedMap decorate(SortedMap map, Transformer keyTransformer, Transformer valueTransformer)
/*  15:    */   {
/*  16: 66 */     return new TransformedSortedMap(map, keyTransformer, valueTransformer);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static SortedMap decorateTransform(SortedMap map, Transformer keyTransformer, Transformer valueTransformer)
/*  20:    */   {
/*  21: 84 */     TransformedSortedMap decorated = new TransformedSortedMap(map, keyTransformer, valueTransformer);
/*  22: 85 */     if (map.size() > 0)
/*  23:    */     {
/*  24: 86 */       Map transformed = decorated.transformMap(map);
/*  25: 87 */       decorated.clear();
/*  26: 88 */       decorated.getMap().putAll(transformed);
/*  27:    */     }
/*  28: 90 */     return decorated;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected TransformedSortedMap(SortedMap map, Transformer keyTransformer, Transformer valueTransformer)
/*  32:    */   {
/*  33:106 */     super(map, keyTransformer, valueTransformer);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected SortedMap getSortedMap()
/*  37:    */   {
/*  38:116 */     return (SortedMap)this.map;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Object firstKey()
/*  42:    */   {
/*  43:121 */     return getSortedMap().firstKey();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object lastKey()
/*  47:    */   {
/*  48:125 */     return getSortedMap().lastKey();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Comparator comparator()
/*  52:    */   {
/*  53:129 */     return getSortedMap().comparator();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public SortedMap subMap(Object fromKey, Object toKey)
/*  57:    */   {
/*  58:133 */     SortedMap map = getSortedMap().subMap(fromKey, toKey);
/*  59:134 */     return new TransformedSortedMap(map, this.keyTransformer, this.valueTransformer);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public SortedMap headMap(Object toKey)
/*  63:    */   {
/*  64:138 */     SortedMap map = getSortedMap().headMap(toKey);
/*  65:139 */     return new TransformedSortedMap(map, this.keyTransformer, this.valueTransformer);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public SortedMap tailMap(Object fromKey)
/*  69:    */   {
/*  70:143 */     SortedMap map = getSortedMap().tailMap(fromKey);
/*  71:144 */     return new TransformedSortedMap(map, this.keyTransformer, this.valueTransformer);
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.TransformedSortedMap
 * JD-Core Version:    0.7.0.1
 */