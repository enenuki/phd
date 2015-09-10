/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import org.apache.commons.collections.list.FixedSizeList;
/*   9:    */ import org.apache.commons.collections.list.LazyList;
/*  10:    */ import org.apache.commons.collections.list.PredicatedList;
/*  11:    */ import org.apache.commons.collections.list.SynchronizedList;
/*  12:    */ import org.apache.commons.collections.list.TransformedList;
/*  13:    */ import org.apache.commons.collections.list.TypedList;
/*  14:    */ import org.apache.commons.collections.list.UnmodifiableList;
/*  15:    */ 
/*  16:    */ public class ListUtils
/*  17:    */ {
/*  18: 53 */   public static final List EMPTY_LIST = Collections.EMPTY_LIST;
/*  19:    */   
/*  20:    */   public static List intersection(List list1, List list2)
/*  21:    */   {
/*  22: 72 */     ArrayList result = new ArrayList();
/*  23: 73 */     Iterator iterator = list2.iterator();
/*  24: 75 */     while (iterator.hasNext())
/*  25:    */     {
/*  26: 76 */       Object o = iterator.next();
/*  27: 78 */       if (list1.contains(o)) {
/*  28: 79 */         result.add(o);
/*  29:    */       }
/*  30:    */     }
/*  31: 83 */     return result;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static List subtract(List list1, List list2)
/*  35:    */   {
/*  36:102 */     ArrayList result = new ArrayList(list1);
/*  37:103 */     Iterator iterator = list2.iterator();
/*  38:105 */     while (iterator.hasNext()) {
/*  39:106 */       result.remove(iterator.next());
/*  40:    */     }
/*  41:109 */     return result;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static List sum(List list1, List list2)
/*  45:    */   {
/*  46:122 */     return subtract(union(list1, list2), intersection(list1, list2));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static List union(List list1, List list2)
/*  50:    */   {
/*  51:136 */     ArrayList result = new ArrayList(list1);
/*  52:137 */     result.addAll(list2);
/*  53:138 */     return result;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static boolean isEqualList(Collection list1, Collection list2)
/*  57:    */   {
/*  58:171 */     if (list1 == list2) {
/*  59:172 */       return true;
/*  60:    */     }
/*  61:174 */     if ((list1 == null) || (list2 == null) || (list1.size() != list2.size())) {
/*  62:175 */       return false;
/*  63:    */     }
/*  64:178 */     Iterator it1 = list1.iterator();
/*  65:179 */     Iterator it2 = list2.iterator();
/*  66:180 */     Object obj1 = null;
/*  67:181 */     Object obj2 = null;
/*  68:183 */     while ((it1.hasNext()) && (it2.hasNext()))
/*  69:    */     {
/*  70:184 */       obj1 = it1.next();
/*  71:185 */       obj2 = it2.next();
/*  72:187 */       if (obj1 == null ? obj2 != null : !obj1.equals(obj2)) {
/*  73:188 */         return false;
/*  74:    */       }
/*  75:    */     }
/*  76:192 */     return (!it1.hasNext()) && (!it2.hasNext());
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static int hashCodeForList(Collection list)
/*  80:    */   {
/*  81:208 */     if (list == null) {
/*  82:209 */       return 0;
/*  83:    */     }
/*  84:211 */     int hashCode = 1;
/*  85:212 */     Iterator it = list.iterator();
/*  86:213 */     Object obj = null;
/*  87:215 */     while (it.hasNext())
/*  88:    */     {
/*  89:216 */       obj = it.next();
/*  90:217 */       hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
/*  91:    */     }
/*  92:219 */     return hashCode;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static List retainAll(Collection collection, Collection retain)
/*  96:    */   {
/*  97:239 */     List list = new ArrayList(Math.min(collection.size(), retain.size()));
/*  98:241 */     for (Iterator iter = collection.iterator(); iter.hasNext();)
/*  99:    */     {
/* 100:242 */       Object obj = iter.next();
/* 101:243 */       if (retain.contains(obj)) {
/* 102:244 */         list.add(obj);
/* 103:    */       }
/* 104:    */     }
/* 105:247 */     return list;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static List removeAll(Collection collection, Collection remove)
/* 109:    */   {
/* 110:267 */     List list = new ArrayList();
/* 111:268 */     for (Iterator iter = collection.iterator(); iter.hasNext();)
/* 112:    */     {
/* 113:269 */       Object obj = iter.next();
/* 114:270 */       if (!remove.contains(obj)) {
/* 115:271 */         list.add(obj);
/* 116:    */       }
/* 117:    */     }
/* 118:274 */     return list;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static List synchronizedList(List list)
/* 122:    */   {
/* 123:301 */     return SynchronizedList.decorate(list);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static List unmodifiableList(List list)
/* 127:    */   {
/* 128:314 */     return UnmodifiableList.decorate(list);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static List predicatedList(List list, Predicate predicate)
/* 132:    */   {
/* 133:331 */     return PredicatedList.decorate(list, predicate);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static List typedList(List list, Class type)
/* 137:    */   {
/* 138:344 */     return TypedList.decorate(list, type);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static List transformedList(List list, Transformer transformer)
/* 142:    */   {
/* 143:360 */     return TransformedList.decorate(list, transformer);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static List lazyList(List list, Factory factory)
/* 147:    */   {
/* 148:393 */     return LazyList.decorate(list, factory);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static List fixedSizeList(List list)
/* 152:    */   {
/* 153:407 */     return FixedSizeList.decorate(list);
/* 154:    */   }
/* 155:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.ListUtils
 * JD-Core Version:    0.7.0.1
 */