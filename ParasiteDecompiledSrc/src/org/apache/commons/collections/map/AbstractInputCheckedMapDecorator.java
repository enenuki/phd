/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Map.Entry;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
/*  10:    */ import org.apache.commons.collections.keyvalue.AbstractMapEntryDecorator;
/*  11:    */ import org.apache.commons.collections.set.AbstractSetDecorator;
/*  12:    */ 
/*  13:    */ abstract class AbstractInputCheckedMapDecorator
/*  14:    */   extends AbstractMapDecorator
/*  15:    */ {
/*  16:    */   protected AbstractInputCheckedMapDecorator() {}
/*  17:    */   
/*  18:    */   protected AbstractInputCheckedMapDecorator(Map map)
/*  19:    */   {
/*  20: 64 */     super(map);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected abstract Object checkSetValue(Object paramObject);
/*  24:    */   
/*  25:    */   protected boolean isSetValueChecking()
/*  26:    */   {
/*  27: 95 */     return true;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Set entrySet()
/*  31:    */   {
/*  32:100 */     if (isSetValueChecking()) {
/*  33:101 */       return new EntrySet(this.map.entrySet(), this);
/*  34:    */     }
/*  35:103 */     return this.map.entrySet();
/*  36:    */   }
/*  37:    */   
/*  38:    */   static class EntrySet
/*  39:    */     extends AbstractSetDecorator
/*  40:    */   {
/*  41:    */     private final AbstractInputCheckedMapDecorator parent;
/*  42:    */     
/*  43:    */     protected EntrySet(Set set, AbstractInputCheckedMapDecorator parent)
/*  44:    */     {
/*  45:117 */       super();
/*  46:118 */       this.parent = parent;
/*  47:    */     }
/*  48:    */     
/*  49:    */     public Iterator iterator()
/*  50:    */     {
/*  51:122 */       return new AbstractInputCheckedMapDecorator.EntrySetIterator(this.collection.iterator(), this.parent);
/*  52:    */     }
/*  53:    */     
/*  54:    */     public Object[] toArray()
/*  55:    */     {
/*  56:126 */       Object[] array = this.collection.toArray();
/*  57:127 */       for (int i = 0; i < array.length; i++) {
/*  58:128 */         array[i] = new AbstractInputCheckedMapDecorator.MapEntry((Map.Entry)array[i], this.parent);
/*  59:    */       }
/*  60:130 */       return array;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public Object[] toArray(Object[] array)
/*  64:    */     {
/*  65:134 */       Object[] result = array;
/*  66:135 */       if (array.length > 0) {
/*  67:138 */         result = (Object[])Array.newInstance(array.getClass().getComponentType(), 0);
/*  68:    */       }
/*  69:140 */       result = this.collection.toArray(result);
/*  70:141 */       for (int i = 0; i < result.length; i++) {
/*  71:142 */         result[i] = new AbstractInputCheckedMapDecorator.MapEntry((Map.Entry)result[i], this.parent);
/*  72:    */       }
/*  73:146 */       if (result.length > array.length) {
/*  74:147 */         return result;
/*  75:    */       }
/*  76:151 */       System.arraycopy(result, 0, array, 0, result.length);
/*  77:152 */       if (array.length > result.length) {
/*  78:153 */         array[result.length] = null;
/*  79:    */       }
/*  80:155 */       return array;
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   static class EntrySetIterator
/*  85:    */     extends AbstractIteratorDecorator
/*  86:    */   {
/*  87:    */     private final AbstractInputCheckedMapDecorator parent;
/*  88:    */     
/*  89:    */     protected EntrySetIterator(Iterator iterator, AbstractInputCheckedMapDecorator parent)
/*  90:    */     {
/*  91:168 */       super();
/*  92:169 */       this.parent = parent;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public Object next()
/*  96:    */     {
/*  97:173 */       Map.Entry entry = (Map.Entry)this.iterator.next();
/*  98:174 */       return new AbstractInputCheckedMapDecorator.MapEntry(entry, this.parent);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   static class MapEntry
/* 103:    */     extends AbstractMapEntryDecorator
/* 104:    */   {
/* 105:    */     private final AbstractInputCheckedMapDecorator parent;
/* 106:    */     
/* 107:    */     protected MapEntry(Map.Entry entry, AbstractInputCheckedMapDecorator parent)
/* 108:    */     {
/* 109:187 */       super();
/* 110:188 */       this.parent = parent;
/* 111:    */     }
/* 112:    */     
/* 113:    */     public Object setValue(Object value)
/* 114:    */     {
/* 115:192 */       value = this.parent.checkSetValue(value);
/* 116:193 */       return this.entry.setValue(value);
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.AbstractInputCheckedMapDecorator
 * JD-Core Version:    0.7.0.1
 */