/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Comparator;
/*   8:    */ import java.util.Dictionary;
/*   9:    */ import java.util.Enumeration;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.ListIterator;
/*  13:    */ import java.util.Map;
/*  14:    */ import org.apache.commons.collections.iterators.ArrayIterator;
/*  15:    */ import org.apache.commons.collections.iterators.ArrayListIterator;
/*  16:    */ import org.apache.commons.collections.iterators.CollatingIterator;
/*  17:    */ import org.apache.commons.collections.iterators.EmptyIterator;
/*  18:    */ import org.apache.commons.collections.iterators.EmptyListIterator;
/*  19:    */ import org.apache.commons.collections.iterators.EmptyMapIterator;
/*  20:    */ import org.apache.commons.collections.iterators.EmptyOrderedIterator;
/*  21:    */ import org.apache.commons.collections.iterators.EmptyOrderedMapIterator;
/*  22:    */ import org.apache.commons.collections.iterators.EnumerationIterator;
/*  23:    */ import org.apache.commons.collections.iterators.FilterIterator;
/*  24:    */ import org.apache.commons.collections.iterators.FilterListIterator;
/*  25:    */ import org.apache.commons.collections.iterators.IteratorChain;
/*  26:    */ import org.apache.commons.collections.iterators.IteratorEnumeration;
/*  27:    */ import org.apache.commons.collections.iterators.ListIteratorWrapper;
/*  28:    */ import org.apache.commons.collections.iterators.LoopingIterator;
/*  29:    */ import org.apache.commons.collections.iterators.LoopingListIterator;
/*  30:    */ import org.apache.commons.collections.iterators.ObjectArrayIterator;
/*  31:    */ import org.apache.commons.collections.iterators.ObjectArrayListIterator;
/*  32:    */ import org.apache.commons.collections.iterators.ObjectGraphIterator;
/*  33:    */ import org.apache.commons.collections.iterators.SingletonIterator;
/*  34:    */ import org.apache.commons.collections.iterators.SingletonListIterator;
/*  35:    */ import org.apache.commons.collections.iterators.TransformIterator;
/*  36:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  37:    */ import org.apache.commons.collections.iterators.UnmodifiableListIterator;
/*  38:    */ import org.apache.commons.collections.iterators.UnmodifiableMapIterator;
/*  39:    */ 
/*  40:    */ public class IteratorUtils
/*  41:    */ {
/*  42: 84 */   public static final ResettableIterator EMPTY_ITERATOR = EmptyIterator.RESETTABLE_INSTANCE;
/*  43: 91 */   public static final ResettableListIterator EMPTY_LIST_ITERATOR = EmptyListIterator.RESETTABLE_INSTANCE;
/*  44: 95 */   public static final OrderedIterator EMPTY_ORDERED_ITERATOR = EmptyOrderedIterator.INSTANCE;
/*  45: 99 */   public static final MapIterator EMPTY_MAP_ITERATOR = EmptyMapIterator.INSTANCE;
/*  46:103 */   public static final OrderedMapIterator EMPTY_ORDERED_MAP_ITERATOR = EmptyOrderedMapIterator.INSTANCE;
/*  47:    */   
/*  48:    */   public static ResettableIterator emptyIterator()
/*  49:    */   {
/*  50:125 */     return EMPTY_ITERATOR;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static ResettableListIterator emptyListIterator()
/*  54:    */   {
/*  55:140 */     return EMPTY_LIST_ITERATOR;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static OrderedIterator emptyOrderedIterator()
/*  59:    */   {
/*  60:152 */     return EMPTY_ORDERED_ITERATOR;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static MapIterator emptyMapIterator()
/*  64:    */   {
/*  65:164 */     return EMPTY_MAP_ITERATOR;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static OrderedMapIterator emptyOrderedMapIterator()
/*  69:    */   {
/*  70:176 */     return EMPTY_ORDERED_MAP_ITERATOR;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static ResettableIterator singletonIterator(Object object)
/*  74:    */   {
/*  75:194 */     return new SingletonIterator(object);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static ListIterator singletonListIterator(Object object)
/*  79:    */   {
/*  80:207 */     return new SingletonListIterator(object);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static ResettableIterator arrayIterator(Object[] array)
/*  84:    */   {
/*  85:223 */     return new ObjectArrayIterator(array);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static ResettableIterator arrayIterator(Object array)
/*  89:    */   {
/*  90:238 */     return new ArrayIterator(array);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static ResettableIterator arrayIterator(Object[] array, int start)
/*  94:    */   {
/*  95:255 */     return new ObjectArrayIterator(array, start);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static ResettableIterator arrayIterator(Object array, int start)
/*  99:    */   {
/* 100:273 */     return new ArrayIterator(array, start);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static ResettableIterator arrayIterator(Object[] array, int start, int end)
/* 104:    */   {
/* 105:291 */     return new ObjectArrayIterator(array, start, end);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static ResettableIterator arrayIterator(Object array, int start, int end)
/* 109:    */   {
/* 110:310 */     return new ArrayIterator(array, start, end);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static ResettableListIterator arrayListIterator(Object[] array)
/* 114:    */   {
/* 115:322 */     return new ObjectArrayListIterator(array);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static ResettableListIterator arrayListIterator(Object array)
/* 119:    */   {
/* 120:337 */     return new ArrayListIterator(array);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static ResettableListIterator arrayListIterator(Object[] array, int start)
/* 124:    */   {
/* 125:350 */     return new ObjectArrayListIterator(array, start);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static ResettableListIterator arrayListIterator(Object array, int start)
/* 129:    */   {
/* 130:367 */     return new ArrayListIterator(array, start);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static ResettableListIterator arrayListIterator(Object[] array, int start, int end)
/* 134:    */   {
/* 135:382 */     return new ObjectArrayListIterator(array, start, end);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static ResettableListIterator arrayListIterator(Object array, int start, int end)
/* 139:    */   {
/* 140:401 */     return new ArrayListIterator(array, start, end);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static Iterator unmodifiableIterator(Iterator iterator)
/* 144:    */   {
/* 145:415 */     return UnmodifiableIterator.decorate(iterator);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static ListIterator unmodifiableListIterator(ListIterator listIterator)
/* 149:    */   {
/* 150:428 */     return UnmodifiableListIterator.decorate(listIterator);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static MapIterator unmodifiableMapIterator(MapIterator mapIterator)
/* 154:    */   {
/* 155:440 */     return UnmodifiableMapIterator.decorate(mapIterator);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static Iterator chainedIterator(Iterator iterator1, Iterator iterator2)
/* 159:    */   {
/* 160:455 */     return new IteratorChain(iterator1, iterator2);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static Iterator chainedIterator(Iterator[] iterators)
/* 164:    */   {
/* 165:467 */     return new IteratorChain(iterators);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static Iterator chainedIterator(Collection iterators)
/* 169:    */   {
/* 170:480 */     return new IteratorChain(iterators);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static Iterator collatedIterator(Comparator comparator, Iterator iterator1, Iterator iterator2)
/* 174:    */   {
/* 175:502 */     return new CollatingIterator(comparator, iterator1, iterator2);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static Iterator collatedIterator(Comparator comparator, Iterator[] iterators)
/* 179:    */   {
/* 180:521 */     return new CollatingIterator(comparator, iterators);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static Iterator collatedIterator(Comparator comparator, Collection iterators)
/* 184:    */   {
/* 185:541 */     return new CollatingIterator(comparator, iterators);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static Iterator objectGraphIterator(Object root, Transformer transformer)
/* 189:    */   {
/* 190:600 */     return new ObjectGraphIterator(root, transformer);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static Iterator transformedIterator(Iterator iterator, Transformer transform)
/* 194:    */   {
/* 195:617 */     if (iterator == null) {
/* 196:618 */       throw new NullPointerException("Iterator must not be null");
/* 197:    */     }
/* 198:620 */     if (transform == null) {
/* 199:621 */       throw new NullPointerException("Transformer must not be null");
/* 200:    */     }
/* 201:623 */     return new TransformIterator(iterator, transform);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static Iterator filteredIterator(Iterator iterator, Predicate predicate)
/* 205:    */   {
/* 206:640 */     if (iterator == null) {
/* 207:641 */       throw new NullPointerException("Iterator must not be null");
/* 208:    */     }
/* 209:643 */     if (predicate == null) {
/* 210:644 */       throw new NullPointerException("Predicate must not be null");
/* 211:    */     }
/* 212:646 */     return new FilterIterator(iterator, predicate);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static ListIterator filteredListIterator(ListIterator listIterator, Predicate predicate)
/* 216:    */   {
/* 217:661 */     if (listIterator == null) {
/* 218:662 */       throw new NullPointerException("ListIterator must not be null");
/* 219:    */     }
/* 220:664 */     if (predicate == null) {
/* 221:665 */       throw new NullPointerException("Predicate must not be null");
/* 222:    */     }
/* 223:667 */     return new FilterListIterator(listIterator, predicate);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static ResettableIterator loopingIterator(Collection coll)
/* 227:    */   {
/* 228:684 */     if (coll == null) {
/* 229:685 */       throw new NullPointerException("Collection must not be null");
/* 230:    */     }
/* 231:687 */     return new LoopingIterator(coll);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static ResettableListIterator loopingListIterator(List list)
/* 235:    */   {
/* 236:702 */     if (list == null) {
/* 237:703 */       throw new NullPointerException("List must not be null");
/* 238:    */     }
/* 239:705 */     return new LoopingListIterator(list);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static Iterator asIterator(Enumeration enumeration)
/* 243:    */   {
/* 244:717 */     if (enumeration == null) {
/* 245:718 */       throw new NullPointerException("Enumeration must not be null");
/* 246:    */     }
/* 247:720 */     return new EnumerationIterator(enumeration);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public static Iterator asIterator(Enumeration enumeration, Collection removeCollection)
/* 251:    */   {
/* 252:732 */     if (enumeration == null) {
/* 253:733 */       throw new NullPointerException("Enumeration must not be null");
/* 254:    */     }
/* 255:735 */     if (removeCollection == null) {
/* 256:736 */       throw new NullPointerException("Collection must not be null");
/* 257:    */     }
/* 258:738 */     return new EnumerationIterator(enumeration, removeCollection);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public static Enumeration asEnumeration(Iterator iterator)
/* 262:    */   {
/* 263:749 */     if (iterator == null) {
/* 264:750 */       throw new NullPointerException("Iterator must not be null");
/* 265:    */     }
/* 266:752 */     return new IteratorEnumeration(iterator);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public static ListIterator toListIterator(Iterator iterator)
/* 270:    */   {
/* 271:766 */     if (iterator == null) {
/* 272:767 */       throw new NullPointerException("Iterator must not be null");
/* 273:    */     }
/* 274:769 */     return new ListIteratorWrapper(iterator);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public static Object[] toArray(Iterator iterator)
/* 278:    */   {
/* 279:783 */     if (iterator == null) {
/* 280:784 */       throw new NullPointerException("Iterator must not be null");
/* 281:    */     }
/* 282:786 */     List list = toList(iterator, 100);
/* 283:787 */     return list.toArray();
/* 284:    */   }
/* 285:    */   
/* 286:    */   public static Object[] toArray(Iterator iterator, Class arrayClass)
/* 287:    */   {
/* 288:804 */     if (iterator == null) {
/* 289:805 */       throw new NullPointerException("Iterator must not be null");
/* 290:    */     }
/* 291:807 */     if (arrayClass == null) {
/* 292:808 */       throw new NullPointerException("Array class must not be null");
/* 293:    */     }
/* 294:810 */     List list = toList(iterator, 100);
/* 295:811 */     return list.toArray((Object[])Array.newInstance(arrayClass, list.size()));
/* 296:    */   }
/* 297:    */   
/* 298:    */   public static List toList(Iterator iterator)
/* 299:    */   {
/* 300:825 */     return toList(iterator, 10);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public static List toList(Iterator iterator, int estimatedSize)
/* 304:    */   {
/* 305:841 */     if (iterator == null) {
/* 306:842 */       throw new NullPointerException("Iterator must not be null");
/* 307:    */     }
/* 308:844 */     if (estimatedSize < 1) {
/* 309:845 */       throw new IllegalArgumentException("Estimated size must be greater than 0");
/* 310:    */     }
/* 311:847 */     List list = new ArrayList(estimatedSize);
/* 312:848 */     while (iterator.hasNext()) {
/* 313:849 */       list.add(iterator.next());
/* 314:    */     }
/* 315:851 */     return list;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public static Iterator getIterator(Object obj)
/* 319:    */   {
/* 320:874 */     if (obj == null) {
/* 321:875 */       return emptyIterator();
/* 322:    */     }
/* 323:877 */     if ((obj instanceof Iterator)) {
/* 324:878 */       return (Iterator)obj;
/* 325:    */     }
/* 326:880 */     if ((obj instanceof Collection)) {
/* 327:881 */       return ((Collection)obj).iterator();
/* 328:    */     }
/* 329:883 */     if ((obj instanceof Object[])) {
/* 330:884 */       return new ObjectArrayIterator((Object[])obj);
/* 331:    */     }
/* 332:886 */     if ((obj instanceof Enumeration)) {
/* 333:887 */       return new EnumerationIterator((Enumeration)obj);
/* 334:    */     }
/* 335:889 */     if ((obj instanceof Map)) {
/* 336:890 */       return ((Map)obj).values().iterator();
/* 337:    */     }
/* 338:892 */     if ((obj instanceof Dictionary)) {
/* 339:893 */       return new EnumerationIterator(((Dictionary)obj).elements());
/* 340:    */     }
/* 341:895 */     if ((obj != null) && (obj.getClass().isArray())) {
/* 342:896 */       return new ArrayIterator(obj);
/* 343:    */     }
/* 344:    */     try
/* 345:    */     {
/* 346:900 */       Method method = obj.getClass().getMethod("iterator", (Class[])null);
/* 347:901 */       if (Iterator.class.isAssignableFrom(method.getReturnType()))
/* 348:    */       {
/* 349:902 */         Iterator it = (Iterator)method.invoke(obj, (Object[])null);
/* 350:903 */         if (it != null) {
/* 351:904 */           return it;
/* 352:    */         }
/* 353:    */       }
/* 354:    */     }
/* 355:    */     catch (Exception ex) {}
/* 356:910 */     return singletonIterator(obj);
/* 357:    */   }
/* 358:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.IteratorUtils
 * JD-Core Version:    0.7.0.1
 */