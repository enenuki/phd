/*  1:   */ package org.hibernate.transform;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ 
/*  5:   */ public class ToListResultTransformer
/*  6:   */   extends BasicTransformerAdapter
/*  7:   */ {
/*  8:35 */   public static final ToListResultTransformer INSTANCE = new ToListResultTransformer();
/*  9:   */   
/* 10:   */   public Object transformTuple(Object[] tuple, String[] aliases)
/* 11:   */   {
/* 12:47 */     return Arrays.asList(tuple);
/* 13:   */   }
/* 14:   */   
/* 15:   */   private Object readResolve()
/* 16:   */   {
/* 17:56 */     return INSTANCE;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.ToListResultTransformer
 * JD-Core Version:    0.7.0.1
 */