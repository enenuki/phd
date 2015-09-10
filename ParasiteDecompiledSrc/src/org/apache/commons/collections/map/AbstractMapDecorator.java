/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Set;
/*   6:    */ 
/*   7:    */ public abstract class AbstractMapDecorator
/*   8:    */   implements Map
/*   9:    */ {
/*  10:    */   protected transient Map map;
/*  11:    */   
/*  12:    */   protected AbstractMapDecorator() {}
/*  13:    */   
/*  14:    */   public AbstractMapDecorator(Map map)
/*  15:    */   {
/*  16: 62 */     if (map == null) {
/*  17: 63 */       throw new IllegalArgumentException("Map must not be null");
/*  18:    */     }
/*  19: 65 */     this.map = map;
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected Map getMap()
/*  23:    */   {
/*  24: 74 */     return this.map;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void clear()
/*  28:    */   {
/*  29: 79 */     this.map.clear();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean containsKey(Object key)
/*  33:    */   {
/*  34: 83 */     return this.map.containsKey(key);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean containsValue(Object value)
/*  38:    */   {
/*  39: 87 */     return this.map.containsValue(value);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Set entrySet()
/*  43:    */   {
/*  44: 91 */     return this.map.entrySet();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object get(Object key)
/*  48:    */   {
/*  49: 95 */     return this.map.get(key);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isEmpty()
/*  53:    */   {
/*  54: 99 */     return this.map.isEmpty();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Set keySet()
/*  58:    */   {
/*  59:103 */     return this.map.keySet();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object put(Object key, Object value)
/*  63:    */   {
/*  64:107 */     return this.map.put(key, value);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void putAll(Map mapToCopy)
/*  68:    */   {
/*  69:111 */     this.map.putAll(mapToCopy);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Object remove(Object key)
/*  73:    */   {
/*  74:115 */     return this.map.remove(key);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int size()
/*  78:    */   {
/*  79:119 */     return this.map.size();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Collection values()
/*  83:    */   {
/*  84:123 */     return this.map.values();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean equals(Object object)
/*  88:    */   {
/*  89:127 */     if (object == this) {
/*  90:128 */       return true;
/*  91:    */     }
/*  92:130 */     return this.map.equals(object);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int hashCode()
/*  96:    */   {
/*  97:134 */     return this.map.hashCode();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String toString()
/* 101:    */   {
/* 102:138 */     return this.map.toString();
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.AbstractMapDecorator
 * JD-Core Version:    0.7.0.1
 */