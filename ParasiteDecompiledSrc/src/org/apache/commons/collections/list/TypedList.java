/*  1:   */ package org.apache.commons.collections.list;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.apache.commons.collections.functors.InstanceofPredicate;
/*  5:   */ 
/*  6:   */ public class TypedList
/*  7:   */ {
/*  8:   */   public static List decorate(List list, Class type)
/*  9:   */   {
/* 10:51 */     return new PredicatedList(list, InstanceofPredicate.getInstance(type));
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.TypedList
 * JD-Core Version:    0.7.0.1
 */