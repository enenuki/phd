/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.BitSet;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.NoSuchElementException;
/*  10:    */ import org.apache.commons.collections.list.UnmodifiableList;
/*  11:    */ 
/*  12:    */ public class CollatingIterator
/*  13:    */   implements Iterator
/*  14:    */ {
/*  15: 46 */   private Comparator comparator = null;
/*  16: 49 */   private ArrayList iterators = null;
/*  17: 52 */   private ArrayList values = null;
/*  18: 55 */   private BitSet valueSet = null;
/*  19: 58 */   private int lastReturned = -1;
/*  20:    */   
/*  21:    */   public CollatingIterator()
/*  22:    */   {
/*  23: 68 */     this(null, 2);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public CollatingIterator(Comparator comp)
/*  27:    */   {
/*  28: 79 */     this(comp, 2);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public CollatingIterator(Comparator comp, int initIterCapacity)
/*  32:    */   {
/*  33: 93 */     this.iterators = new ArrayList(initIterCapacity);
/*  34: 94 */     setComparator(comp);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public CollatingIterator(Comparator comp, Iterator a, Iterator b)
/*  38:    */   {
/*  39:108 */     this(comp, 2);
/*  40:109 */     addIterator(a);
/*  41:110 */     addIterator(b);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CollatingIterator(Comparator comp, Iterator[] iterators)
/*  45:    */   {
/*  46:123 */     this(comp, iterators.length);
/*  47:124 */     for (int i = 0; i < iterators.length; i++) {
/*  48:125 */       addIterator(iterators[i]);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public CollatingIterator(Comparator comp, Collection iterators)
/*  53:    */   {
/*  54:141 */     this(comp, iterators.size());
/*  55:142 */     for (Iterator it = iterators.iterator(); it.hasNext();)
/*  56:    */     {
/*  57:143 */       Iterator item = (Iterator)it.next();
/*  58:144 */       addIterator(item);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void addIterator(Iterator iterator)
/*  63:    */   {
/*  64:158 */     checkNotStarted();
/*  65:159 */     if (iterator == null) {
/*  66:160 */       throw new NullPointerException("Iterator must not be null");
/*  67:    */     }
/*  68:162 */     this.iterators.add(iterator);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIterator(int index, Iterator iterator)
/*  72:    */   {
/*  73:175 */     checkNotStarted();
/*  74:176 */     if (iterator == null) {
/*  75:177 */       throw new NullPointerException("Iterator must not be null");
/*  76:    */     }
/*  77:179 */     this.iterators.set(index, iterator);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List getIterators()
/*  81:    */   {
/*  82:188 */     return UnmodifiableList.decorate(this.iterators);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Comparator getComparator()
/*  86:    */   {
/*  87:195 */     return this.comparator;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setComparator(Comparator comp)
/*  91:    */   {
/*  92:204 */     checkNotStarted();
/*  93:205 */     this.comparator = comp;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean hasNext()
/*  97:    */   {
/*  98:216 */     start();
/*  99:217 */     return (anyValueSet(this.valueSet)) || (anyHasNext(this.iterators));
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object next()
/* 103:    */     throws NoSuchElementException
/* 104:    */   {
/* 105:227 */     if (!hasNext()) {
/* 106:228 */       throw new NoSuchElementException();
/* 107:    */     }
/* 108:230 */     int leastIndex = least();
/* 109:231 */     if (leastIndex == -1) {
/* 110:232 */       throw new NoSuchElementException();
/* 111:    */     }
/* 112:234 */     Object val = this.values.get(leastIndex);
/* 113:235 */     clear(leastIndex);
/* 114:236 */     this.lastReturned = leastIndex;
/* 115:237 */     return val;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void remove()
/* 119:    */   {
/* 120:249 */     if (this.lastReturned == -1) {
/* 121:250 */       throw new IllegalStateException("No value can be removed at present");
/* 122:    */     }
/* 123:252 */     Iterator it = (Iterator)this.iterators.get(this.lastReturned);
/* 124:253 */     it.remove();
/* 125:    */   }
/* 126:    */   
/* 127:    */   private void start()
/* 128:    */   {
/* 129:262 */     if (this.values == null)
/* 130:    */     {
/* 131:263 */       this.values = new ArrayList(this.iterators.size());
/* 132:264 */       this.valueSet = new BitSet(this.iterators.size());
/* 133:265 */       for (int i = 0; i < this.iterators.size(); i++)
/* 134:    */       {
/* 135:266 */         this.values.add(null);
/* 136:267 */         this.valueSet.clear(i);
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private boolean set(int i)
/* 142:    */   {
/* 143:282 */     Iterator it = (Iterator)this.iterators.get(i);
/* 144:283 */     if (it.hasNext())
/* 145:    */     {
/* 146:284 */       this.values.set(i, it.next());
/* 147:285 */       this.valueSet.set(i);
/* 148:286 */       return true;
/* 149:    */     }
/* 150:288 */     this.values.set(i, null);
/* 151:289 */     this.valueSet.clear(i);
/* 152:290 */     return false;
/* 153:    */   }
/* 154:    */   
/* 155:    */   private void clear(int i)
/* 156:    */   {
/* 157:299 */     this.values.set(i, null);
/* 158:300 */     this.valueSet.clear(i);
/* 159:    */   }
/* 160:    */   
/* 161:    */   private void checkNotStarted()
/* 162:    */     throws IllegalStateException
/* 163:    */   {
/* 164:310 */     if (this.values != null) {
/* 165:311 */       throw new IllegalStateException("Can't do that after next or hasNext has been called.");
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   private int least()
/* 170:    */   {
/* 171:322 */     int leastIndex = -1;
/* 172:323 */     Object leastObject = null;
/* 173:324 */     for (int i = 0; i < this.values.size(); i++)
/* 174:    */     {
/* 175:325 */       if (!this.valueSet.get(i)) {
/* 176:326 */         set(i);
/* 177:    */       }
/* 178:328 */       if (this.valueSet.get(i)) {
/* 179:329 */         if (leastIndex == -1)
/* 180:    */         {
/* 181:330 */           leastIndex = i;
/* 182:331 */           leastObject = this.values.get(i);
/* 183:    */         }
/* 184:    */         else
/* 185:    */         {
/* 186:333 */           Object curObject = this.values.get(i);
/* 187:334 */           if (this.comparator.compare(curObject, leastObject) < 0)
/* 188:    */           {
/* 189:335 */             leastObject = curObject;
/* 190:336 */             leastIndex = i;
/* 191:    */           }
/* 192:    */         }
/* 193:    */       }
/* 194:    */     }
/* 195:341 */     return leastIndex;
/* 196:    */   }
/* 197:    */   
/* 198:    */   private boolean anyValueSet(BitSet set)
/* 199:    */   {
/* 200:349 */     for (int i = 0; i < set.size(); i++) {
/* 201:350 */       if (set.get(i)) {
/* 202:351 */         return true;
/* 203:    */       }
/* 204:    */     }
/* 205:354 */     return false;
/* 206:    */   }
/* 207:    */   
/* 208:    */   private boolean anyHasNext(ArrayList iters)
/* 209:    */   {
/* 210:362 */     for (int i = 0; i < iters.size(); i++)
/* 211:    */     {
/* 212:363 */       Iterator it = (Iterator)iters.get(i);
/* 213:364 */       if (it.hasNext()) {
/* 214:365 */         return true;
/* 215:    */       }
/* 216:    */     }
/* 217:368 */     return false;
/* 218:    */   }
/* 219:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.CollatingIterator
 * JD-Core Version:    0.7.0.1
 */