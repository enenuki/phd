/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ public class AvgWithArgumentCastFunction
/*  4:   */   extends StandardAnsiSqlAggregationFunctions.AvgFunction
/*  5:   */ {
/*  6:   */   private final String castType;
/*  7:   */   
/*  8:   */   public AvgWithArgumentCastFunction(String castType)
/*  9:   */   {
/* 10:38 */     this.castType = castType;
/* 11:   */   }
/* 12:   */   
/* 13:   */   protected String renderArgument(String argument, int firstArgumentJdbcType)
/* 14:   */   {
/* 15:43 */     if ((firstArgumentJdbcType == 8) || (firstArgumentJdbcType == 6)) {
/* 16:44 */       return argument;
/* 17:   */     }
/* 18:47 */     return "cast(" + argument + " as " + this.castType + ")";
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.AvgWithArgumentCastFunction
 * JD-Core Version:    0.7.0.1
 */