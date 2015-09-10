/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  4:   */ 
/*  5:   */ public final class RootEntityResultTransformer
/*  6:   */   extends BasicTransformerAdapter
/*  7:   */   implements TupleSubsetResultTransformer
/*  8:   */ {
/*  9:41 */   public static final RootEntityResultTransformer INSTANCE = new RootEntityResultTransformer();
/* 10:   */   
/* 11:   */   public Object transformTuple(Object[] tuple, String[] aliases)
/* 12:   */   {
/* 13:54 */     return tuple[(tuple.length - 1)];
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength)
/* 17:   */   {
/* 18:61 */     return true;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean[] includeInTransform(String[] aliases, int tupleLength)
/* 22:   */   {
/* 23:   */     boolean[] includeInTransform;
/* 24:   */     boolean[] includeInTransform;
/* 25:70 */     if (tupleLength == 1)
/* 26:   */     {
/* 27:71 */       includeInTransform = ArrayHelper.TRUE;
/* 28:   */     }
/* 29:   */     else
/* 30:   */     {
/* 31:74 */       includeInTransform = new boolean[tupleLength];
/* 32:75 */       includeInTransform[(tupleLength - 1)] = true;
/* 33:   */     }
/* 34:77 */     return includeInTransform;
/* 35:   */   }
/* 36:   */   
/* 37:   */   private Object readResolve()
/* 38:   */   {
/* 39:86 */     return INSTANCE;
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.RootEntityResultTransformer
 * JD-Core Version:    0.7.0.1
 */