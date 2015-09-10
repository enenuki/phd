/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.AbstractList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.ListIterator;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Set;
/*  14:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  15:    */ import org.apache.commons.collections.iterators.UnmodifiableListIterator;
/*  16:    */ import org.apache.commons.collections.list.UnmodifiableList;
/*  17:    */ 
/*  18:    */ public class LinkedMap
/*  19:    */   extends AbstractLinkedMap
/*  20:    */   implements Serializable, Cloneable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 9077234323521161066L;
/*  23:    */   
/*  24:    */   public LinkedMap()
/*  25:    */   {
/*  26: 75 */     super(16, 0.75F, 12);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public LinkedMap(int initialCapacity)
/*  30:    */   {
/*  31: 85 */     super(initialCapacity);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public LinkedMap(int initialCapacity, float loadFactor)
/*  35:    */   {
/*  36: 98 */     super(initialCapacity, loadFactor);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public LinkedMap(Map map)
/*  40:    */   {
/*  41:108 */     super(map);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object clone()
/*  45:    */   {
/*  46:118 */     return super.clone();
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void writeObject(ObjectOutputStream out)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52:125 */     out.defaultWriteObject();
/*  53:126 */     doWriteObject(out);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void readObject(ObjectInputStream in)
/*  57:    */     throws IOException, ClassNotFoundException
/*  58:    */   {
/*  59:133 */     in.defaultReadObject();
/*  60:134 */     doReadObject(in);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Object get(int index)
/*  64:    */   {
/*  65:146 */     return getEntry(index).getKey();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object getValue(int index)
/*  69:    */   {
/*  70:157 */     return getEntry(index).getValue();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int indexOf(Object key)
/*  74:    */   {
/*  75:167 */     key = convertKey(key);
/*  76:168 */     int i = 0;
/*  77:169 */     for (AbstractLinkedMap.LinkEntry entry = this.header.after; entry != this.header; i++)
/*  78:    */     {
/*  79:170 */       if (isEqualKey(key, entry.key)) {
/*  80:171 */         return i;
/*  81:    */       }
/*  82:169 */       entry = entry.after;
/*  83:    */     }
/*  84:174 */     return -1;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Object remove(int index)
/*  88:    */   {
/*  89:186 */     return remove(get(index));
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List asList()
/*  93:    */   {
/*  94:205 */     return new LinkedMapList(this);
/*  95:    */   }
/*  96:    */   
/*  97:    */   static class LinkedMapList
/*  98:    */     extends AbstractList
/*  99:    */   {
/* 100:    */     final LinkedMap parent;
/* 101:    */     
/* 102:    */     LinkedMapList(LinkedMap parent)
/* 103:    */     {
/* 104:216 */       this.parent = parent;
/* 105:    */     }
/* 106:    */     
/* 107:    */     public int size()
/* 108:    */     {
/* 109:220 */       return this.parent.size();
/* 110:    */     }
/* 111:    */     
/* 112:    */     public Object get(int index)
/* 113:    */     {
/* 114:224 */       return this.parent.get(index);
/* 115:    */     }
/* 116:    */     
/* 117:    */     public boolean contains(Object obj)
/* 118:    */     {
/* 119:228 */       return this.parent.containsKey(obj);
/* 120:    */     }
/* 121:    */     
/* 122:    */     public int indexOf(Object obj)
/* 123:    */     {
/* 124:232 */       return this.parent.indexOf(obj);
/* 125:    */     }
/* 126:    */     
/* 127:    */     public int lastIndexOf(Object obj)
/* 128:    */     {
/* 129:236 */       return this.parent.indexOf(obj);
/* 130:    */     }
/* 131:    */     
/* 132:    */     public boolean containsAll(Collection coll)
/* 133:    */     {
/* 134:240 */       return this.parent.keySet().containsAll(coll);
/* 135:    */     }
/* 136:    */     
/* 137:    */     public Object remove(int index)
/* 138:    */     {
/* 139:244 */       throw new UnsupportedOperationException();
/* 140:    */     }
/* 141:    */     
/* 142:    */     public boolean remove(Object obj)
/* 143:    */     {
/* 144:248 */       throw new UnsupportedOperationException();
/* 145:    */     }
/* 146:    */     
/* 147:    */     public boolean removeAll(Collection coll)
/* 148:    */     {
/* 149:252 */       throw new UnsupportedOperationException();
/* 150:    */     }
/* 151:    */     
/* 152:    */     public boolean retainAll(Collection coll)
/* 153:    */     {
/* 154:256 */       throw new UnsupportedOperationException();
/* 155:    */     }
/* 156:    */     
/* 157:    */     public void clear()
/* 158:    */     {
/* 159:260 */       throw new UnsupportedOperationException();
/* 160:    */     }
/* 161:    */     
/* 162:    */     public Object[] toArray()
/* 163:    */     {
/* 164:264 */       return this.parent.keySet().toArray();
/* 165:    */     }
/* 166:    */     
/* 167:    */     public Object[] toArray(Object[] array)
/* 168:    */     {
/* 169:268 */       return this.parent.keySet().toArray(array);
/* 170:    */     }
/* 171:    */     
/* 172:    */     public Iterator iterator()
/* 173:    */     {
/* 174:272 */       return UnmodifiableIterator.decorate(this.parent.keySet().iterator());
/* 175:    */     }
/* 176:    */     
/* 177:    */     public ListIterator listIterator()
/* 178:    */     {
/* 179:276 */       return UnmodifiableListIterator.decorate(super.listIterator());
/* 180:    */     }
/* 181:    */     
/* 182:    */     public ListIterator listIterator(int fromIndex)
/* 183:    */     {
/* 184:280 */       return UnmodifiableListIterator.decorate(super.listIterator(fromIndex));
/* 185:    */     }
/* 186:    */     
/* 187:    */     public List subList(int fromIndexInclusive, int toIndexExclusive)
/* 188:    */     {
/* 189:284 */       return UnmodifiableList.decorate(super.subList(fromIndexInclusive, toIndexExclusive));
/* 190:    */     }
/* 191:    */   }
/* 192:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.LinkedMap
 * JD-Core Version:    0.7.0.1
 */