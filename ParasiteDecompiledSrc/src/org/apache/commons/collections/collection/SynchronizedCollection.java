/*   1:    */ package org.apache.commons.collections.collection;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ 
/*   7:    */ public class SynchronizedCollection
/*   8:    */   implements Collection, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 2412805092710877986L;
/*  11:    */   protected final Collection collection;
/*  12:    */   protected final Object lock;
/*  13:    */   
/*  14:    */   public static Collection decorate(Collection coll)
/*  15:    */   {
/*  16: 60 */     return new SynchronizedCollection(coll);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected SynchronizedCollection(Collection collection)
/*  20:    */   {
/*  21: 71 */     if (collection == null) {
/*  22: 72 */       throw new IllegalArgumentException("Collection must not be null");
/*  23:    */     }
/*  24: 74 */     this.collection = collection;
/*  25: 75 */     this.lock = this;
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected SynchronizedCollection(Collection collection, Object lock)
/*  29:    */   {
/*  30: 86 */     if (collection == null) {
/*  31: 87 */       throw new IllegalArgumentException("Collection must not be null");
/*  32:    */     }
/*  33: 89 */     this.collection = collection;
/*  34: 90 */     this.lock = lock;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean add(Object object)
/*  38:    */   {
/*  39: 95 */     synchronized (this.lock)
/*  40:    */     {
/*  41: 96 */       return this.collection.add(object);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean addAll(Collection coll)
/*  46:    */   {
/*  47:101 */     synchronized (this.lock)
/*  48:    */     {
/*  49:102 */       return this.collection.addAll(coll);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void clear()
/*  54:    */   {
/*  55:107 */     synchronized (this.lock)
/*  56:    */     {
/*  57:108 */       this.collection.clear();
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean contains(Object object)
/*  62:    */   {
/*  63:113 */     synchronized (this.lock)
/*  64:    */     {
/*  65:114 */       return this.collection.contains(object);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean containsAll(Collection coll)
/*  70:    */   {
/*  71:119 */     synchronized (this.lock)
/*  72:    */     {
/*  73:120 */       return this.collection.containsAll(coll);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isEmpty()
/*  78:    */   {
/*  79:125 */     synchronized (this.lock)
/*  80:    */     {
/*  81:126 */       return this.collection.isEmpty();
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Iterator iterator()
/*  86:    */   {
/*  87:141 */     return this.collection.iterator();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Object[] toArray()
/*  91:    */   {
/*  92:145 */     synchronized (this.lock)
/*  93:    */     {
/*  94:146 */       return this.collection.toArray();
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Object[] toArray(Object[] object)
/*  99:    */   {
/* 100:151 */     synchronized (this.lock)
/* 101:    */     {
/* 102:152 */       return this.collection.toArray(object);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean remove(Object object)
/* 107:    */   {
/* 108:157 */     synchronized (this.lock)
/* 109:    */     {
/* 110:158 */       return this.collection.remove(object);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean removeAll(Collection coll)
/* 115:    */   {
/* 116:163 */     synchronized (this.lock)
/* 117:    */     {
/* 118:164 */       return this.collection.removeAll(coll);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean retainAll(Collection coll)
/* 123:    */   {
/* 124:169 */     synchronized (this.lock)
/* 125:    */     {
/* 126:170 */       return this.collection.retainAll(coll);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int size()
/* 131:    */   {
/* 132:175 */     synchronized (this.lock)
/* 133:    */     {
/* 134:176 */       return this.collection.size();
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean equals(Object object)
/* 139:    */   {
/* 140:181 */     synchronized (this.lock)
/* 141:    */     {
/* 142:182 */       if (object == this) {
/* 143:183 */         return true;
/* 144:    */       }
/* 145:185 */       return this.collection.equals(object);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int hashCode()
/* 150:    */   {
/* 151:190 */     synchronized (this.lock)
/* 152:    */     {
/* 153:191 */       return this.collection.hashCode();
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String toString()
/* 158:    */   {
/* 159:196 */     synchronized (this.lock)
/* 160:    */     {
/* 161:197 */       return this.collection.toString();
/* 162:    */     }
/* 163:    */   }
/* 164:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.SynchronizedCollection
 * JD-Core Version:    0.7.0.1
 */