/*  1:   */ package org.apache.xml.dtm;
/*  2:   */ 
/*  3:   */ public abstract class DTMAxisTraverser
/*  4:   */ {
/*  5:   */   public int first(int context)
/*  6:   */   {
/*  7:61 */     return next(context, context);
/*  8:   */   }
/*  9:   */   
/* 10:   */   public int first(int context, int extendedTypeID)
/* 11:   */   {
/* 12:80 */     return next(context, context, extendedTypeID);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public abstract int next(int paramInt1, int paramInt2);
/* 16:   */   
/* 17:   */   public abstract int next(int paramInt1, int paramInt2, int paramInt3);
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.DTMAxisTraverser
 * JD-Core Version:    0.7.0.1
 */