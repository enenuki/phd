/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.apache.commons.collections.IterableMap;
/*  11:    */ import org.apache.commons.collections.MapIterator;
/*  12:    */ import org.apache.commons.collections.Unmodifiable;
/*  13:    */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*  14:    */ import org.apache.commons.collections.iterators.EntrySetMapIterator;
/*  15:    */ import org.apache.commons.collections.iterators.UnmodifiableMapIterator;
/*  16:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  17:    */ 
/*  18:    */ public final class UnmodifiableMap
/*  19:    */   extends AbstractMapDecorator
/*  20:    */   implements IterableMap, Unmodifiable, Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 2737023427269031941L;
/*  23:    */   
/*  24:    */   public static Map decorate(Map map)
/*  25:    */   {
/*  26: 59 */     if ((map instanceof Unmodifiable)) {
/*  27: 60 */       return map;
/*  28:    */     }
/*  29: 62 */     return new UnmodifiableMap(map);
/*  30:    */   }
/*  31:    */   
/*  32:    */   private UnmodifiableMap(Map map)
/*  33:    */   {
/*  34: 73 */     super(map);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void writeObject(ObjectOutputStream out)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 85 */     out.defaultWriteObject();
/*  41: 86 */     out.writeObject(this.map);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void readObject(ObjectInputStream in)
/*  45:    */     throws IOException, ClassNotFoundException
/*  46:    */   {
/*  47: 98 */     in.defaultReadObject();
/*  48: 99 */     this.map = ((Map)in.readObject());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void clear()
/*  52:    */   {
/*  53:104 */     throw new UnsupportedOperationException();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object put(Object key, Object value)
/*  57:    */   {
/*  58:108 */     throw new UnsupportedOperationException();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void putAll(Map mapToCopy)
/*  62:    */   {
/*  63:112 */     throw new UnsupportedOperationException();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object remove(Object key)
/*  67:    */   {
/*  68:116 */     throw new UnsupportedOperationException();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public MapIterator mapIterator()
/*  72:    */   {
/*  73:120 */     if ((this.map instanceof IterableMap))
/*  74:    */     {
/*  75:121 */       MapIterator it = ((IterableMap)this.map).mapIterator();
/*  76:122 */       return UnmodifiableMapIterator.decorate(it);
/*  77:    */     }
/*  78:124 */     MapIterator it = new EntrySetMapIterator(this.map);
/*  79:125 */     return UnmodifiableMapIterator.decorate(it);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Set entrySet()
/*  83:    */   {
/*  84:130 */     Set set = super.entrySet();
/*  85:131 */     return UnmodifiableEntrySet.decorate(set);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Set keySet()
/*  89:    */   {
/*  90:135 */     Set set = super.keySet();
/*  91:136 */     return UnmodifiableSet.decorate(set);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Collection values()
/*  95:    */   {
/*  96:140 */     Collection coll = super.values();
/*  97:141 */     return UnmodifiableCollection.decorate(coll);
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.UnmodifiableMap
 * JD-Core Version:    0.7.0.1
 */