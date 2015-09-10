/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.commons.collections.Unmodifiable;
/*   9:    */ import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
/*  10:    */ import org.apache.commons.collections.keyvalue.AbstractMapEntryDecorator;
/*  11:    */ import org.apache.commons.collections.set.AbstractSetDecorator;
/*  12:    */ 
/*  13:    */ public final class UnmodifiableEntrySet
/*  14:    */   extends AbstractSetDecorator
/*  15:    */   implements Unmodifiable
/*  16:    */ {
/*  17:    */   public static Set decorate(Set set)
/*  18:    */   {
/*  19: 48 */     if ((set instanceof Unmodifiable)) {
/*  20: 49 */       return set;
/*  21:    */     }
/*  22: 51 */     return new UnmodifiableEntrySet(set);
/*  23:    */   }
/*  24:    */   
/*  25:    */   private UnmodifiableEntrySet(Set set)
/*  26:    */   {
/*  27: 62 */     super(set);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean add(Object object)
/*  31:    */   {
/*  32: 67 */     throw new UnsupportedOperationException();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean addAll(Collection coll)
/*  36:    */   {
/*  37: 71 */     throw new UnsupportedOperationException();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void clear()
/*  41:    */   {
/*  42: 75 */     throw new UnsupportedOperationException();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean remove(Object object)
/*  46:    */   {
/*  47: 79 */     throw new UnsupportedOperationException();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean removeAll(Collection coll)
/*  51:    */   {
/*  52: 83 */     throw new UnsupportedOperationException();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean retainAll(Collection coll)
/*  56:    */   {
/*  57: 87 */     throw new UnsupportedOperationException();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Iterator iterator()
/*  61:    */   {
/*  62: 92 */     return new UnmodifiableEntrySetIterator(this.collection.iterator());
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object[] toArray()
/*  66:    */   {
/*  67: 96 */     Object[] array = this.collection.toArray();
/*  68: 97 */     for (int i = 0; i < array.length; i++) {
/*  69: 98 */       array[i] = new UnmodifiableEntry((Map.Entry)array[i]);
/*  70:    */     }
/*  71:100 */     return array;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object[] toArray(Object[] array)
/*  75:    */   {
/*  76:104 */     Object[] result = array;
/*  77:105 */     if (array.length > 0) {
/*  78:108 */       result = (Object[])Array.newInstance(array.getClass().getComponentType(), 0);
/*  79:    */     }
/*  80:110 */     result = this.collection.toArray(result);
/*  81:111 */     for (int i = 0; i < result.length; i++) {
/*  82:112 */       result[i] = new UnmodifiableEntry((Map.Entry)result[i]);
/*  83:    */     }
/*  84:116 */     if (result.length > array.length) {
/*  85:117 */       return result;
/*  86:    */     }
/*  87:121 */     System.arraycopy(result, 0, array, 0, result.length);
/*  88:122 */     if (array.length > result.length) {
/*  89:123 */       array[result.length] = null;
/*  90:    */     }
/*  91:125 */     return array;
/*  92:    */   }
/*  93:    */   
/*  94:    */   static final class UnmodifiableEntrySetIterator
/*  95:    */     extends AbstractIteratorDecorator
/*  96:    */   {
/*  97:    */     protected UnmodifiableEntrySetIterator(Iterator iterator)
/*  98:    */     {
/*  99:135 */       super();
/* 100:    */     }
/* 101:    */     
/* 102:    */     public Object next()
/* 103:    */     {
/* 104:139 */       Map.Entry entry = (Map.Entry)this.iterator.next();
/* 105:140 */       return new UnmodifiableEntrySet.UnmodifiableEntry(entry);
/* 106:    */     }
/* 107:    */     
/* 108:    */     public void remove()
/* 109:    */     {
/* 110:144 */       throw new UnsupportedOperationException();
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   static final class UnmodifiableEntry
/* 115:    */     extends AbstractMapEntryDecorator
/* 116:    */   {
/* 117:    */     protected UnmodifiableEntry(Map.Entry entry)
/* 118:    */     {
/* 119:155 */       super();
/* 120:    */     }
/* 121:    */     
/* 122:    */     public Object setValue(Object obj)
/* 123:    */     {
/* 124:159 */       throw new UnsupportedOperationException();
/* 125:    */     }
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.UnmodifiableEntrySet
 * JD-Core Version:    0.7.0.1
 */