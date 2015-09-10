/*   1:    */ package org.apache.commons.collections.collection;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ 
/*   6:    */ public abstract class AbstractCollectionDecorator
/*   7:    */   implements Collection
/*   8:    */ {
/*   9:    */   protected Collection collection;
/*  10:    */   
/*  11:    */   protected AbstractCollectionDecorator() {}
/*  12:    */   
/*  13:    */   protected AbstractCollectionDecorator(Collection coll)
/*  14:    */   {
/*  15: 63 */     if (coll == null) {
/*  16: 64 */       throw new IllegalArgumentException("Collection must not be null");
/*  17:    */     }
/*  18: 66 */     this.collection = coll;
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected Collection getCollection()
/*  22:    */   {
/*  23: 75 */     return this.collection;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean add(Object object)
/*  27:    */   {
/*  28: 80 */     return this.collection.add(object);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean addAll(Collection coll)
/*  32:    */   {
/*  33: 84 */     return this.collection.addAll(coll);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void clear()
/*  37:    */   {
/*  38: 88 */     this.collection.clear();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean contains(Object object)
/*  42:    */   {
/*  43: 92 */     return this.collection.contains(object);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isEmpty()
/*  47:    */   {
/*  48: 96 */     return this.collection.isEmpty();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Iterator iterator()
/*  52:    */   {
/*  53:100 */     return this.collection.iterator();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean remove(Object object)
/*  57:    */   {
/*  58:104 */     return this.collection.remove(object);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int size()
/*  62:    */   {
/*  63:108 */     return this.collection.size();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object[] toArray()
/*  67:    */   {
/*  68:112 */     return this.collection.toArray();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object[] toArray(Object[] object)
/*  72:    */   {
/*  73:116 */     return this.collection.toArray(object);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean containsAll(Collection coll)
/*  77:    */   {
/*  78:120 */     return this.collection.containsAll(coll);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean removeAll(Collection coll)
/*  82:    */   {
/*  83:124 */     return this.collection.removeAll(coll);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean retainAll(Collection coll)
/*  87:    */   {
/*  88:128 */     return this.collection.retainAll(coll);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean equals(Object object)
/*  92:    */   {
/*  93:132 */     if (object == this) {
/*  94:133 */       return true;
/*  95:    */     }
/*  96:135 */     return this.collection.equals(object);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int hashCode()
/* 100:    */   {
/* 101:139 */     return this.collection.hashCode();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String toString()
/* 105:    */   {
/* 106:143 */     return this.collection.toString();
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.AbstractCollectionDecorator
 * JD-Core Version:    0.7.0.1
 */