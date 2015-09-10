/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class MapWrapper<K, V>
/*  10:    */   implements Map<K, V>, Serializable
/*  11:    */ {
/*  12:    */   private Map<K, V> wrappedMap_;
/*  13:    */   
/*  14:    */   protected MapWrapper() {}
/*  15:    */   
/*  16:    */   public MapWrapper(Map<K, V> map)
/*  17:    */   {
/*  18: 48 */     this.wrappedMap_ = map;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void clear()
/*  22:    */   {
/*  23: 55 */     this.wrappedMap_.clear();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean containsKey(Object key)
/*  27:    */   {
/*  28: 62 */     return this.wrappedMap_.containsKey(key);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean containsValue(Object value)
/*  32:    */   {
/*  33: 69 */     return this.wrappedMap_.containsValue(value);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Set<Map.Entry<K, V>> entrySet()
/*  37:    */   {
/*  38: 76 */     return this.wrappedMap_.entrySet();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean equals(Object o)
/*  42:    */   {
/*  43: 84 */     return this.wrappedMap_.equals(o);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public V get(Object key)
/*  47:    */   {
/*  48: 91 */     return this.wrappedMap_.get(key);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int hashCode()
/*  52:    */   {
/*  53: 99 */     return this.wrappedMap_.hashCode();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean isEmpty()
/*  57:    */   {
/*  58:106 */     return this.wrappedMap_.isEmpty();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Set<K> keySet()
/*  62:    */   {
/*  63:113 */     return this.wrappedMap_.keySet();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public V put(K key, V value)
/*  67:    */   {
/*  68:120 */     return this.wrappedMap_.put(key, value);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void putAll(Map<? extends K, ? extends V> t)
/*  72:    */   {
/*  73:127 */     this.wrappedMap_.putAll(t);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public V remove(Object key)
/*  77:    */   {
/*  78:134 */     return this.wrappedMap_.remove(key);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int size()
/*  82:    */   {
/*  83:141 */     return this.wrappedMap_.size();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Collection<V> values()
/*  87:    */   {
/*  88:148 */     return this.wrappedMap_.values();
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.MapWrapper
 * JD-Core Version:    0.7.0.1
 */