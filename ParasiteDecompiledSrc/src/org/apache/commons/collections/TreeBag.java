/*  1:   */ package org.apache.commons.collections;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import java.util.SortedMap;
/*  6:   */ import java.util.TreeMap;
/*  7:   */ 
/*  8:   */ /**
/*  9:   */  * @deprecated
/* 10:   */  */
/* 11:   */ public class TreeBag
/* 12:   */   extends DefaultMapBag
/* 13:   */   implements SortedBag
/* 14:   */ {
/* 15:   */   public TreeBag()
/* 16:   */   {
/* 17:41 */     super(new TreeMap());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public TreeBag(Comparator comparator)
/* 21:   */   {
/* 22:51 */     super(new TreeMap(comparator));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public TreeBag(Collection coll)
/* 26:   */   {
/* 27:61 */     this();
/* 28:62 */     addAll(coll);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object first()
/* 32:   */   {
/* 33:66 */     return ((SortedMap)getMap()).firstKey();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Object last()
/* 37:   */   {
/* 38:70 */     return ((SortedMap)getMap()).lastKey();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Comparator comparator()
/* 42:   */   {
/* 43:74 */     return ((SortedMap)getMap()).comparator();
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.TreeBag
 * JD-Core Version:    0.7.0.1
 */