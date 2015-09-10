/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import java.util.SortedSet;
/*   8:    */ import java.util.TreeSet;
/*   9:    */ import org.apache.commons.collections.set.ListOrderedSet;
/*  10:    */ import org.apache.commons.collections.set.PredicatedSet;
/*  11:    */ import org.apache.commons.collections.set.PredicatedSortedSet;
/*  12:    */ import org.apache.commons.collections.set.SynchronizedSet;
/*  13:    */ import org.apache.commons.collections.set.SynchronizedSortedSet;
/*  14:    */ import org.apache.commons.collections.set.TransformedSet;
/*  15:    */ import org.apache.commons.collections.set.TransformedSortedSet;
/*  16:    */ import org.apache.commons.collections.set.TypedSet;
/*  17:    */ import org.apache.commons.collections.set.TypedSortedSet;
/*  18:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  19:    */ import org.apache.commons.collections.set.UnmodifiableSortedSet;
/*  20:    */ 
/*  21:    */ public class SetUtils
/*  22:    */ {
/*  23: 57 */   public static final Set EMPTY_SET = Collections.EMPTY_SET;
/*  24: 62 */   public static final SortedSet EMPTY_SORTED_SET = UnmodifiableSortedSet.decorate(new TreeSet());
/*  25:    */   
/*  26:    */   public static boolean isEqualSet(Collection set1, Collection set2)
/*  27:    */   {
/*  28:100 */     if (set1 == set2) {
/*  29:101 */       return true;
/*  30:    */     }
/*  31:103 */     if ((set1 == null) || (set2 == null) || (set1.size() != set2.size())) {
/*  32:104 */       return false;
/*  33:    */     }
/*  34:107 */     return set1.containsAll(set2);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static int hashCodeForSet(Collection set)
/*  38:    */   {
/*  39:123 */     if (set == null) {
/*  40:124 */       return 0;
/*  41:    */     }
/*  42:126 */     int hashCode = 0;
/*  43:127 */     Iterator it = set.iterator();
/*  44:128 */     Object obj = null;
/*  45:130 */     while (it.hasNext())
/*  46:    */     {
/*  47:131 */       obj = it.next();
/*  48:132 */       if (obj != null) {
/*  49:133 */         hashCode += obj.hashCode();
/*  50:    */       }
/*  51:    */     }
/*  52:136 */     return hashCode;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Set synchronizedSet(Set set)
/*  56:    */   {
/*  57:163 */     return SynchronizedSet.decorate(set);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static Set unmodifiableSet(Set set)
/*  61:    */   {
/*  62:176 */     return UnmodifiableSet.decorate(set);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static Set predicatedSet(Set set, Predicate predicate)
/*  66:    */   {
/*  67:193 */     return PredicatedSet.decorate(set, predicate);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static Set typedSet(Set set, Class type)
/*  71:    */   {
/*  72:206 */     return TypedSet.decorate(set, type);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Set transformedSet(Set set, Transformer transformer)
/*  76:    */   {
/*  77:222 */     return TransformedSet.decorate(set, transformer);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static Set orderedSet(Set set)
/*  81:    */   {
/*  82:237 */     return ListOrderedSet.decorate(set);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static SortedSet synchronizedSortedSet(SortedSet set)
/*  86:    */   {
/*  87:264 */     return SynchronizedSortedSet.decorate(set);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static SortedSet unmodifiableSortedSet(SortedSet set)
/*  91:    */   {
/*  92:277 */     return UnmodifiableSortedSet.decorate(set);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static SortedSet predicatedSortedSet(SortedSet set, Predicate predicate)
/*  96:    */   {
/*  97:294 */     return PredicatedSortedSet.decorate(set, predicate);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static SortedSet typedSortedSet(SortedSet set, Class type)
/* 101:    */   {
/* 102:307 */     return TypedSortedSet.decorate(set, type);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static SortedSet transformedSortedSet(SortedSet set, Transformer transformer)
/* 106:    */   {
/* 107:323 */     return TransformedSortedSet.decorate(set, transformer);
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.SetUtils
 * JD-Core Version:    0.7.0.1
 */