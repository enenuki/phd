/*   1:    */ package org.hibernate.internal.util.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.IdentityHashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ 
/*   8:    */ public class IdentitySet
/*   9:    */   implements Set
/*  10:    */ {
/*  11: 39 */   private static final Object DUMP_VALUE = new Object();
/*  12:    */   private final IdentityHashMap map;
/*  13:    */   
/*  14:    */   public IdentitySet()
/*  15:    */   {
/*  16: 47 */     this.map = new IdentityHashMap();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public IdentitySet(int sizing)
/*  20:    */   {
/*  21: 56 */     this.map = new IdentityHashMap(sizing);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int size()
/*  25:    */   {
/*  26: 60 */     return this.map.size();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean isEmpty()
/*  30:    */   {
/*  31: 64 */     return this.map.isEmpty();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean contains(Object o)
/*  35:    */   {
/*  36: 68 */     return this.map.get(o) == DUMP_VALUE;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Iterator iterator()
/*  40:    */   {
/*  41: 72 */     return this.map.keySet().iterator();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object[] toArray()
/*  45:    */   {
/*  46: 76 */     return this.map.keySet().toArray();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object[] toArray(Object[] a)
/*  50:    */   {
/*  51: 80 */     return this.map.keySet().toArray(a);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean add(Object o)
/*  55:    */   {
/*  56: 84 */     return this.map.put(o, DUMP_VALUE) == null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean remove(Object o)
/*  60:    */   {
/*  61: 88 */     return this.map.remove(o) == DUMP_VALUE;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean containsAll(Collection c)
/*  65:    */   {
/*  66: 92 */     Iterator it = c.iterator();
/*  67: 93 */     while (it.hasNext()) {
/*  68: 94 */       if (!this.map.containsKey(it.next())) {
/*  69: 95 */         return false;
/*  70:    */       }
/*  71:    */     }
/*  72: 98 */     return true;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean addAll(Collection c)
/*  76:    */   {
/*  77:102 */     Iterator it = c.iterator();
/*  78:103 */     boolean changed = false;
/*  79:104 */     while (it.hasNext()) {
/*  80:105 */       if (add(it.next())) {
/*  81:106 */         changed = true;
/*  82:    */       }
/*  83:    */     }
/*  84:109 */     return changed;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean retainAll(Collection c)
/*  88:    */   {
/*  89:114 */     throw new UnsupportedOperationException();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean removeAll(Collection c)
/*  93:    */   {
/*  94:118 */     Iterator it = c.iterator();
/*  95:119 */     boolean changed = false;
/*  96:120 */     while (it.hasNext()) {
/*  97:121 */       if (remove(it.next())) {
/*  98:122 */         changed = true;
/*  99:    */       }
/* 100:    */     }
/* 101:125 */     return changed;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void clear()
/* 105:    */   {
/* 106:129 */     this.map.clear();
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.IdentitySet
 * JD-Core Version:    0.7.0.1
 */