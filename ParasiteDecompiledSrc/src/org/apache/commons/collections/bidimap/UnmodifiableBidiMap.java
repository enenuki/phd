/*   1:    */ package org.apache.commons.collections.bidimap;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.apache.commons.collections.BidiMap;
/*   7:    */ import org.apache.commons.collections.MapIterator;
/*   8:    */ import org.apache.commons.collections.Unmodifiable;
/*   9:    */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*  10:    */ import org.apache.commons.collections.iterators.UnmodifiableMapIterator;
/*  11:    */ import org.apache.commons.collections.map.UnmodifiableEntrySet;
/*  12:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  13:    */ 
/*  14:    */ public final class UnmodifiableBidiMap
/*  15:    */   extends AbstractBidiMapDecorator
/*  16:    */   implements Unmodifiable
/*  17:    */ {
/*  18:    */   private UnmodifiableBidiMap inverse;
/*  19:    */   
/*  20:    */   public static BidiMap decorate(BidiMap map)
/*  21:    */   {
/*  22: 55 */     if ((map instanceof Unmodifiable)) {
/*  23: 56 */       return map;
/*  24:    */     }
/*  25: 58 */     return new UnmodifiableBidiMap(map);
/*  26:    */   }
/*  27:    */   
/*  28:    */   private UnmodifiableBidiMap(BidiMap map)
/*  29:    */   {
/*  30: 69 */     super(map);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void clear()
/*  34:    */   {
/*  35: 74 */     throw new UnsupportedOperationException();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object put(Object key, Object value)
/*  39:    */   {
/*  40: 78 */     throw new UnsupportedOperationException();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void putAll(Map mapToCopy)
/*  44:    */   {
/*  45: 82 */     throw new UnsupportedOperationException();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Object remove(Object key)
/*  49:    */   {
/*  50: 86 */     throw new UnsupportedOperationException();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Set entrySet()
/*  54:    */   {
/*  55: 90 */     Set set = super.entrySet();
/*  56: 91 */     return UnmodifiableEntrySet.decorate(set);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Set keySet()
/*  60:    */   {
/*  61: 95 */     Set set = super.keySet();
/*  62: 96 */     return UnmodifiableSet.decorate(set);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Collection values()
/*  66:    */   {
/*  67:100 */     Collection coll = super.values();
/*  68:101 */     return UnmodifiableCollection.decorate(coll);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object removeValue(Object value)
/*  72:    */   {
/*  73:106 */     throw new UnsupportedOperationException();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public MapIterator mapIterator()
/*  77:    */   {
/*  78:110 */     MapIterator it = getBidiMap().mapIterator();
/*  79:111 */     return UnmodifiableMapIterator.decorate(it);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public BidiMap inverseBidiMap()
/*  83:    */   {
/*  84:115 */     if (this.inverse == null)
/*  85:    */     {
/*  86:116 */       this.inverse = new UnmodifiableBidiMap(getBidiMap().inverseBidiMap());
/*  87:117 */       this.inverse.inverse = this;
/*  88:    */     }
/*  89:119 */     return this.inverse;
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.UnmodifiableBidiMap
 * JD-Core Version:    0.7.0.1
 */