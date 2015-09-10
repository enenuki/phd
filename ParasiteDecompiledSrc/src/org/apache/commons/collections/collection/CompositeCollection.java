/*   1:    */ package org.apache.commons.collections.collection;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import org.apache.commons.collections.iterators.EmptyIterator;
/*   9:    */ import org.apache.commons.collections.iterators.IteratorChain;
/*  10:    */ import org.apache.commons.collections.list.UnmodifiableList;
/*  11:    */ 
/*  12:    */ public class CompositeCollection
/*  13:    */   implements Collection
/*  14:    */ {
/*  15:    */   protected CollectionMutator mutator;
/*  16:    */   protected Collection[] all;
/*  17:    */   
/*  18:    */   public CompositeCollection()
/*  19:    */   {
/*  20: 56 */     this.all = new Collection[0];
/*  21:    */   }
/*  22:    */   
/*  23:    */   public CompositeCollection(Collection coll)
/*  24:    */   {
/*  25: 65 */     this();
/*  26: 66 */     addComposited(coll);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public CompositeCollection(Collection[] colls)
/*  30:    */   {
/*  31: 76 */     this();
/*  32: 77 */     addComposited(colls);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int size()
/*  36:    */   {
/*  37: 89 */     int size = 0;
/*  38: 90 */     for (int i = this.all.length - 1; i >= 0; i--) {
/*  39: 91 */       size += this.all[i].size();
/*  40:    */     }
/*  41: 93 */     return size;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isEmpty()
/*  45:    */   {
/*  46:104 */     for (int i = this.all.length - 1; i >= 0; i--) {
/*  47:105 */       if (!this.all[i].isEmpty()) {
/*  48:106 */         return false;
/*  49:    */       }
/*  50:    */     }
/*  51:109 */     return true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean contains(Object obj)
/*  55:    */   {
/*  56:121 */     for (int i = this.all.length - 1; i >= 0; i--) {
/*  57:122 */       if (this.all[i].contains(obj)) {
/*  58:123 */         return true;
/*  59:    */       }
/*  60:    */     }
/*  61:126 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Iterator iterator()
/*  65:    */   {
/*  66:140 */     if (this.all.length == 0) {
/*  67:141 */       return EmptyIterator.INSTANCE;
/*  68:    */     }
/*  69:143 */     IteratorChain chain = new IteratorChain();
/*  70:144 */     for (int i = 0; i < this.all.length; i++) {
/*  71:145 */       chain.addIterator(this.all[i].iterator());
/*  72:    */     }
/*  73:147 */     return chain;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object[] toArray()
/*  77:    */   {
/*  78:156 */     Object[] result = new Object[size()];
/*  79:157 */     int i = 0;
/*  80:158 */     for (Iterator it = iterator(); it.hasNext(); i++) {
/*  81:159 */       result[i] = it.next();
/*  82:    */     }
/*  83:161 */     return result;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object[] toArray(Object[] array)
/*  87:    */   {
/*  88:172 */     int size = size();
/*  89:173 */     Object[] result = null;
/*  90:174 */     if (array.length >= size) {
/*  91:175 */       result = array;
/*  92:    */     } else {
/*  93:178 */       result = (Object[])Array.newInstance(array.getClass().getComponentType(), size);
/*  94:    */     }
/*  95:181 */     int offset = 0;
/*  96:    */     Iterator it;
/*  97:182 */     for (int i = 0; i < this.all.length; i++) {
/*  98:183 */       for (it = this.all[i].iterator(); it.hasNext();) {
/*  99:184 */         result[(offset++)] = it.next();
/* 100:    */       }
/* 101:    */     }
/* 102:187 */     if (result.length > size) {
/* 103:188 */       result[size] = null;
/* 104:    */     }
/* 105:190 */     return result;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean add(Object obj)
/* 109:    */   {
/* 110:206 */     if (this.mutator == null) {
/* 111:207 */       throw new UnsupportedOperationException("add() is not supported on CompositeCollection without a CollectionMutator strategy");
/* 112:    */     }
/* 113:210 */     return this.mutator.add(this, this.all, obj);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean remove(Object obj)
/* 117:    */   {
/* 118:225 */     if (this.mutator == null) {
/* 119:226 */       throw new UnsupportedOperationException("remove() is not supported on CompositeCollection without a CollectionMutator strategy");
/* 120:    */     }
/* 121:229 */     return this.mutator.remove(this, this.all, obj);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean containsAll(Collection coll)
/* 125:    */   {
/* 126:242 */     for (Iterator it = coll.iterator(); it.hasNext();) {
/* 127:243 */       if (!contains(it.next())) {
/* 128:244 */         return false;
/* 129:    */       }
/* 130:    */     }
/* 131:247 */     return true;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean addAll(Collection coll)
/* 135:    */   {
/* 136:263 */     if (this.mutator == null) {
/* 137:264 */       throw new UnsupportedOperationException("addAll() is not supported on CompositeCollection without a CollectionMutator strategy");
/* 138:    */     }
/* 139:267 */     return this.mutator.addAll(this, this.all, coll);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean removeAll(Collection coll)
/* 143:    */   {
/* 144:280 */     if (coll.size() == 0) {
/* 145:281 */       return false;
/* 146:    */     }
/* 147:283 */     boolean changed = false;
/* 148:284 */     for (int i = this.all.length - 1; i >= 0; i--) {
/* 149:285 */       changed = (this.all[i].removeAll(coll)) || (changed);
/* 150:    */     }
/* 151:287 */     return changed;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean retainAll(Collection coll)
/* 155:    */   {
/* 156:301 */     boolean changed = false;
/* 157:302 */     for (int i = this.all.length - 1; i >= 0; i--) {
/* 158:303 */       changed = (this.all[i].retainAll(coll)) || (changed);
/* 159:    */     }
/* 160:305 */     return changed;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void clear()
/* 164:    */   {
/* 165:316 */     for (int i = 0; i < this.all.length; i++) {
/* 166:317 */       this.all[i].clear();
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setMutator(CollectionMutator mutator)
/* 171:    */   {
/* 172:328 */     this.mutator = mutator;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void addComposited(Collection[] comps)
/* 176:    */   {
/* 177:337 */     ArrayList list = new ArrayList(Arrays.asList(this.all));
/* 178:338 */     list.addAll(Arrays.asList(comps));
/* 179:339 */     this.all = ((Collection[])list.toArray(new Collection[list.size()]));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void addComposited(Collection c)
/* 183:    */   {
/* 184:348 */     addComposited(new Collection[] { c });
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void addComposited(Collection c, Collection d)
/* 188:    */   {
/* 189:358 */     addComposited(new Collection[] { c, d });
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void removeComposited(Collection coll)
/* 193:    */   {
/* 194:367 */     ArrayList list = new ArrayList(this.all.length);
/* 195:368 */     list.addAll(Arrays.asList(this.all));
/* 196:369 */     list.remove(coll);
/* 197:370 */     this.all = ((Collection[])list.toArray(new Collection[list.size()]));
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Collection toCollection()
/* 201:    */   {
/* 202:380 */     return new ArrayList(this);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Collection getCollections()
/* 206:    */   {
/* 207:389 */     return UnmodifiableList.decorate(Arrays.asList(this.all));
/* 208:    */   }
/* 209:    */   
/* 210:    */   public static abstract interface CollectionMutator
/* 211:    */   {
/* 212:    */     public abstract boolean add(CompositeCollection paramCompositeCollection, Collection[] paramArrayOfCollection, Object paramObject);
/* 213:    */     
/* 214:    */     public abstract boolean addAll(CompositeCollection paramCompositeCollection, Collection[] paramArrayOfCollection, Collection paramCollection);
/* 215:    */     
/* 216:    */     public abstract boolean remove(CompositeCollection paramCompositeCollection, Collection[] paramArrayOfCollection, Object paramObject);
/* 217:    */   }
/* 218:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.CompositeCollection
 * JD-Core Version:    0.7.0.1
 */