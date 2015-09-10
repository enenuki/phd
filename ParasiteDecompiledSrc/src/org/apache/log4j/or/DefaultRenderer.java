/*  1:   */ package org.apache.log4j.or;
/*  2:   */ 
/*  3:   */ class DefaultRenderer
/*  4:   */   implements ObjectRenderer
/*  5:   */ {
/*  6:   */   public String doRender(Object o)
/*  7:   */   {
/*  8:   */     try
/*  9:   */     {
/* 10:37 */       return o.toString();
/* 11:   */     }
/* 12:   */     catch (Exception ex)
/* 13:   */     {
/* 14:39 */       return ex.toString();
/* 15:   */     }
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.or.DefaultRenderer
 * JD-Core Version:    0.7.0.1
 */