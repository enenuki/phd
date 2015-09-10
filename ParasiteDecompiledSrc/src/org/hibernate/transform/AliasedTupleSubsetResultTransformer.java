/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ public abstract class AliasedTupleSubsetResultTransformer
/*  4:   */   extends BasicTransformerAdapter
/*  5:   */   implements TupleSubsetResultTransformer
/*  6:   */ {
/*  7:   */   public boolean[] includeInTransform(String[] aliases, int tupleLength)
/*  8:   */   {
/*  9:42 */     if (aliases == null) {
/* 10:43 */       throw new IllegalArgumentException("aliases cannot be null");
/* 11:   */     }
/* 12:45 */     if (aliases.length != tupleLength) {
/* 13:46 */       throw new IllegalArgumentException("aliases and tupleLength must have the same length; aliases.length=" + aliases.length + "tupleLength=" + tupleLength);
/* 14:   */     }
/* 15:51 */     boolean[] includeInTransform = new boolean[tupleLength];
/* 16:52 */     for (int i = 0; i < aliases.length; i++) {
/* 17:53 */       if (aliases[i] != null) {
/* 18:54 */         includeInTransform[i] = true;
/* 19:   */       }
/* 20:   */     }
/* 21:57 */     return includeInTransform;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.AliasedTupleSubsetResultTransformer
 * JD-Core Version:    0.7.0.1
 */