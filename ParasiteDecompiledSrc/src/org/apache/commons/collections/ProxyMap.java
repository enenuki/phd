/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Set;
/*   6:    */ 
/*   7:    */ /**
/*   8:    */  * @deprecated
/*   9:    */  */
/*  10:    */ public abstract class ProxyMap
/*  11:    */   implements Map
/*  12:    */ {
/*  13:    */   protected Map map;
/*  14:    */   
/*  15:    */   public ProxyMap(Map map)
/*  16:    */   {
/*  17: 62 */     this.map = map;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void clear()
/*  21:    */   {
/*  22: 69 */     this.map.clear();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean containsKey(Object key)
/*  26:    */   {
/*  27: 76 */     return this.map.containsKey(key);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean containsValue(Object value)
/*  31:    */   {
/*  32: 83 */     return this.map.containsValue(value);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Set entrySet()
/*  36:    */   {
/*  37: 90 */     return this.map.entrySet();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean equals(Object m)
/*  41:    */   {
/*  42: 97 */     return this.map.equals(m);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Object get(Object key)
/*  46:    */   {
/*  47:104 */     return this.map.get(key);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int hashCode()
/*  51:    */   {
/*  52:111 */     return this.map.hashCode();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isEmpty()
/*  56:    */   {
/*  57:118 */     return this.map.isEmpty();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Set keySet()
/*  61:    */   {
/*  62:125 */     return this.map.keySet();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object put(Object key, Object value)
/*  66:    */   {
/*  67:132 */     return this.map.put(key, value);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void putAll(Map t)
/*  71:    */   {
/*  72:139 */     this.map.putAll(t);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Object remove(Object key)
/*  76:    */   {
/*  77:146 */     return this.map.remove(key);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int size()
/*  81:    */   {
/*  82:153 */     return this.map.size();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Collection values()
/*  86:    */   {
/*  87:160 */     return this.map.values();
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.ProxyMap
 * JD-Core Version:    0.7.0.1
 */