/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class AliasToEntityMapResultTransformer
/*  7:   */   extends AliasedTupleSubsetResultTransformer
/*  8:   */ {
/*  9:41 */   public static final AliasToEntityMapResultTransformer INSTANCE = new AliasToEntityMapResultTransformer();
/* 10:   */   
/* 11:   */   public Object transformTuple(Object[] tuple, String[] aliases)
/* 12:   */   {
/* 13:53 */     Map result = new HashMap(tuple.length);
/* 14:54 */     for (int i = 0; i < tuple.length; i++)
/* 15:   */     {
/* 16:55 */       String alias = aliases[i];
/* 17:56 */       if (alias != null) {
/* 18:57 */         result.put(alias, tuple[i]);
/* 19:   */       }
/* 20:   */     }
/* 21:60 */     return result;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength)
/* 25:   */   {
/* 26:67 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   private Object readResolve()
/* 30:   */   {
/* 31:76 */     return INSTANCE;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.AliasToEntityMapResultTransformer
 * JD-Core Version:    0.7.0.1
 */