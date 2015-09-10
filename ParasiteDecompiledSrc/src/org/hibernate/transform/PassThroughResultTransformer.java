/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.List;
/*  5:   */ 
/*  6:   */ public class PassThroughResultTransformer
/*  7:   */   extends BasicTransformerAdapter
/*  8:   */   implements TupleSubsetResultTransformer
/*  9:   */ {
/* 10:36 */   public static final PassThroughResultTransformer INSTANCE = new PassThroughResultTransformer();
/* 11:   */   
/* 12:   */   public Object transformTuple(Object[] tuple, String[] aliases)
/* 13:   */   {
/* 14:48 */     return tuple.length == 1 ? tuple[0] : tuple;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength)
/* 18:   */   {
/* 19:55 */     return tupleLength == 1;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean[] includeInTransform(String[] aliases, int tupleLength)
/* 23:   */   {
/* 24:62 */     boolean[] includeInTransformedResult = new boolean[tupleLength];
/* 25:63 */     Arrays.fill(includeInTransformedResult, true);
/* 26:64 */     return includeInTransformedResult;
/* 27:   */   }
/* 28:   */   
/* 29:   */   List untransformToTuples(List results, boolean isSingleResult)
/* 30:   */   {
/* 31:70 */     if (isSingleResult) {
/* 32:71 */       for (int i = 0; i < results.size(); i++)
/* 33:   */       {
/* 34:72 */         Object[] tuple = untransformToTuple(results.get(i), isSingleResult);
/* 35:73 */         results.set(i, tuple);
/* 36:   */       }
/* 37:   */     }
/* 38:76 */     return results;
/* 39:   */   }
/* 40:   */   
/* 41:   */   Object[] untransformToTuple(Object transformed, boolean isSingleResult)
/* 42:   */   {
/* 43:81 */     return isSingleResult ? new Object[] { transformed } : (Object[])transformed;
/* 44:   */   }
/* 45:   */   
/* 46:   */   private Object readResolve()
/* 47:   */   {
/* 48:90 */     return INSTANCE;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.PassThroughResultTransformer
 * JD-Core Version:    0.7.0.1
 */