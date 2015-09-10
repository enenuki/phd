/*   1:    */ package org.apache.commons.collections.set;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
/*  10:    */ import org.apache.commons.collections.list.UnmodifiableList;
/*  11:    */ 
/*  12:    */ public class ListOrderedSet
/*  13:    */   extends AbstractSerializableSetDecorator
/*  14:    */   implements Set
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = -228664372470420141L;
/*  17:    */   protected final List setOrder;
/*  18:    */   
/*  19:    */   public static ListOrderedSet decorate(Set set, List list)
/*  20:    */   {
/*  21: 73 */     if (set == null) {
/*  22: 74 */       throw new IllegalArgumentException("Set must not be null");
/*  23:    */     }
/*  24: 76 */     if (list == null) {
/*  25: 77 */       throw new IllegalArgumentException("List must not be null");
/*  26:    */     }
/*  27: 79 */     if ((set.size() > 0) || (list.size() > 0)) {
/*  28: 80 */       throw new IllegalArgumentException("Set and List must be empty");
/*  29:    */     }
/*  30: 82 */     return new ListOrderedSet(set, list);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static ListOrderedSet decorate(Set set)
/*  34:    */   {
/*  35: 94 */     return new ListOrderedSet(set);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static ListOrderedSet decorate(List list)
/*  39:    */   {
/*  40:109 */     if (list == null) {
/*  41:110 */       throw new IllegalArgumentException("List must not be null");
/*  42:    */     }
/*  43:112 */     Set set = new HashSet(list);
/*  44:113 */     list.retainAll(set);
/*  45:    */     
/*  46:115 */     return new ListOrderedSet(set, list);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public ListOrderedSet()
/*  50:    */   {
/*  51:126 */     super(new HashSet());
/*  52:127 */     this.setOrder = new ArrayList();
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected ListOrderedSet(Set set)
/*  56:    */   {
/*  57:137 */     super(set);
/*  58:138 */     this.setOrder = new ArrayList(set);
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected ListOrderedSet(Set set, List list)
/*  62:    */   {
/*  63:151 */     super(set);
/*  64:152 */     if (list == null) {
/*  65:153 */       throw new IllegalArgumentException("List must not be null");
/*  66:    */     }
/*  67:155 */     this.setOrder = list;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List asList()
/*  71:    */   {
/*  72:165 */     return UnmodifiableList.decorate(this.setOrder);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void clear()
/*  76:    */   {
/*  77:170 */     this.collection.clear();
/*  78:171 */     this.setOrder.clear();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Iterator iterator()
/*  82:    */   {
/*  83:175 */     return new OrderedSetIterator(this.setOrder.iterator(), this.collection, null);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean add(Object object)
/*  87:    */   {
/*  88:179 */     if (this.collection.contains(object)) {
/*  89:181 */       return this.collection.add(object);
/*  90:    */     }
/*  91:184 */     boolean result = this.collection.add(object);
/*  92:185 */     this.setOrder.add(object);
/*  93:186 */     return result;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean addAll(Collection coll)
/*  97:    */   {
/*  98:191 */     boolean result = false;
/*  99:192 */     for (Iterator it = coll.iterator(); it.hasNext();)
/* 100:    */     {
/* 101:193 */       Object object = it.next();
/* 102:194 */       result |= add(object);
/* 103:    */     }
/* 104:196 */     return result;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean remove(Object object)
/* 108:    */   {
/* 109:200 */     boolean result = this.collection.remove(object);
/* 110:201 */     this.setOrder.remove(object);
/* 111:202 */     return result;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean removeAll(Collection coll)
/* 115:    */   {
/* 116:206 */     boolean result = false;
/* 117:207 */     for (Iterator it = coll.iterator(); it.hasNext();)
/* 118:    */     {
/* 119:208 */       Object object = it.next();
/* 120:209 */       result |= remove(object);
/* 121:    */     }
/* 122:211 */     return result;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean retainAll(Collection coll)
/* 126:    */   {
/* 127:215 */     boolean result = this.collection.retainAll(coll);
/* 128:216 */     if (!result) {
/* 129:217 */       return false;
/* 130:    */     }
/* 131:    */     Iterator it;
/* 132:218 */     if (this.collection.size() == 0) {
/* 133:219 */       this.setOrder.clear();
/* 134:    */     } else {
/* 135:221 */       for (it = this.setOrder.iterator(); it.hasNext();)
/* 136:    */       {
/* 137:222 */         Object object = it.next();
/* 138:223 */         if (!this.collection.contains(object)) {
/* 139:224 */           it.remove();
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:228 */     return result;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Object[] toArray()
/* 147:    */   {
/* 148:232 */     return this.setOrder.toArray();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Object[] toArray(Object[] a)
/* 152:    */   {
/* 153:236 */     return this.setOrder.toArray(a);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Object get(int index)
/* 157:    */   {
/* 158:241 */     return this.setOrder.get(index);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int indexOf(Object object)
/* 162:    */   {
/* 163:245 */     return this.setOrder.indexOf(object);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void add(int index, Object object)
/* 167:    */   {
/* 168:249 */     if (!contains(object))
/* 169:    */     {
/* 170:250 */       this.collection.add(object);
/* 171:251 */       this.setOrder.add(index, object);
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean addAll(int index, Collection coll)
/* 176:    */   {
/* 177:256 */     boolean changed = false;
/* 178:257 */     for (Iterator it = coll.iterator(); it.hasNext();)
/* 179:    */     {
/* 180:258 */       Object object = it.next();
/* 181:259 */       if (!contains(object))
/* 182:    */       {
/* 183:260 */         this.collection.add(object);
/* 184:261 */         this.setOrder.add(index, object);
/* 185:262 */         index++;
/* 186:263 */         changed = true;
/* 187:    */       }
/* 188:    */     }
/* 189:266 */     return changed;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Object remove(int index)
/* 193:    */   {
/* 194:270 */     Object obj = this.setOrder.remove(index);
/* 195:271 */     remove(obj);
/* 196:272 */     return obj;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String toString()
/* 200:    */   {
/* 201:282 */     return this.setOrder.toString();
/* 202:    */   }
/* 203:    */   
/* 204:    */   static class OrderedSetIterator
/* 205:    */     extends AbstractIteratorDecorator
/* 206:    */   {
/* 207:    */     protected final Collection set;
/* 208:    */     protected Object last;
/* 209:    */     
/* 210:    */     OrderedSetIterator(Iterator x0, Collection x1, ListOrderedSet.1 x2)
/* 211:    */     {
/* 212:289 */       this(x0, x1);
/* 213:    */     }
/* 214:    */     
/* 215:    */     private OrderedSetIterator(Iterator iterator, Collection set)
/* 216:    */     {
/* 217:297 */       super();
/* 218:298 */       this.set = set;
/* 219:    */     }
/* 220:    */     
/* 221:    */     public Object next()
/* 222:    */     {
/* 223:302 */       this.last = this.iterator.next();
/* 224:303 */       return this.last;
/* 225:    */     }
/* 226:    */     
/* 227:    */     public void remove()
/* 228:    */     {
/* 229:307 */       this.set.remove(this.last);
/* 230:308 */       this.iterator.remove();
/* 231:309 */       this.last = null;
/* 232:    */     }
/* 233:    */   }
/* 234:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.ListOrderedSet
 * JD-Core Version:    0.7.0.1
 */