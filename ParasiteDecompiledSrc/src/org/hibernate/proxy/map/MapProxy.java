/*   1:    */ package org.hibernate.proxy.map;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.hibernate.proxy.HibernateProxy;
/*   8:    */ import org.hibernate.proxy.LazyInitializer;
/*   9:    */ 
/*  10:    */ public class MapProxy
/*  11:    */   implements HibernateProxy, Map, Serializable
/*  12:    */ {
/*  13:    */   private MapLazyInitializer li;
/*  14:    */   
/*  15:    */   MapProxy(MapLazyInitializer li)
/*  16:    */   {
/*  17: 44 */     this.li = li;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Object writeReplace()
/*  21:    */   {
/*  22: 48 */     return this;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public LazyInitializer getHibernateLazyInitializer()
/*  26:    */   {
/*  27: 52 */     return this.li;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int size()
/*  31:    */   {
/*  32: 56 */     return this.li.getMap().size();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void clear()
/*  36:    */   {
/*  37: 60 */     this.li.getMap().clear();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isEmpty()
/*  41:    */   {
/*  42: 64 */     return this.li.getMap().isEmpty();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean containsKey(Object key)
/*  46:    */   {
/*  47: 68 */     return this.li.getMap().containsKey(key);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean containsValue(Object value)
/*  51:    */   {
/*  52: 72 */     return this.li.getMap().containsValue(value);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Collection values()
/*  56:    */   {
/*  57: 76 */     return this.li.getMap().values();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void putAll(Map t)
/*  61:    */   {
/*  62: 80 */     this.li.getMap().putAll(t);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Set entrySet()
/*  66:    */   {
/*  67: 84 */     return this.li.getMap().entrySet();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Set keySet()
/*  71:    */   {
/*  72: 88 */     return this.li.getMap().keySet();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Object get(Object key)
/*  76:    */   {
/*  77: 92 */     return this.li.getMap().get(key);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Object remove(Object key)
/*  81:    */   {
/*  82: 96 */     return this.li.getMap().remove(key);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Object put(Object key, Object value)
/*  86:    */   {
/*  87:100 */     return this.li.getMap().put(key, value);
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.map.MapProxy
 * JD-Core Version:    0.7.0.1
 */