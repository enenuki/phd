/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ 
/*  5:   */ public class DistinctRootEntityResultTransformer
/*  6:   */   implements TupleSubsetResultTransformer
/*  7:   */ {
/*  8:40 */   public static final DistinctRootEntityResultTransformer INSTANCE = new DistinctRootEntityResultTransformer();
/*  9:   */   
/* 10:   */   public Object transformTuple(Object[] tuple, String[] aliases)
/* 11:   */   {
/* 12:56 */     return RootEntityResultTransformer.INSTANCE.transformTuple(tuple, aliases);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public List transformList(List list)
/* 16:   */   {
/* 17:66 */     return DistinctResultTransformer.INSTANCE.transformList(list);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean[] includeInTransform(String[] aliases, int tupleLength)
/* 21:   */   {
/* 22:73 */     return RootEntityResultTransformer.INSTANCE.includeInTransform(aliases, tupleLength);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength)
/* 26:   */   {
/* 27:80 */     return RootEntityResultTransformer.INSTANCE.isTransformedValueATupleElement(null, tupleLength);
/* 28:   */   }
/* 29:   */   
/* 30:   */   private Object readResolve()
/* 31:   */   {
/* 32:89 */     return INSTANCE;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.DistinctRootEntityResultTransformer
 * JD-Core Version:    0.7.0.1
 */