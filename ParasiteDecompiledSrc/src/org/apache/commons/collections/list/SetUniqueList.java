/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.ListIterator;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
/*  11:    */ import org.apache.commons.collections.iterators.AbstractListIteratorDecorator;
/*  12:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  13:    */ 
/*  14:    */ public class SetUniqueList
/*  15:    */   extends AbstractSerializableListDecorator
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 7196982186153478694L;
/*  18:    */   protected final Set set;
/*  19:    */   
/*  20:    */   public static SetUniqueList decorate(List list)
/*  21:    */   {
/*  22: 74 */     if (list == null) {
/*  23: 75 */       throw new IllegalArgumentException("List must not be null");
/*  24:    */     }
/*  25: 77 */     if (list.isEmpty()) {
/*  26: 78 */       return new SetUniqueList(list, new HashSet());
/*  27:    */     }
/*  28: 80 */     List temp = new ArrayList(list);
/*  29: 81 */     list.clear();
/*  30: 82 */     SetUniqueList sl = new SetUniqueList(list, new HashSet());
/*  31: 83 */     sl.addAll(temp);
/*  32: 84 */     return sl;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected SetUniqueList(List list, Set set)
/*  36:    */   {
/*  37: 99 */     super(list);
/*  38:100 */     if (set == null) {
/*  39:101 */       throw new IllegalArgumentException("Set must not be null");
/*  40:    */     }
/*  41:103 */     this.set = set;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Set asSet()
/*  45:    */   {
/*  46:113 */     return UnmodifiableSet.decorate(this.set);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean add(Object object)
/*  50:    */   {
/*  51:130 */     int sizeBefore = size();
/*  52:    */     
/*  53:    */ 
/*  54:133 */     add(size(), object);
/*  55:    */     
/*  56:    */ 
/*  57:136 */     return sizeBefore != size();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void add(int index, Object object)
/*  61:    */   {
/*  62:151 */     if (!this.set.contains(object))
/*  63:    */     {
/*  64:152 */       super.add(index, object);
/*  65:153 */       this.set.add(object);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean addAll(Collection coll)
/*  70:    */   {
/*  71:167 */     return addAll(size(), coll);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean addAll(int index, Collection coll)
/*  75:    */   {
/*  76:186 */     int sizeBefore = size();
/*  77:189 */     for (Iterator it = coll.iterator(); it.hasNext();) {
/*  78:190 */       add(it.next());
/*  79:    */     }
/*  80:194 */     return sizeBefore != size();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Object set(int index, Object object)
/*  84:    */   {
/*  85:211 */     int pos = indexOf(object);
/*  86:212 */     Object removed = super.set(index, object);
/*  87:213 */     if ((pos == -1) || (pos == index)) {
/*  88:214 */       return removed;
/*  89:    */     }
/*  90:219 */     super.remove(pos);
/*  91:220 */     this.set.remove(removed);
/*  92:221 */     return removed;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean remove(Object object)
/*  96:    */   {
/*  97:225 */     boolean result = super.remove(object);
/*  98:226 */     this.set.remove(object);
/*  99:227 */     return result;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object remove(int index)
/* 103:    */   {
/* 104:231 */     Object result = super.remove(index);
/* 105:232 */     this.set.remove(result);
/* 106:233 */     return result;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean removeAll(Collection coll)
/* 110:    */   {
/* 111:237 */     boolean result = super.removeAll(coll);
/* 112:238 */     this.set.removeAll(coll);
/* 113:239 */     return result;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean retainAll(Collection coll)
/* 117:    */   {
/* 118:243 */     boolean result = super.retainAll(coll);
/* 119:244 */     this.set.retainAll(coll);
/* 120:245 */     return result;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void clear()
/* 124:    */   {
/* 125:249 */     super.clear();
/* 126:250 */     this.set.clear();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean contains(Object object)
/* 130:    */   {
/* 131:254 */     return this.set.contains(object);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean containsAll(Collection coll)
/* 135:    */   {
/* 136:258 */     return this.set.containsAll(coll);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Iterator iterator()
/* 140:    */   {
/* 141:262 */     return new SetListIterator(super.iterator(), this.set);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public ListIterator listIterator()
/* 145:    */   {
/* 146:266 */     return new SetListListIterator(super.listIterator(), this.set);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public ListIterator listIterator(int index)
/* 150:    */   {
/* 151:270 */     return new SetListListIterator(super.listIterator(index), this.set);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List subList(int fromIndex, int toIndex)
/* 155:    */   {
/* 156:274 */     return new SetUniqueList(super.subList(fromIndex, toIndex), this.set);
/* 157:    */   }
/* 158:    */   
/* 159:    */   static class SetListIterator
/* 160:    */     extends AbstractIteratorDecorator
/* 161:    */   {
/* 162:    */     protected final Set set;
/* 163:284 */     protected Object last = null;
/* 164:    */     
/* 165:    */     protected SetListIterator(Iterator it, Set set)
/* 166:    */     {
/* 167:287 */       super();
/* 168:288 */       this.set = set;
/* 169:    */     }
/* 170:    */     
/* 171:    */     public Object next()
/* 172:    */     {
/* 173:292 */       this.last = super.next();
/* 174:293 */       return this.last;
/* 175:    */     }
/* 176:    */     
/* 177:    */     public void remove()
/* 178:    */     {
/* 179:297 */       super.remove();
/* 180:298 */       this.set.remove(this.last);
/* 181:299 */       this.last = null;
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   static class SetListListIterator
/* 186:    */     extends AbstractListIteratorDecorator
/* 187:    */   {
/* 188:    */     protected final Set set;
/* 189:309 */     protected Object last = null;
/* 190:    */     
/* 191:    */     protected SetListListIterator(ListIterator it, Set set)
/* 192:    */     {
/* 193:312 */       super();
/* 194:313 */       this.set = set;
/* 195:    */     }
/* 196:    */     
/* 197:    */     public Object next()
/* 198:    */     {
/* 199:317 */       this.last = super.next();
/* 200:318 */       return this.last;
/* 201:    */     }
/* 202:    */     
/* 203:    */     public Object previous()
/* 204:    */     {
/* 205:322 */       this.last = super.previous();
/* 206:323 */       return this.last;
/* 207:    */     }
/* 208:    */     
/* 209:    */     public void remove()
/* 210:    */     {
/* 211:327 */       super.remove();
/* 212:328 */       this.set.remove(this.last);
/* 213:329 */       this.last = null;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public void add(Object object)
/* 217:    */     {
/* 218:333 */       if (!this.set.contains(object))
/* 219:    */       {
/* 220:334 */         super.add(object);
/* 221:335 */         this.set.add(object);
/* 222:    */       }
/* 223:    */     }
/* 224:    */     
/* 225:    */     public void set(Object object)
/* 226:    */     {
/* 227:340 */       throw new UnsupportedOperationException("ListIterator does not support set");
/* 228:    */     }
/* 229:    */   }
/* 230:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.SetUniqueList
 * JD-Core Version:    0.7.0.1
 */