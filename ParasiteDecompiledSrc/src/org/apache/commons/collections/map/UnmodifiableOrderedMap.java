/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.apache.commons.collections.MapIterator;
/*  11:    */ import org.apache.commons.collections.OrderedMap;
/*  12:    */ import org.apache.commons.collections.OrderedMapIterator;
/*  13:    */ import org.apache.commons.collections.Unmodifiable;
/*  14:    */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*  15:    */ import org.apache.commons.collections.iterators.UnmodifiableMapIterator;
/*  16:    */ import org.apache.commons.collections.iterators.UnmodifiableOrderedMapIterator;
/*  17:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  18:    */ 
/*  19:    */ public final class UnmodifiableOrderedMap
/*  20:    */   extends AbstractOrderedMapDecorator
/*  21:    */   implements Unmodifiable, Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 8136428161720526266L;
/*  24:    */   
/*  25:    */   public static OrderedMap decorate(OrderedMap map)
/*  26:    */   {
/*  27: 60 */     if ((map instanceof Unmodifiable)) {
/*  28: 61 */       return map;
/*  29:    */     }
/*  30: 63 */     return new UnmodifiableOrderedMap(map);
/*  31:    */   }
/*  32:    */   
/*  33:    */   private UnmodifiableOrderedMap(OrderedMap map)
/*  34:    */   {
/*  35: 74 */     super(map);
/*  36:    */   }
/*  37:    */   
/*  38:    */   private void writeObject(ObjectOutputStream out)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 86 */     out.defaultWriteObject();
/*  42: 87 */     out.writeObject(this.map);
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void readObject(ObjectInputStream in)
/*  46:    */     throws IOException, ClassNotFoundException
/*  47:    */   {
/*  48: 99 */     in.defaultReadObject();
/*  49:100 */     this.map = ((Map)in.readObject());
/*  50:    */   }
/*  51:    */   
/*  52:    */   public MapIterator mapIterator()
/*  53:    */   {
/*  54:105 */     MapIterator it = getOrderedMap().mapIterator();
/*  55:106 */     return UnmodifiableMapIterator.decorate(it);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public OrderedMapIterator orderedMapIterator()
/*  59:    */   {
/*  60:110 */     OrderedMapIterator it = getOrderedMap().orderedMapIterator();
/*  61:111 */     return UnmodifiableOrderedMapIterator.decorate(it);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void clear()
/*  65:    */   {
/*  66:115 */     throw new UnsupportedOperationException();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Object put(Object key, Object value)
/*  70:    */   {
/*  71:119 */     throw new UnsupportedOperationException();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void putAll(Map mapToCopy)
/*  75:    */   {
/*  76:123 */     throw new UnsupportedOperationException();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Object remove(Object key)
/*  80:    */   {
/*  81:127 */     throw new UnsupportedOperationException();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Set entrySet()
/*  85:    */   {
/*  86:131 */     Set set = super.entrySet();
/*  87:132 */     return UnmodifiableEntrySet.decorate(set);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Set keySet()
/*  91:    */   {
/*  92:136 */     Set set = super.keySet();
/*  93:137 */     return UnmodifiableSet.decorate(set);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Collection values()
/*  97:    */   {
/*  98:141 */     Collection coll = super.values();
/*  99:142 */     return UnmodifiableCollection.decorate(coll);
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.UnmodifiableOrderedMap
 * JD-Core Version:    0.7.0.1
 */