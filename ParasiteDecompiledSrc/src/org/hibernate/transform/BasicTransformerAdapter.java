/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ 
/*  5:   */ public abstract class BasicTransformerAdapter
/*  6:   */   implements ResultTransformer
/*  7:   */ {
/*  8:   */   public Object transformTuple(Object[] tuple, String[] aliases)
/*  9:   */   {
/* 10:35 */     return tuple;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public List transformList(List list)
/* 14:   */   {
/* 15:39 */     return list;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.BasicTransformerAdapter
 * JD-Core Version:    0.7.0.1
 */