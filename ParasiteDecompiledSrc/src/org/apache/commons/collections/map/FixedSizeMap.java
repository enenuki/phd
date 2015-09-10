/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.apache.commons.collections.BoundedMap;
/*  12:    */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*  13:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  14:    */ 
/*  15:    */ public class FixedSizeMap
/*  16:    */   extends AbstractMapDecorator
/*  17:    */   implements Map, BoundedMap, Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 7450927208116179316L;
/*  20:    */   
/*  21:    */   public static Map decorate(Map map)
/*  22:    */   {
/*  23: 73 */     return new FixedSizeMap(map);
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected FixedSizeMap(Map map)
/*  27:    */   {
/*  28: 84 */     super(map);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private void writeObject(ObjectOutputStream out)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 96 */     out.defaultWriteObject();
/*  35: 97 */     out.writeObject(this.map);
/*  36:    */   }
/*  37:    */   
/*  38:    */   private void readObject(ObjectInputStream in)
/*  39:    */     throws IOException, ClassNotFoundException
/*  40:    */   {
/*  41:109 */     in.defaultReadObject();
/*  42:110 */     this.map = ((Map)in.readObject());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Object put(Object key, Object value)
/*  46:    */   {
/*  47:115 */     if (!this.map.containsKey(key)) {
/*  48:116 */       throw new IllegalArgumentException("Cannot put new key/value pair - Map is fixed size");
/*  49:    */     }
/*  50:118 */     return this.map.put(key, value);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void putAll(Map mapToCopy)
/*  54:    */   {
/*  55:122 */     for (Iterator it = mapToCopy.keySet().iterator(); it.hasNext();) {
/*  56:123 */       if (!mapToCopy.containsKey(it.next())) {
/*  57:124 */         throw new IllegalArgumentException("Cannot put new key/value pair - Map is fixed size");
/*  58:    */       }
/*  59:    */     }
/*  60:127 */     this.map.putAll(mapToCopy);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void clear()
/*  64:    */   {
/*  65:131 */     throw new UnsupportedOperationException("Map is fixed size");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object remove(Object key)
/*  69:    */   {
/*  70:135 */     throw new UnsupportedOperationException("Map is fixed size");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Set entrySet()
/*  74:    */   {
/*  75:139 */     Set set = this.map.entrySet();
/*  76:    */     
/*  77:141 */     return UnmodifiableSet.decorate(set);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Set keySet()
/*  81:    */   {
/*  82:145 */     Set set = this.map.keySet();
/*  83:146 */     return UnmodifiableSet.decorate(set);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Collection values()
/*  87:    */   {
/*  88:150 */     Collection coll = this.map.values();
/*  89:151 */     return UnmodifiableCollection.decorate(coll);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isFull()
/*  93:    */   {
/*  94:155 */     return true;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int maxSize()
/*  98:    */   {
/*  99:159 */     return size();
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.FixedSizeMap
 * JD-Core Version:    0.7.0.1
 */